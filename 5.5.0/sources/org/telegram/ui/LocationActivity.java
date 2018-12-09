package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocationController;
import org.telegram.messenger.LocationController.SharingLocationInfo;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.MessagesStorage.IntCallback;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_geoPoint;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeo;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive;
import org.telegram.tgnet.TLRPC$TL_messageMediaVenue;
import org.telegram.tgnet.TLRPC$TL_messages_getRecentLocations;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Adapters.BaseLocationAdapter.BaseLocationAdapterDelegate;
import org.telegram.ui.Adapters.LocationActivityAdapter;
import org.telegram.ui.Adapters.LocationActivitySearchAdapter;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.LocationCell;
import org.telegram.ui.Cells.LocationLoadingCell;
import org.telegram.ui.Cells.LocationPoweredCell;
import org.telegram.ui.Cells.SendLocationCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.MapPlaceholderDrawable;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;

public class LocationActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int map_list_menu_hybrid = 4;
    private static final int map_list_menu_map = 2;
    private static final int map_list_menu_satellite = 3;
    private static final int share = 1;
    private LocationActivityAdapter adapter;
    private AnimatorSet animatorSet;
    private AvatarDrawable avatarDrawable;
    private boolean checkGpsEnabled = true;
    private boolean checkPermission = true;
    private CircleOptions circleOptions;
    private LocationActivityDelegate delegate;
    private long dialogId;
    private EmptyTextProgressView emptyView;
    private boolean firstFocus = true;
    private boolean firstWas = false;
    private GoogleMap googleMap;
    private boolean isFirstLocation = true;
    private LinearLayoutManager layoutManager;
    private RecyclerListView listView;
    private int liveLocationType;
    private ImageView locationButton;
    private MapView mapView;
    private FrameLayout mapViewClip;
    private boolean mapsInitialized;
    private ImageView markerImageView;
    private int markerTop;
    private ImageView markerXImageView;
    private ArrayList<LiveLocation> markers = new ArrayList();
    private HashMap<Integer, LiveLocation> markersMap = new HashMap();
    private MessageObject messageObject;
    private Location myLocation;
    private boolean onResumeCalled;
    private ActionBarMenuItem otherItem;
    private int overScrollHeight = ((AndroidUtilities.displaySize.x - ActionBar.getCurrentActionBarHeight()) - AndroidUtilities.dp(66.0f));
    private ImageView routeButton;
    private LocationActivitySearchAdapter searchAdapter;
    private RecyclerListView searchListView;
    private boolean searchWas;
    private boolean searching;
    private Runnable updateRunnable;
    private Location userLocation;
    private boolean userLocationMoved = false;
    private boolean wasResults;

    public interface LocationActivityDelegate {
        void didSelectLocation(MessageMedia messageMedia, int i);
    }

    /* renamed from: org.telegram.ui.LocationActivity$1 */
    class C48591 extends ActionBarMenuOnItemClick {
        C48591() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                LocationActivity.this.finishFragment();
            } else if (i == 2) {
                if (LocationActivity.this.googleMap != null) {
                    LocationActivity.this.googleMap.setMapType(1);
                }
            } else if (i == 3) {
                if (LocationActivity.this.googleMap != null) {
                    LocationActivity.this.googleMap.setMapType(2);
                }
            } else if (i == 4) {
                if (LocationActivity.this.googleMap != null) {
                    LocationActivity.this.googleMap.setMapType(4);
                }
            } else if (i == 1) {
                try {
                    double d = LocationActivity.this.messageObject.messageOwner.media.geo.lat;
                    double d2 = LocationActivity.this.messageObject.messageOwner.media.geo._long;
                    LocationActivity.this.getParentActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("geo:" + d + "," + d2 + "?q=" + d + "," + d2)));
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.LocationActivity$2 */
    class C48612 extends ActionBarMenuItemSearchListener {
        C48612() {
        }

        public void onSearchCollapse() {
            LocationActivity.this.searching = false;
            LocationActivity.this.searchWas = false;
            LocationActivity.this.otherItem.setVisibility(0);
            LocationActivity.this.searchListView.setEmptyView(null);
            LocationActivity.this.listView.setVisibility(0);
            LocationActivity.this.mapViewClip.setVisibility(0);
            LocationActivity.this.searchListView.setVisibility(8);
            LocationActivity.this.emptyView.setVisibility(8);
            LocationActivity.this.searchAdapter.searchDelayed(null, null);
        }

        public void onSearchExpand() {
            LocationActivity.this.searching = true;
            LocationActivity.this.otherItem.setVisibility(8);
            LocationActivity.this.listView.setVisibility(8);
            LocationActivity.this.mapViewClip.setVisibility(8);
            LocationActivity.this.searchListView.setVisibility(0);
            LocationActivity.this.searchListView.setEmptyView(LocationActivity.this.emptyView);
        }

        public void onTextChanged(EditText editText) {
            if (LocationActivity.this.searchAdapter != null) {
                String obj = editText.getText().toString();
                if (obj.length() != 0) {
                    LocationActivity.this.searchWas = true;
                }
                LocationActivity.this.searchAdapter.searchDelayed(obj, LocationActivity.this.userLocation);
            }
        }
    }

    /* renamed from: org.telegram.ui.LocationActivity$4 */
    class C48634 extends ViewOutlineProvider {
        C48634() {
        }

        @SuppressLint({"NewApi"})
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        }
    }

    /* renamed from: org.telegram.ui.LocationActivity$6 */
    class C48666 extends OnScrollListener {

        /* renamed from: org.telegram.ui.LocationActivity$6$1 */
        class C48651 implements Runnable {
            C48651() {
            }

            public void run() {
                LocationActivity.this.adapter.searchGooglePlacesWithQuery(null, LocationActivity.this.myLocation);
            }
        }

        C48666() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            if (LocationActivity.this.adapter.getItemCount() != 0) {
                int findFirstVisibleItemPosition = LocationActivity.this.layoutManager.findFirstVisibleItemPosition();
                if (findFirstVisibleItemPosition != -1) {
                    LocationActivity.this.updateClipView(findFirstVisibleItemPosition);
                    if (i2 > 0 && !LocationActivity.this.adapter.isPulledUp()) {
                        LocationActivity.this.adapter.setPulledUp();
                        if (LocationActivity.this.myLocation != null) {
                            AndroidUtilities.runOnUIThread(new C48651());
                        }
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.LocationActivity$7 */
    class C48687 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.LocationActivity$7$1 */
        class C48671 implements IntCallback {
            C48671() {
            }

            public void run(int i) {
                MessageMedia tLRPC$TL_messageMediaGeoLive = new TLRPC$TL_messageMediaGeoLive();
                tLRPC$TL_messageMediaGeoLive.geo = new TLRPC$TL_geoPoint();
                tLRPC$TL_messageMediaGeoLive.geo.lat = LocationActivity.this.myLocation.getLatitude();
                tLRPC$TL_messageMediaGeoLive.geo._long = LocationActivity.this.myLocation.getLongitude();
                tLRPC$TL_messageMediaGeoLive.period = i;
                LocationActivity.this.delegate.didSelectLocation(tLRPC$TL_messageMediaGeoLive, LocationActivity.this.liveLocationType);
                LocationActivity.this.finishFragment();
            }
        }

        C48687() {
        }

        public void onItemClick(View view, int i) {
            if (i != 1 || LocationActivity.this.messageObject == null || LocationActivity.this.messageObject.isLiveLocation()) {
                if (i == 1 && LocationActivity.this.liveLocationType != 2) {
                    if (!(LocationActivity.this.delegate == null || LocationActivity.this.userLocation == null)) {
                        MessageMedia tLRPC$TL_messageMediaGeo = new TLRPC$TL_messageMediaGeo();
                        tLRPC$TL_messageMediaGeo.geo = new TLRPC$TL_geoPoint();
                        tLRPC$TL_messageMediaGeo.geo.lat = LocationActivity.this.userLocation.getLatitude();
                        tLRPC$TL_messageMediaGeo.geo._long = LocationActivity.this.userLocation.getLongitude();
                        LocationActivity.this.delegate.didSelectLocation(tLRPC$TL_messageMediaGeo, LocationActivity.this.liveLocationType);
                    }
                    LocationActivity.this.finishFragment();
                } else if ((i != 2 || LocationActivity.this.liveLocationType != 1) && ((i != 1 || LocationActivity.this.liveLocationType != 2) && (i != 3 || LocationActivity.this.liveLocationType != 3))) {
                    Object item = LocationActivity.this.adapter.getItem(i);
                    if (item instanceof TLRPC$TL_messageMediaVenue) {
                        if (!(item == null || LocationActivity.this.delegate == null)) {
                            LocationActivity.this.delegate.didSelectLocation((TLRPC$TL_messageMediaVenue) item, LocationActivity.this.liveLocationType);
                        }
                        LocationActivity.this.finishFragment();
                    } else if (item instanceof LiveLocation) {
                        LocationActivity.this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(((LiveLocation) item).marker.getPosition(), LocationActivity.this.googleMap.getMaxZoomLevel() - 4.0f));
                    }
                } else if (LocationController.getInstance().isSharingLocation(LocationActivity.this.dialogId)) {
                    LocationController.getInstance().removeSharingLocation(LocationActivity.this.dialogId);
                    LocationActivity.this.finishFragment();
                } else if (LocationActivity.this.delegate != null && LocationActivity.this.getParentActivity() != null && LocationActivity.this.myLocation != null) {
                    User user = null;
                    if (((int) LocationActivity.this.dialogId) > 0) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf((int) LocationActivity.this.dialogId));
                    }
                    LocationActivity.this.showDialog(AlertsCreator.createLocationUpdateDialog(LocationActivity.this.getParentActivity(), user, new C48671()));
                }
            } else if (LocationActivity.this.googleMap != null) {
                LocationActivity.this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LocationActivity.this.messageObject.messageOwner.media.geo.lat, LocationActivity.this.messageObject.messageOwner.media.geo._long), LocationActivity.this.googleMap.getMaxZoomLevel() - 4.0f));
            }
        }
    }

    /* renamed from: org.telegram.ui.LocationActivity$8 */
    class C48698 implements BaseLocationAdapterDelegate {
        C48698() {
        }

        public void didLoadedSearchResult(ArrayList<TLRPC$TL_messageMediaVenue> arrayList) {
            if (!LocationActivity.this.wasResults && !arrayList.isEmpty()) {
                LocationActivity.this.wasResults = true;
            }
        }
    }

    public class LiveLocation {
        public Chat chat;
        public int id;
        public Marker marker;
        public Message object;
        public User user;
    }

    public LocationActivity(int i) {
        this.liveLocationType = i;
    }

    private LiveLocation addUserMarker(Message message) {
        LatLng latLng = new LatLng(message.media.geo.lat, message.media.geo._long);
        LiveLocation liveLocation = (LiveLocation) this.markersMap.get(Integer.valueOf(message.from_id));
        if (liveLocation == null) {
            liveLocation = new LiveLocation();
            liveLocation.object = message;
            if (liveLocation.object.from_id != 0) {
                liveLocation.user = MessagesController.getInstance().getUser(Integer.valueOf(liveLocation.object.from_id));
                liveLocation.id = liveLocation.object.from_id;
            } else {
                int dialogId = (int) MessageObject.getDialogId(message);
                if (dialogId > 0) {
                    liveLocation.user = MessagesController.getInstance().getUser(Integer.valueOf(dialogId));
                    liveLocation.id = dialogId;
                } else {
                    liveLocation.chat = MessagesController.getInstance().getChat(Integer.valueOf(-dialogId));
                    liveLocation.id = -dialogId;
                }
            }
            try {
                MarkerOptions position = new MarkerOptions().position(latLng);
                Bitmap createUserBitmap = createUserBitmap(liveLocation);
                if (createUserBitmap != null) {
                    position.icon(BitmapDescriptorFactory.fromBitmap(createUserBitmap));
                    position.anchor(0.5f, 0.907f);
                    liveLocation.marker = this.googleMap.addMarker(position);
                    this.markers.add(liveLocation);
                    this.markersMap.put(Integer.valueOf(liveLocation.id), liveLocation);
                    SharingLocationInfo sharingLocationInfo = LocationController.getInstance().getSharingLocationInfo(this.dialogId);
                    if (liveLocation.id == UserConfig.getClientUserId() && sharingLocationInfo != null && liveLocation.object.id == sharingLocationInfo.mid && this.myLocation != null) {
                        liveLocation.marker.setPosition(new LatLng(this.myLocation.getLatitude(), this.myLocation.getLongitude()));
                    }
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
        } else {
            liveLocation.object = message;
            liveLocation.marker.setPosition(latLng);
        }
        return liveLocation;
    }

    private Bitmap createUserBitmap(LiveLocation liveLocation) {
        Bitmap createBitmap;
        Throwable th;
        try {
            TLObject tLObject = (liveLocation.user == null || liveLocation.user.photo == null) ? (liveLocation.chat == null || liveLocation.chat.photo == null) ? null : liveLocation.chat.photo.photo_small : liveLocation.user.photo.photo_small;
            createBitmap = Bitmap.createBitmap(AndroidUtilities.dp(62.0f), AndroidUtilities.dp(76.0f), Config.ARGB_8888);
            try {
                createBitmap.eraseColor(0);
                Canvas canvas = new Canvas(createBitmap);
                Drawable drawable = ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.livepin);
                drawable.setBounds(0, 0, AndroidUtilities.dp(62.0f), AndroidUtilities.dp(76.0f));
                drawable.draw(canvas);
                Paint paint = new Paint(1);
                RectF rectF = new RectF();
                canvas.save();
                if (tLObject != null) {
                    Bitmap decodeFile = BitmapFactory.decodeFile(FileLoader.getPathToAttach(tLObject, true).toString());
                    if (decodeFile != null) {
                        Shader bitmapShader = new BitmapShader(decodeFile, TileMode.CLAMP, TileMode.CLAMP);
                        Matrix matrix = new Matrix();
                        float dp = ((float) AndroidUtilities.dp(52.0f)) / ((float) decodeFile.getWidth());
                        matrix.postTranslate((float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(5.0f));
                        matrix.postScale(dp, dp);
                        paint.setShader(bitmapShader);
                        bitmapShader.setLocalMatrix(matrix);
                        rectF.set((float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(57.0f), (float) AndroidUtilities.dp(57.0f));
                        canvas.drawRoundRect(rectF, (float) AndroidUtilities.dp(26.0f), (float) AndroidUtilities.dp(26.0f), paint);
                    }
                } else {
                    AvatarDrawable avatarDrawable = new AvatarDrawable();
                    if (liveLocation.user != null) {
                        avatarDrawable.setInfo(liveLocation.user);
                    } else if (liveLocation.chat != null) {
                        avatarDrawable.setInfo(liveLocation.chat);
                    }
                    canvas.translate((float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(5.0f));
                    avatarDrawable.setBounds(0, 0, AndroidUtilities.dp(52.2f), AndroidUtilities.dp(52.2f));
                    avatarDrawable.draw(canvas);
                }
                canvas.restore();
                try {
                    canvas.setBitmap(null);
                } catch (Exception e) {
                }
            } catch (Throwable th2) {
                th = th2;
                FileLog.e(th);
                return createBitmap;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            createBitmap = null;
            th = th4;
            FileLog.e(th);
            return createBitmap;
        }
        return createBitmap;
    }

    private void fetchRecentLocations(ArrayList<Message> arrayList) {
        Builder builder = this.firstFocus ? new Builder() : null;
        int currentTime = ConnectionsManager.getInstance().getCurrentTime();
        for (int i = 0; i < arrayList.size(); i++) {
            Message message = (Message) arrayList.get(i);
            if (message.date + message.media.period > currentTime) {
                if (builder != null) {
                    builder.include(new LatLng(message.media.geo.lat, message.media.geo._long));
                }
                addUserMarker(message);
            }
        }
        if (builder != null) {
            this.firstFocus = false;
            this.adapter.setLiveLocations(this.markers);
            if (this.messageObject.isLiveLocation()) {
                try {
                    LatLngBounds build = builder.build();
                    if (arrayList.size() > 1) {
                        try {
                            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(build, AndroidUtilities.dp(60.0f)));
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                } catch (Exception e2) {
                }
            }
        }
    }

    private void fixLayoutInternal(boolean z) {
        if (this.listView != null) {
            int currentActionBarHeight = ActionBar.getCurrentActionBarHeight() + (this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0);
            int measuredHeight = this.fragmentView.getMeasuredHeight();
            if (measuredHeight != 0) {
                this.overScrollHeight = (measuredHeight - AndroidUtilities.dp(66.0f)) - currentActionBarHeight;
                LayoutParams layoutParams = (LayoutParams) this.listView.getLayoutParams();
                layoutParams.topMargin = currentActionBarHeight;
                this.listView.setLayoutParams(layoutParams);
                layoutParams = (LayoutParams) this.mapViewClip.getLayoutParams();
                layoutParams.topMargin = currentActionBarHeight;
                layoutParams.height = this.overScrollHeight;
                this.mapViewClip.setLayoutParams(layoutParams);
                if (this.searchListView != null) {
                    layoutParams = (LayoutParams) this.searchListView.getLayoutParams();
                    layoutParams.topMargin = currentActionBarHeight;
                    this.searchListView.setLayoutParams(layoutParams);
                }
                this.adapter.setOverScrollHeight(this.overScrollHeight);
                layoutParams = (LayoutParams) this.mapView.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.height = this.overScrollHeight + AndroidUtilities.dp(10.0f);
                    if (this.googleMap != null) {
                        this.googleMap.setPadding(0, 0, AndroidUtilities.dp(70.0f), AndroidUtilities.dp(10.0f));
                    }
                    this.mapView.setLayoutParams(layoutParams);
                }
                this.adapter.notifyDataSetChanged();
                if (z) {
                    LinearLayoutManager linearLayoutManager = this.layoutManager;
                    measuredHeight = (this.liveLocationType == 1 || this.liveLocationType == 2) ? 66 : 0;
                    linearLayoutManager.scrollToPositionWithOffset(0, -AndroidUtilities.dp((float) (measuredHeight + 32)));
                    updateClipView(this.layoutManager.findFirstVisibleItemPosition());
                    this.listView.post(new Runnable() {
                        public void run() {
                            LinearLayoutManager access$1300 = LocationActivity.this.layoutManager;
                            int i = (LocationActivity.this.liveLocationType == 1 || LocationActivity.this.liveLocationType == 2) ? 66 : 0;
                            access$1300.scrollToPositionWithOffset(0, -AndroidUtilities.dp((float) (i + 32)));
                            LocationActivity.this.updateClipView(LocationActivity.this.layoutManager.findFirstVisibleItemPosition());
                        }
                    });
                    return;
                }
                updateClipView(this.layoutManager.findFirstVisibleItemPosition());
            }
        }
    }

    public static Location getLastLocation() {
        LocationManager locationManager = (LocationManager) ApplicationLoader.applicationContext.getSystemService(C1797b.LOCATION);
        List providers = locationManager.getProviders(true);
        Location location = null;
        for (int size = providers.size() - 1; size >= 0; size--) {
            location = locationManager.getLastKnownLocation((String) providers.get(size));
            if (location != null) {
                return location;
            }
        }
        return location;
    }

    private int getMessageId(Message message) {
        return message.from_id != 0 ? message.from_id : (int) MessageObject.getDialogId(message);
    }

    private boolean getRecentLocations() {
        ArrayList arrayList = (ArrayList) LocationController.getInstance().locationsCache.get(Long.valueOf(this.messageObject.getDialogId()));
        if (arrayList == null || !arrayList.isEmpty()) {
            arrayList = null;
        } else {
            fetchRecentLocations(arrayList);
        }
        TLObject tLRPC$TL_messages_getRecentLocations = new TLRPC$TL_messages_getRecentLocations();
        final long dialogId = this.messageObject.getDialogId();
        tLRPC$TL_messages_getRecentLocations.peer = MessagesController.getInputPeer((int) dialogId);
        tLRPC$TL_messages_getRecentLocations.limit = 100;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getRecentLocations, new RequestDelegate() {
            public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLObject != null) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (LocationActivity.this.googleMap != null) {
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
                                LocationController.getInstance().locationsCache.put(Long.valueOf(dialogId), tLRPC$messages_Messages.messages);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsCacheChanged, new Object[]{Long.valueOf(dialogId)});
                                LocationActivity.this.fetchRecentLocations(tLRPC$messages_Messages.messages);
                            }
                        }
                    });
                }
            }
        });
        return arrayList != null;
    }

    private void onMapInit() {
        if (this.googleMap != null) {
            if (this.messageObject == null) {
                this.userLocation = new Location("network");
                this.userLocation.setLatitude(20.659322d);
                this.userLocation.setLongitude(-11.40625d);
            } else if (this.messageObject.isLiveLocation()) {
                LiveLocation addUserMarker = addUserMarker(this.messageObject.messageOwner);
                if (!getRecentLocations()) {
                    this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addUserMarker.marker.getPosition(), this.googleMap.getMaxZoomLevel() - 4.0f));
                }
            } else {
                LatLng latLng = new LatLng(this.userLocation.getLatitude(), this.userLocation.getLongitude());
                try {
                    this.googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin)));
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, this.googleMap.getMaxZoomLevel() - 4.0f));
                this.firstFocus = false;
                getRecentLocations();
            }
            try {
                this.googleMap.setMyLocationEnabled(true);
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
            this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            this.googleMap.getUiSettings().setZoomControlsEnabled(false);
            this.googleMap.getUiSettings().setCompassEnabled(false);
            this.googleMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
                public void onMyLocationChange(Location location) {
                    LocationActivity.this.positionMarker(location);
                    LocationController.getInstance().setGoogleMapLocation(location, LocationActivity.this.isFirstLocation);
                    LocationActivity.this.isFirstLocation = false;
                }
            });
            Location lastLocation = getLastLocation();
            this.myLocation = lastLocation;
            positionMarker(lastLocation);
            if (this.checkGpsEnabled && getParentActivity() != null) {
                this.checkGpsEnabled = false;
                try {
                    if (!((LocationManager) ApplicationLoader.applicationContext.getSystemService(C1797b.LOCATION)).isProviderEnabled("gps")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("GpsDisabledAlert", R.string.GpsDisabledAlert));
                        builder.setPositiveButton(LocaleController.getString("ConnectingToProxyEnable", R.string.ConnectingToProxyEnable), new OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (LocationActivity.this.getParentActivity() != null) {
                                    try {
                                        LocationActivity.this.getParentActivity().startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        });
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        showDialog(builder.create());
                    }
                } catch (Throwable e22) {
                    FileLog.e(e22);
                }
            }
        }
    }

    private void positionMarker(Location location) {
        if (location != null) {
            this.myLocation = new Location(location);
            LiveLocation liveLocation = (LiveLocation) this.markersMap.get(Integer.valueOf(UserConfig.getClientUserId()));
            SharingLocationInfo sharingLocationInfo = LocationController.getInstance().getSharingLocationInfo(this.dialogId);
            if (!(liveLocation == null || sharingLocationInfo == null || liveLocation.object.id != sharingLocationInfo.mid)) {
                liveLocation.marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
            }
            if (this.messageObject != null || this.googleMap == null) {
                this.adapter.setGpsLocation(this.myLocation);
                return;
            }
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (this.adapter != null) {
                if (this.adapter.isPulledUp()) {
                    this.adapter.searchGooglePlacesWithQuery(null, this.myLocation);
                }
                this.adapter.setGpsLocation(this.myLocation);
            }
            if (!this.userLocationMoved) {
                this.userLocation = new Location(location);
                if (this.firstWas) {
                    this.googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    return;
                }
                this.firstWas = true;
                this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, this.googleMap.getMaxZoomLevel() - 4.0f));
            }
        }
    }

    private void showPermissionAlert(boolean z) {
        if (getParentActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            if (z) {
                builder.setMessage(LocaleController.getString("PermissionNoLocationPosition", R.string.PermissionNoLocationPosition));
            } else {
                builder.setMessage(LocaleController.getString("PermissionNoLocation", R.string.PermissionNoLocation));
            }
            builder.setNegativeButton(LocaleController.getString("PermissionOpenSettings", R.string.PermissionOpenSettings), new OnClickListener() {
                @TargetApi(9)
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (LocationActivity.this.getParentActivity() != null) {
                        try {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
                            LocationActivity.this.getParentActivity().startActivity(intent);
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                }
            });
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            showDialog(builder.create());
        }
    }

    private void updateClipView(int i) {
        if (i != -1) {
            View childAt = this.listView.getChildAt(0);
            if (childAt != null) {
                int top;
                int i2;
                if (i == 0) {
                    top = childAt.getTop();
                    i2 = (top < 0 ? top : 0) + this.overScrollHeight;
                } else {
                    top = 0;
                    i2 = 0;
                }
                if (((LayoutParams) this.mapViewClip.getLayoutParams()) != null) {
                    if (i2 <= 0) {
                        if (this.mapView.getVisibility() == 0) {
                            this.mapView.setVisibility(4);
                            this.mapViewClip.setVisibility(4);
                        }
                    } else if (this.mapView.getVisibility() == 4) {
                        this.mapView.setVisibility(0);
                        this.mapViewClip.setVisibility(0);
                    }
                    this.mapViewClip.setTranslationY((float) Math.min(0, top));
                    this.mapView.setTranslationY((float) Math.max(0, (-top) / 2));
                    if (this.markerImageView != null) {
                        ImageView imageView = this.markerImageView;
                        int dp = ((-top) - AndroidUtilities.dp(42.0f)) + (i2 / 2);
                        this.markerTop = dp;
                        imageView.setTranslationY((float) dp);
                        this.markerXImageView.setTranslationY((float) ((i2 / 2) + ((-top) - AndroidUtilities.dp(7.0f))));
                    }
                    if (this.routeButton != null) {
                        this.routeButton.setTranslationY((float) top);
                    }
                    LayoutParams layoutParams = (LayoutParams) this.mapView.getLayoutParams();
                    if (layoutParams != null && layoutParams.height != this.overScrollHeight + AndroidUtilities.dp(10.0f)) {
                        layoutParams.height = this.overScrollHeight + AndroidUtilities.dp(10.0f);
                        if (this.googleMap != null) {
                            this.googleMap.setPadding(0, 0, AndroidUtilities.dp(70.0f), AndroidUtilities.dp(10.0f));
                        }
                        this.mapView.setLayoutParams(layoutParams);
                    }
                }
            }
        }
    }

    private void updateSearchInterface() {
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
    }

    public View createView(Context context) {
        Drawable combinedDrawable;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setAddToContainer(false);
        this.actionBar.setActionBarMenuOnItemClick(new C48591());
        ActionBarMenu createMenu = this.actionBar.createMenu();
        if (this.messageObject == null) {
            this.actionBar.setTitle(LocaleController.getString("ShareLocation", R.string.ShareLocation));
            createMenu.addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C48612()).getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        } else if (this.messageObject.isLiveLocation()) {
            this.actionBar.setTitle(LocaleController.getString("AttachLiveLocation", R.string.AttachLiveLocation));
        } else {
            if (this.messageObject.messageOwner.media.title == null || this.messageObject.messageOwner.media.title.length() <= 0) {
                this.actionBar.setTitle(LocaleController.getString("ChatLocation", R.string.ChatLocation));
            } else {
                this.actionBar.setTitle(LocaleController.getString("SharedPlace", R.string.SharedPlace));
            }
            createMenu.addItem(1, (int) R.drawable.share);
        }
        this.otherItem = createMenu.addItem(0, (int) R.drawable.ic_ab_other);
        this.otherItem.addSubItem(2, LocaleController.getString("Map", R.string.Map));
        this.otherItem.addSubItem(3, LocaleController.getString("Satellite", R.string.Satellite));
        this.otherItem.addSubItem(4, LocaleController.getString("Hybrid", R.string.Hybrid));
        this.fragmentView = new FrameLayout(context) {
            private boolean first = true;

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                if (z) {
                    LocationActivity.this.fixLayoutInternal(this.first);
                    this.first = false;
                }
            }
        };
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.locationButton = new ImageView(context);
        Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_profile_actionBackground), Theme.getColor(Theme.key_profile_actionPressedBackground));
        if (VERSION.SDK_INT < 21) {
            Drawable mutate = context.getResources().getDrawable(R.drawable.floating_shadow_profile).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, Mode.MULTIPLY));
            combinedDrawable = new CombinedDrawable(mutate, createSimpleSelectorCircleDrawable, 0, 0);
            combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        } else {
            combinedDrawable = createSimpleSelectorCircleDrawable;
        }
        this.locationButton.setBackgroundDrawable(combinedDrawable);
        this.locationButton.setImageResource(R.drawable.myloc_on);
        this.locationButton.setScaleType(ScaleType.CENTER);
        this.locationButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_profile_actionIcon), Mode.MULTIPLY));
        if (VERSION.SDK_INT >= 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.locationButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
            stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this.locationButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
            this.locationButton.setStateListAnimator(stateListAnimator);
            this.locationButton.setOutlineProvider(new C48634());
        }
        if (this.messageObject != null) {
            this.userLocation = new Location("network");
            this.userLocation.setLatitude(this.messageObject.messageOwner.media.geo.lat);
            this.userLocation.setLongitude(this.messageObject.messageOwner.media.geo._long);
        }
        this.searchWas = false;
        this.searching = false;
        this.mapViewClip = new FrameLayout(context);
        this.mapViewClip.setBackgroundDrawable(new MapPlaceholderDrawable());
        if (this.adapter != null) {
            this.adapter.destroy();
        }
        if (this.searchAdapter != null) {
            this.searchAdapter.destroy();
        }
        this.listView = new RecyclerListView(context);
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        RecyclerListView recyclerListView = this.listView;
        Adapter locationActivityAdapter = new LocationActivityAdapter(context, this.liveLocationType, this.dialogId);
        this.adapter = locationActivityAdapter;
        recyclerListView.setAdapter(locationActivityAdapter);
        this.listView.setVerticalScrollBarEnabled(false);
        recyclerListView = this.listView;
        LayoutManager c48645 = new LinearLayoutManager(context, 1, false) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.layoutManager = c48645;
        recyclerListView.setLayoutManager(c48645);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setOnScrollListener(new C48666());
        this.listView.setOnItemClickListener(new C48687());
        this.adapter.setDelegate(new C48698());
        this.adapter.setOverScrollHeight(this.overScrollHeight);
        frameLayout.addView(this.mapViewClip, LayoutHelper.createFrame(-1, -1, 51));
        this.mapView = new MapView(context) {
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (LocationActivity.this.messageObject == null) {
                    AnimatorSet access$2000;
                    Animator[] animatorArr;
                    if (motionEvent.getAction() == 0) {
                        if (LocationActivity.this.animatorSet != null) {
                            LocationActivity.this.animatorSet.cancel();
                        }
                        LocationActivity.this.animatorSet = new AnimatorSet();
                        LocationActivity.this.animatorSet.setDuration(200);
                        access$2000 = LocationActivity.this.animatorSet;
                        animatorArr = new Animator[2];
                        animatorArr[0] = ObjectAnimator.ofFloat(LocationActivity.this.markerImageView, "translationY", new float[]{(float) (LocationActivity.this.markerTop + (-AndroidUtilities.dp(10.0f)))});
                        animatorArr[1] = ObjectAnimator.ofFloat(LocationActivity.this.markerXImageView, "alpha", new float[]{1.0f});
                        access$2000.playTogether(animatorArr);
                        LocationActivity.this.animatorSet.start();
                    } else if (motionEvent.getAction() == 1) {
                        if (LocationActivity.this.animatorSet != null) {
                            LocationActivity.this.animatorSet.cancel();
                        }
                        LocationActivity.this.animatorSet = new AnimatorSet();
                        LocationActivity.this.animatorSet.setDuration(200);
                        access$2000 = LocationActivity.this.animatorSet;
                        animatorArr = new Animator[2];
                        animatorArr[0] = ObjectAnimator.ofFloat(LocationActivity.this.markerImageView, "translationY", new float[]{(float) LocationActivity.this.markerTop});
                        animatorArr[1] = ObjectAnimator.ofFloat(LocationActivity.this.markerXImageView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                        access$2000.playTogether(animatorArr);
                        LocationActivity.this.animatorSet.start();
                    }
                    if (motionEvent.getAction() == 2) {
                        if (!LocationActivity.this.userLocationMoved) {
                            access$2000 = new AnimatorSet();
                            access$2000.setDuration(200);
                            access$2000.play(ObjectAnimator.ofFloat(LocationActivity.this.locationButton, "alpha", new float[]{1.0f}));
                            access$2000.start();
                            LocationActivity.this.userLocationMoved = true;
                        }
                        if (!(LocationActivity.this.googleMap == null || LocationActivity.this.userLocation == null)) {
                            LocationActivity.this.userLocation.setLatitude(LocationActivity.this.googleMap.getCameraPosition().target.latitude);
                            LocationActivity.this.userLocation.setLongitude(LocationActivity.this.googleMap.getCameraPosition().target.longitude);
                        }
                        LocationActivity.this.adapter.setCustomLocation(LocationActivity.this.userLocation);
                    }
                }
                return super.onInterceptTouchEvent(motionEvent);
            }
        };
        final MapView mapView = this.mapView;
        new Thread(new Runnable() {

            /* renamed from: org.telegram.ui.LocationActivity$10$1 */
            class C48581 implements Runnable {

                /* renamed from: org.telegram.ui.LocationActivity$10$1$1 */
                class C48571 implements OnMapReadyCallback {
                    C48571() {
                    }

                    public void onMapReady(GoogleMap googleMap) {
                        LocationActivity.this.googleMap = googleMap;
                        LocationActivity.this.googleMap.setPadding(0, 0, AndroidUtilities.dp(70.0f), AndroidUtilities.dp(10.0f));
                        LocationActivity.this.onMapInit();
                    }
                }

                C48581() {
                }

                public void run() {
                    if (LocationActivity.this.mapView != null && LocationActivity.this.getParentActivity() != null) {
                        try {
                            mapView.onCreate(null);
                            MapsInitializer.initialize(LocationActivity.this.getParentActivity());
                            LocationActivity.this.mapView.getMapAsync(new C48571());
                            LocationActivity.this.mapsInitialized = true;
                            if (LocationActivity.this.onResumeCalled) {
                                LocationActivity.this.mapView.onResume();
                            }
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                }
            }

            public void run() {
                try {
                    mapView.onCreate(null);
                } catch (Exception e) {
                }
                AndroidUtilities.runOnUIThread(new C48581());
            }
        }).start();
        View view = new View(context);
        view.setBackgroundResource(R.drawable.header_shadow_reverse);
        this.mapViewClip.addView(view, LayoutHelper.createFrame(-1, 3, 83));
        if (this.messageObject == null) {
            this.markerImageView = new ImageView(context);
            this.markerImageView.setImageResource(R.drawable.map_pin);
            this.mapViewClip.addView(this.markerImageView, LayoutHelper.createFrame(24, 42, 49));
            this.markerXImageView = new ImageView(context);
            this.markerXImageView.setAlpha(BitmapDescriptorFactory.HUE_RED);
            this.markerXImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_location_markerX), Mode.MULTIPLY));
            this.markerXImageView.setImageResource(R.drawable.place_x);
            this.mapViewClip.addView(this.markerXImageView, LayoutHelper.createFrame(14, 14, 49));
            this.emptyView = new EmptyTextProgressView(context);
            this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
            this.emptyView.setShowAtCenter(true);
            this.emptyView.setVisibility(8);
            frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
            this.searchListView = new RecyclerListView(context);
            this.searchListView.setVisibility(8);
            this.searchListView.setLayoutManager(new LinearLayoutManager(context, 1, false));
            recyclerListView = this.searchListView;
            locationActivityAdapter = new LocationActivitySearchAdapter(context);
            this.searchAdapter = locationActivityAdapter;
            recyclerListView.setAdapter(locationActivityAdapter);
            frameLayout.addView(this.searchListView, LayoutHelper.createFrame(-1, -1, 51));
            this.searchListView.setOnScrollListener(new OnScrollListener() {
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    if (i == 1 && LocationActivity.this.searching && LocationActivity.this.searchWas) {
                        AndroidUtilities.hideKeyboard(LocationActivity.this.getParentActivity().getCurrentFocus());
                    }
                }
            });
            this.searchListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(View view, int i) {
                    MessageMedia item = LocationActivity.this.searchAdapter.getItem(i);
                    if (!(item == null || LocationActivity.this.delegate == null)) {
                        LocationActivity.this.delegate.didSelectLocation(item, LocationActivity.this.liveLocationType);
                    }
                    LocationActivity.this.finishFragment();
                }
            });
        } else if (!this.messageObject.isLiveLocation()) {
            this.routeButton = new ImageView(context);
            createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_chats_actionBackground), Theme.getColor(Theme.key_chats_actionPressedBackground));
            if (VERSION.SDK_INT < 21) {
                mutate = context.getResources().getDrawable(R.drawable.floating_shadow).mutate();
                mutate.setColorFilter(new PorterDuffColorFilter(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, Mode.MULTIPLY));
                combinedDrawable = new CombinedDrawable(mutate, createSimpleSelectorCircleDrawable, 0, 0);
                combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
            } else {
                combinedDrawable = createSimpleSelectorCircleDrawable;
            }
            this.routeButton.setBackgroundDrawable(combinedDrawable);
            this.routeButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), Mode.MULTIPLY));
            this.routeButton.setImageResource(R.drawable.navigate);
            this.routeButton.setScaleType(ScaleType.CENTER);
            if (VERSION.SDK_INT >= 21) {
                stateListAnimator = new StateListAnimator();
                stateListAnimator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.routeButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
                stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this.routeButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
                this.routeButton.setStateListAnimator(stateListAnimator);
                this.routeButton.setOutlineProvider(new ViewOutlineProvider() {
                    @SuppressLint({"NewApi"})
                    public void getOutline(View view, Outline outline) {
                        outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
                    }
                });
            }
            frameLayout.addView(this.routeButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, (LocaleController.isRTL ? 3 : 5) | 80, LocaleController.isRTL ? 14.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 14.0f, 37.0f));
            this.routeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (VERSION.SDK_INT >= 23) {
                        Activity parentActivity = LocationActivity.this.getParentActivity();
                        if (!(parentActivity == null || parentActivity.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0)) {
                            LocationActivity.this.showPermissionAlert(true);
                            return;
                        }
                    }
                    if (LocationActivity.this.myLocation != null) {
                        try {
                            LocationActivity.this.getParentActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format(Locale.US, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", new Object[]{Double.valueOf(LocationActivity.this.myLocation.getLatitude()), Double.valueOf(LocationActivity.this.myLocation.getLongitude()), Double.valueOf(LocationActivity.this.messageObject.messageOwner.media.geo.lat), Double.valueOf(LocationActivity.this.messageObject.messageOwner.media.geo._long)}))));
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                }
            });
            this.adapter.setMessageObject(this.messageObject);
        }
        if (this.messageObject == null || this.messageObject.isLiveLocation()) {
            this.mapViewClip.addView(this.locationButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, (LocaleController.isRTL ? 3 : 5) | 80, LocaleController.isRTL ? 14.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 14.0f, 14.0f));
        } else {
            this.mapViewClip.addView(this.locationButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, (LocaleController.isRTL ? 3 : 5) | 80, LocaleController.isRTL ? 14.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 14.0f, 43.0f));
        }
        this.locationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (VERSION.SDK_INT >= 23) {
                    Activity parentActivity = LocationActivity.this.getParentActivity();
                    if (!(parentActivity == null || parentActivity.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0)) {
                        LocationActivity.this.showPermissionAlert(false);
                        return;
                    }
                }
                if (LocationActivity.this.messageObject != null) {
                    if (LocationActivity.this.myLocation != null && LocationActivity.this.googleMap != null) {
                        LocationActivity.this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LocationActivity.this.myLocation.getLatitude(), LocationActivity.this.myLocation.getLongitude()), LocationActivity.this.googleMap.getMaxZoomLevel() - 4.0f));
                    }
                } else if (LocationActivity.this.myLocation != null && LocationActivity.this.googleMap != null) {
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(200);
                    animatorSet.play(ObjectAnimator.ofFloat(LocationActivity.this.locationButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                    animatorSet.start();
                    LocationActivity.this.adapter.setCustomLocation(null);
                    LocationActivity.this.userLocationMoved = false;
                    LocationActivity.this.googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(LocationActivity.this.myLocation.getLatitude(), LocationActivity.this.myLocation.getLongitude())));
                }
            }
        });
        if (this.messageObject == null) {
            this.locationButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
        }
        frameLayout.addView(this.actionBar);
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.closeChats) {
            removeSelfFromStack();
        } else if (i == NotificationCenter.locationPermissionGranted) {
            if (this.googleMap != null) {
                try {
                    this.googleMap.setMyLocationEnabled(true);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        } else if (i == NotificationCenter.didReceivedNewMessages) {
            if (((Long) objArr[0]).longValue() == this.dialogId && this.messageObject != null) {
                r0 = (ArrayList) objArr[1];
                r3 = 0;
                for (r2 = 0; r2 < r0.size(); r2++) {
                    r1 = (MessageObject) r0.get(r2);
                    if (r1.isLiveLocation()) {
                        addUserMarker(r1.messageOwner);
                        r3 = 1;
                    }
                }
                if (r3 != 0 && this.adapter != null) {
                    this.adapter.setLiveLocations(this.markers);
                }
            }
        } else if (i != NotificationCenter.messagesDeleted && i == NotificationCenter.replaceMessagesObjects) {
            long longValue = ((Long) objArr[0]).longValue();
            if (longValue == this.dialogId && this.messageObject != null) {
                r0 = (ArrayList) objArr[1];
                r3 = 0;
                int i2 = 0;
                while (r3 < r0.size()) {
                    r1 = (MessageObject) r0.get(r3);
                    if (r1.isLiveLocation()) {
                        LiveLocation liveLocation = (LiveLocation) this.markersMap.get(Integer.valueOf(getMessageId(r1.messageOwner)));
                        if (liveLocation != null) {
                            SharingLocationInfo sharingLocationInfo = LocationController.getInstance().getSharingLocationInfo(longValue);
                            if (sharingLocationInfo == null || sharingLocationInfo.mid != r1.getId()) {
                                liveLocation.marker.setPosition(new LatLng(r1.messageOwner.media.geo.lat, r1.messageOwner.media.geo._long));
                            }
                            r2 = 1;
                        } else {
                            r2 = i2;
                        }
                    } else {
                        r2 = i2;
                    }
                    r3++;
                    i2 = r2;
                }
                if (i2 != 0 && this.adapter != null) {
                    this.adapter.updateLiveLocations();
                }
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        AnonymousClass21 anonymousClass21 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
            }
        };
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[50];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch);
        themeDescriptionArr[7] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder);
        themeDescriptionArr[8] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUBACKGROUND, null, null, null, null, Theme.key_actionBarDefaultSubmenuBackground);
        themeDescriptionArr[9] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUITEM, null, null, null, null, Theme.key_actionBarDefaultSubmenuItem);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[11] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[12] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        themeDescriptionArr[13] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[14] = new ThemeDescription(this.locationButton, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_profile_actionIcon);
        themeDescriptionArr[15] = new ThemeDescription(this.locationButton, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_profile_actionBackground);
        themeDescriptionArr[16] = new ThemeDescription(this.locationButton, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_profile_actionPressedBackground);
        themeDescriptionArr[17] = new ThemeDescription(this.routeButton, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chats_actionIcon);
        themeDescriptionArr[18] = new ThemeDescription(this.routeButton, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chats_actionBackground);
        themeDescriptionArr[19] = new ThemeDescription(this.routeButton, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_chats_actionPressedBackground);
        themeDescriptionArr[20] = new ThemeDescription(this.markerXImageView, 0, null, null, null, null, Theme.key_location_markerX);
        themeDescriptionArr[21] = new ThemeDescription(this.listView, 0, new Class[]{GraySectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[22] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GraySectionCell.class}, null, null, null, Theme.key_graySection);
        themeDescriptionArr[23] = new ThemeDescription(null, 0, null, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, anonymousClass21, Theme.key_avatar_text);
        themeDescriptionArr[24] = new ThemeDescription(null, 0, null, null, null, anonymousClass21, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[25] = new ThemeDescription(null, 0, null, null, null, anonymousClass21, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[26] = new ThemeDescription(null, 0, null, null, null, anonymousClass21, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[27] = new ThemeDescription(null, 0, null, null, null, anonymousClass21, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[28] = new ThemeDescription(null, 0, null, null, null, anonymousClass21, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[29] = new ThemeDescription(null, 0, null, null, null, anonymousClass21, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[30] = new ThemeDescription(null, 0, null, null, null, anonymousClass21, Theme.key_avatar_backgroundPink);
        themeDescriptionArr[31] = new ThemeDescription(null, 0, null, null, null, null, "location_liveLocationProgress");
        themeDescriptionArr[32] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_location_placeLocationBackground);
        themeDescriptionArr[33] = new ThemeDescription(null, 0, null, null, null, null, "location_liveLocationProgress");
        themeDescriptionArr[34] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{SendLocationCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_location_sendLocationIcon);
        themeDescriptionArr[35] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE, new Class[]{SendLocationCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_location_sendLocationBackground);
        themeDescriptionArr[36] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE, new Class[]{SendLocationCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_location_sendLiveLocationBackground);
        themeDescriptionArr[37] = new ThemeDescription(this.listView, 0, new Class[]{SendLocationCell.class}, new String[]{"titleTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueText7);
        themeDescriptionArr[38] = new ThemeDescription(this.listView, 0, new Class[]{SendLocationCell.class}, new String[]{"accurateTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[39] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{LocationCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[40] = new ThemeDescription(this.listView, 0, new Class[]{LocationCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[41] = new ThemeDescription(this.listView, 0, new Class[]{LocationCell.class}, new String[]{"addressTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[42] = new ThemeDescription(this.searchListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{LocationCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[43] = new ThemeDescription(this.searchListView, 0, new Class[]{LocationCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[44] = new ThemeDescription(this.searchListView, 0, new Class[]{LocationCell.class}, new String[]{"addressTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[45] = new ThemeDescription(this.listView, 0, new Class[]{LocationLoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[46] = new ThemeDescription(this.listView, 0, new Class[]{LocationLoadingCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[47] = new ThemeDescription(this.listView, 0, new Class[]{LocationPoweredCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[48] = new ThemeDescription(this.listView, 0, new Class[]{LocationPoweredCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[49] = new ThemeDescription(this.listView, 0, new Class[]{LocationPoweredCell.class}, new String[]{"textView2"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        return themeDescriptionArr;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.swipeBackEnabled = false;
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeChats);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.locationPermissionGranted);
        if (this.messageObject != null && this.messageObject.isLiveLocation()) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceivedNewMessages);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDeleted);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.replaceMessagesObjects);
        }
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.locationPermissionGranted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceivedNewMessages);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDeleted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.replaceMessagesObjects);
        try {
            if (this.mapView != null) {
                this.mapView.onDestroy();
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
        if (this.adapter != null) {
            this.adapter.destroy();
        }
        if (this.searchAdapter != null) {
            this.searchAdapter.destroy();
        }
        if (this.updateRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.updateRunnable);
            this.updateRunnable = null;
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        if (this.mapView != null && this.mapsInitialized) {
            this.mapView.onLowMemory();
        }
    }

    public void onPause() {
        super.onPause();
        if (this.mapView != null && this.mapsInitialized) {
            try {
                this.mapView.onPause();
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
        this.onResumeCalled = false;
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
        if (this.mapView != null && this.mapsInitialized) {
            try {
                this.mapView.onResume();
            } catch (Throwable th) {
                FileLog.e(th);
            }
        }
        this.onResumeCalled = true;
        if (this.googleMap != null) {
            try {
                this.googleMap.setMyLocationEnabled(true);
            } catch (Throwable th2) {
                FileLog.e(th2);
            }
        }
        fixLayoutInternal(true);
        if (this.checkPermission && VERSION.SDK_INT >= 23) {
            Activity parentActivity = getParentActivity();
            if (parentActivity != null) {
                this.checkPermission = false;
                if (parentActivity.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    parentActivity.requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, 2);
                }
            }
        }
    }

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z) {
            try {
                if (this.mapView.getParent() instanceof ViewGroup) {
                    ((ViewGroup) this.mapView.getParent()).removeView(this.mapView);
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (this.mapViewClip != null) {
                this.mapViewClip.addView(this.mapView, 0, LayoutHelper.createFrame(-1, this.overScrollHeight + AndroidUtilities.dp(10.0f), 51));
                updateClipView(this.layoutManager.findFirstVisibleItemPosition());
            } else if (this.fragmentView != null) {
                ((FrameLayout) this.fragmentView).addView(this.mapView, 0, LayoutHelper.createFrame(-1, -1, 51));
            }
        }
    }

    public void setDelegate(LocationActivityDelegate locationActivityDelegate) {
        this.delegate = locationActivityDelegate;
    }

    public void setDialogId(long j) {
        this.dialogId = j;
    }

    public void setMessageObject(MessageObject messageObject) {
        this.messageObject = messageObject;
        this.dialogId = this.messageObject.getDialogId();
    }
}
