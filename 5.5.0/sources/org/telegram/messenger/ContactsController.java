package org.telegram.messenger;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.dynamite.ProviderConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC$TL_contactStatus;
import org.telegram.tgnet.TLRPC$TL_contacts_contactsNotModified;
import org.telegram.tgnet.TLRPC$TL_contacts_deleteContacts;
import org.telegram.tgnet.TLRPC$TL_contacts_getContacts;
import org.telegram.tgnet.TLRPC$TL_contacts_getStatuses;
import org.telegram.tgnet.TLRPC$TL_contacts_importContacts;
import org.telegram.tgnet.TLRPC$TL_contacts_importedContacts;
import org.telegram.tgnet.TLRPC$TL_contacts_resetSaved;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_help_getInviteText;
import org.telegram.tgnet.TLRPC$TL_help_inviteText;
import org.telegram.tgnet.TLRPC$TL_importedContact;
import org.telegram.tgnet.TLRPC$TL_inputPhoneContact;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyKeyChatInvite;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyKeyPhoneCall;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyKeyStatusTimestamp;
import org.telegram.tgnet.TLRPC$TL_popularContact;
import org.telegram.tgnet.TLRPC$TL_user;
import org.telegram.tgnet.TLRPC$TL_userStatusLastMonth;
import org.telegram.tgnet.TLRPC$TL_userStatusLastWeek;
import org.telegram.tgnet.TLRPC$TL_userStatusRecently;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC$contacts_Contacts;
import org.telegram.tgnet.TLRPC.InputUser;
import org.telegram.tgnet.TLRPC.PrivacyRule;
import org.telegram.tgnet.TLRPC.TL_accountDaysTTL;
import org.telegram.tgnet.TLRPC.TL_account_getAccountTTL;
import org.telegram.tgnet.TLRPC.TL_account_getPrivacy;
import org.telegram.tgnet.TLRPC.TL_account_privacyRules;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ChatActivity;
import utils.p178a.C3791b;

public class ContactsController {
    private static volatile ContactsController Instance = null;
    private static final Object loadContactsSync = new Object();
    private ArrayList<PrivacyRule> callPrivacyRules;
    private int completedRequestsCount;
    public ArrayList<TLRPC$TL_contact> contacts = new ArrayList();
    public ArrayList<TLRPC$TL_contact> contacts1 = new ArrayList();
    public HashMap<String, Contact> contactsBook = new HashMap();
    private boolean contactsBookLoaded;
    public HashMap<String, Contact> contactsBookSPhones = new HashMap();
    public HashMap<String, TLRPC$TL_contact> contactsByPhone = new HashMap();
    public HashMap<String, TLRPC$TL_contact> contactsByShortPhone = new HashMap();
    public ConcurrentHashMap<Integer, TLRPC$TL_contact> contactsDict = new ConcurrentHashMap(20, 1.0f, 2);
    public SparseArray<TLRPC$TL_contact> contactsDict1 = new SparseArray();
    public boolean contactsLoaded;
    private boolean contactsSyncInProgress;
    private Account currentAccount;
    private ArrayList<Integer> delayedContactsUpdate = new ArrayList();
    private int deleteAccountTTL;
    private ArrayList<PrivacyRule> groupPrivacyRules;
    private boolean ignoreChanges;
    private String inviteLink;
    private String lastContactsVersions = TtmlNode.ANONYMOUS_REGION_ID;
    private int loadingCallsInfo;
    private boolean loadingContacts;
    private int loadingDeleteInfo;
    private int loadingGroupInfo;
    private int loadingLastSeenInfo;
    private boolean migratingContacts;
    private final Object observerLock = new Object();
    public ArrayList<Contact> phoneBookContacts = new ArrayList();
    private ArrayList<PrivacyRule> privacyRules;
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

    /* renamed from: org.telegram.messenger.ContactsController$1 */
    class C29901 implements Runnable {
        C29901() {
        }

        public void run() {
            ContactsController.this.migratingContacts = false;
            ContactsController.this.completedRequestsCount = 0;
        }
    }

    /* renamed from: org.telegram.messenger.ContactsController$2 */
    class C30012 implements RequestDelegate {
        C30012() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLObject != null) {
                final TLRPC$TL_help_inviteText tLRPC$TL_help_inviteText = (TLRPC$TL_help_inviteText) tLObject;
                if (tLRPC$TL_help_inviteText.message.length() != 0) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            ContactsController.this.updatingInviteLink = false;
                            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                            edit.putString("invitelink", ContactsController.this.inviteLink = tLRPC$TL_help_inviteText.message);
                            edit.putInt("invitelinktime", (int) (System.currentTimeMillis() / 1000));
                            edit.commit();
                        }
                    });
                }
            }
        }
    }

    /* renamed from: org.telegram.messenger.ContactsController$3 */
    class C30023 implements Runnable {
        C30023() {
        }

        public void run() {
            if (ContactsController.this.checkContactsInternal()) {
                FileLog.m13726e("detected contacts change");
                ContactsController.getInstance().performSyncPhoneBook(ContactsController.getInstance().getContactsCopy(ContactsController.getInstance().contactsBook), true, false, true, false, true, false);
            }
        }
    }

    /* renamed from: org.telegram.messenger.ContactsController$4 */
    class C30034 implements Runnable {
        C30034() {
        }

        public void run() {
            ContactsController.getInstance().performSyncPhoneBook(new HashMap(), true, true, true, true, false, false);
        }
    }

    /* renamed from: org.telegram.messenger.ContactsController$6 */
    class C30056 implements RequestDelegate {
        C30056() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        }
    }

    /* renamed from: org.telegram.messenger.ContactsController$7 */
    class C30067 implements Runnable {
        C30067() {
        }

        public void run() {
            if (!ContactsController.this.contacts.isEmpty() || ContactsController.this.contactsLoaded) {
                synchronized (ContactsController.loadContactsSync) {
                    ContactsController.this.loadingContacts = false;
                }
                return;
            }
            ContactsController.this.loadContacts(true, 0);
        }
    }

    public static class Contact {
        public int contact_id;
        public String first_name;
        public int imported;
        public String key;
        public String last_name;
        public ArrayList<Integer> phoneDeleted = new ArrayList();
        public ArrayList<String> phoneTypes = new ArrayList();
        public ArrayList<String> phones = new ArrayList();
        public ArrayList<String> shortPhones = new ArrayList();
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

    private void applyContactsUpdates(ArrayList<Integer> arrayList, ConcurrentHashMap<Integer, User> concurrentHashMap, ArrayList<TLRPC$TL_contact> arrayList2, ArrayList<Integer> arrayList3) {
        ArrayList arrayList4;
        ArrayList arrayList5;
        Integer num;
        Contact contact;
        int indexOf;
        if (arrayList2 == null || arrayList3 == null) {
            arrayList4 = new ArrayList();
            arrayList5 = new ArrayList();
            for (int i = 0; i < arrayList.size(); i++) {
                num = (Integer) arrayList.get(i);
                if (num.intValue() > 0) {
                    TLRPC$TL_contact tLRPC$TL_contact = new TLRPC$TL_contact();
                    tLRPC$TL_contact.user_id = num.intValue();
                    arrayList4.add(tLRPC$TL_contact);
                } else if (num.intValue() < 0) {
                    arrayList5.add(Integer.valueOf(-num.intValue()));
                }
            }
        }
        FileLog.m13726e("process update - contacts add = " + arrayList4.size() + " delete = " + arrayList5.size());
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        int i2 = 0;
        Object obj = null;
        while (i2 < arrayList4.size()) {
            Object obj2;
            TLRPC$TL_contact tLRPC$TL_contact2 = (TLRPC$TL_contact) arrayList4.get(i2);
            User user = null;
            if (concurrentHashMap != null) {
                user = (User) concurrentHashMap.get(Integer.valueOf(tLRPC$TL_contact2.user_id));
            }
            if (user == null) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contact2.user_id));
            } else {
                MessagesController.getInstance().putUser(user, true);
            }
            if (user == null || TextUtils.isEmpty(user.phone)) {
                obj2 = 1;
            } else {
                contact = (Contact) this.contactsBookSPhones.get(user.phone);
                if (contact != null) {
                    indexOf = contact.shortPhones.indexOf(user.phone);
                    if (indexOf != -1) {
                        contact.phoneDeleted.set(indexOf, Integer.valueOf(0));
                    }
                }
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(user.phone);
                obj2 = obj;
            }
            i2++;
            obj = obj2;
        }
        for (i2 = 0; i2 < arrayList5.size(); i2++) {
            num = (Integer) arrayList5.get(i2);
            Utilities.phoneBookQueue.postRunnable(new Runnable() {
                public void run() {
                    ContactsController.this.deleteContactFromPhoneBook(num.intValue());
                }
            });
            user = null;
            if (concurrentHashMap != null) {
                user = (User) concurrentHashMap.get(num);
            }
            if (user == null) {
                user = MessagesController.getInstance().getUser(num);
            } else {
                MessagesController.getInstance().putUser(user, true);
            }
            if (user == null) {
                obj = 1;
            } else if (!TextUtils.isEmpty(user.phone)) {
                contact = (Contact) this.contactsBookSPhones.get(user.phone);
                if (contact != null) {
                    indexOf = contact.shortPhones.indexOf(user.phone);
                    if (indexOf != -1) {
                        contact.phoneDeleted.set(indexOf, Integer.valueOf(1));
                    }
                }
                if (stringBuilder2.length() != 0) {
                    stringBuilder2.append(",");
                }
                stringBuilder2.append(user.phone);
            }
        }
        if (!(stringBuilder.length() == 0 && stringBuilder2.length() == 0)) {
            MessagesStorage.getInstance().applyPhoneBookUpdates(stringBuilder.toString(), stringBuilder2.toString());
        }
        if (obj != null) {
            Utilities.stageQueue.postRunnable(new Runnable() {
                public void run() {
                    ContactsController.this.loadContacts(false, 0);
                }
            });
        } else {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    boolean z = true;
                    for (int i = 0; i < arrayList4.size(); i++) {
                        TLRPC$TL_contact tLRPC$TL_contact = (TLRPC$TL_contact) arrayList4.get(i);
                        if (ContactsController.this.contactsDict.get(Integer.valueOf(tLRPC$TL_contact.user_id)) == null) {
                            ContactsController.this.contacts.add(tLRPC$TL_contact);
                            ContactsController.this.contactsDict.put(Integer.valueOf(tLRPC$TL_contact.user_id), tLRPC$TL_contact);
                        }
                    }
                    for (int i2 = 0; i2 < arrayList5.size(); i2++) {
                        Integer num = (Integer) arrayList5.get(i2);
                        TLRPC$TL_contact tLRPC$TL_contact2 = (TLRPC$TL_contact) ContactsController.this.contactsDict.get(num);
                        if (tLRPC$TL_contact2 != null) {
                            ContactsController.this.contacts.remove(tLRPC$TL_contact2);
                            ContactsController.this.contactsDict.remove(num);
                        }
                    }
                    if (!arrayList4.isEmpty()) {
                        ContactsController.this.updateUnregisteredContacts(ContactsController.this.contacts);
                        ContactsController.this.performWriteContactsToPhoneBook();
                    }
                    ContactsController.this.performSyncPhoneBook(ContactsController.this.getContactsCopy(ContactsController.this.contactsBook), false, false, false, false, true, false);
                    ContactsController contactsController = ContactsController.this;
                    if (arrayList4.isEmpty()) {
                        z = false;
                    }
                    contactsController.buildContactsSectionsArrays(z);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsDidLoaded, new Object[0]);
                }
            });
        }
    }

    private void buildContactsSectionsArrays(boolean z) {
        if (z) {
            Collections.sort(this.contacts, new Comparator<TLRPC$TL_contact>() {
                public int compare(TLRPC$TL_contact tLRPC$TL_contact, TLRPC$TL_contact tLRPC$TL_contact2) {
                    return UserObject.getFirstName(MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contact.user_id))).compareTo(UserObject.getFirstName(MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contact2.user_id))));
                }
            });
        }
        HashMap hashMap = new HashMap();
        Object arrayList = new ArrayList();
        for (int i = 0; i < this.contacts.size(); i++) {
            TLRPC$TL_contact tLRPC$TL_contact = (TLRPC$TL_contact) this.contacts.get(i);
            User user = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contact.user_id));
            if (user != null) {
                Object obj;
                String firstName = UserObject.getFirstName(user);
                if (firstName.length() > 1) {
                    firstName = firstName.substring(0, 1);
                }
                if (firstName.length() == 0) {
                    obj = "#";
                } else {
                    String toUpperCase = firstName.toUpperCase();
                }
                firstName = (String) this.sectionsToReplace.get(obj);
                if (firstName != null) {
                    obj = firstName;
                }
                ArrayList arrayList2 = (ArrayList) hashMap.get(obj);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                    hashMap.put(obj, arrayList2);
                    arrayList.add(obj);
                }
                arrayList2.add(tLRPC$TL_contact);
            }
        }
        Collections.sort(arrayList, new Comparator<String>() {
            public int compare(String str, String str2) {
                return str.charAt(0) == '#' ? 1 : str2.charAt(0) == '#' ? -1 : str.compareTo(str2);
            }
        });
        this.usersSectionsDict = hashMap;
        this.sortedUsersSectionsArray = arrayList;
    }

    private boolean checkContactsInternal() {
        Cursor query;
        Cursor cursor;
        Throwable th;
        Throwable e;
        boolean z;
        boolean z2 = false;
        try {
            if (!hasContactsPermission()) {
                return false;
            }
            ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
            try {
                query = contentResolver.query(RawContacts.CONTENT_URI, new String[]{ProviderConstants.API_COLNAME_FEATURE_VERSION}, null, null, null);
                if (query != null) {
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        while (query.moveToNext()) {
                            stringBuilder.append(query.getString(query.getColumnIndex(ProviderConstants.API_COLNAME_FEATURE_VERSION)));
                        }
                        String stringBuilder2 = stringBuilder.toString();
                        if (!(this.lastContactsVersions.length() == 0 || this.lastContactsVersions.equals(stringBuilder2))) {
                            z2 = true;
                        }
                        this.lastContactsVersions = stringBuilder2;
                    } catch (Throwable e2) {
                        cursor = query;
                        th = e2;
                        z = false;
                        try {
                            FileLog.m13728e(th);
                            if (cursor != null) {
                                return z;
                            }
                            try {
                                cursor.close();
                                return z;
                            } catch (Exception e3) {
                                th = e3;
                                FileLog.m13728e(th);
                                return z;
                            }
                        } catch (Throwable th2) {
                            z2 = z;
                            e2 = th2;
                            query = cursor;
                            if (query != null) {
                                query.close();
                            }
                            throw e2;
                        }
                    } catch (Throwable th3) {
                        e2 = th3;
                        if (query != null) {
                            query.close();
                        }
                        throw e2;
                    }
                }
                z = z2;
                if (query == null) {
                    return z;
                }
                query.close();
                return z;
            } catch (Throwable e22) {
                th2 = e22;
                cursor = null;
                z = false;
                FileLog.m13728e(th2);
                if (cursor != null) {
                    return z;
                }
                cursor.close();
                return z;
            } catch (Throwable th4) {
                e22 = th4;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw e22;
            }
        } catch (Throwable e222) {
            th2 = e222;
            z = z2;
            FileLog.m13728e(th2);
            return z;
        }
    }

    private void deleteContactFromPhoneBook(int i) {
        if (hasContactsPermission()) {
            synchronized (this.observerLock) {
                this.ignoreChanges = true;
            }
            try {
                ApplicationLoader.applicationContext.getContentResolver().delete(RawContacts.CONTENT_URI.buildUpon().appendQueryParameter("caller_is_syncadapter", "true").appendQueryParameter(Constants.KEY_ACCOUNT_NAME, this.currentAccount.name).appendQueryParameter("account_type", this.currentAccount.type).build(), "sync2 = " + i, null);
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
            synchronized (this.observerLock) {
                this.ignoreChanges = false;
            }
        }
    }

    public static String formatName(String str, String str2) {
        int i = 0;
        if (str != null) {
            str = str.trim();
        }
        if (str2 != null) {
            str2 = str2.trim();
        }
        int length = str != null ? str.length() : 0;
        if (str2 != null) {
            i = str2.length();
        }
        StringBuilder stringBuilder = new StringBuilder((i + length) + 1);
        if (LocaleController.nameDisplayOrder == 1) {
            if (str != null && str.length() > 0) {
                stringBuilder.append(str);
                if (str2 != null && str2.length() > 0) {
                    stringBuilder.append(" ");
                    stringBuilder.append(str2);
                }
            } else if (str2 != null && str2.length() > 0) {
                stringBuilder.append(str2);
            }
        } else if (str2 != null && str2.length() > 0) {
            stringBuilder.append(str2);
            if (str != null && str.length() > 0) {
                stringBuilder.append(" ");
                stringBuilder.append(str);
            }
        } else if (str != null && str.length() > 0) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    private int getContactsHash(ArrayList<TLRPC$TL_contact> arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList);
        Collections.sort(arrayList2, new Comparator<TLRPC$TL_contact>() {
            public int compare(TLRPC$TL_contact tLRPC$TL_contact, TLRPC$TL_contact tLRPC$TL_contact2) {
                return tLRPC$TL_contact.user_id > tLRPC$TL_contact2.user_id ? 1 : tLRPC$TL_contact.user_id < tLRPC$TL_contact2.user_id ? -1 : 0;
            }
        });
        int size = arrayList2.size();
        int i = -1;
        long j = 0;
        while (i < size) {
            long j2;
            if (i == -1) {
                j2 = (((j * 20261) + 2147483648L) + ((long) UserConfig.contactsSavedCount)) % 2147483648L;
            } else {
                j2 = (((long) ((TLRPC$TL_contact) arrayList2.get(i)).user_id) + ((j * 20261) + 2147483648L)) % 2147483648L;
            }
            i++;
            j = j2;
        }
        return (int) j;
    }

    public static ContactsController getInstance() {
        ContactsController contactsController = Instance;
        if (contactsController == null) {
            synchronized (ContactsController.class) {
                contactsController = Instance;
                if (contactsController == null) {
                    contactsController = new ContactsController();
                    Instance = contactsController;
                }
            }
        }
        return contactsController;
    }

    private boolean hasContactsPermission() {
        Cursor query;
        Throwable e;
        Cursor cursor = null;
        if (VERSION.SDK_INT >= 23) {
            return ApplicationLoader.applicationContext.checkSelfPermission("android.permission.READ_CONTACTS") == 0;
        } else {
            try {
                query = ApplicationLoader.applicationContext.getContentResolver().query(Phone.CONTENT_URI, this.projectionPhones, null, null, null);
                if (query != null) {
                    try {
                        if (query.getCount() != 0) {
                            if (query != null) {
                                try {
                                    query.close();
                                } catch (Throwable e2) {
                                    FileLog.m13728e(e2);
                                }
                            }
                            return true;
                        }
                    } catch (Throwable th) {
                        e2 = th;
                        try {
                            FileLog.m13728e(e2);
                            if (query != null) {
                                try {
                                    query.close();
                                } catch (Throwable e22) {
                                    FileLog.m13728e(e22);
                                }
                            }
                            return true;
                        } catch (Throwable th2) {
                            e22 = th2;
                            cursor = query;
                            if (cursor != null) {
                                try {
                                    cursor.close();
                                } catch (Throwable e3) {
                                    FileLog.m13728e(e3);
                                }
                            }
                            throw e22;
                        }
                    }
                }
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable e222) {
                        FileLog.m13728e(e222);
                    }
                }
                return false;
            } catch (Throwable th3) {
                e222 = th3;
                if (cursor != null) {
                    cursor.close();
                }
                throw e222;
            }
        }
    }

    private void performWriteContactsToPhoneBook() {
        final ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.contacts);
        Utilities.phoneBookQueue.postRunnable(new Runnable() {
            public void run() {
                ContactsController.this.performWriteContactsToPhoneBookInternal(arrayList);
            }
        });
    }

    private void performWriteContactsToPhoneBookInternal(ArrayList<TLRPC$TL_contact> arrayList) {
        try {
            if (hasContactsPermission()) {
                Uri build = RawContacts.CONTENT_URI.buildUpon().appendQueryParameter(Constants.KEY_ACCOUNT_NAME, this.currentAccount.name).appendQueryParameter("account_type", this.currentAccount.type).build();
                Cursor query = ApplicationLoader.applicationContext.getContentResolver().query(build, new String[]{"_id", "sync2"}, null, null, null);
                HashMap hashMap = new HashMap();
                if (query != null) {
                    while (query.moveToNext()) {
                        hashMap.put(Integer.valueOf(query.getInt(1)), Long.valueOf(query.getLong(0)));
                    }
                    query.close();
                    for (int i = 0; i < arrayList.size(); i++) {
                        TLRPC$TL_contact tLRPC$TL_contact = (TLRPC$TL_contact) arrayList.get(i);
                        if (!hashMap.containsKey(Integer.valueOf(tLRPC$TL_contact.user_id))) {
                            addContactToPhoneBook(MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contact.user_id)), false);
                        }
                    }
                }
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.HashMap<java.lang.String, org.telegram.messenger.ContactsController.Contact> readContactsFromPhoneBook() {
        /*
        r12 = this;
        r6 = new java.util.HashMap;
        r6.<init>();
        r7 = 0;
        r0 = r12.hasContactsPermission();	 Catch:{ Exception -> 0x0277, all -> 0x026f }
        if (r0 != 0) goto L_0x0018;
    L_0x000c:
        if (r7 == 0) goto L_0x0011;
    L_0x000e:
        r7.close();	 Catch:{ Exception -> 0x0013 }
    L_0x0011:
        r0 = r6;
    L_0x0012:
        return r0;
    L_0x0013:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);
        goto L_0x0011;
    L_0x0018:
        r0 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0277, all -> 0x026f }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0277, all -> 0x026f }
        r8 = new java.util.HashMap;	 Catch:{ Exception -> 0x0277, all -> 0x026f }
        r8.<init>();	 Catch:{ Exception -> 0x0277, all -> 0x026f }
        r9 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0277, all -> 0x026f }
        r9.<init>();	 Catch:{ Exception -> 0x0277, all -> 0x026f }
        r1 = android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI;	 Catch:{ Exception -> 0x0277, all -> 0x026f }
        r2 = r12.projectionPhones;	 Catch:{ Exception -> 0x0277, all -> 0x026f }
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r2 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0277, all -> 0x026f }
        r4 = 1;
        if (r2 == 0) goto L_0x0285;
    L_0x0036:
        r1 = r2.getCount();	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        if (r1 <= 0) goto L_0x0164;
    L_0x003c:
        r1 = r2.moveToNext();	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        if (r1 == 0) goto L_0x0164;
    L_0x0042:
        r1 = 1;
        r1 = r2.getString(r1);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r3 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        if (r3 != 0) goto L_0x003c;
    L_0x004d:
        r3 = 1;
        r7 = org.telegram.p149a.C2488b.m12188a(r1, r3);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1 = android.text.TextUtils.isEmpty(r7);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        if (r1 != 0) goto L_0x003c;
    L_0x0058:
        r1 = "+";
        r1 = r7.startsWith(r1);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        if (r1 == 0) goto L_0x0282;
    L_0x0061:
        r1 = 1;
        r1 = r7.substring(r1);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r5 = r1;
    L_0x0067:
        r1 = r8.containsKey(r5);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        if (r1 != 0) goto L_0x003c;
    L_0x006d:
        r1 = 0;
        r10 = r2.getString(r1);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1.<init>();	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r3 = "'";
        r1 = r1.append(r3);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1 = r1.append(r10);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r3 = "'";
        r1 = r1.append(r3);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r3 = r9.contains(r1);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        if (r3 != 0) goto L_0x0096;
    L_0x0093:
        r9.add(r1);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
    L_0x0096:
        r1 = 2;
        r11 = r2.getInt(r1);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1 = r6.get(r10);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1 = (org.telegram.messenger.ContactsController.Contact) r1;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        if (r1 != 0) goto L_0x027f;
    L_0x00a3:
        r1 = new org.telegram.messenger.ContactsController$Contact;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1.<init>();	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r3 = "";
        r1.first_name = r3;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r3 = "";
        r1.last_name = r3;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1.key = r10;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r3 = r4 + 1;
        r1.contact_id = r4;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r6.put(r10, r1);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r4 = r3;
        r3 = r1;
    L_0x00bd:
        r1 = r3.shortPhones;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1.add(r5);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1 = r3.phones;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1.add(r7);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1 = r3.phoneDeleted;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r7 = 0;
        r7 = java.lang.Integer.valueOf(r7);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1.add(r7);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        if (r11 != 0) goto L_0x00ff;
    L_0x00d3:
        r1 = 3;
        r1 = r2.getString(r1);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r7 = r3.phoneTypes;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        if (r1 == 0) goto L_0x00f4;
    L_0x00dc:
        r7.add(r1);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
    L_0x00df:
        r8.put(r5, r3);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        goto L_0x003c;
    L_0x00e4:
        r0 = move-exception;
        r1 = r2;
    L_0x00e6:
        org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ all -> 0x0249 }
        r6.clear();	 Catch:{ all -> 0x0249 }
        if (r1 == 0) goto L_0x00f1;
    L_0x00ee:
        r1.close();	 Catch:{ Exception -> 0x025e }
    L_0x00f1:
        r0 = r6;
        goto L_0x0012;
    L_0x00f4:
        r1 = "PhoneMobile";
        r10 = 2131232145; // 0x7f080591 float:1.808039E38 double:1.052968586E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r10);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        goto L_0x00dc;
    L_0x00ff:
        r1 = 1;
        if (r11 != r1) goto L_0x0119;
    L_0x0102:
        r1 = r3.phoneTypes;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r7 = "PhoneHome";
        r10 = 2131232143; // 0x7f08058f float:1.8080387E38 double:1.052968585E-314;
        r7 = org.telegram.messenger.LocaleController.getString(r7, r10);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1.add(r7);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        goto L_0x00df;
    L_0x0112:
        r0 = move-exception;
    L_0x0113:
        if (r2 == 0) goto L_0x0118;
    L_0x0115:
        r2.close();	 Catch:{ Exception -> 0x0264 }
    L_0x0118:
        throw r0;
    L_0x0119:
        r1 = 2;
        if (r11 != r1) goto L_0x012c;
    L_0x011c:
        r1 = r3.phoneTypes;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r7 = "PhoneMobile";
        r10 = 2131232145; // 0x7f080591 float:1.808039E38 double:1.052968586E-314;
        r7 = org.telegram.messenger.LocaleController.getString(r7, r10);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1.add(r7);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        goto L_0x00df;
    L_0x012c:
        r1 = 3;
        if (r11 != r1) goto L_0x013f;
    L_0x012f:
        r1 = r3.phoneTypes;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r7 = "PhoneWork";
        r10 = 2131232152; // 0x7f080598 float:1.8080405E38 double:1.0529685896E-314;
        r7 = org.telegram.messenger.LocaleController.getString(r7, r10);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1.add(r7);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        goto L_0x00df;
    L_0x013f:
        r1 = 12;
        if (r11 != r1) goto L_0x0153;
    L_0x0143:
        r1 = r3.phoneTypes;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r7 = "PhoneMain";
        r10 = 2131232144; // 0x7f080590 float:1.8080389E38 double:1.0529685857E-314;
        r7 = org.telegram.messenger.LocaleController.getString(r7, r10);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1.add(r7);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        goto L_0x00df;
    L_0x0153:
        r1 = r3.phoneTypes;	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r7 = "PhoneOther";
        r10 = 2131232150; // 0x7f080596 float:1.8080401E38 double:1.0529685886E-314;
        r7 = org.telegram.messenger.LocaleController.getString(r7, r10);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        r1.add(r7);	 Catch:{ Exception -> 0x00e4, all -> 0x0112 }
        goto L_0x00df;
    L_0x0164:
        r2.close();	 Catch:{ Exception -> 0x026a, all -> 0x0112 }
    L_0x0167:
        r7 = 0;
    L_0x0168:
        r1 = ",";
        r3 = android.text.TextUtils.join(r1, r9);	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r1 = android.provider.ContactsContract.Data.CONTENT_URI;	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r2 = r12.projectionNames;	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r4.<init>();	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r5 = "lookup IN (";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r3 = r4.append(r3);	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r4 = ") AND ";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r4 = "mimetype";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r4 = " = '";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r4 = "vnd.android.cursor.item/name";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r4 = "'";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        r4 = 0;
        r5 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x027b, all -> 0x0273 }
        if (r1 == 0) goto L_0x0251;
    L_0x01b2:
        r0 = r1.moveToNext();	 Catch:{ Exception -> 0x0243 }
        if (r0 == 0) goto L_0x024d;
    L_0x01b8:
        r0 = 0;
        r0 = r1.getString(r0);	 Catch:{ Exception -> 0x0243 }
        r2 = 1;
        r2 = r1.getString(r2);	 Catch:{ Exception -> 0x0243 }
        r3 = 2;
        r3 = r1.getString(r3);	 Catch:{ Exception -> 0x0243 }
        r4 = 3;
        r4 = r1.getString(r4);	 Catch:{ Exception -> 0x0243 }
        r5 = 4;
        r5 = r1.getString(r5);	 Catch:{ Exception -> 0x0243 }
        r0 = r6.get(r0);	 Catch:{ Exception -> 0x0243 }
        r0 = (org.telegram.messenger.ContactsController.Contact) r0;	 Catch:{ Exception -> 0x0243 }
        if (r0 == 0) goto L_0x01b2;
    L_0x01d9:
        r7 = r0.first_name;	 Catch:{ Exception -> 0x0243 }
        r7 = android.text.TextUtils.isEmpty(r7);	 Catch:{ Exception -> 0x0243 }
        if (r7 == 0) goto L_0x01b2;
    L_0x01e1:
        r7 = r0.last_name;	 Catch:{ Exception -> 0x0243 }
        r7 = android.text.TextUtils.isEmpty(r7);	 Catch:{ Exception -> 0x0243 }
        if (r7 == 0) goto L_0x01b2;
    L_0x01e9:
        r0.first_name = r2;	 Catch:{ Exception -> 0x0243 }
        r0.last_name = r3;	 Catch:{ Exception -> 0x0243 }
        r2 = r0.first_name;	 Catch:{ Exception -> 0x0243 }
        if (r2 != 0) goto L_0x01f6;
    L_0x01f1:
        r2 = "";
        r0.first_name = r2;	 Catch:{ Exception -> 0x0243 }
    L_0x01f6:
        r2 = android.text.TextUtils.isEmpty(r5);	 Catch:{ Exception -> 0x0243 }
        if (r2 != 0) goto L_0x0220;
    L_0x01fc:
        r2 = r0.first_name;	 Catch:{ Exception -> 0x0243 }
        r2 = r2.length();	 Catch:{ Exception -> 0x0243 }
        if (r2 == 0) goto L_0x0246;
    L_0x0204:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0243 }
        r2.<init>();	 Catch:{ Exception -> 0x0243 }
        r3 = r0.first_name;	 Catch:{ Exception -> 0x0243 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0243 }
        r3 = " ";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0243 }
        r2 = r2.append(r5);	 Catch:{ Exception -> 0x0243 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0243 }
        r0.first_name = r2;	 Catch:{ Exception -> 0x0243 }
    L_0x0220:
        r2 = r0.last_name;	 Catch:{ Exception -> 0x0243 }
        if (r2 != 0) goto L_0x0229;
    L_0x0224:
        r2 = "";
        r0.last_name = r2;	 Catch:{ Exception -> 0x0243 }
    L_0x0229:
        r2 = r0.last_name;	 Catch:{ Exception -> 0x0243 }
        r2 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x0243 }
        if (r2 == 0) goto L_0x01b2;
    L_0x0231:
        r2 = r0.first_name;	 Catch:{ Exception -> 0x0243 }
        r2 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x0243 }
        if (r2 == 0) goto L_0x01b2;
    L_0x0239:
        r2 = android.text.TextUtils.isEmpty(r4);	 Catch:{ Exception -> 0x0243 }
        if (r2 != 0) goto L_0x01b2;
    L_0x023f:
        r0.first_name = r4;	 Catch:{ Exception -> 0x0243 }
        goto L_0x01b2;
    L_0x0243:
        r0 = move-exception;
        goto L_0x00e6;
    L_0x0246:
        r0.first_name = r5;	 Catch:{ Exception -> 0x0243 }
        goto L_0x0220;
    L_0x0249:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0113;
    L_0x024d:
        r1.close();	 Catch:{ Exception -> 0x026d }
    L_0x0250:
        r1 = 0;
    L_0x0251:
        if (r1 == 0) goto L_0x00f1;
    L_0x0253:
        r1.close();	 Catch:{ Exception -> 0x0258 }
        goto L_0x00f1;
    L_0x0258:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);
        goto L_0x00f1;
    L_0x025e:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);
        goto L_0x00f1;
    L_0x0264:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);
        goto L_0x0118;
    L_0x026a:
        r1 = move-exception;
        goto L_0x0167;
    L_0x026d:
        r0 = move-exception;
        goto L_0x0250;
    L_0x026f:
        r0 = move-exception;
        r2 = r7;
        goto L_0x0113;
    L_0x0273:
        r0 = move-exception;
        r2 = r7;
        goto L_0x0113;
    L_0x0277:
        r0 = move-exception;
        r1 = r7;
        goto L_0x00e6;
    L_0x027b:
        r0 = move-exception;
        r1 = r7;
        goto L_0x00e6;
    L_0x027f:
        r3 = r1;
        goto L_0x00bd;
    L_0x0282:
        r5 = r7;
        goto L_0x0067;
    L_0x0285:
        r7 = r2;
        goto L_0x0168;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.ContactsController.readContactsFromPhoneBook():java.util.HashMap<java.lang.String, org.telegram.messenger.ContactsController$Contact>");
    }

    private void reloadContactsStatusesMaybe() {
        try {
            if (ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getLong("lastReloadStatusTime", 0) < System.currentTimeMillis() - 86400000) {
                reloadContactsStatuses();
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    private void saveContactsLoadTime() {
        try {
            ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putLong("lastReloadStatusTime", System.currentTimeMillis()).commit();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public void addContact(User user) {
        if (user != null && !TextUtils.isEmpty(user.phone)) {
            TLObject tLRPC$TL_contacts_importContacts = new TLRPC$TL_contacts_importContacts();
            ArrayList arrayList = new ArrayList();
            TLRPC$TL_inputPhoneContact tLRPC$TL_inputPhoneContact = new TLRPC$TL_inputPhoneContact();
            tLRPC$TL_inputPhoneContact.phone = user.phone;
            if (!tLRPC$TL_inputPhoneContact.phone.startsWith("+")) {
                tLRPC$TL_inputPhoneContact.phone = "+" + tLRPC$TL_inputPhoneContact.phone;
            }
            tLRPC$TL_inputPhoneContact.first_name = user.first_name;
            tLRPC$TL_inputPhoneContact.last_name = user.last_name;
            tLRPC$TL_inputPhoneContact.client_id = 0;
            arrayList.add(tLRPC$TL_inputPhoneContact);
            tLRPC$TL_contacts_importContacts.contacts = arrayList;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_importContacts, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        final TLRPC$TL_contacts_importedContacts tLRPC$TL_contacts_importedContacts = (TLRPC$TL_contacts_importedContacts) tLObject;
                        MessagesStorage.getInstance().putUsersAndChats(tLRPC$TL_contacts_importedContacts.users, null, true, true);
                        for (int i = 0; i < tLRPC$TL_contacts_importedContacts.users.size(); i++) {
                            final User user = (User) tLRPC$TL_contacts_importedContacts.users.get(i);
                            Utilities.phoneBookQueue.postRunnable(new Runnable() {
                                public void run() {
                                    ContactsController.this.addContactToPhoneBook(user, true);
                                }
                            });
                            TLRPC$TL_contact tLRPC$TL_contact = new TLRPC$TL_contact();
                            tLRPC$TL_contact.user_id = user.id;
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(tLRPC$TL_contact);
                            MessagesStorage.getInstance().putContacts(arrayList, false);
                            if (!TextUtils.isEmpty(user.phone)) {
                                ContactsController.formatName(user.first_name, user.last_name);
                                MessagesStorage.getInstance().applyPhoneBookUpdates(user.phone, TtmlNode.ANONYMOUS_REGION_ID);
                                Contact contact = (Contact) ContactsController.this.contactsBookSPhones.get(user.phone);
                                if (contact != null) {
                                    int indexOf = contact.shortPhones.indexOf(user.phone);
                                    if (indexOf != -1) {
                                        contact.phoneDeleted.set(indexOf, Integer.valueOf(0));
                                    }
                                }
                            }
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                Iterator it = tLRPC$TL_contacts_importedContacts.users.iterator();
                                while (it.hasNext()) {
                                    User user = (User) it.next();
                                    MessagesController.getInstance().putUser(user, false);
                                    if (ContactsController.this.contactsDict.get(Integer.valueOf(user.id)) == null) {
                                        TLRPC$TL_contact tLRPC$TL_contact = new TLRPC$TL_contact();
                                        tLRPC$TL_contact.user_id = user.id;
                                        ContactsController.this.contacts.add(tLRPC$TL_contact);
                                        ContactsController.this.contactsDict.put(Integer.valueOf(tLRPC$TL_contact.user_id), tLRPC$TL_contact);
                                    }
                                }
                                ContactsController.this.buildContactsSectionsArrays(true);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsDidLoaded, new Object[0]);
                            }
                        });
                    }
                }
            }, 6);
        }
    }

    public long addContactToPhoneBook(User user, boolean z) {
        long j = -1;
        if (!(this.currentAccount == null || user == null || TextUtils.isEmpty(user.phone) || !hasContactsPermission())) {
            synchronized (this.observerLock) {
                this.ignoreChanges = true;
            }
            ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
            if (z) {
                try {
                    contentResolver.delete(RawContacts.CONTENT_URI.buildUpon().appendQueryParameter("caller_is_syncadapter", "true").appendQueryParameter(Constants.KEY_ACCOUNT_NAME, this.currentAccount.name).appendQueryParameter("account_type", this.currentAccount.type).build(), "sync2 = " + user.id, null);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
            ArrayList arrayList = new ArrayList();
            Builder newInsert = ContentProviderOperation.newInsert(RawContacts.CONTENT_URI);
            newInsert.withValue(Constants.KEY_ACCOUNT_NAME, this.currentAccount.name);
            newInsert.withValue("account_type", this.currentAccount.type);
            newInsert.withValue("sync1", user.phone);
            newInsert.withValue("sync2", Integer.valueOf(user.id));
            arrayList.add(newInsert.build());
            newInsert = ContentProviderOperation.newInsert(Data.CONTENT_URI);
            newInsert.withValueBackReference("raw_contact_id", 0);
            newInsert.withValue("mimetype", "vnd.android.cursor.item/name");
            newInsert.withValue("data2", user.first_name);
            newInsert.withValue("data3", user.last_name);
            arrayList.add(newInsert.build());
            newInsert = ContentProviderOperation.newInsert(Data.CONTENT_URI);
            newInsert.withValueBackReference("raw_contact_id", 0);
            newInsert.withValue("mimetype", "vnd.android.cursor.item/vnd.org.telegram.messenger.android.profile");
            newInsert.withValue("data1", Integer.valueOf(user.id));
            newInsert.withValue("data2", "Telegram Profile");
            newInsert.withValue("data3", "+" + user.phone);
            newInsert.withValue("data4", Integer.valueOf(user.id));
            arrayList.add(newInsert.build());
            try {
                ContentProviderResult[] applyBatch = contentResolver.applyBatch("com.android.contacts", arrayList);
                if (!(applyBatch == null || applyBatch.length <= 0 || applyBatch[0].uri == null)) {
                    j = Long.parseLong(applyBatch[0].uri.getLastPathSegment());
                }
            } catch (Throwable e2) {
                FileLog.m13728e(e2);
            }
            synchronized (this.observerLock) {
                this.ignoreChanges = false;
            }
        }
        return j;
    }

    public void checkAppAccount() {
        int i = 1;
        int i2 = 0;
        AccountManager accountManager = AccountManager.get(ApplicationLoader.applicationContext);
        try {
            Account[] accountsByType = accountManager.getAccountsByType("org.telegram.account");
            if (accountsByType != null && accountsByType.length > 0) {
                for (Account removeAccount : accountsByType) {
                    accountManager.removeAccount(removeAccount, null, null);
                }
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        Account[] accountsByType2 = accountManager.getAccountsByType("org.telegram.messenger");
        if (UserConfig.isClientActivated()) {
            if (accountsByType2.length == 1) {
                Account account = accountsByType2[0];
                if (account.name.equals(TtmlNode.ANONYMOUS_REGION_ID + UserConfig.getClientUserId())) {
                    this.currentAccount = account;
                    i = 0;
                }
            }
            readContacts();
        } else if (accountsByType2.length <= 0) {
            i = 0;
        }
        if (i != 0) {
            while (i2 < accountsByType2.length) {
                try {
                    accountManager.removeAccount(accountsByType2[i2], null, null);
                    i2++;
                } catch (Throwable e2) {
                    FileLog.m13728e(e2);
                }
            }
            if (UserConfig.isClientActivated()) {
                try {
                    this.currentAccount = new Account(TtmlNode.ANONYMOUS_REGION_ID + UserConfig.getClientUserId(), "org.telegram.messenger");
                    accountManager.addAccountExplicitly(this.currentAccount, TtmlNode.ANONYMOUS_REGION_ID, null);
                } catch (Throwable e22) {
                    FileLog.m13728e(e22);
                }
            }
        }
    }

    public void checkContacts() {
        Utilities.globalQueue.postRunnable(new C30023());
    }

    public void checkInviteText() {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        this.inviteLink = sharedPreferences.getString("invitelink", null);
        int i = sharedPreferences.getInt("invitelinktime", 0);
        if (!this.updatingInviteLink) {
            if (this.inviteLink == null || Math.abs((System.currentTimeMillis() / 1000) - ((long) i)) >= 86400) {
                this.updatingInviteLink = true;
                ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_help_getInviteText(), new C30012(), 2);
            }
        }
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
        this.lastContactsVersions = TtmlNode.ANONYMOUS_REGION_ID;
        this.loadingDeleteInfo = 0;
        this.deleteAccountTTL = 0;
        this.loadingLastSeenInfo = 0;
        this.loadingGroupInfo = 0;
        this.loadingCallsInfo = 0;
        Utilities.globalQueue.postRunnable(new C29901());
        this.privacyRules = null;
    }

    public void deleteAllAppAccounts() {
        try {
            AccountManager accountManager = AccountManager.get(ApplicationLoader.applicationContext);
            Account[] accountsByType = accountManager.getAccountsByType("org.telegram.messenger");
            for (Account removeAccount : accountsByType) {
                accountManager.removeAccount(removeAccount, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteContact(final ArrayList<User> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            TLObject tLRPC$TL_contacts_deleteContacts = new TLRPC$TL_contacts_deleteContacts();
            final ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                User user = (User) it.next();
                InputUser inputUser = MessagesController.getInputUser(user);
                if (inputUser != null) {
                    arrayList2.add(Integer.valueOf(user.id));
                    tLRPC$TL_contacts_deleteContacts.id.add(inputUser);
                }
            }
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_deleteContacts, new RequestDelegate() {

                /* renamed from: org.telegram.messenger.ContactsController$22$1 */
                class C29941 implements Runnable {
                    C29941() {
                    }

                    public void run() {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            ContactsController.this.deleteContactFromPhoneBook(((User) it.next()).id);
                        }
                    }
                }

                /* renamed from: org.telegram.messenger.ContactsController$22$2 */
                class C29952 implements Runnable {
                    C29952() {
                    }

                    public void run() {
                        Iterator it = arrayList.iterator();
                        boolean z = false;
                        while (it.hasNext()) {
                            boolean z2;
                            User user = (User) it.next();
                            TLRPC$TL_contact tLRPC$TL_contact = (TLRPC$TL_contact) ContactsController.this.contactsDict.get(Integer.valueOf(user.id));
                            if (tLRPC$TL_contact != null) {
                                ContactsController.this.contacts.remove(tLRPC$TL_contact);
                                ContactsController.this.contactsDict.remove(Integer.valueOf(user.id));
                                z2 = true;
                            } else {
                                z2 = z;
                            }
                            z = z2;
                        }
                        if (z) {
                            ContactsController.this.buildContactsSectionsArrays(false);
                        }
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(1));
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsDidLoaded, new Object[0]);
                    }
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        MessagesStorage.getInstance().deleteContacts(arrayList2);
                        Utilities.phoneBookQueue.postRunnable(new C29941());
                        for (int i = 0; i < arrayList.size(); i++) {
                            User user = (User) arrayList.get(i);
                            if (!TextUtils.isEmpty(user.phone)) {
                                UserObject.getUserName(user);
                                MessagesStorage.getInstance().applyPhoneBookUpdates(user.phone, TtmlNode.ANONYMOUS_REGION_ID);
                                Contact contact = (Contact) ContactsController.this.contactsBookSPhones.get(user.phone);
                                if (contact != null) {
                                    int indexOf = contact.shortPhones.indexOf(user.phone);
                                    if (indexOf != -1) {
                                        contact.phoneDeleted.set(indexOf, Integer.valueOf(1));
                                    }
                                }
                            }
                        }
                        AndroidUtilities.runOnUIThread(new C29952());
                    }
                }
            });
        }
    }

    public void forceImportContacts() {
        Utilities.globalQueue.postRunnable(new C30034());
    }

    public HashMap<String, Contact> getContactsCopy(HashMap<String, Contact> hashMap) {
        HashMap<String, Contact> hashMap2 = new HashMap();
        for (Entry entry : hashMap.entrySet()) {
            Contact contact = new Contact();
            Contact contact2 = (Contact) entry.getValue();
            contact.phoneDeleted.addAll(contact2.phoneDeleted);
            contact.phones.addAll(contact2.phones);
            contact.phoneTypes.addAll(contact2.phoneTypes);
            contact.shortPhones.addAll(contact2.shortPhones);
            contact.first_name = contact2.first_name;
            contact.last_name = contact2.last_name;
            contact.contact_id = contact2.contact_id;
            contact.key = contact2.key;
            hashMap2.put(contact.key, contact);
        }
        return hashMap2;
    }

    public int getDeleteAccountTTL() {
        return this.deleteAccountTTL;
    }

    public String getInviteText(int i) {
        String str = this.inviteLink == null ? "https://telegram.org/dl" : this.inviteLink;
        if (i <= 1) {
            return LocaleController.formatString("InviteText2", R.string.InviteText2, str);
        }
        try {
            return String.format(LocaleController.getPluralString("InviteTextNum", i), new Object[]{Integer.valueOf(i), str});
        } catch (Exception e) {
            return LocaleController.formatString("InviteText2", R.string.InviteText2, str);
        }
    }

    public boolean getLoadingCallsInfo() {
        return this.loadingCallsInfo != 2;
    }

    public boolean getLoadingDeleteInfo() {
        return this.loadingDeleteInfo != 2;
    }

    public boolean getLoadingGroupInfo() {
        return this.loadingGroupInfo != 2;
    }

    public boolean getLoadingLastSeenInfo() {
        return this.loadingLastSeenInfo != 2;
    }

    public ArrayList<PrivacyRule> getPrivacyRules(int i) {
        return i == 2 ? this.callPrivacyRules : i == 1 ? this.groupPrivacyRules : this.privacyRules;
    }

    public boolean isLoadingContacts() {
        boolean z;
        synchronized (loadContactsSync) {
            z = this.loadingContacts;
        }
        return z;
    }

    public void loadContacts(boolean z, final int i) {
        synchronized (loadContactsSync) {
            this.loadingContacts = true;
        }
        if (z) {
            FileLog.m13726e("load contacts from cache");
            MessagesStorage.getInstance().getContacts();
            return;
        }
        FileLog.m13726e("load contacts from server");
        TLObject tLRPC$TL_contacts_getContacts = new TLRPC$TL_contacts_getContacts();
        tLRPC$TL_contacts_getContacts.hash = i;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_getContacts, new RequestDelegate() {

            /* renamed from: org.telegram.messenger.ContactsController$11$1 */
            class C29821 implements Runnable {
                C29821() {
                }

                public void run() {
                    synchronized (ContactsController.loadContactsSync) {
                        ContactsController.this.loadingContacts = false;
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsDidLoaded, new Object[0]);
                }
            }

            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error == null) {
                    TLRPC$contacts_Contacts tLRPC$contacts_Contacts = (TLRPC$contacts_Contacts) tLObject;
                    if (i == 0 || !(tLRPC$contacts_Contacts instanceof TLRPC$TL_contacts_contactsNotModified)) {
                        UserConfig.contactsSavedCount = tLRPC$contacts_Contacts.saved_count;
                        UserConfig.saveConfig(false);
                        ContactsController.this.processLoadedContacts(tLRPC$contacts_Contacts.contacts, tLRPC$contacts_Contacts.users, 0);
                        return;
                    }
                    ContactsController.this.contactsLoaded = true;
                    if (!ContactsController.this.delayedContactsUpdate.isEmpty() && ContactsController.this.contactsBookLoaded) {
                        ContactsController.this.applyContactsUpdates(ContactsController.this.delayedContactsUpdate, null, null, null);
                        ContactsController.this.delayedContactsUpdate.clear();
                    }
                    UserConfig.lastContactsSyncTime = (int) (System.currentTimeMillis() / 1000);
                    UserConfig.saveConfig(false);
                    AndroidUtilities.runOnUIThread(new C29821());
                    FileLog.m13726e("load contacts don't change");
                }
            }
        });
    }

    public void loadPrivacySettings() {
        if (this.loadingDeleteInfo == 0) {
            this.loadingDeleteInfo = 1;
            ConnectionsManager.getInstance().sendRequest(new TL_account_getAccountTTL(), new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLRPC$TL_error == null) {
                                ContactsController.this.deleteAccountTTL = ((TL_accountDaysTTL) tLObject).days;
                                ContactsController.this.loadingDeleteInfo = 2;
                            } else {
                                ContactsController.this.loadingDeleteInfo = 0;
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.privacyRulesUpdated, new Object[0]);
                        }
                    });
                }
            });
        }
        if (this.loadingLastSeenInfo == 0) {
            this.loadingLastSeenInfo = 1;
            TLObject tL_account_getPrivacy = new TL_account_getPrivacy();
            tL_account_getPrivacy.key = new TLRPC$TL_inputPrivacyKeyStatusTimestamp();
            ConnectionsManager.getInstance().sendRequest(tL_account_getPrivacy, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLRPC$TL_error == null) {
                                TL_account_privacyRules tL_account_privacyRules = (TL_account_privacyRules) tLObject;
                                MessagesController.getInstance().putUsers(tL_account_privacyRules.users, false);
                                ContactsController.this.privacyRules = tL_account_privacyRules.rules;
                                ContactsController.this.loadingLastSeenInfo = 2;
                            } else {
                                ContactsController.this.loadingLastSeenInfo = 0;
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.privacyRulesUpdated, new Object[0]);
                        }
                    });
                }
            });
        }
        if (this.loadingCallsInfo == 0) {
            this.loadingCallsInfo = 1;
            tL_account_getPrivacy = new TL_account_getPrivacy();
            tL_account_getPrivacy.key = new TLRPC$TL_inputPrivacyKeyPhoneCall();
            ConnectionsManager.getInstance().sendRequest(tL_account_getPrivacy, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLRPC$TL_error == null) {
                                TL_account_privacyRules tL_account_privacyRules = (TL_account_privacyRules) tLObject;
                                MessagesController.getInstance().putUsers(tL_account_privacyRules.users, false);
                                ContactsController.this.callPrivacyRules = tL_account_privacyRules.rules;
                                ContactsController.this.loadingCallsInfo = 2;
                            } else {
                                ContactsController.this.loadingCallsInfo = 0;
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.privacyRulesUpdated, new Object[0]);
                        }
                    });
                }
            });
        }
        if (this.loadingGroupInfo == 0) {
            this.loadingGroupInfo = 1;
            tL_account_getPrivacy = new TL_account_getPrivacy();
            tL_account_getPrivacy.key = new TLRPC$TL_inputPrivacyKeyChatInvite();
            ConnectionsManager.getInstance().sendRequest(tL_account_getPrivacy, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLRPC$TL_error == null) {
                                TL_account_privacyRules tL_account_privacyRules = (TL_account_privacyRules) tLObject;
                                MessagesController.getInstance().putUsers(tL_account_privacyRules.users, false);
                                ContactsController.this.groupPrivacyRules = tL_account_privacyRules.rules;
                                ContactsController.this.loadingGroupInfo = 2;
                            } else {
                                ContactsController.this.loadingGroupInfo = 0;
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.privacyRulesUpdated, new Object[0]);
                        }
                    });
                }
            });
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.privacyRulesUpdated, new Object[0]);
    }

    protected void markAsContacted(final String str) {
        if (str != null) {
            Utilities.phoneBookQueue.postRunnable(new Runnable() {
                public void run() {
                    Uri parse = Uri.parse(str);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("last_time_contacted", Long.valueOf(System.currentTimeMillis()));
                    ApplicationLoader.applicationContext.getContentResolver().update(parse, contentValues, null, null);
                }
            });
        }
    }

    protected void migratePhoneBookToV7(final HashMap<Integer, Contact> hashMap) {
        Utilities.globalQueue.postRunnable(new Runnable() {
            public void run() {
                if (!ContactsController.this.migratingContacts) {
                    Contact contact;
                    ContactsController.this.migratingContacts = true;
                    HashMap hashMap = new HashMap();
                    HashMap access$700 = ContactsController.this.readContactsFromPhoneBook();
                    HashMap hashMap2 = new HashMap();
                    for (Entry value : access$700.entrySet()) {
                        contact = (Contact) value.getValue();
                        for (int i = 0; i < contact.shortPhones.size(); i++) {
                            hashMap2.put(contact.shortPhones.get(i), contact.key);
                        }
                    }
                    for (Entry value2 : hashMap.entrySet()) {
                        contact = (Contact) value2.getValue();
                        for (int i2 = 0; i2 < contact.shortPhones.size(); i2++) {
                            String str = (String) hashMap2.get((String) contact.shortPhones.get(i2));
                            if (str != null) {
                                contact.key = str;
                                hashMap.put(str, contact);
                                break;
                            }
                        }
                    }
                    FileLog.m13725d("migrated contacts " + hashMap.size() + " of " + hashMap.size());
                    MessagesStorage.getInstance().putCachedPhoneBook(hashMap, true);
                }
            }
        });
    }

    protected void performSyncPhoneBook(HashMap<String, Contact> hashMap, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        if (z2 || this.contactsBookLoaded) {
            final HashMap<String, Contact> hashMap2 = hashMap;
            final boolean z7 = z3;
            final boolean z8 = z;
            final boolean z9 = z2;
            final boolean z10 = z4;
            final boolean z11 = z5;
            final boolean z12 = z6;
            Utilities.globalQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.ContactsController$9$1 */
                class C30081 implements Runnable {
                    C30081() {
                    }

                    public void run() {
                        ArrayList arrayList = new ArrayList();
                        if (!(hashMap2 == null || hashMap2.isEmpty())) {
                            try {
                                int i;
                                HashMap hashMap = new HashMap();
                                for (i = 0; i < ContactsController.this.contacts.size(); i++) {
                                    User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) ContactsController.this.contacts.get(i)).user_id));
                                    if (!(user == null || TextUtils.isEmpty(user.phone))) {
                                        hashMap.put(user.phone, user);
                                    }
                                }
                                int i2 = 0;
                                for (Entry value : hashMap2.entrySet()) {
                                    Contact contact = (Contact) value.getValue();
                                    int i3 = 0;
                                    Object obj = null;
                                    while (i3 < contact.shortPhones.size()) {
                                        Object obj2;
                                        User user2 = (User) hashMap.get((String) contact.shortPhones.get(i3));
                                        if (user2 != null) {
                                            arrayList.add(user2);
                                            contact.shortPhones.remove(i3);
                                            i = i3 - 1;
                                            obj2 = 1;
                                        } else {
                                            i = i3;
                                            obj2 = obj;
                                        }
                                        obj = obj2;
                                        i3 = i + 1;
                                    }
                                    int i4 = (obj == null || contact.shortPhones.size() == 0) ? i2 + 1 : i2;
                                    i2 = i4;
                                }
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            ContactsController.this.deleteContact(arrayList);
                        }
                    }
                }

                public void run() {
                    int i;
                    int i2;
                    int i3;
                    int i4;
                    int i5;
                    int indexOf;
                    int i6 = 0;
                    HashMap hashMap = new HashMap();
                    for (Entry value : hashMap2.entrySet()) {
                        Contact contact = (Contact) value.getValue();
                        for (i = 0; i < contact.shortPhones.size(); i++) {
                            hashMap.put(contact.shortPhones.get(i), contact);
                        }
                    }
                    FileLog.m13726e("start read contacts from phone");
                    if (!z7) {
                        ContactsController.this.checkContactsInternal();
                    }
                    final HashMap access$700 = ContactsController.this.readContactsFromPhoneBook();
                    final HashMap hashMap2 = new HashMap();
                    int size = hashMap2.size();
                    ArrayList arrayList = new ArrayList();
                    String str;
                    if (hashMap2.isEmpty()) {
                        if (z8) {
                            for (Entry value2 : access$700.entrySet()) {
                                Contact contact2 = (Contact) value2.getValue();
                                String str2 = (String) value2.getKey();
                                for (i2 = 0; i2 < contact2.phones.size(); i2++) {
                                    if (!z10) {
                                        str2 = (String) contact2.shortPhones.get(i2);
                                        String substring = str2.substring(Math.max(0, str2.length() - 7));
                                        TLRPC$TL_contact tLRPC$TL_contact = (TLRPC$TL_contact) ContactsController.this.contactsByPhone.get(str2);
                                        if (tLRPC$TL_contact != null) {
                                            User user = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contact.user_id));
                                            if (user != null) {
                                                i3 = i6 + 1;
                                                str2 = user.first_name != null ? user.first_name : TtmlNode.ANONYMOUS_REGION_ID;
                                                str = user.last_name != null ? user.last_name : TtmlNode.ANONYMOUS_REGION_ID;
                                                if (str2.equals(contact2.first_name) && str.equals(contact2.last_name)) {
                                                    i6 = i3;
                                                } else if (TextUtils.isEmpty(contact2.first_name) && TextUtils.isEmpty(contact2.last_name)) {
                                                    i6 = i3;
                                                } else {
                                                    i6 = i3;
                                                }
                                            }
                                        } else if (ContactsController.this.contactsByShortPhone.containsKey(substring)) {
                                            i6++;
                                        }
                                    }
                                    TLRPC$TL_inputPhoneContact tLRPC$TL_inputPhoneContact = new TLRPC$TL_inputPhoneContact();
                                    tLRPC$TL_inputPhoneContact.client_id = (long) contact2.contact_id;
                                    tLRPC$TL_inputPhoneContact.client_id |= ((long) i2) << 32;
                                    tLRPC$TL_inputPhoneContact.first_name = contact2.first_name;
                                    tLRPC$TL_inputPhoneContact.last_name = contact2.last_name;
                                    tLRPC$TL_inputPhoneContact.phone = (String) contact2.phones.get(i2);
                                    arrayList.add(tLRPC$TL_inputPhoneContact);
                                }
                            }
                        }
                        i2 = i6;
                        i3 = 0;
                    } else {
                        i2 = 0;
                        i3 = 0;
                        for (Entry value22 : access$700.entrySet()) {
                            Contact contact3;
                            Object obj;
                            String str3 = (String) value22.getKey();
                            contact = (Contact) value22.getValue();
                            Contact contact4 = (Contact) hashMap2.get(str3);
                            if (contact4 == null) {
                                for (i4 = 0; i4 < contact.shortPhones.size(); i4++) {
                                    Contact contact5 = (Contact) hashMap.get(contact.shortPhones.get(i4));
                                    if (contact5 != null) {
                                        contact3 = contact5;
                                        obj = contact5.key;
                                        break;
                                    }
                                }
                            }
                            contact3 = contact4;
                            String str4 = str3;
                            if (contact3 != null) {
                                contact.imported = contact3.imported;
                            }
                            Object obj2 = (contact3 == null || ((TextUtils.isEmpty(contact.first_name) || contact3.first_name.equals(contact.first_name)) && (TextUtils.isEmpty(contact.last_name) || contact3.last_name.equals(contact.last_name)))) ? null : 1;
                            int i7;
                            if (contact3 == null || obj2 != null) {
                                for (i7 = 0; i7 < contact.phones.size(); i7++) {
                                    str3 = (String) contact.shortPhones.get(i7);
                                    str3.substring(Math.max(0, str3.length() - 7));
                                    hashMap2.put(str3, contact);
                                    if (contact3 != null) {
                                        i6 = contact3.shortPhones.indexOf(str3);
                                        if (i6 != -1) {
                                            Integer num = (Integer) contact3.phoneDeleted.get(i6);
                                            contact.phoneDeleted.set(i7, num);
                                            if (num.intValue() == 1) {
                                            }
                                        }
                                    }
                                    if (z8) {
                                        if (obj2 == null) {
                                            if (ContactsController.this.contactsByPhone.containsKey(str3)) {
                                                i2++;
                                            } else {
                                                i3++;
                                            }
                                        }
                                        TLRPC$TL_inputPhoneContact tLRPC$TL_inputPhoneContact2 = new TLRPC$TL_inputPhoneContact();
                                        tLRPC$TL_inputPhoneContact2.client_id = (long) contact.contact_id;
                                        tLRPC$TL_inputPhoneContact2.client_id |= ((long) i7) << 32;
                                        tLRPC$TL_inputPhoneContact2.first_name = contact.first_name;
                                        tLRPC$TL_inputPhoneContact2.last_name = contact.last_name;
                                        tLRPC$TL_inputPhoneContact2.phone = (String) contact.phones.get(i7);
                                        arrayList.add(tLRPC$TL_inputPhoneContact2);
                                    }
                                }
                                if (contact3 != null) {
                                    hashMap2.remove(obj);
                                    i5 = i2;
                                    i = i3;
                                }
                                i5 = i2;
                                i = i3;
                            } else {
                                for (i7 = 0; i7 < contact.phones.size(); i7++) {
                                    TLRPC$TL_contact tLRPC$TL_contact2;
                                    User user2;
                                    TLRPC$TL_inputPhoneContact tLRPC$TL_inputPhoneContact3;
                                    str3 = (String) contact.shortPhones.get(i7);
                                    String substring2 = str3.substring(Math.max(0, str3.length() - 7));
                                    hashMap2.put(str3, contact);
                                    indexOf = contact3.shortPhones.indexOf(str3);
                                    obj2 = null;
                                    if (z8) {
                                        TLRPC$TL_contact tLRPC$TL_contact3 = (TLRPC$TL_contact) ContactsController.this.contactsByPhone.get(str3);
                                        if (tLRPC$TL_contact3 != null) {
                                            Object obj3;
                                            User user3 = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contact3.user_id));
                                            if (user3 != null) {
                                                int i8 = i2 + 1;
                                                if (TextUtils.isEmpty(user3.first_name) && TextUtils.isEmpty(user3.last_name) && (!TextUtils.isEmpty(contact.first_name) || !TextUtils.isEmpty(contact.last_name))) {
                                                    i2 = -1;
                                                    obj3 = 1;
                                                    i4 = i8;
                                                } else {
                                                    obj3 = null;
                                                    i2 = indexOf;
                                                    i4 = i8;
                                                }
                                            } else {
                                                obj3 = null;
                                                i4 = i2;
                                                i2 = indexOf;
                                            }
                                            indexOf = i2;
                                            Object obj4 = obj3;
                                            i6 = i4;
                                            obj2 = obj4;
                                        } else if (ContactsController.this.contactsByShortPhone.containsKey(substring2)) {
                                            i6 = i2 + 1;
                                        }
                                        if (indexOf != -1) {
                                            if (z8) {
                                                if (obj2 == null) {
                                                    tLRPC$TL_contact2 = (TLRPC$TL_contact) ContactsController.this.contactsByPhone.get(str3);
                                                    if (tLRPC$TL_contact2 != null) {
                                                        user2 = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contact2.user_id));
                                                        if (user2 == null) {
                                                            i2 = i6 + 1;
                                                            str3 = user2.first_name == null ? user2.first_name : TtmlNode.ANONYMOUS_REGION_ID;
                                                            str = user2.last_name == null ? user2.last_name : TtmlNode.ANONYMOUS_REGION_ID;
                                                            if (!((str3.equals(contact.first_name) && str.equals(contact.last_name)) || (TextUtils.isEmpty(contact.first_name) && TextUtils.isEmpty(contact.last_name)))) {
                                                                i = i2;
                                                            }
                                                        } else {
                                                            i3++;
                                                            i = i6;
                                                        }
                                                        i6 = i;
                                                    } else if (ContactsController.this.contactsByShortPhone.containsKey(substring2)) {
                                                        i6++;
                                                    }
                                                }
                                                tLRPC$TL_inputPhoneContact3 = new TLRPC$TL_inputPhoneContact();
                                                tLRPC$TL_inputPhoneContact3.client_id = (long) contact.contact_id;
                                                tLRPC$TL_inputPhoneContact3.client_id |= ((long) i7) << 32;
                                                tLRPC$TL_inputPhoneContact3.first_name = contact.first_name;
                                                tLRPC$TL_inputPhoneContact3.last_name = contact.last_name;
                                                tLRPC$TL_inputPhoneContact3.phone = (String) contact.phones.get(i7);
                                                arrayList.add(tLRPC$TL_inputPhoneContact3);
                                                i2 = i6;
                                            }
                                            i2 = i6;
                                        } else {
                                            contact.phoneDeleted.set(i7, contact3.phoneDeleted.get(indexOf));
                                            contact3.phones.remove(indexOf);
                                            contact3.shortPhones.remove(indexOf);
                                            contact3.phoneDeleted.remove(indexOf);
                                            contact3.phoneTypes.remove(indexOf);
                                            i2 = i6;
                                        }
                                    }
                                    i6 = i2;
                                    if (indexOf != -1) {
                                        contact.phoneDeleted.set(i7, contact3.phoneDeleted.get(indexOf));
                                        contact3.phones.remove(indexOf);
                                        contact3.shortPhones.remove(indexOf);
                                        contact3.phoneDeleted.remove(indexOf);
                                        contact3.phoneTypes.remove(indexOf);
                                        i2 = i6;
                                    } else {
                                        if (z8) {
                                            if (obj2 == null) {
                                                tLRPC$TL_contact2 = (TLRPC$TL_contact) ContactsController.this.contactsByPhone.get(str3);
                                                if (tLRPC$TL_contact2 != null) {
                                                    user2 = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contact2.user_id));
                                                    if (user2 == null) {
                                                        i3++;
                                                        i = i6;
                                                    } else {
                                                        i2 = i6 + 1;
                                                        if (user2.first_name == null) {
                                                        }
                                                        if (user2.last_name == null) {
                                                        }
                                                        i = i2;
                                                    }
                                                    i6 = i;
                                                } else if (ContactsController.this.contactsByShortPhone.containsKey(substring2)) {
                                                    i6++;
                                                }
                                            }
                                            tLRPC$TL_inputPhoneContact3 = new TLRPC$TL_inputPhoneContact();
                                            tLRPC$TL_inputPhoneContact3.client_id = (long) contact.contact_id;
                                            tLRPC$TL_inputPhoneContact3.client_id |= ((long) i7) << 32;
                                            tLRPC$TL_inputPhoneContact3.first_name = contact.first_name;
                                            tLRPC$TL_inputPhoneContact3.last_name = contact.last_name;
                                            tLRPC$TL_inputPhoneContact3.phone = (String) contact.phones.get(i7);
                                            arrayList.add(tLRPC$TL_inputPhoneContact3);
                                            i2 = i6;
                                        }
                                        i2 = i6;
                                    }
                                }
                                if (contact3.phones.isEmpty()) {
                                    hashMap2.remove(obj);
                                }
                                i5 = i2;
                                i = i3;
                            }
                            i2 = i5;
                            i3 = i;
                        }
                        if (!z9 && hashMap2.isEmpty() && arrayList.isEmpty() && size == access$700.size()) {
                            FileLog.m13726e("contacts not changed!");
                            return;
                        } else if (z8 && !hashMap2.isEmpty() && !access$700.isEmpty() && arrayList.isEmpty()) {
                            MessagesStorage.getInstance().putCachedPhoneBook(access$700, false);
                        }
                    }
                    FileLog.m13726e("done processing contacts");
                    if (!z8) {
                        Utilities.stageQueue.postRunnable(new Runnable() {
                            public void run() {
                                ContactsController.this.contactsBookSPhones = hashMap2;
                                ContactsController.this.contactsBook = access$700;
                                ContactsController.this.contactsSyncInProgress = false;
                                ContactsController.this.contactsBookLoaded = true;
                                if (z9) {
                                    ContactsController.this.contactsLoaded = true;
                                }
                                if (!ContactsController.this.delayedContactsUpdate.isEmpty() && ContactsController.this.contactsLoaded && ContactsController.this.contactsBookLoaded) {
                                    ContactsController.this.applyContactsUpdates(ContactsController.this.delayedContactsUpdate, null, null, null);
                                    ContactsController.this.delayedContactsUpdate.clear();
                                }
                            }
                        });
                        if (!access$700.isEmpty()) {
                            MessagesStorage.getInstance().putCachedPhoneBook(access$700, false);
                        }
                    } else if (arrayList.isEmpty()) {
                        Utilities.stageQueue.postRunnable(new Runnable() {

                            /* renamed from: org.telegram.messenger.ContactsController$9$5$1 */
                            class C30161 implements Runnable {
                                C30161() {
                                }

                                public void run() {
                                    ContactsController.this.updateUnregisteredContacts(ContactsController.this.contacts);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsDidLoaded, new Object[0]);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsImported, new Object[0]);
                                }
                            }

                            public void run() {
                                ContactsController.this.contactsBookSPhones = hashMap2;
                                ContactsController.this.contactsBook = access$700;
                                ContactsController.this.contactsSyncInProgress = false;
                                ContactsController.this.contactsBookLoaded = true;
                                if (z9) {
                                    ContactsController.this.contactsLoaded = true;
                                }
                                if (!ContactsController.this.delayedContactsUpdate.isEmpty() && ContactsController.this.contactsLoaded) {
                                    ContactsController.this.applyContactsUpdates(ContactsController.this.delayedContactsUpdate, null, null, null);
                                    ContactsController.this.delayedContactsUpdate.clear();
                                }
                                AndroidUtilities.runOnUIThread(new C30161());
                            }
                        });
                    } else {
                        i5 = z11 ? i3 >= 30 ? 1 : (z9 && hashMap2.isEmpty() && ContactsController.this.contactsByPhone.size() - i2 > (ContactsController.this.contactsByPhone.size() / 3) * 2) ? 2 : 0 : 0;
                        FileLog.m13725d("new phone book contacts " + i3 + " serverContactsInPhonebook " + i2 + " totalContacts " + ContactsController.this.contactsByPhone.size());
                        if (i5 != 0) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.hasNewContactsToImport, Integer.valueOf(i5), hashMap2, Boolean.valueOf(z9), Boolean.valueOf(z7));
                                }
                            });
                        } else if (z12) {
                            Utilities.stageQueue.postRunnable(new Runnable() {

                                /* renamed from: org.telegram.messenger.ContactsController$9$3$1 */
                                class C30101 implements Runnable {
                                    C30101() {
                                    }

                                    public void run() {
                                        ContactsController.this.updateUnregisteredContacts(ContactsController.this.contacts);
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsDidLoaded, new Object[0]);
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsImported, new Object[0]);
                                    }
                                }

                                public void run() {
                                    ContactsController.this.contactsBookSPhones = hashMap2;
                                    ContactsController.this.contactsBook = access$700;
                                    ContactsController.this.contactsSyncInProgress = false;
                                    ContactsController.this.contactsBookLoaded = true;
                                    if (z9) {
                                        ContactsController.this.contactsLoaded = true;
                                    }
                                    if (!ContactsController.this.delayedContactsUpdate.isEmpty() && ContactsController.this.contactsLoaded) {
                                        ContactsController.this.applyContactsUpdates(ContactsController.this.delayedContactsUpdate, null, null, null);
                                        ContactsController.this.delayedContactsUpdate.clear();
                                    }
                                    MessagesStorage.getInstance().putCachedPhoneBook(access$700, false);
                                    AndroidUtilities.runOnUIThread(new C30101());
                                }
                            });
                        } else {
                            final boolean[] zArr = new boolean[]{false};
                            final HashMap hashMap3 = new HashMap(access$700);
                            final HashMap hashMap4 = new HashMap();
                            for (Entry value222 : hashMap3.entrySet()) {
                                contact = (Contact) value222.getValue();
                                hashMap4.put(Integer.valueOf(contact.contact_id), contact.key);
                            }
                            ContactsController.this.completedRequestsCount = 0;
                            i4 = (int) Math.ceil(((double) arrayList.size()) / 500.0d);
                            for (indexOf = 0; indexOf < i4; indexOf++) {
                                final TLObject tLRPC$TL_contacts_importContacts = new TLRPC$TL_contacts_importContacts();
                                i5 = indexOf * ChatActivity.startAllServices;
                                tLRPC$TL_contacts_importContacts.contacts = new ArrayList(arrayList.subList(i5, Math.min(i5 + ChatActivity.startAllServices, arrayList.size())));
                                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_importContacts, new RequestDelegate() {

                                    /* renamed from: org.telegram.messenger.ContactsController$9$4$1 */
                                    class C30141 implements Runnable {

                                        /* renamed from: org.telegram.messenger.ContactsController$9$4$1$1 */
                                        class C30121 implements Runnable {
                                            C30121() {
                                            }

                                            public void run() {
                                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsImported, new Object[0]);
                                            }
                                        }

                                        /* renamed from: org.telegram.messenger.ContactsController$9$4$1$2 */
                                        class C30132 implements Runnable {
                                            C30132() {
                                            }

                                            public void run() {
                                                MessagesStorage.getInstance().getCachedPhoneBook(true);
                                            }
                                        }

                                        C30141() {
                                        }

                                        public void run() {
                                            ContactsController.this.contactsBookSPhones = hashMap2;
                                            ContactsController.this.contactsBook = access$700;
                                            ContactsController.this.contactsSyncInProgress = false;
                                            ContactsController.this.contactsBookLoaded = true;
                                            if (z9) {
                                                ContactsController.this.contactsLoaded = true;
                                            }
                                            if (!ContactsController.this.delayedContactsUpdate.isEmpty() && ContactsController.this.contactsLoaded) {
                                                ContactsController.this.applyContactsUpdates(ContactsController.this.delayedContactsUpdate, null, null, null);
                                                ContactsController.this.delayedContactsUpdate.clear();
                                            }
                                            AndroidUtilities.runOnUIThread(new C30121());
                                            if (zArr[0]) {
                                                Utilities.globalQueue.postRunnable(new C30132(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                                            }
                                        }
                                    }

                                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                        ContactsController.this.completedRequestsCount = ContactsController.this.completedRequestsCount + 1;
                                        int i;
                                        if (tLRPC$TL_error == null) {
                                            FileLog.m13726e("contacts imported");
                                            TLRPC$TL_contacts_importedContacts tLRPC$TL_contacts_importedContacts = (TLRPC$TL_contacts_importedContacts) tLObject;
                                            if (!tLRPC$TL_contacts_importedContacts.retry_contacts.isEmpty()) {
                                                for (i = 0; i < tLRPC$TL_contacts_importedContacts.retry_contacts.size(); i++) {
                                                    hashMap3.remove(hashMap4.get(Integer.valueOf((int) ((Long) tLRPC$TL_contacts_importedContacts.retry_contacts.get(i)).longValue())));
                                                }
                                                zArr[0] = true;
                                            }
                                            for (int i2 = 0; i2 < tLRPC$TL_contacts_importedContacts.popular_invites.size(); i2++) {
                                                TLRPC$TL_popularContact tLRPC$TL_popularContact = (TLRPC$TL_popularContact) tLRPC$TL_contacts_importedContacts.popular_invites.get(i2);
                                                Contact contact = (Contact) access$700.get(hashMap4.get(Integer.valueOf((int) tLRPC$TL_popularContact.client_id)));
                                                if (contact != null) {
                                                    contact.imported = tLRPC$TL_popularContact.importers;
                                                }
                                            }
                                            MessagesStorage.getInstance().putUsersAndChats(tLRPC$TL_contacts_importedContacts.users, null, true, true);
                                            ArrayList arrayList = new ArrayList();
                                            for (i = 0; i < tLRPC$TL_contacts_importedContacts.imported.size(); i++) {
                                                TLRPC$TL_contact tLRPC$TL_contact = new TLRPC$TL_contact();
                                                tLRPC$TL_contact.user_id = ((TLRPC$TL_importedContact) tLRPC$TL_contacts_importedContacts.imported.get(i)).user_id;
                                                arrayList.add(tLRPC$TL_contact);
                                            }
                                            ContactsController.this.processLoadedContacts(arrayList, tLRPC$TL_contacts_importedContacts.users, 2);
                                        } else {
                                            for (i = 0; i < tLRPC$TL_contacts_importContacts.contacts.size(); i++) {
                                                hashMap3.remove(hashMap4.get(Integer.valueOf((int) ((TLRPC$TL_inputPhoneContact) tLRPC$TL_contacts_importContacts.contacts.get(i)).client_id)));
                                            }
                                            zArr[0] = true;
                                            FileLog.m13726e("import contacts error " + tLRPC$TL_error.text);
                                        }
                                        if (ContactsController.this.completedRequestsCount == i4) {
                                            if (!hashMap3.isEmpty()) {
                                                MessagesStorage.getInstance().putCachedPhoneBook(hashMap3, false);
                                            }
                                            Utilities.stageQueue.postRunnable(new C30141());
                                        }
                                    }
                                }, 6);
                            }
                        }
                    }
                }
            });
        }
    }

    public void processContactsUpdates(ArrayList<Integer> arrayList, ConcurrentHashMap<Integer, User> concurrentHashMap) {
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            int indexOf;
            if (num.intValue() > 0) {
                TLRPC$TL_contact tLRPC$TL_contact = new TLRPC$TL_contact();
                tLRPC$TL_contact.user_id = num.intValue();
                arrayList2.add(tLRPC$TL_contact);
                if (!this.delayedContactsUpdate.isEmpty()) {
                    indexOf = this.delayedContactsUpdate.indexOf(Integer.valueOf(-num.intValue()));
                    if (indexOf != -1) {
                        this.delayedContactsUpdate.remove(indexOf);
                    }
                }
            } else if (num.intValue() < 0) {
                arrayList3.add(Integer.valueOf(-num.intValue()));
                if (!this.delayedContactsUpdate.isEmpty()) {
                    indexOf = this.delayedContactsUpdate.indexOf(Integer.valueOf(-num.intValue()));
                    if (indexOf != -1) {
                        this.delayedContactsUpdate.remove(indexOf);
                    }
                }
            }
        }
        if (!arrayList3.isEmpty()) {
            MessagesStorage.getInstance().deleteContacts(arrayList3);
        }
        if (!arrayList2.isEmpty()) {
            MessagesStorage.getInstance().putContacts(arrayList2, false);
        }
        if (this.contactsLoaded && this.contactsBookLoaded) {
            applyContactsUpdates(arrayList, concurrentHashMap, arrayList2, arrayList3);
            return;
        }
        this.delayedContactsUpdate.addAll(arrayList);
        FileLog.m13726e("delay update - contacts add = " + arrayList2.size() + " delete = " + arrayList3.size());
    }

    public void processLoadedContacts(final ArrayList<TLRPC$TL_contact> arrayList, final ArrayList<User> arrayList2, final int i) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                boolean z = true;
                int i = 0;
                MessagesController instance = MessagesController.getInstance();
                ArrayList arrayList = arrayList2;
                if (i != 1) {
                    z = false;
                }
                instance.putUsers(arrayList, z);
                final HashMap hashMap = new HashMap();
                final boolean isEmpty = arrayList.isEmpty();
                if (!ContactsController.this.contacts.isEmpty()) {
                    int i2 = 0;
                    while (i2 < arrayList.size()) {
                        if (ContactsController.this.contactsDict.get(Integer.valueOf(((TLRPC$TL_contact) arrayList.get(i2)).user_id)) != null) {
                            arrayList.remove(i2);
                            i2--;
                        }
                        i2++;
                    }
                    arrayList.addAll(ContactsController.this.contacts);
                }
                while (i < arrayList.size()) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) arrayList.get(i)).user_id));
                    if (user != null) {
                        hashMap.put(Integer.valueOf(user.id), user);
                    }
                    i++;
                }
                Utilities.stageQueue.postRunnable(new Runnable() {

                    /* renamed from: org.telegram.messenger.ContactsController$12$1$1 */
                    class C29831 implements Comparator<TLRPC$TL_contact> {
                        C29831() {
                        }

                        public int compare(TLRPC$TL_contact tLRPC$TL_contact, TLRPC$TL_contact tLRPC$TL_contact2) {
                            return UserObject.getFirstName((User) hashMap.get(Integer.valueOf(tLRPC$TL_contact.user_id))).compareTo(UserObject.getFirstName((User) hashMap.get(Integer.valueOf(tLRPC$TL_contact2.user_id))));
                        }
                    }

                    /* renamed from: org.telegram.messenger.ContactsController$12$1$2 */
                    class C29842 implements Comparator<String> {
                        C29842() {
                        }

                        public int compare(String str, String str2) {
                            return str.charAt(0) == '#' ? 1 : str2.charAt(0) == '#' ? -1 : str.compareTo(str2);
                        }
                    }

                    /* renamed from: org.telegram.messenger.ContactsController$12$1$3 */
                    class C29853 implements Comparator<String> {
                        C29853() {
                        }

                        public int compare(String str, String str2) {
                            return str.charAt(0) == '#' ? 1 : str2.charAt(0) == '#' ? -1 : str.compareTo(str2);
                        }
                    }

                    public void run() {
                        HashMap hashMap;
                        HashMap hashMap2;
                        FileLog.m13726e("done loading contacts");
                        if (i == 1 && (arrayList.isEmpty() || Math.abs((System.currentTimeMillis() / 1000) - ((long) UserConfig.lastContactsSyncTime)) >= 86400)) {
                            ContactsController.this.loadContacts(false, ContactsController.this.getContactsHash(arrayList));
                            if (arrayList.isEmpty()) {
                                return;
                            }
                        }
                        if (i == 0) {
                            UserConfig.lastContactsSyncTime = (int) (System.currentTimeMillis() / 1000);
                            UserConfig.saveConfig(false);
                        }
                        int i = 0;
                        while (i < arrayList.size()) {
                            TLRPC$TL_contact tLRPC$TL_contact = (TLRPC$TL_contact) arrayList.get(i);
                            if (hashMap.get(Integer.valueOf(tLRPC$TL_contact.user_id)) != null || tLRPC$TL_contact.user_id == UserConfig.getClientUserId()) {
                                i++;
                            } else {
                                ContactsController.this.loadContacts(false, 0);
                                FileLog.m13726e("contacts are broken, load from server");
                                return;
                            }
                        }
                        if (i != 1) {
                            MessagesStorage.getInstance().putUsersAndChats(arrayList2, null, true, true);
                            MessagesStorage.getInstance().putContacts(arrayList, i != 2);
                        }
                        Collections.sort(arrayList, new C29831());
                        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(20, 1.0f, 2);
                        final HashMap hashMap3 = new HashMap();
                        final HashMap hashMap4 = new HashMap();
                        final Object arrayList = new ArrayList();
                        final Object arrayList2 = new ArrayList();
                        final SparseArray sparseArray = new SparseArray();
                        final HashMap hashMap5 = new HashMap();
                        final HashMap hashMap6 = new HashMap();
                        final ArrayList arrayList3 = new ArrayList();
                        final ArrayList arrayList4 = new ArrayList();
                        if (ContactsController.this.contactsBookLoaded) {
                            hashMap = null;
                            hashMap2 = null;
                        } else {
                            HashMap hashMap7 = new HashMap();
                            hashMap = new HashMap();
                            hashMap2 = hashMap7;
                        }
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            tLRPC$TL_contact = (TLRPC$TL_contact) arrayList.get(i2);
                            User user = (User) hashMap.get(Integer.valueOf(tLRPC$TL_contact.user_id));
                            if (user != null) {
                                String firstName;
                                String toUpperCase;
                                ArrayList arrayList5;
                                if (user.id == UserConfig.getClientUserId() || ((user.status != null && user.status.expires > ConnectionsManager.getInstance().getCurrentTime()) || MessagesController.getInstance().onlinePrivacy.containsKey(Integer.valueOf(user.id)))) {
                                    sparseArray.put(tLRPC$TL_contact.user_id, tLRPC$TL_contact);
                                    if (!(null == null || TextUtils.isEmpty(user.phone))) {
                                        null.put(user.phone, tLRPC$TL_contact);
                                    }
                                    firstName = UserObject.getFirstName(user);
                                    if (firstName.length() > 1) {
                                        firstName = firstName.substring(0, 1);
                                    }
                                    toUpperCase = firstName.length() == 0 ? "#" : firstName.toUpperCase();
                                    firstName = (String) ContactsController.this.sectionsToReplace.get(toUpperCase);
                                    if (firstName != null) {
                                        toUpperCase = firstName;
                                    }
                                    arrayList5 = (ArrayList) hashMap5.get(toUpperCase);
                                    if (arrayList5 == null) {
                                        arrayList5 = new ArrayList();
                                        hashMap5.put(toUpperCase, arrayList5);
                                        arrayList3.add(toUpperCase);
                                    }
                                    arrayList5.add(tLRPC$TL_contact);
                                    if (user.mutual_contact) {
                                        arrayList5 = (ArrayList) hashMap6.get(toUpperCase);
                                        if (arrayList5 == null) {
                                            arrayList5 = new ArrayList();
                                            hashMap6.put(toUpperCase, arrayList5);
                                            arrayList4.add(toUpperCase);
                                        }
                                        arrayList5.add(tLRPC$TL_contact);
                                    }
                                }
                                if (C3791b.m13980f(ApplicationLoader.applicationContext, (long) tLRPC$TL_contact.user_id)) {
                                    Log.d("alireza", "alireza user id is  hidden111 " + tLRPC$TL_contact.user_id);
                                } else {
                                    concurrentHashMap.put(Integer.valueOf(tLRPC$TL_contact.user_id), tLRPC$TL_contact);
                                    if (!(hashMap2 == null || TextUtils.isEmpty(user.phone))) {
                                        hashMap2.put(user.phone, tLRPC$TL_contact);
                                        hashMap.put(user.phone.substring(Math.max(0, user.phone.length() - 7)), tLRPC$TL_contact);
                                    }
                                    firstName = UserObject.getFirstName(user);
                                    if (firstName.length() > 1) {
                                        firstName = firstName.substring(0, 1);
                                    }
                                    toUpperCase = firstName.length() == 0 ? "#" : firstName.toUpperCase();
                                    firstName = (String) ContactsController.this.sectionsToReplace.get(toUpperCase);
                                    if (firstName != null) {
                                        toUpperCase = firstName;
                                    }
                                    arrayList5 = (ArrayList) hashMap3.get(toUpperCase);
                                    if (arrayList5 == null) {
                                        arrayList5 = new ArrayList();
                                        hashMap3.put(toUpperCase, arrayList5);
                                        arrayList.add(toUpperCase);
                                    }
                                    arrayList5.add(tLRPC$TL_contact);
                                    if (user.mutual_contact) {
                                        ArrayList arrayList6 = (ArrayList) hashMap4.get(toUpperCase);
                                        if (arrayList6 == null) {
                                            arrayList6 = new ArrayList();
                                            hashMap4.put(toUpperCase, arrayList6);
                                            arrayList2.add(toUpperCase);
                                        }
                                        arrayList6.add(tLRPC$TL_contact);
                                    }
                                }
                            }
                        }
                        Collections.sort(arrayList, new C29842());
                        Collections.sort(arrayList2, new C29853());
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                ContactsController.this.contacts = arrayList;
                                ContactsController.this.contactsDict = concurrentHashMap;
                                ContactsController.this.usersSectionsDict = hashMap3;
                                ContactsController.this.usersMutualSectionsDict = hashMap4;
                                ContactsController.this.sortedUsersSectionsArray = arrayList;
                                ContactsController.this.sortedUsersMutualSectionsArray = arrayList2;
                                ContactsController.this.contactsDict1 = sparseArray;
                                ContactsController.this.usersSectionsDict1 = hashMap5;
                                ContactsController.this.usersMutualSectionsDict1 = hashMap6;
                                ContactsController.this.sortedUsersSectionsArray1 = arrayList3;
                                ContactsController.this.sortedUsersMutualSectionsArray1 = arrayList4;
                                if (i != 2) {
                                    synchronized (ContactsController.loadContactsSync) {
                                        ContactsController.this.loadingContacts = false;
                                    }
                                }
                                ContactsController.this.performWriteContactsToPhoneBook();
                                ContactsController.this.updateUnregisteredContacts(arrayList);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsDidLoaded, new Object[0]);
                                if (i == 1 || isEmpty) {
                                    ContactsController.this.reloadContactsStatusesMaybe();
                                } else {
                                    ContactsController.this.saveContactsLoadTime();
                                }
                            }
                        });
                        if (!ContactsController.this.delayedContactsUpdate.isEmpty() && ContactsController.this.contactsLoaded && ContactsController.this.contactsBookLoaded) {
                            ContactsController.this.applyContactsUpdates(ContactsController.this.delayedContactsUpdate, null, null, null);
                            ContactsController.this.delayedContactsUpdate.clear();
                        }
                        if (hashMap2 != null) {
                            final HashMap hashMap8 = hashMap2;
                            AndroidUtilities.runOnUIThread(new Runnable() {

                                /* renamed from: org.telegram.messenger.ContactsController$12$1$5$1 */
                                class C29871 implements Runnable {
                                    C29871() {
                                    }

                                    public void run() {
                                        ContactsController.this.contactsByPhone = hashMap8;
                                        ContactsController.this.contactsByShortPhone = hashMap;
                                    }
                                }

                                public void run() {
                                    Utilities.globalQueue.postRunnable(new C29871());
                                    if (!ContactsController.this.contactsSyncInProgress) {
                                        ContactsController.this.contactsSyncInProgress = true;
                                        MessagesStorage.getInstance().getCachedPhoneBook(false);
                                    }
                                }
                            });
                            return;
                        }
                        ContactsController.this.contactsLoaded = true;
                    }
                });
            }
        });
    }

    public void readContacts() {
        synchronized (loadContactsSync) {
            if (this.loadingContacts) {
                return;
            }
            this.loadingContacts = true;
            Utilities.stageQueue.postRunnable(new C30067());
        }
    }

    public void reloadContactsStatuses() {
        saveContactsLoadTime();
        MessagesController.getInstance().clearFullUsers();
        final Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putBoolean("needGetStatuses", true).commit();
        ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_contacts_getStatuses(), new RequestDelegate() {
            public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error == null) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            edit.remove("needGetStatuses").commit();
                            TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
                            if (!tLRPC$Vector.objects.isEmpty()) {
                                ArrayList arrayList = new ArrayList();
                                Iterator it = tLRPC$Vector.objects.iterator();
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    User tLRPC$TL_user = new TLRPC$TL_user();
                                    TLRPC$TL_contactStatus tLRPC$TL_contactStatus = (TLRPC$TL_contactStatus) next;
                                    if (tLRPC$TL_contactStatus != null) {
                                        if (tLRPC$TL_contactStatus.status instanceof TLRPC$TL_userStatusRecently) {
                                            tLRPC$TL_contactStatus.status.expires = -100;
                                        } else if (tLRPC$TL_contactStatus.status instanceof TLRPC$TL_userStatusLastWeek) {
                                            tLRPC$TL_contactStatus.status.expires = -101;
                                        } else if (tLRPC$TL_contactStatus.status instanceof TLRPC$TL_userStatusLastMonth) {
                                            tLRPC$TL_contactStatus.status.expires = -102;
                                        }
                                        User user = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contactStatus.user_id));
                                        if (user != null) {
                                            user.status = tLRPC$TL_contactStatus.status;
                                        }
                                        tLRPC$TL_user.status = tLRPC$TL_contactStatus.status;
                                        arrayList.add(tLRPC$TL_user);
                                    }
                                }
                                MessagesStorage.getInstance().updateUsers(arrayList, true, true, true);
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(4));
                        }
                    });
                }
            }
        });
    }

    public void resetImportedContacts() {
        ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_contacts_resetSaved(), new C30056());
    }

    public void setDeleteAccountTTL(int i) {
        this.deleteAccountTTL = i;
    }

    public void setPrivacyRules(ArrayList<PrivacyRule> arrayList, int i) {
        if (i == 2) {
            this.callPrivacyRules = arrayList;
        } else if (i == 1) {
            this.groupPrivacyRules = arrayList;
        } else {
            this.privacyRules = arrayList;
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.privacyRulesUpdated, new Object[0]);
        reloadContactsStatuses();
    }

    public void syncPhoneBookByAlert(HashMap<String, Contact> hashMap, boolean z, boolean z2, boolean z3) {
        final HashMap<String, Contact> hashMap2 = hashMap;
        final boolean z4 = z;
        final boolean z5 = z2;
        final boolean z6 = z3;
        Utilities.globalQueue.postRunnable(new Runnable() {
            public void run() {
                ContactsController.getInstance().performSyncPhoneBook(hashMap2, true, z4, z5, false, false, z6);
            }
        });
    }

    public void updateUnregisteredContacts(ArrayList<TLRPC$TL_contact> arrayList) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < arrayList.size(); i++) {
            TLRPC$TL_contact tLRPC$TL_contact = (TLRPC$TL_contact) arrayList.get(i);
            User user = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contact.user_id));
            if (!(user == null || TextUtils.isEmpty(user.phone))) {
                hashMap.put(user.phone, tLRPC$TL_contact);
            }
        }
        Object arrayList2 = new ArrayList();
        for (Entry value : this.contactsBook.entrySet()) {
            Object obj;
            Contact contact = (Contact) value.getValue();
            int i2 = 0;
            while (i2 < contact.phones.size()) {
                if (hashMap.containsKey((String) contact.shortPhones.get(i2)) || ((Integer) contact.phoneDeleted.get(i2)).intValue() == 1) {
                    obj = 1;
                    break;
                }
                i2++;
            }
            obj = null;
            if (obj == null) {
                arrayList2.add(contact);
            }
        }
        Collections.sort(arrayList2, new Comparator<Contact>() {
            public int compare(Contact contact, Contact contact2) {
                String str = contact.first_name;
                if (str.length() == 0) {
                    str = contact.last_name;
                }
                String str2 = contact2.first_name;
                if (str2.length() == 0) {
                    str2 = contact2.last_name;
                }
                return str.compareTo(str2);
            }
        });
        this.phoneBookContacts = arrayList2;
    }
}
