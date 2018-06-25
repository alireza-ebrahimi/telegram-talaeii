package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$124 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ ArrayList val$chatsArr;
    final /* synthetic */ ArrayList val$usersArr;

    MessagesController$124(MessagesController this$0, ArrayList arrayList, ArrayList arrayList2) {
        this.this$0 = this$0;
        this.val$usersArr = arrayList;
        this.val$chatsArr = arrayList2;
    }

    public void run() {
        this.this$0.putUsers(this.val$usersArr, false);
        this.this$0.putChats(this.val$chatsArr, false);
    }
}
