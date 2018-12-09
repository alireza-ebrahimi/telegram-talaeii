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
import org.telegram.messenger.MediaController.AlbumEntry;
import org.telegram.messenger.MediaController.PhotoEntry;
import org.telegram.messenger.MediaController.SearchImage;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper.SendingMediaInfo;
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
    private ArrayList<AlbumEntry> albumsSorted = null;
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
    private ArrayList<SearchImage> recentGifImages = new ArrayList();
    private HashMap<String, SearchImage> recentImagesGifKeys = new HashMap();
    private HashMap<String, SearchImage> recentImagesWebKeys = new HashMap();
    private ArrayList<SearchImage> recentWebImages = new ArrayList();
    private HashMap<Object, Object> selectedPhotos = new HashMap();
    private ArrayList<Object> selectedPhotosOrder = new ArrayList();
    private boolean sendPressed;
    private boolean singlePhoto;

    public interface PhotoAlbumPickerActivityDelegate {
        void didSelectPhotos(ArrayList<SendingMediaInfo> arrayList);

        void startPhotoSelectActivity();
    }

    /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$1 */
    class C50211 extends ActionBarMenuOnItemClick {
        C50211() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                PhotoAlbumPickerActivity.this.finishFragment();
            } else if (i == 1 && PhotoAlbumPickerActivity.this.delegate != null) {
                PhotoAlbumPickerActivity.this.finishFragment(false);
                PhotoAlbumPickerActivity.this.delegate.startPhotoSelectActivity();
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$2 */
    class C50222 implements OnTouchListener {
        C50222() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$3 */
    class C50233 implements OnClickListener {
        C50233() {
        }

        public void onClick(View view) {
            PhotoAlbumPickerActivity.this.finishFragment();
        }
    }

    /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$4 */
    class C50244 implements OnClickListener {
        C50244() {
        }

        public void onClick(View view) {
            PhotoAlbumPickerActivity.this.sendSelectedPhotos(PhotoAlbumPickerActivity.this.selectedPhotos, PhotoAlbumPickerActivity.this.selectedPhotosOrder);
            PhotoAlbumPickerActivity.this.finishFragment();
        }
    }

    /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$5 */
    class C50255 implements OnPreDrawListener {
        C50255() {
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
    class C50266 implements PhotoPickerActivityDelegate {
        C50266() {
        }

        public void actionButtonPressed(boolean z) {
            PhotoAlbumPickerActivity.this.removeSelfFromStack();
            if (!z) {
                PhotoAlbumPickerActivity.this.sendSelectedPhotos(PhotoAlbumPickerActivity.this.selectedPhotos, PhotoAlbumPickerActivity.this.selectedPhotosOrder);
            }
        }

        public void selectedPhotosChanged() {
            if (PhotoAlbumPickerActivity.this.pickerBottomLayout != null) {
                PhotoAlbumPickerActivity.this.pickerBottomLayout.updateSelectedCount(PhotoAlbumPickerActivity.this.selectedPhotos.size(), true);
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$ListAdapter$1 */
        class C50281 implements PhotoPickerAlbumsCellDelegate {
            C50281() {
            }

            public void didSelectAlbum(AlbumEntry albumEntry) {
                PhotoAlbumPickerActivity.this.openPhotoPicker(albumEntry, 0);
            }
        }

        /* renamed from: org.telegram.ui.PhotoAlbumPickerActivity$ListAdapter$2 */
        class C50292 implements PhotoPickerSearchCellDelegate {
            C50292() {
            }

            public void didPressedSearchButton(int i) {
                PhotoAlbumPickerActivity.this.openPhotoPicker(null, i);
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            int i = 0;
            if (PhotoAlbumPickerActivity.this.singlePhoto) {
                return PhotoAlbumPickerActivity.this.albumsSorted != null ? (int) Math.ceil((double) (((float) PhotoAlbumPickerActivity.this.albumsSorted.size()) / ((float) PhotoAlbumPickerActivity.this.columnsCount))) : 0;
            } else {
                if (PhotoAlbumPickerActivity.this.albumsSorted != null) {
                    i = (int) Math.ceil((double) (((float) PhotoAlbumPickerActivity.this.albumsSorted.size()) / ((float) PhotoAlbumPickerActivity.this.columnsCount)));
                }
                return i + 1;
            }
        }

        public int getItemViewType(int i) {
            return (!PhotoAlbumPickerActivity.this.singlePhoto && i == 0) ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() == 0) {
                PhotoPickerAlbumsCell photoPickerAlbumsCell = (PhotoPickerAlbumsCell) viewHolder.itemView;
                photoPickerAlbumsCell.setAlbumsCount(PhotoAlbumPickerActivity.this.columnsCount);
                int i2 = 0;
                while (i2 < PhotoAlbumPickerActivity.this.columnsCount) {
                    int access$900 = PhotoAlbumPickerActivity.this.singlePhoto ? (PhotoAlbumPickerActivity.this.columnsCount * i) + i2 : ((i - 1) * PhotoAlbumPickerActivity.this.columnsCount) + i2;
                    if (access$900 < PhotoAlbumPickerActivity.this.albumsSorted.size()) {
                        photoPickerAlbumsCell.setAlbum(i2, (AlbumEntry) PhotoAlbumPickerActivity.this.albumsSorted.get(access$900));
                    } else {
                        photoPickerAlbumsCell.setAlbum(i2, null);
                    }
                    i2++;
                }
                photoPickerAlbumsCell.requestLayout();
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View photoPickerAlbumsCell;
            switch (i) {
                case 0:
                    photoPickerAlbumsCell = new PhotoPickerAlbumsCell(this.mContext);
                    photoPickerAlbumsCell.setDelegate(new C50281());
                    break;
                default:
                    photoPickerAlbumsCell = new PhotoPickerSearchCell(this.mContext, PhotoAlbumPickerActivity.this.allowGifs);
                    photoPickerAlbumsCell.setDelegate(new C50292());
                    break;
            }
            return new Holder(photoPickerAlbumsCell);
        }
    }

    public PhotoAlbumPickerActivity(boolean z, boolean z2, boolean z3, ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
        this.singlePhoto = z;
        this.allowGifs = z2;
        this.allowCaption = z3;
    }

    private void fixLayout() {
        if (this.listView != null) {
            this.listView.getViewTreeObserver().addOnPreDrawListener(new C50255());
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

    private void openPhotoPicker(AlbumEntry albumEntry, int i) {
        BaseFragment photoPickerActivity;
        ArrayList arrayList = null;
        if (albumEntry == null) {
            if (i == 0) {
                arrayList = this.recentWebImages;
            } else if (i == 1) {
                arrayList = this.recentGifImages;
            }
        }
        if (albumEntry != null) {
            photoPickerActivity = new PhotoPickerActivity(i, albumEntry, this.selectedPhotos, this.selectedPhotosOrder, arrayList, this.singlePhoto, this.allowCaption, this.chatActivity);
            photoPickerActivity.setDelegate(new C50266());
        } else {
            final HashMap hashMap = new HashMap();
            final ArrayList arrayList2 = new ArrayList();
            photoPickerActivity = new PhotoPickerActivity(i, albumEntry, hashMap, arrayList2, arrayList, this.singlePhoto, this.allowCaption, this.chatActivity);
            photoPickerActivity.setDelegate(new PhotoPickerActivityDelegate() {
                public void actionButtonPressed(boolean z) {
                    PhotoAlbumPickerActivity.this.removeSelfFromStack();
                    if (!z) {
                        PhotoAlbumPickerActivity.this.sendSelectedPhotos(hashMap, arrayList2);
                    }
                }

                public void selectedPhotosChanged() {
                }
            });
        }
        presentFragment(photoPickerActivity);
    }

    private void sendSelectedPhotos(HashMap<Object, Object> hashMap, ArrayList<Object> arrayList) {
        if (!hashMap.isEmpty() && this.delegate != null && !this.sendPressed) {
            this.sendPressed = true;
            ArrayList arrayList2 = new ArrayList();
            Object obj = null;
            Object obj2 = null;
            int i = 0;
            while (i < arrayList.size()) {
                Object obj3;
                Object obj4 = hashMap.get(arrayList.get(i));
                SendingMediaInfo sendingMediaInfo = new SendingMediaInfo();
                arrayList2.add(sendingMediaInfo);
                if (obj4 instanceof PhotoEntry) {
                    PhotoEntry photoEntry = (PhotoEntry) obj4;
                    if (photoEntry.isVideo) {
                        sendingMediaInfo.path = photoEntry.path;
                        sendingMediaInfo.videoEditedInfo = photoEntry.editedInfo;
                    } else if (photoEntry.imagePath != null) {
                        sendingMediaInfo.path = photoEntry.imagePath;
                    } else if (photoEntry.path != null) {
                        sendingMediaInfo.path = photoEntry.path;
                    }
                    sendingMediaInfo.isVideo = photoEntry.isVideo;
                    sendingMediaInfo.caption = photoEntry.caption != null ? photoEntry.caption.toString() : null;
                    sendingMediaInfo.masks = !photoEntry.stickers.isEmpty() ? new ArrayList(photoEntry.stickers) : null;
                    sendingMediaInfo.ttl = photoEntry.ttl;
                    obj3 = obj;
                    obj = obj2;
                } else {
                    if (obj4 instanceof SearchImage) {
                        SearchImage searchImage = (SearchImage) obj4;
                        if (searchImage.imagePath != null) {
                            sendingMediaInfo.path = searchImage.imagePath;
                        } else {
                            sendingMediaInfo.searchImage = searchImage;
                        }
                        sendingMediaInfo.caption = searchImage.caption != null ? searchImage.caption.toString() : null;
                        sendingMediaInfo.masks = !searchImage.stickers.isEmpty() ? new ArrayList(searchImage.stickers) : null;
                        sendingMediaInfo.ttl = searchImage.ttl;
                        searchImage.date = (int) (System.currentTimeMillis() / 1000);
                        SearchImage searchImage2;
                        if (searchImage.type == 0) {
                            searchImage2 = (SearchImage) this.recentImagesWebKeys.get(searchImage.id);
                            if (searchImage2 != null) {
                                this.recentWebImages.remove(searchImage2);
                                this.recentWebImages.add(0, searchImage2);
                            } else {
                                this.recentWebImages.add(0, searchImage);
                            }
                            int i2 = 1;
                            obj = obj2;
                        } else if (searchImage.type == 1) {
                            obj2 = 1;
                            searchImage2 = (SearchImage) this.recentImagesGifKeys.get(searchImage.id);
                            if (searchImage2 != null) {
                                this.recentGifImages.remove(searchImage2);
                                this.recentGifImages.add(0, searchImage2);
                                obj3 = obj;
                                int i3 = 1;
                            } else {
                                this.recentGifImages.add(0, searchImage);
                            }
                        }
                    }
                    obj3 = obj;
                    obj = obj2;
                }
                i++;
                obj2 = obj;
                obj = obj3;
            }
            if (obj != null) {
                MessagesStorage.getInstance().putWebRecent(this.recentWebImages);
            }
            if (obj2 != null) {
                MessagesStorage.getInstance().putWebRecent(this.recentGifImages);
            }
            this.delegate.didSelectPhotos(arrayList2);
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackgroundColor(Theme.ACTION_BAR_MEDIA_PICKER_COLOR);
        this.actionBar.setTitleColor(-1);
        this.actionBar.setItemsBackgroundColor(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, false);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setActionBarMenuOnItemClick(new C50211());
        this.actionBar.createMenu().addItem(1, (int) R.drawable.ic_ab_other);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
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
        this.emptyView.setOnTouchListener(new C50222());
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
        LayoutParams layoutParams2 = (LayoutParams) this.pickerBottomLayout.getLayoutParams();
        layoutParams2.width = -1;
        layoutParams2.height = AndroidUtilities.dp(48.0f);
        layoutParams2.gravity = 80;
        this.pickerBottomLayout.setLayoutParams(layoutParams2);
        this.pickerBottomLayout.cancelButton.setOnClickListener(new C50233());
        this.pickerBottomLayout.doneButton.setOnClickListener(new C50244());
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

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.albumsDidLoaded) {
            if (this.classGuid == ((Integer) objArr[0]).intValue()) {
                if (this.singlePhoto) {
                    this.albumsSorted = (ArrayList) objArr[2];
                } else {
                    this.albumsSorted = (ArrayList) objArr[1];
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
        } else if (i == NotificationCenter.closeChats) {
            removeSelfFromStack();
        } else if (i == NotificationCenter.recentImagesDidLoaded) {
            int intValue = ((Integer) objArr[0]).intValue();
            Iterator it;
            SearchImage searchImage;
            if (intValue == 0) {
                this.recentWebImages = (ArrayList) objArr[1];
                this.recentImagesWebKeys.clear();
                it = this.recentWebImages.iterator();
                while (it.hasNext()) {
                    searchImage = (SearchImage) it.next();
                    this.recentImagesWebKeys.put(searchImage.id, searchImage);
                }
            } else if (intValue == 1) {
                this.recentGifImages = (ArrayList) objArr[1];
                this.recentImagesGifKeys.clear();
                it = this.recentGifImages.iterator();
                while (it.hasNext()) {
                    searchImage = (SearchImage) it.next();
                    this.recentImagesGifKeys.put(searchImage.id, searchImage);
                }
            }
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        fixLayout();
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

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        fixLayout();
    }

    public void setDelegate(PhotoAlbumPickerActivityDelegate photoAlbumPickerActivityDelegate) {
        this.delegate = photoAlbumPickerActivityDelegate;
    }
}
