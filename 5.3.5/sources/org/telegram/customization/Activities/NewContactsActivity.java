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
import org.telegram.customization.Adapters.NewContactsAdapter;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.ContactsController$Contact;
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
import org.telegram.tgnet.TLRPC$EncryptedChat;
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

public class NewContactsActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int add_button = 1;
    private static final int search_button = 0;
    private boolean addingToChannel;
    private boolean allowBots = true;
    private boolean allowUsernameSearch = true;
    private int chat_id;
    private boolean checkPermission = true;
    private boolean createSecretChat;
    private boolean creatingChat;
    private ContactsActivityDelegate delegate;
    private boolean destroyAfterSelect;
    private EmptyTextProgressView emptyView;
    private HashMap<Integer, User> ignoreUsers;
    private RecyclerListView listView;
    private NewContactsAdapter listViewAdapter;
    private boolean needForwardCount = true;
    private boolean needPhonebook;
    private boolean onlyUsers;
    private AlertDialog permissionDialog;
    private boolean returnAsResult;
    private SearchAdapter searchListViewAdapter;
    private boolean searchWas;
    private boolean searching;
    private String selectAlertString = null;

    /* renamed from: org.telegram.customization.Activities.NewContactsActivity$1 */
    class C10321 extends ActionBarMenuOnItemClick {
        C10321() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                NewContactsActivity.this.finishFragment();
            } else if (id == 1) {
                NewContactsActivity.this.presentFragment(new NewContactActivity());
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.NewContactsActivity$2 */
    class C10332 implements OnClickListener {
        C10332() {
        }

        public void onClick(View view) {
            ContactsController.getInstance().loadContacts(false, 0);
        }
    }

    /* renamed from: org.telegram.customization.Activities.NewContactsActivity$3 */
    class C10343 extends ActionBarMenuItemSearchListener {
        C10343() {
        }

        public void onSearchExpand() {
            NewContactsActivity.this.searching = true;
        }

        public void onSearchCollapse() {
            NewContactsActivity.this.searchListViewAdapter.searchDialogs(null);
            NewContactsActivity.this.searching = false;
            NewContactsActivity.this.searchWas = false;
            NewContactsActivity.this.listView.setAdapter(NewContactsActivity.this.listViewAdapter);
            NewContactsActivity.this.listViewAdapter.notifyDataSetChanged();
            NewContactsActivity.this.listView.setFastScrollVisible(true);
            NewContactsActivity.this.listView.setVerticalScrollBarEnabled(false);
            NewContactsActivity.this.emptyView.setText(LocaleController.getString("NoContacts", R.string.NoContacts));
        }

        public void onTextChanged(EditText editText) {
            if (NewContactsActivity.this.searchListViewAdapter != null) {
                String text = editText.getText().toString();
                if (text.length() != 0) {
                    NewContactsActivity.this.searchWas = true;
                    if (NewContactsActivity.this.listView != null) {
                        NewContactsActivity.this.listView.setAdapter(NewContactsActivity.this.searchListViewAdapter);
                        NewContactsActivity.this.searchListViewAdapter.notifyDataSetChanged();
                        NewContactsActivity.this.listView.setFastScrollVisible(false);
                        NewContactsActivity.this.listView.setVerticalScrollBarEnabled(true);
                    }
                    if (NewContactsActivity.this.emptyView != null) {
                        NewContactsActivity.this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
                    }
                }
                NewContactsActivity.this.searchListViewAdapter.searchDialogs(text);
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.NewContactsActivity$4 */
    class C10364 implements OnItemClickListener {
        C10364() {
        }

        public void onItemClick(View view, int position) {
            User user;
            Bundle args;
            if (NewContactsActivity.this.searching && NewContactsActivity.this.searchWas) {
                user = (User) NewContactsActivity.this.searchListViewAdapter.getItem(position);
                if (user != null) {
                    if (NewContactsActivity.this.searchListViewAdapter.isGlobalSearch(position)) {
                        ArrayList<User> users = new ArrayList();
                        users.add(user);
                        MessagesController.getInstance().putUsers(users, false);
                        MessagesStorage.getInstance().putUsersAndChats(users, null, false, true);
                    }
                    if (NewContactsActivity.this.returnAsResult) {
                        if (NewContactsActivity.this.ignoreUsers == null || !NewContactsActivity.this.ignoreUsers.containsKey(Integer.valueOf(user.id))) {
                            NewContactsActivity.this.didSelectResult(user, true, null);
                            return;
                        }
                        return;
                    } else if (!NewContactsActivity.this.createSecretChat) {
                        args = new Bundle();
                        args.putInt("user_id", user.id);
                        if (MessagesController.checkCanOpenChat(args, NewContactsActivity.this)) {
                            NewContactsActivity.this.presentFragment(new ChatActivity(args), true);
                            return;
                        }
                        return;
                    } else if (user.id != UserConfig.getClientUserId()) {
                        NewContactsActivity.this.creatingChat = true;
                        SecretChatHelper.getInstance().startSecretChat(NewContactsActivity.this.getParentActivity(), user);
                        return;
                    } else {
                        return;
                    }
                }
                return;
            }
            int section = NewContactsActivity.this.listViewAdapter.getSectionForPosition(position);
            int row = NewContactsActivity.this.listViewAdapter.getPositionInSectionForPosition(position);
            if (row >= 0 && section >= 0) {
                if ((NewContactsActivity.this.onlyUsers && NewContactsActivity.this.chat_id == 0) || section != 0) {
                    ContactsController$Contact item = NewContactsActivity.this.listViewAdapter.getItem(section, row);
                    if (item instanceof User) {
                        user = (User) item;
                        if (NewContactsActivity.this.returnAsResult) {
                            if (NewContactsActivity.this.ignoreUsers == null || !NewContactsActivity.this.ignoreUsers.containsKey(Integer.valueOf(user.id))) {
                                NewContactsActivity.this.didSelectResult(user, true, null);
                            }
                        } else if (NewContactsActivity.this.createSecretChat) {
                            NewContactsActivity.this.creatingChat = true;
                            SecretChatHelper.getInstance().startSecretChat(NewContactsActivity.this.getParentActivity(), user);
                        } else {
                            args = new Bundle();
                            args.putInt("user_id", user.id);
                            if (MessagesController.checkCanOpenChat(args, NewContactsActivity.this)) {
                                NewContactsActivity.this.presentFragment(new ChatActivity(args), true);
                            }
                        }
                    } else if (item instanceof ContactsController$Contact) {
                        ContactsController$Contact contact = item;
                        String usePhone = null;
                        if (!contact.phones.isEmpty()) {
                            usePhone = (String) contact.phones.get(0);
                        }
                        if (usePhone != null && NewContactsActivity.this.getParentActivity() != null) {
                            Builder builder = new Builder(NewContactsActivity.this.getParentActivity());
                            builder.setMessage(LocaleController.getString("InviteUser", R.string.InviteUser));
                            builder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                            final String arg1 = usePhone;
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        Intent intent = new Intent("android.intent.action.VIEW", Uri.fromParts("sms", arg1, null));
                                        intent.putExtra("sms_body", LocaleController.getString("InviteText", R.string.InviteText));
                                        NewContactsActivity.this.getParentActivity().startActivityForResult(intent, 500);
                                    } catch (Exception e) {
                                        FileLog.e(e);
                                    }
                                }
                            });
                            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                            NewContactsActivity.this.showDialog(builder.create());
                        }
                    }
                } else if (NewContactsActivity.this.needPhonebook) {
                    if (row != 0) {
                    }
                } else if (NewContactsActivity.this.chat_id != 0) {
                    if (row == 0) {
                        NewContactsActivity.this.presentFragment(new GroupInviteActivity(NewContactsActivity.this.chat_id));
                    }
                } else if (row == 0) {
                    if (MessagesController.isFeatureEnabled("chat_create", NewContactsActivity.this)) {
                        NewContactsActivity.this.presentFragment(new GroupCreateActivity(), false);
                    }
                } else if (row == 1) {
                    args = new Bundle();
                    args.putBoolean("onlyUsers", true);
                    args.putBoolean("destroyAfterSelect", true);
                    args.putBoolean("createSecretChat", true);
                    args.putBoolean("allowBots", false);
                    NewContactsActivity.this.presentFragment(new NewContactsActivity(args), false);
                } else if (row == 2 && MessagesController.isFeatureEnabled("broadcast_create", NewContactsActivity.this)) {
                    SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                    if (BuildVars.DEBUG_VERSION || !preferences.getBoolean("channel_intro", false)) {
                        NewContactsActivity.this.presentFragment(new ChannelIntroActivity());
                        preferences.edit().putBoolean("channel_intro", true).commit();
                        return;
                    }
                    args = new Bundle();
                    args.putInt("step", 0);
                    NewContactsActivity.this.presentFragment(new ChannelCreateActivity(args));
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.NewContactsActivity$5 */
    class C10375 extends OnScrollListener {
        C10375() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == 1 && NewContactsActivity.this.searching && NewContactsActivity.this.searchWas) {
                AndroidUtilities.hideKeyboard(NewContactsActivity.this.getParentActivity().getCurrentFocus());
            }
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

    /* renamed from: org.telegram.customization.Activities.NewContactsActivity$8 */
    class C10408 implements ThemeDescriptionDelegate {
        C10408() {
        }

        public void didSetColor(int color) {
            int count = NewContactsActivity.this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = NewContactsActivity.this.listView.getChildAt(a);
                if (child instanceof UserCell) {
                    ((UserCell) child).update(0);
                } else if (child instanceof ProfileSearchCell) {
                    ((ProfileSearchCell) child).update(0);
                }
            }
        }
    }

    public interface ContactsActivityDelegate {
        void didSelectContact(User user, String str);
    }

    public NewContactsActivity(Bundle args) {
        super(args);
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.contactsDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.encryptedChatCreated);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeChats);
        if (this.arguments != null) {
            this.onlyUsers = getArguments().getBoolean("onlyUsers", false);
            this.destroyAfterSelect = this.arguments.getBoolean("destroyAfterSelect", false);
            this.returnAsResult = this.arguments.getBoolean("returnAsResult", false);
            this.createSecretChat = this.arguments.getBoolean("createSecretChat", false);
            this.selectAlertString = this.arguments.getString("selectAlertString");
            this.allowUsernameSearch = this.arguments.getBoolean("allowUsernameSearch", true);
            this.needForwardCount = this.arguments.getBoolean("needForwardCount", true);
            this.allowBots = this.arguments.getBoolean("allowBots", true);
            this.addingToChannel = this.arguments.getBoolean("addingToChannel", false);
            this.chat_id = this.arguments.getInt("chat_id", 0);
        } else {
            this.needPhonebook = true;
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
        this.delegate = null;
    }

    public View createView(Context context) {
        this.searching = false;
        this.searchWas = false;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("onlineContacts", R.string.Online_Contacts));
        this.actionBar.setActionBarMenuOnItemClick(new C10321());
        ActionBarMenu menu = this.actionBar.createMenu();
        menu.addItem(100, (int) R.drawable.ic_sync).setOnClickListener(new C10332());
        menu.addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C10343()).getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        if (!(this.createSecretChat || this.returnAsResult)) {
            menu.addItem(1, (int) R.drawable.add);
        }
        this.searchListViewAdapter = new SearchAdapter(context, this.ignoreUsers, this.allowUsernameSearch, false, false, this.allowBots, 0);
        this.listViewAdapter = new NewContactsAdapter(context, this.onlyUsers ? 1 : 0, this.needPhonebook, this.ignoreUsers, this.chat_id != 0);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.setShowAtCenter(true);
        this.emptyView.showTextView();
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setEmptyView(this.emptyView);
        this.listView.setSectionsType(1);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setFastScrollEnabled();
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setAdapter(this.listViewAdapter);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemClickListener(new C10364());
        this.listView.setOnScrollListener(new C10375());
        return this.fragmentView;
    }

    private void didSelectResult(final User user, boolean useAlert, String param) {
        if (!useAlert || this.selectAlertString == null) {
            if (this.delegate != null) {
                this.delegate.didSelectContact(user, param);
                this.delegate = null;
            }
            finishFragment();
        } else if (getParentActivity() != null) {
            if (user.bot && user.bot_nochats && !this.addingToChannel) {
                try {
                    Toast.makeText(getParentActivity(), LocaleController.getString("BotCantJoinGroups", R.string.BotCantJoinGroups), 0).show();
                    return;
                } catch (Exception e) {
                    FileLog.e(e);
                    return;
                }
            }
            Builder builder = new Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
            String message = LocaleController.formatStringSimple(this.selectAlertString, new Object[]{UserObject.getUserName(user)});
            EditText editText = null;
            if (!user.bot && this.needForwardCount) {
                message = String.format("%s\n\n%s", new Object[]{message, LocaleController.getString("AddToTheGroupForwardCount", R.string.AddToTheGroupForwardCount)});
                editText = new EditText(getParentActivity());
                editText.setTextSize(18.0f);
                editText.setText("50");
                editText.setGravity(17);
                editText.setInputType(2);
                editText.setImeOptions(6);
                editText.setBackgroundDrawable(Theme.createEditTextDrawable(getParentActivity(), true));
                final EditText editTextFinal = editText;
                editText.addTextChangedListener(new TextWatcher() {
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    public void afterTextChanged(Editable s) {
                        try {
                            String str = s.toString();
                            if (str.length() != 0) {
                                int value = Utilities.parseInt(str).intValue();
                                if (value < 0) {
                                    editTextFinal.setText("0");
                                    editTextFinal.setSelection(editTextFinal.length());
                                } else if (value > ScheduleDownloadActivity.CHECK_CELL2) {
                                    editTextFinal.setText("300");
                                    editTextFinal.setSelection(editTextFinal.length());
                                } else if (!str.equals("" + value)) {
                                    editTextFinal.setText("" + value);
                                    editTextFinal.setSelection(editTextFinal.length());
                                }
                            }
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                });
                builder.setView(editText);
            }
            builder.setMessage(message);
            final EditText finalEditText = editText;
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    NewContactsActivity.this.didSelectResult(user, false, finalEditText != null ? finalEditText.getText().toString() : "0");
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            showDialog(builder.create());
            if (editText != null) {
                MarginLayoutParams layoutParams = (MarginLayoutParams) editText.getLayoutParams();
                if (layoutParams != null) {
                    if (layoutParams instanceof LayoutParams) {
                        ((LayoutParams) layoutParams).gravity = 1;
                    }
                    int dp = AndroidUtilities.dp(10.0f);
                    layoutParams.leftMargin = dp;
                    layoutParams.rightMargin = dp;
                    editText.setLayoutParams(layoutParams);
                }
                editText.setSelection(editText.getText().length());
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
        if (this.checkPermission && VERSION.SDK_INT >= 23) {
            Activity activity = getParentActivity();
            if (activity != null) {
                this.checkPermission = false;
                if (activity.checkSelfPermission("android.permission.READ_CONTACTS") == 0) {
                    return;
                }
                if (activity.shouldShowRequestPermissionRationale("android.permission.READ_CONTACTS")) {
                    Builder builder = new Builder(activity);
                    builder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                    builder.setMessage(LocaleController.getString("PermissionContacts", R.string.PermissionContacts));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                    Dialog create = builder.create();
                    this.permissionDialog = create;
                    showDialog(create);
                    return;
                }
                askForPermissons();
            }
        }
    }

    protected void onDialogDismiss(Dialog dialog) {
        super.onDialogDismiss(dialog);
        if (this.permissionDialog != null && dialog == this.permissionDialog && getParentActivity() != null) {
            askForPermissons();
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
            activity.requestPermissions((String[]) permissons.toArray(new String[permissons.size()]), 1);
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
                a++;
            }
        }
    }

    public void onPause() {
        super.onPause();
        if (this.actionBar != null) {
            this.actionBar.closeSearchField();
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.contactsDidLoaded) {
            if (this.listViewAdapter != null) {
                this.listViewAdapter.notifyDataSetChanged();
            }
        } else if (id == NotificationCenter.updateInterfaces) {
            int mask = ((Integer) args[0]).intValue();
            if ((mask & 2) != 0 || (mask & 1) != 0 || (mask & 4) != 0) {
                updateVisibleRows(mask);
            }
        } else if (id == NotificationCenter.encryptedChatCreated) {
            if (this.createSecretChat && this.creatingChat) {
                TLRPC$EncryptedChat encryptedChat = args[0];
                Bundle args2 = new Bundle();
                args2.putInt("enc_id", encryptedChat.id);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                presentFragment(new ChatActivity(args2), true);
            }
        } else if (id == NotificationCenter.closeChats && !this.creatingChat) {
            removeSelfFromStack();
        }
    }

    private void updateVisibleRows(int mask) {
        if (this.listView != null) {
            int count = this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = this.listView.getChildAt(a);
                if (child instanceof UserCell) {
                    ((UserCell) child).update(mask);
                }
            }
        }
    }

    public void setDelegate(ContactsActivityDelegate delegate) {
        this.delegate = delegate;
    }

    public void setIgnoreUsers(HashMap<Integer, User> users) {
        this.ignoreUsers = users;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new C10408();
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[36];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch);
        themeDescriptionArr[7] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SECTIONS, new Class[]{LetterSectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[11] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollActive);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollInactive);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollText);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, сellDelegate, Theme.key_windowBackgroundWhiteGrayText);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusOnlineColor"}, null, null, сellDelegate, Theme.key_windowBackgroundWhiteBlueText);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable}, null, Theme.key_avatar_text);
        themeDescriptionArr[19] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[20] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[21] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[22] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[23] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[24] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[25] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundPink);
        themeDescriptionArr[26] = new ThemeDescription(this.listView, 0, new Class[]{TextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[27] = new ThemeDescription(this.listView, 0, new Class[]{TextCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        themeDescriptionArr[28] = new ThemeDescription(this.listView, 0, new Class[]{GraySectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[29] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GraySectionCell.class}, null, null, null, Theme.key_graySection);
        themeDescriptionArr[30] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_groupDrawable, Theme.dialogs_broadcastDrawable, Theme.dialogs_botDrawable}, null, Theme.key_chats_nameIcon);
        themeDescriptionArr[31] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_verifiedCheckDrawable}, null, Theme.key_chats_verifiedCheck);
        themeDescriptionArr[32] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_verifiedDrawable}, null, Theme.key_chats_verifiedBackground);
        themeDescriptionArr[33] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_offlinePaint, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[34] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_onlinePaint, null, null, Theme.key_windowBackgroundWhiteBlueText3);
        themeDescriptionArr[35] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_namePaint, null, null, Theme.key_chats_name);
        return themeDescriptionArr;
    }
}
