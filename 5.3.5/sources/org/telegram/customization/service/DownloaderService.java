package org.telegram.customization.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.customization.util.DownloadManager;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLoader.FileLoaderDelegateCustomForDownloader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$PhotoSize;
import utils.view.Constants;

public class DownloaderService extends BaseService implements NotificationCenterDelegate {
    private static final int DOCUMENT_ATTACH_TYPE_AUDIO = 3;
    private static final int DOCUMENT_ATTACH_TYPE_DOCUMENT = 1;
    private static final int DOCUMENT_ATTACH_TYPE_GIF = 2;
    private static final int DOCUMENT_ATTACH_TYPE_MUSIC = 5;
    private static final int DOCUMENT_ATTACH_TYPE_NONE = 0;
    private static final int DOCUMENT_ATTACH_TYPE_STICKER = 6;
    private static final int DOCUMENT_ATTACH_TYPE_VIDEO = 4;
    protected static ArrayList<TLRPC$Document> documents = new ArrayList();
    protected static ArrayList<TLRPC$FileLocation> locations = new ArrayList();
    private static boolean stopNow = false;
    FileLoaderDelegateCustomForDownloader delegate = new C12141();
    int didReceivedCalled = 0;
    private ArrayList<DownloadManager> downloads;
    private boolean isClicked;
    long lastSendDownloading = 0;
    protected ArrayList<MessageObject> messages = new ArrayList();

    /* renamed from: org.telegram.customization.service.DownloaderService$1 */
    class C12141 implements FileLoaderDelegateCustomForDownloader {
        C12141() {
        }

        public void fileDidLoaded(TLRPC$Document document, TLRPC$FileLocation location) {
            FileLog.d("alireza dler: didload");
            DownloaderService.this.checkAvailabilityOfDoc(document, location, true);
        }

        public void fileDidFailedLoad(TLRPC$Document document, TLRPC$FileLocation location) {
            FileLog.d("alireza dler: didfiledload");
            DownloaderService.this.checkAvailabilityOfDoc(document, location, true);
        }

        public void fileLoadProgressChanged(TLRPC$Document document, TLRPC$FileLocation location) {
            DownloaderService.this.checkAvailabilityOfDoc(document, location, false);
        }
    }

    private void checkAvailabilityOfDoc(TLRPC$Document document, TLRPC$FileLocation location, boolean haveToRemove) {
        int i = 0;
        if (!haveToRemove && this.lastSendDownloading + 3000 < System.currentTimeMillis()) {
            this.lastSendDownloading = System.currentTimeMillis();
            postNotif(NotificationCenter.isDownloadingFromQueue);
        }
        int i2;
        if (document != null) {
            if (documents != null) {
                i2 = 0;
                while (i2 < documents.size()) {
                    if (((TLRPC$Document) documents.get(i2)).id != document.id) {
                        i2++;
                    } else if (haveToRemove) {
                        documents.remove(i2);
                        FileLog.d("alireza dler: doc removed. size = " + documents.size());
                    }
                }
            }
        } else if (!(location == null || locations == null)) {
            i2 = 0;
            while (i2 < locations.size()) {
                if (((TLRPC$FileLocation) locations.get(i2)).volume_id == location.volume_id && ((TLRPC$FileLocation) locations.get(i2)).local_id == location.local_id && ((TLRPC$FileLocation) locations.get(i2)).dc_id == location.dc_id && haveToRemove) {
                    locations.remove(i2);
                    FileLog.d("alireza dler: loc removed. size = " + locations.size());
                }
                i2++;
            }
        }
        StringBuilder append = new StringBuilder().append("alireza dler: doc size = ").append(documents == null ? 0 : documents.size()).append(" loc size = ");
        if (locations != null) {
            i = locations.size();
        }
        FileLog.d(append.append(i).toString());
        checkStop();
    }

    private void postNotif(final int id) {
        StringBuilder append = new StringBuilder().append("postNotif post id = ");
        String str = id == NotificationCenter.isDownloadingFromQueue ? "isDownloadingFromQueue" : id == NotificationCenter.finishedDownloadingFromQueue ? "finishedDownloadingFromQueue" : "UNKNOWN";
        FileLog.d(append.append(str).toString());
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                NotificationCenter.getInstance().postNotificationName(id, new Object[0]);
                if (id == NotificationCenter.finishedDownloadingFromQueue) {
                    NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDidLoaded);
                    DownloaderService.stopNow = true;
                    DownloaderService.this.stopSelf();
                }
            }
        });
    }

    private void checkStop() {
        if (documents == null) {
            documents = new ArrayList();
        }
        if (locations == null) {
            locations = new ArrayList();
        }
        if (stopNow) {
            locations.clear();
            documents.clear();
        }
        if (documents.isEmpty() && locations.isEmpty()) {
            postNotif(NotificationCenter.finishedDownloadingFromQueue);
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        postNotif(NotificationCenter.isDownloadingFromQueue);
        this.downloads = DownloadManager.getMessagesFromDownloadQueue();
        if (this.downloads == null || this.downloads.isEmpty() || intent == null) {
            postNotif(NotificationCenter.finishedDownloadingFromQueue);
        } else {
            stopNow = false;
            this.messages = new ArrayList();
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDidLoaded);
            this.isClicked = intent.getBooleanExtra(Constants.EXTRA_IS_CLICKED, false);
            FileLoader.getInstance().addCustomDelegate(this.delegate);
            if (documents == null) {
                documents = new ArrayList();
            } else {
                documents.clear();
            }
            if (locations == null) {
                locations = new ArrayList();
            } else {
                locations.clear();
            }
            Iterator it = this.downloads.iterator();
            while (it.hasNext()) {
                DownloadManager dl = (DownloadManager) it.next();
                MessagesController.getInstance().loadMessages(dl.getChatID(), 1, (int) (dl.getMsg_id() + 1), 0, true, 0, 0, 4, 0, true, 0);
            }
        }
        return result;
    }

    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (stopNow) {
            postNotif(NotificationCenter.finishedDownloadingFromQueue);
        } else if (id == NotificationCenter.messagesDidLoaded) {
            this.didReceivedCalled++;
            ArrayList<MessageObject> messArr = args[2];
            if (messArr != null) {
                Iterator it = messArr.iterator();
                while (it.hasNext()) {
                    MessageObject currentMessageObject = (MessageObject) it.next();
                    boolean exist = false;
                    Iterator it2 = this.downloads.iterator();
                    while (it2.hasNext()) {
                        DownloadManager dl = (DownloadManager) it2.next();
                        if (dl.getChatID() == currentMessageObject.messageOwner.dialog_id && dl.getMsg_id() == ((long) currentMessageObject.messageOwner.id)) {
                            exist = true;
                            break;
                        }
                    }
                    if (exist) {
                        File tmp = null;
                        try {
                            tmp = FileLoader.getPathToMessage(currentMessageObject.messageOwner);
                        } catch (Exception e) {
                        }
                        if (tmp == null || !tmp.exists()) {
                            TLRPC$Document documentAttach;
                            this.messages.add(currentMessageObject);
                            int documentAttachType = createDocumentLayout(currentMessageObject);
                            if (currentMessageObject.type == 0) {
                                documentAttach = currentMessageObject.messageOwner.media.webpage.document;
                            } else {
                                documentAttach = currentMessageObject.messageOwner.media.document;
                            }
                            FileLog.d("alireza DidRecDownloader : " + currentMessageObject.type + " - docType " + documentAttachType);
                            if (currentMessageObject.type == 1) {
                                if (!(currentMessageObject.messageOwner.media.photo == null || currentMessageObject.messageOwner.media.photo.sizes == null)) {
                                    it2 = currentMessageObject.messageOwner.media.photo.sizes.iterator();
                                    while (it2.hasNext()) {
                                        TLRPC$PhotoSize ps = (TLRPC$PhotoSize) it2.next();
                                        FileLoader.getInstance().loadFile(ps, "jpg", 0);
                                        locations.add(ps.location);
                                    }
                                }
                            } else if (currentMessageObject.type == 8 || currentMessageObject.type == 9 || documentAttachType == 4 || (currentMessageObject.type == 0 && documentAttachType == 1)) {
                                FileLoader.getInstance().loadFile(documentAttach, true, 0);
                                documents.add(documentAttach);
                            }
                        }
                    }
                }
            }
            if (this.didReceivedCalled >= this.downloads.size()) {
                checkStop();
            }
            if (this.messages.size() < this.downloads.size()) {
            }
        }
    }

    private int createDocumentLayout(MessageObject messageObject) {
        TLRPC$Document documentAttach;
        if (messageObject.type == 0) {
            documentAttach = messageObject.messageOwner.media.webpage.document;
        } else {
            documentAttach = messageObject.messageOwner.media.document;
        }
        if (documentAttach == null) {
            return 0;
        }
        if (MessageObject.isVoiceDocument(documentAttach)) {
            return 3;
        }
        if (MessageObject.isMusicDocument(documentAttach)) {
            return 5;
        }
        if (MessageObject.isVideoDocument(documentAttach)) {
            return 4;
        }
        return 1;
    }

    public static void cancelAll() {
        Iterator it;
        try {
            if (locations != null) {
                it = locations.iterator();
                while (it.hasNext()) {
                    try {
                        FileLoader.getInstance().cancelLoadFile((TLRPC$FileLocation) it.next(), "jpg");
                    } catch (Exception e) {
                    }
                }
                locations.clear();
            }
        } catch (Exception e2) {
        }
        try {
            if (documents != null) {
                it = documents.iterator();
                while (it.hasNext()) {
                    try {
                        FileLoader.getInstance().cancelLoadFile((TLRPC$Document) it.next());
                    } catch (Exception e3) {
                    }
                }
                documents.clear();
            }
        } catch (Exception e4) {
        }
        stopNow = true;
    }
}
