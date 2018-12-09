package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.support.v4.view.aa;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.MetricAffectingSpan;
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
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.FileDownloadProgressListener;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.support.widget.GridLayoutManager;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contacts_resolveUsername;
import org.telegram.tgnet.TLRPC$TL_contacts_resolvedPeer;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser;
import org.telegram.tgnet.TLRPC$TL_messageEntityUrl;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messages_getWebPage;
import org.telegram.tgnet.TLRPC$TL_pageBlockAnchor;
import org.telegram.tgnet.TLRPC$TL_pageBlockAudio;
import org.telegram.tgnet.TLRPC$TL_pageBlockAuthorDate;
import org.telegram.tgnet.TLRPC$TL_pageBlockBlockquote;
import org.telegram.tgnet.TLRPC$TL_pageBlockChannel;
import org.telegram.tgnet.TLRPC$TL_pageBlockCollage;
import org.telegram.tgnet.TLRPC$TL_pageBlockCover;
import org.telegram.tgnet.TLRPC$TL_pageBlockDivider;
import org.telegram.tgnet.TLRPC$TL_pageBlockEmbed;
import org.telegram.tgnet.TLRPC$TL_pageBlockEmbedPost;
import org.telegram.tgnet.TLRPC$TL_pageBlockFooter;
import org.telegram.tgnet.TLRPC$TL_pageBlockHeader;
import org.telegram.tgnet.TLRPC$TL_pageBlockList;
import org.telegram.tgnet.TLRPC$TL_pageBlockParagraph;
import org.telegram.tgnet.TLRPC$TL_pageBlockPhoto;
import org.telegram.tgnet.TLRPC$TL_pageBlockPreformatted;
import org.telegram.tgnet.TLRPC$TL_pageBlockPullquote;
import org.telegram.tgnet.TLRPC$TL_pageBlockSlideshow;
import org.telegram.tgnet.TLRPC$TL_pageBlockSubheader;
import org.telegram.tgnet.TLRPC$TL_pageBlockSubtitle;
import org.telegram.tgnet.TLRPC$TL_pageBlockTitle;
import org.telegram.tgnet.TLRPC$TL_pageBlockUnsupported;
import org.telegram.tgnet.TLRPC$TL_pageBlockVideo;
import org.telegram.tgnet.TLRPC$TL_pageFull;
import org.telegram.tgnet.TLRPC$TL_pagePart;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_textBold;
import org.telegram.tgnet.TLRPC$TL_textConcat;
import org.telegram.tgnet.TLRPC$TL_textEmail;
import org.telegram.tgnet.TLRPC$TL_textEmpty;
import org.telegram.tgnet.TLRPC$TL_textFixed;
import org.telegram.tgnet.TLRPC$TL_textItalic;
import org.telegram.tgnet.TLRPC$TL_textPlain;
import org.telegram.tgnet.TLRPC$TL_textStrike;
import org.telegram.tgnet.TLRPC$TL_textUnderline;
import org.telegram.tgnet.TLRPC$TL_textUrl;
import org.telegram.tgnet.TLRPC$TL_updateNewChannelMessage;
import org.telegram.tgnet.TLRPC$TL_user;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.Peer;
import org.telegram.tgnet.TLRPC.Photo;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.RichText;
import org.telegram.tgnet.TLRPC.TL_channels_joinChannel;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import org.telegram.ui.ActionBar.ActionBarPopupWindow.ActionBarPopupWindowLayout;
import org.telegram.ui.ActionBar.ActionBarPopupWindow.OnDispatchKeyEventListener;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet.Builder;
import org.telegram.ui.ActionBar.DrawerLayoutContainer;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.AnimatedFileDrawable;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.ClippingImageView;
import org.telegram.ui.Components.ContextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.LinkPath;
import org.telegram.ui.Components.RadialProgress;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.Scroller;
import org.telegram.ui.Components.SeekBar;
import org.telegram.ui.Components.SeekBar.SeekBarDelegate;
import org.telegram.ui.Components.ShareAlert;
import org.telegram.ui.Components.TextPaintSpan;
import org.telegram.ui.Components.TextPaintUrlSpan;
import org.telegram.ui.Components.TypefaceSpan;
import org.telegram.ui.Components.VideoPlayer;
import org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate;
import org.telegram.ui.Components.WebPlayerView;
import org.telegram.ui.Components.WebPlayerView.WebPlayerViewDelegate;

public class ArticleViewer implements OnDoubleTapListener, OnGestureListener, NotificationCenterDelegate {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile ArticleViewer Instance = null;
    private static final int TEXT_FLAG_ITALIC = 2;
    private static final int TEXT_FLAG_MEDIUM = 1;
    private static final int TEXT_FLAG_MONO = 4;
    private static final int TEXT_FLAG_REGULAR = 0;
    private static final int TEXT_FLAG_STRIKE = 32;
    private static final int TEXT_FLAG_UNDERLINE = 16;
    private static final int TEXT_FLAG_URL = 8;
    private static TextPaint audioTimePaint = new TextPaint(1);
    private static HashMap<Integer, TextPaint> authorTextPaints = new HashMap();
    private static HashMap<Integer, TextPaint> captionTextPaints = new HashMap();
    private static TextPaint channelNamePaint = null;
    private static Paint colorPaint = null;
    private static DecelerateInterpolator decelerateInterpolator = null;
    private static Paint dividerPaint = null;
    private static Paint dotsPaint = null;
    private static TextPaint embedPostAuthorPaint = null;
    private static HashMap<Integer, TextPaint> embedPostCaptionTextPaints = new HashMap();
    private static TextPaint embedPostDatePaint = null;
    private static HashMap<Integer, TextPaint> embedPostTextPaints = new HashMap();
    private static HashMap<Integer, TextPaint> embedTextPaints = new HashMap();
    private static TextPaint errorTextPaint = null;
    private static HashMap<Integer, TextPaint> footerTextPaints = new HashMap();
    private static final int gallery_menu_openin = 3;
    private static final int gallery_menu_save = 1;
    private static final int gallery_menu_share = 2;
    private static HashMap<Integer, TextPaint> headerTextPaints = new HashMap();
    private static HashMap<Integer, TextPaint> listTextPaints = new HashMap();
    private static TextPaint listTextPointerPaint;
    private static HashMap<Integer, TextPaint> paragraphTextPaints = new HashMap();
    private static Paint preformattedBackgroundPaint;
    private static HashMap<Integer, TextPaint> preformattedTextPaints = new HashMap();
    private static Drawable[] progressDrawables;
    private static Paint progressPaint;
    private static Paint quoteLinePaint;
    private static HashMap<Integer, TextPaint> quoteTextPaints = new HashMap();
    private static Paint selectorPaint;
    private static HashMap<Integer, TextPaint> slideshowTextPaints = new HashMap();
    private static HashMap<Integer, TextPaint> subheaderTextPaints = new HashMap();
    private static HashMap<Integer, TextPaint> subquoteTextPaints = new HashMap();
    private static HashMap<Integer, TextPaint> subtitleTextPaints = new HashMap();
    private static HashMap<Integer, TextPaint> titleTextPaints = new HashMap();
    private static Paint urlPaint;
    private static HashMap<Integer, TextPaint> videoTextPaints = new HashMap();
    private ActionBar actionBar;
    private WebpageAdapter adapter;
    private HashMap<String, Integer> anchors = new HashMap();
    private float animateToScale;
    private float animateToX;
    private float animateToY;
    private ClippingImageView animatingImageView;
    private Runnable animationEndRunnable;
    private int animationInProgress;
    private long animationStartTime;
    private float animationValue;
    private float[][] animationValues = ((float[][]) Array.newInstance(Float.TYPE, new int[]{2, 8}));
    private AspectRatioFrameLayout aspectRatioFrameLayout;
    private boolean attachedToWindow;
    private HashMap<TLRPC$TL_pageBlockAudio, MessageObject> audioBlocks = new HashMap();
    private ArrayList<MessageObject> audioMessages = new ArrayList();
    private ImageView backButton;
    private BackDrawable backDrawable;
    private Paint backgroundPaint;
    private View barBackground;
    private Paint blackPaint = new Paint();
    private ArrayList<PageBlock> blocks = new ArrayList();
    private FrameLayout bottomLayout;
    private boolean canDragDown = true;
    private boolean canZoom = true;
    private TextView captionTextView;
    private TextView captionTextViewNew;
    private TextView captionTextViewOld;
    private ImageReceiver centerImage = new ImageReceiver();
    private boolean changingPage;
    private TLRPC$TL_pageBlockChannel channelBlock;
    private boolean checkingForLongPress = false;
    private boolean collapsed;
    private ColorCell[] colorCells = new ColorCell[3];
    private FrameLayout containerView;
    private int[] coords = new int[2];
    private ArrayList<BlockEmbedCell> createdWebViews = new ArrayList();
    private AnimatorSet currentActionBarAnimation;
    private AnimatedFileDrawable currentAnimation;
    private String[] currentFileNames = new String[3];
    private int currentHeaderHeight;
    private int currentIndex;
    private PageBlock currentMedia;
    private TLRPC$WebPage currentPage;
    private PlaceProviderObject currentPlaceObject;
    private WebPlayerView currentPlayingVideo;
    private int currentRotation;
    private Bitmap currentThumb;
    private View customView;
    private CustomViewCallback customViewCallback;
    private boolean disableShowCheck;
    private boolean discardTap;
    private boolean dontResetZoomOnFirstLayout;
    private boolean doubleTap;
    private float dragY;
    private boolean draggingDown;
    private boolean drawBlockSelection;
    private FontCell[] fontCells = new FontCell[2];
    private final int fontSizeCount = 5;
    private AspectRatioFrameLayout fullscreenAspectRatioView;
    private TextureView fullscreenTextureView;
    private FrameLayout fullscreenVideoContainer;
    private WebPlayerView fullscreenedVideo;
    private GestureDetector gestureDetector;
    private Paint headerPaint = new Paint();
    private Paint headerProgressPaint = new Paint();
    private FrameLayout headerView;
    private PlaceProviderObject hideAfterAnimation;
    private AnimatorSet imageMoveAnimation;
    private ArrayList<PageBlock> imagesArr = new ArrayList();
    private DecelerateInterpolator interpolator = new DecelerateInterpolator(1.5f);
    private boolean invalidCoords;
    private boolean isActionBarVisible = true;
    private boolean isPhotoVisible;
    private boolean isPlaying;
    private int isRtl = -1;
    private boolean isVisible;
    private Object lastInsets;
    private Drawable layerShadowDrawable;
    private LinearLayoutManager layoutManager;
    private ImageReceiver leftImage = new ImageReceiver();
    private RecyclerListView listView;
    private Chat loadedChannel;
    private boolean loadingChannel;
    private float maxX;
    private float maxY;
    private ActionBarMenuItem menuItem;
    private float minX;
    private float minY;
    private float moveStartX;
    private float moveStartY;
    private boolean moving;
    private boolean nightModeEnabled;
    private FrameLayout nightModeHintView;
    private ImageView nightModeImageView;
    private int openUrlReqId;
    private ArrayList<TLRPC$WebPage> pagesStack = new ArrayList();
    private Activity parentActivity;
    private BaseFragment parentFragment;
    private CheckForLongPress pendingCheckForLongPress = null;
    private CheckForTap pendingCheckForTap = null;
    private Runnable photoAnimationEndRunnable;
    private int photoAnimationInProgress;
    private PhotoBackgroundDrawable photoBackgroundDrawable = new PhotoBackgroundDrawable(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
    private ArrayList<PageBlock> photoBlocks = new ArrayList();
    private View photoContainerBackground;
    private FrameLayoutDrawer photoContainerView;
    private long photoTransitionAnimationStartTime;
    private float pinchCenterX;
    private float pinchCenterY;
    private float pinchStartDistance;
    private float pinchStartScale = 1.0f;
    private float pinchStartX;
    private float pinchStartY;
    private ActionBarPopupWindowLayout popupLayout;
    private Rect popupRect;
    private ActionBarPopupWindow popupWindow;
    private int pressCount = 0;
    private int pressedLayoutY;
    private TextPaintUrlSpan pressedLink;
    private StaticLayout pressedLinkOwnerLayout;
    private View pressedLinkOwnerView;
    private int previewsReqId;
    private ContextProgressView progressView;
    private AnimatorSet progressViewAnimation;
    private RadialProgressView[] radialProgressViews = new RadialProgressView[3];
    private ImageReceiver rightImage = new ImageReceiver();
    private float scale = 1.0f;
    private Paint scrimPaint;
    private Scroller scroller;
    private int selectedColor = 0;
    private int selectedFont = 0;
    private int selectedFontSize = 2;
    private ActionBarMenuItem settingsButton;
    private ImageView shareButton;
    private FrameLayout shareContainer;
    private PlaceProviderObject showAfterAnimation;
    private Drawable slideDotBigDrawable;
    private Drawable slideDotDrawable;
    private int switchImageAfterAnimation;
    private boolean textureUploaded;
    private long transitionAnimationStartTime;
    private float translationX;
    private float translationY;
    private Runnable updateProgressRunnable = new Runnable() {
        public void run() {
            if (!(ArticleViewer.this.videoPlayer == null || ArticleViewer.this.videoPlayerSeekbar == null || ArticleViewer.this.videoPlayerSeekbar.isDragging())) {
                ArticleViewer.this.videoPlayerSeekbar.setProgress(((float) ArticleViewer.this.videoPlayer.getCurrentPosition()) / ((float) ArticleViewer.this.videoPlayer.getDuration()));
                ArticleViewer.this.videoPlayerControlFrameLayout.invalidate();
                ArticleViewer.this.updateVideoPlayerTime();
            }
            if (ArticleViewer.this.isPlaying) {
                AndroidUtilities.runOnUIThread(ArticleViewer.this.updateProgressRunnable, 100);
            }
        }
    };
    private LinkPath urlPath = new LinkPath();
    private VelocityTracker velocityTracker;
    private float videoCrossfadeAlpha;
    private long videoCrossfadeAlphaLastTime;
    private boolean videoCrossfadeStarted;
    private ImageView videoPlayButton;
    private VideoPlayer videoPlayer;
    private FrameLayout videoPlayerControlFrameLayout;
    private SeekBar videoPlayerSeekbar;
    private TextView videoPlayerTime;
    private TextureView videoTextureView;
    private Dialog visibleDialog;
    private boolean wasLayout;
    private LayoutParams windowLayoutParams;
    private WindowView windowView;
    private boolean zoomAnimation;
    private boolean zooming;

    /* renamed from: org.telegram.ui.ArticleViewer$1 */
    class C39071 implements OnTouchListener {
        C39071() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getActionMasked() == 0 && ArticleViewer.this.popupWindow != null && ArticleViewer.this.popupWindow.isShowing()) {
                view.getHitRect(ArticleViewer.this.popupRect);
                if (!ArticleViewer.this.popupRect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    ArticleViewer.this.popupWindow.dismiss();
                }
            }
            return false;
        }
    }

    /* renamed from: org.telegram.ui.ArticleViewer$2 */
    class C39112 implements OnDispatchKeyEventListener {
        C39112() {
        }

        public void onDispatchKeyEvent(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && ArticleViewer.this.popupWindow != null && ArticleViewer.this.popupWindow.isShowing()) {
                ArticleViewer.this.popupWindow.dismiss();
            }
        }
    }

    /* renamed from: org.telegram.ui.ArticleViewer$3 */
    class C39163 implements OnClickListener {
        C39163() {
        }

        public void onClick(View view) {
            if (ArticleViewer.this.pressedLinkOwnerLayout != null) {
                AndroidUtilities.addToClipboard(ArticleViewer.this.pressedLinkOwnerLayout.getText());
                Toast.makeText(ArticleViewer.this.parentActivity, LocaleController.getString("TextCopied", R.string.TextCopied), 0).show();
                if (ArticleViewer.this.popupWindow != null && ArticleViewer.this.popupWindow.isShowing()) {
                    ArticleViewer.this.popupWindow.dismiss(true);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ArticleViewer$4 */
    class C39194 implements OnDismissListener {
        C39194() {
        }

        public void onDismiss() {
            if (ArticleViewer.this.pressedLinkOwnerView != null) {
                ArticleViewer.this.pressedLinkOwnerLayout = null;
                ArticleViewer.this.pressedLinkOwnerView.invalidate();
                ArticleViewer.this.pressedLinkOwnerView = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.ArticleViewer$6 */
    class C39226 implements OnApplyWindowInsetsListener {
        C39226() {
        }

        @SuppressLint({"NewApi"})
        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            WindowInsets windowInsets2 = (WindowInsets) ArticleViewer.this.lastInsets;
            ArticleViewer.this.lastInsets = windowInsets;
            if (windowInsets2 == null || !windowInsets2.toString().equals(windowInsets.toString())) {
                ArticleViewer.this.windowView.requestLayout();
            }
            return windowInsets.consumeSystemWindowInsets();
        }
    }

    private class FrameLayoutDrawer extends FrameLayout {
        public FrameLayoutDrawer(Context context) {
            super(context);
        }

        protected boolean drawChild(Canvas canvas, View view, long j) {
            return view != ArticleViewer.this.aspectRatioFrameLayout && super.drawChild(canvas, view, j);
        }

        protected void onDraw(Canvas canvas) {
            ArticleViewer.this.drawContent(canvas);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            ArticleViewer.this.processTouchEvent(motionEvent);
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ArticleViewer$9 */
    class C39259 implements OnItemLongClickListener {
        C39259() {
        }

        public boolean onItemClick(View view, int i) {
            return false;
        }
    }

    private class BlockAudioCell extends View implements FileDownloadProgressListener {
        private int TAG;
        private int buttonPressed;
        private int buttonState;
        private int buttonX;
        private int buttonY;
        private TLRPC$TL_pageBlockAudio currentBlock;
        private Document currentDocument;
        private MessageObject currentMessageObject;
        private StaticLayout durationLayout;
        private boolean isFirst;
        private boolean isLast;
        private int lastCreatedWidth;
        private String lastTimeString;
        private RadialProgress radialProgress = new RadialProgress(this);
        private SeekBar seekBar;
        private int seekBarX;
        private int seekBarY;
        private StaticLayout textLayout;
        private int textX;
        private int textY = AndroidUtilities.dp(54.0f);
        private StaticLayout titleLayout;

        public BlockAudioCell(Context context) {
            super(context);
            this.radialProgress.setAlphaForPrevious(true);
            this.radialProgress.setDiff(AndroidUtilities.dp(BitmapDescriptorFactory.HUE_RED));
            this.radialProgress.setStrikeWidth(AndroidUtilities.dp(2.0f));
            this.TAG = MediaController.getInstance().generateObserverTag();
            this.seekBar = new SeekBar(context);
            this.seekBar.setDelegate(new SeekBarDelegate(ArticleViewer.this) {
                public void onSeekBarDrag(float f) {
                    if (BlockAudioCell.this.currentMessageObject != null) {
                        BlockAudioCell.this.currentMessageObject.audioProgress = f;
                        MediaController.getInstance().seekToProgress(BlockAudioCell.this.currentMessageObject, f);
                    }
                }
            });
        }

        private void didPressedButton(boolean z) {
            if (this.buttonState == 0) {
                if (MediaController.getInstance().setPlaylist(ArticleViewer.this.audioMessages, this.currentMessageObject, false)) {
                    this.buttonState = 1;
                    this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                    invalidate();
                }
            } else if (this.buttonState == 1) {
                if (MediaController.getInstance().pauseMessage(this.currentMessageObject)) {
                    this.buttonState = 0;
                    this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                    invalidate();
                }
            } else if (this.buttonState == 2) {
                this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, false);
                FileLoader.getInstance().loadFile(this.currentDocument, true, 1);
                this.buttonState = 3;
                this.radialProgress.setBackground(getDrawableForCurrentState(), true, false);
                invalidate();
            } else if (this.buttonState == 3) {
                FileLoader.getInstance().cancelLoadFile(this.currentDocument);
                this.buttonState = 2;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                invalidate();
            }
        }

        private Drawable getDrawableForCurrentState() {
            return Theme.chat_ivStatesDrawable[this.buttonState][this.buttonPressed != 0 ? 1 : 0];
        }

        public MessageObject getMessageObject() {
            return this.currentMessageObject;
        }

        public int getObserverTag() {
            return this.TAG;
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                this.radialProgress.draw(canvas);
                canvas.save();
                canvas.translate((float) this.seekBarX, (float) this.seekBarY);
                this.seekBar.draw(canvas);
                canvas.restore();
                if (this.durationLayout != null) {
                    canvas.save();
                    canvas.translate((float) (this.buttonX + AndroidUtilities.dp(54.0f)), (float) (this.seekBarY + AndroidUtilities.dp(6.0f)));
                    this.durationLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.titleLayout != null) {
                    canvas.save();
                    canvas.translate((float) (this.buttonX + AndroidUtilities.dp(54.0f)), (float) (this.seekBarY - AndroidUtilities.dp(16.0f)));
                    this.titleLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.currentBlock.level > 0) {
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
        }

        public void onFailedDownload(String str) {
            updateButtonState(true);
        }

        @SuppressLint({"DrawAllocation"})
        protected void onMeasure(int i, int i2) {
            int dp;
            int size = MeasureSpec.getSize(i);
            int dp2 = AndroidUtilities.dp(54.0f);
            if (this.currentBlock != null) {
                if (this.currentBlock.level > 0) {
                    this.textX = AndroidUtilities.dp((float) (this.currentBlock.level * 14)) + AndroidUtilities.dp(18.0f);
                } else {
                    this.textX = AndroidUtilities.dp(18.0f);
                }
                int dp3 = (size - this.textX) - AndroidUtilities.dp(18.0f);
                int dp4 = AndroidUtilities.dp(40.0f);
                this.buttonX = AndroidUtilities.dp(16.0f);
                this.buttonY = AndroidUtilities.dp(7.0f);
                this.currentBlock.caption = new TLRPC$TL_textPlain();
                this.radialProgress.setProgressRect(this.buttonX, this.buttonY, this.buttonX + dp4, this.buttonY + dp4);
                if (this.lastCreatedWidth != size) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, dp3, this.currentBlock);
                    if (this.textLayout != null) {
                        dp2 += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                dp = (this.isFirst || this.currentBlock.level > 0) ? dp2 : dp2 + AndroidUtilities.dp(8.0f);
                CharSequence musicAuthor = this.currentMessageObject.getMusicAuthor(false);
                CharSequence musicTitle = this.currentMessageObject.getMusicTitle(false);
                this.seekBarX = (this.buttonX + AndroidUtilities.dp(50.0f)) + dp4;
                int dp5 = (size - this.seekBarX) - AndroidUtilities.dp(18.0f);
                if (TextUtils.isEmpty(musicTitle) && TextUtils.isEmpty(musicAuthor)) {
                    this.titleLayout = null;
                    this.seekBarY = this.buttonY + ((dp4 - AndroidUtilities.dp(30.0f)) / 2);
                } else {
                    CharSequence spannableStringBuilder = (TextUtils.isEmpty(musicTitle) || TextUtils.isEmpty(musicAuthor)) ? !TextUtils.isEmpty(musicTitle) ? new SpannableStringBuilder(musicTitle) : new SpannableStringBuilder(musicAuthor) : new SpannableStringBuilder(String.format("%s - %s", new Object[]{musicAuthor, musicTitle}));
                    if (!TextUtils.isEmpty(musicAuthor)) {
                        spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), 0, musicAuthor.length(), 18);
                    }
                    this.titleLayout = new StaticLayout(TextUtils.ellipsize(spannableStringBuilder, Theme.chat_audioTitlePaint, (float) dp5, TruncateAt.END), ArticleViewer.audioTimePaint, dp5, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    this.seekBarY = (this.buttonY + ((dp4 - AndroidUtilities.dp(30.0f)) / 2)) + AndroidUtilities.dp(11.0f);
                }
                this.seekBar.setSize(dp5, AndroidUtilities.dp(30.0f));
            } else {
                dp = 1;
            }
            setMeasuredDimension(size, dp);
            updatePlayingMessageProgress();
        }

        public void onProgressDownload(String str, float f) {
            this.radialProgress.setProgress(f, true);
            if (this.buttonState != 3) {
                updateButtonState(false);
            }
        }

        public void onProgressUpload(String str, float f, boolean z) {
        }

        public void onSuccessDownload(String str) {
            this.radialProgress.setProgress(1.0f, true);
            updateButtonState(true);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (this.seekBar.onTouch(motionEvent.getAction(), motionEvent.getX() - ((float) this.seekBarX), motionEvent.getY() - ((float) this.seekBarY))) {
                if (motionEvent.getAction() == 0) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                invalidate();
                return true;
            }
            if (motionEvent.getAction() == 0) {
                if ((this.buttonState != -1 && x >= ((float) this.buttonX) && x <= ((float) (this.buttonX + AndroidUtilities.dp(48.0f))) && y >= ((float) this.buttonY) && y <= ((float) (this.buttonY + AndroidUtilities.dp(48.0f)))) || this.buttonState == 0) {
                    this.buttonPressed = 1;
                    invalidate();
                }
            } else if (motionEvent.getAction() == 1) {
                if (this.buttonPressed == 1) {
                    this.buttonPressed = 0;
                    playSoundEffect(0);
                    didPressedButton(false);
                    this.radialProgress.swapBackground(getDrawableForCurrentState());
                    invalidate();
                }
            } else if (motionEvent.getAction() == 3) {
                this.buttonPressed = 0;
            }
            boolean z = this.buttonPressed != 0 || ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
            return z;
        }

        public void setBlock(TLRPC$TL_pageBlockAudio tLRPC$TL_pageBlockAudio, boolean z, boolean z2) {
            this.currentBlock = tLRPC$TL_pageBlockAudio;
            this.currentMessageObject = (MessageObject) ArticleViewer.this.audioBlocks.get(this.currentBlock);
            this.currentDocument = this.currentMessageObject.getDocument();
            this.lastCreatedWidth = 0;
            this.isFirst = z;
            this.isLast = z2;
            this.radialProgress.setProgressColor(ArticleViewer.this.getTextColor());
            this.seekBar.setColors(ArticleViewer.this.getTextColor() & 1073741823, ArticleViewer.this.getTextColor(), ArticleViewer.this.getTextColor());
            updateButtonState(false);
            requestLayout();
        }

        public void updateButtonState(boolean z) {
            String attachFileName = FileLoader.getAttachFileName(this.currentDocument);
            boolean exists = FileLoader.getPathToAttach(this.currentDocument, true).exists();
            if (TextUtils.isEmpty(attachFileName)) {
                this.radialProgress.setBackground(null, false, false);
                return;
            }
            if (exists) {
                MediaController.getInstance().removeLoadingFileObserver(this);
                boolean isPlayingMessage = MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
                if (!isPlayingMessage || (isPlayingMessage && MediaController.getInstance().isMessagePaused())) {
                    this.buttonState = 0;
                } else {
                    this.buttonState = 1;
                }
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
            } else {
                MediaController.getInstance().addLoadingFileObserver(attachFileName, null, this);
                if (FileLoader.getInstance().isLoadingFile(attachFileName)) {
                    this.buttonState = 3;
                    Float fileProgress = ImageLoader.getInstance().getFileProgress(attachFileName);
                    if (fileProgress != null) {
                        this.radialProgress.setProgress(fileProgress.floatValue(), z);
                    } else {
                        this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, z);
                    }
                    this.radialProgress.setBackground(getDrawableForCurrentState(), true, z);
                } else {
                    this.buttonState = 2;
                    this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, z);
                    this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
                }
            }
            updatePlayingMessageProgress();
        }

        public void updatePlayingMessageProgress() {
            if (this.currentDocument != null && this.currentMessageObject != null) {
                int i;
                if (!this.seekBar.isDragging()) {
                    this.seekBar.setProgress(this.currentMessageObject.audioProgress);
                }
                if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                    i = this.currentMessageObject.audioProgressSec;
                } else {
                    for (int i2 = 0; i2 < this.currentDocument.attributes.size(); i2++) {
                        DocumentAttribute documentAttribute = (DocumentAttribute) this.currentDocument.attributes.get(i2);
                        if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                            i = documentAttribute.duration;
                            break;
                        }
                    }
                    i = 0;
                }
                CharSequence format = String.format("%d:%02d", new Object[]{Integer.valueOf(i / 60), Integer.valueOf(i % 60)});
                if (this.lastTimeString == null || !(this.lastTimeString == null || this.lastTimeString.equals(format))) {
                    this.lastTimeString = format;
                    ArticleViewer.audioTimePaint.setTextSize((float) AndroidUtilities.dp(16.0f));
                    this.durationLayout = new StaticLayout(format, ArticleViewer.audioTimePaint, (int) Math.ceil((double) ArticleViewer.audioTimePaint.measureText(format)), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                }
                ArticleViewer.audioTimePaint.setColor(ArticleViewer.this.getTextColor());
                invalidate();
            }
        }
    }

    private class BlockAuthorDateCell extends View {
        private TLRPC$TL_pageBlockAuthorDate currentBlock;
        private int lastCreatedWidth;
        private StaticLayout textLayout;
        private int textX;
        private int textY = AndroidUtilities.dp(8.0f);

        public BlockAuthorDateCell(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null && this.textLayout != null) {
                canvas.save();
                canvas.translate((float) this.textX, (float) this.textY);
                ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                this.textLayout.draw(canvas);
                canvas.restore();
            }
        }

        protected void onMeasure(int i, int i2) {
            Throwable th;
            int dp;
            int size = MeasureSpec.getSize(i);
            if (this.currentBlock != null) {
                if (this.lastCreatedWidth != size) {
                    Spannable spannable;
                    MetricAffectingSpan[] metricAffectingSpanArr;
                    CharSequence formatString;
                    CharSequence charSequence;
                    CharSequence access$12700 = ArticleViewer.this.getText(this.currentBlock.author, this.currentBlock.author, this.currentBlock);
                    if (access$12700 instanceof Spannable) {
                        spannable = (Spannable) access$12700;
                        metricAffectingSpanArr = (MetricAffectingSpan[]) spannable.getSpans(0, access$12700.length(), MetricAffectingSpan.class);
                    } else {
                        spannable = null;
                        metricAffectingSpanArr = null;
                    }
                    if (this.currentBlock.published_date != 0 && !TextUtils.isEmpty(access$12700)) {
                        formatString = LocaleController.formatString("ArticleDateByAuthor", R.string.ArticleDateByAuthor, new Object[]{LocaleController.getInstance().chatFullDate.format(((long) this.currentBlock.published_date) * 1000), access$12700});
                    } else if (TextUtils.isEmpty(access$12700)) {
                        formatString = LocaleController.getInstance().chatFullDate.format(((long) this.currentBlock.published_date) * 1000);
                    } else {
                        formatString = LocaleController.formatString("ArticleByAuthor", R.string.ArticleByAuthor, new Object[]{access$12700});
                    }
                    if (metricAffectingSpanArr != null) {
                        try {
                            if (metricAffectingSpanArr.length > 0) {
                                int indexOf = TextUtils.indexOf(formatString, access$12700);
                                if (indexOf != -1) {
                                    Object obj;
                                    Spannable newSpannable = Factory.getInstance().newSpannable(formatString);
                                    int i3 = 0;
                                    while (i3 < metricAffectingSpanArr.length) {
                                        try {
                                            newSpannable.setSpan(metricAffectingSpanArr[i3], spannable.getSpanStart(metricAffectingSpanArr[i3]) + indexOf, spannable.getSpanEnd(metricAffectingSpanArr[i3]) + indexOf, 33);
                                            i3++;
                                        } catch (Throwable e) {
                                            Throwable th2 = e;
                                            obj = newSpannable;
                                            th = th2;
                                        }
                                    }
                                    obj = newSpannable;
                                    this.textLayout = ArticleViewer.this.createLayoutForText(charSequence, null, size - AndroidUtilities.dp(36.0f), this.currentBlock);
                                    if (this.textLayout != null) {
                                        dp = (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight()) + 0;
                                        if (ArticleViewer.this.isRtl == 1) {
                                            this.textX = (int) Math.floor((double) ((((float) size) - this.textLayout.getLineWidth(0)) - ((float) AndroidUtilities.dp(16.0f))));
                                        } else {
                                            this.textX = AndroidUtilities.dp(18.0f);
                                        }
                                    }
                                }
                            }
                        } catch (Throwable e2) {
                            th = e2;
                            charSequence = formatString;
                            FileLog.e(th);
                            this.textLayout = ArticleViewer.this.createLayoutForText(charSequence, null, size - AndroidUtilities.dp(36.0f), this.currentBlock);
                            if (this.textLayout != null) {
                                dp = (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight()) + 0;
                                if (ArticleViewer.this.isRtl == 1) {
                                    this.textX = (int) Math.floor((double) ((((float) size) - this.textLayout.getLineWidth(0)) - ((float) AndroidUtilities.dp(16.0f))));
                                } else {
                                    this.textX = AndroidUtilities.dp(18.0f);
                                }
                                setMeasuredDimension(size, dp);
                            }
                            dp = 0;
                            setMeasuredDimension(size, dp);
                        }
                    }
                    charSequence = formatString;
                    this.textLayout = ArticleViewer.this.createLayoutForText(charSequence, null, size - AndroidUtilities.dp(36.0f), this.currentBlock);
                    if (this.textLayout != null) {
                        dp = (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight()) + 0;
                        if (ArticleViewer.this.isRtl == 1) {
                            this.textX = AndroidUtilities.dp(18.0f);
                        } else {
                            this.textX = (int) Math.floor((double) ((((float) size) - this.textLayout.getLineWidth(0)) - ((float) AndroidUtilities.dp(16.0f))));
                        }
                    }
                }
                dp = 0;
            } else {
                dp = 1;
            }
            setMeasuredDimension(size, dp);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockAuthorDate tLRPC$TL_pageBlockAuthorDate) {
            this.currentBlock = tLRPC$TL_pageBlockAuthorDate;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockBlockquoteCell extends View {
        private TLRPC$TL_pageBlockBlockquote currentBlock;
        private boolean hasRtl;
        private int lastCreatedWidth;
        private StaticLayout textLayout;
        private StaticLayout textLayout2;
        private int textX;
        private int textY = AndroidUtilities.dp(8.0f);
        private int textY2;

        public BlockBlockquoteCell(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.textLayout2 != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY2);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout2);
                    this.textLayout2.draw(canvas);
                    canvas.restore();
                }
                if (this.hasRtl) {
                    int measuredWidth = getMeasuredWidth() - AndroidUtilities.dp(20.0f);
                    canvas.drawRect((float) measuredWidth, (float) AndroidUtilities.dp(6.0f), (float) (measuredWidth + AndroidUtilities.dp(2.0f)), (float) (getMeasuredHeight() - AndroidUtilities.dp(6.0f)), ArticleViewer.quoteLinePaint);
                } else {
                    canvas.drawRect((float) AndroidUtilities.dp((float) ((this.currentBlock.level * 14) + 18)), (float) AndroidUtilities.dp(6.0f), (float) AndroidUtilities.dp((float) ((this.currentBlock.level * 14) + 20)), (float) (getMeasuredHeight() - AndroidUtilities.dp(6.0f)), ArticleViewer.quoteLinePaint);
                }
                if (this.currentBlock.level > 0) {
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
        }

        protected void onMeasure(int i, int i2) {
            int i3;
            int i4 = 0;
            int size = MeasureSpec.getSize(i);
            if (this.currentBlock == null) {
                i3 = 1;
            } else if (this.lastCreatedWidth != size) {
                int dp;
                i3 = size - AndroidUtilities.dp(50.0f);
                if (this.currentBlock.level > 0) {
                    i3 -= AndroidUtilities.dp((float) (this.currentBlock.level * 14));
                }
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, i3, this.currentBlock);
                this.hasRtl = false;
                if (this.textLayout != null) {
                    dp = (AndroidUtilities.dp(8.0f) + this.textLayout.getHeight()) + 0;
                    int lineCount = this.textLayout.getLineCount();
                    while (i4 < lineCount) {
                        if (this.textLayout.getLineLeft(i4) > BitmapDescriptorFactory.HUE_RED) {
                            ArticleViewer.this.isRtl = 1;
                            this.hasRtl = true;
                            break;
                        }
                        i4++;
                    }
                } else {
                    dp = 0;
                }
                if (this.currentBlock.level > 0) {
                    if (this.hasRtl) {
                        this.textX = AndroidUtilities.dp((float) ((this.currentBlock.level * 14) + 14));
                    } else {
                        this.textX = AndroidUtilities.dp((float) (this.currentBlock.level * 14)) + AndroidUtilities.dp(32.0f);
                    }
                } else if (this.hasRtl) {
                    this.textX = AndroidUtilities.dp(14.0f);
                } else {
                    this.textX = AndroidUtilities.dp(32.0f);
                }
                this.textLayout2 = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, i3, this.currentBlock);
                if (this.textLayout2 != null) {
                    this.textY2 = AndroidUtilities.dp(8.0f) + dp;
                    i3 = (AndroidUtilities.dp(8.0f) + this.textLayout2.getHeight()) + dp;
                } else {
                    i3 = dp;
                }
                if (i3 != 0) {
                    i3 += AndroidUtilities.dp(8.0f);
                }
            } else {
                i3 = 0;
            }
            setMeasuredDimension(size, i3);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout2, this.textX, this.textY2) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockBlockquote tLRPC$TL_pageBlockBlockquote) {
            this.currentBlock = tLRPC$TL_pageBlockBlockquote;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockChannelCell extends FrameLayout {
        private Paint backgroundPaint;
        private int buttonWidth;
        private AnimatorSet currentAnimation;
        private TLRPC$TL_pageBlockChannel currentBlock;
        private int currentState;
        private int currentType;
        private ImageView imageView;
        private int lastCreatedWidth;
        private ContextProgressView progressView;
        private StaticLayout textLayout;
        private TextView textView;
        private int textX = AndroidUtilities.dp(18.0f);
        private int textX2;
        private int textY = AndroidUtilities.dp(11.0f);
        private int textY2 = AndroidUtilities.dp(11.5f);

        public BlockChannelCell(Context context, int i) {
            super(context);
            setWillNotDraw(false);
            this.backgroundPaint = new Paint();
            this.currentType = i;
            this.textView = new TextView(context);
            this.textView.setTextSize(1, 14.0f);
            this.textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.textView.setText(LocaleController.getString("ChannelJoin", R.string.ChannelJoin));
            this.textView.setGravity(19);
            addView(this.textView, LayoutHelper.createFrame(-2, 39, 53));
            this.textView.setOnClickListener(new OnClickListener(ArticleViewer.this) {
                public void onClick(View view) {
                    if (BlockChannelCell.this.currentState == 0) {
                        BlockChannelCell.this.setState(1, true);
                        ArticleViewer.this.joinChannel(BlockChannelCell.this, ArticleViewer.this.loadedChannel);
                    }
                }
            });
            this.imageView = new ImageView(context);
            this.imageView.setImageResource(R.drawable.list_check);
            this.imageView.setScaleType(ScaleType.CENTER);
            addView(this.imageView, LayoutHelper.createFrame(39, 39, 53));
            this.progressView = new ContextProgressView(context, 0);
            addView(this.progressView, LayoutHelper.createFrame(39, 39, 53));
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) AndroidUtilities.dp(39.0f), this.backgroundPaint);
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
            }
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            this.imageView.layout((this.textX2 + (this.buttonWidth / 2)) - AndroidUtilities.dp(19.0f), 0, (this.textX2 + (this.buttonWidth / 2)) + AndroidUtilities.dp(20.0f), AndroidUtilities.dp(39.0f));
            this.progressView.layout((this.textX2 + (this.buttonWidth / 2)) - AndroidUtilities.dp(19.0f), 0, (this.textX2 + (this.buttonWidth / 2)) + AndroidUtilities.dp(20.0f), AndroidUtilities.dp(39.0f));
            this.textView.layout(this.textX2, 0, this.textX2 + this.textView.getMeasuredWidth(), this.textView.getMeasuredHeight());
        }

        protected void onMeasure(int i, int i2) {
            int size = MeasureSpec.getSize(i);
            setMeasuredDimension(size, AndroidUtilities.dp(48.0f));
            this.textView.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(39.0f), 1073741824));
            this.buttonWidth = this.textView.getMeasuredWidth();
            this.progressView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(39.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(39.0f), 1073741824));
            this.imageView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(39.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(39.0f), 1073741824));
            if (this.currentBlock != null && this.lastCreatedWidth != size) {
                this.textLayout = ArticleViewer.this.createLayoutForText(this.currentBlock.channel.title, null, (size - AndroidUtilities.dp(52.0f)) - this.buttonWidth, this.currentBlock);
                this.textX2 = (getMeasuredWidth() - this.textX) - this.buttonWidth;
            }
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return this.currentType != 0 ? super.onTouchEvent(motionEvent) : ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockChannel tLRPC$TL_pageBlockChannel) {
            this.currentBlock = tLRPC$TL_pageBlockChannel;
            int access$8800 = ArticleViewer.this.getSelectedColor();
            if (this.currentType == 0) {
                this.textView.setTextColor(-14840360);
                if (access$8800 == 0) {
                    this.backgroundPaint.setColor(-526345);
                } else if (access$8800 == 1) {
                    this.backgroundPaint.setColor(-1712440);
                } else if (access$8800 == 2) {
                    this.backgroundPaint.setColor(-14277082);
                }
                this.imageView.setColorFilter(new PorterDuffColorFilter(-6710887, Mode.MULTIPLY));
            } else {
                this.textView.setTextColor(-1);
                this.backgroundPaint.setColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
                this.imageView.setColorFilter(new PorterDuffColorFilter(-1, Mode.MULTIPLY));
            }
            this.lastCreatedWidth = 0;
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(tLRPC$TL_pageBlockChannel.channel.id));
            if (chat == null || chat.min) {
                ArticleViewer.this.loadChannel(this, tLRPC$TL_pageBlockChannel.channel);
                setState(1, false);
            } else {
                ArticleViewer.this.loadedChannel = chat;
                if (!chat.left || chat.kicked) {
                    setState(4, false);
                } else {
                    setState(0, false);
                }
            }
            requestLayout();
        }

        public void setState(int i, boolean z) {
            float f = 1.0f;
            if (this.currentAnimation != null) {
                this.currentAnimation.cancel();
            }
            this.currentState = i;
            if (z) {
                this.currentAnimation = new AnimatorSet();
                AnimatorSet animatorSet = this.currentAnimation;
                Animator[] animatorArr = new Animator[9];
                TextView textView = this.textView;
                String str = "alpha";
                float[] fArr = new float[1];
                fArr[0] = i == 0 ? 1.0f : BitmapDescriptorFactory.HUE_RED;
                animatorArr[0] = ObjectAnimator.ofFloat(textView, str, fArr);
                textView = this.textView;
                str = "scaleX";
                fArr = new float[1];
                fArr[0] = i == 0 ? 1.0f : 0.1f;
                animatorArr[1] = ObjectAnimator.ofFloat(textView, str, fArr);
                textView = this.textView;
                str = "scaleY";
                fArr = new float[1];
                fArr[0] = i == 0 ? 1.0f : 0.1f;
                animatorArr[2] = ObjectAnimator.ofFloat(textView, str, fArr);
                ContextProgressView contextProgressView = this.progressView;
                String str2 = "alpha";
                float[] fArr2 = new float[1];
                fArr2[0] = i == 1 ? 1.0f : BitmapDescriptorFactory.HUE_RED;
                animatorArr[3] = ObjectAnimator.ofFloat(contextProgressView, str2, fArr2);
                contextProgressView = this.progressView;
                str2 = "scaleX";
                fArr2 = new float[1];
                fArr2[0] = i == 1 ? 1.0f : 0.1f;
                animatorArr[4] = ObjectAnimator.ofFloat(contextProgressView, str2, fArr2);
                contextProgressView = this.progressView;
                str2 = "scaleY";
                fArr2 = new float[1];
                fArr2[0] = i == 1 ? 1.0f : 0.1f;
                animatorArr[5] = ObjectAnimator.ofFloat(contextProgressView, str2, fArr2);
                ImageView imageView = this.imageView;
                str2 = "alpha";
                fArr2 = new float[1];
                fArr2[0] = i == 2 ? 1.0f : BitmapDescriptorFactory.HUE_RED;
                animatorArr[6] = ObjectAnimator.ofFloat(imageView, str2, fArr2);
                imageView = this.imageView;
                str2 = "scaleX";
                fArr2 = new float[1];
                fArr2[0] = i == 2 ? 1.0f : 0.1f;
                animatorArr[7] = ObjectAnimator.ofFloat(imageView, str2, fArr2);
                ImageView imageView2 = this.imageView;
                str = "scaleY";
                fArr = new float[1];
                if (i != 2) {
                    f = 0.1f;
                }
                fArr[0] = f;
                animatorArr[8] = ObjectAnimator.ofFloat(imageView2, str, fArr);
                animatorSet.playTogether(animatorArr);
                this.currentAnimation.setDuration(150);
                this.currentAnimation.start();
                return;
            }
            this.textView.setAlpha(i == 0 ? 1.0f : BitmapDescriptorFactory.HUE_RED);
            this.textView.setScaleX(i == 0 ? 1.0f : 0.1f);
            this.textView.setScaleY(i == 0 ? 1.0f : 0.1f);
            this.progressView.setAlpha(i == 1 ? 1.0f : BitmapDescriptorFactory.HUE_RED);
            this.progressView.setScaleX(i == 1 ? 1.0f : 0.1f);
            this.progressView.setScaleY(i == 1 ? 1.0f : 0.1f);
            this.imageView.setAlpha(i == 2 ? 1.0f : BitmapDescriptorFactory.HUE_RED);
            this.imageView.setScaleX(i == 2 ? 1.0f : 0.1f);
            ImageView imageView3 = this.imageView;
            if (i != 2) {
                f = 0.1f;
            }
            imageView3.setScaleY(f);
        }
    }

    private class BlockCollageCell extends FrameLayout {
        private Adapter adapter;
        private TLRPC$TL_pageBlockCollage currentBlock;
        private GridLayoutManager gridLayoutManager;
        private boolean inLayout;
        private RecyclerListView innerListView;
        private int lastCreatedWidth;
        private int listX;
        private StaticLayout textLayout;
        private int textX;
        private int textY;

        public BlockCollageCell(Context context) {
            super(context);
            this.innerListView = new RecyclerListView(context, ArticleViewer.this) {
                public void requestLayout() {
                    if (!BlockCollageCell.this.inLayout) {
                        super.requestLayout();
                    }
                }
            };
            this.innerListView.addItemDecoration(new ItemDecoration(ArticleViewer.this) {
                public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
                    rect.left = 0;
                    rect.top = 0;
                    int dp = AndroidUtilities.dp(2.0f);
                    rect.right = dp;
                    rect.bottom = dp;
                }
            });
            RecyclerListView recyclerListView = this.innerListView;
            LayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            this.gridLayoutManager = gridLayoutManager;
            recyclerListView.setLayoutManager(gridLayoutManager);
            recyclerListView = this.innerListView;
            Adapter c39303 = new Adapter(ArticleViewer.this) {
                public int getItemCount() {
                    return BlockCollageCell.this.currentBlock == null ? 0 : BlockCollageCell.this.currentBlock.items.size();
                }

                public int getItemViewType(int i) {
                    return ((PageBlock) BlockCollageCell.this.currentBlock.items.get(i)) instanceof TLRPC$TL_pageBlockPhoto ? 0 : 1;
                }

                public void onBindViewHolder(ViewHolder viewHolder, int i) {
                    switch (viewHolder.getItemViewType()) {
                        case 0:
                            ((BlockPhotoCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockPhoto) BlockCollageCell.this.currentBlock.items.get(i), true, true);
                            return;
                        default:
                            ((BlockVideoCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockVideo) BlockCollageCell.this.currentBlock.items.get(i), true, true);
                            return;
                    }
                }

                public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    View blockPhotoCell;
                    switch (i) {
                        case 0:
                            blockPhotoCell = new BlockPhotoCell(BlockCollageCell.this.getContext(), 2);
                            break;
                        default:
                            blockPhotoCell = new BlockVideoCell(BlockCollageCell.this.getContext(), 2);
                            break;
                    }
                    return new Holder(blockPhotoCell);
                }
            };
            this.adapter = c39303;
            recyclerListView.setAdapter(c39303);
            addView(this.innerListView, LayoutHelper.createFrame(-1, -2.0f));
            setWillNotDraw(false);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.currentBlock.level > 0) {
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            this.innerListView.layout(this.listX, 0, this.listX + this.innerListView.getMeasuredWidth(), this.innerListView.getMeasuredHeight());
        }

        protected void onMeasure(int i, int i2) {
            int i3 = 1;
            this.inLayout = true;
            int size = MeasureSpec.getSize(i);
            if (this.currentBlock != null) {
                int i4;
                if (this.currentBlock.level > 0) {
                    i3 = AndroidUtilities.dp((float) (this.currentBlock.level * 14)) + AndroidUtilities.dp(18.0f);
                    this.listX = i3;
                    this.textX = i3;
                    i3 = size - (this.listX + AndroidUtilities.dp(18.0f));
                    i4 = i3;
                } else {
                    this.listX = 0;
                    this.textX = AndroidUtilities.dp(18.0f);
                    i3 = size - AndroidUtilities.dp(36.0f);
                    i4 = size;
                }
                int dp = i4 / AndroidUtilities.dp(100.0f);
                int ceil = (int) Math.ceil((double) (((float) this.currentBlock.items.size()) / ((float) dp)));
                i4 /= dp;
                this.gridLayoutManager.setSpanCount(dp);
                this.innerListView.measure(MeasureSpec.makeMeasureSpec((dp * i4) + AndroidUtilities.dp(2.0f), 1073741824), MeasureSpec.makeMeasureSpec(i4 * ceil, 1073741824));
                i4 = (i4 * ceil) - AndroidUtilities.dp(2.0f);
                if (this.lastCreatedWidth != size) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, i3, this.currentBlock);
                    if (this.textLayout != null) {
                        this.textY = AndroidUtilities.dp(8.0f) + i4;
                        i3 = (AndroidUtilities.dp(8.0f) + this.textLayout.getHeight()) + i4;
                        if (this.currentBlock.level > 0 && !this.currentBlock.bottom) {
                            i3 += AndroidUtilities.dp(8.0f);
                        }
                    }
                }
                i3 = i4;
                i3 += AndroidUtilities.dp(8.0f);
            }
            setMeasuredDimension(size, i3);
            this.inLayout = false;
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockCollage tLRPC$TL_pageBlockCollage) {
            this.currentBlock = tLRPC$TL_pageBlockCollage;
            this.lastCreatedWidth = 0;
            this.adapter.notifyDataSetChanged();
            int access$8800 = ArticleViewer.this.getSelectedColor();
            if (access$8800 == 0) {
                this.innerListView.setGlowColor(-657673);
            } else if (access$8800 == 1) {
                this.innerListView.setGlowColor(-659492);
            } else if (access$8800 == 2) {
                this.innerListView.setGlowColor(-15461356);
            }
            requestLayout();
        }
    }

    private class BlockDividerCell extends View {
        private RectF rect = new RectF();

        public BlockDividerCell(Context context) {
            super(context);
            if (ArticleViewer.dividerPaint == null) {
                ArticleViewer.dividerPaint = new Paint();
                int access$8800 = ArticleViewer.this.getSelectedColor();
                if (access$8800 == 0) {
                    ArticleViewer.dividerPaint.setColor(-3288619);
                } else if (access$8800 == 1) {
                    ArticleViewer.dividerPaint.setColor(-4080987);
                } else if (access$8800 == 2) {
                    ArticleViewer.dividerPaint.setColor(-12303292);
                }
            }
        }

        protected void onDraw(Canvas canvas) {
            int measuredWidth = getMeasuredWidth() / 3;
            this.rect.set((float) measuredWidth, (float) AndroidUtilities.dp(8.0f), (float) (measuredWidth * 2), (float) AndroidUtilities.dp(10.0f));
            canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(1.0f), (float) AndroidUtilities.dp(1.0f), ArticleViewer.dividerPaint);
        }

        protected void onMeasure(int i, int i2) {
            setMeasuredDimension(MeasureSpec.getSize(i), AndroidUtilities.dp(18.0f));
        }
    }

    private class BlockEmbedCell extends FrameLayout {
        private TLRPC$TL_pageBlockEmbed currentBlock;
        private int lastCreatedWidth;
        private int listX;
        private StaticLayout textLayout;
        private int textX;
        private int textY;
        private WebPlayerView videoView;
        private TouchyWebView webView;

        public class TouchyWebView extends WebView {
            public TouchyWebView(Context context) {
                super(context);
                setFocusable(false);
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (BlockEmbedCell.this.currentBlock != null) {
                    if (BlockEmbedCell.this.currentBlock.allow_scrolling) {
                        requestDisallowInterceptTouchEvent(true);
                    } else {
                        ArticleViewer.this.windowView.requestDisallowInterceptTouchEvent(true);
                    }
                }
                return super.onTouchEvent(motionEvent);
            }
        }

        @SuppressLint({"SetJavaScriptEnabled"})
        public BlockEmbedCell(Context context) {
            super(context);
            setWillNotDraw(false);
            this.videoView = new WebPlayerView(context, false, false, new WebPlayerViewDelegate(ArticleViewer.this) {
                public boolean checkInlinePermissons() {
                    return false;
                }

                public ViewGroup getTextureViewContainer() {
                    return null;
                }

                public void onInitFailed() {
                    BlockEmbedCell.this.webView.setVisibility(0);
                    BlockEmbedCell.this.videoView.setVisibility(4);
                    BlockEmbedCell.this.videoView.loadVideo(null, null, null, false);
                    Map hashMap = new HashMap();
                    hashMap.put("Referer", "http://youtube.com");
                    BlockEmbedCell.this.webView.loadUrl(BlockEmbedCell.this.currentBlock.url, hashMap);
                }

                public void onInlineSurfaceTextureReady() {
                }

                public void onPlayStateChanged(WebPlayerView webPlayerView, boolean z) {
                    if (z) {
                        if (!(ArticleViewer.this.currentPlayingVideo == null || ArticleViewer.this.currentPlayingVideo == webPlayerView)) {
                            ArticleViewer.this.currentPlayingVideo.pause();
                        }
                        ArticleViewer.this.currentPlayingVideo = webPlayerView;
                        try {
                            ArticleViewer.this.parentActivity.getWindow().addFlags(128);
                            return;
                        } catch (Throwable e) {
                            FileLog.e(e);
                            return;
                        }
                    }
                    if (ArticleViewer.this.currentPlayingVideo == webPlayerView) {
                        ArticleViewer.this.currentPlayingVideo = null;
                    }
                    try {
                        ArticleViewer.this.parentActivity.getWindow().clearFlags(128);
                    } catch (Throwable e2) {
                        FileLog.e(e2);
                    }
                }

                public void onSharePressed() {
                    if (ArticleViewer.this.parentActivity != null) {
                        ArticleViewer.this.showDialog(new ShareAlert(ArticleViewer.this.parentActivity, null, BlockEmbedCell.this.currentBlock.url, false, BlockEmbedCell.this.currentBlock.url, true));
                    }
                }

                public TextureView onSwitchInlineMode(View view, boolean z, float f, int i, boolean z2) {
                    return null;
                }

                public TextureView onSwitchToFullscreen(View view, boolean z, float f, int i, boolean z2) {
                    if (z) {
                        ArticleViewer.this.fullscreenAspectRatioView.addView(ArticleViewer.this.fullscreenTextureView, LayoutHelper.createFrame(-1, -1.0f));
                        ArticleViewer.this.fullscreenAspectRatioView.setVisibility(0);
                        ArticleViewer.this.fullscreenAspectRatioView.setAspectRatio(f, i);
                        ArticleViewer.this.fullscreenedVideo = BlockEmbedCell.this.videoView;
                        ArticleViewer.this.fullscreenVideoContainer.addView(view, LayoutHelper.createFrame(-1, -1.0f));
                        ArticleViewer.this.fullscreenVideoContainer.setVisibility(0);
                    } else {
                        ArticleViewer.this.fullscreenAspectRatioView.removeView(ArticleViewer.this.fullscreenTextureView);
                        ArticleViewer.this.fullscreenedVideo = null;
                        ArticleViewer.this.fullscreenAspectRatioView.setVisibility(8);
                        ArticleViewer.this.fullscreenVideoContainer.setVisibility(4);
                    }
                    return ArticleViewer.this.fullscreenTextureView;
                }

                public void onVideoSizeChanged(float f, int i) {
                    ArticleViewer.this.fullscreenAspectRatioView.setAspectRatio(f, i);
                }

                public void prepareToSwitchInlineMode(boolean z, Runnable runnable, float f, boolean z2) {
                }
            });
            addView(this.videoView);
            ArticleViewer.this.createdWebViews.add(this);
            this.webView = new TouchyWebView(context);
            this.webView.getSettings().setJavaScriptEnabled(true);
            this.webView.getSettings().setDomStorageEnabled(true);
            this.webView.getSettings().setAllowContentAccess(true);
            if (VERSION.SDK_INT >= 17) {
                this.webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
            }
            if (VERSION.SDK_INT >= 21) {
                this.webView.getSettings().setMixedContentMode(0);
                CookieManager.getInstance().setAcceptThirdPartyCookies(this.webView, true);
            }
            this.webView.setWebChromeClient(new WebChromeClient(ArticleViewer.this) {

                /* renamed from: org.telegram.ui.ArticleViewer$BlockEmbedCell$2$1 */
                class C39321 implements Runnable {
                    C39321() {
                    }

                    public void run() {
                        if (ArticleViewer.this.customView != null) {
                            ArticleViewer.this.fullscreenVideoContainer.addView(ArticleViewer.this.customView, LayoutHelper.createFrame(-1, -1.0f));
                            ArticleViewer.this.fullscreenVideoContainer.setVisibility(0);
                        }
                    }
                }

                public void onHideCustomView() {
                    super.onHideCustomView();
                    if (ArticleViewer.this.customView != null) {
                        ArticleViewer.this.fullscreenVideoContainer.setVisibility(4);
                        ArticleViewer.this.fullscreenVideoContainer.removeView(ArticleViewer.this.customView);
                        if (!(ArticleViewer.this.customViewCallback == null || ArticleViewer.this.customViewCallback.getClass().getName().contains(".chromium."))) {
                            ArticleViewer.this.customViewCallback.onCustomViewHidden();
                        }
                        ArticleViewer.this.customView = null;
                    }
                }

                public void onShowCustomView(View view, int i, CustomViewCallback customViewCallback) {
                    onShowCustomView(view, customViewCallback);
                }

                public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
                    if (ArticleViewer.this.customView != null) {
                        customViewCallback.onCustomViewHidden();
                        return;
                    }
                    ArticleViewer.this.customView = view;
                    ArticleViewer.this.customViewCallback = customViewCallback;
                    AndroidUtilities.runOnUIThread(new C39321(), 100);
                }
            });
            this.webView.setWebViewClient(new WebViewClient(ArticleViewer.this) {
                public void onLoadResource(WebView webView, String str) {
                    super.onLoadResource(webView, str);
                }

                public void onPageFinished(WebView webView, String str) {
                    super.onPageFinished(webView, str);
                }
            });
            addView(this.webView);
        }

        public void destroyWebView(boolean z) {
            try {
                this.webView.stopLoading();
                this.webView.loadUrl("about:blank");
                if (z) {
                    this.webView.destroy();
                }
                this.currentBlock = null;
            } catch (Throwable e) {
                FileLog.e(e);
            }
            this.videoView.destroy();
        }

        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
        }

        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (!ArticleViewer.this.isVisible) {
                this.currentBlock = null;
            }
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.currentBlock.level > 0) {
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            this.webView.layout(this.listX, 0, this.listX + this.webView.getMeasuredWidth(), this.webView.getMeasuredHeight());
            if (this.videoView.getParent() == this) {
                this.videoView.layout(this.listX, 0, this.listX + this.videoView.getMeasuredWidth(), this.videoView.getMeasuredHeight());
            }
        }

        protected void onMeasure(int i, int i2) {
            int dp;
            int size = MeasureSpec.getSize(i);
            if (this.currentBlock != null) {
                int i3;
                if (this.currentBlock.level > 0) {
                    dp = AndroidUtilities.dp((float) (this.currentBlock.level * 14)) + AndroidUtilities.dp(18.0f);
                    this.listX = dp;
                    this.textX = dp;
                    dp = size - (this.listX + AndroidUtilities.dp(18.0f));
                    i3 = dp;
                } else {
                    this.listX = 0;
                    this.textX = AndroidUtilities.dp(18.0f);
                    dp = size - AndroidUtilities.dp(36.0f);
                    i3 = size;
                }
                float f = this.currentBlock.w == 0 ? 1.0f : ((float) size) / ((float) this.currentBlock.w);
                int dp2 = (int) (this.currentBlock.w == 0 ? f * ((float) AndroidUtilities.dp((float) this.currentBlock.h)) : f * ((float) this.currentBlock.h));
                this.webView.measure(MeasureSpec.makeMeasureSpec(i3, 1073741824), MeasureSpec.makeMeasureSpec(dp2, 1073741824));
                if (this.videoView.getParent() == this) {
                    this.videoView.measure(MeasureSpec.makeMeasureSpec(i3, 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(10.0f) + dp2, 1073741824));
                }
                if (this.lastCreatedWidth != size) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, dp, this.currentBlock);
                    if (this.textLayout != null) {
                        this.textY = AndroidUtilities.dp(8.0f) + dp2;
                        dp = (AndroidUtilities.dp(8.0f) + this.textLayout.getHeight()) + dp2;
                        dp += AndroidUtilities.dp(5.0f);
                        if (this.currentBlock.level <= 0 && !this.currentBlock.bottom) {
                            dp += AndroidUtilities.dp(8.0f);
                        } else if (this.currentBlock.level == 0 && this.textLayout != null) {
                            dp += AndroidUtilities.dp(8.0f);
                        }
                    }
                }
                dp = dp2;
                dp += AndroidUtilities.dp(5.0f);
                if (this.currentBlock.level <= 0) {
                }
                dp += AndroidUtilities.dp(8.0f);
            } else {
                dp = 1;
            }
            setMeasuredDimension(size, dp);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockEmbed tLRPC$TL_pageBlockEmbed) {
            TLRPC$TL_pageBlockEmbed tLRPC$TL_pageBlockEmbed2 = this.currentBlock;
            this.currentBlock = tLRPC$TL_pageBlockEmbed;
            this.lastCreatedWidth = 0;
            if (tLRPC$TL_pageBlockEmbed2 != this.currentBlock) {
                try {
                    this.webView.loadUrl("about:blank");
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                try {
                    if (this.currentBlock.html != null) {
                        this.webView.loadDataWithBaseURL("https://telegram.org/embed", this.currentBlock.html, "text/html", C3446C.UTF8_NAME, null);
                        this.videoView.setVisibility(4);
                        this.videoView.loadVideo(null, null, null, false);
                        this.webView.setVisibility(0);
                    } else {
                        if (this.videoView.loadVideo(tLRPC$TL_pageBlockEmbed.url, this.currentBlock.poster_photo_id != 0 ? ArticleViewer.this.getPhotoWithId(this.currentBlock.poster_photo_id) : null, null, this.currentBlock.autoplay)) {
                            this.webView.setVisibility(4);
                            this.videoView.setVisibility(0);
                            this.webView.stopLoading();
                            this.webView.loadUrl("about:blank");
                        } else {
                            this.webView.setVisibility(0);
                            this.videoView.setVisibility(4);
                            this.videoView.loadVideo(null, null, null, false);
                            Map hashMap = new HashMap();
                            hashMap.put("Referer", "http://youtube.com");
                            this.webView.loadUrl(this.currentBlock.url, hashMap);
                        }
                    }
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            }
            requestLayout();
        }
    }

    private class BlockEmbedPostCell extends View {
        private AvatarDrawable avatarDrawable;
        private ImageReceiver avatarImageView = new ImageReceiver(this);
        private boolean avatarVisible;
        private int captionX = AndroidUtilities.dp(18.0f);
        private int captionY;
        private TLRPC$TL_pageBlockEmbedPost currentBlock;
        private StaticLayout dateLayout;
        private int dateX;
        private int lastCreatedWidth;
        private int lineHeight;
        private StaticLayout nameLayout;
        private int nameX;
        private StaticLayout textLayout;
        private int textX = AndroidUtilities.dp(32.0f);
        private int textY = AndroidUtilities.dp(56.0f);

        public BlockEmbedPostCell(Context context) {
            super(context);
            this.avatarImageView.setRoundRadius(AndroidUtilities.dp(20.0f));
            this.avatarImageView.setImageCoords(AndroidUtilities.dp(32.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(40.0f), AndroidUtilities.dp(40.0f));
            this.avatarDrawable = new AvatarDrawable();
        }

        protected void onDraw(Canvas canvas) {
            int i = 54;
            int i2 = 0;
            if (this.currentBlock != null) {
                if (this.avatarVisible) {
                    this.avatarImageView.draw(canvas);
                }
                if (this.nameLayout != null) {
                    canvas.save();
                    canvas.translate((float) AndroidUtilities.dp((float) ((this.avatarVisible ? 54 : 0) + 32)), (float) AndroidUtilities.dp(this.dateLayout != null ? 10.0f : 19.0f));
                    this.nameLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.dateLayout != null) {
                    canvas.save();
                    if (!this.avatarVisible) {
                        i = 0;
                    }
                    canvas.translate((float) AndroidUtilities.dp((float) (i + 32)), (float) AndroidUtilities.dp(29.0f));
                    this.dateLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
                float dp = (float) AndroidUtilities.dp(18.0f);
                float dp2 = (float) AndroidUtilities.dp(6.0f);
                float dp3 = (float) AndroidUtilities.dp(20.0f);
                int i3 = this.lineHeight;
                if (this.currentBlock.level == 0) {
                    i2 = AndroidUtilities.dp(6.0f);
                }
                canvas.drawRect(dp, dp2, dp3, (float) (i3 - i2), ArticleViewer.quoteLinePaint);
            }
        }

        protected void onMeasure(int i, int i2) {
            int i3;
            int i4 = 54;
            int size = MeasureSpec.getSize(i);
            if (this.currentBlock == null) {
                i3 = 1;
            } else if (this.lastCreatedWidth != size) {
                boolean z = this.currentBlock.author_photo_id != 0;
                this.avatarVisible = z;
                if (z) {
                    Photo access$10100 = ArticleViewer.this.getPhotoWithId(this.currentBlock.author_photo_id);
                    z = access$10100 != null;
                    this.avatarVisible = z;
                    if (z) {
                        this.avatarDrawable.setInfo(0, this.currentBlock.author, null, false);
                        this.avatarImageView.setImage(FileLoader.getClosestPhotoSizeWithSize(access$10100.sizes, AndroidUtilities.dp(40.0f), true).location, String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(40), Integer.valueOf(40)}), this.avatarDrawable, 0, null, 1);
                    }
                }
                this.nameLayout = ArticleViewer.this.createLayoutForText(this.currentBlock.author, null, size - AndroidUtilities.dp((float) ((this.avatarVisible ? 54 : 0) + 50)), this.currentBlock);
                if (this.currentBlock.date != 0) {
                    ArticleViewer articleViewer = ArticleViewer.this;
                    CharSequence format = LocaleController.getInstance().chatFullDate.format(((long) this.currentBlock.date) * 1000);
                    if (!this.avatarVisible) {
                        i4 = 0;
                    }
                    this.dateLayout = articleViewer.createLayoutForText(format, null, size - AndroidUtilities.dp((float) (i4 + 50)), this.currentBlock);
                } else {
                    this.dateLayout = null;
                }
                i3 = AndroidUtilities.dp(56.0f);
                if (this.currentBlock.text != null) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, size - AndroidUtilities.dp(50.0f), this.currentBlock);
                    if (this.textLayout != null) {
                        i3 += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                this.lineHeight = i3;
            } else {
                i3 = 0;
            }
            setMeasuredDimension(size, i3);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockEmbedPost tLRPC$TL_pageBlockEmbedPost) {
            this.currentBlock = tLRPC$TL_pageBlockEmbedPost;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockFooterCell extends View {
        private TLRPC$TL_pageBlockFooter currentBlock;
        private int lastCreatedWidth;
        private StaticLayout textLayout;
        private int textX = AndroidUtilities.dp(18.0f);
        private int textY = AndroidUtilities.dp(8.0f);

        public BlockFooterCell(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.currentBlock.level > 0) {
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
        }

        protected void onMeasure(int i, int i2) {
            int i3 = 0;
            int size = MeasureSpec.getSize(i);
            if (this.currentBlock != null) {
                if (this.currentBlock.level == 0) {
                    this.textY = AndroidUtilities.dp(8.0f);
                    this.textX = AndroidUtilities.dp(18.0f);
                } else {
                    this.textY = 0;
                    this.textX = AndroidUtilities.dp((float) ((this.currentBlock.level * 14) + 18));
                }
                if (this.lastCreatedWidth != size) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, (size - AndroidUtilities.dp(18.0f)) - this.textX, this.currentBlock);
                    if (this.textLayout != null) {
                        i3 = this.textLayout.getHeight();
                        i3 = this.currentBlock.level > 0 ? i3 + AndroidUtilities.dp(8.0f) : i3 + AndroidUtilities.dp(16.0f);
                    }
                }
            } else {
                i3 = 1;
            }
            setMeasuredDimension(size, i3);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockFooter tLRPC$TL_pageBlockFooter) {
            this.currentBlock = tLRPC$TL_pageBlockFooter;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockHeaderCell extends View {
        private TLRPC$TL_pageBlockHeader currentBlock;
        private int lastCreatedWidth;
        private StaticLayout textLayout;
        private int textX = AndroidUtilities.dp(18.0f);
        private int textY = AndroidUtilities.dp(8.0f);

        public BlockHeaderCell(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null && this.textLayout != null) {
                canvas.save();
                canvas.translate((float) this.textX, (float) this.textY);
                ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                this.textLayout.draw(canvas);
                canvas.restore();
            }
        }

        protected void onMeasure(int i, int i2) {
            int size = MeasureSpec.getSize(i);
            int i3 = 0;
            if (this.currentBlock == null) {
                i3 = 1;
            } else if (this.lastCreatedWidth != size) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, size - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout != null) {
                    i3 = 0 + (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight());
                }
            }
            setMeasuredDimension(size, i3);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockHeader tLRPC$TL_pageBlockHeader) {
            this.currentBlock = tLRPC$TL_pageBlockHeader;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockListCell extends View {
        private TLRPC$TL_pageBlockList currentBlock;
        private boolean hasRtl;
        private int lastCreatedWidth;
        private int maxLetterWidth;
        private ArrayList<StaticLayout> textLayouts = new ArrayList();
        private ArrayList<StaticLayout> textNumLayouts = new ArrayList();
        private int textX;
        private ArrayList<Integer> textYLayouts = new ArrayList();

        public BlockListCell(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                int size = this.textLayouts.size();
                int measuredWidth = getMeasuredWidth();
                for (int i = 0; i < size; i++) {
                    StaticLayout staticLayout = (StaticLayout) this.textLayouts.get(i);
                    StaticLayout staticLayout2 = (StaticLayout) this.textNumLayouts.get(i);
                    canvas.save();
                    if (this.hasRtl) {
                        canvas.translate((float) ((measuredWidth - AndroidUtilities.dp(18.0f)) - ((int) Math.ceil((double) staticLayout2.getLineWidth(0)))), (float) ((Integer) this.textYLayouts.get(i)).intValue());
                    } else {
                        canvas.translate((float) AndroidUtilities.dp(18.0f), (float) ((Integer) this.textYLayouts.get(i)).intValue());
                    }
                    if (staticLayout2 != null) {
                        staticLayout2.draw(canvas);
                    }
                    canvas.restore();
                    canvas.save();
                    canvas.translate((float) this.textX, (float) ((Integer) this.textYLayouts.get(i)).intValue());
                    ArticleViewer.this.drawLayoutLink(canvas, staticLayout);
                    if (staticLayout != null) {
                        staticLayout.draw(canvas);
                    }
                    canvas.restore();
                }
            }
        }

        protected void onMeasure(int i, int i2) {
            int i3;
            int size = MeasureSpec.getSize(i);
            this.hasRtl = false;
            this.maxLetterWidth = 0;
            if (this.currentBlock == null) {
                i3 = 1;
            } else if (this.lastCreatedWidth != size) {
                int i4;
                StaticLayout access$9300;
                this.textLayouts.clear();
                this.textYLayouts.clear();
                this.textNumLayouts.clear();
                int size2 = this.currentBlock.items.size();
                for (int i5 = 0; i5 < size2; i5++) {
                    RichText richText = (RichText) this.currentBlock.items.get(i5);
                    if (i5 == 0) {
                        StaticLayout access$93002 = ArticleViewer.this.createLayoutForText(null, richText, (size - AndroidUtilities.dp(24.0f)) - this.maxLetterWidth, this.currentBlock);
                        if (access$93002 != null) {
                            int lineCount = access$93002.getLineCount();
                            for (i4 = 0; i4 < lineCount; i4++) {
                                if (access$93002.getLineLeft(i4) > BitmapDescriptorFactory.HUE_RED) {
                                    this.hasRtl = true;
                                    ArticleViewer.this.isRtl = 1;
                                    break;
                                }
                            }
                        }
                    }
                    CharSequence format = this.currentBlock.ordered ? this.hasRtl ? String.format(".%d", new Object[]{Integer.valueOf(i5 + 1)}) : String.format("%d.", new Object[]{Integer.valueOf(i5 + 1)}) : "•";
                    access$9300 = ArticleViewer.this.createLayoutForText(format, richText, size - AndroidUtilities.dp(54.0f), this.currentBlock);
                    this.textNumLayouts.add(access$9300);
                    if (this.currentBlock.ordered) {
                        if (access$9300 != null) {
                            this.maxLetterWidth = Math.max(this.maxLetterWidth, (int) Math.ceil((double) access$9300.getLineWidth(0)));
                        }
                    } else if (i5 == 0) {
                        this.maxLetterWidth = AndroidUtilities.dp(12.0f);
                    }
                }
                i4 = 0;
                int i6 = 0;
                while (i6 < size2) {
                    i4 += AndroidUtilities.dp(8.0f);
                    access$9300 = ArticleViewer.this.createLayoutForText(null, (RichText) this.currentBlock.items.get(i6), (size - AndroidUtilities.dp(42.0f)) - this.maxLetterWidth, this.currentBlock);
                    this.textYLayouts.add(Integer.valueOf(i4));
                    this.textLayouts.add(access$9300);
                    i6++;
                    i4 = access$9300 != null ? access$9300.getHeight() + i4 : i4;
                }
                if (this.hasRtl) {
                    this.textX = AndroidUtilities.dp(18.0f);
                } else {
                    this.textX = AndroidUtilities.dp(24.0f) + this.maxLetterWidth;
                }
                i3 = AndroidUtilities.dp(8.0f) + i4;
            } else {
                i3 = 0;
            }
            setMeasuredDimension(size, i3);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            int size = this.textLayouts.size();
            int dp = AndroidUtilities.dp(36.0f);
            for (int i = 0; i < size; i++) {
                StaticLayout staticLayout = (StaticLayout) this.textLayouts.get(i);
                if (ArticleViewer.this.checkLayoutForLinks(motionEvent, this, staticLayout, dp, ((Integer) this.textYLayouts.get(i)).intValue())) {
                    return true;
                }
            }
            return super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockList tLRPC$TL_pageBlockList) {
            this.currentBlock = tLRPC$TL_pageBlockList;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockParagraphCell extends View {
        private TLRPC$TL_pageBlockParagraph currentBlock;
        private int lastCreatedWidth;
        private StaticLayout textLayout;
        private int textX;
        private int textY;

        public BlockParagraphCell(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.currentBlock.level > 0) {
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
        }

        protected void onMeasure(int i, int i2) {
            int i3 = 0;
            int size = MeasureSpec.getSize(i);
            if (this.currentBlock != null) {
                if (this.currentBlock.level == 0) {
                    if (this.currentBlock.caption != null) {
                        this.textY = AndroidUtilities.dp(4.0f);
                    } else {
                        this.textY = AndroidUtilities.dp(8.0f);
                    }
                    this.textX = AndroidUtilities.dp(18.0f);
                } else {
                    this.textY = 0;
                    this.textX = AndroidUtilities.dp((float) ((this.currentBlock.level * 14) + 18));
                }
                if (this.lastCreatedWidth != size) {
                    if (this.currentBlock.text != null) {
                        this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, (size - AndroidUtilities.dp(18.0f)) - this.textX, this.currentBlock);
                    } else if (this.currentBlock.caption != null) {
                        this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, (size - AndroidUtilities.dp(18.0f)) - this.textX, this.currentBlock);
                    }
                    if (this.textLayout != null) {
                        i3 = this.textLayout.getHeight();
                        i3 = this.currentBlock.level > 0 ? i3 + AndroidUtilities.dp(8.0f) : i3 + AndroidUtilities.dp(16.0f);
                    }
                }
            } else {
                i3 = 1;
            }
            setMeasuredDimension(size, i3);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockParagraph tLRPC$TL_pageBlockParagraph) {
            this.currentBlock = tLRPC$TL_pageBlockParagraph;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockPhotoCell extends FrameLayout {
        private BlockChannelCell channelCell;
        private TLRPC$TL_pageBlockPhoto currentBlock;
        private int currentType;
        private ImageReceiver imageView = new ImageReceiver(this);
        private boolean isFirst;
        private boolean isLast;
        private int lastCreatedWidth;
        private PageBlock parentBlock;
        private boolean photoPressed;
        private StaticLayout textLayout;
        private int textX;
        private int textY;

        public BlockPhotoCell(Context context, int i) {
            super(context);
            setWillNotDraw(false);
            this.channelCell = new BlockChannelCell(context, 1);
            addView(this.channelCell, LayoutHelper.createFrame(-1, -2.0f));
            this.currentType = i;
        }

        public View getChannelCell() {
            return this.channelCell;
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                this.imageView.draw(canvas);
                if (this.textLayout != null) {
                    canvas.save();
                    float f = (float) this.textX;
                    int imageY = (this.imageView.getImageY() + this.imageView.getImageHeight()) + AndroidUtilities.dp(8.0f);
                    this.textY = imageY;
                    canvas.translate(f, (float) imageY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.currentBlock.level > 0) {
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
        }

        protected void onMeasure(int i, int i2) {
            int i3;
            int min;
            int size = MeasureSpec.getSize(i);
            if (this.currentType == 1) {
                int width = ArticleViewer.this.listView.getWidth();
                size = ((View) getParent()).getMeasuredHeight();
                i3 = width;
            } else if (this.currentType == 2) {
                i3 = size;
            } else {
                i3 = size;
                size = 0;
            }
            if (this.currentBlock != null) {
                int i4;
                int dp;
                int i5;
                Photo access$10100 = ArticleViewer.this.getPhotoWithId(this.currentBlock.photo_id);
                if (this.currentType != 0 || this.currentBlock.level <= 0) {
                    i4 = 0;
                    this.textX = AndroidUtilities.dp(18.0f);
                    dp = i3 - AndroidUtilities.dp(36.0f);
                    i5 = i3;
                } else {
                    i4 = AndroidUtilities.dp(18.0f) + AndroidUtilities.dp((float) (this.currentBlock.level * 14));
                    this.textX = i4;
                    width = i3 - (AndroidUtilities.dp(18.0f) + i4);
                    dp = width;
                    i5 = width;
                }
                if (access$10100 != null) {
                    ImageReceiver imageReceiver;
                    PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(access$10100.sizes, AndroidUtilities.getPhotoSize());
                    PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(access$10100.sizes, 80, true);
                    PhotoSize photoSize = closestPhotoSizeWithSize == closestPhotoSizeWithSize2 ? null : closestPhotoSizeWithSize2;
                    if (this.currentType == 0) {
                        size = (int) ((((float) i5) / ((float) closestPhotoSizeWithSize.f10147w)) * ((float) closestPhotoSizeWithSize.f10146h));
                        if (this.parentBlock instanceof TLRPC$TL_pageBlockCover) {
                            width = i5;
                            min = Math.min(size, i5);
                            size = i4;
                        } else {
                            int max = (int) (((float) (Math.max(ArticleViewer.this.listView.getMeasuredWidth(), ArticleViewer.this.listView.getMeasuredHeight()) - AndroidUtilities.dp(56.0f))) * 0.9f);
                            if (size > max) {
                                width = (int) ((((float) max) / ((float) closestPhotoSizeWithSize.f10146h)) * ((float) closestPhotoSizeWithSize.f10147w));
                                size = (((i3 - i4) - width) / 2) + i4;
                                min = max;
                            }
                        }
                        imageReceiver = this.imageView;
                        i4 = (this.isFirst || this.currentType == 1 || this.currentType == 2 || this.currentBlock.level > 0) ? 0 : AndroidUtilities.dp(8.0f);
                        imageReceiver.setImageCoords(size, i4, width, min);
                        this.imageView.setImage(closestPhotoSizeWithSize.location, this.currentType != 0 ? null : String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(width), Integer.valueOf(min)}), photoSize == null ? photoSize.location : null, photoSize == null ? "80_80_b" : null, closestPhotoSizeWithSize.size, null, 1);
                    }
                    width = i5;
                    min = size;
                    size = i4;
                    imageReceiver = this.imageView;
                    if (!this.isFirst) {
                    }
                    imageReceiver.setImageCoords(size, i4, width, min);
                    if (this.currentType != 0) {
                    }
                    if (photoSize == null) {
                    }
                    if (photoSize == null) {
                    }
                    this.imageView.setImage(closestPhotoSizeWithSize.location, this.currentType != 0 ? null : String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(width), Integer.valueOf(min)}), photoSize == null ? photoSize.location : null, photoSize == null ? "80_80_b" : null, closestPhotoSizeWithSize.size, null, 1);
                } else {
                    min = size;
                }
                if (this.currentType == 0 && this.lastCreatedWidth != i3) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, dp, this.currentBlock);
                    if (this.textLayout != null) {
                        min += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                if (!this.isFirst && this.currentType == 0 && this.currentBlock.level <= 0) {
                    min += AndroidUtilities.dp(8.0f);
                }
                Object obj = (!(this.parentBlock instanceof TLRPC$TL_pageBlockCover) || ArticleViewer.this.blocks == null || ArticleViewer.this.blocks.size() <= 1 || !(ArticleViewer.this.blocks.get(1) instanceof TLRPC$TL_pageBlockChannel)) ? null : 1;
                if (this.currentType != 2 && obj == null) {
                    min += AndroidUtilities.dp(8.0f);
                }
            } else {
                min = 1;
            }
            this.channelCell.measure(i, i2);
            this.channelCell.setTranslationY((float) (this.imageView.getImageHeight() - AndroidUtilities.dp(39.0f)));
            setMeasuredDimension(i3, min);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (this.channelCell.getVisibility() != 0 || y <= this.channelCell.getTranslationY() || y >= this.channelCell.getTranslationY() + ((float) AndroidUtilities.dp(39.0f))) {
                if (motionEvent.getAction() == 0 && this.imageView.isInsideImage(x, y)) {
                    this.photoPressed = true;
                } else if (motionEvent.getAction() == 1 && this.photoPressed) {
                    this.photoPressed = false;
                    ArticleViewer.this.openPhoto(this.currentBlock);
                } else if (motionEvent.getAction() == 3) {
                    this.photoPressed = false;
                }
                boolean z = this.photoPressed || ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
                return z;
            } else if (ArticleViewer.this.channelBlock == null || motionEvent.getAction() != 1) {
                return true;
            } else {
                MessagesController.openByUserName(ArticleViewer.this.channelBlock.channel.username, ArticleViewer.this.parentFragment, 2);
                ArticleViewer.this.close(false, true);
                return true;
            }
        }

        public void setBlock(TLRPC$TL_pageBlockPhoto tLRPC$TL_pageBlockPhoto, boolean z, boolean z2) {
            this.parentBlock = null;
            this.currentBlock = tLRPC$TL_pageBlockPhoto;
            this.lastCreatedWidth = 0;
            this.isFirst = z;
            this.isLast = z2;
            this.channelCell.setVisibility(4);
            requestLayout();
        }

        public void setParentBlock(PageBlock pageBlock) {
            this.parentBlock = pageBlock;
            if (ArticleViewer.this.channelBlock != null && (this.parentBlock instanceof TLRPC$TL_pageBlockCover)) {
                this.channelCell.setBlock(ArticleViewer.this.channelBlock);
                this.channelCell.setVisibility(0);
            }
        }
    }

    private class BlockPreformattedCell extends View {
        private TLRPC$TL_pageBlockPreformatted currentBlock;
        private int lastCreatedWidth;
        private StaticLayout textLayout;

        public BlockPreformattedCell(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(8.0f), (float) getMeasuredWidth(), (float) (getMeasuredHeight() - AndroidUtilities.dp(8.0f)), ArticleViewer.preformattedBackgroundPaint);
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) AndroidUtilities.dp(12.0f), (float) AndroidUtilities.dp(16.0f));
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
            }
        }

        protected void onMeasure(int i, int i2) {
            int size = MeasureSpec.getSize(i);
            int i3 = 0;
            if (this.currentBlock == null) {
                i3 = 1;
            } else if (this.lastCreatedWidth != size) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, size - AndroidUtilities.dp(24.0f), this.currentBlock);
                if (this.textLayout != null) {
                    i3 = 0 + (AndroidUtilities.dp(32.0f) + this.textLayout.getHeight());
                }
            }
            setMeasuredDimension(size, i3);
        }

        public void setBlock(TLRPC$TL_pageBlockPreformatted tLRPC$TL_pageBlockPreformatted) {
            this.currentBlock = tLRPC$TL_pageBlockPreformatted;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockPullquoteCell extends View {
        private TLRPC$TL_pageBlockPullquote currentBlock;
        private int lastCreatedWidth;
        private StaticLayout textLayout;
        private StaticLayout textLayout2;
        private int textX = AndroidUtilities.dp(18.0f);
        private int textY = AndroidUtilities.dp(8.0f);
        private int textY2;

        public BlockPullquoteCell(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.textLayout2 != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY2);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout2);
                    this.textLayout2.draw(canvas);
                    canvas.restore();
                }
            }
        }

        protected void onMeasure(int i, int i2) {
            int size = MeasureSpec.getSize(i);
            int i3 = 0;
            if (this.currentBlock == null) {
                i3 = 1;
            } else if (this.lastCreatedWidth != size) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, size - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout != null) {
                    i3 = 0 + (AndroidUtilities.dp(8.0f) + this.textLayout.getHeight());
                }
                this.textLayout2 = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, size - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout2 != null) {
                    this.textY2 = AndroidUtilities.dp(2.0f) + i3;
                    i3 += AndroidUtilities.dp(8.0f) + this.textLayout2.getHeight();
                }
                if (i3 != 0) {
                    i3 += AndroidUtilities.dp(8.0f);
                }
            }
            setMeasuredDimension(size, i3);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout2, this.textX, this.textY2) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockPullquote tLRPC$TL_pageBlockPullquote) {
            this.currentBlock = tLRPC$TL_pageBlockPullquote;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockSlideshowCell extends FrameLayout {
        private aa adapter;
        private TLRPC$TL_pageBlockSlideshow currentBlock;
        private View dotsContainer;
        private ViewPager innerListView;
        private int lastCreatedWidth;
        private StaticLayout textLayout;
        private int textX = AndroidUtilities.dp(18.0f);
        private int textY;

        public BlockSlideshowCell(Context context) {
            super(context);
            if (ArticleViewer.dotsPaint == null) {
                ArticleViewer.dotsPaint = new Paint(1);
                ArticleViewer.dotsPaint.setColor(-1);
            }
            this.innerListView = new ViewPager(context, ArticleViewer.this) {
                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    ArticleViewer.this.windowView.requestDisallowInterceptTouchEvent(true);
                    return super.onInterceptTouchEvent(motionEvent);
                }

                public boolean onTouchEvent(MotionEvent motionEvent) {
                    return super.onTouchEvent(motionEvent);
                }
            };
            this.innerListView.addOnPageChangeListener(new C0188f(ArticleViewer.this) {
                public void onPageScrollStateChanged(int i) {
                }

                public void onPageScrolled(int i, float f, int i2) {
                }

                public void onPageSelected(int i) {
                    BlockSlideshowCell.this.dotsContainer.invalidate();
                }
            });
            ViewPager viewPager = this.innerListView;
            aa c39373 = new aa(ArticleViewer.this) {

                /* renamed from: org.telegram.ui.ArticleViewer$BlockSlideshowCell$3$ObjectContainer */
                class ObjectContainer {
                    private PageBlock block;
                    private View view;

                    ObjectContainer() {
                    }
                }

                public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
                    viewGroup.removeView(((ObjectContainer) obj).view);
                }

                public int getCount() {
                    return BlockSlideshowCell.this.currentBlock == null ? 0 : BlockSlideshowCell.this.currentBlock.items.size();
                }

                public int getItemPosition(Object obj) {
                    return BlockSlideshowCell.this.currentBlock.items.contains(((ObjectContainer) obj).block) ? -1 : -2;
                }

                public Object instantiateItem(ViewGroup viewGroup, int i) {
                    View blockPhotoCell;
                    PageBlock pageBlock = (PageBlock) BlockSlideshowCell.this.currentBlock.items.get(i);
                    if (pageBlock instanceof TLRPC$TL_pageBlockPhoto) {
                        blockPhotoCell = new BlockPhotoCell(BlockSlideshowCell.this.getContext(), 1);
                        ((BlockPhotoCell) blockPhotoCell).setBlock((TLRPC$TL_pageBlockPhoto) pageBlock, true, true);
                    } else {
                        blockPhotoCell = new BlockVideoCell(BlockSlideshowCell.this.getContext(), 1);
                        ((BlockVideoCell) blockPhotoCell).setBlock((TLRPC$TL_pageBlockVideo) pageBlock, true, true);
                    }
                    viewGroup.addView(blockPhotoCell);
                    ObjectContainer objectContainer = new ObjectContainer();
                    objectContainer.view = blockPhotoCell;
                    objectContainer.block = pageBlock;
                    return objectContainer;
                }

                public boolean isViewFromObject(View view, Object obj) {
                    return ((ObjectContainer) obj).view == view;
                }

                public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
                    if (dataSetObserver != null) {
                        super.unregisterDataSetObserver(dataSetObserver);
                    }
                }
            };
            this.adapter = c39373;
            viewPager.setAdapter(c39373);
            int access$8800 = ArticleViewer.this.getSelectedColor();
            if (access$8800 == 0) {
                AndroidUtilities.setViewPagerEdgeEffectColor(this.innerListView, -657673);
            } else if (access$8800 == 1) {
                AndroidUtilities.setViewPagerEdgeEffectColor(this.innerListView, -659492);
            } else if (access$8800 == 2) {
                AndroidUtilities.setViewPagerEdgeEffectColor(this.innerListView, -15461356);
            }
            addView(this.innerListView);
            this.dotsContainer = new View(context, ArticleViewer.this) {
                protected void onDraw(Canvas canvas) {
                    if (BlockSlideshowCell.this.currentBlock != null) {
                        int currentItem = BlockSlideshowCell.this.innerListView.getCurrentItem();
                        int i = 0;
                        while (i < BlockSlideshowCell.this.currentBlock.items.size()) {
                            int dp = (AndroidUtilities.dp(13.0f) * i) + AndroidUtilities.dp(4.0f);
                            Drawable access$12000 = currentItem == i ? ArticleViewer.this.slideDotBigDrawable : ArticleViewer.this.slideDotDrawable;
                            access$12000.setBounds(dp - AndroidUtilities.dp(5.0f), 0, dp + AndroidUtilities.dp(5.0f), AndroidUtilities.dp(10.0f));
                            access$12000.draw(canvas);
                            i++;
                        }
                    }
                }
            };
            addView(this.dotsContainer);
            setWillNotDraw(false);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null && this.textLayout != null) {
                canvas.save();
                canvas.translate((float) this.textX, (float) this.textY);
                ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                this.textLayout.draw(canvas);
                canvas.restore();
            }
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            this.innerListView.layout(0, AndroidUtilities.dp(8.0f), this.innerListView.getMeasuredWidth(), AndroidUtilities.dp(8.0f) + this.innerListView.getMeasuredHeight());
            int bottom = this.innerListView.getBottom() - AndroidUtilities.dp(23.0f);
            int measuredWidth = ((i3 - i) - this.dotsContainer.getMeasuredWidth()) / 2;
            this.dotsContainer.layout(measuredWidth, bottom, this.dotsContainer.getMeasuredWidth() + measuredWidth, this.dotsContainer.getMeasuredHeight() + bottom);
        }

        protected void onMeasure(int i, int i2) {
            int dp;
            int size = MeasureSpec.getSize(i);
            if (this.currentBlock != null) {
                dp = AndroidUtilities.dp(310.0f);
                this.innerListView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(dp, 1073741824));
                int size2 = this.currentBlock.items.size();
                this.dotsContainer.measure(MeasureSpec.makeMeasureSpec((((size2 - 1) * AndroidUtilities.dp(6.0f)) + (AndroidUtilities.dp(7.0f) * size2)) + AndroidUtilities.dp(4.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(10.0f), 1073741824));
                if (this.lastCreatedWidth != size) {
                    this.textY = AndroidUtilities.dp(16.0f) + dp;
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, size - AndroidUtilities.dp(36.0f), this.currentBlock);
                    if (this.textLayout != null) {
                        dp += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                dp += AndroidUtilities.dp(16.0f);
            } else {
                dp = 1;
            }
            setMeasuredDimension(size, dp);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockSlideshow tLRPC$TL_pageBlockSlideshow) {
            this.currentBlock = tLRPC$TL_pageBlockSlideshow;
            this.lastCreatedWidth = 0;
            this.innerListView.setCurrentItem(0, false);
            this.adapter.notifyDataSetChanged();
            requestLayout();
        }
    }

    private class BlockSubheaderCell extends View {
        private TLRPC$TL_pageBlockSubheader currentBlock;
        private int lastCreatedWidth;
        private StaticLayout textLayout;
        private int textX = AndroidUtilities.dp(18.0f);
        private int textY = AndroidUtilities.dp(8.0f);

        public BlockSubheaderCell(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null && this.textLayout != null) {
                canvas.save();
                canvas.translate((float) this.textX, (float) this.textY);
                ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                this.textLayout.draw(canvas);
                canvas.restore();
            }
        }

        protected void onMeasure(int i, int i2) {
            int size = MeasureSpec.getSize(i);
            int i3 = 0;
            if (this.currentBlock == null) {
                i3 = 1;
            } else if (this.lastCreatedWidth != size) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, size - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout != null) {
                    i3 = 0 + (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight());
                }
            }
            setMeasuredDimension(size, i3);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockSubheader tLRPC$TL_pageBlockSubheader) {
            this.currentBlock = tLRPC$TL_pageBlockSubheader;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockSubtitleCell extends View {
        private TLRPC$TL_pageBlockSubtitle currentBlock;
        private int lastCreatedWidth;
        private StaticLayout textLayout;
        private int textX = AndroidUtilities.dp(18.0f);
        private int textY = AndroidUtilities.dp(8.0f);

        public BlockSubtitleCell(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null && this.textLayout != null) {
                canvas.save();
                canvas.translate((float) this.textX, (float) this.textY);
                ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                this.textLayout.draw(canvas);
                canvas.restore();
            }
        }

        protected void onMeasure(int i, int i2) {
            int size = MeasureSpec.getSize(i);
            int i3 = 0;
            if (this.currentBlock == null) {
                i3 = 1;
            } else if (this.lastCreatedWidth != size) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, size - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout != null) {
                    i3 = 0 + (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight());
                }
            }
            setMeasuredDimension(size, i3);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockSubtitle tLRPC$TL_pageBlockSubtitle) {
            this.currentBlock = tLRPC$TL_pageBlockSubtitle;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockTitleCell extends View {
        private TLRPC$TL_pageBlockTitle currentBlock;
        private int lastCreatedWidth;
        private StaticLayout textLayout;
        private int textX = AndroidUtilities.dp(18.0f);
        private int textY;

        public BlockTitleCell(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null && this.textLayout != null) {
                canvas.save();
                canvas.translate((float) this.textX, (float) this.textY);
                ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                this.textLayout.draw(canvas);
                canvas.restore();
            }
        }

        protected void onMeasure(int i, int i2) {
            int i3;
            int size = MeasureSpec.getSize(i);
            if (this.currentBlock == null) {
                i3 = 1;
            } else if (this.lastCreatedWidth != size) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, size - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout != null) {
                    i3 = (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight()) + 0;
                    if (ArticleViewer.this.isRtl == -1) {
                        int lineCount = this.textLayout.getLineCount();
                        for (int i4 = 0; i4 < lineCount; i4++) {
                            if (this.textLayout.getLineLeft(i4) > BitmapDescriptorFactory.HUE_RED) {
                                ArticleViewer.this.isRtl = 1;
                                break;
                            }
                        }
                        if (ArticleViewer.this.isRtl == -1) {
                            ArticleViewer.this.isRtl = 0;
                        }
                    }
                } else {
                    i3 = 0;
                }
                if (this.currentBlock.first) {
                    i3 += AndroidUtilities.dp(8.0f);
                    this.textY = AndroidUtilities.dp(16.0f);
                } else {
                    this.textY = AndroidUtilities.dp(8.0f);
                }
            } else {
                i3 = 0;
            }
            setMeasuredDimension(size, i3);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
        }

        public void setBlock(TLRPC$TL_pageBlockTitle tLRPC$TL_pageBlockTitle) {
            this.currentBlock = tLRPC$TL_pageBlockTitle;
            this.lastCreatedWidth = 0;
            requestLayout();
        }
    }

    private class BlockVideoCell extends FrameLayout implements FileDownloadProgressListener {
        private int TAG;
        private int buttonPressed;
        private int buttonState;
        private int buttonX;
        private int buttonY;
        private boolean cancelLoading;
        private BlockChannelCell channelCell;
        private TLRPC$TL_pageBlockVideo currentBlock;
        private Document currentDocument;
        private int currentType;
        private ImageReceiver imageView = new ImageReceiver(this);
        private boolean isFirst;
        private boolean isGif;
        private boolean isLast;
        private int lastCreatedWidth;
        private PageBlock parentBlock;
        private boolean photoPressed;
        private RadialProgress radialProgress;
        private StaticLayout textLayout;
        private int textX;
        private int textY;

        public BlockVideoCell(Context context, int i) {
            super(context);
            setWillNotDraw(false);
            this.currentType = i;
            this.radialProgress = new RadialProgress(this);
            this.radialProgress.setAlphaForPrevious(true);
            this.radialProgress.setProgressColor(-1);
            this.TAG = MediaController.getInstance().generateObserverTag();
            this.channelCell = new BlockChannelCell(context, 1);
            addView(this.channelCell, LayoutHelper.createFrame(-1, -2.0f));
        }

        private void didPressedButton(boolean z) {
            if (this.buttonState == 0) {
                this.cancelLoading = false;
                this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, false);
                if (this.isGif) {
                    this.imageView.setImage(this.currentDocument, null, this.currentDocument.thumb != null ? this.currentDocument.thumb.location : null, "80_80_b", this.currentDocument.size, null, 1);
                } else {
                    FileLoader.getInstance().loadFile(this.currentDocument, true, 1);
                }
                this.buttonState = 1;
                this.radialProgress.setBackground(getDrawableForCurrentState(), true, z);
                invalidate();
            } else if (this.buttonState == 1) {
                this.cancelLoading = true;
                if (this.isGif) {
                    this.imageView.cancelLoadImage();
                } else {
                    FileLoader.getInstance().cancelLoadFile(this.currentDocument);
                }
                this.buttonState = 0;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
                invalidate();
            } else if (this.buttonState == 2) {
                this.imageView.setAllowStartAnimation(true);
                this.imageView.startAnimation();
                this.buttonState = -1;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
            } else if (this.buttonState == 3) {
                ArticleViewer.this.openPhoto(this.currentBlock);
            }
        }

        private Drawable getDrawableForCurrentState() {
            return (this.buttonState < 0 || this.buttonState >= 4) ? null : Theme.chat_photoStatesDrawables[this.buttonState][this.buttonPressed];
        }

        public View getChannelCell() {
            return this.channelCell;
        }

        public int getObserverTag() {
            return this.TAG;
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                this.imageView.draw(canvas);
                if (this.imageView.getVisible()) {
                    this.radialProgress.draw(canvas);
                }
                if (this.textLayout != null) {
                    canvas.save();
                    float f = (float) this.textX;
                    int imageY = (this.imageView.getImageY() + this.imageView.getImageHeight()) + AndroidUtilities.dp(8.0f);
                    this.textY = imageY;
                    canvas.translate(f, (float) imageY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.currentBlock.level > 0) {
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
        }

        public void onFailedDownload(String str) {
            updateButtonState(false);
        }

        protected void onMeasure(int i, int i2) {
            int i3;
            int min;
            int size = MeasureSpec.getSize(i);
            if (this.currentType == 1) {
                int width = ArticleViewer.this.listView.getWidth();
                size = ((View) getParent()).getMeasuredHeight();
                i3 = width;
            } else if (this.currentType == 2) {
                i3 = size;
            } else {
                i3 = size;
                size = 0;
            }
            if (this.currentBlock != null) {
                int dp;
                int i4;
                if (this.currentType != 0 || this.currentBlock.level <= 0) {
                    this.textX = AndroidUtilities.dp(18.0f);
                    dp = i3 - AndroidUtilities.dp(36.0f);
                    width = 0;
                    i4 = i3;
                } else {
                    i4 = AndroidUtilities.dp(18.0f) + AndroidUtilities.dp((float) (this.currentBlock.level * 14));
                    this.textX = i4;
                    width = i3 - (AndroidUtilities.dp(18.0f) + i4);
                    dp = width;
                    int i5 = i4;
                    i4 = width;
                    width = i5;
                }
                if (this.currentDocument != null) {
                    PhotoSize photoSize = this.currentDocument.thumb;
                    if (this.currentType == 0) {
                        int i6 = (int) ((((float) i4) / ((float) photoSize.f10147w)) * ((float) photoSize.f10146h));
                        if (this.parentBlock instanceof TLRPC$TL_pageBlockCover) {
                            min = Math.min(i6, i4);
                        } else {
                            size = (int) (((float) (Math.max(ArticleViewer.this.listView.getMeasuredWidth(), ArticleViewer.this.listView.getMeasuredHeight()) - AndroidUtilities.dp(56.0f))) * 0.9f);
                            if (i6 > size) {
                                i4 = (int) ((((float) size) / ((float) photoSize.f10146h)) * ((float) photoSize.f10147w));
                                width += ((i3 - width) - i4) / 2;
                                min = size;
                            } else {
                                min = i6;
                            }
                        }
                    } else {
                        min = size;
                    }
                    ImageReceiver imageReceiver = this.imageView;
                    size = (this.isFirst || this.currentType == 1 || this.currentType == 2 || this.currentBlock.level > 0) ? 0 : AndroidUtilities.dp(8.0f);
                    imageReceiver.setImageCoords(width, size, i4, min);
                    if (this.isGif) {
                        this.imageView.setImage(this.currentDocument, String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(i4), Integer.valueOf(min)}), photoSize != null ? photoSize.location : null, photoSize != null ? "80_80_b" : null, this.currentDocument.size, null, 1);
                    } else {
                        this.imageView.setImage(null, null, photoSize != null ? photoSize.location : null, photoSize != null ? "80_80_b" : null, 0, null, 1);
                    }
                    size = AndroidUtilities.dp(48.0f);
                    this.buttonX = (int) (((float) this.imageView.getImageX()) + (((float) (this.imageView.getImageWidth() - size)) / 2.0f));
                    this.buttonY = (int) (((float) this.imageView.getImageY()) + (((float) (this.imageView.getImageHeight() - size)) / 2.0f));
                    this.radialProgress.setProgressRect(this.buttonX, this.buttonY, this.buttonX + size, size + this.buttonY);
                } else {
                    min = size;
                }
                if (this.currentType == 0 && this.lastCreatedWidth != i3) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, dp, this.currentBlock);
                    if (this.textLayout != null) {
                        min += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                if (!this.isFirst && this.currentType == 0 && this.currentBlock.level <= 0) {
                    min += AndroidUtilities.dp(8.0f);
                }
                Object obj = (!(this.parentBlock instanceof TLRPC$TL_pageBlockCover) || ArticleViewer.this.blocks == null || ArticleViewer.this.blocks.size() <= 1 || !(ArticleViewer.this.blocks.get(1) instanceof TLRPC$TL_pageBlockChannel)) ? null : 1;
                if (this.currentType != 2 && obj == null) {
                    min += AndroidUtilities.dp(8.0f);
                }
            } else {
                min = 1;
            }
            this.channelCell.measure(i, i2);
            this.channelCell.setTranslationY((float) (this.imageView.getImageHeight() - AndroidUtilities.dp(39.0f)));
            setMeasuredDimension(i3, min);
        }

        public void onProgressDownload(String str, float f) {
            this.radialProgress.setProgress(f, true);
            if (this.buttonState != 1) {
                updateButtonState(false);
            }
        }

        public void onProgressUpload(String str, float f, boolean z) {
        }

        public void onSuccessDownload(String str) {
            this.radialProgress.setProgress(1.0f, true);
            if (this.isGif) {
                this.buttonState = 2;
                didPressedButton(true);
                return;
            }
            updateButtonState(true);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (this.channelCell.getVisibility() != 0 || y <= this.channelCell.getTranslationY() || y >= this.channelCell.getTranslationY() + ((float) AndroidUtilities.dp(39.0f))) {
                if (motionEvent.getAction() == 0 && this.imageView.isInsideImage(x, y)) {
                    if ((this.buttonState == -1 || x < ((float) this.buttonX) || x > ((float) (this.buttonX + AndroidUtilities.dp(48.0f))) || y < ((float) this.buttonY) || y > ((float) (this.buttonY + AndroidUtilities.dp(48.0f)))) && this.buttonState != 0) {
                        this.photoPressed = true;
                    } else {
                        this.buttonPressed = 1;
                        invalidate();
                    }
                } else if (motionEvent.getAction() == 1) {
                    if (this.photoPressed) {
                        this.photoPressed = false;
                        ArticleViewer.this.openPhoto(this.currentBlock);
                    } else if (this.buttonPressed == 1) {
                        this.buttonPressed = 0;
                        playSoundEffect(0);
                        didPressedButton(false);
                        this.radialProgress.swapBackground(getDrawableForCurrentState());
                        invalidate();
                    }
                } else if (motionEvent.getAction() == 3) {
                    this.photoPressed = false;
                }
                boolean z = this.photoPressed || this.buttonPressed != 0 || ArticleViewer.this.checkLayoutForLinks(motionEvent, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(motionEvent);
                return z;
            } else if (ArticleViewer.this.channelBlock == null || motionEvent.getAction() != 1) {
                return true;
            } else {
                MessagesController.openByUserName(ArticleViewer.this.channelBlock.channel.username, ArticleViewer.this.parentFragment, 2);
                ArticleViewer.this.close(false, true);
                return true;
            }
        }

        public void setBlock(TLRPC$TL_pageBlockVideo tLRPC$TL_pageBlockVideo, boolean z, boolean z2) {
            this.currentBlock = tLRPC$TL_pageBlockVideo;
            this.parentBlock = null;
            this.cancelLoading = false;
            this.currentDocument = ArticleViewer.this.getDocumentWithId(this.currentBlock.video_id);
            this.isGif = MessageObject.isGifDocument(this.currentDocument);
            this.lastCreatedWidth = 0;
            this.isFirst = z;
            this.isLast = z2;
            this.channelCell.setVisibility(4);
            updateButtonState(false);
            requestLayout();
        }

        public void setParentBlock(PageBlock pageBlock) {
            this.parentBlock = pageBlock;
            if (ArticleViewer.this.channelBlock != null && (this.parentBlock instanceof TLRPC$TL_pageBlockCover)) {
                this.channelCell.setBlock(ArticleViewer.this.channelBlock);
                this.channelCell.setVisibility(0);
            }
        }

        public void updateButtonState(boolean z) {
            float f = BitmapDescriptorFactory.HUE_RED;
            String attachFileName = FileLoader.getAttachFileName(this.currentDocument);
            boolean exists = FileLoader.getPathToAttach(this.currentDocument, true).exists();
            if (TextUtils.isEmpty(attachFileName)) {
                this.radialProgress.setBackground(null, false, false);
            } else if (exists) {
                MediaController.getInstance().removeLoadingFileObserver(this);
                if (this.isGif) {
                    this.buttonState = -1;
                } else {
                    this.buttonState = 3;
                }
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
                invalidate();
            } else {
                float f2;
                boolean z2;
                MediaController.getInstance().addLoadingFileObserver(attachFileName, null, this);
                if (FileLoader.getInstance().isLoadingFile(attachFileName)) {
                    this.buttonState = 1;
                    Float fileProgress = ImageLoader.getInstance().getFileProgress(attachFileName);
                    if (fileProgress != null) {
                        f = fileProgress.floatValue();
                    }
                    f2 = f;
                    z2 = true;
                } else if (this.cancelLoading || !this.isGif) {
                    this.buttonState = 0;
                    f2 = BitmapDescriptorFactory.HUE_RED;
                    z2 = false;
                } else {
                    this.buttonState = 1;
                    f2 = BitmapDescriptorFactory.HUE_RED;
                    z2 = true;
                }
                this.radialProgress.setBackground(getDrawableForCurrentState(), z2, z);
                this.radialProgress.setProgress(f2, false);
                invalidate();
            }
        }
    }

    class CheckForLongPress implements Runnable {
        public int currentPressCount;

        CheckForLongPress() {
        }

        public void run() {
            if (ArticleViewer.this.checkingForLongPress && ArticleViewer.this.windowView != null) {
                ArticleViewer.this.checkingForLongPress = false;
                ArticleViewer.this.windowView.performHapticFeedback(0);
                if (ArticleViewer.this.pressedLink != null) {
                    final Object url = ArticleViewer.this.pressedLink.getUrl();
                    Builder builder = new Builder(ArticleViewer.this.parentActivity);
                    builder.setTitle(url);
                    builder.setItems(new CharSequence[]{LocaleController.getString("Open", R.string.Open), LocaleController.getString("Copy", R.string.Copy)}, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (ArticleViewer.this.parentActivity != null) {
                                if (i == 0) {
                                    Browser.openUrl(ArticleViewer.this.parentActivity, url);
                                } else if (i == 1) {
                                    CharSequence charSequence = url;
                                    if (charSequence.startsWith("mailto:")) {
                                        charSequence = charSequence.substring(7);
                                    } else if (charSequence.startsWith("tel:")) {
                                        charSequence = charSequence.substring(4);
                                    }
                                    AndroidUtilities.addToClipboard(charSequence);
                                }
                            }
                        }
                    });
                    ArticleViewer.this.showDialog(builder.create());
                    ArticleViewer.this.hideActionBar();
                    ArticleViewer.this.pressedLink = null;
                    ArticleViewer.this.pressedLinkOwnerLayout = null;
                    ArticleViewer.this.pressedLinkOwnerView.invalidate();
                } else if (ArticleViewer.this.pressedLinkOwnerLayout != null && ArticleViewer.this.pressedLinkOwnerView != null) {
                    int top = (ArticleViewer.this.pressedLinkOwnerView.getTop() - AndroidUtilities.dp(54.0f)) + ArticleViewer.this.pressedLayoutY;
                    if (top < 0) {
                        top *= -1;
                    }
                    ArticleViewer.this.pressedLinkOwnerView.invalidate();
                    ArticleViewer.this.drawBlockSelection = true;
                    ArticleViewer.this.showPopup(ArticleViewer.this.pressedLinkOwnerView, 48, 0, top);
                    ArticleViewer.this.listView.setLayoutFrozen(true);
                    ArticleViewer.this.listView.setLayoutFrozen(false);
                }
            }
        }
    }

    private final class CheckForTap implements Runnable {
        private CheckForTap() {
        }

        public void run() {
            if (ArticleViewer.this.pendingCheckForLongPress == null) {
                ArticleViewer.this.pendingCheckForLongPress = new CheckForLongPress();
            }
            ArticleViewer.this.pendingCheckForLongPress.currentPressCount = ArticleViewer.access$804(ArticleViewer.this);
            if (ArticleViewer.this.windowView != null) {
                ArticleViewer.this.windowView.postDelayed(ArticleViewer.this.pendingCheckForLongPress, (long) (ViewConfiguration.getLongPressTimeout() - ViewConfiguration.getTapTimeout()));
            }
        }
    }

    public class ColorCell extends FrameLayout {
        private int currentColor;
        private boolean selected;
        private TextView textView;
        final /* synthetic */ ArticleViewer this$0;

        public ColorCell(ArticleViewer articleViewer, Context context) {
            int i = 5;
            this.this$0 = articleViewer;
            super(context);
            if (ArticleViewer.colorPaint == null) {
                ArticleViewer.colorPaint = new Paint(1);
                ArticleViewer.selectorPaint = new Paint(1);
                ArticleViewer.selectorPaint.setColor(-15428119);
                ArticleViewer.selectorPaint.setStyle(Style.STROKE);
                ArticleViewer.selectorPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
            }
            setBackgroundDrawable(Theme.createSelectorDrawable(251658240, 2));
            setWillNotDraw(false);
            this.textView = new TextView(context);
            this.textView.setTextColor(-14606047);
            this.textView.setTextSize(1, 16.0f);
            this.textView.setLines(1);
            this.textView.setMaxLines(1);
            this.textView.setSingleLine(true);
            this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            this.textView.setPadding(0, 0, 0, AndroidUtilities.dp(1.0f));
            View view = this.textView;
            if (!LocaleController.isRTL) {
                i = 3;
            }
            addView(view, LayoutHelper.createFrame(-1, -1.0f, i | 48, (float) (LocaleController.isRTL ? 17 : 53), BitmapDescriptorFactory.HUE_RED, (float) (LocaleController.isRTL ? 53 : 17), BitmapDescriptorFactory.HUE_RED));
        }

        protected void onDraw(Canvas canvas) {
            ArticleViewer.colorPaint.setColor(this.currentColor);
            canvas.drawCircle(!LocaleController.isRTL ? (float) AndroidUtilities.dp(28.0f) : (float) (getMeasuredWidth() - AndroidUtilities.dp(48.0f)), (float) (getMeasuredHeight() / 2), (float) AndroidUtilities.dp(10.0f), ArticleViewer.colorPaint);
            if (this.selected) {
                ArticleViewer.selectorPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
                ArticleViewer.selectorPaint.setColor(-15428119);
                canvas.drawCircle(!LocaleController.isRTL ? (float) AndroidUtilities.dp(28.0f) : (float) (getMeasuredWidth() - AndroidUtilities.dp(48.0f)), (float) (getMeasuredHeight() / 2), (float) AndroidUtilities.dp(10.0f), ArticleViewer.selectorPaint);
            } else if (this.currentColor == -1) {
                ArticleViewer.selectorPaint.setStrokeWidth((float) AndroidUtilities.dp(1.0f));
                ArticleViewer.selectorPaint.setColor(-4539718);
                canvas.drawCircle(!LocaleController.isRTL ? (float) AndroidUtilities.dp(28.0f) : (float) (getMeasuredWidth() - AndroidUtilities.dp(48.0f)), (float) (getMeasuredHeight() / 2), (float) AndroidUtilities.dp(9.0f), ArticleViewer.selectorPaint);
            }
        }

        protected void onMeasure(int i, int i2) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
        }

        public void select(boolean z) {
            if (this.selected != z) {
                this.selected = z;
                invalidate();
            }
        }

        public void setTextAndColor(String str, int i) {
            this.textView.setText(str);
            this.currentColor = i;
            invalidate();
        }
    }

    public class FontCell extends FrameLayout {
        private TextView textView;
        private TextView textView2;
        final /* synthetic */ ArticleViewer this$0;

        public FontCell(ArticleViewer articleViewer, Context context) {
            int i = 5;
            this.this$0 = articleViewer;
            super(context);
            setBackgroundDrawable(Theme.createSelectorDrawable(251658240, 2));
            this.textView = new TextView(context);
            this.textView.setTextColor(-14606047);
            this.textView.setTextSize(1, 16.0f);
            this.textView.setLines(1);
            this.textView.setMaxLines(1);
            this.textView.setSingleLine(true);
            this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            addView(this.textView, LayoutHelper.createFrame(-1, -1.0f, (LocaleController.isRTL ? 5 : 3) | 48, (float) (LocaleController.isRTL ? 17 : 53), BitmapDescriptorFactory.HUE_RED, (float) (LocaleController.isRTL ? 53 : 17), BitmapDescriptorFactory.HUE_RED));
            this.textView2 = new TextView(context);
            this.textView2.setTextColor(-14606047);
            this.textView2.setTextSize(1, 16.0f);
            this.textView2.setLines(1);
            this.textView2.setMaxLines(1);
            this.textView2.setSingleLine(true);
            this.textView2.setText("Aa");
            this.textView2.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            View view = this.textView2;
            if (!LocaleController.isRTL) {
                i = 3;
            }
            addView(view, LayoutHelper.createFrame(-1, -1.0f, i | 48, 17.0f, BitmapDescriptorFactory.HUE_RED, 17.0f, BitmapDescriptorFactory.HUE_RED));
        }

        protected void onMeasure(int i, int i2) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
        }

        public void select(boolean z) {
            this.textView2.setTextColor(z ? -15428119 : -14606047);
        }

        public void setTextAndTypeface(String str, Typeface typeface) {
            this.textView.setText(str);
            this.textView.setTypeface(typeface);
            this.textView2.setTypeface(typeface);
            invalidate();
        }
    }

    private class PhotoBackgroundDrawable extends ColorDrawable {
        private Runnable drawRunnable;

        public PhotoBackgroundDrawable(int i) {
            super(i);
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (getAlpha() != 0 && this.drawRunnable != null) {
                this.drawRunnable.run();
                this.drawRunnable = null;
            }
        }

        public void setAlpha(int i) {
            if (ArticleViewer.this.parentActivity instanceof LaunchActivity) {
                DrawerLayoutContainer drawerLayoutContainer = ((LaunchActivity) ArticleViewer.this.parentActivity).drawerLayoutContainer;
                boolean z = (ArticleViewer.this.isPhotoVisible && i == 255) ? false : true;
                drawerLayoutContainer.setAllowDrawContent(z);
            }
            super.setAlpha(i);
        }
    }

    public static class PlaceProviderObject {
        public int clipBottomAddition;
        public int clipTopAddition;
        public ImageReceiver imageReceiver;
        public int index;
        public View parentView;
        public int radius;
        public float scale = 1.0f;
        public int size;
        public Bitmap thumb;
        public int viewX;
        public int viewY;
    }

    private class RadialProgressView {
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

        public RadialProgressView(Context context, View view) {
            if (ArticleViewer.decelerateInterpolator == null) {
                ArticleViewer.decelerateInterpolator = new DecelerateInterpolator(1.5f);
                ArticleViewer.progressPaint = new Paint(1);
                ArticleViewer.progressPaint.setStyle(Style.STROKE);
                ArticleViewer.progressPaint.setStrokeCap(Cap.ROUND);
                ArticleViewer.progressPaint.setStrokeWidth((float) AndroidUtilities.dp(3.0f));
                ArticleViewer.progressPaint.setColor(-1);
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
                        this.animatedProgressValue = (f * ArticleViewer.decelerateInterpolator.getInterpolation(((float) this.currentProgressTime) / 300.0f)) + this.animationProgressStart;
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
            int access$13400 = (ArticleViewer.this.getContainerViewWidth() - i) / 2;
            int access$13500 = (ArticleViewer.this.getContainerViewHeight() - i) / 2;
            if (this.previousBackgroundState >= 0 && this.previousBackgroundState < 4) {
                drawable = ArticleViewer.progressDrawables[this.previousBackgroundState];
                if (drawable != null) {
                    drawable.setAlpha((int) ((this.animatedAlphaValue * 255.0f) * this.alpha));
                    drawable.setBounds(access$13400, access$13500, access$13400 + i, access$13500 + i);
                    drawable.draw(canvas);
                }
            }
            if (this.backgroundState >= 0 && this.backgroundState < 4) {
                drawable = ArticleViewer.progressDrawables[this.backgroundState];
                if (drawable != null) {
                    if (this.previousBackgroundState != -2) {
                        drawable.setAlpha((int) (((1.0f - this.animatedAlphaValue) * 255.0f) * this.alpha));
                    } else {
                        drawable.setAlpha((int) (this.alpha * 255.0f));
                    }
                    drawable.setBounds(access$13400, access$13500, access$13400 + i, access$13500 + i);
                    drawable.draw(canvas);
                }
            }
            if (this.backgroundState == 0 || this.backgroundState == 1 || this.previousBackgroundState == 0 || this.previousBackgroundState == 1) {
                int dp = AndroidUtilities.dp(4.0f);
                if (this.previousBackgroundState != -2) {
                    ArticleViewer.progressPaint.setAlpha((int) ((this.animatedAlphaValue * 255.0f) * this.alpha));
                } else {
                    ArticleViewer.progressPaint.setAlpha((int) (this.alpha * 255.0f));
                }
                this.progressRect.set((float) (access$13400 + dp), (float) (access$13500 + dp), (float) ((access$13400 + i) - dp), (float) ((i + access$13500) - dp));
                canvas.drawArc(this.progressRect, this.radOffset - 0.049804688f, Math.max(4.0f, 360.0f * this.animatedProgressValue), false, ArticleViewer.progressPaint);
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

    private class SizeChooseView extends View {
        private int circleSize;
        private int gapSize;
        private int lineSize;
        private boolean moving;
        private Paint paint = new Paint(1);
        private int sideSide;
        private boolean startMoving;
        private int startMovingQuality;
        private float startX;

        public SizeChooseView(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            int measuredHeight = getMeasuredHeight() / 2;
            int i = 0;
            while (i < 5) {
                int i2 = (this.circleSize / 2) + (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * i));
                if (i <= ArticleViewer.this.selectedFontSize) {
                    this.paint.setColor(-15428119);
                } else {
                    this.paint.setColor(-3355444);
                }
                canvas.drawCircle((float) i2, (float) measuredHeight, i == ArticleViewer.this.selectedFontSize ? (float) AndroidUtilities.dp(4.0f) : (float) (this.circleSize / 2), this.paint);
                if (i != 0) {
                    int i3 = ((i2 - (this.circleSize / 2)) - this.gapSize) - this.lineSize;
                    canvas.drawRect((float) i3, (float) (measuredHeight - AndroidUtilities.dp(1.0f)), (float) (i3 + this.lineSize), (float) (AndroidUtilities.dp(1.0f) + measuredHeight), this.paint);
                }
                i++;
            }
        }

        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            MeasureSpec.getSize(i);
            this.circleSize = AndroidUtilities.dp(5.0f);
            this.gapSize = AndroidUtilities.dp(2.0f);
            this.sideSide = AndroidUtilities.dp(17.0f);
            this.lineSize = (((getMeasuredWidth() - (this.circleSize * 5)) - (this.gapSize * 8)) - (this.sideSide * 2)) / 4;
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            int i = 0;
            float x = motionEvent.getX();
            int i2;
            int i3;
            if (motionEvent.getAction() == 0) {
                getParent().requestDisallowInterceptTouchEvent(true);
                i2 = 0;
                while (i2 < 5) {
                    i3 = (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * i2)) + (this.circleSize / 2);
                    if (x <= ((float) (i3 - AndroidUtilities.dp(15.0f))) || x >= ((float) (i3 + AndroidUtilities.dp(15.0f)))) {
                        i2++;
                    } else {
                        boolean z;
                        if (i2 == ArticleViewer.this.selectedFontSize) {
                            z = true;
                        }
                        this.startMoving = z;
                        this.startX = x;
                        this.startMovingQuality = ArticleViewer.this.selectedFontSize;
                    }
                }
            } else if (motionEvent.getAction() == 2) {
                if (this.startMoving) {
                    if (Math.abs(this.startX - x) >= AndroidUtilities.getPixelsInCM(0.5f, true)) {
                        this.moving = true;
                        this.startMoving = false;
                    }
                } else if (this.moving) {
                    while (i < 5) {
                        i2 = (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * i)) + (this.circleSize / 2);
                        i3 = ((this.lineSize / 2) + (this.circleSize / 2)) + this.gapSize;
                        if (x <= ((float) (i2 - i3)) || x >= ((float) (i2 + i3))) {
                            i++;
                        } else if (ArticleViewer.this.selectedFontSize != i) {
                            ArticleViewer.this.selectedFontSize = i;
                            ArticleViewer.this.updatePaintSize();
                            invalidate();
                        }
                    }
                }
            } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                if (!this.moving) {
                    i2 = 0;
                    while (i2 < 5) {
                        i3 = (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * i2)) + (this.circleSize / 2);
                        if (x <= ((float) (i3 - AndroidUtilities.dp(15.0f))) || x >= ((float) (i3 + AndroidUtilities.dp(15.0f)))) {
                            i2++;
                        } else if (ArticleViewer.this.selectedFontSize != i2) {
                            ArticleViewer.this.selectedFontSize = i2;
                            ArticleViewer.this.updatePaintSize();
                            invalidate();
                        }
                    }
                } else if (ArticleViewer.this.selectedFontSize != this.startMovingQuality) {
                    ArticleViewer.this.updatePaintSize();
                }
                this.startMoving = false;
                this.moving = false;
            }
            return true;
        }
    }

    private class WebpageAdapter extends SelectionAdapter {
        private Context context;

        public WebpageAdapter(Context context) {
            this.context = context;
        }

        private int getTypeForBlock(PageBlock pageBlock) {
            return pageBlock instanceof TLRPC$TL_pageBlockParagraph ? 0 : pageBlock instanceof TLRPC$TL_pageBlockHeader ? 1 : pageBlock instanceof TLRPC$TL_pageBlockDivider ? 2 : pageBlock instanceof TLRPC$TL_pageBlockEmbed ? 3 : pageBlock instanceof TLRPC$TL_pageBlockSubtitle ? 4 : pageBlock instanceof TLRPC$TL_pageBlockVideo ? 5 : pageBlock instanceof TLRPC$TL_pageBlockPullquote ? 6 : pageBlock instanceof TLRPC$TL_pageBlockBlockquote ? 7 : pageBlock instanceof TLRPC$TL_pageBlockSlideshow ? 8 : pageBlock instanceof TLRPC$TL_pageBlockPhoto ? 9 : pageBlock instanceof TLRPC$TL_pageBlockAuthorDate ? 10 : pageBlock instanceof TLRPC$TL_pageBlockTitle ? 11 : pageBlock instanceof TLRPC$TL_pageBlockList ? 12 : pageBlock instanceof TLRPC$TL_pageBlockFooter ? 13 : pageBlock instanceof TLRPC$TL_pageBlockPreformatted ? 14 : pageBlock instanceof TLRPC$TL_pageBlockSubheader ? 15 : pageBlock instanceof TLRPC$TL_pageBlockEmbedPost ? 16 : pageBlock instanceof TLRPC$TL_pageBlockCollage ? 17 : pageBlock instanceof TLRPC$TL_pageBlockChannel ? 18 : pageBlock instanceof TLRPC$TL_pageBlockAudio ? 19 : pageBlock instanceof TLRPC$TL_pageBlockCover ? getTypeForBlock(pageBlock.cover) : 0;
        }

        public int getItemCount() {
            return (ArticleViewer.this.currentPage == null || ArticleViewer.this.currentPage.cached_page == null) ? 0 : ArticleViewer.this.blocks.size() + 1;
        }

        public int getItemViewType(int i) {
            return i == ArticleViewer.this.blocks.size() ? 90 : getTypeForBlock((PageBlock) ArticleViewer.this.blocks.get(i));
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return false;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = true;
            if (i < ArticleViewer.this.blocks.size()) {
                PageBlock pageBlock = (PageBlock) ArticleViewer.this.blocks.get(i);
                PageBlock pageBlock2 = pageBlock instanceof TLRPC$TL_pageBlockCover ? pageBlock.cover : pageBlock;
                boolean z2;
                switch (viewHolder.getItemViewType()) {
                    case 0:
                        ((BlockParagraphCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockParagraph) pageBlock2);
                        return;
                    case 1:
                        ((BlockHeaderCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockHeader) pageBlock2);
                        return;
                    case 2:
                        BlockDividerCell blockDividerCell = (BlockDividerCell) viewHolder.itemView;
                        return;
                    case 3:
                        ((BlockEmbedCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockEmbed) pageBlock2);
                        return;
                    case 4:
                        ((BlockSubtitleCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockSubtitle) pageBlock2);
                        return;
                    case 5:
                        BlockVideoCell blockVideoCell = (BlockVideoCell) viewHolder.itemView;
                        TLRPC$TL_pageBlockVideo tLRPC$TL_pageBlockVideo = (TLRPC$TL_pageBlockVideo) pageBlock2;
                        z2 = i == 0;
                        if (i != ArticleViewer.this.blocks.size() - 1) {
                            z = false;
                        }
                        blockVideoCell.setBlock(tLRPC$TL_pageBlockVideo, z2, z);
                        blockVideoCell.setParentBlock(pageBlock);
                        return;
                    case 6:
                        ((BlockPullquoteCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockPullquote) pageBlock2);
                        return;
                    case 7:
                        ((BlockBlockquoteCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockBlockquote) pageBlock2);
                        return;
                    case 8:
                        ((BlockSlideshowCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockSlideshow) pageBlock2);
                        return;
                    case 9:
                        BlockPhotoCell blockPhotoCell = (BlockPhotoCell) viewHolder.itemView;
                        TLRPC$TL_pageBlockPhoto tLRPC$TL_pageBlockPhoto = (TLRPC$TL_pageBlockPhoto) pageBlock2;
                        z2 = i == 0;
                        if (i != ArticleViewer.this.blocks.size() - 1) {
                            z = false;
                        }
                        blockPhotoCell.setBlock(tLRPC$TL_pageBlockPhoto, z2, z);
                        blockPhotoCell.setParentBlock(pageBlock);
                        return;
                    case 10:
                        ((BlockAuthorDateCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockAuthorDate) pageBlock2);
                        return;
                    case 11:
                        ((BlockTitleCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockTitle) pageBlock2);
                        return;
                    case 12:
                        ((BlockListCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockList) pageBlock2);
                        return;
                    case 13:
                        ((BlockFooterCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockFooter) pageBlock2);
                        return;
                    case 14:
                        ((BlockPreformattedCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockPreformatted) pageBlock2);
                        return;
                    case 15:
                        ((BlockSubheaderCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockSubheader) pageBlock2);
                        return;
                    case 16:
                        ((BlockEmbedPostCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockEmbedPost) pageBlock2);
                        return;
                    case 17:
                        ((BlockCollageCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockCollage) pageBlock2);
                        return;
                    case 18:
                        ((BlockChannelCell) viewHolder.itemView).setBlock((TLRPC$TL_pageBlockChannel) pageBlock2);
                        return;
                    case 19:
                        BlockAudioCell blockAudioCell = (BlockAudioCell) viewHolder.itemView;
                        TLRPC$TL_pageBlockAudio tLRPC$TL_pageBlockAudio = (TLRPC$TL_pageBlockAudio) pageBlock2;
                        boolean z3 = i == 0;
                        if (i != ArticleViewer.this.blocks.size() - 1) {
                            z = false;
                        }
                        blockAudioCell.setBlock(tLRPC$TL_pageBlockAudio, z3, z);
                        return;
                    default:
                        return;
                }
            }
            switch (viewHolder.getItemViewType()) {
                case 90:
                    TextView textView = (TextView) ((ViewGroup) viewHolder.itemView).getChildAt(0);
                    int access$8800 = ArticleViewer.this.getSelectedColor();
                    if (access$8800 == 0) {
                        textView.setTextColor(-8879475);
                        textView.setBackgroundColor(-1183760);
                        return;
                    } else if (access$8800 == 1) {
                        textView.setTextColor(ArticleViewer.this.getGrayTextColor());
                        textView.setBackgroundColor(-1712440);
                        return;
                    } else if (access$8800 == 2) {
                        textView.setTextColor(ArticleViewer.this.getGrayTextColor());
                        textView.setBackgroundColor(-14277082);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View blockParagraphCell;
            switch (i) {
                case 0:
                    blockParagraphCell = new BlockParagraphCell(this.context);
                    break;
                case 1:
                    blockParagraphCell = new BlockHeaderCell(this.context);
                    break;
                case 2:
                    blockParagraphCell = new BlockDividerCell(this.context);
                    break;
                case 3:
                    blockParagraphCell = new BlockEmbedCell(this.context);
                    break;
                case 4:
                    blockParagraphCell = new BlockSubtitleCell(this.context);
                    break;
                case 5:
                    blockParagraphCell = new BlockVideoCell(this.context, 0);
                    break;
                case 6:
                    blockParagraphCell = new BlockPullquoteCell(this.context);
                    break;
                case 7:
                    blockParagraphCell = new BlockBlockquoteCell(this.context);
                    break;
                case 8:
                    blockParagraphCell = new BlockSlideshowCell(this.context);
                    break;
                case 9:
                    blockParagraphCell = new BlockPhotoCell(this.context, 0);
                    break;
                case 10:
                    blockParagraphCell = new BlockAuthorDateCell(this.context);
                    break;
                case 11:
                    blockParagraphCell = new BlockTitleCell(this.context);
                    break;
                case 12:
                    blockParagraphCell = new BlockListCell(this.context);
                    break;
                case 13:
                    blockParagraphCell = new BlockFooterCell(this.context);
                    break;
                case 14:
                    blockParagraphCell = new BlockPreformattedCell(this.context);
                    break;
                case 15:
                    blockParagraphCell = new BlockSubheaderCell(this.context);
                    break;
                case 16:
                    blockParagraphCell = new BlockEmbedPostCell(this.context);
                    break;
                case 17:
                    blockParagraphCell = new BlockCollageCell(this.context);
                    break;
                case 18:
                    blockParagraphCell = new BlockChannelCell(this.context, 0);
                    break;
                case 19:
                    blockParagraphCell = new BlockAudioCell(this.context);
                    break;
                default:
                    View c39401 = new FrameLayout(this.context) {
                        protected void onMeasure(int i, int i2) {
                            super.onMeasure(i, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(44.0f), 1073741824));
                        }
                    };
                    c39401.setTag(Integer.valueOf(90));
                    View textView = new TextView(this.context);
                    c39401.addView(textView, LayoutHelper.createFrame(-1, 34.0f, 51, BitmapDescriptorFactory.HUE_RED, 10.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                    textView.setText(LocaleController.getString("PreviewFeedback", R.string.PreviewFeedback));
                    textView.setTextSize(1, 12.0f);
                    textView.setGravity(17);
                    blockParagraphCell = c39401;
                    break;
            }
            blockParagraphCell.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            return new Holder(blockParagraphCell);
        }
    }

    private class WindowView extends FrameLayout {
        private float alpha;
        private Runnable attachRunnable;
        private boolean closeAnimationInProgress;
        private float innerTranslationX;
        private boolean maybeStartTracking;
        private boolean selfLayout;
        private boolean startedTracking;
        private int startedTrackingPointerId;
        private int startedTrackingX;
        private int startedTrackingY;
        private VelocityTracker tracker;

        public WindowView(Context context) {
            super(context);
        }

        private void prepareForMoving(MotionEvent motionEvent) {
            this.maybeStartTracking = false;
            this.startedTracking = true;
            this.startedTrackingX = (int) motionEvent.getX();
        }

        protected boolean drawChild(Canvas canvas, View view, long j) {
            int measuredWidth = getMeasuredWidth();
            int i = (int) this.innerTranslationX;
            int save = canvas.save();
            canvas.clipRect(i, 0, measuredWidth, getHeight());
            boolean drawChild = super.drawChild(canvas, view, j);
            canvas.restoreToCount(save);
            if (i != 0 && view == ArticleViewer.this.containerView) {
                float min = Math.min(0.8f, ((float) (measuredWidth - i)) / ((float) measuredWidth));
                if (min < BitmapDescriptorFactory.HUE_RED) {
                    min = BitmapDescriptorFactory.HUE_RED;
                }
                ArticleViewer.this.scrimPaint.setColor(((int) (min * 153.0f)) << 24);
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) i, (float) getHeight(), ArticleViewer.this.scrimPaint);
                min = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(((float) (measuredWidth - i)) / ((float) AndroidUtilities.dp(20.0f)), 1.0f));
                ArticleViewer.this.layerShadowDrawable.setBounds(i - ArticleViewer.this.layerShadowDrawable.getIntrinsicWidth(), view.getTop(), i, view.getBottom());
                ArticleViewer.this.layerShadowDrawable.setAlpha((int) (min * 255.0f));
                ArticleViewer.this.layerShadowDrawable.draw(canvas);
            }
            return drawChild;
        }

        public float getAlpha() {
            return this.alpha;
        }

        public float getInnerTranslationX() {
            return this.innerTranslationX;
        }

        public boolean handleTouchEvent(MotionEvent motionEvent) {
            if (ArticleViewer.this.isPhotoVisible || this.closeAnimationInProgress || ArticleViewer.this.fullscreenVideoContainer.getVisibility() == 0) {
                return false;
            }
            if (motionEvent != null && motionEvent.getAction() == 0 && !this.startedTracking && !this.maybeStartTracking) {
                this.startedTrackingPointerId = motionEvent.getPointerId(0);
                this.maybeStartTracking = true;
                this.startedTrackingX = (int) motionEvent.getX();
                this.startedTrackingY = (int) motionEvent.getY();
                if (this.tracker != null) {
                    this.tracker.clear();
                }
            } else if (motionEvent != null && motionEvent.getAction() == 2 && motionEvent.getPointerId(0) == this.startedTrackingPointerId) {
                if (this.tracker == null) {
                    this.tracker = VelocityTracker.obtain();
                }
                int max = Math.max(0, (int) (motionEvent.getX() - ((float) this.startedTrackingX)));
                int abs = Math.abs(((int) motionEvent.getY()) - this.startedTrackingY);
                this.tracker.addMovement(motionEvent);
                if (this.maybeStartTracking && !this.startedTracking && ((float) max) >= AndroidUtilities.getPixelsInCM(0.4f, true) && Math.abs(max) / 3 > abs) {
                    prepareForMoving(motionEvent);
                } else if (this.startedTracking) {
                    ArticleViewer.this.containerView.setTranslationX((float) max);
                    setInnerTranslationX((float) max);
                }
            } else if (motionEvent != null && motionEvent.getPointerId(0) == this.startedTrackingPointerId && (motionEvent.getAction() == 3 || motionEvent.getAction() == 1 || motionEvent.getAction() == 6)) {
                float xVelocity;
                float yVelocity;
                if (this.tracker == null) {
                    this.tracker = VelocityTracker.obtain();
                }
                this.tracker.computeCurrentVelocity(1000);
                if (!this.startedTracking) {
                    xVelocity = this.tracker.getXVelocity();
                    yVelocity = this.tracker.getYVelocity();
                    if (xVelocity >= 3500.0f && xVelocity > Math.abs(yVelocity)) {
                        prepareForMoving(motionEvent);
                    }
                }
                if (this.startedTracking) {
                    xVelocity = ArticleViewer.this.containerView.getX();
                    AnimatorSet animatorSet = new AnimatorSet();
                    yVelocity = this.tracker.getXVelocity();
                    final boolean z = xVelocity < ((float) ArticleViewer.this.containerView.getMeasuredWidth()) / 3.0f && (yVelocity < 3500.0f || yVelocity < this.tracker.getYVelocity());
                    Animator[] animatorArr;
                    if (z) {
                        animatorArr = new Animator[2];
                        animatorArr[0] = ObjectAnimator.ofFloat(ArticleViewer.this.containerView, "translationX", new float[]{BitmapDescriptorFactory.HUE_RED});
                        animatorArr[1] = ObjectAnimator.ofFloat(this, "innerTranslationX", new float[]{BitmapDescriptorFactory.HUE_RED});
                        animatorSet.playTogether(animatorArr);
                    } else {
                        xVelocity = ((float) ArticleViewer.this.containerView.getMeasuredWidth()) - xVelocity;
                        animatorArr = new Animator[2];
                        animatorArr[0] = ObjectAnimator.ofFloat(ArticleViewer.this.containerView, "translationX", new float[]{(float) ArticleViewer.this.containerView.getMeasuredWidth()});
                        animatorArr[1] = ObjectAnimator.ofFloat(this, "innerTranslationX", new float[]{(float) ArticleViewer.this.containerView.getMeasuredWidth()});
                        animatorSet.playTogether(animatorArr);
                    }
                    animatorSet.setDuration((long) Math.max((int) (xVelocity * (200.0f / ((float) ArticleViewer.this.containerView.getMeasuredWidth()))), 50));
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            if (!z) {
                                ArticleViewer.this.saveCurrentPagePosition();
                                ArticleViewer.this.onClosed();
                            }
                            WindowView.this.startedTracking = false;
                            WindowView.this.closeAnimationInProgress = false;
                        }
                    });
                    animatorSet.start();
                    this.closeAnimationInProgress = true;
                } else {
                    this.maybeStartTracking = false;
                    this.startedTracking = false;
                }
                if (this.tracker != null) {
                    this.tracker.recycle();
                    this.tracker = null;
                }
            } else if (motionEvent == null) {
                this.maybeStartTracking = false;
                this.startedTracking = false;
                if (this.tracker != null) {
                    this.tracker.recycle();
                    this.tracker = null;
                }
            }
            return this.startedTracking;
        }

        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            ArticleViewer.this.attachedToWindow = true;
        }

        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            ArticleViewer.this.attachedToWindow = false;
        }

        protected void onDraw(Canvas canvas) {
            canvas.drawRect(this.innerTranslationX, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) getMeasuredHeight(), ArticleViewer.this.backgroundPaint);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return !ArticleViewer.this.collapsed && (handleTouchEvent(motionEvent) || super.onInterceptTouchEvent(motionEvent));
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            if (!this.selfLayout) {
                int i5;
                if (VERSION.SDK_INT < 21 || ArticleViewer.this.lastInsets == null) {
                    i5 = 0;
                } else {
                    WindowInsets windowInsets = (WindowInsets) ArticleViewer.this.lastInsets;
                    int systemWindowInsetLeft = windowInsets.getSystemWindowInsetLeft();
                    if (windowInsets.getSystemWindowInsetRight() != 0) {
                        ArticleViewer.this.barBackground.layout((i3 - i) - windowInsets.getSystemWindowInsetRight(), 0, i3 - i, i4 - i2);
                    } else if (windowInsets.getSystemWindowInsetLeft() != 0) {
                        ArticleViewer.this.barBackground.layout(0, 0, windowInsets.getSystemWindowInsetLeft(), i4 - i2);
                    } else {
                        ArticleViewer.this.barBackground.layout(0, (i4 - i2) - windowInsets.getStableInsetBottom(), i3 - i, i4 - i2);
                    }
                    i5 = systemWindowInsetLeft;
                }
                ArticleViewer.this.containerView.layout(i5, 0, ArticleViewer.this.containerView.getMeasuredWidth() + i5, ArticleViewer.this.containerView.getMeasuredHeight());
                ArticleViewer.this.photoContainerView.layout(i5, 0, ArticleViewer.this.photoContainerView.getMeasuredWidth() + i5, ArticleViewer.this.photoContainerView.getMeasuredHeight());
                ArticleViewer.this.photoContainerBackground.layout(i5, 0, ArticleViewer.this.photoContainerBackground.getMeasuredWidth() + i5, ArticleViewer.this.photoContainerBackground.getMeasuredHeight());
                ArticleViewer.this.fullscreenVideoContainer.layout(i5, 0, ArticleViewer.this.fullscreenVideoContainer.getMeasuredWidth() + i5, ArticleViewer.this.fullscreenVideoContainer.getMeasuredHeight());
                ArticleViewer.this.animatingImageView.layout(0, 0, ArticleViewer.this.animatingImageView.getMeasuredWidth(), ArticleViewer.this.animatingImageView.getMeasuredHeight());
            }
        }

        protected void onMeasure(int i, int i2) {
            int i3;
            int size = MeasureSpec.getSize(i);
            int size2 = MeasureSpec.getSize(i2);
            if (VERSION.SDK_INT < 21 || ArticleViewer.this.lastInsets == null) {
                setMeasuredDimension(size, size2);
                i3 = size;
            } else {
                setMeasuredDimension(size, size2);
                WindowInsets windowInsets = (WindowInsets) ArticleViewer.this.lastInsets;
                if (AndroidUtilities.incorrectDisplaySizeFix) {
                    if (size2 > AndroidUtilities.displaySize.y) {
                        size2 = AndroidUtilities.displaySize.y;
                    }
                    size2 += AndroidUtilities.statusBarHeight;
                }
                size2 -= windowInsets.getSystemWindowInsetBottom();
                size -= windowInsets.getSystemWindowInsetRight() + windowInsets.getSystemWindowInsetLeft();
                if (windowInsets.getSystemWindowInsetRight() != 0) {
                    ArticleViewer.this.barBackground.measure(MeasureSpec.makeMeasureSpec(windowInsets.getSystemWindowInsetRight(), 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
                } else if (windowInsets.getSystemWindowInsetLeft() != 0) {
                    ArticleViewer.this.barBackground.measure(MeasureSpec.makeMeasureSpec(windowInsets.getSystemWindowInsetLeft(), 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
                } else {
                    ArticleViewer.this.barBackground.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(windowInsets.getSystemWindowInsetBottom(), 1073741824));
                }
                i3 = size;
            }
            ArticleViewer.this.containerView.measure(MeasureSpec.makeMeasureSpec(i3, 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
            ArticleViewer.this.photoContainerView.measure(MeasureSpec.makeMeasureSpec(i3, 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
            ArticleViewer.this.photoContainerBackground.measure(MeasureSpec.makeMeasureSpec(i3, 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
            ArticleViewer.this.fullscreenVideoContainer.measure(MeasureSpec.makeMeasureSpec(i3, 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
            ViewGroup.LayoutParams layoutParams = ArticleViewer.this.animatingImageView.getLayoutParams();
            ArticleViewer.this.animatingImageView.measure(MeasureSpec.makeMeasureSpec(layoutParams.width, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(layoutParams.height, Integer.MIN_VALUE));
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return !ArticleViewer.this.collapsed && (handleTouchEvent(motionEvent) || super.onTouchEvent(motionEvent));
        }

        public void requestDisallowInterceptTouchEvent(boolean z) {
            handleTouchEvent(null);
            super.requestDisallowInterceptTouchEvent(z);
        }

        public void setAlpha(float f) {
            ArticleViewer.this.backgroundPaint.setAlpha((int) (255.0f * f));
            this.alpha = f;
            if (ArticleViewer.this.parentActivity instanceof LaunchActivity) {
                DrawerLayoutContainer drawerLayoutContainer = ((LaunchActivity) ArticleViewer.this.parentActivity).drawerLayoutContainer;
                boolean z = (ArticleViewer.this.isVisible && this.alpha == 1.0f && this.innerTranslationX == BitmapDescriptorFactory.HUE_RED) ? false : true;
                drawerLayoutContainer.setAllowDrawContent(z);
            }
            invalidate();
        }

        public void setInnerTranslationX(float f) {
            this.innerTranslationX = f;
            if (ArticleViewer.this.parentActivity instanceof LaunchActivity) {
                DrawerLayoutContainer drawerLayoutContainer = ((LaunchActivity) ArticleViewer.this.parentActivity).drawerLayoutContainer;
                boolean z = (ArticleViewer.this.isVisible && this.alpha == 1.0f && this.innerTranslationX == BitmapDescriptorFactory.HUE_RED) ? false : true;
                drawerLayoutContainer.setAllowDrawContent(z);
            }
            invalidate();
        }
    }

    static /* synthetic */ int access$804(ArticleViewer articleViewer) {
        int i = articleViewer.pressCount + 1;
        articleViewer.pressCount = i;
        return i;
    }

    private void addAllMediaFromBlock(PageBlock pageBlock) {
        if ((pageBlock instanceof TLRPC$TL_pageBlockPhoto) || ((pageBlock instanceof TLRPC$TL_pageBlockVideo) && isVideoBlock(pageBlock))) {
            this.photoBlocks.add(pageBlock);
        } else if (pageBlock instanceof TLRPC$TL_pageBlockSlideshow) {
            TLRPC$TL_pageBlockSlideshow tLRPC$TL_pageBlockSlideshow = (TLRPC$TL_pageBlockSlideshow) pageBlock;
            r3 = tLRPC$TL_pageBlockSlideshow.items.size();
            for (r2 = 0; r2 < r3; r2++) {
                r1 = (PageBlock) tLRPC$TL_pageBlockSlideshow.items.get(r2);
                if ((r1 instanceof TLRPC$TL_pageBlockPhoto) || ((r1 instanceof TLRPC$TL_pageBlockVideo) && isVideoBlock(pageBlock))) {
                    this.photoBlocks.add(r1);
                }
            }
        } else if (pageBlock instanceof TLRPC$TL_pageBlockCollage) {
            TLRPC$TL_pageBlockCollage tLRPC$TL_pageBlockCollage = (TLRPC$TL_pageBlockCollage) pageBlock;
            r3 = tLRPC$TL_pageBlockCollage.items.size();
            for (r2 = 0; r2 < r3; r2++) {
                r1 = (PageBlock) tLRPC$TL_pageBlockCollage.items.get(r2);
                if ((r1 instanceof TLRPC$TL_pageBlockPhoto) || ((r1 instanceof TLRPC$TL_pageBlockVideo) && isVideoBlock(pageBlock))) {
                    this.photoBlocks.add(r1);
                }
            }
        } else if (!(pageBlock instanceof TLRPC$TL_pageBlockCover)) {
        } else {
            if ((pageBlock.cover instanceof TLRPC$TL_pageBlockPhoto) || ((pageBlock.cover instanceof TLRPC$TL_pageBlockVideo) && isVideoBlock(pageBlock.cover))) {
                this.photoBlocks.add(pageBlock.cover);
            }
        }
    }

    private void addPageToStack(TLRPC$WebPage tLRPC$WebPage, String str) {
        saveCurrentPagePosition();
        this.currentPage = tLRPC$WebPage;
        this.pagesStack.add(tLRPC$WebPage);
        updateInterfaceForCurrentPage(false);
        if (str != null) {
            Integer num = (Integer) this.anchors.get(str.toLowerCase());
            if (num != null) {
                this.layoutManager.scrollToPositionWithOffset(num.intValue(), 0);
            }
        }
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
                    ArticleViewer.this.imageMoveAnimation = null;
                    ArticleViewer.this.photoContainerView.invalidate();
                }
            });
            this.imageMoveAnimation.start();
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

    private boolean checkLayoutForLinks(MotionEvent motionEvent, View view, StaticLayout staticLayout, int i, int i2) {
        if (view == null || staticLayout == null) {
            return false;
        }
        Object obj;
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int spanEnd;
        if (motionEvent.getAction() == 0) {
            if (x >= i && x <= staticLayout.getWidth() + i && y >= i2 && y <= staticLayout.getHeight() + i2) {
                this.pressedLinkOwnerLayout = staticLayout;
                this.pressedLinkOwnerView = view;
                this.pressedLayoutY = i2;
                if (staticLayout.getText() instanceof Spannable) {
                    x -= i;
                    try {
                        y = staticLayout.getLineForVertical(y - i2);
                        int offsetForHorizontal = staticLayout.getOffsetForHorizontal(y, (float) x);
                        float lineLeft = staticLayout.getLineLeft(y);
                        if (lineLeft <= ((float) x) && staticLayout.getLineWidth(y) + lineLeft >= ((float) x)) {
                            Spannable spannable = (Spannable) staticLayout.getText();
                            TextPaintUrlSpan[] textPaintUrlSpanArr = (TextPaintUrlSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, TextPaintUrlSpan.class);
                            if (textPaintUrlSpanArr != null && textPaintUrlSpanArr.length > 0) {
                                this.pressedLink = textPaintUrlSpanArr[0];
                                int spanStart = spannable.getSpanStart(this.pressedLink);
                                int i3 = 1;
                                offsetForHorizontal = spannable.getSpanEnd(this.pressedLink);
                                while (i3 < textPaintUrlSpanArr.length) {
                                    TextPaintUrlSpan textPaintUrlSpan = textPaintUrlSpanArr[i3];
                                    int spanStart2 = spannable.getSpanStart(textPaintUrlSpan);
                                    spanEnd = spannable.getSpanEnd(textPaintUrlSpan);
                                    if (spanStart > spanStart2 || spanEnd > offsetForHorizontal) {
                                        this.pressedLink = textPaintUrlSpan;
                                        offsetForHorizontal = spanEnd;
                                        spanEnd = spanStart2;
                                    } else {
                                        spanEnd = spanStart;
                                    }
                                    i3++;
                                    spanStart = spanEnd;
                                }
                                try {
                                    this.urlPath.setCurrentLayout(staticLayout, spanStart, BitmapDescriptorFactory.HUE_RED);
                                    staticLayout.getSelectionPath(spanStart, offsetForHorizontal, this.urlPath);
                                    view.invalidate();
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                            }
                        }
                    } catch (Throwable e2) {
                        FileLog.e(e2);
                    }
                }
                obj = null;
            }
            obj = null;
        } else if (motionEvent.getAction() == 1) {
            if (this.pressedLink != null) {
                String url = this.pressedLink.getUrl();
                if (url != null) {
                    Object obj2 = null;
                    spanEnd = url.lastIndexOf(35);
                    if (spanEnd != -1) {
                        String substring = url.substring(spanEnd + 1);
                        if (url.toLowerCase().contains(this.currentPage.url.toLowerCase())) {
                            Integer num = (Integer) this.anchors.get(substring);
                            if (num != null) {
                                this.layoutManager.scrollToPositionWithOffset(num.intValue(), 0);
                                obj = 1;
                            } else {
                                obj = null;
                            }
                            obj2 = obj;
                            url = substring;
                        } else {
                            url = substring;
                        }
                    } else {
                        url = null;
                    }
                    if (obj2 == null && this.openUrlReqId == 0) {
                        showProgressView(true);
                        final TLObject tLRPC$TL_messages_getWebPage = new TLRPC$TL_messages_getWebPage();
                        tLRPC$TL_messages_getWebPage.url = this.pressedLink.getUrl();
                        tLRPC$TL_messages_getWebPage.hash = 0;
                        this.openUrlReqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getWebPage, new RequestDelegate() {
                            public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        if (ArticleViewer.this.openUrlReqId != 0) {
                                            ArticleViewer.this.openUrlReqId = 0;
                                            ArticleViewer.this.showProgressView(false);
                                            if (!ArticleViewer.this.isVisible) {
                                                return;
                                            }
                                            if ((tLObject instanceof TLRPC$TL_webPage) && (((TLRPC$TL_webPage) tLObject).cached_page instanceof TLRPC$TL_pageFull)) {
                                                ArticleViewer.this.addPageToStack((TLRPC$TL_webPage) tLObject, url);
                                            } else {
                                                Browser.openUrl(ArticleViewer.this.parentActivity, tLRPC$TL_messages_getWebPage.url);
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
                x = 1;
            }
            obj = null;
        } else {
            if (motionEvent.getAction() == 3) {
                obj = 1;
            }
            obj = null;
        }
        if (obj != null) {
            this.pressedLink = null;
            this.pressedLinkOwnerLayout = null;
            this.pressedLinkOwnerView = null;
            view.invalidate();
        }
        if (motionEvent.getAction() == 0) {
            startCheckLongPress();
        }
        if (!(motionEvent.getAction() == 0 || motionEvent.getAction() == 2)) {
            cancelCheckLongPress();
        }
        return this.pressedLinkOwnerLayout != null;
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

    private boolean checkPhotoAnimation() {
        if (this.photoAnimationInProgress != 0 && Math.abs(this.photoTransitionAnimationStartTime - System.currentTimeMillis()) >= 500) {
            if (this.photoAnimationEndRunnable != null) {
                this.photoAnimationEndRunnable.run();
                this.photoAnimationEndRunnable = null;
            }
            this.photoAnimationInProgress = 0;
        }
        return this.photoAnimationInProgress != 0;
    }

    private void checkProgress(int i, boolean z) {
        if (this.currentFileNames[i] != null) {
            int i2 = this.currentIndex;
            if (i == 1) {
                i2++;
            } else if (i == 2) {
                i2--;
            }
            File mediaFile = getMediaFile(i2);
            boolean isMediaVideo = isMediaVideo(i2);
            if (mediaFile == null || !mediaFile.exists()) {
                if (!isMediaVideo) {
                    this.radialProgressViews[i].setBackgroundState(0, z);
                } else if (FileLoader.getInstance().isLoadingFile(this.currentFileNames[i])) {
                    this.radialProgressViews[i].setBackgroundState(1, false);
                } else {
                    this.radialProgressViews[i].setBackgroundState(2, false);
                }
                Float fileProgress = ImageLoader.getInstance().getFileProgress(this.currentFileNames[i]);
                if (fileProgress == null) {
                    fileProgress = Float.valueOf(BitmapDescriptorFactory.HUE_RED);
                }
                this.radialProgressViews[i].setProgress(fileProgress.floatValue(), false);
            } else if (isMediaVideo) {
                this.radialProgressViews[i].setBackgroundState(3, z);
            } else {
                this.radialProgressViews[i].setBackgroundState(-1, z);
            }
            if (i == 0) {
                boolean z2 = (this.currentFileNames[0] == null || isMediaVideo || this.radialProgressViews[0].backgroundState == 0) ? false : true;
                this.canZoom = z2;
                return;
            }
            return;
        }
        this.radialProgressViews[i].setBackgroundState(-1, z);
    }

    private void checkScroll(int i) {
        int dp = AndroidUtilities.dp(56.0f);
        int max = Math.max(AndroidUtilities.statusBarHeight, AndroidUtilities.dp(24.0f));
        float f = (float) (dp - max);
        int i2 = this.currentHeaderHeight - i;
        if (i2 < max) {
            i2 = max;
        } else if (i2 > dp) {
            i2 = dp;
        }
        this.currentHeaderHeight = i2;
        float f2 = 0.8f + ((((float) (this.currentHeaderHeight - max)) / f) * 0.2f);
        max = (int) (((float) dp) * f2);
        this.backButton.setScaleX(f2);
        this.backButton.setScaleY(f2);
        this.backButton.setTranslationY((float) ((dp - this.currentHeaderHeight) / 2));
        this.shareContainer.setScaleX(f2);
        this.shareContainer.setScaleY(f2);
        this.settingsButton.setScaleX(f2);
        this.settingsButton.setScaleY(f2);
        this.shareContainer.setTranslationY((float) ((dp - this.currentHeaderHeight) / 2));
        this.settingsButton.setTranslationY((float) ((dp - this.currentHeaderHeight) / 2));
        this.headerView.setTranslationY((float) (this.currentHeaderHeight - dp));
        this.listView.setTopGlowOffset(this.currentHeaderHeight);
    }

    private StaticLayout createLayoutForText(CharSequence charSequence, RichText richText, int i, PageBlock pageBlock) {
        if (charSequence == null && (richText == null || (richText instanceof TLRPC$TL_textEmpty))) {
            return null;
        }
        int selectedColor = getSelectedColor();
        if (quoteLinePaint == null) {
            quoteLinePaint = new Paint();
            quoteLinePaint.setColor(getTextColor());
            preformattedBackgroundPaint = new Paint();
            if (selectedColor == 0) {
                preformattedBackgroundPaint.setColor(-657156);
            } else if (selectedColor == 1) {
                preformattedBackgroundPaint.setColor(-1712440);
            } else if (selectedColor == 2) {
                preformattedBackgroundPaint.setColor(-14277082);
            }
            urlPaint = new Paint();
            if (selectedColor == 0) {
                urlPaint.setColor(-1315861);
            } else if (selectedColor == 1) {
                urlPaint.setColor(-1712440);
            } else if (selectedColor == 2) {
                urlPaint.setColor(-14277082);
            }
        }
        CharSequence text = charSequence != null ? charSequence : getText(richText, richText, pageBlock);
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        TextPaint textPaint;
        if ((pageBlock instanceof TLRPC$TL_pageBlockEmbedPost) && richText == null) {
            if (pageBlock.author == charSequence) {
                if (embedPostAuthorPaint == null) {
                    embedPostAuthorPaint = new TextPaint(1);
                    embedPostAuthorPaint.setColor(getTextColor());
                }
                embedPostAuthorPaint.setTextSize((float) AndroidUtilities.dp(15.0f));
                textPaint = embedPostAuthorPaint;
            } else {
                if (embedPostDatePaint == null) {
                    embedPostDatePaint = new TextPaint(1);
                    if (selectedColor == 0) {
                        embedPostDatePaint.setColor(-7366752);
                    } else if (selectedColor == 1) {
                        embedPostDatePaint.setColor(-11711675);
                    } else if (selectedColor == 2) {
                        embedPostDatePaint.setColor(-10066330);
                    }
                }
                embedPostDatePaint.setTextSize((float) AndroidUtilities.dp(14.0f));
                textPaint = embedPostDatePaint;
            }
        } else if (pageBlock instanceof TLRPC$TL_pageBlockChannel) {
            if (channelNamePaint == null) {
                channelNamePaint = new TextPaint(1);
                channelNamePaint.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            }
            if (this.channelBlock == null) {
                channelNamePaint.setColor(getTextColor());
            } else {
                channelNamePaint.setColor(-1);
            }
            channelNamePaint.setTextSize((float) AndroidUtilities.dp(15.0f));
            textPaint = channelNamePaint;
        } else if (!(pageBlock instanceof TLRPC$TL_pageBlockList) || charSequence == null) {
            textPaint = getTextPaint(richText, richText, pageBlock);
        } else {
            if (listTextPointerPaint == null) {
                listTextPointerPaint = new TextPaint(1);
                listTextPointerPaint.setColor(getTextColor());
            }
            selectedColor = this.selectedFontSize == 0 ? -AndroidUtilities.dp(4.0f) : this.selectedFontSize == 1 ? -AndroidUtilities.dp(2.0f) : this.selectedFontSize == 3 ? AndroidUtilities.dp(2.0f) : this.selectedFontSize == 4 ? AndroidUtilities.dp(4.0f) : 0;
            listTextPointerPaint.setTextSize((float) (selectedColor + AndroidUtilities.dp(15.0f)));
            textPaint = listTextPointerPaint;
        }
        if ((pageBlock instanceof TLRPC$TL_pageBlockPullquote) || !(richText == null || pageBlock == null || (pageBlock instanceof TLRPC$TL_pageBlockBlockquote) || richText != pageBlock.caption)) {
            return new StaticLayout(text, textPaint, i, Alignment.ALIGN_CENTER, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
        }
        return new StaticLayout(text, textPaint, i, Alignment.ALIGN_NORMAL, 1.0f, (float) AndroidUtilities.dp(4.0f), false);
    }

    private void drawContent(Canvas canvas) {
        if (this.photoAnimationInProgress == 1) {
            return;
        }
        if (this.isPhotoVisible || this.photoAnimationInProgress == 2) {
            float f;
            float f2;
            float f3;
            float containerViewHeight;
            ImageReceiver imageReceiver;
            float f4;
            int bitmapWidth;
            int bitmapHeight;
            float containerViewWidth;
            float containerViewHeight2;
            int i;
            int i2;
            Object obj;
            float containerViewWidth2;
            float containerViewHeight3;
            long currentTimeMillis;
            long j;
            int bitmapWidth2;
            int i3;
            int i4;
            float f5 = -1.0f;
            if (this.imageMoveAnimation != null) {
                if (!this.scroller.isFinished()) {
                    this.scroller.abortAnimation();
                }
                f = ((this.animateToScale - this.scale) * this.animationValue) + this.scale;
                f2 = ((this.animateToX - this.translationX) * this.animationValue) + this.translationX;
                f3 = this.translationY + ((this.animateToY - this.translationY) * this.animationValue);
                if (this.animateToScale == 1.0f && this.scale == 1.0f && this.translationX == BitmapDescriptorFactory.HUE_RED) {
                    f5 = f3;
                }
                this.photoContainerView.invalidate();
                float f6 = f;
                f = f2;
                f2 = f3;
                f3 = f6;
            } else {
                if (this.animationStartTime != 0) {
                    this.translationX = this.animateToX;
                    this.translationY = this.animateToY;
                    this.scale = this.animateToScale;
                    this.animationStartTime = 0;
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
                    this.photoContainerView.invalidate();
                }
                if (this.switchImageAfterAnimation != 0) {
                    if (this.switchImageAfterAnimation == 1) {
                        setImageIndex(this.currentIndex + 1, false);
                    } else if (this.switchImageAfterAnimation == 2) {
                        setImageIndex(this.currentIndex - 1, false);
                    }
                    this.switchImageAfterAnimation = 0;
                }
                f3 = this.scale;
                f2 = this.translationY;
                f = this.translationX;
                if (!this.moving) {
                    f5 = this.translationY;
                }
            }
            if (this.scale != 1.0f || f5 == -1.0f || this.zoomAnimation) {
                this.photoBackgroundDrawable.setAlpha(255);
            } else {
                containerViewHeight = ((float) getContainerViewHeight()) / 4.0f;
                this.photoBackgroundDrawable.setAlpha((int) Math.max(127.0f, (1.0f - (Math.min(Math.abs(f5), containerViewHeight) / containerViewHeight)) * 255.0f));
            }
            if (!(this.scale < 1.0f || this.zoomAnimation || this.zooming)) {
                if (f > this.maxX + ((float) AndroidUtilities.dp(5.0f))) {
                    imageReceiver = this.leftImage;
                } else if (f < this.minX - ((float) AndroidUtilities.dp(5.0f))) {
                    imageReceiver = this.rightImage;
                }
                this.changingPage = imageReceiver == null;
                if (imageReceiver == this.rightImage) {
                    containerViewHeight = BitmapDescriptorFactory.HUE_RED;
                    f5 = 1.0f;
                    if (!this.zoomAnimation || f >= this.minX) {
                        f4 = f;
                    } else {
                        f5 = Math.min(1.0f, (this.minX - f) / ((float) canvas.getWidth()));
                        containerViewHeight = (1.0f - f5) * 0.3f;
                        f4 = (float) ((-canvas.getWidth()) - (AndroidUtilities.dp(30.0f) / 2));
                    }
                    if (imageReceiver.hasBitmapImage()) {
                        canvas.save();
                        canvas.translate((float) (getContainerViewWidth() / 2), (float) (getContainerViewHeight() / 2));
                        canvas.translate(((float) (canvas.getWidth() + (AndroidUtilities.dp(30.0f) / 2))) + f4, BitmapDescriptorFactory.HUE_RED);
                        canvas.scale(1.0f - containerViewHeight, 1.0f - containerViewHeight);
                        bitmapWidth = imageReceiver.getBitmapWidth();
                        bitmapHeight = imageReceiver.getBitmapHeight();
                        containerViewWidth = ((float) getContainerViewWidth()) / ((float) bitmapWidth);
                        containerViewHeight2 = ((float) getContainerViewHeight()) / ((float) bitmapHeight);
                        if (containerViewWidth <= containerViewHeight2) {
                            containerViewHeight2 = containerViewWidth;
                        }
                        i = (int) (((float) bitmapWidth) * containerViewHeight2);
                        i2 = (int) (containerViewHeight2 * ((float) bitmapHeight));
                        imageReceiver.setAlpha(f5);
                        imageReceiver.setImageCoords((-i) / 2, (-i2) / 2, i, i2);
                        imageReceiver.draw(canvas);
                        canvas.restore();
                    }
                    canvas.save();
                    canvas.translate(f4, f2 / f3);
                    canvas.translate(((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f))) / 2.0f, (-f2) / f3);
                    this.radialProgressViews[1].setScale(1.0f - containerViewHeight);
                    this.radialProgressViews[1].setAlpha(f5);
                    this.radialProgressViews[1].onDraw(canvas);
                    canvas.restore();
                }
                containerViewHeight = BitmapDescriptorFactory.HUE_RED;
                f5 = 1.0f;
                if (!this.zoomAnimation || f <= this.maxX) {
                    f4 = f;
                } else {
                    f5 = Math.min(1.0f, (f - this.maxX) / ((float) canvas.getWidth()));
                    containerViewHeight = 0.3f * f5;
                    f5 = 1.0f - f5;
                    f4 = this.maxX;
                }
                obj = (this.aspectRatioFrameLayout == null && this.aspectRatioFrameLayout.getVisibility() == 0) ? 1 : null;
                if (this.centerImage.hasBitmapImage()) {
                    canvas.save();
                    canvas.translate((float) (getContainerViewWidth() / 2), (float) (getContainerViewHeight() / 2));
                    canvas.translate(f4, f2);
                    canvas.scale(f3 - containerViewHeight, f3 - containerViewHeight);
                    bitmapWidth = this.centerImage.getBitmapWidth();
                    i = this.centerImage.getBitmapHeight();
                    if (obj != null && this.textureUploaded && Math.abs((((float) bitmapWidth) / ((float) i)) - (((float) this.videoTextureView.getMeasuredWidth()) / ((float) this.videoTextureView.getMeasuredHeight()))) > 0.01f) {
                        bitmapWidth = this.videoTextureView.getMeasuredWidth();
                        i = this.videoTextureView.getMeasuredHeight();
                    }
                    containerViewWidth2 = ((float) getContainerViewWidth()) / ((float) bitmapWidth);
                    containerViewHeight3 = ((float) getContainerViewHeight()) / ((float) i);
                    if (containerViewWidth2 <= containerViewHeight3) {
                        containerViewHeight3 = containerViewWidth2;
                    }
                    bitmapWidth = (int) (((float) bitmapWidth) * containerViewHeight3);
                    i = (int) (((float) i) * containerViewHeight3);
                    if (!(obj != null && this.textureUploaded && this.videoCrossfadeStarted && this.videoCrossfadeAlpha == 1.0f)) {
                        this.centerImage.setAlpha(f5);
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
                        this.videoTextureView.setAlpha(this.videoCrossfadeAlpha * f5);
                        this.aspectRatioFrameLayout.draw(canvas);
                        if (this.videoCrossfadeStarted && this.videoCrossfadeAlpha < 1.0f) {
                            currentTimeMillis = System.currentTimeMillis();
                            j = currentTimeMillis - this.videoCrossfadeAlphaLastTime;
                            this.videoCrossfadeAlphaLastTime = currentTimeMillis;
                            this.videoCrossfadeAlpha += ((float) j) / 300.0f;
                            this.photoContainerView.invalidate();
                            if (this.videoCrossfadeAlpha > 1.0f) {
                                this.videoCrossfadeAlpha = 1.0f;
                            }
                        }
                    }
                    canvas.restore();
                }
                if (obj == null && this.bottomLayout.getVisibility() != 0) {
                    canvas.save();
                    canvas.translate(f4, f2 / f3);
                    this.radialProgressViews[0].setScale(1.0f - containerViewHeight);
                    this.radialProgressViews[0].setAlpha(f5);
                    this.radialProgressViews[0].onDraw(canvas);
                    canvas.restore();
                }
                if (imageReceiver == this.leftImage) {
                    if (imageReceiver.hasBitmapImage()) {
                        canvas.save();
                        canvas.translate((float) (getContainerViewWidth() / 2), (float) (getContainerViewHeight() / 2));
                        canvas.translate(((-((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f)))) / 2.0f) + f, BitmapDescriptorFactory.HUE_RED);
                        bitmapWidth2 = imageReceiver.getBitmapWidth();
                        i2 = imageReceiver.getBitmapHeight();
                        containerViewHeight = ((float) getContainerViewWidth()) / ((float) bitmapWidth2);
                        f5 = ((float) getContainerViewHeight()) / ((float) i2);
                        if (containerViewHeight <= f5) {
                            f5 = containerViewHeight;
                        }
                        i3 = (int) (((float) bitmapWidth2) * f5);
                        i4 = (int) (f5 * ((float) i2));
                        imageReceiver.setAlpha(1.0f);
                        imageReceiver.setImageCoords((-i3) / 2, (-i4) / 2, i3, i4);
                        imageReceiver.draw(canvas);
                        canvas.restore();
                    }
                    canvas.save();
                    canvas.translate(f, f2 / f3);
                    canvas.translate((-((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f)))) / 2.0f, (-f2) / f3);
                    this.radialProgressViews[2].setScale(1.0f);
                    this.radialProgressViews[2].setAlpha(1.0f);
                    this.radialProgressViews[2].onDraw(canvas);
                    canvas.restore();
                }
            }
            imageReceiver = null;
            if (imageReceiver == null) {
            }
            this.changingPage = imageReceiver == null;
            if (imageReceiver == this.rightImage) {
                containerViewHeight = BitmapDescriptorFactory.HUE_RED;
                f5 = 1.0f;
                if (this.zoomAnimation) {
                }
                f4 = f;
                if (imageReceiver.hasBitmapImage()) {
                    canvas.save();
                    canvas.translate((float) (getContainerViewWidth() / 2), (float) (getContainerViewHeight() / 2));
                    canvas.translate(((float) (canvas.getWidth() + (AndroidUtilities.dp(30.0f) / 2))) + f4, BitmapDescriptorFactory.HUE_RED);
                    canvas.scale(1.0f - containerViewHeight, 1.0f - containerViewHeight);
                    bitmapWidth = imageReceiver.getBitmapWidth();
                    bitmapHeight = imageReceiver.getBitmapHeight();
                    containerViewWidth = ((float) getContainerViewWidth()) / ((float) bitmapWidth);
                    containerViewHeight2 = ((float) getContainerViewHeight()) / ((float) bitmapHeight);
                    if (containerViewWidth <= containerViewHeight2) {
                        containerViewHeight2 = containerViewWidth;
                    }
                    i = (int) (((float) bitmapWidth) * containerViewHeight2);
                    i2 = (int) (containerViewHeight2 * ((float) bitmapHeight));
                    imageReceiver.setAlpha(f5);
                    imageReceiver.setImageCoords((-i) / 2, (-i2) / 2, i, i2);
                    imageReceiver.draw(canvas);
                    canvas.restore();
                }
                canvas.save();
                canvas.translate(f4, f2 / f3);
                canvas.translate(((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f))) / 2.0f, (-f2) / f3);
                this.radialProgressViews[1].setScale(1.0f - containerViewHeight);
                this.radialProgressViews[1].setAlpha(f5);
                this.radialProgressViews[1].onDraw(canvas);
                canvas.restore();
            }
            containerViewHeight = BitmapDescriptorFactory.HUE_RED;
            f5 = 1.0f;
            if (this.zoomAnimation) {
            }
            f4 = f;
            if (this.aspectRatioFrameLayout == null) {
            }
            if (this.centerImage.hasBitmapImage()) {
                canvas.save();
                canvas.translate((float) (getContainerViewWidth() / 2), (float) (getContainerViewHeight() / 2));
                canvas.translate(f4, f2);
                canvas.scale(f3 - containerViewHeight, f3 - containerViewHeight);
                bitmapWidth = this.centerImage.getBitmapWidth();
                i = this.centerImage.getBitmapHeight();
                bitmapWidth = this.videoTextureView.getMeasuredWidth();
                i = this.videoTextureView.getMeasuredHeight();
                containerViewWidth2 = ((float) getContainerViewWidth()) / ((float) bitmapWidth);
                containerViewHeight3 = ((float) getContainerViewHeight()) / ((float) i);
                if (containerViewWidth2 <= containerViewHeight3) {
                    containerViewHeight3 = containerViewWidth2;
                }
                bitmapWidth = (int) (((float) bitmapWidth) * containerViewHeight3);
                i = (int) (((float) i) * containerViewHeight3);
                this.centerImage.setAlpha(f5);
                this.centerImage.setImageCoords((-bitmapWidth) / 2, (-i) / 2, bitmapWidth, i);
                this.centerImage.draw(canvas);
                if (obj != null) {
                    this.videoCrossfadeStarted = true;
                    this.videoCrossfadeAlpha = BitmapDescriptorFactory.HUE_RED;
                    this.videoCrossfadeAlphaLastTime = System.currentTimeMillis();
                    canvas.translate((float) ((-bitmapWidth) / 2), (float) ((-i) / 2));
                    this.videoTextureView.setAlpha(this.videoCrossfadeAlpha * f5);
                    this.aspectRatioFrameLayout.draw(canvas);
                    currentTimeMillis = System.currentTimeMillis();
                    j = currentTimeMillis - this.videoCrossfadeAlphaLastTime;
                    this.videoCrossfadeAlphaLastTime = currentTimeMillis;
                    this.videoCrossfadeAlpha += ((float) j) / 300.0f;
                    this.photoContainerView.invalidate();
                    if (this.videoCrossfadeAlpha > 1.0f) {
                        this.videoCrossfadeAlpha = 1.0f;
                    }
                }
                canvas.restore();
            }
            canvas.save();
            canvas.translate(f4, f2 / f3);
            this.radialProgressViews[0].setScale(1.0f - containerViewHeight);
            this.radialProgressViews[0].setAlpha(f5);
            this.radialProgressViews[0].onDraw(canvas);
            canvas.restore();
            if (imageReceiver == this.leftImage) {
                if (imageReceiver.hasBitmapImage()) {
                    canvas.save();
                    canvas.translate((float) (getContainerViewWidth() / 2), (float) (getContainerViewHeight() / 2));
                    canvas.translate(((-((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f)))) / 2.0f) + f, BitmapDescriptorFactory.HUE_RED);
                    bitmapWidth2 = imageReceiver.getBitmapWidth();
                    i2 = imageReceiver.getBitmapHeight();
                    containerViewHeight = ((float) getContainerViewWidth()) / ((float) bitmapWidth2);
                    f5 = ((float) getContainerViewHeight()) / ((float) i2);
                    if (containerViewHeight <= f5) {
                        f5 = containerViewHeight;
                    }
                    i3 = (int) (((float) bitmapWidth2) * f5);
                    i4 = (int) (f5 * ((float) i2));
                    imageReceiver.setAlpha(1.0f);
                    imageReceiver.setImageCoords((-i3) / 2, (-i4) / 2, i3, i4);
                    imageReceiver.draw(canvas);
                    canvas.restore();
                }
                canvas.save();
                canvas.translate(f, f2 / f3);
                canvas.translate((-((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f)))) / 2.0f, (-f2) / f3);
                this.radialProgressViews[2].setScale(1.0f);
                this.radialProgressViews[2].setAlpha(1.0f);
                this.radialProgressViews[2].onDraw(canvas);
                canvas.restore();
            }
        }
    }

    private void drawLayoutLink(Canvas canvas, StaticLayout staticLayout) {
        if (canvas != null && this.pressedLinkOwnerLayout == staticLayout) {
            if (this.pressedLink != null) {
                canvas.drawPath(this.urlPath, urlPaint);
            } else if (this.drawBlockSelection) {
                float lineWidth;
                float lineLeft;
                float f;
                if (staticLayout.getLineCount() == 1) {
                    lineWidth = staticLayout.getLineWidth(0);
                    lineLeft = staticLayout.getLineLeft(0);
                    f = lineWidth;
                } else {
                    f = (float) staticLayout.getWidth();
                    lineLeft = BitmapDescriptorFactory.HUE_RED;
                }
                lineWidth = ((float) (-AndroidUtilities.dp(2.0f))) + lineLeft;
                lineLeft += f;
                canvas.drawRect(lineWidth, BitmapDescriptorFactory.HUE_RED, ((float) AndroidUtilities.dp(2.0f)) + lineLeft, (float) staticLayout.getHeight(), urlPaint);
            }
        }
    }

    private int getContainerViewHeight() {
        return this.photoContainerView.getHeight();
    }

    private int getContainerViewWidth() {
        return this.photoContainerView.getWidth();
    }

    private Document getDocumentWithId(long j) {
        if (this.currentPage == null || this.currentPage.cached_page == null) {
            return null;
        }
        if (this.currentPage.document != null && this.currentPage.document.id == j) {
            return this.currentPage.document;
        }
        for (int i = 0; i < this.currentPage.cached_page.documents.size(); i++) {
            Document document = (Document) this.currentPage.cached_page.documents.get(i);
            if (document.id == j) {
                return document;
            }
        }
        return null;
    }

    private FileLocation getFileLocation(int i, int[] iArr) {
        if (i < 0 || i >= this.imagesArr.size()) {
            return null;
        }
        TLObject media = getMedia(i);
        if (media instanceof Photo) {
            PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(((Photo) media).sizes, AndroidUtilities.getPhotoSize());
            if (closestPhotoSizeWithSize != null) {
                iArr[0] = closestPhotoSizeWithSize.size;
                if (iArr[0] == 0) {
                    iArr[0] = -1;
                }
                return closestPhotoSizeWithSize.location;
            }
            iArr[0] = -1;
        } else if (media instanceof Document) {
            Document document = (Document) media;
            if (document.thumb != null) {
                iArr[0] = document.thumb.size;
                if (iArr[0] == 0) {
                    iArr[0] = -1;
                }
                return document.thumb.location;
            }
        }
        return null;
    }

    private String getFileName(int i) {
        TLObject media = getMedia(i);
        if (media instanceof Photo) {
            media = FileLoader.getClosestPhotoSizeWithSize(((Photo) media).sizes, AndroidUtilities.getPhotoSize());
        }
        return FileLoader.getAttachFileName(media);
    }

    private int getGrayTextColor() {
        switch (getSelectedColor()) {
            case 0:
                return -8156010;
            case 1:
                return -11711675;
            default:
                return -10066330;
        }
    }

    private ImageReceiver getImageReceiverFromListView(ViewGroup viewGroup, PageBlock pageBlock, int[] iArr) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof BlockPhotoCell) {
                BlockPhotoCell blockPhotoCell = (BlockPhotoCell) childAt;
                if (blockPhotoCell.currentBlock == pageBlock) {
                    childAt.getLocationInWindow(iArr);
                    return blockPhotoCell.imageView;
                }
            } else if (childAt instanceof BlockVideoCell) {
                BlockVideoCell blockVideoCell = (BlockVideoCell) childAt;
                if (blockVideoCell.currentBlock == pageBlock) {
                    childAt.getLocationInWindow(iArr);
                    return blockVideoCell.imageView;
                }
            } else if (childAt instanceof BlockCollageCell) {
                r0 = getImageReceiverFromListView(((BlockCollageCell) childAt).innerListView, pageBlock, iArr);
                if (r0 != null) {
                    return r0;
                }
            } else if (childAt instanceof BlockSlideshowCell) {
                r0 = getImageReceiverFromListView(((BlockSlideshowCell) childAt).innerListView, pageBlock, iArr);
                if (r0 != null) {
                    return r0;
                }
            } else {
                continue;
            }
        }
        return null;
    }

    public static ArticleViewer getInstance() {
        ArticleViewer articleViewer = Instance;
        if (articleViewer == null) {
            synchronized (ArticleViewer.class) {
                articleViewer = Instance;
                if (articleViewer == null) {
                    articleViewer = new ArticleViewer();
                    Instance = articleViewer;
                }
            }
        }
        return articleViewer;
    }

    private TLObject getMedia(int i) {
        if (this.imagesArr.isEmpty() || i >= this.imagesArr.size() || i < 0) {
            return null;
        }
        PageBlock pageBlock = (PageBlock) this.imagesArr.get(i);
        return pageBlock.photo_id != 0 ? getPhotoWithId(pageBlock.photo_id) : pageBlock.video_id != 0 ? getDocumentWithId(pageBlock.video_id) : null;
    }

    private File getMediaFile(int i) {
        if (this.imagesArr.isEmpty() || i >= this.imagesArr.size() || i < 0) {
            return null;
        }
        PageBlock pageBlock = (PageBlock) this.imagesArr.get(i);
        TLObject closestPhotoSizeWithSize;
        if (pageBlock.photo_id != 0) {
            Photo photoWithId = getPhotoWithId(pageBlock.photo_id);
            if (photoWithId != null) {
                closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(photoWithId.sizes, AndroidUtilities.getPhotoSize());
                if (closestPhotoSizeWithSize != null) {
                    return FileLoader.getPathToAttach(closestPhotoSizeWithSize, true);
                }
            }
        } else if (pageBlock.video_id != 0) {
            closestPhotoSizeWithSize = getDocumentWithId(pageBlock.video_id);
            if (closestPhotoSizeWithSize != null) {
                return FileLoader.getPathToAttach(closestPhotoSizeWithSize, true);
            }
        }
        return null;
    }

    private Photo getPhotoWithId(long j) {
        if (this.currentPage == null || this.currentPage.cached_page == null) {
            return null;
        }
        if (this.currentPage.photo != null && this.currentPage.photo.id == j) {
            return this.currentPage.photo;
        }
        for (int i = 0; i < this.currentPage.cached_page.photos.size(); i++) {
            Photo photo = (Photo) this.currentPage.cached_page.photos.get(i);
            if (photo.id == j) {
                return photo;
            }
        }
        return null;
    }

    private PlaceProviderObject getPlaceForPhoto(PageBlock pageBlock) {
        ImageReceiver imageReceiverFromListView = getImageReceiverFromListView(this.listView, pageBlock, this.coords);
        if (imageReceiverFromListView == null) {
            return null;
        }
        PlaceProviderObject placeProviderObject = new PlaceProviderObject();
        placeProviderObject.viewX = this.coords[0];
        placeProviderObject.viewY = this.coords[1];
        placeProviderObject.parentView = this.listView;
        placeProviderObject.imageReceiver = imageReceiverFromListView;
        placeProviderObject.thumb = imageReceiverFromListView.getBitmap();
        placeProviderObject.radius = imageReceiverFromListView.getRoundRadius();
        placeProviderObject.clipTopAddition = this.currentHeaderHeight;
        return placeProviderObject;
    }

    private int getSelectedColor() {
        int i = this.selectedColor;
        if (this.nightModeEnabled && i != 2) {
            int i2 = Calendar.getInstance().get(11);
            if (i2 >= 22 && i2 <= 24) {
                return 2;
            }
            if (i2 >= 0 && i2 <= 6) {
                return 2;
            }
        }
        return i;
    }

    private CharSequence getText(RichText richText, RichText richText2, PageBlock pageBlock) {
        TextPaint textPaint = null;
        int i = 0;
        if (richText2 instanceof TLRPC$TL_textFixed) {
            return getText(richText, ((TLRPC$TL_textFixed) richText2).text, pageBlock);
        }
        if (richText2 instanceof TLRPC$TL_textItalic) {
            return getText(richText, ((TLRPC$TL_textItalic) richText2).text, pageBlock);
        }
        if (richText2 instanceof TLRPC$TL_textBold) {
            return getText(richText, ((TLRPC$TL_textBold) richText2).text, pageBlock);
        }
        if (richText2 instanceof TLRPC$TL_textUnderline) {
            return getText(richText, ((TLRPC$TL_textUnderline) richText2).text, pageBlock);
        }
        if (richText2 instanceof TLRPC$TL_textStrike) {
            return getText(richText, ((TLRPC$TL_textStrike) richText2).text, pageBlock);
        }
        SpannableStringBuilder spannableStringBuilder;
        MetricAffectingSpan[] metricAffectingSpanArr;
        if (richText2 instanceof TLRPC$TL_textEmail) {
            spannableStringBuilder = new SpannableStringBuilder(getText(richText, ((TLRPC$TL_textEmail) richText2).text, pageBlock));
            metricAffectingSpanArr = (MetricAffectingSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), MetricAffectingSpan.class);
            TextPaint textPaint2 = (metricAffectingSpanArr == null || metricAffectingSpanArr.length == 0) ? getTextPaint(richText, richText2, pageBlock) : null;
            spannableStringBuilder.setSpan(new TextPaintUrlSpan(textPaint2, getUrl(richText2)), 0, spannableStringBuilder.length(), 33);
            return spannableStringBuilder;
        } else if (richText2 instanceof TLRPC$TL_textUrl) {
            spannableStringBuilder = new SpannableStringBuilder(getText(richText, ((TLRPC$TL_textUrl) richText2).text, pageBlock));
            metricAffectingSpanArr = (MetricAffectingSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), MetricAffectingSpan.class);
            if (metricAffectingSpanArr == null || metricAffectingSpanArr.length == 0) {
                textPaint = getTextPaint(richText, richText2, pageBlock);
            }
            spannableStringBuilder.setSpan(new TextPaintUrlSpan(textPaint, getUrl(richText2)), 0, spannableStringBuilder.length(), 33);
            return spannableStringBuilder;
        } else if (richText2 instanceof TLRPC$TL_textPlain) {
            return ((TLRPC$TL_textPlain) richText2).text;
        } else {
            if (richText2 instanceof TLRPC$TL_textEmpty) {
                return TtmlNode.ANONYMOUS_REGION_ID;
            }
            if (!(richText2 instanceof TLRPC$TL_textConcat)) {
                return "not supported " + richText2;
            }
            spannableStringBuilder = new SpannableStringBuilder();
            int size = richText2.texts.size();
            while (i < size) {
                RichText richText3 = (RichText) richText2.texts.get(i);
                CharSequence text = getText(richText, richText3, pageBlock);
                int textFlags = getTextFlags(richText3);
                int length = spannableStringBuilder.length();
                spannableStringBuilder.append(text);
                if (!(textFlags == 0 || (text instanceof SpannableStringBuilder))) {
                    if ((textFlags & 8) != 0) {
                        String url = getUrl(richText3);
                        if (url == null) {
                            url = getUrl(richText);
                        }
                        spannableStringBuilder.setSpan(new TextPaintUrlSpan(getTextPaint(richText, richText3, pageBlock), url), length, spannableStringBuilder.length(), 33);
                    } else {
                        spannableStringBuilder.setSpan(new TextPaintSpan(getTextPaint(richText, richText3, pageBlock)), length, spannableStringBuilder.length(), 33);
                    }
                }
                i++;
            }
            return spannableStringBuilder;
        }
    }

    private int getTextColor() {
        switch (getSelectedColor()) {
            case 0:
            case 1:
                return -14606047;
            default:
                return -6710887;
        }
    }

    private int getTextFlags(RichText richText) {
        return richText instanceof TLRPC$TL_textFixed ? getTextFlags(richText.parentRichText) | 4 : richText instanceof TLRPC$TL_textItalic ? getTextFlags(richText.parentRichText) | 2 : richText instanceof TLRPC$TL_textBold ? getTextFlags(richText.parentRichText) | 1 : richText instanceof TLRPC$TL_textUnderline ? getTextFlags(richText.parentRichText) | 16 : richText instanceof TLRPC$TL_textStrike ? getTextFlags(richText.parentRichText) | 32 : richText instanceof TLRPC$TL_textEmail ? getTextFlags(richText.parentRichText) | 8 : richText instanceof TLRPC$TL_textUrl ? getTextFlags(richText.parentRichText) | 8 : richText != null ? getTextFlags(richText.parentRichText) : 0;
    }

    private TextPaint getTextPaint(RichText richText, RichText richText2, PageBlock pageBlock) {
        int dp;
        int i;
        HashMap hashMap;
        int textFlags = getTextFlags(richText2);
        int dp2 = AndroidUtilities.dp(14.0f);
        int i2 = -65536;
        int dp3 = this.selectedFontSize == 0 ? -AndroidUtilities.dp(4.0f) : this.selectedFontSize == 1 ? -AndroidUtilities.dp(2.0f) : this.selectedFontSize == 3 ? AndroidUtilities.dp(2.0f) : this.selectedFontSize == 4 ? AndroidUtilities.dp(4.0f) : 0;
        HashMap hashMap2;
        if (pageBlock instanceof TLRPC$TL_pageBlockPhoto) {
            hashMap2 = captionTextPaints;
            dp = AndroidUtilities.dp(14.0f);
            i2 = getGrayTextColor();
            i = dp;
            hashMap = hashMap2;
        } else if (pageBlock instanceof TLRPC$TL_pageBlockTitle) {
            hashMap2 = titleTextPaints;
            dp = AndroidUtilities.dp(24.0f);
            i2 = getTextColor();
            i = dp;
            hashMap = hashMap2;
        } else if (pageBlock instanceof TLRPC$TL_pageBlockAuthorDate) {
            hashMap2 = authorTextPaints;
            dp = AndroidUtilities.dp(14.0f);
            i2 = getGrayTextColor();
            i = dp;
            hashMap = hashMap2;
        } else if (pageBlock instanceof TLRPC$TL_pageBlockFooter) {
            hashMap2 = footerTextPaints;
            dp = AndroidUtilities.dp(14.0f);
            i2 = getGrayTextColor();
            i = dp;
            hashMap = hashMap2;
        } else if (pageBlock instanceof TLRPC$TL_pageBlockSubtitle) {
            hashMap2 = subtitleTextPaints;
            dp = AndroidUtilities.dp(21.0f);
            i2 = getTextColor();
            i = dp;
            hashMap = hashMap2;
        } else if (pageBlock instanceof TLRPC$TL_pageBlockHeader) {
            hashMap2 = headerTextPaints;
            dp = AndroidUtilities.dp(21.0f);
            i2 = getTextColor();
            i = dp;
            hashMap = hashMap2;
        } else if (pageBlock instanceof TLRPC$TL_pageBlockSubheader) {
            hashMap2 = subheaderTextPaints;
            dp = AndroidUtilities.dp(18.0f);
            i2 = getTextColor();
            i = dp;
            hashMap = hashMap2;
        } else {
            if ((pageBlock instanceof TLRPC$TL_pageBlockBlockquote) || (pageBlock instanceof TLRPC$TL_pageBlockPullquote)) {
                if (pageBlock.text == richText) {
                    hashMap2 = quoteTextPaints;
                    dp = AndroidUtilities.dp(15.0f);
                    i2 = getTextColor();
                    i = dp;
                    hashMap = hashMap2;
                } else if (pageBlock.caption == richText) {
                    hashMap2 = subquoteTextPaints;
                    dp = AndroidUtilities.dp(14.0f);
                    i2 = getGrayTextColor();
                    i = dp;
                    hashMap = hashMap2;
                }
            } else if (pageBlock instanceof TLRPC$TL_pageBlockPreformatted) {
                hashMap2 = preformattedTextPaints;
                dp = AndroidUtilities.dp(14.0f);
                i2 = getTextColor();
                i = dp;
                hashMap = hashMap2;
            } else if (pageBlock instanceof TLRPC$TL_pageBlockParagraph) {
                if (pageBlock.caption == richText) {
                    hashMap2 = embedPostCaptionTextPaints;
                    dp = AndroidUtilities.dp(14.0f);
                    i2 = getGrayTextColor();
                    i = dp;
                    hashMap = hashMap2;
                } else {
                    hashMap2 = paragraphTextPaints;
                    dp = AndroidUtilities.dp(16.0f);
                    i2 = getTextColor();
                    i = dp;
                    hashMap = hashMap2;
                }
            } else if (pageBlock instanceof TLRPC$TL_pageBlockList) {
                hashMap2 = listTextPaints;
                dp = AndroidUtilities.dp(15.0f);
                i2 = getTextColor();
                i = dp;
                hashMap = hashMap2;
            } else if (pageBlock instanceof TLRPC$TL_pageBlockEmbed) {
                hashMap2 = embedTextPaints;
                dp = AndroidUtilities.dp(14.0f);
                i2 = getGrayTextColor();
                i = dp;
                hashMap = hashMap2;
            } else if (pageBlock instanceof TLRPC$TL_pageBlockSlideshow) {
                hashMap2 = slideshowTextPaints;
                dp = AndroidUtilities.dp(14.0f);
                i2 = getGrayTextColor();
                i = dp;
                hashMap = hashMap2;
            } else if (pageBlock instanceof TLRPC$TL_pageBlockEmbedPost) {
                if (richText2 != null) {
                    hashMap2 = embedPostTextPaints;
                    dp = AndroidUtilities.dp(14.0f);
                    i2 = getTextColor();
                    i = dp;
                    hashMap = hashMap2;
                }
            } else if ((pageBlock instanceof TLRPC$TL_pageBlockVideo) || (pageBlock instanceof TLRPC$TL_pageBlockAudio)) {
                hashMap2 = videoTextPaints;
                dp = AndroidUtilities.dp(14.0f);
                i2 = getTextColor();
                i = dp;
                hashMap = hashMap2;
            }
            hashMap = null;
            i = dp2;
        }
        if (hashMap == null) {
            if (errorTextPaint == null) {
                errorTextPaint = new TextPaint(1);
                errorTextPaint.setColor(-65536);
            }
            errorTextPaint.setTextSize((float) AndroidUtilities.dp(14.0f));
            return errorTextPaint;
        }
        TextPaint textPaint = (TextPaint) hashMap.get(Integer.valueOf(textFlags));
        if (textPaint == null) {
            TextPaint textPaint2 = new TextPaint(1);
            if ((textFlags & 4) != 0) {
                textPaint2.setTypeface(AndroidUtilities.getTypeface("fonts/rmono.ttf"));
            } else if (this.selectedFont == 1 || (pageBlock instanceof TLRPC$TL_pageBlockTitle) || (pageBlock instanceof TLRPC$TL_pageBlockHeader) || (pageBlock instanceof TLRPC$TL_pageBlockSubtitle) || (pageBlock instanceof TLRPC$TL_pageBlockSubheader)) {
                if ((textFlags & 1) != 0 && (textFlags & 2) != 0) {
                    textPaint2.setTypeface(Typeface.create(C3446C.SERIF_NAME, 3));
                } else if ((textFlags & 1) != 0) {
                    textPaint2.setTypeface(Typeface.create(C3446C.SERIF_NAME, 1));
                } else if ((textFlags & 2) != 0) {
                    textPaint2.setTypeface(Typeface.create(C3446C.SERIF_NAME, 2));
                } else {
                    textPaint2.setTypeface(Typeface.create(C3446C.SERIF_NAME, 0));
                }
            } else if ((textFlags & 1) != 0 && (textFlags & 2) != 0) {
                textPaint2.setTypeface(AndroidUtilities.getTypeface("fonts/rmediumitalic.ttf"));
            } else if ((textFlags & 1) != 0) {
                textPaint2.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            } else if ((textFlags & 2) != 0) {
                textPaint2.setTypeface(AndroidUtilities.getTypeface("fonts/ritalic.ttf"));
            }
            if ((textFlags & 32) != 0) {
                textPaint2.setFlags(textPaint2.getFlags() | 16);
            }
            if ((textFlags & 16) != 0) {
                textPaint2.setFlags(textPaint2.getFlags() | 8);
            }
            if ((textFlags & 8) != 0) {
                textPaint2.setFlags(textPaint2.getFlags() | 8);
                dp = getTextColor();
            } else {
                dp = i2;
            }
            textPaint2.setColor(dp);
            hashMap.put(Integer.valueOf(textFlags), textPaint2);
            textPaint = textPaint2;
        }
        textPaint.setTextSize((float) (dp3 + i));
        return textPaint;
    }

    private String getUrl(RichText richText) {
        return richText instanceof TLRPC$TL_textFixed ? getUrl(((TLRPC$TL_textFixed) richText).text) : richText instanceof TLRPC$TL_textItalic ? getUrl(((TLRPC$TL_textItalic) richText).text) : richText instanceof TLRPC$TL_textBold ? getUrl(((TLRPC$TL_textBold) richText).text) : richText instanceof TLRPC$TL_textUnderline ? getUrl(((TLRPC$TL_textUnderline) richText).text) : richText instanceof TLRPC$TL_textStrike ? getUrl(((TLRPC$TL_textStrike) richText).text) : richText instanceof TLRPC$TL_textEmail ? ((TLRPC$TL_textEmail) richText).email : richText instanceof TLRPC$TL_textUrl ? ((TLRPC$TL_textUrl) richText).url : null;
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

    private void hideActionBar() {
        AnimatorSet animatorSet = new AnimatorSet();
        r1 = new Animator[3];
        r1[0] = ObjectAnimator.ofFloat(this.backButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        r1[1] = ObjectAnimator.ofFloat(this.shareContainer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        r1[2] = ObjectAnimator.ofFloat(this.settingsButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        animatorSet.playTogether(r1);
        animatorSet.setDuration(250);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
    }

    private boolean isMediaVideo(int i) {
        return !this.imagesArr.isEmpty() && i < this.imagesArr.size() && i >= 0 && isVideoBlock((PageBlock) this.imagesArr.get(i));
    }

    private boolean isVideoBlock(PageBlock pageBlock) {
        if (!(pageBlock == null || pageBlock.video_id == 0)) {
            Document documentWithId = getDocumentWithId(pageBlock.video_id);
            if (documentWithId != null) {
                return MessageObject.isVideoDocument(documentWithId);
            }
        }
        return false;
    }

    private void joinChannel(final BlockChannelCell blockChannelCell, final Chat chat) {
        final TLObject tL_channels_joinChannel = new TL_channels_joinChannel();
        tL_channels_joinChannel.channel = MessagesController.getInputChannel(chat);
        ConnectionsManager.getInstance().sendRequest(tL_channels_joinChannel, new RequestDelegate() {

            /* renamed from: org.telegram.ui.ArticleViewer$37$2 */
            class C39142 implements Runnable {
                C39142() {
                }

                public void run() {
                    blockChannelCell.setState(2, false);
                }
            }

            /* renamed from: org.telegram.ui.ArticleViewer$37$3 */
            class C39153 implements Runnable {
                C39153() {
                }

                public void run() {
                    MessagesController.getInstance().loadFullChat(chat.id, 0, true);
                }
            }

            public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error != null) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            blockChannelCell.setState(0, false);
                            AlertsCreator.processError(tLRPC$TL_error, ArticleViewer.this.parentFragment, tL_channels_joinChannel, Boolean.valueOf(true));
                        }
                    });
                    return;
                }
                boolean z;
                TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                for (int i = 0; i < tLRPC$Updates.updates.size(); i++) {
                    TLRPC$Update tLRPC$Update = (TLRPC$Update) tLRPC$Updates.updates.get(i);
                    if ((tLRPC$Update instanceof TLRPC$TL_updateNewChannelMessage) && (((TLRPC$TL_updateNewChannelMessage) tLRPC$Update).message.action instanceof TLRPC$TL_messageActionChatAddUser)) {
                        z = true;
                        break;
                    }
                }
                z = false;
                MessagesController.getInstance().processUpdates(tLRPC$Updates, false);
                if (!z) {
                    MessagesController.getInstance().generateJoinMessage(chat.id, true);
                }
                AndroidUtilities.runOnUIThread(new C39142());
                AndroidUtilities.runOnUIThread(new C39153(), 1000);
                MessagesStorage.getInstance().updateDialogsWithDeletedMessages(new ArrayList(), null, true, chat.id);
            }
        });
    }

    private void loadChannel(final BlockChannelCell blockChannelCell, Chat chat) {
        if (!this.loadingChannel && !TextUtils.isEmpty(chat.username)) {
            this.loadingChannel = true;
            TLObject tLRPC$TL_contacts_resolveUsername = new TLRPC$TL_contacts_resolveUsername();
            tLRPC$TL_contacts_resolveUsername.username = chat.username;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_resolveUsername, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            ArticleViewer.this.loadingChannel = false;
                            if (ArticleViewer.this.parentFragment != null && ArticleViewer.this.blocks != null && !ArticleViewer.this.blocks.isEmpty()) {
                                if (tLRPC$TL_error == null) {
                                    TLRPC$TL_contacts_resolvedPeer tLRPC$TL_contacts_resolvedPeer = (TLRPC$TL_contacts_resolvedPeer) tLObject;
                                    if (tLRPC$TL_contacts_resolvedPeer.chats.isEmpty()) {
                                        blockChannelCell.setState(4, false);
                                        return;
                                    }
                                    MessagesController.getInstance().putUsers(tLRPC$TL_contacts_resolvedPeer.users, false);
                                    MessagesController.getInstance().putChats(tLRPC$TL_contacts_resolvedPeer.chats, false);
                                    MessagesStorage.getInstance().putUsersAndChats(tLRPC$TL_contacts_resolvedPeer.users, tLRPC$TL_contacts_resolvedPeer.chats, false, true);
                                    ArticleViewer.this.loadedChannel = (Chat) tLRPC$TL_contacts_resolvedPeer.chats.get(0);
                                    if (!ArticleViewer.this.loadedChannel.left || ArticleViewer.this.loadedChannel.kicked) {
                                        blockChannelCell.setState(4, false);
                                        return;
                                    } else {
                                        blockChannelCell.setState(0, false);
                                        return;
                                    }
                                }
                                blockChannelCell.setState(4, false);
                            }
                        }
                    });
                }
            });
        }
    }

    private void onActionClick(boolean z) {
        File file = null;
        TLObject media = getMedia(this.currentIndex);
        if ((media instanceof Document) && this.currentFileNames[0] != null) {
            Document document = (Document) media;
            if (this.currentMedia != null) {
                File mediaFile = getMediaFile(this.currentIndex);
                if (mediaFile == null || mediaFile.exists()) {
                    file = mediaFile;
                }
            }
            if (file != null) {
                preparePlayer(file, true);
            } else if (!z) {
            } else {
                if (FileLoader.getInstance().isLoadingFile(this.currentFileNames[0])) {
                    FileLoader.getInstance().cancelLoadFile(document);
                } else {
                    FileLoader.getInstance().loadFile(document, true, 1);
                }
            }
        }
    }

    private void onClosed() {
        this.isVisible = false;
        this.currentPage = null;
        this.blocks.clear();
        this.photoBlocks.clear();
        this.adapter.notifyDataSetChanged();
        try {
            this.parentActivity.getWindow().clearFlags(128);
        } catch (Throwable e) {
            FileLog.e(e);
        }
        for (int i = 0; i < this.createdWebViews.size(); i++) {
            ((BlockEmbedCell) this.createdWebViews.get(i)).destroyWebView(false);
        }
        this.containerView.post(new Runnable() {
            public void run() {
                try {
                    if (ArticleViewer.this.windowView.getParent() != null) {
                        ((WindowManager) ArticleViewer.this.parentActivity.getSystemService("window")).removeView(ArticleViewer.this.windowView);
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private void onPhotoClosed(PlaceProviderObject placeProviderObject) {
        this.isPhotoVisible = false;
        this.disableShowCheck = true;
        this.currentMedia = null;
        this.currentThumb = null;
        if (this.currentAnimation != null) {
            this.currentAnimation.setSecondParentView(null);
            this.currentAnimation = null;
        }
        for (int i = 0; i < 3; i++) {
            if (this.radialProgressViews[i] != null) {
                this.radialProgressViews[i].setBackgroundState(-1, false);
            }
        }
        this.centerImage.setImageBitmap((Bitmap) null);
        this.leftImage.setImageBitmap((Bitmap) null);
        this.rightImage.setImageBitmap((Bitmap) null);
        this.photoContainerView.post(new Runnable() {
            public void run() {
                ArticleViewer.this.animatingImageView.setImageBitmap(null);
            }
        });
        this.disableShowCheck = false;
        if (placeProviderObject != null) {
            placeProviderObject.imageReceiver.setVisible(true, true);
        }
    }

    private void onPhotoShow(int i, PlaceProviderObject placeProviderObject) {
        this.currentIndex = -1;
        this.currentFileNames[0] = null;
        this.currentFileNames[1] = null;
        this.currentFileNames[2] = null;
        this.currentThumb = placeProviderObject != null ? placeProviderObject.thumb : null;
        this.menuItem.setVisibility(0);
        this.menuItem.hideSubItem(3);
        this.actionBar.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        this.captionTextView.setTag(null);
        this.captionTextView.setVisibility(4);
        for (int i2 = 0; i2 < 3; i2++) {
            if (this.radialProgressViews[i2] != null) {
                this.radialProgressViews[i2].setBackgroundState(-1, false);
            }
        }
        setImageIndex(i, true);
        if (this.currentMedia != null && isMediaVideo(this.currentIndex)) {
            onActionClick(false);
        }
    }

    private void onSharePressed() {
        if (this.parentActivity != null && this.currentMedia != null) {
            try {
                File mediaFile = getMediaFile(this.currentIndex);
                if (mediaFile == null || !mediaFile.exists()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.parentActivity);
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                    builder.setMessage(LocaleController.getString("PleaseDownload", R.string.PleaseDownload));
                    showDialog(builder.create());
                    return;
                }
                Intent intent = new Intent("android.intent.action.SEND");
                if (isMediaVideo(this.currentIndex)) {
                    intent.setType(MimeTypes.VIDEO_MP4);
                } else {
                    intent.setType("image/jpeg");
                }
                if (VERSION.SDK_INT >= 24) {
                    try {
                        intent.putExtra("android.intent.extra.STREAM", FileProvider.a(this.parentActivity, "org.ir.talaeii.provider", mediaFile));
                        intent.setFlags(1);
                    } catch (Exception e) {
                        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(mediaFile));
                    }
                } else {
                    intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(mediaFile));
                }
                this.parentActivity.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("ShareFile", R.string.ShareFile)), ChatActivity.startAllServices);
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
        }
    }

    private boolean open(final MessageObject messageObject, TLRPC$WebPage tLRPC$WebPage, String str, boolean z) {
        if (this.parentActivity == null || ((this.isVisible && !this.collapsed) || (messageObject == null && tLRPC$WebPage == null))) {
            return false;
        }
        final TLRPC$WebPage tLRPC$WebPage2 = messageObject != null ? messageObject.messageOwner.media.webpage : tLRPC$WebPage;
        if (z) {
            TLObject tLRPC$TL_messages_getWebPage = new TLRPC$TL_messages_getWebPage();
            tLRPC$TL_messages_getWebPage.url = tLRPC$WebPage2.url;
            if (tLRPC$WebPage2.cached_page instanceof TLRPC$TL_pagePart) {
                tLRPC$TL_messages_getWebPage.hash = 0;
            } else {
                tLRPC$TL_messages_getWebPage.hash = tLRPC$WebPage2.hash;
            }
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getWebPage, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLObject instanceof TLRPC$TL_webPage) {
                        final TLRPC$TL_webPage tLRPC$TL_webPage = (TLRPC$TL_webPage) tLObject;
                        if (tLRPC$TL_webPage.cached_page != null) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (!ArticleViewer.this.pagesStack.isEmpty() && ArticleViewer.this.pagesStack.get(0) == tLRPC$WebPage2 && tLRPC$TL_webPage.cached_page != null) {
                                        if (messageObject != null) {
                                            messageObject.messageOwner.media.webpage = tLRPC$TL_webPage;
                                        }
                                        ArticleViewer.this.pagesStack.set(0, tLRPC$TL_webPage);
                                        if (ArticleViewer.this.pagesStack.size() == 1) {
                                            ArticleViewer.this.currentPage = tLRPC$TL_webPage;
                                            ApplicationLoader.applicationContext.getSharedPreferences("articles", 0).edit().remove("article" + ArticleViewer.this.currentPage.id).commit();
                                            ArticleViewer.this.updateInterfaceForCurrentPage(false);
                                        }
                                    }
                                }
                            });
                            HashMap hashMap = new HashMap();
                            hashMap.put(Long.valueOf(tLRPC$TL_webPage.id), tLRPC$TL_webPage);
                            MessagesStorage.getInstance().putWebPages(hashMap);
                        }
                    }
                }
            });
        }
        this.pagesStack.clear();
        this.collapsed = false;
        this.backDrawable.setRotation(BitmapDescriptorFactory.HUE_RED, false);
        this.containerView.setTranslationX(BitmapDescriptorFactory.HUE_RED);
        this.containerView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        this.listView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        this.listView.setAlpha(1.0f);
        this.windowView.setInnerTranslationX(BitmapDescriptorFactory.HUE_RED);
        this.actionBar.setVisibility(8);
        this.bottomLayout.setVisibility(8);
        this.captionTextViewNew.setVisibility(8);
        this.captionTextViewOld.setVisibility(8);
        this.shareContainer.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.backButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.settingsButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.layoutManager.scrollToPositionWithOffset(0, 0);
        checkScroll(-AndroidUtilities.dp(56.0f));
        String str2 = null;
        int i;
        if (messageObject != null) {
            String toLowerCase;
            TLRPC$WebPage tLRPC$WebPage3 = messageObject.messageOwner.media.webpage;
            Object toLowerCase2 = tLRPC$WebPage3.url.toLowerCase();
            for (i = 0; i < messageObject.messageOwner.entities.size(); i++) {
                MessageEntity messageEntity = (MessageEntity) messageObject.messageOwner.entities.get(i);
                if (messageEntity instanceof TLRPC$TL_messageEntityUrl) {
                    try {
                        toLowerCase = messageObject.messageOwner.message.substring(messageEntity.offset, messageEntity.length + messageEntity.offset).toLowerCase();
                        if (toLowerCase.contains(toLowerCase2) || toLowerCase2.contains(toLowerCase)) {
                            int lastIndexOf = toLowerCase.lastIndexOf(35);
                            if (lastIndexOf != -1) {
                                toLowerCase = toLowerCase.substring(lastIndexOf + 1);
                                str2 = toLowerCase;
                                tLRPC$WebPage2 = tLRPC$WebPage3;
                            }
                            toLowerCase = null;
                            str2 = toLowerCase;
                            tLRPC$WebPage2 = tLRPC$WebPage3;
                        }
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
            }
            toLowerCase = null;
            str2 = toLowerCase;
            tLRPC$WebPage2 = tLRPC$WebPage3;
        } else if (str != null) {
            i = str.lastIndexOf(35);
            if (i != -1) {
                str2 = str.substring(i + 1);
            }
        }
        addPageToStack(tLRPC$WebPage2, str2);
        this.lastInsets = null;
        if (this.isVisible) {
            LayoutParams layoutParams = this.windowLayoutParams;
            layoutParams.flags &= -17;
            ((WindowManager) this.parentActivity.getSystemService("window")).updateViewLayout(this.windowView, this.windowLayoutParams);
        } else {
            WindowManager windowManager = (WindowManager) this.parentActivity.getSystemService("window");
            if (this.attachedToWindow) {
                try {
                    windowManager.removeView(this.windowView);
                } catch (Exception e2) {
                }
            }
            try {
                if (VERSION.SDK_INT >= 21) {
                    this.windowLayoutParams.flags = -2147417856;
                }
                LayoutParams layoutParams2 = this.windowLayoutParams;
                layoutParams2.flags |= 1032;
                this.windowView.setFocusable(false);
                this.containerView.setFocusable(false);
                windowManager.addView(this.windowView, this.windowLayoutParams);
            } catch (Throwable e3) {
                FileLog.e(e3);
                return false;
            }
        }
        this.isVisible = true;
        this.animationInProgress = 1;
        this.windowView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.containerView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        final AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[3];
        animatorArr[0] = ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
        animatorArr[1] = ObjectAnimator.ofFloat(this.containerView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
        animatorArr[2] = ObjectAnimator.ofFloat(this.windowView, "translationX", new float[]{(float) AndroidUtilities.dp(56.0f), BitmapDescriptorFactory.HUE_RED});
        animatorSet.playTogether(animatorArr);
        this.animationEndRunnable = new Runnable() {
            public void run() {
                if (ArticleViewer.this.containerView != null && ArticleViewer.this.windowView != null) {
                    if (VERSION.SDK_INT >= 18) {
                        ArticleViewer.this.containerView.setLayerType(0, null);
                    }
                    ArticleViewer.this.animationInProgress = 0;
                    AndroidUtilities.hideKeyboard(ArticleViewer.this.parentActivity.getCurrentFocus());
                }
            }
        };
        animatorSet.setDuration(150);
        animatorSet.setInterpolator(this.interpolator);
        animatorSet.addListener(new AnimatorListenerAdapter() {

            /* renamed from: org.telegram.ui.ArticleViewer$26$1 */
            class C39101 implements Runnable {
                C39101() {
                }

                public void run() {
                    NotificationCenter.getInstance().setAnimationInProgress(false);
                    if (ArticleViewer.this.animationEndRunnable != null) {
                        ArticleViewer.this.animationEndRunnable.run();
                        ArticleViewer.this.animationEndRunnable = null;
                    }
                }
            }

            public void onAnimationEnd(Animator animator) {
                AndroidUtilities.runOnUIThread(new C39101());
            }
        });
        this.transitionAnimationStartTime = System.currentTimeMillis();
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                NotificationCenter.getInstance().setAllowedNotificationsDutingAnimation(new int[]{NotificationCenter.dialogsNeedReload, NotificationCenter.closeChats});
                NotificationCenter.getInstance().setAnimationInProgress(true);
                animatorSet.start();
            }
        });
        if (VERSION.SDK_INT >= 18) {
            this.containerView.setLayerType(2, null);
        }
        showActionBar(Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        return true;
    }

    private void openPreviewsChat(User user, long j) {
        if (user != null && this.parentActivity != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("user_id", user.id);
            bundle.putString("botUser", "webpage" + j);
            ((LaunchActivity) this.parentActivity).presentFragment(new ChatActivity(bundle), false, true);
            close(false, true);
        }
    }

    @SuppressLint({"NewApi"})
    private void preparePlayer(File file, boolean z) {
        if (this.parentActivity != null) {
            releasePlayer();
            if (this.videoTextureView == null) {
                this.aspectRatioFrameLayout = new AspectRatioFrameLayout(this.parentActivity);
                this.aspectRatioFrameLayout.setVisibility(4);
                this.photoContainerView.addView(this.aspectRatioFrameLayout, 0, LayoutHelper.createFrame(-1, -1, 17));
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
                        if (!ArticleViewer.this.textureUploaded) {
                            ArticleViewer.this.textureUploaded = true;
                            ArticleViewer.this.containerView.invalidate();
                        }
                    }

                    public void onStateChanged(boolean z, int i) {
                        if (ArticleViewer.this.videoPlayer != null) {
                            if (i == 4 || i == 1) {
                                try {
                                    ArticleViewer.this.parentActivity.getWindow().clearFlags(128);
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                            } else {
                                try {
                                    ArticleViewer.this.parentActivity.getWindow().addFlags(128);
                                } catch (Throwable e2) {
                                    FileLog.e(e2);
                                }
                            }
                            if (i == 3 && ArticleViewer.this.aspectRatioFrameLayout.getVisibility() != 0) {
                                ArticleViewer.this.aspectRatioFrameLayout.setVisibility(0);
                            }
                            if (!ArticleViewer.this.videoPlayer.isPlaying() || i == 4) {
                                if (ArticleViewer.this.isPlaying) {
                                    ArticleViewer.this.isPlaying = false;
                                    ArticleViewer.this.videoPlayButton.setImageResource(R.drawable.inline_video_play);
                                    AndroidUtilities.cancelRunOnUIThread(ArticleViewer.this.updateProgressRunnable);
                                    if (i == 4 && !ArticleViewer.this.videoPlayerSeekbar.isDragging()) {
                                        ArticleViewer.this.videoPlayerSeekbar.setProgress(BitmapDescriptorFactory.HUE_RED);
                                        ArticleViewer.this.videoPlayerControlFrameLayout.invalidate();
                                        ArticleViewer.this.videoPlayer.seekTo(0);
                                        ArticleViewer.this.videoPlayer.pause();
                                    }
                                }
                            } else if (!ArticleViewer.this.isPlaying) {
                                ArticleViewer.this.isPlaying = true;
                                ArticleViewer.this.videoPlayButton.setImageResource(R.drawable.inline_video_pause);
                                AndroidUtilities.runOnUIThread(ArticleViewer.this.updateProgressRunnable);
                            }
                            ArticleViewer.this.updateVideoPlayerTime();
                        }
                    }

                    public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
                        return false;
                    }

                    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                    }

                    public void onVideoSizeChanged(int i, int i2, int i3, float f) {
                        if (ArticleViewer.this.aspectRatioFrameLayout != null) {
                            if (i3 == 90 || i3 == 270) {
                                int i4 = i;
                                i = i2;
                                i2 = i4;
                            }
                            ArticleViewer.this.aspectRatioFrameLayout.setAspectRatio(i2 == 0 ? 1.0f : (((float) i) * f) / ((float) i2), i3);
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
            this.bottomLayout.setVisibility(0);
            this.videoPlayer.setPlayWhenReady(z);
        }
    }

    private boolean processTouchEvent(MotionEvent motionEvent) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (this.photoAnimationInProgress != 0 || this.animationStartTime != 0) {
            return false;
        }
        if (motionEvent.getPointerCount() == 1 && this.gestureDetector.onTouchEvent(motionEvent) && this.doubleTap) {
            this.doubleTap = false;
            this.moving = false;
            this.zooming = false;
            checkMinMax(false);
            return true;
        }
        float y;
        if (motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 5) {
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
            if (this.canZoom && motionEvent.getPointerCount() == 2 && !this.draggingDown && this.zooming && !this.changingPage) {
                this.discardTap = true;
                this.scale = (((float) Math.hypot((double) (motionEvent.getX(1) - motionEvent.getX(0)), (double) (motionEvent.getY(1) - motionEvent.getY(0)))) / this.pinchStartDistance) * this.pinchStartScale;
                this.translationX = (this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - (((this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - this.pinchStartX) * (this.scale / this.pinchStartScale));
                this.translationY = (this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - (((this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - this.pinchStartY) * (this.scale / this.pinchStartScale));
                updateMinMax(this.scale);
                this.photoContainerView.invalidate();
            } else if (motionEvent.getPointerCount() == 1) {
                if (this.velocityTracker != null) {
                    this.velocityTracker.addMovement(motionEvent);
                }
                y = Math.abs(motionEvent.getX() - this.moveStartX);
                r2 = Math.abs(motionEvent.getY() - this.dragY);
                if (y > ((float) AndroidUtilities.dp(3.0f)) || r2 > ((float) AndroidUtilities.dp(3.0f))) {
                    this.discardTap = true;
                }
                if (this.canDragDown && !this.draggingDown && this.scale == 1.0f && r2 >= ((float) AndroidUtilities.dp(30.0f)) && r2 / 2.0f > y) {
                    this.draggingDown = true;
                    this.moving = false;
                    this.dragY = motionEvent.getY();
                    if (this.isActionBarVisible) {
                        toggleActionBar(false, true);
                    }
                    return true;
                } else if (this.draggingDown) {
                    this.translationY = motionEvent.getY() - this.dragY;
                    this.photoContainerView.invalidate();
                } else if (this.invalidCoords || this.animationStartTime != 0) {
                    this.invalidCoords = false;
                    this.moveStartX = motionEvent.getX();
                    this.moveStartY = motionEvent.getY();
                } else {
                    r2 = this.moveStartX - motionEvent.getX();
                    y = this.moveStartY - motionEvent.getY();
                    if (this.moving || ((this.scale == 1.0f && Math.abs(y) + ((float) AndroidUtilities.dp(12.0f)) < Math.abs(r2)) || this.scale != 1.0f)) {
                        if (!this.moving) {
                            this.moving = true;
                            this.canDragDown = false;
                            y = BitmapDescriptorFactory.HUE_RED;
                            r2 = BitmapDescriptorFactory.HUE_RED;
                        }
                        this.moveStartX = motionEvent.getX();
                        this.moveStartY = motionEvent.getY();
                        updateMinMax(this.scale);
                        if ((this.translationX < this.minX && !this.rightImage.hasImage()) || (this.translationX > this.maxX && !this.leftImage.hasImage())) {
                            r2 /= 3.0f;
                        }
                        if (this.maxY != BitmapDescriptorFactory.HUE_RED || this.minY != BitmapDescriptorFactory.HUE_RED) {
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
                        if (this.scale != 1.0f) {
                            this.translationY -= f;
                        }
                        this.photoContainerView.invalidate();
                    }
                }
            }
        } else if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 6) {
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
                    closePhoto(true);
                } else {
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
                if ((this.translationX < this.minX - ((float) (getContainerViewWidth() / 3)) || r1 < ((float) (-AndroidUtilities.dp(650.0f)))) && this.rightImage.hasImage()) {
                    goToNext();
                    return true;
                } else if ((this.translationX > this.maxX + ((float) (getContainerViewWidth() / 3)) || r1 > ((float) AndroidUtilities.dp(650.0f))) && this.leftImage.hasImage()) {
                    goToPrev();
                    return true;
                } else {
                    if (this.translationX < this.minX) {
                        y = this.minX;
                    } else if (this.translationX > this.maxX) {
                        y = this.maxX;
                    }
                    f = this.translationY < this.minY ? this.minY : this.translationY > this.maxY ? this.maxY : r2;
                    animateTo(this.scale, y, f, false);
                }
            }
        }
        return false;
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
            this.photoContainerView.removeView(this.aspectRatioFrameLayout);
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
        this.bottomLayout.setVisibility(8);
    }

    private boolean removeLastPageFromStack() {
        if (this.pagesStack.size() < 2) {
            return false;
        }
        this.pagesStack.remove(this.pagesStack.size() - 1);
        this.currentPage = (TLRPC$WebPage) this.pagesStack.get(this.pagesStack.size() - 1);
        updateInterfaceForCurrentPage(true);
        return true;
    }

    private void saveCurrentPagePosition() {
        boolean z = false;
        if (this.currentPage != null) {
            int findFirstVisibleItemPosition = this.layoutManager.findFirstVisibleItemPosition();
            if (findFirstVisibleItemPosition != -1) {
                View findViewByPosition = this.layoutManager.findViewByPosition(findFirstVisibleItemPosition);
                int top = findViewByPosition != null ? findViewByPosition.getTop() : 0;
                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("articles", 0).edit();
                String str = "article" + this.currentPage.id;
                Editor putInt = edit.putInt(str, findFirstVisibleItemPosition).putInt(str + "o", top);
                String str2 = str + "r";
                if (AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y) {
                    z = true;
                }
                putInt.putBoolean(str2, z).commit();
            }
        }
    }

    private void setCurrentCaption(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.captionTextView.setTextColor(-1);
            this.captionTextView.setTag(null);
            this.captionTextView.setVisibility(4);
            return;
        }
        this.captionTextView = this.captionTextViewOld;
        this.captionTextViewOld = this.captionTextViewNew;
        this.captionTextViewNew = this.captionTextView;
        Theme.createChatResources(null, true);
        CharSequence replaceEmoji = Emoji.replaceEmoji(new SpannableStringBuilder(charSequence.toString()), this.captionTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
        this.captionTextView.setTag(replaceEmoji);
        this.captionTextView.setText(replaceEmoji);
        this.captionTextView.setTextColor(-1);
        this.captionTextView.setAlpha(this.actionBar.getVisibility() == 0 ? 1.0f : BitmapDescriptorFactory.HUE_RED);
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                int i = 4;
                ArticleViewer.this.captionTextViewOld.setTag(null);
                ArticleViewer.this.captionTextViewOld.setVisibility(4);
                TextView access$14300 = ArticleViewer.this.captionTextViewNew;
                if (ArticleViewer.this.actionBar.getVisibility() == 0) {
                    i = 0;
                }
                access$14300.setVisibility(i);
            }
        });
    }

    private void setImageIndex(int i, boolean z) {
        if (this.currentIndex != i) {
            boolean z2;
            boolean z3;
            boolean isMediaVideo;
            if (!z) {
                this.currentThumb = null;
            }
            this.currentFileNames[0] = getFileName(i);
            this.currentFileNames[1] = getFileName(i + 1);
            this.currentFileNames[2] = getFileName(i - 1);
            int i2 = this.currentIndex;
            this.currentIndex = i;
            if (this.imagesArr.isEmpty()) {
                z2 = false;
                z3 = false;
            } else if (this.currentIndex < 0 || this.currentIndex >= this.imagesArr.size()) {
                closePhoto(false);
                return;
            } else {
                PageBlock pageBlock = (PageBlock) this.imagesArr.get(this.currentIndex);
                z2 = this.currentMedia != null && this.currentMedia == pageBlock;
                this.currentMedia = pageBlock;
                isMediaVideo = isMediaVideo(this.currentIndex);
                if (isMediaVideo) {
                    this.menuItem.showSubItem(3);
                }
                setCurrentCaption(getText(this.currentMedia.caption, this.currentMedia.caption, this.currentMedia));
                if (this.currentAnimation != null) {
                    this.menuItem.setVisibility(8);
                    this.menuItem.hideSubItem(1);
                    this.actionBar.setTitle(LocaleController.getString("AttachGif", R.string.AttachGif));
                    z3 = isMediaVideo;
                } else {
                    this.menuItem.setVisibility(0);
                    if (this.imagesArr.size() != 1) {
                        this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(this.currentIndex + 1), Integer.valueOf(this.imagesArr.size())}));
                    } else if (isMediaVideo) {
                        this.actionBar.setTitle(LocaleController.getString("AttachVideo", R.string.AttachVideo));
                    } else {
                        this.actionBar.setTitle(LocaleController.getString("AttachPhoto", R.string.AttachPhoto));
                    }
                    this.menuItem.showSubItem(1);
                    z3 = isMediaVideo;
                }
            }
            int childCount = this.listView.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = this.listView.getChildAt(i3);
                if (childAt instanceof BlockSlideshowCell) {
                    BlockSlideshowCell blockSlideshowCell = (BlockSlideshowCell) childAt;
                    int indexOf = blockSlideshowCell.currentBlock.items.indexOf(this.currentMedia);
                    if (indexOf != -1) {
                        blockSlideshowCell.innerListView.setCurrentItem(indexOf, false);
                        break;
                    }
                }
            }
            if (this.currentPlaceObject != null) {
                if (this.photoAnimationInProgress == 0) {
                    this.currentPlaceObject.imageReceiver.setVisible(true, true);
                } else {
                    this.showAfterAnimation = this.currentPlaceObject;
                }
            }
            this.currentPlaceObject = getPlaceForPhoto(this.currentMedia);
            if (this.currentPlaceObject != null) {
                if (this.photoAnimationInProgress == 0) {
                    this.currentPlaceObject.imageReceiver.setVisible(false, true);
                } else {
                    this.hideAfterAnimation = this.currentPlaceObject;
                }
            }
            if (!z2) {
                this.draggingDown = false;
                this.translationX = BitmapDescriptorFactory.HUE_RED;
                this.translationY = BitmapDescriptorFactory.HUE_RED;
                this.scale = 1.0f;
                this.animateToX = BitmapDescriptorFactory.HUE_RED;
                this.animateToY = BitmapDescriptorFactory.HUE_RED;
                this.animateToScale = 1.0f;
                this.animationStartTime = 0;
                this.imageMoveAnimation = null;
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
                isMediaVideo = (this.currentFileNames[0] == null || z3 || this.radialProgressViews[0].backgroundState == 0) ? false : true;
                this.canZoom = isMediaVideo;
                updateMinMax(this.scale);
            }
            if (i2 == -1) {
                setImages();
                for (int i4 = 0; i4 < 3; i4++) {
                    checkProgress(i4, false);
                }
                return;
            }
            checkProgress(0, false);
            ImageReceiver imageReceiver;
            RadialProgressView radialProgressView;
            if (i2 > this.currentIndex) {
                imageReceiver = this.rightImage;
                this.rightImage = this.centerImage;
                this.centerImage = this.leftImage;
                this.leftImage = imageReceiver;
                radialProgressView = this.radialProgressViews[0];
                this.radialProgressViews[0] = this.radialProgressViews[2];
                this.radialProgressViews[2] = radialProgressView;
                setIndexToImage(this.leftImage, this.currentIndex - 1);
                checkProgress(1, false);
                checkProgress(2, false);
            } else if (i2 < this.currentIndex) {
                imageReceiver = this.leftImage;
                this.leftImage = this.centerImage;
                this.centerImage = this.rightImage;
                this.rightImage = imageReceiver;
                radialProgressView = this.radialProgressViews[0];
                this.radialProgressViews[0] = this.radialProgressViews[1];
                this.radialProgressViews[1] = radialProgressView;
                setIndexToImage(this.rightImage, this.currentIndex + 1);
                checkProgress(1, false);
                checkProgress(2, false);
            }
        }
    }

    private void setImages() {
        if (this.photoAnimationInProgress == 0) {
            setIndexToImage(this.centerImage, this.currentIndex);
            setIndexToImage(this.rightImage, this.currentIndex + 1);
            setIndexToImage(this.leftImage, this.currentIndex - 1);
        }
    }

    private void setIndexToImage(ImageReceiver imageReceiver, int i) {
        imageReceiver.setOrientation(0, false);
        int[] iArr = new int[1];
        TLObject fileLocation = getFileLocation(i, iArr);
        if (fileLocation != null) {
            TLObject media = getMedia(i);
            if (media instanceof Photo) {
                Photo photo = (Photo) media;
                Bitmap bitmap = (this.currentThumb == null || imageReceiver != this.centerImage) ? null : this.currentThumb;
                if (iArr[0] == 0) {
                    iArr[0] = -1;
                }
                PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, 80);
                imageReceiver.setImage(fileLocation, null, null, bitmap != null ? new BitmapDrawable(null, bitmap) : null, closestPhotoSizeWithSize != null ? closestPhotoSizeWithSize.location : null, "b", iArr[0], null, 1);
            } else if (isMediaVideo(i)) {
                if (fileLocation instanceof TLRPC$TL_fileLocationUnavailable) {
                    imageReceiver.setImageBitmap(this.parentActivity.getResources().getDrawable(R.drawable.photoview_placeholder));
                    return;
                }
                Bitmap bitmap2 = null;
                if (this.currentThumb != null && imageReceiver == this.centerImage) {
                    bitmap2 = this.currentThumb;
                }
                imageReceiver.setImage(null, null, null, bitmap2 != null ? new BitmapDrawable(null, bitmap2) : null, fileLocation, "b", 0, null, 1);
            } else if (this.currentAnimation != null) {
                imageReceiver.setImageBitmap(this.currentAnimation);
                this.currentAnimation.setSecondParentView(this.photoContainerView);
            }
        } else if (iArr[0] == 0) {
            imageReceiver.setImageBitmap((Bitmap) null);
        } else {
            imageReceiver.setImageBitmap(this.parentActivity.getResources().getDrawable(R.drawable.photoview_placeholder));
        }
    }

    private void setRichTextParents(RichText richText, RichText richText2) {
        if (richText2 != null) {
            richText2.parentRichText = richText;
            if (richText2 instanceof TLRPC$TL_textFixed) {
                setRichTextParents(richText2, ((TLRPC$TL_textFixed) richText2).text);
            } else if (richText2 instanceof TLRPC$TL_textItalic) {
                setRichTextParents(richText2, ((TLRPC$TL_textItalic) richText2).text);
            } else if (richText2 instanceof TLRPC$TL_textBold) {
                setRichTextParents(richText2, ((TLRPC$TL_textBold) richText2).text);
            } else if (richText2 instanceof TLRPC$TL_textUnderline) {
                setRichTextParents(richText2, ((TLRPC$TL_textUnderline) richText2).text);
            } else if (richText2 instanceof TLRPC$TL_textStrike) {
                setRichTextParents(richText, ((TLRPC$TL_textStrike) richText2).text);
            } else if (richText2 instanceof TLRPC$TL_textEmail) {
                setRichTextParents(richText2, ((TLRPC$TL_textEmail) richText2).text);
            } else if (richText2 instanceof TLRPC$TL_textUrl) {
                setRichTextParents(richText2, ((TLRPC$TL_textUrl) richText2).text);
            } else if (richText2 instanceof TLRPC$TL_textConcat) {
                int size = richText2.texts.size();
                for (int i = 0; i < size; i++) {
                    setRichTextParents(richText2, (RichText) richText2.texts.get(i));
                }
            }
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

    private void showActionBar(int i) {
        AnimatorSet animatorSet = new AnimatorSet();
        r1 = new Animator[3];
        r1[0] = ObjectAnimator.ofFloat(this.backButton, "alpha", new float[]{1.0f});
        r1[1] = ObjectAnimator.ofFloat(this.shareContainer, "alpha", new float[]{1.0f});
        r1[2] = ObjectAnimator.ofFloat(this.settingsButton, "alpha", new float[]{1.0f});
        animatorSet.playTogether(r1);
        animatorSet.setDuration(150);
        animatorSet.setStartDelay((long) i);
        animatorSet.start();
    }

    private void showNightModeHint() {
        if (this.parentActivity != null && this.nightModeHintView == null && this.nightModeEnabled) {
            this.nightModeHintView = new FrameLayout(this.parentActivity);
            this.nightModeHintView.setBackgroundColor(Theme.ACTION_BAR_MEDIA_PICKER_COLOR);
            this.containerView.addView(this.nightModeHintView, LayoutHelper.createFrame(-2, -2, 83));
            View imageView = new ImageView(this.parentActivity);
            imageView.setScaleType(ScaleType.CENTER);
            imageView.setImageResource(R.drawable.moon);
            this.nightModeHintView.addView(imageView, LayoutHelper.createFrame(56, 56, 19));
            View textView = new TextView(this.parentActivity);
            textView.setText(LocaleController.getString("InstantViewNightMode", R.string.InstantViewNightMode));
            textView.setTextColor(-1);
            textView.setTextSize(1, 15.0f);
            this.nightModeHintView.addView(textView, LayoutHelper.createFrame(-1, -1.0f, 51, 56.0f, 11.0f, 10.0f, 12.0f));
            AnimatorSet animatorSet = new AnimatorSet();
            Animator[] animatorArr = new Animator[1];
            animatorArr[0] = ObjectAnimator.ofFloat(this.nightModeHintView, "translationY", new float[]{(float) AndroidUtilities.dp(100.0f), BitmapDescriptorFactory.HUE_RED});
            animatorSet.playTogether(animatorArr);
            animatorSet.setInterpolator(new DecelerateInterpolator(1.5f));
            animatorSet.addListener(new AnimatorListenerAdapter() {

                /* renamed from: org.telegram.ui.ArticleViewer$23$1 */
                class C39081 implements Runnable {
                    C39081() {
                    }

                    public void run() {
                        AnimatorSet animatorSet = new AnimatorSet();
                        Animator[] animatorArr = new Animator[1];
                        animatorArr[0] = ObjectAnimator.ofFloat(ArticleViewer.this.nightModeHintView, "translationY", new float[]{(float) AndroidUtilities.dp(100.0f)});
                        animatorSet.playTogether(animatorArr);
                        animatorSet.setInterpolator(new DecelerateInterpolator(1.5f));
                        animatorSet.setDuration(250);
                        animatorSet.start();
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    AndroidUtilities.runOnUIThread(new C39081(), 3000);
                }
            });
            animatorSet.setDuration(250);
            animatorSet.start();
        }
    }

    private void showPopup(View view, int i, int i2, int i3) {
        if (this.popupWindow == null || !this.popupWindow.isShowing()) {
            if (this.popupLayout == null) {
                this.popupRect = new Rect();
                this.popupLayout = new ActionBarPopupWindowLayout(this.parentActivity);
                this.popupLayout.setBackgroundDrawable(this.parentActivity.getResources().getDrawable(R.drawable.menu_copy));
                this.popupLayout.setAnimationEnabled(false);
                this.popupLayout.setOnTouchListener(new C39071());
                this.popupLayout.setDispatchKeyEventListener(new C39112());
                this.popupLayout.setShowedFromBotton(false);
                View textView = new TextView(this.parentActivity);
                textView.setTextColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
                textView.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                textView.setGravity(16);
                textView.setPadding(AndroidUtilities.dp(14.0f), 0, AndroidUtilities.dp(14.0f), 0);
                textView.setTextSize(1, 15.0f);
                textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                textView.setText(LocaleController.getString("Copy", R.string.Copy).toUpperCase());
                textView.setOnClickListener(new C39163());
                this.popupLayout.addView(textView, LayoutHelper.createFrame(-2, 38.0f));
                this.popupWindow = new ActionBarPopupWindow(this.popupLayout, -2, -2);
                this.popupWindow.setAnimationEnabled(false);
                this.popupWindow.setAnimationStyle(R.style.PopupAnimation);
                this.popupWindow.setOutsideTouchable(true);
                this.popupWindow.setClippingEnabled(true);
                this.popupWindow.setInputMethodMode(2);
                this.popupWindow.setSoftInputMode(0);
                this.popupWindow.getContentView().setFocusableInTouchMode(true);
                this.popupWindow.setOnDismissListener(new C39194());
            }
            this.popupLayout.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE));
            this.popupWindow.setFocusable(true);
            this.popupWindow.showAtLocation(view, i, i2, i3);
            this.popupWindow.startAnimation();
            return;
        }
        this.popupWindow.dismiss();
    }

    private void showProgressView(final boolean z) {
        if (this.progressViewAnimation != null) {
            this.progressViewAnimation.cancel();
        }
        this.progressViewAnimation = new AnimatorSet();
        AnimatorSet animatorSet;
        Animator[] animatorArr;
        if (z) {
            this.progressView.setVisibility(0);
            this.shareContainer.setEnabled(false);
            animatorSet = this.progressViewAnimation;
            animatorArr = new Animator[6];
            animatorArr[0] = ObjectAnimator.ofFloat(this.shareButton, "scaleX", new float[]{0.1f});
            animatorArr[1] = ObjectAnimator.ofFloat(this.shareButton, "scaleY", new float[]{0.1f});
            animatorArr[2] = ObjectAnimator.ofFloat(this.shareButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr[3] = ObjectAnimator.ofFloat(this.progressView, "scaleX", new float[]{1.0f});
            animatorArr[4] = ObjectAnimator.ofFloat(this.progressView, "scaleY", new float[]{1.0f});
            animatorArr[5] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{1.0f});
            animatorSet.playTogether(animatorArr);
        } else {
            this.shareButton.setVisibility(0);
            this.shareContainer.setEnabled(true);
            animatorSet = this.progressViewAnimation;
            animatorArr = new Animator[6];
            animatorArr[0] = ObjectAnimator.ofFloat(this.progressView, "scaleX", new float[]{0.1f});
            animatorArr[1] = ObjectAnimator.ofFloat(this.progressView, "scaleY", new float[]{0.1f});
            animatorArr[2] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr[3] = ObjectAnimator.ofFloat(this.shareButton, "scaleX", new float[]{1.0f});
            animatorArr[4] = ObjectAnimator.ofFloat(this.shareButton, "scaleY", new float[]{1.0f});
            animatorArr[5] = ObjectAnimator.ofFloat(this.shareButton, "alpha", new float[]{1.0f});
            animatorSet.playTogether(animatorArr);
        }
        this.progressViewAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationCancel(Animator animator) {
                if (ArticleViewer.this.progressViewAnimation != null && ArticleViewer.this.progressViewAnimation.equals(animator)) {
                    ArticleViewer.this.progressViewAnimation = null;
                }
            }

            public void onAnimationEnd(Animator animator) {
                if (ArticleViewer.this.progressViewAnimation != null && ArticleViewer.this.progressViewAnimation.equals(animator)) {
                    if (z) {
                        ArticleViewer.this.shareButton.setVisibility(4);
                    } else {
                        ArticleViewer.this.progressView.setVisibility(4);
                    }
                }
            }
        });
        this.progressViewAnimation.setDuration(150);
        this.progressViewAnimation.start();
    }

    private void toggleActionBar(boolean z, boolean z2) {
        float f = 1.0f;
        if (z) {
            this.actionBar.setVisibility(0);
            if (this.videoPlayer != null) {
                this.bottomLayout.setVisibility(0);
            }
            if (this.captionTextView.getTag() != null) {
                this.captionTextView.setVisibility(0);
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
                        if (ArticleViewer.this.currentActionBarAnimation != null && ArticleViewer.this.currentActionBarAnimation.equals(animator)) {
                            ArticleViewer.this.actionBar.setVisibility(8);
                            if (ArticleViewer.this.videoPlayer != null) {
                                ArticleViewer.this.bottomLayout.setVisibility(8);
                            }
                            if (ArticleViewer.this.captionTextView.getTag() != null) {
                                ArticleViewer.this.captionTextView.setVisibility(4);
                            }
                            ArticleViewer.this.currentActionBarAnimation = null;
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
        if (this.captionTextView.getTag() != null) {
            textView = this.captionTextView;
            if (!z) {
                f = BitmapDescriptorFactory.HUE_RED;
            }
            textView.setAlpha(f);
        }
        if (!z) {
            this.actionBar.setVisibility(8);
            if (this.videoPlayer != null) {
                this.bottomLayout.setVisibility(8);
            }
            if (this.captionTextView.getTag() != null) {
                this.captionTextView.setVisibility(4);
            }
        }
    }

    private void updateFontEntry(Entry<Integer, TextPaint> entry, Typeface typeface, Typeface typeface2, Typeface typeface3, Typeface typeface4) {
        Integer num = (Integer) entry.getKey();
        TextPaint textPaint = (TextPaint) entry.getValue();
        if ((num.intValue() & 1) != 0 && (num.intValue() & 2) != 0) {
            textPaint.setTypeface(typeface2);
        } else if ((num.intValue() & 1) != 0) {
            textPaint.setTypeface(typeface3);
        } else if ((num.intValue() & 2) != 0) {
            textPaint.setTypeface(typeface4);
        } else {
            textPaint.setTypeface(typeface);
        }
    }

    private void updateInterfaceForCurrentPage(boolean z) {
        if (this.currentPage != null && this.currentPage.cached_page != null) {
            int clientUserId;
            this.isRtl = -1;
            this.channelBlock = null;
            this.blocks.clear();
            this.photoBlocks.clear();
            this.audioBlocks.clear();
            this.audioMessages.clear();
            int size = this.currentPage.cached_page.blocks.size();
            for (int i = 0; i < size; i++) {
                PageBlock pageBlock = (PageBlock) this.currentPage.cached_page.blocks.get(i);
                if (!(pageBlock instanceof TLRPC$TL_pageBlockUnsupported)) {
                    if (pageBlock instanceof TLRPC$TL_pageBlockAnchor) {
                        this.anchors.put(pageBlock.name.toLowerCase(), Integer.valueOf(this.blocks.size()));
                    } else {
                        int i2;
                        PageBlock pageBlock2;
                        if (pageBlock instanceof TLRPC$TL_pageBlockAudio) {
                            Message tLRPC$TL_message = new TLRPC$TL_message();
                            tLRPC$TL_message.out = true;
                            i2 = ((int) this.currentPage.id) + i;
                            pageBlock.mid = i2;
                            tLRPC$TL_message.id = i2;
                            tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
                            Peer peer = tLRPC$TL_message.to_id;
                            clientUserId = UserConfig.getClientUserId();
                            tLRPC$TL_message.from_id = clientUserId;
                            peer.user_id = clientUserId;
                            tLRPC$TL_message.date = (int) (System.currentTimeMillis() / 1000);
                            tLRPC$TL_message.message = "-1";
                            tLRPC$TL_message.media = new TLRPC$TL_messageMediaDocument();
                            MessageMedia messageMedia = tLRPC$TL_message.media;
                            messageMedia.flags |= 3;
                            tLRPC$TL_message.media.document = getDocumentWithId(pageBlock.audio_id);
                            tLRPC$TL_message.flags |= 768;
                            MessageObject messageObject = new MessageObject(tLRPC$TL_message, null, false);
                            this.audioMessages.add(messageObject);
                            this.audioBlocks.put((TLRPC$TL_pageBlockAudio) pageBlock, messageObject);
                        }
                        setRichTextParents(null, pageBlock.text);
                        setRichTextParents(null, pageBlock.caption);
                        if (pageBlock instanceof TLRPC$TL_pageBlockAuthorDate) {
                            setRichTextParents(null, ((TLRPC$TL_pageBlockAuthorDate) pageBlock).author);
                        } else if (pageBlock instanceof TLRPC$TL_pageBlockCollage) {
                            TLRPC$TL_pageBlockCollage tLRPC$TL_pageBlockCollage = (TLRPC$TL_pageBlockCollage) pageBlock;
                            for (clientUserId = 0; clientUserId < tLRPC$TL_pageBlockCollage.items.size(); clientUserId++) {
                                setRichTextParents(null, ((PageBlock) tLRPC$TL_pageBlockCollage.items.get(clientUserId)).text);
                                setRichTextParents(null, ((PageBlock) tLRPC$TL_pageBlockCollage.items.get(clientUserId)).caption);
                            }
                        } else if (pageBlock instanceof TLRPC$TL_pageBlockList) {
                            TLRPC$TL_pageBlockList tLRPC$TL_pageBlockList = (TLRPC$TL_pageBlockList) pageBlock;
                            for (clientUserId = 0; clientUserId < tLRPC$TL_pageBlockList.items.size(); clientUserId++) {
                                setRichTextParents(null, (RichText) tLRPC$TL_pageBlockList.items.get(clientUserId));
                            }
                        } else if (pageBlock instanceof TLRPC$TL_pageBlockSlideshow) {
                            TLRPC$TL_pageBlockSlideshow tLRPC$TL_pageBlockSlideshow = (TLRPC$TL_pageBlockSlideshow) pageBlock;
                            for (clientUserId = 0; clientUserId < tLRPC$TL_pageBlockSlideshow.items.size(); clientUserId++) {
                                setRichTextParents(null, ((PageBlock) tLRPC$TL_pageBlockSlideshow.items.get(clientUserId)).text);
                                setRichTextParents(null, ((PageBlock) tLRPC$TL_pageBlockSlideshow.items.get(clientUserId)).caption);
                            }
                        }
                        if (i == 0) {
                            pageBlock.first = true;
                            if ((pageBlock instanceof TLRPC$TL_pageBlockCover) && pageBlock.cover.caption != null && !(pageBlock.cover.caption instanceof TLRPC$TL_textEmpty) && size > 1) {
                                pageBlock2 = (PageBlock) this.currentPage.cached_page.blocks.get(1);
                                if (pageBlock2 instanceof TLRPC$TL_pageBlockChannel) {
                                    this.channelBlock = (TLRPC$TL_pageBlockChannel) pageBlock2;
                                }
                            }
                        } else if (i == 1 && this.channelBlock != null) {
                        }
                        addAllMediaFromBlock(pageBlock);
                        this.blocks.add(pageBlock);
                        if (pageBlock instanceof TLRPC$TL_pageBlockEmbedPost) {
                            if (!pageBlock.blocks.isEmpty()) {
                                pageBlock.level = -1;
                                for (i2 = 0; i2 < pageBlock.blocks.size(); i2++) {
                                    pageBlock2 = (PageBlock) pageBlock.blocks.get(i2);
                                    if (!(pageBlock2 instanceof TLRPC$TL_pageBlockUnsupported)) {
                                        if (pageBlock2 instanceof TLRPC$TL_pageBlockAnchor) {
                                            this.anchors.put(pageBlock2.name.toLowerCase(), Integer.valueOf(this.blocks.size()));
                                        } else {
                                            pageBlock2.level = 1;
                                            if (i2 == pageBlock.blocks.size() - 1) {
                                                pageBlock2.bottom = true;
                                            }
                                            this.blocks.add(pageBlock2);
                                            addAllMediaFromBlock(pageBlock2);
                                        }
                                    }
                                }
                            }
                            if (!(pageBlock.caption instanceof TLRPC$TL_textEmpty)) {
                                TLRPC$TL_pageBlockParagraph tLRPC$TL_pageBlockParagraph = new TLRPC$TL_pageBlockParagraph();
                                tLRPC$TL_pageBlockParagraph.caption = pageBlock.caption;
                                this.blocks.add(tLRPC$TL_pageBlockParagraph);
                            }
                        }
                    }
                }
            }
            this.adapter.notifyDataSetChanged();
            if (this.pagesStack.size() == 1 || z) {
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("articles", 0);
                String str = "article" + this.currentPage.id;
                clientUserId = sharedPreferences.getInt(str, -1);
                int i3 = sharedPreferences.getBoolean(new StringBuilder().append(str).append("r").toString(), true) == (AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y) ? sharedPreferences.getInt(str + "o", 0) - this.listView.getPaddingTop() : AndroidUtilities.dp(10.0f);
                if (clientUserId != -1) {
                    this.layoutManager.scrollToPositionWithOffset(clientUserId, i3);
                    return;
                }
                return;
            }
            this.layoutManager.scrollToPositionWithOffset(0, 0);
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
            return;
        }
        this.maxY = BitmapDescriptorFactory.HUE_RED;
        this.minY = BitmapDescriptorFactory.HUE_RED;
    }

    private void updateNightModeButton() {
        this.nightModeImageView.setEnabled(this.selectedColor != 2);
        this.nightModeImageView.setAlpha(this.selectedColor == 2 ? 0.5f : 1.0f);
        ImageView imageView = this.nightModeImageView;
        int i = (!this.nightModeEnabled || this.selectedColor == 2) ? -3355444 : -15428119;
        imageView.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
    }

    private void updatePaintColors() {
        ApplicationLoader.applicationContext.getSharedPreferences("articles", 0).edit().putInt("font_color", this.selectedColor).commit();
        int selectedColor = getSelectedColor();
        if (selectedColor == 0) {
            this.backgroundPaint.setColor(-1);
            this.listView.setGlowColor(-657673);
        } else if (selectedColor == 1) {
            this.backgroundPaint.setColor(-659492);
            this.listView.setGlowColor(-659492);
        } else if (selectedColor == 2) {
            this.backgroundPaint.setColor(-15461356);
            this.listView.setGlowColor(-15461356);
        }
        for (int i = 0; i < Theme.chat_ivStatesDrawable.length; i++) {
            Theme.setCombinedDrawableColor(Theme.chat_ivStatesDrawable[i][0], getTextColor(), false);
            Theme.setCombinedDrawableColor(Theme.chat_ivStatesDrawable[i][0], getTextColor(), true);
            Theme.setCombinedDrawableColor(Theme.chat_ivStatesDrawable[i][1], getTextColor(), false);
            Theme.setCombinedDrawableColor(Theme.chat_ivStatesDrawable[i][1], getTextColor(), true);
        }
        if (quoteLinePaint != null) {
            quoteLinePaint.setColor(getTextColor());
        }
        if (listTextPointerPaint != null) {
            listTextPointerPaint.setColor(getTextColor());
        }
        if (preformattedBackgroundPaint != null) {
            if (selectedColor == 0) {
                preformattedBackgroundPaint.setColor(-657156);
            } else if (selectedColor == 1) {
                preformattedBackgroundPaint.setColor(-1712440);
            } else if (selectedColor == 2) {
                preformattedBackgroundPaint.setColor(-14277082);
            }
        }
        if (urlPaint != null) {
            if (selectedColor == 0) {
                urlPaint.setColor(-1315861);
            } else if (selectedColor == 1) {
                urlPaint.setColor(-1712440);
            } else if (selectedColor == 2) {
                urlPaint.setColor(-14277082);
            }
        }
        if (embedPostAuthorPaint != null) {
            embedPostAuthorPaint.setColor(getTextColor());
        }
        if (channelNamePaint != null) {
            if (this.channelBlock == null) {
                channelNamePaint.setColor(getTextColor());
            } else {
                channelNamePaint.setColor(-1);
            }
        }
        if (embedPostDatePaint != null) {
            if (selectedColor == 0) {
                embedPostDatePaint.setColor(-7366752);
            } else if (selectedColor == 1) {
                embedPostDatePaint.setColor(-11711675);
            } else if (selectedColor == 2) {
                embedPostDatePaint.setColor(-10066330);
            }
        }
        if (dividerPaint != null) {
            if (selectedColor == 0) {
                dividerPaint.setColor(-3288619);
            } else if (selectedColor == 1) {
                dividerPaint.setColor(-4080987);
            } else if (selectedColor == 2) {
                dividerPaint.setColor(-12303292);
            }
        }
        for (Entry value : titleTextPaints.entrySet()) {
            ((TextPaint) value.getValue()).setColor(getTextColor());
        }
        for (Entry value2 : subtitleTextPaints.entrySet()) {
            ((TextPaint) value2.getValue()).setColor(getTextColor());
        }
        for (Entry value22 : headerTextPaints.entrySet()) {
            ((TextPaint) value22.getValue()).setColor(getTextColor());
        }
        for (Entry value222 : subheaderTextPaints.entrySet()) {
            ((TextPaint) value222.getValue()).setColor(getTextColor());
        }
        for (Entry value2222 : quoteTextPaints.entrySet()) {
            ((TextPaint) value2222.getValue()).setColor(getTextColor());
        }
        for (Entry value22222 : preformattedTextPaints.entrySet()) {
            ((TextPaint) value22222.getValue()).setColor(getTextColor());
        }
        for (Entry value222222 : paragraphTextPaints.entrySet()) {
            ((TextPaint) value222222.getValue()).setColor(getTextColor());
        }
        for (Entry value2222222 : listTextPaints.entrySet()) {
            ((TextPaint) value2222222.getValue()).setColor(getTextColor());
        }
        for (Entry value22222222 : embedPostTextPaints.entrySet()) {
            ((TextPaint) value22222222.getValue()).setColor(getTextColor());
        }
        for (Entry value222222222 : videoTextPaints.entrySet()) {
            ((TextPaint) value222222222.getValue()).setColor(getTextColor());
        }
        for (Entry value2222222222 : captionTextPaints.entrySet()) {
            ((TextPaint) value2222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry value22222222222 : authorTextPaints.entrySet()) {
            ((TextPaint) value22222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry value222222222222 : footerTextPaints.entrySet()) {
            ((TextPaint) value222222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry value2222222222222 : subquoteTextPaints.entrySet()) {
            ((TextPaint) value2222222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry value22222222222222 : embedPostCaptionTextPaints.entrySet()) {
            ((TextPaint) value22222222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry value222222222222222 : embedTextPaints.entrySet()) {
            ((TextPaint) value222222222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry value2222222222222222 : slideshowTextPaints.entrySet()) {
            ((TextPaint) value2222222222222222.getValue()).setColor(getGrayTextColor());
        }
    }

    private void updatePaintFonts() {
        ApplicationLoader.applicationContext.getSharedPreferences("articles", 0).edit().putInt("font_type", this.selectedFont).commit();
        Typeface typeface = this.selectedFont == 0 ? Typeface.DEFAULT : Typeface.SERIF;
        Typeface typeface2 = this.selectedFont == 0 ? AndroidUtilities.getTypeface("fonts/ritalic.ttf") : Typeface.create(C3446C.SERIF_NAME, 2);
        Typeface typeface3 = this.selectedFont == 0 ? AndroidUtilities.getTypeface("fonts/rmedium.ttf") : Typeface.create(C3446C.SERIF_NAME, 1);
        Typeface typeface4 = this.selectedFont == 0 ? AndroidUtilities.getTypeface("fonts/rmediumitalic.ttf") : Typeface.create(C3446C.SERIF_NAME, 3);
        for (Entry updateFontEntry : quoteTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry2 : preformattedTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry2, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry22 : paragraphTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry22, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry222 : listTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry222, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry2222 : embedPostTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry2222, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry22222 : videoTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry22222, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry222222 : captionTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry222222, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry2222222 : authorTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry2222222, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry22222222 : footerTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry22222222, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry222222222 : subquoteTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry222222222, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry2222222222 : embedPostCaptionTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry2222222222, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry22222222222 : embedTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry22222222222, typeface, typeface4, typeface3, typeface2);
        }
        for (Entry updateFontEntry222222222222 : slideshowTextPaints.entrySet()) {
            updateFontEntry(updateFontEntry222222222222, typeface, typeface4, typeface3, typeface2);
        }
    }

    private void updatePaintSize() {
        ApplicationLoader.applicationContext.getSharedPreferences("articles", 0).edit().putInt("font_size", this.selectedFontSize).commit();
        this.adapter.notifyDataSetChanged();
    }

    private void updateVideoPlayerTime() {
        CharSequence format;
        if (this.videoPlayer == null) {
            format = String.format("%02d:%02d / %02d:%02d", new Object[]{Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)});
        } else {
            long currentPosition = this.videoPlayer.getCurrentPosition() / 1000;
            if (this.videoPlayer.getDuration() / 1000 == C3446C.TIME_UNSET || currentPosition == C3446C.TIME_UNSET) {
                format = String.format("%02d:%02d / %02d:%02d", new Object[]{Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)});
            } else {
                format = String.format("%02d:%02d / %02d:%02d", new Object[]{Long.valueOf(currentPosition / 60), Long.valueOf(currentPosition % 60), Long.valueOf(r2 / 60), Long.valueOf(r2 % 60)});
            }
        }
        if (!TextUtils.equals(this.videoPlayerTime.getText(), format)) {
            this.videoPlayerTime.setText(format);
        }
    }

    protected void cancelCheckLongPress() {
        this.checkingForLongPress = false;
        if (this.pendingCheckForLongPress != null) {
            this.windowView.removeCallbacks(this.pendingCheckForLongPress);
        }
        if (this.pendingCheckForTap != null) {
            this.windowView.removeCallbacks(this.pendingCheckForTap);
        }
    }

    public void close(boolean z, boolean z2) {
        if (this.parentActivity != null && this.isVisible && !checkAnimation()) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidReset);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidStarted);
            if (this.fullscreenVideoContainer.getVisibility() == 0) {
                if (this.customView != null) {
                    this.fullscreenVideoContainer.setVisibility(4);
                    this.customViewCallback.onCustomViewHidden();
                    this.fullscreenVideoContainer.removeView(this.customView);
                    this.customView = null;
                } else if (this.fullscreenedVideo != null) {
                    this.fullscreenedVideo.exitFullscreen();
                }
                if (!z2) {
                    return;
                }
            }
            if (this.isPhotoVisible) {
                closePhoto(!z2);
                if (!z2) {
                    return;
                }
            }
            if (this.openUrlReqId != 0) {
                ConnectionsManager.getInstance().cancelRequest(this.openUrlReqId, true);
                this.openUrlReqId = 0;
                showProgressView(false);
            }
            if (this.previewsReqId != 0) {
                ConnectionsManager.getInstance().cancelRequest(this.previewsReqId, true);
                this.previewsReqId = 0;
                showProgressView(false);
            }
            saveCurrentPagePosition();
            if (!z || z2 || !removeLastPageFromStack()) {
                this.parentFragment = null;
                try {
                    if (this.visibleDialog != null) {
                        this.visibleDialog.dismiss();
                        this.visibleDialog = null;
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                AnimatorSet animatorSet = new AnimatorSet();
                r3 = new Animator[3];
                r3[0] = ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                r3[1] = ObjectAnimator.ofFloat(this.containerView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                r3[2] = ObjectAnimator.ofFloat(this.windowView, "translationX", new float[]{BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(56.0f)});
                animatorSet.playTogether(r3);
                this.animationInProgress = 2;
                this.animationEndRunnable = new Runnable() {
                    public void run() {
                        if (ArticleViewer.this.containerView != null) {
                            if (VERSION.SDK_INT >= 18) {
                                ArticleViewer.this.containerView.setLayerType(0, null);
                            }
                            ArticleViewer.this.animationInProgress = 0;
                            ArticleViewer.this.onClosed();
                        }
                    }
                };
                animatorSet.setDuration(150);
                animatorSet.setInterpolator(this.interpolator);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        if (ArticleViewer.this.animationEndRunnable != null) {
                            ArticleViewer.this.animationEndRunnable.run();
                            ArticleViewer.this.animationEndRunnable = null;
                        }
                    }
                });
                this.transitionAnimationStartTime = System.currentTimeMillis();
                if (VERSION.SDK_INT >= 18) {
                    this.containerView.setLayerType(2, null);
                }
                animatorSet.start();
            }
        }
    }

    public void closePhoto(boolean z) {
        if (this.parentActivity != null && this.isPhotoVisible && !checkPhotoAnimation()) {
            releasePlayer();
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidFailedLoad);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidLoaded);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileLoadProgressChanged);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
            this.isActionBarVisible = false;
            if (this.velocityTracker != null) {
                this.velocityTracker.recycle();
                this.velocityTracker = null;
            }
            final PlaceProviderObject placeForPhoto = getPlaceForPhoto(this.currentMedia);
            Animator[] animatorArr;
            if (z) {
                Rect rect;
                this.photoAnimationInProgress = 1;
                this.animatingImageView.setVisibility(0);
                this.photoContainerView.invalidate();
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
                    Rect drawRegion = placeForPhoto.imageReceiver.getDrawRegion();
                    layoutParams.width = drawRegion.right - drawRegion.left;
                    layoutParams.height = drawRegion.bottom - drawRegion.top;
                    this.animatingImageView.setImageBitmap(placeForPhoto.thumb);
                    rect = drawRegion;
                } else {
                    this.animatingImageView.setNeedRadius(false);
                    layoutParams.width = this.centerImage.getImageWidth();
                    layoutParams.height = this.centerImage.getImageHeight();
                    this.animatingImageView.setImageBitmap(this.centerImage.getBitmap());
                    rect = null;
                }
                this.animatingImageView.setLayoutParams(layoutParams);
                float f = ((float) AndroidUtilities.displaySize.x) / ((float) layoutParams.width);
                float f2 = ((float) (AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight)) / ((float) layoutParams.height);
                if (f > f2) {
                    f = f2;
                }
                float f3 = (((float) layoutParams.height) * this.scale) * f;
                float f4 = (((float) AndroidUtilities.displaySize.x) - ((((float) layoutParams.width) * this.scale) * f)) / 2.0f;
                f2 = (VERSION.SDK_INT < 21 || this.lastInsets == null) ? f4 : ((float) ((WindowInsets) this.lastInsets).getSystemWindowInsetLeft()) + f4;
                f4 = (((float) (AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight)) - f3) / 2.0f;
                this.animatingImageView.setTranslationX(f2 + this.translationX);
                this.animatingImageView.setTranslationY(f4 + this.translationY);
                this.animatingImageView.setScaleX(this.scale * f);
                this.animatingImageView.setScaleY(f * this.scale);
                if (placeForPhoto != null) {
                    placeForPhoto.imageReceiver.setVisible(false, true);
                    int abs = Math.abs(rect.left - placeForPhoto.imageReceiver.getImageX());
                    int abs2 = Math.abs(rect.top - placeForPhoto.imageReceiver.getImageY());
                    int[] iArr = new int[2];
                    placeForPhoto.parentView.getLocationInWindow(iArr);
                    i = (iArr[1] - (placeForPhoto.viewY + rect.top)) + placeForPhoto.clipTopAddition;
                    if (i < 0) {
                        i = 0;
                    }
                    int height = (((placeForPhoto.viewY + rect.top) + (rect.bottom - rect.top)) - (iArr[1] + placeForPhoto.parentView.getHeight())) + placeForPhoto.clipBottomAddition;
                    if (height < 0) {
                        height = 0;
                    }
                    i = Math.max(i, abs2);
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
                    this.animationValues[1][2] = ((float) placeForPhoto.viewX) + (((float) rect.left) * placeForPhoto.scale);
                    this.animationValues[1][3] = (((float) rect.top) * placeForPhoto.scale) + ((float) placeForPhoto.viewY);
                    this.animationValues[1][4] = ((float) abs) * placeForPhoto.scale;
                    this.animationValues[1][5] = ((float) i) * placeForPhoto.scale;
                    this.animationValues[1][6] = ((float) height) * placeForPhoto.scale;
                    this.animationValues[1][7] = (float) placeForPhoto.radius;
                    Animator[] animatorArr2 = new Animator[5];
                    animatorArr2[0] = ObjectAnimator.ofFloat(this.animatingImageView, "animationProgress", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                    animatorArr2[1] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
                    animatorArr2[2] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorArr2[3] = ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorArr2[4] = ObjectAnimator.ofFloat(this.captionTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorSet.playTogether(animatorArr2);
                } else {
                    i = AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight;
                    animatorArr = new Animator[6];
                    animatorArr[0] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
                    animatorArr[1] = ObjectAnimator.ofFloat(this.animatingImageView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    ClippingImageView clippingImageView = this.animatingImageView;
                    String str = "translationY";
                    float[] fArr = new float[1];
                    fArr[0] = this.translationY >= BitmapDescriptorFactory.HUE_RED ? (float) i : (float) (-i);
                    animatorArr[2] = ObjectAnimator.ofFloat(clippingImageView, str, fArr);
                    animatorArr[3] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorArr[4] = ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorArr[5] = ObjectAnimator.ofFloat(this.captionTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorSet.playTogether(animatorArr);
                }
                this.photoAnimationEndRunnable = new Runnable() {
                    public void run() {
                        if (VERSION.SDK_INT >= 18) {
                            ArticleViewer.this.photoContainerView.setLayerType(0, null);
                        }
                        ArticleViewer.this.photoContainerView.setVisibility(4);
                        ArticleViewer.this.photoContainerBackground.setVisibility(4);
                        ArticleViewer.this.photoAnimationInProgress = 0;
                        ArticleViewer.this.onPhotoClosed(placeForPhoto);
                    }
                };
                animatorSet.setDuration(200);
                animatorSet.addListener(new AnimatorListenerAdapter() {

                    /* renamed from: org.telegram.ui.ArticleViewer$48$1 */
                    class C39181 implements Runnable {
                        C39181() {
                        }

                        public void run() {
                            if (ArticleViewer.this.photoAnimationEndRunnable != null) {
                                ArticleViewer.this.photoAnimationEndRunnable.run();
                                ArticleViewer.this.photoAnimationEndRunnable = null;
                            }
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        AndroidUtilities.runOnUIThread(new C39181());
                    }
                });
                this.photoTransitionAnimationStartTime = System.currentTimeMillis();
                if (VERSION.SDK_INT >= 18) {
                    this.photoContainerView.setLayerType(2, null);
                }
                animatorSet.start();
            } else {
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.photoContainerView, "scaleX", new float[]{0.9f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.photoContainerView, "scaleY", new float[]{0.9f});
                animatorArr[2] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
                animatorArr[3] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[4] = ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[5] = ObjectAnimator.ofFloat(this.captionTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorSet2.playTogether(animatorArr);
                this.photoAnimationInProgress = 2;
                this.photoAnimationEndRunnable = new Runnable() {
                    public void run() {
                        if (ArticleViewer.this.photoContainerView != null) {
                            if (VERSION.SDK_INT >= 18) {
                                ArticleViewer.this.photoContainerView.setLayerType(0, null);
                            }
                            ArticleViewer.this.photoContainerView.setVisibility(4);
                            ArticleViewer.this.photoContainerBackground.setVisibility(4);
                            ArticleViewer.this.photoAnimationInProgress = 0;
                            ArticleViewer.this.onPhotoClosed(placeForPhoto);
                            ArticleViewer.this.photoContainerView.setScaleX(1.0f);
                            ArticleViewer.this.photoContainerView.setScaleY(1.0f);
                        }
                    }
                };
                animatorSet2.setDuration(200);
                animatorSet2.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        if (ArticleViewer.this.photoAnimationEndRunnable != null) {
                            ArticleViewer.this.photoAnimationEndRunnable.run();
                            ArticleViewer.this.photoAnimationEndRunnable = null;
                        }
                    }
                });
                this.photoTransitionAnimationStartTime = System.currentTimeMillis();
                if (VERSION.SDK_INT >= 18) {
                    this.photoContainerView.setLayerType(2, null);
                }
                animatorSet2.start();
            }
            if (this.currentAnimation != null) {
                this.currentAnimation.setSecondParentView(null);
                this.currentAnimation = null;
                this.centerImage.setImageBitmap((Drawable) null);
            }
        }
    }

    public void collapse() {
        if (this.parentActivity != null && this.isVisible && !checkAnimation()) {
            if (this.fullscreenVideoContainer.getVisibility() == 0) {
                if (this.customView != null) {
                    this.fullscreenVideoContainer.setVisibility(4);
                    this.customViewCallback.onCustomViewHidden();
                    this.fullscreenVideoContainer.removeView(this.customView);
                    this.customView = null;
                } else if (this.fullscreenedVideo != null) {
                    this.fullscreenedVideo.exitFullscreen();
                }
            }
            if (this.isPhotoVisible) {
                closePhoto(false);
            }
            try {
                if (this.visibleDialog != null) {
                    this.visibleDialog.dismiss();
                    this.visibleDialog = null;
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            AnimatorSet animatorSet = new AnimatorSet();
            Animator[] animatorArr = new Animator[12];
            animatorArr[0] = ObjectAnimator.ofFloat(this.containerView, "translationX", new float[]{(float) (this.containerView.getMeasuredWidth() - AndroidUtilities.dp(56.0f))});
            FrameLayout frameLayout = this.containerView;
            String str = "translationY";
            float[] fArr = new float[1];
            fArr[0] = (float) ((VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight());
            animatorArr[1] = ObjectAnimator.ofFloat(frameLayout, str, fArr);
            animatorArr[2] = ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr[3] = ObjectAnimator.ofFloat(this.listView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr[4] = ObjectAnimator.ofFloat(this.listView, "translationY", new float[]{(float) (-AndroidUtilities.dp(56.0f))});
            animatorArr[5] = ObjectAnimator.ofFloat(this.headerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr[6] = ObjectAnimator.ofFloat(this.backButton, "scaleX", new float[]{1.0f});
            animatorArr[7] = ObjectAnimator.ofFloat(this.backButton, "scaleY", new float[]{1.0f});
            animatorArr[8] = ObjectAnimator.ofFloat(this.backButton, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr[9] = ObjectAnimator.ofFloat(this.shareContainer, "scaleX", new float[]{1.0f});
            animatorArr[10] = ObjectAnimator.ofFloat(this.shareContainer, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr[11] = ObjectAnimator.ofFloat(this.shareContainer, "scaleY", new float[]{1.0f});
            animatorSet.playTogether(animatorArr);
            this.collapsed = true;
            this.animationInProgress = 2;
            this.animationEndRunnable = new Runnable() {
                public void run() {
                    if (ArticleViewer.this.containerView != null) {
                        if (VERSION.SDK_INT >= 18) {
                            ArticleViewer.this.containerView.setLayerType(0, null);
                        }
                        ArticleViewer.this.animationInProgress = 0;
                        ((WindowManager) ArticleViewer.this.parentActivity.getSystemService("window")).updateViewLayout(ArticleViewer.this.windowView, ArticleViewer.this.windowLayoutParams);
                    }
                }
            };
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.setDuration(250);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    if (ArticleViewer.this.animationEndRunnable != null) {
                        ArticleViewer.this.animationEndRunnable.run();
                        ArticleViewer.this.animationEndRunnable = null;
                    }
                }
            });
            this.transitionAnimationStartTime = System.currentTimeMillis();
            if (VERSION.SDK_INT >= 18) {
                this.containerView.setLayerType(2, null);
            }
            this.backDrawable.setRotation(1.0f, true);
            animatorSet.start();
        }
    }

    public void destroyArticleViewer() {
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
            for (int i = 0; i < this.createdWebViews.size(); i++) {
                ((BlockEmbedCell) this.createdWebViews.get(i)).destroyWebView(true);
            }
            this.createdWebViews.clear();
            try {
                this.parentActivity.getWindow().clearFlags(128);
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
            this.parentActivity = null;
            this.parentFragment = null;
            Instance = null;
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int i2 = 0;
        String str;
        if (i == NotificationCenter.FileDidFailedLoad) {
            str = (String) objArr[0];
            while (i2 < 3) {
                if (this.currentFileNames[i2] == null || !this.currentFileNames[i2].equals(str)) {
                    i2++;
                } else {
                    this.radialProgressViews[i2].setProgress(1.0f, true);
                    checkProgress(i2, true);
                    return;
                }
            }
        } else if (i == NotificationCenter.FileDidLoaded) {
            str = (String) objArr[0];
            r2 = 0;
            while (r2 < 3) {
                if (this.currentFileNames[r2] == null || !this.currentFileNames[r2].equals(str)) {
                    r2++;
                } else {
                    this.radialProgressViews[r2].setProgress(1.0f, true);
                    checkProgress(r2, true);
                    if (r2 == 0 && isMediaVideo(this.currentIndex)) {
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
                    this.radialProgressViews[r2].setProgress(((Float) objArr[1]).floatValue(), true);
                }
                r2++;
            }
        } else if (i == NotificationCenter.emojiDidLoaded) {
            if (this.captionTextView != null) {
                this.captionTextView.invalidate();
            }
        } else if (i == NotificationCenter.messagePlayingDidStarted) {
            r0 = (MessageObject) objArr[0];
            if (this.listView != null) {
                r3 = this.listView.getChildCount();
                for (r2 = 0; r2 < r3; r2++) {
                    r0 = this.listView.getChildAt(r2);
                    if (r0 instanceof BlockAudioCell) {
                        ((BlockAudioCell) r0).updateButtonState(false);
                    }
                }
            }
        } else if (i == NotificationCenter.messagePlayingDidReset || i == NotificationCenter.messagePlayingPlayStateChanged) {
            if (this.listView != null) {
                r3 = this.listView.getChildCount();
                for (r2 = 0; r2 < r3; r2++) {
                    r0 = this.listView.getChildAt(r2);
                    if (r0 instanceof BlockAudioCell) {
                        BlockAudioCell blockAudioCell = (BlockAudioCell) r0;
                        if (blockAudioCell.getMessageObject() != null) {
                            blockAudioCell.updateButtonState(false);
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.messagePlayingProgressDidChanged) {
            Integer num = (Integer) objArr[0];
            if (this.listView != null) {
                r3 = this.listView.getChildCount();
                for (r2 = 0; r2 < r3; r2++) {
                    View childAt = this.listView.getChildAt(r2);
                    if (childAt instanceof BlockAudioCell) {
                        BlockAudioCell blockAudioCell2 = (BlockAudioCell) childAt;
                        MessageObject messageObject = blockAudioCell2.getMessageObject();
                        if (messageObject != null && messageObject.getId() == num.intValue()) {
                            r0 = MediaController.getInstance().getPlayingMessageObject();
                            if (r0 != null) {
                                messageObject.audioProgress = r0.audioProgress;
                                messageObject.audioProgressSec = r0.audioProgressSec;
                                blockAudioCell2.updatePlayingMessageProgress();
                                return;
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    public float getAnimationValue() {
        return this.animationValue;
    }

    public boolean isShowingImage(PageBlock pageBlock) {
        return this.isPhotoVisible && !this.disableShowCheck && pageBlock != null && this.currentMedia == pageBlock;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (!this.canZoom) {
            return false;
        }
        if ((this.scale == 1.0f && (this.translationY != BitmapDescriptorFactory.HUE_RED || this.translationX != BitmapDescriptorFactory.HUE_RED)) || this.animationStartTime != 0 || this.photoAnimationInProgress != 0) {
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
            this.photoContainerView.postInvalidate();
        }
        return false;
    }

    public void onLongPress(MotionEvent motionEvent) {
    }

    public void onPause() {
        if (this.currentAnimation != null) {
            closePhoto(false);
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
        boolean z2 = this.aspectRatioFrameLayout != null && this.aspectRatioFrameLayout.getVisibility() == 0;
        if (!(this.radialProgressViews[0] == null || this.photoContainerView == null || z2)) {
            int access$14100 = this.radialProgressViews[0].backgroundState;
            if (access$14100 > 0 && access$14100 <= 3) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
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
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public boolean open(MessageObject messageObject) {
        return open(messageObject, null, null, true);
    }

    public boolean open(TLRPC$TL_webPage tLRPC$TL_webPage, String str) {
        return open(null, tLRPC$TL_webPage, str, true);
    }

    public boolean openPhoto(PageBlock pageBlock) {
        if (this.parentActivity == null || this.isPhotoVisible || checkPhotoAnimation() || pageBlock == null) {
            return false;
        }
        final PlaceProviderObject placeForPhoto = getPlaceForPhoto(pageBlock);
        if (placeForPhoto == null) {
            return false;
        }
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidFailedLoad);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileLoadProgressChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.isPhotoVisible = true;
        toggleActionBar(true, false);
        this.actionBar.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.bottomLayout.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.captionTextView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.photoBackgroundDrawable.setAlpha(0);
        this.disableShowCheck = true;
        this.photoAnimationInProgress = 1;
        if (pageBlock != null) {
            this.currentAnimation = placeForPhoto.imageReceiver.getAnimation();
        }
        int indexOf = this.photoBlocks.indexOf(pageBlock);
        this.imagesArr.clear();
        if (!(pageBlock instanceof TLRPC$TL_pageBlockVideo) || isVideoBlock(pageBlock)) {
            this.imagesArr.addAll(this.photoBlocks);
        } else {
            this.imagesArr.add(pageBlock);
            indexOf = 0;
        }
        onPhotoShow(indexOf, placeForPhoto);
        Rect drawRegion = placeForPhoto.imageReceiver.getDrawRegion();
        int orientation = placeForPhoto.imageReceiver.getOrientation();
        indexOf = placeForPhoto.imageReceiver.getAnimatedOrientation();
        if (indexOf == 0) {
            indexOf = orientation;
        }
        this.animatingImageView.setVisibility(0);
        this.animatingImageView.setRadius(placeForPhoto.radius);
        this.animatingImageView.setOrientation(indexOf);
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
        float f2 = ((float) (AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight)) / ((float) layoutParams.height);
        if (f > f2) {
            f = f2;
        }
        float f3 = ((float) layoutParams.height) * f;
        float f4 = (((float) AndroidUtilities.displaySize.x) - (((float) layoutParams.width) * f)) / 2.0f;
        f2 = (VERSION.SDK_INT < 21 || this.lastInsets == null) ? f4 : ((float) ((WindowInsets) this.lastInsets).getSystemWindowInsetLeft()) + f4;
        f3 = (((float) (AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight)) - f3) / 2.0f;
        int abs = Math.abs(drawRegion.left - placeForPhoto.imageReceiver.getImageX());
        int abs2 = Math.abs(drawRegion.top - placeForPhoto.imageReceiver.getImageY());
        int[] iArr = new int[2];
        placeForPhoto.parentView.getLocationInWindow(iArr);
        int i = (iArr[1] - (placeForPhoto.viewY + drawRegion.top)) + placeForPhoto.clipTopAddition;
        if (i < 0) {
            i = 0;
        }
        int height = (((drawRegion.top + placeForPhoto.viewY) + layoutParams.height) - (iArr[1] + placeForPhoto.parentView.getHeight())) + placeForPhoto.clipBottomAddition;
        if (height < 0) {
            height = 0;
        }
        i = Math.max(i, abs2);
        height = Math.max(height, abs2);
        this.animationValues[0][0] = this.animatingImageView.getScaleX();
        this.animationValues[0][1] = this.animatingImageView.getScaleY();
        this.animationValues[0][2] = this.animatingImageView.getTranslationX();
        this.animationValues[0][3] = this.animatingImageView.getTranslationY();
        this.animationValues[0][4] = ((float) abs) * placeForPhoto.scale;
        this.animationValues[0][5] = ((float) i) * placeForPhoto.scale;
        this.animationValues[0][6] = ((float) height) * placeForPhoto.scale;
        this.animationValues[0][7] = (float) this.animatingImageView.getRadius();
        this.animationValues[1][0] = f;
        this.animationValues[1][1] = f;
        this.animationValues[1][2] = f2;
        this.animationValues[1][3] = f3;
        this.animationValues[1][4] = BitmapDescriptorFactory.HUE_RED;
        this.animationValues[1][5] = BitmapDescriptorFactory.HUE_RED;
        this.animationValues[1][6] = BitmapDescriptorFactory.HUE_RED;
        this.animationValues[1][7] = BitmapDescriptorFactory.HUE_RED;
        this.photoContainerView.setVisibility(0);
        this.photoContainerBackground.setVisibility(0);
        this.animatingImageView.setAnimationProgress(BitmapDescriptorFactory.HUE_RED);
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.animatingImageView, "animationProgress", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0, 255}), ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.captionTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
        this.photoAnimationEndRunnable = new Runnable() {
            public void run() {
                if (ArticleViewer.this.photoContainerView != null) {
                    if (VERSION.SDK_INT >= 18) {
                        ArticleViewer.this.photoContainerView.setLayerType(0, null);
                    }
                    ArticleViewer.this.photoAnimationInProgress = 0;
                    ArticleViewer.this.photoTransitionAnimationStartTime = 0;
                    ArticleViewer.this.setImages();
                    ArticleViewer.this.photoContainerView.invalidate();
                    ArticleViewer.this.animatingImageView.setVisibility(8);
                    if (ArticleViewer.this.showAfterAnimation != null) {
                        ArticleViewer.this.showAfterAnimation.imageReceiver.setVisible(true, true);
                    }
                    if (ArticleViewer.this.hideAfterAnimation != null) {
                        ArticleViewer.this.hideAfterAnimation.imageReceiver.setVisible(false, true);
                    }
                }
            }
        };
        animatorSet.setDuration(200);
        animatorSet.addListener(new AnimatorListenerAdapter() {

            /* renamed from: org.telegram.ui.ArticleViewer$44$1 */
            class C39171 implements Runnable {
                C39171() {
                }

                public void run() {
                    NotificationCenter.getInstance().setAnimationInProgress(false);
                    if (ArticleViewer.this.photoAnimationEndRunnable != null) {
                        ArticleViewer.this.photoAnimationEndRunnable.run();
                        ArticleViewer.this.photoAnimationEndRunnable = null;
                    }
                }
            }

            public void onAnimationEnd(Animator animator) {
                AndroidUtilities.runOnUIThread(new C39171());
            }
        });
        this.photoTransitionAnimationStartTime = System.currentTimeMillis();
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                NotificationCenter.getInstance().setAllowedNotificationsDutingAnimation(new int[]{NotificationCenter.dialogsNeedReload, NotificationCenter.closeChats});
                NotificationCenter.getInstance().setAnimationInProgress(true);
                animatorSet.start();
            }
        });
        if (VERSION.SDK_INT >= 18) {
            this.photoContainerView.setLayerType(2, null);
        }
        this.photoBackgroundDrawable.drawRunnable = new Runnable() {
            public void run() {
                ArticleViewer.this.disableShowCheck = false;
                placeForPhoto.imageReceiver.setVisible(false, true);
            }
        };
        return true;
    }

    public void setAnimationValue(float f) {
        this.animationValue = f;
        this.photoContainerView.invalidate();
    }

    public void setParentActivity(Activity activity, BaseFragment baseFragment) {
        this.parentFragment = baseFragment;
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidStarted);
        if (this.parentActivity == activity) {
            updatePaintColors();
            return;
        }
        this.parentActivity = activity;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("articles", 0);
        this.selectedFontSize = sharedPreferences.getInt("font_size", 2);
        this.selectedFont = sharedPreferences.getInt("font_type", 0);
        this.selectedColor = sharedPreferences.getInt("font_color", 0);
        this.nightModeEnabled = sharedPreferences.getBoolean("nightModeEnabled", false);
        this.backgroundPaint = new Paint();
        this.layerShadowDrawable = activity.getResources().getDrawable(R.drawable.layer_shadow);
        this.slideDotDrawable = activity.getResources().getDrawable(R.drawable.slide_dot_small);
        this.slideDotBigDrawable = activity.getResources().getDrawable(R.drawable.slide_dot_big);
        this.scrimPaint = new Paint();
        this.windowView = new WindowView(activity);
        this.windowView.setWillNotDraw(false);
        this.windowView.setClipChildren(true);
        this.windowView.setFocusable(false);
        this.containerView = new FrameLayout(activity);
        this.windowView.addView(this.containerView, LayoutHelper.createFrame(-1, -1, 51));
        this.containerView.setFitsSystemWindows(true);
        if (VERSION.SDK_INT >= 21) {
            this.containerView.setOnApplyWindowInsetsListener(new C39226());
        }
        this.containerView.setSystemUiVisibility(1028);
        this.photoContainerBackground = new View(activity);
        this.photoContainerBackground.setVisibility(4);
        this.photoContainerBackground.setBackgroundDrawable(this.photoBackgroundDrawable);
        this.windowView.addView(this.photoContainerBackground, LayoutHelper.createFrame(-1, -1, 51));
        this.animatingImageView = new ClippingImageView(activity);
        this.animatingImageView.setAnimationValues(this.animationValues);
        this.animatingImageView.setVisibility(8);
        this.windowView.addView(this.animatingImageView, LayoutHelper.createFrame(40, 40.0f));
        this.photoContainerView = new FrameLayoutDrawer(activity) {
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                int measuredHeight = (i4 - i2) - ArticleViewer.this.captionTextView.getMeasuredHeight();
                if (ArticleViewer.this.bottomLayout.getVisibility() == 0) {
                    measuredHeight -= ArticleViewer.this.bottomLayout.getMeasuredHeight();
                }
                ArticleViewer.this.captionTextView.layout(0, measuredHeight, ArticleViewer.this.captionTextView.getMeasuredWidth(), ArticleViewer.this.captionTextView.getMeasuredHeight() + measuredHeight);
            }
        };
        this.photoContainerView.setVisibility(4);
        this.photoContainerView.setWillNotDraw(false);
        this.windowView.addView(this.photoContainerView, LayoutHelper.createFrame(-1, -1, 51));
        this.fullscreenVideoContainer = new FrameLayout(activity);
        this.fullscreenVideoContainer.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        this.fullscreenVideoContainer.setVisibility(4);
        this.windowView.addView(this.fullscreenVideoContainer, LayoutHelper.createFrame(-1, -1.0f));
        this.fullscreenAspectRatioView = new AspectRatioFrameLayout(activity);
        this.fullscreenAspectRatioView.setVisibility(8);
        this.fullscreenVideoContainer.addView(this.fullscreenAspectRatioView, LayoutHelper.createFrame(-1, -1, 17));
        this.fullscreenTextureView = new TextureView(activity);
        if (VERSION.SDK_INT >= 21) {
            this.barBackground = new View(activity);
            this.barBackground.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
            this.windowView.addView(this.barBackground);
        }
        this.listView = new RecyclerListView(activity) {
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                int childCount = getChildCount();
                for (int i5 = 0; i5 < childCount; i5++) {
                    View childAt = getChildAt(i5);
                    if ((childAt.getTag() instanceof Integer) && ((Integer) childAt.getTag()).intValue() == 90 && childAt.getBottom() < getMeasuredHeight()) {
                        int measuredHeight = getMeasuredHeight();
                        childAt.layout(0, measuredHeight - childAt.getMeasuredHeight(), childAt.getMeasuredWidth(), measuredHeight);
                        return;
                    }
                }
            }
        };
        RecyclerListView recyclerListView = this.listView;
        LayoutManager linearLayoutManager = new LinearLayoutManager(this.parentActivity, 1, false);
        this.layoutManager = linearLayoutManager;
        recyclerListView.setLayoutManager(linearLayoutManager);
        recyclerListView = this.listView;
        Adapter webpageAdapter = new WebpageAdapter(this.parentActivity);
        this.adapter = webpageAdapter;
        recyclerListView.setAdapter(webpageAdapter);
        this.listView.setClipToPadding(false);
        this.listView.setPadding(0, AndroidUtilities.dp(56.0f), 0, 0);
        this.listView.setTopGlowOffset(AndroidUtilities.dp(56.0f));
        this.containerView.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemLongClickListener(new C39259());
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int i) {
                if (i != ArticleViewer.this.blocks.size() || ArticleViewer.this.currentPage == null) {
                    if (i >= 0 && i < ArticleViewer.this.blocks.size()) {
                        PageBlock pageBlock = (PageBlock) ArticleViewer.this.blocks.get(i);
                        if (pageBlock instanceof TLRPC$TL_pageBlockChannel) {
                            MessagesController.openByUserName(pageBlock.channel.username, ArticleViewer.this.parentFragment, 2);
                            ArticleViewer.this.close(false, true);
                        }
                    }
                } else if (ArticleViewer.this.previewsReqId == 0) {
                    TLObject userOrChat = MessagesController.getInstance().getUserOrChat("previews");
                    if (userOrChat instanceof TLRPC$TL_user) {
                        ArticleViewer.this.openPreviewsChat((User) userOrChat, ArticleViewer.this.currentPage.id);
                        return;
                    }
                    final long j = ArticleViewer.this.currentPage.id;
                    ArticleViewer.this.showProgressView(true);
                    TLObject tLRPC$TL_contacts_resolveUsername = new TLRPC$TL_contacts_resolveUsername();
                    tLRPC$TL_contacts_resolveUsername.username = "previews";
                    ArticleViewer.this.previewsReqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_resolveUsername, new RequestDelegate() {
                        public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (ArticleViewer.this.previewsReqId != 0) {
                                        ArticleViewer.this.previewsReqId = 0;
                                        ArticleViewer.this.showProgressView(false);
                                        if (tLObject != null) {
                                            TLRPC$TL_contacts_resolvedPeer tLRPC$TL_contacts_resolvedPeer = (TLRPC$TL_contacts_resolvedPeer) tLObject;
                                            MessagesController.getInstance().putUsers(tLRPC$TL_contacts_resolvedPeer.users, false);
                                            MessagesStorage.getInstance().putUsersAndChats(tLRPC$TL_contacts_resolvedPeer.users, tLRPC$TL_contacts_resolvedPeer.chats, false, true);
                                            if (!tLRPC$TL_contacts_resolvedPeer.users.isEmpty()) {
                                                ArticleViewer.this.openPreviewsChat((User) tLRPC$TL_contacts_resolvedPeer.users.get(0), j);
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
        this.listView.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                if (ArticleViewer.this.listView.getChildCount() != 0) {
                    ArticleViewer.this.headerView.invalidate();
                    ArticleViewer.this.checkScroll(i2);
                }
            }
        });
        this.headerPaint.setColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        this.headerProgressPaint.setColor(-14408666);
        this.headerView = new FrameLayout(activity) {
            protected void onDraw(Canvas canvas) {
                int measuredWidth = getMeasuredWidth();
                int measuredHeight = getMeasuredHeight();
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) measuredWidth, (float) measuredHeight, ArticleViewer.this.headerPaint);
                if (ArticleViewer.this.layoutManager != null) {
                    int findFirstVisibleItemPosition = ArticleViewer.this.layoutManager.findFirstVisibleItemPosition();
                    int findLastVisibleItemPosition = ArticleViewer.this.layoutManager.findLastVisibleItemPosition();
                    int itemCount = ArticleViewer.this.layoutManager.getItemCount();
                    View findViewByPosition = findLastVisibleItemPosition >= itemCount + -2 ? ArticleViewer.this.layoutManager.findViewByPosition(itemCount - 2) : ArticleViewer.this.layoutManager.findViewByPosition(findFirstVisibleItemPosition);
                    if (findViewByPosition != null) {
                        float f = ((float) measuredWidth) / ((float) (itemCount - 1));
                        ArticleViewer.this.layoutManager.getChildCount();
                        float measuredHeight2 = (float) findViewByPosition.getMeasuredHeight();
                        canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (((float) findFirstVisibleItemPosition) * f) + (findLastVisibleItemPosition >= itemCount + -2 ? (((float) (ArticleViewer.this.listView.getMeasuredHeight() - findViewByPosition.getTop())) * (((float) ((itemCount - 2) - findFirstVisibleItemPosition)) * f)) / measuredHeight2 : (1.0f - ((((float) Math.min(0, findViewByPosition.getTop() - ArticleViewer.this.listView.getPaddingTop())) + measuredHeight2) / measuredHeight2)) * f), (float) measuredHeight, ArticleViewer.this.headerProgressPaint);
                    }
                }
            }
        };
        this.headerView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.headerView.setWillNotDraw(false);
        this.containerView.addView(this.headerView, LayoutHelper.createFrame(-1, 56.0f));
        this.backButton = new ImageView(activity);
        this.backButton.setScaleType(ScaleType.CENTER);
        this.backDrawable = new BackDrawable(false);
        this.backDrawable.setAnimationTime(200.0f);
        this.backDrawable.setColor(-5000269);
        this.backDrawable.setRotated(false);
        this.backButton.setImageDrawable(this.backDrawable);
        this.backButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        this.headerView.addView(this.backButton, LayoutHelper.createFrame(54, 56.0f));
        this.backButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ArticleViewer.this.close(true, true);
            }
        });
        View linearLayout = new LinearLayout(this.parentActivity);
        linearLayout.setPadding(0, AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f));
        linearLayout.setOrientation(1);
        int i = 0;
        while (i < 3) {
            this.colorCells[i] = new ColorCell(this, this.parentActivity);
            switch (i) {
                case 0:
                    this.nightModeImageView = new ImageView(this.parentActivity);
                    this.nightModeImageView.setScaleType(ScaleType.CENTER);
                    this.nightModeImageView.setImageResource(R.drawable.moon);
                    ImageView imageView = this.nightModeImageView;
                    int i2 = (!this.nightModeEnabled || this.selectedColor == 2) ? -3355444 : -15428119;
                    imageView.setColorFilter(new PorterDuffColorFilter(i2, Mode.MULTIPLY));
                    this.nightModeImageView.setBackgroundDrawable(Theme.createSelectorDrawable(251658240));
                    this.colorCells[i].addView(this.nightModeImageView, LayoutHelper.createFrame(48, 48, 53));
                    this.nightModeImageView.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            ArticleViewer.this.nightModeEnabled = !ArticleViewer.this.nightModeEnabled;
                            ApplicationLoader.applicationContext.getSharedPreferences("articles", 0).edit().putBoolean("nightModeEnabled", ArticleViewer.this.nightModeEnabled).commit();
                            ArticleViewer.this.updateNightModeButton();
                            ArticleViewer.this.updatePaintColors();
                            ArticleViewer.this.adapter.notifyDataSetChanged();
                            if (ArticleViewer.this.nightModeEnabled) {
                                ArticleViewer.this.showNightModeHint();
                            }
                        }
                    });
                    this.colorCells[i].setTextAndColor(LocaleController.getString("ColorWhite", R.string.ColorWhite), -1);
                    break;
                case 1:
                    this.colorCells[i].setTextAndColor(LocaleController.getString("ColorSepia", R.string.ColorSepia), -1382967);
                    break;
                case 2:
                    this.colorCells[i].setTextAndColor(LocaleController.getString("ColorDark", R.string.ColorDark), -14474461);
                    break;
            }
            this.colorCells[i].select(i == this.selectedColor);
            this.colorCells[i].setTag(Integer.valueOf(i));
            this.colorCells[i].setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    int intValue = ((Integer) view.getTag()).intValue();
                    ArticleViewer.this.selectedColor = intValue;
                    int i = 0;
                    while (i < 3) {
                        ArticleViewer.this.colorCells[i].select(i == intValue);
                        i++;
                    }
                    ArticleViewer.this.updateNightModeButton();
                    ArticleViewer.this.updatePaintColors();
                    ArticleViewer.this.adapter.notifyDataSetChanged();
                }
            });
            linearLayout.addView(this.colorCells[i], LayoutHelper.createLinear(-1, 48));
            i++;
        }
        updateNightModeButton();
        View view = new View(this.parentActivity);
        view.setBackgroundColor(-2039584);
        linearLayout.addView(view, LayoutHelper.createLinear(-1, 1, 15.0f, 4.0f, 15.0f, 4.0f));
        view.getLayoutParams().height = 1;
        i = 0;
        while (i < 2) {
            this.fontCells[i] = new FontCell(this, this.parentActivity);
            switch (i) {
                case 0:
                    this.fontCells[i].setTextAndTypeface("Roboto", Typeface.DEFAULT);
                    break;
                case 1:
                    this.fontCells[i].setTextAndTypeface("Serif", Typeface.SERIF);
                    break;
            }
            this.fontCells[i].select(i == this.selectedFont);
            this.fontCells[i].setTag(Integer.valueOf(i));
            this.fontCells[i].setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    int intValue = ((Integer) view.getTag()).intValue();
                    ArticleViewer.this.selectedFont = intValue;
                    int i = 0;
                    while (i < 2) {
                        ArticleViewer.this.fontCells[i].select(i == intValue);
                        i++;
                    }
                    ArticleViewer.this.updatePaintFonts();
                    ArticleViewer.this.adapter.notifyDataSetChanged();
                }
            });
            linearLayout.addView(this.fontCells[i], LayoutHelper.createLinear(-1, 48));
            i++;
        }
        view = new View(this.parentActivity);
        view.setBackgroundColor(-2039584);
        linearLayout.addView(view, LayoutHelper.createLinear(-1, 1, 15.0f, 4.0f, 15.0f, 4.0f));
        view.getLayoutParams().height = 1;
        View textView = new TextView(this.parentActivity);
        textView.setTextColor(-14606047);
        textView.setTextSize(1, 16.0f);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine(true);
        textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        textView.setText(LocaleController.getString("FontSize", R.string.FontSize));
        linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 17, 12, 17, 0));
        linearLayout.addView(new SizeChooseView(this.parentActivity), LayoutHelper.createLinear(-1, 38, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 1.0f));
        this.settingsButton = new ActionBarMenuItem(this.parentActivity, null, Theme.ACTION_BAR_WHITE_SELECTOR_COLOR, -1);
        this.settingsButton.setPopupAnimationEnabled(false);
        this.settingsButton.setLayoutInScreen(true);
        View textView2 = new TextView(this.parentActivity);
        textView2.setTextSize(1, 18.0f);
        textView2.setText("Aa");
        textView2.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView2.setTextColor(-5000269);
        textView2.setGravity(17);
        this.settingsButton.addView(textView2, LayoutHelper.createFrame(-1, -1.0f));
        this.settingsButton.addSubItem(linearLayout, AndroidUtilities.dp(220.0f), -2);
        this.settingsButton.redrawPopup(-1);
        this.headerView.addView(this.settingsButton, LayoutHelper.createFrame(48, 56.0f, 53, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 56.0f, BitmapDescriptorFactory.HUE_RED));
        this.shareContainer = new FrameLayout(activity);
        this.headerView.addView(this.shareContainer, LayoutHelper.createFrame(48, 56, 53));
        this.shareContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ArticleViewer.this.currentPage != null && ArticleViewer.this.parentActivity != null) {
                    ArticleViewer.this.showDialog(new ShareAlert(ArticleViewer.this.parentActivity, null, ArticleViewer.this.currentPage.url, false, ArticleViewer.this.currentPage.url, true));
                    ArticleViewer.this.hideActionBar();
                }
            }
        });
        this.shareButton = new ImageView(activity);
        this.shareButton.setScaleType(ScaleType.CENTER);
        this.shareButton.setImageResource(R.drawable.ic_share_article);
        this.shareButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        this.shareContainer.addView(this.shareButton, LayoutHelper.createFrame(48, 56.0f));
        this.progressView = new ContextProgressView(activity, 2);
        this.progressView.setVisibility(8);
        this.shareContainer.addView(this.progressView, LayoutHelper.createFrame(48, 56.0f));
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
        if (progressDrawables == null) {
            progressDrawables = new Drawable[4];
            progressDrawables[0] = this.parentActivity.getResources().getDrawable(R.drawable.circle_big);
            progressDrawables[1] = this.parentActivity.getResources().getDrawable(R.drawable.cancel_big);
            progressDrawables[2] = this.parentActivity.getResources().getDrawable(R.drawable.load_big);
            progressDrawables[3] = this.parentActivity.getResources().getDrawable(R.drawable.play_big);
        }
        this.scroller = new Scroller(activity);
        this.blackPaint.setColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        this.actionBar = new ActionBar(activity);
        this.actionBar.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
        this.actionBar.setOccupyStatusBar(false);
        this.actionBar.setTitleColor(-1);
        this.actionBar.setItemsBackgroundColor(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR, false);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(1), Integer.valueOf(1)}));
        this.photoContainerView.addView(this.actionBar, LayoutHelper.createFrame(-1, -2.0f));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBarMenuOnItemClick() {
            public boolean canOpenMenu() {
                File access$6700 = ArticleViewer.this.getMediaFile(ArticleViewer.this.currentIndex);
                return access$6700 != null && access$6700.exists();
            }

            public void onItemClick(int i) {
                int i2 = 1;
                if (i == -1) {
                    ArticleViewer.this.closePhoto(true);
                } else if (i == 1) {
                    if (VERSION.SDK_INT < 23 || ArticleViewer.this.parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        File access$6700 = ArticleViewer.this.getMediaFile(ArticleViewer.this.currentIndex);
                        if (access$6700 == null || !access$6700.exists()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ArticleViewer.this.parentActivity);
                            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                            builder.setMessage(LocaleController.getString("PleaseDownload", R.string.PleaseDownload));
                            ArticleViewer.this.showDialog(builder.create());
                            return;
                        }
                        String file = access$6700.toString();
                        Context access$1900 = ArticleViewer.this.parentActivity;
                        if (!ArticleViewer.this.isMediaVideo(ArticleViewer.this.currentIndex)) {
                            i2 = 0;
                        }
                        MediaController.saveFile(file, access$1900, i2, null, null);
                        return;
                    }
                    ArticleViewer.this.parentActivity.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 4);
                } else if (i == 2) {
                    ArticleViewer.this.onSharePressed();
                } else if (i == 3) {
                    try {
                        AndroidUtilities.openForView(ArticleViewer.this.getMedia(ArticleViewer.this.currentIndex), ArticleViewer.this.parentActivity);
                        ArticleViewer.this.closePhoto(false);
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
            }
        });
        ActionBarMenu createMenu = this.actionBar.createMenu();
        createMenu.addItem(2, (int) R.drawable.share);
        this.menuItem = createMenu.addItem(0, (int) R.drawable.ic_ab_other);
        this.menuItem.setLayoutInScreen(true);
        this.menuItem.addSubItem(3, LocaleController.getString("OpenInExternalApp", R.string.OpenInExternalApp));
        this.menuItem.addSubItem(1, LocaleController.getString("SaveToGallery", R.string.SaveToGallery));
        this.bottomLayout = new FrameLayout(this.parentActivity);
        this.bottomLayout.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
        this.photoContainerView.addView(this.bottomLayout, LayoutHelper.createFrame(-1, 48, 83));
        this.captionTextViewOld = new TextView(activity);
        this.captionTextViewOld.setMaxLines(10);
        this.captionTextViewOld.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
        this.captionTextViewOld.setPadding(AndroidUtilities.dp(20.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(20.0f), AndroidUtilities.dp(8.0f));
        this.captionTextViewOld.setLinkTextColor(-1);
        this.captionTextViewOld.setTextColor(-1);
        this.captionTextViewOld.setGravity(19);
        this.captionTextViewOld.setTextSize(1, 16.0f);
        this.captionTextViewOld.setVisibility(4);
        this.photoContainerView.addView(this.captionTextViewOld, LayoutHelper.createFrame(-1, -2, 83));
        TextView textView3 = new TextView(activity);
        this.captionTextViewNew = textView3;
        this.captionTextView = textView3;
        this.captionTextViewNew.setMaxLines(10);
        this.captionTextViewNew.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
        this.captionTextViewNew.setPadding(AndroidUtilities.dp(20.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(20.0f), AndroidUtilities.dp(8.0f));
        this.captionTextViewNew.setLinkTextColor(-1);
        this.captionTextViewNew.setTextColor(-1);
        this.captionTextViewNew.setGravity(19);
        this.captionTextViewNew.setTextSize(1, 16.0f);
        this.captionTextViewNew.setVisibility(4);
        this.photoContainerView.addView(this.captionTextViewNew, LayoutHelper.createFrame(-1, -2, 83));
        this.radialProgressViews[0] = new RadialProgressView(activity, this.photoContainerView);
        this.radialProgressViews[0].setBackgroundState(0, false);
        this.radialProgressViews[1] = new RadialProgressView(activity, this.photoContainerView);
        this.radialProgressViews[1].setBackgroundState(0, false);
        this.radialProgressViews[2] = new RadialProgressView(activity, this.photoContainerView);
        this.radialProgressViews[2].setBackgroundState(0, false);
        this.videoPlayerSeekbar = new SeekBar(activity);
        this.videoPlayerSeekbar.setColors(1728053247, -1, -1);
        this.videoPlayerSeekbar.setDelegate(new SeekBarDelegate() {
            public void onSeekBarDrag(float f) {
                if (ArticleViewer.this.videoPlayer != null) {
                    ArticleViewer.this.videoPlayer.seekTo((long) ((int) (((float) ArticleViewer.this.videoPlayer.getDuration()) * f)));
                }
            }
        });
        this.videoPlayerControlFrameLayout = new FrameLayout(activity) {
            protected void onDraw(Canvas canvas) {
                canvas.save();
                canvas.translate((float) AndroidUtilities.dp(48.0f), BitmapDescriptorFactory.HUE_RED);
                ArticleViewer.this.videoPlayerSeekbar.draw(canvas);
                canvas.restore();
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                float f = BitmapDescriptorFactory.HUE_RED;
                if (ArticleViewer.this.videoPlayer != null) {
                    f = ((float) ArticleViewer.this.videoPlayer.getCurrentPosition()) / ((float) ArticleViewer.this.videoPlayer.getDuration());
                }
                ArticleViewer.this.videoPlayerSeekbar.setProgress(f);
            }

            protected void onMeasure(int i, int i2) {
                long j = 0;
                super.onMeasure(i, i2);
                if (ArticleViewer.this.videoPlayer != null) {
                    long duration = ArticleViewer.this.videoPlayer.getDuration();
                    if (duration != C3446C.TIME_UNSET) {
                        j = duration;
                    }
                }
                j /= 1000;
                ArticleViewer.this.videoPlayerSeekbar.setSize((getMeasuredWidth() - AndroidUtilities.dp(64.0f)) - ((int) Math.ceil((double) ArticleViewer.this.videoPlayerTime.getPaint().measureText(String.format("%02d:%02d / %02d:%02d", new Object[]{Long.valueOf(j / 60), Long.valueOf(j % 60), Long.valueOf(j / 60), Long.valueOf(j % 60)})))), getMeasuredHeight());
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                int x = (int) motionEvent.getX();
                x = (int) motionEvent.getY();
                if (!ArticleViewer.this.videoPlayerSeekbar.onTouch(motionEvent.getAction(), motionEvent.getX() - ((float) AndroidUtilities.dp(48.0f)), motionEvent.getY())) {
                    return super.onTouchEvent(motionEvent);
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                invalidate();
                return true;
            }
        };
        this.videoPlayerControlFrameLayout.setWillNotDraw(false);
        this.bottomLayout.addView(this.videoPlayerControlFrameLayout, LayoutHelper.createFrame(-1, -1, 51));
        this.videoPlayButton = new ImageView(activity);
        this.videoPlayButton.setScaleType(ScaleType.CENTER);
        this.videoPlayerControlFrameLayout.addView(this.videoPlayButton, LayoutHelper.createFrame(48, 48, 51));
        this.videoPlayButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ArticleViewer.this.videoPlayer == null) {
                    return;
                }
                if (ArticleViewer.this.isPlaying) {
                    ArticleViewer.this.videoPlayer.pause();
                } else {
                    ArticleViewer.this.videoPlayer.play();
                }
            }
        });
        this.videoPlayerTime = new TextView(activity);
        this.videoPlayerTime.setTextColor(-1);
        this.videoPlayerTime.setGravity(16);
        this.videoPlayerTime.setTextSize(1, 13.0f);
        this.videoPlayerControlFrameLayout.addView(this.videoPlayerTime, LayoutHelper.createFrame(-2, -1.0f, 53, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 8.0f, BitmapDescriptorFactory.HUE_RED));
        this.gestureDetector = new GestureDetector(activity, this);
        this.gestureDetector.setOnDoubleTapListener(this);
        this.centerImage.setParentView(this.photoContainerView);
        this.centerImage.setCrossfadeAlpha((byte) 2);
        this.centerImage.setInvalidateAll(true);
        this.leftImage.setParentView(this.photoContainerView);
        this.leftImage.setCrossfadeAlpha((byte) 2);
        this.leftImage.setInvalidateAll(true);
        this.rightImage.setParentView(this.photoContainerView);
        this.rightImage.setCrossfadeAlpha((byte) 2);
        this.rightImage.setInvalidateAll(true);
        updatePaintColors();
    }

    public void showDialog(Dialog dialog) {
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
                this.visibleDialog = dialog;
                this.visibleDialog.setCanceledOnTouchOutside(true);
                this.visibleDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    public void onDismiss(DialogInterface dialogInterface) {
                        ArticleViewer.this.showActionBar(120);
                        ArticleViewer.this.visibleDialog = null;
                    }
                });
                dialog.show();
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
        }
    }

    protected void startCheckLongPress() {
        if (!this.checkingForLongPress) {
            this.checkingForLongPress = true;
            if (this.pendingCheckForTap == null) {
                this.pendingCheckForTap = new CheckForTap();
            }
            this.windowView.postDelayed(this.pendingCheckForTap, (long) ViewConfiguration.getTapTimeout());
        }
    }

    public void uncollapse() {
        if (this.parentActivity != null && this.isVisible && !checkAnimation()) {
            AnimatorSet animatorSet = new AnimatorSet();
            r1 = new Animator[12];
            r1[0] = ObjectAnimator.ofFloat(this.containerView, "translationX", new float[]{BitmapDescriptorFactory.HUE_RED});
            r1[1] = ObjectAnimator.ofFloat(this.containerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
            r1[2] = ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f});
            r1[3] = ObjectAnimator.ofFloat(this.listView, "alpha", new float[]{1.0f});
            r1[4] = ObjectAnimator.ofFloat(this.listView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
            r1[5] = ObjectAnimator.ofFloat(this.headerView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
            r1[6] = ObjectAnimator.ofFloat(this.backButton, "scaleX", new float[]{1.0f});
            r1[7] = ObjectAnimator.ofFloat(this.backButton, "scaleY", new float[]{1.0f});
            r1[8] = ObjectAnimator.ofFloat(this.backButton, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
            r1[9] = ObjectAnimator.ofFloat(this.shareContainer, "scaleX", new float[]{1.0f});
            r1[10] = ObjectAnimator.ofFloat(this.shareContainer, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
            r1[11] = ObjectAnimator.ofFloat(this.shareContainer, "scaleY", new float[]{1.0f});
            animatorSet.playTogether(r1);
            this.collapsed = false;
            this.animationInProgress = 2;
            this.animationEndRunnable = new Runnable() {
                public void run() {
                    if (ArticleViewer.this.containerView != null) {
                        if (VERSION.SDK_INT >= 18) {
                            ArticleViewer.this.containerView.setLayerType(0, null);
                        }
                        ArticleViewer.this.animationInProgress = 0;
                    }
                }
            };
            animatorSet.setDuration(250);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    if (ArticleViewer.this.animationEndRunnable != null) {
                        ArticleViewer.this.animationEndRunnable.run();
                        ArticleViewer.this.animationEndRunnable = null;
                    }
                }
            });
            this.transitionAnimationStartTime = System.currentTimeMillis();
            if (VERSION.SDK_INT >= 18) {
                this.containerView.setLayerType(2, null);
            }
            this.backDrawable.setRotation(BitmapDescriptorFactory.HUE_RED, true);
            animatorSet.start();
        }
    }
}
