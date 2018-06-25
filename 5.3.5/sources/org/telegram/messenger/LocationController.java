package org.telegram.messenger;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.LongSparseArray;
import android.util.SparseIntArray;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$GeoPoint;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputGeoPoint;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive;
import org.telegram.tgnet.TLRPC$TL_messages_editMessage;
import org.telegram.tgnet.TLRPC$TL_messages_getRecentLocations;
import org.telegram.tgnet.TLRPC$TL_updateEditChannelMessage;
import org.telegram.tgnet.TLRPC$TL_updateEditMessage;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.User;

public class LocationController implements NotificationCenterDelegate {
    private static final int BACKGROUD_UPDATE_TIME = 90000;
    private static final int FOREGROUND_UPDATE_TIME = 20000;
    private static volatile LocationController Instance = null;
    private static final int LOCATION_ACQUIRE_TIME = 10000;
    private static final double eps = 1.0E-4d;
    private LongSparseArray<Boolean> cacheRequests = new LongSparseArray();
    private GpsLocationListener gpsLocationListener = new GpsLocationListener();
    private Location lastKnownLocation;
    private boolean lastLocationByGoogleMaps;
    private long lastLocationSendTime;
    private long lastLocationStartTime;
    private LocationManager locationManager = ((LocationManager) ApplicationLoader.applicationContext.getSystemService(Param.LOCATION));
    private boolean locationSentSinceLastGoogleMapUpdate = true;
    public HashMap<Long, ArrayList<TLRPC$Message>> locationsCache = new HashMap();
    private GpsLocationListener networkLocationListener = new GpsLocationListener();
    private GpsLocationListener passiveLocationListener = new GpsLocationListener();
    private SparseIntArray requests = new SparseIntArray();
    private ArrayList<SharingLocationInfo> sharingLocations = new ArrayList();
    private HashMap<Long, SharingLocationInfo> sharingLocationsMap = new HashMap();
    private HashMap<Long, SharingLocationInfo> sharingLocationsMapUI = new HashMap();
    public ArrayList<SharingLocationInfo> sharingLocationsUI = new ArrayList();
    private boolean started;

    /* renamed from: org.telegram.messenger.LocationController$1 */
    class C13931 implements Runnable {
        C13931() {
        }

        public void run() {
            LocationController locationController = LocationController.getInstance();
            NotificationCenter.getInstance().addObserver(locationController, NotificationCenter.didReceivedNewMessages);
            NotificationCenter.getInstance().addObserver(locationController, NotificationCenter.messagesDeleted);
            NotificationCenter.getInstance().addObserver(locationController, NotificationCenter.replaceMessagesObjects);
        }
    }

    /* renamed from: org.telegram.messenger.LocationController$4 */
    class C13974 implements Runnable {
        C13974() {
        }

        public void run() {
            LocationController.this.requests.clear();
            LocationController.this.sharingLocationsMap.clear();
            LocationController.this.sharingLocations.clear();
            LocationController.this.lastKnownLocation = null;
            LocationController.this.stop(true);
        }
    }

    /* renamed from: org.telegram.messenger.LocationController$6 */
    class C14026 implements Runnable {
        C14026() {
        }

        public void run() {
            final ArrayList<SharingLocationInfo> result = new ArrayList();
            final ArrayList<User> users = new ArrayList();
            final ArrayList<TLRPC$Chat> chats = new ArrayList();
            try {
                ArrayList<Integer> usersToLoad = new ArrayList();
                ArrayList<Integer> chatsToLoad = new ArrayList();
                SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized("SELECT uid, mid, date, period, message FROM sharing_locations WHERE 1", new Object[0]);
                while (cursor.next()) {
                    SharingLocationInfo info = new SharingLocationInfo();
                    info.did = cursor.longValue(0);
                    info.mid = cursor.intValue(1);
                    info.stopTime = cursor.intValue(2);
                    info.period = cursor.intValue(3);
                    NativeByteBuffer data = cursor.byteBufferValue(4);
                    if (data != null) {
                        info.messageObject = new MessageObject(TLRPC$Message.TLdeserialize(data, data.readInt32(false), false), null, false);
                        MessagesStorage.addUsersAndChatsFromMessage(info.messageObject.messageOwner, usersToLoad, chatsToLoad);
                        data.reuse();
                    }
                    result.add(info);
                    int lower_id = (int) info.did;
                    int high_id = (int) (info.did >> 32);
                    if (lower_id != 0) {
                        if (lower_id < 0) {
                            if (!chatsToLoad.contains(Integer.valueOf(-lower_id))) {
                                chatsToLoad.add(Integer.valueOf(-lower_id));
                            }
                        } else if (!usersToLoad.contains(Integer.valueOf(lower_id))) {
                            usersToLoad.add(Integer.valueOf(lower_id));
                        }
                    }
                }
                cursor.dispose();
                if (!chatsToLoad.isEmpty()) {
                    MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", chatsToLoad), chats);
                }
                if (!usersToLoad.isEmpty()) {
                    MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", usersToLoad), users);
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (!result.isEmpty()) {
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.messenger.LocationController$6$1$1 */
                    class C14001 implements Runnable {

                        /* renamed from: org.telegram.messenger.LocationController$6$1$1$1 */
                        class C13991 implements Runnable {
                            C13991() {
                            }

                            public void run() {
                                LocationController.this.sharingLocationsUI.addAll(result);
                                for (int a = 0; a < result.size(); a++) {
                                    SharingLocationInfo info = (SharingLocationInfo) result.get(a);
                                    LocationController.this.sharingLocationsMapUI.put(Long.valueOf(info.did), info);
                                }
                                LocationController.this.startService();
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsChanged, new Object[0]);
                            }
                        }

                        C14001() {
                        }

                        public void run() {
                            LocationController.this.sharingLocations.addAll(result);
                            for (int a = 0; a < LocationController.this.sharingLocations.size(); a++) {
                                SharingLocationInfo info = (SharingLocationInfo) LocationController.this.sharingLocations.get(a);
                                LocationController.this.sharingLocationsMap.put(Long.valueOf(info.did), info);
                            }
                            AndroidUtilities.runOnUIThread(new C13991());
                        }
                    }

                    public void run() {
                        MessagesController.getInstance().putUsers(users, true);
                        MessagesController.getInstance().putChats(chats, true);
                        Utilities.stageQueue.postRunnable(new C14001());
                    }
                });
            }
        }
    }

    /* renamed from: org.telegram.messenger.LocationController$9 */
    class C14099 implements Runnable {

        /* renamed from: org.telegram.messenger.LocationController$9$1 */
        class C14071 implements RequestDelegate {
            C14071() {
            }

            public void run(TLObject response, TLRPC$TL_error error) {
                if (error == null) {
                    MessagesController.getInstance().processUpdates((TLRPC$Updates) response, false);
                }
            }
        }

        /* renamed from: org.telegram.messenger.LocationController$9$2 */
        class C14082 implements Runnable {
            C14082() {
            }

            public void run() {
                LocationController.this.sharingLocationsUI.clear();
                LocationController.this.sharingLocationsMapUI.clear();
                LocationController.this.stopService();
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsChanged, new Object[0]);
            }
        }

        C14099() {
        }

        public void run() {
            for (int a = 0; a < LocationController.this.sharingLocations.size(); a++) {
                SharingLocationInfo info = (SharingLocationInfo) LocationController.this.sharingLocations.get(a);
                TLRPC$TL_messages_editMessage req = new TLRPC$TL_messages_editMessage();
                req.peer = MessagesController.getInputPeer((int) info.did);
                req.id = info.mid;
                req.stop_geo_live = true;
                ConnectionsManager.getInstance().sendRequest(req, new C14071());
            }
            LocationController.this.sharingLocations.clear();
            LocationController.this.sharingLocationsMap.clear();
            LocationController.this.saveSharingLocation(null, 2);
            LocationController.this.stop(true);
            AndroidUtilities.runOnUIThread(new C14082());
        }
    }

    private class GpsLocationListener implements LocationListener {
        private GpsLocationListener() {
        }

        public void onLocationChanged(Location location) {
            if (location != null) {
                if (LocationController.this.lastKnownLocation == null || !(this == LocationController.this.networkLocationListener || this == LocationController.this.passiveLocationListener)) {
                    LocationController.this.lastKnownLocation = location;
                } else if (!LocationController.this.started && location.distanceTo(LocationController.this.lastKnownLocation) > 20.0f) {
                    LocationController.this.lastKnownLocation = location;
                    LocationController.this.lastLocationSendTime = (System.currentTimeMillis() - 90000) + DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
                }
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    }

    public static class SharingLocationInfo {
        public long did;
        public MessageObject messageObject;
        public int mid;
        public int period;
        public int stopTime;
    }

    public static LocationController getInstance() {
        LocationController localInstance = Instance;
        if (localInstance == null) {
            synchronized (LocationController.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        LocationController localInstance2 = new LocationController();
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

    public LocationController() {
        AndroidUtilities.runOnUIThread(new C13931());
        loadSharingLocations();
    }

    public void didReceivedNotification(int id, Object... args) {
        long did;
        ArrayList<TLRPC$Message> messages;
        int a;
        MessageObject messageObject;
        int b;
        if (id == NotificationCenter.didReceivedNewMessages) {
            did = ((Long) args[0]).longValue();
            if (isSharingLocation(did)) {
                messages = (ArrayList) this.locationsCache.get(Long.valueOf(did));
                if (messages != null) {
                    ArrayList<MessageObject> arr = args[1];
                    boolean added = false;
                    for (a = 0; a < arr.size(); a++) {
                        messageObject = (MessageObject) arr.get(a);
                        if (messageObject.isLiveLocation()) {
                            added = true;
                            boolean replaced = false;
                            for (b = 0; b < messages.size(); b++) {
                                if (((TLRPC$Message) messages.get(b)).from_id == messageObject.messageOwner.from_id) {
                                    replaced = true;
                                    messages.set(b, messageObject.messageOwner);
                                    break;
                                }
                            }
                            if (!replaced) {
                                messages.add(messageObject.messageOwner);
                            }
                        }
                    }
                    if (added) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsCacheChanged, new Object[]{Long.valueOf(did)});
                    }
                }
            }
        } else if (id == NotificationCenter.messagesDeleted) {
            if (!this.sharingLocationsUI.isEmpty()) {
                ArrayList<Integer> markAsDeletedMessages = args[0];
                int channelId = ((Integer) args[1]).intValue();
                ArrayList<Long> toRemove = null;
                for (a = 0; a < this.sharingLocationsUI.size(); a++) {
                    SharingLocationInfo info = (SharingLocationInfo) this.sharingLocationsUI.get(a);
                    if (channelId == (info.messageObject != null ? info.messageObject.getChannelId() : 0) && markAsDeletedMessages.contains(Integer.valueOf(info.mid))) {
                        if (toRemove == null) {
                            toRemove = new ArrayList();
                        }
                        toRemove.add(Long.valueOf(info.did));
                    }
                }
                if (toRemove != null) {
                    for (a = 0; a < toRemove.size(); a++) {
                        removeSharingLocation(((Long) toRemove.get(a)).longValue());
                    }
                }
            }
        } else if (id == NotificationCenter.replaceMessagesObjects) {
            did = ((Long) args[0]).longValue();
            if (isSharingLocation(did)) {
                messages = (ArrayList) this.locationsCache.get(Long.valueOf(did));
                if (messages != null) {
                    boolean updated = false;
                    ArrayList<MessageObject> messageObjects = args[1];
                    for (a = 0; a < messageObjects.size(); a++) {
                        messageObject = (MessageObject) messageObjects.get(a);
                        b = 0;
                        while (b < messages.size()) {
                            if (((TLRPC$Message) messages.get(b)).from_id == messageObject.messageOwner.from_id) {
                                if (messageObject.isLiveLocation()) {
                                    messages.set(b, messageObject.messageOwner);
                                } else {
                                    messages.remove(b);
                                }
                                updated = true;
                            } else {
                                b++;
                            }
                        }
                    }
                    if (updated) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsCacheChanged, new Object[]{Long.valueOf(did)});
                    }
                }
            }
        }
    }

    private void broadcastLastKnownLocation() {
        if (this.lastKnownLocation != null) {
            int a;
            if (this.requests.size() != 0) {
                for (a = 0; a < this.requests.size(); a++) {
                    ConnectionsManager.getInstance().cancelRequest(this.requests.keyAt(a), false);
                }
                this.requests.clear();
            }
            int date = ConnectionsManager.getInstance().getCurrentTime();
            for (a = 0; a < this.sharingLocations.size(); a++) {
                final SharingLocationInfo info = (SharingLocationInfo) this.sharingLocations.get(a);
                if (!(info.messageObject.messageOwner.media == null || info.messageObject.messageOwner.media.geo == null)) {
                    int messageDate = info.messageObject.messageOwner.edit_date != 0 ? info.messageObject.messageOwner.edit_date : info.messageObject.messageOwner.date;
                    TLRPC$GeoPoint point = info.messageObject.messageOwner.media.geo;
                    if (Math.abs(date - messageDate) < 30 && Math.abs(point.lat - this.lastKnownLocation.getLatitude()) <= eps && Math.abs(point._long - this.lastKnownLocation.getLongitude()) <= eps) {
                    }
                }
                TLRPC$TL_messages_editMessage req = new TLRPC$TL_messages_editMessage();
                req.peer = MessagesController.getInputPeer((int) info.did);
                req.id = info.mid;
                req.stop_geo_live = false;
                req.flags |= 8192;
                req.geo_point = new TLRPC$TL_inputGeoPoint();
                req.geo_point.lat = this.lastKnownLocation.getLatitude();
                req.geo_point._long = this.lastKnownLocation.getLongitude();
                final int[] reqId = new int[]{ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {

                    /* renamed from: org.telegram.messenger.LocationController$2$1 */
                    class C13941 implements Runnable {
                        C13941() {
                        }

                        public void run() {
                            LocationController.this.sharingLocationsUI.remove(info);
                            LocationController.this.sharingLocationsMapUI.remove(Long.valueOf(info.did));
                            if (LocationController.this.sharingLocationsUI.isEmpty()) {
                                LocationController.this.stopService();
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsChanged, new Object[0]);
                        }
                    }

                    public void run(TLObject response, TLRPC$TL_error error) {
                        if (error == null) {
                            TLRPC$Updates updates = (TLRPC$Updates) response;
                            boolean updated = false;
                            for (int a = 0; a < updates.updates.size(); a++) {
                                TLRPC$Update update = (TLRPC$Update) updates.updates.get(a);
                                if (update instanceof TLRPC$TL_updateEditMessage) {
                                    updated = true;
                                    info.messageObject.messageOwner = ((TLRPC$TL_updateEditMessage) update).message;
                                } else if (update instanceof TLRPC$TL_updateEditChannelMessage) {
                                    updated = true;
                                    info.messageObject.messageOwner = ((TLRPC$TL_updateEditChannelMessage) update).message;
                                }
                            }
                            if (updated) {
                                LocationController.this.saveSharingLocation(info, 0);
                            }
                            MessagesController.getInstance().processUpdates(updates, false);
                        } else if (error.text.equals("MESSAGE_ID_INVALID")) {
                            LocationController.this.sharingLocations.remove(info);
                            LocationController.this.sharingLocationsMap.remove(Long.valueOf(info.did));
                            LocationController.this.saveSharingLocation(info, 1);
                            LocationController.this.requests.delete(reqId[0]);
                            AndroidUtilities.runOnUIThread(new C13941());
                        }
                    }
                })};
                this.requests.put(reqId[0], 0);
            }
            ConnectionsManager.getInstance().resumeNetworkMaybe();
            stop(false);
        }
    }

    protected void update() {
        if (!this.sharingLocations.isEmpty()) {
            int a = 0;
            while (a < this.sharingLocations.size()) {
                final SharingLocationInfo info = (SharingLocationInfo) this.sharingLocations.get(a);
                if (info.stopTime <= ConnectionsManager.getInstance().getCurrentTime()) {
                    this.sharingLocations.remove(a);
                    this.sharingLocationsMap.remove(Long.valueOf(info.did));
                    saveSharingLocation(info, 1);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            LocationController.this.sharingLocationsUI.remove(info);
                            LocationController.this.sharingLocationsMapUI.remove(Long.valueOf(info.did));
                            if (LocationController.this.sharingLocationsUI.isEmpty()) {
                                LocationController.this.stopService();
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsChanged, new Object[0]);
                        }
                    });
                    a--;
                }
                a++;
            }
            if (this.started) {
                if (this.lastLocationByGoogleMaps || Math.abs(this.lastLocationStartTime - System.currentTimeMillis()) > 10000) {
                    this.lastLocationByGoogleMaps = false;
                    this.locationSentSinceLastGoogleMapUpdate = true;
                    this.lastLocationSendTime = System.currentTimeMillis();
                    broadcastLastKnownLocation();
                }
            } else if (Math.abs(this.lastLocationSendTime - System.currentTimeMillis()) > 90000) {
                this.lastLocationStartTime = System.currentTimeMillis();
                start();
            }
        }
    }

    public void cleanup() {
        this.sharingLocationsUI.clear();
        this.sharingLocationsMapUI.clear();
        this.locationsCache.clear();
        this.cacheRequests.clear();
        stopService();
        Utilities.stageQueue.postRunnable(new C13974());
    }

    protected void addSharingLocation(long did, int mid, int period, TLRPC$Message message) {
        final SharingLocationInfo info = new SharingLocationInfo();
        info.did = did;
        info.mid = mid;
        info.period = period;
        info.messageObject = new MessageObject(message, null, null, false);
        info.stopTime = ConnectionsManager.getInstance().getCurrentTime() + period;
        final SharingLocationInfo old = (SharingLocationInfo) this.sharingLocationsMap.put(Long.valueOf(did), info);
        if (old != null) {
            this.sharingLocations.remove(old);
        }
        this.sharingLocations.add(info);
        saveSharingLocation(info, 0);
        this.lastLocationSendTime = (System.currentTimeMillis() - 90000) + DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (old != null) {
                    LocationController.this.sharingLocationsUI.remove(old);
                }
                LocationController.this.sharingLocationsUI.add(info);
                LocationController.this.sharingLocationsMapUI.put(Long.valueOf(info.did), info);
                LocationController.this.startService();
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsChanged, new Object[0]);
            }
        });
    }

    public boolean isSharingLocation(long did) {
        return this.sharingLocationsMapUI.containsKey(Long.valueOf(did));
    }

    public SharingLocationInfo getSharingLocationInfo(long did) {
        return (SharingLocationInfo) this.sharingLocationsMapUI.get(Long.valueOf(did));
    }

    private void loadSharingLocations() {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C14026());
    }

    private void saveSharingLocation(final SharingLocationInfo info, final int remove) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    if (remove == 2) {
                        MessagesStorage.getInstance().getDatabase().executeFast("DELETE FROM sharing_locations WHERE 1").stepThis().dispose();
                    } else if (remove == 1) {
                        if (info != null) {
                            MessagesStorage.getInstance().getDatabase().executeFast("DELETE FROM sharing_locations WHERE uid = " + info.did).stepThis().dispose();
                        }
                    } else if (info != null) {
                        SQLitePreparedStatement state = MessagesStorage.getInstance().getDatabase().executeFast("REPLACE INTO sharing_locations VALUES(?, ?, ?, ?, ?)");
                        state.requery();
                        NativeByteBuffer data = new NativeByteBuffer(info.messageObject.messageOwner.getObjectSize());
                        info.messageObject.messageOwner.serializeToStream(data);
                        state.bindLong(1, info.did);
                        state.bindInteger(2, info.mid);
                        state.bindInteger(3, info.stopTime);
                        state.bindInteger(4, info.period);
                        state.bindByteBuffer(5, data);
                        state.step();
                        state.dispose();
                        data.reuse();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void removeSharingLocation(final long did) {
        Utilities.stageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.LocationController$8$1 */
            class C14041 implements RequestDelegate {
                C14041() {
                }

                public void run(TLObject response, TLRPC$TL_error error) {
                    if (error == null) {
                        MessagesController.getInstance().processUpdates((TLRPC$Updates) response, false);
                    }
                }
            }

            public void run() {
                final SharingLocationInfo info = (SharingLocationInfo) LocationController.this.sharingLocationsMap.remove(Long.valueOf(did));
                if (info != null) {
                    TLRPC$TL_messages_editMessage req = new TLRPC$TL_messages_editMessage();
                    req.peer = MessagesController.getInputPeer((int) info.did);
                    req.id = info.mid;
                    req.stop_geo_live = true;
                    ConnectionsManager.getInstance().sendRequest(req, new C14041());
                    LocationController.this.sharingLocations.remove(info);
                    LocationController.this.saveSharingLocation(info, 1);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            LocationController.this.sharingLocationsUI.remove(info);
                            LocationController.this.sharingLocationsMapUI.remove(Long.valueOf(info.did));
                            if (LocationController.this.sharingLocationsUI.isEmpty()) {
                                LocationController.this.stopService();
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsChanged, new Object[0]);
                        }
                    });
                    if (LocationController.this.sharingLocations.isEmpty()) {
                        LocationController.this.stop(true);
                    }
                }
            }
        });
    }

    private void startService() {
        ApplicationLoader.applicationContext.startService(new Intent(ApplicationLoader.applicationContext, LocationSharingService.class));
    }

    private void stopService() {
        ApplicationLoader.applicationContext.stopService(new Intent(ApplicationLoader.applicationContext, LocationSharingService.class));
    }

    public void removeAllLocationSharings() {
        Utilities.stageQueue.postRunnable(new C14099());
    }

    public void setGoogleMapLocation(Location location, boolean first) {
        if (location != null) {
            this.lastLocationByGoogleMaps = true;
            if (first || (this.lastKnownLocation != null && this.lastKnownLocation.distanceTo(location) >= 20.0f)) {
                this.lastLocationSendTime = System.currentTimeMillis() - 90000;
                this.locationSentSinceLastGoogleMapUpdate = false;
            } else if (this.locationSentSinceLastGoogleMapUpdate) {
                this.lastLocationSendTime = (System.currentTimeMillis() - 90000) + 20000;
                this.locationSentSinceLastGoogleMapUpdate = false;
            }
            this.lastKnownLocation = location;
        }
    }

    private void start() {
        if (!this.started) {
            this.lastLocationStartTime = System.currentTimeMillis();
            this.started = true;
            try {
                this.locationManager.requestLocationUpdates("gps", 1, 0.0f, this.gpsLocationListener);
            } catch (Exception e) {
                FileLog.e(e);
            }
            try {
                this.locationManager.requestLocationUpdates("network", 1, 0.0f, this.networkLocationListener);
            } catch (Exception e2) {
                FileLog.e(e2);
            }
            try {
                this.locationManager.requestLocationUpdates("passive", 1, 0.0f, this.passiveLocationListener);
            } catch (Exception e22) {
                FileLog.e(e22);
            }
            if (this.lastKnownLocation == null) {
                try {
                    this.lastKnownLocation = this.locationManager.getLastKnownLocation("gps");
                    if (this.lastKnownLocation == null) {
                        this.lastKnownLocation = this.locationManager.getLastKnownLocation("network");
                    }
                } catch (Exception e222) {
                    FileLog.e(e222);
                }
            }
        }
    }

    private void stop(boolean empty) {
        this.started = false;
        this.locationManager.removeUpdates(this.gpsLocationListener);
        if (empty) {
            this.locationManager.removeUpdates(this.networkLocationListener);
            this.locationManager.removeUpdates(this.passiveLocationListener);
        }
    }

    public void loadLiveLocations(final long did) {
        if (this.cacheRequests.indexOfKey(did) < 0) {
            this.cacheRequests.put(did, Boolean.valueOf(true));
            TLRPC$TL_messages_getRecentLocations req = new TLRPC$TL_messages_getRecentLocations();
            req.peer = MessagesController.getInputPeer((int) did);
            req.limit = 100;
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, TLRPC$TL_error error) {
                    if (error == null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                LocationController.this.cacheRequests.delete(did);
                                TLRPC$messages_Messages res = response;
                                int a = 0;
                                while (a < res.messages.size()) {
                                    if (!(((TLRPC$Message) res.messages.get(a)).media instanceof TLRPC$TL_messageMediaGeoLive)) {
                                        res.messages.remove(a);
                                        a--;
                                    }
                                    a++;
                                }
                                MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, true, true);
                                MessagesController.getInstance().putUsers(res.users, false);
                                MessagesController.getInstance().putChats(res.chats, false);
                                LocationController.this.locationsCache.put(Long.valueOf(did), res.messages);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsCacheChanged, new Object[]{Long.valueOf(did)});
                            }
                        });
                    }
                }
            });
        }
    }
}
