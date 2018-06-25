package org.telegram.messenger;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.Toast;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import org.ir.talaeii.R;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.ContactChangeLog;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p159b.C2666a;
import org.telegram.customization.util.C2885i;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.query.BotQuery;
import org.telegram.messenger.query.DraftQuery;
import org.telegram.messenger.query.MessagesQuery;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_config;
import org.telegram.tgnet.TLRPC$TL_contactBlocked;
import org.telegram.tgnet.TLRPC$TL_contactLinkContact;
import org.telegram.tgnet.TLRPC$TL_contacts_block;
import org.telegram.tgnet.TLRPC$TL_contacts_getBlocked;
import org.telegram.tgnet.TLRPC$TL_contacts_resolveUsername;
import org.telegram.tgnet.TLRPC$TL_contacts_resolvedPeer;
import org.telegram.tgnet.TLRPC$TL_contacts_unblock;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_disabledFeature;
import org.telegram.tgnet.TLRPC$TL_documentEmpty;
import org.telegram.tgnet.TLRPC$TL_draftMessage;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC$TL_encryptedChatRequested;
import org.telegram.tgnet.TLRPC$TL_encryptedChatWaiting;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_help_getAppChangelog;
import org.telegram.tgnet.TLRPC$TL_help_getRecentMeUrls;
import org.telegram.tgnet.TLRPC$TL_help_recentMeUrls;
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
import org.telegram.tgnet.TLRPC$TL_messageMediaUnsupported;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_messages_addChatUser;
import org.telegram.tgnet.TLRPC$TL_messages_affectedHistory;
import org.telegram.tgnet.TLRPC$TL_messages_affectedMessages;
import org.telegram.tgnet.TLRPC$TL_messages_channelMessages;
import org.telegram.tgnet.TLRPC$TL_messages_chatFull;
import org.telegram.tgnet.TLRPC$TL_messages_createChat;
import org.telegram.tgnet.TLRPC$TL_messages_deleteChatUser;
import org.telegram.tgnet.TLRPC$TL_messages_deleteHistory;
import org.telegram.tgnet.TLRPC$TL_messages_deleteMessages;
import org.telegram.tgnet.TLRPC$TL_messages_dialogs;
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
import org.telegram.tgnet.TLRPC$TL_messages_getUnreadMentions;
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
import org.telegram.tgnet.TLRPC$TL_peerSettings;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC$TL_photos_deletePhotos;
import org.telegram.tgnet.TLRPC$TL_photos_getUserPhotos;
import org.telegram.tgnet.TLRPC$TL_photos_photo;
import org.telegram.tgnet.TLRPC$TL_photos_photos;
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
import org.telegram.tgnet.TLRPC$TL_updateMessageID;
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
import org.telegram.tgnet.TLRPC$TL_updates_channelDifference;
import org.telegram.tgnet.TLRPC$TL_updates_channelDifferenceEmpty;
import org.telegram.tgnet.TLRPC$TL_updates_channelDifferenceTooLong;
import org.telegram.tgnet.TLRPC$TL_updates_difference;
import org.telegram.tgnet.TLRPC$TL_updates_differenceEmpty;
import org.telegram.tgnet.TLRPC$TL_updates_differenceSlice;
import org.telegram.tgnet.TLRPC$TL_updates_differenceTooLong;
import org.telegram.tgnet.TLRPC$TL_updates_getChannelDifference;
import org.telegram.tgnet.TLRPC$TL_updates_getDifference;
import org.telegram.tgnet.TLRPC$TL_updates_getState;
import org.telegram.tgnet.TLRPC$TL_updates_state;
import org.telegram.tgnet.TLRPC$TL_userForeign_old2;
import org.telegram.tgnet.TLRPC$TL_userFull;
import org.telegram.tgnet.TLRPC$TL_userProfilePhoto;
import org.telegram.tgnet.TLRPC$TL_userProfilePhotoEmpty;
import org.telegram.tgnet.TLRPC$TL_users_getFullUser;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.tgnet.TLRPC$TL_webPageEmpty;
import org.telegram.tgnet.TLRPC$TL_webPagePending;
import org.telegram.tgnet.TLRPC$TL_webPageUrlPending;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$UserProfilePhoto;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC$contacts_Blocked;
import org.telegram.tgnet.TLRPC$messages_Dialogs;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC$photos_Photos;
import org.telegram.tgnet.TLRPC$updates_ChannelDifference;
import org.telegram.tgnet.TLRPC$updates_Difference;
import org.telegram.tgnet.TLRPC.BotInfo;
import org.telegram.tgnet.TLRPC.ChannelParticipant;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ChatParticipant;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DraftMessage;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.EncryptedMessage;
import org.telegram.tgnet.TLRPC.ExportedChatInvite;
import org.telegram.tgnet.TLRPC.InputChannel;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.InputPeer;
import org.telegram.tgnet.TLRPC.InputPhoto;
import org.telegram.tgnet.TLRPC.InputUser;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.Peer;
import org.telegram.tgnet.TLRPC.PeerNotifySettings;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.RecentMeUrl;
import org.telegram.tgnet.TLRPC.SendMessageAction;
import org.telegram.tgnet.TLRPC.TL_account_registerDevice;
import org.telegram.tgnet.TLRPC.TL_account_unregisterDevice;
import org.telegram.tgnet.TLRPC.TL_account_updateStatus;
import org.telegram.tgnet.TLRPC.TL_auth_logOut;
import org.telegram.tgnet.TLRPC.TL_boolTrue;
import org.telegram.tgnet.TLRPC.TL_botInfo;
import org.telegram.tgnet.TLRPC.TL_channel;
import org.telegram.tgnet.TLRPC.TL_channelAdminRights;
import org.telegram.tgnet.TLRPC.TL_channelBannedRights;
import org.telegram.tgnet.TLRPC.TL_channelForbidden;
import org.telegram.tgnet.TLRPC.TL_channelMessagesFilterEmpty;
import org.telegram.tgnet.TLRPC.TL_channelParticipantSelf;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsAdmins;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsRecent;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipant;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC.TL_channels_createChannel;
import org.telegram.tgnet.TLRPC.TL_channels_deleteChannel;
import org.telegram.tgnet.TLRPC.TL_channels_deleteHistory;
import org.telegram.tgnet.TLRPC.TL_channels_deleteMessages;
import org.telegram.tgnet.TLRPC.TL_channels_deleteUserHistory;
import org.telegram.tgnet.TLRPC.TL_channels_editAbout;
import org.telegram.tgnet.TLRPC.TL_channels_editAdmin;
import org.telegram.tgnet.TLRPC.TL_channels_editBanned;
import org.telegram.tgnet.TLRPC.TL_channels_editPhoto;
import org.telegram.tgnet.TLRPC.TL_channels_editTitle;
import org.telegram.tgnet.TLRPC.TL_channels_getFullChannel;
import org.telegram.tgnet.TLRPC.TL_channels_getMessages;
import org.telegram.tgnet.TLRPC.TL_channels_getParticipant;
import org.telegram.tgnet.TLRPC.TL_channels_getParticipants;
import org.telegram.tgnet.TLRPC.TL_channels_inviteToChannel;
import org.telegram.tgnet.TLRPC.TL_channels_joinChannel;
import org.telegram.tgnet.TLRPC.TL_channels_leaveChannel;
import org.telegram.tgnet.TLRPC.TL_channels_readHistory;
import org.telegram.tgnet.TLRPC.TL_channels_readMessageContents;
import org.telegram.tgnet.TLRPC.TL_channels_toggleInvites;
import org.telegram.tgnet.TLRPC.TL_channels_togglePreHistoryHidden;
import org.telegram.tgnet.TLRPC.TL_channels_toggleSignatures;
import org.telegram.tgnet.TLRPC.TL_channels_updatePinnedMessage;
import org.telegram.tgnet.TLRPC.TL_channels_updateUsername;
import org.telegram.tgnet.TLRPC.TL_chat;
import org.telegram.tgnet.TLRPC.TL_chatFull;
import org.telegram.tgnet.TLRPC.TL_chatInviteEmpty;
import org.telegram.tgnet.TLRPC.TL_chatParticipant;
import org.telegram.tgnet.TLRPC.TL_chatParticipants;
import org.telegram.tgnet.TLRPC.TL_chatPhotoEmpty;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.ProfileActivity;
import utils.C5319b;
import utils.p178a.C3791b;

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
    public int callReceiveTimeout = 20000;
    public int callRingTimeout = 90000;
    ArrayList<Integer> categoriesIds = new ArrayList();
    private HashMap<Integer, ArrayList<Integer>> channelAdmins = new HashMap();
    private SparseArray<ArrayList<Integer>> channelViewsToSend = new SparseArray();
    private HashMap<Integer, Integer> channelsPts = new HashMap();
    private ConcurrentHashMap<Integer, Chat> chats = new ConcurrentHashMap(100, 1.0f, 2);
    private HashMap<Integer, Boolean> checkingLastMessagesDialogs = new HashMap();
    private ArrayList<Long> createdDialogIds = new ArrayList();
    private ArrayList<Long> createdDialogMainThreadIds = new ArrayList();
    private Runnable currentDeleteTaskRunnable;
    private int currentDeletingTaskChannelId;
    private ArrayList<Integer> currentDeletingTaskMids;
    private int currentDeletingTaskTime;
    public boolean defaultP2pContacts = false;
    private final Comparator<TLRPC$TL_dialog> dialogComparator = new C32041();
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
    private ConcurrentHashMap<Integer, EncryptedChat> encryptedChats = new ConcurrentHashMap(10, 1.0f, 2);
    private HashMap<Integer, ExportedChatInvite> exportedChats = new HashMap();
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
    public ArrayList<RecentMeUrl> hintDialogs = new ArrayList();
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
    public int maxGroupCount = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    public int maxMegagroupCount = 10000;
    public int maxPinnedDialogsCount = 5;
    public int maxRecentGifsCount = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    public int maxRecentStickersCount = 30;
    private boolean migratingDialogs;
    public int minGroupConvertSize = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    private SparseIntArray needShortPollChannels = new SparseIntArray();
    public int nextDialogsCacheOffset;
    private ConcurrentHashMap<String, TLObject> objectsByUsernames = new ConcurrentHashMap(100, 1.0f, 2);
    private boolean offlineSent;
    public ConcurrentHashMap<Integer, Integer> onlinePrivacy = new ConcurrentHashMap(20, 1.0f, 2);
    public HashMap<Long, CharSequence> printingStrings = new HashMap();
    public HashMap<Long, Integer> printingStringsTypes = new HashMap();
    public ConcurrentHashMap<Long, ArrayList<PrintingUser>> printingUsers = new ConcurrentHashMap(20, 1.0f, 2);
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
    private final Comparator<TLRPC$Update> updatesComparator = new C32092();
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

    /* renamed from: org.telegram.messenger.MessagesController$1 */
    class C32041 implements Comparator<TLRPC$TL_dialog> {
        C32041() {
        }

        public int compare(TLRPC$TL_dialog tLRPC$TL_dialog, TLRPC$TL_dialog tLRPC$TL_dialog2) {
            if (!tLRPC$TL_dialog.pinned && tLRPC$TL_dialog2.pinned) {
                return 1;
            }
            if (tLRPC$TL_dialog.pinned && !tLRPC$TL_dialog2.pinned) {
                return -1;
            }
            if (tLRPC$TL_dialog.pinned && tLRPC$TL_dialog2.pinned) {
                return tLRPC$TL_dialog.pinnedNum < tLRPC$TL_dialog2.pinnedNum ? 1 : tLRPC$TL_dialog.pinnedNum > tLRPC$TL_dialog2.pinnedNum ? -1 : 0;
            } else {
                DraftMessage draft = DraftQuery.getDraft(tLRPC$TL_dialog.id);
                int i = (draft == null || draft.date < tLRPC$TL_dialog.last_message_date) ? tLRPC$TL_dialog.last_message_date : draft.date;
                DraftMessage draft2 = DraftQuery.getDraft(tLRPC$TL_dialog2.id);
                int i2 = (draft2 == null || draft2.date < tLRPC$TL_dialog2.last_message_date) ? tLRPC$TL_dialog2.last_message_date : draft2.date;
                return i < i2 ? 1 : i > i2 ? -1 : 0;
            }
        }
    }

    /* renamed from: org.telegram.messenger.MessagesController$2 */
    class C32092 implements Comparator<TLRPC$Update> {
        C32092() {
        }

        public int compare(TLRPC$Update tLRPC$Update, TLRPC$Update tLRPC$Update2) {
            int access$000 = MessagesController.this.getUpdateType(tLRPC$Update);
            int access$0002 = MessagesController.this.getUpdateType(tLRPC$Update2);
            if (access$000 != access$0002) {
                return AndroidUtilities.compare(access$000, access$0002);
            }
            if (access$000 == 0) {
                return AndroidUtilities.compare(tLRPC$Update.pts, tLRPC$Update2.pts);
            }
            if (access$000 == 1) {
                return AndroidUtilities.compare(tLRPC$Update.qts, tLRPC$Update2.qts);
            }
            if (access$000 != 2) {
                return 0;
            }
            access$000 = MessagesController.this.getUpdateChannelId(tLRPC$Update);
            access$0002 = MessagesController.this.getUpdateChannelId(tLRPC$Update2);
            return access$000 == access$0002 ? AndroidUtilities.compare(tLRPC$Update.pts, tLRPC$Update2.pts) : AndroidUtilities.compare(access$000, access$0002);
        }
    }

    /* renamed from: org.telegram.messenger.MessagesController$3 */
    class C32153 implements Runnable {
        C32153() {
        }

        public void run() {
            MessagesController instance = MessagesController.getInstance();
            NotificationCenter.getInstance().addObserver(instance, NotificationCenter.FileDidUpload);
            NotificationCenter.getInstance().addObserver(instance, NotificationCenter.FileDidFailUpload);
            NotificationCenter.getInstance().addObserver(instance, NotificationCenter.FileDidLoaded);
            NotificationCenter.getInstance().addObserver(instance, NotificationCenter.FileDidFailedLoad);
            NotificationCenter.getInstance().addObserver(instance, NotificationCenter.messageReceivedByServer);
            NotificationCenter.getInstance().addObserver(instance, NotificationCenter.updateMessageMedia);
        }
    }

    /* renamed from: org.telegram.messenger.MessagesController$5 */
    class C32245 implements RequestDelegate {

        /* renamed from: org.telegram.messenger.MessagesController$5$1 */
        class C32191 implements Runnable {
            C32191() {
            }

            public void run() {
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(2));
                UserConfig.saveConfig(true);
            }
        }

        C32245() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLRPC$TL_error == null) {
                User user = MessagesController.this.getUser(Integer.valueOf(UserConfig.getClientUserId()));
                if (user == null) {
                    user = UserConfig.getCurrentUser();
                    MessagesController.this.putUser(user, true);
                } else {
                    UserConfig.setCurrentUser(user);
                }
                if (user != null) {
                    TLRPC$TL_photos_photo tLRPC$TL_photos_photo = (TLRPC$TL_photos_photo) tLObject;
                    ArrayList arrayList = tLRPC$TL_photos_photo.photo.sizes;
                    PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, 100);
                    PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(arrayList, 1000);
                    user.photo = new TLRPC$TL_userProfilePhoto();
                    user.photo.photo_id = tLRPC$TL_photos_photo.photo.id;
                    if (closestPhotoSizeWithSize != null) {
                        user.photo.photo_small = closestPhotoSizeWithSize.location;
                    }
                    if (closestPhotoSizeWithSize2 != null) {
                        user.photo.photo_big = closestPhotoSizeWithSize2.location;
                    } else if (closestPhotoSizeWithSize != null) {
                        user.photo.photo_small = closestPhotoSizeWithSize.location;
                    }
                    MessagesStorage.getInstance().clearUserPhotos(user.id);
                    arrayList = new ArrayList();
                    arrayList.add(user);
                    MessagesStorage.getInstance().putUsersAndChats(arrayList, null, false, true);
                    AndroidUtilities.runOnUIThread(new C32191());
                }
            }
        }
    }

    /* renamed from: org.telegram.messenger.MessagesController$6 */
    class C32366 implements Runnable {
        C32366() {
        }

        public void run() {
            MessagesController.this.updatesQueueSeq.clear();
            MessagesController.this.updatesQueuePts.clear();
            MessagesController.this.updatesQueueQts.clear();
            MessagesController.this.gettingUnknownChannels.clear();
            MessagesController.this.updatesStartWaitTimeSeq = 0;
            MessagesController.this.updatesStartWaitTimePts = 0;
            MessagesController.this.updatesStartWaitTimeQts = 0;
            MessagesController.this.createdDialogIds.clear();
            MessagesController.this.gettingDifference = false;
            MessagesController.this.resetDialogsPinned = null;
            MessagesController.this.resetDialogsAll = null;
        }
    }

    /* renamed from: org.telegram.messenger.MessagesController$7 */
    class C32417 implements Runnable {
        C32417() {
        }

        public void run() {
            ConnectionsManager.getInstance().setIsUpdating(false);
            MessagesController.this.updatesQueueChannels.clear();
            MessagesController.this.updatesStartWaitTimeChannels.clear();
            MessagesController.this.gettingDifferenceChannels.clear();
            MessagesController.this.channelsPts.clear();
            MessagesController.this.shortPollChannels.clear();
            MessagesController.this.needShortPollChannels.clear();
        }
    }

    /* renamed from: org.telegram.messenger.MessagesController$9 */
    class C32619 implements Runnable {
        C32619() {
        }

        public void run() {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(4));
        }
    }

    public static class PrintingUser {
        public SendMessageAction action;
        public long lastTime;
        public int userId;
    }

    private class UserActionUpdatesPts extends TLRPC$Updates {
        private UserActionUpdatesPts() {
        }
    }

    private class UserActionUpdatesSeq extends TLRPC$Updates {
        private UserActionUpdatesSeq() {
        }
    }

    public MessagesController() {
        ImageLoader.getInstance();
        MessagesStorage.getInstance();
        LocationController.getInstance();
        AndroidUtilities.runOnUIThread(new C32153());
        addSupportUser();
        this.enableJoined = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("EnableContactJoined", true);
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        this.hideLeftGroup = sharedPreferences.getBoolean("hideLeftGroup", false);
        this.hideJoinedGroup = sharedPreferences.getBoolean("hideJoinedGroup", false);
        SharedPreferences sharedPreferences2 = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        this.secretWebpagePreview = sharedPreferences2.getInt("secretWebpage2", 2);
        this.maxGroupCount = sharedPreferences2.getInt("maxGroupCount", Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.maxMegagroupCount = sharedPreferences2.getInt("maxMegagroupCount", 10000);
        this.maxRecentGifsCount = sharedPreferences2.getInt("maxRecentGifsCount", Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.maxRecentStickersCount = sharedPreferences2.getInt("maxRecentStickersCount", 30);
        this.maxFaveStickersCount = sharedPreferences2.getInt("maxFaveStickersCount", 5);
        this.maxEditTime = sharedPreferences2.getInt("maxEditTime", 3600);
        this.groupBigSize = sharedPreferences2.getInt("groupBigSize", 10);
        this.ratingDecay = sharedPreferences2.getInt("ratingDecay", 2419200);
        this.fontSize = sharedPreferences2.getInt("fons_size", AndroidUtilities.isTablet() ? 18 : 16);
        this.allowBigEmoji = sharedPreferences2.getBoolean("allowBigEmoji", false);
        this.useSystemEmoji = sharedPreferences2.getBoolean("useSystemEmoji", false);
        this.linkPrefix = sharedPreferences2.getString("linkPrefix", "t.me");
        this.callReceiveTimeout = sharedPreferences2.getInt("callReceiveTimeout", 20000);
        this.callRingTimeout = sharedPreferences2.getInt("callRingTimeout", 90000);
        this.callConnectTimeout = sharedPreferences2.getInt("callConnectTimeout", DefaultLoadControl.DEFAULT_MAX_BUFFER_MS);
        this.callPacketTimeout = sharedPreferences2.getInt("callPacketTimeout", 10000);
        this.maxPinnedDialogsCount = sharedPreferences2.getInt("maxPinnedDialogsCount", 5);
        this.installReferer = sharedPreferences2.getString("installReferer", null);
        this.defaultP2pContacts = sharedPreferences2.getBoolean("defaultP2pContacts", false);
        String string = sharedPreferences2.getString("disabledFeatures", null);
        if (string != null && string.length() != 0) {
            try {
                byte[] decode = Base64.decode(string, 0);
                if (decode != null) {
                    AbstractSerializedData serializedData = new SerializedData(decode);
                    int readInt32 = serializedData.readInt32(false);
                    for (int i = 0; i < readInt32; i++) {
                        TLRPC$TL_disabledFeature TLdeserialize = TLRPC$TL_disabledFeature.TLdeserialize(serializedData, serializedData.readInt32(false), false);
                        if (!(TLdeserialize == null || TLdeserialize.feature == null || TLdeserialize.description == null)) {
                            this.disabledFeatures.add(TLdeserialize);
                        }
                    }
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    private void applyDialogNotificationsSettings(long j, PeerNotifySettings peerNotifySettings) {
        int i = 1;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        int i2 = sharedPreferences.getInt("notify2_" + j, 0);
        int i3 = sharedPreferences.getInt("notifyuntil_" + j, 0);
        Editor edit = sharedPreferences.edit();
        TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) this.dialogs_dict.get(Long.valueOf(j));
        if (tLRPC$TL_dialog != null) {
            tLRPC$TL_dialog.notify_settings = peerNotifySettings;
        }
        edit.putBoolean("silent_" + j, peerNotifySettings.silent);
        if (peerNotifySettings.mute_until > ConnectionsManager.getInstance().getCurrentTime()) {
            int i4;
            if (peerNotifySettings.mute_until <= ConnectionsManager.getInstance().getCurrentTime() + 31536000) {
                if (i2 == 3 && i3 == peerNotifySettings.mute_until) {
                    i4 = 0;
                } else {
                    edit.putInt("notify2_" + j, 3);
                    edit.putInt("notifyuntil_" + j, peerNotifySettings.mute_until);
                    if (tLRPC$TL_dialog != null) {
                        tLRPC$TL_dialog.notify_settings.mute_until = 0;
                    }
                    i4 = 1;
                }
                i = i4;
                i4 = peerNotifySettings.mute_until;
            } else if (i2 != 2) {
                edit.putInt("notify2_" + j, 2);
                if (tLRPC$TL_dialog != null) {
                    tLRPC$TL_dialog.notify_settings.mute_until = Integer.MAX_VALUE;
                    i4 = 0;
                } else {
                    i4 = 0;
                }
            } else {
                i4 = 0;
                i = 0;
            }
            MessagesStorage.getInstance().setDialogFlags(j, (((long) i4) << 32) | 1);
            NotificationsController.getInstance().removeNotificationsForDialog(j);
        } else {
            if (i2 == 0 || i2 == 1) {
                i = 0;
            } else {
                if (tLRPC$TL_dialog != null) {
                    tLRPC$TL_dialog.notify_settings.mute_until = 0;
                }
                edit.remove("notify2_" + j);
            }
            MessagesStorage.getInstance().setDialogFlags(j, 0);
        }
        edit.commit();
        if (i != 0) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.notificationsSettingsUpdated, new Object[0]);
        }
    }

    private void applyDialogsNotificationsSettings(ArrayList<TLRPC$TL_dialog> arrayList) {
        Editor editor = null;
        for (int i = 0; i < arrayList.size(); i++) {
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) arrayList.get(i);
            if (tLRPC$TL_dialog.peer != null && (tLRPC$TL_dialog.notify_settings instanceof TLRPC$TL_peerNotifySettings)) {
                if (editor == null) {
                    editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                }
                int i2 = tLRPC$TL_dialog.peer.user_id != 0 ? tLRPC$TL_dialog.peer.user_id : tLRPC$TL_dialog.peer.chat_id != 0 ? -tLRPC$TL_dialog.peer.chat_id : -tLRPC$TL_dialog.peer.channel_id;
                editor.putBoolean("silent_" + i2, tLRPC$TL_dialog.notify_settings.silent);
                if (tLRPC$TL_dialog.notify_settings.mute_until == 0) {
                    editor.remove("notify2_" + i2);
                } else if (tLRPC$TL_dialog.notify_settings.mute_until > ConnectionsManager.getInstance().getCurrentTime() + 31536000) {
                    editor.putInt("notify2_" + i2, 2);
                    tLRPC$TL_dialog.notify_settings.mute_until = Integer.MAX_VALUE;
                } else {
                    editor.putInt("notify2_" + i2, 3);
                    editor.putInt("notifyuntil_" + i2, tLRPC$TL_dialog.notify_settings.mute_until);
                }
            }
        }
        if (editor != null) {
            editor.commit();
        }
    }

    public static boolean checkCanOpenChat(Bundle bundle, BaseFragment baseFragment) {
        return checkCanOpenChat(bundle, baseFragment, null);
    }

    public static boolean checkCanOpenChat(final Bundle bundle, final BaseFragment baseFragment, MessageObject messageObject) {
        String str = null;
        if (bundle == null || baseFragment == null) {
            return true;
        }
        User user;
        Chat chat;
        int i = bundle.getInt("user_id", 0);
        int i2 = bundle.getInt("chat_id", 0);
        int i3 = bundle.getInt("message_id", 0);
        if (i != 0) {
            user = getInstance().getUser(Integer.valueOf(i));
            chat = null;
        } else if (i2 != 0) {
            chat = getInstance().getChat(Integer.valueOf(i2));
            user = null;
        } else {
            chat = null;
            user = null;
        }
        if (user == null && chat == null) {
            return true;
        }
        if (chat != null) {
            str = getRestrictionReason(chat.restriction_reason);
        } else if (user != null) {
            str = getRestrictionReason(user.restriction_reason);
        }
        if (str != null) {
            showCantOpenAlert(baseFragment, str);
            return false;
        } else if (i3 == 0 || messageObject == null || chat == null || chat.access_hash != 0) {
            return true;
        } else {
            int dialogId = (int) messageObject.getDialogId();
            if (dialogId == 0) {
                return true;
            }
            TLObject tLRPC$TL_messages_getMessages;
            final Dialog alertDialog = new AlertDialog(baseFragment.getParentActivity(), 1);
            alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            Chat chat2 = dialogId < 0 ? getInstance().getChat(Integer.valueOf(-dialogId)) : chat;
            if (dialogId > 0 || !ChatObject.isChannel(chat2)) {
                tLRPC$TL_messages_getMessages = new TLRPC$TL_messages_getMessages();
                tLRPC$TL_messages_getMessages.id.add(Integer.valueOf(messageObject.getId()));
            } else {
                chat = getInstance().getChat(Integer.valueOf(-dialogId));
                tLRPC$TL_messages_getMessages = new TL_channels_getMessages();
                tLRPC$TL_messages_getMessages.channel = getInputChannel(chat);
                tLRPC$TL_messages_getMessages.id.add(Integer.valueOf(messageObject.getId()));
            }
            final int sendRequest = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getMessages, new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLObject != null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                try {
                                    alertDialog.dismiss();
                                } catch (Throwable e) {
                                    FileLog.m13728e(e);
                                }
                                TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                                MessagesController.getInstance().putUsers(tLRPC$messages_Messages.users, false);
                                MessagesController.getInstance().putChats(tLRPC$messages_Messages.chats, false);
                                MessagesStorage.getInstance().putUsersAndChats(tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, true, true);
                                baseFragment.presentFragment(new ChatActivity(bundle), true);
                            }
                        });
                    }
                }
            });
            alertDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ConnectionsManager.getInstance().cancelRequest(sendRequest, true);
                    try {
                        dialogInterface.dismiss();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                    if (baseFragment != null) {
                        baseFragment.setVisibleDialog(null);
                    }
                }
            });
            baseFragment.setVisibleDialog(alertDialog);
            alertDialog.show();
            return false;
        }
    }

    private void checkChannelError(String str, int i) {
        int i2 = -1;
        switch (str.hashCode()) {
            case -1809401834:
                if (str.equals("USER_BANNED_IN_CHANNEL")) {
                    i2 = 2;
                    break;
                }
                break;
            case -795226617:
                if (str.equals("CHANNEL_PRIVATE")) {
                    i2 = 0;
                    break;
                }
                break;
            case -471086771:
                if (str.equals("CHANNEL_PUBLIC_GROUP_NA")) {
                    i2 = 1;
                    break;
                }
                break;
        }
        switch (i2) {
            case 0:
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoCantLoad, Integer.valueOf(i), Integer.valueOf(0));
                return;
            case 1:
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoCantLoad, Integer.valueOf(i), Integer.valueOf(1));
                return;
            case 2:
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoCantLoad, Integer.valueOf(i), Integer.valueOf(2));
                return;
            default:
                return;
        }
    }

    private boolean checkDeletingTask(boolean z) {
        int currentTime = ConnectionsManager.getInstance().getCurrentTime();
        if (this.currentDeletingTaskMids == null) {
            return false;
        }
        if (!z && (this.currentDeletingTaskTime == 0 || this.currentDeletingTaskTime > currentTime)) {
            return false;
        }
        this.currentDeletingTaskTime = 0;
        if (!(this.currentDeleteTaskRunnable == null || z)) {
            Utilities.stageQueue.cancelRunnable(this.currentDeleteTaskRunnable);
        }
        this.currentDeleteTaskRunnable = null;
        final ArrayList arrayList = new ArrayList(this.currentDeletingTaskMids);
        AndroidUtilities.runOnUIThread(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesController$25$1 */
            class C32071 implements Runnable {
                C32071() {
                }

                public void run() {
                    MessagesController.this.getNewDeleteTask(arrayList, MessagesController.this.currentDeletingTaskChannelId);
                    MessagesController.this.currentDeletingTaskTime = 0;
                    MessagesController.this.currentDeletingTaskMids = null;
                }
            }

            public void run() {
                if (arrayList.isEmpty() || ((Integer) arrayList.get(0)).intValue() <= 0) {
                    MessagesController.this.deleteMessages(arrayList, null, null, 0, false);
                } else {
                    MessagesStorage.getInstance().emptyMessagesMedia(arrayList);
                }
                Utilities.stageQueue.postRunnable(new C32071());
            }
        });
        return true;
    }

    private void deleteDialog(final long j, boolean z, int i, int i2) {
        int i3 = (int) j;
        int i4 = (int) (j >> 32);
        if (i == 2) {
            MessagesStorage.getInstance().deleteDialog(j, i);
            return;
        }
        if (i == 0 || i == 3) {
            AndroidUtilities.uninstallShortcut(j);
        }
        if (z) {
            MessagesStorage.getInstance().deleteDialog(j, i);
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) this.dialogs_dict.get(Long.valueOf(j));
            if (tLRPC$TL_dialog != null) {
                if (i2 == 0) {
                    i2 = Math.max(0, tLRPC$TL_dialog.top_message);
                }
                if (i == 0 || i == 3) {
                    this.dialogs.remove(tLRPC$TL_dialog);
                    if (this.dialogsServerOnly.remove(tLRPC$TL_dialog) && DialogObject.isChannel(tLRPC$TL_dialog)) {
                        Utilities.stageQueue.postRunnable(new Runnable() {
                            public void run() {
                                MessagesController.this.channelsPts.remove(Integer.valueOf(-((int) j)));
                                MessagesController.this.shortPollChannels.delete(-((int) j));
                                MessagesController.this.needShortPollChannels.delete(-((int) j));
                            }
                        });
                    }
                    this.dialogsUsers.remove(tLRPC$TL_dialog);
                    this.dialogsGroups.remove(tLRPC$TL_dialog);
                    this.dialogsGroupsAll.remove(tLRPC$TL_dialog);
                    this.dialogsChannels.remove(tLRPC$TL_dialog);
                    this.dialogsMegaGroups.remove(tLRPC$TL_dialog);
                    this.dialogsBots.remove(tLRPC$TL_dialog);
                    this.dialogsFavs.remove(tLRPC$TL_dialog);
                    this.dialogsHidden.remove(tLRPC$TL_dialog);
                    this.dialogsAll.remove(tLRPC$TL_dialog);
                    this.dialogsUnread.remove(tLRPC$TL_dialog);
                    this.dialogsAds.remove(tLRPC$TL_dialog);
                    this.dialogsGroupsOnly.remove(tLRPC$TL_dialog);
                    this.dialogs_dict.remove(Long.valueOf(j));
                    this.dialogs_read_inbox_max.remove(Long.valueOf(j));
                    this.dialogs_read_outbox_max.remove(Long.valueOf(j));
                    this.nextDialogsCacheOffset--;
                } else {
                    tLRPC$TL_dialog.unread_count = 0;
                }
                MessageObject messageObject = (MessageObject) this.dialogMessage.remove(Long.valueOf(tLRPC$TL_dialog.id));
                int id;
                if (messageObject != null) {
                    id = messageObject.getId();
                    this.dialogMessagesByIds.remove(Integer.valueOf(messageObject.getId()));
                } else {
                    id = tLRPC$TL_dialog.top_message;
                    messageObject = (MessageObject) this.dialogMessagesByIds.remove(Integer.valueOf(tLRPC$TL_dialog.top_message));
                }
                if (!(messageObject == null || messageObject.messageOwner.random_id == 0)) {
                    this.dialogMessagesByRandomIds.remove(Long.valueOf(messageObject.messageOwner.random_id));
                }
                if (i != 1 || i3 == 0 || r2 <= 0) {
                    tLRPC$TL_dialog.top_message = 0;
                } else {
                    Message tLRPC$TL_messageService = new TLRPC$TL_messageService();
                    tLRPC$TL_messageService.id = tLRPC$TL_dialog.top_message;
                    tLRPC$TL_messageService.out = ((long) UserConfig.getClientUserId()) == j;
                    tLRPC$TL_messageService.from_id = UserConfig.getClientUserId();
                    tLRPC$TL_messageService.flags |= 256;
                    tLRPC$TL_messageService.action = new TLRPC$TL_messageActionHistoryClear();
                    tLRPC$TL_messageService.date = tLRPC$TL_dialog.last_message_date;
                    if (i3 > 0) {
                        tLRPC$TL_messageService.to_id = new TLRPC$TL_peerUser();
                        tLRPC$TL_messageService.to_id.user_id = i3;
                    } else if (ChatObject.isChannel(getChat(Integer.valueOf(-i3)))) {
                        tLRPC$TL_messageService.to_id = new TLRPC$TL_peerChannel();
                        tLRPC$TL_messageService.to_id.channel_id = -i3;
                    } else {
                        tLRPC$TL_messageService.to_id = new TLRPC$TL_peerChat();
                        tLRPC$TL_messageService.to_id.chat_id = -i3;
                    }
                    MessageObject messageObject2 = new MessageObject(tLRPC$TL_messageService, null, this.createdDialogIds.contains(Long.valueOf(tLRPC$TL_messageService.dialog_id)));
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(messageObject2);
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(tLRPC$TL_messageService);
                    updateInterfaceWithMessages(j, arrayList);
                    MessagesStorage.getInstance().putMessages(arrayList2, false, true, false, 0);
                }
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.removeAllMessagesFromDialog, Long.valueOf(j), Boolean.valueOf(false));
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.MessagesController$43$1 */
                class C32161 implements Runnable {
                    C32161() {
                    }

                    public void run() {
                        NotificationsController.getInstance().removeNotificationsForDialog(j);
                    }
                }

                public void run() {
                    AndroidUtilities.runOnUIThread(new C32161());
                }
            });
        }
        final int i5 = i2;
        if (i4 != 1 && i != 3) {
            if (i3 != 0) {
                InputPeer inputPeer = getInputPeer(i3);
                if (inputPeer == null) {
                    return;
                }
                if (!(inputPeer instanceof TLRPC$TL_inputPeerChannel)) {
                    TLObject tLRPC$TL_messages_deleteHistory = new TLRPC$TL_messages_deleteHistory();
                    tLRPC$TL_messages_deleteHistory.peer = inputPeer;
                    tLRPC$TL_messages_deleteHistory.max_id = i == 0 ? Integer.MAX_VALUE : i5;
                    tLRPC$TL_messages_deleteHistory.just_clear = i != 0;
                    final long j2 = j;
                    final int i6 = i;
                    ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_deleteHistory, new RequestDelegate() {
                        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            if (tLRPC$TL_error == null) {
                                TLRPC$TL_messages_affectedHistory tLRPC$TL_messages_affectedHistory = (TLRPC$TL_messages_affectedHistory) tLObject;
                                if (tLRPC$TL_messages_affectedHistory.offset > 0) {
                                    MessagesController.this.deleteDialog(j2, false, i6, i5);
                                }
                                MessagesController.this.processNewDifferenceParams(-1, tLRPC$TL_messages_affectedHistory.pts, -1, tLRPC$TL_messages_affectedHistory.pts_count);
                            }
                        }
                    }, 64);
                } else if (i != 0) {
                    TLObject tL_channels_deleteHistory = new TL_channels_deleteHistory();
                    tL_channels_deleteHistory.channel = new TLRPC$TL_inputChannel();
                    tL_channels_deleteHistory.channel.channel_id = inputPeer.channel_id;
                    tL_channels_deleteHistory.channel.access_hash = inputPeer.access_hash;
                    if (i5 <= 0) {
                        i5 = Integer.MAX_VALUE;
                    }
                    tL_channels_deleteHistory.max_id = i5;
                    ConnectionsManager.getInstance().sendRequest(tL_channels_deleteHistory, new RequestDelegate() {
                        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        }
                    }, 64);
                }
            } else if (i == 1) {
                SecretChatHelper.getInstance().sendClearHistoryMessage(getEncryptedChat(Integer.valueOf(i4)), null);
            } else {
                SecretChatHelper.getInstance().declineSecretChat(i4);
            }
        }
    }

    private void getChannelDifference(int i) {
        getChannelDifference(i, 0, 0, null);
    }

    public static InputChannel getInputChannel(int i) {
        return getInputChannel(getInstance().getChat(Integer.valueOf(i)));
    }

    public static InputChannel getInputChannel(Chat chat) {
        if (!(chat instanceof TL_channel) && !(chat instanceof TL_channelForbidden)) {
            return new TLRPC$TL_inputChannelEmpty();
        }
        InputChannel tLRPC$TL_inputChannel = new TLRPC$TL_inputChannel();
        tLRPC$TL_inputChannel.channel_id = chat.id;
        tLRPC$TL_inputChannel.access_hash = chat.access_hash;
        return tLRPC$TL_inputChannel;
    }

    public static InputPeer getInputPeer(int i) {
        InputPeer tLRPC$TL_inputPeerChannel;
        if (i < 0) {
            Chat chat = getInstance().getChat(Integer.valueOf(-i));
            if (ChatObject.isChannel(chat)) {
                tLRPC$TL_inputPeerChannel = new TLRPC$TL_inputPeerChannel();
                tLRPC$TL_inputPeerChannel.channel_id = -i;
                tLRPC$TL_inputPeerChannel.access_hash = chat.access_hash;
                return tLRPC$TL_inputPeerChannel;
            }
            tLRPC$TL_inputPeerChannel = new TLRPC$TL_inputPeerChat();
            tLRPC$TL_inputPeerChannel.chat_id = -i;
            return tLRPC$TL_inputPeerChannel;
        }
        User user = getInstance().getUser(Integer.valueOf(i));
        tLRPC$TL_inputPeerChannel = new TLRPC$TL_inputPeerUser();
        tLRPC$TL_inputPeerChannel.user_id = i;
        if (user == null) {
            return tLRPC$TL_inputPeerChannel;
        }
        tLRPC$TL_inputPeerChannel.access_hash = user.access_hash;
        return tLRPC$TL_inputPeerChannel;
    }

    public static InputUser getInputUser(int i) {
        return getInputUser(getInstance().getUser(Integer.valueOf(i)));
    }

    public static InputUser getInputUser(User user) {
        if (user == null) {
            return new TLRPC$TL_inputUserEmpty();
        }
        if (user.id == UserConfig.getClientUserId()) {
            return new TLRPC$TL_inputUserSelf();
        }
        InputUser tLRPC$TL_inputUser = new TLRPC$TL_inputUser();
        tLRPC$TL_inputUser.user_id = user.id;
        tLRPC$TL_inputUser.access_hash = user.access_hash;
        return tLRPC$TL_inputUser;
    }

    public static MessagesController getInstance() {
        MessagesController messagesController = Instance;
        if (messagesController == null) {
            synchronized (MessagesController.class) {
                messagesController = Instance;
                if (messagesController == null) {
                    messagesController = new MessagesController();
                    Instance = messagesController;
                }
            }
        }
        return messagesController;
    }

    public static Peer getPeer(int i) {
        Peer tLRPC$TL_peerChannel;
        if (i < 0) {
            Chat chat = getInstance().getChat(Integer.valueOf(-i));
            if ((chat instanceof TL_channel) || (chat instanceof TL_channelForbidden)) {
                tLRPC$TL_peerChannel = new TLRPC$TL_peerChannel();
                tLRPC$TL_peerChannel.channel_id = -i;
                return tLRPC$TL_peerChannel;
            }
            tLRPC$TL_peerChannel = new TLRPC$TL_peerChat();
            tLRPC$TL_peerChannel.chat_id = -i;
            return tLRPC$TL_peerChannel;
        }
        getInstance().getUser(Integer.valueOf(i));
        tLRPC$TL_peerChannel = new TLRPC$TL_peerUser();
        tLRPC$TL_peerChannel.user_id = i;
        return tLRPC$TL_peerChannel;
    }

    private static String getRestrictionReason(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        int indexOf = str.indexOf(": ");
        if (indexOf <= 0) {
            return null;
        }
        String substring = str.substring(0, indexOf);
        return (substring.contains("-all") || substring.contains("-android")) ? str.substring(indexOf + 2) : null;
    }

    private int getUpdateChannelId(TLRPC$Update tLRPC$Update) {
        return tLRPC$Update instanceof TLRPC$TL_updateNewChannelMessage ? ((TLRPC$TL_updateNewChannelMessage) tLRPC$Update).message.to_id.channel_id : tLRPC$Update instanceof TLRPC$TL_updateEditChannelMessage ? ((TLRPC$TL_updateEditChannelMessage) tLRPC$Update).message.to_id.channel_id : tLRPC$Update.channel_id;
    }

    private int getUpdateSeq(TLRPC$Updates tLRPC$Updates) {
        return tLRPC$Updates instanceof TLRPC$TL_updatesCombined ? tLRPC$Updates.seq_start : tLRPC$Updates.seq;
    }

    private int getUpdateType(TLRPC$Update tLRPC$Update) {
        return ((tLRPC$Update instanceof TLRPC$TL_updateNewMessage) || (tLRPC$Update instanceof TLRPC$TL_updateReadMessagesContents) || (tLRPC$Update instanceof TLRPC$TL_updateReadHistoryInbox) || (tLRPC$Update instanceof TLRPC$TL_updateReadHistoryOutbox) || (tLRPC$Update instanceof TLRPC$TL_updateDeleteMessages) || (tLRPC$Update instanceof TLRPC$TL_updateWebPage) || (tLRPC$Update instanceof TLRPC$TL_updateEditMessage)) ? 0 : tLRPC$Update instanceof TLRPC$TL_updateNewEncryptedMessage ? 1 : ((tLRPC$Update instanceof TLRPC$TL_updateNewChannelMessage) || (tLRPC$Update instanceof TLRPC$TL_updateDeleteChannelMessages) || (tLRPC$Update instanceof TLRPC$TL_updateEditChannelMessage) || (tLRPC$Update instanceof TLRPC$TL_updateChannelWebPage)) ? 2 : 3;
    }

    private String getUserNameForTyping(User user) {
        return user == null ? TtmlNode.ANONYMOUS_REGION_ID : (user.first_name == null || user.first_name.length() <= 0) ? (user.last_name == null || user.last_name.length() <= 0) ? TtmlNode.ANONYMOUS_REGION_ID : user.last_name : user.first_name;
    }

    public static boolean isFeatureEnabled(String str, BaseFragment baseFragment) {
        if (str == null || str.length() == 0 || getInstance().disabledFeatures.isEmpty() || baseFragment == null) {
            return true;
        }
        Iterator it = getInstance().disabledFeatures.iterator();
        while (it.hasNext()) {
            TLRPC$TL_disabledFeature tLRPC$TL_disabledFeature = (TLRPC$TL_disabledFeature) it.next();
            if (tLRPC$TL_disabledFeature.feature.equals(str)) {
                if (baseFragment.getParentActivity() != null) {
                    Builder builder = new Builder(baseFragment.getParentActivity());
                    builder.setTitle("Oops!");
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                    builder.setMessage(tLRPC$TL_disabledFeature.description);
                    baseFragment.showDialog(builder.create());
                }
                return false;
            }
        }
        return true;
    }

    private boolean isNotifySettingsMuted(PeerNotifySettings peerNotifySettings) {
        return (peerNotifySettings instanceof TLRPC$TL_peerNotifySettings) && peerNotifySettings.mute_until > ConnectionsManager.getInstance().getCurrentTime();
    }

    public static boolean isSupportId(int i) {
        return i / 1000 == 777 || i == 333000 || i == 4240000 || i == 4240000 || i == 4244000 || i == 4245000 || i == 4246000 || i == 410000 || i == 420000 || i == 431000 || i == 431415000 || i == 434000 || i == 4243000 || i == 439000 || i == 449000 || i == 450000 || i == 452000 || i == 454000 || i == 4254000 || i == 455000 || i == 460000 || i == 470000 || i == 479000 || i == 796000 || i == 482000 || i == 490000 || i == 496000 || i == 497000 || i == 498000 || i == 4298000;
    }

    private int isValidUpdate(TLRPC$Updates tLRPC$Updates, int i) {
        if (i != 0) {
            return i == 1 ? tLRPC$Updates.pts <= MessagesStorage.lastPtsValue ? 2 : MessagesStorage.lastPtsValue + tLRPC$Updates.pts_count == tLRPC$Updates.pts ? 0 : 1 : i == 2 ? tLRPC$Updates.pts <= MessagesStorage.lastQtsValue ? 2 : MessagesStorage.lastQtsValue + tLRPC$Updates.updates.size() == tLRPC$Updates.pts ? 0 : 1 : 0;
        } else {
            int updateSeq = getUpdateSeq(tLRPC$Updates);
            return (MessagesStorage.lastSeqValue + 1 == updateSeq || MessagesStorage.lastSeqValue == updateSeq) ? 0 : MessagesStorage.lastSeqValue >= updateSeq ? 2 : 1;
        }
    }

    public static void loadChannelInfoByUsername(String str, final C2497d c2497d) {
        if (str != null) {
            TLObject tLRPC$TL_contacts_resolveUsername = new TLRPC$TL_contacts_resolveUsername();
            tLRPC$TL_contacts_resolveUsername.username = str;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_resolveUsername, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLRPC$TL_error == null) {
                                TLRPC$TL_contacts_resolvedPeer tLRPC$TL_contacts_resolvedPeer = (TLRPC$TL_contacts_resolvedPeer) tLObject;
                                MessagesController.getInstance().putUsers(tLRPC$TL_contacts_resolvedPeer.users, false);
                                MessagesController.getInstance().putChats(tLRPC$TL_contacts_resolvedPeer.chats, false);
                                Log.d("LEE", "username:" + tLRPC$TL_contacts_resolvedPeer.chats.size());
                                MessagesStorage.getInstance().putUsersAndChats(tLRPC$TL_contacts_resolvedPeer.users, tLRPC$TL_contacts_resolvedPeer.chats, false, true);
                                if (c2497d != null) {
                                    c2497d.onResult(tLRPC$TL_contacts_resolvedPeer.chats, 0);
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    private void migrateDialogs(final int i, int i2, int i3, int i4, int i5, long j) {
        if (!this.migratingDialogs && i != -1) {
            this.migratingDialogs = true;
            TLObject tLRPC$TL_messages_getDialogs = new TLRPC$TL_messages_getDialogs();
            tLRPC$TL_messages_getDialogs.exclude_pinned = true;
            tLRPC$TL_messages_getDialogs.limit = 100;
            tLRPC$TL_messages_getDialogs.offset_id = i;
            tLRPC$TL_messages_getDialogs.offset_date = i2;
            FileLog.m13726e("start migrate with id " + i + " date " + LocaleController.getInstance().formatterStats.format(((long) i2) * 1000));
            if (i == 0) {
                tLRPC$TL_messages_getDialogs.offset_peer = new TLRPC$TL_inputPeerEmpty();
            } else {
                if (i5 != 0) {
                    tLRPC$TL_messages_getDialogs.offset_peer = new TLRPC$TL_inputPeerChannel();
                    tLRPC$TL_messages_getDialogs.offset_peer.channel_id = i5;
                } else if (i3 != 0) {
                    tLRPC$TL_messages_getDialogs.offset_peer = new TLRPC$TL_inputPeerUser();
                    tLRPC$TL_messages_getDialogs.offset_peer.user_id = i3;
                } else {
                    tLRPC$TL_messages_getDialogs.offset_peer = new TLRPC$TL_inputPeerChat();
                    tLRPC$TL_messages_getDialogs.offset_peer.chat_id = i4;
                }
                tLRPC$TL_messages_getDialogs.offset_peer.access_hash = j;
            }
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getDialogs, new RequestDelegate() {

                /* renamed from: org.telegram.messenger.MessagesController$66$2 */
                class C32312 implements Runnable {
                    C32312() {
                    }

                    public void run() {
                        MessagesController.this.migratingDialogs = false;
                    }
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        final TLRPC$messages_Dialogs tLRPC$messages_Dialogs = (TLRPC$messages_Dialogs) tLObject;
                        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                            /* renamed from: org.telegram.messenger.MessagesController$66$1$1 */
                            class C32291 implements Runnable {
                                C32291() {
                                }

                                public void run() {
                                    MessagesController.this.migratingDialogs = false;
                                }
                            }

                            public void run() {
                                try {
                                    Message message;
                                    int i;
                                    TLRPC$TL_dialog tLRPC$TL_dialog;
                                    int i2;
                                    UserConfig.totalDialogsLoadCount += tLRPC$messages_Dialogs.dialogs.size();
                                    Message message2 = null;
                                    int i3 = 0;
                                    while (i3 < tLRPC$messages_Dialogs.messages.size()) {
                                        message = (Message) tLRPC$messages_Dialogs.messages.get(i3);
                                        FileLog.m13726e("search migrate id " + message.id + " date " + LocaleController.getInstance().formatterStats.format(((long) message.date) * 1000));
                                        if (message2 != null && message.date >= message2.date) {
                                            message = message2;
                                        }
                                        i3++;
                                        message2 = message;
                                    }
                                    FileLog.m13726e("migrate step with id " + message2.id + " date " + LocaleController.getInstance().formatterStats.format(((long) message2.date) * 1000));
                                    if (tLRPC$messages_Dialogs.dialogs.size() >= 100) {
                                        i = message2.id;
                                    } else {
                                        FileLog.m13726e("migrate stop due to not 100 dialogs");
                                        UserConfig.dialogsLoadOffsetId = Integer.MAX_VALUE;
                                        UserConfig.dialogsLoadOffsetDate = UserConfig.migrateOffsetDate;
                                        UserConfig.dialogsLoadOffsetUserId = UserConfig.migrateOffsetUserId;
                                        UserConfig.dialogsLoadOffsetChatId = UserConfig.migrateOffsetChatId;
                                        UserConfig.dialogsLoadOffsetChannelId = UserConfig.migrateOffsetChannelId;
                                        UserConfig.dialogsLoadOffsetAccess = UserConfig.migrateOffsetAccess;
                                        i = -1;
                                    }
                                    StringBuilder stringBuilder = new StringBuilder(tLRPC$messages_Dialogs.dialogs.size() * 12);
                                    HashMap hashMap = new HashMap();
                                    for (i3 = 0; i3 < tLRPC$messages_Dialogs.dialogs.size(); i3++) {
                                        tLRPC$TL_dialog = (TLRPC$TL_dialog) tLRPC$messages_Dialogs.dialogs.get(i3);
                                        if (tLRPC$TL_dialog.peer.channel_id != 0) {
                                            tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.channel_id);
                                        } else if (tLRPC$TL_dialog.peer.chat_id != 0) {
                                            tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.chat_id);
                                        } else {
                                            tLRPC$TL_dialog.id = (long) tLRPC$TL_dialog.peer.user_id;
                                        }
                                        if (stringBuilder.length() > 0) {
                                            stringBuilder.append(",");
                                        }
                                        stringBuilder.append(tLRPC$TL_dialog.id);
                                        hashMap.put(Long.valueOf(tLRPC$TL_dialog.id), tLRPC$TL_dialog);
                                    }
                                    SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT did FROM dialogs WHERE did IN (%s)", new Object[]{stringBuilder.toString()}), new Object[0]);
                                    while (b.m12152a()) {
                                        long d = b.m12158d(0);
                                        tLRPC$TL_dialog = (TLRPC$TL_dialog) hashMap.remove(Long.valueOf(d));
                                        if (tLRPC$TL_dialog != null) {
                                            tLRPC$messages_Dialogs.dialogs.remove(tLRPC$TL_dialog);
                                            i2 = 0;
                                            while (i2 < tLRPC$messages_Dialogs.messages.size()) {
                                                Message message3 = (Message) tLRPC$messages_Dialogs.messages.get(i2);
                                                if (MessageObject.getDialogId(message3) != d) {
                                                    i3 = i2;
                                                } else {
                                                    tLRPC$messages_Dialogs.messages.remove(i2);
                                                    i2--;
                                                    if (message3.id == tLRPC$TL_dialog.top_message) {
                                                        tLRPC$TL_dialog.top_message = 0;
                                                        break;
                                                    }
                                                    i3 = i2;
                                                }
                                                i2 = i3 + 1;
                                            }
                                        }
                                    }
                                    b.m12155b();
                                    FileLog.m13726e("migrate found missing dialogs " + tLRPC$messages_Dialogs.dialogs.size());
                                    b = MessagesStorage.getInstance().getDatabase().m12165b("SELECT min(date) FROM dialogs WHERE date != 0 AND did >> 32 IN (0, -1)", new Object[0]);
                                    if (b.m12152a()) {
                                        int max = Math.max(1441062000, b.m12154b(0));
                                        i3 = 0;
                                        i2 = i;
                                        while (i3 < tLRPC$messages_Dialogs.messages.size()) {
                                            message = (Message) tLRPC$messages_Dialogs.messages.get(i3);
                                            if (message.date < max) {
                                                if (i != -1) {
                                                    UserConfig.dialogsLoadOffsetId = UserConfig.migrateOffsetId;
                                                    UserConfig.dialogsLoadOffsetDate = UserConfig.migrateOffsetDate;
                                                    UserConfig.dialogsLoadOffsetUserId = UserConfig.migrateOffsetUserId;
                                                    UserConfig.dialogsLoadOffsetChatId = UserConfig.migrateOffsetChatId;
                                                    UserConfig.dialogsLoadOffsetChannelId = UserConfig.migrateOffsetChannelId;
                                                    UserConfig.dialogsLoadOffsetAccess = UserConfig.migrateOffsetAccess;
                                                    i2 = -1;
                                                    FileLog.m13726e("migrate stop due to reached loaded dialogs " + LocaleController.getInstance().formatterStats.format(((long) max) * 1000));
                                                }
                                                tLRPC$messages_Dialogs.messages.remove(i3);
                                                i3--;
                                                tLRPC$TL_dialog = (TLRPC$TL_dialog) hashMap.remove(Long.valueOf(MessageObject.getDialogId(message)));
                                                if (tLRPC$TL_dialog != null) {
                                                    tLRPC$messages_Dialogs.dialogs.remove(tLRPC$TL_dialog);
                                                }
                                            }
                                            i2 = i2;
                                            i3++;
                                        }
                                        if (!(message2 == null || message2.date >= max || i == -1)) {
                                            UserConfig.dialogsLoadOffsetId = UserConfig.migrateOffsetId;
                                            UserConfig.dialogsLoadOffsetDate = UserConfig.migrateOffsetDate;
                                            UserConfig.dialogsLoadOffsetUserId = UserConfig.migrateOffsetUserId;
                                            UserConfig.dialogsLoadOffsetChatId = UserConfig.migrateOffsetChatId;
                                            UserConfig.dialogsLoadOffsetChannelId = UserConfig.migrateOffsetChannelId;
                                            UserConfig.dialogsLoadOffsetAccess = UserConfig.migrateOffsetAccess;
                                            i2 = -1;
                                            FileLog.m13726e("migrate stop due to reached loaded dialogs " + LocaleController.getInstance().formatterStats.format(((long) max) * 1000));
                                        }
                                    } else {
                                        i2 = i;
                                    }
                                    b.m12155b();
                                    UserConfig.migrateOffsetDate = message2.date;
                                    Chat chat;
                                    if (message2.to_id.channel_id != 0) {
                                        UserConfig.migrateOffsetChannelId = message2.to_id.channel_id;
                                        UserConfig.migrateOffsetChatId = 0;
                                        UserConfig.migrateOffsetUserId = 0;
                                        for (i3 = 0; i3 < tLRPC$messages_Dialogs.chats.size(); i3++) {
                                            chat = (Chat) tLRPC$messages_Dialogs.chats.get(i3);
                                            if (chat.id == UserConfig.migrateOffsetChannelId) {
                                                UserConfig.migrateOffsetAccess = chat.access_hash;
                                                break;
                                            }
                                        }
                                    } else if (message2.to_id.chat_id != 0) {
                                        UserConfig.migrateOffsetChatId = message2.to_id.chat_id;
                                        UserConfig.migrateOffsetChannelId = 0;
                                        UserConfig.migrateOffsetUserId = 0;
                                        for (i3 = 0; i3 < tLRPC$messages_Dialogs.chats.size(); i3++) {
                                            chat = (Chat) tLRPC$messages_Dialogs.chats.get(i3);
                                            if (chat.id == UserConfig.migrateOffsetChatId) {
                                                UserConfig.migrateOffsetAccess = chat.access_hash;
                                                break;
                                            }
                                        }
                                    } else if (message2.to_id.user_id != 0) {
                                        UserConfig.migrateOffsetUserId = message2.to_id.user_id;
                                        UserConfig.migrateOffsetChatId = 0;
                                        UserConfig.migrateOffsetChannelId = 0;
                                        for (i3 = 0; i3 < tLRPC$messages_Dialogs.users.size(); i3++) {
                                            User user = (User) tLRPC$messages_Dialogs.users.get(i3);
                                            if (user.id == UserConfig.migrateOffsetUserId) {
                                                UserConfig.migrateOffsetAccess = user.access_hash;
                                                break;
                                            }
                                        }
                                    }
                                    MessagesController.this.processLoadedDialogs(tLRPC$messages_Dialogs, null, i2, 0, 0, false, true, false);
                                } catch (Throwable e) {
                                    FileLog.m13728e(e);
                                    AndroidUtilities.runOnUIThread(new C32291());
                                }
                            }
                        });
                        return;
                    }
                    AndroidUtilities.runOnUIThread(new C32312());
                }
            });
        }
    }

    public static void openByUserName(String str, final BaseFragment baseFragment, final int i) {
        if (str != null && baseFragment != null) {
            Chat chat;
            User user;
            TLObject userOrChat = getInstance().getUserOrChat(str);
            if (userOrChat instanceof User) {
                User user2 = (User) userOrChat;
                if (user2.min) {
                    chat = null;
                    user = null;
                } else {
                    user = user2;
                    chat = null;
                }
            } else if (userOrChat instanceof Chat) {
                chat = (Chat) userOrChat;
                if (chat.min) {
                    chat = null;
                    user = null;
                } else {
                    user = null;
                }
            } else {
                chat = null;
                user = null;
            }
            if (user != null) {
                openChatOrProfileWith(user, null, baseFragment, i, false);
            } else if (chat != null) {
                openChatOrProfileWith(null, chat, baseFragment, 1, false);
            } else if (baseFragment.getParentActivity() != null) {
                final AlertDialog[] alertDialogArr = new AlertDialog[]{new AlertDialog(baseFragment.getParentActivity(), 1)};
                TLObject tLRPC$TL_contacts_resolveUsername = new TLRPC$TL_contacts_resolveUsername();
                tLRPC$TL_contacts_resolveUsername.username = str;
                final int sendRequest = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_resolveUsername, new RequestDelegate() {
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                try {
                                    alertDialogArr[0].dismiss();
                                } catch (Exception e) {
                                }
                                alertDialogArr[0] = null;
                                baseFragment.setVisibleDialog(null);
                                if (tLRPC$TL_error == null) {
                                    TLRPC$TL_contacts_resolvedPeer tLRPC$TL_contacts_resolvedPeer = (TLRPC$TL_contacts_resolvedPeer) tLObject;
                                    MessagesController.getInstance().putUsers(tLRPC$TL_contacts_resolvedPeer.users, false);
                                    MessagesController.getInstance().putChats(tLRPC$TL_contacts_resolvedPeer.chats, false);
                                    MessagesStorage.getInstance().putUsersAndChats(tLRPC$TL_contacts_resolvedPeer.users, tLRPC$TL_contacts_resolvedPeer.chats, false, true);
                                    if (!tLRPC$TL_contacts_resolvedPeer.chats.isEmpty()) {
                                        MessagesController.openChatOrProfileWith(null, (Chat) tLRPC$TL_contacts_resolvedPeer.chats.get(0), baseFragment, 1, false);
                                    } else if (!tLRPC$TL_contacts_resolvedPeer.users.isEmpty()) {
                                        MessagesController.openChatOrProfileWith((User) tLRPC$TL_contacts_resolvedPeer.users.get(0), null, baseFragment, i, false);
                                    }
                                } else if (baseFragment != null && baseFragment.getParentActivity() != null) {
                                    try {
                                        Toast.makeText(baseFragment.getParentActivity(), LocaleController.getString("NoUsernameFound", R.string.NoUsernameFound), 0).show();
                                    } catch (Throwable e2) {
                                        FileLog.m13728e(e2);
                                    }
                                }
                            }
                        });
                    }
                });
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.messenger.MessagesController$136$1 */
                    class C31971 implements OnClickListener {
                        C31971() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            ConnectionsManager.getInstance().cancelRequest(sendRequest, true);
                            try {
                                dialogInterface.dismiss();
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                    }

                    public void run() {
                        if (alertDialogArr[0] != null) {
                            alertDialogArr[0].setMessage(LocaleController.getString("Loading", R.string.Loading));
                            alertDialogArr[0].setCanceledOnTouchOutside(false);
                            alertDialogArr[0].setCancelable(false);
                            alertDialogArr[0].setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C31971());
                            baseFragment.showDialog(alertDialogArr[0]);
                        }
                    }
                }, 500);
            }
        }
    }

    public static void openChatOrProfileWith(User user, Chat chat, BaseFragment baseFragment, int i, boolean z) {
        if ((user != null || chat != null) && baseFragment != null) {
            String str = null;
            if (chat != null) {
                str = getRestrictionReason(chat.restriction_reason);
            } else if (user != null) {
                str = getRestrictionReason(user.restriction_reason);
                if (user.bot) {
                    z = true;
                    i = 1;
                }
            }
            if (str != null) {
                showCantOpenAlert(baseFragment, str);
                return;
            }
            Bundle bundle = new Bundle();
            if (chat != null) {
                bundle.putInt("chat_id", chat.id);
            } else {
                bundle.putInt("user_id", user.id);
            }
            if (i == 0) {
                baseFragment.presentFragment(new ProfileActivity(bundle));
            } else if (i == 2) {
                baseFragment.presentFragment(new ChatActivity(bundle), true, true);
            } else {
                baseFragment.presentFragment(new ChatActivity(bundle), z);
            }
        }
    }

    private void processChannelsUpdatesQueue(int i, int i2) {
        ArrayList arrayList = (ArrayList) this.updatesQueueChannels.get(Integer.valueOf(i));
        if (arrayList != null) {
            Integer num = (Integer) this.channelsPts.get(Integer.valueOf(i));
            if (arrayList.isEmpty() || num == null) {
                this.updatesQueueChannels.remove(Integer.valueOf(i));
                return;
            }
            Collections.sort(arrayList, new Comparator<TLRPC$Updates>() {
                public int compare(TLRPC$Updates tLRPC$Updates, TLRPC$Updates tLRPC$Updates2) {
                    return AndroidUtilities.compare(tLRPC$Updates.pts, tLRPC$Updates2.pts);
                }
            });
            if (i2 == 2) {
                this.channelsPts.put(Integer.valueOf(i), Integer.valueOf(((TLRPC$Updates) arrayList.get(0)).pts));
            }
            boolean z = false;
            while (arrayList.size() > 0) {
                int i3;
                boolean z2;
                TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) arrayList.get(0);
                if (tLRPC$Updates.pts <= num.intValue()) {
                    i3 = 2;
                } else if (num.intValue() + tLRPC$Updates.pts_count == tLRPC$Updates.pts) {
                    i3 = 0;
                } else {
                    boolean z3 = true;
                }
                if (i3 == 0) {
                    processUpdates(tLRPC$Updates, true);
                    arrayList.remove(0);
                    z2 = true;
                } else if (i3 == 1) {
                    Long l = (Long) this.updatesStartWaitTimeChannels.get(Integer.valueOf(i));
                    if (l == null || (!z && Math.abs(System.currentTimeMillis() - l.longValue()) > 1500)) {
                        FileLog.m13726e("HOLE IN CHANNEL " + i + " UPDATES QUEUE - getChannelDifference ");
                        this.updatesStartWaitTimeChannels.remove(Integer.valueOf(i));
                        this.updatesQueueChannels.remove(Integer.valueOf(i));
                        getChannelDifference(i);
                        return;
                    }
                    FileLog.m13726e("HOLE IN CHANNEL " + i + " UPDATES QUEUE - will wait more time");
                    if (z) {
                        this.updatesStartWaitTimeChannels.put(Integer.valueOf(i), Long.valueOf(System.currentTimeMillis()));
                        return;
                    }
                    return;
                } else {
                    arrayList.remove(0);
                    z2 = z;
                }
                z = z2;
            }
            this.updatesQueueChannels.remove(Integer.valueOf(i));
            this.updatesStartWaitTimeChannels.remove(Integer.valueOf(i));
            FileLog.m13726e("UPDATES CHANNEL " + i + " QUEUE PROCEED - OK");
        }
    }

    private void processUpdatesQueue(int i, int i2) {
        ArrayList arrayList;
        List list;
        if (i == 0) {
            list = this.updatesQueueSeq;
            Collections.sort(list, new Comparator<TLRPC$Updates>() {
                public int compare(TLRPC$Updates tLRPC$Updates, TLRPC$Updates tLRPC$Updates2) {
                    return AndroidUtilities.compare(MessagesController.this.getUpdateSeq(tLRPC$Updates), MessagesController.this.getUpdateSeq(tLRPC$Updates2));
                }
            });
            arrayList = list;
        } else if (i == 1) {
            list = this.updatesQueuePts;
            Collections.sort(list, new Comparator<TLRPC$Updates>() {
                public int compare(TLRPC$Updates tLRPC$Updates, TLRPC$Updates tLRPC$Updates2) {
                    return AndroidUtilities.compare(tLRPC$Updates.pts, tLRPC$Updates2.pts);
                }
            });
            r4 = list;
        } else if (i == 2) {
            list = this.updatesQueueQts;
            Collections.sort(list, new Comparator<TLRPC$Updates>() {
                public int compare(TLRPC$Updates tLRPC$Updates, TLRPC$Updates tLRPC$Updates2) {
                    return AndroidUtilities.compare(tLRPC$Updates.pts, tLRPC$Updates2.pts);
                }
            });
            r4 = list;
        } else {
            arrayList = null;
        }
        if (!(arrayList == null || arrayList.isEmpty())) {
            TLRPC$Updates tLRPC$Updates;
            if (i2 == 2) {
                tLRPC$Updates = (TLRPC$Updates) arrayList.get(0);
                if (i == 0) {
                    MessagesStorage.lastSeqValue = getUpdateSeq(tLRPC$Updates);
                } else if (i == 1) {
                    MessagesStorage.lastPtsValue = tLRPC$Updates.pts;
                } else {
                    MessagesStorage.lastQtsValue = tLRPC$Updates.pts;
                }
            }
            boolean z = false;
            while (arrayList.size() > 0) {
                boolean z2;
                tLRPC$Updates = (TLRPC$Updates) arrayList.get(0);
                int isValidUpdate = isValidUpdate(tLRPC$Updates, i);
                if (isValidUpdate == 0) {
                    processUpdates(tLRPC$Updates, true);
                    arrayList.remove(0);
                    z2 = true;
                } else if (isValidUpdate != 1) {
                    arrayList.remove(0);
                    z2 = z;
                } else if (getUpdatesStartTime(i) == 0 || (!z && Math.abs(System.currentTimeMillis() - getUpdatesStartTime(i)) > 1500)) {
                    FileLog.m13726e("HOLE IN UPDATES QUEUE - getDifference");
                    setUpdatesStartTime(i, 0);
                    arrayList.clear();
                    getDifference();
                    return;
                } else {
                    FileLog.m13726e("HOLE IN UPDATES QUEUE - will wait more time");
                    if (z) {
                        setUpdatesStartTime(i, System.currentTimeMillis());
                        return;
                    }
                    return;
                }
                z = z2;
            }
            arrayList.clear();
            FileLog.m13726e("UPDATES QUEUE PROCEED - OK");
        }
        setUpdatesStartTime(i, 0);
    }

    private void reloadDialogsReadValue(ArrayList<TLRPC$TL_dialog> arrayList, long j) {
        if (j != 0 || (arrayList != null && !arrayList.isEmpty())) {
            TLObject tLRPC$TL_messages_getPeerDialogs = new TLRPC$TL_messages_getPeerDialogs();
            InputPeer inputPeer;
            if (arrayList != null) {
                for (int i = 0; i < arrayList.size(); i++) {
                    inputPeer = getInputPeer((int) ((TLRPC$TL_dialog) arrayList.get(i)).id);
                    if (!(inputPeer instanceof TLRPC$TL_inputPeerChannel) || inputPeer.access_hash != 0) {
                        tLRPC$TL_messages_getPeerDialogs.peers.add(inputPeer);
                    }
                }
            } else {
                inputPeer = getInputPeer((int) j);
                if (!(inputPeer instanceof TLRPC$TL_inputPeerChannel) || inputPeer.access_hash != 0) {
                    tLRPC$TL_messages_getPeerDialogs.peers.add(inputPeer);
                } else {
                    return;
                }
            }
            if (!tLRPC$TL_messages_getPeerDialogs.peers.isEmpty()) {
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getPeerDialogs, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLObject != null) {
                            TLRPC$TL_messages_peerDialogs tLRPC$TL_messages_peerDialogs = (TLRPC$TL_messages_peerDialogs) tLObject;
                            ArrayList arrayList = new ArrayList();
                            for (int i = 0; i < tLRPC$TL_messages_peerDialogs.dialogs.size(); i++) {
                                TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) tLRPC$TL_messages_peerDialogs.dialogs.get(i);
                                if (tLRPC$TL_dialog.read_inbox_max_id == 0) {
                                    tLRPC$TL_dialog.read_inbox_max_id = 1;
                                }
                                if (tLRPC$TL_dialog.read_outbox_max_id == 0) {
                                    tLRPC$TL_dialog.read_outbox_max_id = 1;
                                }
                                if (tLRPC$TL_dialog.id == 0 && tLRPC$TL_dialog.peer != null) {
                                    if (tLRPC$TL_dialog.peer.user_id != 0) {
                                        tLRPC$TL_dialog.id = (long) tLRPC$TL_dialog.peer.user_id;
                                    } else if (tLRPC$TL_dialog.peer.chat_id != 0) {
                                        tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.chat_id);
                                    } else if (tLRPC$TL_dialog.peer.channel_id != 0) {
                                        tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.channel_id);
                                    }
                                }
                                Integer num = (Integer) MessagesController.this.dialogs_read_inbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                                if (num == null) {
                                    num = Integer.valueOf(0);
                                }
                                MessagesController.this.dialogs_read_inbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(tLRPC$TL_dialog.read_inbox_max_id, num.intValue())));
                                if (num.intValue() == 0) {
                                    if (tLRPC$TL_dialog.peer.channel_id != 0) {
                                        TLRPC$TL_updateReadChannelInbox tLRPC$TL_updateReadChannelInbox = new TLRPC$TL_updateReadChannelInbox();
                                        tLRPC$TL_updateReadChannelInbox.channel_id = tLRPC$TL_dialog.peer.channel_id;
                                        tLRPC$TL_updateReadChannelInbox.max_id = tLRPC$TL_dialog.read_inbox_max_id;
                                        arrayList.add(tLRPC$TL_updateReadChannelInbox);
                                    } else {
                                        TLRPC$TL_updateReadHistoryInbox tLRPC$TL_updateReadHistoryInbox = new TLRPC$TL_updateReadHistoryInbox();
                                        tLRPC$TL_updateReadHistoryInbox.peer = tLRPC$TL_dialog.peer;
                                        tLRPC$TL_updateReadHistoryInbox.max_id = tLRPC$TL_dialog.read_inbox_max_id;
                                        arrayList.add(tLRPC$TL_updateReadHistoryInbox);
                                    }
                                }
                                num = (Integer) MessagesController.this.dialogs_read_outbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                                if (num == null) {
                                    num = Integer.valueOf(0);
                                }
                                MessagesController.this.dialogs_read_outbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(tLRPC$TL_dialog.read_outbox_max_id, num.intValue())));
                                if (num.intValue() == 0) {
                                    if (tLRPC$TL_dialog.peer.channel_id != 0) {
                                        TLRPC$TL_updateReadChannelOutbox tLRPC$TL_updateReadChannelOutbox = new TLRPC$TL_updateReadChannelOutbox();
                                        tLRPC$TL_updateReadChannelOutbox.channel_id = tLRPC$TL_dialog.peer.channel_id;
                                        tLRPC$TL_updateReadChannelOutbox.max_id = tLRPC$TL_dialog.read_outbox_max_id;
                                        arrayList.add(tLRPC$TL_updateReadChannelOutbox);
                                    } else {
                                        TLRPC$TL_updateReadHistoryOutbox tLRPC$TL_updateReadHistoryOutbox = new TLRPC$TL_updateReadHistoryOutbox();
                                        tLRPC$TL_updateReadHistoryOutbox.peer = tLRPC$TL_dialog.peer;
                                        tLRPC$TL_updateReadHistoryOutbox.max_id = tLRPC$TL_dialog.read_outbox_max_id;
                                        arrayList.add(tLRPC$TL_updateReadHistoryOutbox);
                                    }
                                }
                            }
                            if (!arrayList.isEmpty()) {
                                MessagesController.this.processUpdateArray(arrayList, null, null, false);
                            }
                        }
                    }
                });
            }
        }
    }

    private void reloadMessages(ArrayList<Integer> arrayList, long j) {
        if (!arrayList.isEmpty()) {
            TLObject tLObject;
            final ArrayList arrayList2 = new ArrayList();
            final Chat chatByDialog = ChatObject.getChatByDialog(j);
            TLObject tL_channels_getMessages;
            if (ChatObject.isChannel(chatByDialog)) {
                tL_channels_getMessages = new TL_channels_getMessages();
                tL_channels_getMessages.channel = getInputChannel(chatByDialog);
                tL_channels_getMessages.id = arrayList2;
                tLObject = tL_channels_getMessages;
            } else {
                tL_channels_getMessages = new TLRPC$TL_messages_getMessages();
                tL_channels_getMessages.id = arrayList2;
                tLObject = tL_channels_getMessages;
            }
            ArrayList arrayList3 = (ArrayList) this.reloadingMessages.get(Long.valueOf(j));
            for (int i = 0; i < arrayList.size(); i++) {
                Integer num = (Integer) arrayList.get(i);
                if (arrayList3 == null || !arrayList3.contains(num)) {
                    arrayList2.add(num);
                }
            }
            if (!arrayList2.isEmpty()) {
                if (arrayList3 == null) {
                    arrayList3 = new ArrayList();
                    this.reloadingMessages.put(Long.valueOf(j), arrayList3);
                }
                arrayList3.addAll(arrayList2);
                final long j2 = j;
                ConnectionsManager.getInstance().sendRequest(tLObject, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            int i;
                            TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                            AbstractMap hashMap = new HashMap();
                            for (i = 0; i < tLRPC$messages_Messages.users.size(); i++) {
                                User user = (User) tLRPC$messages_Messages.users.get(i);
                                hashMap.put(Integer.valueOf(user.id), user);
                            }
                            AbstractMap hashMap2 = new HashMap();
                            for (i = 0; i < tLRPC$messages_Messages.chats.size(); i++) {
                                Chat chat = (Chat) tLRPC$messages_Messages.chats.get(i);
                                hashMap2.put(Integer.valueOf(chat.id), chat);
                            }
                            Integer num = (Integer) MessagesController.this.dialogs_read_inbox_max.get(Long.valueOf(j2));
                            if (num == null) {
                                num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(false, j2));
                                MessagesController.this.dialogs_read_inbox_max.put(Long.valueOf(j2), num);
                            }
                            Integer num2 = num;
                            num = (Integer) MessagesController.this.dialogs_read_outbox_max.get(Long.valueOf(j2));
                            if (num == null) {
                                num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(true, j2));
                                MessagesController.this.dialogs_read_outbox_max.put(Long.valueOf(j2), num);
                            }
                            Integer num3 = num;
                            final ArrayList arrayList = new ArrayList();
                            for (int i2 = 0; i2 < tLRPC$messages_Messages.messages.size(); i2++) {
                                Message message = (Message) tLRPC$messages_Messages.messages.get(i2);
                                if (chatByDialog != null && chatByDialog.megagroup) {
                                    message.flags |= Integer.MIN_VALUE;
                                }
                                message.dialog_id = j2;
                                message.unread = (message.out ? num3 : num2).intValue() < message.id;
                                arrayList.add(new MessageObject(message, hashMap, hashMap2, true));
                            }
                            ImageLoader.saveMessagesThumbs(tLRPC$messages_Messages.messages);
                            MessagesStorage.getInstance().putMessages(tLRPC$messages_Messages, j2, -1, 0, false);
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    ArrayList arrayList = (ArrayList) MessagesController.this.reloadingMessages.get(Long.valueOf(j2));
                                    if (arrayList != null) {
                                        arrayList.removeAll(arrayList2);
                                        if (arrayList.isEmpty()) {
                                            MessagesController.this.reloadingMessages.remove(Long.valueOf(j2));
                                        }
                                    }
                                    MessageObject messageObject = (MessageObject) MessagesController.this.dialogMessage.get(Long.valueOf(j2));
                                    if (messageObject != null) {
                                        int i = 0;
                                        while (i < arrayList.size()) {
                                            MessageObject messageObject2 = (MessageObject) arrayList.get(i);
                                            if (messageObject == null || messageObject.getId() != messageObject2.getId()) {
                                                i++;
                                            } else {
                                                MessagesController.this.dialogMessage.put(Long.valueOf(j2), messageObject2);
                                                if (messageObject2.messageOwner.to_id.channel_id == 0) {
                                                    messageObject = (MessageObject) MessagesController.this.dialogMessagesByIds.remove(Integer.valueOf(messageObject2.getId()));
                                                    if (messageObject != null) {
                                                        MessagesController.this.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                                                    }
                                                }
                                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                            }
                                        }
                                    }
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.replaceMessagesObjects, Long.valueOf(j2), arrayList);
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    private void resetDialogs(boolean z, int i, int i2, int i3, int i4) {
        final int i5;
        final int i6;
        if (z) {
            if (!this.resetingDialogs) {
                this.resetingDialogs = true;
                i5 = i;
                i6 = i2;
                final int i7 = i3;
                final int i8 = i4;
                ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_messages_getPinnedDialogs(), new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLObject != null) {
                            MessagesController.this.resetDialogsPinned = (TLRPC$TL_messages_peerDialogs) tLObject;
                            MessagesController.this.resetDialogs(false, i5, i6, i7, i8);
                        }
                    }
                });
                TLObject tLRPC$TL_messages_getDialogs = new TLRPC$TL_messages_getDialogs();
                tLRPC$TL_messages_getDialogs.limit = 100;
                tLRPC$TL_messages_getDialogs.exclude_pinned = true;
                tLRPC$TL_messages_getDialogs.offset_peer = new TLRPC$TL_inputPeerEmpty();
                i5 = i;
                i6 = i2;
                i7 = i3;
                i8 = i4;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getDialogs, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            MessagesController.this.resetDialogsAll = (TLRPC$messages_Dialogs) tLObject;
                            MessagesController.this.resetDialogs(false, i5, i6, i7, i8);
                        }
                    }
                });
            }
        } else if (this.resetDialogsPinned != null && this.resetDialogsAll != null) {
            int i9;
            Chat chat;
            Integer num;
            i5 = this.resetDialogsAll.messages.size();
            int size = this.resetDialogsAll.dialogs.size();
            this.resetDialogsAll.dialogs.addAll(this.resetDialogsPinned.dialogs);
            this.resetDialogsAll.messages.addAll(this.resetDialogsPinned.messages);
            this.resetDialogsAll.users.addAll(this.resetDialogsPinned.users);
            this.resetDialogsAll.chats.addAll(this.resetDialogsPinned.chats);
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            AbstractMap hashMap3 = new HashMap();
            AbstractMap hashMap4 = new HashMap();
            for (i9 = 0; i9 < this.resetDialogsAll.users.size(); i9++) {
                User user = (User) this.resetDialogsAll.users.get(i9);
                hashMap3.put(Integer.valueOf(user.id), user);
            }
            for (i9 = 0; i9 < this.resetDialogsAll.chats.size(); i9++) {
                chat = (Chat) this.resetDialogsAll.chats.get(i9);
                hashMap4.put(Integer.valueOf(chat.id), chat);
            }
            Message message = null;
            for (i6 = 0; i6 < this.resetDialogsAll.messages.size(); i6++) {
                Message message2 = (Message) this.resetDialogsAll.messages.get(i6);
                if (i6 < i5 && (message == null || message2.date < message.date)) {
                    message = message2;
                }
                MessageObject messageObject;
                if (message2.to_id.channel_id != 0) {
                    chat = (Chat) hashMap4.get(Integer.valueOf(message2.to_id.channel_id));
                    if (chat == null || !chat.left) {
                        if (chat != null && chat.megagroup) {
                            message2.flags |= Integer.MIN_VALUE;
                        }
                        messageObject = new MessageObject(message2, hashMap3, hashMap4, false);
                        hashMap2.put(Long.valueOf(messageObject.getDialogId()), messageObject);
                    }
                } else {
                    if (message2.to_id.chat_id != 0) {
                        chat = (Chat) hashMap4.get(Integer.valueOf(message2.to_id.chat_id));
                        if (!(chat == null || chat.migrated_to == null)) {
                        }
                    }
                    messageObject = new MessageObject(message2, hashMap3, hashMap4, false);
                    hashMap2.put(Long.valueOf(messageObject.getDialogId()), messageObject);
                }
            }
            for (i6 = 0; i6 < this.resetDialogsAll.dialogs.size(); i6++) {
                TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) this.resetDialogsAll.dialogs.get(i6);
                if (tLRPC$TL_dialog.id == 0 && tLRPC$TL_dialog.peer != null) {
                    if (tLRPC$TL_dialog.peer.user_id != 0) {
                        tLRPC$TL_dialog.id = (long) tLRPC$TL_dialog.peer.user_id;
                    } else if (tLRPC$TL_dialog.peer.chat_id != 0) {
                        tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.chat_id);
                    } else if (tLRPC$TL_dialog.peer.channel_id != 0) {
                        tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.channel_id);
                    }
                }
                if (tLRPC$TL_dialog.id != 0) {
                    if (tLRPC$TL_dialog.last_message_date == 0) {
                        MessageObject messageObject2 = (MessageObject) hashMap2.get(Long.valueOf(tLRPC$TL_dialog.id));
                        if (messageObject2 != null) {
                            tLRPC$TL_dialog.last_message_date = messageObject2.messageOwner.date;
                        }
                    }
                    Chat chat2;
                    if (DialogObject.isChannel(tLRPC$TL_dialog)) {
                        chat2 = (Chat) hashMap4.get(Integer.valueOf(-((int) tLRPC$TL_dialog.id)));
                        if (chat2 == null || !chat2.left) {
                            this.channelsPts.put(Integer.valueOf(-((int) tLRPC$TL_dialog.id)), Integer.valueOf(tLRPC$TL_dialog.pts));
                        }
                    } else if (((int) tLRPC$TL_dialog.id) < 0) {
                        chat2 = (Chat) hashMap4.get(Integer.valueOf(-((int) tLRPC$TL_dialog.id)));
                        if (!(chat2 == null || chat2.migrated_to == null)) {
                        }
                    }
                    hashMap.put(Long.valueOf(tLRPC$TL_dialog.id), tLRPC$TL_dialog);
                    num = (Integer) this.dialogs_read_inbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                    if (num == null) {
                        num = Integer.valueOf(0);
                    }
                    this.dialogs_read_inbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_inbox_max_id)));
                    num = (Integer) this.dialogs_read_outbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                    if (num == null) {
                        num = Integer.valueOf(0);
                    }
                    this.dialogs_read_outbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_outbox_max_id)));
                }
            }
            ImageLoader.saveMessagesThumbs(this.resetDialogsAll.messages);
            for (i6 = 0; i6 < this.resetDialogsAll.messages.size(); i6++) {
                Message message3 = (Message) this.resetDialogsAll.messages.get(i6);
                if (message3.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                    User user2 = (User) hashMap3.get(Integer.valueOf(message3.action.user_id));
                    if (user2 != null && user2.bot) {
                        message3.reply_markup = new TLRPC$TL_replyKeyboardHide();
                        message3.flags |= 64;
                    }
                }
                if ((message3.action instanceof TLRPC$TL_messageActionChatMigrateTo) || (message3.action instanceof TLRPC$TL_messageActionChannelCreate)) {
                    message3.unread = false;
                    message3.media_unread = false;
                } else {
                    ConcurrentHashMap concurrentHashMap = message3.out ? this.dialogs_read_outbox_max : this.dialogs_read_inbox_max;
                    num = (Integer) concurrentHashMap.get(Long.valueOf(message3.dialog_id));
                    if (num == null) {
                        num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message3.out, message3.dialog_id));
                        concurrentHashMap.put(Long.valueOf(message3.dialog_id), num);
                    }
                    message3.unread = num.intValue() < message3.id;
                }
            }
            MessagesStorage.getInstance().resetDialogs(this.resetDialogsAll, i5, i, i2, i3, i4, hashMap, hashMap2, message, size);
            this.resetDialogsPinned = null;
            this.resetDialogsAll = null;
        }
    }

    private void setUpdatesStartTime(int i, long j) {
        if (i == 0) {
            this.updatesStartWaitTimeSeq = j;
        } else if (i == 1) {
            this.updatesStartWaitTimePts = j;
        } else if (i == 2) {
            this.updatesStartWaitTimeQts = j;
        }
    }

    public static void setUserAdminRole(final int i, User user, TL_channelAdminRights tL_channelAdminRights, final boolean z, final BaseFragment baseFragment) {
        if (user != null && tL_channelAdminRights != null) {
            final TLObject tL_channels_editAdmin = new TL_channels_editAdmin();
            tL_channels_editAdmin.channel = getInputChannel(i);
            tL_channels_editAdmin.user_id = getInputUser(user);
            tL_channels_editAdmin.admin_rights = tL_channelAdminRights;
            ConnectionsManager.getInstance().sendRequest(tL_channels_editAdmin, new RequestDelegate() {

                /* renamed from: org.telegram.messenger.MessagesController$31$1 */
                class C32121 implements Runnable {
                    C32121() {
                    }

                    public void run() {
                        MessagesController.getInstance().loadFullChat(i, 0, true);
                    }
                }

                public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        MessagesController.getInstance().processUpdates((TLRPC$Updates) tLObject, false);
                        AndroidUtilities.runOnUIThread(new C32121(), 1000);
                        return;
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            boolean z = true;
                            TLRPC$TL_error tLRPC$TL_error = tLRPC$TL_error;
                            BaseFragment baseFragment = baseFragment;
                            TLObject tLObject = tL_channels_editAdmin;
                            Object[] objArr = new Object[1];
                            if (z) {
                                z = false;
                            }
                            objArr[0] = Boolean.valueOf(z);
                            AlertsCreator.processError(tLRPC$TL_error, baseFragment, tLObject, objArr);
                        }
                    });
                }
            });
        }
    }

    public static void setUserBannedRole(final int i, User user, TL_channelBannedRights tL_channelBannedRights, final boolean z, final BaseFragment baseFragment) {
        if (user != null && tL_channelBannedRights != null) {
            final TLObject tL_channels_editBanned = new TL_channels_editBanned();
            tL_channels_editBanned.channel = getInputChannel(i);
            tL_channels_editBanned.user_id = getInputUser(user);
            tL_channels_editBanned.banned_rights = tL_channelBannedRights;
            ConnectionsManager.getInstance().sendRequest(tL_channels_editBanned, new RequestDelegate() {

                /* renamed from: org.telegram.messenger.MessagesController$30$1 */
                class C32101 implements Runnable {
                    C32101() {
                    }

                    public void run() {
                        MessagesController.getInstance().loadFullChat(i, 0, true);
                    }
                }

                public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        MessagesController.getInstance().processUpdates((TLRPC$Updates) tLObject, false);
                        AndroidUtilities.runOnUIThread(new C32101(), 1000);
                        return;
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            boolean z = true;
                            TLRPC$TL_error tLRPC$TL_error = tLRPC$TL_error;
                            BaseFragment baseFragment = baseFragment;
                            TLObject tLObject = tL_channels_editBanned;
                            Object[] objArr = new Object[1];
                            if (z) {
                                z = false;
                            }
                            objArr[0] = Boolean.valueOf(z);
                            AlertsCreator.processError(tLRPC$TL_error, baseFragment, tLObject, objArr);
                        }
                    });
                }
            });
        }
    }

    private static void showCantOpenAlert(BaseFragment baseFragment, String str) {
        if (baseFragment != null && baseFragment.getParentActivity() != null) {
            Builder builder = new Builder(baseFragment.getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            builder.setMessage(str);
            baseFragment.showDialog(builder.create());
        }
    }

    private void updatePrintingStrings() {
        final HashMap hashMap = new HashMap();
        final HashMap hashMap2 = new HashMap();
        ArrayList arrayList = new ArrayList(this.printingUsers.keySet());
        for (Entry entry : this.printingUsers.entrySet()) {
            long longValue = ((Long) entry.getKey()).longValue();
            arrayList = (ArrayList) entry.getValue();
            int i = (int) longValue;
            if (i > 0 || i == 0 || arrayList.size() == 1) {
                PrintingUser printingUser = (PrintingUser) arrayList.get(0);
                if (getUser(Integer.valueOf(printingUser.userId)) != null) {
                    if (printingUser.action instanceof TLRPC$TL_sendMessageRecordAudioAction) {
                        if (i < 0) {
                            hashMap.put(Long.valueOf(longValue), LocaleController.formatString("IsRecordingAudio", R.string.IsRecordingAudio, getUserNameForTyping(r2)));
                        } else {
                            hashMap.put(Long.valueOf(longValue), LocaleController.getString("RecordingAudio", R.string.RecordingAudio));
                        }
                        hashMap2.put(Long.valueOf(longValue), Integer.valueOf(1));
                    } else if ((printingUser.action instanceof TLRPC$TL_sendMessageRecordRoundAction) || (printingUser.action instanceof TLRPC$TL_sendMessageUploadRoundAction)) {
                        if (i < 0) {
                            hashMap.put(Long.valueOf(longValue), LocaleController.formatString("IsRecordingRound", R.string.IsRecordingRound, getUserNameForTyping(r2)));
                        } else {
                            hashMap.put(Long.valueOf(longValue), LocaleController.getString("RecordingRound", R.string.RecordingRound));
                        }
                        hashMap2.put(Long.valueOf(longValue), Integer.valueOf(4));
                    } else if (printingUser.action instanceof TLRPC$TL_sendMessageUploadAudioAction) {
                        if (i < 0) {
                            hashMap.put(Long.valueOf(longValue), LocaleController.formatString("IsSendingAudio", R.string.IsSendingAudio, getUserNameForTyping(r2)));
                        } else {
                            hashMap.put(Long.valueOf(longValue), LocaleController.getString("SendingAudio", R.string.SendingAudio));
                        }
                        hashMap2.put(Long.valueOf(longValue), Integer.valueOf(2));
                    } else if ((printingUser.action instanceof TLRPC$TL_sendMessageUploadVideoAction) || (printingUser.action instanceof TLRPC$TL_sendMessageRecordVideoAction)) {
                        if (i < 0) {
                            hashMap.put(Long.valueOf(longValue), LocaleController.formatString("IsSendingVideo", R.string.IsSendingVideo, getUserNameForTyping(r2)));
                        } else {
                            hashMap.put(Long.valueOf(longValue), LocaleController.getString("SendingVideoStatus", R.string.SendingVideoStatus));
                        }
                        hashMap2.put(Long.valueOf(longValue), Integer.valueOf(2));
                    } else if (printingUser.action instanceof TLRPC$TL_sendMessageUploadDocumentAction) {
                        if (i < 0) {
                            hashMap.put(Long.valueOf(longValue), LocaleController.formatString("IsSendingFile", R.string.IsSendingFile, getUserNameForTyping(r2)));
                        } else {
                            hashMap.put(Long.valueOf(longValue), LocaleController.getString("SendingFile", R.string.SendingFile));
                        }
                        hashMap2.put(Long.valueOf(longValue), Integer.valueOf(2));
                    } else if (printingUser.action instanceof TLRPC$TL_sendMessageUploadPhotoAction) {
                        if (i < 0) {
                            hashMap.put(Long.valueOf(longValue), LocaleController.formatString("IsSendingPhoto", R.string.IsSendingPhoto, getUserNameForTyping(r2)));
                        } else {
                            hashMap.put(Long.valueOf(longValue), LocaleController.getString("SendingPhoto", R.string.SendingPhoto));
                        }
                        hashMap2.put(Long.valueOf(longValue), Integer.valueOf(2));
                    } else if (printingUser.action instanceof TLRPC$TL_sendMessageGamePlayAction) {
                        if (i < 0) {
                            hashMap.put(Long.valueOf(longValue), LocaleController.formatString("IsSendingGame", R.string.IsSendingGame, getUserNameForTyping(r2)));
                        } else {
                            hashMap.put(Long.valueOf(longValue), LocaleController.getString("SendingGame", R.string.SendingGame));
                        }
                        hashMap2.put(Long.valueOf(longValue), Integer.valueOf(3));
                    } else {
                        if (i < 0) {
                            hashMap.put(Long.valueOf(longValue), LocaleController.formatString("IsTypingGroup", R.string.IsTypingGroup, getUserNameForTyping(r2)));
                        } else {
                            hashMap.put(Long.valueOf(longValue), LocaleController.getString("Typing", R.string.Typing));
                        }
                        hashMap2.put(Long.valueOf(longValue), Integer.valueOf(0));
                    }
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                Iterator it = arrayList.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    User user = getUser(Integer.valueOf(((PrintingUser) it.next()).userId));
                    if (user != null) {
                        if (stringBuilder.length() != 0) {
                            stringBuilder.append(", ");
                        }
                        stringBuilder.append(getUserNameForTyping(user));
                        i = i2 + 1;
                    } else {
                        i = i2;
                    }
                    if (i == 2) {
                        break;
                    }
                    i2 = i;
                }
                i = i2;
                if (stringBuilder.length() != 0) {
                    if (i == 1) {
                        hashMap.put(Long.valueOf(longValue), LocaleController.formatString("IsTypingGroup", R.string.IsTypingGroup, stringBuilder.toString()));
                    } else if (arrayList.size() > 2) {
                        String pluralString = LocaleController.getPluralString("AndMoreTypingGroup", arrayList.size() - 2);
                        hashMap.put(Long.valueOf(longValue), String.format(pluralString, new Object[]{stringBuilder.toString(), Integer.valueOf(arrayList.size() - 2)}));
                    } else {
                        hashMap.put(Long.valueOf(longValue), LocaleController.formatString("AreTypingGroup", R.string.AreTypingGroup, stringBuilder.toString()));
                    }
                    hashMap2.put(Long.valueOf(longValue), Integer.valueOf(0));
                }
            }
        }
        this.lastPrintingStringCount = hashMap.size();
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController.this.printingStrings = hashMap;
                MessagesController.this.printingStringsTypes = hashMap2;
            }
        });
    }

    private boolean updatePrintingUsersWithNewMessages(long j, ArrayList<MessageObject> arrayList) {
        if (j > 0) {
            if (((ArrayList) this.printingUsers.get(Long.valueOf(j))) != null) {
                this.printingUsers.remove(Long.valueOf(j));
                return true;
            }
        } else if (j < 0) {
            boolean z;
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                MessageObject messageObject = (MessageObject) it.next();
                if (!arrayList2.contains(Integer.valueOf(messageObject.messageOwner.from_id))) {
                    arrayList2.add(Integer.valueOf(messageObject.messageOwner.from_id));
                }
            }
            ArrayList arrayList3 = (ArrayList) this.printingUsers.get(Long.valueOf(j));
            if (arrayList3 != null) {
                int i = 0;
                z = false;
                while (i < arrayList3.size()) {
                    int i2;
                    boolean z2;
                    if (arrayList2.contains(Integer.valueOf(((PrintingUser) arrayList3.get(i)).userId))) {
                        arrayList3.remove(i);
                        i--;
                        if (arrayList3.isEmpty()) {
                            this.printingUsers.remove(Long.valueOf(j));
                        }
                        i2 = i;
                        z2 = true;
                    } else {
                        i2 = i;
                        z2 = z;
                    }
                    z = z2;
                    i = i2 + 1;
                }
            } else {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public void addSupportUser() {
        User tLRPC$TL_userForeign_old2 = new TLRPC$TL_userForeign_old2();
        tLRPC$TL_userForeign_old2.phone = "333";
        tLRPC$TL_userForeign_old2.id = 333000;
        tLRPC$TL_userForeign_old2.first_name = "Telegram";
        tLRPC$TL_userForeign_old2.last_name = TtmlNode.ANONYMOUS_REGION_ID;
        tLRPC$TL_userForeign_old2.status = null;
        tLRPC$TL_userForeign_old2.photo = new TLRPC$TL_userProfilePhotoEmpty();
        putUser(tLRPC$TL_userForeign_old2, true);
        tLRPC$TL_userForeign_old2 = new TLRPC$TL_userForeign_old2();
        tLRPC$TL_userForeign_old2.phone = "42777";
        tLRPC$TL_userForeign_old2.id = 777000;
        tLRPC$TL_userForeign_old2.first_name = "Telegram";
        tLRPC$TL_userForeign_old2.last_name = "Notifications";
        tLRPC$TL_userForeign_old2.status = null;
        tLRPC$TL_userForeign_old2.photo = new TLRPC$TL_userProfilePhotoEmpty();
        putUser(tLRPC$TL_userForeign_old2, true);
    }

    public void addToViewsQueue(final Message message) {
        Utilities.stageQueue.postRunnable(new Runnable() {
            public void run() {
                int i = message.to_id.channel_id != 0 ? -message.to_id.channel_id : message.to_id.chat_id != 0 ? -message.to_id.chat_id : message.to_id.user_id;
                ArrayList arrayList = (ArrayList) MessagesController.this.channelViewsToSend.get(i);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    MessagesController.this.channelViewsToSend.put(i, arrayList);
                }
                if (!arrayList.contains(Integer.valueOf(message.id))) {
                    arrayList.add(Integer.valueOf(message.id));
                }
            }
        });
    }

    public void addUserToChat(int i, User user, ChatFull chatFull, int i2, String str, BaseFragment baseFragment) {
        if (user != null) {
            if (i > 0) {
                TLObject tLRPC$TL_messages_startBot;
                final boolean isChannel = ChatObject.isChannel(i);
                final boolean z = isChannel && getChat(Integer.valueOf(i)).megagroup;
                final InputUser inputUser = getInputUser(user);
                if (str != null && (!isChannel || z)) {
                    tLRPC$TL_messages_startBot = new TLRPC$TL_messages_startBot();
                    tLRPC$TL_messages_startBot.bot = inputUser;
                    if (isChannel) {
                        tLRPC$TL_messages_startBot.peer = getInputPeer(-i);
                    } else {
                        tLRPC$TL_messages_startBot.peer = new TLRPC$TL_inputPeerChat();
                        tLRPC$TL_messages_startBot.peer.chat_id = i;
                    }
                    tLRPC$TL_messages_startBot.start_param = str;
                    tLRPC$TL_messages_startBot.random_id = Utilities.random.nextLong();
                } else if (!isChannel) {
                    tLRPC$TL_messages_startBot = new TLRPC$TL_messages_addChatUser();
                    tLRPC$TL_messages_startBot.chat_id = i;
                    tLRPC$TL_messages_startBot.fwd_limit = i2;
                    tLRPC$TL_messages_startBot.user_id = inputUser;
                } else if (!(inputUser instanceof TLRPC$TL_inputUserSelf)) {
                    tLRPC$TL_messages_startBot = new TL_channels_inviteToChannel();
                    tLRPC$TL_messages_startBot.channel = getInputChannel(i);
                    tLRPC$TL_messages_startBot.users.add(inputUser);
                } else if (!this.joiningToChannels.contains(Integer.valueOf(i))) {
                    tLRPC$TL_messages_startBot = new TL_channels_joinChannel();
                    tLRPC$TL_messages_startBot.channel = getInputChannel(i);
                    this.joiningToChannels.add(Integer.valueOf(i));
                } else {
                    return;
                }
                final int i3 = i;
                final BaseFragment baseFragment2 = baseFragment;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_startBot, new RequestDelegate() {

                    /* renamed from: org.telegram.messenger.MessagesController$96$1 */
                    class C32551 implements Runnable {
                        C32551() {
                        }

                        public void run() {
                            MessagesController.this.joiningToChannels.remove(Integer.valueOf(i3));
                        }
                    }

                    /* renamed from: org.telegram.messenger.MessagesController$96$3 */
                    class C32573 implements Runnable {
                        C32573() {
                        }

                        public void run() {
                            MessagesController.this.loadFullChat(i3, 0, true);
                        }
                    }

                    public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        if (isChannel && (inputUser instanceof TLRPC$TL_inputUserSelf)) {
                            AndroidUtilities.runOnUIThread(new C32551());
                        }
                        if (tLRPC$TL_error != null) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    boolean z = true;
                                    TLRPC$TL_error tLRPC$TL_error = tLRPC$TL_error;
                                    BaseFragment baseFragment = baseFragment2;
                                    TLObject tLObject = tLRPC$TL_messages_startBot;
                                    Object[] objArr = new Object[1];
                                    if (!isChannel || z) {
                                        z = false;
                                    }
                                    objArr[0] = Boolean.valueOf(z);
                                    AlertsCreator.processError(tLRPC$TL_error, baseFragment, tLObject, objArr);
                                }
                            });
                            return;
                        }
                        boolean z;
                        TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                        for (int i = 0; i < tLRPC$Updates.updates.size(); i++) {
                            TLRPC$Update tLRPC$Update = (TLRPC$Update) tLRPC$Updates.updates.get(i);
                            if ((tLRPC$Update instanceof TLRPC$TL_updateNewChannelMessage) && (((TLRPC$TL_updateNewChannelMessage) tLRPC$Update).message.action instanceof TLRPC$TL_messageActionChatAddUser)) {
                                z = true;
                                break;
                            }
                        }
                        z = false;
                        MessagesController.this.processUpdates(tLRPC$Updates, false);
                        if (isChannel) {
                            if (!z && (inputUser instanceof TLRPC$TL_inputUserSelf)) {
                                MessagesController.this.generateJoinMessage(i3, true);
                            }
                            AndroidUtilities.runOnUIThread(new C32573(), 1000);
                        }
                        if (isChannel && (inputUser instanceof TLRPC$TL_inputUserSelf)) {
                            MessagesStorage.getInstance().updateDialogsWithDeletedMessages(new ArrayList(), null, true, i3);
                        }
                    }
                });
            } else if (chatFull instanceof TL_chatFull) {
                int i4 = 0;
                while (i4 < chatFull.participants.participants.size()) {
                    if (((ChatParticipant) chatFull.participants.participants.get(i4)).user_id != user.id) {
                        i4++;
                    } else {
                        return;
                    }
                }
                Chat chat = getChat(Integer.valueOf(i));
                chat.participants_count++;
                ArrayList arrayList = new ArrayList();
                arrayList.add(chat);
                MessagesStorage.getInstance().putUsersAndChats(null, arrayList, true, true);
                TL_chatParticipant tL_chatParticipant = new TL_chatParticipant();
                tL_chatParticipant.user_id = user.id;
                tL_chatParticipant.inviter_id = UserConfig.getClientUserId();
                tL_chatParticipant.date = ConnectionsManager.getInstance().getCurrentTime();
                chatFull.participants.participants.add(0, tL_chatParticipant);
                MessagesStorage.getInstance().updateChatInfo(chatFull, true);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, chatFull, Integer.valueOf(0), Boolean.valueOf(false), null);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(32));
            }
            if (C3791b.ab(ApplicationLoader.applicationContext)) {
                Object obj;
                ArrayList arrayList2 = new ArrayList();
                Iterator it = C3791b.m13921Y(ApplicationLoader.applicationContext).iterator();
                while (it.hasNext()) {
                    Category category = (Category) it.next();
                    if (category.getChannelId() == i) {
                        category.setStatus(1);
                        arrayList2.add(category);
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj != null) {
                    C2818c.m13087a(ApplicationLoader.applicationContext, new C2497d() {
                        public void onResult(Object obj, int i) {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, Integer.valueOf(-1));
                        }
                    }).m13117a(arrayList2);
                }
            }
        }
    }

    public void addUsersToChannel(int i, ArrayList<InputUser> arrayList, final BaseFragment baseFragment) {
        if (arrayList != null && !arrayList.isEmpty()) {
            final TLObject tL_channels_inviteToChannel = new TL_channels_inviteToChannel();
            tL_channels_inviteToChannel.channel = getInputChannel(i);
            tL_channels_inviteToChannel.users = arrayList;
            ConnectionsManager.getInstance().sendRequest(tL_channels_inviteToChannel, new RequestDelegate() {
                public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error != null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                AlertsCreator.processError(tLRPC$TL_error, baseFragment, tL_channels_inviteToChannel, new Object[]{Boolean.valueOf(true)});
                            }
                        });
                    } else {
                        MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                    }
                }
            });
        }
    }

    public void blockUser(int i) {
        final User user = getUser(Integer.valueOf(i));
        if (user != null && !this.blockedUsers.contains(Integer.valueOf(i))) {
            this.blockedUsers.add(Integer.valueOf(i));
            if (user.bot) {
                SearchQuery.removeInline(i);
            } else {
                SearchQuery.removePeer(i);
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.blockedUsersDidLoaded, new Object[0]);
            TLObject tLRPC$TL_contacts_block = new TLRPC$TL_contacts_block();
            tLRPC$TL_contacts_block.id = getInputUser(user);
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_block, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(Integer.valueOf(user.id));
                        MessagesStorage.getInstance().putBlockedUsers(arrayList, false);
                    }
                }
            });
        }
    }

    public boolean canPinDialog(boolean z) {
        int i = 0;
        for (int i2 = 0; i2 < this.dialogs.size(); i2++) {
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) this.dialogs.get(i2);
            int i3 = (int) tLRPC$TL_dialog.id;
            if ((!z || i3 == 0) && ((z || i3 != 0) && tLRPC$TL_dialog.pinned)) {
                i++;
            }
        }
        return i < this.maxPinnedDialogsCount;
    }

    public void cancelLoadFullChat(int i) {
        this.loadingFullChats.remove(Integer.valueOf(i));
    }

    public void cancelLoadFullUser(int i) {
        this.loadingFullUsers.remove(Integer.valueOf(i));
    }

    public void cancelTyping(int i, long j) {
        HashMap hashMap = (HashMap) this.sendingTypings.get(Integer.valueOf(i));
        if (hashMap != null) {
            hashMap.remove(Long.valueOf(j));
        }
    }

    public void changeChatAvatar(int i, InputFile inputFile) {
        TLObject tL_channels_editPhoto;
        if (ChatObject.isChannel(i)) {
            tL_channels_editPhoto = new TL_channels_editPhoto();
            tL_channels_editPhoto.channel = getInputChannel(i);
            if (inputFile != null) {
                tL_channels_editPhoto.photo = new TLRPC$TL_inputChatUploadedPhoto();
                tL_channels_editPhoto.photo.file = inputFile;
            } else {
                tL_channels_editPhoto.photo = new TLRPC$TL_inputChatPhotoEmpty();
            }
        } else {
            tL_channels_editPhoto = new TLRPC$TL_messages_editChatPhoto();
            tL_channels_editPhoto.chat_id = i;
            if (inputFile != null) {
                tL_channels_editPhoto.photo = new TLRPC$TL_inputChatUploadedPhoto();
                tL_channels_editPhoto.photo.file = inputFile;
            } else {
                tL_channels_editPhoto.photo = new TLRPC$TL_inputChatPhotoEmpty();
            }
        }
        ConnectionsManager.getInstance().sendRequest(tL_channels_editPhoto, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error == null) {
                    MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                }
            }
        }, 64);
    }

    public void changeChatTitle(int i, String str) {
        if (i > 0) {
            TLObject tL_channels_editTitle;
            if (ChatObject.isChannel(i)) {
                tL_channels_editTitle = new TL_channels_editTitle();
                tL_channels_editTitle.channel = getInputChannel(i);
                tL_channels_editTitle.title = str;
            } else {
                tL_channels_editTitle = new TLRPC$TL_messages_editChatTitle();
                tL_channels_editTitle.chat_id = i;
                tL_channels_editTitle.title = str;
            }
            ConnectionsManager.getInstance().sendRequest(tL_channels_editTitle, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                    }
                }
            }, 64);
            return;
        }
        Chat chat = getChat(Integer.valueOf(i));
        chat.title = str;
        ArrayList arrayList = new ArrayList();
        arrayList.add(chat);
        MessagesStorage.getInstance().putUsersAndChats(null, arrayList, true, true);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(16));
    }

    public void checkChannelInviter(final int i) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                final Chat chat = MessagesController.this.getChat(Integer.valueOf(i));
                if (chat != null && ChatObject.isChannel(i) && !chat.creator) {
                    TLObject tL_channels_getParticipant = new TL_channels_getParticipant();
                    tL_channels_getParticipant.channel = MessagesController.getInputChannel(i);
                    tL_channels_getParticipant.user_id = new TLRPC$TL_inputUserSelf();
                    ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipant, new RequestDelegate() {
                        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            final TL_channels_channelParticipant tL_channels_channelParticipant = (TL_channels_channelParticipant) tLObject;
                            if (tL_channels_channelParticipant != null && (tL_channels_channelParticipant.participant instanceof TL_channelParticipantSelf) && tL_channels_channelParticipant.participant.inviter_id != UserConfig.getClientUserId()) {
                                if (!chat.megagroup || !MessagesStorage.getInstance().isMigratedChat(chat.id)) {
                                    AndroidUtilities.runOnUIThread(new Runnable() {
                                        public void run() {
                                            MessagesController.this.putUsers(tL_channels_channelParticipant.users, false);
                                        }
                                    });
                                    MessagesStorage.getInstance().putUsersAndChats(tL_channels_channelParticipant.users, null, true, true);
                                    Message tLRPC$TL_messageService = new TLRPC$TL_messageService();
                                    tLRPC$TL_messageService.media_unread = true;
                                    tLRPC$TL_messageService.unread = true;
                                    tLRPC$TL_messageService.flags = 256;
                                    tLRPC$TL_messageService.post = true;
                                    if (chat.megagroup) {
                                        tLRPC$TL_messageService.flags |= Integer.MIN_VALUE;
                                    }
                                    int newMessageId = UserConfig.getNewMessageId();
                                    tLRPC$TL_messageService.id = newMessageId;
                                    tLRPC$TL_messageService.local_id = newMessageId;
                                    tLRPC$TL_messageService.date = tL_channels_channelParticipant.participant.date;
                                    tLRPC$TL_messageService.action = new TLRPC$TL_messageActionChatAddUser();
                                    tLRPC$TL_messageService.from_id = tL_channels_channelParticipant.participant.inviter_id;
                                    tLRPC$TL_messageService.action.users.add(Integer.valueOf(UserConfig.getClientUserId()));
                                    tLRPC$TL_messageService.to_id = new TLRPC$TL_peerChannel();
                                    tLRPC$TL_messageService.to_id.channel_id = i;
                                    tLRPC$TL_messageService.dialog_id = (long) (-i);
                                    UserConfig.saveConfig(false);
                                    final ArrayList arrayList = new ArrayList();
                                    ArrayList arrayList2 = new ArrayList();
                                    AbstractMap concurrentHashMap = new ConcurrentHashMap();
                                    for (int i = 0; i < tL_channels_channelParticipant.users.size(); i++) {
                                        User user = (User) tL_channels_channelParticipant.users.get(i);
                                        concurrentHashMap.put(Integer.valueOf(user.id), user);
                                    }
                                    arrayList2.add(tLRPC$TL_messageService);
                                    arrayList.add(new MessageObject(tLRPC$TL_messageService, concurrentHashMap, true));
                                    MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                        /* renamed from: org.telegram.messenger.MessagesController$118$1$2$1 */
                                        class C31841 implements Runnable {
                                            C31841() {
                                            }

                                            public void run() {
                                                NotificationsController.getInstance().processNewMessages(arrayList, true);
                                            }
                                        }

                                        public void run() {
                                            AndroidUtilities.runOnUIThread(new C31841());
                                        }
                                    });
                                    MessagesStorage.getInstance().putMessages(arrayList2, true, true, false, 0);
                                    AndroidUtilities.runOnUIThread(new Runnable() {
                                        public void run() {
                                            MessagesController.this.updateInterfaceWithMessages((long) (-i), arrayList);
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    protected void checkLastDialogMessage(TLRPC$TL_dialog tLRPC$TL_dialog, InputPeer inputPeer, long j) {
        NativeByteBuffer nativeByteBuffer;
        Throwable e;
        long createPendingTask;
        final TLRPC$TL_dialog tLRPC$TL_dialog2;
        final int i = (int) tLRPC$TL_dialog.id;
        if (i != 0 && !this.checkingLastMessagesDialogs.containsKey(Integer.valueOf(i))) {
            TLObject tLRPC$TL_messages_getHistory = new TLRPC$TL_messages_getHistory();
            tLRPC$TL_messages_getHistory.peer = inputPeer == null ? getInputPeer(i) : inputPeer;
            if (tLRPC$TL_messages_getHistory.peer != null && !(tLRPC$TL_messages_getHistory.peer instanceof TLRPC$TL_inputPeerChannel)) {
                tLRPC$TL_messages_getHistory.limit = 1;
                this.checkingLastMessagesDialogs.put(Integer.valueOf(i), Boolean.valueOf(true));
                if (j == 0) {
                    try {
                        nativeByteBuffer = new NativeByteBuffer(tLRPC$TL_messages_getHistory.peer.getObjectSize() + 48);
                        try {
                            nativeByteBuffer.writeInt32(8);
                            nativeByteBuffer.writeInt64(tLRPC$TL_dialog.id);
                            nativeByteBuffer.writeInt32(tLRPC$TL_dialog.top_message);
                            nativeByteBuffer.writeInt32(tLRPC$TL_dialog.read_inbox_max_id);
                            nativeByteBuffer.writeInt32(tLRPC$TL_dialog.read_outbox_max_id);
                            nativeByteBuffer.writeInt32(tLRPC$TL_dialog.unread_count);
                            nativeByteBuffer.writeInt32(tLRPC$TL_dialog.last_message_date);
                            nativeByteBuffer.writeInt32(tLRPC$TL_dialog.pts);
                            nativeByteBuffer.writeInt32(tLRPC$TL_dialog.flags);
                            nativeByteBuffer.writeBool(tLRPC$TL_dialog.pinned);
                            nativeByteBuffer.writeInt32(tLRPC$TL_dialog.pinnedNum);
                            nativeByteBuffer.writeInt32(tLRPC$TL_dialog.unread_mentions_count);
                            inputPeer.serializeToStream(nativeByteBuffer);
                        } catch (Exception e2) {
                            e = e2;
                            FileLog.m13728e(e);
                            createPendingTask = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                            tLRPC$TL_dialog2 = tLRPC$TL_dialog;
                            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getHistory, new RequestDelegate() {

                                /* renamed from: org.telegram.messenger.MessagesController$70$1 */
                                class C32371 implements Runnable {
                                    C32371() {
                                    }

                                    public void run() {
                                        TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.this.dialogs_dict.get(Long.valueOf(tLRPC$TL_dialog2.id));
                                        if (tLRPC$TL_dialog != null && tLRPC$TL_dialog.top_message == 0) {
                                            MessagesController.this.deleteDialog(tLRPC$TL_dialog2.id, 3);
                                        }
                                    }
                                }

                                /* renamed from: org.telegram.messenger.MessagesController$70$2 */
                                class C32382 implements Runnable {
                                    C32382() {
                                    }

                                    public void run() {
                                        MessagesController.this.checkingLastMessagesDialogs.remove(Integer.valueOf(i));
                                    }
                                }

                                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                    if (tLObject != null) {
                                        TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                                        if (tLRPC$messages_Messages.messages.isEmpty()) {
                                            AndroidUtilities.runOnUIThread(new C32371());
                                        } else {
                                            TLRPC$messages_Dialogs tLRPC$TL_messages_dialogs = new TLRPC$TL_messages_dialogs();
                                            Message message = (Message) tLRPC$messages_Messages.messages.get(0);
                                            TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
                                            tLRPC$TL_dialog.flags = tLRPC$TL_dialog2.flags;
                                            tLRPC$TL_dialog.top_message = message.id;
                                            tLRPC$TL_dialog.last_message_date = message.date;
                                            tLRPC$TL_dialog.notify_settings = tLRPC$TL_dialog2.notify_settings;
                                            tLRPC$TL_dialog.pts = tLRPC$TL_dialog2.pts;
                                            tLRPC$TL_dialog.unread_count = tLRPC$TL_dialog2.unread_count;
                                            tLRPC$TL_dialog.unread_mentions_count = tLRPC$TL_dialog2.unread_mentions_count;
                                            tLRPC$TL_dialog.read_inbox_max_id = tLRPC$TL_dialog2.read_inbox_max_id;
                                            tLRPC$TL_dialog.read_outbox_max_id = tLRPC$TL_dialog2.read_outbox_max_id;
                                            tLRPC$TL_dialog.pinned = tLRPC$TL_dialog2.pinned;
                                            tLRPC$TL_dialog.pinnedNum = tLRPC$TL_dialog2.pinnedNum;
                                            long j = tLRPC$TL_dialog2.id;
                                            tLRPC$TL_dialog.id = j;
                                            message.dialog_id = j;
                                            tLRPC$TL_messages_dialogs.users.addAll(tLRPC$messages_Messages.users);
                                            tLRPC$TL_messages_dialogs.chats.addAll(tLRPC$messages_Messages.chats);
                                            tLRPC$TL_messages_dialogs.dialogs.add(tLRPC$TL_dialog);
                                            tLRPC$TL_messages_dialogs.messages.addAll(tLRPC$messages_Messages.messages);
                                            tLRPC$TL_messages_dialogs.count = 1;
                                            MessagesController.this.processDialogsUpdate(tLRPC$TL_messages_dialogs, null);
                                            MessagesStorage.getInstance().putMessages(tLRPC$messages_Messages.messages, true, true, false, MediaController.getInstance().getAutodownloadMask(), true);
                                        }
                                    }
                                    if (createPendingTask != 0) {
                                        MessagesStorage.getInstance().removePendingTask(createPendingTask);
                                    }
                                    AndroidUtilities.runOnUIThread(new C32382());
                                }
                            });
                        }
                    } catch (Throwable e3) {
                        Throwable th = e3;
                        nativeByteBuffer = null;
                        e = th;
                        FileLog.m13728e(e);
                        createPendingTask = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                        tLRPC$TL_dialog2 = tLRPC$TL_dialog;
                        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getHistory, /* anonymous class already generated */);
                    }
                    createPendingTask = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                } else {
                    createPendingTask = j;
                }
                tLRPC$TL_dialog2 = tLRPC$TL_dialog;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getHistory, /* anonymous class already generated */);
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
        Utilities.stageQueue.postRunnable(new C32366());
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
        Utilities.stageQueue.postRunnable(new C32417());
        if (this.currentDeleteTaskRunnable != null) {
            Utilities.stageQueue.cancelRunnable(this.currentDeleteTaskRunnable);
            this.currentDeleteTaskRunnable = null;
        }
        addSupportUser();
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
    }

    protected void clearFullUsers() {
        this.loadedFullUsers.clear();
        this.loadedFullChats.clear();
    }

    protected void completeDialogsReset(TLRPC$messages_Dialogs tLRPC$messages_Dialogs, int i, int i2, int i3, int i4, int i5, HashMap<Long, TLRPC$TL_dialog> hashMap, HashMap<Long, MessageObject> hashMap2, Message message) {
        final int i6 = i3;
        final int i7 = i4;
        final int i8 = i5;
        final TLRPC$messages_Dialogs tLRPC$messages_Dialogs2 = tLRPC$messages_Dialogs;
        final HashMap<Long, TLRPC$TL_dialog> hashMap3 = hashMap;
        final HashMap<Long, MessageObject> hashMap4 = hashMap2;
        Utilities.stageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesController$65$1 */
            class C32281 implements Runnable {
                C32281() {
                }

                public void run() {
                    MessageObject messageObject;
                    MessagesController.this.resetingDialogs = false;
                    MessagesController.this.applyDialogsNotificationsSettings(tLRPC$messages_Dialogs2.dialogs);
                    if (!UserConfig.draftsLoaded) {
                        DraftQuery.loadDrafts();
                    }
                    MessagesController.this.putUsers(tLRPC$messages_Dialogs2.users, false);
                    MessagesController.this.putChats(tLRPC$messages_Dialogs2.chats, false);
                    for (int i = 0; i < MessagesController.this.dialogs.size(); i++) {
                        TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.this.dialogs.get(i);
                        if (((int) tLRPC$TL_dialog.id) != 0) {
                            MessagesController.this.dialogs_dict.remove(Long.valueOf(tLRPC$TL_dialog.id));
                            messageObject = (MessageObject) MessagesController.this.dialogMessage.remove(Long.valueOf(tLRPC$TL_dialog.id));
                            if (messageObject != null) {
                                MessagesController.this.dialogMessagesByIds.remove(Integer.valueOf(messageObject.getId()));
                                if (messageObject.messageOwner.random_id != 0) {
                                    MessagesController.this.dialogMessagesByRandomIds.remove(Long.valueOf(messageObject.messageOwner.random_id));
                                }
                            }
                        }
                    }
                    for (Entry entry : hashMap3.entrySet()) {
                        Long l = (Long) entry.getKey();
                        tLRPC$TL_dialog = (TLRPC$TL_dialog) entry.getValue();
                        if (tLRPC$TL_dialog.draft instanceof TLRPC$TL_draftMessage) {
                            DraftQuery.saveDraft(tLRPC$TL_dialog.id, tLRPC$TL_dialog.draft, null, false);
                        }
                        MessagesController.this.dialogs_dict.put(l, tLRPC$TL_dialog);
                        messageObject = (MessageObject) hashMap4.get(Long.valueOf(tLRPC$TL_dialog.id));
                        MessagesController.this.dialogMessage.put(l, messageObject);
                        if (messageObject != null && messageObject.messageOwner.to_id.channel_id == 0) {
                            MessagesController.this.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                            if (messageObject.messageOwner.random_id != 0) {
                                MessagesController.this.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                            }
                        }
                    }
                    MessagesController.this.dialogs.clear();
                    MessagesController.this.dialogs.addAll(MessagesController.this.dialogs_dict.values());
                    MessagesController.this.sortDialogs(null);
                    MessagesController.this.dialogsEndReached = true;
                    MessagesController.this.serverDialogsEndReached = false;
                    if (!(UserConfig.totalDialogsLoadCount >= ChatActivity.scheduleDownloads || UserConfig.dialogsLoadOffsetId == -1 || UserConfig.dialogsLoadOffsetId == Integer.MAX_VALUE)) {
                        MessagesController.this.loadDialogs(0, 100, false);
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                }
            }

            public void run() {
                MessagesController.this.gettingDifference = false;
                MessagesStorage.lastPtsValue = i6;
                MessagesStorage.lastDateValue = i7;
                MessagesStorage.lastQtsValue = i8;
                MessagesController.this.getDifference();
                AndroidUtilities.runOnUIThread(new C32281());
            }
        });
    }

    public void convertToMegaGroup(final Context context, int i) {
        TLObject tLRPC$TL_messages_migrateChat = new TLRPC$TL_messages_migrateChat();
        tLRPC$TL_messages_migrateChat.chat_id = i;
        final AlertDialog alertDialog = new AlertDialog(context, 1);
        alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        final int sendRequest = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_migrateChat, new RequestDelegate() {

            /* renamed from: org.telegram.messenger.MessagesController$85$1 */
            class C32471 implements Runnable {
                C32471() {
                }

                public void run() {
                    if (!((Activity) context).isFinishing()) {
                        try {
                            alertDialog.dismiss();
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                }
            }

            /* renamed from: org.telegram.messenger.MessagesController$85$2 */
            class C32482 implements Runnable {
                C32482() {
                }

                public void run() {
                    if (!((Activity) context).isFinishing()) {
                        try {
                            alertDialog.dismiss();
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                        Builder builder = new Builder(context);
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                        builder.show().setCanceledOnTouchOutside(true);
                    }
                }
            }

            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error == null) {
                    AndroidUtilities.runOnUIThread(new C32471());
                    TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                    MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                    return;
                }
                AndroidUtilities.runOnUIThread(new C32482());
            }
        });
        alertDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ConnectionsManager.getInstance().cancelRequest(sendRequest, true);
                try {
                    dialogInterface.dismiss();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
        try {
            alertDialog.show();
        } catch (Exception e) {
        }
    }

    public int createChat(String str, ArrayList<Integer> arrayList, String str2, int i, final BaseFragment baseFragment) {
        int i2;
        if (i == 1) {
            Chat tL_chat = new TL_chat();
            tL_chat.id = UserConfig.lastBroadcastId;
            tL_chat.title = str;
            tL_chat.photo = new TL_chatPhotoEmpty();
            tL_chat.participants_count = arrayList.size();
            tL_chat.date = (int) (System.currentTimeMillis() / 1000);
            tL_chat.version = 1;
            UserConfig.lastBroadcastId--;
            putChat(tL_chat, false);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(tL_chat);
            MessagesStorage.getInstance().putUsersAndChats(null, arrayList2, true, true);
            ChatFull tL_chatFull = new TL_chatFull();
            tL_chatFull.id = tL_chat.id;
            tL_chatFull.chat_photo = new TLRPC$TL_photoEmpty();
            tL_chatFull.notify_settings = new TLRPC$TL_peerNotifySettingsEmpty();
            tL_chatFull.exported_invite = new TL_chatInviteEmpty();
            tL_chatFull.participants = new TL_chatParticipants();
            tL_chatFull.participants.chat_id = tL_chat.id;
            tL_chatFull.participants.admin_id = UserConfig.getClientUserId();
            tL_chatFull.participants.version = 1;
            for (i2 = 0; i2 < arrayList.size(); i2++) {
                TL_chatParticipant tL_chatParticipant = new TL_chatParticipant();
                tL_chatParticipant.user_id = ((Integer) arrayList.get(i2)).intValue();
                tL_chatParticipant.inviter_id = UserConfig.getClientUserId();
                tL_chatParticipant.date = (int) (System.currentTimeMillis() / 1000);
                tL_chatFull.participants.participants.add(tL_chatParticipant);
            }
            MessagesStorage.getInstance().updateChatInfo(tL_chatFull, false);
            Message tLRPC$TL_messageService = new TLRPC$TL_messageService();
            tLRPC$TL_messageService.action = new TLRPC$TL_messageActionCreatedBroadcastList();
            int newMessageId = UserConfig.getNewMessageId();
            tLRPC$TL_messageService.id = newMessageId;
            tLRPC$TL_messageService.local_id = newMessageId;
            tLRPC$TL_messageService.from_id = UserConfig.getClientUserId();
            tLRPC$TL_messageService.dialog_id = AndroidUtilities.makeBroadcastId(tL_chat.id);
            tLRPC$TL_messageService.to_id = new TLRPC$TL_peerChat();
            tLRPC$TL_messageService.to_id.chat_id = tL_chat.id;
            tLRPC$TL_messageService.date = ConnectionsManager.getInstance().getCurrentTime();
            tLRPC$TL_messageService.random_id = 0;
            tLRPC$TL_messageService.flags |= 256;
            UserConfig.saveConfig(false);
            MessageObject messageObject = new MessageObject(tLRPC$TL_messageService, this.users, true);
            messageObject.messageOwner.send_state = 0;
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add(messageObject);
            ArrayList arrayList4 = new ArrayList();
            arrayList4.add(tLRPC$TL_messageService);
            MessagesStorage.getInstance().putMessages(arrayList4, false, true, false, 0);
            updateInterfaceWithMessages(tLRPC$TL_messageService.dialog_id, arrayList3);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatDidCreated, Integer.valueOf(tL_chat.id));
            return 0;
        } else if (i == 0) {
            final TLObject tLRPC$TL_messages_createChat = new TLRPC$TL_messages_createChat();
            tLRPC$TL_messages_createChat.title = str;
            for (i2 = 0; i2 < arrayList.size(); i2++) {
                User user = getUser((Integer) arrayList.get(i2));
                if (user != null) {
                    tLRPC$TL_messages_createChat.users.add(getInputUser(user));
                }
            }
            return ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_createChat, new RequestDelegate() {
                public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error != null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                AlertsCreator.processError(tLRPC$TL_error, baseFragment, tLRPC$TL_messages_createChat, new Object[0]);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatDidFailCreate, new Object[0]);
                            }
                        });
                        return;
                    }
                    final TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                    MessagesController.this.processUpdates(tLRPC$Updates, false);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            MessagesController.this.putUsers(tLRPC$Updates.users, false);
                            MessagesController.this.putChats(tLRPC$Updates.chats, false);
                            if (tLRPC$Updates.chats == null || tLRPC$Updates.chats.isEmpty()) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatDidFailCreate, new Object[0]);
                                return;
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatDidCreated, Integer.valueOf(((Chat) tLRPC$Updates.chats.get(0)).id));
                        }
                    });
                }
            }, 2);
        } else if (i != 2 && i != 4) {
            return 0;
        } else {
            final TLObject tL_channels_createChannel = new TL_channels_createChannel();
            tL_channels_createChannel.title = str;
            tL_channels_createChannel.about = str2;
            if (i == 4) {
                tL_channels_createChannel.megagroup = true;
            } else {
                tL_channels_createChannel.broadcast = true;
            }
            return ConnectionsManager.getInstance().sendRequest(tL_channels_createChannel, new RequestDelegate() {
                public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error != null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                AlertsCreator.processError(tLRPC$TL_error, baseFragment, tL_channels_createChannel, new Object[0]);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatDidFailCreate, new Object[0]);
                            }
                        });
                        return;
                    }
                    final TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                    MessagesController.this.processUpdates(tLRPC$Updates, false);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            MessagesController.this.putUsers(tLRPC$Updates.users, false);
                            MessagesController.this.putChats(tLRPC$Updates.chats, false);
                            if (tLRPC$Updates.chats == null || tLRPC$Updates.chats.isEmpty()) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatDidFailCreate, new Object[0]);
                                return;
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatDidCreated, Integer.valueOf(((Chat) tLRPC$Updates.chats.get(0)).id));
                        }
                    });
                }
            }, 2);
        }
    }

    public void deleteDialog(long j, int i) {
        deleteDialog(j, true, i, 0);
    }

    public void deleteMessages(ArrayList<Integer> arrayList, ArrayList<Long> arrayList2, EncryptedChat encryptedChat, int i, boolean z) {
        deleteMessages(arrayList, arrayList2, encryptedChat, i, z, 0, null);
    }

    public void deleteMessages(ArrayList<Integer> arrayList, ArrayList<Long> arrayList2, EncryptedChat encryptedChat, final int i, boolean z, long j, TLObject tLObject) {
        Throwable e;
        final long j2;
        if ((arrayList != null && !arrayList.isEmpty()) || tLObject != null) {
            ArrayList arrayList3;
            if (j == 0) {
                int i2;
                if (i == 0) {
                    for (i2 = 0; i2 < arrayList.size(); i2++) {
                        MessageObject messageObject = (MessageObject) this.dialogMessagesByIds.get((Integer) arrayList.get(i2));
                        if (messageObject != null) {
                            messageObject.deleted = true;
                        }
                    }
                } else {
                    markChannelDialogMessageAsDeleted(arrayList, i);
                }
                ArrayList arrayList4 = new ArrayList();
                for (i2 = 0; i2 < arrayList.size(); i2++) {
                    Integer num = (Integer) arrayList.get(i2);
                    if (num.intValue() > 0) {
                        arrayList4.add(num);
                    }
                }
                MessagesStorage.getInstance().markMessagesAsDeleted((ArrayList) arrayList, true, i);
                MessagesStorage.getInstance().updateDialogsWithDeletedMessages(arrayList, null, true, i);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesDeleted, arrayList, Integer.valueOf(i));
                arrayList3 = arrayList4;
            } else {
                arrayList3 = null;
            }
            NativeByteBuffer nativeByteBuffer;
            if (i != 0) {
                if (tLObject != null) {
                    tLObject = (TL_channels_deleteMessages) tLObject;
                } else {
                    tLObject = new TL_channels_deleteMessages();
                    tLObject.id = arrayList3;
                    tLObject.channel = getInputChannel(i);
                    try {
                        nativeByteBuffer = new NativeByteBuffer(tLObject.getObjectSize() + 8);
                        try {
                            nativeByteBuffer.writeInt32(7);
                            nativeByteBuffer.writeInt32(i);
                            tLObject.serializeToStream(nativeByteBuffer);
                        } catch (Exception e2) {
                            e = e2;
                            FileLog.m13728e(e);
                            j = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                            j2 = j;
                            ConnectionsManager.getInstance().sendRequest(tLObject, new RequestDelegate() {
                                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                    if (tLRPC$TL_error == null) {
                                        TLRPC$TL_messages_affectedMessages tLRPC$TL_messages_affectedMessages = (TLRPC$TL_messages_affectedMessages) tLObject;
                                        MessagesController.this.processNewChannelDifferenceParams(tLRPC$TL_messages_affectedMessages.pts, tLRPC$TL_messages_affectedMessages.pts_count, i);
                                    }
                                    if (j2 != 0) {
                                        MessagesStorage.getInstance().removePendingTask(j2);
                                    }
                                }
                            });
                            return;
                        }
                    } catch (Throwable e3) {
                        e = e3;
                        nativeByteBuffer = null;
                        FileLog.m13728e(e);
                        j = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                        j2 = j;
                        ConnectionsManager.getInstance().sendRequest(tLObject, /* anonymous class already generated */);
                        return;
                    }
                    j = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                }
                j2 = j;
                ConnectionsManager.getInstance().sendRequest(tLObject, /* anonymous class already generated */);
                return;
            }
            if (!(arrayList2 == null || encryptedChat == null || arrayList2.isEmpty())) {
                SecretChatHelper.getInstance().sendMessagesDeleteMessage(encryptedChat, arrayList2, null);
            }
            if (tLObject != null) {
                tLObject = (TLRPC$TL_messages_deleteMessages) tLObject;
            } else {
                tLObject = new TLRPC$TL_messages_deleteMessages();
                tLObject.id = arrayList3;
                tLObject.revoke = z;
                try {
                    nativeByteBuffer = new NativeByteBuffer(tLObject.getObjectSize() + 8);
                    try {
                        nativeByteBuffer.writeInt32(7);
                        nativeByteBuffer.writeInt32(i);
                        tLObject.serializeToStream(nativeByteBuffer);
                    } catch (Exception e4) {
                        e = e4;
                        FileLog.m13728e(e);
                        j = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                        j2 = j;
                        ConnectionsManager.getInstance().sendRequest(tLObject, new RequestDelegate() {
                            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                if (tLRPC$TL_error == null) {
                                    TLRPC$TL_messages_affectedMessages tLRPC$TL_messages_affectedMessages = (TLRPC$TL_messages_affectedMessages) tLObject;
                                    MessagesController.this.processNewDifferenceParams(-1, tLRPC$TL_messages_affectedMessages.pts, -1, tLRPC$TL_messages_affectedMessages.pts_count);
                                }
                                if (j2 != 0) {
                                    MessagesStorage.getInstance().removePendingTask(j2);
                                }
                            }
                        });
                    }
                } catch (Throwable e32) {
                    e = e32;
                    nativeByteBuffer = null;
                    FileLog.m13728e(e);
                    j = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                    j2 = j;
                    ConnectionsManager.getInstance().sendRequest(tLObject, /* anonymous class already generated */);
                }
                j = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
            }
            j2 = j;
            ConnectionsManager.getInstance().sendRequest(tLObject, /* anonymous class already generated */);
        }
    }

    public void deleteSelectedDialogs() {
        ArrayList selectedDialogs = getSelectedDialogs();
        for (int i = 0; i < selectedDialogs.size(); i++) {
            long j = ((TLRPC$TL_dialog) selectedDialogs.get(i)).id;
            deleteDialog(j, 0);
            getInstance().deleteUserFromChat((int) (-j), UserConfig.getCurrentUser(), null);
            if (AndroidUtilities.isTablet()) {
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, Long.valueOf(j));
            }
        }
    }

    public void deleteUserChannelHistory(final Chat chat, final User user, int i) {
        if (i == 0) {
            MessagesStorage.getInstance().deleteUserChannelHistory(chat.id, user.id);
        }
        TLObject tL_channels_deleteUserHistory = new TL_channels_deleteUserHistory();
        tL_channels_deleteUserHistory.channel = getInputChannel(chat);
        tL_channels_deleteUserHistory.user_id = getInputUser(user);
        ConnectionsManager.getInstance().sendRequest(tL_channels_deleteUserHistory, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error == null) {
                    TLRPC$TL_messages_affectedHistory tLRPC$TL_messages_affectedHistory = (TLRPC$TL_messages_affectedHistory) tLObject;
                    if (tLRPC$TL_messages_affectedHistory.offset > 0) {
                        MessagesController.this.deleteUserChannelHistory(chat, user, tLRPC$TL_messages_affectedHistory.offset);
                    }
                    MessagesController.this.processNewChannelDifferenceParams(tLRPC$TL_messages_affectedHistory.pts, tLRPC$TL_messages_affectedHistory.pts_count, chat.id);
                }
            }
        });
    }

    public void deleteUserFromChat(int i, User user, ChatFull chatFull) {
        deleteUserFromChat(i, user, chatFull, false);
    }

    public void deleteUserFromChat(int i, User user, ChatFull chatFull, boolean z) {
        if (user != null) {
            if (i > 0) {
                TLObject tLObject;
                final InputUser inputUser = getInputUser(user);
                Chat chat = getChat(Integer.valueOf(i));
                final boolean isChannel = ChatObject.isChannel(chat);
                TLObject tLRPC$TL_messages_deleteChatUser;
                if (!isChannel) {
                    tLRPC$TL_messages_deleteChatUser = new TLRPC$TL_messages_deleteChatUser();
                    tLRPC$TL_messages_deleteChatUser.chat_id = i;
                    tLRPC$TL_messages_deleteChatUser.user_id = getInputUser(user);
                    tLObject = tLRPC$TL_messages_deleteChatUser;
                } else if (!(inputUser instanceof TLRPC$TL_inputUserSelf)) {
                    tLRPC$TL_messages_deleteChatUser = new TL_channels_editBanned();
                    tLRPC$TL_messages_deleteChatUser.channel = getInputChannel(chat);
                    tLRPC$TL_messages_deleteChatUser.user_id = inputUser;
                    tLRPC$TL_messages_deleteChatUser.banned_rights = new TL_channelBannedRights();
                    tLRPC$TL_messages_deleteChatUser.banned_rights.view_messages = true;
                    tLRPC$TL_messages_deleteChatUser.banned_rights.send_media = true;
                    tLRPC$TL_messages_deleteChatUser.banned_rights.send_messages = true;
                    tLRPC$TL_messages_deleteChatUser.banned_rights.send_stickers = true;
                    tLRPC$TL_messages_deleteChatUser.banned_rights.send_gifs = true;
                    tLRPC$TL_messages_deleteChatUser.banned_rights.send_games = true;
                    tLRPC$TL_messages_deleteChatUser.banned_rights.send_inline = true;
                    tLRPC$TL_messages_deleteChatUser.banned_rights.embed_links = true;
                    tLObject = tLRPC$TL_messages_deleteChatUser;
                } else if (chat.creator && z) {
                    tLRPC$TL_messages_deleteChatUser = new TL_channels_deleteChannel();
                    tLRPC$TL_messages_deleteChatUser.channel = getInputChannel(chat);
                    tLObject = tLRPC$TL_messages_deleteChatUser;
                } else {
                    tLRPC$TL_messages_deleteChatUser = new TL_channels_leaveChannel();
                    tLRPC$TL_messages_deleteChatUser.channel = getInputChannel(chat);
                    tLObject = tLRPC$TL_messages_deleteChatUser;
                }
                final User user2 = user;
                final int i2 = i;
                ConnectionsManager.getInstance().sendRequest(tLObject, new RequestDelegate() {

                    /* renamed from: org.telegram.messenger.MessagesController$98$1 */
                    class C32581 implements Runnable {
                        C32581() {
                        }

                        public void run() {
                            MessagesController.this.deleteDialog((long) (-i2), 0);
                        }
                    }

                    /* renamed from: org.telegram.messenger.MessagesController$98$2 */
                    class C32592 implements C2497d {
                        C32592() {
                        }

                        public void onResult(Object obj, int i) {
                        }
                    }

                    /* renamed from: org.telegram.messenger.MessagesController$98$3 */
                    class C32603 implements Runnable {
                        C32603() {
                        }

                        public void run() {
                            MessagesController.this.loadFullChat(i2, 0, true);
                        }
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (user2.id == UserConfig.getClientUserId()) {
                            AndroidUtilities.runOnUIThread(new C32581());
                        }
                        if (tLRPC$TL_error == null) {
                            if (C3791b.ab(ApplicationLoader.applicationContext)) {
                                Category category;
                                Iterator it = C3791b.m13921Y(ApplicationLoader.applicationContext).iterator();
                                long j = 0;
                                boolean z = false;
                                while (it.hasNext()) {
                                    boolean z2;
                                    category = (Category) it.next();
                                    if (category.getChannelId() == i2) {
                                        j = category.getId().longValue();
                                        z2 = true;
                                    } else {
                                        z2 = z;
                                    }
                                    z = z2;
                                }
                                if (z) {
                                    Log.d("alireza", "alireza leave from ads");
                                    category = new Category();
                                    category.setId(Long.valueOf(j));
                                    category.setStatus(2);
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(category);
                                    C2818c.m13087a(ApplicationLoader.applicationContext, new C32592()).m13117a(arrayList);
                                }
                            }
                            MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                            if (isChannel && !(inputUser instanceof TLRPC$TL_inputUserSelf)) {
                                AndroidUtilities.runOnUIThread(new C32603(), 1000);
                            }
                        }
                    }
                }, 64);
            } else if (chatFull instanceof TL_chatFull) {
                boolean z2;
                Chat chat2 = getChat(Integer.valueOf(i));
                chat2.participants_count--;
                ArrayList arrayList = new ArrayList();
                arrayList.add(chat2);
                MessagesStorage.getInstance().putUsersAndChats(null, arrayList, true, true);
                for (int i3 = 0; i3 < chatFull.participants.participants.size(); i3++) {
                    if (((ChatParticipant) chatFull.participants.participants.get(i3)).user_id == user.id) {
                        chatFull.participants.participants.remove(i3);
                        z2 = true;
                        break;
                    }
                }
                z2 = false;
                if (z2) {
                    MessagesStorage.getInstance().updateChatInfo(chatFull, true);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, chatFull, Integer.valueOf(0), Boolean.valueOf(false), null);
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(32));
            }
        }
    }

    public void deleteUserPhoto(InputPhoto inputPhoto) {
        if (inputPhoto == null) {
            TLObject tLRPC$TL_photos_updateProfilePhoto = new TLRPC$TL_photos_updateProfilePhoto();
            tLRPC$TL_photos_updateProfilePhoto.id = new TLRPC$TL_inputPhotoEmpty();
            UserConfig.getCurrentUser().photo = new TLRPC$TL_userProfilePhotoEmpty();
            User user = getUser(Integer.valueOf(UserConfig.getClientUserId()));
            if (user == null) {
                user = UserConfig.getCurrentUser();
            }
            if (user != null) {
                user.photo = UserConfig.getCurrentUser().photo;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(UPDATE_MASK_ALL));
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_photos_updateProfilePhoto, new RequestDelegate() {

                    /* renamed from: org.telegram.messenger.MessagesController$35$1 */
                    class C32141 implements Runnable {
                        C32141() {
                        }

                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_ALL));
                            UserConfig.saveConfig(true);
                        }
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            User user = MessagesController.this.getUser(Integer.valueOf(UserConfig.getClientUserId()));
                            if (user == null) {
                                user = UserConfig.getCurrentUser();
                                MessagesController.this.putUser(user, false);
                            } else {
                                UserConfig.setCurrentUser(user);
                            }
                            if (user != null) {
                                MessagesStorage.getInstance().clearUserPhotos(user.id);
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(user);
                                MessagesStorage.getInstance().putUsersAndChats(arrayList, null, false, true);
                                user.photo = (TLRPC$UserProfilePhoto) tLObject;
                                AndroidUtilities.runOnUIThread(new C32141());
                            }
                        }
                    }
                });
                return;
            }
            return;
        }
        TLObject tLRPC$TL_photos_deletePhotos = new TLRPC$TL_photos_deletePhotos();
        tLRPC$TL_photos_deletePhotos.id.add(inputPhoto);
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_photos_deletePhotos, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            }
        });
    }

    public void didAddedNewTask(final int i, final SparseArray<ArrayList<Long>> sparseArray) {
        Utilities.stageQueue.postRunnable(new Runnable() {
            public void run() {
                if ((MessagesController.this.currentDeletingTaskMids == null && !MessagesController.this.gettingNewDeleteTask) || (MessagesController.this.currentDeletingTaskTime != 0 && i < MessagesController.this.currentDeletingTaskTime)) {
                    MessagesController.this.getNewDeleteTask(null, 0);
                }
            }
        });
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didCreatedNewDeleteTask, sparseArray);
            }
        });
    }

    public void didReceivedNotification(int i, Object... objArr) {
        String str;
        if (i == NotificationCenter.FileDidUpload) {
            str = (String) objArr[0];
            InputFile inputFile = (InputFile) objArr[1];
            if (this.uploadingAvatar != null && this.uploadingAvatar.equals(str)) {
                TLObject tLRPC$TL_photos_uploadProfilePhoto = new TLRPC$TL_photos_uploadProfilePhoto();
                tLRPC$TL_photos_uploadProfilePhoto.file = inputFile;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_photos_uploadProfilePhoto, new C32245());
            }
        } else if (i == NotificationCenter.FileDidFailUpload) {
            str = (String) objArr[0];
            if (this.uploadingAvatar != null && this.uploadingAvatar.equals(str)) {
                this.uploadingAvatar = null;
            }
        } else if (i == NotificationCenter.messageReceivedByServer) {
            Integer num = (Integer) objArr[0];
            Integer num2 = (Integer) objArr[1];
            Long l = (Long) objArr[3];
            MessageObject messageObject = (MessageObject) this.dialogMessage.get(l);
            if (messageObject != null && (messageObject.getId() == num.intValue() || messageObject.messageOwner.local_id == num.intValue())) {
                messageObject.messageOwner.id = num2.intValue();
                messageObject.messageOwner.send_state = 0;
            }
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) this.dialogs_dict.get(l);
            if (tLRPC$TL_dialog != null && tLRPC$TL_dialog.top_message == num.intValue()) {
                tLRPC$TL_dialog.top_message = num2.intValue();
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            }
            MessageObject messageObject2 = (MessageObject) this.dialogMessagesByIds.remove(num);
            if (messageObject2 != null) {
                this.dialogMessagesByIds.put(num2, messageObject2);
            }
        } else if (i == NotificationCenter.updateMessageMedia) {
            Message message = (Message) objArr[0];
            MessageObject messageObject3 = (MessageObject) this.dialogMessagesByIds.get(Integer.valueOf(message.id));
            if (messageObject3 != null) {
                messageObject3.messageOwner.media = message.media;
                if (message.media.ttl_seconds == 0) {
                    return;
                }
                if ((message.media.photo instanceof TLRPC$TL_photoEmpty) || (message.media.document instanceof TLRPC$TL_documentEmpty)) {
                    messageObject3.setType();
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.notificationsSettingsUpdated, new Object[0]);
                }
            }
        }
    }

    public void favSelectedDialogs() {
        ArrayList selectedDialogs = getSelectedDialogs();
        for (int i = 0; i < selectedDialogs.size(); i++) {
            long j = ((TLRPC$TL_dialog) selectedDialogs.get(i)).id;
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) getInstance().dialogs_dict.get(Long.valueOf(j));
            if (!Favourite.isFavourite(Long.valueOf(tLRPC$TL_dialog.id))) {
                Favourite.addFavourite(Long.valueOf(j));
                getInstance().dialogsFavs.add(tLRPC$TL_dialog);
            }
        }
    }

    public void forceResetDialogs() {
        resetDialogs(true, MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
    }

    public void generateJoinMessage(final int i, boolean z) {
        Chat chat = getChat(Integer.valueOf(i));
        if (chat != null && ChatObject.isChannel(i)) {
            if ((!chat.left && !chat.kicked) || z) {
                Message tLRPC$TL_messageService = new TLRPC$TL_messageService();
                tLRPC$TL_messageService.flags = 256;
                int newMessageId = UserConfig.getNewMessageId();
                tLRPC$TL_messageService.id = newMessageId;
                tLRPC$TL_messageService.local_id = newMessageId;
                tLRPC$TL_messageService.date = ConnectionsManager.getInstance().getCurrentTime();
                tLRPC$TL_messageService.from_id = UserConfig.getClientUserId();
                tLRPC$TL_messageService.to_id = new TLRPC$TL_peerChannel();
                tLRPC$TL_messageService.to_id.channel_id = i;
                tLRPC$TL_messageService.dialog_id = (long) (-i);
                tLRPC$TL_messageService.post = true;
                tLRPC$TL_messageService.action = new TLRPC$TL_messageActionChatAddUser();
                tLRPC$TL_messageService.action.users.add(Integer.valueOf(UserConfig.getClientUserId()));
                if (chat.megagroup) {
                    tLRPC$TL_messageService.flags |= Integer.MIN_VALUE;
                }
                UserConfig.saveConfig(false);
                final ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(tLRPC$TL_messageService);
                arrayList.add(new MessageObject(tLRPC$TL_messageService, null, true));
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                    /* renamed from: org.telegram.messenger.MessagesController$116$1 */
                    class C31821 implements Runnable {
                        C31821() {
                        }

                        public void run() {
                            NotificationsController.getInstance().processNewMessages(arrayList, true);
                        }
                    }

                    public void run() {
                        AndroidUtilities.runOnUIThread(new C31821());
                    }
                });
                MessagesStorage.getInstance().putMessages(arrayList2, true, true, false, 0);
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        MessagesController.this.updateInterfaceWithMessages((long) (-i), arrayList);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                    }
                });
            }
        }
    }

    public void generateUpdateMessage() {
        if (!BuildVars.DEBUG_VERSION && UserConfig.lastUpdateVersion != null && !UserConfig.lastUpdateVersion.equals(BuildVars.BUILD_VERSION_STRING)) {
            TLObject tLRPC$TL_help_getAppChangelog = new TLRPC$TL_help_getAppChangelog();
            tLRPC$TL_help_getAppChangelog.prev_app_version = UserConfig.lastUpdateVersion;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_help_getAppChangelog, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        UserConfig.lastUpdateVersion = BuildVars.BUILD_VERSION_STRING;
                        UserConfig.saveConfig(false);
                    }
                    if (tLObject instanceof TLRPC$Updates) {
                        MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                    }
                }
            });
        }
    }

    public void getBlockedUsers(boolean z) {
        if (UserConfig.isClientActivated() && !this.loadingBlockedUsers) {
            this.loadingBlockedUsers = true;
            if (z) {
                MessagesStorage.getInstance().getBlockedUsers();
                return;
            }
            TLObject tLRPC$TL_contacts_getBlocked = new TLRPC$TL_contacts_getBlocked();
            tLRPC$TL_contacts_getBlocked.offset = 0;
            tLRPC$TL_contacts_getBlocked.limit = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_getBlocked, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    ArrayList arrayList;
                    ArrayList arrayList2 = new ArrayList();
                    if (tLRPC$TL_error == null) {
                        TLRPC$contacts_Blocked tLRPC$contacts_Blocked = (TLRPC$contacts_Blocked) tLObject;
                        Iterator it = tLRPC$contacts_Blocked.blocked.iterator();
                        while (it.hasNext()) {
                            arrayList2.add(Integer.valueOf(((TLRPC$TL_contactBlocked) it.next()).user_id));
                        }
                        arrayList = tLRPC$contacts_Blocked.users;
                        MessagesStorage.getInstance().putUsersAndChats(tLRPC$contacts_Blocked.users, null, true, true);
                        MessagesStorage.getInstance().putBlockedUsers(arrayList2, true);
                    } else {
                        arrayList = null;
                    }
                    MessagesController.this.processLoadedBlockedUsers(arrayList2, arrayList, false);
                }
            });
        }
    }

    protected void getChannelDifference(int i, int i2, long j, InputChannel inputChannel) {
        Integer num;
        int i3;
        NativeByteBuffer nativeByteBuffer;
        Throwable e;
        long createPendingTask;
        TLObject tLRPC$TL_updates_getChannelDifference;
        final int i4;
        Boolean bool = (Boolean) this.gettingDifferenceChannels.get(Integer.valueOf(i));
        if (bool == null) {
            bool = Boolean.valueOf(false);
        }
        if (!bool.booleanValue()) {
            if (i2 != 1) {
                Integer num2 = (Integer) this.channelsPts.get(Integer.valueOf(i));
                if (num2 == null) {
                    num2 = Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(i));
                    if (num2.intValue() != 0) {
                        this.channelsPts.put(Integer.valueOf(i), num2);
                    }
                    if (num2.intValue() == 0 && (i2 == 2 || i2 == 3)) {
                        return;
                    }
                }
                if (num2.intValue() != 0) {
                    num = num2;
                    i3 = 100;
                } else {
                    return;
                }
            } else if (((Integer) this.channelsPts.get(Integer.valueOf(i))) == null) {
                num = Integer.valueOf(1);
                i3 = 1;
            } else {
                return;
            }
            if (inputChannel == null) {
                Chat chat = getChat(Integer.valueOf(i));
                if (chat == null) {
                    chat = MessagesStorage.getInstance().getChatSync(i);
                    if (chat != null) {
                        putChat(chat, true);
                    }
                }
                inputChannel = getInputChannel(chat);
            }
            if (inputChannel != null && inputChannel.access_hash != 0) {
                if (j == 0) {
                    try {
                        nativeByteBuffer = new NativeByteBuffer(inputChannel.getObjectSize() + 12);
                        try {
                            nativeByteBuffer.writeInt32(6);
                            nativeByteBuffer.writeInt32(i);
                            nativeByteBuffer.writeInt32(i2);
                            inputChannel.serializeToStream(nativeByteBuffer);
                        } catch (Exception e2) {
                            e = e2;
                            FileLog.m13728e(e);
                            createPendingTask = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                            this.gettingDifferenceChannels.put(Integer.valueOf(i), Boolean.valueOf(true));
                            tLRPC$TL_updates_getChannelDifference = new TLRPC$TL_updates_getChannelDifference();
                            tLRPC$TL_updates_getChannelDifference.channel = inputChannel;
                            tLRPC$TL_updates_getChannelDifference.filter = new TL_channelMessagesFilterEmpty();
                            tLRPC$TL_updates_getChannelDifference.pts = num.intValue();
                            tLRPC$TL_updates_getChannelDifference.limit = i3;
                            tLRPC$TL_updates_getChannelDifference.force = i2 != 3;
                            FileLog.m13726e("start getChannelDifference with pts = " + num + " channelId = " + i);
                            i4 = i;
                            i3 = i2;
                            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_updates_getChannelDifference, new RequestDelegate() {
                                public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                                    int i = 0;
                                    if (tLRPC$TL_error == null) {
                                        int i2;
                                        final TLRPC$updates_ChannelDifference tLRPC$updates_ChannelDifference = (TLRPC$updates_ChannelDifference) tLObject;
                                        final HashMap hashMap = new HashMap();
                                        for (i2 = 0; i2 < tLRPC$updates_ChannelDifference.users.size(); i2++) {
                                            User user = (User) tLRPC$updates_ChannelDifference.users.get(i2);
                                            hashMap.put(Integer.valueOf(user.id), user);
                                        }
                                        Chat chat = null;
                                        for (i2 = 0; i2 < tLRPC$updates_ChannelDifference.chats.size(); i2++) {
                                            Chat chat2 = (Chat) tLRPC$updates_ChannelDifference.chats.get(i2);
                                            if (chat2.id == i4) {
                                                chat = chat2;
                                                break;
                                            }
                                        }
                                        final ArrayList arrayList = new ArrayList();
                                        if (!tLRPC$updates_ChannelDifference.other_updates.isEmpty()) {
                                            while (i < tLRPC$updates_ChannelDifference.other_updates.size()) {
                                                TLRPC$Update tLRPC$Update = (TLRPC$Update) tLRPC$updates_ChannelDifference.other_updates.get(i);
                                                if (tLRPC$Update instanceof TLRPC$TL_updateMessageID) {
                                                    arrayList.add((TLRPC$TL_updateMessageID) tLRPC$Update);
                                                    tLRPC$updates_ChannelDifference.other_updates.remove(i);
                                                    i--;
                                                }
                                                i++;
                                            }
                                        }
                                        MessagesStorage.getInstance().putUsersAndChats(tLRPC$updates_ChannelDifference.users, tLRPC$updates_ChannelDifference.chats, true, true);
                                        AndroidUtilities.runOnUIThread(new Runnable() {
                                            public void run() {
                                                MessagesController.this.putUsers(tLRPC$updates_ChannelDifference.users, false);
                                                MessagesController.this.putChats(tLRPC$updates_ChannelDifference.chats, false);
                                            }
                                        });
                                        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                            /* renamed from: org.telegram.messenger.MessagesController$112$2$2 */
                                            class C31692 implements Runnable {
                                                C31692() {
                                                }

                                                public void run() {
                                                    Integer num;
                                                    Integer num2;
                                                    Integer num3;
                                                    int i;
                                                    Message message;
                                                    boolean z;
                                                    if ((tLRPC$updates_ChannelDifference instanceof TLRPC$TL_updates_channelDifference) || (tLRPC$updates_ChannelDifference instanceof TLRPC$TL_updates_channelDifferenceEmpty)) {
                                                        if (!tLRPC$updates_ChannelDifference.new_messages.isEmpty()) {
                                                            final HashMap hashMap = new HashMap();
                                                            ImageLoader.saveMessagesThumbs(tLRPC$updates_ChannelDifference.new_messages);
                                                            final ArrayList arrayList = new ArrayList();
                                                            long j = (long) (-i4);
                                                            num = (Integer) MessagesController.this.dialogs_read_inbox_max.get(Long.valueOf(j));
                                                            if (num == null) {
                                                                num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(false, j));
                                                                MessagesController.this.dialogs_read_inbox_max.put(Long.valueOf(j), num);
                                                            }
                                                            num2 = num;
                                                            num = (Integer) MessagesController.this.dialogs_read_outbox_max.get(Long.valueOf(j));
                                                            if (num == null) {
                                                                num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(true, j));
                                                                MessagesController.this.dialogs_read_outbox_max.put(Long.valueOf(j), num);
                                                            }
                                                            num3 = num;
                                                            for (i = 0; i < tLRPC$updates_ChannelDifference.new_messages.size(); i++) {
                                                                MessageObject messageObject;
                                                                long j2;
                                                                ArrayList arrayList2;
                                                                message = (Message) tLRPC$updates_ChannelDifference.new_messages.get(i);
                                                                if (chat == null || !chat.left) {
                                                                    if ((message.out ? num3 : num2).intValue() < message.id && !(message.action instanceof TLRPC$TL_messageActionChannelCreate)) {
                                                                        z = true;
                                                                        message.unread = z;
                                                                        if (chat != null && chat.megagroup) {
                                                                            message.flags |= Integer.MIN_VALUE;
                                                                        }
                                                                        messageObject = new MessageObject(message, hashMap, MessagesController.this.createdDialogIds.contains(Long.valueOf(j)));
                                                                        if (!messageObject.isOut() && messageObject.isUnread()) {
                                                                            arrayList.add(messageObject);
                                                                        }
                                                                        j2 = (long) (-i4);
                                                                        arrayList2 = (ArrayList) hashMap.get(Long.valueOf(j2));
                                                                        if (arrayList2 == null) {
                                                                            arrayList2 = new ArrayList();
                                                                            hashMap.put(Long.valueOf(j2), arrayList2);
                                                                        }
                                                                        arrayList2.add(messageObject);
                                                                    }
                                                                }
                                                                z = false;
                                                                message.unread = z;
                                                                message.flags |= Integer.MIN_VALUE;
                                                                messageObject = new MessageObject(message, hashMap, MessagesController.this.createdDialogIds.contains(Long.valueOf(j)));
                                                                arrayList.add(messageObject);
                                                                j2 = (long) (-i4);
                                                                arrayList2 = (ArrayList) hashMap.get(Long.valueOf(j2));
                                                                if (arrayList2 == null) {
                                                                    arrayList2 = new ArrayList();
                                                                    hashMap.put(Long.valueOf(j2), arrayList2);
                                                                }
                                                                arrayList2.add(messageObject);
                                                            }
                                                            AndroidUtilities.runOnUIThread(new Runnable() {
                                                                public void run() {
                                                                    for (Entry entry : hashMap.entrySet()) {
                                                                        Long l = (Long) entry.getKey();
                                                                        MessagesController.this.updateInterfaceWithMessages(l.longValue(), (ArrayList) entry.getValue());
                                                                    }
                                                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                                                }
                                                            });
                                                            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                                                /* renamed from: org.telegram.messenger.MessagesController$112$2$2$2$1 */
                                                                class C31671 implements Runnable {
                                                                    C31671() {
                                                                    }

                                                                    public void run() {
                                                                        NotificationsController.getInstance().processNewMessages(arrayList, true);
                                                                    }
                                                                }

                                                                public void run() {
                                                                    if (!arrayList.isEmpty()) {
                                                                        AndroidUtilities.runOnUIThread(new C31671());
                                                                    }
                                                                    MessagesStorage.getInstance().putMessages(tLRPC$updates_ChannelDifference.new_messages, true, false, false, MediaController.getInstance().getAutodownloadMask());
                                                                }
                                                            });
                                                        }
                                                        if (!tLRPC$updates_ChannelDifference.other_updates.isEmpty()) {
                                                            MessagesController.this.processUpdateArray(tLRPC$updates_ChannelDifference.other_updates, tLRPC$updates_ChannelDifference.users, tLRPC$updates_ChannelDifference.chats, true);
                                                        }
                                                        MessagesController.this.processChannelsUpdatesQueue(i4, 1);
                                                        MessagesStorage.getInstance().saveChannelPts(i4, tLRPC$updates_ChannelDifference.pts);
                                                    } else if (tLRPC$updates_ChannelDifference instanceof TLRPC$TL_updates_channelDifferenceTooLong) {
                                                        long j3 = (long) (-i4);
                                                        num = (Integer) MessagesController.this.dialogs_read_inbox_max.get(Long.valueOf(j3));
                                                        if (num == null) {
                                                            num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(false, j3));
                                                            MessagesController.this.dialogs_read_inbox_max.put(Long.valueOf(j3), num);
                                                        }
                                                        num2 = num;
                                                        num = (Integer) MessagesController.this.dialogs_read_outbox_max.get(Long.valueOf(j3));
                                                        if (num == null) {
                                                            num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(true, j3));
                                                            MessagesController.this.dialogs_read_outbox_max.put(Long.valueOf(j3), num);
                                                        }
                                                        num3 = num;
                                                        for (i = 0; i < tLRPC$updates_ChannelDifference.messages.size(); i++) {
                                                            message = (Message) tLRPC$updates_ChannelDifference.messages.get(i);
                                                            message.dialog_id = (long) (-i4);
                                                            if (!(message.action instanceof TLRPC$TL_messageActionChannelCreate) && (chat == null || !chat.left)) {
                                                                if ((message.out ? num3 : num2).intValue() < message.id) {
                                                                    z = true;
                                                                    message.unread = z;
                                                                    if (chat != null && chat.megagroup) {
                                                                        message.flags |= Integer.MIN_VALUE;
                                                                    }
                                                                }
                                                            }
                                                            z = false;
                                                            message.unread = z;
                                                            message.flags |= Integer.MIN_VALUE;
                                                        }
                                                        MessagesStorage.getInstance().overwriteChannel(i4, (TLRPC$TL_updates_channelDifferenceTooLong) tLRPC$updates_ChannelDifference, i3);
                                                    }
                                                    MessagesController.this.gettingDifferenceChannels.remove(Integer.valueOf(i4));
                                                    MessagesController.this.channelsPts.put(Integer.valueOf(i4), Integer.valueOf(tLRPC$updates_ChannelDifference.pts));
                                                    if ((tLRPC$updates_ChannelDifference.flags & 2) != 0) {
                                                        MessagesController.this.shortPollChannels.put(i4, ((int) (System.currentTimeMillis() / 1000)) + tLRPC$updates_ChannelDifference.timeout);
                                                    }
                                                    if (!tLRPC$updates_ChannelDifference.isFinal) {
                                                        MessagesController.this.getChannelDifference(i4);
                                                    }
                                                    FileLog.m13726e("received channel difference with pts = " + tLRPC$updates_ChannelDifference.pts + " channelId = " + i4);
                                                    FileLog.m13726e("new_messages = " + tLRPC$updates_ChannelDifference.new_messages.size() + " messages = " + tLRPC$updates_ChannelDifference.messages.size() + " users = " + tLRPC$updates_ChannelDifference.users.size() + " chats = " + tLRPC$updates_ChannelDifference.chats.size() + " other updates = " + tLRPC$updates_ChannelDifference.other_updates.size());
                                                    if (createPendingTask != 0) {
                                                        MessagesStorage.getInstance().removePendingTask(createPendingTask);
                                                    }
                                                }
                                            }

                                            public void run() {
                                                if (!arrayList.isEmpty()) {
                                                    final HashMap hashMap = new HashMap();
                                                    Iterator it = arrayList.iterator();
                                                    while (it.hasNext()) {
                                                        TLRPC$TL_updateMessageID tLRPC$TL_updateMessageID = (TLRPC$TL_updateMessageID) it.next();
                                                        Object updateMessageStateAndId = MessagesStorage.getInstance().updateMessageStateAndId(tLRPC$TL_updateMessageID.random_id, null, tLRPC$TL_updateMessageID.id, 0, false, i4);
                                                        if (updateMessageStateAndId != null) {
                                                            hashMap.put(Integer.valueOf(tLRPC$TL_updateMessageID.id), updateMessageStateAndId);
                                                        }
                                                    }
                                                    if (!hashMap.isEmpty()) {
                                                        AndroidUtilities.runOnUIThread(new Runnable() {
                                                            public void run() {
                                                                for (Entry entry : hashMap.entrySet()) {
                                                                    Integer num = (Integer) entry.getKey();
                                                                    SendMessagesHelper.getInstance().processSentMessage(Integer.valueOf((int) ((long[]) entry.getValue())[1]).intValue());
                                                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageReceivedByServer, r3, num, null, Long.valueOf(r0[0]));
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                                Utilities.stageQueue.postRunnable(new C31692());
                                            }
                                        });
                                        return;
                                    }
                                    AndroidUtilities.runOnUIThread(new Runnable() {
                                        public void run() {
                                            MessagesController.this.checkChannelError(tLRPC$TL_error.text, i4);
                                        }
                                    });
                                    MessagesController.this.gettingDifferenceChannels.remove(Integer.valueOf(i4));
                                    if (createPendingTask != 0) {
                                        MessagesStorage.getInstance().removePendingTask(createPendingTask);
                                    }
                                }
                            });
                        }
                    } catch (Throwable e3) {
                        Throwable th = e3;
                        nativeByteBuffer = null;
                        e = th;
                        FileLog.m13728e(e);
                        createPendingTask = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                        this.gettingDifferenceChannels.put(Integer.valueOf(i), Boolean.valueOf(true));
                        tLRPC$TL_updates_getChannelDifference = new TLRPC$TL_updates_getChannelDifference();
                        tLRPC$TL_updates_getChannelDifference.channel = inputChannel;
                        tLRPC$TL_updates_getChannelDifference.filter = new TL_channelMessagesFilterEmpty();
                        tLRPC$TL_updates_getChannelDifference.pts = num.intValue();
                        tLRPC$TL_updates_getChannelDifference.limit = i3;
                        if (i2 != 3) {
                        }
                        tLRPC$TL_updates_getChannelDifference.force = i2 != 3;
                        FileLog.m13726e("start getChannelDifference with pts = " + num + " channelId = " + i);
                        i4 = i;
                        i3 = i2;
                        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_updates_getChannelDifference, /* anonymous class already generated */);
                    }
                    createPendingTask = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                } else {
                    createPendingTask = j;
                }
                this.gettingDifferenceChannels.put(Integer.valueOf(i), Boolean.valueOf(true));
                tLRPC$TL_updates_getChannelDifference = new TLRPC$TL_updates_getChannelDifference();
                tLRPC$TL_updates_getChannelDifference.channel = inputChannel;
                tLRPC$TL_updates_getChannelDifference.filter = new TL_channelMessagesFilterEmpty();
                tLRPC$TL_updates_getChannelDifference.pts = num.intValue();
                tLRPC$TL_updates_getChannelDifference.limit = i3;
                if (i2 != 3) {
                }
                tLRPC$TL_updates_getChannelDifference.force = i2 != 3;
                FileLog.m13726e("start getChannelDifference with pts = " + num + " channelId = " + i);
                i4 = i;
                i3 = i2;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_updates_getChannelDifference, /* anonymous class already generated */);
            } else if (j != 0) {
                MessagesStorage.getInstance().removePendingTask(j);
            }
        }
    }

    public Chat getChat(Integer num) {
        return (Chat) this.chats.get(num);
    }

    public void getDifference() {
        getDifference(MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue, false);
    }

    public void getDifference(int i, final int i2, final int i3, boolean z) {
        registerForPush(UserConfig.pushString);
        if (MessagesStorage.lastPtsValue == 0) {
            loadCurrentState();
        } else if (z || !this.gettingDifference) {
            this.gettingDifference = true;
            TLObject tLRPC$TL_updates_getDifference = new TLRPC$TL_updates_getDifference();
            tLRPC$TL_updates_getDifference.pts = i;
            tLRPC$TL_updates_getDifference.date = i2;
            tLRPC$TL_updates_getDifference.qts = i3;
            if (this.getDifferenceFirstSync) {
                tLRPC$TL_updates_getDifference.flags |= 1;
                if (ConnectionsManager.isConnectedOrConnectingToWiFi()) {
                    tLRPC$TL_updates_getDifference.pts_total_limit = DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS;
                } else {
                    tLRPC$TL_updates_getDifference.pts_total_limit = 1000;
                }
                this.getDifferenceFirstSync = false;
            }
            if (tLRPC$TL_updates_getDifference.date == 0) {
                tLRPC$TL_updates_getDifference.date = ConnectionsManager.getInstance().getCurrentTime();
            }
            FileLog.m13726e("start getDifference with date = " + i2 + " pts = " + i + " qts = " + i3);
            ConnectionsManager.getInstance().setIsUpdating(true);
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_updates_getDifference, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    int i = 0;
                    if (tLRPC$TL_error == null) {
                        final TLRPC$updates_Difference tLRPC$updates_Difference = (TLRPC$updates_Difference) tLObject;
                        if (tLRPC$updates_Difference instanceof TLRPC$TL_updates_differenceTooLong) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    MessagesController.this.loadedFullUsers.clear();
                                    MessagesController.this.loadedFullChats.clear();
                                    MessagesController.this.resetDialogs(true, MessagesStorage.lastSeqValue, tLRPC$updates_Difference.pts, i2, i3);
                                }
                            });
                            return;
                        }
                        int i2;
                        if (tLRPC$updates_Difference instanceof TLRPC$TL_updates_differenceSlice) {
                            MessagesController.this.getDifference(tLRPC$updates_Difference.intermediate_state.pts, tLRPC$updates_Difference.intermediate_state.date, tLRPC$updates_Difference.intermediate_state.qts, true);
                        }
                        final HashMap hashMap = new HashMap();
                        final HashMap hashMap2 = new HashMap();
                        for (i2 = 0; i2 < tLRPC$updates_Difference.users.size(); i2++) {
                            User user = (User) tLRPC$updates_Difference.users.get(i2);
                            hashMap.put(Integer.valueOf(user.id), user);
                        }
                        for (i2 = 0; i2 < tLRPC$updates_Difference.chats.size(); i2++) {
                            Chat chat = (Chat) tLRPC$updates_Difference.chats.get(i2);
                            hashMap2.put(Integer.valueOf(chat.id), chat);
                        }
                        final ArrayList arrayList = new ArrayList();
                        if (!tLRPC$updates_Difference.other_updates.isEmpty()) {
                            while (i < tLRPC$updates_Difference.other_updates.size()) {
                                TLRPC$Update tLRPC$Update = (TLRPC$Update) tLRPC$updates_Difference.other_updates.get(i);
                                if (tLRPC$Update instanceof TLRPC$TL_updateMessageID) {
                                    arrayList.add((TLRPC$TL_updateMessageID) tLRPC$Update);
                                    tLRPC$updates_Difference.other_updates.remove(i);
                                    i--;
                                } else if (MessagesController.this.getUpdateType(tLRPC$Update) == 2) {
                                    int access$100 = MessagesController.this.getUpdateChannelId(tLRPC$Update);
                                    Integer num = (Integer) MessagesController.this.channelsPts.get(Integer.valueOf(access$100));
                                    if (num == null) {
                                        num = Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(access$100));
                                        if (num.intValue() != 0) {
                                            MessagesController.this.channelsPts.put(Integer.valueOf(access$100), num);
                                        }
                                    }
                                    if (num.intValue() != 0 && tLRPC$Update.pts <= num.intValue()) {
                                        tLRPC$updates_Difference.other_updates.remove(i);
                                        i--;
                                    }
                                }
                                i++;
                            }
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                MessagesController.this.loadedFullUsers.clear();
                                MessagesController.this.loadedFullChats.clear();
                                MessagesController.this.putUsers(tLRPC$updates_Difference.users, false);
                                MessagesController.this.putChats(tLRPC$updates_Difference.chats, false);
                            }
                        });
                        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                            /* renamed from: org.telegram.messenger.MessagesController$113$3$2 */
                            class C31782 implements Runnable {
                                C31782() {
                                }

                                public void run() {
                                    int i = 0;
                                    if (!(tLRPC$updates_Difference.new_messages.isEmpty() && tLRPC$updates_Difference.new_encrypted_messages.isEmpty())) {
                                        final HashMap hashMap = new HashMap();
                                        for (int i2 = 0; i2 < tLRPC$updates_Difference.new_encrypted_messages.size(); i2++) {
                                            Collection decryptMessage = SecretChatHelper.getInstance().decryptMessage((EncryptedMessage) tLRPC$updates_Difference.new_encrypted_messages.get(i2));
                                            if (!(decryptMessage == null || decryptMessage.isEmpty())) {
                                                tLRPC$updates_Difference.new_messages.addAll(decryptMessage);
                                            }
                                        }
                                        ImageLoader.saveMessagesThumbs(tLRPC$updates_Difference.new_messages);
                                        final ArrayList arrayList = new ArrayList();
                                        int clientUserId = UserConfig.getClientUserId();
                                        for (int i3 = 0; i3 < tLRPC$updates_Difference.new_messages.size(); i3++) {
                                            Message message = (Message) tLRPC$updates_Difference.new_messages.get(i3);
                                            if (message.dialog_id == 0) {
                                                if (message.to_id.chat_id != 0) {
                                                    message.dialog_id = (long) (-message.to_id.chat_id);
                                                } else {
                                                    if (message.to_id.user_id == UserConfig.getClientUserId()) {
                                                        message.to_id.user_id = message.from_id;
                                                    }
                                                    message.dialog_id = (long) message.to_id.user_id;
                                                }
                                            }
                                            if (((int) message.dialog_id) != 0) {
                                                if (message.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                                                    if (MessagesController.this.hideLeftGroup && message.action.user_id == message.from_id) {
                                                    } else {
                                                        User user = (User) hashMap.get(Integer.valueOf(message.action.user_id));
                                                        if (user != null && user.bot) {
                                                            message.reply_markup = new TLRPC$TL_replyKeyboardHide();
                                                            message.flags |= 64;
                                                        }
                                                    }
                                                }
                                                if ((message.action instanceof TLRPC$TL_messageActionChatMigrateTo) || (message.action instanceof TLRPC$TL_messageActionChannelCreate)) {
                                                    message.unread = false;
                                                    message.media_unread = false;
                                                } else {
                                                    ConcurrentHashMap concurrentHashMap = message.out ? MessagesController.this.dialogs_read_outbox_max : MessagesController.this.dialogs_read_inbox_max;
                                                    Integer num = (Integer) concurrentHashMap.get(Long.valueOf(message.dialog_id));
                                                    if (num == null) {
                                                        num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, message.dialog_id));
                                                        concurrentHashMap.put(Long.valueOf(message.dialog_id), num);
                                                    }
                                                    message.unread = num.intValue() < message.id;
                                                }
                                            }
                                            if (message.dialog_id == ((long) clientUserId)) {
                                                message.unread = false;
                                                message.media_unread = false;
                                                message.out = true;
                                            }
                                            MessageObject messageObject = new MessageObject(message, hashMap, hashMap2, MessagesController.this.createdDialogIds.contains(Long.valueOf(message.dialog_id)));
                                            if (!messageObject.isOut() && messageObject.isUnread()) {
                                                arrayList.add(messageObject);
                                            }
                                            ArrayList arrayList2 = (ArrayList) hashMap.get(Long.valueOf(message.dialog_id));
                                            if (arrayList2 == null) {
                                                arrayList2 = new ArrayList();
                                                hashMap.put(Long.valueOf(message.dialog_id), arrayList2);
                                            }
                                            arrayList2.add(messageObject);
                                        }
                                        AndroidUtilities.runOnUIThread(new Runnable() {
                                            public void run() {
                                                for (Entry entry : hashMap.entrySet()) {
                                                    Long l = (Long) entry.getKey();
                                                    MessagesController.this.updateInterfaceWithMessages(l.longValue(), (ArrayList) entry.getValue());
                                                }
                                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                            }
                                        });
                                        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                            /* renamed from: org.telegram.messenger.MessagesController$113$3$2$2$1 */
                                            class C31761 implements Runnable {
                                                C31761() {
                                                }

                                                public void run() {
                                                    NotificationsController.getInstance().processNewMessages(arrayList, !(tLRPC$updates_Difference instanceof TLRPC$TL_updates_differenceSlice));
                                                }
                                            }

                                            public void run() {
                                                if (!arrayList.isEmpty()) {
                                                    AndroidUtilities.runOnUIThread(new C31761());
                                                }
                                                MessagesStorage.getInstance().putMessages(tLRPC$updates_Difference.new_messages, true, false, false, MediaController.getInstance().getAutodownloadMask());
                                            }
                                        });
                                        SecretChatHelper.getInstance().processPendingEncMessages();
                                    }
                                    if (!tLRPC$updates_Difference.other_updates.isEmpty()) {
                                        MessagesController.this.processUpdateArray(tLRPC$updates_Difference.other_updates, tLRPC$updates_Difference.users, tLRPC$updates_Difference.chats, true);
                                    }
                                    if (tLRPC$updates_Difference instanceof TLRPC$TL_updates_difference) {
                                        MessagesController.this.gettingDifference = false;
                                        MessagesStorage.lastSeqValue = tLRPC$updates_Difference.state.seq;
                                        MessagesStorage.lastDateValue = tLRPC$updates_Difference.state.date;
                                        MessagesStorage.lastPtsValue = tLRPC$updates_Difference.state.pts;
                                        MessagesStorage.lastQtsValue = tLRPC$updates_Difference.state.qts;
                                        ConnectionsManager.getInstance().setIsUpdating(false);
                                        while (i < 3) {
                                            MessagesController.this.processUpdatesQueue(i, 1);
                                            i++;
                                        }
                                    } else if (tLRPC$updates_Difference instanceof TLRPC$TL_updates_differenceSlice) {
                                        MessagesStorage.lastDateValue = tLRPC$updates_Difference.intermediate_state.date;
                                        MessagesStorage.lastPtsValue = tLRPC$updates_Difference.intermediate_state.pts;
                                        MessagesStorage.lastQtsValue = tLRPC$updates_Difference.intermediate_state.qts;
                                    } else if (tLRPC$updates_Difference instanceof TLRPC$TL_updates_differenceEmpty) {
                                        MessagesController.this.gettingDifference = false;
                                        MessagesStorage.lastSeqValue = tLRPC$updates_Difference.seq;
                                        MessagesStorage.lastDateValue = tLRPC$updates_Difference.date;
                                        ConnectionsManager.getInstance().setIsUpdating(false);
                                        while (i < 3) {
                                            MessagesController.this.processUpdatesQueue(i, 1);
                                            i++;
                                        }
                                    }
                                    MessagesStorage.getInstance().saveDiffParams(MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
                                    FileLog.m13726e("received difference with date = " + MessagesStorage.lastDateValue + " pts = " + MessagesStorage.lastPtsValue + " seq = " + MessagesStorage.lastSeqValue + " messages = " + tLRPC$updates_Difference.new_messages.size() + " users = " + tLRPC$updates_Difference.users.size() + " chats = " + tLRPC$updates_Difference.chats.size() + " other updates = " + tLRPC$updates_Difference.other_updates.size());
                                }
                            }

                            public void run() {
                                MessagesStorage.getInstance().putUsersAndChats(tLRPC$updates_Difference.users, tLRPC$updates_Difference.chats, true, false);
                                if (!arrayList.isEmpty()) {
                                    final HashMap hashMap = new HashMap();
                                    for (int i = 0; i < arrayList.size(); i++) {
                                        TLRPC$TL_updateMessageID tLRPC$TL_updateMessageID = (TLRPC$TL_updateMessageID) arrayList.get(i);
                                        Object updateMessageStateAndId = MessagesStorage.getInstance().updateMessageStateAndId(tLRPC$TL_updateMessageID.random_id, null, tLRPC$TL_updateMessageID.id, 0, false, 0);
                                        if (updateMessageStateAndId != null) {
                                            hashMap.put(Integer.valueOf(tLRPC$TL_updateMessageID.id), updateMessageStateAndId);
                                        }
                                    }
                                    if (!hashMap.isEmpty()) {
                                        AndroidUtilities.runOnUIThread(new Runnable() {
                                            public void run() {
                                                for (Entry entry : hashMap.entrySet()) {
                                                    Integer num = (Integer) entry.getKey();
                                                    SendMessagesHelper.getInstance().processSentMessage(Integer.valueOf((int) ((long[]) entry.getValue())[1]).intValue());
                                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageReceivedByServer, r3, num, null, Long.valueOf(r0[0]));
                                                }
                                            }
                                        });
                                    }
                                }
                                Utilities.stageQueue.postRunnable(new C31782());
                            }
                        });
                        return;
                    }
                    MessagesController.this.gettingDifference = false;
                    ConnectionsManager.getInstance().setIsUpdating(false);
                }
            });
        }
    }

    public EncryptedChat getEncryptedChat(Integer num) {
        return (EncryptedChat) this.encryptedChats.get(num);
    }

    public EncryptedChat getEncryptedChatDB(int i, boolean z) {
        EncryptedChat encryptedChat = (EncryptedChat) this.encryptedChats.get(Integer.valueOf(i));
        if (encryptedChat != null) {
            if (!z) {
                return encryptedChat;
            }
            if (!((encryptedChat instanceof TLRPC$TL_encryptedChatWaiting) || (encryptedChat instanceof TLRPC$TL_encryptedChatRequested))) {
                return encryptedChat;
            }
        }
        Semaphore semaphore = new Semaphore(0);
        ArrayList arrayList = new ArrayList();
        MessagesStorage.getInstance().getEncryptedChat(i, semaphore, arrayList);
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        if (arrayList.size() != 2) {
            return encryptedChat;
        }
        encryptedChat = (EncryptedChat) arrayList.get(0);
        User user = (User) arrayList.get(1);
        putEncryptedChat(encryptedChat, false);
        putUser(user, true);
        return encryptedChat;
    }

    public ExportedChatInvite getExportedInvite(int i) {
        return (ExportedChatInvite) this.exportedChats.get(Integer.valueOf(i));
    }

    public void getNewDeleteTask(final ArrayList<Integer> arrayList, final int i) {
        Utilities.stageQueue.postRunnable(new Runnable() {
            public void run() {
                MessagesController.this.gettingNewDeleteTask = true;
                MessagesStorage.getInstance().getNewTask(arrayList, i);
            }
        });
    }

    public ArrayList<TLRPC$TL_dialog> getSelectedDialogs() {
        ArrayList<TLRPC$TL_dialog> arrayList = new ArrayList();
        Iterator it = this.dialogs.iterator();
        while (it.hasNext()) {
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) it.next();
            if (tLRPC$TL_dialog.isSelected()) {
                arrayList.add(tLRPC$TL_dialog);
            }
        }
        return arrayList;
    }

    public long getUpdatesStartTime(int i) {
        return i == 0 ? this.updatesStartWaitTimeSeq : i == 1 ? this.updatesStartWaitTimePts : i == 2 ? this.updatesStartWaitTimeQts : 0;
    }

    public User getUser(Integer num) {
        return (User) this.users.get(num);
    }

    public TLRPC$TL_userFull getUserFull(int i) {
        return (TLRPC$TL_userFull) this.fullUsers.get(Integer.valueOf(i));
    }

    public TLObject getUserOrChat(String str) {
        return (str == null || str.length() == 0) ? null : (TLObject) this.objectsByUsernames.get(str.toLowerCase());
    }

    public ConcurrentHashMap<Integer, User> getUsers() {
        return this.users;
    }

    public void hideReportSpam(long j, User user, Chat chat) {
        if (user != null || chat != null) {
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            edit.putInt("spam3_" + j, 1);
            edit.commit();
            if (((int) j) != 0) {
                TLObject tLRPC$TL_messages_hideReportSpam = new TLRPC$TL_messages_hideReportSpam();
                if (user != null) {
                    tLRPC$TL_messages_hideReportSpam.peer = getInputPeer(user.id);
                } else if (chat != null) {
                    tLRPC$TL_messages_hideReportSpam.peer = getInputPeer(-chat.id);
                }
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_hideReportSpam, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    }
                });
            }
        }
    }

    public boolean isChannelAdmin(int i, int i2) {
        ArrayList arrayList = (ArrayList) this.channelAdmins.get(Integer.valueOf(i));
        return arrayList != null && arrayList.indexOf(Integer.valueOf(i2)) >= 0;
    }

    public boolean isDialogCreated(long j) {
        return this.createdDialogMainThreadIds.contains(Long.valueOf(j));
    }

    public boolean isDialogMuted(long j) {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        int i = sharedPreferences.getInt("notify2_" + j, 0);
        return i == 2 ? true : i == 3 && sharedPreferences.getInt("notifyuntil_" + j, 0) >= ConnectionsManager.getInstance().getCurrentTime();
    }

    public void loadChannelAdmins(final int i, boolean z) {
        if (this.loadingChannelAdmins.indexOfKey(i) < 0) {
            this.loadingChannelAdmins.put(i, 0);
            if (z) {
                MessagesStorage.getInstance().loadChannelAdmins(i);
                return;
            }
            TLObject tL_channels_getParticipants = new TL_channels_getParticipants();
            ArrayList arrayList = (ArrayList) this.channelAdmins.get(Integer.valueOf(i));
            if (arrayList != null) {
                long j = 0;
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    j = (((j * 20261) + 2147483648L) + ((long) ((Integer) arrayList.get(i2)).intValue())) % 2147483648L;
                }
                tL_channels_getParticipants.hash = (int) j;
            }
            tL_channels_getParticipants.channel = getInputChannel(i);
            tL_channels_getParticipants.limit = 100;
            tL_channels_getParticipants.filter = new TL_channelParticipantsAdmins();
            ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipants, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLObject instanceof TL_channels_channelParticipants) {
                        TL_channels_channelParticipants tL_channels_channelParticipants = (TL_channels_channelParticipants) tLObject;
                        ArrayList arrayList = new ArrayList(tL_channels_channelParticipants.participants.size());
                        for (int i = 0; i < tL_channels_channelParticipants.participants.size(); i++) {
                            arrayList.add(Integer.valueOf(((ChannelParticipant) tL_channels_channelParticipants.participants.get(i)).user_id));
                        }
                        MessagesController.this.processLoadedChannelAdmins(arrayList, i, false);
                    }
                }
            });
        }
    }

    public void loadChannelParticipants(final Integer num) {
        if (!this.loadingFullParticipants.contains(num) && !this.loadedFullParticipants.contains(num)) {
            this.loadingFullParticipants.add(num);
            TLObject tL_channels_getParticipants = new TL_channels_getParticipants();
            tL_channels_getParticipants.channel = getInputChannel(num.intValue());
            tL_channels_getParticipants.filter = new TL_channelParticipantsRecent();
            tL_channels_getParticipants.offset = 0;
            tL_channels_getParticipants.limit = 32;
            ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipants, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLRPC$TL_error == null) {
                                TL_channels_channelParticipants tL_channels_channelParticipants = (TL_channels_channelParticipants) tLObject;
                                MessagesController.this.putUsers(tL_channels_channelParticipants.users, false);
                                MessagesStorage.getInstance().putUsersAndChats(tL_channels_channelParticipants.users, null, true, true);
                                MessagesStorage.getInstance().updateChannelUsers(num.intValue(), tL_channels_channelParticipants.participants);
                                MessagesController.this.loadedFullParticipants.add(num);
                            }
                            MessagesController.this.loadingFullParticipants.remove(num);
                        }
                    });
                }
            });
        }
    }

    public void loadChatInfo(int i, Semaphore semaphore, boolean z) {
        MessagesStorage.getInstance().loadChatInfo(i, semaphore, z, false);
    }

    public void loadCurrentState() {
        if (!this.updatingState) {
            this.updatingState = true;
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_updates_getState(), new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    int i = 0;
                    MessagesController.this.updatingState = false;
                    if (tLRPC$TL_error == null) {
                        TLRPC$TL_updates_state tLRPC$TL_updates_state = (TLRPC$TL_updates_state) tLObject;
                        MessagesStorage.lastDateValue = tLRPC$TL_updates_state.date;
                        MessagesStorage.lastPtsValue = tLRPC$TL_updates_state.pts;
                        MessagesStorage.lastSeqValue = tLRPC$TL_updates_state.seq;
                        MessagesStorage.lastQtsValue = tLRPC$TL_updates_state.qts;
                        while (i < 3) {
                            MessagesController.this.processUpdatesQueue(i, 2);
                            i++;
                        }
                        MessagesStorage.getInstance().saveDiffParams(MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
                    } else if (tLRPC$TL_error.code != 401) {
                        MessagesController.this.loadCurrentState();
                    }
                }
            });
        }
    }

    public void loadDialogPhotos(int i, int i2, long j, boolean z, int i3) {
        if (z) {
            MessagesStorage.getInstance().getDialogPhotos(i, i2, j, i3);
        } else if (i > 0) {
            User user = getUser(Integer.valueOf(i));
            if (user != null) {
                r7 = new TLRPC$TL_photos_getUserPhotos();
                r7.limit = i2;
                r7.offset = 0;
                r7.max_id = (long) ((int) j);
                r7.user_id = getInputUser(user);
                r2 = i;
                r3 = i2;
                r4 = j;
                r6 = i3;
                ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(r7, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            MessagesController.this.processLoadedUserPhotos((TLRPC$photos_Photos) tLObject, r2, r3, r4, false, r6);
                        }
                    }
                }), i3);
            }
        } else if (i < 0) {
            r7 = new TLRPC$TL_messages_search();
            r7.filter = new TLRPC$TL_inputMessagesFilterChatPhotos();
            r7.limit = i2;
            r7.offset_id = (int) j;
            r7.f10166q = TtmlNode.ANONYMOUS_REGION_ID;
            r7.peer = getInputPeer(i);
            r2 = i;
            r3 = i2;
            r4 = j;
            r6 = i3;
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(r7, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                        TLRPC$photos_Photos tLRPC$TL_photos_photos = new TLRPC$TL_photos_photos();
                        tLRPC$TL_photos_photos.count = tLRPC$messages_Messages.count;
                        tLRPC$TL_photos_photos.users.addAll(tLRPC$messages_Messages.users);
                        for (int i = 0; i < tLRPC$messages_Messages.messages.size(); i++) {
                            Message message = (Message) tLRPC$messages_Messages.messages.get(i);
                            if (!(message.action == null || message.action.photo == null)) {
                                tLRPC$TL_photos_photos.photos.add(message.action.photo);
                            }
                        }
                        MessagesController.this.processLoadedUserPhotos(tLRPC$TL_photos_photos, r2, r3, r4, false, r6);
                    }
                }
            }), i3);
        }
    }

    public void loadDialogs(int i, final int i2, boolean z) {
        boolean z2 = false;
        if (!this.loadingDialogs && !this.resetingDialogs) {
            this.loadingDialogs = true;
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            FileLog.m13726e("load cacheOffset = " + i + " count = " + i2 + " cache = " + z);
            if (z) {
                MessagesStorage.getInstance().getDialogs(i == 0 ? 0 : this.nextDialogsCacheOffset, i2);
                return;
            }
            TLObject tLRPC$TL_messages_getDialogs = new TLRPC$TL_messages_getDialogs();
            tLRPC$TL_messages_getDialogs.limit = i2;
            tLRPC$TL_messages_getDialogs.exclude_pinned = true;
            if (UserConfig.dialogsLoadOffsetId == -1) {
                for (int size = this.dialogs.size() - 1; size >= 0; size--) {
                    TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) this.dialogs.get(size);
                    if (!tLRPC$TL_dialog.pinned) {
                        int i3 = (int) (tLRPC$TL_dialog.id >> 32);
                        if (!(((int) tLRPC$TL_dialog.id) == 0 || i3 == 1 || tLRPC$TL_dialog.top_message <= 0)) {
                            MessageObject messageObject = (MessageObject) this.dialogMessage.get(Long.valueOf(tLRPC$TL_dialog.id));
                            if (messageObject != null && messageObject.getId() > 0) {
                                tLRPC$TL_messages_getDialogs.offset_date = messageObject.messageOwner.date;
                                tLRPC$TL_messages_getDialogs.offset_id = messageObject.messageOwner.id;
                                int i4 = messageObject.messageOwner.to_id.channel_id != 0 ? -messageObject.messageOwner.to_id.channel_id : messageObject.messageOwner.to_id.chat_id != 0 ? -messageObject.messageOwner.to_id.chat_id : messageObject.messageOwner.to_id.user_id;
                                tLRPC$TL_messages_getDialogs.offset_peer = getInputPeer(i4);
                                z2 = true;
                                if (!z2) {
                                    tLRPC$TL_messages_getDialogs.offset_peer = new TLRPC$TL_inputPeerEmpty();
                                }
                            }
                        }
                    }
                }
                if (z2) {
                    tLRPC$TL_messages_getDialogs.offset_peer = new TLRPC$TL_inputPeerEmpty();
                }
            } else if (UserConfig.dialogsLoadOffsetId == Integer.MAX_VALUE) {
                this.dialogsEndReached = true;
                this.serverDialogsEndReached = true;
                this.loadingDialogs = false;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                return;
            } else {
                tLRPC$TL_messages_getDialogs.offset_id = UserConfig.dialogsLoadOffsetId;
                tLRPC$TL_messages_getDialogs.offset_date = UserConfig.dialogsLoadOffsetDate;
                if (tLRPC$TL_messages_getDialogs.offset_id == 0) {
                    tLRPC$TL_messages_getDialogs.offset_peer = new TLRPC$TL_inputPeerEmpty();
                } else {
                    if (UserConfig.dialogsLoadOffsetChannelId != 0) {
                        tLRPC$TL_messages_getDialogs.offset_peer = new TLRPC$TL_inputPeerChannel();
                        tLRPC$TL_messages_getDialogs.offset_peer.channel_id = UserConfig.dialogsLoadOffsetChannelId;
                    } else if (UserConfig.dialogsLoadOffsetUserId != 0) {
                        tLRPC$TL_messages_getDialogs.offset_peer = new TLRPC$TL_inputPeerUser();
                        tLRPC$TL_messages_getDialogs.offset_peer.user_id = UserConfig.dialogsLoadOffsetUserId;
                    } else {
                        tLRPC$TL_messages_getDialogs.offset_peer = new TLRPC$TL_inputPeerChat();
                        tLRPC$TL_messages_getDialogs.offset_peer.chat_id = UserConfig.dialogsLoadOffsetChatId;
                    }
                    tLRPC$TL_messages_getDialogs.offset_peer.access_hash = UserConfig.dialogsLoadOffsetAccess;
                }
            }
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getDialogs, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        MessagesController.this.processLoadedDialogs((TLRPC$messages_Dialogs) tLObject, null, 0, i2, 0, false, false, false);
                    }
                }
            });
        }
    }

    public void loadFullChat(int i, int i2, boolean z) {
        boolean contains = this.loadedFullChats.contains(Integer.valueOf(i));
        if (!this.loadingFullChats.contains(Integer.valueOf(i))) {
            if (z || !contains) {
                TLObject tLObject;
                this.loadingFullChats.add(Integer.valueOf(i));
                final long j = (long) (-i);
                final Chat chat = getChat(Integer.valueOf(i));
                if (ChatObject.isChannel(chat)) {
                    TLObject tL_channels_getFullChannel = new TL_channels_getFullChannel();
                    tL_channels_getFullChannel.channel = getInputChannel(chat);
                    if (chat.megagroup) {
                        loadChannelAdmins(i, !contains);
                    }
                    tLObject = tL_channels_getFullChannel;
                } else {
                    tLObject = new TLRPC$TL_messages_getFullChat();
                    tLObject.chat_id = i;
                    if (this.dialogs_read_inbox_max.get(Long.valueOf(j)) == null || this.dialogs_read_outbox_max.get(Long.valueOf(j)) == null) {
                        reloadDialogsReadValue(null, j);
                    }
                }
                final int i3 = i;
                final int i4 = i2;
                int sendRequest = ConnectionsManager.getInstance().sendRequest(tLObject, new RequestDelegate() {
                    public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            final TLRPC$TL_messages_chatFull tLRPC$TL_messages_chatFull = (TLRPC$TL_messages_chatFull) tLObject;
                            MessagesStorage.getInstance().putUsersAndChats(tLRPC$TL_messages_chatFull.users, tLRPC$TL_messages_chatFull.chats, true, true);
                            MessagesStorage.getInstance().updateChatInfo(tLRPC$TL_messages_chatFull.full_chat, false);
                            if (ChatObject.isChannel(chat)) {
                                ArrayList arrayList;
                                Integer num = (Integer) MessagesController.this.dialogs_read_inbox_max.get(Long.valueOf(j));
                                if (num == null) {
                                    num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(false, j));
                                }
                                MessagesController.this.dialogs_read_inbox_max.put(Long.valueOf(j), Integer.valueOf(Math.max(tLRPC$TL_messages_chatFull.full_chat.read_inbox_max_id, num.intValue())));
                                if (num.intValue() == 0) {
                                    arrayList = new ArrayList();
                                    TLRPC$TL_updateReadChannelInbox tLRPC$TL_updateReadChannelInbox = new TLRPC$TL_updateReadChannelInbox();
                                    tLRPC$TL_updateReadChannelInbox.channel_id = i3;
                                    tLRPC$TL_updateReadChannelInbox.max_id = tLRPC$TL_messages_chatFull.full_chat.read_inbox_max_id;
                                    arrayList.add(tLRPC$TL_updateReadChannelInbox);
                                    MessagesController.this.processUpdateArray(arrayList, null, null, false);
                                }
                                num = (Integer) MessagesController.this.dialogs_read_outbox_max.get(Long.valueOf(j));
                                if (num == null) {
                                    num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(true, j));
                                }
                                MessagesController.this.dialogs_read_outbox_max.put(Long.valueOf(j), Integer.valueOf(Math.max(tLRPC$TL_messages_chatFull.full_chat.read_outbox_max_id, num.intValue())));
                                if (num.intValue() == 0) {
                                    arrayList = new ArrayList();
                                    TLRPC$TL_updateReadChannelOutbox tLRPC$TL_updateReadChannelOutbox = new TLRPC$TL_updateReadChannelOutbox();
                                    tLRPC$TL_updateReadChannelOutbox.channel_id = i3;
                                    tLRPC$TL_updateReadChannelOutbox.max_id = tLRPC$TL_messages_chatFull.full_chat.read_outbox_max_id;
                                    arrayList.add(tLRPC$TL_updateReadChannelOutbox);
                                    MessagesController.this.processUpdateArray(arrayList, null, null, false);
                                }
                            }
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    MessagesController.this.applyDialogNotificationsSettings((long) (-i3), tLRPC$TL_messages_chatFull.full_chat.notify_settings);
                                    for (int i = 0; i < tLRPC$TL_messages_chatFull.full_chat.bot_info.size(); i++) {
                                        BotQuery.putBotInfo((BotInfo) tLRPC$TL_messages_chatFull.full_chat.bot_info.get(i));
                                    }
                                    MessagesController.this.exportedChats.put(Integer.valueOf(i3), tLRPC$TL_messages_chatFull.full_chat.exported_invite);
                                    MessagesController.this.loadingFullChats.remove(Integer.valueOf(i3));
                                    MessagesController.this.loadedFullChats.add(Integer.valueOf(i3));
                                    if (!tLRPC$TL_messages_chatFull.chats.isEmpty()) {
                                        ((Chat) tLRPC$TL_messages_chatFull.chats.get(0)).address = tLRPC$TL_messages_chatFull.full_chat.about;
                                    }
                                    MessagesController.this.putUsers(tLRPC$TL_messages_chatFull.users, false);
                                    MessagesController.this.putChats(tLRPC$TL_messages_chatFull.chats, false);
                                    if (tLRPC$TL_messages_chatFull.full_chat.stickerset != null) {
                                        StickersQuery.getGroupStickerSetById(tLRPC$TL_messages_chatFull.full_chat.stickerset);
                                    }
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, tLRPC$TL_messages_chatFull.full_chat, Integer.valueOf(i4), Boolean.valueOf(false), null);
                                }
                            });
                            return;
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                MessagesController.this.checkChannelError(tLRPC$TL_error.text, i3);
                                MessagesController.this.loadingFullChats.remove(Integer.valueOf(i3));
                            }
                        });
                    }
                });
                if (i2 != 0) {
                    ConnectionsManager.getInstance().bindRequestToGuid(sendRequest, i2);
                }
            }
        }
    }

    public void loadFullUser(final User user, final int i, boolean z) {
        if (user != null && !this.loadingFullUsers.contains(Integer.valueOf(user.id))) {
            if (z || !this.loadedFullUsers.contains(Integer.valueOf(user.id))) {
                this.loadingFullUsers.add(Integer.valueOf(user.id));
                TLObject tLRPC$TL_users_getFullUser = new TLRPC$TL_users_getFullUser();
                tLRPC$TL_users_getFullUser.id = getInputUser(user);
                long j = (long) user.id;
                if (this.dialogs_read_inbox_max.get(Long.valueOf(j)) == null || this.dialogs_read_outbox_max.get(Long.valueOf(j)) == null) {
                    reloadDialogsReadValue(null, j);
                }
                ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tLRPC$TL_users_getFullUser, new RequestDelegate() {

                    /* renamed from: org.telegram.messenger.MessagesController$15$2 */
                    class C32022 implements Runnable {
                        C32022() {
                        }

                        public void run() {
                            MessagesController.this.loadingFullUsers.remove(Integer.valueOf(user.id));
                        }
                    }

                    public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    TLRPC$TL_userFull tLRPC$TL_userFull = (TLRPC$TL_userFull) tLObject;
                                    MessagesController.this.applyDialogNotificationsSettings((long) user.id, tLRPC$TL_userFull.notify_settings);
                                    if (tLRPC$TL_userFull.bot_info instanceof TL_botInfo) {
                                        BotQuery.putBotInfo(tLRPC$TL_userFull.bot_info);
                                    }
                                    MessagesController.this.fullUsers.put(Integer.valueOf(user.id), tLRPC$TL_userFull);
                                    MessagesController.this.loadingFullUsers.remove(Integer.valueOf(user.id));
                                    MessagesController.this.loadedFullUsers.add(Integer.valueOf(user.id));
                                    String str = user.first_name + user.last_name + user.username;
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(tLRPC$TL_userFull.user);
                                    MessagesController.this.putUsers(arrayList, false);
                                    MessagesStorage.getInstance().putUsersAndChats(arrayList, null, false, true);
                                    if (!(str == null || str.equals(tLRPC$TL_userFull.user.first_name + tLRPC$TL_userFull.user.last_name + tLRPC$TL_userFull.user.username))) {
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(1));
                                    }
                                    if (tLRPC$TL_userFull.bot_info instanceof TL_botInfo) {
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.botInfoDidLoaded, tLRPC$TL_userFull.bot_info, Integer.valueOf(i));
                                    }
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.userInfoDidLoaded, Integer.valueOf(user.id), tLRPC$TL_userFull);
                                }
                            });
                        } else {
                            AndroidUtilities.runOnUIThread(new C32022());
                        }
                    }
                }), i);
            }
        }
    }

    public void loadHintDialogs() {
        if (this.hintDialogs.isEmpty() && !TextUtils.isEmpty(this.installReferer)) {
            TLObject tLRPC$TL_help_getRecentMeUrls = new TLRPC$TL_help_getRecentMeUrls();
            tLRPC$TL_help_getRecentMeUrls.referer = this.installReferer;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_help_getRecentMeUrls, new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                TLRPC$TL_help_recentMeUrls tLRPC$TL_help_recentMeUrls = (TLRPC$TL_help_recentMeUrls) tLObject;
                                MessagesController.this.putUsers(tLRPC$TL_help_recentMeUrls.users, false);
                                MessagesController.this.putChats(tLRPC$TL_help_recentMeUrls.chats, false);
                                MessagesController.this.hintDialogs.clear();
                                MessagesController.this.hintDialogs.addAll(tLRPC$TL_help_recentMeUrls.urls);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                            }
                        });
                    }
                }
            });
        }
    }

    public void loadMessages(long j, int i, int i2, int i3, boolean z, int i4, int i5, int i6, int i7, boolean z2, int i8) {
        loadMessages(j, i, i2, i3, z, i4, i5, i6, i7, z2, i8, 0, 0, 0, false, 0);
    }

    public void loadMessages(long j, int i, int i2, int i3, boolean z, int i4, int i5, int i6, int i7, boolean z2, int i8, int i9, int i10, int i11, boolean z3, int i12) {
        FileLog.m13726e("load messages in chat " + j + " count " + i + " max_id " + i2 + " cache " + z + " mindate = " + i4 + " guid " + i5 + " load_type " + i6 + " last_message_id " + i7 + " index " + i8 + " firstUnread " + i9 + " unread_count " + i10 + " last_date " + i11 + " queryFromServer " + z3);
        int i13 = (int) j;
        if (z || i13 == 0) {
            MessagesStorage.getInstance().getMessages(j, i, i2, i3, i4, i5, i6, z2, i8);
            return;
        }
        TLObject tLRPC$TL_messages_getHistory = new TLRPC$TL_messages_getHistory();
        tLRPC$TL_messages_getHistory.peer = getInputPeer(i13);
        if (i6 == 4) {
            tLRPC$TL_messages_getHistory.add_offset = (-i) + 5;
        } else if (i6 == 3) {
            tLRPC$TL_messages_getHistory.add_offset = (-i) / 2;
        } else if (i6 == 1) {
            tLRPC$TL_messages_getHistory.add_offset = (-i) - 1;
        } else if (i6 == 2 && i2 != 0) {
            tLRPC$TL_messages_getHistory.add_offset = (-i) + 6;
        } else if (i13 < 0 && i2 != 0 && ChatObject.isChannel(getChat(Integer.valueOf(-i13)))) {
            tLRPC$TL_messages_getHistory.add_offset = -1;
            tLRPC$TL_messages_getHistory.limit++;
        }
        tLRPC$TL_messages_getHistory.limit = i;
        tLRPC$TL_messages_getHistory.offset_id = i2;
        tLRPC$TL_messages_getHistory.offset_date = i3;
        final int i14 = i;
        final int i15 = i2;
        final int i16 = i3;
        final long j2 = j;
        final int i17 = i5;
        final int i18 = i9;
        final int i19 = i7;
        final int i20 = i10;
        final int i21 = i11;
        final int i22 = i6;
        final boolean z4 = z2;
        final int i23 = i8;
        final boolean z5 = z3;
        final int i24 = i12;
        ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getHistory, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                Log.d("LEE", "Debug1946 enter to run()  ");
                if (tLObject != null) {
                    Log.d("LEE", "Debug1946 i got message ");
                    TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                    if (tLRPC$messages_Messages.messages.size() > i14) {
                        tLRPC$messages_Messages.messages.remove(0);
                    }
                    int i = i15;
                    if (i16 != 0 && !tLRPC$messages_Messages.messages.isEmpty()) {
                        i = ((Message) tLRPC$messages_Messages.messages.get(tLRPC$messages_Messages.messages.size() - 1)).id;
                        for (int size = tLRPC$messages_Messages.messages.size() - 1; size >= 0; size--) {
                            Message message = (Message) tLRPC$messages_Messages.messages.get(size);
                            if (message.date > i16) {
                                i = message.id;
                                break;
                            }
                        }
                    }
                    MessagesController.this.processLoadedMessages(tLRPC$messages_Messages, j2, i14, i, i16, false, i17, i18, i19, i20, i21, i22, z4, false, i23, z5, i24);
                }
            }
        }), i5);
    }

    public void loadPeerSettings(User user, Chat chat) {
        if (user != null || chat != null) {
            final long j = user != null ? (long) user.id : (long) (-chat.id);
            if (!this.loadingPeerSettings.containsKey(Long.valueOf(j))) {
                this.loadingPeerSettings.put(Long.valueOf(j), Boolean.valueOf(true));
                FileLog.m13725d("request spam button for " + j);
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                if (sharedPreferences.getInt("spam3_" + j, 0) == 1) {
                    FileLog.m13725d("spam button already hidden for " + j);
                } else if (sharedPreferences.getBoolean("spam_" + j, false)) {
                    r2 = new TLRPC$TL_messages_hideReportSpam();
                    if (user != null) {
                        r2.peer = getInputPeer(user.id);
                    } else if (chat != null) {
                        r2.peer = getInputPeer(-chat.id);
                    }
                    ConnectionsManager.getInstance().sendRequest(r2, new RequestDelegate() {

                        /* renamed from: org.telegram.messenger.MessagesController$20$1 */
                        class C32051 implements Runnable {
                            C32051() {
                            }

                            public void run() {
                                MessagesController.this.loadingPeerSettings.remove(Long.valueOf(j));
                                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                                edit.remove("spam_" + j);
                                edit.putInt("spam3_" + j, 1);
                                edit.commit();
                            }
                        }

                        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new C32051());
                        }
                    });
                } else {
                    r2 = new TLRPC$TL_messages_getPeerSettings();
                    if (user != null) {
                        r2.peer = getInputPeer(user.id);
                    } else if (chat != null) {
                        r2.peer = getInputPeer(-chat.id);
                    }
                    ConnectionsManager.getInstance().sendRequest(r2, new RequestDelegate() {
                        public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    MessagesController.this.loadingPeerSettings.remove(Long.valueOf(j));
                                    if (tLObject != null) {
                                        TLRPC$TL_peerSettings tLRPC$TL_peerSettings = (TLRPC$TL_peerSettings) tLObject;
                                        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                                        if (tLRPC$TL_peerSettings.report_spam) {
                                            FileLog.m13725d("show spam button for " + j);
                                            edit.putInt("spam3_" + j, 2);
                                            edit.commit();
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.peerSettingsDidLoaded, Long.valueOf(j));
                                            return;
                                        }
                                        FileLog.m13725d("don't show spam button for " + j);
                                        edit.putInt("spam3_" + j, 1);
                                        edit.commit();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    public void loadPinnedDialogs(final long j, final ArrayList<Long> arrayList) {
        if (!UserConfig.pinnedDialogsLoaded) {
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_messages_getPinnedDialogs(), new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLObject != null) {
                        int i;
                        int i2;
                        Chat chat;
                        MessageObject messageObject;
                        final TLRPC$TL_messages_peerDialogs tLRPC$TL_messages_peerDialogs = (TLRPC$TL_messages_peerDialogs) tLObject;
                        final TLRPC$TL_messages_dialogs tLRPC$TL_messages_dialogs = new TLRPC$TL_messages_dialogs();
                        tLRPC$TL_messages_dialogs.users.addAll(tLRPC$TL_messages_peerDialogs.users);
                        tLRPC$TL_messages_dialogs.chats.addAll(tLRPC$TL_messages_peerDialogs.chats);
                        tLRPC$TL_messages_dialogs.dialogs.addAll(tLRPC$TL_messages_peerDialogs.dialogs);
                        tLRPC$TL_messages_dialogs.messages.addAll(tLRPC$TL_messages_peerDialogs.messages);
                        final HashMap hashMap = new HashMap();
                        AbstractMap hashMap2 = new HashMap();
                        AbstractMap hashMap3 = new HashMap();
                        final ArrayList arrayList = new ArrayList();
                        for (i = 0; i < tLRPC$TL_messages_peerDialogs.users.size(); i++) {
                            User user = (User) tLRPC$TL_messages_peerDialogs.users.get(i);
                            hashMap2.put(Integer.valueOf(user.id), user);
                        }
                        for (i = 0; i < tLRPC$TL_messages_peerDialogs.chats.size(); i++) {
                            Chat chat2 = (Chat) tLRPC$TL_messages_peerDialogs.chats.get(i);
                            hashMap3.put(Integer.valueOf(chat2.id), chat2);
                        }
                        for (i2 = 0; i2 < tLRPC$TL_messages_peerDialogs.messages.size(); i2++) {
                            Message message = (Message) tLRPC$TL_messages_peerDialogs.messages.get(i2);
                            if (message.to_id.channel_id != 0) {
                                chat = (Chat) hashMap3.get(Integer.valueOf(message.to_id.channel_id));
                                if (chat != null && chat.left) {
                                }
                                messageObject = new MessageObject(message, hashMap2, hashMap3, false);
                                hashMap.put(Long.valueOf(messageObject.getDialogId()), messageObject);
                            } else {
                                if (message.to_id.chat_id != 0) {
                                    chat = (Chat) hashMap3.get(Integer.valueOf(message.to_id.chat_id));
                                    if (!(chat == null || chat.migrated_to == null)) {
                                    }
                                }
                                messageObject = new MessageObject(message, hashMap2, hashMap3, false);
                                hashMap.put(Long.valueOf(messageObject.getDialogId()), messageObject);
                            }
                        }
                        for (i2 = 0; i2 < tLRPC$TL_messages_peerDialogs.dialogs.size(); i2++) {
                            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) tLRPC$TL_messages_peerDialogs.dialogs.get(i2);
                            if (tLRPC$TL_dialog.id == 0) {
                                if (tLRPC$TL_dialog.peer.user_id != 0) {
                                    tLRPC$TL_dialog.id = (long) tLRPC$TL_dialog.peer.user_id;
                                } else if (tLRPC$TL_dialog.peer.chat_id != 0) {
                                    tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.chat_id);
                                } else if (tLRPC$TL_dialog.peer.channel_id != 0) {
                                    tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.channel_id);
                                }
                            }
                            arrayList.add(Long.valueOf(tLRPC$TL_dialog.id));
                            Integer num;
                            if (DialogObject.isChannel(tLRPC$TL_dialog)) {
                                chat = (Chat) hashMap3.get(Integer.valueOf(-((int) tLRPC$TL_dialog.id)));
                                if (chat != null && chat.left) {
                                }
                                if (tLRPC$TL_dialog.last_message_date == 0) {
                                    messageObject = (MessageObject) hashMap.get(Long.valueOf(tLRPC$TL_dialog.id));
                                    if (messageObject != null) {
                                        tLRPC$TL_dialog.last_message_date = messageObject.messageOwner.date;
                                    }
                                }
                                num = (Integer) MessagesController.this.dialogs_read_inbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                                if (num == null) {
                                    num = Integer.valueOf(0);
                                }
                                MessagesController.this.dialogs_read_inbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_inbox_max_id)));
                                num = (Integer) MessagesController.this.dialogs_read_outbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                                if (num == null) {
                                    num = Integer.valueOf(0);
                                }
                                MessagesController.this.dialogs_read_outbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_outbox_max_id)));
                            } else {
                                if (((int) tLRPC$TL_dialog.id) < 0) {
                                    chat = (Chat) hashMap3.get(Integer.valueOf(-((int) tLRPC$TL_dialog.id)));
                                    if (!(chat == null || chat.migrated_to == null)) {
                                    }
                                }
                                if (tLRPC$TL_dialog.last_message_date == 0) {
                                    messageObject = (MessageObject) hashMap.get(Long.valueOf(tLRPC$TL_dialog.id));
                                    if (messageObject != null) {
                                        tLRPC$TL_dialog.last_message_date = messageObject.messageOwner.date;
                                    }
                                }
                                num = (Integer) MessagesController.this.dialogs_read_inbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                                if (num == null) {
                                    num = Integer.valueOf(0);
                                }
                                MessagesController.this.dialogs_read_inbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_inbox_max_id)));
                                num = (Integer) MessagesController.this.dialogs_read_outbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                                if (num == null) {
                                    num = Integer.valueOf(0);
                                }
                                MessagesController.this.dialogs_read_outbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_outbox_max_id)));
                            }
                        }
                        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                            /* renamed from: org.telegram.messenger.MessagesController$115$1$1 */
                            class C31801 implements Runnable {
                                C31801() {
                                }

                                public void run() {
                                    int i;
                                    Object obj;
                                    MessagesController.this.applyDialogsNotificationsSettings(tLRPC$TL_messages_peerDialogs.dialogs);
                                    Object obj2 = null;
                                    HashMap hashMap = new HashMap();
                                    ArrayList arrayList = new ArrayList();
                                    Object obj3 = null;
                                    int i2 = 0;
                                    for (i = 0; i < MessagesController.this.dialogs.size(); i++) {
                                        TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.this.dialogs.get(i);
                                        if (((int) tLRPC$TL_dialog.id) != 0) {
                                            if (!tLRPC$TL_dialog.pinned) {
                                                break;
                                            }
                                            i2 = Math.max(tLRPC$TL_dialog.pinnedNum, i2);
                                            hashMap.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(tLRPC$TL_dialog.pinnedNum));
                                            arrayList.add(Long.valueOf(tLRPC$TL_dialog.id));
                                            tLRPC$TL_dialog.pinned = false;
                                            tLRPC$TL_dialog.pinnedNum = 0;
                                            obj3 = 1;
                                        }
                                    }
                                    ArrayList arrayList2 = new ArrayList();
                                    ArrayList arrayList3 = arrayList != null ? arrayList : arrayList;
                                    if (arrayList3.size() < arrayList.size()) {
                                        arrayList3.add(Long.valueOf(0));
                                    }
                                    while (arrayList.size() < arrayList3.size()) {
                                        arrayList.add(0, Long.valueOf(0));
                                    }
                                    if (tLRPC$TL_messages_peerDialogs.dialogs.isEmpty()) {
                                        obj = obj3;
                                    } else {
                                        MessagesController.this.putUsers(tLRPC$TL_messages_peerDialogs.users, false);
                                        MessagesController.this.putChats(tLRPC$TL_messages_peerDialogs.chats, false);
                                        obj = obj3;
                                        int i3 = 0;
                                        while (i3 < tLRPC$TL_messages_peerDialogs.dialogs.size()) {
                                            Object obj4;
                                            tLRPC$TL_dialog = (TLRPC$TL_dialog) tLRPC$TL_messages_peerDialogs.dialogs.get(i3);
                                            Integer num;
                                            if (j != 0) {
                                                num = (Integer) hashMap.get(Long.valueOf(tLRPC$TL_dialog.id));
                                                if (num != null) {
                                                    tLRPC$TL_dialog.pinnedNum = num.intValue();
                                                }
                                            } else {
                                                i = arrayList.indexOf(Long.valueOf(tLRPC$TL_dialog.id));
                                                int indexOf = arrayList3.indexOf(Long.valueOf(tLRPC$TL_dialog.id));
                                                if (!(i == -1 || indexOf == -1)) {
                                                    if (i == indexOf) {
                                                        num = (Integer) hashMap.get(Long.valueOf(tLRPC$TL_dialog.id));
                                                        if (num != null) {
                                                            tLRPC$TL_dialog.pinnedNum = num.intValue();
                                                        }
                                                    } else {
                                                        num = (Integer) hashMap.get(Long.valueOf(((Long) arrayList.get(indexOf)).longValue()));
                                                        if (num != null) {
                                                            tLRPC$TL_dialog.pinnedNum = num.intValue();
                                                        }
                                                    }
                                                }
                                            }
                                            if (tLRPC$TL_dialog.pinnedNum == 0) {
                                                tLRPC$TL_dialog.pinnedNum = (tLRPC$TL_messages_peerDialogs.dialogs.size() - i3) + i2;
                                            }
                                            arrayList2.add(Long.valueOf(tLRPC$TL_dialog.id));
                                            TLRPC$TL_dialog tLRPC$TL_dialog2 = (TLRPC$TL_dialog) MessagesController.this.dialogs_dict.get(Long.valueOf(tLRPC$TL_dialog.id));
                                            if (tLRPC$TL_dialog2 != null) {
                                                tLRPC$TL_dialog2.pinned = true;
                                                tLRPC$TL_dialog2.pinnedNum = tLRPC$TL_dialog.pinnedNum;
                                                MessagesStorage.getInstance().setDialogPinned(tLRPC$TL_dialog.id, tLRPC$TL_dialog.pinnedNum);
                                                obj4 = obj2;
                                            } else {
                                                MessagesController.this.dialogs_dict.put(Long.valueOf(tLRPC$TL_dialog.id), tLRPC$TL_dialog);
                                                MessageObject messageObject = (MessageObject) hashMap.get(Long.valueOf(tLRPC$TL_dialog.id));
                                                MessagesController.this.dialogMessage.put(Long.valueOf(tLRPC$TL_dialog.id), messageObject);
                                                if (messageObject != null && messageObject.messageOwner.to_id.channel_id == 0) {
                                                    MessagesController.this.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                                                    if (messageObject.messageOwner.random_id != 0) {
                                                        MessagesController.this.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                                                    }
                                                }
                                                i = 1;
                                            }
                                            i3++;
                                            int i4 = 1;
                                            obj2 = obj4;
                                        }
                                    }
                                    if (obj != null) {
                                        if (obj2 != null) {
                                            MessagesController.this.dialogs.clear();
                                            MessagesController.this.dialogs.addAll(MessagesController.this.dialogs_dict.values());
                                        }
                                        MessagesController.this.sortDialogs(null);
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                    }
                                    MessagesStorage.getInstance().unpinAllDialogsExceptNew(arrayList2);
                                    MessagesStorage.getInstance().putDialogs(tLRPC$TL_messages_dialogs, true);
                                    UserConfig.pinnedDialogsLoaded = true;
                                    UserConfig.saveConfig(false);
                                }
                            }

                            public void run() {
                                AndroidUtilities.runOnUIThread(new C31801());
                            }
                        });
                    }
                }
            });
        }
    }

    protected void loadUnknownChannel(final Chat chat, long j) {
        Throwable e;
        if ((chat instanceof TL_channel) && !this.gettingUnknownChannels.containsKey(Integer.valueOf(chat.id))) {
            if (chat.access_hash != 0) {
                TLRPC$TL_inputPeerChannel tLRPC$TL_inputPeerChannel = new TLRPC$TL_inputPeerChannel();
                tLRPC$TL_inputPeerChannel.channel_id = chat.id;
                tLRPC$TL_inputPeerChannel.access_hash = chat.access_hash;
                this.gettingUnknownChannels.put(Integer.valueOf(chat.id), Boolean.valueOf(true));
                TLObject tLRPC$TL_messages_getPeerDialogs = new TLRPC$TL_messages_getPeerDialogs();
                tLRPC$TL_messages_getPeerDialogs.peers.add(tLRPC$TL_inputPeerChannel);
                if (j == 0) {
                    NativeByteBuffer nativeByteBuffer;
                    try {
                        nativeByteBuffer = new NativeByteBuffer(chat.getObjectSize() + 4);
                        try {
                            nativeByteBuffer.writeInt32(0);
                            chat.serializeToStream(nativeByteBuffer);
                        } catch (Exception e2) {
                            e = e2;
                            FileLog.m13728e(e);
                            j = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getPeerDialogs, new RequestDelegate() {
                                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                    if (tLObject != null) {
                                        TLRPC$TL_messages_peerDialogs tLRPC$TL_messages_peerDialogs = (TLRPC$TL_messages_peerDialogs) tLObject;
                                        if (!(tLRPC$TL_messages_peerDialogs.dialogs.isEmpty() || tLRPC$TL_messages_peerDialogs.chats.isEmpty())) {
                                            TLRPC$messages_Dialogs tLRPC$TL_messages_dialogs = new TLRPC$TL_messages_dialogs();
                                            tLRPC$TL_messages_dialogs.dialogs.addAll(tLRPC$TL_messages_peerDialogs.dialogs);
                                            tLRPC$TL_messages_dialogs.messages.addAll(tLRPC$TL_messages_peerDialogs.messages);
                                            tLRPC$TL_messages_dialogs.users.addAll(tLRPC$TL_messages_peerDialogs.users);
                                            tLRPC$TL_messages_dialogs.chats.addAll(tLRPC$TL_messages_peerDialogs.chats);
                                            MessagesController.this.processLoadedDialogs(tLRPC$TL_messages_dialogs, null, 0, 1, 2, false, false, false);
                                        }
                                    }
                                    if (j != 0) {
                                        MessagesStorage.getInstance().removePendingTask(j);
                                    }
                                    MessagesController.this.gettingUnknownChannels.remove(Integer.valueOf(chat.id));
                                }
                            });
                        }
                    } catch (Throwable e3) {
                        Throwable th = e3;
                        nativeByteBuffer = null;
                        e = th;
                        FileLog.m13728e(e);
                        j = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getPeerDialogs, /* anonymous class already generated */);
                    }
                    j = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                }
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getPeerDialogs, /* anonymous class already generated */);
            } else if (j != 0) {
                MessagesStorage.getInstance().removePendingTask(j);
            }
        }
    }

    public void markChannelDialogMessageAsDeleted(ArrayList<Integer> arrayList, int i) {
        MessageObject messageObject = (MessageObject) this.dialogMessage.get(Long.valueOf((long) (-i)));
        if (messageObject != null) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (messageObject.getId() == ((Integer) arrayList.get(i2)).intValue()) {
                    messageObject.deleted = true;
                    return;
                }
            }
        }
    }

    public void markDialogAsRead(long j, int i, int i2, int i3, boolean z, boolean z2) {
        int i4 = (int) j;
        int i5 = (int) (j >> 32);
        TLObject tL_channels_readHistory;
        final long j2;
        if (i4 != 0) {
            if (i2 != 0 && i5 != 1) {
                TLObject tLObject;
                InputPeer inputPeer = getInputPeer(i4);
                long j3 = (long) i2;
                if (inputPeer instanceof TLRPC$TL_inputPeerChannel) {
                    tL_channels_readHistory = new TL_channels_readHistory();
                    tL_channels_readHistory.channel = getInputChannel(-i4);
                    tL_channels_readHistory.max_id = i2;
                    j3 |= ((long) (-i4)) << 32;
                    tLObject = tL_channels_readHistory;
                } else {
                    tL_channels_readHistory = new TLRPC$TL_messages_readHistory();
                    tL_channels_readHistory.peer = inputPeer;
                    tL_channels_readHistory.max_id = i2;
                    tLObject = tL_channels_readHistory;
                }
                Integer num = (Integer) this.dialogs_read_inbox_max.get(Long.valueOf(j));
                if (num == null) {
                    num = Integer.valueOf(0);
                }
                this.dialogs_read_inbox_max.put(Long.valueOf(j), Integer.valueOf(Math.max(num.intValue(), i2)));
                MessagesStorage.getInstance().processPendingRead(j, j3, i3);
                j2 = j;
                final boolean z3 = z2;
                final int i6 = i2;
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                    /* renamed from: org.telegram.messenger.MessagesController$79$1 */
                    class C32401 implements Runnable {
                        C32401() {
                        }

                        public void run() {
                            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.this.dialogs_dict.get(Long.valueOf(j2));
                            if (tLRPC$TL_dialog != null) {
                                tLRPC$TL_dialog.unread_count = 0;
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(256));
                            }
                            if (z3) {
                                NotificationsController.getInstance().processReadMessages(null, j2, 0, i6, true);
                                HashMap hashMap = new HashMap();
                                hashMap.put(Long.valueOf(j2), Integer.valueOf(-1));
                                NotificationsController.getInstance().processDialogsUpdateRead(hashMap);
                                return;
                            }
                            NotificationsController.getInstance().processReadMessages(null, j2, 0, i6, false);
                            hashMap = new HashMap();
                            hashMap.put(Long.valueOf(j2), Integer.valueOf(0));
                            NotificationsController.getInstance().processDialogsUpdateRead(hashMap);
                        }
                    }

                    public void run() {
                        AndroidUtilities.runOnUIThread(new C32401());
                    }
                });
                if (C2885i.m13377b(ApplicationLoader.applicationContext) == 0 && i2 != Integer.MAX_VALUE) {
                    ConnectionsManager.getInstance().sendRequest(tLObject, new RequestDelegate() {
                        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            if (tLRPC$TL_error == null && (tLObject instanceof TLRPC$TL_messages_affectedMessages)) {
                                TLRPC$TL_messages_affectedMessages tLRPC$TL_messages_affectedMessages = (TLRPC$TL_messages_affectedMessages) tLObject;
                                MessagesController.this.processNewDifferenceParams(-1, tLRPC$TL_messages_affectedMessages.pts, -1, tLRPC$TL_messages_affectedMessages.pts_count);
                            }
                        }
                    });
                }
            }
        } else if (i3 != 0) {
            EncryptedChat encryptedChat = getEncryptedChat(Integer.valueOf(i5));
            if (C2885i.m13377b(ApplicationLoader.applicationContext) == 0 && encryptedChat.auth_key != null && encryptedChat.auth_key.length > 1 && (encryptedChat instanceof TLRPC$TL_encryptedChat)) {
                tL_channels_readHistory = new TLRPC$TL_messages_readEncryptedHistory();
                tL_channels_readHistory.peer = new TLRPC$TL_inputEncryptedChat();
                tL_channels_readHistory.peer.chat_id = encryptedChat.id;
                tL_channels_readHistory.peer.access_hash = encryptedChat.access_hash;
                tL_channels_readHistory.max_date = i3;
                ConnectionsManager.getInstance().sendRequest(tL_channels_readHistory, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    }
                });
            }
            MessagesStorage.getInstance().processPendingRead(j, (long) i, i3);
            j2 = j;
            final int i7 = i3;
            final boolean z4 = z2;
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.MessagesController$82$1 */
                class C32421 implements Runnable {
                    C32421() {
                    }

                    public void run() {
                        NotificationsController.getInstance().processReadMessages(null, j2, i7, 0, z4);
                        TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.this.dialogs_dict.get(Long.valueOf(j2));
                        if (tLRPC$TL_dialog != null) {
                            tLRPC$TL_dialog.unread_count = 0;
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(256));
                        }
                        HashMap hashMap = new HashMap();
                        hashMap.put(Long.valueOf(j2), Integer.valueOf(0));
                        NotificationsController.getInstance().processDialogsUpdateRead(hashMap);
                    }
                }

                public void run() {
                    AndroidUtilities.runOnUIThread(new C32421());
                }
            });
            if (encryptedChat.ttl > 0 && z) {
                int max = Math.max(ConnectionsManager.getInstance().getCurrentTime(), i3);
                MessagesStorage.getInstance().createTaskForSecretChat(encryptedChat.id, max, max, 0, null);
            }
        }
    }

    public void markMentionMessageAsRead(int i, int i2, long j) {
        MessagesStorage.getInstance().markMentionMessageAsRead(i, i2, j);
        TLObject tL_channels_readMessageContents;
        if (i2 != 0) {
            tL_channels_readMessageContents = new TL_channels_readMessageContents();
            tL_channels_readMessageContents.channel = getInputChannel(i2);
            if (tL_channels_readMessageContents.channel != null) {
                tL_channels_readMessageContents.id.add(Integer.valueOf(i));
                ConnectionsManager.getInstance().sendRequest(tL_channels_readMessageContents, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    }
                });
                return;
            }
            return;
        }
        tL_channels_readMessageContents = new TLRPC$TL_messages_readMessageContents();
        tL_channels_readMessageContents.id.add(Integer.valueOf(i));
        ConnectionsManager.getInstance().sendRequest(tL_channels_readMessageContents, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error == null) {
                    TLRPC$TL_messages_affectedMessages tLRPC$TL_messages_affectedMessages = (TLRPC$TL_messages_affectedMessages) tLObject;
                    MessagesController.this.processNewDifferenceParams(-1, tLRPC$TL_messages_affectedMessages.pts, -1, tLRPC$TL_messages_affectedMessages.pts_count);
                }
            }
        });
    }

    public void markMessageAsRead(int i, int i2, int i3) {
        if (i != 0 && i3 > 0) {
            int currentTime = ConnectionsManager.getInstance().getCurrentTime();
            MessagesStorage.getInstance().createTaskForMid(i, i2, currentTime, currentTime, i3, false);
            TLObject tL_channels_readMessageContents;
            if (i2 != 0) {
                tL_channels_readMessageContents = new TL_channels_readMessageContents();
                tL_channels_readMessageContents.channel = getInputChannel(i2);
                tL_channels_readMessageContents.id.add(Integer.valueOf(i));
                ConnectionsManager.getInstance().sendRequest(tL_channels_readMessageContents, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    }
                });
                return;
            }
            tL_channels_readMessageContents = new TLRPC$TL_messages_readMessageContents();
            tL_channels_readMessageContents.id.add(Integer.valueOf(i));
            ConnectionsManager.getInstance().sendRequest(tL_channels_readMessageContents, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        TLRPC$TL_messages_affectedMessages tLRPC$TL_messages_affectedMessages = (TLRPC$TL_messages_affectedMessages) tLObject;
                        MessagesController.this.processNewDifferenceParams(-1, tLRPC$TL_messages_affectedMessages.pts, -1, tLRPC$TL_messages_affectedMessages.pts_count);
                    }
                }
            });
        }
    }

    public void markMessageAsRead(long j, long j2, int i) {
        if (j2 != 0 && j != 0) {
            if (i > 0 || i == Integer.MIN_VALUE) {
                int i2 = (int) (j >> 32);
                if (((int) j) == 0) {
                    EncryptedChat encryptedChat = getEncryptedChat(Integer.valueOf(i2));
                    if (encryptedChat != null) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(Long.valueOf(j2));
                        SecretChatHelper.getInstance().sendMessagesReadMessage(encryptedChat, arrayList, null);
                        if (i > 0) {
                            int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                            MessagesStorage.getInstance().createTaskForSecretChat(encryptedChat.id, currentTime, currentTime, 0, arrayList);
                        }
                    }
                }
            }
        }
    }

    public void markMessageContentAsRead(MessageObject messageObject) {
        ArrayList arrayList = new ArrayList();
        long id = (long) messageObject.getId();
        if (messageObject.messageOwner.to_id.channel_id != 0) {
            id |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
        }
        if (messageObject.messageOwner.mentioned) {
            MessagesStorage.getInstance().markMentionMessageAsRead(messageObject.getId(), messageObject.messageOwner.to_id.channel_id, messageObject.getDialogId());
        }
        arrayList.add(Long.valueOf(id));
        MessagesStorage.getInstance().markMessagesContentAsRead(arrayList, 0);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesReadContent, arrayList);
        if (messageObject.getId() < 0) {
            markMessageAsRead(messageObject.getDialogId(), messageObject.messageOwner.random_id, Integer.MIN_VALUE);
        } else if (messageObject.messageOwner.to_id.channel_id != 0) {
            r0 = new TL_channels_readMessageContents();
            r0.channel = getInputChannel(messageObject.messageOwner.to_id.channel_id);
            if (r0.channel != null) {
                r0.id.add(Integer.valueOf(messageObject.getId()));
                ConnectionsManager.getInstance().sendRequest(r0, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    }
                });
            }
        } else {
            r0 = new TLRPC$TL_messages_readMessageContents();
            r0.id.add(Integer.valueOf(messageObject.getId()));
            ConnectionsManager.getInstance().sendRequest(r0, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        TLRPC$TL_messages_affectedMessages tLRPC$TL_messages_affectedMessages = (TLRPC$TL_messages_affectedMessages) tLObject;
                        MessagesController.this.processNewDifferenceParams(-1, tLRPC$TL_messages_affectedMessages.pts, -1, tLRPC$TL_messages_affectedMessages.pts_count);
                    }
                }
            });
        }
    }

    public void markSelectedDialogAsRead() {
        ArrayList selectedDialogs = getSelectedDialogs();
        for (int i = 0; i < selectedDialogs.size(); i++) {
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) selectedDialogs.get(i);
            if (tLRPC$TL_dialog.unread_count > 0) {
                getInstance().markDialogAsRead(tLRPC$TL_dialog.id, 0, Math.max(0, tLRPC$TL_dialog.top_message), tLRPC$TL_dialog.last_message_date, true, false);
            }
        }
    }

    public void muteSelectedDialogs() {
        ArrayList selectedDialogs = getSelectedDialogs();
        for (int i = 0; i < selectedDialogs.size(); i++) {
            long j = ((TLRPC$TL_dialog) selectedDialogs.get(i)).id;
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            edit.putInt("notify2_" + j, 2);
            NotificationsController.getInstance().removeNotificationsForDialog(j);
            MessagesStorage.getInstance().setDialogFlags(j, 1);
            edit.commit();
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) getInstance().dialogs_dict.get(Long.valueOf(j));
            if (tLRPC$TL_dialog != null) {
                tLRPC$TL_dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                tLRPC$TL_dialog.notify_settings.mute_until = Integer.MAX_VALUE;
            }
            NotificationsController.updateServerNotificationsSettings(j);
        }
    }

    public void performLogout(boolean z) {
        ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().clear().commit();
        ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putLong("lastGifLoadTime", 0).putLong("lastStickersLoadTime", 0).putLong("lastStickersLoadTimeMask", 0).putLong("lastStickersLoadTimeFavs", 0).commit();
        ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().remove("gifhint").commit();
        if (z) {
            unregistedPush();
            ConnectionsManager.getInstance().sendRequest(new TL_auth_logOut(), new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    ConnectionsManager.getInstance().cleanup();
                }
            });
        } else {
            ConnectionsManager.getInstance().cleanup();
        }
        UserConfig.clearConfig();
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.appDidLogout, new Object[0]);
        MessagesStorage.getInstance().cleanup(false);
        cleanup();
        ContactsController.getInstance().deleteAllAppAccounts();
    }

    public void pinChannelMessage(Chat chat, int i, boolean z) {
        TLObject tL_channels_updatePinnedMessage = new TL_channels_updatePinnedMessage();
        tL_channels_updatePinnedMessage.channel = getInputChannel(chat);
        tL_channels_updatePinnedMessage.id = i;
        tL_channels_updatePinnedMessage.silent = !z;
        ConnectionsManager.getInstance().sendRequest(tL_channels_updatePinnedMessage, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error == null) {
                    MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                }
            }
        });
    }

    public boolean pinDialog(long j, boolean z, InputPeer inputPeer, long j2) {
        Throwable e;
        int i = (int) j;
        TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) this.dialogs_dict.get(Long.valueOf(j));
        if (tLRPC$TL_dialog == null || tLRPC$TL_dialog.pinned == z) {
            return tLRPC$TL_dialog != null;
        } else {
            tLRPC$TL_dialog.pinned = z;
            if (z) {
                int i2 = 0;
                for (int i3 = 0; i3 < this.dialogs.size(); i3++) {
                    TLRPC$TL_dialog tLRPC$TL_dialog2 = (TLRPC$TL_dialog) this.dialogs.get(i3);
                    if (!tLRPC$TL_dialog2.pinned) {
                        break;
                    }
                    i2 = Math.max(tLRPC$TL_dialog2.pinnedNum, i2);
                }
                tLRPC$TL_dialog.pinnedNum = i2 + 1;
            } else {
                tLRPC$TL_dialog.pinnedNum = 0;
            }
            sortDialogs(null);
            if (!z && this.dialogs.get(this.dialogs.size() - 1) == tLRPC$TL_dialog) {
                this.dialogs.remove(this.dialogs.size() - 1);
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            if (!(i == 0 || j2 == -1)) {
                TLObject tLRPC$TL_messages_toggleDialogPin = new TLRPC$TL_messages_toggleDialogPin();
                tLRPC$TL_messages_toggleDialogPin.pinned = z;
                if (inputPeer == null) {
                    inputPeer = getInputPeer(i);
                }
                if (inputPeer instanceof TLRPC$TL_inputPeerEmpty) {
                    return false;
                }
                tLRPC$TL_messages_toggleDialogPin.peer = inputPeer;
                if (j2 == 0) {
                    NativeByteBuffer nativeByteBuffer;
                    try {
                        nativeByteBuffer = new NativeByteBuffer(inputPeer.getObjectSize() + 16);
                        try {
                            nativeByteBuffer.writeInt32(1);
                            nativeByteBuffer.writeInt64(j);
                            nativeByteBuffer.writeBool(z);
                            inputPeer.serializeToStream(nativeByteBuffer);
                        } catch (Exception e2) {
                            e = e2;
                            FileLog.m13728e(e);
                            j2 = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_toggleDialogPin, new RequestDelegate() {
                                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                    if (j2 != 0) {
                                        MessagesStorage.getInstance().removePendingTask(j2);
                                    }
                                }
                            });
                            MessagesStorage.getInstance().setDialogPinned(j, tLRPC$TL_dialog.pinnedNum);
                            return true;
                        }
                    } catch (Throwable e3) {
                        Throwable th = e3;
                        nativeByteBuffer = null;
                        e = th;
                        FileLog.m13728e(e);
                        j2 = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_toggleDialogPin, /* anonymous class already generated */);
                        MessagesStorage.getInstance().setDialogPinned(j, tLRPC$TL_dialog.pinnedNum);
                        return true;
                    }
                    j2 = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                }
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_toggleDialogPin, /* anonymous class already generated */);
            }
            MessagesStorage.getInstance().setDialogPinned(j, tLRPC$TL_dialog.pinnedNum);
            return true;
        }
    }

    public void processChatInfo(int i, ChatFull chatFull, ArrayList<User> arrayList, boolean z, boolean z2, boolean z3, MessageObject messageObject) {
        if (z && i > 0 && !z3) {
            loadFullChat(i, 0, z2);
        }
        if (chatFull != null) {
            final ArrayList<User> arrayList2 = arrayList;
            final boolean z4 = z;
            final ChatFull chatFull2 = chatFull;
            final boolean z5 = z3;
            final MessageObject messageObject2 = messageObject;
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MessagesController.this.putUsers(arrayList2, z4);
                    if (chatFull2.stickerset != null) {
                        StickersQuery.getGroupStickerSetById(chatFull2.stickerset);
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, chatFull2, Integer.valueOf(0), Boolean.valueOf(z5), messageObject2);
                }
            });
        }
    }

    public void processDialogsUpdate(final TLRPC$messages_Dialogs tLRPC$messages_Dialogs, ArrayList<EncryptedChat> arrayList) {
        Utilities.stageQueue.postRunnable(new Runnable() {
            public void run() {
                int i;
                int i2;
                MessageObject messageObject;
                final HashMap hashMap = new HashMap();
                final HashMap hashMap2 = new HashMap();
                AbstractMap hashMap3 = new HashMap();
                AbstractMap hashMap4 = new HashMap();
                final HashMap hashMap5 = new HashMap();
                for (i = 0; i < tLRPC$messages_Dialogs.users.size(); i++) {
                    User user = (User) tLRPC$messages_Dialogs.users.get(i);
                    hashMap3.put(Integer.valueOf(user.id), user);
                }
                for (i = 0; i < tLRPC$messages_Dialogs.chats.size(); i++) {
                    Chat chat = (Chat) tLRPC$messages_Dialogs.chats.get(i);
                    hashMap4.put(Integer.valueOf(chat.id), chat);
                }
                for (i2 = 0; i2 < tLRPC$messages_Dialogs.messages.size(); i2++) {
                    Chat chat2;
                    Message message = (Message) tLRPC$messages_Dialogs.messages.get(i2);
                    if (message.to_id.channel_id != 0) {
                        chat2 = (Chat) hashMap4.get(Integer.valueOf(message.to_id.channel_id));
                        if (chat2 != null && chat2.left) {
                        }
                        messageObject = new MessageObject(message, hashMap3, hashMap4, false);
                        hashMap2.put(Long.valueOf(messageObject.getDialogId()), messageObject);
                    } else {
                        if (message.to_id.chat_id != 0) {
                            chat2 = (Chat) hashMap4.get(Integer.valueOf(message.to_id.chat_id));
                            if (!(chat2 == null || chat2.migrated_to == null)) {
                            }
                        }
                        messageObject = new MessageObject(message, hashMap3, hashMap4, false);
                        hashMap2.put(Long.valueOf(messageObject.getDialogId()), messageObject);
                    }
                }
                for (i2 = 0; i2 < tLRPC$messages_Dialogs.dialogs.size(); i2++) {
                    TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) tLRPC$messages_Dialogs.dialogs.get(i2);
                    if (tLRPC$TL_dialog.id == 0) {
                        if (tLRPC$TL_dialog.peer.user_id != 0) {
                            tLRPC$TL_dialog.id = (long) tLRPC$TL_dialog.peer.user_id;
                        } else if (tLRPC$TL_dialog.peer.chat_id != 0) {
                            tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.chat_id);
                        } else if (tLRPC$TL_dialog.peer.channel_id != 0) {
                            tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.channel_id);
                        }
                    }
                    Integer num;
                    if (DialogObject.isChannel(tLRPC$TL_dialog)) {
                        chat2 = (Chat) hashMap4.get(Integer.valueOf(-((int) tLRPC$TL_dialog.id)));
                        if (chat2 != null && chat2.left) {
                        }
                        if (tLRPC$TL_dialog.last_message_date == 0) {
                            messageObject = (MessageObject) hashMap2.get(Long.valueOf(tLRPC$TL_dialog.id));
                            if (messageObject != null) {
                                tLRPC$TL_dialog.last_message_date = messageObject.messageOwner.date;
                            }
                        }
                        hashMap.put(Long.valueOf(tLRPC$TL_dialog.id), tLRPC$TL_dialog);
                        hashMap5.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(tLRPC$TL_dialog.unread_count));
                        num = (Integer) MessagesController.this.dialogs_read_inbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                        if (num == null) {
                            num = Integer.valueOf(0);
                        }
                        MessagesController.this.dialogs_read_inbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_inbox_max_id)));
                        num = (Integer) MessagesController.this.dialogs_read_outbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                        if (num == null) {
                            num = Integer.valueOf(0);
                        }
                        MessagesController.this.dialogs_read_outbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_outbox_max_id)));
                    } else {
                        if (((int) tLRPC$TL_dialog.id) < 0) {
                            chat2 = (Chat) hashMap4.get(Integer.valueOf(-((int) tLRPC$TL_dialog.id)));
                            if (!(chat2 == null || chat2.migrated_to == null)) {
                            }
                        }
                        if (tLRPC$TL_dialog.last_message_date == 0) {
                            messageObject = (MessageObject) hashMap2.get(Long.valueOf(tLRPC$TL_dialog.id));
                            if (messageObject != null) {
                                tLRPC$TL_dialog.last_message_date = messageObject.messageOwner.date;
                            }
                        }
                        hashMap.put(Long.valueOf(tLRPC$TL_dialog.id), tLRPC$TL_dialog);
                        hashMap5.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(tLRPC$TL_dialog.unread_count));
                        num = (Integer) MessagesController.this.dialogs_read_inbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                        if (num == null) {
                            num = Integer.valueOf(0);
                        }
                        MessagesController.this.dialogs_read_inbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_inbox_max_id)));
                        num = (Integer) MessagesController.this.dialogs_read_outbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                        if (num == null) {
                            num = Integer.valueOf(0);
                        }
                        MessagesController.this.dialogs_read_outbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_outbox_max_id)));
                    }
                }
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        MessagesController.this.putUsers(tLRPC$messages_Dialogs.users, true);
                        MessagesController.this.putChats(tLRPC$messages_Dialogs.chats, true);
                        for (Entry entry : hashMap.entrySet()) {
                            Long l = (Long) entry.getKey();
                            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) entry.getValue();
                            TLRPC$TL_dialog tLRPC$TL_dialog2 = (TLRPC$TL_dialog) MessagesController.this.dialogs_dict.get(l);
                            if (tLRPC$TL_dialog2 == null) {
                                MessagesController messagesController = MessagesController.this;
                                messagesController.nextDialogsCacheOffset++;
                                MessagesController.this.dialogs_dict.put(l, tLRPC$TL_dialog);
                                MessageObject messageObject = (MessageObject) hashMap2.get(Long.valueOf(tLRPC$TL_dialog.id));
                                MessagesController.this.dialogMessage.put(l, messageObject);
                                if (messageObject != null && messageObject.messageOwner.to_id.channel_id == 0) {
                                    MessagesController.this.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                                    if (messageObject.messageOwner.random_id != 0) {
                                        MessagesController.this.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                                    }
                                }
                            } else {
                                tLRPC$TL_dialog2.unread_count = tLRPC$TL_dialog.unread_count;
                                if (tLRPC$TL_dialog2.unread_mentions_count != tLRPC$TL_dialog.unread_mentions_count) {
                                    tLRPC$TL_dialog2.unread_mentions_count = tLRPC$TL_dialog.unread_mentions_count;
                                    if (MessagesController.this.createdDialogMainThreadIds.contains(Long.valueOf(tLRPC$TL_dialog2.id))) {
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateMentionsCount, Long.valueOf(tLRPC$TL_dialog2.id), Integer.valueOf(tLRPC$TL_dialog2.unread_mentions_count));
                                    }
                                }
                                MessageObject messageObject2 = (MessageObject) MessagesController.this.dialogMessage.get(l);
                                MessageObject messageObject3;
                                if (messageObject2 != null && tLRPC$TL_dialog2.top_message <= 0) {
                                    messageObject3 = (MessageObject) hashMap2.get(Long.valueOf(tLRPC$TL_dialog.id));
                                    if (messageObject2.deleted || messageObject3 == null || messageObject3.messageOwner.date > messageObject2.messageOwner.date) {
                                        MessagesController.this.dialogs_dict.put(l, tLRPC$TL_dialog);
                                        MessagesController.this.dialogMessage.put(l, messageObject3);
                                        if (messageObject3 != null && messageObject3.messageOwner.to_id.channel_id == 0) {
                                            MessagesController.this.dialogMessagesByIds.put(Integer.valueOf(messageObject3.getId()), messageObject3);
                                            if (messageObject3.messageOwner.random_id != 0) {
                                                MessagesController.this.dialogMessagesByRandomIds.put(Long.valueOf(messageObject3.messageOwner.random_id), messageObject3);
                                            }
                                        }
                                        MessagesController.this.dialogMessagesByIds.remove(Integer.valueOf(messageObject2.getId()));
                                        if (messageObject2.messageOwner.random_id != 0) {
                                            MessagesController.this.dialogMessagesByRandomIds.remove(Long.valueOf(messageObject2.messageOwner.random_id));
                                        }
                                    }
                                } else if ((messageObject2 != null && messageObject2.deleted) || tLRPC$TL_dialog.top_message > tLRPC$TL_dialog2.top_message) {
                                    MessagesController.this.dialogs_dict.put(l, tLRPC$TL_dialog);
                                    messageObject3 = (MessageObject) hashMap2.get(Long.valueOf(tLRPC$TL_dialog.id));
                                    MessagesController.this.dialogMessage.put(l, messageObject3);
                                    if (messageObject3 != null && messageObject3.messageOwner.to_id.channel_id == 0) {
                                        MessagesController.this.dialogMessagesByIds.put(Integer.valueOf(messageObject3.getId()), messageObject3);
                                        if (messageObject3.messageOwner.random_id != 0) {
                                            MessagesController.this.dialogMessagesByRandomIds.put(Long.valueOf(messageObject3.messageOwner.random_id), messageObject3);
                                        }
                                    }
                                    if (messageObject2 != null) {
                                        MessagesController.this.dialogMessagesByIds.remove(Integer.valueOf(messageObject2.getId()));
                                        if (messageObject2.messageOwner.random_id != 0) {
                                            MessagesController.this.dialogMessagesByRandomIds.remove(Long.valueOf(messageObject2.messageOwner.random_id));
                                        }
                                    }
                                    if (messageObject3 == null) {
                                        MessagesController.this.checkLastDialogMessage(tLRPC$TL_dialog, null, 0);
                                    }
                                }
                            }
                        }
                        MessagesController.this.dialogs.clear();
                        MessagesController.this.dialogs.addAll(MessagesController.this.dialogs_dict.values());
                        MessagesController.this.sortDialogs(null);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                        NotificationsController.getInstance().processDialogsUpdateRead(hashMap5);
                    }
                });
            }
        });
    }

    public void processDialogsUpdateRead(final HashMap<Long, Integer> hashMap, final HashMap<Long, Integer> hashMap2) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                TLRPC$TL_dialog tLRPC$TL_dialog;
                if (hashMap != null) {
                    for (Entry entry : hashMap.entrySet()) {
                        tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.this.dialogs_dict.get((Long) entry.getKey());
                        if (tLRPC$TL_dialog != null) {
                            tLRPC$TL_dialog.unread_count = ((Integer) entry.getValue()).intValue();
                        }
                    }
                }
                if (hashMap2 != null) {
                    for (Entry entry2 : hashMap2.entrySet()) {
                        tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.this.dialogs_dict.get((Long) entry2.getKey());
                        if (tLRPC$TL_dialog != null) {
                            tLRPC$TL_dialog.unread_mentions_count = ((Integer) entry2.getValue()).intValue();
                            if (MessagesController.this.createdDialogMainThreadIds.contains(Long.valueOf(tLRPC$TL_dialog.id))) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateMentionsCount, Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(tLRPC$TL_dialog.unread_mentions_count));
                            }
                        }
                    }
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(256));
                if (hashMap != null) {
                    NotificationsController.getInstance().processDialogsUpdateRead(hashMap);
                }
            }
        });
    }

    public void processLoadedBlockedUsers(final ArrayList<Integer> arrayList, final ArrayList<User> arrayList2, final boolean z) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (arrayList2 != null) {
                    MessagesController.this.putUsers(arrayList2, z);
                }
                MessagesController.this.loadingBlockedUsers = false;
                if (arrayList.isEmpty() && z && !UserConfig.blockedUsersLoaded) {
                    MessagesController.this.getBlockedUsers(false);
                    return;
                }
                if (!z) {
                    UserConfig.blockedUsersLoaded = true;
                    UserConfig.saveConfig(false);
                }
                MessagesController.this.blockedUsers = arrayList;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.blockedUsersDidLoaded, new Object[0]);
            }
        });
    }

    public void processLoadedChannelAdmins(final ArrayList<Integer> arrayList, final int i, final boolean z) {
        Collections.sort(arrayList);
        if (!z) {
            MessagesStorage.getInstance().putChannelAdmins(i, arrayList);
        }
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController.this.loadingChannelAdmins.delete(i);
                MessagesController.this.channelAdmins.put(Integer.valueOf(i), arrayList);
                if (z) {
                    MessagesController.this.loadChannelAdmins(i, false);
                }
            }
        });
    }

    public void processLoadedDeleteTask(final int i, final ArrayList<Integer> arrayList, int i2) {
        Utilities.stageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesController$26$1 */
            class C32081 implements Runnable {
                C32081() {
                }

                public void run() {
                    MessagesController.this.checkDeletingTask(true);
                }
            }

            public void run() {
                MessagesController.this.gettingNewDeleteTask = false;
                if (arrayList != null) {
                    MessagesController.this.currentDeletingTaskTime = i;
                    MessagesController.this.currentDeletingTaskMids = arrayList;
                    if (MessagesController.this.currentDeleteTaskRunnable != null) {
                        Utilities.stageQueue.cancelRunnable(MessagesController.this.currentDeleteTaskRunnable);
                        MessagesController.this.currentDeleteTaskRunnable = null;
                    }
                    if (!MessagesController.this.checkDeletingTask(false)) {
                        MessagesController.this.currentDeleteTaskRunnable = new C32081();
                        Utilities.stageQueue.postRunnable(MessagesController.this.currentDeleteTaskRunnable, ((long) Math.abs(ConnectionsManager.getInstance().getCurrentTime() - MessagesController.this.currentDeletingTaskTime)) * 1000);
                        return;
                    }
                    return;
                }
                MessagesController.this.currentDeletingTaskTime = 0;
                MessagesController.this.currentDeletingTaskMids = null;
            }
        });
    }

    public void processLoadedDialogs(TLRPC$messages_Dialogs tLRPC$messages_Dialogs, ArrayList<EncryptedChat> arrayList, int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        final int i4 = i3;
        final TLRPC$messages_Dialogs tLRPC$messages_Dialogs2 = tLRPC$messages_Dialogs;
        final boolean z4 = z;
        final int i5 = i2;
        final int i6 = i;
        final boolean z5 = z3;
        final boolean z6 = z2;
        final ArrayList<EncryptedChat> arrayList2 = arrayList;
        Utilities.stageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesController$67$1 */
            class C32321 implements Runnable {
                C32321() {
                }

                public void run() {
                    MessagesController.this.putUsers(tLRPC$messages_Dialogs2.users, true);
                    MessagesController.this.loadingDialogs = false;
                    if (z4) {
                        MessagesController.this.dialogsEndReached = false;
                        MessagesController.this.serverDialogsEndReached = false;
                    } else if (UserConfig.dialogsLoadOffsetId == Integer.MAX_VALUE) {
                        MessagesController.this.dialogsEndReached = true;
                        MessagesController.this.serverDialogsEndReached = true;
                    } else {
                        MessagesController.this.loadDialogs(0, i5, false);
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                }
            }

            public void run() {
                if (!MessagesController.this.firstGettingTask) {
                    MessagesController.this.getNewDeleteTask(null, 0);
                    MessagesController.this.firstGettingTask = true;
                }
                FileLog.m13726e("loaded loadType " + i4 + " count " + tLRPC$messages_Dialogs2.dialogs.size());
                if (i4 == 1 && tLRPC$messages_Dialogs2.dialogs.size() == 0) {
                    AndroidUtilities.runOnUIThread(new C32321());
                    return;
                }
                int i;
                int i2;
                Integer num;
                final HashMap hashMap = new HashMap();
                final HashMap hashMap2 = new HashMap();
                AbstractMap hashMap3 = new HashMap();
                final AbstractMap hashMap4 = new HashMap();
                for (i = 0; i < tLRPC$messages_Dialogs2.users.size(); i++) {
                    User user = (User) tLRPC$messages_Dialogs2.users.get(i);
                    hashMap3.put(Integer.valueOf(user.id), user);
                }
                for (i = 0; i < tLRPC$messages_Dialogs2.chats.size(); i++) {
                    Chat chat = (Chat) tLRPC$messages_Dialogs2.chats.get(i);
                    hashMap4.put(Integer.valueOf(chat.id), chat);
                }
                if (i4 == 1) {
                    MessagesController.this.nextDialogsCacheOffset = i6 + i5;
                }
                Message message = null;
                for (i2 = 0; i2 < tLRPC$messages_Dialogs2.messages.size(); i2++) {
                    Message message2 = (Message) tLRPC$messages_Dialogs2.messages.get(i2);
                    if (message == null || message2.date < message.date) {
                        message = message2;
                    }
                    MessageObject messageObject;
                    if (message2.to_id.channel_id != 0) {
                        chat = (Chat) hashMap4.get(Integer.valueOf(message2.to_id.channel_id));
                        if (chat == null || !chat.left) {
                            if (chat != null && chat.megagroup) {
                                message2.flags |= Integer.MIN_VALUE;
                            }
                            messageObject = new MessageObject(message2, hashMap3, hashMap4, false);
                            hashMap2.put(Long.valueOf(messageObject.getDialogId()), messageObject);
                        }
                    } else {
                        if (message2.to_id.chat_id != 0) {
                            chat = (Chat) hashMap4.get(Integer.valueOf(message2.to_id.chat_id));
                            if (!(chat == null || chat.migrated_to == null)) {
                            }
                        }
                        messageObject = new MessageObject(message2, hashMap3, hashMap4, false);
                        hashMap2.put(Long.valueOf(messageObject.getDialogId()), messageObject);
                    }
                }
                if (!(z5 || z6 || UserConfig.dialogsLoadOffsetId == -1 || i4 != 0)) {
                    if (message == null || message.id == UserConfig.dialogsLoadOffsetId) {
                        UserConfig.dialogsLoadOffsetId = Integer.MAX_VALUE;
                    } else {
                        UserConfig.totalDialogsLoadCount += tLRPC$messages_Dialogs2.dialogs.size();
                        UserConfig.dialogsLoadOffsetId = message.id;
                        UserConfig.dialogsLoadOffsetDate = message.date;
                        if (message.to_id.channel_id != 0) {
                            UserConfig.dialogsLoadOffsetChannelId = message.to_id.channel_id;
                            UserConfig.dialogsLoadOffsetChatId = 0;
                            UserConfig.dialogsLoadOffsetUserId = 0;
                            for (i = 0; i < tLRPC$messages_Dialogs2.chats.size(); i++) {
                                chat = (Chat) tLRPC$messages_Dialogs2.chats.get(i);
                                if (chat.id == UserConfig.dialogsLoadOffsetChannelId) {
                                    UserConfig.dialogsLoadOffsetAccess = chat.access_hash;
                                    break;
                                }
                            }
                        } else if (message.to_id.chat_id != 0) {
                            UserConfig.dialogsLoadOffsetChatId = message.to_id.chat_id;
                            UserConfig.dialogsLoadOffsetChannelId = 0;
                            UserConfig.dialogsLoadOffsetUserId = 0;
                            for (i = 0; i < tLRPC$messages_Dialogs2.chats.size(); i++) {
                                chat = (Chat) tLRPC$messages_Dialogs2.chats.get(i);
                                if (chat.id == UserConfig.dialogsLoadOffsetChatId) {
                                    UserConfig.dialogsLoadOffsetAccess = chat.access_hash;
                                    break;
                                }
                            }
                        } else if (message.to_id.user_id != 0) {
                            UserConfig.dialogsLoadOffsetUserId = message.to_id.user_id;
                            UserConfig.dialogsLoadOffsetChatId = 0;
                            UserConfig.dialogsLoadOffsetChannelId = 0;
                            for (i = 0; i < tLRPC$messages_Dialogs2.users.size(); i++) {
                                user = (User) tLRPC$messages_Dialogs2.users.get(i);
                                if (user.id == UserConfig.dialogsLoadOffsetUserId) {
                                    UserConfig.dialogsLoadOffsetAccess = user.access_hash;
                                    break;
                                }
                            }
                        }
                    }
                    UserConfig.saveConfig(false);
                }
                final ArrayList arrayList = new ArrayList();
                for (i2 = 0; i2 < tLRPC$messages_Dialogs2.dialogs.size(); i2++) {
                    TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) tLRPC$messages_Dialogs2.dialogs.get(i2);
                    if (tLRPC$TL_dialog.id == 0 && tLRPC$TL_dialog.peer != null) {
                        if (tLRPC$TL_dialog.peer.user_id != 0) {
                            tLRPC$TL_dialog.id = (long) tLRPC$TL_dialog.peer.user_id;
                        } else if (tLRPC$TL_dialog.peer.chat_id != 0) {
                            tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.chat_id);
                        } else if (tLRPC$TL_dialog.peer.channel_id != 0) {
                            tLRPC$TL_dialog.id = (long) (-tLRPC$TL_dialog.peer.channel_id);
                        }
                    }
                    if (tLRPC$TL_dialog.id != 0) {
                        if (tLRPC$TL_dialog.last_message_date == 0) {
                            MessageObject messageObject2 = (MessageObject) hashMap2.get(Long.valueOf(tLRPC$TL_dialog.id));
                            if (messageObject2 != null) {
                                tLRPC$TL_dialog.last_message_date = messageObject2.messageOwner.date;
                            }
                        }
                        Object obj = 1;
                        Chat chat2;
                        if (DialogObject.isChannel(tLRPC$TL_dialog)) {
                            chat2 = (Chat) hashMap4.get(Integer.valueOf(-((int) tLRPC$TL_dialog.id)));
                            if (chat2 != null) {
                                if (!chat2.megagroup) {
                                    obj = null;
                                }
                                if (chat2.left) {
                                }
                            }
                            MessagesController.this.channelsPts.put(Integer.valueOf(-((int) tLRPC$TL_dialog.id)), Integer.valueOf(tLRPC$TL_dialog.pts));
                        } else if (((int) tLRPC$TL_dialog.id) < 0) {
                            chat2 = (Chat) hashMap4.get(Integer.valueOf(-((int) tLRPC$TL_dialog.id)));
                            if (!(chat2 == null || chat2.migrated_to == null)) {
                            }
                        }
                        hashMap.put(Long.valueOf(tLRPC$TL_dialog.id), tLRPC$TL_dialog);
                        if (obj != null && i4 == 1 && ((tLRPC$TL_dialog.read_outbox_max_id == 0 || tLRPC$TL_dialog.read_inbox_max_id == 0) && tLRPC$TL_dialog.top_message != 0)) {
                            arrayList.add(tLRPC$TL_dialog);
                        }
                        num = (Integer) MessagesController.this.dialogs_read_inbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                        if (num == null) {
                            num = Integer.valueOf(0);
                        }
                        MessagesController.this.dialogs_read_inbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_inbox_max_id)));
                        num = (Integer) MessagesController.this.dialogs_read_outbox_max.get(Long.valueOf(tLRPC$TL_dialog.id));
                        if (num == null) {
                            num = Integer.valueOf(0);
                        }
                        MessagesController.this.dialogs_read_outbox_max.put(Long.valueOf(tLRPC$TL_dialog.id), Integer.valueOf(Math.max(num.intValue(), tLRPC$TL_dialog.read_outbox_max_id)));
                    }
                }
                if (i4 != 1) {
                    ImageLoader.saveMessagesThumbs(tLRPC$messages_Dialogs2.messages);
                    i2 = 0;
                    while (i2 < tLRPC$messages_Dialogs2.messages.size()) {
                        Message message3 = (Message) tLRPC$messages_Dialogs2.messages.get(i2);
                        if (message3.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                            if (MessagesController.this.hideLeftGroup && message3.action.user_id == message3.from_id) {
                                i2++;
                            } else {
                                User user2 = (User) hashMap3.get(Integer.valueOf(message3.action.user_id));
                                if (user2 != null && user2.bot) {
                                    message3.reply_markup = new TLRPC$TL_replyKeyboardHide();
                                    message3.flags |= 64;
                                }
                            }
                        }
                        if ((message3.action instanceof TLRPC$TL_messageActionChatMigrateTo) || (message3.action instanceof TLRPC$TL_messageActionChannelCreate)) {
                            message3.unread = false;
                            message3.media_unread = false;
                            i2++;
                        } else {
                            ConcurrentHashMap concurrentHashMap = message3.out ? MessagesController.this.dialogs_read_outbox_max : MessagesController.this.dialogs_read_inbox_max;
                            num = (Integer) concurrentHashMap.get(Long.valueOf(message3.dialog_id));
                            if (num == null) {
                                num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message3.out, message3.dialog_id));
                                concurrentHashMap.put(Long.valueOf(message3.dialog_id), num);
                            }
                            message3.unread = num.intValue() < message3.id;
                            i2++;
                        }
                    }
                    MessagesStorage.getInstance().putDialogs(tLRPC$messages_Dialogs2, false);
                }
                if (i4 == 2) {
                    chat = (Chat) tLRPC$messages_Dialogs2.chats.get(0);
                    MessagesController.this.getChannelDifference(chat.id);
                    MessagesController.this.checkChannelInviter(chat.id);
                }
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        boolean z;
                        if (i4 != 1) {
                            MessagesController.this.applyDialogsNotificationsSettings(tLRPC$messages_Dialogs2.dialogs);
                            if (!UserConfig.draftsLoaded) {
                                DraftQuery.loadDrafts();
                            }
                        }
                        MessagesController.this.putUsers(tLRPC$messages_Dialogs2.users, i4 == 1);
                        MessagesController.this.putChats(tLRPC$messages_Dialogs2.chats, i4 == 1);
                        if (arrayList2 != null) {
                            for (int i = 0; i < arrayList2.size(); i++) {
                                EncryptedChat encryptedChat = (EncryptedChat) arrayList2.get(i);
                                if ((encryptedChat instanceof TLRPC$TL_encryptedChat) && AndroidUtilities.getMyLayerVersion(encryptedChat.layer) < 73) {
                                    SecretChatHelper.getInstance().sendNotifyLayerMessage(encryptedChat, null);
                                }
                                MessagesController.this.putEncryptedChat(encryptedChat, true);
                            }
                        }
                        if (!z6) {
                            MessagesController.this.loadingDialogs = false;
                        }
                        if (!z6 || MessagesController.this.dialogs.isEmpty()) {
                            boolean z2 = false;
                        } else {
                            int i2 = ((TLRPC$TL_dialog) MessagesController.this.dialogs.get(MessagesController.this.dialogs.size() - 1)).last_message_date;
                        }
                        boolean z3 = false;
                        for (Entry entry : hashMap.entrySet()) {
                            Long l = (Long) entry.getKey();
                            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) entry.getValue();
                            if (!z6 || r6 == 0 || tLRPC$TL_dialog.last_message_date >= r6) {
                                TLRPC$TL_dialog tLRPC$TL_dialog2 = (TLRPC$TL_dialog) MessagesController.this.dialogs_dict.get(l);
                                if (i4 != 1 && (tLRPC$TL_dialog.draft instanceof TLRPC$TL_draftMessage)) {
                                    DraftQuery.saveDraft(tLRPC$TL_dialog.id, tLRPC$TL_dialog.draft, null, false);
                                }
                                MessageObject messageObject;
                                if (tLRPC$TL_dialog2 == null) {
                                    MessagesController.this.dialogs_dict.put(l, tLRPC$TL_dialog);
                                    messageObject = (MessageObject) hashMap2.get(Long.valueOf(tLRPC$TL_dialog.id));
                                    MessagesController.this.dialogMessage.put(l, messageObject);
                                    if (messageObject != null && messageObject.messageOwner.to_id.channel_id == 0) {
                                        MessagesController.this.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                                        if (messageObject.messageOwner.random_id != 0) {
                                            MessagesController.this.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                                        }
                                    }
                                    z = true;
                                } else {
                                    if (i4 != 1) {
                                        tLRPC$TL_dialog2.notify_settings = tLRPC$TL_dialog.notify_settings;
                                    }
                                    tLRPC$TL_dialog2.pinned = tLRPC$TL_dialog.pinned;
                                    tLRPC$TL_dialog2.pinnedNum = tLRPC$TL_dialog.pinnedNum;
                                    MessageObject messageObject2 = (MessageObject) MessagesController.this.dialogMessage.get(l);
                                    if ((messageObject2 == null || !messageObject2.deleted) && messageObject2 != null && tLRPC$TL_dialog2.top_message <= 0) {
                                        MessageObject messageObject3 = (MessageObject) hashMap2.get(Long.valueOf(tLRPC$TL_dialog.id));
                                        if (messageObject2.deleted || messageObject3 == null || messageObject3.messageOwner.date > messageObject2.messageOwner.date) {
                                            MessagesController.this.dialogs_dict.put(l, tLRPC$TL_dialog);
                                            MessagesController.this.dialogMessage.put(l, messageObject3);
                                            if (messageObject3 != null && messageObject3.messageOwner.to_id.channel_id == 0) {
                                                MessagesController.this.dialogMessagesByIds.put(Integer.valueOf(messageObject3.getId()), messageObject3);
                                                if (!(messageObject3 == null || messageObject3.messageOwner.random_id == 0)) {
                                                    MessagesController.this.dialogMessagesByRandomIds.put(Long.valueOf(messageObject3.messageOwner.random_id), messageObject3);
                                                }
                                            }
                                            MessagesController.this.dialogMessagesByIds.remove(Integer.valueOf(messageObject2.getId()));
                                            if (messageObject2.messageOwner.random_id != 0) {
                                                MessagesController.this.dialogMessagesByRandomIds.remove(Long.valueOf(messageObject2.messageOwner.random_id));
                                            }
                                        }
                                    } else if (tLRPC$TL_dialog.top_message >= tLRPC$TL_dialog2.top_message) {
                                        MessagesController.this.dialogs_dict.put(l, tLRPC$TL_dialog);
                                        messageObject = (MessageObject) hashMap2.get(Long.valueOf(tLRPC$TL_dialog.id));
                                        MessagesController.this.dialogMessage.put(l, messageObject);
                                        if (messageObject != null && messageObject.messageOwner.to_id.channel_id == 0) {
                                            MessagesController.this.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                                            if (!(messageObject == null || messageObject.messageOwner.random_id == 0)) {
                                                MessagesController.this.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                                            }
                                        }
                                        if (messageObject2 != null) {
                                            MessagesController.this.dialogMessagesByIds.remove(Integer.valueOf(messageObject2.getId()));
                                            if (messageObject2.messageOwner.random_id != 0) {
                                                MessagesController.this.dialogMessagesByRandomIds.remove(Long.valueOf(messageObject2.messageOwner.random_id));
                                            }
                                        }
                                        z = z3;
                                    }
                                    z = z3;
                                }
                                z3 = z;
                            }
                        }
                        MessagesController.this.dialogs.clear();
                        MessagesController.this.dialogs.addAll(MessagesController.this.dialogs_dict.values());
                        MessagesController.this.sortDialogs(z6 ? hashMap4 : null);
                        if (!(i4 == 2 || z6)) {
                            MessagesController messagesController = MessagesController.this;
                            z = (tLRPC$messages_Dialogs2.dialogs.size() == 0 || tLRPC$messages_Dialogs2.dialogs.size() != i5) && i4 == 0;
                            messagesController.dialogsEndReached = z;
                            if (!z5) {
                                messagesController = MessagesController.this;
                                z = (tLRPC$messages_Dialogs2.dialogs.size() == 0 || tLRPC$messages_Dialogs2.dialogs.size() != i5) && i4 == 0;
                                messagesController.serverDialogsEndReached = z;
                            }
                        }
                        if (!(z5 || z6 || UserConfig.totalDialogsLoadCount >= ChatActivity.scheduleDownloads || UserConfig.dialogsLoadOffsetId == -1 || UserConfig.dialogsLoadOffsetId == Integer.MAX_VALUE)) {
                            MessagesController.this.loadDialogs(0, 100, false);
                        }
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                        if (z6) {
                            UserConfig.migrateOffsetId = i6;
                            UserConfig.saveConfig(false);
                            MessagesController.this.migratingDialogs = false;
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.needReloadRecentDialogsSearch, new Object[0]);
                        } else {
                            MessagesController.this.generateUpdateMessage();
                            if (!z3 && i4 == 1) {
                                MessagesController.this.loadDialogs(0, i5, false);
                            }
                        }
                        MessagesController.this.migrateDialogs(UserConfig.migrateOffsetId, UserConfig.migrateOffsetDate, UserConfig.migrateOffsetUserId, UserConfig.migrateOffsetChatId, UserConfig.migrateOffsetChannelId, UserConfig.migrateOffsetAccess);
                        if (!arrayList.isEmpty()) {
                            MessagesController.this.reloadDialogsReadValue(arrayList, 0);
                        }
                    }
                });
            }
        });
    }

    public void processLoadedMessages(TLRPC$messages_Messages tLRPC$messages_Messages, long j, int i, int i2, int i3, boolean z, int i4, int i5, int i6, int i7, int i8, int i9, boolean z2, boolean z3, int i10, boolean z4, int i11) {
        final TLRPC$messages_Messages tLRPC$messages_Messages2 = tLRPC$messages_Messages;
        final long j2 = j;
        final boolean z5 = z;
        final int i12 = i;
        final int i13 = i9;
        final boolean z6 = z4;
        final int i14 = i5;
        final int i15 = i2;
        final int i16 = i3;
        final int i17 = i4;
        final int i18 = i6;
        final boolean z7 = z2;
        final int i19 = i10;
        final int i20 = i7;
        final int i21 = i8;
        final int i22 = i11;
        final boolean z8 = z3;
        Utilities.stageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesController$60$1 */
            class C32251 implements Runnable {
                C32251() {
                }

                public void run() {
                    MessagesController messagesController = MessagesController.this;
                    long j = j2;
                    int i = i12;
                    int i2 = (i13 == 2 && z6) ? i14 : i15;
                    messagesController.loadMessages(j, i, i2, i16, false, 0, i17, i13, i18, z7, i19, i14, i20, i21, z6, i22);
                }
            }

            public void run() {
                int i;
                Chat chat;
                boolean z;
                boolean z2;
                boolean z3 = false;
                if (tLRPC$messages_Messages2 instanceof TLRPC$TL_messages_channelMessages) {
                    boolean z4;
                    i = -((int) j2);
                    if (((Integer) MessagesController.this.channelsPts.get(Integer.valueOf(i))) == null && Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(i)).intValue() == 0) {
                        MessagesController.this.channelsPts.put(Integer.valueOf(i), Integer.valueOf(tLRPC$messages_Messages2.pts));
                        if (MessagesController.this.needShortPollChannels.indexOfKey(i) < 0 || MessagesController.this.shortPollChannels.indexOfKey(i) >= 0) {
                            MessagesController.this.getChannelDifference(i);
                            z4 = true;
                        } else {
                            MessagesController.this.getChannelDifference(i, 2, 0, null);
                            z4 = true;
                        }
                    } else {
                        z4 = false;
                    }
                    for (int i2 = 0; i2 < tLRPC$messages_Messages2.chats.size(); i2++) {
                        chat = (Chat) tLRPC$messages_Messages2.chats.get(i2);
                        if (chat.id == i) {
                            z = chat.megagroup;
                            z2 = z4;
                            break;
                        }
                    }
                    z = false;
                    z2 = z4;
                } else {
                    z = false;
                    z2 = false;
                }
                int i3 = (int) j2;
                int i4 = (int) (j2 >> 32);
                if (!z5) {
                    ImageLoader.saveMessagesThumbs(tLRPC$messages_Messages2.messages);
                }
                if (i4 == 1 || i3 == 0 || !z5 || tLRPC$messages_Messages2.messages.size() != 0) {
                    User user;
                    Message message;
                    AbstractMap hashMap = new HashMap();
                    AbstractMap hashMap2 = new HashMap();
                    for (i4 = 0; i4 < tLRPC$messages_Messages2.users.size(); i4++) {
                        user = (User) tLRPC$messages_Messages2.users.get(i4);
                        hashMap.put(Integer.valueOf(user.id), user);
                    }
                    for (i4 = 0; i4 < tLRPC$messages_Messages2.chats.size(); i4++) {
                        chat = (Chat) tLRPC$messages_Messages2.chats.get(i4);
                        hashMap2.put(Integer.valueOf(chat.id), chat);
                    }
                    int size = tLRPC$messages_Messages2.messages.size();
                    if (!z5) {
                        Integer num = (Integer) MessagesController.this.dialogs_read_inbox_max.get(Long.valueOf(j2));
                        if (num == null) {
                            num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(false, j2));
                            MessagesController.this.dialogs_read_inbox_max.put(Long.valueOf(j2), num);
                        }
                        Integer num2 = num;
                        num = (Integer) MessagesController.this.dialogs_read_outbox_max.get(Long.valueOf(j2));
                        if (num == null) {
                            num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(true, j2));
                            MessagesController.this.dialogs_read_outbox_max.put(Long.valueOf(j2), num);
                        }
                        Integer num3 = num;
                        int i5 = 0;
                        while (i5 < size) {
                            message = (Message) tLRPC$messages_Messages2.messages.get(i5);
                            if (z) {
                                message.flags |= Integer.MIN_VALUE;
                            }
                            if (message.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                                if (MessagesController.this.hideLeftGroup && message.action.user_id == message.from_id) {
                                    i5++;
                                } else {
                                    User user2 = (User) hashMap.get(Integer.valueOf(message.action.user_id));
                                    if (user2 != null && user2.bot) {
                                        message.reply_markup = new TLRPC$TL_replyKeyboardHide();
                                        message.flags |= 64;
                                    }
                                }
                            }
                            if ((message.action instanceof TLRPC$TL_messageActionChatMigrateTo) || (message.action instanceof TLRPC$TL_messageActionChannelCreate)) {
                                message.unread = false;
                                message.media_unread = false;
                                i5++;
                            } else {
                                message.unread = (message.out ? num3 : num2).intValue() < message.id;
                                i5++;
                            }
                        }
                        MessagesStorage.getInstance().putMessages(tLRPC$messages_Messages2, j2, i13, i15, z2);
                    }
                    final ArrayList arrayList = new ArrayList();
                    final ArrayList arrayList2 = new ArrayList();
                    final HashMap hashMap3 = new HashMap();
                    for (i = 0; i < size; i++) {
                        message = (Message) tLRPC$messages_Messages2.messages.get(i);
                        message.dialog_id = j2;
                        MessageObject messageObject = new MessageObject(message, hashMap, hashMap2, true);
                        arrayList.add(messageObject);
                        if (z5) {
                            if (message.media instanceof TLRPC$TL_messageMediaUnsupported) {
                                if (message.media.bytes != null && (message.media.bytes.length == 0 || (message.media.bytes.length == 1 && message.media.bytes[0] < (byte) 73))) {
                                    arrayList2.add(Integer.valueOf(message.id));
                                }
                            } else if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
                                if ((message.media.webpage instanceof TLRPC$TL_webPagePending) && message.media.webpage.date <= ConnectionsManager.getInstance().getCurrentTime()) {
                                    arrayList2.add(Integer.valueOf(message.id));
                                } else if (message.media.webpage instanceof TLRPC$TL_webPageUrlPending) {
                                    ArrayList arrayList3 = (ArrayList) hashMap3.get(message.media.webpage.url);
                                    if (arrayList3 == null) {
                                        arrayList3 = new ArrayList();
                                        hashMap3.put(message.media.webpage.url, arrayList3);
                                    }
                                    arrayList3.add(messageObject);
                                }
                            }
                        }
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            int i;
                            MessagesController.this.putUsers(tLRPC$messages_Messages2.users, z5);
                            MessagesController.this.putChats(tLRPC$messages_Messages2.chats, z5);
                            if (z6 && i13 == 2) {
                                int i2 = Integer.MAX_VALUE;
                                for (int i3 = 0; i3 < tLRPC$messages_Messages2.messages.size(); i3++) {
                                    Message message = (Message) tLRPC$messages_Messages2.messages.get(i3);
                                    if (!message.out && message.id > i14 && message.id < i2) {
                                        i2 = message.id;
                                    }
                                }
                                i = i2;
                            } else {
                                i = Integer.MAX_VALUE;
                            }
                            if (i == Integer.MAX_VALUE) {
                                i = i14;
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesDidLoaded, Long.valueOf(j2), Integer.valueOf(i12), arrayList, Boolean.valueOf(z5), Integer.valueOf(i), Integer.valueOf(i18), Integer.valueOf(i20), Integer.valueOf(i21), Integer.valueOf(i13), Boolean.valueOf(z8), Integer.valueOf(i17), Integer.valueOf(i19), Integer.valueOf(i15), Integer.valueOf(i22));
                            if (!arrayList2.isEmpty()) {
                                MessagesController.this.reloadMessages(arrayList2, j2);
                            }
                            if (!hashMap3.isEmpty()) {
                                MessagesController.this.reloadWebPages(j2, hashMap3);
                            }
                        }
                    });
                    try {
                        user = MessagesController.this.getUser(Integer.valueOf((int) j2));
                        if (!(user == null || user.bot)) {
                            z3 = true;
                        }
                        if (!z3 && C3791b.m13992h(ApplicationLoader.applicationContext)) {
                            try {
                                Log.d("LEE", "Debug1946 TShot Iam here, checkurl disable before ");
                                C5319b.a(ApplicationLoader.applicationContext, tLRPC$messages_Messages2, j2);
                                Log.d("LEE", "Debug1946 TShot Iam here, checkurl disable after");
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                AndroidUtilities.runOnUIThread(new C32251());
            }
        });
    }

    public void processLoadedUserPhotos(TLRPC$photos_Photos tLRPC$photos_Photos, int i, int i2, long j, boolean z, int i3) {
        if (!z) {
            MessagesStorage.getInstance().putUsersAndChats(tLRPC$photos_Photos.users, null, true, true);
            MessagesStorage.getInstance().putDialogPhotos(i, tLRPC$photos_Photos);
        } else if (tLRPC$photos_Photos == null || tLRPC$photos_Photos.photos.isEmpty()) {
            loadDialogPhotos(i, i2, j, false, i3);
            return;
        }
        final TLRPC$photos_Photos tLRPC$photos_Photos2 = tLRPC$photos_Photos;
        final boolean z2 = z;
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController.this.putUsers(tLRPC$photos_Photos2.users, z2);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogPhotosLoaded, Integer.valueOf(i4), Integer.valueOf(i5), Boolean.valueOf(z2), Integer.valueOf(i6), tLRPC$photos_Photos2.photos);
            }
        });
    }

    protected void processNewChannelDifferenceParams(int i, int i2, int i3) {
        FileLog.m13726e("processNewChannelDifferenceParams pts = " + i + " pts_count = " + i2 + " channeldId = " + i3);
        if (DialogObject.isChannel((TLRPC$TL_dialog) this.dialogs_dict.get(Long.valueOf((long) (-i3))))) {
            Integer num = (Integer) this.channelsPts.get(Integer.valueOf(i3));
            if (num == null) {
                num = Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(i3));
                if (num.intValue() == 0) {
                    num = Integer.valueOf(1);
                }
                this.channelsPts.put(Integer.valueOf(i3), num);
            }
            if (num.intValue() + i2 == i) {
                FileLog.m13726e("APPLY CHANNEL PTS");
                this.channelsPts.put(Integer.valueOf(i3), Integer.valueOf(i));
                MessagesStorage.getInstance().saveChannelPts(i3, i);
            } else if (num.intValue() != i) {
                Long l = (Long) this.updatesStartWaitTimeChannels.get(Integer.valueOf(i3));
                Boolean bool = (Boolean) this.gettingDifferenceChannels.get(Integer.valueOf(i3));
                if (bool == null) {
                    bool = Boolean.valueOf(false);
                }
                if (bool.booleanValue() || l == null || Math.abs(System.currentTimeMillis() - l.longValue()) <= 1500) {
                    FileLog.m13726e("ADD CHANNEL UPDATE TO QUEUE pts = " + i + " pts_count = " + i2);
                    if (l == null) {
                        this.updatesStartWaitTimeChannels.put(Integer.valueOf(i3), Long.valueOf(System.currentTimeMillis()));
                    }
                    UserActionUpdatesPts userActionUpdatesPts = new UserActionUpdatesPts();
                    userActionUpdatesPts.pts = i;
                    userActionUpdatesPts.pts_count = i2;
                    userActionUpdatesPts.chat_id = i3;
                    ArrayList arrayList = (ArrayList) this.updatesQueueChannels.get(Integer.valueOf(i3));
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        this.updatesQueueChannels.put(Integer.valueOf(i3), arrayList);
                    }
                    arrayList.add(userActionUpdatesPts);
                    return;
                }
                getChannelDifference(i3);
            }
        }
    }

    protected void processNewDifferenceParams(int i, int i2, int i3, int i4) {
        FileLog.m13726e("processNewDifferenceParams seq = " + i + " pts = " + i2 + " date = " + i3 + " pts_count = " + i4);
        if (i2 != -1) {
            if (MessagesStorage.lastPtsValue + i4 == i2) {
                FileLog.m13726e("APPLY PTS");
                MessagesStorage.lastPtsValue = i2;
                MessagesStorage.getInstance().saveDiffParams(MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
            } else if (MessagesStorage.lastPtsValue != i2) {
                if (this.gettingDifference || this.updatesStartWaitTimePts == 0 || Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimePts) <= 1500) {
                    FileLog.m13726e("ADD UPDATE TO QUEUE pts = " + i2 + " pts_count = " + i4);
                    if (this.updatesStartWaitTimePts == 0) {
                        this.updatesStartWaitTimePts = System.currentTimeMillis();
                    }
                    UserActionUpdatesPts userActionUpdatesPts = new UserActionUpdatesPts();
                    userActionUpdatesPts.pts = i2;
                    userActionUpdatesPts.pts_count = i4;
                    this.updatesQueuePts.add(userActionUpdatesPts);
                } else {
                    getDifference();
                }
            }
        }
        if (i == -1) {
            return;
        }
        if (MessagesStorage.lastSeqValue + 1 == i) {
            FileLog.m13726e("APPLY SEQ");
            MessagesStorage.lastSeqValue = i;
            if (i3 != -1) {
                MessagesStorage.lastDateValue = i3;
            }
            MessagesStorage.getInstance().saveDiffParams(MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
        } else if (MessagesStorage.lastSeqValue == i) {
        } else {
            if (this.gettingDifference || this.updatesStartWaitTimeSeq == 0 || Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimeSeq) <= 1500) {
                FileLog.m13726e("ADD UPDATE TO QUEUE seq = " + i);
                if (this.updatesStartWaitTimeSeq == 0) {
                    this.updatesStartWaitTimeSeq = System.currentTimeMillis();
                }
                UserActionUpdatesSeq userActionUpdatesSeq = new UserActionUpdatesSeq();
                userActionUpdatesSeq.seq = i;
                this.updatesQueueSeq.add(userActionUpdatesSeq);
                return;
            }
            getDifference();
        }
    }

    public boolean processUpdateArray(ArrayList<TLRPC$Update> arrayList, ArrayList<User> arrayList2, ArrayList<Chat> arrayList3, boolean z) {
        if (arrayList.isEmpty()) {
            if (!(arrayList2 == null && arrayList3 == null)) {
                final ArrayList<User> arrayList4 = arrayList2;
                final ArrayList<Chat> arrayList5 = arrayList3;
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        MessagesController.this.putUsers(arrayList4, false);
                        MessagesController.this.putChats(arrayList5, false);
                    }
                });
            }
            return true;
        }
        AbstractMap concurrentHashMap;
        int i;
        AbstractMap abstractMap;
        AbstractMap abstractMap2;
        Object obj;
        int i2;
        ArrayList decryptMessage;
        long currentTimeMillis = System.currentTimeMillis();
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        HashMap hashMap3 = new HashMap();
        final SparseArray sparseArray = new SparseArray();
        SparseArray sparseArray2 = new SparseArray();
        SparseArray sparseArray3 = new SparseArray();
        ArrayList arrayList8 = new ArrayList();
        HashMap hashMap4 = new HashMap();
        SparseArray sparseArray4 = new SparseArray();
        SparseArray sparseArray5 = new SparseArray();
        final ArrayList arrayList9 = new ArrayList();
        ArrayList arrayList10 = new ArrayList();
        ArrayList arrayList11 = new ArrayList();
        final ArrayList arrayList12 = new ArrayList();
        Object obj2 = 1;
        if (arrayList2 != null) {
            concurrentHashMap = new ConcurrentHashMap();
            for (i = 0; i < arrayList2.size(); i++) {
                User user = (User) arrayList2.get(i);
                concurrentHashMap.put(Integer.valueOf(user.id), user);
            }
            abstractMap = concurrentHashMap;
        } else {
            obj2 = null;
            abstractMap = this.users;
        }
        if (arrayList3 != null) {
            concurrentHashMap = new ConcurrentHashMap();
            for (i = 0; i < arrayList3.size(); i++) {
                Chat chat = (Chat) arrayList3.get(i);
                concurrentHashMap.put(Integer.valueOf(chat.id), chat);
            }
            abstractMap2 = concurrentHashMap;
            obj = obj2;
        } else {
            abstractMap2 = this.chats;
            obj = null;
        }
        Object obj3 = z ? null : obj;
        if (!(arrayList2 == null && arrayList3 == null)) {
            arrayList4 = arrayList2;
            arrayList5 = arrayList3;
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MessagesController.this.putUsers(arrayList4, false);
                    MessagesController.this.putChats(arrayList5, false);
                }
            });
        }
        int i3 = 0;
        int i4 = 0;
        boolean z2 = false;
        while (i3 < arrayList.size()) {
            int i5;
            boolean z3;
            Integer num;
            ArrayList arrayList13;
            TLRPC$Update tLRPC$Update = (TLRPC$Update) arrayList.get(i3);
            FileLog.m13725d("process update " + tLRPC$Update);
            Message message;
            Message message2;
            int i6;
            int i7;
            int i8;
            MessageEntity messageEntity;
            ConcurrentHashMap concurrentHashMap2;
            if ((tLRPC$Update instanceof TLRPC$TL_updateNewMessage) || (tLRPC$Update instanceof TLRPC$TL_updateNewChannelMessage)) {
                Chat chat2;
                if (tLRPC$Update instanceof TLRPC$TL_updateNewMessage) {
                    message = ((TLRPC$TL_updateNewMessage) tLRPC$Update).message;
                } else {
                    message2 = ((TLRPC$TL_updateNewChannelMessage) tLRPC$Update).message;
                    if (BuildVars.DEBUG_VERSION) {
                        FileLog.m13725d(tLRPC$Update + " channelId = " + message2.to_id.channel_id);
                    }
                    if (!message2.out && message2.from_id == UserConfig.getClientUserId()) {
                        message2.out = true;
                    }
                    message = message2;
                }
                i2 = 0;
                i5 = 0;
                if (message.to_id.channel_id != 0) {
                    i2 = message.to_id.channel_id;
                } else if (message.to_id.chat_id != 0) {
                    i2 = message.to_id.chat_id;
                } else if (message.to_id.user_id != 0) {
                    i5 = message.to_id.user_id;
                }
                if (i2 != 0) {
                    chat = (Chat) abstractMap2.get(Integer.valueOf(i2));
                    if (chat == null) {
                        chat = getChat(Integer.valueOf(i2));
                    }
                    if (chat == null) {
                        chat = MessagesStorage.getInstance().getChatSync(i2);
                        putChat(chat, true);
                    }
                    chat2 = chat;
                } else {
                    chat2 = null;
                }
                if (obj3 == null) {
                    i6 = i4;
                } else if (i2 == 0 || chat2 != null) {
                    int size = message.entities.size() + 3;
                    i7 = 0;
                    i2 = i4;
                    i8 = i5;
                    while (i7 < size) {
                        Object obj4 = null;
                        if (i7 == 0) {
                            i5 = i8;
                        } else if (i7 == 1) {
                            i5 = message.from_id;
                            if (message.post) {
                                obj4 = 1;
                            }
                        } else if (i7 == 2) {
                            i5 = message.fwd_from != null ? message.fwd_from.from_id : 0;
                        } else {
                            messageEntity = (MessageEntity) message.entities.get(i7 - 3);
                            i5 = messageEntity instanceof TLRPC$TL_messageEntityMentionName ? ((TLRPC$TL_messageEntityMentionName) messageEntity).user_id : 0;
                        }
                        if (i5 > 0) {
                            user = (User) abstractMap.get(Integer.valueOf(i5));
                            if (user == null || (r7 == null && user.min)) {
                                user = getUser(Integer.valueOf(i5));
                            }
                            if (user == null || (r7 == null && user.min)) {
                                user = MessagesStorage.getInstance().getUserSync(i5);
                                if (user != null && r7 == null && user.min) {
                                    user = null;
                                }
                                putUser(user, true);
                            }
                            if (user == null) {
                                FileLog.m13725d("not found user " + i5);
                                return false;
                            } else if (i7 == 1 && user.status != null && user.status.expires <= 0) {
                                this.onlinePrivacy.put(Integer.valueOf(i5), Integer.valueOf(ConnectionsManager.getInstance().getCurrentTime()));
                                i8 = i2 | 4;
                                i7++;
                                i2 = i8;
                                i8 = i5;
                            }
                        }
                        i8 = i2;
                        i7++;
                        i2 = i8;
                        i8 = i5;
                    }
                    i6 = i2;
                } else {
                    FileLog.m13725d("not found chat " + i2);
                    return false;
                }
                if (chat2 != null && chat2.megagroup) {
                    message.flags |= Integer.MIN_VALUE;
                }
                if (message.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                    if (this.hideLeftGroup && message.action.user_id == message.from_id) {
                        i = i6;
                        z3 = z2;
                    } else {
                        user = (User) abstractMap.get(Integer.valueOf(message.action.user_id));
                        if (user != null && user.bot) {
                            message.reply_markup = new TLRPC$TL_replyKeyboardHide();
                            message.flags |= 64;
                        } else if (message.from_id == UserConfig.getClientUserId() && message.action.user_id == UserConfig.getClientUserId()) {
                            i = i6;
                            z3 = z2;
                        }
                    }
                }
                arrayList7.add(message);
                ImageLoader.saveMessageThumbs(message);
                i5 = UserConfig.getClientUserId();
                if (message.to_id.chat_id != 0) {
                    message.dialog_id = (long) (-message.to_id.chat_id);
                } else if (message.to_id.channel_id != 0) {
                    message.dialog_id = (long) (-message.to_id.channel_id);
                } else {
                    if (message.to_id.user_id == i5) {
                        message.to_id.user_id = message.from_id;
                    }
                    message.dialog_id = (long) message.to_id.user_id;
                }
                concurrentHashMap2 = message.out ? this.dialogs_read_outbox_max : this.dialogs_read_inbox_max;
                num = (Integer) concurrentHashMap2.get(Long.valueOf(message.dialog_id));
                if (num == null) {
                    num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, message.dialog_id));
                    concurrentHashMap2.put(Long.valueOf(message.dialog_id), num);
                }
                boolean z4 = num.intValue() < message.id && !((chat2 != null && ChatObject.isNotInChat(chat2)) || (message.action instanceof TLRPC$TL_messageActionChatMigrateTo) || (message.action instanceof TLRPC$TL_messageActionChannelCreate));
                message.unread = z4;
                if (message.dialog_id == ((long) i5)) {
                    message.unread = false;
                    message.media_unread = false;
                    message.out = true;
                }
                MessageObject messageObject = new MessageObject(message, abstractMap, abstractMap2, this.createdDialogIds.contains(Long.valueOf(message.dialog_id)));
                i2 = messageObject.type == 11 ? i6 | 8 : messageObject.type == 10 ? i6 | 16 : i6;
                arrayList13 = (ArrayList) hashMap.get(Long.valueOf(message.dialog_id));
                if (arrayList13 == null) {
                    arrayList13 = new ArrayList();
                    hashMap.put(Long.valueOf(message.dialog_id), arrayList13);
                }
                arrayList13.add(messageObject);
                if (!messageObject.isOut() && messageObject.isUnread()) {
                    arrayList6.add(messageObject);
                }
                i = i2;
                z3 = z2;
            } else if (tLRPC$Update instanceof TLRPC$TL_updateReadMessagesContents) {
                for (i = 0; i < tLRPC$Update.messages.size(); i++) {
                    arrayList8.add(Long.valueOf((long) ((Integer) tLRPC$Update.messages.get(i)).intValue()));
                }
                i = i4;
                z3 = z2;
            } else if (tLRPC$Update instanceof TLRPC$TL_updateChannelReadMessagesContents) {
                for (i = 0; i < tLRPC$Update.messages.size(); i++) {
                    arrayList8.add(Long.valueOf(((long) ((Integer) tLRPC$Update.messages.get(i)).intValue()) | (((long) tLRPC$Update.channel_id) << 32)));
                }
                i = i4;
                z3 = z2;
            } else if ((tLRPC$Update instanceof TLRPC$TL_updateReadHistoryInbox) || (tLRPC$Update instanceof TLRPC$TL_updateReadHistoryOutbox)) {
                Peer peer;
                if (tLRPC$Update instanceof TLRPC$TL_updateReadHistoryInbox) {
                    peer = ((TLRPC$TL_updateReadHistoryInbox) tLRPC$Update).peer;
                    if (peer.chat_id != 0) {
                        sparseArray2.put(-peer.chat_id, Long.valueOf((long) tLRPC$Update.max_id));
                        r4 = (long) (-peer.chat_id);
                    } else {
                        sparseArray2.put(peer.user_id, Long.valueOf((long) tLRPC$Update.max_id));
                        r4 = (long) peer.user_id;
                    }
                    r6 = r4;
                    r5 = this.dialogs_read_inbox_max;
                } else {
                    peer = ((TLRPC$TL_updateReadHistoryOutbox) tLRPC$Update).peer;
                    if (peer.chat_id != 0) {
                        sparseArray3.put(-peer.chat_id, Long.valueOf((long) tLRPC$Update.max_id));
                        r4 = (long) (-peer.chat_id);
                    } else {
                        sparseArray3.put(peer.user_id, Long.valueOf((long) tLRPC$Update.max_id));
                        r4 = (long) peer.user_id;
                    }
                    r6 = r4;
                    r5 = this.dialogs_read_outbox_max;
                }
                num = (Integer) r5.get(Long.valueOf(r6));
                if (num == null) {
                    num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(tLRPC$Update instanceof TLRPC$TL_updateReadHistoryOutbox, r6));
                }
                r5.put(Long.valueOf(r6), Integer.valueOf(Math.max(num.intValue(), tLRPC$Update.max_id)));
                i = i4;
                z3 = z2;
            } else if (tLRPC$Update instanceof TLRPC$TL_updateDeleteMessages) {
                arrayList13 = (ArrayList) sparseArray4.get(0);
                if (arrayList13 == null) {
                    arrayList13 = new ArrayList();
                    sparseArray4.put(0, arrayList13);
                }
                arrayList13.addAll(tLRPC$Update.messages);
                i = i4;
                z3 = z2;
            } else {
                ArrayList arrayList14;
                Iterator it;
                PrintingUser printingUser;
                if ((tLRPC$Update instanceof TLRPC$TL_updateUserTyping) || (tLRPC$Update instanceof TLRPC$TL_updateChatUserTyping)) {
                    if (tLRPC$Update.user_id != UserConfig.getClientUserId()) {
                        r4 = (long) (-tLRPC$Update.chat_id);
                        r6 = r4 == 0 ? (long) tLRPC$Update.user_id : r4;
                        arrayList13 = (ArrayList) this.printingUsers.get(Long.valueOf(r6));
                        if (!(tLRPC$Update.action instanceof TLRPC$TL_sendMessageCancelAction)) {
                            if (arrayList13 == null) {
                                arrayList13 = new ArrayList();
                                this.printingUsers.put(Long.valueOf(r6), arrayList13);
                                arrayList14 = arrayList13;
                            } else {
                                arrayList14 = arrayList13;
                            }
                            it = arrayList14.iterator();
                            while (it.hasNext()) {
                                printingUser = (PrintingUser) it.next();
                                if (printingUser.userId == tLRPC$Update.user_id) {
                                    printingUser.lastTime = currentTimeMillis;
                                    if (printingUser.action.getClass() != tLRPC$Update.action.getClass()) {
                                        z2 = true;
                                    }
                                    printingUser.action = tLRPC$Update.action;
                                    obj = 1;
                                    if (obj == null) {
                                        printingUser = new PrintingUser();
                                        printingUser.userId = tLRPC$Update.user_id;
                                        printingUser.lastTime = currentTimeMillis;
                                        printingUser.action = tLRPC$Update.action;
                                        arrayList14.add(printingUser);
                                        z2 = true;
                                    }
                                }
                            }
                            obj = null;
                            if (obj == null) {
                                printingUser = new PrintingUser();
                                printingUser.userId = tLRPC$Update.user_id;
                                printingUser.lastTime = currentTimeMillis;
                                printingUser.action = tLRPC$Update.action;
                                arrayList14.add(printingUser);
                                z2 = true;
                            }
                        } else if (arrayList13 != null) {
                            for (i5 = 0; i5 < arrayList13.size(); i5++) {
                                if (((PrintingUser) arrayList13.get(i5)).userId == tLRPC$Update.user_id) {
                                    arrayList13.remove(i5);
                                    z2 = true;
                                    break;
                                }
                            }
                            if (arrayList13.isEmpty()) {
                                this.printingUsers.remove(Long.valueOf(r6));
                            }
                        }
                        this.onlinePrivacy.put(Integer.valueOf(tLRPC$Update.user_id), Integer.valueOf(ConnectionsManager.getInstance().getCurrentTime()));
                        i = i4;
                        z3 = z2;
                    }
                } else if (tLRPC$Update instanceof TLRPC$TL_updateChatParticipants) {
                    i4 |= 32;
                    arrayList9.add(tLRPC$Update.participants);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateUserStatus) {
                    i4 |= 4;
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateUserName) {
                    try {
                        C2666a.getDatabaseHandler().m12217a(new ContactChangeLog((long) tLRPC$Update.user_id, 2, getInstance().getUser(Integer.valueOf(tLRPC$Update.user_id)).username, (long) ((int) (System.currentTimeMillis() / 1000))));
                    } catch (Exception e) {
                    }
                    i4 |= 1;
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateUserPhoto) {
                    try {
                        C2666a.getDatabaseHandler().m12217a(new ContactChangeLog((long) tLRPC$Update.user_id, 1, tLRPC$Update.photo.photo_id + TtmlNode.ANONYMOUS_REGION_ID, (long) tLRPC$Update.date));
                    } catch (Exception e2) {
                    }
                    i4 |= 2;
                    MessagesStorage.getInstance().clearUserPhotos(tLRPC$Update.user_id);
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateUserPhone) {
                    C2666a.getDatabaseHandler().m12217a(new ContactChangeLog((long) tLRPC$Update.user_id, 3, getInstance().getUser(Integer.valueOf(tLRPC$Update.user_id)).phone, (long) tLRPC$Update.date));
                    i4 |= 1024;
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateContactRegistered) {
                    if (this.enableJoined) {
                        if (abstractMap.containsKey(Integer.valueOf(tLRPC$Update.user_id)) && !MessagesStorage.getInstance().isDialogHasMessages((long) tLRPC$Update.user_id)) {
                            message = new TLRPC$TL_messageService();
                            message.action = new TLRPC$TL_messageActionUserJoined();
                            i8 = UserConfig.getNewMessageId();
                            message.id = i8;
                            message.local_id = i8;
                            UserConfig.saveConfig(false);
                            message.unread = false;
                            message.flags = 256;
                            message.date = tLRPC$Update.date;
                            message.from_id = tLRPC$Update.user_id;
                            message.to_id = new TLRPC$TL_peerUser();
                            message.to_id.user_id = UserConfig.getClientUserId();
                            message.dialog_id = (long) tLRPC$Update.user_id;
                            arrayList7.add(message);
                            r6 = new MessageObject(message, abstractMap, abstractMap2, this.createdDialogIds.contains(Long.valueOf(message.dialog_id)));
                            arrayList13 = (ArrayList) hashMap.get(Long.valueOf(message.dialog_id));
                            if (arrayList13 == null) {
                                arrayList13 = new ArrayList();
                                hashMap.put(Long.valueOf(message.dialog_id), arrayList13);
                            }
                            arrayList13.add(r6);
                            i = i4;
                            z3 = z2;
                        }
                    }
                } else if (tLRPC$Update instanceof TLRPC$TL_updateContactLink) {
                    if (tLRPC$Update.my_link instanceof TLRPC$TL_contactLinkContact) {
                        i8 = arrayList12.indexOf(Integer.valueOf(-tLRPC$Update.user_id));
                        if (i8 != -1) {
                            arrayList12.remove(i8);
                        }
                        if (!arrayList12.contains(Integer.valueOf(tLRPC$Update.user_id))) {
                            arrayList12.add(Integer.valueOf(tLRPC$Update.user_id));
                        }
                        i = i4;
                        z3 = z2;
                    } else {
                        i8 = arrayList12.indexOf(Integer.valueOf(tLRPC$Update.user_id));
                        if (i8 != -1) {
                            arrayList12.remove(i8);
                        }
                        if (!arrayList12.contains(Integer.valueOf(tLRPC$Update.user_id))) {
                            arrayList12.add(Integer.valueOf(-tLRPC$Update.user_id));
                        }
                        i = i4;
                        z3 = z2;
                    }
                } else if (tLRPC$Update instanceof TLRPC$TL_updateNewGeoChatMessage) {
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateNewEncryptedMessage) {
                    decryptMessage = SecretChatHelper.getInstance().decryptMessage(((TLRPC$TL_updateNewEncryptedMessage) tLRPC$Update).message);
                    if (!(decryptMessage == null || decryptMessage.isEmpty())) {
                        r8 = ((long) ((TLRPC$TL_updateNewEncryptedMessage) tLRPC$Update).message.chat_id) << 32;
                        arrayList13 = (ArrayList) hashMap.get(Long.valueOf(r8));
                        if (arrayList13 == null) {
                            arrayList13 = new ArrayList();
                            hashMap.put(Long.valueOf(r8), arrayList13);
                            arrayList14 = arrayList13;
                        } else {
                            arrayList14 = arrayList13;
                        }
                        for (i2 = 0; i2 < decryptMessage.size(); i2++) {
                            message2 = (Message) decryptMessage.get(i2);
                            ImageLoader.saveMessageThumbs(message2);
                            arrayList7.add(message2);
                            MessageObject messageObject2 = new MessageObject(message2, abstractMap, abstractMap2, this.createdDialogIds.contains(Long.valueOf(r8)));
                            arrayList14.add(messageObject2);
                            arrayList6.add(messageObject2);
                        }
                    }
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateEncryptedChatTyping) {
                    EncryptedChat encryptedChatDB = getEncryptedChatDB(tLRPC$Update.chat_id, true);
                    if (encryptedChatDB != null) {
                        tLRPC$Update.user_id = encryptedChatDB.user_id;
                        r6 = ((long) tLRPC$Update.chat_id) << 32;
                        arrayList13 = (ArrayList) this.printingUsers.get(Long.valueOf(r6));
                        if (arrayList13 == null) {
                            arrayList13 = new ArrayList();
                            this.printingUsers.put(Long.valueOf(r6), arrayList13);
                            arrayList14 = arrayList13;
                        } else {
                            arrayList14 = arrayList13;
                        }
                        it = arrayList14.iterator();
                        while (it.hasNext()) {
                            printingUser = (PrintingUser) it.next();
                            if (printingUser.userId == tLRPC$Update.user_id) {
                                printingUser.lastTime = currentTimeMillis;
                                printingUser.action = new TLRPC$TL_sendMessageTypingAction();
                                obj = 1;
                                break;
                            }
                        }
                        obj = null;
                        if (obj == null) {
                            printingUser = new PrintingUser();
                            printingUser.userId = tLRPC$Update.user_id;
                            printingUser.lastTime = currentTimeMillis;
                            printingUser.action = new TLRPC$TL_sendMessageTypingAction();
                            arrayList14.add(printingUser);
                            z2 = true;
                        }
                        this.onlinePrivacy.put(Integer.valueOf(tLRPC$Update.user_id), Integer.valueOf(ConnectionsManager.getInstance().getCurrentTime()));
                    }
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateEncryptedMessagesRead) {
                    hashMap4.put(Integer.valueOf(tLRPC$Update.chat_id), Integer.valueOf(Math.max(tLRPC$Update.max_date, tLRPC$Update.date)));
                    arrayList11.add((TLRPC$TL_updateEncryptedMessagesRead) tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateChatParticipantAdd) {
                    MessagesStorage.getInstance().updateChatInfo(tLRPC$Update.chat_id, tLRPC$Update.user_id, 0, tLRPC$Update.inviter_id, tLRPC$Update.version);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateChatParticipantDelete) {
                    MessagesStorage.getInstance().updateChatInfo(tLRPC$Update.chat_id, tLRPC$Update.user_id, 1, 0, tLRPC$Update.version);
                    i = i4;
                    z3 = z2;
                } else if ((tLRPC$Update instanceof TLRPC$TL_updateDcOptions) || (tLRPC$Update instanceof TLRPC$TL_updateConfig)) {
                    ConnectionsManager.getInstance().updateDcSettings();
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateEncryption) {
                    SecretChatHelper.getInstance().processUpdateEncryption((TLRPC$TL_updateEncryption) tLRPC$Update, abstractMap);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateUserBlocked) {
                    final TLRPC$TL_updateUserBlocked tLRPC$TL_updateUserBlocked = (TLRPC$TL_updateUserBlocked) tLRPC$Update;
                    if (tLRPC$TL_updateUserBlocked.blocked) {
                        arrayList13 = new ArrayList();
                        arrayList13.add(Integer.valueOf(tLRPC$TL_updateUserBlocked.user_id));
                        MessagesStorage.getInstance().putBlockedUsers(arrayList13, false);
                    } else {
                        MessagesStorage.getInstance().deleteBlockedUser(tLRPC$TL_updateUserBlocked.user_id);
                    }
                    MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                        /* renamed from: org.telegram.messenger.MessagesController$126$1 */
                        class C31891 implements Runnable {
                            C31891() {
                            }

                            public void run() {
                                if (!tLRPC$TL_updateUserBlocked.blocked) {
                                    MessagesController.this.blockedUsers.remove(Integer.valueOf(tLRPC$TL_updateUserBlocked.user_id));
                                } else if (!MessagesController.this.blockedUsers.contains(Integer.valueOf(tLRPC$TL_updateUserBlocked.user_id))) {
                                    MessagesController.this.blockedUsers.add(Integer.valueOf(tLRPC$TL_updateUserBlocked.user_id));
                                }
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.blockedUsersDidLoaded, new Object[0]);
                            }
                        }

                        public void run() {
                            AndroidUtilities.runOnUIThread(new C31891());
                        }
                    });
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateNotifySettings) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateServiceNotification) {
                    final TLRPC$TL_updateServiceNotification tLRPC$TL_updateServiceNotification = (TLRPC$TL_updateServiceNotification) tLRPC$Update;
                    if (tLRPC$TL_updateServiceNotification.popup && tLRPC$TL_updateServiceNotification.message != null && tLRPC$TL_updateServiceNotification.message.length() > 0) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.needShowAlert, Integer.valueOf(2), tLRPC$TL_updateServiceNotification.message);
                            }
                        });
                    }
                    if ((tLRPC$TL_updateServiceNotification.flags & 2) != 0) {
                        message = new TLRPC$TL_message();
                        i2 = UserConfig.getNewMessageId();
                        message.id = i2;
                        message.local_id = i2;
                        UserConfig.saveConfig(false);
                        message.unread = true;
                        message.flags = 256;
                        if (tLRPC$TL_updateServiceNotification.inbox_date != 0) {
                            message.date = tLRPC$TL_updateServiceNotification.inbox_date;
                        } else {
                            message.date = (int) (System.currentTimeMillis() / 1000);
                        }
                        message.from_id = 777000;
                        message.to_id = new TLRPC$TL_peerUser();
                        message.to_id.user_id = UserConfig.getClientUserId();
                        message.dialog_id = 777000;
                        if (tLRPC$Update.media != null) {
                            message.media = tLRPC$Update.media;
                            message.flags |= 512;
                        }
                        message.message = tLRPC$TL_updateServiceNotification.message;
                        if (tLRPC$TL_updateServiceNotification.entities != null) {
                            message.entities = tLRPC$TL_updateServiceNotification.entities;
                        }
                        arrayList7.add(message);
                        r6 = new MessageObject(message, abstractMap, abstractMap2, this.createdDialogIds.contains(Long.valueOf(message.dialog_id)));
                        arrayList13 = (ArrayList) hashMap.get(Long.valueOf(message.dialog_id));
                        if (arrayList13 == null) {
                            arrayList13 = new ArrayList();
                            hashMap.put(Long.valueOf(message.dialog_id), arrayList13);
                        }
                        arrayList13.add(r6);
                        arrayList6.add(r6);
                    }
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateDialogPinned) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updatePinnedDialogs) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updatePrivacy) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateWebPage) {
                    hashMap2.put(Long.valueOf(tLRPC$Update.webpage.id), tLRPC$Update.webpage);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateChannelWebPage) {
                    hashMap2.put(Long.valueOf(tLRPC$Update.webpage.id), tLRPC$Update.webpage);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateChannelTooLong) {
                    if (BuildVars.DEBUG_VERSION) {
                        FileLog.m13725d(tLRPC$Update + " channelId = " + tLRPC$Update.channel_id);
                    }
                    num = (Integer) this.channelsPts.get(Integer.valueOf(tLRPC$Update.channel_id));
                    if (num == null) {
                        Integer valueOf = Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(tLRPC$Update.channel_id));
                        if (valueOf.intValue() == 0) {
                            chat = (Chat) abstractMap2.get(Integer.valueOf(tLRPC$Update.channel_id));
                            if (chat == null || chat.min) {
                                chat = getChat(Integer.valueOf(tLRPC$Update.channel_id));
                            }
                            if (chat == null || chat.min) {
                                chat = MessagesStorage.getInstance().getChatSync(tLRPC$Update.channel_id);
                                putChat(chat, true);
                            }
                            if (!(chat == null || chat.min)) {
                                loadUnknownChannel(chat, 0);
                            }
                            num = valueOf;
                        } else {
                            this.channelsPts.put(Integer.valueOf(tLRPC$Update.channel_id), valueOf);
                            num = valueOf;
                        }
                    }
                    if (num.intValue() != 0) {
                        if ((tLRPC$Update.flags & 1) == 0) {
                            getChannelDifference(tLRPC$Update.channel_id);
                        } else if (tLRPC$Update.pts > num.intValue()) {
                            getChannelDifference(tLRPC$Update.channel_id);
                        }
                    }
                    i = i4;
                    z3 = z2;
                } else if ((tLRPC$Update instanceof TLRPC$TL_updateReadChannelInbox) || (tLRPC$Update instanceof TLRPC$TL_updateReadChannelOutbox)) {
                    r6 = (((long) tLRPC$Update.channel_id) << 32) | ((long) tLRPC$Update.max_id);
                    r8 = (long) (-tLRPC$Update.channel_id);
                    ConcurrentHashMap concurrentHashMap3;
                    if (tLRPC$Update instanceof TLRPC$TL_updateReadChannelInbox) {
                        concurrentHashMap3 = this.dialogs_read_inbox_max;
                        sparseArray2.put(-tLRPC$Update.channel_id, Long.valueOf(r6));
                        r5 = concurrentHashMap3;
                    } else {
                        concurrentHashMap3 = this.dialogs_read_outbox_max;
                        sparseArray3.put(-tLRPC$Update.channel_id, Long.valueOf(r6));
                        r5 = concurrentHashMap3;
                    }
                    num = (Integer) r5.get(Long.valueOf(r8));
                    if (num == null) {
                        num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(tLRPC$Update instanceof TLRPC$TL_updateReadChannelOutbox, r8));
                    }
                    r5.put(Long.valueOf(r8), Integer.valueOf(Math.max(num.intValue(), tLRPC$Update.max_id)));
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateDeleteChannelMessages) {
                    if (BuildVars.DEBUG_VERSION) {
                        FileLog.m13725d(tLRPC$Update + " channelId = " + tLRPC$Update.channel_id);
                    }
                    arrayList13 = (ArrayList) sparseArray4.get(tLRPC$Update.channel_id);
                    if (arrayList13 == null) {
                        arrayList13 = new ArrayList();
                        sparseArray4.put(tLRPC$Update.channel_id, arrayList13);
                    }
                    arrayList13.addAll(tLRPC$Update.messages);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateChannel) {
                    if (BuildVars.DEBUG_VERSION) {
                        FileLog.m13725d(tLRPC$Update + " channelId = " + tLRPC$Update.channel_id);
                    }
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateChannelMessageViews) {
                    if (BuildVars.DEBUG_VERSION) {
                        FileLog.m13725d(tLRPC$Update + " channelId = " + tLRPC$Update.channel_id);
                    }
                    TLRPC$TL_updateChannelMessageViews tLRPC$TL_updateChannelMessageViews = (TLRPC$TL_updateChannelMessageViews) tLRPC$Update;
                    SparseIntArray sparseIntArray = (SparseIntArray) sparseArray.get(tLRPC$Update.channel_id);
                    if (sparseIntArray == null) {
                        sparseIntArray = new SparseIntArray();
                        sparseArray.put(tLRPC$Update.channel_id, sparseIntArray);
                    }
                    sparseIntArray.put(tLRPC$TL_updateChannelMessageViews.id, tLRPC$Update.views);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateChatParticipantAdmin) {
                    MessagesStorage.getInstance().updateChatInfo(tLRPC$Update.chat_id, tLRPC$Update.user_id, 2, tLRPC$Update.is_admin ? 1 : 0, tLRPC$Update.version);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateChatAdmins) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateStickerSets) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateStickerSetsOrder) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateNewStickerSet) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateDraftMessage) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateSavedGifs) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if ((tLRPC$Update instanceof TLRPC$TL_updateEditChannelMessage) || (tLRPC$Update instanceof TLRPC$TL_updateEditMessage)) {
                    i6 = UserConfig.getClientUserId();
                    if (tLRPC$Update instanceof TLRPC$TL_updateEditChannelMessage) {
                        message = ((TLRPC$TL_updateEditChannelMessage) tLRPC$Update).message;
                        chat = (Chat) abstractMap2.get(Integer.valueOf(message.to_id.channel_id));
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
                        message2 = ((TLRPC$TL_updateEditMessage) tLRPC$Update).message;
                        if (message2.dialog_id == ((long) i6)) {
                            message2.unread = false;
                            message2.media_unread = false;
                            message2.out = true;
                        }
                        message = message2;
                    }
                    if (!message.out && message.from_id == UserConfig.getClientUserId()) {
                        message.out = true;
                    }
                    if (!z) {
                        i5 = message.entities.size();
                        for (i2 = 0; i2 < i5; i2++) {
                            messageEntity = (MessageEntity) message.entities.get(i2);
                            if (messageEntity instanceof TLRPC$TL_messageEntityMentionName) {
                                i7 = ((TLRPC$TL_messageEntityMentionName) messageEntity).user_id;
                                user = (User) abstractMap.get(Integer.valueOf(i7));
                                if (user == null || user.min) {
                                    user = getUser(Integer.valueOf(i7));
                                }
                                if (user == null || user.min) {
                                    user = MessagesStorage.getInstance().getUserSync(i7);
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
                    concurrentHashMap2 = message.out ? this.dialogs_read_outbox_max : this.dialogs_read_inbox_max;
                    num = (Integer) concurrentHashMap2.get(Long.valueOf(message.dialog_id));
                    if (num == null) {
                        num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, message.dialog_id));
                        concurrentHashMap2.put(Long.valueOf(message.dialog_id), num);
                    }
                    message.unread = num.intValue() < message.id;
                    if (message.dialog_id == ((long) i6)) {
                        message.out = true;
                        message.unread = false;
                        message.media_unread = false;
                    }
                    if (message.out && (message.message == null || message.message.length() == 0)) {
                        message.message = "-1";
                        message.attachPath = TtmlNode.ANONYMOUS_REGION_ID;
                    }
                    ImageLoader.saveMessageThumbs(message);
                    r6 = new MessageObject(message, abstractMap, abstractMap2, this.createdDialogIds.contains(Long.valueOf(message.dialog_id)));
                    arrayList13 = (ArrayList) hashMap3.get(Long.valueOf(message.dialog_id));
                    if (arrayList13 == null) {
                        arrayList13 = new ArrayList();
                        hashMap3.put(Long.valueOf(message.dialog_id), arrayList13);
                    }
                    arrayList13.add(r6);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateChannelPinnedMessage) {
                    if (BuildVars.DEBUG_VERSION) {
                        FileLog.m13725d(tLRPC$Update + " channelId = " + tLRPC$Update.channel_id);
                    }
                    MessagesStorage.getInstance().updateChannelPinnedMessage(tLRPC$Update.channel_id, ((TLRPC$TL_updateChannelPinnedMessage) tLRPC$Update).id);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateReadFeaturedStickers) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updatePhoneCall) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateLangPack) {
                    LocaleController.getInstance().saveRemoteLocaleStrings(tLRPC$Update.difference);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateLangPackTooLong) {
                    LocaleController.getInstance().reloadCurrentRemoteLocale();
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateFavedStickers) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateContactsReset) {
                    arrayList10.add(tLRPC$Update);
                    i = i4;
                    z3 = z2;
                } else if (tLRPC$Update instanceof TLRPC$TL_updateChannelAvailableMessages) {
                    num = (Integer) sparseArray5.get(tLRPC$Update.channel_id);
                    if (num == null || num.intValue() < tLRPC$Update.available_min_id) {
                        sparseArray5.put(tLRPC$Update.channel_id, Integer.valueOf(tLRPC$Update.available_min_id));
                    }
                }
                i = i4;
                z3 = z2;
            }
            i3++;
            i4 = i;
            z2 = z3;
        }
        if (!hashMap.isEmpty()) {
            for (Entry entry : hashMap.entrySet()) {
                if (updatePrintingUsersWithNewMessages(((Long) entry.getKey()).longValue(), (ArrayList) entry.getValue())) {
                    z2 = true;
                }
            }
        }
        final boolean z5 = z2;
        if (z5) {
            updatePrintingStrings();
        }
        if (!arrayList12.isEmpty()) {
            ContactsController.getInstance().processContactsUpdates(arrayList12, abstractMap);
        }
        if (!arrayList6.isEmpty()) {
            final ArrayList arrayList15 = arrayList6;
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.MessagesController$128$1 */
                class C31901 implements Runnable {
                    C31901() {
                    }

                    public void run() {
                        NotificationsController.getInstance().processNewMessages(arrayList15, true);
                    }
                }

                public void run() {
                    AndroidUtilities.runOnUIThread(new C31901());
                }
            });
        }
        if (!arrayList7.isEmpty()) {
            StatsController.getInstance().incrementReceivedItemsCount(ConnectionsManager.getCurrentNetworkType(), 1, arrayList7.size());
            MessagesStorage.getInstance().putMessages(arrayList7, true, true, false, MediaController.getInstance().getAutodownloadMask());
        }
        if (!hashMap3.isEmpty()) {
            for (Entry entry2 : hashMap3.entrySet()) {
                TLRPC$messages_Messages tLRPC$TL_messages_messages = new TLRPC$TL_messages_messages();
                arrayList13 = (ArrayList) entry2.getValue();
                for (i5 = 0; i5 < arrayList13.size(); i5++) {
                    tLRPC$TL_messages_messages.messages.add(((MessageObject) arrayList13.get(i5)).messageOwner);
                }
                MessagesStorage.getInstance().putMessages(tLRPC$TL_messages_messages, ((Long) entry2.getKey()).longValue(), -2, 0, false);
            }
        }
        if (sparseArray.size() != 0) {
            MessagesStorage.getInstance().putChannelViews(sparseArray, true);
        }
        i2 = i4;
        decryptMessage = arrayList10;
        final HashMap hashMap5 = hashMap2;
        final HashMap hashMap6 = hashMap;
        final HashMap hashMap7 = hashMap3;
        AndroidUtilities.runOnUIThread(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesController$129$3 */
            class C31933 implements RequestDelegate {
                C31933() {
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLObject != null) {
                        MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                    }
                }
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r18 = this;
                r0 = r18;
                r12 = r6;
                r11 = 0;
                r0 = r18;
                r2 = r7;
                r2 = r2.isEmpty();
                if (r2 != 0) goto L_0x05f6;
            L_0x000f:
                r14 = new java.util.ArrayList;
                r14.<init>();
                r15 = new java.util.ArrayList;
                r15.<init>();
                r10 = 0;
                r2 = 0;
                r13 = r2;
            L_0x001c:
                r0 = r18;
                r2 = r7;
                r2 = r2.size();
                if (r13 >= r2) goto L_0x05d1;
            L_0x0026:
                r0 = r18;
                r2 = r7;
                r2 = r2.get(r13);
                r3 = r2;
                r3 = (org.telegram.tgnet.TLRPC$Update) r3;
                r2 = new org.telegram.tgnet.TLRPC$TL_user;
                r2.<init>();
                r4 = r3.user_id;
                r2.id = r4;
                r0 = r18;
                r4 = org.telegram.messenger.MessagesController.this;
                r5 = r3.user_id;
                r5 = java.lang.Integer.valueOf(r5);
                r4 = r4.getUser(r5);
                r5 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updatePrivacy;
                if (r5 == 0) goto L_0x008a;
            L_0x004c:
                r2 = r3.key;
                r2 = r2 instanceof org.telegram.tgnet.TLRPC$TL_privacyKeyStatusTimestamp;
                if (r2 == 0) goto L_0x0064;
            L_0x0052:
                r2 = org.telegram.messenger.ContactsController.getInstance();
                r3 = r3.rules;
                r4 = 0;
                r2.setPrivacyRules(r3, r4);
                r2 = r11;
                r3 = r12;
            L_0x005e:
                r4 = r13 + 1;
                r13 = r4;
                r11 = r2;
                r12 = r3;
                goto L_0x001c;
            L_0x0064:
                r2 = r3.key;
                r2 = r2 instanceof org.telegram.tgnet.TLRPC$TL_privacyKeyChatInvite;
                if (r2 == 0) goto L_0x0077;
            L_0x006a:
                r2 = org.telegram.messenger.ContactsController.getInstance();
                r3 = r3.rules;
                r4 = 1;
                r2.setPrivacyRules(r3, r4);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x0077:
                r2 = r3.key;
                r2 = r2 instanceof org.telegram.tgnet.TLRPC$TL_privacyKeyPhoneCall;
                if (r2 == 0) goto L_0x05cd;
            L_0x007d:
                r2 = org.telegram.messenger.ContactsController.getInstance();
                r3 = r3.rules;
                r4 = 2;
                r2.setPrivacyRules(r3, r4);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x008a:
                r5 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateUserStatus;
                if (r5 == 0) goto L_0x00db;
            L_0x008e:
                r5 = r3.status;
                r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_userStatusRecently;
                if (r5 == 0) goto L_0x00c1;
            L_0x0094:
                r5 = r3.status;
                r6 = -100;
                r5.expires = r6;
            L_0x009a:
                if (r4 == 0) goto L_0x00a4;
            L_0x009c:
                r5 = r3.user_id;
                r4.id = r5;
                r5 = r3.status;
                r4.status = r5;
            L_0x00a4:
                r4 = r3.status;
                r2.status = r4;
                r15.add(r2);
                r2 = r3.user_id;
                r4 = org.telegram.messenger.UserConfig.getClientUserId();
                if (r2 != r4) goto L_0x05cd;
            L_0x00b3:
                r2 = org.telegram.messenger.NotificationsController.getInstance();
                r3 = r3.status;
                r3 = r3.expires;
                r2.setLastOnlineFromOtherDevice(r3);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x00c1:
                r5 = r3.status;
                r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_userStatusLastWeek;
                if (r5 == 0) goto L_0x00ce;
            L_0x00c7:
                r5 = r3.status;
                r6 = -101; // 0xffffffffffffff9b float:NaN double:NaN;
                r5.expires = r6;
                goto L_0x009a;
            L_0x00ce:
                r5 = r3.status;
                r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_userStatusLastMonth;
                if (r5 == 0) goto L_0x009a;
            L_0x00d4:
                r5 = r3.status;
                r6 = -102; // 0xffffffffffffff9a float:NaN double:NaN;
                r5.expires = r6;
                goto L_0x009a;
            L_0x00db:
                r5 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateUserName;
                if (r5 == 0) goto L_0x0138;
            L_0x00df:
                if (r4 == 0) goto L_0x0125;
            L_0x00e1:
                r5 = org.telegram.messenger.UserObject.isContact(r4);
                if (r5 != 0) goto L_0x00ef;
            L_0x00e7:
                r5 = r3.first_name;
                r4.first_name = r5;
                r5 = r3.last_name;
                r4.last_name = r5;
            L_0x00ef:
                r5 = r4.username;
                if (r5 == 0) goto L_0x0108;
            L_0x00f3:
                r5 = r4.username;
                r5 = r5.length();
                if (r5 <= 0) goto L_0x0108;
            L_0x00fb:
                r0 = r18;
                r5 = org.telegram.messenger.MessagesController.this;
                r5 = r5.objectsByUsernames;
                r6 = r4.username;
                r5.remove(r6);
            L_0x0108:
                r5 = r3.username;
                if (r5 == 0) goto L_0x0121;
            L_0x010c:
                r5 = r3.username;
                r5 = r5.length();
                if (r5 <= 0) goto L_0x0121;
            L_0x0114:
                r0 = r18;
                r5 = org.telegram.messenger.MessagesController.this;
                r5 = r5.objectsByUsernames;
                r6 = r3.username;
                r5.put(r6, r4);
            L_0x0121:
                r5 = r3.username;
                r4.username = r5;
            L_0x0125:
                r4 = r3.first_name;
                r2.first_name = r4;
                r4 = r3.last_name;
                r2.last_name = r4;
                r3 = r3.username;
                r2.username = r3;
                r14.add(r2);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x0138:
                r5 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateDialogPinned;
                if (r5 == 0) goto L_0x0180;
            L_0x013c:
                r2 = r3;
                r2 = (org.telegram.tgnet.TLRPC$TL_updateDialogPinned) r2;
                r3 = r2.peer;
                r3 = r3 instanceof org.telegram.tgnet.TLRPC$TL_peerUser;
                if (r3 == 0) goto L_0x016c;
            L_0x0145:
                r3 = r2.peer;
                r3 = r3.user_id;
                r4 = (long) r3;
            L_0x014a:
                r0 = r18;
                r3 = org.telegram.messenger.MessagesController.this;
                r6 = r2.pinned;
                r7 = 0;
                r8 = -1;
                r2 = r3.pinDialog(r4, r6, r7, r8);
                if (r2 != 0) goto L_0x0168;
            L_0x0159:
                r2 = 0;
                org.telegram.messenger.UserConfig.pinnedDialogsLoaded = r2;
                r2 = 0;
                org.telegram.messenger.UserConfig.saveConfig(r2);
                r0 = r18;
                r2 = org.telegram.messenger.MessagesController.this;
                r3 = 0;
                r2.loadPinnedDialogs(r4, r3);
            L_0x0168:
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x016c:
                r3 = r2.peer;
                r3 = r3 instanceof org.telegram.tgnet.TLRPC$TL_peerChat;
                if (r3 == 0) goto L_0x0179;
            L_0x0172:
                r3 = r2.peer;
                r3 = r3.chat_id;
                r3 = -r3;
                r4 = (long) r3;
                goto L_0x014a;
            L_0x0179:
                r3 = r2.peer;
                r3 = r3.channel_id;
                r3 = -r3;
                r4 = (long) r3;
                goto L_0x014a;
            L_0x0180:
                r5 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updatePinnedDialogs;
                if (r5 == 0) goto L_0x01d8;
            L_0x0184:
                r2 = 0;
                org.telegram.messenger.UserConfig.pinnedDialogsLoaded = r2;
                r2 = 0;
                org.telegram.messenger.UserConfig.saveConfig(r2);
                r2 = r3.flags;
                r2 = r2 & 1;
                if (r2 == 0) goto L_0x01d6;
            L_0x0191:
                r6 = new java.util.ArrayList;
                r6.<init>();
                r3 = (org.telegram.tgnet.TLRPC$TL_updatePinnedDialogs) r3;
                r7 = r3.order;
                r2 = 0;
                r3 = r2;
            L_0x019c:
                r2 = r7.size();
                if (r3 >= r2) goto L_0x01c8;
            L_0x01a2:
                r2 = r7.get(r3);
                r2 = (org.telegram.tgnet.TLRPC.Peer) r2;
                r4 = r2.user_id;
                if (r4 == 0) goto L_0x01ba;
            L_0x01ac:
                r2 = r2.user_id;
                r4 = (long) r2;
            L_0x01af:
                r2 = java.lang.Long.valueOf(r4);
                r6.add(r2);
                r2 = r3 + 1;
                r3 = r2;
                goto L_0x019c;
            L_0x01ba:
                r4 = r2.chat_id;
                if (r4 == 0) goto L_0x01c3;
            L_0x01be:
                r2 = r2.chat_id;
                r2 = -r2;
                r4 = (long) r2;
                goto L_0x01af;
            L_0x01c3:
                r2 = r2.channel_id;
                r2 = -r2;
                r4 = (long) r2;
                goto L_0x01af;
            L_0x01c8:
                r2 = r6;
            L_0x01c9:
                r0 = r18;
                r3 = org.telegram.messenger.MessagesController.this;
                r4 = 0;
                r3.loadPinnedDialogs(r4, r2);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x01d6:
                r2 = 0;
                goto L_0x01c9;
            L_0x01d8:
                r5 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateUserPhoto;
                if (r5 == 0) goto L_0x01ed;
            L_0x01dc:
                if (r4 == 0) goto L_0x01e2;
            L_0x01de:
                r5 = r3.photo;
                r4.photo = r5;
            L_0x01e2:
                r3 = r3.photo;
                r2.photo = r3;
                r14.add(r2);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x01ed:
                r5 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateUserPhone;
                if (r5 == 0) goto L_0x020e;
            L_0x01f1:
                if (r4 == 0) goto L_0x0203;
            L_0x01f3:
                r5 = r3.phone;
                r4.phone = r5;
                r5 = org.telegram.messenger.Utilities.phoneBookQueue;
                r6 = new org.telegram.messenger.MessagesController$129$1;
                r0 = r18;
                r6.<init>(r4);
                r5.postRunnable(r6);
            L_0x0203:
                r3 = r3.phone;
                r2.phone = r3;
                r14.add(r2);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x020e:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateNotifySettings;
                if (r2 == 0) goto L_0x034b;
            L_0x0212:
                r2 = r3;
                r2 = (org.telegram.tgnet.TLRPC$TL_updateNotifySettings) r2;
                r4 = r3.notify_settings;
                r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
                if (r4 == 0) goto L_0x0844;
            L_0x021b:
                r4 = r2.peer;
                r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_notifyPeer;
                if (r4 == 0) goto L_0x0844;
            L_0x0221:
                if (r10 != 0) goto L_0x0841;
            L_0x0223:
                r4 = org.telegram.messenger.ApplicationLoader.applicationContext;
                r5 = "Notifications";
                r6 = 0;
                r4 = r4.getSharedPreferences(r5, r6);
                r4 = r4.edit();
            L_0x0231:
                r5 = r2.peer;
                r5 = r5.peer;
                r5 = r5.user_id;
                if (r5 == 0) goto L_0x02c7;
            L_0x0239:
                r2 = r2.peer;
                r2 = r2.peer;
                r2 = r2.user_id;
                r6 = (long) r2;
            L_0x0240:
                r0 = r18;
                r2 = org.telegram.messenger.MessagesController.this;
                r2 = r2.dialogs_dict;
                r5 = java.lang.Long.valueOf(r6);
                r2 = r2.get(r5);
                r2 = (org.telegram.tgnet.TLRPC$TL_dialog) r2;
                if (r2 == 0) goto L_0x0256;
            L_0x0252:
                r5 = r3.notify_settings;
                r2.notify_settings = r5;
            L_0x0256:
                r5 = new java.lang.StringBuilder;
                r5.<init>();
                r8 = "silent_";
                r5 = r5.append(r8);
                r5 = r5.append(r6);
                r5 = r5.toString();
                r8 = r3.notify_settings;
                r8 = r8.silent;
                r4.putBoolean(r5, r8);
                r5 = org.telegram.tgnet.ConnectionsManager.getInstance();
                r8 = r5.getCurrentTime();
                r5 = r3.notify_settings;
                r5 = r5.mute_until;
                if (r5 <= r8) goto L_0x0322;
            L_0x027f:
                r5 = 0;
                r9 = r3.notify_settings;
                r9 = r9.mute_until;
                r10 = 31536000; // 0x1e13380 float:8.2725845E-38 double:1.5580854E-316;
                r8 = r8 + r10;
                if (r9 <= r8) goto L_0x02e3;
            L_0x028a:
                r3 = new java.lang.StringBuilder;
                r3.<init>();
                r8 = "notify2_";
                r3 = r3.append(r8);
                r3 = r3.append(r6);
                r3 = r3.toString();
                r8 = 2;
                r4.putInt(r3, r8);
                if (r2 == 0) goto L_0x0320;
            L_0x02a4:
                r2 = r2.notify_settings;
                r3 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
                r2.mute_until = r3;
                r2 = r5;
            L_0x02ac:
                r3 = org.telegram.messenger.MessagesStorage.getInstance();
                r8 = (long) r2;
                r2 = 32;
                r8 = r8 << r2;
                r16 = 1;
                r8 = r8 | r16;
                r3.setDialogFlags(r6, r8);
                r2 = org.telegram.messenger.NotificationsController.getInstance();
                r2.removeNotificationsForDialog(r6);
            L_0x02c2:
                r10 = r4;
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x02c7:
                r5 = r2.peer;
                r5 = r5.peer;
                r5 = r5.chat_id;
                if (r5 == 0) goto L_0x02d9;
            L_0x02cf:
                r2 = r2.peer;
                r2 = r2.peer;
                r2 = r2.chat_id;
                r2 = -r2;
                r6 = (long) r2;
                goto L_0x0240;
            L_0x02d9:
                r2 = r2.peer;
                r2 = r2.peer;
                r2 = r2.channel_id;
                r2 = -r2;
                r6 = (long) r2;
                goto L_0x0240;
            L_0x02e3:
                r5 = r3.notify_settings;
                r5 = r5.mute_until;
                r8 = new java.lang.StringBuilder;
                r8.<init>();
                r9 = "notify2_";
                r8 = r8.append(r9);
                r8 = r8.append(r6);
                r8 = r8.toString();
                r9 = 3;
                r4.putInt(r8, r9);
                r8 = new java.lang.StringBuilder;
                r8.<init>();
                r9 = "notifyuntil_";
                r8 = r8.append(r9);
                r8 = r8.append(r6);
                r8 = r8.toString();
                r3 = r3.notify_settings;
                r3 = r3.mute_until;
                r4.putInt(r8, r3);
                if (r2 == 0) goto L_0x0320;
            L_0x031c:
                r2 = r2.notify_settings;
                r2.mute_until = r5;
            L_0x0320:
                r2 = r5;
                goto L_0x02ac;
            L_0x0322:
                if (r2 == 0) goto L_0x0329;
            L_0x0324:
                r2 = r2.notify_settings;
                r3 = 0;
                r2.mute_until = r3;
            L_0x0329:
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r3 = "notify2_";
                r2 = r2.append(r3);
                r2 = r2.append(r6);
                r2 = r2.toString();
                r4.remove(r2);
                r2 = org.telegram.messenger.MessagesStorage.getInstance();
                r8 = 0;
                r2.setDialogFlags(r6, r8);
                goto L_0x02c2;
            L_0x034b:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateChannel;
                if (r2 == 0) goto L_0x03ab;
            L_0x034f:
                r0 = r18;
                r2 = org.telegram.messenger.MessagesController.this;
                r2 = r2.dialogs_dict;
                r4 = r3.channel_id;
                r4 = (long) r4;
                r4 = -r4;
                r4 = java.lang.Long.valueOf(r4);
                r2 = r2.get(r4);
                r2 = (org.telegram.tgnet.TLRPC$TL_dialog) r2;
                r0 = r18;
                r4 = org.telegram.messenger.MessagesController.this;
                r5 = r3.channel_id;
                r5 = java.lang.Integer.valueOf(r5);
                r4 = r4.getChat(r5);
                if (r4 == 0) goto L_0x0389;
            L_0x0373:
                if (r2 != 0) goto L_0x039a;
            L_0x0375:
                r5 = r4 instanceof org.telegram.tgnet.TLRPC.TL_channel;
                if (r5 == 0) goto L_0x039a;
            L_0x0379:
                r5 = r4.left;
                if (r5 != 0) goto L_0x039a;
            L_0x037d:
                r2 = org.telegram.messenger.Utilities.stageQueue;
                r4 = new org.telegram.messenger.MessagesController$129$2;
                r0 = r18;
                r4.<init>(r3);
                r2.postRunnable(r4);
            L_0x0389:
                r2 = r12 | 8192;
                r0 = r18;
                r4 = org.telegram.messenger.MessagesController.this;
                r3 = r3.channel_id;
                r5 = 0;
                r6 = 1;
                r4.loadFullChat(r3, r5, r6);
                r3 = r2;
                r2 = r11;
                goto L_0x005e;
            L_0x039a:
                r4 = r4.left;
                if (r4 == 0) goto L_0x0389;
            L_0x039e:
                if (r2 == 0) goto L_0x0389;
            L_0x03a0:
                r0 = r18;
                r4 = org.telegram.messenger.MessagesController.this;
                r6 = r2.id;
                r2 = 0;
                r4.deleteDialog(r6, r2);
                goto L_0x0389;
            L_0x03ab:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateChatAdmins;
                if (r2 == 0) goto L_0x03b5;
            L_0x03af:
                r2 = r12 | 16384;
                r3 = r2;
                r2 = r11;
                goto L_0x005e;
            L_0x03b5:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateStickerSets;
                if (r2 == 0) goto L_0x03c9;
            L_0x03b9:
                r2 = r3.masks;
                if (r2 == 0) goto L_0x03c7;
            L_0x03bd:
                r2 = 1;
            L_0x03be:
                r3 = 0;
                r4 = 1;
                org.telegram.messenger.query.StickersQuery.loadStickers(r2, r3, r4);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x03c7:
                r2 = 0;
                goto L_0x03be;
            L_0x03c9:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateStickerSetsOrder;
                if (r2 == 0) goto L_0x03df;
            L_0x03cd:
                r2 = r3.masks;
                if (r2 == 0) goto L_0x03dd;
            L_0x03d1:
                r2 = 1;
            L_0x03d2:
                r3 = (org.telegram.tgnet.TLRPC$TL_updateStickerSetsOrder) r3;
                r3 = r3.order;
                org.telegram.messenger.query.StickersQuery.reorderStickers(r2, r3);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x03dd:
                r2 = 0;
                goto L_0x03d2;
            L_0x03df:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateFavedStickers;
                if (r2 == 0) goto L_0x03ee;
            L_0x03e3:
                r2 = 2;
                r3 = 0;
                r4 = 0;
                r5 = 1;
                org.telegram.messenger.query.StickersQuery.loadRecents(r2, r3, r4, r5);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x03ee:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateContactsReset;
                if (r2 == 0) goto L_0x03fd;
            L_0x03f2:
                r2 = org.telegram.messenger.ContactsController.getInstance();
                r2.forceImportContacts();
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x03fd:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateNewStickerSet;
                if (r2 == 0) goto L_0x040a;
            L_0x0401:
                r2 = r3.stickerset;
                org.telegram.messenger.query.StickersQuery.addNewStickerSet(r2);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x040a:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateSavedGifs;
                if (r2 == 0) goto L_0x042c;
            L_0x040e:
                r2 = org.telegram.messenger.ApplicationLoader.applicationContext;
                r3 = "emoji";
                r4 = 0;
                r2 = r2.getSharedPreferences(r3, r4);
                r2 = r2.edit();
                r3 = "lastGifLoadTime";
                r4 = 0;
                r2 = r2.putLong(r3, r4);
                r2.commit();
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x042c:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateRecentStickers;
                if (r2 == 0) goto L_0x044e;
            L_0x0430:
                r2 = org.telegram.messenger.ApplicationLoader.applicationContext;
                r3 = "emoji";
                r4 = 0;
                r2 = r2.getSharedPreferences(r3, r4);
                r2 = r2.edit();
                r3 = "lastStickersLoadTime";
                r4 = 0;
                r2 = r2.putLong(r3, r4);
                r2.commit();
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x044e:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateDraftMessage;
                if (r2 == 0) goto L_0x0478;
            L_0x0452:
                r4 = 1;
                r2 = r3;
                r2 = (org.telegram.tgnet.TLRPC$TL_updateDraftMessage) r2;
                r2 = r2.peer;
                r5 = r2.user_id;
                if (r5 == 0) goto L_0x046a;
            L_0x045c:
                r2 = r2.user_id;
                r6 = (long) r2;
            L_0x045f:
                r2 = r3.draft;
                r3 = 0;
                r5 = 1;
                org.telegram.messenger.query.DraftQuery.saveDraft(r6, r2, r3, r5);
                r2 = r4;
                r3 = r12;
                goto L_0x005e;
            L_0x046a:
                r5 = r2.channel_id;
                if (r5 == 0) goto L_0x0473;
            L_0x046e:
                r2 = r2.channel_id;
                r2 = -r2;
                r6 = (long) r2;
                goto L_0x045f;
            L_0x0473:
                r2 = r2.chat_id;
                r2 = -r2;
                r6 = (long) r2;
                goto L_0x045f;
            L_0x0478:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateReadFeaturedStickers;
                if (r2 == 0) goto L_0x0484;
            L_0x047c:
                r2 = 0;
                org.telegram.messenger.query.StickersQuery.markFaturedStickersAsRead(r2);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x0484:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updatePhoneCall;
                if (r2 == 0) goto L_0x05c1;
            L_0x0488:
                r3 = (org.telegram.tgnet.TLRPC$TL_updatePhoneCall) r3;
                r3 = r3.phone_call;
                r4 = org.telegram.messenger.voip.VoIPService.getSharedInstance();
                r2 = org.telegram.messenger.BuildVars.DEBUG_VERSION;
                if (r2 == 0) goto L_0x04c4;
            L_0x0494:
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r5 = "Received call in update: ";
                r2 = r2.append(r5);
                r2 = r2.append(r3);
                r2 = r2.toString();
                org.telegram.messenger.FileLog.m13725d(r2);
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r5 = "call id ";
                r2 = r2.append(r5);
                r6 = r3.id;
                r2 = r2.append(r6);
                r2 = r2.toString();
                org.telegram.messenger.FileLog.m13725d(r2);
            L_0x04c4:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_phoneCallRequested;
                if (r2 == 0) goto L_0x05a2;
            L_0x04c8:
                r2 = r3.date;
                r0 = r18;
                r5 = org.telegram.messenger.MessagesController.this;
                r5 = r5.callRingTimeout;
                r5 = r5 / 1000;
                r2 = r2 + r5;
                r5 = org.telegram.tgnet.ConnectionsManager.getInstance();
                r5 = r5.getCurrentTime();
                if (r2 >= r5) goto L_0x04eb;
            L_0x04dd:
                r2 = org.telegram.messenger.BuildVars.DEBUG_VERSION;
                if (r2 == 0) goto L_0x05cd;
            L_0x04e1:
                r2 = "ignoring too old call";
                org.telegram.messenger.FileLog.m13725d(r2);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x04eb:
                r2 = org.telegram.messenger.ApplicationLoader.applicationContext;
                r5 = "phone";
                r2 = r2.getSystemService(r5);
                r2 = (android.telephony.TelephonyManager) r2;
                if (r4 != 0) goto L_0x0502;
            L_0x04f8:
                r4 = org.telegram.messenger.voip.VoIPService.callIShouldHavePutIntoIntent;
                if (r4 != 0) goto L_0x0502;
            L_0x04fc:
                r2 = r2.getCallState();
                if (r2 == 0) goto L_0x0557;
            L_0x0502:
                r2 = org.telegram.messenger.BuildVars.DEBUG_VERSION;
                if (r2 == 0) goto L_0x0526;
            L_0x0506:
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r4 = "Auto-declining call ";
                r2 = r2.append(r4);
                r4 = r3.id;
                r2 = r2.append(r4);
                r4 = " because there's already active one";
                r2 = r2.append(r4);
                r2 = r2.toString();
                org.telegram.messenger.FileLog.m13725d(r2);
            L_0x0526:
                r2 = new org.telegram.tgnet.TLRPC$TL_phone_discardCall;
                r2.<init>();
                r4 = new org.telegram.tgnet.TLRPC$TL_inputPhoneCall;
                r4.<init>();
                r2.peer = r4;
                r4 = r2.peer;
                r6 = r3.access_hash;
                r4.access_hash = r6;
                r4 = r2.peer;
                r6 = r3.id;
                r4.id = r6;
                r3 = new org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonBusy;
                r3.<init>();
                r2.reason = r3;
                r3 = org.telegram.tgnet.ConnectionsManager.getInstance();
                r4 = new org.telegram.messenger.MessagesController$129$3;
                r0 = r18;
                r4.<init>();
                r3.sendRequest(r2, r4);
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x0557:
                r2 = org.telegram.messenger.BuildVars.DEBUG_VERSION;
                if (r2 == 0) goto L_0x0574;
            L_0x055b:
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r4 = "Starting service for call ";
                r2 = r2.append(r4);
                r4 = r3.id;
                r2 = r2.append(r4);
                r2 = r2.toString();
                org.telegram.messenger.FileLog.m13725d(r2);
            L_0x0574:
                org.telegram.messenger.voip.VoIPService.callIShouldHavePutIntoIntent = r3;
                r4 = new android.content.Intent;
                r2 = org.telegram.messenger.ApplicationLoader.applicationContext;
                r5 = org.telegram.messenger.voip.VoIPService.class;
                r4.<init>(r2, r5);
                r2 = "is_outgoing";
                r5 = 0;
                r4.putExtra(r2, r5);
                r5 = "user_id";
                r2 = r3.participant_id;
                r6 = org.telegram.messenger.UserConfig.getClientUserId();
                if (r2 != r6) goto L_0x059f;
            L_0x0591:
                r2 = r3.admin_id;
            L_0x0593:
                r4.putExtra(r5, r2);
                r2 = org.telegram.messenger.ApplicationLoader.applicationContext;
                r2.startService(r4);
            L_0x059b:
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x059f:
                r2 = r3.participant_id;
                goto L_0x0593;
            L_0x05a2:
                if (r4 == 0) goto L_0x05aa;
            L_0x05a4:
                if (r3 == 0) goto L_0x05aa;
            L_0x05a6:
                r4.onCallUpdated(r3);
                goto L_0x059b;
            L_0x05aa:
                r2 = org.telegram.messenger.voip.VoIPService.callIShouldHavePutIntoIntent;
                if (r2 == 0) goto L_0x059b;
            L_0x05ae:
                r2 = "Updated the call while the service is starting";
                org.telegram.messenger.FileLog.m13725d(r2);
                r4 = r3.id;
                r2 = org.telegram.messenger.voip.VoIPService.callIShouldHavePutIntoIntent;
                r6 = r2.id;
                r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
                if (r2 != 0) goto L_0x059b;
            L_0x05be:
                org.telegram.messenger.voip.VoIPService.callIShouldHavePutIntoIntent = r3;
                goto L_0x059b;
            L_0x05c1:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateGroupCall;
                if (r2 == 0) goto L_0x05c9;
            L_0x05c5:
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x05c9:
                r2 = r3 instanceof org.telegram.tgnet.TLRPC$TL_updateGroupCallParticipant;
                if (r2 == 0) goto L_0x05cd;
            L_0x05cd:
                r2 = r11;
                r3 = r12;
                goto L_0x005e;
            L_0x05d1:
                if (r10 == 0) goto L_0x05e2;
            L_0x05d3:
                r10.commit();
                r2 = org.telegram.messenger.NotificationCenter.getInstance();
                r3 = org.telegram.messenger.NotificationCenter.notificationsSettingsUpdated;
                r4 = 0;
                r4 = new java.lang.Object[r4];
                r2.postNotificationName(r3, r4);
            L_0x05e2:
                r2 = org.telegram.messenger.MessagesStorage.getInstance();
                r3 = 1;
                r4 = 1;
                r5 = 1;
                r2.updateUsers(r15, r3, r4, r5);
                r2 = org.telegram.messenger.MessagesStorage.getInstance();
                r3 = 0;
                r4 = 1;
                r5 = 1;
                r2.updateUsers(r14, r3, r4, r5);
            L_0x05f6:
                r0 = r18;
                r2 = r8;
                r2 = r2.isEmpty();
                if (r2 != 0) goto L_0x06d3;
            L_0x0600:
                r2 = org.telegram.messenger.NotificationCenter.getInstance();
                r3 = org.telegram.messenger.NotificationCenter.didReceivedWebpagesInUpdates;
                r4 = 1;
                r4 = new java.lang.Object[r4];
                r5 = 0;
                r0 = r18;
                r6 = r8;
                r4[r5] = r6;
                r2.postNotificationName(r3, r4);
                r0 = r18;
                r2 = r8;
                r2 = r2.entrySet();
                r9 = r2.iterator();
            L_0x061f:
                r2 = r9.hasNext();
                if (r2 == 0) goto L_0x06d3;
            L_0x0625:
                r2 = r9.next();
                r2 = (java.util.Map.Entry) r2;
                r0 = r18;
                r3 = org.telegram.messenger.MessagesController.this;
                r3 = r3.reloadingWebpagesPending;
                r4 = r2.getKey();
                r3 = r3.remove(r4);
                r8 = r3;
                r8 = (java.util.ArrayList) r8;
                if (r8 == 0) goto L_0x061f;
            L_0x0640:
                r2 = r2.getValue();
                r2 = (org.telegram.tgnet.TLRPC$WebPage) r2;
                r3 = new java.util.ArrayList;
                r3.<init>();
                r6 = 0;
                r4 = r2 instanceof org.telegram.tgnet.TLRPC$TL_webPage;
                if (r4 != 0) goto L_0x0655;
            L_0x0651:
                r4 = r2 instanceof org.telegram.tgnet.TLRPC$TL_webPageEmpty;
                if (r4 == 0) goto L_0x06c0;
            L_0x0655:
                r4 = 0;
                r5 = r4;
            L_0x0657:
                r4 = r8.size();
                if (r5 >= r4) goto L_0x068f;
            L_0x065d:
                r4 = r8.get(r5);
                r4 = (org.telegram.messenger.MessageObject) r4;
                r4 = r4.messageOwner;
                r4 = r4.media;
                r4.webpage = r2;
                if (r5 != 0) goto L_0x0680;
            L_0x066b:
                r4 = r8.get(r5);
                r4 = (org.telegram.messenger.MessageObject) r4;
                r6 = r4.getDialogId();
                r4 = r8.get(r5);
                r4 = (org.telegram.messenger.MessageObject) r4;
                r4 = r4.messageOwner;
                org.telegram.messenger.ImageLoader.saveMessageThumbs(r4);
            L_0x0680:
                r4 = r8.get(r5);
                r4 = (org.telegram.messenger.MessageObject) r4;
                r4 = r4.messageOwner;
                r3.add(r4);
                r4 = r5 + 1;
                r5 = r4;
                goto L_0x0657;
            L_0x068f:
                r14 = r6;
            L_0x0690:
                r2 = r3.isEmpty();
                if (r2 != 0) goto L_0x061f;
            L_0x0696:
                r2 = org.telegram.messenger.MessagesStorage.getInstance();
                r4 = 1;
                r5 = 1;
                r6 = 0;
                r7 = org.telegram.messenger.MediaController.getInstance();
                r7 = r7.getAutodownloadMask();
                r2.putMessages(r3, r4, r5, r6, r7);
                r2 = org.telegram.messenger.NotificationCenter.getInstance();
                r3 = org.telegram.messenger.NotificationCenter.replaceMessagesObjects;
                r4 = 2;
                r4 = new java.lang.Object[r4];
                r5 = 0;
                r6 = java.lang.Long.valueOf(r14);
                r4[r5] = r6;
                r5 = 1;
                r4[r5] = r8;
                r2.postNotificationName(r3, r4);
                goto L_0x061f;
            L_0x06c0:
                r0 = r18;
                r4 = org.telegram.messenger.MessagesController.this;
                r4 = r4.reloadingWebpagesPending;
                r14 = r2.id;
                r2 = java.lang.Long.valueOf(r14);
                r4.put(r2, r8);
                r14 = r6;
                goto L_0x0690;
            L_0x06d3:
                r2 = 0;
                r0 = r18;
                r3 = r9;
                r3 = r3.isEmpty();
                if (r3 != 0) goto L_0x07aa;
            L_0x06de:
                r0 = r18;
                r2 = r9;
                r2 = r2.entrySet();
                r4 = r2.iterator();
            L_0x06ea:
                r2 = r4.hasNext();
                if (r2 == 0) goto L_0x070e;
            L_0x06f0:
                r2 = r4.next();
                r2 = (java.util.Map.Entry) r2;
                r3 = r2.getKey();
                r3 = (java.lang.Long) r3;
                r2 = r2.getValue();
                r2 = (java.util.ArrayList) r2;
                r0 = r18;
                r5 = org.telegram.messenger.MessagesController.this;
                r6 = r3.longValue();
                r5.updateInterfaceWithMessages(r6, r2);
                goto L_0x06ea;
            L_0x070e:
                r2 = 1;
            L_0x070f:
                r0 = r18;
                r3 = r10;
                r3 = r3.isEmpty();
                if (r3 != 0) goto L_0x07bb;
            L_0x0719:
                r0 = r18;
                r3 = r10;
                r3 = r3.entrySet();
                r8 = r3.iterator();
                r6 = r2;
            L_0x0726:
                r2 = r8.hasNext();
                if (r2 == 0) goto L_0x07bc;
            L_0x072c:
                r2 = r8.next();
                r2 = (java.util.Map.Entry) r2;
                r3 = r2.getKey();
                r3 = (java.lang.Long) r3;
                r2 = r2.getValue();
                r2 = (java.util.ArrayList) r2;
                r0 = r18;
                r4 = org.telegram.messenger.MessagesController.this;
                r4 = r4.dialogMessage;
                r4 = r4.get(r3);
                r4 = (org.telegram.messenger.MessageObject) r4;
                if (r4 == 0) goto L_0x083e;
            L_0x074c:
                r5 = 0;
                r7 = r5;
            L_0x074e:
                r5 = r2.size();
                if (r7 >= r5) goto L_0x083e;
            L_0x0754:
                r5 = r2.get(r7);
                r5 = (org.telegram.messenger.MessageObject) r5;
                r9 = r4.getId();
                r10 = r5.getId();
                if (r9 != r10) goto L_0x07b7;
            L_0x0764:
                r0 = r18;
                r4 = org.telegram.messenger.MessagesController.this;
                r4 = r4.dialogMessage;
                r4.put(r3, r5);
                r4 = r5.messageOwner;
                r4 = r4.to_id;
                if (r4 == 0) goto L_0x078c;
            L_0x0773:
                r4 = r5.messageOwner;
                r4 = r4.to_id;
                r4 = r4.channel_id;
                if (r4 != 0) goto L_0x078c;
            L_0x077b:
                r0 = r18;
                r4 = org.telegram.messenger.MessagesController.this;
                r4 = r4.dialogMessagesByIds;
                r6 = r5.getId();
                r6 = java.lang.Integer.valueOf(r6);
                r4.put(r6, r5);
            L_0x078c:
                r6 = 1;
                r4 = r6;
            L_0x078e:
                r6 = r3.longValue();
                org.telegram.messenger.query.MessagesQuery.loadReplyMessagesForMessages(r2, r6);
                r5 = org.telegram.messenger.NotificationCenter.getInstance();
                r6 = org.telegram.messenger.NotificationCenter.replaceMessagesObjects;
                r7 = 2;
                r7 = new java.lang.Object[r7];
                r9 = 0;
                r7[r9] = r3;
                r3 = 1;
                r7[r3] = r2;
                r5.postNotificationName(r6, r7);
                r6 = r4;
                goto L_0x0726;
            L_0x07aa:
                if (r11 == 0) goto L_0x070f;
            L_0x07ac:
                r0 = r18;
                r2 = org.telegram.messenger.MessagesController.this;
                r3 = 0;
                r2.sortDialogs(r3);
                r2 = 1;
                goto L_0x070f;
            L_0x07b7:
                r5 = r7 + 1;
                r7 = r5;
                goto L_0x074e;
            L_0x07bb:
                r6 = r2;
            L_0x07bc:
                if (r6 == 0) goto L_0x07ca;
            L_0x07be:
                r2 = org.telegram.messenger.NotificationCenter.getInstance();
                r3 = org.telegram.messenger.NotificationCenter.dialogsNeedReload;
                r4 = 0;
                r4 = new java.lang.Object[r4];
                r2.postNotificationName(r3, r4);
            L_0x07ca:
                r0 = r18;
                r2 = r11;
                if (r2 == 0) goto L_0x07d2;
            L_0x07d0:
                r12 = r12 | 64;
            L_0x07d2:
                r0 = r18;
                r2 = r12;
                r2 = r2.isEmpty();
                if (r2 != 0) goto L_0x07e0;
            L_0x07dc:
                r2 = r12 | 1;
                r12 = r2 | 128;
            L_0x07e0:
                r0 = r18;
                r2 = r13;
                r2 = r2.isEmpty();
                if (r2 != 0) goto L_0x080b;
            L_0x07ea:
                r2 = 0;
                r3 = r2;
            L_0x07ec:
                r0 = r18;
                r2 = r13;
                r2 = r2.size();
                if (r3 >= r2) goto L_0x080b;
            L_0x07f6:
                r0 = r18;
                r2 = r13;
                r2 = r2.get(r3);
                r2 = (org.telegram.tgnet.TLRPC.ChatParticipants) r2;
                r4 = org.telegram.messenger.MessagesStorage.getInstance();
                r4.updateChatParticipants(r2);
                r2 = r3 + 1;
                r3 = r2;
                goto L_0x07ec;
            L_0x080b:
                r0 = r18;
                r2 = r14;
                r2 = r2.size();
                if (r2 == 0) goto L_0x0828;
            L_0x0815:
                r2 = org.telegram.messenger.NotificationCenter.getInstance();
                r3 = org.telegram.messenger.NotificationCenter.didUpdatedMessagesViews;
                r4 = 1;
                r4 = new java.lang.Object[r4];
                r5 = 0;
                r0 = r18;
                r6 = r14;
                r4[r5] = r6;
                r2.postNotificationName(r3, r4);
            L_0x0828:
                if (r12 == 0) goto L_0x083d;
            L_0x082a:
                r2 = org.telegram.messenger.NotificationCenter.getInstance();
                r3 = org.telegram.messenger.NotificationCenter.updateInterfaces;
                r4 = 1;
                r4 = new java.lang.Object[r4];
                r5 = 0;
                r6 = java.lang.Integer.valueOf(r12);
                r4[r5] = r6;
                r2.postNotificationName(r3, r4);
            L_0x083d:
                return;
            L_0x083e:
                r4 = r6;
                goto L_0x078e;
            L_0x0841:
                r4 = r10;
                goto L_0x0231;
            L_0x0844:
                r4 = r10;
                goto L_0x02c2;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesController.129.run():void");
            }
        });
        final SparseArray sparseArray6 = sparseArray2;
        final SparseArray sparseArray7 = sparseArray3;
        hashMap5 = hashMap4;
        final ArrayList arrayList16 = arrayList8;
        final SparseArray sparseArray8 = sparseArray4;
        final SparseArray sparseArray9 = sparseArray5;
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MessagesController$130$1 */
            class C31941 implements Runnable {
                C31941() {
                }

                public void run() {
                    int i;
                    int i2;
                    int keyAt;
                    int keyAt2;
                    long intValue;
                    MessageObject messageObject;
                    if (sparseArray6.size() == 0 && sparseArray7.size() == 0) {
                        i = 0;
                    } else {
                        TLRPC$TL_dialog tLRPC$TL_dialog;
                        MessageObject messageObject2;
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesRead, sparseArray6, sparseArray7);
                        NotificationsController.getInstance().processReadMessages(sparseArray6, 0, 0, 0, false);
                        if (sparseArray6.size() != 0) {
                            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                            i = 0;
                            for (i2 = 0; i2 < sparseArray6.size(); i2++) {
                                keyAt = sparseArray6.keyAt(i2);
                                int longValue = (int) ((Long) sparseArray6.get(keyAt)).longValue();
                                tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.this.dialogs_dict.get(Long.valueOf((long) keyAt));
                                if (tLRPC$TL_dialog != null && tLRPC$TL_dialog.top_message > 0 && tLRPC$TL_dialog.top_message <= longValue) {
                                    messageObject2 = (MessageObject) MessagesController.this.dialogMessage.get(Long.valueOf(tLRPC$TL_dialog.id));
                                    if (!(messageObject2 == null || messageObject2.isOut())) {
                                        messageObject2.setIsRead();
                                        i |= 256;
                                    }
                                }
                                if (keyAt != UserConfig.getClientUserId()) {
                                    edit.remove("diditem" + keyAt);
                                    edit.remove("diditemo" + keyAt);
                                }
                            }
                            edit.commit();
                        } else {
                            i = 0;
                        }
                        for (i2 = 0; i2 < sparseArray7.size(); i2++) {
                            keyAt2 = sparseArray7.keyAt(i2);
                            keyAt = (int) ((Long) sparseArray7.get(keyAt2)).longValue();
                            tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.this.dialogs_dict.get(Long.valueOf((long) keyAt2));
                            if (tLRPC$TL_dialog != null && tLRPC$TL_dialog.top_message > 0 && tLRPC$TL_dialog.top_message <= keyAt) {
                                messageObject2 = (MessageObject) MessagesController.this.dialogMessage.get(Long.valueOf(tLRPC$TL_dialog.id));
                                if (messageObject2 != null && messageObject2.isOut()) {
                                    messageObject2.setIsRead();
                                    i |= 256;
                                }
                            }
                        }
                    }
                    if (!hashMap5.isEmpty()) {
                        for (Entry entry : hashMap5.entrySet()) {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesReadEncrypted, entry.getKey(), entry.getValue());
                            intValue = ((long) ((Integer) entry.getKey()).intValue()) << 32;
                            if (((TLRPC$TL_dialog) MessagesController.this.dialogs_dict.get(Long.valueOf(intValue))) != null) {
                                messageObject = (MessageObject) MessagesController.this.dialogMessage.get(Long.valueOf(intValue));
                                if (messageObject != null && messageObject.messageOwner.date <= ((Integer) entry.getValue()).intValue()) {
                                    messageObject.setIsRead();
                                    i |= 256;
                                }
                            }
                        }
                    }
                    keyAt2 = i;
                    if (!arrayList16.isEmpty()) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesReadContent, arrayList16);
                    }
                    if (sparseArray8.size() != 0) {
                        for (keyAt = 0; keyAt < sparseArray8.size(); keyAt++) {
                            i2 = sparseArray8.keyAt(keyAt);
                            ArrayList arrayList = (ArrayList) sparseArray8.get(i2);
                            if (arrayList != null) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesDeleted, arrayList, Integer.valueOf(i2));
                                if (i2 == 0) {
                                    for (i = 0; i < arrayList.size(); i++) {
                                        messageObject = (MessageObject) MessagesController.this.dialogMessagesByIds.get((Integer) arrayList.get(i));
                                        if (messageObject != null) {
                                            messageObject.deleted = true;
                                        }
                                    }
                                } else {
                                    messageObject = (MessageObject) MessagesController.this.dialogMessage.get(Long.valueOf((long) (-i2)));
                                    if (messageObject != null) {
                                        for (longValue = 0; longValue < arrayList.size(); longValue++) {
                                            if (messageObject.getId() == ((Integer) arrayList.get(longValue)).intValue()) {
                                                messageObject.deleted = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        NotificationsController.getInstance().removeDeletedMessagesFromNotifications(sparseArray8);
                    }
                    if (sparseArray9.size() != 0) {
                        for (i = 0; i < sparseArray9.size(); i++) {
                            i2 = sparseArray9.keyAt(i);
                            Integer num = (Integer) sparseArray9.get(i2);
                            if (num != null) {
                                intValue = (long) (-i2);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.historyCleared, Long.valueOf(intValue), num);
                                messageObject = (MessageObject) MessagesController.this.dialogMessage.get(Long.valueOf(intValue));
                                if (messageObject != null && messageObject.getId() <= num.intValue()) {
                                    messageObject.deleted = true;
                                    break;
                                }
                            }
                        }
                        NotificationsController.getInstance().removeDeletedHisoryFromNotifications(sparseArray9);
                    }
                    if (keyAt2 != 0) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(keyAt2));
                    }
                }
            }

            public void run() {
                AndroidUtilities.runOnUIThread(new C31941());
            }
        });
        if (!hashMap2.isEmpty()) {
            MessagesStorage.getInstance().putWebPages(hashMap2);
        }
        if (!(sparseArray2.size() == 0 && sparseArray3.size() == 0 && hashMap4.isEmpty() && arrayList8.isEmpty())) {
            if (!(sparseArray2.size() == 0 && arrayList8.isEmpty())) {
                MessagesStorage.getInstance().updateDialogsWithReadMessages(sparseArray2, sparseArray3, arrayList8, true);
            }
            MessagesStorage.getInstance().markMessagesAsRead(sparseArray2, sparseArray3, hashMap4, true);
        }
        if (!arrayList8.isEmpty()) {
            MessagesStorage.getInstance().markMessagesContentAsRead(arrayList8, ConnectionsManager.getInstance().getCurrentTime());
        }
        if (sparseArray4.size() != 0) {
            for (i = 0; i < sparseArray4.size(); i++) {
                i2 = sparseArray4.keyAt(i);
                arrayList13 = (ArrayList) sparseArray4.get(i2);
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        MessagesStorage.getInstance().updateDialogsWithDeletedMessages(arrayList13, MessagesStorage.getInstance().markMessagesAsDeleted(arrayList13, false, i2), false, i2);
                    }
                });
            }
        }
        if (sparseArray5.size() != 0) {
            for (i = 0; i < sparseArray5.size(); i++) {
                i2 = sparseArray5.keyAt(i);
                num = (Integer) sparseArray5.get(i2);
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        MessagesStorage.getInstance().updateDialogsWithDeletedMessages(new ArrayList(), MessagesStorage.getInstance().markMessagesAsDeleted(i2, num.intValue(), false), false, i2);
                    }
                });
            }
        }
        if (!arrayList11.isEmpty()) {
            for (int i9 = 0; i9 < arrayList11.size(); i9++) {
                TLRPC$TL_updateEncryptedMessagesRead tLRPC$TL_updateEncryptedMessagesRead = (TLRPC$TL_updateEncryptedMessagesRead) arrayList11.get(i9);
                MessagesStorage.getInstance().createTaskForSecretChat(tLRPC$TL_updateEncryptedMessagesRead.chat_id, tLRPC$TL_updateEncryptedMessagesRead.max_date, tLRPC$TL_updateEncryptedMessagesRead.date, 1, null);
            }
        }
        return true;
    }

    public void processUpdates(TLRPC$Updates tLRPC$Updates, boolean z) {
        Object obj;
        final int i;
        int i2;
        Integer num;
        ArrayList arrayList = null;
        Object obj2 = null;
        Object obj3 = null;
        ArrayList arrayList2;
        if (tLRPC$Updates instanceof TLRPC$TL_updateShort) {
            arrayList2 = new ArrayList();
            arrayList2.add(tLRPC$Updates.update);
            processUpdateArray(arrayList2, null, null, false);
            obj = null;
        } else if ((tLRPC$Updates instanceof TLRPC$TL_updateShortChatMessage) || (tLRPC$Updates instanceof TLRPC$TL_updateShortMessage)) {
            User user;
            User user2;
            Object obj4;
            i = tLRPC$Updates instanceof TLRPC$TL_updateShortChatMessage ? tLRPC$Updates.from_id : tLRPC$Updates.user_id;
            User user3 = getUser(Integer.valueOf(i));
            User user4 = null;
            User user5 = null;
            if (user3 == null || user3.min) {
                user3 = MessagesStorage.getInstance().getUserSync(i);
                if (user3 != null && user3.min) {
                    user3 = null;
                }
                putUser(user3, true);
            }
            User user6 = user3;
            r2 = null;
            if (tLRPC$Updates.fwd_from != null) {
                if (tLRPC$Updates.fwd_from.from_id != 0) {
                    user3 = getUser(Integer.valueOf(tLRPC$Updates.fwd_from.from_id));
                    if (user3 == null) {
                        user3 = MessagesStorage.getInstance().getUserSync(tLRPC$Updates.fwd_from.from_id);
                        putUser(user3, true);
                    }
                    user4 = user3;
                    r2 = 1;
                }
                if (tLRPC$Updates.fwd_from.channel_id != 0) {
                    r2 = getChat(Integer.valueOf(tLRPC$Updates.fwd_from.channel_id));
                    if (r2 == null) {
                        r2 = MessagesStorage.getInstance().getChatSync(tLRPC$Updates.fwd_from.channel_id);
                        putChat(r2, true);
                    }
                    user = user4;
                    r5 = r2;
                    r2 = 1;
                } else {
                    user = user4;
                    r5 = null;
                }
            } else {
                user = null;
                r5 = null;
            }
            Object obj5 = null;
            if (tLRPC$Updates.via_bot_id != 0) {
                user2 = getUser(Integer.valueOf(tLRPC$Updates.via_bot_id));
                if (user2 == null) {
                    user2 = MessagesStorage.getInstance().getUserSync(tLRPC$Updates.via_bot_id);
                    putUser(user2, true);
                }
                user5 = user2;
                obj5 = 1;
            }
            if (tLRPC$Updates instanceof TLRPC$TL_updateShortMessage) {
                r2 = (user6 == null || ((r2 != null && user == null && r5 == null) || (obj5 != null && user5 == null))) ? 1 : null;
                r6 = r2;
            } else {
                Chat chat = getChat(Integer.valueOf(tLRPC$Updates.chat_id));
                if (chat == null) {
                    chat = MessagesStorage.getInstance().getChatSync(tLRPC$Updates.chat_id);
                    putChat(chat, true);
                }
                r2 = (chat == null || user6 == null || ((r2 != null && user == null && r5 == null) || (obj5 != null && user5 == null))) ? 1 : null;
                r6 = r2;
            }
            if (r6 == null && !tLRPC$Updates.entities.isEmpty()) {
                for (int i3 = 0; i3 < tLRPC$Updates.entities.size(); i3++) {
                    MessageEntity messageEntity = (MessageEntity) tLRPC$Updates.entities.get(i3);
                    if (messageEntity instanceof TLRPC$TL_messageEntityMentionName) {
                        i2 = ((TLRPC$TL_messageEntityMentionName) messageEntity).user_id;
                        user2 = getUser(Integer.valueOf(i2));
                        if (user2 == null || user2.min) {
                            user3 = MessagesStorage.getInstance().getUserSync(i2);
                            if (user3 != null && user3.min) {
                                user3 = null;
                            }
                            if (user3 == null) {
                                r2 = 1;
                                break;
                            }
                            putUser(user6, true);
                        }
                    }
                }
            }
            r2 = r6;
            if (user6 == null || user6.status == null || user6.status.expires > 0) {
                obj4 = null;
            } else {
                this.onlinePrivacy.put(Integer.valueOf(user6.id), Integer.valueOf(ConnectionsManager.getInstance().getCurrentTime()));
                obj4 = 1;
            }
            if (r2 != null) {
                r2 = 1;
            } else if (MessagesStorage.lastPtsValue + tLRPC$Updates.pts_count == tLRPC$Updates.pts) {
                Message tLRPC$TL_message = new TLRPC$TL_message();
                tLRPC$TL_message.id = tLRPC$Updates.id;
                int clientUserId = UserConfig.getClientUserId();
                if (tLRPC$Updates instanceof TLRPC$TL_updateShortMessage) {
                    if (tLRPC$Updates.out) {
                        tLRPC$TL_message.from_id = clientUserId;
                    } else {
                        tLRPC$TL_message.from_id = i;
                    }
                    tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
                    tLRPC$TL_message.to_id.user_id = i;
                    tLRPC$TL_message.dialog_id = (long) i;
                } else {
                    tLRPC$TL_message.from_id = i;
                    tLRPC$TL_message.to_id = new TLRPC$TL_peerChat();
                    tLRPC$TL_message.to_id.chat_id = tLRPC$Updates.chat_id;
                    tLRPC$TL_message.dialog_id = (long) (-tLRPC$Updates.chat_id);
                }
                tLRPC$TL_message.fwd_from = tLRPC$Updates.fwd_from;
                tLRPC$TL_message.silent = tLRPC$Updates.silent;
                tLRPC$TL_message.out = tLRPC$Updates.out;
                tLRPC$TL_message.mentioned = tLRPC$Updates.mentioned;
                tLRPC$TL_message.media_unread = tLRPC$Updates.media_unread;
                tLRPC$TL_message.entities = tLRPC$Updates.entities;
                tLRPC$TL_message.message = tLRPC$Updates.message;
                tLRPC$TL_message.date = tLRPC$Updates.date;
                tLRPC$TL_message.via_bot_id = tLRPC$Updates.via_bot_id;
                tLRPC$TL_message.flags = tLRPC$Updates.flags | 256;
                tLRPC$TL_message.reply_to_msg_id = tLRPC$Updates.reply_to_msg_id;
                tLRPC$TL_message.media = new TLRPC$TL_messageMediaEmpty();
                ConcurrentHashMap concurrentHashMap = tLRPC$TL_message.out ? this.dialogs_read_outbox_max : this.dialogs_read_inbox_max;
                num = (Integer) concurrentHashMap.get(Long.valueOf(tLRPC$TL_message.dialog_id));
                if (num == null) {
                    num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(tLRPC$TL_message.out, tLRPC$TL_message.dialog_id));
                    concurrentHashMap.put(Long.valueOf(tLRPC$TL_message.dialog_id), num);
                }
                tLRPC$TL_message.unread = num.intValue() < tLRPC$TL_message.id;
                if (tLRPC$TL_message.dialog_id == ((long) clientUserId)) {
                    tLRPC$TL_message.unread = false;
                    tLRPC$TL_message.media_unread = false;
                    tLRPC$TL_message.out = true;
                }
                MessagesStorage.lastPtsValue = tLRPC$Updates.pts;
                MessageObject messageObject = new MessageObject(tLRPC$TL_message, null, this.createdDialogIds.contains(Long.valueOf(tLRPC$TL_message.dialog_id)));
                final ArrayList arrayList3 = new ArrayList();
                arrayList3.add(messageObject);
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add(tLRPC$TL_message);
                final boolean z2;
                if (tLRPC$Updates instanceof TLRPC$TL_updateShortMessage) {
                    z2 = !tLRPC$Updates.out && updatePrintingUsersWithNewMessages((long) tLRPC$Updates.user_id, arrayList3);
                    if (z2) {
                        updatePrintingStrings();
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (z2) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(64));
                            }
                            MessagesController.this.updateInterfaceWithMessages((long) i, arrayList3);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                        }
                    });
                } else {
                    z2 = updatePrintingUsersWithNewMessages((long) (-tLRPC$Updates.chat_id), arrayList3);
                    if (z2) {
                        updatePrintingStrings();
                    }
                    final TLRPC$Updates tLRPC$Updates2 = tLRPC$Updates;
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (z2) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(64));
                            }
                            MessagesController.this.updateInterfaceWithMessages((long) (-tLRPC$Updates2.chat_id), arrayList3);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                        }
                    });
                }
                if (!messageObject.isOut()) {
                    MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                        /* renamed from: org.telegram.messenger.MessagesController$121$1 */
                        class C31881 implements Runnable {
                            C31881() {
                            }

                            public void run() {
                                NotificationsController.getInstance().processNewMessages(arrayList3, true);
                            }
                        }

                        public void run() {
                            AndroidUtilities.runOnUIThread(new C31881());
                        }
                    });
                }
                MessagesStorage.getInstance().putMessages(arrayList4, false, true, false, 0);
                r2 = null;
            } else if (MessagesStorage.lastPtsValue != tLRPC$Updates.pts) {
                FileLog.m13726e("need get diff short message, pts: " + MessagesStorage.lastPtsValue + " " + tLRPC$Updates.pts + " count = " + tLRPC$Updates.pts_count);
                if (this.gettingDifference || this.updatesStartWaitTimePts == 0 || Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimePts) <= 1500) {
                    if (this.updatesStartWaitTimePts == 0) {
                        this.updatesStartWaitTimePts = System.currentTimeMillis();
                    }
                    FileLog.m13726e("add to queue");
                    this.updatesQueuePts.add(tLRPC$Updates);
                    r2 = null;
                } else {
                    r2 = 1;
                }
            } else {
                r2 = null;
            }
            obj = obj4;
            obj2 = r2;
        } else if ((tLRPC$Updates instanceof TLRPC$TL_updatesCombined) || (tLRPC$Updates instanceof TLRPC$TL_updates)) {
            TLRPC$Update tLRPC$Update;
            Object obj6;
            ArrayList arrayList5;
            HashMap hashMap = null;
            for (i = 0; i < tLRPC$Updates.chats.size(); i++) {
                r2 = (Chat) tLRPC$Updates.chats.get(i);
                if ((r2 instanceof TL_channel) && r2.min) {
                    r5 = getChat(Integer.valueOf(r2.id));
                    if (r5 == null || r5.min) {
                        r5 = MessagesStorage.getInstance().getChatSync(tLRPC$Updates.chat_id);
                        putChat(r5, true);
                    }
                    if (r5 == null || r5.min) {
                        if (hashMap == null) {
                            hashMap = new HashMap();
                        }
                        hashMap.put(Integer.valueOf(r2.id), r2);
                    }
                }
            }
            if (hashMap != null) {
                for (i = 0; i < tLRPC$Updates.updates.size(); i++) {
                    tLRPC$Update = (TLRPC$Update) tLRPC$Updates.updates.get(i);
                    if (tLRPC$Update instanceof TLRPC$TL_updateNewChannelMessage) {
                        i2 = ((TLRPC$TL_updateNewChannelMessage) tLRPC$Update).message.to_id.channel_id;
                        if (hashMap.containsKey(Integer.valueOf(i2))) {
                            FileLog.m13726e("need get diff because of min channel " + i2);
                            r6 = 1;
                            break;
                        }
                    }
                }
            }
            r6 = null;
            if (r6 == null) {
                MessagesStorage.getInstance().putUsersAndChats(tLRPC$Updates.users, tLRPC$Updates.chats, true, true);
                Collections.sort(tLRPC$Updates.updates, this.updatesComparator);
                int i4 = 0;
                obj6 = null;
                arrayList5 = null;
                while (tLRPC$Updates.updates.size() > 0) {
                    tLRPC$Update = (TLRPC$Update) tLRPC$Updates.updates.get(i4);
                    TLRPC$TL_updates tLRPC$TL_updates;
                    int i5;
                    TLRPC$Update tLRPC$Update2;
                    if (getUpdateType(tLRPC$Update) == 0) {
                        tLRPC$TL_updates = new TLRPC$TL_updates();
                        tLRPC$TL_updates.updates.add(tLRPC$Update);
                        tLRPC$TL_updates.pts = tLRPC$Update.pts;
                        tLRPC$TL_updates.pts_count = tLRPC$Update.pts_count;
                        for (i5 = 1; i5 < tLRPC$Updates.updates.size(); i5 = 1) {
                            tLRPC$Update2 = (TLRPC$Update) tLRPC$Updates.updates.get(i5);
                            if (getUpdateType(tLRPC$Update2) != 0 || tLRPC$TL_updates.pts + tLRPC$Update2.pts_count != tLRPC$Update2.pts) {
                                break;
                            }
                            tLRPC$TL_updates.updates.add(tLRPC$Update2);
                            tLRPC$TL_updates.pts = tLRPC$Update2.pts;
                            tLRPC$TL_updates.pts_count = tLRPC$Update2.pts_count + tLRPC$TL_updates.pts_count;
                            tLRPC$Updates.updates.remove(i5);
                        }
                        if (MessagesStorage.lastPtsValue + tLRPC$TL_updates.pts_count == tLRPC$TL_updates.pts) {
                            if (processUpdateArray(tLRPC$TL_updates.updates, tLRPC$Updates.users, tLRPC$Updates.chats, false)) {
                                MessagesStorage.lastPtsValue = tLRPC$TL_updates.pts;
                                r2 = r6;
                            } else {
                                FileLog.m13726e("need get diff inner TL_updates, seq: " + MessagesStorage.lastSeqValue + " " + tLRPC$Updates.seq);
                                r2 = 1;
                            }
                        } else if (MessagesStorage.lastPtsValue != tLRPC$TL_updates.pts) {
                            FileLog.m13726e(tLRPC$Update + " need get diff, pts: " + MessagesStorage.lastPtsValue + " " + tLRPC$TL_updates.pts + " count = " + tLRPC$TL_updates.pts_count);
                            if (this.gettingDifference || this.updatesStartWaitTimePts == 0 || (this.updatesStartWaitTimePts != 0 && Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimePts) <= 1500)) {
                                if (this.updatesStartWaitTimePts == 0) {
                                    this.updatesStartWaitTimePts = System.currentTimeMillis();
                                }
                                FileLog.m13726e("add to queue");
                                this.updatesQueuePts.add(tLRPC$TL_updates);
                                r2 = r6;
                            } else {
                                i2 = 1;
                            }
                        } else {
                            r2 = r6;
                        }
                        obj = r2;
                        r2 = obj6;
                    } else {
                        if (getUpdateType(tLRPC$Update) != 1) {
                            if (getUpdateType(tLRPC$Update) != 2) {
                                break;
                            }
                            Integer num2;
                            int updateChannelId = getUpdateChannelId(tLRPC$Update);
                            obj2 = null;
                            Integer num3 = (Integer) this.channelsPts.get(Integer.valueOf(updateChannelId));
                            if (num3 == null) {
                                Integer valueOf = Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(updateChannelId));
                                if (valueOf.intValue() == 0) {
                                    for (i5 = 0; i5 < tLRPC$Updates.chats.size(); i5++) {
                                        Chat chat2 = (Chat) tLRPC$Updates.chats.get(i5);
                                        if (chat2.id == updateChannelId) {
                                            loadUnknownChannel(chat2, 0);
                                            obj = 1;
                                            break;
                                        }
                                    }
                                    obj = null;
                                    num2 = valueOf;
                                    obj2 = obj;
                                } else {
                                    this.channelsPts.put(Integer.valueOf(updateChannelId), valueOf);
                                    num2 = valueOf;
                                }
                            } else {
                                num2 = num3;
                            }
                            TLRPC$TL_updates tLRPC$TL_updates2 = new TLRPC$TL_updates();
                            tLRPC$TL_updates2.updates.add(tLRPC$Update);
                            tLRPC$TL_updates2.pts = tLRPC$Update.pts;
                            tLRPC$TL_updates2.pts_count = tLRPC$Update.pts_count;
                            for (int i6 = 1; i6 < tLRPC$Updates.updates.size(); i6 = 1) {
                                tLRPC$Update2 = (TLRPC$Update) tLRPC$Updates.updates.get(i6);
                                if (getUpdateType(tLRPC$Update2) != 2 || updateChannelId != getUpdateChannelId(tLRPC$Update2) || tLRPC$TL_updates2.pts + tLRPC$Update2.pts_count != tLRPC$Update2.pts) {
                                    break;
                                }
                                tLRPC$TL_updates2.updates.add(tLRPC$Update2);
                                tLRPC$TL_updates2.pts = tLRPC$Update2.pts;
                                tLRPC$TL_updates2.pts_count = tLRPC$Update2.pts_count + tLRPC$TL_updates2.pts_count;
                                tLRPC$Updates.updates.remove(i6);
                            }
                            if (obj2 != null) {
                                FileLog.m13726e("need load unknown channel = " + updateChannelId);
                            } else if (num2.intValue() + tLRPC$TL_updates2.pts_count == tLRPC$TL_updates2.pts) {
                                if (processUpdateArray(tLRPC$TL_updates2.updates, tLRPC$Updates.users, tLRPC$Updates.chats, false)) {
                                    this.channelsPts.put(Integer.valueOf(updateChannelId), Integer.valueOf(tLRPC$TL_updates2.pts));
                                    MessagesStorage.getInstance().saveChannelPts(updateChannelId, tLRPC$TL_updates2.pts);
                                    r2 = obj6;
                                    obj = r6;
                                } else {
                                    FileLog.m13726e("need get channel diff inner TL_updates, channel_id = " + updateChannelId);
                                    if (arrayList5 == null) {
                                        arrayList5 = new ArrayList();
                                        r2 = obj6;
                                        obj = r6;
                                    } else if (!arrayList5.contains(Integer.valueOf(updateChannelId))) {
                                        arrayList5.add(Integer.valueOf(updateChannelId));
                                        r2 = obj6;
                                        obj = r6;
                                    }
                                }
                            } else if (num2.intValue() != tLRPC$TL_updates2.pts) {
                                FileLog.m13726e(tLRPC$Update + " need get channel diff, pts: " + num2 + " " + tLRPC$TL_updates2.pts + " count = " + tLRPC$TL_updates2.pts_count + " channelId = " + updateChannelId);
                                Long l = (Long) this.updatesStartWaitTimeChannels.get(Integer.valueOf(updateChannelId));
                                Boolean bool = (Boolean) this.gettingDifferenceChannels.get(Integer.valueOf(updateChannelId));
                                if (bool == null) {
                                    bool = Boolean.valueOf(false);
                                }
                                if (bool.booleanValue() || l == null || Math.abs(System.currentTimeMillis() - l.longValue()) <= 1500) {
                                    if (l == null) {
                                        this.updatesStartWaitTimeChannels.put(Integer.valueOf(updateChannelId), Long.valueOf(System.currentTimeMillis()));
                                    }
                                    FileLog.m13726e("add to queue");
                                    arrayList2 = (ArrayList) this.updatesQueueChannels.get(Integer.valueOf(updateChannelId));
                                    if (arrayList2 == null) {
                                        arrayList2 = new ArrayList();
                                        this.updatesQueueChannels.put(Integer.valueOf(updateChannelId), arrayList2);
                                    }
                                    arrayList2.add(tLRPC$TL_updates2);
                                    arrayList2 = arrayList5;
                                } else if (arrayList5 == null) {
                                    arrayList2 = new ArrayList();
                                } else {
                                    if (!arrayList5.contains(Integer.valueOf(updateChannelId))) {
                                        arrayList5.add(Integer.valueOf(updateChannelId));
                                    }
                                    arrayList2 = arrayList5;
                                }
                                obj = r6;
                                arrayList5 = arrayList2;
                                r2 = obj6;
                            }
                        } else {
                            tLRPC$TL_updates = new TLRPC$TL_updates();
                            tLRPC$TL_updates.updates.add(tLRPC$Update);
                            tLRPC$TL_updates.pts = tLRPC$Update.qts;
                            for (i5 = 1; i5 < tLRPC$Updates.updates.size(); i5 = 1) {
                                tLRPC$Update2 = (TLRPC$Update) tLRPC$Updates.updates.get(i5);
                                if (getUpdateType(tLRPC$Update2) != 1 || tLRPC$TL_updates.pts + 1 != tLRPC$Update2.qts) {
                                    break;
                                }
                                tLRPC$TL_updates.updates.add(tLRPC$Update2);
                                tLRPC$TL_updates.pts = tLRPC$Update2.qts;
                                tLRPC$Updates.updates.remove(i5);
                            }
                            if (MessagesStorage.lastQtsValue == 0 || MessagesStorage.lastQtsValue + tLRPC$TL_updates.updates.size() == tLRPC$TL_updates.pts) {
                                processUpdateArray(tLRPC$TL_updates.updates, tLRPC$Updates.users, tLRPC$Updates.chats, false);
                                MessagesStorage.lastQtsValue = tLRPC$TL_updates.pts;
                                i2 = 1;
                                obj = r6;
                            } else if (MessagesStorage.lastPtsValue != tLRPC$TL_updates.pts) {
                                FileLog.m13726e(tLRPC$Update + " need get diff, qts: " + MessagesStorage.lastQtsValue + " " + tLRPC$TL_updates.pts);
                                if (this.gettingDifference || this.updatesStartWaitTimeQts == 0 || (this.updatesStartWaitTimeQts != 0 && Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimeQts) <= 1500)) {
                                    if (this.updatesStartWaitTimeQts == 0) {
                                        this.updatesStartWaitTimeQts = System.currentTimeMillis();
                                    }
                                    FileLog.m13726e("add to queue");
                                    this.updatesQueueQts.add(tLRPC$TL_updates);
                                    r2 = obj6;
                                    obj = r6;
                                } else {
                                    r2 = obj6;
                                    int i7 = 1;
                                }
                            }
                        }
                        r2 = obj6;
                        obj = r6;
                    }
                    tLRPC$Updates.updates.remove(i4);
                    i4 = 0;
                    r6 = obj;
                    obj6 = r2;
                }
                r2 = tLRPC$Updates instanceof TLRPC$TL_updatesCombined ? (MessagesStorage.lastSeqValue + 1 == tLRPC$Updates.seq_start || MessagesStorage.lastSeqValue == tLRPC$Updates.seq_start) ? 1 : null : (MessagesStorage.lastSeqValue + 1 == tLRPC$Updates.seq || tLRPC$Updates.seq == 0 || tLRPC$Updates.seq == MessagesStorage.lastSeqValue) ? 1 : null;
                if (r2 != null) {
                    processUpdateArray(tLRPC$Updates.updates, tLRPC$Updates.users, tLRPC$Updates.chats, false);
                    if (tLRPC$Updates.seq != 0) {
                        if (tLRPC$Updates.date != 0) {
                            MessagesStorage.lastDateValue = tLRPC$Updates.date;
                        }
                        MessagesStorage.lastSeqValue = tLRPC$Updates.seq;
                    }
                } else {
                    if (tLRPC$Updates instanceof TLRPC$TL_updatesCombined) {
                        FileLog.m13726e("need get diff TL_updatesCombined, seq: " + MessagesStorage.lastSeqValue + " " + tLRPC$Updates.seq_start);
                    } else {
                        FileLog.m13726e("need get diff TL_updates, seq: " + MessagesStorage.lastSeqValue + " " + tLRPC$Updates.seq);
                    }
                    if (this.gettingDifference || this.updatesStartWaitTimeSeq == 0 || Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimeSeq) <= 1500) {
                        if (this.updatesStartWaitTimeSeq == 0) {
                            this.updatesStartWaitTimeSeq = System.currentTimeMillis();
                        }
                        FileLog.m13726e("add TL_updates/Combined to queue");
                        this.updatesQueueSeq.add(tLRPC$Updates);
                    } else {
                        r6 = 1;
                    }
                }
            } else {
                obj6 = null;
                arrayList5 = null;
            }
            obj = null;
            obj3 = obj6;
            obj2 = r6;
            arrayList = arrayList5;
        } else if (tLRPC$Updates instanceof TLRPC$TL_updatesTooLong) {
            FileLog.m13726e("need get diff TL_updatesTooLong");
            obj2 = 1;
            obj = null;
        } else if (tLRPC$Updates instanceof UserActionUpdatesSeq) {
            MessagesStorage.lastSeqValue = tLRPC$Updates.seq;
            obj = null;
        } else {
            if (tLRPC$Updates instanceof UserActionUpdatesPts) {
                if (tLRPC$Updates.chat_id != 0) {
                    this.channelsPts.put(Integer.valueOf(tLRPC$Updates.chat_id), Integer.valueOf(tLRPC$Updates.pts));
                    MessagesStorage.getInstance().saveChannelPts(tLRPC$Updates.chat_id, tLRPC$Updates.pts);
                    obj = null;
                } else {
                    MessagesStorage.lastPtsValue = tLRPC$Updates.pts;
                }
            }
            obj = null;
        }
        SecretChatHelper.getInstance().processPendingEncMessages();
        if (!z) {
            ArrayList arrayList6 = new ArrayList(this.updatesQueueChannels.keySet());
            for (i = 0; i < arrayList6.size(); i++) {
                num = (Integer) arrayList6.get(i);
                if (arrayList == null || !arrayList.contains(num)) {
                    processChannelsUpdatesQueue(num.intValue(), 0);
                } else {
                    getChannelDifference(num.intValue());
                }
            }
            if (obj2 != null) {
                getDifference();
            } else {
                for (i2 = 0; i2 < 3; i2++) {
                    processUpdatesQueue(i2, 0);
                }
            }
        }
        if (obj3 != null) {
            TLObject tLRPC$TL_messages_receivedQueue = new TLRPC$TL_messages_receivedQueue();
            tLRPC$TL_messages_receivedQueue.max_qts = MessagesStorage.lastQtsValue;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_receivedQueue, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                }
            });
        }
        if (obj != null) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(4));
                }
            });
        }
        MessagesStorage.getInstance().saveDiffParams(MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
    }

    public void putChat(final Chat chat, boolean z) {
        boolean z2 = false;
        if (chat != null) {
            Chat chat2 = (Chat) this.chats.get(Integer.valueOf(chat.id));
            if (chat2 != chat) {
                if (!(chat2 == null || TextUtils.isEmpty(chat2.username))) {
                    this.objectsByUsernames.remove(chat2.username.toLowerCase());
                }
                if (!TextUtils.isEmpty(chat.username)) {
                    this.objectsByUsernames.put(chat.username.toLowerCase(), chat);
                }
                if (chat.min) {
                    if (chat2 == null) {
                        this.chats.put(Integer.valueOf(chat.id), chat);
                    } else if (!z) {
                        chat2.title = chat.title;
                        chat2.photo = chat.photo;
                        chat2.broadcast = chat.broadcast;
                        chat2.verified = chat.verified;
                        chat2.megagroup = chat.megagroup;
                        chat2.democracy = chat.democracy;
                        if (chat.username != null) {
                            chat2.username = chat.username;
                            chat2.flags |= 64;
                        } else {
                            chat2.flags &= -65;
                            chat2.username = null;
                        }
                        if (chat.participants_count != 0) {
                            chat2.participants_count = chat.participants_count;
                        }
                    }
                } else if (!z) {
                    if (chat2 != null) {
                        if (chat.version != chat2.version) {
                            this.loadedFullChats.remove(Integer.valueOf(chat.id));
                        }
                        if (chat2.participants_count != 0 && chat.participants_count == 0) {
                            chat.participants_count = chat2.participants_count;
                            chat.flags |= 131072;
                        }
                        boolean z3 = chat2.banned_rights != null ? chat2.banned_rights.flags : false;
                        if (chat.banned_rights != null) {
                            z2 = chat.banned_rights.flags;
                        }
                        if (z3 != z2) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.channelRightsUpdated, chat);
                                }
                            });
                        }
                    }
                    this.chats.put(Integer.valueOf(chat.id), chat);
                } else if (chat2 == null) {
                    this.chats.put(Integer.valueOf(chat.id), chat);
                } else if (chat2.min) {
                    chat.min = false;
                    chat.title = chat2.title;
                    chat.photo = chat2.photo;
                    chat.broadcast = chat2.broadcast;
                    chat.verified = chat2.verified;
                    chat.megagroup = chat2.megagroup;
                    chat.democracy = chat2.democracy;
                    if (chat2.username != null) {
                        chat.username = chat2.username;
                        chat.flags |= 64;
                    } else {
                        chat.flags &= -65;
                        chat.username = null;
                    }
                    if (chat2.participants_count != 0 && chat.participants_count == 0) {
                        chat.participants_count = chat2.participants_count;
                        chat.flags |= 131072;
                    }
                    this.chats.put(Integer.valueOf(chat.id), chat);
                }
            }
        }
    }

    public void putChats(ArrayList<Chat> arrayList, boolean z) {
        if (arrayList != null && !arrayList.isEmpty()) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                putChat((Chat) arrayList.get(i), z);
            }
        }
    }

    public void putEncryptedChat(EncryptedChat encryptedChat, boolean z) {
        if (encryptedChat != null) {
            if (z) {
                this.encryptedChats.putIfAbsent(Integer.valueOf(encryptedChat.id), encryptedChat);
            } else {
                this.encryptedChats.put(Integer.valueOf(encryptedChat.id), encryptedChat);
            }
        }
    }

    public void putEncryptedChats(ArrayList<EncryptedChat> arrayList, boolean z) {
        if (arrayList != null && !arrayList.isEmpty()) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                putEncryptedChat((EncryptedChat) arrayList.get(i), z);
            }
        }
    }

    public boolean putUser(User user, boolean z) {
        if (user == null) {
            return false;
        }
        boolean z2 = (!z || user.id / 1000 == 333 || user.id == 777000) ? false : true;
        User user2 = (User) this.users.get(Integer.valueOf(user.id));
        if (user2 == user) {
            return false;
        }
        if (!(user2 == null || TextUtils.isEmpty(user2.username))) {
            this.objectsByUsernames.remove(user2.username.toLowerCase());
        }
        if (!TextUtils.isEmpty(user.username)) {
            this.objectsByUsernames.put(user.username.toLowerCase(), user);
        }
        if (user.min) {
            if (user2 == null) {
                this.users.put(Integer.valueOf(user.id), user);
                return false;
            } else if (z2) {
                return false;
            } else {
                if (user.bot) {
                    if (user.username != null) {
                        user2.username = user.username;
                        user2.flags |= 8;
                    } else {
                        user2.flags &= -9;
                        user2.username = null;
                    }
                }
                if (user.photo != null) {
                    user2.photo = user.photo;
                    user2.flags |= 32;
                    return false;
                }
                user2.flags &= -33;
                user2.photo = null;
                return false;
            }
        } else if (!z2) {
            this.users.put(Integer.valueOf(user.id), user);
            if (user.id == UserConfig.getClientUserId()) {
                UserConfig.setCurrentUser(user);
                UserConfig.saveConfig(true);
            }
            return (user2 == null || user.status == null || user2.status == null || user.status.expires == user2.status.expires) ? false : true;
        } else if (user2 == null) {
            this.users.put(Integer.valueOf(user.id), user);
            return false;
        } else if (!user2.min) {
            return false;
        } else {
            user.min = false;
            if (user2.bot) {
                if (user2.username != null) {
                    user.username = user2.username;
                    user.flags |= 8;
                } else {
                    user.flags &= -9;
                    user.username = null;
                }
            }
            if (user2.photo != null) {
                user.photo = user2.photo;
                user.flags |= 32;
            } else {
                user.flags &= -33;
                user.photo = null;
            }
            this.users.put(Integer.valueOf(user.id), user);
            return false;
        }
    }

    public void putUsers(ArrayList<User> arrayList, boolean z) {
        if (arrayList != null && !arrayList.isEmpty()) {
            int size = arrayList.size();
            int i = 0;
            Object obj = null;
            while (i < size) {
                Object obj2 = putUser((User) arrayList.get(i), z) ? 1 : obj;
                i++;
                obj = obj2;
            }
            if (obj != null) {
                AndroidUtilities.runOnUIThread(new C32619());
            }
        }
    }

    public void registerForPush(final String str) {
        if (str != null && str.length() != 0 && !this.registeringForPush && UserConfig.getClientUserId() != 0) {
            if (!UserConfig.registeredForPush || !str.equals(UserConfig.pushString)) {
                this.registeringForPush = true;
                TLObject tL_account_registerDevice = new TL_account_registerDevice();
                tL_account_registerDevice.token_type = 2;
                tL_account_registerDevice.token = str;
                ConnectionsManager.getInstance().sendRequest(tL_account_registerDevice, new RequestDelegate() {

                    /* renamed from: org.telegram.messenger.MessagesController$104$1 */
                    class C31631 implements Runnable {
                        C31631() {
                        }

                        public void run() {
                            MessagesController.this.registeringForPush = false;
                        }
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLObject instanceof TL_boolTrue) {
                            FileLog.m13726e("registered for push");
                            UserConfig.registeredForPush = true;
                            UserConfig.pushString = str;
                            UserConfig.saveConfig(false);
                        }
                        AndroidUtilities.runOnUIThread(new C31631());
                    }
                });
            }
        }
    }

    public void reloadMentionsCountForChannels(final ArrayList<Integer> arrayList) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                for (int i = 0; i < arrayList.size(); i++) {
                    final long j = (long) (-((Integer) arrayList.get(i)).intValue());
                    TLObject tLRPC$TL_messages_getUnreadMentions = new TLRPC$TL_messages_getUnreadMentions();
                    tLRPC$TL_messages_getUnreadMentions.peer = MessagesController.getInputPeer((int) j);
                    tLRPC$TL_messages_getUnreadMentions.limit = 1;
                    ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getUnreadMentions, new RequestDelegate() {
                        public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                                    if (tLRPC$messages_Messages != null) {
                                        MessagesStorage.getInstance().resetMentionsCount(j, tLRPC$messages_Messages.count != 0 ? tLRPC$messages_Messages.count : tLRPC$messages_Messages.messages.size());
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void reloadWebPages(final long j, HashMap<String, ArrayList<MessageObject>> hashMap) {
        for (Entry entry : hashMap.entrySet()) {
            final String str = (String) entry.getKey();
            ArrayList arrayList = (ArrayList) entry.getValue();
            ArrayList arrayList2 = (ArrayList) this.reloadingWebpages.get(str);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
                this.reloadingWebpages.put(str, arrayList2);
            }
            arrayList2.addAll(arrayList);
            TLObject tLRPC$TL_messages_getWebPagePreview = new TLRPC$TL_messages_getWebPagePreview();
            tLRPC$TL_messages_getWebPagePreview.message = str;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getWebPagePreview, new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            ArrayList arrayList = (ArrayList) MessagesController.this.reloadingWebpages.remove(str);
                            if (arrayList != null) {
                                TLRPC$messages_Messages tLRPC$TL_messages_messages = new TLRPC$TL_messages_messages();
                                if (tLObject instanceof TLRPC$TL_messageMediaWebPage) {
                                    TLRPC$TL_messageMediaWebPage tLRPC$TL_messageMediaWebPage = (TLRPC$TL_messageMediaWebPage) tLObject;
                                    if ((tLRPC$TL_messageMediaWebPage.webpage instanceof TLRPC$TL_webPage) || (tLRPC$TL_messageMediaWebPage.webpage instanceof TLRPC$TL_webPageEmpty)) {
                                        for (int i = 0; i < arrayList.size(); i++) {
                                            ((MessageObject) arrayList.get(i)).messageOwner.media.webpage = tLRPC$TL_messageMediaWebPage.webpage;
                                            if (i == 0) {
                                                ImageLoader.saveMessageThumbs(((MessageObject) arrayList.get(i)).messageOwner);
                                            }
                                            tLRPC$TL_messages_messages.messages.add(((MessageObject) arrayList.get(i)).messageOwner);
                                        }
                                    } else {
                                        MessagesController.this.reloadingWebpagesPending.put(Long.valueOf(tLRPC$TL_messageMediaWebPage.webpage.id), arrayList);
                                    }
                                } else {
                                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                        ((MessageObject) arrayList.get(i2)).messageOwner.media.webpage = new TLRPC$TL_webPageEmpty();
                                        tLRPC$TL_messages_messages.messages.add(((MessageObject) arrayList.get(i2)).messageOwner);
                                    }
                                }
                                if (!tLRPC$TL_messages_messages.messages.isEmpty()) {
                                    MessagesStorage.getInstance().putMessages(tLRPC$TL_messages_messages, j, -2, 0, false);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.replaceMessagesObjects, Long.valueOf(j), arrayList);
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    public void reportSpam(long j, User user, Chat chat, EncryptedChat encryptedChat) {
        if (user != null || chat != null || encryptedChat != null) {
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            edit.putInt("spam3_" + j, 1);
            edit.commit();
            TLObject tLRPC$TL_messages_reportSpam;
            if (((int) j) != 0) {
                tLRPC$TL_messages_reportSpam = new TLRPC$TL_messages_reportSpam();
                if (chat != null) {
                    tLRPC$TL_messages_reportSpam.peer = getInputPeer(-chat.id);
                } else if (user != null) {
                    tLRPC$TL_messages_reportSpam.peer = getInputPeer(user.id);
                }
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_reportSpam, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    }
                }, 2);
            } else if (encryptedChat != null && encryptedChat.access_hash != 0) {
                tLRPC$TL_messages_reportSpam = new TLRPC$TL_messages_reportEncryptedSpam();
                tLRPC$TL_messages_reportSpam.peer = new TLRPC$TL_inputEncryptedChat();
                tLRPC$TL_messages_reportSpam.peer.chat_id = encryptedChat.id;
                tLRPC$TL_messages_reportSpam.peer.access_hash = encryptedChat.access_hash;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_reportSpam, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    }
                }, 2);
            }
        }
    }

    public void saveGif(Document document) {
        TLObject tLRPC$TL_messages_saveGif = new TLRPC$TL_messages_saveGif();
        tLRPC$TL_messages_saveGif.id = new TLRPC$TL_inputDocument();
        tLRPC$TL_messages_saveGif.id.id = document.id;
        tLRPC$TL_messages_saveGif.id.access_hash = document.access_hash;
        tLRPC$TL_messages_saveGif.unsave = false;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_saveGif, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            }
        });
    }

    public void saveRecentSticker(Document document, boolean z) {
        TLObject tLRPC$TL_messages_saveRecentSticker = new TLRPC$TL_messages_saveRecentSticker();
        tLRPC$TL_messages_saveRecentSticker.id = new TLRPC$TL_inputDocument();
        tLRPC$TL_messages_saveRecentSticker.id.id = document.id;
        tLRPC$TL_messages_saveRecentSticker.id.access_hash = document.access_hash;
        tLRPC$TL_messages_saveRecentSticker.unsave = false;
        tLRPC$TL_messages_saveRecentSticker.attached = z;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_saveRecentSticker, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            }
        });
    }

    public void selectAllDialogs() {
        for (int i = 0; i < this.dialogs.size(); i++) {
            ((TLRPC$TL_dialog) this.dialogs.get(i)).setSelected(true);
        }
        sortDialogs(null);
    }

    public void sendBotStart(User user, String str) {
        if (user != null) {
            TLObject tLRPC$TL_messages_startBot = new TLRPC$TL_messages_startBot();
            tLRPC$TL_messages_startBot.bot = getInputUser(user);
            tLRPC$TL_messages_startBot.peer = getInputPeer(user.id);
            tLRPC$TL_messages_startBot.start_param = str;
            tLRPC$TL_messages_startBot.random_id = Utilities.random.nextLong();
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_startBot, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                    }
                }
            });
        }
    }

    public void sendTyping(final long j, final int i, int i2) {
        if (C2885i.m13377b(ApplicationLoader.applicationContext) != 1 && j != 0) {
            HashMap hashMap = (HashMap) this.sendingTypings.get(Integer.valueOf(i));
            if (hashMap == null || hashMap.get(Long.valueOf(j)) == null) {
                if (hashMap == null) {
                    hashMap = new HashMap();
                    this.sendingTypings.put(Integer.valueOf(i), hashMap);
                }
                int i3 = (int) j;
                int i4 = (int) (j >> 32);
                TLObject tLRPC$TL_messages_setTyping;
                int sendRequest;
                if (i3 != 0) {
                    if (i4 != 1) {
                        tLRPC$TL_messages_setTyping = new TLRPC$TL_messages_setTyping();
                        tLRPC$TL_messages_setTyping.peer = getInputPeer(i3);
                        if (tLRPC$TL_messages_setTyping.peer instanceof TLRPC$TL_inputPeerChannel) {
                            Chat chat = getChat(Integer.valueOf(tLRPC$TL_messages_setTyping.peer.channel_id));
                            if (chat == null || !chat.megagroup) {
                                return;
                            }
                        }
                        if (tLRPC$TL_messages_setTyping.peer != null) {
                            if (i == 0) {
                                tLRPC$TL_messages_setTyping.action = new TLRPC$TL_sendMessageTypingAction();
                            } else if (i == 1) {
                                tLRPC$TL_messages_setTyping.action = new TLRPC$TL_sendMessageRecordAudioAction();
                            } else if (i == 2) {
                                tLRPC$TL_messages_setTyping.action = new TLRPC$TL_sendMessageCancelAction();
                            } else if (i == 3) {
                                tLRPC$TL_messages_setTyping.action = new TLRPC$TL_sendMessageUploadDocumentAction();
                            } else if (i == 4) {
                                tLRPC$TL_messages_setTyping.action = new TLRPC$TL_sendMessageUploadPhotoAction();
                            } else if (i == 5) {
                                tLRPC$TL_messages_setTyping.action = new TLRPC$TL_sendMessageUploadVideoAction();
                            } else if (i == 6) {
                                tLRPC$TL_messages_setTyping.action = new TLRPC$TL_sendMessageGamePlayAction();
                            } else if (i == 7) {
                                tLRPC$TL_messages_setTyping.action = new TLRPC$TL_sendMessageRecordRoundAction();
                            } else if (i == 8) {
                                tLRPC$TL_messages_setTyping.action = new TLRPC$TL_sendMessageUploadRoundAction();
                            }
                            hashMap.put(Long.valueOf(j), Boolean.valueOf(true));
                            sendRequest = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_setTyping, new RequestDelegate() {

                                /* renamed from: org.telegram.messenger.MessagesController$56$1 */
                                class C32211 implements Runnable {
                                    C32211() {
                                    }

                                    public void run() {
                                        HashMap hashMap = (HashMap) MessagesController.this.sendingTypings.get(Integer.valueOf(i));
                                        if (hashMap != null) {
                                            hashMap.remove(Long.valueOf(j));
                                        }
                                    }
                                }

                                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                    AndroidUtilities.runOnUIThread(new C32211());
                                }
                            }, 2);
                            if (i2 != 0) {
                                ConnectionsManager.getInstance().bindRequestToGuid(sendRequest, i2);
                            }
                        }
                    }
                } else if (i == 0) {
                    EncryptedChat encryptedChat = getEncryptedChat(Integer.valueOf(i4));
                    if (encryptedChat.auth_key != null && encryptedChat.auth_key.length > 1 && (encryptedChat instanceof TLRPC$TL_encryptedChat)) {
                        tLRPC$TL_messages_setTyping = new TLRPC$TL_messages_setEncryptedTyping();
                        tLRPC$TL_messages_setTyping.peer = new TLRPC$TL_inputEncryptedChat();
                        tLRPC$TL_messages_setTyping.peer.chat_id = encryptedChat.id;
                        tLRPC$TL_messages_setTyping.peer.access_hash = encryptedChat.access_hash;
                        tLRPC$TL_messages_setTyping.typing = true;
                        hashMap.put(Long.valueOf(j), Boolean.valueOf(true));
                        sendRequest = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_setTyping, new RequestDelegate() {

                            /* renamed from: org.telegram.messenger.MessagesController$57$1 */
                            class C32221 implements Runnable {
                                C32221() {
                                }

                                public void run() {
                                    HashMap hashMap = (HashMap) MessagesController.this.sendingTypings.get(Integer.valueOf(i));
                                    if (hashMap != null) {
                                        hashMap.remove(Long.valueOf(j));
                                    }
                                }
                            }

                            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                AndroidUtilities.runOnUIThread(new C32221());
                            }
                        }, 2);
                        if (i2 != 0) {
                            ConnectionsManager.getInstance().bindRequestToGuid(sendRequest, i2);
                        }
                    }
                }
            }
        }
    }

    public void setLastCreatedDialogId(final long j, final boolean z) {
        if (z) {
            this.createdDialogMainThreadIds.add(Long.valueOf(j));
        } else {
            this.createdDialogMainThreadIds.remove(Long.valueOf(j));
        }
        Utilities.stageQueue.postRunnable(new Runnable() {
            public void run() {
                if (z) {
                    MessagesController.this.createdDialogIds.add(Long.valueOf(j));
                } else {
                    MessagesController.this.createdDialogIds.remove(Long.valueOf(j));
                }
            }
        });
    }

    public void setReferer(String str) {
        if (str != null) {
            this.installReferer = str;
            ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putString("installReferer", str).commit();
        }
    }

    public void sortDialogs(HashMap<Integer, Chat> hashMap) {
        System.currentTimeMillis();
        this.dialogsServerOnly.clear();
        this.dialogsGroupsOnly.clear();
        this.dialogsForward.clear();
        Object obj = null;
        int clientUserId = UserConfig.getClientUserId();
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
        int A = C3791b.m13892A(ApplicationLoader.applicationContext);
        this.hiddensIds = C3791b.m13941b(ApplicationLoader.applicationContext);
        this.categoriesIds = C3791b.aa(ApplicationLoader.applicationContext);
        boolean x = C3791b.m14058x(ApplicationLoader.applicationContext);
        ArrayList favouriteIds = Favourite.getFavouriteIds();
        Collections.sort(this.dialogs, this.dialogComparator);
        int i = 0;
        while (i < this.dialogs.size()) {
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) this.dialogs.get(i);
            if ((A != 2 || tLRPC$TL_dialog.unread_count > 0) && (!(A == 4 && getInstance().isDialogMuted(tLRPC$TL_dialog.id)) && (A != 3 || getInstance().isDialogMuted(tLRPC$TL_dialog.id)))) {
                Object obj2;
                Chat chat;
                boolean contains = this.hiddensIds.contains(Long.valueOf(tLRPC$TL_dialog.id));
                if (contains) {
                    if (x) {
                        this.dialogsHidden.add(tLRPC$TL_dialog);
                    }
                }
                if (x || !contains) {
                    this.dialogsAll.add(tLRPC$TL_dialog);
                }
                if (tLRPC$TL_dialog.unread_count > 0) {
                    this.dialogsUnread.add(tLRPC$TL_dialog);
                }
                int i2 = (int) (tLRPC$TL_dialog.id >> 32);
                int i3 = (int) tLRPC$TL_dialog.id;
                if (i3 == clientUserId) {
                    this.dialogsForward.add(0, tLRPC$TL_dialog);
                    obj2 = 1;
                } else {
                    this.dialogsForward.add(tLRPC$TL_dialog);
                    obj2 = obj;
                }
                if (!(i3 == 0 || i2 == 1)) {
                    this.dialogsServerOnly.add(tLRPC$TL_dialog);
                    if (DialogObject.isChannel(tLRPC$TL_dialog)) {
                        chat = getChat(Integer.valueOf(-i3));
                        if (chat != null && ((chat.megagroup && chat.admin_rights != null && chat.admin_rights.post_messages) || chat.creator)) {
                            this.dialogsGroupsOnly.add(tLRPC$TL_dialog);
                        }
                    } else if (i3 < 0) {
                        if (hashMap != null) {
                            chat = (Chat) hashMap.get(Integer.valueOf(-i3));
                            if (!(chat == null || chat.migrated_to == null)) {
                                this.dialogs.remove(i);
                                i--;
                                obj = obj2;
                            }
                        }
                        this.dialogsGroupsOnly.add(tLRPC$TL_dialog);
                    }
                }
                if (((int) tLRPC$TL_dialog.id) != 0 && i2 != 1 && (tLRPC$TL_dialog instanceof TLRPC$TL_dialog)) {
                    if (tLRPC$TL_dialog.id < 0) {
                        chat = getChat(Integer.valueOf(-((int) tLRPC$TL_dialog.id)));
                        if (chat != null && chat.megagroup && chat.creator) {
                            this.dialogsGroupsOnly.add(tLRPC$TL_dialog);
                        }
                        if (chat != null) {
                            if (chat.megagroup) {
                                this.dialogsMegaGroups.add(tLRPC$TL_dialog);
                                this.dialogsGroupsAll.add(tLRPC$TL_dialog);
                            } else if (ChatObject.isChannel(chat)) {
                                this.dialogsChannels.add(tLRPC$TL_dialog);
                                Iterator it = this.categoriesIds.iterator();
                                while (it.hasNext()) {
                                    if (((long) ((Integer) it.next()).intValue()) == Math.abs(tLRPC$TL_dialog.id)) {
                                        this.dialogsAds.add(tLRPC$TL_dialog);
                                        break;
                                    }
                                }
                            } else {
                                this.dialogsGroups.add(tLRPC$TL_dialog);
                                this.dialogsGroupsAll.add(tLRPC$TL_dialog);
                            }
                        }
                    } else {
                        User user = getUser(Integer.valueOf((int) tLRPC$TL_dialog.id));
                        if (user == null) {
                            this.dialogsGroups.add(tLRPC$TL_dialog);
                            this.dialogsGroupsAll.add(tLRPC$TL_dialog);
                        } else if (user.bot) {
                            this.dialogsBots.add(tLRPC$TL_dialog);
                            if (!staticBotArr.contains(tLRPC$TL_dialog)) {
                                staticBotArr.add(tLRPC$TL_dialog);
                            }
                        } else {
                            this.dialogsUsers.add(tLRPC$TL_dialog);
                        }
                    }
                }
                if (favouriteIds.contains(Long.valueOf(tLRPC$TL_dialog.id))) {
                    this.dialogsFavs.add(tLRPC$TL_dialog);
                }
                obj = obj2;
            }
            i++;
        }
        if (obj == null) {
            User currentUser = UserConfig.getCurrentUser();
            if (currentUser != null) {
                TLRPC$TL_dialog tLRPC$TL_dialog2 = new TLRPC$TL_dialog();
                tLRPC$TL_dialog2.id = (long) currentUser.id;
                tLRPC$TL_dialog2.notify_settings = new TLRPC$TL_peerNotifySettings();
                tLRPC$TL_dialog2.peer = new TLRPC$TL_peerUser();
                tLRPC$TL_dialog2.peer.user_id = currentUser.id;
                this.dialogsForward.add(0, tLRPC$TL_dialog2);
            }
        }
    }

    public void startShortPoll(final int i, final boolean z) {
        Utilities.stageQueue.postRunnable(new Runnable() {
            public void run() {
                if (z) {
                    MessagesController.this.needShortPollChannels.delete(i);
                    return;
                }
                MessagesController.this.needShortPollChannels.put(i, 0);
                if (MessagesController.this.shortPollChannels.indexOfKey(i) < 0) {
                    MessagesController.this.getChannelDifference(i, 3, 0, null);
                }
            }
        });
    }

    public void toggleAdminMode(final int i, boolean z) {
        TLObject tLRPC$TL_messages_toggleChatAdmins = new TLRPC$TL_messages_toggleChatAdmins();
        tLRPC$TL_messages_toggleChatAdmins.chat_id = i;
        tLRPC$TL_messages_toggleChatAdmins.enabled = z;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_toggleChatAdmins, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error == null) {
                    MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                    MessagesController.this.loadFullChat(i, 0, true);
                }
            }
        });
    }

    public void toggleUserAdmin(int i, int i2, boolean z) {
        TLObject tLRPC$TL_messages_editChatAdmin = new TLRPC$TL_messages_editChatAdmin();
        tLRPC$TL_messages_editChatAdmin.chat_id = i;
        tLRPC$TL_messages_editChatAdmin.user_id = getInputUser(i2);
        tLRPC$TL_messages_editChatAdmin.is_admin = z;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_editChatAdmin, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            }
        });
    }

    public void toogleChannelInvites(int i, boolean z) {
        TLObject tL_channels_toggleInvites = new TL_channels_toggleInvites();
        tL_channels_toggleInvites.channel = getInputChannel(i);
        tL_channels_toggleInvites.enabled = z;
        ConnectionsManager.getInstance().sendRequest(tL_channels_toggleInvites, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLObject != null) {
                    MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                }
            }
        }, 64);
    }

    public void toogleChannelInvitesHistory(int i, boolean z) {
        TLObject tL_channels_togglePreHistoryHidden = new TL_channels_togglePreHistoryHidden();
        tL_channels_togglePreHistoryHidden.channel = getInputChannel(i);
        tL_channels_togglePreHistoryHidden.enabled = z;
        ConnectionsManager.getInstance().sendRequest(tL_channels_togglePreHistoryHidden, new RequestDelegate() {

            /* renamed from: org.telegram.messenger.MessagesController$90$1 */
            class C32521 implements Runnable {
                C32521() {
                }

                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_CHANNEL));
                }
            }

            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLObject != null) {
                    MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                    AndroidUtilities.runOnUIThread(new C32521());
                }
            }
        }, 64);
    }

    public void toogleChannelSignatures(int i, boolean z) {
        TLObject tL_channels_toggleSignatures = new TL_channels_toggleSignatures();
        tL_channels_toggleSignatures.channel = getInputChannel(i);
        tL_channels_toggleSignatures.enabled = z;
        ConnectionsManager.getInstance().sendRequest(tL_channels_toggleSignatures, new RequestDelegate() {

            /* renamed from: org.telegram.messenger.MessagesController$89$1 */
            class C32501 implements Runnable {
                C32501() {
                }

                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_CHANNEL));
                }
            }

            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLObject != null) {
                    MessagesController.this.processUpdates((TLRPC$Updates) tLObject, false);
                    AndroidUtilities.runOnUIThread(new C32501());
                }
            }
        }, 64);
    }

    public void unFavSelectedDialogs() {
        ArrayList selectedDialogs = getSelectedDialogs();
        for (int i = 0; i < selectedDialogs.size(); i++) {
            long j = ((TLRPC$TL_dialog) selectedDialogs.get(i)).id;
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) getInstance().dialogs_dict.get(Long.valueOf(j));
            if (tLRPC$TL_dialog != null && Favourite.isFavourite(Long.valueOf(tLRPC$TL_dialog.id))) {
                Favourite.deleteFavourite(Long.valueOf(j));
                getInstance().dialogsFavs.remove(tLRPC$TL_dialog);
            }
        }
    }

    public void unMuteSelectedDialogs() {
        ArrayList selectedDialogs = getSelectedDialogs();
        for (int i = 0; i < selectedDialogs.size(); i++) {
            long j = ((TLRPC$TL_dialog) selectedDialogs.get(i)).id;
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            edit.putInt("notify2_" + j, 0);
            MessagesStorage.getInstance().setDialogFlags(j, 0);
            edit.commit();
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) getInstance().dialogs_dict.get(Long.valueOf(j));
            if (tLRPC$TL_dialog != null) {
                tLRPC$TL_dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
            }
            NotificationsController.updateServerNotificationsSettings(j);
        }
    }

    public void unSelectAllDialogs() {
        for (int i = 0; i < this.dialogs.size(); i++) {
            ((TLRPC$TL_dialog) this.dialogs.get(i)).setSelected(false);
        }
        sortDialogs(null);
    }

    public void unblockUser(int i) {
        TLObject tLRPC$TL_contacts_unblock = new TLRPC$TL_contacts_unblock();
        final User user = getUser(Integer.valueOf(i));
        if (user != null) {
            this.blockedUsers.remove(Integer.valueOf(user.id));
            tLRPC$TL_contacts_unblock.id = getInputUser(user);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.blockedUsersDidLoaded, new Object[0]);
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_unblock, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    MessagesStorage.getInstance().deleteBlockedUser(user.id);
                }
            });
        }
    }

    public void unregistedPush() {
        if (UserConfig.registeredForPush && UserConfig.pushString.length() == 0) {
            TLObject tL_account_unregisterDevice = new TL_account_unregisterDevice();
            tL_account_unregisterDevice.token = UserConfig.pushString;
            tL_account_unregisterDevice.token_type = 2;
            ConnectionsManager.getInstance().sendRequest(tL_account_unregisterDevice, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                }
            });
        }
    }

    public void updateChannelAbout(int i, final String str, final ChatFull chatFull) {
        if (chatFull != null) {
            TLObject tL_channels_editAbout = new TL_channels_editAbout();
            tL_channels_editAbout.channel = getInputChannel(i);
            tL_channels_editAbout.about = str;
            ConnectionsManager.getInstance().sendRequest(tL_channels_editAbout, new RequestDelegate() {

                /* renamed from: org.telegram.messenger.MessagesController$91$1 */
                class C32531 implements Runnable {
                    C32531() {
                    }

                    public void run() {
                        chatFull.about = str;
                        MessagesStorage.getInstance().updateChatInfo(chatFull, false);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, chatFull, Integer.valueOf(0), Boolean.valueOf(false), null);
                    }
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLObject instanceof TL_boolTrue) {
                        AndroidUtilities.runOnUIThread(new C32531());
                    }
                }
            }, 64);
        }
    }

    public void updateChannelUserName(final int i, final String str) {
        TLObject tL_channels_updateUsername = new TL_channels_updateUsername();
        tL_channels_updateUsername.channel = getInputChannel(i);
        tL_channels_updateUsername.username = str;
        ConnectionsManager.getInstance().sendRequest(tL_channels_updateUsername, new RequestDelegate() {

            /* renamed from: org.telegram.messenger.MessagesController$92$1 */
            class C32541 implements Runnable {
                C32541() {
                }

                public void run() {
                    Chat chat = MessagesController.this.getChat(Integer.valueOf(i));
                    if (str.length() != 0) {
                        chat.flags |= 64;
                    } else {
                        chat.flags &= -65;
                    }
                    chat.username = str;
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(chat);
                    MessagesStorage.getInstance().putUsersAndChats(null, arrayList, true, true);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_CHANNEL));
                }
            }

            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLObject instanceof TL_boolTrue) {
                    AndroidUtilities.runOnUIThread(new C32541());
                }
            }
        }, 64);
    }

    public void updateConfig(final TLRPC$TL_config tLRPC$TL_config) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                LocaleController.getInstance().loadRemoteLanguages();
                MessagesController.this.maxMegagroupCount = tLRPC$TL_config.megagroup_size_max;
                MessagesController.this.maxGroupCount = tLRPC$TL_config.chat_size_max;
                MessagesController.this.groupBigSize = tLRPC$TL_config.chat_big_size;
                MessagesController.this.disabledFeatures = tLRPC$TL_config.disabled_features;
                MessagesController.this.maxEditTime = tLRPC$TL_config.edit_time_limit;
                MessagesController.this.ratingDecay = tLRPC$TL_config.rating_e_decay;
                MessagesController.this.maxRecentGifsCount = tLRPC$TL_config.saved_gifs_limit;
                MessagesController.this.maxRecentStickersCount = tLRPC$TL_config.stickers_recent_limit;
                MessagesController.this.maxFaveStickersCount = tLRPC$TL_config.stickers_faved_limit;
                MessagesController.this.linkPrefix = tLRPC$TL_config.me_url_prefix;
                if (MessagesController.this.linkPrefix.endsWith("/")) {
                    MessagesController.this.linkPrefix = MessagesController.this.linkPrefix.substring(0, MessagesController.this.linkPrefix.length() - 1);
                }
                if (MessagesController.this.linkPrefix.startsWith("https://")) {
                    MessagesController.this.linkPrefix = MessagesController.this.linkPrefix.substring(8);
                } else if (MessagesController.this.linkPrefix.startsWith("http://")) {
                    MessagesController.this.linkPrefix = MessagesController.this.linkPrefix.substring(7);
                }
                MessagesController.this.callReceiveTimeout = tLRPC$TL_config.call_receive_timeout_ms;
                MessagesController.this.callRingTimeout = tLRPC$TL_config.call_ring_timeout_ms;
                MessagesController.this.callConnectTimeout = tLRPC$TL_config.call_connect_timeout_ms;
                MessagesController.this.callPacketTimeout = tLRPC$TL_config.call_packet_timeout_ms;
                MessagesController.this.maxPinnedDialogsCount = tLRPC$TL_config.pinned_dialogs_count_max;
                MessagesController.this.defaultP2pContacts = tLRPC$TL_config.default_p2p_contacts;
                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                edit.putInt("maxGroupCount", MessagesController.this.maxGroupCount);
                edit.putInt("maxMegagroupCount", MessagesController.this.maxMegagroupCount);
                edit.putInt("groupBigSize", MessagesController.this.groupBigSize);
                edit.putInt("maxEditTime", MessagesController.this.maxEditTime);
                edit.putInt("ratingDecay", MessagesController.this.ratingDecay);
                edit.putInt("maxRecentGifsCount", MessagesController.this.maxRecentGifsCount);
                edit.putInt("maxRecentStickersCount", MessagesController.this.maxRecentStickersCount);
                edit.putInt("maxFaveStickersCount", MessagesController.this.maxFaveStickersCount);
                edit.putInt("callReceiveTimeout", MessagesController.this.callReceiveTimeout);
                edit.putInt("callRingTimeout", MessagesController.this.callRingTimeout);
                edit.putInt("callConnectTimeout", MessagesController.this.callConnectTimeout);
                edit.putInt("callPacketTimeout", MessagesController.this.callPacketTimeout);
                edit.putString("linkPrefix", MessagesController.this.linkPrefix);
                edit.putInt("maxPinnedDialogsCount", MessagesController.this.maxPinnedDialogsCount);
                edit.putBoolean("defaultP2pContacts", MessagesController.this.defaultP2pContacts);
                try {
                    AbstractSerializedData serializedData = new SerializedData();
                    serializedData.writeInt32(MessagesController.this.disabledFeatures.size());
                    Iterator it = MessagesController.this.disabledFeatures.iterator();
                    while (it.hasNext()) {
                        ((TLRPC$TL_disabledFeature) it.next()).serializeToStream(serializedData);
                    }
                    String encodeToString = Base64.encodeToString(serializedData.toByteArray(), 0);
                    if (encodeToString.length() != 0) {
                        edit.putString("disabledFeatures", encodeToString);
                    }
                } catch (Throwable e) {
                    edit.remove("disabledFeatures");
                    FileLog.m13728e(e);
                }
                edit.commit();
            }
        });
    }

    public void updateDialogSelectStatus(long j) {
        boolean z = false;
        Iterator it = this.dialogs.iterator();
        int i = 0;
        while (it.hasNext()) {
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) it.next();
            if (j == tLRPC$TL_dialog.id) {
                TLRPC$TL_dialog tLRPC$TL_dialog2 = (TLRPC$TL_dialog) this.dialogs.get(i);
                if (!tLRPC$TL_dialog.isSelected) {
                    z = true;
                }
                tLRPC$TL_dialog2.setSelected(z);
                sortDialogs(null);
            }
            i++;
        }
        sortDialogs(null);
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
        this.hiddensIds = C3791b.m13941b(ApplicationLoader.applicationContext);
        this.categoriesIds = C3791b.aa(ApplicationLoader.applicationContext);
        for (int i = 0; i < this.dialogs.size(); i++) {
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) this.dialogs.get(i);
            if (this.hiddensIds.contains(Long.valueOf(tLRPC$TL_dialog.id))) {
                if (C3791b.m14058x(ApplicationLoader.applicationContext)) {
                    this.dialogsHidden.add(tLRPC$TL_dialog);
                } else {
                }
            }
            if (tLRPC$TL_dialog.unread_count > 0) {
                this.dialogsUnread.add(tLRPC$TL_dialog);
            }
            if (C3791b.m14058x(ApplicationLoader.applicationContext) || !this.hiddensIds.contains(Long.valueOf(tLRPC$TL_dialog.id))) {
                this.dialogsAll.add(tLRPC$TL_dialog);
            }
            int i2 = (int) (tLRPC$TL_dialog.id >> 32);
            if (!(((int) tLRPC$TL_dialog.id) == 0 || i2 == 1)) {
                if (tLRPC$TL_dialog instanceof TLRPC$TL_dialog) {
                    if (tLRPC$TL_dialog.id < 0) {
                        this.dialogsGroupsOnly.add(tLRPC$TL_dialog);
                        this.dialogsGroups.add(tLRPC$TL_dialog);
                        this.dialogsGroupsAll.add(tLRPC$TL_dialog);
                    } else {
                        User user = getUser(Integer.valueOf((int) tLRPC$TL_dialog.id));
                        if (user != null) {
                            if (user.bot) {
                                this.dialogsBots.add(tLRPC$TL_dialog);
                            } else {
                                this.dialogsUsers.add(tLRPC$TL_dialog);
                            }
                        }
                    }
                }
                if (getEncryptedChat(Integer.valueOf(i2)) instanceof TLRPC$TL_encryptedChat) {
                    this.dialogsUsers.add(tLRPC$TL_dialog);
                }
                if (Favourite.isFavourite(Long.valueOf(tLRPC$TL_dialog.id))) {
                    this.dialogsFavs.add(tLRPC$TL_dialog);
                }
            }
        }
    }

    protected void updateInterfaceWithMessages(long j, ArrayList<MessageObject> arrayList) {
        updateInterfaceWithMessages(j, arrayList, false);
    }

    protected void updateInterfaceWithMessages(long j, ArrayList<MessageObject> arrayList, boolean z) {
        if (arrayList != null && !arrayList.isEmpty()) {
            Object obj;
            Object obj2 = ((int) j) == 0 ? 1 : null;
            MessageObject messageObject = null;
            int i = 0;
            Object obj3 = null;
            int i2 = 0;
            while (i2 < arrayList.size()) {
                MessageObject messageObject2 = (MessageObject) arrayList.get(i2);
                if (messageObject == null || ((obj2 == null && messageObject2.getId() > messageObject.getId()) || (((obj2 != null || (messageObject2.getId() < 0 && messageObject.getId() < 0)) && messageObject2.getId() < messageObject.getId()) || messageObject2.messageOwner.date > messageObject.messageOwner.date))) {
                    if (messageObject2.messageOwner.to_id.channel_id != 0) {
                        i = messageObject2.messageOwner.to_id.channel_id;
                        messageObject = messageObject2;
                    } else {
                        messageObject = messageObject2;
                    }
                }
                if (!(!messageObject2.isOut() || messageObject2.isSending() || messageObject2.isForwarded())) {
                    if (messageObject2.isNewGif()) {
                        StickersQuery.addRecentGif(messageObject2.messageOwner.media.document, messageObject2.messageOwner.date);
                    } else if (messageObject2.isSticker()) {
                        StickersQuery.addRecentSticker(0, messageObject2.messageOwner.media.document, messageObject2.messageOwner.date, false);
                    }
                }
                obj = (messageObject2.isOut() && messageObject2.isSent()) ? 1 : obj3;
                i2++;
                obj3 = obj;
            }
            MessagesQuery.loadReplyMessagesForMessages(arrayList, j);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didReceivedNewMessages, Long.valueOf(j), arrayList);
            if (messageObject != null) {
                TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) this.dialogs_dict.get(Long.valueOf(j));
                MessageObject messageObject3;
                if (!(messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatMigrateTo)) {
                    if (tLRPC$TL_dialog == null) {
                        if (!z) {
                            Chat chat = getChat(Integer.valueOf(i));
                            if (i != 0 && chat == null) {
                                return;
                            }
                            if (chat == null || !chat.left) {
                                TLRPC$TL_dialog tLRPC$TL_dialog2 = new TLRPC$TL_dialog();
                                tLRPC$TL_dialog2.id = j;
                                tLRPC$TL_dialog2.unread_count = 0;
                                tLRPC$TL_dialog2.top_message = messageObject.getId();
                                tLRPC$TL_dialog2.last_message_date = messageObject.messageOwner.date;
                                tLRPC$TL_dialog2.flags = ChatObject.isChannel(chat) ? 1 : 0;
                                this.dialogs_dict.put(Long.valueOf(j), tLRPC$TL_dialog2);
                                this.dialogs.add(tLRPC$TL_dialog2);
                                this.dialogMessage.put(Long.valueOf(j), messageObject);
                                if (messageObject.messageOwner.to_id.channel_id == 0) {
                                    this.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                                    if (messageObject.messageOwner.random_id != 0) {
                                        this.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                                    }
                                }
                                this.nextDialogsCacheOffset++;
                                obj = 1;
                            } else {
                                return;
                            }
                        }
                        obj = null;
                    } else {
                        if ((tLRPC$TL_dialog.top_message > 0 && messageObject.getId() > 0 && messageObject.getId() > tLRPC$TL_dialog.top_message) || ((tLRPC$TL_dialog.top_message < 0 && messageObject.getId() < 0 && messageObject.getId() < tLRPC$TL_dialog.top_message) || !this.dialogMessage.containsKey(Long.valueOf(j)) || tLRPC$TL_dialog.top_message < 0 || tLRPC$TL_dialog.last_message_date <= messageObject.messageOwner.date)) {
                            messageObject3 = (MessageObject) this.dialogMessagesByIds.remove(Integer.valueOf(tLRPC$TL_dialog.top_message));
                            if (!(messageObject3 == null || messageObject3.messageOwner.random_id == 0)) {
                                this.dialogMessagesByRandomIds.remove(Long.valueOf(messageObject3.messageOwner.random_id));
                            }
                            tLRPC$TL_dialog.top_message = messageObject.getId();
                            if (z) {
                                obj = null;
                            } else {
                                tLRPC$TL_dialog.last_message_date = messageObject.messageOwner.date;
                                obj = 1;
                            }
                            this.dialogMessage.put(Long.valueOf(j), messageObject);
                            if (messageObject.messageOwner.to_id.channel_id == 0) {
                                this.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                                if (messageObject.messageOwner.random_id != 0) {
                                    this.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                                }
                            }
                        }
                        obj = null;
                    }
                    if (obj != null) {
                        sortDialogs(null);
                    }
                    if (obj3 != null) {
                        SearchQuery.increasePeerRaiting(j);
                    }
                } else if (tLRPC$TL_dialog != null) {
                    this.dialogs.remove(tLRPC$TL_dialog);
                    this.dialogsServerOnly.remove(tLRPC$TL_dialog);
                    this.dialogsGroupsOnly.remove(tLRPC$TL_dialog);
                    this.dialogs_dict.remove(Long.valueOf(tLRPC$TL_dialog.id));
                    this.dialogs_read_inbox_max.remove(Long.valueOf(tLRPC$TL_dialog.id));
                    this.dialogs_read_outbox_max.remove(Long.valueOf(tLRPC$TL_dialog.id));
                    this.nextDialogsCacheOffset--;
                    this.dialogMessage.remove(Long.valueOf(tLRPC$TL_dialog.id));
                    messageObject3 = (MessageObject) this.dialogMessagesByIds.remove(Integer.valueOf(tLRPC$TL_dialog.top_message));
                    if (!(messageObject3 == null || messageObject3.messageOwner.random_id == 0)) {
                        this.dialogMessagesByRandomIds.remove(Long.valueOf(messageObject3.messageOwner.random_id));
                    }
                    tLRPC$TL_dialog.top_message = 0;
                    NotificationsController.getInstance().removeNotificationsForDialog(tLRPC$TL_dialog.id);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.needReloadRecentDialogsSearch, new Object[0]);
                }
            }
        }
    }

    public void updateTimerProc() {
        ArrayList arrayList;
        int i;
        int intValue;
        Long l;
        int i2;
        ArrayList arrayList2;
        long currentTimeMillis = System.currentTimeMillis();
        checkDeletingTask(false);
        if (UserConfig.isClientActivated()) {
            TLObject tL_account_updateStatus;
            if (ConnectionsManager.getInstance().getPauseTime() == 0 && ApplicationLoader.isScreenOn && !ApplicationLoader.mainInterfacePausedStageQueue) {
                if (ApplicationLoader.mainInterfacePausedStageQueueTime != 0 && Math.abs(ApplicationLoader.mainInterfacePausedStageQueueTime - System.currentTimeMillis()) > 1000 && this.statusSettingState != 1 && (this.lastStatusUpdateTime == 0 || Math.abs(System.currentTimeMillis() - this.lastStatusUpdateTime) >= 55000 || this.offlineSent)) {
                    this.statusSettingState = 1;
                    if (this.statusRequest != 0) {
                        ConnectionsManager.getInstance().cancelRequest(this.statusRequest, true);
                    }
                    tL_account_updateStatus = new TL_account_updateStatus();
                    tL_account_updateStatus.offline = false;
                    if (C2885i.m13377b(ApplicationLoader.applicationContext) == 1) {
                        tL_account_updateStatus.offline = true;
                    }
                    this.statusRequest = ConnectionsManager.getInstance().sendRequest(tL_account_updateStatus, new RequestDelegate() {
                        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            if (tLRPC$TL_error == null) {
                                MessagesController.this.lastStatusUpdateTime = System.currentTimeMillis();
                                MessagesController.this.offlineSent = false;
                                MessagesController.this.statusSettingState = 0;
                            } else if (MessagesController.this.lastStatusUpdateTime != 0) {
                                MessagesController.this.lastStatusUpdateTime = MessagesController.this.lastStatusUpdateTime + DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
                            }
                            MessagesController.this.statusRequest = 0;
                        }
                    });
                }
            } else if (!(this.statusSettingState == 2 || this.offlineSent || Math.abs(System.currentTimeMillis() - ConnectionsManager.getInstance().getPauseTime()) < 2000)) {
                this.statusSettingState = 2;
                if (this.statusRequest != 0) {
                    ConnectionsManager.getInstance().cancelRequest(this.statusRequest, true);
                }
                tL_account_updateStatus = new TL_account_updateStatus();
                tL_account_updateStatus.offline = true;
                this.statusRequest = ConnectionsManager.getInstance().sendRequest(tL_account_updateStatus, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            MessagesController.this.offlineSent = true;
                        } else if (MessagesController.this.lastStatusUpdateTime != 0) {
                            MessagesController.this.lastStatusUpdateTime = MessagesController.this.lastStatusUpdateTime + DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
                        }
                        MessagesController.this.statusRequest = 0;
                    }
                });
            }
            if (!this.updatesQueueChannels.isEmpty()) {
                arrayList = new ArrayList(this.updatesQueueChannels.keySet());
                for (i = 0; i < arrayList.size(); i++) {
                    intValue = ((Integer) arrayList.get(i)).intValue();
                    l = (Long) this.updatesStartWaitTimeChannels.get(Integer.valueOf(intValue));
                    if (l != null && l.longValue() + 1500 < currentTimeMillis) {
                        FileLog.m13726e("QUEUE CHANNEL " + intValue + " UPDATES WAIT TIMEOUT - CHECK QUEUE");
                        processChannelsUpdatesQueue(intValue, 0);
                    }
                }
            }
            i2 = 0;
            while (i2 < 3) {
                if (getUpdatesStartTime(i2) != 0 && getUpdatesStartTime(i2) + 1500 < currentTimeMillis) {
                    FileLog.m13726e(i2 + " QUEUE UPDATES WAIT TIMEOUT - CHECK QUEUE");
                    processUpdatesQueue(i2, 0);
                }
                i2++;
            }
        }
        if (this.channelViewsToSend.size() != 0 && Math.abs(System.currentTimeMillis() - this.lastViewsCheckTime) >= DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) {
            this.lastViewsCheckTime = System.currentTimeMillis();
            i = 0;
            while (i < this.channelViewsToSend.size()) {
                final int keyAt = this.channelViewsToSend.keyAt(i);
                final TLObject tLRPC$TL_messages_getMessagesViews = new TLRPC$TL_messages_getMessagesViews();
                tLRPC$TL_messages_getMessagesViews.peer = getInputPeer(keyAt);
                tLRPC$TL_messages_getMessagesViews.id = (ArrayList) this.channelViewsToSend.get(keyAt);
                tLRPC$TL_messages_getMessagesViews.increment = i == 0;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getMessagesViews, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            SparseIntArray sparseIntArray;
                            TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
                            final SparseArray sparseArray = new SparseArray();
                            SparseIntArray sparseIntArray2 = (SparseIntArray) sparseArray.get(keyAt);
                            if (sparseIntArray2 == null) {
                                sparseIntArray2 = new SparseIntArray();
                                sparseArray.put(keyAt, sparseIntArray2);
                                sparseIntArray = sparseIntArray2;
                            } else {
                                sparseIntArray = sparseIntArray2;
                            }
                            int i = 0;
                            while (i < tLRPC$TL_messages_getMessagesViews.id.size() && i < tLRPC$Vector.objects.size()) {
                                sparseIntArray.put(((Integer) tLRPC$TL_messages_getMessagesViews.id.get(i)).intValue(), ((Integer) tLRPC$Vector.objects.get(i)).intValue());
                                i++;
                            }
                            MessagesStorage.getInstance().putChannelViews(sparseArray, tLRPC$TL_messages_getMessagesViews.peer instanceof TLRPC$TL_inputPeerChannel);
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didUpdatedMessagesViews, sparseArray);
                                }
                            });
                        }
                    }
                });
                i++;
            }
            this.channelViewsToSend.clear();
        }
        if (!this.onlinePrivacy.isEmpty()) {
            arrayList = null;
            intValue = ConnectionsManager.getInstance().getCurrentTime();
            for (Entry entry : this.onlinePrivacy.entrySet()) {
                if (((Integer) entry.getValue()).intValue() < intValue - 30) {
                    arrayList2 = arrayList == null ? new ArrayList() : arrayList;
                    arrayList2.add(entry.getKey());
                } else {
                    arrayList2 = arrayList;
                }
                arrayList = arrayList2;
            }
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    this.onlinePrivacy.remove((Integer) it.next());
                }
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(4));
                    }
                });
            }
        }
        if (this.shortPollChannels.size() != 0) {
            for (i2 = 0; i2 < this.shortPollChannels.size(); i2++) {
                i = this.shortPollChannels.keyAt(i2);
                if (((long) this.shortPollChannels.get(i)) < System.currentTimeMillis() / 1000) {
                    this.shortPollChannels.delete(i);
                    if (this.needShortPollChannels.indexOfKey(i) >= 0) {
                        getChannelDifference(i);
                    }
                }
            }
        }
        if (!(this.printingUsers.isEmpty() && this.lastPrintingStringCount == this.printingUsers.size())) {
            ArrayList arrayList3 = new ArrayList(this.printingUsers.keySet());
            intValue = 0;
            Object obj = null;
            while (intValue < arrayList3.size()) {
                l = (Long) arrayList3.get(intValue);
                arrayList2 = (ArrayList) this.printingUsers.get(l);
                int i3 = 0;
                Object obj2 = obj;
                while (i3 < arrayList2.size()) {
                    PrintingUser printingUser = (PrintingUser) arrayList2.get(i3);
                    if (printingUser.lastTime + ((long) (printingUser.action instanceof TLRPC$TL_sendMessageGamePlayAction ? DefaultLoadControl.DEFAULT_MAX_BUFFER_MS : 5900)) < currentTimeMillis) {
                        obj2 = 1;
                        arrayList2.remove(printingUser);
                        i3--;
                    }
                    i3++;
                }
                if (arrayList2.isEmpty()) {
                    this.printingUsers.remove(l);
                    arrayList3.remove(intValue);
                    intValue--;
                }
                intValue++;
                obj = obj2;
            }
            updatePrintingStrings();
            if (obj != null) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(64));
                    }
                });
            }
        }
        LocationController.getInstance().update();
    }

    public void uploadAndApplyUserAvatar(PhotoSize photoSize) {
        if (photoSize != null) {
            this.uploadingAvatar = FileLoader.getInstance().getDirectory(4) + "/" + photoSize.location.volume_id + "_" + photoSize.location.local_id + ".jpg";
            FileLoader.getInstance().uploadFile(this.uploadingAvatar, false, true, 16777216);
        }
    }
}
