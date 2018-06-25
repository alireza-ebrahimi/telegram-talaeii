package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.C0192t;
import android.support.design.widget.C0192t.C0182b;
import android.support.design.widget.C0192t.C0187e;
import android.support.v4.content.C0235a;
import android.support.v4.content.C0424l;
import android.support.v4.view.ViewPager;
import android.support.v4.view.aa;
import android.support.v7.widget.av;
import android.support.v7.widget.av.C1032b;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.C2529a;
import org.telegram.customization.Activities.C2539c;
import org.telegram.customization.Activities.C2542d;
import org.telegram.customization.Activities.C2622l;
import org.telegram.customization.Activities.ClientPersonalizeActivity;
import org.telegram.customization.Model.CAI;
import org.telegram.customization.Model.DialogTab;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.Model.OfficialJoinChannel;
import org.telegram.customization.Model.Payment.HostRequestData;
import org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.service.C2827a;
import org.telegram.customization.util.C2872c;
import org.telegram.customization.util.C2874e;
import org.telegram.customization.util.C2885i;
import org.telegram.customization.util.view.p171b.C2945c;
import org.telegram.customization.util.view.p171b.C2945c.C2935a;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.news.C3747b;
import org.telegram.news.C3752d;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlChat;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlChatInvite;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlStickerSet;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlUnknown;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlUser;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatInvite;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.StickerSet;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.MenuDrawable;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Adapters.DialogsAdapter;
import org.telegram.ui.Adapters.DialogsSearchAdapter;
import org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate;
import org.telegram.ui.Cells.DialogCell;
import org.telegram.ui.Cells.DialogsEmptyCell;
import org.telegram.ui.Cells.DividerCell;
import org.telegram.ui.Cells.DrawerActionCell;
import org.telegram.ui.Cells.DrawerProfileCell;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.HashtagSearchCell;
import org.telegram.ui.Cells.HintDialogCell;
import org.telegram.ui.Cells.LoadingCell;
import org.telegram.ui.Cells.ProfileSearchCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.ChatActivityEnterView;
import org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.EmptyTextProgressView2;
import org.telegram.ui.Components.FragmentContextView;
import org.telegram.ui.Components.JoinGroupAlert;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.SizeNotifierFrameLayout;
import org.telegram.ui.Components.StickersAlert;
import org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PlaceProviderObject;
import p129d.p130a.p131a.p132a.C2339b.C2335b;
import p129d.p130a.p131a.p132a.C2339b.C2336c;
import utils.C3792d;
import utils.C5323c;
import utils.p178a.C3791b;
import utils.view.SlidingTabLayout;
import utils.view.SlidingTabLayout.TabColorizer;
import utils.view.bottombar.BottomBar;
import utils.view.bottombar.OnTabSelectListener;

public class DialogsActivity extends BaseFragment implements C2497d, NotificationCenterDelegate {
    public static boolean dialogsLoaded;
    final String FRG_ELECTION = "FRG_ELECTION";
    final String FRG_HOME = "FRG_HOME";
    final String FRG_HOT = "FRG_HOT";
    final String FRG_NEWS_LIST = "FRG_NEWS_LIST";
    final String FRG_SEARCH = "FRG_SEARCH";
    final String FRG_USER = "FRG_USER";
    private int actionTypeForSelectMode = -1;
    private String addToGroupAlertString;
    boolean allMutedAds = true;
    boolean allMutedAll = true;
    boolean allMutedBots = true;
    boolean allMutedChannels = true;
    boolean allMutedFavs = true;
    boolean allMutedGroups = true;
    boolean allMutedGroupsAll = true;
    boolean allMutedHidden = true;
    boolean allMutedMegaGroups = true;
    boolean allMutedUnread = true;
    boolean allMutedUsers = true;
    private boolean allSelected = false;
    private BackupImageView avatarImage;
    BottomBar bottomBar;
    private int bottomBarHeight = 40;
    private boolean cantSendToChannels;
    private int chat_id = 0;
    private boolean checkPermission = true;
    private ChatActivityEnterView commentView;
    SizeNotifierFrameLayout contentView;
    int countAds = 0;
    int countAll = 0;
    int countBots = 0;
    int countChannels = 0;
    int countFavs = 0;
    int countGroups = 0;
    int countGroupsAll = 0;
    int countHidden = 0;
    int countMegaGroups = 0;
    int countUnread = 0;
    int countUsers = 0;
    private C2539c debugActivity;
    private DialogsActivityDelegate delegate;
    private DialogsAdapter dialogsAdapter;
    private DialogsAdapter dialogsBackupAdapter;
    private DialogsSearchAdapter dialogsSearchAdapter;
    private int dialogsType;
    private boolean disableAnimation;
    private C3747b electionActivity;
    private LinearLayout emptyView;
    private ImageView floatingButton;
    private boolean floatingHidden;
    private final AccelerateDecelerateInterpolator floatingInterpolator = new AccelerateDecelerateInterpolator();
    private boolean forceRefreshTabs;
    private FragmentContextView fragmentContextView;
    private FragmentContextView fragmentLocationContextView;
    FrameLayout frameLayout;
    private boolean hideTabs;
    private boolean isSelectMode = false;
    boolean isTutorailShowedAndttt = false;
    private volatile long lastAnimUpdate = 0;
    private LinearLayoutManager layoutManager;
    private RecyclerListView listView;
    ProgressBar loading;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && !intent.getAction().equals("ACTION_FAVE_CHANGED")) {
                if (intent.getAction().equals("ACTION_SWITCH_TAB")) {
                    try {
                        int intExtra = intent.getIntExtra("EXTRA_CURRENT_POSITION", 2);
                        Log.d("alireza", "alireza broadcast recieved" + intExtra);
                        DialogsActivity.this.bottomBar.m14252a(intExtra);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (intent.getAction().equals("ACTION_REBUILD_ALL")) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(10)});
                    if (DialogsActivity.this.parentLayout != null) {
                        DialogsActivity.this.parentLayout.rebuildAllFragmentViews(true, true);
                    }
                }
            }
        }
    };
    ActionBarMenu menu;
    private C3752d newsListActivity;
    boolean onResumeCalled = false;
    private DialogsOnTouch onTouchListener = null;
    private boolean onlySelect;
    private long openedDialogId;
    private ActionBarMenuItem passcodeItem;
    private AlertDialog permissionDialog;
    private int prevPosition;
    private int prevTop;
    private RadialProgressView progressView;
    private PhotoViewerProvider provider = new C47081();
    C2497d responseReceiver;
    private boolean scrollUpdated;
    private EmptyTextProgressView2 searchEmptyView;
    private String searchString;
    private boolean searchWas;
    private boolean searching;
    private String selectAlertString;
    private String selectAlertStringGroup;
    private long selectedDialog;
    private int selectedTab;
    private RecyclerView sideMenu;
    private SlidingTabLayout slidingTabLayout;
    private C2622l slsHotPostActivity;
    private C2622l slsSearchActivity;
    private C0192t tabLayout;
    private int tabsHeight;
    private boolean tabsHidden;
    private LinearLayout tabsLayout;
    private FrameLayout tabsView;
    private float touchPositionDP;
    private int user_id = 0;
    private ViewPager viewPager;

    public interface DialogsActivityDelegate {
        void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z);
    }

    /* renamed from: org.telegram.ui.DialogsActivity$1 */
    class C47081 extends EmptyPhotoViewerProvider {
        C47081() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            PlaceProviderObject placeProviderObject = null;
            if (fileLocation != null) {
                FileLocation fileLocation2;
                if (DialogsActivity.this.user_id != 0) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(DialogsActivity.this.user_id));
                    if (!(user == null || user.photo == null || user.photo.photo_big == null)) {
                        fileLocation2 = user.photo.photo_big;
                    }
                    fileLocation2 = null;
                } else {
                    if (DialogsActivity.this.chat_id != 0) {
                        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(DialogsActivity.this.chat_id));
                        if (!(chat == null || chat.photo == null || chat.photo.photo_big == null)) {
                            fileLocation2 = chat.photo.photo_big;
                        }
                    }
                    fileLocation2 = null;
                }
                if (fileLocation2 != null && fileLocation2.local_id == fileLocation.local_id && fileLocation2.volume_id == fileLocation.volume_id && fileLocation2.dc_id == fileLocation.dc_id) {
                    int[] iArr = new int[2];
                    DialogsActivity.this.avatarImage.getLocationInWindow(iArr);
                    placeProviderObject = new PlaceProviderObject();
                    placeProviderObject.viewX = iArr[0];
                    placeProviderObject.viewY = iArr[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight);
                    placeProviderObject.parentView = DialogsActivity.this.avatarImage;
                    placeProviderObject.imageReceiver = DialogsActivity.this.avatarImage.getImageReceiver();
                    if (DialogsActivity.this.user_id != 0) {
                        placeProviderObject.dialogId = DialogsActivity.this.user_id;
                    } else if (DialogsActivity.this.chat_id != 0) {
                        placeProviderObject.dialogId = -DialogsActivity.this.chat_id;
                    }
                    placeProviderObject.thumb = placeProviderObject.imageReceiver.getBitmap();
                    placeProviderObject.size = -1;
                    placeProviderObject.radius = DialogsActivity.this.avatarImage.getImageReceiver().getRoundRadius();
                    placeProviderObject.scale = DialogsActivity.this.avatarImage.getScaleX();
                }
            }
            return placeProviderObject;
        }

        public void willHidePhotoViewer() {
            DialogsActivity.this.avatarImage.getImageReceiver().setVisible(true, true);
        }
    }

    /* renamed from: org.telegram.ui.DialogsActivity$2 */
    class C47162 implements C2497d {
        C47162() {
        }

        public void onResult(Object obj, int i) {
            switch (i) {
                case 27:
                    HostRequestData hostRequestData = (HostRequestData) obj;
                    Bundle bundle = new Bundle();
                    bundle.putString("host_security_token", C3791b.ar(ApplicationLoader.applicationContext));
                    bundle.putInt("host_id", 1152);
                    bundle.putString("protocol_version", "1.8.0");
                    bundle.putString("host_data", hostRequestData.getHostRequest());
                    bundle.putString("host_data_sign", hostRequestData.getHostRequestSign());
                    bundle.putString("client_mobile_no", "09127020559");
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: org.telegram.ui.DialogsActivity$4 */
    class C47214 extends ActionBarMenuOnItemClick {
        C47214() {
        }

        public void onItemClick(int i) {
            boolean z = true;
            if (i == -1) {
                if (DialogsActivity.this.onlySelect) {
                    DialogsActivity.this.finishFragment();
                } else if (DialogsActivity.this.parentLayout != null) {
                    DialogsActivity.this.parentLayout.getDrawerLayoutContainer().openDrawer(false);
                }
            } else if (i == 1) {
                if (UserConfig.appLocked) {
                    z = false;
                }
                UserConfig.appLocked = z;
                UserConfig.saveConfig(false);
                DialogsActivity.this.updatePasscodeButton();
            }
        }
    }

    /* renamed from: org.telegram.ui.DialogsActivity$7 */
    class C47247 implements OnItemClickListener {
        C47247() {
        }

        public void onItemClick(View view, int i) {
            if (DialogsActivity.this.isSelectMode) {
                if (DialogsActivity.this.dialogsAdapter.getItem(i) != null) {
                    MessagesController.getInstance().updateDialogSelectStatus(((TLRPC$TL_dialog) DialogsActivity.this.dialogsAdapter.getItem(i)).id);
                }
                DialogsActivity.this.dialogsAdapter.notifyDataSetChanged();
            } else if (DialogsActivity.this.listView != null && DialogsActivity.this.listView.getAdapter() != null && DialogsActivity.this.getParentActivity() != null) {
                int i2;
                int id;
                long j = 0;
                Adapter adapter = DialogsActivity.this.listView.getAdapter();
                if (adapter == DialogsActivity.this.dialogsAdapter) {
                    long j2;
                    TLObject item = DialogsActivity.this.dialogsAdapter.getItem(i);
                    if (item instanceof TLRPC$TL_dialog) {
                        j2 = ((TLRPC$TL_dialog) item).id;
                    } else if (item instanceof TLRPC$TL_recentMeUrlChat) {
                        j2 = (long) (-((TLRPC$TL_recentMeUrlChat) item).chat_id);
                    } else if (item instanceof TLRPC$TL_recentMeUrlUser) {
                        j2 = (long) ((TLRPC$TL_recentMeUrlUser) item).user_id;
                    } else if (item instanceof TLRPC$TL_recentMeUrlChatInvite) {
                        TLRPC$TL_recentMeUrlChatInvite tLRPC$TL_recentMeUrlChatInvite = (TLRPC$TL_recentMeUrlChatInvite) item;
                        ChatInvite chatInvite = tLRPC$TL_recentMeUrlChatInvite.chat_invite;
                        if ((chatInvite.chat == null && (!chatInvite.channel || chatInvite.megagroup)) || (chatInvite.chat != null && (!ChatObject.isChannel(chatInvite.chat) || chatInvite.chat.megagroup))) {
                            String str = tLRPC$TL_recentMeUrlChatInvite.url;
                            int indexOf = str.indexOf(47);
                            if (indexOf > 0) {
                                str = str.substring(indexOf + 1);
                            }
                            DialogsActivity.this.showDialog(new JoinGroupAlert(DialogsActivity.this.getParentActivity(), chatInvite, str, DialogsActivity.this));
                            return;
                        } else if (chatInvite.chat != null) {
                            j2 = (long) (-chatInvite.chat.id);
                        } else {
                            return;
                        }
                    } else if (item instanceof TLRPC$TL_recentMeUrlStickerSet) {
                        StickerSet stickerSet = ((TLRPC$TL_recentMeUrlStickerSet) item).set.set;
                        InputStickerSet tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
                        tLRPC$TL_inputStickerSetID.id = stickerSet.id;
                        tLRPC$TL_inputStickerSetID.access_hash = stickerSet.access_hash;
                        DialogsActivity.this.showDialog(new StickersAlert(DialogsActivity.this.getParentActivity(), DialogsActivity.this, tLRPC$TL_inputStickerSetID, null, null));
                        return;
                    } else if (!(item instanceof TLRPC$TL_recentMeUrlUnknown)) {
                        return;
                    } else {
                        return;
                    }
                    j = j2;
                    i2 = 0;
                } else {
                    if (adapter == DialogsActivity.this.dialogsSearchAdapter) {
                        Object item2 = DialogsActivity.this.dialogsSearchAdapter.getItem(i);
                        if (item2 instanceof User) {
                            j = (long) ((User) item2).id;
                            if (DialogsActivity.this.dialogsSearchAdapter.isGlobalSearch(i)) {
                                ArrayList arrayList = new ArrayList();
                                arrayList.add((User) item2);
                                MessagesController.getInstance().putUsers(arrayList, false);
                                MessagesStorage.getInstance().putUsersAndChats(arrayList, null, false, true);
                            }
                            if (!DialogsActivity.this.onlySelect) {
                                DialogsActivity.this.dialogsSearchAdapter.putRecentSearch(j, (User) item2);
                                i2 = 0;
                            }
                        } else if (item2 instanceof Chat) {
                            if (DialogsActivity.this.dialogsSearchAdapter.isGlobalSearch(i)) {
                                ArrayList arrayList2 = new ArrayList();
                                arrayList2.add((Chat) item2);
                                MessagesController.getInstance().putChats(arrayList2, false);
                                MessagesStorage.getInstance().putUsersAndChats(null, arrayList2, false, true);
                            }
                            j = ((Chat) item2).id > 0 ? (long) (-((Chat) item2).id) : AndroidUtilities.makeBroadcastId(((Chat) item2).id);
                            if (!DialogsActivity.this.onlySelect) {
                                DialogsActivity.this.dialogsSearchAdapter.putRecentSearch(j, (Chat) item2);
                                i2 = 0;
                            }
                        } else if (item2 instanceof EncryptedChat) {
                            j = ((long) ((EncryptedChat) item2).id) << 32;
                            if (!DialogsActivity.this.onlySelect) {
                                DialogsActivity.this.dialogsSearchAdapter.putRecentSearch(j, (EncryptedChat) item2);
                                i2 = 0;
                            }
                        } else if (item2 instanceof MessageObject) {
                            MessageObject messageObject = (MessageObject) item2;
                            j = messageObject.getDialogId();
                            id = messageObject.getId();
                            DialogsActivity.this.dialogsSearchAdapter.addHashtagsFromMessage(DialogsActivity.this.dialogsSearchAdapter.getLastSearchString());
                            i2 = id;
                        } else if (item2 instanceof String) {
                            DialogsActivity.this.actionBar.openSearchField((String) item2);
                        }
                    }
                    i2 = 0;
                }
                if (j != 0) {
                    int i3;
                    boolean z = LocaleController.isRTL ? C3792d.b((float) view.getWidth(), ApplicationLoader.applicationContext) - 65.0f < DialogsActivity.this.touchPositionDP : DialogsActivity.this.touchPositionDP < 65.0f;
                    if (z) {
                        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                        DialogsActivity.this.user_id = 0;
                        DialogsActivity.this.chat_id = 0;
                        i3 = (int) j;
                        int i4 = (int) (j >> 32);
                        if (i3 == 0) {
                            DialogsActivity.this.user_id = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i4)).user_id;
                        } else if (i4 == 1) {
                            DialogsActivity.this.chat_id = i3;
                        } else if (i3 > 0) {
                            DialogsActivity.this.user_id = i3;
                        } else if (i3 < 0) {
                            DialogsActivity.this.chat_id = -i3;
                        }
                        Bundle bundle;
                        if (DialogsActivity.this.user_id != 0) {
                            id = sharedPreferences.getInt("dialogsClickOnPic", 0);
                            if (id == 2) {
                                bundle = new Bundle();
                                bundle.putInt("user_id", DialogsActivity.this.user_id);
                                DialogsActivity.this.presentFragment(new ProfileActivity(bundle));
                                return;
                            } else if (id == 1) {
                                User user = MessagesController.getInstance().getUser(Integer.valueOf(DialogsActivity.this.user_id));
                                if (user.photo != null && user.photo.photo_big != null) {
                                    PhotoViewer.getInstance().setParentActivity(DialogsActivity.this.getParentActivity());
                                    PhotoViewer.getInstance().openPhoto(user.photo.photo_big, DialogsActivity.this.provider);
                                    return;
                                }
                                return;
                            }
                        } else if (DialogsActivity.this.chat_id != 0) {
                            id = sharedPreferences.getInt("dialogsClickOnGroupPic", 0);
                            if (id == 2) {
                                MessagesController.getInstance().loadChatInfo(DialogsActivity.this.chat_id, null, false);
                                bundle = new Bundle();
                                bundle.putInt("chat_id", DialogsActivity.this.chat_id);
                                DialogsActivity.this.presentFragment(new ProfileActivity(bundle));
                                return;
                            } else if (id == 1) {
                                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(DialogsActivity.this.chat_id));
                                if (chat.photo != null && chat.photo.photo_big != null) {
                                    PhotoViewer.getInstance().setParentActivity(DialogsActivity.this.getParentActivity());
                                    PhotoViewer.getInstance().openPhoto(chat.photo.photo_big, DialogsActivity.this.provider);
                                    return;
                                }
                                return;
                            }
                        }
                    }
                    if (!DialogsActivity.this.onlySelect) {
                        Bundle bundle2 = new Bundle();
                        id = (int) j;
                        i3 = (int) (j >> 32);
                        if (id == 0) {
                            bundle2.putInt("enc_id", i3);
                        } else if (i3 == 1) {
                            bundle2.putInt("chat_id", id);
                        } else if (id > 0) {
                            bundle2.putInt("user_id", id);
                        } else if (id < 0) {
                            if (i2 != 0) {
                                Chat chat2 = MessagesController.getInstance().getChat(Integer.valueOf(-id));
                                if (!(chat2 == null || chat2.migrated_to == null)) {
                                    bundle2.putInt("migrated_to", id);
                                    id = -chat2.migrated_to.channel_id;
                                }
                            }
                            bundle2.putInt("chat_id", -id);
                        }
                        if (i2 != 0) {
                            bundle2.putInt("message_id", i2);
                        } else if (DialogsActivity.this.actionBar != null) {
                            DialogsActivity.this.actionBar.closeSearchField();
                        }
                        if (AndroidUtilities.isTablet()) {
                            if (DialogsActivity.this.openedDialogId == j && adapter != DialogsActivity.this.dialogsSearchAdapter) {
                                return;
                            }
                            if (DialogsActivity.this.dialogsAdapter != null) {
                                DialogsActivity.this.dialogsAdapter.setOpenedDialogId(DialogsActivity.this.openedDialogId = j);
                                DialogsActivity.this.updateVisibleRows(512);
                            }
                        }
                        if (DialogsActivity.this.searchString != null) {
                            if (MessagesController.checkCanOpenChat(bundle2, DialogsActivity.this)) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                DialogsActivity.this.presentFragment(new ChatActivity(bundle2));
                            }
                        } else if (MessagesController.checkCanOpenChat(bundle2, DialogsActivity.this)) {
                            DialogsActivity.this.presentFragment(new ChatActivity(bundle2));
                        }
                    } else if (DialogsActivity.this.dialogsAdapter.hasSelectedDialogs()) {
                        DialogsActivity.this.dialogsAdapter.addOrRemoveSelectedDialog(j, view);
                        DialogsActivity.this.updateSelectedCount();
                    } else {
                        DialogsActivity.this.didSelectResult(j, true, false);
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.DialogsActivity$9 */
    class C47329 extends ViewOutlineProvider {
        C47329() {
        }

        @SuppressLint({"NewApi"})
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        }
    }

    public class CustomPagerAdapter extends aa {
        private Context mContext;

        public CustomPagerAdapter(Context context) {
            this.mContext = context;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        public int getCount() {
            return 5;
        }

        public CharSequence getPageTitle(int i) {
            return "85";
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            ViewGroup viewGroup2 = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R.layout.sls_transparent, viewGroup, false);
            viewGroup.addView(viewGroup2);
            return viewGroup2;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }

    public class DialogsOnTouch implements OnTouchListener {
        private static final int MIN_DISTANCE_HIGH = 40;
        private static final int MIN_DISTANCE_HIGH_Y = 60;
        private DisplayMetrics displayMetrics;
        private float downX;
        private float downY;
        Context mContext;
        private float upX;
        private float upY;
        private float vDPI = (this.displayMetrics.xdpi / 160.0f);

        public DialogsOnTouch(Context context) {
            this.mContext = context;
            this.displayMetrics = context.getResources().getDisplayMetrics();
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            DialogsActivity.this.touchPositionDP = (float) Math.round(motionEvent.getX() / this.vDPI);
            if (DialogsActivity.this.hideTabs) {
                return false;
            }
            switch (motionEvent.getAction()) {
                case 0:
                    this.downX = (float) Math.round(motionEvent.getX() / this.vDPI);
                    this.downY = (float) Math.round(motionEvent.getY() / this.vDPI);
                    if (DialogsActivity.this.touchPositionDP > 50.0f) {
                        DialogsActivity.this.parentLayout.getDrawerLayoutContainer().setAllowOpenDrawer(false, false);
                    }
                    return view instanceof LinearLayout;
                case 1:
                    this.upX = (float) Math.round(motionEvent.getX() / this.vDPI);
                    this.upY = (float) Math.round(motionEvent.getY() / this.vDPI);
                    float f = this.downX - this.upX;
                    float f2 = this.downY - this.upY;
                    if (Math.abs(f) > 40.0f && Math.abs(f2) < 60.0f) {
                        DialogsActivity.this.refreshDialogType(f >= BitmapDescriptorFactory.HUE_RED ? 0 : 1);
                        this.downX = (float) Math.round(motionEvent.getX() / this.vDPI);
                        DialogsActivity.this.refreshTabAndListViews(false);
                    }
                    if (DialogsActivity.this.touchPositionDP <= 50.0f) {
                        return false;
                    }
                    DialogsActivity.this.parentLayout.getDrawerLayoutContainer().setAllowOpenDrawer(true, false);
                    return false;
                default:
                    return false;
            }
        }
    }

    public DialogsActivity(Bundle bundle) {
        super(bundle);
    }

    private void addActionbarItems(final Context context) {
        if (this.isSelectMode) {
            this.parentLayout.setInSelectMode(true);
            this.menu.addItem(7, (int) R.drawable.ic_checkbox_multiple_blank_outline_white_24dp).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (DialogsActivity.this.allSelected) {
                        MessagesController.getInstance().unSelectAllDialogs();
                        DialogsActivity.this.menu.getItem(7).setIcon((int) R.drawable.ic_checkbox_multiple_blank_outline_white_24dp);
                        DialogsActivity.this.allSelected = false;
                    } else {
                        MessagesController.getInstance().selectAllDialogs();
                        DialogsActivity.this.menu.getItem(7).setIcon((int) R.drawable.ic_checkbox_multiple_marked_white_24dp);
                        DialogsActivity.this.allSelected = true;
                    }
                    DialogsActivity.this.dialogsAdapter.notifyDataSetChanged();
                }
            });
            this.menu.addItem(6, (int) R.drawable.ic_done).setOnClickListener(new OnClickListener() {

                /* renamed from: org.telegram.ui.DialogsActivity$22$1 */
                class C47091 implements DialogInterface.OnClickListener {
                    C47091() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (DialogsActivity.this.actionTypeForSelectMode) {
                            case 1:
                                MessagesController.getInstance().deleteSelectedDialogs();
                                break;
                            case 2:
                                MessagesController.getInstance().favSelectedDialogs();
                                break;
                            case 3:
                                MessagesController.getInstance().unFavSelectedDialogs();
                                break;
                            case 4:
                                DialogsActivity.this.hideSelectedDialogs();
                                if (DialogsActivity.this.responseReceiver != null) {
                                    DialogsActivity.this.responseReceiver.onResult(null, 0);
                                    break;
                                }
                                break;
                            case 5:
                                MessagesController.getInstance().muteSelectedDialogs();
                                break;
                            case 6:
                                MessagesController.getInstance().unMuteSelectedDialogs();
                                break;
                            case 7:
                                MessagesController.getInstance().markSelectedDialogAsRead();
                                break;
                        }
                        DialogsActivity.this.finishFragment();
                    }
                }

                public void onClick(View view) {
                    CharSequence access$4100 = DialogsActivity.this.getActionMessage();
                    Builder builder = new Builder(DialogsActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                    builder.setMessage(access$4100);
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C47091());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    DialogsActivity.this.showDialog(builder.create());
                }
            });
            return;
        }
        if (!this.onlySelect && this.searchString == null) {
            this.passcodeItem = this.menu.addItem(1, (int) R.drawable.lock_close);
            updatePasscodeButton();
        }
        if (C3791b.ab(ApplicationLoader.applicationContext)) {
            this.menu.addItem(9, getParentActivity().getResources().getDrawable(R.drawable.coins_act)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (C3791b.Y(ApplicationLoader.applicationContext) == null || C3791b.Y(ApplicationLoader.applicationContext).size() <= 0) {
                        DialogsActivity.this.presentFragment(new C2542d(), false);
                    } else {
                        DialogsActivity.this.presentFragment(new C2529a(), false);
                    }
                }
            });
        }
        try {
            if (C3791b.ac(ApplicationLoader.applicationContext)) {
                this.menu.getItem(9).setVisibility(0);
            } else {
                this.menu.getItem(9).setVisibility(8);
            }
        } catch (Exception e) {
        }
        this.menu.addItem(4, getParentActivity().getResources().getDrawable(C3791b.x(getParentActivity()) ? R.drawable.ic_lock_open_outline : R.drawable.ic_lock_outline)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (C3791b.x(DialogsActivity.this.getParentActivity())) {
                    DialogsActivity.this.menu.getItem(4).setIcon((int) R.drawable.ic_lock_outline);
                    C3791b.h(DialogsActivity.this.getParentActivity(), false);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(-1)});
                    return;
                }
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.password_dialog);
                dialog.setCancelable(true);
                final EditText editText = (EditText) dialog.findViewById(R.id.input_password);
                TextView textView = (TextView) dialog.findViewById(R.id.passDialogMessage);
                if (TextUtils.isEmpty(C3791b.d(context))) {
                    textView.setText(LocaleController.getString("PleaseEnterPassword", R.string.PleaseEnterPassword));
                } else {
                    textView.setText(LocaleController.getString("PleaseEnterCurrentPassword", R.string.PleaseEnterCurrentPassword));
                }
                dialog.findViewById(R.id.tvOk).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        String obj = editText.getText().toString();
                        if (TextUtils.isEmpty(obj)) {
                            editText.setError(LocaleController.getString("PleaseEnterCurrentPassword", R.string.PleaseEnterCurrentPassword));
                            editText.requestFocus();
                            return;
                        }
                        CharSequence d = C3791b.d(context);
                        if (TextUtils.isEmpty(d) || obj.equals(d)) {
                            if (TextUtils.isEmpty(d)) {
                                C3791b.b(context, obj);
                            }
                            DialogsActivity.this.menu.getItem(4).setIcon((int) R.drawable.ic_lock_open_outline);
                            C3791b.h(DialogsActivity.this.getParentActivity(), true);
                            dialog.dismiss();
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(-1)});
                            return;
                        }
                        editText.setError(LocaleController.getString("PasswordDoNotMatch", R.string.PasswordDoNotMatch));
                        editText.requestFocus();
                    }
                });
                dialog.findViewById(R.id.tvCancel).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                DialogsActivity.this.showDialog(dialog);
            }
        });
        handleHiddenDialogsVisibility();
        if (C2885i.a().intValue() != 0) {
            new Handler().postDelayed(new Runnable() {

                /* renamed from: org.telegram.ui.DialogsActivity$25$1 */
                class C47121 implements C2336c {
                    C47121() {
                    }

                    public void onHidePrompt(MotionEvent motionEvent, boolean z) {
                        C2885i.c(DialogsActivity.this.getParentActivity(), false);
                        DialogsActivity.this.menu.getItem(2).setIcon((int) R.drawable.ic_action_ghost_off);
                    }

                    public void onHidePromptComplete() {
                        DialogsActivity.this.showHotPostTutorial();
                    }
                }

                public void run() {
                    if (!C2885i.i(DialogsActivity.this.getParentActivity())) {
                        return;
                    }
                    if (DialogsActivity.this.menu == null || DialogsActivity.this.menu.getItem(2) == null || DialogsActivity.this.menu.getItem(2).getVisibility() != 0) {
                        DialogsActivity.this.showHotPostTutorial();
                    } else if (DialogsActivity.this.menu != null && DialogsActivity.this.menu.getItem(2) != null) {
                        DialogsActivity.this.menu.getItem(2).setIcon((int) R.drawable.ic_action_ghost_on);
                        new C2335b(DialogsActivity.this.getParentActivity()).a(DialogsActivity.this.menu.getItem(2)).a(8388613).a(C2872c.b(DialogsActivity.this.getParentActivity())).b(C2872c.c(DialogsActivity.this.getParentActivity())).a(DialogsActivity.this.getParentActivity().getString(R.string.ghos_mode)).b(DialogsActivity.this.getParentActivity().getString(R.string.ghos_mode_tutorial)).a(new C47121()).b();
                    }
                }
            }, 2000);
        }
        new Handler().postDelayed(new Runnable() {

            /* renamed from: org.telegram.ui.DialogsActivity$26$1 */
            class C47131 implements C2336c {
                C47131() {
                }

                public void onHidePrompt(MotionEvent motionEvent, boolean z) {
                    C2885i.e(DialogsActivity.this.getParentActivity(), false);
                    DialogsActivity.this.menu.getItem(9).setIcon((int) R.drawable.coins_act);
                }

                public void onHidePromptComplete() {
                }
            }

            public void run() {
                if (C2885i.k(ApplicationLoader.applicationContext) && DialogsActivity.this.menu != null && DialogsActivity.this.menu.getItem(9) != null && DialogsActivity.this.menu.getItem(9).getVisibility() == 0) {
                    DialogsActivity.this.menu.getItem(9).setIcon((int) R.drawable.coins_act);
                    new C2335b(DialogsActivity.this.getParentActivity()).a(DialogsActivity.this.menu.getItem(9)).a(8388613).a(C2872c.b(DialogsActivity.this.getParentActivity())).b(C2872c.c(DialogsActivity.this.getParentActivity())).a(LocaleController.getString("Advertise", R.string.Advertise)).b(C3791b.ad(ApplicationLoader.applicationContext)).a(new C47131()).b();
                }
            }
        }, 3000);
        this.menu.addItem(0, (int) R.drawable.ic_ab_search);
        this.menu.addItem(1, (int) R.drawable.ic_more).setOnClickListener(new OnClickListener() {

            /* renamed from: org.telegram.ui.DialogsActivity$27$1 */
            class C47141 implements C1032b {
                C47141() {
                }

                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() == 1) {
                        DialogsActivity.this.openBatchBottomSheet();
                    } else if (menuItem.getItemId() == 2) {
                        if (C2885i.b(ApplicationLoader.applicationContext) == 0) {
                            C2885i.a(DialogsActivity.this.getParentActivity(), 1);
                            Toast.makeText(DialogsActivity.this.getParentActivity().getApplicationContext(), R.string.ghost_mode_on, 0).show();
                        } else {
                            C2885i.a(DialogsActivity.this.getParentActivity(), 0);
                            Toast.makeText(DialogsActivity.this.getParentActivity().getApplicationContext(), R.string.ghost_mode_off, 0).show();
                        }
                        MessagesController.getInstance().updateTimerProc();
                    }
                    return false;
                }
            }

            public void onClick(View view) {
                boolean z = C2885i.b(ApplicationLoader.applicationContext) != 0;
                av avVar = new av(DialogsActivity.this.getParentActivity(), view);
                avVar.a().add(0, 1, 0, LocaleController.getString("MultiDialogsAction", R.string.MultiDialogsAction));
                boolean z2 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("showGhostMode", true);
                int O = C3791b.O(DialogsActivity.this.getParentActivity());
                if (z2 && O == 2) {
                    avVar.a().add(2, 2, 1, z ? LocaleController.getString("GhostModeOff", R.string.GhostModeOff) : LocaleController.getString("GhostModeOn", R.string.GhostModeOn));
                }
                avVar.b();
                avVar.a(new C47141());
            }
        });
        this.menu.getItem(0).setIsSearchField(true).setActionBarMenuItemSearchListener(new ActionBarMenuItemSearchListener() {

            /* renamed from: org.telegram.ui.DialogsActivity$28$1 */
            class C47151 implements Runnable {
                C47151() {
                }

                public void run() {
                    if (DialogsActivity.this.actionBar == null) {
                        return;
                    }
                    if (C3791b.z(DialogsActivity.this.getParentActivity()) == null || TextUtils.isEmpty(C3791b.z(DialogsActivity.this.getParentActivity()))) {
                        DialogsActivity.this.actionBar.setSubtitle(TtmlNode.ANONYMOUS_REGION_ID);
                    } else {
                        DialogsActivity.this.actionBar.setSubtitle(C3791b.z(DialogsActivity.this.getParentActivity()));
                    }
                }
            }

            public boolean canCollapseSearch() {
                if (DialogsActivity.this.searchString == null) {
                    return true;
                }
                DialogsActivity.this.finishFragment();
                return false;
            }

            public void onSearchCollapse() {
                DialogsActivity.this.searching = false;
                DialogsActivity.this.searchWas = false;
                DialogsActivity.this.menu.getItem(1).setVisibility(0);
                try {
                    if (C3791b.ab(ApplicationLoader.applicationContext) && C3791b.ac(ApplicationLoader.applicationContext)) {
                        DialogsActivity.this.menu.getItem(9).setVisibility(0);
                        if (DialogsActivity.this.listView != null) {
                            DialogsActivity.this.searchEmptyView.setVisibility(8);
                            if (MessagesController.getInstance().loadingDialogs) {
                            }
                            DialogsActivity.this.progressView.setVisibility(8);
                            DialogsActivity.this.listView.setEmptyView(DialogsActivity.this.emptyView);
                            if (!DialogsActivity.this.onlySelect) {
                                DialogsActivity.this.floatingButton.setVisibility(0);
                                DialogsActivity.this.floatingHidden = true;
                                DialogsActivity.this.floatingButton.setTranslationY((float) AndroidUtilities.dp(100.0f));
                                DialogsActivity.this.hideFloatingButton(false);
                            }
                            if (DialogsActivity.this.listView.getAdapter() != DialogsActivity.this.dialogsAdapter) {
                                DialogsActivity.this.listView.setAdapter(DialogsActivity.this.dialogsAdapter);
                                DialogsActivity.this.dialogsAdapter.notifyDataSetChanged();
                            }
                        }
                        if (DialogsActivity.this.dialogsSearchAdapter != null) {
                            DialogsActivity.this.dialogsSearchAdapter.searchDialogs(null);
                        }
                        DialogsActivity.this.updatePasscodeButton();
                        new Handler().postDelayed(new C47151(), 1);
                    }
                    DialogsActivity.this.menu.getItem(9).setVisibility(8);
                    if (DialogsActivity.this.listView != null) {
                        DialogsActivity.this.searchEmptyView.setVisibility(8);
                        if (MessagesController.getInstance().loadingDialogs || !MessagesController.getInstance().dialogs.isEmpty()) {
                            DialogsActivity.this.progressView.setVisibility(8);
                            DialogsActivity.this.listView.setEmptyView(DialogsActivity.this.emptyView);
                        } else {
                            DialogsActivity.this.emptyView.setVisibility(8);
                            DialogsActivity.this.listView.setEmptyView(DialogsActivity.this.progressView);
                        }
                        if (DialogsActivity.this.onlySelect) {
                            DialogsActivity.this.floatingButton.setVisibility(0);
                            DialogsActivity.this.floatingHidden = true;
                            DialogsActivity.this.floatingButton.setTranslationY((float) AndroidUtilities.dp(100.0f));
                            DialogsActivity.this.hideFloatingButton(false);
                        }
                        if (DialogsActivity.this.listView.getAdapter() != DialogsActivity.this.dialogsAdapter) {
                            DialogsActivity.this.listView.setAdapter(DialogsActivity.this.dialogsAdapter);
                            DialogsActivity.this.dialogsAdapter.notifyDataSetChanged();
                        }
                    }
                    if (DialogsActivity.this.dialogsSearchAdapter != null) {
                        DialogsActivity.this.dialogsSearchAdapter.searchDialogs(null);
                    }
                    DialogsActivity.this.updatePasscodeButton();
                    new Handler().postDelayed(new C47151(), 1);
                } catch (Exception e) {
                }
            }

            public void onSearchExpand() {
                DialogsActivity.this.searching = true;
                try {
                    DialogsActivity.this.menu.getItem(2).setVisibility(8);
                    DialogsActivity.this.menu.getItem(1).setVisibility(8);
                    DialogsActivity.this.menu.getItem(9).setVisibility(8);
                } catch (Exception e) {
                }
                if (DialogsActivity.this.listView != null) {
                    if (DialogsActivity.this.searchString != null) {
                        DialogsActivity.this.listView.setEmptyView(DialogsActivity.this.searchEmptyView);
                        DialogsActivity.this.progressView.setVisibility(8);
                        DialogsActivity.this.emptyView.setVisibility(8);
                    }
                    if (!DialogsActivity.this.onlySelect) {
                        DialogsActivity.this.floatingButton.setVisibility(8);
                    }
                }
                DialogsActivity.this.updatePasscodeButton();
            }

            public void onTextChanged(EditText editText) {
                String obj = editText.getText().toString();
                DialogsActivity.this.searchEmptyView.setSearchTerm(obj);
                if (obj.length() != 0 || (DialogsActivity.this.dialogsSearchAdapter != null && DialogsActivity.this.dialogsSearchAdapter.hasRecentRearch())) {
                    DialogsActivity.this.searchWas = true;
                    if (!(DialogsActivity.this.dialogsSearchAdapter == null || DialogsActivity.this.listView.getAdapter() == DialogsActivity.this.dialogsSearchAdapter)) {
                        DialogsActivity.this.listView.setAdapter(DialogsActivity.this.dialogsSearchAdapter);
                        DialogsActivity.this.dialogsSearchAdapter.notifyDataSetChanged();
                    }
                    if (!(DialogsActivity.this.searchEmptyView == null || DialogsActivity.this.listView.getEmptyView() == DialogsActivity.this.searchEmptyView)) {
                        DialogsActivity.this.emptyView.setVisibility(8);
                        DialogsActivity.this.progressView.setVisibility(8);
                        DialogsActivity.this.searchEmptyView.showTextView();
                        DialogsActivity.this.listView.setEmptyView(DialogsActivity.this.searchEmptyView);
                    }
                }
                if (DialogsActivity.this.dialogsSearchAdapter != null) {
                    DialogsActivity.this.dialogsSearchAdapter.searchDialogs(obj);
                }
            }
        }).getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        if (this.onlySelect) {
            this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
            this.actionBar.setTitle(LocaleController.getString("SelectChat", R.string.SelectChat));
        } else {
            if (this.searchString != null) {
                this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
            } else {
                this.actionBar.setBackButtonDrawable(new MenuDrawable());
            }
            this.actionBar.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
        }
        if (this.isSelectMode) {
            this.actionBar.setTitle(LocaleController.getString("SelectChats", R.string.SelectChats));
        }
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBarMenuOnItemClick() {
            public void onItemClick(int i) {
                boolean z = true;
                if (i == -1) {
                    if (DialogsActivity.this.onlySelect) {
                        DialogsActivity.this.finishFragment();
                    } else if (DialogsActivity.this.parentLayout != null) {
                        DialogsActivity.this.parentLayout.getDrawerLayoutContainer().openDrawer(false);
                    }
                } else if (i == 1) {
                    if (UserConfig.appLocked) {
                        z = false;
                    }
                    UserConfig.appLocked = z;
                    UserConfig.saveConfig(false);
                    DialogsActivity.this.updatePasscodeButton();
                }
            }
        });
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void addShortcut() {
        /*
        r12 = this;
        r5 = new android.content.Intent;
        r0 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r1 = org.telegram.customization.Activities.ShortcutActivity.class;
        r5.<init>(r0, r1);
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "com.tmessages.openchat";
        r0 = r0.append(r1);
        r2 = java.lang.Math.random();
        r0 = r0.append(r2);
        r1 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r0 = r0.append(r1);
        r0 = r0.toString();
        r5.setAction(r0);
        r0 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r5.setFlags(r0);
        r0 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r0.dialogs_dict;
        r2 = r12.selectedDialog;
        r1 = java.lang.Long.valueOf(r2);
        r0 = r0.get(r1);
        r0 = (org.telegram.tgnet.TLRPC$TL_dialog) r0;
        r1 = org.telegram.messenger.MessagesController.getInstance();
        r2 = r12.selectedDialog;
        r2 = -r2;
        r2 = (int) r2;
        r2 = java.lang.Integer.valueOf(r2);
        r4 = r1.getChat(r2);
        r1 = org.telegram.messenger.MessagesController.getInstance();
        r2 = r12.selectedDialog;
        r2 = (int) r2;
        r2 = java.lang.Integer.valueOf(r2);
        r3 = r1.getUser(r2);
        r2 = 0;
        r6 = new org.telegram.ui.Components.AvatarDrawable;
        r6.<init>();
        r0 = r0.id;
        r7 = (int) r0;
        r8 = 32;
        r0 = r0 >> r8;
        r8 = (int) r0;
        if (r7 == 0) goto L_0x00e2;
    L_0x0071:
        r0 = 1;
        if (r8 != r0) goto L_0x0094;
    L_0x0074:
        r0 = org.telegram.messenger.MessagesController.getInstance();
        r1 = java.lang.Integer.valueOf(r7);
        r0 = r0.getChat(r1);
        r1 = "chatId";
        r5.putExtra(r1, r7);
        r6.setInfo(r0);
        r1 = r3;
        r3 = r0;
        r0 = r2;
    L_0x008c:
        if (r3 == 0) goto L_0x0109;
    L_0x008e:
        r0 = r3.title;
        r2 = r0;
    L_0x0091:
        if (r2 != 0) goto L_0x013e;
    L_0x0093:
        return;
    L_0x0094:
        if (r7 >= 0) goto L_0x00c9;
    L_0x0096:
        r0 = org.telegram.messenger.MessagesController.getInstance();
        r1 = -r7;
        r1 = java.lang.Integer.valueOf(r1);
        r1 = r0.getChat(r1);
        if (r1 == 0) goto L_0x0245;
    L_0x00a5:
        r0 = r1.migrated_to;
        if (r0 == 0) goto L_0x0245;
    L_0x00a9:
        r0 = org.telegram.messenger.MessagesController.getInstance();
        r4 = r1.migrated_to;
        r4 = r4.channel_id;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r0.getChat(r4);
        if (r0 == 0) goto L_0x0245;
    L_0x00bb:
        r1 = "chatId";
        r4 = -r7;
        r5.putExtra(r1, r4);
        r6.setInfo(r0);
        r1 = r3;
        r3 = r0;
        r0 = r2;
        goto L_0x008c;
    L_0x00c9:
        r0 = org.telegram.messenger.MessagesController.getInstance();
        r1 = java.lang.Integer.valueOf(r7);
        r0 = r0.getUser(r1);
        r1 = "userId";
        r5.putExtra(r1, r7);
        r6.setInfo(r0);
        r1 = r0;
        r3 = r4;
        r0 = r2;
        goto L_0x008c;
    L_0x00e2:
        r0 = org.telegram.messenger.MessagesController.getInstance();
        r1 = java.lang.Integer.valueOf(r8);
        r0 = r0.getEncryptedChat(r1);
        if (r0 == 0) goto L_0x0241;
    L_0x00f0:
        r1 = org.telegram.messenger.MessagesController.getInstance();
        r2 = r0.user_id;
        r2 = java.lang.Integer.valueOf(r2);
        r1 = r1.getUser(r2);
        r2 = "encId";
        r5.putExtra(r2, r8);
        r6.setInfo(r1);
        r3 = r4;
        goto L_0x008c;
    L_0x0109:
        if (r1 == 0) goto L_0x0114;
    L_0x010b:
        if (r0 != 0) goto L_0x0114;
    L_0x010d:
        r0 = org.telegram.messenger.UserObject.getUserName(r1);
        r2 = r0;
        goto L_0x0091;
    L_0x0114:
        if (r0 == 0) goto L_0x013a;
    L_0x0116:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r2 = new java.lang.String;
        r4 = 128274; // 0x1f512 float:1.7975E-40 double:6.3376E-319;
        r4 = java.lang.Character.toChars(r4);
        r2.<init>(r4);
        r0 = r0.append(r2);
        r2 = org.telegram.messenger.UserObject.getUserName(r1);
        r0 = r0.append(r2);
        r0 = r0.toString();
        r2 = r0;
        goto L_0x0091;
    L_0x013a:
        r0 = 0;
        r2 = r0;
        goto L_0x0091;
    L_0x013e:
        r0 = 0;
        if (r3 == 0) goto L_0x01d9;
    L_0x0141:
        r1 = r3.photo;
        if (r1 == 0) goto L_0x023e;
    L_0x0145:
        r1 = r3.photo;
        r1 = r1.photo_small;
        if (r1 == 0) goto L_0x023e;
    L_0x014b:
        r1 = r3.photo;
        r1 = r1.photo_small;
        r8 = r1.volume_id;
        r10 = 0;
        r1 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r1 == 0) goto L_0x023e;
    L_0x0157:
        r1 = r3.photo;
        r1 = r1.photo_small;
        r1 = r1.local_id;
        if (r1 == 0) goto L_0x023e;
    L_0x015f:
        r0 = r3.photo;
        r0 = r0.photo_small;
        r1 = r0;
    L_0x0164:
        r0 = 0;
        if (r1 == 0) goto L_0x0173;
    L_0x0167:
        r0 = org.telegram.messenger.ImageLoader.getInstance();
        r3 = 0;
        r4 = "50_50";
        r0 = r0.getImageFromMemory(r1, r3, r4);
    L_0x0173:
        r1 = "com.android.launcher.action.INSTALL_SHORTCUT";
        r3 = new android.content.Intent;
        r3.<init>();
        r4 = "android.intent.extra.shortcut.INTENT";
        r3.putExtra(r4, r5);
        r4 = "android.intent.extra.shortcut.NAME";
        r3.putExtra(r4, r2);
        if (r0 == 0) goto L_0x0200;
    L_0x0189:
        r4 = "android.intent.extra.shortcut.ICON";
        r0 = r0.getBitmap();
        r0 = r12.getRoundBitmap(r0);
        r3.putExtra(r4, r0);
    L_0x0197:
        r0 = "duplicate";
        r4 = 0;
        r3.putExtra(r0, r4);
        r3.setAction(r1);
        r0 = 0;
        r4 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r4 = r4.getPackageManager();
        r5 = new android.content.Intent;
        r5.<init>(r1);
        r1 = 0;
        r1 = r4.queryBroadcastReceivers(r5, r1);
        r1 = r1.size();
        if (r1 <= 0) goto L_0x022b;
    L_0x01b8:
        r1 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r1.sendBroadcast(r3);
    L_0x01bd:
        if (r0 == 0) goto L_0x022d;
    L_0x01bf:
        r0 = "ShortcutError";
        r1 = 2131233250; // 0x7f0809e2 float:1.8082632E38 double:1.052969132E-314;
        r3 = 1;
        r3 = new java.lang.Object[r3];
        r4 = 0;
        r3[r4] = r2;
        r0 = org.telegram.messenger.LocaleController.formatString(r0, r1, r3);
    L_0x01cf:
        r1 = new org.telegram.ui.DialogsActivity$39;
        r1.<init>(r0);
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r1);
        goto L_0x0093;
    L_0x01d9:
        if (r1 == 0) goto L_0x023e;
    L_0x01db:
        r3 = r1.photo;
        if (r3 == 0) goto L_0x023e;
    L_0x01df:
        r3 = r1.photo;
        r3 = r3.photo_small;
        if (r3 == 0) goto L_0x023e;
    L_0x01e5:
        r3 = r1.photo;
        r3 = r3.photo_small;
        r8 = r3.volume_id;
        r10 = 0;
        r3 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r3 == 0) goto L_0x023e;
    L_0x01f1:
        r3 = r1.photo;
        r3 = r3.photo_small;
        r3 = r3.local_id;
        if (r3 == 0) goto L_0x023e;
    L_0x01f9:
        r0 = r1.photo;
        r0 = r0.photo_small;
        r1 = r0;
        goto L_0x0164;
    L_0x0200:
        r0 = 1109393408; // 0x42200000 float:40.0 double:5.481131706E-315;
        r0 = org.telegram.messenger.AndroidUtilities.dp(r0);
        r4 = 1109393408; // 0x42200000 float:40.0 double:5.481131706E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r5 = android.graphics.Bitmap.Config.ARGB_8888;
        r5 = android.graphics.Bitmap.createBitmap(r0, r4, r5);
        r7 = new android.graphics.Canvas;
        r7.<init>(r5);
        r8 = 0;
        r9 = 0;
        r6.setBounds(r8, r9, r0, r4);
        r6.draw(r7);
        r0 = "android.intent.extra.shortcut.ICON";
        r4 = r12.getRoundBitmap(r5);
        r3.putExtra(r0, r4);
        goto L_0x0197;
    L_0x022b:
        r0 = 1;
        goto L_0x01bd;
    L_0x022d:
        r0 = "ShortcutAdded";
        r1 = 2131232419; // 0x7f0806a3 float:1.8080947E38 double:1.0529687215E-314;
        r3 = 1;
        r3 = new java.lang.Object[r3];
        r4 = 0;
        r3[r4] = r2;
        r0 = org.telegram.messenger.LocaleController.formatString(r0, r1, r3);
        goto L_0x01cf;
    L_0x023e:
        r1 = r0;
        goto L_0x0164;
    L_0x0241:
        r1 = r3;
        r3 = r4;
        goto L_0x008c;
    L_0x0245:
        r0 = r1;
        goto L_0x00bb;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.DialogsActivity.addShortcut():void");
    }

    private void addTabView(Context context, ImageView imageView, TextView textView, boolean z) {
        imageView.setScaleType(ScaleType.CENTER);
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius((float) AndroidUtilities.dp(32.0f));
        if (textView != null) {
            textView.setBackgroundDrawable(gradientDrawable);
        }
        View relativeLayout = new RelativeLayout(context);
        relativeLayout.addView(imageView, LayoutHelper.createRelative(-1, -1));
        if (z) {
            try {
                this.tabsLayout.addView(relativeLayout, LayoutHelper.createLinear(0, -1, 1.0f));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(23)
    private void askForPermissons() {
        Activity parentActivity = getParentActivity();
        if (parentActivity != null) {
            ArrayList arrayList = new ArrayList();
            if (parentActivity.checkSelfPermission("android.permission.READ_CONTACTS") != 0) {
                arrayList.add("android.permission.READ_CONTACTS");
                arrayList.add("android.permission.WRITE_CONTACTS");
                arrayList.add("android.permission.GET_ACCOUNTS");
            }
            if (parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
                arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
            }
            try {
                parentActivity.requestPermissions((String[]) arrayList.toArray(new String[arrayList.size()]), 1);
            } catch (Exception e) {
            }
        }
    }

    private void createTabs(final Context context) {
        int i = this.forceRefreshTabs ? this.dialogsType : -1;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        Editor edit = sharedPreferences.edit();
        boolean z = sharedPreferences.getBoolean("hideUsers", false);
        boolean z2 = sharedPreferences.getBoolean("hideGroups", false);
        boolean z3 = sharedPreferences.getBoolean("hideSGroups", false);
        boolean z4 = sharedPreferences.getBoolean("hideChannels", false);
        boolean z5 = sharedPreferences.getBoolean("hideBots", false);
        boolean z6 = sharedPreferences.getBoolean("hideFavs", false);
        boolean z7 = sharedPreferences.getBoolean("hideUnread", false);
        this.hideTabs = sharedPreferences.getBoolean("hideTabs", false);
        this.disableAnimation = sharedPreferences.getBoolean("disableTabsAnimation", false);
        if (z && z2 && z3 && z4 && z5 && z6 && z7 && !this.hideTabs) {
            this.hideTabs = true;
            edit.putBoolean("hideTabs", true).apply();
        }
        this.tabsHeight = sharedPreferences.getInt("tabsHeight", 40);
        refreshTabAndListViews(false);
        int i2 = sharedPreferences.getInt("defTab", -1);
        if (i2 == -1) {
            i2 = sharedPreferences.getInt("selTab", 0);
        }
        this.selectedTab = i2;
        if (!(this.hideTabs || this.dialogsType == this.selectedTab)) {
            i2 = (this.selectedTab == 4 && z3) ? 9 : this.selectedTab;
            this.dialogsType = i2;
            this.dialogsAdapter = new DialogsAdapter(context, this.dialogsType);
            this.listView.setAdapter(this.dialogsAdapter);
            this.dialogsAdapter.notifyDataSetChanged();
        }
        this.dialogsBackupAdapter = new DialogsAdapter(context, 0);
        this.tabsLayout = new LinearLayout(context);
        this.tabsLayout.setOrientation(0);
        this.tabsLayout.setGravity(17);
        this.slidingTabLayout = new SlidingTabLayout(context);
        LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        this.tabsLayout.setLayoutParams(layoutParams);
        this.slidingTabLayout.setLayoutParams(layoutParams);
        this.slidingTabLayout.m14184a(R.layout.sls_tab_item, R.id.ftv_msg_count);
        this.slidingTabLayout.setCustomTabColorizer(new TabColorizer() {
            public int getIndicatorColor(int i) {
                return context.getResources().getColor(R.color.white);
            }
        });
        this.slidingTabLayout.setViewPager(this.viewPager);
        try {
            this.tabLayout = new C0192t(getParentActivity());
            this.tabLayout.setLayoutParams(layoutParams);
            this.tabLayout.setSelectedTabIndicatorColor(ApplicationLoader.applicationContext.getResources().getColor(R.color.white));
            this.tabsLayout.removeAllViews();
            this.tabsView.removeAllViews();
            this.tabsView.addView(this.tabLayout);
            this.tabLayout.b();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.tabsView.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        Object v = C3791b.v(getParentActivity());
        Collections.reverse(v);
        Iterator it = v.iterator();
        while (it.hasNext()) {
            final DialogTab dialogTab = (DialogTab) it.next();
            C0187e a = this.tabLayout.a();
            a.a(dialogTab.getTabLayoutResource());
            a.a(dialogTab.getTag());
            if (!dialogTab.isHidden()) {
                this.tabLayout.a(a);
            }
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(dialogTab.getTabDrawable());
            TextView textView = (TextView) a.b().findViewById(R.id.slsTvCounter);
            textView.setTag(dialogTab.getTag());
            addTabView(context, imageView, textView, !dialogTab.isHidden());
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (DialogsActivity.this.dialogsType != dialogTab.getDialogType()) {
                        DialogsActivity.this.dialogsType = dialogTab.getDialogType();
                        DialogsActivity.this.refreshAdapter(context);
                        C3792d.b(dialogTab.getTag());
                    }
                }
            });
            imageView.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View view) {
                    CharSequence string;
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DialogsActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("Favorites", R.string.Favorites));
                    SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    final int i = sharedPreferences.getInt("sortFavs", 0);
                    final int i2 = sharedPreferences.getInt("defTab", -1);
                    if (i2 == 8) {
                        string = LocaleController.getString("ResetDefaultTab", R.string.ResetDefaultTab);
                    } else {
                        Object string2 = LocaleController.getString("SetAsDefaultTab", R.string.SetAsDefaultTab);
                    }
                    String string3 = i == 0 ? LocaleController.getString("SortByUnreadCount", R.string.SortByUnreadCount) : LocaleController.getString("SortByLastMessage", R.string.SortByLastMessage);
                    builder.setItems(new CharSequence[]{string3, string, LocaleController.getString("MarkAllAsRead", R.string.MarkAllAsRead)}, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int i2 = 8;
                            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                            if (i == 1) {
                                String str = "defTab";
                                if (i2 == 8) {
                                    i2 = -1;
                                }
                                edit.putInt(str, i2).apply();
                            } else if (i == 0) {
                                edit.putInt("sortFavs", i == 0 ? 1 : 0).apply();
                                if (DialogsActivity.this.dialogsAdapter.getItemCount() > 1) {
                                    DialogsActivity.this.dialogsAdapter.notifyDataSetChanged();
                                }
                            } else if (i == 2) {
                                DialogsActivity.this.markAsReadDialog(true);
                            }
                        }
                    });
                    DialogsActivity.this.showDialog(builder.create());
                    return true;
                }
            });
        }
        this.tabLayout.setOnTabSelectedListener(new C0182b() {
            public void onTabReselected(C0187e c0187e) {
            }

            public void onTabSelected(C0187e c0187e) {
                int i = 9;
                if (c0187e != null && c0187e.a() != null) {
                    if (c0187e.a().equals("TAB_ALL")) {
                        i = 0;
                    } else if (c0187e.a().equals("TAB_FAVES")) {
                        i = 8;
                    } else if (c0187e.a().equals("TAB_GROUPS")) {
                        if (!ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("hideSGroups", false)) {
                            i = 4;
                        }
                    } else if (!c0187e.a().equals("TAB_SGROUP")) {
                        i = c0187e.a().equals("TAB_BOTS") ? 6 : c0187e.a().equals("TAB_USERS") ? 3 : c0187e.a().equals("TAB_CHANNELS") ? 5 : c0187e.a().equals("TAB_LOCK") ? 10 : c0187e.a().equals("TAB_UNREAD_CHATS") ? 11 : c0187e.a().equals("TAB_ADS") ? 12 : 0;
                    } else if (!ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("hideGroups", false)) {
                        i = 7;
                    }
                    if (DialogsActivity.this.dialogsType != i) {
                        DialogsActivity.this.dialogsType = i;
                        DialogsActivity.this.refreshAdapter(context);
                    }
                }
            }

            public void onTabUnselected(C0187e c0187e) {
            }
        });
        if (this.forceRefreshTabs) {
            this.forceRefreshTabs = false;
            this.dialogsType = i;
            refreshAdapter(context);
        }
        refreshAdapter(context);
    }

    private void didSelectResult(final long j, boolean z, boolean z2) {
        if (this.addToGroupAlertString == null && ((int) j) < 0) {
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) j)));
            if (ChatObject.isChannel(chat) && !chat.megagroup && (this.cantSendToChannels || !ChatObject.isCanWriteToChannel(-((int) j)))) {
                Builder builder = new Builder(getParentActivity());
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setMessage(LocaleController.getString("ChannelCantSendMessage", R.string.ChannelCantSendMessage));
                builder.setNegativeButton(LocaleController.getString("OK", R.string.OK), null);
                showDialog(builder.create());
                return;
            }
        }
        if (!z || ((this.selectAlertString == null || this.selectAlertStringGroup == null) && this.addToGroupAlertString == null)) {
            if (this.delegate != null) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(Long.valueOf(j));
                this.delegate.didSelectDialogs(this, arrayList, null, z2);
                this.delegate = null;
                return;
            }
            finishFragment();
        } else if (getParentActivity() != null) {
            builder = new Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            int i = (int) j;
            int i2 = (int) (j >> 32);
            if (i == 0) {
                if (MessagesController.getInstance().getUser(Integer.valueOf(MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i2)).user_id)) != null) {
                    builder.setMessage(LocaleController.formatStringSimple(this.selectAlertString, new Object[]{UserObject.getUserName(r1)}));
                } else {
                    return;
                }
            } else if (i2 == 1) {
                if (MessagesController.getInstance().getChat(Integer.valueOf(i)) != null) {
                    builder.setMessage(LocaleController.formatStringSimple(this.selectAlertStringGroup, new Object[]{r1.title}));
                } else {
                    return;
                }
            } else if (i == UserConfig.getClientUserId()) {
                builder.setMessage(LocaleController.formatStringSimple(this.selectAlertStringGroup, new Object[]{LocaleController.getString("SavedMessages", R.string.SavedMessages)}));
            } else if (i > 0) {
                if (MessagesController.getInstance().getUser(Integer.valueOf(i)) != null) {
                    builder.setMessage(LocaleController.formatStringSimple(this.selectAlertString, new Object[]{UserObject.getUserName(r1)}));
                } else {
                    return;
                }
            } else if (i < 0) {
                if (MessagesController.getInstance().getChat(Integer.valueOf(-i)) == null) {
                    return;
                }
                if (this.addToGroupAlertString != null) {
                    builder.setMessage(LocaleController.formatStringSimple(this.addToGroupAlertString, new Object[]{r1.title}));
                } else {
                    builder.setMessage(LocaleController.formatStringSimple(this.selectAlertStringGroup, new Object[]{r1.title}));
                }
            }
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    DialogsActivity.this.didSelectResult(j, false, false);
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            showDialog(builder.create());
        }
    }

    private String getActionMessage() {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        switch (this.actionTypeForSelectMode) {
            case 1:
                return String.format(LocaleController.getString("action_message_formatter", R.string.action_message_formatter), new Object[]{LocaleController.getString("RemoveChat", R.string.RemoveChat)});
            case 2:
                return String.format(LocaleController.getString("action_message_formatter", R.string.action_message_formatter), new Object[]{LocaleController.getString("AddToFav", R.string.AddToFav)});
            case 3:
                return String.format(LocaleController.getString("action_message_formatter", R.string.action_message_formatter), new Object[]{LocaleController.getString("RemoveFromFav", R.string.RemoveFromFav)});
            case 4:
                return String.format(LocaleController.getString("action_message_formatter", R.string.action_message_formatter), new Object[]{LocaleController.getString("Hide", R.string.Hide)});
            case 5:
                return String.format(LocaleController.getString("action_message_formatter", R.string.action_message_formatter), new Object[]{LocaleController.getString("ChannelMute", R.string.ChannelMute)});
            case 6:
                return String.format(LocaleController.getString("action_message_formatter", R.string.action_message_formatter), new Object[]{LocaleController.getString("ChannelUnmute", R.string.ChannelUnmute)});
            case 7:
                return String.format(LocaleController.getString("action_message_formatter", R.string.action_message_formatter), new Object[]{LocaleController.getString("markAsRead", R.string.markAsRead)});
            default:
                return str;
        }
    }

    private ArrayList<TLRPC$TL_dialog> getDialogsArray() {
        return this.dialogsType == 0 ? MessagesController.getInstance().dialogsAll : this.dialogsType == 1 ? MessagesController.getInstance().dialogsServerOnly : this.dialogsType == 2 ? MessagesController.getInstance().dialogsGroupsOnly : this.dialogsType == 13 ? MessagesController.getInstance().dialogsForward : this.dialogsType == 3 ? MessagesController.getInstance().dialogsUsers : this.dialogsType == 4 ? MessagesController.getInstance().dialogsGroups : this.dialogsType == 5 ? MessagesController.getInstance().dialogsChannels : this.dialogsType == 6 ? MessagesController.getInstance().dialogsBots : this.dialogsType == 7 ? MessagesController.getInstance().dialogsMegaGroups : this.dialogsType == 8 ? MessagesController.getInstance().dialogsFavs : this.dialogsType == 9 ? MessagesController.getInstance().dialogsGroupsAll : this.dialogsType == 10 ? MessagesController.getInstance().dialogsHidden : this.dialogsType == 11 ? MessagesController.getInstance().dialogsUnread : this.dialogsType == 12 ? MessagesController.getInstance().dialogsAds : null;
    }

    private String getHeaderAllTitles() {
        switch (this.dialogsType) {
            case 3:
                return LocaleController.getString("Users", R.string.Users);
            case 4:
            case 9:
                return LocaleController.getString("Groups", R.string.Groups);
            case 5:
                return LocaleController.getString("Channels", R.string.Channels);
            case 6:
                return LocaleController.getString("Bots", R.string.Bots);
            case 7:
                return LocaleController.getString("SuperGroups", R.string.SuperGroups);
            case 8:
                return LocaleController.getString("Favorites", R.string.Favorites);
            case 10:
                return LocaleController.getString("lockedChats", R.string.lockedChats);
            case 11:
                return LocaleController.getString("unreadChats", R.string.unreadChats);
            case 12:
                return LocaleController.getString("Advertise", R.string.Advertise);
            default:
                return getHeaderTitle();
        }
    }

    private String getHeaderTitle() {
        return LocaleController.getString("MyAppName", R.string.MyAppName);
    }

    private Bitmap getRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int min = Math.min(height / 2, width / 2);
        Bitmap createBitmap = Bitmap.createBitmap(width + 8, height + 8, Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setStyle(Style.FILL);
        canvas.drawCircle((float) ((width / 2) + 4), (float) ((height / 2) + 4), (float) min, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 4.0f, 4.0f, paint);
        return createBitmap;
    }

    private void goToSelectModeByActionType(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("dialogsType", 0);
        bundle.putInt("actionType", i);
        bundle.putBoolean("isSelectMode", true);
        BaseFragment dialogsActivity = new DialogsActivity(bundle);
        dialogsActivity.setResponseReceiver(new C2497d() {
            public void onResult(Object obj, int i) {
                DialogsActivity.this.handleHiddenDialogsVisibility();
                MessagesController.getInstance().updateDialogsForAddOrRemoveDialog();
                DialogsActivity.this.refreshAdapter(DialogsActivity.this.getParentActivity());
            }
        });
        presentFragment(dialogsActivity);
    }

    private void handleHiddenDialogsVisibility() {
        try {
            if (this.menu != null && this.menu.getItem(4) != null) {
                if (C3791b.b(getParentActivity()).size() > 0) {
                    this.menu.getItem(4).setVisibility(0);
                } else {
                    this.menu.getItem(4).setVisibility(8);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideActionbarItems() {
        if (this.actionBar != null) {
            this.actionBar.createMenu().clearItems();
            this.actionBar.getTitleTextView().setGravity(5);
        }
    }

    private void hideFloatingButton(boolean z) {
        if (System.currentTimeMillis() - this.lastAnimUpdate >= 300) {
            this.lastAnimUpdate = System.currentTimeMillis();
            if (this.floatingHidden != z) {
                this.floatingHidden = z;
                ImageView imageView = this.floatingButton;
                String str = "translationY";
                float[] fArr = new float[1];
                fArr[0] = this.floatingHidden ? (float) AndroidUtilities.dp(100.0f) : BitmapDescriptorFactory.HUE_RED;
                ObjectAnimator duration = ObjectAnimator.ofFloat(imageView, str, fArr).setDuration(300);
                duration.setInterpolator(this.floatingInterpolator);
                this.floatingButton.setClickable(!z);
                duration.start();
            }
        }
    }

    private void hideShowTabs(int i) {
        createTabs(ApplicationLoader.applicationContext);
    }

    private void hideTabsAnimated(boolean z) {
        if (this.tabsHidden == z) {
        }
    }

    private void lockChat(Context context, boolean z, long j) {
        int i = 0;
        int i2 = (int) j;
        int i3 = (int) (j >> 32);
        if (i2 == 0) {
            i = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i3)).user_id;
        } else if (i3 != 1) {
            if (i2 > 0) {
                i = i2;
            } else if (i2 < 0) {
            }
        }
        Log.d("alireza", "alireza user id locking 0" + i);
        if (TextUtils.isEmpty(C3791b.d(context))) {
            final boolean z2 = z;
            final Context context2 = context;
            final long j2 = j;
            showPassLockDialog(context, true, new OnClickListener() {
                public void onClick(View view) {
                    if (z2) {
                        C3791b.b(context2, j2);
                        C3791b.e(context2, (long) i);
                    } else {
                        C3791b.a(context2, j2);
                        C3791b.d(context2, (long) i);
                    }
                    DialogsActivity.this.handleHiddenDialogsVisibility();
                    MessagesController.getInstance().updateDialogsForAddOrRemoveDialog();
                    DialogsActivity.this.refreshAdapter(context2);
                }
            }, -1);
            return;
        }
        if (z) {
            C3791b.b(context, j);
            C3791b.e(context, (long) i);
        } else {
            C3791b.a(context, j);
            C3791b.d(context, (long) i);
        }
        handleHiddenDialogsVisibility();
        MessagesController.getInstance().updateDialogsForAddOrRemoveDialog();
        refreshAdapter(context);
    }

    private void markAsReadDialog(final boolean z) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getParentActivity());
        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf((int) (-this.selectedDialog)));
        User user = MessagesController.getInstance().getUser(Integer.valueOf((int) this.selectedDialog));
        CharSequence userName = chat != null ? chat.title : user != null ? UserObject.getUserName(user) : LocaleController.getString("MyAppName", R.string.MyAppName);
        if (z) {
            userName = getHeaderAllTitles();
        }
        builder.setTitle(userName);
        builder.setMessage((z ? LocaleController.getString("MarkAllAsRead", R.string.MarkAllAsRead) : LocaleController.getString("MarkAsRead", R.string.MarkAsRead)) + '\n' + LocaleController.getString("AreYouSure", R.string.AreYouSure));
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                TLRPC$TL_dialog tLRPC$TL_dialog;
                if (z) {
                    ArrayList access$1900 = DialogsActivity.this.getDialogsArray();
                    if (access$1900 != null && !access$1900.isEmpty()) {
                        for (int i2 = 0; i2 < access$1900.size(); i2++) {
                            tLRPC$TL_dialog = (TLRPC$TL_dialog) DialogsActivity.this.getDialogsArray().get(i2);
                            if (tLRPC$TL_dialog.unread_count > 0) {
                                MessagesController.getInstance().markDialogAsRead(tLRPC$TL_dialog.id, 0, Math.max(0, tLRPC$TL_dialog.top_message), tLRPC$TL_dialog.last_message_date, true, false);
                            }
                        }
                        return;
                    }
                    return;
                }
                tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(DialogsActivity.this.selectedDialog));
                if (tLRPC$TL_dialog.unread_count > 0) {
                    MessagesController.getInstance().markDialogAsRead(tLRPC$TL_dialog.id, 0, Math.max(0, tLRPC$TL_dialog.top_message), tLRPC$TL_dialog.last_message_date, true, false);
                }
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
    }

    private void openBatchBottomSheet() {
        BottomSheet.Builder builder = new BottomSheet.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString("MultiDialogsAction", R.string.MultiDialogsAction));
        builder.setItems(new CharSequence[]{LocaleController.getString("deleteDialogs", R.string.deleteDialogs), LocaleController.getString("addDialogsToFave", R.string.addDialogsToFave), LocaleController.getString("deleteDialogsFromFave", R.string.deleteDialogsFromFave), LocaleController.getString("addDialogsToHidden", R.string.addDialogsToHidden), LocaleController.getString("addDialogsToMute", R.string.addDialogsToMute), LocaleController.getString("removeDialogsFromMute", R.string.removeDialogsFromMute), LocaleController.getString("markAsRead", R.string.markAsRead)}, new int[]{R.drawable.ic_delete_white_24dp, R.drawable.ic_heart_white_24dp, R.drawable.ic_heart_off_white_24dp, R.drawable.ic_eye_off_white_24dp, R.drawable.ic_volume_mute_white_24dp, R.drawable.notifications_s_on, R.drawable.ic_checkbox_multiple_marked_white_24dp}, new DialogInterface.OnClickListener() {

            /* renamed from: org.telegram.ui.DialogsActivity$32$1 */
            class C47181 implements OnClickListener {
                C47181() {
                }

                public void onClick(View view) {
                    DialogsActivity.this.goToSelectModeByActionType(4);
                }
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        DialogsActivity.this.goToSelectModeByActionType(1);
                        return;
                    case 1:
                        DialogsActivity.this.goToSelectModeByActionType(2);
                        return;
                    case 2:
                        DialogsActivity.this.goToSelectModeByActionType(3);
                        return;
                    case 3:
                        if (TextUtils.isEmpty(C3791b.d(DialogsActivity.this.getParentActivity()))) {
                            DialogsActivity.this.showPassLockDialog(DialogsActivity.this.getParentActivity(), true, new C47181(), -1);
                            return;
                        } else {
                            DialogsActivity.this.goToSelectModeByActionType(4);
                            return;
                        }
                    case 4:
                        DialogsActivity.this.goToSelectModeByActionType(5);
                        return;
                    case 5:
                        DialogsActivity.this.goToSelectModeByActionType(6);
                        return;
                    case 6:
                        DialogsActivity.this.goToSelectModeByActionType(7);
                        return;
                    default:
                        return;
                }
            }
        });
        showDialog(builder.create());
    }

    private void paintHeader(boolean z) {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0);
        this.actionBar.setTitleColor(sharedPreferences.getInt("chatsHeaderTitleColor", -1));
        int i = sharedPreferences.getInt("themeColor", C2872c.f9484b);
        int i2 = sharedPreferences.getInt("chatsHeaderColor", i);
        if (!z) {
            this.actionBar.setBackgroundColor(i2);
        }
        if (z) {
            this.tabsView.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        }
        int i3 = sharedPreferences.getInt("chatsHeaderGradient", 0);
        if (i3 > 0) {
            Orientation orientation;
            switch (i3) {
                case 2:
                    orientation = Orientation.LEFT_RIGHT;
                    break;
                case 3:
                    orientation = Orientation.TL_BR;
                    break;
                case 4:
                    orientation = Orientation.BL_TR;
                    break;
                default:
                    orientation = Orientation.TOP_BOTTOM;
                    break;
            }
            int i4 = sharedPreferences.getInt("chatsHeaderGradientColor", i);
            Drawable gradientDrawable = new GradientDrawable(orientation, new int[]{i2, i4});
            if (!z) {
                this.actionBar.setBackgroundDrawable(gradientDrawable);
            }
            if (z) {
                this.tabsView.setBackgroundDrawable(gradientDrawable);
            }
        }
    }

    private void refreshAdapter(Context context) {
        refreshAdapterAndTabs(new DialogsAdapter(context, this.dialogsType));
    }

    private void refreshAdapterAndTabs(DialogsAdapter dialogsAdapter) {
        this.dialogsAdapter = dialogsAdapter;
        this.listView.setAdapter(this.dialogsAdapter);
        try {
            this.dialogsAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!this.onlySelect) {
            this.selectedTab = this.dialogsType == 9 ? 4 : this.dialogsType;
            ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit().putInt("selTab", this.selectedTab).apply();
        }
        refreshTabs();
    }

    private void refreshDialogType(int i) {
        int i2 = 0;
        if (!this.hideTabs) {
            C0187e a;
            boolean z = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("infiniteTabsSwipe", false);
            int i3 = this.dialogsType;
            ArrayList v = C3791b.v(ApplicationLoader.applicationContext);
            for (int i4 = 0; i4 < v.size(); i4++) {
                if (((DialogTab) v.get(i4)).getDialogType() == this.dialogsType) {
                    if (i == 1) {
                        if (i4 + 1 < v.size()) {
                            this.dialogsType = ((DialogTab) v.get(i4 + 1)).getDialogType();
                        } else if (z) {
                            this.dialogsType = ((DialogTab) v.get(0)).getDialogType();
                            refreshAdapter(ApplicationLoader.applicationContext);
                            return;
                        } else {
                            return;
                        }
                    } else if (i4 - 1 >= 0) {
                        this.dialogsType = ((DialogTab) v.get(i4 - 1)).getDialogType();
                    } else if (z) {
                        this.dialogsType = ((DialogTab) v.get(v.size() - 1)).getDialogType();
                        refreshAdapter(ApplicationLoader.applicationContext);
                        return;
                    } else {
                        return;
                    }
                    if (this.dialogsType != 10) {
                        this.dialogsType = i3;
                        while (i2 < this.tabLayout.getTabCount()) {
                            a = this.tabLayout.a(i2);
                            if (a == null && a.a() != null && a.a().equals("TAB_LOCK")) {
                                a.f();
                                return;
                            }
                            i2++;
                        }
                    }
                    refreshAdapter(ApplicationLoader.applicationContext);
                    return;
                }
            }
            if (this.dialogsType != 10) {
                refreshAdapter(ApplicationLoader.applicationContext);
                return;
            }
            this.dialogsType = i3;
            while (i2 < this.tabLayout.getTabCount()) {
                a = this.tabLayout.a(i2);
                if (a == null) {
                }
                i2++;
            }
        }
    }

    private void refreshTabAndListViews(boolean z) {
        int i = this.bottomBarHeight;
        try {
            i = this.bottomBar.getHeight();
        } catch (Exception e) {
        }
        if (this.tabsView != null) {
            if (this.hideTabs || z) {
                this.tabsView.setVisibility(8);
                this.listView.setPadding(0, 0, 0, i);
            } else {
                this.tabsView.setVisibility(0);
                int dp = AndroidUtilities.dp((float) this.tabsHeight);
                LayoutParams layoutParams = this.tabsView.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.height = dp;
                    this.tabsView.setLayoutParams(layoutParams);
                }
                this.listView.setPadding(0, dp, 0, i);
                hideTabsAnimated(false);
            }
            this.listView.scrollToPosition(0);
        }
    }

    private void refreshTabs() {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0);
        ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.tab_selected).setColorFilter(sharedPreferences.getInt("chatsHeaderTabIconColor", sharedPreferences.getInt("chatsHeaderIconsColor", -1)), Mode.SRC_IN);
        for (int i = 0; i < this.tabLayout.getTabCount(); i++) {
            C0187e a = this.tabLayout.a(i);
            switch (this.dialogsType == 9 ? 4 : this.dialogsType) {
                case 3:
                    if (!(a == null || a.a() == null || !a.a().equals("TAB_USERS"))) {
                        a.f();
                        break;
                    }
                case 4:
                    if (!(a == null || a.a() == null || !a.a().equals("TAB_GROUPS"))) {
                        a.f();
                        break;
                    }
                case 5:
                    if (!(a == null || a.a() == null || !a.a().equals("TAB_CHANNELS"))) {
                        a.f();
                        break;
                    }
                case 6:
                    if (!(a == null || a.a() == null || !a.a().equals("TAB_BOTS"))) {
                        a.f();
                        break;
                    }
                case 7:
                    if (!(a == null || a.a() == null || !a.a().equals("TAB_SGROUP"))) {
                        a.f();
                        break;
                    }
                case 8:
                    if (!(a == null || a.a() == null || !a.a().equals("TAB_FAVES"))) {
                        a.f();
                        break;
                    }
                case 10:
                    if (!(a == null || a.a() == null || !a.a().equals("TAB_LOCK"))) {
                        a.f();
                        break;
                    }
                case 11:
                    if (!(a == null || a.a() == null || !a.a().equals("TAB_UNREAD_CHATS"))) {
                        a.f();
                        break;
                    }
                case 12:
                    if (!(a == null || a.a() == null || !a.a().equals("TAB_ADS"))) {
                        a.f();
                        break;
                    }
                default:
                    if (!(a == null || a.a() == null || !a.a().equals("TAB_ALL"))) {
                        a.f();
                        break;
                    }
            }
        }
        CharSequence headerAllTitles = getHeaderAllTitles();
        if (this.isSelectMode) {
            this.actionBar.setTitle(LocaleController.getString("SelectChats", R.string.SelectChats));
        } else {
            this.actionBar.setTitle(headerAllTitles);
        }
        if (!TextUtils.isEmpty(C3791b.z(ApplicationLoader.applicationContext))) {
            this.actionBar.setSubtitle(C3791b.z(ApplicationLoader.applicationContext));
        }
        paintHeader(true);
        if (getDialogsArray() != null && getDialogsArray().isEmpty()) {
            this.searchEmptyView.setVisibility(8);
            this.progressView.setVisibility(8);
            if (this.emptyView.getChildCount() > 0) {
                TextView textView = (TextView) this.emptyView.getChildAt(0);
                textView.setPadding(20, 0, 20, 0);
                textView.setTypeface(C2872c.c(ApplicationLoader.applicationContext));
                if (textView != null) {
                    if (this.dialogsType < 3 || this.dialogsType == 11) {
                        headerAllTitles = LocaleController.getString("NoChats", R.string.NoChats);
                    } else if (this.dialogsType == 8) {
                        headerAllTitles = LocaleController.getString("NoFavoritesHelp", R.string.NoFavoritesHelp);
                    } else if (this.dialogsType == 10) {
                        headerAllTitles = LocaleController.getString("NoLockedChatsHelp", R.string.NoLockedChatsHelp);
                    }
                    textView.setText(headerAllTitles);
                    textView.setTextColor(sharedPreferences.getInt("chatsNameColor", -14606047));
                }
                if (this.emptyView.getChildAt(1) != null) {
                    this.emptyView.getChildAt(1).setVisibility(8);
                }
            }
            this.emptyView.setVisibility(0);
            this.emptyView.setBackgroundColor(sharedPreferences.getInt("chatsRowColor", -1));
            this.listView.setEmptyView(this.emptyView);
        }
    }

    private void registerSdkAp() {
        C2818c.a(ApplicationLoader.applicationContext, new C47162()).i();
    }

    private void showPassLockDialog(Context context, boolean z, OnClickListener onClickListener, final int i) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.password_dialog);
        dialog.setCancelable(true);
        final EditText editText = (EditText) dialog.findViewById(R.id.input_password);
        TextView textView = (TextView) dialog.findViewById(R.id.passDialogMessage);
        if (TextUtils.isEmpty(C3791b.d(context))) {
            textView.setText(LocaleController.getString("PleaseEnterPassword", R.string.PleaseEnterPassword));
        } else {
            textView.setText(LocaleController.getString("PleaseEnterCurrentPassword", R.string.PleaseEnterCurrentPassword));
        }
        final boolean z2 = z;
        final Context context2 = context;
        final OnClickListener onClickListener2 = onClickListener;
        dialog.findViewById(R.id.tvOk).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String obj = editText.getText().toString();
                if (TextUtils.isEmpty(obj)) {
                    editText.setError(LocaleController.getString("PleaseEnterCurrentPassword", R.string.PleaseEnterCurrentPassword));
                    editText.requestFocus();
                    return;
                }
                if (z2) {
                    C3791b.b(context2, obj);
                    dialog.dismiss();
                } else {
                    CharSequence d = C3791b.d(context2);
                    if (TextUtils.isEmpty(d) || obj.equals(d)) {
                        if (TextUtils.isEmpty(d)) {
                            C3791b.b(context2, obj);
                        }
                        DialogsActivity.this.dialogsType = 10;
                        DialogsActivity.this.refreshAdapter(context2);
                        dialog.dismiss();
                    } else {
                        editText.setError(LocaleController.getString("PasswordDoNotMatch", R.string.PasswordDoNotMatch));
                        editText.requestFocus();
                    }
                }
                if (onClickListener2 != null) {
                    onClickListener2.onClick(view);
                }
            }
        });
        dialog.findViewById(R.id.tvCancel).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (i >= 0) {
                    DialogsActivity.this.dialogsType = i;
                    DialogsActivity.this.refreshTabs();
                }
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                if (i >= 0) {
                    DialogsActivity.this.dialogsType = i;
                    DialogsActivity.this.refreshTabs();
                }
            }
        });
        showDialog(dialog);
    }

    private void unreadCount() {
        fillAllUnreadCounts();
        try {
            if (C3791b.a("TAB_ALL", ApplicationLoader.applicationContext)) {
                unreadCount(this.countAll, this.allMutedAll, (TextView) this.tabLayout.findViewWithTag("TAB_ALL"));
            }
            if (C3791b.a("TAB_UNREAD_CHATS", ApplicationLoader.applicationContext)) {
                unreadCount(this.countUnread, this.allMutedUnread, (TextView) this.tabLayout.findViewWithTag("TAB_UNREAD_CHATS"));
            }
            if (C3791b.a("TAB_USERS", ApplicationLoader.applicationContext)) {
                unreadCount(this.countUsers, this.allMutedUsers, (TextView) this.tabLayout.findViewWithTag("TAB_USERS"));
            }
            if (C3791b.a("TAB_BOTS", ApplicationLoader.applicationContext)) {
                unreadCount(this.countBots, this.allMutedBots, (TextView) this.tabLayout.findViewWithTag("TAB_BOTS"));
            }
            if (C3791b.a("TAB_CHANNELS", ApplicationLoader.applicationContext)) {
                unreadCount(this.countChannels, this.allMutedChannels, (TextView) this.tabLayout.findViewWithTag("TAB_CHANNELS"));
            }
            if (C3791b.a("TAB_FAVES", ApplicationLoader.applicationContext)) {
                unreadCount(this.countFavs, this.allMutedFavs, (TextView) this.tabLayout.findViewWithTag("TAB_FAVES"));
            }
            if (C3791b.a("TAB_ADS", ApplicationLoader.applicationContext)) {
                unreadCount(this.countAds, this.allMutedAds, (TextView) this.tabLayout.findViewWithTag("TAB_ADS"));
            }
            unreadCountGroups();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unreadCount(int i, boolean z, TextView textView) {
        if (textView != null) {
            textView.setVisibility(0);
            if (i == 0) {
                textView.setVisibility(8);
                return;
            }
            CharSequence valueOf = String.valueOf(i);
            if (i > 99) {
                valueOf = "+99";
            }
            textView.setVisibility(0);
            textView.setText(valueOf);
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0);
            textView.setTextSize(1, (float) sharedPreferences.getInt("chatsHeaderTabCounterSize", 8));
            int i2 = sharedPreferences.getInt("chatsHeaderTabCounterColor", -1);
            if (z) {
                textView.getBackground().setColorFilter(sharedPreferences.getInt("chatsHeaderTabCounterSilentBGColor", -4605511), Mode.SRC_IN);
                textView.setTextColor(i2);
                return;
            }
            textView.getBackground().setColorFilter(sharedPreferences.getInt("chatsHeaderTabCounterBGColor", -2937041), Mode.SRC_IN);
            textView.setTextColor(i2);
        }
    }

    private void unreadCountGroups() {
        if (!ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("hideSGroups", false)) {
            if (C3791b.a("TAB_GROUPS", ApplicationLoader.applicationContext)) {
                unreadCount(this.countGroups, this.allMutedGroups, (TextView) this.tabLayout.findViewWithTag("TAB_GROUPS"));
            }
            if (C3791b.a("TAB_SGROUP", ApplicationLoader.applicationContext)) {
                unreadCount(this.countMegaGroups, this.allMutedMegaGroups, (TextView) this.tabLayout.findViewWithTag("TAB_SGROUP"));
            }
        } else if (C3791b.a("TAB_GROUPS", ApplicationLoader.applicationContext)) {
            unreadCount(this.countGroupsAll, this.allMutedGroupsAll, (TextView) this.tabLayout.findViewWithTag("TAB_GROUPS"));
        }
    }

    private void unreadCountGroupsOld() {
        if (!ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("hideSGroups", false)) {
            if (C3791b.a("TAB_GROUPS", ApplicationLoader.applicationContext)) {
                unreadCountOld(MessagesController.getInstance().dialogsGroups, (TextView) this.tabLayout.findViewWithTag("TAB_GROUPS"));
            }
            if (C3791b.a("TAB_SGROUP", ApplicationLoader.applicationContext)) {
                unreadCountOld(MessagesController.getInstance().dialogsMegaGroups, (TextView) this.tabLayout.findViewWithTag("TAB_SGROUP"));
            }
        } else if (C3791b.a("TAB_GROUPS", ApplicationLoader.applicationContext)) {
            unreadCountOld(MessagesController.getInstance().dialogsGroupsAll, (TextView) this.tabLayout.findViewWithTag("TAB_GROUPS"));
        }
    }

    private void unreadCountOld(ArrayList<TLRPC$TL_dialog> arrayList, TextView textView) {
        if (textView != null) {
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
            if (!sharedPreferences.getBoolean("hideTabs", false)) {
                if (sharedPreferences.getBoolean("hideTabsCounters", false)) {
                    textView.setVisibility(8);
                    return;
                }
                int i;
                int i2;
                boolean z = sharedPreferences.getBoolean("tabsCountersCountChats", false);
                boolean z2 = sharedPreferences.getBoolean("tabsCountersCountNotMuted", false);
                if (arrayList == null || arrayList.isEmpty()) {
                    i = 0;
                    i2 = 1;
                } else {
                    Iterator it = arrayList.iterator();
                    i = 0;
                    boolean z3 = true;
                    while (it.hasNext()) {
                        int i3;
                        boolean z4;
                        TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) it.next();
                        boolean isDialogMuted = MessagesController.getInstance().isDialogMuted(tLRPC$TL_dialog.id);
                        if (!(isDialogMuted && z2)) {
                            i3 = tLRPC$TL_dialog.unread_count;
                            if (i3 > 0) {
                                if (!z) {
                                    i += i3;
                                } else if (i3 > 0) {
                                    i++;
                                }
                                if (i3 > 0 && !isDialogMuted) {
                                    i3 = i;
                                    z4 = false;
                                    z3 = z4;
                                    i = i3;
                                }
                            }
                        }
                        i3 = i;
                        z4 = z3;
                        z3 = z4;
                        i = i3;
                    }
                }
                if (i == 0) {
                    textView.setVisibility(8);
                    return;
                }
                CharSequence valueOf = String.valueOf(i);
                if (i > 99) {
                    valueOf = "+99";
                }
                textView.setVisibility(0);
                textView.setText(valueOf);
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0);
                textView.setTextSize(1, (float) sharedPreferences.getInt("chatsHeaderTabCounterSize", 8));
                i = sharedPreferences.getInt("chatsHeaderTabCounterColor", -1);
                if (i2 != 0) {
                    textView.getBackground().setColorFilter(sharedPreferences.getInt("chatsHeaderTabCounterSilentBGColor", -4605511), Mode.SRC_IN);
                    textView.setTextColor(i);
                    return;
                }
                textView.getBackground().setColorFilter(sharedPreferences.getInt("chatsHeaderTabCounterBGColor", -2937041), Mode.SRC_IN);
                textView.setTextColor(i);
            }
        }
    }

    private void updatePasscodeButton() {
        if (this.passcodeItem != null) {
            if (UserConfig.passcodeHash.length() == 0 || this.searching) {
                this.passcodeItem.setVisibility(8);
                return;
            }
            this.passcodeItem.setVisibility(0);
            if (UserConfig.appLocked) {
                this.passcodeItem.setIcon((int) R.drawable.lock_close);
            } else {
                this.passcodeItem.setIcon((int) R.drawable.lock_open);
            }
        }
    }

    private void updateSelectedCount() {
        if (this.commentView != null) {
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (this.dialogsAdapter.hasSelectedDialogs()) {
                if (this.commentView.getTag() == null) {
                    this.commentView.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
                    this.commentView.setVisibility(0);
                    animatorSet = new AnimatorSet();
                    animatorArr = new Animator[1];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.commentView, "translationY", new float[]{(float) this.commentView.getMeasuredHeight(), BitmapDescriptorFactory.HUE_RED});
                    animatorSet.playTogether(animatorArr);
                    animatorSet.setDuration(180);
                    animatorSet.setInterpolator(new DecelerateInterpolator());
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            DialogsActivity.this.commentView.setTag(Integer.valueOf(2));
                        }
                    });
                    animatorSet.start();
                    this.commentView.setTag(Integer.valueOf(1));
                }
                this.actionBar.setTitle(LocaleController.formatPluralString("Recipient", this.dialogsAdapter.getSelectedDialogs().size()));
                return;
            }
            if (this.dialogsType == 3 && this.selectAlertString == null) {
                this.actionBar.setTitle(LocaleController.getString("ForwardTo", R.string.ForwardTo));
            } else {
                this.actionBar.setTitle(LocaleController.getString("SelectChat", R.string.SelectChat));
            }
            if (this.commentView.getTag() != null) {
                this.commentView.hidePopup(false);
                this.commentView.closeKeyboard();
                animatorSet = new AnimatorSet();
                animatorArr = new Animator[1];
                animatorArr[0] = ObjectAnimator.ofFloat(this.commentView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) this.commentView.getMeasuredHeight()});
                animatorSet.playTogether(animatorArr);
                animatorSet.setDuration(180);
                animatorSet.setInterpolator(new DecelerateInterpolator());
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        DialogsActivity.this.commentView.setVisibility(8);
                    }
                });
                animatorSet.start();
                this.commentView.setTag(null);
                this.listView.requestLayout();
            }
        }
    }

    private void updateTabs() {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        this.hideTabs = sharedPreferences.getBoolean("hideTabs", false);
        this.disableAnimation = sharedPreferences.getBoolean("disableTabsAnimation", false);
        this.tabsHeight = sharedPreferences.getInt("tabsHeight", 40);
        refreshTabAndListViews(false);
        if (this.hideTabs) {
            this.dialogsType = 0;
            refreshAdapterAndTabs(this.dialogsBackupAdapter);
        }
    }

    private void updateVisibleRows(int i) {
        if (this.listView != null) {
            int childCount = this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = this.listView.getChildAt(i2);
                if (childAt instanceof DialogCell) {
                    if (this.listView.getAdapter() != this.dialogsSearchAdapter) {
                        DialogCell dialogCell = (DialogCell) childAt;
                        if ((i & 2048) != 0) {
                            dialogCell.checkCurrentDialogIndex();
                            if (this.dialogsType == 0 && AndroidUtilities.isTablet()) {
                                dialogCell.setDialogSelected(dialogCell.getDialogId() == this.openedDialogId);
                            }
                        } else if ((i & 512) == 0) {
                            dialogCell.update(i);
                        } else if (this.dialogsType == 0 && AndroidUtilities.isTablet()) {
                            dialogCell.setDialogSelected(dialogCell.getDialogId() == this.openedDialogId);
                        }
                    }
                } else if (childAt instanceof UserCell) {
                    ((UserCell) childAt).update(i);
                } else if (childAt instanceof ProfileSearchCell) {
                    ((ProfileSearchCell) childAt).update(i);
                } else if (childAt instanceof RecyclerListView) {
                    RecyclerListView recyclerListView = (RecyclerListView) childAt;
                    int childCount2 = recyclerListView.getChildCount();
                    for (int i3 = 0; i3 < childCount2; i3++) {
                        View childAt2 = recyclerListView.getChildAt(i3);
                        if (childAt2 instanceof HintDialogCell) {
                            ((HintDialogCell) childAt2).checkUnreadCounter(i);
                        }
                    }
                }
            }
        }
    }

    public View createView(final Context context) {
        Drawable combinedDrawable;
        this.searching = false;
        this.searchWas = false;
        if (UserConfig.isClientActivated() && !C3791b.a(ApplicationLoader.applicationContext)) {
            C2818c.a(ApplicationLoader.applicationContext, this).a(false);
        }
        if (!C3791b.at(ApplicationLoader.applicationContext)) {
            Favourite.addFavourite(new Long((long) UserConfig.getClientUserId()));
            C3791b.w(ApplicationLoader.applicationContext, true);
        }
        this.avatarImage = new BackupImageView(getParentActivity());
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(30.0f));
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                Theme.createChatResources(context, false);
            }
        });
        this.menu = this.actionBar.createMenu();
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C47214());
        if (this.sideMenu != null) {
            this.sideMenu.setBackgroundColor(Theme.getColor(Theme.key_chats_menuBackground));
            this.sideMenu.setGlowColor(Theme.getColor(Theme.key_chats_menuBackground));
            this.sideMenu.getAdapter().notifyDataSetChanged();
        }
        this.contentView = new SizeNotifierFrameLayout(context) {
            int inputFieldHeight = 0;

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int i5;
                int childCount = getChildCount();
                Object tag = DialogsActivity.this.commentView != null ? DialogsActivity.this.commentView.getTag() : null;
                if (tag == null || !tag.equals(Integer.valueOf(2))) {
                    i5 = 0;
                } else {
                    int emojiPadding = (getKeyboardHeight() > AndroidUtilities.dp(20.0f) || AndroidUtilities.isInMultiwindow) ? 0 : DialogsActivity.this.commentView.getEmojiPadding();
                    i5 = emojiPadding;
                }
                setBottomClip(i5);
                for (int i6 = 0; i6 < childCount; i6++) {
                    View childAt = getChildAt(i6);
                    if (childAt.getVisibility() != 8) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                        int measuredWidth = childAt.getMeasuredWidth();
                        int measuredHeight = childAt.getMeasuredHeight();
                        int i7 = layoutParams.gravity;
                        if (i7 == -1) {
                            i7 = 51;
                        }
                        int i8 = i7 & 112;
                        switch ((i7 & 7) & 7) {
                            case 1:
                                i7 = ((((i3 - i) - measuredWidth) / 2) + layoutParams.leftMargin) - layoutParams.rightMargin;
                                break;
                            case 5:
                                i7 = (i3 - measuredWidth) - layoutParams.rightMargin;
                                break;
                            default:
                                i7 = layoutParams.leftMargin;
                                break;
                        }
                        switch (i8) {
                            case 16:
                                emojiPadding = (((((i4 - i5) - i2) - measuredHeight) / 2) + layoutParams.topMargin) - layoutParams.bottomMargin;
                                break;
                            case 48:
                                emojiPadding = layoutParams.topMargin + getPaddingTop();
                                break;
                            case 80:
                                emojiPadding = (((i4 - i5) - i2) - measuredHeight) - layoutParams.bottomMargin;
                                break;
                            default:
                                emojiPadding = layoutParams.topMargin;
                                break;
                        }
                        if (DialogsActivity.this.commentView != null && DialogsActivity.this.commentView.isPopupView(childAt)) {
                            emojiPadding = AndroidUtilities.isInMultiwindow ? (DialogsActivity.this.commentView.getTop() - childAt.getMeasuredHeight()) + AndroidUtilities.dp(1.0f) : DialogsActivity.this.commentView.getBottom();
                        }
                        childAt.layout(i7, emojiPadding, measuredWidth + i7, measuredHeight + emojiPadding);
                    }
                }
                notifyHeightChanged();
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i);
                int size2 = MeasureSpec.getSize(i2);
                setMeasuredDimension(size, size2);
                int paddingTop = size2 - getPaddingTop();
                measureChildWithMargins(DialogsActivity.this.actionBar, i, 0, i2, 0);
                int keyboardHeight = getKeyboardHeight();
                int childCount = getChildCount();
                if (DialogsActivity.this.commentView != null) {
                    measureChildWithMargins(DialogsActivity.this.commentView, i, 0, i2, 0);
                    Object tag = DialogsActivity.this.commentView.getTag();
                    if (tag == null || !tag.equals(Integer.valueOf(2))) {
                        this.inputFieldHeight = 0;
                    } else {
                        size2 = (keyboardHeight > AndroidUtilities.dp(20.0f) || AndroidUtilities.isInMultiwindow) ? paddingTop : paddingTop - DialogsActivity.this.commentView.getEmojiPadding();
                        this.inputFieldHeight = DialogsActivity.this.commentView.getMeasuredHeight();
                        paddingTop = size2;
                    }
                }
                for (keyboardHeight = 0; keyboardHeight < childCount; keyboardHeight++) {
                    try {
                        View childAt = getChildAt(keyboardHeight);
                        if (!(childAt == null || childAt.getVisibility() == 8 || childAt == DialogsActivity.this.commentView || childAt == DialogsActivity.this.actionBar)) {
                            if (childAt == DialogsActivity.this.listView || childAt == DialogsActivity.this.progressView || childAt == DialogsActivity.this.searchEmptyView) {
                                childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(Math.max(AndroidUtilities.dp(10.0f), (paddingTop - this.inputFieldHeight) + AndroidUtilities.dp(2.0f)), 1073741824));
                            } else if (DialogsActivity.this.commentView == null || !DialogsActivity.this.commentView.isPopupView(childAt)) {
                                measureChildWithMargins(childAt, i, 0, i2, 0);
                            } else if (!AndroidUtilities.isInMultiwindow) {
                                childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
                            } else if (AndroidUtilities.isTablet()) {
                                childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.dp(320.0f), ((paddingTop - this.inputFieldHeight) - AndroidUtilities.statusBarHeight) + getPaddingTop()), 1073741824));
                            } else {
                                childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(((paddingTop - this.inputFieldHeight) - AndroidUtilities.statusBarHeight) + getPaddingTop(), 1073741824));
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        };
        this.fragmentView = this.contentView;
        this.parentLayout.setShowShadow(false);
        this.frameLayout = new FrameLayout(context);
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(true);
        this.listView.setItemAnimator(null);
        this.listView.setInstantClick(true);
        this.listView.setLayoutAnimation(null);
        this.listView.setTag(Integer.valueOf(4));
        this.layoutManager = new LinearLayoutManager(context) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.layoutManager.setOrientation(1);
        this.listView.setLayoutManager(this.layoutManager);
        this.listView.setVerticalScrollbarPosition(LocaleController.isRTL ? 1 : 2);
        this.contentView.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f, 48, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.isSelectMode ? BitmapDescriptorFactory.HUE_RED : (float) this.bottomBarHeight));
        this.viewPager = new ViewPager(context);
        this.viewPager.setAdapter(new CustomPagerAdapter(context));
        this.frameLayout.addView(this.viewPager, LayoutHelper.createFrame(-1, BitmapDescriptorFactory.HUE_RED, 0, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) this.bottomBarHeight));
        this.onTouchListener = new DialogsOnTouch(context);
        this.listView.setOnTouchListener(this.onTouchListener);
        this.listView.setOnItemClickListener(new C47247());
        this.listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            /* renamed from: org.telegram.ui.DialogsActivity$8$1 */
            class C47251 implements DialogInterface.OnClickListener {
                C47251() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (DialogsActivity.this.dialogsSearchAdapter.isRecentSearchDisplayed()) {
                        DialogsActivity.this.dialogsSearchAdapter.clearRecentSearch();
                    } else {
                        DialogsActivity.this.dialogsSearchAdapter.clearRecentHashtags();
                    }
                }
            }

            public boolean onItemClick(View view, int i) {
                if (DialogsActivity.this.getParentActivity() == null) {
                    return false;
                }
                if (DialogsActivity.this.listView.getAdapter() != DialogsActivity.this.dialogsSearchAdapter) {
                    ArrayList access$1900 = DialogsActivity.this.getDialogsArray();
                    if (i < 0 || i >= access$1900.size()) {
                        return false;
                    }
                    TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) access$1900.get(i);
                    if (!DialogsActivity.this.onlySelect) {
                        DialogsActivity.this.selectedDialog = tLRPC$TL_dialog.id;
                        final boolean z = tLRPC$TL_dialog.pinned;
                        BottomSheet.Builder builder = new BottomSheet.Builder(DialogsActivity.this.getParentActivity());
                        int access$2200 = (int) DialogsActivity.this.selectedDialog;
                        int access$22002 = (int) (DialogsActivity.this.selectedDialog >> 32);
                        boolean c = C3791b.c(context, tLRPC$TL_dialog.id);
                        if (c) {
                            LocaleController.getString("unlock", R.string.unlock);
                        } else {
                            LocaleController.getString("lock", R.string.lock);
                        }
                        String string;
                        String string2;
                        if (DialogObject.isChannel(tLRPC$TL_dialog)) {
                            CharSequence string3;
                            CharSequence[] charSequenceArr;
                            final Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-access$2200));
                            int[] iArr = new int[8];
                            iArr[0] = tLRPC$TL_dialog.pinned ? R.drawable.chats_unpin : R.drawable.chats_pin;
                            iArr[1] = R.drawable.chats_clear;
                            iArr[2] = R.drawable.chats_leave;
                            iArr[3] = Favourite.isFavourite(Long.valueOf(tLRPC$TL_dialog.id)) ? R.drawable.ic_star : R.drawable.ic_off_star;
                            iArr[4] = R.drawable.notifications_s_on;
                            iArr[5] = R.drawable.ic_mark_as_read;
                            iArr[6] = R.drawable.ic_fast;
                            iArr[7] = c ? R.drawable.lock_close_off : R.drawable.lock_close;
                            String string4 = Favourite.isFavourite(Long.valueOf(tLRPC$TL_dialog.id)) ? LocaleController.getString("DeleteFromFavorites", R.string.DeleteFromFavorites) : LocaleController.getString("AddToFavorites", R.string.AddToFavorites);
                            string = MessagesController.getInstance().isDialogMuted(DialogsActivity.this.selectedDialog) ? LocaleController.getString("UnmuteNotifications", R.string.UnmuteNotifications) : LocaleController.getString("MuteNotifications", R.string.MuteNotifications);
                            string2 = LocaleController.getString("AddShortcut", R.string.AddShortcut);
                            if (c) {
                                string3 = LocaleController.getString("unlock", R.string.unlock);
                            } else {
                                Object string5 = LocaleController.getString("lock", R.string.lock);
                            }
                            CharSequence[] charSequenceArr2;
                            String string6;
                            if (chat == null || !chat.megagroup) {
                                charSequenceArr2 = new CharSequence[8];
                                string6 = (tLRPC$TL_dialog.pinned || MessagesController.getInstance().canPinDialog(false)) ? tLRPC$TL_dialog.pinned ? LocaleController.getString("UnpinFromTop", R.string.UnpinFromTop) : LocaleController.getString("PinToTop", R.string.PinToTop) : null;
                                charSequenceArr2[0] = string6;
                                charSequenceArr2[1] = LocaleController.getString("ClearHistoryCache", R.string.ClearHistoryCache);
                                charSequenceArr2[2] = LocaleController.getString("LeaveChannelMenu", R.string.LeaveChannelMenu);
                                charSequenceArr2[3] = string4;
                                charSequenceArr2[4] = string;
                                charSequenceArr2[5] = LocaleController.getString("MarkAsRead", R.string.MarkAsRead);
                                charSequenceArr2[6] = string2;
                                charSequenceArr2[7] = string3;
                                charSequenceArr = charSequenceArr2;
                            } else {
                                charSequenceArr2 = new CharSequence[8];
                                string6 = (tLRPC$TL_dialog.pinned || MessagesController.getInstance().canPinDialog(false)) ? tLRPC$TL_dialog.pinned ? LocaleController.getString("UnpinFromTop", R.string.UnpinFromTop) : LocaleController.getString("PinToTop", R.string.PinToTop) : null;
                                charSequenceArr2[0] = string6;
                                charSequenceArr2[1] = TextUtils.isEmpty(chat.username) ? LocaleController.getString("ClearHistory", R.string.ClearHistory) : LocaleController.getString("ClearHistoryCache", R.string.ClearHistoryCache);
                                charSequenceArr2[2] = LocaleController.getString("LeaveMegaMenu", R.string.LeaveMegaMenu);
                                charSequenceArr2[3] = string4;
                                charSequenceArr2[4] = string;
                                charSequenceArr2[5] = LocaleController.getString("MarkAsRead", R.string.MarkAsRead);
                                charSequenceArr2[6] = string2;
                                charSequenceArr2[7] = string3;
                                charSequenceArr = charSequenceArr2;
                            }
                            builder.setItems(charSequenceArr, iArr, new DialogInterface.OnClickListener() {

                                /* renamed from: org.telegram.ui.DialogsActivity$8$2$1 */
                                class C47261 implements DialogInterface.OnClickListener {
                                    C47261() {
                                    }

                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (chat != null && chat.megagroup && TextUtils.isEmpty(chat.username)) {
                                            MessagesController.getInstance().deleteDialog(DialogsActivity.this.selectedDialog, 1);
                                        } else {
                                            MessagesController.getInstance().deleteDialog(DialogsActivity.this.selectedDialog, 2);
                                        }
                                    }
                                }

                                /* renamed from: org.telegram.ui.DialogsActivity$8$2$2 */
                                class C47272 implements DialogInterface.OnClickListener {
                                    C47272() {
                                    }

                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MessagesController.getInstance().deleteUserFromChat((int) (-DialogsActivity.this.selectedDialog), UserConfig.getCurrentUser(), null);
                                        if (AndroidUtilities.isTablet()) {
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[]{Long.valueOf(DialogsActivity.this.selectedDialog)});
                                        }
                                    }
                                }

                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean z = true;
                                    if (i == 0) {
                                        MessagesController instance = MessagesController.getInstance();
                                        long access$2200 = DialogsActivity.this.selectedDialog;
                                        if (z) {
                                            z = false;
                                        }
                                        if (instance.pinDialog(access$2200, z, null, 0) && !z) {
                                            DialogsActivity.this.listView.smoothScrollToPosition(0);
                                        }
                                    } else if (i == 3) {
                                        r0 = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(DialogsActivity.this.selectedDialog));
                                        if (Favourite.isFavourite(Long.valueOf(r0.id))) {
                                            Favourite.deleteFavourite(Long.valueOf(DialogsActivity.this.selectedDialog));
                                            MessagesController.getInstance().dialogsFavs.remove(r0);
                                        } else {
                                            Favourite.addFavourite(Long.valueOf(DialogsActivity.this.selectedDialog));
                                            MessagesController.getInstance().dialogsFavs.add(r0);
                                        }
                                        if (DialogsActivity.this.dialogsType == 8) {
                                            DialogsActivity.this.dialogsAdapter.notifyDataSetChanged();
                                            if (!DialogsActivity.this.hideTabs) {
                                                DialogsActivity.this.updateTabs();
                                            }
                                        }
                                        DialogsActivity.this.fillAllUnreadCounts();
                                        DialogsActivity.this.unreadCount(DialogsActivity.this.countFavs, DialogsActivity.this.allMutedFavs, (TextView) DialogsActivity.this.tabLayout.findViewWithTag("TAB_FAVES"));
                                    } else if (i == 4) {
                                        if (MessagesController.getInstance().isDialogMuted(DialogsActivity.this.selectedDialog)) {
                                            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                                            edit.putInt("notify2_" + DialogsActivity.this.selectedDialog, 0);
                                            MessagesStorage.getInstance().setDialogFlags(DialogsActivity.this.selectedDialog, 0);
                                            edit.commit();
                                            r0 = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(DialogsActivity.this.selectedDialog));
                                            if (r0 != null) {
                                                r0.notify_settings = new TLRPC$TL_peerNotifySettings();
                                            }
                                            NotificationsController.updateServerNotificationsSettings(DialogsActivity.this.selectedDialog);
                                            return;
                                        }
                                        DialogsActivity.this.showDialog(AlertsCreator.createMuteAlert(DialogsActivity.this.getParentActivity(), DialogsActivity.this.selectedDialog));
                                    } else if (i == 5) {
                                        DialogsActivity.this.markAsReadDialog(false);
                                    } else if (i == 6) {
                                        DialogsActivity.this.addShortcut();
                                    } else if (i == 7) {
                                        boolean c = C3791b.c(context, DialogsActivity.this.selectedDialog);
                                        if (c) {
                                            LocaleController.getString("unlock", R.string.unlock);
                                        } else {
                                            LocaleController.getString("lock", R.string.lock);
                                        }
                                        DialogsActivity.this.lockChat(context, c, DialogsActivity.this.selectedDialog);
                                    } else {
                                        Builder builder = new Builder(DialogsActivity.this.getParentActivity());
                                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                        if (i == 1) {
                                            if (chat == null || !chat.megagroup) {
                                                builder.setMessage(LocaleController.getString("AreYouSureClearHistoryChannel", R.string.AreYouSureClearHistoryChannel));
                                            } else if (TextUtils.isEmpty(chat.username)) {
                                                builder.setMessage(LocaleController.getString("AreYouSureClearHistory", R.string.AreYouSureClearHistory));
                                            } else {
                                                builder.setMessage(LocaleController.getString("AreYouSureClearHistoryGroup", R.string.AreYouSureClearHistoryGroup));
                                            }
                                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C47261());
                                        } else {
                                            Log.d("alireza", "alireza selectedDialog" + DialogsActivity.this.selectedDialog);
                                            if (chat == null || !chat.megagroup) {
                                                builder.setMessage(LocaleController.getString("ChannelLeaveAlert", R.string.ChannelLeaveAlert));
                                            } else {
                                                builder.setMessage(LocaleController.getString("MegaLeaveAlert", R.string.MegaLeaveAlert));
                                            }
                                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C47272());
                                        }
                                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                                        DialogsActivity.this.showDialog(builder.create());
                                    }
                                }
                            });
                            DialogsActivity.this.showDialog(builder.create());
                        } else {
                            String str;
                            int[] iArr2;
                            final boolean z2 = access$2200 < 0 && access$22002 != 1;
                            User user = null;
                            if (!(z2 || access$2200 <= 0 || access$22002 == 1)) {
                                user = MessagesController.getInstance().getUser(Integer.valueOf(access$2200));
                            }
                            final boolean z3 = user != null && user.bot;
                            string = Favourite.isFavourite(Long.valueOf(tLRPC$TL_dialog.id)) ? LocaleController.getString("DeleteFromFavorites", R.string.DeleteFromFavorites) : LocaleController.getString("AddToFavorites", R.string.AddToFavorites);
                            String string7 = MessagesController.getInstance().isDialogMuted(DialogsActivity.this.selectedDialog) ? LocaleController.getString("UnmuteNotifications", R.string.UnmuteNotifications) : LocaleController.getString("MuteNotifications", R.string.MuteNotifications);
                            string2 = LocaleController.getString("AddShortcut", R.string.AddShortcut);
                            String string8 = c ? LocaleController.getString("unlock", R.string.unlock) : LocaleController.getString("lock", R.string.lock);
                            CharSequence[] charSequenceArr3 = new CharSequence[8];
                            if (!tLRPC$TL_dialog.pinned) {
                                if (!MessagesController.getInstance().canPinDialog(access$2200 == 0)) {
                                    str = null;
                                    charSequenceArr3[0] = str;
                                    charSequenceArr3[1] = LocaleController.getString("ClearHistory", R.string.ClearHistory);
                                    str = z2 ? LocaleController.getString("DeleteChat", R.string.DeleteChat) : z3 ? LocaleController.getString("DeleteAndStop", R.string.DeleteAndStop) : LocaleController.getString("Delete", R.string.Delete);
                                    charSequenceArr3[2] = str;
                                    charSequenceArr3[3] = string;
                                    charSequenceArr3[4] = string7;
                                    charSequenceArr3[5] = LocaleController.getString("MarkAsRead", R.string.MarkAsRead);
                                    charSequenceArr3[6] = string2;
                                    charSequenceArr3[7] = string8;
                                    iArr2 = new int[8];
                                    iArr2[0] = tLRPC$TL_dialog.pinned ? R.drawable.chats_unpin : R.drawable.chats_pin;
                                    iArr2[1] = R.drawable.chats_clear;
                                    iArr2[2] = z2 ? R.drawable.chats_leave : R.drawable.chats_delete;
                                    iArr2[3] = Favourite.isFavourite(Long.valueOf(tLRPC$TL_dialog.id)) ? R.drawable.ic_star : R.drawable.ic_off_star;
                                    iArr2[4] = R.drawable.notifications_s_on;
                                    iArr2[5] = R.drawable.ic_mark_as_read;
                                    iArr2[6] = R.drawable.ic_fast;
                                    iArr2[7] = c ? R.drawable.lock_close_off : R.drawable.lock_close;
                                    builder.setItems(charSequenceArr3, iArr2, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, final int i) {
                                            boolean z = true;
                                            if (i == 0) {
                                                MessagesController instance = MessagesController.getInstance();
                                                long access$2200 = DialogsActivity.this.selectedDialog;
                                                if (z) {
                                                    z = false;
                                                }
                                                if (instance.pinDialog(access$2200, z, null, 0) && !z) {
                                                    DialogsActivity.this.listView.smoothScrollToPosition(0);
                                                }
                                            } else if (i == 3) {
                                                r0 = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(DialogsActivity.this.selectedDialog));
                                                if (Favourite.isFavourite(Long.valueOf(r0.id))) {
                                                    Favourite.deleteFavourite(Long.valueOf(DialogsActivity.this.selectedDialog));
                                                    MessagesController.getInstance().dialogsFavs.remove(r0);
                                                } else {
                                                    Favourite.addFavourite(Long.valueOf(DialogsActivity.this.selectedDialog));
                                                    MessagesController.getInstance().dialogsFavs.add(r0);
                                                }
                                                if (DialogsActivity.this.dialogsType == 8) {
                                                    DialogsActivity.this.dialogsAdapter.notifyDataSetChanged();
                                                    if (!DialogsActivity.this.hideTabs) {
                                                        DialogsActivity.this.updateTabs();
                                                    }
                                                }
                                                DialogsActivity.this.fillAllUnreadCounts();
                                                DialogsActivity.this.unreadCount(DialogsActivity.this.countFavs, DialogsActivity.this.allMutedFavs, (TextView) DialogsActivity.this.tabLayout.findViewWithTag("TAB_FAVES"));
                                            } else if (i == 4) {
                                                if (MessagesController.getInstance().isDialogMuted(DialogsActivity.this.selectedDialog)) {
                                                    Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                                                    edit.putInt("notify2_" + DialogsActivity.this.selectedDialog, 0);
                                                    MessagesStorage.getInstance().setDialogFlags(DialogsActivity.this.selectedDialog, 0);
                                                    edit.commit();
                                                    r0 = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(DialogsActivity.this.selectedDialog));
                                                    if (r0 != null) {
                                                        r0.notify_settings = new TLRPC$TL_peerNotifySettings();
                                                    }
                                                    NotificationsController.updateServerNotificationsSettings(DialogsActivity.this.selectedDialog);
                                                    return;
                                                }
                                                DialogsActivity.this.showDialog(AlertsCreator.createMuteAlert(DialogsActivity.this.getParentActivity(), DialogsActivity.this.selectedDialog));
                                            } else if (i == 5) {
                                                DialogsActivity.this.markAsReadDialog(false);
                                            } else if (i == 6) {
                                                DialogsActivity.this.addShortcut();
                                            } else if (i == 7) {
                                                boolean c = C3791b.c(context, DialogsActivity.this.selectedDialog);
                                                if (c) {
                                                    LocaleController.getString("unlock", R.string.unlock);
                                                } else {
                                                    LocaleController.getString("lock", R.string.lock);
                                                }
                                                DialogsActivity.this.lockChat(context, c, DialogsActivity.this.selectedDialog);
                                            } else {
                                                Log.d("alireza", "alireza selectedDialog" + DialogsActivity.this.selectedDialog);
                                                Builder builder = new Builder(DialogsActivity.this.getParentActivity());
                                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                                if (i == 1) {
                                                    builder.setMessage(LocaleController.getString("AreYouSureClearHistory", R.string.AreYouSureClearHistory));
                                                } else if (z2) {
                                                    builder.setMessage(LocaleController.getString("AreYouSureDeleteAndExit", R.string.AreYouSureDeleteAndExit));
                                                } else {
                                                    builder.setMessage(LocaleController.getString("AreYouSureDeleteThisChat", R.string.AreYouSureDeleteThisChat));
                                                }
                                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        if (i != 1) {
                                                            if (z2) {
                                                                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf((int) (-DialogsActivity.this.selectedDialog)));
                                                                if (chat == null || !ChatObject.isNotInChat(chat)) {
                                                                    MessagesController.getInstance().deleteUserFromChat((int) (-DialogsActivity.this.selectedDialog), MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId())), null);
                                                                } else {
                                                                    MessagesController.getInstance().deleteDialog(DialogsActivity.this.selectedDialog, 0);
                                                                }
                                                            } else {
                                                                MessagesController.getInstance().deleteDialog(DialogsActivity.this.selectedDialog, 0);
                                                            }
                                                            if (z3) {
                                                                MessagesController.getInstance().blockUser((int) DialogsActivity.this.selectedDialog);
                                                            }
                                                            if (AndroidUtilities.isTablet()) {
                                                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[]{Long.valueOf(DialogsActivity.this.selectedDialog)});
                                                                return;
                                                            }
                                                            return;
                                                        }
                                                        MessagesController.getInstance().deleteDialog(DialogsActivity.this.selectedDialog, 1);
                                                    }
                                                });
                                                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                                                DialogsActivity.this.showDialog(builder.create());
                                            }
                                        }
                                    });
                                    DialogsActivity.this.showDialog(builder.create());
                                }
                            }
                            str = tLRPC$TL_dialog.pinned ? LocaleController.getString("UnpinFromTop", R.string.UnpinFromTop) : LocaleController.getString("PinToTop", R.string.PinToTop);
                            charSequenceArr3[0] = str;
                            charSequenceArr3[1] = LocaleController.getString("ClearHistory", R.string.ClearHistory);
                            if (z2) {
                            }
                            charSequenceArr3[2] = str;
                            charSequenceArr3[3] = string;
                            charSequenceArr3[4] = string7;
                            charSequenceArr3[5] = LocaleController.getString("MarkAsRead", R.string.MarkAsRead);
                            charSequenceArr3[6] = string2;
                            charSequenceArr3[7] = string8;
                            iArr2 = new int[8];
                            if (tLRPC$TL_dialog.pinned) {
                            }
                            iArr2[0] = tLRPC$TL_dialog.pinned ? R.drawable.chats_unpin : R.drawable.chats_pin;
                            iArr2[1] = R.drawable.chats_clear;
                            if (z2) {
                            }
                            iArr2[2] = z2 ? R.drawable.chats_leave : R.drawable.chats_delete;
                            if (Favourite.isFavourite(Long.valueOf(tLRPC$TL_dialog.id))) {
                            }
                            iArr2[3] = Favourite.isFavourite(Long.valueOf(tLRPC$TL_dialog.id)) ? R.drawable.ic_star : R.drawable.ic_off_star;
                            iArr2[4] = R.drawable.notifications_s_on;
                            iArr2[5] = R.drawable.ic_mark_as_read;
                            iArr2[6] = R.drawable.ic_fast;
                            if (c) {
                            }
                            iArr2[7] = c ? R.drawable.lock_close_off : R.drawable.lock_close;
                            builder.setItems(charSequenceArr3, iArr2, /* anonymous class already generated */);
                            DialogsActivity.this.showDialog(builder.create());
                        }
                    } else if (DialogsActivity.this.dialogsType != 3 || DialogsActivity.this.selectAlertString != null) {
                        return false;
                    } else {
                        DialogsActivity.this.dialogsAdapter.addOrRemoveSelectedDialog(tLRPC$TL_dialog.id, view);
                        DialogsActivity.this.updateSelectedCount();
                    }
                    return true;
                } else if (!(DialogsActivity.this.dialogsSearchAdapter.getItem(i) instanceof String) && !DialogsActivity.this.dialogsSearchAdapter.isRecentSearchDisplayed()) {
                    return false;
                } else {
                    Builder builder2 = new Builder(DialogsActivity.this.getParentActivity());
                    builder2.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder2.setMessage(LocaleController.getString("ClearSearch", R.string.ClearSearch));
                    builder2.setPositiveButton(LocaleController.getString("ClearButton", R.string.ClearButton).toUpperCase(), new C47251());
                    builder2.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    DialogsActivity.this.showDialog(builder2.create());
                    return true;
                }
            }
        });
        this.searchEmptyView = new EmptyTextProgressView2(context);
        this.searchEmptyView.setFragment(this);
        this.searchEmptyView.setVisibility(8);
        this.searchEmptyView.setShowAtCenter(true);
        this.searchEmptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        this.contentView.addView(this.searchEmptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.emptyView = new LinearLayout(context);
        this.emptyView.setOrientation(1);
        this.emptyView.setVisibility(8);
        this.emptyView.setGravity(17);
        this.frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.emptyView.setOnTouchListener(this.onTouchListener);
        this.progressView = new RadialProgressView(context);
        this.progressView.setVisibility(8);
        this.contentView.addView(this.progressView, LayoutHelper.createFrame(-2, -2, 17));
        this.floatingButton = new ImageView(context);
        this.floatingButton.setVisibility(this.onlySelect ? 8 : 0);
        this.floatingButton.setScaleType(ScaleType.CENTER);
        Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_chats_actionBackground), Theme.getColor(Theme.key_chats_actionPressedBackground));
        if (VERSION.SDK_INT < 21) {
            Drawable mutate = context.getResources().getDrawable(R.drawable.floating_shadow).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, Mode.MULTIPLY));
            combinedDrawable = new CombinedDrawable(mutate, createSimpleSelectorCircleDrawable, 0, 0);
            combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        } else {
            combinedDrawable = createSimpleSelectorCircleDrawable;
        }
        this.floatingButton.setBackgroundDrawable(combinedDrawable);
        this.floatingButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), Mode.MULTIPLY));
        this.floatingButton.setImageResource(R.drawable.floating_pencil);
        if (VERSION.SDK_INT >= 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.floatingButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
            stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this.floatingButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
            this.floatingButton.setStateListAnimator(stateListAnimator);
            this.floatingButton.setOutlineProvider(new C47329());
        }
        if (!this.isSelectMode) {
            this.contentView.addView(this.floatingButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, (LocaleController.isRTL ? 3 : 5) | 80, LocaleController.isRTL ? 14.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 14.0f, (float) (this.bottomBarHeight + 14)));
            this.floatingButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("destroyAfterSelect", true);
                    DialogsActivity.this.presentFragment(new ContactsActivity(bundle));
                }
            });
        }
        this.tabsView = new FrameLayout(context);
        createTabs(context);
        this.contentView.addView(this.tabsView, LayoutHelper.createFrame(-1, (float) this.tabsHeight, 48, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.listView.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 1 && DialogsActivity.this.searching && DialogsActivity.this.searchWas) {
                    AndroidUtilities.hideKeyboard(DialogsActivity.this.getParentActivity().getCurrentFocus());
                }
            }

            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                boolean z = false;
                int findFirstVisibleItemPosition = DialogsActivity.this.layoutManager.findFirstVisibleItemPosition();
                int abs = Math.abs(DialogsActivity.this.layoutManager.findLastVisibleItemPosition() - findFirstVisibleItemPosition) + 1;
                int itemCount = recyclerView.getAdapter().getItemCount();
                if (!DialogsActivity.this.searching || !DialogsActivity.this.searchWas) {
                    if (abs > 0 && DialogsActivity.this.layoutManager.findLastVisibleItemPosition() >= DialogsActivity.this.getDialogsArray().size() - 10) {
                        boolean z2 = !MessagesController.getInstance().dialogsEndReached;
                        if (z2 || !MessagesController.getInstance().serverDialogsEndReached) {
                            MessagesController.getInstance().loadDialogs(-1, 100, z2);
                        }
                    }
                    if (DialogsActivity.this.floatingButton.getVisibility() != 8) {
                        boolean z3;
                        int i3;
                        View childAt = recyclerView.getChildAt(0);
                        abs = childAt != null ? childAt.getTop() : 0;
                        if (DialogsActivity.this.prevPosition == findFirstVisibleItemPosition) {
                            int access$3500 = DialogsActivity.this.prevTop - abs;
                            z3 = abs < DialogsActivity.this.prevTop;
                            if (Math.abs(access$3500) > 1) {
                                i3 = 1;
                            }
                        } else {
                            if (findFirstVisibleItemPosition > DialogsActivity.this.prevPosition) {
                                z = true;
                            }
                            z3 = z;
                            i3 = 1;
                        }
                        if (i3 != 0 && DialogsActivity.this.scrollUpdated) {
                            DialogsActivity.this.hideFloatingButton(z3);
                        }
                        DialogsActivity.this.prevPosition = findFirstVisibleItemPosition;
                        DialogsActivity.this.prevTop = abs;
                        DialogsActivity.this.scrollUpdated = true;
                    }
                } else if (abs > 0 && DialogsActivity.this.layoutManager.findLastVisibleItemPosition() == itemCount - 1 && !DialogsActivity.this.dialogsSearchAdapter.isMessagesSearchEndReached()) {
                    DialogsActivity.this.dialogsSearchAdapter.loadMoreSearchMessages();
                }
            }
        });
        if (this.searchString == null) {
            this.dialogsAdapter = new DialogsAdapter(context, this.dialogsType, this.onlySelect);
            if (AndroidUtilities.isTablet() && this.openedDialogId != 0) {
                this.dialogsAdapter.setOpenedDialogId(this.openedDialogId);
            }
            this.listView.setAdapter(this.dialogsAdapter);
        }
        int i = 0;
        if (this.searchString != null) {
            i = 2;
        } else if (!this.onlySelect) {
            i = 1;
        }
        this.dialogsSearchAdapter = new DialogsSearchAdapter(context, i, this.dialogsType);
        this.dialogsSearchAdapter.setDelegate(new DialogsSearchAdapterDelegate() {
            public void didPressedOnSubDialog(long j) {
                if (!DialogsActivity.this.onlySelect) {
                    int i = (int) j;
                    Bundle bundle = new Bundle();
                    if (i > 0) {
                        bundle.putInt("user_id", i);
                    } else {
                        bundle.putInt("chat_id", -i);
                    }
                    if (DialogsActivity.this.actionBar != null) {
                        DialogsActivity.this.actionBar.closeSearchField();
                    }
                    if (AndroidUtilities.isTablet() && DialogsActivity.this.dialogsAdapter != null) {
                        DialogsActivity.this.dialogsAdapter.setOpenedDialogId(DialogsActivity.this.openedDialogId = j);
                        DialogsActivity.this.updateVisibleRows(512);
                    }
                    if (DialogsActivity.this.searchString != null) {
                        if (MessagesController.checkCanOpenChat(bundle, DialogsActivity.this)) {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                            DialogsActivity.this.presentFragment(new ChatActivity(bundle));
                        }
                    } else if (MessagesController.checkCanOpenChat(bundle, DialogsActivity.this)) {
                        DialogsActivity.this.presentFragment(new ChatActivity(bundle));
                    }
                } else if (DialogsActivity.this.dialogsAdapter.hasSelectedDialogs()) {
                    DialogsActivity.this.dialogsAdapter.addOrRemoveSelectedDialog(j, null);
                    DialogsActivity.this.updateSelectedCount();
                    DialogsActivity.this.actionBar.closeSearchField();
                } else {
                    DialogsActivity.this.didSelectResult(j, true, false);
                }
            }

            public void needRemoveHint(final int i) {
                if (DialogsActivity.this.getParentActivity() != null && MessagesController.getInstance().getUser(Integer.valueOf(i)) != null) {
                    Builder builder = new Builder(DialogsActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setMessage(LocaleController.formatString("ChatHintsDelete", R.string.ChatHintsDelete, new Object[]{ContactsController.formatName(r0.first_name, r0.last_name)}));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SearchQuery.removePeer(i);
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    DialogsActivity.this.showDialog(builder.create());
                }
            }

            public void searchStateChanged(boolean z) {
                if (!DialogsActivity.this.searching || !DialogsActivity.this.searchWas || DialogsActivity.this.searchEmptyView == null) {
                    return;
                }
                if (z) {
                    DialogsActivity.this.searchEmptyView.showProgress();
                } else {
                    DialogsActivity.this.searchEmptyView.showTextView();
                }
            }
        });
        if (MessagesController.getInstance().loadingDialogs && MessagesController.getInstance().dialogs.isEmpty()) {
            this.searchEmptyView.setVisibility(8);
            this.listView.setEmptyView(this.progressView);
        } else {
            this.searchEmptyView.setVisibility(8);
            this.progressView.setVisibility(8);
            this.listView.setEmptyView(null);
        }
        if (this.searchString != null) {
            this.actionBar.openSearchField(this.searchString);
        }
        if (!this.onlySelect) {
            SizeNotifierFrameLayout sizeNotifierFrameLayout = this.contentView;
            View fragmentContextView = new FragmentContextView(context, this, true);
            this.fragmentLocationContextView = fragmentContextView;
            sizeNotifierFrameLayout.addView(fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, BitmapDescriptorFactory.HUE_RED, -36.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            sizeNotifierFrameLayout = this.contentView;
            fragmentContextView = new FragmentContextView(context, this, false);
            this.fragmentContextView = fragmentContextView;
            sizeNotifierFrameLayout.addView(fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, BitmapDescriptorFactory.HUE_RED, -36.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.fragmentContextView.setAdditionalContextView(this.fragmentLocationContextView);
            this.fragmentLocationContextView.setAdditionalContextView(this.fragmentContextView);
        } else if (this.dialogsType == 3 && this.selectAlertString == null) {
            if (this.commentView != null) {
                this.commentView.onDestroy();
            }
            this.commentView = new ChatActivityEnterView(getParentActivity(), this.contentView, null, false, 0);
            this.commentView.setAllowStickersAndGifs(false, false);
            this.commentView.setForceShowSendButton(true, false);
            this.commentView.setVisibility(8);
            this.contentView.addView(this.commentView, LayoutHelper.createFrame(-1, -2, 83));
            this.commentView.setDelegate(new ChatActivityEnterViewDelegate() {
                public void didPressedAttachButton() {
                }

                public void needChangeVideoPreviewState(int i, float f) {
                }

                public void needSendTyping() {
                }

                public void needShowMediaBanHint() {
                }

                public void needStartRecordAudio(int i) {
                }

                public void needStartRecordVideo(int i) {
                }

                public void onAttachButtonHidden() {
                }

                public void onAttachButtonShow() {
                }

                public void onMessageEditEnd(boolean z) {
                }

                public void onMessageSend(CharSequence charSequence) {
                    if (DialogsActivity.this.delegate != null) {
                        ArrayList selectedDialogs = DialogsActivity.this.dialogsAdapter.getSelectedDialogs();
                        if (!selectedDialogs.isEmpty()) {
                            DialogsActivity.this.delegate.didSelectDialogs(DialogsActivity.this, selectedDialogs, charSequence, false);
                        }
                    }
                }

                public void onPreAudioVideoRecord() {
                }

                public void onStickersTab(boolean z) {
                }

                public void onSwitchRecordMode(boolean z) {
                }

                public void onTextChanged(CharSequence charSequence, boolean z) {
                }

                public void onWindowSizeChanged(int i) {
                }
            });
        }
        this.slsHotPostActivity = null;
        this.slsSearchActivity = null;
        this.newsListActivity = null;
        this.electionActivity = null;
        this.debugActivity = null;
        this.bottomBar = (BottomBar) LayoutInflater.from(getParentActivity()).inflate(R.layout.bottom_bar, null);
        if (this.isSelectMode) {
            this.bottomBar.setVisibility(8);
            this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
            this.actionBar.getBackButton().setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MessagesController.getInstance().unSelectAllDialogs();
                    DialogsActivity.this.finishFragment();
                }
            });
        } else {
            this.bottomBar.setVisibility(0);
        }
        this.contentView.addView(this.bottomBar, LayoutHelper.createFrame(-1, 50.0f, 80, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.bottomBar.m14252a(4);
        if (!C3791b.q(getParentActivity())) {
            this.bottomBar.m14259d((int) R.id.tab_news).setVisibility(8);
        }
        if (!C3791b.n(getParentActivity())) {
            this.bottomBar.m14259d((int) R.id.tab_hottest).setVisibility(8);
        }
        if (!C3791b.o(getParentActivity())) {
            this.bottomBar.m14259d((int) R.id.tab_search).setVisibility(8);
        }
        if (C3791b.p(getParentActivity())) {
            this.bottomBar.m14259d((int) R.id.tab_debug).setVisibility(0);
        } else {
            this.bottomBar.m14259d((int) R.id.tab_debug).setVisibility(8);
        }
        this.bottomBar.setActiveTabColor(C0235a.c(getParentActivity(), R.color.white));
        this.bottomBar.setInActiveTabColor(C0235a.c(getParentActivity(), R.color.gray));
        this.bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            public void onTabSelected(int i) {
                DialogsActivity.this.onBottomBarTabSelected(i);
            }
        });
        C0424l.a(getParentActivity()).a(this.mMessageReceiver, new IntentFilter("ACTION_SWITCH_TAB"));
        C0424l.a(getParentActivity()).a(this.mMessageReceiver, new IntentFilter("ACTION_FAVE_CHANGED"));
        C0424l.a(getParentActivity()).a(this.mMessageReceiver, new IntentFilter("ACTION_REBUILD_ALL"));
        C2885i.a(1);
        return this.contentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.dialogsNeedReload) {
            if (this.dialogsAdapter != null) {
                if (this.dialogsAdapter.isDataSetChanged()) {
                    this.dialogsAdapter.notifyDataSetChanged();
                } else {
                    unreadCount();
                    updateVisibleRows(2048);
                }
            }
            if (this.dialogsSearchAdapter != null) {
                this.dialogsSearchAdapter.notifyDataSetChanged();
            }
            if (this.listView == null) {
                return;
            }
            if (MessagesController.getInstance().loadingDialogs && MessagesController.getInstance().dialogs.isEmpty()) {
                this.searchEmptyView.setVisibility(8);
                this.listView.setEmptyView(this.progressView);
                return;
            }
            try {
                this.progressView.setVisibility(8);
                if (this.searching && this.searchWas) {
                    this.listView.setEmptyView(this.searchEmptyView);
                    return;
                }
                this.searchEmptyView.setVisibility(8);
                this.listView.setEmptyView(null);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        } else if (i == NotificationCenter.emojiDidLoaded) {
            updateVisibleRows(0);
        } else if (i == NotificationCenter.updateInterfaces) {
            updateVisibleRows(((Integer) objArr[0]).intValue());
        } else if (i == NotificationCenter.appDidLogout) {
            dialogsLoaded = false;
        } else if (i == NotificationCenter.encryptedChatUpdated) {
            updateVisibleRows(0);
        } else if (i == NotificationCenter.contactsDidLoaded) {
            updateVisibleRows(0);
        } else if (i == NotificationCenter.openedChatChanged) {
            if (this.dialogsType == 0 && AndroidUtilities.isTablet()) {
                boolean booleanValue = ((Boolean) objArr[1]).booleanValue();
                long longValue = ((Long) objArr[0]).longValue();
                if (!booleanValue) {
                    this.openedDialogId = longValue;
                } else if (longValue == this.openedDialogId) {
                    this.openedDialogId = 0;
                }
                if (this.dialogsAdapter != null) {
                    this.dialogsAdapter.setOpenedDialogId(this.openedDialogId);
                }
                updateVisibleRows(512);
            }
        } else if (i == NotificationCenter.notificationsSettingsUpdated) {
            updateVisibleRows(0);
        } else if (i == NotificationCenter.messageReceivedByAck || i == NotificationCenter.messageReceivedByServer || i == NotificationCenter.messageSendError) {
            updateVisibleRows(4096);
        } else if (i == NotificationCenter.didSetPasscode) {
            updatePasscodeButton();
        } else if (i == NotificationCenter.needReloadRecentDialogsSearch) {
            if (this.dialogsSearchAdapter != null) {
                this.dialogsSearchAdapter.loadRecentSearch();
            }
        } else if (i == NotificationCenter.didLoadedReplyMessages) {
            updateVisibleRows(0);
        } else if (i == NotificationCenter.reloadHints) {
            if (this.dialogsSearchAdapter != null) {
                this.dialogsSearchAdapter.notifyDataSetChanged();
            }
        } else if (i == NotificationCenter.refreshTabs) {
            Log.d("alireza", "refreshn tab : " + ((Integer) objArr[0]).intValue());
            if (this.actionBar != null) {
                if (C3791b.z(getParentActivity()) == null || TextUtils.isEmpty(C3791b.z(getParentActivity()))) {
                    this.actionBar.setSubtitle(TtmlNode.ANONYMOUS_REGION_ID);
                } else {
                    this.actionBar.setSubtitle(C3791b.z(getParentActivity()));
                }
            }
            updateTabs();
            MessagesController.getInstance().sortDialogs(null);
            hideShowTabs(((Integer) objArr[0]).intValue());
            unreadCount();
        }
    }

    public void fillAllUnreadCounts() {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        if (!sharedPreferences.getBoolean("hideTabs", false) && !sharedPreferences.getBoolean("hideTabsCounters", false)) {
            boolean z = sharedPreferences.getBoolean("tabsCountersCountChats", false);
            boolean z2 = sharedPreferences.getBoolean("tabsCountersCountNotMuted", false);
            this.countUsers = 0;
            this.countGroups = 0;
            this.countGroupsAll = 0;
            this.countChannels = 0;
            this.countMegaGroups = 0;
            this.countBots = 0;
            this.countFavs = 0;
            this.countHidden = 0;
            this.countAll = 0;
            this.countUnread = 0;
            this.countAds = 0;
            this.allMutedUsers = true;
            this.allMutedGroups = true;
            this.allMutedGroupsAll = true;
            this.allMutedChannels = true;
            this.allMutedMegaGroups = true;
            this.allMutedBots = true;
            this.allMutedFavs = true;
            this.allMutedHidden = true;
            this.allMutedAll = true;
            this.allMutedUnread = true;
            this.allMutedAds = true;
            int A = C3791b.A(ApplicationLoader.applicationContext);
            List b = C3791b.b(ApplicationLoader.applicationContext);
            List<Integer> aa = C3791b.aa(ApplicationLoader.applicationContext);
            boolean x = C3791b.x(ApplicationLoader.applicationContext);
            ArrayList favouriteIds = Favourite.getFavouriteIds();
            Iterator it = MessagesController.getInstance().dialogsAll.iterator();
            while (it.hasNext()) {
                int i;
                int i2;
                int i3;
                boolean contains;
                Chat chat;
                User user;
                TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) it.next();
                int i4 = 0;
                boolean isDialogMuted = MessagesController.getInstance().isDialogMuted(tLRPC$TL_dialog.id);
                if (!(isDialogMuted && z2)) {
                    i = tLRPC$TL_dialog.unread_count;
                    if (i > 0) {
                        if (!z) {
                            i4 = 0 + i;
                        } else if (i > 0) {
                            i4 = 1;
                        }
                        if (i > 0 && !isDialogMuted) {
                            i2 = 0;
                            i3 = i4;
                            if ((A != 2 || tLRPC$TL_dialog.unread_count > 0) && (!(A == 4 && MessagesController.getInstance().isDialogMuted(tLRPC$TL_dialog.id)) && (A != 3 || MessagesController.getInstance().isDialogMuted(tLRPC$TL_dialog.id)))) {
                                contains = b.contains(Long.valueOf(tLRPC$TL_dialog.id));
                                if (contains) {
                                    if (x) {
                                        this.countHidden += i3;
                                        this.allMutedHidden &= i2;
                                    }
                                }
                                if (x || !contains) {
                                    this.countAll += i3;
                                    this.allMutedAll &= i2;
                                }
                                if (tLRPC$TL_dialog.unread_count > 0) {
                                    this.countUnread += i3;
                                    this.allMutedUnread &= i2;
                                }
                                i4 = (int) (tLRPC$TL_dialog.id >> 32);
                                i = (int) tLRPC$TL_dialog.id;
                                if (((int) tLRPC$TL_dialog.id) != 0 && i4 != 1 && (tLRPC$TL_dialog instanceof TLRPC$TL_dialog)) {
                                    if (tLRPC$TL_dialog.id >= 0) {
                                        chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) tLRPC$TL_dialog.id)));
                                        if (chat != null) {
                                            if (!chat.megagroup) {
                                                this.countMegaGroups += i3;
                                                this.countGroupsAll += i3;
                                                this.allMutedMegaGroups &= i2;
                                                this.allMutedGroupsAll &= i2;
                                            } else if (ChatObject.isChannel(chat)) {
                                                this.countChannels += i3;
                                                this.allMutedChannels &= i2;
                                                for (Integer intValue : aa) {
                                                    if (((long) intValue.intValue()) == Math.abs(tLRPC$TL_dialog.id)) {
                                                        this.countAds += i3;
                                                        this.allMutedAds &= i2;
                                                        break;
                                                    }
                                                }
                                            } else {
                                                this.countGroups += i3;
                                                this.countGroupsAll += i3;
                                                this.allMutedGroups &= i2;
                                                this.allMutedGroupsAll &= i2;
                                            }
                                        }
                                    } else {
                                        user = MessagesController.getInstance().getUser(Integer.valueOf((int) tLRPC$TL_dialog.id));
                                        if (user != null) {
                                            this.countGroups += i3;
                                            this.countGroupsAll += i3;
                                            this.allMutedGroups &= i2;
                                            this.allMutedGroupsAll &= i2;
                                        } else if (user.bot) {
                                            this.countUsers += i3;
                                            this.allMutedUsers &= i2;
                                        } else {
                                            this.countBots += i3;
                                            this.allMutedBots &= i2;
                                        }
                                    }
                                }
                                if (favouriteIds.contains(Long.valueOf(tLRPC$TL_dialog.id))) {
                                    this.countFavs += i3;
                                    this.allMutedFavs &= i2;
                                }
                            }
                        }
                    }
                }
                i2 = 1;
                i3 = i4;
                contains = b.contains(Long.valueOf(tLRPC$TL_dialog.id));
                if (contains) {
                    if (x) {
                        this.countHidden += i3;
                        this.allMutedHidden &= i2;
                    }
                }
                this.countAll += i3;
                this.allMutedAll &= i2;
                if (tLRPC$TL_dialog.unread_count > 0) {
                    this.countUnread += i3;
                    this.allMutedUnread &= i2;
                }
                i4 = (int) (tLRPC$TL_dialog.id >> 32);
                i = (int) tLRPC$TL_dialog.id;
                if (tLRPC$TL_dialog.id >= 0) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf((int) tLRPC$TL_dialog.id));
                    if (user != null) {
                        this.countGroups += i3;
                        this.countGroupsAll += i3;
                        this.allMutedGroups &= i2;
                        this.allMutedGroupsAll &= i2;
                    } else if (user.bot) {
                        this.countUsers += i3;
                        this.allMutedUsers &= i2;
                    } else {
                        this.countBots += i3;
                        this.allMutedBots &= i2;
                    }
                } else {
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) tLRPC$TL_dialog.id)));
                    if (chat != null) {
                        if (!chat.megagroup) {
                            if (ChatObject.isChannel(chat)) {
                                this.countChannels += i3;
                                this.allMutedChannels &= i2;
                                while (r14.hasNext()) {
                                    if (((long) intValue.intValue()) == Math.abs(tLRPC$TL_dialog.id)) {
                                        this.countAds += i3;
                                        this.allMutedAds &= i2;
                                        break;
                                    }
                                }
                            }
                            this.countGroups += i3;
                            this.countGroupsAll += i3;
                            this.allMutedGroups &= i2;
                            this.allMutedGroupsAll &= i2;
                        } else {
                            this.countMegaGroups += i3;
                            this.countGroupsAll += i3;
                            this.allMutedMegaGroups &= i2;
                            this.allMutedGroupsAll &= i2;
                        }
                    }
                }
                if (favouriteIds.contains(Long.valueOf(tLRPC$TL_dialog.id))) {
                    this.countFavs += i3;
                    this.allMutedFavs &= i2;
                }
            }
        }
    }

    public C2497d getResponseReceiver() {
        return this.responseReceiver;
    }

    public ThemeDescription[] getThemeDescriptions() {
        AnonymousClass20 anonymousClass20 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
                int i2 = 0;
                int childCount = DialogsActivity.this.listView.getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    View childAt = DialogsActivity.this.listView.getChildAt(i3);
                    if (childAt instanceof ProfileSearchCell) {
                        ((ProfileSearchCell) childAt).update(0);
                    } else if (childAt instanceof DialogCell) {
                        ((DialogCell) childAt).update(0);
                    }
                }
                RecyclerListView innerListView = DialogsActivity.this.dialogsSearchAdapter.getInnerListView();
                if (innerListView != null) {
                    childCount = innerListView.getChildCount();
                    while (i2 < childCount) {
                        childAt = innerListView.getChildAt(i2);
                        if (childAt instanceof HintDialogCell) {
                            ((HintDialogCell) childAt).update();
                        }
                        i2++;
                    }
                }
            }
        };
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[TsExtractor.TS_STREAM_TYPE_SPLICE_INFO];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch);
        themeDescriptionArr[7] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[10] = new ThemeDescription(this.searchEmptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        themeDescriptionArr[11] = new ThemeDescription(this.searchEmptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{DialogsEmptyCell.class}, new String[]{"emptyTextView1"}, null, null, null, Theme.key_emptyListPlaceholder);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{DialogsEmptyCell.class}, new String[]{"emptyTextView2"}, null, null, null, Theme.key_emptyListPlaceholder);
        themeDescriptionArr[14] = new ThemeDescription(this.floatingButton, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chats_actionIcon);
        themeDescriptionArr[15] = new ThemeDescription(this.floatingButton, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chats_actionBackground);
        themeDescriptionArr[16] = new ThemeDescription(this.floatingButton, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_chats_actionPressedBackground);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class, ProfileSearchCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        themeDescriptionArr[18] = new ThemeDescription(null, 0, null, null, null, anonymousClass20, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[19] = new ThemeDescription(null, 0, null, null, null, anonymousClass20, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[20] = new ThemeDescription(null, 0, null, null, null, anonymousClass20, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[21] = new ThemeDescription(null, 0, null, null, null, anonymousClass20, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[22] = new ThemeDescription(null, 0, null, null, null, anonymousClass20, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[23] = new ThemeDescription(null, 0, null, null, null, anonymousClass20, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[24] = new ThemeDescription(null, 0, null, null, null, anonymousClass20, Theme.key_avatar_backgroundPink);
        themeDescriptionArr[25] = new ThemeDescription(null, 0, null, null, null, anonymousClass20, Theme.key_avatar_backgroundSaved);
        themeDescriptionArr[26] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_countPaint, null, null, Theme.key_chats_unreadCounter);
        themeDescriptionArr[27] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_countGrayPaint, null, null, Theme.key_chats_unreadCounterMuted);
        themeDescriptionArr[28] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_countTextPaint, null, null, Theme.key_chats_unreadCounterText);
        themeDescriptionArr[29] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class, ProfileSearchCell.class}, Theme.dialogs_namePaint, null, null, Theme.key_chats_name);
        themeDescriptionArr[30] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class, ProfileSearchCell.class}, Theme.dialogs_nameEncryptedPaint, null, null, Theme.key_chats_secretName);
        themeDescriptionArr[31] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class, ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_lockDrawable}, null, Theme.key_chats_secretIcon);
        themeDescriptionArr[32] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class, ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_groupDrawable, Theme.dialogs_broadcastDrawable, Theme.dialogs_botDrawable}, null, Theme.key_chats_nameIcon);
        themeDescriptionArr[33] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, null, new Drawable[]{Theme.dialogs_pinnedDrawable}, null, Theme.key_chats_pinnedIcon);
        themeDescriptionArr[34] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_messagePaint, null, null, Theme.key_chats_message);
        themeDescriptionArr[35] = new ThemeDescription(null, 0, null, null, null, anonymousClass20, Theme.key_chats_nameMessage);
        themeDescriptionArr[36] = new ThemeDescription(null, 0, null, null, null, anonymousClass20, Theme.key_chats_draft);
        themeDescriptionArr[37] = new ThemeDescription(null, 0, null, null, null, anonymousClass20, Theme.key_chats_attachMessage);
        themeDescriptionArr[38] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_messagePrintingPaint, null, null, Theme.key_chats_actionMessage);
        themeDescriptionArr[39] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_timePaint, null, null, Theme.key_chats_date);
        themeDescriptionArr[40] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_pinnedPaint, null, null, Theme.key_chats_pinnedOverlay);
        themeDescriptionArr[41] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_tabletSeletedPaint, null, null, Theme.key_chats_tabletSelectedOverlay);
        themeDescriptionArr[42] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, null, new Drawable[]{Theme.dialogs_checkDrawable, Theme.dialogs_halfCheckDrawable}, null, Theme.key_chats_sentCheck);
        themeDescriptionArr[43] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, null, new Drawable[]{Theme.dialogs_clockDrawable}, null, Theme.key_chats_sentClock);
        themeDescriptionArr[44] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_errorPaint, null, null, Theme.key_chats_sentError);
        themeDescriptionArr[45] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, null, new Drawable[]{Theme.dialogs_errorDrawable}, null, Theme.key_chats_sentErrorIcon);
        themeDescriptionArr[46] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class, ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_verifiedCheckDrawable}, null, Theme.key_chats_verifiedCheck);
        themeDescriptionArr[47] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class, ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_verifiedDrawable}, null, Theme.key_chats_verifiedBackground);
        themeDescriptionArr[48] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, null, new Drawable[]{Theme.dialogs_muteDrawable}, null, Theme.key_chats_muteIcon);
        themeDescriptionArr[49] = new ThemeDescription(this.sideMenu, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_chats_menuBackground);
        themeDescriptionArr[50] = new ThemeDescription(this.sideMenu, 0, new Class[]{DrawerProfileCell.class}, null, null, null, Theme.key_chats_menuName);
        themeDescriptionArr[51] = new ThemeDescription(this.sideMenu, 0, new Class[]{DrawerProfileCell.class}, null, null, null, Theme.key_chats_menuPhone);
        themeDescriptionArr[52] = new ThemeDescription(this.sideMenu, 0, new Class[]{DrawerProfileCell.class}, null, null, null, Theme.key_chats_menuPhoneCats);
        themeDescriptionArr[53] = new ThemeDescription(this.sideMenu, 0, new Class[]{DrawerProfileCell.class}, null, null, null, Theme.key_chats_menuCloudBackgroundCats);
        themeDescriptionArr[54] = new ThemeDescription(this.sideMenu, 0, new Class[]{DrawerProfileCell.class}, new String[]{"cloudDrawable"}, null, null, null, Theme.key_chats_menuCloud);
        themeDescriptionArr[55] = new ThemeDescription(this.sideMenu, 0, new Class[]{DrawerProfileCell.class}, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[56] = new ThemeDescription(this.sideMenu, 0, new Class[]{DrawerProfileCell.class}, null, null, null, Theme.key_chats_menuTopShadow);
        themeDescriptionArr[57] = new ThemeDescription(this.sideMenu, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{DrawerActionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chats_menuItemIcon);
        themeDescriptionArr[58] = new ThemeDescription(this.sideMenu, 0, new Class[]{DrawerActionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chats_menuItemText);
        themeDescriptionArr[59] = new ThemeDescription(this.sideMenu, 0, new Class[]{DividerCell.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[60] = new ThemeDescription(this.listView, 0, new Class[]{LoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[61] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_offlinePaint, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[62] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_onlinePaint, null, null, Theme.key_windowBackgroundWhiteBlueText3);
        themeDescriptionArr[63] = new ThemeDescription(this.listView, 0, new Class[]{GraySectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[64] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GraySectionCell.class}, null, null, null, Theme.key_graySection);
        themeDescriptionArr[65] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{HashtagSearchCell.class}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[66] = new ThemeDescription(this.progressView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[67] = new ThemeDescription(this.dialogsSearchAdapter.getInnerListView(), 0, new Class[]{HintDialogCell.class}, Theme.dialogs_countPaint, null, null, Theme.key_chats_unreadCounter);
        themeDescriptionArr[68] = new ThemeDescription(this.dialogsSearchAdapter.getInnerListView(), 0, new Class[]{HintDialogCell.class}, Theme.dialogs_countGrayPaint, null, null, Theme.key_chats_unreadCounterMuted);
        themeDescriptionArr[69] = new ThemeDescription(this.dialogsSearchAdapter.getInnerListView(), 0, new Class[]{HintDialogCell.class}, Theme.dialogs_countTextPaint, null, null, Theme.key_chats_unreadCounterText);
        themeDescriptionArr[70] = new ThemeDescription(this.dialogsSearchAdapter.getInnerListView(), 0, new Class[]{HintDialogCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[71] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_inappPlayerBackground);
        themeDescriptionArr[72] = new ThemeDescription(this.fragmentContextView, 0, new Class[]{FragmentContextView.class}, new String[]{"playButton"}, null, null, null, Theme.key_inappPlayerPlayPause);
        themeDescriptionArr[73] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{FragmentContextView.class}, new String[]{"titleTextView"}, null, null, null, Theme.key_inappPlayerTitle);
        themeDescriptionArr[74] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_inappPlayerPerformer);
        themeDescriptionArr[75] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"closeButton"}, null, null, null, Theme.key_inappPlayerClose);
        themeDescriptionArr[76] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_returnToCallBackground);
        themeDescriptionArr[77] = new ThemeDescription(this.fragmentContextView, 0, new Class[]{FragmentContextView.class}, new String[]{"titleTextView"}, null, null, null, Theme.key_returnToCallText);
        themeDescriptionArr[78] = new ThemeDescription(this.fragmentLocationContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_inappPlayerBackground);
        themeDescriptionArr[79] = new ThemeDescription(this.fragmentLocationContextView, 0, new Class[]{FragmentContextView.class}, new String[]{"playButton"}, null, null, null, Theme.key_inappPlayerPlayPause);
        themeDescriptionArr[80] = new ThemeDescription(this.fragmentLocationContextView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{FragmentContextView.class}, new String[]{"titleTextView"}, null, null, null, Theme.key_inappPlayerTitle);
        themeDescriptionArr[81] = new ThemeDescription(this.fragmentLocationContextView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_inappPlayerPerformer);
        themeDescriptionArr[82] = new ThemeDescription(this.fragmentLocationContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"closeButton"}, null, null, null, Theme.key_inappPlayerClose);
        themeDescriptionArr[83] = new ThemeDescription(this.fragmentLocationContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_returnToCallBackground);
        themeDescriptionArr[84] = new ThemeDescription(this.fragmentLocationContextView, 0, new Class[]{FragmentContextView.class}, new String[]{"titleTextView"}, null, null, null, Theme.key_returnToCallText);
        themeDescriptionArr[85] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogBackground);
        themeDescriptionArr[86] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogBackgroundGray);
        themeDescriptionArr[87] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextBlack);
        themeDescriptionArr[88] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextLink);
        themeDescriptionArr[89] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogLinkSelection);
        themeDescriptionArr[90] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextBlue);
        themeDescriptionArr[91] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextBlue2);
        themeDescriptionArr[92] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextBlue3);
        themeDescriptionArr[93] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextBlue4);
        themeDescriptionArr[94] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextRed);
        themeDescriptionArr[95] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextGray);
        themeDescriptionArr[96] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextGray2);
        themeDescriptionArr[97] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextGray3);
        themeDescriptionArr[98] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextGray4);
        themeDescriptionArr[99] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogIcon);
        themeDescriptionArr[100] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogTextHint);
        themeDescriptionArr[101] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogInputField);
        themeDescriptionArr[102] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogInputFieldActivated);
        themeDescriptionArr[103] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogCheckboxSquareBackground);
        themeDescriptionArr[104] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogCheckboxSquareCheck);
        themeDescriptionArr[105] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogCheckboxSquareUnchecked);
        themeDescriptionArr[106] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogCheckboxSquareDisabled);
        themeDescriptionArr[107] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogRadioBackground);
        themeDescriptionArr[108] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogRadioBackgroundChecked);
        themeDescriptionArr[109] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogProgressCircle);
        themeDescriptionArr[110] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogButton);
        themeDescriptionArr[111] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogButtonSelector);
        themeDescriptionArr[112] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogScrollGlow);
        themeDescriptionArr[113] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogRoundCheckBox);
        themeDescriptionArr[114] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogRoundCheckBoxCheck);
        themeDescriptionArr[115] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogBadgeBackground);
        themeDescriptionArr[116] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogBadgeText);
        themeDescriptionArr[117] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogLineProgress);
        themeDescriptionArr[118] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogLineProgressBackground);
        themeDescriptionArr[119] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_dialogGrayLine);
        themeDescriptionArr[120] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_actionBar);
        themeDescriptionArr[121] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_actionBarSelector);
        themeDescriptionArr[122] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_actionBarTitle);
        themeDescriptionArr[123] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_actionBarTop);
        themeDescriptionArr[124] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_actionBarSubtitle);
        themeDescriptionArr[125] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_actionBarItems);
        themeDescriptionArr[126] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_background);
        themeDescriptionArr[127] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_time);
        themeDescriptionArr[128] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_progressBackground);
        themeDescriptionArr[129] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_progress);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_HDMV_DTS] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_placeholder);
        themeDescriptionArr[131] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_placeholderBackground);
        themeDescriptionArr[132] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_button);
        themeDescriptionArr[133] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_buttonActive);
        return themeDescriptionArr;
    }

    public C2945c handleChatPreview(long j, View view) {
        int i = (int) j;
        Bundle bundle = new Bundle();
        if (i > 0) {
            bundle.putInt("user_id", i);
        } else {
            bundle.putInt("chat_id", -i);
        }
        ChatActivity chatActivity = new ChatActivity(bundle);
        chatActivity.actionBar = new ActionBar(getParentActivity());
        chatActivity.parentLayout = this.parentLayout;
        View createView = chatActivity.createView(getParentActivity());
        chatActivity.onFragmentCreate();
        return new C2935a(getParentActivity()).a(false).a(createView).a(new View[]{view}).a();
    }

    public void hideSelectedDialogs() {
        ArrayList selectedDialogs = MessagesController.getInstance().getSelectedDialogs();
        for (int i = 0; i < selectedDialogs.size(); i++) {
            C3791b.a(ApplicationLoader.applicationContext, ((TLRPC$TL_dialog) selectedDialogs.get(i)).id);
        }
    }

    public boolean isMainDialogList() {
        return this.delegate == null && this.searchString == null;
    }

    public void onBottomBarTabSelected(int i) {
        if (this.slsHotPostActivity != null) {
            this.slsHotPostActivity.setVisibility(8);
        }
        if (this.slsSearchActivity != null) {
            this.slsSearchActivity.setVisibility(8);
        }
        this.listView.setVisibility(8);
        if (this.newsListActivity != null) {
            this.newsListActivity.setVisibility(8);
        }
        if (this.electionActivity != null) {
            this.electionActivity.setVisibility(8);
        }
        if (this.debugActivity != null) {
            this.debugActivity.setVisibility(8);
        }
        switch (i) {
            case R.id.tab_news:
            case R.id.tab_search:
            case R.id.tab_hottest:
                this.actionBar.closeSearchField();
                this.parentLayout.getDrawerLayoutContainer().setAllowOpenDrawer(false, false);
                try {
                    this.actionBar.getBackButton().setVisibility(8);
                    this.menu.getItem(0).setVisibility(8);
                    break;
                } catch (Exception e) {
                    break;
                }
            case R.id.tab_home:
                this.parentLayout.getDrawerLayoutContainer().setAllowOpenDrawer(true, false);
                try {
                    this.actionBar.getBackButton().setVisibility(0);
                    this.menu.getItem(0).setVisibility(0);
                    break;
                } catch (Exception e2) {
                    break;
                }
        }
        switch (i) {
            case R.id.tab_debug:
                if (this.debugActivity == null) {
                    this.debugActivity = new C2539c(getParentActivity());
                    this.contentView.addView(this.debugActivity, LayoutHelper.createFrame(-1, -1.0f, 48, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) this.bottomBarHeight));
                }
                this.debugActivity.setVisibility(0);
                C2874e.b().a("FRG_USER");
                this.floatingButton.setVisibility(8);
                this.actionBar.actionBarFontSize = 12;
                C3792d.b("Debug View");
                hideActionbarItems();
                return;
            case R.id.tab_news:
                if (this.newsListActivity == null) {
                    this.newsListActivity = new C3752d(getParentActivity());
                    this.contentView.addView(this.newsListActivity, LayoutHelper.createFrame(-1, -1.0f, 0, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) this.bottomBarHeight));
                }
                this.newsListActivity.setVisibility(0);
                C2874e.b().a("FRG_NEWS_LIST");
                this.actionBar.setTitle(getParentActivity().getString(R.string.hot_news));
                this.floatingButton.setVisibility(8);
                C3792d.b("News TAB");
                hideActionbarItems();
                return;
            case R.id.tab_search:
                if (this.slsSearchActivity == null) {
                    this.slsSearchActivity = new C2622l(getParentActivity(), 2, 2, this.actionBar);
                    this.contentView.addView(this.slsSearchActivity, LayoutHelper.createFrame(-1, -1.0f, 48, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) this.bottomBarHeight));
                }
                this.slsSearchActivity.setVisibility(0);
                C2874e.b().a("FRG_SEARCH");
                this.floatingButton.setVisibility(8);
                this.actionBar.actionBarFontSize = 12;
                C3792d.b("Search TAB");
                hideActionbarItems();
                this.actionBar.setTitle(this.actionBar.searchTabTitle);
                return;
            case R.id.tab_hottest:
                if (this.slsHotPostActivity == null) {
                    this.slsHotPostActivity = new C2622l(getParentActivity(), 1, 1, this.actionBar);
                    this.contentView.addView(this.slsHotPostActivity, LayoutHelper.createFrame(-1, -1.0f, 48, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) this.bottomBarHeight));
                }
                this.slsHotPostActivity.setVisibility(0);
                C2874e.b().a("FRG_HOT");
                this.actionBar.setTitle(getParentActivity().getString(R.string.hot_posts));
                this.actionBar.actionBarFontSize = -1;
                this.floatingButton.setVisibility(8);
                C3792d.b("HOT TAB");
                hideActionbarItems();
                return;
            case R.id.tab_home:
                this.listView.setVisibility(0);
                C2874e.b().a("FRG_HOME");
                if (!this.isSelectMode) {
                    this.actionBar.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                }
                this.actionBar.actionBarFontSize = -1;
                this.actionBar.setVisibility(0);
                this.floatingButton.setVisibility(0);
                C3792d.b("Dialog TAB");
                addActionbarItems(getParentActivity());
                this.actionBar.getTitleTextView().setGravity(3);
                return;
            default:
                return;
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (!this.onlySelect && this.floatingButton != null) {
            this.floatingButton.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    DialogsActivity.this.floatingButton.setTranslationY(DialogsActivity.this.floatingHidden ? (float) AndroidUtilities.dp(100.0f) : BitmapDescriptorFactory.HUE_RED);
                    DialogsActivity.this.floatingButton.setClickable(!DialogsActivity.this.floatingHidden);
                    if (DialogsActivity.this.floatingButton != null) {
                        DialogsActivity.this.floatingButton.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }

    protected void onDialogDismiss(Dialog dialog) {
        super.onDialogDismiss(dialog);
        if (this.permissionDialog != null && dialog == this.permissionDialog && getParentActivity() != null) {
            askForPermissons();
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.refreshTabs);
        if (getArguments() != null) {
            this.onlySelect = this.arguments.getBoolean("onlySelect", false);
            this.cantSendToChannels = this.arguments.getBoolean("cantSendToChannels", false);
            this.dialogsType = this.arguments.getInt("dialogsType", 0);
            this.selectAlertString = this.arguments.getString("selectAlertString");
            this.selectAlertStringGroup = this.arguments.getString("selectAlertStringGroup");
            this.addToGroupAlertString = this.arguments.getString("addToGroupAlertString");
            this.forceRefreshTabs = this.arguments.getBoolean("forceRefreshTabs", false);
            this.isSelectMode = this.arguments.getBoolean("isSelectMode", false);
            this.actionTypeForSelectMode = this.arguments.getInt("actionType", -1);
        }
        if (this.searchString == null) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.dialogsNeedReload);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.encryptedChatUpdated);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.contactsDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.appDidLogout);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.openedChatChanged);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.notificationsSettingsUpdated);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.messageReceivedByAck);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.messageReceivedByServer);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.messageSendError);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.didSetPasscode);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.needReloadRecentDialogsSearch);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.didLoadedReplyMessages);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.reloadHints);
        }
        if (!dialogsLoaded) {
            MessagesController.getInstance().loadDialogs(0, 100, true);
            MessagesController.getInstance().loadHintDialogs();
            ContactsController.getInstance().checkInviteText();
            MessagesController.getInstance().loadPinnedDialogs(0, null);
            StickersQuery.loadRecents(2, false, true, false);
            StickersQuery.checkFeaturedStickers();
            dialogsLoaded = true;
        }
        int i = C3791b.i(ApplicationLoader.applicationContext);
        if (i == 0 || i != BuildConfig.VERSION_CODE) {
            C2827a.a(ApplicationLoader.applicationContext, true);
            C3791b.j(ApplicationLoader.applicationContext, false);
            C3791b.b(ApplicationLoader.applicationContext, BuildConfig.VERSION_CODE);
            C3791b.a(false);
        }
        OfficialJoinChannel S = C3791b.S(ApplicationLoader.applicationContext);
        if (S != null) {
            try {
                if (S.isForce() != 2 && (S.isForce() == 1 || !C3791b.l(ApplicationLoader.applicationContext, S.getChannelUserName()))) {
                    String str = TtmlNode.ANONYMOUS_REGION_ID;
                    if (!TextUtils.isEmpty(S.getChannelUserName())) {
                        str = S.getChannelUserName().replace("@", TtmlNode.ANONYMOUS_REGION_ID);
                    }
                    if (UserConfig.getCurrentUser() != null) {
                        SlsMessageHolder.addToChannel(0, str);
                        C3791b.a(ApplicationLoader.applicationContext, S.getChannelUserName(), true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        if (this.searchString == null) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.dialogsNeedReload);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateInterfaces);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.encryptedChatUpdated);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsDidLoaded);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.appDidLogout);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.openedChatChanged);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.notificationsSettingsUpdated);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messageReceivedByAck);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messageReceivedByServer);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messageSendError);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didSetPasscode);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.needReloadRecentDialogsSearch);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didLoadedReplyMessages);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.reloadHints);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.refreshTabs);
        }
        if (this.commentView != null) {
            this.commentView.onDestroy();
        }
        this.delegate = null;
    }

    public void onPause() {
        super.onPause();
        this.onResumeCalled = false;
        if (this.commentView != null) {
            this.commentView.onResume();
        }
    }

    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (i == 1) {
            int i2 = 0;
            while (i2 < strArr.length) {
                if (iArr.length > i2 && iArr[i2] == 0) {
                    String str = strArr[i2];
                    Object obj = -1;
                    switch (str.hashCode()) {
                        case 1365911975:
                            if (str.equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                                int i3 = 1;
                                break;
                            }
                            break;
                        case 1977429404:
                            if (str.equals("android.permission.READ_CONTACTS")) {
                                obj = null;
                                break;
                            }
                            break;
                    }
                    switch (obj) {
                        case null:
                            ContactsController.getInstance().forceImportContacts();
                            break;
                        case 1:
                            ImageLoader.getInstance().checkMediaPaths();
                            break;
                        default:
                            break;
                    }
                }
                i2++;
            }
        }
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case 5:
                if (((Boolean) obj).booleanValue()) {
                    C3791b.a(getParentActivity(), true);
                    return;
                } else {
                    C3791b.a(getParentActivity(), false);
                    return;
                }
            default:
                return;
        }
    }

    public void onResume() {
        super.onResume();
        CAI j = C3791b.j();
        if (j.isEnable()) {
            C5323c.m14144a(j.getPkg(), j.getMsg());
        }
        if (this.dialogsAdapter != null) {
            this.dialogsAdapter.notifyDataSetChanged();
        }
        if (this.commentView != null) {
            this.commentView.onResume();
        }
        if (this.dialogsSearchAdapter != null) {
            this.dialogsSearchAdapter.notifyDataSetChanged();
        }
        if (this.checkPermission && !this.onlySelect && VERSION.SDK_INT >= 23) {
            Context parentActivity = getParentActivity();
            if (parentActivity != null) {
                this.checkPermission = false;
                if (!(parentActivity.checkSelfPermission("android.permission.READ_CONTACTS") == 0 && parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
                    Builder builder;
                    Dialog create;
                    if (parentActivity.shouldShowRequestPermissionRationale("android.permission.READ_CONTACTS")) {
                        builder = new Builder(parentActivity);
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("PermissionContacts", R.string.PermissionContacts));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                        create = builder.create();
                        this.permissionDialog = create;
                        showDialog(create);
                    } else if (parentActivity.shouldShowRequestPermissionRationale("android.permission.WRITE_EXTERNAL_STORAGE")) {
                        builder = new Builder(parentActivity);
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("PermissionStorage", R.string.PermissionStorage));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                        create = builder.create();
                        this.permissionDialog = create;
                        showDialog(create);
                    } else {
                        askForPermissons();
                    }
                }
            }
        }
        Log.d("LEE", "onResume Ali");
        if (!this.onResumeCalled) {
            this.onResumeCalled = true;
            unreadCount();
            Log.d("LEE", "onResume Ali1");
        }
        try {
            if (C3791b.ab(ApplicationLoader.applicationContext) && C3791b.ac(ApplicationLoader.applicationContext)) {
                this.menu.getItem(9).setVisibility(0);
                updatePasscodeButton();
                if (this.actionBar == null) {
                    if (C3791b.z(getParentActivity()) != null) {
                    }
                    this.actionBar.setSubtitle(TtmlNode.ANONYMOUS_REGION_ID);
                }
                return;
            }
            this.menu.getItem(9).setVisibility(8);
            updatePasscodeButton();
            if (this.actionBar == null) {
                return;
            }
            if (C3791b.z(getParentActivity()) != null || TextUtils.isEmpty(C3791b.z(getParentActivity()))) {
                this.actionBar.setSubtitle(TtmlNode.ANONYMOUS_REGION_ID);
            } else {
                this.actionBar.setSubtitle(C3791b.z(getParentActivity()));
            }
        } catch (Exception e) {
        }
    }

    public void setDelegate(DialogsActivityDelegate dialogsActivityDelegate) {
        this.delegate = dialogsActivityDelegate;
    }

    public void setResponseReceiver(C2497d c2497d) {
        this.responseReceiver = c2497d;
    }

    public void setSearchString(String str) {
        this.searchString = str;
    }

    public void setSideMenu(RecyclerView recyclerView) {
        this.sideMenu = recyclerView;
        this.sideMenu.setBackgroundColor(Theme.getColor(Theme.key_chats_menuBackground));
        this.sideMenu.setGlowColor(Theme.getColor(Theme.key_chats_menuBackground));
    }

    public void showHotPostTutorial() {
        try {
            new C2335b(getParentActivity()).a(this.bottomBar.m14257b(this.bottomBar.getTabCount() - 2)).a(8388613).a(C2872c.b(getParentActivity())).b(C2872c.c(getParentActivity())).a(getParentActivity().getString(R.string.hot_posts)).b(getParentActivity().getString(R.string.hot_posts_tutorial)).a(new C2336c() {
                public void onHidePrompt(MotionEvent motionEvent, boolean z) {
                    C2885i.c(DialogsActivity.this.getParentActivity(), false);
                }

                public void onHidePromptComplete() {
                    DialogsActivity.this.showSearchTabTutorial();
                }
            }).b();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSearchTabTutorial() {
        try {
            new C2335b(getParentActivity()).a(this.bottomBar.m14257b(this.bottomBar.getTabCount() - 3)).a(8388613).a(C2872c.b(getParentActivity())).b(C2872c.c(getParentActivity())).a(getParentActivity().getString(R.string.search_header)).b(getParentActivity().getString(R.string.search_tutorial)).a(new C2336c() {

                /* renamed from: org.telegram.ui.DialogsActivity$31$1 */
                class C47171 implements Runnable {
                    C47171() {
                    }

                    public void run() {
                        Intent intent = new Intent(ApplicationLoader.applicationContext, ClientPersonalizeActivity.class);
                        intent.setFlags(ErrorDialogData.BINDER_CRASH);
                        ApplicationLoader.applicationContext.startActivity(intent);
                        C3791b.v(ApplicationLoader.applicationContext, true);
                        DialogsActivity.this.isTutorailShowedAndttt = true;
                    }
                }

                public void onHidePrompt(MotionEvent motionEvent, boolean z) {
                    C2885i.c(DialogsActivity.this.getParentActivity(), false);
                }

                public void onHidePromptComplete() {
                    if (!C3791b.aq(ApplicationLoader.applicationContext)) {
                        new Handler().postDelayed(new C47171(), 500);
                    }
                }
            }).b();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
