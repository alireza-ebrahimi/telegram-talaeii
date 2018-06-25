package org.telegram.messenger;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.location.Location;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.thin.downloadmanager.BuildConfig;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.ir.talaeii.R;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.query.DraftQuery;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.QuickAckDelegate;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$BotInlineResult;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ChatParticipant;
import org.telegram.tgnet.TLRPC$DecryptedMessage;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$InputDocument;
import org.telegram.tgnet.TLRPC$InputEncryptedFile;
import org.telegram.tgnet.TLRPC$InputFile;
import org.telegram.tgnet.TLRPC$InputMedia;
import org.telegram.tgnet.TLRPC$InputPeer;
import org.telegram.tgnet.TLRPC$InputUser;
import org.telegram.tgnet.TLRPC$KeyboardButton;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$MessageFwdHeader;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$ReplyMarkup;
import org.telegram.tgnet.TLRPC$TL_botInlineMessageMediaAuto;
import org.telegram.tgnet.TLRPC$TL_botInlineMessageMediaContact;
import org.telegram.tgnet.TLRPC$TL_botInlineMessageMediaGeo;
import org.telegram.tgnet.TLRPC$TL_botInlineMessageMediaVenue;
import org.telegram.tgnet.TLRPC$TL_botInlineMessageText;
import org.telegram.tgnet.TLRPC$TL_decryptedMessage;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionAbortKey;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionAcceptKey;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionCommitKey;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionDeleteMessages;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionFlushHistory;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionNoop;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionNotifyLayer;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionReadMessages;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionRequestKey;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionResend;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionScreenshotMessages;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionTyping;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaContact;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaEmpty;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaExternalDocument;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaGeoPoint;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaVenue;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaVideo;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_decryptedMessage_layer45;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_documentAttributeFilename;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker_layer55;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
import org.telegram.tgnet.TLRPC$TL_game;
import org.telegram.tgnet.TLRPC$TL_geoPoint;
import org.telegram.tgnet.TLRPC$TL_inputDocument;
import org.telegram.tgnet.TLRPC$TL_inputEncryptedFile;
import org.telegram.tgnet.TLRPC$TL_inputGeoPoint;
import org.telegram.tgnet.TLRPC$TL_inputMediaContact;
import org.telegram.tgnet.TLRPC$TL_inputMediaDocument;
import org.telegram.tgnet.TLRPC$TL_inputMediaEmpty;
import org.telegram.tgnet.TLRPC$TL_inputMediaGame;
import org.telegram.tgnet.TLRPC$TL_inputMediaGeoLive;
import org.telegram.tgnet.TLRPC$TL_inputMediaGeoPoint;
import org.telegram.tgnet.TLRPC$TL_inputMediaGifExternal;
import org.telegram.tgnet.TLRPC$TL_inputMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_inputMediaUploadedDocument;
import org.telegram.tgnet.TLRPC$TL_inputMediaUploadedPhoto;
import org.telegram.tgnet.TLRPC$TL_inputMediaVenue;
import org.telegram.tgnet.TLRPC$TL_inputPeerChannel;
import org.telegram.tgnet.TLRPC$TL_inputPeerEmpty;
import org.telegram.tgnet.TLRPC$TL_inputPeerUser;
import org.telegram.tgnet.TLRPC$TL_inputPhoto;
import org.telegram.tgnet.TLRPC$TL_inputSingleMedia;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetEmpty;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetShortName;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonBuy;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonGame;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageActionScreenshotTaken;
import org.telegram.tgnet.TLRPC$TL_messageEncryptedAction;
import org.telegram.tgnet.TLRPC$TL_messageEntityBold;
import org.telegram.tgnet.TLRPC$TL_messageEntityCode;
import org.telegram.tgnet.TLRPC$TL_messageEntityItalic;
import org.telegram.tgnet.TLRPC$TL_messageEntityPre;
import org.telegram.tgnet.TLRPC$TL_messageEntityTextUrl;
import org.telegram.tgnet.TLRPC$TL_messageEntityUrl;
import org.telegram.tgnet.TLRPC$TL_messageFwdHeader;
import org.telegram.tgnet.TLRPC$TL_messageMediaContact;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaEmpty;
import org.telegram.tgnet.TLRPC$TL_messageMediaGame;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeo;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive;
import org.telegram.tgnet.TLRPC$TL_messageMediaInvoice;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaVenue;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_message_secret;
import org.telegram.tgnet.TLRPC$TL_messages_editMessage;
import org.telegram.tgnet.TLRPC$TL_messages_forwardMessages;
import org.telegram.tgnet.TLRPC$TL_messages_getBotCallbackAnswer;
import org.telegram.tgnet.TLRPC$TL_messages_messages;
import org.telegram.tgnet.TLRPC$TL_messages_sendBroadcast;
import org.telegram.tgnet.TLRPC$TL_messages_sendEncryptedMultiMedia;
import org.telegram.tgnet.TLRPC$TL_messages_sendInlineBotResult;
import org.telegram.tgnet.TLRPC$TL_messages_sendMedia;
import org.telegram.tgnet.TLRPC$TL_messages_sendMessage;
import org.telegram.tgnet.TLRPC$TL_messages_sendMultiMedia;
import org.telegram.tgnet.TLRPC$TL_messages_sendScreenshotNotification;
import org.telegram.tgnet.TLRPC$TL_messages_uploadMedia;
import org.telegram.tgnet.TLRPC$TL_payments_getPaymentForm;
import org.telegram.tgnet.TLRPC$TL_payments_getPaymentReceipt;
import org.telegram.tgnet.TLRPC$TL_peerChannel;
import org.telegram.tgnet.TLRPC$TL_peerChat;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_photo;
import org.telegram.tgnet.TLRPC$TL_photoCachedSize;
import org.telegram.tgnet.TLRPC$TL_photoSize;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
import org.telegram.tgnet.TLRPC$TL_user;
import org.telegram.tgnet.TLRPC$TL_userContact_old2;
import org.telegram.tgnet.TLRPC$TL_userRequest_old2;
import org.telegram.tgnet.TLRPC$TL_webPagePending;
import org.telegram.tgnet.TLRPC$TL_webPageUrlPending;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ChatActivity;

public class SendMessagesHelper implements NotificationCenterDelegate {
    private static volatile SendMessagesHelper Instance = null;
    private static DispatchQueue mediaSendQueue = new DispatchQueue("mediaSendQueue");
    private static ThreadPoolExecutor mediaSendThreadPool;
    private TLRPC$ChatFull currentChatInfo = null;
    private HashMap<String, ArrayList<SendMessagesHelper$DelayedMessage>> delayedMessages = new HashMap();
    private SendMessagesHelper$LocationProvider locationProvider = new SendMessagesHelper$LocationProvider(new SendMessagesHelper$1(this));
    private HashMap<Integer, TLRPC$Message> sendingMessages = new HashMap();
    private HashMap<Integer, MessageObject> unsentMessages = new HashMap();
    private HashMap<String, MessageObject> waitingForCallback = new HashMap();
    private HashMap<String, MessageObject> waitingForLocation = new HashMap();

    static {
        int cores;
        if (VERSION.SDK_INT >= 17) {
            cores = Runtime.getRuntime().availableProcessors();
        } else {
            cores = 2;
        }
        mediaSendThreadPool = new ThreadPoolExecutor(cores, cores, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    }

    public static SendMessagesHelper getInstance() {
        SendMessagesHelper localInstance = Instance;
        if (localInstance == null) {
            synchronized (SendMessagesHelper.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        SendMessagesHelper localInstance2 = new SendMessagesHelper();
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

    public SendMessagesHelper() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidUpload);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidFailUpload);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FilePreparingStarted);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileNewChunkAvailable);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FilePreparingFailed);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.httpFileDidFailedLoad);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.httpFileDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidFailedLoad);
    }

    public void cleanup() {
        this.delayedMessages.clear();
        this.unsentMessages.clear();
        this.sendingMessages.clear();
        this.waitingForLocation.clear();
        this.waitingForCallback.clear();
        this.currentChatInfo = null;
        this.locationProvider.stop();
    }

    public void setCurrentChatInfo(TLRPC$ChatFull info) {
        this.currentChatInfo = info;
    }

    public void didReceivedNotification(int id, Object... args) {
        String location;
        ArrayList<SendMessagesHelper$DelayedMessage> arr;
        int a;
        SendMessagesHelper$DelayedMessage message;
        int index;
        MessageObject messageObject;
        if (id == NotificationCenter.FileDidUpload) {
            location = args[0];
            TLRPC$InputFile file = args[1];
            TLRPC$InputEncryptedFile encryptedFile = args[2];
            arr = (ArrayList) this.delayedMessages.get(location);
            if (arr != null) {
                a = 0;
                while (a < arr.size()) {
                    message = (SendMessagesHelper$DelayedMessage) arr.get(a);
                    TLRPC$InputMedia media = null;
                    if (message.sendRequest instanceof TLRPC$TL_messages_sendMedia) {
                        media = ((TLRPC$TL_messages_sendMedia) message.sendRequest).media;
                    } else if (message.sendRequest instanceof TLRPC$TL_messages_sendBroadcast) {
                        media = ((TLRPC$TL_messages_sendBroadcast) message.sendRequest).media;
                    } else if (message.sendRequest instanceof TLRPC$TL_messages_sendMultiMedia) {
                        media = (TLRPC$InputMedia) message.extraHashMap.get(location);
                    }
                    if (file != null && media != null) {
                        if (message.type == 0) {
                            media.file = file;
                            performSendMessageRequest(message.sendRequest, message.obj, message.originalPath, message, true);
                        } else if (message.type == 1) {
                            if (media.file == null) {
                                media.file = file;
                                if (media.thumb != null || message.location == null) {
                                    performSendMessageRequest(message.sendRequest, message.obj, message.originalPath);
                                } else {
                                    performSendDelayedMessage(message);
                                }
                            } else {
                                media.thumb = file;
                                media.flags |= 4;
                                performSendMessageRequest(message.sendRequest, message.obj, message.originalPath);
                            }
                        } else if (message.type == 2) {
                            if (media.file == null) {
                                media.file = file;
                                if (media.thumb != null || message.location == null) {
                                    performSendMessageRequest(message.sendRequest, message.obj, message.originalPath);
                                } else {
                                    performSendDelayedMessage(message);
                                }
                            } else {
                                media.thumb = file;
                                media.flags |= 4;
                                performSendMessageRequest(message.sendRequest, message.obj, message.originalPath);
                            }
                        } else if (message.type == 3) {
                            media.file = file;
                            performSendMessageRequest(message.sendRequest, message.obj, message.originalPath);
                        } else if (message.type == 4) {
                            if (!(media instanceof TLRPC$TL_inputMediaUploadedDocument)) {
                                media.file = file;
                                uploadMultiMedia(message, media, null, location);
                            } else if (media.file == null) {
                                media.file = file;
                                index = message.messageObjects.indexOf((MessageObject) message.extraHashMap.get(location + "_i"));
                                message.location = (TLRPC$FileLocation) message.extraHashMap.get(location + "_t");
                                stopVideoService(((MessageObject) message.messageObjects.get(index)).messageOwner.attachPath);
                                if (media.thumb != null || message.location == null) {
                                    uploadMultiMedia(message, media, null, location);
                                } else {
                                    performSendDelayedMessage(message, index);
                                }
                            } else {
                                media.thumb = file;
                                media.flags |= 4;
                                uploadMultiMedia(message, media, null, (String) message.extraHashMap.get(location + "_o"));
                            }
                        }
                        arr.remove(a);
                        a--;
                    } else if (!(encryptedFile == null || message.sendEncryptedRequest == null)) {
                        TLRPC$TL_decryptedMessage decryptedMessage;
                        if (message.type == 4) {
                            TLRPC$TL_messages_sendEncryptedMultiMedia req = (TLRPC$TL_messages_sendEncryptedMultiMedia) message.sendEncryptedRequest;
                            TLRPC$InputEncryptedFile inputEncryptedFile = (TLRPC$InputEncryptedFile) message.extraHashMap.get(location);
                            index = req.files.indexOf(inputEncryptedFile);
                            req.files.set(index, encryptedFile);
                            if (inputEncryptedFile.id == 1) {
                                messageObject = (MessageObject) message.extraHashMap.get(location + "_i");
                                message.location = (TLRPC$FileLocation) message.extraHashMap.get(location + "_t");
                                stopVideoService(((MessageObject) message.messageObjects.get(index)).messageOwner.attachPath);
                            }
                            decryptedMessage = (TLRPC$TL_decryptedMessage) req.messages.get(index);
                        } else {
                            decryptedMessage = (TLRPC$TL_decryptedMessage) message.sendEncryptedRequest;
                        }
                        if ((decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaVideo) || (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaPhoto) || (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaDocument)) {
                            decryptedMessage.media.size = (int) ((Long) args[5]).longValue();
                        }
                        decryptedMessage.media.key = (byte[]) args[3];
                        decryptedMessage.media.iv = (byte[]) args[4];
                        if (message.type == 4) {
                            uploadMultiMedia(message, null, encryptedFile, location);
                        } else {
                            SecretChatHelper.getInstance().performSendEncryptedRequest(decryptedMessage, message.obj.messageOwner, message.encryptedChat, encryptedFile, message.originalPath, message.obj);
                        }
                        arr.remove(a);
                        a--;
                    }
                    a++;
                }
                if (arr.isEmpty()) {
                    this.delayedMessages.remove(location);
                }
            }
        } else if (id == NotificationCenter.FileDidFailUpload) {
            location = (String) args[0];
            boolean enc = ((Boolean) args[1]).booleanValue();
            arr = (ArrayList) this.delayedMessages.get(location);
            if (arr != null) {
                a = 0;
                while (a < arr.size()) {
                    SendMessagesHelper$DelayedMessage obj = (SendMessagesHelper$DelayedMessage) arr.get(a);
                    if ((enc && obj.sendEncryptedRequest != null) || !(enc || obj.sendRequest == null)) {
                        obj.markAsError();
                        arr.remove(a);
                        a--;
                    }
                    a++;
                }
                if (arr.isEmpty()) {
                    this.delayedMessages.remove(location);
                }
            }
        } else if (id == NotificationCenter.FilePreparingStarted) {
            messageObject = (MessageObject) args[0];
            if (messageObject.getId() != 0) {
                finalPath = args[1];
                arr = (ArrayList) this.delayedMessages.get(messageObject.messageOwner.attachPath);
                if (arr != null) {
                    a = 0;
                    while (a < arr.size()) {
                        message = (SendMessagesHelper$DelayedMessage) arr.get(a);
                        if (message.type == 4) {
                            index = message.messageObjects.indexOf(messageObject);
                            message.location = (TLRPC$FileLocation) message.extraHashMap.get(messageObject.messageOwner.attachPath + "_t");
                            performSendDelayedMessage(message, index);
                            arr.remove(a);
                            break;
                        } else if (message.obj == messageObject) {
                            message.videoEditedInfo = null;
                            performSendDelayedMessage(message);
                            arr.remove(a);
                            break;
                        } else {
                            a++;
                        }
                    }
                    if (arr.isEmpty()) {
                        this.delayedMessages.remove(messageObject.messageOwner.attachPath);
                    }
                }
            }
        } else if (id == NotificationCenter.FileNewChunkAvailable) {
            messageObject = (MessageObject) args[0];
            if (messageObject.getId() != 0) {
                finalPath = (String) args[1];
                long finalSize = ((Long) args[2]).longValue();
                FileLoader.getInstance().checkUploadNewDataAvailable(finalPath, ((int) messageObject.getDialogId()) == 0, finalSize);
                if (finalSize != 0) {
                    arr = (ArrayList) this.delayedMessages.get(messageObject.messageOwner.attachPath);
                    if (arr != null) {
                        a = 0;
                        while (a < arr.size()) {
                            message = (SendMessagesHelper$DelayedMessage) arr.get(a);
                            ArrayList<TLRPC$Message> messages;
                            if (message.type == 4) {
                                while (0 < message.messageObjects.size()) {
                                    MessageObject obj2 = (MessageObject) message.messageObjects.get(a);
                                    if (obj2 == messageObject) {
                                        obj2.videoEditedInfo = null;
                                        obj2.messageOwner.message = "-1";
                                        obj2.messageOwner.media.document.size = (int) finalSize;
                                        messages = new ArrayList();
                                        messages.add(obj2.messageOwner);
                                        MessagesStorage.getInstance().putMessages(messages, false, true, false, 0);
                                        break;
                                    }
                                    a++;
                                }
                            } else if (message.obj == messageObject) {
                                message.obj.videoEditedInfo = null;
                                message.obj.messageOwner.message = "-1";
                                message.obj.messageOwner.media.document.size = (int) finalSize;
                                messages = new ArrayList();
                                messages.add(message.obj.messageOwner);
                                MessagesStorage.getInstance().putMessages(messages, false, true, false, 0);
                                return;
                            }
                            a++;
                        }
                    }
                }
            }
        } else if (id == NotificationCenter.FilePreparingFailed) {
            messageObject = (MessageObject) args[0];
            if (messageObject.getId() != 0) {
                finalPath = (String) args[1];
                stopVideoService(messageObject.messageOwner.attachPath);
                arr = (ArrayList) this.delayedMessages.get(finalPath);
                if (arr != null) {
                    a = 0;
                    while (a < arr.size()) {
                        message = (SendMessagesHelper$DelayedMessage) arr.get(a);
                        if (message.type == 4) {
                            for (int b = 0; b < message.messages.size(); b++) {
                                if (message.messageObjects.get(b) == messageObject) {
                                    message.markAsError();
                                    arr.remove(a);
                                    a--;
                                    break;
                                }
                            }
                        } else if (message.obj == messageObject) {
                            message.markAsError();
                            arr.remove(a);
                            a--;
                        }
                        a++;
                    }
                    if (arr.isEmpty()) {
                        this.delayedMessages.remove(finalPath);
                    }
                }
            }
        } else if (id == NotificationCenter.httpFileDidLoaded) {
            path = args[0];
            arr = (ArrayList) this.delayedMessages.get(path);
            if (arr != null) {
                for (a = 0; a < arr.size(); a++) {
                    message = (SendMessagesHelper$DelayedMessage) arr.get(a);
                    int fileType = -1;
                    if (message.type == 0) {
                        fileType = 0;
                        messageObject = message.obj;
                    } else if (message.type == 2) {
                        fileType = 1;
                        messageObject = message.obj;
                    } else if (message.type == 4) {
                        messageObject = (MessageObject) message.extraHashMap.get(path);
                        if (messageObject.getDocument() != null) {
                            fileType = 1;
                        } else {
                            fileType = 0;
                        }
                    } else {
                        messageObject = null;
                    }
                    if (fileType == 0) {
                        Utilities.globalQueue.postRunnable(new SendMessagesHelper$2(this, new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(path) + "." + ImageLoader.getHttpUrlExtension(path, "file")), messageObject, message, path));
                    } else if (fileType == 1) {
                        Utilities.globalQueue.postRunnable(new SendMessagesHelper$3(this, message, new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(path) + ".gif"), messageObject));
                    }
                }
                this.delayedMessages.remove(path);
            }
        } else if (id == NotificationCenter.FileDidLoaded) {
            path = (String) args[0];
            arr = (ArrayList) this.delayedMessages.get(path);
            if (arr != null) {
                for (a = 0; a < arr.size(); a++) {
                    performSendDelayedMessage((SendMessagesHelper$DelayedMessage) arr.get(a));
                }
                this.delayedMessages.remove(path);
            }
        } else if (id == NotificationCenter.httpFileDidFailedLoad || id == NotificationCenter.FileDidFailedLoad) {
            path = (String) args[0];
            arr = (ArrayList) this.delayedMessages.get(path);
            if (arr != null) {
                for (a = 0; a < arr.size(); a++) {
                    ((SendMessagesHelper$DelayedMessage) arr.get(a)).markAsError();
                }
                this.delayedMessages.remove(path);
            }
        }
    }

    public void cancelSendingMessage(MessageObject object) {
        ArrayList<String> keysToRemove = new ArrayList();
        boolean enc = false;
        for (Entry<String, ArrayList<SendMessagesHelper$DelayedMessage>> entry : this.delayedMessages.entrySet()) {
            ArrayList<SendMessagesHelper$DelayedMessage> messages = (ArrayList) entry.getValue();
            int a = 0;
            while (a < messages.size()) {
                SendMessagesHelper$DelayedMessage message = (SendMessagesHelper$DelayedMessage) messages.get(a);
                if (message.type == 4) {
                    int index = -1;
                    MessageObject messageObject = null;
                    for (int b = 0; b < message.messageObjects.size(); b++) {
                        messageObject = (MessageObject) message.messageObjects.get(b);
                        if (messageObject.getId() == object.getId()) {
                            index = b;
                            break;
                        }
                    }
                    if (index >= 0) {
                        message.messageObjects.remove(index);
                        message.messages.remove(index);
                        message.originalPaths.remove(index);
                        if (message.sendRequest != null) {
                            ((TLRPC$TL_messages_sendMultiMedia) message.sendRequest).multi_media.remove(index);
                        } else {
                            TLRPC$TL_messages_sendEncryptedMultiMedia request = (TLRPC$TL_messages_sendEncryptedMultiMedia) message.sendEncryptedRequest;
                            request.messages.remove(index);
                            request.files.remove(index);
                        }
                        MediaController.getInstance().cancelVideoConvert(object);
                        String keyToRemove = (String) message.extraHashMap.get(messageObject);
                        if (keyToRemove != null) {
                            keysToRemove.add(keyToRemove);
                        }
                        if (message.messageObjects.isEmpty()) {
                            message.sendDelayedRequests();
                        } else {
                            if (message.finalGroupMessage == object.getId()) {
                                MessageObject prevMessage = (MessageObject) message.messageObjects.get(message.messageObjects.size() - 1);
                                message.finalGroupMessage = prevMessage.getId();
                                prevMessage.messageOwner.params.put("final", BuildConfig.VERSION_NAME);
                                TLRPC$TL_messages_messages messagesRes = new TLRPC$TL_messages_messages();
                                messagesRes.messages.add(prevMessage.messageOwner);
                                MessagesStorage.getInstance().putMessages(messagesRes, message.peer, -2, 0, false);
                            }
                            sendReadyToSendGroup(message, false, true);
                        }
                    }
                } else if (message.obj.getId() == object.getId()) {
                    messages.remove(a);
                    message.sendDelayedRequests();
                    MediaController.getInstance().cancelVideoConvert(message.obj);
                    if (messages.size() == 0) {
                        keysToRemove.add(entry.getKey());
                        if (message.sendEncryptedRequest != null) {
                            enc = true;
                        }
                    }
                } else {
                    a++;
                }
            }
        }
        for (a = 0; a < keysToRemove.size(); a++) {
            String key = (String) keysToRemove.get(a);
            if (key.startsWith("http")) {
                ImageLoader.getInstance().cancelLoadHttpFile(key);
            } else {
                FileLoader.getInstance().cancelUploadFile(key, enc);
            }
            stopVideoService(key);
            this.delayedMessages.remove(key);
        }
        ArrayList<Integer> messages2 = new ArrayList();
        messages2.add(Integer.valueOf(object.getId()));
        MessagesController.getInstance().deleteMessages(messages2, null, null, object.messageOwner.to_id.channel_id, false);
    }

    public boolean retrySendMessage(MessageObject messageObject, boolean unsent) {
        if (messageObject.getId() >= 0) {
            return false;
        }
        if (messageObject.messageOwner.action instanceof TLRPC$TL_messageEncryptedAction) {
            TLRPC$EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (messageObject.getDialogId() >> 32)));
            if (encryptedChat == null) {
                MessagesStorage.getInstance().markMessageAsSendError(messageObject.messageOwner);
                messageObject.messageOwner.send_state = 2;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(messageObject.getId()));
                processSentMessage(messageObject.getId());
                return false;
            }
            if (messageObject.messageOwner.random_id == 0) {
                messageObject.messageOwner.random_id = getNextRandomId();
            }
            if (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL) {
                SecretChatHelper.getInstance().sendTTLMessage(encryptedChat, messageObject.messageOwner);
            } else if (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionDeleteMessages) {
                SecretChatHelper.getInstance().sendMessagesDeleteMessage(encryptedChat, null, messageObject.messageOwner);
            } else if (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionFlushHistory) {
                SecretChatHelper.getInstance().sendClearHistoryMessage(encryptedChat, messageObject.messageOwner);
            } else if (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionNotifyLayer) {
                SecretChatHelper.getInstance().sendNotifyLayerMessage(encryptedChat, messageObject.messageOwner);
            } else if (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionReadMessages) {
                SecretChatHelper.getInstance().sendMessagesReadMessage(encryptedChat, null, messageObject.messageOwner);
            } else if (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages) {
                SecretChatHelper.getInstance().sendScreenshotMessage(encryptedChat, null, messageObject.messageOwner);
            } else if (!((messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionTyping) || (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionResend))) {
                if (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionCommitKey) {
                    SecretChatHelper.getInstance().sendCommitKeyMessage(encryptedChat, messageObject.messageOwner);
                } else if (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionAbortKey) {
                    SecretChatHelper.getInstance().sendAbortKeyMessage(encryptedChat, messageObject.messageOwner, 0);
                } else if (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionRequestKey) {
                    SecretChatHelper.getInstance().sendRequestKeyMessage(encryptedChat, messageObject.messageOwner);
                } else if (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionAcceptKey) {
                    SecretChatHelper.getInstance().sendAcceptKeyMessage(encryptedChat, messageObject.messageOwner);
                } else if (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionNoop) {
                    SecretChatHelper.getInstance().sendNoopMessage(encryptedChat, messageObject.messageOwner);
                }
            }
            return true;
        }
        if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionScreenshotTaken) {
            sendScreenshotMessage(MessagesController.getInstance().getUser(Integer.valueOf((int) messageObject.getDialogId())), messageObject.messageOwner.reply_to_msg_id, messageObject.messageOwner);
        }
        if (unsent) {
            this.unsentMessages.put(Integer.valueOf(messageObject.getId()), messageObject);
        }
        sendMessage(messageObject);
        return true;
    }

    protected void processSentMessage(int id) {
        int prevSize = this.unsentMessages.size();
        this.unsentMessages.remove(Integer.valueOf(id));
        if (prevSize != 0 && this.unsentMessages.size() == 0) {
            checkUnsentMessages();
        }
    }

    public void processForwardFromMyName(MessageObject messageObject, long did) {
        if (messageObject != null) {
            ArrayList<MessageObject> arrayList;
            if (messageObject.messageOwner.media == null || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaEmpty) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice)) {
                if (messageObject.messageOwner.message != null) {
                    ArrayList entities;
                    TLRPC$WebPage webPage = null;
                    if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) {
                        webPage = messageObject.messageOwner.media.webpage;
                    }
                    if (messageObject.messageOwner.entities == null || messageObject.messageOwner.entities.isEmpty()) {
                        entities = null;
                    } else {
                        entities = new ArrayList();
                        for (int a = 0; a < messageObject.messageOwner.entities.size(); a++) {
                            TLRPC$MessageEntity entity = (TLRPC$MessageEntity) messageObject.messageOwner.entities.get(a);
                            if ((entity instanceof TLRPC$TL_messageEntityBold) || (entity instanceof TLRPC$TL_messageEntityItalic) || (entity instanceof TLRPC$TL_messageEntityPre) || (entity instanceof TLRPC$TL_messageEntityCode) || (entity instanceof TLRPC$TL_messageEntityTextUrl)) {
                                entities.add(entity);
                            }
                        }
                    }
                    sendMessage(messageObject.messageOwner.message, did, messageObject.replyMessageObject, webPage, true, entities, null, null);
                } else if (((int) did) != 0) {
                    arrayList = new ArrayList();
                    arrayList.add(messageObject);
                    sendMessage(arrayList, did);
                }
            } else if (messageObject.messageOwner.media.photo instanceof TLRPC$TL_photo) {
                sendMessage((TLRPC$TL_photo) messageObject.messageOwner.media.photo, null, did, messageObject.replyMessageObject, null, null, messageObject.messageOwner.media.ttl_seconds);
            } else if (messageObject.messageOwner.media.document instanceof TLRPC$TL_document) {
                sendMessage((TLRPC$TL_document) messageObject.messageOwner.media.document, null, messageObject.messageOwner.attachPath, did, messageObject.replyMessageObject, null, null, messageObject.messageOwner.media.ttl_seconds);
            } else if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaVenue) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo)) {
                sendMessage(messageObject.messageOwner.media, did, messageObject.replyMessageObject, null, null);
            } else if (messageObject.messageOwner.media.phone_number != null) {
                User user = new TLRPC$TL_userContact_old2();
                user.phone = messageObject.messageOwner.media.phone_number;
                user.first_name = messageObject.messageOwner.media.first_name;
                user.last_name = messageObject.messageOwner.media.last_name;
                user.id = messageObject.messageOwner.media.user_id;
                sendMessage(user, did, messageObject.replyMessageObject, null, null);
            } else if (((int) did) != 0) {
                arrayList = new ArrayList();
                arrayList.add(messageObject);
                sendMessage(arrayList, did);
            }
        }
    }

    public void processForwardFromMyName(MessageObject messageObject, long did, boolean asAdmin) {
        if (messageObject != null) {
            ArrayList<MessageObject> arrayList;
            if (messageObject.messageOwner.media == null || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaEmpty) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage)) {
                if (messageObject.messageOwner.message != null) {
                    TLRPC$WebPage webPage = null;
                    if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) {
                        webPage = messageObject.messageOwner.media.webpage;
                    }
                    sendMessage(messageObject.messageOwner.message, did, messageObject.replyMessageObject, webPage, true, messageObject.messageOwner.entities, null, null);
                    return;
                }
                arrayList = new ArrayList();
                arrayList.add(messageObject);
                sendMessage(arrayList, did);
            } else if (messageObject.messageOwner.media.photo instanceof TLRPC$TL_photo) {
                if (!TextUtils.isEmpty(messageObject.caption)) {
                    messageObject.messageOwner.media.photo.caption = messageObject.caption.toString();
                }
                sendMessage((TLRPC$TL_photo) messageObject.messageOwner.media.photo, null, did, messageObject.replyMessageObject, null, null, 0);
            } else if (messageObject.messageOwner.media.document instanceof TLRPC$TL_document) {
                if (!TextUtils.isEmpty(messageObject.caption)) {
                    messageObject.messageOwner.media.document.caption = messageObject.caption.toString();
                }
                sendMessage((TLRPC$TL_document) messageObject.messageOwner.media.document, null, messageObject.messageOwner.attachPath, did, messageObject.replyMessageObject, null, null, 0);
            } else if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaVenue) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo)) {
                sendMessage(messageObject.messageOwner.media, did, messageObject.replyMessageObject, null, null);
            } else if (messageObject.messageOwner.media.phone_number != null) {
                User user = new TLRPC$TL_userContact_old2();
                user.phone = messageObject.messageOwner.media.phone_number;
                user.first_name = messageObject.messageOwner.media.first_name;
                user.last_name = messageObject.messageOwner.media.last_name;
                user.id = messageObject.messageOwner.media.user_id;
                sendMessage(user, did, messageObject.replyMessageObject, null, null);
            } else {
                arrayList = new ArrayList();
                arrayList.add(messageObject);
                sendMessage(arrayList, did);
            }
        }
    }

    public void sendScreenshotMessage(User user, int messageId, TLRPC$Message resendMessage) {
        if (user != null && messageId != 0 && user.id != UserConfig.getClientUserId()) {
            TLRPC$Message message;
            TLRPC$TL_messages_sendScreenshotNotification req = new TLRPC$TL_messages_sendScreenshotNotification();
            req.peer = new TLRPC$TL_inputPeerUser();
            req.peer.access_hash = user.access_hash;
            req.peer.user_id = user.id;
            if (resendMessage != null) {
                message = resendMessage;
                req.reply_to_msg_id = messageId;
                req.random_id = resendMessage.random_id;
            } else {
                message = new TLRPC$TL_messageService();
                message.random_id = getNextRandomId();
                message.dialog_id = (long) user.id;
                message.unread = true;
                message.out = true;
                int newMessageId = UserConfig.getNewMessageId();
                message.id = newMessageId;
                message.local_id = newMessageId;
                message.from_id = UserConfig.getClientUserId();
                message.flags |= 256;
                message.flags |= 8;
                message.reply_to_msg_id = messageId;
                message.to_id = new TLRPC$TL_peerUser();
                message.to_id.user_id = user.id;
                message.date = ConnectionsManager.getInstance().getCurrentTime();
                message.action = new TLRPC$TL_messageActionScreenshotTaken();
                UserConfig.saveConfig(false);
            }
            req.random_id = message.random_id;
            MessageObject newMsgObj = new MessageObject(message, null, false);
            newMsgObj.messageOwner.send_state = 1;
            ArrayList<MessageObject> objArr = new ArrayList();
            objArr.add(newMsgObj);
            MessagesController.getInstance().updateInterfaceWithMessages(message.dialog_id, objArr);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            ArrayList<TLRPC$Message> arr = new ArrayList();
            arr.add(message);
            MessagesStorage.getInstance().putMessages(arr, false, true, false, 0);
            performSendMessageRequest(req, newMsgObj, null);
        }
    }

    public void sendSticker(TLRPC$Document document, long peer, MessageObject replyingMessageObject, Context context) {
        if (document != null) {
            if (((int) peer) == 0) {
                if (MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (peer >> 32))) != null) {
                    TLRPC$Document newDocument = new TLRPC$TL_document();
                    newDocument.id = document.id;
                    newDocument.access_hash = document.access_hash;
                    newDocument.date = document.date;
                    newDocument.mime_type = document.mime_type;
                    newDocument.size = document.size;
                    newDocument.dc_id = document.dc_id;
                    newDocument.attributes = new ArrayList(document.attributes);
                    if (newDocument.mime_type == null) {
                        newDocument.mime_type = "";
                    }
                    if (document.thumb instanceof TLRPC$TL_photoSize) {
                        File file = FileLoader.getPathToAttach(document.thumb, true);
                        if (file.exists()) {
                            try {
                                int len = (int) file.length();
                                byte[] arr = new byte[((int) file.length())];
                                new RandomAccessFile(file, "r").readFully(arr);
                                newDocument.thumb = new TLRPC$TL_photoCachedSize();
                                newDocument.thumb.location = document.thumb.location;
                                newDocument.thumb.size = document.thumb.size;
                                newDocument.thumb.f78w = document.thumb.f78w;
                                newDocument.thumb.f77h = document.thumb.f77h;
                                newDocument.thumb.type = document.thumb.type;
                                newDocument.thumb.bytes = arr;
                            } catch (Throwable e) {
                                FileLog.m94e(e);
                            }
                        }
                    }
                    if (newDocument.thumb == null) {
                        newDocument.thumb = new TLRPC$TL_photoSizeEmpty();
                        newDocument.thumb.type = "s";
                    }
                    document = newDocument;
                } else {
                    return;
                }
            }
            TLRPC$Document finalDocument = document;
            TLRPC$Document finalDocument1 = document;
            if (ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("confirmForStickers", true)) {
                try {
                    Builder builder = new Builder(context);
                    builder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                    builder.setMessage("برای ارسال مطمینید؟").setCancelable(true).setPositiveButton("بله", new SendMessagesHelper$5(this, finalDocument1, peer, replyingMessageObject)).setNegativeButton("خیر", new SendMessagesHelper$4(this));
                    builder.create().show();
                    return;
                } catch (Exception e2) {
                    getInstance().sendMessage((TLRPC$TL_document) finalDocument1, null, null, peer, replyingMessageObject, null, null, 0);
                    return;
                }
            }
            getInstance().sendMessage((TLRPC$TL_document) finalDocument1, null, null, peer, replyingMessageObject, null, null, 0);
        }
    }

    public int sendMessage(ArrayList<MessageObject> messages, long peer) {
        if (messages == null || messages.isEmpty()) {
            return 0;
        }
        int lower_id = (int) peer;
        int sendResult = 0;
        int a;
        if (lower_id != 0) {
            TLRPC$Chat chat;
            TLRPC$Peer to_id = MessagesController.getPeer((int) peer);
            boolean isMegagroup = false;
            boolean isSignature = false;
            boolean canSendStickers = true;
            boolean canSendMedia = true;
            boolean canSendPreview = true;
            if (lower_id <= 0) {
                chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
                if (ChatObject.isChannel(chat)) {
                    isMegagroup = chat.megagroup;
                    isSignature = chat.signatures;
                    if (chat.banned_rights != null) {
                        canSendStickers = !chat.banned_rights.send_stickers;
                        canSendMedia = !chat.banned_rights.send_media;
                        canSendPreview = !chat.banned_rights.embed_links;
                    }
                }
            } else if (MessagesController.getInstance().getUser(Integer.valueOf(lower_id)) == null) {
                return 0;
            }
            HashMap<Long, Long> groupsMap = new HashMap();
            ArrayList<MessageObject> objArr = new ArrayList();
            ArrayList<TLRPC$Message> arr = new ArrayList();
            ArrayList<Long> randomIds = new ArrayList();
            ArrayList<Integer> ids = new ArrayList();
            HashMap<Long, TLRPC$Message> messagesByRandomIds = new HashMap();
            TLRPC$InputPeer inputPeer = MessagesController.getInputPeer(lower_id);
            int myId = UserConfig.getClientUserId();
            boolean toMyself = peer == ((long) myId);
            a = 0;
            while (a < messages.size()) {
                MessageObject msgObj = (MessageObject) messages.get(a);
                if (msgObj.getId() > 0 && !msgObj.isSecretPhoto()) {
                    if (canSendStickers || !(msgObj.isSticker() || msgObj.isGif() || msgObj.isGame())) {
                        if (canSendMedia || !((msgObj.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) || (msgObj.messageOwner.media instanceof TLRPC$TL_messageMediaDocument))) {
                            TLRPC$MessageFwdHeader tLRPC$MessageFwdHeader;
                            boolean groupedIdChanged = false;
                            TLRPC$Message newMsg = new TLRPC$TL_message();
                            if (msgObj.isForwarded()) {
                                newMsg.fwd_from = new TLRPC$TL_messageFwdHeader();
                                newMsg.fwd_from.flags = msgObj.messageOwner.fwd_from.flags;
                                newMsg.fwd_from.from_id = msgObj.messageOwner.fwd_from.from_id;
                                newMsg.fwd_from.date = msgObj.messageOwner.fwd_from.date;
                                newMsg.fwd_from.channel_id = msgObj.messageOwner.fwd_from.channel_id;
                                newMsg.fwd_from.channel_post = msgObj.messageOwner.fwd_from.channel_post;
                                newMsg.fwd_from.post_author = msgObj.messageOwner.fwd_from.post_author;
                                newMsg.flags = 4;
                            } else {
                                newMsg.fwd_from = new TLRPC$TL_messageFwdHeader();
                                newMsg.fwd_from.channel_post = msgObj.getId();
                                tLRPC$MessageFwdHeader = newMsg.fwd_from;
                                tLRPC$MessageFwdHeader.flags |= 4;
                                if (msgObj.isFromUser()) {
                                    newMsg.fwd_from.from_id = msgObj.messageOwner.from_id;
                                    tLRPC$MessageFwdHeader = newMsg.fwd_from;
                                    tLRPC$MessageFwdHeader.flags |= 1;
                                } else {
                                    newMsg.fwd_from.channel_id = msgObj.messageOwner.to_id.channel_id;
                                    tLRPC$MessageFwdHeader = newMsg.fwd_from;
                                    tLRPC$MessageFwdHeader.flags |= 2;
                                    if (msgObj.messageOwner.post && msgObj.messageOwner.from_id > 0) {
                                        newMsg.fwd_from.from_id = msgObj.messageOwner.from_id;
                                        tLRPC$MessageFwdHeader = newMsg.fwd_from;
                                        tLRPC$MessageFwdHeader.flags |= 1;
                                    }
                                }
                                if (msgObj.messageOwner.post_author != null) {
                                    newMsg.fwd_from.post_author = msgObj.messageOwner.post_author;
                                    tLRPC$MessageFwdHeader = newMsg.fwd_from;
                                    tLRPC$MessageFwdHeader.flags |= 8;
                                } else if (!msgObj.isOutOwner() && msgObj.messageOwner.from_id > 0 && msgObj.messageOwner.post) {
                                    User signUser = MessagesController.getInstance().getUser(Integer.valueOf(msgObj.messageOwner.from_id));
                                    if (signUser != null) {
                                        newMsg.fwd_from.post_author = ContactsController.formatName(signUser.first_name, signUser.last_name);
                                        tLRPC$MessageFwdHeader = newMsg.fwd_from;
                                        tLRPC$MessageFwdHeader.flags |= 8;
                                    }
                                }
                                newMsg.date = msgObj.messageOwner.date;
                                newMsg.flags = 4;
                            }
                            if (peer == ((long) myId) && newMsg.fwd_from != null) {
                                tLRPC$MessageFwdHeader = newMsg.fwd_from;
                                tLRPC$MessageFwdHeader.flags |= 16;
                                newMsg.fwd_from.saved_from_msg_id = msgObj.getId();
                                newMsg.fwd_from.saved_from_peer = msgObj.messageOwner.to_id;
                            }
                            if (canSendPreview || !(msgObj.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage)) {
                                newMsg.media = msgObj.messageOwner.media;
                            } else {
                                newMsg.media = new TLRPC$TL_messageMediaEmpty();
                            }
                            if (newMsg.media != null) {
                                newMsg.flags |= 512;
                            }
                            if (isMegagroup) {
                                newMsg.flags |= Integer.MIN_VALUE;
                            }
                            if (msgObj.messageOwner.via_bot_id != 0) {
                                newMsg.via_bot_id = msgObj.messageOwner.via_bot_id;
                                newMsg.flags |= 2048;
                            }
                            newMsg.message = msgObj.messageOwner.message;
                            newMsg.fwd_msg_id = msgObj.getId();
                            newMsg.attachPath = msgObj.messageOwner.attachPath;
                            newMsg.entities = msgObj.messageOwner.entities;
                            if (!newMsg.entities.isEmpty()) {
                                newMsg.flags |= 128;
                            }
                            if (newMsg.attachPath == null) {
                                newMsg.attachPath = "";
                            }
                            int newMessageId = UserConfig.getNewMessageId();
                            newMsg.id = newMessageId;
                            newMsg.local_id = newMessageId;
                            newMsg.out = true;
                            long lastGroupedId = msgObj.messageOwner.grouped_id;
                            if (lastGroupedId != 0) {
                                Long gId = (Long) groupsMap.get(Long.valueOf(msgObj.messageOwner.grouped_id));
                                if (gId == null) {
                                    gId = Long.valueOf(Utilities.random.nextLong());
                                    groupsMap.put(Long.valueOf(msgObj.messageOwner.grouped_id), gId);
                                }
                                newMsg.grouped_id = gId.longValue();
                                newMsg.flags |= 131072;
                            }
                            if (a != messages.size() - 1) {
                                if (((MessageObject) messages.get(a + 1)).messageOwner.grouped_id != msgObj.messageOwner.grouped_id) {
                                    groupedIdChanged = true;
                                }
                            }
                            if (to_id.channel_id == 0 || isMegagroup) {
                                newMsg.from_id = UserConfig.getClientUserId();
                                newMsg.flags |= 256;
                            } else {
                                newMsg.from_id = isSignature ? UserConfig.getClientUserId() : -to_id.channel_id;
                                newMsg.post = true;
                            }
                            if (newMsg.random_id == 0) {
                                newMsg.random_id = getNextRandomId();
                            }
                            randomIds.add(Long.valueOf(newMsg.random_id));
                            messagesByRandomIds.put(Long.valueOf(newMsg.random_id), newMsg);
                            ids.add(Integer.valueOf(newMsg.fwd_msg_id));
                            newMsg.date = ConnectionsManager.getInstance().getCurrentTime();
                            if (!(inputPeer instanceof TLRPC$TL_inputPeerChannel)) {
                                if ((msgObj.messageOwner.flags & 1024) != 0) {
                                    newMsg.views = msgObj.messageOwner.views;
                                    newMsg.flags |= 1024;
                                }
                                newMsg.unread = true;
                            } else if (isMegagroup) {
                                newMsg.unread = true;
                            } else {
                                newMsg.views = 1;
                                newMsg.flags |= 1024;
                            }
                            newMsg.dialog_id = peer;
                            newMsg.to_id = to_id;
                            if (MessageObject.isVoiceMessage(newMsg) || MessageObject.isRoundVideoMessage(newMsg)) {
                                newMsg.media_unread = true;
                            }
                            if (msgObj.messageOwner.to_id instanceof TLRPC$TL_peerChannel) {
                                newMsg.ttl = -msgObj.messageOwner.to_id.channel_id;
                            }
                            MessageObject messageObject = new MessageObject(newMsg, null, true);
                            messageObject.messageOwner.send_state = 1;
                            objArr.add(messageObject);
                            arr.add(newMsg);
                            putToSendingMessages(newMsg);
                            if (BuildVars.DEBUG_VERSION) {
                                FileLog.m92e("forward message user_id = " + inputPeer.user_id + " chat_id = " + inputPeer.chat_id + " channel_id = " + inputPeer.channel_id + " access_hash = " + inputPeer.access_hash);
                            }
                            if (!((groupedIdChanged && arr.size() > 0) || arr.size() == 100 || a == messages.size() - 1)) {
                                if (a != messages.size() - 1) {
                                    if (((MessageObject) messages.get(a + 1)).getDialogId() == msgObj.getDialogId()) {
                                    }
                                }
                            }
                            MessagesStorage.getInstance().putMessages(new ArrayList(arr), false, true, false, 0);
                            MessagesController.getInstance().updateInterfaceWithMessages(peer, objArr);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                            UserConfig.saveConfig(false);
                            TLRPC$TL_messages_forwardMessages req = new TLRPC$TL_messages_forwardMessages();
                            req.to_peer = inputPeer;
                            req.grouped = lastGroupedId != 0;
                            if (req.to_peer instanceof TLRPC$TL_inputPeerChannel) {
                                req.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + peer, false);
                            }
                            if (msgObj.messageOwner.to_id instanceof TLRPC$TL_peerChannel) {
                                chat = MessagesController.getInstance().getChat(Integer.valueOf(msgObj.messageOwner.to_id.channel_id));
                                req.from_peer = new TLRPC$TL_inputPeerChannel();
                                req.from_peer.channel_id = msgObj.messageOwner.to_id.channel_id;
                                if (chat != null) {
                                    req.from_peer.access_hash = chat.access_hash;
                                }
                            } else {
                                req.from_peer = new TLRPC$TL_inputPeerEmpty();
                            }
                            req.random_id = randomIds;
                            req.id = ids;
                            boolean z = messages.size() == 1 && ((MessageObject) messages.get(0)).messageOwner.with_my_score;
                            req.with_my_score = z;
                            ConnectionsManager.getInstance().sendRequest(req, new SendMessagesHelper$6(this, peer, isMegagroup, toMyself, messagesByRandomIds, arr, objArr, to_id, req), 68);
                            if (a != messages.size() - 1) {
                                objArr = new ArrayList();
                                arr = new ArrayList();
                                randomIds = new ArrayList();
                                ids = new ArrayList();
                                messagesByRandomIds = new HashMap();
                            }
                        } else if (sendResult == 0) {
                            sendResult = 2;
                        }
                    } else if (sendResult == 0) {
                        sendResult = 1;
                    }
                }
                a++;
            }
            return sendResult;
        }
        for (a = 0; a < messages.size(); a++) {
            processForwardFromMyName((MessageObject) messages.get(a), peer);
        }
        return 0;
    }

    public int editMessage(MessageObject messageObject, String message, boolean searchLinks, BaseFragment fragment, ArrayList<TLRPC$MessageEntity> entities, Runnable callback) {
        boolean z = false;
        if (fragment == null || fragment.getParentActivity() == null || callback == null) {
            return 0;
        }
        TLRPC$TL_messages_editMessage req = new TLRPC$TL_messages_editMessage();
        req.peer = MessagesController.getInputPeer((int) messageObject.getDialogId());
        req.message = message;
        req.flags |= 2048;
        req.id = messageObject.getId();
        if (!searchLinks) {
            z = true;
        }
        req.no_webpage = z;
        if (entities != null) {
            req.entities = entities;
            req.flags |= 8;
        }
        return ConnectionsManager.getInstance().sendRequest(req, new SendMessagesHelper$7(this, callback, fragment, req));
    }

    private void sendLocation(Location location) {
        TLRPC$MessageMedia mediaGeo = new TLRPC$TL_messageMediaGeo();
        mediaGeo.geo = new TLRPC$TL_geoPoint();
        mediaGeo.geo.lat = location.getLatitude();
        mediaGeo.geo._long = location.getLongitude();
        for (Entry<String, MessageObject> entry : this.waitingForLocation.entrySet()) {
            MessageObject messageObject = (MessageObject) entry.getValue();
            getInstance().sendMessage(mediaGeo, messageObject.getDialogId(), messageObject, null, null);
        }
    }

    public void sendCurrentLocation(MessageObject messageObject, TLRPC$KeyboardButton button) {
        if (messageObject != null && button != null) {
            this.waitingForLocation.put(messageObject.getDialogId() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + messageObject.getId() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + Utilities.bytesToHex(button.data) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + (button instanceof TLRPC$TL_keyboardButtonGame ? BuildConfig.VERSION_NAME : "0"), messageObject);
            this.locationProvider.start();
        }
    }

    public boolean isSendingCurrentLocation(MessageObject messageObject, TLRPC$KeyboardButton button) {
        if (messageObject == null || button == null) {
            return false;
        }
        return this.waitingForLocation.containsKey(messageObject.getDialogId() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + messageObject.getId() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + Utilities.bytesToHex(button.data) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + (button instanceof TLRPC$TL_keyboardButtonGame ? BuildConfig.VERSION_NAME : "0"));
    }

    public void sendCallback(boolean cache, MessageObject messageObject, TLRPC$KeyboardButton button, ChatActivity parentFragment) {
        if (messageObject != null && button != null && parentFragment != null) {
            boolean cacheFinal;
            int type;
            if (button instanceof TLRPC$TL_keyboardButtonGame) {
                cacheFinal = false;
                type = 1;
            } else {
                cacheFinal = cache;
                if (button instanceof TLRPC$TL_keyboardButtonBuy) {
                    type = 2;
                } else {
                    type = 0;
                }
            }
            String key = messageObject.getDialogId() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + messageObject.getId() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + Utilities.bytesToHex(button.data) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + type;
            this.waitingForCallback.put(key, messageObject);
            RequestDelegate requestDelegate = new SendMessagesHelper$8(this, key, cacheFinal, messageObject, button, parentFragment);
            if (cacheFinal) {
                MessagesStorage.getInstance().getBotCache(key, requestDelegate);
            } else if (!(button instanceof TLRPC$TL_keyboardButtonBuy)) {
                TLRPC$TL_messages_getBotCallbackAnswer req = new TLRPC$TL_messages_getBotCallbackAnswer();
                req.peer = MessagesController.getInputPeer((int) messageObject.getDialogId());
                req.msg_id = messageObject.getId();
                req.game = button instanceof TLRPC$TL_keyboardButtonGame;
                if (button.data != null) {
                    req.flags |= 1;
                    req.data = button.data;
                }
                ConnectionsManager.getInstance().sendRequest(req, requestDelegate, 2);
            } else if ((messageObject.messageOwner.media.flags & 4) == 0) {
                TLRPC$TL_payments_getPaymentForm req2 = new TLRPC$TL_payments_getPaymentForm();
                req2.msg_id = messageObject.getId();
                ConnectionsManager.getInstance().sendRequest(req2, requestDelegate, 2);
            } else {
                TLRPC$TL_payments_getPaymentReceipt req3 = new TLRPC$TL_payments_getPaymentReceipt();
                req3.msg_id = messageObject.messageOwner.media.receipt_msg_id;
                ConnectionsManager.getInstance().sendRequest(req3, requestDelegate, 2);
            }
        }
    }

    public boolean isSendingCallback(MessageObject messageObject, TLRPC$KeyboardButton button) {
        if (messageObject == null || button == null) {
            return false;
        }
        int type;
        if (button instanceof TLRPC$TL_keyboardButtonGame) {
            type = 1;
        } else if (button instanceof TLRPC$TL_keyboardButtonBuy) {
            type = 2;
        } else {
            type = 0;
        }
        return this.waitingForCallback.containsKey(messageObject.getDialogId() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + messageObject.getId() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + Utilities.bytesToHex(button.data) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + type);
    }

    public void sendGame(TLRPC$InputPeer peer, TLRPC$TL_inputMediaGame game, long random_id, long taskId) {
        Throwable e;
        long newTaskId;
        if (peer != null && game != null) {
            TLRPC$TL_messages_sendMedia request = new TLRPC$TL_messages_sendMedia();
            request.peer = peer;
            if (request.peer instanceof TLRPC$TL_inputPeerChannel) {
                request.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + peer.channel_id, false);
            }
            request.random_id = random_id != 0 ? random_id : getNextRandomId();
            request.media = game;
            if (taskId == 0) {
                NativeByteBuffer data = null;
                try {
                    NativeByteBuffer data2 = new NativeByteBuffer(((peer.getObjectSize() + game.getObjectSize()) + 4) + 8);
                    try {
                        data2.writeInt32(3);
                        data2.writeInt64(random_id);
                        peer.serializeToStream(data2);
                        game.serializeToStream(data2);
                        data = data2;
                    } catch (Exception e2) {
                        e = e2;
                        data = data2;
                        FileLog.m94e(e);
                        newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                        ConnectionsManager.getInstance().sendRequest(request, new SendMessagesHelper$9(this, newTaskId));
                    }
                } catch (Exception e3) {
                    e = e3;
                    FileLog.m94e(e);
                    newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                    ConnectionsManager.getInstance().sendRequest(request, new SendMessagesHelper$9(this, newTaskId));
                }
                newTaskId = MessagesStorage.getInstance().createPendingTask(data);
            } else {
                newTaskId = taskId;
            }
            ConnectionsManager.getInstance().sendRequest(request, new SendMessagesHelper$9(this, newTaskId));
        }
    }

    public void sendMessage(MessageObject retryMessageObject) {
        sendMessage(null, null, null, null, null, null, null, retryMessageObject.getDialogId(), retryMessageObject.messageOwner.attachPath, null, null, true, retryMessageObject, null, retryMessageObject.messageOwner.reply_markup, retryMessageObject.messageOwner.params, 0);
    }

    public void sendMessage(User user, long peer, MessageObject reply_to_msg, TLRPC$ReplyMarkup replyMarkup, HashMap<String, String> params) {
        sendMessage(null, null, null, null, user, null, null, peer, null, reply_to_msg, null, true, null, null, replyMarkup, params, 0);
    }

    public void sendMessage(TLRPC$TL_document document, VideoEditedInfo videoEditedInfo, String path, long peer, MessageObject reply_to_msg, TLRPC$ReplyMarkup replyMarkup, HashMap<String, String> params, int ttl) {
        sendMessage(null, null, null, videoEditedInfo, null, document, null, peer, path, reply_to_msg, null, true, null, null, replyMarkup, params, ttl);
    }

    public void sendMessage(String message, long peer, MessageObject reply_to_msg, TLRPC$WebPage webPage, boolean searchLinks, ArrayList<TLRPC$MessageEntity> entities, TLRPC$ReplyMarkup replyMarkup, HashMap<String, String> params) {
        sendMessage(message, null, null, null, null, null, null, peer, null, reply_to_msg, webPage, searchLinks, null, entities, replyMarkup, params, 0);
    }

    public void sendMessage(TLRPC$MessageMedia location, long peer, MessageObject reply_to_msg, TLRPC$ReplyMarkup replyMarkup, HashMap<String, String> params) {
        sendMessage(null, location, null, null, null, null, null, peer, null, reply_to_msg, null, true, null, null, replyMarkup, params, 0);
    }

    public void sendMessage(TLRPC$TL_game game, long peer, TLRPC$ReplyMarkup replyMarkup, HashMap<String, String> params) {
        sendMessage(null, null, null, null, null, null, game, peer, null, null, null, true, null, null, replyMarkup, params, 0);
    }

    public void sendMessage(TLRPC$TL_photo photo, String path, long peer, MessageObject reply_to_msg, TLRPC$ReplyMarkup replyMarkup, HashMap<String, String> params, int ttl) {
        sendMessage(null, null, photo, null, null, null, null, peer, path, reply_to_msg, null, true, null, null, replyMarkup, params, ttl);
    }

    private void sendMessage(String message, TLRPC$MessageMedia location, TLRPC$TL_photo photo, VideoEditedInfo videoEditedInfo, User user, TLRPC$TL_document document, TLRPC$TL_game game, long peer, String path, MessageObject reply_to_msg, TLRPC$WebPage webPage, boolean searchLinks, MessageObject retryMessageObject, ArrayList<TLRPC$MessageEntity> entities, TLRPC$ReplyMarkup replyMarkup, HashMap<String, String> params, int ttl) {
        Throwable e;
        if (peer != 0) {
            TLRPC$Chat chat;
            MessageObject newMsgObj;
            int a;
            DocumentAttribute attribute;
            long groupId;
            SendMessagesHelper$DelayedMessage delayedMessage;
            SendMessagesHelper$DelayedMessage sendMessagesHelper$DelayedMessage;
            SendMessagesHelper$DelayedMessage delayedMessage2;
            String originalPath = null;
            if (params != null) {
                if (params.containsKey("originalPath")) {
                    originalPath = (String) params.get("originalPath");
                }
            }
            TLRPC$Message newMsg = null;
            int type = -1;
            int lower_id = (int) peer;
            int high_id = (int) (peer >> 32);
            boolean isChannel = false;
            TLRPC$EncryptedChat encryptedChat = null;
            TLRPC$InputPeer sendToPeer = lower_id != 0 ? MessagesController.getInputPeer(lower_id) : null;
            ArrayList<TLRPC$InputUser> sendToPeers = null;
            if (lower_id == 0) {
                encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(high_id));
                if (encryptedChat == null) {
                    if (retryMessageObject != null) {
                        MessagesStorage.getInstance().markMessageAsSendError(retryMessageObject.messageOwner);
                        retryMessageObject.messageOwner.send_state = 2;
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(retryMessageObject.getId()));
                        processSentMessage(retryMessageObject.getId());
                        return;
                    }
                    return;
                }
            } else if (sendToPeer instanceof TLRPC$TL_inputPeerChannel) {
                chat = MessagesController.getInstance().getChat(Integer.valueOf(sendToPeer.channel_id));
                isChannel = (chat == null || chat.megagroup) ? false : true;
            }
            if (retryMessageObject != null) {
                try {
                    newMsg = retryMessageObject.messageOwner;
                    if (retryMessageObject.isForwarded()) {
                        type = 4;
                    } else {
                        if (retryMessageObject.type == 0) {
                            if (!(retryMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame)) {
                                message = newMsg.message;
                            }
                            type = 0;
                        } else if (retryMessageObject.type == 4) {
                            location = newMsg.media;
                            type = 1;
                        } else if (retryMessageObject.type == 1) {
                            photo = (TLRPC$TL_photo) newMsg.media.photo;
                            type = 2;
                        } else if (retryMessageObject.type == 3 || retryMessageObject.type == 5 || videoEditedInfo != null) {
                            type = 3;
                            document = (TLRPC$TL_document) newMsg.media.document;
                        } else if (retryMessageObject.type == 12) {
                            User user2 = new TLRPC$TL_userRequest_old2();
                            try {
                                user2.phone = newMsg.media.phone_number;
                                user2.first_name = newMsg.media.first_name;
                                user2.last_name = newMsg.media.last_name;
                                user2.id = newMsg.media.user_id;
                                type = 6;
                                user = user2;
                            } catch (Exception e2) {
                                e = e2;
                                newMsgObj = null;
                                user = user2;
                                FileLog.m94e(e);
                                MessagesStorage.getInstance().markMessageAsSendError(newMsg);
                                if (newMsgObj != null) {
                                    newMsgObj.messageOwner.send_state = 2;
                                }
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(newMsg.id));
                                processSentMessage(newMsg.id);
                            }
                        } else if (retryMessageObject.type == 8 || retryMessageObject.type == 9 || retryMessageObject.type == 13 || retryMessageObject.type == 14) {
                            document = (TLRPC$TL_document) newMsg.media.document;
                            type = 7;
                        } else if (retryMessageObject.type == 2) {
                            document = (TLRPC$TL_document) newMsg.media.document;
                            type = 8;
                        }
                        if (params != null) {
                            if (params.containsKey("query_id")) {
                                type = 9;
                            }
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    newMsgObj = null;
                    FileLog.m94e(e);
                    MessagesStorage.getInstance().markMessageAsSendError(newMsg);
                    if (newMsgObj != null) {
                        newMsgObj.messageOwner.send_state = 2;
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(newMsg.id));
                    processSentMessage(newMsg.id);
                }
            }
            if (message != null) {
                if (encryptedChat != null) {
                    newMsg = new TLRPC$TL_message_secret();
                } else {
                    newMsg = new TLRPC$TL_message();
                }
                if (!(entities == null || entities.isEmpty())) {
                    newMsg.entities = entities;
                }
                if (encryptedChat != null && (webPage instanceof TLRPC$TL_webPagePending)) {
                    if (webPage.url != null) {
                        TLRPC$WebPage newWebPage = new TLRPC$TL_webPageUrlPending();
                        newWebPage.url = webPage.url;
                        webPage = newWebPage;
                    } else {
                        webPage = null;
                    }
                }
                if (webPage == null) {
                    newMsg.media = new TLRPC$TL_messageMediaEmpty();
                } else {
                    newMsg.media = new TLRPC$TL_messageMediaWebPage();
                    newMsg.media.webpage = webPage;
                }
                if (params != null) {
                    if (params.containsKey("query_id")) {
                        type = 9;
                        newMsg.message = message;
                    }
                }
                type = 0;
                newMsg.message = message;
            } else if (location != null) {
                if (encryptedChat != null) {
                    newMsg = new TLRPC$TL_message_secret();
                } else {
                    newMsg = new TLRPC$TL_message();
                }
                newMsg.media = location;
                newMsg.message = "";
                if (params != null) {
                    if (params.containsKey("query_id")) {
                        type = 9;
                    }
                }
                type = 1;
            } else if (photo != null) {
                if (encryptedChat != null) {
                    newMsg = new TLRPC$TL_message_secret();
                } else {
                    newMsg = new TLRPC$TL_message();
                }
                newMsg.media = new TLRPC$TL_messageMediaPhoto();
                r4 = newMsg.media;
                r4.flags |= 3;
                newMsg.media.caption = photo.caption != null ? photo.caption : "";
                if (ttl != 0) {
                    newMsg.media.ttl_seconds = ttl;
                    newMsg.ttl = ttl;
                    r4 = newMsg.media;
                    r4.flags |= 4;
                }
                newMsg.media.photo = photo;
                if (params != null) {
                    if (params.containsKey("query_id")) {
                        type = 9;
                        newMsg.message = "-1";
                        if (path != null && path.length() > 0) {
                            if (path.startsWith("http")) {
                                newMsg.attachPath = path;
                            }
                        }
                        newMsg.attachPath = FileLoader.getPathToAttach(((TLRPC$PhotoSize) photo.sizes.get(photo.sizes.size() - 1)).location, true).toString();
                    }
                }
                type = 2;
                newMsg.message = "-1";
                if (path.startsWith("http")) {
                    newMsg.attachPath = path;
                }
                newMsg.attachPath = FileLoader.getPathToAttach(((TLRPC$PhotoSize) photo.sizes.get(photo.sizes.size() - 1)).location, true).toString();
            } else if (game != null) {
                TLRPC$Message newMsg2 = new TLRPC$TL_message();
                try {
                    newMsg2.media = new TLRPC$TL_messageMediaGame();
                    newMsg2.media.caption = "";
                    newMsg2.media.game = game;
                    newMsg2.message = "";
                    if (params != null) {
                        if (params.containsKey("query_id")) {
                            type = 9;
                            newMsg = newMsg2;
                        }
                    }
                    newMsg = newMsg2;
                } catch (Exception e4) {
                    e = e4;
                    newMsgObj = null;
                    newMsg = newMsg2;
                    FileLog.m94e(e);
                    MessagesStorage.getInstance().markMessageAsSendError(newMsg);
                    if (newMsgObj != null) {
                        newMsgObj.messageOwner.send_state = 2;
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(newMsg.id));
                    processSentMessage(newMsg.id);
                }
            } else if (user != null) {
                String str;
                if (encryptedChat != null) {
                    newMsg = new TLRPC$TL_message_secret();
                } else {
                    newMsg = new TLRPC$TL_message();
                }
                newMsg.media = new TLRPC$TL_messageMediaContact();
                newMsg.media.phone_number = user.phone;
                newMsg.media.first_name = user.first_name;
                newMsg.media.last_name = user.last_name;
                newMsg.media.user_id = user.id;
                if (newMsg.media.first_name == null) {
                    str = "";
                    newMsg.media.first_name = str;
                    user.first_name = str;
                }
                if (newMsg.media.last_name == null) {
                    str = "";
                    newMsg.media.last_name = str;
                    user.last_name = str;
                }
                newMsg.message = "";
                if (params != null) {
                    if (params.containsKey("query_id")) {
                        type = 9;
                    }
                }
                type = 6;
            } else if (document != null) {
                TLRPC$TL_documentAttributeSticker_layer55 attributeSticker;
                String name;
                if (encryptedChat != null) {
                    newMsg = new TLRPC$TL_message_secret();
                } else {
                    newMsg = new TLRPC$TL_message();
                }
                newMsg.media = new TLRPC$TL_messageMediaDocument();
                r4 = newMsg.media;
                r4.flags |= 3;
                if (ttl != 0) {
                    newMsg.media.ttl_seconds = ttl;
                    newMsg.ttl = ttl;
                    r4 = newMsg.media;
                    r4.flags |= 4;
                }
                newMsg.media.caption = document.caption != null ? document.caption : "";
                newMsg.media.document = document;
                if (params != null) {
                    if (params.containsKey("query_id")) {
                        type = 9;
                        if (videoEditedInfo == null) {
                            newMsg.message = "-1";
                        } else {
                            newMsg.message = videoEditedInfo.getString();
                        }
                        if (encryptedChat != null || document.dc_id <= 0 || MessageObject.isStickerDocument(document)) {
                            newMsg.attachPath = path;
                        } else {
                            newMsg.attachPath = FileLoader.getPathToAttach(document).toString();
                        }
                        if (encryptedChat != null && MessageObject.isStickerDocument(document)) {
                            a = 0;
                            while (a < document.attributes.size()) {
                                attribute = (DocumentAttribute) document.attributes.get(a);
                                if (attribute instanceof TLRPC$TL_documentAttributeSticker) {
                                    document.attributes.remove(a);
                                    attributeSticker = new TLRPC$TL_documentAttributeSticker_layer55();
                                    document.attributes.add(attributeSticker);
                                    attributeSticker.alt = attribute.alt;
                                    if (attribute.stickerset != null) {
                                        if (attribute.stickerset instanceof TLRPC$TL_inputStickerSetShortName) {
                                            name = attribute.stickerset.short_name;
                                        } else {
                                            name = StickersQuery.getStickerSetName(attribute.stickerset.id);
                                        }
                                        if (TextUtils.isEmpty(name)) {
                                            attributeSticker.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                        } else {
                                            attributeSticker.stickerset = new TLRPC$TL_inputStickerSetShortName();
                                            attributeSticker.stickerset.short_name = name;
                                        }
                                    } else {
                                        attributeSticker.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                    }
                                } else {
                                    a++;
                                }
                            }
                        }
                    }
                }
                if (MessageObject.isVideoDocument(document) || MessageObject.isRoundVideoDocument(document) || videoEditedInfo != null) {
                    type = 3;
                    if (videoEditedInfo == null) {
                        newMsg.message = videoEditedInfo.getString();
                    } else {
                        newMsg.message = "-1";
                    }
                    if (encryptedChat != null) {
                    }
                    newMsg.attachPath = path;
                    a = 0;
                    while (a < document.attributes.size()) {
                        attribute = (DocumentAttribute) document.attributes.get(a);
                        if (attribute instanceof TLRPC$TL_documentAttributeSticker) {
                            a++;
                        } else {
                            document.attributes.remove(a);
                            attributeSticker = new TLRPC$TL_documentAttributeSticker_layer55();
                            document.attributes.add(attributeSticker);
                            attributeSticker.alt = attribute.alt;
                            if (attribute.stickerset != null) {
                                attributeSticker.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                            } else {
                                if (attribute.stickerset instanceof TLRPC$TL_inputStickerSetShortName) {
                                    name = StickersQuery.getStickerSetName(attribute.stickerset.id);
                                } else {
                                    name = attribute.stickerset.short_name;
                                }
                                if (TextUtils.isEmpty(name)) {
                                    attributeSticker.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                } else {
                                    attributeSticker.stickerset = new TLRPC$TL_inputStickerSetShortName();
                                    attributeSticker.stickerset.short_name = name;
                                }
                            }
                        }
                    }
                } else {
                    if (MessageObject.isVoiceDocument(document)) {
                        type = 8;
                    } else {
                        type = 7;
                    }
                    if (videoEditedInfo == null) {
                        newMsg.message = "-1";
                    } else {
                        newMsg.message = videoEditedInfo.getString();
                    }
                    if (encryptedChat != null) {
                    }
                    newMsg.attachPath = path;
                    a = 0;
                    while (a < document.attributes.size()) {
                        attribute = (DocumentAttribute) document.attributes.get(a);
                        if (attribute instanceof TLRPC$TL_documentAttributeSticker) {
                            document.attributes.remove(a);
                            attributeSticker = new TLRPC$TL_documentAttributeSticker_layer55();
                            document.attributes.add(attributeSticker);
                            attributeSticker.alt = attribute.alt;
                            if (attribute.stickerset != null) {
                                if (attribute.stickerset instanceof TLRPC$TL_inputStickerSetShortName) {
                                    name = attribute.stickerset.short_name;
                                } else {
                                    name = StickersQuery.getStickerSetName(attribute.stickerset.id);
                                }
                                if (TextUtils.isEmpty(name)) {
                                    attributeSticker.stickerset = new TLRPC$TL_inputStickerSetShortName();
                                    attributeSticker.stickerset.short_name = name;
                                } else {
                                    attributeSticker.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                }
                            } else {
                                attributeSticker.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                            }
                        } else {
                            a++;
                        }
                    }
                }
            }
            if (newMsg.attachPath == null) {
                newMsg.attachPath = "";
            }
            int newMessageId = UserConfig.getNewMessageId();
            newMsg.id = newMessageId;
            newMsg.local_id = newMessageId;
            newMsg.out = true;
            if (!isChannel || sendToPeer == null) {
                newMsg.from_id = UserConfig.getClientUserId();
                newMsg.flags |= 256;
            } else {
                newMsg.from_id = -sendToPeer.channel_id;
            }
            UserConfig.saveConfig(false);
            if (newMsg.random_id == 0) {
                newMsg.random_id = getNextRandomId();
            }
            if (params != null) {
                if (params.containsKey("bot")) {
                    if (encryptedChat != null) {
                        newMsg.via_bot_name = (String) params.get("bot_name");
                        if (newMsg.via_bot_name == null) {
                            newMsg.via_bot_name = "";
                        }
                    } else {
                        newMsg.via_bot_id = Utilities.parseInt((String) params.get("bot")).intValue();
                    }
                    newMsg.flags |= 2048;
                }
            }
            newMsg.params = params;
            if (retryMessageObject == null || !retryMessageObject.resendAsIs) {
                newMsg.date = ConnectionsManager.getInstance().getCurrentTime();
                if (sendToPeer instanceof TLRPC$TL_inputPeerChannel) {
                    if (isChannel) {
                        newMsg.views = 1;
                        newMsg.flags |= 1024;
                    }
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(sendToPeer.channel_id));
                    if (chat != null) {
                        if (chat.megagroup) {
                            newMsg.flags |= Integer.MIN_VALUE;
                            newMsg.unread = true;
                        } else {
                            newMsg.post = true;
                            if (chat.signatures) {
                                newMsg.from_id = UserConfig.getClientUserId();
                            }
                        }
                    }
                } else {
                    newMsg.unread = true;
                }
            }
            newMsg.flags |= 512;
            newMsg.dialog_id = peer;
            if (reply_to_msg != null) {
                if (encryptedChat == null || reply_to_msg.messageOwner.random_id == 0) {
                    newMsg.flags |= 8;
                } else {
                    newMsg.reply_to_random_id = reply_to_msg.messageOwner.random_id;
                    newMsg.flags |= 8;
                }
                newMsg.reply_to_msg_id = reply_to_msg.getId();
            }
            if (replyMarkup != null && encryptedChat == null) {
                newMsg.flags |= 64;
                newMsg.reply_markup = replyMarkup;
            }
            if (lower_id == 0) {
                newMsg.to_id = new TLRPC$TL_peerUser();
                if (encryptedChat.participant_id == UserConfig.getClientUserId()) {
                    newMsg.to_id.user_id = encryptedChat.admin_id;
                } else {
                    newMsg.to_id.user_id = encryptedChat.participant_id;
                }
                if (ttl != 0) {
                    newMsg.ttl = ttl;
                } else {
                    newMsg.ttl = encryptedChat.ttl;
                }
                if (!(newMsg.ttl == 0 || newMsg.media.document == null)) {
                    int duration;
                    if (MessageObject.isVoiceMessage(newMsg)) {
                        duration = 0;
                        for (a = 0; a < newMsg.media.document.attributes.size(); a++) {
                            attribute = (DocumentAttribute) newMsg.media.document.attributes.get(a);
                            if (attribute instanceof TLRPC$TL_documentAttributeAudio) {
                                duration = attribute.duration;
                                break;
                            }
                        }
                        newMsg.ttl = Math.max(newMsg.ttl, duration + 1);
                    } else if (MessageObject.isVideoMessage(newMsg) || MessageObject.isRoundVideoMessage(newMsg)) {
                        duration = 0;
                        for (a = 0; a < newMsg.media.document.attributes.size(); a++) {
                            attribute = (DocumentAttribute) newMsg.media.document.attributes.get(a);
                            if (attribute instanceof TLRPC$TL_documentAttributeVideo) {
                                duration = attribute.duration;
                                break;
                            }
                        }
                        newMsg.ttl = Math.max(newMsg.ttl, duration + 1);
                    }
                }
            } else if (high_id != 1) {
                newMsg.to_id = MessagesController.getPeer(lower_id);
                if (lower_id > 0) {
                    User sendToUser = MessagesController.getInstance().getUser(Integer.valueOf(lower_id));
                    if (sendToUser == null) {
                        processSentMessage(newMsg.id);
                        return;
                    } else if (sendToUser.bot) {
                        newMsg.unread = false;
                    }
                }
            } else if (this.currentChatInfo == null) {
                MessagesStorage.getInstance().markMessageAsSendError(newMsg);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(newMsg.id));
                processSentMessage(newMsg.id);
                return;
            } else {
                ArrayList<TLRPC$InputUser> sendToPeers2 = new ArrayList();
                try {
                    Iterator it = this.currentChatInfo.participants.participants.iterator();
                    while (it.hasNext()) {
                        TLRPC$InputUser peerUser = MessagesController.getInputUser(MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$ChatParticipant) it.next()).user_id)));
                        if (peerUser != null) {
                            sendToPeers2.add(peerUser);
                        }
                    }
                    newMsg.to_id = new TLRPC$TL_peerChat();
                    newMsg.to_id.chat_id = lower_id;
                    sendToPeers = sendToPeers2;
                } catch (Exception e5) {
                    e = e5;
                    sendToPeers = sendToPeers2;
                    newMsgObj = null;
                    FileLog.m94e(e);
                    MessagesStorage.getInstance().markMessageAsSendError(newMsg);
                    if (newMsgObj != null) {
                        newMsgObj.messageOwner.send_state = 2;
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(newMsg.id));
                    processSentMessage(newMsg.id);
                }
            }
            if (high_id != 1) {
                if (MessageObject.isVoiceMessage(newMsg) || MessageObject.isRoundVideoMessage(newMsg)) {
                    newMsg.media_unread = true;
                }
            }
            newMsg.send_state = 1;
            newMsgObj = new MessageObject(newMsg, null, true);
            try {
                newMsgObj.replyMessageObject = reply_to_msg;
                if (!newMsgObj.isForwarded() && ((newMsgObj.type == 3 || videoEditedInfo != null || newMsgObj.type == 2) && !TextUtils.isEmpty(newMsg.attachPath))) {
                    newMsgObj.attachPathExists = true;
                }
                groupId = 0;
                boolean isFinalGroupMedia = false;
                if (params != null) {
                    String groupIdStr = (String) params.get("groupId");
                    if (groupIdStr != null) {
                        groupId = Utilities.parseLong(groupIdStr).longValue();
                        newMsg.grouped_id = groupId;
                        newMsg.flags |= 131072;
                    }
                    isFinalGroupMedia = params.get("final") != null;
                }
                if (groupId == 0) {
                    ArrayList<MessageObject> objArr = new ArrayList();
                    objArr.add(newMsgObj);
                    ArrayList<TLRPC$Message> arr = new ArrayList();
                    arr.add(newMsg);
                    MessagesStorage.getInstance().putMessages(arr, false, true, false, 0);
                    MessagesController.getInstance().updateInterfaceWithMessages(peer, objArr);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                    delayedMessage = null;
                } else {
                    ArrayList<SendMessagesHelper$DelayedMessage> arrayList = (ArrayList) this.delayedMessages.get("group_" + groupId);
                    if (arrayList != null) {
                        delayedMessage = (SendMessagesHelper$DelayedMessage) arrayList.get(0);
                    } else {
                        delayedMessage = null;
                    }
                    if (delayedMessage == null) {
                        sendMessagesHelper$DelayedMessage = new SendMessagesHelper$DelayedMessage(this, peer);
                        sendMessagesHelper$DelayedMessage.type = 4;
                        sendMessagesHelper$DelayedMessage.groupId = groupId;
                        sendMessagesHelper$DelayedMessage.messageObjects = new ArrayList();
                        sendMessagesHelper$DelayedMessage.messages = new ArrayList();
                        sendMessagesHelper$DelayedMessage.originalPaths = new ArrayList();
                        sendMessagesHelper$DelayedMessage.extraHashMap = new HashMap();
                        sendMessagesHelper$DelayedMessage.encryptedChat = encryptedChat;
                    } else {
                        delayedMessage2 = delayedMessage;
                    }
                    if (isFinalGroupMedia) {
                        delayedMessage2.finalGroupMessage = newMsg.id;
                    }
                    delayedMessage = delayedMessage2;
                }
            } catch (Exception e6) {
                e = e6;
                FileLog.m94e(e);
                MessagesStorage.getInstance().markMessageAsSendError(newMsg);
                if (newMsgObj != null) {
                    newMsgObj.messageOwner.send_state = 2;
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(newMsg.id));
                processSentMessage(newMsg.id);
            }
            try {
                if (BuildVars.DEBUG_VERSION && sendToPeer != null) {
                    FileLog.m92e("send message user_id = " + sendToPeer.user_id + " chat_id = " + sendToPeer.chat_id + " channel_id = " + sendToPeer.channel_id + " access_hash = " + sendToPeer.access_hash);
                }
                TLRPC$TL_decryptedMessage reqSend;
                ArrayList<Long> random_ids;
                if (type == 0 || !(type != 9 || message == null || encryptedChat == null)) {
                    if (encryptedChat != null) {
                        if (AndroidUtilities.getPeerLayerVersion(encryptedChat.layer) >= 73) {
                            reqSend = new TLRPC$TL_decryptedMessage();
                        } else {
                            reqSend = new TLRPC$TL_decryptedMessage_layer45();
                        }
                        reqSend.ttl = newMsg.ttl;
                        if (!(entities == null || entities.isEmpty())) {
                            reqSend.entities = entities;
                            reqSend.flags |= 128;
                        }
                        if (!(reply_to_msg == null || reply_to_msg.messageOwner.random_id == 0)) {
                            reqSend.reply_to_random_id = reply_to_msg.messageOwner.random_id;
                            reqSend.flags |= 8;
                        }
                        if (params != null) {
                            if (params.get("bot_name") != null) {
                                reqSend.via_bot_name = (String) params.get("bot_name");
                                reqSend.flags |= 2048;
                            }
                        }
                        reqSend.random_id = newMsg.random_id;
                        reqSend.message = message;
                        if (webPage == null || webPage.url == null) {
                            reqSend.media = new TLRPC$TL_decryptedMessageMediaEmpty();
                        } else {
                            reqSend.media = new TLRPC$TL_decryptedMessageMediaWebPage();
                            reqSend.media.url = webPage.url;
                            reqSend.flags |= 512;
                        }
                        SecretChatHelper.getInstance().performSendEncryptedRequest(reqSend, newMsgObj.messageOwner, encryptedChat, null, null, newMsgObj);
                        if (retryMessageObject == null) {
                            DraftQuery.cleanDraft(peer, false);
                        }
                        delayedMessage2 = delayedMessage;
                    } else if (sendToPeers != null) {
                        TLRPC$TL_messages_sendBroadcast reqSend2 = new TLRPC$TL_messages_sendBroadcast();
                        random_ids = new ArrayList();
                        for (a = 0; a < sendToPeers.size(); a++) {
                            random_ids.add(Long.valueOf(Utilities.random.nextLong()));
                        }
                        reqSend2.message = message;
                        reqSend2.contacts = sendToPeers;
                        reqSend2.media = new TLRPC$TL_inputMediaEmpty();
                        reqSend2.random_id = random_ids;
                        performSendMessageRequest(reqSend2, newMsgObj, null);
                        delayedMessage2 = delayedMessage;
                    } else {
                        TLRPC$TL_messages_sendMessage reqSend3 = new TLRPC$TL_messages_sendMessage();
                        reqSend3.message = message;
                        reqSend3.clear_draft = retryMessageObject == null;
                        if (newMsg.to_id instanceof TLRPC$TL_peerChannel) {
                            reqSend3.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + peer, false);
                        }
                        reqSend3.peer = sendToPeer;
                        reqSend3.random_id = newMsg.random_id;
                        if (reply_to_msg != null) {
                            reqSend3.flags |= 1;
                            reqSend3.reply_to_msg_id = reply_to_msg.getId();
                        }
                        if (!searchLinks) {
                            reqSend3.no_webpage = true;
                        }
                        if (!(entities == null || entities.isEmpty())) {
                            reqSend3.entities = entities;
                            reqSend3.flags |= 8;
                        }
                        performSendMessageRequest(reqSend3, newMsgObj, null);
                        if (retryMessageObject == null) {
                            DraftQuery.cleanDraft(peer, false);
                        }
                        delayedMessage2 = delayedMessage;
                    }
                } else if ((type < 1 || type > 3) && ((type < 5 || type > 8) && (type != 9 || encryptedChat == null))) {
                    if (type == 4) {
                        TLRPC$TL_messages_forwardMessages reqSend4 = new TLRPC$TL_messages_forwardMessages();
                        reqSend4.to_peer = sendToPeer;
                        reqSend4.with_my_score = retryMessageObject.messageOwner.with_my_score;
                        if (retryMessageObject.messageOwner.ttl != 0) {
                            chat = MessagesController.getInstance().getChat(Integer.valueOf(-retryMessageObject.messageOwner.ttl));
                            reqSend4.from_peer = new TLRPC$TL_inputPeerChannel();
                            reqSend4.from_peer.channel_id = -retryMessageObject.messageOwner.ttl;
                            if (chat != null) {
                                reqSend4.from_peer.access_hash = chat.access_hash;
                            }
                        } else {
                            reqSend4.from_peer = new TLRPC$TL_inputPeerEmpty();
                        }
                        if (retryMessageObject.messageOwner.to_id instanceof TLRPC$TL_peerChannel) {
                            reqSend4.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + peer, false);
                        }
                        reqSend4.random_id.add(Long.valueOf(newMsg.random_id));
                        if (retryMessageObject.getId() >= 0) {
                            reqSend4.id.add(Integer.valueOf(retryMessageObject.getId()));
                        } else if (retryMessageObject.messageOwner.fwd_msg_id != 0) {
                            reqSend4.id.add(Integer.valueOf(retryMessageObject.messageOwner.fwd_msg_id));
                        } else if (retryMessageObject.messageOwner.fwd_from != null) {
                            reqSend4.id.add(Integer.valueOf(retryMessageObject.messageOwner.fwd_from.channel_post));
                        }
                        performSendMessageRequest(reqSend4, newMsgObj, null);
                        delayedMessage2 = delayedMessage;
                        return;
                    }
                    if (type == 9) {
                        reqSend = new TLRPC$TL_messages_sendInlineBotResult();
                        reqSend.peer = sendToPeer;
                        reqSend.random_id = newMsg.random_id;
                        if (reply_to_msg != null) {
                            reqSend.flags |= 1;
                            reqSend.reply_to_msg_id = reply_to_msg.getId();
                        }
                        if (newMsg.to_id instanceof TLRPC$TL_peerChannel) {
                            reqSend.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + peer, false);
                        }
                        reqSend.query_id = Utilities.parseLong((String) params.get("query_id")).longValue();
                        reqSend.id = (String) params.get("id");
                        if (retryMessageObject == null) {
                            reqSend.clear_draft = true;
                            DraftQuery.cleanDraft(peer, false);
                        }
                        performSendMessageRequest(reqSend, newMsgObj, null);
                    }
                    delayedMessage2 = delayedMessage;
                } else if (encryptedChat == null) {
                    TLObject reqSend5;
                    TLRPC$InputMedia inputMedia = null;
                    if (type == 1) {
                        if (location instanceof TLRPC$TL_messageMediaVenue) {
                            inputMedia = new TLRPC$TL_inputMediaVenue();
                            inputMedia.address = location.address;
                            inputMedia.title = location.title;
                            inputMedia.provider = location.provider;
                            inputMedia.venue_id = location.venue_id;
                            inputMedia.venue_type = "";
                        } else if (location instanceof TLRPC$TL_messageMediaGeoLive) {
                            inputMedia = new TLRPC$TL_inputMediaGeoLive();
                            inputMedia.period = location.period;
                        } else {
                            inputMedia = new TLRPC$TL_inputMediaGeoPoint();
                        }
                        inputMedia.geo_point = new TLRPC$TL_inputGeoPoint();
                        inputMedia.geo_point.lat = location.geo.lat;
                        inputMedia.geo_point._long = location.geo._long;
                        delayedMessage2 = delayedMessage;
                    } else if (type == 2 || (type == 9 && photo != null)) {
                        if (photo.access_hash == 0) {
                            inputMedia = new TLRPC$TL_inputMediaUploadedPhoto();
                            inputMedia.caption = photo.caption != null ? photo.caption : "";
                            if (ttl != 0) {
                                inputMedia.ttl_seconds = ttl;
                                newMsg.ttl = ttl;
                                inputMedia.flags |= 2;
                            }
                            if (params != null) {
                                String masks = (String) params.get("masks");
                                if (masks != null) {
                                    AbstractSerializedData serializedData = new SerializedData(Utilities.hexToBytes(masks));
                                    int count = serializedData.readInt32(false);
                                    for (a = 0; a < count; a++) {
                                        inputMedia.stickers.add(TLRPC$InputDocument.TLdeserialize(serializedData, serializedData.readInt32(false), false));
                                    }
                                    inputMedia.flags |= 1;
                                }
                            }
                            if (delayedMessage == null) {
                                sendMessagesHelper$DelayedMessage = new SendMessagesHelper$DelayedMessage(this, peer);
                                sendMessagesHelper$DelayedMessage.type = 0;
                                sendMessagesHelper$DelayedMessage.obj = newMsgObj;
                                sendMessagesHelper$DelayedMessage.originalPath = originalPath;
                            } else {
                                delayedMessage2 = delayedMessage;
                            }
                            if (path != null && path.length() > 0) {
                                if (path.startsWith("http")) {
                                    delayedMessage2.httpLocation = path;
                                }
                            }
                            delayedMessage2.location = ((TLRPC$PhotoSize) photo.sizes.get(photo.sizes.size() - 1)).location;
                        } else {
                            media = new TLRPC$TL_inputMediaPhoto();
                            media.id = new TLRPC$TL_inputPhoto();
                            media.caption = photo.caption != null ? photo.caption : "";
                            media.id.id = photo.id;
                            media.id.access_hash = photo.access_hash;
                            inputMedia = media;
                            delayedMessage2 = delayedMessage;
                        }
                    } else if (type == 3) {
                        if (document.access_hash == 0) {
                            inputMedia = new TLRPC$TL_inputMediaUploadedDocument();
                            inputMedia.caption = document.caption != null ? document.caption : "";
                            inputMedia.mime_type = document.mime_type;
                            inputMedia.attributes = document.attributes;
                            if (!MessageObject.isRoundVideoDocument(document) && (videoEditedInfo == null || !(videoEditedInfo.muted || videoEditedInfo.roundVideo))) {
                                inputMedia.nosound_video = true;
                            }
                            if (ttl != 0) {
                                inputMedia.ttl_seconds = ttl;
                                newMsg.ttl = ttl;
                                inputMedia.flags |= 2;
                            }
                            if (delayedMessage == null) {
                                sendMessagesHelper$DelayedMessage = new SendMessagesHelper$DelayedMessage(this, peer);
                                sendMessagesHelper$DelayedMessage.type = 1;
                                sendMessagesHelper$DelayedMessage.obj = newMsgObj;
                                sendMessagesHelper$DelayedMessage.originalPath = originalPath;
                            } else {
                                delayedMessage2 = delayedMessage;
                            }
                            delayedMessage2.location = document.thumb.location;
                            delayedMessage2.videoEditedInfo = videoEditedInfo;
                        } else {
                            media = new TLRPC$TL_inputMediaDocument();
                            media.id = new TLRPC$TL_inputDocument();
                            media.caption = document.caption != null ? document.caption : "";
                            media.id.id = document.id;
                            media.id.access_hash = document.access_hash;
                            inputMedia = media;
                            delayedMessage2 = delayedMessage;
                        }
                    } else if (type == 6) {
                        inputMedia = new TLRPC$TL_inputMediaContact();
                        inputMedia.phone_number = user.phone;
                        inputMedia.first_name = user.first_name;
                        inputMedia.last_name = user.last_name;
                        delayedMessage2 = delayedMessage;
                    } else if (type == 7 || type == 9) {
                        if (document.access_hash == 0) {
                            String str2;
                            if (encryptedChat == null && originalPath != null && originalPath.length() > 0) {
                                if (originalPath.startsWith("http") && params != null) {
                                    inputMedia = new TLRPC$TL_inputMediaGifExternal();
                                    String[] args = ((String) params.get("url")).split("\\|");
                                    if (args.length == 2) {
                                        ((TLRPC$TL_inputMediaGifExternal) inputMedia).url = args[0];
                                        inputMedia.f74q = args[1];
                                    }
                                    delayedMessage2 = delayedMessage;
                                    inputMedia.mime_type = document.mime_type;
                                    inputMedia.attributes = document.attributes;
                                    if (document.caption == null) {
                                        str2 = document.caption;
                                    } else {
                                        str2 = "";
                                    }
                                    inputMedia.caption = str2;
                                }
                            }
                            inputMedia = new TLRPC$TL_inputMediaUploadedDocument();
                            if (ttl != 0) {
                                inputMedia.ttl_seconds = ttl;
                                newMsg.ttl = ttl;
                                inputMedia.flags |= 2;
                            }
                            sendMessagesHelper$DelayedMessage = new SendMessagesHelper$DelayedMessage(this, peer);
                            sendMessagesHelper$DelayedMessage.originalPath = originalPath;
                            sendMessagesHelper$DelayedMessage.type = 2;
                            sendMessagesHelper$DelayedMessage.obj = newMsgObj;
                            sendMessagesHelper$DelayedMessage.location = document.thumb.location;
                            inputMedia.mime_type = document.mime_type;
                            inputMedia.attributes = document.attributes;
                            if (document.caption == null) {
                                str2 = "";
                            } else {
                                str2 = document.caption;
                            }
                            inputMedia.caption = str2;
                        } else {
                            media = new TLRPC$TL_inputMediaDocument();
                            media.id = new TLRPC$TL_inputDocument();
                            media.id.id = document.id;
                            media.id.access_hash = document.access_hash;
                            media.caption = document.caption != null ? document.caption : "";
                            inputMedia = media;
                            delayedMessage2 = delayedMessage;
                        }
                    } else if (type != 8) {
                        delayedMessage2 = delayedMessage;
                    } else if (document.access_hash == 0) {
                        inputMedia = new TLRPC$TL_inputMediaUploadedDocument();
                        inputMedia.mime_type = document.mime_type;
                        inputMedia.attributes = document.attributes;
                        inputMedia.caption = document.caption != null ? document.caption : "";
                        if (ttl != 0) {
                            inputMedia.ttl_seconds = ttl;
                            newMsg.ttl = ttl;
                            inputMedia.flags |= 2;
                        }
                        sendMessagesHelper$DelayedMessage = new SendMessagesHelper$DelayedMessage(this, peer);
                        sendMessagesHelper$DelayedMessage.type = 3;
                        sendMessagesHelper$DelayedMessage.obj = newMsgObj;
                    } else {
                        media = new TLRPC$TL_inputMediaDocument();
                        media.id = new TLRPC$TL_inputDocument();
                        media.caption = document.caption != null ? document.caption : "";
                        media.id.id = document.id;
                        media.id.access_hash = document.access_hash;
                        inputMedia = media;
                        delayedMessage2 = delayedMessage;
                    }
                    if (sendToPeers != null) {
                        request = new TLRPC$TL_messages_sendBroadcast();
                        random_ids = new ArrayList();
                        for (a = 0; a < sendToPeers.size(); a++) {
                            random_ids.add(Long.valueOf(Utilities.random.nextLong()));
                        }
                        request.contacts = sendToPeers;
                        request.media = inputMedia;
                        request.random_id = random_ids;
                        request.message = "";
                        if (delayedMessage2 != null) {
                            delayedMessage2.sendRequest = request;
                        }
                        reqSend5 = request;
                        if (retryMessageObject == null) {
                            DraftQuery.cleanDraft(peer, false);
                        }
                    } else if (groupId != 0) {
                        if (delayedMessage2.sendRequest != null) {
                            request = (TLRPC$TL_messages_sendMultiMedia) delayedMessage2.sendRequest;
                        } else {
                            request = new TLRPC$TL_messages_sendMultiMedia();
                            request.peer = sendToPeer;
                            if (newMsg.to_id instanceof TLRPC$TL_peerChannel) {
                                request.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + peer, false);
                            }
                            if (reply_to_msg != null) {
                                request.flags |= 1;
                                request.reply_to_msg_id = reply_to_msg.getId();
                            }
                            delayedMessage2.sendRequest = request;
                        }
                        delayedMessage2.messageObjects.add(newMsgObj);
                        delayedMessage2.messages.add(newMsg);
                        delayedMessage2.originalPaths.add(originalPath);
                        TLRPC$TL_inputSingleMedia inputSingleMedia = new TLRPC$TL_inputSingleMedia();
                        inputSingleMedia.random_id = newMsg.random_id;
                        inputSingleMedia.media = inputMedia;
                        request.multi_media.add(inputSingleMedia);
                        reqSend5 = request;
                    } else {
                        request = new TLRPC$TL_messages_sendMedia();
                        request.peer = sendToPeer;
                        if (newMsg.to_id instanceof TLRPC$TL_peerChannel) {
                            request.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + peer, false);
                        }
                        if (reply_to_msg != null) {
                            request.flags |= 1;
                            request.reply_to_msg_id = reply_to_msg.getId();
                        }
                        request.random_id = newMsg.random_id;
                        request.media = inputMedia;
                        if (delayedMessage2 != null) {
                            delayedMessage2.sendRequest = request;
                        }
                        reqSend5 = request;
                    }
                    if (groupId != 0) {
                        performSendDelayedMessage(delayedMessage2);
                    } else if (type == 1) {
                        performSendMessageRequest(reqSend5, newMsgObj, null);
                    } else if (type == 2) {
                        if (photo.access_hash == 0) {
                            performSendDelayedMessage(delayedMessage2);
                        } else {
                            performSendMessageRequest(reqSend5, newMsgObj, null, null, true);
                        }
                    } else if (type == 3) {
                        if (document.access_hash == 0) {
                            performSendDelayedMessage(delayedMessage2);
                        } else {
                            performSendMessageRequest(reqSend5, newMsgObj, null);
                        }
                    } else if (type == 6) {
                        performSendMessageRequest(reqSend5, newMsgObj, null);
                    } else if (type == 7) {
                        if (document.access_hash != 0 || delayedMessage2 == null) {
                            performSendMessageRequest(reqSend5, newMsgObj, originalPath);
                        } else {
                            performSendDelayedMessage(delayedMessage2);
                        }
                    } else if (type != 8) {
                    } else {
                        if (document.access_hash == 0) {
                            performSendDelayedMessage(delayedMessage2);
                        } else {
                            performSendMessageRequest(reqSend5, newMsgObj, null);
                        }
                    }
                } else {
                    TLRPC$DecryptedMessage reqSend6;
                    TLRPC$TL_inputEncryptedFile encryptedFile;
                    if (AndroidUtilities.getPeerLayerVersion(encryptedChat.layer) >= 73) {
                        reqSend6 = new TLRPC$TL_decryptedMessage();
                        if (groupId != 0) {
                            reqSend6.grouped_id = groupId;
                            reqSend6.flags |= 131072;
                        }
                    } else {
                        reqSend6 = new TLRPC$TL_decryptedMessage_layer45();
                    }
                    reqSend6.ttl = newMsg.ttl;
                    if (!(entities == null || entities.isEmpty())) {
                        reqSend6.entities = entities;
                        reqSend6.flags |= 128;
                    }
                    if (!(reply_to_msg == null || reply_to_msg.messageOwner.random_id == 0)) {
                        reqSend6.reply_to_random_id = reply_to_msg.messageOwner.random_id;
                        reqSend6.flags |= 8;
                    }
                    reqSend6.flags |= 512;
                    if (params != null) {
                        if (params.get("bot_name") != null) {
                            reqSend6.via_bot_name = (String) params.get("bot_name");
                            reqSend6.flags |= 2048;
                        }
                    }
                    reqSend6.random_id = newMsg.random_id;
                    reqSend6.message = "";
                    if (type == 1) {
                        if (location instanceof TLRPC$TL_messageMediaVenue) {
                            reqSend6.media = new TLRPC$TL_decryptedMessageMediaVenue();
                            reqSend6.media.address = location.address;
                            reqSend6.media.title = location.title;
                            reqSend6.media.provider = location.provider;
                            reqSend6.media.venue_id = location.venue_id;
                        } else {
                            reqSend6.media = new TLRPC$TL_decryptedMessageMediaGeoPoint();
                        }
                        reqSend6.media.lat = location.geo.lat;
                        reqSend6.media._long = location.geo._long;
                        SecretChatHelper.getInstance().performSendEncryptedRequest(reqSend6, newMsgObj.messageOwner, encryptedChat, null, null, newMsgObj);
                        delayedMessage2 = delayedMessage;
                    } else {
                        if (type == 2 || (type == 9 && photo != null)) {
                            TLRPC$PhotoSize small = (TLRPC$PhotoSize) photo.sizes.get(0);
                            TLRPC$PhotoSize big = (TLRPC$PhotoSize) photo.sizes.get(photo.sizes.size() - 1);
                            ImageLoader.fillPhotoSizeWithBytes(small);
                            reqSend6.media = new TLRPC$TL_decryptedMessageMediaPhoto();
                            reqSend6.media.caption = photo.caption != null ? photo.caption : "";
                            if (small.bytes != null) {
                                ((TLRPC$TL_decryptedMessageMediaPhoto) reqSend6.media).thumb = small.bytes;
                            } else {
                                ((TLRPC$TL_decryptedMessageMediaPhoto) reqSend6.media).thumb = new byte[0];
                            }
                            reqSend6.media.thumb_h = small.f77h;
                            reqSend6.media.thumb_w = small.f78w;
                            reqSend6.media.f71w = big.f78w;
                            reqSend6.media.f70h = big.f77h;
                            reqSend6.media.size = big.size;
                            if (big.location.key == null || groupId != 0) {
                                if (delayedMessage == null) {
                                    sendMessagesHelper$DelayedMessage = new SendMessagesHelper$DelayedMessage(this, peer);
                                    sendMessagesHelper$DelayedMessage.encryptedChat = encryptedChat;
                                    sendMessagesHelper$DelayedMessage.type = 0;
                                    sendMessagesHelper$DelayedMessage.originalPath = originalPath;
                                    sendMessagesHelper$DelayedMessage.sendEncryptedRequest = reqSend6;
                                    sendMessagesHelper$DelayedMessage.obj = newMsgObj;
                                } else {
                                    delayedMessage2 = delayedMessage;
                                }
                                if (!TextUtils.isEmpty(path)) {
                                    if (path.startsWith("http")) {
                                        delayedMessage2.httpLocation = path;
                                        if (groupId == 0) {
                                            performSendDelayedMessage(delayedMessage2);
                                        }
                                    }
                                }
                                delayedMessage2.location = ((TLRPC$PhotoSize) photo.sizes.get(photo.sizes.size() - 1)).location;
                                if (groupId == 0) {
                                    performSendDelayedMessage(delayedMessage2);
                                }
                            } else {
                                encryptedFile = new TLRPC$TL_inputEncryptedFile();
                                encryptedFile.id = big.location.volume_id;
                                encryptedFile.access_hash = big.location.secret;
                                reqSend6.media.key = big.location.key;
                                reqSend6.media.iv = big.location.iv;
                                SecretChatHelper.getInstance().performSendEncryptedRequest(reqSend6, newMsgObj.messageOwner, encryptedChat, encryptedFile, null, newMsgObj);
                            }
                        } else if (type == 3) {
                            ImageLoader.fillPhotoSizeWithBytes(document.thumb);
                            if (MessageObject.isNewGifDocument(document) || MessageObject.isRoundVideoDocument(document)) {
                                reqSend6.media = new TLRPC$TL_decryptedMessageMediaDocument();
                                reqSend6.media.attributes = document.attributes;
                                if (document.thumb == null || document.thumb.bytes == null) {
                                    ((TLRPC$TL_decryptedMessageMediaDocument) reqSend6.media).thumb = new byte[0];
                                } else {
                                    ((TLRPC$TL_decryptedMessageMediaDocument) reqSend6.media).thumb = document.thumb.bytes;
                                }
                            } else {
                                reqSend6.media = new TLRPC$TL_decryptedMessageMediaVideo();
                                if (document.thumb == null || document.thumb.bytes == null) {
                                    ((TLRPC$TL_decryptedMessageMediaVideo) reqSend6.media).thumb = new byte[0];
                                } else {
                                    ((TLRPC$TL_decryptedMessageMediaVideo) reqSend6.media).thumb = document.thumb.bytes;
                                }
                            }
                            reqSend6.media.caption = document.caption != null ? document.caption : "";
                            reqSend6.media.mime_type = MimeTypes.VIDEO_MP4;
                            reqSend6.media.size = document.size;
                            for (a = 0; a < document.attributes.size(); a++) {
                                attribute = (DocumentAttribute) document.attributes.get(a);
                                if (attribute instanceof TLRPC$TL_documentAttributeVideo) {
                                    reqSend6.media.f71w = attribute.f44w;
                                    reqSend6.media.f70h = attribute.f43h;
                                    reqSend6.media.duration = attribute.duration;
                                    break;
                                }
                            }
                            reqSend6.media.thumb_h = document.thumb.f77h;
                            reqSend6.media.thumb_w = document.thumb.f78w;
                            if (document.key == null || groupId != 0) {
                                if (delayedMessage == null) {
                                    sendMessagesHelper$DelayedMessage = new SendMessagesHelper$DelayedMessage(this, peer);
                                    sendMessagesHelper$DelayedMessage.encryptedChat = encryptedChat;
                                    sendMessagesHelper$DelayedMessage.type = 1;
                                    sendMessagesHelper$DelayedMessage.sendEncryptedRequest = reqSend6;
                                    sendMessagesHelper$DelayedMessage.originalPath = originalPath;
                                    sendMessagesHelper$DelayedMessage.obj = newMsgObj;
                                } else {
                                    delayedMessage2 = delayedMessage;
                                }
                                delayedMessage2.videoEditedInfo = videoEditedInfo;
                                if (groupId == 0) {
                                    performSendDelayedMessage(delayedMessage2);
                                }
                            } else {
                                encryptedFile = new TLRPC$TL_inputEncryptedFile();
                                encryptedFile.id = document.id;
                                encryptedFile.access_hash = document.access_hash;
                                reqSend6.media.key = document.key;
                                reqSend6.media.iv = document.iv;
                                SecretChatHelper.getInstance().performSendEncryptedRequest(reqSend6, newMsgObj.messageOwner, encryptedChat, encryptedFile, null, newMsgObj);
                                delayedMessage2 = delayedMessage;
                            }
                        } else if (type == 6) {
                            reqSend6.media = new TLRPC$TL_decryptedMessageMediaContact();
                            reqSend6.media.phone_number = user.phone;
                            reqSend6.media.first_name = user.first_name;
                            reqSend6.media.last_name = user.last_name;
                            reqSend6.media.user_id = user.id;
                            SecretChatHelper.getInstance().performSendEncryptedRequest(reqSend6, newMsgObj.messageOwner, encryptedChat, null, null, newMsgObj);
                            delayedMessage2 = delayedMessage;
                        } else if (type == 7 || (type == 9 && document != null)) {
                            if (MessageObject.isStickerDocument(document)) {
                                reqSend6.media = new TLRPC$TL_decryptedMessageMediaExternalDocument();
                                reqSend6.media.id = document.id;
                                reqSend6.media.date = document.date;
                                reqSend6.media.access_hash = document.access_hash;
                                reqSend6.media.mime_type = document.mime_type;
                                reqSend6.media.size = document.size;
                                reqSend6.media.dc_id = document.dc_id;
                                reqSend6.media.attributes = document.attributes;
                                if (document.thumb == null) {
                                    ((TLRPC$TL_decryptedMessageMediaExternalDocument) reqSend6.media).thumb = new TLRPC$TL_photoSizeEmpty();
                                    ((TLRPC$TL_decryptedMessageMediaExternalDocument) reqSend6.media).thumb.type = "s";
                                } else {
                                    ((TLRPC$TL_decryptedMessageMediaExternalDocument) reqSend6.media).thumb = document.thumb;
                                }
                                SecretChatHelper.getInstance().performSendEncryptedRequest(reqSend6, newMsgObj.messageOwner, encryptedChat, null, null, newMsgObj);
                                delayedMessage2 = delayedMessage;
                            } else {
                                ImageLoader.fillPhotoSizeWithBytes(document.thumb);
                                reqSend6.media = new TLRPC$TL_decryptedMessageMediaDocument();
                                reqSend6.media.attributes = document.attributes;
                                reqSend6.media.caption = document.caption != null ? document.caption : "";
                                if (document.thumb == null || document.thumb.bytes == null) {
                                    ((TLRPC$TL_decryptedMessageMediaDocument) reqSend6.media).thumb = new byte[0];
                                    reqSend6.media.thumb_h = 0;
                                    reqSend6.media.thumb_w = 0;
                                } else {
                                    ((TLRPC$TL_decryptedMessageMediaDocument) reqSend6.media).thumb = document.thumb.bytes;
                                    reqSend6.media.thumb_h = document.thumb.f77h;
                                    reqSend6.media.thumb_w = document.thumb.f78w;
                                }
                                reqSend6.media.size = document.size;
                                reqSend6.media.mime_type = document.mime_type;
                                if (document.key == null) {
                                    sendMessagesHelper$DelayedMessage = new SendMessagesHelper$DelayedMessage(this, peer);
                                    sendMessagesHelper$DelayedMessage.originalPath = originalPath;
                                    sendMessagesHelper$DelayedMessage.sendEncryptedRequest = reqSend6;
                                    sendMessagesHelper$DelayedMessage.type = 2;
                                    sendMessagesHelper$DelayedMessage.obj = newMsgObj;
                                    sendMessagesHelper$DelayedMessage.encryptedChat = encryptedChat;
                                    if (path != null && path.length() > 0) {
                                        if (path.startsWith("http")) {
                                            sendMessagesHelper$DelayedMessage.httpLocation = path;
                                        }
                                    }
                                    performSendDelayedMessage(sendMessagesHelper$DelayedMessage);
                                } else {
                                    encryptedFile = new TLRPC$TL_inputEncryptedFile();
                                    encryptedFile.id = document.id;
                                    encryptedFile.access_hash = document.access_hash;
                                    reqSend6.media.key = document.key;
                                    reqSend6.media.iv = document.iv;
                                    SecretChatHelper.getInstance().performSendEncryptedRequest(reqSend6, newMsgObj.messageOwner, encryptedChat, encryptedFile, null, newMsgObj);
                                    delayedMessage2 = delayedMessage;
                                }
                            }
                        } else if (type == 8) {
                            sendMessagesHelper$DelayedMessage = new SendMessagesHelper$DelayedMessage(this, peer);
                            sendMessagesHelper$DelayedMessage.encryptedChat = encryptedChat;
                            sendMessagesHelper$DelayedMessage.sendEncryptedRequest = reqSend6;
                            sendMessagesHelper$DelayedMessage.obj = newMsgObj;
                            sendMessagesHelper$DelayedMessage.type = 3;
                            reqSend6.media = new TLRPC$TL_decryptedMessageMediaDocument();
                            reqSend6.media.attributes = document.attributes;
                            reqSend6.media.caption = document.caption != null ? document.caption : "";
                            if (document.thumb == null || document.thumb.bytes == null) {
                                ((TLRPC$TL_decryptedMessageMediaDocument) reqSend6.media).thumb = new byte[0];
                                reqSend6.media.thumb_h = 0;
                                reqSend6.media.thumb_w = 0;
                            } else {
                                ((TLRPC$TL_decryptedMessageMediaDocument) reqSend6.media).thumb = document.thumb.bytes;
                                reqSend6.media.thumb_h = document.thumb.f77h;
                                reqSend6.media.thumb_w = document.thumb.f78w;
                            }
                            reqSend6.media.mime_type = document.mime_type;
                            reqSend6.media.size = document.size;
                            sendMessagesHelper$DelayedMessage.originalPath = originalPath;
                            performSendDelayedMessage(sendMessagesHelper$DelayedMessage);
                        }
                        delayedMessage2 = delayedMessage;
                    }
                    if (groupId != 0) {
                        TLRPC$TL_messages_sendEncryptedMultiMedia request;
                        if (delayedMessage2.sendEncryptedRequest != null) {
                            request = (TLRPC$TL_messages_sendEncryptedMultiMedia) delayedMessage2.sendEncryptedRequest;
                        } else {
                            request = new TLRPC$TL_messages_sendEncryptedMultiMedia();
                            delayedMessage2.sendEncryptedRequest = request;
                        }
                        delayedMessage2.messageObjects.add(newMsgObj);
                        delayedMessage2.messages.add(newMsg);
                        delayedMessage2.originalPaths.add(originalPath);
                        delayedMessage2.upload = true;
                        request.messages.add(reqSend6);
                        encryptedFile = new TLRPC$TL_inputEncryptedFile();
                        encryptedFile.id = type == 3 ? 1 : 0;
                        request.files.add(encryptedFile);
                        performSendDelayedMessage(delayedMessage2);
                    }
                    if (retryMessageObject == null) {
                        DraftQuery.cleanDraft(peer, false);
                    }
                }
            } catch (Exception e7) {
                e = e7;
                delayedMessage2 = delayedMessage;
                FileLog.m94e(e);
                MessagesStorage.getInstance().markMessageAsSendError(newMsg);
                if (newMsgObj != null) {
                    newMsgObj.messageOwner.send_state = 2;
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(newMsg.id));
                processSentMessage(newMsg.id);
            }
        }
    }

    private void performSendDelayedMessage(SendMessagesHelper$DelayedMessage message) {
        performSendDelayedMessage(message, -1);
    }

    private void performSendDelayedMessage(SendMessagesHelper$DelayedMessage message, int index) {
        String location;
        if (message.type == 0) {
            if (message.httpLocation != null) {
                putToDelayedMessages(message.httpLocation, message);
                ImageLoader.getInstance().loadHttpFile(message.httpLocation, "file");
            } else if (message.sendRequest != null) {
                location = FileLoader.getPathToAttach(message.location).toString();
                putToDelayedMessages(location, message);
                FileLoader.getInstance().uploadFile(location, false, true, 16777216);
            } else {
                location = FileLoader.getPathToAttach(message.location).toString();
                if (!(message.sendEncryptedRequest == null || message.location.dc_id == 0)) {
                    File file = new File(location);
                    if (!file.exists()) {
                        location = FileLoader.getPathToAttach(message.location, true).toString();
                        file = new File(location);
                    }
                    if (!file.exists()) {
                        putToDelayedMessages(FileLoader.getAttachFileName(message.location), message);
                        FileLoader.getInstance().loadFile(message.location, "jpg", 0, 0);
                        return;
                    }
                }
                putToDelayedMessages(location, message);
                FileLoader.getInstance().uploadFile(location, true, true, 16777216);
            }
        } else if (message.type == 1) {
            if (message.videoEditedInfo == null || !message.videoEditedInfo.needConvert()) {
                if (message.videoEditedInfo != null) {
                    if (message.videoEditedInfo.file != null) {
                        if (message.sendRequest instanceof TLRPC$TL_messages_sendMedia) {
                            media = ((TLRPC$TL_messages_sendMedia) message.sendRequest).media;
                        } else {
                            media = ((TLRPC$TL_messages_sendBroadcast) message.sendRequest).media;
                        }
                        media.file = message.videoEditedInfo.file;
                        message.videoEditedInfo.file = null;
                    } else if (message.videoEditedInfo.encryptedFile != null) {
                        TLRPC$TL_decryptedMessage decryptedMessage = message.sendEncryptedRequest;
                        decryptedMessage.media.size = (int) message.videoEditedInfo.estimatedSize;
                        decryptedMessage.media.key = message.videoEditedInfo.key;
                        decryptedMessage.media.iv = message.videoEditedInfo.iv;
                        SecretChatHelper.getInstance().performSendEncryptedRequest(decryptedMessage, message.obj.messageOwner, message.encryptedChat, message.videoEditedInfo.encryptedFile, message.originalPath, message.obj);
                        message.videoEditedInfo.encryptedFile = null;
                        return;
                    }
                }
                if (message.sendRequest != null) {
                    if (message.sendRequest instanceof TLRPC$TL_messages_sendMedia) {
                        media = ((TLRPC$TL_messages_sendMedia) message.sendRequest).media;
                    } else {
                        media = ((TLRPC$TL_messages_sendBroadcast) message.sendRequest).media;
                    }
                    if (media.file == null) {
                        location = message.obj.messageOwner.attachPath;
                        document = message.obj.getDocument();
                        if (location == null) {
                            location = FileLoader.getInstance().getDirectory(4) + "/" + document.id + ".mp4";
                        }
                        putToDelayedMessages(location, message);
                        if (message.obj.videoEditedInfo == null || !message.obj.videoEditedInfo.needConvert()) {
                            FileLoader.getInstance().uploadFile(location, false, false, ConnectionsManager.FileTypeVideo);
                            return;
                        } else {
                            FileLoader.getInstance().uploadFile(location, false, false, document.size, ConnectionsManager.FileTypeVideo);
                            return;
                        }
                    }
                    location = FileLoader.getInstance().getDirectory(4) + "/" + message.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + message.location.local_id + ".jpg";
                    putToDelayedMessages(location, message);
                    FileLoader.getInstance().uploadFile(location, false, true, 16777216);
                    return;
                }
                location = message.obj.messageOwner.attachPath;
                document = message.obj.getDocument();
                if (location == null) {
                    location = FileLoader.getInstance().getDirectory(4) + "/" + document.id + ".mp4";
                }
                if (message.sendEncryptedRequest == null || document.dc_id == 0 || new File(location).exists()) {
                    putToDelayedMessages(location, message);
                    if (message.obj.videoEditedInfo == null || !message.obj.videoEditedInfo.needConvert()) {
                        FileLoader.getInstance().uploadFile(location, true, false, ConnectionsManager.FileTypeVideo);
                        return;
                    } else {
                        FileLoader.getInstance().uploadFile(location, true, false, document.size, ConnectionsManager.FileTypeVideo);
                        return;
                    }
                }
                putToDelayedMessages(FileLoader.getAttachFileName(document), message);
                FileLoader.getInstance().loadFile(document, true, 0);
                return;
            }
            location = message.obj.messageOwner.attachPath;
            document = message.obj.getDocument();
            if (location == null) {
                location = FileLoader.getInstance().getDirectory(4) + "/" + document.id + ".mp4";
            }
            putToDelayedMessages(location, message);
            MediaController.getInstance().scheduleVideoConvert(message.obj);
        } else if (message.type == 2) {
            if (message.httpLocation != null) {
                putToDelayedMessages(message.httpLocation, message);
                ImageLoader.getInstance().loadHttpFile(message.httpLocation, "gif");
            } else if (message.sendRequest != null) {
                if (message.sendRequest instanceof TLRPC$TL_messages_sendMedia) {
                    media = ((TLRPC$TL_messages_sendMedia) message.sendRequest).media;
                } else {
                    media = ((TLRPC$TL_messages_sendBroadcast) message.sendRequest).media;
                }
                if (media.file == null) {
                    boolean z;
                    location = message.obj.messageOwner.attachPath;
                    putToDelayedMessages(location, message);
                    FileLoader instance = FileLoader.getInstance();
                    if (message.sendRequest == null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    instance.uploadFile(location, z, false, ConnectionsManager.FileTypeFile);
                } else if (media.thumb == null && message.location != null) {
                    location = FileLoader.getInstance().getDirectory(4) + "/" + message.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + message.location.local_id + ".jpg";
                    putToDelayedMessages(location, message);
                    FileLoader.getInstance().uploadFile(location, false, true, 16777216);
                }
            } else {
                location = message.obj.messageOwner.attachPath;
                document = message.obj.getDocument();
                if (message.sendEncryptedRequest == null || document.dc_id == 0 || new File(location).exists()) {
                    putToDelayedMessages(location, message);
                    FileLoader.getInstance().uploadFile(location, true, false, ConnectionsManager.FileTypeFile);
                    return;
                }
                putToDelayedMessages(FileLoader.getAttachFileName(document), message);
                FileLoader.getInstance().loadFile(document, true, 0);
            }
        } else if (message.type == 3) {
            location = message.obj.messageOwner.attachPath;
            putToDelayedMessages(location, message);
            FileLoader.getInstance().uploadFile(location, message.sendRequest == null, true, ConnectionsManager.FileTypeAudio);
        } else if (message.type == 4) {
            boolean add = index < 0;
            if (message.location != null || message.httpLocation != null || message.upload || index >= 0) {
                if (index < 0) {
                    index = message.messageObjects.size() - 1;
                }
                MessageObject messageObject = (MessageObject) message.messageObjects.get(index);
                if (messageObject.getDocument() != null) {
                    if (message.videoEditedInfo != null) {
                        location = messageObject.messageOwner.attachPath;
                        document = messageObject.getDocument();
                        if (location == null) {
                            location = FileLoader.getInstance().getDirectory(4) + "/" + document.id + ".mp4";
                        }
                        putToDelayedMessages(location, message);
                        message.extraHashMap.put(messageObject, location);
                        message.extraHashMap.put(location + "_i", messageObject);
                        if (message.location != null) {
                            message.extraHashMap.put(location + "_t", message.location);
                        }
                        MediaController.getInstance().scheduleVideoConvert(messageObject);
                    } else {
                        document = messageObject.getDocument();
                        String documentLocation = messageObject.messageOwner.attachPath;
                        if (documentLocation == null) {
                            documentLocation = FileLoader.getInstance().getDirectory(4) + "/" + document.id + ".mp4";
                        }
                        if (message.sendRequest != null) {
                            media = ((TLRPC$TL_inputSingleMedia) ((TLRPC$TL_messages_sendMultiMedia) message.sendRequest).multi_media.get(index)).media;
                            if (media.file == null) {
                                putToDelayedMessages(documentLocation, message);
                                message.extraHashMap.put(messageObject, documentLocation);
                                message.extraHashMap.put(documentLocation, media);
                                message.extraHashMap.put(documentLocation + "_i", messageObject);
                                if (message.location != null) {
                                    message.extraHashMap.put(documentLocation + "_t", message.location);
                                }
                                if (messageObject.videoEditedInfo == null || !messageObject.videoEditedInfo.needConvert()) {
                                    FileLoader.getInstance().uploadFile(documentLocation, false, false, ConnectionsManager.FileTypeVideo);
                                } else {
                                    FileLoader.getInstance().uploadFile(documentLocation, false, false, document.size, ConnectionsManager.FileTypeVideo);
                                }
                            } else {
                                location = FileLoader.getInstance().getDirectory(4) + "/" + message.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + message.location.local_id + ".jpg";
                                putToDelayedMessages(location, message);
                                message.extraHashMap.put(location + "_o", documentLocation);
                                message.extraHashMap.put(messageObject, location);
                                message.extraHashMap.put(location, media);
                                FileLoader.getInstance().uploadFile(location, false, true, 16777216);
                            }
                        } else {
                            TLRPC$TL_messages_sendEncryptedMultiMedia request = (TLRPC$TL_messages_sendEncryptedMultiMedia) message.sendEncryptedRequest;
                            putToDelayedMessages(documentLocation, message);
                            message.extraHashMap.put(messageObject, documentLocation);
                            message.extraHashMap.put(documentLocation, request.files.get(index));
                            message.extraHashMap.put(documentLocation + "_i", messageObject);
                            if (message.location != null) {
                                message.extraHashMap.put(documentLocation + "_t", message.location);
                            }
                            if (messageObject.videoEditedInfo == null || !messageObject.videoEditedInfo.needConvert()) {
                                FileLoader.getInstance().uploadFile(documentLocation, true, false, ConnectionsManager.FileTypeVideo);
                            } else {
                                FileLoader.getInstance().uploadFile(documentLocation, true, false, document.size, ConnectionsManager.FileTypeVideo);
                            }
                        }
                    }
                    message.videoEditedInfo = null;
                    message.location = null;
                } else if (message.httpLocation != null) {
                    putToDelayedMessages(message.httpLocation, message);
                    message.extraHashMap.put(messageObject, message.httpLocation);
                    message.extraHashMap.put(message.httpLocation, messageObject);
                    ImageLoader.getInstance().loadHttpFile(message.httpLocation, "file");
                    message.httpLocation = null;
                } else {
                    TLObject inputMedia;
                    if (message.sendRequest != null) {
                        inputMedia = ((TLRPC$TL_inputSingleMedia) ((TLRPC$TL_messages_sendMultiMedia) message.sendRequest).multi_media.get(index)).media;
                    } else {
                        inputMedia = (TLObject) ((TLRPC$TL_messages_sendEncryptedMultiMedia) message.sendEncryptedRequest).files.get(index);
                    }
                    location = FileLoader.getInstance().getDirectory(4) + "/" + message.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + message.location.local_id + ".jpg";
                    putToDelayedMessages(location, message);
                    message.extraHashMap.put(location, inputMedia);
                    message.extraHashMap.put(messageObject, location);
                    FileLoader.getInstance().uploadFile(location, message.sendEncryptedRequest != null, true, 16777216);
                    message.location = null;
                }
                message.upload = false;
            } else if (!message.messageObjects.isEmpty()) {
                putToSendingMessages(((MessageObject) message.messageObjects.get(message.messageObjects.size() - 1)).messageOwner);
            }
            sendReadyToSendGroup(message, add, true);
        }
    }

    private void uploadMultiMedia(SendMessagesHelper$DelayedMessage message, TLRPC$InputMedia inputMedia, TLRPC$InputEncryptedFile inputEncryptedFile, String key) {
        int a;
        if (inputMedia != null) {
            TLRPC$TL_messages_sendMultiMedia multiMedia = message.sendRequest;
            for (a = 0; a < multiMedia.multi_media.size(); a++) {
                if (((TLRPC$TL_inputSingleMedia) multiMedia.multi_media.get(a)).media == inputMedia) {
                    putToSendingMessages((TLRPC$Message) message.messages.get(a));
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileUploadProgressChanged, key, Float.valueOf(1.0f), Boolean.valueOf(false));
                    break;
                }
            }
            TLRPC$TL_messages_uploadMedia req = new TLRPC$TL_messages_uploadMedia();
            req.media = inputMedia;
            req.peer = ((TLRPC$TL_messages_sendMultiMedia) message.sendRequest).peer;
            ConnectionsManager.getInstance().sendRequest(req, new SendMessagesHelper$10(this, inputMedia, message));
        } else if (inputEncryptedFile != null) {
            TLRPC$TL_messages_sendEncryptedMultiMedia multiMedia2 = message.sendEncryptedRequest;
            for (a = 0; a < multiMedia2.files.size(); a++) {
                if (multiMedia2.files.get(a) == inputEncryptedFile) {
                    putToSendingMessages((TLRPC$Message) message.messages.get(a));
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileUploadProgressChanged, key, Float.valueOf(1.0f), Boolean.valueOf(false));
                    break;
                }
            }
            sendReadyToSendGroup(message, false, true);
        }
    }

    private void sendReadyToSendGroup(SendMessagesHelper$DelayedMessage message, boolean add, boolean check) {
        if (message.messageObjects.isEmpty()) {
            message.markAsError();
            return;
        }
        String key = "group_" + message.groupId;
        if (message.finalGroupMessage == ((MessageObject) message.messageObjects.get(message.messageObjects.size() - 1)).getId()) {
            if (add) {
                this.delayedMessages.remove(key);
                MessagesStorage.getInstance().putMessages(message.messages, false, true, false, 0);
                MessagesController.getInstance().updateInterfaceWithMessages(message.peer, message.messageObjects);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            }
            int a;
            if (message.sendRequest instanceof TLRPC$TL_messages_sendMultiMedia) {
                TLRPC$TL_messages_sendMultiMedia request = message.sendRequest;
                a = 0;
                while (a < request.multi_media.size()) {
                    TLRPC$InputMedia inputMedia = ((TLRPC$TL_inputSingleMedia) request.multi_media.get(a)).media;
                    if (!(inputMedia instanceof TLRPC$TL_inputMediaUploadedPhoto) && !(inputMedia instanceof TLRPC$TL_inputMediaUploadedDocument)) {
                        a++;
                    } else {
                        return;
                    }
                }
                if (check) {
                    SendMessagesHelper$DelayedMessage maxDelayedMessage = findMaxDelayedMessageForMessageId(message.finalGroupMessage, message.peer);
                    if (maxDelayedMessage != null) {
                        maxDelayedMessage.addDelayedRequest(message.sendRequest, message.messageObjects, message.originalPaths);
                        if (message.requests != null) {
                            maxDelayedMessage.requests.addAll(message.requests);
                            return;
                        }
                        return;
                    }
                }
            }
            TLRPC$TL_messages_sendEncryptedMultiMedia request2 = message.sendEncryptedRequest;
            a = 0;
            while (a < request2.files.size()) {
                if (!(((TLRPC$InputEncryptedFile) request2.files.get(a)) instanceof TLRPC$TL_inputEncryptedFile)) {
                    a++;
                } else {
                    return;
                }
            }
            if (message.sendRequest instanceof TLRPC$TL_messages_sendMultiMedia) {
                performSendMessageRequestMulti((TLRPC$TL_messages_sendMultiMedia) message.sendRequest, message.messageObjects, message.originalPaths);
            } else {
                SecretChatHelper.getInstance().performSendEncryptedRequest((TLRPC$TL_messages_sendEncryptedMultiMedia) message.sendEncryptedRequest, message);
            }
            message.sendDelayedRequests();
        } else if (add) {
            putToDelayedMessages(key, message);
        }
    }

    protected void stopVideoService(String path) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new SendMessagesHelper$11(this, path));
    }

    protected void putToSendingMessages(TLRPC$Message message) {
        this.sendingMessages.put(Integer.valueOf(message.id), message);
    }

    protected void removeFromSendingMessages(int mid) {
        this.sendingMessages.remove(Integer.valueOf(mid));
    }

    public boolean isSendingMessage(int mid) {
        return this.sendingMessages.containsKey(Integer.valueOf(mid));
    }

    private void performSendMessageRequestMulti(TLRPC$TL_messages_sendMultiMedia req, ArrayList<MessageObject> msgObjs, ArrayList<String> originalPaths) {
        for (int a = 0; a < msgObjs.size(); a++) {
            putToSendingMessages(((MessageObject) msgObjs.get(a)).messageOwner);
        }
        ConnectionsManager.getInstance().sendRequest((TLObject) req, new SendMessagesHelper$12(this, msgObjs, originalPaths, req), null, 68);
    }

    private void performSendMessageRequest(TLObject req, MessageObject msgObj, String originalPath) {
        performSendMessageRequest(req, msgObj, originalPath, null, false);
    }

    private SendMessagesHelper$DelayedMessage findMaxDelayedMessageForMessageId(int messageId, long dialogId) {
        SendMessagesHelper$DelayedMessage maxDelayedMessage = null;
        int maxDalyedMessageId = Integer.MIN_VALUE;
        for (Entry<String, ArrayList<SendMessagesHelper$DelayedMessage>> entry : this.delayedMessages.entrySet()) {
            ArrayList<SendMessagesHelper$DelayedMessage> messages = (ArrayList) entry.getValue();
            int size = messages.size();
            for (int a = 0; a < size; a++) {
                SendMessagesHelper$DelayedMessage delayedMessage = (SendMessagesHelper$DelayedMessage) messages.get(a);
                if ((delayedMessage.type == 4 || delayedMessage.type == 0) && delayedMessage.peer == dialogId) {
                    int mid = 0;
                    if (delayedMessage.obj != null) {
                        mid = delayedMessage.obj.getId();
                    } else if (!(delayedMessage.messageObjects == null || delayedMessage.messageObjects.isEmpty())) {
                        mid = ((MessageObject) delayedMessage.messageObjects.get(delayedMessage.messageObjects.size() - 1)).getId();
                    }
                    if (mid != 0 && mid > messageId && maxDelayedMessage == null && maxDalyedMessageId < mid) {
                        maxDelayedMessage = delayedMessage;
                        maxDalyedMessageId = mid;
                    }
                }
            }
        }
        return maxDelayedMessage;
    }

    private void performSendMessageRequest(TLObject req, MessageObject msgObj, String originalPath, SendMessagesHelper$DelayedMessage parentMessage, boolean check) {
        int i;
        if (check) {
            SendMessagesHelper$DelayedMessage maxDelayedMessage = findMaxDelayedMessageForMessageId(msgObj.getId(), msgObj.getDialogId());
            if (maxDelayedMessage != null) {
                maxDelayedMessage.addDelayedRequest(req, msgObj, originalPath);
                if (parentMessage != null && parentMessage.requests != null) {
                    maxDelayedMessage.requests.addAll(parentMessage.requests);
                    return;
                }
                return;
            }
        }
        TLRPC$Message newMsgObj = msgObj.messageOwner;
        putToSendingMessages(newMsgObj);
        ConnectionsManager instance = ConnectionsManager.getInstance();
        RequestDelegate sendMessagesHelper$13 = new SendMessagesHelper$13(this, newMsgObj, req, msgObj, originalPath);
        QuickAckDelegate sendMessagesHelper$14 = new SendMessagesHelper$14(this, newMsgObj);
        if (req instanceof TLRPC$TL_messages_sendMessage) {
            i = 128;
        } else {
            i = 0;
        }
        instance.sendRequest(req, sendMessagesHelper$13, sendMessagesHelper$14, i | 68);
        if (parentMessage != null) {
            parentMessage.sendDelayedRequests();
        }
    }

    private void updateMediaPaths(MessageObject newMsgObj, TLRPC$Message sentMessage, String originalPath, boolean post) {
        TLRPC$Message newMsg = newMsgObj.messageOwner;
        if (sentMessage != null) {
            int a;
            TLRPC$PhotoSize size;
            TLRPC$PhotoSize size2;
            String fileName;
            String fileName2;
            File cacheFile;
            File cacheFile2;
            if ((sentMessage.media instanceof TLRPC$TL_messageMediaPhoto) && sentMessage.media.photo != null && (newMsg.media instanceof TLRPC$TL_messageMediaPhoto) && newMsg.media.photo != null) {
                if (sentMessage.media.ttl_seconds == 0) {
                    MessagesStorage.getInstance().putSentFile(originalPath, sentMessage.media.photo, 0);
                }
                if (newMsg.media.photo.sizes.size() == 1 && (((TLRPC$PhotoSize) newMsg.media.photo.sizes.get(0)).location instanceof TLRPC$TL_fileLocationUnavailable)) {
                    newMsg.media.photo.sizes = sentMessage.media.photo.sizes;
                } else {
                    for (a = 0; a < sentMessage.media.photo.sizes.size(); a++) {
                        size = (TLRPC$PhotoSize) sentMessage.media.photo.sizes.get(a);
                        if (!(size == null || size.location == null || (size instanceof TLRPC$TL_photoSizeEmpty) || size.type == null)) {
                            int b = 0;
                            while (b < newMsg.media.photo.sizes.size()) {
                                size2 = (TLRPC$PhotoSize) newMsg.media.photo.sizes.get(b);
                                if (size2 == null || size2.location == null || size2.type == null || !((size2.location.volume_id == -2147483648L && size.type.equals(size2.type)) || (size.f78w == size2.f78w && size.f77h == size2.f77h))) {
                                    b++;
                                } else {
                                    fileName = size2.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + size2.location.local_id;
                                    fileName2 = size.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + size.location.local_id;
                                    if (!fileName.equals(fileName2)) {
                                        cacheFile = new File(FileLoader.getInstance().getDirectory(4), fileName + ".jpg");
                                        if (sentMessage.media.ttl_seconds != 0 || (sentMessage.media.photo.sizes.size() != 1 && size.f78w <= 90 && size.f77h <= 90)) {
                                            cacheFile2 = new File(FileLoader.getInstance().getDirectory(4), fileName2 + ".jpg");
                                        } else {
                                            cacheFile2 = FileLoader.getPathToAttach(size);
                                        }
                                        cacheFile.renameTo(cacheFile2);
                                        ImageLoader.getInstance().replaceImageInCache(fileName, fileName2, size.location, post);
                                        size2.location = size.location;
                                        size2.size = size.size;
                                    }
                                }
                            }
                        }
                    }
                }
                sentMessage.message = newMsg.message;
                sentMessage.attachPath = newMsg.attachPath;
                newMsg.media.photo.id = sentMessage.media.photo.id;
                newMsg.media.photo.access_hash = sentMessage.media.photo.access_hash;
            } else if ((sentMessage.media instanceof TLRPC$TL_messageMediaDocument) && sentMessage.media.document != null && (newMsg.media instanceof TLRPC$TL_messageMediaDocument) && newMsg.media.document != null) {
                DocumentAttribute attribute;
                if (MessageObject.isVideoMessage(sentMessage)) {
                    if (sentMessage.media.ttl_seconds == 0) {
                        MessagesStorage.getInstance().putSentFile(originalPath, sentMessage.media.document, 2);
                    }
                    sentMessage.attachPath = newMsg.attachPath;
                } else if (!(MessageObject.isVoiceMessage(sentMessage) || MessageObject.isRoundVideoMessage(sentMessage) || sentMessage.media.ttl_seconds != 0)) {
                    MessagesStorage.getInstance().putSentFile(originalPath, sentMessage.media.document, 1);
                }
                size2 = newMsg.media.document.thumb;
                size = sentMessage.media.document.thumb;
                if (size2 != null && size2.location != null && size2.location.volume_id == -2147483648L && size != null && size.location != null && !(size instanceof TLRPC$TL_photoSizeEmpty) && !(size2 instanceof TLRPC$TL_photoSizeEmpty)) {
                    fileName = size2.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + size2.location.local_id;
                    fileName2 = size.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + size.location.local_id;
                    if (!fileName.equals(fileName2)) {
                        new File(FileLoader.getInstance().getDirectory(4), fileName + ".jpg").renameTo(new File(FileLoader.getInstance().getDirectory(4), fileName2 + ".jpg"));
                        ImageLoader.getInstance().replaceImageInCache(fileName, fileName2, size.location, post);
                        size2.location = size.location;
                        size2.size = size.size;
                    }
                } else if (size2 != null && MessageObject.isStickerMessage(sentMessage) && size2.location != null) {
                    size.location = size2.location;
                } else if ((size2 != null && (size2.location instanceof TLRPC$TL_fileLocationUnavailable)) || (size2 instanceof TLRPC$TL_photoSizeEmpty)) {
                    newMsg.media.document.thumb = sentMessage.media.document.thumb;
                }
                newMsg.media.document.dc_id = sentMessage.media.document.dc_id;
                newMsg.media.document.id = sentMessage.media.document.id;
                newMsg.media.document.access_hash = sentMessage.media.document.access_hash;
                byte[] oldWaveform = null;
                for (a = 0; a < newMsg.media.document.attributes.size(); a++) {
                    attribute = (DocumentAttribute) newMsg.media.document.attributes.get(a);
                    if (attribute instanceof TLRPC$TL_documentAttributeAudio) {
                        oldWaveform = attribute.waveform;
                        break;
                    }
                }
                newMsg.media.document.attributes = sentMessage.media.document.attributes;
                if (oldWaveform != null) {
                    for (a = 0; a < newMsg.media.document.attributes.size(); a++) {
                        attribute = (DocumentAttribute) newMsg.media.document.attributes.get(a);
                        if (attribute instanceof TLRPC$TL_documentAttributeAudio) {
                            attribute.waveform = oldWaveform;
                            attribute.flags |= 4;
                        }
                    }
                }
                newMsg.media.document.size = sentMessage.media.document.size;
                newMsg.media.document.mime_type = sentMessage.media.document.mime_type;
                if ((sentMessage.flags & 4) == 0 && MessageObject.isOut(sentMessage)) {
                    if (MessageObject.isNewGifDocument(sentMessage.media.document)) {
                        StickersQuery.addRecentGif(sentMessage.media.document, sentMessage.date);
                    } else if (MessageObject.isStickerDocument(sentMessage.media.document)) {
                        StickersQuery.addRecentSticker(0, sentMessage.media.document, sentMessage.date, false);
                    }
                }
                if (newMsg.attachPath == null || !newMsg.attachPath.startsWith(FileLoader.getInstance().getDirectory(4).getAbsolutePath())) {
                    sentMessage.attachPath = newMsg.attachPath;
                    sentMessage.message = newMsg.message;
                    return;
                }
                cacheFile = new File(newMsg.attachPath);
                cacheFile2 = FileLoader.getPathToAttach(sentMessage.media.document, sentMessage.media.ttl_seconds != 0);
                if (!cacheFile.renameTo(cacheFile2)) {
                    sentMessage.attachPath = newMsg.attachPath;
                    sentMessage.message = newMsg.message;
                } else if (MessageObject.isVideoMessage(sentMessage)) {
                    newMsgObj.attachPathExists = true;
                } else {
                    newMsgObj.mediaExists = newMsgObj.attachPathExists;
                    newMsgObj.attachPathExists = false;
                    newMsg.attachPath = "";
                    if (originalPath != null) {
                        if (originalPath.startsWith("http")) {
                            MessagesStorage.getInstance().addRecentLocalFile(originalPath, cacheFile2.toString(), newMsg.media.document);
                        }
                    }
                }
            } else if ((sentMessage.media instanceof TLRPC$TL_messageMediaContact) && (newMsg.media instanceof TLRPC$TL_messageMediaContact)) {
                newMsg.media = sentMessage.media;
            } else if (sentMessage.media instanceof TLRPC$TL_messageMediaWebPage) {
                newMsg.media = sentMessage.media;
            } else if (sentMessage.media instanceof TLRPC$TL_messageMediaGame) {
                newMsg.media = sentMessage.media;
                if ((newMsg.media instanceof TLRPC$TL_messageMediaGame) && !TextUtils.isEmpty(sentMessage.message)) {
                    newMsg.entities = sentMessage.entities;
                    newMsg.message = sentMessage.message;
                }
            }
        }
    }

    private void putToDelayedMessages(String location, SendMessagesHelper$DelayedMessage message) {
        ArrayList<SendMessagesHelper$DelayedMessage> arrayList = (ArrayList) this.delayedMessages.get(location);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.delayedMessages.put(location, arrayList);
        }
        arrayList.add(message);
    }

    protected ArrayList<SendMessagesHelper$DelayedMessage> getDelayedMessages(String location) {
        return (ArrayList) this.delayedMessages.get(location);
    }

    protected long getNextRandomId() {
        long val = 0;
        while (val == 0) {
            val = Utilities.random.nextLong();
        }
        return val;
    }

    public void checkUnsentMessages() {
        MessagesStorage.getInstance().getUnsentMessages(1000);
    }

    protected void processUnsentMessages(ArrayList<TLRPC$Message> messages, ArrayList<User> users, ArrayList<TLRPC$Chat> chats, ArrayList<TLRPC$EncryptedChat> encryptedChats) {
        AndroidUtilities.runOnUIThread(new SendMessagesHelper$15(this, users, chats, encryptedChats, messages));
    }

    public TLRPC$TL_photo generatePhotoSizes(String path, Uri imageUri) {
        Bitmap bitmap = ImageLoader.loadBitmap(path, imageUri, (float) AndroidUtilities.getPhotoSize(), (float) AndroidUtilities.getPhotoSize(), true);
        if (bitmap == null && AndroidUtilities.getPhotoSize() != 800) {
            bitmap = ImageLoader.loadBitmap(path, imageUri, 800.0f, 800.0f, true);
        }
        ArrayList<TLRPC$PhotoSize> sizes = new ArrayList();
        TLRPC$PhotoSize size = ImageLoader.scaleAndSaveImage(bitmap, 90.0f, 90.0f, 55, true);
        if (size != null) {
            sizes.add(size);
        }
        size = ImageLoader.scaleAndSaveImage(bitmap, (float) AndroidUtilities.getPhotoSize(), (float) AndroidUtilities.getPhotoSize(), 80, false, 101, 101);
        if (size != null) {
            sizes.add(size);
        }
        if (bitmap != null) {
            bitmap.recycle();
        }
        if (sizes.isEmpty()) {
            return null;
        }
        UserConfig.saveConfig(false);
        TLRPC$TL_photo photo = new TLRPC$TL_photo();
        photo.date = ConnectionsManager.getInstance().getCurrentTime();
        photo.sizes = sizes;
        return photo;
    }

    private static boolean prepareSendingDocumentInternal(String path, String originalPath, Uri uri, String mime, long dialog_id, MessageObject reply_to_msg, CharSequence caption) {
        if ((path == null || path.length() == 0) && uri == null) {
            return false;
        }
        if (uri != null && AndroidUtilities.isInternalUri(uri)) {
            return false;
        }
        if (path != null && AndroidUtilities.isInternalUri(Uri.fromFile(new File(path)))) {
            return false;
        }
        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        TLRPC$TL_documentAttributeAudio attributeAudio = null;
        String extension = null;
        if (uri != null) {
            boolean hasExt = false;
            if (mime != null) {
                extension = myMime.getExtensionFromMimeType(mime);
            }
            if (extension == null) {
                extension = "txt";
            } else {
                hasExt = true;
            }
            path = MediaController.copyFileToCache(uri, extension);
            if (path == null) {
                return false;
            }
            if (!hasExt) {
                extension = null;
            }
        }
        File file = new File(path);
        if (!file.exists() || file.length() == 0) {
            return false;
        }
        boolean isEncrypted = ((int) dialog_id) == 0;
        boolean allowSticker = !isEncrypted;
        String name = file.getName();
        String ext = "";
        if (extension != null) {
            ext = extension;
        } else {
            int idx = path.lastIndexOf(46);
            if (idx != -1) {
                ext = path.substring(idx + 1);
            }
        }
        if (ext.toLowerCase().equals("mp3") || ext.toLowerCase().equals("m4a")) {
            AudioInfo audioInfo = AudioInfo.getAudioInfo(file);
            if (!(audioInfo == null || audioInfo.getDuration() == 0)) {
                attributeAudio = new TLRPC$TL_documentAttributeAudio();
                attributeAudio.duration = (int) (audioInfo.getDuration() / 1000);
                attributeAudio.title = audioInfo.getTitle();
                attributeAudio.performer = audioInfo.getArtist();
                if (attributeAudio.title == null) {
                    attributeAudio.title = "";
                }
                attributeAudio.flags |= 1;
                if (attributeAudio.performer == null) {
                    attributeAudio.performer = "";
                }
                attributeAudio.flags |= 2;
            }
        }
        boolean sendNew = false;
        if (originalPath != null) {
            if (originalPath.endsWith("attheme")) {
                sendNew = true;
            } else if (attributeAudio != null) {
                originalPath = originalPath + MimeTypes.BASE_TYPE_AUDIO + file.length();
            } else {
                originalPath = originalPath + "" + file.length();
            }
        }
        TLRPC$TL_document tLRPC$TL_document = null;
        if (!(sendNew || isEncrypted)) {
            tLRPC$TL_document = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(originalPath, !isEncrypted ? 1 : 4);
            if (!(tLRPC$TL_document != null || path.equals(originalPath) || isEncrypted)) {
                tLRPC$TL_document = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(path + file.length(), !isEncrypted ? 1 : 4);
            }
        }
        if (tLRPC$TL_document == null) {
            tLRPC$TL_document = new TLRPC$TL_document();
            tLRPC$TL_document.id = 0;
            tLRPC$TL_document.date = ConnectionsManager.getInstance().getCurrentTime();
            TLRPC$TL_documentAttributeFilename fileName = new TLRPC$TL_documentAttributeFilename();
            fileName.file_name = name;
            tLRPC$TL_document.attributes.add(fileName);
            tLRPC$TL_document.size = (int) file.length();
            tLRPC$TL_document.dc_id = 0;
            if (attributeAudio != null) {
                tLRPC$TL_document.attributes.add(attributeAudio);
            }
            if (ext.length() == 0) {
                tLRPC$TL_document.mime_type = "application/octet-stream";
            } else if (ext.toLowerCase().equals("webp")) {
                tLRPC$TL_document.mime_type = "image/webp";
            } else {
                String mimeType = myMime.getMimeTypeFromExtension(ext.toLowerCase());
                if (mimeType != null) {
                    tLRPC$TL_document.mime_type = mimeType;
                } else {
                    tLRPC$TL_document.mime_type = "application/octet-stream";
                }
            }
            if (tLRPC$TL_document.mime_type.equals("image/gif")) {
                try {
                    Bitmap bitmap = ImageLoader.loadBitmap(file.getAbsolutePath(), null, 90.0f, 90.0f, true);
                    if (bitmap != null) {
                        fileName.file_name = "animation.gif";
                        tLRPC$TL_document.thumb = ImageLoader.scaleAndSaveImage(bitmap, 90.0f, 90.0f, 55, isEncrypted);
                        bitmap.recycle();
                    }
                } catch (Throwable e) {
                    FileLog.m94e(e);
                }
            }
            if (tLRPC$TL_document.mime_type.equals("image/webp") && allowSticker) {
                Options bmOptions = new Options();
                try {
                    bmOptions.inJustDecodeBounds = true;
                    RandomAccessFile randomAccessFile = new RandomAccessFile(path, "r");
                    ByteBuffer buffer = randomAccessFile.getChannel().map(MapMode.READ_ONLY, 0, (long) path.length());
                    Utilities.loadWebpImage(null, buffer, buffer.limit(), bmOptions, true);
                    randomAccessFile.close();
                } catch (Throwable e2) {
                    FileLog.m94e(e2);
                }
                if (bmOptions.outWidth != 0 && bmOptions.outHeight != 0 && bmOptions.outWidth <= 800 && bmOptions.outHeight <= 800) {
                    TLRPC$TL_documentAttributeSticker attributeSticker = new TLRPC$TL_documentAttributeSticker();
                    attributeSticker.alt = "";
                    attributeSticker.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                    tLRPC$TL_document.attributes.add(attributeSticker);
                    TLRPC$TL_documentAttributeImageSize attributeImageSize = new TLRPC$TL_documentAttributeImageSize();
                    attributeImageSize.w = bmOptions.outWidth;
                    attributeImageSize.h = bmOptions.outHeight;
                    tLRPC$TL_document.attributes.add(attributeImageSize);
                }
            }
            if (tLRPC$TL_document.thumb == null) {
                tLRPC$TL_document.thumb = new TLRPC$TL_photoSizeEmpty();
                tLRPC$TL_document.thumb.type = "s";
            }
        }
        if (caption != null) {
            tLRPC$TL_document.caption = caption.toString();
        } else {
            tLRPC$TL_document.caption = "";
        }
        HashMap<String, String> params = new HashMap();
        if (originalPath != null) {
            params.put("originalPath", originalPath);
        }
        AndroidUtilities.runOnUIThread(new SendMessagesHelper$16(tLRPC$TL_document, path, dialog_id, reply_to_msg, params));
        return true;
    }

    public static void prepareSendingDocument(String path, String originalPath, Uri uri, String mine, long dialog_id, MessageObject reply_to_msg, InputContentInfoCompat inputContent) {
        if ((path != null && originalPath != null) || uri != null) {
            ArrayList<String> paths = new ArrayList();
            ArrayList<String> originalPaths = new ArrayList();
            ArrayList<Uri> uris = null;
            if (uri != null) {
                uris = new ArrayList();
                uris.add(uri);
            }
            if (path != null) {
                paths.add(path);
                originalPaths.add(originalPath);
            }
            prepareSendingDocuments(paths, originalPaths, uris, mine, dialog_id, reply_to_msg, inputContent);
        }
    }

    public static void prepareSendingAudioDocuments(ArrayList<MessageObject> messageObjects, long dialog_id, MessageObject reply_to_msg) {
        new Thread(new SendMessagesHelper$17(messageObjects, dialog_id, reply_to_msg)).start();
    }

    public static void prepareSendingDocuments(ArrayList<String> paths, ArrayList<String> originalPaths, ArrayList<Uri> uris, String mime, long dialog_id, MessageObject reply_to_msg, InputContentInfoCompat inputContent) {
        if (paths != null || originalPaths != null || uris != null) {
            if (paths == null || originalPaths == null || paths.size() == originalPaths.size()) {
                new Thread(new SendMessagesHelper$18(paths, originalPaths, mime, dialog_id, reply_to_msg, uris, inputContent)).start();
            }
        }
    }

    public static void prepareSendingPhoto(String imageFilePath, Uri imageUri, long dialog_id, MessageObject reply_to_msg, CharSequence caption, ArrayList<TLRPC$InputDocument> stickers, InputContentInfoCompat inputContent, int ttl) {
        SendMessagesHelper$SendingMediaInfo info = new SendMessagesHelper$SendingMediaInfo();
        info.path = imageFilePath;
        info.uri = imageUri;
        if (caption != null) {
            info.caption = caption.toString();
        }
        info.ttl = ttl;
        if (!(stickers == null || stickers.isEmpty())) {
            info.masks = new ArrayList(stickers);
        }
        ArrayList<SendMessagesHelper$SendingMediaInfo> infos = new ArrayList();
        infos.add(info);
        prepareSendingMedia(infos, dialog_id, reply_to_msg, inputContent, false, false);
    }

    public static void prepareSendingBotContextResult(TLRPC$BotInlineResult result, HashMap<String, String> params, long dialog_id, MessageObject reply_to_msg) {
        if (result != null) {
            if (result.send_message instanceof TLRPC$TL_botInlineMessageMediaAuto) {
                new Thread(new SendMessagesHelper$19(result, dialog_id, params, reply_to_msg)).run();
            } else if (result.send_message instanceof TLRPC$TL_botInlineMessageText) {
                boolean z;
                TLRPC$WebPage webPage = null;
                if (((int) dialog_id) == 0) {
                    for (int a = 0; a < result.send_message.entities.size(); a++) {
                        TLRPC$MessageEntity entity = (TLRPC$MessageEntity) result.send_message.entities.get(a);
                        if (entity instanceof TLRPC$TL_messageEntityUrl) {
                            webPage = new TLRPC$TL_webPagePending();
                            webPage.url = result.send_message.message.substring(entity.offset, entity.offset + entity.length);
                            break;
                        }
                    }
                }
                SendMessagesHelper instance = getInstance();
                String str = result.send_message.message;
                if (result.send_message.no_webpage) {
                    z = false;
                } else {
                    z = true;
                }
                instance.sendMessage(str, dialog_id, reply_to_msg, webPage, z, result.send_message.entities, result.send_message.reply_markup, (HashMap) params);
            } else if (result.send_message instanceof TLRPC$TL_botInlineMessageMediaVenue) {
                TLRPC$MessageMedia venue = new TLRPC$TL_messageMediaVenue();
                venue.geo = result.send_message.geo;
                venue.address = result.send_message.address;
                venue.title = result.send_message.title;
                venue.provider = result.send_message.provider;
                venue.venue_id = result.send_message.venue_id;
                venue.venue_type = "";
                getInstance().sendMessage(venue, dialog_id, reply_to_msg, result.send_message.reply_markup, (HashMap) params);
            } else if (result.send_message instanceof TLRPC$TL_botInlineMessageMediaGeo) {
                TLRPC$MessageMedia location;
                if (result.send_message.period != 0) {
                    location = new TLRPC$TL_messageMediaGeoLive();
                    location.period = result.send_message.period;
                    location.geo = result.send_message.geo;
                    getInstance().sendMessage(location, dialog_id, reply_to_msg, result.send_message.reply_markup, (HashMap) params);
                    return;
                }
                location = new TLRPC$TL_messageMediaGeo();
                location.geo = result.send_message.geo;
                getInstance().sendMessage(location, dialog_id, reply_to_msg, result.send_message.reply_markup, (HashMap) params);
            } else if (result.send_message instanceof TLRPC$TL_botInlineMessageMediaContact) {
                User user = new TLRPC$TL_user();
                user.phone = result.send_message.phone_number;
                user.first_name = result.send_message.first_name;
                user.last_name = result.send_message.last_name;
                getInstance().sendMessage(user, dialog_id, reply_to_msg, result.send_message.reply_markup, (HashMap) params);
            }
        }
    }

    private static String getTrimmedString(String src) {
        String result = src.trim();
        if (result.length() == 0) {
            return result;
        }
        while (src.startsWith(LogCollector.LINE_SEPARATOR)) {
            src = src.substring(1);
        }
        while (src.endsWith(LogCollector.LINE_SEPARATOR)) {
            src = src.substring(0, src.length() - 1);
        }
        return src;
    }

    public static void prepareSendingText(String text, long dialog_id) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new SendMessagesHelper$20(text, dialog_id));
    }

    public static void prepareSendingMedia(ArrayList<SendMessagesHelper$SendingMediaInfo> media, long dialog_id, MessageObject reply_to_msg, InputContentInfoCompat inputContent, boolean forceDocument, boolean groupPhotos) {
        if (!media.isEmpty()) {
            mediaSendQueue.postRunnable(new SendMessagesHelper$21(media, dialog_id, forceDocument, groupPhotos, reply_to_msg, inputContent));
        }
    }

    private static void fillVideoAttribute(String videoPath, TLRPC$TL_documentAttributeVideo attributeVideo, VideoEditedInfo videoEditedInfo) {
        Throwable e;
        MediaPlayer mp;
        Throwable th;
        boolean infoObtained = false;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            MediaMetadataRetriever mediaMetadataRetriever2 = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever2.setDataSource(videoPath);
                String width = mediaMetadataRetriever2.extractMetadata(18);
                if (width != null) {
                    attributeVideo.w = Integer.parseInt(width);
                }
                String height = mediaMetadataRetriever2.extractMetadata(19);
                if (height != null) {
                    attributeVideo.h = Integer.parseInt(height);
                }
                String duration = mediaMetadataRetriever2.extractMetadata(9);
                if (duration != null) {
                    attributeVideo.duration = (int) Math.ceil((double) (((float) Long.parseLong(duration)) / 1000.0f));
                }
                if (VERSION.SDK_INT >= 17) {
                    String rotation = mediaMetadataRetriever2.extractMetadata(24);
                    if (rotation != null) {
                        int val = Utilities.parseInt(rotation).intValue();
                        if (videoEditedInfo != null) {
                            videoEditedInfo.rotationValue = val;
                        } else if (val == 90 || val == 270) {
                            int temp = attributeVideo.w;
                            attributeVideo.w = attributeVideo.h;
                            attributeVideo.h = temp;
                        }
                    }
                }
                infoObtained = true;
                if (mediaMetadataRetriever2 != null) {
                    try {
                        mediaMetadataRetriever2.release();
                    } catch (Throwable e2) {
                        FileLog.m94e(e2);
                        mediaMetadataRetriever = mediaMetadataRetriever2;
                    }
                }
                mediaMetadataRetriever = mediaMetadataRetriever2;
            } catch (Exception e3) {
                e2 = e3;
                mediaMetadataRetriever = mediaMetadataRetriever2;
                try {
                    FileLog.m94e(e2);
                    if (mediaMetadataRetriever != null) {
                        try {
                            mediaMetadataRetriever.release();
                        } catch (Throwable e22) {
                            FileLog.m94e(e22);
                        }
                    }
                    if (infoObtained) {
                        try {
                            mp = MediaPlayer.create(ApplicationLoader.applicationContext, Uri.fromFile(new File(videoPath)));
                            if (mp == null) {
                                attributeVideo.duration = (int) Math.ceil((double) (((float) mp.getDuration()) / 1000.0f));
                                attributeVideo.w = mp.getVideoWidth();
                                attributeVideo.h = mp.getVideoHeight();
                                mp.release();
                            }
                        } catch (Throwable e222) {
                            FileLog.m94e(e222);
                            return;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (mediaMetadataRetriever != null) {
                        try {
                            mediaMetadataRetriever.release();
                        } catch (Throwable e2222) {
                            FileLog.m94e(e2222);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                mediaMetadataRetriever = mediaMetadataRetriever2;
                if (mediaMetadataRetriever != null) {
                    mediaMetadataRetriever.release();
                }
                throw th;
            }
        } catch (Exception e4) {
            e2222 = e4;
            FileLog.m94e(e2222);
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
            if (infoObtained) {
                mp = MediaPlayer.create(ApplicationLoader.applicationContext, Uri.fromFile(new File(videoPath)));
                if (mp == null) {
                    attributeVideo.duration = (int) Math.ceil((double) (((float) mp.getDuration()) / 1000.0f));
                    attributeVideo.w = mp.getVideoWidth();
                    attributeVideo.h = mp.getVideoHeight();
                    mp.release();
                }
            }
        }
        if (infoObtained) {
            mp = MediaPlayer.create(ApplicationLoader.applicationContext, Uri.fromFile(new File(videoPath)));
            if (mp == null) {
                attributeVideo.duration = (int) Math.ceil((double) (((float) mp.getDuration()) / 1000.0f));
                attributeVideo.w = mp.getVideoWidth();
                attributeVideo.h = mp.getVideoHeight();
                mp.release();
            }
        }
    }

    private static Bitmap createVideoThumbnail(String filePath, long time) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime(time, 1);
            try {
                retriever.release();
            } catch (RuntimeException e) {
            }
        } catch (Exception e2) {
            try {
                retriever.release();
            } catch (RuntimeException e3) {
            }
        } catch (Throwable th) {
            try {
                retriever.release();
            } catch (RuntimeException e4) {
            }
            throw th;
        }
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int max = Math.max(width, height);
        if (max > 90) {
            float scale = 90.0f / ((float) max);
            bitmap = Bitmaps.createScaledBitmap(bitmap, Math.round(((float) width) * scale), Math.round(((float) height) * scale), true);
        }
        return bitmap;
    }

    public static void prepareSendingVideo(String videoPath, long estimatedSize, long duration, int width, int height, VideoEditedInfo videoEditedInfo, long dialog_id, MessageObject reply_to_msg, CharSequence caption, int ttl) {
        if (videoPath != null && videoPath.length() != 0) {
            new Thread(new SendMessagesHelper$22(dialog_id, videoEditedInfo, videoPath, duration, ttl, height, width, estimatedSize, caption, reply_to_msg)).start();
        }
    }
}
