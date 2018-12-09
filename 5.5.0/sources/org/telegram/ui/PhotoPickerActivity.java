package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.ir.talaeii.R;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.AlbumEntry;
import org.telegram.messenger.MediaController.PhotoEntry;
import org.telegram.messenger.MediaController.SearchImage;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.support.widget.GridLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_foundGifs;
import org.telegram.tgnet.TLRPC$TL_messages_searchGifs;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.FoundGif;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.PhotoPickerPhotoCell;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.PickerBottomLayout;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PlaceProviderObject;

public class PhotoPickerActivity extends BaseFragment implements NotificationCenterDelegate {
    private boolean allowCaption = true;
    private boolean allowIndices;
    private boolean bingSearchEndReached = true;
    private ChatActivity chatActivity;
    private AsyncTask<Void, Void, JSONObject> currentBingTask;
    private PhotoPickerActivityDelegate delegate;
    private EmptyTextProgressView emptyView;
    private FrameLayout frameLayout;
    private int giphyReqId;
    private boolean giphySearchEndReached = true;
    private AnimatorSet hintAnimation;
    private Runnable hintHideRunnable;
    private TextView hintTextView;
    private ImageView imageOrderToggleButton;
    private int itemWidth = 100;
    private String lastSearchString;
    private int lastSearchToken;
    private GridLayoutManager layoutManager;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private boolean loadingRecent;
    private int nextGiphySearchOffset;
    private PickerBottomLayout pickerBottomLayout;
    private PhotoViewerProvider provider = new C50341();
    private ArrayList<SearchImage> recentImages;
    private ActionBarMenuItem searchItem;
    private ArrayList<SearchImage> searchResult = new ArrayList();
    private HashMap<String, SearchImage> searchResultKeys = new HashMap();
    private HashMap<String, SearchImage> searchResultUrls = new HashMap();
    private boolean searching;
    private AlbumEntry selectedAlbum;
    private HashMap<Object, Object> selectedPhotos;
    private ArrayList<Object> selectedPhotosOrder;
    private boolean sendPressed;
    private boolean singlePhoto;
    private int type;

    public interface PhotoPickerActivityDelegate {
        void actionButtonPressed(boolean z);

        void selectedPhotosChanged();
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$1 */
    class C50341 extends EmptyPhotoViewerProvider {
        C50341() {
        }

        public boolean allowCaption() {
            return PhotoPickerActivity.this.allowCaption;
        }

        public boolean allowGroupPhotos() {
            return PhotoPickerActivity.this.imageOrderToggleButton != null;
        }

        public boolean cancelButtonPressed() {
            PhotoPickerActivity.this.delegate.actionButtonPressed(true);
            PhotoPickerActivity.this.finishFragment();
            return true;
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            PhotoPickerPhotoCell access$000 = PhotoPickerActivity.this.getCellForIndex(i);
            if (access$000 == null) {
                return null;
            }
            int[] iArr = new int[2];
            access$000.photoImage.getLocationInWindow(iArr);
            PlaceProviderObject placeProviderObject = new PlaceProviderObject();
            placeProviderObject.viewX = iArr[0];
            placeProviderObject.viewY = iArr[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight);
            placeProviderObject.parentView = PhotoPickerActivity.this.listView;
            placeProviderObject.imageReceiver = access$000.photoImage.getImageReceiver();
            placeProviderObject.thumb = placeProviderObject.imageReceiver.getBitmap();
            placeProviderObject.scale = access$000.photoImage.getScaleX();
            access$000.showCheck(false);
            return placeProviderObject;
        }

        public int getSelectedCount() {
            return PhotoPickerActivity.this.selectedPhotos.size();
        }

        public HashMap<Object, Object> getSelectedPhotos() {
            return PhotoPickerActivity.this.selectedPhotos;
        }

        public ArrayList<Object> getSelectedPhotosOrder() {
            return PhotoPickerActivity.this.selectedPhotosOrder;
        }

        public Bitmap getThumbForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            PhotoPickerPhotoCell access$000 = PhotoPickerActivity.this.getCellForIndex(i);
            return access$000 != null ? access$000.photoImage.getImageReceiver().getBitmap() : null;
        }

        public boolean isPhotoChecked(int i) {
            boolean z = true;
            if (PhotoPickerActivity.this.selectedAlbum != null) {
                return i >= 0 && i < PhotoPickerActivity.this.selectedAlbum.photos.size() && PhotoPickerActivity.this.selectedPhotos.containsKey(Integer.valueOf(((PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(i)).imageId));
            } else {
                ArrayList access$500 = (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) ? PhotoPickerActivity.this.recentImages : PhotoPickerActivity.this.searchResult;
                if (i < 0 || i >= access$500.size() || !PhotoPickerActivity.this.selectedPhotos.containsKey(((SearchImage) access$500.get(i)).id)) {
                    z = false;
                }
                return z;
            }
        }

        public boolean scaleToFill() {
            return false;
        }

        public void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo) {
            if (PhotoPickerActivity.this.selectedPhotos.isEmpty()) {
                if (PhotoPickerActivity.this.selectedAlbum == null) {
                    ArrayList access$500 = (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) ? PhotoPickerActivity.this.recentImages : PhotoPickerActivity.this.searchResult;
                    if (i >= 0 && i < access$500.size()) {
                        PhotoPickerActivity.this.addToSelectedPhotos(access$500.get(i), -1);
                    } else {
                        return;
                    }
                } else if (i >= 0 && i < PhotoPickerActivity.this.selectedAlbum.photos.size()) {
                    PhotoEntry photoEntry = (PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(i);
                    photoEntry.editedInfo = videoEditedInfo;
                    PhotoPickerActivity.this.addToSelectedPhotos(photoEntry, -1);
                } else {
                    return;
                }
            }
            PhotoPickerActivity.this.sendSelectedPhotos();
        }

        public int setPhotoChecked(int i, VideoEditedInfo videoEditedInfo) {
            int indexOf;
            boolean z;
            int access$800;
            if (PhotoPickerActivity.this.selectedAlbum == null) {
                ArrayList access$500 = (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) ? PhotoPickerActivity.this.recentImages : PhotoPickerActivity.this.searchResult;
                if (i < 0 || i >= access$500.size()) {
                    return -1;
                }
                SearchImage searchImage = (SearchImage) access$500.get(i);
                access$800 = PhotoPickerActivity.this.addToSelectedPhotos(searchImage, -1);
                if (access$800 == -1) {
                    indexOf = PhotoPickerActivity.this.selectedPhotosOrder.indexOf(searchImage.id);
                    z = true;
                } else {
                    indexOf = access$800;
                    z = false;
                }
            } else if (i < 0 || i >= PhotoPickerActivity.this.selectedAlbum.photos.size()) {
                return -1;
            } else {
                int indexOf2;
                boolean z2;
                PhotoEntry photoEntry = (PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(i);
                access$800 = PhotoPickerActivity.this.addToSelectedPhotos(photoEntry, -1);
                if (access$800 == -1) {
                    photoEntry.editedInfo = videoEditedInfo;
                    indexOf2 = PhotoPickerActivity.this.selectedPhotosOrder.indexOf(Integer.valueOf(photoEntry.imageId));
                    z2 = true;
                } else {
                    photoEntry.editedInfo = null;
                    indexOf2 = access$800;
                    z2 = false;
                }
                indexOf = indexOf2;
                z = z2;
            }
            int childCount = PhotoPickerActivity.this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = PhotoPickerActivity.this.listView.getChildAt(i2);
                if (((Integer) childAt.getTag()).intValue() == i) {
                    ((PhotoPickerPhotoCell) childAt).setChecked(PhotoPickerActivity.this.allowIndices ? indexOf : -1, z, false);
                    PhotoPickerActivity.this.pickerBottomLayout.updateSelectedCount(PhotoPickerActivity.this.selectedPhotos.size(), true);
                    PhotoPickerActivity.this.delegate.selectedPhotosChanged();
                    return indexOf;
                }
            }
            PhotoPickerActivity.this.pickerBottomLayout.updateSelectedCount(PhotoPickerActivity.this.selectedPhotos.size(), true);
            PhotoPickerActivity.this.delegate.selectedPhotosChanged();
            return indexOf;
        }

        public void toggleGroupPhotosEnabled() {
            if (PhotoPickerActivity.this.imageOrderToggleButton != null) {
                PhotoPickerActivity.this.imageOrderToggleButton.setColorFilter(MediaController.getInstance().isGroupPhotosEnabled() ? new PorterDuffColorFilter(-10043398, Mode.MULTIPLY) : null);
            }
        }

        public void updatePhotoAtIndex(int i) {
            PhotoPickerPhotoCell access$000 = PhotoPickerActivity.this.getCellForIndex(i);
            if (access$000 == null) {
                return;
            }
            if (PhotoPickerActivity.this.selectedAlbum != null) {
                access$000.photoImage.setOrientation(0, true);
                PhotoEntry photoEntry = (PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(i);
                if (photoEntry.thumbPath != null) {
                    access$000.photoImage.setImage(photoEntry.thumbPath, null, access$000.getContext().getResources().getDrawable(R.drawable.nophotos));
                    return;
                } else if (photoEntry.path != null) {
                    access$000.photoImage.setOrientation(photoEntry.orientation, true);
                    if (photoEntry.isVideo) {
                        access$000.photoImage.setImage("vthumb://" + photoEntry.imageId + ":" + photoEntry.path, null, access$000.getContext().getResources().getDrawable(R.drawable.nophotos));
                        return;
                    } else {
                        access$000.photoImage.setImage("thumb://" + photoEntry.imageId + ":" + photoEntry.path, null, access$000.getContext().getResources().getDrawable(R.drawable.nophotos));
                        return;
                    }
                } else {
                    access$000.photoImage.setImageResource(R.drawable.nophotos);
                    return;
                }
            }
            ArrayList access$500 = (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) ? PhotoPickerActivity.this.recentImages : PhotoPickerActivity.this.searchResult;
            SearchImage searchImage = (SearchImage) access$500.get(i);
            if (searchImage.document != null && searchImage.document.thumb != null) {
                access$000.photoImage.setImage(searchImage.document.thumb.location, null, access$000.getContext().getResources().getDrawable(R.drawable.nophotos));
            } else if (searchImage.thumbPath != null) {
                access$000.photoImage.setImage(searchImage.thumbPath, null, access$000.getContext().getResources().getDrawable(R.drawable.nophotos));
            } else if (searchImage.thumbUrl == null || searchImage.thumbUrl.length() <= 0) {
                access$000.photoImage.setImageResource(R.drawable.nophotos);
            } else {
                access$000.photoImage.setImage(searchImage.thumbUrl, null, access$000.getContext().getResources().getDrawable(R.drawable.nophotos));
            }
        }

        public void willHidePhotoViewer() {
            int childCount = PhotoPickerActivity.this.listView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = PhotoPickerActivity.this.listView.getChildAt(i);
                if (childAt instanceof PhotoPickerPhotoCell) {
                    ((PhotoPickerPhotoCell) childAt).showCheck(true);
                }
            }
        }

        public void willSwitchFromPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            int childCount = PhotoPickerActivity.this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = PhotoPickerActivity.this.listView.getChildAt(i2);
                if (childAt.getTag() != null) {
                    PhotoPickerPhotoCell photoPickerPhotoCell = (PhotoPickerPhotoCell) childAt;
                    int intValue = ((Integer) childAt.getTag()).intValue();
                    if (PhotoPickerActivity.this.selectedAlbum == null) {
                        ArrayList access$500 = (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) ? PhotoPickerActivity.this.recentImages : PhotoPickerActivity.this.searchResult;
                        if (intValue < 0) {
                            continue;
                        } else if (intValue >= access$500.size()) {
                        }
                    } else if (intValue < 0) {
                        continue;
                    } else if (intValue >= PhotoPickerActivity.this.selectedAlbum.photos.size()) {
                        continue;
                    }
                    if (intValue == i) {
                        photoPickerPhotoCell.showCheck(true);
                        return;
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$2 */
    class C50352 extends ActionBarMenuOnItemClick {
        C50352() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                PhotoPickerActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$3 */
    class C50363 extends ActionBarMenuItemSearchListener {
        C50363() {
        }

        public boolean canCollapseSearch() {
            PhotoPickerActivity.this.finishFragment();
            return false;
        }

        public void onSearchExpand() {
        }

        public void onSearchPressed(EditText editText) {
            if (editText.getText().toString().length() != 0) {
                PhotoPickerActivity.this.searchResult.clear();
                PhotoPickerActivity.this.searchResultKeys.clear();
                PhotoPickerActivity.this.bingSearchEndReached = true;
                PhotoPickerActivity.this.giphySearchEndReached = true;
                if (PhotoPickerActivity.this.type == 0) {
                    PhotoPickerActivity.this.searchBingImages(editText.getText().toString(), 0, 53);
                } else if (PhotoPickerActivity.this.type == 1) {
                    PhotoPickerActivity.this.nextGiphySearchOffset = 0;
                    PhotoPickerActivity.this.searchGiphyImages(editText.getText().toString(), 0);
                }
                PhotoPickerActivity.this.lastSearchString = editText.getText().toString();
                if (PhotoPickerActivity.this.lastSearchString.length() == 0) {
                    PhotoPickerActivity.this.lastSearchString = null;
                    if (PhotoPickerActivity.this.type == 0) {
                        PhotoPickerActivity.this.emptyView.setText(LocaleController.getString("NoRecentPhotos", R.string.NoRecentPhotos));
                    } else if (PhotoPickerActivity.this.type == 1) {
                        PhotoPickerActivity.this.emptyView.setText(LocaleController.getString("NoRecentGIFs", R.string.NoRecentGIFs));
                    }
                } else {
                    PhotoPickerActivity.this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
                }
                PhotoPickerActivity.this.updateSearchInterface();
            }
        }

        public void onTextChanged(EditText editText) {
            if (editText.getText().length() == 0) {
                PhotoPickerActivity.this.searchResult.clear();
                PhotoPickerActivity.this.searchResultKeys.clear();
                PhotoPickerActivity.this.lastSearchString = null;
                PhotoPickerActivity.this.bingSearchEndReached = true;
                PhotoPickerActivity.this.giphySearchEndReached = true;
                PhotoPickerActivity.this.searching = false;
                if (PhotoPickerActivity.this.currentBingTask != null) {
                    PhotoPickerActivity.this.currentBingTask.cancel(true);
                    PhotoPickerActivity.this.currentBingTask = null;
                }
                if (PhotoPickerActivity.this.giphyReqId != 0) {
                    ConnectionsManager.getInstance().cancelRequest(PhotoPickerActivity.this.giphyReqId, true);
                    PhotoPickerActivity.this.giphyReqId = 0;
                }
                if (PhotoPickerActivity.this.type == 0) {
                    PhotoPickerActivity.this.emptyView.setText(LocaleController.getString("NoRecentPhotos", R.string.NoRecentPhotos));
                } else if (PhotoPickerActivity.this.type == 1) {
                    PhotoPickerActivity.this.emptyView.setText(LocaleController.getString("NoRecentGIFs", R.string.NoRecentGIFs));
                }
                PhotoPickerActivity.this.updateSearchInterface();
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$5 */
    class C50385 extends ItemDecoration {
        C50385() {
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            int i = 0;
            super.getItemOffsets(rect, view, recyclerView, state);
            int itemCount = state.getItemCount();
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            int spanCount = PhotoPickerActivity.this.layoutManager.getSpanCount();
            int ceil = (int) Math.ceil((double) (((float) itemCount) / ((float) spanCount)));
            int i2 = childAdapterPosition / spanCount;
            rect.right = childAdapterPosition % spanCount != spanCount + -1 ? AndroidUtilities.dp(4.0f) : 0;
            if (i2 != ceil - 1) {
                i = AndroidUtilities.dp(4.0f);
            }
            rect.bottom = i;
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$6 */
    class C50396 implements OnItemClickListener {
        C50396() {
        }

        public void onItemClick(View view, int i) {
            ArrayList access$500 = PhotoPickerActivity.this.selectedAlbum != null ? PhotoPickerActivity.this.selectedAlbum.photos : (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) ? PhotoPickerActivity.this.recentImages : PhotoPickerActivity.this.searchResult;
            if (i >= 0 && i < access$500.size()) {
                if (PhotoPickerActivity.this.searchItem != null) {
                    AndroidUtilities.hideKeyboard(PhotoPickerActivity.this.searchItem.getSearchField());
                }
                PhotoViewer.getInstance().setParentActivity(PhotoPickerActivity.this.getParentActivity());
                PhotoViewer.getInstance().openPhotoForSelect(access$500, i, PhotoPickerActivity.this.singlePhoto ? 1 : 0, PhotoPickerActivity.this.provider, PhotoPickerActivity.this.chatActivity);
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$7 */
    class C50417 implements OnItemLongClickListener {

        /* renamed from: org.telegram.ui.PhotoPickerActivity$7$1 */
        class C50401 implements OnClickListener {
            C50401() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                PhotoPickerActivity.this.recentImages.clear();
                if (PhotoPickerActivity.this.listAdapter != null) {
                    PhotoPickerActivity.this.listAdapter.notifyDataSetChanged();
                }
                MessagesStorage.getInstance().clearWebRecent(PhotoPickerActivity.this.type);
            }
        }

        C50417() {
        }

        public boolean onItemClick(View view, int i) {
            if (!PhotoPickerActivity.this.searchResult.isEmpty() || PhotoPickerActivity.this.lastSearchString != null) {
                return false;
            }
            Builder builder = new Builder(PhotoPickerActivity.this.getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setMessage(LocaleController.getString("ClearSearch", R.string.ClearSearch));
            builder.setPositiveButton(LocaleController.getString("ClearButton", R.string.ClearButton).toUpperCase(), new C50401());
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            PhotoPickerActivity.this.showDialog(builder.create());
            return true;
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$8 */
    class C50428 extends OnScrollListener {
        C50428() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 1) {
                AndroidUtilities.hideKeyboard(PhotoPickerActivity.this.getParentActivity().getCurrentFocus());
            }
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            int findFirstVisibleItemPosition = PhotoPickerActivity.this.layoutManager.findFirstVisibleItemPosition();
            int abs = findFirstVisibleItemPosition == -1 ? 0 : Math.abs(PhotoPickerActivity.this.layoutManager.findLastVisibleItemPosition() - findFirstVisibleItemPosition) + 1;
            if (abs > 0) {
                int itemCount = PhotoPickerActivity.this.layoutManager.getItemCount();
                if (abs != 0 && abs + findFirstVisibleItemPosition > itemCount - 2 && !PhotoPickerActivity.this.searching) {
                    if (PhotoPickerActivity.this.type == 0 && !PhotoPickerActivity.this.bingSearchEndReached) {
                        PhotoPickerActivity.this.searchBingImages(PhotoPickerActivity.this.lastSearchString, PhotoPickerActivity.this.searchResult.size(), 54);
                    } else if (PhotoPickerActivity.this.type == 1 && !PhotoPickerActivity.this.giphySearchEndReached) {
                        PhotoPickerActivity.this.searchGiphyImages(PhotoPickerActivity.this.searchItem.getSearchField().getText().toString(), PhotoPickerActivity.this.nextGiphySearchOffset);
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$9 */
    class C50439 implements View.OnClickListener {
        C50439() {
        }

        public void onClick(View view) {
            PhotoPickerActivity.this.delegate.actionButtonPressed(true);
            PhotoPickerActivity.this.finishFragment();
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.PhotoPickerActivity$ListAdapter$1 */
        class C50441 implements View.OnClickListener {
            C50441() {
            }

            public void onClick(View view) {
                boolean z = false;
                int i = -1;
                int intValue = ((Integer) ((View) view.getParent()).getTag()).intValue();
                if (PhotoPickerActivity.this.selectedAlbum != null) {
                    PhotoEntry photoEntry = (PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(intValue);
                    boolean z2 = !PhotoPickerActivity.this.selectedPhotos.containsKey(Integer.valueOf(photoEntry.imageId));
                    if (PhotoPickerActivity.this.allowIndices && z2) {
                        i = PhotoPickerActivity.this.selectedPhotosOrder.size();
                    }
                    ((PhotoPickerPhotoCell) view.getParent()).setChecked(i, z2, true);
                    PhotoPickerActivity.this.addToSelectedPhotos(photoEntry, intValue);
                } else {
                    AndroidUtilities.hideKeyboard(PhotoPickerActivity.this.getParentActivity().getCurrentFocus());
                    SearchImage searchImage = (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) ? (SearchImage) PhotoPickerActivity.this.recentImages.get(((Integer) ((View) view.getParent()).getTag()).intValue()) : (SearchImage) PhotoPickerActivity.this.searchResult.get(((Integer) ((View) view.getParent()).getTag()).intValue());
                    if (!PhotoPickerActivity.this.selectedPhotos.containsKey(searchImage.id)) {
                        z = true;
                    }
                    if (PhotoPickerActivity.this.allowIndices && z) {
                        i = PhotoPickerActivity.this.selectedPhotosOrder.size();
                    }
                    ((PhotoPickerPhotoCell) view.getParent()).setChecked(i, z, true);
                    PhotoPickerActivity.this.addToSelectedPhotos(searchImage, intValue);
                }
                PhotoPickerActivity.this.pickerBottomLayout.updateSelectedCount(PhotoPickerActivity.this.selectedPhotos.size(), true);
                PhotoPickerActivity.this.delegate.selectedPhotosChanged();
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            int i = 0;
            if (PhotoPickerActivity.this.selectedAlbum == null) {
                if (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) {
                    return PhotoPickerActivity.this.recentImages.size();
                }
                int size;
                if (PhotoPickerActivity.this.type == 0) {
                    size = PhotoPickerActivity.this.searchResult.size();
                    if (!PhotoPickerActivity.this.bingSearchEndReached) {
                        i = 1;
                    }
                    return i + size;
                } else if (PhotoPickerActivity.this.type == 1) {
                    size = PhotoPickerActivity.this.searchResult.size();
                    if (!PhotoPickerActivity.this.giphySearchEndReached) {
                        i = 1;
                    }
                    return i + size;
                }
            }
            return PhotoPickerActivity.this.selectedAlbum.photos.size();
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public int getItemViewType(int i) {
            return (PhotoPickerActivity.this.selectedAlbum != null || ((PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null && i < PhotoPickerActivity.this.recentImages.size()) || i < PhotoPickerActivity.this.searchResult.size())) ? 0 : 1;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            if (PhotoPickerActivity.this.selectedAlbum != null) {
                return true;
            }
            int adapterPosition = viewHolder.getAdapterPosition();
            return (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) ? adapterPosition < PhotoPickerActivity.this.recentImages.size() : adapterPosition < PhotoPickerActivity.this.searchResult.size();
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            int i2 = -1;
            int i3 = 0;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    boolean isShowingImage;
                    PhotoPickerPhotoCell photoPickerPhotoCell = (PhotoPickerPhotoCell) viewHolder.itemView;
                    photoPickerPhotoCell.itemWidth = PhotoPickerActivity.this.itemWidth;
                    BackupImageView backupImageView = photoPickerPhotoCell.photoImage;
                    backupImageView.setTag(Integer.valueOf(i));
                    photoPickerPhotoCell.setTag(Integer.valueOf(i));
                    backupImageView.setOrientation(0, true);
                    if (PhotoPickerActivity.this.selectedAlbum != null) {
                        PhotoEntry photoEntry = (PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(i);
                        if (photoEntry.thumbPath != null) {
                            backupImageView.setImage(photoEntry.thumbPath, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                        } else if (photoEntry.path != null) {
                            backupImageView.setOrientation(photoEntry.orientation, true);
                            if (photoEntry.isVideo) {
                                photoPickerPhotoCell.videoInfoContainer.setVisibility(0);
                                int i4 = photoEntry.duration - ((photoEntry.duration / 60) * 60);
                                photoPickerPhotoCell.videoTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(r6), Integer.valueOf(i4)}));
                                backupImageView.setImage("vthumb://" + photoEntry.imageId + ":" + photoEntry.path, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                            } else {
                                photoPickerPhotoCell.videoInfoContainer.setVisibility(4);
                                backupImageView.setImage("thumb://" + photoEntry.imageId + ":" + photoEntry.path, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                            }
                        } else {
                            backupImageView.setImageResource(R.drawable.nophotos);
                        }
                        if (PhotoPickerActivity.this.allowIndices) {
                            i2 = PhotoPickerActivity.this.selectedPhotosOrder.indexOf(Integer.valueOf(photoEntry.imageId));
                        }
                        photoPickerPhotoCell.setChecked(i2, PhotoPickerActivity.this.selectedPhotos.containsKey(Integer.valueOf(photoEntry.imageId)), false);
                        isShowingImage = PhotoViewer.getInstance().isShowingImage(photoEntry.path);
                    } else {
                        SearchImage searchImage = (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) ? (SearchImage) PhotoPickerActivity.this.recentImages.get(i) : (SearchImage) PhotoPickerActivity.this.searchResult.get(i);
                        if (searchImage.thumbPath != null) {
                            backupImageView.setImage(searchImage.thumbPath, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                        } else if (searchImage.thumbUrl != null && searchImage.thumbUrl.length() > 0) {
                            backupImageView.setImage(searchImage.thumbUrl, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                        } else if (searchImage.document == null || searchImage.document.thumb == null) {
                            backupImageView.setImageResource(R.drawable.nophotos);
                        } else {
                            backupImageView.setImage(searchImage.document.thumb.location, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                        }
                        photoPickerPhotoCell.videoInfoContainer.setVisibility(4);
                        if (PhotoPickerActivity.this.allowIndices) {
                            i2 = PhotoPickerActivity.this.selectedPhotosOrder.indexOf(searchImage.id);
                        }
                        photoPickerPhotoCell.setChecked(i2, PhotoPickerActivity.this.selectedPhotos.containsKey(searchImage.id), false);
                        isShowingImage = searchImage.document != null ? PhotoViewer.getInstance().isShowingImage(FileLoader.getPathToAttach(searchImage.document, true).getAbsolutePath()) : PhotoViewer.getInstance().isShowingImage(searchImage.imageUrl);
                    }
                    backupImageView.getImageReceiver().setVisible(!isShowingImage, true);
                    CheckBox checkBox = photoPickerPhotoCell.checkBox;
                    if (PhotoPickerActivity.this.singlePhoto || isShowingImage) {
                        i3 = 8;
                    }
                    checkBox.setVisibility(i3);
                    return;
                case 1:
                    LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
                    if (layoutParams != null) {
                        layoutParams.width = PhotoPickerActivity.this.itemWidth;
                        layoutParams.height = PhotoPickerActivity.this.itemWidth;
                        viewHolder.itemView.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            View photoPickerPhotoCell;
            switch (i) {
                case 0:
                    photoPickerPhotoCell = new PhotoPickerPhotoCell(this.mContext, true);
                    photoPickerPhotoCell.checkFrame.setOnClickListener(new C50441());
                    photoPickerPhotoCell.checkFrame.setVisibility(PhotoPickerActivity.this.singlePhoto ? 8 : 0);
                    view = photoPickerPhotoCell;
                    break;
                default:
                    view = new FrameLayout(this.mContext);
                    photoPickerPhotoCell = new RadialProgressView(this.mContext);
                    photoPickerPhotoCell.setProgressColor(-1);
                    view.addView(photoPickerPhotoCell, LayoutHelper.createFrame(-1, -1.0f));
                    break;
            }
            return new Holder(view);
        }
    }

    public PhotoPickerActivity(int i, AlbumEntry albumEntry, HashMap<Object, Object> hashMap, ArrayList<Object> arrayList, ArrayList<SearchImage> arrayList2, boolean z, boolean z2, ChatActivity chatActivity) {
        this.selectedAlbum = albumEntry;
        this.selectedPhotos = hashMap;
        this.selectedPhotosOrder = arrayList;
        this.type = i;
        this.recentImages = arrayList2;
        this.singlePhoto = z;
        this.chatActivity = chatActivity;
        this.allowCaption = z2;
    }

    private int addToSelectedPhotos(Object obj, int i) {
        Object obj2 = null;
        if (obj instanceof PhotoEntry) {
            obj2 = Integer.valueOf(((PhotoEntry) obj).imageId);
        } else if (obj instanceof SearchImage) {
            obj2 = ((SearchImage) obj).id;
        }
        if (obj2 == null) {
            return -1;
        }
        if (this.selectedPhotos.containsKey(obj2)) {
            this.selectedPhotos.remove(obj2);
            int indexOf = this.selectedPhotosOrder.indexOf(obj2);
            if (indexOf >= 0) {
                this.selectedPhotosOrder.remove(indexOf);
            }
            if (this.allowIndices) {
                updateCheckedPhotoIndices();
            }
            if (i < 0) {
                return indexOf;
            }
            if (obj instanceof PhotoEntry) {
                ((PhotoEntry) obj).reset();
            } else if (obj instanceof SearchImage) {
                ((SearchImage) obj).reset();
            }
            this.provider.updatePhotoAtIndex(i);
            return indexOf;
        }
        this.selectedPhotos.put(obj2, obj);
        this.selectedPhotosOrder.add(obj2);
        return -1;
    }

    private void fixLayout() {
        if (this.listView != null) {
            this.listView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() {
                    PhotoPickerActivity.this.fixLayoutInternal();
                    if (PhotoPickerActivity.this.listView != null) {
                        PhotoPickerActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
                    }
                    return true;
                }
            });
        }
    }

    private void fixLayoutInternal() {
        if (getParentActivity() != null) {
            int findFirstVisibleItemPosition = this.layoutManager.findFirstVisibleItemPosition();
            int rotation = ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
            rotation = AndroidUtilities.isTablet() ? 3 : (rotation == 3 || rotation == 1) ? 5 : 3;
            this.layoutManager.setSpanCount(rotation);
            if (AndroidUtilities.isTablet()) {
                this.itemWidth = (AndroidUtilities.dp(490.0f) - ((rotation + 1) * AndroidUtilities.dp(4.0f))) / rotation;
            } else {
                this.itemWidth = (AndroidUtilities.displaySize.x - ((rotation + 1) * AndroidUtilities.dp(4.0f))) / rotation;
            }
            this.listAdapter.notifyDataSetChanged();
            this.layoutManager.scrollToPosition(findFirstVisibleItemPosition);
            if (this.selectedAlbum == null) {
                this.emptyView.setPadding(0, 0, 0, (int) (((float) (AndroidUtilities.displaySize.y - ActionBar.getCurrentActionBarHeight())) * 0.4f));
            }
        }
    }

    private PhotoPickerPhotoCell getCellForIndex(int i) {
        int childCount = this.listView.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = this.listView.getChildAt(i2);
            if (childAt instanceof PhotoPickerPhotoCell) {
                PhotoPickerPhotoCell photoPickerPhotoCell = (PhotoPickerPhotoCell) childAt;
                int intValue = ((Integer) photoPickerPhotoCell.photoImage.getTag()).intValue();
                if (this.selectedAlbum == null) {
                    ArrayList arrayList = (this.searchResult.isEmpty() && this.lastSearchString == null) ? this.recentImages : this.searchResult;
                    if (intValue < 0) {
                        continue;
                    } else if (intValue >= arrayList.size()) {
                        continue;
                    }
                } else if (intValue < 0) {
                    continue;
                } else if (intValue >= this.selectedAlbum.photos.size()) {
                    continue;
                }
                if (intValue == i) {
                    return photoPickerPhotoCell;
                }
            }
        }
        return null;
    }

    private void hideHint() {
        this.hintAnimation = new AnimatorSet();
        AnimatorSet animatorSet = this.hintAnimation;
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(this.hintTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        animatorSet.playTogether(animatorArr);
        this.hintAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationCancel(Animator animator) {
                if (animator.equals(PhotoPickerActivity.this.hintAnimation)) {
                    PhotoPickerActivity.this.hintHideRunnable = null;
                    PhotoPickerActivity.this.hintHideRunnable = null;
                }
            }

            public void onAnimationEnd(Animator animator) {
                if (animator.equals(PhotoPickerActivity.this.hintAnimation)) {
                    PhotoPickerActivity.this.hintAnimation = null;
                    PhotoPickerActivity.this.hintHideRunnable = null;
                    if (PhotoPickerActivity.this.hintTextView != null) {
                        PhotoPickerActivity.this.hintTextView.setVisibility(8);
                    }
                }
            }
        });
        this.hintAnimation.setDuration(300);
        this.hintAnimation.start();
    }

    private void searchBingImages(String str, int i, int i2) {
        if (this.searching) {
            this.searching = false;
            if (this.giphyReqId != 0) {
                ConnectionsManager.getInstance().cancelRequest(this.giphyReqId, true);
                this.giphyReqId = 0;
            }
            if (this.currentBingTask != null) {
                this.currentBingTask.cancel(true);
                this.currentBingTask = null;
            }
        }
        try {
            this.searching = true;
            String str2 = UserConfig.getCurrentUser().phone;
            boolean z = str2.startsWith("44") || str2.startsWith("49") || str2.startsWith("43") || str2.startsWith("31") || str2.startsWith("1");
            Locale locale = Locale.US;
            String str3 = "https://api.cognitive.microsoft.com/bing/v5.0/images/search?q='%s'&offset=%d&count=%d&$format=json&safeSearch=%s";
            Object[] objArr = new Object[4];
            objArr[0] = URLEncoder.encode(str, C3446C.UTF8_NAME);
            objArr[1] = Integer.valueOf(i);
            objArr[2] = Integer.valueOf(i2);
            objArr[3] = z ? "Strict" : "Off";
            str2 = String.format(locale, str3, objArr);
            this.currentBingTask = new AsyncTask<Void, Void, JSONObject>() {
                private boolean canRetry = true;

                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                private java.lang.String downloadUrlContent(java.lang.String r13) {
                    /*
                    r12 = this;
                    r4 = 1;
                    r5 = 0;
                    r3 = 0;
                    r1 = new java.net.URL;	 Catch:{ Throwable -> 0x00e5 }
                    r1.<init>(r13);	 Catch:{ Throwable -> 0x00e5 }
                    r2 = r1.openConnection();	 Catch:{ Throwable -> 0x00e5 }
                    r1 = "Ocp-Apim-Subscription-Key";
                    r6 = org.telegram.messenger.BuildVars.BING_SEARCH_KEY;	 Catch:{ Throwable -> 0x0163 }
                    r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0163 }
                    r1 = "User-Agent";
                    r6 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
                    r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0163 }
                    r1 = "Accept-Language";
                    r6 = "en-us,en;q=0.5";
                    r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0163 }
                    r1 = "Accept";
                    r6 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
                    r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0163 }
                    r1 = "Accept-Charset";
                    r6 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
                    r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0163 }
                    r1 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
                    r2.setConnectTimeout(r1);	 Catch:{ Throwable -> 0x0163 }
                    r1 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
                    r2.setReadTimeout(r1);	 Catch:{ Throwable -> 0x0163 }
                    r1 = r2 instanceof java.net.HttpURLConnection;	 Catch:{ Throwable -> 0x0163 }
                    if (r1 == 0) goto L_0x00a7;
                L_0x0046:
                    r0 = r2;
                    r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x0163 }
                    r1 = r0;
                    r6 = 1;
                    r1.setInstanceFollowRedirects(r6);	 Catch:{ Throwable -> 0x0163 }
                    r6 = r1.getResponseCode();	 Catch:{ Throwable -> 0x0163 }
                    r7 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
                    if (r6 == r7) goto L_0x005e;
                L_0x0056:
                    r7 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
                    if (r6 == r7) goto L_0x005e;
                L_0x005a:
                    r7 = 303; // 0x12f float:4.25E-43 double:1.497E-321;
                    if (r6 != r7) goto L_0x00a7;
                L_0x005e:
                    r6 = "Location";
                    r6 = r1.getHeaderField(r6);	 Catch:{ Throwable -> 0x0163 }
                    r7 = "Set-Cookie";
                    r1 = r1.getHeaderField(r7);	 Catch:{ Throwable -> 0x0163 }
                    r7 = new java.net.URL;	 Catch:{ Throwable -> 0x0163 }
                    r7.<init>(r6);	 Catch:{ Throwable -> 0x0163 }
                    r2 = r7.openConnection();	 Catch:{ Throwable -> 0x0163 }
                    r6 = "Cookie";
                    r2.setRequestProperty(r6, r1);	 Catch:{ Throwable -> 0x0163 }
                    r1 = "Ocp-Apim-Subscription-Key";
                    r6 = org.telegram.messenger.BuildVars.BING_SEARCH_KEY;	 Catch:{ Throwable -> 0x0163 }
                    r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0163 }
                    r1 = "User-Agent";
                    r6 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
                    r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0163 }
                    r1 = "Accept-Language";
                    r6 = "en-us,en;q=0.5";
                    r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0163 }
                    r1 = "Accept";
                    r6 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
                    r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0163 }
                    r1 = "Accept-Charset";
                    r6 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
                    r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0163 }
                L_0x00a7:
                    r2.connect();	 Catch:{ Throwable -> 0x0163 }
                    r1 = r2.getInputStream();	 Catch:{ Throwable -> 0x0163 }
                    r6 = r1;
                    r1 = r2;
                    r2 = r4;
                L_0x00b1:
                    if (r2 == 0) goto L_0x016c;
                L_0x00b3:
                    if (r1 == 0) goto L_0x00cb;
                L_0x00b5:
                    r2 = r1 instanceof java.net.HttpURLConnection;	 Catch:{ Exception -> 0x011f }
                    if (r2 == 0) goto L_0x00cb;
                L_0x00b9:
                    r1 = (java.net.HttpURLConnection) r1;	 Catch:{ Exception -> 0x011f }
                    r1 = r1.getResponseCode();	 Catch:{ Exception -> 0x011f }
                    r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
                    if (r1 == r2) goto L_0x00cb;
                L_0x00c3:
                    r2 = 202; // 0xca float:2.83E-43 double:1.0E-321;
                    if (r1 == r2) goto L_0x00cb;
                L_0x00c7:
                    r2 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
                    if (r1 == r2) goto L_0x00cb;
                L_0x00cb:
                    if (r6 == 0) goto L_0x0169;
                L_0x00cd:
                    r1 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
                    r7 = new byte[r1];	 Catch:{ Throwable -> 0x015c }
                    r1 = r5;
                L_0x00d3:
                    r2 = r12.isCancelled();	 Catch:{ Throwable -> 0x014c }
                    if (r2 == 0) goto L_0x0124;
                L_0x00d9:
                    if (r6 == 0) goto L_0x00de;
                L_0x00db:
                    r6.close();	 Catch:{ Throwable -> 0x0155 }
                L_0x00de:
                    if (r3 == 0) goto L_0x015a;
                L_0x00e0:
                    r1 = r1.toString();
                L_0x00e4:
                    return r1;
                L_0x00e5:
                    r1 = move-exception;
                    r2 = r1;
                    r6 = r5;
                L_0x00e8:
                    r1 = r2 instanceof java.net.SocketTimeoutException;
                    if (r1 == 0) goto L_0x00fa;
                L_0x00ec:
                    r1 = org.telegram.tgnet.ConnectionsManager.isNetworkOnline();
                    if (r1 == 0) goto L_0x016f;
                L_0x00f2:
                    r1 = r3;
                L_0x00f3:
                    org.telegram.messenger.FileLog.e(r2);
                    r2 = r1;
                    r1 = r6;
                    r6 = r5;
                    goto L_0x00b1;
                L_0x00fa:
                    r1 = r2 instanceof java.net.UnknownHostException;
                    if (r1 == 0) goto L_0x0100;
                L_0x00fe:
                    r1 = r3;
                    goto L_0x00f3;
                L_0x0100:
                    r1 = r2 instanceof java.net.SocketException;
                    if (r1 == 0) goto L_0x0119;
                L_0x0104:
                    r1 = r2.getMessage();
                    if (r1 == 0) goto L_0x016f;
                L_0x010a:
                    r1 = r2.getMessage();
                    r7 = "ECONNRESET";
                    r1 = r1.contains(r7);
                    if (r1 == 0) goto L_0x016f;
                L_0x0117:
                    r1 = r3;
                    goto L_0x00f3;
                L_0x0119:
                    r1 = r2 instanceof java.io.FileNotFoundException;
                    if (r1 == 0) goto L_0x016f;
                L_0x011d:
                    r1 = r3;
                    goto L_0x00f3;
                L_0x011f:
                    r1 = move-exception;
                    org.telegram.messenger.FileLog.e(r1);
                    goto L_0x00cb;
                L_0x0124:
                    r8 = r6.read(r7);	 Catch:{ Exception -> 0x0161 }
                    if (r8 <= 0) goto L_0x013f;
                L_0x012a:
                    if (r1 != 0) goto L_0x0167;
                L_0x012c:
                    r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0161 }
                    r2.<init>();	 Catch:{ Exception -> 0x0161 }
                L_0x0131:
                    r1 = new java.lang.String;	 Catch:{ Exception -> 0x0144, Throwable -> 0x015f }
                    r9 = 0;
                    r10 = "UTF-8";
                    r1.<init>(r7, r9, r8, r10);	 Catch:{ Exception -> 0x0144, Throwable -> 0x015f }
                    r2.append(r1);	 Catch:{ Exception -> 0x0144, Throwable -> 0x015f }
                    r1 = r2;
                    goto L_0x00d3;
                L_0x013f:
                    r2 = -1;
                    if (r8 != r2) goto L_0x00d9;
                L_0x0142:
                    r3 = r4;
                    goto L_0x00d9;
                L_0x0144:
                    r1 = move-exception;
                    r11 = r1;
                    r1 = r2;
                    r2 = r11;
                L_0x0148:
                    org.telegram.messenger.FileLog.e(r2);	 Catch:{ Throwable -> 0x014c }
                    goto L_0x00d9;
                L_0x014c:
                    r2 = move-exception;
                    r11 = r2;
                    r2 = r1;
                    r1 = r11;
                L_0x0150:
                    org.telegram.messenger.FileLog.e(r1);
                    r1 = r2;
                    goto L_0x00d9;
                L_0x0155:
                    r2 = move-exception;
                    org.telegram.messenger.FileLog.e(r2);
                    goto L_0x00de;
                L_0x015a:
                    r1 = r5;
                    goto L_0x00e4;
                L_0x015c:
                    r1 = move-exception;
                    r2 = r5;
                    goto L_0x0150;
                L_0x015f:
                    r1 = move-exception;
                    goto L_0x0150;
                L_0x0161:
                    r2 = move-exception;
                    goto L_0x0148;
                L_0x0163:
                    r1 = move-exception;
                    r6 = r2;
                    r2 = r1;
                    goto L_0x00e8;
                L_0x0167:
                    r2 = r1;
                    goto L_0x0131;
                L_0x0169:
                    r1 = r5;
                    goto L_0x00d9;
                L_0x016c:
                    r1 = r5;
                    goto L_0x00de;
                L_0x016f:
                    r1 = r4;
                    goto L_0x00f3;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PhotoPickerActivity.16.downloadUrlContent(java.lang.String):java.lang.String");
                }

                protected JSONObject doInBackground(Void... voidArr) {
                    String downloadUrlContent = downloadUrlContent(str2);
                    if (isCancelled()) {
                        return null;
                    }
                    try {
                        return new JSONObject(downloadUrlContent);
                    } catch (Throwable e) {
                        FileLog.e(e);
                        return null;
                    }
                }

                protected void onPostExecute(JSONObject jSONObject) {
                    int i;
                    Throwable e;
                    boolean z = true;
                    if (jSONObject != null) {
                        try {
                            JSONArray jSONArray = jSONObject.getJSONArray(C1797b.VALUE);
                            boolean z2 = false;
                            i = 0;
                            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                                try {
                                    JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                                    String MD5 = Utilities.MD5(jSONObject2.getString("contentUrl"));
                                    if (!PhotoPickerActivity.this.searchResultKeys.containsKey(MD5)) {
                                        SearchImage searchImage = new SearchImage();
                                        searchImage.id = MD5;
                                        searchImage.width = jSONObject2.getInt("width");
                                        searchImage.height = jSONObject2.getInt("height");
                                        searchImage.size = Utilities.parseInt(jSONObject2.getString("contentSize")).intValue();
                                        searchImage.imageUrl = jSONObject2.getString("contentUrl");
                                        searchImage.thumbUrl = jSONObject2.getString("thumbnailUrl");
                                        PhotoPickerActivity.this.searchResult.add(searchImage);
                                        PhotoPickerActivity.this.searchResultKeys.put(MD5, searchImage);
                                        i++;
                                        z2 = true;
                                    }
                                } catch (Throwable e2) {
                                    try {
                                        FileLog.e(e2);
                                    } catch (Exception e3) {
                                        e = e3;
                                    }
                                }
                            }
                            PhotoPickerActivity photoPickerActivity = PhotoPickerActivity.this;
                            if (z2) {
                                z = false;
                            }
                            photoPickerActivity.bingSearchEndReached = z;
                        } catch (Exception e4) {
                            e = e4;
                            i = 0;
                            FileLog.e(e);
                            PhotoPickerActivity.this.searching = false;
                            if (i != 0) {
                                PhotoPickerActivity.this.listAdapter.notifyItemRangeInserted(PhotoPickerActivity.this.searchResult.size(), i);
                            } else if (PhotoPickerActivity.this.giphySearchEndReached) {
                                PhotoPickerActivity.this.listAdapter.notifyItemRemoved(PhotoPickerActivity.this.searchResult.size() - 1);
                            }
                            if (PhotoPickerActivity.this.searching) {
                            }
                            PhotoPickerActivity.this.emptyView.showTextView();
                        }
                        PhotoPickerActivity.this.searching = false;
                    } else {
                        PhotoPickerActivity.this.bingSearchEndReached = true;
                        PhotoPickerActivity.this.searching = false;
                        i = 0;
                    }
                    if (i != 0) {
                        PhotoPickerActivity.this.listAdapter.notifyItemRangeInserted(PhotoPickerActivity.this.searchResult.size(), i);
                    } else if (PhotoPickerActivity.this.giphySearchEndReached) {
                        PhotoPickerActivity.this.listAdapter.notifyItemRemoved(PhotoPickerActivity.this.searchResult.size() - 1);
                    }
                    if ((PhotoPickerActivity.this.searching || !PhotoPickerActivity.this.searchResult.isEmpty()) && !(PhotoPickerActivity.this.loadingRecent && PhotoPickerActivity.this.lastSearchString == null)) {
                        PhotoPickerActivity.this.emptyView.showTextView();
                    } else {
                        PhotoPickerActivity.this.emptyView.showProgress();
                    }
                }
            };
            this.currentBingTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
        } catch (Throwable e) {
            FileLog.e(e);
            this.bingSearchEndReached = true;
            this.searching = false;
            this.listAdapter.notifyItemRemoved(this.searchResult.size() - 1);
            if ((this.searching && this.searchResult.isEmpty()) || (this.loadingRecent && this.lastSearchString == null)) {
                this.emptyView.showProgress();
            } else {
                this.emptyView.showTextView();
            }
        }
    }

    private void searchGiphyImages(final String str, int i) {
        if (this.searching) {
            this.searching = false;
            if (this.giphyReqId != 0) {
                ConnectionsManager.getInstance().cancelRequest(this.giphyReqId, true);
                this.giphyReqId = 0;
            }
            if (this.currentBingTask != null) {
                this.currentBingTask.cancel(true);
                this.currentBingTask = null;
            }
        }
        this.searching = true;
        TLObject tLRPC$TL_messages_searchGifs = new TLRPC$TL_messages_searchGifs();
        tLRPC$TL_messages_searchGifs.f10167q = str;
        tLRPC$TL_messages_searchGifs.offset = i;
        final int i2 = this.lastSearchToken + 1;
        this.lastSearchToken = i2;
        this.giphyReqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_searchGifs, new RequestDelegate() {
            public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        boolean z = true;
                        if (i2 == PhotoPickerActivity.this.lastSearchToken) {
                            int i;
                            if (tLObject != null) {
                                TLRPC$TL_messages_foundGifs tLRPC$TL_messages_foundGifs = (TLRPC$TL_messages_foundGifs) tLObject;
                                PhotoPickerActivity.this.nextGiphySearchOffset = tLRPC$TL_messages_foundGifs.next_offset;
                                boolean z2 = false;
                                i = 0;
                                for (int i2 = 0; i2 < tLRPC$TL_messages_foundGifs.results.size(); i2++) {
                                    FoundGif foundGif = (FoundGif) tLRPC$TL_messages_foundGifs.results.get(i2);
                                    if (!PhotoPickerActivity.this.searchResultKeys.containsKey(foundGif.url)) {
                                        SearchImage searchImage = new SearchImage();
                                        searchImage.id = foundGif.url;
                                        if (foundGif.document != null) {
                                            for (int i3 = 0; i3 < foundGif.document.attributes.size(); i3++) {
                                                DocumentAttribute documentAttribute = (DocumentAttribute) foundGif.document.attributes.get(i3);
                                                if ((documentAttribute instanceof TLRPC$TL_documentAttributeImageSize) || (documentAttribute instanceof TLRPC$TL_documentAttributeVideo)) {
                                                    searchImage.width = documentAttribute.f10140w;
                                                    searchImage.height = documentAttribute.f10139h;
                                                    break;
                                                }
                                            }
                                        } else {
                                            searchImage.width = foundGif.f10142w;
                                            searchImage.height = foundGif.f10141h;
                                        }
                                        searchImage.size = 0;
                                        searchImage.imageUrl = foundGif.content_url;
                                        searchImage.thumbUrl = foundGif.thumb_url;
                                        searchImage.localUrl = foundGif.url + "|" + str;
                                        searchImage.document = foundGif.document;
                                        if (!(foundGif.photo == null || foundGif.document == null)) {
                                            PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(foundGif.photo.sizes, PhotoPickerActivity.this.itemWidth, true);
                                            if (closestPhotoSizeWithSize != null) {
                                                foundGif.document.thumb = closestPhotoSizeWithSize;
                                            }
                                        }
                                        searchImage.type = 1;
                                        PhotoPickerActivity.this.searchResult.add(searchImage);
                                        i++;
                                        PhotoPickerActivity.this.searchResultKeys.put(searchImage.id, searchImage);
                                        z2 = true;
                                    }
                                }
                                PhotoPickerActivity photoPickerActivity = PhotoPickerActivity.this;
                                if (z2) {
                                    z = false;
                                }
                                photoPickerActivity.giphySearchEndReached = z;
                            } else {
                                i = 0;
                            }
                            PhotoPickerActivity.this.searching = false;
                            if (i != 0) {
                                PhotoPickerActivity.this.listAdapter.notifyItemRangeInserted(PhotoPickerActivity.this.searchResult.size(), i);
                            } else if (PhotoPickerActivity.this.giphySearchEndReached) {
                                PhotoPickerActivity.this.listAdapter.notifyItemRemoved(PhotoPickerActivity.this.searchResult.size() - 1);
                            }
                            if ((PhotoPickerActivity.this.searching && PhotoPickerActivity.this.searchResult.isEmpty()) || (PhotoPickerActivity.this.loadingRecent && PhotoPickerActivity.this.lastSearchString == null)) {
                                PhotoPickerActivity.this.emptyView.showProgress();
                            } else {
                                PhotoPickerActivity.this.emptyView.showTextView();
                            }
                        }
                    }
                });
            }
        });
        ConnectionsManager.getInstance().bindRequestToGuid(this.giphyReqId, this.classGuid);
    }

    private void sendSelectedPhotos() {
        if (!this.selectedPhotos.isEmpty() && this.delegate != null && !this.sendPressed) {
            this.sendPressed = true;
            this.delegate.actionButtonPressed(false);
            finishFragment();
        }
    }

    private void showHint(boolean z, boolean z2) {
        if (getParentActivity() != null && this.fragmentView != null) {
            if (!z || this.hintTextView != null) {
                if (this.hintTextView == null) {
                    this.hintTextView = new TextView(getParentActivity());
                    this.hintTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
                    this.hintTextView.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
                    this.hintTextView.setTextSize(1, 14.0f);
                    this.hintTextView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f));
                    this.hintTextView.setGravity(16);
                    this.hintTextView.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    this.frameLayout.addView(this.hintTextView, LayoutHelper.createFrame(-2, -2.0f, 81, 5.0f, BitmapDescriptorFactory.HUE_RED, 5.0f, 51.0f));
                }
                if (z) {
                    if (this.hintAnimation != null) {
                        this.hintAnimation.cancel();
                        this.hintAnimation = null;
                    }
                    AndroidUtilities.cancelRunOnUIThread(this.hintHideRunnable);
                    this.hintHideRunnable = null;
                    hideHint();
                    return;
                }
                this.hintTextView.setText(z2 ? LocaleController.getString("GroupPhotosHelp", R.string.GroupPhotosHelp) : LocaleController.getString("SinglePhotosHelp", R.string.SinglePhotosHelp));
                if (this.hintHideRunnable != null) {
                    if (this.hintAnimation != null) {
                        this.hintAnimation.cancel();
                        this.hintAnimation = null;
                    } else {
                        AndroidUtilities.cancelRunOnUIThread(this.hintHideRunnable);
                        Runnable anonymousClass13 = new Runnable() {
                            public void run() {
                                PhotoPickerActivity.this.hideHint();
                            }
                        };
                        this.hintHideRunnable = anonymousClass13;
                        AndroidUtilities.runOnUIThread(anonymousClass13, 2000);
                        return;
                    }
                } else if (this.hintAnimation != null) {
                    return;
                }
                this.hintTextView.setVisibility(0);
                this.hintAnimation = new AnimatorSet();
                AnimatorSet animatorSet = this.hintAnimation;
                Animator[] animatorArr = new Animator[1];
                animatorArr[0] = ObjectAnimator.ofFloat(this.hintTextView, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
                this.hintAnimation.addListener(new AnimatorListenerAdapter() {

                    /* renamed from: org.telegram.ui.PhotoPickerActivity$14$1 */
                    class C50321 implements Runnable {
                        C50321() {
                        }

                        public void run() {
                            PhotoPickerActivity.this.hideHint();
                        }
                    }

                    public void onAnimationCancel(Animator animator) {
                        if (animator.equals(PhotoPickerActivity.this.hintAnimation)) {
                            PhotoPickerActivity.this.hintAnimation = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (animator.equals(PhotoPickerActivity.this.hintAnimation)) {
                            PhotoPickerActivity.this.hintAnimation = null;
                            AndroidUtilities.runOnUIThread(PhotoPickerActivity.this.hintHideRunnable = new C50321(), 2000);
                        }
                    }
                });
                this.hintAnimation.setDuration(300);
                this.hintAnimation.start();
            }
        }
    }

    private void updateCheckedPhotoIndices() {
        if (this.selectedAlbum != null) {
            int childCount = this.listView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.listView.getChildAt(i);
                if (childAt instanceof PhotoPickerPhotoCell) {
                    PhotoPickerPhotoCell photoPickerPhotoCell = (PhotoPickerPhotoCell) childAt;
                    photoPickerPhotoCell.setNum(this.allowIndices ? this.selectedPhotosOrder.indexOf(Integer.valueOf(((PhotoEntry) this.selectedAlbum.photos.get(((Integer) photoPickerPhotoCell.getTag()).intValue())).imageId)) : -1);
                }
            }
        }
    }

    private void updateSearchInterface() {
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        if ((this.searching && this.searchResult.isEmpty()) || (this.loadingRecent && this.lastSearchString == null)) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackgroundColor(Theme.ACTION_BAR_MEDIA_PICKER_COLOR);
        this.actionBar.setItemsBackgroundColor(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, false);
        this.actionBar.setTitleColor(-1);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        if (this.selectedAlbum != null) {
            this.actionBar.setTitle(this.selectedAlbum.bucketName);
        } else if (this.type == 0) {
            this.actionBar.setTitle(LocaleController.getString("SearchImagesTitle", R.string.SearchImagesTitle));
        } else if (this.type == 1) {
            this.actionBar.setTitle(LocaleController.getString("SearchGifsTitle", R.string.SearchGifsTitle));
        }
        this.actionBar.setActionBarMenuOnItemClick(new C50352());
        if (this.selectedAlbum == null) {
            this.searchItem = this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C50363());
        }
        if (this.selectedAlbum == null) {
            if (this.type == 0) {
                this.searchItem.getSearchField().setHint(LocaleController.getString("SearchImagesTitle", R.string.SearchImagesTitle));
            } else if (this.type == 1) {
                this.searchItem.getSearchField().setHint(LocaleController.getString("SearchGifsTitle", R.string.SearchGifsTitle));
            }
        }
        this.fragmentView = new FrameLayout(context);
        this.frameLayout = (FrameLayout) this.fragmentView;
        this.frameLayout.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        this.listView = new RecyclerListView(context);
        this.listView.setPadding(AndroidUtilities.dp(4.0f), AndroidUtilities.dp(4.0f), AndroidUtilities.dp(4.0f), AndroidUtilities.dp(4.0f));
        this.listView.setClipToPadding(false);
        this.listView.setHorizontalScrollBarEnabled(false);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        RecyclerListView recyclerListView = this.listView;
        LayoutManager c50374 = new GridLayoutManager(context, 4) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.layoutManager = c50374;
        recyclerListView.setLayoutManager(c50374);
        this.listView.addItemDecoration(new C50385());
        this.frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.singlePhoto ? BitmapDescriptorFactory.HUE_RED : 48.0f));
        recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setGlowColor(Theme.ACTION_BAR_MEDIA_PICKER_COLOR);
        this.listView.setOnItemClickListener(new C50396());
        if (this.selectedAlbum == null) {
            this.listView.setOnItemLongClickListener(new C50417());
        }
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.setTextColor(-8355712);
        this.emptyView.setProgressBarColor(-1);
        this.emptyView.setShowAtCenter(true);
        if (this.selectedAlbum != null) {
            this.emptyView.setText(LocaleController.getString("NoPhotos", R.string.NoPhotos));
        } else if (this.type == 0) {
            this.emptyView.setText(LocaleController.getString("NoRecentPhotos", R.string.NoRecentPhotos));
        } else if (this.type == 1) {
            this.emptyView.setText(LocaleController.getString("NoRecentGIFs", R.string.NoRecentGIFs));
        }
        this.frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.singlePhoto ? BitmapDescriptorFactory.HUE_RED : 48.0f));
        if (this.selectedAlbum == null) {
            this.listView.setOnScrollListener(new C50428());
            updateSearchInterface();
        }
        this.pickerBottomLayout = new PickerBottomLayout(context);
        this.frameLayout.addView(this.pickerBottomLayout, LayoutHelper.createFrame(-1, 48, 80));
        this.pickerBottomLayout.cancelButton.setOnClickListener(new C50439());
        this.pickerBottomLayout.doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhotoPickerActivity.this.sendSelectedPhotos();
            }
        });
        if (this.singlePhoto) {
            this.pickerBottomLayout.setVisibility(8);
        } else if ((this.selectedAlbum != null || this.type == 0) && this.chatActivity != null && this.chatActivity.allowGroupPhotos()) {
            this.imageOrderToggleButton = new ImageView(context);
            this.imageOrderToggleButton.setScaleType(ScaleType.CENTER);
            this.imageOrderToggleButton.setImageResource(R.drawable.photos_group);
            this.pickerBottomLayout.addView(this.imageOrderToggleButton, LayoutHelper.createFrame(48, -1, 17));
            this.imageOrderToggleButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    MediaController.getInstance().toggleGroupPhotosEnabled();
                    PhotoPickerActivity.this.imageOrderToggleButton.setColorFilter(MediaController.getInstance().isGroupPhotosEnabled() ? new PorterDuffColorFilter(-10043398, Mode.MULTIPLY) : null);
                    PhotoPickerActivity.this.showHint(false, MediaController.getInstance().isGroupPhotosEnabled());
                    PhotoPickerActivity.this.updateCheckedPhotoIndices();
                }
            });
            this.imageOrderToggleButton.setColorFilter(MediaController.getInstance().isGroupPhotosEnabled() ? new PorterDuffColorFilter(-10043398, Mode.MULTIPLY) : null);
        }
        boolean z = this.selectedAlbum != null || this.type == 0;
        this.allowIndices = z;
        this.listView.setEmptyView(this.emptyView);
        this.pickerBottomLayout.updateSelectedCount(this.selectedPhotos.size(), true);
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.closeChats) {
            removeSelfFromStack();
        } else if (i == NotificationCenter.recentImagesDidLoaded && this.selectedAlbum == null && this.type == ((Integer) objArr[0]).intValue()) {
            this.recentImages = (ArrayList) objArr[1];
            this.loadingRecent = false;
            updateSearchInterface();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        fixLayout();
    }

    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeChats);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.recentImagesDidLoaded);
        if (this.selectedAlbum == null && this.recentImages.isEmpty()) {
            MessagesStorage.getInstance().loadWebRecent(this.type);
            this.loadingRecent = true;
        }
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.recentImagesDidLoaded);
        if (this.currentBingTask != null) {
            this.currentBingTask.cancel(true);
            this.currentBingTask = null;
        }
        if (this.giphyReqId != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.giphyReqId, true);
            this.giphyReqId = 0;
        }
        super.onFragmentDestroy();
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        if (this.searchItem != null) {
            this.searchItem.openSearch(true);
            getParentActivity().getWindow().setSoftInputMode(32);
        }
        fixLayout();
    }

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z && this.searchItem != null) {
            AndroidUtilities.showKeyboard(this.searchItem.getSearchField());
        }
    }

    public void setDelegate(PhotoPickerActivityDelegate photoPickerActivityDelegate) {
        this.delegate = photoPickerActivityDelegate;
    }
}
