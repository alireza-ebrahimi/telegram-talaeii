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
import android.view.View.OnClickListener;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
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
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_geoPoint;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeo;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive;
import org.telegram.tgnet.TLRPC$TL_messageMediaVenue;
import org.telegram.tgnet.TLRPC$TL_messages_getRecentLocations;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
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
        void didSelectLocation(TLRPC$MessageMedia tLRPC$MessageMedia, int i);
    }

    /* renamed from: org.telegram.ui.LocationActivity$1 */
    class C30201 extends ActionBarMenuOnItemClick {
        C30201() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                LocationActivity.this.finishFragment();
            } else if (id == 2) {
                if (LocationActivity.this.googleMap != null) {
                    LocationActivity.this.googleMap.setMapType(1);
                }
            } else if (id == 3) {
                if (LocationActivity.this.googleMap != null) {
                    LocationActivity.this.googleMap.setMapType(2);
                }
            } else if (id == 4) {
                if (LocationActivity.this.googleMap != null) {
                    LocationActivity.this.googleMap.setMapType(4);
                }
            } else if (id == 1) {
                try {
                    double lat = LocationActivity.this.messageObject.messageOwner.media.geo.lat;
                    double lon = LocationActivity.this.messageObject.messageOwner.media.geo._long;
                    LocationActivity.this.getParentActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("geo:" + lat + "," + lon + "?q=" + lat + "," + lon)));
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.LocationActivity$2 */
    class C30222 extends ActionBarMenuItemSearchListener {
        C30222() {
        }

        public void onSearchExpand() {
            LocationActivity.this.searching = true;
            LocationActivity.this.otherItem.setVisibility(8);
            LocationActivity.this.listView.setVisibility(8);
            LocationActivity.this.mapViewClip.setVisibility(8);
            LocationActivity.this.searchListView.setVisibility(0);
            LocationActivity.this.searchListView.setEmptyView(LocationActivity.this.emptyView);
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

        public void onTextChanged(EditText editText) {
            if (LocationActivity.this.searchAdapter != null) {
                String text = editText.getText().toString();
                if (text.length() != 0) {
                    LocationActivity.this.searchWas = true;
                }
                LocationActivity.this.searchAdapter.searchDelayed(text, LocationActivity.this.userLocation);
            }
        }
    }

    /* renamed from: org.telegram.ui.LocationActivity$4 */
    class C30244 extends ViewOutlineProvider {
        C30244() {
        }

        @SuppressLint({"NewApi"})
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        }
    }

    /* renamed from: org.telegram.ui.LocationActivity$6 */
    class C30276 extends OnScrollListener {

        /* renamed from: org.telegram.ui.LocationActivity$6$1 */
        class C30261 implements Runnable {
            C30261() {
            }

            public void run() {
                LocationActivity.this.adapter.searchGooglePlacesWithQuery(null, LocationActivity.this.myLocation);
            }
        }

        C30276() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (LocationActivity.this.adapter.getItemCount() != 0) {
                int position = LocationActivity.this.layoutManager.findFirstVisibleItemPosition();
                if (position != -1) {
                    LocationActivity.this.updateClipView(position);
                    if (dy > 0 && !LocationActivity.this.adapter.isPulledUp()) {
                        LocationActivity.this.adapter.setPulledUp();
                        if (LocationActivity.this.myLocation != null) {
                            AndroidUtilities.runOnUIThread(new C30261());
                        }
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.LocationActivity$7 */
    class C30297 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.LocationActivity$7$1 */
        class C30281 implements IntCallback {
            C30281() {
            }

            public void run(int param) {
                TLRPC$TL_messageMediaGeoLive location = new TLRPC$TL_messageMediaGeoLive();
                location.geo = new TLRPC$TL_geoPoint();
                location.geo.lat = LocationActivity.this.myLocation.getLatitude();
                location.geo._long = LocationActivity.this.myLocation.getLongitude();
                location.period = param;
                LocationActivity.this.delegate.didSelectLocation(location, LocationActivity.this.liveLocationType);
                LocationActivity.this.finishFragment();
            }
        }

        C30297() {
        }

        public void onItemClick(View view, int position) {
            if (position != 1 || LocationActivity.this.messageObject == null || LocationActivity.this.messageObject.isLiveLocation()) {
                if (position == 1 && LocationActivity.this.liveLocationType != 2) {
                    if (!(LocationActivity.this.delegate == null || LocationActivity.this.userLocation == null)) {
                        TLRPC$TL_messageMediaGeo location = new TLRPC$TL_messageMediaGeo();
                        location.geo = new TLRPC$TL_geoPoint();
                        location.geo.lat = LocationActivity.this.userLocation.getLatitude();
                        location.geo._long = LocationActivity.this.userLocation.getLongitude();
                        LocationActivity.this.delegate.didSelectLocation(location, LocationActivity.this.liveLocationType);
                    }
                    LocationActivity.this.finishFragment();
                } else if ((position != 2 || LocationActivity.this.liveLocationType != 1) && ((position != 1 || LocationActivity.this.liveLocationType != 2) && (position != 3 || LocationActivity.this.liveLocationType != 3))) {
                    Object object = LocationActivity.this.adapter.getItem(position);
                    if (object instanceof TLRPC$TL_messageMediaVenue) {
                        if (!(object == null || LocationActivity.this.delegate == null)) {
                            LocationActivity.this.delegate.didSelectLocation((TLRPC$TL_messageMediaVenue) object, LocationActivity.this.liveLocationType);
                        }
                        LocationActivity.this.finishFragment();
                    } else if (object instanceof LiveLocation) {
                        LocationActivity.this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(((LiveLocation) object).marker.getPosition(), LocationActivity.this.googleMap.getMaxZoomLevel() - 4.0f));
                    }
                } else if (LocationController.getInstance().isSharingLocation(LocationActivity.this.dialogId)) {
                    LocationController.getInstance().removeSharingLocation(LocationActivity.this.dialogId);
                    LocationActivity.this.finishFragment();
                } else if (LocationActivity.this.delegate != null && LocationActivity.this.getParentActivity() != null && LocationActivity.this.myLocation != null) {
                    User user = null;
                    if (((int) LocationActivity.this.dialogId) > 0) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf((int) LocationActivity.this.dialogId));
                    }
                    LocationActivity.this.showDialog(AlertsCreator.createLocationUpdateDialog(LocationActivity.this.getParentActivity(), user, new C30281()));
                }
            } else if (LocationActivity.this.googleMap != null) {
                LocationActivity.this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LocationActivity.this.messageObject.messageOwner.media.geo.lat, LocationActivity.this.messageObject.messageOwner.media.geo._long), LocationActivity.this.googleMap.getMaxZoomLevel() - 4.0f));
            }
        }
    }

    /* renamed from: org.telegram.ui.LocationActivity$8 */
    class C30308 implements BaseLocationAdapterDelegate {
        C30308() {
        }

        public void didLoadedSearchResult(ArrayList<TLRPC$TL_messageMediaVenue> places) {
            if (!LocationActivity.this.wasResults && !places.isEmpty()) {
                LocationActivity.this.wasResults = true;
            }
        }
    }

    public class LiveLocation {
        public TLRPC$Chat chat;
        public int id;
        public Marker marker;
        public TLRPC$Message object;
        public User user;
    }

    public LocationActivity(int liveLocation) {
        this.liveLocationType = liveLocation;
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
        } catch (Exception e) {
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

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setAddToContainer(false);
        this.actionBar.setActionBarMenuOnItemClick(new C30201());
        ActionBarMenu menu = this.actionBar.createMenu();
        if (this.messageObject == null) {
            this.actionBar.setTitle(LocaleController.getString("ShareLocation", R.string.ShareLocation));
            menu.addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C30222()).getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        } else if (this.messageObject.isLiveLocation()) {
            this.actionBar.setTitle(LocaleController.getString("AttachLiveLocation", R.string.AttachLiveLocation));
        } else {
            if (this.messageObject.messageOwner.media.title == null || this.messageObject.messageOwner.media.title.length() <= 0) {
                this.actionBar.setTitle(LocaleController.getString("ChatLocation", R.string.ChatLocation));
            } else {
                this.actionBar.setTitle(LocaleController.getString("SharedPlace", R.string.SharedPlace));
            }
            menu.addItem(1, (int) R.drawable.share);
        }
        this.otherItem = menu.addItem(0, (int) R.drawable.ic_ab_other);
        this.otherItem.addSubItem(2, LocaleController.getString("Map", R.string.Map));
        this.otherItem.addSubItem(3, LocaleController.getString("Satellite", R.string.Satellite));
        this.otherItem.addSubItem(4, LocaleController.getString("Hybrid", R.string.Hybrid));
        this.fragmentView = new FrameLayout(context) {
            private boolean first = true;

            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                super.onLayout(changed, left, top, right, bottom);
                if (changed) {
                    LocationActivity.this.fixLayoutInternal(this.first);
                    this.first = false;
                }
            }
        };
        FrameLayout frameLayout = this.fragmentView;
        this.locationButton = new ImageView(context);
        Drawable drawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_profile_actionBackground), Theme.getColor(Theme.key_profile_actionPressedBackground));
        if (VERSION.SDK_INT < 21) {
            Drawable shadowDrawable = context.getResources().getDrawable(R.drawable.floating_shadow_profile).mutate();
            shadowDrawable.setColorFilter(new PorterDuffColorFilter(-16777216, Mode.MULTIPLY));
            Drawable combinedDrawable = new CombinedDrawable(shadowDrawable, drawable, 0, 0);
            combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
            drawable = combinedDrawable;
        }
        this.locationButton.setBackgroundDrawable(drawable);
        this.locationButton.setImageResource(R.drawable.myloc_on);
        this.locationButton.setScaleType(ScaleType.CENTER);
        this.locationButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_profile_actionIcon), Mode.MULTIPLY));
        if (VERSION.SDK_INT >= 21) {
            StateListAnimator animator = new StateListAnimator();
            animator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.locationButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
            animator.addState(new int[0], ObjectAnimator.ofFloat(this.locationButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
            this.locationButton.setStateListAnimator(animator);
            this.locationButton.setOutlineProvider(new C30244());
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
        LayoutManager c30255 = new LinearLayoutManager(context, 1, false) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.layoutManager = c30255;
        recyclerListView.setLayoutManager(c30255);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setOnScrollListener(new C30276());
        this.listView.setOnItemClickListener(new C30297());
        this.adapter.setDelegate(new C30308());
        this.adapter.setOverScrollHeight(this.overScrollHeight);
        frameLayout.addView(this.mapViewClip, LayoutHelper.createFrame(-1, -1, 51));
        this.mapView = new MapView(context) {
            public boolean onInterceptTouchEvent(MotionEvent ev) {
                if (LocationActivity.this.messageObject == null) {
                    AnimatorSet access$2000;
                    Animator[] animatorArr;
                    if (ev.getAction() == 0) {
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
                    } else if (ev.getAction() == 1) {
                        if (LocationActivity.this.animatorSet != null) {
                            LocationActivity.this.animatorSet.cancel();
                        }
                        LocationActivity.this.animatorSet = new AnimatorSet();
                        LocationActivity.this.animatorSet.setDuration(200);
                        access$2000 = LocationActivity.this.animatorSet;
                        animatorArr = new Animator[2];
                        animatorArr[0] = ObjectAnimator.ofFloat(LocationActivity.this.markerImageView, "translationY", new float[]{(float) LocationActivity.this.markerTop});
                        animatorArr[1] = ObjectAnimator.ofFloat(LocationActivity.this.markerXImageView, "alpha", new float[]{0.0f});
                        access$2000.playTogether(animatorArr);
                        LocationActivity.this.animatorSet.start();
                    }
                    if (ev.getAction() == 2) {
                        if (!LocationActivity.this.userLocationMoved) {
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.setDuration(200);
                            animatorSet.play(ObjectAnimator.ofFloat(LocationActivity.this.locationButton, "alpha", new float[]{1.0f}));
                            animatorSet.start();
                            LocationActivity.this.userLocationMoved = true;
                        }
                        if (!(LocationActivity.this.googleMap == null || LocationActivity.this.userLocation == null)) {
                            LocationActivity.this.userLocation.setLatitude(LocationActivity.this.googleMap.getCameraPosition().target.latitude);
                            LocationActivity.this.userLocation.setLongitude(LocationActivity.this.googleMap.getCameraPosition().target.longitude);
                        }
                        LocationActivity.this.adapter.setCustomLocation(LocationActivity.this.userLocation);
                    }
                }
                return super.onInterceptTouchEvent(ev);
            }
        };
        final MapView map = this.mapView;
        new Thread(new Runnable() {

            /* renamed from: org.telegram.ui.LocationActivity$10$1 */
            class C30191 implements Runnable {

                /* renamed from: org.telegram.ui.LocationActivity$10$1$1 */
                class C30181 implements OnMapReadyCallback {
                    C30181() {
                    }

                    public void onMapReady(GoogleMap map) {
                        LocationActivity.this.googleMap = map;
                        LocationActivity.this.googleMap.setPadding(0, 0, AndroidUtilities.dp(70.0f), AndroidUtilities.dp(10.0f));
                        LocationActivity.this.onMapInit();
                    }
                }

                C30191() {
                }

                public void run() {
                    if (LocationActivity.this.mapView != null && LocationActivity.this.getParentActivity() != null) {
                        try {
                            map.onCreate(null);
                            MapsInitializer.initialize(LocationActivity.this.getParentActivity());
                            LocationActivity.this.mapView.getMapAsync(new C30181());
                            LocationActivity.this.mapsInitialized = true;
                            if (LocationActivity.this.onResumeCalled) {
                                LocationActivity.this.mapView.onResume();
                            }
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                }
            }

            public void run() {
                try {
                    map.onCreate(null);
                } catch (Exception e) {
                }
                AndroidUtilities.runOnUIThread(new C30191());
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
            this.markerXImageView.setAlpha(0.0f);
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
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == 1 && LocationActivity.this.searching && LocationActivity.this.searchWas) {
                        AndroidUtilities.hideKeyboard(LocationActivity.this.getParentActivity().getCurrentFocus());
                    }
                }
            });
            this.searchListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(View view, int position) {
                    TLRPC$TL_messageMediaVenue object = LocationActivity.this.searchAdapter.getItem(position);
                    if (!(object == null || LocationActivity.this.delegate == null)) {
                        LocationActivity.this.delegate.didSelectLocation(object, LocationActivity.this.liveLocationType);
                    }
                    LocationActivity.this.finishFragment();
                }
            });
        } else if (!this.messageObject.isLiveLocation()) {
            float f;
            int i;
            float f2;
            float f3;
            this.routeButton = new ImageView(context);
            drawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_chats_actionBackground), Theme.getColor(Theme.key_chats_actionPressedBackground));
            if (VERSION.SDK_INT < 21) {
                shadowDrawable = context.getResources().getDrawable(R.drawable.floating_shadow).mutate();
                shadowDrawable.setColorFilter(new PorterDuffColorFilter(-16777216, Mode.MULTIPLY));
                combinedDrawable = new CombinedDrawable(shadowDrawable, drawable, 0, 0);
                combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
                drawable = combinedDrawable;
            }
            this.routeButton.setBackgroundDrawable(drawable);
            this.routeButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), Mode.MULTIPLY));
            this.routeButton.setImageResource(R.drawable.navigate);
            this.routeButton.setScaleType(ScaleType.CENTER);
            if (VERSION.SDK_INT >= 21) {
                animator = new StateListAnimator();
                animator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.routeButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
                animator.addState(new int[0], ObjectAnimator.ofFloat(this.routeButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
                this.routeButton.setStateListAnimator(animator);
                this.routeButton.setOutlineProvider(new ViewOutlineProvider() {
                    @SuppressLint({"NewApi"})
                    public void getOutline(View view, Outline outline) {
                        outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
                    }
                });
            }
            View view2 = this.routeButton;
            int i2 = VERSION.SDK_INT >= 21 ? 56 : 60;
            if (VERSION.SDK_INT >= 21) {
                f = 56.0f;
            } else {
                f = 60.0f;
            }
            if (LocaleController.isRTL) {
                i = 3;
            } else {
                i = 5;
            }
            i |= 80;
            if (LocaleController.isRTL) {
                f2 = 14.0f;
            } else {
                f2 = 0.0f;
            }
            if (LocaleController.isRTL) {
                f3 = 0.0f;
            } else {
                f3 = 14.0f;
            }
            frameLayout.addView(view2, LayoutHelper.createFrame(i2, f, i, f2, 0.0f, f3, 37.0f));
            this.routeButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (VERSION.SDK_INT >= 23) {
                        Activity activity = LocationActivity.this.getParentActivity();
                        if (!(activity == null || activity.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0)) {
                            LocationActivity.this.showPermissionAlert(true);
                            return;
                        }
                    }
                    if (LocationActivity.this.myLocation != null) {
                        try {
                            LocationActivity.this.getParentActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format(Locale.US, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", new Object[]{Double.valueOf(LocationActivity.this.myLocation.getLatitude()), Double.valueOf(LocationActivity.this.myLocation.getLongitude()), Double.valueOf(LocationActivity.this.messageObject.messageOwner.media.geo.lat), Double.valueOf(LocationActivity.this.messageObject.messageOwner.media.geo._long)}))));
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                }
            });
            this.adapter.setMessageObject(this.messageObject);
        }
        if (this.messageObject == null || this.messageObject.isLiveLocation()) {
            this.mapViewClip.addView(this.locationButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, (LocaleController.isRTL ? 3 : 5) | 80, LocaleController.isRTL ? 14.0f : 0.0f, 0.0f, LocaleController.isRTL ? 0.0f : 14.0f, 14.0f));
        } else {
            this.mapViewClip.addView(this.locationButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, (LocaleController.isRTL ? 3 : 5) | 80, LocaleController.isRTL ? 14.0f : 0.0f, 0.0f, LocaleController.isRTL ? 0.0f : 14.0f, 43.0f));
        }
        this.locationButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (VERSION.SDK_INT >= 23) {
                    Activity activity = LocationActivity.this.getParentActivity();
                    if (!(activity == null || activity.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0)) {
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
                    animatorSet.play(ObjectAnimator.ofFloat(LocationActivity.this.locationButton, "alpha", new float[]{0.0f}));
                    animatorSet.start();
                    LocationActivity.this.adapter.setCustomLocation(null);
                    LocationActivity.this.userLocationMoved = false;
                    LocationActivity.this.googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(LocationActivity.this.myLocation.getLatitude(), LocationActivity.this.myLocation.getLongitude())));
                }
            }
        });
        if (this.messageObject == null) {
            this.locationButton.setAlpha(0.0f);
        }
        frameLayout.addView(this.actionBar);
        return this.fragmentView;
    }

    private Bitmap createUserBitmap(LiveLocation liveLocation) {
        Bitmap result = null;
        TLRPC$FileLocation photo = null;
        try {
            if (liveLocation.user != null && liveLocation.user.photo != null) {
                photo = liveLocation.user.photo.photo_small;
            } else if (!(liveLocation.chat == null || liveLocation.chat.photo == null)) {
                photo = liveLocation.chat.photo.photo_small;
            }
            result = Bitmap.createBitmap(AndroidUtilities.dp(62.0f), AndroidUtilities.dp(76.0f), Config.ARGB_8888);
            result.eraseColor(0);
            Canvas canvas = new Canvas(result);
            Drawable drawable = ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.livepin);
            drawable.setBounds(0, 0, AndroidUtilities.dp(62.0f), AndroidUtilities.dp(76.0f));
            drawable.draw(canvas);
            Paint roundPaint = new Paint(1);
            RectF bitmapRect = new RectF();
            canvas.save();
            if (photo != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(FileLoader.getPathToAttach(photo, true).toString());
                if (bitmap != null) {
                    Shader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                    Matrix matrix = new Matrix();
                    float scale = ((float) AndroidUtilities.dp(52.0f)) / ((float) bitmap.getWidth());
                    matrix.postTranslate((float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(5.0f));
                    matrix.postScale(scale, scale);
                    roundPaint.setShader(bitmapShader);
                    bitmapShader.setLocalMatrix(matrix);
                    bitmapRect.set((float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(57.0f), (float) AndroidUtilities.dp(57.0f));
                    canvas.drawRoundRect(bitmapRect, (float) AndroidUtilities.dp(26.0f), (float) AndroidUtilities.dp(26.0f), roundPaint);
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
        } catch (Throwable e2) {
            FileLog.e(e2);
        }
        return result;
    }

    private int getMessageId(TLRPC$Message message) {
        if (message.from_id != 0) {
            return message.from_id;
        }
        return (int) MessageObject.getDialogId(message);
    }

    private LiveLocation addUserMarker(TLRPC$Message message) {
        LatLng latLng = new LatLng(message.media.geo.lat, message.media.geo._long);
        LiveLocation liveLocation = (LiveLocation) this.markersMap.get(Integer.valueOf(message.from_id));
        if (liveLocation == null) {
            liveLocation = new LiveLocation();
            liveLocation.object = message;
            if (liveLocation.object.from_id != 0) {
                liveLocation.user = MessagesController.getInstance().getUser(Integer.valueOf(liveLocation.object.from_id));
                liveLocation.id = liveLocation.object.from_id;
            } else {
                int did = (int) MessageObject.getDialogId(message);
                if (did > 0) {
                    liveLocation.user = MessagesController.getInstance().getUser(Integer.valueOf(did));
                    liveLocation.id = did;
                } else {
                    liveLocation.chat = MessagesController.getInstance().getChat(Integer.valueOf(-did));
                    liveLocation.id = -did;
                }
            }
            try {
                MarkerOptions options = new MarkerOptions().position(latLng);
                Bitmap bitmap = createUserBitmap(liveLocation);
                if (bitmap != null) {
                    options.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    options.anchor(0.5f, 0.907f);
                    liveLocation.marker = this.googleMap.addMarker(options);
                    this.markers.add(liveLocation);
                    this.markersMap.put(Integer.valueOf(liveLocation.id), liveLocation);
                    SharingLocationInfo myInfo = LocationController.getInstance().getSharingLocationInfo(this.dialogId);
                    if (liveLocation.id == UserConfig.getClientUserId() && myInfo != null && liveLocation.object.id == myInfo.mid && this.myLocation != null) {
                        liveLocation.marker.setPosition(new LatLng(this.myLocation.getLatitude(), this.myLocation.getLongitude()));
                    }
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
        } else {
            liveLocation.object = message;
            liveLocation.marker.setPosition(latLng);
        }
        return liveLocation;
    }

    private void onMapInit() {
        if (this.googleMap != null) {
            if (this.messageObject == null) {
                this.userLocation = new Location("network");
                this.userLocation.setLatitude(20.659322d);
                this.userLocation.setLongitude(-11.40625d);
            } else if (this.messageObject.isLiveLocation()) {
                LiveLocation liveLocation = addUserMarker(this.messageObject.messageOwner);
                if (!getRecentLocations()) {
                    this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(liveLocation.marker.getPosition(), this.googleMap.getMaxZoomLevel() - 4.0f));
                }
            } else {
                LatLng latLng = new LatLng(this.userLocation.getLatitude(), this.userLocation.getLongitude());
                try {
                    this.googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin)));
                } catch (Exception e) {
                    FileLog.e(e);
                }
                this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, this.googleMap.getMaxZoomLevel() - 4.0f));
                this.firstFocus = false;
                getRecentLocations();
            }
            try {
                this.googleMap.setMyLocationEnabled(true);
            } catch (Exception e2) {
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
                    if (!((LocationManager) ApplicationLoader.applicationContext.getSystemService(Param.LOCATION)).isProviderEnabled("gps")) {
                        Builder builder = new Builder(getParentActivity());
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("GpsDisabledAlert", R.string.GpsDisabledAlert));
                        builder.setPositiveButton(LocaleController.getString("ConnectingToProxyEnable", R.string.ConnectingToProxyEnable), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
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
                } catch (Exception e22) {
                    FileLog.e(e22);
                }
            }
        }
    }

    private void showPermissionAlert(boolean byButton) {
        if (getParentActivity() != null) {
            Builder builder = new Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            if (byButton) {
                builder.setMessage(LocaleController.getString("PermissionNoLocationPosition", R.string.PermissionNoLocationPosition));
            } else {
                builder.setMessage(LocaleController.getString("PermissionNoLocation", R.string.PermissionNoLocation));
            }
            builder.setNegativeButton(LocaleController.getString("PermissionOpenSettings", R.string.PermissionOpenSettings), new DialogInterface.OnClickListener() {
                @TargetApi(9)
                public void onClick(DialogInterface dialog, int which) {
                    if (LocationActivity.this.getParentActivity() != null) {
                        try {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
                            LocationActivity.this.getParentActivity().startActivity(intent);
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                }
            });
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            showDialog(builder.create());
        }
    }

    public void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if (isOpen) {
            try {
                if (this.mapView.getParent() instanceof ViewGroup) {
                    ((ViewGroup) this.mapView.getParent()).removeView(this.mapView);
                }
            } catch (Exception e) {
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

    private void updateClipView(int firstVisibleItem) {
        if (firstVisibleItem != -1) {
            int height = 0;
            int top = 0;
            View child = this.listView.getChildAt(0);
            if (child != null) {
                int i;
                if (firstVisibleItem == 0) {
                    int i2;
                    top = child.getTop();
                    i = this.overScrollHeight;
                    if (top < 0) {
                        i2 = top;
                    } else {
                        i2 = 0;
                    }
                    height = i + i2;
                }
                if (((LayoutParams) this.mapViewClip.getLayoutParams()) != null) {
                    if (height <= 0) {
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
                        i = ((-top) - AndroidUtilities.dp(42.0f)) + (height / 2);
                        this.markerTop = i;
                        imageView.setTranslationY((float) i);
                        this.markerXImageView.setTranslationY((float) (((-top) - AndroidUtilities.dp(7.0f)) + (height / 2)));
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

    private void fixLayoutInternal(boolean resume) {
        if (this.listView != null) {
            int height = (this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight();
            int viewHeight = this.fragmentView.getMeasuredHeight();
            if (viewHeight != 0) {
                this.overScrollHeight = (viewHeight - AndroidUtilities.dp(66.0f)) - height;
                LayoutParams layoutParams = (LayoutParams) this.listView.getLayoutParams();
                layoutParams.topMargin = height;
                this.listView.setLayoutParams(layoutParams);
                layoutParams = (LayoutParams) this.mapViewClip.getLayoutParams();
                layoutParams.topMargin = height;
                layoutParams.height = this.overScrollHeight;
                this.mapViewClip.setLayoutParams(layoutParams);
                if (this.searchListView != null) {
                    layoutParams = (LayoutParams) this.searchListView.getLayoutParams();
                    layoutParams.topMargin = height;
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
                if (resume) {
                    int i;
                    LinearLayoutManager linearLayoutManager = this.layoutManager;
                    if (this.liveLocationType == 1 || this.liveLocationType == 2) {
                        i = 66;
                    } else {
                        i = 0;
                    }
                    linearLayoutManager.scrollToPositionWithOffset(0, -AndroidUtilities.dp((float) (i + 32)));
                    updateClipView(this.layoutManager.findFirstVisibleItemPosition());
                    this.listView.post(new Runnable() {
                        public void run() {
                            int i;
                            LinearLayoutManager access$1300 = LocationActivity.this.layoutManager;
                            if (LocationActivity.this.liveLocationType == 1 || LocationActivity.this.liveLocationType == 2) {
                                i = 66;
                            } else {
                                i = 0;
                            }
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
        LocationManager lm = (LocationManager) ApplicationLoader.applicationContext.getSystemService(Param.LOCATION);
        List<String> providers = lm.getProviders(true);
        Location l = null;
        for (int i = providers.size() - 1; i >= 0; i--) {
            l = lm.getLastKnownLocation((String) providers.get(i));
            if (l != null) {
                break;
            }
        }
        return l;
    }

    private void positionMarker(Location location) {
        if (location != null) {
            this.myLocation = new Location(location);
            LiveLocation liveLocation = (LiveLocation) this.markersMap.get(Integer.valueOf(UserConfig.getClientUserId()));
            SharingLocationInfo myInfo = LocationController.getInstance().getSharingLocationInfo(this.dialogId);
            if (!(liveLocation == null || myInfo == null || liveLocation.object.id != myInfo.mid)) {
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

    public void setMessageObject(MessageObject message) {
        this.messageObject = message;
        this.dialogId = this.messageObject.getDialogId();
    }

    public void setDialogId(long did) {
        this.dialogId = did;
    }

    private void fetchRecentLocations(ArrayList<TLRPC$Message> messages) {
        LatLngBounds.Builder builder = null;
        if (this.firstFocus) {
            builder = new LatLngBounds.Builder();
        }
        int date = ConnectionsManager.getInstance().getCurrentTime();
        for (int a = 0; a < messages.size(); a++) {
            TLRPC$Message message = (TLRPC$Message) messages.get(a);
            if (message.date + message.media.period > date) {
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
                    LatLngBounds bounds = builder.build();
                    if (messages.size() > 1) {
                        try {
                            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, AndroidUtilities.dp(60.0f)));
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                } catch (Exception e2) {
                }
            }
        }
    }

    private boolean getRecentLocations() {
        ArrayList<TLRPC$Message> messages = (ArrayList) LocationController.getInstance().locationsCache.get(Long.valueOf(this.messageObject.getDialogId()));
        if (messages == null || !messages.isEmpty()) {
            messages = null;
        } else {
            fetchRecentLocations(messages);
        }
        TLRPC$TL_messages_getRecentLocations req = new TLRPC$TL_messages_getRecentLocations();
        final long dialog_id = this.messageObject.getDialogId();
        req.peer = MessagesController.getInputPeer((int) dialog_id);
        req.limit = 100;
        ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
            public void run(final TLObject response, TLRPC$TL_error error) {
                if (response != null) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (LocationActivity.this.googleMap != null) {
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
                                LocationController.getInstance().locationsCache.put(Long.valueOf(dialog_id), res.messages);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.liveLocationsCacheChanged, new Object[]{Long.valueOf(dialog_id)});
                                LocationActivity.this.fetchRecentLocations(res.messages);
                            }
                        }
                    });
                }
            }
        });
        if (messages != null) {
            return true;
        }
        return false;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.closeChats) {
            removeSelfFromStack();
        } else if (id == NotificationCenter.locationPermissionGranted) {
            if (this.googleMap != null) {
                try {
                    this.googleMap.setMyLocationEnabled(true);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        } else if (id == NotificationCenter.didReceivedNewMessages) {
            if (((Long) args[0]).longValue() == this.dialogId && this.messageObject != null) {
                ArrayList<MessageObject> arr = args[1];
                boolean added = false;
                for (a = 0; a < arr.size(); a++) {
                    messageObject = (MessageObject) arr.get(a);
                    if (messageObject.isLiveLocation()) {
                        addUserMarker(messageObject.messageOwner);
                        added = true;
                    }
                }
                if (added && this.adapter != null) {
                    this.adapter.setLiveLocations(this.markers);
                }
            }
        } else if (id != NotificationCenter.messagesDeleted && id == NotificationCenter.replaceMessagesObjects) {
            long did = ((Long) args[0]).longValue();
            if (did == this.dialogId && this.messageObject != null) {
                boolean updated = false;
                ArrayList<MessageObject> messageObjects = args[1];
                for (a = 0; a < messageObjects.size(); a++) {
                    messageObject = (MessageObject) messageObjects.get(a);
                    if (messageObject.isLiveLocation()) {
                        LiveLocation liveLocation = (LiveLocation) this.markersMap.get(Integer.valueOf(getMessageId(messageObject.messageOwner)));
                        if (liveLocation != null) {
                            SharingLocationInfo myInfo = LocationController.getInstance().getSharingLocationInfo(did);
                            if (myInfo == null || myInfo.mid != messageObject.getId()) {
                                liveLocation.marker.setPosition(new LatLng(messageObject.messageOwner.media.geo.lat, messageObject.messageOwner.media.geo._long));
                            }
                            updated = true;
                        }
                    }
                }
                if (updated && this.adapter != null) {
                    this.adapter.updateLiveLocations();
                }
            }
        }
    }

    public void onPause() {
        super.onPause();
        if (this.mapView != null && this.mapsInitialized) {
            try {
                this.mapView.onPause();
            } catch (Exception e) {
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
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
        this.onResumeCalled = true;
        if (this.googleMap != null) {
            try {
                this.googleMap.setMyLocationEnabled(true);
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }
        fixLayoutInternal(true);
        if (this.checkPermission && VERSION.SDK_INT >= 23) {
            Activity activity = getParentActivity();
            if (activity != null) {
                this.checkPermission = false;
                if (activity.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    activity.requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, 2);
                }
            }
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        if (this.mapView != null && this.mapsInitialized) {
            this.mapView.onLowMemory();
        }
    }

    public void setDelegate(LocationActivityDelegate delegate) {
        this.delegate = delegate;
    }

    private void updateSearchInterface() {
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new ThemeDescriptionDelegate() {
            public void didSetColor(int color) {
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
        themeDescriptionArr[23] = new ThemeDescription(null, 0, null, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, сellDelegate, Theme.key_avatar_text);
        themeDescriptionArr[24] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[25] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[26] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[27] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[28] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[29] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[30] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundPink);
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
}
