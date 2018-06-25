package org.telegram.messenger;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.ir.talaeii.R;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$InputUser;
import org.telegram.tgnet.TLRPC$PrivacyRule;
import org.telegram.tgnet.TLRPC$TL_account_getAccountTTL;
import org.telegram.tgnet.TLRPC$TL_account_getPrivacy;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC$TL_contacts_deleteContacts;
import org.telegram.tgnet.TLRPC$TL_contacts_getContacts;
import org.telegram.tgnet.TLRPC$TL_contacts_getStatuses;
import org.telegram.tgnet.TLRPC$TL_contacts_importContacts;
import org.telegram.tgnet.TLRPC$TL_contacts_resetSaved;
import org.telegram.tgnet.TLRPC$TL_help_getInviteText;
import org.telegram.tgnet.TLRPC$TL_inputPhoneContact;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyKeyChatInvite;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyKeyPhoneCall;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyKeyStatusTimestamp;
import org.telegram.tgnet.TLRPC.User;

public class ContactsController {
    private static volatile ContactsController Instance = null;
    private static final Object loadContactsSync = new Object();
    private ArrayList<TLRPC$PrivacyRule> callPrivacyRules;
    private int completedRequestsCount;
    public ArrayList<TLRPC$TL_contact> contacts = new ArrayList();
    public ArrayList<TLRPC$TL_contact> contacts1 = new ArrayList();
    public HashMap<String, ContactsController$Contact> contactsBook = new HashMap();
    private boolean contactsBookLoaded;
    public HashMap<String, ContactsController$Contact> contactsBookSPhones = new HashMap();
    public HashMap<String, TLRPC$TL_contact> contactsByPhone = new HashMap();
    public HashMap<String, TLRPC$TL_contact> contactsByShortPhone = new HashMap();
    public ConcurrentHashMap<Integer, TLRPC$TL_contact> contactsDict = new ConcurrentHashMap(20, 1.0f, 2);
    public SparseArray<TLRPC$TL_contact> contactsDict1 = new SparseArray();
    public boolean contactsLoaded;
    private boolean contactsSyncInProgress;
    private Account currentAccount;
    private ArrayList<Integer> delayedContactsUpdate = new ArrayList();
    private int deleteAccountTTL;
    private ArrayList<TLRPC$PrivacyRule> groupPrivacyRules;
    private boolean ignoreChanges;
    private String inviteLink;
    private String lastContactsVersions = "";
    private int loadingCallsInfo;
    private boolean loadingContacts;
    private int loadingDeleteInfo;
    private int loadingGroupInfo;
    private int loadingLastSeenInfo;
    private boolean migratingContacts;
    private final Object observerLock = new Object();
    public ArrayList<ContactsController$Contact> phoneBookContacts = new ArrayList();
    private ArrayList<TLRPC$PrivacyRule> privacyRules;
    private String[] projectionNames = new String[]{"lookup", "data2", "data3", "display_name", "data5"};
    private String[] projectionPhones = new String[]{"lookup", "data1", "data2", "data3"};
    private HashMap<String, String> sectionsToReplace = new HashMap();
    public ArrayList<String> sortedUsersMutualSectionsArray = new ArrayList();
    public ArrayList<String> sortedUsersMutualSectionsArray1 = new ArrayList();
    public ArrayList<String> sortedUsersSectionsArray = new ArrayList();
    public ArrayList<String> sortedUsersSectionsArray1 = new ArrayList();
    private boolean updatingInviteLink;
    public HashMap<String, ArrayList<TLRPC$TL_contact>> usersMutualSectionsDict = new HashMap();
    public HashMap<String, ArrayList<TLRPC$TL_contact>> usersMutualSectionsDict1 = new HashMap();
    public HashMap<String, ArrayList<TLRPC$TL_contact>> usersSectionsDict = new HashMap();
    public HashMap<String, ArrayList<TLRPC$TL_contact>> usersSectionsDict1 = new HashMap();

    public static ContactsController getInstance() {
        ContactsController localInstance = Instance;
        if (localInstance == null) {
            synchronized (ContactsController.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        ContactsController localInstance2 = new ContactsController();
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

    public ContactsController() {
        if (ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("needGetStatuses", false)) {
            reloadContactsStatuses();
        }
        this.sectionsToReplace.put("À", "A");
        this.sectionsToReplace.put("Á", "A");
        this.sectionsToReplace.put("Ä", "A");
        this.sectionsToReplace.put("Ù", "U");
        this.sectionsToReplace.put("Ú", "U");
        this.sectionsToReplace.put("Ü", "U");
        this.sectionsToReplace.put("Ì", "I");
        this.sectionsToReplace.put("Í", "I");
        this.sectionsToReplace.put("Ï", "I");
        this.sectionsToReplace.put("È", "E");
        this.sectionsToReplace.put("É", "E");
        this.sectionsToReplace.put("Ê", "E");
        this.sectionsToReplace.put("Ë", "E");
        this.sectionsToReplace.put("Ò", "O");
        this.sectionsToReplace.put("Ó", "O");
        this.sectionsToReplace.put("Ö", "O");
        this.sectionsToReplace.put("Ç", "C");
        this.sectionsToReplace.put("Ñ", "N");
        this.sectionsToReplace.put("Ÿ", "Y");
        this.sectionsToReplace.put("Ý", "Y");
        this.sectionsToReplace.put("Ţ", "Y");
    }

    public void cleanup() {
        this.contactsBook.clear();
        this.contactsBookSPhones.clear();
        this.phoneBookContacts.clear();
        this.contacts.clear();
        this.contactsDict.clear();
        this.usersSectionsDict.clear();
        this.usersMutualSectionsDict.clear();
        this.sortedUsersSectionsArray.clear();
        this.sortedUsersMutualSectionsArray.clear();
        this.delayedContactsUpdate.clear();
        this.contactsByPhone.clear();
        this.contactsByShortPhone.clear();
        this.loadingContacts = false;
        this.contactsSyncInProgress = false;
        this.contactsLoaded = false;
        this.contactsBookLoaded = false;
        this.lastContactsVersions = "";
        this.loadingDeleteInfo = 0;
        this.deleteAccountTTL = 0;
        this.loadingLastSeenInfo = 0;
        this.loadingGroupInfo = 0;
        this.loadingCallsInfo = 0;
        Utilities.globalQueue.postRunnable(new ContactsController$1(this));
        this.privacyRules = null;
    }

    public void checkInviteText() {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        this.inviteLink = preferences.getString("invitelink", null);
        int time = preferences.getInt("invitelinktime", 0);
        if (!this.updatingInviteLink) {
            if (this.inviteLink == null || Math.abs((System.currentTimeMillis() / 1000) - ((long) time)) >= 86400) {
                this.updatingInviteLink = true;
                ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_help_getInviteText(), new ContactsController$2(this), 2);
            }
        }
    }

    public String getInviteText(int contacts) {
        String link = this.inviteLink == null ? "https://telegram.org/dl" : this.inviteLink;
        if (contacts <= 1) {
            return LocaleController.formatString("InviteText2", R.string.InviteText2, link);
        }
        try {
            return String.format(LocaleController.getPluralString("InviteTextNum", contacts), new Object[]{Integer.valueOf(contacts), link});
        } catch (Exception e) {
            return LocaleController.formatString("InviteText2", R.string.InviteText2, link);
        }
    }

    public void checkAppAccount() {
        Account[] accounts;
        int a;
        AccountManager am = AccountManager.get(ApplicationLoader.applicationContext);
        try {
            accounts = am.getAccountsByType("org.telegram.account");
            if (accounts != null && accounts.length > 0) {
                for (Account removeAccount : accounts) {
                    am.removeAccount(removeAccount, null, null);
                }
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
        accounts = am.getAccountsByType("org.telegram.messenger");
        boolean recreateAccount = false;
        if (UserConfig.isClientActivated()) {
            if (accounts.length == 1) {
                Account acc = accounts[0];
                if (acc.name.equals("" + UserConfig.getClientUserId())) {
                    this.currentAccount = acc;
                } else {
                    recreateAccount = true;
                }
            } else {
                recreateAccount = true;
            }
            readContacts();
        } else if (accounts.length > 0) {
            recreateAccount = true;
        }
        if (recreateAccount) {
            a = 0;
            while (a < accounts.length) {
                try {
                    am.removeAccount(accounts[a], null, null);
                    a++;
                } catch (Throwable e2) {
                    FileLog.m94e(e2);
                }
            }
            if (UserConfig.isClientActivated()) {
                try {
                    this.currentAccount = new Account("" + UserConfig.getClientUserId(), "org.telegram.messenger");
                    am.addAccountExplicitly(this.currentAccount, "", null);
                } catch (Throwable e22) {
                    FileLog.m94e(e22);
                }
            }
        }
    }

    public void deleteAllAppAccounts() {
        try {
            AccountManager am = AccountManager.get(ApplicationLoader.applicationContext);
            Account[] accounts = am.getAccountsByType("org.telegram.messenger");
            for (Account removeAccount : accounts) {
                am.removeAccount(removeAccount, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkContacts() {
        Utilities.globalQueue.postRunnable(new ContactsController$3(this));
    }

    public void forceImportContacts() {
        Utilities.globalQueue.postRunnable(new ContactsController$4(this));
    }

    public void syncPhoneBookByAlert(HashMap<String, ContactsController$Contact> contacts, boolean first, boolean schedule, boolean cancel) {
        Utilities.globalQueue.postRunnable(new ContactsController$5(this, contacts, first, schedule, cancel));
    }

    public void resetImportedContacts() {
        ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_contacts_resetSaved(), new ContactsController$6(this));
    }

    private boolean checkContactsInternal() {
        boolean reload = false;
        try {
            if (!hasContactsPermission()) {
                return false;
            }
            ContentResolver cr = ApplicationLoader.applicationContext.getContentResolver();
            Cursor pCur = null;
            try {
                pCur = cr.query(RawContacts.CONTENT_URI, new String[]{"version"}, null, null, null);
                if (pCur != null) {
                    StringBuilder currentVersion = new StringBuilder();
                    while (pCur.moveToNext()) {
                        currentVersion.append(pCur.getString(pCur.getColumnIndex("version")));
                    }
                    String newContactsVersion = currentVersion.toString();
                    if (!(this.lastContactsVersions.length() == 0 || this.lastContactsVersions.equals(newContactsVersion))) {
                        reload = true;
                    }
                    this.lastContactsVersions = newContactsVersion;
                }
                if (pCur != null) {
                    pCur.close();
                }
            } catch (Throwable e) {
                FileLog.m94e(e);
                if (pCur != null) {
                    pCur.close();
                }
            } catch (Throwable th) {
                if (pCur != null) {
                    pCur.close();
                }
            }
            return reload;
        } catch (Throwable e2) {
            FileLog.m94e(e2);
        }
    }

    public void readContacts() {
        synchronized (loadContactsSync) {
            if (this.loadingContacts) {
                return;
            }
            this.loadingContacts = true;
            Utilities.stageQueue.postRunnable(new ContactsController$7(this));
        }
    }

    private HashMap<String, ContactsController$Contact> readContactsFromPhoneBook() {
        HashMap<String, ContactsController$Contact> contactsMap = new HashMap();
        Cursor pCur = null;
        if (hasContactsPermission()) {
            String lookup_key;
            ContactsController$Contact contact;
            ContentResolver cr = ApplicationLoader.applicationContext.getContentResolver();
            HashMap<String, ContactsController$Contact> shortContacts = new HashMap();
            ArrayList<String> idsArr = new ArrayList();
            pCur = cr.query(Phone.CONTENT_URI, this.projectionPhones, null, null, null);
            if (pCur != null) {
                if (pCur.getCount() > 0) {
                    int lastContactId = 1;
                    while (pCur.moveToNext()) {
                        String number = pCur.getString(1);
                        if (!TextUtils.isEmpty(number)) {
                            number = PhoneFormat.stripExceptNumbers(number, true);
                            if (TextUtils.isEmpty(number)) {
                                continue;
                            } else {
                                String shortNumber = number;
                                if (number.startsWith("+")) {
                                    shortNumber = number.substring(1);
                                }
                                if (shortContacts.containsKey(shortNumber)) {
                                    continue;
                                } else {
                                    int lastContactId2;
                                    lookup_key = pCur.getString(0);
                                    String key = "'" + lookup_key + "'";
                                    if (!idsArr.contains(key)) {
                                        idsArr.add(key);
                                    }
                                    int type = pCur.getInt(2);
                                    contact = (ContactsController$Contact) contactsMap.get(lookup_key);
                                    if (contact == null) {
                                        contact = new ContactsController$Contact();
                                        contact.first_name = "";
                                        contact.last_name = "";
                                        contact.key = lookup_key;
                                        lastContactId2 = lastContactId + 1;
                                        contact.contact_id = lastContactId;
                                        contactsMap.put(lookup_key, contact);
                                    } else {
                                        lastContactId2 = lastContactId;
                                    }
                                    contact.shortPhones.add(shortNumber);
                                    contact.phones.add(number);
                                    contact.phoneDeleted.add(Integer.valueOf(0));
                                    if (type == 0) {
                                        String custom = pCur.getString(3);
                                        ArrayList arrayList = contact.phoneTypes;
                                        if (custom == null) {
                                            custom = LocaleController.getString("PhoneMobile", R.string.PhoneMobile);
                                        }
                                        arrayList.add(custom);
                                    } else if (type == 1) {
                                        contact.phoneTypes.add(LocaleController.getString("PhoneHome", R.string.PhoneHome));
                                    } else if (type == 2) {
                                        try {
                                            contact.phoneTypes.add(LocaleController.getString("PhoneMobile", R.string.PhoneMobile));
                                        } catch (Throwable e) {
                                            FileLog.m94e(e);
                                            contactsMap.clear();
                                            if (pCur != null) {
                                                try {
                                                    pCur.close();
                                                } catch (Throwable e2) {
                                                    FileLog.m94e(e2);
                                                }
                                            }
                                        } catch (Throwable th) {
                                            if (pCur != null) {
                                                try {
                                                    pCur.close();
                                                } catch (Throwable e22) {
                                                    FileLog.m94e(e22);
                                                }
                                            }
                                        }
                                    } else if (type == 3) {
                                        contact.phoneTypes.add(LocaleController.getString("PhoneWork", R.string.PhoneWork));
                                    } else if (type == 12) {
                                        contact.phoneTypes.add(LocaleController.getString("PhoneMain", R.string.PhoneMain));
                                    } else {
                                        contact.phoneTypes.add(LocaleController.getString("PhoneOther", R.string.PhoneOther));
                                    }
                                    shortContacts.put(shortNumber, contact);
                                    lastContactId = lastContactId2;
                                }
                            }
                        }
                    }
                }
                try {
                    pCur.close();
                } catch (Exception e3) {
                }
                pCur = null;
            }
            pCur = cr.query(Data.CONTENT_URI, this.projectionNames, "lookup IN (" + TextUtils.join(",", idsArr) + ") AND " + "mimetype" + " = '" + "vnd.android.cursor.item/name" + "'", null, null);
            if (pCur != null) {
                while (pCur.moveToNext()) {
                    lookup_key = pCur.getString(0);
                    String fname = pCur.getString(1);
                    String sname = pCur.getString(2);
                    String sname2 = pCur.getString(3);
                    String mname = pCur.getString(4);
                    contact = (ContactsController$Contact) contactsMap.get(lookup_key);
                    if (contact != null && TextUtils.isEmpty(contact.first_name) && TextUtils.isEmpty(contact.last_name)) {
                        contact.first_name = fname;
                        contact.last_name = sname;
                        if (contact.first_name == null) {
                            contact.first_name = "";
                        }
                        if (!TextUtils.isEmpty(mname)) {
                            if (contact.first_name.length() != 0) {
                                contact.first_name += " " + mname;
                            } else {
                                contact.first_name = mname;
                            }
                        }
                        if (contact.last_name == null) {
                            contact.last_name = "";
                        }
                        if (TextUtils.isEmpty(contact.last_name) && TextUtils.isEmpty(contact.first_name) && !TextUtils.isEmpty(sname2)) {
                            contact.first_name = sname2;
                        }
                    }
                }
                try {
                    pCur.close();
                } catch (Exception e4) {
                }
                pCur = null;
            }
            if (pCur != null) {
                try {
                    pCur.close();
                } catch (Throwable e222) {
                    FileLog.m94e(e222);
                }
            }
        } else if (pCur != null) {
            try {
                pCur.close();
            } catch (Throwable e2222) {
                FileLog.m94e(e2222);
            }
        }
        return contactsMap;
    }

    public HashMap<String, ContactsController$Contact> getContactsCopy(HashMap<String, ContactsController$Contact> original) {
        HashMap<String, ContactsController$Contact> ret = new HashMap();
        for (Entry<String, ContactsController$Contact> entry : original.entrySet()) {
            ContactsController$Contact copyContact = new ContactsController$Contact();
            ContactsController$Contact originalContact = (ContactsController$Contact) entry.getValue();
            copyContact.phoneDeleted.addAll(originalContact.phoneDeleted);
            copyContact.phones.addAll(originalContact.phones);
            copyContact.phoneTypes.addAll(originalContact.phoneTypes);
            copyContact.shortPhones.addAll(originalContact.shortPhones);
            copyContact.first_name = originalContact.first_name;
            copyContact.last_name = originalContact.last_name;
            copyContact.contact_id = originalContact.contact_id;
            copyContact.key = originalContact.key;
            ret.put(copyContact.key, copyContact);
        }
        return ret;
    }

    protected void migratePhoneBookToV7(HashMap<Integer, ContactsController$Contact> contactHashMap) {
        Utilities.globalQueue.postRunnable(new ContactsController$8(this, contactHashMap));
    }

    protected void performSyncPhoneBook(HashMap<String, ContactsController$Contact> contactHashMap, boolean request, boolean first, boolean schedule, boolean force, boolean checkCount, boolean canceled) {
        if (first || this.contactsBookLoaded) {
            Utilities.globalQueue.postRunnable(new ContactsController$9(this, contactHashMap, schedule, request, first, force, checkCount, canceled));
        }
    }

    public boolean isLoadingContacts() {
        boolean z;
        synchronized (loadContactsSync) {
            z = this.loadingContacts;
        }
        return z;
    }

    private int getContactsHash(ArrayList<TLRPC$TL_contact> contacts) {
        long acc = 0;
        ArrayList<TLRPC$TL_contact> contacts2 = new ArrayList(contacts);
        Collections.sort(contacts2, new ContactsController$10(this));
        int count = contacts2.size();
        for (int a = -1; a < count; a++) {
            if (a == -1) {
                acc = (((acc * 20261) + 2147483648L) + ((long) UserConfig.contactsSavedCount)) % 2147483648L;
            } else {
                acc = (((acc * 20261) + 2147483648L) + ((long) ((TLRPC$TL_contact) contacts2.get(a)).user_id)) % 2147483648L;
            }
        }
        return (int) acc;
    }

    public void loadContacts(boolean fromCache, int hash) {
        synchronized (loadContactsSync) {
            this.loadingContacts = true;
        }
        if (fromCache) {
            FileLog.m92e("load contacts from cache");
            MessagesStorage.getInstance().getContacts();
            return;
        }
        FileLog.m92e("load contacts from server");
        TLRPC$TL_contacts_getContacts req = new TLRPC$TL_contacts_getContacts();
        req.hash = hash;
        ConnectionsManager.getInstance().sendRequest(req, new ContactsController$11(this, hash));
    }

    public void processLoadedContacts(ArrayList<TLRPC$TL_contact> contactsArr, ArrayList<User> usersArr, int from) {
        AndroidUtilities.runOnUIThread(new ContactsController$12(this, usersArr, from, contactsArr));
    }

    private void reloadContactsStatusesMaybe() {
        try {
            if (ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getLong("lastReloadStatusTime", 0) < System.currentTimeMillis() - 86400000) {
                reloadContactsStatuses();
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
    }

    private void saveContactsLoadTime() {
        try {
            ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putLong("lastReloadStatusTime", System.currentTimeMillis()).commit();
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
    }

    public void updateUnregisteredContacts(ArrayList<TLRPC$TL_contact> contactsArr) {
        int a;
        HashMap<String, TLRPC$TL_contact> contactsPhonesShort = new HashMap();
        for (a = 0; a < contactsArr.size(); a++) {
            TLRPC$TL_contact value = (TLRPC$TL_contact) contactsArr.get(a);
            User user = MessagesController.getInstance().getUser(Integer.valueOf(value.user_id));
            if (!(user == null || TextUtils.isEmpty(user.phone))) {
                contactsPhonesShort.put(user.phone, value);
            }
        }
        ArrayList<ContactsController$Contact> sortedPhoneBookContacts = new ArrayList();
        for (Entry<String, ContactsController$Contact> pair : this.contactsBook.entrySet()) {
            ContactsController$Contact value2 = (ContactsController$Contact) pair.getValue();
            boolean skip = false;
            a = 0;
            while (a < value2.phones.size()) {
                if (contactsPhonesShort.containsKey((String) value2.shortPhones.get(a)) || ((Integer) value2.phoneDeleted.get(a)).intValue() == 1) {
                    skip = true;
                    break;
                }
                a++;
            }
            if (!skip) {
                sortedPhoneBookContacts.add(value2);
            }
        }
        Collections.sort(sortedPhoneBookContacts, new ContactsController$13(this));
        this.phoneBookContacts = sortedPhoneBookContacts;
    }

    private void buildContactsSectionsArrays(boolean sort) {
        if (sort) {
            Collections.sort(this.contacts, new ContactsController$14(this));
        }
        HashMap<String, ArrayList<TLRPC$TL_contact>> sectionsDict = new HashMap();
        ArrayList<String> sortedSectionsArray = new ArrayList();
        for (int a = 0; a < this.contacts.size(); a++) {
            TLRPC$TL_contact value = (TLRPC$TL_contact) this.contacts.get(a);
            User user = MessagesController.getInstance().getUser(Integer.valueOf(value.user_id));
            if (user != null) {
                String key = UserObject.getFirstName(user);
                if (key.length() > 1) {
                    key = key.substring(0, 1);
                }
                if (key.length() == 0) {
                    key = "#";
                } else {
                    key = key.toUpperCase();
                }
                String replace = (String) this.sectionsToReplace.get(key);
                if (replace != null) {
                    key = replace;
                }
                ArrayList<TLRPC$TL_contact> arr = (ArrayList) sectionsDict.get(key);
                if (arr == null) {
                    arr = new ArrayList();
                    sectionsDict.put(key, arr);
                    sortedSectionsArray.add(key);
                }
                arr.add(value);
            }
        }
        Collections.sort(sortedSectionsArray, new ContactsController$15(this));
        this.usersSectionsDict = sectionsDict;
        this.sortedUsersSectionsArray = sortedSectionsArray;
    }

    private boolean hasContactsPermission() {
        if (VERSION.SDK_INT < 23) {
            Cursor cursor = null;
            try {
                cursor = ApplicationLoader.applicationContext.getContentResolver().query(Phone.CONTENT_URI, this.projectionPhones, null, null, null);
                if (cursor == null || cursor.getCount() == 0) {
                    if (cursor != null) {
                        try {
                            cursor.close();
                        } catch (Throwable e) {
                            FileLog.m94e(e);
                        }
                    }
                    return false;
                }
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Throwable e2) {
                        FileLog.m94e(e2);
                    }
                }
                return true;
            } catch (Throwable th) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Throwable e22) {
                        FileLog.m94e(e22);
                    }
                }
            }
        } else if (ApplicationLoader.applicationContext.checkSelfPermission("android.permission.READ_CONTACTS") == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void performWriteContactsToPhoneBookInternal(ArrayList<TLRPC$TL_contact> contactsArray) {
        try {
            if (hasContactsPermission()) {
                Uri rawContactUri = RawContacts.CONTENT_URI.buildUpon().appendQueryParameter("account_name", this.currentAccount.name).appendQueryParameter("account_type", this.currentAccount.type).build();
                Cursor c1 = ApplicationLoader.applicationContext.getContentResolver().query(rawContactUri, new String[]{"_id", "sync2"}, null, null, null);
                HashMap<Integer, Long> bookContacts = new HashMap();
                if (c1 != null) {
                    while (c1.moveToNext()) {
                        bookContacts.put(Integer.valueOf(c1.getInt(1)), Long.valueOf(c1.getLong(0)));
                    }
                    c1.close();
                    for (int a = 0; a < contactsArray.size(); a++) {
                        TLRPC$TL_contact u = (TLRPC$TL_contact) contactsArray.get(a);
                        if (!bookContacts.containsKey(Integer.valueOf(u.user_id))) {
                            addContactToPhoneBook(MessagesController.getInstance().getUser(Integer.valueOf(u.user_id)), false);
                        }
                    }
                }
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
    }

    private void performWriteContactsToPhoneBook() {
        ArrayList<TLRPC$TL_contact> contactsArray = new ArrayList();
        contactsArray.addAll(this.contacts);
        Utilities.phoneBookQueue.postRunnable(new ContactsController$16(this, contactsArray));
    }

    private void applyContactsUpdates(ArrayList<Integer> ids, ConcurrentHashMap<Integer, User> userDict, ArrayList<TLRPC$TL_contact> newC, ArrayList<Integer> contactsTD) {
        int a;
        Integer uid;
        if (newC == null || contactsTD == null) {
            newC = new ArrayList();
            contactsTD = new ArrayList();
            for (a = 0; a < ids.size(); a++) {
                uid = (Integer) ids.get(a);
                if (uid.intValue() > 0) {
                    TLRPC$TL_contact contact = new TLRPC$TL_contact();
                    contact.user_id = uid.intValue();
                    newC.add(contact);
                } else if (uid.intValue() < 0) {
                    contactsTD.add(Integer.valueOf(-uid.intValue()));
                }
            }
        }
        FileLog.m92e("process update - contacts add = " + newC.size() + " delete = " + contactsTD.size());
        StringBuilder toAdd = new StringBuilder();
        StringBuilder toDelete = new StringBuilder();
        boolean reloadContacts = false;
        for (a = 0; a < newC.size(); a++) {
            ContactsController$Contact contact2;
            int index;
            TLRPC$TL_contact newContact = (TLRPC$TL_contact) newC.get(a);
            User user = null;
            if (userDict != null) {
                user = (User) userDict.get(Integer.valueOf(newContact.user_id));
            }
            if (user == null) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(newContact.user_id));
            } else {
                MessagesController.getInstance().putUser(user, true);
            }
            if (user == null || TextUtils.isEmpty(user.phone)) {
                reloadContacts = true;
            } else {
                contact2 = (ContactsController$Contact) this.contactsBookSPhones.get(user.phone);
                if (contact2 != null) {
                    index = contact2.shortPhones.indexOf(user.phone);
                    if (index != -1) {
                        contact2.phoneDeleted.set(index, Integer.valueOf(0));
                    }
                }
                if (toAdd.length() != 0) {
                    toAdd.append(",");
                }
                toAdd.append(user.phone);
            }
        }
        for (a = 0; a < contactsTD.size(); a++) {
            uid = (Integer) contactsTD.get(a);
            Utilities.phoneBookQueue.postRunnable(new ContactsController$17(this, uid));
            user = null;
            if (userDict != null) {
                user = (User) userDict.get(uid);
            }
            if (user == null) {
                user = MessagesController.getInstance().getUser(uid);
            } else {
                MessagesController.getInstance().putUser(user, true);
            }
            if (user == null) {
                reloadContacts = true;
            } else if (!TextUtils.isEmpty(user.phone)) {
                contact2 = (ContactsController$Contact) this.contactsBookSPhones.get(user.phone);
                if (contact2 != null) {
                    index = contact2.shortPhones.indexOf(user.phone);
                    if (index != -1) {
                        contact2.phoneDeleted.set(index, Integer.valueOf(1));
                    }
                }
                if (toDelete.length() != 0) {
                    toDelete.append(",");
                }
                toDelete.append(user.phone);
            }
        }
        if (!(toAdd.length() == 0 && toDelete.length() == 0)) {
            MessagesStorage.getInstance().applyPhoneBookUpdates(toAdd.toString(), toDelete.toString());
        }
        if (reloadContacts) {
            Utilities.stageQueue.postRunnable(new ContactsController$18(this));
        } else {
            AndroidUtilities.runOnUIThread(new ContactsController$19(this, newC, contactsTD));
        }
    }

    public void processContactsUpdates(ArrayList<Integer> ids, ConcurrentHashMap<Integer, User> userDict) {
        ArrayList<TLRPC$TL_contact> newContacts = new ArrayList();
        ArrayList<Integer> contactsToDelete = new ArrayList();
        Iterator it = ids.iterator();
        while (it.hasNext()) {
            Integer uid = (Integer) it.next();
            int idx;
            if (uid.intValue() > 0) {
                TLRPC$TL_contact contact = new TLRPC$TL_contact();
                contact.user_id = uid.intValue();
                newContacts.add(contact);
                if (!this.delayedContactsUpdate.isEmpty()) {
                    idx = this.delayedContactsUpdate.indexOf(Integer.valueOf(-uid.intValue()));
                    if (idx != -1) {
                        this.delayedContactsUpdate.remove(idx);
                    }
                }
            } else if (uid.intValue() < 0) {
                contactsToDelete.add(Integer.valueOf(-uid.intValue()));
                if (!this.delayedContactsUpdate.isEmpty()) {
                    idx = this.delayedContactsUpdate.indexOf(Integer.valueOf(-uid.intValue()));
                    if (idx != -1) {
                        this.delayedContactsUpdate.remove(idx);
                    }
                }
            }
        }
        if (!contactsToDelete.isEmpty()) {
            MessagesStorage.getInstance().deleteContacts(contactsToDelete);
        }
        if (!newContacts.isEmpty()) {
            MessagesStorage.getInstance().putContacts(newContacts, false);
        }
        if (this.contactsLoaded && this.contactsBookLoaded) {
            applyContactsUpdates(ids, userDict, newContacts, contactsToDelete);
            return;
        }
        this.delayedContactsUpdate.addAll(ids);
        FileLog.m92e("delay update - contacts add = " + newContacts.size() + " delete = " + contactsToDelete.size());
    }

    public long addContactToPhoneBook(User user, boolean check) {
        long j = -1;
        if (!(this.currentAccount == null || user == null || TextUtils.isEmpty(user.phone) || !hasContactsPermission())) {
            j = -1;
            synchronized (this.observerLock) {
                this.ignoreChanges = true;
            }
            ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
            if (check) {
                try {
                    contentResolver.delete(RawContacts.CONTENT_URI.buildUpon().appendQueryParameter("caller_is_syncadapter", "true").appendQueryParameter("account_name", this.currentAccount.name).appendQueryParameter("account_type", this.currentAccount.type).build(), "sync2 = " + user.id, null);
                } catch (Throwable e) {
                    FileLog.m94e(e);
                }
            }
            ArrayList<ContentProviderOperation> query = new ArrayList();
            Builder builder = ContentProviderOperation.newInsert(RawContacts.CONTENT_URI);
            builder.withValue("account_name", this.currentAccount.name);
            builder.withValue("account_type", this.currentAccount.type);
            builder.withValue("sync1", user.phone);
            builder.withValue("sync2", Integer.valueOf(user.id));
            query.add(builder.build());
            builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", 0);
            builder.withValue("mimetype", "vnd.android.cursor.item/name");
            builder.withValue("data2", user.first_name);
            builder.withValue("data3", user.last_name);
            query.add(builder.build());
            builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", 0);
            builder.withValue("mimetype", "vnd.android.cursor.item/vnd.org.telegram.messenger.android.profile");
            builder.withValue("data1", Integer.valueOf(user.id));
            builder.withValue("data2", "Telegram Profile");
            builder.withValue("data3", "+" + user.phone);
            builder.withValue("data4", Integer.valueOf(user.id));
            query.add(builder.build());
            try {
                ContentProviderResult[] result = contentResolver.applyBatch("com.android.contacts", query);
                if (!(result == null || result.length <= 0 || result[0].uri == null)) {
                    j = Long.parseLong(result[0].uri.getLastPathSegment());
                }
            } catch (Throwable e2) {
                FileLog.m94e(e2);
            }
            synchronized (this.observerLock) {
                this.ignoreChanges = false;
            }
        }
        return j;
    }

    private void deleteContactFromPhoneBook(int uid) {
        if (hasContactsPermission()) {
            synchronized (this.observerLock) {
                this.ignoreChanges = true;
            }
            try {
                ApplicationLoader.applicationContext.getContentResolver().delete(RawContacts.CONTENT_URI.buildUpon().appendQueryParameter("caller_is_syncadapter", "true").appendQueryParameter("account_name", this.currentAccount.name).appendQueryParameter("account_type", this.currentAccount.type).build(), "sync2 = " + uid, null);
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
            synchronized (this.observerLock) {
                this.ignoreChanges = false;
            }
        }
    }

    protected void markAsContacted(String contactId) {
        if (contactId != null) {
            Utilities.phoneBookQueue.postRunnable(new ContactsController$20(this, contactId));
        }
    }

    public void addContact(User user) {
        if (user != null && !TextUtils.isEmpty(user.phone)) {
            TLRPC$TL_contacts_importContacts req = new TLRPC$TL_contacts_importContacts();
            ArrayList<TLRPC$TL_inputPhoneContact> contactsParams = new ArrayList();
            TLRPC$TL_inputPhoneContact c = new TLRPC$TL_inputPhoneContact();
            c.phone = user.phone;
            if (!c.phone.startsWith("+")) {
                c.phone = "+" + c.phone;
            }
            c.first_name = user.first_name;
            c.last_name = user.last_name;
            c.client_id = 0;
            contactsParams.add(c);
            req.contacts = contactsParams;
            ConnectionsManager.getInstance().sendRequest(req, new ContactsController$21(this), 6);
        }
    }

    public void deleteContact(ArrayList<User> users) {
        if (users != null && !users.isEmpty()) {
            TLRPC$TL_contacts_deleteContacts req = new TLRPC$TL_contacts_deleteContacts();
            ArrayList<Integer> uids = new ArrayList();
            Iterator it = users.iterator();
            while (it.hasNext()) {
                User user = (User) it.next();
                TLRPC$InputUser inputUser = MessagesController.getInputUser(user);
                if (inputUser != null) {
                    uids.add(Integer.valueOf(user.id));
                    req.id.add(inputUser);
                }
            }
            ConnectionsManager.getInstance().sendRequest(req, new ContactsController$22(this, uids, users));
        }
    }

    public void reloadContactsStatuses() {
        saveContactsLoadTime();
        MessagesController.getInstance().clearFullUsers();
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("needGetStatuses", true).commit();
        ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_contacts_getStatuses(), new ContactsController$23(this, editor));
    }

    public void loadPrivacySettings() {
        if (this.loadingDeleteInfo == 0) {
            this.loadingDeleteInfo = 1;
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_account_getAccountTTL(), new ContactsController$24(this));
        }
        if (this.loadingLastSeenInfo == 0) {
            this.loadingLastSeenInfo = 1;
            TLRPC$TL_account_getPrivacy req = new TLRPC$TL_account_getPrivacy();
            req.key = new TLRPC$TL_inputPrivacyKeyStatusTimestamp();
            ConnectionsManager.getInstance().sendRequest(req, new ContactsController$25(this));
        }
        if (this.loadingCallsInfo == 0) {
            this.loadingCallsInfo = 1;
            req = new TLRPC$TL_account_getPrivacy();
            req.key = new TLRPC$TL_inputPrivacyKeyPhoneCall();
            ConnectionsManager.getInstance().sendRequest(req, new ContactsController$26(this));
        }
        if (this.loadingGroupInfo == 0) {
            this.loadingGroupInfo = 1;
            req = new TLRPC$TL_account_getPrivacy();
            req.key = new TLRPC$TL_inputPrivacyKeyChatInvite();
            ConnectionsManager.getInstance().sendRequest(req, new ContactsController$27(this));
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.privacyRulesUpdated, new Object[0]);
    }

    public void setDeleteAccountTTL(int ttl) {
        this.deleteAccountTTL = ttl;
    }

    public int getDeleteAccountTTL() {
        return this.deleteAccountTTL;
    }

    public boolean getLoadingDeleteInfo() {
        return this.loadingDeleteInfo != 2;
    }

    public boolean getLoadingLastSeenInfo() {
        return this.loadingLastSeenInfo != 2;
    }

    public boolean getLoadingCallsInfo() {
        return this.loadingCallsInfo != 2;
    }

    public boolean getLoadingGroupInfo() {
        return this.loadingGroupInfo != 2;
    }

    public ArrayList<TLRPC$PrivacyRule> getPrivacyRules(int type) {
        if (type == 2) {
            return this.callPrivacyRules;
        }
        if (type == 1) {
            return this.groupPrivacyRules;
        }
        return this.privacyRules;
    }

    public void setPrivacyRules(ArrayList<TLRPC$PrivacyRule> rules, int type) {
        if (type == 2) {
            this.callPrivacyRules = rules;
        } else if (type == 1) {
            this.groupPrivacyRules = rules;
        } else {
            this.privacyRules = rules;
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.privacyRulesUpdated, new Object[0]);
        reloadContactsStatuses();
    }

    public static String formatName(String firstName, String lastName) {
        int length;
        int i = 0;
        if (firstName != null) {
            firstName = firstName.trim();
        }
        if (lastName != null) {
            lastName = lastName.trim();
        }
        if (firstName != null) {
            length = firstName.length();
        } else {
            length = 0;
        }
        if (lastName != null) {
            i = lastName.length();
        }
        StringBuilder result = new StringBuilder((i + length) + 1);
        if (LocaleController.nameDisplayOrder == 1) {
            if (firstName != null && firstName.length() > 0) {
                result.append(firstName);
                if (lastName != null && lastName.length() > 0) {
                    result.append(" ");
                    result.append(lastName);
                }
            } else if (lastName != null && lastName.length() > 0) {
                result.append(lastName);
            }
        } else if (lastName != null && lastName.length() > 0) {
            result.append(lastName);
            if (firstName != null && firstName.length() > 0) {
                result.append(" ");
                result.append(firstName);
            }
        } else if (firstName != null && firstName.length() > 0) {
            result.append(firstName);
        }
        return result.toString();
    }
}
