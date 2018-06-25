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
import org.telegram.tgnet.TLRPC$InputPeer;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterEmpty;
import org.telegram.tgnet.TLRPC$TL_messageActionHistoryClear;
import org.telegram.tgnet.TLRPC$TL_messageEmpty;
import org.telegram.tgnet.TLRPC$TL_messages_channelMessages;
import org.telegram.tgnet.TLRPC$TL_messages_messagesSlice;
import org.telegram.tgnet.TLRPC$TL_messages_search;
import org.telegram.tgnet.TLRPC$messages_Messages;
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

    private static int getMask() {
        int mask = 0;
        if (!(lastReturnedNum >= searchResultMessages.size() - 1 && messagesSearchEndReached[0] && messagesSearchEndReached[1])) {
            mask = 0 | 1;
        }
        if (lastReturnedNum > 0) {
            return mask | 2;
        }
        return mask;
    }

    public static boolean isMessageFound(int messageId, boolean mergeDialog) {
        return searchResultMessagesMap[mergeDialog ? 1 : 0].containsKey(Integer.valueOf(messageId));
    }

    public static void searchMessagesInChat(String query, long dialog_id, long mergeDialogId, int guid, int direction, User user) {
        searchMessagesInChat(query, dialog_id, mergeDialogId, guid, direction, false, user);
    }

    private static void searchMessagesInChat(String query, long dialog_id, long mergeDialogId, int guid, int direction, boolean internal, User user) {
        final TLRPC$TL_messages_search req;
        int max_id = 0;
        long queryWithDialog = dialog_id;
        boolean firstQuery = !internal;
        if (reqId != 0) {
            ConnectionsManager.getInstance().cancelRequest(reqId, true);
            reqId = 0;
        }
        if (mergeReqId != 0) {
            ConnectionsManager.getInstance().cancelRequest(mergeReqId, true);
            mergeReqId = 0;
        }
        if (query == null) {
            if (!searchResultMessages.isEmpty()) {
                MessageObject messageObject;
                if (direction == 1) {
                    lastReturnedNum++;
                    if (lastReturnedNum < searchResultMessages.size()) {
                        messageObject = (MessageObject) searchResultMessages.get(lastReturnedNum);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatSearchResultsAvailable, new Object[]{Integer.valueOf(guid), Integer.valueOf(messageObject.getId()), Integer.valueOf(getMask()), Long.valueOf(messageObject.getDialogId()), Integer.valueOf(lastReturnedNum), Integer.valueOf(messagesSearchCount[0] + messagesSearchCount[1])});
                        return;
                    } else if (messagesSearchEndReached[0] && mergeDialogId == 0 && messagesSearchEndReached[1]) {
                        lastReturnedNum--;
                        return;
                    } else {
                        firstQuery = false;
                        query = lastSearchQuery;
                        messageObject = (MessageObject) searchResultMessages.get(searchResultMessages.size() - 1);
                        if (messageObject.getDialogId() != dialog_id || messagesSearchEndReached[0]) {
                            if (messageObject.getDialogId() == mergeDialogId) {
                                max_id = messageObject.getId();
                            }
                            queryWithDialog = mergeDialogId;
                            messagesSearchEndReached[1] = false;
                        } else {
                            max_id = messageObject.getId();
                            queryWithDialog = dialog_id;
                        }
                    }
                } else if (direction == 2) {
                    lastReturnedNum--;
                    if (lastReturnedNum < 0) {
                        lastReturnedNum = 0;
                        return;
                    }
                    if (lastReturnedNum >= searchResultMessages.size()) {
                        lastReturnedNum = searchResultMessages.size() - 1;
                    }
                    messageObject = (MessageObject) searchResultMessages.get(lastReturnedNum);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatSearchResultsAvailable, new Object[]{Integer.valueOf(guid), Integer.valueOf(messageObject.getId()), Integer.valueOf(getMask()), Long.valueOf(messageObject.getDialogId()), Integer.valueOf(lastReturnedNum), Integer.valueOf(messagesSearchCount[0] + messagesSearchCount[1])});
                    return;
                } else {
                    return;
                }
            }
            return;
        } else if (firstQuery) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatSearchResultsLoading, new Object[]{Integer.valueOf(guid)});
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
        if (!(!messagesSearchEndReached[0] || messagesSearchEndReached[1] || mergeDialogId == 0)) {
            queryWithDialog = mergeDialogId;
        }
        if (queryWithDialog == dialog_id && firstQuery) {
            if (mergeDialogId != 0) {
                TLRPC$InputPeer inputPeer = MessagesController.getInputPeer((int) mergeDialogId);
                if (inputPeer != null) {
                    req = new TLRPC$TL_messages_search();
                    req.peer = inputPeer;
                    lastMergeDialogId = mergeDialogId;
                    req.limit = 1;
                    req.f87q = query != null ? query : "";
                    if (user != null) {
                        req.from_id = MessagesController.getInputUser(user);
                        req.flags |= 1;
                    }
                    req.filter = new TLRPC$TL_inputMessagesFilterEmpty();
                    final long j = mergeDialogId;
                    final long j2 = dialog_id;
                    final int i = guid;
                    final int i2 = direction;
                    final User user2 = user;
                    mergeReqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                        public void run(final TLObject response, TLRPC$TL_error error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (MessagesSearchQuery.lastMergeDialogId == j) {
                                        MessagesSearchQuery.mergeReqId = 0;
                                        if (response != null) {
                                            TLRPC$messages_Messages res = response;
                                            MessagesSearchQuery.messagesSearchEndReached[1] = res.messages.isEmpty();
                                            MessagesSearchQuery.messagesSearchCount[1] = res instanceof TLRPC$TL_messages_messagesSlice ? res.count : res.messages.size();
                                            MessagesSearchQuery.searchMessagesInChat(req.f87q, j2, j, i, i2, true, user2);
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
        req = new TLRPC$TL_messages_search();
        req.peer = MessagesController.getInputPeer((int) queryWithDialog);
        if (req.peer != null) {
            req.limit = 21;
            req.f87q = query != null ? query : "";
            req.offset_id = max_id;
            if (user != null) {
                req.from_id = MessagesController.getInputUser(user);
                req.flags |= 1;
            }
            req.filter = new TLRPC$TL_inputMessagesFilterEmpty();
            final int currentReqId = lastReqId + 1;
            lastReqId = currentReqId;
            lastSearchQuery = query;
            j2 = queryWithDialog;
            final long j3 = dialog_id;
            final int i3 = guid;
            final long j4 = mergeDialogId;
            final User user3 = user;
            reqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (currentReqId == MessagesSearchQuery.lastReqId) {
                                MessagesSearchQuery.reqId = 0;
                                if (response != null) {
                                    MessageObject messageObject;
                                    int i;
                                    TLRPC$messages_Messages res = response;
                                    int a = 0;
                                    while (a < res.messages.size()) {
                                        TLRPC$Message message = (TLRPC$Message) res.messages.get(a);
                                        if ((message instanceof TLRPC$TL_messageEmpty) || (message.action instanceof TLRPC$TL_messageActionHistoryClear)) {
                                            res.messages.remove(a);
                                            a--;
                                        }
                                        a++;
                                    }
                                    MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, true, true);
                                    MessagesController.getInstance().putUsers(res.users, false);
                                    MessagesController.getInstance().putChats(res.chats, false);
                                    if (req.offset_id == 0 && j2 == j3) {
                                        MessagesSearchQuery.lastReturnedNum = 0;
                                        MessagesSearchQuery.searchResultMessages.clear();
                                        MessagesSearchQuery.searchResultMessagesMap[0].clear();
                                        MessagesSearchQuery.searchResultMessagesMap[1].clear();
                                        MessagesSearchQuery.messagesSearchCount[0] = 0;
                                    }
                                    boolean added = false;
                                    for (a = 0; a < Math.min(res.messages.size(), 20); a++) {
                                        added = true;
                                        messageObject = new MessageObject((TLRPC$Message) res.messages.get(a), null, false);
                                        MessagesSearchQuery.searchResultMessages.add(messageObject);
                                        HashMap[] access$900 = MessagesSearchQuery.searchResultMessagesMap;
                                        if (j2 == j3) {
                                            i = 0;
                                        } else {
                                            i = 1;
                                        }
                                        access$900[i].put(Integer.valueOf(messageObject.getId()), messageObject);
                                    }
                                    MessagesSearchQuery.messagesSearchEndReached[j2 == j3 ? 0 : 1] = res.messages.size() != 21;
                                    int[] access$300 = MessagesSearchQuery.messagesSearchCount;
                                    i = j2 == j3 ? 0 : 1;
                                    int size = ((res instanceof TLRPC$TL_messages_messagesSlice) || (res instanceof TLRPC$TL_messages_channelMessages)) ? res.count : res.messages.size();
                                    access$300[i] = size;
                                    if (MessagesSearchQuery.searchResultMessages.isEmpty()) {
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatSearchResultsAvailable, new Object[]{Integer.valueOf(i3), Integer.valueOf(0), Integer.valueOf(MessagesSearchQuery.getMask()), Long.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)});
                                    } else if (added) {
                                        if (MessagesSearchQuery.lastReturnedNum >= MessagesSearchQuery.searchResultMessages.size()) {
                                            MessagesSearchQuery.lastReturnedNum = MessagesSearchQuery.searchResultMessages.size() - 1;
                                        }
                                        messageObject = (MessageObject) MessagesSearchQuery.searchResultMessages.get(MessagesSearchQuery.lastReturnedNum);
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatSearchResultsAvailable, new Object[]{Integer.valueOf(i3), Integer.valueOf(messageObject.getId()), Integer.valueOf(MessagesSearchQuery.getMask()), Long.valueOf(messageObject.getDialogId()), Integer.valueOf(MessagesSearchQuery.lastReturnedNum), Integer.valueOf(MessagesSearchQuery.messagesSearchCount[0] + MessagesSearchQuery.messagesSearchCount[1])});
                                    }
                                    if (j2 == j3 && MessagesSearchQuery.messagesSearchEndReached[0] && j4 != 0 && !MessagesSearchQuery.messagesSearchEndReached[1]) {
                                        MessagesSearchQuery.searchMessagesInChat(MessagesSearchQuery.lastSearchQuery, j3, j4, i3, 0, true, user3);
                                    }
                                }
                            }
                        }
                    });
                }
            }, 2);
        }
    }

    public static String getLastSearchQuery() {
        return lastSearchQuery;
    }
}
