package org.telegram.messenger;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC$TL_contacts_importContacts;
import org.telegram.tgnet.TLRPC$TL_contacts_importedContacts;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_importedContact;
import org.telegram.tgnet.TLRPC$TL_inputPhoneContact;
import org.telegram.tgnet.TLRPC$TL_popularContact;
import org.telegram.tgnet.TLRPC.User;

class ContactsController$9 implements Runnable {
    final /* synthetic */ ContactsController this$0;
    final /* synthetic */ boolean val$canceled;
    final /* synthetic */ boolean val$checkCount;
    final /* synthetic */ HashMap val$contactHashMap;
    final /* synthetic */ boolean val$first;
    final /* synthetic */ boolean val$force;
    final /* synthetic */ boolean val$request;
    final /* synthetic */ boolean val$schedule;

    /* renamed from: org.telegram.messenger.ContactsController$9$1 */
    class C13031 implements Runnable {
        C13031() {
        }

        public void run() {
            ArrayList<User> toDelete = new ArrayList();
            if (!(ContactsController$9.this.val$contactHashMap == null || ContactsController$9.this.val$contactHashMap.isEmpty())) {
                try {
                    int a;
                    User user;
                    HashMap<String, User> contactsPhonesShort = new HashMap();
                    for (a = 0; a < ContactsController$9.this.this$0.contacts.size(); a++) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) ContactsController$9.this.this$0.contacts.get(a)).user_id));
                        if (!(user == null || TextUtils.isEmpty(user.phone))) {
                            contactsPhonesShort.put(user.phone, user);
                        }
                    }
                    int removed = 0;
                    for (Entry<String, ContactsController$Contact> entry : ContactsController$9.this.val$contactHashMap.entrySet()) {
                        ContactsController$Contact contact = (ContactsController$Contact) entry.getValue();
                        boolean was = false;
                        a = 0;
                        while (a < contact.shortPhones.size()) {
                            user = (User) contactsPhonesShort.get((String) contact.shortPhones.get(a));
                            if (user != null) {
                                was = true;
                                toDelete.add(user);
                                contact.shortPhones.remove(a);
                                a--;
                            }
                            a++;
                        }
                        if (!was || contact.shortPhones.size() == 0) {
                            removed++;
                        }
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            if (!toDelete.isEmpty()) {
                ContactsController$9.this.this$0.deleteContact(toDelete);
            }
        }
    }

    ContactsController$9(ContactsController this$0, HashMap hashMap, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.this$0 = this$0;
        this.val$contactHashMap = hashMap;
        this.val$schedule = z;
        this.val$request = z2;
        this.val$first = z3;
        this.val$force = z4;
        this.val$checkCount = z5;
        this.val$canceled = z6;
    }

    public void run() {
        ContactsController$Contact value;
        int newPhonebookContacts = 0;
        int serverContactsInPhonebook = 0;
        HashMap<String, ContactsController$Contact> contactShortHashMap = new HashMap();
        for (Entry<String, ContactsController$Contact> entry : this.val$contactHashMap.entrySet()) {
            int a;
            ContactsController$Contact c = (ContactsController$Contact) entry.getValue();
            for (a = 0; a < c.shortPhones.size(); a++) {
                contactShortHashMap.put(c.shortPhones.get(a), c);
            }
        }
        FileLog.e("start read contacts from phone");
        if (!this.val$schedule) {
            ContactsController.access$400(this.this$0);
        }
        final HashMap<String, ContactsController$Contact> contactsMap = ContactsController.access$700(this.this$0);
        final HashMap<String, ContactsController$Contact> contactsBookShort = new HashMap();
        int oldCount = this.val$contactHashMap.size();
        ArrayList<TLRPC$TL_inputPhoneContact> toImport = new ArrayList();
        String sphone;
        String sphone9;
        TLRPC$TL_inputPhoneContact imp;
        TLRPC$TL_contact contact;
        User user;
        String firstName;
        String lastName;
        if (!this.val$contactHashMap.isEmpty()) {
            for (Entry<String, ContactsController$Contact> pair : contactsMap.entrySet()) {
                String id = (String) pair.getKey();
                value = (ContactsController$Contact) pair.getValue();
                ContactsController$Contact existing = (ContactsController$Contact) this.val$contactHashMap.get(id);
                if (existing == null) {
                    for (a = 0; a < value.shortPhones.size(); a++) {
                        c = (ContactsController$Contact) contactShortHashMap.get(value.shortPhones.get(a));
                        if (c != null) {
                            existing = c;
                            id = existing.key;
                            break;
                        }
                    }
                }
                if (existing != null) {
                    value.imported = existing.imported;
                }
                boolean nameChanged = (existing == null || ((TextUtils.isEmpty(value.first_name) || existing.first_name.equals(value.first_name)) && (TextUtils.isEmpty(value.last_name) || existing.last_name.equals(value.last_name)))) ? false : true;
                int index;
                if (existing == null || nameChanged) {
                    for (a = 0; a < value.phones.size(); a++) {
                        sphone = (String) value.shortPhones.get(a);
                        sphone9 = sphone.substring(Math.max(0, sphone.length() - 7));
                        contactsBookShort.put(sphone, value);
                        if (existing != null) {
                            index = existing.shortPhones.indexOf(sphone);
                            if (index != -1) {
                                Integer deleted = (Integer) existing.phoneDeleted.get(index);
                                value.phoneDeleted.set(a, deleted);
                                if (deleted.intValue() == 1) {
                                }
                            }
                        }
                        if (this.val$request) {
                            if (!nameChanged) {
                                if (this.this$0.contactsByPhone.containsKey(sphone)) {
                                    serverContactsInPhonebook++;
                                } else {
                                    newPhonebookContacts++;
                                }
                            }
                            imp = new TLRPC$TL_inputPhoneContact();
                            imp.client_id = (long) value.contact_id;
                            imp.client_id |= ((long) a) << 32;
                            imp.first_name = value.first_name;
                            imp.last_name = value.last_name;
                            imp.phone = (String) value.phones.get(a);
                            toImport.add(imp);
                        }
                    }
                    if (existing != null) {
                        this.val$contactHashMap.remove(id);
                    }
                } else {
                    for (a = 0; a < value.phones.size(); a++) {
                        sphone = (String) value.shortPhones.get(a);
                        sphone9 = sphone.substring(Math.max(0, sphone.length() - 7));
                        contactsBookShort.put(sphone, value);
                        index = existing.shortPhones.indexOf(sphone);
                        boolean emptyNameReimport = false;
                        if (this.val$request) {
                            contact = (TLRPC$TL_contact) this.this$0.contactsByPhone.get(sphone);
                            if (contact != null) {
                                user = MessagesController.getInstance().getUser(Integer.valueOf(contact.user_id));
                                if (user != null) {
                                    serverContactsInPhonebook++;
                                    if (TextUtils.isEmpty(user.first_name) && TextUtils.isEmpty(user.last_name) && !(TextUtils.isEmpty(value.first_name) && TextUtils.isEmpty(value.last_name))) {
                                        index = -1;
                                        emptyNameReimport = true;
                                    }
                                }
                            } else if (this.this$0.contactsByShortPhone.containsKey(sphone9)) {
                                serverContactsInPhonebook++;
                            }
                        }
                        if (index != -1) {
                            value.phoneDeleted.set(a, existing.phoneDeleted.get(index));
                            existing.phones.remove(index);
                            existing.shortPhones.remove(index);
                            existing.phoneDeleted.remove(index);
                            existing.phoneTypes.remove(index);
                        } else if (this.val$request) {
                            if (!emptyNameReimport) {
                                contact = (TLRPC$TL_contact) this.this$0.contactsByPhone.get(sphone);
                                if (contact != null) {
                                    user = MessagesController.getInstance().getUser(Integer.valueOf(contact.user_id));
                                    if (user != null) {
                                        serverContactsInPhonebook++;
                                        firstName = user.first_name != null ? user.first_name : "";
                                        lastName = user.last_name != null ? user.last_name : "";
                                        if (firstName.equals(value.first_name)) {
                                            if (lastName.equals(value.last_name)) {
                                            }
                                        }
                                        if (TextUtils.isEmpty(value.first_name) && TextUtils.isEmpty(value.last_name)) {
                                        }
                                    } else {
                                        newPhonebookContacts++;
                                    }
                                } else if (this.this$0.contactsByShortPhone.containsKey(sphone9)) {
                                    serverContactsInPhonebook++;
                                }
                            }
                            imp = new TLRPC$TL_inputPhoneContact();
                            imp.client_id = (long) value.contact_id;
                            imp.client_id |= ((long) a) << 32;
                            imp.first_name = value.first_name;
                            imp.last_name = value.last_name;
                            imp.phone = (String) value.phones.get(a);
                            toImport.add(imp);
                        }
                    }
                    if (existing.phones.isEmpty()) {
                        this.val$contactHashMap.remove(id);
                    }
                }
            }
            if (!this.val$first && this.val$contactHashMap.isEmpty() && toImport.isEmpty() && oldCount == contactsMap.size()) {
                FileLog.e("contacts not changed!");
                return;
            } else if (!(!this.val$request || this.val$contactHashMap.isEmpty() || contactsMap.isEmpty())) {
                if (toImport.isEmpty()) {
                    MessagesStorage.getInstance().putCachedPhoneBook(contactsMap, false);
                }
                if (!(true || this.val$contactHashMap.isEmpty())) {
                    AndroidUtilities.runOnUIThread(new C13031());
                }
            }
        } else if (this.val$request) {
            for (Entry<String, ContactsController$Contact> pair2 : contactsMap.entrySet()) {
                value = (ContactsController$Contact) pair2.getValue();
                String key = (String) pair2.getKey();
                for (a = 0; a < value.phones.size(); a++) {
                    if (!this.val$force) {
                        sphone = (String) value.shortPhones.get(a);
                        sphone9 = sphone.substring(Math.max(0, sphone.length() - 7));
                        contact = (TLRPC$TL_contact) this.this$0.contactsByPhone.get(sphone);
                        if (contact != null) {
                            user = MessagesController.getInstance().getUser(Integer.valueOf(contact.user_id));
                            if (user != null) {
                                serverContactsInPhonebook++;
                                firstName = user.first_name != null ? user.first_name : "";
                                lastName = user.last_name != null ? user.last_name : "";
                                if (firstName.equals(value.first_name)) {
                                    if (lastName.equals(value.last_name)) {
                                    }
                                }
                                if (TextUtils.isEmpty(value.first_name) && TextUtils.isEmpty(value.last_name)) {
                                }
                            }
                        } else if (this.this$0.contactsByShortPhone.containsKey(sphone9)) {
                            serverContactsInPhonebook++;
                        }
                    }
                    imp = new TLRPC$TL_inputPhoneContact();
                    imp.client_id = (long) value.contact_id;
                    imp.client_id |= ((long) a) << 32;
                    imp.first_name = value.first_name;
                    imp.last_name = value.last_name;
                    imp.phone = (String) value.phones.get(a);
                    toImport.add(imp);
                }
            }
        }
        FileLog.e("done processing contacts");
        if (!this.val$request) {
            Utilities.stageQueue.postRunnable(new Runnable() {
                public void run() {
                    ContactsController$9.this.this$0.contactsBookSPhones = contactsBookShort;
                    ContactsController$9.this.this$0.contactsBook = contactsMap;
                    ContactsController.access$802(ContactsController$9.this.this$0, false);
                    ContactsController.access$902(ContactsController$9.this.this$0, true);
                    if (ContactsController$9.this.val$first) {
                        ContactsController$9.this.this$0.contactsLoaded = true;
                    }
                    if (!ContactsController.access$1000(ContactsController$9.this.this$0).isEmpty() && ContactsController$9.this.this$0.contactsLoaded && ContactsController.access$900(ContactsController$9.this.this$0)) {
                        ContactsController.access$1100(ContactsController$9.this.this$0, ContactsController.access$1000(ContactsController$9.this.this$0), null, null, null);
                        ContactsController.access$1000(ContactsController$9.this.this$0).clear();
                    }
                }
            });
            if (!contactsMap.isEmpty()) {
                MessagesStorage.getInstance().putCachedPhoneBook(contactsMap, false);
            }
        } else if (toImport.isEmpty()) {
            Utilities.stageQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.ContactsController$9$5$1 */
                class C13111 implements Runnable {
                    C13111() {
                    }

                    public void run() {
                        ContactsController$9.this.this$0.updateUnregisteredContacts(ContactsController$9.this.this$0.contacts);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsDidLoaded, new Object[0]);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsImported, new Object[0]);
                    }
                }

                public void run() {
                    ContactsController$9.this.this$0.contactsBookSPhones = contactsBookShort;
                    ContactsController$9.this.this$0.contactsBook = contactsMap;
                    ContactsController.access$802(ContactsController$9.this.this$0, false);
                    ContactsController.access$902(ContactsController$9.this.this$0, true);
                    if (ContactsController$9.this.val$first) {
                        ContactsController$9.this.this$0.contactsLoaded = true;
                    }
                    if (!ContactsController.access$1000(ContactsController$9.this.this$0).isEmpty() && ContactsController$9.this.this$0.contactsLoaded) {
                        ContactsController.access$1100(ContactsController$9.this.this$0, ContactsController.access$1000(ContactsController$9.this.this$0), null, null, null);
                        ContactsController.access$1000(ContactsController$9.this.this$0).clear();
                    }
                    AndroidUtilities.runOnUIThread(new C13111());
                }
            });
        } else {
            int checkType;
            if (!this.val$checkCount) {
                checkType = 0;
            } else if (newPhonebookContacts >= 30) {
                checkType = 1;
            } else if (this.val$first && this.val$contactHashMap.isEmpty() && this.this$0.contactsByPhone.size() - serverContactsInPhonebook > (this.this$0.contactsByPhone.size() / 3) * 2) {
                checkType = 2;
            } else {
                checkType = 0;
            }
            FileLog.d("new phone book contacts " + newPhonebookContacts + " serverContactsInPhonebook " + serverContactsInPhonebook + " totalContacts " + this.this$0.contactsByPhone.size());
            if (checkType != 0) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.hasNewContactsToImport, new Object[]{Integer.valueOf(checkType), ContactsController$9.this.val$contactHashMap, Boolean.valueOf(ContactsController$9.this.val$first), Boolean.valueOf(ContactsController$9.this.val$schedule)});
                    }
                });
            } else if (this.val$canceled) {
                Utilities.stageQueue.postRunnable(new Runnable() {

                    /* renamed from: org.telegram.messenger.ContactsController$9$3$1 */
                    class C13051 implements Runnable {
                        C13051() {
                        }

                        public void run() {
                            ContactsController$9.this.this$0.updateUnregisteredContacts(ContactsController$9.this.this$0.contacts);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsDidLoaded, new Object[0]);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsImported, new Object[0]);
                        }
                    }

                    public void run() {
                        ContactsController$9.this.this$0.contactsBookSPhones = contactsBookShort;
                        ContactsController$9.this.this$0.contactsBook = contactsMap;
                        ContactsController.access$802(ContactsController$9.this.this$0, false);
                        ContactsController.access$902(ContactsController$9.this.this$0, true);
                        if (ContactsController$9.this.val$first) {
                            ContactsController$9.this.this$0.contactsLoaded = true;
                        }
                        if (!ContactsController.access$1000(ContactsController$9.this.this$0).isEmpty() && ContactsController$9.this.this$0.contactsLoaded) {
                            ContactsController.access$1100(ContactsController$9.this.this$0, ContactsController.access$1000(ContactsController$9.this.this$0), null, null, null);
                            ContactsController.access$1000(ContactsController$9.this.this$0).clear();
                        }
                        MessagesStorage.getInstance().putCachedPhoneBook(contactsMap, false);
                        AndroidUtilities.runOnUIThread(new C13051());
                    }
                });
            } else {
                final boolean[] hasErrors = new boolean[]{false};
                final HashMap<String, ContactsController$Contact> contactsMapToSave = new HashMap(contactsMap);
                final HashMap<Integer, String> contactIdToKey = new HashMap();
                for (Entry<String, ContactsController$Contact> entry2 : contactsMapToSave.entrySet()) {
                    value = (ContactsController$Contact) entry2.getValue();
                    contactIdToKey.put(Integer.valueOf(value.contact_id), value.key);
                }
                ContactsController.access$102(this.this$0, 0);
                final int count = (int) Math.ceil(((double) toImport.size()) / 500.0d);
                for (a = 0; a < count; a++) {
                    final TLRPC$TL_contacts_importContacts req = new TLRPC$TL_contacts_importContacts();
                    int start = a * 500;
                    req.contacts = new ArrayList(toImport.subList(start, Math.min(start + 500, toImport.size())));
                    ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {

                        /* renamed from: org.telegram.messenger.ContactsController$9$4$1 */
                        class C13091 implements Runnable {

                            /* renamed from: org.telegram.messenger.ContactsController$9$4$1$1 */
                            class C13071 implements Runnable {
                                C13071() {
                                }

                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsImported, new Object[0]);
                                }
                            }

                            /* renamed from: org.telegram.messenger.ContactsController$9$4$1$2 */
                            class C13082 implements Runnable {
                                C13082() {
                                }

                                public void run() {
                                    MessagesStorage.getInstance().getCachedPhoneBook(true);
                                }
                            }

                            C13091() {
                            }

                            public void run() {
                                ContactsController$9.this.this$0.contactsBookSPhones = contactsBookShort;
                                ContactsController$9.this.this$0.contactsBook = contactsMap;
                                ContactsController.access$802(ContactsController$9.this.this$0, false);
                                ContactsController.access$902(ContactsController$9.this.this$0, true);
                                if (ContactsController$9.this.val$first) {
                                    ContactsController$9.this.this$0.contactsLoaded = true;
                                }
                                if (!ContactsController.access$1000(ContactsController$9.this.this$0).isEmpty() && ContactsController$9.this.this$0.contactsLoaded) {
                                    ContactsController.access$1100(ContactsController$9.this.this$0, ContactsController.access$1000(ContactsController$9.this.this$0), null, null, null);
                                    ContactsController.access$1000(ContactsController$9.this.this$0).clear();
                                }
                                AndroidUtilities.runOnUIThread(new C13071());
                                if (hasErrors[0]) {
                                    Utilities.globalQueue.postRunnable(new C13082(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                                }
                            }
                        }

                        public void run(TLObject response, TLRPC$TL_error error) {
                            ContactsController.access$108(ContactsController$9.this.this$0);
                            int a;
                            if (error == null) {
                                FileLog.e("contacts imported");
                                TLRPC$TL_contacts_importedContacts res = (TLRPC$TL_contacts_importedContacts) response;
                                if (!res.retry_contacts.isEmpty()) {
                                    for (a = 0; a < res.retry_contacts.size(); a++) {
                                        contactsMapToSave.remove(contactIdToKey.get(Integer.valueOf((int) ((Long) res.retry_contacts.get(a)).longValue())));
                                    }
                                    hasErrors[0] = true;
                                }
                                for (a = 0; a < res.popular_invites.size(); a++) {
                                    TLRPC$TL_popularContact popularContact = (TLRPC$TL_popularContact) res.popular_invites.get(a);
                                    ContactsController$Contact contact = (ContactsController$Contact) contactsMap.get(contactIdToKey.get(Integer.valueOf((int) popularContact.client_id)));
                                    if (contact != null) {
                                        contact.imported = popularContact.importers;
                                    }
                                }
                                MessagesStorage.getInstance().putUsersAndChats(res.users, null, true, true);
                                ArrayList<TLRPC$TL_contact> cArr = new ArrayList();
                                for (a = 0; a < res.imported.size(); a++) {
                                    TLRPC$TL_contact contact2 = new TLRPC$TL_contact();
                                    contact2.user_id = ((TLRPC$TL_importedContact) res.imported.get(a)).user_id;
                                    cArr.add(contact2);
                                }
                                ContactsController$9.this.this$0.processLoadedContacts(cArr, res.users, 2);
                            } else {
                                for (a = 0; a < req.contacts.size(); a++) {
                                    contactsMapToSave.remove(contactIdToKey.get(Integer.valueOf((int) ((TLRPC$TL_inputPhoneContact) req.contacts.get(a)).client_id)));
                                }
                                hasErrors[0] = true;
                                FileLog.e("import contacts error " + error.text);
                            }
                            if (ContactsController.access$100(ContactsController$9.this.this$0) == count) {
                                if (!contactsMapToSave.isEmpty()) {
                                    MessagesStorage.getInstance().putCachedPhoneBook(contactsMapToSave, false);
                                }
                                Utilities.stageQueue.postRunnable(new C13091());
                            }
                        }
                    }, 6);
                }
            }
        }
    }
}
