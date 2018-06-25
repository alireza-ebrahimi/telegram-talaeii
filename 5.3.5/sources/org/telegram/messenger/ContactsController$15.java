package org.telegram.messenger;

import java.util.Comparator;

class ContactsController$15 implements Comparator<String> {
    final /* synthetic */ ContactsController this$0;

    ContactsController$15(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public int compare(String s, String s2) {
        char cv1 = s.charAt(0);
        char cv2 = s2.charAt(0);
        if (cv1 == '#') {
            return 1;
        }
        if (cv2 == '#') {
            return -1;
        }
        return s.compareTo(s2);
    }
}
