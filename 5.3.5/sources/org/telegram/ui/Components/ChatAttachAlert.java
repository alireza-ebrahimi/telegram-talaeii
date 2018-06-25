package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.text.TextUtils.TruncateAt;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewAnimationUtils;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.ir.talaeii.R;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController$PhotoEntry;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.camera.CameraController;
import org.telegram.messenger.camera.CameraController.VideoTakeCallback;
import org.telegram.messenger.camera.CameraView;
import org.telegram.messenger.camera.CameraView.CameraViewDelegate;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$TL_topPeer;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.BottomSheet.BottomSheetDelegateInterface;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.PhotoAttachCameraCell;
import org.telegram.ui.Cells.PhotoAttachPhotoCell;
import org.telegram.ui.Cells.PhotoAttachPhotoCell.PhotoAttachPhotoCellDelegate;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.ShutterButton.ShutterButtonDelegate;
import org.telegram.ui.PhotoViewer;
import org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PlaceProviderObject;

public class ChatAttachAlert extends BottomSheet implements NotificationCenterDelegate, BottomSheetDelegateInterface {
    private ListAdapter adapter;
    private int[] animateCameraValues = new int[5];
    private ArrayList<AttachButton> attachButtons = new ArrayList();
    private LinearLayoutManager attachPhotoLayoutManager;
    private RecyclerListView attachPhotoRecyclerView;
    private ViewGroup attachView;
    private ChatActivity baseFragment;
    private boolean cameraAnimationInProgress;
    private File cameraFile;
    private FrameLayout cameraIcon;
    private boolean cameraInitied;
    private float cameraOpenProgress;
    private boolean cameraOpened;
    private FrameLayout cameraPanel;
    private ArrayList<Object> cameraPhoto;
    private CameraView cameraView;
    private int[] cameraViewLocation = new int[2];
    private int cameraViewOffsetX;
    private int cameraViewOffsetY;
    private Paint ciclePaint = new Paint(1);
    private AnimatorSet currentHintAnimation;
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private ChatAttachViewDelegate delegate;
    private boolean deviceHasGoodCamera;
    private boolean dragging;
    private boolean flashAnimationInProgress;
    private ImageView[] flashModeButton = new ImageView[2];
    private Runnable hideHintRunnable;
    private boolean hintShowed;
    private TextView hintTextView;
    private boolean ignoreLayout;
    private ArrayList<InnerAnimator> innerAnimators = new ArrayList();
    private DecelerateInterpolator interpolator = new DecelerateInterpolator(1.5f);
    private float lastY;
    private LinearLayoutManager layoutManager;
    private View lineView;
    private RecyclerListView listView;
    private boolean loading = true;
    private boolean maybeStartDraging;
    private CorrectlyMeasuringTextView mediaBanTooltip;
    private boolean mediaCaptured;
    private boolean mediaEnabled = true;
    private boolean paused;
    private PhotoAttachAdapter photoAttachAdapter;
    private PhotoViewerProvider photoViewerProvider = new C25251();
    private boolean pressed;
    private EmptyTextProgressView progressView;
    private TextView recordTime;
    private boolean requestingPermissions;
    private boolean revealAnimationInProgress;
    private float revealRadius;
    private int revealX;
    private int revealY;
    private int scrollOffsetY;
    private AttachButton sendDocumentsButton;
    private AttachButton sendPhotosButton;
    private Drawable shadowDrawable;
    private ShutterButton shutterButton;
    private ImageView switchCameraButton;
    private boolean takingPhoto;
    private boolean useRevealAnimation;
    private Runnable videoRecordRunnable;
    private int videoRecordTime;
    private int[] viewPosition = new int[2];
    private View[] views = new View[20];
    private ArrayList<Holder> viewsCache = new ArrayList(8);

    public interface ChatAttachViewDelegate {
        void didPressedButton(int i);

        void didSelectBot(User user);

        View getRevealView();

        void onCameraOpened();
    }

    /* renamed from: org.telegram.ui.Components.ChatAttachAlert$1 */
    class C25251 extends EmptyPhotoViewerProvider {
        C25251() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
            PhotoAttachPhotoCell cell = ChatAttachAlert.this.getCellForIndex(index);
            if (cell == null) {
                return null;
            }
            coords = new int[2];
            cell.getImageView().getLocationInWindow(coords);
            coords[0] = coords[0] - ChatAttachAlert.this.getLeftInset();
            PlaceProviderObject object = new PlaceProviderObject();
            object.viewX = coords[0];
            object.viewY = coords[1];
            object.parentView = ChatAttachAlert.this.attachPhotoRecyclerView;
            object.imageReceiver = cell.getImageView().getImageReceiver();
            object.thumb = object.imageReceiver.getBitmap();
            object.scale = cell.getImageView().getScaleX();
            cell.showCheck(false);
            return object;
        }

        public void updatePhotoAtIndex(int index) {
            PhotoAttachPhotoCell cell = ChatAttachAlert.this.getCellForIndex(index);
            if (cell != null) {
                cell.getImageView().setOrientation(0, true);
                MediaController$PhotoEntry photoEntry = (MediaController$PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(index);
                if (photoEntry.thumbPath != null) {
                    cell.getImageView().setImage(photoEntry.thumbPath, null, cell.getContext().getResources().getDrawable(R.drawable.nophotos));
                } else if (photoEntry.path != null) {
                    cell.getImageView().setOrientation(photoEntry.orientation, true);
                    if (photoEntry.isVideo) {
                        cell.getImageView().setImage("vthumb://" + photoEntry.imageId + ":" + photoEntry.path, null, cell.getContext().getResources().getDrawable(R.drawable.nophotos));
                    } else {
                        cell.getImageView().setImage("thumb://" + photoEntry.imageId + ":" + photoEntry.path, null, cell.getContext().getResources().getDrawable(R.drawable.nophotos));
                    }
                } else {
                    cell.getImageView().setImageResource(R.drawable.nophotos);
                }
            }
        }

        public Bitmap getThumbForPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
            PhotoAttachPhotoCell cell = ChatAttachAlert.this.getCellForIndex(index);
            if (cell != null) {
                return cell.getImageView().getImageReceiver().getBitmap();
            }
            return null;
        }

        public void willSwitchFromPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
            PhotoAttachPhotoCell cell = ChatAttachAlert.this.getCellForIndex(index);
            if (cell != null) {
                cell.showCheck(true);
            }
        }

        public void willHidePhotoViewer() {
            int count = ChatAttachAlert.this.attachPhotoRecyclerView.getChildCount();
            for (int a = 0; a < count; a++) {
                View view = ChatAttachAlert.this.attachPhotoRecyclerView.getChildAt(a);
                if (view instanceof PhotoAttachPhotoCell) {
                    ((PhotoAttachPhotoCell) view).showCheck(true);
                }
            }
        }

        public boolean isPhotoChecked(int index) {
            return index >= 0 && index < MediaController.allMediaAlbumEntry.photos.size() && ChatAttachAlert.this.photoAttachAdapter.selectedPhotos.containsKey(Integer.valueOf(((MediaController$PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(index)).imageId));
        }

        public int setPhotoChecked(int index, VideoEditedInfo videoEditedInfo) {
            boolean add = true;
            if (index < 0 || index >= MediaController.allMediaAlbumEntry.photos.size()) {
                return -1;
            }
            MediaController$PhotoEntry photoEntry = (MediaController$PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(index);
            int num = ChatAttachAlert.this.photoAttachAdapter.addToSelectedPhotos(photoEntry, -1);
            if (num == -1) {
                num = ChatAttachAlert.this.photoAttachAdapter.selectedPhotosOrder.indexOf(Integer.valueOf(photoEntry.imageId));
            } else {
                add = false;
                photoEntry.editedInfo = null;
            }
            photoEntry.editedInfo = videoEditedInfo;
            int count = ChatAttachAlert.this.attachPhotoRecyclerView.getChildCount();
            for (int a = 0; a < count; a++) {
                View view = ChatAttachAlert.this.attachPhotoRecyclerView.getChildAt(a);
                if ((view instanceof PhotoAttachPhotoCell) && ((Integer) view.getTag()).intValue() == index) {
                    ((PhotoAttachPhotoCell) view).setChecked(num, add, false);
                    break;
                }
            }
            ChatAttachAlert.this.updatePhotosButton();
            return num;
        }

        public boolean cancelButtonPressed() {
            return false;
        }

        public void sendButtonPressed(int index, VideoEditedInfo videoEditedInfo) {
            if (ChatAttachAlert.this.photoAttachAdapter.selectedPhotos.isEmpty()) {
                if (index >= 0 && index < MediaController.allMediaAlbumEntry.photos.size()) {
                    MediaController$PhotoEntry photoEntry = (MediaController$PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(index);
                    photoEntry.editedInfo = videoEditedInfo;
                    ChatAttachAlert.this.photoAttachAdapter.addToSelectedPhotos(photoEntry, -1);
                } else {
                    return;
                }
            }
            ChatAttachAlert.this.delegate.didPressedButton(7);
        }

        public int getSelectedCount() {
            return ChatAttachAlert.this.photoAttachAdapter.selectedPhotos.size();
        }

        public ArrayList<Object> getSelectedPhotosOrder() {
            return ChatAttachAlert.this.photoAttachAdapter.selectedPhotosOrder;
        }

        public HashMap<Object, Object> getSelectedPhotos() {
            return ChatAttachAlert.this.photoAttachAdapter.selectedPhotos;
        }

        public boolean allowGroupPhotos() {
            return ChatAttachAlert.this.baseFragment != null && ChatAttachAlert.this.baseFragment.allowGroupPhotos();
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatAttachAlert$3 */
    class C25283 extends ItemDecoration {
        C25283() {
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = 0;
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatAttachAlert$4 */
    class C25294 extends OnScrollListener {
        C25294() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (ChatAttachAlert.this.listView.getChildCount() > 0) {
                if (ChatAttachAlert.this.hintShowed && ChatAttachAlert.this.layoutManager.findLastVisibleItemPosition() > 1) {
                    ChatAttachAlert.this.hideHint();
                    ChatAttachAlert.this.hintShowed = false;
                    ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putBoolean("bothint", true).commit();
                }
                ChatAttachAlert.this.updateLayout();
                ChatAttachAlert.this.checkCameraViewPosition();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatAttachAlert$7 */
    class C25327 implements OnItemClickListener {
        C25327() {
        }

        public void onItemClick(View view, int position) {
            if (ChatAttachAlert.this.baseFragment != null && ChatAttachAlert.this.baseFragment.getParentActivity() != null) {
                if (ChatAttachAlert.this.deviceHasGoodCamera && position == 0) {
                    ChatAttachAlert.this.openCamera();
                    return;
                }
                if (ChatAttachAlert.this.deviceHasGoodCamera) {
                    position--;
                }
                if (MediaController.allMediaAlbumEntry != null) {
                    ArrayList<Object> arrayList = MediaController.allMediaAlbumEntry.photos;
                    if (position >= 0 && position < arrayList.size()) {
                        PhotoViewer.getInstance().setParentActivity(ChatAttachAlert.this.baseFragment.getParentActivity());
                        PhotoViewer.getInstance().setParentAlert(ChatAttachAlert.this);
                        PhotoViewer.getInstance().openPhotoForSelect(arrayList, position, 0, ChatAttachAlert.this.photoViewerProvider, ChatAttachAlert.this.baseFragment);
                        AndroidUtilities.hideKeyboard(ChatAttachAlert.this.baseFragment.getFragmentView().findFocus());
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatAttachAlert$8 */
    class C25338 extends OnScrollListener {
        C25338() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            ChatAttachAlert.this.checkCameraViewPosition();
        }
    }

    private class AttachBotButton extends FrameLayout {
        private AvatarDrawable avatarDrawable = new AvatarDrawable();
        private boolean checkingForLongPress = false;
        private User currentUser;
        private BackupImageView imageView;
        private TextView nameTextView;
        private CheckForLongPress pendingCheckForLongPress = null;
        private CheckForTap pendingCheckForTap = null;
        private int pressCount = 0;
        private boolean pressed;

        /* renamed from: org.telegram.ui.Components.ChatAttachAlert$AttachBotButton$1 */
        class C25351 implements OnClickListener {
            C25351() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                SearchQuery.removeInline(AttachBotButton.this.currentUser.id);
            }
        }

        class CheckForLongPress implements Runnable {
            public int currentPressCount;

            CheckForLongPress() {
            }

            public void run() {
                if (AttachBotButton.this.checkingForLongPress && AttachBotButton.this.getParent() != null && this.currentPressCount == AttachBotButton.this.pressCount) {
                    AttachBotButton.this.checkingForLongPress = false;
                    AttachBotButton.this.performHapticFeedback(0);
                    AttachBotButton.this.onLongPress();
                    MotionEvent event = MotionEvent.obtain(0, 0, 3, 0.0f, 0.0f, 0);
                    AttachBotButton.this.onTouchEvent(event);
                    event.recycle();
                }
            }
        }

        private final class CheckForTap implements Runnable {
            private CheckForTap() {
            }

            public void run() {
                if (AttachBotButton.this.pendingCheckForLongPress == null) {
                    AttachBotButton.this.pendingCheckForLongPress = new CheckForLongPress();
                }
                AttachBotButton.this.pendingCheckForLongPress.currentPressCount = AttachBotButton.access$1004(AttachBotButton.this);
                AttachBotButton.this.postDelayed(AttachBotButton.this.pendingCheckForLongPress, (long) (ViewConfiguration.getLongPressTimeout() - ViewConfiguration.getTapTimeout()));
            }
        }

        static /* synthetic */ int access$1004(AttachBotButton x0) {
            int i = x0.pressCount + 1;
            x0.pressCount = i;
            return i;
        }

        public AttachBotButton(Context context) {
            super(context);
            this.imageView = new BackupImageView(context);
            this.imageView.setRoundRadius(AndroidUtilities.dp(27.0f));
            addView(this.imageView, LayoutHelper.createFrame(54, 54.0f, 49, 0.0f, 7.0f, 0.0f, 0.0f));
            this.nameTextView = new TextView(context);
            this.nameTextView.setTextColor(Theme.getColor(Theme.key_dialogTextGray2));
            this.nameTextView.setTextSize(1, 12.0f);
            this.nameTextView.setMaxLines(2);
            this.nameTextView.setGravity(49);
            this.nameTextView.setLines(2);
            this.nameTextView.setEllipsize(TruncateAt.END);
            addView(this.nameTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 6.0f, 65.0f, 6.0f, 0.0f));
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(85.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(100.0f), 1073741824));
        }

        private void onLongPress() {
            if (ChatAttachAlert.this.baseFragment != null && this.currentUser != null) {
                Builder builder = new Builder(getContext());
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setMessage(LocaleController.formatString("ChatHintsDelete", R.string.ChatHintsDelete, new Object[]{ContactsController.formatName(this.currentUser.first_name, this.currentUser.last_name)}));
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C25351());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                builder.show();
            }
        }

        public void setUser(User user) {
            if (user != null) {
                this.currentUser = user;
                TLObject photo = null;
                this.nameTextView.setText(ContactsController.formatName(user.first_name, user.last_name));
                this.avatarDrawable.setInfo(user);
                if (!(user == null || user.photo == null)) {
                    photo = user.photo.photo_small;
                }
                this.imageView.setImage(photo, "50_50", this.avatarDrawable);
                requestLayout();
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            boolean result = false;
            if (event.getAction() == 0) {
                this.pressed = true;
                invalidate();
                result = true;
            } else if (this.pressed) {
                if (event.getAction() == 1) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    this.pressed = false;
                    playSoundEffect(0);
                    ChatAttachAlert.this.delegate.didSelectBot(MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_topPeer) SearchQuery.inlineBots.get(((Integer) getTag()).intValue())).peer.user_id)));
                    ChatAttachAlert.this.setUseRevealAnimation(false);
                    ChatAttachAlert.this.dismiss();
                    ChatAttachAlert.this.setUseRevealAnimation(true);
                    invalidate();
                } else if (event.getAction() == 3) {
                    this.pressed = false;
                    invalidate();
                }
            }
            if (!result) {
                result = super.onTouchEvent(event);
            } else if (event.getAction() == 0) {
                startCheckLongPress();
            }
            if (!(event.getAction() == 0 || event.getAction() == 2)) {
                cancelCheckLongPress();
            }
            return result;
        }

        protected void startCheckLongPress() {
            if (!this.checkingForLongPress) {
                this.checkingForLongPress = true;
                if (this.pendingCheckForTap == null) {
                    this.pendingCheckForTap = new CheckForTap();
                }
                postDelayed(this.pendingCheckForTap, (long) ViewConfiguration.getTapTimeout());
            }
        }

        protected void cancelCheckLongPress() {
            this.checkingForLongPress = false;
            if (this.pendingCheckForLongPress != null) {
                removeCallbacks(this.pendingCheckForLongPress);
            }
            if (this.pendingCheckForTap != null) {
                removeCallbacks(this.pendingCheckForTap);
            }
        }
    }

    private class AttachButton extends FrameLayout {
        private ImageView imageView;
        private TextView textView;

        public AttachButton(Context context) {
            super(context);
            this.imageView = new ImageView(context);
            this.imageView.setScaleType(ScaleType.CENTER);
            addView(this.imageView, LayoutHelper.createFrame(64, 64, 49));
            this.textView = new TextView(context);
            this.textView.setLines(1);
            this.textView.setSingleLine(true);
            this.textView.setGravity(1);
            this.textView.setEllipsize(TruncateAt.END);
            this.textView.setTextColor(Theme.getColor(Theme.key_dialogTextGray2));
            this.textView.setTextSize(1, 12.0f);
            addView(this.textView, LayoutHelper.createFrame(-1, -2.0f, 51, 0.0f, 64.0f, 0.0f, 0.0f));
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(85.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(90.0f), 1073741824));
        }

        public void setTextAndIcon(CharSequence text, Drawable drawable) {
            this.textView.setText(text);
            this.imageView.setBackgroundDrawable(drawable);
        }

        public boolean hasOverlappingRendering() {
            return false;
        }
    }

    private class InnerAnimator {
        private AnimatorSet animatorSet;
        private float startRadius;

        private InnerAnimator() {
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            View frameLayout;
            switch (viewType) {
                case 0:
                    view = ChatAttachAlert.this.attachView;
                    break;
                case 1:
                    frameLayout = new FrameLayout(this.mContext);
                    frameLayout.setBackgroundColor(-986896);
                    frameLayout.addView(new ShadowSectionCell(this.mContext), LayoutHelper.createFrame(-1, -1.0f));
                    view = frameLayout;
                    break;
                default:
                    frameLayout = new FrameLayout(this.mContext) {
                        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                            int diff = ((right - left) - AndroidUtilities.dp(360.0f)) / 3;
                            for (int a = 0; a < 4; a++) {
                                int x = AndroidUtilities.dp(10.0f) + ((a % 4) * (AndroidUtilities.dp(85.0f) + diff));
                                View child = getChildAt(a);
                                child.layout(x, 0, child.getMeasuredWidth() + x, child.getMeasuredHeight());
                            }
                        }
                    };
                    for (int a = 0; a < 4; a++) {
                        frameLayout.addView(new AttachBotButton(this.mContext));
                    }
                    view = frameLayout;
                    frameLayout.setLayoutParams(new LayoutParams(-1, AndroidUtilities.dp(100.0f)));
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position > 1) {
                position = (position - 2) * 4;
                FrameLayout frameLayout = holder.itemView;
                for (int a = 0; a < 4; a++) {
                    AttachBotButton child = (AttachBotButton) frameLayout.getChildAt(a);
                    if (position + a >= SearchQuery.inlineBots.size()) {
                        child.setVisibility(4);
                    } else {
                        child.setVisibility(0);
                        child.setTag(Integer.valueOf(position + a));
                        child.setUser(MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_topPeer) SearchQuery.inlineBots.get(position + a)).peer.user_id)));
                    }
                }
            }
        }

        public boolean isEnabled(ViewHolder holder) {
            return false;
        }

        public int getItemCount() {
            return (!SearchQuery.inlineBots.isEmpty() ? ((int) Math.ceil((double) (((float) SearchQuery.inlineBots.size()) / 4.0f))) + 1 : 0) + 1;
        }

        public int getItemViewType(int position) {
            switch (position) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                default:
                    return 2;
            }
        }
    }

    private class PhotoAttachAdapter extends SelectionAdapter {
        private Context mContext;
        private HashMap<Object, Object> selectedPhotos = new HashMap();
        private ArrayList<Object> selectedPhotosOrder = new ArrayList();

        /* renamed from: org.telegram.ui.Components.ChatAttachAlert$PhotoAttachAdapter$1 */
        class C25371 implements PhotoAttachPhotoCellDelegate {
            C25371() {
            }

            public void onCheckClick(PhotoAttachPhotoCell v) {
                if (ChatAttachAlert.this.mediaEnabled) {
                    int index = ((Integer) v.getTag()).intValue();
                    MediaController$PhotoEntry photoEntry = v.getPhotoEntry();
                    boolean added = !PhotoAttachAdapter.this.selectedPhotos.containsKey(Integer.valueOf(photoEntry.imageId));
                    v.setChecked(added ? PhotoAttachAdapter.this.selectedPhotosOrder.size() : -1, added, true);
                    PhotoAttachAdapter.this.addToSelectedPhotos(photoEntry, index);
                    ChatAttachAlert.this.updatePhotosButton();
                }
            }
        }

        public PhotoAttachAdapter(Context context) {
            this.mContext = context;
        }

        public void clearSelectedPhotos() {
            if (!this.selectedPhotos.isEmpty()) {
                for (Entry<Object, Object> entry : this.selectedPhotos.entrySet()) {
                    ((MediaController$PhotoEntry) entry.getValue()).reset();
                }
                this.selectedPhotos.clear();
                this.selectedPhotosOrder.clear();
                ChatAttachAlert.this.updatePhotosButton();
                notifyDataSetChanged();
            }
        }

        public Holder createHolder() {
            PhotoAttachPhotoCell cell = new PhotoAttachPhotoCell(this.mContext);
            cell.setDelegate(new C25371());
            return new Holder(cell);
        }

        private int addToSelectedPhotos(MediaController$PhotoEntry photoEntry, int index) {
            if (this.selectedPhotos.containsKey(Integer.valueOf(photoEntry.imageId))) {
                this.selectedPhotos.remove(Integer.valueOf(photoEntry.imageId));
                int position = 0;
                for (int a = 0; a < this.selectedPhotosOrder.size(); a++) {
                    if (this.selectedPhotosOrder.get(a).equals(Integer.valueOf(photoEntry.imageId))) {
                        position = a;
                        this.selectedPhotosOrder.remove(a);
                        break;
                    }
                }
                ChatAttachAlert.this.updateCheckedPhotoIndices();
                if (index < 0) {
                    return position;
                }
                photoEntry.reset();
                ChatAttachAlert.this.photoViewerProvider.updatePhotoAtIndex(index);
                return position;
            }
            this.selectedPhotos.put(Integer.valueOf(photoEntry.imageId), photoEntry);
            this.selectedPhotosOrder.add(Integer.valueOf(photoEntry.imageId));
            return -1;
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!ChatAttachAlert.this.deviceHasGoodCamera || position != 0) {
                boolean z;
                if (ChatAttachAlert.this.deviceHasGoodCamera) {
                    position--;
                }
                PhotoAttachPhotoCell cell = holder.itemView;
                MediaController$PhotoEntry photoEntry = (MediaController$PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(position);
                if (position == MediaController.allMediaAlbumEntry.photos.size() - 1) {
                    z = true;
                } else {
                    z = false;
                }
                cell.setPhotoEntry(photoEntry, z);
                cell.setChecked(this.selectedPhotosOrder.indexOf(Integer.valueOf(photoEntry.imageId)), this.selectedPhotos.containsKey(Integer.valueOf(photoEntry.imageId)), false);
                cell.getImageView().setTag(Integer.valueOf(position));
                cell.setTag(Integer.valueOf(position));
            } else if (!ChatAttachAlert.this.deviceHasGoodCamera || position != 0) {
            } else {
                if (ChatAttachAlert.this.cameraView == null || !ChatAttachAlert.this.cameraView.isInitied()) {
                    holder.itemView.setVisibility(0);
                } else {
                    holder.itemView.setVisibility(4);
                }
            }
        }

        public boolean isEnabled(ViewHolder holder) {
            return false;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case 1:
                    return new Holder(new PhotoAttachCameraCell(this.mContext));
                default:
                    if (ChatAttachAlert.this.viewsCache.isEmpty()) {
                        return createHolder();
                    }
                    Holder holder = (Holder) ChatAttachAlert.this.viewsCache.get(0);
                    ChatAttachAlert.this.viewsCache.remove(0);
                    return holder;
            }
        }

        public int getItemCount() {
            int count = 0;
            if (ChatAttachAlert.this.deviceHasGoodCamera) {
                count = 0 + 1;
            }
            if (MediaController.allMediaAlbumEntry != null) {
                return count + MediaController.allMediaAlbumEntry.photos.size();
            }
            return count;
        }

        public int getItemViewType(int position) {
            if (ChatAttachAlert.this.deviceHasGoodCamera && position == 0) {
                return 1;
            }
            return 0;
        }
    }

    private void updateCheckedPhotoIndices() {
        int count = this.attachPhotoRecyclerView.getChildCount();
        for (int a = 0; a < count; a++) {
            View view = this.attachPhotoRecyclerView.getChildAt(a);
            if (view instanceof PhotoAttachPhotoCell) {
                PhotoAttachPhotoCell cell = (PhotoAttachPhotoCell) view;
                cell.setNum(this.photoAttachAdapter.selectedPhotosOrder.indexOf(Integer.valueOf(((MediaController$PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(((Integer) cell.getTag()).intValue())).imageId)));
            }
        }
    }

    public ChatAttachAlert(Context context, final ChatActivity parentFragment) {
        int a;
        super(context, false);
        this.baseFragment = parentFragment;
        this.ciclePaint.setColor(Theme.getColor(Theme.key_dialogBackground));
        setDelegate(this);
        setUseRevealAnimation(true);
        checkCamera(false);
        if (this.deviceHasGoodCamera) {
            CameraController.getInstance().initCamera();
        }
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.albumsDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.reloadInlineHints);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.cameraInitied);
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        ViewGroup c25272 = new RecyclerListView(context) {
            private int lastHeight;
            private int lastWidth;

            public void requestLayout() {
                if (!ChatAttachAlert.this.ignoreLayout) {
                    super.requestLayout();
                }
            }

            public boolean onInterceptTouchEvent(MotionEvent ev) {
                if (ChatAttachAlert.this.cameraAnimationInProgress) {
                    return true;
                }
                if (ChatAttachAlert.this.cameraOpened) {
                    return ChatAttachAlert.this.processTouchEvent(ev);
                }
                if (ev.getAction() != 0 || ChatAttachAlert.this.scrollOffsetY == 0 || ev.getY() >= ((float) ChatAttachAlert.this.scrollOffsetY)) {
                    return super.onInterceptTouchEvent(ev);
                }
                ChatAttachAlert.this.dismiss();
                return true;
            }

            public boolean onTouchEvent(MotionEvent event) {
                if (ChatAttachAlert.this.cameraAnimationInProgress) {
                    return true;
                }
                if (ChatAttachAlert.this.cameraOpened) {
                    return ChatAttachAlert.this.processTouchEvent(event);
                }
                if (ChatAttachAlert.this.isDismissed() || !super.onTouchEvent(event)) {
                    return false;
                }
                return true;
            }

            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int height = MeasureSpec.getSize(heightMeasureSpec);
                if (VERSION.SDK_INT >= 21) {
                    height -= AndroidUtilities.statusBarHeight;
                }
                int contentSize = (AndroidUtilities.dp(294.0f) + ChatAttachAlert.backgroundPaddingTop) + (SearchQuery.inlineBots.isEmpty() ? 0 : (((int) Math.ceil((double) (((float) SearchQuery.inlineBots.size()) / 4.0f))) * AndroidUtilities.dp(100.0f)) + AndroidUtilities.dp(12.0f));
                int padding = contentSize == AndroidUtilities.dp(294.0f) ? 0 : Math.max(0, height - AndroidUtilities.dp(294.0f));
                if (padding != 0 && contentSize < height) {
                    padding -= height - contentSize;
                }
                if (padding == 0) {
                    padding = ChatAttachAlert.backgroundPaddingTop;
                }
                if (getPaddingTop() != padding) {
                    ChatAttachAlert.this.ignoreLayout = true;
                    setPadding(ChatAttachAlert.backgroundPaddingLeft, padding, ChatAttachAlert.backgroundPaddingLeft, 0);
                    ChatAttachAlert.this.ignoreLayout = false;
                }
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Math.min(contentSize, height), 1073741824));
            }

            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                int width = right - left;
                int height = bottom - top;
                int newPosition = -1;
                int newTop = 0;
                int lastVisibleItemPosition = -1;
                int lastVisibleItemPositionTop = 0;
                if (ChatAttachAlert.this.listView.getChildCount() > 0) {
                    View child = ChatAttachAlert.this.listView.getChildAt(ChatAttachAlert.this.listView.getChildCount() - 1);
                    Holder holder = (Holder) ChatAttachAlert.this.listView.findContainingViewHolder(child);
                    if (holder != null) {
                        lastVisibleItemPosition = holder.getAdapterPosition();
                        lastVisibleItemPositionTop = child.getTop();
                    }
                }
                if (lastVisibleItemPosition >= 0 && height - this.lastHeight != 0) {
                    newPosition = lastVisibleItemPosition;
                    newTop = ((lastVisibleItemPositionTop + height) - this.lastHeight) - getPaddingTop();
                }
                super.onLayout(changed, left, top, right, bottom);
                if (newPosition != -1) {
                    ChatAttachAlert.this.ignoreLayout = true;
                    ChatAttachAlert.this.layoutManager.scrollToPositionWithOffset(newPosition, newTop);
                    super.onLayout(false, left, top, right, bottom);
                    ChatAttachAlert.this.ignoreLayout = false;
                }
                this.lastHeight = height;
                this.lastWidth = width;
                ChatAttachAlert.this.updateLayout();
                ChatAttachAlert.this.checkCameraViewPosition();
            }

            public void onDraw(Canvas canvas) {
                if (!ChatAttachAlert.this.useRevealAnimation || VERSION.SDK_INT > 19) {
                    ChatAttachAlert.this.shadowDrawable.setBounds(0, ChatAttachAlert.this.scrollOffsetY - ChatAttachAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                    ChatAttachAlert.this.shadowDrawable.draw(canvas);
                    return;
                }
                canvas.save();
                canvas.clipRect(ChatAttachAlert.backgroundPaddingLeft, ChatAttachAlert.this.scrollOffsetY, getMeasuredWidth() - ChatAttachAlert.backgroundPaddingLeft, getMeasuredHeight());
                if (ChatAttachAlert.this.revealAnimationInProgress) {
                    canvas.drawCircle((float) ChatAttachAlert.this.revealX, (float) ChatAttachAlert.this.revealY, ChatAttachAlert.this.revealRadius, ChatAttachAlert.this.ciclePaint);
                } else {
                    canvas.drawRect((float) ChatAttachAlert.backgroundPaddingLeft, (float) ChatAttachAlert.this.scrollOffsetY, (float) (getMeasuredWidth() - ChatAttachAlert.backgroundPaddingLeft), (float) getMeasuredHeight(), ChatAttachAlert.this.ciclePaint);
                }
                canvas.restore();
            }

            public void setTranslationY(float translationY) {
                super.setTranslationY(translationY);
                ChatAttachAlert.this.checkCameraViewPosition();
            }
        };
        this.listView = c25272;
        this.containerView = c25272;
        this.listView.setWillNotDraw(false);
        this.listView.setClipToPadding(false);
        RecyclerListView recyclerListView = this.listView;
        LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.layoutManager = linearLayoutManager;
        recyclerListView.setLayoutManager(linearLayoutManager);
        this.layoutManager.setOrientation(1);
        recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.adapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setEnabled(true);
        this.listView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
        this.listView.addItemDecoration(new C25283());
        this.listView.setOnScrollListener(new C25294());
        this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        this.attachView = new FrameLayout(context) {
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(294.0f), 1073741824));
            }

            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                int width = right - left;
                int height = bottom - top;
                int t = AndroidUtilities.dp(8.0f);
                ChatAttachAlert.this.attachPhotoRecyclerView.layout(0, t, width, ChatAttachAlert.this.attachPhotoRecyclerView.getMeasuredHeight() + t);
                ChatAttachAlert.this.progressView.layout(0, t, width, ChatAttachAlert.this.progressView.getMeasuredHeight() + t);
                ChatAttachAlert.this.lineView.layout(0, AndroidUtilities.dp(96.0f), width, AndroidUtilities.dp(96.0f) + ChatAttachAlert.this.lineView.getMeasuredHeight());
                ChatAttachAlert.this.hintTextView.layout((width - ChatAttachAlert.this.hintTextView.getMeasuredWidth()) - AndroidUtilities.dp(5.0f), (height - ChatAttachAlert.this.hintTextView.getMeasuredHeight()) - AndroidUtilities.dp(5.0f), width - AndroidUtilities.dp(5.0f), height - AndroidUtilities.dp(5.0f));
                int x = (width - ChatAttachAlert.this.mediaBanTooltip.getMeasuredWidth()) / 2;
                int y = t + ((ChatAttachAlert.this.attachPhotoRecyclerView.getMeasuredHeight() - ChatAttachAlert.this.mediaBanTooltip.getMeasuredHeight()) / 2);
                ChatAttachAlert.this.mediaBanTooltip.layout(x, y, ChatAttachAlert.this.mediaBanTooltip.getMeasuredWidth() + x, ChatAttachAlert.this.mediaBanTooltip.getMeasuredHeight() + y);
                int diff = (width - AndroidUtilities.dp(360.0f)) / 3;
                for (int a = 0; a < 8; a++) {
                    y = AndroidUtilities.dp((float) (((a / 4) * 95) + 105));
                    x = AndroidUtilities.dp(10.0f) + ((a % 4) * (AndroidUtilities.dp(85.0f) + diff));
                    ChatAttachAlert.this.views[a].layout(x, y, ChatAttachAlert.this.views[a].getMeasuredWidth() + x, ChatAttachAlert.this.views[a].getMeasuredHeight() + y);
                }
            }
        };
        View[] viewArr = this.views;
        RecyclerListView recyclerListView2 = new RecyclerListView(context);
        this.attachPhotoRecyclerView = recyclerListView2;
        viewArr[8] = recyclerListView2;
        this.attachPhotoRecyclerView.setVerticalScrollBarEnabled(true);
        recyclerListView = this.attachPhotoRecyclerView;
        listAdapter = new PhotoAttachAdapter(context);
        this.photoAttachAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.attachPhotoRecyclerView.setClipToPadding(false);
        this.attachPhotoRecyclerView.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(8.0f), 0);
        this.attachPhotoRecyclerView.setItemAnimator(null);
        this.attachPhotoRecyclerView.setLayoutAnimation(null);
        this.attachPhotoRecyclerView.setOverScrollMode(2);
        this.attachView.addView(this.attachPhotoRecyclerView, LayoutHelper.createFrame(-1, 80.0f));
        this.attachPhotoLayoutManager = new LinearLayoutManager(context) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.attachPhotoLayoutManager.setOrientation(0);
        this.attachPhotoRecyclerView.setLayoutManager(this.attachPhotoLayoutManager);
        this.attachPhotoRecyclerView.setOnItemClickListener(new C25327());
        this.attachPhotoRecyclerView.setOnScrollListener(new C25338());
        viewArr = this.views;
        CorrectlyMeasuringTextView correctlyMeasuringTextView = new CorrectlyMeasuringTextView(context);
        this.mediaBanTooltip = correctlyMeasuringTextView;
        viewArr[11] = correctlyMeasuringTextView;
        this.mediaBanTooltip.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), -12171706));
        this.mediaBanTooltip.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f));
        this.mediaBanTooltip.setGravity(16);
        this.mediaBanTooltip.setTextSize(1, 14.0f);
        this.mediaBanTooltip.setTextColor(-1);
        this.mediaBanTooltip.setVisibility(4);
        this.attachView.addView(this.mediaBanTooltip, LayoutHelper.createFrame(-2, -2.0f, 51, 14.0f, 0.0f, 14.0f, 0.0f));
        viewArr = this.views;
        EmptyTextProgressView emptyTextProgressView = new EmptyTextProgressView(context);
        this.progressView = emptyTextProgressView;
        viewArr[9] = emptyTextProgressView;
        if (VERSION.SDK_INT < 23 || getContext().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
            this.progressView.setText(LocaleController.getString("NoPhotos", R.string.NoPhotos));
            this.progressView.setTextSize(20);
        } else {
            this.progressView.setText(LocaleController.getString("PermissionStorage", R.string.PermissionStorage));
            this.progressView.setTextSize(16);
        }
        this.attachView.addView(this.progressView, LayoutHelper.createFrame(-1, 80.0f));
        this.attachPhotoRecyclerView.setEmptyView(this.progressView);
        viewArr = this.views;
        View c25349 = new View(getContext()) {
            public boolean hasOverlappingRendering() {
                return false;
            }
        };
        this.lineView = c25349;
        viewArr[10] = c25349;
        this.lineView.setBackgroundColor(Theme.getColor(Theme.key_dialogGrayLine));
        this.attachView.addView(this.lineView, new FrameLayout.LayoutParams(-1, 1, 51));
        CharSequence[] items = new CharSequence[]{LocaleController.getString("ChatCamera", R.string.ChatCamera), LocaleController.getString("ChatGallery", R.string.ChatGallery), LocaleController.getString("ChatVideo", R.string.ChatVideo), LocaleController.getString("AttachMusic", R.string.AttachMusic), LocaleController.getString("ChatDocument", R.string.ChatDocument), LocaleController.getString("AttachContact", R.string.AttachContact), LocaleController.getString("ChatLocation", R.string.ChatLocation), ""};
        for (a = 0; a < 8; a++) {
            AttachButton attachButton = new AttachButton(context);
            this.attachButtons.add(attachButton);
            attachButton.setTextAndIcon(items[a], Theme.chat_attachButtonDrawables[a]);
            this.attachView.addView(attachButton, LayoutHelper.createFrame(85, 90, 51));
            attachButton.setTag(Integer.valueOf(a));
            this.views[a] = attachButton;
            if (a == 7) {
                this.sendPhotosButton = attachButton;
                this.sendPhotosButton.imageView.setPadding(0, AndroidUtilities.dp(4.0f), 0, 0);
            } else if (a == 4) {
                this.sendDocumentsButton = attachButton;
            }
            attachButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ChatAttachAlert.this.delegate.didPressedButton(((Integer) v.getTag()).intValue());
                }
            });
        }
        this.hintTextView = new TextView(context);
        this.hintTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
        this.hintTextView.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
        this.hintTextView.setTextSize(1, 14.0f);
        this.hintTextView.setPadding(AndroidUtilities.dp(10.0f), 0, AndroidUtilities.dp(10.0f), 0);
        this.hintTextView.setText(LocaleController.getString("AttachBotsHelp", R.string.AttachBotsHelp));
        this.hintTextView.setGravity(16);
        this.hintTextView.setVisibility(4);
        this.hintTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.scroll_tip, 0, 0, 0);
        this.hintTextView.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        this.attachView.addView(this.hintTextView, LayoutHelper.createFrame(-2, 32.0f, 85, 5.0f, 0.0f, 5.0f, 5.0f));
        for (a = 0; a < 8; a++) {
            this.viewsCache.add(this.photoAttachAdapter.createHolder());
        }
        if (this.loading) {
            this.progressView.showProgress();
        } else {
            this.progressView.showTextView();
        }
        this.recordTime = new TextView(context);
        this.recordTime.setBackgroundResource(R.drawable.system);
        this.recordTime.getBackground().setColorFilter(new PorterDuffColorFilter(1711276032, Mode.MULTIPLY));
        this.recordTime.setTextSize(1, 15.0f);
        this.recordTime.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.recordTime.setAlpha(0.0f);
        this.recordTime.setTextColor(-1);
        this.recordTime.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(5.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(5.0f));
        this.container.addView(this.recordTime, LayoutHelper.createFrame(-2, -2.0f, 49, 0.0f, 16.0f, 0.0f, 0.0f));
        this.cameraPanel = new FrameLayout(context) {
            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                int cx2;
                int cy2;
                int cx = getMeasuredWidth() / 2;
                int cy = getMeasuredHeight() / 2;
                ChatAttachAlert.this.shutterButton.layout(cx - (ChatAttachAlert.this.shutterButton.getMeasuredWidth() / 2), cy - (ChatAttachAlert.this.shutterButton.getMeasuredHeight() / 2), (ChatAttachAlert.this.shutterButton.getMeasuredWidth() / 2) + cx, (ChatAttachAlert.this.shutterButton.getMeasuredHeight() / 2) + cy);
                if (getMeasuredWidth() == AndroidUtilities.dp(100.0f)) {
                    cx2 = getMeasuredWidth() / 2;
                    cx = cx2;
                    cy2 = ((cy / 2) + cy) + AndroidUtilities.dp(17.0f);
                    cy = (cy / 2) - AndroidUtilities.dp(17.0f);
                } else {
                    cx2 = ((cx / 2) + cx) + AndroidUtilities.dp(17.0f);
                    cx = (cx / 2) - AndroidUtilities.dp(17.0f);
                    cy2 = getMeasuredHeight() / 2;
                    cy = cy2;
                }
                ChatAttachAlert.this.switchCameraButton.layout(cx2 - (ChatAttachAlert.this.switchCameraButton.getMeasuredWidth() / 2), cy2 - (ChatAttachAlert.this.switchCameraButton.getMeasuredHeight() / 2), (ChatAttachAlert.this.switchCameraButton.getMeasuredWidth() / 2) + cx2, (ChatAttachAlert.this.switchCameraButton.getMeasuredHeight() / 2) + cy2);
                for (int a = 0; a < 2; a++) {
                    ChatAttachAlert.this.flashModeButton[a].layout(cx - (ChatAttachAlert.this.flashModeButton[a].getMeasuredWidth() / 2), cy - (ChatAttachAlert.this.flashModeButton[a].getMeasuredHeight() / 2), (ChatAttachAlert.this.flashModeButton[a].getMeasuredWidth() / 2) + cx, (ChatAttachAlert.this.flashModeButton[a].getMeasuredHeight() / 2) + cy);
                }
            }
        };
        this.cameraPanel.setVisibility(8);
        this.cameraPanel.setAlpha(0.0f);
        this.container.addView(this.cameraPanel, LayoutHelper.createFrame(-1, 100, 83));
        this.shutterButton = new ShutterButton(context);
        this.cameraPanel.addView(this.shutterButton, LayoutHelper.createFrame(84, 84, 17));
        this.shutterButton.setDelegate(new ShutterButtonDelegate() {

            /* renamed from: org.telegram.ui.Components.ChatAttachAlert$12$1 */
            class C25151 implements Runnable {
                C25151() {
                }

                public void run() {
                    if (ChatAttachAlert.this.videoRecordRunnable != null) {
                        ChatAttachAlert.this.videoRecordTime = ChatAttachAlert.this.videoRecordTime + 1;
                        ChatAttachAlert.this.recordTime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(ChatAttachAlert.this.videoRecordTime / 60), Integer.valueOf(ChatAttachAlert.this.videoRecordTime % 60)}));
                        AndroidUtilities.runOnUIThread(ChatAttachAlert.this.videoRecordRunnable, 1000);
                    }
                }
            }

            /* renamed from: org.telegram.ui.Components.ChatAttachAlert$12$2 */
            class C25182 implements VideoTakeCallback {
                C25182() {
                }

                public void onFinishVideoRecording(final Bitmap thumb) {
                    if (ChatAttachAlert.this.cameraFile != null && ChatAttachAlert.this.baseFragment != null) {
                        PhotoViewer.getInstance().setParentActivity(ChatAttachAlert.this.baseFragment.getParentActivity());
                        PhotoViewer.getInstance().setParentAlert(ChatAttachAlert.this);
                        ChatAttachAlert.this.cameraPhoto = new ArrayList();
                        ChatAttachAlert.this.cameraPhoto.add(new MediaController$PhotoEntry(0, 0, 0, ChatAttachAlert.this.cameraFile.getAbsolutePath(), 0, true));
                        PhotoViewer.getInstance().openPhotoForSelect(ChatAttachAlert.this.cameraPhoto, 0, 2, new EmptyPhotoViewerProvider() {

                            /* renamed from: org.telegram.ui.Components.ChatAttachAlert$12$2$1$1 */
                            class C25161 implements Runnable {
                                C25161() {
                                }

                                public void run() {
                                    if (ChatAttachAlert.this.cameraView != null && !ChatAttachAlert.this.isDismissed() && VERSION.SDK_INT >= 21) {
                                        ChatAttachAlert.this.cameraView.setSystemUiVisibility(1028);
                                    }
                                }
                            }

                            public Bitmap getThumbForPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
                                return thumb;
                            }

                            public boolean cancelButtonPressed() {
                                if (!(!ChatAttachAlert.this.cameraOpened || ChatAttachAlert.this.cameraView == null || ChatAttachAlert.this.cameraFile == null)) {
                                    ChatAttachAlert.this.cameraFile.delete();
                                    AndroidUtilities.runOnUIThread(new C25161(), 1000);
                                    CameraController.getInstance().startPreview(ChatAttachAlert.this.cameraView.getCameraSession());
                                    ChatAttachAlert.this.cameraFile = null;
                                }
                                return true;
                            }

                            public void sendButtonPressed(int index, VideoEditedInfo videoEditedInfo) {
                                if (ChatAttachAlert.this.cameraFile != null && ChatAttachAlert.this.baseFragment != null) {
                                    AndroidUtilities.addMediaToGallery(ChatAttachAlert.this.cameraFile.getAbsolutePath());
                                    ChatAttachAlert.this.baseFragment.sendMedia((MediaController$PhotoEntry) ChatAttachAlert.this.cameraPhoto.get(0), videoEditedInfo);
                                    ChatAttachAlert.this.closeCamera(false);
                                    ChatAttachAlert.this.dismiss();
                                    ChatAttachAlert.this.cameraFile = null;
                                }
                            }

                            public void willHidePhotoViewer() {
                                ChatAttachAlert.this.mediaCaptured = false;
                            }

                            public boolean canScrollAway() {
                                return false;
                            }
                        }, ChatAttachAlert.this.baseFragment);
                    }
                }
            }

            /* renamed from: org.telegram.ui.Components.ChatAttachAlert$12$3 */
            class C25193 implements Runnable {
                C25193() {
                }

                public void run() {
                    AndroidUtilities.runOnUIThread(ChatAttachAlert.this.videoRecordRunnable, 1000);
                }
            }

            public boolean shutterLongPressed() {
                if (ChatAttachAlert.this.mediaCaptured || ChatAttachAlert.this.takingPhoto || ChatAttachAlert.this.baseFragment == null || ChatAttachAlert.this.baseFragment.getParentActivity() == null || ChatAttachAlert.this.cameraView == null) {
                    return false;
                }
                if (VERSION.SDK_INT < 23 || ChatAttachAlert.this.baseFragment.getParentActivity().checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
                    for (int a = 0; a < 2; a++) {
                        ChatAttachAlert.this.flashModeButton[a].setAlpha(0.0f);
                    }
                    ChatAttachAlert.this.switchCameraButton.setAlpha(0.0f);
                    ChatAttachAlert.this.cameraFile = AndroidUtilities.generateVideoPath();
                    ChatAttachAlert.this.recordTime.setAlpha(1.0f);
                    ChatAttachAlert.this.recordTime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(0), Integer.valueOf(0)}));
                    ChatAttachAlert.this.videoRecordTime = 0;
                    ChatAttachAlert.this.videoRecordRunnable = new C25151();
                    AndroidUtilities.lockOrientation(parentFragment.getParentActivity());
                    CameraController.getInstance().recordVideo(ChatAttachAlert.this.cameraView.getCameraSession(), ChatAttachAlert.this.cameraFile, new C25182(), new C25193(), false);
                    ChatAttachAlert.this.shutterButton.setState(ShutterButton.State.RECORDING, true);
                    return true;
                }
                ChatAttachAlert.this.requestingPermissions = true;
                ChatAttachAlert.this.baseFragment.getParentActivity().requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 21);
                return false;
            }

            public void shutterCancel() {
                if (!ChatAttachAlert.this.mediaCaptured) {
                    ChatAttachAlert.this.cameraFile.delete();
                    ChatAttachAlert.this.resetRecordState();
                    CameraController.getInstance().stopVideoRecording(ChatAttachAlert.this.cameraView.getCameraSession(), true);
                }
            }

            public void shutterReleased() {
                if (!ChatAttachAlert.this.takingPhoto && ChatAttachAlert.this.cameraView != null && !ChatAttachAlert.this.mediaCaptured && ChatAttachAlert.this.cameraView.getCameraSession() != null) {
                    ChatAttachAlert.this.mediaCaptured = true;
                    if (ChatAttachAlert.this.shutterButton.getState() == ShutterButton.State.RECORDING) {
                        ChatAttachAlert.this.resetRecordState();
                        CameraController.getInstance().stopVideoRecording(ChatAttachAlert.this.cameraView.getCameraSession(), false);
                        ChatAttachAlert.this.shutterButton.setState(ShutterButton.State.DEFAULT, true);
                        return;
                    }
                    ChatAttachAlert.this.cameraFile = AndroidUtilities.generatePicturePath();
                    final boolean sameTakePictureOrientation = ChatAttachAlert.this.cameraView.getCameraSession().isSameTakePictureOrientation();
                    ChatAttachAlert.this.takingPhoto = CameraController.getInstance().takePicture(ChatAttachAlert.this.cameraFile, ChatAttachAlert.this.cameraView.getCameraSession(), new Runnable() {

                        /* renamed from: org.telegram.ui.Components.ChatAttachAlert$12$4$1 */
                        class C25211 extends EmptyPhotoViewerProvider {

                            /* renamed from: org.telegram.ui.Components.ChatAttachAlert$12$4$1$1 */
                            class C25201 implements Runnable {
                                C25201() {
                                }

                                public void run() {
                                    if (ChatAttachAlert.this.cameraView != null && !ChatAttachAlert.this.isDismissed() && VERSION.SDK_INT >= 21) {
                                        ChatAttachAlert.this.cameraView.setSystemUiVisibility(1028);
                                    }
                                }
                            }

                            C25211() {
                            }

                            public boolean cancelButtonPressed() {
                                if (!(!ChatAttachAlert.this.cameraOpened || ChatAttachAlert.this.cameraView == null || ChatAttachAlert.this.cameraFile == null)) {
                                    ChatAttachAlert.this.cameraFile.delete();
                                    AndroidUtilities.runOnUIThread(new C25201(), 1000);
                                    CameraController.getInstance().startPreview(ChatAttachAlert.this.cameraView.getCameraSession());
                                    ChatAttachAlert.this.cameraFile = null;
                                }
                                return true;
                            }

                            public void sendButtonPressed(int index, VideoEditedInfo videoEditedInfo) {
                                if (ChatAttachAlert.this.cameraFile != null && ChatAttachAlert.this.baseFragment != null) {
                                    AndroidUtilities.addMediaToGallery(ChatAttachAlert.this.cameraFile.getAbsolutePath());
                                    ChatAttachAlert.this.baseFragment.sendMedia((MediaController$PhotoEntry) ChatAttachAlert.this.cameraPhoto.get(0), null);
                                    ChatAttachAlert.this.closeCamera(false);
                                    ChatAttachAlert.this.dismiss();
                                    ChatAttachAlert.this.cameraFile = null;
                                }
                            }

                            public boolean scaleToFill() {
                                if (ChatAttachAlert.this.baseFragment == null || ChatAttachAlert.this.baseFragment.getParentActivity() == null) {
                                    return false;
                                }
                                int locked = System.getInt(ChatAttachAlert.this.baseFragment.getParentActivity().getContentResolver(), "accelerometer_rotation", 0);
                                if (sameTakePictureOrientation || locked == 1) {
                                    return true;
                                }
                                return false;
                            }

                            public void willHidePhotoViewer() {
                                ChatAttachAlert.this.mediaCaptured = false;
                            }

                            public boolean canScrollAway() {
                                return false;
                            }
                        }

                        public void run() {
                            ChatAttachAlert.this.takingPhoto = false;
                            if (ChatAttachAlert.this.cameraFile != null && ChatAttachAlert.this.baseFragment != null) {
                                PhotoViewer.getInstance().setParentActivity(ChatAttachAlert.this.baseFragment.getParentActivity());
                                PhotoViewer.getInstance().setParentAlert(ChatAttachAlert.this);
                                ChatAttachAlert.this.cameraPhoto = new ArrayList();
                                int orientation = 0;
                                try {
                                    switch (new ExifInterface(ChatAttachAlert.this.cameraFile.getAbsolutePath()).getAttributeInt("Orientation", 1)) {
                                        case 3:
                                            orientation = 180;
                                            break;
                                        case 6:
                                            orientation = 90;
                                            break;
                                        case 8:
                                            orientation = 270;
                                            break;
                                    }
                                } catch (Exception e) {
                                    FileLog.e(e);
                                }
                                ChatAttachAlert.this.cameraPhoto.add(new MediaController$PhotoEntry(0, 0, 0, ChatAttachAlert.this.cameraFile.getAbsolutePath(), orientation, false));
                                PhotoViewer.getInstance().openPhotoForSelect(ChatAttachAlert.this.cameraPhoto, 0, 2, new C25211(), ChatAttachAlert.this.baseFragment);
                            }
                        }
                    });
                }
            }
        });
        this.switchCameraButton = new ImageView(context);
        this.switchCameraButton.setScaleType(ScaleType.CENTER);
        this.cameraPanel.addView(this.switchCameraButton, LayoutHelper.createFrame(48, 48, 21));
        this.switchCameraButton.setOnClickListener(new View.OnClickListener() {

            /* renamed from: org.telegram.ui.Components.ChatAttachAlert$13$1 */
            class C25231 extends AnimatorListenerAdapter {
                C25231() {
                }

                public void onAnimationEnd(Animator animator) {
                    ChatAttachAlert.this.switchCameraButton.setImageResource(ChatAttachAlert.this.cameraView.isFrontface() ? R.drawable.camera_revert1 : R.drawable.camera_revert2);
                    ObjectAnimator.ofFloat(ChatAttachAlert.this.switchCameraButton, "scaleX", new float[]{1.0f}).setDuration(100).start();
                }
            }

            public void onClick(View v) {
                if (!ChatAttachAlert.this.takingPhoto && ChatAttachAlert.this.cameraView != null && ChatAttachAlert.this.cameraView.isInitied()) {
                    ChatAttachAlert.this.cameraInitied = false;
                    ChatAttachAlert.this.cameraView.switchCamera();
                    ObjectAnimator animator = ObjectAnimator.ofFloat(ChatAttachAlert.this.switchCameraButton, "scaleX", new float[]{0.0f}).setDuration(100);
                    animator.addListener(new C25231());
                    animator.start();
                }
            }
        });
        for (a = 0; a < 2; a++) {
            this.flashModeButton[a] = new ImageView(context);
            this.flashModeButton[a].setScaleType(ScaleType.CENTER);
            this.flashModeButton[a].setVisibility(4);
            this.cameraPanel.addView(this.flashModeButton[a], LayoutHelper.createFrame(48, 48, 51));
            this.flashModeButton[a].setOnClickListener(new View.OnClickListener() {
                public void onClick(final View currentImage) {
                    if (!ChatAttachAlert.this.flashAnimationInProgress && ChatAttachAlert.this.cameraView != null && ChatAttachAlert.this.cameraView.isInitied() && ChatAttachAlert.this.cameraOpened) {
                        String current = ChatAttachAlert.this.cameraView.getCameraSession().getCurrentFlashMode();
                        String next = ChatAttachAlert.this.cameraView.getCameraSession().getNextFlashMode();
                        if (!current.equals(next)) {
                            ChatAttachAlert.this.cameraView.getCameraSession().setCurrentFlashMode(next);
                            ChatAttachAlert.this.flashAnimationInProgress = true;
                            ImageView nextImage = ChatAttachAlert.this.flashModeButton[0] == currentImage ? ChatAttachAlert.this.flashModeButton[1] : ChatAttachAlert.this.flashModeButton[0];
                            nextImage.setVisibility(0);
                            ChatAttachAlert.this.setCameraFlashModeIcon(nextImage, next);
                            AnimatorSet animatorSet = new AnimatorSet();
                            r4 = new Animator[4];
                            r4[0] = ObjectAnimator.ofFloat(currentImage, "translationY", new float[]{0.0f, (float) AndroidUtilities.dp(48.0f)});
                            r4[1] = ObjectAnimator.ofFloat(nextImage, "translationY", new float[]{(float) (-AndroidUtilities.dp(48.0f)), 0.0f});
                            r4[2] = ObjectAnimator.ofFloat(currentImage, "alpha", new float[]{1.0f, 0.0f});
                            r4[3] = ObjectAnimator.ofFloat(nextImage, "alpha", new float[]{0.0f, 1.0f});
                            animatorSet.playTogether(r4);
                            animatorSet.setDuration(200);
                            animatorSet.addListener(new AnimatorListenerAdapter() {
                                public void onAnimationEnd(Animator animator) {
                                    ChatAttachAlert.this.flashAnimationInProgress = false;
                                    currentImage.setVisibility(4);
                                }
                            });
                            animatorSet.start();
                        }
                    }
                }
            });
        }
    }

    private boolean processTouchEvent(MotionEvent event) {
        if ((this.pressed || event.getActionMasked() != 0) && event.getActionMasked() != 5) {
            if (this.pressed) {
                AnimatorSet animatorSet;
                Animator[] animatorArr;
                if (event.getActionMasked() == 2) {
                    float newY = event.getY();
                    float dy = newY - this.lastY;
                    if (this.maybeStartDraging) {
                        if (Math.abs(dy) > AndroidUtilities.getPixelsInCM(0.4f, false)) {
                            this.maybeStartDraging = false;
                            this.dragging = true;
                        }
                    } else if (this.dragging && this.cameraView != null) {
                        this.cameraView.setTranslationY(this.cameraView.getTranslationY() + dy);
                        this.lastY = newY;
                        if (this.cameraPanel.getTag() == null) {
                            this.cameraPanel.setTag(Integer.valueOf(1));
                            animatorSet = new AnimatorSet();
                            animatorArr = new Animator[3];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.cameraPanel, "alpha", new float[]{0.0f});
                            animatorArr[1] = ObjectAnimator.ofFloat(this.flashModeButton[0], "alpha", new float[]{0.0f});
                            animatorArr[2] = ObjectAnimator.ofFloat(this.flashModeButton[1], "alpha", new float[]{0.0f});
                            animatorSet.playTogether(animatorArr);
                            animatorSet.setDuration(200);
                            animatorSet.start();
                        }
                    }
                } else if (event.getActionMasked() == 3 || event.getActionMasked() == 1 || event.getActionMasked() == 6) {
                    this.pressed = false;
                    if (this.dragging) {
                        this.dragging = false;
                        if (this.cameraView != null) {
                            if (Math.abs(this.cameraView.getTranslationY()) > ((float) this.cameraView.getMeasuredHeight()) / 6.0f) {
                                closeCamera(true);
                            } else {
                                animatorSet = new AnimatorSet();
                                animatorArr = new Animator[4];
                                animatorArr[0] = ObjectAnimator.ofFloat(this.cameraView, "translationY", new float[]{0.0f});
                                animatorArr[1] = ObjectAnimator.ofFloat(this.cameraPanel, "alpha", new float[]{1.0f});
                                animatorArr[2] = ObjectAnimator.ofFloat(this.flashModeButton[0], "alpha", new float[]{1.0f});
                                animatorArr[3] = ObjectAnimator.ofFloat(this.flashModeButton[1], "alpha", new float[]{1.0f});
                                animatorSet.playTogether(animatorArr);
                                animatorSet.setDuration(250);
                                animatorSet.setInterpolator(this.interpolator);
                                animatorSet.start();
                                this.cameraPanel.setTag(null);
                            }
                        }
                    } else if (this.cameraView != null) {
                        this.cameraView.getLocationOnScreen(this.viewPosition);
                        this.cameraView.focusToPoint((int) (event.getRawX() - ((float) this.viewPosition[0])), (int) (event.getRawY() - ((float) this.viewPosition[1])));
                    }
                }
            }
        } else if (!this.takingPhoto) {
            this.pressed = true;
            this.maybeStartDraging = true;
            this.lastY = event.getY();
        }
        return true;
    }

    protected boolean onContainerTouchEvent(MotionEvent event) {
        return this.cameraOpened && processTouchEvent(event);
    }

    private void resetRecordState() {
        if (this.baseFragment != null) {
            for (int a = 0; a < 2; a++) {
                this.flashModeButton[a].setAlpha(1.0f);
            }
            this.switchCameraButton.setAlpha(1.0f);
            this.recordTime.setAlpha(0.0f);
            AndroidUtilities.cancelRunOnUIThread(this.videoRecordRunnable);
            this.videoRecordRunnable = null;
            AndroidUtilities.unlockOrientation(this.baseFragment.getParentActivity());
        }
    }

    private void setCameraFlashModeIcon(ImageView imageView, String mode) {
        Object obj = -1;
        switch (mode.hashCode()) {
            case 3551:
                if (mode.equals("on")) {
                    obj = 1;
                    break;
                }
                break;
            case 109935:
                if (mode.equals("off")) {
                    obj = null;
                    break;
                }
                break;
            case 3005871:
                if (mode.equals("auto")) {
                    obj = 2;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                imageView.setImageResource(R.drawable.flash_off);
                return;
            case 1:
                imageView.setImageResource(R.drawable.flash_on);
                return;
            case 2:
                imageView.setImageResource(R.drawable.flash_auto);
                return;
            default:
                return;
        }
    }

    protected boolean onCustomMeasure(View view, int width, int height) {
        boolean isPortrait;
        if (width < height) {
            isPortrait = true;
        } else {
            isPortrait = false;
        }
        if (view == this.cameraView) {
            if (this.cameraOpened && !this.cameraAnimationInProgress) {
                this.cameraView.measure(MeasureSpec.makeMeasureSpec(width, 1073741824), MeasureSpec.makeMeasureSpec(height, 1073741824));
                return true;
            }
        } else if (view == this.cameraPanel) {
            if (isPortrait) {
                this.cameraPanel.measure(MeasureSpec.makeMeasureSpec(width, 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(100.0f), 1073741824));
                return true;
            }
            this.cameraPanel.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(100.0f), 1073741824), MeasureSpec.makeMeasureSpec(height, 1073741824));
            return true;
        }
        return false;
    }

    protected boolean onCustomLayout(View view, int left, int top, int right, int bottom) {
        boolean isPortrait;
        int width = right - left;
        int height = bottom - top;
        if (width < height) {
            isPortrait = true;
        } else {
            isPortrait = false;
        }
        if (view == this.cameraPanel) {
            if (isPortrait) {
                this.cameraPanel.layout(0, bottom - AndroidUtilities.dp(100.0f), width, bottom);
                return true;
            }
            this.cameraPanel.layout(right - AndroidUtilities.dp(100.0f), 0, right, height);
            return true;
        } else if (view != this.flashModeButton[0] && view != this.flashModeButton[1]) {
            return false;
        } else {
            int topAdd;
            int leftAdd;
            if (VERSION.SDK_INT >= 21) {
                topAdd = AndroidUtilities.dp(10.0f);
            } else {
                topAdd = 0;
            }
            if (VERSION.SDK_INT >= 21) {
                leftAdd = AndroidUtilities.dp(8.0f);
            } else {
                leftAdd = 0;
            }
            if (isPortrait) {
                view.layout((right - view.getMeasuredWidth()) - leftAdd, topAdd, right - leftAdd, view.getMeasuredHeight() + topAdd);
                return true;
            }
            view.layout(leftAdd, topAdd, view.getMeasuredWidth() + leftAdd, view.getMeasuredHeight() + topAdd);
            return true;
        }
    }

    private void hideHint() {
        if (this.hideHintRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.hideHintRunnable);
            this.hideHintRunnable = null;
        }
        if (this.hintTextView != null) {
            this.currentHintAnimation = new AnimatorSet();
            AnimatorSet animatorSet = this.currentHintAnimation;
            Animator[] animatorArr = new Animator[1];
            animatorArr[0] = ObjectAnimator.ofFloat(this.hintTextView, "alpha", new float[]{0.0f});
            animatorSet.playTogether(animatorArr);
            this.currentHintAnimation.setInterpolator(this.decelerateInterpolator);
            this.currentHintAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    if (ChatAttachAlert.this.currentHintAnimation != null && ChatAttachAlert.this.currentHintAnimation.equals(animation)) {
                        ChatAttachAlert.this.currentHintAnimation = null;
                        if (ChatAttachAlert.this.hintTextView != null) {
                            ChatAttachAlert.this.hintTextView.setVisibility(4);
                        }
                    }
                }

                public void onAnimationCancel(Animator animation) {
                    if (ChatAttachAlert.this.currentHintAnimation != null && ChatAttachAlert.this.currentHintAnimation.equals(animation)) {
                        ChatAttachAlert.this.currentHintAnimation = null;
                    }
                }
            });
            this.currentHintAnimation.setDuration(300);
            this.currentHintAnimation.start();
        }
    }

    public void onPause() {
        if (this.shutterButton != null) {
            if (this.requestingPermissions) {
                if (this.cameraView != null && this.shutterButton.getState() == ShutterButton.State.RECORDING) {
                    this.shutterButton.setState(ShutterButton.State.DEFAULT, true);
                }
                this.requestingPermissions = false;
            } else {
                if (this.cameraView != null && this.shutterButton.getState() == ShutterButton.State.RECORDING) {
                    resetRecordState();
                    CameraController.getInstance().stopVideoRecording(this.cameraView.getCameraSession(), false);
                    this.shutterButton.setState(ShutterButton.State.DEFAULT, true);
                }
                if (this.cameraOpened) {
                    closeCamera(false);
                }
                hideCamera(true);
            }
            this.paused = true;
        }
    }

    public void onResume() {
        this.paused = false;
        if (isShowing() && !isDismissed()) {
            checkCamera(false);
        }
    }

    private void openCamera() {
        if (this.cameraView != null) {
            this.animateCameraValues[0] = 0;
            this.animateCameraValues[1] = AndroidUtilities.dp(80.0f) - this.cameraViewOffsetX;
            this.animateCameraValues[2] = AndroidUtilities.dp(80.0f) - this.cameraViewOffsetY;
            this.cameraAnimationInProgress = true;
            this.cameraPanel.setVisibility(0);
            this.cameraPanel.setTag(null);
            ArrayList<Animator> animators = new ArrayList();
            animators.add(ObjectAnimator.ofFloat(this, "cameraOpenProgress", new float[]{0.0f, 1.0f}));
            animators.add(ObjectAnimator.ofFloat(this.cameraPanel, "alpha", new float[]{1.0f}));
            for (int a = 0; a < 2; a++) {
                if (this.flashModeButton[a].getVisibility() == 0) {
                    animators.add(ObjectAnimator.ofFloat(this.flashModeButton[a], "alpha", new float[]{1.0f}));
                    break;
                }
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animators);
            animatorSet.setDuration(200);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    ChatAttachAlert.this.cameraAnimationInProgress = false;
                    if (ChatAttachAlert.this.cameraOpened) {
                        ChatAttachAlert.this.delegate.onCameraOpened();
                    }
                }
            });
            animatorSet.start();
            if (VERSION.SDK_INT >= 21) {
                this.cameraView.setSystemUiVisibility(1028);
            }
            this.cameraOpened = true;
        }
    }

    public void closeCamera(boolean animated) {
        if (!this.takingPhoto && this.cameraView != null) {
            this.animateCameraValues[1] = AndroidUtilities.dp(80.0f) - this.cameraViewOffsetX;
            this.animateCameraValues[2] = AndroidUtilities.dp(80.0f) - this.cameraViewOffsetY;
            int a;
            if (animated) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.cameraView.getLayoutParams();
                int[] iArr = this.animateCameraValues;
                int translationY = (int) this.cameraView.getTranslationY();
                layoutParams.topMargin = translationY;
                iArr[0] = translationY;
                this.cameraView.setLayoutParams(layoutParams);
                this.cameraView.setTranslationY(0.0f);
                this.cameraAnimationInProgress = true;
                ArrayList<Animator> animators = new ArrayList();
                animators.add(ObjectAnimator.ofFloat(this, "cameraOpenProgress", new float[]{0.0f}));
                animators.add(ObjectAnimator.ofFloat(this.cameraPanel, "alpha", new float[]{0.0f}));
                for (a = 0; a < 2; a++) {
                    if (this.flashModeButton[a].getVisibility() == 0) {
                        animators.add(ObjectAnimator.ofFloat(this.flashModeButton[a], "alpha", new float[]{0.0f}));
                        break;
                    }
                }
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animators);
                animatorSet.setDuration(200);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        ChatAttachAlert.this.cameraAnimationInProgress = false;
                        ChatAttachAlert.this.cameraOpened = false;
                        if (ChatAttachAlert.this.cameraPanel != null) {
                            ChatAttachAlert.this.cameraPanel.setVisibility(8);
                        }
                        if (VERSION.SDK_INT >= 21 && ChatAttachAlert.this.cameraView != null) {
                            ChatAttachAlert.this.cameraView.setSystemUiVisibility(1024);
                        }
                    }
                });
                animatorSet.start();
                return;
            }
            this.animateCameraValues[0] = 0;
            setCameraOpenProgress(0.0f);
            this.cameraPanel.setAlpha(0.0f);
            this.cameraPanel.setVisibility(8);
            for (a = 0; a < 2; a++) {
                if (this.flashModeButton[a].getVisibility() == 0) {
                    this.flashModeButton[a].setAlpha(0.0f);
                    break;
                }
            }
            this.cameraOpened = false;
            if (VERSION.SDK_INT >= 21) {
                this.cameraView.setSystemUiVisibility(1024);
            }
        }
    }

    public void setCameraOpenProgress(float value) {
        if (this.cameraView != null) {
            boolean isPortrait;
            float endWidth;
            float endHeight;
            this.cameraOpenProgress = value;
            float startWidth = (float) this.animateCameraValues[1];
            float startHeight = (float) this.animateCameraValues[2];
            if (AndroidUtilities.displaySize.x < AndroidUtilities.displaySize.y) {
                isPortrait = true;
            } else {
                isPortrait = false;
            }
            if (isPortrait) {
                endWidth = (float) this.container.getWidth();
                endHeight = (float) this.container.getHeight();
            } else {
                endWidth = (float) this.container.getWidth();
                endHeight = (float) this.container.getHeight();
            }
            if (value == 0.0f) {
                this.cameraView.setClipLeft(this.cameraViewOffsetX);
                this.cameraView.setClipTop(this.cameraViewOffsetY);
                this.cameraView.setTranslationX((float) this.cameraViewLocation[0]);
                this.cameraView.setTranslationY((float) this.cameraViewLocation[1]);
                this.cameraIcon.setTranslationX((float) this.cameraViewLocation[0]);
                this.cameraIcon.setTranslationY((float) this.cameraViewLocation[1]);
            } else if (!(this.cameraView.getTranslationX() == 0.0f && this.cameraView.getTranslationY() == 0.0f)) {
                this.cameraView.setTranslationX(0.0f);
                this.cameraView.setTranslationY(0.0f);
            }
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.cameraView.getLayoutParams();
            layoutParams.width = (int) (((endWidth - startWidth) * value) + startWidth);
            layoutParams.height = (int) (((endHeight - startHeight) * value) + startHeight);
            if (value != 0.0f) {
                this.cameraView.setClipLeft((int) (((float) this.cameraViewOffsetX) * (1.0f - value)));
                this.cameraView.setClipTop((int) (((float) this.cameraViewOffsetY) * (1.0f - value)));
                layoutParams.leftMargin = (int) (((float) this.cameraViewLocation[0]) * (1.0f - value));
                layoutParams.topMargin = (int) ((((float) (this.cameraViewLocation[1] - this.animateCameraValues[0])) * (1.0f - value)) + ((float) this.animateCameraValues[0]));
            } else {
                layoutParams.leftMargin = 0;
                layoutParams.topMargin = 0;
            }
            this.cameraView.setLayoutParams(layoutParams);
            if (value <= 0.5f) {
                this.cameraIcon.setAlpha(1.0f - (value / 0.5f));
            } else {
                this.cameraIcon.setAlpha(0.0f);
            }
        }
    }

    public float getCameraOpenProgress() {
        return this.cameraOpenProgress;
    }

    private void checkCameraViewPosition() {
        if (this.deviceHasGoodCamera) {
            int count = this.attachPhotoRecyclerView.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = this.attachPhotoRecyclerView.getChildAt(a);
                if (child instanceof PhotoAttachCameraCell) {
                    if (VERSION.SDK_INT < 19 || child.isAttachedToWindow()) {
                        child.getLocationInWindow(this.cameraViewLocation);
                        int[] iArr = this.cameraViewLocation;
                        iArr[0] = iArr[0] - getLeftInset();
                        float listViewX = (this.listView.getX() + ((float) backgroundPaddingLeft)) - ((float) getLeftInset());
                        if (((float) this.cameraViewLocation[0]) < listViewX) {
                            this.cameraViewOffsetX = (int) (listViewX - ((float) this.cameraViewLocation[0]));
                            if (this.cameraViewOffsetX >= AndroidUtilities.dp(80.0f)) {
                                this.cameraViewOffsetX = 0;
                                this.cameraViewLocation[0] = AndroidUtilities.dp(-150.0f);
                                this.cameraViewLocation[1] = 0;
                            } else {
                                iArr = this.cameraViewLocation;
                                iArr[0] = iArr[0] + this.cameraViewOffsetX;
                            }
                        } else {
                            this.cameraViewOffsetX = 0;
                        }
                        if (VERSION.SDK_INT < 21 || this.cameraViewLocation[1] >= AndroidUtilities.statusBarHeight) {
                            this.cameraViewOffsetY = 0;
                        } else {
                            this.cameraViewOffsetY = AndroidUtilities.statusBarHeight - this.cameraViewLocation[1];
                            if (this.cameraViewOffsetY >= AndroidUtilities.dp(80.0f)) {
                                this.cameraViewOffsetY = 0;
                                this.cameraViewLocation[0] = AndroidUtilities.dp(-150.0f);
                                this.cameraViewLocation[1] = 0;
                            } else {
                                iArr = this.cameraViewLocation;
                                iArr[1] = iArr[1] + this.cameraViewOffsetY;
                            }
                        }
                        applyCameraViewPosition();
                        return;
                    }
                    this.cameraViewOffsetX = 0;
                    this.cameraViewOffsetY = 0;
                    this.cameraViewLocation[0] = AndroidUtilities.dp(-150.0f);
                    this.cameraViewLocation[1] = 0;
                    applyCameraViewPosition();
                }
            }
            this.cameraViewOffsetX = 0;
            this.cameraViewOffsetY = 0;
            this.cameraViewLocation[0] = AndroidUtilities.dp(-150.0f);
            this.cameraViewLocation[1] = 0;
            applyCameraViewPosition();
        }
    }

    private void applyCameraViewPosition() {
        if (this.cameraView != null) {
            FrameLayout.LayoutParams layoutParams;
            final FrameLayout.LayoutParams layoutParamsFinal;
            if (!this.cameraOpened) {
                this.cameraView.setTranslationX((float) this.cameraViewLocation[0]);
                this.cameraView.setTranslationY((float) this.cameraViewLocation[1]);
            }
            this.cameraIcon.setTranslationX((float) this.cameraViewLocation[0]);
            this.cameraIcon.setTranslationY((float) this.cameraViewLocation[1]);
            int finalWidth = AndroidUtilities.dp(80.0f) - this.cameraViewOffsetX;
            int finalHeight = AndroidUtilities.dp(80.0f) - this.cameraViewOffsetY;
            if (!this.cameraOpened) {
                this.cameraView.setClipLeft(this.cameraViewOffsetX);
                this.cameraView.setClipTop(this.cameraViewOffsetY);
                layoutParams = (FrameLayout.LayoutParams) this.cameraView.getLayoutParams();
                if (!(layoutParams.height == finalHeight && layoutParams.width == finalWidth)) {
                    layoutParams.width = finalWidth;
                    layoutParams.height = finalHeight;
                    this.cameraView.setLayoutParams(layoutParams);
                    layoutParamsFinal = layoutParams;
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (ChatAttachAlert.this.cameraView != null) {
                                ChatAttachAlert.this.cameraView.setLayoutParams(layoutParamsFinal);
                            }
                        }
                    });
                }
            }
            layoutParams = (FrameLayout.LayoutParams) this.cameraIcon.getLayoutParams();
            if (layoutParams.height != finalHeight || layoutParams.width != finalWidth) {
                layoutParams.width = finalWidth;
                layoutParams.height = finalHeight;
                this.cameraIcon.setLayoutParams(layoutParams);
                layoutParamsFinal = layoutParams;
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (ChatAttachAlert.this.cameraIcon != null) {
                            ChatAttachAlert.this.cameraIcon.setLayoutParams(layoutParamsFinal);
                        }
                    }
                });
            }
        }
    }

    public void showCamera() {
        float f = 1.0f;
        if (!this.paused && this.mediaEnabled) {
            if (this.cameraView == null) {
                float f2;
                this.cameraView = new CameraView(this.baseFragment.getParentActivity(), false);
                this.container.addView(this.cameraView, 1, LayoutHelper.createFrame(80, 80.0f));
                this.cameraView.setDelegate(new CameraViewDelegate() {
                    public void onCameraCreated(Camera camera) {
                    }

                    public void onCameraInit() {
                        int a;
                        int i = 0;
                        int count = ChatAttachAlert.this.attachPhotoRecyclerView.getChildCount();
                        for (a = 0; a < count; a++) {
                            View child = ChatAttachAlert.this.attachPhotoRecyclerView.getChildAt(a);
                            if (child instanceof PhotoAttachCameraCell) {
                                child.setVisibility(4);
                                break;
                            }
                        }
                        if (ChatAttachAlert.this.cameraView.getCameraSession().getCurrentFlashMode().equals(ChatAttachAlert.this.cameraView.getCameraSession().getNextFlashMode())) {
                            for (a = 0; a < 2; a++) {
                                ChatAttachAlert.this.flashModeButton[a].setVisibility(4);
                                ChatAttachAlert.this.flashModeButton[a].setAlpha(0.0f);
                                ChatAttachAlert.this.flashModeButton[a].setTranslationY(0.0f);
                            }
                        } else {
                            ChatAttachAlert.this.setCameraFlashModeIcon(ChatAttachAlert.this.flashModeButton[0], ChatAttachAlert.this.cameraView.getCameraSession().getCurrentFlashMode());
                            for (a = 0; a < 2; a++) {
                                int i2;
                                float f;
                                ImageView imageView = ChatAttachAlert.this.flashModeButton[a];
                                if (a == 0) {
                                    i2 = 0;
                                } else {
                                    i2 = 4;
                                }
                                imageView.setVisibility(i2);
                                imageView = ChatAttachAlert.this.flashModeButton[a];
                                if (a == 0 && ChatAttachAlert.this.cameraOpened) {
                                    f = 1.0f;
                                } else {
                                    f = 0.0f;
                                }
                                imageView.setAlpha(f);
                                ChatAttachAlert.this.flashModeButton[a].setTranslationY(0.0f);
                            }
                        }
                        ChatAttachAlert.this.switchCameraButton.setImageResource(ChatAttachAlert.this.cameraView.isFrontface() ? R.drawable.camera_revert1 : R.drawable.camera_revert2);
                        ImageView access$5300 = ChatAttachAlert.this.switchCameraButton;
                        if (!ChatAttachAlert.this.cameraView.hasFrontFaceCamera()) {
                            i = 4;
                        }
                        access$5300.setVisibility(i);
                    }
                });
                this.cameraIcon = new FrameLayout(this.baseFragment.getParentActivity());
                this.container.addView(this.cameraIcon, 2, LayoutHelper.createFrame(80, 80.0f));
                ImageView cameraImageView = new ImageView(this.baseFragment.getParentActivity());
                cameraImageView.setScaleType(ScaleType.CENTER);
                cameraImageView.setImageResource(R.drawable.instant_camera);
                this.cameraIcon.addView(cameraImageView, LayoutHelper.createFrame(80, 80, 85));
                CameraView cameraView = this.cameraView;
                if (this.mediaEnabled) {
                    f2 = 1.0f;
                } else {
                    f2 = 0.2f;
                }
                cameraView.setAlpha(f2);
                this.cameraView.setEnabled(this.mediaEnabled);
                FrameLayout frameLayout = this.cameraIcon;
                if (!this.mediaEnabled) {
                    f = 0.2f;
                }
                frameLayout.setAlpha(f);
                this.cameraIcon.setEnabled(this.mediaEnabled);
            }
            this.cameraView.setTranslationX((float) this.cameraViewLocation[0]);
            this.cameraView.setTranslationY((float) this.cameraViewLocation[1]);
            this.cameraIcon.setTranslationX((float) this.cameraViewLocation[0]);
            this.cameraIcon.setTranslationY((float) this.cameraViewLocation[1]);
        }
    }

    public void hideCamera(boolean async) {
        if (this.deviceHasGoodCamera && this.cameraView != null) {
            this.cameraView.destroy(async, null);
            this.container.removeView(this.cameraView);
            this.container.removeView(this.cameraIcon);
            this.cameraView = null;
            this.cameraIcon = null;
            int count = this.attachPhotoRecyclerView.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = this.attachPhotoRecyclerView.getChildAt(a);
                if (child instanceof PhotoAttachCameraCell) {
                    child.setVisibility(0);
                    return;
                }
            }
        }
    }

    private void showHint() {
        if (!SearchQuery.inlineBots.isEmpty() && !ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("bothint", false)) {
            this.hintShowed = true;
            this.hintTextView.setVisibility(0);
            this.currentHintAnimation = new AnimatorSet();
            this.currentHintAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.hintTextView, "alpha", new float[]{0.0f, 1.0f})});
            this.currentHintAnimation.setInterpolator(this.decelerateInterpolator);
            this.currentHintAnimation.addListener(new AnimatorListenerAdapter() {

                /* renamed from: org.telegram.ui.Components.ChatAttachAlert$21$1 */
                class C25261 implements Runnable {
                    C25261() {
                    }

                    public void run() {
                        if (ChatAttachAlert.this.hideHintRunnable == this) {
                            ChatAttachAlert.this.hideHintRunnable = null;
                            ChatAttachAlert.this.hideHint();
                        }
                    }
                }

                public void onAnimationEnd(Animator animation) {
                    if (ChatAttachAlert.this.currentHintAnimation != null && ChatAttachAlert.this.currentHintAnimation.equals(animation)) {
                        ChatAttachAlert.this.currentHintAnimation = null;
                        AndroidUtilities.runOnUIThread(ChatAttachAlert.this.hideHintRunnable = new C25261(), FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
                    }
                }

                public void onAnimationCancel(Animator animation) {
                    if (ChatAttachAlert.this.currentHintAnimation != null && ChatAttachAlert.this.currentHintAnimation.equals(animation)) {
                        ChatAttachAlert.this.currentHintAnimation = null;
                    }
                }
            });
            this.currentHintAnimation.setDuration(300);
            this.currentHintAnimation.start();
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.albumsDidLoaded) {
            if (this.photoAttachAdapter != null) {
                this.loading = false;
                this.progressView.showTextView();
                this.photoAttachAdapter.notifyDataSetChanged();
            }
        } else if (id == NotificationCenter.reloadInlineHints) {
            if (this.adapter != null) {
                this.adapter.notifyDataSetChanged();
            }
        } else if (id == NotificationCenter.cameraInitied) {
            checkCamera(false);
        }
    }

    @SuppressLint({"NewApi"})
    private void updateLayout() {
        if (this.listView.getChildCount() <= 0) {
            RecyclerListView recyclerListView = this.listView;
            int paddingTop = this.listView.getPaddingTop();
            this.scrollOffsetY = paddingTop;
            recyclerListView.setTopGlowOffset(paddingTop);
            this.listView.invalidate();
            return;
        }
        View child = this.listView.getChildAt(0);
        Holder holder = (Holder) this.listView.findContainingViewHolder(child);
        int top = child.getTop();
        int newOffset = 0;
        if (top >= 0 && holder != null && holder.getAdapterPosition() == 0) {
            newOffset = top;
        }
        if (this.scrollOffsetY != newOffset) {
            recyclerListView = this.listView;
            this.scrollOffsetY = newOffset;
            recyclerListView.setTopGlowOffset(newOffset);
            this.listView.invalidate();
        }
    }

    protected boolean canDismissWithSwipe() {
        return false;
    }

    public void updatePhotosButton() {
        int count = this.photoAttachAdapter.selectedPhotos.size();
        if (count == 0) {
            this.sendPhotosButton.imageView.setPadding(0, AndroidUtilities.dp(4.0f), 0, 0);
            this.sendPhotosButton.imageView.setBackgroundResource(R.drawable.attach_hide_states);
            this.sendPhotosButton.imageView.setImageResource(R.drawable.attach_hide2);
            this.sendPhotosButton.textView.setText("");
            this.sendDocumentsButton.textView.setText(LocaleController.getString("ChatDocument", R.string.ChatDocument));
        } else {
            this.sendPhotosButton.imageView.setPadding(AndroidUtilities.dp(2.0f), 0, 0, 0);
            this.sendPhotosButton.imageView.setBackgroundResource(R.drawable.attach_send_states);
            this.sendPhotosButton.imageView.setImageResource(R.drawable.attach_send2);
            TextView access$7200 = this.sendPhotosButton.textView;
            Object[] objArr = new Object[1];
            objArr[0] = String.format("(%d)", new Object[]{Integer.valueOf(count)});
            access$7200.setText(LocaleController.formatString("SendItems", R.string.SendItems, objArr));
            this.sendDocumentsButton.textView.setText(count == 1 ? LocaleController.getString("SendAsFile", R.string.SendAsFile) : LocaleController.getString("SendAsFiles", R.string.SendAsFiles));
        }
        if (VERSION.SDK_INT < 23 || getContext().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
            this.progressView.setText(LocaleController.getString("NoPhotos", R.string.NoPhotos));
            this.progressView.setTextSize(20);
            return;
        }
        this.progressView.setText(LocaleController.getString("PermissionStorage", R.string.PermissionStorage));
        this.progressView.setTextSize(16);
    }

    public void setDelegate(ChatAttachViewDelegate chatAttachViewDelegate) {
        this.delegate = chatAttachViewDelegate;
    }

    public void loadGalleryPhotos() {
        if (MediaController.allMediaAlbumEntry == null && VERSION.SDK_INT >= 21) {
            MediaController.loadGalleryPhotosAlbums(0);
        }
    }

    public void init() {
        if (MediaController.allMediaAlbumEntry != null) {
            for (int a = 0; a < Math.min(100, MediaController.allMediaAlbumEntry.photos.size()); a++) {
                ((MediaController$PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(a)).reset();
            }
        }
        if (this.currentHintAnimation != null) {
            this.currentHintAnimation.cancel();
            this.currentHintAnimation = null;
        }
        this.hintTextView.setAlpha(0.0f);
        this.hintTextView.setVisibility(4);
        this.attachPhotoLayoutManager.scrollToPositionWithOffset(0, 1000000);
        this.photoAttachAdapter.clearSelectedPhotos();
        this.layoutManager.scrollToPositionWithOffset(0, 1000000);
        updatePhotosButton();
    }

    public HashMap<Object, Object> getSelectedPhotos() {
        return this.photoAttachAdapter.selectedPhotos;
    }

    public ArrayList<Object> getSelectedPhotosOrder() {
        return this.photoAttachAdapter.selectedPhotosOrder;
    }

    public void onDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.albumsDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.reloadInlineHints);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.cameraInitied);
        this.baseFragment = null;
    }

    private PhotoAttachPhotoCell getCellForIndex(int index) {
        if (MediaController.allMediaAlbumEntry == null) {
            return null;
        }
        int count = this.attachPhotoRecyclerView.getChildCount();
        for (int a = 0; a < count; a++) {
            View view = this.attachPhotoRecyclerView.getChildAt(a);
            if (view instanceof PhotoAttachPhotoCell) {
                PhotoAttachPhotoCell cell = (PhotoAttachPhotoCell) view;
                int num = ((Integer) cell.getImageView().getTag()).intValue();
                if (num >= 0 && num < MediaController.allMediaAlbumEntry.photos.size() && num == index) {
                    return cell;
                }
            }
        }
        return null;
    }

    private void onRevealAnimationEnd(boolean open) {
        NotificationCenter.getInstance().setAnimationInProgress(false);
        this.revealAnimationInProgress = false;
        if (open && VERSION.SDK_INT <= 19 && MediaController.allMediaAlbumEntry == null) {
            MediaController.loadGalleryPhotosAlbums(0);
        }
        if (open) {
            checkCamera(true);
            showHint();
        }
    }

    public void checkCamera(boolean request) {
        if (this.baseFragment != null) {
            boolean old = this.deviceHasGoodCamera;
            if (!MediaController.getInstance().canInAppCamera()) {
                this.deviceHasGoodCamera = false;
            } else if (VERSION.SDK_INT < 23) {
                CameraController.getInstance().initCamera();
                this.deviceHasGoodCamera = CameraController.getInstance().isCameraInitied();
            } else if (this.baseFragment.getParentActivity().checkSelfPermission("android.permission.CAMERA") != 0) {
                if (request) {
                    this.baseFragment.getParentActivity().requestPermissions(new String[]{"android.permission.CAMERA"}, 17);
                }
                this.deviceHasGoodCamera = false;
            } else {
                CameraController.getInstance().initCamera();
                this.deviceHasGoodCamera = CameraController.getInstance().isCameraInitied();
            }
            if (!(old == this.deviceHasGoodCamera || this.photoAttachAdapter == null)) {
                this.photoAttachAdapter.notifyDataSetChanged();
            }
            if (isShowing() && this.deviceHasGoodCamera && this.baseFragment != null && this.backDrawable.getAlpha() != 0 && !this.revealAnimationInProgress && !this.cameraOpened) {
                showCamera();
            }
        }
    }

    public void onOpenAnimationEnd() {
        onRevealAnimationEnd(true);
    }

    public void onOpenAnimationStart() {
    }

    public boolean canDismiss() {
        return true;
    }

    public void setAllowDrawContent(boolean value) {
        super.setAllowDrawContent(value);
        checkCameraViewPosition();
    }

    private void setUseRevealAnimation(boolean value) {
        if (!value || (value && VERSION.SDK_INT >= 18 && !AndroidUtilities.isTablet() && VERSION.SDK_INT < 26)) {
            this.useRevealAnimation = value;
        }
    }

    @SuppressLint({"NewApi"})
    protected void setRevealRadius(float radius) {
        this.revealRadius = radius;
        if (VERSION.SDK_INT <= 19) {
            this.listView.invalidate();
        }
        if (!isDismissed()) {
            int a = 0;
            while (a < this.innerAnimators.size()) {
                InnerAnimator innerAnimator = (InnerAnimator) this.innerAnimators.get(a);
                if (innerAnimator.startRadius <= radius) {
                    innerAnimator.animatorSet.start();
                    this.innerAnimators.remove(a);
                    a--;
                }
                a++;
            }
        }
    }

    protected float getRevealRadius() {
        return this.revealRadius;
    }

    @SuppressLint({"NewApi"})
    private void startRevealAnimation(boolean open) {
        int a;
        this.containerView.setTranslationY(0.0f);
        final AnimatorSet animatorSet = new AnimatorSet();
        View view = this.delegate.getRevealView();
        if (view.getVisibility() == 0 && ((ViewGroup) view.getParent()).getVisibility() == 0) {
            float top;
            int[] coords = new int[2];
            view.getLocationInWindow(coords);
            if (VERSION.SDK_INT <= 19) {
                top = (float) ((AndroidUtilities.displaySize.y - this.containerView.getMeasuredHeight()) - AndroidUtilities.statusBarHeight);
            } else {
                top = this.containerView.getY();
            }
            this.revealX = coords[0] + (view.getMeasuredWidth() / 2);
            this.revealY = (int) (((float) (coords[1] + (view.getMeasuredHeight() / 2))) - top);
            if (VERSION.SDK_INT <= 19) {
                this.revealY -= AndroidUtilities.statusBarHeight;
            }
        } else {
            this.revealX = (AndroidUtilities.displaySize.x / 2) + backgroundPaddingLeft;
            this.revealY = (int) (((float) AndroidUtilities.displaySize.y) - this.containerView.getY());
        }
        corners = new int[4][];
        int[] iArr = new int[2];
        corners[0] = new int[]{0, 0};
        corners[1] = new int[]{0, AndroidUtilities.dp(304.0f)};
        corners[2] = new int[]{this.containerView.getMeasuredWidth(), 0};
        corners[3] = new int[]{this.containerView.getMeasuredWidth(), AndroidUtilities.dp(304.0f)};
        int finalRevealRadius = 0;
        int y = (this.revealY - this.scrollOffsetY) + backgroundPaddingTop;
        for (a = 0; a < 4; a++) {
            finalRevealRadius = Math.max(finalRevealRadius, (int) Math.ceil(Math.sqrt((double) (((this.revealX - corners[a][0]) * (this.revealX - corners[a][0])) + ((y - corners[a][1]) * (y - corners[a][1]))))));
        }
        int finalRevealX = this.revealX <= this.containerView.getMeasuredWidth() ? this.revealX : this.containerView.getMeasuredWidth();
        ArrayList<Animator> animators = new ArrayList(3);
        String str = "revealRadius";
        float[] fArr = new float[2];
        fArr[0] = open ? 0.0f : (float) finalRevealRadius;
        fArr[1] = open ? (float) finalRevealRadius : 0.0f;
        animators.add(ObjectAnimator.ofFloat(this, str, fArr));
        ColorDrawable colorDrawable = this.backDrawable;
        String str2 = "alpha";
        int[] iArr2 = new int[1];
        iArr2[0] = open ? 51 : 0;
        animators.add(ObjectAnimator.ofInt(colorDrawable, str2, iArr2));
        if (VERSION.SDK_INT >= 21) {
            try {
                animators.add(ViewAnimationUtils.createCircularReveal(this.containerView, finalRevealX, this.revealY, open ? 0.0f : (float) finalRevealRadius, open ? (float) finalRevealRadius : 0.0f));
            } catch (Exception e) {
                FileLog.e(e);
            }
            animatorSet.setDuration(320);
        } else if (open) {
            animatorSet.setDuration(250);
            this.containerView.setScaleX(1.0f);
            this.containerView.setScaleY(1.0f);
            this.containerView.setAlpha(1.0f);
            if (VERSION.SDK_INT <= 19) {
                animatorSet.setStartDelay(20);
            }
        } else {
            animatorSet.setDuration(200);
            this.containerView.setPivotX(this.revealX <= this.containerView.getMeasuredWidth() ? (float) this.revealX : (float) this.containerView.getMeasuredWidth());
            this.containerView.setPivotY((float) this.revealY);
            animators.add(ObjectAnimator.ofFloat(this.containerView, "scaleX", new float[]{0.0f}));
            animators.add(ObjectAnimator.ofFloat(this.containerView, "scaleY", new float[]{0.0f}));
            animators.add(ObjectAnimator.ofFloat(this.containerView, "alpha", new float[]{0.0f}));
        }
        animatorSet.playTogether(animators);
        final boolean z = open;
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (ChatAttachAlert.this.currentSheetAnimation != null && ChatAttachAlert.this.currentSheetAnimation.equals(animation)) {
                    ChatAttachAlert.this.currentSheetAnimation = null;
                    ChatAttachAlert.this.onRevealAnimationEnd(z);
                    ChatAttachAlert.this.containerView.invalidate();
                    ChatAttachAlert.this.containerView.setLayerType(0, null);
                    if (!z) {
                        try {
                            ChatAttachAlert.this.dismissInternal();
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                }
            }

            public void onAnimationCancel(Animator animation) {
                if (ChatAttachAlert.this.currentSheetAnimation != null && animatorSet.equals(animation)) {
                    ChatAttachAlert.this.currentSheetAnimation = null;
                }
            }
        });
        if (open) {
            this.innerAnimators.clear();
            NotificationCenter.getInstance().setAllowedNotificationsDutingAnimation(new int[]{NotificationCenter.dialogsNeedReload});
            NotificationCenter.getInstance().setAnimationInProgress(true);
            this.revealAnimationInProgress = true;
            int count = VERSION.SDK_INT <= 19 ? 12 : 8;
            for (a = 0; a < count; a++) {
                AnimatorSet animatorSetInner;
                if (VERSION.SDK_INT <= 19) {
                    if (a < 8) {
                        this.views[a].setScaleX(0.1f);
                        this.views[a].setScaleY(0.1f);
                    }
                    this.views[a].setAlpha(0.0f);
                } else {
                    this.views[a].setScaleX(0.7f);
                    this.views[a].setScaleY(0.7f);
                }
                InnerAnimator innerAnimator = new InnerAnimator();
                int buttonX = this.views[a].getLeft() + (this.views[a].getMeasuredWidth() / 2);
                int buttonY = (this.views[a].getTop() + this.attachView.getTop()) + (this.views[a].getMeasuredHeight() / 2);
                float dist = (float) Math.sqrt((double) (((this.revealX - buttonX) * (this.revealX - buttonX)) + ((this.revealY - buttonY) * (this.revealY - buttonY))));
                float vecY = ((float) (this.revealY - buttonY)) / dist;
                this.views[a].setPivotX(((float) (this.views[a].getMeasuredWidth() / 2)) + (((float) AndroidUtilities.dp(20.0f)) * (((float) (this.revealX - buttonX)) / dist)));
                this.views[a].setPivotY(((float) (this.views[a].getMeasuredHeight() / 2)) + (((float) AndroidUtilities.dp(20.0f)) * vecY));
                innerAnimator.startRadius = dist - ((float) AndroidUtilities.dp(81.0f));
                this.views[a].setTag(R.string.AppName, Integer.valueOf(1));
                animators = new ArrayList();
                if (a < 8) {
                    fArr = new float[2];
                    animators.add(ObjectAnimator.ofFloat(this.views[a], "scaleX", new float[]{0.7f, 1.05f}));
                    fArr = new float[2];
                    animators.add(ObjectAnimator.ofFloat(this.views[a], "scaleY", new float[]{0.7f, 1.05f}));
                    animatorSetInner = new AnimatorSet();
                    r25 = new Animator[2];
                    r25[0] = ObjectAnimator.ofFloat(this.views[a], "scaleX", new float[]{1.0f});
                    r25[1] = ObjectAnimator.ofFloat(this.views[a], "scaleY", new float[]{1.0f});
                    animatorSetInner.playTogether(r25);
                    animatorSetInner.setDuration(100);
                    animatorSetInner.setInterpolator(this.decelerateInterpolator);
                } else {
                    animatorSetInner = null;
                }
                if (VERSION.SDK_INT <= 19) {
                    animators.add(ObjectAnimator.ofFloat(this.views[a], "alpha", new float[]{1.0f}));
                }
                innerAnimator.animatorSet = new AnimatorSet();
                innerAnimator.animatorSet.playTogether(animators);
                innerAnimator.animatorSet.setDuration(150);
                innerAnimator.animatorSet.setInterpolator(this.decelerateInterpolator);
                innerAnimator.animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        if (animatorSetInner != null) {
                            animatorSetInner.start();
                        }
                    }
                });
                this.innerAnimators.add(innerAnimator);
            }
        }
        this.currentSheetAnimation = animatorSet;
        animatorSet.start();
    }

    public void dismissInternal() {
        if (this.containerView != null) {
            this.containerView.setVisibility(4);
        }
        super.dismissInternal();
    }

    protected boolean onCustomOpenAnimation() {
        float f = 1.0f;
        if (this.baseFragment != null) {
            TLRPC$Chat chat = this.baseFragment.getCurrentChat();
            if (ChatObject.isChannel(chat)) {
                boolean z;
                float f2;
                int i;
                if (chat.banned_rights == null || !chat.banned_rights.send_media) {
                    z = true;
                } else {
                    z = false;
                }
                this.mediaEnabled = z;
                for (int a = 0; a < 5; a++) {
                    float f3;
                    AttachButton attachButton = (AttachButton) this.attachButtons.get(a);
                    if (this.mediaEnabled) {
                        f3 = 1.0f;
                    } else {
                        f3 = 0.2f;
                    }
                    attachButton.setAlpha(f3);
                    ((AttachButton) this.attachButtons.get(a)).setEnabled(this.mediaEnabled);
                }
                RecyclerListView recyclerListView = this.attachPhotoRecyclerView;
                if (this.mediaEnabled) {
                    f2 = 1.0f;
                } else {
                    f2 = 0.2f;
                }
                recyclerListView.setAlpha(f2);
                this.attachPhotoRecyclerView.setEnabled(this.mediaEnabled);
                if (!this.mediaEnabled) {
                    if (AndroidUtilities.isBannedForever(chat.banned_rights.until_date)) {
                        this.mediaBanTooltip.setText(LocaleController.formatString("AttachMediaRestrictedForever", R.string.AttachMediaRestrictedForever, new Object[0]));
                    } else {
                        this.mediaBanTooltip.setText(LocaleController.formatString("AttachMediaRestricted", R.string.AttachMediaRestricted, new Object[]{LocaleController.formatDateForBan((long) chat.banned_rights.until_date)}));
                    }
                }
                CorrectlyMeasuringTextView correctlyMeasuringTextView = this.mediaBanTooltip;
                if (this.mediaEnabled) {
                    i = 4;
                } else {
                    i = 0;
                }
                correctlyMeasuringTextView.setVisibility(i);
                if (this.cameraView != null) {
                    CameraView cameraView = this.cameraView;
                    if (this.mediaEnabled) {
                        f2 = 1.0f;
                    } else {
                        f2 = 0.2f;
                    }
                    cameraView.setAlpha(f2);
                    this.cameraView.setEnabled(this.mediaEnabled);
                }
                if (this.cameraIcon != null) {
                    FrameLayout frameLayout = this.cameraIcon;
                    if (!this.mediaEnabled) {
                        f = 0.2f;
                    }
                    frameLayout.setAlpha(f);
                    this.cameraIcon.setEnabled(this.mediaEnabled);
                }
            }
        }
        if (!this.useRevealAnimation) {
            return false;
        }
        startRevealAnimation(true);
        return true;
    }

    protected boolean onCustomCloseAnimation() {
        if (!this.useRevealAnimation) {
            return false;
        }
        this.backDrawable.setAlpha(51);
        startRevealAnimation(false);
        return true;
    }

    public void dismissWithButtonClick(int item) {
        super.dismissWithButtonClick(item);
        boolean z = (item == 0 || item == 2) ? false : true;
        hideCamera(z);
    }

    protected boolean canDismissWithTouchOutside() {
        return !this.cameraOpened;
    }

    public void dismiss() {
        if (!this.cameraAnimationInProgress) {
            if (this.cameraOpened) {
                closeCamera(true);
                return;
            }
            hideCamera(true);
            super.dismiss();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!this.cameraOpened || (keyCode != 24 && keyCode != 25)) {
            return super.onKeyDown(keyCode, event);
        }
        this.shutterButton.getDelegate().shutterReleased();
        return true;
    }
}
