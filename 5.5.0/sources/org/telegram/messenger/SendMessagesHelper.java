package org.telegram.messenger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.p010c.p012b.p013a.C0060e;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.ir.talaeii.R;
import org.telegram.messenger.MediaController.SearchImage;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.query.DraftQuery;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.QuickAckDelegate;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
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
import org.telegram.tgnet.TLRPC$TL_documentAttributeAnimated;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_documentAttributeFilename;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker_layer55;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo_layer65;
import org.telegram.tgnet.TLRPC$TL_error;
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
import org.telegram.tgnet.TLRPC$TL_messages_botCallbackAnswer;
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
import org.telegram.tgnet.TLRPC$TL_payments_paymentForm;
import org.telegram.tgnet.TLRPC$TL_payments_paymentReceipt;
import org.telegram.tgnet.TLRPC$TL_peerChannel;
import org.telegram.tgnet.TLRPC$TL_peerChat;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_photo;
import org.telegram.tgnet.TLRPC$TL_photoCachedSize;
import org.telegram.tgnet.TLRPC$TL_photoSize;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
import org.telegram.tgnet.TLRPC$TL_updateMessageID;
import org.telegram.tgnet.TLRPC$TL_updateNewChannelMessage;
import org.telegram.tgnet.TLRPC$TL_updateNewMessage;
import org.telegram.tgnet.TLRPC$TL_updateShortSentMessage;
import org.telegram.tgnet.TLRPC$TL_user;
import org.telegram.tgnet.TLRPC$TL_userContact_old2;
import org.telegram.tgnet.TLRPC$TL_userRequest_old2;
import org.telegram.tgnet.TLRPC$TL_webPagePending;
import org.telegram.tgnet.TLRPC$TL_webPageUrlPending;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.BotInlineResult;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ChatParticipant;
import org.telegram.tgnet.TLRPC.DecryptedMessage;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputDocument;
import org.telegram.tgnet.TLRPC.InputEncryptedFile;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.InputMedia;
import org.telegram.tgnet.TLRPC.InputPeer;
import org.telegram.tgnet.TLRPC.InputUser;
import org.telegram.tgnet.TLRPC.KeyboardButton;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.ReplyMarkup;
import org.telegram.tgnet.TLRPC.TL_botInlineMediaResult;
import org.telegram.tgnet.TLRPC.TL_botInlineMessageMediaAuto;
import org.telegram.tgnet.TLRPC.TL_botInlineMessageMediaContact;
import org.telegram.tgnet.TLRPC.TL_botInlineMessageMediaGeo;
import org.telegram.tgnet.TLRPC.TL_botInlineMessageMediaVenue;
import org.telegram.tgnet.TLRPC.TL_botInlineMessageText;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.PaymentFormActivity;

public class SendMessagesHelper implements NotificationCenterDelegate {
    private static volatile SendMessagesHelper Instance = null;
    private static DispatchQueue mediaSendQueue = new DispatchQueue("mediaSendQueue");
    private static ThreadPoolExecutor mediaSendThreadPool;
    private ChatFull currentChatInfo = null;
    private HashMap<String, ArrayList<DelayedMessage>> delayedMessages = new HashMap();
    private LocationProvider locationProvider = new LocationProvider(new C33811());
    private HashMap<Integer, Message> sendingMessages = new HashMap();
    private HashMap<Integer, MessageObject> unsentMessages = new HashMap();
    private HashMap<String, MessageObject> waitingForCallback = new HashMap();
    private HashMap<String, MessageObject> waitingForLocation = new HashMap();

    /* renamed from: org.telegram.messenger.SendMessagesHelper$1 */
    class C33811 implements LocationProviderDelegate {
        C33811() {
        }

        public void onLocationAcquired(Location location) {
            SendMessagesHelper.this.sendLocation(location);
            SendMessagesHelper.this.waitingForLocation.clear();
        }

        public void onUnableLocationAcquire() {
            HashMap hashMap = new HashMap(SendMessagesHelper.this.waitingForLocation);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.wasUnableToFindCurrentLocation, hashMap);
            SendMessagesHelper.this.waitingForLocation.clear();
        }
    }

    /* renamed from: org.telegram.messenger.SendMessagesHelper$4 */
    class C33954 implements OnClickListener {
        C33954() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    protected class DelayedMessage {
        public EncryptedChat encryptedChat;
        public HashMap<Object, Object> extraHashMap;
        public int finalGroupMessage;
        public long groupId;
        public String httpLocation;
        public FileLocation location;
        public ArrayList<MessageObject> messageObjects;
        public ArrayList<Message> messages;
        public MessageObject obj;
        public String originalPath;
        public ArrayList<String> originalPaths;
        public long peer;
        ArrayList<DelayedMessageSendAfterRequest> requests;
        public TLObject sendEncryptedRequest;
        public TLObject sendRequest;
        public int type;
        public boolean upload;
        public VideoEditedInfo videoEditedInfo;

        public DelayedMessage(long j) {
            this.peer = j;
        }

        public void addDelayedRequest(TLObject tLObject, ArrayList<MessageObject> arrayList, ArrayList<String> arrayList2) {
            DelayedMessageSendAfterRequest delayedMessageSendAfterRequest = new DelayedMessageSendAfterRequest();
            delayedMessageSendAfterRequest.request = tLObject;
            delayedMessageSendAfterRequest.msgObjs = arrayList;
            delayedMessageSendAfterRequest.originalPaths = arrayList2;
            if (this.requests == null) {
                this.requests = new ArrayList();
            }
            this.requests.add(delayedMessageSendAfterRequest);
        }

        public void addDelayedRequest(TLObject tLObject, MessageObject messageObject, String str) {
            DelayedMessageSendAfterRequest delayedMessageSendAfterRequest = new DelayedMessageSendAfterRequest();
            delayedMessageSendAfterRequest.request = tLObject;
            delayedMessageSendAfterRequest.msgObj = messageObject;
            delayedMessageSendAfterRequest.originalPath = str;
            if (this.requests == null) {
                this.requests = new ArrayList();
            }
            this.requests.add(delayedMessageSendAfterRequest);
        }

        public void markAsError() {
            if (this.type == 4) {
                for (int i = 0; i < this.messageObjects.size(); i++) {
                    MessageObject messageObject = (MessageObject) this.messageObjects.get(i);
                    MessagesStorage.getInstance().markMessageAsSendError(messageObject.messageOwner);
                    messageObject.messageOwner.send_state = 2;
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(messageObject.getId()));
                    SendMessagesHelper.this.processSentMessage(messageObject.getId());
                }
                SendMessagesHelper.this.delayedMessages.remove("group_" + this.groupId);
            } else {
                MessagesStorage.getInstance().markMessageAsSendError(this.obj.messageOwner);
                this.obj.messageOwner.send_state = 2;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(this.obj.getId()));
                SendMessagesHelper.this.processSentMessage(this.obj.getId());
            }
            sendDelayedRequests();
        }

        public void sendDelayedRequests() {
            if (this.requests == null) {
                return;
            }
            if (this.type == 4 || this.type == 0) {
                int size = this.requests.size();
                for (int i = 0; i < size; i++) {
                    DelayedMessageSendAfterRequest delayedMessageSendAfterRequest = (DelayedMessageSendAfterRequest) this.requests.get(i);
                    if (delayedMessageSendAfterRequest.request instanceof TLRPC$TL_messages_sendEncryptedMultiMedia) {
                        SecretChatHelper.getInstance().performSendEncryptedRequest((TLRPC$TL_messages_sendEncryptedMultiMedia) delayedMessageSendAfterRequest.request, this);
                    } else if (delayedMessageSendAfterRequest.request instanceof TLRPC$TL_messages_sendMultiMedia) {
                        SendMessagesHelper.this.performSendMessageRequestMulti((TLRPC$TL_messages_sendMultiMedia) delayedMessageSendAfterRequest.request, delayedMessageSendAfterRequest.msgObjs, delayedMessageSendAfterRequest.originalPaths);
                    } else {
                        SendMessagesHelper.this.performSendMessageRequest(delayedMessageSendAfterRequest.request, delayedMessageSendAfterRequest.msgObj, delayedMessageSendAfterRequest.originalPath);
                    }
                }
                this.requests = null;
            }
        }
    }

    protected class DelayedMessageSendAfterRequest {
        public MessageObject msgObj;
        public ArrayList<MessageObject> msgObjs;
        public String originalPath;
        public ArrayList<String> originalPaths;
        public TLObject request;

        protected DelayedMessageSendAfterRequest() {
        }
    }

    public static class LocationProvider {
        private LocationProviderDelegate delegate;
        private GpsLocationListener gpsLocationListener = new GpsLocationListener();
        private Location lastKnownLocation;
        private LocationManager locationManager;
        private Runnable locationQueryCancelRunnable;
        private GpsLocationListener networkLocationListener = new GpsLocationListener();

        public interface LocationProviderDelegate {
            void onLocationAcquired(Location location);

            void onUnableLocationAcquire();
        }

        /* renamed from: org.telegram.messenger.SendMessagesHelper$LocationProvider$1 */
        class C34081 implements Runnable {
            C34081() {
            }

            public void run() {
                if (LocationProvider.this.locationQueryCancelRunnable == this) {
                    if (LocationProvider.this.delegate != null) {
                        if (LocationProvider.this.lastKnownLocation != null) {
                            LocationProvider.this.delegate.onLocationAcquired(LocationProvider.this.lastKnownLocation);
                        } else {
                            LocationProvider.this.delegate.onUnableLocationAcquire();
                        }
                    }
                    LocationProvider.this.cleanup();
                }
            }
        }

        private class GpsLocationListener implements LocationListener {
            private GpsLocationListener() {
            }

            public void onLocationChanged(Location location) {
                if (location != null && LocationProvider.this.locationQueryCancelRunnable != null) {
                    FileLog.m13726e("found location " + location);
                    LocationProvider.this.lastKnownLocation = location;
                    if (location.getAccuracy() < 100.0f) {
                        if (LocationProvider.this.delegate != null) {
                            LocationProvider.this.delegate.onLocationAcquired(location);
                        }
                        if (LocationProvider.this.locationQueryCancelRunnable != null) {
                            AndroidUtilities.cancelRunOnUIThread(LocationProvider.this.locationQueryCancelRunnable);
                        }
                        LocationProvider.this.cleanup();
                    }
                }
            }

            public void onProviderDisabled(String str) {
            }

            public void onProviderEnabled(String str) {
            }

            public void onStatusChanged(String str, int i, Bundle bundle) {
            }
        }

        public LocationProvider(LocationProviderDelegate locationProviderDelegate) {
            this.delegate = locationProviderDelegate;
        }

        private void cleanup() {
            this.locationManager.removeUpdates(this.gpsLocationListener);
            this.locationManager.removeUpdates(this.networkLocationListener);
            this.lastKnownLocation = null;
            this.locationQueryCancelRunnable = null;
        }

        public void setDelegate(LocationProviderDelegate locationProviderDelegate) {
            this.delegate = locationProviderDelegate;
        }

        public void start() {
            if (this.locationManager == null) {
                this.locationManager = (LocationManager) ApplicationLoader.applicationContext.getSystemService(C1797b.LOCATION);
            }
            try {
                this.locationManager.requestLocationUpdates("gps", 1, BitmapDescriptorFactory.HUE_RED, this.gpsLocationListener);
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
            try {
                this.locationManager.requestLocationUpdates("network", 1, BitmapDescriptorFactory.HUE_RED, this.networkLocationListener);
            } catch (Throwable e2) {
                FileLog.m13728e(e2);
            }
            try {
                this.lastKnownLocation = this.locationManager.getLastKnownLocation("gps");
                if (this.lastKnownLocation == null) {
                    this.lastKnownLocation = this.locationManager.getLastKnownLocation("network");
                }
            } catch (Throwable e22) {
                FileLog.m13728e(e22);
            }
            if (this.locationQueryCancelRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(this.locationQueryCancelRunnable);
            }
            this.locationQueryCancelRunnable = new C34081();
            AndroidUtilities.runOnUIThread(this.locationQueryCancelRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
        }

        public void stop() {
            if (this.locationManager != null) {
                if (this.locationQueryCancelRunnable != null) {
                    AndroidUtilities.cancelRunOnUIThread(this.locationQueryCancelRunnable);
                }
                cleanup();
            }
        }
    }

    private static class MediaSendPrepareWorker {
        public volatile TLRPC$TL_photo photo;
        public CountDownLatch sync;

        private MediaSendPrepareWorker() {
        }
    }

    public static class SendingMediaInfo {
        public String caption;
        public boolean isVideo;
        public ArrayList<InputDocument> masks;
        public String path;
        public SearchImage searchImage;
        public int ttl;
        public Uri uri;
        public VideoEditedInfo videoEditedInfo;
    }

    static {
        int availableProcessors = VERSION.SDK_INT >= 17 ? Runtime.getRuntime().availableProcessors() : 2;
        mediaSendThreadPool = new ThreadPoolExecutor(availableProcessors, availableProcessors, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
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

    private static Bitmap createVideoThumbnail(String str, long j) {
        Bitmap frameAtTime;
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            frameAtTime = mediaMetadataRetriever.getFrameAtTime(j, 1);
            try {
                mediaMetadataRetriever.release();
            } catch (RuntimeException e) {
            }
        } catch (Exception e2) {
            try {
                mediaMetadataRetriever.release();
                frameAtTime = null;
            } catch (RuntimeException e3) {
                frameAtTime = null;
            }
        } catch (Throwable th) {
            try {
                mediaMetadataRetriever.release();
            } catch (RuntimeException e4) {
            }
            throw th;
        }
        if (frameAtTime == null) {
            return null;
        }
        int width = frameAtTime.getWidth();
        int height = frameAtTime.getHeight();
        int max = Math.max(width, height);
        if (max <= 90) {
            return frameAtTime;
        }
        float f = 90.0f / ((float) max);
        return Bitmaps.createScaledBitmap(frameAtTime, Math.round(((float) width) * f), Math.round(((float) height) * f), true);
    }

    private static void fillVideoAttribute(String str, TLRPC$TL_documentAttributeVideo tLRPC$TL_documentAttributeVideo, VideoEditedInfo videoEditedInfo) {
        MediaMetadataRetriever mediaMetadataRetriever;
        Object obj;
        Throwable e;
        MediaPlayer create;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(str);
                String extractMetadata = mediaMetadataRetriever.extractMetadata(18);
                if (extractMetadata != null) {
                    tLRPC$TL_documentAttributeVideo.w = Integer.parseInt(extractMetadata);
                }
                extractMetadata = mediaMetadataRetriever.extractMetadata(19);
                if (extractMetadata != null) {
                    tLRPC$TL_documentAttributeVideo.h = Integer.parseInt(extractMetadata);
                }
                extractMetadata = mediaMetadataRetriever.extractMetadata(9);
                if (extractMetadata != null) {
                    tLRPC$TL_documentAttributeVideo.duration = (int) Math.ceil((double) (((float) Long.parseLong(extractMetadata)) / 1000.0f));
                }
                if (VERSION.SDK_INT >= 17) {
                    extractMetadata = mediaMetadataRetriever.extractMetadata(24);
                    if (extractMetadata != null) {
                        int intValue = Utilities.parseInt(extractMetadata).intValue();
                        if (videoEditedInfo != null) {
                            videoEditedInfo.rotationValue = intValue;
                        } else if (intValue == 90 || intValue == 270) {
                            intValue = tLRPC$TL_documentAttributeVideo.w;
                            tLRPC$TL_documentAttributeVideo.w = tLRPC$TL_documentAttributeVideo.h;
                            tLRPC$TL_documentAttributeVideo.h = intValue;
                        }
                    }
                }
                obj = 1;
                if (mediaMetadataRetriever != null) {
                    try {
                        mediaMetadataRetriever.release();
                    } catch (Throwable e2) {
                        FileLog.m13728e(e2);
                    }
                }
            } catch (Exception e3) {
                e = e3;
                try {
                    FileLog.m13728e(e);
                    if (mediaMetadataRetriever != null) {
                        try {
                            mediaMetadataRetriever.release();
                        } catch (Throwable e4) {
                            FileLog.m13728e(e4);
                            obj = null;
                        }
                    }
                    obj = null;
                    if (obj != null) {
                        try {
                            create = MediaPlayer.create(ApplicationLoader.applicationContext, Uri.fromFile(new File(str)));
                            if (create == null) {
                                tLRPC$TL_documentAttributeVideo.duration = (int) Math.ceil((double) (((float) create.getDuration()) / 1000.0f));
                                tLRPC$TL_documentAttributeVideo.w = create.getVideoWidth();
                                tLRPC$TL_documentAttributeVideo.h = create.getVideoHeight();
                                create.release();
                            }
                        } catch (Throwable e42) {
                            FileLog.m13728e(e42);
                            return;
                        }
                    }
                } catch (Throwable th) {
                    e42 = th;
                    if (mediaMetadataRetriever != null) {
                        try {
                            mediaMetadataRetriever.release();
                        } catch (Throwable e22) {
                            FileLog.m13728e(e22);
                        }
                    }
                    throw e42;
                }
            }
        } catch (Exception e5) {
            e42 = e5;
            mediaMetadataRetriever = null;
            FileLog.m13728e(e42);
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
            obj = null;
            if (obj != null) {
                create = MediaPlayer.create(ApplicationLoader.applicationContext, Uri.fromFile(new File(str)));
                if (create == null) {
                    tLRPC$TL_documentAttributeVideo.duration = (int) Math.ceil((double) (((float) create.getDuration()) / 1000.0f));
                    tLRPC$TL_documentAttributeVideo.w = create.getVideoWidth();
                    tLRPC$TL_documentAttributeVideo.h = create.getVideoHeight();
                    create.release();
                }
            }
        } catch (Throwable th2) {
            e42 = th2;
            mediaMetadataRetriever = null;
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
            throw e42;
        }
        if (obj != null) {
            create = MediaPlayer.create(ApplicationLoader.applicationContext, Uri.fromFile(new File(str)));
            if (create == null) {
                tLRPC$TL_documentAttributeVideo.duration = (int) Math.ceil((double) (((float) create.getDuration()) / 1000.0f));
                tLRPC$TL_documentAttributeVideo.w = create.getVideoWidth();
                tLRPC$TL_documentAttributeVideo.h = create.getVideoHeight();
                create.release();
            }
        }
    }

    private DelayedMessage findMaxDelayedMessageForMessageId(int i, long j) {
        DelayedMessage delayedMessage = null;
        int i2 = Integer.MIN_VALUE;
        for (Entry value : this.delayedMessages.entrySet()) {
            ArrayList arrayList = (ArrayList) value.getValue();
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                DelayedMessage delayedMessage2;
                int i4;
                DelayedMessage delayedMessage3 = (DelayedMessage) arrayList.get(i3);
                if ((delayedMessage3.type == 4 || delayedMessage3.type == 0) && delayedMessage3.peer == j) {
                    int i5 = 0;
                    if (delayedMessage3.obj != null) {
                        i5 = delayedMessage3.obj.getId();
                    } else if (!(delayedMessage3.messageObjects == null || delayedMessage3.messageObjects.isEmpty())) {
                        i5 = ((MessageObject) delayedMessage3.messageObjects.get(delayedMessage3.messageObjects.size() - 1)).getId();
                    }
                    if (i5 != 0 && i5 > i && delayedMessage == null && i2 < i5) {
                        int i6 = i5;
                        delayedMessage2 = delayedMessage3;
                        i4 = i6;
                        i3++;
                        delayedMessage = delayedMessage2;
                        i2 = i4;
                    }
                }
                i4 = i2;
                delayedMessage2 = delayedMessage;
                i3++;
                delayedMessage = delayedMessage2;
                i2 = i4;
            }
        }
        return delayedMessage;
    }

    public static SendMessagesHelper getInstance() {
        SendMessagesHelper sendMessagesHelper = Instance;
        if (sendMessagesHelper == null) {
            synchronized (SendMessagesHelper.class) {
                sendMessagesHelper = Instance;
                if (sendMessagesHelper == null) {
                    sendMessagesHelper = new SendMessagesHelper();
                    Instance = sendMessagesHelper;
                }
            }
        }
        return sendMessagesHelper;
    }

    private static String getTrimmedString(String str) {
        String trim = str.trim();
        if (trim.length() == 0) {
            return trim;
        }
        while (str.startsWith("\n")) {
            str = str.substring(1);
        }
        while (str.endsWith("\n")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private void performSendDelayedMessage(DelayedMessage delayedMessage) {
        performSendDelayedMessage(delayedMessage, -1);
    }

    private void performSendDelayedMessage(DelayedMessage delayedMessage, int i) {
        boolean z = true;
        boolean z2 = false;
        String file;
        String file2;
        if (delayedMessage.type == 0) {
            if (delayedMessage.httpLocation != null) {
                putToDelayedMessages(delayedMessage.httpLocation, delayedMessage);
                ImageLoader.getInstance().loadHttpFile(delayedMessage.httpLocation, "file");
            } else if (delayedMessage.sendRequest != null) {
                file = FileLoader.getPathToAttach(delayedMessage.location).toString();
                putToDelayedMessages(file, delayedMessage);
                FileLoader.getInstance().uploadFile(file, false, true, 16777216);
            } else {
                file2 = FileLoader.getPathToAttach(delayedMessage.location).toString();
                if (!(delayedMessage.sendEncryptedRequest == null || delayedMessage.location.dc_id == 0)) {
                    File file3 = new File(file2);
                    if (!file3.exists()) {
                        file2 = FileLoader.getPathToAttach(delayedMessage.location, true).toString();
                        file3 = new File(file2);
                    }
                    if (!file3.exists()) {
                        putToDelayedMessages(FileLoader.getAttachFileName(delayedMessage.location), delayedMessage);
                        FileLoader.getInstance().loadFile(delayedMessage.location, "jpg", 0, 0);
                        return;
                    }
                }
                putToDelayedMessages(file2, delayedMessage);
                FileLoader.getInstance().uploadFile(file2, true, true, 16777216);
            }
        } else if (delayedMessage.type == 1) {
            if (delayedMessage.videoEditedInfo == null || !delayedMessage.videoEditedInfo.needConvert()) {
                if (delayedMessage.videoEditedInfo != null) {
                    if (delayedMessage.videoEditedInfo.file != null) {
                        (delayedMessage.sendRequest instanceof TLRPC$TL_messages_sendMedia ? ((TLRPC$TL_messages_sendMedia) delayedMessage.sendRequest).media : ((TLRPC$TL_messages_sendBroadcast) delayedMessage.sendRequest).media).file = delayedMessage.videoEditedInfo.file;
                        delayedMessage.videoEditedInfo.file = null;
                    } else if (delayedMessage.videoEditedInfo.encryptedFile != null) {
                        TLRPC$TL_decryptedMessage tLRPC$TL_decryptedMessage = (TLRPC$TL_decryptedMessage) delayedMessage.sendEncryptedRequest;
                        tLRPC$TL_decryptedMessage.media.size = (int) delayedMessage.videoEditedInfo.estimatedSize;
                        tLRPC$TL_decryptedMessage.media.key = delayedMessage.videoEditedInfo.key;
                        tLRPC$TL_decryptedMessage.media.iv = delayedMessage.videoEditedInfo.iv;
                        SecretChatHelper.getInstance().performSendEncryptedRequest(tLRPC$TL_decryptedMessage, delayedMessage.obj.messageOwner, delayedMessage.encryptedChat, delayedMessage.videoEditedInfo.encryptedFile, delayedMessage.originalPath, delayedMessage.obj);
                        delayedMessage.videoEditedInfo.encryptedFile = null;
                        return;
                    }
                }
                if (delayedMessage.sendRequest != null) {
                    if ((delayedMessage.sendRequest instanceof TLRPC$TL_messages_sendMedia ? ((TLRPC$TL_messages_sendMedia) delayedMessage.sendRequest).media : ((TLRPC$TL_messages_sendBroadcast) delayedMessage.sendRequest).media).file == null) {
                        file2 = delayedMessage.obj.messageOwner.attachPath;
                        r3 = delayedMessage.obj.getDocument();
                        if (file2 == null) {
                            file2 = FileLoader.getInstance().getDirectory(4) + "/" + r3.id + ".mp4";
                        }
                        putToDelayedMessages(file2, delayedMessage);
                        if (delayedMessage.obj.videoEditedInfo == null || !delayedMessage.obj.videoEditedInfo.needConvert()) {
                            FileLoader.getInstance().uploadFile(file2, false, false, ConnectionsManager.FileTypeVideo);
                            return;
                        } else {
                            FileLoader.getInstance().uploadFile(file2, false, false, r3.size, ConnectionsManager.FileTypeVideo);
                            return;
                        }
                    }
                    file = FileLoader.getInstance().getDirectory(4) + "/" + delayedMessage.location.volume_id + "_" + delayedMessage.location.local_id + ".jpg";
                    putToDelayedMessages(file, delayedMessage);
                    FileLoader.getInstance().uploadFile(file, false, true, 16777216);
                    return;
                }
                String str = delayedMessage.obj.messageOwner.attachPath;
                Document document = delayedMessage.obj.getDocument();
                if (str == null) {
                    str = FileLoader.getInstance().getDirectory(4) + "/" + document.id + ".mp4";
                }
                if (delayedMessage.sendEncryptedRequest == null || document.dc_id == 0 || new File(str).exists()) {
                    putToDelayedMessages(str, delayedMessage);
                    if (delayedMessage.obj.videoEditedInfo == null || !delayedMessage.obj.videoEditedInfo.needConvert()) {
                        FileLoader.getInstance().uploadFile(str, true, false, ConnectionsManager.FileTypeVideo);
                        return;
                    } else {
                        FileLoader.getInstance().uploadFile(str, true, false, document.size, ConnectionsManager.FileTypeVideo);
                        return;
                    }
                }
                putToDelayedMessages(FileLoader.getAttachFileName(document), delayedMessage);
                FileLoader.getInstance().loadFile(document, true, 0);
                return;
            }
            file = delayedMessage.obj.messageOwner.attachPath;
            r1 = delayedMessage.obj.getDocument();
            if (file == null) {
                file = FileLoader.getInstance().getDirectory(4) + "/" + r1.id + ".mp4";
            }
            putToDelayedMessages(file, delayedMessage);
            MediaController.getInstance().scheduleVideoConvert(delayedMessage.obj);
        } else if (delayedMessage.type == 2) {
            if (delayedMessage.httpLocation != null) {
                putToDelayedMessages(delayedMessage.httpLocation, delayedMessage);
                ImageLoader.getInstance().loadHttpFile(delayedMessage.httpLocation, "gif");
            } else if (delayedMessage.sendRequest != null) {
                InputMedia inputMedia = delayedMessage.sendRequest instanceof TLRPC$TL_messages_sendMedia ? ((TLRPC$TL_messages_sendMedia) delayedMessage.sendRequest).media : ((TLRPC$TL_messages_sendBroadcast) delayedMessage.sendRequest).media;
                if (inputMedia.file == null) {
                    file = delayedMessage.obj.messageOwner.attachPath;
                    putToDelayedMessages(file, delayedMessage);
                    r1 = FileLoader.getInstance();
                    if (delayedMessage.sendRequest != null) {
                        z = false;
                    }
                    r1.uploadFile(file, z, false, ConnectionsManager.FileTypeFile);
                } else if (inputMedia.thumb == null && delayedMessage.location != null) {
                    file = FileLoader.getInstance().getDirectory(4) + "/" + delayedMessage.location.volume_id + "_" + delayedMessage.location.local_id + ".jpg";
                    putToDelayedMessages(file, delayedMessage);
                    FileLoader.getInstance().uploadFile(file, false, true, 16777216);
                }
            } else {
                file = delayedMessage.obj.messageOwner.attachPath;
                r1 = delayedMessage.obj.getDocument();
                if (delayedMessage.sendEncryptedRequest == null || r1.dc_id == 0 || new File(file).exists()) {
                    putToDelayedMessages(file, delayedMessage);
                    FileLoader.getInstance().uploadFile(file, true, false, ConnectionsManager.FileTypeFile);
                    return;
                }
                putToDelayedMessages(FileLoader.getAttachFileName(r1), delayedMessage);
                FileLoader.getInstance().loadFile(r1, true, 0);
            }
        } else if (delayedMessage.type == 3) {
            file = delayedMessage.obj.messageOwner.attachPath;
            putToDelayedMessages(file, delayedMessage);
            r1 = FileLoader.getInstance();
            if (delayedMessage.sendRequest == null) {
                z2 = true;
            }
            r1.uploadFile(file, z2, true, ConnectionsManager.FileTypeAudio);
        } else if (delayedMessage.type == 4) {
            boolean z3 = i < 0;
            if (delayedMessage.location != null || delayedMessage.httpLocation != null || delayedMessage.upload || i >= 0) {
                if (i < 0) {
                    i = delayedMessage.messageObjects.size() - 1;
                }
                MessageObject messageObject = (MessageObject) delayedMessage.messageObjects.get(i);
                if (messageObject.getDocument() != null) {
                    if (delayedMessage.videoEditedInfo != null) {
                        file2 = messageObject.messageOwner.attachPath;
                        r3 = messageObject.getDocument();
                        if (file2 == null) {
                            file2 = FileLoader.getInstance().getDirectory(4) + "/" + r3.id + ".mp4";
                        }
                        putToDelayedMessages(file2, delayedMessage);
                        delayedMessage.extraHashMap.put(messageObject, file2);
                        delayedMessage.extraHashMap.put(file2 + "_i", messageObject);
                        if (delayedMessage.location != null) {
                            delayedMessage.extraHashMap.put(file2 + "_t", delayedMessage.location);
                        }
                        MediaController.getInstance().scheduleVideoConvert(messageObject);
                    } else {
                        Document document2 = messageObject.getDocument();
                        file2 = messageObject.messageOwner.attachPath;
                        if (file2 == null) {
                            file2 = FileLoader.getInstance().getDirectory(4) + "/" + document2.id + ".mp4";
                        }
                        if (delayedMessage.sendRequest != null) {
                            InputMedia inputMedia2 = ((TLRPC$TL_inputSingleMedia) ((TLRPC$TL_messages_sendMultiMedia) delayedMessage.sendRequest).multi_media.get(i)).media;
                            if (inputMedia2.file == null) {
                                putToDelayedMessages(file2, delayedMessage);
                                delayedMessage.extraHashMap.put(messageObject, file2);
                                delayedMessage.extraHashMap.put(file2, inputMedia2);
                                delayedMessage.extraHashMap.put(file2 + "_i", messageObject);
                                if (delayedMessage.location != null) {
                                    delayedMessage.extraHashMap.put(file2 + "_t", delayedMessage.location);
                                }
                                if (messageObject.videoEditedInfo == null || !messageObject.videoEditedInfo.needConvert()) {
                                    FileLoader.getInstance().uploadFile(file2, false, false, ConnectionsManager.FileTypeVideo);
                                } else {
                                    FileLoader.getInstance().uploadFile(file2, false, false, document2.size, ConnectionsManager.FileTypeVideo);
                                }
                            } else {
                                String str2 = FileLoader.getInstance().getDirectory(4) + "/" + delayedMessage.location.volume_id + "_" + delayedMessage.location.local_id + ".jpg";
                                putToDelayedMessages(str2, delayedMessage);
                                delayedMessage.extraHashMap.put(str2 + "_o", file2);
                                delayedMessage.extraHashMap.put(messageObject, str2);
                                delayedMessage.extraHashMap.put(str2, inputMedia2);
                                FileLoader.getInstance().uploadFile(str2, false, true, 16777216);
                            }
                        } else {
                            TLRPC$TL_messages_sendEncryptedMultiMedia tLRPC$TL_messages_sendEncryptedMultiMedia = (TLRPC$TL_messages_sendEncryptedMultiMedia) delayedMessage.sendEncryptedRequest;
                            putToDelayedMessages(file2, delayedMessage);
                            delayedMessage.extraHashMap.put(messageObject, file2);
                            delayedMessage.extraHashMap.put(file2, tLRPC$TL_messages_sendEncryptedMultiMedia.files.get(i));
                            delayedMessage.extraHashMap.put(file2 + "_i", messageObject);
                            if (delayedMessage.location != null) {
                                delayedMessage.extraHashMap.put(file2 + "_t", delayedMessage.location);
                            }
                            if (messageObject.videoEditedInfo == null || !messageObject.videoEditedInfo.needConvert()) {
                                FileLoader.getInstance().uploadFile(file2, true, false, ConnectionsManager.FileTypeVideo);
                            } else {
                                FileLoader.getInstance().uploadFile(file2, true, false, document2.size, ConnectionsManager.FileTypeVideo);
                            }
                        }
                    }
                    delayedMessage.videoEditedInfo = null;
                    delayedMessage.location = null;
                } else if (delayedMessage.httpLocation != null) {
                    putToDelayedMessages(delayedMessage.httpLocation, delayedMessage);
                    delayedMessage.extraHashMap.put(messageObject, delayedMessage.httpLocation);
                    delayedMessage.extraHashMap.put(delayedMessage.httpLocation, messageObject);
                    ImageLoader.getInstance().loadHttpFile(delayedMessage.httpLocation, "file");
                    delayedMessage.httpLocation = null;
                } else {
                    Object obj;
                    if (delayedMessage.sendRequest != null) {
                        obj = ((TLRPC$TL_inputSingleMedia) ((TLRPC$TL_messages_sendMultiMedia) delayedMessage.sendRequest).multi_media.get(i)).media;
                    } else {
                        TLObject tLObject = (TLObject) ((TLRPC$TL_messages_sendEncryptedMultiMedia) delayedMessage.sendEncryptedRequest).files.get(i);
                    }
                    String str3 = FileLoader.getInstance().getDirectory(4) + "/" + delayedMessage.location.volume_id + "_" + delayedMessage.location.local_id + ".jpg";
                    putToDelayedMessages(str3, delayedMessage);
                    delayedMessage.extraHashMap.put(str3, obj);
                    delayedMessage.extraHashMap.put(messageObject, str3);
                    FileLoader.getInstance().uploadFile(str3, delayedMessage.sendEncryptedRequest != null, true, 16777216);
                    delayedMessage.location = null;
                }
                delayedMessage.upload = false;
            } else if (!delayedMessage.messageObjects.isEmpty()) {
                putToSendingMessages(((MessageObject) delayedMessage.messageObjects.get(delayedMessage.messageObjects.size() - 1)).messageOwner);
            }
            sendReadyToSendGroup(delayedMessage, z3, true);
        }
    }

    private void performSendMessageRequest(TLObject tLObject, MessageObject messageObject, String str) {
        performSendMessageRequest(tLObject, messageObject, str, null, false);
    }

    private void performSendMessageRequest(TLObject tLObject, MessageObject messageObject, String str, DelayedMessage delayedMessage, boolean z) {
        if (z) {
            DelayedMessage findMaxDelayedMessageForMessageId = findMaxDelayedMessageForMessageId(messageObject.getId(), messageObject.getDialogId());
            if (findMaxDelayedMessageForMessageId != null) {
                findMaxDelayedMessageForMessageId.addDelayedRequest(tLObject, messageObject, str);
                if (delayedMessage != null && delayedMessage.requests != null) {
                    findMaxDelayedMessageForMessageId.requests.addAll(delayedMessage.requests);
                    return;
                }
                return;
            }
        }
        final Message message = messageObject.messageOwner;
        putToSendingMessages(message);
        final TLObject tLObject2 = tLObject;
        final MessageObject messageObject2 = messageObject;
        final String str2 = str;
        ConnectionsManager.getInstance().sendRequest(tLObject, new RequestDelegate() {
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        boolean z;
                        if (tLRPC$TL_error == null) {
                            ArrayList arrayList;
                            int i;
                            int i2 = message.id;
                            boolean z2 = tLObject2 instanceof TLRPC$TL_messages_sendBroadcast;
                            ArrayList arrayList2 = new ArrayList();
                            String str = message.attachPath;
                            Message message;
                            if (tLObject instanceof TLRPC$TL_updateShortSentMessage) {
                                final TLRPC$TL_updateShortSentMessage tLRPC$TL_updateShortSentMessage = (TLRPC$TL_updateShortSentMessage) tLObject;
                                Message message2 = message;
                                message = message;
                                int i3 = tLRPC$TL_updateShortSentMessage.id;
                                message.id = i3;
                                message2.local_id = i3;
                                message.date = tLRPC$TL_updateShortSentMessage.date;
                                message.entities = tLRPC$TL_updateShortSentMessage.entities;
                                message.out = tLRPC$TL_updateShortSentMessage.out;
                                if (tLRPC$TL_updateShortSentMessage.media != null) {
                                    message.media = tLRPC$TL_updateShortSentMessage.media;
                                    message2 = message;
                                    message2.flags |= 512;
                                }
                                if ((tLRPC$TL_updateShortSentMessage.media instanceof TLRPC$TL_messageMediaGame) && !TextUtils.isEmpty(tLRPC$TL_updateShortSentMessage.message)) {
                                    message.message = tLRPC$TL_updateShortSentMessage.message;
                                }
                                if (!message.entities.isEmpty()) {
                                    message2 = message;
                                    message2.flags |= 128;
                                }
                                Utilities.stageQueue.postRunnable(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().processNewDifferenceParams(-1, tLRPC$TL_updateShortSentMessage.pts, tLRPC$TL_updateShortSentMessage.date, tLRPC$TL_updateShortSentMessage.pts_count);
                                    }
                                });
                                arrayList2.add(message);
                                z = false;
                            } else if (tLObject instanceof TLRPC$Updates) {
                                boolean z3;
                                final TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                                arrayList = ((TLRPC$Updates) tLObject).updates;
                                i = 0;
                                while (i < arrayList.size()) {
                                    TLRPC$Update tLRPC$Update = (TLRPC$Update) arrayList.get(i);
                                    Message message3;
                                    if (tLRPC$Update instanceof TLRPC$TL_updateNewMessage) {
                                        final TLRPC$TL_updateNewMessage tLRPC$TL_updateNewMessage = (TLRPC$TL_updateNewMessage) tLRPC$Update;
                                        message3 = tLRPC$TL_updateNewMessage.message;
                                        arrayList2.add(message3);
                                        Utilities.stageQueue.postRunnable(new Runnable() {
                                            public void run() {
                                                MessagesController.getInstance().processNewDifferenceParams(-1, tLRPC$TL_updateNewMessage.pts, -1, tLRPC$TL_updateNewMessage.pts_count);
                                            }
                                        });
                                        arrayList.remove(i);
                                        message = message3;
                                        break;
                                    } else if (tLRPC$Update instanceof TLRPC$TL_updateNewChannelMessage) {
                                        final TLRPC$TL_updateNewChannelMessage tLRPC$TL_updateNewChannelMessage = (TLRPC$TL_updateNewChannelMessage) tLRPC$Update;
                                        message3 = tLRPC$TL_updateNewChannelMessage.message;
                                        arrayList2.add(message3);
                                        if ((message.flags & Integer.MIN_VALUE) != 0) {
                                            Message message4 = tLRPC$TL_updateNewChannelMessage.message;
                                            message4.flags |= Integer.MIN_VALUE;
                                        }
                                        Utilities.stageQueue.postRunnable(new Runnable() {
                                            public void run() {
                                                MessagesController.getInstance().processNewChannelDifferenceParams(tLRPC$TL_updateNewChannelMessage.pts, tLRPC$TL_updateNewChannelMessage.pts_count, tLRPC$TL_updateNewChannelMessage.message.to_id.channel_id);
                                            }
                                        });
                                        arrayList.remove(i);
                                        message = message3;
                                    } else {
                                        i++;
                                    }
                                }
                                message = null;
                                if (message != null) {
                                    Integer num = (Integer) MessagesController.getInstance().dialogs_read_outbox_max.get(Long.valueOf(message.dialog_id));
                                    if (num == null) {
                                        num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, message.dialog_id));
                                        MessagesController.getInstance().dialogs_read_outbox_max.put(Long.valueOf(message.dialog_id), num);
                                    }
                                    message.unread = num.intValue() < message.id;
                                    message.id = message.id;
                                    SendMessagesHelper.this.updateMediaPaths(messageObject2, message, str2, false);
                                    z3 = false;
                                } else {
                                    z3 = true;
                                }
                                Utilities.stageQueue.postRunnable(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().processUpdates(tLRPC$Updates, false);
                                    }
                                });
                                z = z3;
                            } else {
                                z = false;
                            }
                            if (MessageObject.isLiveLocationMessage(message)) {
                                LocationController.getInstance().addSharingLocation(message.dialog_id, message.id, message.media.period, message);
                            }
                            if (!z) {
                                StatsController.getInstance().incrementSentItemsCount(ConnectionsManager.getCurrentNetworkType(), 1, 1);
                                message.send_state = 0;
                                NotificationCenter instance = NotificationCenter.getInstance();
                                i = NotificationCenter.messageReceivedByServer;
                                Object[] objArr = new Object[4];
                                objArr[0] = Integer.valueOf(i2);
                                objArr[1] = Integer.valueOf(z2 ? i2 : message.id);
                                objArr[2] = message;
                                objArr[3] = Long.valueOf(message.dialog_id);
                                instance.postNotificationName(i, objArr);
                                i = i2;
                                final boolean z4 = z2;
                                arrayList = arrayList2;
                                final String str2 = str;
                                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                    /* renamed from: org.telegram.messenger.SendMessagesHelper$13$1$5$1 */
                                    class C33741 implements Runnable {
                                        C33741() {
                                        }

                                        public void run() {
                                            if (z4) {
                                                for (int i = 0; i < arrayList.size(); i++) {
                                                    Message message = (Message) arrayList.get(i);
                                                    ArrayList arrayList = new ArrayList();
                                                    MessageObject messageObject = new MessageObject(message, null, false);
                                                    arrayList.add(messageObject);
                                                    MessagesController.getInstance().updateInterfaceWithMessages(messageObject.getDialogId(), arrayList, true);
                                                }
                                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                            }
                                            SearchQuery.increasePeerRaiting(message.dialog_id);
                                            NotificationCenter instance = NotificationCenter.getInstance();
                                            int i2 = NotificationCenter.messageReceivedByServer;
                                            Object[] objArr = new Object[4];
                                            objArr[0] = Integer.valueOf(i);
                                            objArr[1] = Integer.valueOf(z4 ? i : message.id);
                                            objArr[2] = message;
                                            objArr[3] = Long.valueOf(message.dialog_id);
                                            instance.postNotificationName(i2, objArr);
                                            SendMessagesHelper.this.processSentMessage(i);
                                            SendMessagesHelper.this.removeFromSendingMessages(i);
                                        }
                                    }

                                    public void run() {
                                        MessagesStorage.getInstance().updateMessageStateAndId(message.random_id, Integer.valueOf(i), z4 ? i : message.id, 0, false, message.to_id.channel_id);
                                        MessagesStorage.getInstance().putMessages(arrayList, true, false, z4, 0);
                                        if (z4) {
                                            ArrayList arrayList = new ArrayList();
                                            arrayList.add(message);
                                            MessagesStorage.getInstance().putMessages(arrayList, true, false, false, 0);
                                        }
                                        AndroidUtilities.runOnUIThread(new C33741());
                                        if (MessageObject.isVideoMessage(message) || MessageObject.isRoundVideoMessage(message) || MessageObject.isNewGifMessage(message)) {
                                            SendMessagesHelper.this.stopVideoService(str2);
                                        }
                                    }
                                });
                            }
                        } else {
                            AlertsCreator.processError(tLRPC$TL_error, null, tLObject2, new Object[0]);
                            z = true;
                        }
                        if (z) {
                            MessagesStorage.getInstance().markMessageAsSendError(message);
                            message.send_state = 2;
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(message.id));
                            SendMessagesHelper.this.processSentMessage(message.id);
                            if (MessageObject.isVideoMessage(message) || MessageObject.isRoundVideoMessage(message) || MessageObject.isNewGifMessage(message)) {
                                SendMessagesHelper.this.stopVideoService(message.attachPath);
                            }
                            SendMessagesHelper.this.removeFromSendingMessages(message.id);
                        }
                    }
                });
            }
        }, new QuickAckDelegate() {
            public void run() {
                final int i = message.id;
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        message.send_state = 0;
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageReceivedByAck, Integer.valueOf(i));
                    }
                });
            }
        }, (tLObject instanceof TLRPC$TL_messages_sendMessage ? 128 : 0) | 68);
        if (delayedMessage != null) {
            delayedMessage.sendDelayedRequests();
        }
    }

    private void performSendMessageRequestMulti(final TLRPC$TL_messages_sendMultiMedia tLRPC$TL_messages_sendMultiMedia, final ArrayList<MessageObject> arrayList, final ArrayList<String> arrayList2) {
        for (int i = 0; i < arrayList.size(); i++) {
            putToSendingMessages(((MessageObject) arrayList.get(i)).messageOwner);
        }
        ConnectionsManager.getInstance().sendRequest((TLObject) tLRPC$TL_messages_sendMultiMedia, new RequestDelegate() {
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        Object obj;
                        if (tLRPC$TL_error == null) {
                            HashMap hashMap = new HashMap();
                            HashMap hashMap2 = new HashMap();
                            final TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                            ArrayList arrayList = ((TLRPC$Updates) tLObject).updates;
                            int i = 0;
                            while (i < arrayList.size()) {
                                TLRPC$Update tLRPC$Update = (TLRPC$Update) arrayList.get(i);
                                if (tLRPC$Update instanceof TLRPC$TL_updateMessageID) {
                                    hashMap2.put(Long.valueOf(tLRPC$Update.random_id), Integer.valueOf(((TLRPC$TL_updateMessageID) tLRPC$Update).id));
                                    arrayList.remove(i);
                                    i--;
                                } else if (tLRPC$Update instanceof TLRPC$TL_updateNewMessage) {
                                    final TLRPC$TL_updateNewMessage tLRPC$TL_updateNewMessage = (TLRPC$TL_updateNewMessage) tLRPC$Update;
                                    hashMap.put(Integer.valueOf(tLRPC$TL_updateNewMessage.message.id), tLRPC$TL_updateNewMessage.message);
                                    Utilities.stageQueue.postRunnable(new Runnable() {
                                        public void run() {
                                            MessagesController.getInstance().processNewDifferenceParams(-1, tLRPC$TL_updateNewMessage.pts, -1, tLRPC$TL_updateNewMessage.pts_count);
                                        }
                                    });
                                    arrayList.remove(i);
                                    i--;
                                } else if (tLRPC$Update instanceof TLRPC$TL_updateNewChannelMessage) {
                                    final TLRPC$TL_updateNewChannelMessage tLRPC$TL_updateNewChannelMessage = (TLRPC$TL_updateNewChannelMessage) tLRPC$Update;
                                    hashMap.put(Integer.valueOf(tLRPC$TL_updateNewChannelMessage.message.id), tLRPC$TL_updateNewChannelMessage.message);
                                    Utilities.stageQueue.postRunnable(new Runnable() {
                                        public void run() {
                                            MessagesController.getInstance().processNewChannelDifferenceParams(tLRPC$TL_updateNewChannelMessage.pts, tLRPC$TL_updateNewChannelMessage.pts_count, tLRPC$TL_updateNewChannelMessage.message.to_id.channel_id);
                                        }
                                    });
                                    arrayList.remove(i);
                                    i--;
                                }
                                i++;
                            }
                            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                MessageObject messageObject = (MessageObject) arrayList.get(i2);
                                String str = (String) arrayList2.get(i2);
                                final Message message = messageObject.messageOwner;
                                final int i3 = message.id;
                                final ArrayList arrayList2 = new ArrayList();
                                String str2 = message.attachPath;
                                Integer num = (Integer) hashMap2.get(Long.valueOf(message.random_id));
                                if (num == null) {
                                    obj = 1;
                                    break;
                                }
                                Message message2 = (Message) hashMap.get(num);
                                if (message2 == null) {
                                    obj = 1;
                                    break;
                                }
                                arrayList2.add(message2);
                                message.id = message2.id;
                                if ((message.flags & Integer.MIN_VALUE) != 0) {
                                    message2.flags |= Integer.MIN_VALUE;
                                }
                                Integer num2 = (Integer) MessagesController.getInstance().dialogs_read_outbox_max.get(Long.valueOf(message2.dialog_id));
                                if (num2 == null) {
                                    num2 = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message2.out, message2.dialog_id));
                                    MessagesController.getInstance().dialogs_read_outbox_max.put(Long.valueOf(message2.dialog_id), num2);
                                }
                                message2.unread = num2.intValue() < message2.id;
                                SendMessagesHelper.this.updateMediaPaths(messageObject, message2, str, false);
                                StatsController.getInstance().incrementSentItemsCount(ConnectionsManager.getCurrentNetworkType(), 1, 1);
                                message.send_state = 0;
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageReceivedByServer, Integer.valueOf(i3), Integer.valueOf(message.id), message, Long.valueOf(message.dialog_id));
                                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                    /* renamed from: org.telegram.messenger.SendMessagesHelper$12$1$3$1 */
                                    class C33661 implements Runnable {
                                        C33661() {
                                        }

                                        public void run() {
                                            SearchQuery.increasePeerRaiting(message.dialog_id);
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageReceivedByServer, Integer.valueOf(i3), Integer.valueOf(message.id), message, Long.valueOf(message.dialog_id));
                                            SendMessagesHelper.this.processSentMessage(i3);
                                            SendMessagesHelper.this.removeFromSendingMessages(i3);
                                        }
                                    }

                                    public void run() {
                                        MessagesStorage.getInstance().updateMessageStateAndId(message.random_id, Integer.valueOf(i3), message.id, 0, false, message.to_id.channel_id);
                                        MessagesStorage.getInstance().putMessages(arrayList2, true, false, false, 0);
                                        AndroidUtilities.runOnUIThread(new C33661());
                                    }
                                });
                            }
                            obj = null;
                            Utilities.stageQueue.postRunnable(new Runnable() {
                                public void run() {
                                    MessagesController.getInstance().processUpdates(tLRPC$Updates, false);
                                }
                            });
                        } else {
                            AlertsCreator.processError(tLRPC$TL_error, null, tLRPC$TL_messages_sendMultiMedia, new Object[0]);
                            obj = 1;
                        }
                        if (obj != null) {
                            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                                Message message3 = ((MessageObject) arrayList.get(i4)).messageOwner;
                                MessagesStorage.getInstance().markMessageAsSendError(message3);
                                message3.send_state = 2;
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(message3.id));
                                SendMessagesHelper.this.processSentMessage(message3.id);
                                SendMessagesHelper.this.removeFromSendingMessages(message3.id);
                            }
                        }
                    }
                });
            }
        }, null, 68);
    }

    public static void prepareSendingAudioDocuments(final ArrayList<MessageObject> arrayList, final long j, final MessageObject messageObject) {
        new Thread(new Runnable() {
            public void run() {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    final MessageObject messageObject = (MessageObject) arrayList.get(i);
                    String str = messageObject.messageOwner.attachPath;
                    File file = new File(str);
                    Object obj = ((int) j) == 0 ? 1 : null;
                    String str2 = str != null ? str + MimeTypes.BASE_TYPE_AUDIO + file.length() : str;
                    TLRPC$TL_document tLRPC$TL_document = null;
                    if (obj == null) {
                        tLRPC$TL_document = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(str2, obj == null ? 1 : 4);
                    }
                    if (tLRPC$TL_document == null) {
                        tLRPC$TL_document = (TLRPC$TL_document) messageObject.messageOwner.media.document;
                    }
                    if (obj != null) {
                        if (MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (j >> 32))) == null) {
                            return;
                        }
                    }
                    final HashMap hashMap = new HashMap();
                    if (str2 != null) {
                        hashMap.put("originalPath", str2);
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_document, null, messageObject.messageOwner.attachPath, j, messageObject, null, hashMap, 0);
                        }
                    });
                }
            }
        }).start();
    }

    public static void prepareSendingBotContextResult(BotInlineResult botInlineResult, HashMap<String, String> hashMap, long j, MessageObject messageObject) {
        if (botInlineResult != null) {
            if (botInlineResult.send_message instanceof TL_botInlineMessageMediaAuto) {
                final BotInlineResult botInlineResult2 = botInlineResult;
                final long j2 = j;
                final HashMap<String, String> hashMap2 = hashMap;
                final MessageObject messageObject2 = messageObject;
                new Thread(new Runnable() {
                    public void run() {
                        TLRPC$TL_document tLRPC$TL_document;
                        TLRPC$TL_game tLRPC$TL_game;
                        TLRPC$TL_photo tLRPC$TL_photo;
                        String str;
                        if (!(botInlineResult2 instanceof TL_botInlineMediaResult)) {
                            if (botInlineResult2.content_url != null) {
                                File file = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(botInlineResult2.content_url) + "." + ImageLoader.getHttpUrlExtension(botInlineResult2.content_url, "file"));
                                String absolutePath = file.exists() ? file.getAbsolutePath() : botInlineResult2.content_url;
                                String str2 = botInlineResult2.type;
                                int i = -1;
                                switch (str2.hashCode()) {
                                    case -1890252483:
                                        if (str2.equals("sticker")) {
                                            i = 4;
                                            break;
                                        }
                                        break;
                                    case 102340:
                                        if (str2.equals("gif")) {
                                            i = 5;
                                            break;
                                        }
                                        break;
                                    case 3143036:
                                        if (str2.equals("file")) {
                                            i = 2;
                                            break;
                                        }
                                        break;
                                    case 93166550:
                                        if (str2.equals(MimeTypes.BASE_TYPE_AUDIO)) {
                                            i = 0;
                                            break;
                                        }
                                        break;
                                    case 106642994:
                                        if (str2.equals("photo")) {
                                            i = 6;
                                            break;
                                        }
                                        break;
                                    case 112202875:
                                        if (str2.equals(MimeTypes.BASE_TYPE_VIDEO)) {
                                            i = 3;
                                            break;
                                        }
                                        break;
                                    case 112386354:
                                        if (str2.equals("voice")) {
                                            boolean z = true;
                                            break;
                                        }
                                        break;
                                }
                                switch (i) {
                                    case 0:
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                    case 5:
                                        tLRPC$TL_document = new TLRPC$TL_document();
                                        tLRPC$TL_document.id = 0;
                                        tLRPC$TL_document.size = 0;
                                        tLRPC$TL_document.dc_id = 0;
                                        tLRPC$TL_document.mime_type = botInlineResult2.content_type;
                                        tLRPC$TL_document.date = ConnectionsManager.getInstance().getCurrentTime();
                                        TLRPC$TL_documentAttributeFilename tLRPC$TL_documentAttributeFilename = new TLRPC$TL_documentAttributeFilename();
                                        tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeFilename);
                                        String str3 = botInlineResult2.type;
                                        int i2 = -1;
                                        switch (str3.hashCode()) {
                                            case -1890252483:
                                                if (str3.equals("sticker")) {
                                                    i2 = 5;
                                                    break;
                                                }
                                                break;
                                            case 102340:
                                                if (str3.equals("gif")) {
                                                    i2 = 0;
                                                    break;
                                                }
                                                break;
                                            case 3143036:
                                                if (str3.equals("file")) {
                                                    i2 = 3;
                                                    break;
                                                }
                                                break;
                                            case 93166550:
                                                if (str3.equals(MimeTypes.BASE_TYPE_AUDIO)) {
                                                    i2 = 2;
                                                    break;
                                                }
                                                break;
                                            case 112202875:
                                                if (str3.equals(MimeTypes.BASE_TYPE_VIDEO)) {
                                                    i2 = 4;
                                                    break;
                                                }
                                                break;
                                            case 112386354:
                                                if (str3.equals("voice")) {
                                                    boolean z2 = true;
                                                    break;
                                                }
                                                break;
                                        }
                                        Bitmap createVideoThumbnail;
                                        TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio;
                                        switch (i2) {
                                            case 0:
                                                tLRPC$TL_documentAttributeFilename.file_name = "animation.gif";
                                                if (absolutePath.endsWith("mp4")) {
                                                    tLRPC$TL_document.mime_type = MimeTypes.VIDEO_MP4;
                                                    tLRPC$TL_document.attributes.add(new TLRPC$TL_documentAttributeAnimated());
                                                } else {
                                                    tLRPC$TL_document.mime_type = "image/gif";
                                                }
                                                try {
                                                    createVideoThumbnail = absolutePath.endsWith("mp4") ? ThumbnailUtils.createVideoThumbnail(absolutePath, 1) : ImageLoader.loadBitmap(absolutePath, null, 90.0f, 90.0f, true);
                                                    if (createVideoThumbnail != null) {
                                                        tLRPC$TL_document.thumb = ImageLoader.scaleAndSaveImage(createVideoThumbnail, 90.0f, 90.0f, 55, false);
                                                        createVideoThumbnail.recycle();
                                                        break;
                                                    }
                                                } catch (Throwable th) {
                                                    FileLog.m13728e(th);
                                                    break;
                                                }
                                                break;
                                            case 1:
                                                tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
                                                tLRPC$TL_documentAttributeAudio.duration = botInlineResult2.duration;
                                                tLRPC$TL_documentAttributeAudio.voice = true;
                                                tLRPC$TL_documentAttributeFilename.file_name = "audio.ogg";
                                                tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeAudio);
                                                tLRPC$TL_document.thumb = new TLRPC$TL_photoSizeEmpty();
                                                tLRPC$TL_document.thumb.type = "s";
                                                break;
                                            case 2:
                                                tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
                                                tLRPC$TL_documentAttributeAudio.duration = botInlineResult2.duration;
                                                tLRPC$TL_documentAttributeAudio.title = botInlineResult2.title;
                                                tLRPC$TL_documentAttributeAudio.flags |= 1;
                                                if (botInlineResult2.description != null) {
                                                    tLRPC$TL_documentAttributeAudio.performer = botInlineResult2.description;
                                                    tLRPC$TL_documentAttributeAudio.flags |= 2;
                                                }
                                                tLRPC$TL_documentAttributeFilename.file_name = "audio.mp3";
                                                tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeAudio);
                                                tLRPC$TL_document.thumb = new TLRPC$TL_photoSizeEmpty();
                                                tLRPC$TL_document.thumb.type = "s";
                                                break;
                                            case 3:
                                                i2 = botInlineResult2.content_type.indexOf(47);
                                                if (i2 == -1) {
                                                    tLRPC$TL_documentAttributeFilename.file_name = "file";
                                                    break;
                                                } else {
                                                    tLRPC$TL_documentAttributeFilename.file_name = "file." + botInlineResult2.content_type.substring(i2 + 1);
                                                    break;
                                                }
                                            case 4:
                                                tLRPC$TL_documentAttributeFilename.file_name = "video.mp4";
                                                TLRPC$TL_documentAttributeVideo tLRPC$TL_documentAttributeVideo = new TLRPC$TL_documentAttributeVideo();
                                                tLRPC$TL_documentAttributeVideo.w = botInlineResult2.f10135w;
                                                tLRPC$TL_documentAttributeVideo.h = botInlineResult2.f10134h;
                                                tLRPC$TL_documentAttributeVideo.duration = botInlineResult2.duration;
                                                tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeVideo);
                                                try {
                                                    createVideoThumbnail = ImageLoader.loadBitmap(new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(botInlineResult2.thumb_url) + "." + ImageLoader.getHttpUrlExtension(botInlineResult2.thumb_url, "jpg")).getAbsolutePath(), null, 90.0f, 90.0f, true);
                                                    if (createVideoThumbnail != null) {
                                                        tLRPC$TL_document.thumb = ImageLoader.scaleAndSaveImage(createVideoThumbnail, 90.0f, 90.0f, 55, false);
                                                        createVideoThumbnail.recycle();
                                                        break;
                                                    }
                                                } catch (Throwable th2) {
                                                    FileLog.m13728e(th2);
                                                    break;
                                                }
                                                break;
                                            case 5:
                                                TLRPC$TL_documentAttributeSticker tLRPC$TL_documentAttributeSticker = new TLRPC$TL_documentAttributeSticker();
                                                tLRPC$TL_documentAttributeSticker.alt = TtmlNode.ANONYMOUS_REGION_ID;
                                                tLRPC$TL_documentAttributeSticker.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                                tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeSticker);
                                                TLRPC$TL_documentAttributeImageSize tLRPC$TL_documentAttributeImageSize = new TLRPC$TL_documentAttributeImageSize();
                                                tLRPC$TL_documentAttributeImageSize.w = botInlineResult2.f10135w;
                                                tLRPC$TL_documentAttributeImageSize.h = botInlineResult2.f10134h;
                                                tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeImageSize);
                                                tLRPC$TL_documentAttributeFilename.file_name = "sticker.webp";
                                                try {
                                                    createVideoThumbnail = ImageLoader.loadBitmap(new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(botInlineResult2.thumb_url) + "." + ImageLoader.getHttpUrlExtension(botInlineResult2.thumb_url, "webp")).getAbsolutePath(), null, 90.0f, 90.0f, true);
                                                    if (createVideoThumbnail != null) {
                                                        tLRPC$TL_document.thumb = ImageLoader.scaleAndSaveImage(createVideoThumbnail, 90.0f, 90.0f, 55, false);
                                                        createVideoThumbnail.recycle();
                                                        break;
                                                    }
                                                } catch (Throwable th22) {
                                                    FileLog.m13728e(th22);
                                                    break;
                                                }
                                                break;
                                        }
                                        if (tLRPC$TL_documentAttributeFilename.file_name == null) {
                                            tLRPC$TL_documentAttributeFilename.file_name = "file";
                                        }
                                        if (tLRPC$TL_document.mime_type == null) {
                                            tLRPC$TL_document.mime_type = "application/octet-stream";
                                        }
                                        if (tLRPC$TL_document.thumb != null) {
                                            tLRPC$TL_game = null;
                                            tLRPC$TL_photo = null;
                                            str = absolutePath;
                                            break;
                                        }
                                        tLRPC$TL_document.thumb = new TLRPC$TL_photoSize();
                                        tLRPC$TL_document.thumb.f10147w = botInlineResult2.f10135w;
                                        tLRPC$TL_document.thumb.f10146h = botInlineResult2.f10134h;
                                        tLRPC$TL_document.thumb.size = 0;
                                        tLRPC$TL_document.thumb.location = new TLRPC$TL_fileLocationUnavailable();
                                        tLRPC$TL_document.thumb.type = "x";
                                        tLRPC$TL_game = null;
                                        tLRPC$TL_photo = null;
                                        str = absolutePath;
                                        break;
                                    case 6:
                                        tLRPC$TL_photo = file.exists() ? SendMessagesHelper.getInstance().generatePhotoSizes(absolutePath, null) : null;
                                        if (tLRPC$TL_photo == null) {
                                            tLRPC$TL_photo = new TLRPC$TL_photo();
                                            tLRPC$TL_photo.date = ConnectionsManager.getInstance().getCurrentTime();
                                            TLRPC$TL_photoSize tLRPC$TL_photoSize = new TLRPC$TL_photoSize();
                                            tLRPC$TL_photoSize.w = botInlineResult2.f10135w;
                                            tLRPC$TL_photoSize.h = botInlineResult2.f10134h;
                                            tLRPC$TL_photoSize.size = 1;
                                            tLRPC$TL_photoSize.location = new TLRPC$TL_fileLocationUnavailable();
                                            tLRPC$TL_photoSize.type = "x";
                                            tLRPC$TL_photo.sizes.add(tLRPC$TL_photoSize);
                                        }
                                        tLRPC$TL_game = null;
                                        tLRPC$TL_document = null;
                                        str = absolutePath;
                                        break;
                                    default:
                                        tLRPC$TL_game = null;
                                        tLRPC$TL_photo = null;
                                        tLRPC$TL_document = null;
                                        str = absolutePath;
                                        break;
                                }
                            }
                            tLRPC$TL_game = null;
                            tLRPC$TL_photo = null;
                            tLRPC$TL_document = null;
                            str = null;
                        } else if (botInlineResult2.type.equals("game")) {
                            if (((int) j2) != 0) {
                                tLRPC$TL_game = new TLRPC$TL_game();
                                tLRPC$TL_game.title = botInlineResult2.title;
                                tLRPC$TL_game.description = botInlineResult2.description;
                                tLRPC$TL_game.short_name = botInlineResult2.id;
                                tLRPC$TL_game.photo = botInlineResult2.photo;
                                if (botInlineResult2.document instanceof TLRPC$TL_document) {
                                    tLRPC$TL_game.document = botInlineResult2.document;
                                    tLRPC$TL_game.flags |= 1;
                                    tLRPC$TL_photo = null;
                                    tLRPC$TL_document = null;
                                    str = null;
                                } else {
                                    tLRPC$TL_photo = null;
                                    tLRPC$TL_document = null;
                                    str = null;
                                }
                            } else {
                                return;
                            }
                        } else if (botInlineResult2.document != null) {
                            if (botInlineResult2.document instanceof TLRPC$TL_document) {
                                tLRPC$TL_game = null;
                                tLRPC$TL_photo = null;
                                tLRPC$TL_document = (TLRPC$TL_document) botInlineResult2.document;
                                str = null;
                            }
                            tLRPC$TL_game = null;
                            tLRPC$TL_photo = null;
                            tLRPC$TL_document = null;
                            str = null;
                        } else {
                            if (botInlineResult2.photo != null && (botInlineResult2.photo instanceof TLRPC$TL_photo)) {
                                tLRPC$TL_game = null;
                                tLRPC$TL_photo = (TLRPC$TL_photo) botInlineResult2.photo;
                                tLRPC$TL_document = null;
                                str = null;
                            }
                            tLRPC$TL_game = null;
                            tLRPC$TL_photo = null;
                            tLRPC$TL_document = null;
                            str = null;
                        }
                        if (!(hashMap2 == null || botInlineResult2.content_url == null)) {
                            hashMap2.put("originalPath", botInlineResult2.content_url);
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (tLRPC$TL_document != null) {
                                    tLRPC$TL_document.caption = botInlineResult2.send_message.caption;
                                    SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_document, null, str, j2, messageObject2, botInlineResult2.send_message.reply_markup, hashMap2, 0);
                                } else if (tLRPC$TL_photo != null) {
                                    tLRPC$TL_photo.caption = botInlineResult2.send_message.caption;
                                    SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_photo, botInlineResult2.content_url, j2, messageObject2, botInlineResult2.send_message.reply_markup, hashMap2, 0);
                                } else if (tLRPC$TL_game != null) {
                                    SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_game, j2, botInlineResult2.send_message.reply_markup, hashMap2);
                                }
                            }
                        });
                    }
                }).run();
            } else if (botInlineResult.send_message instanceof TL_botInlineMessageText) {
                TLRPC$WebPage tLRPC$WebPage = null;
                if (((int) j) == 0) {
                    for (int i = 0; i < botInlineResult.send_message.entities.size(); i++) {
                        MessageEntity messageEntity = (MessageEntity) botInlineResult.send_message.entities.get(i);
                        if (messageEntity instanceof TLRPC$TL_messageEntityUrl) {
                            tLRPC$WebPage = new TLRPC$TL_webPagePending();
                            tLRPC$WebPage.url = botInlineResult.send_message.message.substring(messageEntity.offset, messageEntity.length + messageEntity.offset);
                            break;
                        }
                    }
                }
                getInstance().sendMessage(botInlineResult.send_message.message, j, messageObject, tLRPC$WebPage, !botInlineResult.send_message.no_webpage, botInlineResult.send_message.entities, botInlineResult.send_message.reply_markup, (HashMap) hashMap);
            } else if (botInlineResult.send_message instanceof TL_botInlineMessageMediaVenue) {
                r1 = new TLRPC$TL_messageMediaVenue();
                r1.geo = botInlineResult.send_message.geo;
                r1.address = botInlineResult.send_message.address;
                r1.title = botInlineResult.send_message.title;
                r1.provider = botInlineResult.send_message.provider;
                r1.venue_id = botInlineResult.send_message.venue_id;
                r1.venue_type = TtmlNode.ANONYMOUS_REGION_ID;
                getInstance().sendMessage(r1, j, messageObject, botInlineResult.send_message.reply_markup, (HashMap) hashMap);
            } else if (botInlineResult.send_message instanceof TL_botInlineMessageMediaGeo) {
                if (botInlineResult.send_message.period != 0) {
                    r1 = new TLRPC$TL_messageMediaGeoLive();
                    r1.period = botInlineResult.send_message.period;
                    r1.geo = botInlineResult.send_message.geo;
                    getInstance().sendMessage(r1, j, messageObject, botInlineResult.send_message.reply_markup, (HashMap) hashMap);
                    return;
                }
                r1 = new TLRPC$TL_messageMediaGeo();
                r1.geo = botInlineResult.send_message.geo;
                getInstance().sendMessage(r1, j, messageObject, botInlineResult.send_message.reply_markup, (HashMap) hashMap);
            } else if (botInlineResult.send_message instanceof TL_botInlineMessageMediaContact) {
                User tLRPC$TL_user = new TLRPC$TL_user();
                tLRPC$TL_user.phone = botInlineResult.send_message.phone_number;
                tLRPC$TL_user.first_name = botInlineResult.send_message.first_name;
                tLRPC$TL_user.last_name = botInlineResult.send_message.last_name;
                getInstance().sendMessage(tLRPC$TL_user, j, messageObject, botInlineResult.send_message.reply_markup, (HashMap) hashMap);
            }
        }
    }

    public static void prepareSendingDocument(String str, String str2, Uri uri, String str3, long j, MessageObject messageObject, C0060e c0060e) {
        if ((str != null && str2 != null) || uri != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = null;
            if (uri != null) {
                arrayList3 = new ArrayList();
                arrayList3.add(uri);
            }
            if (str != null) {
                arrayList.add(str);
                arrayList2.add(str2);
            }
            prepareSendingDocuments(arrayList, arrayList2, arrayList3, str3, j, messageObject, c0060e);
        }
    }

    private static boolean prepareSendingDocumentInternal(String str, String str2, Uri uri, String str3, long j, MessageObject messageObject, CharSequence charSequence) {
        if ((str == null || str.length() == 0) && uri == null) {
            return false;
        }
        if (uri != null && AndroidUtilities.isInternalUri(uri)) {
            return false;
        }
        if (str != null && AndroidUtilities.isInternalUri(Uri.fromFile(new File(str)))) {
            return false;
        }
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        String str4 = null;
        if (uri != null) {
            Object obj = null;
            if (str3 != null) {
                str4 = singleton.getExtensionFromMimeType(str3);
            }
            if (str4 == null) {
                str4 = "txt";
            } else {
                obj = 1;
            }
            str = MediaController.copyFileToCache(uri, str4);
            if (str == null) {
                return false;
            }
            if (obj == null) {
                str4 = null;
            }
        }
        File file = new File(str);
        if (!file.exists() || file.length() == 0) {
            return false;
        }
        Object obj2;
        Object obj3;
        TLRPC$TL_document tLRPC$TL_document;
        TLRPC$TL_document tLRPC$TL_document2;
        TLRPC$TL_documentAttributeFilename tLRPC$TL_documentAttributeFilename;
        Bitmap loadBitmap;
        Options options;
        TLRPC$TL_document tLRPC$TL_document3;
        final HashMap hashMap;
        final String str5;
        final long j2;
        final MessageObject messageObject2;
        boolean z = ((int) j) == 0;
        Object obj4 = !z ? 1 : null;
        String name = file.getName();
        String str6 = TtmlNode.ANONYMOUS_REGION_ID;
        if (str4 == null) {
            int lastIndexOf = str.lastIndexOf(46);
            str4 = lastIndexOf != -1 ? str.substring(lastIndexOf + 1) : str6;
        }
        if (str4.toLowerCase().equals("mp3") || str4.toLowerCase().equals("m4a")) {
            AudioInfo audioInfo = AudioInfo.getAudioInfo(file);
            if (!(audioInfo == null || audioInfo.getDuration() == 0)) {
                TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
                tLRPC$TL_documentAttributeAudio.duration = (int) (audioInfo.getDuration() / 1000);
                tLRPC$TL_documentAttributeAudio.title = audioInfo.getTitle();
                tLRPC$TL_documentAttributeAudio.performer = audioInfo.getArtist();
                if (tLRPC$TL_documentAttributeAudio.title == null) {
                    tLRPC$TL_documentAttributeAudio.title = TtmlNode.ANONYMOUS_REGION_ID;
                }
                tLRPC$TL_documentAttributeAudio.flags |= 1;
                if (tLRPC$TL_documentAttributeAudio.performer == null) {
                    tLRPC$TL_documentAttributeAudio.performer = TtmlNode.ANONYMOUS_REGION_ID;
                }
                tLRPC$TL_documentAttributeAudio.flags |= 2;
                obj2 = tLRPC$TL_documentAttributeAudio;
                if (str2 == null) {
                    if (str2.endsWith("attheme")) {
                        obj3 = 1;
                    } else if (obj2 == null) {
                        str2 = str2 + MimeTypes.BASE_TYPE_AUDIO + file.length();
                        obj3 = null;
                    } else {
                        str2 = str2 + TtmlNode.ANONYMOUS_REGION_ID + file.length();
                        obj3 = null;
                    }
                } else {
                    obj3 = null;
                }
                tLRPC$TL_document = null;
                if (obj3 == null && !z) {
                    tLRPC$TL_document = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(str2, z ? 1 : 4);
                    if (!(tLRPC$TL_document != null || str.equals(str2) || z)) {
                        tLRPC$TL_document = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(str + file.length(), z ? 1 : 4);
                    }
                }
                if (tLRPC$TL_document != null) {
                    tLRPC$TL_document2 = new TLRPC$TL_document();
                    tLRPC$TL_document2.id = 0;
                    tLRPC$TL_document2.date = ConnectionsManager.getInstance().getCurrentTime();
                    tLRPC$TL_documentAttributeFilename = new TLRPC$TL_documentAttributeFilename();
                    tLRPC$TL_documentAttributeFilename.file_name = name;
                    tLRPC$TL_document2.attributes.add(tLRPC$TL_documentAttributeFilename);
                    tLRPC$TL_document2.size = (int) file.length();
                    tLRPC$TL_document2.dc_id = 0;
                    if (obj2 != null) {
                        tLRPC$TL_document2.attributes.add(obj2);
                    }
                    if (str4.length() != 0) {
                        tLRPC$TL_document2.mime_type = "application/octet-stream";
                    } else if (str4.toLowerCase().equals("webp")) {
                        str4 = singleton.getMimeTypeFromExtension(str4.toLowerCase());
                        if (str4 == null) {
                            tLRPC$TL_document2.mime_type = str4;
                        } else {
                            tLRPC$TL_document2.mime_type = "application/octet-stream";
                        }
                    } else {
                        tLRPC$TL_document2.mime_type = "image/webp";
                    }
                    if (tLRPC$TL_document2.mime_type.equals("image/gif")) {
                        try {
                            loadBitmap = ImageLoader.loadBitmap(file.getAbsolutePath(), null, 90.0f, 90.0f, true);
                            if (loadBitmap != null) {
                                tLRPC$TL_documentAttributeFilename.file_name = "animation.gif";
                                tLRPC$TL_document2.thumb = ImageLoader.scaleAndSaveImage(loadBitmap, 90.0f, 90.0f, 55, z);
                                loadBitmap.recycle();
                            }
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                    if (tLRPC$TL_document2.mime_type.equals("image/webp") && obj4 != null) {
                        options = new Options();
                        try {
                            options.inJustDecodeBounds = true;
                            RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
                            ByteBuffer map = randomAccessFile.getChannel().map(MapMode.READ_ONLY, 0, (long) str.length());
                            Utilities.loadWebpImage(null, map, map.limit(), options, true);
                            randomAccessFile.close();
                        } catch (Throwable e2) {
                            FileLog.m13728e(e2);
                        }
                        if (options.outWidth != 0 && options.outHeight != 0 && options.outWidth <= 800 && options.outHeight <= 800) {
                            TLRPC$TL_documentAttributeSticker tLRPC$TL_documentAttributeSticker = new TLRPC$TL_documentAttributeSticker();
                            tLRPC$TL_documentAttributeSticker.alt = TtmlNode.ANONYMOUS_REGION_ID;
                            tLRPC$TL_documentAttributeSticker.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                            tLRPC$TL_document2.attributes.add(tLRPC$TL_documentAttributeSticker);
                            TLRPC$TL_documentAttributeImageSize tLRPC$TL_documentAttributeImageSize = new TLRPC$TL_documentAttributeImageSize();
                            tLRPC$TL_documentAttributeImageSize.w = options.outWidth;
                            tLRPC$TL_documentAttributeImageSize.h = options.outHeight;
                            tLRPC$TL_document2.attributes.add(tLRPC$TL_documentAttributeImageSize);
                        }
                    }
                    if (tLRPC$TL_document2.thumb == null) {
                        tLRPC$TL_document2.thumb = new TLRPC$TL_photoSizeEmpty();
                        tLRPC$TL_document2.thumb.type = "s";
                    }
                    tLRPC$TL_document3 = tLRPC$TL_document2;
                } else {
                    tLRPC$TL_document3 = tLRPC$TL_document;
                }
                if (charSequence == null) {
                    tLRPC$TL_document3.caption = charSequence.toString();
                } else {
                    tLRPC$TL_document3.caption = TtmlNode.ANONYMOUS_REGION_ID;
                }
                hashMap = new HashMap();
                if (str2 != null) {
                    hashMap.put("originalPath", str2);
                }
                str5 = str;
                j2 = j;
                messageObject2 = messageObject;
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_document3, null, str5, j2, messageObject2, null, hashMap, 0);
                    }
                });
                return true;
            }
        }
        obj2 = null;
        if (str2 == null) {
            obj3 = null;
        } else {
            if (str2.endsWith("attheme")) {
                obj3 = 1;
            } else if (obj2 == null) {
                str2 = str2 + TtmlNode.ANONYMOUS_REGION_ID + file.length();
                obj3 = null;
            } else {
                str2 = str2 + MimeTypes.BASE_TYPE_AUDIO + file.length();
                obj3 = null;
            }
        }
        tLRPC$TL_document = null;
        if (z) {
        }
        tLRPC$TL_document = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(str2, z ? 1 : 4);
        if (z) {
        }
        tLRPC$TL_document = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(str + file.length(), z ? 1 : 4);
        if (tLRPC$TL_document != null) {
            tLRPC$TL_document3 = tLRPC$TL_document;
        } else {
            tLRPC$TL_document2 = new TLRPC$TL_document();
            tLRPC$TL_document2.id = 0;
            tLRPC$TL_document2.date = ConnectionsManager.getInstance().getCurrentTime();
            tLRPC$TL_documentAttributeFilename = new TLRPC$TL_documentAttributeFilename();
            tLRPC$TL_documentAttributeFilename.file_name = name;
            tLRPC$TL_document2.attributes.add(tLRPC$TL_documentAttributeFilename);
            tLRPC$TL_document2.size = (int) file.length();
            tLRPC$TL_document2.dc_id = 0;
            if (obj2 != null) {
                tLRPC$TL_document2.attributes.add(obj2);
            }
            if (str4.length() != 0) {
                tLRPC$TL_document2.mime_type = "application/octet-stream";
            } else if (str4.toLowerCase().equals("webp")) {
                str4 = singleton.getMimeTypeFromExtension(str4.toLowerCase());
                if (str4 == null) {
                    tLRPC$TL_document2.mime_type = "application/octet-stream";
                } else {
                    tLRPC$TL_document2.mime_type = str4;
                }
            } else {
                tLRPC$TL_document2.mime_type = "image/webp";
            }
            if (tLRPC$TL_document2.mime_type.equals("image/gif")) {
                loadBitmap = ImageLoader.loadBitmap(file.getAbsolutePath(), null, 90.0f, 90.0f, true);
                if (loadBitmap != null) {
                    tLRPC$TL_documentAttributeFilename.file_name = "animation.gif";
                    tLRPC$TL_document2.thumb = ImageLoader.scaleAndSaveImage(loadBitmap, 90.0f, 90.0f, 55, z);
                    loadBitmap.recycle();
                }
            }
            options = new Options();
            options.inJustDecodeBounds = true;
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(str, "r");
            ByteBuffer map2 = randomAccessFile2.getChannel().map(MapMode.READ_ONLY, 0, (long) str.length());
            Utilities.loadWebpImage(null, map2, map2.limit(), options, true);
            randomAccessFile2.close();
            TLRPC$TL_documentAttributeSticker tLRPC$TL_documentAttributeSticker2 = new TLRPC$TL_documentAttributeSticker();
            tLRPC$TL_documentAttributeSticker2.alt = TtmlNode.ANONYMOUS_REGION_ID;
            tLRPC$TL_documentAttributeSticker2.stickerset = new TLRPC$TL_inputStickerSetEmpty();
            tLRPC$TL_document2.attributes.add(tLRPC$TL_documentAttributeSticker2);
            TLRPC$TL_documentAttributeImageSize tLRPC$TL_documentAttributeImageSize2 = new TLRPC$TL_documentAttributeImageSize();
            tLRPC$TL_documentAttributeImageSize2.w = options.outWidth;
            tLRPC$TL_documentAttributeImageSize2.h = options.outHeight;
            tLRPC$TL_document2.attributes.add(tLRPC$TL_documentAttributeImageSize2);
            if (tLRPC$TL_document2.thumb == null) {
                tLRPC$TL_document2.thumb = new TLRPC$TL_photoSizeEmpty();
                tLRPC$TL_document2.thumb.type = "s";
            }
            tLRPC$TL_document3 = tLRPC$TL_document2;
        }
        if (charSequence == null) {
            tLRPC$TL_document3.caption = TtmlNode.ANONYMOUS_REGION_ID;
        } else {
            tLRPC$TL_document3.caption = charSequence.toString();
        }
        hashMap = new HashMap();
        if (str2 != null) {
            hashMap.put("originalPath", str2);
        }
        str5 = str;
        j2 = j;
        messageObject2 = messageObject;
        AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
        return true;
    }

    public static void prepareSendingDocuments(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<Uri> arrayList3, String str, long j, MessageObject messageObject, C0060e c0060e) {
        if (arrayList != null || arrayList2 != null || arrayList3 != null) {
            if (arrayList == null || arrayList2 == null || arrayList.size() == arrayList2.size()) {
                final ArrayList<String> arrayList4 = arrayList;
                final ArrayList<String> arrayList5 = arrayList2;
                final String str2 = str;
                final long j2 = j;
                final MessageObject messageObject2 = messageObject;
                final ArrayList<Uri> arrayList6 = arrayList3;
                final C0060e c0060e2 = c0060e;
                new Thread(new Runnable() {

                    /* renamed from: org.telegram.messenger.SendMessagesHelper$18$1 */
                    class C33791 implements Runnable {
                        C33791() {
                        }

                        public void run() {
                            try {
                                Toast.makeText(ApplicationLoader.applicationContext, LocaleController.getString("UnsupportedAttachment", R.string.UnsupportedAttachment), 0).show();
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                    }

                    public void run() {
                        Object obj;
                        if (arrayList4 != null) {
                            int i = 0;
                            obj = null;
                            while (i < arrayList4.size()) {
                                Object obj2 = !SendMessagesHelper.prepareSendingDocumentInternal((String) arrayList4.get(i), (String) arrayList5.get(i), null, str2, j2, messageObject2, null) ? 1 : obj;
                                i++;
                                obj = obj2;
                            }
                        } else {
                            obj = null;
                        }
                        if (arrayList6 != null) {
                            for (int i2 = 0; i2 < arrayList6.size(); i2++) {
                                if (!SendMessagesHelper.prepareSendingDocumentInternal(null, null, (Uri) arrayList6.get(i2), str2, j2, messageObject2, null)) {
                                    obj = 1;
                                }
                            }
                        }
                        if (c0060e2 != null) {
                            c0060e2.m154d();
                        }
                        if (obj != null) {
                            AndroidUtilities.runOnUIThread(new C33791());
                        }
                    }
                }).start();
            }
        }
    }

    public static void prepareSendingMedia(ArrayList<SendingMediaInfo> arrayList, long j, MessageObject messageObject, C0060e c0060e, boolean z, boolean z2) {
        if (!arrayList.isEmpty()) {
            final ArrayList<SendingMediaInfo> arrayList2 = arrayList;
            final long j2 = j;
            final boolean z3 = z;
            final boolean z4 = z2;
            final MessageObject messageObject2 = messageObject;
            final C0060e c0060e2 = c0060e;
            mediaSendQueue.postRunnable(new Runnable() {
                public void run() {
                    int peerLayerVersion;
                    HashMap hashMap;
                    int i;
                    final SendingMediaInfo sendingMediaInfo;
                    String str;
                    String str2;
                    File file;
                    TLRPC$TL_photo tLRPC$TL_photo;
                    final MediaSendPrepareWorker mediaSendPrepareWorker;
                    HashMap hashMap2;
                    long j;
                    ArrayList arrayList;
                    ArrayList arrayList2;
                    ArrayList arrayList3;
                    String str3;
                    int i2;
                    int i3;
                    long j2;
                    final SendingMediaInfo sendingMediaInfo2;
                    long j3;
                    final HashMap hashMap3;
                    Document document;
                    TLRPC$TL_document tLRPC$TL_document;
                    File file2;
                    TLRPC$TL_document tLRPC$TL_document2;
                    File file3;
                    File file4;
                    Bitmap createVideoThumbnail;
                    String str4;
                    final String file5;
                    int i4;
                    long j4;
                    boolean z;
                    TLRPC$TL_photo tLRPC$TL_photo2;
                    File file6;
                    final HashMap hashMap4;
                    String str5;
                    String str6;
                    Object obj;
                    ArrayList arrayList4;
                    ArrayList arrayList5;
                    ArrayList arrayList6;
                    TLRPC$TL_photo tLRPC$TL_photo3;
                    MediaSendPrepareWorker mediaSendPrepareWorker2;
                    final HashMap hashMap5;
                    boolean z2;
                    AbstractSerializedData serializedData;
                    int i5;
                    long j5;
                    Bitmap bitmap;
                    String str7;
                    File file7;
                    long j6;
                    boolean z3;
                    TLRPC$TL_document tLRPC$TL_document3;
                    Bitmap access$1900;
                    TLRPC$TL_documentAttributeVideo tLRPC$TL_documentAttributeVideo;
                    final HashMap hashMap6;
                    final SendingMediaInfo sendingMediaInfo3;
                    int i6;
                    long currentTimeMillis = System.currentTimeMillis();
                    int size = arrayList2.size();
                    boolean z4 = ((int) j2) == 0;
                    if (z4) {
                        EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (j2 >> 32)));
                        if (encryptedChat != null) {
                            peerLayerVersion = AndroidUtilities.getPeerLayerVersion(encryptedChat.layer);
                            if ((z4 || peerLayerVersion >= 73) && !z3 && z4) {
                                hashMap = new HashMap();
                                for (i = 0; i < size; i++) {
                                    sendingMediaInfo = (SendingMediaInfo) arrayList2.get(i);
                                    if (sendingMediaInfo.searchImage == null && !sendingMediaInfo.isVideo) {
                                        str = sendingMediaInfo.path;
                                        str2 = sendingMediaInfo.path;
                                        if (str2 == null && sendingMediaInfo.uri != null) {
                                            str2 = AndroidUtilities.getPath(sendingMediaInfo.uri);
                                            str = sendingMediaInfo.uri.toString();
                                        }
                                        if ((str2 == null || !(str2.endsWith(".gif") || str2.endsWith(".webp"))) && !(str2 == null && sendingMediaInfo.uri != null && (MediaController.isGif(sendingMediaInfo.uri) || MediaController.isWebp(sendingMediaInfo.uri)))) {
                                            if (str2 == null) {
                                                file = new File(str2);
                                                str = str + file.length() + "_" + file.lastModified();
                                            } else {
                                                str = null;
                                            }
                                            tLRPC$TL_photo = null;
                                            if (!z4 && sendingMediaInfo.ttl == 0) {
                                                tLRPC$TL_photo = (TLRPC$TL_photo) MessagesStorage.getInstance().getSentFile(str, z4 ? 0 : 3);
                                                if (tLRPC$TL_photo == null && sendingMediaInfo.uri != null) {
                                                    tLRPC$TL_photo = (TLRPC$TL_photo) MessagesStorage.getInstance().getSentFile(AndroidUtilities.getPath(sendingMediaInfo.uri), z4 ? 0 : 3);
                                                }
                                            }
                                            mediaSendPrepareWorker = new MediaSendPrepareWorker();
                                            hashMap.put(sendingMediaInfo, mediaSendPrepareWorker);
                                            if (tLRPC$TL_photo == null) {
                                                mediaSendPrepareWorker.photo = tLRPC$TL_photo;
                                            } else {
                                                mediaSendPrepareWorker.sync = new CountDownLatch(1);
                                                SendMessagesHelper.mediaSendThreadPool.execute(new Runnable() {
                                                    public void run() {
                                                        mediaSendPrepareWorker.photo = SendMessagesHelper.getInstance().generatePhotoSizes(sendingMediaInfo.path, sendingMediaInfo.uri);
                                                        mediaSendPrepareWorker.sync.countDown();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                                hashMap2 = hashMap;
                            } else {
                                hashMap2 = null;
                            }
                            j = 0;
                            arrayList = null;
                            arrayList2 = null;
                            arrayList3 = null;
                            str3 = null;
                            i2 = 0;
                            i3 = 0;
                            j2 = 0;
                            while (i3 < size) {
                                sendingMediaInfo2 = (SendingMediaInfo) arrayList2.get(i3);
                                if (z4 || ((z4 && peerLayerVersion < 73) || size <= 1 || r14 % 10 != 0)) {
                                    j3 = j2;
                                } else {
                                    j = Utilities.random.nextLong();
                                    i2 = 0;
                                    j3 = j;
                                }
                                if (sendingMediaInfo2.searchImage == null) {
                                    if (sendingMediaInfo2.searchImage.type != 1) {
                                        hashMap3 = new HashMap();
                                        if (sendingMediaInfo2.searchImage.document instanceof TLRPC$TL_document) {
                                            if (!z4) {
                                                document = (Document) MessagesStorage.getInstance().getSentFile(sendingMediaInfo2.searchImage.imageUrl, z4 ? 1 : 4);
                                                if (document instanceof TLRPC$TL_document) {
                                                    tLRPC$TL_document = (TLRPC$TL_document) document;
                                                    file2 = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(sendingMediaInfo2.searchImage.imageUrl) + "." + ImageLoader.getHttpUrlExtension(sendingMediaInfo2.searchImage.imageUrl, "jpg"));
                                                    tLRPC$TL_document2 = tLRPC$TL_document;
                                                    file3 = file2;
                                                }
                                            }
                                            tLRPC$TL_document = null;
                                            file2 = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(sendingMediaInfo2.searchImage.imageUrl) + "." + ImageLoader.getHttpUrlExtension(sendingMediaInfo2.searchImage.imageUrl, "jpg"));
                                            tLRPC$TL_document2 = tLRPC$TL_document;
                                            file3 = file2;
                                        } else {
                                            tLRPC$TL_document = (TLRPC$TL_document) sendingMediaInfo2.searchImage.document;
                                            tLRPC$TL_document2 = tLRPC$TL_document;
                                            file3 = FileLoader.getPathToAttach(tLRPC$TL_document, true);
                                        }
                                        if (tLRPC$TL_document2 == null) {
                                            if (sendingMediaInfo2.searchImage.localUrl != null) {
                                                hashMap3.put(ImagesContract.URL, sendingMediaInfo2.searchImage.localUrl);
                                            }
                                            file4 = null;
                                            tLRPC$TL_document2 = new TLRPC$TL_document();
                                            tLRPC$TL_document2.id = 0;
                                            tLRPC$TL_document2.date = ConnectionsManager.getInstance().getCurrentTime();
                                            TLRPC$TL_documentAttributeFilename tLRPC$TL_documentAttributeFilename = new TLRPC$TL_documentAttributeFilename();
                                            tLRPC$TL_documentAttributeFilename.file_name = "animation.gif";
                                            tLRPC$TL_document2.attributes.add(tLRPC$TL_documentAttributeFilename);
                                            tLRPC$TL_document2.size = sendingMediaInfo2.searchImage.size;
                                            tLRPC$TL_document2.dc_id = 0;
                                            if (file3.toString().endsWith("mp4")) {
                                                tLRPC$TL_document2.mime_type = "image/gif";
                                            } else {
                                                tLRPC$TL_document2.mime_type = MimeTypes.VIDEO_MP4;
                                                tLRPC$TL_document2.attributes.add(new TLRPC$TL_documentAttributeAnimated());
                                            }
                                            if (file3.exists()) {
                                                file3 = null;
                                            } else {
                                                file4 = file3;
                                            }
                                            if (file4 == null) {
                                                file4 = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(sendingMediaInfo2.searchImage.thumbUrl) + "." + ImageLoader.getHttpUrlExtension(sendingMediaInfo2.searchImage.thumbUrl, "jpg"));
                                                if (!file4.exists()) {
                                                    file4 = null;
                                                }
                                            }
                                            if (file4 != null) {
                                                try {
                                                    createVideoThumbnail = file4.getAbsolutePath().endsWith("mp4") ? ThumbnailUtils.createVideoThumbnail(file4.getAbsolutePath(), 1) : ImageLoader.loadBitmap(file4.getAbsolutePath(), null, 90.0f, 90.0f, true);
                                                    if (createVideoThumbnail != null) {
                                                        tLRPC$TL_document2.thumb = ImageLoader.scaleAndSaveImage(createVideoThumbnail, 90.0f, 90.0f, 55, z4);
                                                        createVideoThumbnail.recycle();
                                                    }
                                                } catch (Throwable e) {
                                                    FileLog.m13728e(e);
                                                }
                                            }
                                            if (tLRPC$TL_document2.thumb == null) {
                                                tLRPC$TL_document2.thumb = new TLRPC$TL_photoSize();
                                                tLRPC$TL_document2.thumb.f10147w = sendingMediaInfo2.searchImage.width;
                                                tLRPC$TL_document2.thumb.f10146h = sendingMediaInfo2.searchImage.height;
                                                tLRPC$TL_document2.thumb.size = 0;
                                                tLRPC$TL_document2.thumb.location = new TLRPC$TL_fileLocationUnavailable();
                                                tLRPC$TL_document2.thumb.type = "x";
                                            }
                                        }
                                        tLRPC$TL_document2.caption = sendingMediaInfo2.caption;
                                        str4 = sendingMediaInfo2.searchImage.imageUrl;
                                        file5 = file3 != null ? sendingMediaInfo2.searchImage.imageUrl : file3.toString();
                                        if (!(hashMap3 == null || sendingMediaInfo2.searchImage.imageUrl == null)) {
                                            hashMap3.put("originalPath", sendingMediaInfo2.searchImage.imageUrl);
                                        }
                                        AndroidUtilities.runOnUIThread(new Runnable() {
                                            public void run() {
                                                SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_document2, null, file5, j2, messageObject2, null, hashMap3, 0);
                                            }
                                        });
                                        i4 = i2;
                                        str2 = str3;
                                        j4 = j;
                                    } else {
                                        z = true;
                                        tLRPC$TL_photo2 = null;
                                        if (!z4 && sendingMediaInfo2.ttl == 0) {
                                            tLRPC$TL_photo2 = (TLRPC$TL_photo) MessagesStorage.getInstance().getSentFile(sendingMediaInfo2.searchImage.imageUrl, z4 ? 0 : 3);
                                        }
                                        if (tLRPC$TL_photo2 == null) {
                                            file6 = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(sendingMediaInfo2.searchImage.imageUrl) + "." + ImageLoader.getHttpUrlExtension(sendingMediaInfo2.searchImage.imageUrl, "jpg"));
                                            if (file6.exists() && file6.length() != 0) {
                                                tLRPC$TL_photo2 = SendMessagesHelper.getInstance().generatePhotoSizes(file6.toString(), null);
                                                if (tLRPC$TL_photo2 != null) {
                                                    z = false;
                                                }
                                            }
                                            if (tLRPC$TL_photo2 == null) {
                                                file6 = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(sendingMediaInfo2.searchImage.thumbUrl) + "." + ImageLoader.getHttpUrlExtension(sendingMediaInfo2.searchImage.thumbUrl, "jpg"));
                                                if (file6.exists()) {
                                                    tLRPC$TL_photo2 = SendMessagesHelper.getInstance().generatePhotoSizes(file6.toString(), null);
                                                }
                                                if (tLRPC$TL_photo2 == null) {
                                                    tLRPC$TL_photo2 = new TLRPC$TL_photo();
                                                    tLRPC$TL_photo2.date = ConnectionsManager.getInstance().getCurrentTime();
                                                    TLRPC$TL_photoSize tLRPC$TL_photoSize = new TLRPC$TL_photoSize();
                                                    tLRPC$TL_photoSize.w = sendingMediaInfo2.searchImage.width;
                                                    tLRPC$TL_photoSize.h = sendingMediaInfo2.searchImage.height;
                                                    tLRPC$TL_photoSize.size = 0;
                                                    tLRPC$TL_photoSize.location = new TLRPC$TL_fileLocationUnavailable();
                                                    tLRPC$TL_photoSize.type = "x";
                                                    tLRPC$TL_photo2.sizes.add(tLRPC$TL_photoSize);
                                                }
                                            }
                                        }
                                        if (tLRPC$TL_photo2 != null) {
                                            tLRPC$TL_photo2.caption = sendingMediaInfo2.caption;
                                            hashMap4 = new HashMap();
                                            if (sendingMediaInfo2.searchImage.imageUrl != null) {
                                                hashMap4.put("originalPath", sendingMediaInfo2.searchImage.imageUrl);
                                            }
                                            if (z4) {
                                                i2++;
                                                hashMap4.put("groupId", TtmlNode.ANONYMOUS_REGION_ID + j3);
                                                if (i2 == 10 || i3 == size - 1) {
                                                    hashMap4.put("final", "1");
                                                    j = 0;
                                                }
                                            }
                                            AndroidUtilities.runOnUIThread(new Runnable() {
                                                public void run() {
                                                    SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_photo2, z ? sendingMediaInfo2.searchImage.imageUrl : null, j2, messageObject2, null, hashMap4, sendingMediaInfo2.ttl);
                                                }
                                            });
                                        }
                                        i4 = i2;
                                        j4 = j;
                                        str2 = str3;
                                    }
                                } else if (sendingMediaInfo2.isVideo) {
                                    str2 = sendingMediaInfo2.path;
                                    file5 = sendingMediaInfo2.path;
                                    if (file5 == null && sendingMediaInfo2.uri != null) {
                                        file5 = AndroidUtilities.getPath(sendingMediaInfo2.uri);
                                        str2 = sendingMediaInfo2.uri.toString();
                                    }
                                    if (z3) {
                                        str5 = file5;
                                        str6 = str2;
                                        str = FileLoader.getFileExtension(new File(file5));
                                        obj = 1;
                                    } else if (file5 == null && (file5.endsWith(".gif") || file5.endsWith(".webp"))) {
                                        str4 = file5.endsWith(".gif") ? "gif" : "webp";
                                        str5 = file5;
                                        str6 = str2;
                                        i4 = 1;
                                        str = str4;
                                    } else {
                                        if (file5 == null && sendingMediaInfo2.uri != null) {
                                            if (MediaController.isGif(sendingMediaInfo2.uri)) {
                                                obj = 1;
                                                str4 = sendingMediaInfo2.uri.toString();
                                                str5 = MediaController.copyFileToCache(sendingMediaInfo2.uri, "gif");
                                                str6 = str4;
                                                str = "gif";
                                            } else if (MediaController.isWebp(sendingMediaInfo2.uri)) {
                                                obj = 1;
                                                str4 = sendingMediaInfo2.uri.toString();
                                                str5 = MediaController.copyFileToCache(sendingMediaInfo2.uri, "webp");
                                                str6 = str4;
                                                str = "webp";
                                            }
                                        }
                                        str5 = file5;
                                        str6 = str2;
                                        str = str3;
                                        obj = null;
                                    }
                                    if (obj == null) {
                                        if (arrayList != null) {
                                            arrayList4 = new ArrayList();
                                            arrayList5 = new ArrayList();
                                            arrayList6 = new ArrayList();
                                        } else {
                                            arrayList6 = arrayList3;
                                            arrayList5 = arrayList2;
                                            arrayList4 = arrayList;
                                        }
                                        arrayList4.add(str5);
                                        arrayList5.add(str6);
                                        arrayList6.add(sendingMediaInfo2.caption);
                                        arrayList3 = arrayList6;
                                        arrayList2 = arrayList5;
                                        arrayList = arrayList4;
                                        str2 = str;
                                        i4 = i2;
                                        j4 = j;
                                    } else {
                                        if (str5 == null) {
                                            file3 = new File(str5);
                                            str6 = str6 + file3.length() + "_" + file3.lastModified();
                                        } else {
                                            str6 = null;
                                        }
                                        tLRPC$TL_photo3 = null;
                                        if (hashMap2 == null) {
                                            mediaSendPrepareWorker2 = (MediaSendPrepareWorker) hashMap2.get(sendingMediaInfo2);
                                            tLRPC$TL_photo = mediaSendPrepareWorker2.photo;
                                            if (tLRPC$TL_photo != null) {
                                                try {
                                                    mediaSendPrepareWorker2.sync.await();
                                                } catch (Throwable e2) {
                                                    FileLog.m13727e("tmessages", e2);
                                                }
                                                tLRPC$TL_photo3 = mediaSendPrepareWorker2.photo;
                                            } else {
                                                tLRPC$TL_photo3 = tLRPC$TL_photo;
                                            }
                                            tLRPC$TL_photo2 = tLRPC$TL_photo3;
                                        } else {
                                            if (!z4 && sendingMediaInfo2.ttl == 0) {
                                                tLRPC$TL_photo3 = (TLRPC$TL_photo) MessagesStorage.getInstance().getSentFile(str6, z4 ? 0 : 3);
                                                if (tLRPC$TL_photo3 == null && sendingMediaInfo2.uri != null) {
                                                    tLRPC$TL_photo3 = (TLRPC$TL_photo) MessagesStorage.getInstance().getSentFile(AndroidUtilities.getPath(sendingMediaInfo2.uri), z4 ? 0 : 3);
                                                }
                                            }
                                            tLRPC$TL_photo2 = tLRPC$TL_photo3 != null ? SendMessagesHelper.getInstance().generatePhotoSizes(sendingMediaInfo2.path, sendingMediaInfo2.uri) : tLRPC$TL_photo3;
                                        }
                                        if (tLRPC$TL_photo2 == null) {
                                            hashMap5 = new HashMap();
                                            tLRPC$TL_photo2.caption = sendingMediaInfo2.caption;
                                            z2 = sendingMediaInfo2.masks == null && !sendingMediaInfo2.masks.isEmpty();
                                            tLRPC$TL_photo2.has_stickers = z2;
                                            if (z2) {
                                                serializedData = new SerializedData((sendingMediaInfo2.masks.size() * 20) + 4);
                                                serializedData.writeInt32(sendingMediaInfo2.masks.size());
                                                for (i5 = 0; i5 < sendingMediaInfo2.masks.size(); i5++) {
                                                    ((InputDocument) sendingMediaInfo2.masks.get(i5)).serializeToStream(serializedData);
                                                }
                                                hashMap5.put("masks", Utilities.bytesToHex(serializedData.toByteArray()));
                                            }
                                            if (str6 != null) {
                                                hashMap5.put("originalPath", str6);
                                            }
                                            if (z4) {
                                                i4 = i2;
                                                j5 = j;
                                            } else {
                                                i4 = i2 + 1;
                                                hashMap5.put("groupId", TtmlNode.ANONYMOUS_REGION_ID + j3);
                                                if (i4 != 10 || i3 == size - 1) {
                                                    hashMap5.put("final", "1");
                                                    j5 = 0;
                                                } else {
                                                    j5 = j;
                                                }
                                            }
                                            AndroidUtilities.runOnUIThread(new Runnable() {
                                                public void run() {
                                                    SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_photo2, null, j2, messageObject2, null, hashMap5, sendingMediaInfo2.ttl);
                                                }
                                            });
                                            str2 = str;
                                            j4 = j5;
                                        } else {
                                            if (arrayList == null) {
                                                arrayList = new ArrayList();
                                                arrayList2 = new ArrayList();
                                                arrayList3 = new ArrayList();
                                            }
                                            arrayList.add(str5);
                                            arrayList2.add(str6);
                                            arrayList3.add(sendingMediaInfo2.caption);
                                            i4 = i2;
                                            str2 = str;
                                            j4 = j;
                                        }
                                    }
                                } else {
                                    bitmap = null;
                                    if (sendingMediaInfo2.videoEditedInfo == null || sendingMediaInfo2.path.endsWith("mp4")) {
                                        str7 = sendingMediaInfo2.path;
                                        str2 = sendingMediaInfo2.path;
                                        file7 = new File(str2);
                                        str6 = str2 + file7.length() + "_" + file7.lastModified();
                                        if (sendingMediaInfo2.videoEditedInfo == null) {
                                            boolean z5 = sendingMediaInfo2.videoEditedInfo.muted;
                                            file5 = str6 + sendingMediaInfo2.videoEditedInfo.estimatedDuration + "_" + sendingMediaInfo2.videoEditedInfo.startTime + "_" + sendingMediaInfo2.videoEditedInfo.endTime + (sendingMediaInfo2.videoEditedInfo.muted ? "_m" : TtmlNode.ANONYMOUS_REGION_ID);
                                            if (sendingMediaInfo2.videoEditedInfo.resultWidth == sendingMediaInfo2.videoEditedInfo.originalWidth) {
                                                file5 = file5 + "_" + sendingMediaInfo2.videoEditedInfo.resultWidth;
                                            }
                                            j6 = sendingMediaInfo2.videoEditedInfo.startTime < 0 ? sendingMediaInfo2.videoEditedInfo.startTime : 0;
                                            z3 = z5;
                                            str = file5;
                                        } else {
                                            j6 = 0;
                                            z3 = false;
                                            str = str6;
                                        }
                                        tLRPC$TL_document3 = null;
                                        if (!z4 && sendingMediaInfo2.ttl == 0) {
                                            tLRPC$TL_document3 = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(str, z4 ? 2 : 5);
                                        }
                                        if (tLRPC$TL_document3 == null) {
                                            access$1900 = SendMessagesHelper.createVideoThumbnail(sendingMediaInfo2.path, j6);
                                            if (access$1900 == null) {
                                                access$1900 = ThumbnailUtils.createVideoThumbnail(sendingMediaInfo2.path, 1);
                                            }
                                            PhotoSize scaleAndSaveImage = ImageLoader.scaleAndSaveImage(access$1900, 90.0f, 90.0f, 55, z4);
                                            if (!(access$1900 == null || scaleAndSaveImage == null)) {
                                                access$1900 = null;
                                            }
                                            tLRPC$TL_document3 = new TLRPC$TL_document();
                                            tLRPC$TL_document3.thumb = scaleAndSaveImage;
                                            if (tLRPC$TL_document3.thumb != null) {
                                                tLRPC$TL_document3.thumb = new TLRPC$TL_photoSizeEmpty();
                                                tLRPC$TL_document3.thumb.type = "s";
                                            } else {
                                                tLRPC$TL_document3.thumb.type = "s";
                                            }
                                            tLRPC$TL_document3.mime_type = MimeTypes.VIDEO_MP4;
                                            UserConfig.saveConfig(false);
                                            tLRPC$TL_documentAttributeVideo = z4 ? peerLayerVersion < 66 ? new TLRPC$TL_documentAttributeVideo() : new TLRPC$TL_documentAttributeVideo_layer65() : new TLRPC$TL_documentAttributeVideo();
                                            tLRPC$TL_document3.attributes.add(tLRPC$TL_documentAttributeVideo);
                                            if (sendingMediaInfo2.videoEditedInfo == null && sendingMediaInfo2.videoEditedInfo.needConvert()) {
                                                if (sendingMediaInfo2.videoEditedInfo.muted) {
                                                    tLRPC$TL_document3.attributes.add(new TLRPC$TL_documentAttributeAnimated());
                                                    SendMessagesHelper.fillVideoAttribute(sendingMediaInfo2.path, tLRPC$TL_documentAttributeVideo, sendingMediaInfo2.videoEditedInfo);
                                                    sendingMediaInfo2.videoEditedInfo.originalWidth = tLRPC$TL_documentAttributeVideo.w;
                                                    sendingMediaInfo2.videoEditedInfo.originalHeight = tLRPC$TL_documentAttributeVideo.h;
                                                    tLRPC$TL_documentAttributeVideo.w = sendingMediaInfo2.videoEditedInfo.resultWidth;
                                                    tLRPC$TL_documentAttributeVideo.h = sendingMediaInfo2.videoEditedInfo.resultHeight;
                                                } else {
                                                    tLRPC$TL_documentAttributeVideo.duration = (int) (sendingMediaInfo2.videoEditedInfo.estimatedDuration / 1000);
                                                    if (sendingMediaInfo2.videoEditedInfo.rotationValue == 90 || sendingMediaInfo2.videoEditedInfo.rotationValue == 270) {
                                                        tLRPC$TL_documentAttributeVideo.w = sendingMediaInfo2.videoEditedInfo.resultHeight;
                                                        tLRPC$TL_documentAttributeVideo.h = sendingMediaInfo2.videoEditedInfo.resultWidth;
                                                    } else {
                                                        tLRPC$TL_documentAttributeVideo.w = sendingMediaInfo2.videoEditedInfo.resultWidth;
                                                        tLRPC$TL_documentAttributeVideo.h = sendingMediaInfo2.videoEditedInfo.resultHeight;
                                                    }
                                                }
                                                tLRPC$TL_document3.size = (int) sendingMediaInfo2.videoEditedInfo.estimatedSize;
                                                str2 = "-2147483648_" + UserConfig.lastLocalId + ".mp4";
                                                UserConfig.lastLocalId--;
                                                file = new File(FileLoader.getInstance().getDirectory(4), str2);
                                                UserConfig.saveConfig(false);
                                                str7 = file.getAbsolutePath();
                                                bitmap = access$1900;
                                            } else {
                                                if (file7.exists()) {
                                                    tLRPC$TL_document3.size = (int) file7.length();
                                                }
                                                SendMessagesHelper.fillVideoAttribute(sendingMediaInfo2.path, tLRPC$TL_documentAttributeVideo, null);
                                                bitmap = access$1900;
                                            }
                                        }
                                        hashMap6 = new HashMap();
                                        tLRPC$TL_document3.caption = sendingMediaInfo2.caption == null ? sendingMediaInfo2.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                        if (str != null) {
                                            hashMap6.put("originalPath", str);
                                        }
                                        if (!z3 && z4) {
                                            i2++;
                                            hashMap6.put("groupId", TtmlNode.ANONYMOUS_REGION_ID + j3);
                                            if (i2 == 10 || i3 == size - 1) {
                                                hashMap6.put("final", "1");
                                                j = 0;
                                                i4 = i2;
                                                sendingMediaInfo3 = sendingMediaInfo2;
                                                AndroidUtilities.runOnUIThread(new Runnable(null) {
                                                    public void run() {
                                                        if (!(bitmap == null || null == null)) {
                                                            ImageLoader.getInstance().putImageToCache(new BitmapDrawable(bitmap), null);
                                                        }
                                                        SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_document3, sendingMediaInfo3.videoEditedInfo, str7, j2, messageObject2, null, hashMap6, sendingMediaInfo3.ttl);
                                                    }
                                                });
                                                j4 = j;
                                            }
                                        }
                                        i4 = i2;
                                        sendingMediaInfo3 = sendingMediaInfo2;
                                        AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                        j4 = j;
                                    } else {
                                        SendMessagesHelper.prepareSendingDocumentInternal(sendingMediaInfo2.path, sendingMediaInfo2.path, null, null, j2, messageObject2, sendingMediaInfo2.caption);
                                        i4 = i2;
                                        j4 = j;
                                    }
                                    str2 = str3;
                                }
                                i3++;
                                i2 = i4;
                                str3 = str2;
                                j = j4;
                                j2 = j3;
                            }
                            if (j != 0) {
                                final long j7 = j;
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        SendMessagesHelper instance = SendMessagesHelper.getInstance();
                                        ArrayList arrayList = (ArrayList) instance.delayedMessages.get("group_" + j7);
                                        if (arrayList != null && !arrayList.isEmpty()) {
                                            DelayedMessage delayedMessage = (DelayedMessage) arrayList.get(0);
                                            MessageObject messageObject = (MessageObject) delayedMessage.messageObjects.get(delayedMessage.messageObjects.size() - 1);
                                            delayedMessage.finalGroupMessage = messageObject.getId();
                                            messageObject.messageOwner.params.put("final", "1");
                                            TLRPC$messages_Messages tLRPC$TL_messages_messages = new TLRPC$TL_messages_messages();
                                            tLRPC$TL_messages_messages.messages.add(messageObject.messageOwner);
                                            MessagesStorage.getInstance().putMessages(tLRPC$TL_messages_messages, delayedMessage.peer, -2, 0, false);
                                            instance.sendReadyToSendGroup(delayedMessage, true, true);
                                        }
                                    }
                                });
                            }
                            if (c0060e2 != null) {
                                c0060e2.m154d();
                            }
                            if (!(arrayList == null || arrayList.isEmpty())) {
                                for (i6 = 0; i6 < arrayList.size(); i6++) {
                                    SendMessagesHelper.prepareSendingDocumentInternal((String) arrayList.get(i6), (String) arrayList2.get(i6), null, str3, j2, messageObject2, (CharSequence) arrayList3.get(i6));
                                }
                            }
                            FileLog.m13725d("total send time = " + (System.currentTimeMillis() - currentTimeMillis));
                        }
                    }
                    peerLayerVersion = 0;
                    if (z4) {
                    }
                    hashMap = new HashMap();
                    for (i = 0; i < size; i++) {
                        sendingMediaInfo = (SendingMediaInfo) arrayList2.get(i);
                        str = sendingMediaInfo.path;
                        str2 = sendingMediaInfo.path;
                        str2 = AndroidUtilities.getPath(sendingMediaInfo.uri);
                        str = sendingMediaInfo.uri.toString();
                        if (str2 == null) {
                            str = null;
                        } else {
                            file = new File(str2);
                            str = str + file.length() + "_" + file.lastModified();
                        }
                        tLRPC$TL_photo = null;
                        if (z4) {
                        }
                        tLRPC$TL_photo = (TLRPC$TL_photo) MessagesStorage.getInstance().getSentFile(str, z4 ? 0 : 3);
                        if (z4) {
                        }
                        tLRPC$TL_photo = (TLRPC$TL_photo) MessagesStorage.getInstance().getSentFile(AndroidUtilities.getPath(sendingMediaInfo.uri), z4 ? 0 : 3);
                        mediaSendPrepareWorker = new MediaSendPrepareWorker();
                        hashMap.put(sendingMediaInfo, mediaSendPrepareWorker);
                        if (tLRPC$TL_photo == null) {
                            mediaSendPrepareWorker.sync = new CountDownLatch(1);
                            SendMessagesHelper.mediaSendThreadPool.execute(/* anonymous class already generated */);
                        } else {
                            mediaSendPrepareWorker.photo = tLRPC$TL_photo;
                        }
                    }
                    hashMap2 = hashMap;
                    j = 0;
                    arrayList = null;
                    arrayList2 = null;
                    arrayList3 = null;
                    str3 = null;
                    i2 = 0;
                    i3 = 0;
                    j2 = 0;
                    while (i3 < size) {
                        sendingMediaInfo2 = (SendingMediaInfo) arrayList2.get(i3);
                        if (z4) {
                        }
                        j3 = j2;
                        if (sendingMediaInfo2.searchImage == null) {
                            if (sendingMediaInfo2.isVideo) {
                                str2 = sendingMediaInfo2.path;
                                file5 = sendingMediaInfo2.path;
                                file5 = AndroidUtilities.getPath(sendingMediaInfo2.uri);
                                str2 = sendingMediaInfo2.uri.toString();
                                if (z3) {
                                    str5 = file5;
                                    str6 = str2;
                                    str = FileLoader.getFileExtension(new File(file5));
                                    obj = 1;
                                } else {
                                    if (file5 == null) {
                                    }
                                    if (MediaController.isGif(sendingMediaInfo2.uri)) {
                                        obj = 1;
                                        str4 = sendingMediaInfo2.uri.toString();
                                        str5 = MediaController.copyFileToCache(sendingMediaInfo2.uri, "gif");
                                        str6 = str4;
                                        str = "gif";
                                    } else {
                                        if (MediaController.isWebp(sendingMediaInfo2.uri)) {
                                            obj = 1;
                                            str4 = sendingMediaInfo2.uri.toString();
                                            str5 = MediaController.copyFileToCache(sendingMediaInfo2.uri, "webp");
                                            str6 = str4;
                                            str = "webp";
                                        }
                                        str5 = file5;
                                        str6 = str2;
                                        str = str3;
                                        obj = null;
                                    }
                                }
                                if (obj == null) {
                                    if (str5 == null) {
                                        str6 = null;
                                    } else {
                                        file3 = new File(str5);
                                        str6 = str6 + file3.length() + "_" + file3.lastModified();
                                    }
                                    tLRPC$TL_photo3 = null;
                                    if (hashMap2 == null) {
                                        if (z4) {
                                        }
                                        tLRPC$TL_photo3 = (TLRPC$TL_photo) MessagesStorage.getInstance().getSentFile(str6, z4 ? 0 : 3);
                                        if (z4) {
                                        }
                                        tLRPC$TL_photo3 = (TLRPC$TL_photo) MessagesStorage.getInstance().getSentFile(AndroidUtilities.getPath(sendingMediaInfo2.uri), z4 ? 0 : 3);
                                        if (tLRPC$TL_photo3 != null) {
                                        }
                                    } else {
                                        mediaSendPrepareWorker2 = (MediaSendPrepareWorker) hashMap2.get(sendingMediaInfo2);
                                        tLRPC$TL_photo = mediaSendPrepareWorker2.photo;
                                        if (tLRPC$TL_photo != null) {
                                            tLRPC$TL_photo3 = tLRPC$TL_photo;
                                        } else {
                                            mediaSendPrepareWorker2.sync.await();
                                            tLRPC$TL_photo3 = mediaSendPrepareWorker2.photo;
                                        }
                                        tLRPC$TL_photo2 = tLRPC$TL_photo3;
                                    }
                                    if (tLRPC$TL_photo2 == null) {
                                        if (arrayList == null) {
                                            arrayList = new ArrayList();
                                            arrayList2 = new ArrayList();
                                            arrayList3 = new ArrayList();
                                        }
                                        arrayList.add(str5);
                                        arrayList2.add(str6);
                                        arrayList3.add(sendingMediaInfo2.caption);
                                        i4 = i2;
                                        str2 = str;
                                        j4 = j;
                                    } else {
                                        hashMap5 = new HashMap();
                                        tLRPC$TL_photo2.caption = sendingMediaInfo2.caption;
                                        if (sendingMediaInfo2.masks == null) {
                                        }
                                        tLRPC$TL_photo2.has_stickers = z2;
                                        if (z2) {
                                            serializedData = new SerializedData((sendingMediaInfo2.masks.size() * 20) + 4);
                                            serializedData.writeInt32(sendingMediaInfo2.masks.size());
                                            for (i5 = 0; i5 < sendingMediaInfo2.masks.size(); i5++) {
                                                ((InputDocument) sendingMediaInfo2.masks.get(i5)).serializeToStream(serializedData);
                                            }
                                            hashMap5.put("masks", Utilities.bytesToHex(serializedData.toByteArray()));
                                        }
                                        if (str6 != null) {
                                            hashMap5.put("originalPath", str6);
                                        }
                                        if (z4) {
                                            i4 = i2;
                                            j5 = j;
                                        } else {
                                            i4 = i2 + 1;
                                            hashMap5.put("groupId", TtmlNode.ANONYMOUS_REGION_ID + j3);
                                            if (i4 != 10) {
                                            }
                                            hashMap5.put("final", "1");
                                            j5 = 0;
                                        }
                                        AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                        str2 = str;
                                        j4 = j5;
                                    }
                                } else {
                                    if (arrayList != null) {
                                        arrayList6 = arrayList3;
                                        arrayList5 = arrayList2;
                                        arrayList4 = arrayList;
                                    } else {
                                        arrayList4 = new ArrayList();
                                        arrayList5 = new ArrayList();
                                        arrayList6 = new ArrayList();
                                    }
                                    arrayList4.add(str5);
                                    arrayList5.add(str6);
                                    arrayList6.add(sendingMediaInfo2.caption);
                                    arrayList3 = arrayList6;
                                    arrayList2 = arrayList5;
                                    arrayList = arrayList4;
                                    str2 = str;
                                    i4 = i2;
                                    j4 = j;
                                }
                            } else {
                                bitmap = null;
                                if (sendingMediaInfo2.videoEditedInfo == null) {
                                }
                                str7 = sendingMediaInfo2.path;
                                str2 = sendingMediaInfo2.path;
                                file7 = new File(str2);
                                str6 = str2 + file7.length() + "_" + file7.lastModified();
                                if (sendingMediaInfo2.videoEditedInfo == null) {
                                    j6 = 0;
                                    z3 = false;
                                    str = str6;
                                } else {
                                    boolean z52 = sendingMediaInfo2.videoEditedInfo.muted;
                                    if (sendingMediaInfo2.videoEditedInfo.muted) {
                                    }
                                    file5 = str6 + sendingMediaInfo2.videoEditedInfo.estimatedDuration + "_" + sendingMediaInfo2.videoEditedInfo.startTime + "_" + sendingMediaInfo2.videoEditedInfo.endTime + (sendingMediaInfo2.videoEditedInfo.muted ? "_m" : TtmlNode.ANONYMOUS_REGION_ID);
                                    if (sendingMediaInfo2.videoEditedInfo.resultWidth == sendingMediaInfo2.videoEditedInfo.originalWidth) {
                                        file5 = file5 + "_" + sendingMediaInfo2.videoEditedInfo.resultWidth;
                                    }
                                    if (sendingMediaInfo2.videoEditedInfo.startTime < 0) {
                                    }
                                    j6 = sendingMediaInfo2.videoEditedInfo.startTime < 0 ? sendingMediaInfo2.videoEditedInfo.startTime : 0;
                                    z3 = z52;
                                    str = file5;
                                }
                                tLRPC$TL_document3 = null;
                                if (z4) {
                                }
                                tLRPC$TL_document3 = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(str, z4 ? 2 : 5);
                                if (tLRPC$TL_document3 == null) {
                                    access$1900 = SendMessagesHelper.createVideoThumbnail(sendingMediaInfo2.path, j6);
                                    if (access$1900 == null) {
                                        access$1900 = ThumbnailUtils.createVideoThumbnail(sendingMediaInfo2.path, 1);
                                    }
                                    PhotoSize scaleAndSaveImage2 = ImageLoader.scaleAndSaveImage(access$1900, 90.0f, 90.0f, 55, z4);
                                    access$1900 = null;
                                    tLRPC$TL_document3 = new TLRPC$TL_document();
                                    tLRPC$TL_document3.thumb = scaleAndSaveImage2;
                                    if (tLRPC$TL_document3.thumb != null) {
                                        tLRPC$TL_document3.thumb.type = "s";
                                    } else {
                                        tLRPC$TL_document3.thumb = new TLRPC$TL_photoSizeEmpty();
                                        tLRPC$TL_document3.thumb.type = "s";
                                    }
                                    tLRPC$TL_document3.mime_type = MimeTypes.VIDEO_MP4;
                                    UserConfig.saveConfig(false);
                                    if (z4) {
                                        if (peerLayerVersion < 66) {
                                        }
                                    }
                                    tLRPC$TL_document3.attributes.add(tLRPC$TL_documentAttributeVideo);
                                    if (sendingMediaInfo2.videoEditedInfo == null) {
                                    }
                                    if (file7.exists()) {
                                        tLRPC$TL_document3.size = (int) file7.length();
                                    }
                                    SendMessagesHelper.fillVideoAttribute(sendingMediaInfo2.path, tLRPC$TL_documentAttributeVideo, null);
                                    bitmap = access$1900;
                                }
                                hashMap6 = new HashMap();
                                if (sendingMediaInfo2.caption == null) {
                                }
                                tLRPC$TL_document3.caption = sendingMediaInfo2.caption == null ? sendingMediaInfo2.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                if (str != null) {
                                    hashMap6.put("originalPath", str);
                                }
                                i2++;
                                hashMap6.put("groupId", TtmlNode.ANONYMOUS_REGION_ID + j3);
                                hashMap6.put("final", "1");
                                j = 0;
                                i4 = i2;
                                sendingMediaInfo3 = sendingMediaInfo2;
                                AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                j4 = j;
                                str2 = str3;
                            }
                        } else if (sendingMediaInfo2.searchImage.type != 1) {
                            z = true;
                            tLRPC$TL_photo2 = null;
                            if (z4) {
                            }
                            tLRPC$TL_photo2 = (TLRPC$TL_photo) MessagesStorage.getInstance().getSentFile(sendingMediaInfo2.searchImage.imageUrl, z4 ? 0 : 3);
                            if (tLRPC$TL_photo2 == null) {
                                file6 = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(sendingMediaInfo2.searchImage.imageUrl) + "." + ImageLoader.getHttpUrlExtension(sendingMediaInfo2.searchImage.imageUrl, "jpg"));
                                tLRPC$TL_photo2 = SendMessagesHelper.getInstance().generatePhotoSizes(file6.toString(), null);
                                if (tLRPC$TL_photo2 != null) {
                                    z = false;
                                }
                                if (tLRPC$TL_photo2 == null) {
                                    file6 = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(sendingMediaInfo2.searchImage.thumbUrl) + "." + ImageLoader.getHttpUrlExtension(sendingMediaInfo2.searchImage.thumbUrl, "jpg"));
                                    if (file6.exists()) {
                                        tLRPC$TL_photo2 = SendMessagesHelper.getInstance().generatePhotoSizes(file6.toString(), null);
                                    }
                                    if (tLRPC$TL_photo2 == null) {
                                        tLRPC$TL_photo2 = new TLRPC$TL_photo();
                                        tLRPC$TL_photo2.date = ConnectionsManager.getInstance().getCurrentTime();
                                        TLRPC$TL_photoSize tLRPC$TL_photoSize2 = new TLRPC$TL_photoSize();
                                        tLRPC$TL_photoSize2.w = sendingMediaInfo2.searchImage.width;
                                        tLRPC$TL_photoSize2.h = sendingMediaInfo2.searchImage.height;
                                        tLRPC$TL_photoSize2.size = 0;
                                        tLRPC$TL_photoSize2.location = new TLRPC$TL_fileLocationUnavailable();
                                        tLRPC$TL_photoSize2.type = "x";
                                        tLRPC$TL_photo2.sizes.add(tLRPC$TL_photoSize2);
                                    }
                                }
                            }
                            if (tLRPC$TL_photo2 != null) {
                                tLRPC$TL_photo2.caption = sendingMediaInfo2.caption;
                                hashMap4 = new HashMap();
                                if (sendingMediaInfo2.searchImage.imageUrl != null) {
                                    hashMap4.put("originalPath", sendingMediaInfo2.searchImage.imageUrl);
                                }
                                if (z4) {
                                    i2++;
                                    hashMap4.put("groupId", TtmlNode.ANONYMOUS_REGION_ID + j3);
                                    hashMap4.put("final", "1");
                                    j = 0;
                                }
                                AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                            }
                            i4 = i2;
                            j4 = j;
                            str2 = str3;
                        } else {
                            hashMap3 = new HashMap();
                            if (sendingMediaInfo2.searchImage.document instanceof TLRPC$TL_document) {
                                if (z4) {
                                    if (z4) {
                                    }
                                    document = (Document) MessagesStorage.getInstance().getSentFile(sendingMediaInfo2.searchImage.imageUrl, z4 ? 1 : 4);
                                    if (document instanceof TLRPC$TL_document) {
                                        tLRPC$TL_document = (TLRPC$TL_document) document;
                                        file2 = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(sendingMediaInfo2.searchImage.imageUrl) + "." + ImageLoader.getHttpUrlExtension(sendingMediaInfo2.searchImage.imageUrl, "jpg"));
                                        tLRPC$TL_document2 = tLRPC$TL_document;
                                        file3 = file2;
                                    }
                                }
                                tLRPC$TL_document = null;
                                file2 = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(sendingMediaInfo2.searchImage.imageUrl) + "." + ImageLoader.getHttpUrlExtension(sendingMediaInfo2.searchImage.imageUrl, "jpg"));
                                tLRPC$TL_document2 = tLRPC$TL_document;
                                file3 = file2;
                            } else {
                                tLRPC$TL_document = (TLRPC$TL_document) sendingMediaInfo2.searchImage.document;
                                tLRPC$TL_document2 = tLRPC$TL_document;
                                file3 = FileLoader.getPathToAttach(tLRPC$TL_document, true);
                            }
                            if (tLRPC$TL_document2 == null) {
                                if (sendingMediaInfo2.searchImage.localUrl != null) {
                                    hashMap3.put(ImagesContract.URL, sendingMediaInfo2.searchImage.localUrl);
                                }
                                file4 = null;
                                tLRPC$TL_document2 = new TLRPC$TL_document();
                                tLRPC$TL_document2.id = 0;
                                tLRPC$TL_document2.date = ConnectionsManager.getInstance().getCurrentTime();
                                TLRPC$TL_documentAttributeFilename tLRPC$TL_documentAttributeFilename2 = new TLRPC$TL_documentAttributeFilename();
                                tLRPC$TL_documentAttributeFilename2.file_name = "animation.gif";
                                tLRPC$TL_document2.attributes.add(tLRPC$TL_documentAttributeFilename2);
                                tLRPC$TL_document2.size = sendingMediaInfo2.searchImage.size;
                                tLRPC$TL_document2.dc_id = 0;
                                if (file3.toString().endsWith("mp4")) {
                                    tLRPC$TL_document2.mime_type = "image/gif";
                                } else {
                                    tLRPC$TL_document2.mime_type = MimeTypes.VIDEO_MP4;
                                    tLRPC$TL_document2.attributes.add(new TLRPC$TL_documentAttributeAnimated());
                                }
                                if (file3.exists()) {
                                    file3 = null;
                                } else {
                                    file4 = file3;
                                }
                                if (file4 == null) {
                                    file4 = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(sendingMediaInfo2.searchImage.thumbUrl) + "." + ImageLoader.getHttpUrlExtension(sendingMediaInfo2.searchImage.thumbUrl, "jpg"));
                                    if (file4.exists()) {
                                        file4 = null;
                                    }
                                }
                                if (file4 != null) {
                                    if (file4.getAbsolutePath().endsWith("mp4")) {
                                    }
                                    if (createVideoThumbnail != null) {
                                        tLRPC$TL_document2.thumb = ImageLoader.scaleAndSaveImage(createVideoThumbnail, 90.0f, 90.0f, 55, z4);
                                        createVideoThumbnail.recycle();
                                    }
                                }
                                if (tLRPC$TL_document2.thumb == null) {
                                    tLRPC$TL_document2.thumb = new TLRPC$TL_photoSize();
                                    tLRPC$TL_document2.thumb.f10147w = sendingMediaInfo2.searchImage.width;
                                    tLRPC$TL_document2.thumb.f10146h = sendingMediaInfo2.searchImage.height;
                                    tLRPC$TL_document2.thumb.size = 0;
                                    tLRPC$TL_document2.thumb.location = new TLRPC$TL_fileLocationUnavailable();
                                    tLRPC$TL_document2.thumb.type = "x";
                                }
                            }
                            tLRPC$TL_document2.caption = sendingMediaInfo2.caption;
                            str4 = sendingMediaInfo2.searchImage.imageUrl;
                            if (file3 != null) {
                            }
                            hashMap3.put("originalPath", sendingMediaInfo2.searchImage.imageUrl);
                            AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                            i4 = i2;
                            str2 = str3;
                            j4 = j;
                        }
                        i3++;
                        i2 = i4;
                        str3 = str2;
                        j = j4;
                        j2 = j3;
                    }
                    if (j != 0) {
                        final long j72 = j;
                        AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                    }
                    if (c0060e2 != null) {
                        c0060e2.m154d();
                    }
                    for (i6 = 0; i6 < arrayList.size(); i6++) {
                        SendMessagesHelper.prepareSendingDocumentInternal((String) arrayList.get(i6), (String) arrayList2.get(i6), null, str3, j2, messageObject2, (CharSequence) arrayList3.get(i6));
                    }
                    FileLog.m13725d("total send time = " + (System.currentTimeMillis() - currentTimeMillis));
                }
            });
        }
    }

    public static void prepareSendingPhoto(String str, Uri uri, long j, MessageObject messageObject, CharSequence charSequence, ArrayList<InputDocument> arrayList, C0060e c0060e, int i) {
        SendingMediaInfo sendingMediaInfo = new SendingMediaInfo();
        sendingMediaInfo.path = str;
        sendingMediaInfo.uri = uri;
        if (charSequence != null) {
            sendingMediaInfo.caption = charSequence.toString();
        }
        sendingMediaInfo.ttl = i;
        if (!(arrayList == null || arrayList.isEmpty())) {
            sendingMediaInfo.masks = new ArrayList(arrayList);
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(sendingMediaInfo);
        prepareSendingMedia(arrayList2, j, messageObject, c0060e, false, false);
    }

    public static void prepareSendingText(final String str, final long j) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.SendMessagesHelper$20$1 */
            class C33841 implements Runnable {

                /* renamed from: org.telegram.messenger.SendMessagesHelper$20$1$1 */
                class C33831 implements Runnable {
                    C33831() {
                    }

                    public void run() {
                        String access$1600 = SendMessagesHelper.getTrimmedString(str);
                        if (access$1600.length() != 0) {
                            int ceil = (int) Math.ceil((double) (((float) access$1600.length()) / 4096.0f));
                            for (int i = 0; i < ceil; i++) {
                                SendMessagesHelper.getInstance().sendMessage(access$1600.substring(i * 4096, Math.min((i + 1) * 4096, access$1600.length())), j, null, null, true, null, null, null);
                            }
                        }
                    }
                }

                C33841() {
                }

                public void run() {
                    AndroidUtilities.runOnUIThread(new C33831());
                }
            }

            public void run() {
                Utilities.stageQueue.postRunnable(new C33841());
            }
        });
    }

    public static void prepareSendingVideo(String str, long j, long j2, int i, int i2, VideoEditedInfo videoEditedInfo, long j3, MessageObject messageObject, CharSequence charSequence, int i3) {
        if (str != null && str.length() != 0) {
            final long j4 = j3;
            final VideoEditedInfo videoEditedInfo2 = videoEditedInfo;
            final String str2 = str;
            final long j5 = j2;
            final int i4 = i3;
            final int i5 = i2;
            final int i6 = i;
            final long j6 = j;
            final CharSequence charSequence2 = charSequence;
            final MessageObject messageObject2 = messageObject;
            new Thread(new Runnable() {
                public void run() {
                    boolean z = ((int) j4) == 0;
                    boolean z2 = videoEditedInfo2 != null && videoEditedInfo2.roundVideo;
                    if (videoEditedInfo2 != null || str2.endsWith("mp4") || z2) {
                        String str;
                        String str2;
                        String str3;
                        Bitmap bitmap;
                        String str4 = str2;
                        String str5 = str2;
                        File file = new File(str5);
                        long j = 0;
                        str5 = str5 + file.length() + "_" + file.lastModified();
                        if (videoEditedInfo2 != null) {
                            if (!z2) {
                                str5 = str5 + j5 + "_" + videoEditedInfo2.startTime + "_" + videoEditedInfo2.endTime + (videoEditedInfo2.muted ? "_m" : TtmlNode.ANONYMOUS_REGION_ID);
                                if (videoEditedInfo2.resultWidth == videoEditedInfo2.originalWidth) {
                                    str5 = str5 + "_" + videoEditedInfo2.resultWidth;
                                }
                            }
                            j = videoEditedInfo2.startTime >= 0 ? videoEditedInfo2.startTime : 0;
                            str = str5;
                        } else {
                            str = str5;
                        }
                        TLRPC$TL_document tLRPC$TL_document = null;
                        if (!z && i4 == 0) {
                            tLRPC$TL_document = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(str, !z ? 2 : 5);
                        }
                        if (tLRPC$TL_document == null) {
                            Bitmap bitmap2;
                            TLRPC$TL_documentAttributeVideo tLRPC$TL_documentAttributeVideo;
                            Bitmap access$1900 = SendMessagesHelper.createVideoThumbnail(str2, j);
                            if (access$1900 == null) {
                                access$1900 = ThumbnailUtils.createVideoThumbnail(str2, 1);
                            }
                            PhotoSize scaleAndSaveImage = ImageLoader.scaleAndSaveImage(access$1900, 90.0f, 90.0f, 55, z);
                            if (access$1900 == null || scaleAndSaveImage == null) {
                                bitmap2 = access$1900;
                                str5 = null;
                            } else if (!z2) {
                                bitmap2 = null;
                                str5 = null;
                            } else if (z) {
                                Utilities.blurBitmap(access$1900, 7, VERSION.SDK_INT < 21 ? 0 : 1, access$1900.getWidth(), access$1900.getHeight(), access$1900.getRowBytes());
                                r14 = String.format(scaleAndSaveImage.location.volume_id + "_" + scaleAndSaveImage.location.local_id + "@%d_%d_b2", new Object[]{Integer.valueOf((int) (((float) AndroidUtilities.roundMessageSize) / AndroidUtilities.density)), Integer.valueOf((int) (((float) AndroidUtilities.roundMessageSize) / AndroidUtilities.density))});
                                bitmap2 = access$1900;
                                str5 = r14;
                            } else {
                                Utilities.blurBitmap(access$1900, 3, VERSION.SDK_INT < 21 ? 0 : 1, access$1900.getWidth(), access$1900.getHeight(), access$1900.getRowBytes());
                                r14 = String.format(scaleAndSaveImage.location.volume_id + "_" + scaleAndSaveImage.location.local_id + "@%d_%d_b", new Object[]{Integer.valueOf((int) (((float) AndroidUtilities.roundMessageSize) / AndroidUtilities.density)), Integer.valueOf((int) (((float) AndroidUtilities.roundMessageSize) / AndroidUtilities.density))});
                                bitmap2 = access$1900;
                                str5 = r14;
                            }
                            tLRPC$TL_document = new TLRPC$TL_document();
                            tLRPC$TL_document.thumb = scaleAndSaveImage;
                            if (tLRPC$TL_document.thumb == null) {
                                tLRPC$TL_document.thumb = new TLRPC$TL_photoSizeEmpty();
                                tLRPC$TL_document.thumb.type = "s";
                            } else {
                                tLRPC$TL_document.thumb.type = "s";
                            }
                            tLRPC$TL_document.mime_type = MimeTypes.VIDEO_MP4;
                            UserConfig.saveConfig(false);
                            if (z) {
                                EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (j4 >> 32)));
                                if (encryptedChat != null) {
                                    tLRPC$TL_documentAttributeVideo = AndroidUtilities.getPeerLayerVersion(encryptedChat.layer) >= 66 ? new TLRPC$TL_documentAttributeVideo() : new TLRPC$TL_documentAttributeVideo_layer65();
                                } else {
                                    return;
                                }
                            }
                            tLRPC$TL_documentAttributeVideo = new TLRPC$TL_documentAttributeVideo();
                            tLRPC$TL_documentAttributeVideo.round_message = z2;
                            tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeVideo);
                            if (videoEditedInfo2 == null || !videoEditedInfo2.needConvert()) {
                                if (file.exists()) {
                                    tLRPC$TL_document.size = (int) file.length();
                                }
                                SendMessagesHelper.fillVideoAttribute(str2, tLRPC$TL_documentAttributeVideo, null);
                                str2 = str4;
                                str3 = str5;
                                bitmap = bitmap2;
                            } else {
                                if (videoEditedInfo2.muted) {
                                    tLRPC$TL_document.attributes.add(new TLRPC$TL_documentAttributeAnimated());
                                    SendMessagesHelper.fillVideoAttribute(str2, tLRPC$TL_documentAttributeVideo, videoEditedInfo2);
                                    videoEditedInfo2.originalWidth = tLRPC$TL_documentAttributeVideo.w;
                                    videoEditedInfo2.originalHeight = tLRPC$TL_documentAttributeVideo.h;
                                    tLRPC$TL_documentAttributeVideo.w = videoEditedInfo2.resultWidth;
                                    tLRPC$TL_documentAttributeVideo.h = videoEditedInfo2.resultHeight;
                                } else {
                                    tLRPC$TL_documentAttributeVideo.duration = (int) (j5 / 1000);
                                    if (videoEditedInfo2.rotationValue == 90 || videoEditedInfo2.rotationValue == 270) {
                                        tLRPC$TL_documentAttributeVideo.w = i5;
                                        tLRPC$TL_documentAttributeVideo.h = i6;
                                    } else {
                                        tLRPC$TL_documentAttributeVideo.w = i6;
                                        tLRPC$TL_documentAttributeVideo.h = i5;
                                    }
                                }
                                tLRPC$TL_document.size = (int) j6;
                                String str6 = "-2147483648_" + UserConfig.lastLocalId + ".mp4";
                                UserConfig.lastLocalId--;
                                File file2 = new File(FileLoader.getInstance().getDirectory(4), str6);
                                UserConfig.saveConfig(false);
                                str2 = file2.getAbsolutePath();
                                str3 = str5;
                                bitmap = bitmap2;
                            }
                        } else {
                            str3 = null;
                            bitmap = null;
                            str2 = str4;
                        }
                        final HashMap hashMap = new HashMap();
                        if (charSequence2 != null) {
                            tLRPC$TL_document.caption = charSequence2.toString();
                        } else {
                            tLRPC$TL_document.caption = TtmlNode.ANONYMOUS_REGION_ID;
                        }
                        if (str != null) {
                            hashMap.put("originalPath", str);
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (!(bitmap == null || str3 == null)) {
                                    ImageLoader.getInstance().putImageToCache(new BitmapDrawable(bitmap), str3);
                                }
                                SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_document, videoEditedInfo2, str2, j4, messageObject2, null, hashMap, i4);
                            }
                        });
                        return;
                    }
                    SendMessagesHelper.prepareSendingDocumentInternal(str2, str2, null, null, j4, messageObject2, charSequence2);
                }
            }).start();
        }
    }

    private void putToDelayedMessages(String str, DelayedMessage delayedMessage) {
        ArrayList arrayList = (ArrayList) this.delayedMessages.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.delayedMessages.put(str, arrayList);
        }
        arrayList.add(delayedMessage);
    }

    private void sendLocation(Location location) {
        MessageMedia tLRPC$TL_messageMediaGeo = new TLRPC$TL_messageMediaGeo();
        tLRPC$TL_messageMediaGeo.geo = new TLRPC$TL_geoPoint();
        tLRPC$TL_messageMediaGeo.geo.lat = location.getLatitude();
        tLRPC$TL_messageMediaGeo.geo._long = location.getLongitude();
        for (Entry value : this.waitingForLocation.entrySet()) {
            MessageObject messageObject = (MessageObject) value.getValue();
            getInstance().sendMessage(tLRPC$TL_messageMediaGeo, messageObject.getDialogId(), messageObject, null, null);
        }
    }

    private void sendMessage(String str, MessageMedia messageMedia, TLRPC$TL_photo tLRPC$TL_photo, VideoEditedInfo videoEditedInfo, User user, TLRPC$TL_document tLRPC$TL_document, TLRPC$TL_game tLRPC$TL_game, long j, String str2, MessageObject messageObject, TLRPC$WebPage tLRPC$WebPage, boolean z, MessageObject messageObject2, ArrayList<MessageEntity> arrayList, ReplyMarkup replyMarkup, HashMap<String, String> hashMap, int i) {
        Throwable e;
        Message message;
        if (j != 0) {
            String str3;
            Message message2;
            DelayedMessage delayedMessage;
            int i2;
            int i3;
            Object obj;
            InputPeer inputPeer;
            EncryptedChat encryptedChat;
            EncryptedChat encryptedChat2;
            Chat chat;
            Object obj2;
            MessageObject messageObject3;
            int i4;
            Document document;
            int i5;
            Message message3;
            TLRPC$WebPage tLRPC$TL_webPageUrlPending;
            MessageMedia messageMedia2;
            int i6;
            Message tLRPC$TL_message;
            String str4;
            int i7;
            DocumentAttribute documentAttribute;
            TLRPC$TL_documentAttributeSticker_layer55 tLRPC$TL_documentAttributeSticker_layer55;
            ArrayList arrayList2;
            User user2;
            ArrayList arrayList3;
            Iterator it;
            InputUser inputUser;
            long j2;
            String str5;
            Object obj3;
            long j3;
            ArrayList arrayList4;
            ArrayList arrayList5;
            ArrayList arrayList6;
            TLObject tLRPC$TL_messages_sendInlineBotResult;
            DecryptedMessage tLRPC$TL_decryptedMessage;
            if (hashMap != null) {
                if (hashMap.containsKey("originalPath")) {
                    str3 = (String) hashMap.get("originalPath");
                    message2 = null;
                    delayedMessage = null;
                    i2 = (int) j;
                    i3 = (int) (j >> 32);
                    obj = null;
                    inputPeer = i2 == 0 ? MessagesController.getInputPeer(i2) : null;
                    if (i2 == 0) {
                        encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i3));
                        if (encryptedChat == null) {
                            encryptedChat2 = encryptedChat;
                        } else if (messageObject2 != null) {
                            MessagesStorage.getInstance().markMessageAsSendError(messageObject2.messageOwner);
                            messageObject2.messageOwner.send_state = 2;
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(messageObject2.getId()));
                            processSentMessage(messageObject2.getId());
                            return;
                        } else {
                            return;
                        }
                    } else if (inputPeer instanceof TLRPC$TL_inputPeerChannel) {
                        encryptedChat2 = null;
                    } else {
                        chat = MessagesController.getInstance().getChat(Integer.valueOf(inputPeer.channel_id));
                        obj2 = (chat != null || chat.megagroup) ? null : 1;
                        encryptedChat2 = null;
                        obj = obj2;
                    }
                    if (messageObject2 == null) {
                        try {
                            message2 = messageObject2.messageOwner;
                        } catch (Exception e2) {
                            e = e2;
                            messageObject3 = null;
                            message = message2;
                            FileLog.m13728e(e);
                            MessagesStorage.getInstance().markMessageAsSendError(message);
                            if (messageObject3 != null) {
                                messageObject3.messageOwner.send_state = 2;
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(message.id));
                            processSentMessage(message.id);
                        }
                        try {
                            if (messageObject2.isForwarded()) {
                                if (messageObject2.type == 0) {
                                    if (!(messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaGame)) {
                                        str = message2.message;
                                    }
                                    i4 = 0;
                                } else if (messageObject2.type == 4) {
                                    messageMedia = message2.media;
                                    i4 = 1;
                                } else if (messageObject2.type != 1) {
                                    tLRPC$TL_photo = (TLRPC$TL_photo) message2.media.photo;
                                    i4 = 2;
                                } else if (messageObject2.type != 3 || messageObject2.type == 5 || videoEditedInfo != null) {
                                    document = (TLRPC$TL_document) message2.media.document;
                                    i4 = 3;
                                } else if (messageObject2.type == 12) {
                                    user = new TLRPC$TL_userRequest_old2();
                                    user.phone = message2.media.phone_number;
                                    user.first_name = message2.media.first_name;
                                    user.last_name = message2.media.last_name;
                                    user.id = message2.media.user_id;
                                    i4 = 6;
                                } else if (messageObject2.type == 8 || messageObject2.type == 9 || messageObject2.type == 13 || messageObject2.type == 14) {
                                    document = (TLRPC$TL_document) message2.media.document;
                                    i4 = 7;
                                } else if (messageObject2.type == 2) {
                                    document = (TLRPC$TL_document) message2.media.document;
                                    i4 = 8;
                                } else {
                                    i4 = -1;
                                }
                                if (hashMap != null) {
                                    if (hashMap.containsKey("query_id")) {
                                        i5 = 9;
                                        message3 = message2;
                                    }
                                }
                                i5 = i4;
                                message3 = message2;
                            } else {
                                i5 = 4;
                                message3 = message2;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            messageObject3 = null;
                            message = message2;
                            FileLog.m13728e(e);
                            MessagesStorage.getInstance().markMessageAsSendError(message);
                            if (messageObject3 != null) {
                                messageObject3.messageOwner.send_state = 2;
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(message.id));
                            processSentMessage(message.id);
                        }
                    }
                    if (str != null) {
                        message2 = encryptedChat2 == null ? new TLRPC$TL_message_secret() : new TLRPC$TL_message();
                        if (arrayList != null) {
                            try {
                                if (!arrayList.isEmpty()) {
                                    message2.entities = arrayList;
                                }
                            } catch (Exception e4) {
                                e = e4;
                                messageObject3 = null;
                                message = message2;
                                FileLog.m13728e(e);
                                MessagesStorage.getInstance().markMessageAsSendError(message);
                                if (messageObject3 != null) {
                                    messageObject3.messageOwner.send_state = 2;
                                }
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(message.id));
                                processSentMessage(message.id);
                            }
                        }
                        if (encryptedChat2 != null && (tLRPC$WebPage instanceof TLRPC$TL_webPagePending)) {
                            if (tLRPC$WebPage.url == null) {
                                tLRPC$TL_webPageUrlPending = new TLRPC$TL_webPageUrlPending();
                                tLRPC$TL_webPageUrlPending.url = tLRPC$WebPage.url;
                                tLRPC$WebPage = tLRPC$TL_webPageUrlPending;
                            } else {
                                tLRPC$WebPage = null;
                            }
                        }
                        if (tLRPC$WebPage != null) {
                            message2.media = new TLRPC$TL_messageMediaEmpty();
                        } else {
                            message2.media = new TLRPC$TL_messageMediaWebPage();
                            message2.media.webpage = tLRPC$WebPage;
                        }
                        if (hashMap != null) {
                            if (hashMap.containsKey("query_id")) {
                                i4 = 9;
                                message2.message = str;
                            }
                        }
                        i4 = 0;
                        message2.message = str;
                    } else if (messageMedia != null) {
                        message2 = encryptedChat2 == null ? new TLRPC$TL_message_secret() : new TLRPC$TL_message();
                        message2.media = messageMedia;
                        message2.message = TtmlNode.ANONYMOUS_REGION_ID;
                        if (hashMap != null) {
                            if (hashMap.containsKey("query_id")) {
                                i4 = 9;
                            }
                        }
                        i4 = 1;
                    } else if (tLRPC$TL_photo != null) {
                        message2 = encryptedChat2 == null ? new TLRPC$TL_message_secret() : new TLRPC$TL_message();
                        message2.media = new TLRPC$TL_messageMediaPhoto();
                        messageMedia2 = message2.media;
                        messageMedia2.flags |= 3;
                        message2.media.caption = tLRPC$TL_photo.caption == null ? tLRPC$TL_photo.caption : TtmlNode.ANONYMOUS_REGION_ID;
                        if (i != 0) {
                            message2.media.ttl_seconds = i;
                            message2.ttl = i;
                            messageMedia2 = message2.media;
                            messageMedia2.flags |= 4;
                        }
                        message2.media.photo = tLRPC$TL_photo;
                        if (hashMap != null) {
                            if (hashMap.containsKey("query_id")) {
                                i6 = 9;
                                message2.message = "-1";
                                if (str2 != null && str2.length() > 0) {
                                    if (str2.startsWith("http")) {
                                        message2.attachPath = str2;
                                        i4 = i6;
                                    }
                                }
                                message2.attachPath = FileLoader.getPathToAttach(((PhotoSize) tLRPC$TL_photo.sizes.get(tLRPC$TL_photo.sizes.size() - 1)).location, true).toString();
                                i4 = i6;
                            }
                        }
                        i6 = 2;
                        message2.message = "-1";
                        if (str2.startsWith("http")) {
                            message2.attachPath = str2;
                            i4 = i6;
                        }
                        message2.attachPath = FileLoader.getPathToAttach(((PhotoSize) tLRPC$TL_photo.sizes.get(tLRPC$TL_photo.sizes.size() - 1)).location, true).toString();
                        i4 = i6;
                    } else if (tLRPC$TL_game != null) {
                        tLRPC$TL_message = new TLRPC$TL_message();
                        try {
                            tLRPC$TL_message.media = new TLRPC$TL_messageMediaGame();
                            tLRPC$TL_message.media.caption = TtmlNode.ANONYMOUS_REGION_ID;
                            tLRPC$TL_message.media.game = tLRPC$TL_game;
                            tLRPC$TL_message.message = TtmlNode.ANONYMOUS_REGION_ID;
                            if (hashMap != null) {
                                if (hashMap.containsKey("query_id")) {
                                    i4 = 9;
                                    message2 = tLRPC$TL_message;
                                }
                            }
                            i4 = -1;
                            message2 = tLRPC$TL_message;
                        } catch (Exception e5) {
                            e = e5;
                            messageObject3 = null;
                            message = tLRPC$TL_message;
                            FileLog.m13728e(e);
                            MessagesStorage.getInstance().markMessageAsSendError(message);
                            if (messageObject3 != null) {
                                messageObject3.messageOwner.send_state = 2;
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(message.id));
                            processSentMessage(message.id);
                        }
                    } else if (user != null) {
                        message2 = encryptedChat2 == null ? new TLRPC$TL_message_secret() : new TLRPC$TL_message();
                        message2.media = new TLRPC$TL_messageMediaContact();
                        message2.media.phone_number = user.phone;
                        message2.media.first_name = user.first_name;
                        message2.media.last_name = user.last_name;
                        message2.media.user_id = user.id;
                        if (message2.media.first_name == null) {
                            messageMedia2 = message2.media;
                            str4 = TtmlNode.ANONYMOUS_REGION_ID;
                            messageMedia2.first_name = str4;
                            user.first_name = str4;
                        }
                        if (message2.media.last_name == null) {
                            messageMedia2 = message2.media;
                            str4 = TtmlNode.ANONYMOUS_REGION_ID;
                            messageMedia2.last_name = str4;
                            user.last_name = str4;
                        }
                        message2.message = TtmlNode.ANONYMOUS_REGION_ID;
                        if (hashMap != null) {
                            if (hashMap.containsKey("query_id")) {
                                i4 = 9;
                            }
                        }
                        i4 = 6;
                    } else if (tLRPC$TL_document == null) {
                        message2 = encryptedChat2 == null ? new TLRPC$TL_message_secret() : new TLRPC$TL_message();
                        message2.media = new TLRPC$TL_messageMediaDocument();
                        messageMedia2 = message2.media;
                        messageMedia2.flags |= 3;
                        if (i != 0) {
                            message2.media.ttl_seconds = i;
                            message2.ttl = i;
                            messageMedia2 = message2.media;
                            messageMedia2.flags |= 4;
                        }
                        message2.media.caption = tLRPC$TL_document.caption == null ? tLRPC$TL_document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                        message2.media.document = tLRPC$TL_document;
                        if (hashMap != null) {
                            if (hashMap.containsKey("query_id")) {
                                i6 = 9;
                                if (videoEditedInfo == null) {
                                    message2.message = "-1";
                                } else {
                                    message2.message = videoEditedInfo.getString();
                                }
                                if (encryptedChat2 != null || tLRPC$TL_document.dc_id <= 0 || MessageObject.isStickerDocument(tLRPC$TL_document)) {
                                    message2.attachPath = str2;
                                } else {
                                    message2.attachPath = FileLoader.getPathToAttach(tLRPC$TL_document).toString();
                                }
                                if (encryptedChat2 != null && MessageObject.isStickerDocument(tLRPC$TL_document)) {
                                    i7 = 0;
                                    while (i7 < tLRPC$TL_document.attributes.size()) {
                                        documentAttribute = (DocumentAttribute) tLRPC$TL_document.attributes.get(i7);
                                        if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                                            tLRPC$TL_document.attributes.remove(i7);
                                            tLRPC$TL_documentAttributeSticker_layer55 = new TLRPC$TL_documentAttributeSticker_layer55();
                                            tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeSticker_layer55);
                                            tLRPC$TL_documentAttributeSticker_layer55.alt = documentAttribute.alt;
                                            if (documentAttribute.stickerset != null) {
                                                obj2 = documentAttribute.stickerset instanceof TLRPC$TL_inputStickerSetShortName ? documentAttribute.stickerset.short_name : StickersQuery.getStickerSetName(documentAttribute.stickerset.id);
                                                if (TextUtils.isEmpty(obj2)) {
                                                    tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                                } else {
                                                    tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetShortName();
                                                    tLRPC$TL_documentAttributeSticker_layer55.stickerset.short_name = obj2;
                                                }
                                                i4 = i6;
                                            } else {
                                                tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                                i4 = i6;
                                            }
                                        } else {
                                            i7++;
                                        }
                                    }
                                }
                                i4 = i6;
                            }
                        }
                        if (MessageObject.isVideoDocument(tLRPC$TL_document) && !MessageObject.isRoundVideoDocument(tLRPC$TL_document) && videoEditedInfo == null) {
                            i6 = MessageObject.isVoiceDocument(tLRPC$TL_document) ? 8 : 7;
                            if (videoEditedInfo == null) {
                                message2.message = "-1";
                            } else {
                                message2.message = videoEditedInfo.getString();
                            }
                            if (encryptedChat2 != null) {
                            }
                            message2.attachPath = str2;
                            i7 = 0;
                            while (i7 < tLRPC$TL_document.attributes.size()) {
                                documentAttribute = (DocumentAttribute) tLRPC$TL_document.attributes.get(i7);
                                if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                                    tLRPC$TL_document.attributes.remove(i7);
                                    tLRPC$TL_documentAttributeSticker_layer55 = new TLRPC$TL_documentAttributeSticker_layer55();
                                    tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeSticker_layer55);
                                    tLRPC$TL_documentAttributeSticker_layer55.alt = documentAttribute.alt;
                                    if (documentAttribute.stickerset != null) {
                                        if (documentAttribute.stickerset instanceof TLRPC$TL_inputStickerSetShortName) {
                                        }
                                        if (TextUtils.isEmpty(obj2)) {
                                            tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetShortName();
                                            tLRPC$TL_documentAttributeSticker_layer55.stickerset.short_name = obj2;
                                        } else {
                                            tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                        }
                                        i4 = i6;
                                    } else {
                                        tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                        i4 = i6;
                                    }
                                } else {
                                    i7++;
                                }
                            }
                            i4 = i6;
                        } else {
                            i6 = 3;
                            if (videoEditedInfo == null) {
                                message2.message = videoEditedInfo.getString();
                            } else {
                                message2.message = "-1";
                            }
                            if (encryptedChat2 != null) {
                            }
                            message2.attachPath = str2;
                            i7 = 0;
                            while (i7 < tLRPC$TL_document.attributes.size()) {
                                documentAttribute = (DocumentAttribute) tLRPC$TL_document.attributes.get(i7);
                                if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                                    i7++;
                                } else {
                                    tLRPC$TL_document.attributes.remove(i7);
                                    tLRPC$TL_documentAttributeSticker_layer55 = new TLRPC$TL_documentAttributeSticker_layer55();
                                    tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeSticker_layer55);
                                    tLRPC$TL_documentAttributeSticker_layer55.alt = documentAttribute.alt;
                                    if (documentAttribute.stickerset != null) {
                                        tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                        i4 = i6;
                                    } else {
                                        if (documentAttribute.stickerset instanceof TLRPC$TL_inputStickerSetShortName) {
                                        }
                                        if (TextUtils.isEmpty(obj2)) {
                                            tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                        } else {
                                            tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetShortName();
                                            tLRPC$TL_documentAttributeSticker_layer55.stickerset.short_name = obj2;
                                        }
                                        i4 = i6;
                                    }
                                }
                            }
                            i4 = i6;
                        }
                    } else {
                        i4 = -1;
                    }
                    if (message2.attachPath == null) {
                        message2.attachPath = TtmlNode.ANONYMOUS_REGION_ID;
                    }
                    i6 = UserConfig.getNewMessageId();
                    message2.id = i6;
                    message2.local_id = i6;
                    message2.out = true;
                    if (obj != null || inputPeer == null) {
                        message2.from_id = UserConfig.getClientUserId();
                        message2.flags |= 256;
                    } else {
                        message2.from_id = -inputPeer.channel_id;
                    }
                    UserConfig.saveConfig(false);
                    i5 = i4;
                    message3 = message2;
                    if (message3.random_id == 0) {
                        message3.random_id = getNextRandomId();
                    }
                    if (hashMap != null) {
                        if (hashMap.containsKey("bot")) {
                            if (encryptedChat2 == null) {
                                message3.via_bot_name = (String) hashMap.get("bot_name");
                                if (message3.via_bot_name == null) {
                                    message3.via_bot_name = TtmlNode.ANONYMOUS_REGION_ID;
                                }
                            } else {
                                message3.via_bot_id = Utilities.parseInt((String) hashMap.get("bot")).intValue();
                            }
                            message3.flags |= 2048;
                        }
                    }
                    message3.params = hashMap;
                    if (messageObject2 == null || !messageObject2.resendAsIs) {
                        message3.date = ConnectionsManager.getInstance().getCurrentTime();
                        if (inputPeer instanceof TLRPC$TL_inputPeerChannel) {
                            message3.unread = true;
                        } else {
                            if (obj != null) {
                                message3.views = 1;
                                message3.flags |= 1024;
                            }
                            chat = MessagesController.getInstance().getChat(Integer.valueOf(inputPeer.channel_id));
                            if (chat != null) {
                                if (chat.megagroup) {
                                    message3.post = true;
                                    if (chat.signatures) {
                                        message3.from_id = UserConfig.getClientUserId();
                                    }
                                } else {
                                    message3.flags |= Integer.MIN_VALUE;
                                    message3.unread = true;
                                }
                            }
                        }
                    }
                    message3.flags |= 512;
                    message3.dialog_id = j;
                    if (messageObject != null) {
                        if (encryptedChat2 != null || messageObject.messageOwner.random_id == 0) {
                            message3.flags |= 8;
                        } else {
                            message3.reply_to_random_id = messageObject.messageOwner.random_id;
                            message3.flags |= 8;
                        }
                        message3.reply_to_msg_id = messageObject.getId();
                    }
                    if (replyMarkup != null && encryptedChat2 == null) {
                        message3.flags |= 64;
                        message3.reply_markup = replyMarkup;
                    }
                    if (i2 != 0) {
                        message3.to_id = new TLRPC$TL_peerUser();
                        if (encryptedChat2.participant_id != UserConfig.getClientUserId()) {
                            message3.to_id.user_id = encryptedChat2.admin_id;
                        } else {
                            message3.to_id.user_id = encryptedChat2.participant_id;
                        }
                        if (i == 0) {
                            message3.ttl = i;
                        } else {
                            message3.ttl = encryptedChat2.ttl;
                        }
                        if (!(message3.ttl == 0 || message3.media.document == null)) {
                            if (MessageObject.isVoiceMessage(message3)) {
                                for (i6 = 0; i6 < message3.media.document.attributes.size(); i6++) {
                                    documentAttribute = (DocumentAttribute) message3.media.document.attributes.get(i6);
                                    if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                                        i4 = documentAttribute.duration;
                                        break;
                                    }
                                }
                                i4 = 0;
                                message3.ttl = Math.max(message3.ttl, i4 + 1);
                                arrayList2 = null;
                            } else if (MessageObject.isVideoMessage(message3) || MessageObject.isRoundVideoMessage(message3)) {
                                for (i6 = 0; i6 < message3.media.document.attributes.size(); i6++) {
                                    documentAttribute = (DocumentAttribute) message3.media.document.attributes.get(i6);
                                    if (documentAttribute instanceof TLRPC$TL_documentAttributeVideo) {
                                        i4 = documentAttribute.duration;
                                        break;
                                    }
                                }
                                i4 = 0;
                                message3.ttl = Math.max(message3.ttl, i4 + 1);
                            }
                        }
                        arrayList2 = null;
                    } else if (i3 == 1) {
                        message3.to_id = MessagesController.getPeer(i2);
                        if (i2 > 0) {
                            user2 = MessagesController.getInstance().getUser(Integer.valueOf(i2));
                            if (user2 != null) {
                                processSentMessage(message3.id);
                                return;
                            }
                            if (user2.bot) {
                                message3.unread = false;
                            }
                            arrayList2 = null;
                        }
                        arrayList2 = null;
                    } else if (this.currentChatInfo != null) {
                        MessagesStorage.getInstance().markMessageAsSendError(message3);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(message3.id));
                        processSentMessage(message3.id);
                        return;
                    } else {
                        arrayList3 = new ArrayList();
                        it = this.currentChatInfo.participants.participants.iterator();
                        while (it.hasNext()) {
                            inputUser = MessagesController.getInputUser(MessagesController.getInstance().getUser(Integer.valueOf(((ChatParticipant) it.next()).user_id)));
                            if (inputUser != null) {
                                arrayList3.add(inputUser);
                            }
                        }
                        message3.to_id = new TLRPC$TL_peerChat();
                        message3.to_id.chat_id = i2;
                        arrayList2 = arrayList3;
                    }
                    if (i3 != 1 && (MessageObject.isVoiceMessage(message3) || MessageObject.isRoundVideoMessage(message3))) {
                        message3.media_unread = true;
                    }
                    message3.send_state = 1;
                    messageObject3 = new MessageObject(message3, null, true);
                    try {
                        messageObject3.replyMessageObject = messageObject;
                        if (!messageObject3.isForwarded() && ((messageObject3.type == 3 || videoEditedInfo != null || messageObject3.type == 2) && !TextUtils.isEmpty(message3.attachPath))) {
                            messageObject3.attachPathExists = true;
                        }
                        j2 = 0;
                        if (hashMap == null) {
                            str5 = (String) hashMap.get("groupId");
                            if (str5 != null) {
                                j2 = Utilities.parseLong(str5).longValue();
                                message3.grouped_id = j2;
                                message3.flags |= 131072;
                            }
                            obj3 = hashMap.get("final") == null ? 1 : null;
                            j3 = j2;
                        } else {
                            obj3 = null;
                            j3 = 0;
                        }
                        if (j3 != 0) {
                            arrayList4 = new ArrayList();
                            arrayList4.add(messageObject3);
                            arrayList5 = new ArrayList();
                            arrayList5.add(message3);
                            MessagesStorage.getInstance().putMessages(arrayList5, false, true, false, 0);
                            MessagesController.getInstance().updateInterfaceWithMessages(j, arrayList4);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                        } else {
                            arrayList6 = (ArrayList) this.delayedMessages.get("group_" + j3);
                            if (arrayList6 != null) {
                                delayedMessage = (DelayedMessage) arrayList6.get(0);
                            }
                            if (delayedMessage == null) {
                                delayedMessage = new DelayedMessage(j);
                                delayedMessage.type = 4;
                                delayedMessage.groupId = j3;
                                delayedMessage.messageObjects = new ArrayList();
                                delayedMessage.messages = new ArrayList();
                                delayedMessage.originalPaths = new ArrayList();
                                delayedMessage.extraHashMap = new HashMap();
                                delayedMessage.encryptedChat = encryptedChat2;
                            }
                            if (obj3 != null) {
                                delayedMessage.finalGroupMessage = message3.id;
                            }
                        }
                        if (BuildVars.DEBUG_VERSION && inputPeer != null) {
                            FileLog.m13726e("send message user_id = " + inputPeer.user_id + " chat_id = " + inputPeer.chat_id + " channel_id = " + inputPeer.channel_id + " access_hash = " + inputPeer.access_hash);
                        }
                        if (i5 == 0 && (i5 != 9 || str == null || encryptedChat2 == null)) {
                            TLObject tLRPC$TL_messages_forwardMessages;
                            if ((i5 < 1 || i5 > 3) && ((i5 < 5 || i5 > 8) && (i5 != 9 || encryptedChat2 == null))) {
                                if (i5 == 4) {
                                    tLRPC$TL_messages_forwardMessages = new TLRPC$TL_messages_forwardMessages();
                                    tLRPC$TL_messages_forwardMessages.to_peer = inputPeer;
                                    tLRPC$TL_messages_forwardMessages.with_my_score = messageObject2.messageOwner.with_my_score;
                                    if (messageObject2.messageOwner.ttl != 0) {
                                        Chat chat2 = MessagesController.getInstance().getChat(Integer.valueOf(-messageObject2.messageOwner.ttl));
                                        tLRPC$TL_messages_forwardMessages.from_peer = new TLRPC$TL_inputPeerChannel();
                                        tLRPC$TL_messages_forwardMessages.from_peer.channel_id = -messageObject2.messageOwner.ttl;
                                        if (chat2 != null) {
                                            tLRPC$TL_messages_forwardMessages.from_peer.access_hash = chat2.access_hash;
                                        }
                                    } else {
                                        tLRPC$TL_messages_forwardMessages.from_peer = new TLRPC$TL_inputPeerEmpty();
                                    }
                                    if (messageObject2.messageOwner.to_id instanceof TLRPC$TL_peerChannel) {
                                        tLRPC$TL_messages_forwardMessages.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + j, false);
                                    }
                                    tLRPC$TL_messages_forwardMessages.random_id.add(Long.valueOf(message3.random_id));
                                    if (messageObject2.getId() >= 0) {
                                        tLRPC$TL_messages_forwardMessages.id.add(Integer.valueOf(messageObject2.getId()));
                                    } else if (messageObject2.messageOwner.fwd_msg_id != 0) {
                                        tLRPC$TL_messages_forwardMessages.id.add(Integer.valueOf(messageObject2.messageOwner.fwd_msg_id));
                                    } else if (messageObject2.messageOwner.fwd_from != null) {
                                        tLRPC$TL_messages_forwardMessages.id.add(Integer.valueOf(messageObject2.messageOwner.fwd_from.channel_post));
                                    }
                                    performSendMessageRequest(tLRPC$TL_messages_forwardMessages, messageObject3, null);
                                    return;
                                } else if (i5 == 9) {
                                    tLRPC$TL_messages_sendInlineBotResult = new TLRPC$TL_messages_sendInlineBotResult();
                                    tLRPC$TL_messages_sendInlineBotResult.peer = inputPeer;
                                    tLRPC$TL_messages_sendInlineBotResult.random_id = message3.random_id;
                                    if (messageObject != null) {
                                        tLRPC$TL_messages_sendInlineBotResult.flags |= 1;
                                        tLRPC$TL_messages_sendInlineBotResult.reply_to_msg_id = messageObject.getId();
                                    }
                                    if (message3.to_id instanceof TLRPC$TL_peerChannel) {
                                        tLRPC$TL_messages_sendInlineBotResult.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + j, false);
                                    }
                                    tLRPC$TL_messages_sendInlineBotResult.query_id = Utilities.parseLong((String) hashMap.get("query_id")).longValue();
                                    tLRPC$TL_messages_sendInlineBotResult.id = (String) hashMap.get(TtmlNode.ATTR_ID);
                                    if (messageObject2 == null) {
                                        tLRPC$TL_messages_sendInlineBotResult.clear_draft = true;
                                        DraftQuery.cleanDraft(j, false);
                                    }
                                    performSendMessageRequest(tLRPC$TL_messages_sendInlineBotResult, messageObject3, null);
                                    return;
                                } else {
                                    return;
                                }
                            } else if (encryptedChat2 == null) {
                                InputMedia inputMedia;
                                TLObject tLRPC$TL_messages_sendBroadcast;
                                InputMedia tLRPC$TL_inputMediaVenue;
                                if (i5 == 1) {
                                    if (messageMedia instanceof TLRPC$TL_messageMediaVenue) {
                                        tLRPC$TL_inputMediaVenue = new TLRPC$TL_inputMediaVenue();
                                        tLRPC$TL_inputMediaVenue.address = messageMedia.address;
                                        tLRPC$TL_inputMediaVenue.title = messageMedia.title;
                                        tLRPC$TL_inputMediaVenue.provider = messageMedia.provider;
                                        tLRPC$TL_inputMediaVenue.venue_id = messageMedia.venue_id;
                                        tLRPC$TL_inputMediaVenue.venue_type = TtmlNode.ANONYMOUS_REGION_ID;
                                    } else if (messageMedia instanceof TLRPC$TL_messageMediaGeoLive) {
                                        tLRPC$TL_inputMediaVenue = new TLRPC$TL_inputMediaGeoLive();
                                        tLRPC$TL_inputMediaVenue.period = messageMedia.period;
                                    } else {
                                        tLRPC$TL_inputMediaVenue = new TLRPC$TL_inputMediaGeoPoint();
                                    }
                                    tLRPC$TL_inputMediaVenue.geo_point = new TLRPC$TL_inputGeoPoint();
                                    tLRPC$TL_inputMediaVenue.geo_point.lat = messageMedia.geo.lat;
                                    tLRPC$TL_inputMediaVenue.geo_point._long = messageMedia.geo._long;
                                    inputMedia = tLRPC$TL_inputMediaVenue;
                                } else if (i5 == 2 || (i5 == 9 && tLRPC$TL_photo != null)) {
                                    if (tLRPC$TL_photo.access_hash == 0) {
                                        inputMedia = new TLRPC$TL_inputMediaUploadedPhoto();
                                        inputMedia.caption = tLRPC$TL_photo.caption != null ? tLRPC$TL_photo.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                        if (i != 0) {
                                            inputMedia.ttl_seconds = i;
                                            message3.ttl = i;
                                            inputMedia.flags |= 2;
                                        }
                                        if (hashMap != null) {
                                            str5 = (String) hashMap.get("masks");
                                            if (str5 != null) {
                                                AbstractSerializedData serializedData = new SerializedData(Utilities.hexToBytes(str5));
                                                i6 = serializedData.readInt32(false);
                                                for (i4 = 0; i4 < i6; i4++) {
                                                    inputMedia.stickers.add(InputDocument.TLdeserialize(serializedData, serializedData.readInt32(false), false));
                                                }
                                                inputMedia.flags |= 1;
                                            }
                                        }
                                        if (delayedMessage == null) {
                                            delayedMessage = new DelayedMessage(j);
                                            delayedMessage.type = 0;
                                            delayedMessage.obj = messageObject3;
                                            delayedMessage.originalPath = str3;
                                        }
                                        if (str2 != null && str2.length() > 0) {
                                            if (str2.startsWith("http")) {
                                                delayedMessage.httpLocation = str2;
                                            }
                                        }
                                        delayedMessage.location = ((PhotoSize) tLRPC$TL_photo.sizes.get(tLRPC$TL_photo.sizes.size() - 1)).location;
                                    } else {
                                        tLRPC$TL_inputMediaVenue = new TLRPC$TL_inputMediaPhoto();
                                        tLRPC$TL_inputMediaVenue.id = new TLRPC$TL_inputPhoto();
                                        tLRPC$TL_inputMediaVenue.caption = tLRPC$TL_photo.caption != null ? tLRPC$TL_photo.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                        tLRPC$TL_inputMediaVenue.id.id = tLRPC$TL_photo.id;
                                        tLRPC$TL_inputMediaVenue.id.access_hash = tLRPC$TL_photo.access_hash;
                                        inputMedia = tLRPC$TL_inputMediaVenue;
                                    }
                                } else if (i5 == 3) {
                                    if (document.access_hash == 0) {
                                        tLRPC$TL_inputMediaVenue = new TLRPC$TL_inputMediaUploadedDocument();
                                        tLRPC$TL_inputMediaVenue.caption = document.caption != null ? document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                        tLRPC$TL_inputMediaVenue.mime_type = document.mime_type;
                                        tLRPC$TL_inputMediaVenue.attributes = document.attributes;
                                        if (!MessageObject.isRoundVideoDocument(document) && (videoEditedInfo == null || !(videoEditedInfo.muted || videoEditedInfo.roundVideo))) {
                                            tLRPC$TL_inputMediaVenue.nosound_video = true;
                                        }
                                        if (i != 0) {
                                            tLRPC$TL_inputMediaVenue.ttl_seconds = i;
                                            message3.ttl = i;
                                            tLRPC$TL_inputMediaVenue.flags |= 2;
                                        }
                                        if (delayedMessage == null) {
                                            delayedMessage = new DelayedMessage(j);
                                            delayedMessage.type = 1;
                                            delayedMessage.obj = messageObject3;
                                            delayedMessage.originalPath = str3;
                                        }
                                        delayedMessage.location = document.thumb.location;
                                        delayedMessage.videoEditedInfo = videoEditedInfo;
                                        inputMedia = tLRPC$TL_inputMediaVenue;
                                    } else {
                                        tLRPC$TL_inputMediaVenue = new TLRPC$TL_inputMediaDocument();
                                        tLRPC$TL_inputMediaVenue.id = new TLRPC$TL_inputDocument();
                                        tLRPC$TL_inputMediaVenue.caption = document.caption != null ? document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                        tLRPC$TL_inputMediaVenue.id.id = document.id;
                                        tLRPC$TL_inputMediaVenue.id.access_hash = document.access_hash;
                                        inputMedia = tLRPC$TL_inputMediaVenue;
                                    }
                                } else if (i5 == 6) {
                                    tLRPC$TL_inputMediaVenue = new TLRPC$TL_inputMediaContact();
                                    tLRPC$TL_inputMediaVenue.phone_number = user.phone;
                                    tLRPC$TL_inputMediaVenue.first_name = user.first_name;
                                    tLRPC$TL_inputMediaVenue.last_name = user.last_name;
                                    inputMedia = tLRPC$TL_inputMediaVenue;
                                } else if (i5 == 7 || i5 == 9) {
                                    if (document.access_hash == 0) {
                                        if (encryptedChat2 == null && str3 != null && str3.length() > 0) {
                                            if (str3.startsWith("http") && hashMap != null) {
                                                inputMedia = new TLRPC$TL_inputMediaGifExternal();
                                                String[] split = ((String) hashMap.get(ImagesContract.URL)).split("\\|");
                                                if (split.length == 2) {
                                                    ((TLRPC$TL_inputMediaGifExternal) inputMedia).url = split[0];
                                                    inputMedia.f10143q = split[1];
                                                }
                                                tLRPC$TL_inputMediaVenue = inputMedia;
                                                tLRPC$TL_inputMediaVenue.mime_type = document.mime_type;
                                                tLRPC$TL_inputMediaVenue.attributes = document.attributes;
                                                tLRPC$TL_inputMediaVenue.caption = document.caption == null ? document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                                inputMedia = tLRPC$TL_inputMediaVenue;
                                            }
                                        }
                                        tLRPC$TL_inputMediaVenue = new TLRPC$TL_inputMediaUploadedDocument();
                                        if (i != 0) {
                                            tLRPC$TL_inputMediaVenue.ttl_seconds = i;
                                            message3.ttl = i;
                                            tLRPC$TL_inputMediaVenue.flags |= 2;
                                        }
                                        delayedMessage = new DelayedMessage(j);
                                        delayedMessage.originalPath = str3;
                                        delayedMessage.type = 2;
                                        delayedMessage.obj = messageObject3;
                                        delayedMessage.location = document.thumb.location;
                                        tLRPC$TL_inputMediaVenue.mime_type = document.mime_type;
                                        tLRPC$TL_inputMediaVenue.attributes = document.attributes;
                                        if (document.caption == null) {
                                        }
                                        tLRPC$TL_inputMediaVenue.caption = document.caption == null ? document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                        inputMedia = tLRPC$TL_inputMediaVenue;
                                    } else {
                                        tLRPC$TL_inputMediaVenue = new TLRPC$TL_inputMediaDocument();
                                        tLRPC$TL_inputMediaVenue.id = new TLRPC$TL_inputDocument();
                                        tLRPC$TL_inputMediaVenue.id.id = document.id;
                                        tLRPC$TL_inputMediaVenue.id.access_hash = document.access_hash;
                                        tLRPC$TL_inputMediaVenue.caption = document.caption != null ? document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                        inputMedia = tLRPC$TL_inputMediaVenue;
                                    }
                                } else if (i5 != 8) {
                                    inputMedia = null;
                                } else if (document.access_hash == 0) {
                                    tLRPC$TL_inputMediaVenue = new TLRPC$TL_inputMediaUploadedDocument();
                                    tLRPC$TL_inputMediaVenue.mime_type = document.mime_type;
                                    tLRPC$TL_inputMediaVenue.attributes = document.attributes;
                                    tLRPC$TL_inputMediaVenue.caption = document.caption != null ? document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                    if (i != 0) {
                                        tLRPC$TL_inputMediaVenue.ttl_seconds = i;
                                        message3.ttl = i;
                                        tLRPC$TL_inputMediaVenue.flags |= 2;
                                    }
                                    delayedMessage = new DelayedMessage(j);
                                    delayedMessage.type = 3;
                                    delayedMessage.obj = messageObject3;
                                    inputMedia = tLRPC$TL_inputMediaVenue;
                                } else {
                                    tLRPC$TL_inputMediaVenue = new TLRPC$TL_inputMediaDocument();
                                    tLRPC$TL_inputMediaVenue.id = new TLRPC$TL_inputDocument();
                                    tLRPC$TL_inputMediaVenue.caption = document.caption != null ? document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                    tLRPC$TL_inputMediaVenue.id.id = document.id;
                                    tLRPC$TL_inputMediaVenue.id.access_hash = document.access_hash;
                                    inputMedia = tLRPC$TL_inputMediaVenue;
                                }
                                if (arrayList2 != null) {
                                    tLRPC$TL_messages_sendBroadcast = new TLRPC$TL_messages_sendBroadcast();
                                    arrayList3 = new ArrayList();
                                    for (i4 = 0; i4 < arrayList2.size(); i4++) {
                                        arrayList3.add(Long.valueOf(Utilities.random.nextLong()));
                                    }
                                    tLRPC$TL_messages_sendBroadcast.contacts = arrayList2;
                                    tLRPC$TL_messages_sendBroadcast.media = inputMedia;
                                    tLRPC$TL_messages_sendBroadcast.random_id = arrayList3;
                                    tLRPC$TL_messages_sendBroadcast.message = TtmlNode.ANONYMOUS_REGION_ID;
                                    if (delayedMessage != null) {
                                        delayedMessage.sendRequest = tLRPC$TL_messages_sendBroadcast;
                                    }
                                    if (messageObject2 == null) {
                                        DraftQuery.cleanDraft(j, false);
                                    }
                                } else if (j3 != 0) {
                                    if (delayedMessage.sendRequest != null) {
                                        tLRPC$TL_messages_sendBroadcast = (TLRPC$TL_messages_sendMultiMedia) delayedMessage.sendRequest;
                                    } else {
                                        tLRPC$TL_messages_sendBroadcast = new TLRPC$TL_messages_sendMultiMedia();
                                        tLRPC$TL_messages_sendBroadcast.peer = inputPeer;
                                        if (message3.to_id instanceof TLRPC$TL_peerChannel) {
                                            tLRPC$TL_messages_sendBroadcast.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + j, false);
                                        }
                                        if (messageObject != null) {
                                            tLRPC$TL_messages_sendBroadcast.flags |= 1;
                                            tLRPC$TL_messages_sendBroadcast.reply_to_msg_id = messageObject.getId();
                                        }
                                        delayedMessage.sendRequest = tLRPC$TL_messages_sendBroadcast;
                                    }
                                    delayedMessage.messageObjects.add(messageObject3);
                                    delayedMessage.messages.add(message3);
                                    delayedMessage.originalPaths.add(str3);
                                    TLRPC$TL_inputSingleMedia tLRPC$TL_inputSingleMedia = new TLRPC$TL_inputSingleMedia();
                                    tLRPC$TL_inputSingleMedia.random_id = message3.random_id;
                                    tLRPC$TL_inputSingleMedia.media = inputMedia;
                                    tLRPC$TL_messages_sendBroadcast.multi_media.add(tLRPC$TL_inputSingleMedia);
                                } else {
                                    tLRPC$TL_messages_sendBroadcast = new TLRPC$TL_messages_sendMedia();
                                    tLRPC$TL_messages_sendBroadcast.peer = inputPeer;
                                    if (message3.to_id instanceof TLRPC$TL_peerChannel) {
                                        tLRPC$TL_messages_sendBroadcast.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + j, false);
                                    }
                                    if (messageObject != null) {
                                        tLRPC$TL_messages_sendBroadcast.flags |= 1;
                                        tLRPC$TL_messages_sendBroadcast.reply_to_msg_id = messageObject.getId();
                                    }
                                    tLRPC$TL_messages_sendBroadcast.random_id = message3.random_id;
                                    tLRPC$TL_messages_sendBroadcast.media = inputMedia;
                                    if (delayedMessage != null) {
                                        delayedMessage.sendRequest = tLRPC$TL_messages_sendBroadcast;
                                    }
                                }
                                if (j3 != 0) {
                                    performSendDelayedMessage(delayedMessage);
                                    return;
                                } else if (i5 == 1) {
                                    performSendMessageRequest(tLRPC$TL_messages_sendBroadcast, messageObject3, null);
                                    return;
                                } else if (i5 == 2) {
                                    if (tLRPC$TL_photo.access_hash == 0) {
                                        performSendDelayedMessage(delayedMessage);
                                        return;
                                    } else {
                                        performSendMessageRequest(tLRPC$TL_messages_sendBroadcast, messageObject3, null, null, true);
                                        return;
                                    }
                                } else if (i5 == 3) {
                                    if (document.access_hash == 0) {
                                        performSendDelayedMessage(delayedMessage);
                                        return;
                                    } else {
                                        performSendMessageRequest(tLRPC$TL_messages_sendBroadcast, messageObject3, null);
                                        return;
                                    }
                                } else if (i5 == 6) {
                                    performSendMessageRequest(tLRPC$TL_messages_sendBroadcast, messageObject3, null);
                                    return;
                                } else if (i5 == 7) {
                                    if (document.access_hash != 0 || delayedMessage == null) {
                                        performSendMessageRequest(tLRPC$TL_messages_sendBroadcast, messageObject3, str3);
                                        return;
                                    } else {
                                        performSendDelayedMessage(delayedMessage);
                                        return;
                                    }
                                } else if (i5 != 8) {
                                    return;
                                } else {
                                    if (document.access_hash == 0) {
                                        performSendDelayedMessage(delayedMessage);
                                        return;
                                    } else {
                                        performSendMessageRequest(tLRPC$TL_messages_sendBroadcast, messageObject3, null);
                                        return;
                                    }
                                }
                            } else {
                                if (AndroidUtilities.getPeerLayerVersion(encryptedChat2.layer) >= 73) {
                                    tLRPC$TL_messages_sendInlineBotResult = new TLRPC$TL_decryptedMessage();
                                    if (j3 != 0) {
                                        tLRPC$TL_messages_sendInlineBotResult.grouped_id = j3;
                                        tLRPC$TL_messages_sendInlineBotResult.flags |= 131072;
                                    }
                                } else {
                                    tLRPC$TL_messages_sendInlineBotResult = new TLRPC$TL_decryptedMessage_layer45();
                                }
                                tLRPC$TL_messages_sendInlineBotResult.ttl = message3.ttl;
                                if (!(arrayList == null || arrayList.isEmpty())) {
                                    tLRPC$TL_messages_sendInlineBotResult.entities = arrayList;
                                    tLRPC$TL_messages_sendInlineBotResult.flags |= 128;
                                }
                                if (!(messageObject == null || messageObject.messageOwner.random_id == 0)) {
                                    tLRPC$TL_messages_sendInlineBotResult.reply_to_random_id = messageObject.messageOwner.random_id;
                                    tLRPC$TL_messages_sendInlineBotResult.flags |= 8;
                                }
                                tLRPC$TL_messages_sendInlineBotResult.flags |= 512;
                                if (hashMap != null) {
                                    if (hashMap.get("bot_name") != null) {
                                        tLRPC$TL_messages_sendInlineBotResult.via_bot_name = (String) hashMap.get("bot_name");
                                        tLRPC$TL_messages_sendInlineBotResult.flags |= 2048;
                                    }
                                }
                                tLRPC$TL_messages_sendInlineBotResult.random_id = message3.random_id;
                                tLRPC$TL_messages_sendInlineBotResult.message = TtmlNode.ANONYMOUS_REGION_ID;
                                if (i5 == 1) {
                                    if (messageMedia instanceof TLRPC$TL_messageMediaVenue) {
                                        tLRPC$TL_messages_sendInlineBotResult.media = new TLRPC$TL_decryptedMessageMediaVenue();
                                        tLRPC$TL_messages_sendInlineBotResult.media.address = messageMedia.address;
                                        tLRPC$TL_messages_sendInlineBotResult.media.title = messageMedia.title;
                                        tLRPC$TL_messages_sendInlineBotResult.media.provider = messageMedia.provider;
                                        tLRPC$TL_messages_sendInlineBotResult.media.venue_id = messageMedia.venue_id;
                                    } else {
                                        tLRPC$TL_messages_sendInlineBotResult.media = new TLRPC$TL_decryptedMessageMediaGeoPoint();
                                    }
                                    tLRPC$TL_messages_sendInlineBotResult.media.lat = messageMedia.geo.lat;
                                    tLRPC$TL_messages_sendInlineBotResult.media._long = messageMedia.geo._long;
                                    SecretChatHelper.getInstance().performSendEncryptedRequest(tLRPC$TL_messages_sendInlineBotResult, messageObject3.messageOwner, encryptedChat2, null, null, messageObject3);
                                } else if (i5 == 2 || (i5 == 9 && tLRPC$TL_photo != null)) {
                                    DelayedMessage delayedMessage2;
                                    PhotoSize photoSize = (PhotoSize) tLRPC$TL_photo.sizes.get(0);
                                    PhotoSize photoSize2 = (PhotoSize) tLRPC$TL_photo.sizes.get(tLRPC$TL_photo.sizes.size() - 1);
                                    ImageLoader.fillPhotoSizeWithBytes(photoSize);
                                    tLRPC$TL_messages_sendInlineBotResult.media = new TLRPC$TL_decryptedMessageMediaPhoto();
                                    tLRPC$TL_messages_sendInlineBotResult.media.caption = tLRPC$TL_photo.caption != null ? tLRPC$TL_photo.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                    if (photoSize.bytes != null) {
                                        ((TLRPC$TL_decryptedMessageMediaPhoto) tLRPC$TL_messages_sendInlineBotResult.media).thumb = photoSize.bytes;
                                    } else {
                                        ((TLRPC$TL_decryptedMessageMediaPhoto) tLRPC$TL_messages_sendInlineBotResult.media).thumb = new byte[0];
                                    }
                                    tLRPC$TL_messages_sendInlineBotResult.media.thumb_h = photoSize.f10146h;
                                    tLRPC$TL_messages_sendInlineBotResult.media.thumb_w = photoSize.f10147w;
                                    tLRPC$TL_messages_sendInlineBotResult.media.f10138w = photoSize2.f10147w;
                                    tLRPC$TL_messages_sendInlineBotResult.media.f10137h = photoSize2.f10146h;
                                    tLRPC$TL_messages_sendInlineBotResult.media.size = photoSize2.size;
                                    if (photoSize2.location.key == null || j3 != 0) {
                                        DelayedMessage delayedMessage3;
                                        if (delayedMessage == null) {
                                            delayedMessage3 = new DelayedMessage(j);
                                            delayedMessage3.encryptedChat = encryptedChat2;
                                            delayedMessage3.type = 0;
                                            delayedMessage3.originalPath = str3;
                                            delayedMessage3.sendEncryptedRequest = tLRPC$TL_messages_sendInlineBotResult;
                                            delayedMessage3.obj = messageObject3;
                                        } else {
                                            delayedMessage3 = delayedMessage;
                                        }
                                        if (!TextUtils.isEmpty(str2)) {
                                            if (str2.startsWith("http")) {
                                                delayedMessage3.httpLocation = str2;
                                                if (j3 != 0) {
                                                    performSendDelayedMessage(delayedMessage3);
                                                    delayedMessage2 = delayedMessage3;
                                                } else {
                                                    delayedMessage2 = delayedMessage3;
                                                }
                                            }
                                        }
                                        delayedMessage3.location = ((PhotoSize) tLRPC$TL_photo.sizes.get(tLRPC$TL_photo.sizes.size() - 1)).location;
                                        if (j3 != 0) {
                                            delayedMessage2 = delayedMessage3;
                                        } else {
                                            performSendDelayedMessage(delayedMessage3);
                                            delayedMessage2 = delayedMessage3;
                                        }
                                    } else {
                                        r8 = new TLRPC$TL_inputEncryptedFile();
                                        r8.id = photoSize2.location.volume_id;
                                        r8.access_hash = photoSize2.location.secret;
                                        tLRPC$TL_messages_sendInlineBotResult.media.key = photoSize2.location.key;
                                        tLRPC$TL_messages_sendInlineBotResult.media.iv = photoSize2.location.iv;
                                        SecretChatHelper.getInstance().performSendEncryptedRequest(tLRPC$TL_messages_sendInlineBotResult, messageObject3.messageOwner, encryptedChat2, r8, null, messageObject3);
                                        delayedMessage2 = delayedMessage;
                                    }
                                    delayedMessage = delayedMessage2;
                                } else if (i5 == 3) {
                                    ImageLoader.fillPhotoSizeWithBytes(document.thumb);
                                    if (MessageObject.isNewGifDocument(document) || MessageObject.isRoundVideoDocument(document)) {
                                        tLRPC$TL_messages_sendInlineBotResult.media = new TLRPC$TL_decryptedMessageMediaDocument();
                                        tLRPC$TL_messages_sendInlineBotResult.media.attributes = document.attributes;
                                        if (document.thumb == null || document.thumb.bytes == null) {
                                            ((TLRPC$TL_decryptedMessageMediaDocument) tLRPC$TL_messages_sendInlineBotResult.media).thumb = new byte[0];
                                        } else {
                                            ((TLRPC$TL_decryptedMessageMediaDocument) tLRPC$TL_messages_sendInlineBotResult.media).thumb = document.thumb.bytes;
                                        }
                                    } else {
                                        tLRPC$TL_messages_sendInlineBotResult.media = new TLRPC$TL_decryptedMessageMediaVideo();
                                        if (document.thumb == null || document.thumb.bytes == null) {
                                            ((TLRPC$TL_decryptedMessageMediaVideo) tLRPC$TL_messages_sendInlineBotResult.media).thumb = new byte[0];
                                        } else {
                                            ((TLRPC$TL_decryptedMessageMediaVideo) tLRPC$TL_messages_sendInlineBotResult.media).thumb = document.thumb.bytes;
                                        }
                                    }
                                    tLRPC$TL_messages_sendInlineBotResult.media.caption = document.caption != null ? document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                    tLRPC$TL_messages_sendInlineBotResult.media.mime_type = MimeTypes.VIDEO_MP4;
                                    tLRPC$TL_messages_sendInlineBotResult.media.size = document.size;
                                    for (int i8 = 0; i8 < document.attributes.size(); i8++) {
                                        documentAttribute = (DocumentAttribute) document.attributes.get(i8);
                                        if (documentAttribute instanceof TLRPC$TL_documentAttributeVideo) {
                                            tLRPC$TL_messages_sendInlineBotResult.media.f10138w = documentAttribute.f10140w;
                                            tLRPC$TL_messages_sendInlineBotResult.media.f10137h = documentAttribute.f10139h;
                                            tLRPC$TL_messages_sendInlineBotResult.media.duration = documentAttribute.duration;
                                            break;
                                        }
                                    }
                                    tLRPC$TL_messages_sendInlineBotResult.media.thumb_h = document.thumb.f10146h;
                                    tLRPC$TL_messages_sendInlineBotResult.media.thumb_w = document.thumb.f10147w;
                                    if (document.key == null || j3 != 0) {
                                        if (delayedMessage == null) {
                                            delayedMessage = new DelayedMessage(j);
                                            delayedMessage.encryptedChat = encryptedChat2;
                                            delayedMessage.type = 1;
                                            delayedMessage.sendEncryptedRequest = tLRPC$TL_messages_sendInlineBotResult;
                                            delayedMessage.originalPath = str3;
                                            delayedMessage.obj = messageObject3;
                                        }
                                        delayedMessage.videoEditedInfo = videoEditedInfo;
                                        if (j3 == 0) {
                                            performSendDelayedMessage(delayedMessage);
                                        }
                                    } else {
                                        r8 = new TLRPC$TL_inputEncryptedFile();
                                        r8.id = document.id;
                                        r8.access_hash = document.access_hash;
                                        tLRPC$TL_messages_sendInlineBotResult.media.key = document.key;
                                        tLRPC$TL_messages_sendInlineBotResult.media.iv = document.iv;
                                        SecretChatHelper.getInstance().performSendEncryptedRequest(tLRPC$TL_messages_sendInlineBotResult, messageObject3.messageOwner, encryptedChat2, r8, null, messageObject3);
                                    }
                                } else if (i5 == 6) {
                                    tLRPC$TL_messages_sendInlineBotResult.media = new TLRPC$TL_decryptedMessageMediaContact();
                                    tLRPC$TL_messages_sendInlineBotResult.media.phone_number = user.phone;
                                    tLRPC$TL_messages_sendInlineBotResult.media.first_name = user.first_name;
                                    tLRPC$TL_messages_sendInlineBotResult.media.last_name = user.last_name;
                                    tLRPC$TL_messages_sendInlineBotResult.media.user_id = user.id;
                                    SecretChatHelper.getInstance().performSendEncryptedRequest(tLRPC$TL_messages_sendInlineBotResult, messageObject3.messageOwner, encryptedChat2, null, null, messageObject3);
                                } else if (i5 == 7 || (i5 == 9 && document != null)) {
                                    if (MessageObject.isStickerDocument(document)) {
                                        tLRPC$TL_messages_sendInlineBotResult.media = new TLRPC$TL_decryptedMessageMediaExternalDocument();
                                        tLRPC$TL_messages_sendInlineBotResult.media.id = document.id;
                                        tLRPC$TL_messages_sendInlineBotResult.media.date = document.date;
                                        tLRPC$TL_messages_sendInlineBotResult.media.access_hash = document.access_hash;
                                        tLRPC$TL_messages_sendInlineBotResult.media.mime_type = document.mime_type;
                                        tLRPC$TL_messages_sendInlineBotResult.media.size = document.size;
                                        tLRPC$TL_messages_sendInlineBotResult.media.dc_id = document.dc_id;
                                        tLRPC$TL_messages_sendInlineBotResult.media.attributes = document.attributes;
                                        if (document.thumb == null) {
                                            ((TLRPC$TL_decryptedMessageMediaExternalDocument) tLRPC$TL_messages_sendInlineBotResult.media).thumb = new TLRPC$TL_photoSizeEmpty();
                                            ((TLRPC$TL_decryptedMessageMediaExternalDocument) tLRPC$TL_messages_sendInlineBotResult.media).thumb.type = "s";
                                        } else {
                                            ((TLRPC$TL_decryptedMessageMediaExternalDocument) tLRPC$TL_messages_sendInlineBotResult.media).thumb = document.thumb;
                                        }
                                        SecretChatHelper.getInstance().performSendEncryptedRequest(tLRPC$TL_messages_sendInlineBotResult, messageObject3.messageOwner, encryptedChat2, null, null, messageObject3);
                                    } else {
                                        ImageLoader.fillPhotoSizeWithBytes(document.thumb);
                                        tLRPC$TL_messages_sendInlineBotResult.media = new TLRPC$TL_decryptedMessageMediaDocument();
                                        tLRPC$TL_messages_sendInlineBotResult.media.attributes = document.attributes;
                                        tLRPC$TL_messages_sendInlineBotResult.media.caption = document.caption != null ? document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                        if (document.thumb == null || document.thumb.bytes == null) {
                                            ((TLRPC$TL_decryptedMessageMediaDocument) tLRPC$TL_messages_sendInlineBotResult.media).thumb = new byte[0];
                                            tLRPC$TL_messages_sendInlineBotResult.media.thumb_h = 0;
                                            tLRPC$TL_messages_sendInlineBotResult.media.thumb_w = 0;
                                        } else {
                                            ((TLRPC$TL_decryptedMessageMediaDocument) tLRPC$TL_messages_sendInlineBotResult.media).thumb = document.thumb.bytes;
                                            tLRPC$TL_messages_sendInlineBotResult.media.thumb_h = document.thumb.f10146h;
                                            tLRPC$TL_messages_sendInlineBotResult.media.thumb_w = document.thumb.f10147w;
                                        }
                                        tLRPC$TL_messages_sendInlineBotResult.media.size = document.size;
                                        tLRPC$TL_messages_sendInlineBotResult.media.mime_type = document.mime_type;
                                        if (document.key == null) {
                                            delayedMessage = new DelayedMessage(j);
                                            delayedMessage.originalPath = str3;
                                            delayedMessage.sendEncryptedRequest = tLRPC$TL_messages_sendInlineBotResult;
                                            delayedMessage.type = 2;
                                            delayedMessage.obj = messageObject3;
                                            delayedMessage.encryptedChat = encryptedChat2;
                                            if (str2 != null && str2.length() > 0) {
                                                if (str2.startsWith("http")) {
                                                    delayedMessage.httpLocation = str2;
                                                }
                                            }
                                            performSendDelayedMessage(delayedMessage);
                                        } else {
                                            r8 = new TLRPC$TL_inputEncryptedFile();
                                            r8.id = document.id;
                                            r8.access_hash = document.access_hash;
                                            tLRPC$TL_messages_sendInlineBotResult.media.key = document.key;
                                            tLRPC$TL_messages_sendInlineBotResult.media.iv = document.iv;
                                            SecretChatHelper.getInstance().performSendEncryptedRequest(tLRPC$TL_messages_sendInlineBotResult, messageObject3.messageOwner, encryptedChat2, r8, null, messageObject3);
                                        }
                                    }
                                } else if (i5 == 8) {
                                    delayedMessage = new DelayedMessage(j);
                                    delayedMessage.encryptedChat = encryptedChat2;
                                    delayedMessage.sendEncryptedRequest = tLRPC$TL_messages_sendInlineBotResult;
                                    delayedMessage.obj = messageObject3;
                                    delayedMessage.type = 3;
                                    tLRPC$TL_messages_sendInlineBotResult.media = new TLRPC$TL_decryptedMessageMediaDocument();
                                    tLRPC$TL_messages_sendInlineBotResult.media.attributes = document.attributes;
                                    tLRPC$TL_messages_sendInlineBotResult.media.caption = document.caption != null ? document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                                    if (document.thumb == null || document.thumb.bytes == null) {
                                        ((TLRPC$TL_decryptedMessageMediaDocument) tLRPC$TL_messages_sendInlineBotResult.media).thumb = new byte[0];
                                        tLRPC$TL_messages_sendInlineBotResult.media.thumb_h = 0;
                                        tLRPC$TL_messages_sendInlineBotResult.media.thumb_w = 0;
                                    } else {
                                        ((TLRPC$TL_decryptedMessageMediaDocument) tLRPC$TL_messages_sendInlineBotResult.media).thumb = document.thumb.bytes;
                                        tLRPC$TL_messages_sendInlineBotResult.media.thumb_h = document.thumb.f10146h;
                                        tLRPC$TL_messages_sendInlineBotResult.media.thumb_w = document.thumb.f10147w;
                                    }
                                    tLRPC$TL_messages_sendInlineBotResult.media.mime_type = document.mime_type;
                                    tLRPC$TL_messages_sendInlineBotResult.media.size = document.size;
                                    delayedMessage.originalPath = str3;
                                    performSendDelayedMessage(delayedMessage);
                                }
                                if (j3 != 0) {
                                    TLRPC$TL_messages_sendEncryptedMultiMedia tLRPC$TL_messages_sendEncryptedMultiMedia;
                                    if (delayedMessage.sendEncryptedRequest != null) {
                                        tLRPC$TL_messages_sendEncryptedMultiMedia = (TLRPC$TL_messages_sendEncryptedMultiMedia) delayedMessage.sendEncryptedRequest;
                                    } else {
                                        tLRPC$TL_messages_forwardMessages = new TLRPC$TL_messages_sendEncryptedMultiMedia();
                                        delayedMessage.sendEncryptedRequest = tLRPC$TL_messages_forwardMessages;
                                        TLObject tLObject = tLRPC$TL_messages_forwardMessages;
                                    }
                                    delayedMessage.messageObjects.add(messageObject3);
                                    delayedMessage.messages.add(message3);
                                    delayedMessage.originalPaths.add(str3);
                                    delayedMessage.upload = true;
                                    tLRPC$TL_messages_sendEncryptedMultiMedia.messages.add(tLRPC$TL_messages_sendInlineBotResult);
                                    TLRPC$TL_inputEncryptedFile tLRPC$TL_inputEncryptedFile = new TLRPC$TL_inputEncryptedFile();
                                    tLRPC$TL_inputEncryptedFile.id = i5 == 3 ? 1 : 0;
                                    tLRPC$TL_messages_sendEncryptedMultiMedia.files.add(tLRPC$TL_inputEncryptedFile);
                                    performSendDelayedMessage(delayedMessage);
                                }
                                if (messageObject2 == null) {
                                    DraftQuery.cleanDraft(j, false);
                                    return;
                                }
                                return;
                            }
                        } else if (encryptedChat2 == null) {
                            tLRPC$TL_decryptedMessage = AndroidUtilities.getPeerLayerVersion(encryptedChat2.layer) < 73 ? new TLRPC$TL_decryptedMessage() : new TLRPC$TL_decryptedMessage_layer45();
                            tLRPC$TL_decryptedMessage.ttl = message3.ttl;
                            if (!(arrayList == null || arrayList.isEmpty())) {
                                tLRPC$TL_decryptedMessage.entities = arrayList;
                                tLRPC$TL_decryptedMessage.flags |= 128;
                            }
                            if (!(messageObject == null || messageObject.messageOwner.random_id == 0)) {
                                tLRPC$TL_decryptedMessage.reply_to_random_id = messageObject.messageOwner.random_id;
                                tLRPC$TL_decryptedMessage.flags |= 8;
                            }
                            if (hashMap != null) {
                                if (hashMap.get("bot_name") != null) {
                                    tLRPC$TL_decryptedMessage.via_bot_name = (String) hashMap.get("bot_name");
                                    tLRPC$TL_decryptedMessage.flags |= 2048;
                                }
                            }
                            tLRPC$TL_decryptedMessage.random_id = message3.random_id;
                            tLRPC$TL_decryptedMessage.message = str;
                            if (tLRPC$WebPage != null || tLRPC$WebPage.url == null) {
                                tLRPC$TL_decryptedMessage.media = new TLRPC$TL_decryptedMessageMediaEmpty();
                            } else {
                                tLRPC$TL_decryptedMessage.media = new TLRPC$TL_decryptedMessageMediaWebPage();
                                tLRPC$TL_decryptedMessage.media.url = tLRPC$WebPage.url;
                                tLRPC$TL_decryptedMessage.flags |= 512;
                            }
                            SecretChatHelper.getInstance().performSendEncryptedRequest(tLRPC$TL_decryptedMessage, messageObject3.messageOwner, encryptedChat2, null, null, messageObject3);
                            if (messageObject2 == null) {
                                DraftQuery.cleanDraft(j, false);
                            }
                        } else if (arrayList2 == null) {
                            tLRPC$TL_messages_sendInlineBotResult = new TLRPC$TL_messages_sendBroadcast();
                            arrayList3 = new ArrayList();
                            for (i4 = 0; i4 < arrayList2.size(); i4++) {
                                arrayList3.add(Long.valueOf(Utilities.random.nextLong()));
                            }
                            tLRPC$TL_messages_sendInlineBotResult.message = str;
                            tLRPC$TL_messages_sendInlineBotResult.contacts = arrayList2;
                            tLRPC$TL_messages_sendInlineBotResult.media = new TLRPC$TL_inputMediaEmpty();
                            tLRPC$TL_messages_sendInlineBotResult.random_id = arrayList3;
                            performSendMessageRequest(tLRPC$TL_messages_sendInlineBotResult, messageObject3, null);
                        } else {
                            tLRPC$TL_messages_sendInlineBotResult = new TLRPC$TL_messages_sendMessage();
                            tLRPC$TL_messages_sendInlineBotResult.message = str;
                            tLRPC$TL_messages_sendInlineBotResult.clear_draft = messageObject2 != null;
                            if (message3.to_id instanceof TLRPC$TL_peerChannel) {
                                tLRPC$TL_messages_sendInlineBotResult.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + j, false);
                            }
                            tLRPC$TL_messages_sendInlineBotResult.peer = inputPeer;
                            tLRPC$TL_messages_sendInlineBotResult.random_id = message3.random_id;
                            if (messageObject != null) {
                                tLRPC$TL_messages_sendInlineBotResult.flags |= 1;
                                tLRPC$TL_messages_sendInlineBotResult.reply_to_msg_id = messageObject.getId();
                            }
                            if (!z) {
                                tLRPC$TL_messages_sendInlineBotResult.no_webpage = true;
                            }
                            if (!(arrayList == null || arrayList.isEmpty())) {
                                tLRPC$TL_messages_sendInlineBotResult.entities = arrayList;
                                tLRPC$TL_messages_sendInlineBotResult.flags |= 8;
                            }
                            performSendMessageRequest(tLRPC$TL_messages_sendInlineBotResult, messageObject3, null);
                            if (messageObject2 == null) {
                                DraftQuery.cleanDraft(j, false);
                            }
                        }
                    } catch (Exception e6) {
                        e = e6;
                        message = message3;
                        FileLog.m13728e(e);
                        MessagesStorage.getInstance().markMessageAsSendError(message);
                        if (messageObject3 != null) {
                            messageObject3.messageOwner.send_state = 2;
                        }
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(message.id));
                        processSentMessage(message.id);
                    }
                }
            }
            str3 = null;
            message2 = null;
            delayedMessage = null;
            i2 = (int) j;
            i3 = (int) (j >> 32);
            obj = null;
            if (i2 == 0) {
            }
            if (i2 == 0) {
                encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i3));
                if (encryptedChat == null) {
                    encryptedChat2 = encryptedChat;
                } else if (messageObject2 != null) {
                    MessagesStorage.getInstance().markMessageAsSendError(messageObject2.messageOwner);
                    messageObject2.messageOwner.send_state = 2;
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(messageObject2.getId()));
                    processSentMessage(messageObject2.getId());
                    return;
                } else {
                    return;
                }
            } else if (inputPeer instanceof TLRPC$TL_inputPeerChannel) {
                encryptedChat2 = null;
            } else {
                chat = MessagesController.getInstance().getChat(Integer.valueOf(inputPeer.channel_id));
                if (chat != null) {
                }
                encryptedChat2 = null;
                obj = obj2;
            }
            if (messageObject2 == null) {
                if (str != null) {
                    if (encryptedChat2 == null) {
                    }
                    if (arrayList != null) {
                        if (arrayList.isEmpty()) {
                            message2.entities = arrayList;
                        }
                    }
                    if (tLRPC$WebPage.url == null) {
                        tLRPC$WebPage = null;
                    } else {
                        tLRPC$TL_webPageUrlPending = new TLRPC$TL_webPageUrlPending();
                        tLRPC$TL_webPageUrlPending.url = tLRPC$WebPage.url;
                        tLRPC$WebPage = tLRPC$TL_webPageUrlPending;
                    }
                    if (tLRPC$WebPage != null) {
                        message2.media = new TLRPC$TL_messageMediaWebPage();
                        message2.media.webpage = tLRPC$WebPage;
                    } else {
                        message2.media = new TLRPC$TL_messageMediaEmpty();
                    }
                    if (hashMap != null) {
                        if (hashMap.containsKey("query_id")) {
                            i4 = 9;
                            message2.message = str;
                        }
                    }
                    i4 = 0;
                    message2.message = str;
                } else if (messageMedia != null) {
                    if (encryptedChat2 == null) {
                    }
                    message2.media = messageMedia;
                    message2.message = TtmlNode.ANONYMOUS_REGION_ID;
                    if (hashMap != null) {
                        if (hashMap.containsKey("query_id")) {
                            i4 = 9;
                        }
                    }
                    i4 = 1;
                } else if (tLRPC$TL_photo != null) {
                    if (encryptedChat2 == null) {
                    }
                    message2.media = new TLRPC$TL_messageMediaPhoto();
                    messageMedia2 = message2.media;
                    messageMedia2.flags |= 3;
                    if (tLRPC$TL_photo.caption == null) {
                    }
                    message2.media.caption = tLRPC$TL_photo.caption == null ? tLRPC$TL_photo.caption : TtmlNode.ANONYMOUS_REGION_ID;
                    if (i != 0) {
                        message2.media.ttl_seconds = i;
                        message2.ttl = i;
                        messageMedia2 = message2.media;
                        messageMedia2.flags |= 4;
                    }
                    message2.media.photo = tLRPC$TL_photo;
                    if (hashMap != null) {
                        if (hashMap.containsKey("query_id")) {
                            i6 = 9;
                            message2.message = "-1";
                            if (str2.startsWith("http")) {
                                message2.attachPath = str2;
                                i4 = i6;
                            }
                            message2.attachPath = FileLoader.getPathToAttach(((PhotoSize) tLRPC$TL_photo.sizes.get(tLRPC$TL_photo.sizes.size() - 1)).location, true).toString();
                            i4 = i6;
                        }
                    }
                    i6 = 2;
                    message2.message = "-1";
                    if (str2.startsWith("http")) {
                        message2.attachPath = str2;
                        i4 = i6;
                    }
                    message2.attachPath = FileLoader.getPathToAttach(((PhotoSize) tLRPC$TL_photo.sizes.get(tLRPC$TL_photo.sizes.size() - 1)).location, true).toString();
                    i4 = i6;
                } else if (tLRPC$TL_game != null) {
                    tLRPC$TL_message = new TLRPC$TL_message();
                    tLRPC$TL_message.media = new TLRPC$TL_messageMediaGame();
                    tLRPC$TL_message.media.caption = TtmlNode.ANONYMOUS_REGION_ID;
                    tLRPC$TL_message.media.game = tLRPC$TL_game;
                    tLRPC$TL_message.message = TtmlNode.ANONYMOUS_REGION_ID;
                    if (hashMap != null) {
                        if (hashMap.containsKey("query_id")) {
                            i4 = 9;
                            message2 = tLRPC$TL_message;
                        }
                    }
                    i4 = -1;
                    message2 = tLRPC$TL_message;
                } else if (user != null) {
                    if (encryptedChat2 == null) {
                    }
                    message2.media = new TLRPC$TL_messageMediaContact();
                    message2.media.phone_number = user.phone;
                    message2.media.first_name = user.first_name;
                    message2.media.last_name = user.last_name;
                    message2.media.user_id = user.id;
                    if (message2.media.first_name == null) {
                        messageMedia2 = message2.media;
                        str4 = TtmlNode.ANONYMOUS_REGION_ID;
                        messageMedia2.first_name = str4;
                        user.first_name = str4;
                    }
                    if (message2.media.last_name == null) {
                        messageMedia2 = message2.media;
                        str4 = TtmlNode.ANONYMOUS_REGION_ID;
                        messageMedia2.last_name = str4;
                        user.last_name = str4;
                    }
                    message2.message = TtmlNode.ANONYMOUS_REGION_ID;
                    if (hashMap != null) {
                        if (hashMap.containsKey("query_id")) {
                            i4 = 9;
                        }
                    }
                    i4 = 6;
                } else if (tLRPC$TL_document == null) {
                    i4 = -1;
                } else {
                    if (encryptedChat2 == null) {
                    }
                    message2.media = new TLRPC$TL_messageMediaDocument();
                    messageMedia2 = message2.media;
                    messageMedia2.flags |= 3;
                    if (i != 0) {
                        message2.media.ttl_seconds = i;
                        message2.ttl = i;
                        messageMedia2 = message2.media;
                        messageMedia2.flags |= 4;
                    }
                    if (tLRPC$TL_document.caption == null) {
                    }
                    message2.media.caption = tLRPC$TL_document.caption == null ? tLRPC$TL_document.caption : TtmlNode.ANONYMOUS_REGION_ID;
                    message2.media.document = tLRPC$TL_document;
                    if (hashMap != null) {
                        if (hashMap.containsKey("query_id")) {
                            i6 = 9;
                            if (videoEditedInfo == null) {
                                message2.message = videoEditedInfo.getString();
                            } else {
                                message2.message = "-1";
                            }
                            if (encryptedChat2 != null) {
                            }
                            message2.attachPath = str2;
                            i7 = 0;
                            while (i7 < tLRPC$TL_document.attributes.size()) {
                                documentAttribute = (DocumentAttribute) tLRPC$TL_document.attributes.get(i7);
                                if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                                    i7++;
                                } else {
                                    tLRPC$TL_document.attributes.remove(i7);
                                    tLRPC$TL_documentAttributeSticker_layer55 = new TLRPC$TL_documentAttributeSticker_layer55();
                                    tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeSticker_layer55);
                                    tLRPC$TL_documentAttributeSticker_layer55.alt = documentAttribute.alt;
                                    if (documentAttribute.stickerset != null) {
                                        tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                        i4 = i6;
                                    } else {
                                        if (documentAttribute.stickerset instanceof TLRPC$TL_inputStickerSetShortName) {
                                        }
                                        if (TextUtils.isEmpty(obj2)) {
                                            tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                        } else {
                                            tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetShortName();
                                            tLRPC$TL_documentAttributeSticker_layer55.stickerset.short_name = obj2;
                                        }
                                        i4 = i6;
                                    }
                                }
                            }
                            i4 = i6;
                        }
                    }
                    if (MessageObject.isVideoDocument(tLRPC$TL_document)) {
                    }
                    i6 = 3;
                    if (videoEditedInfo == null) {
                        message2.message = "-1";
                    } else {
                        message2.message = videoEditedInfo.getString();
                    }
                    if (encryptedChat2 != null) {
                    }
                    message2.attachPath = str2;
                    i7 = 0;
                    while (i7 < tLRPC$TL_document.attributes.size()) {
                        documentAttribute = (DocumentAttribute) tLRPC$TL_document.attributes.get(i7);
                        if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                            tLRPC$TL_document.attributes.remove(i7);
                            tLRPC$TL_documentAttributeSticker_layer55 = new TLRPC$TL_documentAttributeSticker_layer55();
                            tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeSticker_layer55);
                            tLRPC$TL_documentAttributeSticker_layer55.alt = documentAttribute.alt;
                            if (documentAttribute.stickerset != null) {
                                if (documentAttribute.stickerset instanceof TLRPC$TL_inputStickerSetShortName) {
                                }
                                if (TextUtils.isEmpty(obj2)) {
                                    tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetShortName();
                                    tLRPC$TL_documentAttributeSticker_layer55.stickerset.short_name = obj2;
                                } else {
                                    tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                }
                                i4 = i6;
                            } else {
                                tLRPC$TL_documentAttributeSticker_layer55.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                i4 = i6;
                            }
                        } else {
                            i7++;
                        }
                    }
                    i4 = i6;
                }
                if (message2.attachPath == null) {
                    message2.attachPath = TtmlNode.ANONYMOUS_REGION_ID;
                }
                i6 = UserConfig.getNewMessageId();
                message2.id = i6;
                message2.local_id = i6;
                message2.out = true;
                if (obj != null) {
                }
                message2.from_id = UserConfig.getClientUserId();
                message2.flags |= 256;
                UserConfig.saveConfig(false);
                i5 = i4;
                message3 = message2;
            } else {
                message2 = messageObject2.messageOwner;
                if (messageObject2.isForwarded()) {
                    if (messageObject2.type == 0) {
                        if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                            str = message2.message;
                        }
                        i4 = 0;
                    } else if (messageObject2.type == 4) {
                        messageMedia = message2.media;
                        i4 = 1;
                    } else if (messageObject2.type != 1) {
                        if (messageObject2.type != 3) {
                        }
                        document = (TLRPC$TL_document) message2.media.document;
                        i4 = 3;
                    } else {
                        tLRPC$TL_photo = (TLRPC$TL_photo) message2.media.photo;
                        i4 = 2;
                    }
                    if (hashMap != null) {
                        if (hashMap.containsKey("query_id")) {
                            i5 = 9;
                            message3 = message2;
                        }
                    }
                    i5 = i4;
                    message3 = message2;
                } else {
                    i5 = 4;
                    message3 = message2;
                }
            }
            try {
                if (message3.random_id == 0) {
                    message3.random_id = getNextRandomId();
                }
                if (hashMap != null) {
                    if (hashMap.containsKey("bot")) {
                        if (encryptedChat2 == null) {
                            message3.via_bot_id = Utilities.parseInt((String) hashMap.get("bot")).intValue();
                        } else {
                            message3.via_bot_name = (String) hashMap.get("bot_name");
                            if (message3.via_bot_name == null) {
                                message3.via_bot_name = TtmlNode.ANONYMOUS_REGION_ID;
                            }
                        }
                        message3.flags |= 2048;
                    }
                }
                message3.params = hashMap;
                message3.date = ConnectionsManager.getInstance().getCurrentTime();
                if (inputPeer instanceof TLRPC$TL_inputPeerChannel) {
                    message3.unread = true;
                } else {
                    if (obj != null) {
                        message3.views = 1;
                        message3.flags |= 1024;
                    }
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(inputPeer.channel_id));
                    if (chat != null) {
                        if (chat.megagroup) {
                            message3.post = true;
                            if (chat.signatures) {
                                message3.from_id = UserConfig.getClientUserId();
                            }
                        } else {
                            message3.flags |= Integer.MIN_VALUE;
                            message3.unread = true;
                        }
                    }
                }
                message3.flags |= 512;
                message3.dialog_id = j;
                if (messageObject != null) {
                    if (encryptedChat2 != null) {
                    }
                    message3.flags |= 8;
                    message3.reply_to_msg_id = messageObject.getId();
                }
                message3.flags |= 64;
                message3.reply_markup = replyMarkup;
                if (i2 != 0) {
                    message3.to_id = new TLRPC$TL_peerUser();
                    if (encryptedChat2.participant_id != UserConfig.getClientUserId()) {
                        message3.to_id.user_id = encryptedChat2.participant_id;
                    } else {
                        message3.to_id.user_id = encryptedChat2.admin_id;
                    }
                    if (i == 0) {
                        message3.ttl = encryptedChat2.ttl;
                    } else {
                        message3.ttl = i;
                    }
                    if (MessageObject.isVoiceMessage(message3)) {
                        for (i6 = 0; i6 < message3.media.document.attributes.size(); i6++) {
                            documentAttribute = (DocumentAttribute) message3.media.document.attributes.get(i6);
                            if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                                i4 = documentAttribute.duration;
                                break;
                            }
                        }
                        i4 = 0;
                        message3.ttl = Math.max(message3.ttl, i4 + 1);
                        arrayList2 = null;
                    } else {
                        for (i6 = 0; i6 < message3.media.document.attributes.size(); i6++) {
                            documentAttribute = (DocumentAttribute) message3.media.document.attributes.get(i6);
                            if (documentAttribute instanceof TLRPC$TL_documentAttributeVideo) {
                                i4 = documentAttribute.duration;
                                break;
                            }
                        }
                        i4 = 0;
                        message3.ttl = Math.max(message3.ttl, i4 + 1);
                        arrayList2 = null;
                    }
                } else if (i3 == 1) {
                    message3.to_id = MessagesController.getPeer(i2);
                    if (i2 > 0) {
                        user2 = MessagesController.getInstance().getUser(Integer.valueOf(i2));
                        if (user2 != null) {
                            if (user2.bot) {
                                message3.unread = false;
                            }
                            arrayList2 = null;
                        } else {
                            processSentMessage(message3.id);
                            return;
                        }
                    }
                    arrayList2 = null;
                } else if (this.currentChatInfo != null) {
                    arrayList3 = new ArrayList();
                    it = this.currentChatInfo.participants.participants.iterator();
                    while (it.hasNext()) {
                        inputUser = MessagesController.getInputUser(MessagesController.getInstance().getUser(Integer.valueOf(((ChatParticipant) it.next()).user_id)));
                        if (inputUser != null) {
                            arrayList3.add(inputUser);
                        }
                    }
                    message3.to_id = new TLRPC$TL_peerChat();
                    message3.to_id.chat_id = i2;
                    arrayList2 = arrayList3;
                } else {
                    MessagesStorage.getInstance().markMessageAsSendError(message3);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(message3.id));
                    processSentMessage(message3.id);
                    return;
                }
                message3.media_unread = true;
                message3.send_state = 1;
                messageObject3 = new MessageObject(message3, null, true);
                messageObject3.replyMessageObject = messageObject;
                messageObject3.attachPathExists = true;
                j2 = 0;
                if (hashMap == null) {
                    obj3 = null;
                    j3 = 0;
                } else {
                    str5 = (String) hashMap.get("groupId");
                    if (str5 != null) {
                        j2 = Utilities.parseLong(str5).longValue();
                        message3.grouped_id = j2;
                        message3.flags |= 131072;
                    }
                    if (hashMap.get("final") == null) {
                    }
                    obj3 = hashMap.get("final") == null ? 1 : null;
                    j3 = j2;
                }
                if (j3 != 0) {
                    arrayList6 = (ArrayList) this.delayedMessages.get("group_" + j3);
                    if (arrayList6 != null) {
                        delayedMessage = (DelayedMessage) arrayList6.get(0);
                    }
                    if (delayedMessage == null) {
                        delayedMessage = new DelayedMessage(j);
                        delayedMessage.type = 4;
                        delayedMessage.groupId = j3;
                        delayedMessage.messageObjects = new ArrayList();
                        delayedMessage.messages = new ArrayList();
                        delayedMessage.originalPaths = new ArrayList();
                        delayedMessage.extraHashMap = new HashMap();
                        delayedMessage.encryptedChat = encryptedChat2;
                    }
                    if (obj3 != null) {
                        delayedMessage.finalGroupMessage = message3.id;
                    }
                } else {
                    arrayList4 = new ArrayList();
                    arrayList4.add(messageObject3);
                    arrayList5 = new ArrayList();
                    arrayList5.add(message3);
                    MessagesStorage.getInstance().putMessages(arrayList5, false, true, false, 0);
                    MessagesController.getInstance().updateInterfaceWithMessages(j, arrayList4);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                }
                FileLog.m13726e("send message user_id = " + inputPeer.user_id + " chat_id = " + inputPeer.chat_id + " channel_id = " + inputPeer.channel_id + " access_hash = " + inputPeer.access_hash);
                if (i5 == 0) {
                }
                if (encryptedChat2 == null) {
                    if (AndroidUtilities.getPeerLayerVersion(encryptedChat2.layer) < 73) {
                    }
                    tLRPC$TL_decryptedMessage.ttl = message3.ttl;
                    tLRPC$TL_decryptedMessage.entities = arrayList;
                    tLRPC$TL_decryptedMessage.flags |= 128;
                    tLRPC$TL_decryptedMessage.reply_to_random_id = messageObject.messageOwner.random_id;
                    tLRPC$TL_decryptedMessage.flags |= 8;
                    if (hashMap != null) {
                        if (hashMap.get("bot_name") != null) {
                            tLRPC$TL_decryptedMessage.via_bot_name = (String) hashMap.get("bot_name");
                            tLRPC$TL_decryptedMessage.flags |= 2048;
                        }
                    }
                    tLRPC$TL_decryptedMessage.random_id = message3.random_id;
                    tLRPC$TL_decryptedMessage.message = str;
                    if (tLRPC$WebPage != null) {
                    }
                    tLRPC$TL_decryptedMessage.media = new TLRPC$TL_decryptedMessageMediaEmpty();
                    SecretChatHelper.getInstance().performSendEncryptedRequest(tLRPC$TL_decryptedMessage, messageObject3.messageOwner, encryptedChat2, null, null, messageObject3);
                    if (messageObject2 == null) {
                        DraftQuery.cleanDraft(j, false);
                    }
                } else if (arrayList2 == null) {
                    tLRPC$TL_messages_sendInlineBotResult = new TLRPC$TL_messages_sendMessage();
                    tLRPC$TL_messages_sendInlineBotResult.message = str;
                    if (messageObject2 != null) {
                    }
                    tLRPC$TL_messages_sendInlineBotResult.clear_draft = messageObject2 != null;
                    if (message3.to_id instanceof TLRPC$TL_peerChannel) {
                        tLRPC$TL_messages_sendInlineBotResult.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + j, false);
                    }
                    tLRPC$TL_messages_sendInlineBotResult.peer = inputPeer;
                    tLRPC$TL_messages_sendInlineBotResult.random_id = message3.random_id;
                    if (messageObject != null) {
                        tLRPC$TL_messages_sendInlineBotResult.flags |= 1;
                        tLRPC$TL_messages_sendInlineBotResult.reply_to_msg_id = messageObject.getId();
                    }
                    if (z) {
                        tLRPC$TL_messages_sendInlineBotResult.no_webpage = true;
                    }
                    tLRPC$TL_messages_sendInlineBotResult.entities = arrayList;
                    tLRPC$TL_messages_sendInlineBotResult.flags |= 8;
                    performSendMessageRequest(tLRPC$TL_messages_sendInlineBotResult, messageObject3, null);
                    if (messageObject2 == null) {
                        DraftQuery.cleanDraft(j, false);
                    }
                } else {
                    tLRPC$TL_messages_sendInlineBotResult = new TLRPC$TL_messages_sendBroadcast();
                    arrayList3 = new ArrayList();
                    for (i4 = 0; i4 < arrayList2.size(); i4++) {
                        arrayList3.add(Long.valueOf(Utilities.random.nextLong()));
                    }
                    tLRPC$TL_messages_sendInlineBotResult.message = str;
                    tLRPC$TL_messages_sendInlineBotResult.contacts = arrayList2;
                    tLRPC$TL_messages_sendInlineBotResult.media = new TLRPC$TL_inputMediaEmpty();
                    tLRPC$TL_messages_sendInlineBotResult.random_id = arrayList3;
                    performSendMessageRequest(tLRPC$TL_messages_sendInlineBotResult, messageObject3, null);
                }
            } catch (Exception e7) {
                e = e7;
                messageObject3 = null;
                message = message3;
                FileLog.m13728e(e);
                MessagesStorage.getInstance().markMessageAsSendError(message);
                if (messageObject3 != null) {
                    messageObject3.messageOwner.send_state = 2;
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(message.id));
                processSentMessage(message.id);
            }
        }
    }

    private void sendReadyToSendGroup(DelayedMessage delayedMessage, boolean z, boolean z2) {
        int i = 0;
        if (delayedMessage.messageObjects.isEmpty()) {
            delayedMessage.markAsError();
            return;
        }
        String str = "group_" + delayedMessage.groupId;
        if (delayedMessage.finalGroupMessage == ((MessageObject) delayedMessage.messageObjects.get(delayedMessage.messageObjects.size() - 1)).getId()) {
            if (z) {
                this.delayedMessages.remove(str);
                MessagesStorage.getInstance().putMessages(delayedMessage.messages, false, true, false, 0);
                MessagesController.getInstance().updateInterfaceWithMessages(delayedMessage.peer, delayedMessage.messageObjects);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            }
            if (delayedMessage.sendRequest instanceof TLRPC$TL_messages_sendMultiMedia) {
                TLRPC$TL_messages_sendMultiMedia tLRPC$TL_messages_sendMultiMedia = (TLRPC$TL_messages_sendMultiMedia) delayedMessage.sendRequest;
                while (i < tLRPC$TL_messages_sendMultiMedia.multi_media.size()) {
                    InputMedia inputMedia = ((TLRPC$TL_inputSingleMedia) tLRPC$TL_messages_sendMultiMedia.multi_media.get(i)).media;
                    if (!(inputMedia instanceof TLRPC$TL_inputMediaUploadedPhoto) && !(inputMedia instanceof TLRPC$TL_inputMediaUploadedDocument)) {
                        i++;
                    } else {
                        return;
                    }
                }
                if (z2) {
                    DelayedMessage findMaxDelayedMessageForMessageId = findMaxDelayedMessageForMessageId(delayedMessage.finalGroupMessage, delayedMessage.peer);
                    if (findMaxDelayedMessageForMessageId != null) {
                        findMaxDelayedMessageForMessageId.addDelayedRequest(delayedMessage.sendRequest, delayedMessage.messageObjects, delayedMessage.originalPaths);
                        if (delayedMessage.requests != null) {
                            findMaxDelayedMessageForMessageId.requests.addAll(delayedMessage.requests);
                            return;
                        }
                        return;
                    }
                }
            }
            TLRPC$TL_messages_sendEncryptedMultiMedia tLRPC$TL_messages_sendEncryptedMultiMedia = (TLRPC$TL_messages_sendEncryptedMultiMedia) delayedMessage.sendEncryptedRequest;
            while (i < tLRPC$TL_messages_sendEncryptedMultiMedia.files.size()) {
                if (!(((InputEncryptedFile) tLRPC$TL_messages_sendEncryptedMultiMedia.files.get(i)) instanceof TLRPC$TL_inputEncryptedFile)) {
                    i++;
                } else {
                    return;
                }
            }
            if (delayedMessage.sendRequest instanceof TLRPC$TL_messages_sendMultiMedia) {
                performSendMessageRequestMulti((TLRPC$TL_messages_sendMultiMedia) delayedMessage.sendRequest, delayedMessage.messageObjects, delayedMessage.originalPaths);
            } else {
                SecretChatHelper.getInstance().performSendEncryptedRequest((TLRPC$TL_messages_sendEncryptedMultiMedia) delayedMessage.sendEncryptedRequest, delayedMessage);
            }
            delayedMessage.sendDelayedRequests();
        } else if (z) {
            putToDelayedMessages(str, delayedMessage);
        }
    }

    private void updateMediaPaths(MessageObject messageObject, Message message, String str, boolean z) {
        Message message2 = messageObject.messageOwner;
        if (message != null) {
            int i;
            PhotoSize photoSize;
            PhotoSize photoSize2;
            if ((message.media instanceof TLRPC$TL_messageMediaPhoto) && message.media.photo != null && (message2.media instanceof TLRPC$TL_messageMediaPhoto) && message2.media.photo != null) {
                if (message.media.ttl_seconds == 0) {
                    MessagesStorage.getInstance().putSentFile(str, message.media.photo, 0);
                }
                if (message2.media.photo.sizes.size() == 1 && (((PhotoSize) message2.media.photo.sizes.get(0)).location instanceof TLRPC$TL_fileLocationUnavailable)) {
                    message2.media.photo.sizes = message.media.photo.sizes;
                } else {
                    for (i = 0; i < message.media.photo.sizes.size(); i++) {
                        photoSize = (PhotoSize) message.media.photo.sizes.get(i);
                        if (!(photoSize == null || photoSize.location == null || (photoSize instanceof TLRPC$TL_photoSizeEmpty) || photoSize.type == null)) {
                            int i2 = 0;
                            while (i2 < message2.media.photo.sizes.size()) {
                                photoSize2 = (PhotoSize) message2.media.photo.sizes.get(i2);
                                if (photoSize2 == null || photoSize2.location == null || photoSize2.type == null || !((photoSize2.location.volume_id == -2147483648L && photoSize.type.equals(photoSize2.type)) || (photoSize.f10147w == photoSize2.f10147w && photoSize.f10146h == photoSize2.f10146h))) {
                                    i2++;
                                } else {
                                    String str2 = photoSize2.location.volume_id + "_" + photoSize2.location.local_id;
                                    String str3 = photoSize.location.volume_id + "_" + photoSize.location.local_id;
                                    if (!str2.equals(str3)) {
                                        File file = new File(FileLoader.getInstance().getDirectory(4), str2 + ".jpg");
                                        File file2 = (message.media.ttl_seconds != 0 || (message.media.photo.sizes.size() != 1 && photoSize.f10147w <= 90 && photoSize.f10146h <= 90)) ? new File(FileLoader.getInstance().getDirectory(4), str3 + ".jpg") : FileLoader.getPathToAttach(photoSize);
                                        file.renameTo(file2);
                                        ImageLoader.getInstance().replaceImageInCache(str2, str3, photoSize.location, z);
                                        photoSize2.location = photoSize.location;
                                        photoSize2.size = photoSize.size;
                                    }
                                }
                            }
                        }
                    }
                }
                message.message = message2.message;
                message.attachPath = message2.attachPath;
                message2.media.photo.id = message.media.photo.id;
                message2.media.photo.access_hash = message.media.photo.access_hash;
            } else if ((message.media instanceof TLRPC$TL_messageMediaDocument) && message.media.document != null && (message2.media instanceof TLRPC$TL_messageMediaDocument) && message2.media.document != null) {
                DocumentAttribute documentAttribute;
                byte[] bArr;
                if (MessageObject.isVideoMessage(message)) {
                    if (message.media.ttl_seconds == 0) {
                        MessagesStorage.getInstance().putSentFile(str, message.media.document, 2);
                    }
                    message.attachPath = message2.attachPath;
                } else if (!(MessageObject.isVoiceMessage(message) || MessageObject.isRoundVideoMessage(message) || message.media.ttl_seconds != 0)) {
                    MessagesStorage.getInstance().putSentFile(str, message.media.document, 1);
                }
                photoSize = message2.media.document.thumb;
                photoSize2 = message.media.document.thumb;
                if (photoSize != null && photoSize.location != null && photoSize.location.volume_id == -2147483648L && photoSize2 != null && photoSize2.location != null && !(photoSize2 instanceof TLRPC$TL_photoSizeEmpty) && !(photoSize instanceof TLRPC$TL_photoSizeEmpty)) {
                    String str4 = photoSize.location.volume_id + "_" + photoSize.location.local_id;
                    String str5 = photoSize2.location.volume_id + "_" + photoSize2.location.local_id;
                    if (!str4.equals(str5)) {
                        new File(FileLoader.getInstance().getDirectory(4), str4 + ".jpg").renameTo(new File(FileLoader.getInstance().getDirectory(4), str5 + ".jpg"));
                        ImageLoader.getInstance().replaceImageInCache(str4, str5, photoSize2.location, z);
                        photoSize.location = photoSize2.location;
                        photoSize.size = photoSize2.size;
                    }
                } else if (photoSize != null && MessageObject.isStickerMessage(message) && photoSize.location != null) {
                    photoSize2.location = photoSize.location;
                } else if ((photoSize != null && (photoSize.location instanceof TLRPC$TL_fileLocationUnavailable)) || (photoSize instanceof TLRPC$TL_photoSizeEmpty)) {
                    message2.media.document.thumb = message.media.document.thumb;
                }
                message2.media.document.dc_id = message.media.document.dc_id;
                message2.media.document.id = message.media.document.id;
                message2.media.document.access_hash = message.media.document.access_hash;
                for (int i3 = 0; i3 < message2.media.document.attributes.size(); i3++) {
                    documentAttribute = (DocumentAttribute) message2.media.document.attributes.get(i3);
                    if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                        bArr = documentAttribute.waveform;
                        break;
                    }
                }
                bArr = null;
                message2.media.document.attributes = message.media.document.attributes;
                if (bArr != null) {
                    for (i = 0; i < message2.media.document.attributes.size(); i++) {
                        documentAttribute = (DocumentAttribute) message2.media.document.attributes.get(i);
                        if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                            documentAttribute.waveform = bArr;
                            documentAttribute.flags |= 4;
                        }
                    }
                }
                message2.media.document.size = message.media.document.size;
                message2.media.document.mime_type = message.media.document.mime_type;
                if ((message.flags & 4) == 0 && MessageObject.isOut(message)) {
                    if (MessageObject.isNewGifDocument(message.media.document)) {
                        StickersQuery.addRecentGif(message.media.document, message.date);
                    } else if (MessageObject.isStickerDocument(message.media.document)) {
                        StickersQuery.addRecentSticker(0, message.media.document, message.date, false);
                    }
                }
                if (message2.attachPath == null || !message2.attachPath.startsWith(FileLoader.getInstance().getDirectory(4).getAbsolutePath())) {
                    message.attachPath = message2.attachPath;
                    message.message = message2.message;
                    return;
                }
                File file3 = new File(message2.attachPath);
                File pathToAttach = FileLoader.getPathToAttach(message.media.document, message.media.ttl_seconds != 0);
                if (!file3.renameTo(pathToAttach)) {
                    message.attachPath = message2.attachPath;
                    message.message = message2.message;
                } else if (MessageObject.isVideoMessage(message)) {
                    messageObject.attachPathExists = true;
                } else {
                    messageObject.mediaExists = messageObject.attachPathExists;
                    messageObject.attachPathExists = false;
                    message2.attachPath = TtmlNode.ANONYMOUS_REGION_ID;
                    if (str != null && str.startsWith("http")) {
                        MessagesStorage.getInstance().addRecentLocalFile(str, pathToAttach.toString(), message2.media.document);
                    }
                }
            } else if ((message.media instanceof TLRPC$TL_messageMediaContact) && (message2.media instanceof TLRPC$TL_messageMediaContact)) {
                message2.media = message.media;
            } else if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
                message2.media = message.media;
            } else if (message.media instanceof TLRPC$TL_messageMediaGame) {
                message2.media = message.media;
                if ((message2.media instanceof TLRPC$TL_messageMediaGame) && !TextUtils.isEmpty(message.message)) {
                    message2.entities = message.entities;
                    message2.message = message.message;
                }
            }
        }
    }

    private void uploadMultiMedia(final DelayedMessage delayedMessage, final InputMedia inputMedia, InputEncryptedFile inputEncryptedFile, String str) {
        if (inputMedia != null) {
            TLRPC$TL_messages_sendMultiMedia tLRPC$TL_messages_sendMultiMedia = (TLRPC$TL_messages_sendMultiMedia) delayedMessage.sendRequest;
            for (int i = 0; i < tLRPC$TL_messages_sendMultiMedia.multi_media.size(); i++) {
                if (((TLRPC$TL_inputSingleMedia) tLRPC$TL_messages_sendMultiMedia.multi_media.get(i)).media == inputMedia) {
                    putToSendingMessages((Message) delayedMessage.messages.get(i));
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileUploadProgressChanged, str, Float.valueOf(1.0f), Boolean.valueOf(false));
                    break;
                }
            }
            TLObject tLRPC$TL_messages_uploadMedia = new TLRPC$TL_messages_uploadMedia();
            tLRPC$TL_messages_uploadMedia.media = inputMedia;
            tLRPC$TL_messages_uploadMedia.peer = ((TLRPC$TL_messages_sendMultiMedia) delayedMessage.sendRequest).peer;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_uploadMedia, new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            InputMedia inputMedia;
                            TLRPC$TL_messages_sendMultiMedia tLRPC$TL_messages_sendMultiMedia;
                            int i;
                            if (tLObject != null) {
                                MessageMedia messageMedia = (MessageMedia) tLObject;
                                InputMedia tLRPC$TL_inputMediaPhoto;
                                if ((inputMedia instanceof TLRPC$TL_inputMediaUploadedPhoto) && (messageMedia instanceof TLRPC$TL_messageMediaPhoto)) {
                                    tLRPC$TL_inputMediaPhoto = new TLRPC$TL_inputMediaPhoto();
                                    tLRPC$TL_inputMediaPhoto.id = new TLRPC$TL_inputPhoto();
                                    tLRPC$TL_inputMediaPhoto.id.id = messageMedia.photo.id;
                                    tLRPC$TL_inputMediaPhoto.id.access_hash = messageMedia.photo.access_hash;
                                    inputMedia = tLRPC$TL_inputMediaPhoto;
                                    if (inputMedia != null) {
                                        delayedMessage.markAsError();
                                    }
                                    inputMedia.caption = inputMedia.caption;
                                    if (inputMedia.ttl_seconds != 0) {
                                        inputMedia.ttl_seconds = inputMedia.ttl_seconds;
                                        inputMedia.flags |= 1;
                                    }
                                    tLRPC$TL_messages_sendMultiMedia = (TLRPC$TL_messages_sendMultiMedia) delayedMessage.sendRequest;
                                    for (i = 0; i < tLRPC$TL_messages_sendMultiMedia.multi_media.size(); i++) {
                                        if (((TLRPC$TL_inputSingleMedia) tLRPC$TL_messages_sendMultiMedia.multi_media.get(i)).media != inputMedia) {
                                            ((TLRPC$TL_inputSingleMedia) tLRPC$TL_messages_sendMultiMedia.multi_media.get(i)).media = inputMedia;
                                            break;
                                        }
                                    }
                                    SendMessagesHelper.this.sendReadyToSendGroup(delayedMessage, false, true);
                                    return;
                                } else if ((inputMedia instanceof TLRPC$TL_inputMediaUploadedDocument) && (messageMedia instanceof TLRPC$TL_messageMediaDocument)) {
                                    tLRPC$TL_inputMediaPhoto = new TLRPC$TL_inputMediaDocument();
                                    tLRPC$TL_inputMediaPhoto.id = new TLRPC$TL_inputDocument();
                                    tLRPC$TL_inputMediaPhoto.id.id = messageMedia.document.id;
                                    tLRPC$TL_inputMediaPhoto.id.access_hash = messageMedia.document.access_hash;
                                    inputMedia = tLRPC$TL_inputMediaPhoto;
                                    if (inputMedia != null) {
                                        inputMedia.caption = inputMedia.caption;
                                        if (inputMedia.ttl_seconds != 0) {
                                            inputMedia.ttl_seconds = inputMedia.ttl_seconds;
                                            inputMedia.flags |= 1;
                                        }
                                        tLRPC$TL_messages_sendMultiMedia = (TLRPC$TL_messages_sendMultiMedia) delayedMessage.sendRequest;
                                        for (i = 0; i < tLRPC$TL_messages_sendMultiMedia.multi_media.size(); i++) {
                                            if (((TLRPC$TL_inputSingleMedia) tLRPC$TL_messages_sendMultiMedia.multi_media.get(i)).media != inputMedia) {
                                                ((TLRPC$TL_inputSingleMedia) tLRPC$TL_messages_sendMultiMedia.multi_media.get(i)).media = inputMedia;
                                                break;
                                            }
                                        }
                                        SendMessagesHelper.this.sendReadyToSendGroup(delayedMessage, false, true);
                                        return;
                                    }
                                    delayedMessage.markAsError();
                                }
                            }
                            inputMedia = null;
                            if (inputMedia != null) {
                                inputMedia.caption = inputMedia.caption;
                                if (inputMedia.ttl_seconds != 0) {
                                    inputMedia.ttl_seconds = inputMedia.ttl_seconds;
                                    inputMedia.flags |= 1;
                                }
                                tLRPC$TL_messages_sendMultiMedia = (TLRPC$TL_messages_sendMultiMedia) delayedMessage.sendRequest;
                                for (i = 0; i < tLRPC$TL_messages_sendMultiMedia.multi_media.size(); i++) {
                                    if (((TLRPC$TL_inputSingleMedia) tLRPC$TL_messages_sendMultiMedia.multi_media.get(i)).media != inputMedia) {
                                        ((TLRPC$TL_inputSingleMedia) tLRPC$TL_messages_sendMultiMedia.multi_media.get(i)).media = inputMedia;
                                        break;
                                    }
                                }
                                SendMessagesHelper.this.sendReadyToSendGroup(delayedMessage, false, true);
                                return;
                            }
                            delayedMessage.markAsError();
                        }
                    });
                }
            });
        } else if (inputEncryptedFile != null) {
            TLRPC$TL_messages_sendEncryptedMultiMedia tLRPC$TL_messages_sendEncryptedMultiMedia = (TLRPC$TL_messages_sendEncryptedMultiMedia) delayedMessage.sendEncryptedRequest;
            for (int i2 = 0; i2 < tLRPC$TL_messages_sendEncryptedMultiMedia.files.size(); i2++) {
                if (tLRPC$TL_messages_sendEncryptedMultiMedia.files.get(i2) == inputEncryptedFile) {
                    putToSendingMessages((Message) delayedMessage.messages.get(i2));
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileUploadProgressChanged, str, Float.valueOf(1.0f), Boolean.valueOf(false));
                    break;
                }
            }
            sendReadyToSendGroup(delayedMessage, false, true);
        }
    }

    public void cancelSendingMessage(MessageObject messageObject) {
        ArrayList arrayList;
        int i;
        ArrayList arrayList2 = new ArrayList();
        boolean z = false;
        for (Entry entry : this.delayedMessages.entrySet()) {
            boolean z2;
            arrayList = (ArrayList) entry.getValue();
            int i2 = 0;
            while (i2 < arrayList.size()) {
                String str;
                DelayedMessage delayedMessage = (DelayedMessage) arrayList.get(i2);
                if (delayedMessage.type == 4) {
                    Object obj;
                    int i3 = -1;
                    MessageObject messageObject2 = null;
                    for (i = 0; i < delayedMessage.messageObjects.size(); i++) {
                        messageObject2 = (MessageObject) delayedMessage.messageObjects.get(i);
                        if (messageObject2.getId() == messageObject.getId()) {
                            i3 = i;
                            obj = messageObject2;
                            break;
                        }
                    }
                    MessageObject messageObject3 = messageObject2;
                    if (i3 >= 0) {
                        delayedMessage.messageObjects.remove(i3);
                        delayedMessage.messages.remove(i3);
                        delayedMessage.originalPaths.remove(i3);
                        if (delayedMessage.sendRequest != null) {
                            ((TLRPC$TL_messages_sendMultiMedia) delayedMessage.sendRequest).multi_media.remove(i3);
                        } else {
                            TLRPC$TL_messages_sendEncryptedMultiMedia tLRPC$TL_messages_sendEncryptedMultiMedia = (TLRPC$TL_messages_sendEncryptedMultiMedia) delayedMessage.sendEncryptedRequest;
                            tLRPC$TL_messages_sendEncryptedMultiMedia.messages.remove(i3);
                            tLRPC$TL_messages_sendEncryptedMultiMedia.files.remove(i3);
                        }
                        MediaController.getInstance().cancelVideoConvert(messageObject);
                        str = (String) delayedMessage.extraHashMap.get(obj);
                        if (str != null) {
                            arrayList2.add(str);
                        }
                        if (delayedMessage.messageObjects.isEmpty()) {
                            delayedMessage.sendDelayedRequests();
                        } else {
                            if (delayedMessage.finalGroupMessage == messageObject.getId()) {
                                messageObject2 = (MessageObject) delayedMessage.messageObjects.get(delayedMessage.messageObjects.size() - 1);
                                delayedMessage.finalGroupMessage = messageObject2.getId();
                                messageObject2.messageOwner.params.put("final", "1");
                                TLRPC$messages_Messages tLRPC$TL_messages_messages = new TLRPC$TL_messages_messages();
                                tLRPC$TL_messages_messages.messages.add(messageObject2.messageOwner);
                                MessagesStorage.getInstance().putMessages(tLRPC$TL_messages_messages, delayedMessage.peer, -2, 0, false);
                            }
                            sendReadyToSendGroup(delayedMessage, false, true);
                        }
                        z2 = z;
                        z = z2;
                    }
                    z2 = z;
                    z = z2;
                } else if (delayedMessage.obj.getId() == messageObject.getId()) {
                    arrayList.remove(i2);
                    delayedMessage.sendDelayedRequests();
                    MediaController.getInstance().cancelVideoConvert(delayedMessage.obj);
                    if (arrayList.size() == 0) {
                        arrayList2.add(entry.getKey());
                        if (delayedMessage.sendEncryptedRequest != null) {
                            z2 = true;
                            z = z2;
                        }
                    }
                    z2 = z;
                    z = z2;
                } else {
                    i2++;
                }
            }
            z2 = z;
            z = z2;
        }
        for (i = 0; i < arrayList2.size(); i++) {
            str = (String) arrayList2.get(i);
            if (str.startsWith("http")) {
                ImageLoader.getInstance().cancelLoadHttpFile(str);
            } else {
                FileLoader.getInstance().cancelUploadFile(str, z);
            }
            stopVideoService(str);
            this.delayedMessages.remove(str);
        }
        arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(messageObject.getId()));
        MessagesController.getInstance().deleteMessages(arrayList, null, null, messageObject.messageOwner.to_id.channel_id, false);
    }

    public void checkUnsentMessages() {
        MessagesStorage.getInstance().getUnsentMessages(1000);
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

    public void didReceivedNotification(int i, Object... objArr) {
        DelayedMessage delayedMessage;
        if (i == NotificationCenter.FileDidUpload) {
            String str = (String) objArr[0];
            InputFile inputFile = (InputFile) objArr[1];
            InputEncryptedFile inputEncryptedFile = (InputEncryptedFile) objArr[2];
            ArrayList arrayList = (ArrayList) this.delayedMessages.get(str);
            if (arrayList != null) {
                int i2 = 0;
                while (i2 < arrayList.size()) {
                    delayedMessage = (DelayedMessage) arrayList.get(i2);
                    InputMedia inputMedia = delayedMessage.sendRequest instanceof TLRPC$TL_messages_sendMedia ? ((TLRPC$TL_messages_sendMedia) delayedMessage.sendRequest).media : delayedMessage.sendRequest instanceof TLRPC$TL_messages_sendBroadcast ? ((TLRPC$TL_messages_sendBroadcast) delayedMessage.sendRequest).media : delayedMessage.sendRequest instanceof TLRPC$TL_messages_sendMultiMedia ? (InputMedia) delayedMessage.extraHashMap.get(str) : null;
                    int indexOf;
                    if (inputFile != null && inputMedia != null) {
                        if (delayedMessage.type == 0) {
                            inputMedia.file = inputFile;
                            performSendMessageRequest(delayedMessage.sendRequest, delayedMessage.obj, delayedMessage.originalPath, delayedMessage, true);
                        } else if (delayedMessage.type == 1) {
                            if (inputMedia.file == null) {
                                inputMedia.file = inputFile;
                                if (inputMedia.thumb != null || delayedMessage.location == null) {
                                    performSendMessageRequest(delayedMessage.sendRequest, delayedMessage.obj, delayedMessage.originalPath);
                                } else {
                                    performSendDelayedMessage(delayedMessage);
                                }
                            } else {
                                inputMedia.thumb = inputFile;
                                inputMedia.flags |= 4;
                                performSendMessageRequest(delayedMessage.sendRequest, delayedMessage.obj, delayedMessage.originalPath);
                            }
                        } else if (delayedMessage.type == 2) {
                            if (inputMedia.file == null) {
                                inputMedia.file = inputFile;
                                if (inputMedia.thumb != null || delayedMessage.location == null) {
                                    performSendMessageRequest(delayedMessage.sendRequest, delayedMessage.obj, delayedMessage.originalPath);
                                } else {
                                    performSendDelayedMessage(delayedMessage);
                                }
                            } else {
                                inputMedia.thumb = inputFile;
                                inputMedia.flags |= 4;
                                performSendMessageRequest(delayedMessage.sendRequest, delayedMessage.obj, delayedMessage.originalPath);
                            }
                        } else if (delayedMessage.type == 3) {
                            inputMedia.file = inputFile;
                            performSendMessageRequest(delayedMessage.sendRequest, delayedMessage.obj, delayedMessage.originalPath);
                        } else if (delayedMessage.type == 4) {
                            if (!(inputMedia instanceof TLRPC$TL_inputMediaUploadedDocument)) {
                                inputMedia.file = inputFile;
                                uploadMultiMedia(delayedMessage, inputMedia, null, str);
                            } else if (inputMedia.file == null) {
                                inputMedia.file = inputFile;
                                indexOf = delayedMessage.messageObjects.indexOf((MessageObject) delayedMessage.extraHashMap.get(str + "_i"));
                                delayedMessage.location = (FileLocation) delayedMessage.extraHashMap.get(str + "_t");
                                stopVideoService(((MessageObject) delayedMessage.messageObjects.get(indexOf)).messageOwner.attachPath);
                                if (inputMedia.thumb != null || delayedMessage.location == null) {
                                    uploadMultiMedia(delayedMessage, inputMedia, null, str);
                                } else {
                                    performSendDelayedMessage(delayedMessage, indexOf);
                                }
                            } else {
                                inputMedia.thumb = inputFile;
                                inputMedia.flags |= 4;
                                uploadMultiMedia(delayedMessage, inputMedia, null, (String) delayedMessage.extraHashMap.get(str + "_o"));
                            }
                        }
                        arrayList.remove(i2);
                        i2--;
                    } else if (!(inputEncryptedFile == null || delayedMessage.sendEncryptedRequest == null)) {
                        DecryptedMessage decryptedMessage;
                        if (delayedMessage.type == 4) {
                            TLRPC$TL_messages_sendEncryptedMultiMedia tLRPC$TL_messages_sendEncryptedMultiMedia = (TLRPC$TL_messages_sendEncryptedMultiMedia) delayedMessage.sendEncryptedRequest;
                            InputEncryptedFile inputEncryptedFile2 = (InputEncryptedFile) delayedMessage.extraHashMap.get(str);
                            indexOf = tLRPC$TL_messages_sendEncryptedMultiMedia.files.indexOf(inputEncryptedFile2);
                            tLRPC$TL_messages_sendEncryptedMultiMedia.files.set(indexOf, inputEncryptedFile);
                            if (inputEncryptedFile2.id == 1) {
                                MessageObject messageObject = (MessageObject) delayedMessage.extraHashMap.get(str + "_i");
                                delayedMessage.location = (FileLocation) delayedMessage.extraHashMap.get(str + "_t");
                                stopVideoService(((MessageObject) delayedMessage.messageObjects.get(indexOf)).messageOwner.attachPath);
                            }
                            decryptedMessage = (TLRPC$TL_decryptedMessage) tLRPC$TL_messages_sendEncryptedMultiMedia.messages.get(indexOf);
                        } else {
                            decryptedMessage = (TLRPC$TL_decryptedMessage) delayedMessage.sendEncryptedRequest;
                        }
                        if ((decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaVideo) || (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaPhoto) || (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaDocument)) {
                            decryptedMessage.media.size = (int) ((Long) objArr[5]).longValue();
                        }
                        decryptedMessage.media.key = (byte[]) objArr[3];
                        decryptedMessage.media.iv = (byte[]) objArr[4];
                        if (delayedMessage.type == 4) {
                            uploadMultiMedia(delayedMessage, null, inputEncryptedFile, str);
                        } else {
                            SecretChatHelper.getInstance().performSendEncryptedRequest(decryptedMessage, delayedMessage.obj.messageOwner, delayedMessage.encryptedChat, inputEncryptedFile, delayedMessage.originalPath, delayedMessage.obj);
                        }
                        arrayList.remove(i2);
                        i2--;
                    }
                    i2++;
                }
                if (arrayList.isEmpty()) {
                    this.delayedMessages.remove(str);
                }
            }
        } else if (i == NotificationCenter.FileDidFailUpload) {
            r2 = (String) objArr[0];
            boolean booleanValue = ((Boolean) objArr[1]).booleanValue();
            r3 = (ArrayList) this.delayedMessages.get(r2);
            if (r3 != null) {
                r5 = 0;
                while (r5 < r3.size()) {
                    r4 = (DelayedMessage) r3.get(r5);
                    if ((booleanValue && r4.sendEncryptedRequest != null) || !(booleanValue || r4.sendRequest == null)) {
                        r4.markAsError();
                        r3.remove(r5);
                        r5--;
                    }
                    r5++;
                }
                if (r3.isEmpty()) {
                    this.delayedMessages.remove(r2);
                }
            }
        } else if (i == NotificationCenter.FilePreparingStarted) {
            r2 = (MessageObject) objArr[0];
            if (r2.getId() != 0) {
                r3 = (String) objArr[1];
                r3 = (ArrayList) this.delayedMessages.get(r2.messageOwner.attachPath);
                if (r3 != null) {
                    r6 = 0;
                    while (r6 < r3.size()) {
                        r4 = (DelayedMessage) r3.get(r6);
                        if (r4.type == 4) {
                            r7 = r4.messageObjects.indexOf(r2);
                            r4.location = (FileLocation) r4.extraHashMap.get(r2.messageOwner.attachPath + "_t");
                            performSendDelayedMessage(r4, r7);
                            r3.remove(r6);
                            break;
                        } else if (r4.obj == r2) {
                            r4.videoEditedInfo = null;
                            performSendDelayedMessage(r4);
                            r3.remove(r6);
                            break;
                        } else {
                            r6++;
                        }
                    }
                    if (r3.isEmpty()) {
                        this.delayedMessages.remove(r2.messageOwner.attachPath);
                    }
                }
            }
        } else if (i == NotificationCenter.FileNewChunkAvailable) {
            MessageObject messageObject2 = (MessageObject) objArr[0];
            if (messageObject2.getId() != 0) {
                r2 = (String) objArr[1];
                long longValue = ((Long) objArr[2]).longValue();
                FileLoader.getInstance().checkUploadNewDataAvailable(r2, ((int) messageObject2.getDialogId()) == 0, longValue);
                if (longValue != 0) {
                    ArrayList arrayList2 = (ArrayList) this.delayedMessages.get(messageObject2.messageOwner.attachPath);
                    if (arrayList2 != null) {
                        int i3;
                        for (int i4 = 0; i4 < arrayList2.size(); i4 = i3 + 1) {
                            DelayedMessage delayedMessage2 = (DelayedMessage) arrayList2.get(i4);
                            if (delayedMessage2.type == 4) {
                                i3 = i4;
                                while (0 < delayedMessage2.messageObjects.size()) {
                                    MessageObject messageObject3 = (MessageObject) delayedMessage2.messageObjects.get(i3);
                                    if (messageObject3 == messageObject2) {
                                        messageObject3.videoEditedInfo = null;
                                        messageObject3.messageOwner.message = "-1";
                                        messageObject3.messageOwner.media.document.size = (int) longValue;
                                        r3 = new ArrayList();
                                        r3.add(messageObject3.messageOwner);
                                        MessagesStorage.getInstance().putMessages(r3, false, true, false, 0);
                                        break;
                                    }
                                    i3++;
                                }
                            } else if (delayedMessage2.obj == messageObject2) {
                                delayedMessage2.obj.videoEditedInfo = null;
                                delayedMessage2.obj.messageOwner.message = "-1";
                                delayedMessage2.obj.messageOwner.media.document.size = (int) longValue;
                                r3 = new ArrayList();
                                r3.add(delayedMessage2.obj.messageOwner);
                                MessagesStorage.getInstance().putMessages(r3, false, true, false, 0);
                                return;
                            } else {
                                i3 = i4;
                            }
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.FilePreparingFailed) {
            r2 = (MessageObject) objArr[0];
            if (r2.getId() != 0) {
                r3 = (String) objArr[1];
                stopVideoService(r2.messageOwner.attachPath);
                ArrayList arrayList3 = (ArrayList) this.delayedMessages.get(r3);
                if (arrayList3 != null) {
                    r6 = 0;
                    while (r6 < arrayList3.size()) {
                        DelayedMessage delayedMessage3 = (DelayedMessage) arrayList3.get(r6);
                        if (delayedMessage3.type == 4) {
                            for (r7 = 0; r7 < delayedMessage3.messages.size(); r7++) {
                                if (delayedMessage3.messageObjects.get(r7) == r2) {
                                    delayedMessage3.markAsError();
                                    arrayList3.remove(r6);
                                    r6--;
                                    break;
                                }
                            }
                        } else if (delayedMessage3.obj == r2) {
                            delayedMessage3.markAsError();
                            arrayList3.remove(r6);
                            r6--;
                        }
                        r6++;
                    }
                    if (arrayList3.isEmpty()) {
                        this.delayedMessages.remove(r3);
                    }
                }
            }
        } else if (i == NotificationCenter.httpFileDidLoaded) {
            final String str2 = (String) objArr[0];
            ArrayList arrayList4 = (ArrayList) this.delayedMessages.get(str2);
            if (arrayList4 != null) {
                for (int i5 = 0; i5 < arrayList4.size(); i5++) {
                    MessageObject messageObject4;
                    delayedMessage = (DelayedMessage) arrayList4.get(i5);
                    int i6 = -1;
                    if (delayedMessage.type == 0) {
                        i6 = 0;
                        messageObject4 = delayedMessage.obj;
                    } else if (delayedMessage.type == 2) {
                        i6 = 1;
                        messageObject4 = delayedMessage.obj;
                    } else if (delayedMessage.type == 4) {
                        r2 = (MessageObject) delayedMessage.extraHashMap.get(str2);
                        if (r2.getDocument() != null) {
                            messageObject4 = r2;
                            i6 = 1;
                        } else {
                            messageObject4 = r2;
                            i6 = 0;
                        }
                    } else {
                        messageObject4 = null;
                    }
                    if (i6 == 0) {
                        final File file = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(str2) + "." + ImageLoader.getHttpUrlExtension(str2, "file"));
                        Utilities.globalQueue.postRunnable(new Runnable() {
                            public void run() {
                                final TLRPC$TL_photo generatePhotoSizes = SendMessagesHelper.getInstance().generatePhotoSizes(file.toString(), null);
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        if (generatePhotoSizes != null) {
                                            messageObject4.messageOwner.media.photo = generatePhotoSizes;
                                            messageObject4.messageOwner.attachPath = file.toString();
                                            ArrayList arrayList = new ArrayList();
                                            arrayList.add(messageObject4.messageOwner);
                                            MessagesStorage.getInstance().putMessages(arrayList, false, true, false, 0);
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateMessageMedia, messageObject4.messageOwner);
                                            delayedMessage.location = ((PhotoSize) generatePhotoSizes.sizes.get(generatePhotoSizes.sizes.size() - 1)).location;
                                            delayedMessage.httpLocation = null;
                                            if (delayedMessage.type == 4) {
                                                SendMessagesHelper.this.performSendDelayedMessage(delayedMessage, delayedMessage.messageObjects.indexOf(messageObject4));
                                                return;
                                            } else {
                                                SendMessagesHelper.this.performSendDelayedMessage(delayedMessage);
                                                return;
                                            }
                                        }
                                        FileLog.m13726e("can't load image " + str2 + " to file " + file.toString());
                                        delayedMessage.markAsError();
                                    }
                                });
                            }
                        });
                    } else if (i6 == 1) {
                        final File file2 = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(str2) + ".gif");
                        Utilities.globalQueue.postRunnable(new Runnable() {
                            public void run() {
                                boolean z = true;
                                final Document document = delayedMessage.obj.getDocument();
                                if (document.thumb.location instanceof TLRPC$TL_fileLocationUnavailable) {
                                    try {
                                        Bitmap loadBitmap = ImageLoader.loadBitmap(file2.getAbsolutePath(), null, 90.0f, 90.0f, true);
                                        if (loadBitmap != null) {
                                            if (delayedMessage.sendEncryptedRequest == null) {
                                                z = false;
                                            }
                                            document.thumb = ImageLoader.scaleAndSaveImage(loadBitmap, 90.0f, 90.0f, 55, z);
                                            loadBitmap.recycle();
                                        }
                                    } catch (Throwable e) {
                                        document.thumb = null;
                                        FileLog.m13728e(e);
                                    }
                                    if (document.thumb == null) {
                                        document.thumb = new TLRPC$TL_photoSizeEmpty();
                                        document.thumb.type = "s";
                                    }
                                }
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        delayedMessage.httpLocation = null;
                                        delayedMessage.obj.messageOwner.attachPath = file2.toString();
                                        delayedMessage.location = document.thumb.location;
                                        ArrayList arrayList = new ArrayList();
                                        arrayList.add(messageObject4.messageOwner);
                                        MessagesStorage.getInstance().putMessages(arrayList, false, true, false, 0);
                                        SendMessagesHelper.this.performSendDelayedMessage(delayedMessage);
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateMessageMedia, delayedMessage.obj.messageOwner);
                                    }
                                });
                            }
                        });
                    }
                }
                this.delayedMessages.remove(str2);
            }
        } else if (i == NotificationCenter.FileDidLoaded) {
            r2 = (String) objArr[0];
            r3 = (ArrayList) this.delayedMessages.get(r2);
            if (r3 != null) {
                for (r5 = 0; r5 < r3.size(); r5++) {
                    performSendDelayedMessage((DelayedMessage) r3.get(r5));
                }
                this.delayedMessages.remove(r2);
            }
        } else if (i == NotificationCenter.httpFileDidFailedLoad || i == NotificationCenter.FileDidFailedLoad) {
            r2 = (String) objArr[0];
            r3 = (ArrayList) this.delayedMessages.get(r2);
            if (r3 != null) {
                for (r5 = 0; r5 < r3.size(); r5++) {
                    ((DelayedMessage) r3.get(r5)).markAsError();
                }
                this.delayedMessages.remove(r2);
            }
        }
    }

    public int editMessage(MessageObject messageObject, String str, boolean z, final BaseFragment baseFragment, ArrayList<MessageEntity> arrayList, final Runnable runnable) {
        boolean z2 = false;
        if (baseFragment == null || baseFragment.getParentActivity() == null || runnable == null) {
            return 0;
        }
        final TLObject tLRPC$TL_messages_editMessage = new TLRPC$TL_messages_editMessage();
        tLRPC$TL_messages_editMessage.peer = MessagesController.getInputPeer((int) messageObject.getDialogId());
        tLRPC$TL_messages_editMessage.message = str;
        tLRPC$TL_messages_editMessage.flags |= 2048;
        tLRPC$TL_messages_editMessage.id = messageObject.getId();
        if (!z) {
            z2 = true;
        }
        tLRPC$TL_messages_editMessage.no_webpage = z2;
        if (arrayList != null) {
            tLRPC$TL_messages_editMessage.entities = arrayList;
            tLRPC$TL_messages_editMessage.flags |= 8;
        }
        return ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_editMessage, new RequestDelegate() {

            /* renamed from: org.telegram.messenger.SendMessagesHelper$7$1 */
            class C34021 implements Runnable {
                C34021() {
                }

                public void run() {
                    runnable.run();
                }
            }

            public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new C34021());
                if (tLRPC$TL_error == null) {
                    MessagesController.getInstance().processUpdates((TLRPC$Updates) tLObject, false);
                } else {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            AlertsCreator.processError(tLRPC$TL_error, baseFragment, tLRPC$TL_messages_editMessage, new Object[0]);
                        }
                    });
                }
            }
        });
    }

    public TLRPC$TL_photo generatePhotoSizes(String str, Uri uri) {
        Bitmap loadBitmap = ImageLoader.loadBitmap(str, uri, (float) AndroidUtilities.getPhotoSize(), (float) AndroidUtilities.getPhotoSize(), true);
        if (loadBitmap == null && AndroidUtilities.getPhotoSize() != 800) {
            loadBitmap = ImageLoader.loadBitmap(str, uri, 800.0f, 800.0f, true);
        }
        ArrayList arrayList = new ArrayList();
        PhotoSize scaleAndSaveImage = ImageLoader.scaleAndSaveImage(loadBitmap, 90.0f, 90.0f, 55, true);
        if (scaleAndSaveImage != null) {
            arrayList.add(scaleAndSaveImage);
        }
        scaleAndSaveImage = ImageLoader.scaleAndSaveImage(loadBitmap, (float) AndroidUtilities.getPhotoSize(), (float) AndroidUtilities.getPhotoSize(), 80, false, 101, 101);
        if (scaleAndSaveImage != null) {
            arrayList.add(scaleAndSaveImage);
        }
        if (loadBitmap != null) {
            loadBitmap.recycle();
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        UserConfig.saveConfig(false);
        TLRPC$TL_photo tLRPC$TL_photo = new TLRPC$TL_photo();
        tLRPC$TL_photo.date = ConnectionsManager.getInstance().getCurrentTime();
        tLRPC$TL_photo.sizes = arrayList;
        return tLRPC$TL_photo;
    }

    protected ArrayList<DelayedMessage> getDelayedMessages(String str) {
        return (ArrayList) this.delayedMessages.get(str);
    }

    protected long getNextRandomId() {
        long j = 0;
        while (j == 0) {
            j = Utilities.random.nextLong();
        }
        return j;
    }

    public boolean isSendingCallback(MessageObject messageObject, KeyboardButton keyboardButton) {
        int i = 0;
        if (messageObject == null || keyboardButton == null) {
            return false;
        }
        if (keyboardButton instanceof TLRPC$TL_keyboardButtonGame) {
            i = 1;
        } else if (keyboardButton instanceof TLRPC$TL_keyboardButtonBuy) {
            i = 2;
        }
        return this.waitingForCallback.containsKey(messageObject.getDialogId() + "_" + messageObject.getId() + "_" + Utilities.bytesToHex(keyboardButton.data) + "_" + i);
    }

    public boolean isSendingCurrentLocation(MessageObject messageObject, KeyboardButton keyboardButton) {
        if (messageObject == null || keyboardButton == null) {
            return false;
        }
        return this.waitingForLocation.containsKey(messageObject.getDialogId() + "_" + messageObject.getId() + "_" + Utilities.bytesToHex(keyboardButton.data) + "_" + (keyboardButton instanceof TLRPC$TL_keyboardButtonGame ? "1" : "0"));
    }

    public boolean isSendingMessage(int i) {
        return this.sendingMessages.containsKey(Integer.valueOf(i));
    }

    public void processForwardFromMyName(MessageObject messageObject, long j) {
        if (messageObject != null) {
            ArrayList arrayList;
            if (messageObject.messageOwner.media == null || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaEmpty) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice)) {
                if (messageObject.messageOwner.message != null) {
                    ArrayList arrayList2;
                    TLRPC$WebPage tLRPC$WebPage = null;
                    if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) {
                        tLRPC$WebPage = messageObject.messageOwner.media.webpage;
                    }
                    if (messageObject.messageOwner.entities == null || messageObject.messageOwner.entities.isEmpty()) {
                        arrayList2 = null;
                    } else {
                        arrayList2 = new ArrayList();
                        for (int i = 0; i < messageObject.messageOwner.entities.size(); i++) {
                            MessageEntity messageEntity = (MessageEntity) messageObject.messageOwner.entities.get(i);
                            if ((messageEntity instanceof TLRPC$TL_messageEntityBold) || (messageEntity instanceof TLRPC$TL_messageEntityItalic) || (messageEntity instanceof TLRPC$TL_messageEntityPre) || (messageEntity instanceof TLRPC$TL_messageEntityCode) || (messageEntity instanceof TLRPC$TL_messageEntityTextUrl)) {
                                arrayList2.add(messageEntity);
                            }
                        }
                    }
                    sendMessage(messageObject.messageOwner.message, j, messageObject.replyMessageObject, tLRPC$WebPage, true, arrayList2, null, null);
                } else if (((int) j) != 0) {
                    arrayList = new ArrayList();
                    arrayList.add(messageObject);
                    sendMessage(arrayList, j);
                }
            } else if (messageObject.messageOwner.media.photo instanceof TLRPC$TL_photo) {
                sendMessage((TLRPC$TL_photo) messageObject.messageOwner.media.photo, null, j, messageObject.replyMessageObject, null, null, messageObject.messageOwner.media.ttl_seconds);
            } else if (messageObject.messageOwner.media.document instanceof TLRPC$TL_document) {
                sendMessage((TLRPC$TL_document) messageObject.messageOwner.media.document, null, messageObject.messageOwner.attachPath, j, messageObject.replyMessageObject, null, null, messageObject.messageOwner.media.ttl_seconds);
            } else if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaVenue) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo)) {
                sendMessage(messageObject.messageOwner.media, j, messageObject.replyMessageObject, null, null);
            } else if (messageObject.messageOwner.media.phone_number != null) {
                User tLRPC$TL_userContact_old2 = new TLRPC$TL_userContact_old2();
                tLRPC$TL_userContact_old2.phone = messageObject.messageOwner.media.phone_number;
                tLRPC$TL_userContact_old2.first_name = messageObject.messageOwner.media.first_name;
                tLRPC$TL_userContact_old2.last_name = messageObject.messageOwner.media.last_name;
                tLRPC$TL_userContact_old2.id = messageObject.messageOwner.media.user_id;
                sendMessage(tLRPC$TL_userContact_old2, j, messageObject.replyMessageObject, null, null);
            } else if (((int) j) != 0) {
                arrayList = new ArrayList();
                arrayList.add(messageObject);
                sendMessage(arrayList, j);
            }
        }
    }

    public void processForwardFromMyName(MessageObject messageObject, long j, boolean z) {
        if (messageObject != null) {
            ArrayList arrayList;
            if (messageObject.messageOwner.media == null || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaEmpty) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage)) {
                if (messageObject.messageOwner.message != null) {
                    TLRPC$WebPage tLRPC$WebPage = null;
                    if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) {
                        tLRPC$WebPage = messageObject.messageOwner.media.webpage;
                    }
                    sendMessage(messageObject.messageOwner.message, j, messageObject.replyMessageObject, tLRPC$WebPage, true, messageObject.messageOwner.entities, null, null);
                    return;
                }
                arrayList = new ArrayList();
                arrayList.add(messageObject);
                sendMessage(arrayList, j);
            } else if (messageObject.messageOwner.media.photo instanceof TLRPC$TL_photo) {
                if (!TextUtils.isEmpty(messageObject.caption)) {
                    messageObject.messageOwner.media.photo.caption = messageObject.caption.toString();
                }
                sendMessage((TLRPC$TL_photo) messageObject.messageOwner.media.photo, null, j, messageObject.replyMessageObject, null, null, 0);
            } else if (messageObject.messageOwner.media.document instanceof TLRPC$TL_document) {
                if (!TextUtils.isEmpty(messageObject.caption)) {
                    messageObject.messageOwner.media.document.caption = messageObject.caption.toString();
                }
                sendMessage((TLRPC$TL_document) messageObject.messageOwner.media.document, null, messageObject.messageOwner.attachPath, j, messageObject.replyMessageObject, null, null, 0);
            } else if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaVenue) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo)) {
                sendMessage(messageObject.messageOwner.media, j, messageObject.replyMessageObject, null, null);
            } else if (messageObject.messageOwner.media.phone_number != null) {
                User tLRPC$TL_userContact_old2 = new TLRPC$TL_userContact_old2();
                tLRPC$TL_userContact_old2.phone = messageObject.messageOwner.media.phone_number;
                tLRPC$TL_userContact_old2.first_name = messageObject.messageOwner.media.first_name;
                tLRPC$TL_userContact_old2.last_name = messageObject.messageOwner.media.last_name;
                tLRPC$TL_userContact_old2.id = messageObject.messageOwner.media.user_id;
                sendMessage(tLRPC$TL_userContact_old2, j, messageObject.replyMessageObject, null, null);
            } else {
                arrayList = new ArrayList();
                arrayList.add(messageObject);
                sendMessage(arrayList, j);
            }
        }
    }

    protected void processSentMessage(int i) {
        int size = this.unsentMessages.size();
        this.unsentMessages.remove(Integer.valueOf(i));
        if (size != 0 && this.unsentMessages.size() == 0) {
            checkUnsentMessages();
        }
    }

    protected void processUnsentMessages(ArrayList<Message> arrayList, ArrayList<User> arrayList2, ArrayList<Chat> arrayList3, ArrayList<EncryptedChat> arrayList4) {
        final ArrayList<User> arrayList5 = arrayList2;
        final ArrayList<Chat> arrayList6 = arrayList3;
        final ArrayList<EncryptedChat> arrayList7 = arrayList4;
        final ArrayList<Message> arrayList8 = arrayList;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController.getInstance().putUsers(arrayList5, true);
                MessagesController.getInstance().putChats(arrayList6, true);
                MessagesController.getInstance().putEncryptedChats(arrayList7, true);
                for (int i = 0; i < arrayList8.size(); i++) {
                    SendMessagesHelper.this.retrySendMessage(new MessageObject((Message) arrayList8.get(i), null, false), true);
                }
            }
        });
    }

    protected void putToSendingMessages(Message message) {
        this.sendingMessages.put(Integer.valueOf(message.id), message);
    }

    protected void removeFromSendingMessages(int i) {
        this.sendingMessages.remove(Integer.valueOf(i));
    }

    public boolean retrySendMessage(MessageObject messageObject, boolean z) {
        if (messageObject.getId() >= 0) {
            return false;
        }
        if (messageObject.messageOwner.action instanceof TLRPC$TL_messageEncryptedAction) {
            EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (messageObject.getDialogId() >> 32)));
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
        if (z) {
            this.unsentMessages.put(Integer.valueOf(messageObject.getId()), messageObject);
        }
        sendMessage(messageObject);
        return true;
    }

    public void sendCallback(boolean z, MessageObject messageObject, KeyboardButton keyboardButton, ChatActivity chatActivity) {
        if (messageObject != null && keyboardButton != null && chatActivity != null) {
            int i;
            boolean z2;
            if (keyboardButton instanceof TLRPC$TL_keyboardButtonGame) {
                i = 1;
                z2 = false;
            } else if (keyboardButton instanceof TLRPC$TL_keyboardButtonBuy) {
                i = 2;
                z2 = z;
            } else {
                i = 0;
                z2 = z;
            }
            final String str = messageObject.getDialogId() + "_" + messageObject.getId() + "_" + Utilities.bytesToHex(keyboardButton.data) + "_" + i;
            this.waitingForCallback.put(str, messageObject);
            final MessageObject messageObject2 = messageObject;
            final KeyboardButton keyboardButton2 = keyboardButton;
            final ChatActivity chatActivity2 = chatActivity;
            RequestDelegate c34068 = new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            TLRPC$TL_game tLRPC$TL_game = null;
                            SendMessagesHelper.this.waitingForCallback.remove(str);
                            if (z2 && tLObject == null) {
                                SendMessagesHelper.this.sendCallback(false, messageObject2, keyboardButton2, chatActivity2);
                            } else if (tLObject == null) {
                            } else {
                                if (!(keyboardButton2 instanceof TLRPC$TL_keyboardButtonBuy)) {
                                    TLRPC$TL_messages_botCallbackAnswer tLRPC$TL_messages_botCallbackAnswer = (TLRPC$TL_messages_botCallbackAnswer) tLObject;
                                    if (!(z2 || tLRPC$TL_messages_botCallbackAnswer.cache_time == 0)) {
                                        MessagesStorage.getInstance().saveBotCache(str, tLRPC$TL_messages_botCallbackAnswer);
                                    }
                                    User user;
                                    if (tLRPC$TL_messages_botCallbackAnswer.message != null) {
                                        if (!tLRPC$TL_messages_botCallbackAnswer.alert) {
                                            String formatName;
                                            int i = messageObject2.messageOwner.from_id;
                                            if (messageObject2.messageOwner.via_bot_id != 0) {
                                                i = messageObject2.messageOwner.via_bot_id;
                                            }
                                            if (i > 0) {
                                                user = MessagesController.getInstance().getUser(Integer.valueOf(i));
                                                if (user != null) {
                                                    formatName = ContactsController.formatName(user.first_name, user.last_name);
                                                }
                                                formatName = null;
                                            } else {
                                                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-i));
                                                if (chat != null) {
                                                    formatName = chat.title;
                                                }
                                                formatName = null;
                                            }
                                            if (formatName == null) {
                                                formatName = "bot";
                                            }
                                            chatActivity2.showAlert(formatName, tLRPC$TL_messages_botCallbackAnswer.message);
                                        } else if (chatActivity2.getParentActivity() != null) {
                                            Builder builder = new Builder(chatActivity2.getParentActivity());
                                            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                            builder.setMessage(tLRPC$TL_messages_botCallbackAnswer.message);
                                            chatActivity2.showDialog(builder.create());
                                        }
                                    } else if (tLRPC$TL_messages_botCallbackAnswer.url != null && chatActivity2.getParentActivity() != null) {
                                        int i2 = messageObject2.messageOwner.from_id;
                                        if (messageObject2.messageOwner.via_bot_id != 0) {
                                            i2 = messageObject2.messageOwner.via_bot_id;
                                        }
                                        user = MessagesController.getInstance().getUser(Integer.valueOf(i2));
                                        boolean z = user != null && user.verified;
                                        if (keyboardButton2 instanceof TLRPC$TL_keyboardButtonGame) {
                                            if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                                                tLRPC$TL_game = messageObject2.messageOwner.media.game;
                                            }
                                            if (tLRPC$TL_game != null) {
                                                ChatActivity chatActivity = chatActivity2;
                                                MessageObject messageObject = messageObject2;
                                                String str = tLRPC$TL_messages_botCallbackAnswer.url;
                                                z = !z && ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("askgame_" + i2, true);
                                                chatActivity.showOpenGameAlert(tLRPC$TL_game, messageObject, str, z, i2);
                                                return;
                                            }
                                            return;
                                        }
                                        chatActivity2.showOpenUrlAlert(tLRPC$TL_messages_botCallbackAnswer.url, false);
                                    }
                                } else if (tLObject instanceof TLRPC$TL_payments_paymentForm) {
                                    TLRPC$TL_payments_paymentForm tLRPC$TL_payments_paymentForm = (TLRPC$TL_payments_paymentForm) tLObject;
                                    MessagesController.getInstance().putUsers(tLRPC$TL_payments_paymentForm.users, false);
                                    chatActivity2.presentFragment(new PaymentFormActivity(tLRPC$TL_payments_paymentForm, messageObject2));
                                } else if (tLObject instanceof TLRPC$TL_payments_paymentReceipt) {
                                    chatActivity2.presentFragment(new PaymentFormActivity(messageObject2, (TLRPC$TL_payments_paymentReceipt) tLObject));
                                }
                            }
                        }
                    });
                }
            };
            if (z2) {
                MessagesStorage.getInstance().getBotCache(str, c34068);
            } else if (!(keyboardButton instanceof TLRPC$TL_keyboardButtonBuy)) {
                r1 = new TLRPC$TL_messages_getBotCallbackAnswer();
                r1.peer = MessagesController.getInputPeer((int) messageObject.getDialogId());
                r1.msg_id = messageObject.getId();
                r1.game = keyboardButton instanceof TLRPC$TL_keyboardButtonGame;
                if (keyboardButton.data != null) {
                    r1.flags |= 1;
                    r1.data = keyboardButton.data;
                }
                ConnectionsManager.getInstance().sendRequest(r1, c34068, 2);
            } else if ((messageObject.messageOwner.media.flags & 4) == 0) {
                r1 = new TLRPC$TL_payments_getPaymentForm();
                r1.msg_id = messageObject.getId();
                ConnectionsManager.getInstance().sendRequest(r1, c34068, 2);
            } else {
                r1 = new TLRPC$TL_payments_getPaymentReceipt();
                r1.msg_id = messageObject.messageOwner.media.receipt_msg_id;
                ConnectionsManager.getInstance().sendRequest(r1, c34068, 2);
            }
        }
    }

    public void sendCurrentLocation(MessageObject messageObject, KeyboardButton keyboardButton) {
        if (messageObject != null && keyboardButton != null) {
            this.waitingForLocation.put(messageObject.getDialogId() + "_" + messageObject.getId() + "_" + Utilities.bytesToHex(keyboardButton.data) + "_" + (keyboardButton instanceof TLRPC$TL_keyboardButtonGame ? "1" : "0"), messageObject);
            this.locationProvider.start();
        }
    }

    public void sendGame(InputPeer inputPeer, TLRPC$TL_inputMediaGame tLRPC$TL_inputMediaGame, long j, long j2) {
        NativeByteBuffer nativeByteBuffer;
        Throwable e;
        if (inputPeer != null && tLRPC$TL_inputMediaGame != null) {
            TLObject tLRPC$TL_messages_sendMedia = new TLRPC$TL_messages_sendMedia();
            tLRPC$TL_messages_sendMedia.peer = inputPeer;
            if (tLRPC$TL_messages_sendMedia.peer instanceof TLRPC$TL_inputPeerChannel) {
                tLRPC$TL_messages_sendMedia.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + inputPeer.channel_id, false);
            }
            tLRPC$TL_messages_sendMedia.random_id = j != 0 ? j : getNextRandomId();
            tLRPC$TL_messages_sendMedia.media = tLRPC$TL_inputMediaGame;
            if (j2 == 0) {
                try {
                    nativeByteBuffer = new NativeByteBuffer(((inputPeer.getObjectSize() + tLRPC$TL_inputMediaGame.getObjectSize()) + 4) + 8);
                    try {
                        nativeByteBuffer.writeInt32(3);
                        nativeByteBuffer.writeInt64(j);
                        inputPeer.serializeToStream(nativeByteBuffer);
                        tLRPC$TL_inputMediaGame.serializeToStream(nativeByteBuffer);
                    } catch (Exception e2) {
                        e = e2;
                        FileLog.m13728e(e);
                        j2 = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_sendMedia, new RequestDelegate() {
                            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                if (tLRPC$TL_error == null) {
                                    MessagesController.getInstance().processUpdates((TLRPC$Updates) tLObject, false);
                                }
                                if (j2 != 0) {
                                    MessagesStorage.getInstance().removePendingTask(j2);
                                }
                            }
                        });
                    }
                } catch (Throwable e3) {
                    Throwable th = e3;
                    nativeByteBuffer = null;
                    e = th;
                    FileLog.m13728e(e);
                    j2 = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
                    ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_sendMedia, /* anonymous class already generated */);
                }
                j2 = MessagesStorage.getInstance().createPendingTask(nativeByteBuffer);
            }
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_sendMedia, /* anonymous class already generated */);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int sendMessage(java.util.ArrayList<org.telegram.messenger.MessageObject> r35, long r36) {
        /*
        r34 = this;
        if (r35 == 0) goto L_0x0008;
    L_0x0002:
        r4 = r35.isEmpty();
        if (r4 == 0) goto L_0x000b;
    L_0x0008:
        r25 = 0;
    L_0x000a:
        return r25;
    L_0x000b:
        r0 = r36;
        r14 = (int) r0;
        r9 = 0;
        if (r14 == 0) goto L_0x0587;
    L_0x0011:
        r0 = r36;
        r4 = (int) r0;
        r13 = org.telegram.messenger.MessagesController.getPeer(r4);
        r8 = 0;
        r7 = 0;
        r4 = 1;
        r5 = 1;
        r6 = 1;
        if (r14 <= 0) goto L_0x00a3;
    L_0x001f:
        r10 = org.telegram.messenger.MessagesController.getInstance();
        r11 = java.lang.Integer.valueOf(r14);
        r10 = r10.getUser(r11);
        if (r10 != 0) goto L_0x0030;
    L_0x002d:
        r25 = 0;
        goto L_0x000a;
    L_0x0030:
        r16 = r6;
        r17 = r5;
        r18 = r4;
        r19 = r7;
        r20 = r8;
    L_0x003a:
        r26 = new java.util.HashMap;
        r26.<init>();
        r12 = new java.util.ArrayList;
        r12.<init>();
        r11 = new java.util.ArrayList;
        r11.<init>();
        r6 = new java.util.ArrayList;
        r6.<init>();
        r5 = new java.util.ArrayList;
        r5.<init>();
        r10 = new java.util.HashMap;
        r10.<init>();
        r27 = org.telegram.messenger.MessagesController.getInputPeer(r14);
        r28 = org.telegram.messenger.UserConfig.getClientUserId();
        r0 = r28;
        r14 = (long) r0;
        r4 = (r36 > r14 ? 1 : (r36 == r14 ? 0 : -1));
        if (r4 != 0) goto L_0x00e5;
    L_0x0067:
        r4 = 1;
        r21 = r4;
    L_0x006a:
        r4 = 0;
        r22 = r4;
        r23 = r5;
        r24 = r6;
        r25 = r9;
    L_0x0073:
        r4 = r35.size();
        r0 = r22;
        if (r0 >= r4) goto L_0x000a;
    L_0x007b:
        r0 = r35;
        r1 = r22;
        r4 = r0.get(r1);
        r15 = r4;
        r15 = (org.telegram.messenger.MessageObject) r15;
        r4 = r15.getId();
        if (r4 <= 0) goto L_0x05a6;
    L_0x008c:
        r4 = r15.isSecretPhoto();
        if (r4 == 0) goto L_0x00e9;
    L_0x0092:
        r5 = r23;
        r6 = r24;
        r7 = r25;
    L_0x0098:
        r4 = r22 + 1;
        r22 = r4;
        r23 = r5;
        r24 = r6;
        r25 = r7;
        goto L_0x0073;
    L_0x00a3:
        r10 = org.telegram.messenger.MessagesController.getInstance();
        r11 = -r14;
        r11 = java.lang.Integer.valueOf(r11);
        r10 = r10.getChat(r11);
        r11 = org.telegram.messenger.ChatObject.isChannel(r10);
        if (r11 == 0) goto L_0x05bd;
    L_0x00b6:
        r8 = r10.megagroup;
        r7 = r10.signatures;
        r11 = r10.banned_rights;
        if (r11 == 0) goto L_0x05b1;
    L_0x00be:
        r4 = r10.banned_rights;
        r4 = r4.send_stickers;
        if (r4 != 0) goto L_0x00df;
    L_0x00c4:
        r4 = 1;
    L_0x00c5:
        r5 = r10.banned_rights;
        r5 = r5.send_media;
        if (r5 != 0) goto L_0x00e1;
    L_0x00cb:
        r5 = 1;
    L_0x00cc:
        r6 = r10.banned_rights;
        r6 = r6.embed_links;
        if (r6 != 0) goto L_0x00e3;
    L_0x00d2:
        r6 = 1;
    L_0x00d3:
        r16 = r6;
        r17 = r5;
        r18 = r4;
        r19 = r7;
        r20 = r8;
        goto L_0x003a;
    L_0x00df:
        r4 = 0;
        goto L_0x00c5;
    L_0x00e1:
        r5 = 0;
        goto L_0x00cc;
    L_0x00e3:
        r6 = 0;
        goto L_0x00d3;
    L_0x00e5:
        r4 = 0;
        r21 = r4;
        goto L_0x006a;
    L_0x00e9:
        if (r18 != 0) goto L_0x0108;
    L_0x00eb:
        r4 = r15.isSticker();
        if (r4 != 0) goto L_0x00fd;
    L_0x00f1:
        r4 = r15.isGif();
        if (r4 != 0) goto L_0x00fd;
    L_0x00f7:
        r4 = r15.isGame();
        if (r4 == 0) goto L_0x0108;
    L_0x00fd:
        if (r25 != 0) goto L_0x05a6;
    L_0x00ff:
        r25 = 1;
        r5 = r23;
        r6 = r24;
        r7 = r25;
        goto L_0x0098;
    L_0x0108:
        if (r17 != 0) goto L_0x0126;
    L_0x010a:
        r4 = r15.messageOwner;
        r4 = r4.media;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
        if (r4 != 0) goto L_0x011a;
    L_0x0112:
        r4 = r15.messageOwner;
        r4 = r4.media;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
        if (r4 == 0) goto L_0x0126;
    L_0x011a:
        if (r25 != 0) goto L_0x05a6;
    L_0x011c:
        r25 = 2;
        r5 = r23;
        r6 = r24;
        r7 = r25;
        goto L_0x0098;
    L_0x0126:
        r5 = 0;
        r6 = new org.telegram.tgnet.TLRPC$TL_message;
        r6.<init>();
        r4 = r15.isForwarded();
        if (r4 == 0) goto L_0x0489;
    L_0x0132:
        r4 = new org.telegram.tgnet.TLRPC$TL_messageFwdHeader;
        r4.<init>();
        r6.fwd_from = r4;
        r4 = r6.fwd_from;
        r7 = r15.messageOwner;
        r7 = r7.fwd_from;
        r7 = r7.flags;
        r4.flags = r7;
        r4 = r6.fwd_from;
        r7 = r15.messageOwner;
        r7 = r7.fwd_from;
        r7 = r7.from_id;
        r4.from_id = r7;
        r4 = r6.fwd_from;
        r7 = r15.messageOwner;
        r7 = r7.fwd_from;
        r7 = r7.date;
        r4.date = r7;
        r4 = r6.fwd_from;
        r7 = r15.messageOwner;
        r7 = r7.fwd_from;
        r7 = r7.channel_id;
        r4.channel_id = r7;
        r4 = r6.fwd_from;
        r7 = r15.messageOwner;
        r7 = r7.fwd_from;
        r7 = r7.channel_post;
        r4.channel_post = r7;
        r4 = r6.fwd_from;
        r7 = r15.messageOwner;
        r7 = r7.fwd_from;
        r7 = r7.post_author;
        r4.post_author = r7;
        r4 = 4;
        r6.flags = r4;
    L_0x0178:
        r0 = r28;
        r8 = (long) r0;
        r4 = (r36 > r8 ? 1 : (r36 == r8 ? 0 : -1));
        if (r4 != 0) goto L_0x019b;
    L_0x017f:
        r4 = r6.fwd_from;
        if (r4 == 0) goto L_0x019b;
    L_0x0183:
        r4 = r6.fwd_from;
        r7 = r4.flags;
        r7 = r7 | 16;
        r4.flags = r7;
        r4 = r6.fwd_from;
        r7 = r15.getId();
        r4.saved_from_msg_id = r7;
        r4 = r6.fwd_from;
        r7 = r15.messageOwner;
        r7 = r7.to_id;
        r4.saved_from_peer = r7;
    L_0x019b:
        if (r16 != 0) goto L_0x053f;
    L_0x019d:
        r4 = r15.messageOwner;
        r4 = r4.media;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
        if (r4 == 0) goto L_0x053f;
    L_0x01a5:
        r4 = new org.telegram.tgnet.TLRPC$TL_messageMediaEmpty;
        r4.<init>();
        r6.media = r4;
    L_0x01ac:
        r4 = r6.media;
        if (r4 == 0) goto L_0x01b6;
    L_0x01b0:
        r4 = r6.flags;
        r4 = r4 | 512;
        r6.flags = r4;
    L_0x01b6:
        if (r20 == 0) goto L_0x01bf;
    L_0x01b8:
        r4 = r6.flags;
        r7 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r4 = r4 | r7;
        r6.flags = r4;
    L_0x01bf:
        r4 = r15.messageOwner;
        r4 = r4.via_bot_id;
        if (r4 == 0) goto L_0x01d1;
    L_0x01c5:
        r4 = r15.messageOwner;
        r4 = r4.via_bot_id;
        r6.via_bot_id = r4;
        r4 = r6.flags;
        r4 = r4 | 2048;
        r6.flags = r4;
    L_0x01d1:
        r4 = r15.messageOwner;
        r4 = r4.message;
        r6.message = r4;
        r4 = r15.getId();
        r6.fwd_msg_id = r4;
        r4 = r15.messageOwner;
        r4 = r4.attachPath;
        r6.attachPath = r4;
        r4 = r15.messageOwner;
        r4 = r4.entities;
        r6.entities = r4;
        r4 = r6.entities;
        r4 = r4.isEmpty();
        if (r4 != 0) goto L_0x01f7;
    L_0x01f1:
        r4 = r6.flags;
        r4 = r4 | 128;
        r6.flags = r4;
    L_0x01f7:
        r4 = r6.attachPath;
        if (r4 != 0) goto L_0x0200;
    L_0x01fb:
        r4 = "";
        r6.attachPath = r4;
    L_0x0200:
        r4 = org.telegram.messenger.UserConfig.getNewMessageId();
        r6.id = r4;
        r6.local_id = r4;
        r4 = 1;
        r6.out = r4;
        r4 = r15.messageOwner;
        r0 = r4.grouped_id;
        r30 = r0;
        r8 = 0;
        r4 = (r30 > r8 ? 1 : (r30 == r8 ? 0 : -1));
        if (r4 == 0) goto L_0x024d;
    L_0x0217:
        r4 = r15.messageOwner;
        r8 = r4.grouped_id;
        r4 = java.lang.Long.valueOf(r8);
        r0 = r26;
        r4 = r0.get(r4);
        r4 = (java.lang.Long) r4;
        if (r4 != 0) goto L_0x0240;
    L_0x0229:
        r4 = org.telegram.messenger.Utilities.random;
        r8 = r4.nextLong();
        r4 = java.lang.Long.valueOf(r8);
        r7 = r15.messageOwner;
        r8 = r7.grouped_id;
        r7 = java.lang.Long.valueOf(r8);
        r0 = r26;
        r0.put(r7, r4);
    L_0x0240:
        r8 = r4.longValue();
        r6.grouped_id = r8;
        r4 = r6.flags;
        r7 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        r4 = r4 | r7;
        r6.flags = r4;
    L_0x024d:
        r4 = r35.size();
        r4 = r4 + -1;
        r0 = r22;
        if (r0 == r4) goto L_0x05ae;
    L_0x0257:
        r4 = r22 + 1;
        r0 = r35;
        r4 = r0.get(r4);
        r4 = (org.telegram.messenger.MessageObject) r4;
        r4 = r4.messageOwner;
        r8 = r4.grouped_id;
        r4 = r15.messageOwner;
        r0 = r4.grouped_id;
        r32 = r0;
        r4 = (r8 > r32 ? 1 : (r8 == r32 ? 0 : -1));
        if (r4 == 0) goto L_0x05ae;
    L_0x026f:
        r4 = 1;
    L_0x0270:
        r5 = r13.channel_id;
        if (r5 == 0) goto L_0x054c;
    L_0x0274:
        if (r20 != 0) goto L_0x054c;
    L_0x0276:
        if (r19 == 0) goto L_0x0547;
    L_0x0278:
        r5 = org.telegram.messenger.UserConfig.getClientUserId();
    L_0x027c:
        r6.from_id = r5;
        r5 = 1;
        r6.post = r5;
    L_0x0281:
        r8 = r6.random_id;
        r32 = 0;
        r5 = (r8 > r32 ? 1 : (r8 == r32 ? 0 : -1));
        if (r5 != 0) goto L_0x028f;
    L_0x0289:
        r8 = r34.getNextRandomId();
        r6.random_id = r8;
    L_0x028f:
        r8 = r6.random_id;
        r5 = java.lang.Long.valueOf(r8);
        r0 = r24;
        r0.add(r5);
        r8 = r6.random_id;
        r5 = java.lang.Long.valueOf(r8);
        r10.put(r5, r6);
        r5 = r6.fwd_msg_id;
        r5 = java.lang.Integer.valueOf(r5);
        r0 = r23;
        r0.add(r5);
        r5 = org.telegram.tgnet.ConnectionsManager.getInstance();
        r5 = r5.getCurrentTime();
        r6.date = r5;
        r0 = r27;
        r5 = r0 instanceof org.telegram.tgnet.TLRPC$TL_inputPeerChannel;
        if (r5 == 0) goto L_0x055f;
    L_0x02be:
        if (r20 != 0) goto L_0x055a;
    L_0x02c0:
        r5 = 1;
        r6.views = r5;
        r5 = r6.flags;
        r5 = r5 | 1024;
        r6.flags = r5;
    L_0x02c9:
        r0 = r36;
        r6.dialog_id = r0;
        r6.to_id = r13;
        r5 = org.telegram.messenger.MessageObject.isVoiceMessage(r6);
        if (r5 != 0) goto L_0x02db;
    L_0x02d5:
        r5 = org.telegram.messenger.MessageObject.isRoundVideoMessage(r6);
        if (r5 == 0) goto L_0x02de;
    L_0x02db:
        r5 = 1;
        r6.media_unread = r5;
    L_0x02de:
        r5 = r15.messageOwner;
        r5 = r5.to_id;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_peerChannel;
        if (r5 == 0) goto L_0x02ef;
    L_0x02e6:
        r5 = r15.messageOwner;
        r5 = r5.to_id;
        r5 = r5.channel_id;
        r5 = -r5;
        r6.ttl = r5;
    L_0x02ef:
        r5 = new org.telegram.messenger.MessageObject;
        r7 = 0;
        r8 = 1;
        r5.<init>(r6, r7, r8);
        r7 = r5.messageOwner;
        r8 = 1;
        r7.send_state = r8;
        r12.add(r5);
        r11.add(r6);
        r0 = r34;
        r0.putToSendingMessages(r6);
        r5 = org.telegram.messenger.BuildVars.DEBUG_VERSION;
        if (r5 == 0) goto L_0x0352;
    L_0x030a:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "forward message user_id = ";
        r5 = r5.append(r6);
        r0 = r27;
        r6 = r0.user_id;
        r5 = r5.append(r6);
        r6 = " chat_id = ";
        r5 = r5.append(r6);
        r0 = r27;
        r6 = r0.chat_id;
        r5 = r5.append(r6);
        r6 = " channel_id = ";
        r5 = r5.append(r6);
        r0 = r27;
        r6 = r0.channel_id;
        r5 = r5.append(r6);
        r6 = " access_hash = ";
        r5 = r5.append(r6);
        r0 = r27;
        r6 = r0.access_hash;
        r5 = r5.append(r6);
        r5 = r5.toString();
        org.telegram.messenger.FileLog.m13726e(r5);
    L_0x0352:
        if (r4 == 0) goto L_0x035a;
    L_0x0354:
        r4 = r11.size();
        if (r4 > 0) goto L_0x038c;
    L_0x035a:
        r4 = r11.size();
        r5 = 100;
        if (r4 == r5) goto L_0x038c;
    L_0x0362:
        r4 = r35.size();
        r4 = r4 + -1;
        r0 = r22;
        if (r0 == r4) goto L_0x038c;
    L_0x036c:
        r4 = r35.size();
        r4 = r4 + -1;
        r0 = r22;
        if (r0 == r4) goto L_0x05a6;
    L_0x0376:
        r4 = r22 + 1;
        r0 = r35;
        r4 = r0.get(r4);
        r4 = (org.telegram.messenger.MessageObject) r4;
        r4 = r4.getDialogId();
        r6 = r15.getDialogId();
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x05a6;
    L_0x038c:
        r4 = org.telegram.messenger.MessagesStorage.getInstance();
        r5 = new java.util.ArrayList;
        r5.<init>(r11);
        r6 = 0;
        r7 = 1;
        r8 = 0;
        r9 = 0;
        r4.putMessages(r5, r6, r7, r8, r9);
        r4 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r36;
        r4.updateInterfaceWithMessages(r0, r12);
        r4 = org.telegram.messenger.NotificationCenter.getInstance();
        r5 = org.telegram.messenger.NotificationCenter.dialogsNeedReload;
        r6 = 0;
        r6 = new java.lang.Object[r6];
        r4.postNotificationName(r5, r6);
        r4 = 0;
        org.telegram.messenger.UserConfig.saveConfig(r4);
        r14 = new org.telegram.tgnet.TLRPC$TL_messages_forwardMessages;
        r14.<init>();
        r0 = r27;
        r14.to_peer = r0;
        r4 = 0;
        r4 = (r30 > r4 ? 1 : (r30 == r4 ? 0 : -1));
        if (r4 == 0) goto L_0x0578;
    L_0x03c4:
        r4 = 1;
    L_0x03c5:
        r14.grouped = r4;
        r4 = r14.to_peer;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_inputPeerChannel;
        if (r4 == 0) goto L_0x03f4;
    L_0x03cd:
        r4 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r5 = "Notifications";
        r6 = 0;
        r4 = r4.getSharedPreferences(r5, r6);
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "silent_";
        r5 = r5.append(r6);
        r0 = r36;
        r5 = r5.append(r0);
        r5 = r5.toString();
        r6 = 0;
        r4 = r4.getBoolean(r5, r6);
        r14.silent = r4;
    L_0x03f4:
        r4 = r15.messageOwner;
        r4 = r4.to_id;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_peerChannel;
        if (r4 == 0) goto L_0x057b;
    L_0x03fc:
        r4 = org.telegram.messenger.MessagesController.getInstance();
        r5 = r15.messageOwner;
        r5 = r5.to_id;
        r5 = r5.channel_id;
        r5 = java.lang.Integer.valueOf(r5);
        r4 = r4.getChat(r5);
        r5 = new org.telegram.tgnet.TLRPC$TL_inputPeerChannel;
        r5.<init>();
        r14.from_peer = r5;
        r5 = r14.from_peer;
        r6 = r15.messageOwner;
        r6 = r6.to_id;
        r6 = r6.channel_id;
        r5.channel_id = r6;
        if (r4 == 0) goto L_0x0427;
    L_0x0421:
        r5 = r14.from_peer;
        r6 = r4.access_hash;
        r5.access_hash = r6;
    L_0x0427:
        r0 = r24;
        r14.random_id = r0;
        r0 = r23;
        r14.id = r0;
        r4 = r35.size();
        r5 = 1;
        if (r4 != r5) goto L_0x0584;
    L_0x0436:
        r4 = 0;
        r0 = r35;
        r4 = r0.get(r4);
        r4 = (org.telegram.messenger.MessageObject) r4;
        r4 = r4.messageOwner;
        r4 = r4.with_my_score;
        if (r4 == 0) goto L_0x0584;
    L_0x0445:
        r4 = 1;
    L_0x0446:
        r14.with_my_score = r4;
        r15 = org.telegram.tgnet.ConnectionsManager.getInstance();
        r4 = new org.telegram.messenger.SendMessagesHelper$6;
        r5 = r34;
        r6 = r36;
        r8 = r20;
        r9 = r21;
        r4.<init>(r6, r8, r9, r10, r11, r12, r13, r14);
        r5 = 68;
        r15.sendRequest(r14, r4, r5);
        r4 = r35.size();
        r4 = r4 + -1;
        r0 = r22;
        if (r0 == r4) goto L_0x05a6;
    L_0x0468:
        r12 = new java.util.ArrayList;
        r12.<init>();
        r11 = new java.util.ArrayList;
        r11.<init>();
        r24 = new java.util.ArrayList;
        r24.<init>();
        r23 = new java.util.ArrayList;
        r23.<init>();
        r10 = new java.util.HashMap;
        r10.<init>();
        r5 = r23;
        r6 = r24;
        r7 = r25;
        goto L_0x0098;
    L_0x0489:
        r4 = new org.telegram.tgnet.TLRPC$TL_messageFwdHeader;
        r4.<init>();
        r6.fwd_from = r4;
        r4 = r6.fwd_from;
        r7 = r15.getId();
        r4.channel_post = r7;
        r4 = r6.fwd_from;
        r7 = r4.flags;
        r7 = r7 | 4;
        r4.flags = r7;
        r4 = r15.isFromUser();
        if (r4 == 0) goto L_0x04d7;
    L_0x04a6:
        r4 = r6.fwd_from;
        r7 = r15.messageOwner;
        r7 = r7.from_id;
        r4.from_id = r7;
        r4 = r6.fwd_from;
        r7 = r4.flags;
        r7 = r7 | 1;
        r4.flags = r7;
    L_0x04b6:
        r4 = r15.messageOwner;
        r4 = r4.post_author;
        if (r4 == 0) goto L_0x0506;
    L_0x04bc:
        r4 = r6.fwd_from;
        r7 = r15.messageOwner;
        r7 = r7.post_author;
        r4.post_author = r7;
        r4 = r6.fwd_from;
        r7 = r4.flags;
        r7 = r7 | 8;
        r4.flags = r7;
    L_0x04cc:
        r4 = r15.messageOwner;
        r4 = r4.date;
        r6.date = r4;
        r4 = 4;
        r6.flags = r4;
        goto L_0x0178;
    L_0x04d7:
        r4 = r6.fwd_from;
        r7 = r15.messageOwner;
        r7 = r7.to_id;
        r7 = r7.channel_id;
        r4.channel_id = r7;
        r4 = r6.fwd_from;
        r7 = r4.flags;
        r7 = r7 | 2;
        r4.flags = r7;
        r4 = r15.messageOwner;
        r4 = r4.post;
        if (r4 == 0) goto L_0x04b6;
    L_0x04ef:
        r4 = r15.messageOwner;
        r4 = r4.from_id;
        if (r4 <= 0) goto L_0x04b6;
    L_0x04f5:
        r4 = r6.fwd_from;
        r7 = r15.messageOwner;
        r7 = r7.from_id;
        r4.from_id = r7;
        r4 = r6.fwd_from;
        r7 = r4.flags;
        r7 = r7 | 1;
        r4.flags = r7;
        goto L_0x04b6;
    L_0x0506:
        r4 = r15.isOutOwner();
        if (r4 != 0) goto L_0x04cc;
    L_0x050c:
        r4 = r15.messageOwner;
        r4 = r4.from_id;
        if (r4 <= 0) goto L_0x04cc;
    L_0x0512:
        r4 = r15.messageOwner;
        r4 = r4.post;
        if (r4 == 0) goto L_0x04cc;
    L_0x0518:
        r4 = org.telegram.messenger.MessagesController.getInstance();
        r7 = r15.messageOwner;
        r7 = r7.from_id;
        r7 = java.lang.Integer.valueOf(r7);
        r4 = r4.getUser(r7);
        if (r4 == 0) goto L_0x04cc;
    L_0x052a:
        r7 = r6.fwd_from;
        r8 = r4.first_name;
        r4 = r4.last_name;
        r4 = org.telegram.messenger.ContactsController.formatName(r8, r4);
        r7.post_author = r4;
        r4 = r6.fwd_from;
        r7 = r4.flags;
        r7 = r7 | 8;
        r4.flags = r7;
        goto L_0x04cc;
    L_0x053f:
        r4 = r15.messageOwner;
        r4 = r4.media;
        r6.media = r4;
        goto L_0x01ac;
    L_0x0547:
        r5 = r13.channel_id;
        r5 = -r5;
        goto L_0x027c;
    L_0x054c:
        r5 = org.telegram.messenger.UserConfig.getClientUserId();
        r6.from_id = r5;
        r5 = r6.flags;
        r5 = r5 | 256;
        r6.flags = r5;
        goto L_0x0281;
    L_0x055a:
        r5 = 1;
        r6.unread = r5;
        goto L_0x02c9;
    L_0x055f:
        r5 = r15.messageOwner;
        r5 = r5.flags;
        r5 = r5 & 1024;
        if (r5 == 0) goto L_0x0573;
    L_0x0567:
        r5 = r15.messageOwner;
        r5 = r5.views;
        r6.views = r5;
        r5 = r6.flags;
        r5 = r5 | 1024;
        r6.flags = r5;
    L_0x0573:
        r5 = 1;
        r6.unread = r5;
        goto L_0x02c9;
    L_0x0578:
        r4 = 0;
        goto L_0x03c5;
    L_0x057b:
        r4 = new org.telegram.tgnet.TLRPC$TL_inputPeerEmpty;
        r4.<init>();
        r14.from_peer = r4;
        goto L_0x0427;
    L_0x0584:
        r4 = 0;
        goto L_0x0446;
    L_0x0587:
        r4 = 0;
        r5 = r4;
    L_0x0589:
        r4 = r35.size();
        if (r5 >= r4) goto L_0x05a2;
    L_0x058f:
        r0 = r35;
        r4 = r0.get(r5);
        r4 = (org.telegram.messenger.MessageObject) r4;
        r0 = r34;
        r1 = r36;
        r0.processForwardFromMyName(r4, r1);
        r4 = r5 + 1;
        r5 = r4;
        goto L_0x0589;
    L_0x05a2:
        r25 = r9;
        goto L_0x000a;
    L_0x05a6:
        r5 = r23;
        r6 = r24;
        r7 = r25;
        goto L_0x0098;
    L_0x05ae:
        r4 = r5;
        goto L_0x0270;
    L_0x05b1:
        r16 = r6;
        r17 = r5;
        r18 = r4;
        r19 = r7;
        r20 = r8;
        goto L_0x003a;
    L_0x05bd:
        r16 = r6;
        r17 = r5;
        r18 = r4;
        r19 = r7;
        r20 = r8;
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.sendMessage(java.util.ArrayList, long):int");
    }

    public void sendMessage(String str, long j, MessageObject messageObject, TLRPC$WebPage tLRPC$WebPage, boolean z, ArrayList<MessageEntity> arrayList, ReplyMarkup replyMarkup, HashMap<String, String> hashMap) {
        sendMessage(str, null, null, null, null, null, null, j, null, messageObject, tLRPC$WebPage, z, null, arrayList, replyMarkup, hashMap, 0);
    }

    public void sendMessage(MessageObject messageObject) {
        sendMessage(null, null, null, null, null, null, null, messageObject.getDialogId(), messageObject.messageOwner.attachPath, null, null, true, messageObject, null, messageObject.messageOwner.reply_markup, messageObject.messageOwner.params, 0);
    }

    public void sendMessage(MessageMedia messageMedia, long j, MessageObject messageObject, ReplyMarkup replyMarkup, HashMap<String, String> hashMap) {
        sendMessage(null, messageMedia, null, null, null, null, null, j, null, messageObject, null, true, null, null, replyMarkup, hashMap, 0);
    }

    public void sendMessage(TLRPC$TL_document tLRPC$TL_document, VideoEditedInfo videoEditedInfo, String str, long j, MessageObject messageObject, ReplyMarkup replyMarkup, HashMap<String, String> hashMap, int i) {
        sendMessage(null, null, null, videoEditedInfo, null, tLRPC$TL_document, null, j, str, messageObject, null, true, null, null, replyMarkup, hashMap, i);
    }

    public void sendMessage(TLRPC$TL_game tLRPC$TL_game, long j, ReplyMarkup replyMarkup, HashMap<String, String> hashMap) {
        sendMessage(null, null, null, null, null, null, tLRPC$TL_game, j, null, null, null, true, null, null, replyMarkup, hashMap, 0);
    }

    public void sendMessage(TLRPC$TL_photo tLRPC$TL_photo, String str, long j, MessageObject messageObject, ReplyMarkup replyMarkup, HashMap<String, String> hashMap, int i) {
        sendMessage(null, null, tLRPC$TL_photo, null, null, null, null, j, str, messageObject, null, true, null, null, replyMarkup, hashMap, i);
    }

    public void sendMessage(User user, long j, MessageObject messageObject, ReplyMarkup replyMarkup, HashMap<String, String> hashMap) {
        sendMessage(null, null, null, null, user, null, null, j, null, messageObject, null, true, null, null, replyMarkup, hashMap, 0);
    }

    public void sendScreenshotMessage(User user, int i, Message message) {
        if (user != null && i != 0 && user.id != UserConfig.getClientUserId()) {
            TLObject tLRPC$TL_messages_sendScreenshotNotification = new TLRPC$TL_messages_sendScreenshotNotification();
            tLRPC$TL_messages_sendScreenshotNotification.peer = new TLRPC$TL_inputPeerUser();
            tLRPC$TL_messages_sendScreenshotNotification.peer.access_hash = user.access_hash;
            tLRPC$TL_messages_sendScreenshotNotification.peer.user_id = user.id;
            if (message != null) {
                tLRPC$TL_messages_sendScreenshotNotification.reply_to_msg_id = i;
                tLRPC$TL_messages_sendScreenshotNotification.random_id = message.random_id;
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
                message.reply_to_msg_id = i;
                message.to_id = new TLRPC$TL_peerUser();
                message.to_id.user_id = user.id;
                message.date = ConnectionsManager.getInstance().getCurrentTime();
                message.action = new TLRPC$TL_messageActionScreenshotTaken();
                UserConfig.saveConfig(false);
            }
            tLRPC$TL_messages_sendScreenshotNotification.random_id = message.random_id;
            MessageObject messageObject = new MessageObject(message, null, false);
            messageObject.messageOwner.send_state = 1;
            ArrayList arrayList = new ArrayList();
            arrayList.add(messageObject);
            MessagesController.getInstance().updateInterfaceWithMessages(message.dialog_id, arrayList);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(message);
            MessagesStorage.getInstance().putMessages(arrayList2, false, true, false, 0);
            performSendMessageRequest(tLRPC$TL_messages_sendScreenshotNotification, messageObject, null);
        }
    }

    public void sendSticker(Document document, long j, MessageObject messageObject, Context context) {
        if (document != null) {
            Document tLRPC$TL_document;
            if (((int) j) == 0) {
                if (MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (j >> 32))) != null) {
                    tLRPC$TL_document = new TLRPC$TL_document();
                    tLRPC$TL_document.id = document.id;
                    tLRPC$TL_document.access_hash = document.access_hash;
                    tLRPC$TL_document.date = document.date;
                    tLRPC$TL_document.mime_type = document.mime_type;
                    tLRPC$TL_document.size = document.size;
                    tLRPC$TL_document.dc_id = document.dc_id;
                    tLRPC$TL_document.attributes = new ArrayList(document.attributes);
                    if (tLRPC$TL_document.mime_type == null) {
                        tLRPC$TL_document.mime_type = TtmlNode.ANONYMOUS_REGION_ID;
                    }
                    if (document.thumb instanceof TLRPC$TL_photoSize) {
                        File pathToAttach = FileLoader.getPathToAttach(document.thumb, true);
                        if (pathToAttach.exists()) {
                            try {
                                int length = (int) pathToAttach.length();
                                byte[] bArr = new byte[((int) pathToAttach.length())];
                                new RandomAccessFile(pathToAttach, "r").readFully(bArr);
                                tLRPC$TL_document.thumb = new TLRPC$TL_photoCachedSize();
                                tLRPC$TL_document.thumb.location = document.thumb.location;
                                tLRPC$TL_document.thumb.size = document.thumb.size;
                                tLRPC$TL_document.thumb.f10147w = document.thumb.f10147w;
                                tLRPC$TL_document.thumb.f10146h = document.thumb.f10146h;
                                tLRPC$TL_document.thumb.type = document.thumb.type;
                                tLRPC$TL_document.thumb.bytes = bArr;
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                    }
                    if (tLRPC$TL_document.thumb == null) {
                        tLRPC$TL_document.thumb = new TLRPC$TL_photoSizeEmpty();
                        tLRPC$TL_document.thumb.type = "s";
                    }
                } else {
                    return;
                }
            }
            tLRPC$TL_document = document;
            if (ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("confirmForStickers", true)) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                    final long j2 = j;
                    final MessageObject messageObject2 = messageObject;
                    builder.setMessage("برای ارسال مطمینید؟").setCancelable(true).setPositiveButton("بله", new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SendMessagesHelper.getInstance().sendMessage((TLRPC$TL_document) tLRPC$TL_document, null, null, j2, messageObject2, null, null, 0);
                        }
                    }).setNegativeButton("خیر", new C33954());
                    builder.create().show();
                    return;
                } catch (Exception e2) {
                    getInstance().sendMessage((TLRPC$TL_document) tLRPC$TL_document, null, null, j, messageObject, null, null, 0);
                    return;
                }
            }
            getInstance().sendMessage((TLRPC$TL_document) tLRPC$TL_document, null, null, j, messageObject, null, null, 0);
        }
    }

    public void setCurrentChatInfo(ChatFull chatFull) {
        this.currentChatInfo = chatFull;
    }

    protected void stopVideoService(final String str) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.SendMessagesHelper$11$1 */
            class C33631 implements Runnable {
                C33631() {
                }

                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.stopEncodingService, str);
                }
            }

            public void run() {
                AndroidUtilities.runOnUIThread(new C33631());
            }
        });
    }
}
