package org.telegram.ui.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.location.Location;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.EmojiSuggestion;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.SendMessagesHelper$LocationProvider;
import org.telegram.messenger.SendMessagesHelper$LocationProvider.LocationProviderDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$BotInfo;
import org.telegram.tgnet.TLRPC$BotInlineResult;
import org.telegram.tgnet.TLRPC$ChannelParticipant;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ChatParticipant;
import org.telegram.tgnet.TLRPC$TL_botCommand;
import org.telegram.tgnet.TLRPC$TL_botInlineMessageMediaAuto;
import org.telegram.tgnet.TLRPC$TL_channelFull;
import org.telegram.tgnet.TLRPC$TL_channelParticipantsSearch;
import org.telegram.tgnet.TLRPC$TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC$TL_channels_getParticipants;
import org.telegram.tgnet.TLRPC$TL_contacts_resolveUsername;
import org.telegram.tgnet.TLRPC$TL_contacts_resolvedPeer;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inlineBotSwitchPM;
import org.telegram.tgnet.TLRPC$TL_inputGeoPoint;
import org.telegram.tgnet.TLRPC$TL_inputPeerEmpty;
import org.telegram.tgnet.TLRPC$TL_messages_botResults;
import org.telegram.tgnet.TLRPC$TL_messages_getInlineBotResults;
import org.telegram.tgnet.TLRPC$TL_photo;
import org.telegram.tgnet.TLRPC$TL_topPeer;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Adapters.SearchAdapterHelper.HashtagObject;
import org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate;
import org.telegram.ui.Cells.BotSwitchCell;
import org.telegram.ui.Cells.ContextLinkCell;
import org.telegram.ui.Cells.ContextLinkCell.ContextLinkCellDelegate;
import org.telegram.ui.Cells.MentionCell;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class MentionsAdapter extends SelectionAdapter {
    private boolean allowNewMentions = true;
    private HashMap<Integer, TLRPC$BotInfo> botInfo;
    private int botsCount;
    private int channelLastReqId;
    private int channelReqId;
    private boolean contextMedia;
    private int contextQueryReqid;
    private Runnable contextQueryRunnable;
    private int contextUsernameReqid;
    private MentionsAdapterDelegate delegate;
    private long dialog_id;
    private User foundContextBot;
    private TLRPC$ChatFull info;
    private boolean inlineMediaEnabled = true;
    private boolean isDarkTheme;
    private boolean isSearchingMentions;
    private Location lastKnownLocation;
    private int lastPosition;
    private String lastText;
    private boolean lastUsernameOnly;
    private SendMessagesHelper$LocationProvider locationProvider = new SendMessagesHelper$LocationProvider(new C20291()) {
        public void stop() {
            super.stop();
            MentionsAdapter.this.lastKnownLocation = null;
        }
    };
    private Context mContext;
    private ArrayList<MessageObject> messages;
    private boolean needBotContext = true;
    private boolean needUsernames = true;
    private String nextQueryOffset;
    private boolean noUserName;
    private ChatActivity parentFragment;
    private int resultLength;
    private int resultStartPosition;
    private SearchAdapterHelper searchAdapterHelper;
    private Runnable searchGlobalRunnable;
    private ArrayList<TLRPC$BotInlineResult> searchResultBotContext;
    private HashMap<String, TLRPC$BotInlineResult> searchResultBotContextById;
    private TLRPC$TL_inlineBotSwitchPM searchResultBotContextSwitch;
    private ArrayList<String> searchResultCommands;
    private ArrayList<String> searchResultCommandsHelp;
    private ArrayList<User> searchResultCommandsUsers;
    private ArrayList<String> searchResultHashtags;
    private ArrayList<EmojiSuggestion> searchResultSuggestions;
    private ArrayList<User> searchResultUsernames;
    private HashMap<Integer, User> searchResultUsernamesMap;
    private String searchingContextQuery;
    private String searchingContextUsername;

    /* renamed from: org.telegram.ui.Adapters.MentionsAdapter$1 */
    class C20291 implements LocationProviderDelegate {
        C20291() {
        }

        public void onLocationAcquired(Location location) {
            if (MentionsAdapter.this.foundContextBot != null && MentionsAdapter.this.foundContextBot.bot_inline_geo) {
                MentionsAdapter.this.lastKnownLocation = location;
                MentionsAdapter.this.searchForContextBotResults(true, MentionsAdapter.this.foundContextBot, MentionsAdapter.this.searchingContextQuery, "");
            }
        }

        public void onUnableLocationAcquire() {
            MentionsAdapter.this.onLocationUnavailable();
        }
    }

    /* renamed from: org.telegram.ui.Adapters.MentionsAdapter$3 */
    class C20313 implements SearchAdapterHelperDelegate {
        C20313() {
        }

        public void onDataSetChanged() {
            MentionsAdapter.this.notifyDataSetChanged();
        }

        public void onSetHashtags(ArrayList<HashtagObject> arrayList, HashMap<String, HashtagObject> hashMap) {
            if (MentionsAdapter.this.lastText != null) {
                MentionsAdapter.this.searchUsernameOrHashtag(MentionsAdapter.this.lastText, MentionsAdapter.this.lastPosition, MentionsAdapter.this.messages, MentionsAdapter.this.lastUsernameOnly);
            }
        }
    }

    public interface MentionsAdapterDelegate {
        void needChangePanelVisibility(boolean z);

        void onContextClick(TLRPC$BotInlineResult tLRPC$BotInlineResult);

        void onContextSearch(boolean z);
    }

    static /* synthetic */ int access$3004(MentionsAdapter x0) {
        int i = x0.channelLastReqId + 1;
        x0.channelLastReqId = i;
        return i;
    }

    public MentionsAdapter(Context context, boolean darkTheme, long did, MentionsAdapterDelegate mentionsAdapterDelegate) {
        this.mContext = context;
        this.delegate = mentionsAdapterDelegate;
        this.isDarkTheme = darkTheme;
        this.dialog_id = did;
        this.searchAdapterHelper = new SearchAdapterHelper();
        this.searchAdapterHelper.setDelegate(new C20313());
    }

    public void onDestroy() {
        if (this.locationProvider != null) {
            this.locationProvider.stop();
        }
        if (this.contextQueryRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.contextQueryRunnable);
            this.contextQueryRunnable = null;
        }
        if (this.contextUsernameReqid != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.contextUsernameReqid, true);
            this.contextUsernameReqid = 0;
        }
        if (this.contextQueryReqid != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.contextQueryReqid, true);
            this.contextQueryReqid = 0;
        }
        this.foundContextBot = null;
        this.inlineMediaEnabled = true;
        this.searchingContextUsername = null;
        this.searchingContextQuery = null;
        this.noUserName = false;
    }

    public void setAllowNewMentions(boolean value) {
        this.allowNewMentions = value;
    }

    public void setParentFragment(ChatActivity fragment) {
        this.parentFragment = fragment;
    }

    public void setChatInfo(TLRPC$ChatFull chatInfo) {
        this.info = chatInfo;
        if (!(this.inlineMediaEnabled || this.foundContextBot == null || this.parentFragment == null)) {
            TLRPC$Chat chat = this.parentFragment.getCurrentChat();
            if (chat != null) {
                this.inlineMediaEnabled = ChatObject.canSendStickers(chat);
                if (this.inlineMediaEnabled) {
                    this.searchResultUsernames = null;
                    notifyDataSetChanged();
                    this.delegate.needChangePanelVisibility(false);
                    processFoundUser(this.foundContextBot);
                }
            }
        }
        if (this.lastText != null) {
            searchUsernameOrHashtag(this.lastText, this.lastPosition, this.messages, this.lastUsernameOnly);
        }
    }

    public void setNeedUsernames(boolean value) {
        this.needUsernames = value;
    }

    public void setNeedBotContext(boolean value) {
        this.needBotContext = value;
    }

    public void setBotInfo(HashMap<Integer, TLRPC$BotInfo> info) {
        this.botInfo = info;
    }

    public void setBotsCount(int count) {
        this.botsCount = count;
    }

    public void clearRecentHashtags() {
        this.searchAdapterHelper.clearRecentHashtags();
        this.searchResultHashtags.clear();
        notifyDataSetChanged();
        if (this.delegate != null) {
            this.delegate.needChangePanelVisibility(false);
        }
    }

    public TLRPC$TL_inlineBotSwitchPM getBotContextSwitch() {
        return this.searchResultBotContextSwitch;
    }

    public int getContextBotId() {
        return this.foundContextBot != null ? this.foundContextBot.id : 0;
    }

    public User getContextBotUser() {
        return this.foundContextBot != null ? this.foundContextBot : null;
    }

    public String getContextBotName() {
        return this.foundContextBot != null ? this.foundContextBot.username : "";
    }

    private void processFoundUser(User user) {
        this.contextUsernameReqid = 0;
        this.locationProvider.stop();
        if (user == null || !user.bot || user.bot_inline_placeholder == null) {
            this.foundContextBot = null;
            this.inlineMediaEnabled = true;
        } else {
            this.foundContextBot = user;
            if (this.parentFragment != null) {
                TLRPC$Chat chat = this.parentFragment.getCurrentChat();
                if (chat != null) {
                    this.inlineMediaEnabled = ChatObject.canSendStickers(chat);
                    if (!this.inlineMediaEnabled) {
                        notifyDataSetChanged();
                        this.delegate.needChangePanelVisibility(true);
                        return;
                    }
                }
            }
            if (this.foundContextBot.bot_inline_geo) {
                if (ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("inlinegeo_" + this.foundContextBot.id, false) || this.parentFragment == null || this.parentFragment.getParentActivity() == null) {
                    checkLocationPermissionsOrStart();
                } else {
                    final User foundContextBotFinal = this.foundContextBot;
                    Builder builder = new Builder(this.parentFragment.getParentActivity());
                    builder.setTitle(LocaleController.getString("ShareYouLocationTitle", R.string.ShareYouLocationTitle));
                    builder.setMessage(LocaleController.getString("ShareYouLocationInline", R.string.ShareYouLocationInline));
                    final boolean[] buttonClicked = new boolean[1];
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            buttonClicked[0] = true;
                            if (foundContextBotFinal != null) {
                                ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putBoolean("inlinegeo_" + foundContextBotFinal.id, true).commit();
                                MentionsAdapter.this.checkLocationPermissionsOrStart();
                            }
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            buttonClicked[0] = true;
                            MentionsAdapter.this.onLocationUnavailable();
                        }
                    });
                    this.parentFragment.showDialog(builder.create(), new OnDismissListener() {
                        public void onDismiss(DialogInterface dialog) {
                            if (!buttonClicked[0]) {
                                MentionsAdapter.this.onLocationUnavailable();
                            }
                        }
                    });
                }
            }
        }
        if (this.foundContextBot == null) {
            this.noUserName = true;
            return;
        }
        if (this.delegate != null) {
            this.delegate.onContextSearch(true);
        }
        searchForContextBotResults(true, this.foundContextBot, this.searchingContextQuery, "");
    }

    private void searchForContextBot(final String username, final String query) {
        if (this.foundContextBot == null || this.foundContextBot.username == null || !this.foundContextBot.username.equals(username) || this.searchingContextQuery == null || !this.searchingContextQuery.equals(query)) {
            this.searchResultBotContext = null;
            this.searchResultBotContextById = null;
            this.searchResultBotContextSwitch = null;
            notifyDataSetChanged();
            if (this.foundContextBot != null) {
                if (this.inlineMediaEnabled || username == null || query == null) {
                    this.delegate.needChangePanelVisibility(false);
                } else {
                    return;
                }
            }
            if (this.contextQueryRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(this.contextQueryRunnable);
                this.contextQueryRunnable = null;
            }
            if (TextUtils.isEmpty(username) || !(this.searchingContextUsername == null || this.searchingContextUsername.equals(username))) {
                if (this.contextUsernameReqid != 0) {
                    ConnectionsManager.getInstance().cancelRequest(this.contextUsernameReqid, true);
                    this.contextUsernameReqid = 0;
                }
                if (this.contextQueryReqid != 0) {
                    ConnectionsManager.getInstance().cancelRequest(this.contextQueryReqid, true);
                    this.contextQueryReqid = 0;
                }
                this.foundContextBot = null;
                this.inlineMediaEnabled = true;
                this.searchingContextUsername = null;
                this.searchingContextQuery = null;
                this.locationProvider.stop();
                this.noUserName = false;
                if (this.delegate != null) {
                    this.delegate.onContextSearch(false);
                }
                if (username == null || username.length() == 0) {
                    return;
                }
            }
            if (query == null) {
                if (this.contextQueryReqid != 0) {
                    ConnectionsManager.getInstance().cancelRequest(this.contextQueryReqid, true);
                    this.contextQueryReqid = 0;
                }
                this.searchingContextQuery = null;
                if (this.delegate != null) {
                    this.delegate.onContextSearch(false);
                    return;
                }
                return;
            }
            if (this.delegate != null) {
                if (this.foundContextBot != null) {
                    this.delegate.onContextSearch(true);
                } else if (username.equals("gif")) {
                    this.searchingContextUsername = "gif";
                    this.delegate.onContextSearch(false);
                }
            }
            this.searchingContextQuery = query;
            this.contextQueryRunnable = new Runnable() {

                /* renamed from: org.telegram.ui.Adapters.MentionsAdapter$7$1 */
                class C20361 implements RequestDelegate {
                    C20361() {
                    }

                    public void run(final TLObject response, final TLRPC$TL_error error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (MentionsAdapter.this.searchingContextUsername != null && MentionsAdapter.this.searchingContextUsername.equals(username)) {
                                    User user = null;
                                    if (error == null) {
                                        TLRPC$TL_contacts_resolvedPeer res = response;
                                        if (!res.users.isEmpty()) {
                                            user = (User) res.users.get(0);
                                            MessagesController.getInstance().putUser(user, false);
                                            MessagesStorage.getInstance().putUsersAndChats(res.users, null, true, true);
                                        }
                                    }
                                    MentionsAdapter.this.processFoundUser(user);
                                }
                            }
                        });
                    }
                }

                public void run() {
                    if (MentionsAdapter.this.contextQueryRunnable == this) {
                        MentionsAdapter.this.contextQueryRunnable = null;
                        if (MentionsAdapter.this.foundContextBot == null && !MentionsAdapter.this.noUserName) {
                            MentionsAdapter.this.searchingContextUsername = username;
                            TLObject object = MessagesController.getInstance().getUserOrChat(MentionsAdapter.this.searchingContextUsername);
                            if (object instanceof User) {
                                MentionsAdapter.this.processFoundUser((User) object);
                                return;
                            }
                            TLRPC$TL_contacts_resolveUsername req = new TLRPC$TL_contacts_resolveUsername();
                            req.username = MentionsAdapter.this.searchingContextUsername;
                            MentionsAdapter.this.contextUsernameReqid = ConnectionsManager.getInstance().sendRequest(req, new C20361());
                        } else if (!MentionsAdapter.this.noUserName) {
                            MentionsAdapter.this.searchForContextBotResults(true, MentionsAdapter.this.foundContextBot, query, "");
                        }
                    }
                }
            };
            AndroidUtilities.runOnUIThread(this.contextQueryRunnable, 400);
        }
    }

    private void onLocationUnavailable() {
        if (this.foundContextBot != null && this.foundContextBot.bot_inline_geo) {
            this.lastKnownLocation = new Location("network");
            this.lastKnownLocation.setLatitude(-1000.0d);
            this.lastKnownLocation.setLongitude(-1000.0d);
            searchForContextBotResults(true, this.foundContextBot, this.searchingContextQuery, "");
        }
    }

    private void checkLocationPermissionsOrStart() {
        if (this.parentFragment != null && this.parentFragment.getParentActivity() != null) {
            if (VERSION.SDK_INT >= 23 && this.parentFragment.getParentActivity().checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
                this.parentFragment.getParentActivity().requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, 2);
            } else if (this.foundContextBot != null && this.foundContextBot.bot_inline_geo) {
                this.locationProvider.start();
            }
        }
    }

    public void setSearchingMentions(boolean value) {
        this.isSearchingMentions = value;
    }

    public String getBotCaption() {
        if (this.foundContextBot != null) {
            return this.foundContextBot.bot_inline_placeholder;
        }
        if (this.searchingContextUsername == null || !this.searchingContextUsername.equals("gif")) {
            return null;
        }
        return "Search GIFs";
    }

    public void searchForContextBotForNextOffset() {
        if (this.contextQueryReqid == 0 && this.nextQueryOffset != null && this.nextQueryOffset.length() != 0 && this.foundContextBot != null && this.searchingContextQuery != null) {
            searchForContextBotResults(true, this.foundContextBot, this.searchingContextQuery, this.nextQueryOffset);
        }
    }

    private void searchForContextBotResults(boolean cache, User user, String query, String offset) {
        if (this.contextQueryReqid != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.contextQueryReqid, true);
            this.contextQueryReqid = 0;
        }
        if (this.inlineMediaEnabled) {
            if (query == null || user == null) {
                this.searchingContextQuery = null;
            } else if (!user.bot_inline_geo || this.lastKnownLocation != null) {
                StringBuilder append = new StringBuilder().append(this.dialog_id).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(query).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(offset).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(this.dialog_id).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(user.id).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                Object valueOf = (!user.bot_inline_geo || this.lastKnownLocation == null || this.lastKnownLocation.getLatitude() == -1000.0d) ? "" : Double.valueOf(this.lastKnownLocation.getLatitude() + this.lastKnownLocation.getLongitude());
                final String key = append.append(valueOf).toString();
                final String str = query;
                final boolean z = cache;
                final User user2 = user;
                final String str2 = offset;
                RequestDelegate requestDelegate = new RequestDelegate() {
                    public void run(final TLObject response, TLRPC$TL_error error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                boolean z = false;
                                if (MentionsAdapter.this.searchingContextQuery != null && str.equals(MentionsAdapter.this.searchingContextQuery)) {
                                    MentionsAdapter.this.contextQueryReqid = 0;
                                    if (z && response == null) {
                                        MentionsAdapter.this.searchForContextBotResults(false, user2, str, str2);
                                    } else if (MentionsAdapter.this.delegate != null) {
                                        MentionsAdapter.this.delegate.onContextSearch(false);
                                    }
                                    if (response != null) {
                                        TLRPC$TL_messages_botResults res = response;
                                        if (!(z || res.cache_time == 0)) {
                                            MessagesStorage.getInstance().saveBotCache(key, res);
                                        }
                                        MentionsAdapter.this.nextQueryOffset = res.next_offset;
                                        if (MentionsAdapter.this.searchResultBotContextById == null) {
                                            MentionsAdapter.this.searchResultBotContextById = new HashMap();
                                            MentionsAdapter.this.searchResultBotContextSwitch = res.switch_pm;
                                        }
                                        int a = 0;
                                        while (a < res.results.size()) {
                                            TLRPC$BotInlineResult result = (TLRPC$BotInlineResult) res.results.get(a);
                                            if (MentionsAdapter.this.searchResultBotContextById.containsKey(result.id) || (!(result.document instanceof TLRPC$TL_document) && !(result.photo instanceof TLRPC$TL_photo) && result.content_url == null && (result.send_message instanceof TLRPC$TL_botInlineMessageMediaAuto))) {
                                                res.results.remove(a);
                                                a--;
                                            }
                                            result.query_id = res.query_id;
                                            MentionsAdapter.this.searchResultBotContextById.put(result.id, result);
                                            a++;
                                        }
                                        boolean added = false;
                                        if (MentionsAdapter.this.searchResultBotContext == null || str2.length() == 0) {
                                            MentionsAdapter.this.searchResultBotContext = res.results;
                                            MentionsAdapter.this.contextMedia = res.gallery;
                                        } else {
                                            added = true;
                                            MentionsAdapter.this.searchResultBotContext.addAll(res.results);
                                            if (res.results.isEmpty()) {
                                                MentionsAdapter.this.nextQueryOffset = "";
                                            }
                                        }
                                        MentionsAdapter.this.searchResultHashtags = null;
                                        MentionsAdapter.this.searchResultUsernames = null;
                                        MentionsAdapter.this.searchResultUsernamesMap = null;
                                        MentionsAdapter.this.searchResultCommands = null;
                                        MentionsAdapter.this.searchResultSuggestions = null;
                                        MentionsAdapter.this.searchResultCommandsHelp = null;
                                        MentionsAdapter.this.searchResultCommandsUsers = null;
                                        if (added) {
                                            int i;
                                            boolean hasTop = MentionsAdapter.this.searchResultBotContextSwitch != null;
                                            MentionsAdapter mentionsAdapter = MentionsAdapter.this;
                                            int size = MentionsAdapter.this.searchResultBotContext.size() - res.results.size();
                                            if (hasTop) {
                                                i = 1;
                                            } else {
                                                i = 0;
                                            }
                                            mentionsAdapter.notifyItemChanged((i + size) - 1);
                                            mentionsAdapter = MentionsAdapter.this;
                                            size = MentionsAdapter.this.searchResultBotContext.size() - res.results.size();
                                            if (hasTop) {
                                                i = 1;
                                            } else {
                                                i = 0;
                                            }
                                            mentionsAdapter.notifyItemRangeInserted(i + size, res.results.size());
                                        } else {
                                            MentionsAdapter.this.notifyDataSetChanged();
                                        }
                                        MentionsAdapterDelegate access$1600 = MentionsAdapter.this.delegate;
                                        if (!(MentionsAdapter.this.searchResultBotContext.isEmpty() && MentionsAdapter.this.searchResultBotContextSwitch == null)) {
                                            z = true;
                                        }
                                        access$1600.needChangePanelVisibility(z);
                                    }
                                }
                            }
                        });
                    }
                };
                if (cache) {
                    MessagesStorage.getInstance().getBotCache(key, requestDelegate);
                    return;
                }
                TLRPC$TL_messages_getInlineBotResults req = new TLRPC$TL_messages_getInlineBotResults();
                req.bot = MessagesController.getInputUser(user);
                req.query = query;
                req.offset = offset;
                if (!(!user.bot_inline_geo || this.lastKnownLocation == null || this.lastKnownLocation.getLatitude() == -1000.0d)) {
                    req.flags |= 1;
                    req.geo_point = new TLRPC$TL_inputGeoPoint();
                    req.geo_point.lat = this.lastKnownLocation.getLatitude();
                    req.geo_point._long = this.lastKnownLocation.getLongitude();
                }
                int lower_id = (int) this.dialog_id;
                int high_id = (int) (this.dialog_id >> 32);
                if (lower_id != 0) {
                    req.peer = MessagesController.getInputPeer(lower_id);
                } else {
                    req.peer = new TLRPC$TL_inputPeerEmpty();
                }
                this.contextQueryReqid = ConnectionsManager.getInstance().sendRequest(req, requestDelegate, 2);
            }
        } else if (this.delegate != null) {
            this.delegate.onContextSearch(false);
        }
    }

    public void searchUsernameOrHashtag(String text, int position, ArrayList<MessageObject> messageObjects, boolean usernameOnly) {
        if (this.channelReqId != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.channelReqId, true);
            this.channelReqId = 0;
        }
        if (this.searchGlobalRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.searchGlobalRunnable);
            this.searchGlobalRunnable = null;
        }
        if (TextUtils.isEmpty(text)) {
            searchForContextBot(null, null);
            this.delegate.needChangePanelVisibility(false);
            this.lastText = null;
            return;
        }
        int a;
        char ch;
        int searchPostion = position;
        if (text.length() > 0) {
            searchPostion--;
        }
        this.lastText = null;
        this.lastUsernameOnly = usernameOnly;
        StringBuilder result = new StringBuilder();
        int foundType = -1;
        boolean hasIllegalUsernameCharacters = false;
        if (!usernameOnly && this.needBotContext && text.charAt(0) == '@') {
            int index = text.indexOf(32);
            int len = text.length();
            String username = null;
            String query = null;
            if (index > 0) {
                username = text.substring(1, index);
                query = text.substring(index + 1);
            } else if (text.charAt(len - 1) == 't' && text.charAt(len - 2) == 'o' && text.charAt(len - 3) == 'b') {
                username = text.substring(1);
                query = "";
            } else {
                searchForContextBot(null, null);
            }
            if (username == null || username.length() < 1) {
                username = "";
            } else {
                for (a = 1; a < username.length(); a++) {
                    ch = username.charAt(a);
                    if ((ch < '0' || ch > '9') && ((ch < 'a' || ch > 'z') && ((ch < 'A' || ch > 'Z') && ch != '_'))) {
                        username = "";
                        break;
                    }
                }
            }
            searchForContextBot(username, query);
        } else {
            searchForContextBot(null, null);
        }
        if (this.foundContextBot == null) {
            int dogPostion = -1;
            if (usernameOnly) {
                result.append(text.substring(1));
                this.resultStartPosition = 0;
                this.resultLength = result.length();
                foundType = 0;
            } else {
                a = searchPostion;
                while (a >= 0) {
                    if (a < text.length()) {
                        ch = text.charAt(a);
                        if (a == 0 || text.charAt(a - 1) == ' ' || text.charAt(a - 1) == '\n') {
                            if (ch != '@') {
                                if (ch != '#') {
                                    if (a != 0 || this.botInfo == null || ch != '/') {
                                        if (ch == ':' && result.length() > 0) {
                                            foundType = 3;
                                            this.resultStartPosition = a;
                                            this.resultLength = result.length() + 1;
                                            break;
                                        }
                                    }
                                    foundType = 2;
                                    this.resultStartPosition = a;
                                    this.resultLength = result.length() + 1;
                                    break;
                                } else if (this.searchAdapterHelper.loadRecentHashtags()) {
                                    foundType = 1;
                                    this.resultStartPosition = a;
                                    this.resultLength = result.length() + 1;
                                    result.insert(0, ch);
                                } else {
                                    this.lastText = text;
                                    this.lastPosition = position;
                                    this.messages = messageObjects;
                                    this.delegate.needChangePanelVisibility(false);
                                    return;
                                }
                            } else if (this.needUsernames || (this.needBotContext && a == 0)) {
                                if (this.info != null || a == 0) {
                                    dogPostion = a;
                                    foundType = 0;
                                    this.resultStartPosition = a;
                                    this.resultLength = result.length() + 1;
                                } else {
                                    this.lastText = text;
                                    this.lastPosition = position;
                                    this.messages = messageObjects;
                                    this.delegate.needChangePanelVisibility(false);
                                    return;
                                }
                            }
                        }
                        if ((ch < '0' || ch > '9') && ((ch < 'a' || ch > 'z') && ((ch < 'A' || ch > 'Z') && ch != '_'))) {
                            hasIllegalUsernameCharacters = true;
                        }
                        result.insert(0, ch);
                    }
                    a--;
                }
            }
            if (foundType == -1) {
                this.delegate.needChangePanelVisibility(false);
            } else if (foundType == 0) {
                User user;
                TLRPC$Chat chat;
                ArrayList<Integer> users = new ArrayList();
                for (a = 0; a < Math.min(100, messageObjects.size()); a++) {
                    int from_id = ((MessageObject) messageObjects.get(a)).messageOwner.from_id;
                    if (!users.contains(Integer.valueOf(from_id))) {
                        users.add(Integer.valueOf(from_id));
                    }
                }
                String usernameString = result.toString().toLowerCase();
                boolean hasSpace = usernameString.indexOf(32) >= 0;
                ArrayList<User> newResult = new ArrayList();
                HashMap<Integer, User> newResultsHashMap = new HashMap();
                HashMap<Integer, User> newMap = new HashMap();
                if (!usernameOnly && this.needBotContext && dogPostion == 0 && !SearchQuery.inlineBots.isEmpty()) {
                    int count = 0;
                    for (a = 0; a < SearchQuery.inlineBots.size(); a++) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_topPeer) SearchQuery.inlineBots.get(a)).peer.user_id));
                        if (user != null) {
                            if (user.username != null && user.username.length() > 0 && ((usernameString.length() > 0 && user.username.toLowerCase().startsWith(usernameString)) || usernameString.length() == 0)) {
                                newResult.add(user);
                                newResultsHashMap.put(Integer.valueOf(user.id), user);
                                count++;
                            }
                            if (count == 5) {
                                break;
                            }
                        }
                    }
                }
                if (this.parentFragment != null) {
                    chat = this.parentFragment.getCurrentChat();
                } else if (this.info != null) {
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(this.info.id));
                } else {
                    chat = null;
                }
                if (!(chat == null || this.info == null || this.info.participants == null || (ChatObject.isChannel(chat) && !chat.megagroup))) {
                    for (a = 0; a < this.info.participants.participants.size(); a++) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$ChatParticipant) this.info.participants.participants.get(a)).user_id));
                        if (user != null && ((usernameOnly || !UserObject.isUserSelf(user)) && !newResultsHashMap.containsKey(Integer.valueOf(user.id)))) {
                            if (usernameString.length() == 0) {
                                if (!user.deleted && (this.allowNewMentions || !(this.allowNewMentions || user.username == null || user.username.length() == 0))) {
                                    newResult.add(user);
                                }
                            } else if (user.username != null && user.username.length() > 0 && user.username.toLowerCase().startsWith(usernameString)) {
                                newResult.add(user);
                                newMap.put(Integer.valueOf(user.id), user);
                            } else if (this.allowNewMentions || !(user.username == null || user.username.length() == 0)) {
                                if (user.first_name != null && user.first_name.length() > 0 && user.first_name.toLowerCase().startsWith(usernameString)) {
                                    newResult.add(user);
                                    newMap.put(Integer.valueOf(user.id), user);
                                } else if (user.last_name != null && user.last_name.length() > 0 && user.last_name.toLowerCase().startsWith(usernameString)) {
                                    newResult.add(user);
                                    newMap.put(Integer.valueOf(user.id), user);
                                } else if (hasSpace && ContactsController.formatName(user.first_name, user.last_name).toLowerCase().startsWith(usernameString)) {
                                    newResult.add(user);
                                    newMap.put(Integer.valueOf(user.id), user);
                                }
                            }
                        }
                    }
                }
                this.searchResultHashtags = null;
                this.searchResultCommands = null;
                this.searchResultCommandsHelp = null;
                this.searchResultCommandsUsers = null;
                this.searchResultSuggestions = null;
                this.searchResultUsernames = newResult;
                this.searchResultUsernamesMap = newMap;
                if (chat != null && chat.megagroup && usernameString.length() > 0) {
                    final String str = usernameString;
                    C20429 c20429 = new Runnable() {
                        public void run() {
                            if (MentionsAdapter.this.searchGlobalRunnable == this) {
                                TLRPC$TL_channels_getParticipants req = new TLRPC$TL_channels_getParticipants();
                                req.channel = MessagesController.getInputChannel(chat);
                                req.limit = 20;
                                req.offset = 0;
                                req.filter = new TLRPC$TL_channelParticipantsSearch();
                                req.filter.f69q = str;
                                final int currentReqId = MentionsAdapter.access$3004(MentionsAdapter.this);
                                MentionsAdapter.this.channelReqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                                    public void run(final TLObject response, final TLRPC$TL_error error) {
                                        AndroidUtilities.runOnUIThread(new Runnable() {
                                            public void run() {
                                                if (!(MentionsAdapter.this.channelReqId == 0 || currentReqId != MentionsAdapter.this.channelLastReqId || MentionsAdapter.this.searchResultUsernamesMap == null || MentionsAdapter.this.searchResultUsernames == null || error != null)) {
                                                    TLRPC$TL_channels_channelParticipants res = response;
                                                    MessagesController.getInstance().putUsers(res.users, false);
                                                    if (!res.participants.isEmpty()) {
                                                        int currentUserId = UserConfig.getClientUserId();
                                                        for (int a = 0; a < res.participants.size(); a++) {
                                                            TLRPC$ChannelParticipant participant = (TLRPC$ChannelParticipant) res.participants.get(a);
                                                            if (!MentionsAdapter.this.searchResultUsernamesMap.containsKey(Integer.valueOf(participant.user_id)) && (MentionsAdapter.this.isSearchingMentions || participant.user_id != currentUserId)) {
                                                                User user = MessagesController.getInstance().getUser(Integer.valueOf(participant.user_id));
                                                                if (user != null) {
                                                                    MentionsAdapter.this.searchResultUsernames.add(user);
                                                                } else {
                                                                    return;
                                                                }
                                                            }
                                                        }
                                                        MentionsAdapter.this.notifyDataSetChanged();
                                                    }
                                                }
                                                MentionsAdapter.this.channelReqId = 0;
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    };
                    this.searchGlobalRunnable = c20429;
                    AndroidUtilities.runOnUIThread(c20429, 200);
                }
                final HashMap<Integer, User> hashMap = newResultsHashMap;
                final ArrayList<Integer> arrayList = users;
                Collections.sort(this.searchResultUsernames, new Comparator<User>() {
                    public int compare(User lhs, User rhs) {
                        if (hashMap.containsKey(Integer.valueOf(lhs.id)) && hashMap.containsKey(Integer.valueOf(rhs.id))) {
                            return 0;
                        }
                        if (hashMap.containsKey(Integer.valueOf(lhs.id))) {
                            return -1;
                        }
                        if (hashMap.containsKey(Integer.valueOf(rhs.id))) {
                            return 1;
                        }
                        int lhsNum = arrayList.indexOf(Integer.valueOf(lhs.id));
                        int rhsNum = arrayList.indexOf(Integer.valueOf(rhs.id));
                        if (lhsNum == -1 || rhsNum == -1) {
                            if (lhsNum != -1 && rhsNum == -1) {
                                return -1;
                            }
                            if (lhsNum != -1 || rhsNum == -1) {
                                return 0;
                            }
                            return 1;
                        } else if (lhsNum >= rhsNum) {
                            return lhsNum == rhsNum ? 0 : 1;
                        } else {
                            return -1;
                        }
                    }
                });
                notifyDataSetChanged();
                this.delegate.needChangePanelVisibility(!newResult.isEmpty());
            } else if (foundType == 1) {
                newResult = new ArrayList();
                String hashtagString = result.toString().toLowerCase();
                ArrayList<HashtagObject> hashtags = this.searchAdapterHelper.getHashtags();
                for (a = 0; a < hashtags.size(); a++) {
                    HashtagObject hashtagObject = (HashtagObject) hashtags.get(a);
                    if (!(hashtagObject == null || hashtagObject.hashtag == null || !hashtagObject.hashtag.startsWith(hashtagString))) {
                        newResult.add(hashtagObject.hashtag);
                    }
                }
                this.searchResultHashtags = newResult;
                this.searchResultUsernames = null;
                this.searchResultUsernamesMap = null;
                this.searchResultCommands = null;
                this.searchResultCommandsHelp = null;
                this.searchResultCommandsUsers = null;
                this.searchResultSuggestions = null;
                notifyDataSetChanged();
                this.delegate.needChangePanelVisibility(!newResult.isEmpty());
            } else if (foundType == 2) {
                newResult = new ArrayList();
                ArrayList<String> newResultHelp = new ArrayList();
                ArrayList<User> newResultUsers = new ArrayList();
                String command = result.toString().toLowerCase();
                for (Entry<Integer, TLRPC$BotInfo> entry : this.botInfo.entrySet()) {
                    TLRPC$BotInfo botInfo = (TLRPC$BotInfo) entry.getValue();
                    for (a = 0; a < botInfo.commands.size(); a++) {
                        TLRPC$TL_botCommand botCommand = (TLRPC$TL_botCommand) botInfo.commands.get(a);
                        if (!(botCommand == null || botCommand.command == null || !botCommand.command.startsWith(command))) {
                            newResult.add("/" + botCommand.command);
                            newResultHelp.add(botCommand.description);
                            newResultUsers.add(MessagesController.getInstance().getUser(Integer.valueOf(botInfo.user_id)));
                        }
                    }
                }
                this.searchResultHashtags = null;
                this.searchResultUsernames = null;
                this.searchResultUsernamesMap = null;
                this.searchResultSuggestions = null;
                this.searchResultCommands = newResult;
                this.searchResultCommandsHelp = newResultHelp;
                this.searchResultCommandsUsers = newResultUsers;
                notifyDataSetChanged();
                this.delegate.needChangePanelVisibility(!newResult.isEmpty());
            } else if (foundType != 3) {
            } else {
                if (hasIllegalUsernameCharacters) {
                    this.delegate.needChangePanelVisibility(false);
                    return;
                }
                Object[] suggestions = Emoji.getSuggestion(result.toString());
                if (suggestions != null) {
                    this.searchResultSuggestions = new ArrayList();
                    for (EmojiSuggestion suggestion : suggestions) {
                        suggestion.emoji = suggestion.emoji.replace("️", "");
                        this.searchResultSuggestions.add(suggestion);
                    }
                    Emoji.loadRecentEmoji();
                    Collections.sort(this.searchResultSuggestions, new Comparator<EmojiSuggestion>() {
                        public int compare(EmojiSuggestion o1, EmojiSuggestion o2) {
                            Integer n1 = (Integer) Emoji.emojiUseHistory.get(o1.emoji);
                            if (n1 == null) {
                                n1 = Integer.valueOf(0);
                            }
                            Integer n2 = (Integer) Emoji.emojiUseHistory.get(o2.emoji);
                            if (n2 == null) {
                                n2 = Integer.valueOf(0);
                            }
                            return n2.compareTo(n1);
                        }
                    });
                }
                this.searchResultHashtags = null;
                this.searchResultUsernames = null;
                this.searchResultUsernamesMap = null;
                this.searchResultCommands = null;
                this.searchResultCommandsHelp = null;
                this.searchResultCommandsUsers = null;
                notifyDataSetChanged();
                this.delegate.needChangePanelVisibility(this.searchResultSuggestions != null);
            }
        }
    }

    public int getResultStartPosition() {
        return this.resultStartPosition;
    }

    public int getResultLength() {
        return this.resultLength;
    }

    public ArrayList<TLRPC$BotInlineResult> getSearchResultBotContext() {
        return this.searchResultBotContext;
    }

    public int getItemCount() {
        int i = 1;
        if (this.foundContextBot != null && !this.inlineMediaEnabled) {
            return 1;
        }
        if (this.searchResultBotContext != null) {
            int size = this.searchResultBotContext.size();
            if (this.searchResultBotContextSwitch == null) {
                i = 0;
            }
            return i + size;
        } else if (this.searchResultUsernames != null) {
            return this.searchResultUsernames.size();
        } else {
            if (this.searchResultHashtags != null) {
                return this.searchResultHashtags.size();
            }
            if (this.searchResultCommands != null) {
                return this.searchResultCommands.size();
            }
            return this.searchResultSuggestions != null ? this.searchResultSuggestions.size() : 0;
        }
    }

    public int getItemViewType(int position) {
        if (this.foundContextBot != null && !this.inlineMediaEnabled) {
            return 3;
        }
        if (this.searchResultBotContext == null) {
            return 0;
        }
        if (position != 0 || this.searchResultBotContextSwitch == null) {
            return 1;
        }
        return 2;
    }

    public void addHashtagsFromMessage(CharSequence message) {
        this.searchAdapterHelper.addHashtagsFromMessage(message);
    }

    public int getItemPosition(int i) {
        if (this.searchResultBotContext == null || this.searchResultBotContextSwitch == null) {
            return i;
        }
        return i - 1;
    }

    public Object getItem(int i) {
        if (this.searchResultBotContext != null) {
            if (this.searchResultBotContextSwitch != null) {
                if (i == 0) {
                    return this.searchResultBotContextSwitch;
                }
                i--;
            }
            if (i < 0 || i >= this.searchResultBotContext.size()) {
                return null;
            }
            return this.searchResultBotContext.get(i);
        } else if (this.searchResultUsernames != null) {
            if (i < 0 || i >= this.searchResultUsernames.size()) {
                return null;
            }
            return this.searchResultUsernames.get(i);
        } else if (this.searchResultHashtags != null) {
            if (i < 0 || i >= this.searchResultHashtags.size()) {
                return null;
            }
            return this.searchResultHashtags.get(i);
        } else if (this.searchResultSuggestions != null) {
            if (i < 0 || i >= this.searchResultSuggestions.size()) {
                return null;
            }
            return this.searchResultSuggestions.get(i);
        } else if (this.searchResultCommands == null || i < 0 || i >= this.searchResultCommands.size()) {
            return null;
        } else {
            if (this.searchResultCommandsUsers == null || (this.botsCount == 1 && !(this.info instanceof TLRPC$TL_channelFull))) {
                return this.searchResultCommands.get(i);
            }
            if (this.searchResultCommandsUsers.get(i) != null) {
                String str = "%s@%s";
                Object[] objArr = new Object[2];
                objArr[0] = this.searchResultCommands.get(i);
                objArr[1] = this.searchResultCommandsUsers.get(i) != null ? ((User) this.searchResultCommandsUsers.get(i)).username : "";
                return String.format(str, objArr);
            }
            return String.format("%s", new Object[]{this.searchResultCommands.get(i)});
        }
    }

    public boolean isLongClickEnabled() {
        return (this.searchResultHashtags == null && this.searchResultCommands == null) ? false : true;
    }

    public boolean isBotCommands() {
        return this.searchResultCommands != null;
    }

    public boolean isBotContext() {
        return this.searchResultBotContext != null;
    }

    public boolean isBannedInline() {
        return (this.foundContextBot == null || this.inlineMediaEnabled) ? false : true;
    }

    public boolean isMediaLayout() {
        return this.contextMedia;
    }

    public boolean isEnabled(ViewHolder holder) {
        return this.foundContextBot == null || this.inlineMediaEnabled;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = new MentionCell(this.mContext);
                ((MentionCell) view).setIsDarkTheme(this.isDarkTheme);
                break;
            case 1:
                view = new ContextLinkCell(this.mContext);
                ((ContextLinkCell) view).setDelegate(new ContextLinkCellDelegate() {
                    public void didPressedImage(ContextLinkCell cell) {
                        MentionsAdapter.this.delegate.onContextClick(cell.getResult());
                    }
                });
                break;
            case 2:
                view = new BotSwitchCell(this.mContext);
                break;
            default:
                View textView = new TextView(this.mContext);
                textView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f));
                textView.setTextSize(1, 14.0f);
                textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
                view = textView;
                break;
        }
        return new Holder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        boolean z = true;
        if (holder.getItemViewType() == 3) {
            TextView textView = holder.itemView;
            TLRPC$Chat chat = this.parentFragment.getCurrentChat();
            if (chat == null) {
                return;
            }
            if (AndroidUtilities.isBannedForever(chat.banned_rights.until_date)) {
                textView.setText(LocaleController.getString("AttachInlineRestrictedForever", R.string.AttachInlineRestrictedForever));
            } else {
                textView.setText(LocaleController.formatString("AttachInlineRestricted", R.string.AttachInlineRestricted, new Object[]{LocaleController.formatDateForBan((long) chat.banned_rights.until_date)}));
            }
        } else if (this.searchResultBotContext != null) {
            boolean hasTop;
            if (this.searchResultBotContextSwitch != null) {
                hasTop = true;
            } else {
                hasTop = false;
            }
            if (holder.getItemViewType() != 2) {
                if (hasTop) {
                    position--;
                }
                ContextLinkCell contextLinkCell = (ContextLinkCell) holder.itemView;
                TLRPC$BotInlineResult tLRPC$BotInlineResult = (TLRPC$BotInlineResult) this.searchResultBotContext.get(position);
                boolean z2 = this.contextMedia;
                boolean z3 = position != this.searchResultBotContext.size() + -1;
                if (!(hasTop && position == 0)) {
                    z = false;
                }
                contextLinkCell.setLink(tLRPC$BotInlineResult, z2, z3, z);
            } else if (hasTop) {
                ((BotSwitchCell) holder.itemView).setText(this.searchResultBotContextSwitch.text);
            }
        } else if (this.searchResultUsernames != null) {
            ((MentionCell) holder.itemView).setUser((User) this.searchResultUsernames.get(position));
        } else if (this.searchResultHashtags != null) {
            ((MentionCell) holder.itemView).setText((String) this.searchResultHashtags.get(position));
        } else if (this.searchResultSuggestions != null) {
            ((MentionCell) holder.itemView).setEmojiSuggestion((EmojiSuggestion) this.searchResultSuggestions.get(position));
        } else if (this.searchResultCommands != null) {
            ((MentionCell) holder.itemView).setBotCommand((String) this.searchResultCommands.get(position), (String) this.searchResultCommandsHelp.get(position), this.searchResultCommandsUsers != null ? (User) this.searchResultCommandsUsers.get(position) : null);
        }
    }

    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != 2 || this.foundContextBot == null || !this.foundContextBot.bot_inline_geo) {
            return;
        }
        if (grantResults.length <= 0 || grantResults[0] != 0) {
            onLocationUnavailable();
        } else {
            this.locationProvider.start();
        }
    }
}
