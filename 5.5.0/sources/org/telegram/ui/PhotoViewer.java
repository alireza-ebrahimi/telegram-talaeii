package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaCodecInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.p054b.p055a.C1289d;
import com.p054b.p055a.p056a.C1246e;
import com.p054b.p055a.p056a.C1248b;
import com.p054b.p055a.p056a.C1269l;
import com.p054b.p055a.p056a.C1270m;
import com.p054b.p055a.p056a.C1284y;
import com.p054b.p055a.p056a.C1285z;
import com.p057c.p058a.p063b.C1320g;
import com.p057c.p058a.p063b.C1321h;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.EmojiSuggestion;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.ImageReceiver.ImageReceiverDelegate;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.PhotoEntry;
import org.telegram.messenger.MediaController.SavedFilterState;
import org.telegram.messenger.MediaController.SearchImage;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.query.SharedMediaQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.TLRPC$TL_inputPhoto;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageActionEmpty;
import org.telegram.tgnet.TLRPC$TL_messageActionUserUpdatedPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaEmpty;
import org.telegram.tgnet.TLRPC$TL_messageMediaInvoice;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_pageBlockAuthorDate;
import org.telegram.tgnet.TLRPC$TL_pageFull;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC.BotInlineResult;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputPhoto;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.Photo;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Adapters.MentionsAdapter;
import org.telegram.ui.Adapters.MentionsAdapter.MentionsAdapterDelegate;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.Cells.PhotoPickerPhotoCell;
import org.telegram.ui.Components.AnimatedFileDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.ChatAttachAlert;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.ClippingImageView;
import org.telegram.ui.Components.CubicBezierInterpolator;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.NumberPicker;
import org.telegram.ui.Components.NumberPicker.Formatter;
import org.telegram.ui.Components.PhotoCropView;
import org.telegram.ui.Components.PhotoCropView.PhotoCropViewDelegate;
import org.telegram.ui.Components.PhotoFilterView;
import org.telegram.ui.Components.PhotoPaintView;
import org.telegram.ui.Components.PhotoViewerCaptionEnterView;
import org.telegram.ui.Components.PhotoViewerCaptionEnterView.PhotoViewerCaptionEnterViewDelegate;
import org.telegram.ui.Components.PickerBottomLayoutViewer;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.SeekBar;
import org.telegram.ui.Components.SeekBar.SeekBarDelegate;
import org.telegram.ui.Components.SizeNotifierFrameLayoutPhoto;
import org.telegram.ui.Components.StickersAlert;
import org.telegram.ui.Components.VideoPlayer;
import org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate;
import org.telegram.ui.Components.VideoTimelinePlayView;
import org.telegram.ui.Components.VideoTimelinePlayView.VideoTimelineViewDelegate;
import org.telegram.ui.DialogsActivity.DialogsActivityDelegate;
import utils.p178a.C3791b;

public class PhotoViewer implements OnDoubleTapListener, OnGestureListener, NotificationCenterDelegate {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile PhotoViewer Instance = null;
    private static DecelerateInterpolator decelerateInterpolator = null;
    private static final int gallery_menu_delete = 6;
    private static final int gallery_menu_masks = 13;
    private static final int gallery_menu_openin = 11;
    private static final int gallery_menu_save = 1;
    private static final int gallery_menu_send = 3;
    private static final int gallery_menu_share = 10;
    private static final int gallery_menu_showall = 2;
    private static final int gallery_menu_showinchat = 4;
    private static Drawable[] progressDrawables;
    private static Paint progressPaint;
    private ActionBar actionBar;
    private Context actvityContext;
    private boolean allowMentions;
    private boolean allowShare;
    private float animateToScale;
    private float animateToX;
    private float animateToY;
    private ClippingImageView animatingImageView;
    private Runnable animationEndRunnable;
    private int animationInProgress;
    private long animationStartTime;
    private float animationValue;
    private float[][] animationValues = ((float[][]) Array.newInstance(Float.TYPE, new int[]{2, 8}));
    private boolean applying;
    private AspectRatioFrameLayout aspectRatioFrameLayout;
    private boolean attachedToWindow;
    private long audioFramesSize;
    private ArrayList<Photo> avatarsArr = new ArrayList();
    private int avatarsDialogId;
    private BackgroundDrawable backgroundDrawable = new BackgroundDrawable(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
    private int bitrate;
    private Paint blackPaint = new Paint();
    private FrameLayout bottomLayout;
    private boolean bottomTouchEnabled = true;
    private boolean canDragDown = true;
    private boolean canShowBottom = true;
    private boolean canZoom = true;
    private PhotoViewerCaptionEnterView captionEditText;
    private TextView captionTextView;
    private ImageReceiver centerImage = new ImageReceiver();
    private AnimatorSet changeModeAnimation;
    private boolean changingPage;
    private CheckBox checkImageView;
    private int classGuid;
    private ImageView compressItem;
    private AnimatorSet compressItemAnimation;
    private int compressionsCount = -1;
    private FrameLayoutDrawer containerView;
    private ImageView cropItem;
    private AnimatorSet currentActionBarAnimation;
    private AnimatedFileDrawable currentAnimation;
    private BotInlineResult currentBotInlineResult;
    private long currentDialogId;
    private int currentEditMode;
    private FileLocation currentFileLocation;
    private String[] currentFileNames = new String[3];
    private int currentIndex;
    private AnimatorSet currentListViewAnimation;
    private Runnable currentLoadingVideoRunnable;
    private MessageObject currentMessageObject;
    private String currentPathObject;
    private PlaceProviderObject currentPlaceObject;
    private File currentPlayingVideoFile;
    private String currentSubtitle;
    private Bitmap currentThumb;
    private FileLocation currentUserAvatarLocation = null;
    private int dateOverride;
    private TextView dateTextView;
    private boolean disableShowCheck;
    private boolean discardTap;
    private boolean doneButtonPressed;
    private boolean dontResetZoomOnFirstLayout;
    private boolean doubleTap;
    private float dragY;
    private boolean draggingDown;
    private PickerBottomLayoutViewer editorDoneLayout;
    private boolean[] endReached = new boolean[]{false, true};
    private long endTime;
    private long estimatedDuration;
    private int estimatedSize;
    boolean fromCamera;
    private GestureDetector gestureDetector;
    private GroupedPhotosListView groupedPhotosListView;
    private PlaceProviderObject hideAfterAnimation;
    private AnimatorSet hintAnimation;
    private Runnable hintHideRunnable;
    private TextView hintTextView;
    private boolean ignoreDidSetImage;
    private AnimatorSet imageMoveAnimation;
    private ArrayList<MessageObject> imagesArr = new ArrayList();
    private ArrayList<Object> imagesArrLocals = new ArrayList();
    private ArrayList<FileLocation> imagesArrLocations = new ArrayList();
    private ArrayList<Integer> imagesArrLocationsSizes = new ArrayList();
    private ArrayList<MessageObject> imagesArrTemp = new ArrayList();
    private HashMap<Integer, MessageObject>[] imagesByIds = new HashMap[]{new HashMap(), new HashMap()};
    private HashMap<Integer, MessageObject>[] imagesByIdsTemp = new HashMap[]{new HashMap(), new HashMap()};
    private boolean inPreview;
    private DecelerateInterpolator interpolator = new DecelerateInterpolator(1.5f);
    private boolean invalidCoords;
    private boolean isActionBarVisible = true;
    private boolean isCurrentVideo;
    private boolean isEvent;
    private boolean isFirstLoading;
    private boolean isPhotosListViewVisible;
    private boolean isPlaying;
    private boolean isVisible;
    private LinearLayout itemsLayout;
    private Object lastInsets;
    private String lastTitle;
    private ImageReceiver leftImage = new ImageReceiver();
    private boolean loadInitialVideo;
    private boolean loadingMoreImages;
    private ActionBarMenuItem masksItem;
    private float maxX;
    private float maxY;
    private LinearLayoutManager mentionLayoutManager;
    private AnimatorSet mentionListAnimation;
    private RecyclerListView mentionListView;
    private MentionsAdapter mentionsAdapter;
    private ActionBarMenuItem menuItem;
    private long mergeDialogId;
    private float minX;
    private float minY;
    private float moveStartX;
    private float moveStartY;
    private boolean moving;
    private ImageView muteItem;
    private boolean muteVideo;
    private String nameOverride;
    private TextView nameTextView;
    private boolean needCaptionLayout;
    private boolean needSearchImageInArr;
    private boolean opennedFromMedia;
    private int originalBitrate;
    private int originalHeight;
    private long originalSize;
    private int originalWidth;
    private ImageView paintItem;
    private Activity parentActivity;
    private ChatAttachAlert parentAlert;
    private ChatActivity parentChatActivity;
    private PhotoCropView photoCropView;
    private PhotoFilterView photoFilterView;
    private PhotoPaintView photoPaintView;
    private PhotoProgressView[] photoProgressViews = new PhotoProgressView[3];
    private CounterView photosCounterView;
    private FrameLayout pickerView;
    private ImageView pickerViewSendButton;
    private float pinchCenterX;
    private float pinchCenterY;
    private float pinchStartDistance;
    private float pinchStartScale = 1.0f;
    private float pinchStartX;
    private float pinchStartY;
    private PhotoViewerProvider placeProvider;
    private int previewViewEnd;
    private int previousCompression;
    private RadialProgressView progressView;
    private QualityChooseView qualityChooseView;
    private AnimatorSet qualityChooseViewAnimation;
    private PickerBottomLayoutViewer qualityPicker;
    private boolean requestingPreview;
    private TextView resetButton;
    private int resultHeight;
    private int resultWidth;
    private ImageReceiver rightImage = new ImageReceiver();
    private int rotationValue;
    private float scale = 1.0f;
    private Scroller scroller;
    private int selectedCompression;
    private ListAdapter selectedPhotosAdapter;
    private RecyclerListView selectedPhotosListView;
    private ActionBarMenuItem sendItem;
    private int sendPhotoType;
    private ImageView shareButton;
    private PlaceProviderObject showAfterAnimation;
    private int slideshowMessageId;
    private long startTime;
    private int switchImageAfterAnimation;
    private boolean textureUploaded;
    private ImageView timeItem;
    private int totalImagesCount;
    private int totalImagesCountMerge;
    private long transitionAnimationStartTime;
    private float translationX;
    private float translationY;
    private boolean tryStartRequestPreviewOnFinish;
    private ImageView tuneItem;
    private Runnable updateProgressRunnable = new C50451();
    private VelocityTracker velocityTracker;
    private float videoCrossfadeAlpha;
    private long videoCrossfadeAlphaLastTime;
    private boolean videoCrossfadeStarted;
    private float videoDuration;
    private long videoFramesSize;
    private boolean videoHasAudio;
    private ImageView videoPlayButton;
    private VideoPlayer videoPlayer;
    private FrameLayout videoPlayerControlFrameLayout;
    private SeekBar videoPlayerSeekbar;
    private TextView videoPlayerTime;
    private MessageObject videoPreviewMessageObject;
    private TextureView videoTextureView;
    private VideoTimelinePlayView videoTimelineView;
    private AlertDialog visibleDialog;
    private boolean wasLayout;
    private LayoutParams windowLayoutParams;
    private FrameLayout windowView;
    private boolean zoomAnimation;
    private boolean zooming;

    public interface PhotoViewerProvider {
        boolean allowCaption();

        boolean allowGroupPhotos();

        boolean canScrollAway();

        boolean cancelButtonPressed();

        PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i);

        int getSelectedCount();

        HashMap<Object, Object> getSelectedPhotos();

        ArrayList<Object> getSelectedPhotosOrder();

        Bitmap getThumbForPhoto(MessageObject messageObject, FileLocation fileLocation, int i);

        boolean isPhotoChecked(int i);

        boolean scaleToFill();

        void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo);

        int setPhotoChecked(int i, VideoEditedInfo videoEditedInfo);

        void toggleGroupPhotosEnabled();

        void updatePhotoAtIndex(int i);

        void willHidePhotoViewer();

        void willSwitchFromPhoto(MessageObject messageObject, FileLocation fileLocation, int i);
    }

    public static class EmptyPhotoViewerProvider implements PhotoViewerProvider {
        public boolean allowCaption() {
            return true;
        }

        public boolean allowGroupPhotos() {
            return true;
        }

        public boolean canScrollAway() {
            return true;
        }

        public boolean cancelButtonPressed() {
            return true;
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            return null;
        }

        public int getSelectedCount() {
            return 0;
        }

        public HashMap<Object, Object> getSelectedPhotos() {
            return null;
        }

        public ArrayList<Object> getSelectedPhotosOrder() {
            return null;
        }

        public Bitmap getThumbForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            return null;
        }

        public boolean isPhotoChecked(int i) {
            return false;
        }

        public boolean scaleToFill() {
            return false;
        }

        public void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo) {
        }

        public int setPhotoChecked(int i, VideoEditedInfo videoEditedInfo) {
            return -1;
        }

        public void toggleGroupPhotosEnabled() {
        }

        public void updatePhotoAtIndex(int i) {
        }

        public void willHidePhotoViewer() {
        }

        public void willSwitchFromPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
        }
    }

    /* renamed from: org.telegram.ui.PhotoViewer$1 */
    class C50451 implements Runnable {
        C50451() {
        }

        public void run() {
            float f = 1.0f;
            float f2 = BitmapDescriptorFactory.HUE_RED;
            if (PhotoViewer.this.videoPlayer != null) {
                float currentPosition;
                if (PhotoViewer.this.isCurrentVideo) {
                    if (!PhotoViewer.this.videoTimelineView.isDragging()) {
                        currentPosition = ((float) PhotoViewer.this.videoPlayer.getCurrentPosition()) / ((float) PhotoViewer.this.videoPlayer.getDuration());
                        if (PhotoViewer.this.inPreview || PhotoViewer.this.videoTimelineView.getVisibility() != 0) {
                            PhotoViewer.this.videoTimelineView.setProgress(currentPosition);
                        } else if (currentPosition >= PhotoViewer.this.videoTimelineView.getRightProgress()) {
                            PhotoViewer.this.videoPlayer.pause();
                            PhotoViewer.this.videoTimelineView.setProgress(BitmapDescriptorFactory.HUE_RED);
                            PhotoViewer.this.videoPlayer.seekTo((long) ((int) (PhotoViewer.this.videoTimelineView.getLeftProgress() * ((float) PhotoViewer.this.videoPlayer.getDuration()))));
                            PhotoViewer.this.containerView.invalidate();
                        } else {
                            currentPosition -= PhotoViewer.this.videoTimelineView.getLeftProgress();
                            if (currentPosition >= BitmapDescriptorFactory.HUE_RED) {
                                f2 = currentPosition;
                            }
                            f2 /= PhotoViewer.this.videoTimelineView.getRightProgress() - PhotoViewer.this.videoTimelineView.getLeftProgress();
                            if (f2 > 1.0f) {
                                f2 = 1.0f;
                            }
                            PhotoViewer.this.videoTimelineView.setProgress(f2);
                        }
                        PhotoViewer.this.updateVideoPlayerTime();
                    }
                } else if (!PhotoViewer.this.videoPlayerSeekbar.isDragging()) {
                    currentPosition = ((float) PhotoViewer.this.videoPlayer.getCurrentPosition()) / ((float) PhotoViewer.this.videoPlayer.getDuration());
                    if (PhotoViewer.this.inPreview || PhotoViewer.this.videoTimelineView.getVisibility() != 0) {
                        PhotoViewer.this.videoPlayerSeekbar.setProgress(currentPosition);
                    } else if (currentPosition >= PhotoViewer.this.videoTimelineView.getRightProgress()) {
                        PhotoViewer.this.videoPlayer.pause();
                        PhotoViewer.this.videoPlayerSeekbar.setProgress(BitmapDescriptorFactory.HUE_RED);
                        PhotoViewer.this.videoPlayer.seekTo((long) ((int) (PhotoViewer.this.videoTimelineView.getLeftProgress() * ((float) PhotoViewer.this.videoPlayer.getDuration()))));
                        PhotoViewer.this.containerView.invalidate();
                    } else {
                        currentPosition -= PhotoViewer.this.videoTimelineView.getLeftProgress();
                        if (currentPosition >= BitmapDescriptorFactory.HUE_RED) {
                            f2 = currentPosition;
                        }
                        f2 /= PhotoViewer.this.videoTimelineView.getRightProgress() - PhotoViewer.this.videoTimelineView.getLeftProgress();
                        if (f2 <= 1.0f) {
                            f = f2;
                        }
                        PhotoViewer.this.videoPlayerSeekbar.setProgress(f);
                    }
                    PhotoViewer.this.videoPlayerControlFrameLayout.invalidate();
                    PhotoViewer.this.updateVideoPlayerTime();
                }
            }
            if (PhotoViewer.this.isPlaying) {
                AndroidUtilities.runOnUIThread(PhotoViewer.this.updateProgressRunnable);
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoViewer$3 */
    class C50573 implements OnApplyWindowInsetsListener {
        C50573() {
        }

        @SuppressLint({"NewApi"})
        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            WindowInsets windowInsets2 = (WindowInsets) PhotoViewer.this.lastInsets;
            PhotoViewer.this.lastInsets = windowInsets;
            if (windowInsets2 == null || !windowInsets2.toString().equals(windowInsets.toString())) {
                PhotoViewer.this.windowView.requestLayout();
            }
            return windowInsets.consumeSystemWindowInsets();
        }
    }

    /* renamed from: org.telegram.ui.PhotoViewer$4 */
    class C50674 extends ActionBarMenuOnItemClick {
        C50674() {
        }

        public boolean canOpenMenu() {
            if (PhotoViewer.this.currentMessageObject != null) {
                if (FileLoader.getPathToMessage(PhotoViewer.this.currentMessageObject.messageOwner).exists()) {
                    return true;
                }
            } else if (PhotoViewer.this.currentFileLocation != null) {
                TLObject access$5600 = PhotoViewer.this.currentFileLocation;
                boolean z = PhotoViewer.this.avatarsDialogId != 0 || PhotoViewer.this.isEvent;
                if (FileLoader.getPathToAttach(access$5600, z).exists()) {
                    return true;
                }
            }
            return false;
        }

        public void onItemClick(int i) {
            int i2 = 1;
            if (i == -1) {
                if (PhotoViewer.this.needCaptionLayout && (PhotoViewer.this.captionEditText.isPopupShowing() || PhotoViewer.this.captionEditText.isKeyboardVisible())) {
                    PhotoViewer.this.closeCaptionEnter(false);
                } else {
                    PhotoViewer.this.closePhoto(true, false);
                }
            } else if (i == 1) {
                if (VERSION.SDK_INT < 23 || PhotoViewer.this.parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                    File pathToMessage;
                    if (PhotoViewer.this.currentMessageObject != null) {
                        pathToMessage = FileLoader.getPathToMessage(PhotoViewer.this.currentMessageObject.messageOwner);
                    } else if (PhotoViewer.this.currentFileLocation != null) {
                        TLObject access$5600 = PhotoViewer.this.currentFileLocation;
                        boolean z = PhotoViewer.this.avatarsDialogId != 0 || PhotoViewer.this.isEvent;
                        pathToMessage = FileLoader.getPathToAttach(access$5600, z);
                    } else {
                        pathToMessage = null;
                    }
                    if (pathToMessage == null || !pathToMessage.exists()) {
                        Builder builder = new Builder(PhotoViewer.this.parentActivity);
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                        builder.setMessage(LocaleController.getString("PleaseDownload", R.string.PleaseDownload));
                        PhotoViewer.this.showAlertDialog(builder);
                        return;
                    }
                    String file = pathToMessage.toString();
                    Context access$1500 = PhotoViewer.this.parentActivity;
                    if (PhotoViewer.this.currentMessageObject == null || !PhotoViewer.this.currentMessageObject.isVideo()) {
                        i2 = 0;
                    }
                    MediaController.saveFile(file, access$1500, i2, null, null);
                    return;
                }
                PhotoViewer.this.parentActivity.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 4);
            } else if (i == 2) {
                if (PhotoViewer.this.currentDialogId != 0) {
                    PhotoViewer.this.disableShowCheck = true;
                    r0 = new Bundle();
                    r0.putLong("dialog_id", PhotoViewer.this.currentDialogId);
                    r3 = new MediaActivity(r0);
                    if (PhotoViewer.this.parentChatActivity != null) {
                        r3.setChatInfo(PhotoViewer.this.parentChatActivity.getCurrentChatInfo());
                    }
                    PhotoViewer.this.closePhoto(false, false);
                    ((LaunchActivity) PhotoViewer.this.parentActivity).presentFragment(r3, false, true);
                }
            } else if (i == 4) {
                if (PhotoViewer.this.currentMessageObject != null) {
                    Bundle bundle = new Bundle();
                    r0 = (int) PhotoViewer.this.currentDialogId;
                    int access$5900 = (int) (PhotoViewer.this.currentDialogId >> 32);
                    if (r0 == 0) {
                        bundle.putInt("enc_id", access$5900);
                    } else if (access$5900 == 1) {
                        bundle.putInt("chat_id", r0);
                    } else if (r0 > 0) {
                        bundle.putInt("user_id", r0);
                    } else if (r0 < 0) {
                        r4 = MessagesController.getInstance().getChat(Integer.valueOf(-r0));
                        if (!(r4 == null || r4.migrated_to == null)) {
                            bundle.putInt("migrated_to", r0);
                            r0 = -r4.migrated_to.channel_id;
                        }
                        bundle.putInt("chat_id", -r0);
                    }
                    bundle.putInt("message_id", PhotoViewer.this.currentMessageObject.getId());
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                    ((LaunchActivity) PhotoViewer.this.parentActivity).presentFragment(new ChatActivity(bundle), true, true);
                    PhotoViewer.this.currentMessageObject = null;
                    PhotoViewer.this.closePhoto(false, false);
                }
            } else if (i == 3) {
                if (PhotoViewer.this.currentMessageObject != null && PhotoViewer.this.parentActivity != null) {
                    r0 = new Bundle();
                    r0.putBoolean("onlySelect", true);
                    r0.putInt("dialogsType", 3);
                    r3 = new DialogsActivity(r0);
                    final ArrayList arrayList = new ArrayList();
                    arrayList.add(PhotoViewer.this.currentMessageObject);
                    r3.setDelegate(new DialogsActivityDelegate() {
                        public void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
                            int i = 0;
                            if (arrayList.size() > 1 || ((Long) arrayList.get(0)).longValue() == ((long) UserConfig.getClientUserId()) || charSequence != null) {
                                while (i < arrayList.size()) {
                                    long longValue = ((Long) arrayList.get(i)).longValue();
                                    if (charSequence != null) {
                                        SendMessagesHelper.getInstance().sendMessage(charSequence.toString(), longValue, null, null, true, null, null, null);
                                    }
                                    SendMessagesHelper.getInstance().sendMessage(arrayList, longValue);
                                    i++;
                                }
                                dialogsActivity.finishFragment();
                                return;
                            }
                            long longValue2 = ((Long) arrayList.get(0)).longValue();
                            int i2 = (int) longValue2;
                            int i3 = (int) (longValue2 >> 32);
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("scrollToTopOnResume", true);
                            if (i2 == 0) {
                                bundle.putInt("enc_id", i3);
                            } else if (i2 > 0) {
                                bundle.putInt("user_id", i2);
                            } else if (i2 < 0) {
                                bundle.putInt("chat_id", -i2);
                            }
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                            BaseFragment chatActivity = new ChatActivity(bundle);
                            if (((LaunchActivity) PhotoViewer.this.parentActivity).presentFragment(chatActivity, true, false)) {
                                chatActivity.showReplyPanel(true, null, arrayList, null, false);
                                return;
                            }
                            dialogsActivity.finishFragment();
                        }
                    });
                    ((LaunchActivity) PhotoViewer.this.parentActivity).presentFragment(r3, false, true);
                    PhotoViewer.this.closePhoto(false, false);
                }
            } else if (i == 6) {
                if (PhotoViewer.this.parentActivity != null) {
                    Builder builder2 = new Builder(PhotoViewer.this.parentActivity);
                    if (PhotoViewer.this.currentMessageObject != null && PhotoViewer.this.currentMessageObject.isVideo()) {
                        builder2.setMessage(LocaleController.formatString("AreYouSureDeleteVideo", R.string.AreYouSureDeleteVideo, new Object[0]));
                    } else if (PhotoViewer.this.currentMessageObject == null || !PhotoViewer.this.currentMessageObject.isGif()) {
                        builder2.setMessage(LocaleController.formatString("AreYouSureDeletePhoto", R.string.AreYouSureDeletePhoto, new Object[0]));
                    } else {
                        builder2.setMessage(LocaleController.formatString("AreYouSure", R.string.AreYouSure, new Object[0]));
                    }
                    builder2.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    final boolean[] zArr = new boolean[1];
                    if (PhotoViewer.this.currentMessageObject != null) {
                        r0 = (int) PhotoViewer.this.currentMessageObject.getDialogId();
                        if (r0 != 0) {
                            User user;
                            if (r0 > 0) {
                                user = MessagesController.getInstance().getUser(Integer.valueOf(r0));
                                r4 = null;
                            } else {
                                r4 = MessagesController.getInstance().getChat(Integer.valueOf(-r0));
                                user = null;
                            }
                            if (!(user == null && ChatObject.isChannel(r4))) {
                                int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                                if (!((user == null || user.id == UserConfig.getClientUserId()) && r4 == null) && ((PhotoViewer.this.currentMessageObject.messageOwner.action == null || (PhotoViewer.this.currentMessageObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty)) && PhotoViewer.this.currentMessageObject.isOut() && currentTime - PhotoViewer.this.currentMessageObject.messageOwner.date <= 172800)) {
                                    View frameLayout = new FrameLayout(PhotoViewer.this.parentActivity);
                                    View checkBoxCell = new CheckBoxCell(PhotoViewer.this.parentActivity, true);
                                    checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                                    if (r4 != null) {
                                        checkBoxCell.setText(LocaleController.getString("DeleteForAll", R.string.DeleteForAll), TtmlNode.ANONYMOUS_REGION_ID, false, false);
                                    } else {
                                        checkBoxCell.setText(LocaleController.formatString("DeleteForUser", R.string.DeleteForUser, new Object[]{UserObject.getFirstName(user)}), TtmlNode.ANONYMOUS_REGION_ID, false, false);
                                    }
                                    checkBoxCell.setPadding(LocaleController.isRTL ? AndroidUtilities.dp(16.0f) : AndroidUtilities.dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(8.0f) : AndroidUtilities.dp(16.0f), 0);
                                    frameLayout.addView(checkBoxCell, LayoutHelper.createFrame(-1, 48.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                                    checkBoxCell.setOnClickListener(new OnClickListener() {
                                        public void onClick(View view) {
                                            CheckBoxCell checkBoxCell = (CheckBoxCell) view;
                                            zArr[0] = !zArr[0];
                                            checkBoxCell.setChecked(zArr[0], true);
                                        }
                                    });
                                    builder2.setView(frameLayout);
                                }
                            }
                        }
                    }
                    builder2.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EncryptedChat encryptedChat = null;
                            if (PhotoViewer.this.imagesArr.isEmpty()) {
                                if (!PhotoViewer.this.avatarsArr.isEmpty() && PhotoViewer.this.currentIndex >= 0 && PhotoViewer.this.currentIndex < PhotoViewer.this.avatarsArr.size()) {
                                    boolean z;
                                    InputPhoto tLRPC$TL_inputPhoto;
                                    int access$1100;
                                    Photo photo = (Photo) PhotoViewer.this.avatarsArr.get(PhotoViewer.this.currentIndex);
                                    FileLocation fileLocation = (FileLocation) PhotoViewer.this.imagesArrLocations.get(PhotoViewer.this.currentIndex);
                                    Photo photo2 = photo instanceof TLRPC$TL_photoEmpty ? null : photo;
                                    if (PhotoViewer.this.currentUserAvatarLocation != null) {
                                        if (photo2 != null) {
                                            Iterator it = photo2.sizes.iterator();
                                            while (it.hasNext()) {
                                                PhotoSize photoSize = (PhotoSize) it.next();
                                                if (photoSize.location.local_id == PhotoViewer.this.currentUserAvatarLocation.local_id && photoSize.location.volume_id == PhotoViewer.this.currentUserAvatarLocation.volume_id) {
                                                    z = true;
                                                    break;
                                                }
                                            }
                                        } else if (fileLocation.local_id == PhotoViewer.this.currentUserAvatarLocation.local_id && fileLocation.volume_id == PhotoViewer.this.currentUserAvatarLocation.volume_id) {
                                            z = true;
                                            if (z) {
                                                MessagesController.getInstance().deleteUserPhoto(null);
                                                PhotoViewer.this.closePhoto(false, false);
                                            } else if (photo2 != null) {
                                                tLRPC$TL_inputPhoto = new TLRPC$TL_inputPhoto();
                                                tLRPC$TL_inputPhoto.id = photo2.id;
                                                tLRPC$TL_inputPhoto.access_hash = photo2.access_hash;
                                                MessagesController.getInstance().deleteUserPhoto(tLRPC$TL_inputPhoto);
                                                MessagesStorage.getInstance().clearUserPhoto(PhotoViewer.this.avatarsDialogId, photo2.id);
                                                PhotoViewer.this.imagesArrLocations.remove(PhotoViewer.this.currentIndex);
                                                PhotoViewer.this.imagesArrLocationsSizes.remove(PhotoViewer.this.currentIndex);
                                                PhotoViewer.this.avatarsArr.remove(PhotoViewer.this.currentIndex);
                                                if (PhotoViewer.this.imagesArrLocations.isEmpty()) {
                                                    access$1100 = PhotoViewer.this.currentIndex;
                                                    if (access$1100 >= PhotoViewer.this.avatarsArr.size()) {
                                                        access$1100 = PhotoViewer.this.avatarsArr.size() - 1;
                                                    }
                                                    PhotoViewer.this.currentIndex = -1;
                                                    PhotoViewer.this.setImageIndex(access$1100, true);
                                                    return;
                                                }
                                                PhotoViewer.this.closePhoto(false, false);
                                            }
                                        }
                                    }
                                    z = false;
                                    if (z) {
                                        MessagesController.getInstance().deleteUserPhoto(null);
                                        PhotoViewer.this.closePhoto(false, false);
                                    } else if (photo2 != null) {
                                        tLRPC$TL_inputPhoto = new TLRPC$TL_inputPhoto();
                                        tLRPC$TL_inputPhoto.id = photo2.id;
                                        tLRPC$TL_inputPhoto.access_hash = photo2.access_hash;
                                        MessagesController.getInstance().deleteUserPhoto(tLRPC$TL_inputPhoto);
                                        MessagesStorage.getInstance().clearUserPhoto(PhotoViewer.this.avatarsDialogId, photo2.id);
                                        PhotoViewer.this.imagesArrLocations.remove(PhotoViewer.this.currentIndex);
                                        PhotoViewer.this.imagesArrLocationsSizes.remove(PhotoViewer.this.currentIndex);
                                        PhotoViewer.this.avatarsArr.remove(PhotoViewer.this.currentIndex);
                                        if (PhotoViewer.this.imagesArrLocations.isEmpty()) {
                                            access$1100 = PhotoViewer.this.currentIndex;
                                            if (access$1100 >= PhotoViewer.this.avatarsArr.size()) {
                                                access$1100 = PhotoViewer.this.avatarsArr.size() - 1;
                                            }
                                            PhotoViewer.this.currentIndex = -1;
                                            PhotoViewer.this.setImageIndex(access$1100, true);
                                            return;
                                        }
                                        PhotoViewer.this.closePhoto(false, false);
                                    }
                                }
                            } else if (PhotoViewer.this.currentIndex >= 0 && PhotoViewer.this.currentIndex < PhotoViewer.this.imagesArr.size()) {
                                MessageObject messageObject = (MessageObject) PhotoViewer.this.imagesArr.get(PhotoViewer.this.currentIndex);
                                if (messageObject.isSent()) {
                                    ArrayList arrayList;
                                    PhotoViewer.this.closePhoto(false, false);
                                    ArrayList arrayList2 = new ArrayList();
                                    if (PhotoViewer.this.slideshowMessageId != 0) {
                                        arrayList2.add(Integer.valueOf(PhotoViewer.this.slideshowMessageId));
                                    } else {
                                        arrayList2.add(Integer.valueOf(messageObject.getId()));
                                    }
                                    if (((int) messageObject.getDialogId()) != 0 || messageObject.messageOwner.random_id == 0) {
                                        arrayList = null;
                                    } else {
                                        arrayList = new ArrayList();
                                        arrayList.add(Long.valueOf(messageObject.messageOwner.random_id));
                                        encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (messageObject.getDialogId() >> 32)));
                                    }
                                    MessagesController.getInstance().deleteMessages(arrayList2, arrayList, encryptedChat, messageObject.messageOwner.to_id.channel_id, zArr[0]);
                                }
                            }
                        }
                    });
                    builder2.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    PhotoViewer.this.showAlertDialog(builder2);
                }
            } else if (i == 10) {
                PhotoViewer.this.onSharePressed();
            } else if (i == 11) {
                try {
                    AndroidUtilities.openForView(PhotoViewer.this.currentMessageObject, PhotoViewer.this.parentActivity);
                    PhotoViewer.this.closePhoto(false, false);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            } else if (i == 13 && PhotoViewer.this.parentActivity != null && PhotoViewer.this.currentMessageObject != null && PhotoViewer.this.currentMessageObject.messageOwner.media != null && PhotoViewer.this.currentMessageObject.messageOwner.media.photo != null) {
                new StickersAlert(PhotoViewer.this.parentActivity, PhotoViewer.this.currentMessageObject.messageOwner.media.photo).show();
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoViewer$6 */
    class C50746 implements OnClickListener {
        C50746() {
        }

        public void onClick(View view) {
            PhotoViewer.this.openCaptionEnter();
        }
    }

    /* renamed from: org.telegram.ui.PhotoViewer$7 */
    class C50757 implements OnClickListener {
        C50757() {
        }

        public void onClick(View view) {
            PhotoViewer.this.onSharePressed();
        }
    }

    /* renamed from: org.telegram.ui.PhotoViewer$8 */
    class C50768 implements SeekBarDelegate {
        C50768() {
        }

        public void onSeekBarDrag(float f) {
            if (PhotoViewer.this.videoPlayer != null) {
                if (!PhotoViewer.this.inPreview && PhotoViewer.this.videoTimelineView.getVisibility() == 0) {
                    f = PhotoViewer.this.videoTimelineView.getLeftProgress() + ((PhotoViewer.this.videoTimelineView.getRightProgress() - PhotoViewer.this.videoTimelineView.getLeftProgress()) * f);
                }
                PhotoViewer.this.videoPlayer.seekTo((long) ((int) (((float) PhotoViewer.this.videoPlayer.getDuration()) * f)));
            }
        }
    }

    private class BackgroundDrawable extends ColorDrawable {
        private boolean allowDrawContent;
        private Runnable drawRunnable;

        /* renamed from: org.telegram.ui.PhotoViewer$BackgroundDrawable$1 */
        class C50781 implements Runnable {
            C50781() {
            }

            public void run() {
                if (PhotoViewer.this.parentAlert != null) {
                    PhotoViewer.this.parentAlert.setAllowDrawContent(BackgroundDrawable.this.allowDrawContent);
                }
            }
        }

        public BackgroundDrawable(int i) {
            super(i);
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (getAlpha() != 0 && this.drawRunnable != null) {
                AndroidUtilities.runOnUIThread(this.drawRunnable);
                this.drawRunnable = null;
            }
        }

        public void setAlpha(int i) {
            if (PhotoViewer.this.parentActivity instanceof LaunchActivity) {
                boolean z = (PhotoViewer.this.isVisible && i == 255) ? false : true;
                this.allowDrawContent = z;
                ((LaunchActivity) PhotoViewer.this.parentActivity).drawerLayoutContainer.setAllowDrawContent(this.allowDrawContent);
                if (PhotoViewer.this.parentAlert != null) {
                    if (!this.allowDrawContent) {
                        AndroidUtilities.runOnUIThread(new C50781(), 50);
                    } else if (PhotoViewer.this.parentAlert != null) {
                        PhotoViewer.this.parentAlert.setAllowDrawContent(this.allowDrawContent);
                    }
                }
            }
            super.setAlpha(i);
        }
    }

    private class CounterView extends View {
        private int currentCount = 0;
        private int height;
        private Paint paint;
        private RectF rect;
        private float rotation;
        private StaticLayout staticLayout;
        private TextPaint textPaint = new TextPaint(1);
        private int width;

        public CounterView(Context context) {
            super(context);
            this.textPaint.setTextSize((float) AndroidUtilities.dp(18.0f));
            this.textPaint.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.textPaint.setColor(-1);
            this.paint = new Paint(1);
            this.paint.setColor(-1);
            this.paint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
            this.paint.setStyle(Style.STROKE);
            this.paint.setStrokeJoin(Join.ROUND);
            this.rect = new RectF();
            setCount(0);
        }

        public float getRotationX() {
            return this.rotation;
        }

        protected void onDraw(Canvas canvas) {
            int measuredHeight = getMeasuredHeight() / 2;
            this.paint.setAlpha(255);
            this.rect.set((float) AndroidUtilities.dp(1.0f), (float) (measuredHeight - AndroidUtilities.dp(14.0f)), (float) (getMeasuredWidth() - AndroidUtilities.dp(1.0f)), (float) (measuredHeight + AndroidUtilities.dp(14.0f)));
            canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(15.0f), (float) AndroidUtilities.dp(15.0f), this.paint);
            if (this.staticLayout != null) {
                this.textPaint.setAlpha((int) ((1.0f - this.rotation) * 255.0f));
                canvas.save();
                canvas.translate((float) ((getMeasuredWidth() - this.width) / 2), (((float) ((getMeasuredHeight() - this.height) / 2)) + AndroidUtilities.dpf2(0.2f)) + (this.rotation * ((float) AndroidUtilities.dp(5.0f))));
                this.staticLayout.draw(canvas);
                canvas.restore();
                this.paint.setAlpha((int) (this.rotation * 255.0f));
                int centerX = (int) this.rect.centerX();
                int centerY = (int) (((float) ((int) this.rect.centerY())) - ((((float) AndroidUtilities.dp(5.0f)) * (1.0f - this.rotation)) + ((float) AndroidUtilities.dp(3.0f))));
                canvas.drawLine((float) (AndroidUtilities.dp(0.5f) + centerX), (float) (centerY - AndroidUtilities.dp(0.5f)), (float) (centerX - AndroidUtilities.dp(6.0f)), (float) (AndroidUtilities.dp(6.0f) + centerY), this.paint);
                canvas.drawLine((float) (centerX - AndroidUtilities.dp(0.5f)), (float) (centerY - AndroidUtilities.dp(0.5f)), (float) (AndroidUtilities.dp(6.0f) + centerX), (float) (AndroidUtilities.dp(6.0f) + centerY), this.paint);
            }
        }

        protected void onMeasure(int i, int i2) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(Math.max(this.width + AndroidUtilities.dp(20.0f), AndroidUtilities.dp(30.0f)), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(40.0f), 1073741824));
        }

        public void setCount(int i) {
            this.staticLayout = new StaticLayout(TtmlNode.ANONYMOUS_REGION_ID + Math.max(1, i), this.textPaint, AndroidUtilities.dp(100.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
            this.width = (int) Math.ceil((double) this.staticLayout.getLineWidth(0));
            this.height = this.staticLayout.getLineBottom(0);
            AnimatorSet animatorSet = new AnimatorSet();
            if (i == 0) {
                Animator[] animatorArr = new Animator[4];
                animatorArr[0] = ObjectAnimator.ofFloat(this, "scaleX", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[1] = ObjectAnimator.ofFloat(this, "scaleY", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[2] = ObjectAnimator.ofInt(this.paint, "alpha", new int[]{0});
                animatorArr[3] = ObjectAnimator.ofInt(this.textPaint, "alpha", new int[]{0});
                animatorSet.playTogether(animatorArr);
                animatorSet.setInterpolator(new DecelerateInterpolator());
            } else if (this.currentCount == 0) {
                animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this, "scaleX", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this, "scaleY", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofInt(this.paint, "alpha", new int[]{0, 255}), ObjectAnimator.ofInt(this.textPaint, "alpha", new int[]{0, 255})});
                animatorSet.setInterpolator(new DecelerateInterpolator());
            } else if (i < this.currentCount) {
                animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this, "scaleX", new float[]{1.1f, 1.0f}), ObjectAnimator.ofFloat(this, "scaleY", new float[]{1.1f, 1.0f})});
                animatorSet.setInterpolator(new OvershootInterpolator());
            } else {
                animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this, "scaleX", new float[]{0.9f, 1.0f}), ObjectAnimator.ofFloat(this, "scaleY", new float[]{0.9f, 1.0f})});
                animatorSet.setInterpolator(new OvershootInterpolator());
            }
            animatorSet.setDuration(180);
            animatorSet.start();
            requestLayout();
            this.currentCount = i;
        }

        public void setRotationX(float f) {
            this.rotation = f;
            invalidate();
        }

        public void setScaleX(float f) {
            super.setScaleX(f);
            invalidate();
        }
    }

    private class FrameLayoutDrawer extends SizeNotifierFrameLayoutPhoto {
        private Paint paint = new Paint();

        public FrameLayoutDrawer(Context context) {
            super(context);
            setWillNotDraw(false);
            this.paint.setColor(855638016);
        }

        protected boolean drawChild(Canvas canvas, View view, long j) {
            if (view == PhotoViewer.this.mentionListView || view == PhotoViewer.this.captionEditText) {
                if (!PhotoViewer.this.captionEditText.isPopupShowing() && PhotoViewer.this.captionEditText.getEmojiPadding() == 0 && ((AndroidUtilities.usingHardwareInput && PhotoViewer.this.captionEditText.getTag() == null) || getKeyboardHeight() == 0)) {
                    return false;
                }
            } else if (view == PhotoViewer.this.pickerView || view == PhotoViewer.this.captionTextView || (PhotoViewer.this.muteItem.getVisibility() == 0 && view == PhotoViewer.this.bottomLayout)) {
                int emojiPadding = (getKeyboardHeight() > AndroidUtilities.dp(20.0f) || AndroidUtilities.isInMultiwindow) ? 0 : PhotoViewer.this.captionEditText.getEmojiPadding();
                if (PhotoViewer.this.captionEditText.isPopupShowing() || ((AndroidUtilities.usingHardwareInput && PhotoViewer.this.captionEditText.getTag() != null) || getKeyboardHeight() > 0 || emojiPadding != 0)) {
                    PhotoViewer.this.bottomTouchEnabled = false;
                    return false;
                }
                PhotoViewer.this.bottomTouchEnabled = true;
            } else if (view == PhotoViewer.this.checkImageView || view == PhotoViewer.this.photosCounterView) {
                if (PhotoViewer.this.captionEditText.getTag() != null) {
                    PhotoViewer.this.bottomTouchEnabled = false;
                    return false;
                }
                PhotoViewer.this.bottomTouchEnabled = true;
            }
            try {
                return view != PhotoViewer.this.aspectRatioFrameLayout && super.drawChild(canvas, view, j);
            } catch (Throwable th) {
                return true;
            }
        }

        protected void onDraw(Canvas canvas) {
            PhotoViewer.this.onDraw(canvas);
            if (VERSION.SDK_INT >= 21 && AndroidUtilities.statusBarHeight != 0) {
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) AndroidUtilities.statusBarHeight, this.paint);
            }
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int childCount = getChildCount();
            int emojiPadding = (getKeyboardHeight() > AndroidUtilities.dp(20.0f) || AndroidUtilities.isInMultiwindow) ? 0 : PhotoViewer.this.captionEditText.getEmojiPadding();
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                if (childAt.getVisibility() != 8) {
                    int i6;
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    int i7 = layoutParams.gravity;
                    if (i7 == -1) {
                        i7 = 51;
                    }
                    int i8 = i7 & 112;
                    switch ((i7 & 7) & 7) {
                        case 1:
                            i7 = ((((i3 - i) - measuredWidth) / 2) + layoutParams.leftMargin) - layoutParams.rightMargin;
                            break;
                        case 5:
                            i7 = ((i3 - i) - measuredWidth) - layoutParams.rightMargin;
                            break;
                        default:
                            i7 = layoutParams.leftMargin;
                            break;
                    }
                    switch (i8) {
                        case 16:
                            i6 = (((((i4 - emojiPadding) - i2) - measuredHeight) / 2) + layoutParams.topMargin) - layoutParams.bottomMargin;
                            break;
                        case 48:
                            i6 = layoutParams.topMargin;
                            break;
                        case 80:
                            i6 = (((i4 - emojiPadding) - i2) - measuredHeight) - layoutParams.bottomMargin;
                            break;
                        default:
                            i6 = layoutParams.topMargin;
                            break;
                    }
                    if (childAt == PhotoViewer.this.mentionListView) {
                        i6 -= PhotoViewer.this.captionEditText.getMeasuredHeight();
                    } else if (PhotoViewer.this.captionEditText.isPopupView(childAt)) {
                        i6 = AndroidUtilities.isInMultiwindow ? (PhotoViewer.this.captionEditText.getTop() - childAt.getMeasuredHeight()) + AndroidUtilities.dp(1.0f) : PhotoViewer.this.captionEditText.getBottom();
                    } else if (childAt == PhotoViewer.this.selectedPhotosListView) {
                        i6 = PhotoViewer.this.actionBar.getMeasuredHeight();
                    } else if (childAt == PhotoViewer.this.captionTextView) {
                        if (!PhotoViewer.this.groupedPhotosListView.currentPhotos.isEmpty()) {
                            i6 -= PhotoViewer.this.groupedPhotosListView.getMeasuredHeight();
                        }
                    } else if (PhotoViewer.this.hintTextView != null && childAt == PhotoViewer.this.hintTextView) {
                        i6 = PhotoViewer.this.selectedPhotosListView.getBottom() + AndroidUtilities.dp(3.0f);
                    }
                    childAt.layout(i7, i6, measuredWidth + i7, measuredHeight + i6);
                }
            }
            notifyHeightChanged();
        }

        protected void onMeasure(int i, int i2) {
            int size = MeasureSpec.getSize(i);
            int size2 = MeasureSpec.getSize(i2);
            setMeasuredDimension(size, size2);
            measureChildWithMargins(PhotoViewer.this.captionEditText, i, 0, i2, 0);
            int measuredHeight = PhotoViewer.this.captionEditText.getMeasuredHeight();
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (!(childAt.getVisibility() == 8 || childAt == PhotoViewer.this.captionEditText)) {
                    if (childAt == PhotoViewer.this.aspectRatioFrameLayout) {
                        measureChildWithMargins(childAt, i, 0, MeasureSpec.makeMeasureSpec((VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + AndroidUtilities.displaySize.y, 1073741824), 0);
                    } else if (!PhotoViewer.this.captionEditText.isPopupView(childAt)) {
                        measureChildWithMargins(childAt, i, 0, i2, 0);
                    } else if (!AndroidUtilities.isInMultiwindow) {
                        childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
                    } else if (AndroidUtilities.isTablet()) {
                        childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.dp(320.0f), (size2 - measuredHeight) - AndroidUtilities.statusBarHeight), 1073741824));
                    } else {
                        childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec((size2 - measuredHeight) - AndroidUtilities.statusBarHeight, 1073741824));
                    }
                }
            }
        }
    }

    private class GroupedPhotosListView extends View implements OnGestureListener {
        private boolean animateAllLine;
        private int animateToDX;
        private int animateToDXStart;
        private int animateToItem = -1;
        private Paint backgroundPaint = new Paint();
        private long currentGroupId;
        private int currentImage;
        private float currentItemProgress = 1.0f;
        private ArrayList<Object> currentObjects = new ArrayList();
        private ArrayList<TLObject> currentPhotos = new ArrayList();
        private int drawDx;
        private GestureDetector gestureDetector;
        private boolean ignoreChanges;
        private ArrayList<ImageReceiver> imagesToDraw = new ArrayList();
        private int itemHeight;
        private int itemSpacing;
        private int itemWidth;
        private int itemY;
        private long lastUpdateTime;
        private float moveLineProgress;
        private boolean moving;
        private int nextImage;
        private float nextItemProgress = BitmapDescriptorFactory.HUE_RED;
        private int nextPhotoScrolling = -1;
        private Scroller scroll;
        private boolean scrolling;
        private boolean stopedScrolling;
        private ArrayList<ImageReceiver> unusedReceivers = new ArrayList();

        public GroupedPhotosListView(Context context) {
            super(context);
            this.gestureDetector = new GestureDetector(context, this);
            this.scroll = new Scroller(context);
            this.itemWidth = AndroidUtilities.dp(42.0f);
            this.itemHeight = AndroidUtilities.dp(56.0f);
            this.itemSpacing = AndroidUtilities.dp(1.0f);
            this.itemY = AndroidUtilities.dp(3.0f);
            this.backgroundPaint.setColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
        }

        private void fillImages(boolean z, int i) {
            if (!(z || this.imagesToDraw.isEmpty())) {
                this.unusedReceivers.addAll(this.imagesToDraw);
                this.imagesToDraw.clear();
                this.moving = false;
                this.moveLineProgress = 1.0f;
                this.currentItemProgress = 1.0f;
                this.nextItemProgress = BitmapDescriptorFactory.HUE_RED;
            }
            invalidate();
            if (getMeasuredWidth() != 0 && !this.currentPhotos.isEmpty()) {
                int i2;
                int i3;
                ImageReceiver imageReceiver;
                int i4;
                TLObject tLObject;
                FileLocation fileLocation;
                TLObject tLObject2;
                int measuredWidth = getMeasuredWidth();
                int measuredWidth2 = (getMeasuredWidth() / 2) - (this.itemWidth / 2);
                if (z) {
                    int i5 = Integer.MIN_VALUE;
                    i2 = Integer.MAX_VALUE;
                    int size = this.imagesToDraw.size();
                    i3 = 0;
                    while (i3 < size) {
                        imageReceiver = (ImageReceiver) this.imagesToDraw.get(i3);
                        int param = imageReceiver.getParam();
                        int i6 = (((param - this.currentImage) * (this.itemWidth + this.itemSpacing)) + measuredWidth2) + i;
                        if (i6 > measuredWidth || i6 + this.itemWidth < 0) {
                            this.unusedReceivers.add(imageReceiver);
                            this.imagesToDraw.remove(i3);
                            i4 = i3 - 1;
                            i3 = size - 1;
                        } else {
                            i4 = i3;
                            i3 = size;
                        }
                        i2 = Math.min(i2, param - 1);
                        i5 = Math.max(i5, param + 1);
                        size = i3;
                        i3 = i4 + 1;
                    }
                    i4 = i5;
                } else {
                    i4 = this.currentImage;
                    i2 = this.currentImage - 1;
                }
                if (i4 != Integer.MIN_VALUE) {
                    int size2 = this.currentPhotos.size();
                    for (int i7 = i4; i7 < size2; i7++) {
                        i3 = (((i7 - this.currentImage) * (this.itemWidth + this.itemSpacing)) + measuredWidth2) + i;
                        if (i3 >= measuredWidth) {
                            break;
                        }
                        tLObject = (TLObject) this.currentPhotos.get(i7);
                        if (tLObject instanceof PhotoSize) {
                            fileLocation = ((PhotoSize) tLObject).location;
                        } else {
                            tLObject2 = tLObject;
                        }
                        imageReceiver = getFreeReceiver();
                        imageReceiver.setImageCoords(i3, this.itemY, this.itemWidth, this.itemHeight);
                        imageReceiver.setImage(null, null, null, null, fileLocation, "80_80", 0, null, 1);
                        imageReceiver.setParam(i7);
                    }
                }
                if (i2 != Integer.MAX_VALUE) {
                    while (i2 >= 0) {
                        i3 = this.itemWidth + ((((i2 - this.currentImage) * (this.itemWidth + this.itemSpacing)) + measuredWidth2) + i);
                        if (i3 > 0) {
                            tLObject = (TLObject) this.currentPhotos.get(i2);
                            if (tLObject instanceof PhotoSize) {
                                fileLocation = ((PhotoSize) tLObject).location;
                            } else {
                                tLObject2 = tLObject;
                            }
                            imageReceiver = getFreeReceiver();
                            imageReceiver.setImageCoords(i3, this.itemY, this.itemWidth, this.itemHeight);
                            imageReceiver.setImage(null, null, null, null, fileLocation, "80_80", 0, null, 1);
                            imageReceiver.setParam(i2);
                            i2--;
                        } else {
                            return;
                        }
                    }
                }
            }
        }

        private ImageReceiver getFreeReceiver() {
            ImageReceiver imageReceiver;
            if (this.unusedReceivers.isEmpty()) {
                imageReceiver = new ImageReceiver(this);
            } else {
                imageReceiver = (ImageReceiver) this.unusedReceivers.get(0);
                this.unusedReceivers.remove(0);
            }
            this.imagesToDraw.add(imageReceiver);
            return imageReceiver;
        }

        private int getMaxScrollX() {
            return this.currentImage * (this.itemWidth + (this.itemSpacing * 2));
        }

        private int getMinScrollX() {
            return (-((this.currentPhotos.size() - this.currentImage) - 1)) * (this.itemWidth + (this.itemSpacing * 2));
        }

        private void stopScrolling() {
            this.scrolling = false;
            if (!this.scroll.isFinished()) {
                this.scroll.abortAnimation();
            }
            if (this.nextPhotoScrolling >= 0 && this.nextPhotoScrolling < this.currentObjects.size()) {
                this.stopedScrolling = true;
                int i = this.nextPhotoScrolling;
                this.animateToItem = i;
                this.nextImage = i;
                this.animateToDX = (this.currentImage - this.nextPhotoScrolling) * (this.itemWidth + this.itemSpacing);
                this.animateToDXStart = this.drawDx;
                this.moveLineProgress = 1.0f;
                this.nextPhotoScrolling = -1;
            }
            invalidate();
        }

        private void updateAfterScroll() {
            int i = this.drawDx;
            if (Math.abs(i) > (this.itemWidth / 2) + this.itemSpacing) {
                int i2;
                if (i > 0) {
                    i -= (this.itemWidth / 2) + this.itemSpacing;
                    i2 = 1;
                } else {
                    i += (this.itemWidth / 2) + this.itemSpacing;
                    i2 = -1;
                }
                i = (i / (this.itemWidth + (this.itemSpacing * 2))) + i2;
            } else {
                i = 0;
            }
            this.nextPhotoScrolling = this.currentImage - i;
            if (PhotoViewer.this.currentIndex != this.nextPhotoScrolling && this.nextPhotoScrolling >= 0 && this.nextPhotoScrolling < this.currentPhotos.size()) {
                Object obj = this.currentObjects.get(this.nextPhotoScrolling);
                if (!PhotoViewer.this.imagesArr.isEmpty()) {
                    i = PhotoViewer.this.imagesArr.indexOf((MessageObject) obj);
                } else if (PhotoViewer.this.imagesArrLocations.isEmpty()) {
                    i = -1;
                } else {
                    i = PhotoViewer.this.imagesArrLocations.indexOf((FileLocation) obj);
                }
                if (i >= 0) {
                    this.ignoreChanges = true;
                    PhotoViewer.this.currentIndex = -1;
                    PhotoViewer.this.setImageIndex(i, false);
                }
            }
            if (!this.scrolling) {
                this.scrolling = true;
                this.stopedScrolling = false;
            }
            fillImages(true, this.drawDx);
        }

        public void clear() {
            this.currentPhotos.clear();
            this.currentObjects.clear();
            this.imagesToDraw.clear();
        }

        public void fillList() {
            if (this.ignoreChanges) {
                this.ignoreChanges = false;
                return;
            }
            int size;
            Object obj;
            boolean z;
            MessageObject messageObject;
            if (!PhotoViewer.this.imagesArrLocations.isEmpty()) {
                FileLocation fileLocation = (FileLocation) PhotoViewer.this.imagesArrLocations.get(PhotoViewer.this.currentIndex);
                size = PhotoViewer.this.imagesArrLocations.size();
                obj = fileLocation;
                z = false;
            } else if (PhotoViewer.this.imagesArr.isEmpty()) {
                obj = null;
                size = 0;
                z = false;
            } else {
                messageObject = (MessageObject) PhotoViewer.this.imagesArr.get(PhotoViewer.this.currentIndex);
                MessageObject messageObject2;
                if (messageObject.messageOwner.grouped_id != this.currentGroupId) {
                    this.currentGroupId = messageObject.messageOwner.grouped_id;
                    messageObject2 = messageObject;
                    size = 0;
                    z = true;
                } else {
                    int access$1100;
                    int min = Math.min(PhotoViewer.this.currentIndex + 10, PhotoViewer.this.imagesArr.size());
                    size = 0;
                    for (access$1100 = PhotoViewer.this.currentIndex; access$1100 < min; access$1100++) {
                        messageObject2 = (MessageObject) PhotoViewer.this.imagesArr.get(access$1100);
                        if (PhotoViewer.this.slideshowMessageId == 0 && messageObject2.messageOwner.grouped_id != this.currentGroupId) {
                            break;
                        }
                        size++;
                    }
                    min = Math.max(PhotoViewer.this.currentIndex - 10, 0);
                    for (access$1100 = PhotoViewer.this.currentIndex - 1; access$1100 >= min; access$1100--) {
                        messageObject2 = (MessageObject) PhotoViewer.this.imagesArr.get(access$1100);
                        if (PhotoViewer.this.slideshowMessageId == 0 && messageObject2.messageOwner.grouped_id != this.currentGroupId) {
                            break;
                        }
                        size++;
                    }
                    messageObject2 = messageObject;
                    z = false;
                }
            }
            if (obj != null) {
                int indexOf;
                if (!z) {
                    if (size != this.currentPhotos.size() || this.currentObjects.indexOf(obj) == -1) {
                        z = true;
                    } else {
                        indexOf = this.currentObjects.indexOf(obj);
                        if (!(this.currentImage == indexOf || indexOf == -1)) {
                            if (this.animateAllLine) {
                                this.animateToItem = indexOf;
                                this.nextImage = indexOf;
                                this.animateToDX = (this.currentImage - indexOf) * (this.itemWidth + this.itemSpacing);
                                this.moving = true;
                                this.animateAllLine = false;
                                this.lastUpdateTime = System.currentTimeMillis();
                                invalidate();
                            } else {
                                fillImages(true, (this.currentImage - indexOf) * (this.itemWidth + this.itemSpacing));
                                this.currentImage = indexOf;
                                this.moving = false;
                            }
                            this.drawDx = 0;
                        }
                    }
                }
                if (z) {
                    this.animateAllLine = false;
                    this.currentPhotos.clear();
                    this.currentObjects.clear();
                    if (!PhotoViewer.this.imagesArrLocations.isEmpty()) {
                        this.currentObjects.addAll(PhotoViewer.this.imagesArrLocations);
                        this.currentPhotos.addAll(PhotoViewer.this.imagesArrLocations);
                        this.currentImage = PhotoViewer.this.currentIndex;
                        this.animateToItem = -1;
                    } else if (!PhotoViewer.this.imagesArr.isEmpty() && (this.currentGroupId != 0 || PhotoViewer.this.slideshowMessageId != 0)) {
                        size = Math.min(PhotoViewer.this.currentIndex + 10, PhotoViewer.this.imagesArr.size());
                        for (indexOf = PhotoViewer.this.currentIndex; indexOf < size; indexOf++) {
                            messageObject = (MessageObject) PhotoViewer.this.imagesArr.get(indexOf);
                            if (PhotoViewer.this.slideshowMessageId == 0 && messageObject.messageOwner.grouped_id != this.currentGroupId) {
                                break;
                            }
                            this.currentObjects.add(messageObject);
                            this.currentPhotos.add(FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 56, true));
                        }
                        this.currentImage = 0;
                        this.animateToItem = -1;
                        size = Math.max(PhotoViewer.this.currentIndex - 10, 0);
                        for (indexOf = PhotoViewer.this.currentIndex - 1; indexOf >= size; indexOf--) {
                            messageObject = (MessageObject) PhotoViewer.this.imagesArr.get(indexOf);
                            if (PhotoViewer.this.slideshowMessageId == 0 && messageObject.messageOwner.grouped_id != this.currentGroupId) {
                                break;
                            }
                            this.currentObjects.add(0, messageObject);
                            this.currentPhotos.add(0, FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 56, true));
                            this.currentImage++;
                        }
                    }
                    if (this.currentPhotos.size() == 1) {
                        this.currentPhotos.clear();
                        this.currentObjects.clear();
                    }
                    fillImages(false, 0);
                }
            }
        }

        public boolean onDown(MotionEvent motionEvent) {
            if (!this.scroll.isFinished()) {
                this.scroll.abortAnimation();
            }
            this.animateToItem = -1;
            return true;
        }

        protected void onDraw(Canvas canvas) {
            if (!this.imagesToDraw.isEmpty()) {
                PhotoSize photoSize;
                int max;
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) getMeasuredHeight(), this.backgroundPaint);
                int size = this.imagesToDraw.size();
                int i = this.drawDx;
                int i2 = (int) (((float) this.itemWidth) * 2.0f);
                int dp = AndroidUtilities.dp(8.0f);
                TLObject tLObject = (TLObject) this.currentPhotos.get(this.currentImage);
                if (tLObject instanceof PhotoSize) {
                    photoSize = (PhotoSize) tLObject;
                    max = Math.max(this.itemWidth, (int) ((((float) this.itemHeight) / ((float) photoSize.f10146h)) * ((float) photoSize.f10147w)));
                } else {
                    max = this.itemHeight;
                }
                int i3 = (int) (((float) (dp * 2)) * this.currentItemProgress);
                int min = (((int) (((float) (Math.min(i2, max) - this.itemWidth)) * this.currentItemProgress)) + this.itemWidth) + i3;
                if (this.nextImage < 0 || this.nextImage >= this.currentPhotos.size()) {
                    max = this.itemWidth;
                } else {
                    tLObject = (TLObject) this.currentPhotos.get(this.nextImage);
                    if (tLObject instanceof PhotoSize) {
                        photoSize = (PhotoSize) tLObject;
                        max = Math.max(this.itemWidth, (int) ((((float) this.itemHeight) / ((float) photoSize.f10146h)) * ((float) photoSize.f10147w)));
                    } else {
                        max = this.itemHeight;
                    }
                }
                i2 = Math.min(i2, max);
                dp = (int) (((float) (dp * 2)) * this.nextItemProgress);
                int i4 = (int) ((((float) (this.nextImage > this.currentImage ? -1 : 1)) * (this.nextItemProgress * ((float) (((i2 + dp) - this.itemWidth) / 2)))) + ((float) i));
                i2 = (this.itemWidth + ((int) (((float) (i2 - this.itemWidth)) * this.nextItemProgress))) + dp;
                int measuredWidth = (getMeasuredWidth() - min) / 2;
                for (i = 0; i < size; i++) {
                    ImageReceiver imageReceiver = (ImageReceiver) this.imagesToDraw.get(i);
                    int param = imageReceiver.getParam();
                    if (param == this.currentImage) {
                        imageReceiver.setImageX((measuredWidth + i4) + (i3 / 2));
                        imageReceiver.setImageWidth(min - i3);
                    } else {
                        if (this.nextImage < this.currentImage) {
                            if (param >= this.currentImage) {
                                imageReceiver.setImageX((((measuredWidth + min) + this.itemSpacing) + (((imageReceiver.getParam() - this.currentImage) - 1) * (this.itemWidth + this.itemSpacing))) + i4);
                            } else if (param <= this.nextImage) {
                                imageReceiver.setImageX((((((imageReceiver.getParam() - this.currentImage) + 1) * (this.itemWidth + this.itemSpacing)) + measuredWidth) - (this.itemSpacing + i2)) + i4);
                            } else {
                                imageReceiver.setImageX((((imageReceiver.getParam() - this.currentImage) * (this.itemWidth + this.itemSpacing)) + measuredWidth) + i4);
                            }
                        } else if (param < this.currentImage) {
                            imageReceiver.setImageX((((imageReceiver.getParam() - this.currentImage) * (this.itemWidth + this.itemSpacing)) + measuredWidth) + i4);
                        } else if (param <= this.nextImage) {
                            imageReceiver.setImageX((((measuredWidth + min) + this.itemSpacing) + (((imageReceiver.getParam() - this.currentImage) - 1) * (this.itemWidth + this.itemSpacing))) + i4);
                        } else {
                            imageReceiver.setImageX(((((measuredWidth + min) + this.itemSpacing) + (((imageReceiver.getParam() - this.currentImage) - 2) * (this.itemWidth + this.itemSpacing))) + (this.itemSpacing + i2)) + i4);
                        }
                        if (param == this.nextImage) {
                            imageReceiver.setImageWidth(i2 - dp);
                            imageReceiver.setImageX(imageReceiver.getImageX() + (dp / 2));
                        } else {
                            imageReceiver.setImageWidth(this.itemWidth);
                        }
                    }
                    imageReceiver.draw(canvas);
                }
                long currentTimeMillis = System.currentTimeMillis();
                long j = currentTimeMillis - this.lastUpdateTime;
                if (j > 17) {
                    j = 17;
                }
                this.lastUpdateTime = currentTimeMillis;
                if (this.animateToItem >= 0) {
                    if (this.moveLineProgress > BitmapDescriptorFactory.HUE_RED) {
                        this.moveLineProgress -= ((float) j) / 200.0f;
                        if (this.animateToItem == this.currentImage) {
                            if (this.currentItemProgress < 1.0f) {
                                this.currentItemProgress += ((float) j) / 200.0f;
                                if (this.currentItemProgress > 1.0f) {
                                    this.currentItemProgress = 1.0f;
                                }
                            }
                            this.drawDx = this.animateToDXStart + ((int) Math.ceil((double) (this.currentItemProgress * ((float) (this.animateToDX - this.animateToDXStart)))));
                        } else {
                            this.nextItemProgress = CubicBezierInterpolator.EASE_OUT.getInterpolation(1.0f - this.moveLineProgress);
                            if (this.stopedScrolling) {
                                if (this.currentItemProgress > BitmapDescriptorFactory.HUE_RED) {
                                    this.currentItemProgress -= ((float) j) / 200.0f;
                                    if (this.currentItemProgress < BitmapDescriptorFactory.HUE_RED) {
                                        this.currentItemProgress = BitmapDescriptorFactory.HUE_RED;
                                    }
                                }
                                this.drawDx = this.animateToDXStart + ((int) Math.ceil((double) (this.nextItemProgress * ((float) (this.animateToDX - this.animateToDXStart)))));
                            } else {
                                this.currentItemProgress = CubicBezierInterpolator.EASE_OUT.getInterpolation(this.moveLineProgress);
                                this.drawDx = (int) Math.ceil((double) (this.nextItemProgress * ((float) this.animateToDX)));
                            }
                        }
                        if (this.moveLineProgress <= BitmapDescriptorFactory.HUE_RED) {
                            this.currentImage = this.animateToItem;
                            this.moveLineProgress = 1.0f;
                            this.currentItemProgress = 1.0f;
                            this.nextItemProgress = BitmapDescriptorFactory.HUE_RED;
                            this.moving = false;
                            this.stopedScrolling = false;
                            this.drawDx = 0;
                            this.animateToItem = -1;
                        }
                    }
                    fillImages(true, this.drawDx);
                    invalidate();
                }
                if (this.scrolling && this.currentItemProgress > BitmapDescriptorFactory.HUE_RED) {
                    this.currentItemProgress -= ((float) j) / 200.0f;
                    if (this.currentItemProgress < BitmapDescriptorFactory.HUE_RED) {
                        this.currentItemProgress = BitmapDescriptorFactory.HUE_RED;
                    }
                    invalidate();
                }
                if (!this.scroll.isFinished()) {
                    if (this.scroll.computeScrollOffset()) {
                        this.drawDx = this.scroll.getCurrX();
                        updateAfterScroll();
                        invalidate();
                    }
                    if (this.scroll.isFinished()) {
                        stopScrolling();
                    }
                }
            }
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            this.scroll.abortAnimation();
            if (this.currentPhotos.size() >= 10) {
                this.scroll.fling(this.drawDx, 0, Math.round(f), 0, getMinScrollX(), getMaxScrollX(), 0, 0);
            }
            return false;
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            fillImages(false, 0);
        }

        public void onLongPress(MotionEvent motionEvent) {
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            this.drawDx = (int) (((float) this.drawDx) - f);
            int minScrollX = getMinScrollX();
            int maxScrollX = getMaxScrollX();
            if (this.drawDx < minScrollX) {
                this.drawDx = minScrollX;
            } else if (this.drawDx > maxScrollX) {
                this.drawDx = maxScrollX;
            }
            updateAfterScroll();
            return false;
        }

        public void onShowPress(MotionEvent motionEvent) {
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            stopScrolling();
            int size = this.imagesToDraw.size();
            for (int i = 0; i < size; i++) {
                ImageReceiver imageReceiver = (ImageReceiver) this.imagesToDraw.get(i);
                if (imageReceiver.isInsideImage(motionEvent.getX(), motionEvent.getY())) {
                    int param = imageReceiver.getParam();
                    if (param < 0 || param >= this.currentObjects.size()) {
                        return true;
                    }
                    if (!PhotoViewer.this.imagesArr.isEmpty()) {
                        param = PhotoViewer.this.imagesArr.indexOf((MessageObject) this.currentObjects.get(param));
                        if (PhotoViewer.this.currentIndex == param) {
                            return true;
                        }
                        this.moveLineProgress = 1.0f;
                        this.animateAllLine = true;
                        PhotoViewer.this.currentIndex = -1;
                        PhotoViewer.this.setImageIndex(param, false);
                    } else if (!PhotoViewer.this.imagesArrLocations.isEmpty()) {
                        param = PhotoViewer.this.imagesArrLocations.indexOf((FileLocation) this.currentObjects.get(param));
                        if (PhotoViewer.this.currentIndex == param) {
                            return true;
                        }
                        this.moveLineProgress = 1.0f;
                        this.animateAllLine = true;
                        PhotoViewer.this.currentIndex = -1;
                        PhotoViewer.this.setImageIndex(param, false);
                    }
                    return false;
                }
            }
            return false;
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            boolean z = this.gestureDetector.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent);
            if (this.scrolling && motionEvent.getAction() == 1 && this.scroll.isFinished()) {
                stopScrolling();
            }
            return z;
        }

        public void setMoveProgress(float f) {
            if (!this.scrolling && this.animateToItem < 0) {
                if (f > BitmapDescriptorFactory.HUE_RED) {
                    this.nextImage = this.currentImage - 1;
                } else {
                    this.nextImage = this.currentImage + 1;
                }
                if (this.nextImage < 0 || this.nextImage >= this.currentPhotos.size()) {
                    this.currentItemProgress = 1.0f;
                } else {
                    this.currentItemProgress = 1.0f - Math.abs(f);
                }
                this.nextItemProgress = 1.0f - this.currentItemProgress;
                this.moving = f != BitmapDescriptorFactory.HUE_RED;
                invalidate();
                if (!this.currentPhotos.isEmpty()) {
                    if (f < BitmapDescriptorFactory.HUE_RED && this.currentImage == this.currentPhotos.size() - 1) {
                        return;
                    }
                    if (f <= BitmapDescriptorFactory.HUE_RED || this.currentImage != 0) {
                        this.drawDx = (int) (((float) (this.itemWidth + this.itemSpacing)) * f);
                        fillImages(true, this.drawDx);
                    }
                }
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.PhotoViewer$ListAdapter$1 */
        class C50791 implements OnClickListener {
            C50791() {
            }

            public void onClick(View view) {
                int indexOf = PhotoViewer.this.imagesArrLocals.indexOf(((View) view.getParent()).getTag());
                if (indexOf >= 0) {
                    int photoChecked = PhotoViewer.this.placeProvider.setPhotoChecked(indexOf, PhotoViewer.this.getCurrentVideoEditedInfo());
                    PhotoViewer.this.placeProvider.isPhotoChecked(indexOf);
                    if (indexOf == PhotoViewer.this.currentIndex) {
                        PhotoViewer.this.checkImageView.setChecked(false, true);
                    }
                    if (photoChecked >= 0) {
                        if (PhotoViewer.this.placeProvider.allowGroupPhotos()) {
                            photoChecked++;
                        }
                        PhotoViewer.this.selectedPhotosAdapter.notifyItemRemoved(photoChecked);
                    }
                    PhotoViewer.this.updateSelectedCount();
                }
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return (PhotoViewer.this.placeProvider == null || PhotoViewer.this.placeProvider.getSelectedPhotosOrder() == null) ? 0 : PhotoViewer.this.placeProvider.allowGroupPhotos() ? PhotoViewer.this.placeProvider.getSelectedPhotosOrder().size() + 1 : PhotoViewer.this.placeProvider.getSelectedPhotosOrder().size();
        }

        public int getItemViewType(int i) {
            return (i == 0 && PhotoViewer.this.placeProvider.allowGroupPhotos()) ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    PhotoPickerPhotoCell photoPickerPhotoCell = (PhotoPickerPhotoCell) viewHolder.itemView;
                    photoPickerPhotoCell.itemWidth = AndroidUtilities.dp(82.0f);
                    BackupImageView backupImageView = photoPickerPhotoCell.photoImage;
                    backupImageView.setOrientation(0, true);
                    ArrayList selectedPhotosOrder = PhotoViewer.this.placeProvider.getSelectedPhotosOrder();
                    if (PhotoViewer.this.placeProvider.allowGroupPhotos()) {
                        i--;
                    }
                    Object obj = PhotoViewer.this.placeProvider.getSelectedPhotos().get(selectedPhotosOrder.get(i));
                    if (obj instanceof PhotoEntry) {
                        PhotoEntry photoEntry = (PhotoEntry) obj;
                        photoPickerPhotoCell.setTag(photoEntry);
                        photoPickerPhotoCell.videoInfoContainer.setVisibility(4);
                        if (photoEntry.thumbPath != null) {
                            backupImageView.setImage(photoEntry.thumbPath, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                        } else if (photoEntry.path != null) {
                            backupImageView.setOrientation(photoEntry.orientation, true);
                            if (photoEntry.isVideo) {
                                photoPickerPhotoCell.videoInfoContainer.setVisibility(0);
                                int i2 = photoEntry.duration - ((photoEntry.duration / 60) * 60);
                                photoPickerPhotoCell.videoTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(r4), Integer.valueOf(i2)}));
                                backupImageView.setImage("vthumb://" + photoEntry.imageId + ":" + photoEntry.path, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                            } else {
                                backupImageView.setImage("thumb://" + photoEntry.imageId + ":" + photoEntry.path, null, this.mContext.getResources().getDrawable(R.drawable.nophotos));
                            }
                        } else {
                            backupImageView.setImageResource(R.drawable.nophotos);
                        }
                        photoPickerPhotoCell.setChecked(-1, true, false);
                        photoPickerPhotoCell.checkBox.setVisibility(0);
                        return;
                    } else if (obj instanceof SearchImage) {
                        SearchImage searchImage = (SearchImage) obj;
                        photoPickerPhotoCell.setTag(searchImage);
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
                        photoPickerPhotoCell.setChecked(-1, true, false);
                        photoPickerPhotoCell.checkBox.setVisibility(0);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    ((ImageView) viewHolder.itemView).setColorFilter(MediaController.getInstance().isGroupPhotosEnabled() ? new PorterDuffColorFilter(-10043398, Mode.MULTIPLY) : null);
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View photoPickerPhotoCell;
            switch (i) {
                case 0:
                    photoPickerPhotoCell = new PhotoPickerPhotoCell(this.mContext, false);
                    photoPickerPhotoCell.checkFrame.setOnClickListener(new C50791());
                    break;
                default:
                    photoPickerPhotoCell = new ImageView(this.mContext) {
                        protected void onMeasure(int i, int i2) {
                            super.onMeasure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(66.0f), 1073741824), MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i2), 1073741824));
                        }
                    };
                    photoPickerPhotoCell.setScaleType(ScaleType.CENTER);
                    photoPickerPhotoCell.setImageResource(R.drawable.photos_group);
                    break;
            }
            return new Holder(photoPickerPhotoCell);
        }
    }

    private class PhotoProgressView {
        private float alpha = 1.0f;
        private float animatedAlphaValue = 1.0f;
        private float animatedProgressValue = BitmapDescriptorFactory.HUE_RED;
        private float animationProgressStart = BitmapDescriptorFactory.HUE_RED;
        private int backgroundState = -1;
        private float currentProgress = BitmapDescriptorFactory.HUE_RED;
        private long currentProgressTime = 0;
        private long lastUpdateTime = 0;
        private View parent = null;
        private int previousBackgroundState = -2;
        private RectF progressRect = new RectF();
        private float radOffset = BitmapDescriptorFactory.HUE_RED;
        private float scale = 1.0f;
        private int size = AndroidUtilities.dp(64.0f);

        public PhotoProgressView(Context context, View view) {
            if (PhotoViewer.decelerateInterpolator == null) {
                PhotoViewer.decelerateInterpolator = new DecelerateInterpolator(1.5f);
                PhotoViewer.progressPaint = new Paint(1);
                PhotoViewer.progressPaint.setStyle(Style.STROKE);
                PhotoViewer.progressPaint.setStrokeCap(Cap.ROUND);
                PhotoViewer.progressPaint.setStrokeWidth((float) AndroidUtilities.dp(3.0f));
                PhotoViewer.progressPaint.setColor(-1);
            }
            this.parent = view;
        }

        private void updateAnimation() {
            long currentTimeMillis = System.currentTimeMillis();
            long j = currentTimeMillis - this.lastUpdateTime;
            this.lastUpdateTime = currentTimeMillis;
            if (this.animatedProgressValue != 1.0f) {
                this.radOffset += ((float) (360 * j)) / 3000.0f;
                float f = this.currentProgress - this.animationProgressStart;
                if (f > BitmapDescriptorFactory.HUE_RED) {
                    this.currentProgressTime += j;
                    if (this.currentProgressTime >= 300) {
                        this.animatedProgressValue = this.currentProgress;
                        this.animationProgressStart = this.currentProgress;
                        this.currentProgressTime = 0;
                    } else {
                        this.animatedProgressValue = (f * PhotoViewer.decelerateInterpolator.getInterpolation(((float) this.currentProgressTime) / 300.0f)) + this.animationProgressStart;
                    }
                }
                this.parent.invalidate();
            }
            if (this.animatedProgressValue >= 1.0f && this.previousBackgroundState != -2) {
                this.animatedAlphaValue -= ((float) j) / 200.0f;
                if (this.animatedAlphaValue <= BitmapDescriptorFactory.HUE_RED) {
                    this.animatedAlphaValue = BitmapDescriptorFactory.HUE_RED;
                    this.previousBackgroundState = -2;
                }
                this.parent.invalidate();
            }
        }

        public void onDraw(Canvas canvas) {
            Drawable drawable;
            int i = (int) (((float) this.size) * this.scale);
            int access$2100 = (PhotoViewer.this.getContainerViewWidth() - i) / 2;
            int access$2200 = (PhotoViewer.this.getContainerViewHeight() - i) / 2;
            if (this.previousBackgroundState >= 0 && this.previousBackgroundState < 4) {
                drawable = PhotoViewer.progressDrawables[this.previousBackgroundState];
                if (drawable != null) {
                    drawable.setAlpha((int) ((this.animatedAlphaValue * 255.0f) * this.alpha));
                    drawable.setBounds(access$2100, access$2200, access$2100 + i, access$2200 + i);
                    drawable.draw(canvas);
                }
            }
            if (this.backgroundState >= 0 && this.backgroundState < 4) {
                drawable = PhotoViewer.progressDrawables[this.backgroundState];
                if (drawable != null) {
                    if (this.previousBackgroundState != -2) {
                        drawable.setAlpha((int) (((1.0f - this.animatedAlphaValue) * 255.0f) * this.alpha));
                    } else {
                        drawable.setAlpha((int) (this.alpha * 255.0f));
                    }
                    drawable.setBounds(access$2100, access$2200, access$2100 + i, access$2200 + i);
                    drawable.draw(canvas);
                }
            }
            if (this.backgroundState == 0 || this.backgroundState == 1 || this.previousBackgroundState == 0 || this.previousBackgroundState == 1) {
                int dp = AndroidUtilities.dp(4.0f);
                if (this.previousBackgroundState != -2) {
                    PhotoViewer.progressPaint.setAlpha((int) ((this.animatedAlphaValue * 255.0f) * this.alpha));
                } else {
                    PhotoViewer.progressPaint.setAlpha((int) (this.alpha * 255.0f));
                }
                this.progressRect.set((float) (access$2100 + dp), (float) (access$2200 + dp), (float) ((access$2100 + i) - dp), (float) ((i + access$2200) - dp));
                canvas.drawArc(this.progressRect, this.radOffset - 0.049804688f, Math.max(4.0f, 360.0f * this.animatedProgressValue), false, PhotoViewer.progressPaint);
                updateAnimation();
            }
        }

        public void setAlpha(float f) {
            this.alpha = f;
        }

        public void setBackgroundState(int i, boolean z) {
            this.lastUpdateTime = System.currentTimeMillis();
            if (!z || this.backgroundState == i) {
                this.previousBackgroundState = -2;
            } else {
                this.previousBackgroundState = this.backgroundState;
                this.animatedAlphaValue = 1.0f;
            }
            this.backgroundState = i;
            this.parent.invalidate();
        }

        public void setProgress(float f, boolean z) {
            if (z) {
                this.animationProgressStart = this.animatedProgressValue;
            } else {
                this.animatedProgressValue = f;
                this.animationProgressStart = f;
            }
            this.currentProgress = f;
            this.currentProgressTime = 0;
        }

        public void setScale(float f) {
            this.scale = f;
        }
    }

    public static class PlaceProviderObject {
        public int clipBottomAddition;
        public int clipTopAddition;
        public int dialogId;
        public ImageReceiver imageReceiver;
        public int index;
        public boolean isEvent;
        public View parentView;
        public int radius;
        public float scale = 1.0f;
        public int size;
        public Bitmap thumb;
        public int viewX;
        public int viewY;
    }

    private class QualityChooseView extends View {
        private int circleSize;
        private int gapSize;
        private int lineSize;
        private boolean moving;
        private Paint paint = new Paint(1);
        private int sideSide;
        private boolean startMoving;
        private int startMovingQuality;
        private float startX;
        private TextPaint textPaint = new TextPaint(1);

        public QualityChooseView(Context context) {
            super(context);
            this.textPaint.setTextSize((float) AndroidUtilities.dp(12.0f));
            this.textPaint.setColor(-3289651);
        }

        protected void onDraw(Canvas canvas) {
            if (PhotoViewer.this.compressionsCount != 1) {
                this.lineSize = (((getMeasuredWidth() - (this.circleSize * PhotoViewer.this.compressionsCount)) - (this.gapSize * 8)) - (this.sideSide * 2)) / (PhotoViewer.this.compressionsCount - 1);
            } else {
                this.lineSize = ((getMeasuredWidth() - (this.circleSize * PhotoViewer.this.compressionsCount)) - (this.gapSize * 8)) - (this.sideSide * 2);
            }
            int measuredHeight = (getMeasuredHeight() / 2) + AndroidUtilities.dp(6.0f);
            int i = 0;
            while (i < PhotoViewer.this.compressionsCount) {
                int i2 = (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * i)) + (this.circleSize / 2);
                if (i <= PhotoViewer.this.selectedCompression) {
                    this.paint.setColor(-11292945);
                } else {
                    this.paint.setColor(1728053247);
                }
                r0 = i == PhotoViewer.this.compressionsCount + -1 ? Math.min(PhotoViewer.this.originalWidth, PhotoViewer.this.originalHeight) + TtmlNode.TAG_P : i == 0 ? "240p" : i == 1 ? "360p" : i == 2 ? "480p" : "720p";
                float measureText = this.textPaint.measureText(r0);
                canvas.drawCircle((float) i2, (float) measuredHeight, i == PhotoViewer.this.selectedCompression ? (float) AndroidUtilities.dp(8.0f) : (float) (this.circleSize / 2), this.paint);
                canvas.drawText(r0, ((float) i2) - (measureText / 2.0f), (float) (measuredHeight - AndroidUtilities.dp(16.0f)), this.textPaint);
                if (i != 0) {
                    int i3 = ((i2 - (this.circleSize / 2)) - this.gapSize) - this.lineSize;
                    canvas.drawRect((float) i3, (float) (measuredHeight - AndroidUtilities.dp(1.0f)), (float) (i3 + this.lineSize), (float) (AndroidUtilities.dp(2.0f) + measuredHeight), this.paint);
                }
                i++;
            }
        }

        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            this.circleSize = AndroidUtilities.dp(12.0f);
            this.gapSize = AndroidUtilities.dp(2.0f);
            this.sideSide = AndroidUtilities.dp(18.0f);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            boolean z = false;
            float x = motionEvent.getX();
            int i;
            int i2;
            if (motionEvent.getAction() == 0) {
                getParent().requestDisallowInterceptTouchEvent(true);
                i = 0;
                while (i < PhotoViewer.this.compressionsCount) {
                    i2 = (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * i)) + (this.circleSize / 2);
                    if (x <= ((float) (i2 - AndroidUtilities.dp(15.0f))) || x >= ((float) (i2 + AndroidUtilities.dp(15.0f)))) {
                        i++;
                    } else {
                        if (i == PhotoViewer.this.selectedCompression) {
                            z = true;
                        }
                        this.startMoving = z;
                        this.startX = x;
                        this.startMovingQuality = PhotoViewer.this.selectedCompression;
                    }
                }
            } else if (motionEvent.getAction() == 2) {
                if (this.startMoving) {
                    if (Math.abs(this.startX - x) >= AndroidUtilities.getPixelsInCM(0.5f, true)) {
                        this.moving = true;
                        this.startMoving = false;
                    }
                } else if (this.moving) {
                    i = 0;
                    while (i < PhotoViewer.this.compressionsCount) {
                        i2 = (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * i)) + (this.circleSize / 2);
                        int i3 = ((this.lineSize / 2) + (this.circleSize / 2)) + this.gapSize;
                        if (x <= ((float) (i2 - i3)) || x >= ((float) (i2 + i3))) {
                            i++;
                        } else if (PhotoViewer.this.selectedCompression != i) {
                            PhotoViewer.this.selectedCompression = i;
                            PhotoViewer.this.didChangedCompressionLevel(false);
                            invalidate();
                        }
                    }
                }
            } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                if (!this.moving) {
                    i = 0;
                    while (i < PhotoViewer.this.compressionsCount) {
                        i2 = (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * i)) + (this.circleSize / 2);
                        if (x <= ((float) (i2 - AndroidUtilities.dp(15.0f))) || x >= ((float) (i2 + AndroidUtilities.dp(15.0f)))) {
                            i++;
                        } else if (PhotoViewer.this.selectedCompression != i) {
                            PhotoViewer.this.selectedCompression = i;
                            PhotoViewer.this.didChangedCompressionLevel(true);
                            invalidate();
                        }
                    }
                } else if (PhotoViewer.this.selectedCompression != this.startMovingQuality) {
                    PhotoViewer.this.requestVideoPreview(1);
                }
                this.startMoving = false;
                this.moving = false;
            }
            return true;
        }
    }

    public PhotoViewer() {
        this.blackPaint.setColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
    }

    private void animateTo(float f, float f2, float f3, boolean z) {
        animateTo(f, f2, f3, z, Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
    }

    private void animateTo(float f, float f2, float f3, boolean z, int i) {
        if (this.scale != f || this.translationX != f2 || this.translationY != f3) {
            this.zoomAnimation = z;
            this.animateToScale = f;
            this.animateToX = f2;
            this.animateToY = f3;
            this.animationStartTime = System.currentTimeMillis();
            this.imageMoveAnimation = new AnimatorSet();
            this.imageMoveAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this, "animationValue", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
            this.imageMoveAnimation.setInterpolator(this.interpolator);
            this.imageMoveAnimation.setDuration((long) i);
            this.imageMoveAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    PhotoViewer.this.imageMoveAnimation = null;
                    PhotoViewer.this.containerView.invalidate();
                }
            });
            this.imageMoveAnimation.start();
        }
    }

    private void applyCurrentEditMode() {
        Bitmap bitmap;
        boolean z;
        SavedFilterState savedFilterState;
        Collection collection;
        if (this.currentEditMode == 1) {
            bitmap = this.photoCropView.getBitmap();
            z = true;
            savedFilterState = null;
            collection = null;
        } else if (this.currentEditMode == 2) {
            r1 = this.photoFilterView.getBitmap();
            z = false;
            savedFilterState = this.photoFilterView.getSavedFilterState();
            collection = null;
            bitmap = r1;
        } else if (this.currentEditMode == 3) {
            r1 = this.photoPaintView.getBitmap();
            z = true;
            savedFilterState = null;
            Object masks = this.photoPaintView.getMasks();
            bitmap = r1;
        } else {
            z = false;
            savedFilterState = null;
            collection = null;
            bitmap = null;
        }
        if (bitmap != null) {
            TLObject scaleAndSaveImage = ImageLoader.scaleAndSaveImage(bitmap, (float) AndroidUtilities.getPhotoSize(), (float) AndroidUtilities.getPhotoSize(), 80, false, 101, 101);
            if (scaleAndSaveImage != null) {
                Object obj = this.imagesArrLocals.get(this.currentIndex);
                TLObject scaleAndSaveImage2;
                if (obj instanceof PhotoEntry) {
                    PhotoEntry photoEntry = (PhotoEntry) obj;
                    photoEntry.imagePath = FileLoader.getPathToAttach(scaleAndSaveImage, true).toString();
                    scaleAndSaveImage2 = ImageLoader.scaleAndSaveImage(bitmap, (float) AndroidUtilities.dp(120.0f), (float) AndroidUtilities.dp(120.0f), 70, false, 101, 101);
                    if (scaleAndSaveImage2 != null) {
                        photoEntry.thumbPath = FileLoader.getPathToAttach(scaleAndSaveImage2, true).toString();
                    }
                    if (collection != null) {
                        photoEntry.stickers.addAll(collection);
                    }
                    if (this.currentEditMode == 1) {
                        this.cropItem.setColorFilter(new PorterDuffColorFilter(-12734994, Mode.MULTIPLY));
                        photoEntry.isCropped = true;
                    } else if (this.currentEditMode == 2) {
                        this.tuneItem.setColorFilter(new PorterDuffColorFilter(-12734994, Mode.MULTIPLY));
                        photoEntry.isFiltered = true;
                    } else if (this.currentEditMode == 3) {
                        this.paintItem.setColorFilter(new PorterDuffColorFilter(-12734994, Mode.MULTIPLY));
                        photoEntry.isPainted = true;
                    }
                    if (savedFilterState != null) {
                        photoEntry.savedFilterState = savedFilterState;
                    } else if (z) {
                        photoEntry.savedFilterState = null;
                    }
                } else if (obj instanceof SearchImage) {
                    SearchImage searchImage = (SearchImage) obj;
                    searchImage.imagePath = FileLoader.getPathToAttach(scaleAndSaveImage, true).toString();
                    scaleAndSaveImage2 = ImageLoader.scaleAndSaveImage(bitmap, (float) AndroidUtilities.dp(120.0f), (float) AndroidUtilities.dp(120.0f), 70, false, 101, 101);
                    if (scaleAndSaveImage2 != null) {
                        searchImage.thumbPath = FileLoader.getPathToAttach(scaleAndSaveImage2, true).toString();
                    }
                    if (collection != null) {
                        searchImage.stickers.addAll(collection);
                    }
                    if (this.currentEditMode == 1) {
                        this.cropItem.setColorFilter(new PorterDuffColorFilter(-12734994, Mode.MULTIPLY));
                        searchImage.isCropped = true;
                    } else if (this.currentEditMode == 2) {
                        this.tuneItem.setColorFilter(new PorterDuffColorFilter(-12734994, Mode.MULTIPLY));
                        searchImage.isFiltered = true;
                    } else if (this.currentEditMode == 3) {
                        this.paintItem.setColorFilter(new PorterDuffColorFilter(-12734994, Mode.MULTIPLY));
                        searchImage.isPainted = true;
                    }
                    if (savedFilterState != null) {
                        searchImage.savedFilterState = savedFilterState;
                    } else if (z) {
                        searchImage.savedFilterState = null;
                    }
                }
                if (this.sendPhotoType == 0 && this.placeProvider != null) {
                    this.placeProvider.updatePhotoAtIndex(this.currentIndex);
                    if (!this.placeProvider.isPhotoChecked(this.currentIndex)) {
                        setPhotoChecked();
                    }
                }
                if (this.currentEditMode == 1) {
                    float rectSizeX = this.photoCropView.getRectSizeX() / ((float) getContainerViewWidth());
                    float rectSizeY = this.photoCropView.getRectSizeY() / ((float) getContainerViewHeight());
                    if (rectSizeX <= rectSizeY) {
                        rectSizeX = rectSizeY;
                    }
                    this.scale = rectSizeX;
                    this.translationX = (this.photoCropView.getRectX() + (this.photoCropView.getRectSizeX() / 2.0f)) - ((float) (getContainerViewWidth() / 2));
                    this.translationY = (this.photoCropView.getRectY() + (this.photoCropView.getRectSizeY() / 2.0f)) - ((float) (getContainerViewHeight() / 2));
                    this.zoomAnimation = true;
                    this.applying = true;
                    this.photoCropView.onDisappear();
                }
                this.centerImage.setParentView(null);
                this.centerImage.setOrientation(0, true);
                this.ignoreDidSetImage = true;
                this.centerImage.setImageBitmap(bitmap);
                this.ignoreDidSetImage = false;
                this.centerImage.setParentView(this.containerView);
            }
        }
    }

    private boolean checkAnimation() {
        if (this.animationInProgress != 0 && Math.abs(this.transitionAnimationStartTime - System.currentTimeMillis()) >= 500) {
            if (this.animationEndRunnable != null) {
                this.animationEndRunnable.run();
                this.animationEndRunnable = null;
            }
            this.animationInProgress = 0;
        }
        return this.animationInProgress != 0;
    }

    private void checkMinMax(boolean z) {
        float f = this.translationX;
        float f2 = this.translationY;
        updateMinMax(this.scale);
        if (this.translationX < this.minX) {
            f = this.minX;
        } else if (this.translationX > this.maxX) {
            f = this.maxX;
        }
        if (this.translationY < this.minY) {
            f2 = this.minY;
        } else if (this.translationY > this.maxY) {
            f2 = this.maxY;
        }
        animateTo(this.scale, f, f2, z);
    }

    private void checkProgress(int i, boolean z) {
        boolean z2 = false;
        int i2 = this.currentIndex;
        if (i == 1) {
            i2++;
        } else if (i == 2) {
            i2--;
        }
        if (this.currentFileNames[i] != null) {
            File file;
            boolean z3;
            File file2 = null;
            if (this.currentMessageObject != null) {
                if (i2 < 0 || i2 >= this.imagesArr.size()) {
                    this.photoProgressViews[i].setBackgroundState(-1, z);
                    return;
                }
                MessageObject messageObject = (MessageObject) this.imagesArr.get(i2);
                if (!TextUtils.isEmpty(messageObject.messageOwner.attachPath)) {
                    file2 = new File(messageObject.messageOwner.attachPath);
                    if (!file2.exists()) {
                        file2 = null;
                    }
                }
                if (file2 == null) {
                    file2 = FileLoader.getPathToMessage(messageObject.messageOwner);
                }
                boolean isVideo = messageObject.isVideo();
                file = file2;
                z3 = isVideo;
            } else if (this.currentBotInlineResult != null) {
                if (i2 < 0 || i2 >= this.imagesArrLocals.size()) {
                    this.photoProgressViews[i].setBackgroundState(-1, z);
                    return;
                }
                BotInlineResult botInlineResult = (BotInlineResult) this.imagesArrLocals.get(i2);
                if (botInlineResult.type.equals(MimeTypes.BASE_TYPE_VIDEO) || MessageObject.isVideoDocument(botInlineResult.document)) {
                    file = botInlineResult.document != null ? FileLoader.getPathToAttach(botInlineResult.document) : botInlineResult.content_url != null ? new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(botInlineResult.content_url) + "." + ImageLoader.getHttpUrlExtension(botInlineResult.content_url, "mp4")) : null;
                    z3 = true;
                } else if (botInlineResult.document != null) {
                    file = new File(FileLoader.getInstance().getDirectory(3), this.currentFileNames[i]);
                    z3 = false;
                } else if (botInlineResult.photo != null) {
                    file = new File(FileLoader.getInstance().getDirectory(0), this.currentFileNames[i]);
                    z3 = false;
                } else {
                    file = null;
                    z3 = false;
                }
                if (file == null || !file.exists()) {
                    file = new File(FileLoader.getInstance().getDirectory(4), this.currentFileNames[i]);
                }
            } else if (this.currentFileLocation != null) {
                if (i2 < 0 || i2 >= this.imagesArrLocations.size()) {
                    this.photoProgressViews[i].setBackgroundState(-1, z);
                    return;
                }
                FileLocation fileLocation = (FileLocation) this.imagesArrLocations.get(i2);
                z3 = this.avatarsDialogId != 0 || this.isEvent;
                file = FileLoader.getPathToAttach(fileLocation, z3);
                z3 = false;
            } else if (this.currentPathObject != null) {
                file2 = new File(FileLoader.getInstance().getDirectory(3), this.currentFileNames[i]);
                if (file2.exists()) {
                    file = file2;
                    z3 = false;
                } else {
                    file = new File(FileLoader.getInstance().getDirectory(4), this.currentFileNames[i]);
                    z3 = false;
                }
            } else {
                file = null;
                z3 = false;
            }
            if (file == null || !file.exists()) {
                if (!z3) {
                    this.photoProgressViews[i].setBackgroundState(0, z);
                } else if (FileLoader.getInstance().isLoadingFile(this.currentFileNames[i])) {
                    this.photoProgressViews[i].setBackgroundState(1, false);
                } else {
                    this.photoProgressViews[i].setBackgroundState(2, false);
                }
                Float fileProgress = ImageLoader.getInstance().getFileProgress(this.currentFileNames[i]);
                if (fileProgress == null) {
                    fileProgress = Float.valueOf(BitmapDescriptorFactory.HUE_RED);
                }
                this.photoProgressViews[i].setProgress(fileProgress.floatValue(), false);
            } else if (z3) {
                this.photoProgressViews[i].setBackgroundState(3, z);
            } else {
                this.photoProgressViews[i].setBackgroundState(-1, z);
            }
            if (i == 0) {
                if (!(this.imagesArrLocals.isEmpty() && (this.currentFileNames[0] == null || z3 || this.photoProgressViews[0].backgroundState == 0))) {
                    z2 = true;
                }
                this.canZoom = z2;
                return;
            }
            return;
        }
        if (!this.imagesArrLocals.isEmpty() && i2 >= 0 && i2 < this.imagesArrLocals.size()) {
            Object obj = this.imagesArrLocals.get(i2);
            if (obj instanceof PhotoEntry) {
                z2 = ((PhotoEntry) obj).isVideo;
            }
        }
        if (z2) {
            this.photoProgressViews[i].setBackgroundState(3, z);
        } else {
            this.photoProgressViews[i].setBackgroundState(-1, z);
        }
    }

    private void closeCaptionEnter(boolean z) {
        if (this.currentIndex >= 0 && this.currentIndex < this.imagesArrLocals.size()) {
            Object obj = this.imagesArrLocals.get(this.currentIndex);
            if (z) {
                if (obj instanceof PhotoEntry) {
                    ((PhotoEntry) obj).caption = this.captionEditText.getFieldCharSequence();
                } else if (obj instanceof SearchImage) {
                    ((SearchImage) obj).caption = this.captionEditText.getFieldCharSequence();
                }
                if (!(this.captionEditText.getFieldCharSequence().length() == 0 || this.placeProvider.isPhotoChecked(this.currentIndex))) {
                    setPhotoChecked();
                }
            }
            this.captionEditText.setTag(null);
            if (this.lastTitle != null) {
                this.actionBar.setTitle(this.lastTitle);
                this.lastTitle = null;
            }
            if (this.isCurrentVideo) {
                this.actionBar.setSubtitle(this.muteVideo ? null : this.currentSubtitle);
            }
            updateCaptionTextForCurrentPhoto(obj);
            setCurrentCaption(this.captionEditText.getFieldCharSequence());
            if (this.captionEditText.isPopupShowing()) {
                this.captionEditText.hidePopup();
            }
            this.captionEditText.closeKeyboard();
        }
    }

    private void didChangedCompressionLevel(boolean z) {
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putInt("compress_video2", this.selectedCompression);
        edit.commit();
        updateWidthHeightBitrateForCompression();
        updateVideoInfo();
        if (z) {
            requestVideoPreview(1);
        }
    }

    private int getAdditionX() {
        return (this.currentEditMode == 0 || this.currentEditMode == 3) ? 0 : AndroidUtilities.dp(14.0f);
    }

    private int getAdditionY() {
        int i = 0;
        int dp;
        if (this.currentEditMode == 3) {
            dp = AndroidUtilities.dp(8.0f);
            if (VERSION.SDK_INT >= 21) {
                i = AndroidUtilities.statusBarHeight;
            }
            return i + dp;
        } else if (this.currentEditMode == 0) {
            return 0;
        } else {
            dp = AndroidUtilities.dp(14.0f);
            if (VERSION.SDK_INT >= 21) {
                i = AndroidUtilities.statusBarHeight;
            }
            return i + dp;
        }
    }

    private int getContainerViewHeight() {
        return getContainerViewHeight(this.currentEditMode);
    }

    private int getContainerViewHeight(int i) {
        int i2 = AndroidUtilities.displaySize.y;
        if (i == 0 && VERSION.SDK_INT >= 21) {
            i2 += AndroidUtilities.statusBarHeight;
        }
        return i == 1 ? i2 - AndroidUtilities.dp(144.0f) : i == 2 ? i2 - AndroidUtilities.dp(214.0f) : i == 3 ? i2 - (AndroidUtilities.dp(48.0f) + ActionBar.getCurrentActionBarHeight()) : i2;
    }

    private int getContainerViewWidth() {
        return getContainerViewWidth(this.currentEditMode);
    }

    private int getContainerViewWidth(int i) {
        int width = this.containerView.getWidth();
        return (i == 0 || i == 3) ? width : width - AndroidUtilities.dp(28.0f);
    }

    private VideoEditedInfo getCurrentVideoEditedInfo() {
        int i = -1;
        if (!this.isCurrentVideo || this.currentPlayingVideoFile == null || this.compressionsCount == 0) {
            return null;
        }
        VideoEditedInfo videoEditedInfo = new VideoEditedInfo();
        videoEditedInfo.startTime = this.startTime;
        videoEditedInfo.endTime = this.endTime;
        videoEditedInfo.rotationValue = this.rotationValue;
        videoEditedInfo.originalWidth = this.originalWidth;
        videoEditedInfo.originalHeight = this.originalHeight;
        videoEditedInfo.bitrate = this.bitrate;
        videoEditedInfo.originalPath = this.currentPlayingVideoFile.getAbsolutePath();
        videoEditedInfo.estimatedSize = (long) this.estimatedSize;
        videoEditedInfo.estimatedDuration = this.estimatedDuration;
        if (this.muteVideo || !(this.compressItem.getTag() == null || this.selectedCompression == this.compressionsCount - 1)) {
            if (this.muteVideo) {
                this.selectedCompression = 1;
                updateWidthHeightBitrateForCompression();
            }
            videoEditedInfo.resultWidth = this.resultWidth;
            videoEditedInfo.resultHeight = this.resultHeight;
            if (!this.muteVideo) {
                i = this.bitrate;
            }
            videoEditedInfo.bitrate = i;
            videoEditedInfo.muted = this.muteVideo;
        } else {
            videoEditedInfo.resultWidth = this.originalWidth;
            videoEditedInfo.resultHeight = this.originalHeight;
            if (!this.muteVideo) {
                i = this.originalBitrate;
            }
            videoEditedInfo.bitrate = i;
            videoEditedInfo.muted = this.muteVideo;
        }
        return videoEditedInfo;
    }

    private TLObject getFileLocation(int i, int[] iArr) {
        if (i < 0) {
            return null;
        }
        if (this.imagesArrLocations.isEmpty()) {
            if (!this.imagesArr.isEmpty()) {
                if (i >= this.imagesArr.size()) {
                    return null;
                }
                MessageObject messageObject = (MessageObject) this.imagesArr.get(i);
                PhotoSize closestPhotoSizeWithSize;
                if (messageObject.messageOwner instanceof TLRPC$TL_messageService) {
                    if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionUserUpdatedPhoto) {
                        return messageObject.messageOwner.action.newUserPhoto.photo_big;
                    }
                    closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize());
                    if (closestPhotoSizeWithSize != null) {
                        iArr[0] = closestPhotoSizeWithSize.size;
                        if (iArr[0] == 0) {
                            iArr[0] = -1;
                        }
                        return closestPhotoSizeWithSize.location;
                    }
                    iArr[0] = -1;
                } else if (((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) && messageObject.messageOwner.media.photo != null) || ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) && messageObject.messageOwner.media.webpage != null)) {
                    closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize());
                    if (closestPhotoSizeWithSize != null) {
                        iArr[0] = closestPhotoSizeWithSize.size;
                        if (iArr[0] == 0) {
                            iArr[0] = -1;
                        }
                        return closestPhotoSizeWithSize.location;
                    }
                    iArr[0] = -1;
                } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice) {
                    return ((TLRPC$TL_messageMediaInvoice) messageObject.messageOwner.media).photo;
                } else {
                    if (!(messageObject.getDocument() == null || messageObject.getDocument().thumb == null)) {
                        iArr[0] = messageObject.getDocument().thumb.size;
                        if (iArr[0] == 0) {
                            iArr[0] = -1;
                        }
                        return messageObject.getDocument().thumb.location;
                    }
                }
            }
            return null;
        } else if (i >= this.imagesArrLocations.size()) {
            return null;
        } else {
            iArr[0] = ((Integer) this.imagesArrLocationsSizes.get(i)).intValue();
            return (TLObject) this.imagesArrLocations.get(i);
        }
    }

    private String getFileName(int i) {
        if (i < 0) {
            return null;
        }
        if (this.imagesArrLocations.isEmpty() && this.imagesArr.isEmpty()) {
            if (!this.imagesArrLocals.isEmpty()) {
                if (i >= this.imagesArrLocals.size()) {
                    return null;
                }
                Object obj = this.imagesArrLocals.get(i);
                if (obj instanceof SearchImage) {
                    SearchImage searchImage = (SearchImage) obj;
                    if (searchImage.document != null) {
                        return FileLoader.getAttachFileName(searchImage.document);
                    }
                    if (!(searchImage.type == 1 || searchImage.localUrl == null || searchImage.localUrl.length() <= 0)) {
                        File file = new File(searchImage.localUrl);
                        if (file.exists()) {
                            return file.getName();
                        }
                        searchImage.localUrl = TtmlNode.ANONYMOUS_REGION_ID;
                    }
                    return Utilities.MD5(searchImage.imageUrl) + "." + ImageLoader.getHttpUrlExtension(searchImage.imageUrl, "jpg");
                } else if (obj instanceof BotInlineResult) {
                    BotInlineResult botInlineResult = (BotInlineResult) obj;
                    if (botInlineResult.document != null) {
                        return FileLoader.getAttachFileName(botInlineResult.document);
                    }
                    if (botInlineResult.photo != null) {
                        return FileLoader.getAttachFileName(FileLoader.getClosestPhotoSizeWithSize(botInlineResult.photo.sizes, AndroidUtilities.getPhotoSize()));
                    }
                    if (botInlineResult.content_url != null) {
                        return Utilities.MD5(botInlineResult.content_url) + "." + ImageLoader.getHttpUrlExtension(botInlineResult.content_url, "jpg");
                    }
                }
            }
        } else if (this.imagesArrLocations.isEmpty()) {
            if (!this.imagesArr.isEmpty()) {
                return i >= this.imagesArr.size() ? null : FileLoader.getMessageFileName(((MessageObject) this.imagesArr.get(i)).messageOwner);
            }
        } else if (i >= this.imagesArrLocations.size()) {
            return null;
        } else {
            FileLocation fileLocation = (FileLocation) this.imagesArrLocations.get(i);
            return fileLocation.volume_id + "_" + fileLocation.local_id + ".jpg";
        }
        return null;
    }

    public static PhotoViewer getInstance() {
        PhotoViewer photoViewer = Instance;
        if (photoViewer == null) {
            synchronized (PhotoViewer.class) {
                photoViewer = Instance;
                if (photoViewer == null) {
                    photoViewer = new PhotoViewer();
                    Instance = photoViewer;
                }
            }
        }
        return photoViewer;
    }

    private void goToNext() {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (this.scale != 1.0f) {
            f = ((float) ((getContainerViewWidth() - this.centerImage.getImageWidth()) / 2)) * this.scale;
        }
        this.switchImageAfterAnimation = 1;
        animateTo(this.scale, ((this.minX - ((float) getContainerViewWidth())) - f) - ((float) (AndroidUtilities.dp(30.0f) / 2)), this.translationY, false);
    }

    private void goToPrev() {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (this.scale != 1.0f) {
            f = ((float) ((getContainerViewWidth() - this.centerImage.getImageWidth()) / 2)) * this.scale;
        }
        this.switchImageAfterAnimation = 2;
        animateTo(this.scale, (f + (this.maxX + ((float) getContainerViewWidth()))) + ((float) (AndroidUtilities.dp(30.0f) / 2)), this.translationY, false);
    }

    private void hideHint() {
        this.hintAnimation = new AnimatorSet();
        AnimatorSet animatorSet = this.hintAnimation;
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(this.hintTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        animatorSet.playTogether(animatorArr);
        this.hintAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationCancel(Animator animator) {
                if (animator.equals(PhotoViewer.this.hintAnimation)) {
                    PhotoViewer.this.hintHideRunnable = null;
                    PhotoViewer.this.hintHideRunnable = null;
                }
            }

            public void onAnimationEnd(Animator animator) {
                if (animator.equals(PhotoViewer.this.hintAnimation)) {
                    PhotoViewer.this.hintAnimation = null;
                    PhotoViewer.this.hintHideRunnable = null;
                    if (PhotoViewer.this.hintTextView != null) {
                        PhotoViewer.this.hintTextView.setVisibility(8);
                    }
                }
            }
        });
        this.hintAnimation.setDuration(300);
        this.hintAnimation.start();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onActionClick(boolean r9) {
        /*
        r8 = this;
        r7 = 1;
        r0 = 0;
        r6 = 0;
        r1 = r8.currentMessageObject;
        if (r1 != 0) goto L_0x000b;
    L_0x0007:
        r1 = r8.currentBotInlineResult;
        if (r1 == 0) goto L_0x0011;
    L_0x000b:
        r1 = r8.currentFileNames;
        r1 = r1[r6];
        if (r1 != 0) goto L_0x0012;
    L_0x0011:
        return;
    L_0x0012:
        r1 = r8.currentMessageObject;
        if (r1 == 0) goto L_0x0070;
    L_0x0016:
        r1 = r8.currentMessageObject;
        r1 = r1.messageOwner;
        r1 = r1.attachPath;
        if (r1 == 0) goto L_0x013f;
    L_0x001e:
        r1 = r8.currentMessageObject;
        r1 = r1.messageOwner;
        r1 = r1.attachPath;
        r1 = r1.length();
        if (r1 == 0) goto L_0x013f;
    L_0x002a:
        r1 = new java.io.File;
        r2 = r8.currentMessageObject;
        r2 = r2.messageOwner;
        r2 = r2.attachPath;
        r1.<init>(r2);
        r2 = r1.exists();
        if (r2 != 0) goto L_0x003c;
    L_0x003b:
        r1 = r0;
    L_0x003c:
        if (r1 != 0) goto L_0x013c;
    L_0x003e:
        r1 = r8.currentMessageObject;
        r1 = r1.messageOwner;
        r1 = org.telegram.messenger.FileLoader.getPathToMessage(r1);
        r2 = r1.exists();
        if (r2 != 0) goto L_0x0088;
    L_0x004c:
        if (r0 != 0) goto L_0x0137;
    L_0x004e:
        if (r9 == 0) goto L_0x0011;
    L_0x0050:
        r0 = r8.currentMessageObject;
        if (r0 == 0) goto L_0x00da;
    L_0x0054:
        r0 = org.telegram.messenger.FileLoader.getInstance();
        r1 = r8.currentFileNames;
        r1 = r1[r6];
        r0 = r0.isLoadingFile(r1);
        if (r0 != 0) goto L_0x00cb;
    L_0x0062:
        r0 = org.telegram.messenger.FileLoader.getInstance();
        r1 = r8.currentMessageObject;
        r1 = r1.getDocument();
        r0.loadFile(r1, r7, r6);
        goto L_0x0011;
    L_0x0070:
        r1 = r8.currentBotInlineResult;
        if (r1 == 0) goto L_0x004c;
    L_0x0074:
        r1 = r8.currentBotInlineResult;
        r1 = r1.document;
        if (r1 == 0) goto L_0x008a;
    L_0x007a:
        r1 = r8.currentBotInlineResult;
        r1 = r1.document;
        r1 = org.telegram.messenger.FileLoader.getPathToAttach(r1);
        r2 = r1.exists();
        if (r2 == 0) goto L_0x004c;
    L_0x0088:
        r0 = r1;
        goto L_0x004c;
    L_0x008a:
        r1 = new java.io.File;
        r2 = org.telegram.messenger.FileLoader.getInstance();
        r3 = 4;
        r2 = r2.getDirectory(r3);
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = r8.currentBotInlineResult;
        r4 = r4.content_url;
        r4 = org.telegram.messenger.Utilities.MD5(r4);
        r3 = r3.append(r4);
        r4 = ".";
        r3 = r3.append(r4);
        r4 = r8.currentBotInlineResult;
        r4 = r4.content_url;
        r5 = "mp4";
        r4 = org.telegram.messenger.ImageLoader.getHttpUrlExtension(r4, r5);
        r3 = r3.append(r4);
        r3 = r3.toString();
        r1.<init>(r2, r3);
        r2 = r1.exists();
        if (r2 == 0) goto L_0x004c;
    L_0x00c9:
        r0 = r1;
        goto L_0x004c;
    L_0x00cb:
        r0 = org.telegram.messenger.FileLoader.getInstance();
        r1 = r8.currentMessageObject;
        r1 = r1.getDocument();
        r0.cancelLoadFile(r1);
        goto L_0x0011;
    L_0x00da:
        r0 = r8.currentBotInlineResult;
        if (r0 == 0) goto L_0x0011;
    L_0x00de:
        r0 = r8.currentBotInlineResult;
        r0 = r0.document;
        if (r0 == 0) goto L_0x010c;
    L_0x00e4:
        r0 = org.telegram.messenger.FileLoader.getInstance();
        r1 = r8.currentFileNames;
        r1 = r1[r6];
        r0 = r0.isLoadingFile(r1);
        if (r0 != 0) goto L_0x00ff;
    L_0x00f2:
        r0 = org.telegram.messenger.FileLoader.getInstance();
        r1 = r8.currentBotInlineResult;
        r1 = r1.document;
        r0.loadFile(r1, r7, r6);
        goto L_0x0011;
    L_0x00ff:
        r0 = org.telegram.messenger.FileLoader.getInstance();
        r1 = r8.currentBotInlineResult;
        r1 = r1.document;
        r0.cancelLoadFile(r1);
        goto L_0x0011;
    L_0x010c:
        r0 = org.telegram.messenger.ImageLoader.getInstance();
        r1 = r8.currentBotInlineResult;
        r1 = r1.content_url;
        r0 = r0.isLoadingHttpFile(r1);
        if (r0 != 0) goto L_0x012a;
    L_0x011a:
        r0 = org.telegram.messenger.ImageLoader.getInstance();
        r1 = r8.currentBotInlineResult;
        r1 = r1.content_url;
        r2 = "mp4";
        r0.loadHttpFile(r1, r2);
        goto L_0x0011;
    L_0x012a:
        r0 = org.telegram.messenger.ImageLoader.getInstance();
        r1 = r8.currentBotInlineResult;
        r1 = r1.content_url;
        r0.cancelLoadHttpFile(r1);
        goto L_0x0011;
    L_0x0137:
        r8.preparePlayer(r0, r7, r6);
        goto L_0x0011;
    L_0x013c:
        r0 = r1;
        goto L_0x004c;
    L_0x013f:
        r1 = r0;
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PhotoViewer.onActionClick(boolean):void");
    }

    @SuppressLint({"NewApi", "DrawAllocation"})
    private void onDraw(Canvas canvas) {
        if (this.animationInProgress == 1) {
            return;
        }
        if (this.isVisible || this.animationInProgress == 2) {
            float f;
            float f2;
            float f3;
            float containerViewHeight;
            float f4;
            float f5;
            int bitmapWidth;
            int i;
            int i2;
            float f6 = -1.0f;
            if (this.imageMoveAnimation != null) {
                if (!this.scroller.isFinished()) {
                    this.scroller.abortAnimation();
                }
                f = ((this.animateToScale - this.scale) * this.animationValue) + this.scale;
                f2 = ((this.animateToX - this.translationX) * this.animationValue) + this.translationX;
                f3 = this.translationY + ((this.animateToY - this.translationY) * this.animationValue);
                if (this.currentEditMode == 1) {
                    this.photoCropView.setAnimationProgress(this.animationValue);
                }
                if (this.animateToScale == 1.0f && this.scale == 1.0f && this.translationX == BitmapDescriptorFactory.HUE_RED) {
                    f6 = f3;
                }
                this.containerView.invalidate();
                float f7 = f;
                f = f2;
                f2 = f3;
                f3 = f7;
            } else {
                if (this.animationStartTime != 0) {
                    this.translationX = this.animateToX;
                    this.translationY = this.animateToY;
                    this.scale = this.animateToScale;
                    this.animationStartTime = 0;
                    if (this.currentEditMode == 1) {
                        this.photoCropView.setAnimationProgress(1.0f);
                    }
                    updateMinMax(this.scale);
                    this.zoomAnimation = false;
                }
                if (!this.scroller.isFinished() && this.scroller.computeScrollOffset()) {
                    if (((float) this.scroller.getStartX()) < this.maxX && ((float) this.scroller.getStartX()) > this.minX) {
                        this.translationX = (float) this.scroller.getCurrX();
                    }
                    if (((float) this.scroller.getStartY()) < this.maxY && ((float) this.scroller.getStartY()) > this.minY) {
                        this.translationY = (float) this.scroller.getCurrY();
                    }
                    this.containerView.invalidate();
                }
                if (this.switchImageAfterAnimation != 0) {
                    if (this.switchImageAfterAnimation == 1) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                PhotoViewer.this.setImageIndex(PhotoViewer.this.currentIndex + 1, false);
                            }
                        });
                    } else if (this.switchImageAfterAnimation == 2) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                PhotoViewer.this.setImageIndex(PhotoViewer.this.currentIndex - 1, false);
                            }
                        });
                    }
                    this.switchImageAfterAnimation = 0;
                }
                f3 = this.scale;
                f2 = this.translationY;
                f = this.translationX;
                if (!this.moving) {
                    f6 = this.translationY;
                }
            }
            if (this.animationInProgress != 2) {
                if (this.currentEditMode != 0 || this.scale != 1.0f || f6 == -1.0f || this.zoomAnimation) {
                    this.backgroundDrawable.setAlpha(255);
                } else {
                    containerViewHeight = ((float) getContainerViewHeight()) / 4.0f;
                    this.backgroundDrawable.setAlpha((int) Math.max(127.0f, (1.0f - (Math.min(Math.abs(f6), containerViewHeight) / containerViewHeight)) * 255.0f));
                }
            }
            ImageReceiver imageReceiver = null;
            if (this.currentEditMode == 0) {
                ImageReceiver imageReceiver2;
                if (!(this.scale < 1.0f || this.zoomAnimation || this.zooming)) {
                    if (f > this.maxX + ((float) AndroidUtilities.dp(5.0f))) {
                        imageReceiver2 = this.leftImage;
                    } else if (f < this.minX - ((float) AndroidUtilities.dp(5.0f))) {
                        imageReceiver2 = this.rightImage;
                    } else {
                        this.groupedPhotosListView.setMoveProgress(BitmapDescriptorFactory.HUE_RED);
                    }
                    this.changingPage = imageReceiver2 == null;
                    imageReceiver = imageReceiver2;
                }
                imageReceiver2 = null;
                if (imageReceiver2 == null) {
                }
                this.changingPage = imageReceiver2 == null;
                imageReceiver = imageReceiver2;
            }
            if (imageReceiver == this.rightImage) {
                f4 = BitmapDescriptorFactory.HUE_RED;
                containerViewHeight = 1.0f;
                if (this.zoomAnimation || f >= this.minX) {
                    f5 = f;
                } else {
                    containerViewHeight = Math.min(1.0f, (this.minX - f) / ((float) canvas.getWidth()));
                    f4 = (1.0f - containerViewHeight) * 0.3f;
                    f5 = (float) ((-canvas.getWidth()) - (AndroidUtilities.dp(30.0f) / 2));
                }
                if (imageReceiver.hasBitmapImage()) {
                    canvas.save();
                    canvas.translate((float) (getContainerViewWidth() / 2), (float) (getContainerViewHeight() / 2));
                    canvas.translate(((float) (canvas.getWidth() + (AndroidUtilities.dp(30.0f) / 2))) + f5, BitmapDescriptorFactory.HUE_RED);
                    canvas.scale(1.0f - f4, 1.0f - f4);
                    bitmapWidth = imageReceiver.getBitmapWidth();
                    int bitmapHeight = imageReceiver.getBitmapHeight();
                    float containerViewWidth = ((float) getContainerViewWidth()) / ((float) bitmapWidth);
                    float containerViewHeight2 = ((float) getContainerViewHeight()) / ((float) bitmapHeight);
                    if (containerViewWidth <= containerViewHeight2) {
                        containerViewHeight2 = containerViewWidth;
                    }
                    i = (int) (((float) bitmapWidth) * containerViewHeight2);
                    i2 = (int) (containerViewHeight2 * ((float) bitmapHeight));
                    imageReceiver.setAlpha(containerViewHeight);
                    imageReceiver.setImageCoords((-i) / 2, (-i2) / 2, i, i2);
                    imageReceiver.draw(canvas);
                    canvas.restore();
                }
                this.groupedPhotosListView.setMoveProgress(-containerViewHeight);
                canvas.save();
                canvas.translate(f5, f2 / f3);
                canvas.translate(((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f))) / 2.0f, (-f2) / f3);
                this.photoProgressViews[1].setScale(1.0f - f4);
                this.photoProgressViews[1].setAlpha(containerViewHeight);
                this.photoProgressViews[1].onDraw(canvas);
                canvas.restore();
            }
            f4 = BitmapDescriptorFactory.HUE_RED;
            containerViewHeight = 1.0f;
            if (this.zoomAnimation || f <= this.maxX || this.currentEditMode != 0) {
                f5 = f;
            } else {
                containerViewHeight = Math.min(1.0f, (f - this.maxX) / ((float) canvas.getWidth()));
                f4 = 0.3f * containerViewHeight;
                containerViewHeight = 1.0f - containerViewHeight;
                f5 = this.maxX;
            }
            Object obj = (this.aspectRatioFrameLayout == null || this.aspectRatioFrameLayout.getVisibility() != 0) ? null : 1;
            if (this.centerImage.hasBitmapImage()) {
                canvas.save();
                canvas.translate((float) ((getContainerViewWidth() / 2) + getAdditionX()), (float) ((getContainerViewHeight() / 2) + getAdditionY()));
                canvas.translate(f5, f2);
                canvas.scale(f3 - f4, f3 - f4);
                if (this.currentEditMode == 1) {
                    this.photoCropView.setBitmapParams(f3, f5, f2);
                }
                bitmapWidth = this.centerImage.getBitmapWidth();
                i = this.centerImage.getBitmapHeight();
                if (obj != null && this.textureUploaded && Math.abs((((float) bitmapWidth) / ((float) i)) - (((float) this.videoTextureView.getMeasuredWidth()) / ((float) this.videoTextureView.getMeasuredHeight()))) > 0.01f) {
                    bitmapWidth = this.videoTextureView.getMeasuredWidth();
                    i = this.videoTextureView.getMeasuredHeight();
                }
                float containerViewWidth2 = ((float) getContainerViewWidth()) / ((float) bitmapWidth);
                float containerViewHeight3 = ((float) getContainerViewHeight()) / ((float) i);
                if (containerViewWidth2 <= containerViewHeight3) {
                    containerViewHeight3 = containerViewWidth2;
                }
                bitmapWidth = (int) (((float) bitmapWidth) * containerViewHeight3);
                i = (int) (((float) i) * containerViewHeight3);
                if (!(obj != null && this.textureUploaded && this.videoCrossfadeStarted && this.videoCrossfadeAlpha == 1.0f)) {
                    this.centerImage.setAlpha(containerViewHeight);
                    this.centerImage.setImageCoords((-bitmapWidth) / 2, (-i) / 2, bitmapWidth, i);
                    this.centerImage.draw(canvas);
                }
                if (obj != null) {
                    if (!this.videoCrossfadeStarted && this.textureUploaded) {
                        this.videoCrossfadeStarted = true;
                        this.videoCrossfadeAlpha = BitmapDescriptorFactory.HUE_RED;
                        this.videoCrossfadeAlphaLastTime = System.currentTimeMillis();
                    }
                    canvas.translate((float) ((-bitmapWidth) / 2), (float) ((-i) / 2));
                    this.videoTextureView.setAlpha(this.videoCrossfadeAlpha * containerViewHeight);
                    this.aspectRatioFrameLayout.draw(canvas);
                    if (this.videoCrossfadeStarted && this.videoCrossfadeAlpha < 1.0f) {
                        long currentTimeMillis = System.currentTimeMillis();
                        long j = currentTimeMillis - this.videoCrossfadeAlphaLastTime;
                        this.videoCrossfadeAlphaLastTime = currentTimeMillis;
                        this.videoCrossfadeAlpha += ((float) j) / 200.0f;
                        this.containerView.invalidate();
                        if (this.videoCrossfadeAlpha > 1.0f) {
                            this.videoCrossfadeAlpha = 1.0f;
                        }
                    }
                }
                canvas.restore();
            }
            obj = this.isCurrentVideo ? (this.progressView.getVisibility() == 0 || (this.videoPlayer != null && this.videoPlayer.isPlaying())) ? null : 1 : (obj != null || this.videoPlayerControlFrameLayout.getVisibility() == 0) ? null : 1;
            if (obj != null) {
                canvas.save();
                canvas.translate(f5, f2 / f3);
                this.photoProgressViews[0].setScale(1.0f - f4);
                this.photoProgressViews[0].setAlpha(containerViewHeight);
                this.photoProgressViews[0].onDraw(canvas);
                canvas.restore();
            }
            if (imageReceiver == this.leftImage) {
                if (imageReceiver.hasBitmapImage()) {
                    canvas.save();
                    canvas.translate((float) (getContainerViewWidth() / 2), (float) (getContainerViewHeight() / 2));
                    canvas.translate(((-((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f)))) / 2.0f) + f, BitmapDescriptorFactory.HUE_RED);
                    i2 = imageReceiver.getBitmapWidth();
                    i = imageReceiver.getBitmapHeight();
                    f5 = ((float) getContainerViewWidth()) / ((float) i2);
                    f4 = ((float) getContainerViewHeight()) / ((float) i);
                    if (f5 <= f4) {
                        f4 = f5;
                    }
                    int i3 = (int) (((float) i2) * f4);
                    int i4 = (int) (f4 * ((float) i));
                    imageReceiver.setAlpha(1.0f);
                    imageReceiver.setImageCoords((-i3) / 2, (-i4) / 2, i3, i4);
                    imageReceiver.draw(canvas);
                    canvas.restore();
                }
                this.groupedPhotosListView.setMoveProgress(1.0f - containerViewHeight);
                canvas.save();
                canvas.translate(f, f2 / f3);
                canvas.translate((-((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f)))) / 2.0f, (-f2) / f3);
                this.photoProgressViews[2].setScale(1.0f);
                this.photoProgressViews[2].setAlpha(1.0f);
                this.photoProgressViews[2].onDraw(canvas);
                canvas.restore();
            }
        }
    }

    private void onPhotoClosed(PlaceProviderObject placeProviderObject) {
        this.isVisible = false;
        this.disableShowCheck = true;
        this.currentMessageObject = null;
        this.currentBotInlineResult = null;
        this.currentFileLocation = null;
        this.currentPathObject = null;
        this.currentThumb = null;
        this.parentAlert = null;
        if (this.currentAnimation != null) {
            this.currentAnimation.setSecondParentView(null);
            this.currentAnimation = null;
        }
        for (int i = 0; i < 3; i++) {
            if (this.photoProgressViews[i] != null) {
                this.photoProgressViews[i].setBackgroundState(-1, false);
            }
        }
        requestVideoPreview(0);
        if (this.videoTimelineView != null) {
            this.videoTimelineView.destroy();
        }
        this.centerImage.setImageBitmap((Bitmap) null);
        this.leftImage.setImageBitmap((Bitmap) null);
        this.rightImage.setImageBitmap((Bitmap) null);
        this.containerView.post(new Runnable() {
            public void run() {
                PhotoViewer.this.animatingImageView.setImageBitmap(null);
                try {
                    if (PhotoViewer.this.windowView.getParent() != null) {
                        ((WindowManager) PhotoViewer.this.parentActivity.getSystemService("window")).removeView(PhotoViewer.this.windowView);
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        });
        if (this.placeProvider != null) {
            this.placeProvider.willHidePhotoViewer();
        }
        this.groupedPhotosListView.clear();
        this.placeProvider = null;
        this.selectedPhotosAdapter.notifyDataSetChanged();
        this.disableShowCheck = false;
        if (placeProviderObject != null) {
            placeProviderObject.imageReceiver.setVisible(true, true);
        }
    }

    private void onPhotoShow(MessageObject messageObject, FileLocation fileLocation, ArrayList<MessageObject> arrayList, ArrayList<Object> arrayList2, int i, PlaceProviderObject placeProviderObject) {
        int i2;
        Object obj;
        this.classGuid = ConnectionsManager.getInstance().generateClassGuid();
        this.currentMessageObject = null;
        this.currentFileLocation = null;
        this.currentPathObject = null;
        this.fromCamera = false;
        this.currentBotInlineResult = null;
        this.currentIndex = -1;
        this.currentFileNames[0] = null;
        this.currentFileNames[1] = null;
        this.currentFileNames[2] = null;
        this.avatarsDialogId = 0;
        this.totalImagesCount = 0;
        this.totalImagesCountMerge = 0;
        this.currentEditMode = 0;
        this.isFirstLoading = true;
        this.needSearchImageInArr = false;
        this.loadingMoreImages = false;
        this.endReached[0] = false;
        this.endReached[1] = this.mergeDialogId == 0;
        this.opennedFromMedia = false;
        this.needCaptionLayout = false;
        this.canShowBottom = true;
        this.isCurrentVideo = false;
        this.imagesArr.clear();
        this.imagesArrLocations.clear();
        this.imagesArrLocationsSizes.clear();
        this.avatarsArr.clear();
        this.imagesArrLocals.clear();
        for (i2 = 0; i2 < 2; i2++) {
            this.imagesByIds[i2].clear();
            this.imagesByIdsTemp[i2].clear();
        }
        this.imagesArrTemp.clear();
        this.currentUserAvatarLocation = null;
        this.containerView.setPadding(0, 0, 0, 0);
        this.currentThumb = placeProviderObject != null ? placeProviderObject.thumb : null;
        boolean z = placeProviderObject != null && placeProviderObject.isEvent;
        this.isEvent = z;
        this.menuItem.setVisibility(0);
        this.sendItem.setVisibility(8);
        this.bottomLayout.setVisibility(0);
        this.bottomLayout.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        this.captionTextView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        this.shareButton.setVisibility(8);
        if (this.qualityChooseView != null) {
            this.qualityChooseView.setVisibility(4);
            this.qualityPicker.setVisibility(4);
            this.qualityChooseView.setTag(null);
        }
        if (this.qualityChooseViewAnimation != null) {
            this.qualityChooseViewAnimation.cancel();
            this.qualityChooseViewAnimation = null;
        }
        this.allowShare = false;
        this.slideshowMessageId = 0;
        this.nameOverride = null;
        this.dateOverride = 0;
        this.menuItem.hideSubItem(2);
        this.menuItem.hideSubItem(4);
        this.menuItem.hideSubItem(10);
        this.menuItem.hideSubItem(11);
        this.actionBar.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        this.checkImageView.setAlpha(1.0f);
        this.checkImageView.setVisibility(8);
        this.actionBar.setTitleRightMargin(0);
        this.photosCounterView.setAlpha(1.0f);
        this.photosCounterView.setVisibility(8);
        this.pickerView.setVisibility(8);
        this.pickerView.setAlpha(1.0f);
        this.pickerView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        this.paintItem.setVisibility(8);
        this.cropItem.setVisibility(8);
        this.tuneItem.setVisibility(8);
        this.timeItem.setVisibility(8);
        this.videoTimelineView.setVisibility(8);
        this.compressItem.setVisibility(8);
        this.captionEditText.setVisibility(8);
        this.mentionListView.setVisibility(8);
        this.muteItem.setVisibility(8);
        this.actionBar.setSubtitle(null);
        this.masksItem.setVisibility(8);
        this.muteVideo = false;
        this.muteItem.setImageResource(R.drawable.volume_on);
        this.editorDoneLayout.setVisibility(8);
        this.captionTextView.setTag(null);
        this.captionTextView.setVisibility(4);
        if (this.photoCropView != null) {
            this.photoCropView.setVisibility(8);
        }
        if (this.photoFilterView != null) {
            this.photoFilterView.setVisibility(8);
        }
        for (i2 = 0; i2 < 3; i2++) {
            if (this.photoProgressViews[i2] != null) {
                this.photoProgressViews[i2].setBackgroundState(-1, false);
            }
        }
        int i3;
        if (messageObject != null && arrayList == null) {
            if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) && messageObject.messageOwner.media.webpage != null) {
                TLRPC$WebPage tLRPC$WebPage = messageObject.messageOwner.media.webpage;
                String str = tLRPC$WebPage.site_name;
                if (str != null) {
                    str = str.toLowerCase();
                    if (str.equals("instagram") || str.equals("twitter")) {
                        if (!TextUtils.isEmpty(tLRPC$WebPage.author)) {
                            this.nameOverride = tLRPC$WebPage.author;
                        }
                        if (tLRPC$WebPage.cached_page instanceof TLRPC$TL_pageFull) {
                            for (i3 = 0; i3 < tLRPC$WebPage.cached_page.blocks.size(); i3++) {
                                PageBlock pageBlock = (PageBlock) tLRPC$WebPage.cached_page.blocks.get(i3);
                                if (pageBlock instanceof TLRPC$TL_pageBlockAuthorDate) {
                                    this.dateOverride = ((TLRPC$TL_pageBlockAuthorDate) pageBlock).published_date;
                                    break;
                                }
                            }
                        }
                        Collection webPagePhotos = messageObject.getWebPagePhotos(null, null);
                        if (!webPagePhotos.isEmpty()) {
                            this.slideshowMessageId = messageObject.getId();
                            this.needSearchImageInArr = false;
                            this.imagesArr.addAll(webPagePhotos);
                            this.totalImagesCount = this.imagesArr.size();
                            setImageIndex(this.imagesArr.indexOf(messageObject), true);
                        }
                    }
                }
            }
            if (this.slideshowMessageId == 0) {
                this.imagesArr.add(messageObject);
                if (this.currentAnimation != null || messageObject.eventId != 0) {
                    this.needSearchImageInArr = false;
                } else if (!((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) || (messageObject.messageOwner.action != null && !(messageObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty)))) {
                    this.needSearchImageInArr = true;
                    this.imagesByIds[0].put(Integer.valueOf(messageObject.getId()), messageObject);
                    this.menuItem.showSubItem(2);
                    this.sendItem.setVisibility(0);
                }
                setImageIndex(0, true);
            }
        } else if (fileLocation != null) {
            this.avatarsDialogId = placeProviderObject.dialogId;
            this.imagesArrLocations.add(fileLocation);
            this.imagesArrLocationsSizes.add(Integer.valueOf(placeProviderObject.size));
            this.avatarsArr.add(new TLRPC$TL_photoEmpty());
            this.shareButton.setVisibility(this.videoPlayerControlFrameLayout.getVisibility() != 0 ? 0 : 8);
            this.allowShare = true;
            this.menuItem.hideSubItem(2);
            if (this.shareButton.getVisibility() == 0) {
                this.menuItem.hideSubItem(10);
            } else {
                this.menuItem.showSubItem(10);
            }
            setImageIndex(0, true);
            this.currentUserAvatarLocation = fileLocation;
        } else if (arrayList != null) {
            this.opennedFromMedia = true;
            this.menuItem.showSubItem(4);
            this.sendItem.setVisibility(0);
            this.imagesArr.addAll(arrayList);
            for (i3 = 0; i3 < this.imagesArr.size(); i3++) {
                MessageObject messageObject2 = (MessageObject) this.imagesArr.get(i3);
                this.imagesByIds[messageObject2.getDialogId() == this.currentDialogId ? 0 : 1].put(Integer.valueOf(messageObject2.getId()), messageObject2);
            }
            setImageIndex(i, true);
        } else if (arrayList2 != null) {
            if (this.sendPhotoType == 0) {
                this.checkImageView.setVisibility(0);
                this.photosCounterView.setVisibility(0);
                this.actionBar.setTitleRightMargin(AndroidUtilities.dp(100.0f));
            }
            this.menuItem.setVisibility(8);
            this.imagesArrLocals.addAll(arrayList2);
            obj = this.imagesArrLocals.get(i);
            if (obj instanceof PhotoEntry) {
                if (((PhotoEntry) obj).isVideo) {
                    this.cropItem.setVisibility(8);
                    this.bottomLayout.setVisibility(0);
                    this.bottomLayout.setTranslationY((float) (-AndroidUtilities.dp(48.0f)));
                } else {
                    this.cropItem.setVisibility(0);
                }
                obj = 1;
            } else if (obj instanceof BotInlineResult) {
                this.cropItem.setVisibility(8);
                obj = null;
            } else {
                ImageView imageView = this.cropItem;
                i2 = ((obj instanceof SearchImage) && ((SearchImage) obj).type == 0) ? 0 : 8;
                imageView.setVisibility(i2);
                obj = this.cropItem.getVisibility() == 0 ? 1 : null;
            }
            if (this.parentChatActivity != null && (this.parentChatActivity.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.parentChatActivity.currentEncryptedChat.layer) >= 46)) {
                this.mentionsAdapter.setChatInfo(this.parentChatActivity.info);
                this.mentionsAdapter.setNeedUsernames(this.parentChatActivity.currentChat != null);
                this.mentionsAdapter.setNeedBotContext(false);
                z = obj != null && (this.placeProvider == null || (this.placeProvider != null && this.placeProvider.allowCaption()));
                this.needCaptionLayout = z;
                this.captionEditText.setVisibility(this.needCaptionLayout ? 0 : 8);
                if (this.needCaptionLayout) {
                    this.captionEditText.onCreate();
                }
            }
            this.pickerView.setVisibility(0);
            this.bottomLayout.setVisibility(8);
            this.canShowBottom = false;
            setImageIndex(i, true);
            this.paintItem.setVisibility(this.cropItem.getVisibility());
            this.tuneItem.setVisibility(this.cropItem.getVisibility());
            updateSelectedCount();
        }
        if (this.currentAnimation == null && !this.isEvent) {
            if (this.currentDialogId != 0 && this.totalImagesCount == 0) {
                SharedMediaQuery.getMediaCount(this.currentDialogId, 0, this.classGuid, true);
                if (this.mergeDialogId != 0) {
                    SharedMediaQuery.getMediaCount(this.mergeDialogId, 0, this.classGuid, true);
                }
            } else if (this.avatarsDialogId != 0) {
                MessagesController.getInstance().loadDialogPhotos(this.avatarsDialogId, 80, 0, true, this.classGuid);
            }
        }
        if ((this.currentMessageObject != null && this.currentMessageObject.isVideo()) || (this.currentBotInlineResult != null && (this.currentBotInlineResult.type.equals(MimeTypes.BASE_TYPE_VIDEO) || MessageObject.isVideoDocument(this.currentBotInlineResult.document)))) {
            onActionClick(false);
        } else if (!this.imagesArrLocals.isEmpty()) {
            obj = this.imagesArrLocals.get(i);
            User currentUser = this.parentChatActivity != null ? this.parentChatActivity.getCurrentUser() : null;
            Object obj2 = (this.parentChatActivity == null || this.parentChatActivity.isSecretChat() || currentUser == null || currentUser.bot) ? null : 1;
            if (obj instanceof PhotoEntry) {
                PhotoEntry photoEntry = (PhotoEntry) obj;
                if (photoEntry.isVideo) {
                    preparePlayer(new File(photoEntry.path), false, false);
                }
            } else if (obj2 != null && (obj instanceof SearchImage)) {
                obj2 = ((SearchImage) obj).type == 0 ? 1 : null;
            }
            if (obj2 != null) {
                this.timeItem.setVisibility(0);
            }
        }
    }

    private void onSharePressed() {
        boolean z = false;
        File file = null;
        if (this.parentActivity != null && this.allowShare) {
            try {
                if (this.currentMessageObject != null) {
                    z = this.currentMessageObject.isVideo();
                    if (!TextUtils.isEmpty(this.currentMessageObject.messageOwner.attachPath)) {
                        File file2 = new File(this.currentMessageObject.messageOwner.attachPath);
                        if (file2.exists()) {
                            file = file2;
                        }
                    }
                    if (file == null) {
                        file = FileLoader.getPathToMessage(this.currentMessageObject.messageOwner);
                    }
                } else if (this.currentFileLocation != null) {
                    TLObject tLObject = this.currentFileLocation;
                    boolean z2 = this.avatarsDialogId != 0 || this.isEvent;
                    file = FileLoader.getPathToAttach(tLObject, z2);
                }
                if (file.exists()) {
                    Intent intent = new Intent("android.intent.action.SEND");
                    if (z) {
                        intent.setType(MimeTypes.VIDEO_MP4);
                    } else if (this.currentMessageObject != null) {
                        intent.setType(this.currentMessageObject.getMimeType());
                    } else {
                        intent.setType("image/jpeg");
                    }
                    if (VERSION.SDK_INT >= 24) {
                        try {
                            intent.putExtra("android.intent.extra.STREAM", FileProvider.a(this.parentActivity, "org.ir.talaeii.provider", file));
                            intent.setFlags(1);
                        } catch (Exception e) {
                            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                        }
                    } else {
                        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                    }
                    this.parentActivity.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("ShareFile", R.string.ShareFile)), ChatActivity.startAllServices);
                    return;
                }
                Builder builder = new Builder(this.parentActivity);
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                builder.setMessage(LocaleController.getString("PleaseDownload", R.string.PleaseDownload));
                showAlertDialog(builder);
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
        }
    }

    private boolean onTouchEvent(MotionEvent motionEvent) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (this.animationInProgress != 0 || this.animationStartTime != 0) {
            return false;
        }
        if (this.currentEditMode == 2) {
            this.photoFilterView.onTouch(motionEvent);
            return true;
        } else if (this.currentEditMode == 1) {
            return true;
        } else {
            if (this.captionEditText.isPopupShowing() || this.captionEditText.isKeyboardVisible()) {
                if (motionEvent.getAction() == 1) {
                    closeCaptionEnter(true);
                }
                return true;
            } else if (this.currentEditMode == 0 && motionEvent.getPointerCount() == 1 && this.gestureDetector.onTouchEvent(motionEvent) && this.doubleTap) {
                this.doubleTap = false;
                this.moving = false;
                this.zooming = false;
                checkMinMax(false);
                return true;
            } else {
                float y;
                if (motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 5) {
                    if (this.currentEditMode == 1) {
                        this.photoCropView.cancelAnimationRunnable();
                    }
                    this.discardTap = false;
                    if (!this.scroller.isFinished()) {
                        this.scroller.abortAnimation();
                    }
                    if (!(this.draggingDown || this.changingPage)) {
                        if (this.canZoom && motionEvent.getPointerCount() == 2) {
                            this.pinchStartDistance = (float) Math.hypot((double) (motionEvent.getX(1) - motionEvent.getX(0)), (double) (motionEvent.getY(1) - motionEvent.getY(0)));
                            this.pinchStartScale = this.scale;
                            this.pinchCenterX = (motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f;
                            this.pinchCenterY = (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f;
                            this.pinchStartX = this.translationX;
                            this.pinchStartY = this.translationY;
                            this.zooming = true;
                            this.moving = false;
                            if (this.velocityTracker != null) {
                                this.velocityTracker.clear();
                            }
                        } else if (motionEvent.getPointerCount() == 1) {
                            this.moveStartX = motionEvent.getX();
                            y = motionEvent.getY();
                            this.moveStartY = y;
                            this.dragY = y;
                            this.draggingDown = false;
                            this.canDragDown = true;
                            if (this.velocityTracker != null) {
                                this.velocityTracker.clear();
                            }
                        }
                    }
                } else if (motionEvent.getActionMasked() == 2) {
                    if (this.currentEditMode == 1) {
                        this.photoCropView.cancelAnimationRunnable();
                    }
                    if (this.canZoom && motionEvent.getPointerCount() == 2 && !this.draggingDown && this.zooming && !this.changingPage) {
                        this.discardTap = true;
                        this.scale = (((float) Math.hypot((double) (motionEvent.getX(1) - motionEvent.getX(0)), (double) (motionEvent.getY(1) - motionEvent.getY(0)))) / this.pinchStartDistance) * this.pinchStartScale;
                        this.translationX = (this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - (((this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - this.pinchStartX) * (this.scale / this.pinchStartScale));
                        this.translationY = (this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - (((this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - this.pinchStartY) * (this.scale / this.pinchStartScale));
                        updateMinMax(this.scale);
                        this.containerView.invalidate();
                    } else if (motionEvent.getPointerCount() == 1) {
                        if (this.velocityTracker != null) {
                            this.velocityTracker.addMovement(motionEvent);
                        }
                        y = Math.abs(motionEvent.getX() - this.moveStartX);
                        r2 = Math.abs(motionEvent.getY() - this.dragY);
                        if (y > ((float) AndroidUtilities.dp(3.0f)) || r2 > ((float) AndroidUtilities.dp(3.0f))) {
                            this.discardTap = true;
                            if (this.qualityChooseView != null && this.qualityChooseView.getVisibility() == 0) {
                                return true;
                            }
                        }
                        if (this.placeProvider.canScrollAway() && this.currentEditMode == 0 && this.canDragDown && !this.draggingDown && this.scale == 1.0f && r2 >= ((float) AndroidUtilities.dp(30.0f)) && r2 / 2.0f > y) {
                            this.draggingDown = true;
                            this.moving = false;
                            this.dragY = motionEvent.getY();
                            if (this.isActionBarVisible && this.canShowBottom) {
                                toggleActionBar(false, true);
                            } else if (this.pickerView.getVisibility() == 0) {
                                toggleActionBar(false, true);
                                togglePhotosListView(false, true);
                                toggleCheckImageView(false);
                            }
                            return true;
                        } else if (this.draggingDown) {
                            this.translationY = motionEvent.getY() - this.dragY;
                            this.containerView.invalidate();
                        } else if (this.invalidCoords || this.animationStartTime != 0) {
                            this.invalidCoords = false;
                            this.moveStartX = motionEvent.getX();
                            this.moveStartY = motionEvent.getY();
                        } else {
                            r2 = this.moveStartX - motionEvent.getX();
                            y = this.moveStartY - motionEvent.getY();
                            if (this.moving || this.currentEditMode != 0 || ((this.scale == 1.0f && Math.abs(y) + ((float) AndroidUtilities.dp(12.0f)) < Math.abs(r2)) || this.scale != 1.0f)) {
                                if (!this.moving) {
                                    this.moving = true;
                                    this.canDragDown = false;
                                    y = BitmapDescriptorFactory.HUE_RED;
                                    r2 = BitmapDescriptorFactory.HUE_RED;
                                }
                                this.moveStartX = motionEvent.getX();
                                this.moveStartY = motionEvent.getY();
                                updateMinMax(this.scale);
                                if ((this.translationX < this.minX && !(this.currentEditMode == 0 && this.rightImage.hasImage())) || (this.translationX > this.maxX && !(this.currentEditMode == 0 && this.leftImage.hasImage()))) {
                                    r2 /= 3.0f;
                                }
                                if (this.maxY != BitmapDescriptorFactory.HUE_RED || this.minY != BitmapDescriptorFactory.HUE_RED || this.currentEditMode != 0) {
                                    if (this.translationY < this.minY || this.translationY > this.maxY) {
                                        f = y / 3.0f;
                                    }
                                    f = y;
                                } else if (this.translationY - y < this.minY) {
                                    this.translationY = this.minY;
                                } else {
                                    if (this.translationY - y > this.maxY) {
                                        this.translationY = this.maxY;
                                    }
                                    f = y;
                                }
                                this.translationX -= r2;
                                if (!(this.scale == 1.0f && this.currentEditMode == 0)) {
                                    this.translationY -= f;
                                }
                                this.containerView.invalidate();
                            }
                        }
                    }
                } else if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 6) {
                    if (this.currentEditMode == 1) {
                        this.photoCropView.startAnimationRunnable();
                    }
                    if (this.zooming) {
                        this.invalidCoords = true;
                        if (this.scale < 1.0f) {
                            updateMinMax(1.0f);
                            animateTo(1.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, true);
                        } else if (this.scale > 3.0f) {
                            y = (this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - (((this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - this.pinchStartX) * (3.0f / this.pinchStartScale));
                            f = (this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - (((this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - this.pinchStartY) * (3.0f / this.pinchStartScale));
                            updateMinMax(3.0f);
                            if (y < this.minX) {
                                y = this.minX;
                            } else if (y > this.maxX) {
                                y = this.maxX;
                            }
                            if (f < this.minY) {
                                f = this.minY;
                            } else if (f > this.maxY) {
                                f = this.maxY;
                            }
                            animateTo(3.0f, y, f, true);
                        } else {
                            checkMinMax(true);
                        }
                        this.zooming = false;
                    } else if (this.draggingDown) {
                        if (Math.abs(this.dragY - motionEvent.getY()) > ((float) getContainerViewHeight()) / 6.0f) {
                            closePhoto(true, false);
                        } else {
                            if (this.pickerView.getVisibility() == 0) {
                                toggleActionBar(true, true);
                                toggleCheckImageView(true);
                            }
                            animateTo(1.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, false);
                        }
                        this.draggingDown = false;
                    } else if (this.moving) {
                        y = this.translationX;
                        r2 = this.translationY;
                        updateMinMax(this.scale);
                        this.moving = false;
                        this.canDragDown = true;
                        if (this.velocityTracker != null && this.scale == 1.0f) {
                            this.velocityTracker.computeCurrentVelocity(1000);
                            f = this.velocityTracker.getXVelocity();
                        }
                        if (this.currentEditMode == 0) {
                            if ((this.translationX < this.minX - ((float) (getContainerViewWidth() / 3)) || r1 < ((float) (-AndroidUtilities.dp(650.0f)))) && this.rightImage.hasImage()) {
                                goToNext();
                                return true;
                            } else if ((this.translationX > this.maxX + ((float) (getContainerViewWidth() / 3)) || r1 > ((float) AndroidUtilities.dp(650.0f))) && this.leftImage.hasImage()) {
                                goToPrev();
                                return true;
                            }
                        }
                        if (this.translationX < this.minX) {
                            y = this.minX;
                        } else if (this.translationX > this.maxX) {
                            y = this.maxX;
                        }
                        f = this.translationY < this.minY ? this.minY : this.translationY > this.maxY ? this.maxY : r2;
                        animateTo(this.scale, y, f, false);
                    }
                }
                return false;
            }
        }
    }

    private void openCaptionEnter() {
        if (this.imageMoveAnimation == null && this.changeModeAnimation == null && this.currentEditMode == 0) {
            this.selectedPhotosListView.setVisibility(8);
            this.selectedPhotosListView.setEnabled(false);
            this.selectedPhotosListView.setAlpha(BitmapDescriptorFactory.HUE_RED);
            this.selectedPhotosListView.setTranslationY((float) (-AndroidUtilities.dp(10.0f)));
            this.photosCounterView.setRotationX(BitmapDescriptorFactory.HUE_RED);
            this.isPhotosListViewVisible = false;
            this.captionEditText.setTag(Integer.valueOf(1));
            this.captionEditText.openKeyboard();
            this.lastTitle = this.actionBar.getTitle();
            if (this.isCurrentVideo) {
                this.actionBar.setTitle(this.muteVideo ? LocaleController.getString("GifCaption", R.string.GifCaption) : LocaleController.getString("VideoCaption", R.string.VideoCaption));
                this.actionBar.setSubtitle(null);
                return;
            }
            this.actionBar.setTitle(LocaleController.getString("PhotoCaption", R.string.PhotoCaption));
        }
    }

    private void preparePlayer(File file, boolean z, boolean z2) {
        C3791b.a(C3791b.h() + 1);
        if (!z2) {
            this.currentPlayingVideoFile = file;
        }
        if (this.parentActivity != null) {
            this.inPreview = z2;
            releasePlayer();
            if (this.videoTextureView == null) {
                this.aspectRatioFrameLayout = new AspectRatioFrameLayout(this.parentActivity);
                this.aspectRatioFrameLayout.setVisibility(4);
                this.containerView.addView(this.aspectRatioFrameLayout, 0, LayoutHelper.createFrame(-1, -1, 17));
                this.videoTextureView = new TextureView(this.parentActivity);
                this.videoTextureView.setOpaque(false);
                this.aspectRatioFrameLayout.addView(this.videoTextureView, LayoutHelper.createFrame(-1, -1, 17));
            }
            this.textureUploaded = false;
            this.videoCrossfadeStarted = false;
            TextureView textureView = this.videoTextureView;
            this.videoCrossfadeAlpha = BitmapDescriptorFactory.HUE_RED;
            textureView.setAlpha(BitmapDescriptorFactory.HUE_RED);
            this.videoPlayButton.setImageResource(R.drawable.inline_video_play);
            if (this.videoPlayer == null) {
                long duration;
                this.videoPlayer = new VideoPlayer();
                this.videoPlayer.setTextureView(this.videoTextureView);
                this.videoPlayer.setDelegate(new VideoPlayerDelegate() {
                    public void onError(Exception exception) {
                        FileLog.e(exception);
                    }

                    public void onRenderedFirstFrame() {
                        if (!PhotoViewer.this.textureUploaded) {
                            PhotoViewer.this.textureUploaded = true;
                            PhotoViewer.this.containerView.invalidate();
                        }
                    }

                    public void onStateChanged(boolean z, int i) {
                        if (PhotoViewer.this.videoPlayer != null) {
                            if (i == 4 || i == 1) {
                                try {
                                    PhotoViewer.this.parentActivity.getWindow().clearFlags(128);
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                            } else {
                                try {
                                    PhotoViewer.this.parentActivity.getWindow().addFlags(128);
                                } catch (Throwable e2) {
                                    FileLog.e(e2);
                                }
                            }
                            if (i == 3 && PhotoViewer.this.aspectRatioFrameLayout.getVisibility() != 0) {
                                PhotoViewer.this.aspectRatioFrameLayout.setVisibility(0);
                            }
                            if (!PhotoViewer.this.videoPlayer.isPlaying() || i == 4) {
                                if (PhotoViewer.this.isPlaying) {
                                    PhotoViewer.this.isPlaying = false;
                                    PhotoViewer.this.videoPlayButton.setImageResource(R.drawable.inline_video_play);
                                    AndroidUtilities.cancelRunOnUIThread(PhotoViewer.this.updateProgressRunnable);
                                    if (i == 4) {
                                        if (PhotoViewer.this.isCurrentVideo) {
                                            if (!PhotoViewer.this.videoTimelineView.isDragging()) {
                                                PhotoViewer.this.videoTimelineView.setProgress(BitmapDescriptorFactory.HUE_RED);
                                                if (PhotoViewer.this.inPreview || PhotoViewer.this.videoTimelineView.getVisibility() != 0) {
                                                    PhotoViewer.this.videoPlayer.seekTo(0);
                                                } else {
                                                    PhotoViewer.this.videoPlayer.seekTo((long) ((int) (PhotoViewer.this.videoTimelineView.getLeftProgress() * ((float) PhotoViewer.this.videoPlayer.getDuration()))));
                                                }
                                                PhotoViewer.this.videoPlayer.pause();
                                                PhotoViewer.this.containerView.invalidate();
                                            }
                                        } else if (!PhotoViewer.this.videoPlayerSeekbar.isDragging()) {
                                            PhotoViewer.this.videoPlayerSeekbar.setProgress(BitmapDescriptorFactory.HUE_RED);
                                            PhotoViewer.this.videoPlayerControlFrameLayout.invalidate();
                                            if (PhotoViewer.this.inPreview || PhotoViewer.this.videoTimelineView.getVisibility() != 0) {
                                                PhotoViewer.this.videoPlayer.seekTo(0);
                                            } else {
                                                PhotoViewer.this.videoPlayer.seekTo((long) ((int) (PhotoViewer.this.videoTimelineView.getLeftProgress() * ((float) PhotoViewer.this.videoPlayer.getDuration()))));
                                            }
                                            PhotoViewer.this.videoPlayer.pause();
                                        }
                                    }
                                }
                            } else if (!PhotoViewer.this.isPlaying) {
                                PhotoViewer.this.isPlaying = true;
                                PhotoViewer.this.videoPlayButton.setImageResource(R.drawable.inline_video_pause);
                                AndroidUtilities.runOnUIThread(PhotoViewer.this.updateProgressRunnable);
                            }
                            PhotoViewer.this.updateVideoPlayerTime();
                        }
                    }

                    public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
                        return false;
                    }

                    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                    }

                    public void onVideoSizeChanged(int i, int i2, int i3, float f) {
                        if (PhotoViewer.this.aspectRatioFrameLayout != null) {
                            if (i3 == 90 || i3 == 270) {
                                int i4 = i;
                                i = i2;
                                i2 = i4;
                            }
                            PhotoViewer.this.aspectRatioFrameLayout.setAspectRatio(i2 == 0 ? 1.0f : (((float) i) * f) / ((float) i2), i3);
                        }
                    }
                });
                if (this.videoPlayer != null) {
                    duration = this.videoPlayer.getDuration();
                    if (duration == C3446C.TIME_UNSET) {
                        duration = 0;
                    }
                } else {
                    duration = 0;
                }
                duration /= 1000;
                int ceil = (int) Math.ceil((double) this.videoPlayerTime.getPaint().measureText(String.format("%02d:%02d / %02d:%02d", new Object[]{Long.valueOf(duration / 60), Long.valueOf(duration % 60), Long.valueOf(duration / 60), Long.valueOf(duration % 60)})));
            }
            this.videoPlayer.preparePlayer(Uri.fromFile(file), "other");
            this.videoPlayerSeekbar.setProgress(BitmapDescriptorFactory.HUE_RED);
            this.videoTimelineView.setProgress(BitmapDescriptorFactory.HUE_RED);
            if (this.currentBotInlineResult != null && (this.currentBotInlineResult.type.equals(MimeTypes.BASE_TYPE_VIDEO) || MessageObject.isVideoDocument(this.currentBotInlineResult.document))) {
                this.bottomLayout.setVisibility(0);
                this.bottomLayout.setTranslationY((float) (-AndroidUtilities.dp(48.0f)));
            }
            this.videoPlayerControlFrameLayout.setVisibility(this.isCurrentVideo ? 8 : 0);
            this.dateTextView.setVisibility(8);
            this.nameTextView.setVisibility(8);
            if (this.allowShare) {
                this.shareButton.setVisibility(8);
                this.menuItem.showSubItem(10);
            }
            this.videoPlayer.setPlayWhenReady(z);
            this.inPreview = z2;
        }
    }

    private void processOpenVideo(final String str, boolean z) {
        if (this.currentLoadingVideoRunnable != null) {
            Utilities.globalQueue.cancelRunnable(this.currentLoadingVideoRunnable);
            this.currentLoadingVideoRunnable = null;
        }
        this.videoPreviewMessageObject = null;
        setCompressItemEnabled(false, true);
        this.muteVideo = z;
        this.videoTimelineView.setVideoPath(str);
        this.compressionsCount = -1;
        this.rotationValue = 0;
        this.originalSize = new File(str).length();
        DispatchQueue dispatchQueue = Utilities.globalQueue;
        Runnable anonymousClass69 = new Runnable() {
            public void run() {
                Throwable th;
                C1285z c1285z;
                boolean z;
                if (PhotoViewer.this.currentLoadingVideoRunnable == this) {
                    c1285z = null;
                    boolean z2 = true;
                    C1246e c1289d = new C1289d(str);
                    List b = C1321h.b(c1289d, "/moov/trak/");
                    if (C1321h.a(c1289d, "/moov/trak/mdia/minf/stbl/stsd/mp4a/") == null) {
                        FileLog.d("video hasn't mp4a atom");
                    }
                    if (C1321h.a(c1289d, "/moov/trak/mdia/minf/stbl/stsd/avc1/") == null) {
                        FileLog.d("video hasn't avc1 atom");
                        z2 = false;
                    }
                    PhotoViewer.this.audioFramesSize = 0;
                    PhotoViewer.this.videoFramesSize = 0;
                    int i = 0;
                    while (i < b.size()) {
                        if (PhotoViewer.this.currentLoadingVideoRunnable == this) {
                            long j;
                            C1284y c1284y = (C1284y) ((C1248b) b.get(i));
                            long j2 = 0;
                            try {
                                C1269l c = c1284y.c();
                                C1270m c2 = c.c();
                                long[] d = c.b().b().b().d();
                                int i2 = 0;
                                while (i2 < d.length) {
                                    if (PhotoViewer.this.currentLoadingVideoRunnable == this) {
                                        j2 += d[i2];
                                        i2++;
                                    } else {
                                        return;
                                    }
                                }
                                PhotoViewer.this.videoDuration = ((float) c2.e()) / ((float) c2.d());
                                j = j2;
                                j2 = (long) ((int) (((float) (8 * j2)) / PhotoViewer.this.videoDuration));
                            } catch (Throwable e) {
                                FileLog.e(e);
                                j = 0;
                                j2 = 0;
                            }
                            try {
                                if (PhotoViewer.this.currentLoadingVideoRunnable == this) {
                                    C1285z b2 = c1284y.b();
                                    if (b2.j() == 0.0d || b2.k() == 0.0d) {
                                        PhotoViewer.this.audioFramesSize = PhotoViewer.this.audioFramesSize + j;
                                        b2 = c1285z;
                                    } else {
                                        if (c1285z == null || c1285z.j() < b2.j() || c1285z.k() < b2.k()) {
                                            try {
                                                PhotoViewer.this.originalBitrate = PhotoViewer.this.bitrate = (int) ((j2 / 100000) * 100000);
                                                if (PhotoViewer.this.bitrate > 900000) {
                                                    PhotoViewer.this.bitrate = 900000;
                                                }
                                                PhotoViewer.this.videoFramesSize = PhotoViewer.this.videoFramesSize + j;
                                            } catch (Throwable e2) {
                                                Throwable th2 = e2;
                                                c1285z = b2;
                                                th = th2;
                                            }
                                        }
                                        b2 = c1285z;
                                    }
                                    i++;
                                    c1285z = b2;
                                } else {
                                    return;
                                }
                            } catch (Exception e3) {
                                th = e3;
                            }
                        } else {
                            return;
                        }
                    }
                    z = z2;
                    if (c1285z == null) {
                        FileLog.d("video hasn't trackHeaderBox atom");
                        z = false;
                    }
                    if (PhotoViewer.this.currentLoadingVideoRunnable == this) {
                        PhotoViewer.this.currentLoadingVideoRunnable = null;
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (PhotoViewer.this.parentActivity != null) {
                                    PhotoViewer.this.videoHasAudio = z;
                                    if (z) {
                                        C1320g i = c1285z.i();
                                        if (i.equals(C1320g.f3986k)) {
                                            PhotoViewer.this.rotationValue = 90;
                                        } else if (i.equals(C1320g.f3987l)) {
                                            PhotoViewer.this.rotationValue = 180;
                                        } else if (i.equals(C1320g.f3988m)) {
                                            PhotoViewer.this.rotationValue = 270;
                                        } else {
                                            PhotoViewer.this.rotationValue = 0;
                                        }
                                        PhotoViewer.this.resultWidth = PhotoViewer.this.originalWidth = (int) c1285z.j();
                                        PhotoViewer.this.resultHeight = PhotoViewer.this.originalHeight = (int) c1285z.k();
                                        PhotoViewer.this.videoDuration = PhotoViewer.this.videoDuration * 1000.0f;
                                        PhotoViewer.this.selectedCompression = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("compress_video2", 1);
                                        if (PhotoViewer.this.originalWidth > 1280 || PhotoViewer.this.originalHeight > 1280) {
                                            PhotoViewer.this.compressionsCount = 5;
                                        } else if (PhotoViewer.this.originalWidth > 848 || PhotoViewer.this.originalHeight > 848) {
                                            PhotoViewer.this.compressionsCount = 4;
                                        } else if (PhotoViewer.this.originalWidth > 640 || PhotoViewer.this.originalHeight > 640) {
                                            PhotoViewer.this.compressionsCount = 3;
                                        } else if (PhotoViewer.this.originalWidth > 480 || PhotoViewer.this.originalHeight > 480) {
                                            PhotoViewer.this.compressionsCount = 2;
                                        } else {
                                            PhotoViewer.this.compressionsCount = 1;
                                        }
                                        PhotoViewer.this.updateWidthHeightBitrateForCompression();
                                        PhotoViewer.this.setCompressItemEnabled(PhotoViewer.this.compressionsCount > 1, true);
                                        FileLog.d("compressionsCount = " + PhotoViewer.this.compressionsCount + " w = " + PhotoViewer.this.originalWidth + " h = " + PhotoViewer.this.originalHeight);
                                        if (VERSION.SDK_INT < 18 && PhotoViewer.this.compressItem.getTag() != null) {
                                            try {
                                                MediaCodecInfo selectCodec = MediaController.selectCodec("video/avc");
                                                if (selectCodec == null) {
                                                    FileLog.d("no codec info for video/avc");
                                                    PhotoViewer.this.setCompressItemEnabled(false, true);
                                                } else {
                                                    String name = selectCodec.getName();
                                                    if (name.equals("OMX.google.h264.encoder") || name.equals("OMX.ST.VFM.H264Enc") || name.equals("OMX.Exynos.avc.enc") || name.equals("OMX.MARVELL.VIDEO.HW.CODA7542ENCODER") || name.equals("OMX.MARVELL.VIDEO.H264ENCODER") || name.equals("OMX.k3.video.encoder.avc") || name.equals("OMX.TI.DUCATI1.VIDEO.H264E")) {
                                                        FileLog.d("unsupported encoder = " + name);
                                                        PhotoViewer.this.setCompressItemEnabled(false, true);
                                                    } else if (MediaController.selectColorFormat(selectCodec, "video/avc") == 0) {
                                                        FileLog.d("no color format for video/avc");
                                                        PhotoViewer.this.setCompressItemEnabled(false, true);
                                                    }
                                                }
                                            } catch (Throwable e) {
                                                PhotoViewer.this.setCompressItemEnabled(false, true);
                                                FileLog.e(e);
                                            }
                                        }
                                        PhotoViewer.this.qualityChooseView.invalidate();
                                    } else {
                                        PhotoViewer.this.compressionsCount = 0;
                                    }
                                    PhotoViewer.this.updateVideoInfo();
                                    PhotoViewer.this.updateMuteButton();
                                }
                            }
                        });
                    }
                }
                return;
                FileLog.e(th);
                z = false;
                if (c1285z == null) {
                    FileLog.d("video hasn't trackHeaderBox atom");
                    z = false;
                }
                if (PhotoViewer.this.currentLoadingVideoRunnable == this) {
                    PhotoViewer.this.currentLoadingVideoRunnable = null;
                    AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                }
            }
        };
        this.currentLoadingVideoRunnable = anonymousClass69;
        dispatchQueue.postRunnable(anonymousClass69);
    }

    private void redraw(final int i) {
        if (i < 6 && this.containerView != null) {
            this.containerView.invalidate();
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    PhotoViewer.this.redraw(i + 1);
                }
            }, 100);
        }
    }

    private void releasePlayer() {
        if (this.videoPlayer != null) {
            this.videoPlayer.releasePlayer();
            this.videoPlayer = null;
        }
        try {
            this.parentActivity.getWindow().clearFlags(128);
        } catch (Throwable e) {
            FileLog.e(e);
        }
        if (this.aspectRatioFrameLayout != null) {
            this.containerView.removeView(this.aspectRatioFrameLayout);
            this.aspectRatioFrameLayout = null;
        }
        if (this.videoTextureView != null) {
            this.videoTextureView = null;
        }
        if (this.isPlaying) {
            this.isPlaying = false;
            this.videoPlayButton.setImageResource(R.drawable.inline_video_play);
            AndroidUtilities.cancelRunOnUIThread(this.updateProgressRunnable);
        }
        if (!this.inPreview && !this.requestingPreview) {
            this.videoPlayerControlFrameLayout.setVisibility(8);
            this.dateTextView.setVisibility(0);
            this.nameTextView.setVisibility(0);
            if (this.allowShare) {
                this.shareButton.setVisibility(0);
                this.menuItem.hideSubItem(10);
            }
        }
    }

    private void requestVideoPreview(int i) {
        if (this.videoPreviewMessageObject != null) {
            MediaController.getInstance().cancelVideoConvert(this.videoPreviewMessageObject);
        }
        boolean z = this.requestingPreview && !this.tryStartRequestPreviewOnFinish;
        this.requestingPreview = false;
        this.loadInitialVideo = false;
        this.progressView.setVisibility(4);
        if (i != 1) {
            this.tryStartRequestPreviewOnFinish = false;
            if (i == 2) {
                preparePlayer(this.currentPlayingVideoFile, false, false);
            }
        } else if (this.selectedCompression == this.compressionsCount - 1) {
            this.tryStartRequestPreviewOnFinish = false;
            if (z) {
                this.progressView.setVisibility(0);
                this.loadInitialVideo = true;
            } else {
                preparePlayer(this.currentPlayingVideoFile, false, false);
            }
        } else {
            this.requestingPreview = true;
            releasePlayer();
            if (this.videoPreviewMessageObject == null) {
                Message tLRPC$TL_message = new TLRPC$TL_message();
                tLRPC$TL_message.id = 0;
                tLRPC$TL_message.message = TtmlNode.ANONYMOUS_REGION_ID;
                tLRPC$TL_message.media = new TLRPC$TL_messageMediaEmpty();
                tLRPC$TL_message.action = new TLRPC$TL_messageActionEmpty();
                this.videoPreviewMessageObject = new MessageObject(tLRPC$TL_message, null, false);
                this.videoPreviewMessageObject.messageOwner.attachPath = new File(FileLoader.getInstance().getDirectory(4), "video_preview.mp4").getAbsolutePath();
                this.videoPreviewMessageObject.videoEditedInfo = new VideoEditedInfo();
                this.videoPreviewMessageObject.videoEditedInfo.rotationValue = this.rotationValue;
                this.videoPreviewMessageObject.videoEditedInfo.originalWidth = this.originalWidth;
                this.videoPreviewMessageObject.videoEditedInfo.originalHeight = this.originalHeight;
                this.videoPreviewMessageObject.videoEditedInfo.originalPath = this.currentPlayingVideoFile.getAbsolutePath();
            }
            VideoEditedInfo videoEditedInfo = this.videoPreviewMessageObject.videoEditedInfo;
            long j = this.startTime;
            videoEditedInfo.startTime = j;
            videoEditedInfo = this.videoPreviewMessageObject.videoEditedInfo;
            long j2 = this.endTime;
            videoEditedInfo.endTime = j2;
            if (j == -1) {
                j = 0;
            }
            if (j2 == -1) {
                j2 = (long) (this.videoDuration * 1000.0f);
            }
            if (j2 - j > 5000000) {
                this.videoPreviewMessageObject.videoEditedInfo.endTime = j + 5000000;
            }
            this.videoPreviewMessageObject.videoEditedInfo.bitrate = this.bitrate;
            this.videoPreviewMessageObject.videoEditedInfo.resultWidth = this.resultWidth;
            this.videoPreviewMessageObject.videoEditedInfo.resultHeight = this.resultHeight;
            if (!MediaController.getInstance().scheduleVideoConvert(this.videoPreviewMessageObject, true)) {
                this.tryStartRequestPreviewOnFinish = true;
            }
            this.requestingPreview = true;
            this.progressView.setVisibility(0);
        }
        this.containerView.invalidate();
    }

    private void setCompressItemEnabled(boolean z, boolean z2) {
        float f = 1.0f;
        if (this.compressItem != null) {
            if (z && this.compressItem.getTag() != null) {
                return;
            }
            if (z || this.compressItem.getTag() != null) {
                this.compressItem.setTag(z ? Integer.valueOf(1) : null);
                this.compressItem.setEnabled(z);
                this.compressItem.setClickable(z);
                if (this.compressItemAnimation != null) {
                    this.compressItemAnimation.cancel();
                    this.compressItemAnimation = null;
                }
                if (z2) {
                    this.compressItemAnimation = new AnimatorSet();
                    AnimatorSet animatorSet = this.compressItemAnimation;
                    Animator[] animatorArr = new Animator[1];
                    ImageView imageView = this.compressItem;
                    String str = "alpha";
                    float[] fArr = new float[1];
                    fArr[0] = z ? 1.0f : 0.5f;
                    animatorArr[0] = ObjectAnimator.ofFloat(imageView, str, fArr);
                    animatorSet.playTogether(animatorArr);
                    this.compressItemAnimation.setDuration(180);
                    this.compressItemAnimation.setInterpolator(decelerateInterpolator);
                    this.compressItemAnimation.start();
                    return;
                }
                ImageView imageView2 = this.compressItem;
                if (!z) {
                    f = 0.5f;
                }
                imageView2.setAlpha(f);
            }
        }
    }

    private void setCurrentCaption(CharSequence charSequence) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (this.needCaptionLayout) {
            if (this.captionTextView.getParent() != this.pickerView) {
                this.captionTextView.setBackgroundDrawable(null);
                this.containerView.removeView(this.captionTextView);
                this.pickerView.addView(this.captionTextView, LayoutHelper.createFrame(-1, -2.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 76.0f, 48.0f));
            }
        } else if (this.captionTextView.getParent() != this.containerView) {
            this.captionTextView.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            this.pickerView.removeView(this.captionTextView);
            this.containerView.addView(this.captionTextView, LayoutHelper.createFrame(-1, -2.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
        }
        if (this.isCurrentVideo) {
            this.captionTextView.setMaxLines(1);
            this.captionTextView.setSingleLine(true);
        } else {
            this.captionTextView.setSingleLine(false);
            this.captionTextView.setMaxLines(10);
        }
        if (!TextUtils.isEmpty(charSequence)) {
            Theme.createChatResources(null, true);
            CharSequence replaceEmoji = Emoji.replaceEmoji(new SpannableStringBuilder(charSequence.toString()), this.captionTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
            this.captionTextView.setTag(replaceEmoji);
            try {
                this.captionTextView.setText(replaceEmoji);
            } catch (Throwable e) {
                FileLog.e(e);
            }
            this.captionTextView.setTextColor(-1);
            TextView textView = this.captionTextView;
            if (this.bottomLayout.getVisibility() == 0 || this.pickerView.getVisibility() == 0) {
                f = 1.0f;
            }
            textView.setAlpha(f);
            TextView textView2 = this.captionTextView;
            int i = (this.bottomLayout.getVisibility() == 0 || this.pickerView.getVisibility() == 0) ? 0 : 4;
            textView2.setVisibility(i);
        } else if (this.needCaptionLayout) {
            this.captionTextView.setText(LocaleController.getString("AddCaption", R.string.AddCaption));
            this.captionTextView.setTag("empty");
            this.captionTextView.setVisibility(0);
            this.captionTextView.setTextColor(-1291845633);
        } else {
            this.captionTextView.setTextColor(-1);
            this.captionTextView.setTag(null);
            this.captionTextView.setVisibility(4);
        }
    }

    private void setImageIndex(int i, boolean z) {
        if (this.currentIndex != i && this.placeProvider != null) {
            boolean z2;
            int i2;
            if (!z) {
                this.currentThumb = null;
            }
            this.currentFileNames[0] = getFileName(i);
            this.currentFileNames[1] = getFileName(i + 1);
            this.currentFileNames[2] = getFileName(i - 1);
            this.placeProvider.willSwitchFromPhoto(this.currentMessageObject, this.currentFileLocation, this.currentIndex);
            int i3 = this.currentIndex;
            this.currentIndex = i;
            boolean z3 = false;
            Object obj = null;
            String str = null;
            boolean z4;
            if (this.imagesArr.isEmpty()) {
                Object obj2;
                if (!this.imagesArrLocations.isEmpty()) {
                    this.nameTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
                    this.dateTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
                    if (this.avatarsDialogId != UserConfig.getClientUserId() || this.avatarsArr.isEmpty()) {
                        this.menuItem.hideSubItem(6);
                    } else {
                        this.menuItem.showSubItem(6);
                    }
                    FileLocation fileLocation = this.currentFileLocation;
                    if (i < 0 || i >= this.imagesArrLocations.size()) {
                        closePhoto(false, false);
                        return;
                    }
                    this.currentFileLocation = (FileLocation) this.imagesArrLocations.get(i);
                    obj2 = (fileLocation == null || this.currentFileLocation == null || fileLocation.local_id != this.currentFileLocation.local_id || fileLocation.volume_id != this.currentFileLocation.volume_id) ? null : 1;
                    if (this.isEvent) {
                        this.actionBar.setTitle(LocaleController.getString("AttachPhoto", R.string.AttachPhoto));
                    } else {
                        this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(this.currentIndex + 1), Integer.valueOf(this.imagesArrLocations.size())}));
                    }
                    this.menuItem.showSubItem(1);
                    this.allowShare = true;
                    this.shareButton.setVisibility(this.videoPlayerControlFrameLayout.getVisibility() != 0 ? 0 : 8);
                    if (this.shareButton.getVisibility() == 0) {
                        this.menuItem.hideSubItem(10);
                    } else {
                        this.menuItem.showSubItem(10);
                    }
                    this.groupedPhotosListView.fillList();
                    obj = obj2;
                } else if (!this.imagesArrLocals.isEmpty()) {
                    if (i < 0 || i >= this.imagesArrLocals.size()) {
                        closePhoto(false, false);
                        return;
                    }
                    int i4;
                    String str2;
                    boolean z5;
                    CharSequence charSequence;
                    Object obj3 = this.imagesArrLocals.get(i);
                    if (obj3 instanceof BotInlineResult) {
                        BotInlineResult botInlineResult = (BotInlineResult) obj3;
                        this.currentBotInlineResult = botInlineResult;
                        if (botInlineResult.document != null) {
                            z3 = MessageObject.isVideoDocument(botInlineResult.document);
                            this.currentPathObject = FileLoader.getPathToAttach(botInlineResult.document).getAbsolutePath();
                            z2 = z3;
                        } else if (botInlineResult.photo != null) {
                            this.currentPathObject = FileLoader.getPathToAttach(FileLoader.getClosestPhotoSizeWithSize(botInlineResult.photo.sizes, AndroidUtilities.getPhotoSize())).getAbsolutePath();
                            z2 = false;
                        } else if (botInlineResult.content_url != null) {
                            this.currentPathObject = botInlineResult.content_url;
                            z2 = botInlineResult.type.equals(MimeTypes.BASE_TYPE_VIDEO);
                        } else {
                            z2 = false;
                        }
                        this.pickerView.setPadding(0, AndroidUtilities.dp(14.0f), 0, 0);
                        z3 = false;
                        i4 = 0;
                        str2 = null;
                        z5 = false;
                        charSequence = null;
                        z4 = z2;
                        z2 = false;
                    } else {
                        PhotoEntry photoEntry;
                        boolean z6;
                        SearchImage searchImage;
                        if (obj3 instanceof PhotoEntry) {
                            photoEntry = (PhotoEntry) obj3;
                            this.currentPathObject = photoEntry.path;
                            z3 = photoEntry.isVideo;
                            obj2 = null;
                            z6 = z3;
                        } else {
                            if (obj3 instanceof SearchImage) {
                                searchImage = (SearchImage) obj3;
                                if (searchImage.document != null) {
                                    this.currentPathObject = FileLoader.getPathToAttach(searchImage.document, true).getAbsolutePath();
                                } else {
                                    this.currentPathObject = searchImage.imageUrl;
                                }
                                if (searchImage.type == 1) {
                                    obj2 = 1;
                                    z6 = false;
                                }
                            }
                            obj2 = null;
                            z6 = false;
                        }
                        if (z6) {
                            this.muteItem.setVisibility(0);
                            this.compressItem.setVisibility(0);
                            this.isCurrentVideo = true;
                            z2 = false;
                            if (obj3 instanceof PhotoEntry) {
                                photoEntry = (PhotoEntry) obj3;
                                z2 = photoEntry.editedInfo != null && photoEntry.editedInfo.muted;
                            }
                            processOpenVideo(this.currentPathObject, z2);
                            this.videoTimelineView.setVisibility(0);
                            this.paintItem.setVisibility(8);
                            this.cropItem.setVisibility(8);
                            this.tuneItem.setVisibility(8);
                        } else {
                            this.videoTimelineView.setVisibility(8);
                            this.muteItem.setVisibility(8);
                            this.isCurrentVideo = false;
                            this.compressItem.setVisibility(8);
                            if (obj2 != null) {
                                this.pickerView.setPadding(0, AndroidUtilities.dp(14.0f), 0, 0);
                                this.paintItem.setVisibility(8);
                                this.cropItem.setVisibility(8);
                                this.tuneItem.setVisibility(8);
                            } else {
                                if (this.sendPhotoType != 1) {
                                    this.pickerView.setPadding(0, 0, 0, 0);
                                }
                                this.paintItem.setVisibility(0);
                                this.cropItem.setVisibility(0);
                                this.tuneItem.setVisibility(0);
                            }
                            this.actionBar.setSubtitle(null);
                        }
                        if (obj3 instanceof PhotoEntry) {
                            photoEntry = (PhotoEntry) obj3;
                            z3 = photoEntry.bucketId == 0 && photoEntry.dateTaken == 0 && this.imagesArrLocals.size() == 1;
                            this.fromCamera = z3;
                            charSequence = photoEntry.caption;
                            str2 = photoEntry.path;
                            i4 = photoEntry.ttl;
                            z5 = photoEntry.isFiltered;
                            z3 = photoEntry.isPainted;
                            z2 = photoEntry.isCropped;
                            z4 = z6;
                        } else if (obj3 instanceof SearchImage) {
                            searchImage = (SearchImage) obj3;
                            charSequence = searchImage.caption;
                            i4 = searchImage.ttl;
                            z5 = searchImage.isFiltered;
                            z3 = searchImage.isPainted;
                            z2 = searchImage.isCropped;
                            str2 = null;
                            z4 = z6;
                        } else {
                            z2 = false;
                            z3 = false;
                            z5 = false;
                            i4 = 0;
                            charSequence = null;
                            str2 = null;
                            z4 = z6;
                        }
                    }
                    this.bottomLayout.setVisibility(8);
                    if (!this.fromCamera) {
                        this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(this.currentIndex + 1), Integer.valueOf(this.imagesArrLocals.size())}));
                    } else if (z4) {
                        this.actionBar.setTitle(LocaleController.getString("AttachVideo", R.string.AttachVideo));
                    } else {
                        this.actionBar.setTitle(LocaleController.getString("AttachPhoto", R.string.AttachPhoto));
                    }
                    if (this.parentChatActivity != null) {
                        Chat currentChat = this.parentChatActivity.getCurrentChat();
                        if (currentChat != null) {
                            this.actionBar.setTitle(currentChat.title);
                        } else {
                            User currentUser = this.parentChatActivity.getCurrentUser();
                            if (currentUser != null) {
                                this.actionBar.setTitle(ContactsController.formatName(currentUser.first_name, currentUser.last_name));
                            }
                        }
                    }
                    if (this.sendPhotoType == 0) {
                        this.checkImageView.setChecked(this.placeProvider.isPhotoChecked(this.currentIndex), false);
                    }
                    setCurrentCaption(charSequence);
                    updateCaptionTextForCurrentPhoto(obj3);
                    this.timeItem.setColorFilter(i4 != 0 ? new PorterDuffColorFilter(-12734994, Mode.MULTIPLY) : null);
                    this.paintItem.setColorFilter(z3 ? new PorterDuffColorFilter(-12734994, Mode.MULTIPLY) : null);
                    this.cropItem.setColorFilter(z2 ? new PorterDuffColorFilter(-12734994, Mode.MULTIPLY) : null);
                    this.tuneItem.setColorFilter(z5 ? new PorterDuffColorFilter(-12734994, Mode.MULTIPLY) : null);
                    str = str2;
                    z3 = z4;
                }
            } else if (this.currentIndex < 0 || this.currentIndex >= this.imagesArr.size()) {
                closePhoto(false, false);
                return;
            } else {
                MessageObject messageObject = (MessageObject) this.imagesArr.get(this.currentIndex);
                Object obj4 = (this.currentMessageObject == null || this.currentMessageObject.getId() != messageObject.getId()) ? null : 1;
                this.currentMessageObject = messageObject;
                z4 = this.currentMessageObject.isVideo();
                boolean isInvoice = this.currentMessageObject.isInvoice();
                if (isInvoice) {
                    this.masksItem.setVisibility(8);
                    this.menuItem.hideSubItem(6);
                    this.menuItem.hideSubItem(11);
                    setCurrentCaption(this.currentMessageObject.messageOwner.media.description);
                    this.allowShare = false;
                    this.bottomLayout.setTranslationY((float) AndroidUtilities.dp(48.0f));
                    this.captionTextView.setTranslationY((float) AndroidUtilities.dp(48.0f));
                } else {
                    ActionBarMenuItem actionBarMenuItem = this.masksItem;
                    i2 = (!this.currentMessageObject.hasPhotoStickers() || ((int) this.currentMessageObject.getDialogId()) == 0) ? 8 : 0;
                    actionBarMenuItem.setVisibility(i2);
                    if (this.currentMessageObject.canDeleteMessage(null) && this.slideshowMessageId == 0) {
                        this.menuItem.showSubItem(6);
                    } else {
                        this.menuItem.hideSubItem(6);
                    }
                    if (z4) {
                        this.menuItem.showSubItem(11);
                    } else {
                        this.menuItem.hideSubItem(11);
                    }
                    if (this.nameOverride != null) {
                        this.nameTextView.setText(this.nameOverride);
                    } else if (this.currentMessageObject.isFromUser()) {
                        User user = MessagesController.getInstance().getUser(Integer.valueOf(this.currentMessageObject.messageOwner.from_id));
                        if (user != null) {
                            this.nameTextView.setText(UserObject.getUserName(user));
                        } else {
                            this.nameTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        }
                    } else {
                        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.currentMessageObject.messageOwner.to_id.channel_id));
                        if (chat != null) {
                            this.nameTextView.setText(chat.title);
                        } else {
                            this.nameTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        }
                    }
                    long j = this.dateOverride != 0 ? ((long) this.dateOverride) * 1000 : ((long) this.currentMessageObject.messageOwner.date) * 1000;
                    CharSequence formatString = LocaleController.formatString("formatDateAtTime", R.string.formatDateAtTime, new Object[]{LocaleController.getInstance().formatterYear.format(new Date(j)), LocaleController.getInstance().formatterDay.format(new Date(j))});
                    if (this.currentFileNames[0] == null || !z4) {
                        this.dateTextView.setText(formatString);
                    } else {
                        this.dateTextView.setText(String.format("%s (%s)", new Object[]{formatString, AndroidUtilities.formatFileSize((long) this.currentMessageObject.getDocument().size)}));
                    }
                    setCurrentCaption(this.currentMessageObject.caption);
                }
                if (this.currentAnimation != null) {
                    this.menuItem.hideSubItem(1);
                    this.menuItem.hideSubItem(10);
                    if (!this.currentMessageObject.canDeleteMessage(null)) {
                        this.menuItem.setVisibility(8);
                    }
                    this.allowShare = true;
                    this.shareButton.setVisibility(0);
                    this.actionBar.setTitle(LocaleController.getString("AttachGif", R.string.AttachGif));
                } else {
                    if (this.totalImagesCount + this.totalImagesCountMerge == 0 || this.needSearchImageInArr) {
                        if (this.slideshowMessageId == 0 && (this.currentMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage)) {
                            if (this.currentMessageObject.isVideo()) {
                                this.actionBar.setTitle(LocaleController.getString("AttachVideo", R.string.AttachVideo));
                            } else {
                                this.actionBar.setTitle(LocaleController.getString("AttachPhoto", R.string.AttachPhoto));
                            }
                        } else if (isInvoice) {
                            this.actionBar.setTitle(this.currentMessageObject.messageOwner.media.title);
                        }
                    } else if (this.opennedFromMedia) {
                        if (this.imagesArr.size() < this.totalImagesCount + this.totalImagesCountMerge && !this.loadingMoreImages && this.currentIndex > this.imagesArr.size() - 5) {
                            r3 = this.imagesArr.isEmpty() ? 0 : ((MessageObject) this.imagesArr.get(this.imagesArr.size() - 1)).getId();
                            i2 = 0;
                            if (!this.endReached[0] || this.mergeDialogId == 0) {
                                r5 = r3;
                            } else if (this.imagesArr.isEmpty() || ((MessageObject) this.imagesArr.get(this.imagesArr.size() - 1)).getDialogId() == this.mergeDialogId) {
                                i2 = 1;
                                r5 = r3;
                            } else {
                                i2 = 1;
                                r5 = 0;
                            }
                            SharedMediaQuery.loadMedia(i2 == 0 ? this.currentDialogId : this.mergeDialogId, 80, r5, 0, true, this.classGuid);
                            this.loadingMoreImages = true;
                        }
                        this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(this.currentIndex + 1), Integer.valueOf(this.totalImagesCount + this.totalImagesCountMerge)}));
                    } else {
                        if (this.imagesArr.size() < this.totalImagesCount + this.totalImagesCountMerge && !this.loadingMoreImages && this.currentIndex < 5) {
                            r3 = this.imagesArr.isEmpty() ? 0 : ((MessageObject) this.imagesArr.get(0)).getId();
                            i2 = 0;
                            if (!this.endReached[0] || this.mergeDialogId == 0) {
                                r5 = r3;
                            } else if (this.imagesArr.isEmpty() || ((MessageObject) this.imagesArr.get(0)).getDialogId() == this.mergeDialogId) {
                                i2 = 1;
                                r5 = r3;
                            } else {
                                i2 = 1;
                                r5 = 0;
                            }
                            SharedMediaQuery.loadMedia(i2 == 0 ? this.currentDialogId : this.mergeDialogId, 80, r5, 0, true, this.classGuid);
                            this.loadingMoreImages = true;
                        }
                        this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf((((this.totalImagesCount + this.totalImagesCountMerge) - this.imagesArr.size()) + this.currentIndex) + 1), Integer.valueOf(this.totalImagesCount + this.totalImagesCountMerge)}));
                    }
                    if (((int) this.currentDialogId) == 0) {
                        this.sendItem.setVisibility(8);
                    }
                    if (this.currentMessageObject.messageOwner.ttl == 0 || this.currentMessageObject.messageOwner.ttl >= 3600) {
                        this.allowShare = true;
                        this.menuItem.showSubItem(1);
                        this.shareButton.setVisibility(this.videoPlayerControlFrameLayout.getVisibility() != 0 ? 0 : 8);
                        if (this.shareButton.getVisibility() == 0) {
                            this.menuItem.hideSubItem(10);
                        } else {
                            this.menuItem.showSubItem(10);
                        }
                    } else {
                        this.allowShare = false;
                        this.menuItem.hideSubItem(1);
                        this.shareButton.setVisibility(8);
                        this.menuItem.hideSubItem(10);
                    }
                }
                this.groupedPhotosListView.fillList();
                obj = obj4;
                z3 = z4;
            }
            if (this.currentPlaceObject != null) {
                if (this.animationInProgress == 0) {
                    this.currentPlaceObject.imageReceiver.setVisible(true, true);
                } else {
                    this.showAfterAnimation = this.currentPlaceObject;
                }
            }
            this.currentPlaceObject = this.placeProvider.getPlaceForPhoto(this.currentMessageObject, this.currentFileLocation, this.currentIndex);
            if (this.currentPlaceObject != null) {
                if (this.animationInProgress == 0) {
                    this.currentPlaceObject.imageReceiver.setVisible(false, true);
                } else {
                    this.hideAfterAnimation = this.currentPlaceObject;
                }
            }
            if (obj == null) {
                this.draggingDown = false;
                this.translationX = BitmapDescriptorFactory.HUE_RED;
                this.translationY = BitmapDescriptorFactory.HUE_RED;
                this.scale = 1.0f;
                this.animateToX = BitmapDescriptorFactory.HUE_RED;
                this.animateToY = BitmapDescriptorFactory.HUE_RED;
                this.animateToScale = 1.0f;
                this.animationStartTime = 0;
                this.imageMoveAnimation = null;
                this.changeModeAnimation = null;
                if (this.aspectRatioFrameLayout != null) {
                    this.aspectRatioFrameLayout.setVisibility(4);
                }
                releasePlayer();
                this.pinchStartDistance = BitmapDescriptorFactory.HUE_RED;
                this.pinchStartScale = 1.0f;
                this.pinchCenterX = BitmapDescriptorFactory.HUE_RED;
                this.pinchCenterY = BitmapDescriptorFactory.HUE_RED;
                this.pinchStartX = BitmapDescriptorFactory.HUE_RED;
                this.pinchStartY = BitmapDescriptorFactory.HUE_RED;
                this.moveStartX = BitmapDescriptorFactory.HUE_RED;
                this.moveStartY = BitmapDescriptorFactory.HUE_RED;
                this.zooming = false;
                this.moving = false;
                this.doubleTap = false;
                this.invalidCoords = false;
                this.canDragDown = true;
                this.changingPage = false;
                this.switchImageAfterAnimation = 0;
                z2 = (this.imagesArrLocals.isEmpty() && (this.currentFileNames[0] == null || z3 || this.photoProgressViews[0].backgroundState == 0)) ? false : true;
                this.canZoom = z2;
                updateMinMax(this.scale);
            }
            if (z3 && str != null) {
                preparePlayer(new File(str), false, false);
            }
            if (i3 == -1) {
                setImages();
                for (i2 = 0; i2 < 3; i2++) {
                    checkProgress(i2, false);
                }
                return;
            }
            checkProgress(0, false);
            ImageReceiver imageReceiver;
            PhotoProgressView photoProgressView;
            if (i3 > this.currentIndex) {
                imageReceiver = this.rightImage;
                this.rightImage = this.centerImage;
                this.centerImage = this.leftImage;
                this.leftImage = imageReceiver;
                photoProgressView = this.photoProgressViews[0];
                this.photoProgressViews[0] = this.photoProgressViews[2];
                this.photoProgressViews[2] = photoProgressView;
                setIndexToImage(this.leftImage, this.currentIndex - 1);
                checkProgress(1, false);
                checkProgress(2, false);
            } else if (i3 < this.currentIndex) {
                imageReceiver = this.leftImage;
                this.leftImage = this.centerImage;
                this.centerImage = this.rightImage;
                this.rightImage = imageReceiver;
                photoProgressView = this.photoProgressViews[0];
                this.photoProgressViews[0] = this.photoProgressViews[1];
                this.photoProgressViews[1] = photoProgressView;
                setIndexToImage(this.rightImage, this.currentIndex + 1);
                checkProgress(1, false);
                checkProgress(2, false);
            }
        }
    }

    private void setImages() {
        if (this.animationInProgress == 0) {
            setIndexToImage(this.centerImage, this.currentIndex);
            setIndexToImage(this.rightImage, this.currentIndex + 1);
            setIndexToImage(this.leftImage, this.currentIndex - 1);
        }
    }

    private void setIndexToImage(ImageReceiver imageReceiver, int i) {
        imageReceiver.setOrientation(0, false);
        TLObject fileLocation;
        if (this.imagesArrLocals.isEmpty()) {
            int[] iArr = new int[1];
            fileLocation = getFileLocation(i, iArr);
            if (fileLocation != null) {
                MessageObject messageObject = null;
                if (!this.imagesArr.isEmpty()) {
                    messageObject = (MessageObject) this.imagesArr.get(i);
                }
                imageReceiver.setParentMessageObject(messageObject);
                if (messageObject != null) {
                    imageReceiver.setShouldGenerateQualityThumb(true);
                }
                Bitmap bitmap;
                if (messageObject != null && messageObject.isVideo()) {
                    imageReceiver.setNeedsQualityThumb(true);
                    if (messageObject.photoThumbs == null || messageObject.photoThumbs.isEmpty()) {
                        imageReceiver.setImageBitmap(this.parentActivity.getResources().getDrawable(R.drawable.photoview_placeholder));
                        return;
                    }
                    bitmap = (this.currentThumb == null || imageReceiver != this.centerImage) ? null : this.currentThumb;
                    imageReceiver.setImage(null, null, null, bitmap != null ? new BitmapDrawable(null, bitmap) : null, FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 100).location, "b", 0, null, 1);
                    return;
                } else if (messageObject == null || this.currentAnimation == null) {
                    imageReceiver.setNeedsQualityThumb(true);
                    bitmap = (this.currentThumb == null || imageReceiver != this.centerImage) ? null : this.currentThumb;
                    if (iArr[0] == 0) {
                        iArr[0] = -1;
                    }
                    PhotoSize closestPhotoSizeWithSize = messageObject != null ? FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 100) : null;
                    PhotoSize photoSize = (closestPhotoSizeWithSize == null || closestPhotoSizeWithSize.location != fileLocation) ? closestPhotoSizeWithSize : null;
                    Drawable bitmapDrawable = bitmap != null ? new BitmapDrawable(null, bitmap) : null;
                    FileLocation fileLocation2 = photoSize != null ? photoSize.location : null;
                    String str = "b";
                    int i2 = iArr[0];
                    int i3 = (this.avatarsDialogId != 0 || this.isEvent) ? 1 : 0;
                    imageReceiver.setImage(fileLocation, null, null, bitmapDrawable, fileLocation2, str, i2, null, i3);
                    return;
                } else {
                    imageReceiver.setImageBitmap(this.currentAnimation);
                    this.currentAnimation.setSecondParentView(this.containerView);
                    return;
                }
            }
            imageReceiver.setNeedsQualityThumb(true);
            imageReceiver.setParentMessageObject(null);
            if (iArr[0] == 0) {
                imageReceiver.setImageBitmap((Bitmap) null);
                return;
            } else {
                imageReceiver.setImageBitmap(this.parentActivity.getResources().getDrawable(R.drawable.photoview_placeholder));
                return;
            }
        }
        imageReceiver.setParentMessageObject(null);
        if (i < 0 || i >= this.imagesArrLocals.size()) {
            imageReceiver.setImageBitmap((Bitmap) null);
            return;
        }
        TLObject tLObject;
        String str2;
        String str3;
        boolean z;
        Object obj = this.imagesArrLocals.get(i);
        int photoSize2 = (int) (((float) AndroidUtilities.getPhotoSize()) / AndroidUtilities.density);
        Bitmap bitmap2 = null;
        if (this.currentThumb != null && imageReceiver == this.centerImage) {
            bitmap2 = this.currentThumb;
        }
        Bitmap thumbForPhoto = bitmap2 == null ? this.placeProvider.getThumbForPhoto(null, null, i) : bitmap2;
        str = null;
        TLObject tLObject2 = null;
        int i4 = 0;
        String str4 = null;
        if (obj instanceof PhotoEntry) {
            String str5;
            PhotoEntry photoEntry = (PhotoEntry) obj;
            boolean z2 = photoEntry.isVideo;
            if (photoEntry.isVideo) {
                str4 = "vthumb://" + photoEntry.imageId + ":" + photoEntry.path;
                str5 = null;
            } else {
                if (photoEntry.imagePath != null) {
                    str5 = photoEntry.imagePath;
                } else {
                    imageReceiver.setOrientation(photoEntry.orientation, false);
                    str5 = photoEntry.path;
                }
                str4 = str5;
                str5 = String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(photoSize2), Integer.valueOf(photoSize2)});
            }
            i2 = 0;
            tLObject = null;
            str2 = str5;
            str3 = str4;
            fileLocation = null;
            z = z2;
        } else if (obj instanceof BotInlineResult) {
            TLObject tLObject3;
            String str6;
            TLObject tLObject4;
            int i5;
            BotInlineResult botInlineResult = (BotInlineResult) obj;
            if (botInlineResult.type.equals(MimeTypes.BASE_TYPE_VIDEO) || MessageObject.isVideoDocument(botInlineResult.document)) {
                if (botInlineResult.document != null) {
                    tLObject3 = null;
                    str6 = null;
                    tLObject4 = botInlineResult.document.thumb.location;
                    i5 = 0;
                } else {
                    tLObject4 = null;
                    tLObject3 = null;
                    str6 = botInlineResult.thumb_url;
                    i5 = 0;
                }
            } else if (botInlineResult.type.equals("gif") && botInlineResult.document != null) {
                tLObject4 = botInlineResult.document;
                i5 = botInlineResult.document.size;
                str4 = "d";
                str6 = null;
                TLObject tLObject5 = tLObject4;
                tLObject4 = null;
                tLObject3 = tLObject5;
            } else if (botInlineResult.photo != null) {
                closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(botInlineResult.photo.sizes, AndroidUtilities.getPhotoSize());
                tLObject4 = closestPhotoSizeWithSize.location;
                i5 = closestPhotoSizeWithSize.size;
                str4 = String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(photoSize2), Integer.valueOf(photoSize2)});
                tLObject3 = null;
                str6 = null;
            } else if (botInlineResult.content_url != null) {
                str4 = botInlineResult.type.equals("gif") ? "d" : String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(photoSize2), Integer.valueOf(photoSize2)});
                tLObject4 = null;
                tLObject3 = null;
                str6 = botInlineResult.content_url;
                i5 = 0;
            } else {
                i5 = 0;
                tLObject4 = null;
                tLObject3 = null;
                str6 = null;
            }
            tLObject = tLObject4;
            str3 = str6;
            int i6 = i5;
            z = false;
            i2 = i6;
            TLObject tLObject6 = tLObject3;
            str2 = str4;
            fileLocation = tLObject6;
        } else if (obj instanceof SearchImage) {
            SearchImage searchImage = (SearchImage) obj;
            if (searchImage.imagePath != null) {
                str = searchImage.imagePath;
            } else if (searchImage.document != null) {
                tLObject2 = searchImage.document;
                i4 = searchImage.document.size;
            } else {
                str = searchImage.imageUrl;
                i4 = searchImage.size;
            }
            z = false;
            tLObject = null;
            i2 = i4;
            str2 = "d";
            fileLocation = tLObject2;
            str3 = str;
        } else {
            z = false;
            tLObject = null;
            i2 = 0;
            str2 = null;
            fileLocation = null;
            str3 = null;
        }
        if (fileLocation != null) {
            imageReceiver.setImage(fileLocation, null, "d", thumbForPhoto != null ? new BitmapDrawable(null, thumbForPhoto) : null, thumbForPhoto == null ? fileLocation.thumb.location : null, String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(photoSize2), Integer.valueOf(photoSize2)}), i2, null, 0);
        } else if (tLObject != null) {
            imageReceiver.setImage(tLObject, null, str2, thumbForPhoto != null ? new BitmapDrawable(null, thumbForPhoto) : null, null, String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(photoSize2), Integer.valueOf(photoSize2)}), i2, null, 0);
        } else {
            bitmapDrawable = thumbForPhoto != null ? new BitmapDrawable(null, thumbForPhoto) : (!z || this.parentActivity == null) ? null : this.parentActivity.getResources().getDrawable(R.drawable.nophotos);
            imageReceiver.setImage(str3, str2, bitmapDrawable, null, i2);
        }
    }

    private void setPhotoChecked() {
        if (this.placeProvider != null) {
            int photoChecked = this.placeProvider.setPhotoChecked(this.currentIndex, getCurrentVideoEditedInfo());
            boolean isPhotoChecked = this.placeProvider.isPhotoChecked(this.currentIndex);
            this.checkImageView.setChecked(isPhotoChecked, true);
            if (photoChecked >= 0) {
                if (this.placeProvider.allowGroupPhotos()) {
                    photoChecked++;
                }
                if (isPhotoChecked) {
                    this.selectedPhotosAdapter.notifyItemInserted(photoChecked);
                    this.selectedPhotosListView.smoothScrollToPosition(photoChecked);
                } else {
                    this.selectedPhotosAdapter.notifyItemRemoved(photoChecked);
                }
            }
            updateSelectedCount();
        }
    }

    private void setScaleToFill() {
        float bitmapWidth = (float) this.centerImage.getBitmapWidth();
        float containerViewWidth = (float) getContainerViewWidth();
        float bitmapHeight = (float) this.centerImage.getBitmapHeight();
        float containerViewHeight = (float) getContainerViewHeight();
        float min = Math.min(containerViewHeight / bitmapHeight, containerViewWidth / bitmapWidth);
        this.scale = Math.max(containerViewWidth / ((float) ((int) (bitmapWidth * min))), containerViewHeight / ((float) ((int) (bitmapHeight * min))));
        updateMinMax(this.scale);
    }

    private void showHint(boolean z, boolean z2) {
        if (this.containerView == null) {
            return;
        }
        if (!z || this.hintTextView != null) {
            if (this.hintTextView == null) {
                this.hintTextView = new TextView(this.containerView.getContext());
                this.hintTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
                this.hintTextView.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
                this.hintTextView.setTextSize(1, 14.0f);
                this.hintTextView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f));
                this.hintTextView.setGravity(16);
                this.hintTextView.setAlpha(BitmapDescriptorFactory.HUE_RED);
                this.containerView.addView(this.hintTextView, LayoutHelper.createFrame(-2, -2.0f, 51, 5.0f, BitmapDescriptorFactory.HUE_RED, 5.0f, 3.0f));
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
                    Runnable anonymousClass64 = new Runnable() {
                        public void run() {
                            PhotoViewer.this.hideHint();
                        }
                    };
                    this.hintHideRunnable = anonymousClass64;
                    AndroidUtilities.runOnUIThread(anonymousClass64, 2000);
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

                /* renamed from: org.telegram.ui.PhotoViewer$65$1 */
                class C50711 implements Runnable {
                    C50711() {
                    }

                    public void run() {
                        PhotoViewer.this.hideHint();
                    }
                }

                public void onAnimationCancel(Animator animator) {
                    if (animator.equals(PhotoViewer.this.hintAnimation)) {
                        PhotoViewer.this.hintAnimation = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (animator.equals(PhotoViewer.this.hintAnimation)) {
                        PhotoViewer.this.hintAnimation = null;
                        AndroidUtilities.runOnUIThread(PhotoViewer.this.hintHideRunnable = new C50711(), 2000);
                    }
                }
            });
            this.hintAnimation.setDuration(300);
            this.hintAnimation.start();
        }
    }

    private void showQualityView(final boolean z) {
        if (z) {
            this.previousCompression = this.selectedCompression;
        }
        if (this.qualityChooseViewAnimation != null) {
            this.qualityChooseViewAnimation.cancel();
        }
        this.qualityChooseViewAnimation = new AnimatorSet();
        AnimatorSet animatorSet;
        Animator[] animatorArr;
        if (z) {
            this.qualityChooseView.setTag(Integer.valueOf(1));
            animatorSet = this.qualityChooseViewAnimation;
            animatorArr = new Animator[2];
            animatorArr[0] = ObjectAnimator.ofFloat(this.pickerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(152.0f)});
            animatorArr[1] = ObjectAnimator.ofFloat(this.bottomLayout, "translationY", new float[]{(float) (-AndroidUtilities.dp(48.0f)), (float) AndroidUtilities.dp(104.0f)});
            animatorSet.playTogether(animatorArr);
        } else {
            this.qualityChooseView.setTag(null);
            animatorSet = this.qualityChooseViewAnimation;
            animatorArr = new Animator[3];
            animatorArr[0] = ObjectAnimator.ofFloat(this.qualityChooseView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(166.0f)});
            animatorArr[1] = ObjectAnimator.ofFloat(this.qualityPicker, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(166.0f)});
            animatorArr[2] = ObjectAnimator.ofFloat(this.bottomLayout, "translationY", new float[]{(float) (-AndroidUtilities.dp(48.0f)), (float) AndroidUtilities.dp(118.0f)});
            animatorSet.playTogether(animatorArr);
        }
        this.qualityChooseViewAnimation.addListener(new AnimatorListenerAdapter() {

            /* renamed from: org.telegram.ui.PhotoViewer$68$1 */
            class C50721 extends AnimatorListenerAdapter {
                C50721() {
                }

                public void onAnimationEnd(Animator animator) {
                    if (animator.equals(PhotoViewer.this.qualityChooseViewAnimation)) {
                        PhotoViewer.this.qualityChooseViewAnimation = null;
                    }
                }
            }

            public void onAnimationCancel(Animator animator) {
                PhotoViewer.this.qualityChooseViewAnimation = null;
            }

            public void onAnimationEnd(Animator animator) {
                if (animator.equals(PhotoViewer.this.qualityChooseViewAnimation)) {
                    PhotoViewer.this.qualityChooseViewAnimation = new AnimatorSet();
                    AnimatorSet access$13300;
                    Animator[] animatorArr;
                    if (z) {
                        PhotoViewer.this.qualityChooseView.setVisibility(0);
                        PhotoViewer.this.qualityPicker.setVisibility(0);
                        access$13300 = PhotoViewer.this.qualityChooseViewAnimation;
                        animatorArr = new Animator[3];
                        animatorArr[0] = ObjectAnimator.ofFloat(PhotoViewer.this.qualityChooseView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                        animatorArr[1] = ObjectAnimator.ofFloat(PhotoViewer.this.qualityPicker, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                        animatorArr[2] = ObjectAnimator.ofFloat(PhotoViewer.this.bottomLayout, "translationY", new float[]{(float) (-AndroidUtilities.dp(48.0f))});
                        access$13300.playTogether(animatorArr);
                    } else {
                        PhotoViewer.this.qualityChooseView.setVisibility(4);
                        PhotoViewer.this.qualityPicker.setVisibility(4);
                        access$13300 = PhotoViewer.this.qualityChooseViewAnimation;
                        animatorArr = new Animator[2];
                        animatorArr[0] = ObjectAnimator.ofFloat(PhotoViewer.this.pickerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                        animatorArr[1] = ObjectAnimator.ofFloat(PhotoViewer.this.bottomLayout, "translationY", new float[]{(float) (-AndroidUtilities.dp(48.0f))});
                        access$13300.playTogether(animatorArr);
                    }
                    PhotoViewer.this.qualityChooseViewAnimation.addListener(new C50721());
                    PhotoViewer.this.qualityChooseViewAnimation.setDuration(200);
                    PhotoViewer.this.qualityChooseViewAnimation.setInterpolator(new AccelerateInterpolator());
                    PhotoViewer.this.qualityChooseViewAnimation.start();
                }
            }
        });
        this.qualityChooseViewAnimation.setDuration(200);
        this.qualityChooseViewAnimation.setInterpolator(new DecelerateInterpolator());
        this.qualityChooseViewAnimation.start();
    }

    private void toggleActionBar(boolean z, boolean z2) {
        float f = 1.0f;
        if (z) {
            this.actionBar.setVisibility(0);
            if (this.canShowBottom) {
                this.bottomLayout.setVisibility(0);
                if (this.captionTextView.getTag() != null) {
                    this.captionTextView.setVisibility(0);
                }
            }
        }
        this.isActionBarVisible = z;
        this.actionBar.setEnabled(z);
        this.bottomLayout.setEnabled(z);
        if (z2) {
            Collection arrayList = new ArrayList();
            ActionBar actionBar = this.actionBar;
            String str = "alpha";
            float[] fArr = new float[1];
            fArr[0] = z ? 1.0f : 0.0f;
            arrayList.add(ObjectAnimator.ofFloat(actionBar, str, fArr));
            FrameLayout frameLayout = this.bottomLayout;
            str = "alpha";
            fArr = new float[1];
            fArr[0] = z ? 1.0f : 0.0f;
            arrayList.add(ObjectAnimator.ofFloat(frameLayout, str, fArr));
            GroupedPhotosListView groupedPhotosListView = this.groupedPhotosListView;
            str = "alpha";
            fArr = new float[1];
            fArr[0] = z ? 1.0f : 0.0f;
            arrayList.add(ObjectAnimator.ofFloat(groupedPhotosListView, str, fArr));
            if (this.captionTextView.getTag() != null) {
                TextView textView = this.captionTextView;
                String str2 = "alpha";
                float[] fArr2 = new float[1];
                if (!z) {
                    f = BitmapDescriptorFactory.HUE_RED;
                }
                fArr2[0] = f;
                arrayList.add(ObjectAnimator.ofFloat(textView, str2, fArr2));
            }
            this.currentActionBarAnimation = new AnimatorSet();
            this.currentActionBarAnimation.playTogether(arrayList);
            if (!z) {
                this.currentActionBarAnimation.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        if (PhotoViewer.this.currentActionBarAnimation != null && PhotoViewer.this.currentActionBarAnimation.equals(animator)) {
                            PhotoViewer.this.actionBar.setVisibility(8);
                            if (PhotoViewer.this.canShowBottom) {
                                PhotoViewer.this.bottomLayout.setVisibility(8);
                                if (PhotoViewer.this.captionTextView.getTag() != null) {
                                    PhotoViewer.this.captionTextView.setVisibility(4);
                                }
                            }
                            PhotoViewer.this.currentActionBarAnimation = null;
                        }
                    }
                });
            }
            this.currentActionBarAnimation.setDuration(200);
            this.currentActionBarAnimation.start();
            return;
        }
        this.actionBar.setAlpha(z ? 1.0f : BitmapDescriptorFactory.HUE_RED);
        this.bottomLayout.setAlpha(z ? 1.0f : BitmapDescriptorFactory.HUE_RED);
        this.groupedPhotosListView.setAlpha(z ? 1.0f : BitmapDescriptorFactory.HUE_RED);
        if (this.captionTextView.getTag() != null) {
            textView = this.captionTextView;
            if (!z) {
                f = BitmapDescriptorFactory.HUE_RED;
            }
            textView.setAlpha(f);
        }
        if (!z) {
            this.actionBar.setVisibility(8);
            if (this.canShowBottom) {
                this.bottomLayout.setVisibility(8);
                if (this.captionTextView.getTag() != null) {
                    this.captionTextView.setVisibility(4);
                }
            }
        }
    }

    private void toggleCheckImageView(boolean z) {
        float f = 1.0f;
        AnimatorSet animatorSet = new AnimatorSet();
        Collection arrayList = new ArrayList();
        FrameLayout frameLayout = this.pickerView;
        String str = "alpha";
        float[] fArr = new float[1];
        fArr[0] = z ? 1.0f : 0.0f;
        arrayList.add(ObjectAnimator.ofFloat(frameLayout, str, fArr));
        if (this.needCaptionLayout) {
            TextView textView = this.captionTextView;
            str = "alpha";
            fArr = new float[1];
            fArr[0] = z ? 1.0f : 0.0f;
            arrayList.add(ObjectAnimator.ofFloat(textView, str, fArr));
        }
        if (this.sendPhotoType == 0) {
            CheckBox checkBox = this.checkImageView;
            str = "alpha";
            fArr = new float[1];
            fArr[0] = z ? 1.0f : 0.0f;
            arrayList.add(ObjectAnimator.ofFloat(checkBox, str, fArr));
            CounterView counterView = this.photosCounterView;
            String str2 = "alpha";
            float[] fArr2 = new float[1];
            if (!z) {
                f = 0.0f;
            }
            fArr2[0] = f;
            arrayList.add(ObjectAnimator.ofFloat(counterView, str2, fArr2));
        }
        animatorSet.playTogether(arrayList);
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    private void togglePhotosListView(boolean z, boolean z2) {
        float f = 1.0f;
        if (z != this.isPhotosListViewVisible) {
            if (z) {
                this.selectedPhotosListView.setVisibility(0);
            }
            this.isPhotosListViewVisible = z;
            this.selectedPhotosListView.setEnabled(z);
            CounterView counterView;
            if (z2) {
                Collection arrayList = new ArrayList();
                RecyclerListView recyclerListView = this.selectedPhotosListView;
                String str = "alpha";
                float[] fArr = new float[1];
                fArr[0] = z ? 1.0f : 0.0f;
                arrayList.add(ObjectAnimator.ofFloat(recyclerListView, str, fArr));
                recyclerListView = this.selectedPhotosListView;
                str = "translationY";
                fArr = new float[1];
                fArr[0] = z ? BitmapDescriptorFactory.HUE_RED : (float) (-AndroidUtilities.dp(10.0f));
                arrayList.add(ObjectAnimator.ofFloat(recyclerListView, str, fArr));
                counterView = this.photosCounterView;
                String str2 = "rotationX";
                float[] fArr2 = new float[1];
                if (!z) {
                    f = BitmapDescriptorFactory.HUE_RED;
                }
                fArr2[0] = f;
                arrayList.add(ObjectAnimator.ofFloat(counterView, str2, fArr2));
                this.currentListViewAnimation = new AnimatorSet();
                this.currentListViewAnimation.playTogether(arrayList);
                if (!z) {
                    this.currentListViewAnimation.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            if (PhotoViewer.this.currentListViewAnimation != null && PhotoViewer.this.currentListViewAnimation.equals(animator)) {
                                PhotoViewer.this.selectedPhotosListView.setVisibility(8);
                                PhotoViewer.this.currentListViewAnimation = null;
                            }
                        }
                    });
                }
                this.currentListViewAnimation.setDuration(200);
                this.currentListViewAnimation.start();
                return;
            }
            this.selectedPhotosListView.setAlpha(z ? 1.0f : BitmapDescriptorFactory.HUE_RED);
            this.selectedPhotosListView.setTranslationY(z ? BitmapDescriptorFactory.HUE_RED : (float) (-AndroidUtilities.dp(10.0f)));
            counterView = this.photosCounterView;
            if (!z) {
                f = BitmapDescriptorFactory.HUE_RED;
            }
            counterView.setRotationX(f);
            if (!z) {
                this.selectedPhotosListView.setVisibility(8);
            }
        }
    }

    private void updateCaptionTextForCurrentPhoto(Object obj) {
        CharSequence charSequence = null;
        if (obj instanceof PhotoEntry) {
            charSequence = ((PhotoEntry) obj).caption;
        } else if (!(obj instanceof BotInlineResult) && (obj instanceof SearchImage)) {
            charSequence = ((SearchImage) obj).caption;
        }
        if (charSequence == null || charSequence.length() == 0) {
            this.captionEditText.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
        } else {
            this.captionEditText.setFieldText(charSequence);
        }
    }

    private void updateMinMax(float f) {
        int imageWidth = ((int) ((((float) this.centerImage.getImageWidth()) * f) - ((float) getContainerViewWidth()))) / 2;
        int imageHeight = ((int) ((((float) this.centerImage.getImageHeight()) * f) - ((float) getContainerViewHeight()))) / 2;
        if (imageWidth > 0) {
            this.minX = (float) (-imageWidth);
            this.maxX = (float) imageWidth;
        } else {
            this.maxX = BitmapDescriptorFactory.HUE_RED;
            this.minX = BitmapDescriptorFactory.HUE_RED;
        }
        if (imageHeight > 0) {
            this.minY = (float) (-imageHeight);
            this.maxY = (float) imageHeight;
        } else {
            this.maxY = BitmapDescriptorFactory.HUE_RED;
            this.minY = BitmapDescriptorFactory.HUE_RED;
        }
        if (this.currentEditMode == 1) {
            this.maxX += this.photoCropView.getLimitX();
            this.maxY += this.photoCropView.getLimitY();
            this.minX -= this.photoCropView.getLimitWidth();
            this.minY -= this.photoCropView.getLimitHeight();
        }
    }

    private void updateSelectedCount() {
        if (this.placeProvider != null) {
            int selectedCount = this.placeProvider.getSelectedCount();
            this.photosCounterView.setCount(selectedCount);
            if (selectedCount == 0) {
                togglePhotosListView(false, true);
            }
        }
    }

    private void updateVideoInfo() {
        if (this.actionBar != null) {
            if (this.compressionsCount == 0) {
                this.actionBar.setSubtitle(null);
                return;
            }
            int i;
            int i2;
            if (this.selectedCompression == 0) {
                this.compressItem.setImageResource(R.drawable.video_240);
            } else if (this.selectedCompression == 1) {
                this.compressItem.setImageResource(R.drawable.video_360);
            } else if (this.selectedCompression == 2) {
                this.compressItem.setImageResource(R.drawable.video_480);
            } else if (this.selectedCompression == 3) {
                this.compressItem.setImageResource(R.drawable.video_720);
            } else if (this.selectedCompression == 4) {
                this.compressItem.setImageResource(R.drawable.video_1080);
            }
            this.estimatedDuration = (long) Math.ceil((double) ((this.videoTimelineView.getRightProgress() - this.videoTimelineView.getLeftProgress()) * this.videoDuration));
            if (this.compressItem.getTag() == null || this.selectedCompression == this.compressionsCount - 1) {
                i = (this.rotationValue == 90 || this.rotationValue == 270) ? this.originalHeight : this.originalWidth;
                i2 = (this.rotationValue == 90 || this.rotationValue == 270) ? this.originalWidth : this.originalHeight;
                this.estimatedSize = (int) (((float) this.originalSize) * (((float) this.estimatedDuration) / this.videoDuration));
            } else {
                i = (this.rotationValue == 90 || this.rotationValue == 270) ? this.resultHeight : this.resultWidth;
                i2 = (this.rotationValue == 90 || this.rotationValue == 270) ? this.resultWidth : this.resultHeight;
                this.estimatedSize = (int) (((float) (this.audioFramesSize + this.videoFramesSize)) * (((float) this.estimatedDuration) / this.videoDuration));
                this.estimatedSize += (this.estimatedSize / TLRPC.MESSAGE_FLAG_EDITED) * 16;
            }
            if (this.videoTimelineView.getLeftProgress() == BitmapDescriptorFactory.HUE_RED) {
                this.startTime = -1;
            } else {
                this.startTime = ((long) (this.videoTimelineView.getLeftProgress() * this.videoDuration)) * 1000;
            }
            if (this.videoTimelineView.getRightProgress() == 1.0f) {
                this.endTime = -1;
            } else {
                this.endTime = ((long) (this.videoTimelineView.getRightProgress() * this.videoDuration)) * 1000;
            }
            String format = String.format("%dx%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
            int ceil = ((int) Math.ceil((double) (this.estimatedDuration / 1000))) - (((int) ((this.estimatedDuration / 1000) / 60)) * 60);
            String format2 = String.format("%d:%02d, ~%s", new Object[]{Integer.valueOf((int) ((this.estimatedDuration / 1000) / 60)), Integer.valueOf(ceil), AndroidUtilities.formatFileSize((long) this.estimatedSize)});
            this.currentSubtitle = String.format("%s, %s", new Object[]{format, format2});
            this.actionBar.setSubtitle(this.muteVideo ? null : this.currentSubtitle);
        }
    }

    private void updateVideoPlayerTime() {
        CharSequence format;
        if (this.videoPlayer == null) {
            format = String.format("%02d:%02d / %02d:%02d", new Object[]{Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)});
        } else {
            long currentPosition = this.videoPlayer.getCurrentPosition();
            long duration = this.videoPlayer.getDuration();
            if (duration == C3446C.TIME_UNSET || currentPosition == C3446C.TIME_UNSET) {
                format = String.format("%02d:%02d / %02d:%02d", new Object[]{Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)});
            } else {
                if (!this.inPreview && this.videoTimelineView.getVisibility() == 0) {
                    duration = (long) (((float) duration) * (this.videoTimelineView.getRightProgress() - this.videoTimelineView.getLeftProgress()));
                    currentPosition = (long) (((float) currentPosition) - (this.videoTimelineView.getLeftProgress() * ((float) duration)));
                    if (currentPosition > duration) {
                        currentPosition = duration;
                    }
                }
                currentPosition /= 1000;
                duration /= 1000;
                format = String.format("%02d:%02d / %02d:%02d", new Object[]{Long.valueOf(currentPosition / 60), Long.valueOf(currentPosition % 60), Long.valueOf(duration / 60), Long.valueOf(duration % 60)});
            }
        }
        if (!TextUtils.equals(this.videoPlayerTime.getText(), format)) {
            this.videoPlayerTime.setText(format);
        }
    }

    private void updateWidthHeightBitrateForCompression() {
        if (this.compressionsCount > 0) {
            if (this.selectedCompression >= this.compressionsCount) {
                this.selectedCompression = this.compressionsCount - 1;
            }
            if (this.selectedCompression != this.compressionsCount - 1) {
                float f;
                int i;
                switch (this.selectedCompression) {
                    case 0:
                        f = 432.0f;
                        i = 400000;
                        break;
                    case 1:
                        f = 640.0f;
                        i = 900000;
                        break;
                    case 2:
                        f = 848.0f;
                        i = 1100000;
                        break;
                    default:
                        i = 2500000;
                        f = 1280.0f;
                        break;
                }
                f = this.originalWidth > this.originalHeight ? f / ((float) this.originalWidth) : f / ((float) this.originalHeight);
                this.resultWidth = Math.round((((float) this.originalWidth) * f) / 2.0f) * 2;
                this.resultHeight = Math.round((((float) this.originalHeight) * f) / 2.0f) * 2;
                if (this.bitrate != 0) {
                    this.bitrate = Math.min(i, (int) (((float) this.originalBitrate) / f));
                    this.videoFramesSize = (long) ((((float) (this.bitrate / 8)) * this.videoDuration) / 1000.0f);
                }
            }
        }
    }

    public void closePhoto(boolean z, boolean z2) {
        if (z2 || this.currentEditMode == 0) {
            if (this.qualityChooseView == null || this.qualityChooseView.getTag() == null) {
                try {
                    if (this.visibleDialog != null) {
                        this.visibleDialog.dismiss();
                        this.visibleDialog = null;
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                if (this.currentEditMode != 0) {
                    if (this.currentEditMode == 2) {
                        this.photoFilterView.shutdown();
                        this.containerView.removeView(this.photoFilterView);
                        this.photoFilterView = null;
                    } else if (this.currentEditMode == 1) {
                        this.editorDoneLayout.setVisibility(8);
                        this.photoCropView.setVisibility(8);
                    }
                    this.currentEditMode = 0;
                }
                if (this.parentActivity != null && this.isVisible && !checkAnimation() && this.placeProvider != null) {
                    if (!this.captionEditText.hideActionMode() || z2) {
                        releasePlayer();
                        this.captionEditText.onDestroy();
                        this.parentChatActivity = null;
                        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidFailedLoad);
                        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidLoaded);
                        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileLoadProgressChanged);
                        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.mediaCountDidLoaded);
                        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.mediaDidLoaded);
                        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.dialogPhotosLoaded);
                        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
                        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FilePreparingFailed);
                        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileNewChunkAvailable);
                        ConnectionsManager.getInstance().cancelRequestsForGuid(this.classGuid);
                        this.isActionBarVisible = false;
                        if (this.velocityTracker != null) {
                            this.velocityTracker.recycle();
                            this.velocityTracker = null;
                        }
                        ConnectionsManager.getInstance().cancelRequestsForGuid(this.classGuid);
                        final PlaceProviderObject placeForPhoto = this.placeProvider.getPlaceForPhoto(this.currentMessageObject, this.currentFileLocation, this.currentIndex);
                        Animator[] animatorArr;
                        if (z) {
                            Rect drawRegion;
                            this.animationInProgress = 1;
                            this.animatingImageView.setVisibility(0);
                            this.containerView.invalidate();
                            AnimatorSet animatorSet = new AnimatorSet();
                            ViewGroup.LayoutParams layoutParams = this.animatingImageView.getLayoutParams();
                            int orientation = this.centerImage.getOrientation();
                            int i = 0;
                            if (!(placeForPhoto == null || placeForPhoto.imageReceiver == null)) {
                                i = placeForPhoto.imageReceiver.getAnimatedOrientation();
                            }
                            if (i == 0) {
                                i = orientation;
                            }
                            this.animatingImageView.setOrientation(i);
                            if (placeForPhoto != null) {
                                this.animatingImageView.setNeedRadius(placeForPhoto.radius != 0);
                                drawRegion = placeForPhoto.imageReceiver.getDrawRegion();
                                layoutParams.width = drawRegion.right - drawRegion.left;
                                layoutParams.height = drawRegion.bottom - drawRegion.top;
                                this.animatingImageView.setImageBitmap(placeForPhoto.thumb);
                            } else {
                                this.animatingImageView.setNeedRadius(false);
                                layoutParams.width = this.centerImage.getImageWidth();
                                layoutParams.height = this.centerImage.getImageHeight();
                                this.animatingImageView.setImageBitmap(this.centerImage.getBitmap());
                                drawRegion = null;
                            }
                            this.animatingImageView.setLayoutParams(layoutParams);
                            float f = ((float) AndroidUtilities.displaySize.x) / ((float) layoutParams.width);
                            float f2 = ((float) ((VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + AndroidUtilities.displaySize.y)) / ((float) layoutParams.height);
                            if (f <= f2) {
                                f2 = f;
                            }
                            f = (((float) ((VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + AndroidUtilities.displaySize.y)) - ((((float) layoutParams.height) * this.scale) * f2)) / 2.0f;
                            this.animatingImageView.setTranslationX(((((float) AndroidUtilities.displaySize.x) - ((((float) layoutParams.width) * this.scale) * f2)) / 2.0f) + this.translationX);
                            this.animatingImageView.setTranslationY(f + this.translationY);
                            this.animatingImageView.setScaleX(this.scale * f2);
                            this.animatingImageView.setScaleY(f2 * this.scale);
                            if (placeForPhoto != null) {
                                placeForPhoto.imageReceiver.setVisible(false, true);
                                int abs = Math.abs(drawRegion.left - placeForPhoto.imageReceiver.getImageX());
                                int abs2 = Math.abs(drawRegion.top - placeForPhoto.imageReceiver.getImageY());
                                int[] iArr = new int[2];
                                placeForPhoto.parentView.getLocationInWindow(iArr);
                                orientation = ((iArr[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight)) - (placeForPhoto.viewY + drawRegion.top)) + placeForPhoto.clipTopAddition;
                                if (orientation < 0) {
                                    orientation = 0;
                                }
                                int height = (((placeForPhoto.viewY + drawRegion.top) + (drawRegion.bottom - drawRegion.top)) - ((placeForPhoto.parentView.getHeight() + iArr[1]) - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight))) + placeForPhoto.clipBottomAddition;
                                if (height < 0) {
                                    height = 0;
                                }
                                orientation = Math.max(orientation, abs2);
                                height = Math.max(height, abs2);
                                this.animationValues[0][0] = this.animatingImageView.getScaleX();
                                this.animationValues[0][1] = this.animatingImageView.getScaleY();
                                this.animationValues[0][2] = this.animatingImageView.getTranslationX();
                                this.animationValues[0][3] = this.animatingImageView.getTranslationY();
                                this.animationValues[0][4] = BitmapDescriptorFactory.HUE_RED;
                                this.animationValues[0][5] = BitmapDescriptorFactory.HUE_RED;
                                this.animationValues[0][6] = BitmapDescriptorFactory.HUE_RED;
                                this.animationValues[0][7] = BitmapDescriptorFactory.HUE_RED;
                                this.animationValues[1][0] = placeForPhoto.scale;
                                this.animationValues[1][1] = placeForPhoto.scale;
                                this.animationValues[1][2] = ((float) placeForPhoto.viewX) + (((float) drawRegion.left) * placeForPhoto.scale);
                                this.animationValues[1][3] = (((float) drawRegion.top) * placeForPhoto.scale) + ((float) placeForPhoto.viewY);
                                this.animationValues[1][4] = ((float) abs) * placeForPhoto.scale;
                                this.animationValues[1][5] = ((float) orientation) * placeForPhoto.scale;
                                this.animationValues[1][6] = ((float) height) * placeForPhoto.scale;
                                this.animationValues[1][7] = (float) placeForPhoto.radius;
                                r0 = new Animator[3];
                                r0[1] = ObjectAnimator.ofInt(this.backgroundDrawable, "alpha", new int[]{0});
                                r0[2] = ObjectAnimator.ofFloat(this.containerView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                animatorSet.playTogether(r0);
                            } else {
                                i = (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + AndroidUtilities.displaySize.y;
                                animatorArr = new Animator[4];
                                animatorArr[0] = ObjectAnimator.ofInt(this.backgroundDrawable, "alpha", new int[]{0});
                                animatorArr[1] = ObjectAnimator.ofFloat(this.animatingImageView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                ClippingImageView clippingImageView = this.animatingImageView;
                                String str = "translationY";
                                float[] fArr = new float[1];
                                fArr[0] = this.translationY >= BitmapDescriptorFactory.HUE_RED ? (float) i : (float) (-i);
                                animatorArr[2] = ObjectAnimator.ofFloat(clippingImageView, str, fArr);
                                animatorArr[3] = ObjectAnimator.ofFloat(this.containerView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                animatorSet.playTogether(animatorArr);
                            }
                            this.animationEndRunnable = new Runnable() {
                                public void run() {
                                    if (VERSION.SDK_INT >= 18) {
                                        PhotoViewer.this.containerView.setLayerType(0, null);
                                    }
                                    PhotoViewer.this.animationInProgress = 0;
                                    PhotoViewer.this.onPhotoClosed(placeForPhoto);
                                }
                            };
                            animatorSet.setDuration(200);
                            animatorSet.addListener(new AnimatorListenerAdapter() {

                                /* renamed from: org.telegram.ui.PhotoViewer$57$1 */
                                class C50691 implements Runnable {
                                    C50691() {
                                    }

                                    public void run() {
                                        if (PhotoViewer.this.animationEndRunnable != null) {
                                            PhotoViewer.this.animationEndRunnable.run();
                                            PhotoViewer.this.animationEndRunnable = null;
                                        }
                                    }
                                }

                                public void onAnimationEnd(Animator animator) {
                                    AndroidUtilities.runOnUIThread(new C50691());
                                }
                            });
                            this.transitionAnimationStartTime = System.currentTimeMillis();
                            if (VERSION.SDK_INT >= 18) {
                                this.containerView.setLayerType(2, null);
                            }
                            animatorSet.start();
                        } else {
                            AnimatorSet animatorSet2 = new AnimatorSet();
                            animatorArr = new Animator[4];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.containerView, "scaleX", new float[]{0.9f});
                            animatorArr[1] = ObjectAnimator.ofFloat(this.containerView, "scaleY", new float[]{0.9f});
                            animatorArr[2] = ObjectAnimator.ofInt(this.backgroundDrawable, "alpha", new int[]{0});
                            animatorArr[3] = ObjectAnimator.ofFloat(this.containerView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                            animatorSet2.playTogether(animatorArr);
                            this.animationInProgress = 2;
                            this.animationEndRunnable = new Runnable() {
                                public void run() {
                                    if (PhotoViewer.this.containerView != null) {
                                        if (VERSION.SDK_INT >= 18) {
                                            PhotoViewer.this.containerView.setLayerType(0, null);
                                        }
                                        PhotoViewer.this.animationInProgress = 0;
                                        PhotoViewer.this.onPhotoClosed(placeForPhoto);
                                        PhotoViewer.this.containerView.setScaleX(1.0f);
                                        PhotoViewer.this.containerView.setScaleY(1.0f);
                                    }
                                }
                            };
                            animatorSet2.setDuration(200);
                            animatorSet2.addListener(new AnimatorListenerAdapter() {
                                public void onAnimationEnd(Animator animator) {
                                    if (PhotoViewer.this.animationEndRunnable != null) {
                                        PhotoViewer.this.animationEndRunnable.run();
                                        PhotoViewer.this.animationEndRunnable = null;
                                    }
                                }
                            });
                            this.transitionAnimationStartTime = System.currentTimeMillis();
                            if (VERSION.SDK_INT >= 18) {
                                this.containerView.setLayerType(2, null);
                            }
                            animatorSet2.start();
                        }
                        if (this.currentAnimation != null) {
                            this.currentAnimation.setSecondParentView(null);
                            this.currentAnimation = null;
                            this.centerImage.setImageBitmap((Drawable) null);
                        }
                        if (!this.placeProvider.canScrollAway()) {
                            this.placeProvider.cancelButtonPressed();
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            this.qualityPicker.cancelButton.callOnClick();
        } else if (this.currentEditMode != 3 || this.photoPaintView == null) {
            if (this.currentEditMode == 1) {
                this.photoCropView.cancelAnimationRunnable();
            }
            switchToEditMode(0);
        } else {
            this.photoPaintView.maybeShowDismissalAlert(this, this.parentActivity, new Runnable() {
                public void run() {
                    PhotoViewer.this.switchToEditMode(0);
                }
            });
        }
    }

    public void destroyPhotoViewer() {
        if (this.parentActivity != null && this.windowView != null) {
            releasePlayer();
            try {
                if (this.windowView.getParent() != null) {
                    ((WindowManager) this.parentActivity.getSystemService("window")).removeViewImmediate(this.windowView);
                }
                this.windowView = null;
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (this.captionEditText != null) {
                this.captionEditText.onDestroy();
            }
            Instance = null;
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        String str;
        int i2;
        if (i == NotificationCenter.FileDidFailedLoad) {
            str = (String) objArr[0];
            i2 = 0;
            while (i2 < 3) {
                if (this.currentFileNames[i2] == null || !this.currentFileNames[i2].equals(str)) {
                    i2++;
                } else {
                    this.photoProgressViews[i2].setProgress(1.0f, true);
                    checkProgress(i2, true);
                    return;
                }
            }
        } else if (i == NotificationCenter.FileDidLoaded) {
            str = (String) objArr[0];
            i2 = 0;
            while (i2 < 3) {
                if (this.currentFileNames[i2] == null || !this.currentFileNames[i2].equals(str)) {
                    i2++;
                } else {
                    this.photoProgressViews[i2].setProgress(1.0f, true);
                    checkProgress(i2, true);
                    if (i2 == 0) {
                        if (this.currentMessageObject == null || !this.currentMessageObject.isVideo()) {
                            if (this.currentBotInlineResult == null) {
                                return;
                            }
                            if (!(this.currentBotInlineResult.type.equals(MimeTypes.BASE_TYPE_VIDEO) || MessageObject.isVideoDocument(this.currentBotInlineResult.document))) {
                                return;
                            }
                        }
                        onActionClick(false);
                        return;
                    }
                    return;
                }
            }
        } else if (i == NotificationCenter.FileLoadProgressChanged) {
            str = (String) objArr[0];
            r2 = 0;
            while (r2 < 3) {
                if (this.currentFileNames[r2] != null && this.currentFileNames[r2].equals(str)) {
                    this.photoProgressViews[r2].setProgress(((Float) objArr[1]).floatValue(), true);
                }
                r2++;
            }
        } else if (i == NotificationCenter.dialogPhotosLoaded) {
            i2 = ((Integer) objArr[3]).intValue();
            if (this.avatarsDialogId == ((Integer) objArr[0]).intValue() && this.classGuid == i2) {
                boolean booleanValue = ((Boolean) objArr[2]).booleanValue();
                r0 = (ArrayList) objArr[4];
                if (!r0.isEmpty()) {
                    this.imagesArrLocations.clear();
                    this.imagesArrLocationsSizes.clear();
                    this.avatarsArr.clear();
                    r3 = 0;
                    r4 = -1;
                    while (r3 < r0.size()) {
                        Photo photo = (Photo) r0.get(r3);
                        if (!(photo == null || (photo instanceof TLRPC$TL_photoEmpty))) {
                            if (photo.sizes == null) {
                                r2 = r4;
                                r3++;
                                r4 = r2;
                            } else {
                                PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, 640);
                                if (closestPhotoSizeWithSize != null) {
                                    if (r4 == -1 && this.currentFileLocation != null) {
                                        for (r5 = 0; r5 < photo.sizes.size(); r5++) {
                                            PhotoSize photoSize = (PhotoSize) photo.sizes.get(r5);
                                            if (photoSize.location.local_id == this.currentFileLocation.local_id && photoSize.location.volume_id == this.currentFileLocation.volume_id) {
                                                r4 = this.imagesArrLocations.size();
                                                break;
                                            }
                                        }
                                    }
                                    this.imagesArrLocations.add(closestPhotoSizeWithSize.location);
                                    this.imagesArrLocationsSizes.add(Integer.valueOf(closestPhotoSizeWithSize.size));
                                    this.avatarsArr.add(photo);
                                }
                            }
                        }
                        r2 = r4;
                        r3++;
                        r4 = r2;
                    }
                    if (this.avatarsArr.isEmpty()) {
                        this.menuItem.hideSubItem(6);
                    } else {
                        this.menuItem.showSubItem(6);
                    }
                    this.needSearchImageInArr = false;
                    this.currentIndex = -1;
                    if (r4 != -1) {
                        setImageIndex(r4, true);
                    } else {
                        this.avatarsArr.add(0, new TLRPC$TL_photoEmpty());
                        this.imagesArrLocations.add(0, this.currentFileLocation);
                        this.imagesArrLocationsSizes.add(0, Integer.valueOf(0));
                        setImageIndex(0, true);
                    }
                    if (booleanValue) {
                        MessagesController.getInstance().loadDialogPhotos(this.avatarsDialogId, 80, 0, false, this.classGuid);
                    }
                }
            }
        } else if (i == NotificationCenter.mediaCountDidLoaded) {
            long longValue = ((Long) objArr[0]).longValue();
            if (longValue == this.currentDialogId || longValue == this.mergeDialogId) {
                if (longValue == this.currentDialogId) {
                    this.totalImagesCount = ((Integer) objArr[1]).intValue();
                } else if (longValue == this.mergeDialogId) {
                    this.totalImagesCountMerge = ((Integer) objArr[1]).intValue();
                }
                if (this.needSearchImageInArr && this.isFirstLoading) {
                    this.isFirstLoading = false;
                    this.loadingMoreImages = true;
                    SharedMediaQuery.loadMedia(this.currentDialogId, 80, 0, 0, true, this.classGuid);
                } else if (!this.imagesArr.isEmpty()) {
                    if (this.opennedFromMedia) {
                        this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(this.currentIndex + 1), Integer.valueOf(this.totalImagesCount + this.totalImagesCountMerge)}));
                    } else {
                        this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf((((this.totalImagesCount + this.totalImagesCountMerge) - this.imagesArr.size()) + this.currentIndex) + 1), Integer.valueOf(this.totalImagesCount + this.totalImagesCountMerge)}));
                    }
                }
            }
        } else if (i == NotificationCenter.mediaDidLoaded) {
            long longValue2 = ((Long) objArr[0]).longValue();
            int intValue = ((Integer) objArr[3]).intValue();
            if ((longValue2 == this.currentDialogId || longValue2 == this.mergeDialogId) && intValue == this.classGuid) {
                this.loadingMoreImages = false;
                r3 = longValue2 == this.currentDialogId ? 0 : 1;
                r0 = (ArrayList) objArr[2];
                this.endReached[r3] = ((Boolean) objArr[5]).booleanValue();
                if (!this.needSearchImageInArr) {
                    i2 = 0;
                    Iterator it = r0.iterator();
                    while (it.hasNext()) {
                        r0 = (MessageObject) it.next();
                        if (!this.imagesByIds[r3].containsKey(Integer.valueOf(r0.getId()))) {
                            i2++;
                            if (this.opennedFromMedia) {
                                this.imagesArr.add(r0);
                            } else {
                                this.imagesArr.add(0, r0);
                            }
                            this.imagesByIds[r3].put(Integer.valueOf(r0.getId()), r0);
                        }
                        i2 = i2;
                    }
                    if (this.opennedFromMedia) {
                        if (i2 == 0) {
                            this.totalImagesCount = this.imagesArr.size();
                            this.totalImagesCountMerge = 0;
                        }
                    } else if (i2 != 0) {
                        intValue = this.currentIndex;
                        this.currentIndex = -1;
                        setImageIndex(intValue + i2, true);
                    } else {
                        this.totalImagesCount = this.imagesArr.size();
                        this.totalImagesCountMerge = 0;
                    }
                } else if (!r0.isEmpty() || (r3 == 0 && this.mergeDialogId != 0)) {
                    MessageObject messageObject = (MessageObject) this.imagesArr.get(this.currentIndex);
                    int i3 = -1;
                    r5 = 0;
                    for (r4 = 0; r4 < r0.size(); r4++) {
                        MessageObject messageObject2 = (MessageObject) r0.get(r4);
                        if (!this.imagesByIdsTemp[r3].containsKey(Integer.valueOf(messageObject2.getId()))) {
                            this.imagesByIdsTemp[r3].put(Integer.valueOf(messageObject2.getId()), messageObject2);
                            if (this.opennedFromMedia) {
                                this.imagesArrTemp.add(messageObject2);
                                if (messageObject2.getId() == messageObject.getId()) {
                                    i3 = r5;
                                }
                                r5++;
                            } else {
                                r5++;
                                this.imagesArrTemp.add(0, messageObject2);
                                if (messageObject2.getId() == messageObject.getId()) {
                                    i3 = r0.size() - r5;
                                }
                            }
                        }
                    }
                    if (r5 == 0 && (r3 != 0 || this.mergeDialogId == 0)) {
                        this.totalImagesCount = this.imagesArr.size();
                        this.totalImagesCountMerge = 0;
                    }
                    if (i3 != -1) {
                        this.imagesArr.clear();
                        this.imagesArr.addAll(this.imagesArrTemp);
                        for (intValue = 0; intValue < 2; intValue++) {
                            this.imagesByIds[intValue].clear();
                            this.imagesByIds[intValue].putAll(this.imagesByIdsTemp[intValue]);
                            this.imagesByIdsTemp[intValue].clear();
                        }
                        this.imagesArrTemp.clear();
                        this.needSearchImageInArr = false;
                        this.currentIndex = -1;
                        if (i3 >= this.imagesArr.size()) {
                            i3 = this.imagesArr.size() - 1;
                        }
                        setImageIndex(i3, true);
                        return;
                    }
                    if (this.opennedFromMedia) {
                        i2 = this.imagesArrTemp.isEmpty() ? 0 : ((MessageObject) this.imagesArrTemp.get(this.imagesArrTemp.size() - 1)).getId();
                        if (r3 == 0 && this.endReached[r3] && this.mergeDialogId != 0) {
                            r2 = 1;
                            if (!(this.imagesArrTemp.isEmpty() || ((MessageObject) this.imagesArrTemp.get(this.imagesArrTemp.size() - 1)).getDialogId() == this.mergeDialogId)) {
                                r3 = 0;
                                intValue = 1;
                            }
                            r3 = i2;
                            intValue = r2;
                        }
                        intValue = r3;
                        r3 = i2;
                    } else {
                        i2 = this.imagesArrTemp.isEmpty() ? 0 : ((MessageObject) this.imagesArrTemp.get(0)).getId();
                        if (r3 == 0 && this.endReached[r3] && this.mergeDialogId != 0) {
                            r2 = 1;
                            if (!(this.imagesArrTemp.isEmpty() || ((MessageObject) this.imagesArrTemp.get(0)).getDialogId() == this.mergeDialogId)) {
                                r3 = 0;
                                intValue = 1;
                            }
                            r3 = i2;
                            intValue = r2;
                        }
                        intValue = r3;
                        r3 = i2;
                    }
                    if (!this.endReached[intValue]) {
                        this.loadingMoreImages = true;
                        if (this.opennedFromMedia) {
                            SharedMediaQuery.loadMedia(intValue == 0 ? this.currentDialogId : this.mergeDialogId, 80, r3, 0, true, this.classGuid);
                        } else {
                            SharedMediaQuery.loadMedia(intValue == 0 ? this.currentDialogId : this.mergeDialogId, 80, r3, 0, true, this.classGuid);
                        }
                    }
                } else {
                    this.needSearchImageInArr = false;
                }
            }
        } else if (i == NotificationCenter.emojiDidLoaded) {
            if (this.captionTextView != null) {
                this.captionTextView.invalidate();
            }
        } else if (i == NotificationCenter.FilePreparingFailed) {
            r0 = (MessageObject) objArr[0];
            if (this.loadInitialVideo) {
                this.loadInitialVideo = false;
                this.progressView.setVisibility(4);
                preparePlayer(this.currentPlayingVideoFile, false, false);
            } else if (this.tryStartRequestPreviewOnFinish) {
                releasePlayer();
                this.tryStartRequestPreviewOnFinish = !MediaController.getInstance().scheduleVideoConvert(this.videoPreviewMessageObject, true);
            } else if (r0 == this.videoPreviewMessageObject) {
                this.requestingPreview = false;
                this.progressView.setVisibility(4);
            }
        } else if (i == NotificationCenter.FileNewChunkAvailable && ((MessageObject) objArr[0]) == this.videoPreviewMessageObject) {
            str = (String) objArr[1];
            if (((Long) objArr[2]).longValue() != 0) {
                this.requestingPreview = false;
                this.progressView.setVisibility(4);
                preparePlayer(new File(str), false, true);
            }
        }
    }

    public float getAnimationValue() {
        return this.animationValue;
    }

    public boolean isMuteVideo() {
        return this.muteVideo;
    }

    public boolean isShowingImage(String str) {
        return (!this.isVisible || this.disableShowCheck || str == null || this.currentPathObject == null || !str.equals(this.currentPathObject)) ? false : true;
    }

    public boolean isShowingImage(MessageObject messageObject) {
        return (!this.isVisible || this.disableShowCheck || messageObject == null || this.currentMessageObject == null || this.currentMessageObject.getId() != messageObject.getId()) ? false : true;
    }

    public boolean isShowingImage(BotInlineResult botInlineResult) {
        return (!this.isVisible || this.disableShowCheck || botInlineResult == null || this.currentBotInlineResult == null || botInlineResult.id != this.currentBotInlineResult.id) ? false : true;
    }

    public boolean isShowingImage(FileLocation fileLocation) {
        return this.isVisible && !this.disableShowCheck && fileLocation != null && this.currentFileLocation != null && fileLocation.local_id == this.currentFileLocation.local_id && fileLocation.volume_id == this.currentFileLocation.volume_id && fileLocation.dc_id == this.currentFileLocation.dc_id;
    }

    public boolean isVisible() {
        return this.isVisible && this.placeProvider != null;
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (!this.canZoom) {
            return false;
        }
        if ((this.scale == 1.0f && (this.translationY != BitmapDescriptorFactory.HUE_RED || this.translationX != BitmapDescriptorFactory.HUE_RED)) || this.animationStartTime != 0 || this.animationInProgress != 0) {
            return false;
        }
        if (this.scale == 1.0f) {
            float x = (motionEvent.getX() - ((float) (getContainerViewWidth() / 2))) - (((motionEvent.getX() - ((float) (getContainerViewWidth() / 2))) - this.translationX) * (3.0f / this.scale));
            float y = (motionEvent.getY() - ((float) (getContainerViewHeight() / 2))) - (((motionEvent.getY() - ((float) (getContainerViewHeight() / 2))) - this.translationY) * (3.0f / this.scale));
            updateMinMax(3.0f);
            if (x < this.minX) {
                x = this.minX;
            } else if (x > this.maxX) {
                x = this.maxX;
            }
            if (y < this.minY) {
                y = this.minY;
            } else if (y > this.maxY) {
                y = this.maxY;
            }
            animateTo(3.0f, x, y, true);
        } else {
            animateTo(1.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, true);
        }
        this.doubleTap = true;
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.scale != 1.0f) {
            this.scroller.abortAnimation();
            this.scroller.fling(Math.round(this.translationX), Math.round(this.translationY), Math.round(f), Math.round(f2), (int) this.minX, (int) this.maxX, (int) this.minY, (int) this.maxY);
            this.containerView.postInvalidate();
        }
        return false;
    }

    public void onLongPress(MotionEvent motionEvent) {
    }

    public void onPause() {
        if (this.currentAnimation != null) {
            closePhoto(false, false);
        } else if (this.lastTitle != null) {
            closeCaptionEnter(true);
        }
    }

    public void onResume() {
        redraw(0);
        if (this.videoPlayer != null) {
            this.videoPlayer.seekTo(this.videoPlayer.getCurrentPosition() + 1);
        }
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        boolean z = false;
        if (this.discardTap) {
            return false;
        }
        int access$9800;
        float x;
        float y;
        if (this.canShowBottom) {
            boolean z2 = this.aspectRatioFrameLayout != null && this.aspectRatioFrameLayout.getVisibility() == 0;
            if (!(this.photoProgressViews[0] == null || this.containerView == null || z2)) {
                access$9800 = this.photoProgressViews[0].backgroundState;
                if (access$9800 > 0 && access$9800 <= 3) {
                    x = motionEvent.getX();
                    y = motionEvent.getY();
                    if (x >= ((float) (getContainerViewWidth() - AndroidUtilities.dp(100.0f))) / 2.0f && x <= ((float) (getContainerViewWidth() + AndroidUtilities.dp(100.0f))) / 2.0f && y >= ((float) (getContainerViewHeight() - AndroidUtilities.dp(100.0f))) / 2.0f && y <= ((float) (getContainerViewHeight() + AndroidUtilities.dp(100.0f))) / 2.0f) {
                        onActionClick(true);
                        checkProgress(0, true);
                        return true;
                    }
                }
            }
            if (!this.isActionBarVisible) {
                z = true;
            }
            toggleActionBar(z, true);
            return true;
        } else if (this.sendPhotoType == 0) {
            if (this.isCurrentVideo) {
                this.videoPlayButton.callOnClick();
                return true;
            }
            this.checkImageView.performClick();
            return true;
        } else if (this.currentBotInlineResult != null && (this.currentBotInlineResult.type.equals(MimeTypes.BASE_TYPE_VIDEO) || MessageObject.isVideoDocument(this.currentBotInlineResult.document))) {
            access$9800 = this.photoProgressViews[0].backgroundState;
            if (access$9800 <= 0 || access$9800 > 3) {
                return true;
            }
            x = motionEvent.getX();
            y = motionEvent.getY();
            if (x < ((float) (getContainerViewWidth() - AndroidUtilities.dp(100.0f))) / 2.0f || x > ((float) (getContainerViewWidth() + AndroidUtilities.dp(100.0f))) / 2.0f || y < ((float) (getContainerViewHeight() - AndroidUtilities.dp(100.0f))) / 2.0f || y > ((float) (getContainerViewHeight() + AndroidUtilities.dp(100.0f))) / 2.0f) {
                return true;
            }
            onActionClick(true);
            checkProgress(0, true);
            return true;
        } else if (this.sendPhotoType != 2 || !this.isCurrentVideo) {
            return true;
        } else {
            this.videoPlayButton.callOnClick();
            return true;
        }
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public boolean openPhoto(ArrayList<MessageObject> arrayList, int i, long j, long j2, PhotoViewerProvider photoViewerProvider) {
        return openPhoto((MessageObject) arrayList.get(i), null, arrayList, null, i, photoViewerProvider, null, j, j2);
    }

    public boolean openPhoto(MessageObject messageObject, long j, long j2, PhotoViewerProvider photoViewerProvider) {
        return openPhoto(messageObject, null, null, null, 0, photoViewerProvider, null, j, j2);
    }

    public boolean openPhoto(MessageObject messageObject, FileLocation fileLocation, ArrayList<MessageObject> arrayList, ArrayList<Object> arrayList2, int i, PhotoViewerProvider photoViewerProvider, ChatActivity chatActivity, long j, long j2) {
        if (this.parentActivity == null || this.isVisible || ((photoViewerProvider == null && checkAnimation()) || (messageObject == null && fileLocation == null && arrayList == null && arrayList2 == null))) {
            return false;
        }
        final PlaceProviderObject placeForPhoto = photoViewerProvider.getPlaceForPhoto(messageObject, fileLocation, i);
        if (placeForPhoto == null && arrayList2 == null) {
            return false;
        }
        this.lastInsets = null;
        WindowManager windowManager = (WindowManager) this.parentActivity.getSystemService("window");
        if (this.attachedToWindow) {
            try {
                windowManager.removeView(this.windowView);
            } catch (Exception e) {
            }
        }
        try {
            this.windowLayoutParams.type = 99;
            if (VERSION.SDK_INT >= 21) {
                this.windowLayoutParams.flags = -2147417848;
            } else {
                this.windowLayoutParams.flags = 8;
            }
            this.windowLayoutParams.softInputMode = 272;
            this.windowView.setFocusable(false);
            this.containerView.setFocusable(false);
            windowManager.addView(this.windowView, this.windowLayoutParams);
            this.doneButtonPressed = false;
            this.parentChatActivity = chatActivity;
            this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(1), Integer.valueOf(1)}));
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidFailedLoad);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileLoadProgressChanged);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.mediaCountDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.mediaDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.dialogPhotosLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.FilePreparingFailed);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileNewChunkAvailable);
            this.placeProvider = photoViewerProvider;
            this.mergeDialogId = j2;
            this.currentDialogId = j;
            this.selectedPhotosAdapter.notifyDataSetChanged();
            if (this.velocityTracker == null) {
                this.velocityTracker = VelocityTracker.obtain();
            }
            this.isVisible = true;
            toggleActionBar(true, false);
            togglePhotosListView(false, false);
            if (placeForPhoto != null) {
                this.disableShowCheck = true;
                this.animationInProgress = 1;
                if (messageObject != null) {
                    this.currentAnimation = placeForPhoto.imageReceiver.getAnimation();
                }
                onPhotoShow(messageObject, fileLocation, arrayList, arrayList2, i, placeForPhoto);
                Rect drawRegion = placeForPhoto.imageReceiver.getDrawRegion();
                int orientation = placeForPhoto.imageReceiver.getOrientation();
                int animatedOrientation = placeForPhoto.imageReceiver.getAnimatedOrientation();
                if (animatedOrientation == 0) {
                    animatedOrientation = orientation;
                }
                this.animatingImageView.setVisibility(0);
                this.animatingImageView.setRadius(placeForPhoto.radius);
                this.animatingImageView.setOrientation(animatedOrientation);
                this.animatingImageView.setNeedRadius(placeForPhoto.radius != 0);
                this.animatingImageView.setImageBitmap(placeForPhoto.thumb);
                this.animatingImageView.setAlpha(1.0f);
                this.animatingImageView.setPivotX(BitmapDescriptorFactory.HUE_RED);
                this.animatingImageView.setPivotY(BitmapDescriptorFactory.HUE_RED);
                this.animatingImageView.setScaleX(placeForPhoto.scale);
                this.animatingImageView.setScaleY(placeForPhoto.scale);
                this.animatingImageView.setTranslationX(((float) placeForPhoto.viewX) + (((float) drawRegion.left) * placeForPhoto.scale));
                this.animatingImageView.setTranslationY(((float) placeForPhoto.viewY) + (((float) drawRegion.top) * placeForPhoto.scale));
                ViewGroup.LayoutParams layoutParams = this.animatingImageView.getLayoutParams();
                layoutParams.width = drawRegion.right - drawRegion.left;
                layoutParams.height = drawRegion.bottom - drawRegion.top;
                this.animatingImageView.setLayoutParams(layoutParams);
                float f = ((float) AndroidUtilities.displaySize.x) / ((float) layoutParams.width);
                float f2 = ((float) ((VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + AndroidUtilities.displaySize.y)) / ((float) layoutParams.height);
                if (f <= f2) {
                    f2 = f;
                }
                float f3 = (((float) AndroidUtilities.displaySize.x) - (((float) layoutParams.width) * f2)) / 2.0f;
                float f4 = (((float) ((VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + AndroidUtilities.displaySize.y)) - (((float) layoutParams.height) * f2)) / 2.0f;
                int abs = Math.abs(drawRegion.left - placeForPhoto.imageReceiver.getImageX());
                int abs2 = Math.abs(drawRegion.top - placeForPhoto.imageReceiver.getImageY());
                int[] iArr = new int[2];
                placeForPhoto.parentView.getLocationInWindow(iArr);
                orientation = ((iArr[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight)) - (placeForPhoto.viewY + drawRegion.top)) + placeForPhoto.clipTopAddition;
                if (orientation < 0) {
                    orientation = 0;
                }
                int height = ((layoutParams.height + (drawRegion.top + placeForPhoto.viewY)) - ((placeForPhoto.parentView.getHeight() + iArr[1]) - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight))) + placeForPhoto.clipBottomAddition;
                if (height < 0) {
                    height = 0;
                }
                orientation = Math.max(orientation, abs2);
                height = Math.max(height, abs2);
                this.animationValues[0][0] = this.animatingImageView.getScaleX();
                this.animationValues[0][1] = this.animatingImageView.getScaleY();
                this.animationValues[0][2] = this.animatingImageView.getTranslationX();
                this.animationValues[0][3] = this.animatingImageView.getTranslationY();
                this.animationValues[0][4] = ((float) abs) * placeForPhoto.scale;
                this.animationValues[0][5] = ((float) orientation) * placeForPhoto.scale;
                this.animationValues[0][6] = ((float) height) * placeForPhoto.scale;
                this.animationValues[0][7] = (float) this.animatingImageView.getRadius();
                this.animationValues[1][0] = f2;
                this.animationValues[1][1] = f2;
                this.animationValues[1][2] = f3;
                this.animationValues[1][3] = f4;
                this.animationValues[1][4] = BitmapDescriptorFactory.HUE_RED;
                this.animationValues[1][5] = BitmapDescriptorFactory.HUE_RED;
                this.animationValues[1][6] = BitmapDescriptorFactory.HUE_RED;
                this.animationValues[1][7] = BitmapDescriptorFactory.HUE_RED;
                this.animatingImageView.setAnimationProgress(BitmapDescriptorFactory.HUE_RED);
                this.backgroundDrawable.setAlpha(0);
                this.containerView.setAlpha(BitmapDescriptorFactory.HUE_RED);
                final AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.animatingImageView, "animationProgress", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofInt(this.backgroundDrawable, "alpha", new int[]{0, 255}), ObjectAnimator.ofFloat(this.containerView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
                final ArrayList<Object> arrayList3 = arrayList2;
                this.animationEndRunnable = new Runnable() {
                    public void run() {
                        if (PhotoViewer.this.containerView != null && PhotoViewer.this.windowView != null) {
                            if (VERSION.SDK_INT >= 18) {
                                PhotoViewer.this.containerView.setLayerType(0, null);
                            }
                            PhotoViewer.this.animationInProgress = 0;
                            PhotoViewer.this.transitionAnimationStartTime = 0;
                            PhotoViewer.this.setImages();
                            PhotoViewer.this.containerView.invalidate();
                            PhotoViewer.this.animatingImageView.setVisibility(8);
                            if (PhotoViewer.this.showAfterAnimation != null) {
                                PhotoViewer.this.showAfterAnimation.imageReceiver.setVisible(true, true);
                            }
                            if (PhotoViewer.this.hideAfterAnimation != null) {
                                PhotoViewer.this.hideAfterAnimation.imageReceiver.setVisible(false, true);
                            }
                            if (arrayList3 != null && PhotoViewer.this.sendPhotoType != 3) {
                                if (VERSION.SDK_INT >= 21) {
                                    PhotoViewer.this.windowLayoutParams.flags = -2147417856;
                                } else {
                                    PhotoViewer.this.windowLayoutParams.flags = 0;
                                }
                                PhotoViewer.this.windowLayoutParams.softInputMode = 272;
                                ((WindowManager) PhotoViewer.this.parentActivity.getSystemService("window")).updateViewLayout(PhotoViewer.this.windowView, PhotoViewer.this.windowLayoutParams);
                                PhotoViewer.this.windowView.setFocusable(true);
                                PhotoViewer.this.containerView.setFocusable(true);
                            }
                        }
                    }
                };
                animatorSet.setDuration(200);
                animatorSet.addListener(new AnimatorListenerAdapter() {

                    /* renamed from: org.telegram.ui.PhotoViewer$52$1 */
                    class C50681 implements Runnable {
                        C50681() {
                        }

                        public void run() {
                            NotificationCenter.getInstance().setAnimationInProgress(false);
                            if (PhotoViewer.this.animationEndRunnable != null) {
                                PhotoViewer.this.animationEndRunnable.run();
                                PhotoViewer.this.animationEndRunnable = null;
                            }
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        AndroidUtilities.runOnUIThread(new C50681());
                    }
                });
                this.transitionAnimationStartTime = System.currentTimeMillis();
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().setAllowedNotificationsDutingAnimation(new int[]{NotificationCenter.dialogsNeedReload, NotificationCenter.closeChats, NotificationCenter.mediaCountDidLoaded, NotificationCenter.mediaDidLoaded, NotificationCenter.dialogPhotosLoaded});
                        NotificationCenter.getInstance().setAnimationInProgress(true);
                        animatorSet.start();
                    }
                });
                if (VERSION.SDK_INT >= 18) {
                    this.containerView.setLayerType(2, null);
                }
                this.backgroundDrawable.drawRunnable = new Runnable() {
                    public void run() {
                        PhotoViewer.this.disableShowCheck = false;
                        placeForPhoto.imageReceiver.setVisible(false, true);
                    }
                };
            } else {
                if (!(arrayList2 == null || this.sendPhotoType == 3)) {
                    if (VERSION.SDK_INT >= 21) {
                        this.windowLayoutParams.flags = -2147417856;
                    } else {
                        this.windowLayoutParams.flags = 0;
                    }
                    this.windowLayoutParams.softInputMode = 272;
                    windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
                    this.windowView.setFocusable(true);
                    this.containerView.setFocusable(true);
                }
                this.backgroundDrawable.setAlpha(255);
                this.containerView.setAlpha(1.0f);
                onPhotoShow(messageObject, fileLocation, arrayList, arrayList2, i, placeForPhoto);
            }
            return true;
        } catch (Throwable e2) {
            FileLog.e(e2);
            return false;
        }
    }

    public boolean openPhoto(FileLocation fileLocation, PhotoViewerProvider photoViewerProvider) {
        return openPhoto(null, fileLocation, null, null, 0, photoViewerProvider, null, 0, 0);
    }

    public boolean openPhotoForSelect(ArrayList<Object> arrayList, int i, int i2, PhotoViewerProvider photoViewerProvider, ChatActivity chatActivity) {
        this.sendPhotoType = i2;
        if (this.pickerViewSendButton != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.itemsLayout.getLayoutParams();
            if (this.sendPhotoType == 1) {
                this.pickerView.setPadding(0, AndroidUtilities.dp(14.0f), 0, 0);
                this.pickerViewSendButton.setImageResource(R.drawable.bigcheck);
                this.pickerViewSendButton.setPadding(0, AndroidUtilities.dp(1.0f), 0, 0);
                layoutParams.bottomMargin = AndroidUtilities.dp(16.0f);
            } else {
                this.pickerView.setPadding(0, 0, 0, 0);
                this.pickerViewSendButton.setImageResource(R.drawable.ic_send);
                this.pickerViewSendButton.setPadding(AndroidUtilities.dp(4.0f), 0, 0, 0);
                layoutParams.bottomMargin = 0;
            }
            this.itemsLayout.setLayoutParams(layoutParams);
        }
        return openPhoto(null, null, null, arrayList, i, photoViewerProvider, chatActivity, 0, 0);
    }

    public void setAnimationValue(float f) {
        this.animationValue = f;
        this.containerView.invalidate();
    }

    public void setParentActivity(Activity activity) {
        if (this.parentActivity != activity) {
            FrameLayout.LayoutParams layoutParams;
            this.parentActivity = activity;
            this.actvityContext = new ContextThemeWrapper(this.parentActivity, R.style.Theme.TMessages);
            if (progressDrawables == null) {
                progressDrawables = new Drawable[4];
                progressDrawables[0] = this.parentActivity.getResources().getDrawable(R.drawable.circle_big);
                progressDrawables[1] = this.parentActivity.getResources().getDrawable(R.drawable.cancel_big);
                progressDrawables[2] = this.parentActivity.getResources().getDrawable(R.drawable.load_big);
                progressDrawables[3] = this.parentActivity.getResources().getDrawable(R.drawable.play_big);
            }
            this.scroller = new Scroller(activity);
            this.windowView = new FrameLayout(activity) {
                private Runnable attachRunnable;

                /* renamed from: org.telegram.ui.PhotoViewer$2$1 */
                class C50461 implements Runnable {
                    C50461() {
                    }

                    public void run() {
                        int i = 0;
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) PhotoViewer.this.checkImageView.getLayoutParams();
                        ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
                        layoutParams.topMargin = (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + ((ActionBar.getCurrentActionBarHeight() - AndroidUtilities.dp(40.0f)) / 2);
                        PhotoViewer.this.checkImageView.setLayoutParams(layoutParams);
                        layoutParams = (FrameLayout.LayoutParams) PhotoViewer.this.photosCounterView.getLayoutParams();
                        int currentActionBarHeight = (ActionBar.getCurrentActionBarHeight() - AndroidUtilities.dp(40.0f)) / 2;
                        if (VERSION.SDK_INT >= 21) {
                            i = AndroidUtilities.statusBarHeight;
                        }
                        layoutParams.topMargin = currentActionBarHeight + i;
                        PhotoViewer.this.photosCounterView.setLayoutParams(layoutParams);
                    }
                }

                public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
                    if (keyEvent == null || keyEvent.getKeyCode() != 4 || keyEvent.getAction() != 1) {
                        return super.dispatchKeyEventPreIme(keyEvent);
                    }
                    if (PhotoViewer.this.captionEditText.isPopupShowing() || PhotoViewer.this.captionEditText.isKeyboardVisible()) {
                        PhotoViewer.this.closeCaptionEnter(false);
                        return false;
                    }
                    PhotoViewer.getInstance().closePhoto(true, false);
                    return true;
                }

                protected boolean drawChild(Canvas canvas, View view, long j) {
                    boolean drawChild = super.drawChild(canvas, view, j);
                    if (VERSION.SDK_INT >= 21 && view == PhotoViewer.this.animatingImageView && PhotoViewer.this.lastInsets != null) {
                        WindowInsets windowInsets = (WindowInsets) PhotoViewer.this.lastInsets;
                        canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) getMeasuredHeight(), (float) getMeasuredWidth(), (float) (windowInsets.getSystemWindowInsetBottom() + getMeasuredHeight()), PhotoViewer.this.blackPaint);
                    }
                    return drawChild;
                }

                protected void onAttachedToWindow() {
                    super.onAttachedToWindow();
                    PhotoViewer.this.attachedToWindow = true;
                }

                protected void onDetachedFromWindow() {
                    super.onDetachedFromWindow();
                    PhotoViewer.this.attachedToWindow = false;
                    PhotoViewer.this.wasLayout = false;
                }

                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    return PhotoViewer.this.isVisible && super.onInterceptTouchEvent(motionEvent);
                }

                protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                    int systemWindowInsetLeft = (VERSION.SDK_INT < 21 || PhotoViewer.this.lastInsets == null) ? 0 : ((WindowInsets) PhotoViewer.this.lastInsets).getSystemWindowInsetLeft() + 0;
                    PhotoViewer.this.animatingImageView.layout(systemWindowInsetLeft, 0, PhotoViewer.this.animatingImageView.getMeasuredWidth() + systemWindowInsetLeft, PhotoViewer.this.animatingImageView.getMeasuredHeight());
                    PhotoViewer.this.containerView.layout(systemWindowInsetLeft, 0, PhotoViewer.this.containerView.getMeasuredWidth() + systemWindowInsetLeft, PhotoViewer.this.containerView.getMeasuredHeight());
                    PhotoViewer.this.wasLayout = true;
                    if (z) {
                        if (!PhotoViewer.this.dontResetZoomOnFirstLayout) {
                            PhotoViewer.this.scale = 1.0f;
                            PhotoViewer.this.translationX = BitmapDescriptorFactory.HUE_RED;
                            PhotoViewer.this.translationY = BitmapDescriptorFactory.HUE_RED;
                            PhotoViewer.this.updateMinMax(PhotoViewer.this.scale);
                        }
                        if (PhotoViewer.this.checkImageView != null) {
                            PhotoViewer.this.checkImageView.post(new C50461());
                        }
                    }
                    if (PhotoViewer.this.dontResetZoomOnFirstLayout) {
                        PhotoViewer.this.setScaleToFill();
                        PhotoViewer.this.dontResetZoomOnFirstLayout = false;
                    }
                }

                protected void onMeasure(int i, int i2) {
                    int size = MeasureSpec.getSize(i);
                    int size2 = MeasureSpec.getSize(i2);
                    if (VERSION.SDK_INT >= 21 && PhotoViewer.this.lastInsets != null) {
                        WindowInsets windowInsets = (WindowInsets) PhotoViewer.this.lastInsets;
                        if (AndroidUtilities.incorrectDisplaySizeFix) {
                            if (size2 > AndroidUtilities.displaySize.y) {
                                size2 = AndroidUtilities.displaySize.y;
                            }
                            size2 += AndroidUtilities.statusBarHeight;
                        }
                        size2 -= windowInsets.getSystemWindowInsetBottom();
                        size -= windowInsets.getSystemWindowInsetRight();
                    } else if (size2 > AndroidUtilities.displaySize.y) {
                        size2 = AndroidUtilities.displaySize.y;
                    }
                    setMeasuredDimension(size, size2);
                    if (VERSION.SDK_INT >= 21 && PhotoViewer.this.lastInsets != null) {
                        size -= ((WindowInsets) PhotoViewer.this.lastInsets).getSystemWindowInsetLeft();
                    }
                    ViewGroup.LayoutParams layoutParams = PhotoViewer.this.animatingImageView.getLayoutParams();
                    PhotoViewer.this.animatingImageView.measure(MeasureSpec.makeMeasureSpec(layoutParams.width, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(layoutParams.height, Integer.MIN_VALUE));
                    PhotoViewer.this.containerView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
                }

                public boolean onTouchEvent(MotionEvent motionEvent) {
                    return PhotoViewer.this.isVisible && PhotoViewer.this.onTouchEvent(motionEvent);
                }

                public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i) {
                    if (VERSION.SDK_INT >= 23) {
                        View findViewById = PhotoViewer.this.parentActivity.findViewById(16908290);
                        if (findViewById instanceof ViewGroup) {
                            try {
                                return ((ViewGroup) findViewById).startActionModeForChild(view, callback, i);
                            } catch (Throwable th) {
                                FileLog.e(th);
                            }
                        }
                    }
                    return super.startActionModeForChild(view, callback, i);
                }
            };
            this.windowView.setBackgroundDrawable(this.backgroundDrawable);
            this.windowView.setClipChildren(true);
            this.windowView.setFocusable(false);
            this.animatingImageView = new ClippingImageView(activity);
            this.animatingImageView.setAnimationValues(this.animationValues);
            this.windowView.addView(this.animatingImageView, LayoutHelper.createFrame(40, 40.0f));
            this.containerView = new FrameLayoutDrawer(activity);
            this.containerView.setFocusable(false);
            this.windowView.addView(this.containerView, LayoutHelper.createFrame(-1, -1, 51));
            if (VERSION.SDK_INT >= 21) {
                this.containerView.setFitsSystemWindows(true);
                this.containerView.setOnApplyWindowInsetsListener(new C50573());
                this.containerView.setSystemUiVisibility(1280);
            }
            this.windowLayoutParams = new LayoutParams();
            this.windowLayoutParams.height = -1;
            this.windowLayoutParams.format = -3;
            this.windowLayoutParams.width = -1;
            this.windowLayoutParams.gravity = 51;
            this.windowLayoutParams.type = 99;
            if (VERSION.SDK_INT >= 21) {
                this.windowLayoutParams.flags = -2147417848;
            } else {
                this.windowLayoutParams.flags = 8;
            }
            this.actionBar = new ActionBar(activity);
            this.actionBar.setTitleColor(-1);
            this.actionBar.setSubtitleColor(-1);
            this.actionBar.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            this.actionBar.setOccupyStatusBar(VERSION.SDK_INT >= 21);
            this.actionBar.setItemsBackgroundColor(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR, false);
            this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
            this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(1), Integer.valueOf(1)}));
            this.containerView.addView(this.actionBar, LayoutHelper.createFrame(-1, -2.0f));
            this.actionBar.setActionBarMenuOnItemClick(new C50674());
            ActionBarMenu createMenu = this.actionBar.createMenu();
            this.masksItem = createMenu.addItem(13, (int) R.drawable.ic_masks_msk1);
            this.sendItem = createMenu.addItem(3, (int) R.drawable.msg_panel_reply);
            this.menuItem = createMenu.addItem(0, (int) R.drawable.ic_ab_other);
            this.menuItem.addSubItem(11, LocaleController.getString("OpenInExternalApp", R.string.OpenInExternalApp)).setTextColor(-328966);
            this.menuItem.addSubItem(2, LocaleController.getString("ShowAllMedia", R.string.ShowAllMedia)).setTextColor(-328966);
            this.menuItem.addSubItem(4, LocaleController.getString("ShowInChat", R.string.ShowInChat)).setTextColor(-328966);
            this.menuItem.addSubItem(10, LocaleController.getString("ShareFile", R.string.ShareFile)).setTextColor(-328966);
            this.menuItem.addSubItem(1, LocaleController.getString("SaveToGallery", R.string.SaveToGallery)).setTextColor(-328966);
            this.menuItem.addSubItem(6, LocaleController.getString("Delete", R.string.Delete)).setTextColor(-328966);
            this.menuItem.redrawPopup(-115203550);
            this.bottomLayout = new FrameLayout(this.actvityContext);
            this.bottomLayout.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            this.containerView.addView(this.bottomLayout, LayoutHelper.createFrame(-1, 48, 83));
            this.groupedPhotosListView = new GroupedPhotosListView(this.actvityContext);
            this.containerView.addView(this.groupedPhotosListView, LayoutHelper.createFrame(-1, 62.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
            this.captionTextView = new TextView(this.actvityContext) {
                public boolean onTouchEvent(MotionEvent motionEvent) {
                    return PhotoViewer.this.bottomTouchEnabled && super.onTouchEvent(motionEvent);
                }
            };
            this.captionTextView.setPadding(AndroidUtilities.dp(20.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(20.0f), AndroidUtilities.dp(8.0f));
            this.captionTextView.setLinkTextColor(-1);
            this.captionTextView.setTextColor(-1);
            this.captionTextView.setEllipsize(TruncateAt.END);
            this.captionTextView.setGravity(19);
            this.captionTextView.setTextSize(1, 16.0f);
            this.captionTextView.setVisibility(4);
            this.captionTextView.setOnClickListener(new C50746());
            this.photoProgressViews[0] = new PhotoProgressView(this.containerView.getContext(), this.containerView);
            this.photoProgressViews[0].setBackgroundState(0, false);
            this.photoProgressViews[1] = new PhotoProgressView(this.containerView.getContext(), this.containerView);
            this.photoProgressViews[1].setBackgroundState(0, false);
            this.photoProgressViews[2] = new PhotoProgressView(this.containerView.getContext(), this.containerView);
            this.photoProgressViews[2].setBackgroundState(0, false);
            this.shareButton = new ImageView(this.containerView.getContext());
            this.shareButton.setImageResource(R.drawable.share);
            this.shareButton.setScaleType(ScaleType.CENTER);
            this.shareButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
            this.bottomLayout.addView(this.shareButton, LayoutHelper.createFrame(50, -1, 53));
            this.shareButton.setOnClickListener(new C50757());
            this.nameTextView = new TextView(this.containerView.getContext());
            this.nameTextView.setTextSize(1, 14.0f);
            this.nameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.nameTextView.setSingleLine(true);
            this.nameTextView.setMaxLines(1);
            this.nameTextView.setEllipsize(TruncateAt.END);
            this.nameTextView.setTextColor(-1);
            this.nameTextView.setGravity(3);
            this.bottomLayout.addView(this.nameTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 16.0f, 5.0f, 60.0f, BitmapDescriptorFactory.HUE_RED));
            this.dateTextView = new TextView(this.containerView.getContext());
            this.dateTextView.setTextSize(1, 13.0f);
            this.dateTextView.setSingleLine(true);
            this.dateTextView.setMaxLines(1);
            this.dateTextView.setEllipsize(TruncateAt.END);
            this.dateTextView.setTextColor(-1);
            this.dateTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.dateTextView.setGravity(3);
            this.bottomLayout.addView(this.dateTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 16.0f, 25.0f, 50.0f, BitmapDescriptorFactory.HUE_RED));
            this.videoPlayerSeekbar = new SeekBar(this.containerView.getContext());
            this.videoPlayerSeekbar.setColors(1728053247, -1, -1);
            this.videoPlayerSeekbar.setDelegate(new C50768());
            this.videoPlayerControlFrameLayout = new FrameLayout(this.containerView.getContext()) {
                protected void onDraw(Canvas canvas) {
                    canvas.save();
                    canvas.translate((float) AndroidUtilities.dp(48.0f), BitmapDescriptorFactory.HUE_RED);
                    PhotoViewer.this.videoPlayerSeekbar.draw(canvas);
                    canvas.restore();
                }

                protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                    float f = BitmapDescriptorFactory.HUE_RED;
                    super.onLayout(z, i, i2, i3, i4);
                    if (PhotoViewer.this.videoPlayer != null) {
                        float currentPosition = ((float) PhotoViewer.this.videoPlayer.getCurrentPosition()) / ((float) PhotoViewer.this.videoPlayer.getDuration());
                        if (PhotoViewer.this.inPreview || PhotoViewer.this.videoTimelineView.getVisibility() != 0) {
                            f = currentPosition;
                        } else {
                            currentPosition -= PhotoViewer.this.videoTimelineView.getLeftProgress();
                            if (currentPosition >= BitmapDescriptorFactory.HUE_RED) {
                                f = currentPosition;
                            }
                            f /= PhotoViewer.this.videoTimelineView.getRightProgress() - PhotoViewer.this.videoTimelineView.getLeftProgress();
                            if (f > 1.0f) {
                                f = 1.0f;
                            }
                        }
                    }
                    PhotoViewer.this.videoPlayerSeekbar.setProgress(f);
                    PhotoViewer.this.videoTimelineView.setProgress(f);
                }

                protected void onMeasure(int i, int i2) {
                    long j = 0;
                    super.onMeasure(i, i2);
                    if (PhotoViewer.this.videoPlayer != null) {
                        long duration = PhotoViewer.this.videoPlayer.getDuration();
                        if (duration != C3446C.TIME_UNSET) {
                            j = duration;
                        }
                    }
                    j /= 1000;
                    PhotoViewer.this.videoPlayerSeekbar.setSize((getMeasuredWidth() - AndroidUtilities.dp(64.0f)) - ((int) Math.ceil((double) PhotoViewer.this.videoPlayerTime.getPaint().measureText(String.format("%02d:%02d / %02d:%02d", new Object[]{Long.valueOf(j / 60), Long.valueOf(j % 60), Long.valueOf(j / 60), Long.valueOf(j % 60)})))), getMeasuredHeight());
                }

                public boolean onTouchEvent(MotionEvent motionEvent) {
                    int x = (int) motionEvent.getX();
                    x = (int) motionEvent.getY();
                    if (!PhotoViewer.this.videoPlayerSeekbar.onTouch(motionEvent.getAction(), motionEvent.getX() - ((float) AndroidUtilities.dp(48.0f)), motionEvent.getY())) {
                        return super.onTouchEvent(motionEvent);
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                    invalidate();
                    return true;
                }
            };
            this.videoPlayerControlFrameLayout.setWillNotDraw(false);
            this.bottomLayout.addView(this.videoPlayerControlFrameLayout, LayoutHelper.createFrame(-1, -1, 51));
            this.videoPlayButton = new ImageView(this.containerView.getContext());
            this.videoPlayButton.setScaleType(ScaleType.CENTER);
            this.videoPlayerControlFrameLayout.addView(this.videoPlayButton, LayoutHelper.createFrame(48, 48, 51));
            this.videoPlayButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (PhotoViewer.this.videoPlayer != null) {
                        if (PhotoViewer.this.isPlaying) {
                            PhotoViewer.this.videoPlayer.pause();
                        } else {
                            if (PhotoViewer.this.isCurrentVideo) {
                                if (Math.abs(PhotoViewer.this.videoTimelineView.getProgress() - 1.0f) < 0.01f || PhotoViewer.this.videoPlayer.getCurrentPosition() == PhotoViewer.this.videoPlayer.getDuration()) {
                                    PhotoViewer.this.videoPlayer.seekTo(0);
                                }
                            } else if (Math.abs(PhotoViewer.this.videoPlayerSeekbar.getProgress() - 1.0f) < 0.01f || PhotoViewer.this.videoPlayer.getCurrentPosition() == PhotoViewer.this.videoPlayer.getDuration()) {
                                PhotoViewer.this.videoPlayer.seekTo(0);
                            }
                            PhotoViewer.this.videoPlayer.play();
                        }
                        PhotoViewer.this.containerView.invalidate();
                    }
                }
            });
            this.videoPlayerTime = new TextView(this.containerView.getContext());
            this.videoPlayerTime.setTextColor(-1);
            this.videoPlayerTime.setGravity(16);
            this.videoPlayerTime.setTextSize(1, 13.0f);
            this.videoPlayerControlFrameLayout.addView(this.videoPlayerTime, LayoutHelper.createFrame(-2, -1.0f, 53, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 8.0f, BitmapDescriptorFactory.HUE_RED));
            this.progressView = new RadialProgressView(this.parentActivity);
            this.progressView.setProgressColor(-1);
            this.progressView.setBackgroundResource(R.drawable.circle_big);
            this.progressView.setVisibility(4);
            this.containerView.addView(this.progressView, LayoutHelper.createFrame(54, 54, 17));
            this.qualityPicker = new PickerBottomLayoutViewer(this.parentActivity);
            this.qualityPicker.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            this.qualityPicker.updateSelectedCount(0, false);
            this.qualityPicker.setTranslationY((float) AndroidUtilities.dp(120.0f));
            this.qualityPicker.doneButton.setText(LocaleController.getString("Done", R.string.Done).toUpperCase());
            this.containerView.addView(this.qualityPicker, LayoutHelper.createFrame(-1, 48, 83));
            this.qualityPicker.cancelButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PhotoViewer.this.selectedCompression = PhotoViewer.this.previousCompression;
                    PhotoViewer.this.didChangedCompressionLevel(false);
                    PhotoViewer.this.showQualityView(false);
                    PhotoViewer.this.requestVideoPreview(2);
                }
            });
            this.qualityPicker.doneButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PhotoViewer.this.showQualityView(false);
                    PhotoViewer.this.requestVideoPreview(2);
                }
            });
            this.qualityChooseView = new QualityChooseView(this.parentActivity);
            this.qualityChooseView.setTranslationY((float) AndroidUtilities.dp(120.0f));
            this.qualityChooseView.setVisibility(4);
            this.qualityChooseView.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            this.containerView.addView(this.qualityChooseView, LayoutHelper.createFrame(-1, 70.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
            this.pickerView = new FrameLayout(this.actvityContext) {
                public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                    return PhotoViewer.this.bottomTouchEnabled && super.dispatchTouchEvent(motionEvent);
                }

                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    return PhotoViewer.this.bottomTouchEnabled && super.onInterceptTouchEvent(motionEvent);
                }

                public boolean onTouchEvent(MotionEvent motionEvent) {
                    return PhotoViewer.this.bottomTouchEnabled && super.onTouchEvent(motionEvent);
                }
            };
            this.pickerView.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            this.containerView.addView(this.pickerView, LayoutHelper.createFrame(-1, -2, 83));
            this.videoTimelineView = new VideoTimelinePlayView(this.parentActivity);
            this.videoTimelineView.setDelegate(new VideoTimelineViewDelegate() {
                public void didStartDragging() {
                }

                public void didStopDragging() {
                }

                public void onLeftProgressChanged(float f) {
                    if (PhotoViewer.this.videoPlayer != null) {
                        if (PhotoViewer.this.videoPlayer.isPlaying()) {
                            PhotoViewer.this.videoPlayer.pause();
                            PhotoViewer.this.containerView.invalidate();
                        }
                        PhotoViewer.this.videoPlayer.seekTo((long) ((int) (PhotoViewer.this.videoDuration * f)));
                        PhotoViewer.this.videoPlayerSeekbar.setProgress(BitmapDescriptorFactory.HUE_RED);
                        PhotoViewer.this.videoTimelineView.setProgress(BitmapDescriptorFactory.HUE_RED);
                        PhotoViewer.this.updateVideoInfo();
                    }
                }

                public void onPlayProgressChanged(float f) {
                    if (PhotoViewer.this.videoPlayer != null) {
                        PhotoViewer.this.videoPlayer.seekTo((long) ((int) (PhotoViewer.this.videoDuration * f)));
                    }
                }

                public void onRightProgressChanged(float f) {
                    if (PhotoViewer.this.videoPlayer != null) {
                        if (PhotoViewer.this.videoPlayer.isPlaying()) {
                            PhotoViewer.this.videoPlayer.pause();
                            PhotoViewer.this.containerView.invalidate();
                        }
                        PhotoViewer.this.videoPlayer.seekTo((long) ((int) (PhotoViewer.this.videoDuration * f)));
                        PhotoViewer.this.videoPlayerSeekbar.setProgress(BitmapDescriptorFactory.HUE_RED);
                        PhotoViewer.this.videoTimelineView.setProgress(BitmapDescriptorFactory.HUE_RED);
                        PhotoViewer.this.updateVideoInfo();
                    }
                }
            });
            this.pickerView.addView(this.videoTimelineView, LayoutHelper.createFrame(-1, 58.0f, 51, BitmapDescriptorFactory.HUE_RED, 8.0f, BitmapDescriptorFactory.HUE_RED, 88.0f));
            this.pickerViewSendButton = new ImageView(this.parentActivity);
            this.pickerViewSendButton.setScaleType(ScaleType.CENTER);
            this.pickerViewSendButton.setBackgroundDrawable(Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), -10043398, -10043398));
            this.pickerViewSendButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), Mode.MULTIPLY));
            this.pickerViewSendButton.setPadding(AndroidUtilities.dp(4.0f), 0, 0, 0);
            this.pickerViewSendButton.setImageResource(R.drawable.ic_send);
            this.pickerView.addView(this.pickerViewSendButton, LayoutHelper.createFrame(56, 56.0f, 85, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 14.0f, 14.0f));
            this.pickerViewSendButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (PhotoViewer.this.placeProvider != null && !PhotoViewer.this.doneButtonPressed) {
                        PhotoViewer.this.placeProvider.sendButtonPressed(PhotoViewer.this.currentIndex, PhotoViewer.this.getCurrentVideoEditedInfo());
                        PhotoViewer.this.doneButtonPressed = true;
                        PhotoViewer.this.closePhoto(false, false);
                    }
                }
            });
            this.itemsLayout = new LinearLayout(this.parentActivity);
            this.itemsLayout.setOrientation(0);
            this.pickerView.addView(this.itemsLayout, LayoutHelper.createFrame(-2, 48.0f, 81, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 34.0f, BitmapDescriptorFactory.HUE_RED));
            this.cropItem = new ImageView(this.parentActivity);
            this.cropItem.setScaleType(ScaleType.CENTER);
            this.cropItem.setImageResource(R.drawable.photo_crop);
            this.cropItem.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
            this.itemsLayout.addView(this.cropItem, LayoutHelper.createLinear(70, 48));
            this.cropItem.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PhotoViewer.this.switchToEditMode(1);
                }
            });
            this.paintItem = new ImageView(this.parentActivity);
            this.paintItem.setScaleType(ScaleType.CENTER);
            this.paintItem.setImageResource(R.drawable.photo_paint);
            this.paintItem.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
            this.itemsLayout.addView(this.paintItem, LayoutHelper.createLinear(70, 48));
            this.paintItem.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PhotoViewer.this.switchToEditMode(3);
                }
            });
            this.tuneItem = new ImageView(this.parentActivity);
            this.tuneItem.setScaleType(ScaleType.CENTER);
            this.tuneItem.setImageResource(R.drawable.photo_tools);
            this.tuneItem.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
            this.itemsLayout.addView(this.tuneItem, LayoutHelper.createLinear(70, 48));
            this.tuneItem.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PhotoViewer.this.switchToEditMode(2);
                }
            });
            this.compressItem = new ImageView(this.parentActivity);
            this.compressItem.setTag(Integer.valueOf(1));
            this.compressItem.setScaleType(ScaleType.CENTER);
            this.compressItem.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
            this.selectedCompression = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("compress_video2", 1);
            if (this.selectedCompression <= 0) {
                this.compressItem.setImageResource(R.drawable.video_240);
            } else if (this.selectedCompression == 1) {
                this.compressItem.setImageResource(R.drawable.video_360);
            } else if (this.selectedCompression == 2) {
                this.compressItem.setImageResource(R.drawable.video_480);
            } else if (this.selectedCompression == 3) {
                this.compressItem.setImageResource(R.drawable.video_720);
            } else if (this.selectedCompression == 4) {
                this.compressItem.setImageResource(R.drawable.video_1080);
            }
            this.itemsLayout.addView(this.compressItem, LayoutHelper.createLinear(70, 48));
            this.compressItem.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PhotoViewer.this.showQualityView(true);
                    PhotoViewer.this.requestVideoPreview(1);
                }
            });
            this.muteItem = new ImageView(this.parentActivity);
            this.muteItem.setScaleType(ScaleType.CENTER);
            this.muteItem.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
            this.itemsLayout.addView(this.muteItem, LayoutHelper.createLinear(70, 48));
            this.muteItem.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PhotoViewer.this.muteVideo = !PhotoViewer.this.muteVideo;
                    if (!PhotoViewer.this.muteVideo || PhotoViewer.this.checkImageView.isChecked()) {
                        Object obj = PhotoViewer.this.imagesArrLocals.get(PhotoViewer.this.currentIndex);
                        if (obj instanceof PhotoEntry) {
                            ((PhotoEntry) obj).editedInfo = PhotoViewer.this.getCurrentVideoEditedInfo();
                        }
                    } else {
                        PhotoViewer.this.checkImageView.callOnClick();
                    }
                    PhotoViewer.this.updateMuteButton();
                }
            });
            this.timeItem = new ImageView(this.parentActivity);
            this.timeItem.setScaleType(ScaleType.CENTER);
            this.timeItem.setImageResource(R.drawable.photo_timer);
            this.timeItem.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
            this.itemsLayout.addView(this.timeItem, LayoutHelper.createLinear(70, 48));
            this.timeItem.setOnClickListener(new OnClickListener() {

                /* renamed from: org.telegram.ui.PhotoViewer$21$1 */
                class C50471 implements OnTouchListener {
                    C50471() {
                    }

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                }

                /* renamed from: org.telegram.ui.PhotoViewer$21$2 */
                class C50482 implements OnTouchListener {
                    C50482() {
                    }

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                }

                /* renamed from: org.telegram.ui.PhotoViewer$21$3 */
                class C50493 implements Formatter {
                    C50493() {
                    }

                    public String format(int i) {
                        return i == 0 ? LocaleController.getString("ShortMessageLifetimeForever", R.string.ShortMessageLifetimeForever) : (i < 1 || i >= 21) ? LocaleController.formatTTLString((i - 16) * 5) : LocaleController.formatTTLString(i);
                    }
                }

                public void onClick(View view) {
                    if (PhotoViewer.this.parentActivity != null) {
                        BottomSheet.Builder builder = new BottomSheet.Builder(PhotoViewer.this.parentActivity);
                        builder.setUseHardwareLayer(false);
                        View linearLayout = new LinearLayout(PhotoViewer.this.parentActivity);
                        linearLayout.setOrientation(1);
                        builder.setCustomView(linearLayout);
                        View textView = new TextView(PhotoViewer.this.parentActivity);
                        textView.setLines(1);
                        textView.setSingleLine(true);
                        textView.setText(LocaleController.getString("MessageLifetime", R.string.MessageLifetime));
                        textView.setTextColor(-1);
                        textView.setTextSize(1, 16.0f);
                        textView.setEllipsize(TruncateAt.MIDDLE);
                        textView.setPadding(AndroidUtilities.dp(21.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(21.0f), AndroidUtilities.dp(4.0f));
                        textView.setGravity(16);
                        linearLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f));
                        textView.setOnTouchListener(new C50471());
                        View textView2 = new TextView(PhotoViewer.this.parentActivity);
                        textView2.setText(PhotoViewer.this.isCurrentVideo ? LocaleController.getString("MessageLifetimeVideo", R.string.MessageLifetimeVideo) : LocaleController.getString("MessageLifetimePhoto", R.string.MessageLifetimePhoto));
                        textView2.setTextColor(-8355712);
                        textView2.setTextSize(1, 14.0f);
                        textView2.setEllipsize(TruncateAt.MIDDLE);
                        textView2.setPadding(AndroidUtilities.dp(21.0f), 0, AndroidUtilities.dp(21.0f), AndroidUtilities.dp(8.0f));
                        textView2.setGravity(16);
                        linearLayout.addView(textView2, LayoutHelper.createFrame(-1, -2.0f));
                        textView2.setOnTouchListener(new C50482());
                        final BottomSheet create = builder.create();
                        textView2 = new NumberPicker(PhotoViewer.this.parentActivity);
                        textView2.setMinValue(0);
                        textView2.setMaxValue(28);
                        Object obj = PhotoViewer.this.imagesArrLocals.get(PhotoViewer.this.currentIndex);
                        int i = obj instanceof PhotoEntry ? ((PhotoEntry) obj).ttl : obj instanceof SearchImage ? ((SearchImage) obj).ttl : 0;
                        if (i == 0) {
                            textView2.setValue(ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("self_destruct", 7));
                        } else if (i < 0 || i >= 21) {
                            textView2.setValue(((i / 5) + 21) - 5);
                        } else {
                            textView2.setValue(i);
                        }
                        textView2.setTextColor(-1);
                        textView2.setSelectorColor(-11711155);
                        textView2.setFormatter(new C50493());
                        linearLayout.addView(textView2, LayoutHelper.createLinear(-1, -2));
                        textView = new FrameLayout(PhotoViewer.this.parentActivity) {
                            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                                int childCount = getChildCount();
                                View view = null;
                                int i5 = i3 - i;
                                int i6 = 0;
                                while (i6 < childCount) {
                                    View view2;
                                    View childAt = getChildAt(i6);
                                    if (((Integer) childAt.getTag()).intValue() == -1) {
                                        childAt.layout((i5 - getPaddingRight()) - childAt.getMeasuredWidth(), getPaddingTop(), (i5 - getPaddingRight()) + childAt.getMeasuredWidth(), getPaddingTop() + childAt.getMeasuredHeight());
                                        view2 = childAt;
                                    } else if (((Integer) childAt.getTag()).intValue() == -2) {
                                        int paddingRight = (i5 - getPaddingRight()) - childAt.getMeasuredWidth();
                                        if (view != null) {
                                            paddingRight -= view.getMeasuredWidth() + AndroidUtilities.dp(8.0f);
                                        }
                                        childAt.layout(paddingRight, getPaddingTop(), childAt.getMeasuredWidth() + paddingRight, getPaddingTop() + childAt.getMeasuredHeight());
                                        view2 = view;
                                    } else {
                                        childAt.layout(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + childAt.getMeasuredWidth(), getPaddingTop() + childAt.getMeasuredHeight());
                                        view2 = view;
                                    }
                                    i6++;
                                    view = view2;
                                }
                            }
                        };
                        textView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(8.0f));
                        linearLayout.addView(textView, LayoutHelper.createLinear(-1, 52));
                        linearLayout = new TextView(PhotoViewer.this.parentActivity);
                        linearLayout.setMinWidth(AndroidUtilities.dp(64.0f));
                        linearLayout.setTag(Integer.valueOf(-1));
                        linearLayout.setTextSize(1, 14.0f);
                        linearLayout.setTextColor(-11944718);
                        linearLayout.setGravity(17);
                        linearLayout.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                        linearLayout.setText(LocaleController.getString("Done", R.string.Done).toUpperCase());
                        linearLayout.setBackgroundDrawable(Theme.getRoundRectSelectorDrawable());
                        linearLayout.setPadding(AndroidUtilities.dp(10.0f), 0, AndroidUtilities.dp(10.0f), 0);
                        textView.addView(linearLayout, LayoutHelper.createFrame(-2, 36, 53));
                        linearLayout.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                int value = textView2.getValue();
                                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                                edit.putInt("self_destruct", value);
                                edit.commit();
                                create.dismiss();
                                int i = (value < 0 || value >= 21) ? (value - 16) * 5 : value;
                                Object obj = PhotoViewer.this.imagesArrLocals.get(PhotoViewer.this.currentIndex);
                                if (obj instanceof PhotoEntry) {
                                    ((PhotoEntry) obj).ttl = i;
                                } else if (obj instanceof SearchImage) {
                                    ((SearchImage) obj).ttl = i;
                                }
                                PhotoViewer.this.timeItem.setColorFilter(i != 0 ? new PorterDuffColorFilter(-12734994, Mode.MULTIPLY) : null);
                                if (!PhotoViewer.this.checkImageView.isChecked()) {
                                    PhotoViewer.this.checkImageView.callOnClick();
                                }
                            }
                        });
                        linearLayout = new TextView(PhotoViewer.this.parentActivity);
                        linearLayout.setMinWidth(AndroidUtilities.dp(64.0f));
                        linearLayout.setTag(Integer.valueOf(-2));
                        linearLayout.setTextSize(1, 14.0f);
                        linearLayout.setTextColor(-11944718);
                        linearLayout.setGravity(17);
                        linearLayout.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                        linearLayout.setText(LocaleController.getString("Cancel", R.string.Cancel).toUpperCase());
                        linearLayout.setBackgroundDrawable(Theme.getRoundRectSelectorDrawable());
                        linearLayout.setPadding(AndroidUtilities.dp(10.0f), 0, AndroidUtilities.dp(10.0f), 0);
                        textView.addView(linearLayout, LayoutHelper.createFrame(-2, 36, 53));
                        linearLayout.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                create.dismiss();
                            }
                        });
                        create.show();
                        create.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
                    }
                }
            });
            this.editorDoneLayout = new PickerBottomLayoutViewer(this.actvityContext);
            this.editorDoneLayout.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            this.editorDoneLayout.updateSelectedCount(0, false);
            this.editorDoneLayout.setVisibility(8);
            this.containerView.addView(this.editorDoneLayout, LayoutHelper.createFrame(-1, 48, 83));
            this.editorDoneLayout.cancelButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (PhotoViewer.this.currentEditMode == 1) {
                        PhotoViewer.this.photoCropView.cancelAnimationRunnable();
                    }
                    PhotoViewer.this.switchToEditMode(0);
                }
            });
            this.editorDoneLayout.doneButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (PhotoViewer.this.currentEditMode != 1 || PhotoViewer.this.photoCropView.isReady()) {
                        PhotoViewer.this.applyCurrentEditMode();
                        PhotoViewer.this.switchToEditMode(0);
                    }
                }
            });
            this.resetButton = new TextView(this.actvityContext);
            this.resetButton.setVisibility(8);
            this.resetButton.setTextSize(1, 14.0f);
            this.resetButton.setTextColor(-1);
            this.resetButton.setGravity(17);
            this.resetButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, 0));
            this.resetButton.setPadding(AndroidUtilities.dp(20.0f), 0, AndroidUtilities.dp(20.0f), 0);
            this.resetButton.setText(LocaleController.getString("Reset", R.string.CropReset).toUpperCase());
            this.resetButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.editorDoneLayout.addView(this.resetButton, LayoutHelper.createFrame(-2, -1, 49));
            this.resetButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PhotoViewer.this.photoCropView.reset();
                }
            });
            this.gestureDetector = new GestureDetector(this.containerView.getContext(), this);
            this.gestureDetector.setOnDoubleTapListener(this);
            ImageReceiverDelegate anonymousClass25 = new ImageReceiverDelegate() {
                public void didSetImage(ImageReceiver imageReceiver, boolean z, boolean z2) {
                    if (imageReceiver == PhotoViewer.this.centerImage && z && !z2 && PhotoViewer.this.currentEditMode == 1 && PhotoViewer.this.photoCropView != null) {
                        Bitmap bitmap = imageReceiver.getBitmap();
                        if (bitmap != null) {
                            PhotoViewer.this.photoCropView.setBitmap(bitmap, imageReceiver.getOrientation(), PhotoViewer.this.sendPhotoType != 1);
                        }
                    }
                    if (imageReceiver != PhotoViewer.this.centerImage || !z || PhotoViewer.this.placeProvider == null || !PhotoViewer.this.placeProvider.scaleToFill() || PhotoViewer.this.ignoreDidSetImage) {
                        return;
                    }
                    if (PhotoViewer.this.wasLayout) {
                        PhotoViewer.this.setScaleToFill();
                    } else {
                        PhotoViewer.this.dontResetZoomOnFirstLayout = true;
                    }
                }
            };
            this.centerImage.setParentView(this.containerView);
            this.centerImage.setCrossfadeAlpha((byte) 2);
            this.centerImage.setInvalidateAll(true);
            this.centerImage.setDelegate(anonymousClass25);
            this.leftImage.setParentView(this.containerView);
            this.leftImage.setCrossfadeAlpha((byte) 2);
            this.leftImage.setInvalidateAll(true);
            this.leftImage.setDelegate(anonymousClass25);
            this.rightImage.setParentView(this.containerView);
            this.rightImage.setCrossfadeAlpha((byte) 2);
            this.rightImage.setInvalidateAll(true);
            this.rightImage.setDelegate(anonymousClass25);
            int rotation = ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
            this.checkImageView = new CheckBox(this.containerView.getContext(), R.drawable.selectphoto_large) {
                public boolean onTouchEvent(MotionEvent motionEvent) {
                    return PhotoViewer.this.bottomTouchEnabled && super.onTouchEvent(motionEvent);
                }
            };
            this.checkImageView.setDrawBackground(true);
            this.checkImageView.setHasBorder(true);
            this.checkImageView.setSize(40);
            this.checkImageView.setCheckOffset(AndroidUtilities.dp(1.0f));
            this.checkImageView.setColor(-10043398, -1);
            this.checkImageView.setVisibility(8);
            FrameLayoutDrawer frameLayoutDrawer = this.containerView;
            View view = this.checkImageView;
            float f = (rotation == 3 || rotation == 1) ? 58.0f : 68.0f;
            frameLayoutDrawer.addView(view, LayoutHelper.createFrame(40, 40.0f, 53, BitmapDescriptorFactory.HUE_RED, f, 10.0f, BitmapDescriptorFactory.HUE_RED));
            if (VERSION.SDK_INT >= 21) {
                layoutParams = (FrameLayout.LayoutParams) this.checkImageView.getLayoutParams();
                layoutParams.topMargin += AndroidUtilities.statusBarHeight;
            }
            this.checkImageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    PhotoViewer.this.setPhotoChecked();
                }
            });
            this.photosCounterView = new CounterView(this.parentActivity);
            frameLayoutDrawer = this.containerView;
            view = this.photosCounterView;
            f = (rotation == 3 || rotation == 1) ? 58.0f : 68.0f;
            frameLayoutDrawer.addView(view, LayoutHelper.createFrame(40, 40.0f, 53, BitmapDescriptorFactory.HUE_RED, f, 66.0f, BitmapDescriptorFactory.HUE_RED));
            if (VERSION.SDK_INT >= 21) {
                layoutParams = (FrameLayout.LayoutParams) this.photosCounterView.getLayoutParams();
                layoutParams.topMargin += AndroidUtilities.statusBarHeight;
            }
            this.photosCounterView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (PhotoViewer.this.placeProvider != null && PhotoViewer.this.placeProvider.getSelectedPhotosOrder() != null && !PhotoViewer.this.placeProvider.getSelectedPhotosOrder().isEmpty()) {
                        PhotoViewer.this.togglePhotosListView(!PhotoViewer.this.isPhotosListViewVisible, true);
                    }
                }
            });
            this.selectedPhotosListView = new RecyclerListView(this.parentActivity);
            this.selectedPhotosListView.setVisibility(8);
            this.selectedPhotosListView.setAlpha(BitmapDescriptorFactory.HUE_RED);
            this.selectedPhotosListView.setTranslationY((float) (-AndroidUtilities.dp(10.0f)));
            this.selectedPhotosListView.addItemDecoration(new ItemDecoration() {
                public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
                    int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
                    if ((view instanceof PhotoPickerPhotoCell) && childAdapterPosition == 0) {
                        rect.left = AndroidUtilities.dp(3.0f);
                    } else {
                        rect.left = 0;
                    }
                    rect.right = AndroidUtilities.dp(3.0f);
                }
            });
            this.selectedPhotosListView.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            this.selectedPhotosListView.setPadding(0, AndroidUtilities.dp(3.0f), 0, AndroidUtilities.dp(3.0f));
            this.selectedPhotosListView.setLayoutManager(new LinearLayoutManager(this.parentActivity, 0, false));
            RecyclerListView recyclerListView = this.selectedPhotosListView;
            Adapter listAdapter = new ListAdapter(this.parentActivity);
            this.selectedPhotosAdapter = listAdapter;
            recyclerListView.setAdapter(listAdapter);
            this.containerView.addView(this.selectedPhotosListView, LayoutHelper.createFrame(-1, 88, 51));
            this.selectedPhotosListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(View view, int i) {
                    if (i == 0 && PhotoViewer.this.placeProvider.allowGroupPhotos()) {
                        boolean isGroupPhotosEnabled = MediaController.getInstance().isGroupPhotosEnabled();
                        MediaController.getInstance().toggleGroupPhotosEnabled();
                        PhotoViewer.this.placeProvider.toggleGroupPhotosEnabled();
                        ((ImageView) view).setColorFilter(!isGroupPhotosEnabled ? new PorterDuffColorFilter(-10043398, Mode.MULTIPLY) : null);
                        PhotoViewer.this.showHint(false, !isGroupPhotosEnabled);
                        return;
                    }
                    int indexOf = PhotoViewer.this.imagesArrLocals.indexOf(view.getTag());
                    if (indexOf >= 0) {
                        PhotoViewer.this.currentIndex = -1;
                        PhotoViewer.this.setImageIndex(indexOf, false);
                    }
                }
            });
            this.captionEditText = new PhotoViewerCaptionEnterView(this.actvityContext, this.containerView, this.windowView) {
                public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                    try {
                        return !PhotoViewer.this.bottomTouchEnabled && super.dispatchTouchEvent(motionEvent);
                    } catch (Throwable e) {
                        FileLog.e(e);
                        return false;
                    }
                }

                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    try {
                        return !PhotoViewer.this.bottomTouchEnabled && super.onInterceptTouchEvent(motionEvent);
                    } catch (Throwable e) {
                        FileLog.e(e);
                        return false;
                    }
                }

                public boolean onTouchEvent(MotionEvent motionEvent) {
                    return !PhotoViewer.this.bottomTouchEnabled && super.onTouchEvent(motionEvent);
                }
            };
            this.captionEditText.setDelegate(new PhotoViewerCaptionEnterViewDelegate() {
                public void onCaptionEnter() {
                    PhotoViewer.this.closeCaptionEnter(true);
                }

                public void onTextChanged(CharSequence charSequence) {
                    if (PhotoViewer.this.mentionsAdapter != null && PhotoViewer.this.captionEditText != null && PhotoViewer.this.parentChatActivity != null && charSequence != null) {
                        PhotoViewer.this.mentionsAdapter.searchUsernameOrHashtag(charSequence.toString(), PhotoViewer.this.captionEditText.getCursorPosition(), PhotoViewer.this.parentChatActivity.messages, false);
                    }
                }

                public void onWindowSizeChanged(int i) {
                    if (i - (ActionBar.getCurrentActionBarHeight() * 2) < AndroidUtilities.dp((float) ((PhotoViewer.this.mentionsAdapter.getItemCount() > 3 ? 18 : 0) + (Math.min(3, PhotoViewer.this.mentionsAdapter.getItemCount()) * 36)))) {
                        PhotoViewer.this.allowMentions = false;
                        if (PhotoViewer.this.mentionListView != null && PhotoViewer.this.mentionListView.getVisibility() == 0) {
                            PhotoViewer.this.mentionListView.setVisibility(4);
                            return;
                        }
                        return;
                    }
                    PhotoViewer.this.allowMentions = true;
                    if (PhotoViewer.this.mentionListView != null && PhotoViewer.this.mentionListView.getVisibility() == 4) {
                        PhotoViewer.this.mentionListView.setVisibility(0);
                    }
                }
            });
            this.containerView.addView(this.captionEditText, LayoutHelper.createFrame(-1, -2, 83));
            this.mentionListView = new RecyclerListView(this.actvityContext) {
                public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                    return !PhotoViewer.this.bottomTouchEnabled && super.dispatchTouchEvent(motionEvent);
                }

                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    return !PhotoViewer.this.bottomTouchEnabled && super.onInterceptTouchEvent(motionEvent);
                }

                public boolean onTouchEvent(MotionEvent motionEvent) {
                    return !PhotoViewer.this.bottomTouchEnabled && super.onTouchEvent(motionEvent);
                }
            };
            this.mentionListView.setTag(Integer.valueOf(5));
            this.mentionLayoutManager = new LinearLayoutManager(this.actvityContext) {
                public boolean supportsPredictiveItemAnimations() {
                    return false;
                }
            };
            this.mentionLayoutManager.setOrientation(1);
            this.mentionListView.setLayoutManager(this.mentionLayoutManager);
            this.mentionListView.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            this.mentionListView.setVisibility(8);
            this.mentionListView.setClipToPadding(true);
            this.mentionListView.setOverScrollMode(2);
            this.containerView.addView(this.mentionListView, LayoutHelper.createFrame(-1, 110, 83));
            RecyclerListView recyclerListView2 = this.mentionListView;
            Adapter mentionsAdapter = new MentionsAdapter(this.actvityContext, true, 0, new MentionsAdapterDelegate() {

                /* renamed from: org.telegram.ui.PhotoViewer$35$1 */
                class C50541 extends AnimatorListenerAdapter {
                    C50541() {
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (PhotoViewer.this.mentionListAnimation != null && PhotoViewer.this.mentionListAnimation.equals(animator)) {
                            PhotoViewer.this.mentionListAnimation = null;
                        }
                    }
                }

                /* renamed from: org.telegram.ui.PhotoViewer$35$2 */
                class C50552 extends AnimatorListenerAdapter {
                    C50552() {
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (PhotoViewer.this.mentionListAnimation != null && PhotoViewer.this.mentionListAnimation.equals(animator)) {
                            PhotoViewer.this.mentionListView.setVisibility(8);
                            PhotoViewer.this.mentionListAnimation = null;
                        }
                    }
                }

                public void needChangePanelVisibility(boolean z) {
                    if (z) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) PhotoViewer.this.mentionListView.getLayoutParams();
                        int min = (PhotoViewer.this.mentionsAdapter.getItemCount() > 3 ? 18 : 0) + (Math.min(3, PhotoViewer.this.mentionsAdapter.getItemCount()) * 36);
                        layoutParams.height = AndroidUtilities.dp((float) min);
                        layoutParams.topMargin = -AndroidUtilities.dp((float) min);
                        PhotoViewer.this.mentionListView.setLayoutParams(layoutParams);
                        if (PhotoViewer.this.mentionListAnimation != null) {
                            PhotoViewer.this.mentionListAnimation.cancel();
                            PhotoViewer.this.mentionListAnimation = null;
                        }
                        if (PhotoViewer.this.mentionListView.getVisibility() == 0) {
                            PhotoViewer.this.mentionListView.setAlpha(1.0f);
                            return;
                        }
                        PhotoViewer.this.mentionLayoutManager.scrollToPositionWithOffset(0, 10000);
                        if (PhotoViewer.this.allowMentions) {
                            PhotoViewer.this.mentionListView.setVisibility(0);
                            PhotoViewer.this.mentionListAnimation = new AnimatorSet();
                            PhotoViewer.this.mentionListAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(PhotoViewer.this.mentionListView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
                            PhotoViewer.this.mentionListAnimation.addListener(new C50541());
                            PhotoViewer.this.mentionListAnimation.setDuration(200);
                            PhotoViewer.this.mentionListAnimation.start();
                            return;
                        }
                        PhotoViewer.this.mentionListView.setAlpha(1.0f);
                        PhotoViewer.this.mentionListView.setVisibility(4);
                        return;
                    }
                    if (PhotoViewer.this.mentionListAnimation != null) {
                        PhotoViewer.this.mentionListAnimation.cancel();
                        PhotoViewer.this.mentionListAnimation = null;
                    }
                    if (PhotoViewer.this.mentionListView.getVisibility() == 8) {
                        return;
                    }
                    if (PhotoViewer.this.allowMentions) {
                        PhotoViewer.this.mentionListAnimation = new AnimatorSet();
                        AnimatorSet access$9300 = PhotoViewer.this.mentionListAnimation;
                        Animator[] animatorArr = new Animator[1];
                        animatorArr[0] = ObjectAnimator.ofFloat(PhotoViewer.this.mentionListView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                        access$9300.playTogether(animatorArr);
                        PhotoViewer.this.mentionListAnimation.addListener(new C50552());
                        PhotoViewer.this.mentionListAnimation.setDuration(200);
                        PhotoViewer.this.mentionListAnimation.start();
                        return;
                    }
                    PhotoViewer.this.mentionListView.setVisibility(8);
                }

                public void onContextClick(BotInlineResult botInlineResult) {
                }

                public void onContextSearch(boolean z) {
                }
            });
            this.mentionsAdapter = mentionsAdapter;
            recyclerListView2.setAdapter(mentionsAdapter);
            this.mentionsAdapter.setAllowNewMentions(false);
            this.mentionListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(View view, int i) {
                    Object item = PhotoViewer.this.mentionsAdapter.getItem(i);
                    int resultStartPosition = PhotoViewer.this.mentionsAdapter.getResultStartPosition();
                    int resultLength = PhotoViewer.this.mentionsAdapter.getResultLength();
                    if (item instanceof User) {
                        User user = (User) item;
                        if (user != null) {
                            PhotoViewer.this.captionEditText.replaceWithText(resultStartPosition, resultLength, "@" + user.username + " ", false);
                        }
                    } else if (item instanceof String) {
                        PhotoViewer.this.captionEditText.replaceWithText(resultStartPosition, resultLength, item + " ", false);
                    } else if (item instanceof EmojiSuggestion) {
                        CharSequence charSequence = ((EmojiSuggestion) item).emoji;
                        PhotoViewer.this.captionEditText.addEmojiToRecent(charSequence);
                        PhotoViewer.this.captionEditText.replaceWithText(resultStartPosition, resultLength, charSequence, true);
                    }
                }
            });
            this.mentionListView.setOnItemLongClickListener(new OnItemLongClickListener() {

                /* renamed from: org.telegram.ui.PhotoViewer$37$1 */
                class C50561 implements DialogInterface.OnClickListener {
                    C50561() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        PhotoViewer.this.mentionsAdapter.clearRecentHashtags();
                    }
                }

                public boolean onItemClick(View view, int i) {
                    if (!(PhotoViewer.this.mentionsAdapter.getItem(i) instanceof String)) {
                        return false;
                    }
                    Builder builder = new Builder(PhotoViewer.this.parentActivity);
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setMessage(LocaleController.getString("ClearSearch", R.string.ClearSearch));
                    builder.setPositiveButton(LocaleController.getString("ClearButton", R.string.ClearButton).toUpperCase(), new C50561());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    PhotoViewer.this.showAlertDialog(builder);
                    return true;
                }
            });
        }
    }

    public void setParentAlert(ChatAttachAlert chatAttachAlert) {
        this.parentAlert = chatAttachAlert;
    }

    public void setParentChatActivity(ChatActivity chatActivity) {
        this.parentChatActivity = chatActivity;
    }

    public void showAlertDialog(Builder builder) {
        if (this.parentActivity != null) {
            try {
                if (this.visibleDialog != null) {
                    this.visibleDialog.dismiss();
                    this.visibleDialog = null;
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            try {
                this.visibleDialog = builder.show();
                this.visibleDialog.setCanceledOnTouchOutside(true);
                this.visibleDialog.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss(DialogInterface dialogInterface) {
                        PhotoViewer.this.visibleDialog = null;
                    }
                });
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
        }
    }

    public void switchToEditMode(final int i) {
        if (this.currentEditMode == i || this.centerImage.getBitmap() == null || this.changeModeAnimation != null || this.imageMoveAnimation != null || this.photoProgressViews[0].backgroundState != -1 || this.captionEditText.getTag() != null) {
            return;
        }
        if (i == 0) {
            if (this.centerImage.getBitmap() != null) {
                int bitmapWidth = this.centerImage.getBitmapWidth();
                int bitmapHeight = this.centerImage.getBitmapHeight();
                float containerViewWidth = ((float) getContainerViewWidth()) / ((float) bitmapWidth);
                float containerViewHeight = ((float) getContainerViewHeight()) / ((float) bitmapHeight);
                float containerViewWidth2 = ((float) getContainerViewWidth(0)) / ((float) bitmapWidth);
                float containerViewHeight2 = ((float) getContainerViewHeight(0)) / ((float) bitmapHeight);
                if (containerViewWidth > containerViewHeight) {
                    containerViewWidth = containerViewHeight;
                }
                if (containerViewWidth2 <= containerViewHeight2) {
                    containerViewHeight2 = containerViewWidth2;
                }
                if (this.sendPhotoType != 1 || this.applying) {
                    this.animateToScale = containerViewHeight2 / containerViewWidth;
                } else {
                    containerViewHeight = (float) Math.min(getContainerViewWidth(), getContainerViewHeight());
                    containerViewWidth2 = containerViewHeight / ((float) bitmapWidth);
                    containerViewHeight /= (float) bitmapHeight;
                    if (containerViewWidth2 <= containerViewHeight) {
                        containerViewWidth2 = containerViewHeight;
                    }
                    this.scale = containerViewWidth2 / containerViewWidth;
                    this.animateToScale = (containerViewHeight2 * this.scale) / containerViewWidth2;
                }
                this.animateToX = BitmapDescriptorFactory.HUE_RED;
                if (this.currentEditMode == 1) {
                    this.animateToY = (float) AndroidUtilities.dp(58.0f);
                } else if (this.currentEditMode == 2) {
                    this.animateToY = (float) AndroidUtilities.dp(92.0f);
                } else if (this.currentEditMode == 3) {
                    this.animateToY = (float) AndroidUtilities.dp(44.0f);
                }
                if (VERSION.SDK_INT >= 21) {
                    this.animateToY -= (float) (AndroidUtilities.statusBarHeight / 2);
                }
                this.animationStartTime = System.currentTimeMillis();
                this.zoomAnimation = true;
            }
            this.imageMoveAnimation = new AnimatorSet();
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (this.currentEditMode == 1) {
                animatorSet = this.imageMoveAnimation;
                animatorArr = new Animator[3];
                animatorArr[0] = ObjectAnimator.ofFloat(this.editorDoneLayout, "translationY", new float[]{(float) AndroidUtilities.dp(48.0f)});
                animatorArr[1] = ObjectAnimator.ofFloat(this, "animationValue", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.photoCropView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorSet.playTogether(animatorArr);
            } else if (this.currentEditMode == 2) {
                this.photoFilterView.shutdown();
                animatorSet = this.imageMoveAnimation;
                animatorArr = new Animator[2];
                animatorArr[0] = ObjectAnimator.ofFloat(this.photoFilterView.getToolsView(), "translationY", new float[]{(float) AndroidUtilities.dp(186.0f)});
                animatorArr[1] = ObjectAnimator.ofFloat(this, "animationValue", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                animatorSet.playTogether(animatorArr);
            } else if (this.currentEditMode == 3) {
                this.photoPaintView.shutdown();
                animatorSet = this.imageMoveAnimation;
                animatorArr = new Animator[3];
                animatorArr[0] = ObjectAnimator.ofFloat(this.photoPaintView.getToolsView(), "translationY", new float[]{(float) AndroidUtilities.dp(126.0f)});
                animatorArr[1] = ObjectAnimator.ofFloat(this.photoPaintView.getColorPicker(), "translationY", new float[]{(float) AndroidUtilities.dp(126.0f)});
                animatorArr[2] = ObjectAnimator.ofFloat(this, "animationValue", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                animatorSet.playTogether(animatorArr);
            }
            this.imageMoveAnimation.setDuration(200);
            this.imageMoveAnimation.addListener(new AnimatorListenerAdapter() {

                /* renamed from: org.telegram.ui.PhotoViewer$40$1 */
                class C50611 extends AnimatorListenerAdapter {
                    C50611() {
                    }

                    public void onAnimationStart(Animator animator) {
                        PhotoViewer.this.pickerView.setVisibility(0);
                        PhotoViewer.this.actionBar.setVisibility(0);
                        if (PhotoViewer.this.needCaptionLayout) {
                            PhotoViewer.this.captionTextView.setVisibility(PhotoViewer.this.captionTextView.getTag() != null ? 0 : 4);
                        }
                        if (PhotoViewer.this.sendPhotoType == 0) {
                            PhotoViewer.this.checkImageView.setVisibility(0);
                            PhotoViewer.this.photosCounterView.setVisibility(0);
                        }
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (PhotoViewer.this.currentEditMode == 1) {
                        PhotoViewer.this.editorDoneLayout.setVisibility(8);
                        PhotoViewer.this.photoCropView.setVisibility(8);
                    } else if (PhotoViewer.this.currentEditMode == 2) {
                        PhotoViewer.this.containerView.removeView(PhotoViewer.this.photoFilterView);
                        PhotoViewer.this.photoFilterView = null;
                    } else if (PhotoViewer.this.currentEditMode == 3) {
                        PhotoViewer.this.containerView.removeView(PhotoViewer.this.photoPaintView);
                        PhotoViewer.this.photoPaintView = null;
                    }
                    PhotoViewer.this.imageMoveAnimation = null;
                    PhotoViewer.this.currentEditMode = i;
                    PhotoViewer.this.applying = false;
                    PhotoViewer.this.animateToScale = 1.0f;
                    PhotoViewer.this.animateToX = BitmapDescriptorFactory.HUE_RED;
                    PhotoViewer.this.animateToY = BitmapDescriptorFactory.HUE_RED;
                    PhotoViewer.this.scale = 1.0f;
                    PhotoViewer.this.updateMinMax(PhotoViewer.this.scale);
                    PhotoViewer.this.containerView.invalidate();
                    AnimatorSet animatorSet = new AnimatorSet();
                    Collection arrayList = new ArrayList();
                    arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.pickerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED}));
                    arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.actionBar, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED}));
                    if (PhotoViewer.this.needCaptionLayout) {
                        arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.captionTextView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED}));
                    }
                    if (PhotoViewer.this.sendPhotoType == 0) {
                        arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.checkImageView, "alpha", new float[]{1.0f}));
                        arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.photosCounterView, "alpha", new float[]{1.0f}));
                    }
                    animatorSet.playTogether(arrayList);
                    animatorSet.setDuration(200);
                    animatorSet.addListener(new C50611());
                    animatorSet.start();
                }
            });
            this.imageMoveAnimation.start();
        } else if (i == 1) {
            if (this.photoCropView == null) {
                this.photoCropView = new PhotoCropView(this.actvityContext);
                this.photoCropView.setVisibility(8);
                this.containerView.addView(this.photoCropView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
                this.photoCropView.setDelegate(new PhotoCropViewDelegate() {
                    public Bitmap getBitmap() {
                        return PhotoViewer.this.centerImage.getBitmap();
                    }

                    public void needMoveImageTo(float f, float f2, float f3, boolean z) {
                        if (z) {
                            PhotoViewer.this.animateTo(f3, f, f2, true);
                            return;
                        }
                        PhotoViewer.this.translationX = f;
                        PhotoViewer.this.translationY = f2;
                        PhotoViewer.this.scale = f3;
                        PhotoViewer.this.containerView.invalidate();
                    }

                    public void onChange(boolean z) {
                        PhotoViewer.this.resetButton.setVisibility(z ? 8 : 0);
                    }
                });
            }
            this.photoCropView.onAppear();
            this.editorDoneLayout.doneButton.setText(LocaleController.getString("Crop", R.string.Crop));
            this.editorDoneLayout.doneButton.setTextColor(-11420173);
            this.changeModeAnimation = new AnimatorSet();
            r0 = new ArrayList();
            r0.add(ObjectAnimator.ofFloat(this.pickerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(96.0f)}));
            r0.add(ObjectAnimator.ofFloat(this.actionBar, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) (-this.actionBar.getHeight())}));
            if (this.needCaptionLayout) {
                r0.add(ObjectAnimator.ofFloat(this.captionTextView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(96.0f)}));
            }
            if (this.sendPhotoType == 0) {
                r0.add(ObjectAnimator.ofFloat(this.checkImageView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
                r0.add(ObjectAnimator.ofFloat(this.photosCounterView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
            }
            if (this.selectedPhotosListView.getVisibility() == 0) {
                r0.add(ObjectAnimator.ofFloat(this.selectedPhotosListView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
            }
            this.changeModeAnimation.playTogether(r0);
            this.changeModeAnimation.setDuration(200);
            this.changeModeAnimation.addListener(new AnimatorListenerAdapter() {

                /* renamed from: org.telegram.ui.PhotoViewer$42$1 */
                class C50621 extends AnimatorListenerAdapter {
                    C50621() {
                    }

                    public void onAnimationEnd(Animator animator) {
                        PhotoViewer.this.photoCropView.onAppeared();
                        PhotoViewer.this.imageMoveAnimation = null;
                        PhotoViewer.this.currentEditMode = i;
                        PhotoViewer.this.animateToScale = 1.0f;
                        PhotoViewer.this.animateToX = BitmapDescriptorFactory.HUE_RED;
                        PhotoViewer.this.animateToY = BitmapDescriptorFactory.HUE_RED;
                        PhotoViewer.this.scale = 1.0f;
                        PhotoViewer.this.updateMinMax(PhotoViewer.this.scale);
                        PhotoViewer.this.containerView.invalidate();
                    }

                    public void onAnimationStart(Animator animator) {
                        PhotoViewer.this.editorDoneLayout.setVisibility(0);
                        PhotoViewer.this.photoCropView.setVisibility(0);
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    PhotoViewer.this.changeModeAnimation = null;
                    PhotoViewer.this.pickerView.setVisibility(8);
                    PhotoViewer.this.selectedPhotosListView.setVisibility(8);
                    PhotoViewer.this.selectedPhotosListView.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    PhotoViewer.this.selectedPhotosListView.setTranslationY((float) (-AndroidUtilities.dp(10.0f)));
                    PhotoViewer.this.photosCounterView.setRotationX(BitmapDescriptorFactory.HUE_RED);
                    PhotoViewer.this.selectedPhotosListView.setEnabled(false);
                    PhotoViewer.this.isPhotosListViewVisible = false;
                    if (PhotoViewer.this.needCaptionLayout) {
                        PhotoViewer.this.captionTextView.setVisibility(4);
                    }
                    if (PhotoViewer.this.sendPhotoType == 0) {
                        PhotoViewer.this.checkImageView.setVisibility(8);
                        PhotoViewer.this.photosCounterView.setVisibility(8);
                    }
                    Bitmap bitmap = PhotoViewer.this.centerImage.getBitmap();
                    if (bitmap != null) {
                        PhotoViewer.this.photoCropView.setBitmap(bitmap, PhotoViewer.this.centerImage.getOrientation(), PhotoViewer.this.sendPhotoType != 1);
                        int bitmapWidth = PhotoViewer.this.centerImage.getBitmapWidth();
                        int bitmapHeight = PhotoViewer.this.centerImage.getBitmapHeight();
                        float access$2100 = ((float) PhotoViewer.this.getContainerViewWidth()) / ((float) bitmapWidth);
                        float access$2200 = ((float) PhotoViewer.this.getContainerViewHeight()) / ((float) bitmapHeight);
                        float access$11000 = ((float) PhotoViewer.this.getContainerViewWidth(1)) / ((float) bitmapWidth);
                        float access$11100 = ((float) PhotoViewer.this.getContainerViewHeight(1)) / ((float) bitmapHeight);
                        if (access$2100 <= access$2200) {
                            access$2200 = access$2100;
                        }
                        if (access$11000 <= access$11100) {
                            access$11100 = access$11000;
                        }
                        if (PhotoViewer.this.sendPhotoType == 1) {
                            access$11000 = (float) Math.min(PhotoViewer.this.getContainerViewWidth(1), PhotoViewer.this.getContainerViewHeight(1));
                            access$11100 = access$11000 / ((float) bitmapWidth);
                            access$11000 /= (float) bitmapHeight;
                            if (access$11100 <= access$11000) {
                                access$11100 = access$11000;
                            }
                        }
                        PhotoViewer.this.animateToScale = access$11100 / access$2200;
                        PhotoViewer.this.animateToX = BitmapDescriptorFactory.HUE_RED;
                        PhotoViewer.this.animateToY = (float) ((VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight / 2 : 0) + (-AndroidUtilities.dp(56.0f)));
                        PhotoViewer.this.animationStartTime = System.currentTimeMillis();
                        PhotoViewer.this.zoomAnimation = true;
                    }
                    PhotoViewer.this.imageMoveAnimation = new AnimatorSet();
                    AnimatorSet access$10200 = PhotoViewer.this.imageMoveAnimation;
                    r3 = new Animator[3];
                    r3[0] = ObjectAnimator.ofFloat(PhotoViewer.this.editorDoneLayout, "translationY", new float[]{(float) AndroidUtilities.dp(48.0f), BitmapDescriptorFactory.HUE_RED});
                    r3[1] = ObjectAnimator.ofFloat(PhotoViewer.this, "animationValue", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                    r3[2] = ObjectAnimator.ofFloat(PhotoViewer.this.photoCropView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                    access$10200.playTogether(r3);
                    PhotoViewer.this.imageMoveAnimation.setDuration(200);
                    PhotoViewer.this.imageMoveAnimation.addListener(new C50621());
                    PhotoViewer.this.imageMoveAnimation.start();
                }
            });
            this.changeModeAnimation.start();
        } else if (i == 2) {
            if (this.photoFilterView == null) {
                int i2;
                Bitmap bitmap;
                SavedFilterState savedFilterState = null;
                String str = null;
                if (!this.imagesArrLocals.isEmpty()) {
                    Object obj = this.imagesArrLocals.get(this.currentIndex);
                    if (obj instanceof PhotoEntry) {
                        PhotoEntry photoEntry = (PhotoEntry) obj;
                        if (photoEntry.imagePath == null) {
                            str = photoEntry.path;
                            savedFilterState = photoEntry.savedFilterState;
                        }
                        i2 = photoEntry.orientation;
                    } else if (obj instanceof SearchImage) {
                        SearchImage searchImage = (SearchImage) obj;
                        savedFilterState = searchImage.savedFilterState;
                        str = searchImage.imageUrl;
                        i2 = 0;
                    }
                    if (savedFilterState != null) {
                        bitmap = this.centerImage.getBitmap();
                        i2 = this.centerImage.getOrientation();
                    } else {
                        bitmap = BitmapFactory.decodeFile(str);
                    }
                    this.photoFilterView = new PhotoFilterView(this.parentActivity, bitmap, i2, savedFilterState);
                    this.containerView.addView(this.photoFilterView, LayoutHelper.createFrame(-1, -1.0f));
                    this.photoFilterView.getDoneTextView().setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            PhotoViewer.this.applyCurrentEditMode();
                            PhotoViewer.this.switchToEditMode(0);
                        }
                    });
                    this.photoFilterView.getCancelTextView().setOnClickListener(new OnClickListener() {

                        /* renamed from: org.telegram.ui.PhotoViewer$44$1 */
                        class C50631 implements DialogInterface.OnClickListener {
                            C50631() {
                            }

                            public void onClick(DialogInterface dialogInterface, int i) {
                                PhotoViewer.this.switchToEditMode(0);
                            }
                        }

                        public void onClick(View view) {
                            if (!PhotoViewer.this.photoFilterView.hasChanges()) {
                                PhotoViewer.this.switchToEditMode(0);
                            } else if (PhotoViewer.this.parentActivity != null) {
                                Builder builder = new Builder(PhotoViewer.this.parentActivity);
                                builder.setMessage(LocaleController.getString("DiscardChanges", R.string.DiscardChanges));
                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C50631());
                                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                                PhotoViewer.this.showAlertDialog(builder);
                            }
                        }
                    });
                    this.photoFilterView.getToolsView().setTranslationY((float) AndroidUtilities.dp(186.0f));
                }
                i2 = 0;
                if (savedFilterState != null) {
                    bitmap = BitmapFactory.decodeFile(str);
                } else {
                    bitmap = this.centerImage.getBitmap();
                    i2 = this.centerImage.getOrientation();
                }
                this.photoFilterView = new PhotoFilterView(this.parentActivity, bitmap, i2, savedFilterState);
                this.containerView.addView(this.photoFilterView, LayoutHelper.createFrame(-1, -1.0f));
                this.photoFilterView.getDoneTextView().setOnClickListener(/* anonymous class already generated */);
                this.photoFilterView.getCancelTextView().setOnClickListener(/* anonymous class already generated */);
                this.photoFilterView.getToolsView().setTranslationY((float) AndroidUtilities.dp(186.0f));
            }
            this.changeModeAnimation = new AnimatorSet();
            r0 = new ArrayList();
            r0.add(ObjectAnimator.ofFloat(this.pickerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(96.0f)}));
            r0.add(ObjectAnimator.ofFloat(this.actionBar, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) (-this.actionBar.getHeight())}));
            if (this.sendPhotoType == 0) {
                r0.add(ObjectAnimator.ofFloat(this.checkImageView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
                r0.add(ObjectAnimator.ofFloat(this.photosCounterView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
            }
            if (this.selectedPhotosListView.getVisibility() == 0) {
                r0.add(ObjectAnimator.ofFloat(this.selectedPhotosListView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
            }
            this.changeModeAnimation.playTogether(r0);
            this.changeModeAnimation.setDuration(200);
            this.changeModeAnimation.addListener(new AnimatorListenerAdapter() {

                /* renamed from: org.telegram.ui.PhotoViewer$45$1 */
                class C50641 extends AnimatorListenerAdapter {
                    C50641() {
                    }

                    public void onAnimationEnd(Animator animator) {
                        PhotoViewer.this.photoFilterView.init();
                        PhotoViewer.this.imageMoveAnimation = null;
                        PhotoViewer.this.currentEditMode = i;
                        PhotoViewer.this.animateToScale = 1.0f;
                        PhotoViewer.this.animateToX = BitmapDescriptorFactory.HUE_RED;
                        PhotoViewer.this.animateToY = BitmapDescriptorFactory.HUE_RED;
                        PhotoViewer.this.scale = 1.0f;
                        PhotoViewer.this.updateMinMax(PhotoViewer.this.scale);
                        PhotoViewer.this.containerView.invalidate();
                    }

                    public void onAnimationStart(Animator animator) {
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    PhotoViewer.this.changeModeAnimation = null;
                    PhotoViewer.this.pickerView.setVisibility(8);
                    PhotoViewer.this.actionBar.setVisibility(8);
                    PhotoViewer.this.selectedPhotosListView.setVisibility(8);
                    PhotoViewer.this.selectedPhotosListView.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    PhotoViewer.this.selectedPhotosListView.setTranslationY((float) (-AndroidUtilities.dp(10.0f)));
                    PhotoViewer.this.photosCounterView.setRotationX(BitmapDescriptorFactory.HUE_RED);
                    PhotoViewer.this.selectedPhotosListView.setEnabled(false);
                    PhotoViewer.this.isPhotosListViewVisible = false;
                    if (PhotoViewer.this.needCaptionLayout) {
                        PhotoViewer.this.captionTextView.setVisibility(4);
                    }
                    if (PhotoViewer.this.sendPhotoType == 0) {
                        PhotoViewer.this.checkImageView.setVisibility(8);
                        PhotoViewer.this.photosCounterView.setVisibility(8);
                    }
                    if (PhotoViewer.this.centerImage.getBitmap() != null) {
                        int bitmapWidth = PhotoViewer.this.centerImage.getBitmapWidth();
                        int bitmapHeight = PhotoViewer.this.centerImage.getBitmapHeight();
                        float access$2100 = ((float) PhotoViewer.this.getContainerViewWidth()) / ((float) bitmapWidth);
                        float access$2200 = ((float) PhotoViewer.this.getContainerViewHeight()) / ((float) bitmapHeight);
                        float access$11000 = ((float) PhotoViewer.this.getContainerViewWidth(2)) / ((float) bitmapWidth);
                        float access$11100 = ((float) PhotoViewer.this.getContainerViewHeight(2)) / ((float) bitmapHeight);
                        if (access$2100 <= access$2200) {
                            access$2200 = access$2100;
                        }
                        if (access$11000 <= access$11100) {
                            access$11100 = access$11000;
                        }
                        PhotoViewer.this.animateToScale = access$11100 / access$2200;
                        PhotoViewer.this.animateToX = BitmapDescriptorFactory.HUE_RED;
                        PhotoViewer.this.animateToY = (float) ((VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight / 2 : 0) + (-AndroidUtilities.dp(92.0f)));
                        PhotoViewer.this.animationStartTime = System.currentTimeMillis();
                        PhotoViewer.this.zoomAnimation = true;
                    }
                    PhotoViewer.this.imageMoveAnimation = new AnimatorSet();
                    AnimatorSet access$10200 = PhotoViewer.this.imageMoveAnimation;
                    Animator[] animatorArr = new Animator[2];
                    animatorArr[0] = ObjectAnimator.ofFloat(PhotoViewer.this, "animationValue", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                    animatorArr[1] = ObjectAnimator.ofFloat(PhotoViewer.this.photoFilterView.getToolsView(), "translationY", new float[]{(float) AndroidUtilities.dp(186.0f), BitmapDescriptorFactory.HUE_RED});
                    access$10200.playTogether(animatorArr);
                    PhotoViewer.this.imageMoveAnimation.setDuration(200);
                    PhotoViewer.this.imageMoveAnimation.addListener(new C50641());
                    PhotoViewer.this.imageMoveAnimation.start();
                }
            });
            this.changeModeAnimation.start();
        } else if (i == 3) {
            if (this.photoPaintView == null) {
                this.photoPaintView = new PhotoPaintView(this.parentActivity, this.centerImage.getBitmap(), this.centerImage.getOrientation());
                this.containerView.addView(this.photoPaintView, LayoutHelper.createFrame(-1, -1.0f));
                this.photoPaintView.getDoneTextView().setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        PhotoViewer.this.applyCurrentEditMode();
                        PhotoViewer.this.switchToEditMode(0);
                    }
                });
                this.photoPaintView.getCancelTextView().setOnClickListener(new OnClickListener() {

                    /* renamed from: org.telegram.ui.PhotoViewer$47$1 */
                    class C50651 implements Runnable {
                        C50651() {
                        }

                        public void run() {
                            PhotoViewer.this.switchToEditMode(0);
                        }
                    }

                    public void onClick(View view) {
                        PhotoViewer.this.photoPaintView.maybeShowDismissalAlert(PhotoViewer.this, PhotoViewer.this.parentActivity, new C50651());
                    }
                });
                this.photoPaintView.getColorPicker().setTranslationY((float) AndroidUtilities.dp(126.0f));
                this.photoPaintView.getToolsView().setTranslationY((float) AndroidUtilities.dp(126.0f));
            }
            this.changeModeAnimation = new AnimatorSet();
            r0 = new ArrayList();
            r0.add(ObjectAnimator.ofFloat(this.pickerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(96.0f)}));
            r0.add(ObjectAnimator.ofFloat(this.actionBar, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) (-this.actionBar.getHeight())}));
            if (this.needCaptionLayout) {
                r0.add(ObjectAnimator.ofFloat(this.captionTextView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(96.0f)}));
            }
            if (this.sendPhotoType == 0) {
                r0.add(ObjectAnimator.ofFloat(this.checkImageView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
                r0.add(ObjectAnimator.ofFloat(this.photosCounterView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
            }
            if (this.selectedPhotosListView.getVisibility() == 0) {
                r0.add(ObjectAnimator.ofFloat(this.selectedPhotosListView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
            }
            this.changeModeAnimation.playTogether(r0);
            this.changeModeAnimation.setDuration(200);
            this.changeModeAnimation.addListener(new AnimatorListenerAdapter() {

                /* renamed from: org.telegram.ui.PhotoViewer$48$1 */
                class C50661 extends AnimatorListenerAdapter {
                    C50661() {
                    }

                    public void onAnimationEnd(Animator animator) {
                        PhotoViewer.this.photoPaintView.init();
                        PhotoViewer.this.imageMoveAnimation = null;
                        PhotoViewer.this.currentEditMode = i;
                        PhotoViewer.this.animateToScale = 1.0f;
                        PhotoViewer.this.animateToX = BitmapDescriptorFactory.HUE_RED;
                        PhotoViewer.this.animateToY = BitmapDescriptorFactory.HUE_RED;
                        PhotoViewer.this.scale = 1.0f;
                        PhotoViewer.this.updateMinMax(PhotoViewer.this.scale);
                        PhotoViewer.this.containerView.invalidate();
                    }

                    public void onAnimationStart(Animator animator) {
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    PhotoViewer.this.changeModeAnimation = null;
                    PhotoViewer.this.pickerView.setVisibility(8);
                    PhotoViewer.this.selectedPhotosListView.setVisibility(8);
                    PhotoViewer.this.selectedPhotosListView.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    PhotoViewer.this.selectedPhotosListView.setTranslationY((float) (-AndroidUtilities.dp(10.0f)));
                    PhotoViewer.this.photosCounterView.setRotationX(BitmapDescriptorFactory.HUE_RED);
                    PhotoViewer.this.selectedPhotosListView.setEnabled(false);
                    PhotoViewer.this.isPhotosListViewVisible = false;
                    if (PhotoViewer.this.needCaptionLayout) {
                        PhotoViewer.this.captionTextView.setVisibility(4);
                    }
                    if (PhotoViewer.this.sendPhotoType == 0) {
                        PhotoViewer.this.checkImageView.setVisibility(8);
                        PhotoViewer.this.photosCounterView.setVisibility(8);
                    }
                    if (PhotoViewer.this.centerImage.getBitmap() != null) {
                        int bitmapWidth = PhotoViewer.this.centerImage.getBitmapWidth();
                        int bitmapHeight = PhotoViewer.this.centerImage.getBitmapHeight();
                        float access$2100 = ((float) PhotoViewer.this.getContainerViewWidth()) / ((float) bitmapWidth);
                        float access$2200 = ((float) PhotoViewer.this.getContainerViewHeight()) / ((float) bitmapHeight);
                        float access$11000 = ((float) PhotoViewer.this.getContainerViewWidth(3)) / ((float) bitmapWidth);
                        float access$11100 = ((float) PhotoViewer.this.getContainerViewHeight(3)) / ((float) bitmapHeight);
                        if (access$2100 <= access$2200) {
                            access$2200 = access$2100;
                        }
                        if (access$11000 <= access$11100) {
                            access$11100 = access$11000;
                        }
                        PhotoViewer.this.animateToScale = access$11100 / access$2200;
                        PhotoViewer.this.animateToX = BitmapDescriptorFactory.HUE_RED;
                        PhotoViewer.this.animateToY = (float) ((VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight / 2 : 0) + (-AndroidUtilities.dp(44.0f)));
                        PhotoViewer.this.animationStartTime = System.currentTimeMillis();
                        PhotoViewer.this.zoomAnimation = true;
                    }
                    PhotoViewer.this.imageMoveAnimation = new AnimatorSet();
                    AnimatorSet access$10200 = PhotoViewer.this.imageMoveAnimation;
                    r1 = new Animator[3];
                    r1[1] = ObjectAnimator.ofFloat(PhotoViewer.this.photoPaintView.getColorPicker(), "translationY", new float[]{(float) AndroidUtilities.dp(126.0f), BitmapDescriptorFactory.HUE_RED});
                    r1[2] = ObjectAnimator.ofFloat(PhotoViewer.this.photoPaintView.getToolsView(), "translationY", new float[]{(float) AndroidUtilities.dp(126.0f), BitmapDescriptorFactory.HUE_RED});
                    access$10200.playTogether(r1);
                    PhotoViewer.this.imageMoveAnimation.setDuration(200);
                    PhotoViewer.this.imageMoveAnimation.addListener(new C50661());
                    PhotoViewer.this.imageMoveAnimation.start();
                }
            });
            this.changeModeAnimation.start();
        }
    }

    public void updateMuteButton() {
        if (this.videoPlayer != null) {
            this.videoPlayer.setMute(this.muteVideo);
        }
        if (this.videoHasAudio) {
            this.muteItem.setEnabled(true);
            this.muteItem.setClickable(true);
            this.muteItem.setAlpha(1.0f);
            if (this.muteVideo) {
                this.actionBar.setSubtitle(null);
                this.muteItem.setImageResource(R.drawable.volume_off);
                this.muteItem.setColorFilter(new PorterDuffColorFilter(-12734994, Mode.MULTIPLY));
                if (this.compressItem.getTag() != null) {
                    this.compressItem.setClickable(false);
                    this.compressItem.setAlpha(0.5f);
                    this.compressItem.setEnabled(false);
                }
                this.videoTimelineView.setMaxProgressDiff(30000.0f / this.videoDuration);
                return;
            }
            this.muteItem.setColorFilter(null);
            this.actionBar.setSubtitle(this.currentSubtitle);
            this.muteItem.setImageResource(R.drawable.volume_on);
            if (this.compressItem.getTag() != null) {
                this.compressItem.setClickable(true);
                this.compressItem.setAlpha(1.0f);
                this.compressItem.setEnabled(true);
            }
            this.videoTimelineView.setMaxProgressDiff(1.0f);
            return;
        }
        this.muteItem.setEnabled(false);
        this.muteItem.setClickable(false);
        this.muteItem.setAlpha(0.5f);
    }
}
