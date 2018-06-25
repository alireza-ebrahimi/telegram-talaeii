package org.telegram.customization.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.customization.p156a.C2652h;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.ContactsController.Contact;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SecretChatHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Adapters.SearchAdapter;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.LetterSectionCell;
import org.telegram.ui.Cells.ProfileSearchCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.ChannelCreateActivity;
import org.telegram.ui.ChannelIntroActivity;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.GroupCreateActivity;
import org.telegram.ui.GroupInviteActivity;
import org.telegram.ui.NewContactActivity;

/* renamed from: org.telegram.customization.Activities.e */
public class C2553e extends BaseFragment implements NotificationCenterDelegate {
    /* renamed from: a */
    private C2652h f8512a;
    /* renamed from: b */
    private EmptyTextProgressView f8513b;
    /* renamed from: c */
    private RecyclerListView f8514c;
    /* renamed from: d */
    private SearchAdapter f8515d;
    /* renamed from: e */
    private boolean f8516e;
    /* renamed from: f */
    private boolean f8517f;
    /* renamed from: g */
    private boolean f8518g;
    /* renamed from: h */
    private boolean f8519h;
    /* renamed from: i */
    private boolean f8520i;
    /* renamed from: j */
    private boolean f8521j;
    /* renamed from: k */
    private boolean f8522k;
    /* renamed from: l */
    private boolean f8523l;
    /* renamed from: m */
    private boolean f8524m = true;
    /* renamed from: n */
    private boolean f8525n = true;
    /* renamed from: o */
    private boolean f8526o;
    /* renamed from: p */
    private int f8527p;
    /* renamed from: q */
    private String f8528q = null;
    /* renamed from: r */
    private HashMap<Integer, User> f8529r;
    /* renamed from: s */
    private boolean f8530s = true;
    /* renamed from: t */
    private C2552a f8531t;
    /* renamed from: u */
    private AlertDialog f8532u;
    /* renamed from: v */
    private boolean f8533v = true;

    /* renamed from: org.telegram.customization.Activities.e$1 */
    class C25431 extends ActionBarMenuOnItemClick {
        /* renamed from: a */
        final /* synthetic */ C2553e f8499a;

        C25431(C2553e c2553e) {
            this.f8499a = c2553e;
        }

        public void onItemClick(int i) {
            if (i == -1) {
                this.f8499a.finishFragment();
            } else if (i == 1) {
                this.f8499a.presentFragment(new NewContactActivity());
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.e$2 */
    class C25442 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2553e f8500a;

        C25442(C2553e c2553e) {
            this.f8500a = c2553e;
        }

        public void onClick(View view) {
            ContactsController.getInstance().loadContacts(false, 0);
        }
    }

    /* renamed from: org.telegram.customization.Activities.e$3 */
    class C25453 extends ActionBarMenuItemSearchListener {
        /* renamed from: a */
        final /* synthetic */ C2553e f8501a;

        C25453(C2553e c2553e) {
            this.f8501a = c2553e;
        }

        public void onSearchCollapse() {
            this.f8501a.f8515d.searchDialogs(null);
            this.f8501a.f8517f = false;
            this.f8501a.f8516e = false;
            this.f8501a.f8514c.setAdapter(this.f8501a.f8512a);
            this.f8501a.f8512a.notifyDataSetChanged();
            this.f8501a.f8514c.setFastScrollVisible(true);
            this.f8501a.f8514c.setVerticalScrollBarEnabled(false);
            this.f8501a.f8513b.setText(LocaleController.getString("NoContacts", R.string.NoContacts));
        }

        public void onSearchExpand() {
            this.f8501a.f8517f = true;
        }

        public void onTextChanged(EditText editText) {
            if (this.f8501a.f8515d != null) {
                String obj = editText.getText().toString();
                if (obj.length() != 0) {
                    this.f8501a.f8516e = true;
                    if (this.f8501a.f8514c != null) {
                        this.f8501a.f8514c.setAdapter(this.f8501a.f8515d);
                        this.f8501a.f8515d.notifyDataSetChanged();
                        this.f8501a.f8514c.setFastScrollVisible(false);
                        this.f8501a.f8514c.setVerticalScrollBarEnabled(true);
                    }
                    if (this.f8501a.f8513b != null) {
                        this.f8501a.f8513b.setText(LocaleController.getString("NoResult", R.string.NoResult));
                    }
                }
                this.f8501a.f8515d.searchDialogs(obj);
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.e$4 */
    class C25474 implements OnItemClickListener {
        /* renamed from: a */
        final /* synthetic */ C2553e f8504a;

        C25474(C2553e c2553e) {
            this.f8504a = c2553e;
        }

        public void onItemClick(View view, int i) {
            User user;
            Bundle bundle;
            if (this.f8504a.f8517f && this.f8504a.f8516e) {
                user = (User) this.f8504a.f8515d.getItem(i);
                if (user != null) {
                    if (this.f8504a.f8515d.isGlobalSearch(i)) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(user);
                        MessagesController.getInstance().putUsers(arrayList, false);
                        MessagesStorage.getInstance().putUsersAndChats(arrayList, null, false, true);
                    }
                    if (this.f8504a.f8521j) {
                        if (this.f8504a.f8529r == null || !this.f8504a.f8529r.containsKey(Integer.valueOf(user.id))) {
                            this.f8504a.m12331a(user, true, null);
                            return;
                        }
                        return;
                    } else if (!this.f8504a.f8522k) {
                        bundle = new Bundle();
                        bundle.putInt("user_id", user.id);
                        if (MessagesController.checkCanOpenChat(bundle, this.f8504a)) {
                            this.f8504a.presentFragment(new ChatActivity(bundle), true);
                            return;
                        }
                        return;
                    } else if (user.id != UserConfig.getClientUserId()) {
                        this.f8504a.f8523l = true;
                        SecretChatHelper.getInstance().startSecretChat(this.f8504a.getParentActivity(), user);
                        return;
                    } else {
                        return;
                    }
                }
                return;
            }
            int sectionForPosition = this.f8504a.f8512a.getSectionForPosition(i);
            int positionInSectionForPosition = this.f8504a.f8512a.getPositionInSectionForPosition(i);
            if (positionInSectionForPosition >= 0 && sectionForPosition >= 0) {
                if ((this.f8504a.f8518g && this.f8504a.f8527p == 0) || sectionForPosition != 0) {
                    Object item = this.f8504a.f8512a.getItem(sectionForPosition, positionInSectionForPosition);
                    if (item instanceof User) {
                        user = (User) item;
                        if (this.f8504a.f8521j) {
                            if (this.f8504a.f8529r == null || !this.f8504a.f8529r.containsKey(Integer.valueOf(user.id))) {
                                this.f8504a.m12331a(user, true, null);
                            }
                        } else if (this.f8504a.f8522k) {
                            this.f8504a.f8523l = true;
                            SecretChatHelper.getInstance().startSecretChat(this.f8504a.getParentActivity(), user);
                        } else {
                            bundle = new Bundle();
                            bundle.putInt("user_id", user.id);
                            if (MessagesController.checkCanOpenChat(bundle, this.f8504a)) {
                                this.f8504a.presentFragment(new ChatActivity(bundle), true);
                            }
                        }
                    } else if (item instanceof Contact) {
                        Contact contact = (Contact) item;
                        final String str = !contact.phones.isEmpty() ? (String) contact.phones.get(0) : null;
                        if (str != null && this.f8504a.getParentActivity() != null) {
                            Builder builder = new Builder(this.f8504a.getParentActivity());
                            builder.setMessage(LocaleController.getString("InviteUser", R.string.InviteUser));
                            builder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener(this) {
                                /* renamed from: b */
                                final /* synthetic */ C25474 f8503b;

                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        Intent intent = new Intent("android.intent.action.VIEW", Uri.fromParts("sms", str, null));
                                        intent.putExtra("sms_body", LocaleController.getString("InviteText", R.string.InviteText));
                                        this.f8503b.f8504a.getParentActivity().startActivityForResult(intent, ChatActivity.startAllServices);
                                    } catch (Throwable e) {
                                        FileLog.m13728e(e);
                                    }
                                }
                            });
                            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                            this.f8504a.showDialog(builder.create());
                        }
                    }
                } else if (this.f8504a.f8519h) {
                    if (positionInSectionForPosition != 0) {
                    }
                } else if (this.f8504a.f8527p != 0) {
                    if (positionInSectionForPosition == 0) {
                        this.f8504a.presentFragment(new GroupInviteActivity(this.f8504a.f8527p));
                    }
                } else if (positionInSectionForPosition == 0) {
                    if (MessagesController.isFeatureEnabled("chat_create", this.f8504a)) {
                        this.f8504a.presentFragment(new GroupCreateActivity(), false);
                    }
                } else if (positionInSectionForPosition == 1) {
                    r0 = new Bundle();
                    r0.putBoolean("onlyUsers", true);
                    r0.putBoolean("destroyAfterSelect", true);
                    r0.putBoolean("createSecretChat", true);
                    r0.putBoolean("allowBots", false);
                    this.f8504a.presentFragment(new C2553e(r0), false);
                } else if (positionInSectionForPosition == 2 && MessagesController.isFeatureEnabled("broadcast_create", this.f8504a)) {
                    SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                    if (BuildVars.DEBUG_VERSION || !sharedPreferences.getBoolean("channel_intro", false)) {
                        this.f8504a.presentFragment(new ChannelIntroActivity());
                        sharedPreferences.edit().putBoolean("channel_intro", true).commit();
                        return;
                    }
                    r0 = new Bundle();
                    r0.putInt("step", 0);
                    this.f8504a.presentFragment(new ChannelCreateActivity(r0));
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.e$5 */
    class C25485 extends OnScrollListener {
        /* renamed from: a */
        final /* synthetic */ C2553e f8505a;

        C25485(C2553e c2553e) {
            this.f8505a = c2553e;
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 1 && this.f8505a.f8517f && this.f8505a.f8516e) {
                AndroidUtilities.hideKeyboard(this.f8505a.getParentActivity().getCurrentFocus());
            }
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            super.onScrolled(recyclerView, i, i2);
        }
    }

    /* renamed from: org.telegram.customization.Activities.e$8 */
    class C25518 implements ThemeDescriptionDelegate {
        /* renamed from: a */
        final /* synthetic */ C2553e f8511a;

        C25518(C2553e c2553e) {
            this.f8511a = c2553e;
        }

        public void didSetColor(int i) {
            int childCount = this.f8511a.f8514c.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = this.f8511a.f8514c.getChildAt(i2);
                if (childAt instanceof UserCell) {
                    ((UserCell) childAt).update(0);
                } else if (childAt instanceof ProfileSearchCell) {
                    ((ProfileSearchCell) childAt).update(0);
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.e$a */
    public interface C2552a {
        /* renamed from: a */
        void m12326a(User user, String str);
    }

    public C2553e(Bundle bundle) {
        super(bundle);
    }

    @TargetApi(23)
    /* renamed from: a */
    private void m12328a() {
        Activity parentActivity = getParentActivity();
        if (parentActivity != null) {
            ArrayList arrayList = new ArrayList();
            if (parentActivity.checkSelfPermission("android.permission.READ_CONTACTS") != 0) {
                arrayList.add("android.permission.READ_CONTACTS");
                arrayList.add("android.permission.WRITE_CONTACTS");
                arrayList.add("android.permission.GET_ACCOUNTS");
            }
            parentActivity.requestPermissions((String[]) arrayList.toArray(new String[arrayList.size()]), 1);
        }
    }

    /* renamed from: a */
    private void m12329a(int i) {
        if (this.f8514c != null) {
            int childCount = this.f8514c.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = this.f8514c.getChildAt(i2);
                if (childAt instanceof UserCell) {
                    ((UserCell) childAt).update(i);
                }
            }
        }
    }

    /* renamed from: a */
    private void m12331a(final User user, boolean z, String str) {
        if (!z || this.f8528q == null) {
            if (this.f8531t != null) {
                this.f8531t.m12326a(user, str);
                this.f8531t = null;
            }
            finishFragment();
        } else if (getParentActivity() != null) {
            if (user.bot && user.bot_nochats && !this.f8526o) {
                try {
                    Toast.makeText(getParentActivity(), LocaleController.getString("BotCantJoinGroups", R.string.BotCantJoinGroups), 0).show();
                    return;
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                    return;
                }
            }
            EditText editText;
            Builder builder = new Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
            CharSequence formatStringSimple = LocaleController.formatStringSimple(this.f8528q, UserObject.getUserName(user));
            if (user.bot || !this.f8525n) {
                editText = null;
            } else {
                String format = String.format("%s\n\n%s", new Object[]{formatStringSimple, LocaleController.getString("AddToTheGroupForwardCount", R.string.AddToTheGroupForwardCount)});
                final View editText2 = new EditText(getParentActivity());
                editText2.setTextSize(18.0f);
                editText2.setText("50");
                editText2.setGravity(17);
                editText2.setInputType(2);
                editText2.setImeOptions(6);
                editText2.setBackgroundDrawable(Theme.createEditTextDrawable(getParentActivity(), true));
                editText2.addTextChangedListener(new TextWatcher(this) {
                    /* renamed from: b */
                    final /* synthetic */ C2553e f8507b;

                    public void afterTextChanged(Editable editable) {
                        try {
                            String obj = editable.toString();
                            if (obj.length() != 0) {
                                int intValue = Utilities.parseInt(obj).intValue();
                                if (intValue < 0) {
                                    editText2.setText("0");
                                    editText2.setSelection(editText2.length());
                                } else if (intValue > 300) {
                                    editText2.setText("300");
                                    editText2.setSelection(editText2.length());
                                } else if (!obj.equals(TtmlNode.ANONYMOUS_REGION_ID + intValue)) {
                                    editText2.setText(TtmlNode.ANONYMOUS_REGION_ID + intValue);
                                    editText2.setSelection(editText2.length());
                                }
                            }
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }

                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }
                });
                builder.setView(editText2);
                View view = editText2;
                formatStringSimple = format;
                editText = view;
            }
            builder.setMessage(formatStringSimple);
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener(this) {
                /* renamed from: c */
                final /* synthetic */ C2553e f8510c;

                public void onClick(DialogInterface dialogInterface, int i) {
                    this.f8510c.m12331a(user, false, editText != null ? editText.getText().toString() : "0");
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            showDialog(builder.create());
            if (editText != null) {
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) editText.getLayoutParams();
                if (marginLayoutParams != null) {
                    if (marginLayoutParams instanceof LayoutParams) {
                        ((LayoutParams) marginLayoutParams).gravity = 1;
                    }
                    int dp = AndroidUtilities.dp(10.0f);
                    marginLayoutParams.leftMargin = dp;
                    marginLayoutParams.rightMargin = dp;
                    editText.setLayoutParams(marginLayoutParams);
                }
                editText.setSelection(editText.getText().length());
            }
        }
    }

    public View createView(Context context) {
        this.f8517f = false;
        this.f8516e = false;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("onlineContacts", R.string.Online_Contacts));
        this.actionBar.setActionBarMenuOnItemClick(new C25431(this));
        ActionBarMenu createMenu = this.actionBar.createMenu();
        createMenu.addItem(100, R.drawable.ic_sync).setOnClickListener(new C25442(this));
        createMenu.addItem(0, R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C25453(this)).getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        if (!(this.f8522k || this.f8521j)) {
            createMenu.addItem(1, R.drawable.add);
        }
        this.f8515d = new SearchAdapter(context, this.f8529r, this.f8530s, false, false, this.f8524m, 0);
        this.f8512a = new C2652h(context, this.f8518g ? 1 : 0, this.f8519h, this.f8529r, this.f8527p != 0);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.f8513b = new EmptyTextProgressView(context);
        this.f8513b.setShowAtCenter(true);
        this.f8513b.showTextView();
        frameLayout.addView(this.f8513b, LayoutHelper.createFrame(-1, -1.0f));
        this.f8514c = new RecyclerListView(context);
        this.f8514c.setEmptyView(this.f8513b);
        this.f8514c.setSectionsType(1);
        this.f8514c.setVerticalScrollBarEnabled(false);
        this.f8514c.setFastScrollEnabled();
        this.f8514c.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.f8514c.setAdapter(this.f8512a);
        frameLayout.addView(this.f8514c, LayoutHelper.createFrame(-1, -1.0f));
        this.f8514c.setOnItemClickListener(new C25474(this));
        this.f8514c.setOnScrollListener(new C25485(this));
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.contactsDidLoaded) {
            if (this.f8512a != null) {
                this.f8512a.notifyDataSetChanged();
            }
        } else if (i == NotificationCenter.updateInterfaces) {
            int intValue = ((Integer) objArr[0]).intValue();
            if ((intValue & 2) != 0 || (intValue & 1) != 0 || (intValue & 4) != 0) {
                m12329a(intValue);
            }
        } else if (i == NotificationCenter.encryptedChatCreated) {
            if (this.f8522k && this.f8523l) {
                EncryptedChat encryptedChat = (EncryptedChat) objArr[0];
                Bundle bundle = new Bundle();
                bundle.putInt("enc_id", encryptedChat.id);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                presentFragment(new ChatActivity(bundle), true);
            }
        } else if (i == NotificationCenter.closeChats && !this.f8523l) {
            removeSelfFromStack();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        C25518 c25518 = new C25518(this);
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[36];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.f8514c, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch);
        themeDescriptionArr[7] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder);
        themeDescriptionArr[8] = new ThemeDescription(this.f8514c, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[9] = new ThemeDescription(this.f8514c, ThemeDescription.FLAG_SECTIONS, new Class[]{LetterSectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[10] = new ThemeDescription(this.f8514c, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[11] = new ThemeDescription(this.f8513b, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        themeDescriptionArr[12] = new ThemeDescription(this.f8514c, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollActive);
        themeDescriptionArr[13] = new ThemeDescription(this.f8514c, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollInactive);
        themeDescriptionArr[14] = new ThemeDescription(this.f8514c, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollText);
        themeDescriptionArr[15] = new ThemeDescription(this.f8514c, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[16] = new ThemeDescription(this.f8514c, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, c25518, Theme.key_windowBackgroundWhiteGrayText);
        themeDescriptionArr[17] = new ThemeDescription(this.f8514c, 0, new Class[]{UserCell.class}, new String[]{"statusOnlineColor"}, null, null, c25518, Theme.key_windowBackgroundWhiteBlueText);
        themeDescriptionArr[18] = new ThemeDescription(this.f8514c, 0, new Class[]{UserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable}, null, Theme.key_avatar_text);
        themeDescriptionArr[19] = new ThemeDescription(null, 0, null, null, null, c25518, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[20] = new ThemeDescription(null, 0, null, null, null, c25518, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[21] = new ThemeDescription(null, 0, null, null, null, c25518, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[22] = new ThemeDescription(null, 0, null, null, null, c25518, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[23] = new ThemeDescription(null, 0, null, null, null, c25518, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[24] = new ThemeDescription(null, 0, null, null, null, c25518, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[25] = new ThemeDescription(null, 0, null, null, null, c25518, Theme.key_avatar_backgroundPink);
        themeDescriptionArr[26] = new ThemeDescription(this.f8514c, 0, new Class[]{TextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[27] = new ThemeDescription(this.f8514c, 0, new Class[]{TextCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        themeDescriptionArr[28] = new ThemeDescription(this.f8514c, 0, new Class[]{GraySectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[29] = new ThemeDescription(this.f8514c, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GraySectionCell.class}, null, null, null, Theme.key_graySection);
        themeDescriptionArr[30] = new ThemeDescription(this.f8514c, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_groupDrawable, Theme.dialogs_broadcastDrawable, Theme.dialogs_botDrawable}, null, Theme.key_chats_nameIcon);
        themeDescriptionArr[31] = new ThemeDescription(this.f8514c, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_verifiedCheckDrawable}, null, Theme.key_chats_verifiedCheck);
        themeDescriptionArr[32] = new ThemeDescription(this.f8514c, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_verifiedDrawable}, null, Theme.key_chats_verifiedBackground);
        themeDescriptionArr[33] = new ThemeDescription(this.f8514c, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_offlinePaint, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[34] = new ThemeDescription(this.f8514c, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_onlinePaint, null, null, Theme.key_windowBackgroundWhiteBlueText3);
        themeDescriptionArr[35] = new ThemeDescription(this.f8514c, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_namePaint, null, null, Theme.key_chats_name);
        return themeDescriptionArr;
    }

    protected void onDialogDismiss(Dialog dialog) {
        super.onDialogDismiss(dialog);
        if (this.f8532u != null && dialog == this.f8532u && getParentActivity() != null) {
            m12328a();
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.contactsDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.encryptedChatCreated);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeChats);
        if (this.arguments != null) {
            this.f8518g = getArguments().getBoolean("onlyUsers", false);
            this.f8520i = this.arguments.getBoolean("destroyAfterSelect", false);
            this.f8521j = this.arguments.getBoolean("returnAsResult", false);
            this.f8522k = this.arguments.getBoolean("createSecretChat", false);
            this.f8528q = this.arguments.getString("selectAlertString");
            this.f8530s = this.arguments.getBoolean("allowUsernameSearch", true);
            this.f8525n = this.arguments.getBoolean("needForwardCount", true);
            this.f8524m = this.arguments.getBoolean("allowBots", true);
            this.f8526o = this.arguments.getBoolean("addingToChannel", false);
            this.f8527p = this.arguments.getInt("chat_id", 0);
        } else {
            this.f8519h = true;
        }
        ContactsController.getInstance().checkInviteText();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.encryptedChatCreated);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
        this.f8531t = null;
    }

    public void onPause() {
        super.onPause();
        if (this.actionBar != null) {
            this.actionBar.closeSearchField();
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
                        case 1977429404:
                            if (str.equals("android.permission.READ_CONTACTS")) {
                                obj = null;
                                break;
                            }
                            break;
                    }
                    switch (obj) {
                        case null:
                            ContactsController.getInstance().readContacts();
                            break;
                        default:
                            break;
                    }
                }
                i2++;
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (this.f8512a != null) {
            this.f8512a.notifyDataSetChanged();
        }
        if (this.f8533v && VERSION.SDK_INT >= 23) {
            Context parentActivity = getParentActivity();
            if (parentActivity != null) {
                this.f8533v = false;
                if (parentActivity.checkSelfPermission("android.permission.READ_CONTACTS") == 0) {
                    return;
                }
                if (parentActivity.shouldShowRequestPermissionRationale("android.permission.READ_CONTACTS")) {
                    Builder builder = new Builder(parentActivity);
                    builder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                    builder.setMessage(LocaleController.getString("PermissionContacts", R.string.PermissionContacts));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                    Dialog create = builder.create();
                    this.f8532u = create;
                    showDialog(create);
                    return;
                }
                m12328a();
            }
        }
    }
}
