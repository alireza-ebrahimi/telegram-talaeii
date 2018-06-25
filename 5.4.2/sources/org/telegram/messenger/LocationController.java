package org.telegram.messenger;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.LongSparseArray;
import android.util.SparseIntArray;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
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
import org.telegram.tgnet.TLRPC.GeoPoint;
import org.telegram.tgnet.TLRPC.Message;

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
    private LocationManager locationManager = ((LocationManager) ApplicationLoader.applicationContext.getSystemService(C1797b.LOCATION));
    private boolean locationSentSinceLastGoogleMapUpdate = true;
    public HashMap<Long, ArrayList<Message>> locationsCache = new HashMap();
    private GpsLocationListener networkLocationListener = new GpsLocationListener();
    private GpsLocationListener passiveLocationListener = new GpsLocationListener();
    private SparseIntArray requests = new SparseIntArray();
    private ArrayList<SharingLocationInfo> sharingLocations = new ArrayList();
    private HashMap<Long, SharingLocationInfo> sharingLocationsMap = new HashMap();
    private HashMap<Long, SharingLocationInfo> sharingLocationsMapUI = new HashMap();
    public ArrayList<SharingLocationInfo> sharingLocationsUI = new ArrayList();
    private boolean started;

    /* renamed from: org.telegram.messenger.LocationController$1 */
    class C31111 implements Runnable {
        C31111() {
        }

        public void run() {
            LocationController instance = LocationController.getInstance();
            NotificationCenter.getInstance().addObserver(instance, NotificationCenter.didReceivedNewMessages);
            NotificationCenter.getInstance().addObserver(instance, NotificationCenter.messagesDeleted);
            NotificationCenter.getInstance().addObserver(instance, NotificationCenter.replaceMessagesObjects);
        }
    }

    /* renamed from: org.telegram.messenger.LocationController$4 */
    class C31154 implements Runnable {
        C31154() {
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
    class C31206 implements Runnable {
        C31206() {
        }

        public void run() {
            final ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            final ArrayList arrayList3 = new ArrayList();
            try {
                Iterable arrayList4 = new ArrayList();
                Iterable arrayList5 = new ArrayList();
                SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b("SELECT uid, mid, date, period, message FROM sharing_locations WHERE 1", new Object[0]);
                while (b.m12152a()) {
                    SharingLocationInfo sharingLocationInfo = new SharingLocationInfo();
                    sharingLocationInfo.did = b.m12158d(0);
                    sharingLocationInfo.mid = b.m12154b(1);
                    sharingLocationInfo.stopTime = b.m12154b(2);
                    sharingLocationInfo.period = b.m12154b(3);
                    AbstractSerializedData g = b.m12161g(4);
                    if (g != null) {
                        sharingLocationInfo.messageObject = new MessageObject(Message.TLdeserialize(g, g.readInt32(false), false), null, false);
                        MessagesStorage.addUsersAndChatsFromMessage(sharingLocationInfo.messageObject.messageOwner, arrayList4, arrayList5);
                        g.reuse();
                    }
                    arrayList.add(sharingLocationInfo);
                    int i = (int) sharingLocationInfo.did;
                    int i2 = (int) (sharingLocationInfo.did >> 32);
                    if (i != 0) {
                        if (i < 0) {
                            if (!arrayList5.contains(Integer.valueOf(-i))) {
                                arrayList5.add(Integer.valueOf(-i));
                            }
                        } else if (!arrayList4.contains(Integer.valueOf(i))) {
                            arrayList4.add(Integer.valueOf(i));
                        }
                    }
                }
                b.m12155b();
                if (!arrayList5.isEmpty()) {
                    MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", arrayList5), arrayList3);
                }
                if (!arrayList4.isEmpty()) {
                    MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", arrayList4), arrayList2);
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
            if (!arrayList.isEmpty()) {
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.messenger.LocationController$6$1$1 */
                    class C31181 implements Runnable {

                        /* renamed from: org.telegram.messenger.LocationController$6$1$1$1 */
                        class C31171 implements Runnable {
                            C31171() {
                            }

                            public void run() {
                                LocationController.this.sharingLocationsUI.addAll(arrayList);
                                for (int i = 0; i < arrayList.size(); i++) {
                                    SharingLocationInfo sharingLocationInfo = (SharingLocationInfo) arrayList.get(i);
                                    LocationController.this.sharingLocationsMapUI.put(Long.valueOf(sharingLocationInfo.did), sharingLocationInfo);
                                }
                                LocationController.this.startService();
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsChanged, new Object[0]);
                            }
                        }

                        C31181() {
                        }

                        public void run() {
                            LocationController.this.sharingLocations.addAll(arrayList);
                            for (int i = 0; i < LocationController.this.sharingLocations.size(); i++) {
                                SharingLocationInfo sharingLocationInfo = (SharingLocationInfo) LocationController.this.sharingLocations.get(i);
                                LocationController.this.sharingLocationsMap.put(Long.valueOf(sharingLocationInfo.did), sharingLocationInfo);
                            }
                            AndroidUtilities.runOnUIThread(new C31171());
                        }
                    }

                    public void run() {
                        MessagesController.getInstance().putUsers(arrayList2, true);
                        MessagesController.getInstance().putChats(arrayList3, true);
                        Utilities.stageQueue.postRunnable(new C31181());
                    }
                });
            }
        }
    }

    /* renamed from: org.telegram.messenger.LocationController$9 */
    class C31279 implements Runnable {

        /* renamed from: org.telegram.messenger.LocationController$9$1 */
        class C31251 implements RequestDelegate {
            C31251() {
            }

            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error == null) {
                    MessagesController.getInstance().processUpdates((TLRPC$Updates) tLObject, false);
                }
            }
        }

        /* renamed from: org.telegram.messenger.LocationController$9$2 */
        class C31262 implements Runnable {
            C31262() {
            }

            public void run() {
                LocationController.this.sharingLocationsUI.clear();
                LocationController.this.sharingLocationsMapUI.clear();
                LocationController.this.stopService();
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsChanged, new Object[0]);
            }
        }

        C31279() {
        }

        public void run() {
            for (int i = 0; i < LocationController.this.sharingLocations.size(); i++) {
                SharingLocationInfo sharingLocationInfo = (SharingLocationInfo) LocationController.this.sharingLocations.get(i);
                TLObject tLRPC$TL_messages_editMessage = new TLRPC$TL_messages_editMessage();
                tLRPC$TL_messages_editMessage.peer = MessagesController.getInputPeer((int) sharingLocationInfo.did);
                tLRPC$TL_messages_editMessage.id = sharingLocationInfo.mid;
                tLRPC$TL_messages_editMessage.stop_geo_live = true;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_editMessage, new C31251());
            }
            LocationController.this.sharingLocations.clear();
            LocationController.this.sharingLocationsMap.clear();
            LocationController.this.saveSharingLocation(null, 2);
            LocationController.this.stop(true);
            AndroidUtilities.runOnUIThread(new C31262());
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

        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    public static class SharingLocationInfo {
        public long did;
        public MessageObject messageObject;
        public int mid;
        public int period;
        public int stopTime;
    }

    public LocationController() {
        AndroidUtilities.runOnUIThread(new C31111());
        loadSharingLocations();
    }

    private void broadcastLastKnownLocation() {
        if (this.lastKnownLocation != null) {
            if (this.requests.size() != 0) {
                for (int i = 0; i < this.requests.size(); i++) {
                    ConnectionsManager.getInstance().cancelRequest(this.requests.keyAt(i), false);
                }
                this.requests.clear();
            }
            int currentTime = ConnectionsManager.getInstance().getCurrentTime();
            for (int i2 = 0; i2 < this.sharingLocations.size(); i2++) {
                final SharingLocationInfo sharingLocationInfo = (SharingLocationInfo) this.sharingLocations.get(i2);
                if (!(sharingLocationInfo.messageObject.messageOwner.media == null || sharingLocationInfo.messageObject.messageOwner.media.geo == null)) {
                    int i3 = sharingLocationInfo.messageObject.messageOwner.edit_date != 0 ? sharingLocationInfo.messageObject.messageOwner.edit_date : sharingLocationInfo.messageObject.messageOwner.date;
                    GeoPoint geoPoint = sharingLocationInfo.messageObject.messageOwner.media.geo;
                    if (Math.abs(currentTime - i3) < 30 && Math.abs(geoPoint.lat - this.lastKnownLocation.getLatitude()) <= eps && Math.abs(geoPoint._long - this.lastKnownLocation.getLongitude()) <= eps) {
                    }
                }
                TLObject tLRPC$TL_messages_editMessage = new TLRPC$TL_messages_editMessage();
                tLRPC$TL_messages_editMessage.peer = MessagesController.getInputPeer((int) sharingLocationInfo.did);
                tLRPC$TL_messages_editMessage.id = sharingLocationInfo.mid;
                tLRPC$TL_messages_editMessage.stop_geo_live = false;
                tLRPC$TL_messages_editMessage.flags |= MessagesController.UPDATE_MASK_CHANNEL;
                tLRPC$TL_messages_editMessage.geo_point = new TLRPC$TL_inputGeoPoint();
                tLRPC$TL_messages_editMessage.geo_point.lat = this.lastKnownLocation.getLatitude();
                tLRPC$TL_messages_editMessage.geo_point._long = this.lastKnownLocation.getLongitude();
                final int[] iArr = new int[]{ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_editMessage, new RequestDelegate() {

                    /* renamed from: org.telegram.messenger.LocationController$2$1 */
                    class C31121 implements Runnable {
                        C31121() {
                        }

                        public void run() {
                            LocationController.this.sharingLocationsUI.remove(sharingLocationInfo);
                            LocationController.this.sharingLocationsMapUI.remove(Long.valueOf(sharingLocationInfo.did));
                            if (LocationController.this.sharingLocationsUI.isEmpty()) {
                                LocationController.this.stopService();
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsChanged, new Object[0]);
                        }
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                            boolean z = false;
                            for (int i = 0; i < tLRPC$Updates.updates.size(); i++) {
                                TLRPC$Update tLRPC$Update = (TLRPC$Update) tLRPC$Updates.updates.get(i);
                                if (tLRPC$Update instanceof TLRPC$TL_updateEditMessage) {
                                    sharingLocationInfo.messageObject.messageOwner = ((TLRPC$TL_updateEditMessage) tLRPC$Update).message;
                                    z = true;
                                } else if (tLRPC$Update instanceof TLRPC$TL_updateEditChannelMessage) {
                                    sharingLocationInfo.messageObject.messageOwner = ((TLRPC$TL_updateEditChannelMessage) tLRPC$Update).message;
                                    z = true;
                                }
                            }
                            if (z) {
                                LocationController.this.saveSharingLocation(sharingLocationInfo, 0);
                            }
                            MessagesController.getInstance().processUpdates(tLRPC$Updates, false);
                        } else if (tLRPC$TL_error.text.equals("MESSAGE_ID_INVALID")) {
                            LocationController.this.sharingLocations.remove(sharingLocationInfo);
                            LocationController.this.sharingLocationsMap.remove(Long.valueOf(sharingLocationInfo.did));
                            LocationController.this.saveSharingLocation(sharingLocationInfo, 1);
                            LocationController.this.requests.delete(iArr[0]);
                            AndroidUtilities.runOnUIThread(new C31121());
                        }
                    }
                })};
                this.requests.put(iArr[0], 0);
            }
            ConnectionsManager.getInstance().resumeNetworkMaybe();
            stop(false);
        }
    }

    public static LocationController getInstance() {
        LocationController locationController = Instance;
        if (locationController == null) {
            synchronized (LocationController.class) {
                locationController = Instance;
                if (locationController == null) {
                    locationController = new LocationController();
                    Instance = locationController;
                }
            }
        }
        return locationController;
    }

    private void loadSharingLocations() {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C31206());
    }

    private void saveSharingLocation(final SharingLocationInfo sharingLocationInfo, final int i) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    if (i == 2) {
                        MessagesStorage.getInstance().getDatabase().m12164a("DELETE FROM sharing_locations WHERE 1").m12179c().m12181e();
                    } else if (i == 1) {
                        if (sharingLocationInfo != null) {
                            MessagesStorage.getInstance().getDatabase().m12164a("DELETE FROM sharing_locations WHERE uid = " + sharingLocationInfo.did).m12179c().m12181e();
                        }
                    } else if (sharingLocationInfo != null) {
                        SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("REPLACE INTO sharing_locations VALUES(?, ?, ?, ?, ?)");
                        a.m12180d();
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(sharingLocationInfo.messageObject.messageOwner.getObjectSize());
                        sharingLocationInfo.messageObject.messageOwner.serializeToStream(nativeByteBuffer);
                        a.m12175a(1, sharingLocationInfo.did);
                        a.m12174a(2, sharingLocationInfo.mid);
                        a.m12174a(3, sharingLocationInfo.stopTime);
                        a.m12174a(4, sharingLocationInfo.period);
                        a.m12177a(5, nativeByteBuffer);
                        a.m12178b();
                        a.m12181e();
                        nativeByteBuffer.reuse();
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    private void start() {
        if (!this.started) {
            this.lastLocationStartTime = System.currentTimeMillis();
            this.started = true;
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
                this.locationManager.requestLocationUpdates("passive", 1, BitmapDescriptorFactory.HUE_RED, this.passiveLocationListener);
            } catch (Throwable e22) {
                FileLog.m13728e(e22);
            }
            if (this.lastKnownLocation == null) {
                try {
                    this.lastKnownLocation = this.locationManager.getLastKnownLocation("gps");
                    if (this.lastKnownLocation == null) {
                        this.lastKnownLocation = this.locationManager.getLastKnownLocation("network");
                    }
                } catch (Throwable e222) {
                    FileLog.m13728e(e222);
                }
            }
        }
    }

    private void startService() {
        ApplicationLoader.applicationContext.startService(new Intent(ApplicationLoader.applicationContext, LocationSharingService.class));
    }

    private void stop(boolean z) {
        this.started = false;
        this.locationManager.removeUpdates(this.gpsLocationListener);
        if (z) {
            this.locationManager.removeUpdates(this.networkLocationListener);
            this.locationManager.removeUpdates(this.passiveLocationListener);
        }
    }

    private void stopService() {
        ApplicationLoader.applicationContext.stopService(new Intent(ApplicationLoader.applicationContext, LocationSharingService.class));
    }

    protected void addSharingLocation(long j, int i, int i2, Message message) {
        final SharingLocationInfo sharingLocationInfo = new SharingLocationInfo();
        sharingLocationInfo.did = j;
        sharingLocationInfo.mid = i;
        sharingLocationInfo.period = i2;
        sharingLocationInfo.messageObject = new MessageObject(message, null, null, false);
        sharingLocationInfo.stopTime = ConnectionsManager.getInstance().getCurrentTime() + i2;
        final SharingLocationInfo sharingLocationInfo2 = (SharingLocationInfo) this.sharingLocationsMap.put(Long.valueOf(j), sharingLocationInfo);
        if (sharingLocationInfo2 != null) {
            this.sharingLocations.remove(sharingLocationInfo2);
        }
        this.sharingLocations.add(sharingLocationInfo);
        saveSharingLocation(sharingLocationInfo, 0);
        this.lastLocationSendTime = (System.currentTimeMillis() - 90000) + DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (sharingLocationInfo2 != null) {
                    LocationController.this.sharingLocationsUI.remove(sharingLocationInfo2);
                }
                LocationController.this.sharingLocationsUI.add(sharingLocationInfo);
                LocationController.this.sharingLocationsMapUI.put(Long.valueOf(sharingLocationInfo.did), sharingLocationInfo);
                LocationController.this.startService();
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsChanged, new Object[0]);
            }
        });
    }

    public void cleanup() {
        this.sharingLocationsUI.clear();
        this.sharingLocationsMapUI.clear();
        this.locationsCache.clear();
        this.cacheRequests.clear();
        stopService();
        Utilities.stageQueue.postRunnable(new C31154());
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int i2 = 0;
        ArrayList arrayList;
        ArrayList arrayList2;
        int i3;
        int i4;
        MessageObject messageObject;
        int i5;
        if (i == NotificationCenter.didReceivedNewMessages) {
            long longValue = ((Long) objArr[0]).longValue();
            if (isSharingLocation(longValue)) {
                arrayList = (ArrayList) this.locationsCache.get(Long.valueOf(longValue));
                if (arrayList != null) {
                    arrayList2 = (ArrayList) objArr[1];
                    i3 = 0;
                    for (i4 = 0; i4 < arrayList2.size(); i4++) {
                        messageObject = (MessageObject) arrayList2.get(i4);
                        if (messageObject.isLiveLocation()) {
                            for (i5 = 0; i5 < arrayList.size(); i5++) {
                                if (((Message) arrayList.get(i5)).from_id == messageObject.messageOwner.from_id) {
                                    arrayList.set(i5, messageObject.messageOwner);
                                    i3 = 1;
                                    break;
                                }
                            }
                            i3 = 0;
                            if (i3 == 0) {
                                arrayList.add(messageObject.messageOwner);
                            }
                            i3 = 1;
                        }
                    }
                    if (i3 != 0) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsCacheChanged, Long.valueOf(longValue));
                    }
                }
            }
        } else if (i == NotificationCenter.messagesDeleted) {
            if (!this.sharingLocationsUI.isEmpty()) {
                arrayList = (ArrayList) objArr[0];
                i5 = ((Integer) objArr[1]).intValue();
                ArrayList arrayList3 = null;
                for (i3 = 0; i3 < this.sharingLocationsUI.size(); i3++) {
                    SharingLocationInfo sharingLocationInfo = (SharingLocationInfo) this.sharingLocationsUI.get(i3);
                    if (i5 == (sharingLocationInfo.messageObject != null ? sharingLocationInfo.messageObject.getChannelId() : 0) && arrayList.contains(Integer.valueOf(sharingLocationInfo.mid))) {
                        if (arrayList3 == null) {
                            arrayList3 = new ArrayList();
                        }
                        arrayList3.add(Long.valueOf(sharingLocationInfo.did));
                    }
                }
                if (arrayList3 != null) {
                    while (i2 < arrayList3.size()) {
                        removeSharingLocation(((Long) arrayList3.get(i2)).longValue());
                        i2++;
                    }
                }
            }
        } else if (i == NotificationCenter.replaceMessagesObjects) {
            long longValue2 = ((Long) objArr[0]).longValue();
            if (isSharingLocation(longValue2)) {
                arrayList = (ArrayList) this.locationsCache.get(Long.valueOf(longValue2));
                if (arrayList != null) {
                    arrayList2 = (ArrayList) objArr[1];
                    i4 = 0;
                    i5 = 0;
                    while (i4 < arrayList2.size()) {
                        messageObject = (MessageObject) arrayList2.get(i4);
                        int i6 = 0;
                        while (i6 < arrayList.size()) {
                            if (((Message) arrayList.get(i6)).from_id == messageObject.messageOwner.from_id) {
                                if (messageObject.isLiveLocation()) {
                                    arrayList.set(i6, messageObject.messageOwner);
                                } else {
                                    arrayList.remove(i6);
                                }
                                i3 = 1;
                                i4++;
                                i5 = i3;
                            } else {
                                i6++;
                            }
                        }
                        i3 = i5;
                        i4++;
                        i5 = i3;
                    }
                    if (i5 != 0) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsCacheChanged, Long.valueOf(longValue2));
                    }
                }
            }
        }
    }

    public SharingLocationInfo getSharingLocationInfo(long j) {
        return (SharingLocationInfo) this.sharingLocationsMapUI.get(Long.valueOf(j));
    }

    public boolean isSharingLocation(long j) {
        return this.sharingLocationsMapUI.containsKey(Long.valueOf(j));
    }

    public void loadLiveLocations(final long j) {
        if (this.cacheRequests.indexOfKey(j) < 0) {
            this.cacheRequests.put(j, Boolean.valueOf(true));
            TLObject tLRPC$TL_messages_getRecentLocations = new TLRPC$TL_messages_getRecentLocations();
            tLRPC$TL_messages_getRecentLocations.peer = MessagesController.getInputPeer((int) j);
            tLRPC$TL_messages_getRecentLocations.limit = 100;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getRecentLocations, new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                LocationController.this.cacheRequests.delete(j);
                                TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                                int i = 0;
                                while (i < tLRPC$messages_Messages.messages.size()) {
                                    if (!(((Message) tLRPC$messages_Messages.messages.get(i)).media instanceof TLRPC$TL_messageMediaGeoLive)) {
                                        tLRPC$messages_Messages.messages.remove(i);
                                        i--;
                                    }
                                    i++;
                                }
                                MessagesStorage.getInstance().putUsersAndChats(tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, true, true);
                                MessagesController.getInstance().putUsers(tLRPC$messages_Messages.users, false);
                                MessagesController.getInstance().putChats(tLRPC$messages_Messages.chats, false);
                                LocationController.this.locationsCache.put(Long.valueOf(j), tLRPC$messages_Messages.messages);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsCacheChanged, Long.valueOf(j));
                            }
                        });
                    }
                }
            });
        }
    }

    public void removeAllLocationSharings() {
        Utilities.stageQueue.postRunnable(new C31279());
    }

    public void removeSharingLocation(final long j) {
        Utilities.stageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.LocationController$8$1 */
            class C31221 implements RequestDelegate {
                C31221() {
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        MessagesController.getInstance().processUpdates((TLRPC$Updates) tLObject, false);
                    }
                }
            }

            public void run() {
                final SharingLocationInfo sharingLocationInfo = (SharingLocationInfo) LocationController.this.sharingLocationsMap.remove(Long.valueOf(j));
                if (sharingLocationInfo != null) {
                    TLObject tLRPC$TL_messages_editMessage = new TLRPC$TL_messages_editMessage();
                    tLRPC$TL_messages_editMessage.peer = MessagesController.getInputPeer((int) sharingLocationInfo.did);
                    tLRPC$TL_messages_editMessage.id = sharingLocationInfo.mid;
                    tLRPC$TL_messages_editMessage.stop_geo_live = true;
                    ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_editMessage, new C31221());
                    LocationController.this.sharingLocations.remove(sharingLocationInfo);
                    LocationController.this.saveSharingLocation(sharingLocationInfo, 1);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            LocationController.this.sharingLocationsUI.remove(sharingLocationInfo);
                            LocationController.this.sharingLocationsMapUI.remove(Long.valueOf(sharingLocationInfo.did));
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

    public void setGoogleMapLocation(Location location, boolean z) {
        if (location != null) {
            this.lastLocationByGoogleMaps = true;
            if (z || (this.lastKnownLocation != null && this.lastKnownLocation.distanceTo(location) >= 20.0f)) {
                this.lastLocationSendTime = System.currentTimeMillis() - 90000;
                this.locationSentSinceLastGoogleMapUpdate = false;
            } else if (this.locationSentSinceLastGoogleMapUpdate) {
                this.lastLocationSendTime = (System.currentTimeMillis() - 90000) + 20000;
                this.locationSentSinceLastGoogleMapUpdate = false;
            }
            this.lastKnownLocation = location;
        }
    }

    protected void update() {
        if (!this.sharingLocations.isEmpty()) {
            int i = 0;
            while (i < this.sharingLocations.size()) {
                final SharingLocationInfo sharingLocationInfo = (SharingLocationInfo) this.sharingLocations.get(i);
                if (sharingLocationInfo.stopTime <= ConnectionsManager.getInstance().getCurrentTime()) {
                    this.sharingLocations.remove(i);
                    this.sharingLocationsMap.remove(Long.valueOf(sharingLocationInfo.did));
                    saveSharingLocation(sharingLocationInfo, 1);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            LocationController.this.sharingLocationsUI.remove(sharingLocationInfo);
                            LocationController.this.sharingLocationsMapUI.remove(Long.valueOf(sharingLocationInfo.did));
                            if (LocationController.this.sharingLocationsUI.isEmpty()) {
                                LocationController.this.stopService();
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsChanged, new Object[0]);
                        }
                    });
                    i--;
                }
                i++;
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
}
