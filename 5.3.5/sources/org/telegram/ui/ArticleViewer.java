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
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
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
import org.telegram.messenger.MediaController$FileDownloadProgressListener;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.exoplayer2.C0907C;
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
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$PageBlock;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$Photo;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$RichText;
import org.telegram.tgnet.TLRPC$TL_channels_joinChannel;
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
import org.telegram.tgnet.TLRPC.DocumentAttribute;
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

public class ArticleViewer implements NotificationCenterDelegate, OnGestureListener, OnDoubleTapListener {
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
    private ArrayList<TLRPC$PageBlock> blocks = new ArrayList();
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
    private TLRPC$PageBlock currentMedia;
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
    private ArrayList<TLRPC$PageBlock> imagesArr = new ArrayList();
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
    private TLRPC$Chat loadedChannel;
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
    private PhotoBackgroundDrawable photoBackgroundDrawable = new PhotoBackgroundDrawable(-16777216);
    private ArrayList<TLRPC$PageBlock> photoBlocks = new ArrayList();
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
    class C20691 implements OnTouchListener {
        C20691() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == 0 && ArticleViewer.this.popupWindow != null && ArticleViewer.this.popupWindow.isShowing()) {
                v.getHitRect(ArticleViewer.this.popupRect);
                if (!ArticleViewer.this.popupRect.contains((int) event.getX(), (int) event.getY())) {
                    ArticleViewer.this.popupWindow.dismiss();
                }
            }
            return false;
        }
    }

    /* renamed from: org.telegram.ui.ArticleViewer$2 */
    class C20732 implements OnDispatchKeyEventListener {
        C20732() {
        }

        public void onDispatchKeyEvent(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && ArticleViewer.this.popupWindow != null && ArticleViewer.this.popupWindow.isShowing()) {
                ArticleViewer.this.popupWindow.dismiss();
            }
        }
    }

    /* renamed from: org.telegram.ui.ArticleViewer$3 */
    class C20783 implements OnClickListener {
        C20783() {
        }

        public void onClick(View v) {
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
    class C20814 implements OnDismissListener {
        C20814() {
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
    class C20846 implements OnApplyWindowInsetsListener {
        C20846() {
        }

        @SuppressLint({"NewApi"})
        public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
            WindowInsets oldInsets = (WindowInsets) ArticleViewer.this.lastInsets;
            ArticleViewer.this.lastInsets = insets;
            if (oldInsets == null || !oldInsets.toString().equals(insets.toString())) {
                ArticleViewer.this.windowView.requestLayout();
            }
            return insets.consumeSystemWindowInsets();
        }
    }

    private class FrameLayoutDrawer extends FrameLayout {
        public FrameLayoutDrawer(Context context) {
            super(context);
        }

        public boolean onTouchEvent(MotionEvent event) {
            ArticleViewer.this.processTouchEvent(event);
            return true;
        }

        protected void onDraw(Canvas canvas) {
            ArticleViewer.this.drawContent(canvas);
        }

        protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
            return child != ArticleViewer.this.aspectRatioFrameLayout && super.drawChild(canvas, child, drawingTime);
        }
    }

    /* renamed from: org.telegram.ui.ArticleViewer$9 */
    class C20879 implements OnItemLongClickListener {
        C20879() {
        }

        public boolean onItemClick(View view, int position) {
            return false;
        }
    }

    private class BlockAudioCell extends View implements MediaController$FileDownloadProgressListener {
        private int TAG;
        private int buttonPressed;
        private int buttonState;
        private int buttonX;
        private int buttonY;
        private TLRPC$TL_pageBlockAudio currentBlock;
        private TLRPC$Document currentDocument;
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
            this.radialProgress.setDiff(AndroidUtilities.dp(0.0f));
            this.radialProgress.setStrikeWidth(AndroidUtilities.dp(2.0f));
            this.TAG = MediaController.getInstance().generateObserverTag();
            this.seekBar = new SeekBar(context);
            this.seekBar.setDelegate(new SeekBarDelegate(ArticleViewer.this) {
                public void onSeekBarDrag(float progress) {
                    if (BlockAudioCell.this.currentMessageObject != null) {
                        BlockAudioCell.this.currentMessageObject.audioProgress = progress;
                        MediaController.getInstance().seekToProgress(BlockAudioCell.this.currentMessageObject, progress);
                    }
                }
            });
        }

        public void setBlock(TLRPC$TL_pageBlockAudio block, boolean first, boolean last) {
            this.currentBlock = block;
            this.currentMessageObject = (MessageObject) ArticleViewer.this.audioBlocks.get(this.currentBlock);
            this.currentDocument = this.currentMessageObject.getDocument();
            this.lastCreatedWidth = 0;
            this.isFirst = first;
            this.isLast = last;
            this.radialProgress.setProgressColor(ArticleViewer.this.getTextColor());
            this.seekBar.setColors(ArticleViewer.this.getTextColor() & 1073741823, ArticleViewer.this.getTextColor(), ArticleViewer.this.getTextColor());
            updateButtonState(false);
            requestLayout();
        }

        public MessageObject getMessageObject() {
            return this.currentMessageObject;
        }

        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            if (this.seekBar.onTouch(event.getAction(), event.getX() - ((float) this.seekBarX), event.getY() - ((float) this.seekBarY))) {
                if (event.getAction() == 0) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                invalidate();
                return true;
            }
            boolean z;
            if (event.getAction() == 0) {
                if ((this.buttonState != -1 && x >= ((float) this.buttonX) && x <= ((float) (this.buttonX + AndroidUtilities.dp(48.0f))) && y >= ((float) this.buttonY) && y <= ((float) (this.buttonY + AndroidUtilities.dp(48.0f)))) || this.buttonState == 0) {
                    this.buttonPressed = 1;
                    invalidate();
                }
            } else if (event.getAction() == 1) {
                if (this.buttonPressed == 1) {
                    this.buttonPressed = 0;
                    playSoundEffect(0);
                    didPressedButton(false);
                    this.radialProgress.swapBackground(getDrawableForCurrentState());
                    invalidate();
                }
            } else if (event.getAction() == 3) {
                this.buttonPressed = 0;
            }
            if (this.buttonPressed != 0 || ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event)) {
                z = true;
            } else {
                z = false;
            }
            return z;
        }

        @SuppressLint({"DrawAllocation"})
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = AndroidUtilities.dp(54.0f);
            if (this.currentBlock != null) {
                if (this.currentBlock.level > 0) {
                    this.textX = AndroidUtilities.dp((float) (this.currentBlock.level * 14)) + AndroidUtilities.dp(18.0f);
                } else {
                    this.textX = AndroidUtilities.dp(18.0f);
                }
                int textWidth = (width - this.textX) - AndroidUtilities.dp(18.0f);
                int size = AndroidUtilities.dp(40.0f);
                this.buttonX = AndroidUtilities.dp(16.0f);
                this.buttonY = AndroidUtilities.dp(7.0f);
                this.currentBlock.caption = new TLRPC$TL_textPlain();
                this.radialProgress.setProgressRect(this.buttonX, this.buttonY, this.buttonX + size, this.buttonY + size);
                if (this.lastCreatedWidth != width) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, textWidth, this.currentBlock);
                    if (this.textLayout != null) {
                        height += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                if (!this.isFirst && this.currentBlock.level <= 0) {
                    height += AndroidUtilities.dp(8.0f);
                }
                String author = this.currentMessageObject.getMusicAuthor(false);
                String title = this.currentMessageObject.getMusicTitle(false);
                this.seekBarX = (this.buttonX + AndroidUtilities.dp(50.0f)) + size;
                int w = (width - this.seekBarX) - AndroidUtilities.dp(18.0f);
                if (TextUtils.isEmpty(title) && TextUtils.isEmpty(author)) {
                    this.titleLayout = null;
                    this.seekBarY = this.buttonY + ((size - AndroidUtilities.dp(30.0f)) / 2);
                } else {
                    SpannableStringBuilder stringBuilder;
                    if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(author)) {
                        stringBuilder = new SpannableStringBuilder(String.format("%s - %s", new Object[]{author, title}));
                    } else if (TextUtils.isEmpty(title)) {
                        stringBuilder = new SpannableStringBuilder(author);
                    } else {
                        stringBuilder = new SpannableStringBuilder(title);
                    }
                    if (!TextUtils.isEmpty(author)) {
                        stringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), 0, author.length(), 18);
                    }
                    this.titleLayout = new StaticLayout(TextUtils.ellipsize(stringBuilder, Theme.chat_audioTitlePaint, (float) w, TruncateAt.END), ArticleViewer.audioTimePaint, w, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                    this.seekBarY = (this.buttonY + ((size - AndroidUtilities.dp(30.0f)) / 2)) + AndroidUtilities.dp(11.0f);
                }
                this.seekBar.setSize(w, AndroidUtilities.dp(30.0f));
            } else {
                height = 1;
            }
            setMeasuredDimension(width, height);
            updatePlayingMessageProgress();
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
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), 0.0f, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
        }

        private Drawable getDrawableForCurrentState() {
            return Theme.chat_ivStatesDrawable[this.buttonState][this.buttonPressed != 0 ? 1 : 0];
        }

        public void updatePlayingMessageProgress() {
            if (this.currentDocument != null && this.currentMessageObject != null) {
                if (!this.seekBar.isDragging()) {
                    this.seekBar.setProgress(this.currentMessageObject.audioProgress);
                }
                int duration = 0;
                if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                    duration = this.currentMessageObject.audioProgressSec;
                } else {
                    for (int a = 0; a < this.currentDocument.attributes.size(); a++) {
                        DocumentAttribute attribute = (DocumentAttribute) this.currentDocument.attributes.get(a);
                        if (attribute instanceof TLRPC$TL_documentAttributeAudio) {
                            duration = attribute.duration;
                            break;
                        }
                    }
                }
                String timeString = String.format("%d:%02d", new Object[]{Integer.valueOf(duration / 60), Integer.valueOf(duration % 60)});
                if (this.lastTimeString == null || !(this.lastTimeString == null || this.lastTimeString.equals(timeString))) {
                    this.lastTimeString = timeString;
                    ArticleViewer.audioTimePaint.setTextSize((float) AndroidUtilities.dp(16.0f));
                    this.durationLayout = new StaticLayout(timeString, ArticleViewer.audioTimePaint, (int) Math.ceil((double) ArticleViewer.audioTimePaint.measureText(timeString)), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                }
                ArticleViewer.audioTimePaint.setColor(ArticleViewer.this.getTextColor());
                invalidate();
            }
        }

        public void updateButtonState(boolean animated) {
            String fileName = FileLoader.getAttachFileName(this.currentDocument);
            boolean fileExists = FileLoader.getPathToAttach(this.currentDocument, true).exists();
            if (TextUtils.isEmpty(fileName)) {
                this.radialProgress.setBackground(null, false, false);
                return;
            }
            if (fileExists) {
                MediaController.getInstance().removeLoadingFileObserver(this);
                boolean playing = MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
                if (!playing || (playing && MediaController.getInstance().isMessagePaused())) {
                    this.buttonState = 0;
                } else {
                    this.buttonState = 1;
                }
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, animated);
            } else {
                MediaController.getInstance().addLoadingFileObserver(fileName, null, this);
                if (FileLoader.getInstance().isLoadingFile(fileName)) {
                    this.buttonState = 3;
                    Float progress = ImageLoader.getInstance().getFileProgress(fileName);
                    if (progress != null) {
                        this.radialProgress.setProgress(progress.floatValue(), animated);
                    } else {
                        this.radialProgress.setProgress(0.0f, animated);
                    }
                    this.radialProgress.setBackground(getDrawableForCurrentState(), true, animated);
                } else {
                    this.buttonState = 2;
                    this.radialProgress.setProgress(0.0f, animated);
                    this.radialProgress.setBackground(getDrawableForCurrentState(), false, animated);
                }
            }
            updatePlayingMessageProgress();
        }

        private void didPressedButton(boolean animated) {
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
                this.radialProgress.setProgress(0.0f, false);
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

        public void onFailedDownload(String fileName) {
            updateButtonState(true);
        }

        public void onSuccessDownload(String fileName) {
            this.radialProgress.setProgress(1.0f, true);
            updateButtonState(true);
        }

        public void onProgressUpload(String fileName, float progress, boolean isEncrypted) {
        }

        public void onProgressDownload(String fileName, float progress) {
            this.radialProgress.setProgress(progress, true);
            if (this.buttonState != 3) {
                updateButtonState(false);
            }
        }

        public int getObserverTag() {
            return this.TAG;
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

        public void setBlock(TLRPC$TL_pageBlockAuthorDate block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentBlock == null) {
                height = 1;
            } else if (this.lastCreatedWidth != width) {
                Spannable spannableAuthor;
                MetricAffectingSpan[] spans;
                CharSequence text;
                CharSequence author = ArticleViewer.this.getText(this.currentBlock.author, this.currentBlock.author, this.currentBlock);
                if (author instanceof Spannable) {
                    spannableAuthor = (Spannable) author;
                    spans = (MetricAffectingSpan[]) spannableAuthor.getSpans(0, author.length(), MetricAffectingSpan.class);
                } else {
                    spannableAuthor = null;
                    spans = null;
                }
                if (this.currentBlock.published_date != 0 && !TextUtils.isEmpty(author)) {
                    r16 = new Object[2];
                    r16[0] = LocaleController.getInstance().chatFullDate.format(((long) this.currentBlock.published_date) * 1000);
                    r16[1] = author;
                    text = LocaleController.formatString("ArticleDateByAuthor", R.string.ArticleDateByAuthor, r16);
                } else if (TextUtils.isEmpty(author)) {
                    text = LocaleController.getInstance().chatFullDate.format(((long) this.currentBlock.published_date) * 1000);
                } else {
                    text = LocaleController.formatString("ArticleByAuthor", R.string.ArticleByAuthor, new Object[]{author});
                }
                if (spans != null) {
                    try {
                        if (spans.length > 0) {
                            int idx = TextUtils.indexOf(text, author);
                            if (idx != -1) {
                                Spannable spannable = Factory.getInstance().newSpannable(text);
                                text = spannable;
                                for (int a = 0; a < spans.length; a++) {
                                    spannable.setSpan(spans[a], spannableAuthor.getSpanStart(spans[a]) + idx, spannableAuthor.getSpanEnd(spans[a]) + idx, 33);
                                }
                            }
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
                this.textLayout = ArticleViewer.this.createLayoutForText(text, null, width - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout != null) {
                    height = 0 + (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight());
                    if (ArticleViewer.this.isRtl == 1) {
                        this.textX = (int) Math.floor((double) ((((float) width) - this.textLayout.getLineWidth(0)) - ((float) AndroidUtilities.dp(16.0f))));
                    } else {
                        this.textX = AndroidUtilities.dp(18.0f);
                    }
                }
            }
            setMeasuredDimension(width, height);
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

        public void setBlock(TLRPC$TL_pageBlockBlockquote block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout2, this.textX, this.textY2) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentBlock == null) {
                height = 1;
            } else if (this.lastCreatedWidth != width) {
                int textWidth = width - AndroidUtilities.dp(50.0f);
                if (this.currentBlock.level > 0) {
                    textWidth -= AndroidUtilities.dp((float) (this.currentBlock.level * 14));
                }
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, textWidth, this.currentBlock);
                this.hasRtl = false;
                if (this.textLayout != null) {
                    height = 0 + (AndroidUtilities.dp(8.0f) + this.textLayout.getHeight());
                    int count = this.textLayout.getLineCount();
                    for (int a = 0; a < count; a++) {
                        if (this.textLayout.getLineLeft(a) > 0.0f) {
                            ArticleViewer.this.isRtl = 1;
                            this.hasRtl = true;
                            break;
                        }
                    }
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
                this.textLayout2 = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, textWidth, this.currentBlock);
                if (this.textLayout2 != null) {
                    this.textY2 = AndroidUtilities.dp(8.0f) + height;
                    height += AndroidUtilities.dp(8.0f) + this.textLayout2.getHeight();
                }
                if (height != 0) {
                    height += AndroidUtilities.dp(8.0f);
                }
            }
            setMeasuredDimension(width, height);
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
                    int x = getMeasuredWidth() - AndroidUtilities.dp(20.0f);
                    canvas.drawRect((float) x, (float) AndroidUtilities.dp(6.0f), (float) (AndroidUtilities.dp(2.0f) + x), (float) (getMeasuredHeight() - AndroidUtilities.dp(6.0f)), ArticleViewer.quoteLinePaint);
                } else {
                    canvas.drawRect((float) AndroidUtilities.dp((float) ((this.currentBlock.level * 14) + 18)), (float) AndroidUtilities.dp(6.0f), (float) AndroidUtilities.dp((float) ((this.currentBlock.level * 14) + 20)), (float) (getMeasuredHeight() - AndroidUtilities.dp(6.0f)), ArticleViewer.quoteLinePaint);
                }
                if (this.currentBlock.level > 0) {
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), 0.0f, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
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

        public BlockChannelCell(Context context, int type) {
            super(context);
            setWillNotDraw(false);
            this.backgroundPaint = new Paint();
            this.currentType = type;
            this.textView = new TextView(context);
            this.textView.setTextSize(1, 14.0f);
            this.textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.textView.setText(LocaleController.getString("ChannelJoin", R.string.ChannelJoin));
            this.textView.setGravity(19);
            addView(this.textView, LayoutHelper.createFrame(-2, 39, 53));
            this.textView.setOnClickListener(new OnClickListener(ArticleViewer.this) {
                public void onClick(View v) {
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

        public void setBlock(TLRPC$TL_pageBlockChannel block) {
            this.currentBlock = block;
            int color = ArticleViewer.this.getSelectedColor();
            if (this.currentType == 0) {
                this.textView.setTextColor(-14840360);
                if (color == 0) {
                    this.backgroundPaint.setColor(-526345);
                } else if (color == 1) {
                    this.backgroundPaint.setColor(-1712440);
                } else if (color == 2) {
                    this.backgroundPaint.setColor(-14277082);
                }
                this.imageView.setColorFilter(new PorterDuffColorFilter(-6710887, Mode.MULTIPLY));
            } else {
                this.textView.setTextColor(-1);
                this.backgroundPaint.setColor(2130706432);
                this.imageView.setColorFilter(new PorterDuffColorFilter(-1, Mode.MULTIPLY));
            }
            this.lastCreatedWidth = 0;
            TLRPC$Chat channel = MessagesController.getInstance().getChat(Integer.valueOf(block.channel.id));
            if (channel == null || channel.min) {
                ArticleViewer.this.loadChannel(this, block.channel);
                setState(1, false);
            } else {
                ArticleViewer.this.loadedChannel = channel;
                if (!channel.left || channel.kicked) {
                    setState(4, false);
                } else {
                    setState(0, false);
                }
            }
            requestLayout();
        }

        public void setState(int state, boolean animated) {
            float f = 1.0f;
            if (this.currentAnimation != null) {
                this.currentAnimation.cancel();
            }
            this.currentState = state;
            float f2;
            if (animated) {
                this.currentAnimation = new AnimatorSet();
                AnimatorSet animatorSet = this.currentAnimation;
                Animator[] animatorArr = new Animator[9];
                TextView textView = this.textView;
                String str = "alpha";
                float[] fArr = new float[1];
                fArr[0] = state == 0 ? 1.0f : 0.0f;
                animatorArr[0] = ObjectAnimator.ofFloat(textView, str, fArr);
                textView = this.textView;
                str = "scaleX";
                fArr = new float[1];
                if (state == 0) {
                    f2 = 1.0f;
                } else {
                    f2 = 0.1f;
                }
                fArr[0] = f2;
                animatorArr[1] = ObjectAnimator.ofFloat(textView, str, fArr);
                textView = this.textView;
                str = "scaleY";
                fArr = new float[1];
                if (state == 0) {
                    f2 = 1.0f;
                } else {
                    f2 = 0.1f;
                }
                fArr[0] = f2;
                animatorArr[2] = ObjectAnimator.ofFloat(textView, str, fArr);
                ContextProgressView contextProgressView = this.progressView;
                String str2 = "alpha";
                float[] fArr2 = new float[1];
                fArr2[0] = state == 1 ? 1.0f : 0.0f;
                animatorArr[3] = ObjectAnimator.ofFloat(contextProgressView, str2, fArr2);
                contextProgressView = this.progressView;
                str2 = "scaleX";
                fArr2 = new float[1];
                if (state == 1) {
                    f2 = 1.0f;
                } else {
                    f2 = 0.1f;
                }
                fArr2[0] = f2;
                animatorArr[4] = ObjectAnimator.ofFloat(contextProgressView, str2, fArr2);
                contextProgressView = this.progressView;
                str2 = "scaleY";
                fArr2 = new float[1];
                if (state == 1) {
                    f2 = 1.0f;
                } else {
                    f2 = 0.1f;
                }
                fArr2[0] = f2;
                animatorArr[5] = ObjectAnimator.ofFloat(contextProgressView, str2, fArr2);
                ImageView imageView = this.imageView;
                str2 = "alpha";
                fArr2 = new float[1];
                fArr2[0] = state == 2 ? 1.0f : 0.0f;
                animatorArr[6] = ObjectAnimator.ofFloat(imageView, str2, fArr2);
                imageView = this.imageView;
                str2 = "scaleX";
                fArr2 = new float[1];
                if (state == 2) {
                    f2 = 1.0f;
                } else {
                    f2 = 0.1f;
                }
                fArr2[0] = f2;
                animatorArr[7] = ObjectAnimator.ofFloat(imageView, str2, fArr2);
                ImageView imageView2 = this.imageView;
                str = "scaleY";
                fArr = new float[1];
                if (state != 2) {
                    f = 0.1f;
                }
                fArr[0] = f;
                animatorArr[8] = ObjectAnimator.ofFloat(imageView2, str, fArr);
                animatorSet.playTogether(animatorArr);
                this.currentAnimation.setDuration(150);
                this.currentAnimation.start();
                return;
            }
            this.textView.setAlpha(state == 0 ? 1.0f : 0.0f);
            TextView textView2 = this.textView;
            if (state == 0) {
                f2 = 1.0f;
            } else {
                f2 = 0.1f;
            }
            textView2.setScaleX(f2);
            textView2 = this.textView;
            if (state == 0) {
                f2 = 1.0f;
            } else {
                f2 = 0.1f;
            }
            textView2.setScaleY(f2);
            this.progressView.setAlpha(state == 1 ? 1.0f : 0.0f);
            ContextProgressView contextProgressView2 = this.progressView;
            if (state == 1) {
                f2 = 1.0f;
            } else {
                f2 = 0.1f;
            }
            contextProgressView2.setScaleX(f2);
            contextProgressView2 = this.progressView;
            if (state == 1) {
                f2 = 1.0f;
            } else {
                f2 = 0.1f;
            }
            contextProgressView2.setScaleY(f2);
            this.imageView.setAlpha(state == 2 ? 1.0f : 0.0f);
            ImageView imageView3 = this.imageView;
            if (state == 2) {
                f2 = 1.0f;
            } else {
                f2 = 0.1f;
            }
            imageView3.setScaleX(f2);
            ImageView imageView4 = this.imageView;
            if (state != 2) {
                f = 0.1f;
            }
            imageView4.setScaleY(f);
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (this.currentType != 0) {
                return super.onTouchEvent(event);
            }
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(width, AndroidUtilities.dp(48.0f));
            this.textView.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(39.0f), 1073741824));
            this.buttonWidth = this.textView.getMeasuredWidth();
            this.progressView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(39.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(39.0f), 1073741824));
            this.imageView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(39.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(39.0f), 1073741824));
            if (this.currentBlock != null && this.lastCreatedWidth != width) {
                this.textLayout = ArticleViewer.this.createLayoutForText(this.currentBlock.channel.title, null, (width - AndroidUtilities.dp(52.0f)) - this.buttonWidth, this.currentBlock);
                this.textX2 = (getMeasuredWidth() - this.textX) - this.buttonWidth;
            }
        }

        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            this.imageView.layout((this.textX2 + (this.buttonWidth / 2)) - AndroidUtilities.dp(19.0f), 0, (this.textX2 + (this.buttonWidth / 2)) + AndroidUtilities.dp(20.0f), AndroidUtilities.dp(39.0f));
            this.progressView.layout((this.textX2 + (this.buttonWidth / 2)) - AndroidUtilities.dp(19.0f), 0, (this.textX2 + (this.buttonWidth / 2)) + AndroidUtilities.dp(20.0f), AndroidUtilities.dp(39.0f));
            this.textView.layout(this.textX2, 0, this.textX2 + this.textView.getMeasuredWidth(), this.textView.getMeasuredHeight());
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                canvas.drawRect(0.0f, 0.0f, (float) getMeasuredWidth(), (float) AndroidUtilities.dp(39.0f), this.backgroundPaint);
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) this.textX, (float) this.textY);
                    ArticleViewer.this.drawLayoutLink(canvas, this.textLayout);
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
            }
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
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                    outRect.left = 0;
                    outRect.top = 0;
                    int dp = AndroidUtilities.dp(2.0f);
                    outRect.right = dp;
                    outRect.bottom = dp;
                }
            });
            RecyclerListView recyclerListView = this.innerListView;
            LayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            this.gridLayoutManager = gridLayoutManager;
            recyclerListView.setLayoutManager(gridLayoutManager);
            recyclerListView = this.innerListView;
            Adapter c20923 = new Adapter(ArticleViewer.this) {
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view;
                    switch (viewType) {
                        case 0:
                            view = new BlockPhotoCell(BlockCollageCell.this.getContext(), 2);
                            break;
                        default:
                            view = new BlockVideoCell(BlockCollageCell.this.getContext(), 2);
                            break;
                    }
                    return new Holder(view);
                }

                public void onBindViewHolder(ViewHolder holder, int position) {
                    switch (holder.getItemViewType()) {
                        case 0:
                            holder.itemView.setBlock((TLRPC$TL_pageBlockPhoto) BlockCollageCell.this.currentBlock.items.get(position), true, true);
                            return;
                        default:
                            holder.itemView.setBlock((TLRPC$TL_pageBlockVideo) BlockCollageCell.this.currentBlock.items.get(position), true, true);
                            return;
                    }
                }

                public int getItemCount() {
                    if (BlockCollageCell.this.currentBlock == null) {
                        return 0;
                    }
                    return BlockCollageCell.this.currentBlock.items.size();
                }

                public int getItemViewType(int position) {
                    if (((TLRPC$PageBlock) BlockCollageCell.this.currentBlock.items.get(position)) instanceof TLRPC$TL_pageBlockPhoto) {
                        return 0;
                    }
                    return 1;
                }
            };
            this.adapter = c20923;
            recyclerListView.setAdapter(c20923);
            addView(this.innerListView, LayoutHelper.createFrame(-1, -2.0f));
            setWillNotDraw(false);
        }

        public void setBlock(TLRPC$TL_pageBlockCollage block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            this.adapter.notifyDataSetChanged();
            int color = ArticleViewer.this.getSelectedColor();
            if (color == 0) {
                this.innerListView.setGlowColor(-657673);
            } else if (color == 1) {
                this.innerListView.setGlowColor(-659492);
            } else if (color == 2) {
                this.innerListView.setGlowColor(-15461356);
            }
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int height;
            this.inLayout = true;
            int width = MeasureSpec.getSize(widthMeasureSpec);
            if (this.currentBlock != null) {
                int textWidth;
                int listWidth = width;
                if (this.currentBlock.level > 0) {
                    int dp = AndroidUtilities.dp((float) (this.currentBlock.level * 14)) + AndroidUtilities.dp(18.0f);
                    this.listX = dp;
                    this.textX = dp;
                    listWidth -= this.listX + AndroidUtilities.dp(18.0f);
                    textWidth = listWidth;
                } else {
                    this.listX = 0;
                    this.textX = AndroidUtilities.dp(18.0f);
                    textWidth = width - AndroidUtilities.dp(36.0f);
                }
                int countPerRow = listWidth / AndroidUtilities.dp(100.0f);
                int rowCount = (int) Math.ceil((double) (((float) this.currentBlock.items.size()) / ((float) countPerRow)));
                int itemSize = listWidth / countPerRow;
                this.gridLayoutManager.setSpanCount(countPerRow);
                this.innerListView.measure(MeasureSpec.makeMeasureSpec((itemSize * countPerRow) + AndroidUtilities.dp(2.0f), 1073741824), MeasureSpec.makeMeasureSpec(itemSize * rowCount, 1073741824));
                height = (rowCount * itemSize) - AndroidUtilities.dp(2.0f);
                if (this.lastCreatedWidth != width) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, textWidth, this.currentBlock);
                    if (this.textLayout != null) {
                        this.textY = AndroidUtilities.dp(8.0f) + height;
                        height += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                if (this.currentBlock.level > 0 && !this.currentBlock.bottom) {
                    height += AndroidUtilities.dp(8.0f);
                }
            } else {
                height = 1;
            }
            setMeasuredDimension(width, height);
            this.inLayout = false;
        }

        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            this.innerListView.layout(this.listX, 0, this.listX + this.innerListView.getMeasuredWidth(), this.innerListView.getMeasuredHeight());
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
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), 0.0f, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
        }
    }

    private class BlockDividerCell extends View {
        private RectF rect = new RectF();

        public BlockDividerCell(Context context) {
            super(context);
            if (ArticleViewer.dividerPaint == null) {
                ArticleViewer.dividerPaint = new Paint();
                int color = ArticleViewer.this.getSelectedColor();
                if (color == 0) {
                    ArticleViewer.dividerPaint.setColor(-3288619);
                } else if (color == 1) {
                    ArticleViewer.dividerPaint.setColor(-4080987);
                } else if (color == 2) {
                    ArticleViewer.dividerPaint.setColor(-12303292);
                }
            }
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), AndroidUtilities.dp(18.0f));
        }

        protected void onDraw(Canvas canvas) {
            int width = getMeasuredWidth() / 3;
            this.rect.set((float) width, (float) AndroidUtilities.dp(8.0f), (float) (width * 2), (float) AndroidUtilities.dp(10.0f));
            canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(1.0f), (float) AndroidUtilities.dp(1.0f), ArticleViewer.dividerPaint);
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

            public boolean onTouchEvent(MotionEvent event) {
                if (BlockEmbedCell.this.currentBlock != null) {
                    if (BlockEmbedCell.this.currentBlock.allow_scrolling) {
                        requestDisallowInterceptTouchEvent(true);
                    } else {
                        ArticleViewer.this.windowView.requestDisallowInterceptTouchEvent(true);
                    }
                }
                return super.onTouchEvent(event);
            }
        }

        @SuppressLint({"SetJavaScriptEnabled"})
        public BlockEmbedCell(Context context) {
            super(context);
            setWillNotDraw(false);
            this.videoView = new WebPlayerView(context, false, false, new WebPlayerViewDelegate(ArticleViewer.this) {
                public void onInitFailed() {
                    BlockEmbedCell.this.webView.setVisibility(0);
                    BlockEmbedCell.this.videoView.setVisibility(4);
                    BlockEmbedCell.this.videoView.loadVideo(null, null, null, false);
                    HashMap<String, String> args = new HashMap();
                    args.put(HttpRequest.HEADER_REFERER, "http://youtube.com");
                    BlockEmbedCell.this.webView.loadUrl(BlockEmbedCell.this.currentBlock.url, args);
                }

                public void onVideoSizeChanged(float aspectRatio, int rotation) {
                    ArticleViewer.this.fullscreenAspectRatioView.setAspectRatio(aspectRatio, rotation);
                }

                public void onInlineSurfaceTextureReady() {
                }

                public TextureView onSwitchToFullscreen(View controlsView, boolean fullscreen, float aspectRatio, int rotation, boolean byButton) {
                    if (fullscreen) {
                        ArticleViewer.this.fullscreenAspectRatioView.addView(ArticleViewer.this.fullscreenTextureView, LayoutHelper.createFrame(-1, -1.0f));
                        ArticleViewer.this.fullscreenAspectRatioView.setVisibility(0);
                        ArticleViewer.this.fullscreenAspectRatioView.setAspectRatio(aspectRatio, rotation);
                        ArticleViewer.this.fullscreenedVideo = BlockEmbedCell.this.videoView;
                        ArticleViewer.this.fullscreenVideoContainer.addView(controlsView, LayoutHelper.createFrame(-1, -1.0f));
                        ArticleViewer.this.fullscreenVideoContainer.setVisibility(0);
                    } else {
                        ArticleViewer.this.fullscreenAspectRatioView.removeView(ArticleViewer.this.fullscreenTextureView);
                        ArticleViewer.this.fullscreenedVideo = null;
                        ArticleViewer.this.fullscreenAspectRatioView.setVisibility(8);
                        ArticleViewer.this.fullscreenVideoContainer.setVisibility(4);
                    }
                    return ArticleViewer.this.fullscreenTextureView;
                }

                public void prepareToSwitchInlineMode(boolean inline, Runnable switchInlineModeRunnable, float aspectRatio, boolean animated) {
                }

                public TextureView onSwitchInlineMode(View controlsView, boolean inline, float aspectRatio, int rotation, boolean animated) {
                    return null;
                }

                public void onSharePressed() {
                    if (ArticleViewer.this.parentActivity != null) {
                        ArticleViewer.this.showDialog(new ShareAlert(ArticleViewer.this.parentActivity, null, BlockEmbedCell.this.currentBlock.url, false, BlockEmbedCell.this.currentBlock.url, true));
                    }
                }

                public void onPlayStateChanged(WebPlayerView playerView, boolean playing) {
                    if (playing) {
                        if (!(ArticleViewer.this.currentPlayingVideo == null || ArticleViewer.this.currentPlayingVideo == playerView)) {
                            ArticleViewer.this.currentPlayingVideo.pause();
                        }
                        ArticleViewer.this.currentPlayingVideo = playerView;
                        try {
                            ArticleViewer.this.parentActivity.getWindow().addFlags(128);
                            return;
                        } catch (Exception e) {
                            FileLog.e(e);
                            return;
                        }
                    }
                    if (ArticleViewer.this.currentPlayingVideo == playerView) {
                        ArticleViewer.this.currentPlayingVideo = null;
                    }
                    try {
                        ArticleViewer.this.parentActivity.getWindow().clearFlags(128);
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                }

                public boolean checkInlinePermissons() {
                    return false;
                }

                public ViewGroup getTextureViewContainer() {
                    return null;
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
                class C20941 implements Runnable {
                    C20941() {
                    }

                    public void run() {
                        if (ArticleViewer.this.customView != null) {
                            ArticleViewer.this.fullscreenVideoContainer.addView(ArticleViewer.this.customView, LayoutHelper.createFrame(-1, -1.0f));
                            ArticleViewer.this.fullscreenVideoContainer.setVisibility(0);
                        }
                    }
                }

                public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
                    onShowCustomView(view, callback);
                }

                public void onShowCustomView(View view, CustomViewCallback callback) {
                    if (ArticleViewer.this.customView != null) {
                        callback.onCustomViewHidden();
                        return;
                    }
                    ArticleViewer.this.customView = view;
                    ArticleViewer.this.customViewCallback = callback;
                    AndroidUtilities.runOnUIThread(new C20941(), 100);
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
            });
            this.webView.setWebViewClient(new WebViewClient(ArticleViewer.this) {
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }

                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
            addView(this.webView);
        }

        public void destroyWebView(boolean completely) {
            try {
                this.webView.stopLoading();
                this.webView.loadUrl("about:blank");
                if (completely) {
                    this.webView.destroy();
                }
                this.currentBlock = null;
            } catch (Exception e) {
                FileLog.e(e);
            }
            this.videoView.destroy();
        }

        public void setBlock(TLRPC$TL_pageBlockEmbed block) {
            TLRPC$Photo thumb = null;
            TLRPC$TL_pageBlockEmbed previousBlock = this.currentBlock;
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            if (previousBlock != this.currentBlock) {
                try {
                    this.webView.loadUrl("about:blank");
                } catch (Exception e) {
                    FileLog.e(e);
                }
                try {
                    if (this.currentBlock.html != null) {
                        this.webView.loadDataWithBaseURL("https://telegram.org/embed", this.currentBlock.html, "text/html", "UTF-8", null);
                        this.videoView.setVisibility(4);
                        this.videoView.loadVideo(null, null, null, false);
                        this.webView.setVisibility(0);
                    } else {
                        if (this.currentBlock.poster_photo_id != 0) {
                            thumb = ArticleViewer.this.getPhotoWithId(this.currentBlock.poster_photo_id);
                        }
                        if (this.videoView.loadVideo(block.url, thumb, null, this.currentBlock.autoplay)) {
                            this.webView.setVisibility(4);
                            this.videoView.setVisibility(0);
                            this.webView.stopLoading();
                            this.webView.loadUrl("about:blank");
                        } else {
                            this.webView.setVisibility(0);
                            this.videoView.setVisibility(4);
                            this.videoView.loadVideo(null, null, null, false);
                            HashMap<String, String> args = new HashMap();
                            args.put(HttpRequest.HEADER_REFERER, "http://youtube.com");
                            this.webView.loadUrl(this.currentBlock.url, args);
                        }
                    }
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
            requestLayout();
        }

        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (!ArticleViewer.this.isVisible) {
                this.currentBlock = null;
            }
        }

        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int height;
            int width = MeasureSpec.getSize(widthMeasureSpec);
            if (this.currentBlock != null) {
                int textWidth;
                float scale;
                float dp;
                int listWidth = width;
                if (this.currentBlock.level > 0) {
                    int dp2 = AndroidUtilities.dp((float) (this.currentBlock.level * 14)) + AndroidUtilities.dp(18.0f);
                    this.listX = dp2;
                    this.textX = dp2;
                    listWidth -= this.listX + AndroidUtilities.dp(18.0f);
                    textWidth = listWidth;
                } else {
                    this.listX = 0;
                    this.textX = AndroidUtilities.dp(18.0f);
                    textWidth = width - AndroidUtilities.dp(36.0f);
                }
                if (this.currentBlock.w == 0) {
                    scale = 1.0f;
                } else {
                    scale = ((float) width) / ((float) this.currentBlock.w);
                }
                if (this.currentBlock.w == 0) {
                    dp = ((float) AndroidUtilities.dp((float) this.currentBlock.h)) * scale;
                } else {
                    dp = ((float) this.currentBlock.h) * scale;
                }
                height = (int) dp;
                this.webView.measure(MeasureSpec.makeMeasureSpec(listWidth, 1073741824), MeasureSpec.makeMeasureSpec(height, 1073741824));
                if (this.videoView.getParent() == this) {
                    this.videoView.measure(MeasureSpec.makeMeasureSpec(listWidth, 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(10.0f) + height, 1073741824));
                }
                if (this.lastCreatedWidth != width) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, textWidth, this.currentBlock);
                    if (this.textLayout != null) {
                        this.textY = AndroidUtilities.dp(8.0f) + height;
                        height += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                height += AndroidUtilities.dp(5.0f);
                if (this.currentBlock.level > 0 && !this.currentBlock.bottom) {
                    height += AndroidUtilities.dp(8.0f);
                } else if (this.currentBlock.level == 0 && this.textLayout != null) {
                    height += AndroidUtilities.dp(8.0f);
                }
            } else {
                height = 1;
            }
            setMeasuredDimension(width, height);
        }

        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            this.webView.layout(this.listX, 0, this.listX + this.webView.getMeasuredWidth(), this.webView.getMeasuredHeight());
            if (this.videoView.getParent() == this) {
                this.videoView.layout(this.listX, 0, this.listX + this.videoView.getMeasuredWidth(), this.videoView.getMeasuredHeight());
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
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), 0.0f, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
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

        public void setBlock(TLRPC$TL_pageBlockEmbedPost block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentBlock == null) {
                height = 1;
            } else if (this.lastCreatedWidth != width) {
                boolean z = this.currentBlock.author_photo_id != 0;
                this.avatarVisible = z;
                if (z) {
                    TLRPC$Photo photo = ArticleViewer.this.getPhotoWithId(this.currentBlock.author_photo_id);
                    z = photo != null;
                    this.avatarVisible = z;
                    if (z) {
                        this.avatarDrawable.setInfo(0, this.currentBlock.author, null, false);
                        this.avatarImageView.setImage(FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.dp(40.0f), true).location, String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(40), Integer.valueOf(40)}), this.avatarDrawable, 0, null, 1);
                    }
                }
                this.nameLayout = ArticleViewer.this.createLayoutForText(this.currentBlock.author, null, width - AndroidUtilities.dp((float) ((this.avatarVisible ? 54 : 0) + 50)), this.currentBlock);
                if (this.currentBlock.date != 0) {
                    this.dateLayout = ArticleViewer.this.createLayoutForText(LocaleController.getInstance().chatFullDate.format(((long) this.currentBlock.date) * 1000), null, width - AndroidUtilities.dp((float) ((this.avatarVisible ? 54 : 0) + 50)), this.currentBlock);
                } else {
                    this.dateLayout = null;
                }
                height = AndroidUtilities.dp(56.0f);
                if (this.currentBlock.text != null) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, width - AndroidUtilities.dp(50.0f), this.currentBlock);
                    if (this.textLayout != null) {
                        height += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                this.lineHeight = height;
            }
            setMeasuredDimension(width, height);
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

        public void setBlock(TLRPC$TL_pageBlockFooter block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentBlock != null) {
                if (this.currentBlock.level == 0) {
                    this.textY = AndroidUtilities.dp(8.0f);
                    this.textX = AndroidUtilities.dp(18.0f);
                } else {
                    this.textY = 0;
                    this.textX = AndroidUtilities.dp((float) ((this.currentBlock.level * 14) + 18));
                }
                if (this.lastCreatedWidth != width) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, (width - AndroidUtilities.dp(18.0f)) - this.textX, this.currentBlock);
                    if (this.textLayout != null) {
                        height = this.textLayout.getHeight();
                        height = this.currentBlock.level > 0 ? height + AndroidUtilities.dp(8.0f) : height + AndroidUtilities.dp(16.0f);
                    }
                }
            } else {
                height = 1;
            }
            setMeasuredDimension(width, height);
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
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), 0.0f, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
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

        public void setBlock(TLRPC$TL_pageBlockHeader block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentBlock == null) {
                height = 1;
            } else if (this.lastCreatedWidth != width) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, width - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout != null) {
                    height = 0 + (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight());
                }
            }
            setMeasuredDimension(width, height);
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

        public void setBlock(TLRPC$TL_pageBlockList block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            int count = this.textLayouts.size();
            int textX = AndroidUtilities.dp(36.0f);
            for (int a = 0; a < count; a++) {
                StaticLayout textLayout = (StaticLayout) this.textLayouts.get(a);
                if (ArticleViewer.this.checkLayoutForLinks(event, this, textLayout, textX, ((Integer) this.textYLayouts.get(a)).intValue())) {
                    return true;
                }
            }
            return super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            this.hasRtl = false;
            this.maxLetterWidth = 0;
            if (this.currentBlock == null) {
                height = 1;
            } else if (this.lastCreatedWidth != width) {
                int a;
                StaticLayout textLayout;
                this.textLayouts.clear();
                this.textYLayouts.clear();
                this.textNumLayouts.clear();
                int count = this.currentBlock.items.size();
                for (a = 0; a < count; a++) {
                    String num;
                    TLRPC$RichText item = (TLRPC$RichText) this.currentBlock.items.get(a);
                    if (a == 0) {
                        textLayout = ArticleViewer.this.createLayoutForText(null, item, (width - AndroidUtilities.dp(24.0f)) - this.maxLetterWidth, this.currentBlock);
                        if (textLayout != null) {
                            int lCount = textLayout.getLineCount();
                            for (int b = 0; b < lCount; b++) {
                                if (textLayout.getLineLeft(b) > 0.0f) {
                                    this.hasRtl = true;
                                    ArticleViewer.this.isRtl = 1;
                                    break;
                                }
                            }
                        }
                    }
                    if (!this.currentBlock.ordered) {
                        num = "•";
                    } else if (this.hasRtl) {
                        num = String.format(".%d", new Object[]{Integer.valueOf(a + 1)});
                    } else {
                        num = String.format("%d.", new Object[]{Integer.valueOf(a + 1)});
                    }
                    textLayout = ArticleViewer.this.createLayoutForText(num, item, width - AndroidUtilities.dp(54.0f), this.currentBlock);
                    this.textNumLayouts.add(textLayout);
                    if (this.currentBlock.ordered) {
                        if (textLayout != null) {
                            this.maxLetterWidth = Math.max(this.maxLetterWidth, (int) Math.ceil((double) textLayout.getLineWidth(0)));
                        }
                    } else if (a == 0) {
                        this.maxLetterWidth = AndroidUtilities.dp(12.0f);
                    }
                }
                for (a = 0; a < count; a++) {
                    height += AndroidUtilities.dp(8.0f);
                    textLayout = ArticleViewer.this.createLayoutForText(null, (TLRPC$RichText) this.currentBlock.items.get(a), (width - AndroidUtilities.dp(42.0f)) - this.maxLetterWidth, this.currentBlock);
                    this.textYLayouts.add(Integer.valueOf(height));
                    this.textLayouts.add(textLayout);
                    if (textLayout != null) {
                        height += textLayout.getHeight();
                    }
                }
                if (this.hasRtl) {
                    this.textX = AndroidUtilities.dp(18.0f);
                } else {
                    this.textX = AndroidUtilities.dp(24.0f) + this.maxLetterWidth;
                }
                height += AndroidUtilities.dp(8.0f);
            }
            setMeasuredDimension(width, height);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                int count = this.textLayouts.size();
                int width = getMeasuredWidth();
                for (int a = 0; a < count; a++) {
                    StaticLayout textLayout = (StaticLayout) this.textLayouts.get(a);
                    StaticLayout textLayout2 = (StaticLayout) this.textNumLayouts.get(a);
                    canvas.save();
                    if (this.hasRtl) {
                        canvas.translate((float) ((width - AndroidUtilities.dp(18.0f)) - ((int) Math.ceil((double) textLayout2.getLineWidth(0)))), (float) ((Integer) this.textYLayouts.get(a)).intValue());
                    } else {
                        canvas.translate((float) AndroidUtilities.dp(18.0f), (float) ((Integer) this.textYLayouts.get(a)).intValue());
                    }
                    if (textLayout2 != null) {
                        textLayout2.draw(canvas);
                    }
                    canvas.restore();
                    canvas.save();
                    canvas.translate((float) this.textX, (float) ((Integer) this.textYLayouts.get(a)).intValue());
                    ArticleViewer.this.drawLayoutLink(canvas, textLayout);
                    if (textLayout != null) {
                        textLayout.draw(canvas);
                    }
                    canvas.restore();
                }
            }
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

        public void setBlock(TLRPC$TL_pageBlockParagraph block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
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
                if (this.lastCreatedWidth != width) {
                    if (this.currentBlock.text != null) {
                        this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, (width - AndroidUtilities.dp(18.0f)) - this.textX, this.currentBlock);
                    } else if (this.currentBlock.caption != null) {
                        this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, (width - AndroidUtilities.dp(18.0f)) - this.textX, this.currentBlock);
                    }
                    if (this.textLayout != null) {
                        height = this.textLayout.getHeight();
                        height = this.currentBlock.level > 0 ? height + AndroidUtilities.dp(8.0f) : height + AndroidUtilities.dp(16.0f);
                    }
                }
            } else {
                height = 1;
            }
            setMeasuredDimension(width, height);
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
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), 0.0f, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
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
        private TLRPC$PageBlock parentBlock;
        private boolean photoPressed;
        private StaticLayout textLayout;
        private int textX;
        private int textY;

        public BlockPhotoCell(Context context, int type) {
            super(context);
            setWillNotDraw(false);
            this.channelCell = new BlockChannelCell(context, 1);
            addView(this.channelCell, LayoutHelper.createFrame(-1, -2.0f));
            this.currentType = type;
        }

        public void setBlock(TLRPC$TL_pageBlockPhoto block, boolean first, boolean last) {
            this.parentBlock = null;
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            this.isFirst = first;
            this.isLast = last;
            this.channelCell.setVisibility(4);
            requestLayout();
        }

        public void setParentBlock(TLRPC$PageBlock block) {
            this.parentBlock = block;
            if (ArticleViewer.this.channelBlock != null && (this.parentBlock instanceof TLRPC$TL_pageBlockCover)) {
                this.channelCell.setBlock(ArticleViewer.this.channelBlock);
                this.channelCell.setVisibility(0);
            }
        }

        public View getChannelCell() {
            return this.channelCell;
        }

        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            if (this.channelCell.getVisibility() != 0 || y <= this.channelCell.getTranslationY() || y >= this.channelCell.getTranslationY() + ((float) AndroidUtilities.dp(39.0f))) {
                boolean z;
                if (event.getAction() == 0 && this.imageView.isInsideImage(x, y)) {
                    this.photoPressed = true;
                } else if (event.getAction() == 1 && this.photoPressed) {
                    this.photoPressed = false;
                    ArticleViewer.this.openPhoto(this.currentBlock);
                } else if (event.getAction() == 3) {
                    this.photoPressed = false;
                }
                if (this.photoPressed || ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event)) {
                    z = true;
                } else {
                    z = false;
                }
                return z;
            } else if (ArticleViewer.this.channelBlock == null || event.getAction() != 1) {
                return true;
            } else {
                MessagesController.openByUserName(ArticleViewer.this.channelBlock.channel.username, ArticleViewer.this.parentFragment, 2);
                ArticleViewer.this.close(false, true);
                return true;
            }
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentType == 1) {
                width = ArticleViewer.this.listView.getWidth();
                height = ((View) getParent()).getMeasuredHeight();
            } else if (this.currentType == 2) {
                height = width;
            }
            if (this.currentBlock != null) {
                int photoX;
                int textWidth;
                TLRPC$Photo photo = ArticleViewer.this.getPhotoWithId(this.currentBlock.photo_id);
                int photoWidth = width;
                if (this.currentType != 0 || this.currentBlock.level <= 0) {
                    photoX = 0;
                    this.textX = AndroidUtilities.dp(18.0f);
                    textWidth = width - AndroidUtilities.dp(36.0f);
                } else {
                    photoX = AndroidUtilities.dp((float) (this.currentBlock.level * 14)) + AndroidUtilities.dp(18.0f);
                    this.textX = photoX;
                    photoWidth -= AndroidUtilities.dp(18.0f) + photoX;
                    textWidth = photoWidth;
                }
                if (photo != null) {
                    String filter;
                    String str;
                    TLRPC$PhotoSize image = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.getPhotoSize());
                    TLRPC$PhotoSize thumb = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, 80, true);
                    if (image == thumb) {
                        thumb = null;
                    }
                    if (this.currentType == 0) {
                        height = (int) (((float) image.f77h) * (((float) photoWidth) / ((float) image.f78w)));
                        if (this.parentBlock instanceof TLRPC$TL_pageBlockCover) {
                            height = Math.min(height, photoWidth);
                        } else {
                            int maxHeight = (int) (((float) (Math.max(ArticleViewer.this.listView.getMeasuredWidth(), ArticleViewer.this.listView.getMeasuredHeight()) - AndroidUtilities.dp(56.0f))) * 0.9f);
                            if (height > maxHeight) {
                                height = maxHeight;
                                photoWidth = (int) (((float) image.f78w) * (((float) height) / ((float) image.f77h)));
                                photoX += ((width - photoX) - photoWidth) / 2;
                            }
                        }
                    }
                    ImageReceiver imageReceiver = this.imageView;
                    int dp = (this.isFirst || this.currentType == 1 || this.currentType == 2 || this.currentBlock.level > 0) ? 0 : AndroidUtilities.dp(8.0f);
                    imageReceiver.setImageCoords(photoX, dp, photoWidth, height);
                    if (this.currentType == 0) {
                        filter = null;
                    } else {
                        filter = String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(photoWidth), Integer.valueOf(height)});
                    }
                    ImageReceiver imageReceiver2 = this.imageView;
                    TLObject tLObject = image.location;
                    TLRPC$FileLocation tLRPC$FileLocation = thumb != null ? thumb.location : null;
                    if (thumb != null) {
                        str = "80_80_b";
                    } else {
                        str = null;
                    }
                    imageReceiver2.setImage(tLObject, filter, tLRPC$FileLocation, str, image.size, null, 1);
                }
                if (this.currentType == 0 && this.lastCreatedWidth != width) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, textWidth, this.currentBlock);
                    if (this.textLayout != null) {
                        height += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                if (!this.isFirst && this.currentType == 0 && this.currentBlock.level <= 0) {
                    height += AndroidUtilities.dp(8.0f);
                }
                boolean nextIsChannel = (this.parentBlock instanceof TLRPC$TL_pageBlockCover) && ArticleViewer.this.blocks != null && ArticleViewer.this.blocks.size() > 1 && (ArticleViewer.this.blocks.get(1) instanceof TLRPC$TL_pageBlockChannel);
                if (!(this.currentType == 2 || nextIsChannel)) {
                    height += AndroidUtilities.dp(8.0f);
                }
            } else {
                height = 1;
            }
            this.channelCell.measure(widthMeasureSpec, heightMeasureSpec);
            this.channelCell.setTranslationY((float) (this.imageView.getImageHeight() - AndroidUtilities.dp(39.0f)));
            setMeasuredDimension(width, height);
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
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), 0.0f, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
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

        public void setBlock(TLRPC$TL_pageBlockPreformatted block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentBlock == null) {
                height = 1;
            } else if (this.lastCreatedWidth != width) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, width - AndroidUtilities.dp(24.0f), this.currentBlock);
                if (this.textLayout != null) {
                    height = 0 + (AndroidUtilities.dp(32.0f) + this.textLayout.getHeight());
                }
            }
            setMeasuredDimension(width, height);
        }

        protected void onDraw(Canvas canvas) {
            if (this.currentBlock != null) {
                canvas.drawRect(0.0f, (float) AndroidUtilities.dp(8.0f), (float) getMeasuredWidth(), (float) (getMeasuredHeight() - AndroidUtilities.dp(8.0f)), ArticleViewer.preformattedBackgroundPaint);
                if (this.textLayout != null) {
                    canvas.save();
                    canvas.translate((float) AndroidUtilities.dp(12.0f), (float) AndroidUtilities.dp(16.0f));
                    this.textLayout.draw(canvas);
                    canvas.restore();
                }
            }
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

        public void setBlock(TLRPC$TL_pageBlockPullquote block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout2, this.textX, this.textY2) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentBlock == null) {
                height = 1;
            } else if (this.lastCreatedWidth != width) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, width - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout != null) {
                    height = 0 + (AndroidUtilities.dp(8.0f) + this.textLayout.getHeight());
                }
                this.textLayout2 = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, width - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout2 != null) {
                    this.textY2 = AndroidUtilities.dp(2.0f) + height;
                    height += AndroidUtilities.dp(8.0f) + this.textLayout2.getHeight();
                }
                if (height != 0) {
                    height += AndroidUtilities.dp(8.0f);
                }
            }
            setMeasuredDimension(width, height);
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
    }

    private class BlockSlideshowCell extends FrameLayout {
        private PagerAdapter adapter;
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
                public boolean onTouchEvent(MotionEvent ev) {
                    return super.onTouchEvent(ev);
                }

                public boolean onInterceptTouchEvent(MotionEvent ev) {
                    ArticleViewer.this.windowView.requestDisallowInterceptTouchEvent(true);
                    return super.onInterceptTouchEvent(ev);
                }
            };
            this.innerListView.addOnPageChangeListener(new OnPageChangeListener(ArticleViewer.this) {
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int position) {
                    BlockSlideshowCell.this.dotsContainer.invalidate();
                }

                public void onPageScrollStateChanged(int state) {
                }
            });
            ViewPager viewPager = this.innerListView;
            PagerAdapter c20993 = new PagerAdapter(ArticleViewer.this) {

                /* renamed from: org.telegram.ui.ArticleViewer$BlockSlideshowCell$3$ObjectContainer */
                class ObjectContainer {
                    private TLRPC$PageBlock block;
                    private View view;

                    ObjectContainer() {
                    }
                }

                public int getCount() {
                    if (BlockSlideshowCell.this.currentBlock == null) {
                        return 0;
                    }
                    return BlockSlideshowCell.this.currentBlock.items.size();
                }

                public boolean isViewFromObject(View view, Object object) {
                    return ((ObjectContainer) object).view == view;
                }

                public int getItemPosition(Object object) {
                    if (BlockSlideshowCell.this.currentBlock.items.contains(((ObjectContainer) object).block)) {
                        return -1;
                    }
                    return -2;
                }

                public Object instantiateItem(ViewGroup container, int position) {
                    View view;
                    TLRPC$PageBlock block = (TLRPC$PageBlock) BlockSlideshowCell.this.currentBlock.items.get(position);
                    if (block instanceof TLRPC$TL_pageBlockPhoto) {
                        view = new BlockPhotoCell(BlockSlideshowCell.this.getContext(), 1);
                        ((BlockPhotoCell) view).setBlock((TLRPC$TL_pageBlockPhoto) block, true, true);
                    } else {
                        view = new BlockVideoCell(BlockSlideshowCell.this.getContext(), 1);
                        ((BlockVideoCell) view).setBlock((TLRPC$TL_pageBlockVideo) block, true, true);
                    }
                    container.addView(view);
                    ObjectContainer objectContainer = new ObjectContainer();
                    objectContainer.view = view;
                    objectContainer.block = block;
                    return objectContainer;
                }

                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(((ObjectContainer) object).view);
                }

                public void unregisterDataSetObserver(DataSetObserver observer) {
                    if (observer != null) {
                        super.unregisterDataSetObserver(observer);
                    }
                }
            };
            this.adapter = c20993;
            viewPager.setAdapter(c20993);
            int color = ArticleViewer.this.getSelectedColor();
            if (color == 0) {
                AndroidUtilities.setViewPagerEdgeEffectColor(this.innerListView, -657673);
            } else if (color == 1) {
                AndroidUtilities.setViewPagerEdgeEffectColor(this.innerListView, -659492);
            } else if (color == 2) {
                AndroidUtilities.setViewPagerEdgeEffectColor(this.innerListView, -15461356);
            }
            addView(this.innerListView);
            this.dotsContainer = new View(context, ArticleViewer.this) {
                protected void onDraw(Canvas canvas) {
                    if (BlockSlideshowCell.this.currentBlock != null) {
                        int selected = BlockSlideshowCell.this.innerListView.getCurrentItem();
                        int a = 0;
                        while (a < BlockSlideshowCell.this.currentBlock.items.size()) {
                            int cx = AndroidUtilities.dp(4.0f) + (AndroidUtilities.dp(13.0f) * a);
                            Drawable drawable = selected == a ? ArticleViewer.this.slideDotBigDrawable : ArticleViewer.this.slideDotDrawable;
                            drawable.setBounds(cx - AndroidUtilities.dp(5.0f), 0, AndroidUtilities.dp(5.0f) + cx, AndroidUtilities.dp(10.0f));
                            drawable.draw(canvas);
                            a++;
                        }
                    }
                }
            };
            addView(this.dotsContainer);
            setWillNotDraw(false);
        }

        public void setBlock(TLRPC$TL_pageBlockSlideshow block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            this.innerListView.setCurrentItem(0, false);
            this.adapter.notifyDataSetChanged();
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int height;
            int width = MeasureSpec.getSize(widthMeasureSpec);
            if (this.currentBlock != null) {
                height = AndroidUtilities.dp(310.0f);
                this.innerListView.measure(MeasureSpec.makeMeasureSpec(width, 1073741824), MeasureSpec.makeMeasureSpec(height, 1073741824));
                int count = this.currentBlock.items.size();
                this.dotsContainer.measure(MeasureSpec.makeMeasureSpec(((AndroidUtilities.dp(7.0f) * count) + ((count - 1) * AndroidUtilities.dp(6.0f))) + AndroidUtilities.dp(4.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(10.0f), 1073741824));
                if (this.lastCreatedWidth != width) {
                    this.textY = AndroidUtilities.dp(16.0f) + height;
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, width - AndroidUtilities.dp(36.0f), this.currentBlock);
                    if (this.textLayout != null) {
                        height += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                height += AndroidUtilities.dp(16.0f);
            } else {
                height = 1;
            }
            setMeasuredDimension(width, height);
        }

        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            this.innerListView.layout(0, AndroidUtilities.dp(8.0f), this.innerListView.getMeasuredWidth(), AndroidUtilities.dp(8.0f) + this.innerListView.getMeasuredHeight());
            int y = this.innerListView.getBottom() - AndroidUtilities.dp(23.0f);
            int x = ((right - left) - this.dotsContainer.getMeasuredWidth()) / 2;
            this.dotsContainer.layout(x, y, this.dotsContainer.getMeasuredWidth() + x, this.dotsContainer.getMeasuredHeight() + y);
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

        public void setBlock(TLRPC$TL_pageBlockSubheader block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentBlock == null) {
                height = 1;
            } else if (this.lastCreatedWidth != width) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, width - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout != null) {
                    height = 0 + (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight());
                }
            }
            setMeasuredDimension(width, height);
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

        public void setBlock(TLRPC$TL_pageBlockSubtitle block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentBlock == null) {
                height = 1;
            } else if (this.lastCreatedWidth != width) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, width - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout != null) {
                    height = 0 + (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight());
                }
            }
            setMeasuredDimension(width, height);
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

        public void setBlock(TLRPC$TL_pageBlockTitle block) {
            this.currentBlock = block;
            this.lastCreatedWidth = 0;
            requestLayout();
        }

        public boolean onTouchEvent(MotionEvent event) {
            return ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentBlock == null) {
                height = 1;
            } else if (this.lastCreatedWidth != width) {
                this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.text, width - AndroidUtilities.dp(36.0f), this.currentBlock);
                if (this.textLayout != null) {
                    height = 0 + (AndroidUtilities.dp(16.0f) + this.textLayout.getHeight());
                    if (ArticleViewer.this.isRtl == -1) {
                        int count = this.textLayout.getLineCount();
                        for (int a = 0; a < count; a++) {
                            if (this.textLayout.getLineLeft(a) > 0.0f) {
                                ArticleViewer.this.isRtl = 1;
                                break;
                            }
                        }
                        if (ArticleViewer.this.isRtl == -1) {
                            ArticleViewer.this.isRtl = 0;
                        }
                    }
                }
                if (this.currentBlock.first) {
                    height += AndroidUtilities.dp(8.0f);
                    this.textY = AndroidUtilities.dp(16.0f);
                } else {
                    this.textY = AndroidUtilities.dp(8.0f);
                }
            }
            setMeasuredDimension(width, height);
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
    }

    private class BlockVideoCell extends FrameLayout implements MediaController$FileDownloadProgressListener {
        private int TAG;
        private int buttonPressed;
        private int buttonState;
        private int buttonX;
        private int buttonY;
        private boolean cancelLoading;
        private BlockChannelCell channelCell;
        private TLRPC$TL_pageBlockVideo currentBlock;
        private TLRPC$Document currentDocument;
        private int currentType;
        private ImageReceiver imageView = new ImageReceiver(this);
        private boolean isFirst;
        private boolean isGif;
        private boolean isLast;
        private int lastCreatedWidth;
        private TLRPC$PageBlock parentBlock;
        private boolean photoPressed;
        private RadialProgress radialProgress;
        private StaticLayout textLayout;
        private int textX;
        private int textY;

        public BlockVideoCell(Context context, int type) {
            super(context);
            setWillNotDraw(false);
            this.currentType = type;
            this.radialProgress = new RadialProgress(this);
            this.radialProgress.setAlphaForPrevious(true);
            this.radialProgress.setProgressColor(-1);
            this.TAG = MediaController.getInstance().generateObserverTag();
            this.channelCell = new BlockChannelCell(context, 1);
            addView(this.channelCell, LayoutHelper.createFrame(-1, -2.0f));
        }

        public void setBlock(TLRPC$TL_pageBlockVideo block, boolean first, boolean last) {
            this.currentBlock = block;
            this.parentBlock = null;
            this.cancelLoading = false;
            this.currentDocument = ArticleViewer.this.getDocumentWithId(this.currentBlock.video_id);
            this.isGif = MessageObject.isGifDocument(this.currentDocument);
            this.lastCreatedWidth = 0;
            this.isFirst = first;
            this.isLast = last;
            this.channelCell.setVisibility(4);
            updateButtonState(false);
            requestLayout();
        }

        public void setParentBlock(TLRPC$PageBlock block) {
            this.parentBlock = block;
            if (ArticleViewer.this.channelBlock != null && (this.parentBlock instanceof TLRPC$TL_pageBlockCover)) {
                this.channelCell.setBlock(ArticleViewer.this.channelBlock);
                this.channelCell.setVisibility(0);
            }
        }

        public View getChannelCell() {
            return this.channelCell;
        }

        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            if (this.channelCell.getVisibility() != 0 || y <= this.channelCell.getTranslationY() || y >= this.channelCell.getTranslationY() + ((float) AndroidUtilities.dp(39.0f))) {
                boolean z;
                if (event.getAction() == 0 && this.imageView.isInsideImage(x, y)) {
                    if ((this.buttonState == -1 || x < ((float) this.buttonX) || x > ((float) (this.buttonX + AndroidUtilities.dp(48.0f))) || y < ((float) this.buttonY) || y > ((float) (this.buttonY + AndroidUtilities.dp(48.0f)))) && this.buttonState != 0) {
                        this.photoPressed = true;
                    } else {
                        this.buttonPressed = 1;
                        invalidate();
                    }
                } else if (event.getAction() == 1) {
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
                } else if (event.getAction() == 3) {
                    this.photoPressed = false;
                }
                if (this.photoPressed || this.buttonPressed != 0 || ArticleViewer.this.checkLayoutForLinks(event, this, this.textLayout, this.textX, this.textY) || super.onTouchEvent(event)) {
                    z = true;
                } else {
                    z = false;
                }
                return z;
            } else if (ArticleViewer.this.channelBlock == null || event.getAction() != 1) {
                return true;
            } else {
                MessagesController.openByUserName(ArticleViewer.this.channelBlock.channel.username, ArticleViewer.this.parentFragment, 2);
                ArticleViewer.this.close(false, true);
                return true;
            }
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (this.currentType == 1) {
                width = ArticleViewer.this.listView.getWidth();
                height = ((View) getParent()).getMeasuredHeight();
            } else if (this.currentType == 2) {
                height = width;
            }
            if (this.currentBlock != null) {
                int photoX;
                int textWidth;
                int photoWidth = width;
                if (this.currentType != 0 || this.currentBlock.level <= 0) {
                    photoX = 0;
                    this.textX = AndroidUtilities.dp(18.0f);
                    textWidth = width - AndroidUtilities.dp(36.0f);
                } else {
                    photoX = AndroidUtilities.dp((float) (this.currentBlock.level * 14)) + AndroidUtilities.dp(18.0f);
                    this.textX = photoX;
                    photoWidth -= AndroidUtilities.dp(18.0f) + photoX;
                    textWidth = photoWidth;
                }
                if (this.currentDocument != null) {
                    TLRPC$PhotoSize thumb = this.currentDocument.thumb;
                    if (this.currentType == 0) {
                        height = (int) (((float) thumb.f77h) * (((float) photoWidth) / ((float) thumb.f78w)));
                        if (this.parentBlock instanceof TLRPC$TL_pageBlockCover) {
                            height = Math.min(height, photoWidth);
                        } else {
                            int maxHeight = (int) (((float) (Math.max(ArticleViewer.this.listView.getMeasuredWidth(), ArticleViewer.this.listView.getMeasuredHeight()) - AndroidUtilities.dp(56.0f))) * 0.9f);
                            if (height > maxHeight) {
                                height = maxHeight;
                                photoWidth = (int) (((float) thumb.f78w) * (((float) height) / ((float) thumb.f77h)));
                                photoX += ((width - photoX) - photoWidth) / 2;
                            }
                        }
                    }
                    ImageReceiver imageReceiver = this.imageView;
                    int dp = (this.isFirst || this.currentType == 1 || this.currentType == 2 || this.currentBlock.level > 0) ? 0 : AndroidUtilities.dp(8.0f);
                    imageReceiver.setImageCoords(photoX, dp, photoWidth, height);
                    if (this.isGif) {
                        this.imageView.setImage(this.currentDocument, String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(photoWidth), Integer.valueOf(height)}), thumb != null ? thumb.location : null, thumb != null ? "80_80_b" : null, this.currentDocument.size, null, 1);
                    } else {
                        this.imageView.setImage(null, null, thumb != null ? thumb.location : null, thumb != null ? "80_80_b" : null, 0, null, 1);
                    }
                    int size = AndroidUtilities.dp(48.0f);
                    this.buttonX = (int) (((float) this.imageView.getImageX()) + (((float) (this.imageView.getImageWidth() - size)) / 2.0f));
                    this.buttonY = (int) (((float) this.imageView.getImageY()) + (((float) (this.imageView.getImageHeight() - size)) / 2.0f));
                    this.radialProgress.setProgressRect(this.buttonX, this.buttonY, this.buttonX + size, this.buttonY + size);
                }
                if (this.currentType == 0 && this.lastCreatedWidth != width) {
                    this.textLayout = ArticleViewer.this.createLayoutForText(null, this.currentBlock.caption, textWidth, this.currentBlock);
                    if (this.textLayout != null) {
                        height += AndroidUtilities.dp(8.0f) + this.textLayout.getHeight();
                    }
                }
                if (!this.isFirst && this.currentType == 0 && this.currentBlock.level <= 0) {
                    height += AndroidUtilities.dp(8.0f);
                }
                boolean nextIsChannel = (this.parentBlock instanceof TLRPC$TL_pageBlockCover) && ArticleViewer.this.blocks != null && ArticleViewer.this.blocks.size() > 1 && (ArticleViewer.this.blocks.get(1) instanceof TLRPC$TL_pageBlockChannel);
                if (!(this.currentType == 2 || nextIsChannel)) {
                    height += AndroidUtilities.dp(8.0f);
                }
            } else {
                height = 1;
            }
            this.channelCell.measure(widthMeasureSpec, heightMeasureSpec);
            this.channelCell.setTranslationY((float) (this.imageView.getImageHeight() - AndroidUtilities.dp(39.0f)));
            setMeasuredDimension(width, height);
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
                    canvas.drawRect((float) AndroidUtilities.dp(18.0f), 0.0f, (float) AndroidUtilities.dp(20.0f), (float) (getMeasuredHeight() - (this.currentBlock.bottom ? AndroidUtilities.dp(6.0f) : 0)), ArticleViewer.quoteLinePaint);
                }
            }
        }

        private Drawable getDrawableForCurrentState() {
            if (this.buttonState < 0 || this.buttonState >= 4) {
                return null;
            }
            return Theme.chat_photoStatesDrawables[this.buttonState][this.buttonPressed];
        }

        public void updateButtonState(boolean animated) {
            String fileName = FileLoader.getAttachFileName(this.currentDocument);
            boolean fileExists = FileLoader.getPathToAttach(this.currentDocument, true).exists();
            if (TextUtils.isEmpty(fileName)) {
                this.radialProgress.setBackground(null, false, false);
            } else if (fileExists) {
                MediaController.getInstance().removeLoadingFileObserver(this);
                if (this.isGif) {
                    this.buttonState = -1;
                } else {
                    this.buttonState = 3;
                }
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, animated);
                invalidate();
            } else {
                MediaController.getInstance().addLoadingFileObserver(fileName, null, this);
                float setProgress = 0.0f;
                boolean progressVisible = false;
                if (FileLoader.getInstance().isLoadingFile(fileName)) {
                    progressVisible = true;
                    this.buttonState = 1;
                    Float progress = ImageLoader.getInstance().getFileProgress(fileName);
                    setProgress = progress != null ? progress.floatValue() : 0.0f;
                } else if (this.cancelLoading || !this.isGif) {
                    this.buttonState = 0;
                } else {
                    progressVisible = true;
                    this.buttonState = 1;
                }
                this.radialProgress.setBackground(getDrawableForCurrentState(), progressVisible, animated);
                this.radialProgress.setProgress(setProgress, false);
                invalidate();
            }
        }

        private void didPressedButton(boolean animated) {
            if (this.buttonState == 0) {
                this.cancelLoading = false;
                this.radialProgress.setProgress(0.0f, false);
                if (this.isGif) {
                    this.imageView.setImage(this.currentDocument, null, this.currentDocument.thumb != null ? this.currentDocument.thumb.location : null, "80_80_b", this.currentDocument.size, null, 1);
                } else {
                    FileLoader.getInstance().loadFile(this.currentDocument, true, 1);
                }
                this.buttonState = 1;
                this.radialProgress.setBackground(getDrawableForCurrentState(), true, animated);
                invalidate();
            } else if (this.buttonState == 1) {
                this.cancelLoading = true;
                if (this.isGif) {
                    this.imageView.cancelLoadImage();
                } else {
                    FileLoader.getInstance().cancelLoadFile(this.currentDocument);
                }
                this.buttonState = 0;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, animated);
                invalidate();
            } else if (this.buttonState == 2) {
                this.imageView.setAllowStartAnimation(true);
                this.imageView.startAnimation();
                this.buttonState = -1;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, animated);
            } else if (this.buttonState == 3) {
                ArticleViewer.this.openPhoto(this.currentBlock);
            }
        }

        public void onFailedDownload(String fileName) {
            updateButtonState(false);
        }

        public void onSuccessDownload(String fileName) {
            this.radialProgress.setProgress(1.0f, true);
            if (this.isGif) {
                this.buttonState = 2;
                didPressedButton(true);
                return;
            }
            updateButtonState(true);
        }

        public void onProgressUpload(String fileName, float progress, boolean isEncrypted) {
        }

        public void onProgressDownload(String fileName, float progress) {
            this.radialProgress.setProgress(progress, true);
            if (this.buttonState != 1) {
                updateButtonState(false);
            }
        }

        public int getObserverTag() {
            return this.TAG;
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
                    final String urlFinal = ArticleViewer.this.pressedLink.getUrl();
                    Builder builder = new Builder(ArticleViewer.this.parentActivity);
                    builder.setTitle(urlFinal);
                    builder.setItems(new CharSequence[]{LocaleController.getString("Open", R.string.Open), LocaleController.getString("Copy", R.string.Copy)}, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (ArticleViewer.this.parentActivity != null) {
                                if (which == 0) {
                                    Browser.openUrl(ArticleViewer.this.parentActivity, urlFinal);
                                } else if (which == 1) {
                                    String url = urlFinal;
                                    if (url.startsWith("mailto:")) {
                                        url = url.substring(7);
                                    } else if (url.startsWith("tel:")) {
                                        url = url.substring(4);
                                    }
                                    AndroidUtilities.addToClipboard(url);
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
                    int y = (ArticleViewer.this.pressedLinkOwnerView.getTop() - AndroidUtilities.dp(54.0f)) + ArticleViewer.this.pressedLayoutY;
                    if (y < 0) {
                        y *= -1;
                    }
                    ArticleViewer.this.pressedLinkOwnerView.invalidate();
                    ArticleViewer.this.drawBlockSelection = true;
                    ArticleViewer.this.showPopup(ArticleViewer.this.pressedLinkOwnerView, 48, 0, y);
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

        public ColorCell(ArticleViewer this$0, Context context) {
            int i;
            int i2 = 5;
            this.this$0 = this$0;
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
            TextView textView = this.textView;
            if (LocaleController.isRTL) {
                i = 5;
            } else {
                i = 3;
            }
            textView.setGravity(i | 16);
            this.textView.setPadding(0, 0, 0, AndroidUtilities.dp(1.0f));
            View view = this.textView;
            if (!LocaleController.isRTL) {
                i2 = 3;
            }
            addView(view, LayoutHelper.createFrame(-1, -1.0f, i2 | 48, (float) (LocaleController.isRTL ? 17 : 53), 0.0f, (float) (LocaleController.isRTL ? 53 : 17), 0.0f));
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
        }

        public void setTextAndColor(String text, int color) {
            this.textView.setText(text);
            this.currentColor = color;
            invalidate();
        }

        public void select(boolean value) {
            if (this.selected != value) {
                this.selected = value;
                invalidate();
            }
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
    }

    public class FontCell extends FrameLayout {
        private TextView textView;
        private TextView textView2;
        final /* synthetic */ ArticleViewer this$0;

        public FontCell(ArticleViewer this$0, Context context) {
            int i;
            int i2;
            int i3 = 5;
            this.this$0 = this$0;
            super(context);
            setBackgroundDrawable(Theme.createSelectorDrawable(251658240, 2));
            this.textView = new TextView(context);
            this.textView.setTextColor(-14606047);
            this.textView.setTextSize(1, 16.0f);
            this.textView.setLines(1);
            this.textView.setMaxLines(1);
            this.textView.setSingleLine(true);
            this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            View view = this.textView;
            if (LocaleController.isRTL) {
                i = 5;
            } else {
                i = 3;
            }
            addView(view, LayoutHelper.createFrame(-1, -1.0f, i | 48, (float) (LocaleController.isRTL ? 17 : 53), 0.0f, (float) (LocaleController.isRTL ? 53 : 17), 0.0f));
            this.textView2 = new TextView(context);
            this.textView2.setTextColor(-14606047);
            this.textView2.setTextSize(1, 16.0f);
            this.textView2.setLines(1);
            this.textView2.setMaxLines(1);
            this.textView2.setSingleLine(true);
            this.textView2.setText("Aa");
            TextView textView = this.textView2;
            if (LocaleController.isRTL) {
                i2 = 5;
            } else {
                i2 = 3;
            }
            textView.setGravity(i2 | 16);
            view = this.textView2;
            if (!LocaleController.isRTL) {
                i3 = 3;
            }
            addView(view, LayoutHelper.createFrame(-1, -1.0f, i3 | 48, 17.0f, 0.0f, 17.0f, 0.0f));
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
        }

        public void select(boolean value) {
            this.textView2.setTextColor(value ? -15428119 : -14606047);
        }

        public void setTextAndTypeface(String text, Typeface typeface) {
            this.textView.setText(text);
            this.textView.setTypeface(typeface);
            this.textView2.setTypeface(typeface);
            invalidate();
        }
    }

    private class PhotoBackgroundDrawable extends ColorDrawable {
        private Runnable drawRunnable;

        public PhotoBackgroundDrawable(int color) {
            super(color);
        }

        public void setAlpha(int alpha) {
            if (ArticleViewer.this.parentActivity instanceof LaunchActivity) {
                DrawerLayoutContainer drawerLayoutContainer = ((LaunchActivity) ArticleViewer.this.parentActivity).drawerLayoutContainer;
                boolean z = (ArticleViewer.this.isPhotoVisible && alpha == 255) ? false : true;
                drawerLayoutContainer.setAllowDrawContent(z);
            }
            super.setAlpha(alpha);
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (getAlpha() != 0 && this.drawRunnable != null) {
                this.drawRunnable.run();
                this.drawRunnable = null;
            }
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
        private float animatedProgressValue = 0.0f;
        private float animationProgressStart = 0.0f;
        private int backgroundState = -1;
        private float currentProgress = 0.0f;
        private long currentProgressTime = 0;
        private long lastUpdateTime = 0;
        private View parent = null;
        private int previousBackgroundState = -2;
        private RectF progressRect = new RectF();
        private float radOffset = 0.0f;
        private float scale = 1.0f;
        private int size = AndroidUtilities.dp(64.0f);

        public RadialProgressView(Context context, View parentView) {
            if (ArticleViewer.decelerateInterpolator == null) {
                ArticleViewer.decelerateInterpolator = new DecelerateInterpolator(1.5f);
                ArticleViewer.progressPaint = new Paint(1);
                ArticleViewer.progressPaint.setStyle(Style.STROKE);
                ArticleViewer.progressPaint.setStrokeCap(Cap.ROUND);
                ArticleViewer.progressPaint.setStrokeWidth((float) AndroidUtilities.dp(3.0f));
                ArticleViewer.progressPaint.setColor(-1);
            }
            this.parent = parentView;
        }

        private void updateAnimation() {
            long newTime = System.currentTimeMillis();
            long dt = newTime - this.lastUpdateTime;
            this.lastUpdateTime = newTime;
            if (this.animatedProgressValue != 1.0f) {
                this.radOffset += ((float) (360 * dt)) / 3000.0f;
                float progressDiff = this.currentProgress - this.animationProgressStart;
                if (progressDiff > 0.0f) {
                    this.currentProgressTime += dt;
                    if (this.currentProgressTime >= 300) {
                        this.animatedProgressValue = this.currentProgress;
                        this.animationProgressStart = this.currentProgress;
                        this.currentProgressTime = 0;
                    } else {
                        this.animatedProgressValue = this.animationProgressStart + (ArticleViewer.decelerateInterpolator.getInterpolation(((float) this.currentProgressTime) / 300.0f) * progressDiff);
                    }
                }
                this.parent.invalidate();
            }
            if (this.animatedProgressValue >= 1.0f && this.previousBackgroundState != -2) {
                this.animatedAlphaValue -= ((float) dt) / 200.0f;
                if (this.animatedAlphaValue <= 0.0f) {
                    this.animatedAlphaValue = 0.0f;
                    this.previousBackgroundState = -2;
                }
                this.parent.invalidate();
            }
        }

        public void setProgress(float value, boolean animated) {
            if (animated) {
                this.animationProgressStart = this.animatedProgressValue;
            } else {
                this.animatedProgressValue = value;
                this.animationProgressStart = value;
            }
            this.currentProgress = value;
            this.currentProgressTime = 0;
        }

        public void setBackgroundState(int state, boolean animated) {
            this.lastUpdateTime = System.currentTimeMillis();
            if (!animated || this.backgroundState == state) {
                this.previousBackgroundState = -2;
            } else {
                this.previousBackgroundState = this.backgroundState;
                this.animatedAlphaValue = 1.0f;
            }
            this.backgroundState = state;
            this.parent.invalidate();
        }

        public void setAlpha(float value) {
            this.alpha = value;
        }

        public void setScale(float value) {
            this.scale = value;
        }

        public void onDraw(Canvas canvas) {
            Drawable drawable;
            int sizeScaled = (int) (((float) this.size) * this.scale);
            int x = (ArticleViewer.this.getContainerViewWidth() - sizeScaled) / 2;
            int y = (ArticleViewer.this.getContainerViewHeight() - sizeScaled) / 2;
            if (this.previousBackgroundState >= 0 && this.previousBackgroundState < 4) {
                drawable = ArticleViewer.progressDrawables[this.previousBackgroundState];
                if (drawable != null) {
                    drawable.setAlpha((int) ((this.animatedAlphaValue * 255.0f) * this.alpha));
                    drawable.setBounds(x, y, x + sizeScaled, y + sizeScaled);
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
                    drawable.setBounds(x, y, x + sizeScaled, y + sizeScaled);
                    drawable.draw(canvas);
                }
            }
            if (this.backgroundState == 0 || this.backgroundState == 1 || this.previousBackgroundState == 0 || this.previousBackgroundState == 1) {
                int diff = AndroidUtilities.dp(4.0f);
                if (this.previousBackgroundState != -2) {
                    ArticleViewer.progressPaint.setAlpha((int) ((this.animatedAlphaValue * 255.0f) * this.alpha));
                } else {
                    ArticleViewer.progressPaint.setAlpha((int) (this.alpha * 255.0f));
                }
                this.progressRect.set((float) (x + diff), (float) (y + diff), (float) ((x + sizeScaled) - diff), (float) ((y + sizeScaled) - diff));
                canvas.drawArc(this.progressRect, this.radOffset - 0.049804688f, Math.max(4.0f, 360.0f * this.animatedProgressValue), false, ArticleViewer.progressPaint);
                updateAnimation();
            }
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

        public boolean onTouchEvent(MotionEvent event) {
            boolean z = false;
            float x = event.getX();
            int a;
            int cx;
            if (event.getAction() == 0) {
                getParent().requestDisallowInterceptTouchEvent(true);
                a = 0;
                while (a < 5) {
                    cx = (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * a)) + (this.circleSize / 2);
                    if (x <= ((float) (cx - AndroidUtilities.dp(15.0f))) || x >= ((float) (AndroidUtilities.dp(15.0f) + cx))) {
                        a++;
                    } else {
                        if (a == ArticleViewer.this.selectedFontSize) {
                            z = true;
                        }
                        this.startMoving = z;
                        this.startX = x;
                        this.startMovingQuality = ArticleViewer.this.selectedFontSize;
                    }
                }
            } else if (event.getAction() == 2) {
                if (this.startMoving) {
                    if (Math.abs(this.startX - x) >= AndroidUtilities.getPixelsInCM(0.5f, true)) {
                        this.moving = true;
                        this.startMoving = false;
                    }
                } else if (this.moving) {
                    a = 0;
                    while (a < 5) {
                        cx = (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * a)) + (this.circleSize / 2);
                        int diff = ((this.lineSize / 2) + (this.circleSize / 2)) + this.gapSize;
                        if (x <= ((float) (cx - diff)) || x >= ((float) (cx + diff))) {
                            a++;
                        } else if (ArticleViewer.this.selectedFontSize != a) {
                            ArticleViewer.this.selectedFontSize = a;
                            ArticleViewer.this.updatePaintSize();
                            invalidate();
                        }
                    }
                }
            } else if (event.getAction() == 1 || event.getAction() == 3) {
                if (!this.moving) {
                    a = 0;
                    while (a < 5) {
                        cx = (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * a)) + (this.circleSize / 2);
                        if (x <= ((float) (cx - AndroidUtilities.dp(15.0f))) || x >= ((float) (AndroidUtilities.dp(15.0f) + cx))) {
                            a++;
                        } else if (ArticleViewer.this.selectedFontSize != a) {
                            ArticleViewer.this.selectedFontSize = a;
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

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = MeasureSpec.getSize(widthMeasureSpec);
            this.circleSize = AndroidUtilities.dp(5.0f);
            this.gapSize = AndroidUtilities.dp(2.0f);
            this.sideSide = AndroidUtilities.dp(17.0f);
            this.lineSize = (((getMeasuredWidth() - (this.circleSize * 5)) - (this.gapSize * 8)) - (this.sideSide * 2)) / 4;
        }

        protected void onDraw(Canvas canvas) {
            int cy = getMeasuredHeight() / 2;
            int a = 0;
            while (a < 5) {
                int cx = (this.sideSide + (((this.lineSize + (this.gapSize * 2)) + this.circleSize) * a)) + (this.circleSize / 2);
                if (a <= ArticleViewer.this.selectedFontSize) {
                    this.paint.setColor(-15428119);
                } else {
                    this.paint.setColor(-3355444);
                }
                canvas.drawCircle((float) cx, (float) cy, a == ArticleViewer.this.selectedFontSize ? (float) AndroidUtilities.dp(4.0f) : (float) (this.circleSize / 2), this.paint);
                if (a != 0) {
                    int x = ((cx - (this.circleSize / 2)) - this.gapSize) - this.lineSize;
                    canvas.drawRect((float) x, (float) (cy - AndroidUtilities.dp(1.0f)), (float) (this.lineSize + x), (float) (AndroidUtilities.dp(1.0f) + cy), this.paint);
                }
                a++;
            }
        }
    }

    private class WebpageAdapter extends SelectionAdapter {
        private Context context;

        public WebpageAdapter(Context ctx) {
            this.context = ctx;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new BlockParagraphCell(this.context);
                    break;
                case 1:
                    view = new BlockHeaderCell(this.context);
                    break;
                case 2:
                    view = new BlockDividerCell(this.context);
                    break;
                case 3:
                    view = new BlockEmbedCell(this.context);
                    break;
                case 4:
                    view = new BlockSubtitleCell(this.context);
                    break;
                case 5:
                    view = new BlockVideoCell(this.context, 0);
                    break;
                case 6:
                    view = new BlockPullquoteCell(this.context);
                    break;
                case 7:
                    view = new BlockBlockquoteCell(this.context);
                    break;
                case 8:
                    view = new BlockSlideshowCell(this.context);
                    break;
                case 9:
                    view = new BlockPhotoCell(this.context, 0);
                    break;
                case 10:
                    view = new BlockAuthorDateCell(this.context);
                    break;
                case 11:
                    view = new BlockTitleCell(this.context);
                    break;
                case 12:
                    view = new BlockListCell(this.context);
                    break;
                case 13:
                    view = new BlockFooterCell(this.context);
                    break;
                case 14:
                    view = new BlockPreformattedCell(this.context);
                    break;
                case 15:
                    view = new BlockSubheaderCell(this.context);
                    break;
                case 16:
                    view = new BlockEmbedPostCell(this.context);
                    break;
                case 17:
                    view = new BlockCollageCell(this.context);
                    break;
                case 18:
                    view = new BlockChannelCell(this.context, 0);
                    break;
                case 19:
                    view = new BlockAudioCell(this.context);
                    break;
                default:
                    View frameLayout = new FrameLayout(this.context) {
                        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(44.0f), 1073741824));
                        }
                    };
                    frameLayout.setTag(Integer.valueOf(90));
                    TextView textView = new TextView(this.context);
                    frameLayout.addView(textView, LayoutHelper.createFrame(-1, 34.0f, 51, 0.0f, 10.0f, 0.0f, 0.0f));
                    textView.setText(LocaleController.getString("PreviewFeedback", R.string.PreviewFeedback));
                    textView.setTextSize(1, 12.0f);
                    textView.setGravity(17);
                    view = frameLayout;
                    break;
            }
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            return new Holder(view);
        }

        public boolean isEnabled(ViewHolder holder) {
            return false;
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            boolean z = true;
            if (position < ArticleViewer.this.blocks.size()) {
                TLRPC$PageBlock block = (TLRPC$PageBlock) ArticleViewer.this.blocks.get(position);
                TLRPC$PageBlock originalBlock = block;
                if (block instanceof TLRPC$TL_pageBlockCover) {
                    block = block.cover;
                }
                boolean z2;
                switch (holder.getItemViewType()) {
                    case 0:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockParagraph) block);
                        return;
                    case 1:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockHeader) block);
                        return;
                    case 2:
                        BlockDividerCell cell = holder.itemView;
                        return;
                    case 3:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockEmbed) block);
                        return;
                    case 4:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockSubtitle) block);
                        return;
                    case 5:
                        BlockVideoCell cell2 = holder.itemView;
                        TLRPC$TL_pageBlockVideo tLRPC$TL_pageBlockVideo = (TLRPC$TL_pageBlockVideo) block;
                        if (position == 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (position != ArticleViewer.this.blocks.size() - 1) {
                            z = false;
                        }
                        cell2.setBlock(tLRPC$TL_pageBlockVideo, z2, z);
                        cell2.setParentBlock(originalBlock);
                        return;
                    case 6:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockPullquote) block);
                        return;
                    case 7:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockBlockquote) block);
                        return;
                    case 8:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockSlideshow) block);
                        return;
                    case 9:
                        BlockPhotoCell cell3 = holder.itemView;
                        TLRPC$TL_pageBlockPhoto tLRPC$TL_pageBlockPhoto = (TLRPC$TL_pageBlockPhoto) block;
                        if (position == 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (position != ArticleViewer.this.blocks.size() - 1) {
                            z = false;
                        }
                        cell3.setBlock(tLRPC$TL_pageBlockPhoto, z2, z);
                        cell3.setParentBlock(originalBlock);
                        return;
                    case 10:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockAuthorDate) block);
                        return;
                    case 11:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockTitle) block);
                        return;
                    case 12:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockList) block);
                        return;
                    case 13:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockFooter) block);
                        return;
                    case 14:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockPreformatted) block);
                        return;
                    case 15:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockSubheader) block);
                        return;
                    case 16:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockEmbedPost) block);
                        return;
                    case 17:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockCollage) block);
                        return;
                    case 18:
                        holder.itemView.setBlock((TLRPC$TL_pageBlockChannel) block);
                        return;
                    case 19:
                        BlockAudioCell cell4 = holder.itemView;
                        TLRPC$TL_pageBlockAudio tLRPC$TL_pageBlockAudio = (TLRPC$TL_pageBlockAudio) block;
                        z2 = position == 0;
                        if (position != ArticleViewer.this.blocks.size() - 1) {
                            z = false;
                        }
                        cell4.setBlock(tLRPC$TL_pageBlockAudio, z2, z);
                        return;
                    default:
                        return;
                }
            }
            switch (holder.getItemViewType()) {
                case 90:
                    TextView textView = (TextView) ((ViewGroup) holder.itemView).getChildAt(0);
                    int color = ArticleViewer.this.getSelectedColor();
                    if (color == 0) {
                        textView.setTextColor(-8879475);
                        textView.setBackgroundColor(-1183760);
                        return;
                    } else if (color == 1) {
                        textView.setTextColor(ArticleViewer.this.getGrayTextColor());
                        textView.setBackgroundColor(-1712440);
                        return;
                    } else if (color == 2) {
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

        private int getTypeForBlock(TLRPC$PageBlock block) {
            if (block instanceof TLRPC$TL_pageBlockParagraph) {
                return 0;
            }
            if (block instanceof TLRPC$TL_pageBlockHeader) {
                return 1;
            }
            if (block instanceof TLRPC$TL_pageBlockDivider) {
                return 2;
            }
            if (block instanceof TLRPC$TL_pageBlockEmbed) {
                return 3;
            }
            if (block instanceof TLRPC$TL_pageBlockSubtitle) {
                return 4;
            }
            if (block instanceof TLRPC$TL_pageBlockVideo) {
                return 5;
            }
            if (block instanceof TLRPC$TL_pageBlockPullquote) {
                return 6;
            }
            if (block instanceof TLRPC$TL_pageBlockBlockquote) {
                return 7;
            }
            if (block instanceof TLRPC$TL_pageBlockSlideshow) {
                return 8;
            }
            if (block instanceof TLRPC$TL_pageBlockPhoto) {
                return 9;
            }
            if (block instanceof TLRPC$TL_pageBlockAuthorDate) {
                return 10;
            }
            if (block instanceof TLRPC$TL_pageBlockTitle) {
                return 11;
            }
            if (block instanceof TLRPC$TL_pageBlockList) {
                return 12;
            }
            if (block instanceof TLRPC$TL_pageBlockFooter) {
                return 13;
            }
            if (block instanceof TLRPC$TL_pageBlockPreformatted) {
                return 14;
            }
            if (block instanceof TLRPC$TL_pageBlockSubheader) {
                return 15;
            }
            if (block instanceof TLRPC$TL_pageBlockEmbedPost) {
                return 16;
            }
            if (block instanceof TLRPC$TL_pageBlockCollage) {
                return 17;
            }
            if (block instanceof TLRPC$TL_pageBlockChannel) {
                return 18;
            }
            if (block instanceof TLRPC$TL_pageBlockAudio) {
                return 19;
            }
            if (block instanceof TLRPC$TL_pageBlockCover) {
                return getTypeForBlock(block.cover);
            }
            return 0;
        }

        public int getItemViewType(int position) {
            if (position == ArticleViewer.this.blocks.size()) {
                return 90;
            }
            return getTypeForBlock((TLRPC$PageBlock) ArticleViewer.this.blocks.get(position));
        }

        public int getItemCount() {
            return (ArticleViewer.this.currentPage == null || ArticleViewer.this.currentPage.cached_page == null) ? 0 : ArticleViewer.this.blocks.size() + 1;
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

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            if (VERSION.SDK_INT < 21 || ArticleViewer.this.lastInsets == null) {
                setMeasuredDimension(widthSize, heightSize);
            } else {
                setMeasuredDimension(widthSize, heightSize);
                WindowInsets insets = (WindowInsets) ArticleViewer.this.lastInsets;
                if (AndroidUtilities.incorrectDisplaySizeFix) {
                    if (heightSize > AndroidUtilities.displaySize.y) {
                        heightSize = AndroidUtilities.displaySize.y;
                    }
                    heightSize += AndroidUtilities.statusBarHeight;
                }
                heightSize -= insets.getSystemWindowInsetBottom();
                widthSize -= insets.getSystemWindowInsetRight() + insets.getSystemWindowInsetLeft();
                if (insets.getSystemWindowInsetRight() != 0) {
                    ArticleViewer.this.barBackground.measure(MeasureSpec.makeMeasureSpec(insets.getSystemWindowInsetRight(), 1073741824), MeasureSpec.makeMeasureSpec(heightSize, 1073741824));
                } else if (insets.getSystemWindowInsetLeft() != 0) {
                    ArticleViewer.this.barBackground.measure(MeasureSpec.makeMeasureSpec(insets.getSystemWindowInsetLeft(), 1073741824), MeasureSpec.makeMeasureSpec(heightSize, 1073741824));
                } else {
                    ArticleViewer.this.barBackground.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(insets.getSystemWindowInsetBottom(), 1073741824));
                }
            }
            ArticleViewer.this.containerView.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(heightSize, 1073741824));
            ArticleViewer.this.photoContainerView.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(heightSize, 1073741824));
            ArticleViewer.this.photoContainerBackground.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(heightSize, 1073741824));
            ArticleViewer.this.fullscreenVideoContainer.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(heightSize, 1073741824));
            ViewGroup.LayoutParams layoutParams = ArticleViewer.this.animatingImageView.getLayoutParams();
            ArticleViewer.this.animatingImageView.measure(MeasureSpec.makeMeasureSpec(layoutParams.width, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(layoutParams.height, Integer.MIN_VALUE));
        }

        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            if (!this.selfLayout) {
                int x;
                if (VERSION.SDK_INT < 21 || ArticleViewer.this.lastInsets == null) {
                    x = 0;
                } else {
                    WindowInsets insets = (WindowInsets) ArticleViewer.this.lastInsets;
                    x = insets.getSystemWindowInsetLeft();
                    if (insets.getSystemWindowInsetRight() != 0) {
                        ArticleViewer.this.barBackground.layout((right - left) - insets.getSystemWindowInsetRight(), 0, right - left, bottom - top);
                    } else if (insets.getSystemWindowInsetLeft() != 0) {
                        ArticleViewer.this.barBackground.layout(0, 0, insets.getSystemWindowInsetLeft(), bottom - top);
                    } else {
                        ArticleViewer.this.barBackground.layout(0, (bottom - top) - insets.getStableInsetBottom(), right - left, bottom - top);
                    }
                }
                ArticleViewer.this.containerView.layout(x, 0, ArticleViewer.this.containerView.getMeasuredWidth() + x, ArticleViewer.this.containerView.getMeasuredHeight());
                ArticleViewer.this.photoContainerView.layout(x, 0, ArticleViewer.this.photoContainerView.getMeasuredWidth() + x, ArticleViewer.this.photoContainerView.getMeasuredHeight());
                ArticleViewer.this.photoContainerBackground.layout(x, 0, ArticleViewer.this.photoContainerBackground.getMeasuredWidth() + x, ArticleViewer.this.photoContainerBackground.getMeasuredHeight());
                ArticleViewer.this.fullscreenVideoContainer.layout(x, 0, ArticleViewer.this.fullscreenVideoContainer.getMeasuredWidth() + x, ArticleViewer.this.fullscreenVideoContainer.getMeasuredHeight());
                ArticleViewer.this.animatingImageView.layout(0, 0, ArticleViewer.this.animatingImageView.getMeasuredWidth(), ArticleViewer.this.animatingImageView.getMeasuredHeight());
            }
        }

        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            ArticleViewer.this.attachedToWindow = true;
        }

        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            ArticleViewer.this.attachedToWindow = false;
        }

        public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            handleTouchEvent(null);
            super.requestDisallowInterceptTouchEvent(disallowIntercept);
        }

        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return !ArticleViewer.this.collapsed && (handleTouchEvent(ev) || super.onInterceptTouchEvent(ev));
        }

        public boolean onTouchEvent(MotionEvent event) {
            return !ArticleViewer.this.collapsed && (handleTouchEvent(event) || super.onTouchEvent(event));
        }

        public void setInnerTranslationX(float value) {
            this.innerTranslationX = value;
            if (ArticleViewer.this.parentActivity instanceof LaunchActivity) {
                DrawerLayoutContainer drawerLayoutContainer = ((LaunchActivity) ArticleViewer.this.parentActivity).drawerLayoutContainer;
                boolean z = (ArticleViewer.this.isVisible && this.alpha == 1.0f && this.innerTranslationX == 0.0f) ? false : true;
                drawerLayoutContainer.setAllowDrawContent(z);
            }
            invalidate();
        }

        protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
            int width = getMeasuredWidth();
            int translationX = (int) this.innerTranslationX;
            int restoreCount = canvas.save();
            canvas.clipRect(translationX, 0, width, getHeight());
            boolean result = super.drawChild(canvas, child, drawingTime);
            canvas.restoreToCount(restoreCount);
            if (translationX != 0 && child == ArticleViewer.this.containerView) {
                float opacity = Math.min(0.8f, ((float) (width - translationX)) / ((float) width));
                if (opacity < 0.0f) {
                    opacity = 0.0f;
                }
                ArticleViewer.this.scrimPaint.setColor(((int) (153.0f * opacity)) << 24);
                canvas.drawRect(0.0f, 0.0f, (float) translationX, (float) getHeight(), ArticleViewer.this.scrimPaint);
                float alpha = Math.max(0.0f, Math.min(((float) (width - translationX)) / ((float) AndroidUtilities.dp(20.0f)), 1.0f));
                ArticleViewer.this.layerShadowDrawable.setBounds(translationX - ArticleViewer.this.layerShadowDrawable.getIntrinsicWidth(), child.getTop(), translationX, child.getBottom());
                ArticleViewer.this.layerShadowDrawable.setAlpha((int) (255.0f * alpha));
                ArticleViewer.this.layerShadowDrawable.draw(canvas);
            }
            return result;
        }

        public float getInnerTranslationX() {
            return this.innerTranslationX;
        }

        private void prepareForMoving(MotionEvent ev) {
            this.maybeStartTracking = false;
            this.startedTracking = true;
            this.startedTrackingX = (int) ev.getX();
        }

        public boolean handleTouchEvent(MotionEvent event) {
            if (ArticleViewer.this.isPhotoVisible || this.closeAnimationInProgress || ArticleViewer.this.fullscreenVideoContainer.getVisibility() == 0) {
                return false;
            }
            if (event != null && event.getAction() == 0 && !this.startedTracking && !this.maybeStartTracking) {
                this.startedTrackingPointerId = event.getPointerId(0);
                this.maybeStartTracking = true;
                this.startedTrackingX = (int) event.getX();
                this.startedTrackingY = (int) event.getY();
                if (this.tracker != null) {
                    this.tracker.clear();
                }
            } else if (event != null && event.getAction() == 2 && event.getPointerId(0) == this.startedTrackingPointerId) {
                if (this.tracker == null) {
                    this.tracker = VelocityTracker.obtain();
                }
                int dx = Math.max(0, (int) (event.getX() - ((float) this.startedTrackingX)));
                int dy = Math.abs(((int) event.getY()) - this.startedTrackingY);
                this.tracker.addMovement(event);
                if (this.maybeStartTracking && !this.startedTracking && ((float) dx) >= AndroidUtilities.getPixelsInCM(0.4f, true) && Math.abs(dx) / 3 > dy) {
                    prepareForMoving(event);
                } else if (this.startedTracking) {
                    ArticleViewer.this.containerView.setTranslationX((float) dx);
                    setInnerTranslationX((float) dx);
                }
            } else if (event != null && event.getPointerId(0) == this.startedTrackingPointerId && (event.getAction() == 3 || event.getAction() == 1 || event.getAction() == 6)) {
                float velX;
                if (this.tracker == null) {
                    this.tracker = VelocityTracker.obtain();
                }
                this.tracker.computeCurrentVelocity(1000);
                if (!this.startedTracking) {
                    velX = this.tracker.getXVelocity();
                    float velY = this.tracker.getYVelocity();
                    if (velX >= 3500.0f && velX > Math.abs(velY)) {
                        prepareForMoving(event);
                    }
                }
                if (this.startedTracking) {
                    float distToMove;
                    float x = ArticleViewer.this.containerView.getX();
                    AnimatorSet animatorSet = new AnimatorSet();
                    velX = this.tracker.getXVelocity();
                    final boolean backAnimation = x < ((float) ArticleViewer.this.containerView.getMeasuredWidth()) / 3.0f && (velX < 3500.0f || velX < this.tracker.getYVelocity());
                    Animator[] animatorArr;
                    if (backAnimation) {
                        distToMove = x;
                        animatorArr = new Animator[2];
                        animatorArr[0] = ObjectAnimator.ofFloat(ArticleViewer.this.containerView, "translationX", new float[]{0.0f});
                        animatorArr[1] = ObjectAnimator.ofFloat(this, "innerTranslationX", new float[]{0.0f});
                        animatorSet.playTogether(animatorArr);
                    } else {
                        distToMove = ((float) ArticleViewer.this.containerView.getMeasuredWidth()) - x;
                        animatorArr = new Animator[2];
                        animatorArr[0] = ObjectAnimator.ofFloat(ArticleViewer.this.containerView, "translationX", new float[]{(float) ArticleViewer.this.containerView.getMeasuredWidth()});
                        animatorArr[1] = ObjectAnimator.ofFloat(this, "innerTranslationX", new float[]{(float) ArticleViewer.this.containerView.getMeasuredWidth()});
                        animatorSet.playTogether(animatorArr);
                    }
                    animatorSet.setDuration((long) Math.max((int) ((200.0f / ((float) ArticleViewer.this.containerView.getMeasuredWidth())) * distToMove), 50));
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            if (!backAnimation) {
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
            } else if (event == null) {
                this.maybeStartTracking = false;
                this.startedTracking = false;
                if (this.tracker != null) {
                    this.tracker.recycle();
                    this.tracker = null;
                }
            }
            return this.startedTracking;
        }

        protected void onDraw(Canvas canvas) {
            canvas.drawRect(this.innerTranslationX, 0.0f, (float) getMeasuredWidth(), (float) getMeasuredHeight(), ArticleViewer.this.backgroundPaint);
        }

        public void setAlpha(float value) {
            ArticleViewer.this.backgroundPaint.setAlpha((int) (255.0f * value));
            this.alpha = value;
            if (ArticleViewer.this.parentActivity instanceof LaunchActivity) {
                DrawerLayoutContainer drawerLayoutContainer = ((LaunchActivity) ArticleViewer.this.parentActivity).drawerLayoutContainer;
                boolean z = (ArticleViewer.this.isVisible && this.alpha == 1.0f && this.innerTranslationX == 0.0f) ? false : true;
                drawerLayoutContainer.setAllowDrawContent(z);
            }
            invalidate();
        }

        public float getAlpha() {
            return this.alpha;
        }
    }

    static /* synthetic */ int access$804(ArticleViewer x0) {
        int i = x0.pressCount + 1;
        x0.pressCount = i;
        return i;
    }

    public static ArticleViewer getInstance() {
        ArticleViewer localInstance = Instance;
        if (localInstance == null) {
            synchronized (ArticleViewer.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        ArticleViewer localInstance2 = new ArticleViewer();
                        try {
                            Instance = localInstance2;
                            localInstance = localInstance2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            localInstance = localInstance2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return localInstance;
    }

    private void showPopup(View parent, int gravity, int x, int y) {
        if (this.popupWindow == null || !this.popupWindow.isShowing()) {
            if (this.popupLayout == null) {
                this.popupRect = new Rect();
                this.popupLayout = new ActionBarPopupWindowLayout(this.parentActivity);
                this.popupLayout.setBackgroundDrawable(this.parentActivity.getResources().getDrawable(R.drawable.menu_copy));
                this.popupLayout.setAnimationEnabled(false);
                this.popupLayout.setOnTouchListener(new C20691());
                this.popupLayout.setDispatchKeyEventListener(new C20732());
                this.popupLayout.setShowedFromBotton(false);
                TextView deleteView = new TextView(this.parentActivity);
                deleteView.setTextColor(-16777216);
                deleteView.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                deleteView.setGravity(16);
                deleteView.setPadding(AndroidUtilities.dp(14.0f), 0, AndroidUtilities.dp(14.0f), 0);
                deleteView.setTextSize(1, 15.0f);
                deleteView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                deleteView.setText(LocaleController.getString("Copy", R.string.Copy).toUpperCase());
                deleteView.setOnClickListener(new C20783());
                this.popupLayout.addView(deleteView, LayoutHelper.createFrame(-2, 38.0f));
                this.popupWindow = new ActionBarPopupWindow(this.popupLayout, -2, -2);
                this.popupWindow.setAnimationEnabled(false);
                this.popupWindow.setAnimationStyle(R.style.PopupAnimation);
                this.popupWindow.setOutsideTouchable(true);
                this.popupWindow.setClippingEnabled(true);
                this.popupWindow.setInputMethodMode(2);
                this.popupWindow.setSoftInputMode(0);
                this.popupWindow.getContentView().setFocusableInTouchMode(true);
                this.popupWindow.setOnDismissListener(new C20814());
            }
            this.popupLayout.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE));
            this.popupWindow.setFocusable(true);
            this.popupWindow.showAtLocation(parent, gravity, x, y);
            this.popupWindow.startAnimation();
            return;
        }
        this.popupWindow.dismiss();
    }

    private void setRichTextParents(TLRPC$RichText parentRichText, TLRPC$RichText richText) {
        if (richText != null) {
            richText.parentRichText = parentRichText;
            if (richText instanceof TLRPC$TL_textFixed) {
                setRichTextParents(richText, ((TLRPC$TL_textFixed) richText).text);
            } else if (richText instanceof TLRPC$TL_textItalic) {
                setRichTextParents(richText, ((TLRPC$TL_textItalic) richText).text);
            } else if (richText instanceof TLRPC$TL_textBold) {
                setRichTextParents(richText, ((TLRPC$TL_textBold) richText).text);
            } else if (richText instanceof TLRPC$TL_textUnderline) {
                setRichTextParents(richText, ((TLRPC$TL_textUnderline) richText).text);
            } else if (richText instanceof TLRPC$TL_textStrike) {
                setRichTextParents(parentRichText, ((TLRPC$TL_textStrike) richText).text);
            } else if (richText instanceof TLRPC$TL_textEmail) {
                setRichTextParents(richText, ((TLRPC$TL_textEmail) richText).text);
            } else if (richText instanceof TLRPC$TL_textUrl) {
                setRichTextParents(richText, ((TLRPC$TL_textUrl) richText).text);
            } else if (richText instanceof TLRPC$TL_textConcat) {
                int count = richText.texts.size();
                for (int a = 0; a < count; a++) {
                    setRichTextParents(richText, (TLRPC$RichText) richText.texts.get(a));
                }
            }
        }
    }

    private void updateInterfaceForCurrentPage(boolean back) {
        if (this.currentPage != null && this.currentPage.cached_page != null) {
            this.isRtl = -1;
            this.channelBlock = null;
            this.blocks.clear();
            this.photoBlocks.clear();
            this.audioBlocks.clear();
            this.audioMessages.clear();
            int count = this.currentPage.cached_page.blocks.size();
            for (int a = 0; a < count; a++) {
                TLRPC$PageBlock block = (TLRPC$PageBlock) this.currentPage.cached_page.blocks.get(a);
                if (!(block instanceof TLRPC$TL_pageBlockUnsupported)) {
                    if (block instanceof TLRPC$TL_pageBlockAnchor) {
                        this.anchors.put(block.name.toLowerCase(), Integer.valueOf(this.blocks.size()));
                    } else {
                        if (block instanceof TLRPC$TL_pageBlockAudio) {
                            TLRPC$TL_message message = new TLRPC$TL_message();
                            message.out = true;
                            int i = ((int) this.currentPage.id) + a;
                            block.mid = i;
                            message.id = i;
                            message.to_id = new TLRPC$TL_peerUser();
                            TLRPC$Peer tLRPC$Peer = message.to_id;
                            int clientUserId = UserConfig.getClientUserId();
                            message.from_id = clientUserId;
                            tLRPC$Peer.user_id = clientUserId;
                            message.date = (int) (System.currentTimeMillis() / 1000);
                            message.message = "-1";
                            message.media = new TLRPC$TL_messageMediaDocument();
                            TLRPC$MessageMedia tLRPC$MessageMedia = message.media;
                            tLRPC$MessageMedia.flags |= 3;
                            message.media.document = getDocumentWithId(block.audio_id);
                            message.flags |= 768;
                            MessageObject messageObject = new MessageObject(message, null, false);
                            this.audioMessages.add(messageObject);
                            this.audioBlocks.put((TLRPC$TL_pageBlockAudio) block, messageObject);
                        }
                        setRichTextParents(null, block.text);
                        setRichTextParents(null, block.caption);
                        if (block instanceof TLRPC$TL_pageBlockAuthorDate) {
                            setRichTextParents(null, ((TLRPC$TL_pageBlockAuthorDate) block).author);
                        } else if (block instanceof TLRPC$TL_pageBlockCollage) {
                            TLRPC$TL_pageBlockCollage innerBlock = (TLRPC$TL_pageBlockCollage) block;
                            for (i = 0; i < innerBlock.items.size(); i++) {
                                setRichTextParents(null, ((TLRPC$PageBlock) innerBlock.items.get(i)).text);
                                setRichTextParents(null, ((TLRPC$PageBlock) innerBlock.items.get(i)).caption);
                            }
                        } else if (block instanceof TLRPC$TL_pageBlockList) {
                            TLRPC$TL_pageBlockList innerBlock2 = (TLRPC$TL_pageBlockList) block;
                            for (i = 0; i < innerBlock2.items.size(); i++) {
                                setRichTextParents(null, (TLRPC$RichText) innerBlock2.items.get(i));
                            }
                        } else if (block instanceof TLRPC$TL_pageBlockSlideshow) {
                            TLRPC$TL_pageBlockSlideshow innerBlock3 = (TLRPC$TL_pageBlockSlideshow) block;
                            for (i = 0; i < innerBlock3.items.size(); i++) {
                                setRichTextParents(null, ((TLRPC$PageBlock) innerBlock3.items.get(i)).text);
                                setRichTextParents(null, ((TLRPC$PageBlock) innerBlock3.items.get(i)).caption);
                            }
                        }
                        if (a == 0) {
                            block.first = true;
                            if ((block instanceof TLRPC$TL_pageBlockCover) && block.cover.caption != null && !(block.cover.caption instanceof TLRPC$TL_textEmpty) && count > 1) {
                                TLRPC$PageBlock next = (TLRPC$PageBlock) this.currentPage.cached_page.blocks.get(1);
                                if (next instanceof TLRPC$TL_pageBlockChannel) {
                                    this.channelBlock = (TLRPC$TL_pageBlockChannel) next;
                                }
                            }
                        } else if (a == 1 && this.channelBlock != null) {
                        }
                        addAllMediaFromBlock(block);
                        this.blocks.add(block);
                        if (block instanceof TLRPC$TL_pageBlockEmbedPost) {
                            if (!block.blocks.isEmpty()) {
                                block.level = -1;
                                for (int b = 0; b < block.blocks.size(); b++) {
                                    TLRPC$PageBlock innerBlock4 = (TLRPC$PageBlock) block.blocks.get(b);
                                    if (!(innerBlock4 instanceof TLRPC$TL_pageBlockUnsupported)) {
                                        if (innerBlock4 instanceof TLRPC$TL_pageBlockAnchor) {
                                            this.anchors.put(innerBlock4.name.toLowerCase(), Integer.valueOf(this.blocks.size()));
                                        } else {
                                            innerBlock4.level = 1;
                                            if (b == block.blocks.size() - 1) {
                                                innerBlock4.bottom = true;
                                            }
                                            this.blocks.add(innerBlock4);
                                            addAllMediaFromBlock(innerBlock4);
                                        }
                                    }
                                }
                            }
                            if (!(block.caption instanceof TLRPC$TL_textEmpty)) {
                                TLRPC$TL_pageBlockParagraph caption = new TLRPC$TL_pageBlockParagraph();
                                caption.caption = block.caption;
                                this.blocks.add(caption);
                            }
                        }
                    }
                }
            }
            this.adapter.notifyDataSetChanged();
            if (this.pagesStack.size() == 1 || back) {
                int offset;
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("articles", 0);
                String key = "article" + this.currentPage.id;
                int position = preferences.getInt(key, -1);
                if (preferences.getBoolean(key + "r", true) == (AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y)) {
                    offset = preferences.getInt(key + "o", 0) - this.listView.getPaddingTop();
                } else {
                    offset = AndroidUtilities.dp(10.0f);
                }
                if (position != -1) {
                    this.layoutManager.scrollToPositionWithOffset(position, offset);
                    return;
                }
                return;
            }
            this.layoutManager.scrollToPositionWithOffset(0, 0);
        }
    }

    private void addPageToStack(TLRPC$WebPage webPage, String anchor) {
        saveCurrentPagePosition();
        this.currentPage = webPage;
        this.pagesStack.add(webPage);
        updateInterfaceForCurrentPage(false);
        if (anchor != null) {
            Integer row = (Integer) this.anchors.get(anchor.toLowerCase());
            if (row != null) {
                this.layoutManager.scrollToPositionWithOffset(row.intValue(), 0);
            }
        }
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

    protected void startCheckLongPress() {
        if (!this.checkingForLongPress) {
            this.checkingForLongPress = true;
            if (this.pendingCheckForTap == null) {
                this.pendingCheckForTap = new CheckForTap();
            }
            this.windowView.postDelayed(this.pendingCheckForTap, (long) ViewConfiguration.getTapTimeout());
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

    private int getTextFlags(TLRPC$RichText richText) {
        if (richText instanceof TLRPC$TL_textFixed) {
            return getTextFlags(richText.parentRichText) | 4;
        }
        if (richText instanceof TLRPC$TL_textItalic) {
            return getTextFlags(richText.parentRichText) | 2;
        }
        if (richText instanceof TLRPC$TL_textBold) {
            return getTextFlags(richText.parentRichText) | 1;
        }
        if (richText instanceof TLRPC$TL_textUnderline) {
            return getTextFlags(richText.parentRichText) | 16;
        }
        if (richText instanceof TLRPC$TL_textStrike) {
            return getTextFlags(richText.parentRichText) | 32;
        }
        if (richText instanceof TLRPC$TL_textEmail) {
            return getTextFlags(richText.parentRichText) | 8;
        }
        if (richText instanceof TLRPC$TL_textUrl) {
            return getTextFlags(richText.parentRichText) | 8;
        }
        if (richText != null) {
            return getTextFlags(richText.parentRichText);
        }
        return 0;
    }

    private CharSequence getText(TLRPC$RichText parentRichText, TLRPC$RichText richText, TLRPC$PageBlock parentBlock) {
        if (richText instanceof TLRPC$TL_textFixed) {
            return getText(parentRichText, ((TLRPC$TL_textFixed) richText).text, parentBlock);
        }
        if (richText instanceof TLRPC$TL_textItalic) {
            return getText(parentRichText, ((TLRPC$TL_textItalic) richText).text, parentBlock);
        }
        if (richText instanceof TLRPC$TL_textBold) {
            return getText(parentRichText, ((TLRPC$TL_textBold) richText).text, parentBlock);
        }
        if (richText instanceof TLRPC$TL_textUnderline) {
            return getText(parentRichText, ((TLRPC$TL_textUnderline) richText).text, parentBlock);
        }
        if (richText instanceof TLRPC$TL_textStrike) {
            return getText(parentRichText, ((TLRPC$TL_textStrike) richText).text, parentBlock);
        }
        CharSequence spannableStringBuilder;
        MetricAffectingSpan[] innerSpans;
        TextPaint textPaint;
        if (richText instanceof TLRPC$TL_textEmail) {
            spannableStringBuilder = new SpannableStringBuilder(getText(parentRichText, ((TLRPC$TL_textEmail) richText).text, parentBlock));
            innerSpans = (MetricAffectingSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), MetricAffectingSpan.class);
            textPaint = (innerSpans == null || innerSpans.length == 0) ? getTextPaint(parentRichText, richText, parentBlock) : null;
            spannableStringBuilder.setSpan(new TextPaintUrlSpan(textPaint, getUrl(richText)), 0, spannableStringBuilder.length(), 33);
            return spannableStringBuilder;
        } else if (richText instanceof TLRPC$TL_textUrl) {
            spannableStringBuilder = new SpannableStringBuilder(getText(parentRichText, ((TLRPC$TL_textUrl) richText).text, parentBlock));
            innerSpans = (MetricAffectingSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), MetricAffectingSpan.class);
            textPaint = (innerSpans == null || innerSpans.length == 0) ? getTextPaint(parentRichText, richText, parentBlock) : null;
            spannableStringBuilder.setSpan(new TextPaintUrlSpan(textPaint, getUrl(richText)), 0, spannableStringBuilder.length(), 33);
            return spannableStringBuilder;
        } else if (richText instanceof TLRPC$TL_textPlain) {
            return ((TLRPC$TL_textPlain) richText).text;
        } else {
            if (richText instanceof TLRPC$TL_textEmpty) {
                return "";
            }
            if (!(richText instanceof TLRPC$TL_textConcat)) {
                return "not supported " + richText;
            }
            spannableStringBuilder = new SpannableStringBuilder();
            int count = richText.texts.size();
            for (int a = 0; a < count; a++) {
                TLRPC$RichText innerRichText = (TLRPC$RichText) richText.texts.get(a);
                CharSequence innerText = getText(parentRichText, innerRichText, parentBlock);
                int flags = getTextFlags(innerRichText);
                int startLength = spannableStringBuilder.length();
                spannableStringBuilder.append(innerText);
                if (!(flags == 0 || (innerText instanceof SpannableStringBuilder))) {
                    if ((flags & 8) != 0) {
                        String url = getUrl(innerRichText);
                        if (url == null) {
                            url = getUrl(parentRichText);
                        }
                        spannableStringBuilder.setSpan(new TextPaintUrlSpan(getTextPaint(parentRichText, innerRichText, parentBlock), url), startLength, spannableStringBuilder.length(), 33);
                    } else {
                        spannableStringBuilder.setSpan(new TextPaintSpan(getTextPaint(parentRichText, innerRichText, parentBlock)), startLength, spannableStringBuilder.length(), 33);
                    }
                }
            }
            return spannableStringBuilder;
        }
    }

    private String getUrl(TLRPC$RichText richText) {
        if (richText instanceof TLRPC$TL_textFixed) {
            return getUrl(((TLRPC$TL_textFixed) richText).text);
        }
        if (richText instanceof TLRPC$TL_textItalic) {
            return getUrl(((TLRPC$TL_textItalic) richText).text);
        }
        if (richText instanceof TLRPC$TL_textBold) {
            return getUrl(((TLRPC$TL_textBold) richText).text);
        }
        if (richText instanceof TLRPC$TL_textUnderline) {
            return getUrl(((TLRPC$TL_textUnderline) richText).text);
        }
        if (richText instanceof TLRPC$TL_textStrike) {
            return getUrl(((TLRPC$TL_textStrike) richText).text);
        }
        if (richText instanceof TLRPC$TL_textEmail) {
            return ((TLRPC$TL_textEmail) richText).email;
        }
        if (richText instanceof TLRPC$TL_textUrl) {
            return ((TLRPC$TL_textUrl) richText).url;
        }
        return null;
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

    private TextPaint getTextPaint(TLRPC$RichText parentRichText, TLRPC$RichText richText, TLRPC$PageBlock parentBlock) {
        int additionalSize;
        int flags = getTextFlags(richText);
        HashMap<Integer, TextPaint> currentMap = null;
        int textSize = AndroidUtilities.dp(14.0f);
        int textColor = SupportMenu.CATEGORY_MASK;
        if (this.selectedFontSize == 0) {
            additionalSize = -AndroidUtilities.dp(4.0f);
        } else if (this.selectedFontSize == 1) {
            additionalSize = -AndroidUtilities.dp(2.0f);
        } else if (this.selectedFontSize == 3) {
            additionalSize = AndroidUtilities.dp(2.0f);
        } else if (this.selectedFontSize == 4) {
            additionalSize = AndroidUtilities.dp(4.0f);
        } else {
            additionalSize = 0;
        }
        if (parentBlock instanceof TLRPC$TL_pageBlockPhoto) {
            currentMap = captionTextPaints;
            textSize = AndroidUtilities.dp(14.0f);
            textColor = getGrayTextColor();
        } else if (parentBlock instanceof TLRPC$TL_pageBlockTitle) {
            currentMap = titleTextPaints;
            textSize = AndroidUtilities.dp(24.0f);
            textColor = getTextColor();
        } else if (parentBlock instanceof TLRPC$TL_pageBlockAuthorDate) {
            currentMap = authorTextPaints;
            textSize = AndroidUtilities.dp(14.0f);
            textColor = getGrayTextColor();
        } else if (parentBlock instanceof TLRPC$TL_pageBlockFooter) {
            currentMap = footerTextPaints;
            textSize = AndroidUtilities.dp(14.0f);
            textColor = getGrayTextColor();
        } else if (parentBlock instanceof TLRPC$TL_pageBlockSubtitle) {
            currentMap = subtitleTextPaints;
            textSize = AndroidUtilities.dp(21.0f);
            textColor = getTextColor();
        } else if (parentBlock instanceof TLRPC$TL_pageBlockHeader) {
            currentMap = headerTextPaints;
            textSize = AndroidUtilities.dp(21.0f);
            textColor = getTextColor();
        } else if (parentBlock instanceof TLRPC$TL_pageBlockSubheader) {
            currentMap = subheaderTextPaints;
            textSize = AndroidUtilities.dp(18.0f);
            textColor = getTextColor();
        } else if ((parentBlock instanceof TLRPC$TL_pageBlockBlockquote) || (parentBlock instanceof TLRPC$TL_pageBlockPullquote)) {
            if (parentBlock.text == parentRichText) {
                currentMap = quoteTextPaints;
                textSize = AndroidUtilities.dp(15.0f);
                textColor = getTextColor();
            } else if (parentBlock.caption == parentRichText) {
                currentMap = subquoteTextPaints;
                textSize = AndroidUtilities.dp(14.0f);
                textColor = getGrayTextColor();
            }
        } else if (parentBlock instanceof TLRPC$TL_pageBlockPreformatted) {
            currentMap = preformattedTextPaints;
            textSize = AndroidUtilities.dp(14.0f);
            textColor = getTextColor();
        } else if (parentBlock instanceof TLRPC$TL_pageBlockParagraph) {
            if (parentBlock.caption == parentRichText) {
                currentMap = embedPostCaptionTextPaints;
                textSize = AndroidUtilities.dp(14.0f);
                textColor = getGrayTextColor();
            } else {
                currentMap = paragraphTextPaints;
                textSize = AndroidUtilities.dp(16.0f);
                textColor = getTextColor();
            }
        } else if (parentBlock instanceof TLRPC$TL_pageBlockList) {
            currentMap = listTextPaints;
            textSize = AndroidUtilities.dp(15.0f);
            textColor = getTextColor();
        } else if (parentBlock instanceof TLRPC$TL_pageBlockEmbed) {
            currentMap = embedTextPaints;
            textSize = AndroidUtilities.dp(14.0f);
            textColor = getGrayTextColor();
        } else if (parentBlock instanceof TLRPC$TL_pageBlockSlideshow) {
            currentMap = slideshowTextPaints;
            textSize = AndroidUtilities.dp(14.0f);
            textColor = getGrayTextColor();
        } else if (parentBlock instanceof TLRPC$TL_pageBlockEmbedPost) {
            if (richText != null) {
                currentMap = embedPostTextPaints;
                textSize = AndroidUtilities.dp(14.0f);
                textColor = getTextColor();
            }
        } else if ((parentBlock instanceof TLRPC$TL_pageBlockVideo) || (parentBlock instanceof TLRPC$TL_pageBlockAudio)) {
            currentMap = videoTextPaints;
            textSize = AndroidUtilities.dp(14.0f);
            textColor = getTextColor();
        }
        if (currentMap == null) {
            if (errorTextPaint == null) {
                errorTextPaint = new TextPaint(1);
                errorTextPaint.setColor(SupportMenu.CATEGORY_MASK);
            }
            errorTextPaint.setTextSize((float) AndroidUtilities.dp(14.0f));
            return errorTextPaint;
        }
        TextPaint paint = (TextPaint) currentMap.get(Integer.valueOf(flags));
        if (paint == null) {
            paint = new TextPaint(1);
            if ((flags & 4) != 0) {
                paint.setTypeface(AndroidUtilities.getTypeface("fonts/rmono.ttf"));
            } else if (this.selectedFont == 1 || (parentBlock instanceof TLRPC$TL_pageBlockTitle) || (parentBlock instanceof TLRPC$TL_pageBlockHeader) || (parentBlock instanceof TLRPC$TL_pageBlockSubtitle) || (parentBlock instanceof TLRPC$TL_pageBlockSubheader)) {
                if ((flags & 1) != 0 && (flags & 2) != 0) {
                    paint.setTypeface(Typeface.create(C0907C.SERIF_NAME, 3));
                } else if ((flags & 1) != 0) {
                    paint.setTypeface(Typeface.create(C0907C.SERIF_NAME, 1));
                } else if ((flags & 2) != 0) {
                    paint.setTypeface(Typeface.create(C0907C.SERIF_NAME, 2));
                } else {
                    paint.setTypeface(Typeface.create(C0907C.SERIF_NAME, 0));
                }
            } else if ((flags & 1) != 0 && (flags & 2) != 0) {
                paint.setTypeface(AndroidUtilities.getTypeface("fonts/rmediumitalic.ttf"));
            } else if ((flags & 1) != 0) {
                paint.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            } else if ((flags & 2) != 0) {
                paint.setTypeface(AndroidUtilities.getTypeface("fonts/ritalic.ttf"));
            }
            if ((flags & 32) != 0) {
                paint.setFlags(paint.getFlags() | 16);
            }
            if ((flags & 16) != 0) {
                paint.setFlags(paint.getFlags() | 8);
            }
            if ((flags & 8) != 0) {
                paint.setFlags(paint.getFlags() | 8);
                textColor = getTextColor();
            }
            paint.setColor(textColor);
            currentMap.put(Integer.valueOf(flags), paint);
        }
        paint.setTextSize((float) (textSize + additionalSize));
        return paint;
    }

    private StaticLayout createLayoutForText(CharSequence plainText, TLRPC$RichText richText, int width, TLRPC$PageBlock parentBlock) {
        if (plainText == null && (richText == null || (richText instanceof TLRPC$TL_textEmpty))) {
            return null;
        }
        CharSequence text;
        int color = getSelectedColor();
        if (quoteLinePaint == null) {
            quoteLinePaint = new Paint();
            quoteLinePaint.setColor(getTextColor());
            preformattedBackgroundPaint = new Paint();
            if (color == 0) {
                preformattedBackgroundPaint.setColor(-657156);
            } else if (color == 1) {
                preformattedBackgroundPaint.setColor(-1712440);
            } else if (color == 2) {
                preformattedBackgroundPaint.setColor(-14277082);
            }
            urlPaint = new Paint();
            if (color == 0) {
                urlPaint.setColor(-1315861);
            } else if (color == 1) {
                urlPaint.setColor(-1712440);
            } else if (color == 2) {
                urlPaint.setColor(-14277082);
            }
        }
        if (plainText != null) {
            text = plainText;
        } else {
            text = getText(richText, richText, parentBlock);
        }
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        TextPaint paint;
        if ((parentBlock instanceof TLRPC$TL_pageBlockEmbedPost) && richText == null) {
            if (parentBlock.author == plainText) {
                if (embedPostAuthorPaint == null) {
                    embedPostAuthorPaint = new TextPaint(1);
                    embedPostAuthorPaint.setColor(getTextColor());
                }
                embedPostAuthorPaint.setTextSize((float) AndroidUtilities.dp(15.0f));
                paint = embedPostAuthorPaint;
            } else {
                if (embedPostDatePaint == null) {
                    embedPostDatePaint = new TextPaint(1);
                    if (color == 0) {
                        embedPostDatePaint.setColor(-7366752);
                    } else if (color == 1) {
                        embedPostDatePaint.setColor(-11711675);
                    } else if (color == 2) {
                        embedPostDatePaint.setColor(-10066330);
                    }
                }
                embedPostDatePaint.setTextSize((float) AndroidUtilities.dp(14.0f));
                paint = embedPostDatePaint;
            }
        } else if (parentBlock instanceof TLRPC$TL_pageBlockChannel) {
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
            paint = channelNamePaint;
        } else if (!(parentBlock instanceof TLRPC$TL_pageBlockList) || plainText == null) {
            paint = getTextPaint(richText, richText, parentBlock);
        } else {
            int additionalSize;
            if (listTextPointerPaint == null) {
                listTextPointerPaint = new TextPaint(1);
                listTextPointerPaint.setColor(getTextColor());
            }
            if (this.selectedFontSize == 0) {
                additionalSize = -AndroidUtilities.dp(4.0f);
            } else if (this.selectedFontSize == 1) {
                additionalSize = -AndroidUtilities.dp(2.0f);
            } else if (this.selectedFontSize == 3) {
                additionalSize = AndroidUtilities.dp(2.0f);
            } else if (this.selectedFontSize == 4) {
                additionalSize = AndroidUtilities.dp(4.0f);
            } else {
                additionalSize = 0;
            }
            listTextPointerPaint.setTextSize((float) (AndroidUtilities.dp(15.0f) + additionalSize));
            paint = listTextPointerPaint;
        }
        if ((parentBlock instanceof TLRPC$TL_pageBlockPullquote) || !(richText == null || parentBlock == null || (parentBlock instanceof TLRPC$TL_pageBlockBlockquote) || richText != parentBlock.caption)) {
            return new StaticLayout(text, paint, width, Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        }
        return new StaticLayout(text, paint, width, Alignment.ALIGN_NORMAL, 1.0f, (float) AndroidUtilities.dp(4.0f), false);
    }

    private void drawLayoutLink(Canvas canvas, StaticLayout layout) {
        if (canvas != null && this.pressedLinkOwnerLayout == layout) {
            if (this.pressedLink != null) {
                canvas.drawPath(this.urlPath, urlPaint);
            } else if (this.drawBlockSelection) {
                float width;
                float x;
                if (layout.getLineCount() == 1) {
                    width = layout.getLineWidth(0);
                    x = layout.getLineLeft(0);
                } else {
                    width = (float) layout.getWidth();
                    x = 0.0f;
                }
                Canvas canvas2 = canvas;
                canvas2.drawRect(((float) (-AndroidUtilities.dp(2.0f))) + x, 0.0f, ((float) AndroidUtilities.dp(2.0f)) + (x + width), (float) layout.getHeight(), urlPaint);
            }
        }
    }

    private boolean checkLayoutForLinks(MotionEvent event, View parentView, StaticLayout layout, int layoutX, int layoutY) {
        if (parentView == null || layout == null) {
            return false;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        boolean removeLink = false;
        if (event.getAction() == 0) {
            if (x >= layoutX && x <= layout.getWidth() + layoutX && y >= layoutY && y <= layout.getHeight() + layoutY) {
                this.pressedLinkOwnerLayout = layout;
                this.pressedLinkOwnerView = parentView;
                this.pressedLayoutY = layoutY;
                if (layout.getText() instanceof Spannable) {
                    int checkX = x - layoutX;
                    try {
                        int line = layout.getLineForVertical(y - layoutY);
                        int off = layout.getOffsetForHorizontal(line, (float) checkX);
                        float left = layout.getLineLeft(line);
                        if (left <= ((float) checkX) && layout.getLineWidth(line) + left >= ((float) checkX)) {
                            Spannable buffer = (Spannable) layout.getText();
                            TextPaintUrlSpan[] link = (TextPaintUrlSpan[]) buffer.getSpans(off, off, TextPaintUrlSpan.class);
                            if (link != null && link.length > 0) {
                                this.pressedLink = link[0];
                                int pressedStart = buffer.getSpanStart(this.pressedLink);
                                int pressedEnd = buffer.getSpanEnd(this.pressedLink);
                                for (int a = 1; a < link.length; a++) {
                                    TextPaintUrlSpan span = link[a];
                                    int start = buffer.getSpanStart(span);
                                    int end = buffer.getSpanEnd(span);
                                    if (pressedStart > start || end > pressedEnd) {
                                        this.pressedLink = span;
                                        pressedStart = start;
                                        pressedEnd = end;
                                    }
                                }
                                try {
                                    this.urlPath.setCurrentLayout(layout, pressedStart, 0.0f);
                                    layout.getSelectionPath(pressedStart, pressedEnd, this.urlPath);
                                    parentView.invalidate();
                                } catch (Exception e) {
                                    FileLog.e(e);
                                }
                            }
                        }
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                }
            }
        } else if (event.getAction() == 1) {
            if (this.pressedLink != null) {
                removeLink = true;
                String url = this.pressedLink.getUrl();
                if (url != null) {
                    String anchor;
                    boolean isAnchor = false;
                    int index = url.lastIndexOf(35);
                    if (index != -1) {
                        anchor = url.substring(index + 1);
                        if (url.toLowerCase().contains(this.currentPage.url.toLowerCase())) {
                            Integer row = (Integer) this.anchors.get(anchor);
                            if (row != null) {
                                this.layoutManager.scrollToPositionWithOffset(row.intValue(), 0);
                                isAnchor = true;
                            }
                        }
                    } else {
                        anchor = null;
                    }
                    if (!isAnchor && this.openUrlReqId == 0) {
                        showProgressView(true);
                        TLObject req = new TLRPC$TL_messages_getWebPage();
                        req.url = this.pressedLink.getUrl();
                        req.hash = 0;
                        final TLObject tLObject = req;
                        this.openUrlReqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                            public void run(final TLObject response, TLRPC$TL_error error) {
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        if (ArticleViewer.this.openUrlReqId != 0) {
                                            ArticleViewer.this.openUrlReqId = 0;
                                            ArticleViewer.this.showProgressView(false);
                                            if (!ArticleViewer.this.isVisible) {
                                                return;
                                            }
                                            if ((response instanceof TLRPC$TL_webPage) && (((TLRPC$TL_webPage) response).cached_page instanceof TLRPC$TL_pageFull)) {
                                                ArticleViewer.this.addPageToStack((TLRPC$TL_webPage) response, anchor);
                                            } else {
                                                Browser.openUrl(ArticleViewer.this.parentActivity, tLObject.url);
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        } else if (event.getAction() == 3) {
            removeLink = true;
        }
        if (removeLink) {
            this.pressedLink = null;
            this.pressedLinkOwnerLayout = null;
            this.pressedLinkOwnerView = null;
            parentView.invalidate();
        }
        if (event.getAction() == 0) {
            startCheckLongPress();
        }
        if (!(event.getAction() == 0 || event.getAction() == 2)) {
            cancelCheckLongPress();
        }
        if (this.pressedLinkOwnerLayout != null) {
            return true;
        }
        return false;
    }

    private TLRPC$Photo getPhotoWithId(long id) {
        if (this.currentPage == null || this.currentPage.cached_page == null) {
            return null;
        }
        if (this.currentPage.photo != null && this.currentPage.photo.id == id) {
            return this.currentPage.photo;
        }
        for (int a = 0; a < this.currentPage.cached_page.photos.size(); a++) {
            TLRPC$Photo photo = (TLRPC$Photo) this.currentPage.cached_page.photos.get(a);
            if (photo.id == id) {
                return photo;
            }
        }
        return null;
    }

    private TLRPC$Document getDocumentWithId(long id) {
        if (this.currentPage == null || this.currentPage.cached_page == null) {
            return null;
        }
        if (this.currentPage.document != null && this.currentPage.document.id == id) {
            return this.currentPage.document;
        }
        for (int a = 0; a < this.currentPage.cached_page.documents.size(); a++) {
            TLRPC$Document document = (TLRPC$Document) this.currentPage.cached_page.documents.get(a);
            if (document.id == id) {
                return document;
            }
        }
        return null;
    }

    public void didReceivedNotification(int id, Object... args) {
        String location;
        int a;
        if (id == NotificationCenter.FileDidFailedLoad) {
            location = args[0];
            a = 0;
            while (a < 3) {
                if (this.currentFileNames[a] == null || !this.currentFileNames[a].equals(location)) {
                    a++;
                } else {
                    this.radialProgressViews[a].setProgress(1.0f, true);
                    checkProgress(a, true);
                    return;
                }
            }
        } else if (id == NotificationCenter.FileDidLoaded) {
            location = (String) args[0];
            a = 0;
            while (a < 3) {
                if (this.currentFileNames[a] == null || !this.currentFileNames[a].equals(location)) {
                    a++;
                } else {
                    this.radialProgressViews[a].setProgress(1.0f, true);
                    checkProgress(a, true);
                    if (a == 0 && isMediaVideo(this.currentIndex)) {
                        onActionClick(false);
                        return;
                    }
                    return;
                }
            }
        } else if (id == NotificationCenter.FileLoadProgressChanged) {
            location = (String) args[0];
            a = 0;
            while (a < 3) {
                if (this.currentFileNames[a] != null && this.currentFileNames[a].equals(location)) {
                    this.radialProgressViews[a].setProgress(args[1].floatValue(), true);
                }
                a++;
            }
        } else if (id == NotificationCenter.emojiDidLoaded) {
            if (this.captionTextView != null) {
                this.captionTextView.invalidate();
            }
        } else if (id == NotificationCenter.messagePlayingDidStarted) {
            MessageObject messageObject = args[0];
            if (this.listView != null) {
                count = this.listView.getChildCount();
                for (a = 0; a < count; a++) {
                    view = this.listView.getChildAt(a);
                    if (view instanceof BlockAudioCell) {
                        ((BlockAudioCell) view).updateButtonState(false);
                    }
                }
            }
        } else if (id == NotificationCenter.messagePlayingDidReset || id == NotificationCenter.messagePlayingPlayStateChanged) {
            if (this.listView != null) {
                count = this.listView.getChildCount();
                for (a = 0; a < count; a++) {
                    view = this.listView.getChildAt(a);
                    if (view instanceof BlockAudioCell) {
                        cell = (BlockAudioCell) view;
                        if (cell.getMessageObject() != null) {
                            cell.updateButtonState(false);
                        }
                    }
                }
            }
        } else if (id == NotificationCenter.messagePlayingProgressDidChanged) {
            Integer mid = args[0];
            if (this.listView != null) {
                count = this.listView.getChildCount();
                for (a = 0; a < count; a++) {
                    view = this.listView.getChildAt(a);
                    if (view instanceof BlockAudioCell) {
                        cell = (BlockAudioCell) view;
                        MessageObject playing = cell.getMessageObject();
                        if (playing != null && playing.getId() == mid.intValue()) {
                            MessageObject player = MediaController.getInstance().getPlayingMessageObject();
                            if (player != null) {
                                playing.audioProgress = player.audioProgress;
                                playing.audioProgressSec = player.audioProgressSec;
                                cell.updatePlayingMessageProgress();
                                return;
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    private void updatePaintSize() {
        ApplicationLoader.applicationContext.getSharedPreferences("articles", 0).edit().putInt("font_size", this.selectedFontSize).commit();
        this.adapter.notifyDataSetChanged();
    }

    private void updatePaintFonts() {
        ApplicationLoader.applicationContext.getSharedPreferences("articles", 0).edit().putInt("font_type", this.selectedFont).commit();
        Typeface typefaceNormal = this.selectedFont == 0 ? Typeface.DEFAULT : Typeface.SERIF;
        Typeface typefaceItalic = this.selectedFont == 0 ? AndroidUtilities.getTypeface("fonts/ritalic.ttf") : Typeface.create(C0907C.SERIF_NAME, 2);
        Typeface typefaceBold = this.selectedFont == 0 ? AndroidUtilities.getTypeface("fonts/rmedium.ttf") : Typeface.create(C0907C.SERIF_NAME, 1);
        Typeface typefaceBoldItalic = this.selectedFont == 0 ? AndroidUtilities.getTypeface("fonts/rmediumitalic.ttf") : Typeface.create(C0907C.SERIF_NAME, 3);
        for (Entry<Integer, TextPaint> entry : quoteTextPaints.entrySet()) {
            updateFontEntry(entry, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry2 : preformattedTextPaints.entrySet()) {
            updateFontEntry(entry2, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry22 : paragraphTextPaints.entrySet()) {
            updateFontEntry(entry22, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry222 : listTextPaints.entrySet()) {
            updateFontEntry(entry222, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry2222 : embedPostTextPaints.entrySet()) {
            updateFontEntry(entry2222, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry22222 : videoTextPaints.entrySet()) {
            updateFontEntry(entry22222, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry222222 : captionTextPaints.entrySet()) {
            updateFontEntry(entry222222, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry2222222 : authorTextPaints.entrySet()) {
            updateFontEntry(entry2222222, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry22222222 : footerTextPaints.entrySet()) {
            updateFontEntry(entry22222222, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry222222222 : subquoteTextPaints.entrySet()) {
            updateFontEntry(entry222222222, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry2222222222 : embedPostCaptionTextPaints.entrySet()) {
            updateFontEntry(entry2222222222, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry22222222222 : embedTextPaints.entrySet()) {
            updateFontEntry(entry22222222222, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
        for (Entry<Integer, TextPaint> entry222222222222 : slideshowTextPaints.entrySet()) {
            updateFontEntry(entry222222222222, typefaceNormal, typefaceBoldItalic, typefaceBold, typefaceItalic);
        }
    }

    private void updateFontEntry(Entry<Integer, TextPaint> entry, Typeface typefaceNormal, Typeface typefaceBoldItalic, Typeface typefaceBold, Typeface typefaceItalic) {
        Integer flags = (Integer) entry.getKey();
        TextPaint paint = (TextPaint) entry.getValue();
        if ((flags.intValue() & 1) != 0 && (flags.intValue() & 2) != 0) {
            paint.setTypeface(typefaceBoldItalic);
        } else if ((flags.intValue() & 1) != 0) {
            paint.setTypeface(typefaceBold);
        } else if ((flags.intValue() & 2) != 0) {
            paint.setTypeface(typefaceItalic);
        } else {
            paint.setTypeface(typefaceNormal);
        }
    }

    private int getSelectedColor() {
        int currentColor = this.selectedColor;
        if (!this.nightModeEnabled || currentColor == 2) {
            return currentColor;
        }
        int hour = Calendar.getInstance().get(11);
        if ((hour < 22 || hour > 24) && (hour < 0 || hour > 6)) {
            return currentColor;
        }
        return 2;
    }

    private void updatePaintColors() {
        ApplicationLoader.applicationContext.getSharedPreferences("articles", 0).edit().putInt("font_color", this.selectedColor).commit();
        int currentColor = getSelectedColor();
        if (currentColor == 0) {
            this.backgroundPaint.setColor(-1);
            this.listView.setGlowColor(-657673);
        } else if (currentColor == 1) {
            this.backgroundPaint.setColor(-659492);
            this.listView.setGlowColor(-659492);
        } else if (currentColor == 2) {
            this.backgroundPaint.setColor(-15461356);
            this.listView.setGlowColor(-15461356);
        }
        for (int a = 0; a < Theme.chat_ivStatesDrawable.length; a++) {
            Theme.setCombinedDrawableColor(Theme.chat_ivStatesDrawable[a][0], getTextColor(), false);
            Theme.setCombinedDrawableColor(Theme.chat_ivStatesDrawable[a][0], getTextColor(), true);
            Theme.setCombinedDrawableColor(Theme.chat_ivStatesDrawable[a][1], getTextColor(), false);
            Theme.setCombinedDrawableColor(Theme.chat_ivStatesDrawable[a][1], getTextColor(), true);
        }
        if (quoteLinePaint != null) {
            quoteLinePaint.setColor(getTextColor());
        }
        if (listTextPointerPaint != null) {
            listTextPointerPaint.setColor(getTextColor());
        }
        if (preformattedBackgroundPaint != null) {
            if (currentColor == 0) {
                preformattedBackgroundPaint.setColor(-657156);
            } else if (currentColor == 1) {
                preformattedBackgroundPaint.setColor(-1712440);
            } else if (currentColor == 2) {
                preformattedBackgroundPaint.setColor(-14277082);
            }
        }
        if (urlPaint != null) {
            if (currentColor == 0) {
                urlPaint.setColor(-1315861);
            } else if (currentColor == 1) {
                urlPaint.setColor(-1712440);
            } else if (currentColor == 2) {
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
            if (currentColor == 0) {
                embedPostDatePaint.setColor(-7366752);
            } else if (currentColor == 1) {
                embedPostDatePaint.setColor(-11711675);
            } else if (currentColor == 2) {
                embedPostDatePaint.setColor(-10066330);
            }
        }
        if (dividerPaint != null) {
            if (currentColor == 0) {
                dividerPaint.setColor(-3288619);
            } else if (currentColor == 1) {
                dividerPaint.setColor(-4080987);
            } else if (currentColor == 2) {
                dividerPaint.setColor(-12303292);
            }
        }
        for (Entry<Integer, TextPaint> entry : titleTextPaints.entrySet()) {
            ((TextPaint) entry.getValue()).setColor(getTextColor());
        }
        for (Entry<Integer, TextPaint> entry2 : subtitleTextPaints.entrySet()) {
            ((TextPaint) entry2.getValue()).setColor(getTextColor());
        }
        for (Entry<Integer, TextPaint> entry22 : headerTextPaints.entrySet()) {
            ((TextPaint) entry22.getValue()).setColor(getTextColor());
        }
        for (Entry<Integer, TextPaint> entry222 : subheaderTextPaints.entrySet()) {
            ((TextPaint) entry222.getValue()).setColor(getTextColor());
        }
        for (Entry<Integer, TextPaint> entry2222 : quoteTextPaints.entrySet()) {
            ((TextPaint) entry2222.getValue()).setColor(getTextColor());
        }
        for (Entry<Integer, TextPaint> entry22222 : preformattedTextPaints.entrySet()) {
            ((TextPaint) entry22222.getValue()).setColor(getTextColor());
        }
        for (Entry<Integer, TextPaint> entry222222 : paragraphTextPaints.entrySet()) {
            ((TextPaint) entry222222.getValue()).setColor(getTextColor());
        }
        for (Entry<Integer, TextPaint> entry2222222 : listTextPaints.entrySet()) {
            ((TextPaint) entry2222222.getValue()).setColor(getTextColor());
        }
        for (Entry<Integer, TextPaint> entry22222222 : embedPostTextPaints.entrySet()) {
            ((TextPaint) entry22222222.getValue()).setColor(getTextColor());
        }
        for (Entry<Integer, TextPaint> entry222222222 : videoTextPaints.entrySet()) {
            ((TextPaint) entry222222222.getValue()).setColor(getTextColor());
        }
        for (Entry<Integer, TextPaint> entry2222222222 : captionTextPaints.entrySet()) {
            ((TextPaint) entry2222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry<Integer, TextPaint> entry22222222222 : authorTextPaints.entrySet()) {
            ((TextPaint) entry22222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry<Integer, TextPaint> entry222222222222 : footerTextPaints.entrySet()) {
            ((TextPaint) entry222222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry<Integer, TextPaint> entry2222222222222 : subquoteTextPaints.entrySet()) {
            ((TextPaint) entry2222222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry<Integer, TextPaint> entry22222222222222 : embedPostCaptionTextPaints.entrySet()) {
            ((TextPaint) entry22222222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry<Integer, TextPaint> entry222222222222222 : embedTextPaints.entrySet()) {
            ((TextPaint) entry222222222222222.getValue()).setColor(getGrayTextColor());
        }
        for (Entry<Integer, TextPaint> entry2222222222222222 : slideshowTextPaints.entrySet()) {
            ((TextPaint) entry2222222222222222.getValue()).setColor(getGrayTextColor());
        }
    }

    public void setParentActivity(Activity activity, BaseFragment fragment) {
        this.parentFragment = fragment;
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
            this.containerView.setOnApplyWindowInsetsListener(new C20846());
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
            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                super.onLayout(changed, left, top, right, bottom);
                int y = (bottom - top) - ArticleViewer.this.captionTextView.getMeasuredHeight();
                if (ArticleViewer.this.bottomLayout.getVisibility() == 0) {
                    y -= ArticleViewer.this.bottomLayout.getMeasuredHeight();
                }
                ArticleViewer.this.captionTextView.layout(0, y, ArticleViewer.this.captionTextView.getMeasuredWidth(), ArticleViewer.this.captionTextView.getMeasuredHeight() + y);
            }
        };
        this.photoContainerView.setVisibility(4);
        this.photoContainerView.setWillNotDraw(false);
        this.windowView.addView(this.photoContainerView, LayoutHelper.createFrame(-1, -1, 51));
        this.fullscreenVideoContainer = new FrameLayout(activity);
        this.fullscreenVideoContainer.setBackgroundColor(-16777216);
        this.fullscreenVideoContainer.setVisibility(4);
        this.windowView.addView(this.fullscreenVideoContainer, LayoutHelper.createFrame(-1, -1.0f));
        this.fullscreenAspectRatioView = new AspectRatioFrameLayout(activity);
        this.fullscreenAspectRatioView.setVisibility(8);
        this.fullscreenVideoContainer.addView(this.fullscreenAspectRatioView, LayoutHelper.createFrame(-1, -1, 17));
        this.fullscreenTextureView = new TextureView(activity);
        if (VERSION.SDK_INT >= 21) {
            this.barBackground = new View(activity);
            this.barBackground.setBackgroundColor(-16777216);
            this.windowView.addView(this.barBackground);
        }
        this.listView = new RecyclerListView(activity) {
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                super.onLayout(changed, l, t, r, b);
                int count = getChildCount();
                for (int a = 0; a < count; a++) {
                    View child = getChildAt(a);
                    if ((child.getTag() instanceof Integer) && ((Integer) child.getTag()).intValue() == 90 && child.getBottom() < getMeasuredHeight()) {
                        int height = getMeasuredHeight();
                        child.layout(0, height - child.getMeasuredHeight(), child.getMeasuredWidth(), height);
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
        this.listView.setOnItemLongClickListener(new C20879());
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                if (position != ArticleViewer.this.blocks.size() || ArticleViewer.this.currentPage == null) {
                    if (position >= 0 && position < ArticleViewer.this.blocks.size()) {
                        TLRPC$PageBlock pageBlock = (TLRPC$PageBlock) ArticleViewer.this.blocks.get(position);
                        if (pageBlock instanceof TLRPC$TL_pageBlockChannel) {
                            MessagesController.openByUserName(pageBlock.channel.username, ArticleViewer.this.parentFragment, 2);
                            ArticleViewer.this.close(false, true);
                        }
                    }
                } else if (ArticleViewer.this.previewsReqId == 0) {
                    TLObject object = MessagesController.getInstance().getUserOrChat("previews");
                    if (object instanceof TLRPC$TL_user) {
                        ArticleViewer.this.openPreviewsChat((User) object, ArticleViewer.this.currentPage.id);
                        return;
                    }
                    final long pageId = ArticleViewer.this.currentPage.id;
                    ArticleViewer.this.showProgressView(true);
                    TLRPC$TL_contacts_resolveUsername req = new TLRPC$TL_contacts_resolveUsername();
                    req.username = "previews";
                    ArticleViewer.this.previewsReqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                        public void run(final TLObject response, TLRPC$TL_error error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (ArticleViewer.this.previewsReqId != 0) {
                                        ArticleViewer.this.previewsReqId = 0;
                                        ArticleViewer.this.showProgressView(false);
                                        if (response != null) {
                                            TLRPC$TL_contacts_resolvedPeer res = response;
                                            MessagesController.getInstance().putUsers(res.users, false);
                                            MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, false, true);
                                            if (!res.users.isEmpty()) {
                                                ArticleViewer.this.openPreviewsChat((User) res.users.get(0), pageId);
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
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (ArticleViewer.this.listView.getChildCount() != 0) {
                    ArticleViewer.this.headerView.invalidate();
                    ArticleViewer.this.checkScroll(dy);
                }
            }
        });
        this.headerPaint.setColor(-16777216);
        this.headerProgressPaint.setColor(-14408666);
        this.headerView = new FrameLayout(activity) {
            protected void onDraw(Canvas canvas) {
                int width = getMeasuredWidth();
                int height = getMeasuredHeight();
                canvas.drawRect(0.0f, 0.0f, (float) width, (float) height, ArticleViewer.this.headerPaint);
                if (ArticleViewer.this.layoutManager != null) {
                    View view;
                    int first = ArticleViewer.this.layoutManager.findFirstVisibleItemPosition();
                    int last = ArticleViewer.this.layoutManager.findLastVisibleItemPosition();
                    int count = ArticleViewer.this.layoutManager.getItemCount();
                    if (last >= count - 2) {
                        view = ArticleViewer.this.layoutManager.findViewByPosition(count - 2);
                    } else {
                        view = ArticleViewer.this.layoutManager.findViewByPosition(first);
                    }
                    if (view != null) {
                        float viewProgress;
                        float itemProgress = ((float) width) / ((float) (count - 1));
                        int childCount = ArticleViewer.this.layoutManager.getChildCount();
                        float viewHeight = (float) view.getMeasuredHeight();
                        if (last >= count - 2) {
                            viewProgress = ((((float) ((count - 2) - first)) * itemProgress) * ((float) (ArticleViewer.this.listView.getMeasuredHeight() - view.getTop()))) / viewHeight;
                        } else {
                            viewProgress = itemProgress * (1.0f - ((((float) Math.min(0, view.getTop() - ArticleViewer.this.listView.getPaddingTop())) + viewHeight) / viewHeight));
                        }
                        canvas.drawRect(0.0f, 0.0f, (((float) first) * itemProgress) + viewProgress, (float) height, ArticleViewer.this.headerProgressPaint);
                    }
                }
            }
        };
        this.headerView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
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
            public void onClick(View v) {
                ArticleViewer.this.close(true, true);
            }
        });
        LinearLayout settingsContainer = new LinearLayout(this.parentActivity);
        settingsContainer.setPadding(0, AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f));
        settingsContainer.setOrientation(1);
        int a = 0;
        while (a < 3) {
            this.colorCells[a] = new ColorCell(this, this.parentActivity);
            switch (a) {
                case 0:
                    this.nightModeImageView = new ImageView(this.parentActivity);
                    this.nightModeImageView.setScaleType(ScaleType.CENTER);
                    this.nightModeImageView.setImageResource(R.drawable.moon);
                    ImageView imageView = this.nightModeImageView;
                    int i = (!this.nightModeEnabled || this.selectedColor == 2) ? -3355444 : -15428119;
                    imageView.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                    this.nightModeImageView.setBackgroundDrawable(Theme.createSelectorDrawable(251658240));
                    this.colorCells[a].addView(this.nightModeImageView, LayoutHelper.createFrame(48, 48, 53));
                    this.nightModeImageView.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
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
                    this.colorCells[a].setTextAndColor(LocaleController.getString("ColorWhite", R.string.ColorWhite), -1);
                    break;
                case 1:
                    this.colorCells[a].setTextAndColor(LocaleController.getString("ColorSepia", R.string.ColorSepia), -1382967);
                    break;
                case 2:
                    this.colorCells[a].setTextAndColor(LocaleController.getString("ColorDark", R.string.ColorDark), -14474461);
                    break;
            }
            this.colorCells[a].select(a == this.selectedColor);
            this.colorCells[a].setTag(Integer.valueOf(a));
            this.colorCells[a].setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    int num = ((Integer) v.getTag()).intValue();
                    ArticleViewer.this.selectedColor = num;
                    int a = 0;
                    while (a < 3) {
                        ArticleViewer.this.colorCells[a].select(a == num);
                        a++;
                    }
                    ArticleViewer.this.updateNightModeButton();
                    ArticleViewer.this.updatePaintColors();
                    ArticleViewer.this.adapter.notifyDataSetChanged();
                }
            });
            settingsContainer.addView(this.colorCells[a], LayoutHelper.createLinear(-1, 48));
            a++;
        }
        updateNightModeButton();
        View divider = new View(this.parentActivity);
        divider.setBackgroundColor(-2039584);
        settingsContainer.addView(divider, LayoutHelper.createLinear(-1, 1, 15.0f, 4.0f, 15.0f, 4.0f));
        divider.getLayoutParams().height = 1;
        a = 0;
        while (a < 2) {
            this.fontCells[a] = new FontCell(this, this.parentActivity);
            switch (a) {
                case 0:
                    this.fontCells[a].setTextAndTypeface("Roboto", Typeface.DEFAULT);
                    break;
                case 1:
                    this.fontCells[a].setTextAndTypeface("Serif", Typeface.SERIF);
                    break;
            }
            this.fontCells[a].select(a == this.selectedFont);
            this.fontCells[a].setTag(Integer.valueOf(a));
            this.fontCells[a].setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    int num = ((Integer) v.getTag()).intValue();
                    ArticleViewer.this.selectedFont = num;
                    int a = 0;
                    while (a < 2) {
                        ArticleViewer.this.fontCells[a].select(a == num);
                        a++;
                    }
                    ArticleViewer.this.updatePaintFonts();
                    ArticleViewer.this.adapter.notifyDataSetChanged();
                }
            });
            settingsContainer.addView(this.fontCells[a], LayoutHelper.createLinear(-1, 48));
            a++;
        }
        divider = new View(this.parentActivity);
        divider.setBackgroundColor(-2039584);
        settingsContainer.addView(divider, LayoutHelper.createLinear(-1, 1, 15.0f, 4.0f, 15.0f, 4.0f));
        divider.getLayoutParams().height = 1;
        TextView textView = new TextView(this.parentActivity);
        textView.setTextColor(-14606047);
        textView.setTextSize(1, 16.0f);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine(true);
        textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        textView.setText(LocaleController.getString("FontSize", R.string.FontSize));
        settingsContainer.addView(textView, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 17, 12, 17, 0));
        settingsContainer.addView(new SizeChooseView(this.parentActivity), LayoutHelper.createLinear(-1, 38, 0.0f, 0.0f, 0.0f, 1.0f));
        this.settingsButton = new ActionBarMenuItem(this.parentActivity, null, Theme.ACTION_BAR_WHITE_SELECTOR_COLOR, -1);
        this.settingsButton.setPopupAnimationEnabled(false);
        this.settingsButton.setLayoutInScreen(true);
        textView = new TextView(this.parentActivity);
        textView.setTextSize(1, 18.0f);
        textView.setText("Aa");
        textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView.setTextColor(-5000269);
        textView.setGravity(17);
        this.settingsButton.addView(textView, LayoutHelper.createFrame(-1, -1.0f));
        this.settingsButton.addSubItem(settingsContainer, AndroidUtilities.dp(220.0f), -2);
        this.settingsButton.redrawPopup(-1);
        this.headerView.addView(this.settingsButton, LayoutHelper.createFrame(48, 56.0f, 53, 0.0f, 0.0f, 56.0f, 0.0f));
        this.shareContainer = new FrameLayout(activity);
        this.headerView.addView(this.shareContainer, LayoutHelper.createFrame(48, 56, 53));
        this.shareContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
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
        this.blackPaint.setColor(-16777216);
        this.actionBar = new ActionBar(activity);
        this.actionBar.setBackgroundColor(2130706432);
        this.actionBar.setOccupyStatusBar(false);
        this.actionBar.setTitleColor(-1);
        this.actionBar.setItemsBackgroundColor(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR, false);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(1), Integer.valueOf(1)}));
        this.photoContainerView.addView(this.actionBar, LayoutHelper.createFrame(-1, -2.0f));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBarMenuOnItemClick() {
            public void onItemClick(int id) {
                int i = 1;
                if (id == -1) {
                    ArticleViewer.this.closePhoto(true);
                } else if (id == 1) {
                    if (VERSION.SDK_INT < 23 || ArticleViewer.this.parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        File f = ArticleViewer.this.getMediaFile(ArticleViewer.this.currentIndex);
                        if (f == null || !f.exists()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ArticleViewer.this.parentActivity);
                            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                            builder.setMessage(LocaleController.getString("PleaseDownload", R.string.PleaseDownload));
                            ArticleViewer.this.showDialog(builder.create());
                            return;
                        }
                        String file = f.toString();
                        Context access$1900 = ArticleViewer.this.parentActivity;
                        if (!ArticleViewer.this.isMediaVideo(ArticleViewer.this.currentIndex)) {
                            i = 0;
                        }
                        MediaController.saveFile(file, access$1900, i, null, null);
                        return;
                    }
                    ArticleViewer.this.parentActivity.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 4);
                } else if (id == 2) {
                    ArticleViewer.this.onSharePressed();
                } else if (id == 3) {
                    try {
                        AndroidUtilities.openForView(ArticleViewer.this.getMedia(ArticleViewer.this.currentIndex), ArticleViewer.this.parentActivity);
                        ArticleViewer.this.closePhoto(false);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            }

            public boolean canOpenMenu() {
                File f = ArticleViewer.this.getMediaFile(ArticleViewer.this.currentIndex);
                return f != null && f.exists();
            }
        });
        ActionBarMenu menu = this.actionBar.createMenu();
        menu.addItem(2, (int) R.drawable.share);
        this.menuItem = menu.addItem(0, (int) R.drawable.ic_ab_other);
        this.menuItem.setLayoutInScreen(true);
        this.menuItem.addSubItem(3, LocaleController.getString("OpenInExternalApp", R.string.OpenInExternalApp));
        this.menuItem.addSubItem(1, LocaleController.getString("SaveToGallery", R.string.SaveToGallery));
        this.bottomLayout = new FrameLayout(this.parentActivity);
        this.bottomLayout.setBackgroundColor(2130706432);
        this.photoContainerView.addView(this.bottomLayout, LayoutHelper.createFrame(-1, 48, 83));
        this.captionTextViewOld = new TextView(activity);
        this.captionTextViewOld.setMaxLines(10);
        this.captionTextViewOld.setBackgroundColor(2130706432);
        this.captionTextViewOld.setPadding(AndroidUtilities.dp(20.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(20.0f), AndroidUtilities.dp(8.0f));
        this.captionTextViewOld.setLinkTextColor(-1);
        this.captionTextViewOld.setTextColor(-1);
        this.captionTextViewOld.setGravity(19);
        this.captionTextViewOld.setTextSize(1, 16.0f);
        this.captionTextViewOld.setVisibility(4);
        this.photoContainerView.addView(this.captionTextViewOld, LayoutHelper.createFrame(-1, -2, 83));
        TextView textView2 = new TextView(activity);
        this.captionTextViewNew = textView2;
        this.captionTextView = textView2;
        this.captionTextViewNew.setMaxLines(10);
        this.captionTextViewNew.setBackgroundColor(2130706432);
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
            public void onSeekBarDrag(float progress) {
                if (ArticleViewer.this.videoPlayer != null) {
                    ArticleViewer.this.videoPlayer.seekTo((long) ((int) (((float) ArticleViewer.this.videoPlayer.getDuration()) * progress)));
                }
            }
        });
        this.videoPlayerControlFrameLayout = new FrameLayout(activity) {
            public boolean onTouchEvent(MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (!ArticleViewer.this.videoPlayerSeekbar.onTouch(event.getAction(), event.getX() - ((float) AndroidUtilities.dp(48.0f)), event.getY())) {
                    return super.onTouchEvent(event);
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                invalidate();
                return true;
            }

            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                long duration;
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                if (ArticleViewer.this.videoPlayer != null) {
                    duration = ArticleViewer.this.videoPlayer.getDuration();
                    if (duration == C0907C.TIME_UNSET) {
                        duration = 0;
                    }
                } else {
                    duration = 0;
                }
                duration /= 1000;
                ArticleViewer.this.videoPlayerSeekbar.setSize((getMeasuredWidth() - AndroidUtilities.dp(64.0f)) - ((int) Math.ceil((double) ArticleViewer.this.videoPlayerTime.getPaint().measureText(String.format("%02d:%02d / %02d:%02d", new Object[]{Long.valueOf(duration / 60), Long.valueOf(duration % 60), Long.valueOf(duration / 60), Long.valueOf(duration % 60)})))), getMeasuredHeight());
            }

            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                super.onLayout(changed, left, top, right, bottom);
                float progress = 0.0f;
                if (ArticleViewer.this.videoPlayer != null) {
                    progress = ((float) ArticleViewer.this.videoPlayer.getCurrentPosition()) / ((float) ArticleViewer.this.videoPlayer.getDuration());
                }
                ArticleViewer.this.videoPlayerSeekbar.setProgress(progress);
            }

            protected void onDraw(Canvas canvas) {
                canvas.save();
                canvas.translate((float) AndroidUtilities.dp(48.0f), 0.0f);
                ArticleViewer.this.videoPlayerSeekbar.draw(canvas);
                canvas.restore();
            }
        };
        this.videoPlayerControlFrameLayout.setWillNotDraw(false);
        this.bottomLayout.addView(this.videoPlayerControlFrameLayout, LayoutHelper.createFrame(-1, -1, 51));
        this.videoPlayButton = new ImageView(activity);
        this.videoPlayButton.setScaleType(ScaleType.CENTER);
        this.videoPlayerControlFrameLayout.addView(this.videoPlayButton, LayoutHelper.createFrame(48, 48, 51));
        this.videoPlayButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
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
        this.videoPlayerControlFrameLayout.addView(this.videoPlayerTime, LayoutHelper.createFrame(-2, -1.0f, 53, 0.0f, 0.0f, 8.0f, 0.0f));
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

    private void showNightModeHint() {
        if (this.parentActivity != null && this.nightModeHintView == null && this.nightModeEnabled) {
            this.nightModeHintView = new FrameLayout(this.parentActivity);
            this.nightModeHintView.setBackgroundColor(Theme.ACTION_BAR_MEDIA_PICKER_COLOR);
            this.containerView.addView(this.nightModeHintView, LayoutHelper.createFrame(-2, -2, 83));
            ImageView nightModeImageView = new ImageView(this.parentActivity);
            nightModeImageView.setScaleType(ScaleType.CENTER);
            nightModeImageView.setImageResource(R.drawable.moon);
            this.nightModeHintView.addView(nightModeImageView, LayoutHelper.createFrame(56, 56, 19));
            TextView textView = new TextView(this.parentActivity);
            textView.setText(LocaleController.getString("InstantViewNightMode", R.string.InstantViewNightMode));
            textView.setTextColor(-1);
            textView.setTextSize(1, 15.0f);
            this.nightModeHintView.addView(textView, LayoutHelper.createFrame(-1, -1.0f, 51, 56.0f, 11.0f, 10.0f, 12.0f));
            AnimatorSet animatorSet = new AnimatorSet();
            Animator[] animatorArr = new Animator[1];
            animatorArr[0] = ObjectAnimator.ofFloat(this.nightModeHintView, "translationY", new float[]{(float) AndroidUtilities.dp(100.0f), 0.0f});
            animatorSet.playTogether(animatorArr);
            animatorSet.setInterpolator(new DecelerateInterpolator(1.5f));
            animatorSet.addListener(new AnimatorListenerAdapter() {

                /* renamed from: org.telegram.ui.ArticleViewer$23$1 */
                class C20701 implements Runnable {
                    C20701() {
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

                public void onAnimationEnd(Animator animation) {
                    AndroidUtilities.runOnUIThread(new C20701(), 3000);
                }
            });
            animatorSet.setDuration(250);
            animatorSet.start();
        }
    }

    private void updateNightModeButton() {
        this.nightModeImageView.setEnabled(this.selectedColor != 2);
        this.nightModeImageView.setAlpha(this.selectedColor == 2 ? 0.5f : 1.0f);
        ImageView imageView = this.nightModeImageView;
        int i = (!this.nightModeEnabled || this.selectedColor == 2) ? -3355444 : -15428119;
        imageView.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
    }

    private void checkScroll(int dy) {
        int maxHeight = AndroidUtilities.dp(56.0f);
        int minHeight = Math.max(AndroidUtilities.statusBarHeight, AndroidUtilities.dp(24.0f));
        float heightDiff = (float) (maxHeight - minHeight);
        int newHeight = this.currentHeaderHeight - dy;
        if (newHeight < minHeight) {
            newHeight = minHeight;
        } else if (newHeight > maxHeight) {
            newHeight = maxHeight;
        }
        this.currentHeaderHeight = newHeight;
        float scale = 0.8f + ((((float) (this.currentHeaderHeight - minHeight)) / heightDiff) * 0.2f);
        int scaledHeight = (int) (((float) maxHeight) * scale);
        this.backButton.setScaleX(scale);
        this.backButton.setScaleY(scale);
        this.backButton.setTranslationY((float) ((maxHeight - this.currentHeaderHeight) / 2));
        this.shareContainer.setScaleX(scale);
        this.shareContainer.setScaleY(scale);
        this.settingsButton.setScaleX(scale);
        this.settingsButton.setScaleY(scale);
        this.shareContainer.setTranslationY((float) ((maxHeight - this.currentHeaderHeight) / 2));
        this.settingsButton.setTranslationY((float) ((maxHeight - this.currentHeaderHeight) / 2));
        this.headerView.setTranslationY((float) (this.currentHeaderHeight - maxHeight));
        this.listView.setTopGlowOffset(this.currentHeaderHeight);
    }

    private void openPreviewsChat(User user, long wid) {
        if (user != null && this.parentActivity != null) {
            Bundle args = new Bundle();
            args.putInt("user_id", user.id);
            args.putString("botUser", "webpage" + wid);
            ((LaunchActivity) this.parentActivity).presentFragment(new ChatActivity(args), false, true);
            close(false, true);
        }
    }

    private void addAllMediaFromBlock(TLRPC$PageBlock block) {
        if ((block instanceof TLRPC$TL_pageBlockPhoto) || ((block instanceof TLRPC$TL_pageBlockVideo) && isVideoBlock(block))) {
            this.photoBlocks.add(block);
        } else if (block instanceof TLRPC$TL_pageBlockSlideshow) {
            TLRPC$TL_pageBlockSlideshow slideshow = (TLRPC$TL_pageBlockSlideshow) block;
            count = slideshow.items.size();
            for (a = 0; a < count; a++) {
                innerBlock = (TLRPC$PageBlock) slideshow.items.get(a);
                if ((innerBlock instanceof TLRPC$TL_pageBlockPhoto) || ((innerBlock instanceof TLRPC$TL_pageBlockVideo) && isVideoBlock(block))) {
                    this.photoBlocks.add(innerBlock);
                }
            }
        } else if (block instanceof TLRPC$TL_pageBlockCollage) {
            TLRPC$TL_pageBlockCollage collage = (TLRPC$TL_pageBlockCollage) block;
            count = collage.items.size();
            for (a = 0; a < count; a++) {
                innerBlock = (TLRPC$PageBlock) collage.items.get(a);
                if ((innerBlock instanceof TLRPC$TL_pageBlockPhoto) || ((innerBlock instanceof TLRPC$TL_pageBlockVideo) && isVideoBlock(block))) {
                    this.photoBlocks.add(innerBlock);
                }
            }
        } else if (!(block instanceof TLRPC$TL_pageBlockCover)) {
        } else {
            if ((block.cover instanceof TLRPC$TL_pageBlockPhoto) || ((block.cover instanceof TLRPC$TL_pageBlockVideo) && isVideoBlock(block.cover))) {
                this.photoBlocks.add(block.cover);
            }
        }
    }

    public boolean open(MessageObject messageObject) {
        return open(messageObject, null, null, true);
    }

    public boolean open(TLRPC$TL_webPage webpage, String url) {
        return open(null, webpage, url, true);
    }

    private boolean open(MessageObject messageObject, TLRPC$WebPage webpage, String url, boolean first) {
        if (this.parentActivity == null || ((this.isVisible && !this.collapsed) || (messageObject == null && webpage == null))) {
            return false;
        }
        if (messageObject != null) {
            webpage = messageObject.messageOwner.media.webpage;
        }
        if (first) {
            TLRPC$TL_messages_getWebPage req = new TLRPC$TL_messages_getWebPage();
            req.url = webpage.url;
            if (webpage.cached_page instanceof TLRPC$TL_pagePart) {
                req.hash = 0;
            } else {
                req.hash = webpage.hash;
            }
            final TLRPC$WebPage webPageFinal = webpage;
            final MessageObject messageObject2 = messageObject;
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(TLObject response, TLRPC$TL_error error) {
                    if (response instanceof TLRPC$TL_webPage) {
                        final TLRPC$TL_webPage webPage = (TLRPC$TL_webPage) response;
                        if (webPage.cached_page != null) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (!ArticleViewer.this.pagesStack.isEmpty() && ArticleViewer.this.pagesStack.get(0) == webPageFinal && webPage.cached_page != null) {
                                        if (messageObject2 != null) {
                                            messageObject2.messageOwner.media.webpage = webPage;
                                        }
                                        ArticleViewer.this.pagesStack.set(0, webPage);
                                        if (ArticleViewer.this.pagesStack.size() == 1) {
                                            ArticleViewer.this.currentPage = webPage;
                                            ApplicationLoader.applicationContext.getSharedPreferences("articles", 0).edit().remove("article" + ArticleViewer.this.currentPage.id).commit();
                                            ArticleViewer.this.updateInterfaceForCurrentPage(false);
                                        }
                                    }
                                }
                            });
                            HashMap<Long, TLRPC$WebPage> webpages = new HashMap();
                            webpages.put(Long.valueOf(webPage.id), webPage);
                            MessagesStorage.getInstance().putWebPages(webpages);
                        }
                    }
                }
            });
        }
        this.pagesStack.clear();
        this.collapsed = false;
        this.backDrawable.setRotation(0.0f, false);
        this.containerView.setTranslationX(0.0f);
        this.containerView.setTranslationY(0.0f);
        this.listView.setTranslationY(0.0f);
        this.listView.setAlpha(1.0f);
        this.windowView.setInnerTranslationX(0.0f);
        this.actionBar.setVisibility(8);
        this.bottomLayout.setVisibility(8);
        this.captionTextViewNew.setVisibility(8);
        this.captionTextViewOld.setVisibility(8);
        this.shareContainer.setAlpha(0.0f);
        this.backButton.setAlpha(0.0f);
        this.settingsButton.setAlpha(0.0f);
        this.layoutManager.scrollToPositionWithOffset(0, 0);
        checkScroll(-AndroidUtilities.dp(56.0f));
        String anchor = null;
        int index;
        if (messageObject != null) {
            webpage = messageObject.messageOwner.media.webpage;
            String webPageUrl = webpage.url.toLowerCase();
            for (int a = 0; a < messageObject.messageOwner.entities.size(); a++) {
                TLRPC$MessageEntity entity = (TLRPC$MessageEntity) messageObject.messageOwner.entities.get(a);
                if (entity instanceof TLRPC$TL_messageEntityUrl) {
                    try {
                        url = messageObject.messageOwner.message.substring(entity.offset, entity.offset + entity.length).toLowerCase();
                        if (url.contains(webPageUrl) || webPageUrl.contains(url)) {
                            index = url.lastIndexOf(35);
                            if (index != -1) {
                                anchor = url.substring(index + 1);
                            }
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            }
        } else if (url != null) {
            index = url.lastIndexOf(35);
            if (index != -1) {
                anchor = url.substring(index + 1);
            }
        }
        addPageToStack(webpage, anchor);
        this.lastInsets = null;
        LayoutParams layoutParams;
        if (this.isVisible) {
            layoutParams = this.windowLayoutParams;
            layoutParams.flags &= -17;
            ((WindowManager) this.parentActivity.getSystemService("window")).updateViewLayout(this.windowView, this.windowLayoutParams);
        } else {
            WindowManager wm = (WindowManager) this.parentActivity.getSystemService("window");
            if (this.attachedToWindow) {
                try {
                    wm.removeView(this.windowView);
                } catch (Exception e2) {
                }
            }
            try {
                if (VERSION.SDK_INT >= 21) {
                    this.windowLayoutParams.flags = -2147417856;
                }
                layoutParams = this.windowLayoutParams;
                layoutParams.flags |= 1032;
                this.windowView.setFocusable(false);
                this.containerView.setFocusable(false);
                wm.addView(this.windowView, this.windowLayoutParams);
            } catch (Exception e3) {
                FileLog.e(e3);
                return false;
            }
        }
        this.isVisible = true;
        this.animationInProgress = 1;
        this.windowView.setAlpha(0.0f);
        this.containerView.setAlpha(0.0f);
        final AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[3];
        float[] fArr = new float[2];
        animatorArr[0] = ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{0.0f, 1.0f});
        fArr = new float[2];
        animatorArr[1] = ObjectAnimator.ofFloat(this.containerView, "alpha", new float[]{0.0f, 1.0f});
        animatorArr[2] = ObjectAnimator.ofFloat(this.windowView, "translationX", new float[]{(float) AndroidUtilities.dp(56.0f), 0.0f});
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
            class C20721 implements Runnable {
                C20721() {
                }

                public void run() {
                    NotificationCenter.getInstance().setAnimationInProgress(false);
                    if (ArticleViewer.this.animationEndRunnable != null) {
                        ArticleViewer.this.animationEndRunnable.run();
                        ArticleViewer.this.animationEndRunnable = null;
                    }
                }
            }

            public void onAnimationEnd(Animator animation) {
                AndroidUtilities.runOnUIThread(new C20721());
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
        showActionBar(200);
        return true;
    }

    private void hideActionBar() {
        AnimatorSet animatorSet = new AnimatorSet();
        r1 = new Animator[3];
        r1[0] = ObjectAnimator.ofFloat(this.backButton, "alpha", new float[]{0.0f});
        r1[1] = ObjectAnimator.ofFloat(this.shareContainer, "alpha", new float[]{0.0f});
        r1[2] = ObjectAnimator.ofFloat(this.settingsButton, "alpha", new float[]{0.0f});
        animatorSet.playTogether(r1);
        animatorSet.setDuration(250);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
    }

    private void showActionBar(int delay) {
        AnimatorSet animatorSet = new AnimatorSet();
        r1 = new Animator[3];
        r1[0] = ObjectAnimator.ofFloat(this.backButton, "alpha", new float[]{1.0f});
        r1[1] = ObjectAnimator.ofFloat(this.shareContainer, "alpha", new float[]{1.0f});
        r1[2] = ObjectAnimator.ofFloat(this.settingsButton, "alpha", new float[]{1.0f});
        animatorSet.playTogether(r1);
        animatorSet.setDuration(150);
        animatorSet.setStartDelay((long) delay);
        animatorSet.start();
    }

    private void showProgressView(final boolean show) {
        if (this.progressViewAnimation != null) {
            this.progressViewAnimation.cancel();
        }
        this.progressViewAnimation = new AnimatorSet();
        AnimatorSet animatorSet;
        Animator[] animatorArr;
        if (show) {
            this.progressView.setVisibility(0);
            this.shareContainer.setEnabled(false);
            animatorSet = this.progressViewAnimation;
            animatorArr = new Animator[6];
            animatorArr[0] = ObjectAnimator.ofFloat(this.shareButton, "scaleX", new float[]{0.1f});
            animatorArr[1] = ObjectAnimator.ofFloat(this.shareButton, "scaleY", new float[]{0.1f});
            animatorArr[2] = ObjectAnimator.ofFloat(this.shareButton, "alpha", new float[]{0.0f});
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
            animatorArr[2] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{0.0f});
            animatorArr[3] = ObjectAnimator.ofFloat(this.shareButton, "scaleX", new float[]{1.0f});
            animatorArr[4] = ObjectAnimator.ofFloat(this.shareButton, "scaleY", new float[]{1.0f});
            animatorArr[5] = ObjectAnimator.ofFloat(this.shareButton, "alpha", new float[]{1.0f});
            animatorSet.playTogether(animatorArr);
        }
        this.progressViewAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (ArticleViewer.this.progressViewAnimation != null && ArticleViewer.this.progressViewAnimation.equals(animation)) {
                    if (show) {
                        ArticleViewer.this.shareButton.setVisibility(4);
                    } else {
                        ArticleViewer.this.progressView.setVisibility(4);
                    }
                }
            }

            public void onAnimationCancel(Animator animation) {
                if (ArticleViewer.this.progressViewAnimation != null && ArticleViewer.this.progressViewAnimation.equals(animation)) {
                    ArticleViewer.this.progressViewAnimation = null;
                }
            }
        });
        this.progressViewAnimation.setDuration(150);
        this.progressViewAnimation.start();
    }

    public void collapse() {
        if (this.parentActivity != null && this.isVisible && !checkAnimation()) {
            int i;
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
            } catch (Exception e) {
                FileLog.e(e);
            }
            AnimatorSet animatorSet = new AnimatorSet();
            Animator[] animatorArr = new Animator[12];
            animatorArr[0] = ObjectAnimator.ofFloat(this.containerView, "translationX", new float[]{(float) (this.containerView.getMeasuredWidth() - AndroidUtilities.dp(56.0f))});
            FrameLayout frameLayout = this.containerView;
            String str = "translationY";
            float[] fArr = new float[1];
            int currentActionBarHeight = ActionBar.getCurrentActionBarHeight();
            if (VERSION.SDK_INT >= 21) {
                i = AndroidUtilities.statusBarHeight;
            } else {
                i = 0;
            }
            fArr[0] = (float) (i + currentActionBarHeight);
            animatorArr[1] = ObjectAnimator.ofFloat(frameLayout, str, fArr);
            animatorArr[2] = ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{0.0f});
            animatorArr[3] = ObjectAnimator.ofFloat(this.listView, "alpha", new float[]{0.0f});
            animatorArr[4] = ObjectAnimator.ofFloat(this.listView, "translationY", new float[]{(float) (-AndroidUtilities.dp(56.0f))});
            animatorArr[5] = ObjectAnimator.ofFloat(this.headerView, "translationY", new float[]{0.0f});
            animatorArr[6] = ObjectAnimator.ofFloat(this.backButton, "scaleX", new float[]{1.0f});
            animatorArr[7] = ObjectAnimator.ofFloat(this.backButton, "scaleY", new float[]{1.0f});
            animatorArr[8] = ObjectAnimator.ofFloat(this.backButton, "translationY", new float[]{0.0f});
            animatorArr[9] = ObjectAnimator.ofFloat(this.shareContainer, "scaleX", new float[]{1.0f});
            animatorArr[10] = ObjectAnimator.ofFloat(this.shareContainer, "translationY", new float[]{0.0f});
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
                public void onAnimationEnd(Animator animation) {
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

    public void uncollapse() {
        if (this.parentActivity != null && this.isVisible && !checkAnimation()) {
            AnimatorSet animatorSet = new AnimatorSet();
            r1 = new Animator[12];
            r1[0] = ObjectAnimator.ofFloat(this.containerView, "translationX", new float[]{0.0f});
            r1[1] = ObjectAnimator.ofFloat(this.containerView, "translationY", new float[]{0.0f});
            r1[2] = ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f});
            r1[3] = ObjectAnimator.ofFloat(this.listView, "alpha", new float[]{1.0f});
            r1[4] = ObjectAnimator.ofFloat(this.listView, "translationY", new float[]{0.0f});
            r1[5] = ObjectAnimator.ofFloat(this.headerView, "translationY", new float[]{0.0f});
            r1[6] = ObjectAnimator.ofFloat(this.backButton, "scaleX", new float[]{1.0f});
            r1[7] = ObjectAnimator.ofFloat(this.backButton, "scaleY", new float[]{1.0f});
            r1[8] = ObjectAnimator.ofFloat(this.backButton, "translationY", new float[]{0.0f});
            r1[9] = ObjectAnimator.ofFloat(this.shareContainer, "scaleX", new float[]{1.0f});
            r1[10] = ObjectAnimator.ofFloat(this.shareContainer, "translationY", new float[]{0.0f});
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
                public void onAnimationEnd(Animator animation) {
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
            this.backDrawable.setRotation(0.0f, true);
            animatorSet.start();
        }
    }

    private void saveCurrentPagePosition() {
        boolean z = false;
        if (this.currentPage != null) {
            int position = this.layoutManager.findFirstVisibleItemPosition();
            if (position != -1) {
                int offset;
                View view = this.layoutManager.findViewByPosition(position);
                if (view != null) {
                    offset = view.getTop();
                } else {
                    offset = 0;
                }
                Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("articles", 0).edit();
                String key = "article" + this.currentPage.id;
                Editor putInt = editor.putInt(key, position).putInt(key + "o", offset);
                String str = key + "r";
                if (AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y) {
                    z = true;
                }
                putInt.putBoolean(str, z).commit();
            }
        }
    }

    public void close(boolean byBackPress, boolean force) {
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
                if (!force) {
                    return;
                }
            }
            if (this.isPhotoVisible) {
                boolean z;
                if (force) {
                    z = false;
                } else {
                    z = true;
                }
                closePhoto(z);
                if (!force) {
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
            if (!byBackPress || force || !removeLastPageFromStack()) {
                this.parentFragment = null;
                try {
                    if (this.visibleDialog != null) {
                        this.visibleDialog.dismiss();
                        this.visibleDialog = null;
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
                AnimatorSet animatorSet = new AnimatorSet();
                r2 = new Animator[3];
                r2[0] = ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{0.0f});
                r2[1] = ObjectAnimator.ofFloat(this.containerView, "alpha", new float[]{0.0f});
                r2[2] = ObjectAnimator.ofFloat(this.windowView, "translationX", new float[]{0.0f, (float) AndroidUtilities.dp(56.0f)});
                animatorSet.playTogether(r2);
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
                    public void onAnimationEnd(Animator animation) {
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

    private void onClosed() {
        this.isVisible = false;
        this.currentPage = null;
        this.blocks.clear();
        this.photoBlocks.clear();
        this.adapter.notifyDataSetChanged();
        try {
            this.parentActivity.getWindow().clearFlags(128);
        } catch (Exception e) {
            FileLog.e(e);
        }
        for (int a = 0; a < this.createdWebViews.size(); a++) {
            ((BlockEmbedCell) this.createdWebViews.get(a)).destroyWebView(false);
        }
        this.containerView.post(new Runnable() {
            public void run() {
                try {
                    if (ArticleViewer.this.windowView.getParent() != null) {
                        ((WindowManager) ArticleViewer.this.parentActivity.getSystemService("window")).removeView(ArticleViewer.this.windowView);
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private void loadChannel(final BlockChannelCell cell, TLRPC$Chat channel) {
        if (!this.loadingChannel && !TextUtils.isEmpty(channel.username)) {
            this.loadingChannel = true;
            TLRPC$TL_contacts_resolveUsername req = new TLRPC$TL_contacts_resolveUsername();
            req.username = channel.username;
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            ArticleViewer.this.loadingChannel = false;
                            if (ArticleViewer.this.parentFragment != null && ArticleViewer.this.blocks != null && !ArticleViewer.this.blocks.isEmpty()) {
                                if (error == null) {
                                    TLRPC$TL_contacts_resolvedPeer res = response;
                                    if (res.chats.isEmpty()) {
                                        cell.setState(4, false);
                                        return;
                                    }
                                    MessagesController.getInstance().putUsers(res.users, false);
                                    MessagesController.getInstance().putChats(res.chats, false);
                                    MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, false, true);
                                    ArticleViewer.this.loadedChannel = (TLRPC$Chat) res.chats.get(0);
                                    if (!ArticleViewer.this.loadedChannel.left || ArticleViewer.this.loadedChannel.kicked) {
                                        cell.setState(4, false);
                                        return;
                                    } else {
                                        cell.setState(0, false);
                                        return;
                                    }
                                }
                                cell.setState(4, false);
                            }
                        }
                    });
                }
            });
        }
    }

    private void joinChannel(final BlockChannelCell cell, final TLRPC$Chat channel) {
        final TLRPC$TL_channels_joinChannel req = new TLRPC$TL_channels_joinChannel();
        req.channel = MessagesController.getInputChannel(channel);
        ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {

            /* renamed from: org.telegram.ui.ArticleViewer$37$2 */
            class C20762 implements Runnable {
                C20762() {
                }

                public void run() {
                    cell.setState(2, false);
                }
            }

            /* renamed from: org.telegram.ui.ArticleViewer$37$3 */
            class C20773 implements Runnable {
                C20773() {
                }

                public void run() {
                    MessagesController.getInstance().loadFullChat(channel.id, 0, true);
                }
            }

            public void run(TLObject response, final TLRPC$TL_error error) {
                if (error != null) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            cell.setState(0, false);
                            AlertsCreator.processError(error, ArticleViewer.this.parentFragment, req, Boolean.valueOf(true));
                        }
                    });
                    return;
                }
                boolean hasJoinMessage = false;
                TLRPC$Updates updates = (TLRPC$Updates) response;
                for (int a = 0; a < updates.updates.size(); a++) {
                    TLRPC$Update update = (TLRPC$Update) updates.updates.get(a);
                    if ((update instanceof TLRPC$TL_updateNewChannelMessage) && (((TLRPC$TL_updateNewChannelMessage) update).message.action instanceof TLRPC$TL_messageActionChatAddUser)) {
                        hasJoinMessage = true;
                        break;
                    }
                }
                MessagesController.getInstance().processUpdates(updates, false);
                if (!hasJoinMessage) {
                    MessagesController.getInstance().generateJoinMessage(channel.id, true);
                }
                AndroidUtilities.runOnUIThread(new C20762());
                AndroidUtilities.runOnUIThread(new C20773(), 1000);
                MessagesStorage.getInstance().updateDialogsWithDeletedMessages(new ArrayList(), null, true, channel.id);
            }
        });
    }

    private boolean checkAnimation() {
        if (this.animationInProgress != 0 && Math.abs(this.transitionAnimationStartTime - System.currentTimeMillis()) >= 500) {
            if (this.animationEndRunnable != null) {
                this.animationEndRunnable.run();
                this.animationEndRunnable = null;
            }
            this.animationInProgress = 0;
        }
        if (this.animationInProgress != 0) {
            return true;
        }
        return false;
    }

    public void destroyArticleViewer() {
        if (this.parentActivity != null && this.windowView != null) {
            releasePlayer();
            try {
                if (this.windowView.getParent() != null) {
                    ((WindowManager) this.parentActivity.getSystemService("window")).removeViewImmediate(this.windowView);
                }
                this.windowView = null;
            } catch (Exception e) {
                FileLog.e(e);
            }
            for (int a = 0; a < this.createdWebViews.size(); a++) {
                ((BlockEmbedCell) this.createdWebViews.get(a)).destroyWebView(true);
            }
            this.createdWebViews.clear();
            try {
                this.parentActivity.getWindow().clearFlags(128);
            } catch (Exception e2) {
                FileLog.e(e2);
            }
            this.parentActivity = null;
            this.parentFragment = null;
            Instance = null;
        }
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void showDialog(Dialog dialog) {
        if (this.parentActivity != null) {
            try {
                if (this.visibleDialog != null) {
                    this.visibleDialog.dismiss();
                    this.visibleDialog = null;
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            try {
                this.visibleDialog = dialog;
                this.visibleDialog.setCanceledOnTouchOutside(true);
                this.visibleDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        ArticleViewer.this.showActionBar(120);
                        ArticleViewer.this.visibleDialog = null;
                    }
                });
                dialog.show();
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }
    }

    private void onSharePressed() {
        if (this.parentActivity != null && this.currentMedia != null) {
            try {
                File f = getMediaFile(this.currentIndex);
                if (f == null || !f.exists()) {
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
                        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this.parentActivity, "org.ir.talaeii.provider", f));
                        intent.setFlags(1);
                    } catch (Exception e) {
                        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(f));
                    }
                } else {
                    intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(f));
                }
                this.parentActivity.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("ShareFile", R.string.ShareFile)), 500);
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }
    }

    private void setScaleToFill() {
        float bitmapWidth = (float) this.centerImage.getBitmapWidth();
        float containerWidth = (float) getContainerViewWidth();
        float bitmapHeight = (float) this.centerImage.getBitmapHeight();
        float containerHeight = (float) getContainerViewHeight();
        float scaleFit = Math.min(containerHeight / bitmapHeight, containerWidth / bitmapWidth);
        this.scale = Math.max(containerWidth / ((float) ((int) (bitmapWidth * scaleFit))), containerHeight / ((float) ((int) (bitmapHeight * scaleFit))));
        updateMinMax(this.scale);
    }

    private void updateVideoPlayerTime() {
        String newText;
        if (this.videoPlayer == null) {
            newText = String.format("%02d:%02d / %02d:%02d", new Object[]{Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)});
        } else {
            long current = this.videoPlayer.getCurrentPosition() / 1000;
            if (this.videoPlayer.getDuration() / 1000 == C0907C.TIME_UNSET || current == C0907C.TIME_UNSET) {
                newText = String.format("%02d:%02d / %02d:%02d", new Object[]{Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)});
            } else {
                newText = String.format("%02d:%02d / %02d:%02d", new Object[]{Long.valueOf(current / 60), Long.valueOf(current % 60), Long.valueOf(total / 60), Long.valueOf(total % 60)});
            }
        }
        if (!TextUtils.equals(this.videoPlayerTime.getText(), newText)) {
            this.videoPlayerTime.setText(newText);
        }
    }

    @SuppressLint({"NewApi"})
    private void preparePlayer(File file, boolean playWhenReady) {
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
            this.videoCrossfadeAlpha = 0.0f;
            textureView.setAlpha(0.0f);
            this.videoPlayButton.setImageResource(R.drawable.inline_video_play);
            if (this.videoPlayer == null) {
                long duration;
                this.videoPlayer = new VideoPlayer();
                this.videoPlayer.setTextureView(this.videoTextureView);
                this.videoPlayer.setDelegate(new VideoPlayerDelegate() {
                    public void onStateChanged(boolean playWhenReady, int playbackState) {
                        if (ArticleViewer.this.videoPlayer != null) {
                            if (playbackState == 4 || playbackState == 1) {
                                try {
                                    ArticleViewer.this.parentActivity.getWindow().clearFlags(128);
                                } catch (Exception e) {
                                    FileLog.e(e);
                                }
                            } else {
                                try {
                                    ArticleViewer.this.parentActivity.getWindow().addFlags(128);
                                } catch (Exception e2) {
                                    FileLog.e(e2);
                                }
                            }
                            if (playbackState == 3 && ArticleViewer.this.aspectRatioFrameLayout.getVisibility() != 0) {
                                ArticleViewer.this.aspectRatioFrameLayout.setVisibility(0);
                            }
                            if (!ArticleViewer.this.videoPlayer.isPlaying() || playbackState == 4) {
                                if (ArticleViewer.this.isPlaying) {
                                    ArticleViewer.this.isPlaying = false;
                                    ArticleViewer.this.videoPlayButton.setImageResource(R.drawable.inline_video_play);
                                    AndroidUtilities.cancelRunOnUIThread(ArticleViewer.this.updateProgressRunnable);
                                    if (playbackState == 4 && !ArticleViewer.this.videoPlayerSeekbar.isDragging()) {
                                        ArticleViewer.this.videoPlayerSeekbar.setProgress(0.0f);
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

                    public void onError(Exception e) {
                        FileLog.e(e);
                    }

                    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
                        if (ArticleViewer.this.aspectRatioFrameLayout != null) {
                            if (unappliedRotationDegrees == 90 || unappliedRotationDegrees == 270) {
                                int temp = width;
                                width = height;
                                height = temp;
                            }
                            ArticleViewer.this.aspectRatioFrameLayout.setAspectRatio(height == 0 ? 1.0f : (((float) width) * pixelWidthHeightRatio) / ((float) height), unappliedRotationDegrees);
                        }
                    }

                    public void onRenderedFirstFrame() {
                        if (!ArticleViewer.this.textureUploaded) {
                            ArticleViewer.this.textureUploaded = true;
                            ArticleViewer.this.containerView.invalidate();
                        }
                    }

                    public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
                        return false;
                    }

                    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                    }
                });
                if (this.videoPlayer != null) {
                    duration = this.videoPlayer.getDuration();
                    if (duration == C0907C.TIME_UNSET) {
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
            this.videoPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    private void releasePlayer() {
        if (this.videoPlayer != null) {
            this.videoPlayer.releasePlayer();
            this.videoPlayer = null;
        }
        try {
            this.parentActivity.getWindow().clearFlags(128);
        } catch (Exception e) {
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

    private void toggleActionBar(boolean show, boolean animated) {
        float f = 1.0f;
        if (show) {
            this.actionBar.setVisibility(0);
            if (this.videoPlayer != null) {
                this.bottomLayout.setVisibility(0);
            }
            if (this.captionTextView.getTag() != null) {
                this.captionTextView.setVisibility(0);
            }
        }
        this.isActionBarVisible = show;
        this.actionBar.setEnabled(show);
        this.bottomLayout.setEnabled(show);
        float f2;
        if (animated) {
            ArrayList<Animator> arrayList = new ArrayList();
            ActionBar actionBar = this.actionBar;
            String str = "alpha";
            float[] fArr = new float[1];
            if (show) {
                f2 = 1.0f;
            } else {
                f2 = 0.0f;
            }
            fArr[0] = f2;
            arrayList.add(ObjectAnimator.ofFloat(actionBar, str, fArr));
            FrameLayout frameLayout = this.bottomLayout;
            str = "alpha";
            fArr = new float[1];
            if (show) {
                f2 = 1.0f;
            } else {
                f2 = 0.0f;
            }
            fArr[0] = f2;
            arrayList.add(ObjectAnimator.ofFloat(frameLayout, str, fArr));
            if (this.captionTextView.getTag() != null) {
                TextView textView = this.captionTextView;
                String str2 = "alpha";
                float[] fArr2 = new float[1];
                if (!show) {
                    f = 0.0f;
                }
                fArr2[0] = f;
                arrayList.add(ObjectAnimator.ofFloat(textView, str2, fArr2));
            }
            this.currentActionBarAnimation = new AnimatorSet();
            this.currentActionBarAnimation.playTogether(arrayList);
            if (!show) {
                this.currentActionBarAnimation.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        if (ArticleViewer.this.currentActionBarAnimation != null && ArticleViewer.this.currentActionBarAnimation.equals(animation)) {
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
        this.actionBar.setAlpha(show ? 1.0f : 0.0f);
        frameLayout = this.bottomLayout;
        if (show) {
            f2 = 1.0f;
        } else {
            f2 = 0.0f;
        }
        frameLayout.setAlpha(f2);
        if (this.captionTextView.getTag() != null) {
            textView = this.captionTextView;
            if (!show) {
                f = 0.0f;
            }
            textView.setAlpha(f);
        }
        if (!show) {
            this.actionBar.setVisibility(8);
            if (this.videoPlayer != null) {
                this.bottomLayout.setVisibility(8);
            }
            if (this.captionTextView.getTag() != null) {
                this.captionTextView.setVisibility(4);
            }
        }
    }

    private String getFileName(int index) {
        TLObject media = getMedia(index);
        if (media instanceof TLRPC$Photo) {
            media = FileLoader.getClosestPhotoSizeWithSize(((TLRPC$Photo) media).sizes, AndroidUtilities.getPhotoSize());
        }
        return FileLoader.getAttachFileName(media);
    }

    private TLObject getMedia(int index) {
        if (this.imagesArr.isEmpty() || index >= this.imagesArr.size() || index < 0) {
            return null;
        }
        TLRPC$PageBlock block = (TLRPC$PageBlock) this.imagesArr.get(index);
        if (block.photo_id != 0) {
            return getPhotoWithId(block.photo_id);
        }
        if (block.video_id != 0) {
            return getDocumentWithId(block.video_id);
        }
        return null;
    }

    private File getMediaFile(int index) {
        if (this.imagesArr.isEmpty() || index >= this.imagesArr.size() || index < 0) {
            return null;
        }
        TLRPC$PageBlock block = (TLRPC$PageBlock) this.imagesArr.get(index);
        if (block.photo_id != 0) {
            TLRPC$Photo photo = getPhotoWithId(block.photo_id);
            if (photo == null) {
                return null;
            }
            TLRPC$PhotoSize sizeFull = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.getPhotoSize());
            if (sizeFull != null) {
                return FileLoader.getPathToAttach(sizeFull, true);
            }
            return null;
        } else if (block.video_id == 0) {
            return null;
        } else {
            TLRPC$Document document = getDocumentWithId(block.video_id);
            if (document != null) {
                return FileLoader.getPathToAttach(document, true);
            }
            return null;
        }
    }

    private boolean isVideoBlock(TLRPC$PageBlock block) {
        if (!(block == null || block.video_id == 0)) {
            TLRPC$Document document = getDocumentWithId(block.video_id);
            if (document != null) {
                return MessageObject.isVideoDocument(document);
            }
        }
        return false;
    }

    private boolean isMediaVideo(int index) {
        return !this.imagesArr.isEmpty() && index < this.imagesArr.size() && index >= 0 && isVideoBlock((TLRPC$PageBlock) this.imagesArr.get(index));
    }

    private TLRPC$FileLocation getFileLocation(int index, int[] size) {
        if (index < 0 || index >= this.imagesArr.size()) {
            return null;
        }
        TLObject media = getMedia(index);
        if (media instanceof TLRPC$Photo) {
            TLRPC$PhotoSize sizeFull = FileLoader.getClosestPhotoSizeWithSize(((TLRPC$Photo) media).sizes, AndroidUtilities.getPhotoSize());
            if (sizeFull != null) {
                size[0] = sizeFull.size;
                if (size[0] == 0) {
                    size[0] = -1;
                }
                return sizeFull.location;
            }
            size[0] = -1;
            return null;
        } else if (!(media instanceof TLRPC$Document)) {
            return null;
        } else {
            TLRPC$Document document = (TLRPC$Document) media;
            if (document.thumb == null) {
                return null;
            }
            size[0] = document.thumb.size;
            if (size[0] == 0) {
                size[0] = -1;
            }
            return document.thumb.location;
        }
    }

    private void onPhotoShow(int index, PlaceProviderObject object) {
        Bitmap bitmap;
        this.currentIndex = -1;
        this.currentFileNames[0] = null;
        this.currentFileNames[1] = null;
        this.currentFileNames[2] = null;
        if (object != null) {
            bitmap = object.thumb;
        } else {
            bitmap = null;
        }
        this.currentThumb = bitmap;
        this.menuItem.setVisibility(0);
        this.menuItem.hideSubItem(3);
        this.actionBar.setTranslationY(0.0f);
        this.captionTextView.setTag(null);
        this.captionTextView.setVisibility(4);
        for (int a = 0; a < 3; a++) {
            if (this.radialProgressViews[a] != null) {
                this.radialProgressViews[a].setBackgroundState(-1, false);
            }
        }
        setImageIndex(index, true);
        if (this.currentMedia != null && isMediaVideo(this.currentIndex)) {
            onActionClick(false);
        }
    }

    private void setImages() {
        if (this.photoAnimationInProgress == 0) {
            setIndexToImage(this.centerImage, this.currentIndex);
            setIndexToImage(this.rightImage, this.currentIndex + 1);
            setIndexToImage(this.leftImage, this.currentIndex - 1);
        }
    }

    private void setImageIndex(int index, boolean init) {
        if (this.currentIndex != index) {
            int a;
            if (!init) {
                this.currentThumb = null;
            }
            this.currentFileNames[0] = getFileName(index);
            this.currentFileNames[1] = getFileName(index + 1);
            this.currentFileNames[2] = getFileName(index - 1);
            int prevIndex = this.currentIndex;
            this.currentIndex = index;
            boolean isVideo = false;
            boolean sameImage = false;
            if (!this.imagesArr.isEmpty()) {
                if (this.currentIndex < 0 || this.currentIndex >= this.imagesArr.size()) {
                    closePhoto(false);
                    return;
                }
                TLRPC$PageBlock newMedia = (TLRPC$PageBlock) this.imagesArr.get(this.currentIndex);
                sameImage = this.currentMedia != null && this.currentMedia == newMedia;
                this.currentMedia = newMedia;
                isVideo = isMediaVideo(this.currentIndex);
                if (isVideo) {
                    this.menuItem.showSubItem(3);
                }
                setCurrentCaption(getText(this.currentMedia.caption, this.currentMedia.caption, this.currentMedia));
                if (this.currentAnimation != null) {
                    this.menuItem.setVisibility(8);
                    this.menuItem.hideSubItem(1);
                    this.actionBar.setTitle(LocaleController.getString("AttachGif", R.string.AttachGif));
                } else {
                    this.menuItem.setVisibility(0);
                    if (this.imagesArr.size() != 1) {
                        this.actionBar.setTitle(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(this.currentIndex + 1), Integer.valueOf(this.imagesArr.size())}));
                    } else if (isVideo) {
                        this.actionBar.setTitle(LocaleController.getString("AttachVideo", R.string.AttachVideo));
                    } else {
                        this.actionBar.setTitle(LocaleController.getString("AttachPhoto", R.string.AttachPhoto));
                    }
                    this.menuItem.showSubItem(1);
                }
            }
            int count = this.listView.getChildCount();
            for (a = 0; a < count; a++) {
                View child = this.listView.getChildAt(a);
                if (child instanceof BlockSlideshowCell) {
                    BlockSlideshowCell cell = (BlockSlideshowCell) child;
                    int idx = cell.currentBlock.items.indexOf(this.currentMedia);
                    if (idx != -1) {
                        cell.innerListView.setCurrentItem(idx, false);
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
            if (!sameImage) {
                this.draggingDown = false;
                this.translationX = 0.0f;
                this.translationY = 0.0f;
                this.scale = 1.0f;
                this.animateToX = 0.0f;
                this.animateToY = 0.0f;
                this.animateToScale = 1.0f;
                this.animationStartTime = 0;
                this.imageMoveAnimation = null;
                if (this.aspectRatioFrameLayout != null) {
                    this.aspectRatioFrameLayout.setVisibility(4);
                }
                releasePlayer();
                this.pinchStartDistance = 0.0f;
                this.pinchStartScale = 1.0f;
                this.pinchCenterX = 0.0f;
                this.pinchCenterY = 0.0f;
                this.pinchStartX = 0.0f;
                this.pinchStartY = 0.0f;
                this.moveStartX = 0.0f;
                this.moveStartY = 0.0f;
                this.zooming = false;
                this.moving = false;
                this.doubleTap = false;
                this.invalidCoords = false;
                this.canDragDown = true;
                this.changingPage = false;
                this.switchImageAfterAnimation = 0;
                boolean z = (this.currentFileNames[0] == null || isVideo || this.radialProgressViews[0].backgroundState == 0) ? false : true;
                this.canZoom = z;
                updateMinMax(this.scale);
            }
            if (prevIndex == -1) {
                setImages();
                for (a = 0; a < 3; a++) {
                    checkProgress(a, false);
                }
                return;
            }
            checkProgress(0, false);
            ImageReceiver temp;
            RadialProgressView tempProgress;
            if (prevIndex > this.currentIndex) {
                temp = this.rightImage;
                this.rightImage = this.centerImage;
                this.centerImage = this.leftImage;
                this.leftImage = temp;
                tempProgress = this.radialProgressViews[0];
                this.radialProgressViews[0] = this.radialProgressViews[2];
                this.radialProgressViews[2] = tempProgress;
                setIndexToImage(this.leftImage, this.currentIndex - 1);
                checkProgress(1, false);
                checkProgress(2, false);
            } else if (prevIndex < this.currentIndex) {
                temp = this.leftImage;
                this.leftImage = this.centerImage;
                this.centerImage = this.rightImage;
                this.rightImage = temp;
                tempProgress = this.radialProgressViews[0];
                this.radialProgressViews[0] = this.radialProgressViews[1];
                this.radialProgressViews[1] = tempProgress;
                setIndexToImage(this.rightImage, this.currentIndex + 1);
                checkProgress(1, false);
                checkProgress(2, false);
            }
        }
    }

    private void setCurrentCaption(CharSequence caption) {
        if (TextUtils.isEmpty(caption)) {
            this.captionTextView.setTextColor(-1);
            this.captionTextView.setTag(null);
            this.captionTextView.setVisibility(4);
            return;
        }
        this.captionTextView = this.captionTextViewOld;
        this.captionTextViewOld = this.captionTextViewNew;
        this.captionTextViewNew = this.captionTextView;
        Theme.createChatResources(null, true);
        CharSequence str = Emoji.replaceEmoji(new SpannableStringBuilder(caption.toString()), this.captionTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
        this.captionTextView.setTag(str);
        this.captionTextView.setText(str);
        this.captionTextView.setTextColor(-1);
        this.captionTextView.setAlpha(this.actionBar.getVisibility() == 0 ? 1.0f : 0.0f);
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

    private void checkProgress(int a, boolean animated) {
        boolean z = true;
        if (this.currentFileNames[a] != null) {
            int index = this.currentIndex;
            if (a == 1) {
                index++;
            } else if (a == 2) {
                index--;
            }
            File f = getMediaFile(index);
            boolean isVideo = isMediaVideo(index);
            if (f == null || !f.exists()) {
                if (!isVideo) {
                    this.radialProgressViews[a].setBackgroundState(0, animated);
                } else if (FileLoader.getInstance().isLoadingFile(this.currentFileNames[a])) {
                    this.radialProgressViews[a].setBackgroundState(1, false);
                } else {
                    this.radialProgressViews[a].setBackgroundState(2, false);
                }
                Float progress = ImageLoader.getInstance().getFileProgress(this.currentFileNames[a]);
                if (progress == null) {
                    progress = Float.valueOf(0.0f);
                }
                this.radialProgressViews[a].setProgress(progress.floatValue(), false);
            } else if (isVideo) {
                this.radialProgressViews[a].setBackgroundState(3, animated);
            } else {
                this.radialProgressViews[a].setBackgroundState(-1, animated);
            }
            if (a == 0) {
                if (this.currentFileNames[0] == null || isVideo || this.radialProgressViews[0].backgroundState == 0) {
                    z = false;
                }
                this.canZoom = z;
                return;
            }
            return;
        }
        this.radialProgressViews[a].setBackgroundState(-1, animated);
    }

    private void setIndexToImage(ImageReceiver imageReceiver, int index) {
        imageReceiver.setOrientation(0, false);
        int[] size = new int[1];
        TLRPC$FileLocation fileLocation = getFileLocation(index, size);
        if (fileLocation != null) {
            TLObject media = getMedia(index);
            Bitmap placeHolder;
            if (media instanceof TLRPC$Photo) {
                Drawable bitmapDrawable;
                TLRPC$FileLocation tLRPC$FileLocation;
                TLRPC$Photo photo = (TLRPC$Photo) media;
                placeHolder = null;
                if (this.currentThumb != null && imageReceiver == this.centerImage) {
                    placeHolder = this.currentThumb;
                }
                if (size[0] == 0) {
                    size[0] = -1;
                }
                TLRPC$PhotoSize thumbLocation = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, 80);
                if (placeHolder != null) {
                    bitmapDrawable = new BitmapDrawable(null, placeHolder);
                } else {
                    bitmapDrawable = null;
                }
                if (thumbLocation != null) {
                    tLRPC$FileLocation = thumbLocation.location;
                } else {
                    tLRPC$FileLocation = null;
                }
                imageReceiver.setImage(fileLocation, null, null, bitmapDrawable, tLRPC$FileLocation, "b", size[0], null, 1);
            } else if (isMediaVideo(index)) {
                if (fileLocation instanceof TLRPC$TL_fileLocationUnavailable) {
                    imageReceiver.setImageBitmap(this.parentActivity.getResources().getDrawable(R.drawable.photoview_placeholder));
                    return;
                }
                placeHolder = null;
                if (this.currentThumb != null && imageReceiver == this.centerImage) {
                    placeHolder = this.currentThumb;
                }
                imageReceiver.setImage(null, null, null, placeHolder != null ? new BitmapDrawable(null, placeHolder) : null, fileLocation, "b", 0, null, 1);
            } else if (this.currentAnimation != null) {
                imageReceiver.setImageBitmap(this.currentAnimation);
                this.currentAnimation.setSecondParentView(this.photoContainerView);
            }
        } else if (size[0] == 0) {
            imageReceiver.setImageBitmap((Bitmap) null);
        } else {
            imageReceiver.setImageBitmap(this.parentActivity.getResources().getDrawable(R.drawable.photoview_placeholder));
        }
    }

    public boolean isShowingImage(TLRPC$PageBlock object) {
        return this.isPhotoVisible && !this.disableShowCheck && object != null && this.currentMedia == object;
    }

    private boolean checkPhotoAnimation() {
        if (this.photoAnimationInProgress != 0 && Math.abs(this.photoTransitionAnimationStartTime - System.currentTimeMillis()) >= 500) {
            if (this.photoAnimationEndRunnable != null) {
                this.photoAnimationEndRunnable.run();
                this.photoAnimationEndRunnable = null;
            }
            this.photoAnimationInProgress = 0;
        }
        if (this.photoAnimationInProgress != 0) {
            return true;
        }
        return false;
    }

    public boolean openPhoto(TLRPC$PageBlock block) {
        if (this.parentActivity == null || this.isPhotoVisible || checkPhotoAnimation() || block == null) {
            return false;
        }
        final PlaceProviderObject object = getPlaceForPhoto(block);
        if (object == null) {
            return false;
        }
        float scale;
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidFailedLoad);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileLoadProgressChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.isPhotoVisible = true;
        toggleActionBar(true, false);
        this.actionBar.setAlpha(0.0f);
        this.bottomLayout.setAlpha(0.0f);
        this.captionTextView.setAlpha(0.0f);
        this.photoBackgroundDrawable.setAlpha(0);
        this.disableShowCheck = true;
        this.photoAnimationInProgress = 1;
        if (block != null) {
            this.currentAnimation = object.imageReceiver.getAnimation();
        }
        int index = this.photoBlocks.indexOf(block);
        this.imagesArr.clear();
        if (!(block instanceof TLRPC$TL_pageBlockVideo) || isVideoBlock(block)) {
            this.imagesArr.addAll(this.photoBlocks);
        } else {
            this.imagesArr.add(block);
            index = 0;
        }
        onPhotoShow(index, object);
        Rect drawRegion = object.imageReceiver.getDrawRegion();
        int orientation = object.imageReceiver.getOrientation();
        int animatedOrientation = object.imageReceiver.getAnimatedOrientation();
        if (animatedOrientation != 0) {
            orientation = animatedOrientation;
        }
        this.animatingImageView.setVisibility(0);
        this.animatingImageView.setRadius(object.radius);
        this.animatingImageView.setOrientation(orientation);
        this.animatingImageView.setNeedRadius(object.radius != 0);
        this.animatingImageView.setImageBitmap(object.thumb);
        this.animatingImageView.setAlpha(1.0f);
        this.animatingImageView.setPivotX(0.0f);
        this.animatingImageView.setPivotY(0.0f);
        this.animatingImageView.setScaleX(object.scale);
        this.animatingImageView.setScaleY(object.scale);
        this.animatingImageView.setTranslationX(((float) object.viewX) + (((float) drawRegion.left) * object.scale));
        this.animatingImageView.setTranslationY(((float) object.viewY) + (((float) drawRegion.top) * object.scale));
        ViewGroup.LayoutParams layoutParams = this.animatingImageView.getLayoutParams();
        layoutParams.width = drawRegion.right - drawRegion.left;
        layoutParams.height = drawRegion.bottom - drawRegion.top;
        this.animatingImageView.setLayoutParams(layoutParams);
        float scaleX = ((float) AndroidUtilities.displaySize.x) / ((float) layoutParams.width);
        float scaleY = ((float) (AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight)) / ((float) layoutParams.height);
        if (scaleX > scaleY) {
            scale = scaleY;
        } else {
            scale = scaleX;
        }
        float height = ((float) layoutParams.height) * scale;
        float xPos = (((float) AndroidUtilities.displaySize.x) - (((float) layoutParams.width) * scale)) / 2.0f;
        if (VERSION.SDK_INT >= 21 && this.lastInsets != null) {
            xPos += (float) ((WindowInsets) this.lastInsets).getSystemWindowInsetLeft();
        }
        float yPos = (((float) (AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight)) - height) / 2.0f;
        int clipHorizontal = Math.abs(drawRegion.left - object.imageReceiver.getImageX());
        int clipVertical = Math.abs(drawRegion.top - object.imageReceiver.getImageY());
        int[] coords2 = new int[2];
        object.parentView.getLocationInWindow(coords2);
        int clipTop = (coords2[1] - (object.viewY + drawRegion.top)) + object.clipTopAddition;
        if (clipTop < 0) {
            clipTop = 0;
        }
        int clipBottom = (((object.viewY + drawRegion.top) + layoutParams.height) - (coords2[1] + object.parentView.getHeight())) + object.clipBottomAddition;
        if (clipBottom < 0) {
            clipBottom = 0;
        }
        clipTop = Math.max(clipTop, clipVertical);
        clipBottom = Math.max(clipBottom, clipVertical);
        this.animationValues[0][0] = this.animatingImageView.getScaleX();
        this.animationValues[0][1] = this.animatingImageView.getScaleY();
        this.animationValues[0][2] = this.animatingImageView.getTranslationX();
        this.animationValues[0][3] = this.animatingImageView.getTranslationY();
        this.animationValues[0][4] = ((float) clipHorizontal) * object.scale;
        this.animationValues[0][5] = ((float) clipTop) * object.scale;
        this.animationValues[0][6] = ((float) clipBottom) * object.scale;
        this.animationValues[0][7] = (float) this.animatingImageView.getRadius();
        this.animationValues[1][0] = scale;
        this.animationValues[1][1] = scale;
        this.animationValues[1][2] = xPos;
        this.animationValues[1][3] = yPos;
        this.animationValues[1][4] = 0.0f;
        this.animationValues[1][5] = 0.0f;
        this.animationValues[1][6] = 0.0f;
        this.animationValues[1][7] = 0.0f;
        this.photoContainerView.setVisibility(0);
        this.photoContainerBackground.setVisibility(0);
        this.animatingImageView.setAnimationProgress(0.0f);
        final AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[5];
        float[] fArr = new float[2];
        animatorArr[0] = ObjectAnimator.ofFloat(this.animatingImageView, "animationProgress", new float[]{0.0f, 1.0f});
        int[] iArr = new int[2];
        animatorArr[1] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0, 255});
        fArr = new float[2];
        animatorArr[2] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{0.0f, 1.0f});
        fArr = new float[2];
        animatorArr[3] = ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{0.0f, 1.0f});
        fArr = new float[2];
        animatorArr[4] = ObjectAnimator.ofFloat(this.captionTextView, "alpha", new float[]{0.0f, 1.0f});
        animatorSet.playTogether(animatorArr);
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
            class C20791 implements Runnable {
                C20791() {
                }

                public void run() {
                    NotificationCenter.getInstance().setAnimationInProgress(false);
                    if (ArticleViewer.this.photoAnimationEndRunnable != null) {
                        ArticleViewer.this.photoAnimationEndRunnable.run();
                        ArticleViewer.this.photoAnimationEndRunnable = null;
                    }
                }
            }

            public void onAnimationEnd(Animator animation) {
                AndroidUtilities.runOnUIThread(new C20791());
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
                object.imageReceiver.setVisible(false, true);
            }
        };
        return true;
    }

    public void closePhoto(boolean animated) {
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
            final PlaceProviderObject object = getPlaceForPhoto(this.currentMedia);
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (animated) {
                float scale2;
                this.photoAnimationInProgress = 1;
                this.animatingImageView.setVisibility(0);
                this.photoContainerView.invalidate();
                animatorSet = new AnimatorSet();
                ViewGroup.LayoutParams layoutParams = this.animatingImageView.getLayoutParams();
                Rect drawRegion = null;
                int orientation = this.centerImage.getOrientation();
                int animatedOrientation = 0;
                if (!(object == null || object.imageReceiver == null)) {
                    animatedOrientation = object.imageReceiver.getAnimatedOrientation();
                }
                if (animatedOrientation != 0) {
                    orientation = animatedOrientation;
                }
                this.animatingImageView.setOrientation(orientation);
                if (object != null) {
                    this.animatingImageView.setNeedRadius(object.radius != 0);
                    drawRegion = object.imageReceiver.getDrawRegion();
                    layoutParams.width = drawRegion.right - drawRegion.left;
                    layoutParams.height = drawRegion.bottom - drawRegion.top;
                    this.animatingImageView.setImageBitmap(object.thumb);
                } else {
                    this.animatingImageView.setNeedRadius(false);
                    layoutParams.width = this.centerImage.getImageWidth();
                    layoutParams.height = this.centerImage.getImageHeight();
                    this.animatingImageView.setImageBitmap(this.centerImage.getBitmap());
                }
                this.animatingImageView.setLayoutParams(layoutParams);
                float scaleX = ((float) AndroidUtilities.displaySize.x) / ((float) layoutParams.width);
                float scaleY = ((float) (AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight)) / ((float) layoutParams.height);
                if (scaleX > scaleY) {
                    scale2 = scaleY;
                } else {
                    scale2 = scaleX;
                }
                float height = (((float) layoutParams.height) * this.scale) * scale2;
                float xPos = (((float) AndroidUtilities.displaySize.x) - ((((float) layoutParams.width) * this.scale) * scale2)) / 2.0f;
                if (VERSION.SDK_INT >= 21 && this.lastInsets != null) {
                    xPos += (float) ((WindowInsets) this.lastInsets).getSystemWindowInsetLeft();
                }
                float yPos = (((float) (AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight)) - height) / 2.0f;
                this.animatingImageView.setTranslationX(this.translationX + xPos);
                this.animatingImageView.setTranslationY(this.translationY + yPos);
                this.animatingImageView.setScaleX(this.scale * scale2);
                this.animatingImageView.setScaleY(this.scale * scale2);
                if (object != null) {
                    object.imageReceiver.setVisible(false, true);
                    int clipHorizontal = Math.abs(drawRegion.left - object.imageReceiver.getImageX());
                    int clipVertical = Math.abs(drawRegion.top - object.imageReceiver.getImageY());
                    int[] coords2 = new int[2];
                    object.parentView.getLocationInWindow(coords2);
                    int clipTop = (coords2[1] - (object.viewY + drawRegion.top)) + object.clipTopAddition;
                    if (clipTop < 0) {
                        clipTop = 0;
                    }
                    int clipBottom = (((object.viewY + drawRegion.top) + (drawRegion.bottom - drawRegion.top)) - (coords2[1] + object.parentView.getHeight())) + object.clipBottomAddition;
                    if (clipBottom < 0) {
                        clipBottom = 0;
                    }
                    clipTop = Math.max(clipTop, clipVertical);
                    clipBottom = Math.max(clipBottom, clipVertical);
                    this.animationValues[0][0] = this.animatingImageView.getScaleX();
                    this.animationValues[0][1] = this.animatingImageView.getScaleY();
                    this.animationValues[0][2] = this.animatingImageView.getTranslationX();
                    this.animationValues[0][3] = this.animatingImageView.getTranslationY();
                    this.animationValues[0][4] = 0.0f;
                    this.animationValues[0][5] = 0.0f;
                    this.animationValues[0][6] = 0.0f;
                    this.animationValues[0][7] = 0.0f;
                    this.animationValues[1][0] = object.scale;
                    this.animationValues[1][1] = object.scale;
                    this.animationValues[1][2] = ((float) object.viewX) + (((float) drawRegion.left) * object.scale);
                    this.animationValues[1][3] = ((float) object.viewY) + (((float) drawRegion.top) * object.scale);
                    this.animationValues[1][4] = ((float) clipHorizontal) * object.scale;
                    this.animationValues[1][5] = ((float) clipTop) * object.scale;
                    this.animationValues[1][6] = ((float) clipBottom) * object.scale;
                    this.animationValues[1][7] = (float) object.radius;
                    animatorArr = new Animator[5];
                    float[] fArr = new float[2];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.animatingImageView, "animationProgress", new float[]{0.0f, 1.0f});
                    animatorArr[1] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
                    animatorArr[2] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{0.0f});
                    animatorArr[3] = ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{0.0f});
                    animatorArr[4] = ObjectAnimator.ofFloat(this.captionTextView, "alpha", new float[]{0.0f});
                    animatorSet.playTogether(animatorArr);
                } else {
                    float f;
                    int h = AndroidUtilities.displaySize.y + AndroidUtilities.statusBarHeight;
                    r24 = new Animator[6];
                    r24[0] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
                    r24[1] = ObjectAnimator.ofFloat(this.animatingImageView, "alpha", new float[]{0.0f});
                    ClippingImageView clippingImageView = this.animatingImageView;
                    String str = "translationY";
                    float[] fArr2 = new float[1];
                    if (this.translationY >= 0.0f) {
                        f = (float) h;
                    } else {
                        f = (float) (-h);
                    }
                    fArr2[0] = f;
                    r24[2] = ObjectAnimator.ofFloat(clippingImageView, str, fArr2);
                    r24[3] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{0.0f});
                    r24[4] = ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{0.0f});
                    r24[5] = ObjectAnimator.ofFloat(this.captionTextView, "alpha", new float[]{0.0f});
                    animatorSet.playTogether(r24);
                }
                this.photoAnimationEndRunnable = new Runnable() {
                    public void run() {
                        if (VERSION.SDK_INT >= 18) {
                            ArticleViewer.this.photoContainerView.setLayerType(0, null);
                        }
                        ArticleViewer.this.photoContainerView.setVisibility(4);
                        ArticleViewer.this.photoContainerBackground.setVisibility(4);
                        ArticleViewer.this.photoAnimationInProgress = 0;
                        ArticleViewer.this.onPhotoClosed(object);
                    }
                };
                animatorSet.setDuration(200);
                animatorSet.addListener(new AnimatorListenerAdapter() {

                    /* renamed from: org.telegram.ui.ArticleViewer$48$1 */
                    class C20801 implements Runnable {
                        C20801() {
                        }

                        public void run() {
                            if (ArticleViewer.this.photoAnimationEndRunnable != null) {
                                ArticleViewer.this.photoAnimationEndRunnable.run();
                                ArticleViewer.this.photoAnimationEndRunnable = null;
                            }
                        }
                    }

                    public void onAnimationEnd(Animator animation) {
                        AndroidUtilities.runOnUIThread(new C20801());
                    }
                });
                this.photoTransitionAnimationStartTime = System.currentTimeMillis();
                if (VERSION.SDK_INT >= 18) {
                    this.photoContainerView.setLayerType(2, null);
                }
                animatorSet.start();
            } else {
                animatorSet = new AnimatorSet();
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.photoContainerView, "scaleX", new float[]{0.9f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.photoContainerView, "scaleY", new float[]{0.9f});
                animatorArr[2] = ObjectAnimator.ofInt(this.photoBackgroundDrawable, "alpha", new int[]{0});
                animatorArr[3] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{0.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{0.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.captionTextView, "alpha", new float[]{0.0f});
                animatorSet.playTogether(animatorArr);
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
                            ArticleViewer.this.onPhotoClosed(object);
                            ArticleViewer.this.photoContainerView.setScaleX(1.0f);
                            ArticleViewer.this.photoContainerView.setScaleY(1.0f);
                        }
                    }
                };
                animatorSet.setDuration(200);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
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
                animatorSet.start();
            }
            if (this.currentAnimation != null) {
                this.currentAnimation.setSecondParentView(null);
                this.currentAnimation = null;
                this.centerImage.setImageBitmap((Drawable) null);
            }
        }
    }

    private void onPhotoClosed(PlaceProviderObject object) {
        this.isPhotoVisible = false;
        this.disableShowCheck = true;
        this.currentMedia = null;
        this.currentThumb = null;
        if (this.currentAnimation != null) {
            this.currentAnimation.setSecondParentView(null);
            this.currentAnimation = null;
        }
        for (int a = 0; a < 3; a++) {
            if (this.radialProgressViews[a] != null) {
                this.radialProgressViews[a].setBackgroundState(-1, false);
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
        if (object != null) {
            object.imageReceiver.setVisible(true, true);
        }
    }

    public void onPause() {
        if (this.currentAnimation != null) {
            closePhoto(false);
        }
    }

    private void updateMinMax(float scale) {
        int maxW = ((int) ((((float) this.centerImage.getImageWidth()) * scale) - ((float) getContainerViewWidth()))) / 2;
        int maxH = ((int) ((((float) this.centerImage.getImageHeight()) * scale) - ((float) getContainerViewHeight()))) / 2;
        if (maxW > 0) {
            this.minX = (float) (-maxW);
            this.maxX = (float) maxW;
        } else {
            this.maxX = 0.0f;
            this.minX = 0.0f;
        }
        if (maxH > 0) {
            this.minY = (float) (-maxH);
            this.maxY = (float) maxH;
            return;
        }
        this.maxY = 0.0f;
        this.minY = 0.0f;
    }

    private int getContainerViewWidth() {
        return this.photoContainerView.getWidth();
    }

    private int getContainerViewHeight() {
        return this.photoContainerView.getHeight();
    }

    private boolean processTouchEvent(MotionEvent ev) {
        if (this.photoAnimationInProgress != 0 || this.animationStartTime != 0) {
            return false;
        }
        if (ev.getPointerCount() == 1 && this.gestureDetector.onTouchEvent(ev) && this.doubleTap) {
            this.doubleTap = false;
            this.moving = false;
            this.zooming = false;
            checkMinMax(false);
            return true;
        }
        if (ev.getActionMasked() == 0 || ev.getActionMasked() == 5) {
            this.discardTap = false;
            if (!this.scroller.isFinished()) {
                this.scroller.abortAnimation();
            }
            if (!(this.draggingDown || this.changingPage)) {
                if (this.canZoom && ev.getPointerCount() == 2) {
                    this.pinchStartDistance = (float) Math.hypot((double) (ev.getX(1) - ev.getX(0)), (double) (ev.getY(1) - ev.getY(0)));
                    this.pinchStartScale = this.scale;
                    this.pinchCenterX = (ev.getX(0) + ev.getX(1)) / 2.0f;
                    this.pinchCenterY = (ev.getY(0) + ev.getY(1)) / 2.0f;
                    this.pinchStartX = this.translationX;
                    this.pinchStartY = this.translationY;
                    this.zooming = true;
                    this.moving = false;
                    if (this.velocityTracker != null) {
                        this.velocityTracker.clear();
                    }
                } else if (ev.getPointerCount() == 1) {
                    this.moveStartX = ev.getX();
                    float y = ev.getY();
                    this.moveStartY = y;
                    this.dragY = y;
                    this.draggingDown = false;
                    this.canDragDown = true;
                    if (this.velocityTracker != null) {
                        this.velocityTracker.clear();
                    }
                }
            }
        } else if (ev.getActionMasked() == 2) {
            if (this.canZoom && ev.getPointerCount() == 2 && !this.draggingDown && this.zooming && !this.changingPage) {
                this.discardTap = true;
                this.scale = (((float) Math.hypot((double) (ev.getX(1) - ev.getX(0)), (double) (ev.getY(1) - ev.getY(0)))) / this.pinchStartDistance) * this.pinchStartScale;
                this.translationX = (this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - (((this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - this.pinchStartX) * (this.scale / this.pinchStartScale));
                this.translationY = (this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - (((this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - this.pinchStartY) * (this.scale / this.pinchStartScale));
                updateMinMax(this.scale);
                this.photoContainerView.invalidate();
            } else if (ev.getPointerCount() == 1) {
                if (this.velocityTracker != null) {
                    this.velocityTracker.addMovement(ev);
                }
                float dx = Math.abs(ev.getX() - this.moveStartX);
                float dy = Math.abs(ev.getY() - this.dragY);
                if (dx > ((float) AndroidUtilities.dp(3.0f)) || dy > ((float) AndroidUtilities.dp(3.0f))) {
                    this.discardTap = true;
                }
                if (this.canDragDown && !this.draggingDown && this.scale == 1.0f && dy >= ((float) AndroidUtilities.dp(30.0f)) && dy / 2.0f > dx) {
                    this.draggingDown = true;
                    this.moving = false;
                    this.dragY = ev.getY();
                    if (this.isActionBarVisible) {
                        toggleActionBar(false, true);
                    }
                    return true;
                } else if (this.draggingDown) {
                    this.translationY = ev.getY() - this.dragY;
                    this.photoContainerView.invalidate();
                } else if (this.invalidCoords || this.animationStartTime != 0) {
                    this.invalidCoords = false;
                    this.moveStartX = ev.getX();
                    this.moveStartY = ev.getY();
                } else {
                    float moveDx = this.moveStartX - ev.getX();
                    float moveDy = this.moveStartY - ev.getY();
                    if (this.moving || ((this.scale == 1.0f && Math.abs(moveDy) + ((float) AndroidUtilities.dp(12.0f)) < Math.abs(moveDx)) || this.scale != 1.0f)) {
                        if (!this.moving) {
                            moveDx = 0.0f;
                            moveDy = 0.0f;
                            this.moving = true;
                            this.canDragDown = false;
                        }
                        this.moveStartX = ev.getX();
                        this.moveStartY = ev.getY();
                        updateMinMax(this.scale);
                        if ((this.translationX < this.minX && !this.rightImage.hasImage()) || (this.translationX > this.maxX && !this.leftImage.hasImage())) {
                            moveDx /= 3.0f;
                        }
                        if (this.maxY == 0.0f && this.minY == 0.0f) {
                            if (this.translationY - moveDy < this.minY) {
                                this.translationY = this.minY;
                                moveDy = 0.0f;
                            } else if (this.translationY - moveDy > this.maxY) {
                                this.translationY = this.maxY;
                                moveDy = 0.0f;
                            }
                        } else if (this.translationY < this.minY || this.translationY > this.maxY) {
                            moveDy /= 3.0f;
                        }
                        this.translationX -= moveDx;
                        if (this.scale != 1.0f) {
                            this.translationY -= moveDy;
                        }
                        this.photoContainerView.invalidate();
                    }
                }
            }
        } else if (ev.getActionMasked() == 3 || ev.getActionMasked() == 1 || ev.getActionMasked() == 6) {
            if (this.zooming) {
                this.invalidCoords = true;
                if (this.scale < 1.0f) {
                    updateMinMax(1.0f);
                    animateTo(1.0f, 0.0f, 0.0f, true);
                } else if (this.scale > 3.0f) {
                    float atx = (this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - (((this.pinchCenterX - ((float) (getContainerViewWidth() / 2))) - this.pinchStartX) * (3.0f / this.pinchStartScale));
                    float aty = (this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - (((this.pinchCenterY - ((float) (getContainerViewHeight() / 2))) - this.pinchStartY) * (3.0f / this.pinchStartScale));
                    updateMinMax(3.0f);
                    if (atx < this.minX) {
                        atx = this.minX;
                    } else if (atx > this.maxX) {
                        atx = this.maxX;
                    }
                    if (aty < this.minY) {
                        aty = this.minY;
                    } else if (aty > this.maxY) {
                        aty = this.maxY;
                    }
                    animateTo(3.0f, atx, aty, true);
                } else {
                    checkMinMax(true);
                }
                this.zooming = false;
            } else if (this.draggingDown) {
                if (Math.abs(this.dragY - ev.getY()) > ((float) getContainerViewHeight()) / 6.0f) {
                    closePhoto(true);
                } else {
                    animateTo(1.0f, 0.0f, 0.0f, false);
                }
                this.draggingDown = false;
            } else if (this.moving) {
                float moveToX = this.translationX;
                float moveToY = this.translationY;
                updateMinMax(this.scale);
                this.moving = false;
                this.canDragDown = true;
                float velocity = 0.0f;
                if (this.velocityTracker != null && this.scale == 1.0f) {
                    this.velocityTracker.computeCurrentVelocity(1000);
                    velocity = this.velocityTracker.getXVelocity();
                }
                if ((this.translationX < this.minX - ((float) (getContainerViewWidth() / 3)) || velocity < ((float) (-AndroidUtilities.dp(650.0f)))) && this.rightImage.hasImage()) {
                    goToNext();
                    return true;
                } else if ((this.translationX > this.maxX + ((float) (getContainerViewWidth() / 3)) || velocity > ((float) AndroidUtilities.dp(650.0f))) && this.leftImage.hasImage()) {
                    goToPrev();
                    return true;
                } else {
                    if (this.translationX < this.minX) {
                        moveToX = this.minX;
                    } else if (this.translationX > this.maxX) {
                        moveToX = this.maxX;
                    }
                    if (this.translationY < this.minY) {
                        moveToY = this.minY;
                    } else if (this.translationY > this.maxY) {
                        moveToY = this.maxY;
                    }
                    animateTo(this.scale, moveToX, moveToY, false);
                }
            }
        }
        return false;
    }

    private void checkMinMax(boolean zoom) {
        float moveToX = this.translationX;
        float moveToY = this.translationY;
        updateMinMax(this.scale);
        if (this.translationX < this.minX) {
            moveToX = this.minX;
        } else if (this.translationX > this.maxX) {
            moveToX = this.maxX;
        }
        if (this.translationY < this.minY) {
            moveToY = this.minY;
        } else if (this.translationY > this.maxY) {
            moveToY = this.maxY;
        }
        animateTo(this.scale, moveToX, moveToY, zoom);
    }

    private void goToNext() {
        float extra = 0.0f;
        if (this.scale != 1.0f) {
            extra = ((float) ((getContainerViewWidth() - this.centerImage.getImageWidth()) / 2)) * this.scale;
        }
        this.switchImageAfterAnimation = 1;
        animateTo(this.scale, ((this.minX - ((float) getContainerViewWidth())) - extra) - ((float) (AndroidUtilities.dp(30.0f) / 2)), this.translationY, false);
    }

    private void goToPrev() {
        float extra = 0.0f;
        if (this.scale != 1.0f) {
            extra = ((float) ((getContainerViewWidth() - this.centerImage.getImageWidth()) / 2)) * this.scale;
        }
        this.switchImageAfterAnimation = 2;
        animateTo(this.scale, ((this.maxX + ((float) getContainerViewWidth())) + extra) + ((float) (AndroidUtilities.dp(30.0f) / 2)), this.translationY, false);
    }

    private void animateTo(float newScale, float newTx, float newTy, boolean isZoom) {
        animateTo(newScale, newTx, newTy, isZoom, 250);
    }

    private void animateTo(float newScale, float newTx, float newTy, boolean isZoom, int duration) {
        if (this.scale != newScale || this.translationX != newTx || this.translationY != newTy) {
            this.zoomAnimation = isZoom;
            this.animateToScale = newScale;
            this.animateToX = newTx;
            this.animateToY = newTy;
            this.animationStartTime = System.currentTimeMillis();
            this.imageMoveAnimation = new AnimatorSet();
            this.imageMoveAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this, "animationValue", new float[]{0.0f, 1.0f})});
            this.imageMoveAnimation.setInterpolator(this.interpolator);
            this.imageMoveAnimation.setDuration((long) duration);
            this.imageMoveAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    ArticleViewer.this.imageMoveAnimation = null;
                    ArticleViewer.this.photoContainerView.invalidate();
                }
            });
            this.imageMoveAnimation.start();
        }
    }

    public void setAnimationValue(float value) {
        this.animationValue = value;
        this.photoContainerView.invalidate();
    }

    public float getAnimationValue() {
        return this.animationValue;
    }

    private void drawContent(Canvas canvas) {
        if (this.photoAnimationInProgress == 1) {
            return;
        }
        if (this.isPhotoVisible || this.photoAnimationInProgress == 2) {
            float currentScale;
            float currentTranslationY;
            float currentTranslationX;
            float scaleDiff;
            float alpha;
            int bitmapWidth;
            int bitmapHeight;
            float scaleX;
            float scaleY;
            float scale;
            int width;
            int height;
            float aty = -1.0f;
            if (this.imageMoveAnimation != null) {
                if (!this.scroller.isFinished()) {
                    this.scroller.abortAnimation();
                }
                float ts = this.scale + ((this.animateToScale - this.scale) * this.animationValue);
                float tx = this.translationX + ((this.animateToX - this.translationX) * this.animationValue);
                float ty = this.translationY + ((this.animateToY - this.translationY) * this.animationValue);
                if (this.animateToScale == 1.0f && this.scale == 1.0f && this.translationX == 0.0f) {
                    aty = ty;
                }
                currentScale = ts;
                currentTranslationY = ty;
                currentTranslationX = tx;
                this.photoContainerView.invalidate();
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
                currentScale = this.scale;
                currentTranslationY = this.translationY;
                currentTranslationX = this.translationX;
                if (!this.moving) {
                    aty = this.translationY;
                }
            }
            if (this.scale != 1.0f || aty == -1.0f || this.zoomAnimation) {
                this.photoBackgroundDrawable.setAlpha(255);
            } else {
                float maxValue = ((float) getContainerViewHeight()) / 4.0f;
                this.photoBackgroundDrawable.setAlpha((int) Math.max(127.0f, 255.0f * (1.0f - (Math.min(Math.abs(aty), maxValue) / maxValue))));
            }
            ImageReceiver sideImage = null;
            if (!(this.scale < 1.0f || this.zoomAnimation || this.zooming)) {
                if (currentTranslationX > this.maxX + ((float) AndroidUtilities.dp(5.0f))) {
                    sideImage = this.leftImage;
                } else if (currentTranslationX < this.minX - ((float) AndroidUtilities.dp(5.0f))) {
                    sideImage = this.rightImage;
                }
            }
            this.changingPage = sideImage != null;
            if (sideImage == this.rightImage) {
                float tranlateX = currentTranslationX;
                scaleDiff = 0.0f;
                alpha = 1.0f;
                if (!this.zoomAnimation && tranlateX < this.minX) {
                    alpha = Math.min(1.0f, (this.minX - tranlateX) / ((float) canvas.getWidth()));
                    scaleDiff = (1.0f - alpha) * 0.3f;
                    tranlateX = (float) ((-canvas.getWidth()) - (AndroidUtilities.dp(30.0f) / 2));
                }
                if (sideImage.hasBitmapImage()) {
                    canvas.save();
                    canvas.translate((float) (getContainerViewWidth() / 2), (float) (getContainerViewHeight() / 2));
                    canvas.translate(((float) (canvas.getWidth() + (AndroidUtilities.dp(30.0f) / 2))) + tranlateX, 0.0f);
                    canvas.scale(1.0f - scaleDiff, 1.0f - scaleDiff);
                    bitmapWidth = sideImage.getBitmapWidth();
                    bitmapHeight = sideImage.getBitmapHeight();
                    scaleX = ((float) getContainerViewWidth()) / ((float) bitmapWidth);
                    scaleY = ((float) getContainerViewHeight()) / ((float) bitmapHeight);
                    if (scaleX > scaleY) {
                        scale = scaleY;
                    } else {
                        scale = scaleX;
                    }
                    width = (int) (((float) bitmapWidth) * scale);
                    height = (int) (((float) bitmapHeight) * scale);
                    sideImage.setAlpha(alpha);
                    sideImage.setImageCoords((-width) / 2, (-height) / 2, width, height);
                    sideImage.draw(canvas);
                    canvas.restore();
                }
                canvas.save();
                canvas.translate(tranlateX, currentTranslationY / currentScale);
                canvas.translate(((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f))) / 2.0f, (-currentTranslationY) / currentScale);
                this.radialProgressViews[1].setScale(1.0f - scaleDiff);
                this.radialProgressViews[1].setAlpha(alpha);
                this.radialProgressViews[1].onDraw(canvas);
                canvas.restore();
            }
            float translateX = currentTranslationX;
            scaleDiff = 0.0f;
            alpha = 1.0f;
            if (!this.zoomAnimation && translateX > this.maxX) {
                alpha = Math.min(1.0f, (translateX - this.maxX) / ((float) canvas.getWidth()));
                scaleDiff = alpha * 0.3f;
                alpha = 1.0f - alpha;
                translateX = this.maxX;
            }
            boolean drawTextureView = this.aspectRatioFrameLayout != null && this.aspectRatioFrameLayout.getVisibility() == 0;
            if (this.centerImage.hasBitmapImage()) {
                canvas.save();
                canvas.translate((float) (getContainerViewWidth() / 2), (float) (getContainerViewHeight() / 2));
                canvas.translate(translateX, currentTranslationY);
                canvas.scale(currentScale - scaleDiff, currentScale - scaleDiff);
                bitmapWidth = this.centerImage.getBitmapWidth();
                bitmapHeight = this.centerImage.getBitmapHeight();
                if (drawTextureView && this.textureUploaded && Math.abs((((float) bitmapWidth) / ((float) bitmapHeight)) - (((float) this.videoTextureView.getMeasuredWidth()) / ((float) this.videoTextureView.getMeasuredHeight()))) > 0.01f) {
                    bitmapWidth = this.videoTextureView.getMeasuredWidth();
                    bitmapHeight = this.videoTextureView.getMeasuredHeight();
                }
                scaleX = ((float) getContainerViewWidth()) / ((float) bitmapWidth);
                scaleY = ((float) getContainerViewHeight()) / ((float) bitmapHeight);
                if (scaleX > scaleY) {
                    scale = scaleY;
                } else {
                    scale = scaleX;
                }
                width = (int) (((float) bitmapWidth) * scale);
                height = (int) (((float) bitmapHeight) * scale);
                if (!(drawTextureView && this.textureUploaded && this.videoCrossfadeStarted && this.videoCrossfadeAlpha == 1.0f)) {
                    this.centerImage.setAlpha(alpha);
                    this.centerImage.setImageCoords((-width) / 2, (-height) / 2, width, height);
                    this.centerImage.draw(canvas);
                }
                if (drawTextureView) {
                    if (!this.videoCrossfadeStarted && this.textureUploaded) {
                        this.videoCrossfadeStarted = true;
                        this.videoCrossfadeAlpha = 0.0f;
                        this.videoCrossfadeAlphaLastTime = System.currentTimeMillis();
                    }
                    canvas.translate((float) ((-width) / 2), (float) ((-height) / 2));
                    this.videoTextureView.setAlpha(this.videoCrossfadeAlpha * alpha);
                    this.aspectRatioFrameLayout.draw(canvas);
                    if (this.videoCrossfadeStarted && this.videoCrossfadeAlpha < 1.0f) {
                        long newUpdateTime = System.currentTimeMillis();
                        long dt = newUpdateTime - this.videoCrossfadeAlphaLastTime;
                        this.videoCrossfadeAlphaLastTime = newUpdateTime;
                        this.videoCrossfadeAlpha += ((float) dt) / 300.0f;
                        this.photoContainerView.invalidate();
                        if (this.videoCrossfadeAlpha > 1.0f) {
                            this.videoCrossfadeAlpha = 1.0f;
                        }
                    }
                }
                canvas.restore();
            }
            if (!(drawTextureView || this.bottomLayout.getVisibility() == 0)) {
                canvas.save();
                canvas.translate(translateX, currentTranslationY / currentScale);
                this.radialProgressViews[0].setScale(1.0f - scaleDiff);
                this.radialProgressViews[0].setAlpha(alpha);
                this.radialProgressViews[0].onDraw(canvas);
                canvas.restore();
            }
            if (sideImage == this.leftImage) {
                if (sideImage.hasBitmapImage()) {
                    canvas.save();
                    canvas.translate((float) (getContainerViewWidth() / 2), (float) (getContainerViewHeight() / 2));
                    canvas.translate(((-((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f)))) / 2.0f) + currentTranslationX, 0.0f);
                    bitmapWidth = sideImage.getBitmapWidth();
                    bitmapHeight = sideImage.getBitmapHeight();
                    scaleX = ((float) getContainerViewWidth()) / ((float) bitmapWidth);
                    scaleY = ((float) getContainerViewHeight()) / ((float) bitmapHeight);
                    scale = scaleX > scaleY ? scaleY : scaleX;
                    width = (int) (((float) bitmapWidth) * scale);
                    height = (int) (((float) bitmapHeight) * scale);
                    sideImage.setAlpha(1.0f);
                    sideImage.setImageCoords((-width) / 2, (-height) / 2, width, height);
                    sideImage.draw(canvas);
                    canvas.restore();
                }
                canvas.save();
                canvas.translate(currentTranslationX, currentTranslationY / currentScale);
                canvas.translate((-((((float) canvas.getWidth()) * (this.scale + 1.0f)) + ((float) AndroidUtilities.dp(30.0f)))) / 2.0f, (-currentTranslationY) / currentScale);
                this.radialProgressViews[2].setScale(1.0f);
                this.radialProgressViews[2].setAlpha(1.0f);
                this.radialProgressViews[2].onDraw(canvas);
                canvas.restore();
            }
        }
    }

    private void onActionClick(boolean download) {
        TLObject media = getMedia(this.currentIndex);
        if ((media instanceof TLRPC$Document) && this.currentFileNames[0] != null) {
            TLRPC$Document document = (TLRPC$Document) media;
            File file = null;
            if (this.currentMedia != null) {
                file = getMediaFile(this.currentIndex);
                if (!(file == null || file.exists())) {
                    file = null;
                }
            }
            if (file != null) {
                preparePlayer(file, true);
            } else if (!download) {
            } else {
                if (FileLoader.getInstance().isLoadingFile(this.currentFileNames[0])) {
                    FileLoader.getInstance().cancelLoadFile(document);
                } else {
                    FileLoader.getInstance().loadFile(document, true, 1);
                }
            }
        }
    }

    public boolean onDown(MotionEvent e) {
        return false;
    }

    public void onShowPress(MotionEvent e) {
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    public void onLongPress(MotionEvent e) {
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (this.scale != 1.0f) {
            this.scroller.abortAnimation();
            this.scroller.fling(Math.round(this.translationX), Math.round(this.translationY), Math.round(velocityX), Math.round(velocityY), (int) this.minX, (int) this.maxX, (int) this.minY, (int) this.maxY);
            this.photoContainerView.postInvalidate();
        }
        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        boolean z = false;
        if (this.discardTap) {
            return false;
        }
        boolean drawTextureView;
        if (this.aspectRatioFrameLayout == null || this.aspectRatioFrameLayout.getVisibility() != 0) {
            drawTextureView = false;
        } else {
            drawTextureView = true;
        }
        if (!(this.radialProgressViews[0] == null || this.photoContainerView == null || drawTextureView)) {
            int state = this.radialProgressViews[0].backgroundState;
            if (state > 0 && state <= 3) {
                float x = e.getX();
                float y = e.getY();
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

    public boolean onDoubleTap(MotionEvent e) {
        if (!this.canZoom || (this.scale == 1.0f && (this.translationY != 0.0f || this.translationX != 0.0f))) {
            return false;
        }
        if (this.animationStartTime != 0 || this.photoAnimationInProgress != 0) {
            return false;
        }
        if (this.scale == 1.0f) {
            float atx = (e.getX() - ((float) (getContainerViewWidth() / 2))) - (((e.getX() - ((float) (getContainerViewWidth() / 2))) - this.translationX) * (3.0f / this.scale));
            float aty = (e.getY() - ((float) (getContainerViewHeight() / 2))) - (((e.getY() - ((float) (getContainerViewHeight() / 2))) - this.translationY) * (3.0f / this.scale));
            updateMinMax(3.0f);
            if (atx < this.minX) {
                atx = this.minX;
            } else if (atx > this.maxX) {
                atx = this.maxX;
            }
            if (aty < this.minY) {
                aty = this.minY;
            } else if (aty > this.maxY) {
                aty = this.maxY;
            }
            animateTo(3.0f, atx, aty, true);
        } else {
            animateTo(1.0f, 0.0f, 0.0f, true);
        }
        this.doubleTap = true;
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    private ImageReceiver getImageReceiverFromListView(ViewGroup listView, TLRPC$PageBlock pageBlock, int[] coords) {
        int count = listView.getChildCount();
        for (int a = 0; a < count; a++) {
            View view = listView.getChildAt(a);
            if (view instanceof BlockPhotoCell) {
                BlockPhotoCell cell = (BlockPhotoCell) view;
                if (cell.currentBlock == pageBlock) {
                    view.getLocationInWindow(coords);
                    return cell.imageView;
                }
            } else if (view instanceof BlockVideoCell) {
                BlockVideoCell cell2 = (BlockVideoCell) view;
                if (cell2.currentBlock == pageBlock) {
                    view.getLocationInWindow(coords);
                    return cell2.imageView;
                }
            } else if (view instanceof BlockCollageCell) {
                imageReceiver = getImageReceiverFromListView(((BlockCollageCell) view).innerListView, pageBlock, coords);
                if (imageReceiver != null) {
                    return imageReceiver;
                }
            } else if (view instanceof BlockSlideshowCell) {
                imageReceiver = getImageReceiverFromListView(((BlockSlideshowCell) view).innerListView, pageBlock, coords);
                if (imageReceiver != null) {
                    return imageReceiver;
                }
            } else {
                continue;
            }
        }
        return null;
    }

    private PlaceProviderObject getPlaceForPhoto(TLRPC$PageBlock pageBlock) {
        ImageReceiver imageReceiver = getImageReceiverFromListView(this.listView, pageBlock, this.coords);
        if (imageReceiver == null) {
            return null;
        }
        PlaceProviderObject object = new PlaceProviderObject();
        object.viewX = this.coords[0];
        object.viewY = this.coords[1];
        object.parentView = this.listView;
        object.imageReceiver = imageReceiver;
        object.thumb = imageReceiver.getBitmap();
        object.radius = imageReceiver.getRoundRadius();
        object.clipTopAddition = this.currentHeaderHeight;
        return object;
    }
}
