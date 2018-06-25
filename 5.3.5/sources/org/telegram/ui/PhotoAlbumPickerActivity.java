package org.telegram.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController$AlbumEntry;
import org.telegram.messenger.MediaController$PhotoEntry;
import org.telegram.messenger.MediaController$SearchImage;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper$SendingMediaInfo;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.PhotoPickerAlbumsCell;
import org.telegram.ui.Cells.PhotoPickerAlbumsCell.PhotoPickerAlbumsCellDelegate;
import org.telegram.ui.Cells.PhotoPickerSearchCell;
import org.telegram.ui.Cells.PhotoPickerSearchCell.PhotoPickerSearchCellDelegate;
import org.telegram.ui.Components.PickerBottomLayout;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.PhotoPickerActivity.PhotoPickerActivityDelegate;

public class PhotoAlbumPickerActivity extends BaseFragment implements NotificationCenterDelegate {
    private ArrayList<MediaController$AlbumEntry> albumsSorted = null;
    private boolean allowCaption;
    private boolean allowGifs;
    private ChatActivity chatActivity;
    private int columnsCount = 2;
    private PhotoAlbumPickerActivityDelegate delegate;
    private TextView emptyView;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private boolean loading = false;
    private PickerBottomLayout pickerBottomLayout;
    private FrameLayout progressView;
    private ArrayList<MediaController$SearchImage> recentGifImages = new ArrayList();
    private HashMap<String, MediaController$SearchImage> recentImagesGifKeys = new HashMap();
    private HashMap<String, MediaController$SearchImage> recentImagesWebKeys = new HashMap();
    private ArrayList<MediaController$SearchImage> recentWebImages = new ArrayList();
    private HashMap<Object, Object> selectedPhotos = new HashMap();
    private ArrayList<Object> selectedPhotosOrder = new ArrayList();
    private boolean sendPressed;
    private boolean singlePhoto;

    public interface PhotoAlbumPickerActivityDelegate {
        void didSelectPhotos(ArrayList<SendMessagesHelper$SendingMediaInfo> arrayList);

        void startPhotoSelectActivity();
    }

    /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$1 */
    class C31821 extends ActionBarMenuOnItemClick {
        C31821() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                PhotoAlbumPickerActivity.this.finishFragment();
            } else if (id == 1 && PhotoAlbumPickerActivity.this.delegate != null) {
                PhotoAlbumPickerActivity.this.finishFragment(false);
                PhotoAlbumPickerActivity.this.delegate.startPhotoSelectActivity();
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$2 */
    class C31832 implements OnTouchListener {
        C31832() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$3 */
    class C31843 implements OnClickListener {
        C31843() {
        }

        public void onClick(View view) {
            PhotoAlbumPickerActivity.this.finishFragment();
        }
    }

    /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$4 */
    class C31854 implements OnClickListener {
        C31854() {
        }

        public void onClick(View view) {
            PhotoAlbumPickerActivity.this.sendSelectedPhotos(PhotoAlbumPickerActivity.this.selectedPhotos, PhotoAlbumPickerActivity.this.selectedPhotosOrder);
            PhotoAlbumPickerActivity.this.finishFragment();
        }
    }

    /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$5 */
    class C31865 implements OnPreDrawListener {
        C31865() {
        }

        public boolean onPreDraw() {
            PhotoAlbumPickerActivity.this.fixLayoutInternal();
            if (PhotoAlbumPickerActivity.this.listView != null) {
                PhotoAlbumPickerActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            return true;
        }
    }

    /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$6 */
    class C31876 implements PhotoPickerActivityDelegate {
        C31876() {
        }

        public void selectedPhotosChanged() {
            if (PhotoAlbumPickerActivity.this.pickerBottomLayout != null) {
                PhotoAlbumPickerActivity.this.pickerBottomLayout.updateSelectedCount(PhotoAlbumPickerActivity.this.selectedPhotos.size(), true);
            }
        }

        public void actionButtonPressed(boolean canceled) {
            PhotoAlbumPickerActivity.this.removeSelfFromStack();
            if (!canceled) {
                PhotoAlbumPickerActivity.this.sendSelectedPhotos(PhotoAlbumPickerActivity.this.selectedPhotos, PhotoAlbumPickerActivity.this.selectedPhotosOrder);
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$ListAdapter$1 */
        class C31891 implements PhotoPickerAlbumsCellDelegate {
            C31891() {
            }

            public void didSelectAlbum(MediaController$AlbumEntry albumEntry) {
                PhotoAlbumPickerActivity.this.openPhotoPicker(albumEntry, 0);
            }
        }

        /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$ListAdapter$2 */
        class C31902 implements PhotoPickerSearchCellDelegate {
            C31902() {
            }

            public void didPressedSearchButton(int index) {
                PhotoAlbumPickerActivity.this.openPhotoPicker(null, index);
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public boolean isEnabled(ViewHolder holder) {
            return true;
        }

        public int getItemCount() {
            int i = 0;
            if (!PhotoAlbumPickerActivity.this.singlePhoto) {
                if (PhotoAlbumPickerActivity.this.albumsSorted != null) {
                    i = (int) Math.ceil((double) (((float) PhotoAlbumPickerActivity.this.albumsSorted.size()) / ((float) PhotoAlbumPickerActivity.this.columnsCount)));
                }
                return i + 1;
            } else if (PhotoAlbumPickerActivity.this.albumsSorted != null) {
                return (int) Math.ceil((double) (((float) PhotoAlbumPickerActivity.this.albumsSorted.size()) / ((float) PhotoAlbumPickerActivity.this.columnsCount)));
            } else {
                return 0;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            View cell;
            switch (viewType) {
                case 0:
                    cell = new PhotoPickerAlbumsCell(this.mContext);
                    cell.setDelegate(new C31891());
                    view = cell;
                    break;
                default:
                    cell = new PhotoPickerSearchCell(this.mContext, PhotoAlbumPickerActivity.this.allowGifs);
                    cell.setDelegate(new C31902());
                    view = cell;
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder.getItemViewType() == 0) {
                PhotoPickerAlbumsCell photoPickerAlbumsCell = holder.itemView;
                photoPickerAlbumsCell.setAlbumsCount(PhotoAlbumPickerActivity.this.columnsCount);
                for (int a = 0; a < PhotoAlbumPickerActivity.this.columnsCount; a++) {
                    int index;
                    if (PhotoAlbumPickerActivity.this.singlePhoto) {
                        index = (PhotoAlbumPickerActivity.this.columnsCount * position) + a;
                    } else {
                        index = ((position - 1) * PhotoAlbumPickerActivity.this.columnsCount) + a;
                    }
                    if (index < PhotoAlbumPickerActivity.this.albumsSorted.size()) {
                        photoPickerAlbumsCell.setAlbum(a, (MediaController$AlbumEntry) PhotoAlbumPickerActivity.this.albumsSorted.get(index));
                    } else {
                        photoPickerAlbumsCell.setAlbum(a, null);
                    }
                }
                photoPickerAlbumsCell.requestLayout();
            }
        }

        public int getItemViewType(int i) {
            if (!PhotoAlbumPickerActivity.this.singlePhoto && i == 0) {
                return 1;
            }
            return 0;
        }
    }

    public PhotoAlbumPickerActivity(boolean singlePhoto, boolean allowGifs, boolean allowCaption, ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
        this.singlePhoto = singlePhoto;
        this.allowGifs = allowGifs;
        this.allowCaption = allowCaption;
    }

    public boolean onFragmentCreate() {
        this.loading = true;
        MediaController.loadGalleryPhotosAlbums(this.classGuid);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.albumsDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.recentImagesDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeChats);
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.albumsDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.recentImagesDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
        super.onFragmentDestroy();
    }

    public View createView(Context context) {
        this.actionBar.setBackgroundColor(Theme.ACTION_BAR_MEDIA_PICKER_COLOR);
        this.actionBar.setTitleColor(-1);
        this.actionBar.setItemsBackgroundColor(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, false);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setActionBarMenuOnItemClick(new C31821());
        this.actionBar.createMenu().addItem(1, (int) R.drawable.ic_ab_other);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
        frameLayout.setBackgroundColor(-16777216);
        this.actionBar.setTitle(LocaleController.getString("Gallery", R.string.Gallery));
        this.listView = new RecyclerListView(context);
        this.listView.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), AndroidUtilities.dp(4.0f));
        this.listView.setClipToPadding(false);
        this.listView.setHorizontalScrollBarEnabled(false);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setDrawingCacheEnabled(false);
        frameLayout.addView(this.listView);
        LayoutParams layoutParams = (LayoutParams) this.listView.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.bottomMargin = AndroidUtilities.dp(48.0f);
        this.listView.setLayoutParams(layoutParams);
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setGlowColor(Theme.ACTION_BAR_MEDIA_PICKER_COLOR);
        this.emptyView = new TextView(context);
        this.emptyView.setTextColor(-8355712);
        this.emptyView.setTextSize(20.0f);
        this.emptyView.setGravity(17);
        this.emptyView.setVisibility(8);
        this.emptyView.setText(LocaleController.getString("NoPhotos", R.string.NoPhotos));
        frameLayout.addView(this.emptyView);
        layoutParams = (LayoutParams) this.emptyView.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.bottomMargin = AndroidUtilities.dp(48.0f);
        this.emptyView.setLayoutParams(layoutParams);
        this.emptyView.setOnTouchListener(new C31832());
        this.progressView = new FrameLayout(context);
        this.progressView.setVisibility(8);
        frameLayout.addView(this.progressView);
        layoutParams = (LayoutParams) this.progressView.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.bottomMargin = AndroidUtilities.dp(48.0f);
        this.progressView.setLayoutParams(layoutParams);
        this.progressView.addView(new RadialProgressView(context));
        layoutParams = (LayoutParams) this.progressView.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.gravity = 17;
        this.progressView.setLayoutParams(layoutParams);
        this.pickerBottomLayout = new PickerBottomLayout(context);
        frameLayout.addView(this.pickerBottomLayout);
        layoutParams = (LayoutParams) this.pickerBottomLayout.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = AndroidUtilities.dp(48.0f);
        layoutParams.gravity = 80;
        this.pickerBottomLayout.setLayoutParams(layoutParams);
        this.pickerBottomLayout.cancelButton.setOnClickListener(new C31843());
        this.pickerBottomLayout.doneButton.setOnClickListener(new C31854());
        if (!this.loading || (this.albumsSorted != null && (this.albumsSorted == null || !this.albumsSorted.isEmpty()))) {
            this.progressView.setVisibility(8);
            this.listView.setEmptyView(this.emptyView);
        } else {
            this.progressView.setVisibility(0);
            this.listView.setEmptyView(null);
        }
        this.pickerBottomLayout.updateSelectedCount(this.selectedPhotos.size(), true);
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        fixLayout();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        fixLayout();
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.albumsDidLoaded) {
            if (this.classGuid == ((Integer) args[0]).intValue()) {
                if (this.singlePhoto) {
                    this.albumsSorted = (ArrayList) args[2];
                } else {
                    this.albumsSorted = (ArrayList) args[1];
                }
                if (this.progressView != null) {
                    this.progressView.setVisibility(8);
                }
                if (this.listView != null && this.listView.getEmptyView() == null) {
                    this.listView.setEmptyView(this.emptyView);
                }
                if (this.listAdapter != null) {
                    this.listAdapter.notifyDataSetChanged();
                }
                this.loading = false;
            }
        } else if (id == NotificationCenter.closeChats) {
            removeSelfFromStack();
        } else if (id == NotificationCenter.recentImagesDidLoaded) {
            int type = ((Integer) args[0]).intValue();
            Iterator it;
            MediaController$SearchImage searchImage;
            if (type == 0) {
                this.recentWebImages = (ArrayList) args[1];
                this.recentImagesWebKeys.clear();
                it = this.recentWebImages.iterator();
                while (it.hasNext()) {
                    searchImage = (MediaController$SearchImage) it.next();
                    this.recentImagesWebKeys.put(searchImage.id, searchImage);
                }
            } else if (type == 1) {
                this.recentGifImages = (ArrayList) args[1];
                this.recentImagesGifKeys.clear();
                it = this.recentGifImages.iterator();
                while (it.hasNext()) {
                    searchImage = (MediaController$SearchImage) it.next();
                    this.recentImagesGifKeys.put(searchImage.id, searchImage);
                }
            }
        }
    }

    public void setDelegate(PhotoAlbumPickerActivityDelegate delegate) {
        this.delegate = delegate;
    }

    private void sendSelectedPhotos(HashMap<Object, Object> photos, ArrayList<Object> order) {
        if (!photos.isEmpty() && this.delegate != null && !this.sendPressed) {
            this.sendPressed = true;
            boolean gifChanged = false;
            boolean webChange = false;
            ArrayList<SendMessagesHelper$SendingMediaInfo> media = new ArrayList();
            for (int a = 0; a < order.size(); a++) {
                MediaController$PhotoEntry object = photos.get(order.get(a));
                SendMessagesHelper$SendingMediaInfo info = new SendMessagesHelper$SendingMediaInfo();
                media.add(info);
                if (object instanceof MediaController$PhotoEntry) {
                    MediaController$PhotoEntry photoEntry = object;
                    if (photoEntry.isVideo) {
                        info.path = photoEntry.path;
                        info.videoEditedInfo = photoEntry.editedInfo;
                    } else if (photoEntry.imagePath != null) {
                        info.path = photoEntry.imagePath;
                    } else if (photoEntry.path != null) {
                        info.path = photoEntry.path;
                    }
                    info.isVideo = photoEntry.isVideo;
                    info.caption = photoEntry.caption != null ? photoEntry.caption.toString() : null;
                    info.masks = !photoEntry.stickers.isEmpty() ? new ArrayList(photoEntry.stickers) : null;
                    info.ttl = photoEntry.ttl;
                } else if (object instanceof MediaController$SearchImage) {
                    MediaController$SearchImage searchImage = (MediaController$SearchImage) object;
                    if (searchImage.imagePath != null) {
                        info.path = searchImage.imagePath;
                    } else {
                        info.searchImage = searchImage;
                    }
                    info.caption = searchImage.caption != null ? searchImage.caption.toString() : null;
                    info.masks = !searchImage.stickers.isEmpty() ? new ArrayList(searchImage.stickers) : null;
                    info.ttl = searchImage.ttl;
                    searchImage.date = (int) (System.currentTimeMillis() / 1000);
                    MediaController$SearchImage recentImage;
                    if (searchImage.type == 0) {
                        webChange = true;
                        recentImage = (MediaController$SearchImage) this.recentImagesWebKeys.get(searchImage.id);
                        if (recentImage != null) {
                            this.recentWebImages.remove(recentImage);
                            this.recentWebImages.add(0, recentImage);
                        } else {
                            this.recentWebImages.add(0, searchImage);
                        }
                    } else if (searchImage.type == 1) {
                        gifChanged = true;
                        recentImage = (MediaController$SearchImage) this.recentImagesGifKeys.get(searchImage.id);
                        if (recentImage != null) {
                            this.recentGifImages.remove(recentImage);
                            this.recentGifImages.add(0, recentImage);
                        } else {
                            this.recentGifImages.add(0, searchImage);
                        }
                    }
                }
            }
            if (webChange) {
                MessagesStorage.getInstance().putWebRecent(this.recentWebImages);
            }
            if (gifChanged) {
                MessagesStorage.getInstance().putWebRecent(this.recentGifImages);
            }
            this.delegate.didSelectPhotos(media);
        }
    }

    private void fixLayout() {
        if (this.listView != null) {
            this.listView.getViewTreeObserver().addOnPreDrawListener(new C31865());
        }
    }

    private void fixLayoutInternal() {
        if (getParentActivity() != null) {
            int rotation = ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
            this.columnsCount = 2;
            if (!AndroidUtilities.isTablet() && (rotation == 3 || rotation == 1)) {
                this.columnsCount = 4;
            }
            this.listAdapter.notifyDataSetChanged();
        }
    }

    private void openPhotoPicker(MediaController$AlbumEntry albumEntry, int type) {
        PhotoPickerActivity fragment;
        ArrayList<MediaController$SearchImage> recentImages = null;
        if (albumEntry == null) {
            if (type == 0) {
                recentImages = this.recentWebImages;
            } else if (type == 1) {
                recentImages = this.recentGifImages;
            }
        }
        if (albumEntry != null) {
            fragment = new PhotoPickerActivity(type, albumEntry, this.selectedPhotos, this.selectedPhotosOrder, recentImages, this.singlePhoto, this.allowCaption, this.chatActivity);
            fragment.setDelegate(new C31876());
        } else {
            final HashMap<Object, Object> photos = new HashMap();
            final ArrayList<Object> order = new ArrayList();
            fragment = new PhotoPickerActivity(type, albumEntry, photos, order, recentImages, this.singlePhoto, this.allowCaption, this.chatActivity);
            fragment.setDelegate(new PhotoPickerActivityDelegate() {
                public void selectedPhotosChanged() {
                }

                public void actionButtonPressed(boolean canceled) {
                    PhotoAlbumPickerActivity.this.removeSelfFromStack();
                    if (!canceled) {
                        PhotoAlbumPickerActivity.this.sendSelectedPhotos(photos, order);
                    }
                }
            });
        }
        presentFragment(fragment);
    }
}
