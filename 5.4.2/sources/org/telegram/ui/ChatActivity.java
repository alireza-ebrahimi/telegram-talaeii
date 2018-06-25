package org.telegram.ui;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.Contacts;
import android.support.v4.content.FileProvider;
import android.support.v7.app.C0767b.C0766a;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.persianswitch.sdk.api.Response;
import com.persianswitch.sdk.api.Response.General;
import com.persianswitch.sdk.api.Response.Payment;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.C2601j;
import org.telegram.customization.Activities.C2624m;
import org.telegram.customization.Activities.PaymentConfirmActivity;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.DialogStatus;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.Model.FilterResponse;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2757a;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p156a.C2663k;
import org.telegram.customization.p156a.C2663k.C2661a;
import org.telegram.customization.p159b.C2666a;
import org.telegram.customization.service.DownloadManagerService;
import org.telegram.customization.util.C2879f;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.EmojiSuggestion;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.PhotoEntry;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessageObject.GroupedMessagePosition;
import org.telegram.messenger.MessageObject.GroupedMessages;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.MessagesStorage.IntCallback;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.SecretChatHelper;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SendMessagesHelper.SendingMediaInfo;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.query.BotQuery;
import org.telegram.messenger.query.DraftQuery;
import org.telegram.messenger.query.MessagesQuery;
import org.telegram.messenger.query.MessagesSearchQuery;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.GridLayoutManager.SpanSizeLookup;
import org.telegram.messenger.support.widget.GridLayoutManagerFixed;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.LinearSmoothScrollerMiddle;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.SmoothScroller;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.SimpleCallback;
import org.telegram.p149a.C2488b;
import org.telegram.p150b.C2491a;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_documentEmpty;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC$TL_encryptedChatDiscarded;
import org.telegram.tgnet.TLRPC$TL_encryptedChatRequested;
import org.telegram.tgnet.TLRPC$TL_encryptedChatWaiting;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
import org.telegram.tgnet.TLRPC$TL_game;
import org.telegram.tgnet.TLRPC$TL_inlineBotSwitchPM;
import org.telegram.tgnet.TLRPC$TL_inputMessageEntityMentionName;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetShortName;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonBuy;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonCallback;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonGame;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonRow;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonSwitchInline;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonUrl;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser;
import org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser;
import org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo;
import org.telegram.tgnet.TLRPC$TL_messageActionEmpty;
import org.telegram.tgnet.TLRPC$TL_messageActionGameScore;
import org.telegram.tgnet.TLRPC$TL_messageActionPaymentSent;
import org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall;
import org.telegram.tgnet.TLRPC$TL_messageActionPinMessage;
import org.telegram.tgnet.TLRPC$TL_messageEncryptedAction;
import org.telegram.tgnet.TLRPC$TL_messageEntityBold;
import org.telegram.tgnet.TLRPC$TL_messageEntityCode;
import org.telegram.tgnet.TLRPC$TL_messageEntityItalic;
import org.telegram.tgnet.TLRPC$TL_messageEntityMentionName;
import org.telegram.tgnet.TLRPC$TL_messageEntityPre;
import org.telegram.tgnet.TLRPC$TL_messageMediaGame;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messages_deleteChatUser;
import org.telegram.tgnet.TLRPC$TL_messages_getMessageEditData;
import org.telegram.tgnet.TLRPC$TL_messages_getUnreadMentions;
import org.telegram.tgnet.TLRPC$TL_messages_getWebPagePreview;
import org.telegram.tgnet.TLRPC$TL_messages_readMentions;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonBusy;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonMissed;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
import org.telegram.tgnet.TLRPC$TL_replyInlineMarkup;
import org.telegram.tgnet.TLRPC$TL_replyKeyboardForceReply;
import org.telegram.tgnet.TLRPC$TL_user;
import org.telegram.tgnet.TLRPC$TL_userFull;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.tgnet.TLRPC$TL_webPageEmpty;
import org.telegram.tgnet.TLRPC$TL_webPagePending;
import org.telegram.tgnet.TLRPC$TL_webPageUrlPending;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.BotInfo;
import org.telegram.tgnet.TLRPC.BotInlineResult;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ChatParticipant;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.DraftMessage;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.KeyboardButton;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.TL_botCommand;
import org.telegram.tgnet.TLRPC.TL_channelForbidden;
import org.telegram.tgnet.TLRPC.TL_channelFull;
import org.telegram.tgnet.TLRPC.TL_channelParticipantAdmin;
import org.telegram.tgnet.TLRPC.TL_channelParticipantCreator;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipant;
import org.telegram.tgnet.TLRPC.TL_channels_getParticipant;
import org.telegram.tgnet.TLRPC.TL_channels_reportSpam;
import org.telegram.tgnet.TLRPC.TL_chatForbidden;
import org.telegram.tgnet.TLRPC.TL_chatFull;
import org.telegram.tgnet.TLRPC.TL_chatParticipantsForbidden;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarLayout;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet.Builder;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Adapters.MentionsAdapter;
import org.telegram.ui.Adapters.MentionsAdapter.MentionsAdapterDelegate;
import org.telegram.ui.Adapters.StickersAdapter;
import org.telegram.ui.Adapters.StickersAdapter.StickersAdapterDelegate;
import org.telegram.ui.AudioSelectActivity.AudioSelectActivityDelegate;
import org.telegram.ui.Cells.BotHelpCell;
import org.telegram.ui.Cells.BotHelpCell.BotHelpCellDelegate;
import org.telegram.ui.Cells.BotSwitchCell;
import org.telegram.ui.Cells.ChatActionCell;
import org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate;
import org.telegram.ui.Cells.ChatLoadingCell;
import org.telegram.ui.Cells.ChatMessageCell;
import org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate;
import org.telegram.ui.Cells.ChatUnreadCell;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.Cells.ContextLinkCell;
import org.telegram.ui.Cells.MentionCell;
import org.telegram.ui.Cells.StickerCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.ChatActivityEnterView;
import org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate;
import org.telegram.ui.Components.ChatAttachAlert;
import org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate;
import org.telegram.ui.Components.ChatAvatarContainer;
import org.telegram.ui.Components.ChatBigEmptyView;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.CorrectlyMeasuringTextView;
import org.telegram.ui.Components.EmbedBottomSheet;
import org.telegram.ui.Components.EmojiView;
import org.telegram.ui.Components.ExtendedGridLayoutManager;
import org.telegram.ui.Components.FragmentContextView;
import org.telegram.ui.Components.InstantCameraView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.NumberTextView;
import org.telegram.ui.Components.PipRoundVideoView;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.ShareAlert;
import org.telegram.ui.Components.Size;
import org.telegram.ui.Components.SizeNotifierFrameLayout;
import org.telegram.ui.Components.TypefaceSpan;
import org.telegram.ui.Components.URLSpanBotCommand;
import org.telegram.ui.Components.URLSpanMono;
import org.telegram.ui.Components.URLSpanNoUnderline;
import org.telegram.ui.Components.URLSpanReplacement;
import org.telegram.ui.Components.URLSpanUserMention;
import org.telegram.ui.Components.voip.VoIPHelper;
import org.telegram.ui.ContactsActivity.ContactsActivityDelegate;
import org.telegram.ui.DialogsActivity.DialogsActivityDelegate;
import org.telegram.ui.DocumentSelectActivity.DocumentSelectActivityDelegate;
import org.telegram.ui.LocationActivity.LocationActivityDelegate;
import org.telegram.ui.PhotoAlbumPickerActivity.PhotoAlbumPickerActivityDelegate;
import org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PlaceProviderObject;
import utils.C3792d;
import utils.p178a.C3791b;
import utils.view.ToastUtil;

public class ChatActivity extends BaseFragment implements NotificationCenterDelegate, DialogsActivityDelegate, LocationActivityDelegate {
    private static boolean QuoteForward = false;
    private static final int add_fave_dialog = 300;
    private static final int add_member = 29;
    private static final int add_shortcut = 24;
    private static final int attach_audio = 3;
    private static final int attach_contact = 5;
    private static final int attach_document = 4;
    private static final int attach_gallery = 1;
    private static final int attach_location = 6;
    private static final int attach_photo = 0;
    private static final int attach_video = 2;
    private static final int bot_help = 30;
    private static final int bot_settings = 31;
    private static final int call = 32;
    private static final int chat_background = 99;
    private static final int chat_enc_timer = 13;
    private static final int chat_menu_attach = 14;
    private static final int clear_history = 15;
    private static final int copy = 10;
    private static final int delete = 12;
    private static final int delete_chat = 16;
    private static final int edit = 23;
    private static final int forward = 11;
    private static final int go_to_first = 200;
    private static final int id_chat_compose_panel = 1000;
    private static final int leave_group = 28;
    private static final int mute = 18;
    private static final int quoteforward = 111;
    private static final int reply = 19;
    private static final int report = 21;
    public static final int scheduleDownloads = 400;
    private static final int search = 40;
    private static final int share_contact = 17;
    private static final int star = 22;
    public static final int startAllServices = 500;
    private SimpleTextView actionModeSubTextView;
    private SimpleTextView actionModeTextView;
    private FrameLayout actionModeTitleContainer;
    private ArrayList<View> actionModeViews = new ArrayList();
    private TextView addContactItem;
    private TextView addToContactsButton;
    private TextView alertNameTextView;
    private TextView alertTextView;
    private FrameLayout alertView;
    private AnimatorSet alertViewAnimator;
    private boolean allowContextBotPanel;
    private boolean allowContextBotPanelSecond = true;
    private boolean allowStickersPanel;
    private ArrayList<MessageObject> animatingMessageObjects = new ArrayList();
    private Paint aspectPaint;
    private Path aspectPath;
    private AspectRatioFrameLayout aspectRatioFrameLayout;
    private ActionBarMenuItem attachItem;
    private ChatAvatarContainer avatarContainer;
    private ChatBigEmptyView bigEmptyView;
    private MessageObject botButtons;
    private PhotoViewerProvider botContextProvider = new C42433();
    private ArrayList<Object> botContextResults;
    private HashMap<Integer, BotInfo> botInfo = new HashMap();
    private MessageObject botReplyButtons;
    private String botUser;
    private int botsCount;
    private FrameLayout bottomOverlay;
    private FrameLayout bottomOverlayChat;
    private TextView bottomOverlayChatText;
    private TextView bottomOverlayText;
    private boolean[] cacheEndReached = new boolean[2];
    private int canEditMessagesCount;
    private int cantDeleteMessagesCount;
    boolean channelIsFilter = false;
    protected ChatActivityEnterView chatActivityEnterView;
    private ChatActivityAdapter chatAdapter;
    private ChatAttachAlert chatAttachAlert;
    private long chatEnterTime;
    private GridLayoutManagerFixed chatLayoutManager;
    private long chatLeaveTime;
    private RecyclerListView chatListView;
    private boolean chatListViewIgnoreLayout;
    private ArrayList<ChatMessageCell> chatMessageCellsCache = new ArrayList();
    private int chat_id;
    private boolean checkTextureViewPosition;
    private Dialog closeChatDialog;
    private ImageView closePinned;
    private ImageView closeReportSpam;
    private SizeNotifierFrameLayout contentView;
    private int createUnreadMessageAfterId;
    private boolean createUnreadMessageAfterIdLoading;
    protected Chat currentChat;
    protected EncryptedChat currentEncryptedChat;
    private boolean currentFloatingDateOnScreen;
    private boolean currentFloatingTopIsNotMessage;
    private String currentPicturePath;
    protected User currentUser;
    ProgressDialog dialogLoading;
    private long dialog_id;
    private boolean directShareToMenu;
    private ProgressBar downloadManagerLoading;
    private int editingMessageObjectReqId;
    private View emojiButtonRed;
    private TextView emptyView;
    private FrameLayout emptyViewContainer;
    private boolean[] endReached = new boolean[2];
    private boolean first = true;
    private boolean firstLoading = true;
    private int first_unread_id;
    private AnimatorSet floatingDateAnimation;
    private ChatActionCell floatingDateView;
    private boolean forceScrollToTop;
    private boolean[] forwardEndReached = new boolean[]{true, true};
    private MessageObject forwardingMessage;
    private GroupedMessages forwardingMessageGroup;
    private ArrayList<MessageObject> forwardingMessages;
    private ArrayList<CharSequence> foundUrls;
    private TLRPC$WebPage foundWebPage;
    private FragmentContextView fragmentContextView;
    private FragmentContextView fragmentLocationContextView;
    private TextView gifHintTextView;
    private boolean goToFirstMsg = false;
    private HashMap<Long, GroupedMessages> groupedMessagesMap = new HashMap();
    private boolean hasAllMentionsLocal;
    private boolean hasBotsCommands;
    private boolean hasUnfavedSelected;
    private ActionBarMenuItem headerItem;
    private Runnable hideAlertViewRunnable;
    private int highlightMessageId = Integer.MAX_VALUE;
    private boolean ignoreAttachOnPause;
    protected ChatFull info;
    private long inlineReturn;
    private InstantCameraView instantCameraView;
    boolean inviteBoxAdded = false;
    View inviteRootView;
    boolean isAdvancedForward = false;
    private boolean isBroadcast;
    private boolean isDownloadManager = false;
    boolean isShowingQuickAccess = false;
    private int lastLoadIndex;
    private int last_message_id = 0;
    private int linkSearchRequestId;
    private boolean loading;
    private boolean loadingForward;
    private boolean loadingFromOldPosition;
    private int loadingPinnedMessage;
    private int loadsCount;
    private int[] maxDate = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
    private int[] maxMessageId = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
    private TextView mediaBanTooltip;
    private FrameLayout mentionContainer;
    private ExtendedGridLayoutManager mentionGridLayoutManager;
    private LinearLayoutManager mentionLayoutManager;
    private AnimatorSet mentionListAnimation;
    private RecyclerListView mentionListView;
    private boolean mentionListViewIgnoreLayout;
    private boolean mentionListViewIsScrolling;
    private int mentionListViewLastViewPosition;
    private int mentionListViewLastViewTop;
    private int mentionListViewScrollOffsetY;
    private FrameLayout mentiondownButton;
    private ObjectAnimator mentiondownButtonAnimation;
    private TextView mentiondownButtonCounter;
    private ImageView mentiondownButtonImage;
    private MentionsAdapter mentionsAdapter;
    private OnItemClickListener mentionsOnItemClickListener;
    private long mergeDialogId;
    MessageObject messageObject;
    protected ArrayList<MessageObject> messages = new ArrayList();
    private HashMap<String, ArrayList<MessageObject>> messagesByDays = new HashMap();
    private HashMap<Integer, MessageObject>[] messagesDict = new HashMap[]{new HashMap(), new HashMap()};
    private int[] minDate = new int[2];
    private int[] minMessageId = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
    private TextView muteItem;
    private boolean needSelectFromMessageId;
    private int newMentionsCount;
    private int newUnreadMessageCount;
    OnItemClickListenerExtended onItemClickListener = new C42515();
    OnItemLongClickListener onItemLongClickListener = new C42484();
    private boolean openAnimationEnded;
    private boolean openSearchKeyboard;
    private View overlayView;
    private FrameLayout pagedownButton;
    private AnimatorSet pagedownButtonAnimation;
    private TextView pagedownButtonCounter;
    private ImageView pagedownButtonImage;
    private boolean pagedownButtonShowedByScroll;
    private boolean paused = true;
    private boolean pausedOnLastMessage;
    private String pendingLinkSearchString;
    private Runnable pendingWebPageTimeoutRunnable;
    private PhotoViewerProvider photoViewerProvider = new C42341();
    private FileLocation pinnedImageLocation;
    private View pinnedLineView;
    private BackupImageView pinnedMessageImageView;
    private SimpleTextView pinnedMessageNameTextView;
    private MessageObject pinnedMessageObject;
    private SimpleTextView pinnedMessageTextView;
    private FrameLayout pinnedMessageView;
    private AnimatorSet pinnedMessageViewAnimator;
    private RadialProgressView progressBar;
    private FrameLayout progressView;
    private View progressView2;
    private Runnable readRunnable = new C42372();
    private boolean readWhenResume;
    private int readWithDate;
    private int readWithMid;
    private AnimatorSet replyButtonAnimation;
    private ImageView replyCloseImageView;
    private ImageView replyIconImageView;
    private FileLocation replyImageLocation;
    private BackupImageView replyImageView;
    private View replyLineView;
    private SimpleTextView replyNameTextView;
    private SimpleTextView replyObjectTextView;
    private MessageObject replyingMessageObject;
    private TextView reportSpamButton;
    private FrameLayout reportSpamContainer;
    private LinearLayout reportSpamView;
    private AnimatorSet reportSpamViewAnimator;
    private int returnToLoadIndex;
    private int returnToMessageId;
    private FrameLayout roundVideoContainer;
    private AnimatorSet runningAnimation;
    private MessageObject scrollToMessage;
    private int scrollToMessagePosition = -10000;
    private int scrollToOffsetOnRecreate = 0;
    private int scrollToPositionOnRecreate = -1;
    private boolean scrollToTopOnResume;
    private boolean scrollToTopUnReadOnResume;
    private boolean scrollingFloatingDate;
    private ImageView searchCalendarButton;
    private FrameLayout searchContainer;
    private SimpleTextView searchCountText;
    private ImageView searchDownButton;
    private ActionBarMenuItem searchItem;
    private ImageView searchUpButton;
    private ImageView searchUserButton;
    private boolean searchingForUser;
    private User searchingUserMessages;
    private HashMap<Integer, MessageObject>[] selectedMessagesCanCopyIds = new HashMap[]{new HashMap(), new HashMap()};
    private HashMap<Integer, MessageObject>[] selectedMessagesCanStarIds = new HashMap[]{new HashMap(), new HashMap()};
    private NumberTextView selectedMessagesCountTextView;
    private HashMap<Integer, MessageObject>[] selectedMessagesIds = new HashMap[]{new HashMap(), new HashMap()};
    private MessageObject selectedObject;
    private GroupedMessages selectedObjectGroup;
    boolean showFilterDialog = false;
    private boolean showingDialog;
    private int startLoadFromMessageId;
    private int startLoadFromMessageOffset = Integer.MAX_VALUE;
    private boolean startReplyOnTextChange;
    private String startVideoEdit;
    private StickersAdapter stickersAdapter;
    private RecyclerListView stickersListView;
    private OnItemClickListener stickersOnItemClickListener;
    private FrameLayout stickersPanel;
    private ImageView stickersPanelArrow;
    private View timeItem2;
    private int topViewWasVisible;
    private MessageObject unreadMessageObject;
    private int unread_to_load;
    private boolean userBlocked = false;
    private TextureView videoTextureView;
    private AnimatorSet voiceHintAnimation;
    private Runnable voiceHintHideRunnable;
    private TextView voiceHintTextView;
    private Runnable waitingForCharaterEnterRunnable;
    private ArrayList<Integer> waitingForLoad = new ArrayList();
    private boolean waitingForReplyMessageLoad;
    private boolean wasPaused;

    /* renamed from: org.telegram.ui.ChatActivity$1 */
    class C42341 extends EmptyPhotoViewerProvider {
        C42341() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            int childCount = ChatActivity.this.chatListView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                ImageReceiver imageReceiver = null;
                View childAt = ChatActivity.this.chatListView.getChildAt(i2);
                if (childAt instanceof ChatMessageCell) {
                    if (messageObject != null) {
                        ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                        MessageObject messageObject2 = chatMessageCell.getMessageObject();
                        ImageReceiver photoImage = (messageObject2 == null || messageObject2.getId() != messageObject.getId()) ? null : chatMessageCell.getPhotoImage();
                        imageReceiver = photoImage;
                    }
                } else if (childAt instanceof ChatActionCell) {
                    ChatActionCell chatActionCell = (ChatActionCell) childAt;
                    MessageObject messageObject3 = chatActionCell.getMessageObject();
                    if (messageObject3 != null) {
                        if (messageObject != null) {
                            if (messageObject3.getId() == messageObject.getId()) {
                                imageReceiver = chatActionCell.getPhotoImage();
                            }
                        } else if (fileLocation != null && messageObject3.photoThumbs != null) {
                            for (int i3 = 0; i3 < messageObject3.photoThumbs.size(); i3++) {
                                PhotoSize photoSize = (PhotoSize) messageObject3.photoThumbs.get(i3);
                                if (photoSize.location.volume_id == fileLocation.volume_id && photoSize.location.local_id == fileLocation.local_id) {
                                    imageReceiver = chatActionCell.getPhotoImage();
                                    break;
                                }
                            }
                        }
                    }
                }
                if (imageReceiver != null) {
                    int[] iArr = new int[2];
                    childAt.getLocationInWindow(iArr);
                    PlaceProviderObject placeProviderObject = new PlaceProviderObject();
                    placeProviderObject.viewX = iArr[0];
                    placeProviderObject.viewY = iArr[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight);
                    placeProviderObject.parentView = ChatActivity.this.chatListView;
                    placeProviderObject.imageReceiver = imageReceiver;
                    placeProviderObject.thumb = imageReceiver.getBitmap();
                    placeProviderObject.radius = imageReceiver.getRoundRadius();
                    if ((childAt instanceof ChatActionCell) && ChatActivity.this.currentChat != null) {
                        placeProviderObject.dialogId = -ChatActivity.this.currentChat.id;
                    }
                    if ((ChatActivity.this.pinnedMessageView != null && ChatActivity.this.pinnedMessageView.getTag() == null) || (ChatActivity.this.reportSpamView != null && ChatActivity.this.reportSpamView.getTag() == null)) {
                        placeProviderObject.clipTopAddition = AndroidUtilities.dp(48.0f);
                    }
                    return placeProviderObject;
                }
            }
            return null;
        }
    }

    /* renamed from: org.telegram.ui.ChatActivity$2 */
    class C42372 implements Runnable {
        C42372() {
        }

        public void run() {
            if (ChatActivity.this.readWhenResume && !ChatActivity.this.messages.isEmpty()) {
                for (int i = 0; i < ChatActivity.this.messages.size(); i++) {
                    MessageObject messageObject = (MessageObject) ChatActivity.this.messages.get(i);
                    if (!messageObject.isUnread() && !messageObject.isOut()) {
                        break;
                    }
                    if (!messageObject.isOut()) {
                        messageObject.setIsRead();
                    }
                }
                ChatActivity.this.readWhenResume = false;
                MessagesController.getInstance().markDialogAsRead(ChatActivity.this.dialog_id, ((MessageObject) ChatActivity.this.messages.get(0)).getId(), ChatActivity.this.readWithMid, ChatActivity.this.readWithDate, true, false);
            }
        }
    }

    /* renamed from: org.telegram.ui.ChatActivity$3 */
    class C42433 extends EmptyPhotoViewerProvider {
        C42433() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            if (i < 0 || i >= ChatActivity.this.botContextResults.size()) {
                return null;
            }
            int childCount = ChatActivity.this.mentionListView.getChildCount();
            BotInlineResult botInlineResult = ChatActivity.this.botContextResults.get(i);
            int i2 = 0;
            while (i2 < childCount) {
                ImageReceiver photoImage;
                View childAt = ChatActivity.this.mentionListView.getChildAt(i2);
                if (childAt instanceof ContextLinkCell) {
                    ContextLinkCell contextLinkCell = (ContextLinkCell) childAt;
                    if (contextLinkCell.getResult() == botInlineResult) {
                        photoImage = contextLinkCell.getPhotoImage();
                        if (photoImage == null) {
                            int[] iArr = new int[2];
                            childAt.getLocationInWindow(iArr);
                            PlaceProviderObject placeProviderObject = new PlaceProviderObject();
                            placeProviderObject.viewX = iArr[0];
                            placeProviderObject.viewY = iArr[1] - (VERSION.SDK_INT < 21 ? 0 : AndroidUtilities.statusBarHeight);
                            placeProviderObject.parentView = ChatActivity.this.mentionListView;
                            placeProviderObject.imageReceiver = photoImage;
                            placeProviderObject.thumb = photoImage.getBitmap();
                            placeProviderObject.radius = photoImage.getRoundRadius();
                            return placeProviderObject;
                        }
                        i2++;
                    }
                }
                photoImage = null;
                if (photoImage == null) {
                    i2++;
                } else {
                    int[] iArr2 = new int[2];
                    childAt.getLocationInWindow(iArr2);
                    PlaceProviderObject placeProviderObject2 = new PlaceProviderObject();
                    placeProviderObject2.viewX = iArr2[0];
                    if (VERSION.SDK_INT < 21) {
                    }
                    placeProviderObject2.viewY = iArr2[1] - (VERSION.SDK_INT < 21 ? 0 : AndroidUtilities.statusBarHeight);
                    placeProviderObject2.parentView = ChatActivity.this.mentionListView;
                    placeProviderObject2.imageReceiver = photoImage;
                    placeProviderObject2.thumb = photoImage.getBitmap();
                    placeProviderObject2.radius = photoImage.getRoundRadius();
                    return placeProviderObject2;
                }
            }
            return null;
        }

        public void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo) {
            if (i >= 0 && i < ChatActivity.this.botContextResults.size()) {
                ChatActivity.this.sendBotInlineResult((BotInlineResult) ChatActivity.this.botContextResults.get(i));
            }
        }
    }

    /* renamed from: org.telegram.ui.ChatActivity$4 */
    class C42484 implements OnItemLongClickListener {
        C42484() {
        }

        public boolean onItemClick(View view, int i) {
            if (ChatActivity.this.actionBar.isActionModeShowed()) {
                return false;
            }
            ChatActivity.this.createMenu(view, false, true);
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChatActivity$5 */
    class C42515 implements OnItemClickListenerExtended {
        C42515() {
        }

        public void onItemClick(View view, int i, float f, float f2) {
            if (ChatActivity.this.actionBar.isActionModeShowed()) {
                boolean z = view instanceof ChatMessageCell ? !((ChatMessageCell) view).isInsideBackground(f, f2) : false;
                ChatActivity.this.processRowSelect(view, z);
                return;
            }
            ChatActivity.this.createMenu(view, true, false);
        }
    }

    public class ChatActivityAdapter extends Adapter {
        private int botInfoRow = -1;
        private boolean isBot;
        private int loadingDownRow;
        private int loadingUpRow;
        private Context mContext;
        private int messagesEndRow;
        private int messagesStartRow;
        private int rowCount;

        /* renamed from: org.telegram.ui.ChatActivity$ChatActivityAdapter$1 */
        class C42781 implements ChatMessageCellDelegate {

            /* renamed from: org.telegram.ui.ChatActivity$ChatActivityAdapter$1$1 */
            class C42761 implements C2497d {
                C42761() {
                }

                public void onResult(Object obj, int i) {
                    switch (i) {
                        case -23:
                            Log.d("alireza", "alireza log NOT send");
                            return;
                        case 23:
                            ToastUtil.m14194a(ApplicationLoader.applicationContext, ((C2757a) obj).c()).show();
                            Log.d("alireza", "alireza log send");
                            return;
                        default:
                            return;
                    }
                }
            }

            C42781() {
            }

            public boolean canPerformActions() {
                return (ChatActivity.this.actionBar == null || ChatActivity.this.actionBar.isActionModeShowed()) ? false : true;
            }

            public void didLongPressed(ChatMessageCell chatMessageCell) {
                ChatActivity.this.createMenu(chatMessageCell, false, false);
            }

            public void didPressedBotButton(ChatMessageCell chatMessageCell, KeyboardButton keyboardButton) {
                if (ChatActivity.this.getParentActivity() == null) {
                    return;
                }
                if (ChatActivity.this.bottomOverlayChat.getVisibility() != 0 || (keyboardButton instanceof TLRPC$TL_keyboardButtonSwitchInline) || (keyboardButton instanceof TLRPC$TL_keyboardButtonCallback) || (keyboardButton instanceof TLRPC$TL_keyboardButtonGame) || (keyboardButton instanceof TLRPC$TL_keyboardButtonUrl) || (keyboardButton instanceof TLRPC$TL_keyboardButtonBuy)) {
                    ChatActivity.this.chatActivityEnterView.didPressedBotButton(keyboardButton, chatMessageCell.getMessageObject(), chatMessageCell.getMessageObject());
                }
            }

            public void didPressedCancelSendButton(ChatMessageCell chatMessageCell) {
                MessageObject messageObject = chatMessageCell.getMessageObject();
                if (messageObject.messageOwner.send_state != 0) {
                    SendMessagesHelper.getInstance().cancelSendingMessage(messageObject);
                }
            }

            public void didPressedChannelAvatar(ChatMessageCell chatMessageCell, Chat chat, int i) {
                if (ChatActivity.this.actionBar.isActionModeShowed()) {
                    ChatActivity.this.processRowSelect(chatMessageCell, true);
                } else if (chat != null && chat != ChatActivity.this.currentChat) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("chat_id", chat.id);
                    if (i != 0) {
                        bundle.putInt("message_id", i);
                    }
                    if (MessagesController.checkCanOpenChat(bundle, ChatActivity.this, chatMessageCell.getMessageObject())) {
                        ChatActivity.this.presentFragment(new ChatActivity(bundle), true);
                    }
                }
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void didPressedImage(org.telegram.ui.Cells.ChatMessageCell r9) {
                /*
                r8 = this;
                r0 = 3;
                r6 = 1;
                r1 = 0;
                r5 = -1;
                r4 = 0;
                r7 = r9.getMessageObject();
                r2 = r7.isSendError();
                if (r2 == 0) goto L_0x0017;
            L_0x000f:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0.createMenu(r9, r1, r1);
            L_0x0016:
                return;
            L_0x0017:
                r2 = r7.isSending();
                if (r2 != 0) goto L_0x0016;
            L_0x001d:
                r2 = r7.isSecretPhoto();
                if (r2 == 0) goto L_0x004f;
            L_0x0023:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0 = r0.sendSecretMessageRead(r7);
                if (r0 == 0) goto L_0x0030;
            L_0x002d:
                r9.invalidate();
            L_0x0030:
                r0 = org.telegram.ui.SecretMediaViewer.getInstance();
                r1 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChatActivity.this;
                r1 = r1.getParentActivity();
                r0.setParentActivity(r1);
                r0 = org.telegram.ui.SecretMediaViewer.getInstance();
                r1 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChatActivity.this;
                r1 = r1.photoViewerProvider;
                r0.openMedia(r7, r1);
                goto L_0x0016;
            L_0x004f:
                r2 = r7.type;
                r3 = 13;
                if (r2 != r3) goto L_0x0094;
            L_0x0055:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r6 = org.telegram.ui.ChatActivity.this;
                r0 = new org.telegram.ui.Components.StickersAlert;
                r1 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChatActivity.this;
                r1 = r1.getParentActivity();
                r2 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChatActivity.this;
                r3 = r7.getInputStickerSet();
                r5 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r5 = org.telegram.ui.ChatActivity.this;
                r5 = r5.bottomOverlayChat;
                r5 = r5.getVisibility();
                if (r5 == 0) goto L_0x0092;
            L_0x0079:
                r5 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r5 = org.telegram.ui.ChatActivity.this;
                r5 = r5.currentChat;
                r5 = org.telegram.messenger.ChatObject.canSendStickers(r5);
                if (r5 == 0) goto L_0x0092;
            L_0x0085:
                r5 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r5 = org.telegram.ui.ChatActivity.this;
                r5 = r5.chatActivityEnterView;
            L_0x008b:
                r0.<init>(r1, r2, r3, r4, r5);
                r6.showDialog(r0);
                goto L_0x0016;
            L_0x0092:
                r5 = r4;
                goto L_0x008b;
            L_0x0094:
                r2 = r7.isVideo();
                if (r2 != 0) goto L_0x00ae;
            L_0x009a:
                r2 = r7.type;
                if (r2 == r6) goto L_0x00ae;
            L_0x009e:
                r2 = r7.type;
                if (r2 != 0) goto L_0x00a8;
            L_0x00a2:
                r2 = r7.isWebpageDocument();
                if (r2 == 0) goto L_0x00ae;
            L_0x00a8:
                r2 = r7.isGif();
                if (r2 == 0) goto L_0x0108;
            L_0x00ae:
                r0 = r7.isVideo();
                if (r0 == 0) goto L_0x00bb;
            L_0x00b4:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0.sendSecretMessageRead(r7);
            L_0x00bb:
                r0 = org.telegram.ui.PhotoViewer.getInstance();
                r1 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChatActivity.this;
                r1 = r1.getParentActivity();
                r0.setParentActivity(r1);
                r0 = org.telegram.ui.PhotoViewer.getInstance();
                r1 = r7.type;
                if (r1 == 0) goto L_0x0102;
            L_0x00d2:
                r1 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChatActivity.this;
                r2 = r1.dialog_id;
            L_0x00da:
                r1 = r7.type;
                if (r1 == 0) goto L_0x0105;
            L_0x00de:
                r1 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChatActivity.this;
                r4 = r1.mergeDialogId;
            L_0x00e6:
                r1 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChatActivity.this;
                r6 = r1.photoViewerProvider;
                r1 = r7;
                r0 = r0.openPhoto(r1, r2, r4, r6);
                if (r0 == 0) goto L_0x0016;
            L_0x00f5:
                r0 = org.telegram.ui.PhotoViewer.getInstance();
                r1 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChatActivity.this;
                r0.setParentChatActivity(r1);
                goto L_0x0016;
            L_0x0102:
                r2 = 0;
                goto L_0x00da;
            L_0x0105:
                r4 = 0;
                goto L_0x00e6;
            L_0x0108:
                r2 = r7.type;
                if (r2 != r0) goto L_0x0189;
            L_0x010c:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0.sendSecretMessageRead(r7);
                r0 = r7.messageOwner;	 Catch:{ Exception -> 0x0171 }
                r0 = r0.attachPath;	 Catch:{ Exception -> 0x0171 }
                if (r0 == 0) goto L_0x02c9;
            L_0x0119:
                r0 = r7.messageOwner;	 Catch:{ Exception -> 0x0171 }
                r0 = r0.attachPath;	 Catch:{ Exception -> 0x0171 }
                r0 = r0.length();	 Catch:{ Exception -> 0x0171 }
                if (r0 == 0) goto L_0x02c9;
            L_0x0123:
                r4 = new java.io.File;	 Catch:{ Exception -> 0x0171 }
                r0 = r7.messageOwner;	 Catch:{ Exception -> 0x0171 }
                r0 = r0.attachPath;	 Catch:{ Exception -> 0x0171 }
                r4.<init>(r0);	 Catch:{ Exception -> 0x0171 }
                r0 = r4;
            L_0x012d:
                if (r0 == 0) goto L_0x0135;
            L_0x012f:
                r1 = r0.exists();	 Catch:{ Exception -> 0x0171 }
                if (r1 != 0) goto L_0x013b;
            L_0x0135:
                r0 = r7.messageOwner;	 Catch:{ Exception -> 0x0171 }
                r0 = org.telegram.messenger.FileLoader.getPathToMessage(r0);	 Catch:{ Exception -> 0x0171 }
            L_0x013b:
                r1 = new android.content.Intent;	 Catch:{ Exception -> 0x0171 }
                r2 = "android.intent.action.VIEW";
                r1.<init>(r2);	 Catch:{ Exception -> 0x0171 }
                r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x0171 }
                r3 = 24;
                if (r2 < r3) goto L_0x017e;
            L_0x0149:
                r2 = 1;
                r1.setFlags(r2);	 Catch:{ Exception -> 0x0171 }
                r2 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;	 Catch:{ Exception -> 0x0171 }
                r2 = org.telegram.ui.ChatActivity.this;	 Catch:{ Exception -> 0x0171 }
                r2 = r2.getParentActivity();	 Catch:{ Exception -> 0x0171 }
                r3 = "org.ir.talaeii.provider";
                r0 = android.support.v4.content.FileProvider.a(r2, r3, r0);	 Catch:{ Exception -> 0x0171 }
                r2 = "video/mp4";
                r1.setDataAndType(r0, r2);	 Catch:{ Exception -> 0x0171 }
            L_0x0162:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;	 Catch:{ Exception -> 0x0171 }
                r0 = org.telegram.ui.ChatActivity.this;	 Catch:{ Exception -> 0x0171 }
                r0 = r0.getParentActivity();	 Catch:{ Exception -> 0x0171 }
                r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
                r0.startActivityForResult(r1, r2);	 Catch:{ Exception -> 0x0171 }
                goto L_0x0016;
            L_0x0171:
                r0 = move-exception;
                org.telegram.messenger.FileLog.e(r0);
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0.alertUserOpenError(r7);
                goto L_0x0016;
            L_0x017e:
                r0 = android.net.Uri.fromFile(r0);	 Catch:{ Exception -> 0x0171 }
                r2 = "video/mp4";
                r1.setDataAndType(r0, r2);	 Catch:{ Exception -> 0x0171 }
                goto L_0x0162;
            L_0x0189:
                r2 = r7.type;
                r3 = 4;
                if (r2 != r3) goto L_0x01d9;
            L_0x018e:
                r2 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChatActivity.this;
                r2 = org.telegram.messenger.AndroidUtilities.isGoogleMapsInstalled(r2);
                if (r2 == 0) goto L_0x0016;
            L_0x0198:
                r2 = r7.isLiveLocation();
                if (r2 == 0) goto L_0x01b7;
            L_0x019e:
                r0 = new org.telegram.ui.LocationActivity;
                r1 = 2;
                r0.<init>(r1);
                r0.setMessageObject(r7);
                r1 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChatActivity.this;
                r0.setDelegate(r1);
                r1 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChatActivity.this;
                r1.presentFragment(r0);
                goto L_0x0016;
            L_0x01b7:
                r2 = new org.telegram.ui.LocationActivity;
                r3 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r3 = org.telegram.ui.ChatActivity.this;
                r3 = r3.currentEncryptedChat;
                if (r3 != 0) goto L_0x01d7;
            L_0x01c1:
                r2.<init>(r0);
                r2.setMessageObject(r7);
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r2.setDelegate(r0);
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0.presentFragment(r2);
                goto L_0x0016;
            L_0x01d7:
                r0 = r1;
                goto L_0x01c1;
            L_0x01d9:
                r0 = r7.type;
                r1 = 9;
                if (r0 == r1) goto L_0x01e3;
            L_0x01df:
                r0 = r7.type;
                if (r0 != 0) goto L_0x0016;
            L_0x01e3:
                r0 = r7.getDocumentName();
                r1 = "attheme";
                r0 = r0.endsWith(r1);
                if (r0 == 0) goto L_0x02ac;
            L_0x01f0:
                r0 = r7.messageOwner;
                r0 = r0.attachPath;
                if (r0 == 0) goto L_0x02c6;
            L_0x01f6:
                r0 = r7.messageOwner;
                r0 = r0.attachPath;
                r0 = r0.length();
                if (r0 == 0) goto L_0x02c6;
            L_0x0200:
                r1 = new java.io.File;
                r0 = r7.messageOwner;
                r0 = r0.attachPath;
                r1.<init>(r0);
                r0 = r1.exists();
                if (r0 == 0) goto L_0x02c6;
            L_0x020f:
                if (r1 != 0) goto L_0x021e;
            L_0x0211:
                r0 = r7.messageOwner;
                r0 = org.telegram.messenger.FileLoader.getPathToMessage(r0);
                r2 = r0.exists();
                if (r2 == 0) goto L_0x021e;
            L_0x021d:
                r1 = r0;
            L_0x021e:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0 = r0.chatLayoutManager;
                if (r0 == 0) goto L_0x027d;
            L_0x0228:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0 = r0.chatLayoutManager;
                r0 = r0.findFirstVisibleItemPosition();
                if (r0 == 0) goto L_0x029d;
            L_0x0236:
                r2 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChatActivity.this;
                r2.scrollToPositionOnRecreate = r0;
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0 = r0.chatListView;
                r2 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChatActivity.this;
                r2 = r2.scrollToPositionOnRecreate;
                r0 = r0.findViewHolderForAdapterPosition(r2);
                r0 = (org.telegram.ui.Components.RecyclerListView.Holder) r0;
                if (r0 == 0) goto L_0x0295;
            L_0x0255:
                r2 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChatActivity.this;
                r3 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r3 = org.telegram.ui.ChatActivity.this;
                r3 = r3.chatListView;
                r3 = r3.getMeasuredHeight();
                r0 = r0.itemView;
                r0 = r0.getBottom();
                r0 = r3 - r0;
                r3 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r3 = org.telegram.ui.ChatActivity.this;
                r3 = r3.chatListView;
                r3 = r3.getPaddingBottom();
                r0 = r0 - r3;
                r2.scrollToOffsetOnRecreate = r0;
            L_0x027d:
                r0 = r7.getDocumentName();
                r0 = org.telegram.ui.ActionBar.Theme.applyThemeFile(r1, r0, r6);
                if (r0 == 0) goto L_0x02a5;
            L_0x0287:
                r2 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChatActivity.this;
                r3 = new org.telegram.ui.ThemePreviewActivity;
                r3.<init>(r1, r0);
                r2.presentFragment(r3);
                goto L_0x0016;
            L_0x0295:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0.scrollToPositionOnRecreate = r5;
                goto L_0x027d;
            L_0x029d:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0.scrollToPositionOnRecreate = r5;
                goto L_0x027d;
            L_0x02a5:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0.scrollToPositionOnRecreate = r5;
            L_0x02ac:
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;	 Catch:{ Exception -> 0x02b9 }
                r0 = org.telegram.ui.ChatActivity.this;	 Catch:{ Exception -> 0x02b9 }
                r0 = r0.getParentActivity();	 Catch:{ Exception -> 0x02b9 }
                org.telegram.messenger.AndroidUtilities.openForView(r7, r0);	 Catch:{ Exception -> 0x02b9 }
                goto L_0x0016;
            L_0x02b9:
                r0 = move-exception;
                org.telegram.messenger.FileLog.e(r0);
                r0 = org.telegram.ui.ChatActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChatActivity.this;
                r0.alertUserOpenError(r7);
                goto L_0x0016;
            L_0x02c6:
                r1 = r4;
                goto L_0x020f;
            L_0x02c9:
                r0 = r4;
                goto L_0x012d;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.ChatActivity.ChatActivityAdapter.1.didPressedImage(org.telegram.ui.Cells.ChatMessageCell):void");
            }

            public void didPressedInstantButton(ChatMessageCell chatMessageCell, int i) {
                MessageObject messageObject = chatMessageCell.getMessageObject();
                if (i == 0) {
                    if (messageObject.messageOwner.media != null && messageObject.messageOwner.media.webpage != null && messageObject.messageOwner.media.webpage.cached_page != null) {
                        ArticleViewer.getInstance().setParentActivity(ChatActivity.this.getParentActivity(), ChatActivity.this);
                        ArticleViewer.getInstance().open(messageObject);
                    }
                } else if (messageObject.messageOwner.media != null && messageObject.messageOwner.media.webpage != null) {
                    Browser.openUrl(ChatActivity.this.getParentActivity(), messageObject.messageOwner.media.webpage.url);
                }
            }

            public void didPressedOther(ChatMessageCell chatMessageCell) {
                if (chatMessageCell.getMessageObject().type != 16) {
                    ChatActivity.this.createMenu(chatMessageCell, true, false, false);
                } else if (ChatActivity.this.currentUser != null) {
                    VoIPHelper.startCall(ChatActivity.this.currentUser, ChatActivity.this.getParentActivity(), MessagesController.getInstance().getUserFull(ChatActivity.this.currentUser.id));
                }
            }

            public void didPressedReplyMessage(ChatMessageCell chatMessageCell, int i) {
                MessageObject messageObject = chatMessageCell.getMessageObject();
                ChatActivity.this.scrollToMessageId(i, messageObject.getId(), true, messageObject.getDialogId() == ChatActivity.this.mergeDialogId ? 1 : 0, false);
            }

            public void didPressedShare(ChatMessageCell chatMessageCell) {
                if (ChatActivity.this.getParentActivity() != null) {
                    if (ChatActivity.this.chatActivityEnterView != null) {
                        ChatActivity.this.chatActivityEnterView.closeKeyboard();
                    }
                    MessageObject messageObject = chatMessageCell.getMessageObject();
                    if (!UserObject.isUserSelf(ChatActivity.this.currentUser) || messageObject.messageOwner.fwd_from.saved_from_peer == null) {
                        ArrayList arrayList;
                        ChatActivity chatActivity;
                        Context access$17700;
                        boolean z;
                        if (messageObject.getGroupId() != 0) {
                            GroupedMessages groupedMessages = (GroupedMessages) ChatActivity.this.groupedMessagesMap.get(Long.valueOf(messageObject.getGroupId()));
                            if (groupedMessages != null) {
                                arrayList = groupedMessages.messages;
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                    arrayList.add(messageObject);
                                }
                                chatActivity = ChatActivity.this;
                                access$17700 = ChatActivityAdapter.this.mContext;
                                z = ChatObject.isChannel(ChatActivity.this.currentChat) && !ChatActivity.this.currentChat.megagroup && ChatActivity.this.currentChat.username != null && ChatActivity.this.currentChat.username.length() > 0;
                                chatActivity.showDialog(new ShareAlert(access$17700, arrayList, null, z, null, false));
                                return;
                            }
                        }
                        arrayList = null;
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            arrayList.add(messageObject);
                        }
                        chatActivity = ChatActivity.this;
                        access$17700 = ChatActivityAdapter.this.mContext;
                        if (!ChatObject.isChannel(ChatActivity.this.currentChat)) {
                        }
                        chatActivity.showDialog(new ShareAlert(access$17700, arrayList, null, z, null, false));
                        return;
                    }
                    Bundle bundle = new Bundle();
                    if (messageObject.messageOwner.fwd_from.saved_from_peer.channel_id != 0) {
                        bundle.putInt("chat_id", messageObject.messageOwner.fwd_from.saved_from_peer.channel_id);
                    } else if (messageObject.messageOwner.fwd_from.saved_from_peer.chat_id != 0) {
                        bundle.putInt("chat_id", messageObject.messageOwner.fwd_from.saved_from_peer.chat_id);
                    } else if (messageObject.messageOwner.fwd_from.saved_from_peer.user_id != 0) {
                        bundle.putInt("user_id", messageObject.messageOwner.fwd_from.saved_from_peer.user_id);
                    }
                    bundle.putInt("message_id", messageObject.messageOwner.fwd_from.saved_from_msg_id);
                    if (MessagesController.checkCanOpenChat(bundle, ChatActivity.this)) {
                        ChatActivity.this.presentFragment(new ChatActivity(bundle));
                    }
                }
            }

            public void didPressedUrl(MessageObject messageObject, CharacterStyle characterStyle, boolean z) {
                boolean z2 = true;
                if (characterStyle != null) {
                    boolean z3;
                    String url = ((URLSpan) characterStyle).getURL();
                    if (C3791b.ab(ApplicationLoader.applicationContext)) {
                        Iterator it = C3791b.Y(ApplicationLoader.applicationContext).iterator();
                        while (it.hasNext()) {
                            if (((Category) it.next()).getChannelId() == ChatActivity.this.chat_id) {
                                z3 = true;
                                break;
                            }
                        }
                        z3 = false;
                        if (z3 && !url.startsWith("@")) {
                            org.telegram.customization.Model.Ads.Log log = new org.telegram.customization.Model.Ads.Log();
                            log.setChannelId(ChatActivity.this.chat_id);
                            log.setMessageId(messageObject.getId());
                            log.setUrl(url);
                            log.setAction(2);
                            C2818c.a(ChatActivity.this.getParentActivity(), new C42761()).a(log);
                        }
                    }
                    if (characterStyle instanceof URLSpanMono) {
                        ((URLSpanMono) characterStyle).copyToClipboard();
                        Toast.makeText(ChatActivity.this.getParentActivity(), LocaleController.getString("TextCopied", R.string.TextCopied), 0).show();
                    } else if (characterStyle instanceof URLSpanUserMention) {
                        User user = MessagesController.getInstance().getUser(Utilities.parseInt(((URLSpanUserMention) characterStyle).getURL()));
                        if (user != null) {
                            MessagesController.openChatOrProfileWith(user, null, ChatActivity.this, 0, false);
                        }
                    } else if (characterStyle instanceof URLSpanNoUnderline) {
                        url = ((URLSpanNoUnderline) characterStyle).getURL();
                        if (url.startsWith("@")) {
                            MessagesController.openByUserName(url.substring(1), ChatActivity.this, 0);
                        } else if (url.startsWith("#")) {
                            if (ChatObject.isChannel(ChatActivity.this.currentChat)) {
                                ChatActivity.this.openSearchWithText(url);
                                return;
                            }
                            BaseFragment dialogsActivity = new DialogsActivity(null);
                            dialogsActivity.setSearchString(url);
                            ChatActivity.this.presentFragment(dialogsActivity);
                        } else if (url.startsWith("/") && URLSpanBotCommand.enabled) {
                            ChatActivityEnterView chatActivityEnterView = ChatActivity.this.chatActivityEnterView;
                            z3 = ChatActivity.this.currentChat != null && ChatActivity.this.currentChat.megagroup;
                            chatActivityEnterView.setCommand(messageObject, url, z, z3);
                            if (!z && ChatActivity.this.chatActivityEnterView.getFieldText() == null) {
                                ChatActivity.this.showReplyPanel(false, null, null, null, false);
                            }
                        }
                    } else {
                        final String url2 = ((URLSpan) characterStyle).getURL();
                        if (z) {
                            Builder builder = new Builder(ChatActivity.this.getParentActivity());
                            builder.setTitle(url2);
                            builder.setItems(new CharSequence[]{LocaleController.getString("Open", R.string.Open), LocaleController.getString("Copy", R.string.Copy)}, new OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean z = true;
                                    if (i == 0) {
                                        Context parentActivity = ChatActivity.this.getParentActivity();
                                        String str = url2;
                                        if (ChatActivity.this.inlineReturn != 0) {
                                            z = false;
                                        }
                                        Browser.openUrl(parentActivity, str, z);
                                    } else if (i == 1) {
                                        CharSequence charSequence = url2;
                                        if (charSequence.startsWith("mailto:")) {
                                            charSequence = charSequence.substring(7);
                                        } else if (charSequence.startsWith("tel:")) {
                                            charSequence = charSequence.substring(4);
                                        }
                                        AndroidUtilities.addToClipboard(charSequence);
                                    }
                                }
                            });
                            ChatActivity.this.showDialog(builder.create());
                        } else if (characterStyle instanceof URLSpanReplacement) {
                            ChatActivity.this.showOpenUrlAlert(((URLSpanReplacement) characterStyle).getURL(), true);
                        } else if (characterStyle instanceof URLSpan) {
                            if (!(!(messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) || messageObject.messageOwner.media.webpage == null || messageObject.messageOwner.media.webpage.cached_page == null)) {
                                CharSequence toLowerCase = url2.toLowerCase();
                                Object toLowerCase2 = messageObject.messageOwner.media.webpage.url.toLowerCase();
                                if ((toLowerCase.contains("telegra.ph") || toLowerCase.contains("t.me/iv")) && (toLowerCase.contains(toLowerCase2) || toLowerCase2.contains(toLowerCase))) {
                                    ArticleViewer.getInstance().setParentActivity(ChatActivity.this.getParentActivity(), ChatActivity.this);
                                    ArticleViewer.getInstance().open(messageObject);
                                    return;
                                }
                            }
                            Context parentActivity = ChatActivity.this.getParentActivity();
                            if (ChatActivity.this.inlineReturn != 0) {
                                z2 = false;
                            }
                            Browser.openUrl(parentActivity, url2, z2);
                        } else if (characterStyle instanceof ClickableSpan) {
                            ((ClickableSpan) characterStyle).onClick(ChatActivity.this.fragmentView);
                        }
                    }
                }
            }

            public void didPressedUserAvatar(ChatMessageCell chatMessageCell, User user) {
                boolean z = true;
                if (ChatActivity.this.actionBar.isActionModeShowed()) {
                    ChatActivity.this.processRowSelect(chatMessageCell, true);
                } else if (user != null && user.id != UserConfig.getClientUserId()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("user_id", user.id);
                    BaseFragment profileActivity = new ProfileActivity(bundle);
                    if (ChatActivity.this.currentUser == null || ChatActivity.this.currentUser.id != user.id) {
                        z = false;
                    }
                    profileActivity.setPlayProfileAnimation(z);
                    ChatActivity.this.presentFragment(profileActivity);
                }
            }

            public void didPressedViaBot(ChatMessageCell chatMessageCell, String str) {
                if (ChatActivity.this.bottomOverlayChat != null && ChatActivity.this.bottomOverlayChat.getVisibility() == 0) {
                    return;
                }
                if ((ChatActivity.this.bottomOverlay == null || ChatActivity.this.bottomOverlay.getVisibility() != 0) && ChatActivity.this.chatActivityEnterView != null && str != null && str.length() > 0) {
                    ChatActivity.this.chatActivityEnterView.setFieldText("@" + str + " ");
                    ChatActivity.this.chatActivityEnterView.openKeyboard();
                }
            }

            public void didTagPressed(String str) {
                Bundle bundle = new Bundle();
                bundle.putString("EXTRA_TAG", str);
                ChatActivity.this.presentFragment(new C2624m(bundle));
            }

            public boolean isChatAdminCell(int i) {
                return (ChatObject.isChannel(ChatActivity.this.currentChat) && ChatActivity.this.currentChat.megagroup) ? MessagesController.getInstance().isChannelAdmin(ChatActivity.this.currentChat.id, i) : false;
            }

            public void needOpenWebView(String str, String str2, String str3, String str4, int i, int i2) {
                EmbedBottomSheet.show(ChatActivityAdapter.this.mContext, str2, str3, str4, str, i, i2);
            }

            public boolean needPlayMessage(MessageObject messageObject) {
                if (!messageObject.isVoice() && !messageObject.isRoundVideo()) {
                    return messageObject.isMusic() ? MediaController.getInstance().setPlaylist(ChatActivity.this.messages, messageObject) : false;
                } else {
                    boolean playMessage = MediaController.getInstance().playMessage(messageObject);
                    MediaController.getInstance().setVoiceMessagesPlaylist(playMessage ? ChatActivity.this.createVoiceMessagesPlaylist(messageObject, false) : null, false);
                    return playMessage;
                }
            }
        }

        /* renamed from: org.telegram.ui.ChatActivity$ChatActivityAdapter$2 */
        class C42792 implements ChatActionCellDelegate {
            C42792() {
            }

            public void didClickedImage(ChatActionCell chatActionCell) {
                MessageObject messageObject = chatActionCell.getMessageObject();
                PhotoViewer.getInstance().setParentActivity(ChatActivity.this.getParentActivity());
                PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 640);
                if (closestPhotoSizeWithSize != null) {
                    PhotoViewer.getInstance().openPhoto(closestPhotoSizeWithSize.location, ChatActivity.this.photoViewerProvider);
                    return;
                }
                PhotoViewer.getInstance().openPhoto(messageObject, 0, 0, ChatActivity.this.photoViewerProvider);
            }

            public void didLongPressed(ChatActionCell chatActionCell) {
                ChatActivity.this.createMenu(chatActionCell, false, false);
            }

            public void didPressedBotButton(MessageObject messageObject, KeyboardButton keyboardButton) {
                if (ChatActivity.this.getParentActivity() == null) {
                    return;
                }
                if (ChatActivity.this.bottomOverlayChat.getVisibility() != 0 || (keyboardButton instanceof TLRPC$TL_keyboardButtonSwitchInline) || (keyboardButton instanceof TLRPC$TL_keyboardButtonCallback) || (keyboardButton instanceof TLRPC$TL_keyboardButtonGame) || (keyboardButton instanceof TLRPC$TL_keyboardButtonUrl) || (keyboardButton instanceof TLRPC$TL_keyboardButtonBuy)) {
                    ChatActivity.this.chatActivityEnterView.didPressedBotButton(keyboardButton, messageObject, messageObject);
                }
            }

            public void didPressedReplyMessage(ChatActionCell chatActionCell, int i) {
                MessageObject messageObject = chatActionCell.getMessageObject();
                ChatActivity.this.scrollToMessageId(i, messageObject.getId(), true, messageObject.getDialogId() == ChatActivity.this.mergeDialogId ? 1 : 0, false);
            }

            public void needOpenUserProfile(int i) {
                boolean z = true;
                Bundle bundle;
                if (i < 0) {
                    bundle = new Bundle();
                    bundle.putInt("chat_id", -i);
                    if (MessagesController.checkCanOpenChat(bundle, ChatActivity.this)) {
                        ChatActivity.this.presentFragment(new ChatActivity(bundle), true);
                    }
                } else if (i != UserConfig.getClientUserId()) {
                    bundle = new Bundle();
                    bundle.putInt("user_id", i);
                    if (ChatActivity.this.currentEncryptedChat != null && i == ChatActivity.this.currentUser.id) {
                        bundle.putLong("dialog_id", ChatActivity.this.dialog_id);
                    }
                    BaseFragment profileActivity = new ProfileActivity(bundle);
                    if (ChatActivity.this.currentUser == null || ChatActivity.this.currentUser.id != i) {
                        z = false;
                    }
                    profileActivity.setPlayProfileAnimation(z);
                    ChatActivity.this.presentFragment(profileActivity);
                }
            }
        }

        /* renamed from: org.telegram.ui.ChatActivity$ChatActivityAdapter$3 */
        class C42803 implements BotHelpCellDelegate {
            C42803() {
            }

            public void didPressUrl(String str) {
                if (str.startsWith("@")) {
                    MessagesController.openByUserName(str.substring(1), ChatActivity.this, 0);
                } else if (str.startsWith("#")) {
                    BaseFragment dialogsActivity = new DialogsActivity(null);
                    dialogsActivity.setSearchString(str);
                    ChatActivity.this.presentFragment(dialogsActivity);
                } else if (str.startsWith("/")) {
                    ChatActivity.this.chatActivityEnterView.setCommand(null, str, false, false);
                    if (ChatActivity.this.chatActivityEnterView.getFieldText() == null) {
                        ChatActivity.this.showReplyPanel(false, null, null, null, false);
                    }
                }
            }
        }

        public ChatActivityAdapter(Context context) {
            this.mContext = context;
            boolean z = ChatActivity.this.currentUser != null && ChatActivity.this.currentUser.bot;
            this.isBot = z;
        }

        public int getItemCount() {
            return this.rowCount;
        }

        public long getItemId(int i) {
            return -1;
        }

        public int getItemViewType(int i) {
            return (i < this.messagesStartRow || i >= this.messagesEndRow) ? i == this.botInfoRow ? 3 : 4 : ((MessageObject) ChatActivity.this.messages.get(i - this.messagesStartRow)).contentType;
        }

        public void notifyDataSetChanged() {
            updateRows();
            try {
                super.notifyDataSetChanged();
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        public void notifyItemChanged(int i) {
            try {
                super.notifyItemChanged(i);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        public void notifyItemInserted(int i) {
            updateRows();
            try {
                super.notifyItemInserted(i);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        public void notifyItemMoved(int i, int i2) {
            updateRows();
            try {
                super.notifyItemMoved(i, i2);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        public void notifyItemRangeChanged(int i, int i2) {
            try {
                super.notifyItemRangeChanged(i, i2);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        public void notifyItemRangeInserted(int i, int i2) {
            updateRows();
            try {
                super.notifyItemRangeInserted(i, i2);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        public void notifyItemRangeRemoved(int i, int i2) {
            updateRows();
            try {
                super.notifyItemRangeRemoved(i, i2);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        public void notifyItemRemoved(int i) {
            updateRows();
            try {
                super.notifyItemRemoved(i);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (i == this.botInfoRow) {
                ((BotHelpCell) viewHolder.itemView).setText(!ChatActivity.this.botInfo.isEmpty() ? ((BotInfo) ChatActivity.this.botInfo.get(Integer.valueOf(ChatActivity.this.currentUser.id))).description : null);
            } else if (i == this.loadingDownRow || i == this.loadingUpRow) {
                ((ChatLoadingCell) viewHolder.itemView).setProgressVisible(ChatActivity.this.loadsCount > 1);
            } else if (i >= this.messagesStartRow && i < this.messagesEndRow) {
                MessageObject messageObject = (MessageObject) ChatActivity.this.messages.get(i - this.messagesStartRow);
                View view = viewHolder.itemView;
                if (view instanceof ChatMessageCell) {
                    int indexOf;
                    int indexOf2;
                    MessageObject messageObject2;
                    final ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                    boolean z = ChatActivity.this.currentChat != null || UserObject.isUserSelf(ChatActivity.this.currentUser);
                    chatMessageCell.isChat = z;
                    boolean z2 = false;
                    boolean z3 = false;
                    GroupedMessages access$7400 = ChatActivity.this.getValidGroupedMessage(messageObject);
                    if (access$7400 != null) {
                        GroupedMessagePosition groupedMessagePosition = (GroupedMessagePosition) access$7400.positions.get(messageObject);
                        if (groupedMessagePosition != null) {
                            if ((groupedMessagePosition.flags & 4) != 0) {
                                indexOf = (access$7400.posArray.indexOf(groupedMessagePosition) + i) + 1;
                            } else {
                                z3 = true;
                                indexOf = -100;
                            }
                            if ((groupedMessagePosition.flags & 8) != 0) {
                                indexOf2 = access$7400.posArray.indexOf(groupedMessagePosition) + (i - access$7400.posArray.size());
                            } else {
                                z2 = true;
                                indexOf2 = -100;
                            }
                        } else {
                            indexOf = -100;
                            indexOf2 = -100;
                        }
                    } else {
                        indexOf2 = i - 1;
                        indexOf = i + 1;
                    }
                    int itemViewType = getItemViewType(indexOf2);
                    int itemViewType2 = getItemViewType(indexOf);
                    if (!(messageObject.messageOwner.reply_markup instanceof TLRPC$TL_replyInlineMarkup) && itemViewType == viewHolder.getItemViewType()) {
                        messageObject2 = (MessageObject) ChatActivity.this.messages.get(indexOf2 - this.messagesStartRow);
                        z2 = messageObject2.isOutOwner() == messageObject.isOutOwner() && Math.abs(messageObject2.messageOwner.date - messageObject.messageOwner.date) <= ChatActivity.add_fave_dialog;
                        if (z2) {
                            if (ChatActivity.this.currentChat != null) {
                                z2 = messageObject2.messageOwner.from_id == messageObject.messageOwner.from_id;
                            } else if (UserObject.isUserSelf(ChatActivity.this.currentUser)) {
                                z2 = messageObject2.getFromId() == messageObject.getFromId();
                            }
                        }
                    }
                    if (itemViewType2 == viewHolder.getItemViewType()) {
                        messageObject2 = (MessageObject) ChatActivity.this.messages.get(indexOf - this.messagesStartRow);
                        z3 = !(messageObject2.messageOwner.reply_markup instanceof TLRPC$TL_replyInlineMarkup) && messageObject2.isOutOwner() == messageObject.isOutOwner() && Math.abs(messageObject2.messageOwner.date - messageObject.messageOwner.date) <= ChatActivity.add_fave_dialog;
                        if (z3) {
                            if (ChatActivity.this.currentChat != null) {
                                z = messageObject2.messageOwner.from_id == messageObject.messageOwner.from_id;
                            } else if (UserObject.isUserSelf(ChatActivity.this.currentUser)) {
                                z = messageObject2.getFromId() == messageObject.getFromId();
                            }
                            chatMessageCell.setMessageObject(messageObject, access$7400, z2, z);
                            if ((view instanceof ChatMessageCell) && MediaController.getInstance().canDownloadMedia(messageObject)) {
                                ((ChatMessageCell) view).downloadAudioIfNeed();
                            }
                            z = ChatActivity.this.highlightMessageId == Integer.MAX_VALUE && messageObject.getId() == ChatActivity.this.highlightMessageId;
                            chatMessageCell.setHighlighted(z);
                            if (ChatActivity.this.searchContainer != null && ChatActivity.this.searchContainer.getVisibility() == 0) {
                                if (MessagesSearchQuery.isMessageFound(messageObject.getId(), messageObject.getDialogId() != ChatActivity.this.mergeDialogId) && MessagesSearchQuery.getLastSearchQuery() != null) {
                                    chatMessageCell.setHighlightedText(MessagesSearchQuery.getLastSearchQuery());
                                    indexOf2 = ChatActivity.this.animatingMessageObjects.indexOf(messageObject);
                                    if (indexOf2 != -1) {
                                        ChatActivity.this.animatingMessageObjects.remove(indexOf2);
                                        chatMessageCell.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                                            public boolean onPreDraw() {
                                                PipRoundVideoView instance = PipRoundVideoView.getInstance();
                                                if (instance != null) {
                                                    instance.showTemporary(true);
                                                }
                                                chatMessageCell.getViewTreeObserver().removeOnPreDrawListener(this);
                                                ImageReceiver photoImage = chatMessageCell.getPhotoImage();
                                                float imageWidth = ((float) photoImage.getImageWidth()) / ChatActivity.this.instantCameraView.getCameraRect().width;
                                                int[] iArr = new int[2];
                                                chatMessageCell.setAlpha(BitmapDescriptorFactory.HUE_RED);
                                                chatMessageCell.getLocationOnScreen(iArr);
                                                iArr[0] = iArr[0] + photoImage.getImageX();
                                                iArr[1] = photoImage.getImageY() + iArr[1];
                                                final View cameraContainer = ChatActivity.this.instantCameraView.getCameraContainer();
                                                cameraContainer.setPivotX(BitmapDescriptorFactory.HUE_RED);
                                                cameraContainer.setPivotY(BitmapDescriptorFactory.HUE_RED);
                                                AnimatorSet animatorSet = new AnimatorSet();
                                                r5 = new Animator[8];
                                                r5[0] = ObjectAnimator.ofFloat(ChatActivity.this.instantCameraView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                                r5[1] = ObjectAnimator.ofFloat(cameraContainer, "scaleX", new float[]{imageWidth});
                                                r5[2] = ObjectAnimator.ofFloat(cameraContainer, "scaleY", new float[]{imageWidth});
                                                r5[3] = ObjectAnimator.ofFloat(cameraContainer, "translationX", new float[]{((float) iArr[0]) - r2.f10186x});
                                                r5[4] = ObjectAnimator.ofFloat(cameraContainer, "translationY", new float[]{((float) iArr[1]) - r2.f10187y});
                                                r5[5] = ObjectAnimator.ofFloat(ChatActivity.this.instantCameraView.getSwitchButtonView(), "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                                r5[6] = ObjectAnimator.ofInt(ChatActivity.this.instantCameraView.getPaint(), "alpha", new int[]{0});
                                                r5[7] = ObjectAnimator.ofFloat(ChatActivity.this.instantCameraView.getMuteImageView(), "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                                animatorSet.playTogether(r5);
                                                animatorSet.setDuration(180);
                                                animatorSet.setInterpolator(new DecelerateInterpolator());
                                                animatorSet.addListener(new AnimatorListenerAdapter() {

                                                    /* renamed from: org.telegram.ui.ChatActivity$ChatActivityAdapter$4$1$1 */
                                                    class C42811 extends AnimatorListenerAdapter {
                                                        C42811() {
                                                        }

                                                        public void onAnimationEnd(Animator animator) {
                                                            ChatActivity.this.instantCameraView.hideCamera(true);
                                                            ChatActivity.this.instantCameraView.setVisibility(4);
                                                        }
                                                    }

                                                    public void onAnimationEnd(Animator animator) {
                                                        AnimatorSet animatorSet = new AnimatorSet();
                                                        r1 = new Animator[2];
                                                        r1[0] = ObjectAnimator.ofFloat(cameraContainer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                                        r1[1] = ObjectAnimator.ofFloat(chatMessageCell, "alpha", new float[]{1.0f});
                                                        animatorSet.playTogether(r1);
                                                        animatorSet.setDuration(100);
                                                        animatorSet.setInterpolator(new DecelerateInterpolator());
                                                        animatorSet.addListener(new C42811());
                                                        animatorSet.start();
                                                    }
                                                });
                                                animatorSet.start();
                                                return true;
                                            }
                                        });
                                    }
                                }
                            }
                            chatMessageCell.setHighlightedText(null);
                            indexOf2 = ChatActivity.this.animatingMessageObjects.indexOf(messageObject);
                            if (indexOf2 != -1) {
                                ChatActivity.this.animatingMessageObjects.remove(indexOf2);
                                chatMessageCell.getViewTreeObserver().addOnPreDrawListener(/* anonymous class already generated */);
                            }
                        }
                    }
                    z = z3;
                    chatMessageCell.setMessageObject(messageObject, access$7400, z2, z);
                    ((ChatMessageCell) view).downloadAudioIfNeed();
                    if (ChatActivity.this.highlightMessageId == Integer.MAX_VALUE) {
                    }
                    chatMessageCell.setHighlighted(z);
                    if (messageObject.getDialogId() != ChatActivity.this.mergeDialogId) {
                    }
                    chatMessageCell.setHighlightedText(MessagesSearchQuery.getLastSearchQuery());
                    indexOf2 = ChatActivity.this.animatingMessageObjects.indexOf(messageObject);
                    if (indexOf2 != -1) {
                        ChatActivity.this.animatingMessageObjects.remove(indexOf2);
                        chatMessageCell.getViewTreeObserver().addOnPreDrawListener(/* anonymous class already generated */);
                    }
                } else if (view instanceof ChatActionCell) {
                    ChatActionCell chatActionCell = (ChatActionCell) view;
                    chatActionCell.setMessageObject(messageObject);
                    chatActionCell.setAlpha(1.0f);
                } else if (view instanceof ChatUnreadCell) {
                    ((ChatUnreadCell) view).setText(LocaleController.formatPluralString("NewMessages", ChatActivity.this.unread_to_load));
                    if (ChatActivity.this.createUnreadMessageAfterId != 0) {
                        ChatActivity.this.createUnreadMessageAfterId = 0;
                    }
                }
                if (messageObject != null && messageObject.messageOwner != null && messageObject.messageOwner.media_unread && messageObject.messageOwner.mentioned) {
                    if (!(messageObject.isVoice() || messageObject.isRoundVideo())) {
                        ChatActivity.this.newMentionsCount = ChatActivity.this.newMentionsCount - 1;
                        if (ChatActivity.this.newMentionsCount <= 0) {
                            ChatActivity.this.newMentionsCount = 0;
                            ChatActivity.this.hasAllMentionsLocal = true;
                            ChatActivity.this.showMentiondownButton(false, true);
                        } else {
                            ChatActivity.this.mentiondownButtonCounter.setText(String.format("%d", new Object[]{Integer.valueOf(ChatActivity.this.newMentionsCount)}));
                        }
                        MessagesController.getInstance().markMentionMessageAsRead(messageObject.getId(), ChatObject.isChannel(ChatActivity.this.currentChat) ? ChatActivity.this.currentChat.id : 0, ChatActivity.this.dialog_id);
                        messageObject.setContentIsRead();
                    }
                    if (view instanceof ChatMessageCell) {
                        ((ChatMessageCell) view).setHighlightedAnimated();
                    }
                }
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            if (i == 0) {
                if (ChatActivity.this.chatMessageCellsCache.isEmpty()) {
                    view = new ChatMessageCell(this.mContext);
                } else {
                    View view2 = (View) ChatActivity.this.chatMessageCellsCache.get(0);
                    ChatActivity.this.chatMessageCellsCache.remove(0);
                    view = view2;
                }
                ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                chatMessageCell.setDelegate(new C42781());
                if (ChatActivity.this.currentEncryptedChat == null) {
                    chatMessageCell.setAllowAssistant(true);
                }
            } else if (i == 1) {
                view = new ChatActionCell(this.mContext);
                ((ChatActionCell) view).setDelegate(new C42792());
            } else if (i == 2) {
                view = new ChatUnreadCell(this.mContext);
            } else if (i == 3) {
                view = new BotHelpCell(this.mContext);
                ((BotHelpCell) view).setDelegate(new C42803());
            } else if (i == 4) {
                view = new ChatLoadingCell(this.mContext);
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }

        public void onViewAttachedToWindow(ViewHolder viewHolder) {
            boolean z = true;
            if (viewHolder.itemView instanceof ChatMessageCell) {
                boolean z2;
                boolean z3;
                final ChatMessageCell chatMessageCell = (ChatMessageCell) viewHolder.itemView;
                MessageObject messageObject = chatMessageCell.getMessageObject();
                if (ChatActivity.this.actionBar.isActionModeShowed()) {
                    MessageObject editingMessageObject = ChatActivity.this.chatActivityEnterView != null ? ChatActivity.this.chatActivityEnterView.getEditingMessageObject() : null;
                    int i = messageObject.getDialogId() == ChatActivity.this.dialog_id ? 0 : 1;
                    if (editingMessageObject == messageObject || ChatActivity.this.selectedMessagesIds[i].containsKey(Integer.valueOf(messageObject.getId()))) {
                        ChatActivity.this.setCellSelectionBackground(messageObject, chatMessageCell, i);
                        z2 = true;
                    } else {
                        chatMessageCell.setBackgroundDrawable(null);
                        z2 = false;
                    }
                    z3 = z2;
                    z2 = true;
                } else {
                    chatMessageCell.setBackgroundDrawable(null);
                    z2 = false;
                    z3 = false;
                }
                boolean z4 = !z2;
                z2 = z2 && z3;
                chatMessageCell.setCheckPressed(z4, z2);
                chatMessageCell.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                    public boolean onPreDraw() {
                        chatMessageCell.getViewTreeObserver().removeOnPreDrawListener(this);
                        int measuredHeight = ChatActivity.this.chatListView.getMeasuredHeight();
                        int top = chatMessageCell.getTop();
                        chatMessageCell.getBottom();
                        top = top >= 0 ? 0 : -top;
                        int measuredHeight2 = chatMessageCell.getMeasuredHeight();
                        if (measuredHeight2 > measuredHeight) {
                            measuredHeight2 = top + measuredHeight;
                        }
                        chatMessageCell.setVisiblePart(top, measuredHeight2 - top);
                        return true;
                    }
                });
                if (ChatActivity.this.highlightMessageId == Integer.MAX_VALUE || chatMessageCell.getMessageObject().getId() != ChatActivity.this.highlightMessageId) {
                    z = false;
                }
                chatMessageCell.setHighlighted(z);
            }
        }

        public void updateRowWithMessageObject(MessageObject messageObject) {
            int indexOf = ChatActivity.this.messages.indexOf(messageObject);
            if (indexOf != -1) {
                notifyItemChanged(indexOf + this.messagesStartRow);
            }
        }

        public void updateRows() {
            int i;
            this.rowCount = 0;
            if (ChatActivity.this.messages.isEmpty()) {
                this.loadingUpRow = -1;
                this.loadingDownRow = -1;
                this.messagesStartRow = -1;
                this.messagesEndRow = -1;
            } else {
                if (ChatActivity.this.forwardEndReached[0] && (ChatActivity.this.mergeDialogId == 0 || ChatActivity.this.forwardEndReached[1])) {
                    this.loadingDownRow = -1;
                } else {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.loadingDownRow = i;
                }
                this.messagesStartRow = this.rowCount;
                this.rowCount += ChatActivity.this.messages.size();
                this.messagesEndRow = this.rowCount;
                if (ChatActivity.this.endReached[0] && (ChatActivity.this.mergeDialogId == 0 || ChatActivity.this.endReached[1])) {
                    this.loadingUpRow = -1;
                } else {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.loadingUpRow = i;
                }
            }
            if (ChatActivity.this.currentUser == null || !ChatActivity.this.currentUser.bot) {
                this.botInfoRow = -1;
                return;
            }
            i = this.rowCount;
            this.rowCount = i + 1;
            this.botInfoRow = i;
        }
    }

    public ChatActivity(Bundle bundle) {
        super(bundle);
    }

    private void addInviteBoxToUser(Context context, SizeNotifierFrameLayout sizeNotifierFrameLayout) {
        try {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(Integer.parseInt(this.dialog_id + TtmlNode.ANONYMOUS_REGION_ID)));
            if (!this.isAdvancedForward && user != null) {
                this.inviteRootView = LayoutInflater.from(context).inflate(R.layout.invite_to_user, sizeNotifierFrameLayout, false);
                TextView textView = (TextView) this.inviteRootView.findViewById(R.id.tv);
                this.inviteRootView.findViewById(R.id.ll_container).setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
                textView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
                textView.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
                this.inviteRootView.findViewById(R.id.ll_container).setOnClickListener(new View.OnClickListener() {

                    /* renamed from: org.telegram.ui.ChatActivity$130$1 */
                    class C42311 implements OnClickListener {
                        C42311() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }

                    /* renamed from: org.telegram.ui.ChatActivity$130$2 */
                    class C42322 implements OnClickListener {
                        C42322() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            ChatActivity.this.sendMessage(C3791b.j(ChatActivity.this.getParentActivity()));
                        }
                    }

                    public void onClick(View view) {
                        new AlertDialog.Builder(ChatActivity.this.getParentActivity()).setMessage("برای ارسال دعوتنامه مطمین هستید؟").setPositiveButton("بله", new C42322()).setNegativeButton("خیر", new C42311()).setIcon(17301543).show();
                        C2666a.getDatabaseHandler().a(ChatActivity.this.dialog_id, true);
                        ChatActivity.this.inviteRootView.setVisibility(8);
                    }
                });
                this.inviteBoxAdded = true;
                sizeNotifierFrameLayout.addView(this.inviteRootView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addQuickAccessToUsers(Context context, SizeNotifierFrameLayout sizeNotifierFrameLayout) {
        if (!this.isAdvancedForward) {
            final View inflate = LayoutInflater.from(context).inflate(R.layout.quick_access_to_users, sizeNotifierFrameLayout, false);
            final RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler);
            recyclerView.setLayoutManager(new android.support.v7.widget.LinearLayoutManager(context, 0, false));
            recyclerView.setAdapter(new C2663k(new C2661a() {
                public void onClick(User user, Chat chat) {
                    ChatActivity.this.translateQuickAccess(inflate, recyclerView, false);
                    if (user != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("user_id", user.id);
                        if (MessagesController.checkCanOpenChat(bundle, ChatActivity.this)) {
                            ChatActivity.this.presentFragment(new ChatActivity(bundle), false);
                        }
                    } else if (chat != null) {
                        C2879f.a((long) chat.id, 0, ChatActivity.this.getParentActivity(), chat.username);
                    }
                }
            }));
            inflate.findViewById(R.id.openDialog).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.d("LEE", "Click Quick");
                    ChatActivity.this.translateQuickAccess(inflate, recyclerView, false);
                }
            });
            this.isShowingQuickAccess = false;
            try {
                ((ImageView) inflate.findViewById(R.id.openDialogMark)).setImageResource(this.isShowingQuickAccess ? R.drawable.opmu : R.drawable.opmd);
                inflate.post(new Runnable() {
                    public void run() {
                        try {
                            inflate.setY(inflate.getY() - ((float) recyclerView.getHeight()));
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }
            sizeNotifierFrameLayout.addView(inflate);
        }
    }

    private void addToSelectedMessages(MessageObject messageObject, boolean z) {
        addToSelectedMessages(messageObject, z, true);
    }

    private void addToSelectedMessages(MessageObject messageObject, boolean z, boolean z2) {
        int i = messageObject.getDialogId() == this.dialog_id ? 0 : 1;
        GroupedMessages groupedMessages;
        int visibility;
        if (!z || messageObject.getGroupId() == 0) {
            if (this.selectedMessagesIds[i].containsKey(Integer.valueOf(messageObject.getId()))) {
                this.selectedMessagesIds[i].remove(Integer.valueOf(messageObject.getId()));
                if (messageObject.type == 0 || messageObject.caption != null) {
                    this.selectedMessagesCanCopyIds[i].remove(Integer.valueOf(messageObject.getId()));
                }
                if (messageObject.isSticker()) {
                    this.selectedMessagesCanStarIds[i].remove(Integer.valueOf(messageObject.getId()));
                }
                if (messageObject.canEditMessage(this.currentChat) && messageObject.getGroupId() != 0) {
                    groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject.getGroupId()));
                    if (groupedMessages != null && groupedMessages.messages.size() > 1) {
                        this.canEditMessagesCount--;
                    }
                }
                if (!messageObject.canDeleteMessage(this.currentChat)) {
                    this.cantDeleteMessagesCount--;
                }
            } else if (this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size() < 100) {
                this.selectedMessagesIds[i].put(Integer.valueOf(messageObject.getId()), messageObject);
                if (messageObject.type == 0 || messageObject.caption != null) {
                    this.selectedMessagesCanCopyIds[i].put(Integer.valueOf(messageObject.getId()), messageObject);
                }
                if (messageObject.isSticker()) {
                    this.selectedMessagesCanStarIds[i].put(Integer.valueOf(messageObject.getId()), messageObject);
                }
                if (messageObject.canEditMessage(this.currentChat) && messageObject.getGroupId() != 0) {
                    groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject.getGroupId()));
                    if (groupedMessages != null && groupedMessages.messages.size() > 1) {
                        this.canEditMessagesCount++;
                    }
                }
                if (!messageObject.canDeleteMessage(this.currentChat)) {
                    this.cantDeleteMessagesCount++;
                }
            } else {
                return;
            }
            if (z2 && this.actionBar.isActionModeShowed()) {
                int size = this.selectedMessagesIds[1].size() + this.selectedMessagesIds[0].size();
                if (size == 0) {
                    this.actionBar.hideActionMode();
                    updatePinnedMessageView(true);
                    this.startReplyOnTextChange = false;
                    return;
                }
                ActionBarMenuItem item = this.actionBar.createActionMode().getItem(10);
                ActionBarMenuItem item2 = this.actionBar.createActionMode().getItem(22);
                ActionBarMenuItem item3 = this.actionBar.createActionMode().getItem(23);
                final ActionBarMenuItem item4 = this.actionBar.createActionMode().getItem(19);
                i = item.getVisibility();
                int visibility2 = item2.getVisibility();
                item.setVisibility(this.selectedMessagesCanCopyIds[0].size() + this.selectedMessagesCanCopyIds[1].size() != 0 ? 0 : 8);
                int i2 = (StickersQuery.canAddStickerToFavorites() && this.selectedMessagesCanStarIds[0].size() + this.selectedMessagesCanStarIds[1].size() == size) ? 0 : 8;
                item2.setVisibility(i2);
                visibility = item.getVisibility();
                int visibility3 = item2.getVisibility();
                this.actionBar.createActionMode().getItem(12).setVisibility(this.cantDeleteMessagesCount == 0 ? 0 : 8);
                if (item3 != null) {
                    i2 = (this.canEditMessagesCount == 1 && this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size() == 1) ? 0 : 8;
                    item3.setVisibility(i2);
                }
                this.hasUnfavedSelected = false;
                for (size = 0; size < 2; size++) {
                    for (Entry value : this.selectedMessagesCanStarIds[size].entrySet()) {
                        if (!StickersQuery.isStickerInFavorites(((MessageObject) value.getValue()).getDocument())) {
                            this.hasUnfavedSelected = true;
                            break;
                        }
                    }
                    if (this.hasUnfavedSelected) {
                        break;
                    }
                }
                item2.setIcon(this.hasUnfavedSelected ? R.drawable.ic_ab_fave : R.drawable.ic_ab_unfave);
                if (item4 != null) {
                    Object obj = 1;
                    if ((this.currentEncryptedChat != null && AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) < 46) || this.isBroadcast || ((this.bottomOverlayChat != null && this.bottomOverlayChat.getVisibility() == 0) || (this.currentChat != null && (ChatObject.isNotInChat(this.currentChat) || !((!ChatObject.isChannel(this.currentChat) || ChatObject.canPost(this.currentChat) || this.currentChat.megagroup) && ChatObject.canSendMessages(this.currentChat)))))) {
                        obj = null;
                    }
                    size = (obj == null || this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size() != 1) ? 8 : 0;
                    boolean z3 = size == 0 && !this.chatActivityEnterView.hasText();
                    this.startReplyOnTextChange = z3;
                    if (item4.getVisibility() != size) {
                        if (this.replyButtonAnimation != null) {
                            this.replyButtonAnimation.cancel();
                        }
                        if (i == visibility && visibility2 == visibility3) {
                            this.replyButtonAnimation = new AnimatorSet();
                            item4.setPivotX((float) AndroidUtilities.dp(54.0f));
                            item3.setPivotX((float) AndroidUtilities.dp(54.0f));
                            AnimatorSet animatorSet;
                            Animator[] animatorArr;
                            if (size == 0) {
                                item4.setVisibility(size);
                                animatorSet = this.replyButtonAnimation;
                                animatorArr = new Animator[4];
                                animatorArr[0] = ObjectAnimator.ofFloat(item4, "alpha", new float[]{1.0f});
                                animatorArr[1] = ObjectAnimator.ofFloat(item4, "scaleX", new float[]{1.0f});
                                animatorArr[2] = ObjectAnimator.ofFloat(item3, "alpha", new float[]{1.0f});
                                animatorArr[3] = ObjectAnimator.ofFloat(item3, "scaleX", new float[]{1.0f});
                                animatorSet.playTogether(animatorArr);
                            } else {
                                animatorSet = this.replyButtonAnimation;
                                animatorArr = new Animator[4];
                                animatorArr[0] = ObjectAnimator.ofFloat(item4, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                animatorArr[1] = ObjectAnimator.ofFloat(item4, "scaleX", new float[]{BitmapDescriptorFactory.HUE_RED});
                                animatorArr[2] = ObjectAnimator.ofFloat(item3, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                animatorArr[3] = ObjectAnimator.ofFloat(item3, "scaleX", new float[]{BitmapDescriptorFactory.HUE_RED});
                                animatorSet.playTogether(animatorArr);
                            }
                            this.replyButtonAnimation.setDuration(100);
                            this.replyButtonAnimation.addListener(new AnimatorListenerAdapter() {
                                public void onAnimationCancel(Animator animator) {
                                    if (ChatActivity.this.replyButtonAnimation != null && ChatActivity.this.replyButtonAnimation.equals(animator)) {
                                        ChatActivity.this.replyButtonAnimation = null;
                                    }
                                }

                                public void onAnimationEnd(Animator animator) {
                                    if (ChatActivity.this.replyButtonAnimation != null && ChatActivity.this.replyButtonAnimation.equals(animator) && size == 8) {
                                        item4.setVisibility(8);
                                    }
                                }
                            });
                            this.replyButtonAnimation.start();
                            return;
                        }
                        if (size == 0) {
                            item4.setAlpha(1.0f);
                            item4.setScaleX(1.0f);
                        } else {
                            item4.setAlpha(BitmapDescriptorFactory.HUE_RED);
                            item4.setScaleX(BitmapDescriptorFactory.HUE_RED);
                        }
                        item4.setVisibility(size);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject.getGroupId()));
        if (groupedMessages != null) {
            Object obj2 = null;
            int i3 = 0;
            for (visibility = 0; visibility < groupedMessages.messages.size(); visibility++) {
                if (!this.selectedMessagesIds[i].containsKey(Integer.valueOf(((MessageObject) groupedMessages.messages.get(visibility)).getId()))) {
                    obj2 = 1;
                    i3 = visibility;
                }
            }
            visibility = 0;
            while (visibility < groupedMessages.messages.size()) {
                MessageObject messageObject2 = (MessageObject) groupedMessages.messages.get(visibility);
                if (obj2 == null) {
                    addToSelectedMessages(messageObject2, false, visibility == groupedMessages.messages.size() + -1);
                } else if (!this.selectedMessagesIds[i].containsKey(Integer.valueOf(messageObject2.getId()))) {
                    addToSelectedMessages(messageObject2, false, visibility == i3);
                }
                visibility++;
            }
        }
    }

    private void alertUserOpenError(MessageObject messageObject) {
        if (getParentActivity() != null) {
            org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            if (messageObject.type == 3) {
                builder.setMessage(LocaleController.getString("NoPlayerInstalled", R.string.NoPlayerInstalled));
            } else {
                builder.setMessage(LocaleController.formatString("NoHandleAppInstalled", R.string.NoHandleAppInstalled, new Object[]{messageObject.getDocument().mime_type}));
            }
            showDialog(builder.create());
        }
    }

    private void applyDraftMaybe(boolean z) {
        if (this.chatActivityEnterView != null) {
            DraftMessage draft = DraftQuery.getDraft(this.dialog_id);
            Message draftMessage = (draft == null || draft.reply_to_msg_id == 0) ? null : DraftQuery.getDraftMessage(this.dialog_id);
            if (this.chatActivityEnterView.getFieldText() == null) {
                if (draft != null) {
                    CharSequence charSequence;
                    this.chatActivityEnterView.setWebPage(null, !draft.no_webpage);
                    if (draft.entities.isEmpty()) {
                        charSequence = draft.message;
                    } else {
                        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(draft.message);
                        MessagesQuery.sortEntities(draft.entities);
                        int i = 0;
                        int i2 = 0;
                        while (i < draft.entities.size()) {
                            int i3;
                            MessageEntity messageEntity = (MessageEntity) draft.entities.get(i);
                            if ((messageEntity instanceof TLRPC$TL_inputMessageEntityMentionName) || (messageEntity instanceof TLRPC$TL_messageEntityMentionName)) {
                                i3 = messageEntity instanceof TLRPC$TL_inputMessageEntityMentionName ? ((TLRPC$TL_inputMessageEntityMentionName) messageEntity).user_id.user_id : ((TLRPC$TL_messageEntityMentionName) messageEntity).user_id;
                                if ((messageEntity.offset + i2) + messageEntity.length < valueOf.length() && valueOf.charAt((messageEntity.offset + i2) + messageEntity.length) == ' ') {
                                    messageEntity.length++;
                                }
                                valueOf.setSpan(new URLSpanUserMention(TtmlNode.ANONYMOUS_REGION_ID + i3, true), messageEntity.offset + i2, messageEntity.length + (messageEntity.offset + i2), 33);
                                i3 = i2;
                            } else if (messageEntity instanceof TLRPC$TL_messageEntityCode) {
                                valueOf.insert((messageEntity.offset + messageEntity.length) + i2, "`");
                                valueOf.insert(messageEntity.offset + i2, "`");
                                i3 = i2 + 2;
                            } else if (messageEntity instanceof TLRPC$TL_messageEntityPre) {
                                valueOf.insert((messageEntity.offset + messageEntity.length) + i2, "```");
                                valueOf.insert(messageEntity.offset + i2, "```");
                                i3 = i2 + 6;
                            } else if (messageEntity instanceof TLRPC$TL_messageEntityBold) {
                                valueOf.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), messageEntity.offset + i2, (messageEntity.length + messageEntity.offset) + i2, 33);
                                i3 = i2;
                            } else {
                                if (messageEntity instanceof TLRPC$TL_messageEntityItalic) {
                                    valueOf.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/ritalic.ttf")), messageEntity.offset + i2, (messageEntity.length + messageEntity.offset) + i2, 33);
                                }
                                i3 = i2;
                            }
                            i++;
                            i2 = i3;
                        }
                        charSequence = valueOf;
                    }
                    this.chatActivityEnterView.setFieldText(charSequence);
                    if (getArguments().getBoolean("hasUrl", false)) {
                        this.chatActivityEnterView.setSelection(draft.message.indexOf(10) + 1);
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (ChatActivity.this.chatActivityEnterView != null) {
                                    ChatActivity.this.chatActivityEnterView.setFieldFocused(true);
                                    ChatActivity.this.chatActivityEnterView.openKeyboard();
                                }
                            }
                        }, 700);
                    }
                }
            } else if (z && draft == null) {
                this.chatActivityEnterView.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
                showReplyPanel(false, null, null, null, false);
            }
            if (this.replyingMessageObject == null && draftMessage != null) {
                this.replyingMessageObject = new MessageObject(draftMessage, MessagesController.getInstance().getUsers(), false);
                showReplyPanel(true, this.replyingMessageObject, null, null, false);
            }
        }
    }

    private void checkActionBarMenu() {
        if ((this.currentEncryptedChat == null || (this.currentEncryptedChat instanceof TLRPC$TL_encryptedChat)) && ((this.currentChat == null || !ChatObject.isNotInChat(this.currentChat)) && (this.currentUser == null || !UserObject.isDeleted(this.currentUser)))) {
            if (this.timeItem2 != null) {
                this.timeItem2.setVisibility(0);
            }
            if (this.avatarContainer != null) {
                this.avatarContainer.showTimeItem();
            }
        } else {
            if (this.timeItem2 != null) {
                this.timeItem2.setVisibility(8);
            }
            if (this.avatarContainer != null) {
                this.avatarContainer.hideTimeItem();
            }
        }
        if (!(this.avatarContainer == null || this.currentEncryptedChat == null)) {
            this.avatarContainer.setTime(this.currentEncryptedChat.ttl);
        }
        checkAndUpdateAvatar();
    }

    private void checkAndUpdateAvatar() {
        if (this.currentUser != null) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(this.currentUser.id));
            if (user != null) {
                this.currentUser = user;
            } else {
                return;
            }
        } else if (this.currentChat != null) {
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.currentChat.id));
            if (chat != null) {
                this.currentChat = chat;
            } else {
                return;
            }
        }
        if (!this.isDownloadManager && this.avatarContainer != null) {
            this.avatarContainer.checkAndUpdateAvatar();
        }
    }

    private void checkBotCommands() {
        boolean z = false;
        URLSpanBotCommand.enabled = false;
        if (this.currentUser != null && this.currentUser.bot) {
            URLSpanBotCommand.enabled = true;
        } else if (this.info instanceof TL_chatFull) {
            int i = 0;
            while (i < this.info.participants.participants.size()) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(((ChatParticipant) this.info.participants.participants.get(i)).user_id));
                if (user == null || !user.bot) {
                    i++;
                } else {
                    URLSpanBotCommand.enabled = true;
                    return;
                }
            }
        } else if (this.info instanceof TL_channelFull) {
            if (!(this.info.bot_info.isEmpty() || this.currentChat == null || !this.currentChat.megagroup)) {
                z = true;
            }
            URLSpanBotCommand.enabled = z;
        }
    }

    private void checkContextBotPanel() {
        if (!this.allowStickersPanel || this.mentionsAdapter == null || !this.mentionsAdapter.isBotContext()) {
            return;
        }
        if (this.allowContextBotPanel || this.allowContextBotPanelSecond) {
            if (this.mentionContainer.getVisibility() == 4 || this.mentionContainer.getTag() != null) {
                if (this.mentionListAnimation != null) {
                    this.mentionListAnimation.cancel();
                }
                this.mentionContainer.setTag(null);
                this.mentionContainer.setVisibility(0);
                this.mentionListAnimation = new AnimatorSet();
                this.mentionListAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.mentionContainer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
                this.mentionListAnimation.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationCancel(Animator animator) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animator)) {
                            ChatActivity.this.mentionListAnimation = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animator)) {
                            ChatActivity.this.mentionListAnimation = null;
                        }
                    }
                });
                this.mentionListAnimation.setDuration(200);
                this.mentionListAnimation.start();
            }
        } else if (this.mentionContainer.getVisibility() == 0 && this.mentionContainer.getTag() == null) {
            if (this.mentionListAnimation != null) {
                this.mentionListAnimation.cancel();
            }
            this.mentionContainer.setTag(Integer.valueOf(1));
            this.mentionListAnimation = new AnimatorSet();
            AnimatorSet animatorSet = this.mentionListAnimation;
            Animator[] animatorArr = new Animator[1];
            animatorArr[0] = ObjectAnimator.ofFloat(this.mentionContainer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorSet.playTogether(animatorArr);
            this.mentionListAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animator)) {
                        ChatActivity.this.mentionListAnimation = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animator)) {
                        ChatActivity.this.mentionContainer.setVisibility(4);
                        ChatActivity.this.mentionListAnimation = null;
                    }
                }
            });
            this.mentionListAnimation.setDuration(200);
            this.mentionListAnimation.start();
        }
    }

    private void checkEditTimer() {
        if (this.chatActivityEnterView != null) {
            MessageObject editingMessageObject = this.chatActivityEnterView.getEditingMessageObject();
            if (editingMessageObject == null) {
                return;
            }
            if (this.currentUser == null || !this.currentUser.self) {
                int abs = editingMessageObject.canEditMessageAnytime(this.currentChat) ? 360 : (MessagesController.getInstance().maxEditTime + add_fave_dialog) - Math.abs(ConnectionsManager.getInstance().getCurrentTime() - editingMessageObject.messageOwner.date);
                if (abs > 0) {
                    if (abs <= add_fave_dialog) {
                        if (this.actionModeSubTextView.getVisibility() != 0) {
                            this.actionModeSubTextView.setVisibility(0);
                        }
                        SimpleTextView simpleTextView = this.actionModeSubTextView;
                        Object[] objArr = new Object[1];
                        objArr[0] = String.format("%d:%02d", new Object[]{Integer.valueOf(abs / 60), Integer.valueOf(abs % 60)});
                        simpleTextView.setText(LocaleController.formatString("TimeToEdit", R.string.TimeToEdit, objArr));
                    } else if (this.actionModeSubTextView.getVisibility() != 8) {
                        this.actionModeSubTextView.setVisibility(8);
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            ChatActivity.this.checkEditTimer();
                        }
                    }, 1000);
                    return;
                }
                this.chatActivityEnterView.onEditTimeExpired();
                this.actionModeSubTextView.setText(LocaleController.formatString("TimeToEditExpired", R.string.TimeToEditExpired, new Object[0]));
            } else if (this.actionModeSubTextView.getVisibility() != 8) {
                this.actionModeSubTextView.setVisibility(8);
            }
        }
    }

    private void checkListViewPaddings() {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    int i2;
                    int findFirstVisibleItemPosition = ChatActivity.this.chatLayoutManager.findFirstVisibleItemPosition();
                    if (findFirstVisibleItemPosition != -1) {
                        View findViewByPosition = ChatActivity.this.chatLayoutManager.findViewByPosition(findFirstVisibleItemPosition);
                        if (findViewByPosition != null) {
                            i = (ChatActivity.this.chatListView.getMeasuredHeight() - findViewByPosition.getBottom()) - ChatActivity.this.chatListView.getPaddingBottom();
                        }
                        i2 = i;
                    } else {
                        i2 = 0;
                    }
                    FrameLayout.LayoutParams layoutParams;
                    if (ChatActivity.this.chatListView.getPaddingTop() != AndroidUtilities.dp(52.0f) && ((ChatActivity.this.pinnedMessageView != null && ChatActivity.this.pinnedMessageView.getTag() == null) || (ChatActivity.this.reportSpamView != null && ChatActivity.this.reportSpamView.getTag() == null))) {
                        ChatActivity.this.chatListView.setPadding(0, AndroidUtilities.dp(52.0f), 0, AndroidUtilities.dp(3.0f));
                        layoutParams = (FrameLayout.LayoutParams) ChatActivity.this.floatingDateView.getLayoutParams();
                        layoutParams.topMargin = AndroidUtilities.dp(52.0f);
                        ChatActivity.this.floatingDateView.setLayoutParams(layoutParams);
                        ChatActivity.this.chatListView.setTopGlowOffset(AndroidUtilities.dp(48.0f));
                        i = findFirstVisibleItemPosition;
                    } else if (ChatActivity.this.chatListView.getPaddingTop() == AndroidUtilities.dp(4.0f) || ((ChatActivity.this.pinnedMessageView != null && ChatActivity.this.pinnedMessageView.getTag() == null) || (ChatActivity.this.reportSpamView != null && ChatActivity.this.reportSpamView.getTag() == null))) {
                        i = -1;
                    } else {
                        ChatActivity.this.chatListView.setPadding(0, AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(3.0f));
                        layoutParams = (FrameLayout.LayoutParams) ChatActivity.this.floatingDateView.getLayoutParams();
                        layoutParams.topMargin = AndroidUtilities.dp(4.0f);
                        ChatActivity.this.floatingDateView.setLayoutParams(layoutParams);
                        ChatActivity.this.chatListView.setTopGlowOffset(0);
                        i = findFirstVisibleItemPosition;
                    }
                    if (i != -1) {
                        ChatActivity.this.chatLayoutManager.scrollToPositionWithOffset(i, i2);
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private void checkRaiseSensors() {
        if (ChatObject.isChannel(this.currentChat) && this.currentChat.banned_rights != null && this.currentChat.banned_rights.send_media) {
            MediaController.getInstance().setAllowStartRecord(false);
        } else if (ApplicationLoader.mainInterfacePaused || ((this.bottomOverlayChat != null && this.bottomOverlayChat.getVisibility() == 0) || ((this.bottomOverlay != null && this.bottomOverlay.getVisibility() == 0) || (this.searchContainer != null && this.searchContainer.getVisibility() == 0)))) {
            MediaController.getInstance().setAllowStartRecord(false);
        } else {
            MediaController.getInstance().setAllowStartRecord(true);
        }
    }

    private void checkScrollForLoad(boolean z) {
        if (this.chatLayoutManager != null && !this.paused && !this.isAdvancedForward) {
            int findFirstVisibleItemPosition = this.chatLayoutManager.findFirstVisibleItemPosition();
            int abs = findFirstVisibleItemPosition == -1 ? 0 : Math.abs(this.chatLayoutManager.findLastVisibleItemPosition() - findFirstVisibleItemPosition) + 1;
            if (abs > 0 || this.currentEncryptedChat != null) {
                MessagesController instance;
                long j;
                int i;
                int i2;
                int i3;
                boolean isChannel;
                int i4;
                if ((this.chatAdapter.getItemCount() - findFirstVisibleItemPosition) - abs <= (z ? 25 : 5) && !this.loading) {
                    boolean z2;
                    if (!this.endReached[0]) {
                        this.loading = true;
                        this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                        if (this.messagesByDays.size() != 0) {
                            instance = MessagesController.getInstance();
                            j = this.dialog_id;
                            i = this.maxMessageId[0];
                            z2 = !this.cacheEndReached[0];
                            i2 = this.minDate[0];
                            i3 = this.classGuid;
                            isChannel = ChatObject.isChannel(this.currentChat);
                            i4 = this.lastLoadIndex;
                            this.lastLoadIndex = i4 + 1;
                            instance.loadMessages(j, 50, i, 0, z2, i2, i3, 0, 0, isChannel, i4);
                        } else {
                            instance = MessagesController.getInstance();
                            j = this.dialog_id;
                            z2 = !this.cacheEndReached[0];
                            i2 = this.minDate[0];
                            i3 = this.classGuid;
                            isChannel = ChatObject.isChannel(this.currentChat);
                            i4 = this.lastLoadIndex;
                            this.lastLoadIndex = i4 + 1;
                            instance.loadMessages(j, 50, 0, 0, z2, i2, i3, 0, 0, isChannel, i4);
                        }
                    } else if (!(this.mergeDialogId == 0 || this.endReached[1])) {
                        this.loading = true;
                        this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                        instance = MessagesController.getInstance();
                        j = this.mergeDialogId;
                        i = this.maxMessageId[1];
                        z2 = !this.cacheEndReached[1];
                        i2 = this.minDate[1];
                        i3 = this.classGuid;
                        isChannel = ChatObject.isChannel(this.currentChat);
                        i4 = this.lastLoadIndex;
                        this.lastLoadIndex = i4 + 1;
                        instance.loadMessages(j, 50, i, 0, z2, i2, i3, 0, 0, isChannel, i4);
                    }
                }
                if (abs > 0 && !this.loadingForward && findFirstVisibleItemPosition <= 10) {
                    if (this.mergeDialogId != 0 && !this.forwardEndReached[1]) {
                        this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                        instance = MessagesController.getInstance();
                        j = this.mergeDialogId;
                        i = this.minMessageId[1];
                        i2 = this.maxDate[1];
                        i3 = this.classGuid;
                        isChannel = ChatObject.isChannel(this.currentChat);
                        i4 = this.lastLoadIndex;
                        this.lastLoadIndex = i4 + 1;
                        instance.loadMessages(j, 50, i, 0, true, i2, i3, 1, 0, isChannel, i4);
                        this.loadingForward = true;
                    } else if (!this.forwardEndReached[0]) {
                        this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                        instance = MessagesController.getInstance();
                        j = this.dialog_id;
                        i = this.minMessageId[0];
                        i2 = this.maxDate[0];
                        i3 = this.classGuid;
                        isChannel = ChatObject.isChannel(this.currentChat);
                        i4 = this.lastLoadIndex;
                        this.lastLoadIndex = i4 + 1;
                        instance.loadMessages(j, 50, i, 0, true, i2, i3, 1, 0, isChannel, i4);
                        this.loadingForward = true;
                    }
                }
            }
        }
    }

    private void clearChatData() {
        this.messages.clear();
        this.messagesByDays.clear();
        this.waitingForLoad.clear();
        this.groupedMessagesMap.clear();
        this.progressView.setVisibility(this.chatAdapter.botInfoRow == -1 ? 0 : 4);
        this.chatListView.setEmptyView(null);
        for (int i = 0; i < 2; i++) {
            this.messagesDict[i].clear();
            if (this.currentEncryptedChat == null) {
                this.maxMessageId[i] = Integer.MAX_VALUE;
                this.minMessageId[i] = Integer.MIN_VALUE;
            } else {
                this.maxMessageId[i] = Integer.MIN_VALUE;
                this.minMessageId[i] = Integer.MAX_VALUE;
            }
            this.maxDate[i] = Integer.MIN_VALUE;
            this.minDate[i] = 0;
            this.endReached[i] = false;
            this.cacheEndReached[i] = false;
            this.forwardEndReached[i] = true;
        }
        this.first = true;
        this.firstLoading = true;
        this.loading = true;
        this.loadingForward = false;
        this.waitingForReplyMessageLoad = false;
        this.startLoadFromMessageId = 0;
        this.last_message_id = 0;
        this.unreadMessageObject = null;
        this.createUnreadMessageAfterId = 0;
        this.createUnreadMessageAfterIdLoading = false;
        this.needSelectFromMessageId = false;
        this.chatAdapter.notifyDataSetChanged();
    }

    private void createChatAttachView() {
        if (getParentActivity() != null && this.chatAttachAlert == null) {
            this.chatAttachAlert = new ChatAttachAlert(getParentActivity(), this);
            this.chatAttachAlert.setDelegate(new ChatAttachViewDelegate() {
                public void didPressedButton(int i) {
                    if (ChatActivity.this.getParentActivity() != null && ChatActivity.this.chatAttachAlert != null) {
                        if (i == 7 || (i == 4 && !ChatActivity.this.chatAttachAlert.getSelectedPhotos().isEmpty())) {
                            ChatActivity.this.chatAttachAlert.dismiss();
                            HashMap selectedPhotos = ChatActivity.this.chatAttachAlert.getSelectedPhotos();
                            ArrayList selectedPhotosOrder = ChatActivity.this.chatAttachAlert.getSelectedPhotosOrder();
                            if (!selectedPhotos.isEmpty()) {
                                ArrayList arrayList = new ArrayList();
                                for (int i2 = 0; i2 < selectedPhotosOrder.size(); i2++) {
                                    PhotoEntry photoEntry = (PhotoEntry) selectedPhotos.get(selectedPhotosOrder.get(i2));
                                    SendingMediaInfo sendingMediaInfo = new SendingMediaInfo();
                                    if (photoEntry.imagePath != null) {
                                        sendingMediaInfo.path = photoEntry.imagePath;
                                    } else if (photoEntry.path != null) {
                                        sendingMediaInfo.path = photoEntry.path;
                                    }
                                    sendingMediaInfo.isVideo = photoEntry.isVideo;
                                    sendingMediaInfo.caption = photoEntry.caption != null ? photoEntry.caption.toString() : null;
                                    sendingMediaInfo.masks = !photoEntry.stickers.isEmpty() ? new ArrayList(photoEntry.stickers) : null;
                                    sendingMediaInfo.ttl = photoEntry.ttl;
                                    sendingMediaInfo.videoEditedInfo = photoEntry.editedInfo;
                                    arrayList.add(sendingMediaInfo);
                                    photoEntry.reset();
                                }
                                SendMessagesHelper.prepareSendingMedia(arrayList, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject, null, i == 4, MediaController.getInstance().isGroupPhotosEnabled());
                                ChatActivity.this.showReplyPanel(false, null, null, null, false);
                                DraftQuery.cleanDraft(ChatActivity.this.dialog_id, true);
                                return;
                            }
                            return;
                        }
                        if (ChatActivity.this.chatAttachAlert != null) {
                            ChatActivity.this.chatAttachAlert.dismissWithButtonClick(i);
                        }
                        ChatActivity.this.processSelectedAttach(i);
                    }
                }

                public void didSelectBot(User user) {
                    if (ChatActivity.this.chatActivityEnterView != null && !TextUtils.isEmpty(user.username)) {
                        ChatActivity.this.chatActivityEnterView.setFieldText("@" + user.username + " ");
                        ChatActivity.this.chatActivityEnterView.openKeyboard();
                    }
                }

                public View getRevealView() {
                    return ChatActivity.this.chatActivityEnterView.getAttachButton();
                }

                public void onCameraOpened() {
                    ChatActivity.this.chatActivityEnterView.closeKeyboard();
                }
            });
        }
    }

    private void createDeleteMessagesAlert(MessageObject messageObject, GroupedMessages groupedMessages) {
        createDeleteMessagesAlert(messageObject, groupedMessages, 1);
    }

    private void createDeleteMessagesAlert(MessageObject messageObject, GroupedMessages groupedMessages, int i) {
        if (getParentActivity() != null) {
            User user;
            org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
            int size = groupedMessages != null ? groupedMessages.messages.size() : messageObject != null ? 1 : this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size();
            builder.setMessage(LocaleController.formatString("AreYouSureDeleteMessages", R.string.AreYouSureDeleteMessages, new Object[]{LocaleController.formatPluralString("messages", size)}));
            builder.setTitle(LocaleController.getString("Message", R.string.Message));
            final boolean[] zArr = new boolean[3];
            boolean[] zArr2 = new boolean[1];
            Object obj;
            int currentTime;
            Object obj2;
            int i2;
            Object obj3;
            MessageObject messageObject2;
            View checkBoxCell;
            final boolean[] zArr3;
            if (this.currentChat == null || !this.currentChat.megagroup) {
                if (!ChatObject.isChannel(this.currentChat) && this.currentEncryptedChat == null) {
                    obj = null;
                    currentTime = ConnectionsManager.getInstance().getCurrentTime();
                    if ((this.currentUser != null && this.currentUser.id != UserConfig.getClientUserId() && !this.currentUser.bot) || this.currentChat != null) {
                        if (messageObject == null) {
                            obj2 = null;
                            i2 = 1;
                            while (i2 >= 0) {
                                obj3 = obj;
                                for (Entry value : this.selectedMessagesIds[i2].entrySet()) {
                                    messageObject2 = (MessageObject) value.getValue();
                                    if (messageObject2.messageOwner.action == null) {
                                        if (!messageObject2.isOut() && (this.currentChat == null || (!this.currentChat.creator && (!this.currentChat.admin || !this.currentChat.admins_enabled)))) {
                                            obj = null;
                                            obj3 = 1;
                                            break;
                                        }
                                        obj = (obj3 != null || currentTime - messageObject2.messageOwner.date > 172800) ? obj3 : 1;
                                        obj3 = obj;
                                    }
                                }
                                obj = obj3;
                                obj3 = obj2;
                                if (obj3 != null) {
                                    break;
                                }
                                i2--;
                                obj2 = obj3;
                            }
                        } else {
                            obj = (messageObject.isSendError() || (!(messageObject.messageOwner.action == null || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty)) || ((!messageObject.isOut() && (this.currentChat == null || !(this.currentChat.creator || (this.currentChat.admin && this.currentChat.admins_enabled)))) || currentTime - messageObject.messageOwner.date > 172800))) ? null : 1;
                        }
                    }
                    if (obj != null) {
                        View frameLayout = new FrameLayout(getParentActivity());
                        checkBoxCell = new CheckBoxCell(getParentActivity(), true);
                        checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                        if (this.currentChat != null) {
                            checkBoxCell.setText(LocaleController.getString("DeleteForAll", R.string.DeleteForAll), TtmlNode.ANONYMOUS_REGION_ID, false, false);
                        } else {
                            checkBoxCell.setText(LocaleController.formatString("DeleteForUser", R.string.DeleteForUser, new Object[]{UserObject.getFirstName(this.currentUser)}), TtmlNode.ANONYMOUS_REGION_ID, false, false);
                        }
                        checkBoxCell.setPadding(LocaleController.isRTL ? AndroidUtilities.dp(16.0f) : AndroidUtilities.dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(8.0f) : AndroidUtilities.dp(16.0f), 0);
                        frameLayout.addView(checkBoxCell, LayoutHelper.createFrame(-1, 48.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                        zArr3 = zArr2;
                        checkBoxCell.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                CheckBoxCell checkBoxCell = (CheckBoxCell) view;
                                zArr3[0] = !zArr3[0];
                                checkBoxCell.setChecked(zArr3[0], true);
                            }
                        });
                        builder.setView(frameLayout);
                    }
                }
                user = null;
            } else {
                User user2;
                User user3;
                int i3;
                boolean canBlockUsers = ChatObject.canBlockUsers(this.currentChat);
                int currentTime2 = ConnectionsManager.getInstance().getCurrentTime();
                if (messageObject != null) {
                    user2 = (messageObject.messageOwner.action == null || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty) || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatDeleteUser)) ? MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.from_id)) : null;
                    obj3 = (!messageObject.isSendError() && messageObject.getDialogId() == this.mergeDialogId && ((messageObject.messageOwner.action == null || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty)) && messageObject.isOut() && currentTime2 - messageObject.messageOwner.date <= 172800)) ? 1 : null;
                    user3 = user2;
                } else {
                    size = -1;
                    for (i2 = 1; i2 >= 0; i2--) {
                        i3 = size;
                        for (Entry value2 : this.selectedMessagesIds[i2].entrySet()) {
                            messageObject2 = (MessageObject) value2.getValue();
                            if (i3 == -1) {
                                i3 = messageObject2.messageOwner.from_id;
                            }
                            if (i3 >= 0) {
                                if (i3 != messageObject2.messageOwner.from_id) {
                                }
                            }
                            size = -2;
                        }
                        size = i3;
                        if (size == -2) {
                            i3 = size;
                            break;
                        }
                    }
                    i3 = size;
                    Object obj4 = null;
                    currentTime = 1;
                    obj = null;
                    while (currentTime >= 0) {
                        obj2 = obj;
                        for (Entry value22 : this.selectedMessagesIds[currentTime].entrySet()) {
                            messageObject2 = (MessageObject) value22.getValue();
                            if (currentTime != 1) {
                                if (currentTime == 0 && !messageObject2.isOut()) {
                                    obj = null;
                                    int i4 = 1;
                                    break;
                                }
                                obj = obj2;
                            } else if (!messageObject2.isOut() || messageObject2.messageOwner.action != null) {
                                obj = null;
                                obj2 = 1;
                                break;
                            } else {
                                if (currentTime2 - messageObject2.messageOwner.date <= 172800) {
                                    obj = 1;
                                }
                                obj = obj2;
                            }
                            obj2 = obj;
                        }
                        obj = obj2;
                        obj2 = obj4;
                        if (obj2 != null) {
                            break;
                        }
                        currentTime--;
                        obj4 = obj2;
                    }
                    if (i3 != -1) {
                        user3 = MessagesController.getInstance().getUser(Integer.valueOf(i3));
                        obj3 = obj;
                    } else {
                        obj3 = obj;
                        user3 = null;
                    }
                }
                if (user3 == null || user3.id == UserConfig.getClientUserId() || i == 2) {
                    if (obj3 != null) {
                        View frameLayout2 = new FrameLayout(getParentActivity());
                        checkBoxCell = new CheckBoxCell(getParentActivity(), true);
                        checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                        if (this.currentChat != null) {
                            checkBoxCell.setText(LocaleController.getString("DeleteForAll", R.string.DeleteForAll), TtmlNode.ANONYMOUS_REGION_ID, false, false);
                        } else {
                            checkBoxCell.setText(LocaleController.formatString("DeleteForUser", R.string.DeleteForUser, new Object[]{UserObject.getFirstName(this.currentUser)}), TtmlNode.ANONYMOUS_REGION_ID, false, false);
                        }
                        checkBoxCell.setPadding(LocaleController.isRTL ? AndroidUtilities.dp(16.0f) : AndroidUtilities.dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(8.0f) : AndroidUtilities.dp(16.0f), 0);
                        frameLayout2.addView(checkBoxCell, LayoutHelper.createFrame(-1, 48.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                        zArr3 = zArr2;
                        checkBoxCell.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                CheckBoxCell checkBoxCell = (CheckBoxCell) view;
                                zArr3[0] = !zArr3[0];
                                checkBoxCell.setChecked(zArr3[0], true);
                            }
                        });
                        builder.setView(frameLayout2);
                        user2 = user3;
                    } else {
                        user2 = null;
                    }
                } else if (i != 1 || this.currentChat.creator) {
                    View frameLayout3 = new FrameLayout(getParentActivity());
                    int i5 = 0;
                    int i6 = 0;
                    while (i6 < 3) {
                        if (canBlockUsers || i6 != 0) {
                            frameLayout3 = new CheckBoxCell(getParentActivity(), true);
                            frameLayout3.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                            frameLayout3.setTag(Integer.valueOf(i6));
                            if (i6 == 0) {
                                frameLayout3.setText(LocaleController.getString("DeleteBanUser", R.string.DeleteBanUser), TtmlNode.ANONYMOUS_REGION_ID, false, false);
                            } else if (i6 == 1) {
                                frameLayout3.setText(LocaleController.getString("DeleteReportSpam", R.string.DeleteReportSpam), TtmlNode.ANONYMOUS_REGION_ID, false, false);
                            } else if (i6 == 2) {
                                frameLayout3.setText(LocaleController.formatString("DeleteAllFrom", R.string.DeleteAllFrom, new Object[]{ContactsController.formatName(user3.first_name, user3.last_name)}), TtmlNode.ANONYMOUS_REGION_ID, false, false);
                            }
                            frameLayout3.setPadding(LocaleController.isRTL ? AndroidUtilities.dp(16.0f) : AndroidUtilities.dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(8.0f) : AndroidUtilities.dp(16.0f), 0);
                            frameLayout3.addView(frameLayout3, LayoutHelper.createFrame(-1, 48.0f, 51, BitmapDescriptorFactory.HUE_RED, (float) (i5 * 48), BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                            frameLayout3.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
                                    if (view.isEnabled()) {
                                        CheckBoxCell checkBoxCell = (CheckBoxCell) view;
                                        Integer num = (Integer) checkBoxCell.getTag();
                                        zArr[num.intValue()] = !zArr[num.intValue()];
                                        checkBoxCell.setChecked(zArr[num.intValue()], true);
                                    }
                                }
                            });
                            size = i5 + 1;
                        } else {
                            size = i5;
                        }
                        i6++;
                        i5 = size;
                    }
                    builder.setView(frameLayout3);
                    user2 = user3;
                } else {
                    final org.telegram.ui.ActionBar.AlertDialog[] alertDialogArr = new org.telegram.ui.ActionBar.AlertDialog[]{new org.telegram.ui.ActionBar.AlertDialog(getParentActivity(), 1)};
                    TLObject tL_channels_getParticipant = new TL_channels_getParticipant();
                    tL_channels_getParticipant.channel = MessagesController.getInputChannel(this.currentChat);
                    tL_channels_getParticipant.user_id = MessagesController.getInputUser(user3);
                    final MessageObject messageObject3 = messageObject;
                    final GroupedMessages groupedMessages2 = groupedMessages;
                    i3 = ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipant, new RequestDelegate() {
                        public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    int i;
                                    try {
                                        alertDialogArr[0].dismiss();
                                    } catch (Throwable th) {
                                    }
                                    alertDialogArr[0] = null;
                                    if (tLObject != null) {
                                        TL_channels_channelParticipant tL_channels_channelParticipant = (TL_channels_channelParticipant) tLObject;
                                        if (!((tL_channels_channelParticipant.participant instanceof TL_channelParticipantAdmin) || (tL_channels_channelParticipant.participant instanceof TL_channelParticipantCreator))) {
                                            i = 0;
                                            ChatActivity.this.createDeleteMessagesAlert(messageObject3, groupedMessages2, i);
                                        }
                                    }
                                    i = 2;
                                    ChatActivity.this.createDeleteMessagesAlert(messageObject3, groupedMessages2, i);
                                }
                            });
                        }
                    });
                    if (i3 != 0) {
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.ui.ChatActivity$107$1 */
                            class C42231 implements OnClickListener {
                                C42231() {
                                }

                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ConnectionsManager.getInstance().cancelRequest(i3, true);
                                    try {
                                        dialogInterface.dismiss();
                                    } catch (Throwable e) {
                                        FileLog.e(e);
                                    }
                                }
                            }

                            public void run() {
                                if (alertDialogArr[0] != null) {
                                    alertDialogArr[0].setMessage(LocaleController.getString("Loading", R.string.Loading));
                                    alertDialogArr[0].setCanceledOnTouchOutside(false);
                                    alertDialogArr[0].setCancelable(false);
                                    alertDialogArr[0].setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C42231());
                                    ChatActivity.this.showDialog(alertDialogArr[0]);
                                }
                            }
                        }, 1000);
                        return;
                    }
                    return;
                }
                user = user2;
            }
            final MessageObject messageObject4 = messageObject;
            final GroupedMessages groupedMessages3 = groupedMessages;
            final boolean[] zArr4 = zArr2;
            final boolean[] zArr5 = zArr;
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {

                /* renamed from: org.telegram.ui.ChatActivity$111$1 */
                class C42241 implements RequestDelegate {
                    C42241() {
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    }
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    ArrayList arrayList = null;
                    ArrayList arrayList2;
                    MessageObject messageObject;
                    if (messageObject4 != null) {
                        arrayList = new ArrayList();
                        arrayList2 = null;
                        if (groupedMessages3 != null) {
                            for (int i2 = 0; i2 < groupedMessages3.messages.size(); i2++) {
                                messageObject = (MessageObject) groupedMessages3.messages.get(i2);
                                arrayList.add(Integer.valueOf(messageObject.getId()));
                                if (!(ChatActivity.this.currentEncryptedChat == null || messageObject.messageOwner.random_id == 0 || messageObject.type == 10)) {
                                    if (arrayList2 == null) {
                                        arrayList2 = new ArrayList();
                                    }
                                    arrayList2.add(Long.valueOf(messageObject.messageOwner.random_id));
                                }
                            }
                        } else {
                            arrayList.add(Integer.valueOf(messageObject4.getId()));
                            if (!(ChatActivity.this.currentEncryptedChat == null || messageObject4.messageOwner.random_id == 0 || messageObject4.type == 10)) {
                                arrayList2 = new ArrayList();
                                arrayList2.add(Long.valueOf(messageObject4.messageOwner.random_id));
                            }
                            if (ChatActivity.this.isDownloadManager) {
                                MessagesController.getInstance().deleteMessages(arrayList, arrayList2, ChatActivity.this.currentEncryptedChat, 0, zArr4[0]);
                            }
                        }
                        MessagesController.getInstance().deleteMessages(arrayList, arrayList2, ChatActivity.this.currentEncryptedChat, messageObject4.messageOwner.to_id.channel_id, zArr4[0]);
                    } else {
                        for (int i3 = 1; i3 >= 0; i3--) {
                            arrayList = new ArrayList(ChatActivity.this.selectedMessagesIds[i3].keySet());
                            arrayList2 = null;
                            int i4 = 0;
                            if (!arrayList.isEmpty()) {
                                messageObject = (MessageObject) ChatActivity.this.selectedMessagesIds[i3].get(arrayList.get(0));
                                if (messageObject.messageOwner.to_id.channel_id != 0) {
                                    i4 = messageObject.messageOwner.to_id.channel_id;
                                }
                            }
                            if (ChatActivity.this.currentEncryptedChat != null) {
                                arrayList2 = new ArrayList();
                                for (Entry value : ChatActivity.this.selectedMessagesIds[i3].entrySet()) {
                                    messageObject = (MessageObject) value.getValue();
                                    if (!(messageObject.messageOwner.random_id == 0 || messageObject.type == 10)) {
                                        arrayList2.add(Long.valueOf(messageObject.messageOwner.random_id));
                                    }
                                }
                            }
                            MessagesController.getInstance().deleteMessages(arrayList, arrayList2, ChatActivity.this.currentEncryptedChat, i4, zArr4[0]);
                        }
                        ChatActivity.this.actionBar.hideActionMode();
                        ChatActivity.this.updatePinnedMessageView(true);
                    }
                    if (user != null) {
                        if (zArr5[0]) {
                            MessagesController.getInstance().deleteUserFromChat(ChatActivity.this.currentChat.id, user, ChatActivity.this.info);
                        }
                        if (zArr5[1]) {
                            TLObject tL_channels_reportSpam = new TL_channels_reportSpam();
                            tL_channels_reportSpam.channel = MessagesController.getInputChannel(ChatActivity.this.currentChat);
                            tL_channels_reportSpam.user_id = MessagesController.getInputUser(user);
                            tL_channels_reportSpam.id = arrayList;
                            ConnectionsManager.getInstance().sendRequest(tL_channels_reportSpam, new C42241());
                        }
                        if (zArr5[2]) {
                            MessagesController.getInstance().deleteUserChannelHistory(ChatActivity.this.currentChat, user, 0);
                        }
                    }
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            showDialog(builder.create());
        }
    }

    private void createMenu(View view, boolean z, boolean z2) {
        createMenu(view, z, z2, true);
    }

    private void createMenu(View view, boolean z, boolean z2, boolean z3) {
        if (!this.actionBar.isActionModeShowed()) {
            MessageObject messageObject = view instanceof ChatMessageCell ? ((ChatMessageCell) view).getMessageObject() : view instanceof ChatActionCell ? ((ChatActionCell) view).getMessageObject() : null;
            if (messageObject != null) {
                int messageType = getMessageType(messageObject);
                if (z && (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPinMessage)) {
                    scrollToMessageId(messageObject.messageOwner.reply_to_msg_id, messageObject.messageOwner.id, true, 0, false);
                    return;
                }
                this.selectedObject = null;
                this.selectedObjectGroup = null;
                this.forwardingMessage = null;
                this.forwardingMessageGroup = null;
                for (int i = 1; i >= 0; i--) {
                    this.selectedMessagesCanCopyIds[i].clear();
                    this.selectedMessagesCanStarIds[i].clear();
                    this.selectedMessagesIds[i].clear();
                }
                this.cantDeleteMessagesCount = 0;
                this.canEditMessagesCount = 0;
                this.actionBar.hideActionMode();
                updatePinnedMessageView(true);
                GroupedMessages validGroupedMessage = z3 ? getValidGroupedMessage(messageObject) : null;
                Object obj = 1;
                Object obj2 = (messageObject.getDialogId() == this.mergeDialogId || messageObject.getId() <= 0 || !ChatObject.isChannel(this.currentChat) || ((!this.currentChat.creator && (this.currentChat.admin_rights == null || (!(this.currentChat.megagroup && this.currentChat.admin_rights.pin_messages) && (this.currentChat.megagroup || !this.currentChat.admin_rights.edit_messages)))) || !(messageObject.messageOwner.action == null || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty)))) ? null : 1;
                Object obj3 = (messageObject.getDialogId() == this.mergeDialogId || this.info == null || this.info.pinned_msg_id != messageObject.getId() || (!this.currentChat.creator && (this.currentChat.admin_rights == null || (!(this.currentChat.megagroup && this.currentChat.admin_rights.pin_messages) && (this.currentChat.megagroup || !this.currentChat.admin_rights.edit_messages))))) ? null : 1;
                Object obj4 = (validGroupedMessage != null || !messageObject.canEditMessage(this.currentChat) || this.chatActivityEnterView.hasAudioToSend() || messageObject.getDialogId() == this.mergeDialogId) ? null : 1;
                if ((this.currentEncryptedChat != null && AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) < 46) || ((messageType == 1 && (messageObject.getDialogId() == this.mergeDialogId || messageObject.isSecretPhoto())) || ((this.currentEncryptedChat == null && messageObject.getId() < 0) || ((this.bottomOverlayChat != null && this.bottomOverlayChat.getVisibility() == 0) || this.isBroadcast || (this.currentChat != null && (ChatObject.isNotInChat(this.currentChat) || !((!ChatObject.isChannel(this.currentChat) || ChatObject.canPost(this.currentChat) || this.currentChat.megagroup) && ChatObject.canSendMessages(this.currentChat)))))))) {
                    obj = null;
                }
                if (!z && messageType >= 2 && messageType != 20 && !messageObject.isSecretPhoto() && !messageObject.isLiveLocation()) {
                    ActionBarMenu createActionMode = this.actionBar.createActionMode();
                    View item = createActionMode.getItem(11);
                    if (item != null) {
                        item.setVisibility(0);
                    }
                    View item2 = createActionMode.getItem(12);
                    if (item2 != null) {
                        item2.setVisibility(0);
                    }
                    this.actionBar.showActionMode();
                    updatePinnedMessageView(true);
                    AnimatorSet animatorSet = new AnimatorSet();
                    Collection arrayList = new ArrayList();
                    for (int i2 = 0; i2 < this.actionModeViews.size(); i2++) {
                        item2 = (View) this.actionModeViews.get(i2);
                        item2.setPivotY((float) (ActionBar.getCurrentActionBarHeight() / 2));
                        AndroidUtilities.clearDrawableAnimation(item2);
                        arrayList.add(ObjectAnimator.ofFloat(item2, "scaleY", new float[]{0.1f, 1.0f}));
                    }
                    animatorSet.playTogether(arrayList);
                    animatorSet.setDuration(250);
                    animatorSet.start();
                    addToSelectedMessages(messageObject, z2);
                    this.selectedMessagesCountTextView.setNumber(this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size(), false);
                    updateVisibleRows();
                } else if (messageType >= 0) {
                    this.selectedObject = messageObject;
                    this.selectedObjectGroup = validGroupedMessage;
                    if (getParentActivity() != null) {
                        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
                        ArrayList arrayList2 = new ArrayList();
                        final ArrayList arrayList3 = new ArrayList();
                        if (messageType == 0) {
                            arrayList2.add(LocaleController.getString("Retry", R.string.Retry));
                            arrayList3.add(Integer.valueOf(0));
                            arrayList2.add(LocaleController.getString("Delete", R.string.Delete));
                            arrayList3.add(Integer.valueOf(1));
                        } else if (messageType == 1) {
                            if (this.currentChat == null || this.isBroadcast) {
                                if (messageObject.messageOwner.action != null && (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPhoneCall)) {
                                    TLRPC$TL_messageActionPhoneCall tLRPC$TL_messageActionPhoneCall = (TLRPC$TL_messageActionPhoneCall) messageObject.messageOwner.action;
                                    obj2 = (((tLRPC$TL_messageActionPhoneCall.reason instanceof TLRPC$TL_phoneCallDiscardReasonMissed) || (tLRPC$TL_messageActionPhoneCall.reason instanceof TLRPC$TL_phoneCallDiscardReasonBusy)) && !messageObject.isOutOwner()) ? LocaleController.getString("CallBack", R.string.CallBack) : LocaleController.getString("CallAgain", R.string.CallAgain);
                                    arrayList2.add(obj2);
                                    arrayList3.add(Integer.valueOf(18));
                                    if (VoIPHelper.canRateCall(tLRPC$TL_messageActionPhoneCall)) {
                                        arrayList2.add(LocaleController.getString("CallMessageReportProblem", R.string.CallMessageReportProblem));
                                        arrayList3.add(Integer.valueOf(19));
                                    }
                                }
                                if (z && this.selectedObject.getId() > 0 && obj != null) {
                                    arrayList2.add(LocaleController.getString("Reply", R.string.Reply));
                                    arrayList3.add(Integer.valueOf(8));
                                }
                                if (this.isDownloadManager || messageObject.canDeleteMessage(this.currentChat)) {
                                    arrayList2.add(LocaleController.getString("Delete", R.string.Delete));
                                    arrayList3.add(Integer.valueOf(1));
                                }
                            } else {
                                if (obj != null) {
                                    arrayList2.add(LocaleController.getString("Reply", R.string.Reply));
                                    arrayList3.add(Integer.valueOf(8));
                                }
                                if (this.directShareToMenu) {
                                    arrayList2.add(LocaleController.getString("DirectShare", R.string.DirectShare));
                                    arrayList3.add(Integer.valueOf(98));
                                }
                                if (obj3 != null) {
                                    arrayList2.add(LocaleController.getString("UnpinMessage", R.string.UnpinMessage));
                                    arrayList3.add(Integer.valueOf(14));
                                } else if (obj2 != null) {
                                    arrayList2.add(LocaleController.getString("PinMessage", R.string.PinMessage));
                                    arrayList3.add(Integer.valueOf(13));
                                }
                                if (obj4 != null) {
                                    arrayList2.add(LocaleController.getString("Edit", R.string.Edit));
                                    arrayList3.add(Integer.valueOf(12));
                                }
                                if (messageObject.canDeleteMessage(this.currentChat)) {
                                    arrayList2.add(LocaleController.getString("Delete", R.string.Delete));
                                    arrayList3.add(Integer.valueOf(1));
                                }
                            }
                        } else if (messageType == 20) {
                            arrayList2.add(LocaleController.getString("Retry", R.string.Retry));
                            arrayList3.add(Integer.valueOf(0));
                            arrayList2.add(LocaleController.getString("Copy", R.string.Copy));
                            arrayList3.add(Integer.valueOf(3));
                            arrayList2.add(LocaleController.getString("Delete", R.string.Delete));
                            arrayList3.add(Integer.valueOf(1));
                        } else if (this.currentEncryptedChat == null) {
                            if (obj != null) {
                                arrayList2.add(LocaleController.getString("Reply", R.string.Reply));
                                arrayList3.add(Integer.valueOf(8));
                            }
                            if (this.directShareToMenu) {
                                arrayList2.add(LocaleController.getString("DirectShare", R.string.DirectShare));
                                arrayList3.add(Integer.valueOf(98));
                            }
                            if (this.selectedObject.type == 0 || this.selectedObject.caption != null) {
                                arrayList2.add(LocaleController.getString("Copy", R.string.Copy));
                                arrayList3.add(Integer.valueOf(3));
                            }
                            if (ChatObject.isChannel(this.currentChat) && this.currentChat.megagroup && !TextUtils.isEmpty(this.currentChat.username) && ChatObject.hasAdminRights(this.currentChat)) {
                                arrayList2.add(LocaleController.getString("CopyLink", R.string.CopyLink));
                                arrayList3.add(Integer.valueOf(22));
                            }
                            if (messageType == 3) {
                                if ((this.selectedObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) && MessageObject.isNewGifDocument(this.selectedObject.messageOwner.media.webpage.document)) {
                                    arrayList2.add(LocaleController.getString("SaveToGIFs", R.string.SaveToGIFs));
                                    arrayList3.add(Integer.valueOf(11));
                                }
                            } else if (messageType == 4) {
                                if (this.selectedObject.isVideo()) {
                                    if (!this.selectedObject.isSecretPhoto()) {
                                        arrayList2.add(LocaleController.getString("SaveToGallery", R.string.SaveToGallery));
                                        arrayList3.add(Integer.valueOf(4));
                                        arrayList2.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                                        arrayList3.add(Integer.valueOf(6));
                                    }
                                } else if (this.selectedObject.isMusic()) {
                                    arrayList2.add(LocaleController.getString("SaveToMusic", R.string.SaveToMusic));
                                    arrayList3.add(Integer.valueOf(10));
                                    arrayList2.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                                    arrayList3.add(Integer.valueOf(6));
                                } else if (this.selectedObject.getDocument() != null) {
                                    if (MessageObject.isNewGifDocument(this.selectedObject.getDocument())) {
                                        arrayList2.add(LocaleController.getString("SaveToGIFs", R.string.SaveToGIFs));
                                        arrayList3.add(Integer.valueOf(11));
                                    }
                                    arrayList2.add(LocaleController.getString("SaveToDownloads", R.string.SaveToDownloads));
                                    arrayList3.add(Integer.valueOf(10));
                                    arrayList2.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                                    arrayList3.add(Integer.valueOf(6));
                                } else if (!this.selectedObject.isSecretPhoto()) {
                                    arrayList2.add(LocaleController.getString("SaveToGallery", R.string.SaveToGallery));
                                    arrayList3.add(Integer.valueOf(4));
                                }
                            } else if (messageType == 5) {
                                arrayList2.add(LocaleController.getString("ApplyLocalizationFile", R.string.ApplyLocalizationFile));
                                arrayList3.add(Integer.valueOf(5));
                                arrayList2.add(LocaleController.getString("SaveToDownloads", R.string.SaveToDownloads));
                                arrayList3.add(Integer.valueOf(10));
                                arrayList2.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                                arrayList3.add(Integer.valueOf(6));
                            } else if (messageType == 10) {
                                arrayList2.add(LocaleController.getString("ApplyThemeFile", R.string.ApplyThemeFile));
                                arrayList3.add(Integer.valueOf(5));
                                arrayList2.add(LocaleController.getString("SaveToDownloads", R.string.SaveToDownloads));
                                arrayList3.add(Integer.valueOf(10));
                                arrayList2.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                                arrayList3.add(Integer.valueOf(6));
                            } else if (messageType == 6) {
                                arrayList2.add(LocaleController.getString("SaveToGallery", R.string.SaveToGallery));
                                arrayList3.add(Integer.valueOf(7));
                                arrayList2.add(LocaleController.getString("SaveToDownloads", R.string.SaveToDownloads));
                                arrayList3.add(Integer.valueOf(10));
                                arrayList2.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                                arrayList3.add(Integer.valueOf(6));
                            } else if (messageType == 7) {
                                if (this.selectedObject.isMask()) {
                                    arrayList2.add(LocaleController.getString("AddToMasks", R.string.AddToMasks));
                                    arrayList3.add(Integer.valueOf(9));
                                } else {
                                    arrayList2.add(LocaleController.getString("AddToStickers", R.string.AddToStickers));
                                    arrayList3.add(Integer.valueOf(9));
                                    if (StickersQuery.isStickerInFavorites(this.selectedObject.getDocument())) {
                                        arrayList2.add(LocaleController.getString("DeleteFromFavorites", R.string.DeleteFromFavorites));
                                        arrayList3.add(Integer.valueOf(21));
                                    } else if (StickersQuery.canAddStickerToFavorites()) {
                                        arrayList2.add(LocaleController.getString("AddToFavorites", R.string.AddToFavorites));
                                        arrayList3.add(Integer.valueOf(20));
                                    }
                                }
                            } else if (messageType == 8) {
                                User user = MessagesController.getInstance().getUser(Integer.valueOf(this.selectedObject.messageOwner.media.user_id));
                                if (!(user == null || user.id == UserConfig.getClientUserId() || ContactsController.getInstance().contactsDict.get(Integer.valueOf(user.id)) != null)) {
                                    arrayList2.add(LocaleController.getString("AddContactTitle", R.string.AddContactTitle));
                                    arrayList3.add(Integer.valueOf(15));
                                }
                                if (!TextUtils.isEmpty(this.selectedObject.messageOwner.media.phone_number)) {
                                    arrayList2.add(LocaleController.getString("Copy", R.string.Copy));
                                    arrayList3.add(Integer.valueOf(16));
                                    arrayList2.add(LocaleController.getString("Call", R.string.Call));
                                    arrayList3.add(Integer.valueOf(17));
                                }
                            } else if (messageType == 9) {
                                if (StickersQuery.isStickerInFavorites(this.selectedObject.getDocument())) {
                                    arrayList2.add(LocaleController.getString("DeleteFromFavorites", R.string.DeleteFromFavorites));
                                    arrayList3.add(Integer.valueOf(21));
                                } else {
                                    arrayList2.add(LocaleController.getString("AddToFavorites", R.string.AddToFavorites));
                                    arrayList3.add(Integer.valueOf(20));
                                }
                            }
                            arrayList2.add(LocaleController.getString("MultiForward", R.string.MultiForward));
                            arrayList3.add(Integer.valueOf(23));
                            arrayList2.add(LocaleController.getString("AdvancedForward", R.string.AdvancedForward));
                            arrayList3.add(Integer.valueOf(24));
                            arrayList2.add(LocaleController.getString("ForwardNoQuote", R.string.ForwardNoQuote));
                            arrayList3.add(Integer.valueOf(2));
                            if (!(this.selectedObject.isMediaEmpty() || this.selectedObject.mediaExists)) {
                                arrayList2.add(LocaleController.getString("AddDownloadManager", R.string.AddDownloadManager));
                                arrayList3.add(Integer.valueOf(25));
                            }
                            if (!(this.selectedObject.isSecretPhoto() || this.selectedObject.isLiveLocation())) {
                                arrayList2.add(LocaleController.getString("Forward", R.string.Forward));
                                arrayList3.add(Integer.valueOf(30));
                            }
                            if (obj3 != null) {
                                arrayList2.add(LocaleController.getString("UnpinMessage", R.string.UnpinMessage));
                                arrayList3.add(Integer.valueOf(14));
                            } else if (obj2 != null) {
                                arrayList2.add(LocaleController.getString("PinMessage", R.string.PinMessage));
                                arrayList3.add(Integer.valueOf(13));
                            }
                            if (obj4 != null) {
                                arrayList2.add(LocaleController.getString("Edit", R.string.Edit));
                                arrayList3.add(Integer.valueOf(12));
                            }
                            if (this.isDownloadManager || messageObject.canDeleteMessage(this.currentChat)) {
                                arrayList2.add(LocaleController.getString("Delete", R.string.Delete));
                                arrayList3.add(Integer.valueOf(1));
                            }
                        } else {
                            if (obj != null) {
                                arrayList2.add(LocaleController.getString("Reply", R.string.Reply));
                                arrayList3.add(Integer.valueOf(8));
                            }
                            if (this.selectedObject.type == 0 || this.selectedObject.caption != null) {
                                arrayList2.add(LocaleController.getString("Copy", R.string.Copy));
                                arrayList3.add(Integer.valueOf(3));
                            }
                            if (messageType == 4) {
                                if (this.selectedObject.isVideo()) {
                                    arrayList2.add(LocaleController.getString("SaveToGallery", R.string.SaveToGallery));
                                    arrayList3.add(Integer.valueOf(4));
                                    arrayList2.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                                    arrayList3.add(Integer.valueOf(6));
                                } else if (this.selectedObject.isMusic()) {
                                    arrayList2.add(LocaleController.getString("SaveToMusic", R.string.SaveToMusic));
                                    arrayList3.add(Integer.valueOf(10));
                                    arrayList2.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                                    arrayList3.add(Integer.valueOf(6));
                                } else if (this.selectedObject.isVideo() || this.selectedObject.getDocument() == null) {
                                    arrayList2.add(LocaleController.getString("SaveToGallery", R.string.SaveToGallery));
                                    arrayList3.add(Integer.valueOf(4));
                                } else {
                                    arrayList2.add(LocaleController.getString("SaveToDownloads", R.string.SaveToDownloads));
                                    arrayList3.add(Integer.valueOf(10));
                                    arrayList2.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                                    arrayList3.add(Integer.valueOf(6));
                                }
                            } else if (messageType == 5) {
                                arrayList2.add(LocaleController.getString("ApplyLocalizationFile", R.string.ApplyLocalizationFile));
                                arrayList3.add(Integer.valueOf(5));
                            } else if (messageType == 10) {
                                arrayList2.add(LocaleController.getString("ApplyThemeFile", R.string.ApplyThemeFile));
                                arrayList3.add(Integer.valueOf(5));
                            } else if (messageType == 7) {
                                arrayList2.add(LocaleController.getString("AddToStickers", R.string.AddToStickers));
                                arrayList3.add(Integer.valueOf(9));
                            }
                            arrayList2.add(LocaleController.getString("Delete", R.string.Delete));
                            arrayList3.add(Integer.valueOf(1));
                        }
                        if (!arrayList3.isEmpty()) {
                            builder.setItems((CharSequence[]) arrayList2.toArray(new CharSequence[arrayList2.size()]), new OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (ChatActivity.this.selectedObject != null && i >= 0 && i < arrayList3.size()) {
                                        ChatActivity.this.processSelectedOption(((Integer) arrayList3.get(i)).intValue());
                                    }
                                }
                            });
                            builder.setTitle(LocaleController.getString("Message", R.string.Message));
                            if (!ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("disableMessageClick", false)) {
                                showDialog(builder.create());
                            }
                        }
                    }
                }
            }
        }
    }

    private TextureView createTextureView(boolean z) {
        if (this.parentLayout == null) {
            return null;
        }
        if (this.roundVideoContainer == null) {
            if (VERSION.SDK_INT >= 21) {
                this.roundVideoContainer = new FrameLayout(getParentActivity()) {
                    public void setTranslationY(float f) {
                        super.setTranslationY(f);
                        ChatActivity.this.contentView.invalidate();
                    }
                };
                this.roundVideoContainer.setOutlineProvider(new ViewOutlineProvider() {
                    @TargetApi(21)
                    public void getOutline(View view, Outline outline) {
                        outline.setOval(0, 0, AndroidUtilities.roundMessageSize, AndroidUtilities.roundMessageSize);
                    }
                });
                this.roundVideoContainer.setClipToOutline(true);
            } else {
                this.roundVideoContainer = new FrameLayout(getParentActivity()) {
                    protected void dispatchDraw(Canvas canvas) {
                        super.dispatchDraw(canvas);
                        canvas.drawPath(ChatActivity.this.aspectPath, ChatActivity.this.aspectPaint);
                    }

                    protected void onSizeChanged(int i, int i2, int i3, int i4) {
                        super.onSizeChanged(i, i2, i3, i4);
                        ChatActivity.this.aspectPath.reset();
                        ChatActivity.this.aspectPath.addCircle((float) (i / 2), (float) (i2 / 2), (float) (i / 2), Direction.CW);
                        ChatActivity.this.aspectPath.toggleInverseFillType();
                    }

                    public void setTranslationY(float f) {
                        super.setTranslationY(f);
                        ChatActivity.this.contentView.invalidate();
                    }

                    public void setVisibility(int i) {
                        super.setVisibility(i);
                        if (i == 0) {
                            setLayerType(2, null);
                        }
                    }
                };
                this.aspectPath = new Path();
                this.aspectPaint = new Paint(1);
                this.aspectPaint.setColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
                this.aspectPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
            }
            this.roundVideoContainer.setWillNotDraw(false);
            this.roundVideoContainer.setVisibility(4);
            this.aspectRatioFrameLayout = new AspectRatioFrameLayout(getParentActivity());
            this.aspectRatioFrameLayout.setBackgroundColor(0);
            if (z) {
                this.roundVideoContainer.addView(this.aspectRatioFrameLayout, LayoutHelper.createFrame(-1, -1.0f));
            }
            this.videoTextureView = new TextureView(getParentActivity());
            this.videoTextureView.setOpaque(false);
            this.aspectRatioFrameLayout.addView(this.videoTextureView, LayoutHelper.createFrame(-1, -1.0f));
        }
        if (this.roundVideoContainer.getParent() == null) {
            this.contentView.addView(this.roundVideoContainer, 1, new FrameLayout.LayoutParams(AndroidUtilities.roundMessageSize, AndroidUtilities.roundMessageSize));
        }
        this.roundVideoContainer.setVisibility(4);
        this.aspectRatioFrameLayout.setDrawingReady(false);
        return this.videoTextureView;
    }

    private ArrayList<MessageObject> createVoiceMessagesPlaylist(MessageObject messageObject, boolean z) {
        ArrayList<MessageObject> arrayList = new ArrayList();
        arrayList.add(messageObject);
        int id = messageObject.getId();
        messageObject.getDialogId();
        if (id != 0) {
            for (int size = this.messages.size() - 1; size >= 0; size--) {
                MessageObject messageObject2 = (MessageObject) this.messages.get(size);
                if ((messageObject2.getDialogId() != this.mergeDialogId || messageObject.getDialogId() == this.mergeDialogId) && (((this.currentEncryptedChat == null && messageObject2.getId() > id) || (this.currentEncryptedChat != null && messageObject2.getId() < id)) && ((messageObject2.isVoice() || messageObject2.isRoundVideo()) && (!z || (messageObject2.isContentUnread() && !messageObject2.isOut()))))) {
                    arrayList.add(messageObject2);
                }
            }
        }
        return arrayList;
    }

    private void destroyTextureView() {
        if (this.roundVideoContainer != null && this.roundVideoContainer.getParent() != null) {
            this.contentView.removeView(this.roundVideoContainer);
            this.aspectRatioFrameLayout.setDrawingReady(false);
            this.roundVideoContainer.setVisibility(4);
            if (VERSION.SDK_INT < 21) {
                this.roundVideoContainer.setLayerType(0, null);
            }
        }
    }

    private void fixLayout() {
        if (this.avatarContainer != null) {
            this.avatarContainer.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() {
                    if (ChatActivity.this.avatarContainer != null) {
                        ChatActivity.this.avatarContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                    }
                    return ChatActivity.this.fixLayoutInternal();
                }
            });
        }
    }

    private boolean fixLayoutInternal() {
        if (AndroidUtilities.isTablet() || ApplicationLoader.applicationContext.getResources().getConfiguration().orientation != 2) {
            this.selectedMessagesCountTextView.setTextSize(20);
        } else {
            this.selectedMessagesCountTextView.setTextSize(18);
        }
        HashMap hashMap = null;
        int childCount = this.chatListView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.chatListView.getChildAt(i);
            if (childAt instanceof ChatMessageCell) {
                GroupedMessages currentMessagesGroup = ((ChatMessageCell) childAt).getCurrentMessagesGroup();
                if (currentMessagesGroup != null && currentMessagesGroup.hasSibling) {
                    if (hashMap == null) {
                        hashMap = new HashMap();
                    }
                    if (!hashMap.containsKey(Long.valueOf(currentMessagesGroup.groupId))) {
                        hashMap.put(Long.valueOf(currentMessagesGroup.groupId), currentMessagesGroup);
                        int indexOf = this.messages.indexOf((MessageObject) currentMessagesGroup.messages.get(currentMessagesGroup.messages.size() - 1));
                        if (indexOf >= 0) {
                            this.chatAdapter.notifyItemRangeChanged(indexOf + this.chatAdapter.messagesStartRow, currentMessagesGroup.messages.size());
                        }
                    }
                }
            }
        }
        if (!AndroidUtilities.isTablet()) {
            return true;
        }
        if (AndroidUtilities.isSmallTablet() && ApplicationLoader.applicationContext.getResources().getConfiguration().orientation == 1) {
            this.actionBar.setBackButtonDrawable(new BackDrawable(false));
            if (this.fragmentContextView == null || this.fragmentContextView.getParent() != null) {
                return false;
            }
            ((ViewGroup) this.fragmentView).addView(this.fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, BitmapDescriptorFactory.HUE_RED, -36.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            return false;
        }
        ActionBar actionBar = this.actionBar;
        boolean z = this.parentLayout == null || this.parentLayout.fragmentsStack.isEmpty() || this.parentLayout.fragmentsStack.get(0) == this || this.parentLayout.fragmentsStack.size() == 1;
        actionBar.setBackButtonDrawable(new BackDrawable(z));
        if (this.fragmentContextView == null || this.fragmentContextView.getParent() == null) {
            return false;
        }
        this.fragmentView.setPadding(0, 0, 0, 0);
        ((ViewGroup) this.fragmentView).removeView(this.fragmentContextView);
        return false;
    }

    private void forwardMessages(ArrayList<MessageObject> arrayList, boolean z) {
        if (arrayList != null && !arrayList.isEmpty()) {
            if (z) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    SendMessagesHelper.getInstance().processForwardFromMyName((MessageObject) it.next(), this.dialog_id, false);
                }
                return;
            }
            AlertsCreator.showSendMediaAlert(SendMessagesHelper.getInstance().sendMessage(arrayList, this.dialog_id), this);
        }
    }

    private String getMessageContent(MessageObject messageObject, int i, boolean z) {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        if (z && i != messageObject.messageOwner.from_id) {
            if (messageObject.messageOwner.from_id > 0) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.from_id));
                if (user != null) {
                    str = ContactsController.formatName(user.first_name, user.last_name) + ":\n";
                }
            } else if (messageObject.messageOwner.from_id < 0) {
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-messageObject.messageOwner.from_id));
                if (chat != null) {
                    str = chat.title + ":\n";
                }
            }
        }
        return (messageObject.type != 0 || messageObject.messageOwner.message == null) ? (messageObject.messageOwner.media == null || messageObject.messageOwner.media.caption == null) ? str + messageObject.messageText : str + messageObject.messageOwner.media.caption : str + messageObject.messageOwner.message;
    }

    private int getMessageType(MessageObject messageObject) {
        int i = 1;
        if (messageObject == null) {
            return -1;
        }
        int i2;
        InputStickerSet inputStickerSet;
        String str;
        if (this.currentEncryptedChat == null) {
            i2 = (this.isBroadcast && messageObject.getId() <= 0 && messageObject.isSendError()) ? 1 : 0;
            if ((!this.isBroadcast && messageObject.getId() <= 0 && messageObject.isOut()) || i2 != 0) {
                return messageObject.isSendError() ? messageObject.isMediaEmpty() ? 20 : 0 : -1;
            } else {
                if (messageObject.type == 6) {
                    return -1;
                }
                if (messageObject.type == 10 || messageObject.type == 11 || messageObject.type == 16) {
                    return messageObject.getId() == 0 ? -1 : 1;
                } else {
                    if (messageObject.isVoice()) {
                        return 2;
                    }
                    if (messageObject.isSticker()) {
                        inputStickerSet = messageObject.getInputStickerSet();
                        if (inputStickerSet instanceof TLRPC$TL_inputStickerSetID) {
                            if (!StickersQuery.isStickerPackInstalled(inputStickerSet.id)) {
                                return 7;
                            }
                        } else if ((inputStickerSet instanceof TLRPC$TL_inputStickerSetShortName) && !StickersQuery.isStickerPackInstalled(inputStickerSet.short_name)) {
                            return 7;
                        }
                        return 9;
                    }
                    if ((!messageObject.isRoundVideo() || (messageObject.isRoundVideo() && BuildVars.DEBUG_VERSION)) && ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) || messageObject.getDocument() != null || messageObject.isMusic() || messageObject.isVideo())) {
                        i2 = (TextUtils.isEmpty(messageObject.messageOwner.attachPath) || !new File(messageObject.messageOwner.attachPath).exists()) ? 0 : 1;
                        if (!(i2 == 0 && FileLoader.getPathToMessage(messageObject.messageOwner).exists())) {
                            i = i2;
                        }
                        if (i != 0) {
                            if (messageObject.getDocument() != null) {
                                str = messageObject.getDocument().mime_type;
                                if (str != null) {
                                    if (messageObject.getDocumentName().endsWith("attheme")) {
                                        return 10;
                                    }
                                    if (str.endsWith("/xml")) {
                                        return 5;
                                    }
                                    if (str.endsWith("/png") || str.endsWith("/jpg") || str.endsWith("/jpeg")) {
                                        return 6;
                                    }
                                }
                            }
                            return 4;
                        }
                    } else if (messageObject.type == 12) {
                        return 8;
                    } else {
                        if (messageObject.isMediaEmpty()) {
                            return 3;
                        }
                    }
                    return 2;
                }
            }
        } else if (messageObject.isSending()) {
            return -1;
        } else {
            if (messageObject.type == 6) {
                return -1;
            }
            if (messageObject.isSendError()) {
                return messageObject.isMediaEmpty() ? 20 : 0;
            } else {
                if (messageObject.type == 10 || messageObject.type == 11) {
                    return (messageObject.getId() == 0 || messageObject.isSending()) ? -1 : 1;
                } else {
                    if (messageObject.isVoice()) {
                        return 2;
                    }
                    if (messageObject.isSticker()) {
                        inputStickerSet = messageObject.getInputStickerSet();
                        if ((inputStickerSet instanceof TLRPC$TL_inputStickerSetShortName) && !StickersQuery.isStickerPackInstalled(inputStickerSet.short_name)) {
                            return 7;
                        }
                    } else if (!messageObject.isRoundVideo() && ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) || messageObject.getDocument() != null || messageObject.isMusic() || messageObject.isVideo())) {
                        i2 = (TextUtils.isEmpty(messageObject.messageOwner.attachPath) || !new File(messageObject.messageOwner.attachPath).exists()) ? 0 : 1;
                        if (!(i2 == 0 && FileLoader.getPathToMessage(messageObject.messageOwner).exists())) {
                            i = i2;
                        }
                        if (i != 0) {
                            if (messageObject.getDocument() != null) {
                                str = messageObject.getDocument().mime_type;
                                if (str != null && str.endsWith("text/xml")) {
                                    return 5;
                                }
                            }
                            if (messageObject.messageOwner.ttl <= 0) {
                                return 4;
                            }
                        }
                    } else if (messageObject.type == 12) {
                        return 8;
                    } else {
                        if (messageObject.isMediaEmpty()) {
                            return 3;
                        }
                    }
                    return 2;
                }
            }
        }
    }

    public static String getPersianDate(long j) {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        Date date = new Date((long) (Double.valueOf((double) j).doubleValue() * 1000.0d));
        String b = C3792d.b(date);
        String c = C3792d.c(date);
        Object d = C3792d.d(date);
        String d2 = C3792d.d(new Date());
        str = str + b + " " + c;
        return !d2.contentEquals(d) ? str + " " + d : str;
    }

    private int getScrollOffsetForMessage(MessageObject messageObject) {
        int measuredHeight;
        GroupedMessages validGroupedMessage = getValidGroupedMessage(messageObject);
        if (validGroupedMessage != null) {
            GroupedMessagePosition groupedMessagePosition = (GroupedMessagePosition) validGroupedMessage.positions.get(messageObject);
            float max = ((float) Math.max(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) * 0.5f;
            float f = groupedMessagePosition.siblingHeights != null ? groupedMessagePosition.siblingHeights[0] : groupedMessagePosition.ph;
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            float f2 = BitmapDescriptorFactory.HUE_RED;
            float f3 = BitmapDescriptorFactory.HUE_RED;
            for (int i = 0; i < validGroupedMessage.posArray.size(); i++) {
                GroupedMessagePosition groupedMessagePosition2 = (GroupedMessagePosition) validGroupedMessage.posArray.get(i);
                if (sparseBooleanArray.indexOfKey(groupedMessagePosition2.minY) < 0 && groupedMessagePosition2.siblingHeights == null) {
                    sparseBooleanArray.put(groupedMessagePosition2.minY, true);
                    if (groupedMessagePosition2.minY < groupedMessagePosition.minY) {
                        f2 -= groupedMessagePosition2.ph;
                    } else if (groupedMessagePosition2.minY > groupedMessagePosition.minY) {
                        f2 += groupedMessagePosition2.ph;
                    }
                    f3 += groupedMessagePosition2.ph;
                }
            }
            measuredHeight = Math.abs(f3 - f) < 0.02f ? ((((int) (((float) this.chatListView.getMeasuredHeight()) - (f3 * max))) / 2) - this.chatListView.getPaddingTop()) - AndroidUtilities.dp(7.0f) : ((((int) (((float) this.chatListView.getMeasuredHeight()) - ((f + f2) * max))) / 2) - this.chatListView.getPaddingTop()) - AndroidUtilities.dp(7.0f);
        } else {
            measuredHeight = Integer.MAX_VALUE;
        }
        if (measuredHeight == Integer.MAX_VALUE) {
            measuredHeight = (this.chatListView.getMeasuredHeight() - messageObject.getApproximateHeight()) / 2;
        }
        return Math.max(0, measuredHeight);
    }

    private GroupedMessages getValidGroupedMessage(MessageObject messageObject) {
        if (messageObject.getGroupId() == 0) {
            return null;
        }
        GroupedMessages groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject.getGroupId()));
        return groupedMessages != null ? (groupedMessages.messages.size() <= 1 || groupedMessages.positions.get(messageObject) == null) ? null : groupedMessages : groupedMessages;
    }

    private void hideFloatingDateView(boolean z) {
        if (this.floatingDateView.getTag() != null && !this.currentFloatingDateOnScreen) {
            if (!this.scrollingFloatingDate || this.currentFloatingTopIsNotMessage) {
                this.floatingDateView.setTag(null);
                if (z) {
                    this.floatingDateAnimation = new AnimatorSet();
                    this.floatingDateAnimation.setDuration(150);
                    AnimatorSet animatorSet = this.floatingDateAnimation;
                    Animator[] animatorArr = new Animator[1];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.floatingDateView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorSet.playTogether(animatorArr);
                    this.floatingDateAnimation.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            if (animator.equals(ChatActivity.this.floatingDateAnimation)) {
                                ChatActivity.this.floatingDateAnimation = null;
                            }
                        }
                    });
                    this.floatingDateAnimation.setStartDelay(500);
                    this.floatingDateAnimation.start();
                    return;
                }
                if (this.floatingDateAnimation != null) {
                    this.floatingDateAnimation.cancel();
                    this.floatingDateAnimation = null;
                }
                this.floatingDateView.setAlpha(BitmapDescriptorFactory.HUE_RED);
            }
        }
    }

    private void hidePinnedMessageView(boolean z) {
        if (this.pinnedMessageView.getTag() == null) {
            this.pinnedMessageView.setTag(Integer.valueOf(1));
            if (this.pinnedMessageViewAnimator != null) {
                this.pinnedMessageViewAnimator.cancel();
                this.pinnedMessageViewAnimator = null;
            }
            if (z) {
                this.pinnedMessageViewAnimator = new AnimatorSet();
                AnimatorSet animatorSet = this.pinnedMessageViewAnimator;
                Animator[] animatorArr = new Animator[1];
                animatorArr[0] = ObjectAnimator.ofFloat(this.pinnedMessageView, "translationY", new float[]{(float) (-AndroidUtilities.dp(50.0f))});
                animatorSet.playTogether(animatorArr);
                this.pinnedMessageViewAnimator.setDuration(200);
                this.pinnedMessageViewAnimator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationCancel(Animator animator) {
                        if (ChatActivity.this.pinnedMessageViewAnimator != null && ChatActivity.this.pinnedMessageViewAnimator.equals(animator)) {
                            ChatActivity.this.pinnedMessageViewAnimator = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (ChatActivity.this.pinnedMessageViewAnimator != null && ChatActivity.this.pinnedMessageViewAnimator.equals(animator)) {
                            ChatActivity.this.pinnedMessageView.setVisibility(8);
                            ChatActivity.this.pinnedMessageViewAnimator = null;
                        }
                    }
                });
                this.pinnedMessageViewAnimator.start();
                return;
            }
            this.pinnedMessageView.setTranslationY((float) (-AndroidUtilities.dp(50.0f)));
            this.pinnedMessageView.setVisibility(8);
        }
    }

    private void hideVoiceHint() {
        this.voiceHintAnimation = new AnimatorSet();
        AnimatorSet animatorSet = this.voiceHintAnimation;
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(this.voiceHintTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        animatorSet.playTogether(animatorArr);
        this.voiceHintAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationCancel(Animator animator) {
                if (animator.equals(ChatActivity.this.voiceHintAnimation)) {
                    ChatActivity.this.voiceHintHideRunnable = null;
                    ChatActivity.this.voiceHintHideRunnable = null;
                }
            }

            public void onAnimationEnd(Animator animator) {
                if (animator.equals(ChatActivity.this.voiceHintAnimation)) {
                    ChatActivity.this.voiceHintAnimation = null;
                    ChatActivity.this.voiceHintHideRunnable = null;
                    if (ChatActivity.this.voiceHintTextView != null) {
                        ChatActivity.this.voiceHintTextView.setVisibility(8);
                    }
                }
            }
        });
        this.voiceHintAnimation.setDuration(300);
        this.voiceHintAnimation.start();
    }

    private void initStickers() {
        if (this.chatActivityEnterView != null && getParentActivity() != null && this.stickersAdapter == null) {
            if (this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 23) {
                if (this.stickersAdapter != null) {
                    this.stickersAdapter.onDestroy();
                }
                this.stickersListView.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
                RecyclerListView recyclerListView = this.stickersListView;
                Adapter stickersAdapter = new StickersAdapter(getParentActivity(), new StickersAdapterDelegate() {
                    public void needChangePanelVisibility(final boolean z) {
                        float f = 1.0f;
                        if (!z || ChatActivity.this.stickersPanel.getVisibility() != 0) {
                            if (z || ChatActivity.this.stickersPanel.getVisibility() != 8) {
                                if (z) {
                                    ChatActivity.this.stickersListView.scrollToPosition(0);
                                    ChatActivity.this.stickersPanel.setVisibility(ChatActivity.this.allowStickersPanel ? 0 : 4);
                                }
                                if (ChatActivity.this.runningAnimation != null) {
                                    ChatActivity.this.runningAnimation.cancel();
                                    ChatActivity.this.runningAnimation = null;
                                }
                                if (ChatActivity.this.stickersPanel.getVisibility() != 4) {
                                    ChatActivity.this.runningAnimation = new AnimatorSet();
                                    AnimatorSet access$14700 = ChatActivity.this.runningAnimation;
                                    Animator[] animatorArr = new Animator[1];
                                    FrameLayout access$12100 = ChatActivity.this.stickersPanel;
                                    String str = "alpha";
                                    float[] fArr = new float[2];
                                    fArr[0] = z ? 0.0f : 1.0f;
                                    if (!z) {
                                        f = 0.0f;
                                    }
                                    fArr[1] = f;
                                    animatorArr[0] = ObjectAnimator.ofFloat(access$12100, str, fArr);
                                    access$14700.playTogether(animatorArr);
                                    ChatActivity.this.runningAnimation.setDuration(150);
                                    ChatActivity.this.runningAnimation.addListener(new AnimatorListenerAdapter() {
                                        public void onAnimationCancel(Animator animator) {
                                            if (ChatActivity.this.runningAnimation != null && ChatActivity.this.runningAnimation.equals(animator)) {
                                                ChatActivity.this.runningAnimation = null;
                                            }
                                        }

                                        public void onAnimationEnd(Animator animator) {
                                            if (ChatActivity.this.runningAnimation != null && ChatActivity.this.runningAnimation.equals(animator)) {
                                                if (!z) {
                                                    ChatActivity.this.stickersAdapter.clearStickers();
                                                    ChatActivity.this.stickersPanel.setVisibility(8);
                                                    if (StickerPreviewViewer.getInstance().isVisible()) {
                                                        StickerPreviewViewer.getInstance().close();
                                                    }
                                                    StickerPreviewViewer.getInstance().reset();
                                                }
                                                ChatActivity.this.runningAnimation = null;
                                            }
                                        }
                                    });
                                    ChatActivity.this.runningAnimation.start();
                                } else if (!z) {
                                    ChatActivity.this.stickersPanel.setVisibility(8);
                                }
                            }
                        }
                    }
                });
                this.stickersAdapter = stickersAdapter;
                recyclerListView.setAdapter(stickersAdapter);
                recyclerListView = this.stickersListView;
                OnItemClickListener anonymousClass69 = new OnItemClickListener() {
                    public void onItemClick(View view, int i) {
                        Document item = ChatActivity.this.stickersAdapter.getItem(i);
                        if (item instanceof TLRPC$TL_document) {
                            SendMessagesHelper.getInstance().sendSticker(item, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject, ChatActivity.this.getParentActivity());
                            ChatActivity.this.showReplyPanel(false, null, null, null, false);
                            ChatActivity.this.chatActivityEnterView.addStickerToRecent(item);
                        }
                        ChatActivity.this.chatActivityEnterView.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
                    }
                };
                this.stickersOnItemClickListener = anonymousClass69;
                recyclerListView.setOnItemClickListener(anonymousClass69);
            }
        }
    }

    private void jumpToDate(int i) {
        if (!this.messages.isEmpty()) {
            MessageObject messageObject = (MessageObject) this.messages.get(this.messages.size() - 1);
            if (((MessageObject) this.messages.get(0)).messageOwner.date >= i && messageObject.messageOwner.date <= i) {
                int size = this.messages.size() - 1;
                while (size >= 0) {
                    MessageObject messageObject2 = (MessageObject) this.messages.get(size);
                    if (messageObject2.messageOwner.date < i || messageObject2.getId() == 0) {
                        size--;
                    } else {
                        scrollToMessageId(messageObject2.getId(), 0, false, messageObject2.getDialogId() == this.mergeDialogId ? 1 : 0, false);
                        return;
                    }
                }
            } else if (((int) this.dialog_id) != 0) {
                clearChatData();
                this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                MessagesController instance = MessagesController.getInstance();
                long j = this.dialog_id;
                int i2 = this.classGuid;
                boolean isChannel = ChatObject.isChannel(this.currentChat);
                int i3 = this.lastLoadIndex;
                this.lastLoadIndex = i3 + 1;
                instance.loadMessages(j, 30, 0, i, true, 0, i2, 4, 0, isChannel, i3);
                this.floatingDateView.setAlpha(BitmapDescriptorFactory.HUE_RED);
                this.floatingDateView.setTag(null);
            }
        }
    }

    private void mentionListViewUpdateLayout() {
        if (this.mentionListView.getChildCount() <= 0) {
            this.mentionListViewScrollOffsetY = 0;
            this.mentionListViewLastViewPosition = -1;
            return;
        }
        View childAt = this.mentionListView.getChildAt(this.mentionListView.getChildCount() - 1);
        Holder holder = (Holder) this.mentionListView.findContainingViewHolder(childAt);
        int measuredHeight;
        if (this.mentionLayoutManager.getReverseLayout()) {
            if (holder != null) {
                this.mentionListViewLastViewPosition = holder.getAdapterPosition();
                this.mentionListViewLastViewTop = childAt.getBottom();
            } else {
                this.mentionListViewLastViewPosition = -1;
            }
            childAt = this.mentionListView.getChildAt(0);
            holder = (Holder) this.mentionListView.findContainingViewHolder(childAt);
            measuredHeight = (childAt.getBottom() >= this.mentionListView.getMeasuredHeight() || holder == null || holder.getAdapterPosition() != 0) ? this.mentionListView.getMeasuredHeight() : childAt.getBottom();
            if (this.mentionListViewScrollOffsetY != measuredHeight) {
                RecyclerListView recyclerListView = this.mentionListView;
                this.mentionListViewScrollOffsetY = measuredHeight;
                recyclerListView.setBottomGlowOffset(measuredHeight);
                this.mentionListView.setTopGlowOffset(0);
                this.mentionListView.invalidate();
                this.mentionContainer.invalidate();
                return;
            }
            return;
        }
        if (holder != null) {
            this.mentionListViewLastViewPosition = holder.getAdapterPosition();
            this.mentionListViewLastViewTop = childAt.getTop();
        } else {
            this.mentionListViewLastViewPosition = -1;
        }
        childAt = this.mentionListView.getChildAt(0);
        holder = (Holder) this.mentionListView.findContainingViewHolder(childAt);
        measuredHeight = (childAt.getTop() <= 0 || holder == null || holder.getAdapterPosition() != 0) ? 0 : childAt.getTop();
        if (this.mentionListViewScrollOffsetY != measuredHeight) {
            recyclerListView = this.mentionListView;
            this.mentionListViewScrollOffsetY = measuredHeight;
            recyclerListView.setTopGlowOffset(measuredHeight);
            this.mentionListView.setBottomGlowOffset(0);
            this.mentionListView.invalidate();
            this.mentionContainer.invalidate();
        }
    }

    private void moveScrollToLastMessage() {
        if (this.chatListView != null && !this.messages.isEmpty()) {
            this.chatLayoutManager.scrollToPositionWithOffset(0, 0);
        }
    }

    private void mute() {
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
        edit.putInt("notify2_" + this.dialog_id, 2);
        MessagesStorage.getInstance().setDialogFlags(this.dialog_id, 1);
        edit.commit();
        TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.dialog_id));
        if (tLRPC$TL_dialog != null) {
            tLRPC$TL_dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
            tLRPC$TL_dialog.notify_settings.mute_until = Integer.MAX_VALUE;
        }
        NotificationsController.updateServerNotificationsSettings(this.dialog_id);
        NotificationsController.getInstance().removeNotificationsForDialog(this.dialog_id);
    }

    private void openAddMember() {
        int i = 0;
        boolean z = true;
        Bundle bundle = new Bundle();
        bundle.putBoolean("onlyUsers", true);
        bundle.putBoolean("destroyAfterSelect", true);
        bundle.putBoolean("returnAsResult", true);
        String str = "needForwardCount";
        if (ChatObject.isChannel(this.currentChat)) {
            z = false;
        }
        bundle.putBoolean(str, z);
        if (this.chat_id > 0) {
            if (this.currentChat.creator) {
                bundle.putInt("chat_id", this.currentChat.id);
            }
            bundle.putString("selectAlertString", LocaleController.getString("AddToTheGroup", R.string.AddToTheGroup));
        }
        BaseFragment contactsActivity = new ContactsActivity(bundle);
        contactsActivity.setDelegate(new ContactsActivityDelegate() {
            public void didSelectContact(User user, String str, ContactsActivity contactsActivity) {
                MessagesController.getInstance().addUserToChat(ChatActivity.this.chat_id, user, ChatActivity.this.info, str != null ? Utilities.parseInt(str).intValue() : 0, null, ChatActivity.this);
            }
        });
        if (this.info instanceof TL_chatFull) {
            HashMap hashMap = new HashMap();
            while (i < this.info.participants.participants.size()) {
                hashMap.put(Integer.valueOf(((ChatParticipant) this.info.participants.participants.get(i)).user_id), null);
                i++;
            }
            contactsActivity.setIgnoreUsers(hashMap);
        }
        presentFragment(contactsActivity);
    }

    private void openAttachMenu() {
        if (getParentActivity() != null) {
            createChatAttachView();
            this.chatAttachAlert.loadGalleryPhotos();
            if (VERSION.SDK_INT == 21 || VERSION.SDK_INT == 22) {
                this.chatActivityEnterView.closeKeyboard();
            }
            this.chatAttachAlert.init();
            showDialog(this.chatAttachAlert);
        }
    }

    private void openSearchWithText(String str) {
        this.avatarContainer.setVisibility(8);
        this.headerItem.setVisibility(8);
        this.attachItem.setVisibility(8);
        this.searchItem.setVisibility(0);
        updateSearchButtons(0, 0, -1);
        updateBottomOverlay();
        this.openSearchKeyboard = str == null;
        this.searchItem.openSearch(this.openSearchKeyboard);
        if (str != null) {
            this.searchItem.getSearchField().setText(str);
            this.searchItem.getSearchField().setSelection(this.searchItem.getSearchField().length());
            MessagesSearchQuery.searchMessagesInChat(str, this.dialog_id, this.mergeDialogId, this.classGuid, 0, this.searchingUserMessages);
        }
        updatePinnedMessageView(true);
    }

    private void processRowSelect(View view, boolean z) {
        MessageObject messageObject = null;
        if (view instanceof ChatMessageCell) {
            messageObject = ((ChatMessageCell) view).getMessageObject();
        } else if (view instanceof ChatActionCell) {
            messageObject = ((ChatActionCell) view).getMessageObject();
        }
        int messageType = getMessageType(messageObject);
        if (messageType >= 2 && messageType != 20) {
            addToSelectedMessages(messageObject, z);
            updateActionModeTitle();
            updateVisibleRows();
        }
    }

    private void processSelectedAttach(int i) {
        int i2 = 1;
        if (i == 0 || i == 1 || i == 4 || i == 2) {
            String str = this.currentChat != null ? this.currentChat.participants_count > MessagesController.getInstance().groupBigSize ? (i == 0 || i == 1) ? "bigchat_upload_photo" : "bigchat_upload_document" : (i == 0 || i == 1) ? "chat_upload_photo" : "chat_upload_document" : (i == 0 || i == 1) ? "pm_upload_photo" : "pm_upload_document";
            if (!MessagesController.isFeatureEnabled(str, this)) {
                return;
            }
        }
        Intent intent;
        File generatePicturePath;
        if (i == 0) {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.CAMERA") == 0) {
                try {
                    intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    generatePicturePath = AndroidUtilities.generatePicturePath();
                    if (generatePicturePath != null) {
                        if (VERSION.SDK_INT >= 24) {
                            intent.putExtra("output", FileProvider.a(getParentActivity(), "org.ir.talaeii.provider", generatePicturePath));
                            intent.addFlags(2);
                            intent.addFlags(1);
                        } else {
                            intent.putExtra("output", Uri.fromFile(generatePicturePath));
                        }
                        this.currentPicturePath = generatePicturePath.getAbsolutePath();
                    }
                    startActivityForResult(intent, 0);
                    return;
                } catch (Throwable e) {
                    FileLog.e(e);
                    return;
                }
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.CAMERA"}, 19);
        } else if (i == 1) {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
                boolean z = this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 46;
                BaseFragment photoAlbumPickerActivity = new PhotoAlbumPickerActivity(false, z, true, this);
                photoAlbumPickerActivity.setDelegate(new PhotoAlbumPickerActivityDelegate() {
                    public void didSelectPhotos(ArrayList<SendingMediaInfo> arrayList) {
                        SendMessagesHelper.prepareSendingMedia(arrayList, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject, null, false, MediaController.getInstance().isGroupPhotosEnabled());
                        ChatActivity.this.showReplyPanel(false, null, null, null, false);
                        DraftQuery.cleanDraft(ChatActivity.this.dialog_id, true);
                    }

                    public void startPhotoSelectActivity() {
                        try {
                            Intent intent = new Intent();
                            intent.setType("video/*");
                            intent.setAction("android.intent.action.GET_CONTENT");
                            intent.putExtra("android.intent.extra.sizeLimit", 1610612736);
                            Intent intent2 = new Intent("android.intent.action.PICK");
                            intent2.setType("image/*");
                            intent2 = Intent.createChooser(intent2, null);
                            intent2.putExtra("android.intent.extra.INITIAL_INTENTS", new Intent[]{intent});
                            ChatActivity.this.startActivityForResult(intent2, 1);
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                });
                presentFragment(photoAlbumPickerActivity);
                return;
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 4);
        } else if (i == 2) {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.CAMERA") == 0) {
                try {
                    intent = new Intent("android.media.action.VIDEO_CAPTURE");
                    generatePicturePath = AndroidUtilities.generateVideoPath();
                    if (generatePicturePath != null) {
                        if (VERSION.SDK_INT >= 24) {
                            intent.putExtra("output", FileProvider.a(getParentActivity(), "org.ir.talaeii.provider", generatePicturePath));
                            intent.addFlags(2);
                            intent.addFlags(1);
                        } else if (VERSION.SDK_INT >= 18) {
                            intent.putExtra("output", Uri.fromFile(generatePicturePath));
                        }
                        intent.putExtra("android.intent.extra.sizeLimit", 1610612736);
                        this.currentPicturePath = generatePicturePath.getAbsolutePath();
                    }
                    startActivityForResult(intent, 2);
                    return;
                } catch (Throwable e2) {
                    FileLog.e(e2);
                    return;
                }
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.CAMERA"}, 20);
        } else if (i == 6) {
            if (AndroidUtilities.isGoogleMapsInstalled(this)) {
                if (this.currentEncryptedChat != null) {
                    i2 = 0;
                }
                r0 = new LocationActivity(i2);
                r0.setDialogId(this.dialog_id);
                r0.setDelegate(this);
                presentFragment(r0);
            }
        } else if (i == 4) {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
                r0 = new DocumentSelectActivity();
                r0.setDelegate(new DocumentSelectActivityDelegate() {
                    public void didSelectFiles(DocumentSelectActivity documentSelectActivity, ArrayList<String> arrayList) {
                        documentSelectActivity.finishFragment();
                        SendMessagesHelper.prepareSendingDocuments(arrayList, arrayList, null, null, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject, null);
                        ChatActivity.this.showReplyPanel(false, null, null, null, false);
                        DraftQuery.cleanDraft(ChatActivity.this.dialog_id, true);
                    }

                    public void startDocumentSelectActivity() {
                        try {
                            Intent intent = new Intent("android.intent.action.GET_CONTENT");
                            if (VERSION.SDK_INT >= 18) {
                                intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
                            }
                            intent.setType("*/*");
                            ChatActivity.this.startActivityForResult(intent, 21);
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                });
                presentFragment(r0);
                return;
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 4);
        } else if (i == 3) {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
                r0 = new AudioSelectActivity();
                r0.setDelegate(new AudioSelectActivityDelegate() {
                    public void didSelectAudio(ArrayList<MessageObject> arrayList) {
                        SendMessagesHelper.prepareSendingAudioDocuments(arrayList, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject);
                        ChatActivity.this.showReplyPanel(false, null, null, null, false);
                        DraftQuery.cleanDraft(ChatActivity.this.dialog_id, true);
                    }
                });
                presentFragment(r0);
                return;
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 4);
        } else if (i != 5) {
        } else {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.READ_CONTACTS") == 0) {
                try {
                    intent = new Intent("android.intent.action.PICK", Contacts.CONTENT_URI);
                    intent.setType("vnd.android.cursor.dir/phone_v2");
                    startActivityForResult(intent, 31);
                    return;
                } catch (Throwable e22) {
                    FileLog.e(e22);
                    return;
                }
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.READ_CONTACTS"}, 5);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processSelectedOption(int r13) {
        /*
        r12 = this;
        r0 = r12.selectedObject;
        if (r0 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        switch(r13) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0052;
            case 2: goto L_0x0067;
            case 3: goto L_0x0098;
            case 4: goto L_0x00a5;
            case 5: goto L_0x00f9;
            case 6: goto L_0x022e;
            case 7: goto L_0x02bf;
            case 8: goto L_0x0325;
            case 9: goto L_0x0331;
            case 10: goto L_0x035b;
            case 11: goto L_0x03f3;
            case 12: goto L_0x040a;
            case 13: goto L_0x0417;
            case 14: goto L_0x04fb;
            case 15: goto L_0x0547;
            case 16: goto L_0x0579;
            case 17: goto L_0x0586;
            case 18: goto L_0x05c4;
            case 19: goto L_0x05df;
            case 20: goto L_0x05f0;
            case 21: goto L_0x0605;
            case 22: goto L_0x061a;
            case 23: goto L_0x063d;
            case 24: goto L_0x0674;
            case 25: goto L_0x068d;
            case 26: goto L_0x0008;
            case 27: goto L_0x0008;
            case 28: goto L_0x0008;
            case 29: goto L_0x0008;
            case 30: goto L_0x0067;
            default: goto L_0x0008;
        };
    L_0x0008:
        r0 = 0;
        r12.selectedObject = r0;
        r0 = 0;
        r12.selectedObjectGroup = r0;
        goto L_0x0004;
    L_0x000f:
        r0 = r12.selectedObjectGroup;
        if (r0 == 0) goto L_0x0041;
    L_0x0013:
        r1 = 1;
        r0 = 0;
        r2 = r1;
        r1 = r0;
    L_0x0017:
        r0 = r12.selectedObjectGroup;
        r0 = r0.messages;
        r0 = r0.size();
        if (r1 >= r0) goto L_0x003b;
    L_0x0021:
        r3 = org.telegram.messenger.SendMessagesHelper.getInstance();
        r0 = r12.selectedObjectGroup;
        r0 = r0.messages;
        r0 = r0.get(r1);
        r0 = (org.telegram.messenger.MessageObject) r0;
        r4 = 0;
        r0 = r3.retrySendMessage(r0, r4);
        if (r0 != 0) goto L_0x0037;
    L_0x0036:
        r2 = 0;
    L_0x0037:
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x0017;
    L_0x003b:
        if (r2 == 0) goto L_0x0008;
    L_0x003d:
        r12.moveScrollToLastMessage();
        goto L_0x0008;
    L_0x0041:
        r0 = org.telegram.messenger.SendMessagesHelper.getInstance();
        r1 = r12.selectedObject;
        r2 = 0;
        r0 = r0.retrySendMessage(r1, r2);
        if (r0 == 0) goto L_0x0008;
    L_0x004e:
        r12.moveScrollToLastMessage();
        goto L_0x0008;
    L_0x0052:
        r0 = r12.getParentActivity();
        if (r0 != 0) goto L_0x005f;
    L_0x0058:
        r0 = 0;
        r12.selectedObject = r0;
        r0 = 0;
        r12.selectedObjectGroup = r0;
        goto L_0x0004;
    L_0x005f:
        r0 = r12.selectedObject;
        r1 = r12.selectedObjectGroup;
        r12.createDeleteMessagesAlert(r0, r1);
        goto L_0x0008;
    L_0x0067:
        r0 = 30;
        if (r13 != r0) goto L_0x0096;
    L_0x006b:
        r0 = 1;
    L_0x006c:
        QuoteForward = r0;
        r0 = r12.selectedObject;
        r12.forwardingMessage = r0;
        r0 = r12.selectedObjectGroup;
        r12.forwardingMessageGroup = r0;
        r0 = new android.os.Bundle;
        r0.<init>();
        r1 = "onlySelect";
        r2 = 1;
        r0.putBoolean(r1, r2);
        r1 = "dialogsType";
        r2 = 3;
        r0.putInt(r1, r2);
        r1 = new org.telegram.ui.DialogsActivity;
        r1.<init>(r0);
        r1.setDelegate(r12);
        r12.presentFragment(r1);
        goto L_0x0008;
    L_0x0096:
        r0 = 0;
        goto L_0x006c;
    L_0x0098:
        r0 = r12.selectedObject;
        r1 = 0;
        r2 = 0;
        r0 = r12.getMessageContent(r0, r1, r2);
        org.telegram.messenger.AndroidUtilities.addToClipboard(r0);
        goto L_0x0008;
    L_0x00a5:
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 23;
        if (r0 < r1) goto L_0x00d1;
    L_0x00ab:
        r0 = r12.getParentActivity();
        r1 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r0 = r0.checkSelfPermission(r1);
        if (r0 == 0) goto L_0x00d1;
    L_0x00b8:
        r0 = r12.getParentActivity();
        r1 = 1;
        r1 = new java.lang.String[r1];
        r2 = 0;
        r3 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r1[r2] = r3;
        r2 = 4;
        r0.requestPermissions(r1, r2);
        r0 = 0;
        r12.selectedObject = r0;
        r0 = 0;
        r12.selectedObjectGroup = r0;
        goto L_0x0004;
    L_0x00d1:
        r0 = r12.selectedObjectGroup;
        if (r0 == 0) goto L_0x00f2;
    L_0x00d5:
        r0 = 0;
        r1 = r0;
    L_0x00d7:
        r0 = r12.selectedObjectGroup;
        r0 = r0.messages;
        r0 = r0.size();
        if (r1 >= r0) goto L_0x0008;
    L_0x00e1:
        r0 = r12.selectedObjectGroup;
        r0 = r0.messages;
        r0 = r0.get(r1);
        r0 = (org.telegram.messenger.MessageObject) r0;
        r12.saveMessageToGallery(r0);
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x00d7;
    L_0x00f2:
        r0 = r12.selectedObject;
        r12.saveMessageToGallery(r0);
        goto L_0x0008;
    L_0x00f9:
        r0 = 0;
        r1 = r12.selectedObject;
        r1 = r1.messageOwner;
        r1 = r1.attachPath;
        r1 = android.text.TextUtils.isEmpty(r1);
        if (r1 != 0) goto L_0x06cb;
    L_0x0106:
        r1 = new java.io.File;
        r2 = r12.selectedObject;
        r2 = r2.messageOwner;
        r2 = r2.attachPath;
        r1.<init>(r2);
        r2 = r1.exists();
        if (r2 == 0) goto L_0x06cb;
    L_0x0117:
        if (r1 != 0) goto L_0x0128;
    L_0x0119:
        r0 = r12.selectedObject;
        r0 = r0.messageOwner;
        r0 = org.telegram.messenger.FileLoader.getPathToMessage(r0);
        r2 = r0.exists();
        if (r2 == 0) goto L_0x0128;
    L_0x0127:
        r1 = r0;
    L_0x0128:
        if (r1 == 0) goto L_0x0008;
    L_0x012a:
        r0 = r1.getName();
        r2 = "attheme";
        r0 = r0.endsWith(r2);
        if (r0 == 0) goto L_0x01d2;
    L_0x0137:
        r0 = r12.chatLayoutManager;
        if (r0 == 0) goto L_0x0168;
    L_0x013b:
        r0 = r12.chatLayoutManager;
        r0 = r0.findFirstVisibleItemPosition();
        if (r0 == 0) goto L_0x0183;
    L_0x0143:
        r12.scrollToPositionOnRecreate = r0;
        r0 = r12.chatListView;
        r2 = r12.scrollToPositionOnRecreate;
        r0 = r0.findViewHolderForAdapterPosition(r2);
        r0 = (org.telegram.ui.Components.RecyclerListView.Holder) r0;
        if (r0 == 0) goto L_0x017f;
    L_0x0151:
        r2 = r12.chatListView;
        r2 = r2.getMeasuredHeight();
        r0 = r0.itemView;
        r0 = r0.getBottom();
        r0 = r2 - r0;
        r2 = r12.chatListView;
        r2 = r2.getPaddingBottom();
        r0 = r0 - r2;
        r12.scrollToOffsetOnRecreate = r0;
    L_0x0168:
        r0 = r12.selectedObject;
        r0 = r0.getDocumentName();
        r2 = 1;
        r0 = org.telegram.ui.ActionBar.Theme.applyThemeFile(r1, r0, r2);
        if (r0 == 0) goto L_0x0187;
    L_0x0175:
        r2 = new org.telegram.ui.ThemePreviewActivity;
        r2.<init>(r1, r0);
        r12.presentFragment(r2);
        goto L_0x0008;
    L_0x017f:
        r0 = -1;
        r12.scrollToPositionOnRecreate = r0;
        goto L_0x0168;
    L_0x0183:
        r0 = -1;
        r12.scrollToPositionOnRecreate = r0;
        goto L_0x0168;
    L_0x0187:
        r0 = -1;
        r12.scrollToPositionOnRecreate = r0;
        r0 = r12.getParentActivity();
        if (r0 != 0) goto L_0x0198;
    L_0x0190:
        r0 = 0;
        r12.selectedObject = r0;
        r0 = 0;
        r12.selectedObjectGroup = r0;
        goto L_0x0004;
    L_0x0198:
        r0 = new org.telegram.ui.ActionBar.AlertDialog$Builder;
        r1 = r12.getParentActivity();
        r0.<init>(r1);
        r1 = "AppName";
        r2 = 2131230885; // 0x7f0800a5 float:1.8077835E38 double:1.0529679636E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setTitle(r1);
        r1 = "IncorrectTheme";
        r2 = 2131231651; // 0x7f0803a3 float:1.807939E38 double:1.052968342E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setMessage(r1);
        r1 = "OK";
        r2 = 2131232018; // 0x7f080512 float:1.8080133E38 double:1.0529685234E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r2 = 0;
        r0.setPositiveButton(r1, r2);
        r0 = r0.create();
        r12.showDialog(r0);
        goto L_0x0008;
    L_0x01d2:
        r0 = org.telegram.messenger.LocaleController.getInstance();
        r0 = r0.applyLanguageFile(r1);
        if (r0 == 0) goto L_0x01e6;
    L_0x01dc:
        r0 = new org.telegram.ui.LanguageSelectActivity;
        r0.<init>();
        r12.presentFragment(r0);
        goto L_0x0008;
    L_0x01e6:
        r0 = r12.getParentActivity();
        if (r0 != 0) goto L_0x01f4;
    L_0x01ec:
        r0 = 0;
        r12.selectedObject = r0;
        r0 = 0;
        r12.selectedObjectGroup = r0;
        goto L_0x0004;
    L_0x01f4:
        r0 = new org.telegram.ui.ActionBar.AlertDialog$Builder;
        r1 = r12.getParentActivity();
        r0.<init>(r1);
        r1 = "AppName";
        r2 = 2131230885; // 0x7f0800a5 float:1.8077835E38 double:1.0529679636E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setTitle(r1);
        r1 = "IncorrectLocalization";
        r2 = 2131231650; // 0x7f0803a2 float:1.8079387E38 double:1.0529683416E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setMessage(r1);
        r1 = "OK";
        r2 = 2131232018; // 0x7f080512 float:1.8080133E38 double:1.0529685234E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r2 = 0;
        r0.setPositiveButton(r1, r2);
        r0 = r0.create();
        r12.showDialog(r0);
        goto L_0x0008;
    L_0x022e:
        r0 = r12.selectedObject;
        r0 = r0.messageOwner;
        r0 = r0.attachPath;
        if (r0 == 0) goto L_0x0248;
    L_0x0236:
        r1 = r0.length();
        if (r1 <= 0) goto L_0x0248;
    L_0x023c:
        r1 = new java.io.File;
        r1.<init>(r0);
        r1 = r1.exists();
        if (r1 != 0) goto L_0x0248;
    L_0x0247:
        r0 = 0;
    L_0x0248:
        if (r0 == 0) goto L_0x0250;
    L_0x024a:
        r1 = r0.length();
        if (r1 != 0) goto L_0x025c;
    L_0x0250:
        r0 = r12.selectedObject;
        r0 = r0.messageOwner;
        r0 = org.telegram.messenger.FileLoader.getPathToMessage(r0);
        r0 = r0.toString();
    L_0x025c:
        r1 = new android.content.Intent;
        r2 = "android.intent.action.SEND";
        r1.<init>(r2);
        r2 = r12.selectedObject;
        r2 = r2.getDocument();
        r2 = r2.mime_type;
        r1.setType(r2);
        r2 = new java.io.File;
        r2.<init>(r0);
        r0 = android.os.Build.VERSION.SDK_INT;
        r3 = 24;
        if (r0 < r3) goto L_0x02b4;
    L_0x027a:
        r0 = "android.intent.extra.STREAM";
        r3 = r12.getParentActivity();	 Catch:{ Exception -> 0x02a8 }
        r4 = "org.ir.talaeii.provider";
        r3 = android.support.v4.content.FileProvider.a(r3, r4, r2);	 Catch:{ Exception -> 0x02a8 }
        r1.putExtra(r0, r3);	 Catch:{ Exception -> 0x02a8 }
        r0 = 1;
        r1.setFlags(r0);	 Catch:{ Exception -> 0x02a8 }
    L_0x028f:
        r0 = r12.getParentActivity();
        r2 = "ShareFile";
        r3 = 2131232402; // 0x7f080692 float:1.8080912E38 double:1.052968713E-314;
        r2 = org.telegram.messenger.LocaleController.getString(r2, r3);
        r1 = android.content.Intent.createChooser(r1, r2);
        r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r0.startActivityForResult(r1, r2);
        goto L_0x0008;
    L_0x02a8:
        r0 = move-exception;
        r0 = "android.intent.extra.STREAM";
        r2 = android.net.Uri.fromFile(r2);
        r1.putExtra(r0, r2);
        goto L_0x028f;
    L_0x02b4:
        r0 = "android.intent.extra.STREAM";
        r2 = android.net.Uri.fromFile(r2);
        r1.putExtra(r0, r2);
        goto L_0x028f;
    L_0x02bf:
        r0 = r12.selectedObject;
        r0 = r0.messageOwner;
        r0 = r0.attachPath;
        if (r0 == 0) goto L_0x02d9;
    L_0x02c7:
        r1 = r0.length();
        if (r1 <= 0) goto L_0x02d9;
    L_0x02cd:
        r1 = new java.io.File;
        r1.<init>(r0);
        r1 = r1.exists();
        if (r1 != 0) goto L_0x02d9;
    L_0x02d8:
        r0 = 0;
    L_0x02d9:
        if (r0 == 0) goto L_0x02e1;
    L_0x02db:
        r1 = r0.length();
        if (r1 != 0) goto L_0x02ed;
    L_0x02e1:
        r0 = r12.selectedObject;
        r0 = r0.messageOwner;
        r0 = org.telegram.messenger.FileLoader.getPathToMessage(r0);
        r0 = r0.toString();
    L_0x02ed:
        r1 = android.os.Build.VERSION.SDK_INT;
        r2 = 23;
        if (r1 < r2) goto L_0x0319;
    L_0x02f3:
        r1 = r12.getParentActivity();
        r2 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r1 = r1.checkSelfPermission(r2);
        if (r1 == 0) goto L_0x0319;
    L_0x0300:
        r0 = r12.getParentActivity();
        r1 = 1;
        r1 = new java.lang.String[r1];
        r2 = 0;
        r3 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r1[r2] = r3;
        r2 = 4;
        r0.requestPermissions(r1, r2);
        r0 = 0;
        r12.selectedObject = r0;
        r0 = 0;
        r12.selectedObjectGroup = r0;
        goto L_0x0004;
    L_0x0319:
        r1 = r12.getParentActivity();
        r2 = 0;
        r3 = 0;
        r4 = 0;
        org.telegram.messenger.MediaController.saveFile(r0, r1, r2, r3, r4);
        goto L_0x0008;
    L_0x0325:
        r1 = 1;
        r2 = r12.selectedObject;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r0 = r12;
        r0.showReplyPanel(r1, r2, r3, r4, r5);
        goto L_0x0008;
    L_0x0331:
        r0 = new org.telegram.ui.Components.StickersAlert;
        r1 = r12.getParentActivity();
        r2 = r12.selectedObject;
        r3 = r2.getInputStickerSet();
        r4 = 0;
        r2 = r12.bottomOverlayChat;
        r2 = r2.getVisibility();
        if (r2 == 0) goto L_0x0359;
    L_0x0346:
        r2 = r12.currentChat;
        r2 = org.telegram.messenger.ChatObject.canSendStickers(r2);
        if (r2 == 0) goto L_0x0359;
    L_0x034e:
        r5 = r12.chatActivityEnterView;
    L_0x0350:
        r2 = r12;
        r0.<init>(r1, r2, r3, r4, r5);
        r12.showDialog(r0);
        goto L_0x0008;
    L_0x0359:
        r5 = 0;
        goto L_0x0350;
    L_0x035b:
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 23;
        if (r0 < r1) goto L_0x0387;
    L_0x0361:
        r0 = r12.getParentActivity();
        r1 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r0 = r0.checkSelfPermission(r1);
        if (r0 == 0) goto L_0x0387;
    L_0x036e:
        r0 = r12.getParentActivity();
        r1 = 1;
        r1 = new java.lang.String[r1];
        r2 = 0;
        r3 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r1[r2] = r3;
        r2 = 4;
        r0.requestPermissions(r1, r2);
        r0 = 0;
        r12.selectedObject = r0;
        r0 = 0;
        r12.selectedObjectGroup = r0;
        goto L_0x0004;
    L_0x0387:
        r0 = r12.selectedObject;
        r0 = r0.getDocument();
        r0 = org.telegram.messenger.FileLoader.getDocumentFileName(r0);
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 == 0) goto L_0x039d;
    L_0x0397:
        r0 = r12.selectedObject;
        r0 = r0.getFileName();
    L_0x039d:
        r1 = r12.selectedObject;
        r1 = r1.messageOwner;
        r1 = r1.attachPath;
        if (r1 == 0) goto L_0x03b7;
    L_0x03a5:
        r2 = r1.length();
        if (r2 <= 0) goto L_0x03b7;
    L_0x03ab:
        r2 = new java.io.File;
        r2.<init>(r1);
        r2 = r2.exists();
        if (r2 != 0) goto L_0x03b7;
    L_0x03b6:
        r1 = 0;
    L_0x03b7:
        if (r1 == 0) goto L_0x03bf;
    L_0x03b9:
        r2 = r1.length();
        if (r2 != 0) goto L_0x03cb;
    L_0x03bf:
        r1 = r12.selectedObject;
        r1 = r1.messageOwner;
        r1 = org.telegram.messenger.FileLoader.getPathToMessage(r1);
        r1 = r1.toString();
    L_0x03cb:
        r4 = r12.getParentActivity();
        r2 = r12.selectedObject;
        r2 = r2.isMusic();
        if (r2 == 0) goto L_0x03ed;
    L_0x03d7:
        r2 = 3;
    L_0x03d8:
        r3 = r12.selectedObject;
        r3 = r3.getDocument();
        if (r3 == 0) goto L_0x03ef;
    L_0x03e0:
        r3 = r12.selectedObject;
        r3 = r3.getDocument();
        r3 = r3.mime_type;
    L_0x03e8:
        org.telegram.messenger.MediaController.saveFile(r1, r4, r2, r0, r3);
        goto L_0x0008;
    L_0x03ed:
        r2 = 2;
        goto L_0x03d8;
    L_0x03ef:
        r3 = "";
        goto L_0x03e8;
    L_0x03f3:
        r0 = r12.selectedObject;
        r0 = r0.getDocument();
        r1 = org.telegram.messenger.MessagesController.getInstance();
        r1.saveGif(r0);
        r12.showGifHint();
        r1 = r12.chatActivityEnterView;
        r1.addRecentGif(r0);
        goto L_0x0008;
    L_0x040a:
        r0 = r12.selectedObject;
        r12.startEditingMessageObject(r0);
        r0 = 0;
        r12.selectedObject = r0;
        r0 = 0;
        r12.selectedObjectGroup = r0;
        goto L_0x0008;
    L_0x0417:
        r0 = r12.selectedObject;
        r8 = r0.getId();
        r9 = new org.telegram.ui.ActionBar.AlertDialog$Builder;
        r0 = r12.getParentActivity();
        r9.<init>(r0);
        r0 = r12.currentChat;
        r0 = org.telegram.messenger.ChatObject.isChannel(r0);
        if (r0 == 0) goto L_0x04e6;
    L_0x042e:
        r0 = r12.currentChat;
        r0 = r0.megagroup;
        if (r0 == 0) goto L_0x04e6;
    L_0x0434:
        r0 = "PinMessageAlert";
        r1 = 2131232164; // 0x7f0805a4 float:1.808043E38 double:1.0529685955E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        r9.setMessage(r0);
        r0 = 1;
        r7 = new boolean[r0];
        r0 = 0;
        r1 = 1;
        r7[r0] = r1;
        r10 = new android.widget.FrameLayout;
        r0 = r12.getParentActivity();
        r10.<init>(r0);
        r11 = new org.telegram.ui.Cells.CheckBoxCell;
        r0 = r12.getParentActivity();
        r1 = 1;
        r11.<init>(r0, r1);
        r0 = 0;
        r0 = org.telegram.ui.ActionBar.Theme.getSelectorDrawable(r0);
        r11.setBackgroundDrawable(r0);
        r0 = "PinNotify";
        r1 = 2131232165; // 0x7f0805a5 float:1.8080432E38 double:1.052968596E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        r1 = "";
        r2 = 1;
        r3 = 0;
        r11.setText(r0, r1, r2, r3);
        r0 = org.telegram.messenger.LocaleController.isRTL;
        if (r0 == 0) goto L_0x04dd;
    L_0x0479:
        r0 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r0 = org.telegram.messenger.AndroidUtilities.dp(r0);
    L_0x047f:
        r2 = 0;
        r1 = org.telegram.messenger.LocaleController.isRTL;
        if (r1 == 0) goto L_0x04df;
    L_0x0484:
        r1 = 0;
    L_0x0485:
        r3 = 0;
        r11.setPadding(r0, r2, r1, r3);
        r0 = -1;
        r1 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
        r2 = 51;
        r3 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r4 = 0;
        r5 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r6 = 0;
        r0 = org.telegram.ui.Components.LayoutHelper.createFrame(r0, r1, r2, r3, r4, r5, r6);
        r10.addView(r11, r0);
        r0 = new org.telegram.ui.ChatActivity$114;
        r0.<init>(r7);
        r11.setOnClickListener(r0);
        r9.setView(r10);
        r0 = r7;
    L_0x04a7:
        r1 = "OK";
        r2 = 2131232018; // 0x7f080512 float:1.8080133E38 double:1.0529685234E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r2 = new org.telegram.ui.ChatActivity$115;
        r2.<init>(r8, r0);
        r9.setPositiveButton(r1, r2);
        r0 = "AppName";
        r1 = 2131230885; // 0x7f0800a5 float:1.8077835E38 double:1.0529679636E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        r9.setTitle(r0);
        r0 = "Cancel";
        r1 = 2131231022; // 0x7f08012e float:1.8078113E38 double:1.0529680313E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        r1 = 0;
        r9.setNegativeButton(r0, r1);
        r0 = r9.create();
        r12.showDialog(r0);
        goto L_0x0008;
    L_0x04dd:
        r0 = 0;
        goto L_0x047f;
    L_0x04df:
        r1 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r1 = org.telegram.messenger.AndroidUtilities.dp(r1);
        goto L_0x0485;
    L_0x04e6:
        r0 = "PinMessageAlertChannel";
        r1 = 2131232925; // 0x7f08089d float:1.8081973E38 double:1.0529689715E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        r9.setMessage(r0);
        r0 = 1;
        r0 = new boolean[r0];
        r1 = 0;
        r2 = 0;
        r0[r1] = r2;
        goto L_0x04a7;
    L_0x04fb:
        r0 = new org.telegram.ui.ActionBar.AlertDialog$Builder;
        r1 = r12.getParentActivity();
        r0.<init>(r1);
        r1 = "UnpinMessageAlert";
        r2 = 2131232536; // 0x7f080718 float:1.8081184E38 double:1.0529687793E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setMessage(r1);
        r1 = "OK";
        r2 = 2131232018; // 0x7f080512 float:1.8080133E38 double:1.0529685234E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r2 = new org.telegram.ui.ChatActivity$116;
        r2.<init>();
        r0.setPositiveButton(r1, r2);
        r1 = "AppName";
        r2 = 2131230885; // 0x7f0800a5 float:1.8077835E38 double:1.0529679636E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r0.setTitle(r1);
        r1 = "Cancel";
        r2 = 2131231022; // 0x7f08012e float:1.8078113E38 double:1.0529680313E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r2);
        r2 = 0;
        r0.setNegativeButton(r1, r2);
        r0 = r0.create();
        r12.showDialog(r0);
        goto L_0x0008;
    L_0x0547:
        r0 = new android.os.Bundle;
        r0.<init>();
        r1 = "user_id";
        r2 = r12.selectedObject;
        r2 = r2.messageOwner;
        r2 = r2.media;
        r2 = r2.user_id;
        r0.putInt(r1, r2);
        r1 = "phone";
        r2 = r12.selectedObject;
        r2 = r2.messageOwner;
        r2 = r2.media;
        r2 = r2.phone_number;
        r0.putString(r1, r2);
        r1 = "addContact";
        r2 = 1;
        r0.putBoolean(r1, r2);
        r1 = new org.telegram.ui.ContactAddActivity;
        r1.<init>(r0);
        r12.presentFragment(r1);
        goto L_0x0008;
    L_0x0579:
        r0 = r12.selectedObject;
        r0 = r0.messageOwner;
        r0 = r0.media;
        r0 = r0.phone_number;
        org.telegram.messenger.AndroidUtilities.addToClipboard(r0);
        goto L_0x0008;
    L_0x0586:
        r0 = new android.content.Intent;	 Catch:{ Exception -> 0x05be }
        r1 = "android.intent.action.DIAL";
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x05be }
        r2.<init>();	 Catch:{ Exception -> 0x05be }
        r3 = "tel:";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x05be }
        r3 = r12.selectedObject;	 Catch:{ Exception -> 0x05be }
        r3 = r3.messageOwner;	 Catch:{ Exception -> 0x05be }
        r3 = r3.media;	 Catch:{ Exception -> 0x05be }
        r3 = r3.phone_number;	 Catch:{ Exception -> 0x05be }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x05be }
        r2 = r2.toString();	 Catch:{ Exception -> 0x05be }
        r2 = android.net.Uri.parse(r2);	 Catch:{ Exception -> 0x05be }
        r0.<init>(r1, r2);	 Catch:{ Exception -> 0x05be }
        r1 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r0.addFlags(r1);	 Catch:{ Exception -> 0x05be }
        r1 = r12.getParentActivity();	 Catch:{ Exception -> 0x05be }
        r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r1.startActivityForResult(r0, r2);	 Catch:{ Exception -> 0x05be }
        goto L_0x0008;
    L_0x05be:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x0008;
    L_0x05c4:
        r0 = r12.currentUser;
        if (r0 == 0) goto L_0x0008;
    L_0x05c8:
        r0 = r12.currentUser;
        r1 = r12.getParentActivity();
        r2 = org.telegram.messenger.MessagesController.getInstance();
        r3 = r12.currentUser;
        r3 = r3.id;
        r2 = r2.getUserFull(r3);
        org.telegram.ui.Components.voip.VoIPHelper.startCall(r0, r1, r2);
        goto L_0x0008;
    L_0x05df:
        r1 = r12.getParentActivity();
        r0 = r12.selectedObject;
        r0 = r0.messageOwner;
        r0 = r0.action;
        r0 = (org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall) r0;
        org.telegram.ui.Components.voip.VoIPHelper.showRateAlert(r1, r0);
        goto L_0x0008;
    L_0x05f0:
        r0 = 2;
        r1 = r12.selectedObject;
        r1 = r1.getDocument();
        r2 = java.lang.System.currentTimeMillis();
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2 = r2 / r4;
        r2 = (int) r2;
        r3 = 0;
        org.telegram.messenger.query.StickersQuery.addRecentSticker(r0, r1, r2, r3);
        goto L_0x0008;
    L_0x0605:
        r0 = 2;
        r1 = r12.selectedObject;
        r1 = r1.getDocument();
        r2 = java.lang.System.currentTimeMillis();
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2 = r2 / r4;
        r2 = (int) r2;
        r3 = 1;
        org.telegram.messenger.query.StickersQuery.addRecentSticker(r0, r1, r2, r3);
        goto L_0x0008;
    L_0x061a:
        r0 = new org.telegram.tgnet.TLRPC$TL_channels_exportMessageLink;
        r0.<init>();
        r1 = r12.selectedObject;
        r1 = r1.getId();
        r0.id = r1;
        r1 = r12.currentChat;
        r1 = org.telegram.messenger.MessagesController.getInputChannel(r1);
        r0.channel = r1;
        r1 = org.telegram.tgnet.ConnectionsManager.getInstance();
        r2 = new org.telegram.ui.ChatActivity$117;
        r2.<init>();
        r1.sendRequest(r0, r2);
        goto L_0x0008;
    L_0x063d:
        r0 = r12.selectedObject;
        r12.forwardingMessage = r0;
        r0 = 1;
        r12.showingDialog = r0;
        r2 = new java.util.ArrayList;
        r2.<init>();
        r0 = r12.forwardingMessage;
        r2.add(r0);
        r0 = new org.telegram.ui.Components.ShareAlert;
        r1 = r12.getParentActivity();
        r3 = "telegram";
        r4 = 1;
        r5 = "";
        r6 = 0;
        r0.<init>(r1, r2, r3, r4, r5, r6);
        r12.showDialog(r0);
        r1 = new org.telegram.ui.ChatActivity$118;
        r1.<init>();
        r0.setOnDismissListener(r1);
        r1 = new org.telegram.ui.ChatActivity$119;
        r1.<init>();
        r0.setOnCancelListener(r1);
        goto L_0x0008;
    L_0x0674:
        r0 = new android.os.Bundle;
        r0.<init>();
        r1 = "isAdvancedForward";
        r2 = 1;
        r0.putBoolean(r1, r2);
        r1 = new org.telegram.ui.ChatActivity;
        r1.<init>(r0);
        r0 = r12.selectedObject;
        r1.setMessageObject(r0);
        r12.presentFragment(r1);
    L_0x068d:
        r0 = org.telegram.messenger.MessagesStorage.getInstance();
        r2 = 111444999; // 0x6a48407 float:6.188394E-35 double:5.50611454E-316;
        r0 = r0.getTotalMessageCount(r2);
        r1 = new org.telegram.tgnet.TLRPC$TL_messages_messages;
        r1.<init>();
        r2 = r12.selectedObject;
        r2 = r2.messageOwner;
        r4 = java.lang.System.currentTimeMillis();
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r6;
        r3 = (int) r4;
        r2.date = r3;
        r2 = r12.selectedObject;
        r2 = r2.messageOwner;
        r0 = r0 + 1;
        r2.id = r0;
        r0 = r1.messages;
        r2 = r12.selectedObject;
        r2 = r2.messageOwner;
        r0.add(r2);
        r0 = org.telegram.messenger.MessagesStorage.getInstance();
        r2 = 111444999; // 0x6a48407 float:6.188394E-35 double:5.50611454E-316;
        r4 = -1;
        r5 = 0;
        r6 = 0;
        r0.putMessages(r1, r2, r4, r5, r6);
        goto L_0x0008;
    L_0x06cb:
        r1 = r0;
        goto L_0x0117;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.ChatActivity.processSelectedOption(int):void");
    }

    private void removeMessageObject(MessageObject messageObject) {
        int indexOf = this.messages.indexOf(messageObject);
        if (indexOf != -1) {
            this.messages.remove(indexOf);
            if (this.chatAdapter != null) {
                this.chatAdapter.notifyItemRemoved(indexOf + this.chatAdapter.messagesStartRow);
            }
        }
    }

    private void removeUnreadPlane() {
        if (this.unreadMessageObject != null) {
            boolean[] zArr = this.forwardEndReached;
            this.forwardEndReached[1] = true;
            zArr[0] = true;
            this.first_unread_id = 0;
            this.last_message_id = 0;
            this.createUnreadMessageAfterId = 0;
            this.createUnreadMessageAfterIdLoading = false;
            this.unread_to_load = 0;
            removeMessageObject(this.unreadMessageObject);
            this.unreadMessageObject = null;
        }
    }

    private void saveMessageToGallery(MessageObject messageObject) {
        String str = messageObject.messageOwner.attachPath;
        if (!(TextUtils.isEmpty(str) || new File(str).exists())) {
            str = null;
        }
        if (TextUtils.isEmpty(str)) {
            str = FileLoader.getPathToMessage(messageObject.messageOwner).toString();
        }
        MediaController.saveFile(str, getParentActivity(), messageObject.type == 3 ? 1 : 0, null, null);
    }

    private void scrollToLastMessage(boolean z) {
        if (!this.forwardEndReached[0] || this.first_unread_id != 0 || this.startLoadFromMessageId != 0) {
            clearChatData();
            this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
            MessagesController instance = MessagesController.getInstance();
            long j = this.dialog_id;
            int i = this.classGuid;
            boolean isChannel = ChatObject.isChannel(this.currentChat);
            int i2 = this.lastLoadIndex;
            this.lastLoadIndex = i2 + 1;
            instance.loadMessages(j, 30, 0, 0, true, 0, i, 0, 0, isChannel, i2);
        } else if (z && this.chatLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
            showPagedownButton(false, true);
            this.highlightMessageId = Integer.MAX_VALUE;
            updateVisibleRows();
        } else {
            this.chatLayoutManager.scrollToPositionWithOffset(0, 0);
        }
    }

    private void searchLinks(final CharSequence charSequence, final boolean z) {
        boolean z2 = true;
        if (this.currentEncryptedChat == null || (MessagesController.getInstance().secretWebpagePreview != 0 && AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 46)) {
            if (z && this.foundWebPage != null) {
                if (this.foundWebPage.url != null) {
                    int indexOf = TextUtils.indexOf(charSequence, this.foundWebPage.url);
                    boolean z3;
                    char charAt;
                    boolean z4;
                    if (indexOf != -1) {
                        if (this.foundWebPage.url.length() + indexOf != charSequence.length()) {
                            z2 = false;
                        }
                        z3 = z2;
                        charAt = !z2 ? charSequence.charAt(this.foundWebPage.url.length() + indexOf) : '\u0000';
                        z4 = z3;
                    } else if (this.foundWebPage.display_url != null) {
                        indexOf = TextUtils.indexOf(charSequence, this.foundWebPage.display_url);
                        if (indexOf == -1 || this.foundWebPage.display_url.length() + indexOf != charSequence.length()) {
                            z2 = false;
                        }
                        char charAt2 = (indexOf == -1 || z2) ? '\u0000' : charSequence.charAt(this.foundWebPage.display_url.length() + indexOf);
                        z3 = z2;
                        charAt = charAt2;
                        z4 = z3;
                    } else {
                        z4 = false;
                        z2 = false;
                    }
                    if (indexOf != -1 && (r0 || r3 == ' ' || r3 == ',' || r3 == '.' || r3 == '!' || r3 == '/')) {
                        return;
                    }
                }
                this.pendingLinkSearchString = null;
                showReplyPanel(false, null, null, this.foundWebPage, false);
            }
            Utilities.searchQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.ui.ChatActivity$82$1 */
                class C42651 implements Runnable {
                    C42651() {
                    }

                    public void run() {
                        if (ChatActivity.this.foundWebPage != null) {
                            ChatActivity.this.showReplyPanel(false, null, null, ChatActivity.this.foundWebPage, false);
                            ChatActivity.this.foundWebPage = null;
                        }
                    }
                }

                /* renamed from: org.telegram.ui.ChatActivity$82$2 */
                class C42662 implements Runnable {
                    C42662() {
                    }

                    public void run() {
                        if (ChatActivity.this.foundWebPage != null) {
                            ChatActivity.this.showReplyPanel(false, null, null, ChatActivity.this.foundWebPage, false);
                            ChatActivity.this.foundWebPage = null;
                        }
                    }
                }

                /* renamed from: org.telegram.ui.ChatActivity$82$3 */
                class C42683 implements Runnable {

                    /* renamed from: org.telegram.ui.ChatActivity$82$3$1 */
                    class C42671 implements OnClickListener {
                        C42671() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            MessagesController.getInstance().secretWebpagePreview = 1;
                            ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("secretWebpage2", MessagesController.getInstance().secretWebpagePreview).commit();
                            ChatActivity.this.foundUrls = null;
                            ChatActivity.this.searchLinks(charSequence, z);
                        }
                    }

                    C42683() {
                    }

                    public void run() {
                        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C42671());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        builder.setMessage(LocaleController.getString("SecretLinkPreviewAlert", R.string.SecretLinkPreviewAlert));
                        ChatActivity.this.showDialog(builder.create());
                        MessagesController.getInstance().secretWebpagePreview = 0;
                        ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("secretWebpage2", MessagesController.getInstance().secretWebpagePreview).commit();
                    }
                }

                public void run() {
                    boolean z = true;
                    if (ChatActivity.this.linkSearchRequestId != 0) {
                        ConnectionsManager.getInstance().cancelRequest(ChatActivity.this.linkSearchRequestId, true);
                        ChatActivity.this.linkSearchRequestId = 0;
                    }
                    CharSequence join;
                    try {
                        Matcher matcher = AndroidUtilities.WEB_URL.matcher(charSequence);
                        Iterable iterable = null;
                        while (matcher.find()) {
                            if (matcher.start() <= 0 || charSequence.charAt(matcher.start() - 1) != '@') {
                                ArrayList arrayList;
                                if (iterable == null) {
                                    arrayList = new ArrayList();
                                } else {
                                    Iterable iterable2 = iterable;
                                }
                                arrayList.add(charSequence.subSequence(matcher.start(), matcher.end()));
                                iterable = arrayList;
                            }
                        }
                        if (!(iterable == null || ChatActivity.this.foundUrls == null || iterable.size() != ChatActivity.this.foundUrls.size())) {
                            int i = 0;
                            while (i < iterable.size()) {
                                boolean z2 = !TextUtils.equals((CharSequence) iterable.get(i), (CharSequence) ChatActivity.this.foundUrls.get(i)) ? false : z;
                                i++;
                                z = z2;
                            }
                            if (z) {
                                return;
                            }
                        }
                        ChatActivity.this.foundUrls = iterable;
                        if (iterable == null) {
                            AndroidUtilities.runOnUIThread(new C42651());
                            return;
                        }
                        join = TextUtils.join(" ", iterable);
                        if (ChatActivity.this.currentEncryptedChat == null || MessagesController.getInstance().secretWebpagePreview != 2) {
                            final TLObject tLRPC$TL_messages_getWebPagePreview = new TLRPC$TL_messages_getWebPagePreview();
                            if (join instanceof String) {
                                tLRPC$TL_messages_getWebPagePreview.message = (String) join;
                            } else {
                                tLRPC$TL_messages_getWebPagePreview.message = join.toString();
                            }
                            ChatActivity.this.linkSearchRequestId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getWebPagePreview, new RequestDelegate() {
                                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                                    AndroidUtilities.runOnUIThread(new Runnable() {
                                        public void run() {
                                            ChatActivity.this.linkSearchRequestId = 0;
                                            if (tLRPC$TL_error != null) {
                                                return;
                                            }
                                            if (tLObject instanceof TLRPC$TL_messageMediaWebPage) {
                                                ChatActivity.this.foundWebPage = ((TLRPC$TL_messageMediaWebPage) tLObject).webpage;
                                                if ((ChatActivity.this.foundWebPage instanceof TLRPC$TL_webPage) || (ChatActivity.this.foundWebPage instanceof TLRPC$TL_webPagePending)) {
                                                    if (ChatActivity.this.foundWebPage instanceof TLRPC$TL_webPagePending) {
                                                        ChatActivity.this.pendingLinkSearchString = tLRPC$TL_messages_getWebPagePreview.message;
                                                    }
                                                    if (ChatActivity.this.currentEncryptedChat != null && (ChatActivity.this.foundWebPage instanceof TLRPC$TL_webPagePending)) {
                                                        ChatActivity.this.foundWebPage.url = tLRPC$TL_messages_getWebPagePreview.message;
                                                    }
                                                    ChatActivity.this.showReplyPanel(true, null, null, ChatActivity.this.foundWebPage, false);
                                                } else if (ChatActivity.this.foundWebPage != null) {
                                                    ChatActivity.this.showReplyPanel(false, null, null, ChatActivity.this.foundWebPage, false);
                                                    ChatActivity.this.foundWebPage = null;
                                                }
                                            } else if (ChatActivity.this.foundWebPage != null) {
                                                ChatActivity.this.showReplyPanel(false, null, null, ChatActivity.this.foundWebPage, false);
                                                ChatActivity.this.foundWebPage = null;
                                            }
                                        }
                                    });
                                }
                            });
                            ConnectionsManager.getInstance().bindRequestToGuid(ChatActivity.this.linkSearchRequestId, ChatActivity.this.classGuid);
                            return;
                        }
                        AndroidUtilities.runOnUIThread(new C42683());
                    } catch (Throwable e) {
                        FileLog.e(e);
                        String toLowerCase = charSequence.toString().toLowerCase();
                        if (charSequence.length() < 13 || !(toLowerCase.contains("http://") || toLowerCase.contains("https://"))) {
                            AndroidUtilities.runOnUIThread(new C42662());
                            return;
                        }
                        join = charSequence;
                    }
                }
            });
        }
    }

    private void sendBotInlineResult(BotInlineResult botInlineResult) {
        int contextBotId = this.mentionsAdapter.getContextBotId();
        HashMap hashMap = new HashMap();
        hashMap.put(TtmlNode.ATTR_ID, botInlineResult.id);
        hashMap.put("query_id", TtmlNode.ANONYMOUS_REGION_ID + botInlineResult.query_id);
        hashMap.put("bot", TtmlNode.ANONYMOUS_REGION_ID + contextBotId);
        hashMap.put("bot_name", this.mentionsAdapter.getContextBotName());
        SendMessagesHelper.prepareSendingBotContextResult(botInlineResult, hashMap, this.dialog_id, this.replyingMessageObject);
        this.chatActivityEnterView.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
        showReplyPanel(false, null, null, null, false);
        SearchQuery.increaseInlineRaiting(contextBotId);
    }

    private void sendMessage(String str) {
        this.chatActivityEnterView.setFieldText(str);
        this.chatActivityEnterView.getSendButton().performClick();
        this.chatActivityEnterView.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
    }

    private boolean sendSecretMessageRead(MessageObject messageObject) {
        int i = 0;
        if (messageObject == null || messageObject.isOut() || !messageObject.isSecretMedia() || messageObject.messageOwner.destroyTime != 0 || messageObject.messageOwner.ttl <= 0) {
            return false;
        }
        if (this.currentEncryptedChat != null) {
            MessagesController.getInstance().markMessageAsRead(this.dialog_id, messageObject.messageOwner.random_id, messageObject.messageOwner.ttl);
        } else {
            MessagesController instance = MessagesController.getInstance();
            int id = messageObject.getId();
            if (ChatObject.isChannel(this.currentChat)) {
                i = this.currentChat.id;
            }
            instance.markMessageAsRead(id, i, messageObject.messageOwner.ttl);
        }
        messageObject.messageOwner.destroyTime = messageObject.messageOwner.ttl + ConnectionsManager.getInstance().getCurrentTime();
        return true;
    }

    private void sendUriAsDocument(Uri uri) {
        if (uri != null) {
            String uri2 = uri.toString();
            if (uri2.contains("com.google.android.apps.photos.contentprovider")) {
                try {
                    uri2 = uri2.split("/1/")[1];
                    int indexOf = uri2.indexOf("/ACTUAL");
                    if (indexOf != -1) {
                        uri = Uri.parse(URLDecoder.decode(uri2.substring(0, indexOf), C3446C.UTF8_NAME));
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
            String path = AndroidUtilities.getPath(uri);
            if (path == null) {
                path = uri.toString();
                uri2 = MediaController.copyFileToCache(uri, "file");
            } else {
                uri2 = path;
            }
            if (uri2 == null) {
                showAttachmentError();
                return;
            }
            SendMessagesHelper.prepareSendingDocument(uri2, path, null, null, this.dialog_id, this.replyingMessageObject, null);
        }
    }

    private void setCellSelectionBackground(MessageObject messageObject, ChatMessageCell chatMessageCell, int i) {
        Drawable drawable;
        Object obj = null;
        GroupedMessages validGroupedMessage = getValidGroupedMessage(messageObject);
        if (validGroupedMessage != null) {
            for (int i2 = 0; i2 < validGroupedMessage.messages.size(); i2++) {
                if (!this.selectedMessagesIds[i].containsKey(Integer.valueOf(((MessageObject) validGroupedMessage.messages.get(i2)).getId()))) {
                    obj = 1;
                    break;
                }
            }
            if (obj == null) {
                drawable = null;
                if (drawable != null) {
                    chatMessageCell.setBackgroundColor(Theme.getColor(Theme.key_chat_selectedBackground));
                } else {
                    chatMessageCell.setBackground(null);
                }
            }
        }
        Object obj2 = validGroupedMessage;
        if (drawable != null) {
            chatMessageCell.setBackground(null);
        } else {
            chatMessageCell.setBackgroundColor(Theme.getColor(Theme.key_chat_selectedBackground));
        }
    }

    private void showAttachmentError() {
        if (getParentActivity() != null) {
            Toast.makeText(getParentActivity(), LocaleController.getString("UnsupportedAttachment", R.string.UnsupportedAttachment), 0).show();
        }
    }

    private void showCantOpenAlert(BaseFragment baseFragment, String str, final boolean z) {
        if (baseFragment != null) {
            try {
                if (baseFragment.getParentActivity() != null) {
                    C0766a c0766a = new C0766a(baseFragment.getParentActivity());
                    c0766a.a(TtmlNode.ANONYMOUS_REGION_ID);
                    c0766a.a(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (z) {
                                ChatActivity.this.parentLayout.onBackPressed();
                            }
                        }
                    });
                    c0766a.a(new OnCancelListener() {
                        public void onCancel(DialogInterface dialogInterface) {
                            if (z && ChatActivity.this.parentLayout != null) {
                                ChatActivity.this.parentLayout.onBackPressed();
                            }
                        }
                    });
                    c0766a.b(str);
                    c0766a.a(false);
                    baseFragment.showDialog(c0766a.b());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showGifHint() {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        if (!sharedPreferences.getBoolean("gifhint", false)) {
            sharedPreferences.edit().putBoolean("gifhint", true).commit();
            if (getParentActivity() != null && this.fragmentView != null && this.gifHintTextView == null) {
                if (this.allowContextBotPanelSecond) {
                    SizeNotifierFrameLayout sizeNotifierFrameLayout = (SizeNotifierFrameLayout) this.fragmentView;
                    int indexOfChild = sizeNotifierFrameLayout.indexOfChild(this.chatActivityEnterView);
                    if (indexOfChild != -1) {
                        this.chatActivityEnterView.setOpenGifsTabFirst();
                        this.emojiButtonRed = new View(getParentActivity());
                        this.emojiButtonRed.setBackgroundResource(R.drawable.redcircle);
                        sizeNotifierFrameLayout.addView(this.emojiButtonRed, indexOfChild + 1, LayoutHelper.createFrame(10, 10.0f, 83, 30.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 27.0f));
                        this.gifHintTextView = new TextView(getParentActivity());
                        this.gifHintTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
                        this.gifHintTextView.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
                        this.gifHintTextView.setTextSize(1, 14.0f);
                        this.gifHintTextView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f));
                        this.gifHintTextView.setText(LocaleController.getString("TapHereGifs", R.string.TapHereGifs));
                        this.gifHintTextView.setGravity(16);
                        sizeNotifierFrameLayout.addView(this.gifHintTextView, indexOfChild + 1, LayoutHelper.createFrame(-2, -2.0f, 83, 5.0f, BitmapDescriptorFactory.HUE_RED, 5.0f, 3.0f));
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.gifHintTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.emojiButtonRed, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
                        animatorSet.addListener(new AnimatorListenerAdapter() {

                            /* renamed from: org.telegram.ui.ChatActivity$75$1 */
                            class C42631 implements Runnable {

                                /* renamed from: org.telegram.ui.ChatActivity$75$1$1 */
                                class C42621 extends AnimatorListenerAdapter {
                                    C42621() {
                                    }

                                    public void onAnimationEnd(Animator animator) {
                                        if (ChatActivity.this.gifHintTextView != null) {
                                            ChatActivity.this.gifHintTextView.setVisibility(8);
                                        }
                                    }
                                }

                                C42631() {
                                }

                                public void run() {
                                    if (ChatActivity.this.gifHintTextView != null) {
                                        AnimatorSet animatorSet = new AnimatorSet();
                                        Animator[] animatorArr = new Animator[1];
                                        animatorArr[0] = ObjectAnimator.ofFloat(ChatActivity.this.gifHintTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                        animatorSet.playTogether(animatorArr);
                                        animatorSet.addListener(new C42621());
                                        animatorSet.setDuration(300);
                                        animatorSet.start();
                                    }
                                }
                            }

                            public void onAnimationEnd(Animator animator) {
                                AndroidUtilities.runOnUIThread(new C42631(), 2000);
                            }
                        });
                        animatorSet.setDuration(300);
                        animatorSet.start();
                    }
                } else if (this.chatActivityEnterView != null) {
                    this.chatActivityEnterView.setOpenGifsTabFirst();
                }
            }
        }
    }

    private void showLoadingDialog(Context context, String str, String str2) {
        if (getParentActivity() == null) {
            return;
        }
        if (this.dialogLoading == null) {
            this.dialogLoading = new ProgressDialog(getParentActivity());
            this.dialogLoading.setTitle(str);
            this.dialogLoading.setMessage(str2);
            this.dialogLoading.show();
            return;
        }
        this.dialogLoading.show();
    }

    private void showMediaBannedHint() {
        if (getParentActivity() != null && this.currentChat != null && this.currentChat.banned_rights != null && this.fragmentView != null) {
            if (this.mediaBanTooltip == null || this.mediaBanTooltip.getVisibility() != 0) {
                SizeNotifierFrameLayout sizeNotifierFrameLayout = (SizeNotifierFrameLayout) this.fragmentView;
                int indexOfChild = sizeNotifierFrameLayout.indexOfChild(this.chatActivityEnterView);
                if (indexOfChild != -1) {
                    if (this.mediaBanTooltip == null) {
                        this.mediaBanTooltip = new CorrectlyMeasuringTextView(getParentActivity());
                        this.mediaBanTooltip.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
                        this.mediaBanTooltip.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
                        this.mediaBanTooltip.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f));
                        this.mediaBanTooltip.setGravity(16);
                        this.mediaBanTooltip.setTextSize(1, 14.0f);
                        sizeNotifierFrameLayout.addView(this.mediaBanTooltip, indexOfChild + 1, LayoutHelper.createFrame(-2, -2.0f, 85, 30.0f, BitmapDescriptorFactory.HUE_RED, 5.0f, 3.0f));
                    }
                    if (AndroidUtilities.isBannedForever(this.currentChat.banned_rights.until_date)) {
                        this.mediaBanTooltip.setText(LocaleController.getString("AttachMediaRestrictedForever", R.string.AttachMediaRestrictedForever));
                    } else {
                        this.mediaBanTooltip.setText(LocaleController.formatString("AttachMediaRestricted", R.string.AttachMediaRestricted, new Object[]{LocaleController.formatDateForBan((long) this.currentChat.banned_rights.until_date)}));
                    }
                    this.mediaBanTooltip.setVisibility(0);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.mediaBanTooltip, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
                    animatorSet.addListener(new AnimatorListenerAdapter() {

                        /* renamed from: org.telegram.ui.ChatActivity$74$1 */
                        class C42611 implements Runnable {

                            /* renamed from: org.telegram.ui.ChatActivity$74$1$1 */
                            class C42601 extends AnimatorListenerAdapter {
                                C42601() {
                                }

                                public void onAnimationEnd(Animator animator) {
                                    if (ChatActivity.this.mediaBanTooltip != null) {
                                        ChatActivity.this.mediaBanTooltip.setVisibility(8);
                                    }
                                }
                            }

                            C42611() {
                            }

                            public void run() {
                                if (ChatActivity.this.mediaBanTooltip != null) {
                                    AnimatorSet animatorSet = new AnimatorSet();
                                    Animator[] animatorArr = new Animator[1];
                                    animatorArr[0] = ObjectAnimator.ofFloat(ChatActivity.this.mediaBanTooltip, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                    animatorSet.playTogether(animatorArr);
                                    animatorSet.addListener(new C42601());
                                    animatorSet.setDuration(300);
                                    animatorSet.start();
                                }
                            }
                        }

                        public void onAnimationEnd(Animator animator) {
                            AndroidUtilities.runOnUIThread(new C42611(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                        }
                    });
                    animatorSet.setDuration(300);
                    animatorSet.start();
                }
            }
        }
    }

    private void showMentiondownButton(boolean z, boolean z2) {
        if (this.mentiondownButton != null) {
            if (!z) {
                this.returnToMessageId = 0;
                if (this.mentiondownButton.getTag() != null) {
                    this.mentiondownButton.setTag(null);
                    if (this.mentiondownButtonAnimation != null) {
                        this.mentiondownButtonAnimation.cancel();
                        this.mentiondownButtonAnimation = null;
                    }
                    if (z2) {
                        if (this.pagedownButton.getVisibility() == 0) {
                            this.mentiondownButtonAnimation = ObjectAnimator.ofFloat(this.mentiondownButton, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}).setDuration(200);
                        } else {
                            this.mentiondownButtonAnimation = ObjectAnimator.ofFloat(this.mentiondownButton, "translationY", new float[]{(float) AndroidUtilities.dp(100.0f)}).setDuration(200);
                        }
                        this.mentiondownButtonAnimation.addListener(new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator animator) {
                                ChatActivity.this.mentiondownButtonCounter.setVisibility(4);
                                ChatActivity.this.mentiondownButton.setVisibility(4);
                            }
                        });
                        this.mentiondownButtonAnimation.start();
                        return;
                    }
                    this.mentiondownButton.setVisibility(4);
                }
            } else if (this.mentiondownButton.getTag() == null) {
                if (this.mentiondownButtonAnimation != null) {
                    this.mentiondownButtonAnimation.cancel();
                    this.mentiondownButtonAnimation = null;
                }
                if (z2) {
                    this.mentiondownButton.setVisibility(0);
                    this.mentiondownButton.setTag(Integer.valueOf(1));
                    if (this.pagedownButton.getVisibility() == 0) {
                        this.mentiondownButton.setTranslationY((float) (-AndroidUtilities.dp(72.0f)));
                        this.mentiondownButtonAnimation = ObjectAnimator.ofFloat(this.mentiondownButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}).setDuration(200);
                    } else {
                        if (this.mentiondownButton.getTranslationY() == BitmapDescriptorFactory.HUE_RED) {
                            this.mentiondownButton.setTranslationY((float) AndroidUtilities.dp(100.0f));
                        }
                        this.mentiondownButtonAnimation = ObjectAnimator.ofFloat(this.mentiondownButton, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED}).setDuration(200);
                    }
                    this.mentiondownButtonAnimation.start();
                    return;
                }
                this.mentiondownButton.setVisibility(0);
            }
        }
    }

    private void showPagedownButton(boolean z, boolean z2) {
        if (this.pagedownButton != null) {
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (z) {
                this.pagedownButtonShowedByScroll = false;
                if (this.pagedownButton.getTag() == null) {
                    if (this.pagedownButtonAnimation != null) {
                        this.pagedownButtonAnimation.cancel();
                        this.pagedownButtonAnimation = null;
                    }
                    if (z2) {
                        if (this.pagedownButton.getTranslationY() == BitmapDescriptorFactory.HUE_RED) {
                            this.pagedownButton.setTranslationY((float) AndroidUtilities.dp(100.0f));
                        }
                        this.pagedownButton.setVisibility(0);
                        this.pagedownButton.setTag(Integer.valueOf(1));
                        this.pagedownButtonAnimation = new AnimatorSet();
                        if (this.mentiondownButton.getVisibility() == 0) {
                            animatorSet = this.pagedownButtonAnimation;
                            animatorArr = new Animator[2];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.pagedownButton, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                            animatorArr[1] = ObjectAnimator.ofFloat(this.mentiondownButton, "translationY", new float[]{(float) (-AndroidUtilities.dp(72.0f))});
                            animatorSet.playTogether(animatorArr);
                        } else {
                            animatorSet = this.pagedownButtonAnimation;
                            animatorArr = new Animator[1];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.pagedownButton, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                            animatorSet.playTogether(animatorArr);
                        }
                        this.pagedownButtonAnimation.setDuration(200);
                        this.pagedownButtonAnimation.start();
                        return;
                    }
                    this.pagedownButton.setVisibility(0);
                    return;
                }
                return;
            }
            this.returnToMessageId = 0;
            this.newUnreadMessageCount = 0;
            if (this.pagedownButton.getTag() != null) {
                this.pagedownButton.setTag(null);
                if (this.pagedownButtonAnimation != null) {
                    this.pagedownButtonAnimation.cancel();
                    this.pagedownButtonAnimation = null;
                }
                if (z2) {
                    this.pagedownButtonAnimation = new AnimatorSet();
                    if (this.mentiondownButton.getVisibility() == 0) {
                        animatorSet = this.pagedownButtonAnimation;
                        animatorArr = new Animator[2];
                        animatorArr[0] = ObjectAnimator.ofFloat(this.pagedownButton, "translationY", new float[]{(float) AndroidUtilities.dp(100.0f)});
                        animatorArr[1] = ObjectAnimator.ofFloat(this.mentiondownButton, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                        animatorSet.playTogether(animatorArr);
                    } else {
                        animatorSet = this.pagedownButtonAnimation;
                        animatorArr = new Animator[1];
                        animatorArr[0] = ObjectAnimator.ofFloat(this.pagedownButton, "translationY", new float[]{(float) AndroidUtilities.dp(100.0f)});
                        animatorSet.playTogether(animatorArr);
                    }
                    this.pagedownButtonAnimation.setDuration(200);
                    this.pagedownButtonAnimation.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            ChatActivity.this.pagedownButtonCounter.setVisibility(4);
                            ChatActivity.this.pagedownButton.setVisibility(4);
                        }
                    });
                    this.pagedownButtonAnimation.start();
                    return;
                }
                this.pagedownButton.setVisibility(4);
            }
        }
    }

    private void showVoiceHint(boolean z, boolean z2) {
        if (getParentActivity() != null && this.fragmentView != null) {
            if (!z || this.voiceHintTextView != null) {
                if (this.voiceHintTextView == null) {
                    SizeNotifierFrameLayout sizeNotifierFrameLayout = (SizeNotifierFrameLayout) this.fragmentView;
                    int indexOfChild = sizeNotifierFrameLayout.indexOfChild(this.chatActivityEnterView);
                    if (indexOfChild != -1) {
                        this.voiceHintTextView = new TextView(getParentActivity());
                        this.voiceHintTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
                        this.voiceHintTextView.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
                        this.voiceHintTextView.setTextSize(1, 14.0f);
                        this.voiceHintTextView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f));
                        this.voiceHintTextView.setGravity(16);
                        this.voiceHintTextView.setAlpha(BitmapDescriptorFactory.HUE_RED);
                        sizeNotifierFrameLayout.addView(this.voiceHintTextView, indexOfChild + 1, LayoutHelper.createFrame(-2, -2.0f, 85, 5.0f, BitmapDescriptorFactory.HUE_RED, 5.0f, 3.0f));
                    } else {
                        return;
                    }
                }
                if (z) {
                    if (this.voiceHintAnimation != null) {
                        this.voiceHintAnimation.cancel();
                        this.voiceHintAnimation = null;
                    }
                    AndroidUtilities.cancelRunOnUIThread(this.voiceHintHideRunnable);
                    this.voiceHintHideRunnable = null;
                    hideVoiceHint();
                    return;
                }
                this.voiceHintTextView.setText(z2 ? LocaleController.getString("HoldToVideo", R.string.HoldToVideo) : LocaleController.getString("HoldToAudio", R.string.HoldToAudio));
                if (this.voiceHintHideRunnable != null) {
                    if (this.voiceHintAnimation != null) {
                        this.voiceHintAnimation.cancel();
                        this.voiceHintAnimation = null;
                    } else {
                        AndroidUtilities.cancelRunOnUIThread(this.voiceHintHideRunnable);
                        Runnable anonymousClass72 = new Runnable() {
                            public void run() {
                                ChatActivity.this.hideVoiceHint();
                            }
                        };
                        this.voiceHintHideRunnable = anonymousClass72;
                        AndroidUtilities.runOnUIThread(anonymousClass72, 2000);
                        return;
                    }
                } else if (this.voiceHintAnimation != null) {
                    return;
                }
                this.voiceHintTextView.setVisibility(0);
                this.voiceHintAnimation = new AnimatorSet();
                AnimatorSet animatorSet = this.voiceHintAnimation;
                Animator[] animatorArr = new Animator[1];
                animatorArr[0] = ObjectAnimator.ofFloat(this.voiceHintTextView, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
                this.voiceHintAnimation.addListener(new AnimatorListenerAdapter() {

                    /* renamed from: org.telegram.ui.ChatActivity$73$1 */
                    class C42591 implements Runnable {
                        C42591() {
                        }

                        public void run() {
                            ChatActivity.this.hideVoiceHint();
                        }
                    }

                    public void onAnimationCancel(Animator animator) {
                        if (animator.equals(ChatActivity.this.voiceHintAnimation)) {
                            ChatActivity.this.voiceHintAnimation = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (animator.equals(ChatActivity.this.voiceHintAnimation)) {
                            ChatActivity.this.voiceHintAnimation = null;
                            AndroidUtilities.runOnUIThread(ChatActivity.this.voiceHintHideRunnable = new C42591(), 2000);
                        }
                    }
                });
                this.voiceHintAnimation.setDuration(300);
                this.voiceHintAnimation.start();
            }
        }
    }

    private void startEditingMessageObject(MessageObject messageObject) {
        if (messageObject != null && getParentActivity() != null) {
            if (this.searchItem != null && this.actionBar.isSearchFieldVisible()) {
                this.actionBar.closeSearchField();
                this.chatActivityEnterView.setFieldFocused();
            }
            this.mentionsAdapter.setNeedBotContext(false);
            this.chatListView.setOnItemLongClickListener(null);
            this.chatListView.setOnItemClickListener((OnItemClickListenerExtended) null);
            this.chatListView.setClickable(false);
            this.chatListView.setLongClickable(false);
            this.chatActivityEnterView.setEditingMessageObject(messageObject, !messageObject.isMediaEmpty());
            updateBottomOverlay();
            if (this.chatActivityEnterView.isEditingCaption()) {
                this.mentionsAdapter.setAllowNewMentions(false);
            }
            this.actionModeTitleContainer.setVisibility(0);
            this.selectedMessagesCountTextView.setVisibility(8);
            checkEditTimer();
            this.chatActivityEnterView.setAllowStickersAndGifs(false, false);
            ActionBarMenu createActionMode = this.actionBar.createActionMode();
            createActionMode.getItem(19).setVisibility(8);
            createActionMode.getItem(10).setVisibility(8);
            createActionMode.getItem(11).setVisibility(8);
            createActionMode.getItem(12).setVisibility(8);
            createActionMode.getItem(23).setVisibility(8);
            this.actionBar.showActionMode();
            updatePinnedMessageView(true);
            updateVisibleRows();
            TLObject tLRPC$TL_messages_getMessageEditData = new TLRPC$TL_messages_getMessageEditData();
            tLRPC$TL_messages_getMessageEditData.peer = MessagesController.getInputPeer((int) this.dialog_id);
            tLRPC$TL_messages_getMessageEditData.id = messageObject.getId();
            this.editingMessageObjectReqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getMessageEditData, new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            ChatActivity.this.editingMessageObjectReqId = 0;
                            if (tLObject == null) {
                                org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                builder.setMessage(LocaleController.getString("EditMessageError", R.string.EditMessageError));
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                ChatActivity.this.showDialog(builder.create());
                                if (ChatActivity.this.chatActivityEnterView != null) {
                                    ChatActivity.this.chatActivityEnterView.setEditingMessageObject(null, false);
                                }
                            } else if (ChatActivity.this.chatActivityEnterView != null) {
                                ChatActivity.this.chatActivityEnterView.showEditDoneProgress(false, true);
                            }
                        }
                    });
                }
            });
        }
    }

    private void toggleMute(boolean z) {
        Editor edit;
        TLRPC$TL_dialog tLRPC$TL_dialog;
        if (MessagesController.getInstance().isDialogMuted(this.dialog_id)) {
            edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            edit.putInt("notify2_" + this.dialog_id, 0);
            MessagesStorage.getInstance().setDialogFlags(this.dialog_id, 0);
            edit.commit();
            tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.dialog_id));
            if (tLRPC$TL_dialog != null) {
                tLRPC$TL_dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
            }
            NotificationsController.updateServerNotificationsSettings(this.dialog_id);
        } else if (z) {
            edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            edit.putInt("notify2_" + this.dialog_id, 2);
            MessagesStorage.getInstance().setDialogFlags(this.dialog_id, 1);
            edit.commit();
            tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.dialog_id));
            if (tLRPC$TL_dialog != null) {
                tLRPC$TL_dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                tLRPC$TL_dialog.notify_settings.mute_until = Integer.MAX_VALUE;
            }
            NotificationsController.updateServerNotificationsSettings(this.dialog_id);
            NotificationsController.getInstance().removeNotificationsForDialog(this.dialog_id);
        } else {
            showDialog(AlertsCreator.createMuteAlert(getParentActivity(), this.dialog_id));
        }
    }

    private void translateQuickAccess(final View view, RecyclerView recyclerView, boolean z) {
        this.isShowingQuickAccess = !this.isShowingQuickAccess;
        view.animate().setDuration(z ? 1 : 500).setListener(new AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                try {
                    ((ImageView) view.findViewById(R.id.openDialogMark)).setImageResource(ChatActivity.this.isShowingQuickAccess ? R.drawable.opmu : R.drawable.opmd);
                } catch (Exception e) {
                }
                view.findViewById(R.id.openDialog).setClickable(true);
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                view.findViewById(R.id.openDialog).setClickable(false);
            }
        }).translationYBy(this.isShowingQuickAccess ? (float) recyclerView.getHeight() : (float) (-recyclerView.getHeight()));
    }

    private void updateActionModeTitle() {
        if (!this.actionBar.isActionModeShowed()) {
            return;
        }
        if (!this.selectedMessagesIds[0].isEmpty() || !this.selectedMessagesIds[1].isEmpty()) {
            this.selectedMessagesCountTextView.setNumber(this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size(), true);
        }
    }

    private void updateBotButtons() {
        if (this.headerItem != null && this.currentUser != null && this.currentEncryptedChat == null && this.currentUser.bot) {
            Object obj;
            Object obj2;
            if (this.botInfo.isEmpty()) {
                obj = null;
                obj2 = null;
            } else {
                obj = null;
                obj2 = null;
                for (Entry value : this.botInfo.entrySet()) {
                    Object obj3;
                    BotInfo botInfo = (BotInfo) value.getValue();
                    Object obj4 = obj;
                    Object obj5 = obj2;
                    for (int i = 0; i < botInfo.commands.size(); i++) {
                        TL_botCommand tL_botCommand = (TL_botCommand) botInfo.commands.get(i);
                        if (tL_botCommand.command.toLowerCase().equals("help")) {
                            obj5 = 1;
                        } else if (tL_botCommand.command.toLowerCase().equals("settings")) {
                            int i2 = 1;
                        }
                        if (obj4 != null && obj5 != null) {
                            obj3 = obj4;
                            obj = obj5;
                            break;
                        }
                    }
                    obj3 = obj4;
                    obj = obj5;
                    obj2 = obj;
                    obj = obj3;
                }
            }
            if (obj2 != null) {
                this.headerItem.showSubItem(30);
            } else {
                this.headerItem.hideSubItem(30);
            }
            if (obj != null) {
                this.headerItem.showSubItem(31);
            } else {
                this.headerItem.hideSubItem(31);
            }
        }
    }

    private void updateBottomOverlay() {
        if (this.bottomOverlayChatText != null) {
            if (this.currentChat != null) {
                if (!ChatObject.isChannel(this.currentChat) || (this.currentChat instanceof TL_channelForbidden)) {
                    if (ChatObject.isLeftFromChat(this.currentChat)) {
                        this.bottomOverlayChatText.setText(LocaleController.getString("ReturnToThisGroup", R.string.ReturnToThisGroup));
                    } else {
                        this.bottomOverlayChatText.setText(LocaleController.getString("DeleteThisGroup", R.string.DeleteThisGroup));
                    }
                } else if (ChatObject.isNotInChat(this.currentChat)) {
                    this.bottomOverlayChatText.setText(LocaleController.getString("ChannelJoin", R.string.ChannelJoin));
                } else if (MessagesController.getInstance().isDialogMuted(this.dialog_id)) {
                    this.bottomOverlayChatText.setText(LocaleController.getString("ChannelUnmute", R.string.ChannelUnmute));
                } else {
                    this.bottomOverlayChatText.setText(LocaleController.getString("ChannelMute", R.string.ChannelMute));
                }
            } else if (this.userBlocked) {
                if (this.currentUser.bot) {
                    this.bottomOverlayChatText.setText(LocaleController.getString("BotUnblock", R.string.BotUnblock));
                } else {
                    this.bottomOverlayChatText.setText(LocaleController.getString("Unblock", R.string.Unblock));
                }
                if (this.botButtons != null) {
                    this.botButtons = null;
                    if (this.chatActivityEnterView != null) {
                        if (this.replyingMessageObject != null && this.botReplyButtons == this.replyingMessageObject) {
                            this.botReplyButtons = null;
                            showReplyPanel(false, null, null, null, false);
                        }
                        this.chatActivityEnterView.setButtons(this.botButtons, false);
                    }
                }
            } else if (this.botUser == null || !this.currentUser.bot) {
                this.bottomOverlayChatText.setText(LocaleController.getString("DeleteThisChat", R.string.DeleteThisChat));
            } else {
                this.bottomOverlayChatText.setText(LocaleController.getString("BotStart", R.string.BotStart));
                this.chatActivityEnterView.hidePopup(false);
                if (getParentActivity() != null) {
                    AndroidUtilities.hideKeyboard(getParentActivity().getCurrentFocus());
                }
            }
            if (this.searchItem == null || this.searchItem.getVisibility() != 0) {
                this.searchContainer.setVisibility(4);
                if ((this.currentChat == null || (!ChatObject.isNotInChat(this.currentChat) && ChatObject.canWriteToChat(this.currentChat))) && (this.currentUser == null || !(UserObject.isDeleted(this.currentUser) || this.userBlocked))) {
                    if (this.botUser == null || !this.currentUser.bot) {
                        this.chatActivityEnterView.setVisibility(0);
                        this.bottomOverlayChat.setVisibility(4);
                    } else {
                        this.bottomOverlayChat.setVisibility(0);
                        this.chatActivityEnterView.setVisibility(4);
                    }
                    if (this.muteItem != null) {
                        this.muteItem.setVisibility(0);
                    }
                } else {
                    if (this.chatActivityEnterView.isEditingMessage()) {
                        this.chatActivityEnterView.setVisibility(0);
                        this.bottomOverlayChat.setVisibility(4);
                        this.chatActivityEnterView.setFieldFocused();
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                ChatActivity.this.chatActivityEnterView.openKeyboard();
                            }
                        }, 100);
                    } else {
                        this.bottomOverlayChat.setVisibility(0);
                        this.chatActivityEnterView.setFieldFocused(false);
                        this.chatActivityEnterView.setVisibility(4);
                        this.chatActivityEnterView.closeKeyboard();
                    }
                    if (this.muteItem != null) {
                        this.muteItem.setVisibility(8);
                    }
                    this.attachItem.setVisibility(8);
                    this.headerItem.setVisibility(0);
                }
                if (this.topViewWasVisible == 1) {
                    this.chatActivityEnterView.showTopView(false, false);
                    this.topViewWasVisible = 0;
                }
            } else {
                this.searchContainer.setVisibility(0);
                this.bottomOverlayChat.setVisibility(4);
                this.chatActivityEnterView.setFieldFocused(false);
                this.chatActivityEnterView.setVisibility(4);
                if (this.chatActivityEnterView.isTopViewVisible()) {
                    this.topViewWasVisible = 1;
                    this.chatActivityEnterView.hideTopView(false);
                } else {
                    this.topViewWasVisible = 2;
                }
            }
            checkRaiseSensors();
            if (this.isDownloadManager) {
                this.chatActivityEnterView.setVisibility(8);
                this.bottomOverlayChat.setVisibility(0);
                this.bottomOverlayChatText.setText(LocaleController.getString("StartDownloadService", R.string.StartDownloadService));
                this.downloadManagerLoading = new ProgressBar(ApplicationLoader.applicationContext, null, 16842872);
                this.downloadManagerLoading.setIndeterminate(true);
                this.bottomOverlayChat.addView(this.downloadManagerLoading, LayoutHelper.createFrame(-1, -2, 80));
                this.downloadManagerLoading.setVisibility(8);
            }
        }
    }

    private void updateContactStatus() {
        if (this.addContactItem != null) {
            if (this.currentUser == null) {
                this.addContactItem.setVisibility(8);
            } else {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(this.currentUser.id));
                if (user != null) {
                    this.currentUser = user;
                }
                if ((this.currentEncryptedChat != null && !(this.currentEncryptedChat instanceof TLRPC$TL_encryptedChat)) || MessagesController.isSupportId(this.currentUser.id) || UserObject.isDeleted(this.currentUser) || ContactsController.getInstance().isLoadingContacts() || (!TextUtils.isEmpty(this.currentUser.phone) && ContactsController.getInstance().contactsDict.get(Integer.valueOf(this.currentUser.id)) != null && (ContactsController.getInstance().contactsDict.size() != 0 || !ContactsController.getInstance().isLoadingContacts()))) {
                    this.addContactItem.setVisibility(8);
                } else {
                    this.addContactItem.setVisibility(0);
                    if (TextUtils.isEmpty(this.currentUser.phone)) {
                        this.addContactItem.setText(LocaleController.getString("ShareMyContactInfo", R.string.ShareMyContactInfo));
                        this.addToContactsButton.setVisibility(8);
                        this.reportSpamButton.setPadding(AndroidUtilities.dp(50.0f), 0, AndroidUtilities.dp(50.0f), 0);
                        this.reportSpamContainer.setLayoutParams(LayoutHelper.createLinear(-1, -1, 1.0f, 51, 0, 0, 0, AndroidUtilities.dp(1.0f)));
                    } else {
                        this.addContactItem.setText(LocaleController.getString("AddToContacts", R.string.AddToContacts));
                        this.reportSpamButton.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(50.0f), 0);
                        this.addToContactsButton.setVisibility(0);
                        this.reportSpamContainer.setLayoutParams(LayoutHelper.createLinear(-1, -1, 0.5f, 51, 0, 0, 0, AndroidUtilities.dp(1.0f)));
                    }
                }
            }
            checkListViewPaddings();
        }
    }

    private void updateInformationForScreenshotDetector() {
        if (this.currentUser != null) {
            if (this.currentEncryptedChat != null) {
                ArrayList arrayList = new ArrayList();
                if (this.chatListView != null) {
                    int childCount = this.chatListView.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View childAt = this.chatListView.getChildAt(i);
                        MessageObject messageObject = childAt instanceof ChatMessageCell ? ((ChatMessageCell) childAt).getMessageObject() : null;
                        if (!(messageObject == null || messageObject.getId() >= 0 || messageObject.messageOwner.random_id == 0)) {
                            arrayList.add(Long.valueOf(messageObject.messageOwner.random_id));
                        }
                    }
                }
                MediaController.getInstance().setLastVisibleMessageIds(this.chatEnterTime, this.chatLeaveTime, this.currentUser, this.currentEncryptedChat, arrayList, 0);
                return;
            }
            SecretMediaViewer instance = SecretMediaViewer.getInstance();
            MessageObject currentMessageObject = instance.getCurrentMessageObject();
            if (currentMessageObject != null && !currentMessageObject.isOut()) {
                MediaController.getInstance().setLastVisibleMessageIds(instance.getOpenTime(), instance.getCloseTime(), this.currentUser, null, null, currentMessageObject.getId());
            }
        }
    }

    private void updateMessagesVisisblePart() {
        if (this.chatListView != null) {
            int childCount = this.chatListView.getChildCount();
            int dp = this.chatActivityEnterView.isTopViewVisible() ? AndroidUtilities.dp(48.0f) : 0;
            int measuredHeight = this.chatListView.getMeasuredHeight();
            int i = Integer.MAX_VALUE;
            int i2 = Integer.MAX_VALUE;
            View view = null;
            View view2 = null;
            View view3 = null;
            Object obj = null;
            int i3 = 0;
            while (i3 < childCount) {
                int i4;
                Object obj2;
                View view4;
                int i5;
                int i6;
                View childAt = this.chatListView.getChildAt(i3);
                if (childAt instanceof ChatMessageCell) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                    int top = chatMessageCell.getTop();
                    chatMessageCell.getBottom();
                    i4 = top >= 0 ? 0 : -top;
                    int measuredHeight2 = chatMessageCell.getMeasuredHeight();
                    if (measuredHeight2 > measuredHeight) {
                        measuredHeight2 = i4 + measuredHeight;
                    }
                    chatMessageCell.setVisiblePart(i4, measuredHeight2 - i4);
                    MessageObject messageObject = chatMessageCell.getMessageObject();
                    if (this.roundVideoContainer != null && messageObject.isRoundVideo() && MediaController.getInstance().isPlayingMessage(messageObject)) {
                        ImageReceiver photoImage = chatMessageCell.getPhotoImage();
                        this.roundVideoContainer.setTranslationX((float) photoImage.getImageX());
                        this.roundVideoContainer.setTranslationY((float) ((photoImage.getImageY() + (this.fragmentView.getPaddingTop() + top)) - dp));
                        this.fragmentView.invalidate();
                        this.roundVideoContainer.invalidate();
                        obj2 = 1;
                        if (childAt.getBottom() > this.chatListView.getPaddingTop()) {
                            view4 = view2;
                            childAt = view;
                            i5 = i2;
                            i6 = i;
                        } else {
                            i4 = childAt.getBottom();
                            if (i4 < i) {
                                if ((childAt instanceof ChatMessageCell) || (childAt instanceof ChatActionCell)) {
                                    view3 = childAt;
                                }
                                view2 = childAt;
                                i = i4;
                            }
                            if ((childAt instanceof ChatActionCell) && ((ChatActionCell) childAt).getMessageObject().isDateObject) {
                                if (childAt.getAlpha() != 1.0f) {
                                    childAt.setAlpha(1.0f);
                                }
                                if (i4 < i2) {
                                    view4 = view2;
                                    i6 = i;
                                    i5 = i4;
                                }
                            }
                            view4 = view2;
                            childAt = view;
                            i5 = i2;
                            i6 = i;
                        }
                        i3++;
                        obj = obj2;
                        i = i6;
                        view = childAt;
                        i2 = i5;
                        view2 = view4;
                    }
                }
                obj2 = obj;
                if (childAt.getBottom() > this.chatListView.getPaddingTop()) {
                    i4 = childAt.getBottom();
                    if (i4 < i) {
                        view3 = childAt;
                        view2 = childAt;
                        i = i4;
                    }
                    if (childAt.getAlpha() != 1.0f) {
                        childAt.setAlpha(1.0f);
                    }
                    if (i4 < i2) {
                        view4 = view2;
                        i6 = i;
                        i5 = i4;
                    }
                    view4 = view2;
                    childAt = view;
                    i5 = i2;
                    i6 = i;
                } else {
                    view4 = view2;
                    childAt = view;
                    i5 = i2;
                    i6 = i;
                }
                i3++;
                obj = obj2;
                i = i6;
                view = childAt;
                i2 = i5;
                view2 = view4;
            }
            if (this.roundVideoContainer != null) {
                if (obj == null) {
                    this.roundVideoContainer.setTranslationY((float) ((-AndroidUtilities.roundMessageSize) - 100));
                    this.fragmentView.invalidate();
                    MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
                    if (playingMessageObject != null && playingMessageObject.isRoundVideo() && playingMessageObject.eventId == 0 && this.checkTextureViewPosition) {
                        MediaController.getInstance().setCurrentRoundVisible(false);
                    }
                } else {
                    MediaController.getInstance().setCurrentRoundVisible(true);
                }
            }
            if (view3 != null) {
                this.floatingDateView.setCustomDate((view3 instanceof ChatMessageCell ? ((ChatMessageCell) view3).getMessageObject() : ((ChatActionCell) view3).getMessageObject()).messageOwner.date);
            }
            this.currentFloatingDateOnScreen = false;
            boolean z = ((view2 instanceof ChatMessageCell) || (view2 instanceof ChatActionCell)) ? false : true;
            this.currentFloatingTopIsNotMessage = z;
            if (view != null) {
                if (view.getTop() > this.chatListView.getPaddingTop() || this.currentFloatingTopIsNotMessage) {
                    if (view.getAlpha() != 1.0f) {
                        view.setAlpha(1.0f);
                    }
                    hideFloatingDateView(!this.currentFloatingTopIsNotMessage);
                } else {
                    if (view.getAlpha() != BitmapDescriptorFactory.HUE_RED) {
                        view.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    }
                    if (this.floatingDateAnimation != null) {
                        this.floatingDateAnimation.cancel();
                        this.floatingDateAnimation = null;
                    }
                    if (this.floatingDateView.getTag() == null) {
                        this.floatingDateView.setTag(Integer.valueOf(1));
                    }
                    if (this.floatingDateView.getAlpha() != 1.0f) {
                        this.floatingDateView.setAlpha(1.0f);
                    }
                    this.currentFloatingDateOnScreen = true;
                }
                int bottom = view.getBottom() - this.chatListView.getPaddingTop();
                if (bottom <= this.floatingDateView.getMeasuredHeight() || bottom >= this.floatingDateView.getMeasuredHeight() * 2) {
                    this.floatingDateView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    return;
                } else {
                    this.floatingDateView.setTranslationY((float) (bottom + ((-this.floatingDateView.getMeasuredHeight()) * 2)));
                    return;
                }
            }
            hideFloatingDateView(true);
            this.floatingDateView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        }
    }

    private void updatePinnedMessageView(boolean z) {
        if (this.pinnedMessageView != null) {
            if (this.info != null) {
                if (!(this.pinnedMessageObject == null || this.info.pinned_msg_id == this.pinnedMessageObject.getId())) {
                    this.pinnedMessageObject = null;
                }
                if (this.info.pinned_msg_id != 0 && this.pinnedMessageObject == null) {
                    this.pinnedMessageObject = (MessageObject) this.messagesDict[0].get(Integer.valueOf(this.info.pinned_msg_id));
                }
            }
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            if (this.info == null || this.info.pinned_msg_id == 0 || this.info.pinned_msg_id == sharedPreferences.getInt("pin_" + this.dialog_id, 0) || (this.actionBar != null && (this.actionBar.isActionModeShowed() || this.actionBar.isSearchFieldVisible()))) {
                hidePinnedMessageView(z);
            } else if (this.pinnedMessageObject != null) {
                if (this.pinnedMessageView.getTag() != null) {
                    this.pinnedMessageView.setTag(null);
                    if (this.pinnedMessageViewAnimator != null) {
                        this.pinnedMessageViewAnimator.cancel();
                        this.pinnedMessageViewAnimator = null;
                    }
                    if (z) {
                        this.pinnedMessageView.setVisibility(0);
                        this.pinnedMessageViewAnimator = new AnimatorSet();
                        AnimatorSet animatorSet = this.pinnedMessageViewAnimator;
                        Animator[] animatorArr = new Animator[1];
                        animatorArr[0] = ObjectAnimator.ofFloat(this.pinnedMessageView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                        animatorSet.playTogether(animatorArr);
                        this.pinnedMessageViewAnimator.setDuration(200);
                        this.pinnedMessageViewAnimator.addListener(new AnimatorListenerAdapter() {
                            public void onAnimationCancel(Animator animator) {
                                if (ChatActivity.this.pinnedMessageViewAnimator != null && ChatActivity.this.pinnedMessageViewAnimator.equals(animator)) {
                                    ChatActivity.this.pinnedMessageViewAnimator = null;
                                }
                            }

                            public void onAnimationEnd(Animator animator) {
                                if (ChatActivity.this.pinnedMessageViewAnimator != null && ChatActivity.this.pinnedMessageViewAnimator.equals(animator)) {
                                    ChatActivity.this.pinnedMessageViewAnimator = null;
                                }
                            }
                        });
                        this.pinnedMessageViewAnimator.start();
                    } else {
                        this.pinnedMessageView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                        this.pinnedMessageView.setVisibility(0);
                    }
                }
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.pinnedMessageNameTextView.getLayoutParams();
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.pinnedMessageTextView.getLayoutParams();
                PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(this.pinnedMessageObject.photoThumbs2, AndroidUtilities.dp(50.0f));
                if (closestPhotoSizeWithSize == null) {
                    closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(this.pinnedMessageObject.photoThumbs, AndroidUtilities.dp(50.0f));
                }
                int dp;
                if (closestPhotoSizeWithSize == null || (closestPhotoSizeWithSize instanceof TLRPC$TL_photoSizeEmpty) || (closestPhotoSizeWithSize.location instanceof TLRPC$TL_fileLocationUnavailable) || this.pinnedMessageObject.type == 13) {
                    this.pinnedMessageImageView.setImageBitmap(null);
                    this.pinnedImageLocation = null;
                    this.pinnedMessageImageView.setVisibility(4);
                    dp = AndroidUtilities.dp(18.0f);
                    layoutParams2.leftMargin = dp;
                    layoutParams.leftMargin = dp;
                } else {
                    if (this.pinnedMessageObject.isRoundVideo()) {
                        this.pinnedMessageImageView.setRoundRadius(AndroidUtilities.dp(16.0f));
                    } else {
                        this.pinnedMessageImageView.setRoundRadius(0);
                    }
                    this.pinnedImageLocation = closestPhotoSizeWithSize.location;
                    this.pinnedMessageImageView.setImage(this.pinnedImageLocation, "50_50", (Drawable) null);
                    this.pinnedMessageImageView.setVisibility(0);
                    dp = AndroidUtilities.dp(55.0f);
                    layoutParams2.leftMargin = dp;
                    layoutParams.leftMargin = dp;
                }
                this.pinnedMessageNameTextView.setLayoutParams(layoutParams);
                this.pinnedMessageTextView.setLayoutParams(layoutParams2);
                this.pinnedMessageNameTextView.setText(LocaleController.getString("PinnedMessage", R.string.PinnedMessage));
                if (this.pinnedMessageObject.type == 14) {
                    this.pinnedMessageTextView.setText(String.format("%s - %s", new Object[]{this.pinnedMessageObject.getMusicAuthor(), this.pinnedMessageObject.getMusicTitle()}));
                } else if (this.pinnedMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                    this.pinnedMessageTextView.setText(Emoji.replaceEmoji(this.pinnedMessageObject.messageOwner.media.game.title, this.pinnedMessageTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                } else if (this.pinnedMessageObject.messageText != null) {
                    String charSequence = this.pinnedMessageObject.messageText.toString();
                    if (charSequence.length() > 150) {
                        charSequence = charSequence.substring(0, 150);
                    }
                    this.pinnedMessageTextView.setText(Emoji.replaceEmoji(charSequence.replace('\n', ' '), this.pinnedMessageTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                }
            } else {
                this.pinnedImageLocation = null;
                hidePinnedMessageView(z);
                if (this.loadingPinnedMessage != this.info.pinned_msg_id) {
                    this.loadingPinnedMessage = this.info.pinned_msg_id;
                    MessagesQuery.loadPinnedMessage(this.currentChat.id, this.info.pinned_msg_id, true);
                }
            }
            checkListViewPaddings();
        }
    }

    private void updateSearchButtons(int i, int i2, int i3) {
        float f = 1.0f;
        if (this.searchUpButton != null) {
            this.searchUpButton.setEnabled((i & 1) != 0);
            this.searchDownButton.setEnabled((i & 2) != 0);
            this.searchUpButton.setAlpha(this.searchUpButton.isEnabled() ? 1.0f : 0.5f);
            ImageView imageView = this.searchDownButton;
            if (!this.searchDownButton.isEnabled()) {
                f = 0.5f;
            }
            imageView.setAlpha(f);
            if (i3 < 0) {
                this.searchCountText.setText(TtmlNode.ANONYMOUS_REGION_ID);
            } else if (i3 == 0) {
                this.searchCountText.setText(LocaleController.getString("NoResult", R.string.NoResult));
            } else {
                this.searchCountText.setText(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(i2 + 1), Integer.valueOf(i3)}));
            }
        }
    }

    private void updateSecretStatus() {
        int i = 1;
        if (this.bottomOverlay != null) {
            if (ChatObject.isChannel(this.currentChat) && this.currentChat.banned_rights != null && this.currentChat.banned_rights.send_messages) {
                if (AndroidUtilities.isBannedForever(this.currentChat.banned_rights.until_date)) {
                    this.bottomOverlayText.setText(LocaleController.getString("SendMessageRestrictedForever", R.string.SendMessageRestrictedForever));
                } else {
                    this.bottomOverlayText.setText(LocaleController.formatString("SendMessageRestricted", R.string.SendMessageRestricted, new Object[]{LocaleController.formatDateForBan((long) this.currentChat.banned_rights.until_date)}));
                }
                this.bottomOverlay.setVisibility(0);
                if (this.mentionListAnimation != null) {
                    this.mentionListAnimation.cancel();
                    this.mentionListAnimation = null;
                }
                this.mentionContainer.setVisibility(8);
                this.mentionContainer.setTag(null);
            } else if (this.currentEncryptedChat == null || this.bigEmptyView == null) {
                this.bottomOverlay.setVisibility(4);
                return;
            } else {
                if (this.currentEncryptedChat instanceof TLRPC$TL_encryptedChatRequested) {
                    this.bottomOverlayText.setText(LocaleController.getString("EncryptionProcessing", R.string.EncryptionProcessing));
                    this.bottomOverlay.setVisibility(0);
                } else if (this.currentEncryptedChat instanceof TLRPC$TL_encryptedChatWaiting) {
                    this.bottomOverlayText.setText(AndroidUtilities.replaceTags(LocaleController.formatString("AwaitingEncryption", R.string.AwaitingEncryption, new Object[]{"<b>" + this.currentUser.first_name + "</b>"})));
                    this.bottomOverlay.setVisibility(0);
                } else if (this.currentEncryptedChat instanceof TLRPC$TL_encryptedChatDiscarded) {
                    this.bottomOverlayText.setText(LocaleController.getString("EncryptionRejected", R.string.EncryptionRejected));
                    this.bottomOverlay.setVisibility(0);
                    this.chatActivityEnterView.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
                    DraftQuery.cleanDraft(this.dialog_id, false);
                } else {
                    if (this.currentEncryptedChat instanceof TLRPC$TL_encryptedChat) {
                        this.bottomOverlay.setVisibility(4);
                    }
                    i = 0;
                }
                checkRaiseSensors();
                checkActionBarMenu();
            }
            if (i != 0) {
                this.chatActivityEnterView.hidePopup(false);
                if (getParentActivity() != null) {
                    AndroidUtilities.hideKeyboard(getParentActivity().getCurrentFocus());
                }
            }
        }
    }

    private void updateSpamView() {
        if (this.reportSpamView == null) {
            FileLog.d("no spam view found");
            return;
        }
        int i;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        if (this.currentEncryptedChat != null) {
            i = (this.currentEncryptedChat.admin_id == UserConfig.getClientUserId() || ContactsController.getInstance().isLoadingContacts() || ContactsController.getInstance().contactsDict.get(Integer.valueOf(this.currentUser.id)) != null) ? 0 : 1;
            if (i != 0 && sharedPreferences.getInt("spam3_" + this.dialog_id, 0) == 1) {
                i = 0;
            }
        } else {
            i = sharedPreferences.getInt(new StringBuilder().append("spam3_").append(this.dialog_id).toString(), 0) == 2 ? 1 : 0;
        }
        AnimatorSet animatorSet;
        Animator[] animatorArr;
        if (i == 0) {
            if (this.reportSpamView.getTag() == null) {
                FileLog.d("hide spam button");
                this.reportSpamView.setTag(Integer.valueOf(1));
                if (this.reportSpamViewAnimator != null) {
                    this.reportSpamViewAnimator.cancel();
                }
                this.reportSpamViewAnimator = new AnimatorSet();
                animatorSet = this.reportSpamViewAnimator;
                animatorArr = new Animator[1];
                animatorArr[0] = ObjectAnimator.ofFloat(this.reportSpamView, "translationY", new float[]{(float) (-AndroidUtilities.dp(50.0f))});
                animatorSet.playTogether(animatorArr);
                this.reportSpamViewAnimator.setDuration(200);
                this.reportSpamViewAnimator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationCancel(Animator animator) {
                        if (ChatActivity.this.reportSpamViewAnimator != null && ChatActivity.this.reportSpamViewAnimator.equals(animator)) {
                            ChatActivity.this.reportSpamViewAnimator = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (ChatActivity.this.reportSpamViewAnimator != null && ChatActivity.this.reportSpamViewAnimator.equals(animator)) {
                            ChatActivity.this.reportSpamView.setVisibility(8);
                            ChatActivity.this.reportSpamViewAnimator = null;
                        }
                    }
                });
                this.reportSpamViewAnimator.start();
            }
        } else if (this.reportSpamView.getTag() != null) {
            FileLog.d("show spam button");
            this.reportSpamView.setTag(null);
            this.reportSpamView.setVisibility(0);
            if (this.reportSpamViewAnimator != null) {
                this.reportSpamViewAnimator.cancel();
            }
            this.reportSpamViewAnimator = new AnimatorSet();
            animatorSet = this.reportSpamViewAnimator;
            animatorArr = new Animator[1];
            animatorArr[0] = ObjectAnimator.ofFloat(this.reportSpamView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorSet.playTogether(animatorArr);
            this.reportSpamViewAnimator.setDuration(200);
            this.reportSpamViewAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    if (ChatActivity.this.reportSpamViewAnimator != null && ChatActivity.this.reportSpamViewAnimator.equals(animator)) {
                        ChatActivity.this.reportSpamViewAnimator = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (ChatActivity.this.reportSpamViewAnimator != null && ChatActivity.this.reportSpamViewAnimator.equals(animator)) {
                        ChatActivity.this.reportSpamViewAnimator = null;
                    }
                }
            });
            this.reportSpamViewAnimator.start();
        }
        checkListViewPaddings();
    }

    private void updateTextureViewPosition() {
        if (this.fragmentView != null) {
            boolean z;
            int childCount = this.chatListView.getChildCount();
            int dp = this.chatActivityEnterView.isTopViewVisible() ? AndroidUtilities.dp(48.0f) : 0;
            for (int i = 0; i < childCount; i++) {
                View childAt = this.chatListView.getChildAt(i);
                if (childAt instanceof ChatMessageCell) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                    MessageObject messageObject = chatMessageCell.getMessageObject();
                    if (this.roundVideoContainer != null && messageObject.isRoundVideo() && MediaController.getInstance().isPlayingMessage(messageObject)) {
                        ImageReceiver photoImage = chatMessageCell.getPhotoImage();
                        this.roundVideoContainer.setTranslationX((float) photoImage.getImageX());
                        this.roundVideoContainer.setTranslationY((float) (((chatMessageCell.getTop() + this.fragmentView.getPaddingTop()) + photoImage.getImageY()) - dp));
                        this.fragmentView.invalidate();
                        this.roundVideoContainer.invalidate();
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            if (this.roundVideoContainer != null) {
                MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
                if (playingMessageObject.eventId != 0) {
                    return;
                }
                if (z) {
                    MediaController.getInstance().setCurrentRoundVisible(true);
                    scrollToMessageId(playingMessageObject.getId(), 0, false, 0, true);
                    return;
                }
                this.roundVideoContainer.setTranslationY((float) ((-AndroidUtilities.roundMessageSize) - 100));
                this.fragmentView.invalidate();
                if (playingMessageObject != null && playingMessageObject.isRoundVideo()) {
                    if (this.checkTextureViewPosition || PipRoundVideoView.getInstance() != null) {
                        MediaController.getInstance().setCurrentRoundVisible(false);
                    } else {
                        scrollToMessageId(playingMessageObject.getId(), 0, false, 0, true);
                    }
                }
            }
        }
    }

    private void updateTitle() {
        if (this.avatarContainer != null) {
            if (this.isDownloadManager) {
                this.avatarContainer.setTitle(LocaleController.getString("downloadManager", R.string.downloadManager));
                try {
                    int size = this.messages.size();
                    if (size != 0) {
                        size--;
                    }
                    this.avatarContainer.setSubtitle(LocaleController.getString("downloadCount", R.string.downloadCount) + " : " + size);
                } catch (Exception e) {
                }
            } else if (this.currentChat != null) {
                this.avatarContainer.setTitle(this.currentChat.title);
            } else if (this.currentUser == null) {
            } else {
                if (this.currentUser.self) {
                    this.avatarContainer.setTitle(LocaleController.getString("SavedMessages", R.string.SavedMessages));
                } else if (MessagesController.isSupportId(this.currentUser.id) || ContactsController.getInstance().contactsDict.get(Integer.valueOf(this.currentUser.id)) != null || (ContactsController.getInstance().contactsDict.size() == 0 && ContactsController.getInstance().isLoadingContacts())) {
                    this.avatarContainer.setTitle(UserObject.getUserName(this.currentUser));
                } else if (TextUtils.isEmpty(this.currentUser.phone)) {
                    this.avatarContainer.setTitle(UserObject.getUserName(this.currentUser));
                } else {
                    this.avatarContainer.setTitle(C2488b.a().e("+" + this.currentUser.phone));
                }
            }
        }
    }

    private void updateTitleIcons() {
        Drawable drawable = null;
        if (this.avatarContainer != null) {
            if (this.isDownloadManager) {
                this.avatarContainer.avatarImageView.setImageDrawable(getParentActivity().getResources().getDrawable(R.drawable.download_avatar));
                return;
            }
            Drawable drawable2 = MessagesController.getInstance().isDialogMuted(this.dialog_id) ? Theme.chat_muteIconDrawable : null;
            ChatAvatarContainer chatAvatarContainer = this.avatarContainer;
            if (this.currentEncryptedChat != null) {
                drawable = Theme.chat_lockIconDrawable;
            }
            chatAvatarContainer.setTitleIcons(drawable, drawable2);
            if (this.muteItem == null) {
                return;
            }
            if (drawable2 != null) {
                this.muteItem.setText(LocaleController.getString("UnmuteNotifications", R.string.UnmuteNotifications));
            } else {
                this.muteItem.setText(LocaleController.getString("MuteNotifications", R.string.MuteNotifications));
            }
        }
    }

    private void updateVisibleRows() {
        if (this.chatListView != null) {
            MessageObject editingMessageObject;
            int childCount = this.chatListView.getChildCount();
            if (this.chatActivityEnterView != null) {
                editingMessageObject = this.chatActivityEnterView.getEditingMessageObject();
            } else {
                Object obj = null;
            }
            for (int i = 0; i < childCount; i++) {
                View childAt = this.chatListView.getChildAt(i);
                if (childAt instanceof ChatMessageCell) {
                    Object obj2;
                    Object obj3;
                    ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                    MessageObject messageObject = chatMessageCell.getMessageObject();
                    if (this.actionBar.isActionModeShowed()) {
                        int i2 = messageObject.getDialogId() == this.dialog_id ? 0 : 1;
                        if (messageObject == editingMessageObject || this.selectedMessagesIds[i2].containsKey(Integer.valueOf(messageObject.getId()))) {
                            setCellSelectionBackground(messageObject, chatMessageCell, i2);
                            obj2 = 1;
                        } else {
                            childAt.setBackgroundDrawable(null);
                            obj2 = null;
                        }
                        obj3 = 1;
                    } else {
                        childAt.setBackgroundDrawable(null);
                        obj2 = null;
                        obj3 = null;
                    }
                    chatMessageCell.setMessageObject(chatMessageCell.getMessageObject(), chatMessageCell.getCurrentMessagesGroup(), chatMessageCell.isPinnedBottom(), chatMessageCell.isPinnedTop());
                    boolean z = obj3 == null;
                    boolean z2 = (obj3 == null || obj2 == null) ? false : true;
                    chatMessageCell.setCheckPressed(z, z2);
                    z2 = (this.highlightMessageId == Integer.MAX_VALUE || messageObject == null || messageObject.getId() != this.highlightMessageId) ? false : true;
                    chatMessageCell.setHighlighted(z2);
                    if (this.searchContainer != null && this.searchContainer.getVisibility() == 0) {
                        if (MessagesSearchQuery.isMessageFound(messageObject.getId(), messageObject.getDialogId() == this.mergeDialogId) && MessagesSearchQuery.getLastSearchQuery() != null) {
                            chatMessageCell.setHighlightedText(MessagesSearchQuery.getLastSearchQuery());
                        }
                    }
                    chatMessageCell.setHighlightedText(null);
                } else if (childAt instanceof ChatActionCell) {
                    ChatActionCell chatActionCell = (ChatActionCell) childAt;
                    chatActionCell.setMessageObject(chatActionCell.getMessageObject());
                }
            }
            this.chatListView.invalidate();
        }
    }

    public boolean allowGroupPhotos() {
        return this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 73;
    }

    public boolean checkRecordLocked() {
        if (this.chatActivityEnterView == null || !this.chatActivityEnterView.isRecordLocked()) {
            return false;
        }
        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
        if (this.chatActivityEnterView.isInVideoMode()) {
            builder.setTitle(LocaleController.getString("DiscardVideoMessageTitle", R.string.DiscardVideoMessageTitle));
            builder.setMessage(LocaleController.getString("DiscardVideoMessageDescription", R.string.DiscardVideoMessageDescription));
        } else {
            builder.setTitle(LocaleController.getString("DiscardVoiceMessageTitle", R.string.DiscardVoiceMessageTitle));
            builder.setMessage(LocaleController.getString("DiscardVoiceMessageDescription", R.string.DiscardVoiceMessageDescription));
        }
        builder.setPositiveButton(LocaleController.getString("DiscardVoiceMessageAction", R.string.DiscardVoiceMessageAction), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (ChatActivity.this.chatActivityEnterView != null) {
                    ChatActivity.this.chatActivityEnterView.cancelRecordingAudioVideo();
                }
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
        return true;
    }

    public View createView(Context context) {
        this.channelIsFilter = false;
        this.showFilterDialog = false;
        if (!BuildConfig.FLAVOR.contentEquals("vip") && C3791b.ap(ApplicationLoader.applicationContext)) {
            if (C2491a.c(Math.abs(this.dialog_id))) {
                mute();
                this.channelIsFilter = true;
                return createView2(context);
            }
            new Handler().postDelayed(new Runnable() {

                /* renamed from: org.telegram.ui.ChatActivity$10$1 */
                class C42211 implements C2497d {
                    C42211() {
                    }

                    public void onResult(Object obj, int i) {
                        switch (i) {
                            case -19:
                                if (ChatActivity.this.chatListView != null) {
                                    ChatActivity.this.chatListView.setAlpha(1.0f);
                                }
                                if (ChatActivity.this.bottomOverlayChat != null) {
                                    ChatActivity.this.bottomOverlayChat.setVisibility(0);
                                    return;
                                }
                                return;
                            case 19:
                                Log.d("alireza", "alireza succes get filter channel1");
                                FilterResponse filterResponse = (FilterResponse) obj;
                                if (filterResponse != null) {
                                    Log.d("alireza", "alireza succes get filter channel2");
                                    DialogStatus dialogStatus = new DialogStatus();
                                    dialogStatus.setFilter(filterResponse.isFilter());
                                    dialogStatus.setDialogId((long) ChatActivity.this.currentChat.id);
                                    ApplicationLoader.databaseHandler.a(dialogStatus);
                                    if (filterResponse.isFilter()) {
                                        ChatActivity.this.channelIsFilter = filterResponse.isFilter();
                                        if (ChatActivity.this.showFilterDialog) {
                                            ChatActivity.this.showFilterDialog = true;
                                        } else {
                                            ChatActivity.this.showFilterDialog = filterResponse.isShowDialog();
                                        }
                                        ChatActivity.this.mute();
                                        if (ChatActivity.this.chatListView != null) {
                                            ChatActivity.this.chatListView.setAlpha(BitmapDescriptorFactory.HUE_RED);
                                        }
                                        if (ChatActivity.this.bottomOverlayChat != null) {
                                            ChatActivity.this.bottomOverlayChat.setVisibility(8);
                                        }
                                        if (ChatActivity.this.pinnedMessageView != null) {
                                            ChatActivity.this.pinnedMessageView.setVisibility(8);
                                            return;
                                        }
                                        return;
                                    }
                                    if (ChatActivity.this.chatListView != null) {
                                        ChatActivity.this.chatListView.setAlpha(1.0f);
                                    }
                                    if (ChatActivity.this.bottomOverlayChat != null) {
                                        ChatActivity.this.bottomOverlayChat.setVisibility(0);
                                        return;
                                    }
                                    return;
                                }
                                Log.d("alireza", "alireza succes get filter channel3");
                                if (ChatActivity.this.chatListView != null) {
                                    ChatActivity.this.chatListView.setAlpha(1.0f);
                                }
                                if (ChatActivity.this.bottomOverlayChat != null) {
                                    ChatActivity.this.bottomOverlayChat.setVisibility(0);
                                    return;
                                }
                                return;
                            default:
                                return;
                        }
                    }
                }

                public void run() {
                    if (ChatObject.isChannel(ChatActivity.this.currentChat) && ChatActivity.this.currentChat != null && !TextUtils.isEmpty(ChatActivity.this.currentChat.username) && ChatObject.isNotInChat(ChatActivity.this.currentChat)) {
                        C2818c.a(ApplicationLoader.applicationContext, new C42211()).a((long) UserConfig.getClientUserId(), ChatActivity.this.currentChat.id);
                    }
                }
            }, 1000);
        }
        if (!C2491a.c(Math.abs(this.dialog_id))) {
            return createView2(context);
        }
        mute();
        this.channelIsFilter = true;
        return createView2(context);
    }

    public View createView2(Context context) {
        int i;
        CharSequence fieldText;
        boolean z;
        if (this.chatMessageCellsCache.isEmpty()) {
            for (i = 0; i < 8; i++) {
                this.chatMessageCellsCache.add(new ChatMessageCell(context));
            }
        }
        for (i = 1; i >= 0; i--) {
            this.selectedMessagesIds[i].clear();
            this.selectedMessagesCanCopyIds[i].clear();
            this.selectedMessagesCanStarIds[i].clear();
        }
        this.cantDeleteMessagesCount = 0;
        this.canEditMessagesCount = 0;
        this.hasOwnBackground = true;
        if (this.chatAttachAlert != null) {
            try {
                if (this.chatAttachAlert.isShowing()) {
                    this.chatAttachAlert.dismiss();
                }
            } catch (Exception e) {
            }
            this.chatAttachAlert.onDestroy();
            this.chatAttachAlert = null;
        }
        Theme.createChatResources(context, false);
        this.actionBar.setAddToContainer(false);
        this.actionBar.setBackButtonDrawable(new BackDrawable(false));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBarMenuOnItemClick() {

            /* renamed from: org.telegram.ui.ChatActivity$13$2 */
            class C42302 implements OnClickListener {

                /* renamed from: org.telegram.ui.ChatActivity$13$2$1 */
                class C42291 implements RequestDelegate {

                    /* renamed from: org.telegram.ui.ChatActivity$13$2$1$1 */
                    class C42281 implements Runnable {
                        C42281() {
                        }

                        public void run() {
                            try {
                                ChatActivity.this.finishFragment();
                            } catch (Throwable e) {
                                FileLog.e("tmessages", e);
                            }
                        }
                    }

                    C42291() {
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLObject != null) {
                            if (tLRPC$TL_error != null) {
                                MessagesController.getInstance().processUpdates((TLRPC$Updates) tLObject, false);
                                AndroidUtilities.runOnUIThread(new C42281());
                            }
                        } else if (tLRPC$TL_error != null) {
                            MessagesController.getInstance().processUpdates((TLRPC$Updates) tLObject, false);
                            AndroidUtilities.runOnUIThread(new C42281());
                        }
                    }
                }

                C42302() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId()));
                    if (user != null && ChatActivity.this.chat_id > 0) {
                        TLObject tLRPC$TL_messages_deleteChatUser = new TLRPC$TL_messages_deleteChatUser();
                        tLRPC$TL_messages_deleteChatUser.chat_id = ChatActivity.this.chat_id;
                        tLRPC$TL_messages_deleteChatUser.user_id = MessagesController.getInputUser(user);
                        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_deleteChatUser, new C42291(), 64);
                    }
                }
            }

            public void onItemClick(final int i) {
                int i2;
                if (i == -1) {
                    if (ChatActivity.this.actionBar.isActionModeShowed()) {
                        for (i2 = 1; i2 >= 0; i2--) {
                            ChatActivity.this.selectedMessagesIds[i2].clear();
                            ChatActivity.this.selectedMessagesCanCopyIds[i2].clear();
                            ChatActivity.this.selectedMessagesCanStarIds[i2].clear();
                        }
                        ChatActivity.this.cantDeleteMessagesCount = 0;
                        ChatActivity.this.canEditMessagesCount = 0;
                        if (ChatActivity.this.chatActivityEnterView.isEditingMessage()) {
                            ChatActivity.this.chatActivityEnterView.setEditingMessageObject(null, false);
                        } else {
                            ChatActivity.this.actionBar.hideActionMode();
                            ChatActivity.this.updatePinnedMessageView(true);
                        }
                        ChatActivity.this.updateVisibleRows();
                        return;
                    }
                    ChatActivity.this.finishFragment();
                } else if (i == 10) {
                    CharSequence charSequence = TtmlNode.ANONYMOUS_REGION_ID;
                    r1 = 0;
                    int i3 = 1;
                    while (i3 >= 0) {
                        List arrayList = new ArrayList(ChatActivity.this.selectedMessagesCanCopyIds[i3].keySet());
                        if (ChatActivity.this.currentEncryptedChat == null) {
                            Collections.sort(arrayList);
                        } else {
                            Collections.sort(arrayList, Collections.reverseOrder());
                        }
                        int i4 = r1;
                        String str = charSequence;
                        int i5 = 0;
                        while (i5 < arrayList.size()) {
                            r0 = (MessageObject) ChatActivity.this.selectedMessagesCanCopyIds[i3].get((Integer) arrayList.get(i5));
                            if (str.length() != 0) {
                                str = str + "\n\n";
                            }
                            i5++;
                            str = str + ChatActivity.this.getMessageContent(r0, i4, true);
                            i4 = r0.messageOwner.from_id;
                        }
                        i3--;
                        Object obj = str;
                        r1 = i4;
                    }
                    if (charSequence.length() != 0) {
                        AndroidUtilities.addToClipboard(charSequence);
                    }
                    for (i2 = 1; i2 >= 0; i2--) {
                        ChatActivity.this.selectedMessagesIds[i2].clear();
                        ChatActivity.this.selectedMessagesCanCopyIds[i2].clear();
                        ChatActivity.this.selectedMessagesCanStarIds[i2].clear();
                    }
                    ChatActivity.this.cantDeleteMessagesCount = 0;
                    ChatActivity.this.canEditMessagesCount = 0;
                    ChatActivity.this.actionBar.hideActionMode();
                    ChatActivity.this.updatePinnedMessageView(true);
                    ChatActivity.this.updateVisibleRows();
                } else if (i == 12) {
                    if (ChatActivity.this.getParentActivity() != null) {
                        ChatActivity.this.createDeleteMessagesAlert(null, null);
                    }
                } else if (i == 11 || i == 111) {
                    if (i == 111) {
                        ChatActivity.QuoteForward = true;
                    } else {
                        ChatActivity.QuoteForward = false;
                    }
                    r0 = new Bundle();
                    r0.putBoolean("onlySelect", true);
                    r0.putInt("dialogsType", 3);
                    BaseFragment dialogsActivity = new DialogsActivity(r0);
                    dialogsActivity.setDelegate(ChatActivity.this);
                    ChatActivity.this.presentFragment(dialogsActivity);
                } else if (i == 13) {
                    if (ChatActivity.this.getParentActivity() != null) {
                        ChatActivity.this.showDialog(AlertsCreator.createTTLAlert(ChatActivity.this.getParentActivity(), ChatActivity.this.currentEncryptedChat).create());
                    }
                } else if (i == 15 || i == 16) {
                    if (ChatActivity.this.getParentActivity() != null) {
                        final boolean z = ((int) ChatActivity.this.dialog_id) < 0 && ((int) (ChatActivity.this.dialog_id >> 32)) != 1;
                        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        if (i == 15) {
                            builder.setMessage(LocaleController.getString("AreYouSureClearHistory", R.string.AreYouSureClearHistory));
                        } else if (z) {
                            builder.setMessage(LocaleController.getString("AreYouSureDeleteAndExit", R.string.AreYouSureDeleteAndExit));
                        } else {
                            builder.setMessage(LocaleController.getString("AreYouSureDeleteThisChat", R.string.AreYouSureDeleteThisChat));
                        }
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i != 15) {
                                    if (!z) {
                                        MessagesController.getInstance().deleteDialog(ChatActivity.this.dialog_id, 0);
                                    } else if (ChatObject.isNotInChat(ChatActivity.this.currentChat)) {
                                        MessagesController.getInstance().deleteDialog(ChatActivity.this.dialog_id, 0);
                                    } else {
                                        MessagesController.getInstance().deleteUserFromChat((int) (-ChatActivity.this.dialog_id), MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId())), null);
                                    }
                                    ChatActivity.this.finishFragment();
                                    return;
                                }
                                if (!(!ChatObject.isChannel(ChatActivity.this.currentChat) || ChatActivity.this.info == null || ChatActivity.this.info.pinned_msg_id == 0)) {
                                    ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putInt("pin_" + ChatActivity.this.dialog_id, ChatActivity.this.info.pinned_msg_id).commit();
                                    ChatActivity.this.updatePinnedMessageView(true);
                                }
                                MessagesController.getInstance().deleteDialog(ChatActivity.this.dialog_id, 1);
                            }
                        });
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ChatActivity.this.showDialog(builder.create());
                    }
                } else if (i == 28) {
                    if (ChatActivity.this.getParentActivity() != null) {
                        Object obj2 = (((int) ChatActivity.this.dialog_id) >= 0 || ((int) (ChatActivity.this.dialog_id >> 32)) == 1) ? null : 1;
                        if (obj2 != null) {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(ChatActivity.this.getParentActivity());
                            builder2.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                            builder2.setMessage(LocaleController.getString("LeaveWithoutDeleteMsg", R.string.LeaveWithoutDeleteMsg));
                            builder2.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C42302());
                            builder2.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                            ChatActivity.this.showDialog(builder2.create());
                        }
                    }
                } else if (i == 17) {
                    if (ChatActivity.this.currentUser != null && ChatActivity.this.getParentActivity() != null) {
                        if (ChatActivity.this.currentUser.phone == null || ChatActivity.this.currentUser.phone.length() == 0) {
                            ChatActivity.this.shareMyContact(ChatActivity.this.replyingMessageObject);
                            return;
                        }
                        r0 = new Bundle();
                        r0.putInt("user_id", ChatActivity.this.currentUser.id);
                        r0.putBoolean("addContact", true);
                        ChatActivity.this.presentFragment(new ContactAddActivity(r0));
                    }
                } else if (i == 18) {
                    ChatActivity.this.toggleMute(false);
                } else if (i == 24) {
                    try {
                        AndroidUtilities.installShortcut((long) ChatActivity.this.currentUser.id);
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                } else if (i == 21) {
                    ChatActivity.this.showDialog(AlertsCreator.createReportAlert(ChatActivity.this.getParentActivity(), ChatActivity.this.dialog_id, ChatActivity.this));
                } else if (i == 19) {
                    MessageObject messageObject = null;
                    r1 = 1;
                    while (r1 >= 0) {
                        if (messageObject == null && ChatActivity.this.selectedMessagesIds[r1].size() == 1) {
                            messageObject = (MessageObject) ChatActivity.this.messagesDict[r1].get(new ArrayList(ChatActivity.this.selectedMessagesIds[r1].keySet()).get(0));
                        }
                        ChatActivity.this.selectedMessagesIds[r1].clear();
                        ChatActivity.this.selectedMessagesCanCopyIds[r1].clear();
                        ChatActivity.this.selectedMessagesCanStarIds[r1].clear();
                        r1--;
                    }
                    if (messageObject != null && (messageObject.messageOwner.id > 0 || (messageObject.messageOwner.id < 0 && ChatActivity.this.currentEncryptedChat != null))) {
                        ChatActivity.this.showReplyPanel(true, messageObject, null, null, false);
                    }
                    ChatActivity.this.cantDeleteMessagesCount = 0;
                    ChatActivity.this.canEditMessagesCount = 0;
                    ChatActivity.this.actionBar.hideActionMode();
                    ChatActivity.this.updatePinnedMessageView(true);
                    ChatActivity.this.updateVisibleRows();
                } else if (i == 22) {
                    for (r1 = 0; r1 < 2; r1++) {
                        for (Entry value : ChatActivity.this.selectedMessagesCanStarIds[r1].entrySet()) {
                            StickersQuery.addRecentSticker(2, ((MessageObject) value.getValue()).getDocument(), (int) (System.currentTimeMillis() / 1000), !ChatActivity.this.hasUnfavedSelected);
                        }
                    }
                    for (i2 = 1; i2 >= 0; i2--) {
                        ChatActivity.this.selectedMessagesIds[i2].clear();
                        ChatActivity.this.selectedMessagesCanCopyIds[i2].clear();
                        ChatActivity.this.selectedMessagesCanStarIds[i2].clear();
                    }
                    ChatActivity.this.cantDeleteMessagesCount = 0;
                    ChatActivity.this.canEditMessagesCount = 0;
                    ChatActivity.this.actionBar.hideActionMode();
                    ChatActivity.this.updatePinnedMessageView(true);
                    ChatActivity.this.updateVisibleRows();
                } else if (i == 23) {
                    r0 = null;
                    r1 = 1;
                    while (r1 >= 0) {
                        if (r0 == null && ChatActivity.this.selectedMessagesIds[r1].size() == 1) {
                            r0 = (MessageObject) ChatActivity.this.messagesDict[r1].get(new ArrayList(ChatActivity.this.selectedMessagesIds[r1].keySet()).get(0));
                        }
                        ChatActivity.this.selectedMessagesIds[r1].clear();
                        ChatActivity.this.selectedMessagesCanCopyIds[r1].clear();
                        ChatActivity.this.selectedMessagesCanStarIds[r1].clear();
                        r1--;
                    }
                    ChatActivity.this.startReplyOnTextChange = false;
                    ChatActivity.this.startEditingMessageObject(r0);
                    ChatActivity.this.cantDeleteMessagesCount = 0;
                    ChatActivity.this.canEditMessagesCount = 0;
                    ChatActivity.this.updatePinnedMessageView(true);
                    ChatActivity.this.updateVisibleRows();
                } else if (i == 14) {
                    ChatActivity.this.openAttachMenu();
                } else if (i == 30) {
                    SendMessagesHelper.getInstance().sendMessage("/help", ChatActivity.this.dialog_id, null, null, false, null, null, null);
                } else if (i == 31) {
                    SendMessagesHelper.getInstance().sendMessage("/settings", ChatActivity.this.dialog_id, null, null, false, null, null, null);
                } else if (i == 40) {
                    ChatActivity.this.openSearchWithText(null);
                } else if (i == 32) {
                    if (ChatActivity.this.currentUser != null && ChatActivity.this.getParentActivity() != null) {
                        VoIPHelper.startCall(ChatActivity.this.currentUser, ChatActivity.this.getParentActivity(), MessagesController.getInstance().getUserFull(ChatActivity.this.currentUser.id));
                    }
                } else if (i == 29) {
                    ChatActivity.this.openAddMember();
                } else if (i == 99) {
                    ChatActivity.this.presentFragment(new WallpapersActivity());
                } else if (i == 200) {
                    ChatActivity.this.goToFirstMsg = true;
                    ChatActivity.this.scrollToMessageId(1, 0, true, 0, false);
                } else if (i == ChatActivity.add_fave_dialog) {
                    TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(ChatActivity.this.dialog_id));
                    if (tLRPC$TL_dialog == null) {
                        return;
                    }
                    if (Favourite.isFavourite(Long.valueOf(tLRPC$TL_dialog.id))) {
                        Favourite.deleteFavourite(Long.valueOf(ChatActivity.this.dialog_id));
                        MessagesController.getInstance().dialogsFavs.remove(tLRPC$TL_dialog);
                        ChatActivity.this.headerItem.getItemById(ChatActivity.add_fave_dialog).setText(LocaleController.getString("addDialogsToFave", R.string.addDialogsToFave));
                        return;
                    }
                    Favourite.addFavourite(Long.valueOf(ChatActivity.this.dialog_id));
                    MessagesController.getInstance().dialogsFavs.add(tLRPC$TL_dialog);
                    ChatActivity.this.headerItem.getItemById(ChatActivity.add_fave_dialog).setText(LocaleController.getString("deleteDialogsFromFave", R.string.deleteDialogsFromFave));
                } else if (i == ChatActivity.scheduleDownloads) {
                    ChatActivity.this.presentFragment(new C2601j());
                }
            }
        });
        this.avatarContainer = new ChatAvatarContainer(context, this, this.currentEncryptedChat != null);
        this.actionBar.addView(this.avatarContainer, 0, LayoutHelper.createFrame(-2, -1.0f, 51, 56.0f, BitmapDescriptorFactory.HUE_RED, 40.0f, BitmapDescriptorFactory.HUE_RED));
        if (!(this.currentChat == null || ChatObject.isChannel(this.currentChat))) {
            i = this.currentChat.participants_count;
            if (this.info != null) {
                i = this.info.participants.participants.size();
            }
            if (i == 0 || this.currentChat.deactivated || this.currentChat.left || (this.currentChat instanceof TL_chatForbidden) || (this.info != null && (this.info.participants instanceof TL_chatParticipantsForbidden))) {
                this.avatarContainer.setEnabled(false);
            }
        }
        ActionBarMenu createMenu = this.actionBar.createMenu();
        if (this.isDownloadManager) {
            this.headerItem = createMenu.addItem(0, (int) R.drawable.ic_ab_other);
            this.avatarContainer.setEnabled(false);
            this.headerItem.addSubItem(startAllServices, LocaleController.getString("startAllDownloads", R.string.startAllDownloads));
            this.headerItem.addSubItem(scheduleDownloads, LocaleController.getString("scheduleDownloads", R.string.scheduleDownloads));
        } else {
            if (this.currentEncryptedChat == null && !this.isBroadcast) {
                this.searchItem = createMenu.addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new ActionBarMenuItemSearchListener() {
                    boolean searchWas;

                    /* renamed from: org.telegram.ui.ChatActivity$14$1 */
                    class C42331 implements Runnable {
                        C42331() {
                        }

                        public void run() {
                            AnonymousClass14.this.searchWas = false;
                            ChatActivity.this.searchItem.getSearchField().requestFocus();
                            AndroidUtilities.showKeyboard(ChatActivity.this.searchItem.getSearchField());
                        }
                    }

                    public void onCaptionCleared() {
                        if (ChatActivity.this.searchingUserMessages != null) {
                            ChatActivity.this.searchUserButton.callOnClick();
                            return;
                        }
                        if (ChatActivity.this.searchingForUser) {
                            ChatActivity.this.mentionsAdapter.searchUsernameOrHashtag(null, 0, null, false);
                            ChatActivity.this.searchingForUser = false;
                        }
                        ChatActivity.this.searchItem.getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
                        ChatActivity.this.searchCalendarButton.setVisibility(0);
                        ChatActivity.this.searchUserButton.setVisibility(0);
                        ChatActivity.this.searchingUserMessages = null;
                    }

                    public void onSearchCollapse() {
                        ChatActivity.this.searchCalendarButton.setVisibility(0);
                        if (ChatActivity.this.searchUserButton != null) {
                            ChatActivity.this.searchUserButton.setVisibility(0);
                        }
                        if (ChatActivity.this.searchingForUser) {
                            ChatActivity.this.mentionsAdapter.searchUsernameOrHashtag(null, 0, null, false);
                            ChatActivity.this.searchingForUser = false;
                        }
                        ChatActivity.this.mentionLayoutManager.setReverseLayout(false);
                        ChatActivity.this.mentionsAdapter.setSearchingMentions(false);
                        ChatActivity.this.searchingUserMessages = null;
                        ChatActivity.this.searchItem.getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
                        ChatActivity.this.searchItem.setSearchFieldCaption(null);
                        ChatActivity.this.avatarContainer.setVisibility(0);
                        if (ChatActivity.this.chatActivityEnterView.hasText()) {
                            if (ChatActivity.this.headerItem != null) {
                                ChatActivity.this.headerItem.setVisibility(8);
                            }
                            if (ChatActivity.this.attachItem != null) {
                                ChatActivity.this.attachItem.setVisibility(0);
                            }
                        } else {
                            if (ChatActivity.this.headerItem != null) {
                                ChatActivity.this.headerItem.setVisibility(0);
                            }
                            if (ChatActivity.this.attachItem != null) {
                                ChatActivity.this.attachItem.setVisibility(8);
                            }
                        }
                        ChatActivity.this.searchItem.setVisibility(8);
                        ChatActivity.this.highlightMessageId = Integer.MAX_VALUE;
                        ChatActivity.this.updateVisibleRows();
                        if (this.searchWas) {
                            ChatActivity.this.scrollToLastMessage(false);
                        }
                        ChatActivity.this.updateBottomOverlay();
                        ChatActivity.this.updatePinnedMessageView(true);
                    }

                    public void onSearchExpand() {
                        if (ChatActivity.this.openSearchKeyboard) {
                            AndroidUtilities.runOnUIThread(new C42331(), 300);
                        }
                    }

                    public void onSearchPressed(EditText editText) {
                        this.searchWas = true;
                        ChatActivity.this.updateSearchButtons(0, 0, -1);
                        MessagesSearchQuery.searchMessagesInChat(editText.getText().toString(), ChatActivity.this.dialog_id, ChatActivity.this.mergeDialogId, ChatActivity.this.classGuid, 0, ChatActivity.this.searchingUserMessages);
                    }

                    public void onTextChanged(EditText editText) {
                        if (ChatActivity.this.searchingForUser) {
                            ChatActivity.this.mentionsAdapter.searchUsernameOrHashtag("@" + editText.getText().toString(), 0, ChatActivity.this.messages, true);
                        } else if (!ChatActivity.this.searchingForUser && ChatActivity.this.searchingUserMessages == null && ChatActivity.this.searchUserButton != null && TextUtils.equals(editText.getText(), LocaleController.getString("SearchFrom", R.string.SearchFrom))) {
                            ChatActivity.this.searchUserButton.callOnClick();
                        }
                    }
                });
                this.searchItem.getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
                this.searchItem.setVisibility(8);
            }
            this.headerItem = createMenu.addItem(0, (int) R.drawable.ic_ab_other);
            this.headerItem.addSubItem(200, LocaleController.getString("goToFirst", R.string.goToFirst));
            this.headerItem.addSubItem(add_fave_dialog, Favourite.isFavourite(Long.valueOf(this.dialog_id)) ? LocaleController.getString("deleteDialogsFromFave", R.string.deleteDialogsFromFave) : LocaleController.getString("addDialogsToFave", R.string.addDialogsToFave));
            try {
                if (this.chat_id > 0) {
                    if (ChatObject.isChannel(this.currentChat)) {
                        if (this.currentChat.creator && (this.info == null || this.info.participants_count < MessagesController.getInstance().maxMegagroupCount)) {
                            this.headerItem.addSubItem(29, LocaleController.getString("AddMember", R.string.AddMember));
                        }
                    } else if (this.currentChat.admin || this.currentChat.creator || !this.currentChat.admins_enabled) {
                        this.headerItem.addSubItem(29, LocaleController.getString("AddMember", R.string.AddMember));
                    }
                }
                this.headerItem.addSubItem(99, LocaleController.getString("ChatBackground", R.string.ChatBackground));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (this.currentUser != null) {
                this.headerItem.addSubItem(32, LocaleController.getString("Call", R.string.Call));
                TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(this.currentUser.id);
                if (userFull == null || !userFull.phone_calls_available) {
                    this.headerItem.hideSubItem(32);
                } else {
                    this.headerItem.showSubItem(32);
                }
            }
            if (this.searchItem != null) {
                this.headerItem.addSubItem(40, LocaleController.getString("Search", R.string.Search));
            }
            if (ChatObject.isChannel(this.currentChat) && !this.currentChat.creator && (!this.currentChat.megagroup || (this.currentChat.username != null && this.currentChat.username.length() > 0))) {
                this.headerItem.addSubItem(21, LocaleController.getString("ReportChat", R.string.ReportChat));
            }
            if (this.currentUser != null) {
                this.addContactItem = this.headerItem.addSubItem(17, TtmlNode.ANONYMOUS_REGION_ID);
            }
            if (this.currentEncryptedChat != null) {
                this.timeItem2 = this.headerItem.addSubItem(13, LocaleController.getString("SetTimer", R.string.SetTimer));
            }
            if (!ChatObject.isChannel(this.currentChat) || (this.currentChat != null && this.currentChat.megagroup && TextUtils.isEmpty(this.currentChat.username))) {
                this.headerItem.addSubItem(15, LocaleController.getString("ClearHistory", R.string.ClearHistory));
            }
            if (!ChatObject.isChannel(this.currentChat)) {
                if (this.currentChat == null || this.isBroadcast) {
                    this.headerItem.addSubItem(16, LocaleController.getString("DeleteChatUser", R.string.DeleteChatUser));
                } else {
                    this.headerItem.addSubItem(16, LocaleController.getString("DeleteAndExit", R.string.DeleteAndExit));
                }
            }
            if (this.currentUser == null || !this.currentUser.self) {
                this.muteItem = this.headerItem.addSubItem(18, null);
            } else if (this.currentUser.self) {
                this.headerItem.addSubItem(24, LocaleController.getString("AddShortcut", R.string.AddShortcut));
            }
            if (this.currentUser != null && this.currentEncryptedChat == null && this.currentUser.bot) {
                this.headerItem.addSubItem(31, LocaleController.getString("BotSettings", R.string.BotSettings));
                this.headerItem.addSubItem(30, LocaleController.getString("BotHelp", R.string.BotHelp));
                updateBotButtons();
            }
        }
        updateTitle();
        if (!this.isDownloadManager) {
            this.avatarContainer.updateOnlineCount();
            this.avatarContainer.updateSubtitle();
        }
        updateTitleIcons();
        this.attachItem = createMenu.addItem(14, (int) R.drawable.ic_ab_other).setOverrideMenuClick(true).setAllowCloseAnimation(false);
        this.attachItem.setVisibility(8);
        this.actionModeViews.clear();
        ActionBarMenu createActionMode = this.actionBar.createActionMode();
        this.selectedMessagesCountTextView = new NumberTextView(createActionMode.getContext());
        this.selectedMessagesCountTextView.setTextSize(18);
        this.selectedMessagesCountTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.selectedMessagesCountTextView.setTextColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon));
        createActionMode.addView(this.selectedMessagesCountTextView, LayoutHelper.createLinear(0, -1, 1.0f, 65, 0, 0, 0));
        this.selectedMessagesCountTextView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.actionModeTitleContainer = new FrameLayout(context) {
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int dp;
                int i5 = i4 - i2;
                if (ChatActivity.this.actionModeSubTextView.getVisibility() != 8) {
                    int textHeight = ((i5 / 2) - ChatActivity.this.actionModeTextView.getTextHeight()) / 2;
                    float f = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 3.0f : 2.0f;
                    dp = AndroidUtilities.dp(f) + textHeight;
                } else {
                    dp = (i5 - ChatActivity.this.actionModeTextView.getTextHeight()) / 2;
                }
                ChatActivity.this.actionModeTextView.layout(0, dp, ChatActivity.this.actionModeTextView.getMeasuredWidth(), ChatActivity.this.actionModeTextView.getTextHeight() + dp);
                if (ChatActivity.this.actionModeSubTextView.getVisibility() != 8) {
                    dp = (i5 / 2) + (((i5 / 2) - ChatActivity.this.actionModeSubTextView.getTextHeight()) / 2);
                    if (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation == 2) {
                        dp -= AndroidUtilities.dp(1.0f);
                        ChatActivity.this.actionModeSubTextView.layout(0, dp, ChatActivity.this.actionModeSubTextView.getMeasuredWidth(), ChatActivity.this.actionModeSubTextView.getTextHeight() + dp);
                    } else {
                        dp -= AndroidUtilities.dp(1.0f);
                        ChatActivity.this.actionModeSubTextView.layout(0, dp, ChatActivity.this.actionModeSubTextView.getMeasuredWidth(), ChatActivity.this.actionModeSubTextView.getTextHeight() + dp);
                    }
                }
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i);
                setMeasuredDimension(size, MeasureSpec.getSize(i2));
                SimpleTextView access$5200 = ChatActivity.this.actionModeTextView;
                int i3 = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 20 : 18;
                access$5200.setTextSize(i3);
                ChatActivity.this.actionModeTextView.measure(MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), Integer.MIN_VALUE));
                if (ChatActivity.this.actionModeSubTextView.getVisibility() != 8) {
                    access$5200 = ChatActivity.this.actionModeSubTextView;
                    i3 = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 16 : 14;
                    access$5200.setTextSize(i3);
                    ChatActivity.this.actionModeSubTextView.measure(MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), Integer.MIN_VALUE));
                }
            }
        };
        createActionMode.addView(this.actionModeTitleContainer, LayoutHelper.createLinear(0, -1, 1.0f, 65, 0, 0, 0));
        this.actionModeTitleContainer.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.actionModeTitleContainer.setVisibility(8);
        this.actionModeTextView = new SimpleTextView(context);
        this.actionModeTextView.setTextSize(18);
        this.actionModeTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.actionModeTextView.setTextColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon));
        this.actionModeTextView.setText(LocaleController.getString("Edit", R.string.Edit));
        this.actionModeTitleContainer.addView(this.actionModeTextView, LayoutHelper.createFrame(-1, -1.0f));
        this.actionModeSubTextView = new SimpleTextView(context);
        this.actionModeSubTextView.setGravity(3);
        this.actionModeSubTextView.setTextColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon));
        this.actionModeTitleContainer.addView(this.actionModeSubTextView, LayoutHelper.createFrame(-1, -1.0f));
        if (this.currentEncryptedChat == null) {
            this.actionModeViews.add(createActionMode.addItemWithWidth(23, R.drawable.group_edit, AndroidUtilities.dp(54.0f)));
            if (!this.isBroadcast) {
                this.actionModeViews.add(createActionMode.addItemWithWidth(19, R.drawable.ic_ab_reply, AndroidUtilities.dp(54.0f)));
            }
            this.actionModeViews.add(createActionMode.addItemWithWidth(22, R.drawable.ic_ab_fave, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(createActionMode.addItemWithWidth(10, R.drawable.ic_ab_copy, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(createActionMode.addItem(111, R.drawable.ic_ab_fwd_quoteforward, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(createActionMode.addItemWithWidth(11, R.drawable.ic_ab_forward, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(createActionMode.addItemWithWidth(12, R.drawable.ic_ab_delete, AndroidUtilities.dp(54.0f)));
        } else {
            this.actionModeViews.add(createActionMode.addItemWithWidth(23, R.drawable.group_edit, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(createActionMode.addItemWithWidth(19, R.drawable.ic_ab_reply, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(createActionMode.addItemWithWidth(22, R.drawable.ic_ab_fave, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(createActionMode.addItemWithWidth(10, R.drawable.ic_ab_copy, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(createActionMode.addItemWithWidth(12, R.drawable.ic_ab_delete, AndroidUtilities.dp(54.0f)));
        }
        ActionBarMenuItem item = createActionMode.getItem(23);
        i = (this.canEditMessagesCount == 1 && this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size() == 1) ? 0 : 8;
        item.setVisibility(i);
        createActionMode.getItem(10).setVisibility(this.selectedMessagesCanCopyIds[0].size() + this.selectedMessagesCanCopyIds[1].size() != 0 ? 0 : 8);
        createActionMode.getItem(22).setVisibility(this.selectedMessagesCanStarIds[0].size() + this.selectedMessagesCanStarIds[1].size() != 0 ? 0 : 8);
        createActionMode.getItem(12).setVisibility(this.cantDeleteMessagesCount == 0 ? 0 : 8);
        checkActionBarMenu();
        this.fragmentView = new SizeNotifierFrameLayout(context) {
            int inputFieldHeight = 0;

            protected boolean drawChild(Canvas canvas, View view, long j) {
                boolean drawChild;
                int i = 0;
                MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
                int i2 = (playingMessageObject != null && playingMessageObject.eventId == 0 && playingMessageObject.isRoundVideo()) ? 1 : 0;
                if (i2 == 0 || view != ChatActivity.this.roundVideoContainer) {
                    drawChild = super.drawChild(canvas, view, j);
                    if (!(i2 == 0 || view != ChatActivity.this.chatListView || playingMessageObject.type == 5 || ChatActivity.this.roundVideoContainer == null)) {
                        super.drawChild(canvas, ChatActivity.this.roundVideoContainer, j);
                    }
                } else if (playingMessageObject.type == 5) {
                    if (Theme.chat_roundVideoShadow != null && ChatActivity.this.aspectRatioFrameLayout.isDrawingReady()) {
                        int x = ((int) view.getX()) - AndroidUtilities.dp(3.0f);
                        i2 = ((int) view.getY()) - AndroidUtilities.dp(2.0f);
                        Theme.chat_roundVideoShadow.setAlpha(255);
                        Theme.chat_roundVideoShadow.setBounds(x, i2, (AndroidUtilities.roundMessageSize + x) + AndroidUtilities.dp(6.0f), (AndroidUtilities.roundMessageSize + i2) + AndroidUtilities.dp(6.0f));
                        Theme.chat_roundVideoShadow.draw(canvas);
                    }
                    drawChild = super.drawChild(canvas, view, j);
                } else {
                    drawChild = false;
                }
                if (view == ChatActivity.this.actionBar && ChatActivity.this.parentLayout != null) {
                    ActionBarLayout actionBarLayout = ChatActivity.this.parentLayout;
                    if (ChatActivity.this.actionBar.getVisibility() == 0) {
                        i = ChatActivity.this.actionBar.getMeasuredHeight();
                    }
                    actionBarLayout.drawHeaderShadow(canvas, i);
                }
                return drawChild;
            }

            protected boolean isActionBarVisible() {
                return ChatActivity.this.actionBar.getVisibility() == 0;
            }

            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
                if (playingMessageObject != null && playingMessageObject.isRoundVideo() && playingMessageObject.eventId == 0 && playingMessageObject.getDialogId() == ChatActivity.this.dialog_id) {
                    MediaController.getInstance().setTextureView(ChatActivity.this.createTextureView(false), ChatActivity.this.aspectRatioFrameLayout, ChatActivity.this.roundVideoContainer, true);
                }
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int childCount = getChildCount();
                int emojiPadding = (getKeyboardHeight() > AndroidUtilities.dp(20.0f) || AndroidUtilities.isInMultiwindow) ? 0 : ChatActivity.this.chatActivityEnterView.getEmojiPadding();
                setBottomClip(emojiPadding);
                for (int i5 = 0; i5 < childCount; i5++) {
                    View childAt = getChildAt(i5);
                    if (childAt.getVisibility() != 8) {
                        int i6;
                        int i7;
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                        int measuredWidth = childAt.getMeasuredWidth();
                        int measuredHeight = childAt.getMeasuredHeight();
                        int i8 = layoutParams.gravity;
                        if (i8 == -1) {
                            i8 = 51;
                        }
                        int i9 = i8 & 112;
                        switch ((i8 & 7) & 7) {
                            case 1:
                                i6 = ((((i3 - i) - measuredWidth) / 2) + layoutParams.leftMargin) - layoutParams.rightMargin;
                                break;
                            case 5:
                                i6 = (i3 - measuredWidth) - layoutParams.rightMargin;
                                break;
                            default:
                                i6 = layoutParams.leftMargin;
                                break;
                        }
                        switch (i9) {
                            case 16:
                                i7 = (((((i4 - emojiPadding) - i2) - measuredHeight) / 2) + layoutParams.topMargin) - layoutParams.bottomMargin;
                                break;
                            case 48:
                                i7 = layoutParams.topMargin + getPaddingTop();
                                if (childAt != ChatActivity.this.actionBar && ChatActivity.this.actionBar.getVisibility() == 0) {
                                    i7 += ChatActivity.this.actionBar.getMeasuredHeight();
                                    break;
                                }
                            case 80:
                                i7 = (((i4 - emojiPadding) - i2) - measuredHeight) - layoutParams.bottomMargin;
                                break;
                            default:
                                i7 = layoutParams.topMargin;
                                break;
                        }
                        if (childAt == ChatActivity.this.mentionContainer) {
                            i7 -= ChatActivity.this.chatActivityEnterView.getMeasuredHeight() - AndroidUtilities.dp(2.0f);
                        } else if (childAt == ChatActivity.this.pagedownButton) {
                            i7 -= ChatActivity.this.chatActivityEnterView.getMeasuredHeight();
                        } else if (childAt == ChatActivity.this.mentiondownButton) {
                            i7 -= ChatActivity.this.chatActivityEnterView.getMeasuredHeight();
                        } else if (childAt == ChatActivity.this.emptyViewContainer) {
                            i7 -= (this.inputFieldHeight / 2) - (ChatActivity.this.actionBar.getVisibility() == 0 ? ChatActivity.this.actionBar.getMeasuredHeight() / 2 : 0);
                        } else if (ChatActivity.this.chatActivityEnterView.isPopupView(childAt)) {
                            i7 = AndroidUtilities.isInMultiwindow ? (ChatActivity.this.chatActivityEnterView.getTop() - childAt.getMeasuredHeight()) + AndroidUtilities.dp(1.0f) : ChatActivity.this.chatActivityEnterView.getBottom();
                        } else if (childAt == ChatActivity.this.gifHintTextView || childAt == ChatActivity.this.voiceHintTextView || childAt == ChatActivity.this.mediaBanTooltip) {
                            i7 -= this.inputFieldHeight;
                        } else if (childAt == ChatActivity.this.chatListView || childAt == ChatActivity.this.progressView) {
                            if (ChatActivity.this.chatActivityEnterView.isTopViewVisible()) {
                                i7 -= AndroidUtilities.dp(48.0f);
                            }
                        } else if (childAt == ChatActivity.this.actionBar) {
                            i7 -= getPaddingTop();
                        } else if (childAt == ChatActivity.this.roundVideoContainer) {
                            i7 = ChatActivity.this.actionBar.getMeasuredHeight();
                        } else if (childAt == ChatActivity.this.instantCameraView || childAt == ChatActivity.this.overlayView) {
                            i7 = 0;
                        }
                        childAt.layout(i6, i7, i6 + measuredWidth, i7 + measuredHeight);
                    }
                }
                ChatActivity.this.updateMessagesVisisblePart();
                notifyHeightChanged();
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i);
                int size2 = MeasureSpec.getSize(i2);
                setMeasuredDimension(size, size2);
                int paddingTop = size2 - getPaddingTop();
                measureChildWithMargins(ChatActivity.this.actionBar, i, 0, i2, 0);
                int measuredHeight = ChatActivity.this.actionBar.getMeasuredHeight();
                int i3 = ChatActivity.this.actionBar.getVisibility() == 0 ? paddingTop - measuredHeight : paddingTop;
                if (getKeyboardHeight() > AndroidUtilities.dp(20.0f) || AndroidUtilities.isInMultiwindow) {
                    paddingTop = i3;
                } else {
                    paddingTop = i3 - ChatActivity.this.chatActivityEnterView.getEmojiPadding();
                    size2 -= ChatActivity.this.chatActivityEnterView.getEmojiPadding();
                }
                int childCount = getChildCount();
                measureChildWithMargins(ChatActivity.this.chatActivityEnterView, i, 0, i2, 0);
                this.inputFieldHeight = ChatActivity.this.chatActivityEnterView.getMeasuredHeight();
                for (int i4 = 0; i4 < childCount; i4++) {
                    View childAt = getChildAt(i4);
                    if (!(childAt == null || childAt.getVisibility() == 8 || childAt == ChatActivity.this.chatActivityEnterView || childAt == ChatActivity.this.actionBar)) {
                        if (childAt == ChatActivity.this.chatListView || childAt == ChatActivity.this.progressView) {
                            childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(Math.max(AndroidUtilities.dp(10.0f), AndroidUtilities.dp((float) ((ChatActivity.this.chatActivityEnterView.isTopViewVisible() ? 48 : 0) + 2)) + (paddingTop - this.inputFieldHeight)), 1073741824));
                        } else if (childAt == ChatActivity.this.instantCameraView || childAt == ChatActivity.this.overlayView) {
                            childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec((size2 - this.inputFieldHeight) + AndroidUtilities.dp(3.0f), 1073741824));
                        } else if (childAt == ChatActivity.this.emptyViewContainer) {
                            childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(paddingTop, 1073741824));
                        } else if (ChatActivity.this.chatActivityEnterView.isPopupView(childAt)) {
                            if (!AndroidUtilities.isInMultiwindow) {
                                childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
                            } else if (AndroidUtilities.isTablet()) {
                                childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.dp(320.0f), (((paddingTop - this.inputFieldHeight) + measuredHeight) - AndroidUtilities.statusBarHeight) + getPaddingTop()), 1073741824));
                            } else {
                                childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec((((paddingTop - this.inputFieldHeight) + measuredHeight) - AndroidUtilities.statusBarHeight) + getPaddingTop(), 1073741824));
                            }
                        } else if (childAt == ChatActivity.this.mentionContainer) {
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ChatActivity.this.mentionContainer.getLayoutParams();
                            if (ChatActivity.this.mentionsAdapter.isBannedInline()) {
                                childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(paddingTop, Integer.MIN_VALUE));
                            } else {
                                int rowsCount;
                                ChatActivity.this.mentionListViewIgnoreLayout = true;
                                int dp;
                                if (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.isMediaLayout()) {
                                    rowsCount = ChatActivity.this.mentionGridLayoutManager.getRowsCount(size) * 102;
                                    if (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.getBotContextSwitch() != null) {
                                        rowsCount += 34;
                                    }
                                    dp = (rowsCount != 0 ? AndroidUtilities.dp(2.0f) : 0) + (paddingTop - ChatActivity.this.chatActivityEnterView.getMeasuredHeight());
                                    rowsCount = Math.max(0, dp - AndroidUtilities.dp(Math.min((float) rowsCount, 122.399994f)));
                                    if (ChatActivity.this.mentionLayoutManager.getReverseLayout()) {
                                        ChatActivity.this.mentionListView.setPadding(0, 0, 0, rowsCount);
                                    } else {
                                        ChatActivity.this.mentionListView.setPadding(0, rowsCount, 0, 0);
                                    }
                                    rowsCount = dp;
                                } else {
                                    dp = ChatActivity.this.mentionsAdapter.getItemCount();
                                    rowsCount = 0;
                                    if (ChatActivity.this.mentionsAdapter.isBotContext()) {
                                        if (ChatActivity.this.mentionsAdapter.getBotContextSwitch() != null) {
                                            rowsCount = 36;
                                            dp--;
                                        }
                                        rowsCount += dp * 68;
                                    } else {
                                        rowsCount = 0 + (dp * 36);
                                    }
                                    dp = (rowsCount != 0 ? AndroidUtilities.dp(2.0f) : 0) + (paddingTop - ChatActivity.this.chatActivityEnterView.getMeasuredHeight());
                                    rowsCount = Math.max(0, dp - AndroidUtilities.dp(Math.min((float) rowsCount, 122.399994f)));
                                    if (ChatActivity.this.mentionLayoutManager.getReverseLayout()) {
                                        ChatActivity.this.mentionListView.setPadding(0, 0, 0, rowsCount);
                                        rowsCount = dp;
                                    } else {
                                        ChatActivity.this.mentionListView.setPadding(0, rowsCount, 0, 0);
                                        rowsCount = dp;
                                    }
                                }
                                layoutParams.height = rowsCount;
                                layoutParams.topMargin = 0;
                                ChatActivity.this.mentionListViewIgnoreLayout = false;
                                childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824));
                            }
                        } else {
                            measureChildWithMargins(childAt, i, 0, i2, 0);
                        }
                    }
                }
            }
        };
        this.contentView = (SizeNotifierFrameLayout) this.fragmentView;
        this.contentView.setBackgroundImage(Theme.getCachedWallpaper());
        this.emptyViewContainer = new FrameLayout(context);
        this.emptyViewContainer.setVisibility(4);
        this.contentView.addView(this.emptyViewContainer, LayoutHelper.createFrame(-1, -2, 17));
        this.emptyViewContainer.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        if (this.currentEncryptedChat != null) {
            this.bigEmptyView = new ChatBigEmptyView(context, true);
            if (this.currentEncryptedChat.admin_id == UserConfig.getClientUserId()) {
                this.bigEmptyView.setSecretText(LocaleController.formatString("EncryptedPlaceholderTitleOutgoing", R.string.EncryptedPlaceholderTitleOutgoing, new Object[]{UserObject.getFirstName(this.currentUser)}));
            } else {
                this.bigEmptyView.setSecretText(LocaleController.formatString("EncryptedPlaceholderTitleIncoming", R.string.EncryptedPlaceholderTitleIncoming, new Object[]{UserObject.getFirstName(this.currentUser)}));
            }
            this.emptyViewContainer.addView(this.bigEmptyView, new FrameLayout.LayoutParams(-2, -2, 17));
        } else if (this.currentUser == null || !this.currentUser.self || this.isDownloadManager) {
            this.emptyView = new TextView(context);
            if (this.currentUser == null || this.currentUser.id == 777000 || this.currentUser.id == 429000 || this.currentUser.id == 4244000 || !MessagesController.isSupportId(this.currentUser.id)) {
                this.emptyView.setText(LocaleController.getString("NoMessages", R.string.NoMessages));
            } else {
                this.emptyView.setText(LocaleController.getString("GotAQuestion", R.string.GotAQuestion));
            }
            this.emptyView.setTextSize(1, 14.0f);
            this.emptyView.setGravity(17);
            this.emptyView.setTextColor(Theme.getColor(Theme.key_chat_serviceText));
            this.emptyView.setBackgroundResource(R.drawable.system);
            this.emptyView.getBackground().setColorFilter(Theme.colorFilter);
            this.emptyView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.emptyView.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(2.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(3.0f));
            this.emptyViewContainer.addView(this.emptyView, new FrameLayout.LayoutParams(-2, -2, 17));
        } else {
            this.bigEmptyView = new ChatBigEmptyView(context, false);
            this.emptyViewContainer.addView(this.bigEmptyView, new FrameLayout.LayoutParams(-2, -2, 17));
        }
        if (this.chatActivityEnterView != null) {
            this.chatActivityEnterView.onDestroy();
            fieldText = !this.chatActivityEnterView.isEditingMessage() ? this.chatActivityEnterView.getFieldText() : null;
        } else {
            fieldText = null;
        }
        if (this.mentionsAdapter != null) {
            this.mentionsAdapter.onDestroy();
        }
        this.chatListView = new RecyclerListView(context) {
            ArrayList<ChatMessageCell> drawCaptionAfter = new ArrayList();
            ArrayList<ChatMessageCell> drawNamesAfter = new ArrayList();
            ArrayList<ChatMessageCell> drawTimeAfter = new ArrayList();

            public boolean drawChild(Canvas canvas, View view, long j) {
                ChatMessageCell chatMessageCell;
                int backgroundDrawableLeft;
                int i;
                boolean drawChild;
                int childCount;
                int i2;
                int size;
                GroupedMessagePosition currentPosition;
                ImageReceiver avatarImage;
                GroupedMessages access$7400;
                ViewHolder childViewHolder;
                ViewHolder findViewHolderForAdapterPosition;
                if (view instanceof ChatMessageCell) {
                    chatMessageCell = (ChatMessageCell) view;
                    GroupedMessagePosition currentPosition2 = chatMessageCell.getCurrentPosition();
                    GroupedMessages currentMessagesGroup = chatMessageCell.getCurrentMessagesGroup();
                    if (currentPosition2 != null) {
                        if (currentPosition2.pw != currentPosition2.spanSize && currentPosition2.spanSize == 1000 && currentPosition2.siblingHeights == null && currentMessagesGroup.hasSibling) {
                            backgroundDrawableLeft = ((ChatMessageCell) view).getBackgroundDrawableLeft();
                            i = 0;
                            if (backgroundDrawableLeft == 0) {
                                canvas.save();
                                canvas.clipRect(backgroundDrawableLeft, view.getTop(), view.getRight(), view.getBottom());
                            } else if (i != 0) {
                                canvas.save();
                                canvas.clipRect(view.getLeft(), view.getTop(), view.getRight(), i);
                            }
                            drawChild = super.drawChild(canvas, view, j);
                            canvas.restore();
                            childCount = getChildCount();
                            for (i = 0; i < childCount; i++) {
                                if (getChildAt(i) != view) {
                                    i2 = i;
                                    break;
                                }
                            }
                            i2 = 0;
                            if (i2 == childCount - 1) {
                                size = this.drawTimeAfter.size();
                                if (size > 0) {
                                    for (backgroundDrawableLeft = 0; backgroundDrawableLeft < size; backgroundDrawableLeft++) {
                                        chatMessageCell = (ChatMessageCell) this.drawTimeAfter.get(backgroundDrawableLeft);
                                        canvas.save();
                                        canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                                        chatMessageCell.drawTimeLayout(canvas);
                                        canvas.restore();
                                    }
                                    this.drawTimeAfter.clear();
                                }
                                size = this.drawNamesAfter.size();
                                if (size > 0) {
                                    for (backgroundDrawableLeft = 0; backgroundDrawableLeft < size; backgroundDrawableLeft++) {
                                        chatMessageCell = (ChatMessageCell) this.drawNamesAfter.get(backgroundDrawableLeft);
                                        canvas.save();
                                        canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                                        chatMessageCell.drawNamesLayout(canvas);
                                        canvas.restore();
                                    }
                                    this.drawNamesAfter.clear();
                                }
                                size = this.drawCaptionAfter.size();
                                if (size > 0) {
                                    for (backgroundDrawableLeft = 0; backgroundDrawableLeft < size; backgroundDrawableLeft++) {
                                        chatMessageCell = (ChatMessageCell) this.drawCaptionAfter.get(backgroundDrawableLeft);
                                        canvas.save();
                                        canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                                        chatMessageCell.drawCaptionLayout(canvas);
                                        canvas.restore();
                                    }
                                    this.drawCaptionAfter.clear();
                                }
                            }
                            if (view instanceof ChatMessageCell) {
                                chatMessageCell = (ChatMessageCell) view;
                                currentPosition = chatMessageCell.getCurrentPosition();
                                if (currentPosition != null) {
                                    if (i2 == childCount - 1) {
                                        if (currentPosition.last) {
                                            this.drawTimeAfter.add(chatMessageCell);
                                        }
                                        this.drawNamesAfter.add(chatMessageCell);
                                    } else {
                                        canvas.save();
                                        canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                                        if (currentPosition.last) {
                                            chatMessageCell.drawTimeLayout(canvas);
                                        }
                                        chatMessageCell.drawNamesLayout(canvas);
                                        canvas.restore();
                                    }
                                    if (i2 == childCount - 1) {
                                        this.drawCaptionAfter.add(chatMessageCell);
                                    } else {
                                        canvas.save();
                                        canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                                        chatMessageCell.drawCaptionLayout(canvas);
                                        canvas.restore();
                                    }
                                }
                                avatarImage = chatMessageCell.getAvatarImage();
                                if (avatarImage != null) {
                                    access$7400 = ChatActivity.this.getValidGroupedMessage(chatMessageCell.getMessageObject());
                                    childCount = view.getTop();
                                    if (chatMessageCell.isPinnedBottom()) {
                                        childViewHolder = ChatActivity.this.chatListView.getChildViewHolder(view);
                                        if (childViewHolder != null) {
                                            backgroundDrawableLeft = childViewHolder.getAdapterPosition();
                                            if (access$7400 != null) {
                                            }
                                            backgroundDrawableLeft--;
                                            if (ChatActivity.this.chatListView.findViewHolderForAdapterPosition(backgroundDrawableLeft) != null) {
                                                avatarImage.setImageY(-AndroidUtilities.dp(1000.0f));
                                                avatarImage.draw(canvas);
                                                return drawChild;
                                            }
                                        }
                                    }
                                    if (chatMessageCell.isPinnedTop()) {
                                        childViewHolder = ChatActivity.this.chatListView.getChildViewHolder(view);
                                        if (childViewHolder != null) {
                                            while (true) {
                                                backgroundDrawableLeft = childViewHolder.getAdapterPosition();
                                                if (access$7400 != null) {
                                                }
                                                backgroundDrawableLeft++;
                                                findViewHolderForAdapterPosition = ChatActivity.this.chatListView.findViewHolderForAdapterPosition(backgroundDrawableLeft);
                                                if (findViewHolderForAdapterPosition != null) {
                                                    break;
                                                }
                                                childCount = findViewHolderForAdapterPosition.itemView.getTop();
                                                childViewHolder = findViewHolderForAdapterPosition;
                                            }
                                        }
                                    }
                                    backgroundDrawableLeft = view.getTop() + chatMessageCell.getLayoutHeight();
                                    i = ChatActivity.this.chatListView.getMeasuredHeight() - ChatActivity.this.chatListView.getPaddingBottom();
                                    if (backgroundDrawableLeft <= i) {
                                        i = backgroundDrawableLeft;
                                    }
                                    if (i - AndroidUtilities.dp(48.0f) < childCount) {
                                        i = AndroidUtilities.dp(48.0f) + childCount;
                                    }
                                    avatarImage.setImageY(i - AndroidUtilities.dp(44.0f));
                                    avatarImage.draw(canvas);
                                }
                            }
                            return drawChild;
                        } else if (currentPosition2.siblingHeights != null) {
                            i = view.getBottom() - AndroidUtilities.dp((float) ((chatMessageCell.isPinnedBottom() ? 1 : 0) + 1));
                            backgroundDrawableLeft = 0;
                            if (backgroundDrawableLeft == 0) {
                                canvas.save();
                                canvas.clipRect(backgroundDrawableLeft, view.getTop(), view.getRight(), view.getBottom());
                            } else if (i != 0) {
                                canvas.save();
                                canvas.clipRect(view.getLeft(), view.getTop(), view.getRight(), i);
                            }
                            drawChild = super.drawChild(canvas, view, j);
                            if (!(backgroundDrawableLeft == 0 && i == 0)) {
                                canvas.restore();
                            }
                            childCount = getChildCount();
                            for (i = 0; i < childCount; i++) {
                                if (getChildAt(i) != view) {
                                    i2 = i;
                                    break;
                                }
                            }
                            i2 = 0;
                            if (i2 == childCount - 1) {
                                size = this.drawTimeAfter.size();
                                if (size > 0) {
                                    for (backgroundDrawableLeft = 0; backgroundDrawableLeft < size; backgroundDrawableLeft++) {
                                        chatMessageCell = (ChatMessageCell) this.drawTimeAfter.get(backgroundDrawableLeft);
                                        canvas.save();
                                        canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                                        chatMessageCell.drawTimeLayout(canvas);
                                        canvas.restore();
                                    }
                                    this.drawTimeAfter.clear();
                                }
                                size = this.drawNamesAfter.size();
                                if (size > 0) {
                                    for (backgroundDrawableLeft = 0; backgroundDrawableLeft < size; backgroundDrawableLeft++) {
                                        chatMessageCell = (ChatMessageCell) this.drawNamesAfter.get(backgroundDrawableLeft);
                                        canvas.save();
                                        canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                                        chatMessageCell.drawNamesLayout(canvas);
                                        canvas.restore();
                                    }
                                    this.drawNamesAfter.clear();
                                }
                                size = this.drawCaptionAfter.size();
                                if (size > 0) {
                                    for (backgroundDrawableLeft = 0; backgroundDrawableLeft < size; backgroundDrawableLeft++) {
                                        chatMessageCell = (ChatMessageCell) this.drawCaptionAfter.get(backgroundDrawableLeft);
                                        canvas.save();
                                        canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                                        chatMessageCell.drawCaptionLayout(canvas);
                                        canvas.restore();
                                    }
                                    this.drawCaptionAfter.clear();
                                }
                            }
                            if (view instanceof ChatMessageCell) {
                                chatMessageCell = (ChatMessageCell) view;
                                currentPosition = chatMessageCell.getCurrentPosition();
                                if (currentPosition != null) {
                                    if (currentPosition.last || (currentPosition.minX == (byte) 0 && currentPosition.minY == (byte) 0)) {
                                        if (i2 == childCount - 1) {
                                            canvas.save();
                                            canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                                            if (currentPosition.last) {
                                                chatMessageCell.drawTimeLayout(canvas);
                                            }
                                            if (currentPosition.minX == (byte) 0 && currentPosition.minY == (byte) 0) {
                                                chatMessageCell.drawNamesLayout(canvas);
                                            }
                                            canvas.restore();
                                        } else {
                                            if (currentPosition.last) {
                                                this.drawTimeAfter.add(chatMessageCell);
                                            }
                                            if (currentPosition.minX == (byte) 0 && currentPosition.minY == (byte) 0 && chatMessageCell.hasNameLayout()) {
                                                this.drawNamesAfter.add(chatMessageCell);
                                            }
                                        }
                                    }
                                    if (i2 == childCount - 1) {
                                        canvas.save();
                                        canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                                        if (!(!chatMessageCell.hasCaptionLayout() || (currentPosition.flags & 8) == 0 || (currentPosition.flags & 1) == 0)) {
                                            chatMessageCell.drawCaptionLayout(canvas);
                                        }
                                        canvas.restore();
                                    } else if (!(!chatMessageCell.hasCaptionLayout() || (currentPosition.flags & 8) == 0 || (currentPosition.flags & 1) == 0)) {
                                        this.drawCaptionAfter.add(chatMessageCell);
                                    }
                                }
                                avatarImage = chatMessageCell.getAvatarImage();
                                if (avatarImage != null) {
                                    int indexOf;
                                    access$7400 = ChatActivity.this.getValidGroupedMessage(chatMessageCell.getMessageObject());
                                    childCount = view.getTop();
                                    if (chatMessageCell.isPinnedBottom()) {
                                        childViewHolder = ChatActivity.this.chatListView.getChildViewHolder(view);
                                        if (childViewHolder != null) {
                                            backgroundDrawableLeft = childViewHolder.getAdapterPosition();
                                            if (access$7400 != null || currentPosition == null) {
                                                backgroundDrawableLeft--;
                                            } else {
                                                indexOf = access$7400.posArray.indexOf(currentPosition);
                                                int size2 = access$7400.posArray.size();
                                                if ((currentPosition.flags & 8) != 0) {
                                                    backgroundDrawableLeft = (backgroundDrawableLeft - size2) + indexOf;
                                                } else {
                                                    i2 = backgroundDrawableLeft - 1;
                                                    size = indexOf + 1;
                                                    while (indexOf < size2) {
                                                        if (((GroupedMessagePosition) access$7400.posArray.get(size)).minY > currentPosition.maxY) {
                                                            backgroundDrawableLeft = i2;
                                                            break;
                                                        }
                                                        i2--;
                                                        size++;
                                                    }
                                                    backgroundDrawableLeft = i2;
                                                }
                                            }
                                            if (ChatActivity.this.chatListView.findViewHolderForAdapterPosition(backgroundDrawableLeft) != null) {
                                                avatarImage.setImageY(-AndroidUtilities.dp(1000.0f));
                                                avatarImage.draw(canvas);
                                                return drawChild;
                                            }
                                        }
                                    }
                                    if (chatMessageCell.isPinnedTop()) {
                                        childViewHolder = ChatActivity.this.chatListView.getChildViewHolder(view);
                                        if (childViewHolder != null) {
                                            while (true) {
                                                backgroundDrawableLeft = childViewHolder.getAdapterPosition();
                                                if (access$7400 != null || currentPosition == null) {
                                                    backgroundDrawableLeft++;
                                                } else {
                                                    indexOf = access$7400.posArray.indexOf(currentPosition);
                                                    access$7400.posArray.size();
                                                    if ((currentPosition.flags & 4) != 0) {
                                                        backgroundDrawableLeft = (backgroundDrawableLeft + indexOf) + 1;
                                                    } else {
                                                        i2 = backgroundDrawableLeft + 1;
                                                        size = indexOf - 1;
                                                        while (indexOf >= 0) {
                                                            if (((GroupedMessagePosition) access$7400.posArray.get(size)).maxY < currentPosition.minY) {
                                                                backgroundDrawableLeft = i2;
                                                                break;
                                                            }
                                                            i2++;
                                                            size--;
                                                        }
                                                        backgroundDrawableLeft = i2;
                                                    }
                                                }
                                                findViewHolderForAdapterPosition = ChatActivity.this.chatListView.findViewHolderForAdapterPosition(backgroundDrawableLeft);
                                                if (findViewHolderForAdapterPosition != null) {
                                                    break;
                                                }
                                                childCount = findViewHolderForAdapterPosition.itemView.getTop();
                                                if (!(findViewHolderForAdapterPosition.itemView instanceof ChatMessageCell) || !((ChatMessageCell) findViewHolderForAdapterPosition.itemView).isPinnedTop()) {
                                                    break;
                                                }
                                                childViewHolder = findViewHolderForAdapterPosition;
                                            }
                                        }
                                    }
                                    backgroundDrawableLeft = view.getTop() + chatMessageCell.getLayoutHeight();
                                    i = ChatActivity.this.chatListView.getMeasuredHeight() - ChatActivity.this.chatListView.getPaddingBottom();
                                    if (backgroundDrawableLeft <= i) {
                                        i = backgroundDrawableLeft;
                                    }
                                    if (i - AndroidUtilities.dp(48.0f) < childCount) {
                                        i = AndroidUtilities.dp(48.0f) + childCount;
                                    }
                                    avatarImage.setImageY(i - AndroidUtilities.dp(44.0f));
                                    avatarImage.draw(canvas);
                                }
                            }
                            return drawChild;
                        }
                    }
                }
                i = 0;
                backgroundDrawableLeft = 0;
                if (backgroundDrawableLeft == 0) {
                    canvas.save();
                    canvas.clipRect(backgroundDrawableLeft, view.getTop(), view.getRight(), view.getBottom());
                } else if (i != 0) {
                    canvas.save();
                    canvas.clipRect(view.getLeft(), view.getTop(), view.getRight(), i);
                }
                drawChild = super.drawChild(canvas, view, j);
                canvas.restore();
                childCount = getChildCount();
                for (i = 0; i < childCount; i++) {
                    if (getChildAt(i) != view) {
                        i2 = i;
                        break;
                    }
                }
                i2 = 0;
                if (i2 == childCount - 1) {
                    size = this.drawTimeAfter.size();
                    if (size > 0) {
                        for (backgroundDrawableLeft = 0; backgroundDrawableLeft < size; backgroundDrawableLeft++) {
                            chatMessageCell = (ChatMessageCell) this.drawTimeAfter.get(backgroundDrawableLeft);
                            canvas.save();
                            canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                            chatMessageCell.drawTimeLayout(canvas);
                            canvas.restore();
                        }
                        this.drawTimeAfter.clear();
                    }
                    size = this.drawNamesAfter.size();
                    if (size > 0) {
                        for (backgroundDrawableLeft = 0; backgroundDrawableLeft < size; backgroundDrawableLeft++) {
                            chatMessageCell = (ChatMessageCell) this.drawNamesAfter.get(backgroundDrawableLeft);
                            canvas.save();
                            canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                            chatMessageCell.drawNamesLayout(canvas);
                            canvas.restore();
                        }
                        this.drawNamesAfter.clear();
                    }
                    size = this.drawCaptionAfter.size();
                    if (size > 0) {
                        for (backgroundDrawableLeft = 0; backgroundDrawableLeft < size; backgroundDrawableLeft++) {
                            chatMessageCell = (ChatMessageCell) this.drawCaptionAfter.get(backgroundDrawableLeft);
                            canvas.save();
                            canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                            chatMessageCell.drawCaptionLayout(canvas);
                            canvas.restore();
                        }
                        this.drawCaptionAfter.clear();
                    }
                }
                if (view instanceof ChatMessageCell) {
                    chatMessageCell = (ChatMessageCell) view;
                    currentPosition = chatMessageCell.getCurrentPosition();
                    if (currentPosition != null) {
                        if (i2 == childCount - 1) {
                            canvas.save();
                            canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                            if (currentPosition.last) {
                                chatMessageCell.drawTimeLayout(canvas);
                            }
                            chatMessageCell.drawNamesLayout(canvas);
                            canvas.restore();
                        } else {
                            if (currentPosition.last) {
                                this.drawTimeAfter.add(chatMessageCell);
                            }
                            this.drawNamesAfter.add(chatMessageCell);
                        }
                        if (i2 == childCount - 1) {
                            canvas.save();
                            canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                            chatMessageCell.drawCaptionLayout(canvas);
                            canvas.restore();
                        } else {
                            this.drawCaptionAfter.add(chatMessageCell);
                        }
                    }
                    avatarImage = chatMessageCell.getAvatarImage();
                    if (avatarImage != null) {
                        access$7400 = ChatActivity.this.getValidGroupedMessage(chatMessageCell.getMessageObject());
                        childCount = view.getTop();
                        if (chatMessageCell.isPinnedBottom()) {
                            childViewHolder = ChatActivity.this.chatListView.getChildViewHolder(view);
                            if (childViewHolder != null) {
                                backgroundDrawableLeft = childViewHolder.getAdapterPosition();
                                if (access$7400 != null) {
                                }
                                backgroundDrawableLeft--;
                                if (ChatActivity.this.chatListView.findViewHolderForAdapterPosition(backgroundDrawableLeft) != null) {
                                    avatarImage.setImageY(-AndroidUtilities.dp(1000.0f));
                                    avatarImage.draw(canvas);
                                    return drawChild;
                                }
                            }
                        }
                        if (chatMessageCell.isPinnedTop()) {
                            childViewHolder = ChatActivity.this.chatListView.getChildViewHolder(view);
                            if (childViewHolder != null) {
                                while (true) {
                                    backgroundDrawableLeft = childViewHolder.getAdapterPosition();
                                    if (access$7400 != null) {
                                    }
                                    backgroundDrawableLeft++;
                                    findViewHolderForAdapterPosition = ChatActivity.this.chatListView.findViewHolderForAdapterPosition(backgroundDrawableLeft);
                                    if (findViewHolderForAdapterPosition != null) {
                                        break;
                                    }
                                    childCount = findViewHolderForAdapterPosition.itemView.getTop();
                                    childViewHolder = findViewHolderForAdapterPosition;
                                }
                            }
                        }
                        backgroundDrawableLeft = view.getTop() + chatMessageCell.getLayoutHeight();
                        i = ChatActivity.this.chatListView.getMeasuredHeight() - ChatActivity.this.chatListView.getPaddingBottom();
                        if (backgroundDrawableLeft <= i) {
                            i = backgroundDrawableLeft;
                        }
                        if (i - AndroidUtilities.dp(48.0f) < childCount) {
                            i = AndroidUtilities.dp(48.0f) + childCount;
                        }
                        avatarImage.setImageY(i - AndroidUtilities.dp(44.0f));
                        avatarImage.draw(canvas);
                    }
                }
                return drawChild;
            }

            protected void onChildPressed(View view, boolean z) {
                super.onChildPressed(view, z);
                if (view instanceof ChatMessageCell) {
                    GroupedMessages currentMessagesGroup = ((ChatMessageCell) view).getCurrentMessagesGroup();
                    if (currentMessagesGroup != null) {
                        int childCount = getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View childAt = getChildAt(i);
                            if (childAt != view && (childAt instanceof ChatMessageCell)) {
                                ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                                if (((ChatMessageCell) childAt).getCurrentMessagesGroup() == currentMessagesGroup) {
                                    chatMessageCell.setPressed(z);
                                }
                            }
                        }
                    }
                }
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                ChatActivity.this.forceScrollToTop = false;
                if (ChatActivity.this.chatAdapter.isBot) {
                    int childCount = getChildCount();
                    for (int i5 = 0; i5 < childCount; i5++) {
                        View childAt = getChildAt(i5);
                        if (childAt instanceof BotHelpCell) {
                            i5 = ((i4 - i2) / 2) - (childAt.getMeasuredHeight() / 2);
                            if (childAt.getTop() > i5) {
                                childAt.layout(0, i5, i3 - i, childAt.getMeasuredHeight() + i5);
                                return;
                            }
                            return;
                        }
                    }
                }
            }

            public void requestLayout() {
                if (!ChatActivity.this.chatListViewIgnoreLayout) {
                    super.requestLayout();
                }
            }
        };
        Callback anonymousClass21 = new SimpleCallback(0, 4) {
            public int getSwipeDirs(org.telegram.messenger.support.widget.RecyclerView recyclerView, ViewHolder viewHolder) {
                return viewHolder.itemView instanceof ChatMessageCell ? super.getSwipeDirs(recyclerView, viewHolder) : 0;
            }

            public boolean onMove(org.telegram.messenger.support.widget.RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2) {
                return false;
            }

            public void onSwiped(ViewHolder viewHolder, int i) {
                viewHolder.itemView.setX(BitmapDescriptorFactory.HUE_RED);
                viewHolder.itemView.setY(BitmapDescriptorFactory.HUE_RED);
                ChatActivity.this.chatAdapter.notifyDataSetChanged();
                if (viewHolder.itemView instanceof ChatMessageCell) {
                    ChatActivity.this.showReplyPanel(true, ((ChatMessageCell) viewHolder.itemView).getMessageObject(), null, null, false);
                }
            }
        };
        if (!ChatObject.isChannel(this.currentChat) || this.currentChat.megagroup || ChatObject.hasAdminRights(this.currentChat)) {
            new ItemTouchHelper(anonymousClass21).attachToRecyclerView(this.chatListView);
        }
        this.chatListView.setTag(Integer.valueOf(1));
        this.chatListView.setVerticalScrollBarEnabled(true);
        RecyclerListView recyclerListView = this.chatListView;
        Adapter chatActivityAdapter = new ChatActivityAdapter(context);
        this.chatAdapter = chatActivityAdapter;
        recyclerListView.setAdapter(chatActivityAdapter);
        this.chatListView.setClipToPadding(false);
        this.chatListView.setPadding(0, AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(3.0f));
        this.chatListView.setItemAnimator(null);
        this.chatListView.setLayoutAnimation(null);
        if (this.currentChat != null && ChatObject.isNotInChat(this.currentChat)) {
            this.chatListView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        }
        this.chatLayoutManager = new GridLayoutManagerFixed(context, 1000, 1, true) {
            protected boolean hasSiblingChild(int i) {
                if (i >= ChatActivity.this.chatAdapter.messagesStartRow && i < ChatActivity.this.chatAdapter.messagesEndRow) {
                    int access$7500 = i - ChatActivity.this.chatAdapter.messagesStartRow;
                    if (access$7500 >= 0 && access$7500 < ChatActivity.this.messages.size()) {
                        MessageObject messageObject = (MessageObject) ChatActivity.this.messages.get(access$7500);
                        GroupedMessages access$7400 = ChatActivity.this.getValidGroupedMessage(messageObject);
                        if (access$7400 != null) {
                            GroupedMessagePosition groupedMessagePosition = (GroupedMessagePosition) access$7400.positions.get(messageObject);
                            if (groupedMessagePosition.minX == groupedMessagePosition.maxX || groupedMessagePosition.minY != groupedMessagePosition.maxY || groupedMessagePosition.minY == (byte) 0) {
                                return false;
                            }
                            int size = access$7400.posArray.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                GroupedMessagePosition groupedMessagePosition2 = (GroupedMessagePosition) access$7400.posArray.get(i2);
                                if (groupedMessagePosition2 != groupedMessagePosition && groupedMessagePosition2.minY <= groupedMessagePosition.minY && groupedMessagePosition2.maxY >= groupedMessagePosition.minY) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }

            public boolean shouldLayoutChildFromOpositeSide(View view) {
                return (view instanceof ChatMessageCell) && !((ChatMessageCell) view).getMessageObject().isOutOwner();
            }

            public void smoothScrollToPosition(org.telegram.messenger.support.widget.RecyclerView recyclerView, State state, int i) {
                SmoothScroller linearSmoothScrollerMiddle = new LinearSmoothScrollerMiddle(recyclerView.getContext());
                linearSmoothScrollerMiddle.setTargetPosition(i);
                startSmoothScroll(linearSmoothScrollerMiddle);
            }

            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.chatLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            public int getSpanSize(int i) {
                if (i >= ChatActivity.this.chatAdapter.messagesStartRow && i < ChatActivity.this.chatAdapter.messagesEndRow) {
                    int access$7500 = i - ChatActivity.this.chatAdapter.messagesStartRow;
                    if (access$7500 >= 0 && access$7500 < ChatActivity.this.messages.size()) {
                        MessageObject messageObject = (MessageObject) ChatActivity.this.messages.get(access$7500);
                        GroupedMessages access$7400 = ChatActivity.this.getValidGroupedMessage(messageObject);
                        if (access$7400 != null) {
                            return ((GroupedMessagePosition) access$7400.positions.get(messageObject)).spanSize;
                        }
                    }
                }
                return 1000;
            }
        });
        this.chatListView.setLayoutManager(this.chatLayoutManager);
        this.chatListView.addItemDecoration(new ItemDecoration() {
            public void getItemOffsets(Rect rect, View view, org.telegram.messenger.support.widget.RecyclerView recyclerView, State state) {
                int i = 0;
                rect.bottom = 0;
                if (view instanceof ChatMessageCell) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                    GroupedMessages currentMessagesGroup = chatMessageCell.getCurrentMessagesGroup();
                    if (currentMessagesGroup != null) {
                        GroupedMessagePosition currentPosition = chatMessageCell.getCurrentPosition();
                        if (currentPosition != null && currentPosition.siblingHeights != null) {
                            int ceil;
                            float max = ((float) Math.max(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) * 0.5f;
                            int i2 = 0;
                            for (float f : currentPosition.siblingHeights) {
                                i2 += (int) Math.ceil((double) (f * max));
                            }
                            i2 += (currentPosition.maxY - currentPosition.minY) * AndroidUtilities.dp(11.0f);
                            int size = currentMessagesGroup.posArray.size();
                            while (i < size) {
                                GroupedMessagePosition groupedMessagePosition = (GroupedMessagePosition) currentMessagesGroup.posArray.get(i);
                                if (groupedMessagePosition.minY == currentPosition.minY && ((groupedMessagePosition.minX != currentPosition.minX || groupedMessagePosition.maxX != currentPosition.maxX || groupedMessagePosition.minY != currentPosition.minY || groupedMessagePosition.maxY != currentPosition.maxY) && groupedMessagePosition.minY == currentPosition.minY)) {
                                    ceil = i2 - (((int) Math.ceil((double) (groupedMessagePosition.ph * max))) - AndroidUtilities.dp(4.0f));
                                    break;
                                }
                                i++;
                            }
                            ceil = i2;
                            rect.bottom = -ceil;
                        }
                    }
                }
            }
        });
        this.contentView.addView(this.chatListView, LayoutHelper.createFrame(-1, -1.0f));
        this.chatListView.setOnItemLongClickListener(this.onItemLongClickListener);
        this.chatListView.setOnItemClickListener(this.onItemClickListener);
        this.chatListView.setOnScrollListener(new OnScrollListener() {
            private final int scrollValue = AndroidUtilities.dp(100.0f);
            private float totalDy = BitmapDescriptorFactory.HUE_RED;

            /* renamed from: org.telegram.ui.ChatActivity$25$1 */
            class C42351 extends AnimatorListenerAdapter {
                C42351() {
                }

                public void onAnimationEnd(Animator animator) {
                    if (animator.equals(ChatActivity.this.floatingDateAnimation)) {
                        ChatActivity.this.floatingDateAnimation = null;
                    }
                }
            }

            public void onScrollStateChanged(org.telegram.messenger.support.widget.RecyclerView recyclerView, int i) {
                if (i == 1) {
                    ChatActivity.this.scrollingFloatingDate = true;
                    ChatActivity.this.checkTextureViewPosition = true;
                } else if (i == 0) {
                    ChatActivity.this.scrollingFloatingDate = false;
                    ChatActivity.this.checkTextureViewPosition = false;
                    ChatActivity.this.hideFloatingDateView(true);
                }
            }

            public void onScrolled(org.telegram.messenger.support.widget.RecyclerView recyclerView, int i, int i2) {
                ChatActivity.this.chatListView.invalidate();
                if (!(i2 == 0 || !ChatActivity.this.scrollingFloatingDate || ChatActivity.this.currentFloatingTopIsNotMessage)) {
                    if (ChatActivity.this.highlightMessageId != Integer.MAX_VALUE) {
                        ChatActivity.this.highlightMessageId = Integer.MAX_VALUE;
                        ChatActivity.this.updateVisibleRows();
                    }
                    if (ChatActivity.this.floatingDateView.getTag() == null) {
                        if (ChatActivity.this.floatingDateAnimation != null) {
                            ChatActivity.this.floatingDateAnimation.cancel();
                        }
                        ChatActivity.this.floatingDateView.setTag(Integer.valueOf(1));
                        ChatActivity.this.floatingDateAnimation = new AnimatorSet();
                        ChatActivity.this.floatingDateAnimation.setDuration(150);
                        AnimatorSet access$8200 = ChatActivity.this.floatingDateAnimation;
                        Animator[] animatorArr = new Animator[1];
                        animatorArr[0] = ObjectAnimator.ofFloat(ChatActivity.this.floatingDateView, "alpha", new float[]{1.0f});
                        access$8200.playTogether(animatorArr);
                        ChatActivity.this.floatingDateAnimation.addListener(new C42351());
                        ChatActivity.this.floatingDateAnimation.start();
                    }
                }
                ChatActivity.this.checkScrollForLoad(true);
                int findFirstVisibleItemPosition = ChatActivity.this.chatLayoutManager.findFirstVisibleItemPosition();
                if (findFirstVisibleItemPosition != -1) {
                    ChatActivity.this.chatAdapter.getItemCount();
                    if (findFirstVisibleItemPosition == 0 && ChatActivity.this.forwardEndReached[0]) {
                        ChatActivity.this.showPagedownButton(false, true);
                    } else if (i2 > 0) {
                        if (ChatActivity.this.pagedownButton.getTag() == null) {
                            this.totalDy += (float) i2;
                            if (this.totalDy > ((float) this.scrollValue)) {
                                this.totalDy = BitmapDescriptorFactory.HUE_RED;
                                ChatActivity.this.showPagedownButton(true, true);
                                ChatActivity.this.pagedownButtonShowedByScroll = true;
                            }
                        }
                    } else if (ChatActivity.this.pagedownButtonShowedByScroll && ChatActivity.this.pagedownButton.getTag() != null) {
                        this.totalDy += (float) i2;
                        if (this.totalDy < ((float) (-this.scrollValue))) {
                            ChatActivity.this.showPagedownButton(false, true);
                            this.totalDy = BitmapDescriptorFactory.HUE_RED;
                        }
                    }
                }
                ChatActivity.this.updateMessagesVisisblePart();
            }
        });
        if (this.scrollToPositionOnRecreate != -1) {
            this.chatLayoutManager.scrollToPositionWithOffset(this.scrollToPositionOnRecreate, this.scrollToOffsetOnRecreate);
            this.scrollToPositionOnRecreate = -1;
        }
        this.progressView = new FrameLayout(context);
        this.progressView.setVisibility(4);
        this.contentView.addView(this.progressView, LayoutHelper.createFrame(-1, -1, 51));
        this.progressView2 = new View(context);
        this.progressView2.setBackgroundResource(R.drawable.system_loader);
        this.progressView2.getBackground().setColorFilter(Theme.colorFilter);
        this.progressView.addView(this.progressView2, LayoutHelper.createFrame(36, 36, 17));
        this.progressBar = new RadialProgressView(context);
        this.progressBar.setSize(AndroidUtilities.dp(28.0f));
        this.progressBar.setProgressColor(Theme.getColor(Theme.key_chat_serviceText));
        this.progressView.addView(this.progressBar, LayoutHelper.createFrame(32, 32, 17));
        try {
            if (!(this.chatAdapter == null || this.chatAdapter.isBot)) {
                DialogStatus b = C2666a.getDatabaseHandler().b(this.dialog_id);
                if (b == null) {
                    addInviteBoxToUser(context, this.contentView);
                    C2666a.getDatabaseHandler().a(new DialogStatus(this.dialog_id, false, false));
                } else if (!(b.isHasHotgram() || b.isInviteSent() || BuildConfig.FLAVOR.contains("mowjgram"))) {
                    addInviteBoxToUser(context, this.contentView);
                }
            }
        } catch (Exception e3) {
        }
        this.floatingDateView = new ChatActionCell(context);
        this.floatingDateView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.contentView.addView(this.floatingDateView, LayoutHelper.createFrame(-2, -2.0f, 49, BitmapDescriptorFactory.HUE_RED, this.inviteBoxAdded ? 24.0f : 4.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.floatingDateView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ChatActivity.this.floatingDateView.getAlpha() != BitmapDescriptorFactory.HUE_RED) {
                    Calendar instance = Calendar.getInstance();
                    instance.setTimeInMillis(((long) ChatActivity.this.floatingDateView.getCustomDate()) * 1000);
                    int i = instance.get(1);
                    int i2 = instance.get(2);
                    int i3 = instance.get(5);
                    instance.clear();
                    instance.set(i, i2, i3);
                    ChatActivity.this.jumpToDate((int) (instance.getTime().getTime() / 1000));
                }
            }
        });
        addQuickAccessToUsers(context, this.contentView);
        if (ChatObject.isChannel(this.currentChat)) {
            this.pinnedMessageView = new FrameLayout(context);
            this.pinnedMessageView.setTag(Integer.valueOf(1));
            this.pinnedMessageView.setTranslationY((float) (-AndroidUtilities.dp(50.0f)));
            this.pinnedMessageView.setVisibility(8);
            this.pinnedMessageView.setBackgroundResource(R.drawable.blockpanel);
            this.pinnedMessageView.getBackground().setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_topPanelBackground), Mode.MULTIPLY));
            this.contentView.addView(this.pinnedMessageView, LayoutHelper.createFrame(-1, 50, 51));
            this.pinnedMessageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ChatActivity.this.scrollToMessageId(ChatActivity.this.info.pinned_msg_id, 0, true, 0, false);
                }
            });
            this.pinnedLineView = new View(context);
            this.pinnedLineView.setBackgroundColor(Theme.getColor(Theme.key_chat_topPanelLine));
            this.pinnedMessageView.addView(this.pinnedLineView, LayoutHelper.createFrame(2, 32.0f, 51, 8.0f, 8.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.pinnedMessageImageView = new BackupImageView(context);
            this.pinnedMessageView.addView(this.pinnedMessageImageView, LayoutHelper.createFrame(32, 32.0f, 51, 17.0f, 8.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.pinnedMessageNameTextView = new SimpleTextView(context);
            this.pinnedMessageNameTextView.setTextSize(14);
            this.pinnedMessageNameTextView.setTextColor(Theme.getColor(Theme.key_chat_topPanelTitle));
            this.pinnedMessageNameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.pinnedMessageView.addView(this.pinnedMessageNameTextView, LayoutHelper.createFrame(-1, (float) AndroidUtilities.dp(18.0f), 51, 18.0f, 7.3f, 52.0f, BitmapDescriptorFactory.HUE_RED));
            this.pinnedMessageTextView = new SimpleTextView(context);
            this.pinnedMessageTextView.setTextSize(14);
            this.pinnedMessageTextView.setTextColor(Theme.getColor(Theme.key_chat_topPanelMessage));
            this.pinnedMessageView.addView(this.pinnedMessageTextView, LayoutHelper.createFrame(-1, (float) AndroidUtilities.dp(18.0f), 51, 18.0f, 25.3f, 52.0f, BitmapDescriptorFactory.HUE_RED));
            this.closePinned = new ImageView(context);
            this.closePinned.setImageResource(R.drawable.miniplayer_close);
            this.closePinned.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_topPanelClose), Mode.MULTIPLY));
            this.closePinned.setScaleType(ScaleType.CENTER);
            this.pinnedMessageView.addView(this.closePinned, LayoutHelper.createFrame(48, 48, 53));
            this.closePinned.setOnClickListener(new View.OnClickListener() {

                /* renamed from: org.telegram.ui.ChatActivity$28$1 */
                class C42361 implements OnClickListener {
                    C42361() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        MessagesController.getInstance().pinChannelMessage(ChatActivity.this.currentChat, 0, false);
                    }
                }

                public void onClick(View view) {
                    if (ChatActivity.this.getParentActivity() != null) {
                        if (ChatActivity.this.currentChat.creator || (ChatActivity.this.currentChat.admin_rights != null && ((ChatActivity.this.currentChat.megagroup && ChatActivity.this.currentChat.admin_rights.pin_messages) || (!ChatActivity.this.currentChat.megagroup && ChatActivity.this.currentChat.admin_rights.edit_messages)))) {
                            org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                            builder.setMessage(LocaleController.getString("UnpinMessageAlert", R.string.UnpinMessageAlert));
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C42361());
                            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                            ChatActivity.this.showDialog(builder.create());
                            return;
                        }
                        ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putInt("pin_" + ChatActivity.this.dialog_id, ChatActivity.this.info.pinned_msg_id).commit();
                        ChatActivity.this.updatePinnedMessageView(true);
                    }
                }
            });
        }
        this.reportSpamView = new LinearLayout(context);
        this.reportSpamView.setTag(Integer.valueOf(1));
        this.reportSpamView.setTranslationY((float) (-AndroidUtilities.dp(50.0f)));
        this.reportSpamView.setVisibility(8);
        this.reportSpamView.setBackgroundResource(R.drawable.blockpanel);
        this.reportSpamView.getBackground().setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_topPanelBackground), Mode.MULTIPLY));
        this.contentView.addView(this.reportSpamView, LayoutHelper.createFrame(-1, 50, 51));
        this.addToContactsButton = new TextView(context);
        this.addToContactsButton.setTextColor(Theme.getColor(Theme.key_chat_addContact));
        this.addToContactsButton.setVisibility(8);
        this.addToContactsButton.setTextSize(1, 14.0f);
        this.addToContactsButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.addToContactsButton.setSingleLine(true);
        this.addToContactsButton.setMaxLines(1);
        this.addToContactsButton.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
        this.addToContactsButton.setGravity(17);
        this.addToContactsButton.setText(LocaleController.getString("AddContactChat", R.string.AddContactChat));
        this.reportSpamView.addView(this.addToContactsButton, LayoutHelper.createLinear(-1, -1, 0.5f, 51, 0, 0, 0, AndroidUtilities.dp(1.0f)));
        this.addToContactsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", ChatActivity.this.currentUser.id);
                bundle.putBoolean("addContact", true);
                ChatActivity.this.presentFragment(new ContactAddActivity(bundle));
            }
        });
        this.reportSpamContainer = new FrameLayout(context);
        this.reportSpamView.addView(this.reportSpamContainer, LayoutHelper.createLinear(-1, -1, 1.0f, 51, 0, 0, 0, AndroidUtilities.dp(1.0f)));
        this.reportSpamButton = new TextView(context);
        this.reportSpamButton.setTextColor(Theme.getColor(Theme.key_chat_reportSpam));
        this.reportSpamButton.setTextSize(1, 14.0f);
        this.reportSpamButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.reportSpamButton.setSingleLine(true);
        this.reportSpamButton.setMaxLines(1);
        if (this.currentChat != null) {
            this.reportSpamButton.setText(LocaleController.getString("ReportSpamAndLeave", R.string.ReportSpamAndLeave));
        } else {
            this.reportSpamButton.setText(LocaleController.getString("ReportSpam", R.string.ReportSpam));
        }
        this.reportSpamButton.setGravity(17);
        this.reportSpamButton.setPadding(AndroidUtilities.dp(50.0f), 0, AndroidUtilities.dp(50.0f), 0);
        this.reportSpamContainer.addView(this.reportSpamButton, LayoutHelper.createFrame(-1, -1, 51));
        this.reportSpamButton.setOnClickListener(new View.OnClickListener() {

            /* renamed from: org.telegram.ui.ChatActivity$30$1 */
            class C42381 implements OnClickListener {
                C42381() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (ChatActivity.this.currentUser != null) {
                        MessagesController.getInstance().blockUser(ChatActivity.this.currentUser.id);
                    }
                    MessagesController.getInstance().reportSpam(ChatActivity.this.dialog_id, ChatActivity.this.currentUser, ChatActivity.this.currentChat, ChatActivity.this.currentEncryptedChat);
                    ChatActivity.this.updateSpamView();
                    if (ChatActivity.this.currentChat == null) {
                        MessagesController.getInstance().deleteDialog(ChatActivity.this.dialog_id, 0);
                    } else if (ChatObject.isNotInChat(ChatActivity.this.currentChat)) {
                        MessagesController.getInstance().deleteDialog(ChatActivity.this.dialog_id, 0);
                    } else {
                        MessagesController.getInstance().deleteUserFromChat((int) (-ChatActivity.this.dialog_id), MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId())), null);
                    }
                    ChatActivity.this.finishFragment();
                }
            }

            public void onClick(View view) {
                if (ChatActivity.this.getParentActivity() != null) {
                    org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                    if (ChatObject.isChannel(ChatActivity.this.currentChat) && !ChatActivity.this.currentChat.megagroup) {
                        builder.setMessage(LocaleController.getString("ReportSpamAlertChannel", R.string.ReportSpamAlertChannel));
                    } else if (ChatActivity.this.currentChat != null) {
                        builder.setMessage(LocaleController.getString("ReportSpamAlertGroup", R.string.ReportSpamAlertGroup));
                    } else {
                        builder.setMessage(LocaleController.getString("ReportSpamAlert", R.string.ReportSpamAlert));
                    }
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C42381());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    ChatActivity.this.showDialog(builder.create());
                }
            }
        });
        this.closeReportSpam = new ImageView(context);
        this.closeReportSpam.setImageResource(R.drawable.miniplayer_close);
        this.closeReportSpam.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_topPanelClose), Mode.MULTIPLY));
        this.closeReportSpam.setScaleType(ScaleType.CENTER);
        this.reportSpamContainer.addView(this.closeReportSpam, LayoutHelper.createFrame(48, 48, 53));
        this.closeReportSpam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MessagesController.getInstance().hideReportSpam(ChatActivity.this.dialog_id, ChatActivity.this.currentUser, ChatActivity.this.currentChat);
                ChatActivity.this.updateSpamView();
            }
        });
        this.alertView = new FrameLayout(context);
        this.alertView.setTag(Integer.valueOf(1));
        this.alertView.setTranslationY((float) (-AndroidUtilities.dp(50.0f)));
        this.alertView.setVisibility(8);
        this.alertView.setBackgroundResource(R.drawable.blockpanel);
        this.alertView.getBackground().setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_topPanelBackground), Mode.MULTIPLY));
        this.contentView.addView(this.alertView, LayoutHelper.createFrame(-1, 50, 51));
        this.alertNameTextView = new TextView(context);
        this.alertNameTextView.setTextSize(1, 14.0f);
        this.alertNameTextView.setTextColor(Theme.getColor(Theme.key_chat_topPanelTitle));
        this.alertNameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.alertNameTextView.setSingleLine(true);
        this.alertNameTextView.setEllipsize(TruncateAt.END);
        this.alertNameTextView.setMaxLines(1);
        this.alertView.addView(this.alertNameTextView, LayoutHelper.createFrame(-2, -2.0f, 51, 8.0f, 5.0f, 8.0f, BitmapDescriptorFactory.HUE_RED));
        this.alertTextView = new TextView(context);
        this.alertTextView.setTextSize(1, 14.0f);
        this.alertTextView.setTextColor(Theme.getColor(Theme.key_chat_topPanelMessage));
        this.alertTextView.setSingleLine(true);
        this.alertTextView.setEllipsize(TruncateAt.END);
        this.alertTextView.setMaxLines(1);
        this.alertView.addView(this.alertTextView, LayoutHelper.createFrame(-2, -2.0f, 51, 8.0f, 23.0f, 8.0f, BitmapDescriptorFactory.HUE_RED));
        this.pagedownButton = new FrameLayout(context);
        this.pagedownButton.setVisibility(4);
        this.contentView.addView(this.pagedownButton, LayoutHelper.createFrame(46, 59.0f, 85, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 7.0f, 5.0f));
        this.pagedownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ChatActivity.this.checkTextureViewPosition = true;
                if (ChatActivity.this.createUnreadMessageAfterId != 0) {
                    ChatActivity.this.scrollToMessageId(ChatActivity.this.createUnreadMessageAfterId, 0, false, ChatActivity.this.returnToLoadIndex, false);
                } else if (ChatActivity.this.returnToMessageId > 0) {
                    ChatActivity.this.scrollToMessageId(ChatActivity.this.returnToMessageId, 0, true, ChatActivity.this.returnToLoadIndex, false);
                } else {
                    ChatActivity.this.scrollToLastMessage(true);
                }
            }
        });
        this.mentiondownButton = new FrameLayout(context);
        this.mentiondownButton.setVisibility(4);
        this.contentView.addView(this.mentiondownButton, LayoutHelper.createFrame(46, 59.0f, 85, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 7.0f, 5.0f));
        this.mentiondownButton.setOnClickListener(new View.OnClickListener() {

            /* renamed from: org.telegram.ui.ChatActivity$33$1 */
            class C42391 implements IntCallback {
                C42391() {
                }

                public void run(int i) {
                    if (i == 0) {
                        ChatActivity.this.hasAllMentionsLocal = false;
                        AnonymousClass33.this.loadLastUnreadMention();
                        return;
                    }
                    ChatActivity.this.scrollToMessageId(i, 0, false, 0, false);
                }
            }

            /* renamed from: org.telegram.ui.ChatActivity$33$2 */
            class C42412 implements RequestDelegate {
                C42412() {
                }

                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                            if (tLRPC$TL_error != null || tLRPC$messages_Messages.messages.isEmpty()) {
                                if (tLRPC$messages_Messages != null) {
                                    ChatActivity.this.newMentionsCount = tLRPC$messages_Messages.count;
                                } else {
                                    ChatActivity.this.newMentionsCount = 0;
                                }
                                MessagesStorage.getInstance().resetMentionsCount(ChatActivity.this.dialog_id, ChatActivity.this.newMentionsCount);
                                if (ChatActivity.this.newMentionsCount == 0) {
                                    ChatActivity.this.hasAllMentionsLocal = true;
                                    ChatActivity.this.showMentiondownButton(false, true);
                                    return;
                                }
                                ChatActivity.this.mentiondownButtonCounter.setText(String.format("%d", new Object[]{Integer.valueOf(ChatActivity.this.newMentionsCount)}));
                                AnonymousClass33.this.loadLastUnreadMention();
                                return;
                            }
                            int i = ((Message) tLRPC$messages_Messages.messages.get(0)).id;
                            long j = (long) i;
                            if (ChatObject.isChannel(ChatActivity.this.currentChat)) {
                                j |= ((long) ChatActivity.this.currentChat.id) << 32;
                            }
                            MessageObject messageObject = (MessageObject) ChatActivity.this.messagesDict[0].get(Integer.valueOf(i));
                            MessagesStorage.getInstance().markMessageAsMention(j);
                            if (messageObject != null) {
                                messageObject.messageOwner.media_unread = true;
                                messageObject.messageOwner.mentioned = true;
                            }
                            ChatActivity.this.scrollToMessageId(i, 0, false, 0, false);
                        }
                    });
                }
            }

            private void loadLastUnreadMention() {
                if (ChatActivity.this.hasAllMentionsLocal) {
                    MessagesStorage.getInstance().getUnreadMention(ChatActivity.this.dialog_id, new C42391());
                    return;
                }
                TLObject tLRPC$TL_messages_getUnreadMentions = new TLRPC$TL_messages_getUnreadMentions();
                tLRPC$TL_messages_getUnreadMentions.peer = MessagesController.getInputPeer((int) ChatActivity.this.dialog_id);
                tLRPC$TL_messages_getUnreadMentions.limit = 1;
                tLRPC$TL_messages_getUnreadMentions.add_offset = ChatActivity.this.newMentionsCount - 1;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getUnreadMentions, new C42412());
            }

            public void onClick(View view) {
                loadLastUnreadMention();
            }
        });
        this.mentiondownButton.setOnLongClickListener(new OnLongClickListener() {

            /* renamed from: org.telegram.ui.ChatActivity$34$1 */
            class C42421 implements RequestDelegate {
                C42421() {
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                }
            }

            public boolean onLongClick(View view) {
                for (int i = 0; i < ChatActivity.this.messages.size(); i++) {
                    MessageObject messageObject = (MessageObject) ChatActivity.this.messages.get(i);
                    if (messageObject.messageOwner.mentioned && !messageObject.isContentUnread()) {
                        messageObject.setContentIsRead();
                    }
                }
                ChatActivity.this.newMentionsCount = 0;
                MessagesStorage.getInstance().resetMentionsCount(ChatActivity.this.dialog_id, ChatActivity.this.newMentionsCount);
                ChatActivity.this.hasAllMentionsLocal = true;
                ChatActivity.this.showMentiondownButton(false, true);
                TLObject tLRPC$TL_messages_readMentions = new TLRPC$TL_messages_readMentions();
                tLRPC$TL_messages_readMentions.peer = MessagesController.getInputPeer((int) ChatActivity.this.dialog_id);
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_readMentions, new C42421());
                return true;
            }
        });
        if (!this.isBroadcast) {
            this.mentionContainer = new FrameLayout(context) {
                public void onDraw(Canvas canvas) {
                    if (ChatActivity.this.mentionListView.getChildCount() > 0) {
                        int access$9800;
                        if (ChatActivity.this.mentionLayoutManager.getReverseLayout()) {
                            access$9800 = ChatActivity.this.mentionListViewScrollOffsetY + AndroidUtilities.dp(2.0f);
                            Theme.chat_composeShadowDrawable.setBounds(0, Theme.chat_composeShadowDrawable.getIntrinsicHeight() + access$9800, getMeasuredWidth(), access$9800);
                            Theme.chat_composeShadowDrawable.draw(canvas);
                            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) access$9800, Theme.chat_composeBackgroundPaint);
                            return;
                        }
                        access$9800 = (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.isMediaLayout() && ChatActivity.this.mentionsAdapter.getBotContextSwitch() == null) ? ChatActivity.this.mentionListViewScrollOffsetY - AndroidUtilities.dp(4.0f) : ChatActivity.this.mentionListViewScrollOffsetY - AndroidUtilities.dp(2.0f);
                        int intrinsicHeight = Theme.chat_composeShadowDrawable.getIntrinsicHeight() + access$9800;
                        Theme.chat_composeShadowDrawable.setBounds(0, access$9800, getMeasuredWidth(), intrinsicHeight);
                        Theme.chat_composeShadowDrawable.draw(canvas);
                        canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) intrinsicHeight, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
                    }
                }

                public void requestLayout() {
                    if (!ChatActivity.this.mentionListViewIgnoreLayout) {
                        super.requestLayout();
                    }
                }
            };
            this.mentionContainer.setVisibility(8);
            this.mentionContainer.setWillNotDraw(false);
            this.contentView.addView(this.mentionContainer, LayoutHelper.createFrame(-1, 110, 83));
            this.mentionListView = new RecyclerListView(context) {
                private int lastHeight;
                private int lastWidth;

                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    if (ChatActivity.this.mentionLayoutManager.getReverseLayout()) {
                        if (!(ChatActivity.this.mentionListViewIsScrolling || ChatActivity.this.mentionListViewScrollOffsetY == 0 || motionEvent.getY() <= ((float) ChatActivity.this.mentionListViewScrollOffsetY))) {
                            return false;
                        }
                    } else if (!(ChatActivity.this.mentionListViewIsScrolling || ChatActivity.this.mentionListViewScrollOffsetY == 0 || motionEvent.getY() >= ((float) ChatActivity.this.mentionListViewScrollOffsetY))) {
                        return false;
                    }
                    return super.onInterceptTouchEvent(motionEvent) || StickerPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, ChatActivity.this.mentionListView, 0, null);
                }

                protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                    int i5;
                    int i6;
                    int i7 = i3 - i;
                    int i8 = i4 - i2;
                    if (ChatActivity.this.mentionLayoutManager.getReverseLayout() || ChatActivity.this.mentionListView == null || ChatActivity.this.mentionListViewLastViewPosition < 0 || i7 != this.lastWidth || i8 - this.lastHeight == 0) {
                        i5 = 0;
                        i6 = -1;
                    } else {
                        i6 = ChatActivity.this.mentionListViewLastViewPosition;
                        i5 = ((ChatActivity.this.mentionListViewLastViewTop + i8) - this.lastHeight) - getPaddingTop();
                    }
                    super.onLayout(z, i, i2, i3, i4);
                    if (i6 != -1) {
                        ChatActivity.this.mentionListViewIgnoreLayout = true;
                        if (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.isMediaLayout()) {
                            ChatActivity.this.mentionGridLayoutManager.scrollToPositionWithOffset(i6, i5);
                        } else {
                            ChatActivity.this.mentionLayoutManager.scrollToPositionWithOffset(i6, i5);
                        }
                        super.onLayout(false, i, i2, i3, i4);
                        ChatActivity.this.mentionListViewIgnoreLayout = false;
                    }
                    this.lastHeight = i8;
                    this.lastWidth = i7;
                    ChatActivity.this.mentionListViewUpdateLayout();
                }

                public boolean onTouchEvent(MotionEvent motionEvent) {
                    if (ChatActivity.this.mentionLayoutManager.getReverseLayout()) {
                        if (!(ChatActivity.this.mentionListViewIsScrolling || ChatActivity.this.mentionListViewScrollOffsetY == 0 || motionEvent.getY() <= ((float) ChatActivity.this.mentionListViewScrollOffsetY))) {
                            return false;
                        }
                    } else if (!(ChatActivity.this.mentionListViewIsScrolling || ChatActivity.this.mentionListViewScrollOffsetY == 0 || motionEvent.getY() >= ((float) ChatActivity.this.mentionListViewScrollOffsetY))) {
                        return false;
                    }
                    return super.onTouchEvent(motionEvent);
                }

                public void requestLayout() {
                    if (!ChatActivity.this.mentionListViewIgnoreLayout) {
                        super.requestLayout();
                    }
                }
            };
            this.mentionListView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return StickerPreviewViewer.getInstance().onTouch(motionEvent, ChatActivity.this.mentionListView, 0, ChatActivity.this.mentionsOnItemClickListener, null);
                }
            });
            this.mentionListView.setTag(Integer.valueOf(2));
            this.mentionLayoutManager = new LinearLayoutManager(context) {
                public boolean supportsPredictiveItemAnimations() {
                    return false;
                }
            };
            this.mentionLayoutManager.setOrientation(1);
            this.mentionGridLayoutManager = new ExtendedGridLayoutManager(context, 100) {
                private Size size = new Size();

                protected int getFlowItemCount() {
                    return ChatActivity.this.mentionsAdapter.getBotContextSwitch() != null ? getItemCount() - 1 : super.getFlowItemCount();
                }

                protected Size getSizeForItem(int i) {
                    float f = 100.0f;
                    if (ChatActivity.this.mentionsAdapter.getBotContextSwitch() != null) {
                        i++;
                    }
                    Object item = ChatActivity.this.mentionsAdapter.getItem(i);
                    if (item instanceof BotInlineResult) {
                        BotInlineResult botInlineResult = (BotInlineResult) item;
                        if (botInlineResult.document != null) {
                            this.size.width = botInlineResult.document.thumb != null ? (float) botInlineResult.document.thumb.f10147w : 100.0f;
                            Size size = this.size;
                            if (botInlineResult.document.thumb != null) {
                                f = (float) botInlineResult.document.thumb.f10146h;
                            }
                            size.height = f;
                            for (int i2 = 0; i2 < botInlineResult.document.attributes.size(); i2++) {
                                DocumentAttribute documentAttribute = (DocumentAttribute) botInlineResult.document.attributes.get(i2);
                                if ((documentAttribute instanceof TLRPC$TL_documentAttributeImageSize) || (documentAttribute instanceof TLRPC$TL_documentAttributeVideo)) {
                                    this.size.width = (float) documentAttribute.f10140w;
                                    this.size.height = (float) documentAttribute.f10139h;
                                    break;
                                }
                            }
                        } else {
                            this.size.width = (float) botInlineResult.f10135w;
                            this.size.height = (float) botInlineResult.f10134h;
                        }
                    }
                    return this.size;
                }
            };
            this.mentionGridLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
                public int getSpanSize(int i) {
                    if (ChatActivity.this.mentionsAdapter.getItem(i) instanceof TLRPC$TL_inlineBotSwitchPM) {
                        return 100;
                    }
                    if (ChatActivity.this.mentionsAdapter.getBotContextSwitch() != null) {
                        i--;
                    }
                    return ChatActivity.this.mentionGridLayoutManager.getSpanSizeForItem(i);
                }
            });
            this.mentionListView.addItemDecoration(new ItemDecoration() {
                public void getItemOffsets(Rect rect, View view, org.telegram.messenger.support.widget.RecyclerView recyclerView, State state) {
                    rect.left = 0;
                    rect.right = 0;
                    rect.top = 0;
                    rect.bottom = 0;
                    if (recyclerView.getLayoutManager() == ChatActivity.this.mentionGridLayoutManager) {
                        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
                        if (ChatActivity.this.mentionsAdapter.getBotContextSwitch() == null) {
                            rect.top = AndroidUtilities.dp(2.0f);
                        } else if (childAdapterPosition != 0) {
                            childAdapterPosition--;
                            if (!ChatActivity.this.mentionGridLayoutManager.isFirstRow(childAdapterPosition)) {
                                rect.top = AndroidUtilities.dp(2.0f);
                            }
                        } else {
                            return;
                        }
                        rect.right = ChatActivity.this.mentionGridLayoutManager.isLastInRow(childAdapterPosition) ? 0 : AndroidUtilities.dp(2.0f);
                    }
                }
            });
            this.mentionListView.setItemAnimator(null);
            this.mentionListView.setLayoutAnimation(null);
            this.mentionListView.setClipToPadding(false);
            this.mentionListView.setLayoutManager(this.mentionLayoutManager);
            this.mentionListView.setOverScrollMode(2);
            this.mentionContainer.addView(this.mentionListView, LayoutHelper.createFrame(-1, -1.0f));
            recyclerListView = this.mentionListView;
            chatActivityAdapter = new MentionsAdapter(context, false, this.dialog_id, new MentionsAdapterDelegate() {

                /* renamed from: org.telegram.ui.ChatActivity$42$1 */
                class C42441 extends AnimatorListenerAdapter {
                    C42441() {
                    }

                    public void onAnimationCancel(Animator animator) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animator)) {
                            ChatActivity.this.mentionListAnimation = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animator)) {
                            ChatActivity.this.mentionListAnimation = null;
                        }
                    }
                }

                /* renamed from: org.telegram.ui.ChatActivity$42$2 */
                class C42452 extends AnimatorListenerAdapter {
                    C42452() {
                    }

                    public void onAnimationCancel(Animator animator) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animator)) {
                            ChatActivity.this.mentionListAnimation = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animator)) {
                            ChatActivity.this.mentionContainer.setVisibility(8);
                            ChatActivity.this.mentionContainer.setTag(null);
                            ChatActivity.this.mentionListAnimation = null;
                        }
                    }
                }

                public void needChangePanelVisibility(boolean z) {
                    if (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.isMediaLayout()) {
                        ChatActivity.this.mentionListView.setLayoutManager(ChatActivity.this.mentionGridLayoutManager);
                    } else {
                        ChatActivity.this.mentionListView.setLayoutManager(ChatActivity.this.mentionLayoutManager);
                    }
                    if (z && ChatActivity.this.bottomOverlay.getVisibility() == 0) {
                        z = false;
                    }
                    if (z) {
                        if (ChatActivity.this.mentionListAnimation != null) {
                            ChatActivity.this.mentionListAnimation.cancel();
                            ChatActivity.this.mentionListAnimation = null;
                        }
                        if (ChatActivity.this.mentionContainer.getVisibility() == 0) {
                            ChatActivity.this.mentionContainer.setAlpha(1.0f);
                            return;
                        }
                        if (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.isMediaLayout()) {
                            ChatActivity.this.mentionGridLayoutManager.scrollToPositionWithOffset(0, 10000);
                        } else {
                            ChatActivity.this.mentionLayoutManager.scrollToPositionWithOffset(0, 10000);
                        }
                        if (ChatActivity.this.allowStickersPanel && (!ChatActivity.this.mentionsAdapter.isBotContext() || ChatActivity.this.allowContextBotPanel || ChatActivity.this.allowContextBotPanelSecond)) {
                            if (ChatActivity.this.currentEncryptedChat != null && ChatActivity.this.mentionsAdapter.isBotContext()) {
                                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                                if (!sharedPreferences.getBoolean("secretbot", false)) {
                                    org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                    builder.setMessage(LocaleController.getString("SecretChatContextBotAlert", R.string.SecretChatContextBotAlert));
                                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                    ChatActivity.this.showDialog(builder.create());
                                    sharedPreferences.edit().putBoolean("secretbot", true).commit();
                                }
                            }
                            ChatActivity.this.mentionContainer.setVisibility(0);
                            ChatActivity.this.mentionContainer.setTag(null);
                            ChatActivity.this.mentionListAnimation = new AnimatorSet();
                            ChatActivity.this.mentionListAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(ChatActivity.this.mentionContainer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
                            ChatActivity.this.mentionListAnimation.addListener(new C42441());
                            ChatActivity.this.mentionListAnimation.setDuration(200);
                            ChatActivity.this.mentionListAnimation.start();
                            return;
                        }
                        ChatActivity.this.mentionContainer.setAlpha(1.0f);
                        ChatActivity.this.mentionContainer.setVisibility(4);
                        return;
                    }
                    if (ChatActivity.this.mentionListAnimation != null) {
                        ChatActivity.this.mentionListAnimation.cancel();
                        ChatActivity.this.mentionListAnimation = null;
                    }
                    if (ChatActivity.this.mentionContainer.getVisibility() == 8) {
                        return;
                    }
                    if (ChatActivity.this.allowStickersPanel) {
                        ChatActivity.this.mentionListAnimation = new AnimatorSet();
                        AnimatorSet access$10500 = ChatActivity.this.mentionListAnimation;
                        Animator[] animatorArr = new Animator[1];
                        animatorArr[0] = ObjectAnimator.ofFloat(ChatActivity.this.mentionContainer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                        access$10500.playTogether(animatorArr);
                        ChatActivity.this.mentionListAnimation.addListener(new C42452());
                        ChatActivity.this.mentionListAnimation.setDuration(200);
                        ChatActivity.this.mentionListAnimation.start();
                        return;
                    }
                    ChatActivity.this.mentionContainer.setTag(null);
                    ChatActivity.this.mentionContainer.setVisibility(8);
                }

                public void onContextClick(BotInlineResult botInlineResult) {
                    if (ChatActivity.this.getParentActivity() != null && botInlineResult.content_url != null) {
                        if (botInlineResult.type.equals(MimeTypes.BASE_TYPE_VIDEO) || botInlineResult.type.equals("web_player_video")) {
                            EmbedBottomSheet.show(ChatActivity.this.getParentActivity(), botInlineResult.title != null ? botInlineResult.title : TtmlNode.ANONYMOUS_REGION_ID, botInlineResult.description, botInlineResult.content_url, botInlineResult.content_url, botInlineResult.f10135w, botInlineResult.f10134h);
                        } else {
                            Browser.openUrl(ChatActivity.this.getParentActivity(), botInlineResult.content_url);
                        }
                    }
                }

                public void onContextSearch(boolean z) {
                    if (ChatActivity.this.chatActivityEnterView != null) {
                        ChatActivity.this.chatActivityEnterView.setCaption(ChatActivity.this.mentionsAdapter.getBotCaption());
                        ChatActivity.this.chatActivityEnterView.showContextProgress(z);
                    }
                }
            });
            this.mentionsAdapter = chatActivityAdapter;
            recyclerListView.setAdapter(chatActivityAdapter);
            if (!ChatObject.isChannel(this.currentChat) || (this.currentChat != null && this.currentChat.megagroup)) {
                this.mentionsAdapter.setBotInfo(this.botInfo);
            }
            this.mentionsAdapter.setParentFragment(this);
            this.mentionsAdapter.setChatInfo(this.info);
            this.mentionsAdapter.setNeedUsernames(this.currentChat != null);
            MentionsAdapter mentionsAdapter = this.mentionsAdapter;
            z = this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 46;
            mentionsAdapter.setNeedBotContext(z);
            this.mentionsAdapter.setBotsCount(this.currentChat != null ? this.botsCount : 1);
            recyclerListView = this.mentionListView;
            OnItemClickListener anonymousClass43 = new OnItemClickListener() {
                public void onItemClick(View view, int i) {
                    if (!ChatActivity.this.mentionsAdapter.isBannedInline()) {
                        Object item = ChatActivity.this.mentionsAdapter.getItem(i);
                        int resultStartPosition = ChatActivity.this.mentionsAdapter.getResultStartPosition();
                        int resultLength = ChatActivity.this.mentionsAdapter.getResultLength();
                        if (item instanceof User) {
                            if (ChatActivity.this.searchingForUser && ChatActivity.this.searchContainer.getVisibility() == 0) {
                                ChatActivity.this.searchingUserMessages = (User) item;
                                if (ChatActivity.this.searchingUserMessages != null) {
                                    String str = ChatActivity.this.searchingUserMessages.first_name;
                                    if (TextUtils.isEmpty(str)) {
                                        str = ChatActivity.this.searchingUserMessages.last_name;
                                    }
                                    ChatActivity.this.searchingForUser = false;
                                    String string = LocaleController.getString("SearchFrom", R.string.SearchFrom);
                                    CharSequence spannableString = new SpannableString(string + " " + str);
                                    spannableString.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_actionBarDefaultSubtitle)), string.length() + 1, spannableString.length(), 33);
                                    ChatActivity.this.searchItem.setSearchFieldCaption(spannableString);
                                    ChatActivity.this.mentionsAdapter.searchUsernameOrHashtag(null, 0, null, false);
                                    ChatActivity.this.searchItem.getSearchField().setHint(null);
                                    ChatActivity.this.searchItem.clearSearchText();
                                    MessagesSearchQuery.searchMessagesInChat(TtmlNode.ANONYMOUS_REGION_ID, ChatActivity.this.dialog_id, ChatActivity.this.mergeDialogId, ChatActivity.this.classGuid, 0, ChatActivity.this.searchingUserMessages);
                                    return;
                                }
                                return;
                            }
                            User user = (User) item;
                            if (user == null) {
                                return;
                            }
                            if (user.username != null) {
                                ChatActivity.this.chatActivityEnterView.replaceWithText(resultStartPosition, resultLength, "@" + user.username + " ", false);
                                return;
                            }
                            CharSequence spannableString2 = new SpannableString(UserObject.getFirstName(user) + " ");
                            spannableString2.setSpan(new URLSpanUserMention(TtmlNode.ANONYMOUS_REGION_ID + user.id, true), 0, spannableString2.length(), 33);
                            ChatActivity.this.chatActivityEnterView.replaceWithText(resultStartPosition, resultLength, spannableString2, false);
                        } else if (item instanceof String) {
                            if (ChatActivity.this.mentionsAdapter.isBotCommands()) {
                                SendMessagesHelper.getInstance().sendMessage((String) item, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject, null, false, null, null, null);
                                ChatActivity.this.chatActivityEnterView.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
                                ChatActivity.this.showReplyPanel(false, null, null, null, false);
                                return;
                            }
                            ChatActivity.this.chatActivityEnterView.replaceWithText(resultStartPosition, resultLength, item + " ", false);
                        } else if (item instanceof BotInlineResult) {
                            if (ChatActivity.this.chatActivityEnterView.getFieldText() != null) {
                                BotInlineResult botInlineResult = (BotInlineResult) item;
                                if ((!botInlineResult.type.equals("photo") || (botInlineResult.photo == null && botInlineResult.content_url == null)) && ((!botInlineResult.type.equals("gif") || (botInlineResult.document == null && botInlineResult.content_url == null)) && (!botInlineResult.type.equals(MimeTypes.BASE_TYPE_VIDEO) || botInlineResult.document == null))) {
                                    ChatActivity.this.sendBotInlineResult(botInlineResult);
                                    return;
                                }
                                ArrayList access$702 = ChatActivity.this.botContextResults = new ArrayList(ChatActivity.this.mentionsAdapter.getSearchResultBotContext());
                                PhotoViewer.getInstance().setParentActivity(ChatActivity.this.getParentActivity());
                                PhotoViewer.getInstance().openPhotoForSelect(access$702, ChatActivity.this.mentionsAdapter.getItemPosition(i), 3, ChatActivity.this.botContextProvider, null);
                            }
                        } else if (item instanceof TLRPC$TL_inlineBotSwitchPM) {
                            ChatActivity.this.processInlineBotContextPM((TLRPC$TL_inlineBotSwitchPM) item);
                        } else if (item instanceof EmojiSuggestion) {
                            CharSequence charSequence = ((EmojiSuggestion) item).emoji;
                            ChatActivity.this.chatActivityEnterView.addEmojiToRecent(charSequence);
                            ChatActivity.this.chatActivityEnterView.replaceWithText(resultStartPosition, resultLength, charSequence, true);
                        }
                    }
                }
            };
            this.mentionsOnItemClickListener = anonymousClass43;
            recyclerListView.setOnItemClickListener(anonymousClass43);
            this.mentionListView.setOnItemLongClickListener(new OnItemLongClickListener() {

                /* renamed from: org.telegram.ui.ChatActivity$44$1 */
                class C42461 implements OnClickListener {
                    C42461() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        ChatActivity.this.mentionsAdapter.clearRecentHashtags();
                    }
                }

                public boolean onItemClick(View view, int i) {
                    boolean z = false;
                    if (ChatActivity.this.getParentActivity() == null || !ChatActivity.this.mentionsAdapter.isLongClickEnabled()) {
                        return false;
                    }
                    Object item = ChatActivity.this.mentionsAdapter.getItem(i);
                    if (!(item instanceof String)) {
                        return false;
                    }
                    if (!ChatActivity.this.mentionsAdapter.isBotCommands()) {
                        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("ClearSearch", R.string.ClearSearch));
                        builder.setPositiveButton(LocaleController.getString("ClearButton", R.string.ClearButton).toUpperCase(), new C42461());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ChatActivity.this.showDialog(builder.create());
                        return true;
                    } else if (!URLSpanBotCommand.enabled) {
                        return false;
                    } else {
                        ChatActivity.this.chatActivityEnterView.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
                        ChatActivityEnterView chatActivityEnterView = ChatActivity.this.chatActivityEnterView;
                        String str = (String) item;
                        if (ChatActivity.this.currentChat != null && ChatActivity.this.currentChat.megagroup) {
                            z = true;
                        }
                        chatActivityEnterView.setCommand(null, str, true, z);
                        return true;
                    }
                }
            });
            this.mentionListView.setOnScrollListener(new OnScrollListener() {
                public void onScrollStateChanged(org.telegram.messenger.support.widget.RecyclerView recyclerView, int i) {
                    boolean z = true;
                    ChatActivity chatActivity = ChatActivity.this;
                    if (i != 1) {
                        z = false;
                    }
                    chatActivity.mentionListViewIsScrolling = z;
                }

                public void onScrolled(org.telegram.messenger.support.widget.RecyclerView recyclerView, int i, int i2) {
                    int findLastVisibleItemPosition = (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.isMediaLayout()) ? ChatActivity.this.mentionGridLayoutManager.findLastVisibleItemPosition() : ChatActivity.this.mentionLayoutManager.findLastVisibleItemPosition();
                    if ((findLastVisibleItemPosition == -1 ? 0 : findLastVisibleItemPosition) > 0 && findLastVisibleItemPosition > ChatActivity.this.mentionsAdapter.getItemCount() - 5) {
                        ChatActivity.this.mentionsAdapter.searchForContextBotForNextOffset();
                    }
                    ChatActivity.this.mentionListViewUpdateLayout();
                }
            });
        }
        this.pagedownButtonImage = new ImageView(context);
        this.pagedownButtonImage.setImageResource(R.drawable.pagedown);
        this.pagedownButtonImage.setScaleType(ScaleType.CENTER);
        this.pagedownButtonImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_goDownButtonIcon), Mode.MULTIPLY));
        this.pagedownButtonImage.setPadding(0, AndroidUtilities.dp(2.0f), 0, 0);
        Drawable createCircleDrawable = Theme.createCircleDrawable(AndroidUtilities.dp(42.0f), Theme.getColor(Theme.key_chat_goDownButton));
        Drawable mutate = context.getResources().getDrawable(R.drawable.pagedown_shadow).mutate();
        mutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_goDownButtonShadow), Mode.MULTIPLY));
        Drawable combinedDrawable = new CombinedDrawable(mutate, createCircleDrawable, 0, 0);
        combinedDrawable.setIconSize(AndroidUtilities.dp(42.0f), AndroidUtilities.dp(42.0f));
        this.pagedownButtonImage.setBackgroundDrawable(combinedDrawable);
        this.pagedownButton.addView(this.pagedownButtonImage, LayoutHelper.createFrame(46, 46, 83));
        this.pagedownButtonCounter = new TextView(context);
        this.pagedownButtonCounter.setVisibility(4);
        this.pagedownButtonCounter.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.pagedownButtonCounter.setTextSize(1, 13.0f);
        this.pagedownButtonCounter.setTextColor(Theme.getColor(Theme.key_chat_goDownButtonCounter));
        this.pagedownButtonCounter.setGravity(17);
        this.pagedownButtonCounter.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(11.5f), Theme.getColor(Theme.key_chat_goDownButtonCounterBackground)));
        this.pagedownButtonCounter.setMinWidth(AndroidUtilities.dp(23.0f));
        this.pagedownButtonCounter.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(8.0f), AndroidUtilities.dp(1.0f));
        this.pagedownButton.addView(this.pagedownButtonCounter, LayoutHelper.createFrame(-2, 23, 49));
        this.mentiondownButtonImage = new ImageView(context);
        this.mentiondownButtonImage.setImageResource(R.drawable.mentionbutton);
        this.mentiondownButtonImage.setScaleType(ScaleType.CENTER);
        this.mentiondownButtonImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_goDownButtonIcon), Mode.MULTIPLY));
        this.mentiondownButtonImage.setPadding(0, AndroidUtilities.dp(2.0f), 0, 0);
        createCircleDrawable = Theme.createCircleDrawable(AndroidUtilities.dp(42.0f), Theme.getColor(Theme.key_chat_goDownButton));
        mutate = context.getResources().getDrawable(R.drawable.pagedown_shadow).mutate();
        mutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_goDownButtonShadow), Mode.MULTIPLY));
        combinedDrawable = new CombinedDrawable(mutate, createCircleDrawable, 0, 0);
        combinedDrawable.setIconSize(AndroidUtilities.dp(42.0f), AndroidUtilities.dp(42.0f));
        this.mentiondownButtonImage.setBackgroundDrawable(combinedDrawable);
        this.mentiondownButton.addView(this.mentiondownButtonImage, LayoutHelper.createFrame(46, 46, 83));
        this.mentiondownButtonCounter = new TextView(context);
        this.mentiondownButtonCounter.setVisibility(4);
        this.mentiondownButtonCounter.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.mentiondownButtonCounter.setTextSize(1, 13.0f);
        this.mentiondownButtonCounter.setTextColor(Theme.getColor(Theme.key_chat_goDownButtonCounter));
        this.mentiondownButtonCounter.setGravity(17);
        this.mentiondownButtonCounter.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(11.5f), Theme.getColor(Theme.key_chat_goDownButtonCounterBackground)));
        this.mentiondownButtonCounter.setMinWidth(AndroidUtilities.dp(23.0f));
        this.mentiondownButtonCounter.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(8.0f), AndroidUtilities.dp(1.0f));
        this.mentiondownButton.addView(this.mentiondownButtonCounter, LayoutHelper.createFrame(-2, 23, 49));
        if (!AndroidUtilities.isTablet() || AndroidUtilities.isSmallTablet()) {
            SizeNotifierFrameLayout sizeNotifierFrameLayout = this.contentView;
            View fragmentContextView = new FragmentContextView(context, this, true);
            this.fragmentLocationContextView = fragmentContextView;
            sizeNotifierFrameLayout.addView(fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, BitmapDescriptorFactory.HUE_RED, -36.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            sizeNotifierFrameLayout = this.contentView;
            fragmentContextView = new FragmentContextView(context, this, false);
            this.fragmentContextView = fragmentContextView;
            sizeNotifierFrameLayout.addView(fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, BitmapDescriptorFactory.HUE_RED, -36.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.fragmentContextView.setAdditionalContextView(this.fragmentLocationContextView);
            this.fragmentLocationContextView.setAdditionalContextView(this.fragmentContextView);
        }
        this.contentView.addView(this.actionBar);
        this.overlayView = new View(context);
        this.overlayView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    ChatActivity.this.checkRecordLocked();
                }
                ChatActivity.this.overlayView.getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            }
        });
        this.contentView.addView(this.overlayView, LayoutHelper.createFrame(-1, -1, 51));
        this.overlayView.setVisibility(8);
        this.instantCameraView = new InstantCameraView(context, this);
        this.contentView.addView(this.instantCameraView, LayoutHelper.createFrame(-1, -1, 51));
        this.chatActivityEnterView = new ChatActivityEnterView(getParentActivity(), this.contentView, this, true, this.dialog_id);
        this.chatActivityEnterView.setDialogId(this.dialog_id);
        this.chatActivityEnterView.setId(1000);
        this.chatActivityEnterView.setBotsCount(this.botsCount, this.hasBotsCommands);
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        z = this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 23;
        boolean z2 = this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 46;
        chatActivityEnterView.setAllowStickersAndGifs(z, z2);
        this.contentView.addView(this.chatActivityEnterView, this.contentView.getChildCount() - 1, LayoutHelper.createFrame(-1, -2, 83));
        this.chatActivityEnterView.setDelegate(new ChatActivityEnterViewDelegate() {
            public void didPressedAttachButton() {
                ChatActivity.this.openAttachMenu();
            }

            public void needChangeVideoPreviewState(int i, float f) {
                if (ChatActivity.this.instantCameraView != null) {
                    ChatActivity.this.instantCameraView.changeVideoPreviewState(i, f);
                }
            }

            public void needSendTyping() {
                MessagesController.getInstance().sendTyping(ChatActivity.this.dialog_id, 0, ChatActivity.this.classGuid);
            }

            public void needShowMediaBanHint() {
                ChatActivity.this.showMediaBannedHint();
            }

            public void needStartRecordAudio(int i) {
                ChatActivity.this.overlayView.setVisibility(i == 0 ? 8 : 0);
            }

            public void needStartRecordVideo(int i) {
                if (ChatActivity.this.instantCameraView == null) {
                    return;
                }
                if (i == 0) {
                    ChatActivity.this.instantCameraView.showCamera();
                } else if (i == 1 || i == 3 || i == 4) {
                    ChatActivity.this.instantCameraView.send(i);
                } else if (i == 2) {
                    ChatActivity.this.instantCameraView.cancel();
                }
            }

            public void onAttachButtonHidden() {
                if (!ChatActivity.this.actionBar.isSearchFieldVisible()) {
                    if (ChatActivity.this.attachItem != null) {
                        ChatActivity.this.attachItem.setVisibility(0);
                    }
                    if (ChatActivity.this.headerItem != null) {
                        ChatActivity.this.headerItem.setVisibility(8);
                    }
                }
            }

            public void onAttachButtonShow() {
                if (!ChatActivity.this.actionBar.isSearchFieldVisible()) {
                    if (ChatActivity.this.attachItem != null) {
                        ChatActivity.this.attachItem.setVisibility(8);
                    }
                    if (ChatActivity.this.headerItem != null) {
                        ChatActivity.this.headerItem.setVisibility(0);
                    }
                }
            }

            public void onMessageEditEnd(boolean z) {
                if (!z) {
                    MentionsAdapter access$3900 = ChatActivity.this.mentionsAdapter;
                    boolean z2 = ChatActivity.this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(ChatActivity.this.currentEncryptedChat.layer) >= 46;
                    access$3900.setNeedBotContext(z2);
                    ChatActivity.this.chatListView.setOnItemLongClickListener(ChatActivity.this.onItemLongClickListener);
                    ChatActivity.this.chatListView.setOnItemClickListener(ChatActivity.this.onItemClickListener);
                    ChatActivity.this.chatListView.setClickable(true);
                    ChatActivity.this.chatListView.setLongClickable(true);
                    ChatActivity.this.mentionsAdapter.setAllowNewMentions(true);
                    ChatActivity.this.actionModeTitleContainer.setVisibility(8);
                    ChatActivity.this.selectedMessagesCountTextView.setVisibility(0);
                    ChatActivityEnterView chatActivityEnterView = ChatActivity.this.chatActivityEnterView;
                    z2 = ChatActivity.this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(ChatActivity.this.currentEncryptedChat.layer) >= 23;
                    boolean z3 = ChatActivity.this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(ChatActivity.this.currentEncryptedChat.layer) >= 46;
                    chatActivityEnterView.setAllowStickersAndGifs(z2, z3);
                    if (ChatActivity.this.editingMessageObjectReqId != 0) {
                        ConnectionsManager.getInstance().cancelRequest(ChatActivity.this.editingMessageObjectReqId, true);
                        ChatActivity.this.editingMessageObjectReqId = 0;
                    }
                    ChatActivity.this.actionBar.hideActionMode();
                    ChatActivity.this.updatePinnedMessageView(true);
                    ChatActivity.this.updateBottomOverlay();
                    ChatActivity.this.updateVisibleRows();
                }
            }

            public void onMessageSend(CharSequence charSequence) {
                ChatActivity.this.moveScrollToLastMessage();
                ChatActivity.this.showReplyPanel(false, null, null, null, false);
                if (ChatActivity.this.mentionsAdapter != null) {
                    ChatActivity.this.mentionsAdapter.addHashtagsFromMessage(charSequence);
                }
            }

            public void onPreAudioVideoRecord() {
                ChatActivity.this.showVoiceHint(true, false);
            }

            public void onStickersTab(boolean z) {
                if (ChatActivity.this.emojiButtonRed != null) {
                    ChatActivity.this.emojiButtonRed.setVisibility(8);
                }
                ChatActivity.this.allowContextBotPanelSecond = !z;
                ChatActivity.this.checkContextBotPanel();
            }

            public void onSwitchRecordMode(boolean z) {
                ChatActivity.this.showVoiceHint(false, z);
            }

            public void onTextChanged(final CharSequence charSequence, boolean z) {
                if (ChatActivity.this.startReplyOnTextChange && charSequence.length() > 0) {
                    ChatActivity.this.actionBar.getActionBarMenuOnItemClick().onItemClick(19);
                    ChatActivity.this.startReplyOnTextChange = false;
                }
                MediaController instance = MediaController.getInstance();
                boolean z2 = !TextUtils.isEmpty(charSequence) || ChatActivity.this.chatActivityEnterView.isEditingMessage();
                instance.setInputFieldHasText(z2);
                if (!(ChatActivity.this.stickersAdapter == null || ChatActivity.this.chatActivityEnterView.isEditingMessage() || !ChatObject.canSendStickers(ChatActivity.this.currentChat))) {
                    ChatActivity.this.stickersAdapter.loadStikersForEmoji(charSequence);
                }
                if (ChatActivity.this.mentionsAdapter != null) {
                    ChatActivity.this.mentionsAdapter.searchUsernameOrHashtag(charSequence.toString(), ChatActivity.this.chatActivityEnterView.getCursorPosition(), ChatActivity.this.messages, false);
                }
                if (ChatActivity.this.waitingForCharaterEnterRunnable != null) {
                    AndroidUtilities.cancelRunOnUIThread(ChatActivity.this.waitingForCharaterEnterRunnable);
                    ChatActivity.this.waitingForCharaterEnterRunnable = null;
                }
                if (!ChatObject.canSendEmbed(ChatActivity.this.currentChat) || !ChatActivity.this.chatActivityEnterView.isMessageWebPageSearchEnabled()) {
                    return;
                }
                if (!ChatActivity.this.chatActivityEnterView.isEditingMessage() || !ChatActivity.this.chatActivityEnterView.isEditingCaption()) {
                    if (z) {
                        ChatActivity.this.searchLinks(charSequence, true);
                        return;
                    }
                    ChatActivity.this.waitingForCharaterEnterRunnable = new Runnable() {
                        public void run() {
                            if (this == ChatActivity.this.waitingForCharaterEnterRunnable) {
                                ChatActivity.this.searchLinks(charSequence, false);
                                ChatActivity.this.waitingForCharaterEnterRunnable = null;
                            }
                        }
                    };
                    AndroidUtilities.runOnUIThread(ChatActivity.this.waitingForCharaterEnterRunnable, AndroidUtilities.WEB_URL == null ? 3000 : 1000);
                }
            }

            public void onWindowSizeChanged(int i) {
                boolean z = true;
                if (i < AndroidUtilities.dp(72.0f) + ActionBar.getCurrentActionBarHeight()) {
                    ChatActivity.this.allowStickersPanel = false;
                    if (ChatActivity.this.stickersPanel.getVisibility() == 0) {
                        ChatActivity.this.stickersPanel.setVisibility(4);
                    }
                    if (ChatActivity.this.mentionContainer != null && ChatActivity.this.mentionContainer.getVisibility() == 0) {
                        ChatActivity.this.mentionContainer.setVisibility(4);
                    }
                } else {
                    ChatActivity.this.allowStickersPanel = true;
                    if (ChatActivity.this.stickersPanel.getVisibility() == 4) {
                        ChatActivity.this.stickersPanel.setVisibility(0);
                    }
                    if (ChatActivity.this.mentionContainer != null && ChatActivity.this.mentionContainer.getVisibility() == 4 && (!ChatActivity.this.mentionsAdapter.isBotContext() || ChatActivity.this.allowContextBotPanel || ChatActivity.this.allowContextBotPanelSecond)) {
                        ChatActivity.this.mentionContainer.setVisibility(0);
                        ChatActivity.this.mentionContainer.setTag(null);
                    }
                }
                ChatActivity chatActivity = ChatActivity.this;
                if (ChatActivity.this.chatActivityEnterView.isPopupShowing()) {
                    z = false;
                }
                chatActivity.allowContextBotPanel = z;
                ChatActivity.this.checkContextBotPanel();
            }
        });
        View anonymousClass48 = new FrameLayout(context) {
            public boolean hasOverlappingRendering() {
                return false;
            }

            public void setTranslationY(float f) {
                super.setTranslationY(f);
                if (ChatActivity.this.chatActivityEnterView != null) {
                    ChatActivity.this.chatActivityEnterView.invalidate();
                }
                if (getVisibility() != 8) {
                    int i = getLayoutParams().height;
                    if (ChatActivity.this.chatListView != null) {
                        ChatActivity.this.chatListView.setTranslationY(f);
                    }
                    if (ChatActivity.this.progressView != null) {
                        ChatActivity.this.progressView.setTranslationY(f);
                    }
                    if (ChatActivity.this.mentionContainer != null) {
                        ChatActivity.this.mentionContainer.setTranslationY(f);
                    }
                    if (ChatActivity.this.pagedownButton != null) {
                        ChatActivity.this.pagedownButton.setTranslationY(f);
                    }
                    if (ChatActivity.this.mentiondownButton != null) {
                        FrameLayout access$6500 = ChatActivity.this.mentiondownButton;
                        if (ChatActivity.this.pagedownButton.getVisibility() == 0) {
                            f -= (float) AndroidUtilities.dp(72.0f);
                        }
                        access$6500.setTranslationY(f);
                    }
                }
            }

            public void setVisibility(int i) {
                float f = BitmapDescriptorFactory.HUE_RED;
                super.setVisibility(i);
                if (i == 8) {
                    FrameLayout access$6400;
                    if (ChatActivity.this.chatListView != null) {
                        ChatActivity.this.chatListView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    }
                    if (ChatActivity.this.progressView != null) {
                        ChatActivity.this.progressView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    }
                    if (ChatActivity.this.mentionContainer != null) {
                        ChatActivity.this.mentionContainer.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    }
                    if (ChatActivity.this.pagedownButton != null) {
                        access$6400 = ChatActivity.this.pagedownButton;
                        if (ChatActivity.this.pagedownButton.getTag() == null) {
                            f = (float) AndroidUtilities.dp(100.0f);
                        }
                        access$6400.setTranslationY(f);
                    }
                    if (ChatActivity.this.mentiondownButton != null) {
                        access$6400 = ChatActivity.this.mentiondownButton;
                        if (ChatActivity.this.mentiondownButton.getTag() == null) {
                            f = (float) AndroidUtilities.dp(100.0f);
                        } else {
                            f = (float) (ChatActivity.this.pagedownButton.getVisibility() == 0 ? -AndroidUtilities.dp(72.0f) : 0);
                        }
                        access$6400.setTranslationY(f);
                    }
                }
            }
        };
        if (this.isAdvancedForward) {
            final MessageObject messageObject = getMessageObject();
            try {
                getMessageObject().backupCaptionAndText();
            } catch (Exception e4) {
            }
            if (messageObject != null) {
                if (TextUtils.isEmpty(messageObject.caption)) {
                    this.chatActivityEnterView.setFieldText(messageObject.messageText);
                } else {
                    this.chatActivityEnterView.setFieldText(messageObject.caption);
                    messageObject.caption = TtmlNode.ANONYMOUS_REGION_ID;
                    messageObject.messageText = TtmlNode.ANONYMOUS_REGION_ID;
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        r0 = new Object[13];
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(messageObject);
                        r0[2] = arrayList;
                        r0[3] = Boolean.valueOf(true);
                        r0[4] = Integer.valueOf(0);
                        r0[5] = Integer.valueOf(0);
                        r0[6] = Integer.valueOf(0);
                        r0[7] = Integer.valueOf(0);
                        r0[8] = Integer.valueOf(2);
                        r0[9] = Boolean.valueOf(false);
                        r0[10] = Integer.valueOf(ChatActivity.this.classGuid);
                        r0[11] = Integer.valueOf(0);
                        r0[12] = Integer.valueOf(0);
                        ChatActivity.this.didReceivedNotification(NotificationCenter.messagesDidLoaded, r0);
                    }
                }, 1000);
            }
            this.chatActivityEnterView.getSendButton().setOnClickListener(new View.OnClickListener() {

                /* renamed from: org.telegram.ui.ChatActivity$50$1 */
                class C42491 implements OnDismissListener {
                    C42491() {
                    }

                    public void onDismiss(DialogInterface dialogInterface) {
                        ChatActivity.this.showingDialog = false;
                    }
                }

                /* renamed from: org.telegram.ui.ChatActivity$50$2 */
                class C42502 implements OnCancelListener {
                    C42502() {
                    }

                    public void onCancel(DialogInterface dialogInterface) {
                        ChatActivity.this.showingDialog = false;
                    }
                }

                public void onClick(View view) {
                    MessageObject messageObject = messageObject;
                    Log.d("LEE", "mediaExists:" + messageObject.mediaExists);
                    if (messageObject.mediaExists || messageObject.type == 3) {
                        messageObject.caption = ChatActivity.this.chatActivityEnterView.getFieldText();
                    } else {
                        messageObject.messageText = ChatActivity.this.chatActivityEnterView.getFieldText();
                        messageObject.messageOwner.message = ChatActivity.this.chatActivityEnterView.getFieldText().toString();
                    }
                    Log.d("LEE", "mediaExists:" + messageObject.messageText + "   " + messageObject.caption);
                    ChatActivity.this.showingDialog = true;
                    Dialog shareAlert = new ShareAlert(ChatActivity.this.getParentActivity(), messageObject, TtmlNode.ANONYMOUS_REGION_ID, true, TtmlNode.ANONYMOUS_REGION_ID, true, false);
                    ChatActivity.this.showDialog(shareAlert);
                    shareAlert.setOnDismissListener(new C42491());
                    shareAlert.setOnCancelListener(new C42502());
                }
            });
        }
        this.chatActivityEnterView.addTopView(anonymousClass48, 48);
        anonymousClass48.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ChatActivity.this.replyingMessageObject != null) {
                    ChatActivity.this.scrollToMessageId(ChatActivity.this.replyingMessageObject.getId(), 0, true, 0, false);
                }
            }
        });
        this.replyLineView = new View(context);
        this.replyLineView.setBackgroundColor(Theme.getColor(Theme.key_chat_replyPanelLine));
        anonymousClass48.addView(this.replyLineView, LayoutHelper.createFrame(-1, 1, 83));
        this.replyIconImageView = new ImageView(context);
        this.replyIconImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_replyPanelIcons), Mode.MULTIPLY));
        this.replyIconImageView.setScaleType(ScaleType.CENTER);
        anonymousClass48.addView(this.replyIconImageView, LayoutHelper.createFrame(52, 46, 51));
        this.replyCloseImageView = new ImageView(context);
        this.replyCloseImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_replyPanelClose), Mode.MULTIPLY));
        this.replyCloseImageView.setImageResource(R.drawable.msg_panel_clear);
        this.replyCloseImageView.setScaleType(ScaleType.CENTER);
        anonymousClass48.addView(this.replyCloseImageView, LayoutHelper.createFrame(52, 46.0f, 53, BitmapDescriptorFactory.HUE_RED, 0.5f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.replyCloseImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ChatActivity.this.forwardingMessages != null) {
                    ChatActivity.this.forwardingMessages.clear();
                }
                ChatActivity.this.showReplyPanel(false, null, null, ChatActivity.this.foundWebPage, true);
            }
        });
        this.replyNameTextView = new SimpleTextView(context);
        this.replyNameTextView.setTextSize(14);
        this.replyNameTextView.setTextColor(Theme.getColor(Theme.key_chat_replyPanelName));
        this.replyNameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        anonymousClass48.addView(this.replyNameTextView, LayoutHelper.createFrame(-1, 18.0f, 51, 52.0f, 6.0f, 52.0f, BitmapDescriptorFactory.HUE_RED));
        this.replyObjectTextView = new SimpleTextView(context);
        this.replyObjectTextView.setTextSize(14);
        this.replyObjectTextView.setTextColor(Theme.getColor(Theme.key_chat_replyPanelMessage));
        anonymousClass48.addView(this.replyObjectTextView, LayoutHelper.createFrame(-1, 18.0f, 51, 52.0f, 24.0f, 52.0f, BitmapDescriptorFactory.HUE_RED));
        this.replyImageView = new BackupImageView(context);
        anonymousClass48.addView(this.replyImageView, LayoutHelper.createFrame(34, 34.0f, 51, 52.0f, 6.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.stickersPanel = new FrameLayout(context);
        this.stickersPanel.setVisibility(8);
        this.contentView.addView(this.stickersPanel, LayoutHelper.createFrame(-2, 81.5f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 38.0f));
        this.stickersListView = new RecyclerListView(context) {
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return super.onInterceptTouchEvent(motionEvent) || StickerPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, ChatActivity.this.stickersListView, 0, null);
            }
        };
        this.stickersListView.setTag(Integer.valueOf(3));
        this.stickersListView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return StickerPreviewViewer.getInstance().onTouch(motionEvent, ChatActivity.this.stickersListView, 0, ChatActivity.this.stickersOnItemClickListener, null);
            }
        });
        this.stickersListView.setDisallowInterceptTouchEvents(true);
        LayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(0);
        this.stickersListView.setLayoutManager(linearLayoutManager);
        this.stickersListView.setClipToPadding(false);
        this.stickersListView.setOverScrollMode(2);
        this.stickersPanel.addView(this.stickersListView, LayoutHelper.createFrame(-1, 78.0f));
        initStickers();
        this.stickersPanelArrow = new ImageView(context);
        this.stickersPanelArrow.setImageResource(R.drawable.stickers_back_arrow);
        this.stickersPanelArrow.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_stickersHintPanel), Mode.MULTIPLY));
        this.stickersPanel.addView(this.stickersPanelArrow, LayoutHelper.createFrame(-2, -2.0f, 83, 53.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.searchContainer = new FrameLayout(context) {
            public void onDraw(Canvas canvas) {
                int intrinsicHeight = Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                Theme.chat_composeShadowDrawable.setBounds(0, 0, getMeasuredWidth(), intrinsicHeight);
                Theme.chat_composeShadowDrawable.draw(canvas);
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) intrinsicHeight, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
            }
        };
        this.searchContainer.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.searchContainer.setWillNotDraw(false);
        this.searchContainer.setVisibility(4);
        this.searchContainer.setFocusable(true);
        this.searchContainer.setFocusableInTouchMode(true);
        this.searchContainer.setClickable(true);
        this.searchContainer.setPadding(0, AndroidUtilities.dp(3.0f), 0, 0);
        this.searchUpButton = new ImageView(context);
        this.searchUpButton.setScaleType(ScaleType.CENTER);
        this.searchUpButton.setImageResource(R.drawable.search_up);
        this.searchUpButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_searchPanelIcons), Mode.MULTIPLY));
        this.searchContainer.addView(this.searchUpButton, LayoutHelper.createFrame(48, 48.0f, 53, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED));
        this.searchUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MessagesSearchQuery.searchMessagesInChat(null, ChatActivity.this.dialog_id, ChatActivity.this.mergeDialogId, ChatActivity.this.classGuid, 1, ChatActivity.this.searchingUserMessages);
            }
        });
        this.searchDownButton = new ImageView(context);
        this.searchDownButton.setScaleType(ScaleType.CENTER);
        this.searchDownButton.setImageResource(R.drawable.search_down);
        this.searchDownButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_searchPanelIcons), Mode.MULTIPLY));
        this.searchContainer.addView(this.searchDownButton, LayoutHelper.createFrame(48, 48.0f, 53, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.searchDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MessagesSearchQuery.searchMessagesInChat(null, ChatActivity.this.dialog_id, ChatActivity.this.mergeDialogId, ChatActivity.this.classGuid, 2, ChatActivity.this.searchingUserMessages);
            }
        });
        if (this.currentChat != null && (!ChatObject.isChannel(this.currentChat) || this.currentChat.megagroup)) {
            this.searchUserButton = new ImageView(context);
            this.searchUserButton.setScaleType(ScaleType.CENTER);
            this.searchUserButton.setImageResource(R.drawable.usersearch);
            this.searchUserButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_searchPanelIcons), Mode.MULTIPLY));
            this.searchContainer.addView(this.searchUserButton, LayoutHelper.createFrame(48, 48.0f, 51, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.searchUserButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ChatActivity.this.mentionLayoutManager.setReverseLayout(true);
                    ChatActivity.this.mentionsAdapter.setSearchingMentions(true);
                    ChatActivity.this.searchCalendarButton.setVisibility(8);
                    ChatActivity.this.searchUserButton.setVisibility(8);
                    ChatActivity.this.searchingForUser = true;
                    ChatActivity.this.searchingUserMessages = null;
                    ChatActivity.this.searchItem.getSearchField().setHint(LocaleController.getString("SearchMembers", R.string.SearchMembers));
                    ChatActivity.this.searchItem.setSearchFieldCaption(LocaleController.getString("SearchFrom", R.string.SearchFrom));
                    AndroidUtilities.showKeyboard(ChatActivity.this.searchItem.getSearchField());
                    ChatActivity.this.searchItem.clearSearchText();
                }
            });
        }
        this.searchCalendarButton = new ImageView(context);
        this.searchCalendarButton.setScaleType(ScaleType.CENTER);
        this.searchCalendarButton.setImageResource(R.drawable.search_calendar);
        this.searchCalendarButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_searchPanelIcons), Mode.MULTIPLY));
        this.searchContainer.addView(this.searchCalendarButton, LayoutHelper.createFrame(48, 48, 51));
        this.searchCalendarButton.setOnClickListener(new View.OnClickListener() {

            /* renamed from: org.telegram.ui.ChatActivity$60$1 */
            class C42521 implements OnDateSetListener {
                C42521() {
                }

                public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                    Calendar instance = Calendar.getInstance();
                    instance.clear();
                    instance.set(i, i2, i3);
                    int time = (int) (instance.getTime().getTime() / 1000);
                    ChatActivity.this.clearChatData();
                    ChatActivity.this.waitingForLoad.add(Integer.valueOf(ChatActivity.this.lastLoadIndex));
                    MessagesController.getInstance().loadMessages(ChatActivity.this.dialog_id, 30, 0, time, true, 0, ChatActivity.this.classGuid, 4, 0, ChatObject.isChannel(ChatActivity.this.currentChat), ChatActivity.this.lastLoadIndex = ChatActivity.this.lastLoadIndex + 1);
                }
            }

            /* renamed from: org.telegram.ui.ChatActivity$60$2 */
            class C42532 implements OnClickListener {
                C42532() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }

            public void onClick(View view) {
                if (ChatActivity.this.getParentActivity() != null) {
                    AndroidUtilities.hideKeyboard(ChatActivity.this.searchItem.getSearchField());
                    Calendar instance = Calendar.getInstance();
                    try {
                        Dialog datePickerDialog = new DatePickerDialog(ChatActivity.this.getParentActivity(), new C42521(), instance.get(1), instance.get(2), instance.get(5));
                        final DatePicker datePicker = datePickerDialog.getDatePicker();
                        datePicker.setMinDate(1375315200000L);
                        datePicker.setMaxDate(System.currentTimeMillis());
                        datePickerDialog.setButton(-1, LocaleController.getString("JumpToDate", R.string.JumpToDate), datePickerDialog);
                        datePickerDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C42532());
                        if (VERSION.SDK_INT >= 21) {
                            datePickerDialog.setOnShowListener(new OnShowListener() {
                                public void onShow(DialogInterface dialogInterface) {
                                    int childCount = datePicker.getChildCount();
                                    for (int i = 0; i < childCount; i++) {
                                        View childAt = datePicker.getChildAt(i);
                                        ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                                        layoutParams.width = -1;
                                        childAt.setLayoutParams(layoutParams);
                                    }
                                }
                            });
                        }
                        ChatActivity.this.showDialog(datePickerDialog);
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
            }
        });
        this.searchCountText = new SimpleTextView(context);
        this.searchCountText.setTextColor(Theme.getColor(Theme.key_chat_searchPanelText));
        this.searchCountText.setTextSize(15);
        this.searchCountText.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.searchCountText.setGravity(5);
        this.searchContainer.addView(this.searchCountText, LayoutHelper.createFrame(-2, -2.0f, 21, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 108.0f, BitmapDescriptorFactory.HUE_RED));
        this.bottomOverlay = new FrameLayout(context) {
            public void onDraw(Canvas canvas) {
                int intrinsicHeight = Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                Theme.chat_composeShadowDrawable.setBounds(0, 0, getMeasuredWidth(), intrinsicHeight);
                Theme.chat_composeShadowDrawable.draw(canvas);
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) intrinsicHeight, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
            }
        };
        this.bottomOverlay.setWillNotDraw(false);
        this.bottomOverlay.setVisibility(4);
        this.bottomOverlay.setFocusable(true);
        this.bottomOverlay.setFocusableInTouchMode(true);
        this.bottomOverlay.setClickable(true);
        this.bottomOverlay.setPadding(0, AndroidUtilities.dp(2.0f), 0, 0);
        this.contentView.addView(this.bottomOverlay, LayoutHelper.createFrame(-1, 51, 80));
        this.bottomOverlayText = new TextView(context);
        this.bottomOverlayText.setTextSize(1, 14.0f);
        this.bottomOverlayText.setGravity(17);
        this.bottomOverlayText.setMaxLines(2);
        this.bottomOverlayText.setEllipsize(TruncateAt.END);
        this.bottomOverlayText.setLineSpacing((float) AndroidUtilities.dp(2.0f), 1.0f);
        this.bottomOverlayText.setTextColor(Theme.getColor(Theme.key_chat_secretChatStatusText));
        this.bottomOverlay.addView(this.bottomOverlayText, LayoutHelper.createFrame(-2, -2.0f, 17, 14.0f, BitmapDescriptorFactory.HUE_RED, 14.0f, BitmapDescriptorFactory.HUE_RED));
        this.bottomOverlayChat = new FrameLayout(context) {
            public void onDraw(Canvas canvas) {
                int intrinsicHeight = Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                Theme.chat_composeShadowDrawable.setBounds(0, 0, getMeasuredWidth(), intrinsicHeight);
                Theme.chat_composeShadowDrawable.draw(canvas);
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) intrinsicHeight, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
            }
        };
        this.bottomOverlayChat.setWillNotDraw(false);
        this.bottomOverlayChat.setPadding(0, AndroidUtilities.dp(3.0f), 0, 0);
        this.bottomOverlayChat.setVisibility(4);
        this.contentView.addView(this.bottomOverlayChat, LayoutHelper.createFrame(-1, 51, 80));
        this.bottomOverlayChat.setOnClickListener(new View.OnClickListener() {

            /* renamed from: org.telegram.ui.ChatActivity$63$1 */
            class C42551 implements OnClickListener {
                C42551() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    MessagesController.getInstance().unblockUser(ChatActivity.this.currentUser.id);
                }
            }

            /* renamed from: org.telegram.ui.ChatActivity$63$2 */
            class C42562 implements OnClickListener {
                C42562() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    MessagesController.getInstance().deleteDialog(ChatActivity.this.dialog_id, 0);
                    ChatActivity.this.finishFragment();
                }
            }

            public void onClick(View view) {
                if (ChatActivity.this.isDownloadManager) {
                    Log.d("LEE", "botton Click");
                    if (ChatActivity.this.downloadManagerLoading.getVisibility() == 8) {
                        Intent intent = new Intent(ApplicationLoader.applicationContext, DownloadManagerService.class);
                        intent.putExtra("EXTRA_IS_FORCE", true);
                        ApplicationLoader.applicationContext.startService(intent);
                        ChatActivity.this.downloadManagerLoading.setVisibility(0);
                        return;
                    }
                    ApplicationLoader.applicationContext.stopService(new Intent(ApplicationLoader.applicationContext, DownloadManagerService.class));
                } else if (ChatActivity.this.getParentActivity() != null) {
                    org.telegram.ui.ActionBar.AlertDialog.Builder builder;
                    if (ChatActivity.this.currentUser == null || !ChatActivity.this.userBlocked) {
                        if (ChatActivity.this.currentUser != null && ChatActivity.this.currentUser.bot && ChatActivity.this.botUser != null) {
                            if (ChatActivity.this.botUser.length() != 0) {
                                MessagesController.getInstance().sendBotStart(ChatActivity.this.currentUser, ChatActivity.this.botUser);
                            } else {
                                SendMessagesHelper.getInstance().sendMessage("/start", ChatActivity.this.dialog_id, null, null, false, null, null, null);
                            }
                            ChatActivity.this.botUser = null;
                            ChatActivity.this.updateBottomOverlay();
                            builder = null;
                        } else if (!ChatObject.isChannel(ChatActivity.this.currentChat) || (ChatActivity.this.currentChat instanceof TL_channelForbidden)) {
                            builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                            builder.setMessage(LocaleController.getString("AreYouSureDeleteThisChat", R.string.AreYouSureDeleteThisChat));
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C42562());
                        } else if (ChatObject.isNotInChat(ChatActivity.this.currentChat)) {
                            MessagesController.getInstance().addUserToChat(ChatActivity.this.currentChat.id, UserConfig.getCurrentUser(), null, 0, null, ChatActivity.this);
                            builder = null;
                        } else {
                            ChatActivity.this.toggleMute(true);
                            builder = null;
                        }
                    } else if (ChatActivity.this.currentUser.bot) {
                        String access$14000 = ChatActivity.this.botUser;
                        ChatActivity.this.botUser = null;
                        MessagesController.getInstance().unblockUser(ChatActivity.this.currentUser.id);
                        if (access$14000 == null || access$14000.length() == 0) {
                            SendMessagesHelper.getInstance().sendMessage("/start", ChatActivity.this.dialog_id, null, null, false, null, null, null);
                        } else {
                            MessagesController.getInstance().sendBotStart(ChatActivity.this.currentUser, access$14000);
                        }
                        builder = null;
                    } else {
                        builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                        builder.setMessage(LocaleController.getString("AreYouSureUnblockContact", R.string.AreYouSureUnblockContact));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C42551());
                    }
                    if (builder != null) {
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ChatActivity.this.showDialog(builder.create());
                    }
                }
            }
        });
        this.bottomOverlayChatText = new TextView(context);
        this.bottomOverlayChatText.setTextSize(1, 15.0f);
        this.bottomOverlayChatText.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.bottomOverlayChatText.setTextColor(Theme.getColor(Theme.key_chat_fieldOverlayText));
        this.bottomOverlayChat.addView(this.bottomOverlayChatText, LayoutHelper.createFrame(-2, -2, 17));
        this.contentView.addView(this.searchContainer, LayoutHelper.createFrame(-1, 51, 80));
        this.chatAdapter.updateRows();
        if (this.loading && this.messages.isEmpty()) {
            this.progressView.setVisibility(this.chatAdapter.botInfoRow == -1 ? 0 : 4);
            this.chatListView.setEmptyView(null);
            if (this.isDownloadManager) {
                this.progressView.setVisibility(4);
            }
        } else {
            this.progressView.setVisibility(4);
            this.chatListView.setEmptyView(this.emptyViewContainer);
        }
        this.chatActivityEnterView.setButtons(this.userBlocked ? null : this.botButtons);
        updateContactStatus();
        updateBottomOverlay();
        updateSecretStatus();
        updateSpamView();
        updatePinnedMessageView(true);
        try {
            if (this.currentEncryptedChat != null && VERSION.SDK_INT >= 23 && (UserConfig.passcodeHash.length() == 0 || UserConfig.allowScreenCapture)) {
                getParentActivity().getWindow().setFlags(MessagesController.UPDATE_MASK_CHANNEL, MessagesController.UPDATE_MASK_CHANNEL);
            }
        } catch (Throwable th) {
            FileLog.e(th);
        }
        if (fieldText != null) {
            this.chatActivityEnterView.setFieldText(fieldText);
        }
        fixLayoutInternal();
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int indexOf;
        ArrayList arrayList;
        int i2;
        MessageObject messageObject;
        Object obj;
        MessageObject messageObject2;
        int i3;
        int i4;
        boolean z;
        boolean z2;
        int i5;
        int i6;
        int i7;
        HashMap hashMap;
        int i8;
        int i9;
        HashMap hashMap2;
        ArrayList arrayList2;
        MessageObject messageObject3;
        int i10;
        GroupedMessages groupedMessages;
        MessageObject messageObject4;
        GroupedMessages groupedMessages2;
        HashMap hashMap3;
        int i11;
        Message tLRPC$TL_message;
        GroupedMessages groupedMessages3;
        View findViewByPosition;
        boolean z3;
        int i12;
        Object obj2;
        if (i == NotificationCenter.messagesDidLoaded) {
            Log.d("LEE", "Debug1946 didReceivedNotification()");
            if (this.channelIsFilter) {
                this.pinnedMessageView.setVisibility(8);
                this.bottomOverlayChat.setVisibility(8);
                return;
            }
            this.showFilterDialog = true;
            Log.d("LEE", "Debug1946 didReceivedNotification() after  check filtering");
            if (((Integer) objArr[10]).intValue() == this.classGuid) {
                boolean z4;
                Object obj3;
                boolean[] zArr;
                boolean[] zArr2;
                HashMap hashMap4;
                boolean z5;
                Message tLRPC$TL_message2;
                ArrayList arrayList3;
                MessageObject messageObject5;
                TextView textView;
                Object[] objArr2;
                final int i13;
                if (!this.openAnimationEnded) {
                    NotificationCenter.getInstance().setAllowedNotificationsDutingAnimation(new int[]{NotificationCenter.chatInfoDidLoaded, NotificationCenter.dialogsNeedReload, NotificationCenter.closeChats, NotificationCenter.botKeyboardDidLoaded});
                }
                indexOf = this.waitingForLoad.indexOf(Integer.valueOf(((Integer) objArr[11]).intValue()));
                if (!this.isAdvancedForward) {
                    if (indexOf != -1) {
                        this.waitingForLoad.remove(indexOf);
                    } else {
                        return;
                    }
                }
                int clientUserId = UserConfig.getClientUserId();
                arrayList = (ArrayList) objArr[2];
                if (this.waitingForReplyMessageLoad) {
                    if (!this.createUnreadMessageAfterIdLoading) {
                        for (i2 = 0; i2 < arrayList.size(); i2++) {
                            messageObject = (MessageObject) arrayList.get(i2);
                            if (messageObject.getId() == this.startLoadFromMessageId) {
                                obj = 1;
                                break;
                            }
                            try {
                                if (this.goToFirstMsg && arrayList.size() >= 9) {
                                    this.startLoadFromMessageId = ((MessageObject) arrayList.get(arrayList.size() - 1)).getId();
                                    this.goToFirstMsg = false;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (i2 + 1 < arrayList.size()) {
                                messageObject2 = (MessageObject) arrayList.get(i2 + 1);
                                if (messageObject.getId() >= this.startLoadFromMessageId && messageObject2.getId() < this.startLoadFromMessageId) {
                                    this.startLoadFromMessageId = messageObject.getId();
                                    obj = 1;
                                    break;
                                }
                            }
                        }
                        obj = null;
                        if (obj == null) {
                            this.startLoadFromMessageId = 0;
                            return;
                        }
                    }
                    i3 = this.startLoadFromMessageId;
                    boolean z6 = this.needSelectFromMessageId;
                    i4 = this.createUnreadMessageAfterId;
                    z = this.createUnreadMessageAfterIdLoading;
                    clearChatData();
                    this.createUnreadMessageAfterId = i4;
                    this.startLoadFromMessageId = i3;
                    this.needSelectFromMessageId = z6;
                    z2 = z;
                } else {
                    z2 = false;
                }
                this.loadsCount++;
                i5 = ((Long) objArr[0]).longValue() == this.dialog_id ? 0 : 1;
                int intValue = ((Integer) objArr[1]).intValue();
                boolean booleanValue = ((Boolean) objArr[3]).booleanValue();
                i2 = ((Integer) objArr[4]).intValue();
                int intValue2 = ((Integer) objArr[7]).intValue();
                int intValue3 = ((Integer) objArr[8]).intValue();
                i3 = ((Integer) objArr[12]).intValue();
                i6 = 0;
                if (!this.isAdvancedForward) {
                    i6 = ((Integer) objArr[13]).intValue();
                    if (i6 < 0) {
                        i6 *= -1;
                        this.hasAllMentionsLocal = false;
                        i7 = i6;
                        if (intValue3 == 4) {
                            this.startLoadFromMessageId = i3;
                            for (i3 = arrayList.size() - 1; i3 > 0; i3--) {
                                messageObject = (MessageObject) arrayList.get(i3);
                                if (messageObject.type >= 0 && messageObject.getId() == this.startLoadFromMessageId) {
                                    this.startLoadFromMessageId = ((MessageObject) arrayList.get(i3 - 1)).getId();
                                    break;
                                }
                            }
                        }
                        z4 = false;
                        if (i2 == 0) {
                            this.last_message_id = ((Integer) objArr[5]).intValue();
                            if (intValue3 != 3) {
                                if (this.loadingFromOldPosition) {
                                    obj = null;
                                } else {
                                    this.unread_to_load = ((Integer) objArr[6]).intValue();
                                    if (this.unread_to_load != 0) {
                                        this.createUnreadMessageAfterId = i2;
                                    }
                                    obj = 1;
                                    this.loadingFromOldPosition = false;
                                }
                                this.first_unread_id = 0;
                                obj3 = obj;
                            } else {
                                this.first_unread_id = i2;
                                this.unread_to_load = ((Integer) objArr[6]).intValue();
                                obj3 = null;
                            }
                        } else {
                            if (this.startLoadFromMessageId != 0 && (intValue3 == 3 || intValue3 == 4)) {
                                this.last_message_id = ((Integer) objArr[5]).intValue();
                            }
                            obj3 = null;
                        }
                        i2 = 0;
                        if (intValue3 != 0) {
                            zArr = this.forwardEndReached;
                            z = this.startLoadFromMessageId != 0 && this.last_message_id == 0;
                            zArr[i5] = z;
                        }
                        if ((intValue3 == 1 || intValue3 == 3) && i5 == 1) {
                            zArr2 = this.endReached;
                            this.cacheEndReached[0] = true;
                            zArr2[0] = true;
                            this.forwardEndReached[0] = false;
                            this.minMessageId[0] = 0;
                        }
                        if (this.loadsCount == 1 && arrayList.size() > 20) {
                            this.loadsCount++;
                        }
                        if (this.firstLoading) {
                            if (!this.forwardEndReached[i5]) {
                                this.messages.clear();
                                this.messagesByDays.clear();
                                this.groupedMessagesMap.clear();
                                for (i6 = 0; i6 < 2; i6++) {
                                    this.messagesDict[i6].clear();
                                    if (this.currentEncryptedChat != null) {
                                        this.maxMessageId[i6] = Integer.MAX_VALUE;
                                        this.minMessageId[i6] = Integer.MIN_VALUE;
                                    } else {
                                        this.maxMessageId[i6] = Integer.MIN_VALUE;
                                        this.minMessageId[i6] = Integer.MAX_VALUE;
                                    }
                                    this.maxDate[i6] = Integer.MIN_VALUE;
                                    this.minDate[i6] = 0;
                                }
                            }
                            this.firstLoading = false;
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (ChatActivity.this.parentLayout != null) {
                                        ChatActivity.this.parentLayout.resumeDelayedFragmentAnimation();
                                    }
                                }
                            });
                        }
                        if (intValue3 == 1) {
                            Collections.reverse(arrayList);
                        }
                        if (this.currentEncryptedChat == null) {
                            MessagesQuery.loadReplyMessagesForMessages(arrayList, this.dialog_id);
                        }
                        if (intValue3 == 2 && arrayList.isEmpty() && !booleanValue) {
                            this.forwardEndReached[0] = true;
                        }
                        hashMap4 = null;
                        hashMap = null;
                        i8 = 0;
                        i9 = 0;
                        while (i8 < arrayList.size()) {
                            messageObject2 = (MessageObject) arrayList.get(i8);
                            i9 += messageObject2.getApproximateHeight();
                            if (this.currentUser != null) {
                                if (this.currentUser.self) {
                                    messageObject2.messageOwner.out = true;
                                }
                                if ((this.currentUser.bot && messageObject2.isOut()) || this.currentUser.id == clientUserId) {
                                    messageObject2.setIsRead();
                                }
                            }
                            if (this.messagesDict[i5].containsKey(Integer.valueOf(messageObject2.getId()))) {
                                if (i5 == 1) {
                                    messageObject2.setIsRead();
                                }
                                if (i5 == 0 && ChatObject.isChannel(this.currentChat) && messageObject2.getId() == 1) {
                                    this.endReached[i5] = true;
                                    this.cacheEndReached[i5] = true;
                                }
                                if (messageObject2.getId() > 0) {
                                    this.maxMessageId[i5] = Math.min(messageObject2.getId(), this.maxMessageId[i5]);
                                    this.minMessageId[i5] = Math.max(messageObject2.getId(), this.minMessageId[i5]);
                                } else if (this.currentEncryptedChat != null) {
                                    this.maxMessageId[i5] = Math.max(messageObject2.getId(), this.maxMessageId[i5]);
                                    this.minMessageId[i5] = Math.min(messageObject2.getId(), this.minMessageId[i5]);
                                }
                                if (messageObject2.messageOwner.date != 0) {
                                    this.maxDate[i5] = Math.max(this.maxDate[i5], messageObject2.messageOwner.date);
                                    if (this.minDate[i5] == 0 || messageObject2.messageOwner.date < this.minDate[i5]) {
                                        this.minDate[i5] = messageObject2.messageOwner.date;
                                    }
                                }
                                if (messageObject2.getId() == this.last_message_id) {
                                    this.forwardEndReached[i5] = true;
                                }
                                if (messageObject2.type >= 0) {
                                    i6 = i2;
                                    z5 = z4;
                                    hashMap2 = hashMap;
                                } else if (i5 == 1 || !(messageObject2.messageOwner.action instanceof TLRPC$TL_messageActionChatMigrateTo)) {
                                    if (!messageObject2.isOut() && messageObject2.isUnread()) {
                                        z4 = true;
                                    }
                                    this.messagesDict[i5].put(Integer.valueOf(messageObject2.getId()), messageObject2);
                                    arrayList2 = (ArrayList) this.messagesByDays.get(messageObject2.dateKey);
                                    if (arrayList2 != null) {
                                        arrayList2 = new ArrayList();
                                        this.messagesByDays.put(messageObject2.dateKey, arrayList2);
                                        tLRPC$TL_message2 = new TLRPC$TL_message();
                                        if (BuildConfig.FLAVOR.contentEquals("arabgram")) {
                                            tLRPC$TL_message2.message = getPersianDate((long) messageObject2.messageOwner.date);
                                        } else {
                                            tLRPC$TL_message2.message = LocaleController.formatDateChat((long) messageObject2.messageOwner.date);
                                        }
                                        tLRPC$TL_message2.id = 0;
                                        tLRPC$TL_message2.date = messageObject2.messageOwner.date;
                                        messageObject3 = new MessageObject(tLRPC$TL_message2, null, false);
                                        messageObject3.type = 10;
                                        messageObject3.contentType = 1;
                                        messageObject3.isDateObject = true;
                                        if (intValue3 != 1) {
                                            this.messages.add(0, messageObject3);
                                        } else {
                                            this.messages.add(messageObject3);
                                        }
                                        arrayList3 = arrayList2;
                                        i10 = i2 + 1;
                                    } else {
                                        arrayList3 = arrayList2;
                                        i10 = i2;
                                    }
                                    if (messageObject2.hasValidGroupId()) {
                                        if (messageObject2.messageOwner.grouped_id != 0) {
                                            messageObject2.messageOwner.grouped_id = 0;
                                        }
                                        hashMap2 = hashMap;
                                    } else {
                                        groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject2.messageOwner.grouped_id));
                                        if (groupedMessages != null && this.messages.size() > 1) {
                                            messageObject4 = intValue3 != 1 ? (MessageObject) this.messages.get(0) : (MessageObject) this.messages.get(this.messages.size() - 2);
                                            if (messageObject4.messageOwner.grouped_id != messageObject2.messageOwner.grouped_id) {
                                                if (messageObject4.localGroupId != 0) {
                                                    messageObject2.localGroupId = messageObject4.localGroupId;
                                                    groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject4.localGroupId));
                                                }
                                            } else if (messageObject4.messageOwner.grouped_id != messageObject2.messageOwner.grouped_id) {
                                                messageObject2.localGroupId = Utilities.random.nextLong();
                                                groupedMessages = null;
                                            }
                                        }
                                        if (groupedMessages != null) {
                                            groupedMessages = new GroupedMessages();
                                            groupedMessages.groupId = messageObject2.getGroupId();
                                            this.groupedMessagesMap.put(Long.valueOf(groupedMessages.groupId), groupedMessages);
                                            hashMap2 = hashMap;
                                            groupedMessages2 = groupedMessages;
                                        } else {
                                            if (hashMap4 == null || !hashMap4.containsKey(Long.valueOf(messageObject2.getGroupId()))) {
                                                if (hashMap == null) {
                                                    hashMap = new HashMap();
                                                }
                                                hashMap.put(Long.valueOf(messageObject2.getGroupId()), groupedMessages);
                                            }
                                            hashMap2 = hashMap;
                                            groupedMessages2 = groupedMessages;
                                        }
                                        hashMap3 = hashMap4 != null ? new HashMap() : hashMap4;
                                        hashMap3.put(Long.valueOf(groupedMessages2.groupId), groupedMessages2);
                                        if (intValue3 != 1) {
                                            groupedMessages2.messages.add(messageObject2);
                                        } else {
                                            groupedMessages2.messages.add(0, messageObject2);
                                        }
                                        hashMap4 = hashMap3;
                                    }
                                    i11 = i10 + 1;
                                    arrayList3.add(messageObject2);
                                    if (intValue3 != 1) {
                                        this.messages.add(0, messageObject2);
                                    } else {
                                        this.messages.add(this.messages.size() - 1, messageObject2);
                                    }
                                    if (this.currentEncryptedChat != null) {
                                        if (this.createUnreadMessageAfterId != 0 || intValue3 == 1 || i8 + 1 >= arrayList.size()) {
                                            messageObject5 = null;
                                        } else {
                                            messageObject = (MessageObject) arrayList.get(i8 + 1);
                                            if (messageObject2.isOut() || messageObject.getId() >= this.createUnreadMessageAfterId) {
                                                messageObject5 = null;
                                            }
                                            messageObject5 = messageObject;
                                        }
                                    } else if (this.createUnreadMessageAfterId != 0 || intValue3 == 1 || i8 - 1 < 0) {
                                        messageObject5 = null;
                                    } else {
                                        messageObject = (MessageObject) arrayList.get(i8 - 1);
                                        if (messageObject2.isOut() || messageObject.getId() >= this.createUnreadMessageAfterId) {
                                            messageObject5 = null;
                                        }
                                        messageObject5 = messageObject;
                                    }
                                    if (intValue3 == 2 || messageObject2.getId() != this.first_unread_id) {
                                        if ((intValue3 == 3 || intValue3 == 4) && messageObject2.getId() == this.startLoadFromMessageId) {
                                            if (this.needSelectFromMessageId) {
                                                this.highlightMessageId = Integer.MAX_VALUE;
                                            } else {
                                                this.highlightMessageId = messageObject2.getId();
                                            }
                                            this.scrollToMessage = messageObject2;
                                            this.startLoadFromMessageId = 0;
                                            if (this.scrollToMessagePosition == -10000) {
                                                this.scrollToMessagePosition = -9000;
                                            }
                                        }
                                        i6 = i11;
                                    } else {
                                        if (i9 > AndroidUtilities.displaySize.y / 2 || !this.forwardEndReached[0]) {
                                            tLRPC$TL_message = new TLRPC$TL_message();
                                            tLRPC$TL_message.message = TtmlNode.ANONYMOUS_REGION_ID;
                                            tLRPC$TL_message.id = 0;
                                            messageObject3 = new MessageObject(tLRPC$TL_message, null, false);
                                            messageObject3.type = 6;
                                            messageObject3.contentType = 2;
                                            this.messages.add(this.messages.size() - 1, messageObject3);
                                            this.unreadMessageObject = messageObject3;
                                            this.scrollToMessage = this.unreadMessageObject;
                                            this.scrollToMessagePosition = -10000;
                                            i6 = i11 + 1;
                                        }
                                        i6 = i11;
                                    }
                                    if (intValue3 != 2 || this.unreadMessageObject != null || this.createUnreadMessageAfterId == 0 || (((this.currentEncryptedChat != null || messageObject2.isOut() || messageObject2.getId() < this.createUnreadMessageAfterId) && (this.currentEncryptedChat == null || messageObject2.isOut() || messageObject2.getId() > this.createUnreadMessageAfterId)) || !(intValue3 == 1 || r15 != null || (r15 == null && z2 && i8 == arrayList.size() - 1)))) {
                                        z5 = z4;
                                    } else {
                                        Message tLRPC$TL_message3 = new TLRPC$TL_message();
                                        tLRPC$TL_message3.message = TtmlNode.ANONYMOUS_REGION_ID;
                                        tLRPC$TL_message3.id = 0;
                                        MessageObject messageObject6 = new MessageObject(tLRPC$TL_message3, null, false);
                                        messageObject6.type = 6;
                                        messageObject6.contentType = 2;
                                        if (intValue3 == 1) {
                                            this.messages.add(1, messageObject6);
                                        } else {
                                            this.messages.add(this.messages.size() - 1, messageObject6);
                                        }
                                        this.unreadMessageObject = messageObject6;
                                        if (intValue3 == 3) {
                                            this.scrollToMessage = this.unreadMessageObject;
                                            this.startLoadFromMessageId = 0;
                                            this.scrollToMessagePosition = -9000;
                                        }
                                        i6++;
                                        z5 = z4;
                                    }
                                } else {
                                    i6 = i2;
                                    z5 = z4;
                                    hashMap2 = hashMap;
                                }
                            } else {
                                i6 = i2;
                                z5 = z4;
                                hashMap2 = hashMap;
                            }
                            i8++;
                            z4 = z5;
                            hashMap = hashMap2;
                            i2 = i6;
                        }
                        if (z2) {
                            this.createUnreadMessageAfterId = 0;
                        }
                        if (intValue3 == 0 && i2 == 0) {
                            this.loadsCount--;
                        }
                        if (hashMap4 != null) {
                            for (Entry entry : hashMap4.entrySet()) {
                                groupedMessages3 = (GroupedMessages) entry.getValue();
                                groupedMessages3.calculate();
                                if (!(this.chatAdapter == null || hashMap == null || !hashMap.containsKey(entry.getKey()))) {
                                    i6 = this.messages.indexOf((MessageObject) groupedMessages3.messages.get(groupedMessages3.messages.size() - 1));
                                    if (i6 >= 0) {
                                        this.chatAdapter.notifyItemRangeChanged(i6 + this.chatAdapter.messagesStartRow, groupedMessages3.messages.size());
                                    }
                                }
                            }
                        }
                        if (this.forwardEndReached[i5] && i5 != 1) {
                            this.first_unread_id = 0;
                            this.last_message_id = 0;
                            this.createUnreadMessageAfterId = 0;
                        }
                        if (intValue3 != 1) {
                            if (arrayList.size() != intValue || (booleanValue && this.currentEncryptedChat == null && !this.forwardEndReached[i5])) {
                                i3 = 0;
                            } else {
                                this.forwardEndReached[i5] = true;
                                if (i5 != 1) {
                                    this.first_unread_id = 0;
                                    this.last_message_id = 0;
                                    this.createUnreadMessageAfterId = 0;
                                    this.chatAdapter.notifyItemRemoved(this.chatAdapter.loadingDownRow);
                                    indexOf = 1;
                                } else {
                                    indexOf = 0;
                                }
                                this.startLoadFromMessageId = 0;
                                i3 = indexOf;
                            }
                            if (i2 > 0) {
                                indexOf = this.chatLayoutManager.findFirstVisibleItemPosition();
                                if (indexOf == 0) {
                                    indexOf++;
                                }
                                findViewByPosition = this.chatLayoutManager.findViewByPosition(indexOf);
                                i6 = findViewByPosition != null ? 0 : (this.chatListView.getMeasuredHeight() - findViewByPosition.getBottom()) - this.chatListView.getPaddingBottom();
                                this.chatAdapter.notifyItemRangeInserted(1, i2);
                                if (indexOf != -1) {
                                    this.chatLayoutManager.scrollToPositionWithOffset((indexOf + i2) - i3, i6);
                                }
                            }
                            this.loadingForward = false;
                        } else {
                            if (!(arrayList.size() >= intValue || intValue3 == 3 || intValue3 == 4)) {
                                if (booleanValue) {
                                    if (this.currentEncryptedChat != null || this.isBroadcast) {
                                        this.endReached[i5] = true;
                                    }
                                    if (intValue3 != 2) {
                                        this.cacheEndReached[i5] = true;
                                    }
                                } else if (intValue3 != 2 || (arrayList.size() == 0 && this.messages.isEmpty())) {
                                    this.endReached[i5] = true;
                                }
                            }
                            this.loading = false;
                            if (this.chatListView == null) {
                                if (!this.first || this.scrollToTopOnResume || this.forceScrollToTop) {
                                    this.forceScrollToTop = false;
                                    this.chatAdapter.notifyDataSetChanged();
                                    if (this.scrollToMessage == null) {
                                        z3 = true;
                                        if (this.startLoadFromMessageOffset != Integer.MAX_VALUE) {
                                            i6 = (-this.startLoadFromMessageOffset) - this.chatListView.getPaddingBottom();
                                            this.startLoadFromMessageOffset = Integer.MAX_VALUE;
                                        } else if (this.scrollToMessagePosition == -9000) {
                                            i6 = getScrollOffsetForMessage(this.scrollToMessage);
                                            z3 = false;
                                        } else if (this.scrollToMessagePosition != -10000) {
                                            i6 = (-this.chatListView.getPaddingTop()) - AndroidUtilities.dp(7.0f);
                                            z3 = false;
                                        } else {
                                            i6 = this.scrollToMessagePosition;
                                        }
                                        if (!this.messages.isEmpty()) {
                                            if (this.messages.get(this.messages.size() - 1) != this.scrollToMessage || this.messages.get(this.messages.size() - 2) == this.scrollToMessage) {
                                                this.chatLayoutManager.scrollToPositionWithOffset(this.chatAdapter.loadingUpRow, i6, z3);
                                            } else {
                                                this.chatLayoutManager.scrollToPositionWithOffset(this.chatAdapter.messagesStartRow + this.messages.indexOf(this.scrollToMessage), i6, z3);
                                            }
                                        }
                                        this.chatListView.invalidate();
                                        if (this.scrollToMessagePosition == -10000 || this.scrollToMessagePosition == -9000) {
                                            showPagedownButton(true, true);
                                            if (!(intValue3 != 3 || this.unread_to_load == 0 || r17 == null)) {
                                                this.pagedownButtonCounter.setVisibility(0);
                                                textView = this.pagedownButtonCounter;
                                                objArr2 = new Object[1];
                                                i12 = this.unread_to_load;
                                                this.newUnreadMessageCount = i12;
                                                objArr2[0] = Integer.valueOf(i12);
                                                textView.setText(String.format("%d", objArr2));
                                            }
                                        }
                                        this.scrollToMessagePosition = -10000;
                                        this.scrollToMessage = null;
                                    } else {
                                        moveScrollToLastMessage();
                                    }
                                    if (i7 != 0) {
                                        showMentiondownButton(true, true);
                                        this.mentiondownButtonCounter.setVisibility(0);
                                        textView = this.mentiondownButtonCounter;
                                        objArr2 = new Object[1];
                                        this.newMentionsCount = i7;
                                        objArr2[0] = Integer.valueOf(i7);
                                        textView.setText(String.format("%d", objArr2));
                                    }
                                } else if (i2 != 0) {
                                    obj2 = null;
                                    if (this.endReached[i5] && ((i5 == 0 && this.mergeDialogId == 0) || i5 == 1)) {
                                        obj2 = 1;
                                        this.chatAdapter.notifyItemRangeChanged(this.chatAdapter.loadingUpRow - 1, 2);
                                        this.chatAdapter.updateRows();
                                    }
                                    i4 = this.chatLayoutManager.findFirstVisibleItemPosition();
                                    findViewByPosition = this.chatLayoutManager.findViewByPosition(i4);
                                    i3 = findViewByPosition == null ? 0 : (this.chatListView.getMeasuredHeight() - findViewByPosition.getBottom()) - this.chatListView.getPaddingBottom();
                                    if (i2 - (obj2 != null ? 1 : 0) > 0) {
                                        i6 = this.chatAdapter.messagesEndRow;
                                        this.chatAdapter.notifyItemChanged(this.chatAdapter.loadingUpRow);
                                        this.chatAdapter.notifyItemRangeInserted(i6, i2 - (obj2 != null ? 1 : 0));
                                    }
                                    if (i4 != -1) {
                                        this.chatLayoutManager.scrollToPositionWithOffset(i4, i3);
                                    }
                                } else if (this.endReached[i5] && ((i5 == 0 && this.mergeDialogId == 0) || i5 == 1)) {
                                    this.chatAdapter.notifyItemRemoved(this.chatAdapter.loadingUpRow);
                                }
                                if (this.paused) {
                                    this.scrollToTopOnResume = true;
                                    if (this.scrollToMessage != null) {
                                        this.scrollToTopUnReadOnResume = true;
                                    }
                                }
                                if (this.first && this.chatListView != null) {
                                    this.chatListView.setEmptyView(this.emptyViewContainer);
                                }
                            } else {
                                this.scrollToTopOnResume = true;
                                if (this.scrollToMessage != null) {
                                    this.scrollToTopUnReadOnResume = true;
                                }
                            }
                        }
                        if (this.first && this.messages.size() > 0) {
                            if (i5 == 0) {
                                indexOf = ((MessageObject) this.messages.get(0)).getId();
                                if (!this.isAdvancedForward) {
                                    i13 = intValue2;
                                    AndroidUtilities.runOnUIThread(new Runnable() {
                                        public void run() {
                                            if (ChatActivity.this.last_message_id != 0) {
                                                MessagesController.getInstance().markDialogAsRead(ChatActivity.this.dialog_id, indexOf, ChatActivity.this.last_message_id, i13, z4, false);
                                            } else {
                                                MessagesController.getInstance().markDialogAsRead(ChatActivity.this.dialog_id, indexOf, ChatActivity.this.minMessageId[0], ChatActivity.this.maxDate[0], z4, false);
                                            }
                                        }
                                    }, 700);
                                }
                            }
                            this.first = false;
                        }
                        if (this.messages.isEmpty() && this.currentEncryptedChat == null && this.currentUser != null && this.currentUser.bot && this.botUser == null) {
                            this.botUser = TtmlNode.ANONYMOUS_REGION_ID;
                            updateBottomOverlay();
                        }
                        if (i2 != 0 && this.currentEncryptedChat != null && !this.endReached[0]) {
                            this.first = true;
                            if (this.chatListView != null) {
                                this.chatListView.setEmptyView(null);
                            }
                            if (this.emptyViewContainer != null) {
                                this.emptyViewContainer.setVisibility(4);
                            }
                        } else if (this.progressView != null) {
                            this.progressView.setVisibility(4);
                        }
                        checkScrollForLoad(false);
                    } else if (this.first) {
                        this.hasAllMentionsLocal = true;
                    }
                }
                i7 = i6;
                if (intValue3 == 4) {
                    this.startLoadFromMessageId = i3;
                    while (i3 > 0) {
                        messageObject = (MessageObject) arrayList.get(i3);
                        if (messageObject.type >= 0) {
                        }
                    }
                }
                z4 = false;
                if (i2 == 0) {
                    this.last_message_id = ((Integer) objArr[5]).intValue();
                    obj3 = null;
                } else {
                    this.last_message_id = ((Integer) objArr[5]).intValue();
                    if (intValue3 != 3) {
                        this.first_unread_id = i2;
                        this.unread_to_load = ((Integer) objArr[6]).intValue();
                        obj3 = null;
                    } else {
                        if (this.loadingFromOldPosition) {
                            obj = null;
                        } else {
                            this.unread_to_load = ((Integer) objArr[6]).intValue();
                            if (this.unread_to_load != 0) {
                                this.createUnreadMessageAfterId = i2;
                            }
                            obj = 1;
                            this.loadingFromOldPosition = false;
                        }
                        this.first_unread_id = 0;
                        obj3 = obj;
                    }
                }
                i2 = 0;
                if (intValue3 != 0) {
                    zArr = this.forwardEndReached;
                    if (this.startLoadFromMessageId != 0) {
                    }
                    zArr[i5] = z;
                }
                zArr2 = this.endReached;
                this.cacheEndReached[0] = true;
                zArr2[0] = true;
                this.forwardEndReached[0] = false;
                this.minMessageId[0] = 0;
                this.loadsCount++;
                if (this.firstLoading) {
                    if (this.forwardEndReached[i5]) {
                        this.messages.clear();
                        this.messagesByDays.clear();
                        this.groupedMessagesMap.clear();
                        for (i6 = 0; i6 < 2; i6++) {
                            this.messagesDict[i6].clear();
                            if (this.currentEncryptedChat != null) {
                                this.maxMessageId[i6] = Integer.MIN_VALUE;
                                this.minMessageId[i6] = Integer.MAX_VALUE;
                            } else {
                                this.maxMessageId[i6] = Integer.MAX_VALUE;
                                this.minMessageId[i6] = Integer.MIN_VALUE;
                            }
                            this.maxDate[i6] = Integer.MIN_VALUE;
                            this.minDate[i6] = 0;
                        }
                    }
                    this.firstLoading = false;
                    AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                }
                if (intValue3 == 1) {
                    Collections.reverse(arrayList);
                }
                if (this.currentEncryptedChat == null) {
                    MessagesQuery.loadReplyMessagesForMessages(arrayList, this.dialog_id);
                }
                this.forwardEndReached[0] = true;
                hashMap4 = null;
                hashMap = null;
                i8 = 0;
                i9 = 0;
                while (i8 < arrayList.size()) {
                    messageObject2 = (MessageObject) arrayList.get(i8);
                    i9 += messageObject2.getApproximateHeight();
                    if (this.currentUser != null) {
                        if (this.currentUser.self) {
                            messageObject2.messageOwner.out = true;
                        }
                        messageObject2.setIsRead();
                    }
                    if (this.messagesDict[i5].containsKey(Integer.valueOf(messageObject2.getId()))) {
                        if (i5 == 1) {
                            messageObject2.setIsRead();
                        }
                        this.endReached[i5] = true;
                        this.cacheEndReached[i5] = true;
                        if (messageObject2.getId() > 0) {
                            this.maxMessageId[i5] = Math.min(messageObject2.getId(), this.maxMessageId[i5]);
                            this.minMessageId[i5] = Math.max(messageObject2.getId(), this.minMessageId[i5]);
                        } else if (this.currentEncryptedChat != null) {
                            this.maxMessageId[i5] = Math.max(messageObject2.getId(), this.maxMessageId[i5]);
                            this.minMessageId[i5] = Math.min(messageObject2.getId(), this.minMessageId[i5]);
                        }
                        if (messageObject2.messageOwner.date != 0) {
                            this.maxDate[i5] = Math.max(this.maxDate[i5], messageObject2.messageOwner.date);
                            this.minDate[i5] = messageObject2.messageOwner.date;
                        }
                        if (messageObject2.getId() == this.last_message_id) {
                            this.forwardEndReached[i5] = true;
                        }
                        if (messageObject2.type >= 0) {
                            i6 = i2;
                            z5 = z4;
                            hashMap2 = hashMap;
                        } else {
                            if (i5 == 1) {
                            }
                            z4 = true;
                            this.messagesDict[i5].put(Integer.valueOf(messageObject2.getId()), messageObject2);
                            arrayList2 = (ArrayList) this.messagesByDays.get(messageObject2.dateKey);
                            if (arrayList2 != null) {
                                arrayList3 = arrayList2;
                                i10 = i2;
                            } else {
                                arrayList2 = new ArrayList();
                                this.messagesByDays.put(messageObject2.dateKey, arrayList2);
                                tLRPC$TL_message2 = new TLRPC$TL_message();
                                if (BuildConfig.FLAVOR.contentEquals("arabgram")) {
                                    tLRPC$TL_message2.message = getPersianDate((long) messageObject2.messageOwner.date);
                                } else {
                                    tLRPC$TL_message2.message = LocaleController.formatDateChat((long) messageObject2.messageOwner.date);
                                }
                                tLRPC$TL_message2.id = 0;
                                tLRPC$TL_message2.date = messageObject2.messageOwner.date;
                                messageObject3 = new MessageObject(tLRPC$TL_message2, null, false);
                                messageObject3.type = 10;
                                messageObject3.contentType = 1;
                                messageObject3.isDateObject = true;
                                if (intValue3 != 1) {
                                    this.messages.add(messageObject3);
                                } else {
                                    this.messages.add(0, messageObject3);
                                }
                                arrayList3 = arrayList2;
                                i10 = i2 + 1;
                            }
                            if (messageObject2.hasValidGroupId()) {
                                if (messageObject2.messageOwner.grouped_id != 0) {
                                    messageObject2.messageOwner.grouped_id = 0;
                                }
                                hashMap2 = hashMap;
                            } else {
                                groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject2.messageOwner.grouped_id));
                                if (intValue3 != 1) {
                                }
                                if (messageObject4.messageOwner.grouped_id != messageObject2.messageOwner.grouped_id) {
                                    if (messageObject4.messageOwner.grouped_id != messageObject2.messageOwner.grouped_id) {
                                        messageObject2.localGroupId = Utilities.random.nextLong();
                                        groupedMessages = null;
                                    }
                                } else if (messageObject4.localGroupId != 0) {
                                    messageObject2.localGroupId = messageObject4.localGroupId;
                                    groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject4.localGroupId));
                                }
                                if (groupedMessages != null) {
                                    if (hashMap == null) {
                                        hashMap = new HashMap();
                                    }
                                    hashMap.put(Long.valueOf(messageObject2.getGroupId()), groupedMessages);
                                    hashMap2 = hashMap;
                                    groupedMessages2 = groupedMessages;
                                } else {
                                    groupedMessages = new GroupedMessages();
                                    groupedMessages.groupId = messageObject2.getGroupId();
                                    this.groupedMessagesMap.put(Long.valueOf(groupedMessages.groupId), groupedMessages);
                                    hashMap2 = hashMap;
                                    groupedMessages2 = groupedMessages;
                                }
                                if (hashMap4 != null) {
                                }
                                hashMap3.put(Long.valueOf(groupedMessages2.groupId), groupedMessages2);
                                if (intValue3 != 1) {
                                    groupedMessages2.messages.add(0, messageObject2);
                                } else {
                                    groupedMessages2.messages.add(messageObject2);
                                }
                                hashMap4 = hashMap3;
                            }
                            i11 = i10 + 1;
                            arrayList3.add(messageObject2);
                            if (intValue3 != 1) {
                                this.messages.add(this.messages.size() - 1, messageObject2);
                            } else {
                                this.messages.add(0, messageObject2);
                            }
                            if (this.currentEncryptedChat != null) {
                                if (this.createUnreadMessageAfterId != 0) {
                                }
                                messageObject5 = null;
                            } else {
                                if (this.createUnreadMessageAfterId != 0) {
                                }
                                messageObject5 = null;
                            }
                            if (intValue3 == 2) {
                            }
                            if (this.needSelectFromMessageId) {
                                this.highlightMessageId = Integer.MAX_VALUE;
                            } else {
                                this.highlightMessageId = messageObject2.getId();
                            }
                            this.scrollToMessage = messageObject2;
                            this.startLoadFromMessageId = 0;
                            if (this.scrollToMessagePosition == -10000) {
                                this.scrollToMessagePosition = -9000;
                            }
                            i6 = i11;
                            if (intValue3 != 2) {
                            }
                            z5 = z4;
                        }
                    } else {
                        i6 = i2;
                        z5 = z4;
                        hashMap2 = hashMap;
                    }
                    i8++;
                    z4 = z5;
                    hashMap = hashMap2;
                    i2 = i6;
                }
                if (z2) {
                    this.createUnreadMessageAfterId = 0;
                }
                this.loadsCount--;
                if (hashMap4 != null) {
                    for (Entry entry2 : hashMap4.entrySet()) {
                        groupedMessages3 = (GroupedMessages) entry2.getValue();
                        groupedMessages3.calculate();
                        i6 = this.messages.indexOf((MessageObject) groupedMessages3.messages.get(groupedMessages3.messages.size() - 1));
                        if (i6 >= 0) {
                            this.chatAdapter.notifyItemRangeChanged(i6 + this.chatAdapter.messagesStartRow, groupedMessages3.messages.size());
                        }
                    }
                }
                this.first_unread_id = 0;
                this.last_message_id = 0;
                this.createUnreadMessageAfterId = 0;
                if (intValue3 != 1) {
                    if (booleanValue) {
                        this.endReached[i5] = true;
                        if (intValue3 != 2) {
                            this.cacheEndReached[i5] = true;
                        }
                    } else {
                        this.endReached[i5] = true;
                    }
                    this.loading = false;
                    if (this.chatListView == null) {
                        this.scrollToTopOnResume = true;
                        if (this.scrollToMessage != null) {
                            this.scrollToTopUnReadOnResume = true;
                        }
                    } else {
                        if (this.first) {
                        }
                        this.forceScrollToTop = false;
                        this.chatAdapter.notifyDataSetChanged();
                        if (this.scrollToMessage == null) {
                            moveScrollToLastMessage();
                        } else {
                            z3 = true;
                            if (this.startLoadFromMessageOffset != Integer.MAX_VALUE) {
                                i6 = (-this.startLoadFromMessageOffset) - this.chatListView.getPaddingBottom();
                                this.startLoadFromMessageOffset = Integer.MAX_VALUE;
                            } else if (this.scrollToMessagePosition == -9000) {
                                i6 = getScrollOffsetForMessage(this.scrollToMessage);
                                z3 = false;
                            } else if (this.scrollToMessagePosition != -10000) {
                                i6 = this.scrollToMessagePosition;
                            } else {
                                i6 = (-this.chatListView.getPaddingTop()) - AndroidUtilities.dp(7.0f);
                                z3 = false;
                            }
                            if (this.messages.isEmpty()) {
                                if (this.messages.get(this.messages.size() - 1) != this.scrollToMessage) {
                                }
                                this.chatLayoutManager.scrollToPositionWithOffset(this.chatAdapter.loadingUpRow, i6, z3);
                            }
                            this.chatListView.invalidate();
                            showPagedownButton(true, true);
                            this.pagedownButtonCounter.setVisibility(0);
                            textView = this.pagedownButtonCounter;
                            objArr2 = new Object[1];
                            i12 = this.unread_to_load;
                            this.newUnreadMessageCount = i12;
                            objArr2[0] = Integer.valueOf(i12);
                            textView.setText(String.format("%d", objArr2));
                            this.scrollToMessagePosition = -10000;
                            this.scrollToMessage = null;
                        }
                        if (i7 != 0) {
                            showMentiondownButton(true, true);
                            this.mentiondownButtonCounter.setVisibility(0);
                            textView = this.mentiondownButtonCounter;
                            objArr2 = new Object[1];
                            this.newMentionsCount = i7;
                            objArr2[0] = Integer.valueOf(i7);
                            textView.setText(String.format("%d", objArr2));
                        }
                        if (this.paused) {
                            this.scrollToTopOnResume = true;
                            if (this.scrollToMessage != null) {
                                this.scrollToTopUnReadOnResume = true;
                            }
                        }
                        this.chatListView.setEmptyView(this.emptyViewContainer);
                    }
                } else {
                    if (arrayList.size() != intValue) {
                    }
                    i3 = 0;
                    if (i2 > 0) {
                        indexOf = this.chatLayoutManager.findFirstVisibleItemPosition();
                        if (indexOf == 0) {
                            indexOf++;
                        }
                        findViewByPosition = this.chatLayoutManager.findViewByPosition(indexOf);
                        if (findViewByPosition != null) {
                        }
                        this.chatAdapter.notifyItemRangeInserted(1, i2);
                        if (indexOf != -1) {
                            this.chatLayoutManager.scrollToPositionWithOffset((indexOf + i2) - i3, i6);
                        }
                    }
                    this.loadingForward = false;
                }
                if (i5 == 0) {
                    indexOf = ((MessageObject) this.messages.get(0)).getId();
                    if (this.isAdvancedForward) {
                        i13 = intValue2;
                        AndroidUtilities.runOnUIThread(/* anonymous class already generated */, 700);
                    }
                }
                this.first = false;
                this.botUser = TtmlNode.ANONYMOUS_REGION_ID;
                updateBottomOverlay();
                if (i2 != 0) {
                }
                if (this.progressView != null) {
                    this.progressView.setVisibility(4);
                }
                checkScrollForLoad(false);
            }
            if (this.isAdvancedForward) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        ChatActivity.this.endReached[0] = true;
                        ChatActivity.this.endReached[1] = true;
                        ChatActivity.this.forwardEndReached[0] = true;
                        ChatActivity.this.forwardEndReached[1] = true;
                        ChatActivity.this.chatAdapter.notifyDataSetChanged();
                        ChatActivity.this.actionBar.setTitle("ارسال پیشرفته");
                    }
                }, 100);
            }
            if (this.isDownloadManager) {
                try {
                    indexOf = this.messages.size();
                    if (indexOf != 0) {
                        indexOf--;
                    }
                    this.avatarContainer.setSubtitle(LocaleController.getString("downloadCount", R.string.downloadCount) + " : " + indexOf);
                } catch (Exception e2) {
                }
            }
            Log.d("LEE", "Debug1946 didReceivedNotification() success");
        } else if (i == NotificationCenter.paymentSuccessMessage) {
            r4 = (String) objArr[0];
            Log.d("LEE", "NotificationCenter payment");
            sendMessage(r4);
        } else if (i == NotificationCenter.goToPayment) {
            r4 = (String) objArr[0];
            r4 = r4.substring(r4.lastIndexOf("/") + 1);
            Log.d("alireza", "alireza payment id : " + r4);
            Intent intent = new Intent(ApplicationLoader.applicationContext, PaymentConfirmActivity.class);
            intent.putExtra("EXTRA_PAYMENT_ID", r4);
            getParentActivity().startActivity(intent);
        } else if (i == NotificationCenter.emojiDidLoaded) {
            if (this.chatListView != null) {
                this.chatListView.invalidateViews();
            }
            if (this.replyObjectTextView != null) {
                this.replyObjectTextView.invalidate();
            }
            if (this.alertTextView != null) {
                this.alertTextView.invalidate();
            }
            if (this.pinnedMessageTextView != null) {
                this.pinnedMessageTextView.invalidate();
            }
            if (this.mentionListView != null) {
                this.mentionListView.invalidateViews();
            }
        } else if (i == NotificationCenter.updateInterfaces) {
            i6 = ((Integer) objArr[0]).intValue();
            if (!((i6 & 1) == 0 && (i6 & 16) == 0)) {
                if (this.currentChat != null) {
                    r4 = MessagesController.getInstance().getChat(Integer.valueOf(this.currentChat.id));
                    if (r4 != null) {
                        this.currentChat = r4;
                    }
                } else if (this.currentUser != null) {
                    r4 = MessagesController.getInstance().getUser(Integer.valueOf(this.currentUser.id));
                    if (r4 != null) {
                        this.currentUser = r4;
                    }
                }
                updateTitle();
            }
            obj2 = null;
            if (!((i6 & 32) == 0 && (i6 & 4) == 0)) {
                if (!(this.currentChat == null || this.avatarContainer == null)) {
                    this.avatarContainer.updateOnlineCount();
                }
                obj2 = 1;
            }
            if (!((i6 & 2) == 0 && (i6 & 8) == 0 && (i6 & 1) == 0)) {
                checkAndUpdateAvatar();
                updateVisibleRows();
            }
            if ((i6 & 64) != 0) {
                obj2 = 1;
            }
            if ((i6 & MessagesController.UPDATE_MASK_CHANNEL) != 0 && ChatObject.isChannel(this.currentChat)) {
                r4 = MessagesController.getInstance().getChat(Integer.valueOf(this.currentChat.id));
                if (r4 != null) {
                    this.currentChat = r4;
                    obj2 = 1;
                    updateBottomOverlay();
                    if (this.chatActivityEnterView != null) {
                        this.chatActivityEnterView.setDialogId(this.dialog_id);
                    }
                } else {
                    return;
                }
            }
            if (!(this.isDownloadManager || this.avatarContainer == null || r4 == null)) {
                this.avatarContainer.updateSubtitle();
            }
            if ((i6 & 128) != 0) {
                updateContactStatus();
            }
        } else if (i == NotificationCenter.didReceivedNewMessages) {
            if (((Long) objArr[0]).longValue() == this.dialog_id) {
                Object obj4;
                r17 = UserConfig.getClientUserId();
                arrayList = (ArrayList) objArr[1];
                if (this.currentEncryptedChat != null && arrayList.size() == 1) {
                    messageObject = (MessageObject) arrayList.get(0);
                    if (this.currentEncryptedChat != null && messageObject.isOut() && messageObject.messageOwner.action != null && (messageObject.messageOwner.action instanceof TLRPC$TL_messageEncryptedAction) && (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL) && getParentActivity() != null && AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) < 17 && this.currentEncryptedChat.ttl > 0 && this.currentEncryptedChat.ttl <= 60) {
                        r5 = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
                        r5.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        r5.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                        r5.setMessage(LocaleController.formatString("CompatibilityChat", R.string.CompatibilityChat, new Object[]{this.currentUser.first_name, this.currentUser.first_name}));
                        showDialog(r5.create());
                    }
                }
                if (!(this.currentChat == null && this.inlineReturn == 0)) {
                    for (i4 = 0; i4 < arrayList.size(); i4++) {
                        messageObject = (MessageObject) arrayList.get(i4);
                        if (this.currentChat != null) {
                            if (((messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatDeleteUser) && messageObject.messageOwner.action.user_id == r17) || ((messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatAddUser) && messageObject.messageOwner.action.users.contains(Integer.valueOf(r17)))) {
                                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.currentChat.id));
                                if (chat != null) {
                                    this.currentChat = chat;
                                    checkActionBarMenu();
                                    updateBottomOverlay();
                                    if (!(this.isDownloadManager || this.avatarContainer == null)) {
                                        this.avatarContainer.updateSubtitle();
                                    }
                                }
                            } else if (messageObject.messageOwner.reply_to_msg_id != 0 && messageObject.replyMessageObject == null) {
                                messageObject.replyMessageObject = (MessageObject) this.messagesDict[0].get(Integer.valueOf(messageObject.messageOwner.reply_to_msg_id));
                                if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPinMessage) {
                                    messageObject.generatePinMessageText(null, null);
                                } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionGameScore) {
                                    messageObject.generateGameMessageText(null);
                                } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPaymentSent) {
                                    messageObject.generatePaymentSentMessageText(null);
                                }
                                if (!(!messageObject.isMegagroup() || messageObject.replyMessageObject == null || messageObject.replyMessageObject.messageOwner == null)) {
                                    tLRPC$TL_message = messageObject.replyMessageObject.messageOwner;
                                    tLRPC$TL_message.flags |= Integer.MIN_VALUE;
                                }
                            }
                        } else if (!(this.inlineReturn == 0 || messageObject.messageOwner.reply_markup == null)) {
                            for (i5 = 0; i5 < messageObject.messageOwner.reply_markup.rows.size(); i5++) {
                                TLRPC$TL_keyboardButtonRow tLRPC$TL_keyboardButtonRow = (TLRPC$TL_keyboardButtonRow) messageObject.messageOwner.reply_markup.rows.get(i5);
                                for (i12 = 0; i12 < tLRPC$TL_keyboardButtonRow.buttons.size(); i12++) {
                                    KeyboardButton keyboardButton = (KeyboardButton) tLRPC$TL_keyboardButtonRow.buttons.get(i12);
                                    if (keyboardButton instanceof TLRPC$TL_keyboardButtonSwitchInline) {
                                        processSwitchButton((TLRPC$TL_keyboardButtonSwitchInline) keyboardButton);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                Object obj5;
                final BaseFragment baseFragment;
                if (this.forwardEndReached[0]) {
                    Object obj6 = null;
                    hashMap2 = null;
                    if (BuildVars.DEBUG_VERSION) {
                        FileLog.d("received new messages " + arrayList.size() + " in dialog " + this.dialog_id);
                    }
                    i10 = 0;
                    obj4 = null;
                    obj5 = 1;
                    HashMap hashMap5 = null;
                    r9 = null;
                    r13 = null;
                    while (i10 < arrayList.size()) {
                        i12 = -1;
                        messageObject2 = (MessageObject) arrayList.get(i10);
                        if (this.currentUser != null && ((this.currentUser.bot && messageObject2.isOut()) || this.currentUser.id == r17)) {
                            messageObject2.setIsRead();
                        }
                        if (!(this.avatarContainer == null || this.currentEncryptedChat == null || messageObject2.messageOwner.action == null || !(messageObject2.messageOwner.action instanceof TLRPC$TL_messageEncryptedAction) || !(messageObject2.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL))) {
                            this.avatarContainer.setTime(((TLRPC$TL_decryptedMessageActionSetMessageTTL) messageObject2.messageOwner.action.encryptedAction).ttl_seconds);
                        }
                        if (messageObject2.type < 0) {
                            hashMap3 = hashMap5;
                            r6 = obj4;
                        } else if (this.messagesDict[0].containsKey(Integer.valueOf(messageObject2.getId()))) {
                            hashMap3 = hashMap5;
                            r6 = obj4;
                        } else {
                            HashMap hashMap6;
                            if (i10 == 0 && messageObject2.messageOwner.id < 0 && messageObject2.type == 5) {
                                this.animatingMessageObjects.add(messageObject2);
                            }
                            if (messageObject2.hasValidGroupId()) {
                                groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject2.getGroupId()));
                                if (groupedMessages == null) {
                                    groupedMessages = new GroupedMessages();
                                    groupedMessages.groupId = messageObject2.getGroupId();
                                    this.groupedMessagesMap.put(Long.valueOf(groupedMessages.groupId), groupedMessages);
                                }
                                if (hashMap5 == null) {
                                    hashMap5 = new HashMap();
                                }
                                hashMap5.put(Long.valueOf(groupedMessages.groupId), groupedMessages);
                                groupedMessages.messages.add(messageObject2);
                                hashMap6 = hashMap5;
                            } else {
                                groupedMessages = null;
                                hashMap6 = hashMap5;
                            }
                            if (groupedMessages != null) {
                                obj = groupedMessages.messages.size() > 1 ? (MessageObject) groupedMessages.messages.get(groupedMessages.messages.size() - 2) : null;
                                if (obj != null) {
                                    i12 = this.messages.indexOf(obj);
                                }
                            }
                            if (i12 == -1) {
                                if (messageObject2.messageOwner.id < 0 || this.messages.isEmpty()) {
                                    i12 = 0;
                                } else {
                                    i7 = this.messages.size();
                                    i4 = 0;
                                    while (i4 < i7) {
                                        messageObject = (MessageObject) this.messages.get(i4);
                                        if (messageObject.type < 0 || messageObject.messageOwner.date <= 0 || ((messageObject.messageOwner.id <= 0 || messageObject2.messageOwner.id <= 0 || messageObject.messageOwner.id >= messageObject2.messageOwner.id) && messageObject.messageOwner.date >= messageObject2.messageOwner.date)) {
                                            i4++;
                                        } else {
                                            if (messageObject.getGroupId() != 0) {
                                                groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject.getGroupId()));
                                                if (groupedMessages != null && groupedMessages.messages.size() == 0) {
                                                    groupedMessages = null;
                                                }
                                            } else {
                                                groupedMessages = null;
                                            }
                                            i12 = groupedMessages == null ? i4 : this.messages.indexOf(groupedMessages.messages.get(groupedMessages.messages.size() - 1));
                                            if (i12 == -1 || i12 > this.messages.size()) {
                                                i12 = this.messages.size();
                                            }
                                        }
                                    }
                                    i12 = this.messages.size();
                                }
                            }
                            if (this.currentEncryptedChat != null && (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) && (messageObject2.messageOwner.media.webpage instanceof TLRPC$TL_webPageUrlPending)) {
                                if (hashMap2 == null) {
                                    hashMap2 = new HashMap();
                                }
                                arrayList2 = (ArrayList) hashMap2.get(messageObject2.messageOwner.media.webpage.url);
                                if (arrayList2 == null) {
                                    arrayList2 = new ArrayList();
                                    hashMap2.put(messageObject2.messageOwner.media.webpage.url, arrayList2);
                                }
                                arrayList2.add(messageObject2);
                            }
                            messageObject2.checkLayout();
                            if (messageObject2.messageOwner.action instanceof TLRPC$TL_messageActionChatMigrateTo) {
                                final Bundle bundle = new Bundle();
                                bundle.putInt("chat_id", messageObject2.messageOwner.action.channel_id);
                                baseFragment = this.parentLayout.fragmentsStack.size() > 0 ? (BaseFragment) this.parentLayout.fragmentsStack.get(this.parentLayout.fragmentsStack.size() - 1) : null;
                                i3 = messageObject2.messageOwner.action.channel_id;
                                AndroidUtilities.runOnUIThread(new Runnable() {

                                    /* renamed from: org.telegram.ui.ChatActivity$94$1 */
                                    class C42731 implements Runnable {
                                        C42731() {
                                        }

                                        public void run() {
                                            MessagesController.getInstance().loadFullChat(i3, 0, true);
                                        }
                                    }

                                    public void run() {
                                        ActionBarLayout actionBarLayout = ChatActivity.this.parentLayout;
                                        if (baseFragment != null) {
                                            NotificationCenter.getInstance().removeObserver(baseFragment, NotificationCenter.closeChats);
                                        }
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                        actionBarLayout.presentFragment(new ChatActivity(bundle), true);
                                        AndroidUtilities.runOnUIThread(new C42731(), 1000);
                                    }
                                });
                                if (hashMap6 != null) {
                                    for (Entry value : hashMap6.entrySet()) {
                                        ((GroupedMessages) value.getValue()).calculate();
                                    }
                                    return;
                                }
                                return;
                            }
                            r8 = (this.currentChat != null && this.currentChat.megagroup && ((messageObject2.messageOwner.action instanceof TLRPC$TL_messageActionChatAddUser) || (messageObject2.messageOwner.action instanceof TLRPC$TL_messageActionChatDeleteUser))) ? 1 : r13;
                            if (this.minDate[0] == 0 || messageObject2.messageOwner.date < this.minDate[0]) {
                                this.minDate[0] = messageObject2.messageOwner.date;
                            }
                            if (messageObject2.isOut()) {
                                removeUnreadPlane();
                                r9 = 1;
                            }
                            if (messageObject2.getId() > 0) {
                                this.maxMessageId[0] = Math.min(messageObject2.getId(), this.maxMessageId[0]);
                                this.minMessageId[0] = Math.max(messageObject2.getId(), this.minMessageId[0]);
                            } else if (this.currentEncryptedChat != null) {
                                this.maxMessageId[0] = Math.max(messageObject2.getId(), this.maxMessageId[0]);
                                this.minMessageId[0] = Math.min(messageObject2.getId(), this.minMessageId[0]);
                            }
                            this.maxDate[0] = Math.max(this.maxDate[0], messageObject2.messageOwner.date);
                            this.messagesDict[0].put(Integer.valueOf(messageObject2.getId()), messageObject2);
                            arrayList2 = (ArrayList) this.messagesByDays.get(messageObject2.dateKey);
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                                this.messagesByDays.put(messageObject2.dateKey, arrayList2);
                                Message tLRPC$TL_message4 = new TLRPC$TL_message();
                                if (BuildConfig.FLAVOR.contentEquals("arabgram")) {
                                    tLRPC$TL_message4.message = LocaleController.formatDateChat((long) messageObject2.messageOwner.date);
                                } else {
                                    tLRPC$TL_message4.message = getPersianDate((long) messageObject2.messageOwner.date);
                                }
                                tLRPC$TL_message4.id = 0;
                                tLRPC$TL_message4.date = messageObject2.messageOwner.date;
                                messageObject3 = new MessageObject(tLRPC$TL_message4, null, false);
                                messageObject3.type = 10;
                                messageObject3.contentType = 1;
                                messageObject3.isDateObject = true;
                                this.messages.add(i12, messageObject3);
                                if (this.chatAdapter != null) {
                                    this.chatAdapter.notifyItemInserted(i12);
                                }
                            }
                            if (!messageObject2.isOut()) {
                                if (this.paused && i12 == 0) {
                                    if (!(this.scrollToTopUnReadOnResume || this.unreadMessageObject == null)) {
                                        removeMessageObject(this.unreadMessageObject);
                                        if (i12 > 0) {
                                            i12--;
                                        }
                                        this.unreadMessageObject = null;
                                    }
                                    if (this.unreadMessageObject == null) {
                                        Message tLRPC$TL_message5 = new TLRPC$TL_message();
                                        tLRPC$TL_message5.message = TtmlNode.ANONYMOUS_REGION_ID;
                                        tLRPC$TL_message5.id = 0;
                                        MessageObject messageObject7 = new MessageObject(tLRPC$TL_message5, null, false);
                                        messageObject7.type = 6;
                                        messageObject7.contentType = 2;
                                        this.messages.add(0, messageObject7);
                                        if (this.chatAdapter != null) {
                                            this.chatAdapter.notifyItemInserted(0);
                                        }
                                        this.unreadMessageObject = messageObject7;
                                        this.scrollToMessage = this.unreadMessageObject;
                                        this.scrollToMessagePosition = -10000;
                                        obj5 = null;
                                        this.unread_to_load = 0;
                                        this.scrollToTopUnReadOnResume = true;
                                    }
                                }
                                if (this.unreadMessageObject != null) {
                                    this.unread_to_load++;
                                    obj5 = 1;
                                }
                                if (messageObject2.isUnread()) {
                                    if (!this.paused) {
                                        messageObject2.setIsRead();
                                    }
                                    obj6 = 1;
                                }
                            }
                            arrayList2.add(0, messageObject2);
                            if (i12 > this.messages.size()) {
                                i12 = this.messages.size();
                            }
                            this.messages.add(i12, messageObject2);
                            if (this.chatAdapter != null) {
                                this.chatAdapter.notifyItemChanged(i12);
                                this.chatAdapter.notifyItemInserted(i12);
                            }
                            if (!messageObject2.isOut() && messageObject2.messageOwner.mentioned && messageObject2.isContentUnread()) {
                                this.newMentionsCount++;
                            }
                            this.newUnreadMessageCount++;
                            if (messageObject2.type == 10 || messageObject2.type == 11) {
                                r13 = r8;
                                i3 = 1;
                                hashMap3 = hashMap6;
                            } else {
                                hashMap3 = hashMap6;
                                r13 = r8;
                                r6 = obj4;
                            }
                        }
                        i10++;
                        obj4 = r6;
                        hashMap5 = hashMap3;
                    }
                    if (hashMap2 != null) {
                        MessagesController.getInstance().reloadWebPages(this.dialog_id, hashMap2);
                    }
                    if (hashMap5 != null) {
                        for (Entry value2 : hashMap5.entrySet()) {
                            groupedMessages = (GroupedMessages) value2.getValue();
                            i2 = groupedMessages.posArray.size();
                            ((GroupedMessages) value2.getValue()).calculate();
                            indexOf = groupedMessages.posArray.size();
                            if (indexOf - i2 > 0) {
                                i6 = this.messages.indexOf(groupedMessages.messages.get(groupedMessages.messages.size() - 1));
                                if (i6 >= 0) {
                                    this.chatAdapter.notifyItemRangeChanged(i6, indexOf);
                                }
                            }
                        }
                    }
                    if (this.progressView != null) {
                        this.progressView.setVisibility(4);
                    }
                    if (this.chatAdapter == null) {
                        this.scrollToTopOnResume = true;
                    } else if (obj5 != null) {
                        this.chatAdapter.updateRowWithMessageObject(this.unreadMessageObject);
                    }
                    if (this.chatListView == null || this.chatAdapter == null) {
                        this.scrollToTopOnResume = true;
                    } else {
                        indexOf = this.chatLayoutManager.findFirstVisibleItemPosition();
                        if (indexOf == -1) {
                            indexOf = 0;
                        }
                        if (indexOf == 0 || r9 != null) {
                            this.newUnreadMessageCount = 0;
                            if (!this.firstLoading) {
                                if (this.paused) {
                                    this.scrollToTopOnResume = true;
                                } else {
                                    this.forceScrollToTop = true;
                                    moveScrollToLastMessage();
                                }
                            }
                        } else {
                            if (!(this.newUnreadMessageCount == 0 || this.pagedownButtonCounter == null)) {
                                this.pagedownButtonCounter.setVisibility(0);
                                this.pagedownButtonCounter.setText(String.format("%d", new Object[]{Integer.valueOf(this.newUnreadMessageCount)}));
                            }
                            showPagedownButton(true, true);
                        }
                        if (!(this.newMentionsCount == 0 || this.mentiondownButtonCounter == null)) {
                            this.mentiondownButtonCounter.setVisibility(0);
                            this.mentiondownButtonCounter.setText(String.format("%d", new Object[]{Integer.valueOf(this.newMentionsCount)}));
                            showMentiondownButton(true, true);
                        }
                    }
                    if (obj6 != null) {
                        if (this.paused) {
                            this.readWhenResume = true;
                            this.readWithDate = this.maxDate[0];
                            this.readWithMid = this.minMessageId[0];
                        } else {
                            MessagesController.getInstance().markDialogAsRead(this.dialog_id, ((MessageObject) this.messages.get(0)).getId(), this.minMessageId[0], this.maxDate[0], true, false);
                        }
                    }
                } else {
                    i12 = Integer.MIN_VALUE;
                    i6 = Integer.MIN_VALUE;
                    if (this.currentEncryptedChat != null) {
                        i6 = Integer.MAX_VALUE;
                    }
                    i5 = i6;
                    r13 = null;
                    Object obj7 = null;
                    r8 = null;
                    i2 = 0;
                    while (i2 < arrayList.size()) {
                        messageObject = (MessageObject) arrayList.get(i2);
                        if (this.currentUser != null && ((this.currentUser.bot && messageObject.isOut()) || this.currentUser.id == r17)) {
                            messageObject.setIsRead();
                        }
                        if (!(this.avatarContainer == null || this.currentEncryptedChat == null || messageObject.messageOwner.action == null || !(messageObject.messageOwner.action instanceof TLRPC$TL_messageEncryptedAction) || !(messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL))) {
                            this.avatarContainer.setTime(((TLRPC$TL_decryptedMessageActionSetMessageTTL) messageObject.messageOwner.action.encryptedAction).ttl_seconds);
                        }
                        if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatMigrateTo) {
                            final Bundle bundle2 = new Bundle();
                            bundle2.putInt("chat_id", messageObject.messageOwner.action.channel_id);
                            baseFragment = this.parentLayout.fragmentsStack.size() > 0 ? (BaseFragment) this.parentLayout.fragmentsStack.get(this.parentLayout.fragmentsStack.size() - 1) : null;
                            i6 = messageObject.messageOwner.action.channel_id;
                            AndroidUtilities.runOnUIThread(new Runnable() {

                                /* renamed from: org.telegram.ui.ChatActivity$93$1 */
                                class C42721 implements Runnable {
                                    C42721() {
                                    }

                                    public void run() {
                                        MessagesController.getInstance().loadFullChat(i6, 0, true);
                                    }
                                }

                                public void run() {
                                    ActionBarLayout actionBarLayout = ChatActivity.this.parentLayout;
                                    if (baseFragment != null) {
                                        NotificationCenter.getInstance().removeObserver(baseFragment, NotificationCenter.closeChats);
                                    }
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                    actionBarLayout.presentFragment(new ChatActivity(bundle2), true);
                                    AndroidUtilities.runOnUIThread(new C42721(), 1000);
                                }
                            });
                            return;
                        }
                        obj5 = (this.currentChat != null && this.currentChat.megagroup && ((messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatAddUser) || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatDeleteUser))) ? 1 : r13;
                        if (messageObject.isOut() && messageObject.isSending()) {
                            scrollToLastMessage(false);
                            return;
                        }
                        if (messageObject.type >= 0) {
                            if (this.messagesDict[0].containsKey(Integer.valueOf(messageObject.getId()))) {
                                r6 = r8;
                                r8 = obj7;
                            } else {
                                messageObject.checkLayout();
                                i12 = Math.max(i12, messageObject.messageOwner.date);
                                if (messageObject.getId() > 0) {
                                    i5 = Math.max(messageObject.getId(), i5);
                                    this.last_message_id = Math.max(this.last_message_id, messageObject.getId());
                                } else if (this.currentEncryptedChat != null) {
                                    i5 = Math.min(messageObject.getId(), i5);
                                    this.last_message_id = Math.min(this.last_message_id, messageObject.getId());
                                }
                                if (!messageObject.isOut() && messageObject.isUnread()) {
                                    this.unread_to_load++;
                                    r8 = 1;
                                }
                                if (messageObject.messageOwner.mentioned && messageObject.isContentUnread()) {
                                    this.newMentionsCount++;
                                }
                                this.newUnreadMessageCount++;
                                if (messageObject.type == 10 || messageObject.type == 11) {
                                    r6 = r8;
                                    i4 = 1;
                                }
                            }
                            i2++;
                            r13 = obj5;
                            obj7 = r8;
                            r8 = r6;
                        }
                        r6 = r8;
                        r8 = obj7;
                        i2++;
                        r13 = obj5;
                        obj7 = r8;
                        r8 = r6;
                    }
                    if (!(this.newUnreadMessageCount == 0 || this.pagedownButtonCounter == null)) {
                        this.pagedownButtonCounter.setVisibility(0);
                        this.pagedownButtonCounter.setText(String.format("%d", new Object[]{Integer.valueOf(this.newUnreadMessageCount)}));
                    }
                    if (!(this.newMentionsCount == 0 || this.mentiondownButtonCounter == null)) {
                        this.mentiondownButtonCounter.setVisibility(0);
                        this.mentiondownButtonCounter.setText(String.format("%d", new Object[]{Integer.valueOf(this.newMentionsCount)}));
                        showMentiondownButton(true, true);
                    }
                    if (r8 != null) {
                        if (this.paused) {
                            this.readWhenResume = true;
                            this.readWithDate = i12;
                            this.readWithMid = i5;
                        } else if (this.messages.size() > 0) {
                            MessagesController.getInstance().markDialogAsRead(this.dialog_id, ((MessageObject) this.messages.get(0)).getId(), i5, i12, true, false);
                        }
                    }
                    updateVisibleRows();
                    obj4 = obj7;
                }
                if (!(this.messages.isEmpty() || this.botUser == null || this.botUser.length() != 0)) {
                    this.botUser = null;
                    updateBottomOverlay();
                }
                if (obj4 != null) {
                    updateTitle();
                    checkAndUpdateAvatar();
                }
                if (r13 != null) {
                    MessagesController.getInstance().loadFullChat(this.currentChat.id, 0, true);
                }
            }
        } else if (i == NotificationCenter.closeChats) {
            if (objArr == null || objArr.length <= 0) {
                removeSelfFromStack();
            } else if (((Long) objArr[0]).longValue() == this.dialog_id) {
                finishFragment();
            }
        } else if (i == NotificationCenter.messagesRead) {
            r4 = (SparseArray) objArr[0];
            SparseArray sparseArray = (SparseArray) objArr[1];
            r8 = null;
            i2 = 0;
            while (i2 < r4.size()) {
                i5 = r4.keyAt(i2);
                r10 = ((Long) r4.get(i5)).longValue();
                if (((long) i5) != this.dialog_id) {
                    i2++;
                } else {
                    for (i2 = 0; i2 < this.messages.size(); i2++) {
                        messageObject2 = (MessageObject) this.messages.get(i2);
                        if (!messageObject2.isOut() && messageObject2.getId() > 0 && messageObject2.getId() <= ((int) r10)) {
                            if (!messageObject2.isUnread()) {
                                break;
                            }
                            messageObject2.setIsRead();
                            r8 = 1;
                        }
                    }
                    while (i2 < sparseArray.size()) {
                        i5 = sparseArray.keyAt(i2);
                        i12 = (int) ((Long) sparseArray.get(i5)).longValue();
                        if (((long) i5) == this.dialog_id) {
                        } else {
                            for (i3 = 0; i3 < this.messages.size(); i3++) {
                                messageObject = (MessageObject) this.messages.get(i3);
                                if (messageObject.isOut() && messageObject.getId() > 0 && messageObject.getId() <= i12) {
                                    if (!messageObject.isUnread()) {
                                        break;
                                    }
                                    messageObject.setIsRead();
                                    r8 = 1;
                                }
                            }
                            if (r4.size() != 0) {
                                removeUnreadPlane();
                            }
                            if (r8 == null) {
                                updateVisibleRows();
                            }
                        }
                    }
                    if (r4.size() != 0) {
                        removeUnreadPlane();
                    }
                    if (r8 == null) {
                        updateVisibleRows();
                    }
                }
            }
            for (i2 = 0; i2 < sparseArray.size(); i2++) {
                i5 = sparseArray.keyAt(i2);
                i12 = (int) ((Long) sparseArray.get(i5)).longValue();
                if (((long) i5) == this.dialog_id) {
                    for (i3 = 0; i3 < this.messages.size(); i3++) {
                        messageObject = (MessageObject) this.messages.get(i3);
                        if (!messageObject.isUnread()) {
                            break;
                        }
                        messageObject.setIsRead();
                        r8 = 1;
                    }
                    if (r4.size() != 0) {
                        removeUnreadPlane();
                    }
                    if (r8 == null) {
                        updateVisibleRows();
                    }
                }
            }
            if (r4.size() != 0) {
                removeUnreadPlane();
            }
            if (r8 == null) {
                updateVisibleRows();
            }
        } else if (i == NotificationCenter.historyCleared) {
            if (((Long) objArr[0]).longValue() == this.dialog_id) {
                i2 = ((Integer) objArr[1]).intValue();
                r18 = null;
                i6 = 0;
                while (i6 < this.messages.size()) {
                    r4 = (MessageObject) this.messages.get(i6);
                    i4 = r4.getId();
                    if (i4 <= 0) {
                        indexOf = i6;
                        obj = r18;
                    } else if (i4 > i2) {
                        indexOf = i6;
                        obj = r18;
                    } else {
                        if (this.info != null && this.info.pinned_msg_id == i4) {
                            this.pinnedMessageObject = null;
                            this.info.pinned_msg_id = 0;
                            MessagesStorage.getInstance().updateChannelPinnedMessage(this.info.id, 0);
                            updatePinnedMessageView(true);
                        }
                        this.messages.remove(i6);
                        i3 = i6 - 1;
                        this.messagesDict[0].remove(Integer.valueOf(i4));
                        arrayList2 = (ArrayList) this.messagesByDays.get(r4.dateKey);
                        if (arrayList2 != null) {
                            arrayList2.remove(r4);
                            if (arrayList2.isEmpty()) {
                                this.messagesByDays.remove(r4.dateKey);
                                if (i3 >= 0 && i3 < this.messages.size()) {
                                    this.messages.remove(i3);
                                    indexOf = i3 - 1;
                                    i6 = 1;
                                }
                            }
                        }
                        indexOf = i3;
                        i6 = 1;
                    }
                    r18 = obj;
                    i6 = indexOf + 1;
                }
                if (this.messages.isEmpty()) {
                    if (this.endReached[0] || this.loading) {
                        if (this.botButtons != null) {
                            this.botButtons = null;
                            if (this.chatActivityEnterView != null) {
                                this.chatActivityEnterView.setButtons(null, false);
                            }
                        }
                        if (this.currentEncryptedChat == null && this.currentUser != null && this.currentUser.bot && this.botUser == null) {
                            this.botUser = TtmlNode.ANONYMOUS_REGION_ID;
                            updateBottomOverlay();
                        }
                    } else {
                        if (this.progressView != null) {
                            this.progressView.setVisibility(4);
                        }
                        if (this.chatListView != null) {
                            this.chatListView.setEmptyView(null);
                        }
                        if (this.currentEncryptedChat == null) {
                            r4 = this.maxMessageId;
                            this.maxMessageId[1] = Integer.MAX_VALUE;
                            r4[0] = Integer.MAX_VALUE;
                            r4 = this.minMessageId;
                            this.minMessageId[1] = Integer.MIN_VALUE;
                            r4[0] = Integer.MIN_VALUE;
                        } else {
                            r4 = this.maxMessageId;
                            this.maxMessageId[1] = Integer.MIN_VALUE;
                            r4[0] = Integer.MIN_VALUE;
                            r4 = this.minMessageId;
                            this.minMessageId[1] = Integer.MAX_VALUE;
                            r4[0] = Integer.MAX_VALUE;
                        }
                        r4 = this.maxDate;
                        this.maxDate[1] = Integer.MIN_VALUE;
                        r4[0] = Integer.MIN_VALUE;
                        r4 = this.minDate;
                        this.minDate[1] = 0;
                        r4[0] = 0;
                        this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                        r5 = MessagesController.getInstance();
                        r6 = this.dialog_id;
                        r11 = !this.cacheEndReached[0];
                        r12 = this.minDate[0];
                        i8 = this.classGuid;
                        r16 = ChatObject.isChannel(this.currentChat);
                        r17 = this.lastLoadIndex;
                        this.lastLoadIndex = r17 + 1;
                        r5.loadMessages(r6, 30, 0, 0, r11, r12, i8, 0, 0, r16, r17);
                        this.loading = true;
                    }
                }
                if (!(r18 == null || this.chatAdapter == null)) {
                    removeUnreadPlane();
                    this.chatAdapter.notifyDataSetChanged();
                }
                if (this.isDownloadManager) {
                    try {
                        indexOf = this.messages.size();
                        if (indexOf != 0) {
                            indexOf--;
                        }
                        this.avatarContainer.setSubtitle(LocaleController.getString("downloadCount", R.string.downloadCount) + " : " + indexOf);
                    } catch (Exception e3) {
                    }
                }
            }
        } else if (i == NotificationCenter.messagesDeleted) {
            arrayList = (ArrayList) objArr[0];
            i11 = ((Integer) objArr[1]).intValue();
            if (ChatObject.isChannel(this.currentChat)) {
                if (i11 == 0 && this.mergeDialogId != 0) {
                    i4 = 1;
                } else if (i11 == this.currentChat.id) {
                    i4 = 0;
                } else {
                    return;
                }
            } else if (i11 == 0) {
                i4 = 0;
            } else {
                return;
            }
            r18 = null;
            HashMap hashMap7 = null;
            i12 = 0;
            while (i12 < arrayList.size()) {
                Integer num = (Integer) arrayList.get(i12);
                messageObject2 = (MessageObject) this.messagesDict[i4].get(num);
                if (i4 == 0 && this.info != null && this.info.pinned_msg_id == num.intValue()) {
                    this.pinnedMessageObject = null;
                    this.info.pinned_msg_id = 0;
                    MessagesStorage.getInstance().updateChannelPinnedMessage(i11, 0);
                    updatePinnedMessageView(true);
                }
                if (messageObject2 != null) {
                    r12 = this.messages.indexOf(messageObject2);
                    if (r12 != -1) {
                        messageObject4 = (MessageObject) this.messages.remove(r12);
                        if (messageObject4.getGroupId() != 0) {
                            GroupedMessages groupedMessages4 = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject4.getGroupId()));
                            if (groupedMessages4 != null) {
                                if (hashMap7 == null) {
                                    hashMap7 = new HashMap();
                                }
                                hashMap7.put(Long.valueOf(groupedMessages4.groupId), groupedMessages4);
                                groupedMessages4.messages.remove(messageObject2);
                            }
                        }
                        this.messagesDict[i4].remove(num);
                        arrayList2 = (ArrayList) this.messagesByDays.get(messageObject2.dateKey);
                        if (arrayList2 != null) {
                            arrayList2.remove(messageObject2);
                            if (arrayList2.isEmpty()) {
                                this.messagesByDays.remove(messageObject2.dateKey);
                                if (r12 >= 0 && r12 < this.messages.size()) {
                                    this.messages.remove(r12);
                                }
                            }
                        }
                        obj = 1;
                        i12++;
                        r18 = obj;
                    }
                }
                obj = r18;
                i12++;
                r18 = obj;
            }
            if (hashMap7 != null) {
                for (Entry value22 : hashMap7.entrySet()) {
                    r4 = (GroupedMessages) value22.getValue();
                    if (r4.messages.isEmpty()) {
                        this.groupedMessagesMap.remove(Long.valueOf(r4.groupId));
                    } else {
                        r4.calculate();
                        i6 = this.messages.indexOf((MessageObject) r4.messages.get(r4.messages.size() - 1));
                        if (i6 >= 0 && this.chatAdapter != null) {
                            this.chatAdapter.notifyItemRangeChanged(i6 + this.chatAdapter.messagesStartRow, r4.messages.size());
                        }
                    }
                }
            }
            if (this.messages.isEmpty()) {
                if (this.endReached[0] || this.loading) {
                    if (this.botButtons != null) {
                        this.botButtons = null;
                        if (this.chatActivityEnterView != null) {
                            this.chatActivityEnterView.setButtons(null, false);
                        }
                    }
                    if (this.currentEncryptedChat == null && this.currentUser != null && this.currentUser.bot && this.botUser == null) {
                        this.botUser = TtmlNode.ANONYMOUS_REGION_ID;
                        updateBottomOverlay();
                    }
                } else {
                    if (this.progressView != null) {
                        this.progressView.setVisibility(4);
                    }
                    if (this.chatListView != null) {
                        this.chatListView.setEmptyView(null);
                    }
                    if (this.currentEncryptedChat == null) {
                        r4 = this.maxMessageId;
                        this.maxMessageId[1] = Integer.MAX_VALUE;
                        r4[0] = Integer.MAX_VALUE;
                        r4 = this.minMessageId;
                        this.minMessageId[1] = Integer.MIN_VALUE;
                        r4[0] = Integer.MIN_VALUE;
                    } else {
                        r4 = this.maxMessageId;
                        this.maxMessageId[1] = Integer.MIN_VALUE;
                        r4[0] = Integer.MIN_VALUE;
                        r4 = this.minMessageId;
                        this.minMessageId[1] = Integer.MAX_VALUE;
                        r4[0] = Integer.MAX_VALUE;
                    }
                    r4 = this.maxDate;
                    this.maxDate[1] = Integer.MIN_VALUE;
                    r4[0] = Integer.MIN_VALUE;
                    r4 = this.minDate;
                    this.minDate[1] = 0;
                    r4[0] = 0;
                    this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                    r5 = MessagesController.getInstance();
                    r6 = this.dialog_id;
                    r11 = !this.cacheEndReached[0];
                    r12 = this.minDate[0];
                    i8 = this.classGuid;
                    r16 = ChatObject.isChannel(this.currentChat);
                    r17 = this.lastLoadIndex;
                    this.lastLoadIndex = r17 + 1;
                    r5.loadMessages(r6, 30, 0, 0, r11, r12, i8, 0, 0, r16, r17);
                    this.loading = true;
                }
            }
            if (this.chatAdapter == null) {
                return;
            }
            if (r18 != null) {
                removeUnreadPlane();
                this.chatAdapter.notifyDataSetChanged();
                return;
            }
            this.first_unread_id = 0;
            this.last_message_id = 0;
            this.createUnreadMessageAfterId = 0;
            this.unread_to_load = 0;
            removeMessageObject(this.unreadMessageObject);
            this.unreadMessageObject = null;
            if (this.pagedownButtonCounter != null) {
                this.pagedownButtonCounter.setVisibility(4);
            }
        } else if (i == NotificationCenter.messageReceivedByServer) {
            r4 = (Integer) objArr[0];
            messageObject = (MessageObject) this.messagesDict[0].get(r4);
            if (messageObject != null) {
                Integer num2 = (Integer) objArr[1];
                if (num2.equals(r4) || !this.messagesDict[0].containsKey(num2)) {
                    Message message = (Message) objArr[2];
                    boolean z7 = false;
                    r9 = null;
                    if (message != null) {
                        try {
                            r9 = (!messageObject.isForwarded() || ((messageObject.messageOwner.reply_markup != null || message.reply_markup == null) && messageObject.messageOwner.message.equals(message.message))) ? null : 1;
                            z2 = r9 != null || ((messageObject.messageOwner.params != null && messageObject.messageOwner.params.containsKey("query_id")) || !(message.media == null || messageObject.messageOwner.media == null || message.media.getClass().equals(messageObject.messageOwner.media.getClass())));
                            z7 = z2;
                        } catch (Throwable e4) {
                            Throwable th = e4;
                            r8 = null;
                            FileLog.e(th);
                            r9 = r8;
                        }
                        if (!(messageObject.getGroupId() == 0 || message.grouped_id == 0)) {
                            GroupedMessages groupedMessages5 = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject.getGroupId()));
                            if (groupedMessages5 != null) {
                                this.groupedMessagesMap.put(Long.valueOf(message.grouped_id), groupedMessages5);
                            }
                        }
                        messageObject.messageOwner = message;
                        messageObject.generateThumbs(true);
                        messageObject.setType();
                        if (message.media instanceof TLRPC$TL_messageMediaGame) {
                            messageObject.applyNewText();
                        }
                    }
                    if (r9 != null) {
                        messageObject.measureInlineBotButtons();
                    }
                    this.messagesDict[0].remove(r4);
                    this.messagesDict[0].put(num2, messageObject);
                    messageObject.messageOwner.id = num2.intValue();
                    messageObject.messageOwner.send_state = 0;
                    messageObject.forceUpdate = z7;
                    arrayList = new ArrayList();
                    arrayList.add(messageObject);
                    if (this.currentEncryptedChat == null) {
                        MessagesQuery.loadReplyMessagesForMessages(arrayList, this.dialog_id);
                    }
                    if (this.chatAdapter != null) {
                        this.chatAdapter.updateRowWithMessageObject(messageObject);
                    }
                    if (this.chatLayoutManager != null && z7 && this.chatLayoutManager.findFirstVisibleItemPosition() == 0) {
                        moveScrollToLastMessage();
                    }
                    NotificationsController.getInstance().playOutChatSound();
                } else {
                    r4 = (MessageObject) this.messagesDict[0].remove(r4);
                    if (r4 != null) {
                        i3 = this.messages.indexOf(r4);
                        this.messages.remove(i3);
                        arrayList = (ArrayList) this.messagesByDays.get(r4.dateKey);
                        arrayList.remove(messageObject);
                        if (arrayList.isEmpty()) {
                            this.messagesByDays.remove(messageObject.dateKey);
                            if (i3 >= 0 && i3 < this.messages.size()) {
                                this.messages.remove(i3);
                            }
                        }
                        if (this.chatAdapter != null) {
                            this.chatAdapter.notifyDataSetChanged();
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            if (this.isAdvancedForward) {
                this.chatActivityEnterView.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
                finishFragment();
            }
        } else if (i == NotificationCenter.messageReceivedByAck) {
            r4 = (MessageObject) this.messagesDict[0].get((Integer) objArr[0]);
            if (r4 != null) {
                r4.messageOwner.send_state = 0;
                if (this.chatAdapter != null) {
                    this.chatAdapter.updateRowWithMessageObject(r4);
                }
            }
        } else if (i == NotificationCenter.messageSendError) {
            r4 = (MessageObject) this.messagesDict[0].get((Integer) objArr[0]);
            if (r4 != null) {
                r4.messageOwner.send_state = 2;
                updateVisibleRows();
            }
        } else if (i == NotificationCenter.chatInfoDidLoaded) {
            ChatFull chatFull = (ChatFull) objArr[0];
            if (this.currentChat != null && chatFull.id == this.currentChat.id) {
                if (chatFull instanceof TL_channelFull) {
                    if (this.currentChat.megagroup) {
                        if (chatFull.participants != null) {
                            i2 = 0;
                            for (i3 = 0; i3 < chatFull.participants.participants.size(); i3++) {
                                i2 = Math.max(((ChatParticipant) chatFull.participants.participants.get(i3)).date, i2);
                            }
                        } else {
                            i2 = 0;
                        }
                        if (i2 == 0 || Math.abs((System.currentTimeMillis() / 1000) - ((long) i2)) > 3600) {
                            MessagesController.getInstance().loadChannelParticipants(Integer.valueOf(this.currentChat.id));
                        }
                    }
                    if (chatFull.participants == null && this.info != null) {
                        chatFull.participants = this.info.participants;
                    }
                }
                this.info = chatFull;
                if (this.chatActivityEnterView != null) {
                    this.chatActivityEnterView.setChatInfo(this.info);
                }
                if (this.mentionsAdapter != null) {
                    this.mentionsAdapter.setChatInfo(this.info);
                }
                if (objArr[3] instanceof MessageObject) {
                    this.pinnedMessageObject = (MessageObject) objArr[3];
                    updatePinnedMessageView(false);
                } else {
                    updatePinnedMessageView(true);
                }
                if (!(this.isDownloadManager || this.avatarContainer == null)) {
                    this.avatarContainer.updateOnlineCount();
                    this.avatarContainer.updateSubtitle();
                }
                if (this.isBroadcast) {
                    SendMessagesHelper.getInstance().setCurrentChatInfo(this.info);
                }
                if (this.info instanceof TL_chatFull) {
                    this.hasBotsCommands = false;
                    this.botInfo.clear();
                    this.botsCount = 0;
                    URLSpanBotCommand.enabled = false;
                    for (i6 = 0; i6 < this.info.participants.participants.size(); i6++) {
                        r4 = MessagesController.getInstance().getUser(Integer.valueOf(((ChatParticipant) this.info.participants.participants.get(i6)).user_id));
                        if (r4 != null && r4.bot) {
                            URLSpanBotCommand.enabled = true;
                            this.botsCount++;
                            BotQuery.loadBotInfo(r4.id, true, this.classGuid);
                        }
                    }
                    if (this.chatListView != null) {
                        this.chatListView.invalidateViews();
                    }
                } else if (this.info instanceof TL_channelFull) {
                    this.hasBotsCommands = false;
                    this.botInfo.clear();
                    this.botsCount = 0;
                    z3 = (this.info.bot_info.isEmpty() || this.currentChat == null || !this.currentChat.megagroup) ? false : true;
                    URLSpanBotCommand.enabled = z3;
                    this.botsCount = this.info.bot_info.size();
                    for (i6 = 0; i6 < this.info.bot_info.size(); i6++) {
                        r4 = (BotInfo) this.info.bot_info.get(i6);
                        if (!r4.commands.isEmpty() && (!ChatObject.isChannel(this.currentChat) || (this.currentChat != null && this.currentChat.megagroup))) {
                            this.hasBotsCommands = true;
                        }
                        this.botInfo.put(Integer.valueOf(r4.user_id), r4);
                    }
                    if (this.chatListView != null) {
                        this.chatListView.invalidateViews();
                    }
                    if (this.mentionsAdapter != null && (!ChatObject.isChannel(this.currentChat) || (this.currentChat != null && this.currentChat.megagroup))) {
                        this.mentionsAdapter.setBotInfo(this.botInfo);
                    }
                }
                if (this.chatActivityEnterView != null) {
                    this.chatActivityEnterView.setBotsCount(this.botsCount, this.hasBotsCommands);
                }
                if (this.mentionsAdapter != null) {
                    this.mentionsAdapter.setBotsCount(this.botsCount);
                }
                if (ChatObject.isChannel(this.currentChat) && this.mergeDialogId == 0 && this.info.migrated_from_chat_id != 0) {
                    this.mergeDialogId = (long) (-this.info.migrated_from_chat_id);
                    this.maxMessageId[1] = this.info.migrated_from_max_id;
                    if (this.chatAdapter != null) {
                        this.chatAdapter.notifyDataSetChanged();
                    }
                }
            }
        } else if (i == NotificationCenter.chatInfoCantLoad) {
            indexOf = ((Integer) objArr[0]).intValue();
            if (this.currentChat != null && this.currentChat.id == indexOf) {
                indexOf = ((Integer) objArr[1]).intValue();
                if (getParentActivity() != null && this.closeChatDialog == null) {
                    r5 = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
                    r5.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    if (indexOf == 0) {
                        r5.setMessage(LocaleController.getString("ChannelCantOpenPrivate", R.string.ChannelCantOpenPrivate));
                    } else if (indexOf == 1) {
                        r5.setMessage(LocaleController.getString("ChannelCantOpenNa", R.string.ChannelCantOpenNa));
                    } else if (indexOf == 2) {
                        r5.setMessage(LocaleController.getString("ChannelCantOpenBanned", R.string.ChannelCantOpenBanned));
                    }
                    r5.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                    Dialog create = r5.create();
                    this.closeChatDialog = create;
                    showDialog(create);
                    this.loading = false;
                    if (this.progressView != null) {
                        this.progressView.setVisibility(4);
                    }
                    if (this.chatAdapter != null) {
                        this.chatAdapter.notifyDataSetChanged();
                    }
                }
            }
        } else if (i == NotificationCenter.contactsDidLoaded) {
            updateContactStatus();
            if (this.currentEncryptedChat != null) {
                updateSpamView();
            }
            if (!this.isDownloadManager && this.avatarContainer != null) {
                this.avatarContainer.updateSubtitle();
            }
        } else if (i == NotificationCenter.encryptedChatUpdated) {
            EncryptedChat encryptedChat = (EncryptedChat) objArr[0];
            if (this.currentEncryptedChat != null && encryptedChat.id == this.currentEncryptedChat.id) {
                this.currentEncryptedChat = encryptedChat;
                updateContactStatus();
                updateSecretStatus();
                initStickers();
                if (this.chatActivityEnterView != null) {
                    ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
                    z3 = this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 23;
                    z = this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 46;
                    chatActivityEnterView.setAllowStickersAndGifs(z3, z);
                    this.chatActivityEnterView.checkRoundVideo();
                }
                if (this.mentionsAdapter != null) {
                    MentionsAdapter mentionsAdapter = this.mentionsAdapter;
                    z3 = !this.chatActivityEnterView.isEditingMessage() && (this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 46);
                    mentionsAdapter.setNeedBotContext(z3);
                }
            }
        } else if (i == NotificationCenter.messagesReadEncrypted) {
            indexOf = ((Integer) objArr[0]).intValue();
            if (this.currentEncryptedChat != null && this.currentEncryptedChat.id == indexOf) {
                i6 = ((Integer) objArr[1]).intValue();
                r6 = this.messages.iterator();
                while (r6.hasNext()) {
                    r4 = (MessageObject) r6.next();
                    if (r4.isOut()) {
                        if (r4.isOut() && !r4.isUnread()) {
                            break;
                        } else if (r4.messageOwner.date - 1 <= i6) {
                            r4.setIsRead();
                        }
                    }
                }
                updateVisibleRows();
            }
        } else if (i == NotificationCenter.removeAllMessagesFromDialog) {
            if (this.dialog_id == ((Long) objArr[0]).longValue()) {
                this.messages.clear();
                this.waitingForLoad.clear();
                this.messagesByDays.clear();
                this.groupedMessagesMap.clear();
                for (indexOf = 1; indexOf >= 0; indexOf--) {
                    this.messagesDict[indexOf].clear();
                    if (this.currentEncryptedChat == null) {
                        this.maxMessageId[indexOf] = Integer.MAX_VALUE;
                        this.minMessageId[indexOf] = Integer.MIN_VALUE;
                    } else {
                        this.maxMessageId[indexOf] = Integer.MIN_VALUE;
                        this.minMessageId[indexOf] = Integer.MAX_VALUE;
                    }
                    this.maxDate[indexOf] = Integer.MIN_VALUE;
                    this.minDate[indexOf] = 0;
                    this.selectedMessagesIds[indexOf].clear();
                    this.selectedMessagesCanCopyIds[indexOf].clear();
                    this.selectedMessagesCanStarIds[indexOf].clear();
                }
                this.cantDeleteMessagesCount = 0;
                this.canEditMessagesCount = 0;
                this.actionBar.hideActionMode();
                updatePinnedMessageView(true);
                if (this.botButtons != null) {
                    this.botButtons = null;
                    if (this.chatActivityEnterView != null) {
                        this.chatActivityEnterView.setButtons(null, false);
                    }
                }
                if (((Boolean) objArr[1]).booleanValue()) {
                    if (this.chatAdapter != null) {
                        this.progressView.setVisibility(this.chatAdapter.botInfoRow == -1 ? 0 : 4);
                        this.chatListView.setEmptyView(null);
                    }
                    for (indexOf = 0; indexOf < 2; indexOf++) {
                        this.endReached[indexOf] = false;
                        this.cacheEndReached[indexOf] = false;
                        this.forwardEndReached[indexOf] = true;
                    }
                    this.first = true;
                    this.firstLoading = true;
                    this.loading = true;
                    this.startLoadFromMessageId = 0;
                    this.needSelectFromMessageId = false;
                    this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                    r5 = MessagesController.getInstance();
                    r6 = this.dialog_id;
                    i4 = AndroidUtilities.isTablet() ? 30 : 20;
                    i8 = this.classGuid;
                    r16 = ChatObject.isChannel(this.currentChat);
                    r17 = this.lastLoadIndex;
                    this.lastLoadIndex = r17 + 1;
                    r5.loadMessages(r6, i4, 0, 0, true, 0, i8, 2, 0, r16, r17);
                } else if (this.progressView != null) {
                    this.progressView.setVisibility(4);
                    this.chatListView.setEmptyView(this.emptyViewContainer);
                }
                if (this.chatAdapter != null) {
                    this.chatAdapter.notifyDataSetChanged();
                }
                if (this.currentEncryptedChat == null && this.currentUser != null && this.currentUser.bot && this.botUser == null) {
                    this.botUser = TtmlNode.ANONYMOUS_REGION_ID;
                    updateBottomOverlay();
                }
            }
        } else if (i == NotificationCenter.screenshotTook) {
            updateInformationForScreenshotDetector();
        } else if (i == NotificationCenter.blockedUsersDidLoaded) {
            if (this.currentUser != null) {
                z3 = this.userBlocked;
                this.userBlocked = MessagesController.getInstance().blockedUsers.contains(Integer.valueOf(this.currentUser.id));
                if (z3 != this.userBlocked) {
                    updateBottomOverlay();
                }
            }
        } else if (i == NotificationCenter.FileNewChunkAvailable) {
            r4 = (MessageObject) objArr[0];
            r6 = ((Long) objArr[2]).longValue();
            if (r6 != 0 && this.dialog_id == r4.getDialogId()) {
                r4 = (MessageObject) this.messagesDict[0].get(Integer.valueOf(r4.getId()));
                if (r4 != null) {
                    r4.messageOwner.media.document.size = (int) r6;
                    updateVisibleRows();
                }
            }
        } else if (i == NotificationCenter.didCreatedNewDeleteTask) {
            r4 = (SparseArray) objArr[0];
            r6 = null;
            i2 = 0;
            while (i2 < r4.size()) {
                i11 = r4.keyAt(i2);
                arrayList2 = (ArrayList) r4.get(i11);
                r8 = r6;
                for (i5 = 0; i5 < arrayList2.size(); i5++) {
                    long longValue = ((Long) arrayList2.get(i5)).longValue();
                    if (i5 == 0) {
                        i3 = (int) (longValue >> 32);
                        if (i3 < 0) {
                            i3 = 0;
                        }
                        if (i3 != (ChatObject.isChannel(this.currentChat) ? this.currentChat.id : 0)) {
                            return;
                        }
                    }
                    messageObject2 = (MessageObject) this.messagesDict[0].get(Integer.valueOf((int) longValue));
                    if (messageObject2 != null) {
                        messageObject2.messageOwner.destroyTime = i11;
                        r8 = 1;
                    }
                }
                i2++;
                r6 = r8;
            }
            if (r6 != null) {
                updateVisibleRows();
            }
        } else if (i == NotificationCenter.messagePlayingDidStarted) {
            r4 = (MessageObject) objArr[0];
            if (r4.eventId == 0) {
                sendSecretMessageRead(r4);
                if (r4.isRoundVideo()) {
                    MediaController.getInstance().setTextureView(createTextureView(true), this.aspectRatioFrameLayout, this.roundVideoContainer, true);
                    updateTextureViewPosition();
                }
                if (this.chatListView != null) {
                    i3 = this.chatListView.getChildCount();
                    for (i6 = 0; i6 < i3; i6++) {
                        r4 = this.chatListView.getChildAt(i6);
                        if (r4 instanceof ChatMessageCell) {
                            r4 = (ChatMessageCell) r4;
                            messageObject4 = r4.getMessageObject();
                            if (messageObject4 != null) {
                                if (messageObject4.isVoice() || messageObject4.isMusic()) {
                                    r4.updateButtonState(false);
                                } else if (messageObject4.isRoundVideo()) {
                                    r4.checkRoundVideoPlayback(false);
                                }
                            }
                        }
                    }
                    i3 = this.mentionListView.getChildCount();
                    for (i6 = 0; i6 < i3; i6++) {
                        r4 = this.mentionListView.getChildAt(i6);
                        if (r4 instanceof ContextLinkCell) {
                            r4 = (ContextLinkCell) r4;
                            messageObject4 = r4.getMessageObject();
                            if (messageObject4 != null && (messageObject4.isVoice() || messageObject4.isMusic())) {
                                r4.updateButtonState(false);
                            }
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.messagePlayingDidReset || i == NotificationCenter.messagePlayingPlayStateChanged) {
            if (i == NotificationCenter.messagePlayingDidReset) {
                destroyTextureView();
            }
            if (this.chatListView != null) {
                i3 = this.chatListView.getChildCount();
                for (i6 = 0; i6 < i3; i6++) {
                    r4 = this.chatListView.getChildAt(i6);
                    if (r4 instanceof ChatMessageCell) {
                        r4 = (ChatMessageCell) r4;
                        messageObject4 = r4.getMessageObject();
                        if (messageObject4 != null) {
                            if (messageObject4.isVoice() || messageObject4.isMusic()) {
                                r4.updateButtonState(false);
                            } else if (messageObject4.isRoundVideo() && !MediaController.getInstance().isPlayingMessage(messageObject4)) {
                                r4.checkRoundVideoPlayback(true);
                            }
                        }
                    }
                }
                i3 = this.mentionListView.getChildCount();
                for (i6 = 0; i6 < i3; i6++) {
                    r4 = this.mentionListView.getChildAt(i6);
                    if (r4 instanceof ContextLinkCell) {
                        r4 = (ContextLinkCell) r4;
                        messageObject4 = r4.getMessageObject();
                        if (messageObject4 != null && (messageObject4.isVoice() || messageObject4.isMusic())) {
                            r4.updateButtonState(false);
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.messagePlayingProgressDidChanged) {
            r4 = (Integer) objArr[0];
            if (this.chatListView != null) {
                i2 = this.chatListView.getChildCount();
                for (i3 = 0; i3 < i2; i3++) {
                    findViewByPosition = this.chatListView.getChildAt(i3);
                    if (findViewByPosition instanceof ChatMessageCell) {
                        ChatMessageCell chatMessageCell = (ChatMessageCell) findViewByPosition;
                        MessageObject messageObject8 = chatMessageCell.getMessageObject();
                        if (messageObject8 != null && messageObject8.getId() == r4.intValue()) {
                            r4 = MediaController.getInstance().getPlayingMessageObject();
                            if (r4 != null) {
                                messageObject8.audioProgress = r4.audioProgress;
                                messageObject8.audioProgressSec = r4.audioProgressSec;
                                chatMessageCell.updatePlayingMessageProgress();
                                return;
                            }
                            return;
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.updateMessageMedia) {
            r4 = (Message) objArr[0];
            messageObject = (MessageObject) this.messagesDict[0].get(Integer.valueOf(r4.id));
            if (messageObject != null) {
                messageObject.messageOwner.media = r4.media;
                messageObject.messageOwner.attachPath = r4.attachPath;
                messageObject.generateThumbs(false);
                if (messageObject.getGroupId() != 0 && (messageObject.photoThumbs == null || messageObject.photoThumbs.isEmpty())) {
                    groupedMessages3 = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject.getGroupId()));
                    if (groupedMessages3 != null) {
                        i5 = groupedMessages3.messages.indexOf(messageObject);
                        if (i5 >= 0) {
                            i12 = groupedMessages3.messages.size();
                            r7 = null;
                            if (i5 > 0 && i5 < groupedMessages3.messages.size() - 1) {
                                groupedMessages2 = new GroupedMessages();
                                groupedMessages2.groupId = Utilities.random.nextLong();
                                groupedMessages2.messages.addAll(groupedMessages3.messages.subList(i5 + 1, groupedMessages3.messages.size()));
                                for (i4 = 0; i4 < groupedMessages2.messages.size(); i4++) {
                                    ((MessageObject) groupedMessages2.messages.get(i4)).localGroupId = groupedMessages2.groupId;
                                    groupedMessages3.messages.remove(i5 + 1);
                                }
                                this.groupedMessagesMap.put(Long.valueOf(groupedMessages2.groupId), groupedMessages2);
                                r7 = (MessageObject) groupedMessages2.messages.get(groupedMessages2.messages.size() - 1);
                                groupedMessages2.calculate();
                            }
                            groupedMessages3.messages.remove(i5);
                            if (r7 == null) {
                                r7 = (MessageObject) groupedMessages3.messages.get(groupedMessages3.messages.size() - 1);
                            }
                            if (groupedMessages3.messages.isEmpty()) {
                                this.groupedMessagesMap.remove(Long.valueOf(groupedMessages3.groupId));
                            } else {
                                groupedMessages3.calculate();
                                i3 = this.messages.indexOf(r7);
                                if (i3 >= 0 && this.chatAdapter != null) {
                                    this.chatAdapter.notifyItemRangeChanged(i3 + this.chatAdapter.messagesStartRow, i12);
                                }
                            }
                        }
                    }
                }
                if (r4.media.ttl_seconds == 0 || !((r4.media.photo instanceof TLRPC$TL_photoEmpty) || (r4.media.document instanceof TLRPC$TL_documentEmpty))) {
                    updateVisibleRows();
                    return;
                }
                messageObject.setType();
                this.chatAdapter.updateRowWithMessageObject(messageObject);
            }
        } else if (i == NotificationCenter.replaceMessagesObjects) {
            long longValue2 = ((Long) objArr[0]).longValue();
            if (longValue2 == this.dialog_id || longValue2 == this.mergeDialogId) {
                i12 = longValue2 == this.dialog_id ? 0 : 1;
                r9 = null;
                r8 = null;
                arrayList = (ArrayList) objArr[1];
                hashMap = null;
                i9 = 0;
                while (i9 < arrayList.size()) {
                    messageObject2 = (MessageObject) arrayList.get(i9);
                    messageObject4 = (MessageObject) this.messagesDict[i12].get(Integer.valueOf(messageObject2.getId()));
                    if (this.pinnedMessageObject != null && this.pinnedMessageObject.getId() == messageObject2.getId()) {
                        this.pinnedMessageObject = messageObject2;
                        updatePinnedMessageView(true);
                    }
                    if (messageObject4 != null) {
                        if (messageObject2.type >= 0) {
                            if (r8 == null && (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage)) {
                                r8 = 1;
                            }
                            if (messageObject4.replyMessageObject != null) {
                                messageObject2.replyMessageObject = messageObject4.replyMessageObject;
                                if (messageObject2.messageOwner.action instanceof TLRPC$TL_messageActionGameScore) {
                                    messageObject2.generateGameMessageText(null);
                                } else if (messageObject2.messageOwner.action instanceof TLRPC$TL_messageActionPaymentSent) {
                                    messageObject2.generatePaymentSentMessageText(null);
                                }
                            }
                            messageObject2.messageOwner.attachPath = messageObject4.messageOwner.attachPath;
                            messageObject2.attachPathExists = messageObject4.attachPathExists;
                            messageObject2.mediaExists = messageObject4.mediaExists;
                            this.messagesDict[i12].put(Integer.valueOf(messageObject4.getId()), messageObject2);
                            r13 = r8;
                        } else {
                            this.messagesDict[i12].remove(Integer.valueOf(messageObject4.getId()));
                            r13 = r8;
                        }
                        i10 = this.messages.indexOf(messageObject4);
                        if (i10 >= 0) {
                            ArrayList arrayList4 = (ArrayList) this.messagesByDays.get(messageObject4.dateKey);
                            int indexOf2 = arrayList4 != null ? arrayList4.indexOf(messageObject4) : -1;
                            if (messageObject4.getGroupId() != 0) {
                                GroupedMessages groupedMessages6 = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject4.getGroupId()));
                                if (groupedMessages6 != null) {
                                    r17 = groupedMessages6.messages.indexOf(messageObject4);
                                    if (r17 >= 0) {
                                        if (messageObject4.getGroupId() != messageObject2.getGroupId()) {
                                            this.groupedMessagesMap.put(Long.valueOf(messageObject2.getGroupId()), groupedMessages6);
                                        }
                                        if (messageObject2.photoThumbs == null || messageObject2.photoThumbs.isEmpty()) {
                                            if (hashMap == null) {
                                                hashMap = new HashMap();
                                            }
                                            hashMap.put(Long.valueOf(groupedMessages6.groupId), groupedMessages6);
                                            if (r17 > 0 && r17 < groupedMessages6.messages.size() - 1) {
                                                GroupedMessages groupedMessages7 = new GroupedMessages();
                                                groupedMessages7.groupId = Utilities.random.nextLong();
                                                groupedMessages7.messages.addAll(groupedMessages6.messages.subList(r17 + 1, groupedMessages6.messages.size()));
                                                for (r12 = 0; r12 < groupedMessages7.messages.size(); r12++) {
                                                    ((MessageObject) groupedMessages7.messages.get(r12)).localGroupId = groupedMessages7.groupId;
                                                    groupedMessages6.messages.remove(r17 + 1);
                                                }
                                                hashMap.put(Long.valueOf(groupedMessages7.groupId), groupedMessages7);
                                                this.groupedMessagesMap.put(Long.valueOf(groupedMessages7.groupId), groupedMessages7);
                                            }
                                            groupedMessages6.messages.remove(r17);
                                        } else {
                                            groupedMessages6.messages.set(r17, messageObject2);
                                            GroupedMessagePosition groupedMessagePosition = (GroupedMessagePosition) groupedMessages6.positions.remove(messageObject4);
                                            if (groupedMessagePosition != null) {
                                                groupedMessages6.positions.put(messageObject2, groupedMessagePosition);
                                            }
                                        }
                                    }
                                }
                            }
                            if (messageObject2.type >= 0) {
                                this.messages.set(i10, messageObject2);
                                if (this.chatAdapter != null) {
                                    this.chatAdapter.notifyItemChanged(this.chatAdapter.messagesStartRow + i10);
                                }
                                if (indexOf2 >= 0) {
                                    arrayList4.set(indexOf2, messageObject2);
                                }
                            } else {
                                this.messages.remove(i10);
                                if (this.chatAdapter != null) {
                                    this.chatAdapter.notifyItemRemoved(this.chatAdapter.messagesStartRow + i10);
                                }
                                if (indexOf2 >= 0) {
                                    arrayList4.remove(indexOf2);
                                    if (arrayList4.isEmpty()) {
                                        this.messagesByDays.remove(messageObject4.dateKey);
                                        this.messages.remove(i10);
                                        this.chatAdapter.notifyItemRemoved(this.chatAdapter.messagesStartRow);
                                    }
                                }
                            }
                            r6 = 1;
                            obj = r13;
                        } else {
                            obj = r13;
                            r6 = r9;
                        }
                    } else {
                        obj = r8;
                        r6 = r9;
                    }
                    i9++;
                    r8 = obj;
                    r9 = r6;
                }
                if (hashMap != null) {
                    for (Entry value222 : hashMap.entrySet()) {
                        r4 = (GroupedMessages) value222.getValue();
                        if (r4.messages.isEmpty()) {
                            this.groupedMessagesMap.remove(Long.valueOf(r4.groupId));
                        } else {
                            r4.calculate();
                            i6 = this.messages.indexOf((MessageObject) r4.messages.get(r4.messages.size() - 1));
                            if (i6 >= 0 && this.chatAdapter != null) {
                                this.chatAdapter.notifyItemRangeChanged(i6 + this.chatAdapter.messagesStartRow, r4.messages.size());
                            }
                        }
                    }
                }
                if (r9 != null && this.chatLayoutManager != null && r8 != null && this.chatLayoutManager.findFirstVisibleItemPosition() == 0) {
                }
            }
        } else if (i == NotificationCenter.notificationsSettingsUpdated) {
            updateTitleIcons();
            if (ChatObject.isChannel(this.currentChat)) {
                updateBottomOverlay();
            }
        } else if (i == NotificationCenter.didLoadedReplyMessages) {
            if (((Long) objArr[0]).longValue() == this.dialog_id) {
                updateVisibleRows();
            }
        } else if (i == NotificationCenter.didLoadedPinnedMessage) {
            r4 = (MessageObject) objArr[0];
            if (r4.getDialogId() == this.dialog_id && this.info != null && this.info.pinned_msg_id == r4.getId()) {
                this.pinnedMessageObject = r4;
                this.loadingPinnedMessage = 0;
                updatePinnedMessageView(true);
            }
        } else if (i == NotificationCenter.didReceivedWebpages) {
            arrayList = (ArrayList) objArr[0];
            i2 = 0;
            r8 = null;
            while (i2 < arrayList.size()) {
                tLRPC$TL_message = (Message) arrayList.get(i2);
                r10 = MessageObject.getDialogId(tLRPC$TL_message);
                if (r10 == this.dialog_id || r10 == this.mergeDialogId) {
                    messageObject2 = (MessageObject) this.messagesDict[r10 == this.dialog_id ? 0 : 1].get(Integer.valueOf(tLRPC$TL_message.id));
                    if (messageObject2 != null) {
                        messageObject2.messageOwner.media = new TLRPC$TL_messageMediaWebPage();
                        messageObject2.messageOwner.media.webpage = tLRPC$TL_message.media.webpage;
                        messageObject2.generateThumbs(true);
                        i3 = 1;
                    } else {
                        r6 = r8;
                    }
                } else {
                    r6 = r8;
                }
                i2++;
                r8 = r6;
            }
            if (r8 != null) {
                updateVisibleRows();
                if (this.chatLayoutManager != null && this.chatLayoutManager.findFirstVisibleItemPosition() == 0) {
                    moveScrollToLastMessage();
                }
            }
        } else if (i == NotificationCenter.didReceivedWebpagesInUpdates) {
            if (this.foundWebPage != null) {
                for (TLRPC$WebPage tLRPC$WebPage : ((HashMap) objArr[0]).values()) {
                    if (tLRPC$WebPage.id == this.foundWebPage.id) {
                        showReplyPanel(!(tLRPC$WebPage instanceof TLRPC$TL_webPageEmpty), null, null, tLRPC$WebPage, false);
                        return;
                    }
                }
            }
        } else if (i == NotificationCenter.messagesReadContent) {
            arrayList = (ArrayList) objArr[0];
            i3 = ChatObject.isChannel(this.currentChat) ? this.currentChat.id : 0;
            r8 = null;
            for (i2 = 0; i2 < arrayList.size(); i2++) {
                r10 = ((Long) arrayList.get(i2)).longValue();
                i6 = (int) (r10 >> 32);
                if (i6 < 0) {
                    i6 = 0;
                }
                if (i6 == i3) {
                    messageObject = (MessageObject) this.messagesDict[0].get(Integer.valueOf((int) r10));
                    if (messageObject != null) {
                        messageObject.setContentIsRead();
                        r8 = 1;
                        if (messageObject.messageOwner.mentioned) {
                            this.newMentionsCount--;
                            if (this.newMentionsCount <= 0) {
                                this.newMentionsCount = 0;
                                this.hasAllMentionsLocal = true;
                                showMentiondownButton(false, true);
                            } else {
                                this.mentiondownButtonCounter.setText(String.format("%d", new Object[]{Integer.valueOf(this.newMentionsCount)}));
                            }
                        }
                    }
                }
            }
            if (r8 != null) {
                updateVisibleRows();
            }
        } else if (i == NotificationCenter.botInfoDidLoaded) {
            if (this.classGuid == ((Integer) objArr[1]).intValue()) {
                r4 = (BotInfo) objArr[0];
                if (this.currentEncryptedChat == null) {
                    if (!(r4.commands.isEmpty() || ChatObject.isChannel(this.currentChat))) {
                        this.hasBotsCommands = true;
                    }
                    this.botInfo.put(Integer.valueOf(r4.user_id), r4);
                    if (this.chatAdapter != null) {
                        this.chatAdapter.notifyItemChanged(this.chatAdapter.botInfoRow);
                    }
                    if (this.mentionsAdapter != null && (!ChatObject.isChannel(this.currentChat) || (this.currentChat != null && this.currentChat.megagroup))) {
                        this.mentionsAdapter.setBotInfo(this.botInfo);
                    }
                    if (this.chatActivityEnterView != null) {
                        this.chatActivityEnterView.setBotsCount(this.botsCount, this.hasBotsCommands);
                    }
                }
                updateBotButtons();
            }
        } else if (i == NotificationCenter.botKeyboardDidLoaded) {
            if (this.dialog_id == ((Long) objArr[1]).longValue()) {
                r4 = (Message) objArr[0];
                if (r4 == null || this.userBlocked) {
                    this.botButtons = null;
                    if (this.chatActivityEnterView != null) {
                        if (this.replyingMessageObject != null && this.botReplyButtons == this.replyingMessageObject) {
                            this.botReplyButtons = null;
                            showReplyPanel(false, null, null, null, false);
                        }
                        this.chatActivityEnterView.setButtons(this.botButtons);
                        return;
                    }
                    return;
                }
                this.botButtons = new MessageObject(r4, null, false);
                if (this.chatActivityEnterView == null) {
                    return;
                }
                if (!(this.botButtons.messageOwner.reply_markup instanceof TLRPC$TL_replyKeyboardForceReply)) {
                    if (this.replyingMessageObject != null && this.botReplyButtons == this.replyingMessageObject) {
                        this.botReplyButtons = null;
                        showReplyPanel(false, null, null, null, false);
                    }
                    this.chatActivityEnterView.setButtons(this.botButtons);
                } else if (ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("answered_" + this.dialog_id, 0) == this.botButtons.getId()) {
                } else {
                    if (this.replyingMessageObject == null || this.chatActivityEnterView.getFieldText() == null) {
                        this.botReplyButtons = this.botButtons;
                        this.chatActivityEnterView.setButtons(this.botButtons);
                        showReplyPanel(true, this.botButtons, null, null, false);
                    }
                }
            }
        } else if (i == NotificationCenter.chatSearchResultsAvailable) {
            if (this.classGuid == ((Integer) objArr[0]).intValue()) {
                i6 = ((Integer) objArr[1]).intValue();
                long longValue3 = ((Long) objArr[3]).longValue();
                if (i6 != 0) {
                    scrollToMessageId(i6, 0, true, longValue3 == this.dialog_id ? 0 : 1, false);
                }
                updateSearchButtons(((Integer) objArr[2]).intValue(), ((Integer) objArr[4]).intValue(), ((Integer) objArr[5]).intValue());
                if (this.searchItem != null) {
                    this.searchItem.setShowSearchProgress(false);
                }
            }
        } else if (i == NotificationCenter.chatSearchResultsLoading) {
            if (this.classGuid == ((Integer) objArr[0]).intValue() && this.searchItem != null) {
                this.searchItem.setShowSearchProgress(true);
            }
        } else if (i == NotificationCenter.didUpdatedMessagesViews) {
            SparseIntArray sparseIntArray = (SparseIntArray) ((SparseArray) objArr[0]).get((int) this.dialog_id);
            if (sparseIntArray != null) {
                r7 = null;
                for (i3 = 0; i3 < sparseIntArray.size(); i3++) {
                    i4 = sparseIntArray.keyAt(i3);
                    messageObject = (MessageObject) this.messagesDict[0].get(Integer.valueOf(i4));
                    if (messageObject != null) {
                        i4 = sparseIntArray.get(i4);
                        if (i4 > messageObject.messageOwner.views) {
                            messageObject.messageOwner.views = i4;
                            r7 = 1;
                        }
                    }
                }
                if (r7 != null) {
                    updateVisibleRows();
                }
            }
        } else if (i == NotificationCenter.peerSettingsDidLoaded) {
            if (((Long) objArr[0]).longValue() == this.dialog_id) {
                updateSpamView();
            }
        } else if (i == NotificationCenter.newDraftReceived) {
            if (((Long) objArr[0]).longValue() == this.dialog_id) {
                applyDraftMaybe(true);
            }
        } else if (i == NotificationCenter.userInfoDidLoaded) {
            r4 = (Integer) objArr[0];
            if (this.currentUser != null && this.currentUser.id == r4.intValue()) {
                TLRPC$TL_userFull tLRPC$TL_userFull = (TLRPC$TL_userFull) objArr[1];
                if (this.headerItem == null) {
                    return;
                }
                if (tLRPC$TL_userFull.phone_calls_available) {
                    this.headerItem.showSubItem(32);
                } else {
                    this.headerItem.hideSubItem(32);
                }
            }
        } else if (i == NotificationCenter.didSetNewWallpapper) {
            if (this.fragmentView != null) {
                ((SizeNotifierFrameLayout) this.fragmentView).setBackgroundImage(Theme.getCachedWallpaper());
                this.progressView2.getBackground().setColorFilter(Theme.colorFilter);
                if (this.emptyView != null) {
                    this.emptyView.getBackground().setColorFilter(Theme.colorFilter);
                }
                if (this.bigEmptyView != null) {
                    this.bigEmptyView.getBackground().setColorFilter(Theme.colorFilter);
                }
                this.chatListView.invalidateViews();
            }
        } else if (i == NotificationCenter.channelRightsUpdated) {
            r4 = (Chat) objArr[0];
            if (this.currentChat != null && r4.id == this.currentChat.id && this.chatActivityEnterView != null) {
                this.currentChat = r4;
                this.chatActivityEnterView.checkChannelRights();
                checkRaiseSensors();
                updateSecretStatus();
            }
        } else if (i == NotificationCenter.updateMentionsCount) {
            if (this.dialog_id == ((Long) objArr[0]).longValue()) {
                indexOf = ((Integer) objArr[1]).intValue();
                if (this.newMentionsCount > indexOf) {
                    this.newMentionsCount = indexOf;
                    if (this.newMentionsCount <= 0) {
                        this.newMentionsCount = 0;
                        this.hasAllMentionsLocal = true;
                        showMentiondownButton(false, true);
                        return;
                    }
                    this.mentiondownButtonCounter.setText(String.format("%d", new Object[]{Integer.valueOf(this.newMentionsCount)}));
                }
            }
        } else if (i == NotificationCenter.DownloadServiceStart) {
            this.bottomOverlayChatText.setText(LocaleController.getString("StopDownloadService", R.string.StopDownloadService));
            this.downloadManagerLoading.setVisibility(0);
        } else if (i == NotificationCenter.DownloadServiceStop) {
            this.bottomOverlayChatText.setText(LocaleController.getString("StartDownloadService", R.string.StartDownloadService));
            this.downloadManagerLoading.setVisibility(8);
        }
    }

    public void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
        if (this.forwardingMessage != null || !this.selectedMessagesIds[0].isEmpty() || !this.selectedMessagesIds[1].isEmpty()) {
            int i;
            ArrayList arrayList2 = new ArrayList();
            if (this.forwardingMessage != null) {
                if (this.forwardingMessageGroup != null) {
                    arrayList2.addAll(this.forwardingMessageGroup.messages);
                } else {
                    arrayList2.add(this.forwardingMessage);
                }
                this.forwardingMessage = null;
                this.forwardingMessageGroup = null;
            } else {
                for (int i2 = 1; i2 >= 0; i2--) {
                    ArrayList arrayList3 = new ArrayList(this.selectedMessagesIds[i2].keySet());
                    Collections.sort(arrayList3);
                    for (i = 0; i < arrayList3.size(); i++) {
                        Integer num = (Integer) arrayList3.get(i);
                        MessageObject messageObject = (MessageObject) this.selectedMessagesIds[i2].get(num);
                        if (messageObject != null && num.intValue() > 0) {
                            arrayList2.add(messageObject);
                        }
                    }
                    this.selectedMessagesCanCopyIds[i2].clear();
                    this.selectedMessagesCanStarIds[i2].clear();
                    this.selectedMessagesIds[i2].clear();
                }
                this.cantDeleteMessagesCount = 0;
                this.canEditMessagesCount = 0;
                this.actionBar.hideActionMode();
                updatePinnedMessageView(true);
            }
            if (arrayList.size() > 1 || ((Long) arrayList.get(0)).longValue() == ((long) UserConfig.getClientUserId()) || charSequence != null) {
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    long longValue = ((Long) arrayList.get(i3)).longValue();
                    if (charSequence != null) {
                        SendMessagesHelper.getInstance().sendMessage(charSequence.toString(), longValue, null, null, true, null, null, null);
                    }
                    SendMessagesHelper.getInstance().sendMessage(arrayList2, longValue);
                }
                dialogsActivity.finishFragment();
                return;
            }
            long longValue2 = ((Long) arrayList.get(0)).longValue();
            if (longValue2 != this.dialog_id) {
                i = (int) longValue2;
                int i4 = (int) (longValue2 >> 32);
                Bundle bundle = new Bundle();
                bundle.putBoolean("scrollToTopOnResume", this.scrollToTopOnResume);
                if (i == 0) {
                    bundle.putInt("enc_id", i4);
                } else if (i > 0) {
                    bundle.putInt("user_id", i);
                } else if (i < 0) {
                    bundle.putInt("chat_id", -i);
                }
                if (i == 0 || MessagesController.checkCanOpenChat(bundle, dialogsActivity)) {
                    BaseFragment chatActivity = new ChatActivity(bundle);
                    if (presentFragment(chatActivity, true)) {
                        chatActivity.showReplyPanel(true, null, arrayList2, null, false);
                        if (!AndroidUtilities.isTablet()) {
                            removeSelfFromStack();
                            return;
                        }
                        return;
                    }
                    dialogsActivity.finishFragment();
                    return;
                }
                return;
            }
            dialogsActivity.finishFragment();
            moveScrollToLastMessage();
            showReplyPanel(true, null, arrayList2, null, false);
            if (AndroidUtilities.isTablet()) {
                this.actionBar.hideActionMode();
                updatePinnedMessageView(true);
            }
            updateVisibleRows();
        }
    }

    public void didSelectLocation(MessageMedia messageMedia, int i) {
        SendMessagesHelper.getInstance().sendMessage(messageMedia, this.dialog_id, this.replyingMessageObject, null, null);
        moveScrollToLastMessage();
        if (i == 1) {
            showReplyPanel(false, null, null, null, false);
            DraftQuery.cleanDraft(this.dialog_id, true);
        }
        if (this.paused) {
            this.scrollToTopOnResume = true;
        }
    }

    public void dismissCurrentDialig() {
        if (this.chatAttachAlert == null || this.visibleDialog != this.chatAttachAlert) {
            super.dismissCurrentDialig();
            return;
        }
        this.chatAttachAlert.closeCamera(false);
        this.chatAttachAlert.dismissInternal();
        this.chatAttachAlert.hideCamera(true);
    }

    public boolean dismissDialogOnPause(Dialog dialog) {
        return dialog != this.chatAttachAlert && super.dismissDialogOnPause(dialog);
    }

    public boolean extendActionMode(Menu menu) {
        if (!(this.chatActivityEnterView.getSelectionLength() == 0 || menu.findItem(16908321) == null)) {
            if (VERSION.SDK_INT >= 23) {
                menu.removeItem(16908341);
            }
            CharSequence spannableStringBuilder = new SpannableStringBuilder(LocaleController.getString("Bold", R.string.Bold));
            spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), 0, spannableStringBuilder.length(), 33);
            menu.add(R.id.menu_groupbolditalic, R.id.menu_bold, 6, spannableStringBuilder);
            spannableStringBuilder = new SpannableStringBuilder(LocaleController.getString("Italic", R.string.Italic));
            spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/ritalic.ttf")), 0, spannableStringBuilder.length(), 33);
            menu.add(R.id.menu_groupbolditalic, R.id.menu_italic, 7, spannableStringBuilder);
            menu.add(R.id.menu_groupbolditalic, R.id.menu_regular, 8, LocaleController.getString("Regular", R.string.Regular));
        }
        return true;
    }

    public Chat getCurrentChat() {
        return this.currentChat;
    }

    public ChatFull getCurrentChatInfo() {
        return this.info;
    }

    public EncryptedChat getCurrentEncryptedChat() {
        return this.currentEncryptedChat;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public long getDialogId() {
        return this.dialog_id;
    }

    public MessageObject getMessageObject() {
        return this.messageObject;
    }

    public ThemeDescription[] getThemeDescriptions() {
        AnonymousClass124 anonymousClass124 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
                ChatActivity.this.updateVisibleRows();
            }
        };
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[338];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, 0, null, null, null, null, Theme.key_chat_wallpaper);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUBACKGROUND, null, null, null, null, Theme.key_actionBarDefaultSubmenuBackground);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUITEM, null, null, null, null, Theme.key_actionBarDefaultSubmenuItem);
        themeDescriptionArr[7] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[8] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[9] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[10] = new ThemeDescription(this.avatarContainer.getTitleTextView(), ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[11] = new ThemeDescription(this.avatarContainer.getSubtitleTextView(), ThemeDescription.FLAG_TEXTCOLOR, null, new Paint[]{Theme.chat_statusPaint, Theme.chat_statusRecordPaint}, null, null, Theme.key_actionBarDefaultSubtitle, null);
        themeDescriptionArr[12] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[13] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch);
        themeDescriptionArr[14] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder);
        themeDescriptionArr[15] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultIcon);
        themeDescriptionArr[16] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_BACKGROUND, null, null, null, null, Theme.key_actionBarActionModeDefault);
        themeDescriptionArr[17] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_TOPBACKGROUND, null, null, null, null, Theme.key_actionBarActionModeDefaultTop);
        themeDescriptionArr[18] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultSelector);
        themeDescriptionArr[19] = new ThemeDescription(this.selectedMessagesCountTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultIcon);
        themeDescriptionArr[20] = new ThemeDescription(this.actionModeTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultIcon);
        themeDescriptionArr[21] = new ThemeDescription(this.actionModeSubTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultIcon);
        themeDescriptionArr[22] = new ThemeDescription(this.avatarContainer.getTitleTextView(), 0, null, null, new Drawable[]{Theme.chat_muteIconDrawable}, null, Theme.key_chat_muteIcon);
        themeDescriptionArr[23] = new ThemeDescription(this.avatarContainer.getTitleTextView(), 0, null, null, new Drawable[]{Theme.chat_lockIconDrawable}, null, Theme.key_chat_lockIcon);
        themeDescriptionArr[24] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        themeDescriptionArr[25] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[26] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[27] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[28] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[29] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[30] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[31] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundPink);
        themeDescriptionArr[32] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageRed);
        themeDescriptionArr[33] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageOrange);
        themeDescriptionArr[34] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageViolet);
        themeDescriptionArr[35] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageGreen);
        themeDescriptionArr[36] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageCyan);
        themeDescriptionArr[37] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageBlue);
        themeDescriptionArr[38] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessagePink);
        themeDescriptionArr[39] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInDrawable, Theme.chat_msgInMediaDrawable}, null, Theme.key_chat_inBubble);
        themeDescriptionArr[40] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInSelectedDrawable, Theme.chat_msgInMediaSelectedDrawable}, null, Theme.key_chat_inBubbleSelected);
        themeDescriptionArr[41] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInShadowDrawable, Theme.chat_msgInMediaShadowDrawable}, null, Theme.key_chat_inBubbleShadow);
        themeDescriptionArr[42] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutDrawable, Theme.chat_msgOutMediaDrawable}, null, Theme.key_chat_outBubble);
        themeDescriptionArr[43] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutSelectedDrawable, Theme.chat_msgOutMediaSelectedDrawable}, null, Theme.key_chat_outBubbleSelected);
        themeDescriptionArr[44] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutShadowDrawable, Theme.chat_msgOutMediaShadowDrawable}, null, Theme.key_chat_outBubbleShadow);
        themeDescriptionArr[45] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{ChatActionCell.class}, Theme.chat_actionTextPaint, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[46] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_LINKCOLOR, new Class[]{ChatActionCell.class}, Theme.chat_actionTextPaint, null, null, Theme.key_chat_serviceLink);
        themeDescriptionArr[47] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_shareIconDrawable, Theme.chat_botInlineDrawable, Theme.chat_botLinkDrawalbe, Theme.chat_goIconDrawable}, null, Theme.key_chat_serviceIcon);
        themeDescriptionArr[48] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class, ChatActionCell.class}, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[49] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class, ChatActionCell.class}, null, null, null, Theme.key_chat_serviceBackgroundSelected);
        themeDescriptionArr[50] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_messageTextIn);
        themeDescriptionArr[51] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_messageTextOut);
        themeDescriptionArr[52] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_LINKCOLOR, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_messageLinkIn, null);
        themeDescriptionArr[53] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_LINKCOLOR, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_messageLinkOut, null);
        themeDescriptionArr[54] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutCheckDrawable, Theme.chat_msgOutHalfCheckDrawable}, null, Theme.key_chat_outSentCheck);
        themeDescriptionArr[55] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutCheckSelectedDrawable, Theme.chat_msgOutHalfCheckSelectedDrawable}, null, Theme.key_chat_outSentCheckSelected);
        themeDescriptionArr[56] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutClockDrawable}, null, Theme.key_chat_outSentClock);
        themeDescriptionArr[57] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutSelectedClockDrawable}, null, Theme.key_chat_outSentClockSelected);
        themeDescriptionArr[58] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInClockDrawable}, null, Theme.key_chat_inSentClock);
        themeDescriptionArr[59] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInSelectedClockDrawable}, null, Theme.key_chat_inSentClockSelected);
        themeDescriptionArr[60] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgMediaCheckDrawable, Theme.chat_msgMediaHalfCheckDrawable}, null, Theme.key_chat_mediaSentCheck);
        themeDescriptionArr[61] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgStickerHalfCheckDrawable, Theme.chat_msgStickerCheckDrawable, Theme.chat_msgStickerClockDrawable, Theme.chat_msgStickerViewsDrawable}, null, Theme.key_chat_serviceText);
        themeDescriptionArr[62] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgMediaClockDrawable}, null, Theme.key_chat_mediaSentClock);
        themeDescriptionArr[63] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutViewsDrawable}, null, Theme.key_chat_outViews);
        themeDescriptionArr[64] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutViewsSelectedDrawable}, null, Theme.key_chat_outViewsSelected);
        themeDescriptionArr[65] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInViewsDrawable}, null, Theme.key_chat_inViews);
        themeDescriptionArr[66] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInViewsSelectedDrawable}, null, Theme.key_chat_inViewsSelected);
        themeDescriptionArr[67] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgMediaViewsDrawable}, null, Theme.key_chat_mediaViews);
        themeDescriptionArr[68] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutMenuDrawable}, null, Theme.key_chat_outMenu);
        themeDescriptionArr[69] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutMenuSelectedDrawable}, null, Theme.key_chat_outMenuSelected);
        themeDescriptionArr[70] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInMenuDrawable}, null, Theme.key_chat_inMenu);
        themeDescriptionArr[71] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInMenuSelectedDrawable}, null, Theme.key_chat_inMenuSelected);
        themeDescriptionArr[72] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgMediaMenuDrawable}, null, Theme.key_chat_mediaMenu);
        themeDescriptionArr[73] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutInstantDrawable, Theme.chat_msgOutCallDrawable}, null, Theme.key_chat_outInstant);
        themeDescriptionArr[74] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutCallSelectedDrawable}, null, Theme.key_chat_outInstantSelected);
        themeDescriptionArr[75] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInInstantDrawable, Theme.chat_msgInCallDrawable}, null, Theme.key_chat_inInstant);
        themeDescriptionArr[76] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInCallSelectedDrawable}, null, Theme.key_chat_inInstantSelected);
        themeDescriptionArr[77] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgCallUpRedDrawable, Theme.chat_msgCallDownRedDrawable}, null, Theme.key_calls_callReceivedRedIcon);
        themeDescriptionArr[78] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgCallUpGreenDrawable, Theme.chat_msgCallDownGreenDrawable}, null, Theme.key_calls_callReceivedGreenIcon);
        themeDescriptionArr[79] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_msgErrorPaint, null, null, Theme.key_chat_sentError);
        themeDescriptionArr[80] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgErrorDrawable}, null, Theme.key_chat_sentErrorIcon);
        themeDescriptionArr[81] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, anonymousClass124, Theme.key_chat_selectedBackground);
        themeDescriptionArr[82] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_durationPaint, null, null, Theme.key_chat_previewDurationText);
        themeDescriptionArr[83] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_gamePaint, null, null, Theme.key_chat_previewGameText);
        themeDescriptionArr[84] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inPreviewInstantText);
        themeDescriptionArr[85] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outPreviewInstantText);
        themeDescriptionArr[86] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inPreviewInstantSelectedText);
        themeDescriptionArr[87] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outPreviewInstantSelectedText);
        themeDescriptionArr[88] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_deleteProgressPaint, null, null, Theme.key_chat_secretTimeText);
        themeDescriptionArr[89] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_stickerNameText);
        themeDescriptionArr[90] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_botButtonPaint, null, null, Theme.key_chat_botButtonText);
        themeDescriptionArr[91] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_botProgressPaint, null, null, Theme.key_chat_botProgress);
        themeDescriptionArr[92] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inForwardedNameText);
        themeDescriptionArr[93] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outForwardedNameText);
        themeDescriptionArr[94] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inViaBotNameText);
        themeDescriptionArr[95] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outViaBotNameText);
        themeDescriptionArr[96] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_stickerViaBotNameText);
        themeDescriptionArr[97] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inReplyLine);
        themeDescriptionArr[98] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outReplyLine);
        themeDescriptionArr[99] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_stickerReplyLine);
        themeDescriptionArr[100] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inReplyNameText);
        themeDescriptionArr[101] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outReplyNameText);
        themeDescriptionArr[102] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_stickerReplyNameText);
        themeDescriptionArr[103] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inReplyMessageText);
        themeDescriptionArr[104] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outReplyMessageText);
        themeDescriptionArr[105] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inReplyMediaMessageText);
        themeDescriptionArr[106] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outReplyMediaMessageText);
        themeDescriptionArr[107] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inReplyMediaMessageSelectedText);
        themeDescriptionArr[108] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outReplyMediaMessageSelectedText);
        themeDescriptionArr[109] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_stickerReplyMessageText);
        themeDescriptionArr[110] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inPreviewLine);
        themeDescriptionArr[111] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outPreviewLine);
        themeDescriptionArr[112] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inSiteNameText);
        themeDescriptionArr[113] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outSiteNameText);
        themeDescriptionArr[114] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inContactNameText);
        themeDescriptionArr[115] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outContactNameText);
        themeDescriptionArr[116] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inContactPhoneText);
        themeDescriptionArr[117] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outContactPhoneText);
        themeDescriptionArr[118] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_mediaProgress);
        themeDescriptionArr[119] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioProgress);
        themeDescriptionArr[120] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioProgress);
        themeDescriptionArr[121] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioSelectedProgress);
        themeDescriptionArr[122] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioSelectedProgress);
        themeDescriptionArr[123] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_mediaTimeText);
        themeDescriptionArr[124] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inTimeText);
        themeDescriptionArr[125] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outTimeText);
        themeDescriptionArr[126] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inTimeSelectedText);
        themeDescriptionArr[127] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_adminText);
        themeDescriptionArr[128] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_adminSelectedText);
        themeDescriptionArr[129] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outTimeSelectedText);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_HDMV_DTS] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioPerfomerText);
        themeDescriptionArr[131] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioPerfomerText);
        themeDescriptionArr[132] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioTitleText);
        themeDescriptionArr[133] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioTitleText);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_SPLICE_INFO] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioDurationText);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_E_AC3] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioDurationText);
        themeDescriptionArr[136] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioDurationSelectedText);
        themeDescriptionArr[137] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioDurationSelectedText);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_DTS] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioSeekbar);
        themeDescriptionArr[139] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioSeekbar);
        themeDescriptionArr[140] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioSeekbarSelected);
        themeDescriptionArr[141] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioSeekbarSelected);
        themeDescriptionArr[BuildConfig.VERSION_CODE] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioSeekbarFill);
        themeDescriptionArr[143] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioSeekbarFill);
        themeDescriptionArr[144] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVoiceSeekbar);
        themeDescriptionArr[145] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVoiceSeekbar);
        themeDescriptionArr[146] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVoiceSeekbarSelected);
        themeDescriptionArr[147] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVoiceSeekbarSelected);
        themeDescriptionArr[148] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVoiceSeekbarFill);
        themeDescriptionArr[149] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVoiceSeekbarFill);
        themeDescriptionArr[150] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileProgress);
        themeDescriptionArr[151] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileProgress);
        themeDescriptionArr[152] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileProgressSelected);
        themeDescriptionArr[153] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileProgressSelected);
        themeDescriptionArr[154] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileNameText);
        themeDescriptionArr[155] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileNameText);
        themeDescriptionArr[156] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileInfoText);
        themeDescriptionArr[157] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileInfoText);
        themeDescriptionArr[158] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileInfoSelectedText);
        themeDescriptionArr[159] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileInfoSelectedText);
        themeDescriptionArr[160] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileBackground);
        themeDescriptionArr[161] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileBackground);
        themeDescriptionArr[162] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileBackgroundSelected);
        themeDescriptionArr[163] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileBackgroundSelected);
        themeDescriptionArr[164] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVenueNameText);
        themeDescriptionArr[165] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVenueNameText);
        themeDescriptionArr[166] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVenueInfoText);
        themeDescriptionArr[167] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVenueInfoText);
        themeDescriptionArr[168] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVenueInfoSelectedText);
        themeDescriptionArr[169] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVenueInfoSelectedText);
        themeDescriptionArr[170] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_mediaInfoText);
        themeDescriptionArr[171] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_urlPaint, null, null, Theme.key_chat_linkSelectBackground);
        themeDescriptionArr[172] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_textSearchSelectionPaint, null, null, Theme.key_chat_textSelectBackground);
        themeDescriptionArr[173] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[0][0], Theme.chat_fileStatesDrawable[1][0], Theme.chat_fileStatesDrawable[2][0], Theme.chat_fileStatesDrawable[3][0], Theme.chat_fileStatesDrawable[4][0]}, null, Theme.key_chat_outLoader);
        themeDescriptionArr[174] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[0][0], Theme.chat_fileStatesDrawable[1][0], Theme.chat_fileStatesDrawable[2][0], Theme.chat_fileStatesDrawable[3][0], Theme.chat_fileStatesDrawable[4][0]}, null, Theme.key_chat_outBubble);
        themeDescriptionArr[175] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[0][1], Theme.chat_fileStatesDrawable[1][1], Theme.chat_fileStatesDrawable[2][1], Theme.chat_fileStatesDrawable[3][1], Theme.chat_fileStatesDrawable[4][1]}, null, Theme.key_chat_outLoaderSelected);
        themeDescriptionArr[176] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[0][1], Theme.chat_fileStatesDrawable[1][1], Theme.chat_fileStatesDrawable[2][1], Theme.chat_fileStatesDrawable[3][1], Theme.chat_fileStatesDrawable[4][1]}, null, Theme.key_chat_outBubbleSelected);
        themeDescriptionArr[177] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[5][0], Theme.chat_fileStatesDrawable[6][0], Theme.chat_fileStatesDrawable[7][0], Theme.chat_fileStatesDrawable[8][0], Theme.chat_fileStatesDrawable[9][0]}, null, Theme.key_chat_inLoader);
        themeDescriptionArr[178] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[5][0], Theme.chat_fileStatesDrawable[6][0], Theme.chat_fileStatesDrawable[7][0], Theme.chat_fileStatesDrawable[8][0], Theme.chat_fileStatesDrawable[9][0]}, null, Theme.key_chat_inBubble);
        themeDescriptionArr[179] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[5][1], Theme.chat_fileStatesDrawable[6][1], Theme.chat_fileStatesDrawable[7][1], Theme.chat_fileStatesDrawable[8][1], Theme.chat_fileStatesDrawable[9][1]}, null, Theme.key_chat_inLoaderSelected);
        themeDescriptionArr[180] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[5][1], Theme.chat_fileStatesDrawable[6][1], Theme.chat_fileStatesDrawable[7][1], Theme.chat_fileStatesDrawable[8][1], Theme.chat_fileStatesDrawable[9][1]}, null, Theme.key_chat_inBubbleSelected);
        themeDescriptionArr[181] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[0][0], Theme.chat_photoStatesDrawables[1][0], Theme.chat_photoStatesDrawables[2][0], Theme.chat_photoStatesDrawables[3][0]}, null, Theme.key_chat_mediaLoaderPhoto);
        themeDescriptionArr[182] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[0][0], Theme.chat_photoStatesDrawables[1][0], Theme.chat_photoStatesDrawables[2][0], Theme.chat_photoStatesDrawables[3][0]}, null, Theme.key_chat_mediaLoaderPhotoIcon);
        themeDescriptionArr[183] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[0][1], Theme.chat_photoStatesDrawables[1][1], Theme.chat_photoStatesDrawables[2][1], Theme.chat_photoStatesDrawables[3][1]}, null, Theme.key_chat_mediaLoaderPhotoSelected);
        themeDescriptionArr[184] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[0][1], Theme.chat_photoStatesDrawables[1][1], Theme.chat_photoStatesDrawables[2][1], Theme.chat_photoStatesDrawables[3][1]}, null, Theme.key_chat_mediaLoaderPhotoIconSelected);
        themeDescriptionArr[185] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[7][0], Theme.chat_photoStatesDrawables[8][0]}, null, Theme.key_chat_outLoaderPhoto);
        themeDescriptionArr[186] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[7][0], Theme.chat_photoStatesDrawables[8][0]}, null, Theme.key_chat_outLoaderPhotoIcon);
        themeDescriptionArr[187] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[7][1], Theme.chat_photoStatesDrawables[8][1]}, null, Theme.key_chat_outLoaderPhotoSelected);
        themeDescriptionArr[188] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[7][1], Theme.chat_photoStatesDrawables[8][1]}, null, Theme.key_chat_outLoaderPhotoIconSelected);
        themeDescriptionArr[PsExtractor.PRIVATE_STREAM_1] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[10][0], Theme.chat_photoStatesDrawables[11][0]}, null, Theme.key_chat_inLoaderPhoto);
        themeDescriptionArr[190] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[10][0], Theme.chat_photoStatesDrawables[11][0]}, null, Theme.key_chat_inLoaderPhotoIcon);
        themeDescriptionArr[191] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[10][1], Theme.chat_photoStatesDrawables[11][1]}, null, Theme.key_chat_inLoaderPhotoSelected);
        themeDescriptionArr[PsExtractor.AUDIO_STREAM] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[10][1], Theme.chat_photoStatesDrawables[11][1]}, null, Theme.key_chat_inLoaderPhotoIconSelected);
        themeDescriptionArr[193] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[9][0]}, null, Theme.key_chat_outFileIcon);
        themeDescriptionArr[194] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[9][1]}, null, Theme.key_chat_outFileSelectedIcon);
        themeDescriptionArr[195] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[12][0]}, null, Theme.key_chat_inFileIcon);
        themeDescriptionArr[196] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[12][1]}, null, Theme.key_chat_inFileSelectedIcon);
        themeDescriptionArr[197] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_contactDrawable[0]}, null, Theme.key_chat_inContactBackground);
        themeDescriptionArr[198] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_contactDrawable[0]}, null, Theme.key_chat_inContactIcon);
        themeDescriptionArr[199] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_contactDrawable[1]}, null, Theme.key_chat_outContactBackground);
        themeDescriptionArr[200] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_contactDrawable[1]}, null, Theme.key_chat_outContactIcon);
        themeDescriptionArr[201] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_locationDrawable[0]}, null, Theme.key_chat_inLocationBackground);
        themeDescriptionArr[202] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_locationDrawable[0]}, null, Theme.key_chat_inLocationIcon);
        themeDescriptionArr[203] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_locationDrawable[1]}, null, Theme.key_chat_outLocationBackground);
        themeDescriptionArr[204] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_locationDrawable[1]}, null, Theme.key_chat_outLocationIcon);
        themeDescriptionArr[205] = new ThemeDescription(this.mentionContainer, 0, null, Theme.chat_composeBackgroundPaint, null, null, Theme.key_chat_messagePanelBackground);
        themeDescriptionArr[206] = new ThemeDescription(this.mentionContainer, 0, null, null, new Drawable[]{Theme.chat_composeShadowDrawable}, null, Theme.key_chat_messagePanelShadow);
        themeDescriptionArr[207] = new ThemeDescription(this.searchContainer, 0, null, Theme.chat_composeBackgroundPaint, null, null, Theme.key_chat_messagePanelBackground);
        themeDescriptionArr[208] = new ThemeDescription(this.searchContainer, 0, null, null, new Drawable[]{Theme.chat_composeShadowDrawable}, null, Theme.key_chat_messagePanelShadow);
        themeDescriptionArr[209] = new ThemeDescription(this.bottomOverlay, 0, null, Theme.chat_composeBackgroundPaint, null, null, Theme.key_chat_messagePanelBackground);
        themeDescriptionArr[210] = new ThemeDescription(this.bottomOverlay, 0, null, null, new Drawable[]{Theme.chat_composeShadowDrawable}, null, Theme.key_chat_messagePanelShadow);
        themeDescriptionArr[211] = new ThemeDescription(this.bottomOverlayChat, 0, null, Theme.chat_composeBackgroundPaint, null, null, Theme.key_chat_messagePanelBackground);
        themeDescriptionArr[212] = new ThemeDescription(this.bottomOverlayChat, 0, null, null, new Drawable[]{Theme.chat_composeShadowDrawable}, null, Theme.key_chat_messagePanelShadow);
        themeDescriptionArr[213] = new ThemeDescription(this.chatActivityEnterView, 0, null, Theme.chat_composeBackgroundPaint, null, null, Theme.key_chat_messagePanelBackground);
        themeDescriptionArr[214] = new ThemeDescription(this.chatActivityEnterView, 0, null, null, new Drawable[]{Theme.chat_composeShadowDrawable}, null, Theme.key_chat_messagePanelShadow);
        themeDescriptionArr[215] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_BACKGROUND, new Class[]{ChatActivityEnterView.class}, new String[]{"audioVideoButtonContainer"}, null, null, null, Theme.key_chat_messagePanelBackground);
        themeDescriptionArr[216] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{ChatActivityEnterView.class}, new String[]{"messageEditText"}, null, null, null, Theme.key_chat_messagePanelText);
        themeDescriptionArr[217] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{ChatActivityEnterView.class}, new String[]{"recordSendText"}, null, null, null, Theme.key_chat_fieldOverlayText);
        themeDescriptionArr[218] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_HINTTEXTCOLOR, new Class[]{ChatActivityEnterView.class}, new String[]{"messageEditText"}, null, null, null, Theme.key_chat_messagePanelHint);
        themeDescriptionArr[219] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"sendButton"}, null, null, null, Theme.key_chat_messagePanelSend);
        themeDescriptionArr[220] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"emojiButton"}, null, null, null, Theme.key_chat_messagePanelIcons);
        themeDescriptionArr[221] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"botButton"}, null, null, null, Theme.key_chat_messagePanelIcons);
        themeDescriptionArr[222] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"notifyButton"}, null, null, null, Theme.key_chat_messagePanelIcons);
        themeDescriptionArr[223] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"attachButton"}, null, null, null, Theme.key_chat_messagePanelIcons);
        themeDescriptionArr[224] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"audioSendButton"}, null, null, null, Theme.key_chat_messagePanelIcons);
        themeDescriptionArr[225] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"videoSendButton"}, null, null, null, Theme.key_chat_messagePanelIcons);
        themeDescriptionArr[226] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{ChatActivityEnterView.class}, new String[]{"doneButtonImage"}, null, null, null, Theme.key_chat_editDoneIcon);
        themeDescriptionArr[227] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_BACKGROUND, new Class[]{ChatActivityEnterView.class}, new String[]{"recordedAudioPanel"}, null, null, null, Theme.key_chat_messagePanelBackground);
        themeDescriptionArr[228] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"micDrawable"}, null, null, null, Theme.key_chat_messagePanelVoicePressed);
        themeDescriptionArr[229] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"cameraDrawable"}, null, null, null, Theme.key_chat_messagePanelVoicePressed);
        themeDescriptionArr[230] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"sendDrawable"}, null, null, null, Theme.key_chat_messagePanelVoicePressed);
        themeDescriptionArr[231] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"lockDrawable"}, null, null, null, Theme.key_chat_messagePanelVoiceLock);
        themeDescriptionArr[232] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"lockTopDrawable"}, null, null, null, Theme.key_chat_messagePanelVoiceLock);
        themeDescriptionArr[233] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"lockArrowDrawable"}, null, null, null, Theme.key_chat_messagePanelVoiceLock);
        themeDescriptionArr[234] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"lockBackgroundDrawable"}, null, null, null, Theme.key_chat_messagePanelVoiceLockBackground);
        themeDescriptionArr[235] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"lockShadowDrawable"}, null, null, null, Theme.key_chat_messagePanelVoiceLockShadow);
        themeDescriptionArr[236] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{ChatActivityEnterView.class}, new String[]{"recordDeleteImageView"}, null, null, null, Theme.key_chat_messagePanelVoiceDelete);
        themeDescriptionArr[237] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatActivityEnterView.class}, new String[]{"recordedAudioBackground"}, null, null, null, Theme.key_chat_recordedVoiceBackground);
        themeDescriptionArr[238] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{ChatActivityEnterView.class}, new String[]{"recordTimeText"}, null, null, null, Theme.key_chat_recordTime);
        themeDescriptionArr[239] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_BACKGROUND, new Class[]{ChatActivityEnterView.class}, new String[]{"recordTimeContainer"}, null, null, null, Theme.key_chat_messagePanelBackground);
        themeDescriptionArr[PsExtractor.VIDEO_STREAM_MASK] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{ChatActivityEnterView.class}, new String[]{"recordCancelText"}, null, null, null, Theme.key_chat_recordVoiceCancel);
        themeDescriptionArr[241] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_BACKGROUND, new Class[]{ChatActivityEnterView.class}, new String[]{"recordPanel"}, null, null, null, Theme.key_chat_messagePanelBackground);
        themeDescriptionArr[242] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{ChatActivityEnterView.class}, new String[]{"recordedAudioTimeTextView"}, null, null, null, Theme.key_chat_messagePanelVoiceDuration);
        themeDescriptionArr[243] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{ChatActivityEnterView.class}, new String[]{"recordCancelImage"}, null, null, null, Theme.key_chat_recordVoiceCancel);
        themeDescriptionArr[244] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"doneButtonProgress"}, null, null, null, Theme.key_contextProgressInner1);
        themeDescriptionArr[245] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"doneButtonProgress"}, null, null, null, Theme.key_contextProgressOuter1);
        themeDescriptionArr[246] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{ChatActivityEnterView.class}, new String[]{"cancelBotButton"}, null, null, null, Theme.key_chat_messagePanelCancelInlineBot);
        themeDescriptionArr[247] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"redDotPaint"}, null, null, null, Theme.key_chat_recordedVoiceDot);
        themeDescriptionArr[248] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"paint"}, null, null, null, Theme.key_chat_messagePanelVoiceBackground);
        themeDescriptionArr[249] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"paintRecord"}, null, null, null, Theme.key_chat_messagePanelVoiceShadow);
        themeDescriptionArr[Callback.DEFAULT_SWIPE_ANIMATION_DURATION] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"seekBarWaveform"}, null, null, null, Theme.key_chat_recordedVoiceProgress);
        themeDescriptionArr[251] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"seekBarWaveform"}, null, null, null, Theme.key_chat_recordedVoiceProgressInner);
        themeDescriptionArr[252] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"playDrawable"}, null, null, null, Theme.key_chat_recordedVoicePlayPause);
        themeDescriptionArr[253] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"pauseDrawable"}, null, null, null, Theme.key_chat_recordedVoicePlayPause);
        themeDescriptionArr[254] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"dotPaint"}, null, null, null, Theme.key_chat_emojiPanelNewTrending);
        themeDescriptionArr[255] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, new Class[]{ChatActivityEnterView.class}, new String[]{"playDrawable"}, null, null, null, Theme.key_chat_recordedVoicePlayPausePressed);
        themeDescriptionArr[256] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, new Class[]{ChatActivityEnterView.class}, new String[]{"pauseDrawable"}, null, null, null, Theme.key_chat_recordedVoicePlayPausePressed);
        themeDescriptionArr[257] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{TtmlNode.ANONYMOUS_REGION_ID}, null, null, null, Theme.key_chat_emojiPanelBackground);
        themeDescriptionArr[258] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{TtmlNode.ANONYMOUS_REGION_ID}, null, null, null, Theme.key_chat_emojiPanelShadowLine);
        themeDescriptionArr[259] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{TtmlNode.ANONYMOUS_REGION_ID}, null, null, null, Theme.key_chat_emojiPanelEmptyText);
        themeDescriptionArr[260] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{TtmlNode.ANONYMOUS_REGION_ID}, null, null, null, Theme.key_chat_emojiPanelIcon);
        themeDescriptionArr[261] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{TtmlNode.ANONYMOUS_REGION_ID}, null, null, null, Theme.key_chat_emojiPanelIconSelected);
        themeDescriptionArr[262] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{TtmlNode.ANONYMOUS_REGION_ID}, null, null, null, Theme.key_chat_emojiPanelStickerPackSelector);
        themeDescriptionArr[263] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{TtmlNode.ANONYMOUS_REGION_ID}, null, null, null, Theme.key_chat_emojiPanelIconSelector);
        themeDescriptionArr[264] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{TtmlNode.ANONYMOUS_REGION_ID}, null, null, null, Theme.key_chat_emojiPanelBackspace);
        themeDescriptionArr[265] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{TtmlNode.ANONYMOUS_REGION_ID}, null, null, null, Theme.key_chat_emojiPanelTrendingTitle);
        themeDescriptionArr[266] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{TtmlNode.ANONYMOUS_REGION_ID}, null, null, null, Theme.key_chat_emojiPanelTrendingDescription);
        themeDescriptionArr[267] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_chat_botKeyboardButtonText);
        themeDescriptionArr[268] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_chat_botKeyboardButtonBackground);
        themeDescriptionArr[269] = new ThemeDescription(null, 0, null, null, null, null, Theme.key_chat_botKeyboardButtonBackgroundPressed);
        themeDescriptionArr[270] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_inappPlayerBackground);
        themeDescriptionArr[271] = new ThemeDescription(this.fragmentContextView, 0, new Class[]{FragmentContextView.class}, new String[]{"playButton"}, null, null, null, Theme.key_inappPlayerPlayPause);
        themeDescriptionArr[272] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{FragmentContextView.class}, new String[]{"titleTextView"}, null, null, null, Theme.key_inappPlayerTitle);
        themeDescriptionArr[273] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_inappPlayerPerformer);
        themeDescriptionArr[274] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"closeButton"}, null, null, null, Theme.key_inappPlayerClose);
        themeDescriptionArr[275] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_returnToCallBackground);
        themeDescriptionArr[276] = new ThemeDescription(this.fragmentContextView, 0, new Class[]{FragmentContextView.class}, new String[]{"titleTextView"}, null, null, null, Theme.key_returnToCallText);
        themeDescriptionArr[277] = new ThemeDescription(this.pinnedLineView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_chat_topPanelLine);
        themeDescriptionArr[278] = new ThemeDescription(this.pinnedMessageNameTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_topPanelTitle);
        themeDescriptionArr[279] = new ThemeDescription(this.pinnedMessageTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_topPanelMessage);
        themeDescriptionArr[280] = new ThemeDescription(this.alertNameTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_topPanelTitle);
        themeDescriptionArr[281] = new ThemeDescription(this.alertTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_topPanelMessage);
        themeDescriptionArr[282] = new ThemeDescription(this.closePinned, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_topPanelClose);
        themeDescriptionArr[283] = new ThemeDescription(this.closeReportSpam, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_topPanelClose);
        themeDescriptionArr[284] = new ThemeDescription(this.reportSpamView, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chat_topPanelBackground);
        themeDescriptionArr[285] = new ThemeDescription(this.alertView, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chat_topPanelBackground);
        themeDescriptionArr[286] = new ThemeDescription(this.pinnedMessageView, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chat_topPanelBackground);
        themeDescriptionArr[287] = new ThemeDescription(this.addToContactsButton, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_addContact);
        themeDescriptionArr[288] = new ThemeDescription(this.reportSpamButton, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_reportSpam);
        themeDescriptionArr[289] = new ThemeDescription(this.replyLineView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_chat_replyPanelLine);
        themeDescriptionArr[290] = new ThemeDescription(this.replyNameTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_replyPanelName);
        themeDescriptionArr[291] = new ThemeDescription(this.replyObjectTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_replyPanelMessage);
        themeDescriptionArr[292] = new ThemeDescription(this.replyIconImageView, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_replyPanelIcons);
        themeDescriptionArr[293] = new ThemeDescription(this.replyCloseImageView, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_replyPanelClose);
        themeDescriptionArr[294] = new ThemeDescription(this.searchUpButton, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_searchPanelIcons);
        themeDescriptionArr[295] = new ThemeDescription(this.searchDownButton, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_searchPanelIcons);
        themeDescriptionArr[296] = new ThemeDescription(this.searchCalendarButton, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_searchPanelIcons);
        themeDescriptionArr[297] = new ThemeDescription(this.searchUserButton, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_searchPanelIcons);
        themeDescriptionArr[298] = new ThemeDescription(this.searchCountText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_searchPanelText);
        themeDescriptionArr[299] = new ThemeDescription(this.bottomOverlayText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_secretChatStatusText);
        themeDescriptionArr[add_fave_dialog] = new ThemeDescription(this.bottomOverlayChatText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_fieldOverlayText);
        themeDescriptionArr[301] = new ThemeDescription(this.bigEmptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[302] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[303] = new ThemeDescription(this.progressBar, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[304] = new ThemeDescription(this.stickersPanelArrow, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_stickersHintPanel);
        themeDescriptionArr[305] = new ThemeDescription(this.stickersListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{StickerCell.class}, null, null, null, Theme.key_chat_stickersHintPanel);
        themeDescriptionArr[306] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE, new Class[]{ChatUnreadCell.class}, new String[]{"backgroundLayout"}, null, null, null, Theme.key_chat_unreadMessagesStartBackground);
        themeDescriptionArr[307] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{ChatUnreadCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_chat_unreadMessagesStartArrowIcon);
        themeDescriptionArr[308] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{ChatUnreadCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chat_unreadMessagesStartText);
        themeDescriptionArr[309] = new ThemeDescription(this.progressView2, ThemeDescription.FLAG_SERVICEBACKGROUND, null, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[310] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_SERVICEBACKGROUND, null, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[311] = new ThemeDescription(this.bigEmptyView, ThemeDescription.FLAG_SERVICEBACKGROUND, null, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[312] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_SERVICEBACKGROUND, new Class[]{ChatLoadingCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[313] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_PROGRESSBAR, new Class[]{ChatLoadingCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[314] = new ThemeDescription(this.mentionListView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{BotSwitchCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chat_botSwitchToInlineText);
        themeDescriptionArr[315] = new ThemeDescription(this.mentionListView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{MentionCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[316] = new ThemeDescription(this.mentionListView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{MentionCell.class}, new String[]{"usernameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[317] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, new Drawable[]{Theme.chat_inlineResultFile, Theme.chat_inlineResultAudio, Theme.chat_inlineResultLocation}, null, Theme.key_chat_inlineResultIcon);
        themeDescriptionArr[318] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[319] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_windowBackgroundWhiteLinkText);
        themeDescriptionArr[320] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[321] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_chat_inAudioProgress);
        themeDescriptionArr[322] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_chat_inAudioSelectedProgress);
        themeDescriptionArr[323] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_divider);
        themeDescriptionArr[324] = new ThemeDescription(this.gifHintTextView, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chat_gifSaveHintBackground);
        themeDescriptionArr[325] = new ThemeDescription(this.gifHintTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_gifSaveHintText);
        themeDescriptionArr[326] = new ThemeDescription(this.pagedownButtonCounter, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chat_goDownButtonCounterBackground);
        themeDescriptionArr[327] = new ThemeDescription(this.pagedownButtonCounter, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_goDownButtonCounter);
        themeDescriptionArr[328] = new ThemeDescription(this.pagedownButtonImage, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chat_goDownButton);
        themeDescriptionArr[329] = new ThemeDescription(this.pagedownButtonImage, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_chat_goDownButtonShadow);
        themeDescriptionArr[330] = new ThemeDescription(this.pagedownButtonImage, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_goDownButtonIcon);
        themeDescriptionArr[331] = new ThemeDescription(this.mentiondownButtonCounter, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chat_goDownButtonCounterBackground);
        themeDescriptionArr[332] = new ThemeDescription(this.mentiondownButtonCounter, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_goDownButtonCounter);
        themeDescriptionArr[333] = new ThemeDescription(this.mentiondownButtonImage, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chat_goDownButton);
        themeDescriptionArr[334] = new ThemeDescription(this.mentiondownButtonImage, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_chat_goDownButtonShadow);
        themeDescriptionArr[335] = new ThemeDescription(this.mentiondownButtonImage, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_goDownButtonIcon);
        themeDescriptionArr[336] = new ThemeDescription(this.avatarContainer.getTimeItem(), 0, null, null, null, null, Theme.key_chat_secretTimerBackground);
        themeDescriptionArr[337] = new ThemeDescription(this.avatarContainer.getTimeItem(), 0, null, null, null, null, Theme.key_chat_secretTimerText);
        return themeDescriptionArr;
    }

    public boolean isSecretChat() {
        return this.currentEncryptedChat != null;
    }

    public boolean needDelayOpenAnimation() {
        return this.firstLoading;
    }

    public void onActivityResultFragment(int i, int i2, Intent intent) {
        Throwable th;
        Cursor cursor;
        if (i2 == -1) {
            int i3;
            if (i == 0) {
                int i4;
                PhotoViewer.getInstance().setParentActivity(getParentActivity());
                final ArrayList arrayList = new ArrayList();
                i3 = 0;
                try {
                    switch (new ExifInterface(this.currentPicturePath).getAttributeInt("Orientation", 1)) {
                        case 3:
                            i3 = 180;
                            break;
                        case 6:
                            i3 = 90;
                            break;
                        case 8:
                            i3 = 270;
                            break;
                    }
                    i4 = i3;
                } catch (Throwable e) {
                    FileLog.e(e);
                    i4 = 0;
                }
                arrayList.add(new PhotoEntry(0, 0, 0, this.currentPicturePath, i4, false));
                PhotoViewer.getInstance().openPhotoForSelect(arrayList, 0, 2, new EmptyPhotoViewerProvider() {
                    public void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo) {
                        ChatActivity.this.sendMedia((PhotoEntry) arrayList.get(0), null);
                    }
                }, this);
                AndroidUtilities.addMediaToGallery(this.currentPicturePath);
                this.currentPicturePath = null;
            } else if (i == 1) {
                if (intent == null || intent.getData() == null) {
                    showAttachmentError();
                    return;
                }
                r1 = intent.getData();
                if (r1.toString().contains(MimeTypes.BASE_TYPE_VIDEO)) {
                    r0 = null;
                    try {
                        r0 = AndroidUtilities.getPath(r1);
                    } catch (Throwable e2) {
                        FileLog.e(e2);
                    }
                    if (r0 == null) {
                        showAttachmentError();
                    }
                    if (this.paused) {
                        this.startVideoEdit = r0;
                    } else {
                        openVideoEditor(r0, null);
                    }
                } else {
                    SendMessagesHelper.prepareSendingPhoto(null, r1, this.dialog_id, this.replyingMessageObject, null, null, null, 0);
                }
                showReplyPanel(false, null, null, null, false);
                DraftQuery.cleanDraft(this.dialog_id, true);
            } else if (i == 2) {
                r0 = null;
                FileLog.d("pic path " + this.currentPicturePath);
                if (!(intent == null || this.currentPicturePath == null || !new File(this.currentPicturePath).exists())) {
                    intent = null;
                }
                if (intent != null) {
                    Uri data = intent.getData();
                    if (data != null) {
                        FileLog.d("video record uri " + data.toString());
                        r0 = AndroidUtilities.getPath(data);
                        FileLog.d("resolved path = " + r0);
                        if (r0 == null || !new File(r0).exists()) {
                            r0 = this.currentPicturePath;
                        }
                    } else {
                        r0 = this.currentPicturePath;
                    }
                    AndroidUtilities.addMediaToGallery(this.currentPicturePath);
                    this.currentPicturePath = null;
                }
                if (r0 == null && this.currentPicturePath != null) {
                    if (new File(this.currentPicturePath).exists()) {
                        r0 = this.currentPicturePath;
                    }
                    this.currentPicturePath = null;
                }
                if (this.paused) {
                    this.startVideoEdit = r0;
                } else {
                    openVideoEditor(r0, null);
                }
            } else if (i == 21) {
                if (intent == null) {
                    showAttachmentError();
                    return;
                }
                if (intent.getData() != null) {
                    sendUriAsDocument(intent.getData());
                } else if (intent.getClipData() != null) {
                    ClipData clipData = intent.getClipData();
                    for (i3 = 0; i3 < clipData.getItemCount(); i3++) {
                        sendUriAsDocument(clipData.getItemAt(i3).getUri());
                    }
                } else {
                    showAttachmentError();
                }
                showReplyPanel(false, null, null, null, false);
                DraftQuery.cleanDraft(this.dialog_id, true);
            } else if (i == 31) {
                if (intent == null || intent.getData() == null) {
                    showAttachmentError();
                    return;
                }
                r1 = intent.getData();
                try {
                    Cursor query = getParentActivity().getContentResolver().query(r1, new String[]{"display_name", "data1"}, null, null, null);
                    if (query != null) {
                        Object obj = null;
                        while (query.moveToNext()) {
                            try {
                                r0 = query.getString(0);
                                String string = query.getString(1);
                                User tLRPC$TL_user = new TLRPC$TL_user();
                                tLRPC$TL_user.first_name = r0;
                                tLRPC$TL_user.last_name = TtmlNode.ANONYMOUS_REGION_ID;
                                tLRPC$TL_user.phone = string;
                                SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_user, this.dialog_id, this.replyingMessageObject, null, null);
                                i3 = 1;
                            } catch (Throwable th2) {
                                th = th2;
                                cursor = query;
                            }
                        }
                        if (obj != null) {
                            showReplyPanel(false, null, null, null, false);
                            DraftQuery.cleanDraft(this.dialog_id, true);
                        }
                    }
                    if (query != null) {
                        try {
                            if (!query.isClosed()) {
                                query.close();
                            }
                        } catch (Throwable th3) {
                            FileLog.e(th3);
                        }
                    }
                } catch (Throwable th4) {
                    th3 = th4;
                    cursor = null;
                    if (cursor != null) {
                        try {
                            if (!cursor.isClosed()) {
                                cursor.close();
                            }
                        } catch (Throwable e22) {
                            FileLog.e(e22);
                        }
                    }
                    throw th3;
                }
            }
        }
        if (i2 == -1 && intent != null) {
            Bundle bundleExtra = intent.getBundleExtra(Response.f6990a);
            if (bundleExtra != null) {
                int i5 = bundleExtra.getInt(General.f6983a);
                string = bundleExtra.getString(General.f6985c);
                String string2 = bundleExtra.getString(General.f6986d);
                switch (i5) {
                    case 0:
                        Long.valueOf(bundleExtra.getLong(Payment.f6987a));
                        if (C3791b.c(ApplicationLoader.applicationContext, string, string2)) {
                            Toast.makeText(ApplicationLoader.applicationContext, "Sign is valid, you can trust response", 0).show();
                        } else {
                            Toast.makeText(ApplicationLoader.applicationContext, "Sign is not valid, you must don't trust response", 0).show();
                        }
                        Toast.makeText(ApplicationLoader.applicationContext, "Transaction Successful", 0).show();
                        return;
                    case 1001:
                    case 1201:
                        Long.valueOf(bundleExtra.getLong(Payment.f6987a));
                        Toast.makeText(ApplicationLoader.applicationContext, "Transaction Unknown", 0).show();
                        return;
                    case 1002:
                        Toast.makeText(ApplicationLoader.applicationContext, "Transaction Failed", 0).show();
                        return;
                    case 1100:
                        Toast.makeText(ApplicationLoader.applicationContext, "Invalid Host Data, check your host data request ", 0).show();
                        return;
                    case 1102:
                        Toast.makeText(ApplicationLoader.applicationContext, "First Register User", 0).show();
                        return;
                    case 1105:
                        Toast.makeText(ApplicationLoader.applicationContext, "You must update your sdk", 0).show();
                        return;
                    case 2020:
                        Toast.makeText(ApplicationLoader.applicationContext, "Transaction Canceled By User", 0).show();
                        return;
                    case 2021:
                        Toast.makeText(ApplicationLoader.applicationContext, "Transaction Canceled By SDK(due to timeout)", 0).show();
                        return;
                    case 2022:
                        Toast.makeText(ApplicationLoader.applicationContext, "User don't confirm registration data (such as mobile no)", 0).show();
                        return;
                    case 2023:
                        Toast.makeText(ApplicationLoader.applicationContext, "Don't change secret key until register again", 0).show();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public boolean onBackPressed() {
        if (checkRecordLocked()) {
            return false;
        }
        if (this.actionBar != null && this.actionBar.isActionModeShowed()) {
            for (int i = 1; i >= 0; i--) {
                this.selectedMessagesIds[i].clear();
                this.selectedMessagesCanCopyIds[i].clear();
                this.selectedMessagesCanStarIds[i].clear();
            }
            this.chatActivityEnterView.setEditingMessageObject(null, false);
            this.actionBar.hideActionMode();
            updatePinnedMessageView(true);
            this.cantDeleteMessagesCount = 0;
            this.canEditMessagesCount = 0;
            updateVisibleRows();
            return false;
        } else if (this.chatActivityEnterView == null || !this.chatActivityEnterView.isPopupShowing()) {
            return true;
        } else {
            this.chatActivityEnterView.hidePopup(true);
            return false;
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        fixLayout();
        if (this.visibleDialog instanceof DatePickerDialog) {
            this.visibleDialog.dismiss();
        }
    }

    protected void onDialogDismiss(Dialog dialog) {
        if (this.closeChatDialog != null && dialog == this.closeChatDialog) {
            MessagesController.getInstance().deleteDialog(this.dialog_id, 0);
            if (this.parentLayout == null || this.parentLayout.fragmentsStack.isEmpty() || this.parentLayout.fragmentsStack.get(this.parentLayout.fragmentsStack.size() - 1) == this) {
                finishFragment();
                return;
            }
            BaseFragment baseFragment = (BaseFragment) this.parentLayout.fragmentsStack.get(this.parentLayout.fragmentsStack.size() - 1);
            removeSelfFromStack();
            baseFragment.finishFragment();
        }
    }

    public boolean onFragmentCreate() {
        final Semaphore semaphore;
        final int i = this.arguments.getInt("chat_id", 0);
        final int i2 = this.arguments.getInt("user_id", 0);
        final int i3 = this.arguments.getInt("enc_id", 0);
        this.inlineReturn = this.arguments.getLong("inline_return", 0);
        String string = this.arguments.getString("inline_query");
        this.startLoadFromMessageId = this.arguments.getInt("message_id", 0);
        int i4 = this.arguments.getInt("migrated_to", 0);
        this.scrollToTopOnResume = this.arguments.getBoolean("scrollToTopOnResume", false);
        Log.d("alireza", "alireza dialogId" + i);
        this.chat_id = i;
        this.isAdvancedForward = this.arguments.getBoolean("isAdvancedForward", false);
        this.isDownloadManager = this.arguments.getBoolean("isDownloadManager", false);
        this.directShareToMenu = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("directShareToMenu", false);
        if (i != 0) {
            this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(i));
            if (this.currentChat == null) {
                final Semaphore semaphore2 = new Semaphore(0);
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        ChatActivity.this.currentChat = MessagesStorage.getInstance().getChat(i);
                        semaphore2.release();
                    }
                });
                try {
                    semaphore2.acquire();
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                if (this.currentChat != null) {
                    MessagesController.getInstance().putChat(this.currentChat, true);
                } else if (i != 111444999) {
                    return false;
                }
            }
            if (i > 0) {
                this.dialog_id = (long) (-i);
            } else {
                this.isBroadcast = true;
                this.dialog_id = AndroidUtilities.makeBroadcastId(i);
            }
            if (ChatObject.isChannel(this.currentChat)) {
                MessagesController.getInstance().startShortPoll(i, false);
            }
        } else if (i2 != 0) {
            this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(i2));
            if (this.currentUser == null) {
                semaphore = new Semaphore(0);
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        ChatActivity.this.currentUser = MessagesStorage.getInstance().getUser(i2);
                        semaphore.release();
                    }
                });
                try {
                    semaphore.acquire();
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
                if (this.currentUser == null) {
                    return false;
                }
                MessagesController.getInstance().putUser(this.currentUser, true);
            }
            this.dialog_id = (long) i2;
            this.botUser = this.arguments.getString("botUser");
            if (string != null) {
                MessagesController.getInstance().sendBotStart(this.currentUser, string);
            }
        } else if (i3 != 0) {
            this.currentEncryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i3));
            if (this.currentEncryptedChat == null) {
                semaphore = new Semaphore(0);
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        ChatActivity.this.currentEncryptedChat = MessagesStorage.getInstance().getEncryptedChat(i3);
                        semaphore.release();
                    }
                });
                try {
                    semaphore.acquire();
                } catch (Throwable e22) {
                    FileLog.e(e22);
                }
                if (this.currentEncryptedChat == null) {
                    return false;
                }
                MessagesController.getInstance().putEncryptedChat(this.currentEncryptedChat, true);
            }
            this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(this.currentEncryptedChat.user_id));
            if (this.currentUser == null) {
                semaphore = new Semaphore(0);
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        ChatActivity.this.currentUser = MessagesStorage.getInstance().getUser(ChatActivity.this.currentEncryptedChat.user_id);
                        semaphore.release();
                    }
                });
                try {
                    semaphore.acquire();
                } catch (Throwable e222) {
                    FileLog.e(e222);
                }
                if (this.currentUser == null) {
                    return false;
                }
                MessagesController.getInstance().putUser(this.currentUser, true);
            }
            this.dialog_id = ((long) i3) << 32;
            int[] iArr = this.maxMessageId;
            this.maxMessageId[1] = Integer.MIN_VALUE;
            iArr[0] = Integer.MIN_VALUE;
            iArr = this.minMessageId;
            this.minMessageId[1] = Integer.MAX_VALUE;
            iArr[0] = Integer.MAX_VALUE;
        } else if (!this.isAdvancedForward) {
            return false;
        }
        if (this.currentUser != null) {
            MediaController.getInstance().startMediaObserver();
        }
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceivedNewMessages);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeChats);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesRead);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDeleted);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.historyCleared);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messageReceivedByServer);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messageReceivedByAck);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messageSendError);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.contactsDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.encryptedChatUpdated);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesReadEncrypted);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.removeAllMessagesFromDialog);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.screenshotTook);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.blockedUsersDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileNewChunkAvailable);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didCreatedNewDeleteTask);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidStarted);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateMessageMedia);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.replaceMessagesObjects);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.notificationsSettingsUpdated);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didLoadedReplyMessages);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceivedWebpages);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceivedWebpagesInUpdates);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesReadContent);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.botInfoDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.botKeyboardDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatSearchResultsAvailable);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatSearchResultsLoading);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didUpdatedMessagesViews);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoCantLoad);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didLoadedPinnedMessage);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.peerSettingsDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.newDraftReceived);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.userInfoDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didSetNewWallpapper);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.channelRightsUpdated);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateMentionsCount);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.paymentSuccessMessage);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.goToPayment);
        super.onFragmentCreate();
        if (this.currentEncryptedChat == null && !this.isBroadcast) {
            BotQuery.loadBotKeyboard(this.dialog_id);
        }
        this.loading = true;
        MessagesController.getInstance().loadPeerSettings(this.currentUser, this.currentChat);
        MessagesController.getInstance().setLastCreatedDialogId(this.dialog_id, true);
        if (this.startLoadFromMessageId == 0) {
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            i3 = sharedPreferences.getInt("diditem" + this.dialog_id, 0);
            if (i3 != 0) {
                this.loadingFromOldPosition = true;
                this.startLoadFromMessageOffset = sharedPreferences.getInt("diditemo" + this.dialog_id, 0);
                this.startLoadFromMessageId = i3;
            }
        } else {
            this.needSelectFromMessageId = true;
        }
        if (this.isDownloadManager) {
            this.dialog_id = 111444999;
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.DownloadServiceStart);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.DownloadServiceStop);
        }
        MessagesController instance;
        long j;
        int i5;
        int i6;
        boolean isChannel;
        int i7;
        if (this.startLoadFromMessageId != 0) {
            this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
            int i8;
            if (i4 != 0) {
                this.mergeDialogId = (long) i4;
                instance = MessagesController.getInstance();
                j = this.mergeDialogId;
                i5 = this.loadingFromOldPosition ? 50 : AndroidUtilities.isTablet() ? 30 : 20;
                i8 = this.startLoadFromMessageId;
                i6 = this.classGuid;
                isChannel = ChatObject.isChannel(this.currentChat);
                i7 = this.lastLoadIndex;
                this.lastLoadIndex = i7 + 1;
                instance.loadMessages(j, i5, i8, 0, true, 0, i6, 3, 0, isChannel, i7);
            } else {
                instance = MessagesController.getInstance();
                j = this.dialog_id;
                i5 = this.loadingFromOldPosition ? 50 : AndroidUtilities.isTablet() ? 30 : 20;
                i8 = this.startLoadFromMessageId;
                i6 = this.classGuid;
                isChannel = ChatObject.isChannel(this.currentChat);
                i7 = this.lastLoadIndex;
                this.lastLoadIndex = i7 + 1;
                instance.loadMessages(j, i5, i8, 0, true, 0, i6, 3, 0, isChannel, i7);
            }
        } else {
            this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
            instance = MessagesController.getInstance();
            j = this.dialog_id;
            i5 = AndroidUtilities.isTablet() ? 30 : 20;
            i6 = this.classGuid;
            isChannel = ChatObject.isChannel(this.currentChat);
            i7 = this.lastLoadIndex;
            this.lastLoadIndex = i7 + 1;
            instance.loadMessages(j, i5, 0, 0, true, 0, i6, 2, 0, isChannel, i7);
        }
        if (this.currentChat != null) {
            semaphore = null;
            if (this.isBroadcast) {
                semaphore = new Semaphore(0);
            }
            MessagesController.getInstance().loadChatInfo(this.currentChat.id, semaphore, ChatObject.isChannel(this.currentChat));
            if (this.isBroadcast && semaphore != null) {
                try {
                    semaphore.acquire();
                } catch (Throwable e2222) {
                    FileLog.e(e2222);
                }
            }
        }
        if (i2 != 0 && this.currentUser.bot) {
            BotQuery.loadBotInfo(i2, true, this.classGuid);
        } else if (this.info instanceof TL_chatFull) {
            for (i3 = 0; i3 < this.info.participants.participants.size(); i3++) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(((ChatParticipant) this.info.participants.participants.get(i3)).user_id));
                if (user != null && user.bot) {
                    BotQuery.loadBotInfo(user.id, true, this.classGuid);
                }
            }
        }
        if (this.currentUser != null) {
            this.userBlocked = MessagesController.getInstance().blockedUsers.contains(Integer.valueOf(this.currentUser.id));
        }
        if (AndroidUtilities.isTablet()) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.openedChatChanged, new Object[]{Long.valueOf(this.dialog_id), Boolean.valueOf(false)});
        }
        if (!(this.currentEncryptedChat == null || AndroidUtilities.getMyLayerVersion(this.currentEncryptedChat.layer) == 73)) {
            SecretChatHelper.getInstance().sendNotifyLayerMessage(this.currentEncryptedChat, null);
        }
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        if (this.chatActivityEnterView != null) {
            this.chatActivityEnterView.onDestroy();
        }
        if (this.mentionsAdapter != null) {
            this.mentionsAdapter.onDestroy();
        }
        if (this.chatAttachAlert != null) {
            this.chatAttachAlert.dismissInternal();
        }
        MessagesController.getInstance().setLastCreatedDialogId(this.dialog_id, false);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceivedNewMessages);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesRead);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDeleted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.historyCleared);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messageReceivedByServer);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messageReceivedByAck);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messageSendError);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatInfoDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.encryptedChatUpdated);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesReadEncrypted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.removeAllMessagesFromDialog);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.screenshotTook);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.blockedUsersDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileNewChunkAvailable);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didCreatedNewDeleteTask);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidStarted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateMessageMedia);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.replaceMessagesObjects);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.notificationsSettingsUpdated);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didLoadedReplyMessages);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceivedWebpages);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceivedWebpagesInUpdates);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesReadContent);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.botInfoDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.botKeyboardDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatSearchResultsAvailable);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatSearchResultsLoading);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didUpdatedMessagesViews);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatInfoCantLoad);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didLoadedPinnedMessage);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.peerSettingsDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.newDraftReceived);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.userInfoDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didSetNewWallpapper);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.channelRightsUpdated);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateMentionsCount);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.paymentSuccessMessage);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.goToPayment);
        if (AndroidUtilities.isTablet()) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.openedChatChanged, new Object[]{Long.valueOf(this.dialog_id), Boolean.valueOf(true)});
        }
        if (this.currentUser != null) {
            MediaController.getInstance().stopMediaObserver();
        }
        if (this.currentEncryptedChat != null) {
            try {
                if (VERSION.SDK_INT >= 23 && (UserConfig.passcodeHash.length() == 0 || UserConfig.allowScreenCapture)) {
                    getParentActivity().getWindow().clearFlags(MessagesController.UPDATE_MASK_CHANNEL);
                }
            } catch (Throwable th) {
                FileLog.e(th);
            }
        }
        if (this.currentUser != null) {
            MessagesController.getInstance().cancelLoadFullUser(this.currentUser.id);
        }
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
        if (this.stickersAdapter != null) {
            this.stickersAdapter.onDestroy();
        }
        if (this.chatAttachAlert != null) {
            this.chatAttachAlert.onDestroy();
        }
        AndroidUtilities.unlockOrientation(getParentActivity());
        if (ChatObject.isChannel(this.currentChat)) {
            MessagesController.getInstance().startShortPoll(this.currentChat.id, true);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onPause() {
        /*
        r9 = this;
        r4 = 0;
        r6 = 1;
        r8 = 0;
        super.onPause();
        r0 = r9.readRunnable;
        org.telegram.messenger.AndroidUtilities.cancelRunOnUIThread(r0);
        r0 = org.telegram.messenger.MediaController.getInstance();
        r0.stopRaiseToEarSensors(r9);
        r9.paused = r6;
        r9.wasPaused = r6;
        r0 = org.telegram.messenger.NotificationsController.getInstance();
        r2 = 0;
        r0.setOpenedDialogId(r2);
        r0 = r9.ignoreAttachOnPause;
        if (r0 != 0) goto L_0x019a;
    L_0x0023:
        r0 = r9.chatActivityEnterView;
        if (r0 == 0) goto L_0x019a;
    L_0x0027:
        r0 = r9.bottomOverlayChat;
        r0 = r0.getVisibility();
        if (r0 == 0) goto L_0x019a;
    L_0x002f:
        r0 = r9.chatActivityEnterView;
        r0.onPause();
        r2 = r9.replyingMessageObject;
        r0 = r9.chatActivityEnterView;
        r0 = r0.isEditingMessage();
        if (r0 != 0) goto L_0x0197;
    L_0x003e:
        r0 = r9.chatActivityEnterView;
        r0 = r0.getFieldText();
        r0 = org.telegram.messenger.AndroidUtilities.getTrimmedString(r0);
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 != 0) goto L_0x0197;
    L_0x004e:
        r1 = "@gif";
        r1 = android.text.TextUtils.equals(r0, r1);
        if (r1 != 0) goto L_0x0197;
    L_0x0057:
        r1 = r9.chatActivityEnterView;
        r1 = r1.isMessageWebPageSearchEnabled();
        r3 = r9.chatActivityEnterView;
        r3.setFieldFocused(r8);
        r5 = r1;
        r7 = r2;
    L_0x0064:
        r1 = r9.chatAttachAlert;
        if (r1 == 0) goto L_0x0071;
    L_0x0068:
        r1 = r9.ignoreAttachOnPause;
        if (r1 != 0) goto L_0x0141;
    L_0x006c:
        r1 = r9.chatAttachAlert;
        r1.onPause();
    L_0x0071:
        r2 = new java.lang.CharSequence[r6];
        r2[r8] = r0;
        r3 = org.telegram.messenger.query.MessagesQuery.getEntities(r2);
        r0 = r9.dialog_id;
        r2 = r2[r8];
        if (r7 == 0) goto L_0x0081;
    L_0x007f:
        r4 = r7.messageOwner;
    L_0x0081:
        if (r5 != 0) goto L_0x0145;
    L_0x0083:
        r5 = r6;
    L_0x0084:
        org.telegram.messenger.query.DraftQuery.saveDraft(r0, r2, r3, r4, r5);
        r0 = org.telegram.messenger.MessagesController.getInstance();
        r2 = r9.dialog_id;
        r0.cancelTyping(r8, r2);
        r0 = r9.pausedOnLastMessage;
        if (r0 != 0) goto L_0x0133;
    L_0x0094:
        r0 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r1 = "Notifications";
        r0 = r0.getSharedPreferences(r1, r8);
        r2 = r0.edit();
        r0 = r9.chatLayoutManager;
        if (r0 == 0) goto L_0x0194;
    L_0x00a5:
        r0 = r9.chatLayoutManager;
        r0 = r0.findFirstVisibleItemPosition();
        if (r0 == 0) goto L_0x0194;
    L_0x00ad:
        r1 = r9.chatListView;
        r0 = r1.findViewHolderForAdapterPosition(r0);
        r0 = (org.telegram.ui.Components.RecyclerListView.Holder) r0;
        if (r0 == 0) goto L_0x0194;
    L_0x00b7:
        r1 = r0.itemView;
        r1 = r1 instanceof org.telegram.ui.Cells.ChatMessageCell;
        if (r1 == 0) goto L_0x0148;
    L_0x00bd:
        r1 = r0.itemView;
        r1 = (org.telegram.ui.Cells.ChatMessageCell) r1;
        r1 = r1.getMessageObject();
        r1 = r1.getId();
    L_0x00c9:
        if (r1 == 0) goto L_0x00fb;
    L_0x00cb:
        r0 = r0.itemView;
        r0 = r0.getBottom();
        r3 = r9.chatListView;
        r3 = r3.getMeasuredHeight();
        r8 = r0 - r3;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r3 = "save offset = ";
        r0 = r0.append(r3);
        r0 = r0.append(r8);
        r3 = " for mid ";
        r0 = r0.append(r3);
        r0 = r0.append(r1);
        r0 = r0.toString();
        org.telegram.messenger.FileLog.d(r0);
    L_0x00fb:
        r0 = r1;
    L_0x00fc:
        if (r0 == 0) goto L_0x015c;
    L_0x00fe:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r3 = "diditem";
        r1 = r1.append(r3);
        r4 = r9.dialog_id;
        r1 = r1.append(r4);
        r1 = r1.toString();
        r2.putInt(r1, r0);
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "diditemo";
        r0 = r0.append(r1);
        r4 = r9.dialog_id;
        r0 = r0.append(r4);
        r0 = r0.toString();
        r2.putInt(r0, r8);
    L_0x0130:
        r2.commit();
    L_0x0133:
        r0 = r9.currentUser;
        if (r0 == 0) goto L_0x0140;
    L_0x0137:
        r0 = java.lang.System.currentTimeMillis();
        r9.chatLeaveTime = r0;
        r9.updateInformationForScreenshotDetector();
    L_0x0140:
        return;
    L_0x0141:
        r9.ignoreAttachOnPause = r8;
        goto L_0x0071;
    L_0x0145:
        r5 = r8;
        goto L_0x0084;
    L_0x0148:
        r1 = r0.itemView;
        r1 = r1 instanceof org.telegram.ui.Cells.ChatActionCell;
        if (r1 == 0) goto L_0x0191;
    L_0x014e:
        r1 = r0.itemView;
        r1 = (org.telegram.ui.Cells.ChatActionCell) r1;
        r1 = r1.getMessageObject();
        r1 = r1.getId();
        goto L_0x00c9;
    L_0x015c:
        r9.pausedOnLastMessage = r6;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "diditem";
        r0 = r0.append(r1);
        r4 = r9.dialog_id;
        r0 = r0.append(r4);
        r0 = r0.toString();
        r2.remove(r0);
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "diditemo";
        r0 = r0.append(r1);
        r4 = r9.dialog_id;
        r0 = r0.append(r4);
        r0 = r0.toString();
        r2.remove(r0);
        goto L_0x0130;
    L_0x0191:
        r1 = r8;
        goto L_0x00c9;
    L_0x0194:
        r0 = r8;
        goto L_0x00fc;
    L_0x0197:
        r0 = r4;
        goto L_0x0057;
    L_0x019a:
        r5 = r6;
        r7 = r4;
        r0 = r4;
        goto L_0x0064;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.ChatActivity.onPause():void");
    }

    protected void onRemoveFromParent() {
        MediaController.getInstance().setTextureView(this.videoTextureView, null, null, false);
    }

    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (this.chatActivityEnterView != null) {
            this.chatActivityEnterView.onRequestPermissionsResultFragment(i, strArr, iArr);
        }
        if (this.mentionsAdapter != null) {
            this.mentionsAdapter.onRequestPermissionsResultFragment(i, strArr, iArr);
        }
        if (i == 17 && this.chatAttachAlert != null) {
            this.chatAttachAlert.checkCamera(false);
        } else if (i == 21) {
            if (getParentActivity() != null && iArr != null && iArr.length != 0 && iArr[0] != 0) {
                org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setMessage(LocaleController.getString("PermissionNoAudioVideo", R.string.PermissionNoAudioVideo));
                builder.setNegativeButton(LocaleController.getString("PermissionOpenSettings", R.string.PermissionOpenSettings), new OnClickListener() {
                    @TargetApi(9)
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
                            ChatActivity.this.getParentActivity().startActivity(intent);
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                });
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                builder.show();
            }
        } else if (i == 19 && iArr != null && iArr.length > 0 && iArr[0] == 0) {
            processSelectedAttach(0);
        } else if (i == 20 && iArr != null && iArr.length > 0 && iArr[0] == 0) {
            processSelectedAttach(2);
        } else if (i == 101 && this.currentUser != null) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                VoIPHelper.permissionDenied(getParentActivity(), null);
            } else {
                VoIPHelper.startCall(this.currentUser, getParentActivity(), MessagesController.getInstance().getUserFull(this.currentUser.id));
            }
        }
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
        MediaController.getInstance().startRaiseToEarSensors(this);
        checkRaiseSensors();
        if (this.chatAttachAlert != null) {
            this.chatAttachAlert.onResume();
        }
        checkActionBarMenu();
        if (!(this.replyImageLocation == null || this.replyImageView == null)) {
            this.replyImageView.setImage(this.replyImageLocation, "50_50", (Drawable) null);
        }
        if (!(this.pinnedImageLocation == null || this.pinnedMessageImageView == null)) {
            this.pinnedMessageImageView.setImage(this.pinnedImageLocation, "50_50", (Drawable) null);
        }
        NotificationsController.getInstance().setOpenedDialogId(this.dialog_id);
        if (this.scrollToTopOnResume) {
            if (!this.scrollToTopUnReadOnResume || this.scrollToMessage == null) {
                moveScrollToLastMessage();
            } else if (this.chatListView != null) {
                int scrollOffsetForMessage;
                boolean z;
                if (this.scrollToMessagePosition == -9000) {
                    scrollOffsetForMessage = getScrollOffsetForMessage(this.scrollToMessage);
                    z = false;
                } else if (this.scrollToMessagePosition == -10000) {
                    scrollOffsetForMessage = (-this.chatListView.getPaddingTop()) - AndroidUtilities.dp(7.0f);
                    z = false;
                } else {
                    scrollOffsetForMessage = this.scrollToMessagePosition;
                    z = true;
                }
                this.chatLayoutManager.scrollToPositionWithOffset(this.chatAdapter.messagesStartRow + this.messages.indexOf(this.scrollToMessage), scrollOffsetForMessage, z);
            }
            this.scrollToTopUnReadOnResume = false;
            this.scrollToTopOnResume = false;
            this.scrollToMessage = null;
        }
        this.paused = false;
        this.pausedOnLastMessage = false;
        AndroidUtilities.runOnUIThread(this.readRunnable, 500);
        checkScrollForLoad(false);
        if (this.wasPaused) {
            this.wasPaused = false;
            if (this.chatAdapter != null) {
                this.chatAdapter.notifyDataSetChanged();
            }
        }
        fixLayout();
        applyDraftMaybe(false);
        if (!(this.bottomOverlayChat == null || this.bottomOverlayChat.getVisibility() == 0)) {
            this.chatActivityEnterView.setFieldFocused(true);
        }
        if (this.chatActivityEnterView != null) {
            this.chatActivityEnterView.onResume();
        }
        if (this.currentUser != null) {
            this.chatEnterTime = System.currentTimeMillis();
            this.chatLeaveTime = 0;
        }
        if (this.startVideoEdit != null) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ChatActivity.this.openVideoEditor(ChatActivity.this.startVideoEdit, null);
                    ChatActivity.this.startVideoEdit = null;
                }
            });
        }
        if (this.chatListView != null && (this.chatActivityEnterView == null || !this.chatActivityEnterView.isEditingMessage())) {
            this.chatListView.setOnItemLongClickListener(this.onItemLongClickListener);
            this.chatListView.setOnItemClickListener(this.onItemClickListener);
            this.chatListView.setLongClickable(true);
        }
        checkBotCommands();
        if (this.channelIsFilter && this.showFilterDialog) {
            try {
                showCantOpenAlert(this, C3791b.T(ApplicationLoader.applicationContext), true);
            } catch (Exception e) {
            }
        }
    }

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        NotificationCenter.getInstance().setAnimationInProgress(false);
        if (z) {
            this.openAnimationEnded = true;
            if (this.currentUser != null) {
                MessagesController.getInstance().loadFullUser(this.currentUser, this.classGuid, false);
            }
            if (VERSION.SDK_INT >= 21) {
                createChatAttachView();
            }
            if (this.chatActivityEnterView.hasRecordVideo() && !this.chatActivityEnterView.isSendButtonVisible()) {
                boolean z3 = this.currentChat != null ? ChatObject.isChannel(this.currentChat) && !this.currentChat.megagroup : false;
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                String str = z3 ? "needShowRoundHintChannel" : "needShowRoundHint";
                if (sharedPreferences.getBoolean(str, true) && Utilities.random.nextFloat() < 0.2f) {
                    showVoiceHint(false, this.chatActivityEnterView.isInVideoMode());
                    sharedPreferences.edit().putBoolean(str, false).commit();
                }
            }
        }
    }

    public void onTransitionAnimationStart(boolean z, boolean z2) {
        NotificationCenter.getInstance().setAllowedNotificationsDutingAnimation(new int[]{NotificationCenter.chatInfoDidLoaded, NotificationCenter.dialogsNeedReload, NotificationCenter.closeChats, NotificationCenter.messagesDidLoaded, NotificationCenter.botKeyboardDidLoaded});
        NotificationCenter.getInstance().setAnimationInProgress(true);
        if (z) {
            this.openAnimationEnded = false;
        }
    }

    public void openVideoEditor(String str, String str2) {
        if (getParentActivity() != null) {
            final Bitmap createVideoThumbnail = ThumbnailUtils.createVideoThumbnail(str, 1);
            PhotoViewer.getInstance().setParentActivity(getParentActivity());
            final ArrayList arrayList = new ArrayList();
            PhotoEntry photoEntry = new PhotoEntry(0, 0, 0, str, 0, true);
            photoEntry.caption = str2;
            arrayList.add(photoEntry);
            PhotoViewer.getInstance().openPhotoForSelect(arrayList, 0, 2, new EmptyPhotoViewerProvider() {
                public boolean canScrollAway() {
                    return false;
                }

                public Bitmap getThumbForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
                    return createVideoThumbnail;
                }

                public void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo) {
                    ChatActivity.this.sendMedia((PhotoEntry) arrayList.get(0), videoEditedInfo);
                }
            }, this);
            return;
        }
        SendMessagesHelper.prepareSendingVideo(str, 0, 0, 0, 0, null, this.dialog_id, this.replyingMessageObject, null, 0);
        showReplyPanel(false, null, null, null, false);
        DraftQuery.cleanDraft(this.dialog_id, true);
    }

    public boolean playFirstUnreadVoiceMessage() {
        if (this.chatActivityEnterView != null && this.chatActivityEnterView.isRecordingAudioVideo()) {
            return true;
        }
        for (int size = this.messages.size() - 1; size >= 0; size--) {
            MessageObject messageObject = (MessageObject) this.messages.get(size);
            if ((messageObject.isVoice() || messageObject.isRoundVideo()) && messageObject.isContentUnread() && !messageObject.isOut()) {
                MediaController.getInstance().setVoiceMessagesPlaylist(MediaController.getInstance().playMessage(messageObject) ? createVoiceMessagesPlaylist(messageObject, true) : null, true);
                return true;
            }
        }
        if (VERSION.SDK_INT < 23 || getParentActivity() == null || getParentActivity().checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
            return false;
        }
        getParentActivity().requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 3);
        return true;
    }

    public void processInlineBotContextPM(TLRPC$TL_inlineBotSwitchPM tLRPC$TL_inlineBotSwitchPM) {
        if (tLRPC$TL_inlineBotSwitchPM != null) {
            User contextBotUser = this.mentionsAdapter.getContextBotUser();
            if (contextBotUser != null) {
                this.chatActivityEnterView.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
                if (this.dialog_id == ((long) contextBotUser.id)) {
                    this.inlineReturn = this.dialog_id;
                    MessagesController.getInstance().sendBotStart(this.currentUser, tLRPC$TL_inlineBotSwitchPM.start_param);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", contextBotUser.id);
                bundle.putString("inline_query", tLRPC$TL_inlineBotSwitchPM.start_param);
                bundle.putLong("inline_return", this.dialog_id);
                if (MessagesController.checkCanOpenChat(bundle, this)) {
                    presentFragment(new ChatActivity(bundle));
                }
            }
        }
    }

    public boolean processSendingText(String str) {
        return this.chatActivityEnterView.processSendingText(str);
    }

    public boolean processSwitchButton(TLRPC$TL_keyboardButtonSwitchInline tLRPC$TL_keyboardButtonSwitchInline) {
        if (this.inlineReturn == 0 || tLRPC$TL_keyboardButtonSwitchInline.same_peer || this.parentLayout == null) {
            return false;
        }
        CharSequence charSequence = "@" + this.currentUser.username + " " + tLRPC$TL_keyboardButtonSwitchInline.query;
        if (this.inlineReturn == this.dialog_id) {
            this.inlineReturn = 0;
            this.chatActivityEnterView.setFieldText(charSequence);
        } else {
            DraftQuery.saveDraft(this.inlineReturn, charSequence, null, null, false);
            if (this.parentLayout.fragmentsStack.size() > 1) {
                BaseFragment baseFragment = (BaseFragment) this.parentLayout.fragmentsStack.get(this.parentLayout.fragmentsStack.size() - 2);
                if ((baseFragment instanceof ChatActivity) && ((ChatActivity) baseFragment).dialog_id == this.inlineReturn) {
                    finishFragment();
                } else {
                    Bundle bundle = new Bundle();
                    int i = (int) this.inlineReturn;
                    int i2 = (int) (this.inlineReturn >> 32);
                    if (i == 0) {
                        bundle.putInt("enc_id", i2);
                    } else if (i > 0) {
                        bundle.putInt("user_id", i);
                    } else if (i < 0) {
                        bundle.putInt("chat_id", -i);
                    }
                    presentFragment(new ChatActivity(bundle), true);
                }
            }
        }
        return true;
    }

    public void restoreSelfArgs(Bundle bundle) {
        this.currentPicturePath = bundle.getString("path");
    }

    public void saveSelfArgs(Bundle bundle) {
        if (this.currentPicturePath != null) {
            bundle.putString("path", this.currentPicturePath);
        }
    }

    public void scrollToMessageId(int i, int i2, boolean z, int i3, boolean z2) {
        Object obj;
        int childCount;
        int i4;
        MessageObject messageObject = (MessageObject) this.messagesDict[i3].get(Integer.valueOf(i));
        if (messageObject == null) {
            obj = 1;
        } else if (this.messages.indexOf(messageObject) != -1) {
            if (z) {
                this.highlightMessageId = i;
            } else {
                this.highlightMessageId = Integer.MAX_VALUE;
            }
            int scrollOffsetForMessage = getScrollOffsetForMessage(messageObject);
            if (z2) {
                if (this.messages.get(this.messages.size() - 1) == messageObject) {
                    this.chatListView.smoothScrollToPosition(this.chatAdapter.getItemCount() - 1);
                } else {
                    this.chatListView.smoothScrollToPosition(this.chatAdapter.messagesStartRow + this.messages.indexOf(messageObject));
                }
            } else if (this.messages.get(this.messages.size() - 1) == messageObject) {
                this.chatLayoutManager.scrollToPositionWithOffset(this.chatAdapter.getItemCount() - 1, scrollOffsetForMessage, false);
            } else {
                this.chatLayoutManager.scrollToPositionWithOffset(this.chatAdapter.messagesStartRow + this.messages.indexOf(messageObject), scrollOffsetForMessage, false);
            }
            updateVisibleRows();
            childCount = this.chatListView.getChildCount();
            for (i4 = 0; i4 < childCount; i4++) {
                View childAt = this.chatListView.getChildAt(i4);
                MessageObject messageObject2;
                if (childAt instanceof ChatMessageCell) {
                    messageObject2 = ((ChatMessageCell) childAt).getMessageObject();
                    if (messageObject2 != null && messageObject2.getId() == messageObject.getId()) {
                        obj = 1;
                        break;
                    }
                } else if (childAt instanceof ChatActionCell) {
                    messageObject2 = ((ChatActionCell) childAt).getMessageObject();
                    if (messageObject2 != null && messageObject2.getId() == messageObject.getId()) {
                        obj = 1;
                        break;
                    }
                } else {
                    continue;
                }
            }
            obj = null;
            if (obj == null) {
                showPagedownButton(true, true);
            }
            obj = null;
        } else {
            obj = 1;
        }
        if (obj != null) {
            if (this.currentEncryptedChat == null || MessagesStorage.getInstance().checkMessageId(this.dialog_id, this.startLoadFromMessageId)) {
                this.waitingForLoad.clear();
                this.waitingForReplyMessageLoad = true;
                this.highlightMessageId = Integer.MAX_VALUE;
                this.scrollToMessagePosition = -10000;
                this.startLoadFromMessageId = i;
                if (i == this.createUnreadMessageAfterId) {
                    this.createUnreadMessageAfterIdLoading = true;
                }
                this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                MessagesController instance = MessagesController.getInstance();
                long j = i3 == 0 ? this.dialog_id : this.mergeDialogId;
                i4 = AndroidUtilities.isTablet() ? 30 : 20;
                childCount = this.startLoadFromMessageId;
                int i5 = this.classGuid;
                boolean isChannel = ChatObject.isChannel(this.currentChat);
                int i6 = this.lastLoadIndex;
                this.lastLoadIndex = i6 + 1;
                instance.loadMessages(j, i4, childCount, 0, true, 0, i5, 3, 0, isChannel, i6);
            } else {
                return;
            }
        }
        this.returnToMessageId = i2;
        this.returnToLoadIndex = i3;
        this.needSelectFromMessageId = z;
    }

    public void sendMedia(PhotoEntry photoEntry, VideoEditedInfo videoEditedInfo) {
        if (photoEntry.isVideo) {
            if (videoEditedInfo != null) {
                SendMessagesHelper.prepareSendingVideo(photoEntry.path, videoEditedInfo.estimatedSize, videoEditedInfo.estimatedDuration, videoEditedInfo.resultWidth, videoEditedInfo.resultHeight, videoEditedInfo, this.dialog_id, this.replyingMessageObject, photoEntry.caption != null ? photoEntry.caption.toString() : null, photoEntry.ttl);
            } else {
                SendMessagesHelper.prepareSendingVideo(photoEntry.path, 0, 0, 0, 0, null, this.dialog_id, this.replyingMessageObject, photoEntry.caption != null ? photoEntry.caption.toString() : null, photoEntry.ttl);
            }
            showReplyPanel(false, null, null, null, false);
            DraftQuery.cleanDraft(this.dialog_id, true);
        } else if (photoEntry.imagePath != null) {
            SendMessagesHelper.prepareSendingPhoto(photoEntry.imagePath, null, this.dialog_id, this.replyingMessageObject, photoEntry.caption, photoEntry.stickers, null, photoEntry.ttl);
            showReplyPanel(false, null, null, null, false);
            DraftQuery.cleanDraft(this.dialog_id, true);
        } else if (photoEntry.path != null) {
            SendMessagesHelper.prepareSendingPhoto(photoEntry.path, null, this.dialog_id, this.replyingMessageObject, photoEntry.caption, photoEntry.stickers, null, photoEntry.ttl);
            showReplyPanel(false, null, null, null, false);
            DraftQuery.cleanDraft(this.dialog_id, true);
        }
    }

    public void setBotUser(String str) {
        if (this.inlineReturn != 0) {
            MessagesController.getInstance().sendBotStart(this.currentUser, str);
            return;
        }
        this.botUser = str;
        updateBottomOverlay();
    }

    protected void setIgnoreAttachOnPause(boolean z) {
        this.ignoreAttachOnPause = z;
    }

    public void setMessageObject(MessageObject messageObject) {
        this.messageObject = messageObject;
    }

    public void shareMyContact(final MessageObject messageObject) {
        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString("ShareYouPhoneNumberTitle", R.string.ShareYouPhoneNumberTitle));
        if (this.currentUser == null) {
            builder.setMessage(LocaleController.getString("AreYouSureShareMyContactInfo", R.string.AreYouSureShareMyContactInfo));
        } else if (this.currentUser.bot) {
            builder.setMessage(LocaleController.getString("AreYouSureShareMyContactInfoBot", R.string.AreYouSureShareMyContactInfoBot));
        } else {
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("AreYouSureShareMyContactInfoUser", R.string.AreYouSureShareMyContactInfoUser, new Object[]{C2488b.a().e("+" + UserConfig.getCurrentUser().phone), ContactsController.formatName(this.currentUser.first_name, this.currentUser.last_name)})));
        }
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                SendMessagesHelper.getInstance().sendMessage(UserConfig.getCurrentUser(), ChatActivity.this.dialog_id, messageObject, null, null);
                ChatActivity.this.moveScrollToLastMessage();
                ChatActivity.this.showReplyPanel(false, null, null, null, false);
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
    }

    public void showAlert(String str, String str2) {
        if (this.alertView != null && str != null && str2 != null) {
            if (this.alertView.getTag() != null) {
                this.alertView.setTag(null);
                if (this.alertViewAnimator != null) {
                    this.alertViewAnimator.cancel();
                    this.alertViewAnimator = null;
                }
                this.alertView.setVisibility(0);
                this.alertViewAnimator = new AnimatorSet();
                AnimatorSet animatorSet = this.alertViewAnimator;
                Animator[] animatorArr = new Animator[1];
                animatorArr[0] = ObjectAnimator.ofFloat(this.alertView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorSet.playTogether(animatorArr);
                this.alertViewAnimator.setDuration(200);
                this.alertViewAnimator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationCancel(Animator animator) {
                        if (ChatActivity.this.alertViewAnimator != null && ChatActivity.this.alertViewAnimator.equals(animator)) {
                            ChatActivity.this.alertViewAnimator = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (ChatActivity.this.alertViewAnimator != null && ChatActivity.this.alertViewAnimator.equals(animator)) {
                            ChatActivity.this.alertViewAnimator = null;
                        }
                    }
                });
                this.alertViewAnimator.start();
            }
            this.alertNameTextView.setText(str);
            this.alertTextView.setText(Emoji.replaceEmoji(str2.replace('\n', ' '), this.alertTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
            if (this.hideAlertViewRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(this.hideAlertViewRunnable);
            }
            Runnable anonymousClass97 = new Runnable() {

                /* renamed from: org.telegram.ui.ChatActivity$97$1 */
                class C42741 extends AnimatorListenerAdapter {
                    C42741() {
                    }

                    public void onAnimationCancel(Animator animator) {
                        if (ChatActivity.this.alertViewAnimator != null && ChatActivity.this.alertViewAnimator.equals(animator)) {
                            ChatActivity.this.alertViewAnimator = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (ChatActivity.this.alertViewAnimator != null && ChatActivity.this.alertViewAnimator.equals(animator)) {
                            ChatActivity.this.alertView.setVisibility(8);
                            ChatActivity.this.alertViewAnimator = null;
                        }
                    }
                }

                public void run() {
                    if (ChatActivity.this.hideAlertViewRunnable == this && ChatActivity.this.alertView.getTag() == null) {
                        ChatActivity.this.alertView.setTag(Integer.valueOf(1));
                        if (ChatActivity.this.alertViewAnimator != null) {
                            ChatActivity.this.alertViewAnimator.cancel();
                            ChatActivity.this.alertViewAnimator = null;
                        }
                        ChatActivity.this.alertViewAnimator = new AnimatorSet();
                        AnimatorSet access$16300 = ChatActivity.this.alertViewAnimator;
                        Animator[] animatorArr = new Animator[1];
                        animatorArr[0] = ObjectAnimator.ofFloat(ChatActivity.this.alertView, "translationY", new float[]{(float) (-AndroidUtilities.dp(50.0f))});
                        access$16300.playTogether(animatorArr);
                        ChatActivity.this.alertViewAnimator.setDuration(200);
                        ChatActivity.this.alertViewAnimator.addListener(new C42741());
                        ChatActivity.this.alertViewAnimator.start();
                    }
                }
            };
            this.hideAlertViewRunnable = anonymousClass97;
            AndroidUtilities.runOnUIThread(anonymousClass97, 3000);
        }
    }

    public void showOpenGameAlert(TLRPC$TL_game tLRPC$TL_game, MessageObject messageObject, String str, boolean z, int i) {
        User user = MessagesController.getInstance().getUser(Integer.valueOf(i));
        String formatName;
        if (z) {
            org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            formatName = user != null ? ContactsController.formatName(user.first_name, user.last_name) : TtmlNode.ANONYMOUS_REGION_ID;
            builder.setMessage(LocaleController.formatString("BotPermissionGameAlert", R.string.BotPermissionGameAlert, new Object[]{formatName}));
            final TLRPC$TL_game tLRPC$TL_game2 = tLRPC$TL_game;
            final MessageObject messageObject2 = messageObject;
            final String str2 = str;
            final int i2 = i;
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ChatActivity.this.showOpenGameAlert(tLRPC$TL_game2, messageObject2, str2, false, i2);
                    ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putBoolean("askgame_" + i2, false).commit();
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            showDialog(builder.create());
        } else if (VERSION.SDK_INT < 21 || AndroidUtilities.isTablet() || !WebviewActivity.supportWebview()) {
            Activity parentActivity = getParentActivity();
            String str3 = tLRPC$TL_game.short_name;
            formatName = (user == null || user.username == null) ? TtmlNode.ANONYMOUS_REGION_ID : user.username;
            WebviewActivity.openGameInBrowser(str, messageObject, parentActivity, str3, formatName);
        } else if (this.parentLayout.fragmentsStack.get(this.parentLayout.fragmentsStack.size() - 1) == this) {
            String str4 = (user == null || TextUtils.isEmpty(user.username)) ? TtmlNode.ANONYMOUS_REGION_ID : user.username;
            presentFragment(new WebviewActivity(str, str4, tLRPC$TL_game.title, tLRPC$TL_game.short_name, messageObject));
        }
    }

    public void showOpenUrlAlert(final String str, boolean z) {
        boolean z2 = true;
        if (Browser.isInternalUrl(str, null) || !z) {
            Context parentActivity = getParentActivity();
            if (this.inlineReturn != 0) {
                z2 = false;
            }
            Browser.openUrl(parentActivity, str, z2);
            return;
        }
        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
        builder.setMessage(LocaleController.formatString("OpenUrlAlert", R.string.OpenUrlAlert, new Object[]{str}));
        builder.setPositiveButton(LocaleController.getString("Open", R.string.Open), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Browser.openUrl(ChatActivity.this.getParentActivity(), str, ChatActivity.this.inlineReturn == 0);
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
    }

    public void showReplyPanel(boolean z, MessageObject messageObject, ArrayList<MessageObject> arrayList, TLRPC$WebPage tLRPC$WebPage, boolean z2) {
        if (this.chatActivityEnterView != null) {
            if (z) {
                if (messageObject != null || arrayList != null || tLRPC$WebPage != null) {
                    boolean z3;
                    MessageObject messageObject2;
                    ArrayList arrayList2;
                    int i;
                    if (this.searchItem != null && this.actionBar.isSearchFieldVisible()) {
                        this.actionBar.closeSearchField(false);
                        this.chatActivityEnterView.setFieldFocused();
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (ChatActivity.this.chatActivityEnterView != null) {
                                    ChatActivity.this.chatActivityEnterView.openKeyboard();
                                }
                            }
                        }, 100);
                    }
                    if (messageObject == null || messageObject.getDialogId() == this.dialog_id) {
                        z3 = false;
                        messageObject2 = messageObject;
                    } else {
                        arrayList2 = new ArrayList();
                        arrayList2.add(messageObject);
                        z3 = true;
                        messageObject2 = null;
                    }
                    User user;
                    CharSequence userName;
                    String charSequence;
                    if (messageObject2 != null) {
                        this.forwardingMessages = null;
                        this.replyingMessageObject = messageObject2;
                        this.chatActivityEnterView.setReplyingMessageObject(messageObject2);
                        if (this.foundWebPage == null) {
                            if (messageObject2.isFromUser()) {
                                user = MessagesController.getInstance().getUser(Integer.valueOf(messageObject2.messageOwner.from_id));
                                if (user != null) {
                                    userName = UserObject.getUserName(user);
                                } else {
                                    return;
                                }
                            }
                            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(messageObject2.messageOwner.to_id.channel_id));
                            if (chat != null) {
                                userName = chat.title;
                            } else {
                                return;
                            }
                            this.replyIconImageView.setImageResource(R.drawable.msg_panel_reply);
                            this.replyNameTextView.setText(userName);
                            if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                                this.replyObjectTextView.setText(Emoji.replaceEmoji(messageObject2.messageOwner.media.game.title, this.replyObjectTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                            } else if (messageObject2.messageText != null) {
                                charSequence = messageObject2.messageText.toString();
                                if (charSequence.length() > 150) {
                                    charSequence = charSequence.substring(0, 150);
                                }
                                this.replyObjectTextView.setText(Emoji.replaceEmoji(charSequence.replace('\n', ' '), this.replyObjectTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                            }
                        } else {
                            return;
                        }
                    } else if (arrayList2 == null) {
                        this.replyIconImageView.setImageResource(R.drawable.msg_panel_link);
                        if (tLRPC$WebPage instanceof TLRPC$TL_webPagePending) {
                            this.replyNameTextView.setText(LocaleController.getString("GettingLinkInfo", R.string.GettingLinkInfo));
                            this.replyObjectTextView.setText(this.pendingLinkSearchString);
                        } else {
                            if (tLRPC$WebPage.site_name != null) {
                                this.replyNameTextView.setText(tLRPC$WebPage.site_name);
                            } else if (tLRPC$WebPage.title != null) {
                                this.replyNameTextView.setText(tLRPC$WebPage.title);
                            } else {
                                this.replyNameTextView.setText(LocaleController.getString("LinkPreview", R.string.LinkPreview));
                            }
                            if (tLRPC$WebPage.title != null) {
                                this.replyObjectTextView.setText(tLRPC$WebPage.title);
                            } else if (tLRPC$WebPage.description != null) {
                                this.replyObjectTextView.setText(tLRPC$WebPage.description);
                            } else if (tLRPC$WebPage.author != null) {
                                this.replyObjectTextView.setText(tLRPC$WebPage.author);
                            } else {
                                this.replyObjectTextView.setText(tLRPC$WebPage.display_url);
                            }
                            this.chatActivityEnterView.setWebPage(tLRPC$WebPage, true);
                        }
                    } else if (!arrayList2.isEmpty()) {
                        this.replyingMessageObject = null;
                        this.chatActivityEnterView.setReplyingMessageObject(null);
                        this.forwardingMessages = arrayList2;
                        if (this.foundWebPage == null) {
                            int i2;
                            this.chatActivityEnterView.setForceShowSendButton(true, false);
                            ArrayList arrayList3 = new ArrayList();
                            this.replyIconImageView.setImageResource(R.drawable.msg_panel_forward);
                            MessageObject messageObject3 = (MessageObject) arrayList2.get(0);
                            if (messageObject3.isFromUser()) {
                                arrayList3.add(Integer.valueOf(messageObject3.messageOwner.from_id));
                            } else {
                                arrayList3.add(Integer.valueOf(-messageObject3.messageOwner.to_id.channel_id));
                            }
                            i = ((MessageObject) arrayList2.get(0)).type;
                            for (i2 = 1; i2 < arrayList2.size(); i2++) {
                                messageObject3 = (MessageObject) arrayList2.get(i2);
                                Object valueOf = messageObject3.isFromUser() ? Integer.valueOf(messageObject3.messageOwner.from_id) : Integer.valueOf(-messageObject3.messageOwner.to_id.channel_id);
                                if (!arrayList3.contains(valueOf)) {
                                    arrayList3.add(valueOf);
                                }
                                if (((MessageObject) arrayList2.get(i2)).type != i) {
                                    i = -1;
                                }
                            }
                            CharSequence stringBuilder = new StringBuilder();
                            for (i2 = 0; i2 < arrayList3.size(); i2++) {
                                Chat chat2;
                                Integer num = (Integer) arrayList3.get(i2);
                                if (num.intValue() > 0) {
                                    user = MessagesController.getInstance().getUser(num);
                                    chat2 = null;
                                } else {
                                    chat2 = MessagesController.getInstance().getChat(Integer.valueOf(-num.intValue()));
                                    user = null;
                                }
                                if (user != null || chat2 != null) {
                                    if (arrayList3.size() != 1) {
                                        if (arrayList3.size() != 2 && stringBuilder.length() != 0) {
                                            stringBuilder.append(" ");
                                            stringBuilder.append(LocaleController.formatPluralString("AndOther", arrayList3.size() - 1));
                                            break;
                                        }
                                        if (stringBuilder.length() > 0) {
                                            stringBuilder.append(", ");
                                        }
                                        if (user == null) {
                                            stringBuilder.append(chat2.title);
                                        } else if (!TextUtils.isEmpty(user.first_name)) {
                                            stringBuilder.append(user.first_name);
                                        } else if (TextUtils.isEmpty(user.last_name)) {
                                            stringBuilder.append(" ");
                                        } else {
                                            stringBuilder.append(user.last_name);
                                        }
                                    } else if (user != null) {
                                        stringBuilder.append(UserObject.getUserName(user));
                                    } else {
                                        stringBuilder.append(chat2.title);
                                    }
                                }
                            }
                            this.replyNameTextView.setText(stringBuilder);
                            if (i != -1 && i != 0 && i != 10 && i != 11) {
                                if (i == 1) {
                                    this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedPhoto", arrayList2.size()));
                                    if (arrayList2.size() == 1) {
                                        messageObject3 = (MessageObject) arrayList2.get(0);
                                    }
                                } else if (i == 4) {
                                    this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedLocation", arrayList2.size()));
                                    messageObject3 = messageObject2;
                                } else if (i == 3) {
                                    this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedVideo", arrayList2.size()));
                                    if (arrayList2.size() == 1) {
                                        messageObject3 = (MessageObject) arrayList2.get(0);
                                    }
                                } else if (i == 12) {
                                    this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedContact", arrayList2.size()));
                                    messageObject3 = messageObject2;
                                } else if (i == 2) {
                                    this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedAudio", arrayList2.size()));
                                    messageObject3 = messageObject2;
                                } else if (i == 5) {
                                    this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedRound", arrayList2.size()));
                                    messageObject3 = messageObject2;
                                } else if (i == 14) {
                                    this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedMusic", arrayList2.size()));
                                    messageObject3 = messageObject2;
                                } else if (i == 13) {
                                    this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedSticker", arrayList2.size()));
                                    messageObject3 = messageObject2;
                                } else if (i == 8 || i == 9) {
                                    if (arrayList2.size() != 1) {
                                        this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedFile", arrayList2.size()));
                                    } else if (i == 8) {
                                        this.replyObjectTextView.setText(LocaleController.getString("AttachGif", R.string.AttachGif));
                                        messageObject3 = messageObject2;
                                    } else {
                                        userName = FileLoader.getDocumentFileName(((MessageObject) arrayList2.get(0)).getDocument());
                                        if (userName.length() != 0) {
                                            this.replyObjectTextView.setText(userName);
                                        }
                                        messageObject3 = (MessageObject) arrayList2.get(0);
                                    }
                                }
                                messageObject3 = messageObject2;
                            } else if (arrayList2.size() != 1 || ((MessageObject) arrayList2.get(0)).messageText == null) {
                                this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedMessageCount", arrayList2.size()));
                                messageObject3 = messageObject2;
                            } else {
                                messageObject3 = (MessageObject) arrayList2.get(0);
                                if (messageObject3.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                                    this.replyObjectTextView.setText(Emoji.replaceEmoji(messageObject3.messageOwner.media.game.title, this.replyObjectTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                                } else {
                                    charSequence = messageObject3.messageText.toString();
                                    if (charSequence.length() > 150) {
                                        charSequence = charSequence.substring(0, 150);
                                    }
                                    this.replyObjectTextView.setText(Emoji.replaceEmoji(charSequence.replace('\n', ' '), this.replyObjectTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                                }
                                messageObject3 = messageObject2;
                            }
                            messageObject2 = messageObject3;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.replyNameTextView.getLayoutParams();
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.replyObjectTextView.getLayoutParams();
                    PhotoSize photoSize = null;
                    if (messageObject2 != null) {
                        photoSize = FileLoader.getClosestPhotoSizeWithSize(messageObject2.photoThumbs2, 80);
                        if (photoSize == null) {
                            photoSize = FileLoader.getClosestPhotoSizeWithSize(messageObject2.photoThumbs, 80);
                        }
                    }
                    if (photoSize == null || (photoSize instanceof TLRPC$TL_photoSizeEmpty) || (photoSize.location instanceof TLRPC$TL_fileLocationUnavailable) || messageObject2.type == 13 || (messageObject2 != null && messageObject2.isSecretMedia())) {
                        this.replyImageView.setImageBitmap(null);
                        this.replyImageLocation = null;
                        this.replyImageView.setVisibility(4);
                        i = AndroidUtilities.dp(52.0f);
                        layoutParams2.leftMargin = i;
                        layoutParams.leftMargin = i;
                    } else {
                        if (messageObject2.isRoundVideo()) {
                            this.replyImageView.setRoundRadius(AndroidUtilities.dp(17.0f));
                        } else {
                            this.replyImageView.setRoundRadius(0);
                        }
                        this.replyImageLocation = photoSize.location;
                        this.replyImageView.setImage(this.replyImageLocation, "50_50", (Drawable) null);
                        this.replyImageView.setVisibility(0);
                        i = AndroidUtilities.dp(96.0f);
                        layoutParams2.leftMargin = i;
                        layoutParams.leftMargin = i;
                    }
                    this.replyNameTextView.setLayoutParams(layoutParams);
                    this.replyObjectTextView.setLayoutParams(layoutParams2);
                    this.chatActivityEnterView.showTopView(false, z3);
                }
            } else if (this.replyingMessageObject != null || this.forwardingMessages != null || this.foundWebPage != null) {
                if (this.replyingMessageObject != null && (this.replyingMessageObject.messageOwner.reply_markup instanceof TLRPC$TL_replyKeyboardForceReply)) {
                    ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("answered_" + this.dialog_id, this.replyingMessageObject.getId()).commit();
                }
                if (this.foundWebPage != null) {
                    this.foundWebPage = null;
                    this.chatActivityEnterView.setWebPage(null, !z2);
                    if (!(tLRPC$WebPage == null || (this.replyingMessageObject == null && this.forwardingMessages == null))) {
                        showReplyPanel(true, this.replyingMessageObject, this.forwardingMessages, null, false);
                        return;
                    }
                }
                if (this.forwardingMessages != null) {
                    forwardMessages(this.forwardingMessages, !QuoteForward);
                }
                this.chatActivityEnterView.setForceShowSendButton(false, false);
                this.chatActivityEnterView.hideTopView(false);
                this.chatActivityEnterView.setReplyingMessageObject(null);
                this.replyingMessageObject = null;
                this.forwardingMessages = null;
                this.replyImageLocation = null;
            }
        }
    }
}
