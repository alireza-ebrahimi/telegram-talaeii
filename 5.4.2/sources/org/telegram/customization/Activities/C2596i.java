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
import com.google.p098a.C1768f;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.C2596i.C25892;
import org.telegram.customization.Model.DialogTab;
import org.telegram.customization.p156a.C2594b;
import org.telegram.customization.util.C2872c;
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
import utils.p178a.C3791b;
import utils.view.FontUtil;

/* renamed from: org.telegram.customization.Activities.i */
public class C2596i extends BaseFragment implements NotificationCenterDelegate {
    /* renamed from: A */
    private int f8625A;
    /* renamed from: B */
    private int f8626B;
    /* renamed from: C */
    private int f8627C;
    /* renamed from: D */
    private int f8628D;
    /* renamed from: E */
    private int f8629E;
    /* renamed from: F */
    private int f8630F;
    /* renamed from: G */
    private int f8631G;
    /* renamed from: H */
    private int f8632H;
    /* renamed from: I */
    private int f8633I;
    /* renamed from: J */
    private int f8634J;
    /* renamed from: K */
    private int f8635K;
    /* renamed from: L */
    private int f8636L;
    /* renamed from: M */
    private int f8637M;
    /* renamed from: N */
    private int f8638N;
    /* renamed from: O */
    private int f8639O;
    /* renamed from: P */
    private int f8640P;
    /* renamed from: Q */
    private int f8641Q;
    /* renamed from: R */
    private int f8642R;
    /* renamed from: S */
    private int f8643S;
    /* renamed from: T */
    private int f8644T;
    /* renamed from: U */
    private int f8645U;
    /* renamed from: V */
    private int f8646V;
    /* renamed from: W */
    private int f8647W;
    /* renamed from: X */
    private int f8648X;
    /* renamed from: Y */
    private int f8649Y;
    /* renamed from: Z */
    private int f8650Z;
    /* renamed from: a */
    public boolean f8651a = false;
    private int aa;
    private int ab;
    private int ac;
    private int ad;
    private int ae;
    private int af;
    private int ag;
    private int ah;
    private boolean ai = false;
    private boolean aj = false;
    private int ak;
    /* renamed from: b */
    private ListView f8652b;
    /* renamed from: c */
    private C2595a f8653c;
    /* renamed from: d */
    private int f8654d;
    /* renamed from: e */
    private int f8655e;
    /* renamed from: f */
    private int f8656f;
    /* renamed from: g */
    private int f8657g;
    /* renamed from: h */
    private int f8658h;
    /* renamed from: i */
    private int f8659i;
    /* renamed from: j */
    private int f8660j;
    /* renamed from: k */
    private int f8661k;
    /* renamed from: l */
    private int f8662l;
    /* renamed from: m */
    private int f8663m;
    /* renamed from: n */
    private int f8664n;
    /* renamed from: o */
    private int f8665o;
    /* renamed from: p */
    private int f8666p;
    /* renamed from: q */
    private int f8667q;
    /* renamed from: r */
    private int f8668r;
    /* renamed from: s */
    private int f8669s;
    /* renamed from: t */
    private int f8670t;
    /* renamed from: u */
    private int f8671u;
    /* renamed from: v */
    private int f8672v;
    /* renamed from: w */
    private int f8673w;
    /* renamed from: x */
    private int f8674x;
    /* renamed from: y */
    private int f8675y;
    /* renamed from: z */
    private int f8676z;

    /* renamed from: org.telegram.customization.Activities.i$1 */
    class C25721 extends ActionBarMenuOnItemClick {
        /* renamed from: a */
        final /* synthetic */ C2596i f8588a;

        C25721(C2596i c2596i) {
            this.f8588a = c2596i;
        }

        public void onItemClick(int i) {
            if (i == -1) {
                this.f8588a.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.i$4 */
    class C25914 implements OnMultiChoiceClickListener {
        /* renamed from: a */
        final /* synthetic */ C2596i f8620a;

        C25914(C2596i c2596i) {
            this.f8620a = c2596i;
        }

        public void onClick(DialogInterface dialogInterface, int i, boolean z) {
            boolean z2 = true;
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
            String str;
            if (i == 0) {
                str = "hideSharedMedia";
                if (z) {
                    z2 = false;
                }
                edit.putBoolean(str, z2);
            } else if (i == 1) {
                str = "hideSharedFiles";
                if (z) {
                    z2 = false;
                }
                edit.putBoolean(str, z2);
            } else if (i == 2) {
                str = "hideSharedMusic";
                if (z) {
                    z2 = false;
                }
                edit.putBoolean(str, z2);
            } else if (i == 3) {
                str = "hideSharedLinks";
                if (z) {
                    z2 = false;
                }
                edit.putBoolean(str, z2);
            }
            edit.apply();
        }
    }

    /* renamed from: org.telegram.customization.Activities.i$5 */
    class C25925 implements OnMultiChoiceClickListener {
        /* renamed from: a */
        final /* synthetic */ C2596i f8621a;

        C25925(C2596i c2596i) {
            this.f8621a = c2596i;
        }

        public void onClick(DialogInterface dialogInterface, int i, boolean z) {
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
            if (i == 0) {
                edit.putBoolean("showDSBtnUsers", z);
            } else if (i == 1) {
                edit.putBoolean("showDSBtnGroups", z);
            } else if (i == 2) {
                edit.putBoolean("showDSBtnSGroups", z);
            } else if (i == 3) {
                edit.putBoolean("showDSBtnChannels", z);
            } else if (i == 4) {
                edit.putBoolean("showDSBtnBots", z);
            }
            edit.apply();
        }
    }

    /* renamed from: org.telegram.customization.Activities.i$6 */
    class C25936 implements OnPreDrawListener {
        /* renamed from: a */
        final /* synthetic */ C2596i f8622a;

        C25936(C2596i c2596i) {
            this.f8622a = c2596i;
        }

        public boolean onPreDraw() {
            if (this.f8622a.fragmentView != null) {
                this.f8622a.fragmentView.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            return true;
        }
    }

    /* renamed from: org.telegram.customization.Activities.i$a */
    private class C2595a extends C2594b {
        /* renamed from: a */
        final /* synthetic */ C2596i f8623a;
        /* renamed from: b */
        private Context f8624b;

        public C2595a(C2596i c2596i, Context context) {
            this.f8623a = c2596i;
            this.f8624b = context;
        }

        public boolean areAllItemsEnabled() {
            return false;
        }

        public int getCount() {
            return this.f8623a.f8674x;
        }

        public Object getItem(int i) {
            return null;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public int getItemViewType(int i) {
            return (i == this.f8623a.f8655e || i == this.f8623a.f8654d) ? 0 : (i == this.f8623a.f8656f || i == this.f8623a.f8663m || i == this.f8623a.f8665o || i == this.f8623a.f8660j || i == this.f8623a.f8671u || i == this.f8623a.f8658h || i == this.f8623a.f8632H || i == this.f8623a.f8668r || i == this.f8623a.ad) ? 1 : (i == this.f8623a.f8631G || i == this.f8623a.f8675y || i == this.f8623a.f8636L || i == this.f8623a.f8640P || i == this.f8623a.f8641Q || i == this.f8623a.f8625A || i == this.f8623a.f8643S || i == this.f8623a.f8644T || i == this.f8623a.f8645U || i == this.f8623a.f8676z || i == this.f8623a.f8626B || i == this.f8623a.f8628D || i == this.f8623a.f8673w || i == this.f8623a.f8662l || i == this.f8623a.f8646V || i == this.f8623a.f8647W || i == this.f8623a.f8648X || i == this.f8623a.f8649Y || i == this.f8623a.f8650Z || i == this.f8623a.aa || i == this.f8623a.f8670t || i == this.f8623a.ab || i == this.f8623a.ac || i == this.f8623a.ak) ? 3 : (i == this.f8623a.f8630F || i == this.f8623a.f8637M || i == this.f8623a.f8634J || i == this.f8623a.f8635K) ? 2 : (i == this.f8623a.f8638N || i == this.f8623a.f8642R || i == this.f8623a.f8667q || i == this.f8623a.af || i == this.f8623a.ag || i == this.f8623a.f8627C || i == this.f8623a.f8639O || i == this.f8623a.ah) ? 6 : i == this.f8623a.f8629E ? 7 : (i == this.f8623a.f8657g || i == this.f8623a.f8664n || i == this.f8623a.f8666p || i == this.f8623a.f8661k || i == this.f8623a.f8672v || i == this.f8623a.f8659i || i == this.f8623a.f8633I || i == this.f8623a.f8669s || i == this.f8623a.ae) ? 4 : 2;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            int itemViewType = getItemViewType(i);
            View emptyCell;
            if (itemViewType == 0) {
                emptyCell = view == null ? new EmptyCell(this.f8624b) : view;
                if (i == this.f8623a.f8654d) {
                    ((EmptyCell) emptyCell).setHeight(AndroidUtilities.dp(88.0f));
                    return emptyCell;
                }
                ((EmptyCell) emptyCell).setHeight(AndroidUtilities.dp(16.0f));
                return emptyCell;
            }
            if (itemViewType == 1) {
                if (view == null) {
                    return new ShadowSectionCell(this.f8624b);
                }
            } else if (itemViewType == 2) {
                emptyCell = view == null ? new TextSettingsCell(this.f8624b) : view;
                TextSettingsCell textSettingsCell = (TextSettingsCell) emptyCell;
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                int i2;
                if (i == this.f8623a.f8630F) {
                    i2 = sharedPreferences.getInt("emojiPopupSize", AndroidUtilities.isTablet() ? 65 : 60);
                    textSettingsCell.setTextAndValue(LocaleController.getString("EmojiPopupSize", R.string.EmojiPopupSize), String.format("%d", new Object[]{Integer.valueOf(i2)}), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8637M) {
                    i2 = sharedPreferences.getInt("tabsHeight", AndroidUtilities.isTablet() ? 42 : 40);
                    textSettingsCell.setTextAndValue(LocaleController.getString("TabsHeight", R.string.TabsHeight), String.format("%d", new Object[]{Integer.valueOf(i2)}), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8634J) {
                    i2 = sharedPreferences.getInt("dialogsClickOnPic", 0);
                    r2 = i2 == 0 ? LocaleController.getString("RowGradientDisabled", R.string.RowGradientDisabled) : i2 == 1 ? LocaleController.getString("ShowPics", R.string.ShowPics) : LocaleController.getString("ShowProfile", R.string.ShowProfile);
                    textSettingsCell.setTextAndValue(LocaleController.getString("ClickOnContactPic", R.string.ClickOnContactPic), r2, true);
                    return emptyCell;
                } else if (i != this.f8623a.f8635K) {
                    return emptyCell;
                } else {
                    i2 = sharedPreferences.getInt("dialogsClickOnGroupPic", 0);
                    r2 = i2 == 0 ? LocaleController.getString("RowGradientDisabled", R.string.RowGradientDisabled) : i2 == 1 ? LocaleController.getString("ShowPics", R.string.ShowPics) : LocaleController.getString("ShowProfile", R.string.ShowProfile);
                    textSettingsCell.setTextAndValue(LocaleController.getString("ClickOnGroupPic", R.string.ClickOnGroupPic), r2, true);
                    return emptyCell;
                }
            } else if (itemViewType == 3) {
                emptyCell = view == null ? new TextCheckCell(this.f8624b) : view;
                TextCheckCell textCheckCell = (TextCheckCell) emptyCell;
                r2 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                if (i == this.f8623a.f8631G) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("DisableAudioStop", R.string.DisableAudioStop), r2.getBoolean("disableAudioStop", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8675y) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("DisableMessageClick", R.string.DisableMessageClick), r2.getBoolean("disableMessageClick", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8646V) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("DirectShareToMenu", R.string.DirectShareToMenu), r2.getBoolean("directShareToMenu", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8647W) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("DirectShareShowFavsFirst", R.string.DirectShareShowFavsFirst), r2.getBoolean("directShareFavsFirst", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8648X) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("ShowEditedMark", R.string.ShowEditedMark), r2.getBoolean("showEditedMark", true), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8649Y) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("HideLeftGroup", R.string.HideLeftGroup), r2.getBoolean("hideLeftGroup", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8650Z) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("HideJoinedGroup", R.string.HideJoinedGroup), r2.getBoolean("hideJoinedGroup", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.aa) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("HideBotKeyboard", R.string.HideBotKeyboard), r2.getBoolean("hideBotKeyboard", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8676z) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("ShowAndroidEmoji", R.string.ShowAndroidEmoji), r2.getBoolean("showAndroidEmoji", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8625A) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("showAdsIcon", R.string.showAdsIcon), C3791b.ac(ApplicationLoader.applicationContext), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8626B) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("UseDeviceFont", R.string.UseDeviceFont), r2.getBoolean("useDeviceFont", false), false);
                    return emptyCell;
                } else if (i == this.f8623a.f8628D) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("mutualShow", R.string.mutualShow), r2.getBoolean("mutualShow", false), false);
                    return emptyCell;
                } else if (i == this.f8623a.f8636L) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("HideTabs", R.string.HideTabs), r2.getBoolean("hideTabs", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8640P) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("DisableTabsAnimation", R.string.DisableTabsAnimation), r2.getBoolean("disableTabsAnimation", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8641Q) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("InfiniteSwipe", R.string.InfiniteSwipe), r2.getBoolean("infiniteTabsSwipe", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8643S) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("HideTabsCounters", R.string.HideTabsCounters), r2.getBoolean("hideTabsCounters", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8644T) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("HeaderTabCounterCountChats", R.string.HeaderTabCounterCountChats), r2.getBoolean("tabsCountersCountChats", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8645U) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("HeaderTabCounterCountNotMuted", R.string.HeaderTabCounterCountNotMuted), r2.getBoolean("tabsCountersCountNotMuted", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.ak) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("ShowUnreadDialogsAtTop", R.string.ShowUnreadDialogsAtTop), C3791b.m13972e(this.f8624b), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8673w) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("HideMobile", R.string.HideMobile), r2.getBoolean("hideMobile", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8662l) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("ShowUsernameInMenu", R.string.ShowUsernameInMenu), r2.getBoolean("showUsername", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8670t) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("InvertMessageOrder", R.string.InvertMessageOrder), r2.getBoolean("invertMessagesOrder", false), true);
                    return emptyCell;
                } else if (i == this.f8623a.ab) {
                    textCheckCell.setTextAndCheck(LocaleController.getString("SearchUserOnTwitter", R.string.SearchUserOnTwitter), r2.getBoolean("searchOnTwitter", true), false);
                    return emptyCell;
                } else if (i != this.f8623a.ac) {
                    return emptyCell;
                } else {
                    textCheckCell.setTextAndCheck(LocaleController.getString("confirmForStickers", R.string.confirmForStickers), r2.getBoolean("confirmForStickers", true), false);
                    return emptyCell;
                }
            } else if (itemViewType == 4) {
                emptyCell = view == null ? new HeaderCell(this.f8624b) : view;
                if (i == this.f8623a.f8657g) {
                    ((HeaderCell) emptyCell).setText(LocaleController.getString("General", R.string.General));
                    return emptyCell;
                } else if (i == this.f8623a.f8664n) {
                    ((HeaderCell) emptyCell).setText(LocaleController.getString("MessagesSettings", R.string.MessagesSettings));
                    return emptyCell;
                } else if (i == this.f8623a.f8666p) {
                    ((HeaderCell) emptyCell).setText(LocaleController.getString("ProfileScreen", R.string.ProfileScreen));
                    return emptyCell;
                } else if (i == this.f8623a.f8661k) {
                    ((HeaderCell) emptyCell).setText(LocaleController.getString("NavigationDrawer", R.string.NavigationDrawer));
                    return emptyCell;
                } else if (i == this.f8623a.f8672v) {
                    ((HeaderCell) emptyCell).setText(LocaleController.getString("PrivacySettings", R.string.PrivacySettings));
                    return emptyCell;
                } else if (i == this.f8623a.f8659i) {
                    ((HeaderCell) emptyCell).setText(LocaleController.getString("SharedMedia", R.string.SharedMedia));
                    return emptyCell;
                } else if (i == this.f8623a.f8633I) {
                    ((HeaderCell) emptyCell).setText(LocaleController.getString("DialogsSettings", R.string.DialogsSettings));
                    return emptyCell;
                } else if (i == this.f8623a.f8669s) {
                    ((HeaderCell) emptyCell).setText(LocaleController.getString("Notifications", R.string.Notifications));
                    return emptyCell;
                } else if (i != this.f8623a.ae) {
                    return emptyCell;
                } else {
                    ((HeaderCell) emptyCell).setText(LocaleController.getString("PlusSettings", R.string.PlusSettings));
                    return emptyCell;
                }
            } else if (itemViewType == 6) {
                emptyCell = view == null ? new TextDetailSettingsCell(this.f8624b) : view;
                TextDetailSettingsCell textDetailSettingsCell = (TextDetailSettingsCell) emptyCell;
                if (i == this.f8623a.f8639O) {
                    textDetailSettingsCell.setTextAndValue(LocaleController.getString("sort_tabs", R.string.sort_tabs), LocaleController.getString("sort_tabs_details", R.string.sort_tabs_details), true);
                }
                boolean z;
                boolean z2;
                boolean z3;
                boolean z4;
                boolean z5;
                CharSequence charSequence;
                if (i == this.f8623a.f8638N) {
                    r2 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    z = r2.getBoolean("hideUsers", false);
                    z2 = r2.getBoolean("hideGroups", false);
                    z3 = r2.getBoolean("hideSGroups", false);
                    z4 = r2.getBoolean("hideChannels", false);
                    z5 = r2.getBoolean("hideBots", false);
                    boolean z6 = r2.getBoolean("hideFavs", false);
                    boolean z7 = r2.getBoolean("hideUnread", false);
                    String string = LocaleController.getString("HideShowTabs", R.string.HideShowTabs);
                    charSequence = TtmlNode.ANONYMOUS_REGION_ID;
                    if (!z) {
                        charSequence = charSequence + LocaleController.getString("Users", R.string.Users);
                    }
                    if (!z2) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("Groups", R.string.Groups);
                    }
                    if (!z3) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("SuperGroups", R.string.SuperGroups);
                    }
                    if (!z4) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("Channels", R.string.Channels);
                    }
                    if (!z5) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("Bots", R.string.Bots);
                    }
                    if (!z6) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("Favorites", R.string.Favorites);
                    }
                    if (!z7) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("unreadChats", R.string.unreadChats);
                    }
                    if (charSequence.length() == 0) {
                        charSequence = TtmlNode.ANONYMOUS_REGION_ID;
                    }
                    textDetailSettingsCell.setTextAndValue(string, charSequence, true);
                    return emptyCell;
                } else if (i == this.f8623a.f8642R) {
                    r2 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    z = r2.getBoolean("showDSBtnUsers", false);
                    z2 = r2.getBoolean("showDSBtnGroups", true);
                    z3 = r2.getBoolean("showDSBtnSGroups", true);
                    z4 = r2.getBoolean("showDSBtnChannels", true);
                    z5 = r2.getBoolean("showDSBtnBots", true);
                    String string2 = LocaleController.getString("ShowDirectShareButton", R.string.ShowDirectShareButton);
                    charSequence = TtmlNode.ANONYMOUS_REGION_ID;
                    if (z) {
                        charSequence = charSequence + LocaleController.getString("Users", R.string.Users);
                    }
                    if (z2) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("Groups", R.string.Groups);
                    }
                    if (z3) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("SuperGroups", R.string.SuperGroups);
                    }
                    if (z4) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("Channels", R.string.Channels);
                    }
                    if (z5) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("Bots", R.string.Bots);
                    }
                    if (charSequence.length() == 0) {
                        charSequence = LocaleController.getString("Channels", R.string.UsernameEmpty);
                    }
                    textDetailSettingsCell.setTextAndValue(string2, charSequence, true);
                    return emptyCell;
                } else if (i == this.f8623a.f8667q) {
                    r2 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    z = r2.getBoolean("hideSharedMedia", false);
                    z2 = r2.getBoolean("hideSharedFiles", false);
                    z3 = r2.getBoolean("hideSharedMusic", false);
                    z4 = r2.getBoolean("hideSharedLinks", false);
                    String string3 = LocaleController.getString("SharedMedia", R.string.SharedMedia);
                    charSequence = TtmlNode.ANONYMOUS_REGION_ID;
                    if (!z) {
                        charSequence = charSequence + LocaleController.getString("Users", R.string.SharedMediaTitle);
                    }
                    if (!z2) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("DocumentsTitle", R.string.DocumentsTitle);
                    }
                    if (!z3) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("AudioTitle", R.string.AudioTitle);
                    }
                    if (!z4 && BuildVars.DEBUG_VERSION) {
                        if (charSequence.length() != 0) {
                            r2 = charSequence + ", ";
                        }
                        charSequence = r2 + LocaleController.getString("LinksTitle", R.string.LinksTitle);
                    }
                    if (charSequence.length() == 0) {
                        charSequence = TtmlNode.ANONYMOUS_REGION_ID;
                    }
                    textDetailSettingsCell.setTextAndValue(string3, charSequence, true);
                    return emptyCell;
                } else if (i == this.f8623a.af) {
                    textDetailSettingsCell.setMultilineDetail(true);
                    textDetailSettingsCell.setTextAndValue(LocaleController.getString("SaveSettings", R.string.SaveSettings), LocaleController.getString("SaveSettingsSum", R.string.SaveSettingsSum), true);
                    return emptyCell;
                } else if (i == this.f8623a.ag) {
                    textDetailSettingsCell.setMultilineDetail(true);
                    textDetailSettingsCell.setTextAndValue(LocaleController.getString("RestoreSettings", R.string.RestoreSettings), LocaleController.getString("RestoreSettingsSum", R.string.RestoreSettingsSum), true);
                    return emptyCell;
                } else if (i == this.f8623a.f8627C) {
                    textDetailSettingsCell.setMultilineDetail(true);
                    textDetailSettingsCell.setTextAndValue(LocaleController.getString("selectFont", R.string.select_font), TtmlNode.ANONYMOUS_REGION_ID, true);
                    return emptyCell;
                } else if (i != this.f8623a.ah) {
                    return emptyCell;
                } else {
                    textDetailSettingsCell.setMultilineDetail(true);
                    textDetailSettingsCell.setTextAndValue(LocaleController.getString("ResetSettings", R.string.ResetSettings), LocaleController.getString("ResetSettingsSum", R.string.ResetSettingsSum), false);
                    return emptyCell;
                }
            } else if (itemViewType == 7) {
                emptyCell = view == null ? new TextInfoPrivacyCell(this.f8624b) : view;
                if (i != this.f8623a.f8629E) {
                    return emptyCell;
                }
                ((TextInfoPrivacyCell) emptyCell).setText(LocaleController.getString("KeepOriginalFilenameHelp", R.string.KeepOriginalFilenameHelp));
                emptyCell.setBackgroundResource(R.drawable.greydivider);
                return emptyCell;
            }
            return view;
        }

        public int getViewTypeCount() {
            return 8;
        }

        public boolean hasStableIds() {
            return false;
        }

        public boolean isEmpty() {
            return false;
        }

        public boolean isEnabled(int i) {
            return i == this.f8623a.f8625A || i == this.f8623a.f8676z || i == this.f8623a.f8626B || i == this.f8623a.f8630F || i == this.f8623a.f8628D || i == this.f8623a.f8637M || i == this.f8623a.f8638N || i == this.f8623a.f8642R || i == this.f8623a.f8667q || i == this.f8623a.f8627C || i == this.f8623a.f8639O || i == this.f8623a.f8631G || i == this.f8623a.f8675y || i == this.f8623a.f8646V || i == this.f8623a.f8647W || i == this.f8623a.f8648X || i == this.f8623a.f8649Y || i == this.f8623a.f8650Z || i == this.f8623a.aa || i == this.f8623a.f8636L || i == this.f8623a.f8640P || i == this.f8623a.f8641Q || i == this.f8623a.f8643S || i == this.f8623a.f8644T || i == this.f8623a.f8645U || i == this.f8623a.ab || i == this.f8623a.ac || i == this.f8623a.f8634J || i == this.f8623a.f8635K || i == this.f8623a.f8673w || i == this.f8623a.f8662l || i == this.f8623a.f8670t || i == this.f8623a.af || i == this.f8623a.ag || i == this.f8623a.ah || i == this.f8623a.ak;
        }
    }

    /* renamed from: a */
    private Builder m12386a(Builder builder) {
        builder.setTitle(LocaleController.getString("HideShowTabs", R.string.HideShowTabs));
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        final ArrayList u = C3791b.m14047u(getParentActivity());
        Iterator it = u.iterator();
        while (it.hasNext()) {
            DialogTab dialogTab = (DialogTab) it.next();
            arrayList.add(dialogTab.getShowName());
            arrayList2.add(Boolean.valueOf(!dialogTab.isHidden()));
        }
        CharSequence[] charSequenceArr = (CharSequence[]) arrayList.toArray(new String[0]);
        boolean[] zArr = new boolean[arrayList2.size()];
        for (int i = 0; i < arrayList2.size(); i++) {
            zArr[i] = ((Boolean) arrayList2.get(i)).booleanValue();
        }
        builder.setMultiChoiceItems(charSequenceArr, zArr, new OnMultiChoiceClickListener(this) {
            /* renamed from: b */
            final /* synthetic */ C2596i f8619b;

            public void onClick(DialogInterface dialogInterface, int i, boolean z) {
                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                ((DialogTab) u.get(i)).setHidden(!z);
                C3791b.m13995i(this.f8619b.getParentActivity(), new C1768f().m8395a(u));
                Iterator it = u.iterator();
                boolean z2 = false;
                while (it.hasNext()) {
                    boolean z3 = z2 || !((DialogTab) it.next()).isHidden();
                    z2 = z3;
                }
                if (z2) {
                    edit.putBoolean("hideTabs", false);
                    edit.apply();
                    if (this.f8619b.f8652b != null) {
                        this.f8619b.f8652b.invalidateViews();
                    }
                } else {
                    edit.putBoolean("hideTabs", true);
                    edit.apply();
                    if (this.f8619b.f8652b != null) {
                        this.f8619b.f8652b.invalidateViews();
                    }
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, Integer.valueOf(i));
            }
        });
        return builder;
    }

    /* renamed from: a */
    private Builder m12387a(Builder builder, int i) {
        if (i == this.f8642R) {
            builder.setTitle(LocaleController.getString("ShowDirectShareButton", R.string.ShowDirectShareButton));
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
            boolean z = sharedPreferences.getBoolean("showDSBtnUsers", false);
            boolean z2 = sharedPreferences.getBoolean("showDSBtnGroups", true);
            boolean z3 = sharedPreferences.getBoolean("showDSBtnSGroups", true);
            boolean z4 = sharedPreferences.getBoolean("showDSBtnChannels", true);
            boolean z5 = sharedPreferences.getBoolean("showDSBtnBots", true);
            builder.setMultiChoiceItems(new CharSequence[]{LocaleController.getString("Users", R.string.Users), LocaleController.getString("Groups", R.string.Groups), LocaleController.getString("SuperGroups", R.string.SuperGroups), LocaleController.getString("Channels", R.string.Channels), LocaleController.getString("Bots", R.string.Bots)}, new boolean[]{z, z2, z3, z4, z5}, new C25925(this));
        }
        return builder;
    }

    /* renamed from: a */
    private void m12390a() {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0);
        sharedPreferences.getInt("themeColor", C2872c.f9484b);
        this.actionBar.setTitleColor(sharedPreferences.getInt("prefHeaderTitleColor", -1));
        Drawable drawable = getParentActivity().getResources().getDrawable(R.drawable.ic_ab_back);
        drawable.setColorFilter(sharedPreferences.getInt("prefHeaderIconsColor", -1), Mode.MULTIPLY);
        this.actionBar.setBackButtonDrawable(drawable);
        getParentActivity().getResources().getDrawable(R.drawable.ic_ab_other).setColorFilter(sharedPreferences.getInt("prefHeaderIconsColor", -1), Mode.MULTIPLY);
    }

    /* renamed from: b */
    private Builder m12392b(Builder builder) {
        boolean[] zArr;
        boolean z = true;
        builder.setTitle(LocaleController.getString("SharedMedia", R.string.SharedMedia));
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        boolean z2 = sharedPreferences.getBoolean("hideSharedMedia", false);
        boolean z3 = sharedPreferences.getBoolean("hideSharedFiles", false);
        boolean z4 = sharedPreferences.getBoolean("hideSharedMusic", false);
        boolean z5 = sharedPreferences.getBoolean("hideSharedLinks", false);
        CharSequence[] charSequenceArr = BuildVars.DEBUG_VERSION ? new CharSequence[]{LocaleController.getString("SharedMediaTitle", R.string.SharedMediaTitle), LocaleController.getString("DocumentsTitle", R.string.DocumentsTitle), LocaleController.getString("AudioTitle", R.string.AudioTitle), LocaleController.getString("LinksTitle", R.string.LinksTitle)} : new CharSequence[]{LocaleController.getString("SharedMediaTitle", R.string.SharedMediaTitle), LocaleController.getString("DocumentsTitle", R.string.DocumentsTitle), LocaleController.getString("AudioTitle", R.string.AudioTitle)};
        boolean[] zArr2;
        if (BuildVars.DEBUG_VERSION) {
            zArr2 = new boolean[4];
            zArr2[0] = !z2;
            zArr2[1] = !z3;
            zArr2[2] = !z4;
            if (z5) {
                z = false;
            }
            zArr2[3] = z;
            zArr = zArr2;
        } else {
            zArr2 = new boolean[3];
            zArr2[0] = !z2;
            zArr2[1] = !z3;
            if (z4) {
                z = false;
            }
            zArr2[2] = z;
            zArr = zArr2;
        }
        builder.setMultiChoiceItems(charSequenceArr, zArr, new C25914(this));
        return builder;
    }

    /* renamed from: b */
    private void m12395b() {
        if (this.fragmentView != null) {
            this.fragmentView.getViewTreeObserver().addOnPreDrawListener(new C25936(this));
        }
    }

    public View createView(final Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setTitle(LocaleController.getString("PlusSettings", R.string.PlusSettings));
        this.actionBar.setActionBarMenuOnItemClick(new C25721(this));
        this.f8653c = new C2595a(this, context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.f8652b = new ListView(context);
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0);
        this.f8652b.setDivider(null);
        this.f8652b.setDividerHeight(0);
        this.f8652b.setVerticalScrollBarEnabled(false);
        this.f8652b.setBackgroundColor(Theme.getColor(Theme.key_chat_inBubble));
        this.f8652b.setAdapter(this.f8653c);
        sharedPreferences.getInt("prefBGColor", -1);
        C2872c.m13344a(this.f8652b, sharedPreferences.getInt("prefHeaderColor", sharedPreferences.getInt("themeColor", C2872c.f9484b)));
        frameLayout.addView(this.f8652b, LayoutHelper.createFrame(-1, -1, 51));
        this.f8652b.setAdapter(this.f8653c);
        this.f8652b.setOnItemClickListener(new OnItemClickListener(this) {
            /* renamed from: b */
            final /* synthetic */ C2596i f8617b;

            /* renamed from: org.telegram.customization.Activities.i$2$2 */
            class C25742 implements OnClickListener {
                /* renamed from: a */
                final /* synthetic */ C25892 f8597a;

                C25742(C25892 c25892) {
                    this.f8597a = c25892;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (this.f8597a.f8617b.f8652b != null) {
                        this.f8597a.f8617b.f8652b.invalidateViews();
                    }
                }
            }

            /* renamed from: org.telegram.customization.Activities.i$2$3 */
            class C25753 implements OnClickListener {
                /* renamed from: a */
                final /* synthetic */ C25892 f8598a;

                C25753(C25892 c25892) {
                    this.f8598a = c25892;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (this.f8598a.f8617b.f8652b != null) {
                        this.f8598a.f8617b.f8652b.invalidateViews();
                    }
                }
            }

            /* renamed from: org.telegram.customization.Activities.i$2$5 */
            class C25805 implements DocumentSelectActivityDelegate {
                /* renamed from: a */
                final /* synthetic */ C25892 f8605a;

                C25805(C25892 c25892) {
                    this.f8605a = c25892;
                }

                public void didSelectFiles(DocumentSelectActivity documentSelectActivity, ArrayList<String> arrayList) {
                    final String str = (String) arrayList.get(0);
                    File file = new File(str);
                    Builder builder = new Builder(this.f8605a.f8617b.getParentActivity());
                    builder.setTitle(LocaleController.getString("RestoreSettings", R.string.RestoreSettings));
                    builder.setMessage(file.getName());
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener(this) {
                        /* renamed from: b */
                        final /* synthetic */ C25805 f8604b;

                        /* renamed from: org.telegram.customization.Activities.i$2$5$1$1 */
                        class C25781 implements Runnable {
                            /* renamed from: a */
                            final /* synthetic */ C25791 f8602a;

                            C25781(C25791 c25791) {
                                this.f8602a = c25791;
                            }

                            public void run() {
                                if (C2872c.m13333a(this.f8602a.f8604b.f8605a.f8617b.getParentActivity(), str, "plusconfig") == 4) {
                                    C2872c.m13342a();
                                }
                            }
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            AndroidUtilities.runOnUIThread(new C25781(this));
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    this.f8605a.f8617b.showDialog(builder.create());
                }

                public void startDocumentSelectActivity() {
                }
            }

            /* renamed from: org.telegram.customization.Activities.i$2$6 */
            class C25836 implements OnClickListener {
                /* renamed from: a */
                final /* synthetic */ C25892 f8608a;

                /* renamed from: org.telegram.customization.Activities.i$2$6$1 */
                class C25811 implements Runnable {
                    /* renamed from: a */
                    final /* synthetic */ C25836 f8606a;

                    C25811(C25836 c25836) {
                        this.f8606a = c25836;
                    }

                    public void run() {
                        this.f8606a.f8608a.f8617b.ai = false;
                        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                        edit.clear();
                        edit.apply();
                        if (this.f8606a.f8608a.f8617b.f8652b != null) {
                            this.f8606a.f8608a.f8617b.f8652b.invalidateViews();
                            this.f8606a.f8608a.f8617b.m12395b();
                        }
                    }
                }

                /* renamed from: org.telegram.customization.Activities.i$2$6$2 */
                class C25822 implements Runnable {
                    /* renamed from: a */
                    final /* synthetic */ C25836 f8607a;

                    C25822(C25836 c25836) {
                        this.f8607a = c25836;
                    }

                    public void run() {
                        if (this.f8607a.f8608a.f8617b.getParentActivity() != null) {
                            Toast.makeText(this.f8607a.f8608a.f8617b.getParentActivity(), LocaleController.getString("AppWillRestart", R.string.AppWillRestart), 0).show();
                        }
                    }
                }

                C25836(C25892 c25892) {
                    this.f8608a = c25892;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (!this.f8608a.f8617b.ai) {
                        this.f8608a.f8617b.ai = true;
                        AndroidUtilities.runOnUIThread(new C25811(this));
                        C2872c.f9483a = true;
                        AndroidUtilities.runOnUIThread(new C25822(this));
                    }
                }
            }

            /* renamed from: org.telegram.customization.Activities.i$2$7 */
            class C25847 implements Runnable {
                /* renamed from: a */
                final /* synthetic */ C25892 f8609a;

                C25847(C25892 c25892) {
                    this.f8609a = c25892;
                }

                public void run() {
                    if (this.f8609a.f8617b.getParentActivity() != null) {
                        Toast.makeText(this.f8609a.f8617b.getParentActivity(), LocaleController.getString("AppWillRestart", R.string.AppWillRestart), 0).show();
                    }
                }
            }

            /* renamed from: org.telegram.customization.Activities.i$2$8 */
            class C25858 implements Runnable {
                /* renamed from: a */
                final /* synthetic */ C25892 f8610a;

                C25858(C25892 c25892) {
                    this.f8610a = c25892;
                }

                public void run() {
                    C2872c.m13342a();
                }
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Builder builder;
                final View numberPicker;
                if (i == this.f8617b.f8630F) {
                    if (this.f8617b.getParentActivity() != null) {
                        builder = new Builder(this.f8617b.getParentActivity());
                        builder.setTitle(LocaleController.getString("EmojiPopupSize", R.string.EmojiPopupSize));
                        numberPicker = new NumberPicker(this.f8617b.getParentActivity());
                        numberPicker.setMinValue(60);
                        numberPicker.setMaxValue(100);
                        numberPicker.setValue(ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getInt("emojiPopupSize", AndroidUtilities.isTablet() ? 65 : 60));
                        builder.setView(numberPicker);
                        builder.setNegativeButton(LocaleController.getString("Done", R.string.Done), new OnClickListener(this) {
                            /* renamed from: b */
                            final /* synthetic */ C25892 f8596b;

                            public void onClick(DialogInterface dialogInterface, int i) {
                                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                                edit.putInt("emojiPopupSize", numberPicker.getValue());
                                edit.apply();
                                if (this.f8596b.f8617b.f8652b != null) {
                                    this.f8596b.f8617b.f8652b.invalidateViews();
                                }
                            }
                        });
                        this.f8617b.showDialog(builder.create());
                    }
                } else if (i == this.f8617b.f8676z) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.edit();
                    r2 = r0.getBoolean("showAndroidEmoji", false);
                    r1.putBoolean("showAndroidEmoji", !r2);
                    r1.apply();
                    ApplicationLoader.SHOW_ANDROID_EMOJI = !r2;
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r2);
                    }
                } else if (C3791b.ab(this.f8617b.getParentActivity()) && i == this.f8617b.f8625A) {
                    r1 = C3791b.ac(ApplicationLoader.applicationContext);
                    C3791b.m14038r(ApplicationLoader.applicationContext, !r1);
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8626B) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.edit();
                    r2 = r0.getBoolean("useDeviceFont", false);
                    r1.putBoolean("useDeviceFont", !r2);
                    r1.apply();
                    ApplicationLoader.USE_DEVICE_FONT = !r2;
                    C2872c.f9483a = true;
                    AndroidUtilities.runOnUIThread(new C25847(this));
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r2);
                    }
                    if (C2872c.f9483a) {
                        new Handler().postDelayed(new C25858(this), 1000);
                    }
                } else if (i == this.f8617b.f8627C) {
                    final Dialog dialog = new Dialog(this.f8617b.getParentActivity());
                    dialog.setContentView(R.layout.dialog_select_lang);
                    LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.container);
                    LayoutInflater layoutInflater = (LayoutInflater) ApplicationLoader.applicationContext.getSystemService("layout_inflater");
                    String[] strArr = FontUtil.f10283d;
                    for (int i2 = 0; i2 < strArr.length; i2++) {
                        CharSequence charSequence = strArr[i2];
                        View inflate = layoutInflater.inflate(R.layout.item_font, linearLayout, false);
                        ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_checked);
                        ((TextView) inflate.findViewById(R.id.tv_font_name)).setText(charSequence);
                        if (C3791b.m14054w(ApplicationLoader.applicationContext) == i2) {
                            imageView.setColorFilter(this.f8617b.getParentActivity().getResources().getColor(R.color.green));
                        } else {
                            imageView.setColorFilter(this.f8617b.getParentActivity().getResources().getColor(R.color.gray));
                        }
                        inflate.setOnClickListener(new View.OnClickListener(this) {
                            /* renamed from: c */
                            final /* synthetic */ C25892 f8615c;

                            /* renamed from: org.telegram.customization.Activities.i$2$9$1 */
                            class C25861 implements Runnable {
                                /* renamed from: a */
                                final /* synthetic */ C25889 f8611a;

                                C25861(C25889 c25889) {
                                    this.f8611a = c25889;
                                }

                                public void run() {
                                    if (this.f8611a.f8615c.f8617b.getParentActivity() != null) {
                                        Toast.makeText(this.f8611a.f8615c.f8617b.getParentActivity(), LocaleController.getString("AppWillRestart", R.string.AppWillRestart), 0).show();
                                    }
                                }
                            }

                            /* renamed from: org.telegram.customization.Activities.i$2$9$2 */
                            class C25872 implements Runnable {
                                /* renamed from: a */
                                final /* synthetic */ C25889 f8612a;

                                C25872(C25889 c25889) {
                                    this.f8612a = c25889;
                                }

                                public void run() {
                                    C2872c.m13342a();
                                }
                            }

                            public void onClick(View view) {
                                C3791b.m13952c(ApplicationLoader.applicationContext, i2);
                                dialog.dismiss();
                                C2872c.f9483a = true;
                                AndroidUtilities.runOnUIThread(new C25861(this));
                                if (C2872c.f9483a) {
                                    new Handler().postDelayed(new C25872(this), 1000);
                                }
                            }
                        });
                        linearLayout.addView(inflate);
                    }
                    dialog.show();
                } else if (i == this.f8617b.f8628D) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.edit();
                    r2 = r0.getBoolean("mutualShow", false);
                    r1.putBoolean("mutualShow", !r2);
                    r1.apply();
                    if (view instanceof TextCheckCell) {
                        if (!r2) {
                            new Builder(context).setTitle(LocaleController.getString("MyAppName", R.string.MyAppName)).setMessage("با استفاده از این امکان میتوانید کسانی که شماره موبایل شما را دارند را در قسمت مخاطبین ببینید").setPositiveButton(LocaleController.getString("ok", R.string.ok), new OnClickListener(this) {
                                /* renamed from: a */
                                final /* synthetic */ C25892 f8589a;

                                {
                                    this.f8589a = r1;
                                }

                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).show();
                        }
                        ((TextCheckCell) view).setChecked(!r2);
                    }
                } else if (i == this.f8617b.f8631G) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("disableAudioStop", false);
                    r2 = r0.edit();
                    r2.putBoolean("disableAudioStop", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8675y) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("disableMessageClick", false);
                    r2 = r0.edit();
                    r2.putBoolean("disableMessageClick", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8646V) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("directShareToMenu", false);
                    r2 = r0.edit();
                    r2.putBoolean("directShareToMenu", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8647W) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("directShareFavsFirst", false);
                    r2 = r0.edit();
                    r2.putBoolean("directShareFavsFirst", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8648X) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("showEditedMark", true);
                    r2 = r0.edit();
                    r2.putBoolean("showEditedMark", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8649Y) {
                    r1 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r2 = r1.getBoolean("hideLeftGroup", false);
                    MessagesController.getInstance().hideLeftGroup = !r2;
                    r1 = r1.edit();
                    r1.putBoolean("hideLeftGroup", !r2);
                    r1.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r2);
                    }
                } else if (i == this.f8617b.f8650Z) {
                    r1 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r2 = r1.getBoolean("hideJoinedGroup", false);
                    MessagesController.getInstance().hideJoinedGroup = !r2;
                    r1 = r1.edit();
                    r1.putBoolean("hideJoinedGroup", !r2);
                    r1.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r2);
                    }
                } else if (i == this.f8617b.aa) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("hideBotKeyboard", false);
                    r2 = r0.edit();
                    r2.putBoolean("hideBotKeyboard", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8636L) {
                    r1 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r2 = r1.getBoolean("hideTabs", false);
                    Editor edit = r1.edit();
                    edit.putBoolean("hideTabs", !r2);
                    edit.apply();
                    boolean z = r1.getBoolean("hideUsers", false);
                    boolean z2 = r1.getBoolean("hideGroups", false);
                    boolean z3 = r1.getBoolean("hideSGroups", false);
                    boolean z4 = r1.getBoolean("hideChannels", false);
                    boolean z5 = r1.getBoolean("hideBots", false);
                    boolean z6 = r1.getBoolean("hideFavs", false);
                    r1 = r1.getBoolean("hideUnread", false);
                    if (z && z2 && z3 && z4 && z5 && z6 && r1 && this.f8617b.f8652b != null) {
                        this.f8617b.f8652b.invalidateViews();
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, Integer.valueOf(10));
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r2);
                    }
                } else if (i == this.f8617b.f8640P) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("disableTabsAnimation", false);
                    r2 = r0.edit();
                    r2.putBoolean("disableTabsAnimation", !r1);
                    r2.apply();
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, Integer.valueOf(11));
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8641Q) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("infiniteTabsSwipe", false);
                    r2 = r0.edit();
                    r2.putBoolean("infiniteTabsSwipe", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8643S) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("hideTabsCounters", false);
                    r2 = r0.edit();
                    r2.putBoolean("hideTabsCounters", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8644T) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("tabsCountersCountChats", false);
                    r2 = r0.edit();
                    r2.putBoolean("tabsCountersCountChats", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8645U) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("tabsCountersCountNotMuted", false);
                    r2 = r0.edit();
                    r2.putBoolean("tabsCountersCountNotMuted", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.ak) {
                    r1 = C3791b.m13972e(context);
                    C3791b.m13948b(context, !r1);
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.f8662l) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("showUsername", false);
                    r2 = r0.edit();
                    r2.putBoolean("showUsername", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                } else if (i == this.f8617b.f8673w) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("hideMobile", false);
                    r2 = r0.edit();
                    r2.putBoolean("hideMobile", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                } else if (i == this.f8617b.f8634J) {
                    if (this.f8617b.getParentActivity() != null) {
                        r0 = new Builder(this.f8617b.getParentActivity());
                        r0.setTitle(LocaleController.getString("ClickOnContactPic", R.string.ClickOnContactPic));
                        r0.setItems(new CharSequence[]{LocaleController.getString("RowGradientDisabled", R.string.RowGradientDisabled), LocaleController.getString("ShowPics", R.string.ShowPics), LocaleController.getString("ShowProfile", R.string.ShowProfile)}, new OnClickListener(this) {
                            /* renamed from: a */
                            final /* synthetic */ C25892 f8590a;

                            {
                                this.f8590a = r1;
                            }

                            public void onClick(DialogInterface dialogInterface, int i) {
                                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                                edit.putInt("dialogsClickOnPic", i);
                                edit.apply();
                                if (this.f8590a.f8617b.f8652b != null) {
                                    this.f8590a.f8617b.f8652b.invalidateViews();
                                }
                            }
                        });
                        r0.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        this.f8617b.showDialog(r0.create());
                    }
                } else if (i == this.f8617b.f8635K) {
                    if (this.f8617b.getParentActivity() != null) {
                        r0 = new Builder(this.f8617b.getParentActivity());
                        r0.setTitle(LocaleController.getString("ClickOnGroupPic", R.string.ClickOnGroupPic));
                        r0.setItems(new CharSequence[]{LocaleController.getString("RowGradientDisabled", R.string.RowGradientDisabled), LocaleController.getString("ShowPics", R.string.ShowPics), LocaleController.getString("ShowProfile", R.string.ShowProfile)}, new OnClickListener(this) {
                            /* renamed from: a */
                            final /* synthetic */ C25892 f8591a;

                            {
                                this.f8591a = r1;
                            }

                            public void onClick(DialogInterface dialogInterface, int i) {
                                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                                edit.putInt("dialogsClickOnGroupPic", i);
                                edit.apply();
                                if (this.f8591a.f8617b.f8652b != null) {
                                    this.f8591a.f8617b.f8652b.invalidateViews();
                                }
                            }
                        });
                        r0.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        this.f8617b.showDialog(r0.create());
                    }
                } else if (i == this.f8617b.f8637M) {
                    if (this.f8617b.getParentActivity() != null) {
                        builder = new Builder(this.f8617b.getParentActivity());
                        builder.setTitle(LocaleController.getString("TabsHeight", R.string.TabsHeight));
                        numberPicker = new NumberPicker(this.f8617b.getParentActivity());
                        numberPicker.setMinValue(30);
                        numberPicker.setMaxValue(48);
                        numberPicker.setValue(ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getInt("tabsHeight", AndroidUtilities.isTablet() ? 42 : 40));
                        builder.setView(numberPicker);
                        builder.setNegativeButton(LocaleController.getString("Done", R.string.Done), new OnClickListener(this) {
                            /* renamed from: b */
                            final /* synthetic */ C25892 f8593b;

                            public void onClick(DialogInterface dialogInterface, int i) {
                                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
                                edit.putInt("tabsHeight", numberPicker.getValue());
                                edit.apply();
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, Integer.valueOf(12));
                                if (this.f8593b.f8617b.f8652b != null) {
                                    this.f8593b.f8617b.f8652b.invalidateViews();
                                }
                            }
                        });
                        this.f8617b.showDialog(builder.create());
                    }
                } else if (i == this.f8617b.f8638N) {
                    if (this.f8617b.getParentActivity() != null) {
                        r0 = new Builder(this.f8617b.getParentActivity());
                        this.f8617b.m12386a(r0);
                        r0.setNegativeButton(LocaleController.getString("Done", R.string.Done), new OnClickListener(this) {
                            /* renamed from: a */
                            final /* synthetic */ C25892 f8594a;

                            {
                                this.f8594a = r1;
                            }

                            public void onClick(DialogInterface dialogInterface, int i) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, Integer.valueOf(13));
                                if (this.f8594a.f8617b.f8652b != null) {
                                    this.f8594a.f8617b.f8652b.invalidateViews();
                                }
                            }
                        });
                        this.f8617b.showDialog(r0.create());
                    }
                } else if (i == this.f8617b.f8639O) {
                    if (this.f8617b.getParentActivity() != null) {
                        this.f8617b.getParentActivity().startActivity(new Intent(this.f8617b.getParentActivity(), SortTabsActivity.class));
                    }
                } else if (i == this.f8617b.f8667q) {
                    if (this.f8617b.getParentActivity() != null) {
                        r0 = new Builder(this.f8617b.getParentActivity());
                        this.f8617b.m12392b(r0);
                        r0.setNegativeButton(LocaleController.getString("Done", R.string.Done), new C25742(this));
                        this.f8617b.showDialog(r0.create());
                    }
                } else if (i == this.f8617b.f8642R) {
                    if (this.f8617b.getParentActivity() != null) {
                        r0 = new Builder(this.f8617b.getParentActivity());
                        this.f8617b.m12387a(r0, this.f8617b.f8642R);
                        r0.setNegativeButton(LocaleController.getString("Done", R.string.Done), new C25753(this));
                        this.f8617b.showDialog(r0.create());
                    }
                } else if (i == this.f8617b.f8670t) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("invertMessagesOrder", false);
                    r2 = r0.edit();
                    r2.putBoolean("invertMessagesOrder", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                } else if (i == this.f8617b.ab) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("searchOnTwitter", true);
                    r2 = r0.edit();
                    r2.putBoolean("searchOnTwitter", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.ac) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                    r1 = r0.getBoolean("confirmForStickers", true);
                    r2 = r0.edit();
                    r2.putBoolean("confirmForStickers", !r1);
                    r2.apply();
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(!r1);
                    }
                } else if (i == this.f8617b.af) {
                    View inflate2 = LayoutInflater.from(this.f8617b.getParentActivity()).inflate(R.layout.editbox_dialog, null);
                    builder = new Builder(this.f8617b.getParentActivity());
                    builder.setView(inflate2);
                    final EditText editText = (EditText) inflate2.findViewById(R.id.editTextDialogUserInput);
                    editText.setHint(LocaleController.getString("EnterName", R.string.EnterName));
                    editText.setHintTextColor(-6842473);
                    SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0);
                    editText.getBackground().setColorFilter(sharedPreferences.getInt("dialogColor", sharedPreferences.getInt("themeColor", C2872c.f9484b)), Mode.SRC_IN);
                    AndroidUtilities.clearCursorDrawable(editText);
                    builder.setTitle(LocaleController.getString("SaveSettings", R.string.SaveSettings));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener(this) {
                        /* renamed from: b */
                        final /* synthetic */ C25892 f8601b;

                        /* renamed from: org.telegram.customization.Activities.i$2$4$1 */
                        class C25761 implements Runnable {
                            /* renamed from: a */
                            final /* synthetic */ C25774 f8599a;

                            C25761(C25774 c25774) {
                                this.f8599a = c25774;
                            }

                            public void run() {
                                this.f8599a.f8601b.f8617b.aj = false;
                                if (this.f8599a.f8601b.f8617b.getParentActivity() != null) {
                                    C2872c.m13343a(this.f8599a.f8601b.f8617b.getParentActivity(), "/Telegram/Telegram Documents", "plusconfig.xml", editText.getText().toString() + ".xml", true);
                                }
                            }
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (!this.f8601b.f8617b.aj) {
                                this.f8601b.f8617b.aj = true;
                                AndroidUtilities.runOnUIThread(new C25761(this));
                            }
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    this.f8617b.showDialog(builder.create());
                } else if (i == this.f8617b.ag) {
                    BaseFragment documentSelectActivity = new DocumentSelectActivity();
                    documentSelectActivity.fileFilter = ".xml";
                    documentSelectActivity.setDelegate(new C25805(this));
                    this.f8617b.presentFragment(documentSelectActivity);
                } else if (i == this.f8617b.ah) {
                    r0 = new Builder(this.f8617b.getParentActivity());
                    r0.setMessage(LocaleController.getString("AreYouSure", R.string.AreYouSure));
                    r0.setTitle(LocaleController.getString("ResetSettings", R.string.ResetSettings));
                    r0.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C25836(this));
                    r0.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    this.f8617b.showDialog(r0.create());
                }
            }
        });
        frameLayout.addView(this.actionBar);
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        m12395b();
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.refreshTabs);
        if (BuildConfig.FLAVOR.contentEquals("gram")) {
            this.f8651a = true;
        }
        this.f8674x = 0;
        this.f8654d = -1;
        this.f8655e = -1;
        int i = this.f8674x;
        this.f8674x = i + 1;
        this.f8656f = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8657g = i;
        if (VERSION.SDK_INT >= 19) {
            i = this.f8674x;
            this.f8674x = i + 1;
            this.f8676z = i;
        } else {
            this.f8676z = -1;
        }
        if (C3791b.ab(ApplicationLoader.applicationContext)) {
            i = this.f8674x;
            this.f8674x = i + 1;
            this.f8625A = i;
        }
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8626B = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8628D = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8627C = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8663m = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8664n = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8630F = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8631G = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8675y = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8642R = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8646V = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8647W = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8648X = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8649Y = i;
        this.f8650Z = -1;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.aa = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.ab = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.ac = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8632H = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8633I = i;
        if (!this.f8651a) {
            i = this.f8674x;
            this.f8674x = i + 1;
            this.f8636L = i;
        }
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8638N = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8639O = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8637M = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8640P = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8641Q = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8643S = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8645U = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8644T = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8634J = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8635K = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.ak = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8665o = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8666p = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8667q = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8660j = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8661k = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8662l = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8668r = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8669s = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8670t = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8671u = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8672v = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8673w = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8658h = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8659i = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.f8629E = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.ad = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.ae = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.af = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.ag = i;
        i = this.f8674x;
        this.f8674x = i + 1;
        this.ah = i;
        MessagesController.getInstance().loadFullUser(UserConfig.getCurrentUser(), this.classGuid, true);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.refreshTabs);
    }

    public void onResume() {
        super.onResume();
        if (this.f8653c != null) {
            this.f8653c.notifyDataSetChanged();
        }
        m12390a();
        m12395b();
    }
}
