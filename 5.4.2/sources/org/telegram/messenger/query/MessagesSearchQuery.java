package org.telegram.messenger.query;

import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterEmpty;
import org.telegram.tgnet.TLRPC$TL_messageActionHistoryClear;
import org.telegram.tgnet.TLRPC$TL_messageEmpty;
import org.telegram.tgnet.TLRPC$TL_messages_channelMessages;
import org.telegram.tgnet.TLRPC$TL_messages_messagesSlice;
import org.telegram.tgnet.TLRPC$TL_messages_search;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.InputPeer;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.User;

public class MessagesSearchQuery {
    private static long lastMergeDialogId;
    private static int lastReqId;
    private static int lastReturnedNum;
    private static String lastSearchQuery;
    private static int mergeReqId;
    private static int[] messagesSearchCount = new int[]{0, 0};
    private static boolean[] messagesSearchEndReached = new boolean[]{false, false};
    private static int reqId;
    private static ArrayList<MessageObject> searchResultMessages = new ArrayList();
    private static HashMap<Integer, MessageObject>[] searchResultMessagesMap = new HashMap[]{new HashMap(), new HashMap()};

    public static String getLastSearchQuery() {
        return lastSearchQuery;
    }

    private static int getMask() {
        int i = 0;
        if (!(lastReturnedNum >= searchResultMessages.size() - 1 && messagesSearchEndReached[0] && messagesSearchEndReached[1])) {
            i = 1;
        }
        return lastReturnedNum > 0 ? i | 2 : i;
    }

    public static boolean isMessageFound(int i, boolean z) {
        return searchResultMessagesMap[z ? 1 : 0].containsKey(Integer.valueOf(i));
    }

    public static void searchMessagesInChat(String str, long j, long j2, int i, int i2, User user) {
        searchMessagesInChat(str, j, j2, i, i2, false, user);
    }

    private static void searchMessagesInChat(String str, long j, long j2, int i, int i2, boolean z, User user) {
        long j3;
        int i3;
        final TLObject tLRPC$TL_messages_search;
        int i4 = 0;
        Object obj = !z ? 1 : null;
        if (reqId != 0) {
            ConnectionsManager.getInstance().cancelRequest(reqId, true);
            reqId = 0;
        }
        if (mergeReqId != 0) {
            ConnectionsManager.getInstance().cancelRequest(mergeReqId, true);
            mergeReqId = 0;
        }
        if (str != null) {
            if (obj != null) {
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatSearchResultsLoading, Integer.valueOf(i));
                boolean[] zArr = messagesSearchEndReached;
                messagesSearchEndReached[1] = false;
                zArr[0] = false;
                int[] iArr = messagesSearchCount;
                messagesSearchCount[1] = 0;
                iArr[0] = 0;
                searchResultMessages.clear();
                searchResultMessagesMap[0].clear();
                searchResultMessagesMap[1].clear();
            }
            j3 = j;
        } else if (!searchResultMessages.isEmpty()) {
            MessageObject messageObject;
            if (i2 == 1) {
                lastReturnedNum++;
                if (lastReturnedNum < searchResultMessages.size()) {
                    messageObject = (MessageObject) searchResultMessages.get(lastReturnedNum);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatSearchResultsAvailable, Integer.valueOf(i), Integer.valueOf(messageObject.getId()), Integer.valueOf(getMask()), Long.valueOf(messageObject.getDialogId()), Integer.valueOf(lastReturnedNum), Integer.valueOf(messagesSearchCount[0] + messagesSearchCount[1]));
                    return;
                } else if (messagesSearchEndReached[0] && j2 == 0 && messagesSearchEndReached[1]) {
                    lastReturnedNum--;
                    return;
                } else {
                    long j4;
                    str = lastSearchQuery;
                    messageObject = (MessageObject) searchResultMessages.get(searchResultMessages.size() - 1);
                    if (messageObject.getDialogId() != j || messagesSearchEndReached[0]) {
                        int id = messageObject.getDialogId() == j2 ? messageObject.getId() : 0;
                        messagesSearchEndReached[1] = false;
                        i3 = id;
                        j4 = j2;
                    } else {
                        i3 = messageObject.getId();
                        j4 = j;
                    }
                    long j5 = j4;
                    obj = null;
                    i4 = i3;
                    j3 = j5;
                }
            } else if (i2 == 2) {
                lastReturnedNum--;
                if (lastReturnedNum < 0) {
                    lastReturnedNum = 0;
                    return;
                }
                if (lastReturnedNum >= searchResultMessages.size()) {
                    lastReturnedNum = searchResultMessages.size() - 1;
                }
                messageObject = (MessageObject) searchResultMessages.get(lastReturnedNum);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatSearchResultsAvailable, Integer.valueOf(i), Integer.valueOf(messageObject.getId()), Integer.valueOf(getMask()), Long.valueOf(messageObject.getDialogId()), Integer.valueOf(lastReturnedNum), Integer.valueOf(messagesSearchCount[0] + messagesSearchCount[1]));
                return;
            } else {
                return;
            }
        } else {
            return;
        }
        final long j6 = (!messagesSearchEndReached[0] || messagesSearchEndReached[1] || j2 == 0) ? j3 : j2;
        if (j6 == j && r2 != null) {
            if (j2 != 0) {
                InputPeer inputPeer = MessagesController.getInputPeer((int) j2);
                if (inputPeer != null) {
                    tLRPC$TL_messages_search = new TLRPC$TL_messages_search();
                    tLRPC$TL_messages_search.peer = inputPeer;
                    lastMergeDialogId = j2;
                    tLRPC$TL_messages_search.limit = 1;
                    if (str == null) {
                        str = TtmlNode.ANONYMOUS_REGION_ID;
                    }
                    tLRPC$TL_messages_search.f10166q = str;
                    if (user != null) {
                        tLRPC$TL_messages_search.from_id = MessagesController.getInputUser(user);
                        tLRPC$TL_messages_search.flags |= 1;
                    }
                    tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterEmpty();
                    final long j7 = j2;
                    j6 = j;
                    final int i5 = i;
                    final int i6 = i2;
                    final User user2 = user;
                    mergeReqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_search, new RequestDelegate() {
                        public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (MessagesSearchQuery.lastMergeDialogId == j7) {
                                        MessagesSearchQuery.mergeReqId = 0;
                                        if (tLObject != null) {
                                            TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                                            MessagesSearchQuery.messagesSearchEndReached[1] = tLRPC$messages_Messages.messages.isEmpty();
                                            MessagesSearchQuery.messagesSearchCount[1] = tLRPC$messages_Messages instanceof TLRPC$TL_messages_messagesSlice ? tLRPC$messages_Messages.count : tLRPC$messages_Messages.messages.size();
                                            MessagesSearchQuery.searchMessagesInChat(tLRPC$TL_messages_search.f10166q, j6, j7, i5, i6, true, user2);
                                        }
                                    }
                                }
                            });
                        }
                    }, 2);
                    return;
                }
                return;
            }
            lastMergeDialogId = 0;
            messagesSearchEndReached[1] = true;
            messagesSearchCount[1] = 0;
        }
        tLRPC$TL_messages_search = new TLRPC$TL_messages_search();
        tLRPC$TL_messages_search.peer = MessagesController.getInputPeer((int) j6);
        if (tLRPC$TL_messages_search.peer != null) {
            tLRPC$TL_messages_search.limit = 21;
            tLRPC$TL_messages_search.f10166q = str != null ? str : TtmlNode.ANONYMOUS_REGION_ID;
            tLRPC$TL_messages_search.offset_id = i4;
            if (user != null) {
                tLRPC$TL_messages_search.from_id = MessagesController.getInputUser(user);
                tLRPC$TL_messages_search.flags |= 1;
            }
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterEmpty();
            i3 = lastReqId + 1;
            lastReqId = i3;
            lastSearchQuery = str;
            final long j8 = j;
            final int i7 = i;
            final long j9 = j2;
            final User user3 = user;
            reqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_search, new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (i3 == MessagesSearchQuery.lastReqId) {
                                MessagesSearchQuery.reqId = 0;
                                if (tLObject != null) {
                                    TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                                    int i = 0;
                                    while (i < tLRPC$messages_Messages.messages.size()) {
                                        Message message = (Message) tLRPC$messages_Messages.messages.get(i);
                                        if ((message instanceof TLRPC$TL_messageEmpty) || (message.action instanceof TLRPC$TL_messageActionHistoryClear)) {
                                            tLRPC$messages_Messages.messages.remove(i);
                                            i--;
                                        }
                                        i++;
                                    }
                                    MessagesStorage.getInstance().putUsersAndChats(tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, true, true);
                                    MessagesController.getInstance().putUsers(tLRPC$messages_Messages.users, false);
                                    MessagesController.getInstance().putChats(tLRPC$messages_Messages.chats, false);
                                    if (tLRPC$TL_messages_search.offset_id == 0 && j6 == j8) {
                                        MessagesSearchQuery.lastReturnedNum = 0;
                                        MessagesSearchQuery.searchResultMessages.clear();
                                        MessagesSearchQuery.searchResultMessagesMap[0].clear();
                                        MessagesSearchQuery.searchResultMessagesMap[1].clear();
                                        MessagesSearchQuery.messagesSearchCount[0] = 0;
                                    }
                                    i = 0;
                                    boolean z = false;
                                    while (i < Math.min(tLRPC$messages_Messages.messages.size(), 20)) {
                                        MessageObject messageObject = new MessageObject((Message) tLRPC$messages_Messages.messages.get(i), null, false);
                                        MessagesSearchQuery.searchResultMessages.add(messageObject);
                                        MessagesSearchQuery.searchResultMessagesMap[j6 == j8 ? 0 : 1].put(Integer.valueOf(messageObject.getId()), messageObject);
                                        i++;
                                        z = true;
                                    }
                                    MessagesSearchQuery.messagesSearchEndReached[j6 == j8 ? 0 : 1] = tLRPC$messages_Messages.messages.size() != 21;
                                    int[] access$300 = MessagesSearchQuery.messagesSearchCount;
                                    i = j6 == j8 ? 0 : 1;
                                    int size = ((tLRPC$messages_Messages instanceof TLRPC$TL_messages_messagesSlice) || (tLRPC$messages_Messages instanceof TLRPC$TL_messages_channelMessages)) ? tLRPC$messages_Messages.count : tLRPC$messages_Messages.messages.size();
                                    access$300[i] = size;
                                    if (MessagesSearchQuery.searchResultMessages.isEmpty()) {
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatSearchResultsAvailable, Integer.valueOf(i7), Integer.valueOf(0), Integer.valueOf(MessagesSearchQuery.getMask()), Long.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
                                    } else if (z) {
                                        if (MessagesSearchQuery.lastReturnedNum >= MessagesSearchQuery.searchResultMessages.size()) {
                                            MessagesSearchQuery.lastReturnedNum = MessagesSearchQuery.searchResultMessages.size() - 1;
                                        }
                                        MessageObject messageObject2 = (MessageObject) MessagesSearchQuery.searchResultMessages.get(MessagesSearchQuery.lastReturnedNum);
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatSearchResultsAvailable, Integer.valueOf(i7), Integer.valueOf(messageObject2.getId()), Integer.valueOf(MessagesSearchQuery.getMask()), Long.valueOf(messageObject2.getDialogId()), Integer.valueOf(MessagesSearchQuery.lastReturnedNum), Integer.valueOf(MessagesSearchQuery.messagesSearchCount[0] + MessagesSearchQuery.messagesSearchCount[1]));
                                    }
                                    if (j6 == j8 && MessagesSearchQuery.messagesSearchEndReached[0] && j9 != 0 && !MessagesSearchQuery.messagesSearchEndReached[1]) {
                                        MessagesSearchQuery.searchMessagesInChat(MessagesSearchQuery.lastSearchQuery, j8, j9, i7, 0, true, user3);
                                    }
                                }
                            }
                        }
                    });
                }
            }, 2);
        }
    }
}
