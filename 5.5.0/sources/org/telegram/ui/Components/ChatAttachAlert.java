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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.PhotoEntry;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.camera.CameraController;
import org.telegram.messenger.camera.CameraController.VideoTakeCallback;
import org.telegram.messenger.camera.CameraView;
import org.telegram.messenger.camera.CameraView.CameraViewDelegate;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
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
import org.telegram.tgnet.TLRPC$TL_topPeer;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.FileLocation;
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
    private PhotoViewerProvider photoViewerProvider = new C43631();
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
    class C43631 extends EmptyPhotoViewerProvider {
        C43631() {
        }

        public boolean allowGroupPhotos() {
            return ChatAttachAlert.this.baseFragment != null && ChatAttachAlert.this.baseFragment.allowGroupPhotos();
        }

        public boolean cancelButtonPressed() {
            return false;
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            PhotoAttachPhotoCell access$000 = ChatAttachAlert.this.getCellForIndex(i);
            if (access$000 == null) {
                return null;
            }
            r2 = new int[2];
            access$000.getImageView().getLocationInWindow(r2);
            r2[0] = r2[0] - ChatAttachAlert.this.getLeftInset();
            PlaceProviderObject placeProviderObject = new PlaceProviderObject();
            placeProviderObject.viewX = r2[0];
            placeProviderObject.viewY = r2[1];
            placeProviderObject.parentView = ChatAttachAlert.this.attachPhotoRecyclerView;
            placeProviderObject.imageReceiver = access$000.getImageView().getImageReceiver();
            placeProviderObject.thumb = placeProviderObject.imageReceiver.getBitmap();
            placeProviderObject.scale = access$000.getImageView().getScaleX();
            access$000.showCheck(false);
            return placeProviderObject;
        }

        public int getSelectedCount() {
            return ChatAttachAlert.this.photoAttachAdapter.selectedPhotos.size();
        }

        public HashMap<Object, Object> getSelectedPhotos() {
            return ChatAttachAlert.this.photoAttachAdapter.selectedPhotos;
        }

        public ArrayList<Object> getSelectedPhotosOrder() {
            return ChatAttachAlert.this.photoAttachAdapter.selectedPhotosOrder;
        }

        public Bitmap getThumbForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            PhotoAttachPhotoCell access$000 = ChatAttachAlert.this.getCellForIndex(i);
            return access$000 != null ? access$000.getImageView().getImageReceiver().getBitmap() : null;
        }

        public boolean isPhotoChecked(int i) {
            return i >= 0 && i < MediaController.allMediaAlbumEntry.photos.size() && ChatAttachAlert.this.photoAttachAdapter.selectedPhotos.containsKey(Integer.valueOf(((PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(i)).imageId));
        }

        public void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo) {
            if (ChatAttachAlert.this.photoAttachAdapter.selectedPhotos.isEmpty()) {
                if (i >= 0 && i < MediaController.allMediaAlbumEntry.photos.size()) {
                    PhotoEntry photoEntry = (PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(i);
                    photoEntry.editedInfo = videoEditedInfo;
                    ChatAttachAlert.this.photoAttachAdapter.addToSelectedPhotos(photoEntry, -1);
                } else {
                    return;
                }
            }
            ChatAttachAlert.this.delegate.didPressedButton(7);
        }

        public int setPhotoChecked(int i, VideoEditedInfo videoEditedInfo) {
            if (i < 0 || i >= MediaController.allMediaAlbumEntry.photos.size()) {
                return -1;
            }
            boolean z;
            int indexOf;
            PhotoEntry photoEntry = (PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(i);
            int access$500 = ChatAttachAlert.this.photoAttachAdapter.addToSelectedPhotos(photoEntry, -1);
            if (access$500 == -1) {
                z = true;
                indexOf = ChatAttachAlert.this.photoAttachAdapter.selectedPhotosOrder.indexOf(Integer.valueOf(photoEntry.imageId));
            } else {
                photoEntry.editedInfo = null;
                indexOf = access$500;
                z = false;
            }
            photoEntry.editedInfo = videoEditedInfo;
            int childCount = ChatAttachAlert.this.attachPhotoRecyclerView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = ChatAttachAlert.this.attachPhotoRecyclerView.getChildAt(i2);
                if ((childAt instanceof PhotoAttachPhotoCell) && ((Integer) childAt.getTag()).intValue() == i) {
                    ((PhotoAttachPhotoCell) childAt).setChecked(indexOf, z, false);
                    break;
                }
            }
            ChatAttachAlert.this.updatePhotosButton();
            return indexOf;
        }

        public void updatePhotoAtIndex(int i) {
            PhotoAttachPhotoCell access$000 = ChatAttachAlert.this.getCellForIndex(i);
            if (access$000 != null) {
                access$000.getImageView().setOrientation(0, true);
                PhotoEntry photoEntry = (PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(i);
                if (photoEntry.thumbPath != null) {
                    access$000.getImageView().setImage(photoEntry.thumbPath, null, access$000.getContext().getResources().getDrawable(R.drawable.nophotos));
                } else if (photoEntry.path != null) {
                    access$000.getImageView().setOrientation(photoEntry.orientation, true);
                    if (photoEntry.isVideo) {
                        access$000.getImageView().setImage("vthumb://" + photoEntry.imageId + ":" + photoEntry.path, null, access$000.getContext().getResources().getDrawable(R.drawable.nophotos));
                    } else {
                        access$000.getImageView().setImage("thumb://" + photoEntry.imageId + ":" + photoEntry.path, null, access$000.getContext().getResources().getDrawable(R.drawable.nophotos));
                    }
                } else {
                    access$000.getImageView().setImageResource(R.drawable.nophotos);
                }
            }
        }

        public void willHidePhotoViewer() {
            int childCount = ChatAttachAlert.this.attachPhotoRecyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ChatAttachAlert.this.attachPhotoRecyclerView.getChildAt(i);
                if (childAt instanceof PhotoAttachPhotoCell) {
                    ((PhotoAttachPhotoCell) childAt).showCheck(true);
                }
            }
        }

        public void willSwitchFromPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            PhotoAttachPhotoCell access$000 = ChatAttachAlert.this.getCellForIndex(i);
            if (access$000 != null) {
                access$000.showCheck(true);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatAttachAlert$3 */
    class C43663 extends ItemDecoration {
        C43663() {
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            rect.left = 0;
            rect.right = 0;
            rect.top = 0;
            rect.bottom = 0;
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatAttachAlert$4 */
    class C43674 extends OnScrollListener {
        C43674() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
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
    class C43707 implements OnItemClickListener {
        C43707() {
        }

        public void onItemClick(View view, int i) {
            if (ChatAttachAlert.this.baseFragment != null && ChatAttachAlert.this.baseFragment.getParentActivity() != null) {
                if (ChatAttachAlert.this.deviceHasGoodCamera && i == 0) {
                    ChatAttachAlert.this.openCamera();
                    return;
                }
                int i2 = ChatAttachAlert.this.deviceHasGoodCamera ? i - 1 : i;
                if (MediaController.allMediaAlbumEntry != null) {
                    ArrayList arrayList = MediaController.allMediaAlbumEntry.photos;
                    if (i2 >= 0 && i2 < arrayList.size()) {
                        PhotoViewer.getInstance().setParentActivity(ChatAttachAlert.this.baseFragment.getParentActivity());
                        PhotoViewer.getInstance().setParentAlert(ChatAttachAlert.this);
                        PhotoViewer.getInstance().openPhotoForSelect(arrayList, i2, 0, ChatAttachAlert.this.photoViewerProvider, ChatAttachAlert.this.baseFragment);
                        AndroidUtilities.hideKeyboard(ChatAttachAlert.this.baseFragment.getFragmentView().findFocus());
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatAttachAlert$8 */
    class C43718 extends OnScrollListener {
        C43718() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
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
        class C43731 implements OnClickListener {
            C43731() {
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
                    MotionEvent obtain = MotionEvent.obtain(0, 0, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
                    AttachBotButton.this.onTouchEvent(obtain);
                    obtain.recycle();
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

        public AttachBotButton(Context context) {
            super(context);
            this.imageView = new BackupImageView(context);
            this.imageView.setRoundRadius(AndroidUtilities.dp(27.0f));
            addView(this.imageView, LayoutHelper.createFrame(54, 54.0f, 49, BitmapDescriptorFactory.HUE_RED, 7.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.nameTextView = new TextView(context);
            this.nameTextView.setTextColor(Theme.getColor(Theme.key_dialogTextGray2));
            this.nameTextView.setTextSize(1, 12.0f);
            this.nameTextView.setMaxLines(2);
            this.nameTextView.setGravity(49);
            this.nameTextView.setLines(2);
            this.nameTextView.setEllipsize(TruncateAt.END);
            addView(this.nameTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 6.0f, 65.0f, 6.0f, BitmapDescriptorFactory.HUE_RED));
        }

        static /* synthetic */ int access$1004(AttachBotButton attachBotButton) {
            int i = attachBotButton.pressCount + 1;
            attachBotButton.pressCount = i;
            return i;
        }

        private void onLongPress() {
            if (ChatAttachAlert.this.baseFragment != null && this.currentUser != null) {
                Builder builder = new Builder(getContext());
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setMessage(LocaleController.formatString("ChatHintsDelete", R.string.ChatHintsDelete, new Object[]{ContactsController.formatName(this.currentUser.first_name, this.currentUser.last_name)}));
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C43731());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                builder.show();
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

        protected void onMeasure(int i, int i2) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(85.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(100.0f), 1073741824));
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            boolean z;
            if (motionEvent.getAction() == 0) {
                this.pressed = true;
                invalidate();
                z = true;
            } else {
                if (this.pressed) {
                    if (motionEvent.getAction() == 1) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        this.pressed = false;
                        playSoundEffect(0);
                        ChatAttachAlert.this.delegate.didSelectBot(MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_topPeer) SearchQuery.inlineBots.get(((Integer) getTag()).intValue())).peer.user_id)));
                        ChatAttachAlert.this.setUseRevealAnimation(false);
                        ChatAttachAlert.this.dismiss();
                        ChatAttachAlert.this.setUseRevealAnimation(true);
                        invalidate();
                        z = false;
                    } else if (motionEvent.getAction() == 3) {
                        this.pressed = false;
                        invalidate();
                    }
                }
                z = false;
            }
            if (!z) {
                z = super.onTouchEvent(motionEvent);
            } else if (motionEvent.getAction() == 0) {
                startCheckLongPress();
            }
            if (!(motionEvent.getAction() == 0 || motionEvent.getAction() == 2)) {
                cancelCheckLongPress();
            }
            return z;
        }

        public void setUser(User user) {
            if (user != null) {
                this.currentUser = user;
                TLObject tLObject = null;
                this.nameTextView.setText(ContactsController.formatName(user.first_name, user.last_name));
                this.avatarDrawable.setInfo(user);
                if (!(user == null || user.photo == null)) {
                    tLObject = user.photo.photo_small;
                }
                this.imageView.setImage(tLObject, "50_50", this.avatarDrawable);
                requestLayout();
            }
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
            addView(this.textView, LayoutHelper.createFrame(-1, -2.0f, 51, BitmapDescriptorFactory.HUE_RED, 64.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        }

        public boolean hasOverlappingRendering() {
            return false;
        }

        protected void onMeasure(int i, int i2) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(85.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(90.0f), 1073741824));
        }

        public void setTextAndIcon(CharSequence charSequence, Drawable drawable) {
            this.textView.setText(charSequence);
            this.imageView.setBackgroundDrawable(drawable);
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

        public int getItemCount() {
            return (!SearchQuery.inlineBots.isEmpty() ? ((int) Math.ceil((double) (((float) SearchQuery.inlineBots.size()) / 4.0f))) + 1 : 0) + 1;
        }

        public int getItemViewType(int i) {
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                default:
                    return 2;
            }
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return false;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (i > 1) {
                int i2 = (i - 2) * 4;
                FrameLayout frameLayout = (FrameLayout) viewHolder.itemView;
                for (int i3 = 0; i3 < 4; i3++) {
                    AttachBotButton attachBotButton = (AttachBotButton) frameLayout.getChildAt(i3);
                    if (i2 + i3 >= SearchQuery.inlineBots.size()) {
                        attachBotButton.setVisibility(4);
                    } else {
                        attachBotButton.setVisibility(0);
                        attachBotButton.setTag(Integer.valueOf(i2 + i3));
                        attachBotButton.setUser(MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_topPeer) SearchQuery.inlineBots.get(i2 + i3)).peer.user_id)));
                    }
                }
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View access$7300;
            switch (i) {
                case 0:
                    access$7300 = ChatAttachAlert.this.attachView;
                    break;
                case 1:
                    access$7300 = new FrameLayout(this.mContext);
                    access$7300.setBackgroundColor(-986896);
                    access$7300.addView(new ShadowSectionCell(this.mContext), LayoutHelper.createFrame(-1, -1.0f));
                    break;
                default:
                    access$7300 = new FrameLayout(this.mContext) {
                        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                            int dp = ((i3 - i) - AndroidUtilities.dp(360.0f)) / 3;
                            for (int i5 = 0; i5 < 4; i5++) {
                                int dp2 = AndroidUtilities.dp(10.0f) + ((i5 % 4) * (AndroidUtilities.dp(85.0f) + dp));
                                View childAt = getChildAt(i5);
                                childAt.layout(dp2, 0, childAt.getMeasuredWidth() + dp2, childAt.getMeasuredHeight());
                            }
                        }
                    };
                    for (int i2 = 0; i2 < 4; i2++) {
                        access$7300.addView(new AttachBotButton(this.mContext));
                    }
                    access$7300.setLayoutParams(new LayoutParams(-1, AndroidUtilities.dp(100.0f)));
                    break;
            }
            return new Holder(access$7300);
        }
    }

    private class PhotoAttachAdapter extends SelectionAdapter {
        private Context mContext;
        private HashMap<Object, Object> selectedPhotos = new HashMap();
        private ArrayList<Object> selectedPhotosOrder = new ArrayList();

        /* renamed from: org.telegram.ui.Components.ChatAttachAlert$PhotoAttachAdapter$1 */
        class C43751 implements PhotoAttachPhotoCellDelegate {
            C43751() {
            }

            public void onCheckClick(PhotoAttachPhotoCell photoAttachPhotoCell) {
                if (ChatAttachAlert.this.mediaEnabled) {
                    int intValue = ((Integer) photoAttachPhotoCell.getTag()).intValue();
                    PhotoEntry photoEntry = photoAttachPhotoCell.getPhotoEntry();
                    boolean z = !PhotoAttachAdapter.this.selectedPhotos.containsKey(Integer.valueOf(photoEntry.imageId));
                    photoAttachPhotoCell.setChecked(z ? PhotoAttachAdapter.this.selectedPhotosOrder.size() : -1, z, true);
                    PhotoAttachAdapter.this.addToSelectedPhotos(photoEntry, intValue);
                    ChatAttachAlert.this.updatePhotosButton();
                }
            }
        }

        public PhotoAttachAdapter(Context context) {
            this.mContext = context;
        }

        private int addToSelectedPhotos(PhotoEntry photoEntry, int i) {
            if (this.selectedPhotos.containsKey(Integer.valueOf(photoEntry.imageId))) {
                this.selectedPhotos.remove(Integer.valueOf(photoEntry.imageId));
                int i2 = 0;
                while (i2 < this.selectedPhotosOrder.size()) {
                    if (this.selectedPhotosOrder.get(i2).equals(Integer.valueOf(photoEntry.imageId))) {
                        this.selectedPhotosOrder.remove(i2);
                        break;
                    }
                    i2++;
                }
                i2 = 0;
                ChatAttachAlert.this.updateCheckedPhotoIndices();
                if (i < 0) {
                    return i2;
                }
                photoEntry.reset();
                ChatAttachAlert.this.photoViewerProvider.updatePhotoAtIndex(i);
                return i2;
            }
            this.selectedPhotos.put(Integer.valueOf(photoEntry.imageId), photoEntry);
            this.selectedPhotosOrder.add(Integer.valueOf(photoEntry.imageId));
            return -1;
        }

        public void clearSelectedPhotos() {
            if (!this.selectedPhotos.isEmpty()) {
                for (Entry value : this.selectedPhotos.entrySet()) {
                    ((PhotoEntry) value.getValue()).reset();
                }
                this.selectedPhotos.clear();
                this.selectedPhotosOrder.clear();
                ChatAttachAlert.this.updatePhotosButton();
                notifyDataSetChanged();
            }
        }

        public Holder createHolder() {
            View photoAttachPhotoCell = new PhotoAttachPhotoCell(this.mContext);
            photoAttachPhotoCell.setDelegate(new C43751());
            return new Holder(photoAttachPhotoCell);
        }

        public int getItemCount() {
            int i = 0;
            if (ChatAttachAlert.this.deviceHasGoodCamera) {
                i = 1;
            }
            return MediaController.allMediaAlbumEntry != null ? i + MediaController.allMediaAlbumEntry.photos.size() : i;
        }

        public int getItemViewType(int i) {
            return (ChatAttachAlert.this.deviceHasGoodCamera && i == 0) ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return false;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (!ChatAttachAlert.this.deviceHasGoodCamera || i != 0) {
                if (ChatAttachAlert.this.deviceHasGoodCamera) {
                    i--;
                }
                PhotoAttachPhotoCell photoAttachPhotoCell = (PhotoAttachPhotoCell) viewHolder.itemView;
                PhotoEntry photoEntry = (PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(i);
                photoAttachPhotoCell.setPhotoEntry(photoEntry, i == MediaController.allMediaAlbumEntry.photos.size() + -1);
                photoAttachPhotoCell.setChecked(this.selectedPhotosOrder.indexOf(Integer.valueOf(photoEntry.imageId)), this.selectedPhotos.containsKey(Integer.valueOf(photoEntry.imageId)), false);
                photoAttachPhotoCell.getImageView().setTag(Integer.valueOf(i));
                photoAttachPhotoCell.setTag(Integer.valueOf(i));
            } else if (!ChatAttachAlert.this.deviceHasGoodCamera || i != 0) {
            } else {
                if (ChatAttachAlert.this.cameraView == null || !ChatAttachAlert.this.cameraView.isInitied()) {
                    viewHolder.itemView.setVisibility(0);
                } else {
                    viewHolder.itemView.setVisibility(4);
                }
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            switch (i) {
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
    }

    public ChatAttachAlert(Context context, final ChatActivity chatActivity) {
        int i;
        super(context, false);
        this.baseFragment = chatActivity;
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
        ViewGroup c43652 = new RecyclerListView(context) {
            private int lastHeight;
            private int lastWidth;

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

            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (ChatAttachAlert.this.cameraAnimationInProgress) {
                    return true;
                }
                if (ChatAttachAlert.this.cameraOpened) {
                    return ChatAttachAlert.this.processTouchEvent(motionEvent);
                }
                if (motionEvent.getAction() != 0 || ChatAttachAlert.this.scrollOffsetY == 0 || motionEvent.getY() >= ((float) ChatAttachAlert.this.scrollOffsetY)) {
                    return super.onInterceptTouchEvent(motionEvent);
                }
                ChatAttachAlert.this.dismiss();
                return true;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int adapterPosition;
                int top;
                int i5 = i3 - i;
                int i6 = i4 - i2;
                if (ChatAttachAlert.this.listView.getChildCount() > 0) {
                    View childAt = ChatAttachAlert.this.listView.getChildAt(ChatAttachAlert.this.listView.getChildCount() - 1);
                    Holder holder = (Holder) ChatAttachAlert.this.listView.findContainingViewHolder(childAt);
                    if (holder != null) {
                        adapterPosition = holder.getAdapterPosition();
                        top = childAt.getTop();
                        if (adapterPosition >= 0 || i6 - this.lastHeight == 0) {
                            top = 0;
                            adapterPosition = -1;
                        } else {
                            top = ((top + i6) - this.lastHeight) - getPaddingTop();
                        }
                        super.onLayout(z, i, i2, i3, i4);
                        if (adapterPosition != -1) {
                            ChatAttachAlert.this.ignoreLayout = true;
                            ChatAttachAlert.this.layoutManager.scrollToPositionWithOffset(adapterPosition, top);
                            super.onLayout(false, i, i2, i3, i4);
                            ChatAttachAlert.this.ignoreLayout = false;
                        }
                        this.lastHeight = i6;
                        this.lastWidth = i5;
                        ChatAttachAlert.this.updateLayout();
                        ChatAttachAlert.this.checkCameraViewPosition();
                    }
                }
                top = 0;
                adapterPosition = -1;
                if (adapterPosition >= 0) {
                }
                top = 0;
                adapterPosition = -1;
                super.onLayout(z, i, i2, i3, i4);
                if (adapterPosition != -1) {
                    ChatAttachAlert.this.ignoreLayout = true;
                    ChatAttachAlert.this.layoutManager.scrollToPositionWithOffset(adapterPosition, top);
                    super.onLayout(false, i, i2, i3, i4);
                    ChatAttachAlert.this.ignoreLayout = false;
                }
                this.lastHeight = i6;
                this.lastWidth = i5;
                ChatAttachAlert.this.updateLayout();
                ChatAttachAlert.this.checkCameraViewPosition();
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i2);
                if (VERSION.SDK_INT >= 21) {
                    size -= AndroidUtilities.statusBarHeight;
                }
                int dp = (AndroidUtilities.dp(294.0f) + ChatAttachAlert.backgroundPaddingTop) + (SearchQuery.inlineBots.isEmpty() ? 0 : (((int) Math.ceil((double) (((float) SearchQuery.inlineBots.size()) / 4.0f))) * AndroidUtilities.dp(100.0f)) + AndroidUtilities.dp(12.0f));
                int max = dp == AndroidUtilities.dp(294.0f) ? 0 : Math.max(0, size - AndroidUtilities.dp(294.0f));
                if (max != 0 && dp < size) {
                    max -= size - dp;
                }
                if (max == 0) {
                    max = ChatAttachAlert.backgroundPaddingTop;
                }
                if (getPaddingTop() != max) {
                    ChatAttachAlert.this.ignoreLayout = true;
                    setPadding(ChatAttachAlert.backgroundPaddingLeft, max, ChatAttachAlert.backgroundPaddingLeft, 0);
                    ChatAttachAlert.this.ignoreLayout = false;
                }
                super.onMeasure(i, MeasureSpec.makeMeasureSpec(Math.min(dp, size), 1073741824));
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                return ChatAttachAlert.this.cameraAnimationInProgress ? true : ChatAttachAlert.this.cameraOpened ? ChatAttachAlert.this.processTouchEvent(motionEvent) : !ChatAttachAlert.this.isDismissed() && super.onTouchEvent(motionEvent);
            }

            public void requestLayout() {
                if (!ChatAttachAlert.this.ignoreLayout) {
                    super.requestLayout();
                }
            }

            public void setTranslationY(float f) {
                super.setTranslationY(f);
                ChatAttachAlert.this.checkCameraViewPosition();
            }
        };
        this.listView = c43652;
        this.containerView = c43652;
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
        this.listView.addItemDecoration(new C43663());
        this.listView.setOnScrollListener(new C43674());
        this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        this.attachView = new FrameLayout(context) {
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int i5 = 0;
                int i6 = i3 - i;
                int i7 = i4 - i2;
                int dp = AndroidUtilities.dp(8.0f);
                ChatAttachAlert.this.attachPhotoRecyclerView.layout(0, dp, i6, ChatAttachAlert.this.attachPhotoRecyclerView.getMeasuredHeight() + dp);
                ChatAttachAlert.this.progressView.layout(0, dp, i6, ChatAttachAlert.this.progressView.getMeasuredHeight() + dp);
                ChatAttachAlert.this.lineView.layout(0, AndroidUtilities.dp(96.0f), i6, AndroidUtilities.dp(96.0f) + ChatAttachAlert.this.lineView.getMeasuredHeight());
                ChatAttachAlert.this.hintTextView.layout((i6 - ChatAttachAlert.this.hintTextView.getMeasuredWidth()) - AndroidUtilities.dp(5.0f), (i7 - ChatAttachAlert.this.hintTextView.getMeasuredHeight()) - AndroidUtilities.dp(5.0f), i6 - AndroidUtilities.dp(5.0f), i7 - AndroidUtilities.dp(5.0f));
                i7 = (i6 - ChatAttachAlert.this.mediaBanTooltip.getMeasuredWidth()) / 2;
                dp += (ChatAttachAlert.this.attachPhotoRecyclerView.getMeasuredHeight() - ChatAttachAlert.this.mediaBanTooltip.getMeasuredHeight()) / 2;
                ChatAttachAlert.this.mediaBanTooltip.layout(i7, dp, ChatAttachAlert.this.mediaBanTooltip.getMeasuredWidth() + i7, ChatAttachAlert.this.mediaBanTooltip.getMeasuredHeight() + dp);
                i6 = (i6 - AndroidUtilities.dp(360.0f)) / 3;
                while (i5 < 8) {
                    i7 = AndroidUtilities.dp((float) (((i5 / 4) * 95) + 105));
                    dp = AndroidUtilities.dp(10.0f) + ((i5 % 4) * (AndroidUtilities.dp(85.0f) + i6));
                    ChatAttachAlert.this.views[i5].layout(dp, i7, ChatAttachAlert.this.views[i5].getMeasuredWidth() + dp, ChatAttachAlert.this.views[i5].getMeasuredHeight() + i7);
                    i5++;
                }
            }

            protected void onMeasure(int i, int i2) {
                super.onMeasure(i, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(294.0f), 1073741824));
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
        this.attachPhotoRecyclerView.setOnItemClickListener(new C43707());
        this.attachPhotoRecyclerView.setOnScrollListener(new C43718());
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
        this.attachView.addView(this.mediaBanTooltip, LayoutHelper.createFrame(-2, -2.0f, 51, 14.0f, BitmapDescriptorFactory.HUE_RED, 14.0f, BitmapDescriptorFactory.HUE_RED));
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
        View c43729 = new View(getContext()) {
            public boolean hasOverlappingRendering() {
                return false;
            }
        };
        this.lineView = c43729;
        viewArr[10] = c43729;
        this.lineView.setBackgroundColor(Theme.getColor(Theme.key_dialogGrayLine));
        this.attachView.addView(this.lineView, new FrameLayout.LayoutParams(-1, 1, 51));
        CharSequence[] charSequenceArr = new CharSequence[]{LocaleController.getString("ChatCamera", R.string.ChatCamera), LocaleController.getString("ChatGallery", R.string.ChatGallery), LocaleController.getString("ChatVideo", R.string.ChatVideo), LocaleController.getString("AttachMusic", R.string.AttachMusic), LocaleController.getString("ChatDocument", R.string.ChatDocument), LocaleController.getString("AttachContact", R.string.AttachContact), LocaleController.getString("ChatLocation", R.string.ChatLocation), TtmlNode.ANONYMOUS_REGION_ID};
        for (i = 0; i < 8; i++) {
            c43729 = new AttachButton(context);
            this.attachButtons.add(c43729);
            c43729.setTextAndIcon(charSequenceArr[i], Theme.chat_attachButtonDrawables[i]);
            this.attachView.addView(c43729, LayoutHelper.createFrame(85, 90, 51));
            c43729.setTag(Integer.valueOf(i));
            this.views[i] = c43729;
            if (i == 7) {
                this.sendPhotosButton = c43729;
                this.sendPhotosButton.imageView.setPadding(0, AndroidUtilities.dp(4.0f), 0, 0);
            } else if (i == 4) {
                this.sendDocumentsButton = c43729;
            }
            c43729.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ChatAttachAlert.this.delegate.didPressedButton(((Integer) view.getTag()).intValue());
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
        this.attachView.addView(this.hintTextView, LayoutHelper.createFrame(-2, 32.0f, 85, 5.0f, BitmapDescriptorFactory.HUE_RED, 5.0f, 5.0f));
        for (i = 0; i < 8; i++) {
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
        this.recordTime.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.recordTime.setTextColor(-1);
        this.recordTime.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(5.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(5.0f));
        this.container.addView(this.recordTime, LayoutHelper.createFrame(-2, -2.0f, 49, BitmapDescriptorFactory.HUE_RED, 16.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.cameraPanel = new FrameLayout(context) {
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int measuredWidth;
                int i5;
                int measuredWidth2 = getMeasuredWidth() / 2;
                int measuredHeight = getMeasuredHeight() / 2;
                ChatAttachAlert.this.shutterButton.layout(measuredWidth2 - (ChatAttachAlert.this.shutterButton.getMeasuredWidth() / 2), measuredHeight - (ChatAttachAlert.this.shutterButton.getMeasuredHeight() / 2), (ChatAttachAlert.this.shutterButton.getMeasuredWidth() / 2) + measuredWidth2, (ChatAttachAlert.this.shutterButton.getMeasuredHeight() / 2) + measuredHeight);
                if (getMeasuredWidth() == AndroidUtilities.dp(100.0f)) {
                    measuredWidth = getMeasuredWidth() / 2;
                    measuredWidth2 = ((measuredHeight / 2) + measuredHeight) + AndroidUtilities.dp(17.0f);
                    measuredHeight = (measuredHeight / 2) - AndroidUtilities.dp(17.0f);
                    i5 = measuredWidth;
                } else {
                    measuredWidth = ((measuredWidth2 / 2) + measuredWidth2) + AndroidUtilities.dp(17.0f);
                    measuredHeight = (measuredWidth2 / 2) - AndroidUtilities.dp(17.0f);
                    measuredWidth2 = getMeasuredHeight() / 2;
                    i5 = measuredHeight;
                    measuredHeight = measuredWidth2;
                }
                ChatAttachAlert.this.switchCameraButton.layout(measuredWidth - (ChatAttachAlert.this.switchCameraButton.getMeasuredWidth() / 2), measuredWidth2 - (ChatAttachAlert.this.switchCameraButton.getMeasuredHeight() / 2), measuredWidth + (ChatAttachAlert.this.switchCameraButton.getMeasuredWidth() / 2), measuredWidth2 + (ChatAttachAlert.this.switchCameraButton.getMeasuredHeight() / 2));
                for (measuredWidth2 = 0; measuredWidth2 < 2; measuredWidth2++) {
                    ChatAttachAlert.this.flashModeButton[measuredWidth2].layout(i5 - (ChatAttachAlert.this.flashModeButton[measuredWidth2].getMeasuredWidth() / 2), measuredHeight - (ChatAttachAlert.this.flashModeButton[measuredWidth2].getMeasuredHeight() / 2), (ChatAttachAlert.this.flashModeButton[measuredWidth2].getMeasuredWidth() / 2) + i5, (ChatAttachAlert.this.flashModeButton[measuredWidth2].getMeasuredHeight() / 2) + measuredHeight);
                }
            }
        };
        this.cameraPanel.setVisibility(8);
        this.cameraPanel.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.container.addView(this.cameraPanel, LayoutHelper.createFrame(-1, 100, 83));
        this.shutterButton = new ShutterButton(context);
        this.cameraPanel.addView(this.shutterButton, LayoutHelper.createFrame(84, 84, 17));
        this.shutterButton.setDelegate(new ShutterButtonDelegate() {

            /* renamed from: org.telegram.ui.Components.ChatAttachAlert$12$1 */
            class C43531 implements Runnable {
                C43531() {
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
            class C43562 implements VideoTakeCallback {
                C43562() {
                }

                public void onFinishVideoRecording(final Bitmap bitmap) {
                    if (ChatAttachAlert.this.cameraFile != null && ChatAttachAlert.this.baseFragment != null) {
                        PhotoViewer.getInstance().setParentActivity(ChatAttachAlert.this.baseFragment.getParentActivity());
                        PhotoViewer.getInstance().setParentAlert(ChatAttachAlert.this);
                        ChatAttachAlert.this.cameraPhoto = new ArrayList();
                        ChatAttachAlert.this.cameraPhoto.add(new PhotoEntry(0, 0, 0, ChatAttachAlert.this.cameraFile.getAbsolutePath(), 0, true));
                        PhotoViewer.getInstance().openPhotoForSelect(ChatAttachAlert.this.cameraPhoto, 0, 2, new EmptyPhotoViewerProvider() {

                            /* renamed from: org.telegram.ui.Components.ChatAttachAlert$12$2$1$1 */
                            class C43541 implements Runnable {
                                C43541() {
                                }

                                public void run() {
                                    if (ChatAttachAlert.this.cameraView != null && !ChatAttachAlert.this.isDismissed() && VERSION.SDK_INT >= 21) {
                                        ChatAttachAlert.this.cameraView.setSystemUiVisibility(1028);
                                    }
                                }
                            }

                            public boolean canScrollAway() {
                                return false;
                            }

                            public boolean cancelButtonPressed() {
                                if (!(!ChatAttachAlert.this.cameraOpened || ChatAttachAlert.this.cameraView == null || ChatAttachAlert.this.cameraFile == null)) {
                                    ChatAttachAlert.this.cameraFile.delete();
                                    AndroidUtilities.runOnUIThread(new C43541(), 1000);
                                    CameraController.getInstance().startPreview(ChatAttachAlert.this.cameraView.getCameraSession());
                                    ChatAttachAlert.this.cameraFile = null;
                                }
                                return true;
                            }

                            public Bitmap getThumbForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
                                return bitmap;
                            }

                            public void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo) {
                                if (ChatAttachAlert.this.cameraFile != null && ChatAttachAlert.this.baseFragment != null) {
                                    AndroidUtilities.addMediaToGallery(ChatAttachAlert.this.cameraFile.getAbsolutePath());
                                    ChatAttachAlert.this.baseFragment.sendMedia((PhotoEntry) ChatAttachAlert.this.cameraPhoto.get(0), videoEditedInfo);
                                    ChatAttachAlert.this.closeCamera(false);
                                    ChatAttachAlert.this.dismiss();
                                    ChatAttachAlert.this.cameraFile = null;
                                }
                            }

                            public void willHidePhotoViewer() {
                                ChatAttachAlert.this.mediaCaptured = false;
                            }
                        }, ChatAttachAlert.this.baseFragment);
                    }
                }
            }

            /* renamed from: org.telegram.ui.Components.ChatAttachAlert$12$3 */
            class C43573 implements Runnable {
                C43573() {
                }

                public void run() {
                    AndroidUtilities.runOnUIThread(ChatAttachAlert.this.videoRecordRunnable, 1000);
                }
            }

            public void shutterCancel() {
                if (!ChatAttachAlert.this.mediaCaptured) {
                    ChatAttachAlert.this.cameraFile.delete();
                    ChatAttachAlert.this.resetRecordState();
                    CameraController.getInstance().stopVideoRecording(ChatAttachAlert.this.cameraView.getCameraSession(), true);
                }
            }

            public boolean shutterLongPressed() {
                if (ChatAttachAlert.this.mediaCaptured || ChatAttachAlert.this.takingPhoto || ChatAttachAlert.this.baseFragment == null || ChatAttachAlert.this.baseFragment.getParentActivity() == null || ChatAttachAlert.this.cameraView == null) {
                    return false;
                }
                if (VERSION.SDK_INT < 23 || ChatAttachAlert.this.baseFragment.getParentActivity().checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
                    for (int i = 0; i < 2; i++) {
                        ChatAttachAlert.this.flashModeButton[i].setAlpha(BitmapDescriptorFactory.HUE_RED);
                    }
                    ChatAttachAlert.this.switchCameraButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    ChatAttachAlert.this.cameraFile = AndroidUtilities.generateVideoPath();
                    ChatAttachAlert.this.recordTime.setAlpha(1.0f);
                    ChatAttachAlert.this.recordTime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(0), Integer.valueOf(0)}));
                    ChatAttachAlert.this.videoRecordTime = 0;
                    ChatAttachAlert.this.videoRecordRunnable = new C43531();
                    AndroidUtilities.lockOrientation(chatActivity.getParentActivity());
                    CameraController.getInstance().recordVideo(ChatAttachAlert.this.cameraView.getCameraSession(), ChatAttachAlert.this.cameraFile, new C43562(), new C43573(), false);
                    ChatAttachAlert.this.shutterButton.setState(ShutterButton.State.RECORDING, true);
                    return true;
                }
                ChatAttachAlert.this.requestingPermissions = true;
                ChatAttachAlert.this.baseFragment.getParentActivity().requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 21);
                return false;
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
                    final boolean isSameTakePictureOrientation = ChatAttachAlert.this.cameraView.getCameraSession().isSameTakePictureOrientation();
                    ChatAttachAlert.this.takingPhoto = CameraController.getInstance().takePicture(ChatAttachAlert.this.cameraFile, ChatAttachAlert.this.cameraView.getCameraSession(), new Runnable() {

                        /* renamed from: org.telegram.ui.Components.ChatAttachAlert$12$4$1 */
                        class C43591 extends EmptyPhotoViewerProvider {

                            /* renamed from: org.telegram.ui.Components.ChatAttachAlert$12$4$1$1 */
                            class C43581 implements Runnable {
                                C43581() {
                                }

                                public void run() {
                                    if (ChatAttachAlert.this.cameraView != null && !ChatAttachAlert.this.isDismissed() && VERSION.SDK_INT >= 21) {
                                        ChatAttachAlert.this.cameraView.setSystemUiVisibility(1028);
                                    }
                                }
                            }

                            C43591() {
                            }

                            public boolean canScrollAway() {
                                return false;
                            }

                            public boolean cancelButtonPressed() {
                                if (!(!ChatAttachAlert.this.cameraOpened || ChatAttachAlert.this.cameraView == null || ChatAttachAlert.this.cameraFile == null)) {
                                    ChatAttachAlert.this.cameraFile.delete();
                                    AndroidUtilities.runOnUIThread(new C43581(), 1000);
                                    CameraController.getInstance().startPreview(ChatAttachAlert.this.cameraView.getCameraSession());
                                    ChatAttachAlert.this.cameraFile = null;
                                }
                                return true;
                            }

                            public boolean scaleToFill() {
                                if (ChatAttachAlert.this.baseFragment == null || ChatAttachAlert.this.baseFragment.getParentActivity() == null) {
                                    return false;
                                }
                                return isSameTakePictureOrientation || System.getInt(ChatAttachAlert.this.baseFragment.getParentActivity().getContentResolver(), "accelerometer_rotation", 0) == 1;
                            }

                            public void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo) {
                                if (ChatAttachAlert.this.cameraFile != null && ChatAttachAlert.this.baseFragment != null) {
                                    AndroidUtilities.addMediaToGallery(ChatAttachAlert.this.cameraFile.getAbsolutePath());
                                    ChatAttachAlert.this.baseFragment.sendMedia((PhotoEntry) ChatAttachAlert.this.cameraPhoto.get(0), null);
                                    ChatAttachAlert.this.closeCamera(false);
                                    ChatAttachAlert.this.dismiss();
                                    ChatAttachAlert.this.cameraFile = null;
                                }
                            }

                            public void willHidePhotoViewer() {
                                ChatAttachAlert.this.mediaCaptured = false;
                            }
                        }

                        public void run() {
                            ChatAttachAlert.this.takingPhoto = false;
                            if (ChatAttachAlert.this.cameraFile != null && ChatAttachAlert.this.baseFragment != null) {
                                int i;
                                PhotoViewer.getInstance().setParentActivity(ChatAttachAlert.this.baseFragment.getParentActivity());
                                PhotoViewer.getInstance().setParentAlert(ChatAttachAlert.this);
                                ChatAttachAlert.this.cameraPhoto = new ArrayList();
                                try {
                                    boolean z;
                                    switch (new ExifInterface(ChatAttachAlert.this.cameraFile.getAbsolutePath()).getAttributeInt("Orientation", 1)) {
                                        case 3:
                                            z = true;
                                            break;
                                        case 6:
                                            z = true;
                                            break;
                                        case 8:
                                            z = true;
                                            break;
                                        default:
                                            z = false;
                                            break;
                                    }
                                    i = z;
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                    i = 0;
                                }
                                ChatAttachAlert.this.cameraPhoto.add(new PhotoEntry(0, 0, 0, ChatAttachAlert.this.cameraFile.getAbsolutePath(), i, false));
                                PhotoViewer.getInstance().openPhotoForSelect(ChatAttachAlert.this.cameraPhoto, 0, 2, new C43591(), ChatAttachAlert.this.baseFragment);
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
            class C43611 extends AnimatorListenerAdapter {
                C43611() {
                }

                public void onAnimationEnd(Animator animator) {
                    ChatAttachAlert.this.switchCameraButton.setImageResource(ChatAttachAlert.this.cameraView.isFrontface() ? R.drawable.camera_revert1 : R.drawable.camera_revert2);
                    ObjectAnimator.ofFloat(ChatAttachAlert.this.switchCameraButton, "scaleX", new float[]{1.0f}).setDuration(100).start();
                }
            }

            public void onClick(View view) {
                if (!ChatAttachAlert.this.takingPhoto && ChatAttachAlert.this.cameraView != null && ChatAttachAlert.this.cameraView.isInitied()) {
                    ChatAttachAlert.this.cameraInitied = false;
                    ChatAttachAlert.this.cameraView.switchCamera();
                    ObjectAnimator duration = ObjectAnimator.ofFloat(ChatAttachAlert.this.switchCameraButton, "scaleX", new float[]{BitmapDescriptorFactory.HUE_RED}).setDuration(100);
                    duration.addListener(new C43611());
                    duration.start();
                }
            }
        });
        for (i = 0; i < 2; i++) {
            this.flashModeButton[i] = new ImageView(context);
            this.flashModeButton[i].setScaleType(ScaleType.CENTER);
            this.flashModeButton[i].setVisibility(4);
            this.cameraPanel.addView(this.flashModeButton[i], LayoutHelper.createFrame(48, 48, 51));
            this.flashModeButton[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(final View view) {
                    if (!ChatAttachAlert.this.flashAnimationInProgress && ChatAttachAlert.this.cameraView != null && ChatAttachAlert.this.cameraView.isInitied() && ChatAttachAlert.this.cameraOpened) {
                        String currentFlashMode = ChatAttachAlert.this.cameraView.getCameraSession().getCurrentFlashMode();
                        String nextFlashMode = ChatAttachAlert.this.cameraView.getCameraSession().getNextFlashMode();
                        if (!currentFlashMode.equals(nextFlashMode)) {
                            ChatAttachAlert.this.cameraView.getCameraSession().setCurrentFlashMode(nextFlashMode);
                            ChatAttachAlert.this.flashAnimationInProgress = true;
                            ImageView imageView = ChatAttachAlert.this.flashModeButton[0] == view ? ChatAttachAlert.this.flashModeButton[1] : ChatAttachAlert.this.flashModeButton[0];
                            imageView.setVisibility(0);
                            ChatAttachAlert.this.setCameraFlashModeIcon(imageView, nextFlashMode);
                            AnimatorSet animatorSet = new AnimatorSet();
                            r2 = new Animator[4];
                            r2[0] = ObjectAnimator.ofFloat(view, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(48.0f)});
                            r2[1] = ObjectAnimator.ofFloat(imageView, "translationY", new float[]{(float) (-AndroidUtilities.dp(48.0f)), BitmapDescriptorFactory.HUE_RED});
                            r2[2] = ObjectAnimator.ofFloat(view, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED});
                            r2[3] = ObjectAnimator.ofFloat(imageView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                            animatorSet.playTogether(r2);
                            animatorSet.setDuration(200);
                            animatorSet.addListener(new AnimatorListenerAdapter() {
                                public void onAnimationEnd(Animator animator) {
                                    ChatAttachAlert.this.flashAnimationInProgress = false;
                                    view.setVisibility(4);
                                }
                            });
                            animatorSet.start();
                        }
                    }
                }
            });
        }
    }

    private void applyCameraViewPosition() {
        if (this.cameraView != null) {
            final FrameLayout.LayoutParams layoutParams;
            if (!this.cameraOpened) {
                this.cameraView.setTranslationX((float) this.cameraViewLocation[0]);
                this.cameraView.setTranslationY((float) this.cameraViewLocation[1]);
            }
            this.cameraIcon.setTranslationX((float) this.cameraViewLocation[0]);
            this.cameraIcon.setTranslationY((float) this.cameraViewLocation[1]);
            int dp = AndroidUtilities.dp(80.0f) - this.cameraViewOffsetX;
            int dp2 = AndroidUtilities.dp(80.0f) - this.cameraViewOffsetY;
            if (!this.cameraOpened) {
                this.cameraView.setClipLeft(this.cameraViewOffsetX);
                this.cameraView.setClipTop(this.cameraViewOffsetY);
                layoutParams = (FrameLayout.LayoutParams) this.cameraView.getLayoutParams();
                if (!(layoutParams.height == dp2 && layoutParams.width == dp)) {
                    layoutParams.width = dp;
                    layoutParams.height = dp2;
                    this.cameraView.setLayoutParams(layoutParams);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (ChatAttachAlert.this.cameraView != null) {
                                ChatAttachAlert.this.cameraView.setLayoutParams(layoutParams);
                            }
                        }
                    });
                }
            }
            layoutParams = (FrameLayout.LayoutParams) this.cameraIcon.getLayoutParams();
            if (layoutParams.height != dp2 || layoutParams.width != dp) {
                layoutParams.width = dp;
                layoutParams.height = dp2;
                this.cameraIcon.setLayoutParams(layoutParams);
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (ChatAttachAlert.this.cameraIcon != null) {
                            ChatAttachAlert.this.cameraIcon.setLayoutParams(layoutParams);
                        }
                    }
                });
            }
        }
    }

    private void checkCameraViewPosition() {
        if (this.deviceHasGoodCamera) {
            int childCount = this.attachPhotoRecyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.attachPhotoRecyclerView.getChildAt(i);
                if (childAt instanceof PhotoAttachCameraCell) {
                    if (VERSION.SDK_INT < 19 || childAt.isAttachedToWindow()) {
                        childAt.getLocationInWindow(this.cameraViewLocation);
                        int[] iArr = this.cameraViewLocation;
                        iArr[0] = iArr[0] - getLeftInset();
                        float x = (this.listView.getX() + ((float) backgroundPaddingLeft)) - ((float) getLeftInset());
                        if (((float) this.cameraViewLocation[0]) < x) {
                            this.cameraViewOffsetX = (int) (x - ((float) this.cameraViewLocation[0]));
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

    private PhotoAttachPhotoCell getCellForIndex(int i) {
        if (MediaController.allMediaAlbumEntry == null) {
            return null;
        }
        int childCount = this.attachPhotoRecyclerView.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = this.attachPhotoRecyclerView.getChildAt(i2);
            if (childAt instanceof PhotoAttachPhotoCell) {
                PhotoAttachPhotoCell photoAttachPhotoCell = (PhotoAttachPhotoCell) childAt;
                int intValue = ((Integer) photoAttachPhotoCell.getImageView().getTag()).intValue();
                if (intValue >= 0 && intValue < MediaController.allMediaAlbumEntry.photos.size() && intValue == i) {
                    return photoAttachPhotoCell;
                }
            }
        }
        return null;
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
            animatorArr[0] = ObjectAnimator.ofFloat(this.hintTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorSet.playTogether(animatorArr);
            this.currentHintAnimation.setInterpolator(this.decelerateInterpolator);
            this.currentHintAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    if (ChatAttachAlert.this.currentHintAnimation != null && ChatAttachAlert.this.currentHintAnimation.equals(animator)) {
                        ChatAttachAlert.this.currentHintAnimation = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (ChatAttachAlert.this.currentHintAnimation != null && ChatAttachAlert.this.currentHintAnimation.equals(animator)) {
                        ChatAttachAlert.this.currentHintAnimation = null;
                        if (ChatAttachAlert.this.hintTextView != null) {
                            ChatAttachAlert.this.hintTextView.setVisibility(4);
                        }
                    }
                }
            });
            this.currentHintAnimation.setDuration(300);
            this.currentHintAnimation.start();
        }
    }

    private void onRevealAnimationEnd(boolean z) {
        NotificationCenter.getInstance().setAnimationInProgress(false);
        this.revealAnimationInProgress = false;
        if (z && VERSION.SDK_INT <= 19 && MediaController.allMediaAlbumEntry == null) {
            MediaController.loadGalleryPhotosAlbums(0);
        }
        if (z) {
            checkCamera(true);
            showHint();
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
            Collection arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofFloat(this, "cameraOpenProgress", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}));
            arrayList.add(ObjectAnimator.ofFloat(this.cameraPanel, "alpha", new float[]{1.0f}));
            for (int i = 0; i < 2; i++) {
                if (this.flashModeButton[i].getVisibility() == 0) {
                    arrayList.add(ObjectAnimator.ofFloat(this.flashModeButton[i], "alpha", new float[]{1.0f}));
                    break;
                }
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
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

    private boolean processTouchEvent(MotionEvent motionEvent) {
        if ((this.pressed || motionEvent.getActionMasked() != 0) && motionEvent.getActionMasked() != 5) {
            if (this.pressed) {
                AnimatorSet animatorSet;
                Animator[] animatorArr;
                if (motionEvent.getActionMasked() == 2) {
                    float y = motionEvent.getY();
                    float f = y - this.lastY;
                    if (this.maybeStartDraging) {
                        if (Math.abs(f) > AndroidUtilities.getPixelsInCM(0.4f, false)) {
                            this.maybeStartDraging = false;
                            this.dragging = true;
                        }
                    } else if (this.dragging && this.cameraView != null) {
                        this.cameraView.setTranslationY(f + this.cameraView.getTranslationY());
                        this.lastY = y;
                        if (this.cameraPanel.getTag() == null) {
                            this.cameraPanel.setTag(Integer.valueOf(1));
                            animatorSet = new AnimatorSet();
                            animatorArr = new Animator[3];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.cameraPanel, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                            animatorArr[1] = ObjectAnimator.ofFloat(this.flashModeButton[0], "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                            animatorArr[2] = ObjectAnimator.ofFloat(this.flashModeButton[1], "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                            animatorSet.playTogether(animatorArr);
                            animatorSet.setDuration(200);
                            animatorSet.start();
                        }
                    }
                } else if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 6) {
                    this.pressed = false;
                    if (this.dragging) {
                        this.dragging = false;
                        if (this.cameraView != null) {
                            if (Math.abs(this.cameraView.getTranslationY()) > ((float) this.cameraView.getMeasuredHeight()) / 6.0f) {
                                closeCamera(true);
                            } else {
                                animatorSet = new AnimatorSet();
                                animatorArr = new Animator[4];
                                animatorArr[0] = ObjectAnimator.ofFloat(this.cameraView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
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
                        this.cameraView.focusToPoint((int) (motionEvent.getRawX() - ((float) this.viewPosition[0])), (int) (motionEvent.getRawY() - ((float) this.viewPosition[1])));
                    }
                }
            }
        } else if (!this.takingPhoto) {
            this.pressed = true;
            this.maybeStartDraging = true;
            this.lastY = motionEvent.getY();
        }
        return true;
    }

    private void resetRecordState() {
        if (this.baseFragment != null) {
            for (int i = 0; i < 2; i++) {
                this.flashModeButton[i].setAlpha(1.0f);
            }
            this.switchCameraButton.setAlpha(1.0f);
            this.recordTime.setAlpha(BitmapDescriptorFactory.HUE_RED);
            AndroidUtilities.cancelRunOnUIThread(this.videoRecordRunnable);
            this.videoRecordRunnable = null;
            AndroidUtilities.unlockOrientation(this.baseFragment.getParentActivity());
        }
    }

    private void setCameraFlashModeIcon(ImageView imageView, String str) {
        Object obj = -1;
        switch (str.hashCode()) {
            case 3551:
                if (str.equals("on")) {
                    obj = 1;
                    break;
                }
                break;
            case 109935:
                if (str.equals("off")) {
                    obj = null;
                    break;
                }
                break;
            case 3005871:
                if (str.equals("auto")) {
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

    private void setUseRevealAnimation(boolean z) {
        if (!z || (z && VERSION.SDK_INT >= 18 && !AndroidUtilities.isTablet() && VERSION.SDK_INT < 26)) {
            this.useRevealAnimation = z;
        }
    }

    private void showHint() {
        if (!SearchQuery.inlineBots.isEmpty() && !ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("bothint", false)) {
            this.hintShowed = true;
            this.hintTextView.setVisibility(0);
            this.currentHintAnimation = new AnimatorSet();
            this.currentHintAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.hintTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
            this.currentHintAnimation.setInterpolator(this.decelerateInterpolator);
            this.currentHintAnimation.addListener(new AnimatorListenerAdapter() {

                /* renamed from: org.telegram.ui.Components.ChatAttachAlert$21$1 */
                class C43641 implements Runnable {
                    C43641() {
                    }

                    public void run() {
                        if (ChatAttachAlert.this.hideHintRunnable == this) {
                            ChatAttachAlert.this.hideHintRunnable = null;
                            ChatAttachAlert.this.hideHint();
                        }
                    }
                }

                public void onAnimationCancel(Animator animator) {
                    if (ChatAttachAlert.this.currentHintAnimation != null && ChatAttachAlert.this.currentHintAnimation.equals(animator)) {
                        ChatAttachAlert.this.currentHintAnimation = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (ChatAttachAlert.this.currentHintAnimation != null && ChatAttachAlert.this.currentHintAnimation.equals(animator)) {
                        ChatAttachAlert.this.currentHintAnimation = null;
                        AndroidUtilities.runOnUIThread(ChatAttachAlert.this.hideHintRunnable = new C43641(), 2000);
                    }
                }
            });
            this.currentHintAnimation.setDuration(300);
            this.currentHintAnimation.start();
        }
    }

    @SuppressLint({"NewApi"})
    private void startRevealAnimation(final boolean z) {
        this.containerView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        final AnimatorSet animatorSet = new AnimatorSet();
        View revealView = this.delegate.getRevealView();
        if (revealView.getVisibility() == 0 && ((ViewGroup) revealView.getParent()).getVisibility() == 0) {
            int[] iArr = new int[2];
            revealView.getLocationInWindow(iArr);
            float measuredHeight = VERSION.SDK_INT <= 19 ? (float) ((AndroidUtilities.displaySize.y - this.containerView.getMeasuredHeight()) - AndroidUtilities.statusBarHeight) : this.containerView.getY();
            this.revealX = iArr[0] + (revealView.getMeasuredWidth() / 2);
            this.revealY = (int) (((float) ((revealView.getMeasuredHeight() / 2) + iArr[1])) - measuredHeight);
            if (VERSION.SDK_INT <= 19) {
                this.revealY -= AndroidUtilities.statusBarHeight;
            }
        } else {
            this.revealX = (AndroidUtilities.displaySize.x / 2) + backgroundPaddingLeft;
            this.revealY = (int) (((float) AndroidUtilities.displaySize.y) - this.containerView.getY());
        }
        r2 = new int[4][];
        r2[1] = new int[]{0, AndroidUtilities.dp(304.0f)};
        r2[2] = new int[]{this.containerView.getMeasuredWidth(), 0};
        r2[3] = new int[]{this.containerView.getMeasuredWidth(), AndroidUtilities.dp(304.0f)};
        int i = (this.revealY - this.scrollOffsetY) + backgroundPaddingTop;
        int i2 = 0;
        int i3 = 0;
        while (i2 < 4) {
            i2++;
            i3 = Math.max(i3, (int) Math.ceil(Math.sqrt((double) (((this.revealX - r2[i2][0]) * (this.revealX - r2[i2][0])) + ((i - r2[i2][1]) * (i - r2[i2][1]))))));
        }
        i2 = this.revealX <= this.containerView.getMeasuredWidth() ? this.revealX : this.containerView.getMeasuredWidth();
        ArrayList arrayList = new ArrayList(3);
        String str = "revealRadius";
        float[] fArr = new float[2];
        fArr[0] = z ? BitmapDescriptorFactory.HUE_RED : (float) i3;
        fArr[1] = z ? (float) i3 : BitmapDescriptorFactory.HUE_RED;
        arrayList.add(ObjectAnimator.ofFloat(this, str, fArr));
        ColorDrawable colorDrawable = this.backDrawable;
        String str2 = "alpha";
        int[] iArr2 = new int[1];
        iArr2[0] = z ? 51 : 0;
        arrayList.add(ObjectAnimator.ofInt(colorDrawable, str2, iArr2));
        if (VERSION.SDK_INT >= 21) {
            try {
                arrayList.add(ViewAnimationUtils.createCircularReveal(this.containerView, i2, this.revealY, z ? BitmapDescriptorFactory.HUE_RED : (float) i3, z ? (float) i3 : BitmapDescriptorFactory.HUE_RED));
            } catch (Throwable e) {
                FileLog.e(e);
            }
            animatorSet.setDuration(320);
        } else if (z) {
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
            arrayList.add(ObjectAnimator.ofFloat(this.containerView, "scaleX", new float[]{BitmapDescriptorFactory.HUE_RED}));
            arrayList.add(ObjectAnimator.ofFloat(this.containerView, "scaleY", new float[]{BitmapDescriptorFactory.HUE_RED}));
            arrayList.add(ObjectAnimator.ofFloat(this.containerView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
        }
        animatorSet.playTogether(arrayList);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationCancel(Animator animator) {
                if (ChatAttachAlert.this.currentSheetAnimation != null && animatorSet.equals(animator)) {
                    ChatAttachAlert.this.currentSheetAnimation = null;
                }
            }

            public void onAnimationEnd(Animator animator) {
                if (ChatAttachAlert.this.currentSheetAnimation != null && ChatAttachAlert.this.currentSheetAnimation.equals(animator)) {
                    ChatAttachAlert.this.currentSheetAnimation = null;
                    ChatAttachAlert.this.onRevealAnimationEnd(z);
                    ChatAttachAlert.this.containerView.invalidate();
                    ChatAttachAlert.this.containerView.setLayerType(0, null);
                    if (!z) {
                        try {
                            ChatAttachAlert.this.dismissInternal();
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                }
            }
        });
        if (z) {
            this.innerAnimators.clear();
            NotificationCenter.getInstance().setAllowedNotificationsDutingAnimation(new int[]{NotificationCenter.dialogsNeedReload});
            NotificationCenter.getInstance().setAnimationInProgress(true);
            this.revealAnimationInProgress = true;
            i2 = VERSION.SDK_INT <= 19 ? 12 : 8;
            for (int i4 = 0; i4 < i2; i4++) {
                AnimatorSet animatorSet2;
                if (VERSION.SDK_INT <= 19) {
                    if (i4 < 8) {
                        this.views[i4].setScaleX(0.1f);
                        this.views[i4].setScaleY(0.1f);
                    }
                    this.views[i4].setAlpha(BitmapDescriptorFactory.HUE_RED);
                } else {
                    this.views[i4].setScaleX(0.7f);
                    this.views[i4].setScaleY(0.7f);
                }
                InnerAnimator innerAnimator = new InnerAnimator();
                int left = this.views[i4].getLeft() + (this.views[i4].getMeasuredWidth() / 2);
                i = (this.views[i4].getTop() + this.attachView.getTop()) + (this.views[i4].getMeasuredHeight() / 2);
                float sqrt = (float) Math.sqrt((double) (((this.revealX - left) * (this.revealX - left)) + ((this.revealY - i) * (this.revealY - i))));
                float f = ((float) (this.revealY - i)) / sqrt;
                this.views[i4].setPivotX(((((float) (this.revealX - left)) / sqrt) * ((float) AndroidUtilities.dp(20.0f))) + ((float) (this.views[i4].getMeasuredWidth() / 2)));
                this.views[i4].setPivotY((f * ((float) AndroidUtilities.dp(20.0f))) + ((float) (this.views[i4].getMeasuredHeight() / 2)));
                innerAnimator.startRadius = sqrt - ((float) AndroidUtilities.dp(81.0f));
                this.views[i4].setTag(R.string.AppName, Integer.valueOf(1));
                Collection arrayList2 = new ArrayList();
                if (i4 < 8) {
                    arrayList2.add(ObjectAnimator.ofFloat(this.views[i4], "scaleX", new float[]{0.7f, 1.05f}));
                    arrayList2.add(ObjectAnimator.ofFloat(this.views[i4], "scaleY", new float[]{0.7f, 1.05f}));
                    animatorSet2 = new AnimatorSet();
                    r6 = new Animator[2];
                    r6[0] = ObjectAnimator.ofFloat(this.views[i4], "scaleX", new float[]{1.0f});
                    r6[1] = ObjectAnimator.ofFloat(this.views[i4], "scaleY", new float[]{1.0f});
                    animatorSet2.playTogether(r6);
                    animatorSet2.setDuration(100);
                    animatorSet2.setInterpolator(this.decelerateInterpolator);
                } else {
                    animatorSet2 = null;
                }
                if (VERSION.SDK_INT <= 19) {
                    arrayList2.add(ObjectAnimator.ofFloat(this.views[i4], "alpha", new float[]{1.0f}));
                }
                innerAnimator.animatorSet = new AnimatorSet();
                innerAnimator.animatorSet.playTogether(arrayList2);
                innerAnimator.animatorSet.setDuration(150);
                innerAnimator.animatorSet.setInterpolator(this.decelerateInterpolator);
                innerAnimator.animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        if (animatorSet2 != null) {
                            animatorSet2.start();
                        }
                    }
                });
                this.innerAnimators.add(innerAnimator);
            }
        }
        this.currentSheetAnimation = animatorSet;
        animatorSet.start();
    }

    private void updateCheckedPhotoIndices() {
        int childCount = this.attachPhotoRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.attachPhotoRecyclerView.getChildAt(i);
            if (childAt instanceof PhotoAttachPhotoCell) {
                PhotoAttachPhotoCell photoAttachPhotoCell = (PhotoAttachPhotoCell) childAt;
                photoAttachPhotoCell.setNum(this.photoAttachAdapter.selectedPhotosOrder.indexOf(Integer.valueOf(((PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(((Integer) photoAttachPhotoCell.getTag()).intValue())).imageId)));
            }
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
        View childAt = this.listView.getChildAt(0);
        Holder holder = (Holder) this.listView.findContainingViewHolder(childAt);
        paddingTop = childAt.getTop();
        int i = (paddingTop < 0 || holder == null || holder.getAdapterPosition() != 0) ? 0 : paddingTop;
        if (this.scrollOffsetY != i) {
            RecyclerListView recyclerListView2 = this.listView;
            this.scrollOffsetY = i;
            recyclerListView2.setTopGlowOffset(i);
            this.listView.invalidate();
        }
    }

    public boolean canDismiss() {
        return true;
    }

    protected boolean canDismissWithSwipe() {
        return false;
    }

    protected boolean canDismissWithTouchOutside() {
        return !this.cameraOpened;
    }

    public void checkCamera(boolean z) {
        if (this.baseFragment != null) {
            boolean z2 = this.deviceHasGoodCamera;
            if (!MediaController.getInstance().canInAppCamera()) {
                this.deviceHasGoodCamera = false;
            } else if (VERSION.SDK_INT < 23) {
                CameraController.getInstance().initCamera();
                this.deviceHasGoodCamera = CameraController.getInstance().isCameraInitied();
            } else if (this.baseFragment.getParentActivity().checkSelfPermission("android.permission.CAMERA") != 0) {
                if (z) {
                    this.baseFragment.getParentActivity().requestPermissions(new String[]{"android.permission.CAMERA"}, 17);
                }
                this.deviceHasGoodCamera = false;
            } else {
                CameraController.getInstance().initCamera();
                this.deviceHasGoodCamera = CameraController.getInstance().isCameraInitied();
            }
            if (!(z2 == this.deviceHasGoodCamera || this.photoAttachAdapter == null)) {
                this.photoAttachAdapter.notifyDataSetChanged();
            }
            if (isShowing() && this.deviceHasGoodCamera && this.baseFragment != null && this.backDrawable.getAlpha() != 0 && !this.revealAnimationInProgress && !this.cameraOpened) {
                showCamera();
            }
        }
    }

    public void closeCamera(boolean z) {
        if (!this.takingPhoto && this.cameraView != null) {
            this.animateCameraValues[1] = AndroidUtilities.dp(80.0f) - this.cameraViewOffsetX;
            this.animateCameraValues[2] = AndroidUtilities.dp(80.0f) - this.cameraViewOffsetY;
            int i;
            if (z) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.cameraView.getLayoutParams();
                int[] iArr = this.animateCameraValues;
                int translationY = (int) this.cameraView.getTranslationY();
                layoutParams.topMargin = translationY;
                iArr[0] = translationY;
                this.cameraView.setLayoutParams(layoutParams);
                this.cameraView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                this.cameraAnimationInProgress = true;
                Collection arrayList = new ArrayList();
                arrayList.add(ObjectAnimator.ofFloat(this, "cameraOpenProgress", new float[]{BitmapDescriptorFactory.HUE_RED}));
                arrayList.add(ObjectAnimator.ofFloat(this.cameraPanel, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                for (i = 0; i < 2; i++) {
                    if (this.flashModeButton[i].getVisibility() == 0) {
                        arrayList.add(ObjectAnimator.ofFloat(this.flashModeButton[i], "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                        break;
                    }
                }
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList);
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
            setCameraOpenProgress(BitmapDescriptorFactory.HUE_RED);
            this.cameraPanel.setAlpha(BitmapDescriptorFactory.HUE_RED);
            this.cameraPanel.setVisibility(8);
            for (i = 0; i < 2; i++) {
                if (this.flashModeButton[i].getVisibility() == 0) {
                    this.flashModeButton[i].setAlpha(BitmapDescriptorFactory.HUE_RED);
                    break;
                }
            }
            this.cameraOpened = false;
            if (VERSION.SDK_INT >= 21) {
                this.cameraView.setSystemUiVisibility(1024);
            }
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.albumsDidLoaded) {
            if (this.photoAttachAdapter != null) {
                this.loading = false;
                this.progressView.showTextView();
                this.photoAttachAdapter.notifyDataSetChanged();
            }
        } else if (i == NotificationCenter.reloadInlineHints) {
            if (this.adapter != null) {
                this.adapter.notifyDataSetChanged();
            }
        } else if (i == NotificationCenter.cameraInitied) {
            checkCamera(false);
        }
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

    public void dismissInternal() {
        if (this.containerView != null) {
            this.containerView.setVisibility(4);
        }
        super.dismissInternal();
    }

    public void dismissWithButtonClick(int i) {
        super.dismissWithButtonClick(i);
        boolean z = (i == 0 || i == 2) ? false : true;
        hideCamera(z);
    }

    public float getCameraOpenProgress() {
        return this.cameraOpenProgress;
    }

    protected float getRevealRadius() {
        return this.revealRadius;
    }

    public HashMap<Object, Object> getSelectedPhotos() {
        return this.photoAttachAdapter.selectedPhotos;
    }

    public ArrayList<Object> getSelectedPhotosOrder() {
        return this.photoAttachAdapter.selectedPhotosOrder;
    }

    public void hideCamera(boolean z) {
        if (this.deviceHasGoodCamera && this.cameraView != null) {
            this.cameraView.destroy(z, null);
            this.container.removeView(this.cameraView);
            this.container.removeView(this.cameraIcon);
            this.cameraView = null;
            this.cameraIcon = null;
            int childCount = this.attachPhotoRecyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.attachPhotoRecyclerView.getChildAt(i);
                if (childAt instanceof PhotoAttachCameraCell) {
                    childAt.setVisibility(0);
                    return;
                }
            }
        }
    }

    public void init() {
        if (MediaController.allMediaAlbumEntry != null) {
            for (int i = 0; i < Math.min(100, MediaController.allMediaAlbumEntry.photos.size()); i++) {
                ((PhotoEntry) MediaController.allMediaAlbumEntry.photos.get(i)).reset();
            }
        }
        if (this.currentHintAnimation != null) {
            this.currentHintAnimation.cancel();
            this.currentHintAnimation = null;
        }
        this.hintTextView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.hintTextView.setVisibility(4);
        this.attachPhotoLayoutManager.scrollToPositionWithOffset(0, 1000000);
        this.photoAttachAdapter.clearSelectedPhotos();
        this.layoutManager.scrollToPositionWithOffset(0, 1000000);
        updatePhotosButton();
    }

    public void loadGalleryPhotos() {
        if (MediaController.allMediaAlbumEntry == null && VERSION.SDK_INT >= 21) {
            MediaController.loadGalleryPhotosAlbums(0);
        }
    }

    protected boolean onContainerTouchEvent(MotionEvent motionEvent) {
        return this.cameraOpened && processTouchEvent(motionEvent);
    }

    protected boolean onCustomCloseAnimation() {
        if (!this.useRevealAnimation) {
            return false;
        }
        this.backDrawable.setAlpha(51);
        startRevealAnimation(false);
        return true;
    }

    protected boolean onCustomLayout(View view, int i, int i2, int i3, int i4) {
        int i5 = 0;
        int i6 = i3 - i;
        int i7 = i4 - i2;
        boolean z = i6 < i7;
        if (view == this.cameraPanel) {
            if (z) {
                this.cameraPanel.layout(0, i4 - AndroidUtilities.dp(100.0f), i6, i4);
                return true;
            }
            this.cameraPanel.layout(i3 - AndroidUtilities.dp(100.0f), 0, i3, i7);
            return true;
        } else if (view != this.flashModeButton[0] && view != this.flashModeButton[1]) {
            return false;
        } else {
            i6 = VERSION.SDK_INT >= 21 ? AndroidUtilities.dp(10.0f) : 0;
            if (VERSION.SDK_INT >= 21) {
                i5 = AndroidUtilities.dp(8.0f);
            }
            if (z) {
                view.layout((i3 - view.getMeasuredWidth()) - i5, i6, i3 - i5, view.getMeasuredHeight() + i6);
                return true;
            }
            view.layout(i5, i6, view.getMeasuredWidth() + i5, view.getMeasuredHeight() + i6);
            return true;
        }
    }

    protected boolean onCustomMeasure(View view, int i, int i2) {
        boolean z = i < i2;
        if (view == this.cameraView) {
            if (this.cameraOpened && !this.cameraAnimationInProgress) {
                this.cameraView.measure(MeasureSpec.makeMeasureSpec(i, 1073741824), MeasureSpec.makeMeasureSpec(i2, 1073741824));
                return true;
            }
        } else if (view == this.cameraPanel) {
            if (z) {
                this.cameraPanel.measure(MeasureSpec.makeMeasureSpec(i, 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(100.0f), 1073741824));
                return true;
            }
            this.cameraPanel.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(100.0f), 1073741824), MeasureSpec.makeMeasureSpec(i2, 1073741824));
            return true;
        }
        return false;
    }

    protected boolean onCustomOpenAnimation() {
        float f = 1.0f;
        if (this.baseFragment != null) {
            Chat currentChat = this.baseFragment.getCurrentChat();
            if (ChatObject.isChannel(currentChat)) {
                boolean z = currentChat.banned_rights == null || !currentChat.banned_rights.send_media;
                this.mediaEnabled = z;
                for (int i = 0; i < 5; i++) {
                    ((AttachButton) this.attachButtons.get(i)).setAlpha(this.mediaEnabled ? 1.0f : 0.2f);
                    ((AttachButton) this.attachButtons.get(i)).setEnabled(this.mediaEnabled);
                }
                this.attachPhotoRecyclerView.setAlpha(this.mediaEnabled ? 1.0f : 0.2f);
                this.attachPhotoRecyclerView.setEnabled(this.mediaEnabled);
                if (!this.mediaEnabled) {
                    if (AndroidUtilities.isBannedForever(currentChat.banned_rights.until_date)) {
                        this.mediaBanTooltip.setText(LocaleController.formatString("AttachMediaRestrictedForever", R.string.AttachMediaRestrictedForever, new Object[0]));
                    } else {
                        this.mediaBanTooltip.setText(LocaleController.formatString("AttachMediaRestricted", R.string.AttachMediaRestricted, new Object[]{LocaleController.formatDateForBan((long) currentChat.banned_rights.until_date)}));
                    }
                }
                this.mediaBanTooltip.setVisibility(this.mediaEnabled ? 4 : 0);
                if (this.cameraView != null) {
                    this.cameraView.setAlpha(this.mediaEnabled ? 1.0f : 0.2f);
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

    public void onDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.albumsDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.reloadInlineHints);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.cameraInitied);
        this.baseFragment = null;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!this.cameraOpened || (i != 24 && i != 25)) {
            return super.onKeyDown(i, keyEvent);
        }
        this.shutterButton.getDelegate().shutterReleased();
        return true;
    }

    public void onOpenAnimationEnd() {
        onRevealAnimationEnd(true);
    }

    public void onOpenAnimationStart() {
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

    public void setAllowDrawContent(boolean z) {
        super.setAllowDrawContent(z);
        checkCameraViewPosition();
    }

    public void setCameraOpenProgress(float f) {
        if (this.cameraView != null) {
            float width;
            float height;
            this.cameraOpenProgress = f;
            float f2 = (float) this.animateCameraValues[1];
            float f3 = (float) this.animateCameraValues[2];
            if ((AndroidUtilities.displaySize.x < AndroidUtilities.displaySize.y ? 1 : 0) != 0) {
                width = (float) this.container.getWidth();
                height = (float) this.container.getHeight();
            } else {
                width = (float) this.container.getWidth();
                height = (float) this.container.getHeight();
            }
            if (f == BitmapDescriptorFactory.HUE_RED) {
                this.cameraView.setClipLeft(this.cameraViewOffsetX);
                this.cameraView.setClipTop(this.cameraViewOffsetY);
                this.cameraView.setTranslationX((float) this.cameraViewLocation[0]);
                this.cameraView.setTranslationY((float) this.cameraViewLocation[1]);
                this.cameraIcon.setTranslationX((float) this.cameraViewLocation[0]);
                this.cameraIcon.setTranslationY((float) this.cameraViewLocation[1]);
            } else if (!(this.cameraView.getTranslationX() == BitmapDescriptorFactory.HUE_RED && this.cameraView.getTranslationY() == BitmapDescriptorFactory.HUE_RED)) {
                this.cameraView.setTranslationX(BitmapDescriptorFactory.HUE_RED);
                this.cameraView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
            }
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.cameraView.getLayoutParams();
            layoutParams.width = (int) (((width - f2) * f) + f2);
            layoutParams.height = (int) (((height - f3) * f) + f3);
            if (f != BitmapDescriptorFactory.HUE_RED) {
                this.cameraView.setClipLeft((int) (((float) this.cameraViewOffsetX) * (1.0f - f)));
                this.cameraView.setClipTop((int) (((float) this.cameraViewOffsetY) * (1.0f - f)));
                layoutParams.leftMargin = (int) (((float) this.cameraViewLocation[0]) * (1.0f - f));
                layoutParams.topMargin = (int) (((float) this.animateCameraValues[0]) + (((float) (this.cameraViewLocation[1] - this.animateCameraValues[0])) * (1.0f - f)));
            } else {
                layoutParams.leftMargin = 0;
                layoutParams.topMargin = 0;
            }
            this.cameraView.setLayoutParams(layoutParams);
            if (f <= 0.5f) {
                this.cameraIcon.setAlpha(1.0f - (f / 0.5f));
            } else {
                this.cameraIcon.setAlpha(BitmapDescriptorFactory.HUE_RED);
            }
        }
    }

    public void setDelegate(ChatAttachViewDelegate chatAttachViewDelegate) {
        this.delegate = chatAttachViewDelegate;
    }

    @SuppressLint({"NewApi"})
    protected void setRevealRadius(float f) {
        this.revealRadius = f;
        if (VERSION.SDK_INT <= 19) {
            this.listView.invalidate();
        }
        if (!isDismissed()) {
            int i = 0;
            while (i < this.innerAnimators.size()) {
                InnerAnimator innerAnimator = (InnerAnimator) this.innerAnimators.get(i);
                if (innerAnimator.startRadius <= f) {
                    innerAnimator.animatorSet.start();
                    this.innerAnimators.remove(i);
                    i--;
                }
                i++;
            }
        }
    }

    public void showCamera() {
        float f = 1.0f;
        if (!this.paused && this.mediaEnabled) {
            if (this.cameraView == null) {
                this.cameraView = new CameraView(this.baseFragment.getParentActivity(), false);
                this.container.addView(this.cameraView, 1, LayoutHelper.createFrame(80, 80.0f));
                this.cameraView.setDelegate(new CameraViewDelegate() {
                    public void onCameraCreated(Camera camera) {
                    }

                    public void onCameraInit() {
                        int i;
                        int i2 = 0;
                        int childCount = ChatAttachAlert.this.attachPhotoRecyclerView.getChildCount();
                        for (i = 0; i < childCount; i++) {
                            View childAt = ChatAttachAlert.this.attachPhotoRecyclerView.getChildAt(i);
                            if (childAt instanceof PhotoAttachCameraCell) {
                                childAt.setVisibility(4);
                                break;
                            }
                        }
                        if (ChatAttachAlert.this.cameraView.getCameraSession().getCurrentFlashMode().equals(ChatAttachAlert.this.cameraView.getCameraSession().getNextFlashMode())) {
                            for (i = 0; i < 2; i++) {
                                ChatAttachAlert.this.flashModeButton[i].setVisibility(4);
                                ChatAttachAlert.this.flashModeButton[i].setAlpha(BitmapDescriptorFactory.HUE_RED);
                                ChatAttachAlert.this.flashModeButton[i].setTranslationY(BitmapDescriptorFactory.HUE_RED);
                            }
                        } else {
                            ChatAttachAlert.this.setCameraFlashModeIcon(ChatAttachAlert.this.flashModeButton[0], ChatAttachAlert.this.cameraView.getCameraSession().getCurrentFlashMode());
                            childCount = 0;
                            while (childCount < 2) {
                                ChatAttachAlert.this.flashModeButton[childCount].setVisibility(childCount == 0 ? 0 : 4);
                                ImageView imageView = ChatAttachAlert.this.flashModeButton[childCount];
                                float f = (childCount == 0 && ChatAttachAlert.this.cameraOpened) ? 1.0f : BitmapDescriptorFactory.HUE_RED;
                                imageView.setAlpha(f);
                                ChatAttachAlert.this.flashModeButton[childCount].setTranslationY(BitmapDescriptorFactory.HUE_RED);
                                childCount++;
                            }
                        }
                        ChatAttachAlert.this.switchCameraButton.setImageResource(ChatAttachAlert.this.cameraView.isFrontface() ? R.drawable.camera_revert1 : R.drawable.camera_revert2);
                        ImageView access$5300 = ChatAttachAlert.this.switchCameraButton;
                        if (!ChatAttachAlert.this.cameraView.hasFrontFaceCamera()) {
                            i2 = 4;
                        }
                        access$5300.setVisibility(i2);
                    }
                });
                this.cameraIcon = new FrameLayout(this.baseFragment.getParentActivity());
                this.container.addView(this.cameraIcon, 2, LayoutHelper.createFrame(80, 80.0f));
                View imageView = new ImageView(this.baseFragment.getParentActivity());
                imageView.setScaleType(ScaleType.CENTER);
                imageView.setImageResource(R.drawable.instant_camera);
                this.cameraIcon.addView(imageView, LayoutHelper.createFrame(80, 80, 85));
                this.cameraView.setAlpha(this.mediaEnabled ? 1.0f : 0.2f);
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

    public void updatePhotosButton() {
        int size = this.photoAttachAdapter.selectedPhotos.size();
        if (size == 0) {
            this.sendPhotosButton.imageView.setPadding(0, AndroidUtilities.dp(4.0f), 0, 0);
            this.sendPhotosButton.imageView.setBackgroundResource(R.drawable.attach_hide_states);
            this.sendPhotosButton.imageView.setImageResource(R.drawable.attach_hide2);
            this.sendPhotosButton.textView.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.sendDocumentsButton.textView.setText(LocaleController.getString("ChatDocument", R.string.ChatDocument));
        } else {
            this.sendPhotosButton.imageView.setPadding(AndroidUtilities.dp(2.0f), 0, 0, 0);
            this.sendPhotosButton.imageView.setBackgroundResource(R.drawable.attach_send_states);
            this.sendPhotosButton.imageView.setImageResource(R.drawable.attach_send2);
            TextView access$7200 = this.sendPhotosButton.textView;
            Object[] objArr = new Object[1];
            objArr[0] = String.format("(%d)", new Object[]{Integer.valueOf(size)});
            access$7200.setText(LocaleController.formatString("SendItems", R.string.SendItems, objArr));
            this.sendDocumentsButton.textView.setText(size == 1 ? LocaleController.getString("SendAsFile", R.string.SendAsFile) : LocaleController.getString("SendAsFiles", R.string.SendAsFiles));
        }
        if (VERSION.SDK_INT < 23 || getContext().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
            this.progressView.setText(LocaleController.getString("NoPhotos", R.string.NoPhotos));
            this.progressView.setTextSize(20);
            return;
        }
        this.progressView.setText(LocaleController.getString("PermissionStorage", R.string.PermissionStorage));
        this.progressView.setTextSize(16);
    }
}
