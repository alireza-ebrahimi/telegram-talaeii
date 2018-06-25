package org.telegram.customization.Activities;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.customization.Adapters.BaseFragmentAdapter;
import org.telegram.customization.Model.DialogTab;
import org.telegram.customization.util.AppUtilities;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextDetailSettingsCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.NumberPicker;
import org.telegram.ui.DocumentSelectActivity;
import org.telegram.ui.DocumentSelectActivity.DocumentSelectActivityDelegate;
import utils.app.AppPreferences;
import utils.view.Constants;
import utils.view.FontUtil;

public class PlusSettingsActivity extends BaseFragment implements NotificationCenterDelegate {
    private int chatDirectShareFavsFirst;
    private int chatDirectShareToMenu;
    private int chatHideBotKeyboardRow;
    private int chatHideJoinedGroupRow;
    private int chatHideLeftGroupRow;
    private int chatSearchUserOnTwitterRow;
    private int chatShowDirectShareBtn;
    private int chatShowEditedMarkRow;
    private int confirmForStickers;
    private int dialogsDisableTabsAnimationCheckRow;
    private int dialogsGroupPicClickRow;
    private int dialogsHideTabsCheckRow;
    private int dialogsHideTabsCounters;
    private int dialogsInfiniteTabsSwipe;
    private int dialogsPicClickRow;
    private int dialogsSectionRow;
    private int dialogsSectionRow2;
    private int dialogsTabsCountersCountChats;
    private int dialogsTabsCountersCountNotMuted;
    private int dialogsTabsHeightRow;
    private int dialogsTabsRow;
    private int dialogsTabsSort;
    private int disableAudioStopRow;
    private int disableMessageClickRow;
    private int drawerSectionRow;
    private int drawerSectionRow2;
    private int emojiPopupSize;
    private int emptyRow;
    private int hideMobileNumberRow;
    public boolean isGram = false;
    private int keepOriginalFilenameDetailRow;
    private ListAdapter listAdapter;
    private ListView listView;
    private int mediaDownloadSection;
    private int mediaDownloadSection2;
    private int messagesSectionRow;
    private int messagesSectionRow2;
    private int mutualShow;
    private int notificationInvertMessagesOrderRow;
    private int notificationSection2Row;
    private int notificationSectionRow;
    private int overscrollRow;
    private int plusSettingsSectionRow;
    private int plusSettingsSectionRow2;
    private int privacySectionRow;
    private int privacySectionRow2;
    private int profileSectionRow;
    private int profileSectionRow2;
    private int profileSharedOptionsRow;
    private int resetPlusSettingsRow;
    private boolean reseting = false;
    private int restorePlusSettingsRow;
    private int rowCount;
    private int savePlusSettingsRow;
    private boolean saving = false;
    private int selectFont;
    private int settingsSectionRow;
    private int settingsSectionRow2;
    private int showAndroidEmojiRow;
    private int showCoinRow;
    private int showUsernameRow;
    private int sortDialogOrderByUnread;
    private int useDeviceFontRow;

    /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$1 */
    class C10661 extends ActionBarMenuOnItemClick {
        C10661() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                PlusSettingsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$4 */
    class C10854 implements OnMultiChoiceClickListener {
        C10854() {
        }

        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            boolean z = true;
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
            String str;
            if (which == 0) {
                str = "hideSharedMedia";
                if (isChecked) {
                    z = false;
                }
                editor.putBoolean(str, z);
            } else if (which == 1) {
                str = "hideSharedFiles";
                if (isChecked) {
                    z = false;
                }
                editor.putBoolean(str, z);
            } else if (which == 2) {
                str = "hideSharedMusic";
                if (isChecked) {
                    z = false;
                }
                editor.putBoolean(str, z);
            } else if (which == 3) {
                str = "hideSharedLinks";
                if (isChecked) {
                    z = false;
                }
                editor.putBoolean(str, z);
            }
            editor.apply();
        }
    }

    /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$5 */
    class C10865 implements OnMultiChoiceClickListener {
        C10865() {
        }

        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
            if (which == 0) {
                editor.putBoolean("showDSBtnUsers", isChecked);
            } else if (which == 1) {
                editor.putBoolean("showDSBtnGroups", isChecked);
            } else if (which == 2) {
                editor.putBoolean("showDSBtnSGroups", isChecked);
            } else if (which == 3) {
                editor.putBoolean("showDSBtnChannels", isChecked);
            } else if (which == 4) {
                editor.putBoolean("showDSBtnBots", isChecked);
            }
            editor.apply();
        }
    }

    /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$6 */
    class C10876 implements OnPreDrawListener {
        C10876() {
        }

        public boolean onPreDraw() {
            if (PlusSettingsActivity.this.fragmentView != null) {
                PlusSettingsActivity.this.fragmentView.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            return true;
        }
    }

    private class ListAdapter extends BaseFragmentAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public boolean areAllItemsEnabled() {
            return false;
        }

        public boolean isEnabled(int i) {
            return i == PlusSettingsActivity.this.showCoinRow || i == PlusSettingsActivity.this.showAndroidEmojiRow || i == PlusSettingsActivity.this.useDeviceFontRow || i == PlusSettingsActivity.this.emojiPopupSize || i == PlusSettingsActivity.this.mutualShow || i == PlusSettingsActivity.this.dialogsTabsHeightRow || i == PlusSettingsActivity.this.dialogsTabsRow || i == PlusSettingsActivity.this.chatShowDirectShareBtn || i == PlusSettingsActivity.this.profileSharedOptionsRow || i == PlusSettingsActivity.this.selectFont || i == PlusSettingsActivity.this.dialogsTabsSort || i == PlusSettingsActivity.this.disableAudioStopRow || i == PlusSettingsActivity.this.disableMessageClickRow || i == PlusSettingsActivity.this.chatDirectShareToMenu || i == PlusSettingsActivity.this.chatDirectShareFavsFirst || i == PlusSettingsActivity.this.chatShowEditedMarkRow || i == PlusSettingsActivity.this.chatHideLeftGroupRow || i == PlusSettingsActivity.this.chatHideJoinedGroupRow || i == PlusSettingsActivity.this.chatHideBotKeyboardRow || i == PlusSettingsActivity.this.dialogsHideTabsCheckRow || i == PlusSettingsActivity.this.dialogsDisableTabsAnimationCheckRow || i == PlusSettingsActivity.this.dialogsInfiniteTabsSwipe || i == PlusSettingsActivity.this.dialogsHideTabsCounters || i == PlusSettingsActivity.this.dialogsTabsCountersCountChats || i == PlusSettingsActivity.this.dialogsTabsCountersCountNotMuted || i == PlusSettingsActivity.this.chatSearchUserOnTwitterRow || i == PlusSettingsActivity.this.confirmForStickers || i == PlusSettingsActivity.this.dialogsPicClickRow || i == PlusSettingsActivity.this.dialogsGroupPicClickRow || i == PlusSettingsActivity.this.hideMobileNumberRow || i == PlusSettingsActivity.this.showUsernameRow || i == PlusSettingsActivity.this.notificationInvertMessagesOrderRow || i == PlusSettingsActivity.this.savePlusSettingsRow || i == PlusSettingsActivity.this.restorePlusSettingsRow || i == PlusSettingsActivity.this.resetPlusSettingsRow || i == PlusSettingsActivity.this.sortDialogOrderByUnread;
        }

        public int getCount() {
            return PlusSettingsActivity.this.rowCount;
        }

        public Object getItem(int i) {
            return null;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public boolean hasStableIds() {
            return false;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            int type = getItemViewType(i);
            View emptyCell;
            if (type == 0) {
                if (view == null) {
                    emptyCell = new EmptyCell(this.mContext);
                }
                if (i == PlusSettingsActivity.this.overscrollRow) {
                    ((EmptyCell) view).setHeight(AndroidUtilities.dp(88.0f));
                    return view;
                }
                ((EmptyCell) view).setHeight(AndroidUtilities.dp(16.0f));
                return view;
            } else if (type == 1) {
                if (view == null) {
                    return new ShadowSectionCell(this.mContext);
                }
                return view;
            } else if (type == 2) {
                if (view == null) {
                    emptyCell = new TextSettingsCell(this.mContext);
                }
                TextSettingsCell textCell = (TextSettingsCell) view;
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                int size;
                if (i == PlusSettingsActivity.this.emojiPopupSize) {
                    size = preferences.getInt("emojiPopupSize", AndroidUtilities.isTablet() ? 65 : 60);
                    textCell.setTextAndValue(LocaleController.getString("EmojiPopupSize", R.string.EmojiPopupSize), String.format("%d", new Object[]{Integer.valueOf(size)}), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.dialogsTabsHeightRow) {
                    size = preferences.getInt("tabsHeight", AndroidUtilities.isTablet() ? 42 : 40);
                    textCell.setTextAndValue(LocaleController.getString("TabsHeight", R.string.TabsHeight), String.format("%d", new Object[]{Integer.valueOf(size)}), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.dialogsPicClickRow) {
                    sort = preferences.getInt("dialogsClickOnPic", 0);
                    if (sort == 0) {
                        value = LocaleController.getString("RowGradientDisabled", R.string.RowGradientDisabled);
                    } else if (sort == 1) {
                        value = LocaleController.getString("ShowPics", R.string.ShowPics);
                    } else {
                        value = LocaleController.getString("ShowProfile", R.string.ShowProfile);
                    }
                    textCell.setTextAndValue(LocaleController.getString("ClickOnContactPic", R.string.ClickOnContactPic), value, true);
                    return view;
                } else if (i != PlusSettingsActivity.this.dialogsGroupPicClickRow) {
                    return view;
                } else {
                    sort = preferences.getInt("dialogsClickOnGroupPic", 0);
                    if (sort == 0) {
                        value = LocaleController.getString("RowGradientDisabled", R.string.RowGradientDisabled);
                    } else if (sort == 1) {
                        value = LocaleController.getString("ShowPics", R.string.ShowPics);
                    } else {
                        value = LocaleController.getString("ShowProfile", R.string.ShowProfile);
                    }
                    textCell.setTextAndValue(LocaleController.getString("ClickOnGroupPic", R.string.ClickOnGroupPic), value, true);
                    return view;
                }
            } else if (type == 3) {
                if (view == null) {
                    emptyCell = new TextCheckCell(this.mContext);
                }
                TextCheckCell textCell2 = (TextCheckCell) view;
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                if (i == PlusSettingsActivity.this.disableAudioStopRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("DisableAudioStop", R.string.DisableAudioStop), preferences.getBoolean("disableAudioStop", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.disableMessageClickRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("DisableMessageClick", R.string.DisableMessageClick), preferences.getBoolean("disableMessageClick", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.chatDirectShareToMenu) {
                    textCell2.setTextAndCheck(LocaleController.getString("DirectShareToMenu", R.string.DirectShareToMenu), preferences.getBoolean("directShareToMenu", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.chatDirectShareFavsFirst) {
                    textCell2.setTextAndCheck(LocaleController.getString("DirectShareShowFavsFirst", R.string.DirectShareShowFavsFirst), preferences.getBoolean("directShareFavsFirst", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.chatShowEditedMarkRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("ShowEditedMark", R.string.ShowEditedMark), preferences.getBoolean("showEditedMark", true), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.chatHideLeftGroupRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("HideLeftGroup", R.string.HideLeftGroup), preferences.getBoolean("hideLeftGroup", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.chatHideJoinedGroupRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("HideJoinedGroup", R.string.HideJoinedGroup), preferences.getBoolean("hideJoinedGroup", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.chatHideBotKeyboardRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("HideBotKeyboard", R.string.HideBotKeyboard), preferences.getBoolean("hideBotKeyboard", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.showAndroidEmojiRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("ShowAndroidEmoji", R.string.ShowAndroidEmoji), preferences.getBoolean("showAndroidEmoji", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.showCoinRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("showAdsIcon", R.string.showAdsIcon), AppPreferences.isShowCoinIcon(ApplicationLoader.applicationContext), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.useDeviceFontRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("UseDeviceFont", R.string.UseDeviceFont), preferences.getBoolean("useDeviceFont", false), false);
                    return view;
                } else if (i == PlusSettingsActivity.this.mutualShow) {
                    textCell2.setTextAndCheck(LocaleController.getString("mutualShow", R.string.mutualShow), preferences.getBoolean("mutualShow", false), false);
                    return view;
                } else if (i == PlusSettingsActivity.this.dialogsHideTabsCheckRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("HideTabs", R.string.HideTabs), preferences.getBoolean("hideTabs", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.dialogsDisableTabsAnimationCheckRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("DisableTabsAnimation", R.string.DisableTabsAnimation), preferences.getBoolean("disableTabsAnimation", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.dialogsInfiniteTabsSwipe) {
                    textCell2.setTextAndCheck(LocaleController.getString("InfiniteSwipe", R.string.InfiniteSwipe), preferences.getBoolean("infiniteTabsSwipe", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.dialogsHideTabsCounters) {
                    textCell2.setTextAndCheck(LocaleController.getString("HideTabsCounters", R.string.HideTabsCounters), preferences.getBoolean("hideTabsCounters", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.dialogsTabsCountersCountChats) {
                    textCell2.setTextAndCheck(LocaleController.getString("HeaderTabCounterCountChats", R.string.HeaderTabCounterCountChats), preferences.getBoolean("tabsCountersCountChats", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.dialogsTabsCountersCountNotMuted) {
                    textCell2.setTextAndCheck(LocaleController.getString("HeaderTabCounterCountNotMuted", R.string.HeaderTabCounterCountNotMuted), preferences.getBoolean("tabsCountersCountNotMuted", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.sortDialogOrderByUnread) {
                    textCell2.setTextAndCheck(LocaleController.getString("ShowUnreadDialogsAtTop", R.string.ShowUnreadDialogsAtTop), AppPreferences.isSortDialogsOrderByUnread(this.mContext), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.hideMobileNumberRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("HideMobile", R.string.HideMobile), preferences.getBoolean("hideMobile", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.showUsernameRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("ShowUsernameInMenu", R.string.ShowUsernameInMenu), preferences.getBoolean("showUsername", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.notificationInvertMessagesOrderRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("InvertMessageOrder", R.string.InvertMessageOrder), preferences.getBoolean("invertMessagesOrder", false), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.chatSearchUserOnTwitterRow) {
                    textCell2.setTextAndCheck(LocaleController.getString("SearchUserOnTwitter", R.string.SearchUserOnTwitter), preferences.getBoolean("searchOnTwitter", true), false);
                    return view;
                } else if (i != PlusSettingsActivity.this.confirmForStickers) {
                    return view;
                } else {
                    textCell2.setTextAndCheck(LocaleController.getString("confirmForStickers", R.string.confirmForStickers), preferences.getBoolean("confirmForStickers", true), false);
                    return view;
                }
            } else if (type == 4) {
                if (view == null) {
                    emptyCell = new HeaderCell(this.mContext);
                }
                if (i == PlusSettingsActivity.this.settingsSectionRow2) {
                    ((HeaderCell) view).setText(LocaleController.getString("General", R.string.General));
                    return view;
                } else if (i == PlusSettingsActivity.this.messagesSectionRow2) {
                    ((HeaderCell) view).setText(LocaleController.getString("MessagesSettings", R.string.MessagesSettings));
                    return view;
                } else if (i == PlusSettingsActivity.this.profileSectionRow2) {
                    ((HeaderCell) view).setText(LocaleController.getString("ProfileScreen", R.string.ProfileScreen));
                    return view;
                } else if (i == PlusSettingsActivity.this.drawerSectionRow2) {
                    ((HeaderCell) view).setText(LocaleController.getString("NavigationDrawer", R.string.NavigationDrawer));
                    return view;
                } else if (i == PlusSettingsActivity.this.privacySectionRow2) {
                    ((HeaderCell) view).setText(LocaleController.getString("PrivacySettings", R.string.PrivacySettings));
                    return view;
                } else if (i == PlusSettingsActivity.this.mediaDownloadSection2) {
                    ((HeaderCell) view).setText(LocaleController.getString("SharedMedia", R.string.SharedMedia));
                    return view;
                } else if (i == PlusSettingsActivity.this.dialogsSectionRow2) {
                    ((HeaderCell) view).setText(LocaleController.getString("DialogsSettings", R.string.DialogsSettings));
                    return view;
                } else if (i == PlusSettingsActivity.this.notificationSection2Row) {
                    ((HeaderCell) view).setText(LocaleController.getString("Notifications", R.string.Notifications));
                    return view;
                } else if (i != PlusSettingsActivity.this.plusSettingsSectionRow2) {
                    return view;
                } else {
                    ((HeaderCell) view).setText(LocaleController.getString("PlusSettings", R.string.PlusSettings));
                    return view;
                }
            } else if (type == 6) {
                if (view == null) {
                    emptyCell = new TextDetailSettingsCell(this.mContext);
                }
                TextDetailSettingsCell textCell3 = (TextDetailSettingsCell) view;
                if (i == PlusSettingsActivity.this.dialogsTabsSort) {
                    textCell3.setTextAndValue(LocaleController.getString("sort_tabs", R.string.sort_tabs), LocaleController.getString("sort_tabs_details", R.string.sort_tabs_details), true);
                }
                String text;
                if (i == PlusSettingsActivity.this.dialogsTabsRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    boolean hideUsers = preferences.getBoolean("hideUsers", false);
                    boolean hideGroups = preferences.getBoolean("hideGroups", false);
                    boolean hideSGroups = preferences.getBoolean("hideSGroups", false);
                    boolean hideChannels = preferences.getBoolean("hideChannels", false);
                    boolean hideBots = preferences.getBoolean("hideBots", false);
                    boolean hideFavs = preferences.getBoolean("hideFavs", false);
                    boolean hideUnread = preferences.getBoolean("hideUnread", false);
                    value = LocaleController.getString("HideShowTabs", R.string.HideShowTabs);
                    text = "";
                    if (!hideUsers) {
                        text = text + LocaleController.getString("Users", R.string.Users);
                    }
                    if (!hideGroups) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("Groups", R.string.Groups);
                    }
                    if (!hideSGroups) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("SuperGroups", R.string.SuperGroups);
                    }
                    if (!hideChannels) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("Channels", R.string.Channels);
                    }
                    if (!hideBots) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("Bots", R.string.Bots);
                    }
                    if (!hideFavs) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("Favorites", R.string.Favorites);
                    }
                    if (!hideUnread) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("unreadChats", R.string.unreadChats);
                    }
                    if (text.length() == 0) {
                        text = "";
                    }
                    textCell3.setTextAndValue(value, text, true);
                    return view;
                } else if (i == PlusSettingsActivity.this.chatShowDirectShareBtn) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    boolean showDSBtnUsers = preferences.getBoolean("showDSBtnUsers", false);
                    boolean showDSBtnGroups = preferences.getBoolean("showDSBtnGroups", true);
                    boolean showDSBtnSGroups = preferences.getBoolean("showDSBtnSGroups", true);
                    boolean showDSBtnChannels = preferences.getBoolean("showDSBtnChannels", true);
                    boolean showDSBtnBots = preferences.getBoolean("showDSBtnBots", true);
                    value = LocaleController.getString("ShowDirectShareButton", R.string.ShowDirectShareButton);
                    text = "";
                    if (showDSBtnUsers) {
                        text = text + LocaleController.getString("Users", R.string.Users);
                    }
                    if (showDSBtnGroups) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("Groups", R.string.Groups);
                    }
                    if (showDSBtnSGroups) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("SuperGroups", R.string.SuperGroups);
                    }
                    if (showDSBtnChannels) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("Channels", R.string.Channels);
                    }
                    if (showDSBtnBots) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("Bots", R.string.Bots);
                    }
                    if (text.length() == 0) {
                        text = LocaleController.getString("Channels", R.string.UsernameEmpty);
                    }
                    textCell3.setTextAndValue(value, text, true);
                    return view;
                } else if (i == PlusSettingsActivity.this.profileSharedOptionsRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    boolean hideMedia = preferences.getBoolean("hideSharedMedia", false);
                    boolean hideFiles = preferences.getBoolean("hideSharedFiles", false);
                    boolean hideMusic = preferences.getBoolean("hideSharedMusic", false);
                    boolean hideLinks = preferences.getBoolean("hideSharedLinks", false);
                    value = LocaleController.getString("SharedMedia", R.string.SharedMedia);
                    text = "";
                    if (!hideMedia) {
                        text = text + LocaleController.getString("Users", R.string.SharedMediaTitle);
                    }
                    if (!hideFiles) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("DocumentsTitle", R.string.DocumentsTitle);
                    }
                    if (!hideMusic) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("AudioTitle", R.string.AudioTitle);
                    }
                    if (!hideLinks && BuildVars.DEBUG_VERSION) {
                        if (text.length() != 0) {
                            text = text + ", ";
                        }
                        text = text + LocaleController.getString("LinksTitle", R.string.LinksTitle);
                    }
                    if (text.length() == 0) {
                        text = "";
                    }
                    textCell3.setTextAndValue(value, text, true);
                    return view;
                } else if (i == PlusSettingsActivity.this.savePlusSettingsRow) {
                    textCell3.setMultilineDetail(true);
                    textCell3.setTextAndValue(LocaleController.getString("SaveSettings", R.string.SaveSettings), LocaleController.getString("SaveSettingsSum", R.string.SaveSettingsSum), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.restorePlusSettingsRow) {
                    textCell3.setMultilineDetail(true);
                    textCell3.setTextAndValue(LocaleController.getString("RestoreSettings", R.string.RestoreSettings), LocaleController.getString("RestoreSettingsSum", R.string.RestoreSettingsSum), true);
                    return view;
                } else if (i == PlusSettingsActivity.this.selectFont) {
                    textCell3.setMultilineDetail(true);
                    textCell3.setTextAndValue(LocaleController.getString("selectFont", R.string.select_font), "", true);
                    return view;
                } else if (i != PlusSettingsActivity.this.resetPlusSettingsRow) {
                    return view;
                } else {
                    textCell3.setMultilineDetail(true);
                    textCell3.setTextAndValue(LocaleController.getString("ResetSettings", R.string.ResetSettings), LocaleController.getString("ResetSettingsSum", R.string.ResetSettingsSum), false);
                    return view;
                }
            } else if (type != 7) {
                return view;
            } else {
                if (view == null) {
                    emptyCell = new TextInfoPrivacyCell(this.mContext);
                }
                if (i != PlusSettingsActivity.this.keepOriginalFilenameDetailRow) {
                    return view;
                }
                ((TextInfoPrivacyCell) view).setText(LocaleController.getString("KeepOriginalFilenameHelp", R.string.KeepOriginalFilenameHelp));
                view.setBackgroundResource(R.drawable.greydivider);
                return view;
            }
        }

        public int getItemViewType(int i) {
            if (i == PlusSettingsActivity.this.emptyRow || i == PlusSettingsActivity.this.overscrollRow) {
                return 0;
            }
            if (i == PlusSettingsActivity.this.settingsSectionRow || i == PlusSettingsActivity.this.messagesSectionRow || i == PlusSettingsActivity.this.profileSectionRow || i == PlusSettingsActivity.this.drawerSectionRow || i == PlusSettingsActivity.this.privacySectionRow || i == PlusSettingsActivity.this.mediaDownloadSection || i == PlusSettingsActivity.this.dialogsSectionRow || i == PlusSettingsActivity.this.notificationSectionRow || i == PlusSettingsActivity.this.plusSettingsSectionRow) {
                return 1;
            }
            if (i == PlusSettingsActivity.this.disableAudioStopRow || i == PlusSettingsActivity.this.disableMessageClickRow || i == PlusSettingsActivity.this.dialogsHideTabsCheckRow || i == PlusSettingsActivity.this.dialogsDisableTabsAnimationCheckRow || i == PlusSettingsActivity.this.dialogsInfiniteTabsSwipe || i == PlusSettingsActivity.this.showCoinRow || i == PlusSettingsActivity.this.dialogsHideTabsCounters || i == PlusSettingsActivity.this.dialogsTabsCountersCountChats || i == PlusSettingsActivity.this.dialogsTabsCountersCountNotMuted || i == PlusSettingsActivity.this.showAndroidEmojiRow || i == PlusSettingsActivity.this.useDeviceFontRow || i == PlusSettingsActivity.this.mutualShow || i == PlusSettingsActivity.this.hideMobileNumberRow || i == PlusSettingsActivity.this.showUsernameRow || i == PlusSettingsActivity.this.chatDirectShareToMenu || i == PlusSettingsActivity.this.chatDirectShareFavsFirst || i == PlusSettingsActivity.this.chatShowEditedMarkRow || i == PlusSettingsActivity.this.chatHideLeftGroupRow || i == PlusSettingsActivity.this.chatHideJoinedGroupRow || i == PlusSettingsActivity.this.chatHideBotKeyboardRow || i == PlusSettingsActivity.this.notificationInvertMessagesOrderRow || i == PlusSettingsActivity.this.chatSearchUserOnTwitterRow || i == PlusSettingsActivity.this.confirmForStickers || i == PlusSettingsActivity.this.sortDialogOrderByUnread) {
                return 3;
            }
            if (i == PlusSettingsActivity.this.emojiPopupSize || i == PlusSettingsActivity.this.dialogsTabsHeightRow || i == PlusSettingsActivity.this.dialogsPicClickRow || i == PlusSettingsActivity.this.dialogsGroupPicClickRow) {
                return 2;
            }
            if (i == PlusSettingsActivity.this.dialogsTabsRow || i == PlusSettingsActivity.this.chatShowDirectShareBtn || i == PlusSettingsActivity.this.profileSharedOptionsRow || i == PlusSettingsActivity.this.savePlusSettingsRow || i == PlusSettingsActivity.this.restorePlusSettingsRow || i == PlusSettingsActivity.this.selectFont || i == PlusSettingsActivity.this.dialogsTabsSort || i == PlusSettingsActivity.this.resetPlusSettingsRow) {
                return 6;
            }
            if (i == PlusSettingsActivity.this.keepOriginalFilenameDetailRow) {
                return 7;
            }
            if (i == PlusSettingsActivity.this.settingsSectionRow2 || i == PlusSettingsActivity.this.messagesSectionRow2 || i == PlusSettingsActivity.this.profileSectionRow2 || i == PlusSettingsActivity.this.drawerSectionRow2 || i == PlusSettingsActivity.this.privacySectionRow2 || i == PlusSettingsActivity.this.mediaDownloadSection2 || i == PlusSettingsActivity.this.dialogsSectionRow2 || i == PlusSettingsActivity.this.notificationSection2Row || i == PlusSettingsActivity.this.plusSettingsSectionRow2) {
                return 4;
            }
            return 2;
        }

        public int getViewTypeCount() {
            return 8;
        }

        public boolean isEmpty() {
            return false;
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.refreshTabs);
        if (BuildConfig.FLAVOR.contentEquals("gram")) {
            this.isGram = true;
        }
        this.rowCount = 0;
        this.overscrollRow = -1;
        this.emptyRow = -1;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.settingsSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.settingsSectionRow2 = i;
        if (VERSION.SDK_INT >= 19) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.showAndroidEmojiRow = i;
        } else {
            this.showAndroidEmojiRow = -1;
        }
        if (AppPreferences.isAdsEnable(ApplicationLoader.applicationContext)) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.showCoinRow = i;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.useDeviceFontRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.mutualShow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.selectFont = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messagesSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messagesSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.emojiPopupSize = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.disableAudioStopRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.disableMessageClickRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.chatShowDirectShareBtn = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.chatDirectShareToMenu = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.chatDirectShareFavsFirst = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.chatShowEditedMarkRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.chatHideLeftGroupRow = i;
        this.chatHideJoinedGroupRow = -1;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.chatHideBotKeyboardRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.chatSearchUserOnTwitterRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.confirmForStickers = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsSectionRow2 = i;
        if (!this.isGram) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.dialogsHideTabsCheckRow = i;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsTabsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsTabsSort = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsTabsHeightRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsDisableTabsAnimationCheckRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsInfiniteTabsSwipe = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsHideTabsCounters = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsTabsCountersCountNotMuted = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsTabsCountersCountChats = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsPicClickRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dialogsGroupPicClickRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.sortDialogOrderByUnread = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.profileSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.profileSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.profileSharedOptionsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.drawerSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.drawerSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.showUsernameRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.notificationSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.notificationSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.notificationInvertMessagesOrderRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.privacySectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.privacySectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.hideMobileNumberRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.mediaDownloadSection = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.mediaDownloadSection2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.keepOriginalFilenameDetailRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.plusSettingsSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.plusSettingsSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.savePlusSettingsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.restorePlusSettingsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.resetPlusSettingsRow = i;
        MessagesController.getInstance().loadFullUser(UserConfig.getCurrentUser(), this.classGuid, true);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.refreshTabs);
    }

    public View createView(final Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setTitle(LocaleController.getString("PlusSettings", R.string.PlusSettings));
        this.actionBar.setActionBarMenuOnItemClick(new C10661());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
        this.listView = new ListView(context);
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences(AppUtilities.THEME_PREFS, 0);
        this.listView.setDivider(null);
        this.listView.setDividerHeight(0);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setBackgroundColor(Theme.getColor(Theme.key_chat_inBubble));
        this.listView.setAdapter(this.listAdapter);
        int bgColor = preferences.getInt("prefBGColor", -1);
        AppUtilities.setListViewEdgeEffectColor(this.listView, preferences.getInt("prefHeaderColor", preferences.getInt("themeColor", AppUtilities.defColor)));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new OnItemClickListener() {

            /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$2 */
            class C10732 implements Runnable {
                C10732() {
                }

                public void run() {
                    if (PlusSettingsActivity.this.getParentActivity() != null) {
                        Toast.makeText(PlusSettingsActivity.this.getParentActivity(), LocaleController.getString("AppWillRestart", R.string.AppWillRestart), 0).show();
                    }
                }
            }

            /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$3 */
            class C10743 implements Runnable {
                C10743() {
                }

                public void run() {
                    AppUtilities.restartApp();
                }
            }

            /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$5 */
            class C10785 implements OnClickListener {
                C10785() {
                }

                public void onClick(DialogInterface dialog, int which) {
                }
            }

            /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$6 */
            class C10796 implements OnClickListener {
                C10796() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                    editor.putInt("dialogsClickOnPic", which);
                    editor.apply();
                    if (PlusSettingsActivity.this.listView != null) {
                        PlusSettingsActivity.this.listView.invalidateViews();
                    }
                }
            }

            /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$7 */
            class C10807 implements OnClickListener {
                C10807() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                    editor.putInt("dialogsClickOnGroupPic", which);
                    editor.apply();
                    if (PlusSettingsActivity.this.listView != null) {
                        PlusSettingsActivity.this.listView.invalidateViews();
                    }
                }
            }

            /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$9 */
            class C10829 implements OnClickListener {
                C10829() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(13)});
                    if (PlusSettingsActivity.this.listView != null) {
                        PlusSettingsActivity.this.listView.invalidateViews();
                    }
                }
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Builder builder;
                View numberPicker;
                final View view2;
                if (i == PlusSettingsActivity.this.emojiPopupSize) {
                    if (PlusSettingsActivity.this.getParentActivity() != null) {
                        builder = new Builder(PlusSettingsActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("EmojiPopupSize", R.string.EmojiPopupSize));
                        numberPicker = new NumberPicker(PlusSettingsActivity.this.getParentActivity());
                        numberPicker.setMinValue(60);
                        numberPicker.setMaxValue(100);
                        numberPicker.setValue(ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getInt("emojiPopupSize", AndroidUtilities.isTablet() ? 65 : 60));
                        builder.setView(numberPicker);
                        view2 = numberPicker;
                        builder.setNegativeButton(LocaleController.getString("Done", R.string.Done), new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                                editor.putInt("emojiPopupSize", view2.getValue());
                                editor.apply();
                                if (PlusSettingsActivity.this.listView != null) {
                                    PlusSettingsActivity.this.listView.invalidateViews();
                                }
                            }
                        });
                        PlusSettingsActivity.this.showDialog(builder.create());
                    }
                } else if (i == PlusSettingsActivity.this.showAndroidEmojiRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    editor = preferences.edit();
                    enabled = preferences.getBoolean("showAndroidEmoji", false);
                    editor.putBoolean("showAndroidEmoji", !enabled);
                    editor.apply();
                    ApplicationLoader.SHOW_ANDROID_EMOJI = !enabled;
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!enabled);
                    }
                } else if (AppPreferences.isAdsEnable(PlusSettingsActivity.this.getParentActivity()) && i == PlusSettingsActivity.this.showCoinRow) {
                    enabled = AppPreferences.isShowCoinIcon(ApplicationLoader.applicationContext);
                    AppPreferences.setShowCoinIcon(ApplicationLoader.applicationContext, !enabled);
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!enabled);
                    }
                } else if (i == PlusSettingsActivity.this.useDeviceFontRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    editor = preferences.edit();
                    enabled = preferences.getBoolean("useDeviceFont", false);
                    editor.putBoolean("useDeviceFont", !enabled);
                    editor.apply();
                    ApplicationLoader.USE_DEVICE_FONT = !enabled;
                    AppUtilities.needRestart = true;
                    AndroidUtilities.runOnUIThread(new C10732());
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!enabled);
                    }
                    if (AppUtilities.needRestart) {
                        new Handler().postDelayed(new C10743(), 1000);
                    }
                } else if (i == PlusSettingsActivity.this.selectFont) {
                    final Dialog dialog = new Dialog(PlusSettingsActivity.this.getParentActivity());
                    dialog.setContentView(R.layout.dialog_select_lang);
                    ViewGroup llContainer = (LinearLayout) dialog.findViewById(R.id.container);
                    LayoutInflater inflater = (LayoutInflater) ApplicationLoader.applicationContext.getSystemService("layout_inflater");
                    String[] fonts = FontUtil.FONTS;
                    for (int j = 0; j < fonts.length; j++) {
                        String font = fonts[j];
                        View item = inflater.inflate(R.layout.item_font, llContainer, false);
                        ImageView ivCheck = (ImageView) item.findViewById(R.id.iv_checked);
                        ((TextView) item.findViewById(R.id.tv_font_name)).setText(font);
                        if (AppPreferences.getSelectedFont(ApplicationLoader.applicationContext) == j) {
                            ivCheck.setColorFilter(PlusSettingsActivity.this.getParentActivity().getResources().getColor(R.color.green));
                        } else {
                            ivCheck.setColorFilter(PlusSettingsActivity.this.getParentActivity().getResources().getColor(R.color.gray));
                        }
                        final int finalJ = j;
                        item.setOnClickListener(new View.OnClickListener() {

                            /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$4$1 */
                            class C10751 implements Runnable {
                                C10751() {
                                }

                                public void run() {
                                    if (PlusSettingsActivity.this.getParentActivity() != null) {
                                        Toast.makeText(PlusSettingsActivity.this.getParentActivity(), LocaleController.getString("AppWillRestart", R.string.AppWillRestart), 0).show();
                                    }
                                }
                            }

                            /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$4$2 */
                            class C10762 implements Runnable {
                                C10762() {
                                }

                                public void run() {
                                    AppUtilities.restartApp();
                                }
                            }

                            public void onClick(View view) {
                                AppPreferences.setSelectedFont(ApplicationLoader.applicationContext, finalJ);
                                dialog.dismiss();
                                AppUtilities.needRestart = true;
                                AndroidUtilities.runOnUIThread(new C10751());
                                if (AppUtilities.needRestart) {
                                    new Handler().postDelayed(new C10762(), 1000);
                                }
                            }
                        });
                        llContainer.addView(item);
                    }
                    dialog.show();
                } else if (i == PlusSettingsActivity.this.mutualShow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    editor = preferences.edit();
                    enabled = preferences.getBoolean("mutualShow", false);
                    editor.putBoolean("mutualShow", !enabled);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        if (!enabled) {
                            new Builder(context).setTitle(LocaleController.getString("MyAppName", R.string.MyAppName)).setMessage("با استفاده از این امکان میتوانید کسانی که شماره موبایل شما را دارند را در قسمت مخاطبین ببینید").setPositiveButton(LocaleController.getString(Constants.ACTION_TYPE_OK, R.string.ok), new C10785()).show();
                        }
                        r44 = (TextCheckCell) view;
                        if (enabled) {
                            r37 = false;
                        } else {
                            r37 = true;
                        }
                        r44.setChecked(r37);
                    }
                } else if (i == PlusSettingsActivity.this.disableAudioStopRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    send = preferences.getBoolean("disableAudioStop", false);
                    editor = preferences.edit();
                    editor.putBoolean("disableAudioStop", !send);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!send);
                    }
                } else if (i == PlusSettingsActivity.this.disableMessageClickRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    send = preferences.getBoolean("disableMessageClick", false);
                    editor = preferences.edit();
                    editor.putBoolean("disableMessageClick", !send);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!send);
                    }
                } else if (i == PlusSettingsActivity.this.chatDirectShareToMenu) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    send = preferences.getBoolean("directShareToMenu", false);
                    editor = preferences.edit();
                    editor.putBoolean("directShareToMenu", !send);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!send);
                    }
                } else if (i == PlusSettingsActivity.this.chatDirectShareFavsFirst) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    send = preferences.getBoolean("directShareFavsFirst", false);
                    editor = preferences.edit();
                    editor.putBoolean("directShareFavsFirst", !send);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!send);
                    }
                } else if (i == PlusSettingsActivity.this.chatShowEditedMarkRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    send = preferences.getBoolean("showEditedMark", true);
                    editor = preferences.edit();
                    editor.putBoolean("showEditedMark", !send);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!send);
                    }
                } else if (i == PlusSettingsActivity.this.chatHideLeftGroupRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    hide = preferences.getBoolean("hideLeftGroup", false);
                    MessagesController.getInstance().hideLeftGroup = !hide;
                    editor = preferences.edit();
                    editor.putBoolean("hideLeftGroup", !hide);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        r44 = (TextCheckCell) view;
                        if (hide) {
                            r37 = false;
                        } else {
                            r37 = true;
                        }
                        r44.setChecked(r37);
                    }
                } else if (i == PlusSettingsActivity.this.chatHideJoinedGroupRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    hide = preferences.getBoolean("hideJoinedGroup", false);
                    MessagesController.getInstance().hideJoinedGroup = !hide;
                    editor = preferences.edit();
                    editor.putBoolean("hideJoinedGroup", !hide);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        r44 = (TextCheckCell) view;
                        if (hide) {
                            r37 = false;
                        } else {
                            r37 = true;
                        }
                        r44.setChecked(r37);
                    }
                } else if (i == PlusSettingsActivity.this.chatHideBotKeyboardRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    hide = preferences.getBoolean("hideBotKeyboard", false);
                    editor = preferences.edit();
                    editor.putBoolean("hideBotKeyboard", !hide);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!hide);
                    }
                } else if (i == PlusSettingsActivity.this.dialogsHideTabsCheckRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    hide = preferences.getBoolean("hideTabs", false);
                    editor = preferences.edit();
                    editor.putBoolean("hideTabs", !hide);
                    editor.apply();
                    boolean hideUsers = preferences.getBoolean("hideUsers", false);
                    boolean hideGroups = preferences.getBoolean("hideGroups", false);
                    boolean hideSGroups = preferences.getBoolean("hideSGroups", false);
                    boolean hideChannels = preferences.getBoolean("hideChannels", false);
                    boolean hideBots = preferences.getBoolean("hideBots", false);
                    boolean hideFavs = preferences.getBoolean("hideFavs", false);
                    boolean hideUnread = preferences.getBoolean("hideUnread", false);
                    if (hideUsers && hideGroups && hideSGroups && hideChannels && hideBots && hideFavs && hideUnread && PlusSettingsActivity.this.listView != null) {
                        PlusSettingsActivity.this.listView.invalidateViews();
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(10)});
                    if (view instanceof TextCheckCell) {
                        r44 = (TextCheckCell) view;
                        if (hide) {
                            r37 = false;
                        } else {
                            r37 = true;
                        }
                        r44.setChecked(r37);
                    }
                } else if (i == PlusSettingsActivity.this.dialogsDisableTabsAnimationCheckRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    disable = preferences.getBoolean("disableTabsAnimation", false);
                    editor = preferences.edit();
                    editor.putBoolean("disableTabsAnimation", !disable);
                    editor.apply();
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(11)});
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!disable);
                    }
                } else if (i == PlusSettingsActivity.this.dialogsInfiniteTabsSwipe) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    disable = preferences.getBoolean("infiniteTabsSwipe", false);
                    editor = preferences.edit();
                    editor.putBoolean("infiniteTabsSwipe", !disable);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!disable);
                    }
                } else if (i == PlusSettingsActivity.this.dialogsHideTabsCounters) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    disable = preferences.getBoolean("hideTabsCounters", false);
                    editor = preferences.edit();
                    editor.putBoolean("hideTabsCounters", !disable);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!disable);
                    }
                } else if (i == PlusSettingsActivity.this.dialogsTabsCountersCountChats) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    disable = preferences.getBoolean("tabsCountersCountChats", false);
                    editor = preferences.edit();
                    editor.putBoolean("tabsCountersCountChats", !disable);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!disable);
                    }
                } else if (i == PlusSettingsActivity.this.dialogsTabsCountersCountNotMuted) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    disable = preferences.getBoolean("tabsCountersCountNotMuted", false);
                    editor = preferences.edit();
                    editor.putBoolean("tabsCountersCountNotMuted", !disable);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!disable);
                    }
                } else if (i == PlusSettingsActivity.this.sortDialogOrderByUnread) {
                    boolean isSortDialogsOrderByUnread = AppPreferences.isSortDialogsOrderByUnread(context);
                    AppPreferences.setSortDialogsOrderByUnread(context, !isSortDialogsOrderByUnread);
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!isSortDialogsOrderByUnread);
                    }
                } else if (i == PlusSettingsActivity.this.showUsernameRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    scr = preferences.getBoolean("showUsername", false);
                    editor = preferences.edit();
                    editor.putBoolean("showUsername", !scr);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!scr);
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                } else if (i == PlusSettingsActivity.this.hideMobileNumberRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    scr = preferences.getBoolean("hideMobile", false);
                    editor = preferences.edit();
                    editor.putBoolean("hideMobile", !scr);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!scr);
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                } else if (i == PlusSettingsActivity.this.dialogsPicClickRow) {
                    if (PlusSettingsActivity.this.getParentActivity() != null) {
                        builder = new Builder(PlusSettingsActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("ClickOnContactPic", R.string.ClickOnContactPic));
                        builder.setItems(new CharSequence[]{LocaleController.getString("RowGradientDisabled", R.string.RowGradientDisabled), LocaleController.getString("ShowPics", R.string.ShowPics), LocaleController.getString("ShowProfile", R.string.ShowProfile)}, new C10796());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        PlusSettingsActivity.this.showDialog(builder.create());
                    }
                } else if (i == PlusSettingsActivity.this.dialogsGroupPicClickRow) {
                    if (PlusSettingsActivity.this.getParentActivity() != null) {
                        builder = new Builder(PlusSettingsActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("ClickOnGroupPic", R.string.ClickOnGroupPic));
                        builder.setItems(new CharSequence[]{LocaleController.getString("RowGradientDisabled", R.string.RowGradientDisabled), LocaleController.getString("ShowPics", R.string.ShowPics), LocaleController.getString("ShowProfile", R.string.ShowProfile)}, new C10807());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        PlusSettingsActivity.this.showDialog(builder.create());
                    }
                } else if (i == PlusSettingsActivity.this.dialogsTabsHeightRow) {
                    if (PlusSettingsActivity.this.getParentActivity() != null) {
                        builder = new Builder(PlusSettingsActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("TabsHeight", R.string.TabsHeight));
                        numberPicker = new NumberPicker(PlusSettingsActivity.this.getParentActivity());
                        numberPicker.setMinValue(30);
                        numberPicker.setMaxValue(48);
                        numberPicker.setValue(ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getInt("tabsHeight", AndroidUtilities.isTablet() ? 42 : 40));
                        builder.setView(numberPicker);
                        view2 = numberPicker;
                        builder.setNegativeButton(LocaleController.getString("Done", R.string.Done), new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                                editor.putInt("tabsHeight", view2.getValue());
                                editor.apply();
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(12)});
                                if (PlusSettingsActivity.this.listView != null) {
                                    PlusSettingsActivity.this.listView.invalidateViews();
                                }
                            }
                        });
                        PlusSettingsActivity.this.showDialog(builder.create());
                    }
                } else if (i == PlusSettingsActivity.this.dialogsTabsRow) {
                    if (PlusSettingsActivity.this.getParentActivity() != null) {
                        builder = new Builder(PlusSettingsActivity.this.getParentActivity());
                        PlusSettingsActivity.this.createTabsDialog(builder);
                        builder.setNegativeButton(LocaleController.getString("Done", R.string.Done), new C10829());
                        PlusSettingsActivity.this.showDialog(builder.create());
                    }
                } else if (i == PlusSettingsActivity.this.dialogsTabsSort) {
                    if (PlusSettingsActivity.this.getParentActivity() != null) {
                        PlusSettingsActivity.this.getParentActivity().startActivity(new Intent(PlusSettingsActivity.this.getParentActivity(), SortTabsActivity.class));
                    }
                } else if (i == PlusSettingsActivity.this.profileSharedOptionsRow) {
                    if (PlusSettingsActivity.this.getParentActivity() != null) {
                        builder = new Builder(PlusSettingsActivity.this.getParentActivity());
                        PlusSettingsActivity.this.createSharedOptions(builder);
                        builder.setNegativeButton(LocaleController.getString("Done", R.string.Done), new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (PlusSettingsActivity.this.listView != null) {
                                    PlusSettingsActivity.this.listView.invalidateViews();
                                }
                            }
                        });
                        PlusSettingsActivity.this.showDialog(builder.create());
                    }
                } else if (i == PlusSettingsActivity.this.chatShowDirectShareBtn) {
                    if (PlusSettingsActivity.this.getParentActivity() != null) {
                        builder = new Builder(PlusSettingsActivity.this.getParentActivity());
                        PlusSettingsActivity.this.createDialog(builder, PlusSettingsActivity.this.chatShowDirectShareBtn);
                        builder.setNegativeButton(LocaleController.getString("Done", R.string.Done), new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (PlusSettingsActivity.this.listView != null) {
                                    PlusSettingsActivity.this.listView.invalidateViews();
                                }
                            }
                        });
                        PlusSettingsActivity.this.showDialog(builder.create());
                    }
                } else if (i == PlusSettingsActivity.this.notificationInvertMessagesOrderRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    scr = preferences.getBoolean("invertMessagesOrder", false);
                    editor = preferences.edit();
                    editor.putBoolean("invertMessagesOrder", !scr);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!scr);
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                } else if (i == PlusSettingsActivity.this.chatSearchUserOnTwitterRow) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    hide = preferences.getBoolean("searchOnTwitter", true);
                    editor = preferences.edit();
                    editor.putBoolean("searchOnTwitter", !hide);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!hide);
                    }
                } else if (i == PlusSettingsActivity.this.confirmForStickers) {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    hide = preferences.getBoolean("confirmForStickers", true);
                    editor = preferences.edit();
                    editor.putBoolean("confirmForStickers", !hide);
                    editor.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!hide);
                    }
                } else if (i == PlusSettingsActivity.this.savePlusSettingsRow) {
                    View promptsView = LayoutInflater.from(PlusSettingsActivity.this.getParentActivity()).inflate(R.layout.editbox_dialog, null);
                    builder = new Builder(PlusSettingsActivity.this.getParentActivity());
                    builder.setView(promptsView);
                    EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                    userInput.setHint(LocaleController.getString("EnterName", R.string.EnterName));
                    userInput.setHintTextColor(-6842473);
                    SharedPreferences themePrefs = ApplicationLoader.applicationContext.getSharedPreferences(AppUtilities.THEME_PREFS, 0);
                    userInput.getBackground().setColorFilter(themePrefs.getInt("dialogColor", themePrefs.getInt("themeColor", AppUtilities.defColor)), Mode.SRC_IN);
                    AndroidUtilities.clearCursorDrawable(userInput);
                    builder.setTitle(LocaleController.getString("SaveSettings", R.string.SaveSettings));
                    final EditText editText = userInput;
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {

                        /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$12$1 */
                        class C10671 implements Runnable {
                            C10671() {
                            }

                            public void run() {
                                PlusSettingsActivity.this.saving = false;
                                if (PlusSettingsActivity.this.getParentActivity() != null) {
                                    AppUtilities.savePreferencesToSD(PlusSettingsActivity.this.getParentActivity(), "/Telegram/Telegram Documents", "plusconfig.xml", editText.getText().toString() + ".xml", true);
                                }
                            }
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (!PlusSettingsActivity.this.saving) {
                                PlusSettingsActivity.this.saving = true;
                                AndroidUtilities.runOnUIThread(new C10671());
                            }
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    PlusSettingsActivity.this.showDialog(builder.create());
                } else if (i == PlusSettingsActivity.this.restorePlusSettingsRow) {
                    DocumentSelectActivity fragment = new DocumentSelectActivity();
                    fragment.fileFilter = ".xml";
                    fragment.setDelegate(new DocumentSelectActivityDelegate() {
                        public void didSelectFiles(DocumentSelectActivity activity, ArrayList<String> files) {
                            final String xmlFile = (String) files.get(0);
                            File file = new File(xmlFile);
                            Builder builder = new Builder(PlusSettingsActivity.this.getParentActivity());
                            builder.setTitle(LocaleController.getString("RestoreSettings", R.string.RestoreSettings));
                            builder.setMessage(file.getName());
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {

                                /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$13$1$1 */
                                class C10681 implements Runnable {
                                    C10681() {
                                    }

                                    public void run() {
                                        if (AppUtilities.loadPrefFromSD(PlusSettingsActivity.this.getParentActivity(), xmlFile, "plusconfig") == 4) {
                                            AppUtilities.restartApp();
                                        }
                                    }
                                }

                                public void onClick(DialogInterface dialogInterface, int i) {
                                    AndroidUtilities.runOnUIThread(new C10681());
                                }
                            });
                            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                            PlusSettingsActivity.this.showDialog(builder.create());
                        }

                        public void startDocumentSelectActivity() {
                        }
                    });
                    PlusSettingsActivity.this.presentFragment(fragment);
                } else if (i == PlusSettingsActivity.this.resetPlusSettingsRow) {
                    builder = new Builder(PlusSettingsActivity.this.getParentActivity());
                    builder.setMessage(LocaleController.getString("AreYouSure", R.string.AreYouSure));
                    builder.setTitle(LocaleController.getString("ResetSettings", R.string.ResetSettings));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {

                        /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$14$1 */
                        class C10701 implements Runnable {
                            C10701() {
                            }

                            public void run() {
                                PlusSettingsActivity.this.reseting = false;
                                Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                                editor.clear();
                                editor.apply();
                                if (PlusSettingsActivity.this.listView != null) {
                                    PlusSettingsActivity.this.listView.invalidateViews();
                                    PlusSettingsActivity.this.fixLayout();
                                }
                            }
                        }

                        /* renamed from: org.telegram.customization.Activities.PlusSettingsActivity$2$14$2 */
                        class C10712 implements Runnable {
                            C10712() {
                            }

                            public void run() {
                                if (PlusSettingsActivity.this.getParentActivity() != null) {
                                    Toast.makeText(PlusSettingsActivity.this.getParentActivity(), LocaleController.getString("AppWillRestart", R.string.AppWillRestart), 0).show();
                                }
                            }
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (!PlusSettingsActivity.this.reseting) {
                                PlusSettingsActivity.this.reseting = true;
                                AndroidUtilities.runOnUIThread(new C10701());
                                AppUtilities.needRestart = true;
                                AndroidUtilities.runOnUIThread(new C10712());
                            }
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    PlusSettingsActivity.this.showDialog(builder.create());
                }
            }
        });
        frameLayout.addView(this.actionBar);
        return this.fragmentView;
    }

    private Builder createTabsDialog(Builder builder) {
        builder.setTitle(LocaleController.getString("HideShowTabs", R.string.HideShowTabs));
        ArrayList<CharSequence> tabs = new ArrayList();
        ArrayList<Boolean> selected = new ArrayList();
        final ArrayList<DialogTab> dialogTabs = AppPreferences.getAllDialogTabs(getParentActivity());
        Iterator it = dialogTabs.iterator();
        while (it.hasNext()) {
            DialogTab dialogTab = (DialogTab) it.next();
            tabs.add(dialogTab.getShowName());
            selected.add(Boolean.valueOf(!dialogTab.isHidden()));
        }
        CharSequence[] titles = (CharSequence[]) tabs.toArray(new String[0]);
        boolean[] status = new boolean[selected.size()];
        for (int i = 0; i < selected.size(); i++) {
            status[i] = ((Boolean) selected.get(i)).booleanValue();
        }
        builder.setMultiChoiceItems(titles, status, new OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                boolean z;
                Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                DialogTab dialogTab = (DialogTab) dialogTabs.get(which);
                if (isChecked) {
                    z = false;
                } else {
                    z = true;
                }
                dialogTab.setHidden(z);
                AppPreferences.setDialogTabs(PlusSettingsActivity.this.getParentActivity(), new Gson().toJson(dialogTabs));
                boolean ans = false;
                Iterator it = dialogTabs.iterator();
                while (it.hasNext()) {
                    DialogTab dialogTab2 = (DialogTab) it.next();
                    if (ans || !dialogTab2.isHidden()) {
                        ans = true;
                    } else {
                        ans = false;
                    }
                }
                if (ans) {
                    editor.putBoolean("hideTabs", false);
                    editor.apply();
                    if (PlusSettingsActivity.this.listView != null) {
                        PlusSettingsActivity.this.listView.invalidateViews();
                    }
                } else {
                    editor.putBoolean("hideTabs", true);
                    editor.apply();
                    if (PlusSettingsActivity.this.listView != null) {
                        PlusSettingsActivity.this.listView.invalidateViews();
                    }
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(which)});
            }
        });
        return builder;
    }

    private Builder createSharedOptions(Builder builder) {
        boolean[] b;
        boolean z = true;
        builder.setTitle(LocaleController.getString("SharedMedia", R.string.SharedMedia));
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        boolean hideMedia = preferences.getBoolean("hideSharedMedia", false);
        boolean hideFiles = preferences.getBoolean("hideSharedFiles", false);
        boolean hideMusic = preferences.getBoolean("hideSharedMusic", false);
        boolean hideLinks = preferences.getBoolean("hideSharedLinks", false);
        CharSequence[] cs = BuildVars.DEBUG_VERSION ? new CharSequence[]{LocaleController.getString("SharedMediaTitle", R.string.SharedMediaTitle), LocaleController.getString("DocumentsTitle", R.string.DocumentsTitle), LocaleController.getString("AudioTitle", R.string.AudioTitle), LocaleController.getString("LinksTitle", R.string.LinksTitle)} : new CharSequence[]{LocaleController.getString("SharedMediaTitle", R.string.SharedMediaTitle), LocaleController.getString("DocumentsTitle", R.string.DocumentsTitle), LocaleController.getString("AudioTitle", R.string.AudioTitle)};
        if (BuildVars.DEBUG_VERSION) {
            boolean z2;
            b = new boolean[4];
            if (hideMedia) {
                z2 = false;
            } else {
                z2 = true;
            }
            b[0] = z2;
            b[1] = !hideFiles;
            b[2] = !hideMusic;
            if (hideLinks) {
                z = false;
            }
            b[3] = z;
        } else {
            b = new boolean[3];
            b[0] = !hideMedia;
            b[1] = !hideFiles;
            if (hideMusic) {
                z = false;
            }
            b[2] = z;
        }
        builder.setMultiChoiceItems(cs, b, new C10854());
        return builder;
    }

    private Builder createDialog(Builder builder, int i) {
        if (i == this.chatShowDirectShareBtn) {
            builder.setTitle(LocaleController.getString("ShowDirectShareButton", R.string.ShowDirectShareButton));
            SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
            boolean showDSBtnUsers = preferences.getBoolean("showDSBtnUsers", false);
            boolean showDSBtnGroups = preferences.getBoolean("showDSBtnGroups", true);
            boolean showDSBtnSGroups = preferences.getBoolean("showDSBtnSGroups", true);
            boolean showDSBtnChannels = preferences.getBoolean("showDSBtnChannels", true);
            boolean showDSBtnBots = preferences.getBoolean("showDSBtnBots", true);
            builder.setMultiChoiceItems(new CharSequence[]{LocaleController.getString("Users", R.string.Users), LocaleController.getString("Groups", R.string.Groups), LocaleController.getString("SuperGroups", R.string.SuperGroups), LocaleController.getString("Channels", R.string.Channels), LocaleController.getString("Bots", R.string.Bots)}, new boolean[]{showDSBtnUsers, showDSBtnGroups, showDSBtnSGroups, showDSBtnChannels, showDSBtnBots}, new C10865());
        }
        return builder;
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        updateTheme();
        fixLayout();
    }

    private void updateTheme() {
        SharedPreferences themePrefs = ApplicationLoader.applicationContext.getSharedPreferences(AppUtilities.THEME_PREFS, 0);
        int def = themePrefs.getInt("themeColor", AppUtilities.defColor);
        this.actionBar.setTitleColor(themePrefs.getInt("prefHeaderTitleColor", -1));
        Drawable back = getParentActivity().getResources().getDrawable(R.drawable.ic_ab_back);
        back.setColorFilter(themePrefs.getInt("prefHeaderIconsColor", -1), Mode.MULTIPLY);
        this.actionBar.setBackButtonDrawable(back);
        getParentActivity().getResources().getDrawable(R.drawable.ic_ab_other).setColorFilter(themePrefs.getInt("prefHeaderIconsColor", -1), Mode.MULTIPLY);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        fixLayout();
    }

    private void fixLayout() {
        if (this.fragmentView != null) {
            this.fragmentView.getViewTreeObserver().addOnPreDrawListener(new C10876());
        }
    }

    public void didReceivedNotification(int id, Object... args) {
    }
}
