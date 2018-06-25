package org.telegram.messenger;

import java.util.HashMap;
import java.util.Map.Entry;

class ContactsController$8 implements Runnable {
    final /* synthetic */ ContactsController this$0;
    final /* synthetic */ HashMap val$contactHashMap;

    ContactsController$8(ContactsController this$0, HashMap hashMap) {
        this.this$0 = this$0;
        this.val$contactHashMap = hashMap;
    }

    public void run() {
        if (!ContactsController.access$000(this.this$0)) {
            ContactsController$Contact value;
            int a;
            ContactsController.access$002(this.this$0, true);
            HashMap<String, ContactsController$Contact> migratedMap = new HashMap();
            HashMap<String, ContactsController$Contact> contactsMap = ContactsController.access$700(this.this$0);
            HashMap<String, String> contactsBookShort = new HashMap();
            for (Entry<String, ContactsController$Contact> entry : contactsMap.entrySet()) {
                value = (ContactsController$Contact) entry.getValue();
                for (a = 0; a < value.shortPhones.size(); a++) {
                    contactsBookShort.put(value.shortPhones.get(a), value.key);
                }
            }
            for (Entry<Integer, ContactsController$Contact> entry2 : this.val$contactHashMap.entrySet()) {
                value = (ContactsController$Contact) entry2.getValue();
                for (a = 0; a < value.shortPhones.size(); a++) {
                    String key = (String) contactsBookShort.get((String) value.shortPhones.get(a));
                    if (key != null) {
                        value.key = key;
                        migratedMap.put(key, value);
                        break;
                    }
                }
            }
            FileLog.d("migrated contacts " + migratedMap.size() + " of " + this.val$contactHashMap.size());
            MessagesStorage.getInstance().putCachedPhoneBook(migratedMap, true);
        }
    }
}
