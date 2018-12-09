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
import org.telegram.messenger.SendMessagesHelper.LocationProvider;
import org.telegram.messenger.SendMessagesHelper.LocationProvider.LocationProviderDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
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
import org.telegram.tgnet.TLRPC.BotInfo;
import org.telegram.tgnet.TLRPC.BotInlineResult;
import org.telegram.tgnet.TLRPC.ChannelParticipant;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ChatParticipant;
import org.telegram.tgnet.TLRPC.TL_botCommand;
import org.telegram.tgnet.TLRPC.TL_botInlineMessageMediaAuto;
import org.telegram.tgnet.TLRPC.TL_channelFull;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsSearch;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC.TL_channels_getParticipants;
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
    private HashMap<Integer, BotInfo> botInfo;
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
    private ChatFull info;
    private boolean inlineMediaEnabled = true;
    private boolean isDarkTheme;
    private boolean isSearchingMentions;
    private Location lastKnownLocation;
    private int lastPosition;
    private String lastText;
    private boolean lastUsernameOnly;
    private LocationProvider locationProvider = new LocationProvider(new C38671()) {
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
    private ArrayList<BotInlineResult> searchResultBotContext;
    private HashMap<String, BotInlineResult> searchResultBotContextById;
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
    class C38671 implements LocationProviderDelegate {
        C38671() {
        }

        public void onLocationAcquired(Location location) {
            if (MentionsAdapter.this.foundContextBot != null && MentionsAdapter.this.foundContextBot.bot_inline_geo) {
                MentionsAdapter.this.lastKnownLocation = location;
                MentionsAdapter.this.searchForContextBotResults(true, MentionsAdapter.this.foundContextBot, MentionsAdapter.this.searchingContextQuery, TtmlNode.ANONYMOUS_REGION_ID);
            }
        }

        public void onUnableLocationAcquire() {
            MentionsAdapter.this.onLocationUnavailable();
        }
    }

    /* renamed from: org.telegram.ui.Adapters.MentionsAdapter$3 */
    class C38693 implements SearchAdapterHelperDelegate {
        C38693() {
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

        void onContextClick(BotInlineResult botInlineResult);

        void onContextSearch(boolean z);
    }

    public MentionsAdapter(Context context, boolean z, long j, MentionsAdapterDelegate mentionsAdapterDelegate) {
        this.mContext = context;
        this.delegate = mentionsAdapterDelegate;
        this.isDarkTheme = z;
        this.dialog_id = j;
        this.searchAdapterHelper = new SearchAdapterHelper();
        this.searchAdapterHelper.setDelegate(new C38693());
    }

    static /* synthetic */ int access$3004(MentionsAdapter mentionsAdapter) {
        int i = mentionsAdapter.channelLastReqId + 1;
        mentionsAdapter.channelLastReqId = i;
        return i;
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

    private void onLocationUnavailable() {
        if (this.foundContextBot != null && this.foundContextBot.bot_inline_geo) {
            this.lastKnownLocation = new Location("network");
            this.lastKnownLocation.setLatitude(-1000.0d);
            this.lastKnownLocation.setLongitude(-1000.0d);
            searchForContextBotResults(true, this.foundContextBot, this.searchingContextQuery, TtmlNode.ANONYMOUS_REGION_ID);
        }
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
                Chat currentChat = this.parentFragment.getCurrentChat();
                if (currentChat != null) {
                    this.inlineMediaEnabled = ChatObject.canSendStickers(currentChat);
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
                    final User user2 = this.foundContextBot;
                    Builder builder = new Builder(this.parentFragment.getParentActivity());
                    builder.setTitle(LocaleController.getString("ShareYouLocationTitle", R.string.ShareYouLocationTitle));
                    builder.setMessage(LocaleController.getString("ShareYouLocationInline", R.string.ShareYouLocationInline));
                    final boolean[] zArr = new boolean[1];
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            zArr[0] = true;
                            if (user2 != null) {
                                ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putBoolean("inlinegeo_" + user2.id, true).commit();
                                MentionsAdapter.this.checkLocationPermissionsOrStart();
                            }
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            zArr[0] = true;
                            MentionsAdapter.this.onLocationUnavailable();
                        }
                    });
                    this.parentFragment.showDialog(builder.create(), new OnDismissListener() {
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (!zArr[0]) {
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
        searchForContextBotResults(true, this.foundContextBot, this.searchingContextQuery, TtmlNode.ANONYMOUS_REGION_ID);
    }

    private void searchForContextBot(final String str, final String str2) {
        if (this.foundContextBot == null || this.foundContextBot.username == null || !this.foundContextBot.username.equals(str) || this.searchingContextQuery == null || !this.searchingContextQuery.equals(str2)) {
            this.searchResultBotContext = null;
            this.searchResultBotContextById = null;
            this.searchResultBotContextSwitch = null;
            notifyDataSetChanged();
            if (this.foundContextBot != null) {
                if (this.inlineMediaEnabled || str == null || str2 == null) {
                    this.delegate.needChangePanelVisibility(false);
                } else {
                    return;
                }
            }
            if (this.contextQueryRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(this.contextQueryRunnable);
                this.contextQueryRunnable = null;
            }
            if (TextUtils.isEmpty(str) || !(this.searchingContextUsername == null || this.searchingContextUsername.equals(str))) {
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
                if (str == null || str.length() == 0) {
                    return;
                }
            }
            if (str2 == null) {
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
                } else if (str.equals("gif")) {
                    this.searchingContextUsername = "gif";
                    this.delegate.onContextSearch(false);
                }
            }
            this.searchingContextQuery = str2;
            this.contextQueryRunnable = new Runnable() {

                /* renamed from: org.telegram.ui.Adapters.MentionsAdapter$7$1 */
                class C38741 implements RequestDelegate {
                    C38741() {
                    }

                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (MentionsAdapter.this.searchingContextUsername != null && MentionsAdapter.this.searchingContextUsername.equals(str)) {
                                    User user;
                                    if (tLRPC$TL_error == null) {
                                        TLRPC$TL_contacts_resolvedPeer tLRPC$TL_contacts_resolvedPeer = (TLRPC$TL_contacts_resolvedPeer) tLObject;
                                        if (!tLRPC$TL_contacts_resolvedPeer.users.isEmpty()) {
                                            user = (User) tLRPC$TL_contacts_resolvedPeer.users.get(0);
                                            MessagesController.getInstance().putUser(user, false);
                                            MessagesStorage.getInstance().putUsersAndChats(tLRPC$TL_contacts_resolvedPeer.users, null, true, true);
                                            MentionsAdapter.this.processFoundUser(user);
                                        }
                                    }
                                    user = null;
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
                            MentionsAdapter.this.searchingContextUsername = str;
                            TLObject userOrChat = MessagesController.getInstance().getUserOrChat(MentionsAdapter.this.searchingContextUsername);
                            if (userOrChat instanceof User) {
                                MentionsAdapter.this.processFoundUser((User) userOrChat);
                                return;
                            }
                            userOrChat = new TLRPC$TL_contacts_resolveUsername();
                            userOrChat.username = MentionsAdapter.this.searchingContextUsername;
                            MentionsAdapter.this.contextUsernameReqid = ConnectionsManager.getInstance().sendRequest(userOrChat, new C38741());
                        } else if (!MentionsAdapter.this.noUserName) {
                            MentionsAdapter.this.searchForContextBotResults(true, MentionsAdapter.this.foundContextBot, str2, TtmlNode.ANONYMOUS_REGION_ID);
                        }
                    }
                }
            };
            AndroidUtilities.runOnUIThread(this.contextQueryRunnable, 400);
        }
    }

    private void searchForContextBotResults(boolean z, User user, String str, String str2) {
        if (this.contextQueryReqid != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.contextQueryReqid, true);
            this.contextQueryReqid = 0;
        }
        if (this.inlineMediaEnabled) {
            if (str == null || user == null) {
                this.searchingContextQuery = null;
            } else if (!user.bot_inline_geo || this.lastKnownLocation != null) {
                StringBuilder append = new StringBuilder().append(this.dialog_id).append("_").append(str).append("_").append(str2).append("_").append(this.dialog_id).append("_").append(user.id).append("_");
                Object valueOf = (!user.bot_inline_geo || this.lastKnownLocation == null || this.lastKnownLocation.getLatitude() == -1000.0d) ? TtmlNode.ANONYMOUS_REGION_ID : Double.valueOf(this.lastKnownLocation.getLatitude() + this.lastKnownLocation.getLongitude());
                final String stringBuilder = append.append(valueOf).toString();
                final String str3 = str;
                final boolean z2 = z;
                final User user2 = user;
                final String str4 = str2;
                RequestDelegate c38778 = new RequestDelegate() {
                    public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                boolean z = false;
                                if (MentionsAdapter.this.searchingContextQuery != null && str3.equals(MentionsAdapter.this.searchingContextQuery)) {
                                    MentionsAdapter.this.contextQueryReqid = 0;
                                    if (z2 && tLObject == null) {
                                        MentionsAdapter.this.searchForContextBotResults(false, user2, str3, str4);
                                    } else if (MentionsAdapter.this.delegate != null) {
                                        MentionsAdapter.this.delegate.onContextSearch(false);
                                    }
                                    if (tLObject != null) {
                                        boolean z2;
                                        TLRPC$TL_messages_botResults tLRPC$TL_messages_botResults = (TLRPC$TL_messages_botResults) tLObject;
                                        if (!(z2 || tLRPC$TL_messages_botResults.cache_time == 0)) {
                                            MessagesStorage.getInstance().saveBotCache(stringBuilder, tLRPC$TL_messages_botResults);
                                        }
                                        MentionsAdapter.this.nextQueryOffset = tLRPC$TL_messages_botResults.next_offset;
                                        if (MentionsAdapter.this.searchResultBotContextById == null) {
                                            MentionsAdapter.this.searchResultBotContextById = new HashMap();
                                            MentionsAdapter.this.searchResultBotContextSwitch = tLRPC$TL_messages_botResults.switch_pm;
                                        }
                                        int i = 0;
                                        while (i < tLRPC$TL_messages_botResults.results.size()) {
                                            BotInlineResult botInlineResult = (BotInlineResult) tLRPC$TL_messages_botResults.results.get(i);
                                            if (MentionsAdapter.this.searchResultBotContextById.containsKey(botInlineResult.id) || (!(botInlineResult.document instanceof TLRPC$TL_document) && !(botInlineResult.photo instanceof TLRPC$TL_photo) && botInlineResult.content_url == null && (botInlineResult.send_message instanceof TL_botInlineMessageMediaAuto))) {
                                                tLRPC$TL_messages_botResults.results.remove(i);
                                                i--;
                                            }
                                            botInlineResult.query_id = tLRPC$TL_messages_botResults.query_id;
                                            MentionsAdapter.this.searchResultBotContextById.put(botInlineResult.id, botInlineResult);
                                            i++;
                                        }
                                        if (MentionsAdapter.this.searchResultBotContext == null || str4.length() == 0) {
                                            MentionsAdapter.this.searchResultBotContext = tLRPC$TL_messages_botResults.results;
                                            MentionsAdapter.this.contextMedia = tLRPC$TL_messages_botResults.gallery;
                                            z2 = false;
                                        } else {
                                            MentionsAdapter.this.searchResultBotContext.addAll(tLRPC$TL_messages_botResults.results);
                                            if (tLRPC$TL_messages_botResults.results.isEmpty()) {
                                                MentionsAdapter.this.nextQueryOffset = TtmlNode.ANONYMOUS_REGION_ID;
                                            }
                                            z2 = true;
                                        }
                                        MentionsAdapter.this.searchResultHashtags = null;
                                        MentionsAdapter.this.searchResultUsernames = null;
                                        MentionsAdapter.this.searchResultUsernamesMap = null;
                                        MentionsAdapter.this.searchResultCommands = null;
                                        MentionsAdapter.this.searchResultSuggestions = null;
                                        MentionsAdapter.this.searchResultCommandsHelp = null;
                                        MentionsAdapter.this.searchResultCommandsUsers = null;
                                        if (z2) {
                                            z2 = MentionsAdapter.this.searchResultBotContextSwitch != null;
                                            MentionsAdapter.this.notifyItemChanged(((z2 ? 1 : 0) + (MentionsAdapter.this.searchResultBotContext.size() - tLRPC$TL_messages_botResults.results.size())) - 1);
                                            MentionsAdapter.this.notifyItemRangeInserted((z2 ? 1 : 0) + (MentionsAdapter.this.searchResultBotContext.size() - tLRPC$TL_messages_botResults.results.size()), tLRPC$TL_messages_botResults.results.size());
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
                if (z) {
                    MessagesStorage.getInstance().getBotCache(stringBuilder, c38778);
                    return;
                }
                TLObject tLRPC$TL_messages_getInlineBotResults = new TLRPC$TL_messages_getInlineBotResults();
                tLRPC$TL_messages_getInlineBotResults.bot = MessagesController.getInputUser(user);
                tLRPC$TL_messages_getInlineBotResults.query = str;
                tLRPC$TL_messages_getInlineBotResults.offset = str2;
                if (!(!user.bot_inline_geo || this.lastKnownLocation == null || this.lastKnownLocation.getLatitude() == -1000.0d)) {
                    tLRPC$TL_messages_getInlineBotResults.flags |= 1;
                    tLRPC$TL_messages_getInlineBotResults.geo_point = new TLRPC$TL_inputGeoPoint();
                    tLRPC$TL_messages_getInlineBotResults.geo_point.lat = this.lastKnownLocation.getLatitude();
                    tLRPC$TL_messages_getInlineBotResults.geo_point._long = this.lastKnownLocation.getLongitude();
                }
                int i = (int) this.dialog_id;
                int i2 = (int) (this.dialog_id >> 32);
                if (i != 0) {
                    tLRPC$TL_messages_getInlineBotResults.peer = MessagesController.getInputPeer(i);
                } else {
                    tLRPC$TL_messages_getInlineBotResults.peer = new TLRPC$TL_inputPeerEmpty();
                }
                this.contextQueryReqid = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getInlineBotResults, c38778, 2);
            }
        } else if (this.delegate != null) {
            this.delegate.onContextSearch(false);
        }
    }

    public void addHashtagsFromMessage(CharSequence charSequence) {
        this.searchAdapterHelper.addHashtagsFromMessage(charSequence);
    }

    public void clearRecentHashtags() {
        this.searchAdapterHelper.clearRecentHashtags();
        this.searchResultHashtags.clear();
        notifyDataSetChanged();
        if (this.delegate != null) {
            this.delegate.needChangePanelVisibility(false);
        }
    }

    public String getBotCaption() {
        return this.foundContextBot != null ? this.foundContextBot.bot_inline_placeholder : (this.searchingContextUsername == null || !this.searchingContextUsername.equals("gif")) ? null : "Search GIFs";
    }

    public TLRPC$TL_inlineBotSwitchPM getBotContextSwitch() {
        return this.searchResultBotContextSwitch;
    }

    public int getContextBotId() {
        return this.foundContextBot != null ? this.foundContextBot.id : 0;
    }

    public String getContextBotName() {
        return this.foundContextBot != null ? this.foundContextBot.username : TtmlNode.ANONYMOUS_REGION_ID;
    }

    public User getContextBotUser() {
        return this.foundContextBot != null ? this.foundContextBot : null;
    }

    public Object getItem(int i) {
        if (this.searchResultBotContext != null) {
            if (this.searchResultBotContextSwitch != null) {
                if (i == 0) {
                    return this.searchResultBotContextSwitch;
                }
                i--;
            }
            return (i < 0 || i >= this.searchResultBotContext.size()) ? null : this.searchResultBotContext.get(i);
        } else if (this.searchResultUsernames != null) {
            return (i < 0 || i >= this.searchResultUsernames.size()) ? null : this.searchResultUsernames.get(i);
        } else {
            if (this.searchResultHashtags != null) {
                return (i < 0 || i >= this.searchResultHashtags.size()) ? null : this.searchResultHashtags.get(i);
            } else {
                if (this.searchResultSuggestions != null) {
                    return (i < 0 || i >= this.searchResultSuggestions.size()) ? null : this.searchResultSuggestions.get(i);
                } else {
                    if (this.searchResultCommands == null || i < 0 || i >= this.searchResultCommands.size()) {
                        return null;
                    }
                    if (this.searchResultCommandsUsers == null || (this.botsCount == 1 && !(this.info instanceof TL_channelFull))) {
                        return this.searchResultCommands.get(i);
                    }
                    if (this.searchResultCommandsUsers.get(i) != null) {
                        String str = "%s@%s";
                        Object[] objArr = new Object[2];
                        objArr[0] = this.searchResultCommands.get(i);
                        objArr[1] = this.searchResultCommandsUsers.get(i) != null ? ((User) this.searchResultCommandsUsers.get(i)).username : TtmlNode.ANONYMOUS_REGION_ID;
                        return String.format(str, objArr);
                    }
                    return String.format("%s", new Object[]{this.searchResultCommands.get(i)});
                }
            }
        }
    }

    public int getItemCount() {
        int i = 1;
        if (this.foundContextBot != null && !this.inlineMediaEnabled) {
            return 1;
        }
        if (this.searchResultBotContext == null) {
            return this.searchResultUsernames != null ? this.searchResultUsernames.size() : this.searchResultHashtags != null ? this.searchResultHashtags.size() : this.searchResultCommands != null ? this.searchResultCommands.size() : this.searchResultSuggestions != null ? this.searchResultSuggestions.size() : 0;
        } else {
            int size = this.searchResultBotContext.size();
            if (this.searchResultBotContextSwitch == null) {
                i = 0;
            }
            return i + size;
        }
    }

    public int getItemPosition(int i) {
        return (this.searchResultBotContext == null || this.searchResultBotContextSwitch == null) ? i : i - 1;
    }

    public int getItemViewType(int i) {
        return (this.foundContextBot == null || this.inlineMediaEnabled) ? this.searchResultBotContext != null ? (i != 0 || this.searchResultBotContextSwitch == null) ? 1 : 2 : 0 : 3;
    }

    public int getResultLength() {
        return this.resultLength;
    }

    public int getResultStartPosition() {
        return this.resultStartPosition;
    }

    public ArrayList<BotInlineResult> getSearchResultBotContext() {
        return this.searchResultBotContext;
    }

    public boolean isBannedInline() {
        return (this.foundContextBot == null || this.inlineMediaEnabled) ? false : true;
    }

    public boolean isBotCommands() {
        return this.searchResultCommands != null;
    }

    public boolean isBotContext() {
        return this.searchResultBotContext != null;
    }

    public boolean isEnabled(ViewHolder viewHolder) {
        return this.foundContextBot == null || this.inlineMediaEnabled;
    }

    public boolean isLongClickEnabled() {
        return (this.searchResultHashtags == null && this.searchResultCommands == null) ? false : true;
    }

    public boolean isMediaLayout() {
        return this.contextMedia;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        boolean z = true;
        if (viewHolder.getItemViewType() == 3) {
            TextView textView = (TextView) viewHolder.itemView;
            Chat currentChat = this.parentFragment.getCurrentChat();
            if (currentChat == null) {
                return;
            }
            if (AndroidUtilities.isBannedForever(currentChat.banned_rights.until_date)) {
                textView.setText(LocaleController.getString("AttachInlineRestrictedForever", R.string.AttachInlineRestrictedForever));
            } else {
                textView.setText(LocaleController.formatString("AttachInlineRestricted", R.string.AttachInlineRestricted, new Object[]{LocaleController.formatDateForBan((long) currentChat.banned_rights.until_date)}));
            }
        } else if (this.searchResultBotContext != null) {
            boolean z2 = this.searchResultBotContextSwitch != null;
            if (viewHolder.getItemViewType() != 2) {
                if (z2) {
                    i--;
                }
                ContextLinkCell contextLinkCell = (ContextLinkCell) viewHolder.itemView;
                BotInlineResult botInlineResult = (BotInlineResult) this.searchResultBotContext.get(i);
                boolean z3 = this.contextMedia;
                boolean z4 = i != this.searchResultBotContext.size() + -1;
                if (!(z2 && i == 0)) {
                    z = false;
                }
                contextLinkCell.setLink(botInlineResult, z3, z4, z);
            } else if (z2) {
                ((BotSwitchCell) viewHolder.itemView).setText(this.searchResultBotContextSwitch.text);
            }
        } else if (this.searchResultUsernames != null) {
            ((MentionCell) viewHolder.itemView).setUser((User) this.searchResultUsernames.get(i));
        } else if (this.searchResultHashtags != null) {
            ((MentionCell) viewHolder.itemView).setText((String) this.searchResultHashtags.get(i));
        } else if (this.searchResultSuggestions != null) {
            ((MentionCell) viewHolder.itemView).setEmojiSuggestion((EmojiSuggestion) this.searchResultSuggestions.get(i));
        } else if (this.searchResultCommands != null) {
            ((MentionCell) viewHolder.itemView).setBotCommand((String) this.searchResultCommands.get(i), (String) this.searchResultCommandsHelp.get(i), this.searchResultCommandsUsers != null ? (User) this.searchResultCommandsUsers.get(i) : null);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View mentionCell;
        switch (i) {
            case 0:
                mentionCell = new MentionCell(this.mContext);
                ((MentionCell) mentionCell).setIsDarkTheme(this.isDarkTheme);
                break;
            case 1:
                mentionCell = new ContextLinkCell(this.mContext);
                ((ContextLinkCell) mentionCell).setDelegate(new ContextLinkCellDelegate() {
                    public void didPressedImage(ContextLinkCell contextLinkCell) {
                        MentionsAdapter.this.delegate.onContextClick(contextLinkCell.getResult());
                    }
                });
                break;
            case 2:
                mentionCell = new BotSwitchCell(this.mContext);
                break;
            default:
                mentionCell = new TextView(this.mContext);
                mentionCell.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f));
                mentionCell.setTextSize(1, 14.0f);
                mentionCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
                break;
        }
        return new Holder(mentionCell);
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

    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (i != 2 || this.foundContextBot == null || !this.foundContextBot.bot_inline_geo) {
            return;
        }
        if (iArr.length <= 0 || iArr[0] != 0) {
            onLocationUnavailable();
        } else {
            this.locationProvider.start();
        }
    }

    public void searchForContextBotForNextOffset() {
        if (this.contextQueryReqid == 0 && this.nextQueryOffset != null && this.nextQueryOffset.length() != 0 && this.foundContextBot != null && this.searchingContextQuery != null) {
            searchForContextBotResults(true, this.foundContextBot, this.searchingContextQuery, this.nextQueryOffset);
        }
    }

    public void searchUsernameOrHashtag(String str, int i, ArrayList<MessageObject> arrayList, boolean z) {
        if (this.channelReqId != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.channelReqId, true);
            this.channelReqId = 0;
        }
        if (this.searchGlobalRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.searchGlobalRunnable);
            this.searchGlobalRunnable = null;
        }
        if (TextUtils.isEmpty(str)) {
            searchForContextBot(null, null);
            this.delegate.needChangePanelVisibility(false);
            this.lastText = null;
            return;
        }
        String substring;
        int i2;
        int i3 = str.length() > 0 ? i - 1 : i;
        this.lastText = null;
        this.lastUsernameOnly = z;
        StringBuilder stringBuilder = new StringBuilder();
        if (!z && this.needBotContext && str.charAt(0) == '@') {
            String str2;
            int indexOf = str.indexOf(32);
            int length = str.length();
            String str3 = null;
            if (indexOf > 0) {
                str3 = str.substring(1, indexOf);
                substring = str.substring(indexOf + 1);
            } else if (str.charAt(length - 1) == 't' && str.charAt(length - 2) == 'o' && str.charAt(length - 3) == 'b') {
                str3 = str.substring(1);
                substring = TtmlNode.ANONYMOUS_REGION_ID;
            } else {
                searchForContextBot(null, null);
                substring = null;
            }
            if (str3 == null || str3.length() < 1) {
                str2 = TtmlNode.ANONYMOUS_REGION_ID;
            } else {
                for (i2 = 1; i2 < str3.length(); i2++) {
                    char charAt = str3.charAt(i2);
                    if ((charAt < '0' || charAt > '9') && ((charAt < 'a' || charAt > 'z') && ((charAt < 'A' || charAt > 'Z') && charAt != '_'))) {
                        str2 = TtmlNode.ANONYMOUS_REGION_ID;
                        break;
                    }
                }
                str2 = str3;
            }
            searchForContextBot(str2, substring);
        } else {
            searchForContextBot(null, null);
        }
        if (this.foundContextBot == null) {
            int i4;
            Object obj;
            if (z) {
                stringBuilder.append(str.substring(1));
                this.resultStartPosition = 0;
                this.resultLength = stringBuilder.length();
                i4 = -1;
                i2 = 0;
                obj = null;
            } else {
                i4 = i3;
                obj = null;
                while (i4 >= 0) {
                    if (i4 < str.length()) {
                        char charAt2 = str.charAt(i4);
                        if (i4 == 0 || str.charAt(i4 - 1) == ' ' || str.charAt(i4 - 1) == '\n') {
                            if (charAt2 != '@') {
                                if (charAt2 != '#') {
                                    if (i4 != 0 || this.botInfo == null || charAt2 != '/') {
                                        if (charAt2 == ':' && stringBuilder.length() > 0) {
                                            this.resultStartPosition = i4;
                                            this.resultLength = stringBuilder.length() + 1;
                                            i4 = -1;
                                            i2 = 3;
                                            break;
                                        }
                                    }
                                    this.resultStartPosition = i4;
                                    this.resultLength = stringBuilder.length() + 1;
                                    i4 = -1;
                                    i2 = 2;
                                    break;
                                } else if (this.searchAdapterHelper.loadRecentHashtags()) {
                                    this.resultStartPosition = i4;
                                    this.resultLength = stringBuilder.length() + 1;
                                    stringBuilder.insert(0, charAt2);
                                    i4 = -1;
                                    i2 = 1;
                                } else {
                                    this.lastText = str;
                                    this.lastPosition = i;
                                    this.messages = arrayList;
                                    this.delegate.needChangePanelVisibility(false);
                                    return;
                                }
                            } else if (this.needUsernames || (this.needBotContext && i4 == 0)) {
                                if (this.info != null || i4 == 0) {
                                    i2 = 0;
                                    this.resultStartPosition = i4;
                                    this.resultLength = stringBuilder.length() + 1;
                                } else {
                                    this.lastText = str;
                                    this.lastPosition = i;
                                    this.messages = arrayList;
                                    this.delegate.needChangePanelVisibility(false);
                                    return;
                                }
                            }
                        }
                        if ((charAt2 < '0' || charAt2 > '9') && ((charAt2 < 'a' || charAt2 > 'z') && ((charAt2 < 'A' || charAt2 > 'Z') && charAt2 != '_'))) {
                            obj = 1;
                        }
                        stringBuilder.insert(0, charAt2);
                    }
                    i4--;
                }
                i4 = -1;
                i2 = -1;
            }
            if (i2 == -1) {
                this.delegate.needChangePanelVisibility(false);
            } else if (i2 == 0) {
                User user;
                r4 = new ArrayList();
                for (i2 = 0; i2 < Math.min(100, arrayList.size()); i2++) {
                    i3 = ((MessageObject) arrayList.get(i2)).messageOwner.from_id;
                    if (!r4.contains(Integer.valueOf(i3))) {
                        r4.add(Integer.valueOf(i3));
                    }
                }
                substring = stringBuilder.toString().toLowerCase();
                Object obj2 = substring.indexOf(32) >= 0 ? 1 : null;
                ArrayList arrayList2 = new ArrayList();
                final HashMap hashMap = new HashMap();
                HashMap hashMap2 = new HashMap();
                if (!z && this.needBotContext && r2 == 0 && !SearchQuery.inlineBots.isEmpty()) {
                    int i5 = 0;
                    for (i4 = 0; i4 < SearchQuery.inlineBots.size(); i4++) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_topPeer) SearchQuery.inlineBots.get(i4)).peer.user_id));
                        if (user != null) {
                            if (user.username != null && user.username.length() > 0 && ((substring.length() > 0 && user.username.toLowerCase().startsWith(substring)) || substring.length() == 0)) {
                                arrayList2.add(user);
                                hashMap.put(Integer.valueOf(user.id), user);
                                i5++;
                            }
                            if (i5 == 5) {
                                break;
                            }
                        }
                    }
                }
                Chat currentChat = this.parentFragment != null ? this.parentFragment.getCurrentChat() : this.info != null ? MessagesController.getInstance().getChat(Integer.valueOf(this.info.id)) : null;
                if (!(currentChat == null || this.info == null || this.info.participants == null || (ChatObject.isChannel(currentChat) && !currentChat.megagroup))) {
                    for (i4 = 0; i4 < this.info.participants.participants.size(); i4++) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(((ChatParticipant) this.info.participants.participants.get(i4)).user_id));
                        if (user != null && ((z || !UserObject.isUserSelf(user)) && !hashMap.containsKey(Integer.valueOf(user.id)))) {
                            if (substring.length() == 0) {
                                if (!user.deleted && (this.allowNewMentions || !(this.allowNewMentions || user.username == null || user.username.length() == 0))) {
                                    arrayList2.add(user);
                                }
                            } else if (user.username != null && user.username.length() > 0 && user.username.toLowerCase().startsWith(substring)) {
                                arrayList2.add(user);
                                hashMap2.put(Integer.valueOf(user.id), user);
                            } else if (this.allowNewMentions || !(user.username == null || user.username.length() == 0)) {
                                if (user.first_name != null && user.first_name.length() > 0 && user.first_name.toLowerCase().startsWith(substring)) {
                                    arrayList2.add(user);
                                    hashMap2.put(Integer.valueOf(user.id), user);
                                } else if (user.last_name != null && user.last_name.length() > 0 && user.last_name.toLowerCase().startsWith(substring)) {
                                    arrayList2.add(user);
                                    hashMap2.put(Integer.valueOf(user.id), user);
                                } else if (obj2 != null && ContactsController.formatName(user.first_name, user.last_name).toLowerCase().startsWith(substring)) {
                                    arrayList2.add(user);
                                    hashMap2.put(Integer.valueOf(user.id), user);
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
                this.searchResultUsernames = arrayList2;
                this.searchResultUsernamesMap = hashMap2;
                if (currentChat != null && currentChat.megagroup && substring.length() > 0) {
                    Runnable c38809 = new Runnable() {
                        public void run() {
                            if (MentionsAdapter.this.searchGlobalRunnable == this) {
                                TLObject tL_channels_getParticipants = new TL_channels_getParticipants();
                                tL_channels_getParticipants.channel = MessagesController.getInputChannel(currentChat);
                                tL_channels_getParticipants.limit = 20;
                                tL_channels_getParticipants.offset = 0;
                                tL_channels_getParticipants.filter = new TL_channelParticipantsSearch();
                                tL_channels_getParticipants.filter.f10136q = substring;
                                final int access$3004 = MentionsAdapter.access$3004(MentionsAdapter.this);
                                MentionsAdapter.this.channelReqId = ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipants, new RequestDelegate() {
                                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                                        AndroidUtilities.runOnUIThread(new Runnable() {
                                            public void run() {
                                                if (!(MentionsAdapter.this.channelReqId == 0 || access$3004 != MentionsAdapter.this.channelLastReqId || MentionsAdapter.this.searchResultUsernamesMap == null || MentionsAdapter.this.searchResultUsernames == null || tLRPC$TL_error != null)) {
                                                    TL_channels_channelParticipants tL_channels_channelParticipants = (TL_channels_channelParticipants) tLObject;
                                                    MessagesController.getInstance().putUsers(tL_channels_channelParticipants.users, false);
                                                    if (!tL_channels_channelParticipants.participants.isEmpty()) {
                                                        int clientUserId = UserConfig.getClientUserId();
                                                        for (int i = 0; i < tL_channels_channelParticipants.participants.size(); i++) {
                                                            ChannelParticipant channelParticipant = (ChannelParticipant) tL_channels_channelParticipants.participants.get(i);
                                                            if (!MentionsAdapter.this.searchResultUsernamesMap.containsKey(Integer.valueOf(channelParticipant.user_id)) && (MentionsAdapter.this.isSearchingMentions || channelParticipant.user_id != clientUserId)) {
                                                                User user = MessagesController.getInstance().getUser(Integer.valueOf(channelParticipant.user_id));
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
                    this.searchGlobalRunnable = c38809;
                    AndroidUtilities.runOnUIThread(c38809, 200);
                }
                Collections.sort(this.searchResultUsernames, new Comparator<User>() {
                    public int compare(User user, User user2) {
                        if (hashMap.containsKey(Integer.valueOf(user.id)) && hashMap.containsKey(Integer.valueOf(user2.id))) {
                            return 0;
                        }
                        if (hashMap.containsKey(Integer.valueOf(user.id))) {
                            return -1;
                        }
                        if (hashMap.containsKey(Integer.valueOf(user2.id))) {
                            return 1;
                        }
                        int indexOf = r4.indexOf(Integer.valueOf(user.id));
                        int indexOf2 = r4.indexOf(Integer.valueOf(user2.id));
                        return (indexOf == -1 || indexOf2 == -1) ? (indexOf == -1 || indexOf2 != -1) ? (indexOf != -1 || indexOf2 == -1) ? 0 : 1 : -1 : indexOf >= indexOf2 ? indexOf == indexOf2 ? 0 : 1 : -1;
                    }
                });
                notifyDataSetChanged();
                this.delegate.needChangePanelVisibility(!arrayList2.isEmpty());
            } else if (i2 == 1) {
                ArrayList arrayList3 = new ArrayList();
                String toLowerCase = stringBuilder.toString().toLowerCase();
                r4 = this.searchAdapterHelper.getHashtags();
                for (i2 = 0; i2 < r4.size(); i2++) {
                    HashtagObject hashtagObject = (HashtagObject) r4.get(i2);
                    if (!(hashtagObject == null || hashtagObject.hashtag == null || !hashtagObject.hashtag.startsWith(toLowerCase))) {
                        arrayList3.add(hashtagObject.hashtag);
                    }
                }
                this.searchResultHashtags = arrayList3;
                this.searchResultUsernames = null;
                this.searchResultUsernamesMap = null;
                this.searchResultCommands = null;
                this.searchResultCommandsHelp = null;
                this.searchResultCommandsUsers = null;
                this.searchResultSuggestions = null;
                notifyDataSetChanged();
                this.delegate.needChangePanelVisibility(!arrayList3.isEmpty());
            } else if (i2 == 2) {
                ArrayList arrayList4 = new ArrayList();
                r4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList();
                String toLowerCase2 = stringBuilder.toString().toLowerCase();
                for (Entry value : this.botInfo.entrySet()) {
                    BotInfo botInfo = (BotInfo) value.getValue();
                    for (i4 = 0; i4 < botInfo.commands.size(); i4++) {
                        TL_botCommand tL_botCommand = (TL_botCommand) botInfo.commands.get(i4);
                        if (!(tL_botCommand == null || tL_botCommand.command == null || !tL_botCommand.command.startsWith(toLowerCase2))) {
                            arrayList4.add("/" + tL_botCommand.command);
                            r4.add(tL_botCommand.description);
                            arrayList5.add(MessagesController.getInstance().getUser(Integer.valueOf(botInfo.user_id)));
                        }
                    }
                }
                this.searchResultHashtags = null;
                this.searchResultUsernames = null;
                this.searchResultUsernamesMap = null;
                this.searchResultSuggestions = null;
                this.searchResultCommands = arrayList4;
                this.searchResultCommandsHelp = r4;
                this.searchResultCommandsUsers = arrayList5;
                notifyDataSetChanged();
                this.delegate.needChangePanelVisibility(!arrayList4.isEmpty());
            } else if (i2 != 3) {
            } else {
                if (obj == null) {
                    Object[] suggestion = Emoji.getSuggestion(stringBuilder.toString());
                    if (suggestion != null) {
                        this.searchResultSuggestions = new ArrayList();
                        for (Object obj3 : suggestion) {
                            EmojiSuggestion emojiSuggestion = (EmojiSuggestion) obj3;
                            emojiSuggestion.emoji = emojiSuggestion.emoji.replace("️", TtmlNode.ANONYMOUS_REGION_ID);
                            this.searchResultSuggestions.add(emojiSuggestion);
                        }
                        Emoji.loadRecentEmoji();
                        Collections.sort(this.searchResultSuggestions, new Comparator<EmojiSuggestion>() {
                            public int compare(EmojiSuggestion emojiSuggestion, EmojiSuggestion emojiSuggestion2) {
                                Integer num = (Integer) Emoji.emojiUseHistory.get(emojiSuggestion.emoji);
                                Integer valueOf = num == null ? Integer.valueOf(0) : num;
                                num = (Integer) Emoji.emojiUseHistory.get(emojiSuggestion2.emoji);
                                if (num == null) {
                                    num = Integer.valueOf(0);
                                }
                                return num.compareTo(valueOf);
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
                    return;
                }
                this.delegate.needChangePanelVisibility(false);
            }
        }
    }

    public void setAllowNewMentions(boolean z) {
        this.allowNewMentions = z;
    }

    public void setBotInfo(HashMap<Integer, BotInfo> hashMap) {
        this.botInfo = hashMap;
    }

    public void setBotsCount(int i) {
        this.botsCount = i;
    }

    public void setChatInfo(ChatFull chatFull) {
        this.info = chatFull;
        if (!(this.inlineMediaEnabled || this.foundContextBot == null || this.parentFragment == null)) {
            Chat currentChat = this.parentFragment.getCurrentChat();
            if (currentChat != null) {
                this.inlineMediaEnabled = ChatObject.canSendStickers(currentChat);
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

    public void setNeedBotContext(boolean z) {
        this.needBotContext = z;
    }

    public void setNeedUsernames(boolean z) {
        this.needUsernames = z;
    }

    public void setParentFragment(ChatActivity chatActivity) {
        this.parentFragment = chatActivity;
    }

    public void setSearchingMentions(boolean z) {
        this.isSearchingMentions = z;
    }
}
