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
import android.content.DialogInterface.OnClickListener;
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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.persianswitch.sdk.api.Request.General;
import com.persianswitch.sdk.api.Request.Register;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.AdsTransactionActivity;
import org.telegram.customization.Activities.ClientPersonalizeActivity;
import org.telegram.customization.Activities.JoinAdsActivity;
import org.telegram.customization.Activities.ShortcutActivity;
import org.telegram.customization.Activities.SlsHotPostActivity;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.DialogTab;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.Model.OfficialJoinChannel;
import org.telegram.customization.Model.Payment.HostRequestData;
import org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.customization.service.BaseService;
import org.telegram.customization.util.AppUtilities;
import org.telegram.customization.util.HomeViewManager;
import org.telegram.customization.util.Prefs;
import org.telegram.customization.util.view.PeekAndPop.PeekAndPop;
import org.telegram.customization.util.view.PeekAndPop.PeekAndPop.Builder;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
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
import org.telegram.messenger.query.SearchQuery;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.news.ElectionActivity;
import org.telegram.news.NewsListActivity;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatInvite;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$StickerSet;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlChat;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlChatInvite;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlStickerSet;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlUnknown;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlUser;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog;
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
import org.telegram.ui.Components.AvatarDrawable;
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
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.OnHidePromptListener;
import utils.Utilities;
import utils.app.AppPreferences;
import utils.view.Constants;
import utils.view.SlidingTabLayout;
import utils.view.SlidingTabLayout.TabColorizer;
import utils.view.bottombar.BottomBar;
import utils.view.bottombar.OnTabSelectListener;

public class DialogsActivity extends BaseFragment implements NotificationCenterDelegate, IResponseReceiver {
    public static boolean dialogsLoaded;
    final String FRG_ELECTION = "FRG_ELECTION";
    final String FRG_HOME = Constants.FRG_HOME;
    final String FRG_HOT = "FRG_HOT";
    final String FRG_NEWS_LIST = "FRG_NEWS_LIST";
    final String FRG_SEARCH = Constants.FRG_SEARCH;
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
    private DialogsActivityDelegate delegate;
    private DialogsAdapter dialogsAdapter;
    private DialogsAdapter dialogsBackupAdapter;
    private DialogsSearchAdapter dialogsSearchAdapter;
    private int dialogsType;
    private boolean disableAnimation;
    private ElectionActivity electionActivity;
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
            if (intent.getAction() != null && !intent.getAction().equals(org.telegram.customization.util.Constants.ACTION_FAVE_CHANGED)) {
                if (intent.getAction().equals(org.telegram.customization.util.Constants.ACTION_SWITCH_TAB)) {
                    try {
                        int pos = intent.getIntExtra(org.telegram.customization.util.Constants.EXTRA_CURRENT_POSITION, 2);
                        Log.d("alireza", "alireza broadcast recieved" + pos);
                        DialogsActivity.this.bottomBar.selectTabAtPosition(pos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (intent.getAction().equals(org.telegram.customization.util.Constants.ACTION_REBUILD_ALL)) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(10)});
                    if (DialogsActivity.this.parentLayout != null) {
                        DialogsActivity.this.parentLayout.rebuildAllFragmentViews(true, true);
                    }
                }
            }
        }
    };
    ActionBarMenu menu;
    private NewsListActivity newsListActivity;
    boolean onResumeCalled = false;
    private DialogsOnTouch onTouchListener = null;
    private boolean onlySelect;
    private long openedDialogId;
    private ActionBarMenuItem passcodeItem;
    private AlertDialog permissionDialog;
    private int prevPosition;
    private int prevTop;
    private RadialProgressView progressView;
    private PhotoViewerProvider provider = new C28701();
    IResponseReceiver responseReceiver;
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
    private SlsHotPostActivity slsHotPostActivity;
    private SlsHotPostActivity slsSearchActivity;
    private TabLayout tabLayout;
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
    class C28701 extends EmptyPhotoViewerProvider {
        C28701() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
            PlaceProviderObject placeProviderObject = null;
            int i = 0;
            if (fileLocation != null) {
                TLRPC$FileLocation photoBig = null;
                if (DialogsActivity.this.user_id != 0) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(DialogsActivity.this.user_id));
                    if (!(user == null || user.photo == null || user.photo.photo_big == null)) {
                        photoBig = user.photo.photo_big;
                    }
                } else if (DialogsActivity.this.chat_id != 0) {
                    TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(DialogsActivity.this.chat_id));
                    if (!(chat == null || chat.photo == null || chat.photo.photo_big == null)) {
                        photoBig = chat.photo.photo_big;
                    }
                }
                if (photoBig != null && photoBig.local_id == fileLocation.local_id && photoBig.volume_id == fileLocation.volume_id && photoBig.dc_id == fileLocation.dc_id) {
                    int[] coords = new int[2];
                    DialogsActivity.this.avatarImage.getLocationInWindow(coords);
                    placeProviderObject = new PlaceProviderObject();
                    placeProviderObject.viewX = coords[0];
                    int i2 = coords[1];
                    if (VERSION.SDK_INT < 21) {
                        i = AndroidUtilities.statusBarHeight;
                    }
                    placeProviderObject.viewY = i2 - i;
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
    class C28782 implements IResponseReceiver {
        C28782() {
        }

        public void onResult(Object object, int StatusCode) {
            switch (StatusCode) {
                case 27:
                    HostRequestData hostRequestData = (HostRequestData) object;
                    Bundle registerData = new Bundle();
                    registerData.putString(General.SECURE_TOKEN, AppPreferences.getSecurityToken(ApplicationLoader.applicationContext));
                    registerData.putInt(General.HOST_ID, 1152);
                    registerData.putString(General.PROTOCOL_VERSION, "1.8.0");
                    registerData.putString(General.HOST_DATA, hostRequestData.getHostRequest());
                    registerData.putString(General.HOST_DATA_SIGN, hostRequestData.getHostRequestSign());
                    registerData.putString(Register.MOBILE_NO, "09127020559");
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: org.telegram.ui.DialogsActivity$4 */
    class C28834 extends ActionBarMenuOnItemClick {
        C28834() {
        }

        public void onItemClick(int id) {
            boolean z = true;
            if (id == -1) {
                if (DialogsActivity.this.onlySelect) {
                    DialogsActivity.this.finishFragment();
                } else if (DialogsActivity.this.parentLayout != null) {
                    DialogsActivity.this.parentLayout.getDrawerLayoutContainer().openDrawer(false);
                }
            } else if (id == 1) {
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
    class C28867 implements OnItemClickListener {
        C28867() {
        }

        public void onItemClick(View view, int position) {
            if (DialogsActivity.this.isSelectMode) {
                if (DialogsActivity.this.dialogsAdapter.getItem(position) != null) {
                    MessagesController.getInstance().updateDialogSelectStatus(((TLRPC$TL_dialog) DialogsActivity.this.dialogsAdapter.getItem(position)).id);
                }
                DialogsActivity.this.dialogsAdapter.notifyDataSetChanged();
            } else if (DialogsActivity.this.listView != null && DialogsActivity.this.listView.getAdapter() != null && DialogsActivity.this.getParentActivity() != null) {
                long dialog_id = 0;
                int message_id = 0;
                Adapter adapter = DialogsActivity.this.listView.getAdapter();
                if (adapter == DialogsActivity.this.dialogsAdapter) {
                    TLObject object = DialogsActivity.this.dialogsAdapter.getItem(position);
                    if (object instanceof TLRPC$TL_dialog) {
                        dialog_id = ((TLRPC$TL_dialog) object).id;
                    } else if (object instanceof TLRPC$TL_recentMeUrlChat) {
                        dialog_id = (long) (-((TLRPC$TL_recentMeUrlChat) object).chat_id);
                    } else if (object instanceof TLRPC$TL_recentMeUrlUser) {
                        dialog_id = (long) ((TLRPC$TL_recentMeUrlUser) object).user_id;
                    } else if (object instanceof TLRPC$TL_recentMeUrlChatInvite) {
                        TLRPC$TL_recentMeUrlChatInvite chatInvite = (TLRPC$TL_recentMeUrlChatInvite) object;
                        TLRPC$ChatInvite invite = chatInvite.chat_invite;
                        if ((invite.chat == null && (!invite.channel || invite.megagroup)) || (invite.chat != null && (!ChatObject.isChannel(invite.chat) || invite.chat.megagroup))) {
                            String hash = chatInvite.url;
                            int index = hash.indexOf(47);
                            if (index > 0) {
                                hash = hash.substring(index + 1);
                            }
                            DialogsActivity.this.showDialog(new JoinGroupAlert(DialogsActivity.this.getParentActivity(), invite, hash, DialogsActivity.this));
                            return;
                        } else if (invite.chat != null) {
                            dialog_id = (long) (-invite.chat.id);
                        } else {
                            return;
                        }
                    } else if (object instanceof TLRPC$TL_recentMeUrlStickerSet) {
                        TLRPC$StickerSet stickerSet = ((TLRPC$TL_recentMeUrlStickerSet) object).set.set;
                        TLRPC$TL_inputStickerSetID set = new TLRPC$TL_inputStickerSetID();
                        set.id = stickerSet.id;
                        set.access_hash = stickerSet.access_hash;
                        DialogsActivity.this.showDialog(new StickersAlert(DialogsActivity.this.getParentActivity(), DialogsActivity.this, set, null, null));
                        return;
                    } else if (!(object instanceof TLRPC$TL_recentMeUrlUnknown)) {
                        return;
                    } else {
                        return;
                    }
                } else if (adapter == DialogsActivity.this.dialogsSearchAdapter) {
                    MessageObject obj = DialogsActivity.this.dialogsSearchAdapter.getItem(position);
                    if (obj instanceof User) {
                        dialog_id = (long) ((User) obj).id;
                        if (DialogsActivity.this.dialogsSearchAdapter.isGlobalSearch(position)) {
                            ArrayList<User> users = new ArrayList();
                            users.add((User) obj);
                            MessagesController.getInstance().putUsers(users, false);
                            MessagesStorage.getInstance().putUsersAndChats(users, null, false, true);
                        }
                        if (!DialogsActivity.this.onlySelect) {
                            DialogsActivity.this.dialogsSearchAdapter.putRecentSearch(dialog_id, (User) obj);
                        }
                    } else if (obj instanceof TLRPC$Chat) {
                        if (DialogsActivity.this.dialogsSearchAdapter.isGlobalSearch(position)) {
                            ArrayList<TLRPC$Chat> chats = new ArrayList();
                            chats.add((TLRPC$Chat) obj);
                            MessagesController.getInstance().putChats(chats, false);
                            MessagesStorage.getInstance().putUsersAndChats(null, chats, false, true);
                        }
                        if (((TLRPC$Chat) obj).id > 0) {
                            dialog_id = (long) (-((TLRPC$Chat) obj).id);
                        } else {
                            dialog_id = AndroidUtilities.makeBroadcastId(((TLRPC$Chat) obj).id);
                        }
                        if (!DialogsActivity.this.onlySelect) {
                            DialogsActivity.this.dialogsSearchAdapter.putRecentSearch(dialog_id, (TLRPC$Chat) obj);
                        }
                    } else if (obj instanceof TLRPC$EncryptedChat) {
                        dialog_id = ((long) ((TLRPC$EncryptedChat) obj).id) << 32;
                        if (!DialogsActivity.this.onlySelect) {
                            DialogsActivity.this.dialogsSearchAdapter.putRecentSearch(dialog_id, (TLRPC$EncryptedChat) obj);
                        }
                    } else if (obj instanceof MessageObject) {
                        MessageObject messageObject = obj;
                        dialog_id = messageObject.getDialogId();
                        message_id = messageObject.getId();
                        DialogsActivity.this.dialogsSearchAdapter.addHashtagsFromMessage(DialogsActivity.this.dialogsSearchAdapter.getLastSearchString());
                    } else if (obj instanceof String) {
                        DialogsActivity.this.actionBar.openSearchField((String) obj);
                    }
                }
                if (dialog_id != 0) {
                    int lower_part;
                    int high_id;
                    Bundle args;
                    TLRPC$Chat chat;
                    boolean imageClicked = LocaleController.isRTL ? Utilities.convertPixelsToDp((float) view.getWidth(), ApplicationLoader.applicationContext) - 65.0f < DialogsActivity.this.touchPositionDP : DialogsActivity.this.touchPositionDP < 65.0f;
                    if (imageClicked) {
                        SharedPreferences plusPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                        DialogsActivity.this.user_id = 0;
                        DialogsActivity.this.chat_id = 0;
                        lower_part = (int) dialog_id;
                        high_id = (int) (dialog_id >> 32);
                        if (lower_part == 0) {
                            DialogsActivity.this.user_id = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(high_id)).user_id;
                        } else if (high_id == 1) {
                            DialogsActivity.this.chat_id = lower_part;
                        } else if (lower_part > 0) {
                            DialogsActivity.this.user_id = lower_part;
                        } else if (lower_part < 0) {
                            DialogsActivity.this.chat_id = -lower_part;
                        }
                        int picClick;
                        if (DialogsActivity.this.user_id != 0) {
                            picClick = plusPreferences.getInt("dialogsClickOnPic", 0);
                            if (picClick == 2) {
                                args = new Bundle();
                                args.putInt("user_id", DialogsActivity.this.user_id);
                                DialogsActivity.this.presentFragment(new ProfileActivity(args));
                                return;
                            } else if (picClick == 1) {
                                User user = MessagesController.getInstance().getUser(Integer.valueOf(DialogsActivity.this.user_id));
                                if (user.photo != null && user.photo.photo_big != null) {
                                    PhotoViewer.getInstance().setParentActivity(DialogsActivity.this.getParentActivity());
                                    PhotoViewer.getInstance().openPhoto(user.photo.photo_big, DialogsActivity.this.provider);
                                    return;
                                }
                                return;
                            }
                        } else if (DialogsActivity.this.chat_id != 0) {
                            picClick = plusPreferences.getInt("dialogsClickOnGroupPic", 0);
                            if (picClick == 2) {
                                MessagesController.getInstance().loadChatInfo(DialogsActivity.this.chat_id, null, false);
                                args = new Bundle();
                                args.putInt("chat_id", DialogsActivity.this.chat_id);
                                DialogsActivity.this.presentFragment(new ProfileActivity(args));
                                return;
                            } else if (picClick == 1) {
                                chat = MessagesController.getInstance().getChat(Integer.valueOf(DialogsActivity.this.chat_id));
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
                        args = new Bundle();
                        lower_part = (int) dialog_id;
                        high_id = (int) (dialog_id >> 32);
                        if (lower_part == 0) {
                            args.putInt("enc_id", high_id);
                        } else if (high_id == 1) {
                            args.putInt("chat_id", lower_part);
                        } else if (lower_part > 0) {
                            args.putInt("user_id", lower_part);
                        } else if (lower_part < 0) {
                            if (message_id != 0) {
                                chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_part));
                                if (!(chat == null || chat.migrated_to == null)) {
                                    args.putInt("migrated_to", lower_part);
                                    lower_part = -chat.migrated_to.channel_id;
                                }
                            }
                            args.putInt("chat_id", -lower_part);
                        }
                        if (message_id != 0) {
                            args.putInt("message_id", message_id);
                        } else if (DialogsActivity.this.actionBar != null) {
                            DialogsActivity.this.actionBar.closeSearchField();
                        }
                        if (AndroidUtilities.isTablet()) {
                            if (DialogsActivity.this.openedDialogId == dialog_id && adapter != DialogsActivity.this.dialogsSearchAdapter) {
                                return;
                            }
                            if (DialogsActivity.this.dialogsAdapter != null) {
                                DialogsActivity.this.dialogsAdapter.setOpenedDialogId(DialogsActivity.this.openedDialogId = dialog_id);
                                DialogsActivity.this.updateVisibleRows(512);
                            }
                        }
                        if (DialogsActivity.this.searchString != null) {
                            if (MessagesController.checkCanOpenChat(args, DialogsActivity.this)) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                DialogsActivity.this.presentFragment(new ChatActivity(args));
                            }
                        } else if (MessagesController.checkCanOpenChat(args, DialogsActivity.this)) {
                            DialogsActivity.this.presentFragment(new ChatActivity(args));
                        }
                    } else if (DialogsActivity.this.dialogsAdapter.hasSelectedDialogs()) {
                        DialogsActivity.this.dialogsAdapter.addOrRemoveSelectedDialog(dialog_id, view);
                        DialogsActivity.this.updateSelectedCount();
                    } else {
                        DialogsActivity.this.didSelectResult(dialog_id, true, false);
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.DialogsActivity$9 */
    class C28949 extends ViewOutlineProvider {
        C28949() {
        }

        @SuppressLint({"NewApi"})
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        }
    }

    public class CustomPagerAdapter extends PagerAdapter {
        private Context mContext;

        public CustomPagerAdapter(Context context) {
            this.mContext = context;
        }

        public Object instantiateItem(ViewGroup collection, int position) {
            ViewGroup layout = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R.layout.sls_transparent, collection, false);
            collection.addView(layout);
            return layout;
        }

        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        public int getCount() {
            return 5;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public CharSequence getPageTitle(int position) {
            return "85";
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

        public boolean onTouch(View view, MotionEvent event) {
            DialogsActivity.this.touchPositionDP = (float) Math.round(event.getX() / this.vDPI);
            if (DialogsActivity.this.hideTabs) {
                return false;
            }
            switch (event.getAction()) {
                case 0:
                    this.downX = (float) Math.round(event.getX() / this.vDPI);
                    this.downY = (float) Math.round(event.getY() / this.vDPI);
                    if (DialogsActivity.this.touchPositionDP > 50.0f) {
                        DialogsActivity.this.parentLayout.getDrawerLayoutContainer().setAllowOpenDrawer(false, false);
                    }
                    return view instanceof LinearLayout;
                case 1:
                    this.upX = (float) Math.round(event.getX() / this.vDPI);
                    this.upY = (float) Math.round(event.getY() / this.vDPI);
                    float deltaX = this.downX - this.upX;
                    float deltaY = this.downY - this.upY;
                    if (Math.abs(deltaX) > 40.0f && Math.abs(deltaY) < 60.0f) {
                        DialogsActivity.this.refreshDialogType(deltaX >= 0.0f ? 0 : 1);
                        this.downX = (float) Math.round(event.getX() / this.vDPI);
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

    public IResponseReceiver getResponseReceiver() {
        return this.responseReceiver;
    }

    public void setResponseReceiver(IResponseReceiver responseReceiver) {
        this.responseReceiver = responseReceiver;
    }

    public DialogsActivity(Bundle args) {
        super(args);
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
            this.isSelectMode = this.arguments.getBoolean(org.telegram.customization.util.Constants.IS_SELECT_MODE, false);
            this.actionTypeForSelectMode = this.arguments.getInt(org.telegram.customization.util.Constants.ACTION_TYPE, -1);
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
        int appVer = AppPreferences.getAppVersion(ApplicationLoader.applicationContext);
        if (appVer == 0 || appVer != 135) {
            BaseService.startAllServices(ApplicationLoader.applicationContext, true);
            AppPreferences.setSpFirstTimeSync(ApplicationLoader.applicationContext, false);
            AppPreferences.setAppVersion(ApplicationLoader.applicationContext, 135);
            AppPreferences.setApRegisterStatus(false);
        }
        OfficialJoinChannel channel = AppPreferences.getJoinToOfficialChannel(ApplicationLoader.applicationContext);
        if (channel != null) {
            try {
                if (channel.isForce() != 2 && (channel.isForce() == 1 || !AppPreferences.isJoinChannel(ApplicationLoader.applicationContext, channel.getChannelUserName()))) {
                    String channelUsername = "";
                    if (!TextUtils.isEmpty(channel.getChannelUserName())) {
                        channelUsername = channel.getChannelUserName().replace("@", "");
                    }
                    if (UserConfig.getCurrentUser() != null) {
                        SlsMessageHolder.addToChannel(0, channelUsername);
                        AppPreferences.setJoinChannel(ApplicationLoader.applicationContext, channel.getChannelUserName(), true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void registerSdkAp() {
        HandleRequest.getNew(ApplicationLoader.applicationContext, new C28782()).registerApSdk();
    }

    public PeekAndPop handleChatPreview(long did, View lcView) {
        int lower_id = (int) did;
        Bundle args = new Bundle();
        if (lower_id > 0) {
            args.putInt("user_id", lower_id);
        } else {
            args.putInt("chat_id", -lower_id);
        }
        ChatActivity chatAct = new ChatActivity(args);
        chatAct.actionBar = new ActionBar(getParentActivity());
        chatAct.parentLayout = this.parentLayout;
        View chatView = chatAct.createView(getParentActivity());
        chatAct.onFragmentCreate();
        return new Builder(getParentActivity()).blurBackground(false).peekLayout(chatView).longClickViews(new View[]{lcView}).build();
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

    public View createView(Context context) {
        float f;
        this.searching = false;
        this.searchWas = false;
        if (UserConfig.isClientActivated() && !AppPreferences.isRegistered(ApplicationLoader.applicationContext)) {
            HandleRequest.getNew(ApplicationLoader.applicationContext, this).registerOnMono(false);
        }
        if (!AppPreferences.isAddCurrentUserToFave(ApplicationLoader.applicationContext)) {
            Favourite.addFavourite(new Long((long) UserConfig.getClientUserId()));
            AppPreferences.setAddCurrentUserToFave(ApplicationLoader.applicationContext, true);
        }
        this.avatarImage = new BackupImageView(getParentActivity());
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(30.0f));
        final Context context2 = context;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                Theme.createChatResources(context2, false);
            }
        });
        this.menu = this.actionBar.createMenu();
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C28834());
        if (this.sideMenu != null) {
            this.sideMenu.setBackgroundColor(Theme.getColor(Theme.key_chats_menuBackground));
            this.sideMenu.setGlowColor(Theme.getColor(Theme.key_chats_menuBackground));
            this.sideMenu.getAdapter().notifyDataSetChanged();
        }
        this.contentView = new SizeNotifierFrameLayout(context) {
            int inputFieldHeight = 0;

            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int widthSize = MeasureSpec.getSize(widthMeasureSpec);
                int heightSize = MeasureSpec.getSize(heightMeasureSpec);
                setMeasuredDimension(widthSize, heightSize);
                heightSize -= getPaddingTop();
                measureChildWithMargins(DialogsActivity.this.actionBar, widthMeasureSpec, 0, heightMeasureSpec, 0);
                int keyboardSize = getKeyboardHeight();
                int childCount = getChildCount();
                if (DialogsActivity.this.commentView != null) {
                    measureChildWithMargins(DialogsActivity.this.commentView, widthMeasureSpec, 0, heightMeasureSpec, 0);
                    Object tag = DialogsActivity.this.commentView.getTag();
                    if (tag == null || !tag.equals(Integer.valueOf(2))) {
                        this.inputFieldHeight = 0;
                    } else {
                        if (keyboardSize <= AndroidUtilities.dp(20.0f) && !AndroidUtilities.isInMultiwindow) {
                            heightSize -= DialogsActivity.this.commentView.getEmojiPadding();
                        }
                        this.inputFieldHeight = DialogsActivity.this.commentView.getMeasuredHeight();
                    }
                }
                for (int i = 0; i < childCount; i++) {
                    try {
                        View child = getChildAt(i);
                        if (!(child == null || child.getVisibility() == 8 || child == DialogsActivity.this.commentView || child == DialogsActivity.this.actionBar)) {
                            if (child == DialogsActivity.this.listView || child == DialogsActivity.this.progressView || child == DialogsActivity.this.searchEmptyView) {
                                child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(Math.max(AndroidUtilities.dp(10.0f), (heightSize - this.inputFieldHeight) + AndroidUtilities.dp(2.0f)), 1073741824));
                            } else if (DialogsActivity.this.commentView == null || !DialogsActivity.this.commentView.isPopupView(child)) {
                                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                            } else if (!AndroidUtilities.isInMultiwindow) {
                                child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(child.getLayoutParams().height, 1073741824));
                            } else if (AndroidUtilities.isTablet()) {
                                child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.dp(320.0f), ((heightSize - this.inputFieldHeight) - AndroidUtilities.statusBarHeight) + getPaddingTop()), 1073741824));
                            } else {
                                child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(((heightSize - this.inputFieldHeight) - AndroidUtilities.statusBarHeight) + getPaddingTop(), 1073741824));
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }

            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                int count = getChildCount();
                Object tag = DialogsActivity.this.commentView != null ? DialogsActivity.this.commentView.getTag() : null;
                int paddingBottom = (tag == null || !tag.equals(Integer.valueOf(2))) ? 0 : (getKeyboardHeight() > AndroidUtilities.dp(20.0f) || AndroidUtilities.isInMultiwindow) ? 0 : DialogsActivity.this.commentView.getEmojiPadding();
                setBottomClip(paddingBottom);
                for (int i = 0; i < count; i++) {
                    View child = getChildAt(i);
                    if (child.getVisibility() != 8) {
                        int childLeft;
                        int childTop;
                        LayoutParams lp = (LayoutParams) child.getLayoutParams();
                        int width = child.getMeasuredWidth();
                        int height = child.getMeasuredHeight();
                        int gravity = lp.gravity;
                        if (gravity == -1) {
                            gravity = 51;
                        }
                        int verticalGravity = gravity & 112;
                        switch ((gravity & 7) & 7) {
                            case 1:
                                childLeft = ((((r - l) - width) / 2) + lp.leftMargin) - lp.rightMargin;
                                break;
                            case 5:
                                childLeft = (r - width) - lp.rightMargin;
                                break;
                            default:
                                childLeft = lp.leftMargin;
                                break;
                        }
                        switch (verticalGravity) {
                            case 16:
                                childTop = (((((b - paddingBottom) - t) - height) / 2) + lp.topMargin) - lp.bottomMargin;
                                break;
                            case 48:
                                childTop = lp.topMargin + getPaddingTop();
                                break;
                            case 80:
                                childTop = (((b - paddingBottom) - t) - height) - lp.bottomMargin;
                                break;
                            default:
                                childTop = lp.topMargin;
                                break;
                        }
                        if (DialogsActivity.this.commentView != null && DialogsActivity.this.commentView.isPopupView(child)) {
                            if (AndroidUtilities.isInMultiwindow) {
                                childTop = (DialogsActivity.this.commentView.getTop() - child.getMeasuredHeight()) + AndroidUtilities.dp(1.0f);
                            } else {
                                childTop = DialogsActivity.this.commentView.getBottom();
                            }
                        }
                        child.layout(childLeft, childTop, childLeft + width, childTop + height);
                    }
                }
                notifyHeightChanged();
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
        SizeNotifierFrameLayout sizeNotifierFrameLayout = this.contentView;
        View view = this.listView;
        if (this.isSelectMode) {
            f = 0.0f;
        } else {
            f = (float) this.bottomBarHeight;
        }
        sizeNotifierFrameLayout.addView(view, LayoutHelper.createFrame(-1, -1.0f, 48, 0.0f, 0.0f, 0.0f, f));
        this.viewPager = new ViewPager(context);
        this.viewPager.setAdapter(new CustomPagerAdapter(context));
        this.frameLayout.addView(this.viewPager, LayoutHelper.createFrame(-1, 0.0f, 0, 0.0f, 0.0f, 0.0f, (float) this.bottomBarHeight));
        this.onTouchListener = new DialogsOnTouch(context);
        this.listView.setOnTouchListener(this.onTouchListener);
        this.listView.setOnItemClickListener(new C28867());
        context2 = context;
        this.listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            /* renamed from: org.telegram.ui.DialogsActivity$8$1 */
            class C28871 implements OnClickListener {
                C28871() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (DialogsActivity.this.dialogsSearchAdapter.isRecentSearchDisplayed()) {
                        DialogsActivity.this.dialogsSearchAdapter.clearRecentSearch();
                    } else {
                        DialogsActivity.this.dialogsSearchAdapter.clearRecentHashtags();
                    }
                }
            }

            public boolean onItemClick(View view, int position) {
                if (DialogsActivity.this.getParentActivity() == null) {
                    return false;
                }
                if (DialogsActivity.this.listView.getAdapter() != DialogsActivity.this.dialogsSearchAdapter) {
                    ArrayList<TLRPC$TL_dialog> dialogs = DialogsActivity.this.getDialogsArray();
                    if (position < 0 || position >= dialogs.size()) {
                        return false;
                    }
                    TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) dialogs.get(position);
                    if (!DialogsActivity.this.onlySelect) {
                        DialogsActivity.this.selectedDialog = dialog.id;
                        boolean pinned = dialog.pinned;
                        BottomSheet.Builder builder = new BottomSheet.Builder(DialogsActivity.this.getParentActivity());
                        int lower_id = (int) DialogsActivity.this.selectedDialog;
                        int high_id = (int) (DialogsActivity.this.selectedDialog >> 32);
                        boolean wasHideChat = AppPreferences.isHiddenChat(context2, dialog.id);
                        if (wasHideChat) {
                            CharSequence hideDialog = LocaleController.getString(JoinPoint.SYNCHRONIZATION_UNLOCK, R.string.unlock);
                        } else {
                            String string = LocaleController.getString(JoinPoint.SYNCHRONIZATION_LOCK, R.string.lock);
                        }
                        int i;
                        CharSequence cs2;
                        CharSequence cs;
                        CharSequence csa;
                        CharSequence wasLock;
                        String string2;
                        final boolean z;
                        if (DialogObject.isChannel(dialog)) {
                            CharSequence[] items;
                            final TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
                            int[] icons = new int[8];
                            icons[0] = dialog.pinned ? R.drawable.chats_unpin : R.drawable.chats_pin;
                            icons[1] = R.drawable.chats_clear;
                            icons[2] = R.drawable.chats_leave;
                            if (Favourite.isFavourite(Long.valueOf(dialog.id))) {
                                i = R.drawable.ic_star;
                            } else {
                                i = R.drawable.ic_off_star;
                            }
                            icons[3] = i;
                            icons[4] = R.drawable.notifications_s_on;
                            icons[5] = R.drawable.ic_mark_as_read;
                            icons[6] = R.drawable.ic_fast;
                            if (wasHideChat) {
                                i = R.drawable.lock_close_off;
                            } else {
                                i = R.drawable.lock_close;
                            }
                            icons[7] = i;
                            cs2 = Favourite.isFavourite(Long.valueOf(dialog.id)) ? LocaleController.getString("DeleteFromFavorites", R.string.DeleteFromFavorites) : LocaleController.getString("AddToFavorites", R.string.AddToFavorites);
                            cs = MessagesController.getInstance().isDialogMuted(DialogsActivity.this.selectedDialog) ? LocaleController.getString("UnmuteNotifications", R.string.UnmuteNotifications) : LocaleController.getString("MuteNotifications", R.string.MuteNotifications);
                            csa = LocaleController.getString("AddShortcut", R.string.AddShortcut);
                            wasLock = wasHideChat ? LocaleController.getString(JoinPoint.SYNCHRONIZATION_UNLOCK, R.string.unlock) : LocaleController.getString(JoinPoint.SYNCHRONIZATION_LOCK, R.string.lock);
                            if (chat == null || !chat.megagroup) {
                                items = new CharSequence[8];
                                string2 = (dialog.pinned || MessagesController.getInstance().canPinDialog(false)) ? dialog.pinned ? LocaleController.getString("UnpinFromTop", R.string.UnpinFromTop) : LocaleController.getString("PinToTop", R.string.PinToTop) : null;
                                items[0] = string2;
                                items[1] = LocaleController.getString("ClearHistoryCache", R.string.ClearHistoryCache);
                                items[2] = LocaleController.getString("LeaveChannelMenu", R.string.LeaveChannelMenu);
                                items[3] = cs2;
                                items[4] = cs;
                                items[5] = LocaleController.getString("MarkAsRead", R.string.MarkAsRead);
                                items[6] = csa;
                                items[7] = wasLock;
                            } else {
                                items = new CharSequence[8];
                                string2 = (dialog.pinned || MessagesController.getInstance().canPinDialog(false)) ? dialog.pinned ? LocaleController.getString("UnpinFromTop", R.string.UnpinFromTop) : LocaleController.getString("PinToTop", R.string.PinToTop) : null;
                                items[0] = string2;
                                items[1] = TextUtils.isEmpty(chat.username) ? LocaleController.getString("ClearHistory", R.string.ClearHistory) : LocaleController.getString("ClearHistoryCache", R.string.ClearHistoryCache);
                                items[2] = LocaleController.getString("LeaveMegaMenu", R.string.LeaveMegaMenu);
                                items[3] = cs2;
                                items[4] = cs;
                                items[5] = LocaleController.getString("MarkAsRead", R.string.MarkAsRead);
                                items[6] = csa;
                                items[7] = wasLock;
                            }
                            z = pinned;
                            builder.setItems(items, icons, new OnClickListener() {

                                /* renamed from: org.telegram.ui.DialogsActivity$8$2$1 */
                                class C28881 implements OnClickListener {
                                    C28881() {
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
                                class C28892 implements OnClickListener {
                                    C28892() {
                                    }

                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MessagesController.getInstance().deleteUserFromChat((int) (-DialogsActivity.this.selectedDialog), UserConfig.getCurrentUser(), null);
                                        if (AndroidUtilities.isTablet()) {
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[]{Long.valueOf(DialogsActivity.this.selectedDialog)});
                                        }
                                    }
                                }

                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        if (MessagesController.getInstance().pinDialog(DialogsActivity.this.selectedDialog, !z, null, 0) && !z) {
                                            DialogsActivity.this.listView.smoothScrollToPosition(0);
                                        }
                                    } else if (which == 3) {
                                        dialg = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(DialogsActivity.this.selectedDialog));
                                        if (Favourite.isFavourite(Long.valueOf(dialg.id))) {
                                            Favourite.deleteFavourite(Long.valueOf(DialogsActivity.this.selectedDialog));
                                            MessagesController.getInstance().dialogsFavs.remove(dialg);
                                        } else {
                                            Favourite.addFavourite(Long.valueOf(DialogsActivity.this.selectedDialog));
                                            MessagesController.getInstance().dialogsFavs.add(dialg);
                                        }
                                        if (DialogsActivity.this.dialogsType == 8) {
                                            DialogsActivity.this.dialogsAdapter.notifyDataSetChanged();
                                            if (!DialogsActivity.this.hideTabs) {
                                                DialogsActivity.this.updateTabs();
                                            }
                                        }
                                        DialogsActivity.this.fillAllUnreadCounts();
                                        DialogsActivity.this.unreadCount(DialogsActivity.this.countFavs, DialogsActivity.this.allMutedFavs, (TextView) DialogsActivity.this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_FAVES));
                                    } else if (which == 4) {
                                        if (MessagesController.getInstance().isDialogMuted(DialogsActivity.this.selectedDialog)) {
                                            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                                            editor.putInt("notify2_" + DialogsActivity.this.selectedDialog, 0);
                                            MessagesStorage.getInstance().setDialogFlags(DialogsActivity.this.selectedDialog, 0);
                                            editor.commit();
                                            dialg = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(DialogsActivity.this.selectedDialog));
                                            if (dialg != null) {
                                                dialg.notify_settings = new TLRPC$TL_peerNotifySettings();
                                            }
                                            NotificationsController.updateServerNotificationsSettings(DialogsActivity.this.selectedDialog);
                                            return;
                                        }
                                        DialogsActivity.this.showDialog(AlertsCreator.createMuteAlert(DialogsActivity.this.getParentActivity(), DialogsActivity.this.selectedDialog));
                                    } else if (which == 5) {
                                        DialogsActivity.this.markAsReadDialog(false);
                                    } else if (which == 6) {
                                        DialogsActivity.this.addShortcut();
                                    } else if (which == 7) {
                                        boolean wasHideChat = AppPreferences.isHiddenChat(context2, DialogsActivity.this.selectedDialog);
                                        if (wasHideChat) {
                                            CharSequence hideDialog = LocaleController.getString(JoinPoint.SYNCHRONIZATION_UNLOCK, R.string.unlock);
                                        } else {
                                            String string = LocaleController.getString(JoinPoint.SYNCHRONIZATION_LOCK, R.string.lock);
                                        }
                                        DialogsActivity.this.lockChat(context2, wasHideChat, DialogsActivity.this.selectedDialog);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DialogsActivity.this.getParentActivity());
                                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                        if (which == 1) {
                                            if (chat == null || !chat.megagroup) {
                                                builder.setMessage(LocaleController.getString("AreYouSureClearHistoryChannel", R.string.AreYouSureClearHistoryChannel));
                                            } else if (TextUtils.isEmpty(chat.username)) {
                                                builder.setMessage(LocaleController.getString("AreYouSureClearHistory", R.string.AreYouSureClearHistory));
                                            } else {
                                                builder.setMessage(LocaleController.getString("AreYouSureClearHistoryGroup", R.string.AreYouSureClearHistoryGroup));
                                            }
                                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C28881());
                                        } else {
                                            Log.d("alireza", "alireza selectedDialog" + DialogsActivity.this.selectedDialog);
                                            if (chat == null || !chat.megagroup) {
                                                builder.setMessage(LocaleController.getString("ChannelLeaveAlert", R.string.ChannelLeaveAlert));
                                            } else {
                                                builder.setMessage(LocaleController.getString("MegaLeaveAlert", R.string.MegaLeaveAlert));
                                            }
                                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C28892());
                                        }
                                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                                        DialogsActivity.this.showDialog(builder.create());
                                    }
                                }
                            });
                            DialogsActivity.this.showDialog(builder.create());
                        } else {
                            int[] iArr;
                            final boolean z2;
                            boolean isChat = lower_id < 0 && high_id != 1;
                            User user = null;
                            if (!(isChat || lower_id <= 0 || high_id == 1)) {
                                user = MessagesController.getInstance().getUser(Integer.valueOf(lower_id));
                            }
                            final boolean isBot = user != null && user.bot;
                            cs2 = Favourite.isFavourite(Long.valueOf(dialog.id)) ? LocaleController.getString("DeleteFromFavorites", R.string.DeleteFromFavorites) : LocaleController.getString("AddToFavorites", R.string.AddToFavorites);
                            cs = MessagesController.getInstance().isDialogMuted(DialogsActivity.this.selectedDialog) ? LocaleController.getString("UnmuteNotifications", R.string.UnmuteNotifications) : LocaleController.getString("MuteNotifications", R.string.MuteNotifications);
                            csa = LocaleController.getString("AddShortcut", R.string.AddShortcut);
                            wasLock = wasHideChat ? LocaleController.getString(JoinPoint.SYNCHRONIZATION_UNLOCK, R.string.unlock) : LocaleController.getString(JoinPoint.SYNCHRONIZATION_LOCK, R.string.lock);
                            CharSequence[] charSequenceArr = new CharSequence[8];
                            if (!dialog.pinned) {
                                if (!MessagesController.getInstance().canPinDialog(lower_id == 0)) {
                                    string2 = null;
                                    charSequenceArr[0] = string2;
                                    charSequenceArr[1] = LocaleController.getString("ClearHistory", R.string.ClearHistory);
                                    string2 = isChat ? LocaleController.getString("DeleteChat", R.string.DeleteChat) : isBot ? LocaleController.getString("DeleteAndStop", R.string.DeleteAndStop) : LocaleController.getString("Delete", R.string.Delete);
                                    charSequenceArr[2] = string2;
                                    charSequenceArr[3] = cs2;
                                    charSequenceArr[4] = cs;
                                    charSequenceArr[5] = LocaleController.getString("MarkAsRead", R.string.MarkAsRead);
                                    charSequenceArr[6] = csa;
                                    charSequenceArr[7] = wasLock;
                                    iArr = new int[8];
                                    iArr[0] = dialog.pinned ? R.drawable.chats_unpin : R.drawable.chats_pin;
                                    iArr[1] = R.drawable.chats_clear;
                                    iArr[2] = isChat ? R.drawable.chats_leave : R.drawable.chats_delete;
                                    if (Favourite.isFavourite(Long.valueOf(dialog.id))) {
                                        i = R.drawable.ic_off_star;
                                    } else {
                                        i = R.drawable.ic_star;
                                    }
                                    iArr[3] = i;
                                    iArr[4] = R.drawable.notifications_s_on;
                                    iArr[5] = R.drawable.ic_mark_as_read;
                                    iArr[6] = R.drawable.ic_fast;
                                    if (wasHideChat) {
                                        i = R.drawable.lock_close;
                                    } else {
                                        i = R.drawable.lock_close_off;
                                    }
                                    iArr[7] = i;
                                    z = pinned;
                                    z2 = isChat;
                                    builder.setItems(charSequenceArr, iArr, new OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (which == 0) {
                                                if (MessagesController.getInstance().pinDialog(DialogsActivity.this.selectedDialog, !z, null, 0) && !z) {
                                                    DialogsActivity.this.listView.smoothScrollToPosition(0);
                                                }
                                            } else if (which == 3) {
                                                dialg = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(DialogsActivity.this.selectedDialog));
                                                if (Favourite.isFavourite(Long.valueOf(dialg.id))) {
                                                    Favourite.deleteFavourite(Long.valueOf(DialogsActivity.this.selectedDialog));
                                                    MessagesController.getInstance().dialogsFavs.remove(dialg);
                                                } else {
                                                    Favourite.addFavourite(Long.valueOf(DialogsActivity.this.selectedDialog));
                                                    MessagesController.getInstance().dialogsFavs.add(dialg);
                                                }
                                                if (DialogsActivity.this.dialogsType == 8) {
                                                    DialogsActivity.this.dialogsAdapter.notifyDataSetChanged();
                                                    if (!DialogsActivity.this.hideTabs) {
                                                        DialogsActivity.this.updateTabs();
                                                    }
                                                }
                                                DialogsActivity.this.fillAllUnreadCounts();
                                                DialogsActivity.this.unreadCount(DialogsActivity.this.countFavs, DialogsActivity.this.allMutedFavs, (TextView) DialogsActivity.this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_FAVES));
                                            } else if (which == 4) {
                                                if (MessagesController.getInstance().isDialogMuted(DialogsActivity.this.selectedDialog)) {
                                                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                                                    editor.putInt("notify2_" + DialogsActivity.this.selectedDialog, 0);
                                                    MessagesStorage.getInstance().setDialogFlags(DialogsActivity.this.selectedDialog, 0);
                                                    editor.commit();
                                                    dialg = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(DialogsActivity.this.selectedDialog));
                                                    if (dialg != null) {
                                                        dialg.notify_settings = new TLRPC$TL_peerNotifySettings();
                                                    }
                                                    NotificationsController.updateServerNotificationsSettings(DialogsActivity.this.selectedDialog);
                                                    return;
                                                }
                                                DialogsActivity.this.showDialog(AlertsCreator.createMuteAlert(DialogsActivity.this.getParentActivity(), DialogsActivity.this.selectedDialog));
                                            } else if (which == 5) {
                                                DialogsActivity.this.markAsReadDialog(false);
                                            } else if (which == 6) {
                                                DialogsActivity.this.addShortcut();
                                            } else if (which == 7) {
                                                boolean wasHideChat = AppPreferences.isHiddenChat(context2, DialogsActivity.this.selectedDialog);
                                                if (wasHideChat) {
                                                    CharSequence hideDialog = LocaleController.getString(JoinPoint.SYNCHRONIZATION_UNLOCK, R.string.unlock);
                                                } else {
                                                    String string = LocaleController.getString(JoinPoint.SYNCHRONIZATION_LOCK, R.string.lock);
                                                }
                                                DialogsActivity.this.lockChat(context2, wasHideChat, DialogsActivity.this.selectedDialog);
                                            } else {
                                                Log.d("alireza", "alireza selectedDialog" + DialogsActivity.this.selectedDialog);
                                                AlertDialog.Builder builder = new AlertDialog.Builder(DialogsActivity.this.getParentActivity());
                                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                                if (which == 1) {
                                                    builder.setMessage(LocaleController.getString("AreYouSureClearHistory", R.string.AreYouSureClearHistory));
                                                } else if (z2) {
                                                    builder.setMessage(LocaleController.getString("AreYouSureDeleteAndExit", R.string.AreYouSureDeleteAndExit));
                                                } else {
                                                    builder.setMessage(LocaleController.getString("AreYouSureDeleteThisChat", R.string.AreYouSureDeleteThisChat));
                                                }
                                                final int i = which;
                                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        if (i != 1) {
                                                            if (z2) {
                                                                TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf((int) (-DialogsActivity.this.selectedDialog)));
                                                                if (currentChat == null || !ChatObject.isNotInChat(currentChat)) {
                                                                    MessagesController.getInstance().deleteUserFromChat((int) (-DialogsActivity.this.selectedDialog), MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId())), null);
                                                                } else {
                                                                    MessagesController.getInstance().deleteDialog(DialogsActivity.this.selectedDialog, 0);
                                                                }
                                                            } else {
                                                                MessagesController.getInstance().deleteDialog(DialogsActivity.this.selectedDialog, 0);
                                                            }
                                                            if (isBot) {
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
                            string2 = dialog.pinned ? LocaleController.getString("UnpinFromTop", R.string.UnpinFromTop) : LocaleController.getString("PinToTop", R.string.PinToTop);
                            charSequenceArr[0] = string2;
                            charSequenceArr[1] = LocaleController.getString("ClearHistory", R.string.ClearHistory);
                            if (isChat) {
                            }
                            charSequenceArr[2] = string2;
                            charSequenceArr[3] = cs2;
                            charSequenceArr[4] = cs;
                            charSequenceArr[5] = LocaleController.getString("MarkAsRead", R.string.MarkAsRead);
                            charSequenceArr[6] = csa;
                            charSequenceArr[7] = wasLock;
                            iArr = new int[8];
                            if (dialog.pinned) {
                            }
                            iArr[0] = dialog.pinned ? R.drawable.chats_unpin : R.drawable.chats_pin;
                            iArr[1] = R.drawable.chats_clear;
                            if (isChat) {
                            }
                            iArr[2] = isChat ? R.drawable.chats_leave : R.drawable.chats_delete;
                            if (Favourite.isFavourite(Long.valueOf(dialog.id))) {
                                i = R.drawable.ic_off_star;
                            } else {
                                i = R.drawable.ic_star;
                            }
                            iArr[3] = i;
                            iArr[4] = R.drawable.notifications_s_on;
                            iArr[5] = R.drawable.ic_mark_as_read;
                            iArr[6] = R.drawable.ic_fast;
                            if (wasHideChat) {
                                i = R.drawable.lock_close;
                            } else {
                                i = R.drawable.lock_close_off;
                            }
                            iArr[7] = i;
                            z = pinned;
                            z2 = isChat;
                            builder.setItems(charSequenceArr, iArr, /* anonymous class already generated */);
                            DialogsActivity.this.showDialog(builder.create());
                        }
                    } else if (DialogsActivity.this.dialogsType != 3 || DialogsActivity.this.selectAlertString != null) {
                        return false;
                    } else {
                        DialogsActivity.this.dialogsAdapter.addOrRemoveSelectedDialog(dialog.id, view);
                        DialogsActivity.this.updateSelectedCount();
                    }
                    return true;
                } else if (!(DialogsActivity.this.dialogsSearchAdapter.getItem(position) instanceof String) && !DialogsActivity.this.dialogsSearchAdapter.isRecentSearchDisplayed()) {
                    return false;
                } else {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(DialogsActivity.this.getParentActivity());
                    builder2.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder2.setMessage(LocaleController.getString("ClearSearch", R.string.ClearSearch));
                    builder2.setPositiveButton(LocaleController.getString("ClearButton", R.string.ClearButton).toUpperCase(), new C28871());
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
        Drawable drawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_chats_actionBackground), Theme.getColor(Theme.key_chats_actionPressedBackground));
        if (VERSION.SDK_INT < 21) {
            Drawable shadowDrawable = context.getResources().getDrawable(R.drawable.floating_shadow).mutate();
            shadowDrawable.setColorFilter(new PorterDuffColorFilter(-16777216, Mode.MULTIPLY));
            Drawable combinedDrawable = new CombinedDrawable(shadowDrawable, drawable, 0, 0);
            combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
            drawable = combinedDrawable;
        }
        this.floatingButton.setBackgroundDrawable(drawable);
        this.floatingButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), Mode.MULTIPLY));
        this.floatingButton.setImageResource(R.drawable.floating_pencil);
        if (VERSION.SDK_INT >= 21) {
            StateListAnimator animator = new StateListAnimator();
            animator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.floatingButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
            animator.addState(new int[0], ObjectAnimator.ofFloat(this.floatingButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
            this.floatingButton.setStateListAnimator(animator);
            this.floatingButton.setOutlineProvider(new C28949());
        }
        if (!this.isSelectMode) {
            float f2;
            int i;
            float f3;
            float f4;
            sizeNotifierFrameLayout = this.contentView;
            view = this.floatingButton;
            int i2 = VERSION.SDK_INT >= 21 ? 56 : 60;
            if (VERSION.SDK_INT >= 21) {
                f2 = 56.0f;
            } else {
                f2 = 60.0f;
            }
            if (LocaleController.isRTL) {
                i = 3;
            } else {
                i = 5;
            }
            i |= 80;
            if (LocaleController.isRTL) {
                f3 = 14.0f;
            } else {
                f3 = 0.0f;
            }
            if (LocaleController.isRTL) {
                f4 = 0.0f;
            } else {
                f4 = 14.0f;
            }
            sizeNotifierFrameLayout.addView(view, LayoutHelper.createFrame(i2, f2, i, f3, 0.0f, f4, (float) (this.bottomBarHeight + 14)));
            this.floatingButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putBoolean("destroyAfterSelect", true);
                    DialogsActivity.this.presentFragment(new ContactsActivity(args));
                }
            });
        }
        this.tabsView = new FrameLayout(context);
        createTabs(context);
        this.contentView.addView(this.tabsView, LayoutHelper.createFrame(-1, (float) this.tabsHeight, 48, 0.0f, 0.0f, 0.0f, 0.0f));
        this.listView.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 1 && DialogsActivity.this.searching && DialogsActivity.this.searchWas) {
                    AndroidUtilities.hideKeyboard(DialogsActivity.this.getParentActivity().getCurrentFocus());
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int firstVisibleItem = DialogsActivity.this.layoutManager.findFirstVisibleItemPosition();
                int visibleItemCount = Math.abs(DialogsActivity.this.layoutManager.findLastVisibleItemPosition() - firstVisibleItem) + 1;
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                if (!DialogsActivity.this.searching || !DialogsActivity.this.searchWas) {
                    if (visibleItemCount > 0 && DialogsActivity.this.layoutManager.findLastVisibleItemPosition() >= DialogsActivity.this.getDialogsArray().size() - 10) {
                        boolean fromCache = !MessagesController.getInstance().dialogsEndReached;
                        if (fromCache || !MessagesController.getInstance().serverDialogsEndReached) {
                            MessagesController.getInstance().loadDialogs(-1, 100, fromCache);
                        }
                    }
                    if (DialogsActivity.this.floatingButton.getVisibility() != 8) {
                        boolean goingDown;
                        View topChild = recyclerView.getChildAt(0);
                        int firstViewTop = 0;
                        if (topChild != null) {
                            firstViewTop = topChild.getTop();
                        }
                        boolean changed = true;
                        if (DialogsActivity.this.prevPosition == firstVisibleItem) {
                            int topDelta = DialogsActivity.this.prevTop - firstViewTop;
                            goingDown = firstViewTop < DialogsActivity.this.prevTop;
                            changed = Math.abs(topDelta) > 1;
                        } else {
                            goingDown = firstVisibleItem > DialogsActivity.this.prevPosition;
                        }
                        if (changed && DialogsActivity.this.scrollUpdated) {
                            DialogsActivity.this.hideFloatingButton(goingDown);
                        }
                        DialogsActivity.this.prevPosition = firstVisibleItem;
                        DialogsActivity.this.prevTop = firstViewTop;
                        DialogsActivity.this.scrollUpdated = true;
                    }
                } else if (visibleItemCount > 0 && DialogsActivity.this.layoutManager.findLastVisibleItemPosition() == totalItemCount - 1 && !DialogsActivity.this.dialogsSearchAdapter.isMessagesSearchEndReached()) {
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
        int type = 0;
        if (this.searchString != null) {
            type = 2;
        } else if (!this.onlySelect) {
            type = 1;
        }
        this.dialogsSearchAdapter = new DialogsSearchAdapter(context, type, this.dialogsType);
        this.dialogsSearchAdapter.setDelegate(new DialogsSearchAdapterDelegate() {
            public void searchStateChanged(boolean search) {
                if (!DialogsActivity.this.searching || !DialogsActivity.this.searchWas || DialogsActivity.this.searchEmptyView == null) {
                    return;
                }
                if (search) {
                    DialogsActivity.this.searchEmptyView.showProgress();
                } else {
                    DialogsActivity.this.searchEmptyView.showTextView();
                }
            }

            public void didPressedOnSubDialog(long did) {
                if (!DialogsActivity.this.onlySelect) {
                    int lower_id = (int) did;
                    Bundle args = new Bundle();
                    if (lower_id > 0) {
                        args.putInt("user_id", lower_id);
                    } else {
                        args.putInt("chat_id", -lower_id);
                    }
                    if (DialogsActivity.this.actionBar != null) {
                        DialogsActivity.this.actionBar.closeSearchField();
                    }
                    if (AndroidUtilities.isTablet() && DialogsActivity.this.dialogsAdapter != null) {
                        DialogsActivity.this.dialogsAdapter.setOpenedDialogId(DialogsActivity.this.openedDialogId = did);
                        DialogsActivity.this.updateVisibleRows(512);
                    }
                    if (DialogsActivity.this.searchString != null) {
                        if (MessagesController.checkCanOpenChat(args, DialogsActivity.this)) {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                            DialogsActivity.this.presentFragment(new ChatActivity(args));
                        }
                    } else if (MessagesController.checkCanOpenChat(args, DialogsActivity.this)) {
                        DialogsActivity.this.presentFragment(new ChatActivity(args));
                    }
                } else if (DialogsActivity.this.dialogsAdapter.hasSelectedDialogs()) {
                    DialogsActivity.this.dialogsAdapter.addOrRemoveSelectedDialog(did, null);
                    DialogsActivity.this.updateSelectedCount();
                    DialogsActivity.this.actionBar.closeSearchField();
                } else {
                    DialogsActivity.this.didSelectResult(did, true, false);
                }
            }

            public void needRemoveHint(final int did) {
                if (DialogsActivity.this.getParentActivity() != null && MessagesController.getInstance().getUser(Integer.valueOf(did)) != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DialogsActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setMessage(LocaleController.formatString("ChatHintsDelete", R.string.ChatHintsDelete, new Object[]{ContactsController.formatName(user.first_name, user.last_name)}));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SearchQuery.removePeer(did);
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    DialogsActivity.this.showDialog(builder.create());
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
            sizeNotifierFrameLayout = this.contentView;
            View fragmentContextView = new FragmentContextView(context, this, true);
            this.fragmentLocationContextView = fragmentContextView;
            sizeNotifierFrameLayout.addView(fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, 0.0f, -36.0f, 0.0f, 0.0f));
            sizeNotifierFrameLayout = this.contentView;
            fragmentContextView = new FragmentContextView(context, this, false);
            this.fragmentContextView = fragmentContextView;
            sizeNotifierFrameLayout.addView(fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, 0.0f, -36.0f, 0.0f, 0.0f));
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
                public void onMessageSend(CharSequence message) {
                    if (DialogsActivity.this.delegate != null) {
                        ArrayList<Long> selectedDialogs = DialogsActivity.this.dialogsAdapter.getSelectedDialogs();
                        if (!selectedDialogs.isEmpty()) {
                            DialogsActivity.this.delegate.didSelectDialogs(DialogsActivity.this, selectedDialogs, message, false);
                        }
                    }
                }

                public void onSwitchRecordMode(boolean video) {
                }

                public void onPreAudioVideoRecord() {
                }

                public void onTextChanged(CharSequence text, boolean bigChange) {
                }

                public void needSendTyping() {
                }

                public void onAttachButtonHidden() {
                }

                public void onAttachButtonShow() {
                }

                public void onMessageEditEnd(boolean loading) {
                }

                public void onWindowSizeChanged(int size) {
                }

                public void onStickersTab(boolean opened) {
                }

                public void didPressedAttachButton() {
                }

                public void needStartRecordVideo(int state) {
                }

                public void needChangeVideoPreviewState(int state, float seekProgress) {
                }

                public void needStartRecordAudio(int state) {
                }

                public void needShowMediaBanHint() {
                }
            });
        }
        this.slsHotPostActivity = null;
        this.slsSearchActivity = null;
        this.newsListActivity = null;
        this.electionActivity = null;
        this.bottomBar = (BottomBar) LayoutInflater.from(getParentActivity()).inflate(R.layout.bottom_bar, null);
        if (this.isSelectMode) {
            this.bottomBar.setVisibility(8);
            this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
            this.actionBar.getBackButton().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MessagesController.getInstance().unSelectAllDialogs();
                    DialogsActivity.this.finishFragment();
                }
            });
        } else {
            this.bottomBar.setVisibility(0);
        }
        this.contentView.addView(this.bottomBar, LayoutHelper.createFrame(-1, 50.0f, 80, 0.0f, 0.0f, 0.0f, 0.0f));
        this.bottomBar.selectTabAtPosition(3);
        if (!AppPreferences.shouldShowNewsTab(getParentActivity())) {
            this.bottomBar.getTabWithId(R.id.tab_news).setVisibility(8);
        }
        if (!AppPreferences.shouldShowHotTab(getParentActivity())) {
            this.bottomBar.getTabWithId(R.id.tab_hottest).setVisibility(8);
        }
        if (!AppPreferences.shouldShowSearchTab(getParentActivity())) {
            this.bottomBar.getTabWithId(R.id.tab_search).setVisibility(8);
        }
        this.bottomBar.setActiveTabColor(ContextCompat.getColor(getParentActivity(), R.color.white));
        this.bottomBar.setInActiveTabColor(ContextCompat.getColor(getParentActivity(), R.color.gray));
        this.bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            public void onTabSelected(@IdRes int tabId) {
                DialogsActivity.this.onBottomBarTabSelected(tabId);
            }
        });
        LocalBroadcastManager.getInstance(getParentActivity()).registerReceiver(this.mMessageReceiver, new IntentFilter(org.telegram.customization.util.Constants.ACTION_SWITCH_TAB));
        LocalBroadcastManager.getInstance(getParentActivity()).registerReceiver(this.mMessageReceiver, new IntentFilter(org.telegram.customization.util.Constants.ACTION_FAVE_CHANGED));
        LocalBroadcastManager.getInstance(getParentActivity()).registerReceiver(this.mMessageReceiver, new IntentFilter(org.telegram.customization.util.Constants.ACTION_REBUILD_ALL));
        Prefs.setFirstLaunchDone(1);
        return this.contentView;
    }

    public void onResume() {
        super.onResume();
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
            Activity activity = getParentActivity();
            if (activity != null) {
                this.checkPermission = false;
                if (!(activity.checkSelfPermission("android.permission.READ_CONTACTS") == 0 && activity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
                    AlertDialog.Builder builder;
                    Dialog create;
                    if (activity.shouldShowRequestPermissionRationale("android.permission.READ_CONTACTS")) {
                        builder = new AlertDialog.Builder(activity);
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("PermissionContacts", R.string.PermissionContacts));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                        create = builder.create();
                        this.permissionDialog = create;
                        showDialog(create);
                    } else if (activity.shouldShowRequestPermissionRationale("android.permission.WRITE_EXTERNAL_STORAGE")) {
                        builder = new AlertDialog.Builder(activity);
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
            if (AppPreferences.isAdsEnable(ApplicationLoader.applicationContext) && AppPreferences.isShowCoinIcon(ApplicationLoader.applicationContext)) {
                this.menu.getItem(9).setVisibility(0);
                updatePasscodeButton();
                if (this.actionBar == null) {
                    if (AppPreferences.getSubtitle(getParentActivity()) != null) {
                    }
                    this.actionBar.setSubtitle("");
                }
                return;
            }
            this.menu.getItem(9).setVisibility(8);
            updatePasscodeButton();
            if (this.actionBar == null) {
                return;
            }
            if (AppPreferences.getSubtitle(getParentActivity()) != null || TextUtils.isEmpty(AppPreferences.getSubtitle(getParentActivity()))) {
                this.actionBar.setSubtitle("");
            } else {
                this.actionBar.setSubtitle(AppPreferences.getSubtitle(getParentActivity()));
            }
        } catch (Exception e) {
        }
    }

    public void onPause() {
        super.onPause();
        this.onResumeCalled = false;
        if (this.commentView != null) {
            this.commentView.onResume();
        }
    }

    private void updateSelectedCount() {
        if (this.commentView != null) {
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (this.dialogsAdapter.hasSelectedDialogs()) {
                if (this.commentView.getTag() == null) {
                    this.commentView.setFieldText("");
                    this.commentView.setVisibility(0);
                    animatorSet = new AnimatorSet();
                    animatorArr = new Animator[1];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.commentView, "translationY", new float[]{(float) this.commentView.getMeasuredHeight(), 0.0f});
                    animatorSet.playTogether(animatorArr);
                    animatorSet.setDuration(180);
                    animatorSet.setInterpolator(new DecelerateInterpolator());
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
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
                animatorArr[0] = ObjectAnimator.ofFloat(this.commentView, "translationY", new float[]{0.0f, (float) this.commentView.getMeasuredHeight()});
                animatorSet.playTogether(animatorArr);
                animatorSet.setDuration(180);
                animatorSet.setInterpolator(new DecelerateInterpolator());
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        DialogsActivity.this.commentView.setVisibility(8);
                    }
                });
                animatorSet.start();
                this.commentView.setTag(null);
                this.listView.requestLayout();
            }
        }
    }

    @TargetApi(23)
    private void askForPermissons() {
        Activity activity = getParentActivity();
        if (activity != null) {
            ArrayList<String> permissons = new ArrayList();
            if (activity.checkSelfPermission("android.permission.READ_CONTACTS") != 0) {
                permissons.add("android.permission.READ_CONTACTS");
                permissons.add("android.permission.WRITE_CONTACTS");
                permissons.add("android.permission.GET_ACCOUNTS");
            }
            if (activity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                permissons.add("android.permission.READ_EXTERNAL_STORAGE");
                permissons.add("android.permission.WRITE_EXTERNAL_STORAGE");
            }
            try {
                activity.requestPermissions((String[]) permissons.toArray(new String[permissons.size()]), 1);
            } catch (Exception e) {
            }
        }
    }

    protected void onDialogDismiss(Dialog dialog) {
        super.onDialogDismiss(dialog);
        if (this.permissionDialog != null && dialog == this.permissionDialog && getParentActivity() != null) {
            askForPermissons();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!this.onlySelect && this.floatingButton != null) {
            this.floatingButton.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    DialogsActivity.this.floatingButton.setTranslationY(DialogsActivity.this.floatingHidden ? (float) AndroidUtilities.dp(100.0f) : 0.0f);
                    DialogsActivity.this.floatingButton.setClickable(!DialogsActivity.this.floatingHidden);
                    if (DialogsActivity.this.floatingButton != null) {
                        DialogsActivity.this.floatingButton.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }

    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            int a = 0;
            while (a < permissions.length) {
                if (grantResults.length > a && grantResults[a] == 0) {
                    String str = permissions[a];
                    Object obj = -1;
                    switch (str.hashCode()) {
                        case 1365911975:
                            if (str.equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                                int i = 1;
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
                a++;
            }
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.dialogsNeedReload) {
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
            } catch (Exception e) {
                FileLog.e(e);
            }
        } else if (id == NotificationCenter.emojiDidLoaded) {
            updateVisibleRows(0);
        } else if (id == NotificationCenter.updateInterfaces) {
            updateVisibleRows(((Integer) args[0]).intValue());
        } else if (id == NotificationCenter.appDidLogout) {
            dialogsLoaded = false;
        } else if (id == NotificationCenter.encryptedChatUpdated) {
            updateVisibleRows(0);
        } else if (id == NotificationCenter.contactsDidLoaded) {
            updateVisibleRows(0);
        } else if (id == NotificationCenter.openedChatChanged) {
            if (this.dialogsType == 0 && AndroidUtilities.isTablet()) {
                boolean close = ((Boolean) args[1]).booleanValue();
                long dialog_id = ((Long) args[0]).longValue();
                if (!close) {
                    this.openedDialogId = dialog_id;
                } else if (dialog_id == this.openedDialogId) {
                    this.openedDialogId = 0;
                }
                if (this.dialogsAdapter != null) {
                    this.dialogsAdapter.setOpenedDialogId(this.openedDialogId);
                }
                updateVisibleRows(512);
            }
        } else if (id == NotificationCenter.notificationsSettingsUpdated) {
            updateVisibleRows(0);
        } else if (id == NotificationCenter.messageReceivedByAck || id == NotificationCenter.messageReceivedByServer || id == NotificationCenter.messageSendError) {
            updateVisibleRows(4096);
        } else if (id == NotificationCenter.didSetPasscode) {
            updatePasscodeButton();
        } else if (id == NotificationCenter.needReloadRecentDialogsSearch) {
            if (this.dialogsSearchAdapter != null) {
                this.dialogsSearchAdapter.loadRecentSearch();
            }
        } else if (id == NotificationCenter.didLoadedReplyMessages) {
            updateVisibleRows(0);
        } else if (id == NotificationCenter.reloadHints) {
            if (this.dialogsSearchAdapter != null) {
                this.dialogsSearchAdapter.notifyDataSetChanged();
            }
        } else if (id == NotificationCenter.refreshTabs) {
            Log.d("alireza", "refreshn tab : " + ((Integer) args[0]).intValue());
            if (this.actionBar != null) {
                if (AppPreferences.getSubtitle(getParentActivity()) == null || TextUtils.isEmpty(AppPreferences.getSubtitle(getParentActivity()))) {
                    this.actionBar.setSubtitle("");
                } else {
                    this.actionBar.setSubtitle(AppPreferences.getSubtitle(getParentActivity()));
                }
            }
            updateTabs();
            MessagesController.getInstance().sortDialogs(null);
            hideShowTabs(((Integer) args[0]).intValue());
            unreadCount();
        }
    }

    private ArrayList<TLRPC$TL_dialog> getDialogsArray() {
        if (this.dialogsType == 0) {
            return MessagesController.getInstance().dialogsAll;
        }
        if (this.dialogsType == 1) {
            return MessagesController.getInstance().dialogsServerOnly;
        }
        if (this.dialogsType == 2) {
            return MessagesController.getInstance().dialogsGroupsOnly;
        }
        if (this.dialogsType == 13) {
            return MessagesController.getInstance().dialogsForward;
        }
        if (this.dialogsType == 3) {
            return MessagesController.getInstance().dialogsUsers;
        }
        if (this.dialogsType == 4) {
            return MessagesController.getInstance().dialogsGroups;
        }
        if (this.dialogsType == 5) {
            return MessagesController.getInstance().dialogsChannels;
        }
        if (this.dialogsType == 6) {
            return MessagesController.getInstance().dialogsBots;
        }
        if (this.dialogsType == 7) {
            return MessagesController.getInstance().dialogsMegaGroups;
        }
        if (this.dialogsType == 8) {
            return MessagesController.getInstance().dialogsFavs;
        }
        if (this.dialogsType == 9) {
            return MessagesController.getInstance().dialogsGroupsAll;
        }
        if (this.dialogsType == 10) {
            return MessagesController.getInstance().dialogsHidden;
        }
        if (this.dialogsType == 11) {
            return MessagesController.getInstance().dialogsUnread;
        }
        if (this.dialogsType == 12) {
            return MessagesController.getInstance().dialogsAds;
        }
        return null;
    }

    public void setSideMenu(RecyclerView recyclerView) {
        this.sideMenu = recyclerView;
        this.sideMenu.setBackgroundColor(Theme.getColor(Theme.key_chats_menuBackground));
        this.sideMenu.setGlowColor(Theme.getColor(Theme.key_chats_menuBackground));
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

    private void hideFloatingButton(boolean hide) {
        if (System.currentTimeMillis() - this.lastAnimUpdate >= 300) {
            this.lastAnimUpdate = System.currentTimeMillis();
            if (this.floatingHidden != hide) {
                boolean z;
                this.floatingHidden = hide;
                ImageView imageView = this.floatingButton;
                String str = "translationY";
                float[] fArr = new float[1];
                fArr[0] = this.floatingHidden ? (float) AndroidUtilities.dp(100.0f) : 0.0f;
                ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, str, fArr).setDuration(300);
                animator.setInterpolator(this.floatingInterpolator);
                imageView = this.floatingButton;
                if (hide) {
                    z = false;
                } else {
                    z = true;
                }
                imageView.setClickable(z);
                animator.start();
            }
        }
    }

    private void updateVisibleRows(int mask) {
        if (this.listView != null) {
            int count = this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = this.listView.getChildAt(a);
                if (child instanceof DialogCell) {
                    if (this.listView.getAdapter() != this.dialogsSearchAdapter) {
                        DialogCell cell = (DialogCell) child;
                        if ((mask & 2048) != 0) {
                            cell.checkCurrentDialogIndex();
                            if (this.dialogsType == 0 && AndroidUtilities.isTablet()) {
                                boolean z;
                                if (cell.getDialogId() == this.openedDialogId) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                cell.setDialogSelected(z);
                            }
                        } else if ((mask & 512) == 0) {
                            cell.update(mask);
                        } else if (this.dialogsType == 0 && AndroidUtilities.isTablet()) {
                            cell.setDialogSelected(cell.getDialogId() == this.openedDialogId);
                        }
                    }
                } else if (child instanceof UserCell) {
                    ((UserCell) child).update(mask);
                } else if (child instanceof ProfileSearchCell) {
                    ((ProfileSearchCell) child).update(mask);
                } else if (child instanceof RecyclerListView) {
                    RecyclerListView innerListView = (RecyclerListView) child;
                    int count2 = innerListView.getChildCount();
                    for (int b = 0; b < count2; b++) {
                        View child2 = innerListView.getChildAt(b);
                        if (child2 instanceof HintDialogCell) {
                            ((HintDialogCell) child2).checkUnreadCounter(mask);
                        }
                    }
                }
            }
        }
    }

    public void setDelegate(DialogsActivityDelegate dialogsActivityDelegate) {
        this.delegate = dialogsActivityDelegate;
    }

    public void setSearchString(String string) {
        this.searchString = string;
    }

    public boolean isMainDialogList() {
        return this.delegate == null && this.searchString == null;
    }

    private void didSelectResult(final long dialog_id, boolean useAlert, boolean param) {
        TLRPC$Chat chat;
        if (this.addToGroupAlertString == null && ((int) dialog_id) < 0) {
            chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) dialog_id)));
            if (ChatObject.isChannel(chat) && !chat.megagroup && (this.cantSendToChannels || !ChatObject.isCanWriteToChannel(-((int) dialog_id)))) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setMessage(LocaleController.getString("ChannelCantSendMessage", R.string.ChannelCantSendMessage));
                builder.setNegativeButton(LocaleController.getString("OK", R.string.OK), null);
                showDialog(builder.create());
                return;
            }
        }
        if (!useAlert || ((this.selectAlertString == null || this.selectAlertStringGroup == null) && this.addToGroupAlertString == null)) {
            if (this.delegate != null) {
                ArrayList<Long> dids = new ArrayList();
                dids.add(Long.valueOf(dialog_id));
                this.delegate.didSelectDialogs(this, dids, null, param);
                this.delegate = null;
                return;
            }
            finishFragment();
        } else if (getParentActivity() != null) {
            builder = new AlertDialog.Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            int lower_part = (int) dialog_id;
            int high_id = (int) (dialog_id >> 32);
            if (lower_part == 0) {
                if (MessagesController.getInstance().getUser(Integer.valueOf(MessagesController.getInstance().getEncryptedChat(Integer.valueOf(high_id)).user_id)) != null) {
                    builder.setMessage(LocaleController.formatStringSimple(this.selectAlertString, new Object[]{UserObject.getUserName(user)}));
                } else {
                    return;
                }
            } else if (high_id == 1) {
                if (MessagesController.getInstance().getChat(Integer.valueOf(lower_part)) != null) {
                    builder.setMessage(LocaleController.formatStringSimple(this.selectAlertStringGroup, new Object[]{chat.title}));
                } else {
                    return;
                }
            } else if (lower_part == UserConfig.getClientUserId()) {
                builder.setMessage(LocaleController.formatStringSimple(this.selectAlertStringGroup, new Object[]{LocaleController.getString("SavedMessages", R.string.SavedMessages)}));
            } else if (lower_part > 0) {
                if (MessagesController.getInstance().getUser(Integer.valueOf(lower_part)) != null) {
                    builder.setMessage(LocaleController.formatStringSimple(this.selectAlertString, new Object[]{UserObject.getUserName(user)}));
                } else {
                    return;
                }
            } else if (lower_part < 0) {
                if (MessagesController.getInstance().getChat(Integer.valueOf(-lower_part)) == null) {
                    return;
                }
                if (this.addToGroupAlertString != null) {
                    builder.setMessage(LocaleController.formatStringSimple(this.addToGroupAlertString, new Object[]{chat.title}));
                } else {
                    builder.setMessage(LocaleController.formatStringSimple(this.selectAlertStringGroup, new Object[]{chat.title}));
                }
            }
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    DialogsActivity.this.didSelectResult(dialog_id, false, false);
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            showDialog(builder.create());
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new ThemeDescriptionDelegate() {
            public void didSetColor(int color) {
                int a;
                int count = DialogsActivity.this.listView.getChildCount();
                for (a = 0; a < count; a++) {
                    View child = DialogsActivity.this.listView.getChildAt(a);
                    if (child instanceof ProfileSearchCell) {
                        ((ProfileSearchCell) child).update(0);
                    } else if (child instanceof DialogCell) {
                        ((DialogCell) child).update(0);
                    }
                }
                RecyclerListView recyclerListView = DialogsActivity.this.dialogsSearchAdapter.getInnerListView();
                if (recyclerListView != null) {
                    count = recyclerListView.getChildCount();
                    for (a = 0; a < count; a++) {
                        child = recyclerListView.getChildAt(a);
                        if (child instanceof HintDialogCell) {
                            ((HintDialogCell) child).update();
                        }
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
        themeDescriptionArr[18] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[19] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[20] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[21] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[22] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[23] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[24] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundPink);
        themeDescriptionArr[25] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundSaved);
        themeDescriptionArr[26] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_countPaint, null, null, Theme.key_chats_unreadCounter);
        themeDescriptionArr[27] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_countGrayPaint, null, null, Theme.key_chats_unreadCounterMuted);
        themeDescriptionArr[28] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_countTextPaint, null, null, Theme.key_chats_unreadCounterText);
        themeDescriptionArr[29] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class, ProfileSearchCell.class}, Theme.dialogs_namePaint, null, null, Theme.key_chats_name);
        themeDescriptionArr[30] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class, ProfileSearchCell.class}, Theme.dialogs_nameEncryptedPaint, null, null, Theme.key_chats_secretName);
        themeDescriptionArr[31] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class, ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_lockDrawable}, null, Theme.key_chats_secretIcon);
        themeDescriptionArr[32] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class, ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_groupDrawable, Theme.dialogs_broadcastDrawable, Theme.dialogs_botDrawable}, null, Theme.key_chats_nameIcon);
        themeDescriptionArr[33] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, null, new Drawable[]{Theme.dialogs_pinnedDrawable}, null, Theme.key_chats_pinnedIcon);
        themeDescriptionArr[34] = new ThemeDescription(this.listView, 0, new Class[]{DialogCell.class}, Theme.dialogs_messagePaint, null, null, Theme.key_chats_message);
        themeDescriptionArr[35] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_chats_nameMessage);
        themeDescriptionArr[36] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_chats_draft);
        themeDescriptionArr[37] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_chats_attachMessage);
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
        themeDescriptionArr[TransportMediator.KEYCODE_MEDIA_PLAY] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_background);
        themeDescriptionArr[127] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_time);
        themeDescriptionArr[128] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_progressBackground);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_AC3] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_progress);
        themeDescriptionArr[130] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_placeholder);
        themeDescriptionArr[131] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_placeholderBackground);
        themeDescriptionArr[132] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_button);
        themeDescriptionArr[133] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_player_buttonActive);
        return themeDescriptionArr;
    }

    public void onBottomBarTabSelected(int tabId) {
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
        switch (tabId) {
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
        switch (tabId) {
            case R.id.tab_news:
                if (this.newsListActivity == null) {
                    this.newsListActivity = new NewsListActivity(getParentActivity());
                    this.contentView.addView(this.newsListActivity, LayoutHelper.createFrame(-1, -1.0f, 0, 0.0f, 0.0f, 0.0f, (float) this.bottomBarHeight));
                }
                this.newsListActivity.setVisibility(0);
                HomeViewManager.getInstance().addViewsToStack("FRG_NEWS_LIST");
                this.actionBar.setTitle(getParentActivity().getString(R.string.hot_news));
                this.floatingButton.setVisibility(8);
                Utilities.addViewActionByActionName("News TAB");
                hideActionbarItems();
                return;
            case R.id.tab_search:
                if (this.slsSearchActivity == null) {
                    this.slsSearchActivity = new SlsHotPostActivity(getParentActivity(), 2, 2, this.actionBar);
                    this.contentView.addView(this.slsSearchActivity, LayoutHelper.createFrame(-1, -1.0f, 48, 0.0f, 0.0f, 0.0f, (float) this.bottomBarHeight));
                }
                this.slsSearchActivity.setVisibility(0);
                HomeViewManager.getInstance().addViewsToStack(Constants.FRG_SEARCH);
                this.floatingButton.setVisibility(8);
                this.actionBar.actionBarFontSize = 12;
                Utilities.addViewActionByActionName("Search TAB");
                hideActionbarItems();
                this.actionBar.setTitle(this.actionBar.searchTabTitle);
                return;
            case R.id.tab_hottest:
                if (this.slsHotPostActivity == null) {
                    this.slsHotPostActivity = new SlsHotPostActivity(getParentActivity(), 1, 1, this.actionBar);
                    this.contentView.addView(this.slsHotPostActivity, LayoutHelper.createFrame(-1, -1.0f, 48, 0.0f, 0.0f, 0.0f, (float) this.bottomBarHeight));
                }
                this.slsHotPostActivity.setVisibility(0);
                HomeViewManager.getInstance().addViewsToStack("FRG_HOT");
                this.actionBar.setTitle(getParentActivity().getString(R.string.hot_posts));
                this.actionBar.actionBarFontSize = -1;
                this.floatingButton.setVisibility(8);
                Utilities.addViewActionByActionName("HOT TAB");
                hideActionbarItems();
                return;
            case R.id.tab_home:
                this.listView.setVisibility(0);
                HomeViewManager.getInstance().addViewsToStack(Constants.FRG_HOME);
                if (!this.isSelectMode) {
                    this.actionBar.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                }
                this.actionBar.actionBarFontSize = -1;
                this.actionBar.setVisibility(0);
                this.floatingButton.setVisibility(0);
                Utilities.addViewActionByActionName("Dialog TAB");
                addActionbarItems(getParentActivity());
                this.actionBar.getTitleTextView().setGravity(3);
                return;
            default:
                return;
        }
    }

    private void hideActionbarItems() {
        if (this.actionBar != null) {
            this.actionBar.createMenu().clearItems();
            this.actionBar.getTitleTextView().setGravity(5);
        }
    }

    private void addActionbarItems(final Context context) {
        if (this.isSelectMode) {
            this.parentLayout.setInSelectMode(true);
            this.menu.addItem(7, (int) R.drawable.ic_checkbox_multiple_blank_outline_white_24dp).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
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
            this.menu.addItem(6, (int) R.drawable.ic_done).setOnClickListener(new View.OnClickListener() {

                /* renamed from: org.telegram.ui.DialogsActivity$22$1 */
                class C28711 implements OnClickListener {
                    C28711() {
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

                public void onClick(View v) {
                    String dialogMessage = DialogsActivity.this.getActionMessage();
                    AlertDialog.Builder builder = new AlertDialog.Builder(DialogsActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                    builder.setMessage(dialogMessage);
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C28711());
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
        if (AppPreferences.isAdsEnable(ApplicationLoader.applicationContext)) {
            this.menu.addItem(9, getParentActivity().getResources().getDrawable(R.drawable.coins_act)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (AppPreferences.getAdsChannel(ApplicationLoader.applicationContext) == null || AppPreferences.getAdsChannel(ApplicationLoader.applicationContext).size() <= 0) {
                        DialogsActivity.this.presentFragment(new JoinAdsActivity(), false);
                    } else {
                        DialogsActivity.this.presentFragment(new AdsTransactionActivity(), false);
                    }
                }
            });
        }
        try {
            if (AppPreferences.isShowCoinIcon(ApplicationLoader.applicationContext)) {
                this.menu.getItem(9).setVisibility(0);
            } else {
                this.menu.getItem(9).setVisibility(8);
            }
        } catch (Exception e) {
        }
        this.menu.addItem(4, getParentActivity().getResources().getDrawable(AppPreferences.isShowHiddenDialogs(getParentActivity()) ? R.drawable.ic_lock_open_outline : R.drawable.ic_lock_outline)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (AppPreferences.isShowHiddenDialogs(DialogsActivity.this.getParentActivity())) {
                    DialogsActivity.this.menu.getItem(4).setIcon((int) R.drawable.ic_lock_outline);
                    AppPreferences.setShowHiddenDialogs(DialogsActivity.this.getParentActivity(), false);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(-1)});
                    return;
                }
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.password_dialog);
                dialog.setCancelable(true);
                final EditText password = (EditText) dialog.findViewById(R.id.input_password);
                TextView tvMsg = (TextView) dialog.findViewById(R.id.passDialogMessage);
                if (TextUtils.isEmpty(AppPreferences.getPasswordForLockingChats(context))) {
                    tvMsg.setText(LocaleController.getString("PleaseEnterPassword", R.string.PleaseEnterPassword));
                } else {
                    tvMsg.setText(LocaleController.getString("PleaseEnterCurrentPassword", R.string.PleaseEnterCurrentPassword));
                }
                dialog.findViewById(R.id.tvOk).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String pass = password.getText().toString();
                        if (TextUtils.isEmpty(pass)) {
                            password.setError(LocaleController.getString("PleaseEnterCurrentPassword", R.string.PleaseEnterCurrentPassword));
                            password.requestFocus();
                            return;
                        }
                        String currentPass = AppPreferences.getPasswordForLockingChats(context);
                        if (TextUtils.isEmpty(currentPass) || pass.equals(currentPass)) {
                            if (TextUtils.isEmpty(currentPass)) {
                                AppPreferences.setPasswordForLockingChats(context, pass);
                            }
                            DialogsActivity.this.menu.getItem(4).setIcon((int) R.drawable.ic_lock_open_outline);
                            AppPreferences.setShowHiddenDialogs(DialogsActivity.this.getParentActivity(), true);
                            dialog.dismiss();
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(-1)});
                            return;
                        }
                        password.setError(LocaleController.getString("PasswordDoNotMatch", R.string.PasswordDoNotMatch));
                        password.requestFocus();
                    }
                });
                dialog.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                DialogsActivity.this.showDialog(dialog);
            }
        });
        handleHiddenDialogsVisibility();
        if (Prefs.getFirstLaunchDone().intValue() != 0) {
            new Handler().postDelayed(new Runnable() {

                /* renamed from: org.telegram.ui.DialogsActivity$25$1 */
                class C28741 implements OnHidePromptListener {
                    C28741() {
                    }

                    public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                        Prefs.setGhostModeTu(DialogsActivity.this.getParentActivity(), false);
                        DialogsActivity.this.menu.getItem(2).setIcon((int) R.drawable.ic_action_ghost_off);
                    }

                    public void onHidePromptComplete() {
                        DialogsActivity.this.showHotPostTutorial();
                    }
                }

                public void run() {
                    if (!Prefs.getGhostModeTu(DialogsActivity.this.getParentActivity())) {
                        return;
                    }
                    if (DialogsActivity.this.menu == null || DialogsActivity.this.menu.getItem(2) == null || DialogsActivity.this.menu.getItem(2).getVisibility() != 0) {
                        DialogsActivity.this.showHotPostTutorial();
                    } else if (DialogsActivity.this.menu != null && DialogsActivity.this.menu.getItem(2) != null) {
                        DialogsActivity.this.menu.getItem(2).setIcon((int) R.drawable.ic_action_ghost_on);
                        new MaterialTapTargetPrompt.Builder(DialogsActivity.this.getParentActivity()).setTarget(DialogsActivity.this.menu.getItem(2)).setTextGravity(GravityCompat.END).setPrimaryTextTypeface(AppUtilities.getLightSansTypeface(DialogsActivity.this.getParentActivity())).setSecondaryTextTypeface(AppUtilities.getUltraLightSansTypeface(DialogsActivity.this.getParentActivity())).setPrimaryText(DialogsActivity.this.getParentActivity().getString(R.string.ghos_mode)).setSecondaryText(DialogsActivity.this.getParentActivity().getString(R.string.ghos_mode_tutorial)).setOnHidePromptListener(new C28741()).show();
                    }
                }
            }, FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
        }
        new Handler().postDelayed(new Runnable() {

            /* renamed from: org.telegram.ui.DialogsActivity$26$1 */
            class C28751 implements OnHidePromptListener {
                C28751() {
                }

                public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                    Prefs.setAdsTu(DialogsActivity.this.getParentActivity(), false);
                    DialogsActivity.this.menu.getItem(9).setIcon((int) R.drawable.coins_act);
                }

                public void onHidePromptComplete() {
                }
            }

            public void run() {
                if (Prefs.getAdsTu(ApplicationLoader.applicationContext) && DialogsActivity.this.menu != null && DialogsActivity.this.menu.getItem(9) != null && DialogsActivity.this.menu.getItem(9).getVisibility() == 0) {
                    DialogsActivity.this.menu.getItem(9).setIcon((int) R.drawable.coins_act);
                    new MaterialTapTargetPrompt.Builder(DialogsActivity.this.getParentActivity()).setTarget(DialogsActivity.this.menu.getItem(9)).setTextGravity(GravityCompat.END).setPrimaryTextTypeface(AppUtilities.getLightSansTypeface(DialogsActivity.this.getParentActivity())).setSecondaryTextTypeface(AppUtilities.getUltraLightSansTypeface(DialogsActivity.this.getParentActivity())).setPrimaryText(LocaleController.getString("Advertise", R.string.Advertise)).setSecondaryText(AppPreferences.getAdsTutorialDialogAct(ApplicationLoader.applicationContext)).setOnHidePromptListener(new C28751()).show();
                }
            }
        }, 3000);
        this.menu.addItem(0, (int) R.drawable.ic_ab_search);
        this.menu.addItem(1, (int) R.drawable.ic_more).setOnClickListener(new View.OnClickListener() {

            /* renamed from: org.telegram.ui.DialogsActivity$27$1 */
            class C28761 implements OnMenuItemClickListener {
                C28761() {
                }

                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == 1) {
                        DialogsActivity.this.openBatchBottomSheet();
                    } else if (item.getItemId() == 2) {
                        if (Prefs.getGhostMode(ApplicationLoader.applicationContext) == 0) {
                            Prefs.setGhostMode(DialogsActivity.this.getParentActivity(), 1);
                            Toast.makeText(DialogsActivity.this.getParentActivity().getApplicationContext(), R.string.ghost_mode_on, 0).show();
                        } else {
                            Prefs.setGhostMode(DialogsActivity.this.getParentActivity(), 0);
                            Toast.makeText(DialogsActivity.this.getParentActivity().getApplicationContext(), R.string.ghost_mode_off, 0).show();
                        }
                        MessagesController.getInstance().updateTimerProc();
                    }
                    return false;
                }
            }

            public void onClick(View v) {
                boolean ghModeIsOn = false;
                if (Prefs.getGhostMode(ApplicationLoader.applicationContext) != 0) {
                    ghModeIsOn = true;
                }
                PopupMenu popup = new PopupMenu(DialogsActivity.this.getParentActivity(), v);
                popup.getMenu().add(0, 1, 0, LocaleController.getString("MultiDialogsAction", R.string.MultiDialogsAction));
                boolean enabledLocal = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("showGhostMode", true);
                int enableServer = AppPreferences.getGhostEnable(DialogsActivity.this.getParentActivity());
                if (enabledLocal && enableServer == 2) {
                    CharSequence string;
                    Menu menu = popup.getMenu();
                    if (ghModeIsOn) {
                        string = LocaleController.getString("GhostModeOff", R.string.GhostModeOff);
                    } else {
                        string = LocaleController.getString("GhostModeOn", R.string.GhostModeOn);
                    }
                    menu.add(2, 2, 1, string);
                }
                popup.show();
                popup.setOnMenuItemClickListener(new C28761());
            }
        });
        this.menu.getItem(0).setIsSearchField(true).setActionBarMenuItemSearchListener(new ActionBarMenuItemSearchListener() {

            /* renamed from: org.telegram.ui.DialogsActivity$28$1 */
            class C28771 implements Runnable {
                C28771() {
                }

                public void run() {
                    if (DialogsActivity.this.actionBar == null) {
                        return;
                    }
                    if (AppPreferences.getSubtitle(DialogsActivity.this.getParentActivity()) == null || TextUtils.isEmpty(AppPreferences.getSubtitle(DialogsActivity.this.getParentActivity()))) {
                        DialogsActivity.this.actionBar.setSubtitle("");
                    } else {
                        DialogsActivity.this.actionBar.setSubtitle(AppPreferences.getSubtitle(DialogsActivity.this.getParentActivity()));
                    }
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
                    if (AppPreferences.isAdsEnable(ApplicationLoader.applicationContext) && AppPreferences.isShowCoinIcon(ApplicationLoader.applicationContext)) {
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
                        new Handler().postDelayed(new C28771(), 1);
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
                    new Handler().postDelayed(new C28771(), 1);
                } catch (Exception e) {
                }
            }

            public void onTextChanged(EditText editText) {
                String text = editText.getText().toString();
                DialogsActivity.this.searchEmptyView.setSearchTerm(text);
                if (text.length() != 0 || (DialogsActivity.this.dialogsSearchAdapter != null && DialogsActivity.this.dialogsSearchAdapter.hasRecentRearch())) {
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
                    DialogsActivity.this.dialogsSearchAdapter.searchDialogs(text);
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
            public void onItemClick(int id) {
                boolean z = true;
                if (id == -1) {
                    if (DialogsActivity.this.onlySelect) {
                        DialogsActivity.this.finishFragment();
                    } else if (DialogsActivity.this.parentLayout != null) {
                        DialogsActivity.this.parentLayout.getDrawerLayoutContainer().openDrawer(false);
                    }
                } else if (id == 1) {
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

    private String getActionMessage() {
        String ans = "";
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
                return ans;
        }
    }

    public void hideSelectedDialogs() {
        ArrayList<TLRPC$TL_dialog> i = MessagesController.getInstance().getSelectedDialogs();
        for (int a = 0; a < i.size(); a++) {
            AppPreferences.addToHiddenChats(ApplicationLoader.applicationContext, ((TLRPC$TL_dialog) i.get(a)).id);
        }
    }

    private void handleHiddenDialogsVisibility() {
        try {
            if (this.menu != null && this.menu.getItem(4) != null) {
                if (AppPreferences.getHiddenList(getParentActivity()).size() > 0) {
                    this.menu.getItem(4).setVisibility(0);
                } else {
                    this.menu.getItem(4).setVisibility(8);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showHotPostTutorial() {
        try {
            new MaterialTapTargetPrompt.Builder(getParentActivity()).setTarget(this.bottomBar.getTabAtPosition(this.bottomBar.getTabCount() - 2)).setTextGravity(GravityCompat.END).setPrimaryTextTypeface(AppUtilities.getLightSansTypeface(getParentActivity())).setSecondaryTextTypeface(AppUtilities.getUltraLightSansTypeface(getParentActivity())).setPrimaryText(getParentActivity().getString(R.string.hot_posts)).setSecondaryText(getParentActivity().getString(R.string.hot_posts_tutorial)).setOnHidePromptListener(new OnHidePromptListener() {
                public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                    Prefs.setGhostModeTu(DialogsActivity.this.getParentActivity(), false);
                }

                public void onHidePromptComplete() {
                    DialogsActivity.this.showSearchTabTutorial();
                }
            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSearchTabTutorial() {
        try {
            new MaterialTapTargetPrompt.Builder(getParentActivity()).setTarget(this.bottomBar.getTabAtPosition(this.bottomBar.getTabCount() - 3)).setTextGravity(GravityCompat.END).setPrimaryTextTypeface(AppUtilities.getLightSansTypeface(getParentActivity())).setSecondaryTextTypeface(AppUtilities.getUltraLightSansTypeface(getParentActivity())).setPrimaryText(getParentActivity().getString(R.string.search_header)).setSecondaryText(getParentActivity().getString(R.string.search_tutorial)).setOnHidePromptListener(new OnHidePromptListener() {

                /* renamed from: org.telegram.ui.DialogsActivity$31$1 */
                class C28791 implements Runnable {
                    C28791() {
                    }

                    public void run() {
                        Intent intent = new Intent(ApplicationLoader.applicationContext, ClientPersonalizeActivity.class);
                        intent.setFlags(268435456);
                        ApplicationLoader.applicationContext.startActivity(intent);
                        AppPreferences.setThemNotShown(ApplicationLoader.applicationContext, true);
                        DialogsActivity.this.isTutorailShowedAndttt = true;
                    }
                }

                public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                    Prefs.setGhostModeTu(DialogsActivity.this.getParentActivity(), false);
                }

                public void onHidePromptComplete() {
                    if (!AppPreferences.isThemNotShown(ApplicationLoader.applicationContext)) {
                        new Handler().postDelayed(new C28791(), 500);
                    }
                }
            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openBatchBottomSheet() {
        BottomSheet.Builder builder = new BottomSheet.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString("MultiDialogsAction", R.string.MultiDialogsAction));
        builder.setItems(new CharSequence[]{LocaleController.getString("deleteDialogs", R.string.deleteDialogs), LocaleController.getString("addDialogsToFave", R.string.addDialogsToFave), LocaleController.getString("deleteDialogsFromFave", R.string.deleteDialogsFromFave), LocaleController.getString("addDialogsToHidden", R.string.addDialogsToHidden), LocaleController.getString("addDialogsToMute", R.string.addDialogsToMute), LocaleController.getString("removeDialogsFromMute", R.string.removeDialogsFromMute), LocaleController.getString("markAsRead", R.string.markAsRead)}, new int[]{R.drawable.ic_delete_white_24dp, R.drawable.ic_heart_white_24dp, R.drawable.ic_heart_off_white_24dp, R.drawable.ic_eye_off_white_24dp, R.drawable.ic_volume_mute_white_24dp, R.drawable.notifications_s_on, R.drawable.ic_checkbox_multiple_marked_white_24dp}, new OnClickListener() {

            /* renamed from: org.telegram.ui.DialogsActivity$32$1 */
            class C28801 implements View.OnClickListener {
                C28801() {
                }

                public void onClick(View v) {
                    DialogsActivity.this.goToSelectModeByActionType(4);
                }
            }

            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
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
                        if (TextUtils.isEmpty(AppPreferences.getPasswordForLockingChats(DialogsActivity.this.getParentActivity()))) {
                            DialogsActivity.this.showPassLockDialog(DialogsActivity.this.getParentActivity(), true, new C28801(), -1);
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

    private void goToSelectModeByActionType(int actionType) {
        Bundle args = new Bundle();
        args.putInt("dialogsType", 0);
        args.putInt(org.telegram.customization.util.Constants.ACTION_TYPE, actionType);
        args.putBoolean(org.telegram.customization.util.Constants.IS_SELECT_MODE, true);
        DialogsActivity dialogsActivity = new DialogsActivity(args);
        dialogsActivity.setResponseReceiver(new IResponseReceiver() {
            public void onResult(Object object, int StatusCode) {
                DialogsActivity.this.handleHiddenDialogsVisibility();
                MessagesController.getInstance().updateDialogsForAddOrRemoveDialog();
                DialogsActivity.this.refreshAdapter(DialogsActivity.this.getParentActivity());
            }
        });
        presentFragment(dialogsActivity);
    }

    private void showPassLockDialog(Context context, boolean isForSavingNewPass, View.OnClickListener okClickListener, final int lastDialogId) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.password_dialog);
        dialog.setCancelable(true);
        final EditText password = (EditText) dialog.findViewById(R.id.input_password);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.passDialogMessage);
        if (TextUtils.isEmpty(AppPreferences.getPasswordForLockingChats(context))) {
            tvMsg.setText(LocaleController.getString("PleaseEnterPassword", R.string.PleaseEnterPassword));
        } else {
            tvMsg.setText(LocaleController.getString("PleaseEnterCurrentPassword", R.string.PleaseEnterCurrentPassword));
        }
        final boolean z = isForSavingNewPass;
        final Context context2 = context;
        final View.OnClickListener onClickListener = okClickListener;
        dialog.findViewById(R.id.tvOk).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String pass = password.getText().toString();
                if (TextUtils.isEmpty(pass)) {
                    password.setError(LocaleController.getString("PleaseEnterCurrentPassword", R.string.PleaseEnterCurrentPassword));
                    password.requestFocus();
                    return;
                }
                if (z) {
                    AppPreferences.setPasswordForLockingChats(context2, pass);
                    dialog.dismiss();
                } else {
                    String currentPass = AppPreferences.getPasswordForLockingChats(context2);
                    if (TextUtils.isEmpty(currentPass) || pass.equals(currentPass)) {
                        if (TextUtils.isEmpty(currentPass)) {
                            AppPreferences.setPasswordForLockingChats(context2, pass);
                        }
                        DialogsActivity.this.dialogsType = 10;
                        DialogsActivity.this.refreshAdapter(context2);
                        dialog.dismiss();
                    } else {
                        password.setError(LocaleController.getString("PasswordDoNotMatch", R.string.PasswordDoNotMatch));
                        password.requestFocus();
                    }
                }
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
            }
        });
        dialog.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lastDialogId >= 0) {
                    DialogsActivity.this.dialogsType = lastDialogId;
                    DialogsActivity.this.refreshTabs();
                }
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                if (lastDialogId >= 0) {
                    DialogsActivity.this.dialogsType = lastDialogId;
                    DialogsActivity.this.refreshTabs();
                }
            }
        });
        showDialog(dialog);
    }

    private void refreshAdapterAndTabs(DialogsAdapter adapter) {
        this.dialogsAdapter = adapter;
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

    private void refreshTabs() {
        SharedPreferences themePrefs = ApplicationLoader.applicationContext.getSharedPreferences(AppUtilities.THEME_PREFS, 0);
        ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.tab_selected).setColorFilter(themePrefs.getInt("chatsHeaderTabIconColor", themePrefs.getInt("chatsHeaderIconsColor", -1)), Mode.SRC_IN);
        for (int tabCount = 0; tabCount < this.tabLayout.getTabCount(); tabCount++) {
            int i;
            Tab tab = this.tabLayout.getTabAt(tabCount);
            if (this.dialogsType == 9) {
                i = 4;
            } else {
                i = this.dialogsType;
            }
            switch (i) {
                case 3:
                    if (!(tab == null || tab.getTag() == null || !tab.getTag().equals(org.telegram.customization.util.Constants.TAB_USERS))) {
                        tab.select();
                        break;
                    }
                case 4:
                    if (!(tab == null || tab.getTag() == null || !tab.getTag().equals(org.telegram.customization.util.Constants.TAB_GROUPS))) {
                        tab.select();
                        break;
                    }
                case 5:
                    if (!(tab == null || tab.getTag() == null || !tab.getTag().equals(org.telegram.customization.util.Constants.TAB_CHANNELS))) {
                        tab.select();
                        break;
                    }
                case 6:
                    if (!(tab == null || tab.getTag() == null || !tab.getTag().equals(org.telegram.customization.util.Constants.TAB_BOTS))) {
                        tab.select();
                        break;
                    }
                case 7:
                    if (!(tab == null || tab.getTag() == null || !tab.getTag().equals(org.telegram.customization.util.Constants.TAB_SGROUP))) {
                        tab.select();
                        break;
                    }
                case 8:
                    if (!(tab == null || tab.getTag() == null || !tab.getTag().equals(org.telegram.customization.util.Constants.TAB_FAVES))) {
                        tab.select();
                        break;
                    }
                case 10:
                    if (!(tab == null || tab.getTag() == null || !tab.getTag().equals(org.telegram.customization.util.Constants.TAB_LOCK))) {
                        tab.select();
                        break;
                    }
                case 11:
                    if (!(tab == null || tab.getTag() == null || !tab.getTag().equals(org.telegram.customization.util.Constants.TAB_UNREAD_CHATS))) {
                        tab.select();
                        break;
                    }
                case 12:
                    if (!(tab == null || tab.getTag() == null || !tab.getTag().equals(org.telegram.customization.util.Constants.TAB_ADS))) {
                        tab.select();
                        break;
                    }
                default:
                    if (!(tab == null || tab.getTag() == null || !tab.getTag().equals(org.telegram.customization.util.Constants.TAB_ALL))) {
                        tab.select();
                        break;
                    }
            }
        }
        String t = getHeaderAllTitles();
        if (this.isSelectMode) {
            this.actionBar.setTitle(LocaleController.getString("SelectChats", R.string.SelectChats));
        } else {
            this.actionBar.setTitle(t);
        }
        if (!TextUtils.isEmpty(AppPreferences.getSubtitle(ApplicationLoader.applicationContext))) {
            this.actionBar.setSubtitle(AppPreferences.getSubtitle(ApplicationLoader.applicationContext));
        }
        paintHeader(true);
        if (getDialogsArray() != null && getDialogsArray().isEmpty()) {
            this.searchEmptyView.setVisibility(8);
            this.progressView.setVisibility(8);
            if (this.emptyView.getChildCount() > 0) {
                TextView tv = (TextView) this.emptyView.getChildAt(0);
                tv.setPadding(20, 0, 20, 0);
                tv.setTypeface(AppUtilities.getUltraLightSansTypeface(ApplicationLoader.applicationContext));
                if (tv != null) {
                    if (this.dialogsType < 3 || this.dialogsType == 11) {
                        t = LocaleController.getString("NoChats", R.string.NoChats);
                    } else if (this.dialogsType == 8) {
                        t = LocaleController.getString("NoFavoritesHelp", R.string.NoFavoritesHelp);
                    } else if (this.dialogsType == 10) {
                        t = LocaleController.getString("NoLockedChatsHelp", R.string.NoLockedChatsHelp);
                    }
                    tv.setText(t);
                    tv.setTextColor(themePrefs.getInt("chatsNameColor", -14606047));
                }
                if (this.emptyView.getChildAt(1) != null) {
                    this.emptyView.getChildAt(1).setVisibility(8);
                }
            }
            this.emptyView.setVisibility(0);
            this.emptyView.setBackgroundColor(themePrefs.getInt("chatsRowColor", -1));
            this.listView.setEmptyView(this.emptyView);
        }
    }

    private void refreshAdapter(Context context) {
        refreshAdapterAndTabs(new DialogsAdapter(context, this.dialogsType));
    }

    private void refreshDialogType(int d) {
        if (!this.hideTabs) {
            int tabCount;
            Tab tab;
            boolean disable = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("infiniteTabsSwipe", false);
            int dialogsTypeTmp = this.dialogsType;
            ArrayList<DialogTab> dialogTabs = AppPreferences.getActiveTabs(ApplicationLoader.applicationContext);
            for (int i = 0; i < dialogTabs.size(); i++) {
                if (((DialogTab) dialogTabs.get(i)).getDialogType() == this.dialogsType) {
                    if (d == 1) {
                        if (i + 1 < dialogTabs.size()) {
                            this.dialogsType = ((DialogTab) dialogTabs.get(i + 1)).getDialogType();
                        } else if (disable) {
                            this.dialogsType = ((DialogTab) dialogTabs.get(0)).getDialogType();
                            refreshAdapter(ApplicationLoader.applicationContext);
                            return;
                        } else {
                            return;
                        }
                    } else if (i - 1 >= 0) {
                        this.dialogsType = ((DialogTab) dialogTabs.get(i - 1)).getDialogType();
                    } else if (disable) {
                        this.dialogsType = ((DialogTab) dialogTabs.get(dialogTabs.size() - 1)).getDialogType();
                        refreshAdapter(ApplicationLoader.applicationContext);
                        return;
                    } else {
                        return;
                    }
                    if (this.dialogsType != 10) {
                        this.dialogsType = dialogsTypeTmp;
                        for (tabCount = 0; tabCount < this.tabLayout.getTabCount(); tabCount++) {
                            tab = this.tabLayout.getTabAt(tabCount);
                            if (tab == null && tab.getTag() != null && tab.getTag().equals(org.telegram.customization.util.Constants.TAB_LOCK)) {
                                tab.select();
                                return;
                            }
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
            this.dialogsType = dialogsTypeTmp;
            for (tabCount = 0; tabCount < this.tabLayout.getTabCount(); tabCount++) {
                tab = this.tabLayout.getTabAt(tabCount);
                if (tab == null) {
                }
            }
        }
    }

    private void refreshTabAndListViews(boolean forceHide) {
        int paddingBottomForListView = this.bottomBarHeight;
        try {
            paddingBottomForListView = this.bottomBar.getHeight();
        } catch (Exception e) {
        }
        if (this.tabsView != null) {
            if (this.hideTabs || forceHide) {
                this.tabsView.setVisibility(8);
                this.listView.setPadding(0, 0, 0, paddingBottomForListView);
            } else {
                this.tabsView.setVisibility(0);
                int h = AndroidUtilities.dp((float) this.tabsHeight);
                ViewGroup.LayoutParams params = this.tabsView.getLayoutParams();
                if (params != null) {
                    params.height = h;
                    this.tabsView.setLayoutParams(params);
                }
                this.listView.setPadding(0, h, 0, paddingBottomForListView);
                hideTabsAnimated(false);
            }
            this.listView.scrollToPosition(0);
        }
    }

    private void hideTabsAnimated(boolean hide) {
        if (this.tabsHidden == hide) {
        }
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

    private void paintHeader(boolean tabs) {
        SharedPreferences themePrefs = ApplicationLoader.applicationContext.getSharedPreferences(AppUtilities.THEME_PREFS, 0);
        this.actionBar.setTitleColor(themePrefs.getInt("chatsHeaderTitleColor", -1));
        int def = themePrefs.getInt("themeColor", AppUtilities.defColor);
        int hColor = themePrefs.getInt("chatsHeaderColor", def);
        if (!tabs) {
            this.actionBar.setBackgroundColor(hColor);
        }
        if (tabs) {
            this.tabsView.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        }
        int val = themePrefs.getInt("chatsHeaderGradient", 0);
        if (val > 0) {
            Orientation go;
            switch (val) {
                case 2:
                    go = Orientation.LEFT_RIGHT;
                    break;
                case 3:
                    go = Orientation.TL_BR;
                    break;
                case 4:
                    go = Orientation.BL_TR;
                    break;
                default:
                    go = Orientation.TOP_BOTTOM;
                    break;
            }
            int gradColor = themePrefs.getInt("chatsHeaderGradientColor", def);
            GradientDrawable gd = new GradientDrawable(go, new int[]{hColor, gradColor});
            if (!tabs) {
                this.actionBar.setBackgroundDrawable(gd);
            }
            if (tabs) {
                this.tabsView.setBackgroundDrawable(gd);
            }
        }
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case 5:
                if (((Boolean) object).booleanValue()) {
                    AppPreferences.setRegistered(getParentActivity(), true);
                    return;
                } else {
                    AppPreferences.setRegistered(getParentActivity(), false);
                    return;
                }
            default:
                return;
        }
    }

    private void updateTabs() {
        SharedPreferences plusPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        this.hideTabs = plusPreferences.getBoolean("hideTabs", false);
        this.disableAnimation = plusPreferences.getBoolean("disableTabsAnimation", false);
        this.tabsHeight = plusPreferences.getInt("tabsHeight", 40);
        refreshTabAndListViews(false);
        if (this.hideTabs) {
            this.dialogsType = 0;
            refreshAdapterAndTabs(this.dialogsBackupAdapter);
        }
    }

    public void fillAllUnreadCounts() {
        SharedPreferences plusPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        if (!plusPreferences.getBoolean("hideTabs", false) && !plusPreferences.getBoolean("hideTabsCounters", false)) {
            boolean countDialogs = plusPreferences.getBoolean("tabsCountersCountChats", false);
            boolean countNotMuted = plusPreferences.getBoolean("tabsCountersCountNotMuted", false);
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
            int type = AppPreferences.getSubtitleType(ApplicationLoader.applicationContext);
            List<Long> hiddensIds = AppPreferences.getHiddenList(ApplicationLoader.applicationContext);
            List<Integer> categoriesIds = AppPreferences.getAdsChannelIds(ApplicationLoader.applicationContext);
            boolean isShowHiddenDialogs = AppPreferences.isShowHiddenDialogs(ApplicationLoader.applicationContext);
            ArrayList<Long> favouritesIds = Favourite.getFavouriteIds();
            Iterator it = MessagesController.getInstance().dialogsAll.iterator();
            while (it.hasNext()) {
                TLRPC$TL_dialog d = (TLRPC$TL_dialog) it.next();
                boolean dialogMuted = true;
                int unreadCount = 0;
                boolean isMuted = MessagesController.getInstance().isDialogMuted(d.id);
                if (!(isMuted && countNotMuted)) {
                    int i = d.unread_count;
                    if (i > 0) {
                        if (!countDialogs) {
                            unreadCount = 0 + i;
                        } else if (i > 0) {
                            unreadCount = 0 + 1;
                        }
                        if (i > 0 && !isMuted) {
                            dialogMuted = false;
                        }
                    }
                }
                if (type != 2 || d.unread_count > 0) {
                    if (type == 4) {
                        if (MessagesController.getInstance().isDialogMuted(d.id)) {
                        }
                    }
                    if (type == 3) {
                        if (!MessagesController.getInstance().isDialogMuted(d.id)) {
                        }
                    }
                    boolean isDialogHidden = hiddensIds.contains(Long.valueOf(d.id));
                    if (isDialogHidden) {
                        if (isShowHiddenDialogs) {
                            this.countHidden += unreadCount;
                            this.allMutedHidden &= dialogMuted;
                        }
                    }
                    if (isShowHiddenDialogs || !isDialogHidden) {
                        this.countAll += unreadCount;
                        this.allMutedAll &= dialogMuted;
                    }
                    if (d.unread_count > 0) {
                        this.countUnread += unreadCount;
                        this.allMutedUnread &= dialogMuted;
                    }
                    int high_id = (int) (d.id >> 32);
                    int lower_id = (int) d.id;
                    if (((int) d.id) != 0 && high_id != 1 && (d instanceof TLRPC$TL_dialog)) {
                        if (d.id < 0) {
                            TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) d.id)));
                            if (chat != null) {
                                if (chat.megagroup) {
                                    this.countMegaGroups += unreadCount;
                                    this.countGroupsAll += unreadCount;
                                    this.allMutedMegaGroups &= dialogMuted;
                                    this.allMutedGroupsAll &= dialogMuted;
                                } else if (ChatObject.isChannel(chat)) {
                                    this.countChannels += unreadCount;
                                    this.allMutedChannels &= dialogMuted;
                                    for (Integer i2 : categoriesIds) {
                                        if (((long) i2.intValue()) == Math.abs(d.id)) {
                                            this.countAds += unreadCount;
                                            this.allMutedAds &= dialogMuted;
                                            break;
                                        }
                                    }
                                } else {
                                    this.countGroups += unreadCount;
                                    this.countGroupsAll += unreadCount;
                                    this.allMutedGroups &= dialogMuted;
                                    this.allMutedGroupsAll &= dialogMuted;
                                }
                            }
                        } else {
                            User user = MessagesController.getInstance().getUser(Integer.valueOf((int) d.id));
                            if (user == null) {
                                this.countGroups += unreadCount;
                                this.countGroupsAll += unreadCount;
                                this.allMutedGroups &= dialogMuted;
                                this.allMutedGroupsAll &= dialogMuted;
                            } else if (user.bot) {
                                this.countBots += unreadCount;
                                this.allMutedBots &= dialogMuted;
                            } else {
                                this.countUsers += unreadCount;
                                this.allMutedUsers &= dialogMuted;
                            }
                        }
                    }
                    if (favouritesIds.contains(Long.valueOf(d.id))) {
                        this.countFavs += unreadCount;
                        this.allMutedFavs &= dialogMuted;
                    }
                }
            }
        }
    }

    private void unreadCount() {
        fillAllUnreadCounts();
        try {
            if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_ALL, ApplicationLoader.applicationContext)) {
                unreadCount(this.countAll, this.allMutedAll, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_ALL));
            }
            if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_UNREAD_CHATS, ApplicationLoader.applicationContext)) {
                unreadCount(this.countUnread, this.allMutedUnread, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_UNREAD_CHATS));
            }
            if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_USERS, ApplicationLoader.applicationContext)) {
                unreadCount(this.countUsers, this.allMutedUsers, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_USERS));
            }
            if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_BOTS, ApplicationLoader.applicationContext)) {
                unreadCount(this.countBots, this.allMutedBots, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_BOTS));
            }
            if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_CHANNELS, ApplicationLoader.applicationContext)) {
                unreadCount(this.countChannels, this.allMutedChannels, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_CHANNELS));
            }
            if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_FAVES, ApplicationLoader.applicationContext)) {
                unreadCount(this.countFavs, this.allMutedFavs, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_FAVES));
            }
            if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_ADS, ApplicationLoader.applicationContext)) {
                unreadCount(this.countAds, this.allMutedAds, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_ADS));
            }
            unreadCountGroups();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unreadCountGroups() {
        if (!ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("hideSGroups", false)) {
            if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_GROUPS, ApplicationLoader.applicationContext)) {
                unreadCount(this.countGroups, this.allMutedGroups, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_GROUPS));
            }
            if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_SGROUP, ApplicationLoader.applicationContext)) {
                unreadCount(this.countMegaGroups, this.allMutedMegaGroups, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_SGROUP));
            }
        } else if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_GROUPS, ApplicationLoader.applicationContext)) {
            unreadCount(this.countGroupsAll, this.allMutedGroupsAll, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_GROUPS));
        }
    }

    private void unreadCount(int unreadCount, boolean allMuted, TextView tv) {
        if (tv != null) {
            tv.setVisibility(0);
            if (unreadCount == 0) {
                tv.setVisibility(8);
                return;
            }
            String txt = String.valueOf(unreadCount);
            if (unreadCount > 99) {
                txt = "+99";
            }
            tv.setVisibility(0);
            tv.setText(txt);
            SharedPreferences themePrefs = ApplicationLoader.applicationContext.getSharedPreferences(AppUtilities.THEME_PREFS, 0);
            tv.setTextSize(1, (float) themePrefs.getInt("chatsHeaderTabCounterSize", 8));
            int cColor = themePrefs.getInt("chatsHeaderTabCounterColor", -1);
            if (allMuted) {
                tv.getBackground().setColorFilter(themePrefs.getInt("chatsHeaderTabCounterSilentBGColor", -4605511), Mode.SRC_IN);
                tv.setTextColor(cColor);
                return;
            }
            tv.getBackground().setColorFilter(themePrefs.getInt("chatsHeaderTabCounterBGColor", -2937041), Mode.SRC_IN);
            tv.setTextColor(cColor);
        }
    }

    private void unreadCountOld(ArrayList<TLRPC$TL_dialog> dialogs, TextView tv) {
        if (tv != null) {
            SharedPreferences plusPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
            if (!plusPreferences.getBoolean("hideTabs", false)) {
                if (plusPreferences.getBoolean("hideTabsCounters", false)) {
                    tv.setVisibility(8);
                    return;
                }
                boolean allMuted = true;
                boolean countDialogs = plusPreferences.getBoolean("tabsCountersCountChats", false);
                boolean countNotMuted = plusPreferences.getBoolean("tabsCountersCountNotMuted", false);
                int unreadCount = 0;
                if (!(dialogs == null || dialogs.isEmpty())) {
                    Iterator it = dialogs.iterator();
                    while (it.hasNext()) {
                        TLRPC$TL_dialog dialg = (TLRPC$TL_dialog) it.next();
                        boolean isMuted = MessagesController.getInstance().isDialogMuted(dialg.id);
                        if (!isMuted || !countNotMuted) {
                            int i = dialg.unread_count;
                            if (i > 0) {
                                if (!countDialogs) {
                                    unreadCount += i;
                                } else if (i > 0) {
                                    unreadCount++;
                                }
                                if (i > 0 && !isMuted) {
                                    allMuted = false;
                                }
                            }
                        }
                    }
                }
                if (unreadCount == 0) {
                    tv.setVisibility(8);
                    return;
                }
                String txt = String.valueOf(unreadCount);
                if (unreadCount > 99) {
                    txt = "+99";
                }
                tv.setVisibility(0);
                tv.setText(txt);
                SharedPreferences themePrefs = ApplicationLoader.applicationContext.getSharedPreferences(AppUtilities.THEME_PREFS, 0);
                tv.setTextSize(1, (float) themePrefs.getInt("chatsHeaderTabCounterSize", 8));
                int cColor = themePrefs.getInt("chatsHeaderTabCounterColor", -1);
                if (allMuted) {
                    tv.getBackground().setColorFilter(themePrefs.getInt("chatsHeaderTabCounterSilentBGColor", -4605511), Mode.SRC_IN);
                    tv.setTextColor(cColor);
                    return;
                }
                tv.getBackground().setColorFilter(themePrefs.getInt("chatsHeaderTabCounterBGColor", -2937041), Mode.SRC_IN);
                tv.setTextColor(cColor);
            }
        }
    }

    private void unreadCountGroupsOld() {
        if (!ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("hideSGroups", false)) {
            if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_GROUPS, ApplicationLoader.applicationContext)) {
                unreadCountOld(MessagesController.getInstance().dialogsGroups, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_GROUPS));
            }
            if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_SGROUP, ApplicationLoader.applicationContext)) {
                unreadCountOld(MessagesController.getInstance().dialogsMegaGroups, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_SGROUP));
            }
        } else if (AppPreferences.isTabActive(org.telegram.customization.util.Constants.TAB_GROUPS, ApplicationLoader.applicationContext)) {
            unreadCountOld(MessagesController.getInstance().dialogsGroupsAll, (TextView) this.tabLayout.findViewWithTag(org.telegram.customization.util.Constants.TAB_GROUPS));
        }
    }

    private void markAsReadDialog(final boolean all) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getParentActivity());
        TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf((int) (-this.selectedDialog)));
        User user = MessagesController.getInstance().getUser(Integer.valueOf((int) this.selectedDialog));
        String title = currentChat != null ? currentChat.title : user != null ? UserObject.getUserName(user) : LocaleController.getString("MyAppName", R.string.MyAppName);
        if (all) {
            title = getHeaderAllTitles();
        }
        builder.setTitle(title);
        builder.setMessage((all ? LocaleController.getString("MarkAllAsRead", R.string.MarkAllAsRead) : LocaleController.getString("MarkAsRead", R.string.MarkAsRead)) + '\n' + LocaleController.getString("AreYouSure", R.string.AreYouSure));
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                TLRPC$TL_dialog dialg;
                if (all) {
                    ArrayList<TLRPC$TL_dialog> dialogs = DialogsActivity.this.getDialogsArray();
                    if (dialogs != null && !dialogs.isEmpty()) {
                        for (int a = 0; a < dialogs.size(); a++) {
                            dialg = (TLRPC$TL_dialog) DialogsActivity.this.getDialogsArray().get(a);
                            if (dialg.unread_count > 0) {
                                MessagesController.getInstance().markDialogAsRead(dialg.id, 0, Math.max(0, dialg.top_message), dialg.last_message_date, true, false);
                            }
                        }
                        return;
                    }
                    return;
                }
                dialg = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(DialogsActivity.this.selectedDialog));
                if (dialg.unread_count > 0) {
                    MessagesController.getInstance().markDialogAsRead(dialg.id, 0, Math.max(0, dialg.top_message), dialg.last_message_date, true, false);
                }
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
    }

    private void addShortcut() {
        Intent intent = new Intent(ApplicationLoader.applicationContext, ShortcutActivity.class);
        intent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
        intent.setFlags(32768);
        TLRPC$TL_dialog dialg = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.selectedDialog));
        TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf((int) (-this.selectedDialog)));
        User user = MessagesController.getInstance().getUser(Integer.valueOf((int) this.selectedDialog));
        TLRPC$EncryptedChat encryptedChat = null;
        AvatarDrawable avatarDrawable = new AvatarDrawable();
        long dialog_id = dialg.id;
        int lower_id = (int) dialog_id;
        int high_id = (int) (dialog_id >> 32);
        if (lower_id == 0) {
            encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(high_id));
            if (encryptedChat != null) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(encryptedChat.user_id));
                intent.putExtra("encId", high_id);
                avatarDrawable.setInfo(user);
            }
        } else if (high_id == 1) {
            currentChat = MessagesController.getInstance().getChat(Integer.valueOf(lower_id));
            intent.putExtra("chatId", lower_id);
            avatarDrawable.setInfo(currentChat);
        } else if (lower_id < 0) {
            currentChat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
            if (!(currentChat == null || currentChat.migrated_to == null)) {
                TLRPC$Chat chat2 = MessagesController.getInstance().getChat(Integer.valueOf(currentChat.migrated_to.channel_id));
                if (chat2 != null) {
                    currentChat = chat2;
                }
            }
            intent.putExtra("chatId", -lower_id);
            avatarDrawable.setInfo(currentChat);
        } else {
            user = MessagesController.getInstance().getUser(Integer.valueOf(lower_id));
            intent.putExtra("userId", lower_id);
            avatarDrawable.setInfo(user);
        }
        String name = currentChat != null ? currentChat.title : (user == null || encryptedChat != null) ? encryptedChat != null ? new String(Character.toChars(128274)) + UserObject.getUserName(user) : null : UserObject.getUserName(user);
        if (name != null) {
            TLObject photoPath = null;
            if (currentChat != null) {
                if (!(currentChat.photo == null || currentChat.photo.photo_small == null || currentChat.photo.photo_small.volume_id == 0 || currentChat.photo.photo_small.local_id == 0)) {
                    photoPath = currentChat.photo.photo_small;
                }
            } else if (!(user == null || user.photo == null || user.photo.photo_small == null || user.photo.photo_small.volume_id == 0 || user.photo.photo_small.local_id == 0)) {
                photoPath = user.photo.photo_small;
            }
            BitmapDrawable img = null;
            if (photoPath != null) {
                img = ImageLoader.getInstance().getImageFromMemory(photoPath, null, "50_50");
            }
            String action = "com.android.launcher.action.INSTALL_SHORTCUT";
            Intent addIntent = new Intent();
            addIntent.putExtra("android.intent.extra.shortcut.INTENT", intent);
            addIntent.putExtra("android.intent.extra.shortcut.NAME", name);
            if (img != null) {
                addIntent.putExtra("android.intent.extra.shortcut.ICON", getRoundBitmap(img.getBitmap()));
            } else {
                int w = AndroidUtilities.dp(40.0f);
                int h = AndroidUtilities.dp(40.0f);
                Bitmap mutableBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mutableBitmap);
                avatarDrawable.setBounds(0, 0, w, h);
                avatarDrawable.draw(canvas);
                addIntent.putExtra("android.intent.extra.shortcut.ICON", getRoundBitmap(mutableBitmap));
            }
            addIntent.putExtra("duplicate", false);
            addIntent.setAction(action);
            boolean error = false;
            if (ApplicationLoader.applicationContext.getPackageManager().queryBroadcastReceivers(new Intent(action), 0).size() > 0) {
                ApplicationLoader.applicationContext.sendBroadcast(addIntent);
            } else {
                error = true;
            }
            final String formatString = error ? LocaleController.formatString("ShortcutError", R.string.ShortcutError, new Object[]{name}) : LocaleController.formatString("ShortcutAdded", R.string.ShortcutAdded, new Object[]{name});
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (DialogsActivity.this.getParentActivity() != null) {
                        Toast.makeText(DialogsActivity.this.getParentActivity(), formatString, 0).show();
                    }
                }
            });
        }
    }

    private Bitmap getRoundBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int radius = Math.min(h / 2, w / 2);
        Bitmap output = Bitmap.createBitmap(w + 8, h + 8, Config.ARGB_8888);
        Paint p = new Paint();
        p.setAntiAlias(true);
        Canvas c = new Canvas(output);
        c.drawARGB(0, 0, 0, 0);
        p.setStyle(Style.FILL);
        c.drawCircle((float) ((w / 2) + 4), (float) ((h / 2) + 4), (float) radius, p);
        p.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        c.drawBitmap(bitmap, 4.0f, 4.0f, p);
        return output;
    }

    private void lockChat(Context context, boolean wasHideChat, long selectedDialog) {
        if (TextUtils.isEmpty(AppPreferences.getPasswordForLockingChats(context))) {
            final boolean z = wasHideChat;
            final Context context2 = context;
            final long j = selectedDialog;
            showPassLockDialog(context, true, new View.OnClickListener() {
                public void onClick(View v) {
                    if (z) {
                        AppPreferences.removeFromHiddenChats(context2, j);
                    } else {
                        AppPreferences.addToHiddenChats(context2, j);
                    }
                    DialogsActivity.this.handleHiddenDialogsVisibility();
                    MessagesController.getInstance().updateDialogsForAddOrRemoveDialog();
                    DialogsActivity.this.refreshAdapter(context2);
                }
            }, -1);
            return;
        }
        if (wasHideChat) {
            AppPreferences.removeFromHiddenChats(context, selectedDialog);
        } else {
            AppPreferences.addToHiddenChats(context, selectedDialog);
        }
        handleHiddenDialogsVisibility();
        MessagesController.getInstance().updateDialogsForAddOrRemoveDialog();
        refreshAdapter(context);
    }

    private void createTabs(Context context) {
        int dialogTypeForForceRefresh = -1;
        if (this.forceRefreshTabs) {
            dialogTypeForForceRefresh = this.dialogsType;
        }
        SharedPreferences plusPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        Editor editor = plusPreferences.edit();
        boolean hideUsers = plusPreferences.getBoolean("hideUsers", false);
        boolean hideGroups = plusPreferences.getBoolean("hideGroups", false);
        boolean hideSGroups = plusPreferences.getBoolean("hideSGroups", false);
        boolean hideChannels = plusPreferences.getBoolean("hideChannels", false);
        boolean hideBots = plusPreferences.getBoolean("hideBots", false);
        boolean hideFavs = plusPreferences.getBoolean("hideFavs", false);
        boolean hideUnread = plusPreferences.getBoolean("hideUnread", false);
        this.hideTabs = plusPreferences.getBoolean("hideTabs", false);
        this.disableAnimation = plusPreferences.getBoolean("disableTabsAnimation", false);
        if (hideUsers && hideGroups && hideSGroups && hideChannels && hideBots && hideFavs && hideUnread && !this.hideTabs) {
            this.hideTabs = true;
            editor.putBoolean("hideTabs", true).apply();
        }
        this.tabsHeight = plusPreferences.getInt("tabsHeight", 40);
        refreshTabAndListViews(false);
        int t = plusPreferences.getInt("defTab", -1);
        if (t == -1) {
            t = plusPreferences.getInt("selTab", 0);
        }
        this.selectedTab = t;
        if (!(this.hideTabs || this.dialogsType == this.selectedTab)) {
            int i;
            if (this.selectedTab == 4 && hideSGroups) {
                i = 9;
            } else {
                i = this.selectedTab;
            }
            this.dialogsType = i;
            this.dialogsAdapter = new DialogsAdapter(context, this.dialogsType);
            this.listView.setAdapter(this.dialogsAdapter);
            this.dialogsAdapter.notifyDataSetChanged();
        }
        this.dialogsBackupAdapter = new DialogsAdapter(context, 0);
        this.tabsLayout = new LinearLayout(context);
        this.tabsLayout.setOrientation(0);
        this.tabsLayout.setGravity(17);
        this.slidingTabLayout = new SlidingTabLayout(context);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(-1, -2);
        this.tabsLayout.setLayoutParams(layoutParams);
        this.slidingTabLayout.setLayoutParams(layoutParams);
        this.slidingTabLayout.setCustomTabView(R.layout.sls_tab_item, R.id.ftv_msg_count);
        final Context context2 = context;
        this.slidingTabLayout.setCustomTabColorizer(new TabColorizer() {
            public int getIndicatorColor(int position) {
                return context2.getResources().getColor(R.color.white);
            }
        });
        this.slidingTabLayout.setViewPager(this.viewPager);
        try {
            this.tabLayout = new TabLayout(getParentActivity());
            this.tabLayout.setLayoutParams(layoutParams);
            this.tabLayout.setSelectedTabIndicatorColor(ApplicationLoader.applicationContext.getResources().getColor(R.color.white));
            this.tabsLayout.removeAllViews();
            this.tabsView.removeAllViews();
            this.tabsView.addView(this.tabLayout);
            this.tabLayout.removeAllTabs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.tabsView.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        ArrayList<DialogTab> dialogTabs = AppPreferences.getActiveTabs(getParentActivity());
        Collections.reverse(dialogTabs);
        Iterator it = dialogTabs.iterator();
        while (it.hasNext()) {
            final DialogTab dialogTab = (DialogTab) it.next();
            Tab tab = this.tabLayout.newTab();
            tab.setCustomView(dialogTab.getTabLayoutResource());
            tab.setTag(dialogTab.getTag());
            if (!dialogTab.isHidden()) {
                this.tabLayout.addTab(tab);
            }
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(dialogTab.getTabDrawable());
            TextView counter = (TextView) tab.getCustomView().findViewById(R.id.slsTvCounter);
            counter.setTag(dialogTab.getTag());
            addTabView(context, imageView, counter, !dialogTab.isHidden());
            context2 = context;
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (DialogsActivity.this.dialogsType != dialogTab.getDialogType()) {
                        DialogsActivity.this.dialogsType = dialogTab.getDialogType();
                        DialogsActivity.this.refreshAdapter(context2);
                        Utilities.addViewActionByActionName(dialogTab.getTag());
                    }
                }
            });
            imageView.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View view) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DialogsActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("Favorites", R.string.Favorites));
                    SharedPreferences plusPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    final int sort = plusPreferences.getInt("sortFavs", 0);
                    final int def = plusPreferences.getInt("defTab", -1);
                    CharSequence cs = def == 8 ? LocaleController.getString("ResetDefaultTab", R.string.ResetDefaultTab) : LocaleController.getString("SetAsDefaultTab", R.string.SetAsDefaultTab);
                    CharSequence cs1 = sort == 0 ? LocaleController.getString("SortByUnreadCount", R.string.SortByUnreadCount) : LocaleController.getString("SortByLastMessage", R.string.SortByLastMessage);
                    builder.setItems(new CharSequence[]{cs1, cs, LocaleController.getString("MarkAllAsRead", R.string.MarkAllAsRead)}, new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            int i = 8;
                            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                            if (which == 1) {
                                String str = "defTab";
                                if (def == 8) {
                                    i = -1;
                                }
                                editor.putInt(str, i).apply();
                            } else if (which == 0) {
                                String str2 = "sortFavs";
                                if (sort == 0) {
                                    i = 1;
                                } else {
                                    i = 0;
                                }
                                editor.putInt(str2, i).apply();
                                if (DialogsActivity.this.dialogsAdapter.getItemCount() > 1) {
                                    DialogsActivity.this.dialogsAdapter.notifyDataSetChanged();
                                }
                            } else if (which == 2) {
                                DialogsActivity.this.markAsReadDialog(true);
                            }
                        }
                    });
                    DialogsActivity.this.showDialog(builder.create());
                    return true;
                }
            });
        }
        context2 = context;
        this.tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            public void onTabSelected(Tab tab) {
                if (tab != null && tab.getTag() != null) {
                    int dialogTypeTmp = 0;
                    if (tab.getTag().equals(org.telegram.customization.util.Constants.TAB_ALL)) {
                        dialogTypeTmp = 0;
                    } else if (tab.getTag().equals(org.telegram.customization.util.Constants.TAB_FAVES)) {
                        dialogTypeTmp = 8;
                    } else if (tab.getTag().equals(org.telegram.customization.util.Constants.TAB_GROUPS)) {
                        dialogTypeTmp = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("hideSGroups", false) ? 9 : 4;
                    } else if (tab.getTag().equals(org.telegram.customization.util.Constants.TAB_SGROUP)) {
                        dialogTypeTmp = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("hideGroups", false) ? 9 : 7;
                    } else if (tab.getTag().equals(org.telegram.customization.util.Constants.TAB_BOTS)) {
                        dialogTypeTmp = 6;
                    } else if (tab.getTag().equals(org.telegram.customization.util.Constants.TAB_USERS)) {
                        dialogTypeTmp = 3;
                    } else if (tab.getTag().equals(org.telegram.customization.util.Constants.TAB_CHANNELS)) {
                        dialogTypeTmp = 5;
                    } else if (tab.getTag().equals(org.telegram.customization.util.Constants.TAB_LOCK)) {
                        dialogTypeTmp = 10;
                    } else if (tab.getTag().equals(org.telegram.customization.util.Constants.TAB_UNREAD_CHATS)) {
                        dialogTypeTmp = 11;
                    } else if (tab.getTag().equals(org.telegram.customization.util.Constants.TAB_ADS)) {
                        dialogTypeTmp = 12;
                    }
                    if (DialogsActivity.this.dialogsType != dialogTypeTmp) {
                        DialogsActivity.this.dialogsType = dialogTypeTmp;
                        DialogsActivity.this.refreshAdapter(context2);
                    }
                }
            }

            public void onTabUnselected(Tab tab) {
            }

            public void onTabReselected(Tab tab) {
            }
        });
        if (this.forceRefreshTabs) {
            this.forceRefreshTabs = false;
            this.dialogsType = dialogTypeForForceRefresh;
            refreshAdapter(context);
        }
        refreshAdapter(context);
    }

    private void addTabView(Context context, ImageView iv, TextView tv, boolean show) {
        iv.setScaleType(ScaleType.CENTER);
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(0);
        shape.setCornerRadius((float) AndroidUtilities.dp(32.0f));
        if (tv != null) {
            tv.setBackgroundDrawable(shape);
        }
        RelativeLayout layout = new RelativeLayout(context);
        layout.addView(iv, LayoutHelper.createRelative(-1, -1));
        if (show) {
            try {
                this.tabsLayout.addView(layout, LayoutHelper.createLinear(0, -1, 1.0f));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void hideShowTabs(int i) {
        createTabs(ApplicationLoader.applicationContext);
    }
}
