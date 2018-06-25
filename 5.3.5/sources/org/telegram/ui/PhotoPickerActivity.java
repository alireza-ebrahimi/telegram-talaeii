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
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.persianswitch.sdk.BuildConfig;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.ir.talaeii.R;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController$AlbumEntry;
import org.telegram.messenger.MediaController$PhotoEntry;
import org.telegram.messenger.MediaController$SearchImage;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.VideoEditedInfo;
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
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$FoundGif;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_foundGifs;
import org.telegram.tgnet.TLRPC$TL_messages_searchGifs;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
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
    private PhotoViewerProvider provider = new C31951();
    private ArrayList<MediaController$SearchImage> recentImages;
    private ActionBarMenuItem searchItem;
    private ArrayList<MediaController$SearchImage> searchResult = new ArrayList();
    private HashMap<String, MediaController$SearchImage> searchResultKeys = new HashMap();
    private HashMap<String, MediaController$SearchImage> searchResultUrls = new HashMap();
    private boolean searching;
    private MediaController$AlbumEntry selectedAlbum;
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
    class C31951 extends EmptyPhotoViewerProvider {
        C31951() {
        }

        public boolean scaleToFill() {
            return false;
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
            PhotoPickerPhotoCell cell = PhotoPickerActivity.this.getCellForIndex(index);
            if (cell == null) {
                return null;
            }
            int[] coords = new int[2];
            cell.photoImage.getLocationInWindow(coords);
            PlaceProviderObject object = new PlaceProviderObject();
            object.viewX = coords[0];
            object.viewY = coords[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight);
            object.parentView = PhotoPickerActivity.this.listView;
            object.imageReceiver = cell.photoImage.getImageReceiver();
            object.thumb = object.imageReceiver.getBitmap();
            object.scale = cell.photoImage.getScaleX();
            cell.showCheck(false);
            return object;
        }

        public void updatePhotoAtIndex(int index) {
            PhotoPickerPhotoCell cell = PhotoPickerActivity.this.getCellForIndex(index);
            if (cell == null) {
                return;
            }
            if (PhotoPickerActivity.this.selectedAlbum != null) {
                cell.photoImage.setOrientation(0, true);
                MediaController$PhotoEntry photoEntry = (MediaController$PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(index);
                if (photoEntry.thumbPath != null) {
                    cell.photoImage.setImage(photoEntry.thumbPath, null, cell.getContext().getResources().getDrawable(R.drawable.nophotos));
                    return;
                } else if (photoEntry.path != null) {
                    cell.photoImage.setOrientation(photoEntry.orientation, true);
                    if (photoEntry.isVideo) {
                        cell.photoImage.setImage("vthumb://" + photoEntry.imageId + ":" + photoEntry.path, null, cell.getContext().getResources().getDrawable(R.drawable.nophotos));
                        return;
                    } else {
                        cell.photoImage.setImage("thumb://" + photoEntry.imageId + ":" + photoEntry.path, null, cell.getContext().getResources().getDrawable(R.drawable.nophotos));
                        return;
                    }
                } else {
                    cell.photoImage.setImageResource(R.drawable.nophotos);
                    return;
                }
            }
            ArrayList<MediaController$SearchImage> array;
            if (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) {
                array = PhotoPickerActivity.this.recentImages;
            } else {
                array = PhotoPickerActivity.this.searchResult;
            }
            MediaController$SearchImage photoEntry2 = (MediaController$SearchImage) array.get(index);
            if (photoEntry2.document != null && photoEntry2.document.thumb != null) {
                cell.photoImage.setImage(photoEntry2.document.thumb.location, null, cell.getContext().getResources().getDrawable(R.drawable.nophotos));
            } else if (photoEntry2.thumbPath != null) {
                cell.photoImage.setImage(photoEntry2.thumbPath, null, cell.getContext().getResources().getDrawable(R.drawable.nophotos));
            } else if (photoEntry2.thumbUrl == null || photoEntry2.thumbUrl.length() <= 0) {
                cell.photoImage.setImageResource(R.drawable.nophotos);
            } else {
                cell.photoImage.setImage(photoEntry2.thumbUrl, null, cell.getContext().getResources().getDrawable(R.drawable.nophotos));
            }
        }

        public boolean allowCaption() {
            return PhotoPickerActivity.this.allowCaption;
        }

        public Bitmap getThumbForPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
            PhotoPickerPhotoCell cell = PhotoPickerActivity.this.getCellForIndex(index);
            if (cell != null) {
                return cell.photoImage.getImageReceiver().getBitmap();
            }
            return null;
        }

        public void willSwitchFromPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
            int count = PhotoPickerActivity.this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View view = PhotoPickerActivity.this.listView.getChildAt(a);
                if (view.getTag() != null) {
                    PhotoPickerPhotoCell cell = (PhotoPickerPhotoCell) view;
                    int num = ((Integer) view.getTag()).intValue();
                    if (PhotoPickerActivity.this.selectedAlbum == null) {
                        ArrayList<MediaController$SearchImage> array;
                        if (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) {
                            array = PhotoPickerActivity.this.recentImages;
                        } else {
                            array = PhotoPickerActivity.this.searchResult;
                        }
                        if (num < 0) {
                            continue;
                        } else if (num >= array.size()) {
                        }
                    } else if (num < 0) {
                        continue;
                    } else if (num >= PhotoPickerActivity.this.selectedAlbum.photos.size()) {
                        continue;
                    }
                    if (num == index) {
                        cell.showCheck(true);
                        return;
                    }
                }
            }
        }

        public void willHidePhotoViewer() {
            int count = PhotoPickerActivity.this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View view = PhotoPickerActivity.this.listView.getChildAt(a);
                if (view instanceof PhotoPickerPhotoCell) {
                    ((PhotoPickerPhotoCell) view).showCheck(true);
                }
            }
        }

        public boolean isPhotoChecked(int index) {
            boolean z = true;
            if (PhotoPickerActivity.this.selectedAlbum != null) {
                return index >= 0 && index < PhotoPickerActivity.this.selectedAlbum.photos.size() && PhotoPickerActivity.this.selectedPhotos.containsKey(Integer.valueOf(((MediaController$PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(index)).imageId));
            } else {
                ArrayList<MediaController$SearchImage> array;
                if (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) {
                    array = PhotoPickerActivity.this.recentImages;
                } else {
                    array = PhotoPickerActivity.this.searchResult;
                }
                if (index < 0 || index >= array.size() || !PhotoPickerActivity.this.selectedPhotos.containsKey(((MediaController$SearchImage) array.get(index)).id)) {
                    z = false;
                }
                return z;
            }
        }

        public int setPhotoChecked(int index, VideoEditedInfo videoEditedInfo) {
            int num;
            boolean add = true;
            if (PhotoPickerActivity.this.selectedAlbum == null) {
                ArrayList<MediaController$SearchImage> array;
                if (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) {
                    array = PhotoPickerActivity.this.recentImages;
                } else {
                    array = PhotoPickerActivity.this.searchResult;
                }
                if (index < 0 || index >= array.size()) {
                    return -1;
                }
                MediaController$SearchImage photoEntry = (MediaController$SearchImage) array.get(index);
                num = PhotoPickerActivity.this.addToSelectedPhotos(photoEntry, -1);
                if (num == -1) {
                    num = PhotoPickerActivity.this.selectedPhotosOrder.indexOf(photoEntry.id);
                } else {
                    add = false;
                }
            } else if (index < 0 || index >= PhotoPickerActivity.this.selectedAlbum.photos.size()) {
                return -1;
            } else {
                MediaController$PhotoEntry photoEntry2 = (MediaController$PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(index);
                num = PhotoPickerActivity.this.addToSelectedPhotos(photoEntry2, -1);
                if (num == -1) {
                    photoEntry2.editedInfo = videoEditedInfo;
                    num = PhotoPickerActivity.this.selectedPhotosOrder.indexOf(Integer.valueOf(photoEntry2.imageId));
                } else {
                    add = false;
                    photoEntry2.editedInfo = null;
                }
            }
            int count = PhotoPickerActivity.this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View view = PhotoPickerActivity.this.listView.getChildAt(a);
                if (((Integer) view.getTag()).intValue() == index) {
                    int i;
                    PhotoPickerPhotoCell photoPickerPhotoCell = (PhotoPickerPhotoCell) view;
                    if (PhotoPickerActivity.this.allowIndices) {
                        i = num;
                    } else {
                        i = -1;
                    }
                    photoPickerPhotoCell.setChecked(i, add, false);
                    PhotoPickerActivity.this.pickerBottomLayout.updateSelectedCount(PhotoPickerActivity.this.selectedPhotos.size(), true);
                    PhotoPickerActivity.this.delegate.selectedPhotosChanged();
                    return num;
                }
            }
            PhotoPickerActivity.this.pickerBottomLayout.updateSelectedCount(PhotoPickerActivity.this.selectedPhotos.size(), true);
            PhotoPickerActivity.this.delegate.selectedPhotosChanged();
            return num;
        }

        public boolean cancelButtonPressed() {
            PhotoPickerActivity.this.delegate.actionButtonPressed(true);
            PhotoPickerActivity.this.finishFragment();
            return true;
        }

        public int getSelectedCount() {
            return PhotoPickerActivity.this.selectedPhotos.size();
        }

        public void sendButtonPressed(int index, VideoEditedInfo videoEditedInfo) {
            if (PhotoPickerActivity.this.selectedPhotos.isEmpty()) {
                if (PhotoPickerActivity.this.selectedAlbum == null) {
                    ArrayList<MediaController$SearchImage> array;
                    if (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) {
                        array = PhotoPickerActivity.this.recentImages;
                    } else {
                        array = PhotoPickerActivity.this.searchResult;
                    }
                    if (index >= 0 && index < array.size()) {
                        PhotoPickerActivity.this.addToSelectedPhotos(array.get(index), -1);
                    } else {
                        return;
                    }
                } else if (index >= 0 && index < PhotoPickerActivity.this.selectedAlbum.photos.size()) {
                    MediaController$PhotoEntry photoEntry = (MediaController$PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(index);
                    photoEntry.editedInfo = videoEditedInfo;
                    PhotoPickerActivity.this.addToSelectedPhotos(photoEntry, -1);
                } else {
                    return;
                }
            }
            PhotoPickerActivity.this.sendSelectedPhotos();
        }

        public void toggleGroupPhotosEnabled() {
            if (PhotoPickerActivity.this.imageOrderToggleButton != null) {
                PhotoPickerActivity.this.imageOrderToggleButton.setColorFilter(MediaController.getInstance().isGroupPhotosEnabled() ? new PorterDuffColorFilter(-10043398, Mode.MULTIPLY) : null);
            }
        }

        public ArrayList<Object> getSelectedPhotosOrder() {
            return PhotoPickerActivity.this.selectedPhotosOrder;
        }

        public HashMap<Object, Object> getSelectedPhotos() {
            return PhotoPickerActivity.this.selectedPhotos;
        }

        public boolean allowGroupPhotos() {
            return PhotoPickerActivity.this.imageOrderToggleButton != null;
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$2 */
    class C31962 extends ActionBarMenuOnItemClick {
        C31962() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                PhotoPickerActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$3 */
    class C31973 extends ActionBarMenuItemSearchListener {
        C31973() {
        }

        public void onSearchExpand() {
        }

        public boolean canCollapseSearch() {
            PhotoPickerActivity.this.finishFragment();
            return false;
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
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$5 */
    class C31995 extends ItemDecoration {
        C31995() {
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            int dp;
            int i = 0;
            super.getItemOffsets(outRect, view, parent, state);
            int total = state.getItemCount();
            int position = parent.getChildAdapterPosition(view);
            int spanCount = PhotoPickerActivity.this.layoutManager.getSpanCount();
            int rowsCOunt = (int) Math.ceil((double) (((float) total) / ((float) spanCount)));
            int row = position / spanCount;
            if (position % spanCount != spanCount - 1) {
                dp = AndroidUtilities.dp(4.0f);
            } else {
                dp = 0;
            }
            outRect.right = dp;
            if (row != rowsCOunt - 1) {
                i = AndroidUtilities.dp(4.0f);
            }
            outRect.bottom = i;
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$6 */
    class C32006 implements OnItemClickListener {
        C32006() {
        }

        public void onItemClick(View view, int position) {
            ArrayList<Object> arrayList;
            if (PhotoPickerActivity.this.selectedAlbum != null) {
                arrayList = PhotoPickerActivity.this.selectedAlbum.photos;
            } else if (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) {
                arrayList = PhotoPickerActivity.this.recentImages;
            } else {
                arrayList = PhotoPickerActivity.this.searchResult;
            }
            if (position >= 0 && position < arrayList.size()) {
                if (PhotoPickerActivity.this.searchItem != null) {
                    AndroidUtilities.hideKeyboard(PhotoPickerActivity.this.searchItem.getSearchField());
                }
                PhotoViewer.getInstance().setParentActivity(PhotoPickerActivity.this.getParentActivity());
                PhotoViewer.getInstance().openPhotoForSelect(arrayList, position, PhotoPickerActivity.this.singlePhoto ? 1 : 0, PhotoPickerActivity.this.provider, PhotoPickerActivity.this.chatActivity);
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$7 */
    class C32027 implements OnItemLongClickListener {

        /* renamed from: org.telegram.ui.PhotoPickerActivity$7$1 */
        class C32011 implements OnClickListener {
            C32011() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                PhotoPickerActivity.this.recentImages.clear();
                if (PhotoPickerActivity.this.listAdapter != null) {
                    PhotoPickerActivity.this.listAdapter.notifyDataSetChanged();
                }
                MessagesStorage.getInstance().clearWebRecent(PhotoPickerActivity.this.type);
            }
        }

        C32027() {
        }

        public boolean onItemClick(View view, int position) {
            if (!PhotoPickerActivity.this.searchResult.isEmpty() || PhotoPickerActivity.this.lastSearchString != null) {
                return false;
            }
            Builder builder = new Builder(PhotoPickerActivity.this.getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setMessage(LocaleController.getString("ClearSearch", R.string.ClearSearch));
            builder.setPositiveButton(LocaleController.getString("ClearButton", R.string.ClearButton).toUpperCase(), new C32011());
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            PhotoPickerActivity.this.showDialog(builder.create());
            return true;
        }
    }

    /* renamed from: org.telegram.ui.PhotoPickerActivity$8 */
    class C32038 extends OnScrollListener {
        C32038() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == 1) {
                AndroidUtilities.hideKeyboard(PhotoPickerActivity.this.getParentActivity().getCurrentFocus());
            }
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int firstVisibleItem = PhotoPickerActivity.this.layoutManager.findFirstVisibleItemPosition();
            int visibleItemCount = firstVisibleItem == -1 ? 0 : Math.abs(PhotoPickerActivity.this.layoutManager.findLastVisibleItemPosition() - firstVisibleItem) + 1;
            if (visibleItemCount > 0) {
                int totalItemCount = PhotoPickerActivity.this.layoutManager.getItemCount();
                if (visibleItemCount != 0 && firstVisibleItem + visibleItemCount > totalItemCount - 2 && !PhotoPickerActivity.this.searching) {
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
    class C32049 implements View.OnClickListener {
        C32049() {
        }

        public void onClick(View view) {
            PhotoPickerActivity.this.delegate.actionButtonPressed(true);
            PhotoPickerActivity.this.finishFragment();
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.PhotoPickerActivity$ListAdapter$1 */
        class C32051 implements View.OnClickListener {
            C32051() {
            }

            public void onClick(View v) {
                boolean added = false;
                int num = -1;
                int index = ((Integer) ((View) v.getParent()).getTag()).intValue();
                if (PhotoPickerActivity.this.selectedAlbum != null) {
                    MediaController$PhotoEntry photoEntry = (MediaController$PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(index);
                    if (!PhotoPickerActivity.this.selectedPhotos.containsKey(Integer.valueOf(photoEntry.imageId))) {
                        added = true;
                    }
                    if (PhotoPickerActivity.this.allowIndices && added) {
                        num = PhotoPickerActivity.this.selectedPhotosOrder.size();
                    }
                    ((PhotoPickerPhotoCell) v.getParent()).setChecked(num, added, true);
                    PhotoPickerActivity.this.addToSelectedPhotos(photoEntry, index);
                } else {
                    MediaController$SearchImage photoEntry2;
                    AndroidUtilities.hideKeyboard(PhotoPickerActivity.this.getParentActivity().getCurrentFocus());
                    if (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) {
                        photoEntry2 = (MediaController$SearchImage) PhotoPickerActivity.this.recentImages.get(((Integer) ((View) v.getParent()).getTag()).intValue());
                    } else {
                        photoEntry2 = (MediaController$SearchImage) PhotoPickerActivity.this.searchResult.get(((Integer) ((View) v.getParent()).getTag()).intValue());
                    }
                    if (!PhotoPickerActivity.this.selectedPhotos.containsKey(photoEntry2.id)) {
                        added = true;
                    }
                    if (PhotoPickerActivity.this.allowIndices && added) {
                        num = PhotoPickerActivity.this.selectedPhotosOrder.size();
                    }
                    ((PhotoPickerPhotoCell) v.getParent()).setChecked(num, added, true);
                    PhotoPickerActivity.this.addToSelectedPhotos(photoEntry2, index);
                }
                PhotoPickerActivity.this.pickerBottomLayout.updateSelectedCount(PhotoPickerActivity.this.selectedPhotos.size(), true);
                PhotoPickerActivity.this.delegate.selectedPhotosChanged();
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public boolean isEnabled(ViewHolder holder) {
            if (PhotoPickerActivity.this.selectedAlbum != null) {
                return true;
            }
            int position = holder.getAdapterPosition();
            if (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) {
                if (position < PhotoPickerActivity.this.recentImages.size()) {
                    return true;
                }
                return false;
            } else if (position >= PhotoPickerActivity.this.searchResult.size()) {
                return false;
            } else {
                return true;
            }
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

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    View cell = new PhotoPickerPhotoCell(this.mContext, true);
                    cell.checkFrame.setOnClickListener(new C32051());
                    cell.checkFrame.setVisibility(PhotoPickerActivity.this.singlePhoto ? 8 : 0);
                    view = cell;
                    break;
                default:
                    View frameLayout = new FrameLayout(this.mContext);
                    view = frameLayout;
                    RadialProgressView progressBar = new RadialProgressView(this.mContext);
                    progressBar.setProgressColor(-1);
                    frameLayout.addView(progressBar, LayoutHelper.createFrame(-1, -1.0f));
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    boolean showing;
                    int i;
                    PhotoPickerPhotoCell cell = holder.itemView;
                    cell.itemWidth = PhotoPickerActivity.this.itemWidth;
                    BackupImageView imageView = cell.photoImage;
                    imageView.setTag(Integer.valueOf(position));
                    cell.setTag(Integer.valueOf(position));
                    imageView.setOrientation(0, true);
                    if (PhotoPickerActivity.this.selectedAlbum != null) {
                        MediaController$PhotoEntry photoEntry = (MediaController$PhotoEntry) PhotoPickerActivity.this.selectedAlbum.photos.get(position);
                        if (photoEntry.thumbPath != null) {
                            imageView.setImage(photoEntry.thumbPath, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                        } else if (photoEntry.path != null) {
                            imageView.setOrientation(photoEntry.orientation, true);
                            if (photoEntry.isVideo) {
                                cell.videoInfoContainer.setVisibility(0);
                                int seconds = photoEntry.duration - ((photoEntry.duration / 60) * 60);
                                cell.videoTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)}));
                                imageView.setImage("vthumb://" + photoEntry.imageId + ":" + photoEntry.path, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                            } else {
                                cell.videoInfoContainer.setVisibility(4);
                                imageView.setImage("thumb://" + photoEntry.imageId + ":" + photoEntry.path, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                            }
                        } else {
                            imageView.setImageResource(R.drawable.nophotos);
                        }
                        cell.setChecked(PhotoPickerActivity.this.allowIndices ? PhotoPickerActivity.this.selectedPhotosOrder.indexOf(Integer.valueOf(photoEntry.imageId)) : -1, PhotoPickerActivity.this.selectedPhotos.containsKey(Integer.valueOf(photoEntry.imageId)), false);
                        showing = PhotoViewer.getInstance().isShowingImage(photoEntry.path);
                    } else {
                        MediaController$SearchImage photoEntry2;
                        if (PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null) {
                            photoEntry2 = (MediaController$SearchImage) PhotoPickerActivity.this.recentImages.get(position);
                        } else {
                            photoEntry2 = (MediaController$SearchImage) PhotoPickerActivity.this.searchResult.get(position);
                        }
                        if (photoEntry2.thumbPath != null) {
                            imageView.setImage(photoEntry2.thumbPath, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                        } else if (photoEntry2.thumbUrl != null && photoEntry2.thumbUrl.length() > 0) {
                            imageView.setImage(photoEntry2.thumbUrl, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                        } else if (photoEntry2.document == null || photoEntry2.document.thumb == null) {
                            imageView.setImageResource(R.drawable.nophotos);
                        } else {
                            imageView.setImage(photoEntry2.document.thumb.location, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                        }
                        cell.videoInfoContainer.setVisibility(4);
                        cell.setChecked(PhotoPickerActivity.this.allowIndices ? PhotoPickerActivity.this.selectedPhotosOrder.indexOf(photoEntry2.id) : -1, PhotoPickerActivity.this.selectedPhotos.containsKey(photoEntry2.id), false);
                        if (photoEntry2.document != null) {
                            showing = PhotoViewer.getInstance().isShowingImage(FileLoader.getPathToAttach(photoEntry2.document, true).getAbsolutePath());
                        } else {
                            showing = PhotoViewer.getInstance().isShowingImage(photoEntry2.imageUrl);
                        }
                    }
                    imageView.getImageReceiver().setVisible(!showing, true);
                    CheckBox checkBox = cell.checkBox;
                    if (PhotoPickerActivity.this.singlePhoto || showing) {
                        i = 8;
                    } else {
                        i = 0;
                    }
                    checkBox.setVisibility(i);
                    return;
                case 1:
                    LayoutParams params = holder.itemView.getLayoutParams();
                    if (params != null) {
                        params.width = PhotoPickerActivity.this.itemWidth;
                        params.height = PhotoPickerActivity.this.itemWidth;
                        holder.itemView.setLayoutParams(params);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        public int getItemViewType(int i) {
            if (PhotoPickerActivity.this.selectedAlbum != null || ((PhotoPickerActivity.this.searchResult.isEmpty() && PhotoPickerActivity.this.lastSearchString == null && i < PhotoPickerActivity.this.recentImages.size()) || i < PhotoPickerActivity.this.searchResult.size())) {
                return 0;
            }
            return 1;
        }
    }

    public PhotoPickerActivity(int type, MediaController$AlbumEntry selectedAlbum, HashMap<Object, Object> selectedPhotos, ArrayList<Object> selectedPhotosOrder, ArrayList<MediaController$SearchImage> recentImages, boolean onlyOnePhoto, boolean allowCaption, ChatActivity chatActivity) {
        this.selectedAlbum = selectedAlbum;
        this.selectedPhotos = selectedPhotos;
        this.selectedPhotosOrder = selectedPhotosOrder;
        this.type = type;
        this.recentImages = recentImages;
        this.singlePhoto = onlyOnePhoto;
        this.chatActivity = chatActivity;
        this.allowCaption = allowCaption;
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

    public View createView(Context context) {
        boolean z;
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
        this.actionBar.setActionBarMenuOnItemClick(new C31962());
        if (this.selectedAlbum == null) {
            this.searchItem = this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C31973());
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
        this.frameLayout.setBackgroundColor(-16777216);
        this.listView = new RecyclerListView(context);
        this.listView.setPadding(AndroidUtilities.dp(4.0f), AndroidUtilities.dp(4.0f), AndroidUtilities.dp(4.0f), AndroidUtilities.dp(4.0f));
        this.listView.setClipToPadding(false);
        this.listView.setHorizontalScrollBarEnabled(false);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        RecyclerListView recyclerListView = this.listView;
        LayoutManager c31984 = new GridLayoutManager(context, 4) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.layoutManager = c31984;
        recyclerListView.setLayoutManager(c31984);
        this.listView.addItemDecoration(new C31995());
        this.frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, this.singlePhoto ? 0.0f : 48.0f));
        recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setGlowColor(Theme.ACTION_BAR_MEDIA_PICKER_COLOR);
        this.listView.setOnItemClickListener(new C32006());
        if (this.selectedAlbum == null) {
            this.listView.setOnItemLongClickListener(new C32027());
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
        this.frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, this.singlePhoto ? 0.0f : 48.0f));
        if (this.selectedAlbum == null) {
            this.listView.setOnScrollListener(new C32038());
            updateSearchInterface();
        }
        this.pickerBottomLayout = new PickerBottomLayout(context);
        this.frameLayout.addView(this.pickerBottomLayout, LayoutHelper.createFrame(-1, 48, 80));
        this.pickerBottomLayout.cancelButton.setOnClickListener(new C32049());
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
                public void onClick(View v) {
                    MediaController.getInstance().toggleGroupPhotosEnabled();
                    PhotoPickerActivity.this.imageOrderToggleButton.setColorFilter(MediaController.getInstance().isGroupPhotosEnabled() ? new PorterDuffColorFilter(-10043398, Mode.MULTIPLY) : null);
                    PhotoPickerActivity.this.showHint(false, MediaController.getInstance().isGroupPhotosEnabled());
                    PhotoPickerActivity.this.updateCheckedPhotoIndices();
                }
            });
            this.imageOrderToggleButton.setColorFilter(MediaController.getInstance().isGroupPhotosEnabled() ? new PorterDuffColorFilter(-10043398, Mode.MULTIPLY) : null);
        }
        if (this.selectedAlbum != null || this.type == 0) {
            z = true;
        } else {
            z = false;
        }
        this.allowIndices = z;
        this.listView.setEmptyView(this.emptyView);
        this.pickerBottomLayout.updateSelectedCount(this.selectedPhotos.size(), true);
        return this.fragmentView;
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

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        fixLayout();
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.closeChats) {
            removeSelfFromStack();
        } else if (id == NotificationCenter.recentImagesDidLoaded && this.selectedAlbum == null && this.type == ((Integer) args[0]).intValue()) {
            this.recentImages = (ArrayList) args[1];
            this.loadingRecent = false;
            updateSearchInterface();
        }
    }

    private void hideHint() {
        this.hintAnimation = new AnimatorSet();
        AnimatorSet animatorSet = this.hintAnimation;
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(this.hintTextView, "alpha", new float[]{0.0f});
        animatorSet.playTogether(animatorArr);
        this.hintAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (animation.equals(PhotoPickerActivity.this.hintAnimation)) {
                    PhotoPickerActivity.this.hintAnimation = null;
                    PhotoPickerActivity.this.hintHideRunnable = null;
                    if (PhotoPickerActivity.this.hintTextView != null) {
                        PhotoPickerActivity.this.hintTextView.setVisibility(8);
                    }
                }
            }

            public void onAnimationCancel(Animator animation) {
                if (animation.equals(PhotoPickerActivity.this.hintAnimation)) {
                    PhotoPickerActivity.this.hintHideRunnable = null;
                    PhotoPickerActivity.this.hintHideRunnable = null;
                }
            }
        });
        this.hintAnimation.setDuration(300);
        this.hintAnimation.start();
    }

    private void showHint(boolean hide, boolean enabled) {
        if (getParentActivity() != null && this.fragmentView != null) {
            if (!hide || this.hintTextView != null) {
                if (this.hintTextView == null) {
                    this.hintTextView = new TextView(getParentActivity());
                    this.hintTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
                    this.hintTextView.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
                    this.hintTextView.setTextSize(1, 14.0f);
                    this.hintTextView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f));
                    this.hintTextView.setGravity(16);
                    this.hintTextView.setAlpha(0.0f);
                    this.frameLayout.addView(this.hintTextView, LayoutHelper.createFrame(-2, -2.0f, 81, 5.0f, 0.0f, 5.0f, 51.0f));
                }
                if (hide) {
                    if (this.hintAnimation != null) {
                        this.hintAnimation.cancel();
                        this.hintAnimation = null;
                    }
                    AndroidUtilities.cancelRunOnUIThread(this.hintHideRunnable);
                    this.hintHideRunnable = null;
                    hideHint();
                    return;
                }
                this.hintTextView.setText(enabled ? LocaleController.getString("GroupPhotosHelp", R.string.GroupPhotosHelp) : LocaleController.getString("SinglePhotosHelp", R.string.SinglePhotosHelp));
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
                        AndroidUtilities.runOnUIThread(anonymousClass13, FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
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
                    class C31931 implements Runnable {
                        C31931() {
                        }

                        public void run() {
                            PhotoPickerActivity.this.hideHint();
                        }
                    }

                    public void onAnimationEnd(Animator animation) {
                        if (animation.equals(PhotoPickerActivity.this.hintAnimation)) {
                            PhotoPickerActivity.this.hintAnimation = null;
                            AndroidUtilities.runOnUIThread(PhotoPickerActivity.this.hintHideRunnable = new C31931(), FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (animation.equals(PhotoPickerActivity.this.hintAnimation)) {
                            PhotoPickerActivity.this.hintAnimation = null;
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
            int count = this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View view = this.listView.getChildAt(a);
                if (view instanceof PhotoPickerPhotoCell) {
                    int indexOf;
                    PhotoPickerPhotoCell cell = (PhotoPickerPhotoCell) view;
                    MediaController$PhotoEntry photoEntry = (MediaController$PhotoEntry) this.selectedAlbum.photos.get(((Integer) cell.getTag()).intValue());
                    if (this.allowIndices) {
                        indexOf = this.selectedPhotosOrder.indexOf(Integer.valueOf(photoEntry.imageId));
                    } else {
                        indexOf = -1;
                    }
                    cell.setNum(indexOf);
                }
            }
        }
    }

    private PhotoPickerPhotoCell getCellForIndex(int index) {
        int count = this.listView.getChildCount();
        for (int a = 0; a < count; a++) {
            View view = this.listView.getChildAt(a);
            if (view instanceof PhotoPickerPhotoCell) {
                PhotoPickerPhotoCell cell = (PhotoPickerPhotoCell) view;
                int num = ((Integer) cell.photoImage.getTag()).intValue();
                if (this.selectedAlbum == null) {
                    ArrayList<MediaController$SearchImage> array;
                    if (this.searchResult.isEmpty() && this.lastSearchString == null) {
                        array = this.recentImages;
                    } else {
                        array = this.searchResult;
                    }
                    if (num < 0) {
                        continue;
                    } else if (num >= array.size()) {
                        continue;
                    }
                } else if (num < 0) {
                    continue;
                } else if (num >= this.selectedAlbum.photos.size()) {
                    continue;
                }
                if (num == index) {
                    return cell;
                }
            }
        }
        return null;
    }

    private int addToSelectedPhotos(Object object, int index) {
        int i = -1;
        Object key = null;
        if (object instanceof MediaController$PhotoEntry) {
            key = Integer.valueOf(((MediaController$PhotoEntry) object).imageId);
        } else if (object instanceof MediaController$SearchImage) {
            key = ((MediaController$SearchImage) object).id;
        }
        if (key != null) {
            if (this.selectedPhotos.containsKey(key)) {
                this.selectedPhotos.remove(key);
                i = this.selectedPhotosOrder.indexOf(key);
                if (i >= 0) {
                    this.selectedPhotosOrder.remove(i);
                }
                if (this.allowIndices) {
                    updateCheckedPhotoIndices();
                }
                if (index >= 0) {
                    if (object instanceof MediaController$PhotoEntry) {
                        ((MediaController$PhotoEntry) object).reset();
                    } else if (object instanceof MediaController$SearchImage) {
                        ((MediaController$SearchImage) object).reset();
                    }
                    this.provider.updatePhotoAtIndex(index);
                }
            } else {
                this.selectedPhotos.put(key, object);
                this.selectedPhotosOrder.add(key);
            }
        }
        return i;
    }

    public void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if (isOpen && this.searchItem != null) {
            AndroidUtilities.showKeyboard(this.searchItem.getSearchField());
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

    private void searchGiphyImages(final String query, int offset) {
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
        TLRPC$TL_messages_searchGifs req = new TLRPC$TL_messages_searchGifs();
        req.f88q = query;
        req.offset = offset;
        final int token = this.lastSearchToken + 1;
        this.lastSearchToken = token;
        this.giphyReqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
            public void run(final TLObject response, TLRPC$TL_error error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        boolean z = true;
                        if (token == PhotoPickerActivity.this.lastSearchToken) {
                            int addedCount = 0;
                            if (response != null) {
                                boolean added = false;
                                TLRPC$TL_messages_foundGifs res = response;
                                PhotoPickerActivity.this.nextGiphySearchOffset = res.next_offset;
                                for (int a = 0; a < res.results.size(); a++) {
                                    TLRPC$FoundGif gif = (TLRPC$FoundGif) res.results.get(a);
                                    if (!PhotoPickerActivity.this.searchResultKeys.containsKey(gif.url)) {
                                        added = true;
                                        MediaController$SearchImage bingImage = new MediaController$SearchImage();
                                        bingImage.id = gif.url;
                                        if (gif.document != null) {
                                            for (int b = 0; b < gif.document.attributes.size(); b++) {
                                                DocumentAttribute attribute = (DocumentAttribute) gif.document.attributes.get(b);
                                                if ((attribute instanceof TLRPC$TL_documentAttributeImageSize) || (attribute instanceof TLRPC$TL_documentAttributeVideo)) {
                                                    bingImage.width = attribute.f44w;
                                                    bingImage.height = attribute.f43h;
                                                    break;
                                                }
                                            }
                                        } else {
                                            bingImage.width = gif.f73w;
                                            bingImage.height = gif.f72h;
                                        }
                                        bingImage.size = 0;
                                        bingImage.imageUrl = gif.content_url;
                                        bingImage.thumbUrl = gif.thumb_url;
                                        bingImage.localUrl = gif.url + "|" + query;
                                        bingImage.document = gif.document;
                                        if (!(gif.photo == null || gif.document == null)) {
                                            TLRPC$PhotoSize size = FileLoader.getClosestPhotoSizeWithSize(gif.photo.sizes, PhotoPickerActivity.this.itemWidth, true);
                                            if (size != null) {
                                                gif.document.thumb = size;
                                            }
                                        }
                                        bingImage.type = 1;
                                        PhotoPickerActivity.this.searchResult.add(bingImage);
                                        addedCount++;
                                        PhotoPickerActivity.this.searchResultKeys.put(bingImage.id, bingImage);
                                    }
                                }
                                PhotoPickerActivity photoPickerActivity = PhotoPickerActivity.this;
                                if (added) {
                                    z = false;
                                }
                                photoPickerActivity.giphySearchEndReached = z;
                            }
                            PhotoPickerActivity.this.searching = false;
                            if (addedCount != 0) {
                                PhotoPickerActivity.this.listAdapter.notifyItemRangeInserted(PhotoPickerActivity.this.searchResult.size(), addedCount);
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

    private void searchBingImages(String query, int offset, int count) {
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
            boolean adult;
            this.searching = true;
            String phone = UserConfig.getCurrentUser().phone;
            if (phone.startsWith("44") || phone.startsWith(BuildConfig.DISTRIBUTOR_CODE) || phone.startsWith("43") || phone.startsWith("31") || phone.startsWith(com.thin.downloadmanager.BuildConfig.VERSION_NAME)) {
                adult = true;
            } else {
                adult = false;
            }
            Locale locale = Locale.US;
            String str = "https://api.cognitive.microsoft.com/bing/v5.0/images/search?q='%s'&offset=%d&count=%d&$format=json&safeSearch=%s";
            Object[] objArr = new Object[4];
            objArr[0] = URLEncoder.encode(query, "UTF-8");
            objArr[1] = Integer.valueOf(offset);
            objArr[2] = Integer.valueOf(count);
            objArr[3] = adult ? "Strict" : "Off";
            final String url = String.format(locale, str, objArr);
            this.currentBingTask = new AsyncTask<Void, Void, JSONObject>() {
                private boolean canRetry = true;

                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                private java.lang.String downloadUrlContent(java.lang.String r22) {
                    /*
                    r21 = this;
                    r3 = 1;
                    r11 = 0;
                    r7 = 0;
                    r15 = 0;
                    r10 = 0;
                    r8 = new java.net.URL;	 Catch:{ Throwable -> 0x0136 }
                    r0 = r22;
                    r8.<init>(r0);	 Catch:{ Throwable -> 0x0136 }
                    r10 = r8.openConnection();	 Catch:{ Throwable -> 0x0136 }
                    r18 = "Ocp-Apim-Subscription-Key";
                    r19 = org.telegram.messenger.BuildVars.BING_SEARCH_KEY;	 Catch:{ Throwable -> 0x0136 }
                    r0 = r18;
                    r1 = r19;
                    r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x0136 }
                    r18 = "User-Agent";
                    r19 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
                    r0 = r18;
                    r1 = r19;
                    r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x0136 }
                    r18 = "Accept-Language";
                    r19 = "en-us,en;q=0.5";
                    r0 = r18;
                    r1 = r19;
                    r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x0136 }
                    r18 = "Accept";
                    r19 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
                    r0 = r18;
                    r1 = r19;
                    r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x0136 }
                    r18 = "Accept-Charset";
                    r19 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
                    r0 = r18;
                    r1 = r19;
                    r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x0136 }
                    r18 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
                    r0 = r18;
                    r10.setConnectTimeout(r0);	 Catch:{ Throwable -> 0x0136 }
                    r18 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
                    r0 = r18;
                    r10.setReadTimeout(r0);	 Catch:{ Throwable -> 0x0136 }
                    r0 = r10 instanceof java.net.HttpURLConnection;	 Catch:{ Throwable -> 0x0136 }
                    r18 = r0;
                    if (r18 == 0) goto L_0x00ee;
                L_0x0064:
                    r0 = r10;
                    r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x0136 }
                    r12 = r0;
                    r18 = 1;
                    r0 = r18;
                    r12.setInstanceFollowRedirects(r0);	 Catch:{ Throwable -> 0x0136 }
                    r17 = r12.getResponseCode();	 Catch:{ Throwable -> 0x0136 }
                    r18 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
                    r0 = r17;
                    r1 = r18;
                    if (r0 == r1) goto L_0x008b;
                L_0x007b:
                    r18 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
                    r0 = r17;
                    r1 = r18;
                    if (r0 == r1) goto L_0x008b;
                L_0x0083:
                    r18 = 303; // 0x12f float:4.25E-43 double:1.497E-321;
                    r0 = r17;
                    r1 = r18;
                    if (r0 != r1) goto L_0x00ee;
                L_0x008b:
                    r18 = "Location";
                    r0 = r18;
                    r13 = r12.getHeaderField(r0);	 Catch:{ Throwable -> 0x0136 }
                    r18 = "Set-Cookie";
                    r0 = r18;
                    r5 = r12.getHeaderField(r0);	 Catch:{ Throwable -> 0x0136 }
                    r8 = new java.net.URL;	 Catch:{ Throwable -> 0x0136 }
                    r8.<init>(r13);	 Catch:{ Throwable -> 0x0136 }
                    r10 = r8.openConnection();	 Catch:{ Throwable -> 0x0136 }
                    r18 = "Cookie";
                    r0 = r18;
                    r10.setRequestProperty(r0, r5);	 Catch:{ Throwable -> 0x0136 }
                    r18 = "Ocp-Apim-Subscription-Key";
                    r19 = org.telegram.messenger.BuildVars.BING_SEARCH_KEY;	 Catch:{ Throwable -> 0x0136 }
                    r0 = r18;
                    r1 = r19;
                    r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x0136 }
                    r18 = "User-Agent";
                    r19 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
                    r0 = r18;
                    r1 = r19;
                    r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x0136 }
                    r18 = "Accept-Language";
                    r19 = "en-us,en;q=0.5";
                    r0 = r18;
                    r1 = r19;
                    r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x0136 }
                    r18 = "Accept";
                    r19 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
                    r0 = r18;
                    r1 = r19;
                    r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x0136 }
                    r18 = "Accept-Charset";
                    r19 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
                    r0 = r18;
                    r1 = r19;
                    r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x0136 }
                L_0x00ee:
                    r10.connect();	 Catch:{ Throwable -> 0x0136 }
                    r11 = r10.getInputStream();	 Catch:{ Throwable -> 0x0136 }
                L_0x00f5:
                    if (r3 == 0) goto L_0x012f;
                L_0x00f7:
                    if (r10 == 0) goto L_0x0117;
                L_0x00f9:
                    r0 = r10 instanceof java.net.HttpURLConnection;	 Catch:{ Exception -> 0x0173 }
                    r18 = r0;
                    if (r18 == 0) goto L_0x0117;
                L_0x00ff:
                    r10 = (java.net.HttpURLConnection) r10;	 Catch:{ Exception -> 0x0173 }
                    r4 = r10.getResponseCode();	 Catch:{ Exception -> 0x0173 }
                    r18 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
                    r0 = r18;
                    if (r4 == r0) goto L_0x0117;
                L_0x010b:
                    r18 = 202; // 0xca float:2.83E-43 double:1.0E-321;
                    r0 = r18;
                    if (r4 == r0) goto L_0x0117;
                L_0x0111:
                    r18 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
                    r0 = r18;
                    if (r4 == r0) goto L_0x0117;
                L_0x0117:
                    if (r11 == 0) goto L_0x012a;
                L_0x0119:
                    r18 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
                    r0 = r18;
                    r6 = new byte[r0];	 Catch:{ Throwable -> 0x01b2 }
                    r16 = r15;
                L_0x0122:
                    r18 = r21.isCancelled();	 Catch:{ Throwable -> 0x01c2 }
                    if (r18 == 0) goto L_0x0178;
                L_0x0128:
                    r15 = r16;
                L_0x012a:
                    if (r11 == 0) goto L_0x012f;
                L_0x012c:
                    r11.close();	 Catch:{ Throwable -> 0x01b8 }
                L_0x012f:
                    if (r7 == 0) goto L_0x01be;
                L_0x0131:
                    r18 = r15.toString();
                L_0x0135:
                    return r18;
                L_0x0136:
                    r9 = move-exception;
                    r0 = r9 instanceof java.net.SocketTimeoutException;
                    r18 = r0;
                    if (r18 == 0) goto L_0x0148;
                L_0x013d:
                    r18 = org.telegram.tgnet.ConnectionsManager.isNetworkOnline();
                    if (r18 == 0) goto L_0x0144;
                L_0x0143:
                    r3 = 0;
                L_0x0144:
                    org.telegram.messenger.FileLog.e(r9);
                    goto L_0x00f5;
                L_0x0148:
                    r0 = r9 instanceof java.net.UnknownHostException;
                    r18 = r0;
                    if (r18 == 0) goto L_0x0150;
                L_0x014e:
                    r3 = 0;
                    goto L_0x0144;
                L_0x0150:
                    r0 = r9 instanceof java.net.SocketException;
                    r18 = r0;
                    if (r18 == 0) goto L_0x016b;
                L_0x0156:
                    r18 = r9.getMessage();
                    if (r18 == 0) goto L_0x0144;
                L_0x015c:
                    r18 = r9.getMessage();
                    r19 = "ECONNRESET";
                    r18 = r18.contains(r19);
                    if (r18 == 0) goto L_0x0144;
                L_0x0169:
                    r3 = 0;
                    goto L_0x0144;
                L_0x016b:
                    r0 = r9 instanceof java.io.FileNotFoundException;
                    r18 = r0;
                    if (r18 == 0) goto L_0x0144;
                L_0x0171:
                    r3 = 0;
                    goto L_0x0144;
                L_0x0173:
                    r9 = move-exception;
                    org.telegram.messenger.FileLog.e(r9);
                    goto L_0x0117;
                L_0x0178:
                    r14 = r11.read(r6);	 Catch:{ Exception -> 0x01aa }
                    if (r14 <= 0) goto L_0x019d;
                L_0x017e:
                    if (r16 != 0) goto L_0x01c8;
                L_0x0180:
                    r15 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01aa }
                    r15.<init>();	 Catch:{ Exception -> 0x01aa }
                L_0x0185:
                    r18 = new java.lang.String;	 Catch:{ Exception -> 0x01c6 }
                    r19 = 0;
                    r20 = "UTF-8";
                    r0 = r18;
                    r1 = r19;
                    r2 = r20;
                    r0.<init>(r6, r1, r14, r2);	 Catch:{ Exception -> 0x01c6 }
                    r0 = r18;
                    r15.append(r0);	 Catch:{ Exception -> 0x01c6 }
                    r16 = r15;
                    goto L_0x0122;
                L_0x019d:
                    r18 = -1;
                    r0 = r18;
                    if (r14 != r0) goto L_0x01a7;
                L_0x01a3:
                    r7 = 1;
                    r15 = r16;
                    goto L_0x012a;
                L_0x01a7:
                    r15 = r16;
                    goto L_0x012a;
                L_0x01aa:
                    r9 = move-exception;
                    r15 = r16;
                L_0x01ad:
                    org.telegram.messenger.FileLog.e(r9);	 Catch:{ Throwable -> 0x01b2 }
                    goto L_0x012a;
                L_0x01b2:
                    r9 = move-exception;
                L_0x01b3:
                    org.telegram.messenger.FileLog.e(r9);
                    goto L_0x012a;
                L_0x01b8:
                    r9 = move-exception;
                    org.telegram.messenger.FileLog.e(r9);
                    goto L_0x012f;
                L_0x01be:
                    r18 = 0;
                    goto L_0x0135;
                L_0x01c2:
                    r9 = move-exception;
                    r15 = r16;
                    goto L_0x01b3;
                L_0x01c6:
                    r9 = move-exception;
                    goto L_0x01ad;
                L_0x01c8:
                    r15 = r16;
                    goto L_0x0185;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PhotoPickerActivity.16.downloadUrlContent(java.lang.String):java.lang.String");
                }

                protected JSONObject doInBackground(Void... voids) {
                    String code = downloadUrlContent(url);
                    if (isCancelled()) {
                        return null;
                    }
                    try {
                        return new JSONObject(code);
                    } catch (Exception e) {
                        FileLog.e(e);
                        return null;
                    }
                }

                protected void onPostExecute(JSONObject response) {
                    boolean z = true;
                    int addedCount = 0;
                    if (response != null) {
                        try {
                            JSONArray result = response.getJSONArray(Param.VALUE);
                            boolean added = false;
                            for (int a = 0; a < result.length(); a++) {
                                try {
                                    JSONObject object = result.getJSONObject(a);
                                    String id = Utilities.MD5(object.getString("contentUrl"));
                                    if (!PhotoPickerActivity.this.searchResultKeys.containsKey(id)) {
                                        MediaController$SearchImage bingImage = new MediaController$SearchImage();
                                        bingImage.id = id;
                                        bingImage.width = object.getInt(SettingsJsonConstants.ICON_WIDTH_KEY);
                                        bingImage.height = object.getInt("height");
                                        bingImage.size = Utilities.parseInt(object.getString("contentSize")).intValue();
                                        bingImage.imageUrl = object.getString("contentUrl");
                                        bingImage.thumbUrl = object.getString("thumbnailUrl");
                                        PhotoPickerActivity.this.searchResult.add(bingImage);
                                        PhotoPickerActivity.this.searchResultKeys.put(id, bingImage);
                                        addedCount++;
                                        added = true;
                                    }
                                } catch (Exception e) {
                                    FileLog.e(e);
                                }
                            }
                            PhotoPickerActivity photoPickerActivity = PhotoPickerActivity.this;
                            if (added) {
                                z = false;
                            }
                            photoPickerActivity.bingSearchEndReached = z;
                        } catch (Exception e2) {
                            FileLog.e(e2);
                        }
                        PhotoPickerActivity.this.searching = false;
                    } else {
                        PhotoPickerActivity.this.bingSearchEndReached = true;
                        PhotoPickerActivity.this.searching = false;
                    }
                    if (addedCount != 0) {
                        PhotoPickerActivity.this.listAdapter.notifyItemRangeInserted(PhotoPickerActivity.this.searchResult.size(), addedCount);
                    } else if (PhotoPickerActivity.this.giphySearchEndReached) {
                        PhotoPickerActivity.this.listAdapter.notifyItemRemoved(PhotoPickerActivity.this.searchResult.size() - 1);
                    }
                    if ((PhotoPickerActivity.this.searching && PhotoPickerActivity.this.searchResult.isEmpty()) || (PhotoPickerActivity.this.loadingRecent && PhotoPickerActivity.this.lastSearchString == null)) {
                        PhotoPickerActivity.this.emptyView.showProgress();
                    } else {
                        PhotoPickerActivity.this.emptyView.showTextView();
                    }
                }
            };
            this.currentBingTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
        } catch (Exception e) {
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

    public void setDelegate(PhotoPickerActivityDelegate delegate) {
        this.delegate = delegate;
    }

    private void sendSelectedPhotos() {
        if (!this.selectedPhotos.isEmpty() && this.delegate != null && !this.sendPressed) {
            this.sendPressed = true;
            this.delegate.actionButtonPressed(false);
            finishFragment();
        }
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
            int columnsCount;
            int position = this.layoutManager.findFirstVisibleItemPosition();
            int rotation = ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
            if (AndroidUtilities.isTablet()) {
                columnsCount = 3;
            } else if (rotation == 3 || rotation == 1) {
                columnsCount = 5;
            } else {
                columnsCount = 3;
            }
            this.layoutManager.setSpanCount(columnsCount);
            if (AndroidUtilities.isTablet()) {
                this.itemWidth = (AndroidUtilities.dp(490.0f) - ((columnsCount + 1) * AndroidUtilities.dp(4.0f))) / columnsCount;
            } else {
                this.itemWidth = (AndroidUtilities.displaySize.x - ((columnsCount + 1) * AndroidUtilities.dp(4.0f))) / columnsCount;
            }
            this.listAdapter.notifyDataSetChanged();
            this.layoutManager.scrollToPosition(position);
            if (this.selectedAlbum == null) {
                this.emptyView.setPadding(0, 0, 0, (int) (((float) (AndroidUtilities.displaySize.y - ActionBar.getCurrentActionBarHeight())) * 0.4f));
            }
        }
    }
}
