package org.telegram.ui;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.support.v4.media.TransportMediator;
import android.support.v4.view.InputDeviceCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.SparseBooleanArray;
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
import android.view.ViewPropertyAnimator;
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
import com.persianswitch.okhttp3.internal.http.StatusLine;
import com.persianswitch.sdk.api.Response;
import com.persianswitch.sdk.api.Response.General;
import com.persianswitch.sdk.api.Response.Payment;
import com.persianswitch.sdk.api.Response.Status;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import org.apache.commons.lang3.ClassUtils;
import org.ir.talaeii.R;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.SLSSQLite.DatabaseHandler;
import org.telegram.customization.Activities.ScheduleDownloadActivity;
import org.telegram.customization.Activities.TagSearchActivity;
import org.telegram.customization.Adapters.UserViewAdapter;
import org.telegram.customization.Adapters.UserViewAdapter.OnQuickAccessClickListener;
import org.telegram.customization.Application.AppApplication;
import org.telegram.customization.Internet.BaseResponseModel;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.DialogStatus;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.Model.FilterResponse;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.customization.fetch.FetchService;
import org.telegram.customization.service.DownloadManagerService;
import org.telegram.customization.util.IntentMaker;
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
import org.telegram.messenger.MediaController$PhotoEntry;
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
import org.telegram.messenger.SendMessagesHelper$SendingMediaInfo;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
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
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.SimpleCallback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$BotInfo;
import org.telegram.tgnet.TLRPC$BotInlineResult;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ChatParticipant;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$DraftMessage;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$InputStickerSet;
import org.telegram.tgnet.TLRPC$KeyboardButton;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_botCommand;
import org.telegram.tgnet.TLRPC$TL_channelForbidden;
import org.telegram.tgnet.TLRPC$TL_channelFull;
import org.telegram.tgnet.TLRPC$TL_channelParticipantAdmin;
import org.telegram.tgnet.TLRPC$TL_channelParticipantCreator;
import org.telegram.tgnet.TLRPC$TL_channels_channelParticipant;
import org.telegram.tgnet.TLRPC$TL_channels_exportMessageLink;
import org.telegram.tgnet.TLRPC$TL_channels_getParticipant;
import org.telegram.tgnet.TLRPC$TL_channels_reportSpam;
import org.telegram.tgnet.TLRPC$TL_chatForbidden;
import org.telegram.tgnet.TLRPC$TL_chatFull;
import org.telegram.tgnet.TLRPC$TL_chatParticipantsForbidden;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
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
import org.telegram.tgnet.TLRPC$TL_keyboardButtonSwitchInline;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonUrl;
import org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser;
import org.telegram.tgnet.TLRPC$TL_messageActionEmpty;
import org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall;
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
import org.telegram.tgnet.TLRPC$TL_messages_messages;
import org.telegram.tgnet.TLRPC$TL_messages_readMentions;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
import org.telegram.tgnet.TLRPC$TL_replyInlineMarkup;
import org.telegram.tgnet.TLRPC$TL_replyKeyboardForceReply;
import org.telegram.tgnet.TLRPC$TL_user;
import org.telegram.tgnet.TLRPC$TL_userFull;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.tgnet.TLRPC$TL_webPagePending;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet.Builder;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.Theme.ThemeInfo;
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
import org.telegram.ui.Components.StickersAlert;
import org.telegram.ui.Components.StickersAlert.StickersAlertDelegate;
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
import utils.app.AppPreferences;
import utils.view.Constants;
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
    private PhotoViewerProvider botContextProvider = new C24053();
    private ArrayList<Object> botContextResults;
    private HashMap<Integer, TLRPC$BotInfo> botInfo = new HashMap();
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
    protected TLRPC$Chat currentChat;
    protected TLRPC$EncryptedChat currentEncryptedChat;
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
    protected TLRPC$ChatFull info;
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
    OnItemClickListenerExtended onItemClickListener = new C24135();
    OnItemLongClickListener onItemLongClickListener = new C24104();
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
    private PhotoViewerProvider photoViewerProvider = new C23961();
    private TLRPC$FileLocation pinnedImageLocation;
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
    private Runnable readRunnable = new C23992();
    private boolean readWhenResume;
    private int readWithDate;
    private int readWithMid;
    private AnimatorSet replyButtonAnimation;
    private ImageView replyCloseImageView;
    private ImageView replyIconImageView;
    private TLRPC$FileLocation replyImageLocation;
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
    class C23961 extends EmptyPhotoViewerProvider {
        C23961() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
            int count = ChatActivity.this.chatListView.getChildCount();
            for (int a = 0; a < count; a++) {
                ImageReceiver imageReceiver = null;
                View view = ChatActivity.this.chatListView.getChildAt(a);
                MessageObject message;
                if (view instanceof ChatMessageCell) {
                    if (messageObject != null) {
                        ChatMessageCell cell = (ChatMessageCell) view;
                        message = cell.getMessageObject();
                        if (message != null && message.getId() == messageObject.getId()) {
                            imageReceiver = cell.getPhotoImage();
                        }
                    }
                } else if (view instanceof ChatActionCell) {
                    ChatActionCell cell2 = (ChatActionCell) view;
                    message = cell2.getMessageObject();
                    if (message != null) {
                        if (messageObject != null) {
                            if (message.getId() == messageObject.getId()) {
                                imageReceiver = cell2.getPhotoImage();
                            }
                        } else if (fileLocation != null && message.photoThumbs != null) {
                            for (int b = 0; b < message.photoThumbs.size(); b++) {
                                TLRPC$PhotoSize photoSize = (TLRPC$PhotoSize) message.photoThumbs.get(b);
                                if (photoSize.location.volume_id == fileLocation.volume_id && photoSize.location.local_id == fileLocation.local_id) {
                                    imageReceiver = cell2.getPhotoImage();
                                    break;
                                }
                            }
                        }
                    }
                }
                if (imageReceiver != null) {
                    int[] coords = new int[2];
                    view.getLocationInWindow(coords);
                    PlaceProviderObject object = new PlaceProviderObject();
                    object.viewX = coords[0];
                    object.viewY = coords[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight);
                    object.parentView = ChatActivity.this.chatListView;
                    object.imageReceiver = imageReceiver;
                    object.thumb = imageReceiver.getBitmap();
                    object.radius = imageReceiver.getRoundRadius();
                    if ((view instanceof ChatActionCell) && ChatActivity.this.currentChat != null) {
                        object.dialogId = -ChatActivity.this.currentChat.id;
                    }
                    if ((ChatActivity.this.pinnedMessageView == null || ChatActivity.this.pinnedMessageView.getTag() != null) && (ChatActivity.this.reportSpamView == null || ChatActivity.this.reportSpamView.getTag() != null)) {
                        return object;
                    }
                    object.clipTopAddition = AndroidUtilities.dp(48.0f);
                    return object;
                }
            }
            return null;
        }
    }

    /* renamed from: org.telegram.ui.ChatActivity$2 */
    class C23992 implements Runnable {
        C23992() {
        }

        public void run() {
            if (ChatActivity.this.readWhenResume && !ChatActivity.this.messages.isEmpty()) {
                for (int a = 0; a < ChatActivity.this.messages.size(); a++) {
                    MessageObject messageObject = (MessageObject) ChatActivity.this.messages.get(a);
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
    class C24053 extends EmptyPhotoViewerProvider {
        C24053() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
            PlaceProviderObject placeProviderObject = null;
            int i = 0;
            if (index >= 0 && index < ChatActivity.this.botContextResults.size()) {
                int count = ChatActivity.this.mentionListView.getChildCount();
                TLRPC$BotInlineResult result = ChatActivity.this.botContextResults.get(index);
                int a = 0;
                while (a < count) {
                    ImageReceiver imageReceiver = null;
                    View view = ChatActivity.this.mentionListView.getChildAt(a);
                    if (view instanceof ContextLinkCell) {
                        ContextLinkCell cell = (ContextLinkCell) view;
                        if (cell.getResult() == result) {
                            imageReceiver = cell.getPhotoImage();
                        }
                    }
                    if (imageReceiver != null) {
                        int[] coords = new int[2];
                        view.getLocationInWindow(coords);
                        placeProviderObject = new PlaceProviderObject();
                        placeProviderObject.viewX = coords[0];
                        int i2 = coords[1];
                        if (VERSION.SDK_INT < 21) {
                            i = AndroidUtilities.statusBarHeight;
                        }
                        placeProviderObject.viewY = i2 - i;
                        placeProviderObject.parentView = ChatActivity.this.mentionListView;
                        placeProviderObject.imageReceiver = imageReceiver;
                        placeProviderObject.thumb = imageReceiver.getBitmap();
                        placeProviderObject.radius = imageReceiver.getRoundRadius();
                    } else {
                        a++;
                    }
                }
            }
            return placeProviderObject;
        }

        public void sendButtonPressed(int index, VideoEditedInfo videoEditedInfo) {
            if (index >= 0 && index < ChatActivity.this.botContextResults.size()) {
                ChatActivity.this.sendBotInlineResult((TLRPC$BotInlineResult) ChatActivity.this.botContextResults.get(index));
            }
        }
    }

    /* renamed from: org.telegram.ui.ChatActivity$4 */
    class C24104 implements OnItemLongClickListener {
        C24104() {
        }

        public boolean onItemClick(View view, int position) {
            if (ChatActivity.this.actionBar.isActionModeShowed()) {
                return false;
            }
            ChatActivity.this.createMenu(view, false, true);
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChatActivity$5 */
    class C24135 implements OnItemClickListenerExtended {
        C24135() {
        }

        public void onItemClick(View view, int position, float x, float y) {
            if (ChatActivity.this.actionBar.isActionModeShowed()) {
                boolean outside = false;
                if (view instanceof ChatMessageCell) {
                    if (((ChatMessageCell) view).isInsideBackground(x, y)) {
                        outside = false;
                    } else {
                        outside = true;
                    }
                }
                ChatActivity.this.processRowSelect(view, outside);
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
        class C24401 implements ChatMessageCellDelegate {

            /* renamed from: org.telegram.ui.ChatActivity$ChatActivityAdapter$1$1 */
            class C24381 implements IResponseReceiver {
                C24381() {
                }

                public void onResult(Object object, int StatusCode) {
                    switch (StatusCode) {
                        case Constants.ERROR_ADS_LOGS /*-23*/:
                            Log.d("alireza", "alireza log NOT send");
                            return;
                        case 23:
                            ToastUtil.AppToast(ApplicationLoader.applicationContext, ((BaseResponseModel) object).getMessage()).show();
                            Log.d("alireza", "alireza log send");
                            return;
                        default:
                            return;
                    }
                }
            }

            C24401() {
            }

            public void didPressedShare(ChatMessageCell cell) {
                if (ChatActivity.this.getParentActivity() != null) {
                    if (ChatActivity.this.chatActivityEnterView != null) {
                        ChatActivity.this.chatActivityEnterView.closeKeyboard();
                    }
                    MessageObject messageObject = cell.getMessageObject();
                    if (!UserObject.isUserSelf(ChatActivity.this.currentUser) || messageObject.messageOwner.fwd_from.saved_from_peer == null) {
                        ArrayList<MessageObject> arrayList = null;
                        if (messageObject.getGroupId() != 0) {
                            GroupedMessages groupedMessages = (GroupedMessages) ChatActivity.this.groupedMessagesMap.get(Long.valueOf(messageObject.getGroupId()));
                            if (groupedMessages != null) {
                                arrayList = groupedMessages.messages;
                            }
                        }
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            arrayList.add(messageObject);
                        }
                        ChatActivity chatActivity = ChatActivity.this;
                        Context access$17700 = ChatActivityAdapter.this.mContext;
                        boolean z = ChatObject.isChannel(ChatActivity.this.currentChat) && !ChatActivity.this.currentChat.megagroup && ChatActivity.this.currentChat.username != null && ChatActivity.this.currentChat.username.length() > 0;
                        chatActivity.showDialog(new ShareAlert(access$17700, arrayList, null, z, null, false));
                        return;
                    }
                    Bundle args = new Bundle();
                    if (messageObject.messageOwner.fwd_from.saved_from_peer.channel_id != 0) {
                        args.putInt("chat_id", messageObject.messageOwner.fwd_from.saved_from_peer.channel_id);
                    } else if (messageObject.messageOwner.fwd_from.saved_from_peer.chat_id != 0) {
                        args.putInt("chat_id", messageObject.messageOwner.fwd_from.saved_from_peer.chat_id);
                    } else if (messageObject.messageOwner.fwd_from.saved_from_peer.user_id != 0) {
                        args.putInt("user_id", messageObject.messageOwner.fwd_from.saved_from_peer.user_id);
                    }
                    args.putInt("message_id", messageObject.messageOwner.fwd_from.saved_from_msg_id);
                    if (MessagesController.checkCanOpenChat(args, ChatActivity.this)) {
                        ChatActivity.this.presentFragment(new ChatActivity(args));
                    }
                }
            }

            public boolean needPlayMessage(MessageObject messageObject) {
                if (!messageObject.isVoice() && !messageObject.isRoundVideo()) {
                    return messageObject.isMusic() ? MediaController.getInstance().setPlaylist(ChatActivity.this.messages, messageObject) : false;
                } else {
                    boolean result = MediaController.getInstance().playMessage(messageObject);
                    MediaController.getInstance().setVoiceMessagesPlaylist(result ? ChatActivity.this.createVoiceMessagesPlaylist(messageObject, false) : null, false);
                    return result;
                }
            }

            public void didPressedChannelAvatar(ChatMessageCell cell, TLRPC$Chat chat, int postId) {
                if (ChatActivity.this.actionBar.isActionModeShowed()) {
                    ChatActivity.this.processRowSelect(cell, true);
                } else if (chat != null && chat != ChatActivity.this.currentChat) {
                    Bundle args = new Bundle();
                    args.putInt("chat_id", chat.id);
                    if (postId != 0) {
                        args.putInt("message_id", postId);
                    }
                    if (MessagesController.checkCanOpenChat(args, ChatActivity.this, cell.getMessageObject())) {
                        ChatActivity.this.presentFragment(new ChatActivity(args), true);
                    }
                }
            }

            public void didPressedOther(ChatMessageCell cell) {
                if (cell.getMessageObject().type != 16) {
                    ChatActivity.this.createMenu(cell, true, false, false);
                } else if (ChatActivity.this.currentUser != null) {
                    VoIPHelper.startCall(ChatActivity.this.currentUser, ChatActivity.this.getParentActivity(), MessagesController.getInstance().getUserFull(ChatActivity.this.currentUser.id));
                }
            }

            public void didPressedUserAvatar(ChatMessageCell cell, User user) {
                boolean z = true;
                if (ChatActivity.this.actionBar.isActionModeShowed()) {
                    ChatActivity.this.processRowSelect(cell, true);
                } else if (user != null && user.id != UserConfig.getClientUserId()) {
                    Bundle args = new Bundle();
                    args.putInt("user_id", user.id);
                    ProfileActivity fragment = new ProfileActivity(args);
                    if (ChatActivity.this.currentUser == null || ChatActivity.this.currentUser.id != user.id) {
                        z = false;
                    }
                    fragment.setPlayProfileAnimation(z);
                    ChatActivity.this.presentFragment(fragment);
                }
            }

            public void didPressedBotButton(ChatMessageCell cell, TLRPC$KeyboardButton button) {
                if (ChatActivity.this.getParentActivity() == null) {
                    return;
                }
                if (ChatActivity.this.bottomOverlayChat.getVisibility() != 0 || (button instanceof TLRPC$TL_keyboardButtonSwitchInline) || (button instanceof TLRPC$TL_keyboardButtonCallback) || (button instanceof TLRPC$TL_keyboardButtonGame) || (button instanceof TLRPC$TL_keyboardButtonUrl) || (button instanceof TLRPC$TL_keyboardButtonBuy)) {
                    ChatActivity.this.chatActivityEnterView.didPressedBotButton(button, cell.getMessageObject(), cell.getMessageObject());
                }
            }

            public void didPressedCancelSendButton(ChatMessageCell cell) {
                MessageObject message = cell.getMessageObject();
                if (message.messageOwner.send_state != 0) {
                    SendMessagesHelper.getInstance().cancelSendingMessage(message);
                }
            }

            public void didLongPressed(ChatMessageCell cell) {
                ChatActivity.this.createMenu(cell, false, false);
            }

            public boolean canPerformActions() {
                return (ChatActivity.this.actionBar == null || ChatActivity.this.actionBar.isActionModeShowed()) ? false : true;
            }

            public void didPressedUrl(MessageObject messageObject, CharacterStyle url, boolean longPress) {
                if (url != null) {
                    String url2 = ((URLSpan) url).getURL();
                    if (AppPreferences.isAdsEnable(ApplicationLoader.applicationContext)) {
                        boolean userIsJoined = false;
                        Iterator it = AppPreferences.getAdsChannel(ApplicationLoader.applicationContext).iterator();
                        while (it.hasNext()) {
                            if (((Category) it.next()).getChannelId() == ChatActivity.this.chat_id) {
                                userIsJoined = true;
                                break;
                            }
                        }
                        if (userIsJoined) {
                            if (!url2.startsWith("@")) {
                                org.telegram.customization.Model.Ads.Log log = new org.telegram.customization.Model.Ads.Log();
                                log.setChannelId(ChatActivity.this.chat_id);
                                log.setMessageId(messageObject.getId());
                                log.setUrl(url2);
                                log.setAction(2);
                                HandleRequest.getNew(ChatActivity.this.getParentActivity(), new C24381()).adsLog(log);
                            }
                        }
                    }
                    if (url instanceof URLSpanMono) {
                        ((URLSpanMono) url).copyToClipboard();
                        Toast.makeText(ChatActivity.this.getParentActivity(), LocaleController.getString("TextCopied", R.string.TextCopied), 0).show();
                    } else if (url instanceof URLSpanUserMention) {
                        User user = MessagesController.getInstance().getUser(Utilities.parseInt(((URLSpanUserMention) url).getURL()));
                        if (user != null) {
                            MessagesController.openChatOrProfileWith(user, null, ChatActivity.this, 0, false);
                        }
                    } else if (url instanceof URLSpanNoUnderline) {
                        String str = ((URLSpanNoUnderline) url).getURL();
                        if (str.startsWith("@")) {
                            MessagesController.openByUserName(str.substring(1), ChatActivity.this, 0);
                        } else if (str.startsWith("#")) {
                            if (ChatObject.isChannel(ChatActivity.this.currentChat)) {
                                ChatActivity.this.openSearchWithText(str);
                                return;
                            }
                            DialogsActivity fragment = new DialogsActivity(null);
                            fragment.setSearchString(str);
                            ChatActivity.this.presentFragment(fragment);
                        } else if (str.startsWith("/") && URLSpanBotCommand.enabled) {
                            ChatActivityEnterView chatActivityEnterView = ChatActivity.this.chatActivityEnterView;
                            boolean z = ChatActivity.this.currentChat != null && ChatActivity.this.currentChat.megagroup;
                            chatActivityEnterView.setCommand(messageObject, str, longPress, z);
                            if (!longPress && ChatActivity.this.chatActivityEnterView.getFieldText() == null) {
                                ChatActivity.this.showReplyPanel(false, null, null, null, false);
                            }
                        }
                    } else {
                        String urlFinal = ((URLSpan) url).getURL();
                        if (longPress) {
                            Builder builder = new Builder(ChatActivity.this.getParentActivity());
                            builder.setTitle(urlFinal);
                            final String str2 = urlFinal;
                            builder.setItems(new CharSequence[]{LocaleController.getString("Open", R.string.Open), LocaleController.getString("Copy", R.string.Copy)}, new OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean z = true;
                                    if (which == 0) {
                                        Context parentActivity = ChatActivity.this.getParentActivity();
                                        String str = str2;
                                        if (ChatActivity.this.inlineReturn != 0) {
                                            z = false;
                                        }
                                        Browser.openUrl(parentActivity, str, z);
                                    } else if (which == 1) {
                                        String url = str2;
                                        if (url.startsWith("mailto:")) {
                                            url = url.substring(7);
                                        } else if (url.startsWith("tel:")) {
                                            url = url.substring(4);
                                        }
                                        AndroidUtilities.addToClipboard(url);
                                    }
                                }
                            });
                            ChatActivity.this.showDialog(builder.create());
                        } else if (url instanceof URLSpanReplacement) {
                            ChatActivity.this.showOpenUrlAlert(((URLSpanReplacement) url).getURL(), true);
                        } else if (url instanceof URLSpan) {
                            if (!(!(messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) || messageObject.messageOwner.media.webpage == null || messageObject.messageOwner.media.webpage.cached_page == null)) {
                                String lowerUrl = urlFinal.toLowerCase();
                                String lowerUrl2 = messageObject.messageOwner.media.webpage.url.toLowerCase();
                                if ((lowerUrl.contains("telegra.ph") || lowerUrl.contains("t.me/iv")) && (lowerUrl.contains(lowerUrl2) || lowerUrl2.contains(lowerUrl))) {
                                    ArticleViewer.getInstance().setParentActivity(ChatActivity.this.getParentActivity(), ChatActivity.this);
                                    ArticleViewer.getInstance().open(messageObject);
                                    return;
                                }
                            }
                            Browser.openUrl(ChatActivity.this.getParentActivity(), urlFinal, ChatActivity.this.inlineReturn == 0);
                        } else if (url instanceof ClickableSpan) {
                            ((ClickableSpan) url).onClick(ChatActivity.this.fragmentView);
                        }
                    }
                }
            }

            public void needOpenWebView(String url, String title, String description, String originalUrl, int w, int h) {
                EmbedBottomSheet.show(ChatActivityAdapter.this.mContext, title, description, originalUrl, url, w, h);
            }

            public void didPressedReplyMessage(ChatMessageCell cell, int id) {
                int i;
                MessageObject messageObject = cell.getMessageObject();
                ChatActivity chatActivity = ChatActivity.this;
                int id2 = messageObject.getId();
                if (messageObject.getDialogId() == ChatActivity.this.mergeDialogId) {
                    i = 1;
                } else {
                    i = 0;
                }
                chatActivity.scrollToMessageId(id, id2, true, i, false);
            }

            public void didPressedViaBot(ChatMessageCell cell, String username) {
                if (ChatActivity.this.bottomOverlayChat != null && ChatActivity.this.bottomOverlayChat.getVisibility() == 0) {
                    return;
                }
                if ((ChatActivity.this.bottomOverlay == null || ChatActivity.this.bottomOverlay.getVisibility() != 0) && ChatActivity.this.chatActivityEnterView != null && username != null && username.length() > 0) {
                    ChatActivity.this.chatActivityEnterView.setFieldText("@" + username + " ");
                    ChatActivity.this.chatActivityEnterView.openKeyboard();
                }
            }

            public void didTagPressed(String tag) {
                Bundle args = new Bundle();
                args.putString(Constants.EXTRA_TAG, tag);
                ChatActivity.this.presentFragment(new TagSearchActivity(args));
            }

            public void didPressedImage(ChatMessageCell cell) {
                MessageObject message = cell.getMessageObject();
                if (message.isSendError()) {
                    ChatActivity.this.createMenu(cell, false, false);
                } else if (!message.isSending()) {
                    if (message.isSecretPhoto()) {
                        if (ChatActivity.this.sendSecretMessageRead(message)) {
                            cell.invalidate();
                        }
                        SecretMediaViewer.getInstance().setParentActivity(ChatActivity.this.getParentActivity());
                        SecretMediaViewer.getInstance().openMedia(message, ChatActivity.this.photoViewerProvider);
                    } else if (message.type == 13) {
                        ChatActivity chatActivity = ChatActivity.this;
                        Context parentActivity = ChatActivity.this.getParentActivity();
                        BaseFragment baseFragment = ChatActivity.this;
                        TLRPC$InputStickerSet inputStickerSet = message.getInputStickerSet();
                        StickersAlertDelegate stickersAlertDelegate = (ChatActivity.this.bottomOverlayChat.getVisibility() == 0 || !ChatObject.canSendStickers(ChatActivity.this.currentChat)) ? null : ChatActivity.this.chatActivityEnterView;
                        chatActivity.showDialog(new StickersAlert(parentActivity, baseFragment, inputStickerSet, null, stickersAlertDelegate));
                    } else if (message.isVideo() || message.type == 1 || ((message.type == 0 && !message.isWebpageDocument()) || message.isGif())) {
                        if (message.isVideo()) {
                            ChatActivity.this.sendSecretMessageRead(message);
                        }
                        PhotoViewer.getInstance().setParentActivity(ChatActivity.this.getParentActivity());
                        if (PhotoViewer.getInstance().openPhoto(message, message.type != 0 ? ChatActivity.this.dialog_id : 0, message.type != 0 ? ChatActivity.this.mergeDialogId : 0, ChatActivity.this.photoViewerProvider)) {
                            PhotoViewer.getInstance().setParentChatActivity(ChatActivity.this);
                        }
                    } else if (message.type == 3) {
                        ChatActivity.this.sendSecretMessageRead(message);
                        f = null;
                        try {
                            if (!(message.messageOwner.attachPath == null || message.messageOwner.attachPath.length() == 0)) {
                                f = new File(message.messageOwner.attachPath);
                            }
                            if (f == null || !f.exists()) {
                                f = FileLoader.getPathToMessage(message.messageOwner);
                            }
                            Intent intent = new Intent("android.intent.action.VIEW");
                            if (VERSION.SDK_INT >= 24) {
                                intent.setFlags(1);
                                intent.setDataAndType(FileProvider.getUriForFile(ChatActivity.this.getParentActivity(), "org.ir.talaeii.provider", f), MimeTypes.VIDEO_MP4);
                            } else {
                                intent.setDataAndType(Uri.fromFile(f), MimeTypes.VIDEO_MP4);
                            }
                            ChatActivity.this.getParentActivity().startActivityForResult(intent, 500);
                        } catch (Exception e) {
                            FileLog.e(e);
                            ChatActivity.this.alertUserOpenError(message);
                        }
                    } else if (message.type == 4) {
                        if (!AndroidUtilities.isGoogleMapsInstalled(ChatActivity.this)) {
                            return;
                        }
                        LocationActivity fragment;
                        if (message.isLiveLocation()) {
                            fragment = new LocationActivity(2);
                            fragment.setMessageObject(message);
                            fragment.setDelegate(ChatActivity.this);
                            ChatActivity.this.presentFragment(fragment);
                            return;
                        }
                        fragment = new LocationActivity(ChatActivity.this.currentEncryptedChat == null ? 3 : 0);
                        fragment.setMessageObject(message);
                        fragment.setDelegate(ChatActivity.this);
                        ChatActivity.this.presentFragment(fragment);
                    } else if (message.type == 9 || message.type == 0) {
                        if (message.getDocumentName().endsWith("attheme")) {
                            File locFile = null;
                            if (!(message.messageOwner.attachPath == null || message.messageOwner.attachPath.length() == 0)) {
                                f = new File(message.messageOwner.attachPath);
                                if (f.exists()) {
                                    locFile = f;
                                }
                            }
                            if (locFile == null) {
                                f = FileLoader.getPathToMessage(message.messageOwner);
                                if (f.exists()) {
                                    locFile = f;
                                }
                            }
                            if (ChatActivity.this.chatLayoutManager != null) {
                                int lastPosition = ChatActivity.this.chatLayoutManager.findFirstVisibleItemPosition();
                                if (lastPosition != 0) {
                                    ChatActivity.this.scrollToPositionOnRecreate = lastPosition;
                                    Holder holder = (Holder) ChatActivity.this.chatListView.findViewHolderForAdapterPosition(ChatActivity.this.scrollToPositionOnRecreate);
                                    if (holder != null) {
                                        ChatActivity.this.scrollToOffsetOnRecreate = (ChatActivity.this.chatListView.getMeasuredHeight() - holder.itemView.getBottom()) - ChatActivity.this.chatListView.getPaddingBottom();
                                    } else {
                                        ChatActivity.this.scrollToPositionOnRecreate = -1;
                                    }
                                } else {
                                    ChatActivity.this.scrollToPositionOnRecreate = -1;
                                }
                            }
                            ThemeInfo themeInfo = Theme.applyThemeFile(locFile, message.getDocumentName(), true);
                            if (themeInfo != null) {
                                ChatActivity.this.presentFragment(new ThemePreviewActivity(locFile, themeInfo));
                                return;
                            }
                            ChatActivity.this.scrollToPositionOnRecreate = -1;
                        }
                        try {
                            AndroidUtilities.openForView(message, ChatActivity.this.getParentActivity());
                        } catch (Exception e2) {
                            FileLog.e(e2);
                            ChatActivity.this.alertUserOpenError(message);
                        }
                    }
                }
            }

            public void didPressedInstantButton(ChatMessageCell cell, int type) {
                MessageObject messageObject = cell.getMessageObject();
                if (type == 0) {
                    if (messageObject.messageOwner.media != null && messageObject.messageOwner.media.webpage != null && messageObject.messageOwner.media.webpage.cached_page != null) {
                        ArticleViewer.getInstance().setParentActivity(ChatActivity.this.getParentActivity(), ChatActivity.this);
                        ArticleViewer.getInstance().open(messageObject);
                    }
                } else if (messageObject.messageOwner.media != null && messageObject.messageOwner.media.webpage != null) {
                    Browser.openUrl(ChatActivity.this.getParentActivity(), messageObject.messageOwner.media.webpage.url);
                }
            }

            public boolean isChatAdminCell(int uid) {
                if (ChatObject.isChannel(ChatActivity.this.currentChat) && ChatActivity.this.currentChat.megagroup) {
                    return MessagesController.getInstance().isChannelAdmin(ChatActivity.this.currentChat.id, uid);
                }
                return false;
            }
        }

        /* renamed from: org.telegram.ui.ChatActivity$ChatActivityAdapter$2 */
        class C24412 implements ChatActionCellDelegate {
            C24412() {
            }

            public void didClickedImage(ChatActionCell cell) {
                MessageObject message = cell.getMessageObject();
                PhotoViewer.getInstance().setParentActivity(ChatActivity.this.getParentActivity());
                TLRPC$PhotoSize photoSize = FileLoader.getClosestPhotoSizeWithSize(message.photoThumbs, 640);
                if (photoSize != null) {
                    PhotoViewer.getInstance().openPhoto(photoSize.location, ChatActivity.this.photoViewerProvider);
                    return;
                }
                PhotoViewer.getInstance().openPhoto(message, 0, 0, ChatActivity.this.photoViewerProvider);
            }

            public void didLongPressed(ChatActionCell cell) {
                ChatActivity.this.createMenu(cell, false, false);
            }

            public void needOpenUserProfile(int uid) {
                boolean z = true;
                Bundle args;
                if (uid < 0) {
                    args = new Bundle();
                    args.putInt("chat_id", -uid);
                    if (MessagesController.checkCanOpenChat(args, ChatActivity.this)) {
                        ChatActivity.this.presentFragment(new ChatActivity(args), true);
                    }
                } else if (uid != UserConfig.getClientUserId()) {
                    args = new Bundle();
                    args.putInt("user_id", uid);
                    if (ChatActivity.this.currentEncryptedChat != null && uid == ChatActivity.this.currentUser.id) {
                        args.putLong("dialog_id", ChatActivity.this.dialog_id);
                    }
                    ProfileActivity fragment = new ProfileActivity(args);
                    if (ChatActivity.this.currentUser == null || ChatActivity.this.currentUser.id != uid) {
                        z = false;
                    }
                    fragment.setPlayProfileAnimation(z);
                    ChatActivity.this.presentFragment(fragment);
                }
            }

            public void didPressedReplyMessage(ChatActionCell cell, int id) {
                int i;
                MessageObject messageObject = cell.getMessageObject();
                ChatActivity chatActivity = ChatActivity.this;
                int id2 = messageObject.getId();
                if (messageObject.getDialogId() == ChatActivity.this.mergeDialogId) {
                    i = 1;
                } else {
                    i = 0;
                }
                chatActivity.scrollToMessageId(id, id2, true, i, false);
            }

            public void didPressedBotButton(MessageObject messageObject, TLRPC$KeyboardButton button) {
                if (ChatActivity.this.getParentActivity() == null) {
                    return;
                }
                if (ChatActivity.this.bottomOverlayChat.getVisibility() != 0 || (button instanceof TLRPC$TL_keyboardButtonSwitchInline) || (button instanceof TLRPC$TL_keyboardButtonCallback) || (button instanceof TLRPC$TL_keyboardButtonGame) || (button instanceof TLRPC$TL_keyboardButtonUrl) || (button instanceof TLRPC$TL_keyboardButtonBuy)) {
                    ChatActivity.this.chatActivityEnterView.didPressedBotButton(button, messageObject, messageObject);
                }
            }
        }

        /* renamed from: org.telegram.ui.ChatActivity$ChatActivityAdapter$3 */
        class C24423 implements BotHelpCellDelegate {
            C24423() {
            }

            public void didPressUrl(String url) {
                if (url.startsWith("@")) {
                    MessagesController.openByUserName(url.substring(1), ChatActivity.this, 0);
                } else if (url.startsWith("#")) {
                    DialogsActivity fragment = new DialogsActivity(null);
                    fragment.setSearchString(url);
                    ChatActivity.this.presentFragment(fragment);
                } else if (url.startsWith("/")) {
                    ChatActivity.this.chatActivityEnterView.setCommand(null, url, false, false);
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

        public int getItemCount() {
            return this.rowCount;
        }

        public long getItemId(int i) {
            return -1;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == 0) {
                if (ChatActivity.this.chatMessageCellsCache.isEmpty()) {
                    view = new ChatMessageCell(this.mContext);
                } else {
                    view = (View) ChatActivity.this.chatMessageCellsCache.get(0);
                    ChatActivity.this.chatMessageCellsCache.remove(0);
                }
                ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                chatMessageCell.setDelegate(new C24401());
                if (ChatActivity.this.currentEncryptedChat == null) {
                    chatMessageCell.setAllowAssistant(true);
                }
            } else if (viewType == 1) {
                view = new ChatActionCell(this.mContext);
                ((ChatActionCell) view).setDelegate(new C24412());
            } else if (viewType == 2) {
                view = new ChatUnreadCell(this.mContext);
            } else if (viewType == 3) {
                view = new BotHelpCell(this.mContext);
                ((BotHelpCell) view).setDelegate(new C24423());
            } else if (viewType == 4) {
                view = new ChatLoadingCell(this.mContext);
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position == this.botInfoRow) {
                String str;
                BotHelpCell helpView = holder.itemView;
                if (ChatActivity.this.botInfo.isEmpty()) {
                    str = null;
                } else {
                    str = ((TLRPC$BotInfo) ChatActivity.this.botInfo.get(Integer.valueOf(ChatActivity.this.currentUser.id))).description;
                }
                helpView.setText(str);
                return;
            }
            if (position == this.loadingDownRow || position == this.loadingUpRow) {
                holder.itemView.setProgressVisible(ChatActivity.this.loadsCount > 1);
                return;
            }
            if (position >= this.messagesStartRow && position < this.messagesEndRow) {
                MessageObject message = (MessageObject) ChatActivity.this.messages.get(position - this.messagesStartRow);
                View view = holder.itemView;
                if (view instanceof ChatMessageCell) {
                    int prevPosition;
                    int nextPosition;
                    int index;
                    final ChatMessageCell messageCell = (ChatMessageCell) view;
                    boolean z = ChatActivity.this.currentChat != null || UserObject.isUserSelf(ChatActivity.this.currentUser);
                    messageCell.isChat = z;
                    boolean pinnedBottom = false;
                    boolean pinnedTop = false;
                    GroupedMessages groupedMessages = ChatActivity.this.getValidGroupedMessage(message);
                    if (groupedMessages != null) {
                        GroupedMessagePosition pos = (GroupedMessagePosition) groupedMessages.positions.get(message);
                        if (pos != null) {
                            if ((pos.flags & 4) != 0) {
                                prevPosition = (groupedMessages.posArray.indexOf(pos) + position) + 1;
                            } else {
                                pinnedTop = true;
                                prevPosition = -100;
                            }
                            if ((pos.flags & 8) != 0) {
                                nextPosition = (position - groupedMessages.posArray.size()) + groupedMessages.posArray.indexOf(pos);
                            } else {
                                pinnedBottom = true;
                                nextPosition = -100;
                            }
                        } else {
                            prevPosition = -100;
                            nextPosition = -100;
                        }
                    } else {
                        nextPosition = position - 1;
                        prevPosition = position + 1;
                    }
                    int nextType = getItemViewType(nextPosition);
                    int prevType = getItemViewType(prevPosition);
                    if (!(message.messageOwner.reply_markup instanceof TLRPC$TL_replyInlineMarkup) && nextType == holder.getItemViewType()) {
                        MessageObject nextMessage = (MessageObject) ChatActivity.this.messages.get(nextPosition - this.messagesStartRow);
                        pinnedBottom = nextMessage.isOutOwner() == message.isOutOwner() && Math.abs(nextMessage.messageOwner.date - message.messageOwner.date) <= 300;
                        if (pinnedBottom) {
                            if (ChatActivity.this.currentChat != null) {
                                pinnedBottom = nextMessage.messageOwner.from_id == message.messageOwner.from_id;
                            } else if (UserObject.isUserSelf(ChatActivity.this.currentUser)) {
                                pinnedBottom = nextMessage.getFromId() == message.getFromId();
                            }
                        }
                    }
                    if (prevType == holder.getItemViewType()) {
                        MessageObject prevMessage = (MessageObject) ChatActivity.this.messages.get(prevPosition - this.messagesStartRow);
                        pinnedTop = !(prevMessage.messageOwner.reply_markup instanceof TLRPC$TL_replyInlineMarkup) && prevMessage.isOutOwner() == message.isOutOwner() && Math.abs(prevMessage.messageOwner.date - message.messageOwner.date) <= 300;
                        if (pinnedTop) {
                            if (ChatActivity.this.currentChat != null) {
                                pinnedTop = prevMessage.messageOwner.from_id == message.messageOwner.from_id;
                            } else if (UserObject.isUserSelf(ChatActivity.this.currentUser)) {
                                pinnedTop = prevMessage.getFromId() == message.getFromId();
                            }
                        }
                    }
                    messageCell.setMessageObject(message, groupedMessages, pinnedBottom, pinnedTop);
                    if ((view instanceof ChatMessageCell) && MediaController.getInstance().canDownloadMedia(message)) {
                        ((ChatMessageCell) view).downloadAudioIfNeed();
                    }
                    z = ChatActivity.this.highlightMessageId != Integer.MAX_VALUE && message.getId() == ChatActivity.this.highlightMessageId;
                    messageCell.setHighlighted(z);
                    if (ChatActivity.this.searchContainer != null && ChatActivity.this.searchContainer.getVisibility() == 0) {
                        if (MessagesSearchQuery.isMessageFound(message.getId(), message.getDialogId() == ChatActivity.this.mergeDialogId) && MessagesSearchQuery.getLastSearchQuery() != null) {
                            messageCell.setHighlightedText(MessagesSearchQuery.getLastSearchQuery());
                            index = ChatActivity.this.animatingMessageObjects.indexOf(message);
                            if (index != -1) {
                                ChatActivity.this.animatingMessageObjects.remove(index);
                                messageCell.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                                    public boolean onPreDraw() {
                                        PipRoundVideoView pipRoundVideoView = PipRoundVideoView.getInstance();
                                        if (pipRoundVideoView != null) {
                                            pipRoundVideoView.showTemporary(true);
                                        }
                                        messageCell.getViewTreeObserver().removeOnPreDrawListener(this);
                                        ImageReceiver imageReceiver = messageCell.getPhotoImage();
                                        float scale = ((float) imageReceiver.getImageWidth()) / ChatActivity.this.instantCameraView.getCameraRect().width;
                                        int[] position = new int[2];
                                        messageCell.setAlpha(0.0f);
                                        messageCell.getLocationOnScreen(position);
                                        position[0] = position[0] + imageReceiver.getImageX();
                                        position[1] = position[1] + imageReceiver.getImageY();
                                        final View cameraContainer = ChatActivity.this.instantCameraView.getCameraContainer();
                                        cameraContainer.setPivotX(0.0f);
                                        cameraContainer.setPivotY(0.0f);
                                        AnimatorSet animatorSet = new AnimatorSet();
                                        r8 = new Animator[8];
                                        r8[0] = ObjectAnimator.ofFloat(ChatActivity.this.instantCameraView, "alpha", new float[]{0.0f});
                                        r8[1] = ObjectAnimator.ofFloat(cameraContainer, "scaleX", new float[]{scale});
                                        r8[2] = ObjectAnimator.ofFloat(cameraContainer, "scaleY", new float[]{scale});
                                        r8[3] = ObjectAnimator.ofFloat(cameraContainer, "translationX", new float[]{((float) position[0]) - rect.f107x});
                                        r8[4] = ObjectAnimator.ofFloat(cameraContainer, "translationY", new float[]{((float) position[1]) - rect.f108y});
                                        r8[5] = ObjectAnimator.ofFloat(ChatActivity.this.instantCameraView.getSwitchButtonView(), "alpha", new float[]{0.0f});
                                        r8[6] = ObjectAnimator.ofInt(ChatActivity.this.instantCameraView.getPaint(), "alpha", new int[]{0});
                                        r8[7] = ObjectAnimator.ofFloat(ChatActivity.this.instantCameraView.getMuteImageView(), "alpha", new float[]{0.0f});
                                        animatorSet.playTogether(r8);
                                        animatorSet.setDuration(180);
                                        animatorSet.setInterpolator(new DecelerateInterpolator());
                                        animatorSet.addListener(new AnimatorListenerAdapter() {

                                            /* renamed from: org.telegram.ui.ChatActivity$ChatActivityAdapter$4$1$1 */
                                            class C24431 extends AnimatorListenerAdapter {
                                                C24431() {
                                                }

                                                public void onAnimationEnd(Animator animation) {
                                                    ChatActivity.this.instantCameraView.hideCamera(true);
                                                    ChatActivity.this.instantCameraView.setVisibility(4);
                                                }
                                            }

                                            public void onAnimationEnd(Animator animation) {
                                                AnimatorSet animatorSet = new AnimatorSet();
                                                r1 = new Animator[2];
                                                r1[0] = ObjectAnimator.ofFloat(cameraContainer, "alpha", new float[]{0.0f});
                                                r1[1] = ObjectAnimator.ofFloat(messageCell, "alpha", new float[]{1.0f});
                                                animatorSet.playTogether(r1);
                                                animatorSet.setDuration(100);
                                                animatorSet.setInterpolator(new DecelerateInterpolator());
                                                animatorSet.addListener(new C24431());
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
                    messageCell.setHighlightedText(null);
                    index = ChatActivity.this.animatingMessageObjects.indexOf(message);
                    if (index != -1) {
                        ChatActivity.this.animatingMessageObjects.remove(index);
                        messageCell.getViewTreeObserver().addOnPreDrawListener(/* anonymous class already generated */);
                    }
                } else if (view instanceof ChatActionCell) {
                    ChatActionCell actionCell = (ChatActionCell) view;
                    actionCell.setMessageObject(message);
                    actionCell.setAlpha(1.0f);
                } else if (view instanceof ChatUnreadCell) {
                    ((ChatUnreadCell) view).setText(LocaleController.formatPluralString("NewMessages", ChatActivity.this.unread_to_load));
                    if (ChatActivity.this.createUnreadMessageAfterId != 0) {
                        ChatActivity.this.createUnreadMessageAfterId = 0;
                    }
                }
                if (message != null && message.messageOwner != null && message.messageOwner.media_unread && message.messageOwner.mentioned) {
                    if (!(message.isVoice() || message.isRoundVideo())) {
                        ChatActivity.this.newMentionsCount = ChatActivity.this.newMentionsCount - 1;
                        if (ChatActivity.this.newMentionsCount <= 0) {
                            ChatActivity.this.newMentionsCount = 0;
                            ChatActivity.this.hasAllMentionsLocal = true;
                            ChatActivity.this.showMentiondownButton(false, true);
                        } else {
                            ChatActivity.this.mentiondownButtonCounter.setText(String.format("%d", new Object[]{Integer.valueOf(ChatActivity.this.newMentionsCount)}));
                        }
                        MessagesController.getInstance().markMentionMessageAsRead(message.getId(), ChatObject.isChannel(ChatActivity.this.currentChat) ? ChatActivity.this.currentChat.id : 0, ChatActivity.this.dialog_id);
                        message.setContentIsRead();
                    }
                    if (view instanceof ChatMessageCell) {
                        ((ChatMessageCell) view).setHighlightedAnimated();
                    }
                }
            }
        }

        public int getItemViewType(int position) {
            if (position >= this.messagesStartRow && position < this.messagesEndRow) {
                return ((MessageObject) ChatActivity.this.messages.get(position - this.messagesStartRow)).contentType;
            }
            if (position == this.botInfoRow) {
                return 3;
            }
            return 4;
        }

        public void onViewAttachedToWindow(ViewHolder holder) {
            boolean z = true;
            if (holder.itemView instanceof ChatMessageCell) {
                boolean z2;
                boolean z3;
                final ChatMessageCell messageCell = holder.itemView;
                MessageObject message = messageCell.getMessageObject();
                boolean selected = false;
                boolean disableSelection = false;
                if (ChatActivity.this.actionBar.isActionModeShowed()) {
                    MessageObject messageObject;
                    int idx;
                    if (ChatActivity.this.chatActivityEnterView != null) {
                        messageObject = ChatActivity.this.chatActivityEnterView.getEditingMessageObject();
                    } else {
                        messageObject = null;
                    }
                    if (message.getDialogId() == ChatActivity.this.dialog_id) {
                        idx = 0;
                    } else {
                        idx = 1;
                    }
                    if (messageObject == message || ChatActivity.this.selectedMessagesIds[idx].containsKey(Integer.valueOf(message.getId()))) {
                        ChatActivity.this.setCellSelectionBackground(message, messageCell, idx);
                        selected = true;
                    } else {
                        messageCell.setBackgroundDrawable(null);
                    }
                    disableSelection = true;
                } else {
                    messageCell.setBackgroundDrawable(null);
                }
                if (disableSelection) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (disableSelection && selected) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                messageCell.setCheckPressed(z2, z3);
                messageCell.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                    public boolean onPreDraw() {
                        messageCell.getViewTreeObserver().removeOnPreDrawListener(this);
                        int height = ChatActivity.this.chatListView.getMeasuredHeight();
                        int top = messageCell.getTop();
                        int bottom = messageCell.getBottom();
                        int viewTop = top >= 0 ? 0 : -top;
                        int viewBottom = messageCell.getMeasuredHeight();
                        if (viewBottom > height) {
                            viewBottom = viewTop + height;
                        }
                        messageCell.setVisiblePart(viewTop, viewBottom - viewTop);
                        return true;
                    }
                });
                if (ChatActivity.this.highlightMessageId == Integer.MAX_VALUE || messageCell.getMessageObject().getId() != ChatActivity.this.highlightMessageId) {
                    z = false;
                }
                messageCell.setHighlighted(z);
            }
        }

        public void updateRowWithMessageObject(MessageObject messageObject) {
            int index = ChatActivity.this.messages.indexOf(messageObject);
            if (index != -1) {
                notifyItemChanged(this.messagesStartRow + index);
            }
        }

        public void notifyDataSetChanged() {
            updateRows();
            try {
                super.notifyDataSetChanged();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        public void notifyItemChanged(int position) {
            try {
                super.notifyItemChanged(position);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        public void notifyItemRangeChanged(int positionStart, int itemCount) {
            try {
                super.notifyItemRangeChanged(positionStart, itemCount);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        public void notifyItemInserted(int position) {
            updateRows();
            try {
                super.notifyItemInserted(position);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        public void notifyItemMoved(int fromPosition, int toPosition) {
            updateRows();
            try {
                super.notifyItemMoved(fromPosition, toPosition);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        public void notifyItemRangeInserted(int positionStart, int itemCount) {
            updateRows();
            try {
                super.notifyItemRangeInserted(positionStart, itemCount);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        public void notifyItemRemoved(int position) {
            updateRows();
            try {
                super.notifyItemRemoved(position);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        public void notifyItemRangeRemoved(int positionStart, int itemCount) {
            updateRows();
            try {
                super.notifyItemRangeRemoved(positionStart, itemCount);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    public MessageObject getMessageObject() {
        return this.messageObject;
    }

    public void setMessageObject(MessageObject messageObject) {
        this.messageObject = messageObject;
    }

    public ChatActivity(Bundle args) {
        super(args);
    }

    public boolean onFragmentCreate() {
        Semaphore semaphore;
        int chatId = this.arguments.getInt("chat_id", 0);
        int userId = this.arguments.getInt("user_id", 0);
        int encId = this.arguments.getInt("enc_id", 0);
        this.inlineReturn = this.arguments.getLong("inline_return", 0);
        String inlineQuery = this.arguments.getString("inline_query");
        this.startLoadFromMessageId = this.arguments.getInt("message_id", 0);
        int migrated_to = this.arguments.getInt("migrated_to", 0);
        this.scrollToTopOnResume = this.arguments.getBoolean("scrollToTopOnResume", false);
        this.chat_id = chatId;
        this.isAdvancedForward = this.arguments.getBoolean("isAdvancedForward", false);
        this.isDownloadManager = this.arguments.getBoolean("isDownloadManager", false);
        this.directShareToMenu = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("directShareToMenu", false);
        final int i;
        final Semaphore semaphore2;
        if (chatId != 0) {
            this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(chatId));
            if (this.currentChat == null) {
                semaphore = new Semaphore(0);
                i = chatId;
                semaphore2 = semaphore;
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        ChatActivity.this.currentChat = MessagesStorage.getInstance().getChat(i);
                        semaphore2.release();
                    }
                });
                try {
                    semaphore.acquire();
                } catch (Exception e) {
                    FileLog.e(e);
                }
                if (this.currentChat != null) {
                    MessagesController.getInstance().putChat(this.currentChat, true);
                } else if (chatId != 111444999) {
                    return false;
                }
            }
            if (chatId > 0) {
                this.dialog_id = (long) (-chatId);
            } else {
                this.isBroadcast = true;
                this.dialog_id = AndroidUtilities.makeBroadcastId(chatId);
            }
            if (ChatObject.isChannel(this.currentChat)) {
                MessagesController.getInstance().startShortPoll(chatId, false);
            }
        } else if (userId != 0) {
            this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(userId));
            if (this.currentUser == null) {
                semaphore = new Semaphore(0);
                i = userId;
                semaphore2 = semaphore;
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        ChatActivity.this.currentUser = MessagesStorage.getInstance().getUser(i);
                        semaphore2.release();
                    }
                });
                try {
                    semaphore.acquire();
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
                if (this.currentUser == null) {
                    return false;
                }
                MessagesController.getInstance().putUser(this.currentUser, true);
            }
            this.dialog_id = (long) userId;
            this.botUser = this.arguments.getString("botUser");
            if (inlineQuery != null) {
                MessagesController.getInstance().sendBotStart(this.currentUser, inlineQuery);
            }
        } else if (encId != 0) {
            this.currentEncryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(encId));
            if (this.currentEncryptedChat == null) {
                semaphore = new Semaphore(0);
                i = encId;
                semaphore2 = semaphore;
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        ChatActivity.this.currentEncryptedChat = MessagesStorage.getInstance().getEncryptedChat(i);
                        semaphore2.release();
                    }
                });
                try {
                    semaphore.acquire();
                } catch (Exception e22) {
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
                final Semaphore semaphore3 = semaphore;
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        ChatActivity.this.currentUser = MessagesStorage.getInstance().getUser(ChatActivity.this.currentEncryptedChat.user_id);
                        semaphore3.release();
                    }
                });
                try {
                    semaphore.acquire();
                } catch (Exception e222) {
                    FileLog.e(e222);
                }
                if (this.currentUser == null) {
                    return false;
                }
                MessagesController.getInstance().putUser(this.currentUser, true);
            }
            this.dialog_id = ((long) encId) << 32;
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
            int messageId = sharedPreferences.getInt("diditem" + this.dialog_id, 0);
            if (messageId != 0) {
                this.loadingFromOldPosition = true;
                this.startLoadFromMessageOffset = sharedPreferences.getInt("diditemo" + this.dialog_id, 0);
                this.startLoadFromMessageId = messageId;
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
        int i2;
        int i3;
        boolean isChannel;
        int i4;
        if (this.startLoadFromMessageId != 0) {
            this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
            int i5;
            if (migrated_to != 0) {
                this.mergeDialogId = (long) migrated_to;
                instance = MessagesController.getInstance();
                j = this.mergeDialogId;
                i2 = this.loadingFromOldPosition ? 50 : AndroidUtilities.isTablet() ? 30 : 20;
                i5 = this.startLoadFromMessageId;
                i3 = this.classGuid;
                isChannel = ChatObject.isChannel(this.currentChat);
                i4 = this.lastLoadIndex;
                this.lastLoadIndex = i4 + 1;
                instance.loadMessages(j, i2, i5, 0, true, 0, i3, 3, 0, isChannel, i4);
            } else {
                instance = MessagesController.getInstance();
                j = this.dialog_id;
                i2 = this.loadingFromOldPosition ? 50 : AndroidUtilities.isTablet() ? 30 : 20;
                i5 = this.startLoadFromMessageId;
                i3 = this.classGuid;
                isChannel = ChatObject.isChannel(this.currentChat);
                i4 = this.lastLoadIndex;
                this.lastLoadIndex = i4 + 1;
                instance.loadMessages(j, i2, i5, 0, true, 0, i3, 3, 0, isChannel, i4);
            }
        } else {
            this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
            instance = MessagesController.getInstance();
            j = this.dialog_id;
            i2 = AndroidUtilities.isTablet() ? 30 : 20;
            i3 = this.classGuid;
            isChannel = ChatObject.isChannel(this.currentChat);
            i4 = this.lastLoadIndex;
            this.lastLoadIndex = i4 + 1;
            instance.loadMessages(j, i2, 0, 0, true, 0, i3, 2, 0, isChannel, i4);
        }
        if (this.currentChat != null) {
            Semaphore semaphore4 = null;
            if (this.isBroadcast) {
                semaphore = new Semaphore(0);
            }
            MessagesController.getInstance().loadChatInfo(this.currentChat.id, semaphore4, ChatObject.isChannel(this.currentChat));
            if (this.isBroadcast && semaphore4 != null) {
                try {
                    semaphore4.acquire();
                } catch (Exception e2222) {
                    FileLog.e(e2222);
                }
            }
        }
        if (userId != 0 && this.currentUser.bot) {
            BotQuery.loadBotInfo(userId, true, this.classGuid);
        } else if (this.info instanceof TLRPC$TL_chatFull) {
            for (int a = 0; a < this.info.participants.participants.size(); a++) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$ChatParticipant) this.info.participants.participants.get(a)).user_id));
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
                    getParentActivity().getWindow().clearFlags(8192);
                }
            } catch (Throwable e) {
                FileLog.e(e);
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

    public View createView(Context context) {
        if (!BuildConfig.FLAVOR.contentEquals("vip") && AppPreferences.checkFilterChannel(ApplicationLoader.applicationContext)) {
            if (DatabaseHandler.isFilter(Math.abs(this.dialog_id))) {
                mute();
                this.channelIsFilter = true;
                return createView2(context);
            }
            new Handler().postDelayed(new Runnable() {

                /* renamed from: org.telegram.ui.ChatActivity$10$1 */
                class C23831 implements IResponseReceiver {
                    C23831() {
                    }

                    public void onResult(Object object, int StatusCode) {
                        switch (StatusCode) {
                            case 19:
                                FilterResponse response = (FilterResponse) object;
                                if (response != null) {
                                    DialogStatus dialogStatus = new DialogStatus();
                                    dialogStatus.setFilter(response.isFilter());
                                    dialogStatus.setDialogId(response.getChannelId());
                                    ApplicationLoader.databaseHandler.createOrUpdateDialogStatus(dialogStatus);
                                    if (response.isFilter()) {
                                        ChatActivity.this.channelIsFilter = response.isFilter();
                                        ChatActivity.this.showCantOpenAlert(ChatActivity.this, AppPreferences.getFilterMessage(ApplicationLoader.applicationContext), true);
                                        ChatActivity.this.mute();
                                        return;
                                    }
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
                        HandleRequest.getNew(ApplicationLoader.applicationContext, new C23831()).singleCheckFilterStatus((long) UserConfig.getClientUserId(), ChatActivity.this.currentChat.username);
                    }
                }
            }, 1000);
        }
        if (!DatabaseHandler.isFilter(Math.abs(this.dialog_id))) {
            return createView2(context);
        }
        mute();
        this.channelIsFilter = true;
        return createView2(context);
    }

    private void mute() {
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
        editor.putInt("notify2_" + this.dialog_id, 2);
        MessagesStorage.getInstance().setDialogFlags(this.dialog_id, 1);
        editor.commit();
        TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.dialog_id));
        if (dialog != null) {
            dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
            dialog.notify_settings.mute_until = Integer.MAX_VALUE;
        }
        NotificationsController.updateServerNotificationsSettings(this.dialog_id);
        NotificationsController.getInstance().removeNotificationsForDialog(this.dialog_id);
    }

    private void showCantOpenAlert(BaseFragment fragment, String reason, final boolean callBackPress) {
        if (fragment != null) {
            try {
                if (fragment.getParentActivity() != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getParentActivity());
                    builder.setTitle("");
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (callBackPress) {
                                ChatActivity.this.parentLayout.onBackPressed();
                            }
                        }
                    });
                    builder.setOnCancelListener(new OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            if (callBackPress && ChatActivity.this.parentLayout != null) {
                                ChatActivity.this.parentLayout.onBackPressed();
                            }
                        }
                    });
                    builder.setMessage(reason);
                    builder.setCancelable(false);
                    fragment.showDialog(builder.create());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public View createView2(Context context) {
        int a;
        ActionBarMenuItem actionBarMenuItem;
        CharSequence oldMessage;
        boolean z;
        View fragmentContextView;
        boolean z2;
        MessageObject messageObject;
        if (this.chatMessageCellsCache.isEmpty()) {
            for (a = 0; a < 8; a++) {
                this.chatMessageCellsCache.add(new ChatMessageCell(context));
            }
        }
        for (a = 1; a >= 0; a--) {
            this.selectedMessagesIds[a].clear();
            this.selectedMessagesCanCopyIds[a].clear();
            this.selectedMessagesCanStarIds[a].clear();
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
            class C23922 implements OnClickListener {

                /* renamed from: org.telegram.ui.ChatActivity$13$2$1 */
                class C23911 implements RequestDelegate {

                    /* renamed from: org.telegram.ui.ChatActivity$13$2$1$1 */
                    class C23901 implements Runnable {
                        C23901() {
                        }

                        public void run() {
                            try {
                                ChatActivity.this.finishFragment();
                            } catch (Exception e) {
                                FileLog.e("tmessages", e);
                            }
                        }
                    }

                    C23911() {
                    }

                    public void run(TLObject response, TLRPC$TL_error error) {
                        if (response != null) {
                            if (error != null) {
                                MessagesController.getInstance().processUpdates((TLRPC$Updates) response, false);
                                AndroidUtilities.runOnUIThread(new C23901());
                            }
                        } else if (error != null) {
                            MessagesController.getInstance().processUpdates((TLRPC$Updates) response, false);
                            AndroidUtilities.runOnUIThread(new C23901());
                        }
                    }
                }

                C23922() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId()));
                    if (user != null && ChatActivity.this.chat_id > 0) {
                        TLObject req = new TLRPC$TL_messages_deleteChatUser();
                        req.chat_id = ChatActivity.this.chat_id;
                        req.user_id = MessagesController.getInputUser(user);
                        ConnectionsManager.getInstance().sendRequest(req, new C23911(), 64);
                    }
                }
            }

            public void onItemClick(int id) {
                int a;
                if (id == -1) {
                    if (ChatActivity.this.actionBar.isActionModeShowed()) {
                        for (a = 1; a >= 0; a--) {
                            ChatActivity.this.selectedMessagesIds[a].clear();
                            ChatActivity.this.selectedMessagesCanCopyIds[a].clear();
                            ChatActivity.this.selectedMessagesCanStarIds[a].clear();
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
                } else if (id == 10) {
                    String str = "";
                    int previousUid = 0;
                    for (a = 1; a >= 0; a--) {
                        ArrayList<Integer> arrayList = new ArrayList(ChatActivity.this.selectedMessagesCanCopyIds[a].keySet());
                        if (ChatActivity.this.currentEncryptedChat == null) {
                            Collections.sort(arrayList);
                        } else {
                            Collections.sort(arrayList, Collections.reverseOrder());
                        }
                        for (int b = 0; b < arrayList.size(); b++) {
                            messageObject = (MessageObject) ChatActivity.this.selectedMessagesCanCopyIds[a].get((Integer) arrayList.get(b));
                            if (str.length() != 0) {
                                str = str + "\n\n";
                            }
                            str = str + ChatActivity.this.getMessageContent(messageObject, previousUid, true);
                            previousUid = messageObject.messageOwner.from_id;
                        }
                    }
                    if (str.length() != 0) {
                        AndroidUtilities.addToClipboard(str);
                    }
                    for (a = 1; a >= 0; a--) {
                        ChatActivity.this.selectedMessagesIds[a].clear();
                        ChatActivity.this.selectedMessagesCanCopyIds[a].clear();
                        ChatActivity.this.selectedMessagesCanStarIds[a].clear();
                    }
                    ChatActivity.this.cantDeleteMessagesCount = 0;
                    ChatActivity.this.canEditMessagesCount = 0;
                    ChatActivity.this.actionBar.hideActionMode();
                    ChatActivity.this.updatePinnedMessageView(true);
                    ChatActivity.this.updateVisibleRows();
                } else if (id == 12) {
                    if (ChatActivity.this.getParentActivity() != null) {
                        ChatActivity.this.createDeleteMessagesAlert(null, null);
                    }
                } else if (id == 11 || id == 111) {
                    if (id == 111) {
                        ChatActivity.QuoteForward = true;
                    } else {
                        ChatActivity.QuoteForward = false;
                    }
                    args = new Bundle();
                    args.putBoolean("onlySelect", true);
                    args.putInt("dialogsType", 3);
                    BaseFragment dialogsActivity = new DialogsActivity(args);
                    dialogsActivity.setDelegate(ChatActivity.this);
                    ChatActivity.this.presentFragment(dialogsActivity);
                } else if (id == 13) {
                    if (ChatActivity.this.getParentActivity() != null) {
                        ChatActivity.this.showDialog(AlertsCreator.createTTLAlert(ChatActivity.this.getParentActivity(), ChatActivity.this.currentEncryptedChat).create());
                    }
                } else if (id == 15 || id == 16) {
                    if (ChatActivity.this.getParentActivity() != null) {
                        isChat = ((int) ChatActivity.this.dialog_id) < 0 && ((int) (ChatActivity.this.dialog_id >> 32)) != 1;
                        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        if (id == 15) {
                            builder.setMessage(LocaleController.getString("AreYouSureClearHistory", R.string.AreYouSureClearHistory));
                        } else if (isChat) {
                            builder.setMessage(LocaleController.getString("AreYouSureDeleteAndExit", R.string.AreYouSureDeleteAndExit));
                        } else {
                            builder.setMessage(LocaleController.getString("AreYouSureDeleteThisChat", R.string.AreYouSureDeleteThisChat));
                        }
                        final int i = id;
                        final boolean z = isChat;
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
                } else if (id == 28) {
                    if (ChatActivity.this.getParentActivity() != null) {
                        isChat = ((int) ChatActivity.this.dialog_id) < 0 && ((int) (ChatActivity.this.dialog_id >> 32)) != 1;
                        if (isChat) {
                            android.app.AlertDialog.Builder builder2 = new android.app.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                            builder2.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                            builder2.setMessage(LocaleController.getString("LeaveWithoutDeleteMsg", R.string.LeaveWithoutDeleteMsg));
                            builder2.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C23922());
                            builder2.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                            ChatActivity.this.showDialog(builder2.create());
                        }
                    }
                } else if (id == 17) {
                    if (ChatActivity.this.currentUser != null && ChatActivity.this.getParentActivity() != null) {
                        if (ChatActivity.this.currentUser.phone == null || ChatActivity.this.currentUser.phone.length() == 0) {
                            ChatActivity.this.shareMyContact(ChatActivity.this.replyingMessageObject);
                            return;
                        }
                        args = new Bundle();
                        args.putInt("user_id", ChatActivity.this.currentUser.id);
                        args.putBoolean("addContact", true);
                        ChatActivity.this.presentFragment(new ContactAddActivity(args));
                    }
                } else if (id == 18) {
                    ChatActivity.this.toggleMute(false);
                } else if (id == 24) {
                    try {
                        AndroidUtilities.installShortcut((long) ChatActivity.this.currentUser.id);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                } else if (id == 21) {
                    ChatActivity.this.showDialog(AlertsCreator.createReportAlert(ChatActivity.this.getParentActivity(), ChatActivity.this.dialog_id, ChatActivity.this));
                } else if (id == 19) {
                    messageObject = null;
                    a = 1;
                    while (a >= 0) {
                        if (messageObject == null && ChatActivity.this.selectedMessagesIds[a].size() == 1) {
                            messageObject = (MessageObject) ChatActivity.this.messagesDict[a].get(new ArrayList(ChatActivity.this.selectedMessagesIds[a].keySet()).get(0));
                        }
                        ChatActivity.this.selectedMessagesIds[a].clear();
                        ChatActivity.this.selectedMessagesCanCopyIds[a].clear();
                        ChatActivity.this.selectedMessagesCanStarIds[a].clear();
                        a--;
                    }
                    if (messageObject != null && (messageObject.messageOwner.id > 0 || (messageObject.messageOwner.id < 0 && ChatActivity.this.currentEncryptedChat != null))) {
                        ChatActivity.this.showReplyPanel(true, messageObject, null, null, false);
                    }
                    ChatActivity.this.cantDeleteMessagesCount = 0;
                    ChatActivity.this.canEditMessagesCount = 0;
                    ChatActivity.this.actionBar.hideActionMode();
                    ChatActivity.this.updatePinnedMessageView(true);
                    ChatActivity.this.updateVisibleRows();
                } else if (id == 22) {
                    for (a = 0; a < 2; a++) {
                        for (Entry<Integer, MessageObject> entry : ChatActivity.this.selectedMessagesCanStarIds[a].entrySet()) {
                            StickersQuery.addRecentSticker(2, ((MessageObject) entry.getValue()).getDocument(), (int) (System.currentTimeMillis() / 1000), !ChatActivity.this.hasUnfavedSelected);
                        }
                    }
                    for (a = 1; a >= 0; a--) {
                        ChatActivity.this.selectedMessagesIds[a].clear();
                        ChatActivity.this.selectedMessagesCanCopyIds[a].clear();
                        ChatActivity.this.selectedMessagesCanStarIds[a].clear();
                    }
                    ChatActivity.this.cantDeleteMessagesCount = 0;
                    ChatActivity.this.canEditMessagesCount = 0;
                    ChatActivity.this.actionBar.hideActionMode();
                    ChatActivity.this.updatePinnedMessageView(true);
                    ChatActivity.this.updateVisibleRows();
                } else if (id == 23) {
                    messageObject = null;
                    a = 1;
                    while (a >= 0) {
                        if (messageObject == null && ChatActivity.this.selectedMessagesIds[a].size() == 1) {
                            messageObject = (MessageObject) ChatActivity.this.messagesDict[a].get(new ArrayList(ChatActivity.this.selectedMessagesIds[a].keySet()).get(0));
                        }
                        ChatActivity.this.selectedMessagesIds[a].clear();
                        ChatActivity.this.selectedMessagesCanCopyIds[a].clear();
                        ChatActivity.this.selectedMessagesCanStarIds[a].clear();
                        a--;
                    }
                    ChatActivity.this.startReplyOnTextChange = false;
                    ChatActivity.this.startEditingMessageObject(messageObject);
                    ChatActivity.this.cantDeleteMessagesCount = 0;
                    ChatActivity.this.canEditMessagesCount = 0;
                    ChatActivity.this.updatePinnedMessageView(true);
                    ChatActivity.this.updateVisibleRows();
                } else if (id == 14) {
                    ChatActivity.this.openAttachMenu();
                } else if (id == 30) {
                    SendMessagesHelper.getInstance().sendMessage("/help", ChatActivity.this.dialog_id, null, null, false, null, null, null);
                } else if (id == 31) {
                    SendMessagesHelper.getInstance().sendMessage("/settings", ChatActivity.this.dialog_id, null, null, false, null, null, null);
                } else if (id == 40) {
                    ChatActivity.this.openSearchWithText(null);
                } else if (id == 32) {
                    if (ChatActivity.this.currentUser != null && ChatActivity.this.getParentActivity() != null) {
                        VoIPHelper.startCall(ChatActivity.this.currentUser, ChatActivity.this.getParentActivity(), MessagesController.getInstance().getUserFull(ChatActivity.this.currentUser.id));
                    }
                } else if (id == 29) {
                    ChatActivity.this.openAddMember();
                } else if (id == 99) {
                    ChatActivity.this.presentFragment(new WallpapersActivity());
                } else if (id == 200) {
                    ChatActivity.this.goToFirstMsg = true;
                    ChatActivity.this.scrollToMessageId(1, 0, true, 0, false);
                } else if (id == 300) {
                    TLRPC$TL_dialog dialg = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(ChatActivity.this.dialog_id));
                    if (dialg == null) {
                        return;
                    }
                    if (Favourite.isFavourite(Long.valueOf(dialg.id))) {
                        Favourite.deleteFavourite(Long.valueOf(ChatActivity.this.dialog_id));
                        MessagesController.getInstance().dialogsFavs.remove(dialg);
                        ChatActivity.this.headerItem.getItemById(300).setText(LocaleController.getString("addDialogsToFave", R.string.addDialogsToFave));
                        return;
                    }
                    Favourite.addFavourite(Long.valueOf(ChatActivity.this.dialog_id));
                    MessagesController.getInstance().dialogsFavs.add(dialg);
                    ChatActivity.this.headerItem.getItemById(300).setText(LocaleController.getString("deleteDialogsFromFave", R.string.deleteDialogsFromFave));
                } else if (id == 400) {
                    ChatActivity.this.presentFragment(new ScheduleDownloadActivity());
                }
            }
        });
        this.avatarContainer = new ChatAvatarContainer(context, this, this.currentEncryptedChat != null);
        this.actionBar.addView(this.avatarContainer, 0, LayoutHelper.createFrame(-2, -1.0f, 51, 56.0f, 0.0f, 40.0f, 0.0f));
        if (!(this.currentChat == null || ChatObject.isChannel(this.currentChat))) {
            int count = this.currentChat.participants_count;
            if (this.info != null) {
                count = this.info.participants.participants.size();
            }
            if (count == 0 || this.currentChat.deactivated || this.currentChat.left || (this.currentChat instanceof TLRPC$TL_chatForbidden) || (this.info != null && (this.info.participants instanceof TLRPC$TL_chatParticipantsForbidden))) {
                this.avatarContainer.setEnabled(false);
            }
        }
        ActionBarMenu menu = this.actionBar.createMenu();
        if (this.isDownloadManager) {
            this.headerItem = menu.addItem(0, (int) R.drawable.ic_ab_other);
            this.avatarContainer.setEnabled(false);
            this.headerItem.addSubItem(500, LocaleController.getString("startAllDownloads", R.string.startAllDownloads));
            this.headerItem.addSubItem(scheduleDownloads, LocaleController.getString("scheduleDownloads", R.string.scheduleDownloads));
        } else {
            String string;
            if (this.currentEncryptedChat == null && !this.isBroadcast) {
                this.searchItem = menu.addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new ActionBarMenuItemSearchListener() {
                    boolean searchWas;

                    /* renamed from: org.telegram.ui.ChatActivity$14$1 */
                    class C23951 implements Runnable {
                        C23951() {
                        }

                        public void run() {
                            AnonymousClass14.this.searchWas = false;
                            ChatActivity.this.searchItem.getSearchField().requestFocus();
                            AndroidUtilities.showKeyboard(ChatActivity.this.searchItem.getSearchField());
                        }
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
                            AndroidUtilities.runOnUIThread(new C23951(), 300);
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
                });
                this.searchItem.getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
                this.searchItem.setVisibility(8);
            }
            this.headerItem = menu.addItem(0, (int) R.drawable.ic_ab_other);
            this.headerItem.addSubItem(200, LocaleController.getString("goToFirst", R.string.goToFirst));
            actionBarMenuItem = this.headerItem;
            if (Favourite.isFavourite(Long.valueOf(this.dialog_id))) {
                string = LocaleController.getString("deleteDialogsFromFave", R.string.deleteDialogsFromFave);
            } else {
                string = LocaleController.getString("addDialogsToFave", R.string.addDialogsToFave);
            }
            actionBarMenuItem.addSubItem(300, string);
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
                this.addContactItem = this.headerItem.addSubItem(17, "");
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
        this.attachItem = menu.addItem(14, (int) R.drawable.ic_ab_other).setOverrideMenuClick(true).setAllowCloseAnimation(false);
        this.attachItem.setVisibility(8);
        this.actionModeViews.clear();
        ActionBarMenu actionMode = this.actionBar.createActionMode();
        this.selectedMessagesCountTextView = new NumberTextView(actionMode.getContext());
        this.selectedMessagesCountTextView.setTextSize(18);
        this.selectedMessagesCountTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.selectedMessagesCountTextView.setTextColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon));
        actionMode.addView(this.selectedMessagesCountTextView, LayoutHelper.createLinear(0, -1, 1.0f, 65, 0, 0, 0));
        this.selectedMessagesCountTextView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.actionModeTitleContainer = new FrameLayout(context) {
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));
                SimpleTextView access$5200 = ChatActivity.this.actionModeTextView;
                int i = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 20 : 18;
                access$5200.setTextSize(i);
                ChatActivity.this.actionModeTextView.measure(MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), Integer.MIN_VALUE));
                if (ChatActivity.this.actionModeSubTextView.getVisibility() != 8) {
                    access$5200 = ChatActivity.this.actionModeSubTextView;
                    i = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 16 : 14;
                    access$5200.setTextSize(i);
                    ChatActivity.this.actionModeSubTextView.measure(MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), Integer.MIN_VALUE));
                }
            }

            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                int textTop;
                int height = bottom - top;
                if (ChatActivity.this.actionModeSubTextView.getVisibility() != 8) {
                    int textHeight = ((height / 2) - ChatActivity.this.actionModeTextView.getTextHeight()) / 2;
                    float f = (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 3.0f : 2.0f;
                    textTop = textHeight + AndroidUtilities.dp(f);
                } else {
                    textTop = (height - ChatActivity.this.actionModeTextView.getTextHeight()) / 2;
                }
                ChatActivity.this.actionModeTextView.layout(0, textTop, ChatActivity.this.actionModeTextView.getMeasuredWidth(), ChatActivity.this.actionModeTextView.getTextHeight() + textTop);
                if (ChatActivity.this.actionModeSubTextView.getVisibility() != 8) {
                    int textHeight2 = (height / 2) + (((height / 2) - ChatActivity.this.actionModeSubTextView.getTextHeight()) / 2);
                    if (AndroidUtilities.isTablet() || getResources().getConfiguration().orientation == 2) {
                        textTop = textHeight2 - AndroidUtilities.dp(1.0f);
                        ChatActivity.this.actionModeSubTextView.layout(0, textTop, ChatActivity.this.actionModeSubTextView.getMeasuredWidth(), ChatActivity.this.actionModeSubTextView.getTextHeight() + textTop);
                    } else {
                        textTop = textHeight2 - AndroidUtilities.dp(1.0f);
                        ChatActivity.this.actionModeSubTextView.layout(0, textTop, ChatActivity.this.actionModeSubTextView.getMeasuredWidth(), ChatActivity.this.actionModeSubTextView.getTextHeight() + textTop);
                    }
                }
            }
        };
        actionMode.addView(this.actionModeTitleContainer, LayoutHelper.createLinear(0, -1, 1.0f, 65, 0, 0, 0));
        this.actionModeTitleContainer.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
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
            this.actionModeViews.add(actionMode.addItemWithWidth(23, R.drawable.group_edit, AndroidUtilities.dp(54.0f)));
            if (!this.isBroadcast) {
                this.actionModeViews.add(actionMode.addItemWithWidth(19, R.drawable.ic_ab_reply, AndroidUtilities.dp(54.0f)));
            }
            this.actionModeViews.add(actionMode.addItemWithWidth(22, R.drawable.ic_ab_fave, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(actionMode.addItemWithWidth(10, R.drawable.ic_ab_copy, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(actionMode.addItem(111, R.drawable.ic_ab_fwd_quoteforward, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(actionMode.addItemWithWidth(11, R.drawable.ic_ab_forward, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(actionMode.addItemWithWidth(12, R.drawable.ic_ab_delete, AndroidUtilities.dp(54.0f)));
        } else {
            this.actionModeViews.add(actionMode.addItemWithWidth(23, R.drawable.group_edit, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(actionMode.addItemWithWidth(19, R.drawable.ic_ab_reply, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(actionMode.addItemWithWidth(22, R.drawable.ic_ab_fave, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(actionMode.addItemWithWidth(10, R.drawable.ic_ab_copy, AndroidUtilities.dp(54.0f)));
            this.actionModeViews.add(actionMode.addItemWithWidth(12, R.drawable.ic_ab_delete, AndroidUtilities.dp(54.0f)));
        }
        actionBarMenuItem = actionMode.getItem(23);
        int i = (this.canEditMessagesCount == 1 && this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size() == 1) ? 0 : 8;
        actionBarMenuItem.setVisibility(i);
        actionMode.getItem(10).setVisibility(this.selectedMessagesCanCopyIds[0].size() + this.selectedMessagesCanCopyIds[1].size() != 0 ? 0 : 8);
        actionMode.getItem(22).setVisibility(this.selectedMessagesCanStarIds[0].size() + this.selectedMessagesCanStarIds[1].size() != 0 ? 0 : 8);
        actionMode.getItem(12).setVisibility(this.cantDeleteMessagesCount == 0 ? 0 : 8);
        checkActionBarMenu();
        this.fragmentView = new SizeNotifierFrameLayout(context) {
            int inputFieldHeight = 0;

            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
                if (messageObject != null && messageObject.isRoundVideo() && messageObject.eventId == 0 && messageObject.getDialogId() == ChatActivity.this.dialog_id) {
                    MediaController.getInstance().setTextureView(ChatActivity.this.createTextureView(false), ChatActivity.this.aspectRatioFrameLayout, ChatActivity.this.roundVideoContainer, true);
                }
            }

            protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
                boolean result;
                MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
                boolean isRoundVideo = messageObject != null && messageObject.eventId == 0 && messageObject.isRoundVideo();
                if (!isRoundVideo || child != ChatActivity.this.roundVideoContainer) {
                    result = super.drawChild(canvas, child, drawingTime);
                    if (isRoundVideo && child == ChatActivity.this.chatListView && messageObject.type != 5 && ChatActivity.this.roundVideoContainer != null) {
                        super.drawChild(canvas, ChatActivity.this.roundVideoContainer, drawingTime);
                    }
                } else if (messageObject.type == 5) {
                    if (Theme.chat_roundVideoShadow != null && ChatActivity.this.aspectRatioFrameLayout.isDrawingReady()) {
                        int x = ((int) child.getX()) - AndroidUtilities.dp(3.0f);
                        int y = ((int) child.getY()) - AndroidUtilities.dp(2.0f);
                        Theme.chat_roundVideoShadow.setAlpha(255);
                        Theme.chat_roundVideoShadow.setBounds(x, y, (AndroidUtilities.roundMessageSize + x) + AndroidUtilities.dp(6.0f), (AndroidUtilities.roundMessageSize + y) + AndroidUtilities.dp(6.0f));
                        Theme.chat_roundVideoShadow.draw(canvas);
                    }
                    result = super.drawChild(canvas, child, drawingTime);
                } else {
                    result = false;
                }
                if (child == ChatActivity.this.actionBar && ChatActivity.this.parentLayout != null) {
                    ChatActivity.this.parentLayout.drawHeaderShadow(canvas, ChatActivity.this.actionBar.getVisibility() == 0 ? ChatActivity.this.actionBar.getMeasuredHeight() : 0);
                }
                return result;
            }

            protected boolean isActionBarVisible() {
                return ChatActivity.this.actionBar.getVisibility() == 0;
            }

            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int widthSize = MeasureSpec.getSize(widthMeasureSpec);
                int allHeight = MeasureSpec.getSize(heightMeasureSpec);
                int heightSize = allHeight;
                setMeasuredDimension(widthSize, heightSize);
                heightSize -= getPaddingTop();
                measureChildWithMargins(ChatActivity.this.actionBar, widthMeasureSpec, 0, heightMeasureSpec, 0);
                int actionBarHeight = ChatActivity.this.actionBar.getMeasuredHeight();
                if (ChatActivity.this.actionBar.getVisibility() == 0) {
                    heightSize -= actionBarHeight;
                }
                if (getKeyboardHeight() <= AndroidUtilities.dp(20.0f) && !AndroidUtilities.isInMultiwindow) {
                    heightSize -= ChatActivity.this.chatActivityEnterView.getEmojiPadding();
                    allHeight -= ChatActivity.this.chatActivityEnterView.getEmojiPadding();
                }
                int childCount = getChildCount();
                measureChildWithMargins(ChatActivity.this.chatActivityEnterView, widthMeasureSpec, 0, heightMeasureSpec, 0);
                this.inputFieldHeight = ChatActivity.this.chatActivityEnterView.getMeasuredHeight();
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    if (!(child == null || child.getVisibility() == 8 || child == ChatActivity.this.chatActivityEnterView || child == ChatActivity.this.actionBar)) {
                        if (child == ChatActivity.this.chatListView || child == ChatActivity.this.progressView) {
                            child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(Math.max(AndroidUtilities.dp(10.0f), AndroidUtilities.dp((float) ((ChatActivity.this.chatActivityEnterView.isTopViewVisible() ? 48 : 0) + 2)) + (heightSize - this.inputFieldHeight)), 1073741824));
                        } else if (child == ChatActivity.this.instantCameraView || child == ChatActivity.this.overlayView) {
                            child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec((allHeight - this.inputFieldHeight) + AndroidUtilities.dp(3.0f), 1073741824));
                        } else if (child == ChatActivity.this.emptyViewContainer) {
                            child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(heightSize, 1073741824));
                        } else if (ChatActivity.this.chatActivityEnterView.isPopupView(child)) {
                            if (!AndroidUtilities.isInMultiwindow) {
                                child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(child.getLayoutParams().height, 1073741824));
                            } else if (AndroidUtilities.isTablet()) {
                                child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.dp(320.0f), (((heightSize - this.inputFieldHeight) + actionBarHeight) - AndroidUtilities.statusBarHeight) + getPaddingTop()), 1073741824));
                            } else {
                                child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec((((heightSize - this.inputFieldHeight) + actionBarHeight) - AndroidUtilities.statusBarHeight) + getPaddingTop(), 1073741824));
                            }
                        } else if (child == ChatActivity.this.mentionContainer) {
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ChatActivity.this.mentionContainer.getLayoutParams();
                            if (ChatActivity.this.mentionsAdapter.isBannedInline()) {
                                child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(heightSize, Integer.MIN_VALUE));
                            } else {
                                int height;
                                ChatActivity.this.mentionListViewIgnoreLayout = true;
                                int maxHeight;
                                int padding;
                                if (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.isMediaLayout()) {
                                    maxHeight = ChatActivity.this.mentionGridLayoutManager.getRowsCount(widthSize) * 102;
                                    if (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.getBotContextSwitch() != null) {
                                        maxHeight += 34;
                                    }
                                    height = (heightSize - ChatActivity.this.chatActivityEnterView.getMeasuredHeight()) + (maxHeight != 0 ? AndroidUtilities.dp(2.0f) : 0);
                                    padding = Math.max(0, height - AndroidUtilities.dp(Math.min((float) maxHeight, 122.399994f)));
                                    if (ChatActivity.this.mentionLayoutManager.getReverseLayout()) {
                                        ChatActivity.this.mentionListView.setPadding(0, 0, 0, padding);
                                    } else {
                                        ChatActivity.this.mentionListView.setPadding(0, padding, 0, 0);
                                    }
                                } else {
                                    int size = ChatActivity.this.mentionsAdapter.getItemCount();
                                    maxHeight = 0;
                                    if (ChatActivity.this.mentionsAdapter.isBotContext()) {
                                        if (ChatActivity.this.mentionsAdapter.getBotContextSwitch() != null) {
                                            maxHeight = 0 + 36;
                                            size--;
                                        }
                                        maxHeight += size * 68;
                                    } else {
                                        maxHeight = 0 + (size * 36);
                                    }
                                    height = (heightSize - ChatActivity.this.chatActivityEnterView.getMeasuredHeight()) + (maxHeight != 0 ? AndroidUtilities.dp(2.0f) : 0);
                                    padding = Math.max(0, height - AndroidUtilities.dp(Math.min((float) maxHeight, 122.399994f)));
                                    if (ChatActivity.this.mentionLayoutManager.getReverseLayout()) {
                                        ChatActivity.this.mentionListView.setPadding(0, 0, 0, padding);
                                    } else {
                                        ChatActivity.this.mentionListView.setPadding(0, padding, 0, 0);
                                    }
                                }
                                layoutParams.height = height;
                                layoutParams.topMargin = 0;
                                ChatActivity.this.mentionListViewIgnoreLayout = false;
                                child.measure(MeasureSpec.makeMeasureSpec(widthSize, 1073741824), MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824));
                            }
                        } else {
                            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                        }
                    }
                }
            }

            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                int count = getChildCount();
                int paddingBottom = (getKeyboardHeight() > AndroidUtilities.dp(20.0f) || AndroidUtilities.isInMultiwindow) ? 0 : ChatActivity.this.chatActivityEnterView.getEmojiPadding();
                setBottomClip(paddingBottom);
                for (int i = 0; i < count; i++) {
                    View child = getChildAt(i);
                    if (child.getVisibility() != 8) {
                        int childLeft;
                        int childTop;
                        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();
                        int width = child.getMeasuredWidth();
                        int height = child.getMeasuredHeight();
                        int gravity = lp.gravity;
                        if (gravity == -1) {
                            gravity = 51;
                        }
                        int verticalGravity = gravity & 112;
                        switch ((gravity & 7) & 7) {
                            case 1:
                                childLeft = ((((r - l) - width) / 2) + lp.leftMargin) - lp.rightMargin;
                                break;
                            case 5:
                                childLeft = (r - width) - lp.rightMargin;
                                break;
                            default:
                                childLeft = lp.leftMargin;
                                break;
                        }
                        switch (verticalGravity) {
                            case 16:
                                childTop = (((((b - paddingBottom) - t) - height) / 2) + lp.topMargin) - lp.bottomMargin;
                                break;
                            case 48:
                                childTop = lp.topMargin + getPaddingTop();
                                if (child != ChatActivity.this.actionBar && ChatActivity.this.actionBar.getVisibility() == 0) {
                                    childTop += ChatActivity.this.actionBar.getMeasuredHeight();
                                    break;
                                }
                            case 80:
                                childTop = (((b - paddingBottom) - t) - height) - lp.bottomMargin;
                                break;
                            default:
                                childTop = lp.topMargin;
                                break;
                        }
                        if (child == ChatActivity.this.mentionContainer) {
                            childTop -= ChatActivity.this.chatActivityEnterView.getMeasuredHeight() - AndroidUtilities.dp(2.0f);
                        } else if (child == ChatActivity.this.pagedownButton) {
                            childTop -= ChatActivity.this.chatActivityEnterView.getMeasuredHeight();
                        } else if (child == ChatActivity.this.mentiondownButton) {
                            childTop -= ChatActivity.this.chatActivityEnterView.getMeasuredHeight();
                        } else if (child == ChatActivity.this.emptyViewContainer) {
                            childTop -= (this.inputFieldHeight / 2) - (ChatActivity.this.actionBar.getVisibility() == 0 ? ChatActivity.this.actionBar.getMeasuredHeight() / 2 : 0);
                        } else if (ChatActivity.this.chatActivityEnterView.isPopupView(child)) {
                            if (AndroidUtilities.isInMultiwindow) {
                                childTop = (ChatActivity.this.chatActivityEnterView.getTop() - child.getMeasuredHeight()) + AndroidUtilities.dp(1.0f);
                            } else {
                                childTop = ChatActivity.this.chatActivityEnterView.getBottom();
                            }
                        } else if (child == ChatActivity.this.gifHintTextView || child == ChatActivity.this.voiceHintTextView || child == ChatActivity.this.mediaBanTooltip) {
                            childTop -= this.inputFieldHeight;
                        } else if (child == ChatActivity.this.chatListView || child == ChatActivity.this.progressView) {
                            if (ChatActivity.this.chatActivityEnterView.isTopViewVisible()) {
                                childTop -= AndroidUtilities.dp(48.0f);
                            }
                        } else if (child == ChatActivity.this.actionBar) {
                            childTop -= getPaddingTop();
                        } else if (child == ChatActivity.this.roundVideoContainer) {
                            childTop = ChatActivity.this.actionBar.getMeasuredHeight();
                        } else if (child == ChatActivity.this.instantCameraView || child == ChatActivity.this.overlayView) {
                            childTop = 0;
                        }
                        child.layout(childLeft, childTop, childLeft + width, childTop + height);
                    }
                }
                ChatActivity.this.updateMessagesVisisblePart();
                notifyHeightChanged();
            }
        };
        this.contentView = (SizeNotifierFrameLayout) this.fragmentView;
        this.contentView.setBackgroundImage(Theme.getCachedWallpaper());
        this.emptyViewContainer = new FrameLayout(context);
        this.emptyViewContainer.setVisibility(4);
        this.contentView.addView(this.emptyViewContainer, LayoutHelper.createFrame(-1, -2, 17));
        this.emptyViewContainer.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
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
            if (this.chatActivityEnterView.isEditingMessage()) {
                oldMessage = null;
            } else {
                oldMessage = this.chatActivityEnterView.getFieldText();
            }
        } else {
            oldMessage = null;
        }
        if (this.mentionsAdapter != null) {
            this.mentionsAdapter.onDestroy();
        }
        this.chatListView = new RecyclerListView(context) {
            ArrayList<ChatMessageCell> drawCaptionAfter = new ArrayList();
            ArrayList<ChatMessageCell> drawNamesAfter = new ArrayList();
            ArrayList<ChatMessageCell> drawTimeAfter = new ArrayList();

            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                super.onLayout(changed, l, t, r, b);
                ChatActivity.this.forceScrollToTop = false;
                if (ChatActivity.this.chatAdapter.isBot) {
                    int childCount = getChildCount();
                    for (int a = 0; a < childCount; a++) {
                        View child = getChildAt(a);
                        if (child instanceof BotHelpCell) {
                            int top = ((b - t) / 2) - (child.getMeasuredHeight() / 2);
                            if (child.getTop() > top) {
                                child.layout(0, top, r - l, child.getMeasuredHeight() + top);
                                return;
                            }
                            return;
                        }
                    }
                }
            }

            protected void onChildPressed(View child, boolean pressed) {
                super.onChildPressed(child, pressed);
                if (child instanceof ChatMessageCell) {
                    GroupedMessages groupedMessages = ((ChatMessageCell) child).getCurrentMessagesGroup();
                    if (groupedMessages != null) {
                        int count = getChildCount();
                        for (int a = 0; a < count; a++) {
                            View item = getChildAt(a);
                            if (item != child && (item instanceof ChatMessageCell)) {
                                ChatMessageCell cell = (ChatMessageCell) item;
                                if (((ChatMessageCell) item).getCurrentMessagesGroup() == groupedMessages) {
                                    cell.setPressed(pressed);
                                }
                            }
                        }
                    }
                }
            }

            public void requestLayout() {
                if (!ChatActivity.this.chatListViewIgnoreLayout) {
                    super.requestLayout();
                }
            }

            public boolean drawChild(Canvas canvas, View child, long drawingTime) {
                ChatMessageCell cell;
                GroupedMessagePosition position;
                int a;
                int size;
                int clipLeft = 0;
                int clipBottom = 0;
                if (child instanceof ChatMessageCell) {
                    cell = (ChatMessageCell) child;
                    position = cell.getCurrentPosition();
                    GroupedMessages group = cell.getCurrentMessagesGroup();
                    if (position != null) {
                        if (position.pw != position.spanSize && position.spanSize == 1000 && position.siblingHeights == null && group.hasSibling) {
                            clipLeft = ((ChatMessageCell) child).getBackgroundDrawableLeft();
                        } else if (position.siblingHeights != null) {
                            clipBottom = child.getBottom() - AndroidUtilities.dp((float) ((cell.isPinnedBottom() ? 1 : 0) + 1));
                        }
                    }
                }
                if (clipLeft != 0) {
                    canvas.save();
                    canvas.clipRect(clipLeft, child.getTop(), child.getRight(), child.getBottom());
                } else if (clipBottom != 0) {
                    canvas.save();
                    canvas.clipRect(child.getLeft(), child.getTop(), child.getRight(), clipBottom);
                }
                boolean result = super.drawChild(canvas, child, drawingTime);
                if (!(clipLeft == 0 && clipBottom == 0)) {
                    canvas.restore();
                }
                int num = 0;
                int count = getChildCount();
                for (a = 0; a < count; a++) {
                    if (getChildAt(a) == child) {
                        num = a;
                        break;
                    }
                }
                if (num == count - 1) {
                    size = this.drawTimeAfter.size();
                    if (size > 0) {
                        for (a = 0; a < size; a++) {
                            cell = (ChatMessageCell) this.drawTimeAfter.get(a);
                            canvas.save();
                            canvas.translate((float) cell.getLeft(), (float) cell.getTop());
                            cell.drawTimeLayout(canvas);
                            canvas.restore();
                        }
                        this.drawTimeAfter.clear();
                    }
                    size = this.drawNamesAfter.size();
                    if (size > 0) {
                        for (a = 0; a < size; a++) {
                            cell = (ChatMessageCell) this.drawNamesAfter.get(a);
                            canvas.save();
                            canvas.translate((float) cell.getLeft(), (float) cell.getTop());
                            cell.drawNamesLayout(canvas);
                            canvas.restore();
                        }
                        this.drawNamesAfter.clear();
                    }
                    size = this.drawCaptionAfter.size();
                    if (size > 0) {
                        for (a = 0; a < size; a++) {
                            cell = (ChatMessageCell) this.drawCaptionAfter.get(a);
                            canvas.save();
                            canvas.translate((float) cell.getLeft(), (float) cell.getTop());
                            cell.drawCaptionLayout(canvas);
                            canvas.restore();
                        }
                        this.drawCaptionAfter.clear();
                    }
                }
                if (child instanceof ChatMessageCell) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) child;
                    position = chatMessageCell.getCurrentPosition();
                    if (position != null) {
                        if (position.last || (position.minX == (byte) 0 && position.minY == (byte) 0)) {
                            if (num == count - 1) {
                                canvas.save();
                                canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                                if (position.last) {
                                    chatMessageCell.drawTimeLayout(canvas);
                                }
                                if (position.minX == (byte) 0 && position.minY == (byte) 0) {
                                    chatMessageCell.drawNamesLayout(canvas);
                                }
                                canvas.restore();
                            } else {
                                if (position.last) {
                                    this.drawTimeAfter.add(chatMessageCell);
                                }
                                if (position.minX == (byte) 0 && position.minY == (byte) 0 && chatMessageCell.hasNameLayout()) {
                                    this.drawNamesAfter.add(chatMessageCell);
                                }
                            }
                        }
                        if (num == count - 1) {
                            canvas.save();
                            canvas.translate((float) chatMessageCell.getLeft(), (float) chatMessageCell.getTop());
                            if (!(!chatMessageCell.hasCaptionLayout() || (position.flags & 8) == 0 || (position.flags & 1) == 0)) {
                                chatMessageCell.drawCaptionLayout(canvas);
                            }
                            canvas.restore();
                        } else if (!(!chatMessageCell.hasCaptionLayout() || (position.flags & 8) == 0 || (position.flags & 1) == 0)) {
                            this.drawCaptionAfter.add(chatMessageCell);
                        }
                    }
                    ImageReceiver imageReceiver = chatMessageCell.getAvatarImage();
                    if (imageReceiver != null) {
                        ViewHolder holder;
                        int p;
                        int idx;
                        GroupedMessages groupedMessages = ChatActivity.this.getValidGroupedMessage(chatMessageCell.getMessageObject());
                        int top = child.getTop();
                        if (chatMessageCell.isPinnedBottom()) {
                            holder = ChatActivity.this.chatListView.getChildViewHolder(child);
                            if (holder != null) {
                                int nextPosition;
                                p = holder.getAdapterPosition();
                                if (groupedMessages == null || position == null) {
                                    nextPosition = p - 1;
                                } else {
                                    idx = groupedMessages.posArray.indexOf(position);
                                    size = groupedMessages.posArray.size();
                                    if ((position.flags & 8) != 0) {
                                        nextPosition = (p - size) + idx;
                                    } else {
                                        nextPosition = p - 1;
                                        a = idx + 1;
                                        while (idx < size && ((GroupedMessagePosition) groupedMessages.posArray.get(a)).minY <= position.maxY) {
                                            nextPosition--;
                                            a++;
                                        }
                                    }
                                }
                                if (ChatActivity.this.chatListView.findViewHolderForAdapterPosition(nextPosition) != null) {
                                    imageReceiver.setImageY(-AndroidUtilities.dp(1000.0f));
                                    imageReceiver.draw(canvas);
                                }
                            }
                        }
                        if (chatMessageCell.isPinnedTop()) {
                            holder = ChatActivity.this.chatListView.getChildViewHolder(child);
                            if (holder != null) {
                                do {
                                    int prevPosition;
                                    p = holder.getAdapterPosition();
                                    if (groupedMessages == null || position == null) {
                                        prevPosition = p + 1;
                                    } else {
                                        idx = groupedMessages.posArray.indexOf(position);
                                        size = groupedMessages.posArray.size();
                                        if ((position.flags & 4) != 0) {
                                            prevPosition = (p + idx) + 1;
                                        } else {
                                            prevPosition = p + 1;
                                            a = idx - 1;
                                            while (idx >= 0 && ((GroupedMessagePosition) groupedMessages.posArray.get(a)).maxY >= position.minY) {
                                                prevPosition++;
                                                a--;
                                            }
                                        }
                                    }
                                    holder = ChatActivity.this.chatListView.findViewHolderForAdapterPosition(prevPosition);
                                    if (holder == null) {
                                        break;
                                    }
                                    top = holder.itemView.getTop();
                                    if (!(holder.itemView instanceof ChatMessageCell)) {
                                        break;
                                    }
                                } while (((ChatMessageCell) holder.itemView).isPinnedTop());
                            }
                        }
                        int y = child.getTop() + chatMessageCell.getLayoutHeight();
                        int maxY = ChatActivity.this.chatListView.getMeasuredHeight() - ChatActivity.this.chatListView.getPaddingBottom();
                        if (y > maxY) {
                            y = maxY;
                        }
                        if (y - AndroidUtilities.dp(48.0f) < top) {
                            y = top + AndroidUtilities.dp(48.0f);
                        }
                        imageReceiver.setImageY(y - AndroidUtilities.dp(44.0f));
                        imageReceiver.draw(canvas);
                    }
                }
                return result;
            }
        };
        Callback anonymousClass21 = new SimpleCallback(0, 4) {
            public int getSwipeDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
                if (viewHolder.itemView instanceof ChatMessageCell) {
                    return super.getSwipeDirs(recyclerView, viewHolder);
                }
                return 0;
            }

            public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
                return false;
            }

            public void onSwiped(ViewHolder viewHolder, int swipeDir) {
                viewHolder.itemView.setX(0.0f);
                viewHolder.itemView.setY(0.0f);
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
        this.chatLayoutManager = new GridLayoutManagerFixed(context, 1000, 1, true) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }

            public void smoothScrollToPosition(RecyclerView recyclerView, State state, int position) {
                LinearSmoothScrollerMiddle linearSmoothScroller = new LinearSmoothScrollerMiddle(recyclerView.getContext());
                linearSmoothScroller.setTargetPosition(position);
                startSmoothScroll(linearSmoothScroller);
            }

            public boolean shouldLayoutChildFromOpositeSide(View child) {
                if (!(child instanceof ChatMessageCell) || ((ChatMessageCell) child).getMessageObject().isOutOwner()) {
                    return false;
                }
                return true;
            }

            protected boolean hasSiblingChild(int position) {
                if (position < ChatActivity.this.chatAdapter.messagesStartRow || position >= ChatActivity.this.chatAdapter.messagesEndRow) {
                    return false;
                }
                int index = position - ChatActivity.this.chatAdapter.messagesStartRow;
                if (index < 0 || index >= ChatActivity.this.messages.size()) {
                    return false;
                }
                MessageObject message = (MessageObject) ChatActivity.this.messages.get(index);
                GroupedMessages group = ChatActivity.this.getValidGroupedMessage(message);
                if (group == null) {
                    return false;
                }
                GroupedMessagePosition pos = (GroupedMessagePosition) group.positions.get(message);
                if (pos.minX == pos.maxX || pos.minY != pos.maxY || pos.minY == (byte) 0) {
                    return false;
                }
                int count = group.posArray.size();
                for (int a = 0; a < count; a++) {
                    GroupedMessagePosition p = (GroupedMessagePosition) group.posArray.get(a);
                    if (p != pos && p.minY <= pos.minY && p.maxY >= pos.minY) {
                        return true;
                    }
                }
                return false;
            }
        };
        this.chatLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            public int getSpanSize(int position) {
                if (position >= ChatActivity.this.chatAdapter.messagesStartRow && position < ChatActivity.this.chatAdapter.messagesEndRow) {
                    int idx = position - ChatActivity.this.chatAdapter.messagesStartRow;
                    if (idx >= 0 && idx < ChatActivity.this.messages.size()) {
                        MessageObject message = (MessageObject) ChatActivity.this.messages.get(idx);
                        GroupedMessages groupedMessages = ChatActivity.this.getValidGroupedMessage(message);
                        if (groupedMessages != null) {
                            return ((GroupedMessagePosition) groupedMessages.positions.get(message)).spanSize;
                        }
                    }
                }
                return 1000;
            }
        });
        this.chatListView.setLayoutManager(this.chatLayoutManager);
        this.chatListView.addItemDecoration(new ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                outRect.bottom = 0;
                if (view instanceof ChatMessageCell) {
                    ChatMessageCell cell = (ChatMessageCell) view;
                    GroupedMessages group = cell.getCurrentMessagesGroup();
                    if (group != null) {
                        GroupedMessagePosition position = cell.getCurrentPosition();
                        if (position != null && position.siblingHeights != null) {
                            int a;
                            float maxHeight = ((float) Math.max(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) * 0.5f;
                            int h = 0;
                            for (float f : position.siblingHeights) {
                                h += (int) Math.ceil((double) (f * maxHeight));
                            }
                            h += (position.maxY - position.minY) * AndroidUtilities.dp(11.0f);
                            int count = group.posArray.size();
                            for (a = 0; a < count; a++) {
                                GroupedMessagePosition pos = (GroupedMessagePosition) group.posArray.get(a);
                                if (pos.minY == position.minY && ((pos.minX != position.minX || pos.maxX != position.maxX || pos.minY != position.minY || pos.maxY != position.maxY) && pos.minY == position.minY)) {
                                    h -= ((int) Math.ceil((double) (pos.ph * maxHeight))) - AndroidUtilities.dp(4.0f);
                                    break;
                                }
                            }
                            outRect.bottom = -h;
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
            private float totalDy = 0.0f;

            /* renamed from: org.telegram.ui.ChatActivity$25$1 */
            class C23971 extends AnimatorListenerAdapter {
                C23971() {
                }

                public void onAnimationEnd(Animator animation) {
                    if (animation.equals(ChatActivity.this.floatingDateAnimation)) {
                        ChatActivity.this.floatingDateAnimation = null;
                    }
                }
            }

            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 1) {
                    ChatActivity.this.scrollingFloatingDate = true;
                    ChatActivity.this.checkTextureViewPosition = true;
                } else if (newState == 0) {
                    ChatActivity.this.scrollingFloatingDate = false;
                    ChatActivity.this.checkTextureViewPosition = false;
                    ChatActivity.this.hideFloatingDateView(true);
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                ChatActivity.this.chatListView.invalidate();
                if (!(dy == 0 || !ChatActivity.this.scrollingFloatingDate || ChatActivity.this.currentFloatingTopIsNotMessage)) {
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
                        ChatActivity.this.floatingDateAnimation.addListener(new C23971());
                        ChatActivity.this.floatingDateAnimation.start();
                    }
                }
                ChatActivity.this.checkScrollForLoad(true);
                int firstVisibleItem = ChatActivity.this.chatLayoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItem != -1) {
                    int totalItemCount = ChatActivity.this.chatAdapter.getItemCount();
                    if (firstVisibleItem == 0 && ChatActivity.this.forwardEndReached[0]) {
                        ChatActivity.this.showPagedownButton(false, true);
                    } else if (dy > 0) {
                        if (ChatActivity.this.pagedownButton.getTag() == null) {
                            this.totalDy += (float) dy;
                            if (this.totalDy > ((float) this.scrollValue)) {
                                this.totalDy = 0.0f;
                                ChatActivity.this.showPagedownButton(true, true);
                                ChatActivity.this.pagedownButtonShowedByScroll = true;
                            }
                        }
                    } else if (ChatActivity.this.pagedownButtonShowedByScroll && ChatActivity.this.pagedownButton.getTag() != null) {
                        this.totalDy += (float) dy;
                        if (this.totalDy < ((float) (-this.scrollValue))) {
                            ChatActivity.this.showPagedownButton(false, true);
                            this.totalDy = 0.0f;
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
                DialogStatus dialogStatus = AppApplication.getDatabaseHandler().getDialogStatus(this.dialog_id);
                if (dialogStatus == null) {
                    addInviteBoxToUser(context, this.contentView);
                    AppApplication.getDatabaseHandler().createOrUpdateDialogStatus(new DialogStatus(this.dialog_id, false, false));
                } else if (!(dialogStatus.isHasHotgram() || dialogStatus.isInviteSent() || BuildConfig.FLAVOR.contains("mowjgram"))) {
                    addInviteBoxToUser(context, this.contentView);
                }
            }
        } catch (Exception e3) {
        }
        this.floatingDateView = new ChatActionCell(context);
        this.floatingDateView.setAlpha(0.0f);
        this.contentView.addView(this.floatingDateView, LayoutHelper.createFrame(-2, -2.0f, 49, 0.0f, this.inviteBoxAdded ? 24.0f : 4.0f, 0.0f, 0.0f));
        this.floatingDateView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ChatActivity.this.floatingDateView.getAlpha() != 0.0f) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(((long) ChatActivity.this.floatingDateView.getCustomDate()) * 1000);
                    int year = calendar.get(1);
                    int monthOfYear = calendar.get(2);
                    int dayOfMonth = calendar.get(5);
                    calendar.clear();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    ChatActivity.this.jumpToDate((int) (calendar.getTime().getTime() / 1000));
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
                public void onClick(View v) {
                    ChatActivity.this.scrollToMessageId(ChatActivity.this.info.pinned_msg_id, 0, true, 0, false);
                }
            });
            this.pinnedLineView = new View(context);
            this.pinnedLineView.setBackgroundColor(Theme.getColor(Theme.key_chat_topPanelLine));
            this.pinnedMessageView.addView(this.pinnedLineView, LayoutHelper.createFrame(2, 32.0f, 51, 8.0f, 8.0f, 0.0f, 0.0f));
            this.pinnedMessageImageView = new BackupImageView(context);
            this.pinnedMessageView.addView(this.pinnedMessageImageView, LayoutHelper.createFrame(32, 32.0f, 51, 17.0f, 8.0f, 0.0f, 0.0f));
            this.pinnedMessageNameTextView = new SimpleTextView(context);
            this.pinnedMessageNameTextView.setTextSize(14);
            this.pinnedMessageNameTextView.setTextColor(Theme.getColor(Theme.key_chat_topPanelTitle));
            this.pinnedMessageNameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.pinnedMessageView.addView(this.pinnedMessageNameTextView, LayoutHelper.createFrame(-1, (float) AndroidUtilities.dp(18.0f), 51, 18.0f, 7.3f, 52.0f, 0.0f));
            this.pinnedMessageTextView = new SimpleTextView(context);
            this.pinnedMessageTextView.setTextSize(14);
            this.pinnedMessageTextView.setTextColor(Theme.getColor(Theme.key_chat_topPanelMessage));
            this.pinnedMessageView.addView(this.pinnedMessageTextView, LayoutHelper.createFrame(-1, (float) AndroidUtilities.dp(18.0f), 51, 18.0f, 25.3f, 52.0f, 0.0f));
            this.closePinned = new ImageView(context);
            this.closePinned.setImageResource(R.drawable.miniplayer_close);
            this.closePinned.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_topPanelClose), Mode.MULTIPLY));
            this.closePinned.setScaleType(ScaleType.CENTER);
            this.pinnedMessageView.addView(this.closePinned, LayoutHelper.createFrame(48, 48, 53));
            this.closePinned.setOnClickListener(new View.OnClickListener() {

                /* renamed from: org.telegram.ui.ChatActivity$28$1 */
                class C23981 implements OnClickListener {
                    C23981() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        MessagesController.getInstance().pinChannelMessage(ChatActivity.this.currentChat, 0, false);
                    }
                }

                public void onClick(View v) {
                    if (ChatActivity.this.getParentActivity() != null) {
                        if (ChatActivity.this.currentChat.creator || (ChatActivity.this.currentChat.admin_rights != null && ((ChatActivity.this.currentChat.megagroup && ChatActivity.this.currentChat.admin_rights.pin_messages) || (!ChatActivity.this.currentChat.megagroup && ChatActivity.this.currentChat.admin_rights.edit_messages)))) {
                            org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                            builder.setMessage(LocaleController.getString("UnpinMessageAlert", R.string.UnpinMessageAlert));
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C23981());
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
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("user_id", ChatActivity.this.currentUser.id);
                args.putBoolean("addContact", true);
                ChatActivity.this.presentFragment(new ContactAddActivity(args));
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
            class C24001 implements OnClickListener {
                C24001() {
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

            public void onClick(View v) {
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
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C24001());
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
            public void onClick(View v) {
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
        this.alertView.addView(this.alertNameTextView, LayoutHelper.createFrame(-2, -2.0f, 51, 8.0f, 5.0f, 8.0f, 0.0f));
        this.alertTextView = new TextView(context);
        this.alertTextView.setTextSize(1, 14.0f);
        this.alertTextView.setTextColor(Theme.getColor(Theme.key_chat_topPanelMessage));
        this.alertTextView.setSingleLine(true);
        this.alertTextView.setEllipsize(TruncateAt.END);
        this.alertTextView.setMaxLines(1);
        this.alertView.addView(this.alertTextView, LayoutHelper.createFrame(-2, -2.0f, 51, 8.0f, 23.0f, 8.0f, 0.0f));
        this.pagedownButton = new FrameLayout(context);
        this.pagedownButton.setVisibility(4);
        this.contentView.addView(this.pagedownButton, LayoutHelper.createFrame(46, 59.0f, 85, 0.0f, 0.0f, 7.0f, 5.0f));
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
        this.contentView.addView(this.mentiondownButton, LayoutHelper.createFrame(46, 59.0f, 85, 0.0f, 0.0f, 7.0f, 5.0f));
        this.mentiondownButton.setOnClickListener(new View.OnClickListener() {

            /* renamed from: org.telegram.ui.ChatActivity$33$1 */
            class C24011 implements IntCallback {
                C24011() {
                }

                public void run(int param) {
                    if (param == 0) {
                        ChatActivity.this.hasAllMentionsLocal = false;
                        AnonymousClass33.this.loadLastUnreadMention();
                        return;
                    }
                    ChatActivity.this.scrollToMessageId(param, 0, false, 0, false);
                }
            }

            /* renamed from: org.telegram.ui.ChatActivity$33$2 */
            class C24032 implements RequestDelegate {
                C24032() {
                }

                public void run(final TLObject response, final TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            TLRPC$messages_Messages res = response;
                            if (error != null || res.messages.isEmpty()) {
                                if (res != null) {
                                    ChatActivity.this.newMentionsCount = res.count;
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
                            int id = ((TLRPC$Message) res.messages.get(0)).id;
                            long mid = (long) id;
                            if (ChatObject.isChannel(ChatActivity.this.currentChat)) {
                                mid |= ((long) ChatActivity.this.currentChat.id) << 32;
                            }
                            MessageObject object = (MessageObject) ChatActivity.this.messagesDict[0].get(Integer.valueOf(id));
                            MessagesStorage.getInstance().markMessageAsMention(mid);
                            if (object != null) {
                                object.messageOwner.media_unread = true;
                                object.messageOwner.mentioned = true;
                            }
                            ChatActivity.this.scrollToMessageId(id, 0, false, 0, false);
                        }
                    });
                }
            }

            private void loadLastUnreadMention() {
                if (ChatActivity.this.hasAllMentionsLocal) {
                    MessagesStorage.getInstance().getUnreadMention(ChatActivity.this.dialog_id, new C24011());
                    return;
                }
                TLRPC$TL_messages_getUnreadMentions req = new TLRPC$TL_messages_getUnreadMentions();
                req.peer = MessagesController.getInputPeer((int) ChatActivity.this.dialog_id);
                req.limit = 1;
                req.add_offset = ChatActivity.this.newMentionsCount - 1;
                ConnectionsManager.getInstance().sendRequest(req, new C24032());
            }

            public void onClick(View view) {
                loadLastUnreadMention();
            }
        });
        this.mentiondownButton.setOnLongClickListener(new OnLongClickListener() {

            /* renamed from: org.telegram.ui.ChatActivity$34$1 */
            class C24041 implements RequestDelegate {
                C24041() {
                }

                public void run(TLObject response, TLRPC$TL_error error) {
                }
            }

            public boolean onLongClick(View view) {
                for (int a = 0; a < ChatActivity.this.messages.size(); a++) {
                    MessageObject messageObject = (MessageObject) ChatActivity.this.messages.get(a);
                    if (messageObject.messageOwner.mentioned && !messageObject.isContentUnread()) {
                        messageObject.setContentIsRead();
                    }
                }
                ChatActivity.this.newMentionsCount = 0;
                MessagesStorage.getInstance().resetMentionsCount(ChatActivity.this.dialog_id, ChatActivity.this.newMentionsCount);
                ChatActivity.this.hasAllMentionsLocal = true;
                ChatActivity.this.showMentiondownButton(false, true);
                TLRPC$TL_messages_readMentions req = new TLRPC$TL_messages_readMentions();
                req.peer = MessagesController.getInputPeer((int) ChatActivity.this.dialog_id);
                ConnectionsManager.getInstance().sendRequest(req, new C24041());
                return true;
            }
        });
        if (!this.isBroadcast) {
            this.mentionContainer = new FrameLayout(context) {
                public void onDraw(Canvas canvas) {
                    if (ChatActivity.this.mentionListView.getChildCount() > 0) {
                        int top;
                        if (ChatActivity.this.mentionLayoutManager.getReverseLayout()) {
                            top = ChatActivity.this.mentionListViewScrollOffsetY + AndroidUtilities.dp(2.0f);
                            Theme.chat_composeShadowDrawable.setBounds(0, top + Theme.chat_composeShadowDrawable.getIntrinsicHeight(), getMeasuredWidth(), top);
                            Theme.chat_composeShadowDrawable.draw(canvas);
                            canvas.drawRect(0.0f, 0.0f, (float) getMeasuredWidth(), (float) top, Theme.chat_composeBackgroundPaint);
                            return;
                        }
                        if (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.isMediaLayout() && ChatActivity.this.mentionsAdapter.getBotContextSwitch() == null) {
                            top = ChatActivity.this.mentionListViewScrollOffsetY - AndroidUtilities.dp(4.0f);
                        } else {
                            top = ChatActivity.this.mentionListViewScrollOffsetY - AndroidUtilities.dp(2.0f);
                        }
                        int bottom = top + Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                        Theme.chat_composeShadowDrawable.setBounds(0, top, getMeasuredWidth(), bottom);
                        Theme.chat_composeShadowDrawable.draw(canvas);
                        canvas.drawRect(0.0f, (float) bottom, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
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

                public boolean onInterceptTouchEvent(MotionEvent event) {
                    if (ChatActivity.this.mentionLayoutManager.getReverseLayout()) {
                        if (!(ChatActivity.this.mentionListViewIsScrolling || ChatActivity.this.mentionListViewScrollOffsetY == 0 || event.getY() <= ((float) ChatActivity.this.mentionListViewScrollOffsetY))) {
                            return false;
                        }
                    } else if (!(ChatActivity.this.mentionListViewIsScrolling || ChatActivity.this.mentionListViewScrollOffsetY == 0 || event.getY() >= ((float) ChatActivity.this.mentionListViewScrollOffsetY))) {
                        return false;
                    }
                    boolean result = StickerPreviewViewer.getInstance().onInterceptTouchEvent(event, ChatActivity.this.mentionListView, 0, null);
                    if (super.onInterceptTouchEvent(event) || result) {
                        return true;
                    }
                    return false;
                }

                public boolean onTouchEvent(MotionEvent event) {
                    if (ChatActivity.this.mentionLayoutManager.getReverseLayout()) {
                        if (!(ChatActivity.this.mentionListViewIsScrolling || ChatActivity.this.mentionListViewScrollOffsetY == 0 || event.getY() <= ((float) ChatActivity.this.mentionListViewScrollOffsetY))) {
                            return false;
                        }
                    } else if (!(ChatActivity.this.mentionListViewIsScrolling || ChatActivity.this.mentionListViewScrollOffsetY == 0 || event.getY() >= ((float) ChatActivity.this.mentionListViewScrollOffsetY))) {
                        return false;
                    }
                    return super.onTouchEvent(event);
                }

                public void requestLayout() {
                    if (!ChatActivity.this.mentionListViewIgnoreLayout) {
                        super.requestLayout();
                    }
                }

                protected void onLayout(boolean changed, int l, int t, int r, int b) {
                    int width = r - l;
                    int height = b - t;
                    int newPosition = -1;
                    int newTop = 0;
                    if (!(ChatActivity.this.mentionLayoutManager.getReverseLayout() || ChatActivity.this.mentionListView == null || ChatActivity.this.mentionListViewLastViewPosition < 0 || width != this.lastWidth || height - this.lastHeight == 0)) {
                        newPosition = ChatActivity.this.mentionListViewLastViewPosition;
                        newTop = ((ChatActivity.this.mentionListViewLastViewTop + height) - this.lastHeight) - getPaddingTop();
                    }
                    super.onLayout(changed, l, t, r, b);
                    if (newPosition != -1) {
                        ChatActivity.this.mentionListViewIgnoreLayout = true;
                        if (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.isMediaLayout()) {
                            ChatActivity.this.mentionGridLayoutManager.scrollToPositionWithOffset(newPosition, newTop);
                        } else {
                            ChatActivity.this.mentionLayoutManager.scrollToPositionWithOffset(newPosition, newTop);
                        }
                        super.onLayout(false, l, t, r, b);
                        ChatActivity.this.mentionListViewIgnoreLayout = false;
                    }
                    this.lastHeight = height;
                    this.lastWidth = width;
                    ChatActivity.this.mentionListViewUpdateLayout();
                }
            };
            this.mentionListView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return StickerPreviewViewer.getInstance().onTouch(event, ChatActivity.this.mentionListView, 0, ChatActivity.this.mentionsOnItemClickListener, null);
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

                protected Size getSizeForItem(int i) {
                    float f = 100.0f;
                    if (ChatActivity.this.mentionsAdapter.getBotContextSwitch() != null) {
                        i++;
                    }
                    TLRPC$BotInlineResult object = ChatActivity.this.mentionsAdapter.getItem(i);
                    if (object instanceof TLRPC$BotInlineResult) {
                        TLRPC$BotInlineResult inlineResult = object;
                        if (inlineResult.document != null) {
                            float f2;
                            Size size = this.size;
                            if (inlineResult.document.thumb != null) {
                                f2 = (float) inlineResult.document.thumb.f78w;
                            } else {
                                f2 = 100.0f;
                            }
                            size.width = f2;
                            Size size2 = this.size;
                            if (inlineResult.document.thumb != null) {
                                f = (float) inlineResult.document.thumb.f77h;
                            }
                            size2.height = f;
                            for (int b = 0; b < inlineResult.document.attributes.size(); b++) {
                                DocumentAttribute attribute = (DocumentAttribute) inlineResult.document.attributes.get(b);
                                if ((attribute instanceof TLRPC$TL_documentAttributeImageSize) || (attribute instanceof TLRPC$TL_documentAttributeVideo)) {
                                    this.size.width = (float) attribute.f44w;
                                    this.size.height = (float) attribute.f43h;
                                    break;
                                }
                            }
                        } else {
                            this.size.width = (float) inlineResult.f68w;
                            this.size.height = (float) inlineResult.f67h;
                        }
                    }
                    return this.size;
                }

                protected int getFlowItemCount() {
                    if (ChatActivity.this.mentionsAdapter.getBotContextSwitch() != null) {
                        return getItemCount() - 1;
                    }
                    return super.getFlowItemCount();
                }
            };
            this.mentionGridLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
                public int getSpanSize(int position) {
                    if (ChatActivity.this.mentionsAdapter.getItem(position) instanceof TLRPC$TL_inlineBotSwitchPM) {
                        return 100;
                    }
                    if (ChatActivity.this.mentionsAdapter.getBotContextSwitch() != null) {
                        position--;
                    }
                    return ChatActivity.this.mentionGridLayoutManager.getSpanSizeForItem(position);
                }
            });
            this.mentionListView.addItemDecoration(new ItemDecoration() {
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                    int i = 0;
                    outRect.left = 0;
                    outRect.right = 0;
                    outRect.top = 0;
                    outRect.bottom = 0;
                    if (parent.getLayoutManager() == ChatActivity.this.mentionGridLayoutManager) {
                        int position = parent.getChildAdapterPosition(view);
                        if (ChatActivity.this.mentionsAdapter.getBotContextSwitch() == null) {
                            outRect.top = AndroidUtilities.dp(2.0f);
                        } else if (position != 0) {
                            position--;
                            if (!ChatActivity.this.mentionGridLayoutManager.isFirstRow(position)) {
                                outRect.top = AndroidUtilities.dp(2.0f);
                            }
                        } else {
                            return;
                        }
                        if (!ChatActivity.this.mentionGridLayoutManager.isLastInRow(position)) {
                            i = AndroidUtilities.dp(2.0f);
                        }
                        outRect.right = i;
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
                class C24061 extends AnimatorListenerAdapter {
                    C24061() {
                    }

                    public void onAnimationEnd(Animator animation) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animation)) {
                            ChatActivity.this.mentionListAnimation = null;
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animation)) {
                            ChatActivity.this.mentionListAnimation = null;
                        }
                    }
                }

                /* renamed from: org.telegram.ui.ChatActivity$42$2 */
                class C24072 extends AnimatorListenerAdapter {
                    C24072() {
                    }

                    public void onAnimationEnd(Animator animation) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animation)) {
                            ChatActivity.this.mentionContainer.setVisibility(8);
                            ChatActivity.this.mentionContainer.setTag(null);
                            ChatActivity.this.mentionListAnimation = null;
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animation)) {
                            ChatActivity.this.mentionListAnimation = null;
                        }
                    }
                }

                public void needChangePanelVisibility(boolean show) {
                    if (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.isMediaLayout()) {
                        ChatActivity.this.mentionListView.setLayoutManager(ChatActivity.this.mentionGridLayoutManager);
                    } else {
                        ChatActivity.this.mentionListView.setLayoutManager(ChatActivity.this.mentionLayoutManager);
                    }
                    if (show && ChatActivity.this.bottomOverlay.getVisibility() == 0) {
                        show = false;
                    }
                    if (show) {
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
                                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                                if (!preferences.getBoolean("secretbot", false)) {
                                    org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                    builder.setMessage(LocaleController.getString("SecretChatContextBotAlert", R.string.SecretChatContextBotAlert));
                                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                    ChatActivity.this.showDialog(builder.create());
                                    preferences.edit().putBoolean("secretbot", true).commit();
                                }
                            }
                            ChatActivity.this.mentionContainer.setVisibility(0);
                            ChatActivity.this.mentionContainer.setTag(null);
                            ChatActivity.this.mentionListAnimation = new AnimatorSet();
                            ChatActivity.this.mentionListAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(ChatActivity.this.mentionContainer, "alpha", new float[]{0.0f, 1.0f})});
                            ChatActivity.this.mentionListAnimation.addListener(new C24061());
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
                        animatorArr[0] = ObjectAnimator.ofFloat(ChatActivity.this.mentionContainer, "alpha", new float[]{0.0f});
                        access$10500.playTogether(animatorArr);
                        ChatActivity.this.mentionListAnimation.addListener(new C24072());
                        ChatActivity.this.mentionListAnimation.setDuration(200);
                        ChatActivity.this.mentionListAnimation.start();
                        return;
                    }
                    ChatActivity.this.mentionContainer.setTag(null);
                    ChatActivity.this.mentionContainer.setVisibility(8);
                }

                public void onContextSearch(boolean searching) {
                    if (ChatActivity.this.chatActivityEnterView != null) {
                        ChatActivity.this.chatActivityEnterView.setCaption(ChatActivity.this.mentionsAdapter.getBotCaption());
                        ChatActivity.this.chatActivityEnterView.showContextProgress(searching);
                    }
                }

                public void onContextClick(TLRPC$BotInlineResult result) {
                    if (ChatActivity.this.getParentActivity() != null && result.content_url != null) {
                        if (result.type.equals(MimeTypes.BASE_TYPE_VIDEO) || result.type.equals("web_player_video")) {
                            EmbedBottomSheet.show(ChatActivity.this.getParentActivity(), result.title != null ? result.title : "", result.description, result.content_url, result.content_url, result.f68w, result.f67h);
                        } else {
                            Browser.openUrl(ChatActivity.this.getParentActivity(), result.content_url);
                        }
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
                public void onItemClick(View view, int position) {
                    if (!ChatActivity.this.mentionsAdapter.isBannedInline()) {
                        TLObject object = ChatActivity.this.mentionsAdapter.getItem(position);
                        int start = ChatActivity.this.mentionsAdapter.getResultStartPosition();
                        int len = ChatActivity.this.mentionsAdapter.getResultLength();
                        if (object instanceof User) {
                            Spannable spannableString;
                            if (ChatActivity.this.searchingForUser && ChatActivity.this.searchContainer.getVisibility() == 0) {
                                ChatActivity.this.searchingUserMessages = (User) object;
                                if (ChatActivity.this.searchingUserMessages != null) {
                                    String name = ChatActivity.this.searchingUserMessages.first_name;
                                    if (TextUtils.isEmpty(name)) {
                                        name = ChatActivity.this.searchingUserMessages.last_name;
                                    }
                                    ChatActivity.this.searchingForUser = false;
                                    String from = LocaleController.getString("SearchFrom", R.string.SearchFrom);
                                    spannableString = new SpannableString(from + " " + name);
                                    spannableString.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_actionBarDefaultSubtitle)), from.length() + 1, spannableString.length(), 33);
                                    ChatActivity.this.searchItem.setSearchFieldCaption(spannableString);
                                    ChatActivity.this.mentionsAdapter.searchUsernameOrHashtag(null, 0, null, false);
                                    ChatActivity.this.searchItem.getSearchField().setHint(null);
                                    ChatActivity.this.searchItem.clearSearchText();
                                    MessagesSearchQuery.searchMessagesInChat("", ChatActivity.this.dialog_id, ChatActivity.this.mergeDialogId, ChatActivity.this.classGuid, 0, ChatActivity.this.searchingUserMessages);
                                    return;
                                }
                                return;
                            }
                            User user = (User) object;
                            if (user == null) {
                                return;
                            }
                            if (user.username != null) {
                                ChatActivity.this.chatActivityEnterView.replaceWithText(start, len, "@" + user.username + " ", false);
                                return;
                            }
                            spannableString = new SpannableString(UserObject.getFirstName(user) + " ");
                            spannableString.setSpan(new URLSpanUserMention("" + user.id, true), 0, spannableString.length(), 33);
                            ChatActivity.this.chatActivityEnterView.replaceWithText(start, len, spannableString, false);
                        } else if (object instanceof String) {
                            if (ChatActivity.this.mentionsAdapter.isBotCommands()) {
                                SendMessagesHelper.getInstance().sendMessage((String) object, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject, null, false, null, null, null);
                                ChatActivity.this.chatActivityEnterView.setFieldText("");
                                ChatActivity.this.showReplyPanel(false, null, null, null, false);
                                return;
                            }
                            ChatActivity.this.chatActivityEnterView.replaceWithText(start, len, object + " ", false);
                        } else if (object instanceof TLRPC$BotInlineResult) {
                            if (ChatActivity.this.chatActivityEnterView.getFieldText() != null) {
                                TLRPC$BotInlineResult result = (TLRPC$BotInlineResult) object;
                                if ((!result.type.equals("photo") || (result.photo == null && result.content_url == null)) && ((!result.type.equals("gif") || (result.document == null && result.content_url == null)) && (!result.type.equals(MimeTypes.BASE_TYPE_VIDEO) || result.document == null))) {
                                    ChatActivity.this.sendBotInlineResult(result);
                                    return;
                                }
                                ArrayList<Object> arrayList = ChatActivity.this.botContextResults = new ArrayList(ChatActivity.this.mentionsAdapter.getSearchResultBotContext());
                                PhotoViewer.getInstance().setParentActivity(ChatActivity.this.getParentActivity());
                                PhotoViewer.getInstance().openPhotoForSelect(arrayList, ChatActivity.this.mentionsAdapter.getItemPosition(position), 3, ChatActivity.this.botContextProvider, null);
                            }
                        } else if (object instanceof TLRPC$TL_inlineBotSwitchPM) {
                            ChatActivity.this.processInlineBotContextPM((TLRPC$TL_inlineBotSwitchPM) object);
                        } else if (object instanceof EmojiSuggestion) {
                            String code = ((EmojiSuggestion) object).emoji;
                            ChatActivity.this.chatActivityEnterView.addEmojiToRecent(code);
                            ChatActivity.this.chatActivityEnterView.replaceWithText(start, len, code, true);
                        }
                    }
                }
            };
            this.mentionsOnItemClickListener = anonymousClass43;
            recyclerListView.setOnItemClickListener(anonymousClass43);
            this.mentionListView.setOnItemLongClickListener(new OnItemLongClickListener() {

                /* renamed from: org.telegram.ui.ChatActivity$44$1 */
                class C24081 implements OnClickListener {
                    C24081() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        ChatActivity.this.mentionsAdapter.clearRecentHashtags();
                    }
                }

                public boolean onItemClick(View view, int position) {
                    boolean z = false;
                    if (ChatActivity.this.getParentActivity() == null || !ChatActivity.this.mentionsAdapter.isLongClickEnabled()) {
                        return false;
                    }
                    Object object = ChatActivity.this.mentionsAdapter.getItem(position);
                    if (!(object instanceof String)) {
                        return false;
                    }
                    if (!ChatActivity.this.mentionsAdapter.isBotCommands()) {
                        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("ClearSearch", R.string.ClearSearch));
                        builder.setPositiveButton(LocaleController.getString("ClearButton", R.string.ClearButton).toUpperCase(), new C24081());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ChatActivity.this.showDialog(builder.create());
                        return true;
                    } else if (!URLSpanBotCommand.enabled) {
                        return false;
                    } else {
                        ChatActivity.this.chatActivityEnterView.setFieldText("");
                        ChatActivityEnterView chatActivityEnterView = ChatActivity.this.chatActivityEnterView;
                        String str = (String) object;
                        if (ChatActivity.this.currentChat != null && ChatActivity.this.currentChat.megagroup) {
                            z = true;
                        }
                        chatActivityEnterView.setCommand(null, str, true, z);
                        return true;
                    }
                }
            });
            this.mentionListView.setOnScrollListener(new OnScrollListener() {
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    boolean z = true;
                    ChatActivity chatActivity = ChatActivity.this;
                    if (newState != 1) {
                        z = false;
                    }
                    chatActivity.mentionListViewIsScrolling = z;
                }

                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    int lastVisibleItem;
                    int visibleItemCount;
                    if (ChatActivity.this.mentionsAdapter.isBotContext() && ChatActivity.this.mentionsAdapter.isMediaLayout()) {
                        lastVisibleItem = ChatActivity.this.mentionGridLayoutManager.findLastVisibleItemPosition();
                    } else {
                        lastVisibleItem = ChatActivity.this.mentionLayoutManager.findLastVisibleItemPosition();
                    }
                    if (lastVisibleItem == -1) {
                        visibleItemCount = 0;
                    } else {
                        visibleItemCount = lastVisibleItem;
                    }
                    if (visibleItemCount > 0 && lastVisibleItem > ChatActivity.this.mentionsAdapter.getItemCount() - 5) {
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
        Drawable drawable = Theme.createCircleDrawable(AndroidUtilities.dp(42.0f), Theme.getColor(Theme.key_chat_goDownButton));
        Drawable shadowDrawable = context.getResources().getDrawable(R.drawable.pagedown_shadow).mutate();
        shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_goDownButtonShadow), Mode.MULTIPLY));
        Drawable combinedDrawable = new CombinedDrawable(shadowDrawable, drawable, 0, 0);
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
        drawable = Theme.createCircleDrawable(AndroidUtilities.dp(42.0f), Theme.getColor(Theme.key_chat_goDownButton));
        shadowDrawable = context.getResources().getDrawable(R.drawable.pagedown_shadow).mutate();
        shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_goDownButtonShadow), Mode.MULTIPLY));
        combinedDrawable = new CombinedDrawable(shadowDrawable, drawable, 0, 0);
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
            fragmentContextView = new FragmentContextView(context, this, true);
            this.fragmentLocationContextView = fragmentContextView;
            sizeNotifierFrameLayout.addView(fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, 0.0f, -36.0f, 0.0f, 0.0f));
            sizeNotifierFrameLayout = this.contentView;
            fragmentContextView = new FragmentContextView(context, this, false);
            this.fragmentContextView = fragmentContextView;
            sizeNotifierFrameLayout.addView(fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, 0.0f, -36.0f, 0.0f, 0.0f));
            this.fragmentContextView.setAdditionalContextView(this.fragmentLocationContextView);
            this.fragmentLocationContextView.setAdditionalContextView(this.fragmentContextView);
        }
        this.contentView.addView(this.actionBar);
        this.overlayView = new View(context);
        this.overlayView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0) {
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
        if (this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 46) {
            z2 = true;
        } else {
            z2 = false;
        }
        chatActivityEnterView.setAllowStickersAndGifs(z, z2);
        this.contentView.addView(this.chatActivityEnterView, this.contentView.getChildCount() - 1, LayoutHelper.createFrame(-1, -2, 83));
        this.chatActivityEnterView.setDelegate(new ChatActivityEnterViewDelegate() {
            public void onMessageSend(CharSequence message) {
                ChatActivity.this.moveScrollToLastMessage();
                ChatActivity.this.showReplyPanel(false, null, null, null, false);
                if (ChatActivity.this.mentionsAdapter != null) {
                    ChatActivity.this.mentionsAdapter.addHashtagsFromMessage(message);
                }
            }

            public void onSwitchRecordMode(boolean video) {
                ChatActivity.this.showVoiceHint(false, video);
            }

            public void onPreAudioVideoRecord() {
                ChatActivity.this.showVoiceHint(true, false);
            }

            public void onTextChanged(final CharSequence text, boolean bigChange) {
                if (ChatActivity.this.startReplyOnTextChange && text.length() > 0) {
                    ChatActivity.this.actionBar.getActionBarMenuOnItemClick().onItemClick(19);
                    ChatActivity.this.startReplyOnTextChange = false;
                }
                MediaController instance = MediaController.getInstance();
                boolean z = !TextUtils.isEmpty(text) || ChatActivity.this.chatActivityEnterView.isEditingMessage();
                instance.setInputFieldHasText(z);
                if (!(ChatActivity.this.stickersAdapter == null || ChatActivity.this.chatActivityEnterView.isEditingMessage() || !ChatObject.canSendStickers(ChatActivity.this.currentChat))) {
                    ChatActivity.this.stickersAdapter.loadStikersForEmoji(text);
                }
                if (ChatActivity.this.mentionsAdapter != null) {
                    ChatActivity.this.mentionsAdapter.searchUsernameOrHashtag(text.toString(), ChatActivity.this.chatActivityEnterView.getCursorPosition(), ChatActivity.this.messages, false);
                }
                if (ChatActivity.this.waitingForCharaterEnterRunnable != null) {
                    AndroidUtilities.cancelRunOnUIThread(ChatActivity.this.waitingForCharaterEnterRunnable);
                    ChatActivity.this.waitingForCharaterEnterRunnable = null;
                }
                if (!ChatObject.canSendEmbed(ChatActivity.this.currentChat) || !ChatActivity.this.chatActivityEnterView.isMessageWebPageSearchEnabled()) {
                    return;
                }
                if (!ChatActivity.this.chatActivityEnterView.isEditingMessage() || !ChatActivity.this.chatActivityEnterView.isEditingCaption()) {
                    if (bigChange) {
                        ChatActivity.this.searchLinks(text, true);
                        return;
                    }
                    ChatActivity.this.waitingForCharaterEnterRunnable = new Runnable() {
                        public void run() {
                            if (this == ChatActivity.this.waitingForCharaterEnterRunnable) {
                                ChatActivity.this.searchLinks(text, false);
                                ChatActivity.this.waitingForCharaterEnterRunnable = null;
                            }
                        }
                    };
                    AndroidUtilities.runOnUIThread(ChatActivity.this.waitingForCharaterEnterRunnable, AndroidUtilities.WEB_URL == null ? 3000 : 1000);
                }
            }

            public void needSendTyping() {
                MessagesController.getInstance().sendTyping(ChatActivity.this.dialog_id, 0, ChatActivity.this.classGuid);
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

            public void onMessageEditEnd(boolean loading) {
                if (!loading) {
                    MentionsAdapter access$3900 = ChatActivity.this.mentionsAdapter;
                    boolean z = ChatActivity.this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(ChatActivity.this.currentEncryptedChat.layer) >= 46;
                    access$3900.setNeedBotContext(z);
                    ChatActivity.this.chatListView.setOnItemLongClickListener(ChatActivity.this.onItemLongClickListener);
                    ChatActivity.this.chatListView.setOnItemClickListener(ChatActivity.this.onItemClickListener);
                    ChatActivity.this.chatListView.setClickable(true);
                    ChatActivity.this.chatListView.setLongClickable(true);
                    ChatActivity.this.mentionsAdapter.setAllowNewMentions(true);
                    ChatActivity.this.actionModeTitleContainer.setVisibility(8);
                    ChatActivity.this.selectedMessagesCountTextView.setVisibility(0);
                    ChatActivityEnterView chatActivityEnterView = ChatActivity.this.chatActivityEnterView;
                    if (ChatActivity.this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(ChatActivity.this.currentEncryptedChat.layer) >= 23) {
                        z = true;
                    } else {
                        z = false;
                    }
                    boolean z2 = ChatActivity.this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(ChatActivity.this.currentEncryptedChat.layer) >= 46;
                    chatActivityEnterView.setAllowStickersAndGifs(z, z2);
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

            public void onWindowSizeChanged(int size) {
                boolean z = true;
                if (size < AndroidUtilities.dp(72.0f) + ActionBar.getCurrentActionBarHeight()) {
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

            public void onStickersTab(boolean opened) {
                if (ChatActivity.this.emojiButtonRed != null) {
                    ChatActivity.this.emojiButtonRed.setVisibility(8);
                }
                ChatActivity.this.allowContextBotPanelSecond = !opened;
                ChatActivity.this.checkContextBotPanel();
            }

            public void didPressedAttachButton() {
                ChatActivity.this.openAttachMenu();
            }

            public void needStartRecordVideo(int state) {
                if (ChatActivity.this.instantCameraView == null) {
                    return;
                }
                if (state == 0) {
                    ChatActivity.this.instantCameraView.showCamera();
                } else if (state == 1 || state == 3 || state == 4) {
                    ChatActivity.this.instantCameraView.send(state);
                } else if (state == 2) {
                    ChatActivity.this.instantCameraView.cancel();
                }
            }

            public void needChangeVideoPreviewState(int state, float seekProgress) {
                if (ChatActivity.this.instantCameraView != null) {
                    ChatActivity.this.instantCameraView.changeVideoPreviewState(state, seekProgress);
                }
            }

            public void needStartRecordAudio(int state) {
                ChatActivity.this.overlayView.setVisibility(state == 0 ? 8 : 0);
            }

            public void needShowMediaBanHint() {
                ChatActivity.this.showMediaBannedHint();
            }
        });
        fragmentContextView = new FrameLayout(context) {
            public void setTranslationY(float translationY) {
                super.setTranslationY(translationY);
                if (ChatActivity.this.chatActivityEnterView != null) {
                    ChatActivity.this.chatActivityEnterView.invalidate();
                }
                if (getVisibility() != 8) {
                    int height = getLayoutParams().height;
                    if (ChatActivity.this.chatListView != null) {
                        ChatActivity.this.chatListView.setTranslationY(translationY);
                    }
                    if (ChatActivity.this.progressView != null) {
                        ChatActivity.this.progressView.setTranslationY(translationY);
                    }
                    if (ChatActivity.this.mentionContainer != null) {
                        ChatActivity.this.mentionContainer.setTranslationY(translationY);
                    }
                    if (ChatActivity.this.pagedownButton != null) {
                        ChatActivity.this.pagedownButton.setTranslationY(translationY);
                    }
                    if (ChatActivity.this.mentiondownButton != null) {
                        FrameLayout access$6500 = ChatActivity.this.mentiondownButton;
                        if (ChatActivity.this.pagedownButton.getVisibility() == 0) {
                            translationY -= (float) AndroidUtilities.dp(72.0f);
                        }
                        access$6500.setTranslationY(translationY);
                    }
                }
            }

            public boolean hasOverlappingRendering() {
                return false;
            }

            public void setVisibility(int visibility) {
                float f = 0.0f;
                super.setVisibility(visibility);
                if (visibility == 8) {
                    FrameLayout access$6400;
                    if (ChatActivity.this.chatListView != null) {
                        ChatActivity.this.chatListView.setTranslationY(0.0f);
                    }
                    if (ChatActivity.this.progressView != null) {
                        ChatActivity.this.progressView.setTranslationY(0.0f);
                    }
                    if (ChatActivity.this.mentionContainer != null) {
                        ChatActivity.this.mentionContainer.setTranslationY(0.0f);
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
            final MessageObject advancedObject = getMessageObject();
            try {
                getMessageObject().backupCaptionAndText();
            } catch (Exception e4) {
            }
            if (advancedObject != null) {
                if (TextUtils.isEmpty(advancedObject.caption)) {
                    this.chatActivityEnterView.setFieldText(advancedObject.messageText);
                } else {
                    this.chatActivityEnterView.setFieldText(advancedObject.caption);
                    advancedObject.caption = "";
                    advancedObject.messageText = "";
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        objects = new Object[13];
                        ArrayList<MessageObject> arr = new ArrayList();
                        arr.add(advancedObject);
                        objects[2] = arr;
                        objects[3] = Boolean.valueOf(true);
                        objects[4] = Integer.valueOf(0);
                        objects[5] = Integer.valueOf(0);
                        objects[6] = Integer.valueOf(0);
                        objects[7] = Integer.valueOf(0);
                        objects[8] = Integer.valueOf(2);
                        objects[9] = Boolean.valueOf(false);
                        objects[10] = Integer.valueOf(ChatActivity.this.classGuid);
                        objects[11] = Integer.valueOf(0);
                        objects[12] = Integer.valueOf(0);
                        ChatActivity.this.didReceivedNotification(NotificationCenter.messagesDidLoaded, objects);
                    }
                }, 1000);
            }
            this.chatActivityEnterView.getSendButton().setOnClickListener(new View.OnClickListener() {

                /* renamed from: org.telegram.ui.ChatActivity$50$1 */
                class C24111 implements OnDismissListener {
                    C24111() {
                    }

                    public void onDismiss(DialogInterface dialog) {
                        ChatActivity.this.showingDialog = false;
                    }
                }

                /* renamed from: org.telegram.ui.ChatActivity$50$2 */
                class C24122 implements OnCancelListener {
                    C24122() {
                    }

                    public void onCancel(DialogInterface dialog) {
                        ChatActivity.this.showingDialog = false;
                    }
                }

                public void onClick(View v) {
                    MessageObject msgObj = advancedObject;
                    Log.d("LEE", "mediaExists:" + msgObj.mediaExists);
                    if (msgObj.mediaExists || msgObj.type == 3) {
                        msgObj.caption = ChatActivity.this.chatActivityEnterView.getFieldText();
                    } else {
                        msgObj.messageText = ChatActivity.this.chatActivityEnterView.getFieldText();
                        msgObj.messageOwner.message = ChatActivity.this.chatActivityEnterView.getFieldText().toString();
                    }
                    Log.d("LEE", "mediaExists:" + msgObj.messageText + "   " + msgObj.caption);
                    ChatActivity.this.showingDialog = true;
                    ShareAlert visibleDialog = new ShareAlert(ChatActivity.this.getParentActivity(), msgObj, "", true, "", true, false);
                    ChatActivity.this.showDialog(visibleDialog);
                    visibleDialog.setOnDismissListener(new C24111());
                    visibleDialog.setOnCancelListener(new C24122());
                }
            });
        }
        this.chatActivityEnterView.addTopView(fragmentContextView, 48);
        fragmentContextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ChatActivity.this.replyingMessageObject != null) {
                    ChatActivity.this.scrollToMessageId(ChatActivity.this.replyingMessageObject.getId(), 0, true, 0, false);
                }
            }
        });
        this.replyLineView = new View(context);
        this.replyLineView.setBackgroundColor(Theme.getColor(Theme.key_chat_replyPanelLine));
        fragmentContextView.addView(this.replyLineView, LayoutHelper.createFrame(-1, 1, 83));
        this.replyIconImageView = new ImageView(context);
        this.replyIconImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_replyPanelIcons), Mode.MULTIPLY));
        this.replyIconImageView.setScaleType(ScaleType.CENTER);
        fragmentContextView.addView(this.replyIconImageView, LayoutHelper.createFrame(52, 46, 51));
        this.replyCloseImageView = new ImageView(context);
        this.replyCloseImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_replyPanelClose), Mode.MULTIPLY));
        this.replyCloseImageView.setImageResource(R.drawable.msg_panel_clear);
        this.replyCloseImageView.setScaleType(ScaleType.CENTER);
        fragmentContextView.addView(this.replyCloseImageView, LayoutHelper.createFrame(52, 46.0f, 53, 0.0f, 0.5f, 0.0f, 0.0f));
        this.replyCloseImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        fragmentContextView.addView(this.replyNameTextView, LayoutHelper.createFrame(-1, 18.0f, 51, 52.0f, 6.0f, 52.0f, 0.0f));
        this.replyObjectTextView = new SimpleTextView(context);
        this.replyObjectTextView.setTextSize(14);
        this.replyObjectTextView.setTextColor(Theme.getColor(Theme.key_chat_replyPanelMessage));
        fragmentContextView.addView(this.replyObjectTextView, LayoutHelper.createFrame(-1, 18.0f, 51, 52.0f, 24.0f, 52.0f, 0.0f));
        this.replyImageView = new BackupImageView(context);
        fragmentContextView.addView(this.replyImageView, LayoutHelper.createFrame(34, 34.0f, 51, 52.0f, 6.0f, 0.0f, 0.0f));
        this.stickersPanel = new FrameLayout(context);
        this.stickersPanel.setVisibility(8);
        this.contentView.addView(this.stickersPanel, LayoutHelper.createFrame(-2, 81.5f, 83, 0.0f, 0.0f, 0.0f, 38.0f));
        this.stickersListView = new RecyclerListView(context) {
            public boolean onInterceptTouchEvent(MotionEvent event) {
                boolean result = StickerPreviewViewer.getInstance().onInterceptTouchEvent(event, ChatActivity.this.stickersListView, 0, null);
                if (super.onInterceptTouchEvent(event) || result) {
                    return true;
                }
                return false;
            }
        };
        this.stickersListView.setTag(Integer.valueOf(3));
        this.stickersListView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return StickerPreviewViewer.getInstance().onTouch(event, ChatActivity.this.stickersListView, 0, ChatActivity.this.stickersOnItemClickListener, null);
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
        this.stickersPanel.addView(this.stickersPanelArrow, LayoutHelper.createFrame(-2, -2.0f, 83, 53.0f, 0.0f, 0.0f, 0.0f));
        this.searchContainer = new FrameLayout(context) {
            public void onDraw(Canvas canvas) {
                int bottom = Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                Theme.chat_composeShadowDrawable.setBounds(0, 0, getMeasuredWidth(), bottom);
                Theme.chat_composeShadowDrawable.draw(canvas);
                canvas.drawRect(0.0f, (float) bottom, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
            }
        };
        this.searchContainer.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
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
        this.searchContainer.addView(this.searchUpButton, LayoutHelper.createFrame(48, 48.0f, 53, 0.0f, 0.0f, 48.0f, 0.0f));
        this.searchUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MessagesSearchQuery.searchMessagesInChat(null, ChatActivity.this.dialog_id, ChatActivity.this.mergeDialogId, ChatActivity.this.classGuid, 1, ChatActivity.this.searchingUserMessages);
            }
        });
        this.searchDownButton = new ImageView(context);
        this.searchDownButton.setScaleType(ScaleType.CENTER);
        this.searchDownButton.setImageResource(R.drawable.search_down);
        this.searchDownButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_searchPanelIcons), Mode.MULTIPLY));
        this.searchContainer.addView(this.searchDownButton, LayoutHelper.createFrame(48, 48.0f, 53, 0.0f, 0.0f, 0.0f, 0.0f));
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
            this.searchContainer.addView(this.searchUserButton, LayoutHelper.createFrame(48, 48.0f, 51, 48.0f, 0.0f, 0.0f, 0.0f));
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
            class C24141 implements OnDateSetListener {
                C24141() {
                }

                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.set(year, month, dayOfMonth);
                    int date = (int) (calendar.getTime().getTime() / 1000);
                    ChatActivity.this.clearChatData();
                    ChatActivity.this.waitingForLoad.add(Integer.valueOf(ChatActivity.this.lastLoadIndex));
                    MessagesController.getInstance().loadMessages(ChatActivity.this.dialog_id, 30, 0, date, true, 0, ChatActivity.this.classGuid, 4, 0, ChatObject.isChannel(ChatActivity.this.currentChat), ChatActivity.this.lastLoadIndex = ChatActivity.this.lastLoadIndex + 1);
                }
            }

            /* renamed from: org.telegram.ui.ChatActivity$60$2 */
            class C24152 implements OnClickListener {
                C24152() {
                }

                public void onClick(DialogInterface dialog, int which) {
                }
            }

            public void onClick(View view) {
                if (ChatActivity.this.getParentActivity() != null) {
                    AndroidUtilities.hideKeyboard(ChatActivity.this.searchItem.getSearchField());
                    Calendar calendar = Calendar.getInstance();
                    try {
                        DatePickerDialog dialog = new DatePickerDialog(ChatActivity.this.getParentActivity(), new C24141(), calendar.get(1), calendar.get(2), calendar.get(5));
                        final DatePicker datePicker = dialog.getDatePicker();
                        datePicker.setMinDate(1375315200000L);
                        datePicker.setMaxDate(System.currentTimeMillis());
                        dialog.setButton(-1, LocaleController.getString("JumpToDate", R.string.JumpToDate), dialog);
                        dialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C24152());
                        if (VERSION.SDK_INT >= 21) {
                            dialog.setOnShowListener(new OnShowListener() {
                                public void onShow(DialogInterface dialog) {
                                    int count = datePicker.getChildCount();
                                    for (int a = 0; a < count; a++) {
                                        View child = datePicker.getChildAt(a);
                                        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
                                        layoutParams.width = -1;
                                        child.setLayoutParams(layoutParams);
                                    }
                                }
                            });
                        }
                        ChatActivity.this.showDialog(dialog);
                    } catch (Exception e) {
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
        this.searchContainer.addView(this.searchCountText, LayoutHelper.createFrame(-2, -2.0f, 21, 0.0f, 0.0f, 108.0f, 0.0f));
        this.bottomOverlay = new FrameLayout(context) {
            public void onDraw(Canvas canvas) {
                int bottom = Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                Theme.chat_composeShadowDrawable.setBounds(0, 0, getMeasuredWidth(), bottom);
                Theme.chat_composeShadowDrawable.draw(canvas);
                canvas.drawRect(0.0f, (float) bottom, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
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
        this.bottomOverlay.addView(this.bottomOverlayText, LayoutHelper.createFrame(-2, -2.0f, 17, 14.0f, 0.0f, 14.0f, 0.0f));
        this.bottomOverlayChat = new FrameLayout(context) {
            public void onDraw(Canvas canvas) {
                int bottom = Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                Theme.chat_composeShadowDrawable.setBounds(0, 0, getMeasuredWidth(), bottom);
                Theme.chat_composeShadowDrawable.draw(canvas);
                canvas.drawRect(0.0f, (float) bottom, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
            }
        };
        this.bottomOverlayChat.setWillNotDraw(false);
        this.bottomOverlayChat.setPadding(0, AndroidUtilities.dp(3.0f), 0, 0);
        this.bottomOverlayChat.setVisibility(4);
        this.contentView.addView(this.bottomOverlayChat, LayoutHelper.createFrame(-1, 51, 80));
        this.bottomOverlayChat.setOnClickListener(new View.OnClickListener() {

            /* renamed from: org.telegram.ui.ChatActivity$63$1 */
            class C24171 implements OnClickListener {
                C24171() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    MessagesController.getInstance().unblockUser(ChatActivity.this.currentUser.id);
                }
            }

            /* renamed from: org.telegram.ui.ChatActivity$63$2 */
            class C24182 implements OnClickListener {
                C24182() {
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
                        Intent channelIntent = new Intent(ApplicationLoader.applicationContext, DownloadManagerService.class);
                        channelIntent.putExtra(Constants.EXTRA_IS_FORCE, true);
                        ApplicationLoader.applicationContext.startService(channelIntent);
                        ChatActivity.this.downloadManagerLoading.setVisibility(0);
                        return;
                    }
                    ApplicationLoader.applicationContext.stopService(new Intent(ApplicationLoader.applicationContext, DownloadManagerService.class));
                } else if (ChatActivity.this.getParentActivity() != null) {
                    org.telegram.ui.ActionBar.AlertDialog.Builder builder = null;
                    if (ChatActivity.this.currentUser == null || !ChatActivity.this.userBlocked) {
                        if (ChatActivity.this.currentUser != null && ChatActivity.this.currentUser.bot && ChatActivity.this.botUser != null) {
                            if (ChatActivity.this.botUser.length() != 0) {
                                MessagesController.getInstance().sendBotStart(ChatActivity.this.currentUser, ChatActivity.this.botUser);
                            } else {
                                SendMessagesHelper.getInstance().sendMessage("/start", ChatActivity.this.dialog_id, null, null, false, null, null, null);
                            }
                            ChatActivity.this.botUser = null;
                            ChatActivity.this.updateBottomOverlay();
                        } else if (!ChatObject.isChannel(ChatActivity.this.currentChat) || (ChatActivity.this.currentChat instanceof TLRPC$TL_channelForbidden)) {
                            builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                            builder.setMessage(LocaleController.getString("AreYouSureDeleteThisChat", R.string.AreYouSureDeleteThisChat));
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C24182());
                        } else if (ChatObject.isNotInChat(ChatActivity.this.currentChat)) {
                            MessagesController.getInstance().addUserToChat(ChatActivity.this.currentChat.id, UserConfig.getCurrentUser(), null, 0, null, ChatActivity.this);
                        } else {
                            ChatActivity.this.toggleMute(true);
                        }
                    } else if (ChatActivity.this.currentUser.bot) {
                        String botUserLast = ChatActivity.this.botUser;
                        ChatActivity.this.botUser = null;
                        MessagesController.getInstance().unblockUser(ChatActivity.this.currentUser.id);
                        if (botUserLast == null || botUserLast.length() == 0) {
                            SendMessagesHelper.getInstance().sendMessage("/start", ChatActivity.this.dialog_id, null, null, false, null, null, null);
                        } else {
                            MessagesController.getInstance().sendBotStart(ChatActivity.this.currentUser, botUserLast);
                        }
                    } else {
                        builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                        builder.setMessage(LocaleController.getString("AreYouSureUnblockContact", R.string.AreYouSureUnblockContact));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C24171());
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
        ChatActivityEnterView chatActivityEnterView2 = this.chatActivityEnterView;
        if (this.userBlocked) {
            messageObject = null;
        } else {
            messageObject = this.botButtons;
        }
        chatActivityEnterView2.setButtons(messageObject);
        updateContactStatus();
        updateBottomOverlay();
        updateSecretStatus();
        updateSpamView();
        updatePinnedMessageView(true);
        try {
            if (this.currentEncryptedChat != null && VERSION.SDK_INT >= 23 && (UserConfig.passcodeHash.length() == 0 || UserConfig.allowScreenCapture)) {
                getParentActivity().getWindow().setFlags(8192, 8192);
            }
        } catch (Throwable e5) {
            FileLog.e(e5);
        }
        if (oldMessage != null) {
            this.chatActivityEnterView.setFieldText(oldMessage);
        }
        fixLayoutInternal();
        return this.fragmentView;
    }

    private TextureView createTextureView(boolean add) {
        if (this.parentLayout == null) {
            return null;
        }
        if (this.roundVideoContainer == null) {
            if (VERSION.SDK_INT >= 21) {
                this.roundVideoContainer = new FrameLayout(getParentActivity()) {
                    public void setTranslationY(float translationY) {
                        super.setTranslationY(translationY);
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
                    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
                        super.onSizeChanged(w, h, oldw, oldh);
                        ChatActivity.this.aspectPath.reset();
                        ChatActivity.this.aspectPath.addCircle((float) (w / 2), (float) (h / 2), (float) (w / 2), Direction.CW);
                        ChatActivity.this.aspectPath.toggleInverseFillType();
                    }

                    public void setTranslationY(float translationY) {
                        super.setTranslationY(translationY);
                        ChatActivity.this.contentView.invalidate();
                    }

                    public void setVisibility(int visibility) {
                        super.setVisibility(visibility);
                        if (visibility == 0) {
                            setLayerType(2, null);
                        }
                    }

                    protected void dispatchDraw(Canvas canvas) {
                        super.dispatchDraw(canvas);
                        canvas.drawPath(ChatActivity.this.aspectPath, ChatActivity.this.aspectPaint);
                    }
                };
                this.aspectPath = new Path();
                this.aspectPaint = new Paint(1);
                this.aspectPaint.setColor(-16777216);
                this.aspectPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
            }
            this.roundVideoContainer.setWillNotDraw(false);
            this.roundVideoContainer.setVisibility(4);
            this.aspectRatioFrameLayout = new AspectRatioFrameLayout(getParentActivity());
            this.aspectRatioFrameLayout.setBackgroundColor(0);
            if (add) {
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

    private void sendBotInlineResult(TLRPC$BotInlineResult result) {
        int uid = this.mentionsAdapter.getContextBotId();
        HashMap<String, String> params = new HashMap();
        params.put("id", result.id);
        params.put("query_id", "" + result.query_id);
        params.put("bot", "" + uid);
        params.put("bot_name", this.mentionsAdapter.getContextBotName());
        SendMessagesHelper.prepareSendingBotContextResult(result, params, this.dialog_id, this.replyingMessageObject);
        this.chatActivityEnterView.setFieldText("");
        showReplyPanel(false, null, null, null, false);
        SearchQuery.increaseInlineRaiting(uid);
    }

    private void mentionListViewUpdateLayout() {
        if (this.mentionListView.getChildCount() <= 0) {
            this.mentionListViewScrollOffsetY = 0;
            this.mentionListViewLastViewPosition = -1;
            return;
        }
        View child = this.mentionListView.getChildAt(this.mentionListView.getChildCount() - 1);
        Holder holder = (Holder) this.mentionListView.findContainingViewHolder(child);
        int newOffset;
        if (this.mentionLayoutManager.getReverseLayout()) {
            if (holder != null) {
                this.mentionListViewLastViewPosition = holder.getAdapterPosition();
                this.mentionListViewLastViewTop = child.getBottom();
            } else {
                this.mentionListViewLastViewPosition = -1;
            }
            child = this.mentionListView.getChildAt(0);
            holder = (Holder) this.mentionListView.findContainingViewHolder(child);
            newOffset = (child.getBottom() >= this.mentionListView.getMeasuredHeight() || holder == null || holder.getAdapterPosition() != 0) ? this.mentionListView.getMeasuredHeight() : child.getBottom();
            if (this.mentionListViewScrollOffsetY != newOffset) {
                RecyclerListView recyclerListView = this.mentionListView;
                this.mentionListViewScrollOffsetY = newOffset;
                recyclerListView.setBottomGlowOffset(newOffset);
                this.mentionListView.setTopGlowOffset(0);
                this.mentionListView.invalidate();
                this.mentionContainer.invalidate();
                return;
            }
            return;
        }
        if (holder != null) {
            this.mentionListViewLastViewPosition = holder.getAdapterPosition();
            this.mentionListViewLastViewTop = child.getTop();
        } else {
            this.mentionListViewLastViewPosition = -1;
        }
        child = this.mentionListView.getChildAt(0);
        holder = (Holder) this.mentionListView.findContainingViewHolder(child);
        if (child.getTop() <= 0 || holder == null || holder.getAdapterPosition() != 0) {
            newOffset = 0;
        } else {
            newOffset = child.getTop();
        }
        if (this.mentionListViewScrollOffsetY != newOffset) {
            recyclerListView = this.mentionListView;
            this.mentionListViewScrollOffsetY = newOffset;
            recyclerListView.setTopGlowOffset(newOffset);
            this.mentionListView.setBottomGlowOffset(0);
            this.mentionListView.invalidate();
            this.mentionContainer.invalidate();
        }
    }

    private void checkBotCommands() {
        boolean z = true;
        URLSpanBotCommand.enabled = false;
        if (this.currentUser != null && this.currentUser.bot) {
            URLSpanBotCommand.enabled = true;
        } else if (this.info instanceof TLRPC$TL_chatFull) {
            int a = 0;
            while (a < this.info.participants.participants.size()) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$ChatParticipant) this.info.participants.participants.get(a)).user_id));
                if (user == null || !user.bot) {
                    a++;
                } else {
                    URLSpanBotCommand.enabled = true;
                    return;
                }
            }
        } else if (this.info instanceof TLRPC$TL_channelFull) {
            if (this.info.bot_info.isEmpty() || this.currentChat == null || !this.currentChat.megagroup) {
                z = false;
            }
            URLSpanBotCommand.enabled = z;
        }
    }

    private GroupedMessages getValidGroupedMessage(MessageObject message) {
        if (message.getGroupId() == 0) {
            return null;
        }
        GroupedMessages groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(message.getGroupId()));
        if (groupedMessages == null) {
            return groupedMessages;
        }
        if (groupedMessages.messages.size() <= 1 || groupedMessages.positions.get(message) == null) {
            return null;
        }
        return groupedMessages;
    }

    private void jumpToDate(int date) {
        if (!this.messages.isEmpty()) {
            MessageObject lastMessage = (MessageObject) this.messages.get(this.messages.size() - 1);
            if (((MessageObject) this.messages.get(0)).messageOwner.date >= date && lastMessage.messageOwner.date <= date) {
                int a = this.messages.size() - 1;
                while (a >= 0) {
                    MessageObject message = (MessageObject) this.messages.get(a);
                    if (message.messageOwner.date < date || message.getId() == 0) {
                        a--;
                    } else {
                        scrollToMessageId(message.getId(), 0, false, message.getDialogId() == this.mergeDialogId ? 1 : 0, false);
                        return;
                    }
                }
            } else if (((int) this.dialog_id) != 0) {
                clearChatData();
                this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                MessagesController instance = MessagesController.getInstance();
                long j = this.dialog_id;
                int i = this.classGuid;
                boolean isChannel = ChatObject.isChannel(this.currentChat);
                int i2 = this.lastLoadIndex;
                this.lastLoadIndex = i2 + 1;
                instance.loadMessages(j, 30, 0, date, true, 0, i, 4, 0, isChannel, i2);
                this.floatingDateView.setAlpha(0.0f);
                this.floatingDateView.setTag(null);
            }
        }
    }

    public void processInlineBotContextPM(TLRPC$TL_inlineBotSwitchPM object) {
        if (object != null) {
            User user = this.mentionsAdapter.getContextBotUser();
            if (user != null) {
                this.chatActivityEnterView.setFieldText("");
                if (this.dialog_id == ((long) user.id)) {
                    this.inlineReturn = this.dialog_id;
                    MessagesController.getInstance().sendBotStart(this.currentUser, object.start_param);
                    return;
                }
                Bundle args = new Bundle();
                args.putInt("user_id", user.id);
                args.putString("inline_query", object.start_param);
                args.putLong("inline_return", this.dialog_id);
                if (MessagesController.checkCanOpenChat(args, this)) {
                    presentFragment(new ChatActivity(args));
                }
            }
        }
    }

    private void createChatAttachView() {
        if (getParentActivity() != null && this.chatAttachAlert == null) {
            this.chatAttachAlert = new ChatAttachAlert(getParentActivity(), this);
            this.chatAttachAlert.setDelegate(new ChatAttachViewDelegate() {
                public void didPressedButton(int button) {
                    if (ChatActivity.this.getParentActivity() != null && ChatActivity.this.chatAttachAlert != null) {
                        if (button == 7 || (button == 4 && !ChatActivity.this.chatAttachAlert.getSelectedPhotos().isEmpty())) {
                            ChatActivity.this.chatAttachAlert.dismiss();
                            HashMap<Object, Object> selectedPhotos = ChatActivity.this.chatAttachAlert.getSelectedPhotos();
                            ArrayList<Object> selectedPhotosOrder = ChatActivity.this.chatAttachAlert.getSelectedPhotosOrder();
                            if (!selectedPhotos.isEmpty()) {
                                ArrayList<SendMessagesHelper$SendingMediaInfo> photos = new ArrayList();
                                for (int a = 0; a < selectedPhotosOrder.size(); a++) {
                                    MediaController$PhotoEntry photoEntry = (MediaController$PhotoEntry) selectedPhotos.get(selectedPhotosOrder.get(a));
                                    SendMessagesHelper$SendingMediaInfo info = new SendMessagesHelper$SendingMediaInfo();
                                    if (photoEntry.imagePath != null) {
                                        info.path = photoEntry.imagePath;
                                    } else if (photoEntry.path != null) {
                                        info.path = photoEntry.path;
                                    }
                                    info.isVideo = photoEntry.isVideo;
                                    info.caption = photoEntry.caption != null ? photoEntry.caption.toString() : null;
                                    info.masks = !photoEntry.stickers.isEmpty() ? new ArrayList(photoEntry.stickers) : null;
                                    info.ttl = photoEntry.ttl;
                                    info.videoEditedInfo = photoEntry.editedInfo;
                                    photos.add(info);
                                    photoEntry.reset();
                                }
                                SendMessagesHelper.prepareSendingMedia(photos, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject, null, button == 4, MediaController.getInstance().isGroupPhotosEnabled());
                                ChatActivity.this.showReplyPanel(false, null, null, null, false);
                                DraftQuery.cleanDraft(ChatActivity.this.dialog_id, true);
                                return;
                            }
                            return;
                        }
                        if (ChatActivity.this.chatAttachAlert != null) {
                            ChatActivity.this.chatAttachAlert.dismissWithButtonClick(button);
                        }
                        ChatActivity.this.processSelectedAttach(button);
                    }
                }

                public View getRevealView() {
                    return ChatActivity.this.chatActivityEnterView.getAttachButton();
                }

                public void didSelectBot(User user) {
                    if (ChatActivity.this.chatActivityEnterView != null && !TextUtils.isEmpty(user.username)) {
                        ChatActivity.this.chatActivityEnterView.setFieldText("@" + user.username + " ");
                        ChatActivity.this.chatActivityEnterView.openKeyboard();
                    }
                }

                public void onCameraOpened() {
                    ChatActivity.this.chatActivityEnterView.closeKeyboard();
                }
            });
        }
    }

    public long getDialogId() {
        return this.dialog_id;
    }

    public void setBotUser(String value) {
        if (this.inlineReturn != 0) {
            MessagesController.getInstance().sendBotStart(this.currentUser, value);
            return;
        }
        this.botUser = value;
        updateBottomOverlay();
    }

    public boolean playFirstUnreadVoiceMessage() {
        if (this.chatActivityEnterView != null && this.chatActivityEnterView.isRecordingAudioVideo()) {
            return true;
        }
        for (int a = this.messages.size() - 1; a >= 0; a--) {
            MessageObject messageObject = (MessageObject) this.messages.get(a);
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

    private void initStickers() {
        if (this.chatActivityEnterView != null && getParentActivity() != null && this.stickersAdapter == null) {
            if (this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 23) {
                if (this.stickersAdapter != null) {
                    this.stickersAdapter.onDestroy();
                }
                this.stickersListView.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
                RecyclerListView recyclerListView = this.stickersListView;
                Adapter stickersAdapter = new StickersAdapter(getParentActivity(), new StickersAdapterDelegate() {
                    public void needChangePanelVisibility(final boolean show) {
                        float f = 1.0f;
                        if (!show || ChatActivity.this.stickersPanel.getVisibility() != 0) {
                            if (show || ChatActivity.this.stickersPanel.getVisibility() != 8) {
                                if (show) {
                                    ChatActivity.this.stickersListView.scrollToPosition(0);
                                    ChatActivity.this.stickersPanel.setVisibility(ChatActivity.this.allowStickersPanel ? 0 : 4);
                                }
                                if (ChatActivity.this.runningAnimation != null) {
                                    ChatActivity.this.runningAnimation.cancel();
                                    ChatActivity.this.runningAnimation = null;
                                }
                                if (ChatActivity.this.stickersPanel.getVisibility() != 4) {
                                    float f2;
                                    ChatActivity.this.runningAnimation = new AnimatorSet();
                                    AnimatorSet access$14700 = ChatActivity.this.runningAnimation;
                                    Animator[] animatorArr = new Animator[1];
                                    FrameLayout access$12100 = ChatActivity.this.stickersPanel;
                                    String str = "alpha";
                                    float[] fArr = new float[2];
                                    if (show) {
                                        f2 = 0.0f;
                                    } else {
                                        f2 = 1.0f;
                                    }
                                    fArr[0] = f2;
                                    if (!show) {
                                        f = 0.0f;
                                    }
                                    fArr[1] = f;
                                    animatorArr[0] = ObjectAnimator.ofFloat(access$12100, str, fArr);
                                    access$14700.playTogether(animatorArr);
                                    ChatActivity.this.runningAnimation.setDuration(150);
                                    ChatActivity.this.runningAnimation.addListener(new AnimatorListenerAdapter() {
                                        public void onAnimationEnd(Animator animation) {
                                            if (ChatActivity.this.runningAnimation != null && ChatActivity.this.runningAnimation.equals(animation)) {
                                                if (!show) {
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

                                        public void onAnimationCancel(Animator animation) {
                                            if (ChatActivity.this.runningAnimation != null && ChatActivity.this.runningAnimation.equals(animation)) {
                                                ChatActivity.this.runningAnimation = null;
                                            }
                                        }
                                    });
                                    ChatActivity.this.runningAnimation.start();
                                } else if (!show) {
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
                    public void onItemClick(View view, int position) {
                        TLRPC$Document document = ChatActivity.this.stickersAdapter.getItem(position);
                        if (document instanceof TLRPC$TL_document) {
                            SendMessagesHelper.getInstance().sendSticker(document, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject, ChatActivity.this.getParentActivity());
                            ChatActivity.this.showReplyPanel(false, null, null, null, false);
                            ChatActivity.this.chatActivityEnterView.addStickerToRecent(document);
                        }
                        ChatActivity.this.chatActivityEnterView.setFieldText("");
                    }
                };
                this.stickersOnItemClickListener = anonymousClass69;
                recyclerListView.setOnItemClickListener(anonymousClass69);
            }
        }
    }

    public void shareMyContact(final MessageObject messageObject) {
        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString("ShareYouPhoneNumberTitle", R.string.ShareYouPhoneNumberTitle));
        if (this.currentUser == null) {
            builder.setMessage(LocaleController.getString("AreYouSureShareMyContactInfo", R.string.AreYouSureShareMyContactInfo));
        } else if (this.currentUser.bot) {
            builder.setMessage(LocaleController.getString("AreYouSureShareMyContactInfoBot", R.string.AreYouSureShareMyContactInfoBot));
        } else {
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("AreYouSureShareMyContactInfoUser", R.string.AreYouSureShareMyContactInfoUser, new Object[]{PhoneFormat.getInstance().format("+" + UserConfig.getCurrentUser().phone), ContactsController.formatName(this.currentUser.first_name, this.currentUser.last_name)})));
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

    private void hideVoiceHint() {
        this.voiceHintAnimation = new AnimatorSet();
        AnimatorSet animatorSet = this.voiceHintAnimation;
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(this.voiceHintTextView, "alpha", new float[]{0.0f});
        animatorSet.playTogether(animatorArr);
        this.voiceHintAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (animation.equals(ChatActivity.this.voiceHintAnimation)) {
                    ChatActivity.this.voiceHintAnimation = null;
                    ChatActivity.this.voiceHintHideRunnable = null;
                    if (ChatActivity.this.voiceHintTextView != null) {
                        ChatActivity.this.voiceHintTextView.setVisibility(8);
                    }
                }
            }

            public void onAnimationCancel(Animator animation) {
                if (animation.equals(ChatActivity.this.voiceHintAnimation)) {
                    ChatActivity.this.voiceHintHideRunnable = null;
                    ChatActivity.this.voiceHintHideRunnable = null;
                }
            }
        });
        this.voiceHintAnimation.setDuration(300);
        this.voiceHintAnimation.start();
    }

    private void showVoiceHint(boolean hide, boolean video) {
        if (getParentActivity() != null && this.fragmentView != null) {
            if (!hide || this.voiceHintTextView != null) {
                if (this.voiceHintTextView == null) {
                    SizeNotifierFrameLayout frameLayout = this.fragmentView;
                    int index = frameLayout.indexOfChild(this.chatActivityEnterView);
                    if (index != -1) {
                        this.voiceHintTextView = new TextView(getParentActivity());
                        this.voiceHintTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
                        this.voiceHintTextView.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
                        this.voiceHintTextView.setTextSize(1, 14.0f);
                        this.voiceHintTextView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f));
                        this.voiceHintTextView.setGravity(16);
                        this.voiceHintTextView.setAlpha(0.0f);
                        frameLayout.addView(this.voiceHintTextView, index + 1, LayoutHelper.createFrame(-2, -2.0f, 85, 5.0f, 0.0f, 5.0f, 3.0f));
                    } else {
                        return;
                    }
                }
                if (hide) {
                    if (this.voiceHintAnimation != null) {
                        this.voiceHintAnimation.cancel();
                        this.voiceHintAnimation = null;
                    }
                    AndroidUtilities.cancelRunOnUIThread(this.voiceHintHideRunnable);
                    this.voiceHintHideRunnable = null;
                    hideVoiceHint();
                    return;
                }
                this.voiceHintTextView.setText(video ? LocaleController.getString("HoldToVideo", R.string.HoldToVideo) : LocaleController.getString("HoldToAudio", R.string.HoldToAudio));
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
                        AndroidUtilities.runOnUIThread(anonymousClass72, FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
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
                    class C24211 implements Runnable {
                        C24211() {
                        }

                        public void run() {
                            ChatActivity.this.hideVoiceHint();
                        }
                    }

                    public void onAnimationEnd(Animator animation) {
                        if (animation.equals(ChatActivity.this.voiceHintAnimation)) {
                            ChatActivity.this.voiceHintAnimation = null;
                            AndroidUtilities.runOnUIThread(ChatActivity.this.voiceHintHideRunnable = new C24211(), FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (animation.equals(ChatActivity.this.voiceHintAnimation)) {
                            ChatActivity.this.voiceHintAnimation = null;
                        }
                    }
                });
                this.voiceHintAnimation.setDuration(300);
                this.voiceHintAnimation.start();
            }
        }
    }

    private void showMediaBannedHint() {
        if (getParentActivity() != null && this.currentChat != null && this.currentChat.banned_rights != null && this.fragmentView != null) {
            if (this.mediaBanTooltip == null || this.mediaBanTooltip.getVisibility() != 0) {
                SizeNotifierFrameLayout frameLayout = this.fragmentView;
                int index = frameLayout.indexOfChild(this.chatActivityEnterView);
                if (index != -1) {
                    if (this.mediaBanTooltip == null) {
                        this.mediaBanTooltip = new CorrectlyMeasuringTextView(getParentActivity());
                        this.mediaBanTooltip.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
                        this.mediaBanTooltip.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
                        this.mediaBanTooltip.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f));
                        this.mediaBanTooltip.setGravity(16);
                        this.mediaBanTooltip.setTextSize(1, 14.0f);
                        frameLayout.addView(this.mediaBanTooltip, index + 1, LayoutHelper.createFrame(-2, -2.0f, 85, 30.0f, 0.0f, 5.0f, 3.0f));
                    }
                    if (AndroidUtilities.isBannedForever(this.currentChat.banned_rights.until_date)) {
                        this.mediaBanTooltip.setText(LocaleController.getString("AttachMediaRestrictedForever", R.string.AttachMediaRestrictedForever));
                    } else {
                        this.mediaBanTooltip.setText(LocaleController.formatString("AttachMediaRestricted", R.string.AttachMediaRestricted, new Object[]{LocaleController.formatDateForBan((long) this.currentChat.banned_rights.until_date)}));
                    }
                    this.mediaBanTooltip.setVisibility(0);
                    AnimatorSet AnimatorSet = new AnimatorSet();
                    AnimatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.mediaBanTooltip, "alpha", new float[]{0.0f, 1.0f})});
                    AnimatorSet.addListener(new AnimatorListenerAdapter() {

                        /* renamed from: org.telegram.ui.ChatActivity$74$1 */
                        class C24231 implements Runnable {

                            /* renamed from: org.telegram.ui.ChatActivity$74$1$1 */
                            class C24221 extends AnimatorListenerAdapter {
                                C24221() {
                                }

                                public void onAnimationEnd(Animator animation) {
                                    if (ChatActivity.this.mediaBanTooltip != null) {
                                        ChatActivity.this.mediaBanTooltip.setVisibility(8);
                                    }
                                }
                            }

                            C24231() {
                            }

                            public void run() {
                                if (ChatActivity.this.mediaBanTooltip != null) {
                                    AnimatorSet AnimatorSet = new AnimatorSet();
                                    Animator[] animatorArr = new Animator[1];
                                    animatorArr[0] = ObjectAnimator.ofFloat(ChatActivity.this.mediaBanTooltip, "alpha", new float[]{0.0f});
                                    AnimatorSet.playTogether(animatorArr);
                                    AnimatorSet.addListener(new C24221());
                                    AnimatorSet.setDuration(300);
                                    AnimatorSet.start();
                                }
                            }
                        }

                        public void onAnimationEnd(Animator animation) {
                            AndroidUtilities.runOnUIThread(new C24231(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                        }
                    });
                    AnimatorSet.setDuration(300);
                    AnimatorSet.start();
                }
            }
        }
    }

    private void showGifHint() {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        if (!preferences.getBoolean("gifhint", false)) {
            preferences.edit().putBoolean("gifhint", true).commit();
            if (getParentActivity() != null && this.fragmentView != null && this.gifHintTextView == null) {
                if (this.allowContextBotPanelSecond) {
                    SizeNotifierFrameLayout frameLayout = this.fragmentView;
                    int index = frameLayout.indexOfChild(this.chatActivityEnterView);
                    if (index != -1) {
                        this.chatActivityEnterView.setOpenGifsTabFirst();
                        this.emojiButtonRed = new View(getParentActivity());
                        this.emojiButtonRed.setBackgroundResource(R.drawable.redcircle);
                        frameLayout.addView(this.emojiButtonRed, index + 1, LayoutHelper.createFrame(10, 10.0f, 83, 30.0f, 0.0f, 0.0f, 27.0f));
                        this.gifHintTextView = new TextView(getParentActivity());
                        this.gifHintTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
                        this.gifHintTextView.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
                        this.gifHintTextView.setTextSize(1, 14.0f);
                        this.gifHintTextView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f));
                        this.gifHintTextView.setText(LocaleController.getString("TapHereGifs", R.string.TapHereGifs));
                        this.gifHintTextView.setGravity(16);
                        frameLayout.addView(this.gifHintTextView, index + 1, LayoutHelper.createFrame(-2, -2.0f, 83, 5.0f, 0.0f, 5.0f, 3.0f));
                        AnimatorSet AnimatorSet = new AnimatorSet();
                        AnimatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.gifHintTextView, "alpha", new float[]{0.0f, 1.0f}), ObjectAnimator.ofFloat(this.emojiButtonRed, "alpha", new float[]{0.0f, 1.0f})});
                        AnimatorSet.addListener(new AnimatorListenerAdapter() {

                            /* renamed from: org.telegram.ui.ChatActivity$75$1 */
                            class C24251 implements Runnable {

                                /* renamed from: org.telegram.ui.ChatActivity$75$1$1 */
                                class C24241 extends AnimatorListenerAdapter {
                                    C24241() {
                                    }

                                    public void onAnimationEnd(Animator animation) {
                                        if (ChatActivity.this.gifHintTextView != null) {
                                            ChatActivity.this.gifHintTextView.setVisibility(8);
                                        }
                                    }
                                }

                                C24251() {
                                }

                                public void run() {
                                    if (ChatActivity.this.gifHintTextView != null) {
                                        AnimatorSet AnimatorSet = new AnimatorSet();
                                        Animator[] animatorArr = new Animator[1];
                                        animatorArr[0] = ObjectAnimator.ofFloat(ChatActivity.this.gifHintTextView, "alpha", new float[]{0.0f});
                                        AnimatorSet.playTogether(animatorArr);
                                        AnimatorSet.addListener(new C24241());
                                        AnimatorSet.setDuration(300);
                                        AnimatorSet.start();
                                    }
                                }
                            }

                            public void onAnimationEnd(Animator animation) {
                                AndroidUtilities.runOnUIThread(new C24251(), FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
                            }
                        });
                        AnimatorSet.setDuration(300);
                        AnimatorSet.start();
                    }
                } else if (this.chatActivityEnterView != null) {
                    this.chatActivityEnterView.setOpenGifsTabFirst();
                }
            }
        }
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
                this.mentionListAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.mentionContainer, "alpha", new float[]{0.0f, 1.0f})});
                this.mentionListAnimation.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animation)) {
                            ChatActivity.this.mentionListAnimation = null;
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animation)) {
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
            animatorArr[0] = ObjectAnimator.ofFloat(this.mentionContainer, "alpha", new float[]{0.0f});
            animatorSet.playTogether(animatorArr);
            this.mentionListAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animation)) {
                        ChatActivity.this.mentionContainer.setVisibility(4);
                        ChatActivity.this.mentionListAnimation = null;
                    }
                }

                public void onAnimationCancel(Animator animation) {
                    if (ChatActivity.this.mentionListAnimation != null && ChatActivity.this.mentionListAnimation.equals(animation)) {
                        ChatActivity.this.mentionListAnimation = null;
                    }
                }
            });
            this.mentionListAnimation.setDuration(200);
            this.mentionListAnimation.start();
        }
    }

    private void hideFloatingDateView(boolean animated) {
        if (this.floatingDateView.getTag() != null && !this.currentFloatingDateOnScreen) {
            if (!this.scrollingFloatingDate || this.currentFloatingTopIsNotMessage) {
                this.floatingDateView.setTag(null);
                if (animated) {
                    this.floatingDateAnimation = new AnimatorSet();
                    this.floatingDateAnimation.setDuration(150);
                    AnimatorSet animatorSet = this.floatingDateAnimation;
                    Animator[] animatorArr = new Animator[1];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.floatingDateView, "alpha", new float[]{0.0f});
                    animatorSet.playTogether(animatorArr);
                    this.floatingDateAnimation.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            if (animation.equals(ChatActivity.this.floatingDateAnimation)) {
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
                this.floatingDateView.setAlpha(0.0f);
            }
        }
    }

    protected void onRemoveFromParent() {
        MediaController.getInstance().setTextureView(this.videoTextureView, null, null, false);
    }

    protected void setIgnoreAttachOnPause(boolean value) {
        this.ignoreAttachOnPause = value;
    }

    private void checkScrollForLoad(boolean scroll) {
        if (this.chatLayoutManager != null && !this.paused && !this.isAdvancedForward) {
            int firstVisibleItem = this.chatLayoutManager.findFirstVisibleItemPosition();
            int visibleItemCount = firstVisibleItem == -1 ? 0 : Math.abs(this.chatLayoutManager.findLastVisibleItemPosition() - firstVisibleItem) + 1;
            if (visibleItemCount > 0 || this.currentEncryptedChat != null) {
                int checkLoadCount;
                MessagesController instance;
                long j;
                int i;
                int i2;
                int i3;
                boolean isChannel;
                int i4;
                int totalItemCount = this.chatAdapter.getItemCount();
                if (scroll) {
                    checkLoadCount = 25;
                } else {
                    checkLoadCount = 5;
                }
                if ((totalItemCount - firstVisibleItem) - visibleItemCount <= checkLoadCount && !this.loading) {
                    boolean z;
                    if (!this.endReached[0]) {
                        this.loading = true;
                        this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                        if (this.messagesByDays.size() != 0) {
                            instance = MessagesController.getInstance();
                            j = this.dialog_id;
                            i = this.maxMessageId[0];
                            z = !this.cacheEndReached[0];
                            i2 = this.minDate[0];
                            i3 = this.classGuid;
                            isChannel = ChatObject.isChannel(this.currentChat);
                            i4 = this.lastLoadIndex;
                            this.lastLoadIndex = i4 + 1;
                            instance.loadMessages(j, 50, i, 0, z, i2, i3, 0, 0, isChannel, i4);
                        } else {
                            instance = MessagesController.getInstance();
                            j = this.dialog_id;
                            z = !this.cacheEndReached[0];
                            i2 = this.minDate[0];
                            i3 = this.classGuid;
                            isChannel = ChatObject.isChannel(this.currentChat);
                            i4 = this.lastLoadIndex;
                            this.lastLoadIndex = i4 + 1;
                            instance.loadMessages(j, 50, 0, 0, z, i2, i3, 0, 0, isChannel, i4);
                        }
                    } else if (!(this.mergeDialogId == 0 || this.endReached[1])) {
                        this.loading = true;
                        this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                        instance = MessagesController.getInstance();
                        j = this.mergeDialogId;
                        i = this.maxMessageId[1];
                        z = !this.cacheEndReached[1];
                        i2 = this.minDate[1];
                        i3 = this.classGuid;
                        isChannel = ChatObject.isChannel(this.currentChat);
                        i4 = this.lastLoadIndex;
                        this.lastLoadIndex = i4 + 1;
                        instance.loadMessages(j, 50, i, 0, z, i2, i3, 0, 0, isChannel, i4);
                    }
                }
                if (visibleItemCount > 0 && !this.loadingForward && firstVisibleItem <= 10) {
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

    private void processSelectedAttach(int which) {
        if (which == 0 || which == 1 || which == 4 || which == 2) {
            String action;
            if (this.currentChat != null) {
                if (this.currentChat.participants_count > MessagesController.getInstance().groupBigSize) {
                    if (which == 0 || which == 1) {
                        action = "bigchat_upload_photo";
                    } else {
                        action = "bigchat_upload_document";
                    }
                } else if (which == 0 || which == 1) {
                    action = "chat_upload_photo";
                } else {
                    action = "chat_upload_document";
                }
            } else if (which == 0 || which == 1) {
                action = "pm_upload_photo";
            } else {
                action = "pm_upload_document";
            }
            if (!MessagesController.isFeatureEnabled(action, this)) {
                return;
            }
        }
        if (which == 0) {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.CAMERA") == 0) {
                try {
                    Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    File image = AndroidUtilities.generatePicturePath();
                    if (image != null) {
                        if (VERSION.SDK_INT >= 24) {
                            takePictureIntent.putExtra("output", FileProvider.getUriForFile(getParentActivity(), "org.ir.talaeii.provider", image));
                            takePictureIntent.addFlags(2);
                            takePictureIntent.addFlags(1);
                        } else {
                            takePictureIntent.putExtra("output", Uri.fromFile(image));
                        }
                        this.currentPicturePath = image.getAbsolutePath();
                    }
                    startActivityForResult(takePictureIntent, 0);
                    return;
                } catch (Exception e) {
                    FileLog.e(e);
                    return;
                }
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.CAMERA"}, 19);
        } else if (which == 1) {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
                boolean z = this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 46;
                PhotoAlbumPickerActivity fragment = new PhotoAlbumPickerActivity(false, z, true, this);
                fragment.setDelegate(new PhotoAlbumPickerActivityDelegate() {
                    public void didSelectPhotos(ArrayList<SendMessagesHelper$SendingMediaInfo> photos) {
                        SendMessagesHelper.prepareSendingMedia(photos, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject, null, false, MediaController.getInstance().isGroupPhotosEnabled());
                        ChatActivity.this.showReplyPanel(false, null, null, null, false);
                        DraftQuery.cleanDraft(ChatActivity.this.dialog_id, true);
                    }

                    public void startPhotoSelectActivity() {
                        try {
                            Intent videoPickerIntent = new Intent();
                            videoPickerIntent.setType("video/*");
                            videoPickerIntent.setAction("android.intent.action.GET_CONTENT");
                            videoPickerIntent.putExtra("android.intent.extra.sizeLimit", 1610612736);
                            Intent photoPickerIntent = new Intent("android.intent.action.PICK");
                            photoPickerIntent.setType("image/*");
                            Intent chooserIntent = Intent.createChooser(photoPickerIntent, null);
                            chooserIntent.putExtra("android.intent.extra.INITIAL_INTENTS", new Intent[]{videoPickerIntent});
                            ChatActivity.this.startActivityForResult(chooserIntent, 1);
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                });
                presentFragment(fragment);
                return;
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 4);
        } else if (which == 2) {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.CAMERA") == 0) {
                try {
                    Intent takeVideoIntent = new Intent("android.media.action.VIDEO_CAPTURE");
                    File video = AndroidUtilities.generateVideoPath();
                    if (video != null) {
                        if (VERSION.SDK_INT >= 24) {
                            takeVideoIntent.putExtra("output", FileProvider.getUriForFile(getParentActivity(), "org.ir.talaeii.provider", video));
                            takeVideoIntent.addFlags(2);
                            takeVideoIntent.addFlags(1);
                        } else if (VERSION.SDK_INT >= 18) {
                            takeVideoIntent.putExtra("output", Uri.fromFile(video));
                        }
                        takeVideoIntent.putExtra("android.intent.extra.sizeLimit", 1610612736);
                        this.currentPicturePath = video.getAbsolutePath();
                    }
                    startActivityForResult(takeVideoIntent, 2);
                    return;
                } catch (Exception e2) {
                    FileLog.e(e2);
                    return;
                }
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.CAMERA"}, 20);
        } else if (which == 6) {
            if (AndroidUtilities.isGoogleMapsInstalled(this)) {
                LocationActivity fragment2 = new LocationActivity(this.currentEncryptedChat == null ? 1 : 0);
                fragment2.setDialogId(this.dialog_id);
                fragment2.setDelegate(this);
                presentFragment(fragment2);
            }
        } else if (which == 4) {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
                DocumentSelectActivity fragment3 = new DocumentSelectActivity();
                fragment3.setDelegate(new DocumentSelectActivityDelegate() {
                    public void didSelectFiles(DocumentSelectActivity activity, ArrayList<String> files) {
                        activity.finishFragment();
                        SendMessagesHelper.prepareSendingDocuments(files, files, null, null, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject, null);
                        ChatActivity.this.showReplyPanel(false, null, null, null, false);
                        DraftQuery.cleanDraft(ChatActivity.this.dialog_id, true);
                    }

                    public void startDocumentSelectActivity() {
                        try {
                            Intent photoPickerIntent = new Intent("android.intent.action.GET_CONTENT");
                            if (VERSION.SDK_INT >= 18) {
                                photoPickerIntent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
                            }
                            photoPickerIntent.setType("*/*");
                            ChatActivity.this.startActivityForResult(photoPickerIntent, 21);
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                });
                presentFragment(fragment3);
                return;
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 4);
        } else if (which == 3) {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
                AudioSelectActivity fragment4 = new AudioSelectActivity();
                fragment4.setDelegate(new AudioSelectActivityDelegate() {
                    public void didSelectAudio(ArrayList<MessageObject> audios) {
                        SendMessagesHelper.prepareSendingAudioDocuments(audios, ChatActivity.this.dialog_id, ChatActivity.this.replyingMessageObject);
                        ChatActivity.this.showReplyPanel(false, null, null, null, false);
                        DraftQuery.cleanDraft(ChatActivity.this.dialog_id, true);
                    }
                });
                presentFragment(fragment4);
                return;
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 4);
        } else if (which != 5) {
        } else {
            if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.READ_CONTACTS") == 0) {
                try {
                    Intent intent = new Intent("android.intent.action.PICK", Contacts.CONTENT_URI);
                    intent.setType("vnd.android.cursor.dir/phone_v2");
                    startActivityForResult(intent, 31);
                    return;
                } catch (Exception e22) {
                    FileLog.e(e22);
                    return;
                }
            }
            getParentActivity().requestPermissions(new String[]{"android.permission.READ_CONTACTS"}, 5);
        }
    }

    public boolean dismissDialogOnPause(Dialog dialog) {
        return dialog != this.chatAttachAlert && super.dismissDialogOnPause(dialog);
    }

    private void searchLinks(final CharSequence charSequence, final boolean force) {
        if (this.currentEncryptedChat == null || (MessagesController.getInstance().secretWebpagePreview != 0 && AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 46)) {
            if (force && this.foundWebPage != null) {
                if (this.foundWebPage.url != null) {
                    int index = TextUtils.indexOf(charSequence, this.foundWebPage.url);
                    char lastChar = '\u0000';
                    boolean lenEqual = false;
                    if (index != -1) {
                        if (this.foundWebPage.url.length() + index == charSequence.length()) {
                            lenEqual = true;
                        } else {
                            lenEqual = false;
                        }
                        if (lenEqual) {
                            lastChar = '\u0000';
                        } else {
                            lastChar = charSequence.charAt(this.foundWebPage.url.length() + index);
                        }
                    } else if (this.foundWebPage.display_url != null) {
                        index = TextUtils.indexOf(charSequence, this.foundWebPage.display_url);
                        if (index == -1 || this.foundWebPage.display_url.length() + index != charSequence.length()) {
                            lenEqual = false;
                        } else {
                            lenEqual = true;
                        }
                        if (index == -1 || lenEqual) {
                            lastChar = '\u0000';
                        } else {
                            lastChar = charSequence.charAt(this.foundWebPage.display_url.length() + index);
                        }
                    }
                    if (index != -1 && (lenEqual || lastChar == ' ' || lastChar == ',' || lastChar == ClassUtils.PACKAGE_SEPARATOR_CHAR || lastChar == '!' || lastChar == '/')) {
                        return;
                    }
                }
                this.pendingLinkSearchString = null;
                showReplyPanel(false, null, null, this.foundWebPage, false);
            }
            Utilities.searchQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.ui.ChatActivity$82$1 */
                class C24271 implements Runnable {
                    C24271() {
                    }

                    public void run() {
                        if (ChatActivity.this.foundWebPage != null) {
                            ChatActivity.this.showReplyPanel(false, null, null, ChatActivity.this.foundWebPage, false);
                            ChatActivity.this.foundWebPage = null;
                        }
                    }
                }

                /* renamed from: org.telegram.ui.ChatActivity$82$2 */
                class C24282 implements Runnable {
                    C24282() {
                    }

                    public void run() {
                        if (ChatActivity.this.foundWebPage != null) {
                            ChatActivity.this.showReplyPanel(false, null, null, ChatActivity.this.foundWebPage, false);
                            ChatActivity.this.foundWebPage = null;
                        }
                    }
                }

                /* renamed from: org.telegram.ui.ChatActivity$82$3 */
                class C24303 implements Runnable {

                    /* renamed from: org.telegram.ui.ChatActivity$82$3$1 */
                    class C24291 implements OnClickListener {
                        C24291() {
                        }

                        public void onClick(DialogInterface dialog, int which) {
                            MessagesController.getInstance().secretWebpagePreview = 1;
                            ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("secretWebpage2", MessagesController.getInstance().secretWebpagePreview).commit();
                            ChatActivity.this.foundUrls = null;
                            ChatActivity.this.searchLinks(charSequence, force);
                        }
                    }

                    C24303() {
                    }

                    public void run() {
                        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(ChatActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C24291());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        builder.setMessage(LocaleController.getString("SecretLinkPreviewAlert", R.string.SecretLinkPreviewAlert));
                        ChatActivity.this.showDialog(builder.create());
                        MessagesController.getInstance().secretWebpagePreview = 0;
                        ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("secretWebpage2", MessagesController.getInstance().secretWebpagePreview).commit();
                    }
                }

                public void run() {
                    Exception e;
                    CharSequence textToCheck;
                    final TLRPC$TL_messages_getWebPagePreview req;
                    if (ChatActivity.this.linkSearchRequestId != 0) {
                        ConnectionsManager.getInstance().cancelRequest(ChatActivity.this.linkSearchRequestId, true);
                        ChatActivity.this.linkSearchRequestId = 0;
                    }
                    try {
                        ArrayList<CharSequence> urls;
                        Matcher m = AndroidUtilities.WEB_URL.matcher(charSequence);
                        ArrayList<CharSequence> urls2 = null;
                        while (m.find()) {
                            try {
                                if (m.start() <= 0 || charSequence.charAt(m.start() - 1) != '@') {
                                    if (urls2 == null) {
                                        urls = new ArrayList();
                                    } else {
                                        urls = urls2;
                                    }
                                    urls.add(charSequence.subSequence(m.start(), m.end()));
                                    urls2 = urls;
                                }
                            } catch (Exception e2) {
                                e = e2;
                                urls = urls2;
                            }
                        }
                        if (urls2 != null) {
                            if (ChatActivity.this.foundUrls != null && urls2.size() == ChatActivity.this.foundUrls.size()) {
                                boolean clear = true;
                                for (int a = 0; a < urls2.size(); a++) {
                                    if (!TextUtils.equals((CharSequence) urls2.get(a), (CharSequence) ChatActivity.this.foundUrls.get(a))) {
                                        clear = false;
                                    }
                                }
                                if (clear) {
                                    urls = urls2;
                                    return;
                                }
                            }
                        }
                        ChatActivity.this.foundUrls = urls2;
                        if (urls2 == null) {
                            AndroidUtilities.runOnUIThread(new C24271());
                            urls = urls2;
                            return;
                        }
                        textToCheck = TextUtils.join(" ", urls2);
                        urls = urls2;
                        if (ChatActivity.this.currentEncryptedChat == null && MessagesController.getInstance().secretWebpagePreview == 2) {
                            AndroidUtilities.runOnUIThread(new C24303());
                            return;
                        }
                        req = new TLRPC$TL_messages_getWebPagePreview();
                        if (textToCheck instanceof String) {
                            req.message = textToCheck.toString();
                        } else {
                            req.message = (String) textToCheck;
                        }
                        ChatActivity.this.linkSearchRequestId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                            public void run(final TLObject response, final TLRPC$TL_error error) {
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        ChatActivity.this.linkSearchRequestId = 0;
                                        if (error != null) {
                                            return;
                                        }
                                        if (response instanceof TLRPC$TL_messageMediaWebPage) {
                                            ChatActivity.this.foundWebPage = ((TLRPC$TL_messageMediaWebPage) response).webpage;
                                            if ((ChatActivity.this.foundWebPage instanceof TLRPC$TL_webPage) || (ChatActivity.this.foundWebPage instanceof TLRPC$TL_webPagePending)) {
                                                if (ChatActivity.this.foundWebPage instanceof TLRPC$TL_webPagePending) {
                                                    ChatActivity.this.pendingLinkSearchString = req.message;
                                                }
                                                if (ChatActivity.this.currentEncryptedChat != null && (ChatActivity.this.foundWebPage instanceof TLRPC$TL_webPagePending)) {
                                                    ChatActivity.this.foundWebPage.url = req.message;
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
                    } catch (Exception e3) {
                        e = e3;
                    }
                    FileLog.e(e);
                    String text = charSequence.toString().toLowerCase();
                    if (charSequence.length() < 13 || !(text.contains("http://") || text.contains("https://"))) {
                        AndroidUtilities.runOnUIThread(new C24282());
                        return;
                    }
                    textToCheck = charSequence;
                    if (ChatActivity.this.currentEncryptedChat == null) {
                    }
                    req = new TLRPC$TL_messages_getWebPagePreview();
                    if (textToCheck instanceof String) {
                        req.message = textToCheck.toString();
                    } else {
                        req.message = (String) textToCheck;
                    }
                    ChatActivity.this.linkSearchRequestId = ConnectionsManager.getInstance().sendRequest(req, /* anonymous class already generated */);
                    ConnectionsManager.getInstance().bindRequestToGuid(ChatActivity.this.linkSearchRequestId, ChatActivity.this.classGuid);
                }
            });
        }
    }

    private void forwardMessages(ArrayList<MessageObject> arrayList, boolean fromMyName) {
        if (arrayList != null && !arrayList.isEmpty()) {
            if (fromMyName) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    SendMessagesHelper.getInstance().processForwardFromMyName((MessageObject) it.next(), this.dialog_id, false);
                }
                return;
            }
            AlertsCreator.showSendMediaAlert(SendMessagesHelper.getInstance().sendMessage(arrayList, this.dialog_id), this);
        }
    }

    public void showReplyPanel(boolean show, MessageObject messageObjectToReply, ArrayList<MessageObject> messageObjectsToForward, TLRPC$WebPage webPage, boolean cancel) {
        if (this.chatActivityEnterView != null) {
            if (show) {
                if (messageObjectToReply != null || messageObjectsToForward != null || webPage != null) {
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
                    boolean openKeyboard = false;
                    if (!(messageObjectToReply == null || messageObjectToReply.getDialogId() == this.dialog_id)) {
                        messageObjectsToForward = new ArrayList();
                        messageObjectsToForward.add(messageObjectToReply);
                        messageObjectToReply = null;
                        openKeyboard = true;
                    }
                    User user;
                    String name;
                    TLRPC$Chat chat;
                    String mess;
                    if (messageObjectToReply != null) {
                        this.forwardingMessages = null;
                        this.replyingMessageObject = messageObjectToReply;
                        this.chatActivityEnterView.setReplyingMessageObject(messageObjectToReply);
                        if (this.foundWebPage == null) {
                            if (messageObjectToReply.isFromUser()) {
                                user = MessagesController.getInstance().getUser(Integer.valueOf(messageObjectToReply.messageOwner.from_id));
                                if (user != null) {
                                    name = UserObject.getUserName(user);
                                } else {
                                    return;
                                }
                            }
                            chat = MessagesController.getInstance().getChat(Integer.valueOf(messageObjectToReply.messageOwner.to_id.channel_id));
                            if (chat != null) {
                                name = chat.title;
                            } else {
                                return;
                            }
                            this.replyIconImageView.setImageResource(R.drawable.msg_panel_reply);
                            this.replyNameTextView.setText(name);
                            if (messageObjectToReply.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                                this.replyObjectTextView.setText(Emoji.replaceEmoji(messageObjectToReply.messageOwner.media.game.title, this.replyObjectTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                            } else if (messageObjectToReply.messageText != null) {
                                mess = messageObjectToReply.messageText.toString();
                                if (mess.length() > 150) {
                                    mess = mess.substring(0, 150);
                                }
                                this.replyObjectTextView.setText(Emoji.replaceEmoji(mess.replace('\n', ' '), this.replyObjectTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                            }
                        } else {
                            return;
                        }
                    } else if (messageObjectsToForward == null) {
                        this.replyIconImageView.setImageResource(R.drawable.msg_panel_link);
                        if (webPage instanceof TLRPC$TL_webPagePending) {
                            this.replyNameTextView.setText(LocaleController.getString("GettingLinkInfo", R.string.GettingLinkInfo));
                            this.replyObjectTextView.setText(this.pendingLinkSearchString);
                        } else {
                            if (webPage.site_name != null) {
                                this.replyNameTextView.setText(webPage.site_name);
                            } else if (webPage.title != null) {
                                this.replyNameTextView.setText(webPage.title);
                            } else {
                                this.replyNameTextView.setText(LocaleController.getString("LinkPreview", R.string.LinkPreview));
                            }
                            if (webPage.title != null) {
                                this.replyObjectTextView.setText(webPage.title);
                            } else if (webPage.description != null) {
                                this.replyObjectTextView.setText(webPage.description);
                            } else if (webPage.author != null) {
                                this.replyObjectTextView.setText(webPage.author);
                            } else {
                                this.replyObjectTextView.setText(webPage.display_url);
                            }
                            this.chatActivityEnterView.setWebPage(webPage, true);
                        }
                    } else if (!messageObjectsToForward.isEmpty()) {
                        this.replyingMessageObject = null;
                        this.chatActivityEnterView.setReplyingMessageObject(null);
                        this.forwardingMessages = messageObjectsToForward;
                        if (this.foundWebPage == null) {
                            int a;
                            Integer uid;
                            this.chatActivityEnterView.setForceShowSendButton(true, false);
                            ArrayList<Integer> uids = new ArrayList();
                            this.replyIconImageView.setImageResource(R.drawable.msg_panel_forward);
                            MessageObject object = (MessageObject) messageObjectsToForward.get(0);
                            if (object.isFromUser()) {
                                uids.add(Integer.valueOf(object.messageOwner.from_id));
                            } else {
                                uids.add(Integer.valueOf(-object.messageOwner.to_id.channel_id));
                            }
                            int type = ((MessageObject) messageObjectsToForward.get(0)).type;
                            for (a = 1; a < messageObjectsToForward.size(); a++) {
                                object = (MessageObject) messageObjectsToForward.get(a);
                                if (object.isFromUser()) {
                                    uid = Integer.valueOf(object.messageOwner.from_id);
                                } else {
                                    uid = Integer.valueOf(-object.messageOwner.to_id.channel_id);
                                }
                                if (!uids.contains(uid)) {
                                    uids.add(uid);
                                }
                                if (((MessageObject) messageObjectsToForward.get(a)).type != type) {
                                    type = -1;
                                }
                            }
                            StringBuilder userNames = new StringBuilder();
                            for (a = 0; a < uids.size(); a++) {
                                uid = (Integer) uids.get(a);
                                chat = null;
                                user = null;
                                if (uid.intValue() > 0) {
                                    user = MessagesController.getInstance().getUser(uid);
                                } else {
                                    chat = MessagesController.getInstance().getChat(Integer.valueOf(-uid.intValue()));
                                }
                                if (user != null || chat != null) {
                                    if (uids.size() != 1) {
                                        if (uids.size() != 2 && userNames.length() != 0) {
                                            userNames.append(" ");
                                            userNames.append(LocaleController.formatPluralString("AndOther", uids.size() - 1));
                                            break;
                                        }
                                        if (userNames.length() > 0) {
                                            userNames.append(", ");
                                        }
                                        if (user == null) {
                                            userNames.append(chat.title);
                                        } else if (!TextUtils.isEmpty(user.first_name)) {
                                            userNames.append(user.first_name);
                                        } else if (TextUtils.isEmpty(user.last_name)) {
                                            userNames.append(" ");
                                        } else {
                                            userNames.append(user.last_name);
                                        }
                                    } else if (user != null) {
                                        userNames.append(UserObject.getUserName(user));
                                    } else {
                                        userNames.append(chat.title);
                                    }
                                }
                            }
                            this.replyNameTextView.setText(userNames);
                            if (type == -1 || type == 0 || type == 10 || type == 11) {
                                if (messageObjectsToForward.size() != 1 || ((MessageObject) messageObjectsToForward.get(0)).messageText == null) {
                                    this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedMessageCount", messageObjectsToForward.size()));
                                } else {
                                    MessageObject messageObject = (MessageObject) messageObjectsToForward.get(0);
                                    if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                                        this.replyObjectTextView.setText(Emoji.replaceEmoji(messageObject.messageOwner.media.game.title, this.replyObjectTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                                    } else {
                                        mess = messageObject.messageText.toString();
                                        if (mess.length() > 150) {
                                            mess = mess.substring(0, 150);
                                        }
                                        this.replyObjectTextView.setText(Emoji.replaceEmoji(mess.replace('\n', ' '), this.replyObjectTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                                    }
                                }
                            } else if (type == 1) {
                                this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedPhoto", messageObjectsToForward.size()));
                                if (messageObjectsToForward.size() == 1) {
                                    messageObjectToReply = (MessageObject) messageObjectsToForward.get(0);
                                }
                            } else if (type == 4) {
                                this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedLocation", messageObjectsToForward.size()));
                            } else if (type == 3) {
                                this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedVideo", messageObjectsToForward.size()));
                                if (messageObjectsToForward.size() == 1) {
                                    messageObjectToReply = (MessageObject) messageObjectsToForward.get(0);
                                }
                            } else if (type == 12) {
                                this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedContact", messageObjectsToForward.size()));
                            } else if (type == 2) {
                                this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedAudio", messageObjectsToForward.size()));
                            } else if (type == 5) {
                                this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedRound", messageObjectsToForward.size()));
                            } else if (type == 14) {
                                this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedMusic", messageObjectsToForward.size()));
                            } else if (type == 13) {
                                this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedSticker", messageObjectsToForward.size()));
                            } else if (type == 8 || type == 9) {
                                if (messageObjectsToForward.size() != 1) {
                                    this.replyObjectTextView.setText(LocaleController.formatPluralString("ForwardedFile", messageObjectsToForward.size()));
                                } else if (type == 8) {
                                    this.replyObjectTextView.setText(LocaleController.getString("AttachGif", R.string.AttachGif));
                                } else {
                                    name = FileLoader.getDocumentFileName(((MessageObject) messageObjectsToForward.get(0)).getDocument());
                                    if (name.length() != 0) {
                                        this.replyObjectTextView.setText(name);
                                    }
                                    messageObjectToReply = (MessageObject) messageObjectsToForward.get(0);
                                }
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                    FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) this.replyNameTextView.getLayoutParams();
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.replyObjectTextView.getLayoutParams();
                    TLRPC$PhotoSize photoSize = null;
                    if (messageObjectToReply != null) {
                        photoSize = FileLoader.getClosestPhotoSizeWithSize(messageObjectToReply.photoThumbs2, 80);
                        if (photoSize == null) {
                            photoSize = FileLoader.getClosestPhotoSizeWithSize(messageObjectToReply.photoThumbs, 80);
                        }
                    }
                    int dp;
                    if (photoSize == null || (photoSize instanceof TLRPC$TL_photoSizeEmpty) || (photoSize.location instanceof TLRPC$TL_fileLocationUnavailable) || messageObjectToReply.type == 13 || (messageObjectToReply != null && messageObjectToReply.isSecretMedia())) {
                        this.replyImageView.setImageBitmap(null);
                        this.replyImageLocation = null;
                        this.replyImageView.setVisibility(4);
                        dp = AndroidUtilities.dp(52.0f);
                        layoutParams2.leftMargin = dp;
                        layoutParams1.leftMargin = dp;
                    } else {
                        if (messageObjectToReply.isRoundVideo()) {
                            this.replyImageView.setRoundRadius(AndroidUtilities.dp(17.0f));
                        } else {
                            this.replyImageView.setRoundRadius(0);
                        }
                        this.replyImageLocation = photoSize.location;
                        this.replyImageView.setImage(this.replyImageLocation, "50_50", (Drawable) null);
                        this.replyImageView.setVisibility(0);
                        dp = AndroidUtilities.dp(96.0f);
                        layoutParams2.leftMargin = dp;
                        layoutParams1.leftMargin = dp;
                    }
                    this.replyNameTextView.setLayoutParams(layoutParams1);
                    this.replyObjectTextView.setLayoutParams(layoutParams2);
                    this.chatActivityEnterView.showTopView(false, openKeyboard);
                }
            } else if (this.replyingMessageObject != null || this.forwardingMessages != null || this.foundWebPage != null) {
                if (this.replyingMessageObject != null && (this.replyingMessageObject.messageOwner.reply_markup instanceof TLRPC$TL_replyKeyboardForceReply)) {
                    ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("answered_" + this.dialog_id, this.replyingMessageObject.getId()).commit();
                }
                if (this.foundWebPage != null) {
                    this.foundWebPage = null;
                    this.chatActivityEnterView.setWebPage(null, !cancel);
                    if (!(webPage == null || (this.replyingMessageObject == null && this.forwardingMessages == null))) {
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

    private void moveScrollToLastMessage() {
        if (this.chatListView != null && !this.messages.isEmpty()) {
            this.chatLayoutManager.scrollToPositionWithOffset(0, 0);
        }
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

    private void clearChatData() {
        this.messages.clear();
        this.messagesByDays.clear();
        this.waitingForLoad.clear();
        this.groupedMessagesMap.clear();
        this.progressView.setVisibility(this.chatAdapter.botInfoRow == -1 ? 0 : 4);
        this.chatListView.setEmptyView(null);
        for (int a = 0; a < 2; a++) {
            this.messagesDict[a].clear();
            if (this.currentEncryptedChat == null) {
                this.maxMessageId[a] = Integer.MAX_VALUE;
                this.minMessageId[a] = Integer.MIN_VALUE;
            } else {
                this.maxMessageId[a] = Integer.MIN_VALUE;
                this.minMessageId[a] = Integer.MAX_VALUE;
            }
            this.maxDate[a] = Integer.MIN_VALUE;
            this.minDate[a] = 0;
            this.endReached[a] = false;
            this.cacheEndReached[a] = false;
            this.forwardEndReached[a] = true;
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

    private void scrollToLastMessage(boolean pagedown) {
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
        } else if (pagedown && this.chatLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
            showPagedownButton(false, true);
            this.highlightMessageId = Integer.MAX_VALUE;
            updateVisibleRows();
        } else {
            this.chatLayoutManager.scrollToPositionWithOffset(0, 0);
        }
    }

    private void updateTextureViewPosition() {
        if (this.fragmentView != null) {
            MessageObject messageObject;
            boolean foundTextureViewMessage = false;
            int count = this.chatListView.getChildCount();
            int additionalTop = this.chatActivityEnterView.isTopViewVisible() ? AndroidUtilities.dp(48.0f) : 0;
            for (int a = 0; a < count; a++) {
                View view = this.chatListView.getChildAt(a);
                if (view instanceof ChatMessageCell) {
                    ChatMessageCell messageCell = (ChatMessageCell) view;
                    messageObject = messageCell.getMessageObject();
                    if (this.roundVideoContainer != null && messageObject.isRoundVideo() && MediaController.getInstance().isPlayingMessage(messageObject)) {
                        ImageReceiver imageReceiver = messageCell.getPhotoImage();
                        this.roundVideoContainer.setTranslationX((float) imageReceiver.getImageX());
                        this.roundVideoContainer.setTranslationY((float) (((this.fragmentView.getPaddingTop() + messageCell.getTop()) + imageReceiver.getImageY()) - additionalTop));
                        this.fragmentView.invalidate();
                        this.roundVideoContainer.invalidate();
                        foundTextureViewMessage = true;
                        break;
                    }
                }
            }
            if (this.roundVideoContainer != null) {
                messageObject = MediaController.getInstance().getPlayingMessageObject();
                if (messageObject.eventId != 0) {
                    return;
                }
                if (foundTextureViewMessage) {
                    MediaController.getInstance().setCurrentRoundVisible(true);
                    scrollToMessageId(messageObject.getId(), 0, false, 0, true);
                    return;
                }
                this.roundVideoContainer.setTranslationY((float) ((-AndroidUtilities.roundMessageSize) - 100));
                this.fragmentView.invalidate();
                if (messageObject != null && messageObject.isRoundVideo()) {
                    if (this.checkTextureViewPosition || PipRoundVideoView.getInstance() != null) {
                        MediaController.getInstance().setCurrentRoundVisible(false);
                    } else {
                        scrollToMessageId(messageObject.getId(), 0, false, 0, true);
                    }
                }
            }
        }
    }

    private void updateMessagesVisisblePart() {
        if (this.chatListView != null) {
            MessageObject messageObject;
            int count = this.chatListView.getChildCount();
            int additionalTop = this.chatActivityEnterView.isTopViewVisible() ? AndroidUtilities.dp(48.0f) : 0;
            int height = this.chatListView.getMeasuredHeight();
            int minPositionHolder = Integer.MAX_VALUE;
            int minPositionDateHolder = Integer.MAX_VALUE;
            View minDateChild = null;
            View minChild = null;
            View minMessageChild = null;
            boolean foundTextureViewMessage = false;
            for (int a = 0; a < count; a++) {
                View view = this.chatListView.getChildAt(a);
                if (view instanceof ChatMessageCell) {
                    int viewTop;
                    ChatMessageCell messageCell = (ChatMessageCell) view;
                    int top = messageCell.getTop();
                    int bottom = messageCell.getBottom();
                    if (top >= 0) {
                        viewTop = 0;
                    } else {
                        viewTop = -top;
                    }
                    int viewBottom = messageCell.getMeasuredHeight();
                    if (viewBottom > height) {
                        viewBottom = viewTop + height;
                    }
                    messageCell.setVisiblePart(viewTop, viewBottom - viewTop);
                    messageObject = messageCell.getMessageObject();
                    if (this.roundVideoContainer != null && messageObject.isRoundVideo() && MediaController.getInstance().isPlayingMessage(messageObject)) {
                        ImageReceiver imageReceiver = messageCell.getPhotoImage();
                        this.roundVideoContainer.setTranslationX((float) imageReceiver.getImageX());
                        this.roundVideoContainer.setTranslationY((float) (((this.fragmentView.getPaddingTop() + top) + imageReceiver.getImageY()) - additionalTop));
                        this.fragmentView.invalidate();
                        this.roundVideoContainer.invalidate();
                        foundTextureViewMessage = true;
                    }
                }
                if (view.getBottom() > this.chatListView.getPaddingTop()) {
                    int position = view.getBottom();
                    if (position < minPositionHolder) {
                        minPositionHolder = position;
                        if ((view instanceof ChatMessageCell) || (view instanceof ChatActionCell)) {
                            minMessageChild = view;
                        }
                        minChild = view;
                    }
                    if ((view instanceof ChatActionCell) && ((ChatActionCell) view).getMessageObject().isDateObject) {
                        if (view.getAlpha() != 1.0f) {
                            view.setAlpha(1.0f);
                        }
                        if (position < minPositionDateHolder) {
                            minPositionDateHolder = position;
                            minDateChild = view;
                        }
                    }
                }
            }
            if (this.roundVideoContainer != null) {
                if (foundTextureViewMessage) {
                    MediaController.getInstance().setCurrentRoundVisible(true);
                } else {
                    this.roundVideoContainer.setTranslationY((float) ((-AndroidUtilities.roundMessageSize) - 100));
                    this.fragmentView.invalidate();
                    messageObject = MediaController.getInstance().getPlayingMessageObject();
                    if (messageObject != null && messageObject.isRoundVideo() && messageObject.eventId == 0 && this.checkTextureViewPosition) {
                        MediaController.getInstance().setCurrentRoundVisible(false);
                    }
                }
            }
            if (minMessageChild != null) {
                if (minMessageChild instanceof ChatMessageCell) {
                    messageObject = ((ChatMessageCell) minMessageChild).getMessageObject();
                } else {
                    messageObject = ((ChatActionCell) minMessageChild).getMessageObject();
                }
                this.floatingDateView.setCustomDate(messageObject.messageOwner.date);
            }
            this.currentFloatingDateOnScreen = false;
            boolean z = ((minChild instanceof ChatMessageCell) || (minChild instanceof ChatActionCell)) ? false : true;
            this.currentFloatingTopIsNotMessage = z;
            if (minDateChild != null) {
                if (minDateChild.getTop() > this.chatListView.getPaddingTop() || this.currentFloatingTopIsNotMessage) {
                    if (minDateChild.getAlpha() != 1.0f) {
                        minDateChild.setAlpha(1.0f);
                    }
                    if (this.currentFloatingTopIsNotMessage) {
                        z = false;
                    } else {
                        z = true;
                    }
                    hideFloatingDateView(z);
                } else {
                    if (minDateChild.getAlpha() != 0.0f) {
                        minDateChild.setAlpha(0.0f);
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
                int offset = minDateChild.getBottom() - this.chatListView.getPaddingTop();
                if (offset <= this.floatingDateView.getMeasuredHeight() || offset >= this.floatingDateView.getMeasuredHeight() * 2) {
                    this.floatingDateView.setTranslationY(0.0f);
                    return;
                } else {
                    this.floatingDateView.setTranslationY((float) (((-this.floatingDateView.getMeasuredHeight()) * 2) + offset));
                    return;
                }
            }
            hideFloatingDateView(true);
            this.floatingDateView.setTranslationY(0.0f);
        }
    }

    private void toggleMute(boolean instant) {
        Editor editor;
        TLRPC$TL_dialog dialog;
        if (MessagesController.getInstance().isDialogMuted(this.dialog_id)) {
            editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            editor.putInt("notify2_" + this.dialog_id, 0);
            MessagesStorage.getInstance().setDialogFlags(this.dialog_id, 0);
            editor.commit();
            dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.dialog_id));
            if (dialog != null) {
                dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
            }
            NotificationsController.updateServerNotificationsSettings(this.dialog_id);
        } else if (instant) {
            editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            editor.putInt("notify2_" + this.dialog_id, 2);
            MessagesStorage.getInstance().setDialogFlags(this.dialog_id, 1);
            editor.commit();
            dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.dialog_id));
            if (dialog != null) {
                dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                dialog.notify_settings.mute_until = Integer.MAX_VALUE;
            }
            NotificationsController.updateServerNotificationsSettings(this.dialog_id);
            NotificationsController.getInstance().removeNotificationsForDialog(this.dialog_id);
        } else {
            showDialog(AlertsCreator.createMuteAlert(getParentActivity(), this.dialog_id));
        }
    }

    private int getScrollOffsetForMessage(MessageObject object) {
        int offset = Integer.MAX_VALUE;
        GroupedMessages groupedMessages = getValidGroupedMessage(object);
        if (groupedMessages != null) {
            float itemHeight;
            GroupedMessagePosition currentPosition = (GroupedMessagePosition) groupedMessages.positions.get(object);
            float maxH = ((float) Math.max(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) * 0.5f;
            if (currentPosition.siblingHeights != null) {
                itemHeight = currentPosition.siblingHeights[0];
            } else {
                itemHeight = currentPosition.ph;
            }
            float totalHeight = 0.0f;
            float moveDiff = 0.0f;
            SparseBooleanArray array = new SparseBooleanArray();
            for (int a = 0; a < groupedMessages.posArray.size(); a++) {
                GroupedMessagePosition pos = (GroupedMessagePosition) groupedMessages.posArray.get(a);
                if (array.indexOfKey(pos.minY) < 0 && pos.siblingHeights == null) {
                    array.put(pos.minY, true);
                    if (pos.minY < currentPosition.minY) {
                        moveDiff -= pos.ph;
                    } else if (pos.minY > currentPosition.minY) {
                        moveDiff += pos.ph;
                    }
                    totalHeight += pos.ph;
                }
            }
            if (Math.abs(totalHeight - itemHeight) < 0.02f) {
                offset = ((((int) (((float) this.chatListView.getMeasuredHeight()) - (totalHeight * maxH))) / 2) - this.chatListView.getPaddingTop()) - AndroidUtilities.dp(7.0f);
            } else {
                offset = ((((int) (((float) this.chatListView.getMeasuredHeight()) - ((itemHeight + moveDiff) * maxH))) / 2) - this.chatListView.getPaddingTop()) - AndroidUtilities.dp(7.0f);
            }
        }
        if (offset == Integer.MAX_VALUE) {
            offset = (this.chatListView.getMeasuredHeight() - object.getApproximateHeight()) / 2;
        }
        return Math.max(0, offset);
    }

    public void scrollToMessageId(int id, int fromMessageId, boolean select, int loadIndex, boolean smooth) {
        MessageObject object = (MessageObject) this.messagesDict[loadIndex].get(Integer.valueOf(id));
        boolean query = false;
        if (object == null) {
            query = true;
        } else if (this.messages.indexOf(object) != -1) {
            if (select) {
                this.highlightMessageId = id;
            } else {
                this.highlightMessageId = Integer.MAX_VALUE;
            }
            int yOffset = getScrollOffsetForMessage(object);
            if (smooth) {
                if (this.messages.get(this.messages.size() - 1) == object) {
                    this.chatListView.smoothScrollToPosition(this.chatAdapter.getItemCount() - 1);
                } else {
                    this.chatListView.smoothScrollToPosition(this.chatAdapter.messagesStartRow + this.messages.indexOf(object));
                }
            } else if (this.messages.get(this.messages.size() - 1) == object) {
                this.chatLayoutManager.scrollToPositionWithOffset(this.chatAdapter.getItemCount() - 1, yOffset, false);
            } else {
                this.chatLayoutManager.scrollToPositionWithOffset(this.chatAdapter.messagesStartRow + this.messages.indexOf(object), yOffset, false);
            }
            updateVisibleRows();
            boolean found = false;
            int count = this.chatListView.getChildCount();
            for (int a = 0; a < count; a++) {
                View view = this.chatListView.getChildAt(a);
                MessageObject messageObject;
                if (view instanceof ChatMessageCell) {
                    messageObject = ((ChatMessageCell) view).getMessageObject();
                    if (messageObject != null && messageObject.getId() == object.getId()) {
                        found = true;
                        break;
                    }
                } else if (view instanceof ChatActionCell) {
                    messageObject = ((ChatActionCell) view).getMessageObject();
                    if (messageObject != null && messageObject.getId() == object.getId()) {
                        found = true;
                        break;
                    }
                } else {
                    continue;
                }
            }
            if (!found) {
                showPagedownButton(true, true);
            }
        } else {
            query = true;
        }
        if (query) {
            if (this.currentEncryptedChat == null || MessagesStorage.getInstance().checkMessageId(this.dialog_id, this.startLoadFromMessageId)) {
                this.waitingForLoad.clear();
                this.waitingForReplyMessageLoad = true;
                this.highlightMessageId = Integer.MAX_VALUE;
                this.scrollToMessagePosition = -10000;
                this.startLoadFromMessageId = id;
                if (id == this.createUnreadMessageAfterId) {
                    this.createUnreadMessageAfterIdLoading = true;
                }
                this.waitingForLoad.add(Integer.valueOf(this.lastLoadIndex));
                MessagesController instance = MessagesController.getInstance();
                long j = loadIndex == 0 ? this.dialog_id : this.mergeDialogId;
                int i = AndroidUtilities.isTablet() ? 30 : 20;
                int i2 = this.startLoadFromMessageId;
                int i3 = this.classGuid;
                boolean isChannel = ChatObject.isChannel(this.currentChat);
                int i4 = this.lastLoadIndex;
                this.lastLoadIndex = i4 + 1;
                instance.loadMessages(j, i, i2, 0, true, 0, i3, 3, 0, isChannel, i4);
            } else {
                return;
            }
        }
        this.returnToMessageId = fromMessageId;
        this.returnToLoadIndex = loadIndex;
        this.needSelectFromMessageId = select;
    }

    private void showPagedownButton(boolean show, boolean animated) {
        if (this.pagedownButton != null) {
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (show) {
                this.pagedownButtonShowedByScroll = false;
                if (this.pagedownButton.getTag() == null) {
                    if (this.pagedownButtonAnimation != null) {
                        this.pagedownButtonAnimation.cancel();
                        this.pagedownButtonAnimation = null;
                    }
                    if (animated) {
                        if (this.pagedownButton.getTranslationY() == 0.0f) {
                            this.pagedownButton.setTranslationY((float) AndroidUtilities.dp(100.0f));
                        }
                        this.pagedownButton.setVisibility(0);
                        this.pagedownButton.setTag(Integer.valueOf(1));
                        this.pagedownButtonAnimation = new AnimatorSet();
                        if (this.mentiondownButton.getVisibility() == 0) {
                            animatorSet = this.pagedownButtonAnimation;
                            animatorArr = new Animator[2];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.pagedownButton, "translationY", new float[]{0.0f});
                            animatorArr[1] = ObjectAnimator.ofFloat(this.mentiondownButton, "translationY", new float[]{(float) (-AndroidUtilities.dp(72.0f))});
                            animatorSet.playTogether(animatorArr);
                        } else {
                            animatorSet = this.pagedownButtonAnimation;
                            animatorArr = new Animator[1];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.pagedownButton, "translationY", new float[]{0.0f});
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
                if (animated) {
                    this.pagedownButtonAnimation = new AnimatorSet();
                    if (this.mentiondownButton.getVisibility() == 0) {
                        animatorSet = this.pagedownButtonAnimation;
                        animatorArr = new Animator[2];
                        animatorArr[0] = ObjectAnimator.ofFloat(this.pagedownButton, "translationY", new float[]{(float) AndroidUtilities.dp(100.0f)});
                        animatorArr[1] = ObjectAnimator.ofFloat(this.mentiondownButton, "translationY", new float[]{0.0f});
                        animatorSet.playTogether(animatorArr);
                    } else {
                        animatorSet = this.pagedownButtonAnimation;
                        animatorArr = new Animator[1];
                        animatorArr[0] = ObjectAnimator.ofFloat(this.pagedownButton, "translationY", new float[]{(float) AndroidUtilities.dp(100.0f)});
                        animatorSet.playTogether(animatorArr);
                    }
                    this.pagedownButtonAnimation.setDuration(200);
                    this.pagedownButtonAnimation.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
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

    private void showMentiondownButton(boolean show, boolean animated) {
        if (this.mentiondownButton != null) {
            if (!show) {
                this.returnToMessageId = 0;
                if (this.mentiondownButton.getTag() != null) {
                    this.mentiondownButton.setTag(null);
                    if (this.mentiondownButtonAnimation != null) {
                        this.mentiondownButtonAnimation.cancel();
                        this.mentiondownButtonAnimation = null;
                    }
                    if (animated) {
                        if (this.pagedownButton.getVisibility() == 0) {
                            this.mentiondownButtonAnimation = ObjectAnimator.ofFloat(this.mentiondownButton, "alpha", new float[]{1.0f, 0.0f}).setDuration(200);
                        } else {
                            this.mentiondownButtonAnimation = ObjectAnimator.ofFloat(this.mentiondownButton, "translationY", new float[]{(float) AndroidUtilities.dp(100.0f)}).setDuration(200);
                        }
                        this.mentiondownButtonAnimation.addListener(new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator animation) {
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
                if (animated) {
                    this.mentiondownButton.setVisibility(0);
                    this.mentiondownButton.setTag(Integer.valueOf(1));
                    if (this.pagedownButton.getVisibility() == 0) {
                        this.mentiondownButton.setTranslationY((float) (-AndroidUtilities.dp(72.0f)));
                        this.mentiondownButtonAnimation = ObjectAnimator.ofFloat(this.mentiondownButton, "alpha", new float[]{0.0f, 1.0f}).setDuration(200);
                    } else {
                        if (this.mentiondownButton.getTranslationY() == 0.0f) {
                            this.mentiondownButton.setTranslationY((float) AndroidUtilities.dp(100.0f));
                        }
                        this.mentiondownButtonAnimation = ObjectAnimator.ofFloat(this.mentiondownButton, "translationY", new float[]{0.0f}).setDuration(200);
                    }
                    this.mentiondownButtonAnimation.start();
                    return;
                }
                this.mentiondownButton.setVisibility(0);
            }
        }
    }

    private void updateSecretStatus() {
        if (this.bottomOverlay != null) {
            boolean hideKeyboard = false;
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
                hideKeyboard = true;
            } else if (this.currentEncryptedChat == null || this.bigEmptyView == null) {
                this.bottomOverlay.setVisibility(4);
                return;
            } else {
                if (this.currentEncryptedChat instanceof TLRPC$TL_encryptedChatRequested) {
                    this.bottomOverlayText.setText(LocaleController.getString("EncryptionProcessing", R.string.EncryptionProcessing));
                    this.bottomOverlay.setVisibility(0);
                    hideKeyboard = true;
                } else if (this.currentEncryptedChat instanceof TLRPC$TL_encryptedChatWaiting) {
                    this.bottomOverlayText.setText(AndroidUtilities.replaceTags(LocaleController.formatString("AwaitingEncryption", R.string.AwaitingEncryption, new Object[]{"<b>" + this.currentUser.first_name + "</b>"})));
                    this.bottomOverlay.setVisibility(0);
                    hideKeyboard = true;
                } else if (this.currentEncryptedChat instanceof TLRPC$TL_encryptedChatDiscarded) {
                    this.bottomOverlayText.setText(LocaleController.getString("EncryptionRejected", R.string.EncryptionRejected));
                    this.bottomOverlay.setVisibility(0);
                    this.chatActivityEnterView.setFieldText("");
                    DraftQuery.cleanDraft(this.dialog_id, false);
                    hideKeyboard = true;
                } else if (this.currentEncryptedChat instanceof TLRPC$TL_encryptedChat) {
                    this.bottomOverlay.setVisibility(4);
                }
                checkRaiseSensors();
                checkActionBarMenu();
            }
            if (hideKeyboard) {
                this.chatActivityEnterView.hidePopup(false);
                if (getParentActivity() != null) {
                    AndroidUtilities.hideKeyboard(getParentActivity().getCurrentFocus());
                }
            }
        }
    }

    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {
        if (this.chatActivityEnterView != null) {
            this.chatActivityEnterView.onRequestPermissionsResultFragment(requestCode, permissions, grantResults);
        }
        if (this.mentionsAdapter != null) {
            this.mentionsAdapter.onRequestPermissionsResultFragment(requestCode, permissions, grantResults);
        }
        if (requestCode == 17 && this.chatAttachAlert != null) {
            this.chatAttachAlert.checkCamera(false);
        } else if (requestCode == 21) {
            if (getParentActivity() != null && grantResults != null && grantResults.length != 0 && grantResults[0] != 0) {
                org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setMessage(LocaleController.getString("PermissionNoAudioVideo", R.string.PermissionNoAudioVideo));
                builder.setNegativeButton(LocaleController.getString("PermissionOpenSettings", R.string.PermissionOpenSettings), new OnClickListener() {
                    @TargetApi(9)
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
                            ChatActivity.this.getParentActivity().startActivity(intent);
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                });
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                builder.show();
            }
        } else if (requestCode == 19 && grantResults != null && grantResults.length > 0 && grantResults[0] == 0) {
            processSelectedAttach(0);
        } else if (requestCode == 20 && grantResults != null && grantResults.length > 0 && grantResults[0] == 0) {
            processSelectedAttach(2);
        } else if (requestCode == 101 && this.currentUser != null) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                VoIPHelper.permissionDenied(getParentActivity(), null);
            } else {
                VoIPHelper.startCall(this.currentUser, getParentActivity(), MessagesController.getInstance().getUserFull(this.currentUser.id));
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

    private int getMessageType(MessageObject messageObject) {
        if (messageObject == null) {
            return -1;
        }
        TLRPC$InputStickerSet inputStickerSet;
        boolean canSave;
        String mime;
        if (this.currentEncryptedChat == null) {
            boolean isBroadcastError;
            if (this.isBroadcast && messageObject.getId() <= 0 && messageObject.isSendError()) {
                isBroadcastError = true;
            } else {
                isBroadcastError = false;
            }
            if ((this.isBroadcast || messageObject.getId() > 0 || !messageObject.isOut()) && !isBroadcastError) {
                if (messageObject.type == 6) {
                    return -1;
                }
                if (messageObject.type == 10 || messageObject.type == 11 || messageObject.type == 16) {
                    if (messageObject.getId() == 0) {
                        return -1;
                    }
                    return 1;
                } else if (messageObject.isVoice()) {
                    return 2;
                } else {
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
                        canSave = false;
                        if (!TextUtils.isEmpty(messageObject.messageOwner.attachPath) && new File(messageObject.messageOwner.attachPath).exists()) {
                            canSave = true;
                        }
                        if (!canSave && FileLoader.getPathToMessage(messageObject.messageOwner).exists()) {
                            canSave = true;
                        }
                        if (canSave) {
                            if (messageObject.getDocument() != null) {
                                mime = messageObject.getDocument().mime_type;
                                if (mime != null) {
                                    if (messageObject.getDocumentName().endsWith("attheme")) {
                                        return 10;
                                    }
                                    if (mime.endsWith("/xml")) {
                                        return 5;
                                    }
                                    if (mime.endsWith("/png") || mime.endsWith("/jpg") || mime.endsWith("/jpeg")) {
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
            } else if (!messageObject.isSendError()) {
                return -1;
            } else {
                if (messageObject.isMediaEmpty()) {
                    return 20;
                }
                return 0;
            }
        } else if (messageObject.isSending()) {
            return -1;
        } else {
            if (messageObject.type == 6) {
                return -1;
            }
            if (messageObject.isSendError()) {
                if (messageObject.isMediaEmpty()) {
                    return 20;
                }
                return 0;
            } else if (messageObject.type == 10 || messageObject.type == 11) {
                if (messageObject.getId() == 0 || messageObject.isSending()) {
                    return -1;
                }
                return 1;
            } else if (messageObject.isVoice()) {
                return 2;
            } else {
                if (messageObject.isSticker()) {
                    inputStickerSet = messageObject.getInputStickerSet();
                    if ((inputStickerSet instanceof TLRPC$TL_inputStickerSetShortName) && !StickersQuery.isStickerPackInstalled(inputStickerSet.short_name)) {
                        return 7;
                    }
                } else if (!messageObject.isRoundVideo() && ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) || messageObject.getDocument() != null || messageObject.isMusic() || messageObject.isVideo())) {
                    canSave = false;
                    if (!TextUtils.isEmpty(messageObject.messageOwner.attachPath) && new File(messageObject.messageOwner.attachPath).exists()) {
                        canSave = true;
                    }
                    if (!canSave && FileLoader.getPathToMessage(messageObject.messageOwner).exists()) {
                        canSave = true;
                    }
                    if (canSave) {
                        if (messageObject.getDocument() != null) {
                            mime = messageObject.getDocument().mime_type;
                            if (mime != null && mime.endsWith("text/xml")) {
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

    private void addToSelectedMessages(MessageObject messageObject, boolean outside) {
        addToSelectedMessages(messageObject, outside, true);
    }

    private void addToSelectedMessages(MessageObject messageObject, boolean outside, boolean last) {
        int index = messageObject.getDialogId() == this.dialog_id ? 0 : 1;
        GroupedMessages groupedMessages;
        int a;
        if (!outside || messageObject.getGroupId() == 0) {
            if (this.selectedMessagesIds[index].containsKey(Integer.valueOf(messageObject.getId()))) {
                this.selectedMessagesIds[index].remove(Integer.valueOf(messageObject.getId()));
                if (messageObject.type == 0 || messageObject.caption != null) {
                    this.selectedMessagesCanCopyIds[index].remove(Integer.valueOf(messageObject.getId()));
                }
                if (messageObject.isSticker()) {
                    this.selectedMessagesCanStarIds[index].remove(Integer.valueOf(messageObject.getId()));
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
                this.selectedMessagesIds[index].put(Integer.valueOf(messageObject.getId()), messageObject);
                if (messageObject.type == 0 || messageObject.caption != null) {
                    this.selectedMessagesCanCopyIds[index].put(Integer.valueOf(messageObject.getId()), messageObject);
                }
                if (messageObject.isSticker()) {
                    this.selectedMessagesCanStarIds[index].put(Integer.valueOf(messageObject.getId()), messageObject);
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
            if (last && this.actionBar.isActionModeShowed()) {
                int selectedCount = this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size();
                if (selectedCount == 0) {
                    this.actionBar.hideActionMode();
                    updatePinnedMessageView(true);
                    this.startReplyOnTextChange = false;
                    return;
                }
                ActionBarMenuItem copyItem = this.actionBar.createActionMode().getItem(10);
                ActionBarMenuItem starItem = this.actionBar.createActionMode().getItem(22);
                ActionBarMenuItem editItem = this.actionBar.createActionMode().getItem(23);
                ActionBarMenuItem replyItem = this.actionBar.createActionMode().getItem(19);
                int copyVisible = copyItem.getVisibility();
                int starVisible = starItem.getVisibility();
                copyItem.setVisibility(this.selectedMessagesCanCopyIds[0].size() + this.selectedMessagesCanCopyIds[1].size() != 0 ? 0 : 8);
                int i = (StickersQuery.canAddStickerToFavorites() && this.selectedMessagesCanStarIds[0].size() + this.selectedMessagesCanStarIds[1].size() == selectedCount) ? 0 : 8;
                starItem.setVisibility(i);
                int newCopyVisible = copyItem.getVisibility();
                int newStarVisible = starItem.getVisibility();
                this.actionBar.createActionMode().getItem(12).setVisibility(this.cantDeleteMessagesCount == 0 ? 0 : 8);
                if (editItem != null) {
                    i = (this.canEditMessagesCount == 1 && this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size() == 1) ? 0 : 8;
                    editItem.setVisibility(i);
                }
                this.hasUnfavedSelected = false;
                for (a = 0; a < 2; a++) {
                    for (Entry<Integer, MessageObject> entry : this.selectedMessagesCanStarIds[a].entrySet()) {
                        if (!StickersQuery.isStickerInFavorites(((MessageObject) entry.getValue()).getDocument())) {
                            this.hasUnfavedSelected = true;
                            break;
                        }
                    }
                    if (this.hasUnfavedSelected) {
                        break;
                    }
                }
                starItem.setIcon(this.hasUnfavedSelected ? R.drawable.ic_ab_fave : R.drawable.ic_ab_unfave);
                if (replyItem != null) {
                    boolean allowChatActions = true;
                    if ((this.currentEncryptedChat != null && AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) < 46) || this.isBroadcast || ((this.bottomOverlayChat != null && this.bottomOverlayChat.getVisibility() == 0) || (this.currentChat != null && (ChatObject.isNotInChat(this.currentChat) || !((!ChatObject.isChannel(this.currentChat) || ChatObject.canPost(this.currentChat) || this.currentChat.megagroup) && ChatObject.canSendMessages(this.currentChat)))))) {
                        allowChatActions = false;
                    }
                    int newVisibility = (allowChatActions && this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size() == 1) ? 0 : 8;
                    boolean z = newVisibility == 0 && !this.chatActivityEnterView.hasText();
                    this.startReplyOnTextChange = z;
                    if (replyItem.getVisibility() != newVisibility) {
                        if (this.replyButtonAnimation != null) {
                            this.replyButtonAnimation.cancel();
                        }
                        if (copyVisible == newCopyVisible && starVisible == newStarVisible) {
                            this.replyButtonAnimation = new AnimatorSet();
                            replyItem.setPivotX((float) AndroidUtilities.dp(54.0f));
                            editItem.setPivotX((float) AndroidUtilities.dp(54.0f));
                            AnimatorSet animatorSet;
                            Animator[] animatorArr;
                            if (newVisibility == 0) {
                                replyItem.setVisibility(newVisibility);
                                animatorSet = this.replyButtonAnimation;
                                animatorArr = new Animator[4];
                                animatorArr[0] = ObjectAnimator.ofFloat(replyItem, "alpha", new float[]{1.0f});
                                animatorArr[1] = ObjectAnimator.ofFloat(replyItem, "scaleX", new float[]{1.0f});
                                animatorArr[2] = ObjectAnimator.ofFloat(editItem, "alpha", new float[]{1.0f});
                                animatorArr[3] = ObjectAnimator.ofFloat(editItem, "scaleX", new float[]{1.0f});
                                animatorSet.playTogether(animatorArr);
                            } else {
                                animatorSet = this.replyButtonAnimation;
                                animatorArr = new Animator[4];
                                animatorArr[0] = ObjectAnimator.ofFloat(replyItem, "alpha", new float[]{0.0f});
                                animatorArr[1] = ObjectAnimator.ofFloat(replyItem, "scaleX", new float[]{0.0f});
                                animatorArr[2] = ObjectAnimator.ofFloat(editItem, "alpha", new float[]{0.0f});
                                animatorArr[3] = ObjectAnimator.ofFloat(editItem, "scaleX", new float[]{0.0f});
                                animatorSet.playTogether(animatorArr);
                            }
                            this.replyButtonAnimation.setDuration(100);
                            final int i2 = newVisibility;
                            final ActionBarMenuItem actionBarMenuItem = replyItem;
                            this.replyButtonAnimation.addListener(new AnimatorListenerAdapter() {
                                public void onAnimationEnd(Animator animation) {
                                    if (ChatActivity.this.replyButtonAnimation != null && ChatActivity.this.replyButtonAnimation.equals(animation) && i2 == 8) {
                                        actionBarMenuItem.setVisibility(8);
                                    }
                                }

                                public void onAnimationCancel(Animator animation) {
                                    if (ChatActivity.this.replyButtonAnimation != null && ChatActivity.this.replyButtonAnimation.equals(animation)) {
                                        ChatActivity.this.replyButtonAnimation = null;
                                    }
                                }
                            });
                            this.replyButtonAnimation.start();
                            return;
                        }
                        if (newVisibility == 0) {
                            replyItem.setAlpha(1.0f);
                            replyItem.setScaleX(1.0f);
                        } else {
                            replyItem.setAlpha(0.0f);
                            replyItem.setScaleX(0.0f);
                        }
                        replyItem.setVisibility(newVisibility);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        boolean hasUnselected = false;
        groupedMessages = (GroupedMessages) this.groupedMessagesMap.get(Long.valueOf(messageObject.getGroupId()));
        if (groupedMessages != null) {
            int lastNum = 0;
            for (a = 0; a < groupedMessages.messages.size(); a++) {
                if (!this.selectedMessagesIds[index].containsKey(Integer.valueOf(((MessageObject) groupedMessages.messages.get(a)).getId()))) {
                    hasUnselected = true;
                    lastNum = a;
                }
            }
            a = 0;
            while (a < groupedMessages.messages.size()) {
                MessageObject message = (MessageObject) groupedMessages.messages.get(a);
                if (!hasUnselected) {
                    addToSelectedMessages(message, false, a == groupedMessages.messages.size() + -1);
                } else if (!this.selectedMessagesIds[index].containsKey(Integer.valueOf(message.getId()))) {
                    addToSelectedMessages(message, false, a == lastNum);
                }
                a++;
            }
        }
    }

    private void processRowSelect(View view, boolean outside) {
        MessageObject message = null;
        if (view instanceof ChatMessageCell) {
            message = ((ChatMessageCell) view).getMessageObject();
        } else if (view instanceof ChatActionCell) {
            message = ((ChatActionCell) view).getMessageObject();
        }
        int type = getMessageType(message);
        if (type >= 2 && type != 20) {
            addToSelectedMessages(message, outside);
            updateActionModeTitle();
            updateVisibleRows();
        }
    }

    private void updateActionModeTitle() {
        if (!this.actionBar.isActionModeShowed()) {
            return;
        }
        if (!this.selectedMessagesIds[0].isEmpty() || !this.selectedMessagesIds[1].isEmpty()) {
            this.selectedMessagesCountTextView.setNumber(this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size(), true);
        }
    }

    private void updateTitle() {
        if (this.avatarContainer != null) {
            if (this.isDownloadManager) {
                this.avatarContainer.setTitle(LocaleController.getString("downloadManager", R.string.downloadManager));
                try {
                    int i = this.messages.size();
                    if (i != 0) {
                        i--;
                    }
                    this.avatarContainer.setSubtitle(LocaleController.getString("downloadCount", R.string.downloadCount) + " : " + i);
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
                    this.avatarContainer.setTitle(PhoneFormat.getInstance().format("+" + this.currentUser.phone));
                }
            }
        }
    }

    private void updateBotButtons() {
        if (this.headerItem != null && this.currentUser != null && this.currentEncryptedChat == null && this.currentUser.bot) {
            boolean hasHelp = false;
            boolean hasSettings = false;
            if (!this.botInfo.isEmpty()) {
                for (Entry<Integer, TLRPC$BotInfo> entry : this.botInfo.entrySet()) {
                    TLRPC$BotInfo info = (TLRPC$BotInfo) entry.getValue();
                    for (int a = 0; a < info.commands.size(); a++) {
                        TLRPC$TL_botCommand command = (TLRPC$TL_botCommand) info.commands.get(a);
                        if (command.command.toLowerCase().equals("help")) {
                            hasHelp = true;
                        } else if (command.command.toLowerCase().equals("settings")) {
                            hasSettings = true;
                        }
                        if (hasSettings && hasHelp) {
                            break;
                        }
                    }
                }
            }
            if (hasHelp) {
                this.headerItem.showSubItem(30);
            } else {
                this.headerItem.hideSubItem(30);
            }
            if (hasSettings) {
                this.headerItem.showSubItem(31);
            } else {
                this.headerItem.hideSubItem(31);
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
            Drawable rightIcon;
            if (MessagesController.getInstance().isDialogMuted(this.dialog_id)) {
                rightIcon = Theme.chat_muteIconDrawable;
            } else {
                rightIcon = null;
            }
            ChatAvatarContainer chatAvatarContainer = this.avatarContainer;
            if (this.currentEncryptedChat != null) {
                drawable = Theme.chat_lockIconDrawable;
            }
            chatAvatarContainer.setTitleIcons(drawable, rightIcon);
            if (this.muteItem == null) {
                return;
            }
            if (rightIcon != null) {
                this.muteItem.setText(LocaleController.getString("UnmuteNotifications", R.string.UnmuteNotifications));
            } else {
                this.muteItem.setText(LocaleController.getString("MuteNotifications", R.string.MuteNotifications));
            }
        }
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
            TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.currentChat.id));
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

    public void openVideoEditor(String videoPath, String caption) {
        if (getParentActivity() != null) {
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(videoPath, 1);
            PhotoViewer.getInstance().setParentActivity(getParentActivity());
            final ArrayList<Object> cameraPhoto = new ArrayList();
            MediaController$PhotoEntry entry = new MediaController$PhotoEntry(0, 0, 0, videoPath, 0, true);
            entry.caption = caption;
            cameraPhoto.add(entry);
            final Bitmap bitmap = thumb;
            PhotoViewer.getInstance().openPhotoForSelect(cameraPhoto, 0, 2, new EmptyPhotoViewerProvider() {
                public Bitmap getThumbForPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
                    return bitmap;
                }

                public void sendButtonPressed(int index, VideoEditedInfo videoEditedInfo) {
                    ChatActivity.this.sendMedia((MediaController$PhotoEntry) cameraPhoto.get(0), videoEditedInfo);
                }

                public boolean canScrollAway() {
                    return false;
                }
            }, this);
            return;
        }
        SendMessagesHelper.prepareSendingVideo(videoPath, 0, 0, 0, 0, null, this.dialog_id, this.replyingMessageObject, null, 0);
        showReplyPanel(false, null, null, null, false);
        DraftQuery.cleanDraft(this.dialog_id, true);
    }

    private void showAttachmentError() {
        if (getParentActivity() != null) {
            Toast.makeText(getParentActivity(), LocaleController.getString("UnsupportedAttachment", R.string.UnsupportedAttachment), 0).show();
        }
    }

    private void sendUriAsDocument(Uri uri) {
        if (uri != null) {
            String extractUriFrom = uri.toString();
            if (extractUriFrom.contains("com.google.android.apps.photos.contentprovider")) {
                try {
                    String firstExtraction = extractUriFrom.split("/1/")[1];
                    int index = firstExtraction.indexOf("/ACTUAL");
                    if (index != -1) {
                        uri = Uri.parse(URLDecoder.decode(firstExtraction.substring(0, index), "UTF-8"));
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            String tempPath = AndroidUtilities.getPath(uri);
            String originalPath = tempPath;
            if (tempPath == null) {
                originalPath = uri.toString();
                tempPath = MediaController.copyFileToCache(uri, "file");
            }
            if (tempPath == null) {
                showAttachmentError();
                return;
            }
            SendMessagesHelper.prepareSendingDocument(tempPath, originalPath, null, null, this.dialog_id, this.replyingMessageObject, null);
        }
    }

    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            if (requestCode == 0) {
                PhotoViewer.getInstance().setParentActivity(getParentActivity());
                ArrayList<Object> arrayList = new ArrayList();
                int orientation = 0;
                try {
                    switch (new ExifInterface(this.currentPicturePath).getAttributeInt("Orientation", 1)) {
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
                arrayList.add(new MediaController$PhotoEntry(0, 0, 0, this.currentPicturePath, orientation, false));
                final ArrayList<Object> arrayList2 = arrayList;
                PhotoViewer.getInstance().openPhotoForSelect(arrayList, 0, 2, new EmptyPhotoViewerProvider() {
                    public void sendButtonPressed(int index, VideoEditedInfo videoEditedInfo) {
                        ChatActivity.this.sendMedia((MediaController$PhotoEntry) arrayList2.get(0), null);
                    }
                }, this);
                AndroidUtilities.addMediaToGallery(this.currentPicturePath);
                this.currentPicturePath = null;
            } else if (requestCode == 1) {
                if (data == null || data.getData() == null) {
                    showAttachmentError();
                    return;
                }
                uri = data.getData();
                if (uri.toString().contains(MimeTypes.BASE_TYPE_VIDEO)) {
                    videoPath = null;
                    try {
                        videoPath = AndroidUtilities.getPath(uri);
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                    if (videoPath == null) {
                        showAttachmentError();
                    }
                    if (this.paused) {
                        this.startVideoEdit = videoPath;
                    } else {
                        openVideoEditor(videoPath, null);
                    }
                } else {
                    SendMessagesHelper.prepareSendingPhoto(null, uri, this.dialog_id, this.replyingMessageObject, null, null, null, 0);
                }
                showReplyPanel(false, null, null, null, false);
                DraftQuery.cleanDraft(this.dialog_id, true);
            } else if (requestCode == 2) {
                videoPath = null;
                FileLog.d("pic path " + this.currentPicturePath);
                if (!(data == null || this.currentPicturePath == null || !new File(this.currentPicturePath).exists())) {
                    data = null;
                }
                if (data != null) {
                    uri = data.getData();
                    if (uri != null) {
                        FileLog.d("video record uri " + uri.toString());
                        videoPath = AndroidUtilities.getPath(uri);
                        FileLog.d("resolved path = " + videoPath);
                        if (videoPath == null || !new File(videoPath).exists()) {
                            videoPath = this.currentPicturePath;
                        }
                    } else {
                        videoPath = this.currentPicturePath;
                    }
                    AndroidUtilities.addMediaToGallery(this.currentPicturePath);
                    this.currentPicturePath = null;
                }
                if (videoPath == null && this.currentPicturePath != null) {
                    if (new File(this.currentPicturePath).exists()) {
                        videoPath = this.currentPicturePath;
                    }
                    this.currentPicturePath = null;
                }
                if (this.paused) {
                    this.startVideoEdit = videoPath;
                } else {
                    openVideoEditor(videoPath, null);
                }
            } else if (requestCode == 21) {
                if (data == null) {
                    showAttachmentError();
                    return;
                }
                if (data.getData() != null) {
                    sendUriAsDocument(data.getData());
                } else if (data.getClipData() != null) {
                    ClipData clipData = data.getClipData();
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        sendUriAsDocument(clipData.getItemAt(i).getUri());
                    }
                } else {
                    showAttachmentError();
                }
                showReplyPanel(false, null, null, null, false);
                DraftQuery.cleanDraft(this.dialog_id, true);
            } else if (requestCode == 31) {
                if (data == null || data.getData() == null) {
                    showAttachmentError();
                    return;
                }
                uri = data.getData();
                Cursor c = null;
                try {
                    c = getParentActivity().getContentResolver().query(uri, new String[]{"display_name", "data1"}, null, null, null);
                    if (c != null) {
                        boolean sent = false;
                        while (c.moveToNext()) {
                            sent = true;
                            String name = c.getString(0);
                            String number = c.getString(1);
                            User user = new TLRPC$TL_user();
                            user.first_name = name;
                            user.last_name = "";
                            user.phone = number;
                            SendMessagesHelper.getInstance().sendMessage(user, this.dialog_id, this.replyingMessageObject, null, null);
                        }
                        if (sent) {
                            showReplyPanel(false, null, null, null, false);
                            DraftQuery.cleanDraft(this.dialog_id, true);
                        }
                    }
                    if (c != null) {
                        try {
                            if (!c.isClosed()) {
                                c.close();
                            }
                        } catch (Exception e22) {
                            FileLog.e(e22);
                        }
                    }
                } catch (Throwable th) {
                    if (c != null) {
                        try {
                            if (!c.isClosed()) {
                                c.close();
                            }
                        } catch (Exception e222) {
                            FileLog.e(e222);
                        }
                    }
                }
            }
        }
        if (resultCode == -1 && data != null) {
            Bundle result = data.getBundleExtra(Response.BUNDLE_KEY);
            if (result != null) {
                int sdkResponseStatus = result.getInt(General.STATUS_CODE);
                String hostData = result.getString(General.HOST_RESPONSE);
                String hostDataSign = result.getString(General.HOST_RESPONSE_SIGN);
                Long uniqueTranId;
                switch (sdkResponseStatus) {
                    case 0:
                        uniqueTranId = Long.valueOf(result.getLong(Payment.UNIQUE_TRAN_ID));
                        if (AppPreferences.isResponseValid(ApplicationLoader.applicationContext, hostData, hostDataSign)) {
                            Toast.makeText(ApplicationLoader.applicationContext, "Sign is valid, you can trust response", 0).show();
                        } else {
                            Toast.makeText(ApplicationLoader.applicationContext, "Sign is not valid, you must don't trust response", 0).show();
                        }
                        Toast.makeText(ApplicationLoader.applicationContext, "Transaction Successful", 0).show();
                        return;
                    case 1001:
                    case Status.STATUS_UNKNOWN /*1201*/:
                        uniqueTranId = Long.valueOf(result.getLong(Payment.UNIQUE_TRAN_ID));
                        Toast.makeText(ApplicationLoader.applicationContext, "Transaction Unknown", 0).show();
                        return;
                    case 1002:
                        Toast.makeText(ApplicationLoader.applicationContext, "Transaction Failed", 0).show();
                        return;
                    case Status.STATUS_INVALID_HOST_REQUEST /*1100*/:
                        Toast.makeText(ApplicationLoader.applicationContext, "Invalid Host Data, check your host data request ", 0).show();
                        return;
                    case Status.STATUS_REGISTER_NEEDED /*1102*/:
                        Toast.makeText(ApplicationLoader.applicationContext, "First Register User", 0).show();
                        return;
                    case Status.STATUS_SDK_NEED_UPDATE /*1105*/:
                        Toast.makeText(ApplicationLoader.applicationContext, "You must update your sdk", 0).show();
                        return;
                    case Status.STATUS_CANCELED /*2020*/:
                        Toast.makeText(ApplicationLoader.applicationContext, "Transaction Canceled By User", 0).show();
                        return;
                    case Status.STATUS_PAYMENT_TIMEOUT /*2021*/:
                        Toast.makeText(ApplicationLoader.applicationContext, "Transaction Canceled By SDK(due to timeout)", 0).show();
                        return;
                    case Status.STATUS_INVALID_USER_DATA /*2022*/:
                        Toast.makeText(ApplicationLoader.applicationContext, "User don't confirm registration data (such as mobile no)", 0).show();
                        return;
                    case Status.STATUS_DECRYPTION_ERROR /*2023*/:
                        Toast.makeText(ApplicationLoader.applicationContext, "Don't change secret key until register again", 0).show();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void saveSelfArgs(Bundle args) {
        if (this.currentPicturePath != null) {
            args.putString("path", this.currentPicturePath);
        }
    }

    public void restoreSelfArgs(Bundle args) {
        this.currentPicturePath = args.getString("path");
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

    public boolean processSendingText(String text) {
        return this.chatActivityEnterView.processSendingText(text);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void didReceivedNotification(int r168, java.lang.Object... r169) {
        /*
        r167 = this;
        r5 = org.telegram.messenger.NotificationCenter.messagesDidLoaded;
        r0 = r168;
        if (r0 != r5) goto L_0x0e1a;
    L_0x0006:
        r5 = "LEE";
        r6 = "Debug1946 didReceivedNotification()";
        android.util.Log.d(r5, r6);
        r0 = r167;
        r5 = r0.channelIsFilter;
        if (r5 == 0) goto L_0x0016;
    L_0x0015:
        return;
    L_0x0016:
        r5 = "LEE";
        r6 = "Debug1946 didReceivedNotification() after  check filtering";
        android.util.Log.d(r5, r6);
        r5 = 10;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r73 = r5.intValue();
        r0 = r167;
        r5 = r0.classGuid;
        r0 = r73;
        if (r0 != r5) goto L_0x0aa2;
    L_0x0031:
        r0 = r167;
        r5 = r0.openAnimationEnded;
        if (r5 != 0) goto L_0x0055;
    L_0x0037:
        r5 = org.telegram.messenger.NotificationCenter.getInstance();
        r6 = 4;
        r6 = new int[r6];
        r7 = 0;
        r8 = org.telegram.messenger.NotificationCenter.chatInfoDidLoaded;
        r6[r7] = r8;
        r7 = 1;
        r8 = org.telegram.messenger.NotificationCenter.dialogsNeedReload;
        r6[r7] = r8;
        r7 = 2;
        r8 = org.telegram.messenger.NotificationCenter.closeChats;
        r6[r7] = r8;
        r7 = 3;
        r8 = org.telegram.messenger.NotificationCenter.botKeyboardDidLoaded;
        r6[r7] = r8;
        r5.setAllowedNotificationsDutingAnimation(r6);
    L_0x0055:
        r5 = 11;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r140 = r5.intValue();
        r0 = r167;
        r5 = r0.waitingForLoad;
        r6 = java.lang.Integer.valueOf(r140);
        r81 = r5.indexOf(r6);
        r0 = r167;
        r5 = r0.isAdvancedForward;
        if (r5 != 0) goto L_0x007f;
    L_0x0071:
        r5 = -1;
        r0 = r81;
        if (r0 == r5) goto L_0x0015;
    L_0x0076:
        r0 = r167;
        r5 = r0.waitingForLoad;
        r0 = r81;
        r5.remove(r0);
    L_0x007f:
        r53 = org.telegram.messenger.UserConfig.getClientUserId();
        r5 = 2;
        r105 = r169[r5];
        r105 = (java.util.ArrayList) r105;
        r48 = 0;
        r0 = r167;
        r5 = r0.waitingForReplyMessageLoad;
        if (r5 == 0) goto L_0x014c;
    L_0x0090:
        r0 = r167;
        r5 = r0.createUnreadMessageAfterIdLoading;
        if (r5 != 0) goto L_0x011f;
    L_0x0096:
        r71 = 0;
        r4 = 0;
    L_0x0099:
        r5 = r105.size();
        if (r4 >= r5) goto L_0x00b3;
    L_0x009f:
        r0 = r105;
        r124 = r0.get(r4);
        r124 = (org.telegram.messenger.MessageObject) r124;
        r5 = r124.getId();
        r0 = r167;
        r6 = r0.startLoadFromMessageId;
        if (r5 != r6) goto L_0x00bc;
    L_0x00b1:
        r71 = 1;
    L_0x00b3:
        if (r71 != 0) goto L_0x011f;
    L_0x00b5:
        r5 = 0;
        r0 = r167;
        r0.startLoadFromMessageId = r5;
        goto L_0x0015;
    L_0x00bc:
        r0 = r167;
        r5 = r0.goToFirstMsg;	 Catch:{ Exception -> 0x0116 }
        if (r5 == 0) goto L_0x00e5;
    L_0x00c2:
        r5 = r105.size();	 Catch:{ Exception -> 0x0116 }
        r6 = 9;
        if (r5 < r6) goto L_0x00e5;
    L_0x00ca:
        r5 = r105.size();	 Catch:{ Exception -> 0x0116 }
        r5 = r5 + -1;
        r0 = r105;
        r5 = r0.get(r5);	 Catch:{ Exception -> 0x0116 }
        r5 = (org.telegram.messenger.MessageObject) r5;	 Catch:{ Exception -> 0x0116 }
        r5 = r5.getId();	 Catch:{ Exception -> 0x0116 }
        r0 = r167;
        r0.startLoadFromMessageId = r5;	 Catch:{ Exception -> 0x0116 }
        r5 = 0;
        r0 = r167;
        r0.goToFirstMsg = r5;	 Catch:{ Exception -> 0x0116 }
    L_0x00e5:
        r5 = r4 + 1;
        r6 = r105.size();
        if (r5 >= r6) goto L_0x011b;
    L_0x00ed:
        r5 = r4 + 1;
        r0 = r105;
        r125 = r0.get(r5);
        r125 = (org.telegram.messenger.MessageObject) r125;
        r5 = r124.getId();
        r0 = r167;
        r6 = r0.startLoadFromMessageId;
        if (r5 < r6) goto L_0x011b;
    L_0x0101:
        r5 = r125.getId();
        r0 = r167;
        r6 = r0.startLoadFromMessageId;
        if (r5 >= r6) goto L_0x011b;
    L_0x010b:
        r5 = r124.getId();
        r0 = r167;
        r0.startLoadFromMessageId = r5;
        r71 = 1;
        goto L_0x00b3;
    L_0x0116:
        r59 = move-exception;
        r59.printStackTrace();
        goto L_0x00e5;
    L_0x011b:
        r4 = r4 + 1;
        goto L_0x0099;
    L_0x011f:
        r0 = r167;
        r0 = r0.startLoadFromMessageId;
        r149 = r0;
        r0 = r167;
        r0 = r0.needSelectFromMessageId;
        r116 = r0;
        r0 = r167;
        r0 = r0.createUnreadMessageAfterId;
        r152 = r0;
        r0 = r167;
        r0 = r0.createUnreadMessageAfterIdLoading;
        r48 = r0;
        r167.clearChatData();
        r0 = r152;
        r1 = r167;
        r1.createUnreadMessageAfterId = r0;
        r0 = r149;
        r1 = r167;
        r1.startLoadFromMessageId = r0;
        r0 = r116;
        r1 = r167;
        r1.needSelectFromMessageId = r0;
    L_0x014c:
        r0 = r167;
        r5 = r0.loadsCount;
        r5 = r5 + 1;
        r0 = r167;
        r0.loadsCount = r5;
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r60 = r5.longValue();
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x02f3;
    L_0x0167:
        r97 = 0;
    L_0x0169:
        r5 = 1;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r47 = r5.intValue();
        r5 = 3;
        r5 = r169[r5];
        r5 = (java.lang.Boolean) r5;
        r86 = r5.booleanValue();
        r5 = 4;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r70 = r5.intValue();
        r5 = 7;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r93 = r5.intValue();
        r5 = 8;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r98 = r5.intValue();
        r5 = 12;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r99 = r5.intValue();
        r100 = 0;
        r0 = r167;
        r5 = r0.isAdvancedForward;
        if (r5 != 0) goto L_0x01bc;
    L_0x01a9:
        r5 = 13;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r100 = r5.intValue();
        if (r100 >= 0) goto L_0x02f7;
    L_0x01b5:
        r100 = r100 * -1;
        r5 = 0;
        r0 = r167;
        r0.hasAllMentionsLocal = r5;
    L_0x01bc:
        r5 = 4;
        r0 = r98;
        if (r0 != r5) goto L_0x01f9;
    L_0x01c1:
        r0 = r99;
        r1 = r167;
        r1.startLoadFromMessageId = r0;
        r5 = r105.size();
        r4 = r5 + -1;
    L_0x01cd:
        if (r4 <= 0) goto L_0x01f9;
    L_0x01cf:
        r0 = r105;
        r124 = r0.get(r4);
        r124 = (org.telegram.messenger.MessageObject) r124;
        r0 = r124;
        r5 = r0.type;
        if (r5 >= 0) goto L_0x0304;
    L_0x01dd:
        r5 = r124.getId();
        r0 = r167;
        r6 = r0.startLoadFromMessageId;
        if (r5 != r6) goto L_0x0304;
    L_0x01e7:
        r5 = r4 + -1;
        r0 = r105;
        r5 = r0.get(r5);
        r5 = (org.telegram.messenger.MessageObject) r5;
        r5 = r5.getId();
        r0 = r167;
        r0.startLoadFromMessageId = r5;
    L_0x01f9:
        r163 = 0;
        r146 = 0;
        if (r70 == 0) goto L_0x031d;
    L_0x01ff:
        r5 = 5;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r5 = r5.intValue();
        r0 = r167;
        r0.last_message_id = r5;
        r5 = 3;
        r0 = r98;
        if (r0 != r5) goto L_0x0308;
    L_0x0211:
        r0 = r167;
        r5 = r0.loadingFromOldPosition;
        if (r5 == 0) goto L_0x0237;
    L_0x0217:
        r5 = 6;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r5 = r5.intValue();
        r0 = r167;
        r0.unread_to_load = r5;
        r0 = r167;
        r5 = r0.unread_to_load;
        if (r5 == 0) goto L_0x0230;
    L_0x022a:
        r0 = r70;
        r1 = r167;
        r1.createUnreadMessageAfterId = r0;
    L_0x0230:
        r146 = 1;
        r5 = 0;
        r0 = r167;
        r0.loadingFromOldPosition = r5;
    L_0x0237:
        r5 = 0;
        r0 = r167;
        r0.first_unread_id = r5;
    L_0x023c:
        r122 = 0;
        if (r98 == 0) goto L_0x0253;
    L_0x0240:
        r0 = r167;
        r6 = r0.forwardEndReached;
        r0 = r167;
        r5 = r0.startLoadFromMessageId;
        if (r5 != 0) goto L_0x033c;
    L_0x024a:
        r0 = r167;
        r5 = r0.last_message_id;
        if (r5 != 0) goto L_0x033c;
    L_0x0250:
        r5 = 1;
    L_0x0251:
        r6[r97] = r5;
    L_0x0253:
        r5 = 1;
        r0 = r98;
        if (r0 == r5) goto L_0x025d;
    L_0x0258:
        r5 = 3;
        r0 = r98;
        if (r0 != r5) goto L_0x0281;
    L_0x025d:
        r5 = 1;
        r0 = r97;
        if (r0 != r5) goto L_0x0281;
    L_0x0262:
        r0 = r167;
        r5 = r0.endReached;
        r6 = 0;
        r0 = r167;
        r7 = r0.cacheEndReached;
        r8 = 0;
        r11 = 1;
        r7[r8] = r11;
        r5[r6] = r11;
        r0 = r167;
        r5 = r0.forwardEndReached;
        r6 = 0;
        r7 = 0;
        r5[r6] = r7;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = 0;
        r7 = 0;
        r5[r6] = r7;
    L_0x0281:
        r0 = r167;
        r5 = r0.loadsCount;
        r6 = 1;
        if (r5 != r6) goto L_0x029a;
    L_0x0288:
        r5 = r105.size();
        r6 = 20;
        if (r5 <= r6) goto L_0x029a;
    L_0x0290:
        r0 = r167;
        r5 = r0.loadsCount;
        r5 = r5 + 1;
        r0 = r167;
        r0.loadsCount = r5;
    L_0x029a:
        r0 = r167;
        r5 = r0.firstLoading;
        if (r5 == 0) goto L_0x0360;
    L_0x02a0:
        r0 = r167;
        r5 = r0.forwardEndReached;
        r5 = r5[r97];
        if (r5 != 0) goto L_0x0351;
    L_0x02a8:
        r0 = r167;
        r5 = r0.messages;
        r5.clear();
        r0 = r167;
        r5 = r0.messagesByDays;
        r5.clear();
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r5.clear();
        r4 = 0;
    L_0x02be:
        r5 = 2;
        if (r4 >= r5) goto L_0x0351;
    L_0x02c1:
        r0 = r167;
        r5 = r0.messagesDict;
        r5 = r5[r4];
        r5.clear();
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x033f;
    L_0x02d0:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r5[r4] = r6;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r5[r4] = r6;
    L_0x02e1:
        r0 = r167;
        r5 = r0.maxDate;
        r6 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r5[r4] = r6;
        r0 = r167;
        r5 = r0.minDate;
        r6 = 0;
        r5[r4] = r6;
        r4 = r4 + 1;
        goto L_0x02be;
    L_0x02f3:
        r97 = 1;
        goto L_0x0169;
    L_0x02f7:
        r0 = r167;
        r5 = r0.first;
        if (r5 == 0) goto L_0x01bc;
    L_0x02fd:
        r5 = 1;
        r0 = r167;
        r0.hasAllMentionsLocal = r5;
        goto L_0x01bc;
    L_0x0304:
        r4 = r4 + -1;
        goto L_0x01cd;
    L_0x0308:
        r0 = r70;
        r1 = r167;
        r1.first_unread_id = r0;
        r5 = 6;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r5 = r5.intValue();
        r0 = r167;
        r0.unread_to_load = r5;
        goto L_0x023c;
    L_0x031d:
        r0 = r167;
        r5 = r0.startLoadFromMessageId;
        if (r5 == 0) goto L_0x023c;
    L_0x0323:
        r5 = 3;
        r0 = r98;
        if (r0 == r5) goto L_0x032d;
    L_0x0328:
        r5 = 4;
        r0 = r98;
        if (r0 != r5) goto L_0x023c;
    L_0x032d:
        r5 = 5;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r5 = r5.intValue();
        r0 = r167;
        r0.last_message_id = r5;
        goto L_0x023c;
    L_0x033c:
        r5 = 0;
        goto L_0x0251;
    L_0x033f:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r5[r4] = r6;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r5[r4] = r6;
        goto L_0x02e1;
    L_0x0351:
        r5 = 0;
        r0 = r167;
        r0.firstLoading = r5;
        r5 = new org.telegram.ui.ChatActivity$90;
        r0 = r167;
        r5.<init>();
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r5);
    L_0x0360:
        r5 = 1;
        r0 = r98;
        if (r0 != r5) goto L_0x0368;
    L_0x0365:
        java.util.Collections.reverse(r105);
    L_0x0368:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x0377;
    L_0x036e:
        r0 = r167;
        r6 = r0.dialog_id;
        r0 = r105;
        org.telegram.messenger.query.MessagesQuery.loadReplyMessagesForMessages(r0, r6);
    L_0x0377:
        r24 = 0;
        r5 = 2;
        r0 = r98;
        if (r0 != r5) goto L_0x038e;
    L_0x037e:
        r5 = r105.isEmpty();
        if (r5 == 0) goto L_0x038e;
    L_0x0384:
        if (r86 != 0) goto L_0x038e;
    L_0x0386:
        r0 = r167;
        r5 = r0.forwardEndReached;
        r6 = 0;
        r7 = 1;
        r5[r6] = r7;
    L_0x038e:
        r119 = 0;
        r40 = 0;
        r4 = 0;
    L_0x0393:
        r5 = r105.size();
        if (r4 >= r5) goto L_0x08cd;
    L_0x0399:
        r0 = r105;
        r124 = r0.get(r4);
        r124 = (org.telegram.messenger.MessageObject) r124;
        r5 = r124.getApproximateHeight();
        r24 = r24 + r5;
        r0 = r167;
        r5 = r0.currentUser;
        if (r5 == 0) goto L_0x03d7;
    L_0x03ad:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.self;
        if (r5 == 0) goto L_0x03bc;
    L_0x03b5:
        r0 = r124;
        r5 = r0.messageOwner;
        r6 = 1;
        r5.out = r6;
    L_0x03bc:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.bot;
        if (r5 == 0) goto L_0x03ca;
    L_0x03c4:
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x03d4;
    L_0x03ca:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.id;
        r0 = r53;
        if (r5 != r0) goto L_0x03d7;
    L_0x03d4:
        r124.setIsRead();
    L_0x03d7:
        r0 = r167;
        r5 = r0.messagesDict;
        r5 = r5[r97];
        r6 = r124.getId();
        r6 = java.lang.Integer.valueOf(r6);
        r5 = r5.containsKey(r6);
        if (r5 == 0) goto L_0x03ee;
    L_0x03eb:
        r4 = r4 + 1;
        goto L_0x0393;
    L_0x03ee:
        r5 = 1;
        r0 = r97;
        if (r0 != r5) goto L_0x03f6;
    L_0x03f3:
        r124.setIsRead();
    L_0x03f6:
        if (r97 != 0) goto L_0x0417;
    L_0x03f8:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = org.telegram.messenger.ChatObject.isChannel(r5);
        if (r5 == 0) goto L_0x0417;
    L_0x0402:
        r5 = r124.getId();
        r6 = 1;
        if (r5 != r6) goto L_0x0417;
    L_0x0409:
        r0 = r167;
        r5 = r0.endReached;
        r6 = 1;
        r5[r97] = r6;
        r0 = r167;
        r5 = r0.cacheEndReached;
        r6 = 1;
        r5[r97] = r6;
    L_0x0417:
        r5 = r124.getId();
        if (r5 <= 0) goto L_0x075d;
    L_0x041d:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = r124.getId();
        r0 = r167;
        r7 = r0.maxMessageId;
        r7 = r7[r97];
        r6 = java.lang.Math.min(r6, r7);
        r5[r97] = r6;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = r124.getId();
        r0 = r167;
        r7 = r0.minMessageId;
        r7 = r7[r97];
        r6 = java.lang.Math.max(r6, r7);
        r5[r97] = r6;
    L_0x0445:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.date;
        if (r5 == 0) goto L_0x0485;
    L_0x044d:
        r0 = r167;
        r5 = r0.maxDate;
        r0 = r167;
        r6 = r0.maxDate;
        r6 = r6[r97];
        r0 = r124;
        r7 = r0.messageOwner;
        r7 = r7.date;
        r6 = java.lang.Math.max(r6, r7);
        r5[r97] = r6;
        r0 = r167;
        r5 = r0.minDate;
        r5 = r5[r97];
        if (r5 == 0) goto L_0x0479;
    L_0x046b:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r0 = r167;
        r6 = r0.minDate;
        r6 = r6[r97];
        if (r5 >= r6) goto L_0x0485;
    L_0x0479:
        r0 = r167;
        r5 = r0.minDate;
        r0 = r124;
        r6 = r0.messageOwner;
        r6 = r6.date;
        r5[r97] = r6;
    L_0x0485:
        r5 = r124.getId();
        r0 = r167;
        r6 = r0.last_message_id;
        if (r5 != r6) goto L_0x0496;
    L_0x048f:
        r0 = r167;
        r5 = r0.forwardEndReached;
        r6 = 1;
        r5[r97] = r6;
    L_0x0496:
        r0 = r124;
        r5 = r0.type;
        if (r5 < 0) goto L_0x03eb;
    L_0x049c:
        r5 = 1;
        r0 = r97;
        if (r0 != r5) goto L_0x04ab;
    L_0x04a1:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo;
        if (r5 != 0) goto L_0x03eb;
    L_0x04ab:
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x04b9;
    L_0x04b1:
        r5 = r124.isUnread();
        if (r5 == 0) goto L_0x04b9;
    L_0x04b7:
        r163 = 1;
    L_0x04b9:
        r0 = r167;
        r5 = r0.messagesDict;
        r5 = r5[r97];
        r6 = r124.getId();
        r6 = java.lang.Integer.valueOf(r6);
        r0 = r124;
        r5.put(r6, r0);
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r124;
        r6 = r0.dateKey;
        r58 = r5.get(r6);
        r58 = (java.util.ArrayList) r58;
        if (r58 != 0) goto L_0x0549;
    L_0x04dc:
        r58 = new java.util.ArrayList;
        r58.<init>();
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r124;
        r6 = r0.dateKey;
        r0 = r58;
        r5.put(r6, r0);
        r55 = new org.telegram.tgnet.TLRPC$TL_message;
        r55.<init>();
        r5 = "talagram";
        r6 = "arabgram";
        r5 = r5.contentEquals(r6);
        if (r5 == 0) goto L_0x078d;
    L_0x04ff:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r6 = (long) r5;
        r5 = org.telegram.messenger.LocaleController.formatDateChat(r6);
        r0 = r55;
        r0.message = r5;
    L_0x050e:
        r5 = 0;
        r0 = r55;
        r0.id = r5;
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r0 = r55;
        r0.date = r5;
        r56 = new org.telegram.messenger.MessageObject;
        r5 = 0;
        r6 = 0;
        r0 = r56;
        r1 = r55;
        r0.<init>(r1, r5, r6);
        r5 = 10;
        r0 = r56;
        r0.type = r5;
        r5 = 1;
        r0 = r56;
        r0.contentType = r5;
        r5 = 1;
        r0 = r56;
        r0.isDateObject = r5;
        r5 = 1;
        r0 = r98;
        if (r0 != r5) goto L_0x079e;
    L_0x053d:
        r0 = r167;
        r5 = r0.messages;
        r6 = 0;
        r0 = r56;
        r5.add(r6, r0);
    L_0x0547:
        r122 = r122 + 1;
    L_0x0549:
        r5 = r124.hasValidGroupId();
        if (r5 == 0) goto L_0x0813;
    L_0x054f:
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r0 = r124;
        r6 = r0.messageOwner;
        r6 = r6.grouped_id;
        r6 = java.lang.Long.valueOf(r6);
        r72 = r5.get(r6);
        r72 = (org.telegram.messenger.MessageObject.GroupedMessages) r72;
        if (r72 == 0) goto L_0x05b4;
    L_0x0565:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.size();
        r6 = 1;
        if (r5 <= r6) goto L_0x05b4;
    L_0x0570:
        r5 = 1;
        r0 = r98;
        if (r0 != r5) goto L_0x07a9;
    L_0x0575:
        r0 = r167;
        r5 = r0.messages;
        r6 = 0;
        r139 = r5.get(r6);
        r139 = (org.telegram.messenger.MessageObject) r139;
    L_0x0580:
        r0 = r139;
        r5 = r0.messageOwner;
        r6 = r5.grouped_id;
        r0 = r124;
        r5 = r0.messageOwner;
        r12 = r5.grouped_id;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 != 0) goto L_0x07bf;
    L_0x0590:
        r0 = r139;
        r6 = r0.localGroupId;
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x05b4;
    L_0x059a:
        r0 = r139;
        r6 = r0.localGroupId;
        r0 = r124;
        r0.localGroupId = r6;
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r0 = r139;
        r6 = r0.localGroupId;
        r6 = java.lang.Long.valueOf(r6);
        r72 = r5.get(r6);
        r72 = (org.telegram.messenger.MessageObject.GroupedMessages) r72;
    L_0x05b4:
        if (r72 != 0) goto L_0x07dd;
    L_0x05b6:
        r72 = new org.telegram.messenger.MessageObject$GroupedMessages;
        r72.<init>();
        r6 = r124.getGroupId();
        r0 = r72;
        r0.groupId = r6;
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r0 = r72;
        r6 = r0.groupId;
        r6 = java.lang.Long.valueOf(r6);
        r0 = r72;
        r5.put(r6, r0);
    L_0x05d4:
        if (r119 != 0) goto L_0x05db;
    L_0x05d6:
        r119 = new java.util.HashMap;
        r119.<init>();
    L_0x05db:
        r0 = r72;
        r6 = r0.groupId;
        r5 = java.lang.Long.valueOf(r6);
        r0 = r119;
        r1 = r72;
        r0.put(r5, r1);
        r5 = 1;
        r0 = r98;
        if (r0 != r5) goto L_0x0807;
    L_0x05ef:
        r0 = r72;
        r5 = r0.messages;
        r0 = r124;
        r5.add(r0);
    L_0x05f8:
        r122 = r122 + 1;
        r0 = r58;
        r1 = r124;
        r0.add(r1);
        r5 = 1;
        r0 = r98;
        if (r0 != r5) goto L_0x0829;
    L_0x0606:
        r0 = r167;
        r5 = r0.messages;
        r6 = 0;
        r0 = r124;
        r5.add(r6, r0);
    L_0x0610:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x0842;
    L_0x0616:
        r0 = r167;
        r5 = r0.createUnreadMessageAfterId;
        if (r5 == 0) goto L_0x083e;
    L_0x061c:
        r5 = 1;
        r0 = r98;
        if (r0 == r5) goto L_0x083e;
    L_0x0621:
        r5 = r4 + 1;
        r6 = r105.size();
        if (r5 >= r6) goto L_0x083e;
    L_0x0629:
        r5 = r4 + 1;
        r0 = r105;
        r138 = r0.get(r5);
        r138 = (org.telegram.messenger.MessageObject) r138;
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x0643;
    L_0x0639:
        r5 = r138.getId();
        r0 = r167;
        r6 = r0.createUnreadMessageAfterId;
        if (r5 < r6) goto L_0x0645;
    L_0x0643:
        r138 = 0;
    L_0x0645:
        r5 = 2;
        r0 = r98;
        if (r0 != r5) goto L_0x0873;
    L_0x064a:
        r5 = r124.getId();
        r0 = r167;
        r6 = r0.first_unread_id;
        if (r5 != r6) goto L_0x0873;
    L_0x0654:
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.y;
        r5 = r5 / 2;
        r0 = r24;
        if (r0 > r5) goto L_0x0667;
    L_0x065e:
        r0 = r167;
        r5 = r0.forwardEndReached;
        r6 = 0;
        r5 = r5[r6];
        if (r5 != 0) goto L_0x06b6;
    L_0x0667:
        r55 = new org.telegram.tgnet.TLRPC$TL_message;
        r55.<init>();
        r5 = "";
        r0 = r55;
        r0.message = r5;
        r5 = 0;
        r0 = r55;
        r0.id = r5;
        r56 = new org.telegram.messenger.MessageObject;
        r5 = 0;
        r6 = 0;
        r0 = r56;
        r1 = r55;
        r0.<init>(r1, r5, r6);
        r5 = 6;
        r0 = r56;
        r0.type = r5;
        r5 = 2;
        r0 = r56;
        r0.contentType = r5;
        r0 = r167;
        r5 = r0.messages;
        r0 = r167;
        r6 = r0.messages;
        r6 = r6.size();
        r6 = r6 + -1;
        r0 = r56;
        r5.add(r6, r0);
        r0 = r56;
        r1 = r167;
        r1.unreadMessageObject = r0;
        r0 = r167;
        r5 = r0.unreadMessageObject;
        r0 = r167;
        r0.scrollToMessage = r5;
        r5 = -10000; // 0xffffffffffffd8f0 float:NaN double:NaN;
        r0 = r167;
        r0.scrollToMessagePosition = r5;
        r122 = r122 + 1;
    L_0x06b6:
        r5 = 2;
        r0 = r98;
        if (r0 == r5) goto L_0x03eb;
    L_0x06bb:
        r0 = r167;
        r5 = r0.unreadMessageObject;
        if (r5 != 0) goto L_0x03eb;
    L_0x06c1:
        r0 = r167;
        r5 = r0.createUnreadMessageAfterId;
        if (r5 == 0) goto L_0x03eb;
    L_0x06c7:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x06dd;
    L_0x06cd:
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x06dd;
    L_0x06d3:
        r5 = r124.getId();
        r0 = r167;
        r6 = r0.createUnreadMessageAfterId;
        if (r5 >= r6) goto L_0x06f3;
    L_0x06dd:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x03eb;
    L_0x06e3:
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x03eb;
    L_0x06e9:
        r5 = r124.getId();
        r0 = r167;
        r6 = r0.createUnreadMessageAfterId;
        if (r5 > r6) goto L_0x03eb;
    L_0x06f3:
        r5 = 1;
        r0 = r98;
        if (r0 == r5) goto L_0x0706;
    L_0x06f8:
        if (r138 != 0) goto L_0x0706;
    L_0x06fa:
        if (r138 != 0) goto L_0x03eb;
    L_0x06fc:
        if (r48 == 0) goto L_0x03eb;
    L_0x06fe:
        r5 = r105.size();
        r5 = r5 + -1;
        if (r4 != r5) goto L_0x03eb;
    L_0x0706:
        r55 = new org.telegram.tgnet.TLRPC$TL_message;
        r55.<init>();
        r5 = "";
        r0 = r55;
        r0.message = r5;
        r5 = 0;
        r0 = r55;
        r0.id = r5;
        r56 = new org.telegram.messenger.MessageObject;
        r5 = 0;
        r6 = 0;
        r0 = r56;
        r1 = r55;
        r0.<init>(r1, r5, r6);
        r5 = 6;
        r0 = r56;
        r0.type = r5;
        r5 = 2;
        r0 = r56;
        r0.contentType = r5;
        r5 = 1;
        r0 = r98;
        if (r0 != r5) goto L_0x08b8;
    L_0x0731:
        r0 = r167;
        r5 = r0.messages;
        r6 = 1;
        r0 = r56;
        r5.add(r6, r0);
    L_0x073b:
        r0 = r56;
        r1 = r167;
        r1.unreadMessageObject = r0;
        r5 = 3;
        r0 = r98;
        if (r0 != r5) goto L_0x0759;
    L_0x0746:
        r0 = r167;
        r5 = r0.unreadMessageObject;
        r0 = r167;
        r0.scrollToMessage = r5;
        r5 = 0;
        r0 = r167;
        r0.startLoadFromMessageId = r5;
        r5 = -9000; // 0xffffffffffffdcd8 float:NaN double:NaN;
        r0 = r167;
        r0.scrollToMessagePosition = r5;
    L_0x0759:
        r122 = r122 + 1;
        goto L_0x03eb;
    L_0x075d:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x0445;
    L_0x0763:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = r124.getId();
        r0 = r167;
        r7 = r0.maxMessageId;
        r7 = r7[r97];
        r6 = java.lang.Math.max(r6, r7);
        r5[r97] = r6;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = r124.getId();
        r0 = r167;
        r7 = r0.minMessageId;
        r7 = r7[r97];
        r6 = java.lang.Math.min(r6, r7);
        r5[r97] = r6;
        goto L_0x0445;
    L_0x078d:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r6 = (long) r5;
        r5 = getPersianDate(r6);
        r0 = r55;
        r0.message = r5;
        goto L_0x050e;
    L_0x079e:
        r0 = r167;
        r5 = r0.messages;
        r0 = r56;
        r5.add(r0);
        goto L_0x0547;
    L_0x07a9:
        r0 = r167;
        r5 = r0.messages;
        r0 = r167;
        r6 = r0.messages;
        r6 = r6.size();
        r6 = r6 + -2;
        r139 = r5.get(r6);
        r139 = (org.telegram.messenger.MessageObject) r139;
        goto L_0x0580;
    L_0x07bf:
        r0 = r139;
        r5 = r0.messageOwner;
        r6 = r5.grouped_id;
        r0 = r124;
        r5 = r0.messageOwner;
        r12 = r5.grouped_id;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x05b4;
    L_0x07cf:
        r5 = org.telegram.messenger.Utilities.random;
        r6 = r5.nextLong();
        r0 = r124;
        r0.localGroupId = r6;
        r72 = 0;
        goto L_0x05b4;
    L_0x07dd:
        if (r119 == 0) goto L_0x07ef;
    L_0x07df:
        r6 = r124.getGroupId();
        r5 = java.lang.Long.valueOf(r6);
        r0 = r119;
        r5 = r0.containsKey(r5);
        if (r5 != 0) goto L_0x05d4;
    L_0x07ef:
        if (r40 != 0) goto L_0x07f6;
    L_0x07f1:
        r40 = new java.util.HashMap;
        r40.<init>();
    L_0x07f6:
        r6 = r124.getGroupId();
        r5 = java.lang.Long.valueOf(r6);
        r0 = r40;
        r1 = r72;
        r0.put(r5, r1);
        goto L_0x05d4;
    L_0x0807:
        r0 = r72;
        r5 = r0.messages;
        r6 = 0;
        r0 = r124;
        r5.add(r6, r0);
        goto L_0x05f8;
    L_0x0813:
        r0 = r124;
        r5 = r0.messageOwner;
        r6 = r5.grouped_id;
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x05f8;
    L_0x081f:
        r0 = r124;
        r5 = r0.messageOwner;
        r6 = 0;
        r5.grouped_id = r6;
        goto L_0x05f8;
    L_0x0829:
        r0 = r167;
        r5 = r0.messages;
        r0 = r167;
        r6 = r0.messages;
        r6 = r6.size();
        r6 = r6 + -1;
        r0 = r124;
        r5.add(r6, r0);
        goto L_0x0610;
    L_0x083e:
        r138 = 0;
        goto L_0x0645;
    L_0x0842:
        r0 = r167;
        r5 = r0.createUnreadMessageAfterId;
        if (r5 == 0) goto L_0x086f;
    L_0x0848:
        r5 = 1;
        r0 = r98;
        if (r0 == r5) goto L_0x086f;
    L_0x084d:
        r5 = r4 + -1;
        if (r5 < 0) goto L_0x086f;
    L_0x0851:
        r5 = r4 + -1;
        r0 = r105;
        r138 = r0.get(r5);
        r138 = (org.telegram.messenger.MessageObject) r138;
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x086b;
    L_0x0861:
        r5 = r138.getId();
        r0 = r167;
        r6 = r0.createUnreadMessageAfterId;
        if (r5 < r6) goto L_0x0645;
    L_0x086b:
        r138 = 0;
        goto L_0x0645;
    L_0x086f:
        r138 = 0;
        goto L_0x0645;
    L_0x0873:
        r5 = 3;
        r0 = r98;
        if (r0 == r5) goto L_0x087d;
    L_0x0878:
        r5 = 4;
        r0 = r98;
        if (r0 != r5) goto L_0x06b6;
    L_0x087d:
        r5 = r124.getId();
        r0 = r167;
        r6 = r0.startLoadFromMessageId;
        if (r5 != r6) goto L_0x06b6;
    L_0x0887:
        r0 = r167;
        r5 = r0.needSelectFromMessageId;
        if (r5 == 0) goto L_0x08b0;
    L_0x088d:
        r5 = r124.getId();
        r0 = r167;
        r0.highlightMessageId = r5;
    L_0x0895:
        r0 = r124;
        r1 = r167;
        r1.scrollToMessage = r0;
        r5 = 0;
        r0 = r167;
        r0.startLoadFromMessageId = r5;
        r0 = r167;
        r5 = r0.scrollToMessagePosition;
        r6 = -10000; // 0xffffffffffffd8f0 float:NaN double:NaN;
        if (r5 != r6) goto L_0x06b6;
    L_0x08a8:
        r5 = -9000; // 0xffffffffffffdcd8 float:NaN double:NaN;
        r0 = r167;
        r0.scrollToMessagePosition = r5;
        goto L_0x06b6;
    L_0x08b0:
        r5 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r0 = r167;
        r0.highlightMessageId = r5;
        goto L_0x0895;
    L_0x08b8:
        r0 = r167;
        r5 = r0.messages;
        r0 = r167;
        r6 = r0.messages;
        r6 = r6.size();
        r6 = r6 + -1;
        r0 = r56;
        r5.add(r6, r0);
        goto L_0x073b;
    L_0x08cd:
        if (r48 == 0) goto L_0x08d4;
    L_0x08cf:
        r5 = 0;
        r0 = r167;
        r0.createUnreadMessageAfterId = r5;
    L_0x08d4:
        if (r98 != 0) goto L_0x08e2;
    L_0x08d6:
        if (r122 != 0) goto L_0x08e2;
    L_0x08d8:
        r0 = r167;
        r5 = r0.loadsCount;
        r5 = r5 + -1;
        r0 = r167;
        r0.loadsCount = r5;
    L_0x08e2:
        if (r119 == 0) goto L_0x094f;
    L_0x08e4:
        r5 = r119.entrySet();
        r5 = r5.iterator();
    L_0x08ec:
        r6 = r5.hasNext();
        if (r6 == 0) goto L_0x094f;
    L_0x08f2:
        r64 = r5.next();
        r64 = (java.util.Map.Entry) r64;
        r72 = r64.getValue();
        r72 = (org.telegram.messenger.MessageObject.GroupedMessages) r72;
        r72.calculate();
        r0 = r167;
        r6 = r0.chatAdapter;
        if (r6 == 0) goto L_0x08ec;
    L_0x0907:
        if (r40 == 0) goto L_0x08ec;
    L_0x0909:
        r6 = r64.getKey();
        r0 = r40;
        r6 = r0.containsKey(r6);
        if (r6 == 0) goto L_0x08ec;
    L_0x0915:
        r0 = r72;
        r6 = r0.messages;
        r0 = r72;
        r7 = r0.messages;
        r7 = r7.size();
        r7 = r7 + -1;
        r107 = r6.get(r7);
        r107 = (org.telegram.messenger.MessageObject) r107;
        r0 = r167;
        r6 = r0.messages;
        r0 = r107;
        r78 = r6.indexOf(r0);
        if (r78 < 0) goto L_0x08ec;
    L_0x0935:
        r0 = r167;
        r6 = r0.chatAdapter;
        r0 = r167;
        r7 = r0.chatAdapter;
        r7 = r7.messagesStartRow;
        r7 = r7 + r78;
        r0 = r72;
        r8 = r0.messages;
        r8 = r8.size();
        r6.notifyItemRangeChanged(r7, r8);
        goto L_0x08ec;
    L_0x094f:
        r0 = r167;
        r5 = r0.forwardEndReached;
        r5 = r5[r97];
        if (r5 == 0) goto L_0x096b;
    L_0x0957:
        r5 = 1;
        r0 = r97;
        if (r0 == r5) goto L_0x096b;
    L_0x095c:
        r5 = 0;
        r0 = r167;
        r0.first_unread_id = r5;
        r5 = 0;
        r0 = r167;
        r0.last_message_id = r5;
        r5 = 0;
        r0 = r167;
        r0.createUnreadMessageAfterId = r5;
    L_0x096b:
        r5 = 1;
        r0 = r98;
        if (r0 != r5) goto L_0x0b1a;
    L_0x0970:
        r145 = 0;
        r5 = r105.size();
        r0 = r47;
        if (r5 == r0) goto L_0x09bb;
    L_0x097a:
        if (r86 == 0) goto L_0x098a;
    L_0x097c:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x098a;
    L_0x0982:
        r0 = r167;
        r5 = r0.forwardEndReached;
        r5 = r5[r97];
        if (r5 == 0) goto L_0x09bb;
    L_0x098a:
        r0 = r167;
        r5 = r0.forwardEndReached;
        r6 = 1;
        r5[r97] = r6;
        r5 = 1;
        r0 = r97;
        if (r0 == r5) goto L_0x09b6;
    L_0x0996:
        r5 = 0;
        r0 = r167;
        r0.first_unread_id = r5;
        r5 = 0;
        r0 = r167;
        r0.last_message_id = r5;
        r5 = 0;
        r0 = r167;
        r0.createUnreadMessageAfterId = r5;
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r167;
        r6 = r0.chatAdapter;
        r6 = r6.loadingDownRow;
        r5.notifyItemRemoved(r6);
        r145 = r145 + 1;
    L_0x09b6:
        r5 = 0;
        r0 = r167;
        r0.startLoadFromMessageId = r5;
    L_0x09bb:
        if (r122 <= 0) goto L_0x09f5;
    L_0x09bd:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r68 = r5.findFirstVisibleItemPosition();
        r150 = 0;
        if (r68 != 0) goto L_0x09cb;
    L_0x09c9:
        r68 = r68 + 1;
    L_0x09cb:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r0 = r68;
        r69 = r5.findViewByPosition(r0);
        if (r69 != 0) goto L_0x0b01;
    L_0x09d7:
        r150 = 0;
    L_0x09d9:
        r0 = r167;
        r5 = r0.chatAdapter;
        r6 = 1;
        r0 = r122;
        r5.notifyItemRangeInserted(r6, r0);
        r5 = -1;
        r0 = r68;
        if (r0 == r5) goto L_0x09f5;
    L_0x09e8:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r6 = r68 + r122;
        r6 = r6 - r145;
        r0 = r150;
        r5.scrollToPositionWithOffset(r6, r0);
    L_0x09f5:
        r5 = 0;
        r0 = r167;
        r0.loadingForward = r5;
    L_0x09fa:
        r0 = r167;
        r5 = r0.first;
        if (r5 == 0) goto L_0x0a3c;
    L_0x0a00:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.size();
        if (r5 <= 0) goto L_0x0a3c;
    L_0x0a0a:
        if (r97 != 0) goto L_0x0a37;
    L_0x0a0c:
        r164 = r163;
        r94 = r93;
        r0 = r167;
        r5 = r0.messages;
        r6 = 0;
        r5 = r5.get(r6);
        r5 = (org.telegram.messenger.MessageObject) r5;
        r95 = r5.getId();
        r0 = r167;
        r5 = r0.isAdvancedForward;
        if (r5 != 0) goto L_0x0a37;
    L_0x0a25:
        r5 = new org.telegram.ui.ChatActivity$91;
        r0 = r167;
        r1 = r95;
        r2 = r94;
        r3 = r164;
        r5.<init>(r1, r2, r3);
        r6 = 700; // 0x2bc float:9.81E-43 double:3.46E-321;
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r5, r6);
    L_0x0a37:
        r5 = 0;
        r0 = r167;
        r0.first = r5;
    L_0x0a3c:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.isEmpty();
        if (r5 == 0) goto L_0x0a6a;
    L_0x0a46:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x0a6a;
    L_0x0a4c:
        r0 = r167;
        r5 = r0.currentUser;
        if (r5 == 0) goto L_0x0a6a;
    L_0x0a52:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.bot;
        if (r5 == 0) goto L_0x0a6a;
    L_0x0a5a:
        r0 = r167;
        r5 = r0.botUser;
        if (r5 != 0) goto L_0x0a6a;
    L_0x0a60:
        r5 = "";
        r0 = r167;
        r0.botUser = r5;
        r167.updateBottomOverlay();
    L_0x0a6a:
        if (r122 != 0) goto L_0x0e0a;
    L_0x0a6c:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x0e0a;
    L_0x0a72:
        r0 = r167;
        r5 = r0.endReached;
        r6 = 0;
        r5 = r5[r6];
        if (r5 != 0) goto L_0x0e0a;
    L_0x0a7b:
        r5 = 1;
        r0 = r167;
        r0.first = r5;
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x0a8e;
    L_0x0a86:
        r0 = r167;
        r5 = r0.chatListView;
        r6 = 0;
        r5.setEmptyView(r6);
    L_0x0a8e:
        r0 = r167;
        r5 = r0.emptyViewContainer;
        if (r5 == 0) goto L_0x0a9c;
    L_0x0a94:
        r0 = r167;
        r5 = r0.emptyViewContainer;
        r6 = 4;
        r5.setVisibility(r6);
    L_0x0a9c:
        r5 = 0;
        r0 = r167;
        r0.checkScrollForLoad(r5);
    L_0x0aa2:
        r0 = r167;
        r5 = r0.isAdvancedForward;
        if (r5 == 0) goto L_0x0ab9;
    L_0x0aa8:
        r5 = new android.os.Handler;
        r5.<init>();
        r6 = new org.telegram.ui.ChatActivity$92;
        r0 = r167;
        r6.<init>();
        r12 = 100;
        r5.postDelayed(r6, r12);
    L_0x0ab9:
        r0 = r167;
        r5 = r0.isDownloadManager;
        if (r5 == 0) goto L_0x0af6;
    L_0x0abf:
        r0 = r167;
        r5 = r0.messages;	 Catch:{ Exception -> 0x3a0c }
        r76 = r5.size();	 Catch:{ Exception -> 0x3a0c }
        if (r76 == 0) goto L_0x0acb;
    L_0x0ac9:
        r76 = r76 + -1;
    L_0x0acb:
        r0 = r167;
        r5 = r0.avatarContainer;	 Catch:{ Exception -> 0x3a0c }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x3a0c }
        r6.<init>();	 Catch:{ Exception -> 0x3a0c }
        r7 = "downloadCount";
        r8 = 2131232709; // 0x7f0807c5 float:1.8081535E38 double:1.052968865E-314;
        r7 = org.telegram.messenger.LocaleController.getString(r7, r8);	 Catch:{ Exception -> 0x3a0c }
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x3a0c }
        r7 = " : ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x3a0c }
        r0 = r76;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x3a0c }
        r6 = r6.toString();	 Catch:{ Exception -> 0x3a0c }
        r5.setSubtitle(r6);	 Catch:{ Exception -> 0x3a0c }
    L_0x0af6:
        r5 = "LEE";
        r6 = "Debug1946 didReceivedNotification() success";
        android.util.Log.d(r5, r6);
        goto L_0x0015;
    L_0x0b01:
        r0 = r167;
        r5 = r0.chatListView;
        r5 = r5.getMeasuredHeight();
        r6 = r69.getBottom();
        r5 = r5 - r6;
        r0 = r167;
        r6 = r0.chatListView;
        r6 = r6.getPaddingBottom();
        r150 = r5 - r6;
        goto L_0x09d9;
    L_0x0b1a:
        r5 = r105.size();
        r0 = r47;
        if (r5 >= r0) goto L_0x0b4d;
    L_0x0b22:
        r5 = 3;
        r0 = r98;
        if (r0 == r5) goto L_0x0b4d;
    L_0x0b27:
        r5 = 4;
        r0 = r98;
        if (r0 == r5) goto L_0x0b4d;
    L_0x0b2c:
        if (r86 == 0) goto L_0x0ca6;
    L_0x0b2e:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x0b3a;
    L_0x0b34:
        r0 = r167;
        r5 = r0.isBroadcast;
        if (r5 == 0) goto L_0x0b41;
    L_0x0b3a:
        r0 = r167;
        r5 = r0.endReached;
        r6 = 1;
        r5[r97] = r6;
    L_0x0b41:
        r5 = 2;
        r0 = r98;
        if (r0 == r5) goto L_0x0b4d;
    L_0x0b46:
        r0 = r167;
        r5 = r0.cacheEndReached;
        r6 = 1;
        r5[r97] = r6;
    L_0x0b4d:
        r5 = 0;
        r0 = r167;
        r0.loading = r5;
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x0df8;
    L_0x0b58:
        r0 = r167;
        r5 = r0.first;
        if (r5 != 0) goto L_0x0b6a;
    L_0x0b5e:
        r0 = r167;
        r5 = r0.scrollToTopOnResume;
        if (r5 != 0) goto L_0x0b6a;
    L_0x0b64:
        r0 = r167;
        r5 = r0.forceScrollToTop;
        if (r5 == 0) goto L_0x0d26;
    L_0x0b6a:
        r5 = 0;
        r0 = r167;
        r0.forceScrollToTop = r5;
        r0 = r167;
        r5 = r0.chatAdapter;
        r5.notifyDataSetChanged();
        r0 = r167;
        r5 = r0.scrollToMessage;
        if (r5 == 0) goto L_0x0d21;
    L_0x0b7c:
        r33 = 1;
        r0 = r167;
        r5 = r0.startLoadFromMessageOffset;
        r6 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        if (r5 == r6) goto L_0x0cc4;
    L_0x0b87:
        r0 = r167;
        r5 = r0.startLoadFromMessageOffset;
        r5 = -r5;
        r0 = r167;
        r6 = r0.chatListView;
        r6 = r6.getPaddingBottom();
        r166 = r5 - r6;
        r5 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r0 = r167;
        r0.startLoadFromMessageOffset = r5;
    L_0x0b9d:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.isEmpty();
        if (r5 != 0) goto L_0x0bea;
    L_0x0ba7:
        r0 = r167;
        r5 = r0.messages;
        r0 = r167;
        r6 = r0.messages;
        r6 = r6.size();
        r6 = r6 + -1;
        r5 = r5.get(r6);
        r0 = r167;
        r6 = r0.scrollToMessage;
        if (r5 == r6) goto L_0x0bd7;
    L_0x0bbf:
        r0 = r167;
        r5 = r0.messages;
        r0 = r167;
        r6 = r0.messages;
        r6 = r6.size();
        r6 = r6 + -2;
        r5 = r5.get(r6);
        r0 = r167;
        r6 = r0.scrollToMessage;
        if (r5 != r6) goto L_0x0cff;
    L_0x0bd7:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r0 = r167;
        r6 = r0.chatAdapter;
        r6 = r6.loadingUpRow;
        r0 = r166;
        r1 = r33;
        r5.scrollToPositionWithOffset(r6, r0, r1);
    L_0x0bea:
        r0 = r167;
        r5 = r0.chatListView;
        r5.invalidate();
        r0 = r167;
        r5 = r0.scrollToMessagePosition;
        r6 = -10000; // 0xffffffffffffd8f0 float:NaN double:NaN;
        if (r5 == r6) goto L_0x0c01;
    L_0x0bf9:
        r0 = r167;
        r5 = r0.scrollToMessagePosition;
        r6 = -9000; // 0xffffffffffffdcd8 float:NaN double:NaN;
        if (r5 != r6) goto L_0x0c3d;
    L_0x0c01:
        r5 = 1;
        r6 = 1;
        r0 = r167;
        r0.showPagedownButton(r5, r6);
        r5 = 3;
        r0 = r98;
        if (r0 != r5) goto L_0x0c3d;
    L_0x0c0d:
        r0 = r167;
        r5 = r0.unread_to_load;
        if (r5 == 0) goto L_0x0c3d;
    L_0x0c13:
        if (r146 == 0) goto L_0x0c3d;
    L_0x0c15:
        r0 = r167;
        r5 = r0.pagedownButtonCounter;
        r6 = 0;
        r5.setVisibility(r6);
        r0 = r167;
        r5 = r0.pagedownButtonCounter;
        r6 = "%d";
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r0 = r167;
        r11 = r0.unread_to_load;
        r0 = r167;
        r0.newUnreadMessageCount = r11;
        r11 = java.lang.Integer.valueOf(r11);
        r7[r8] = r11;
        r6 = java.lang.String.format(r6, r7);
        r5.setText(r6);
    L_0x0c3d:
        r5 = -10000; // 0xffffffffffffd8f0 float:NaN double:NaN;
        r0 = r167;
        r0.scrollToMessagePosition = r5;
        r5 = 0;
        r0 = r167;
        r0.scrollToMessage = r5;
    L_0x0c48:
        if (r100 == 0) goto L_0x0c77;
    L_0x0c4a:
        r5 = 1;
        r6 = 1;
        r0 = r167;
        r0.showMentiondownButton(r5, r6);
        r0 = r167;
        r5 = r0.mentiondownButtonCounter;
        r6 = 0;
        r5.setVisibility(r6);
        r0 = r167;
        r5 = r0.mentiondownButtonCounter;
        r6 = "%d";
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r0 = r100;
        r1 = r167;
        r1.newMentionsCount = r0;
        r11 = java.lang.Integer.valueOf(r100);
        r7[r8] = r11;
        r6 = java.lang.String.format(r6, r7);
        r5.setText(r6);
    L_0x0c77:
        r0 = r167;
        r5 = r0.paused;
        if (r5 == 0) goto L_0x0c8d;
    L_0x0c7d:
        r5 = 1;
        r0 = r167;
        r0.scrollToTopOnResume = r5;
        r0 = r167;
        r5 = r0.scrollToMessage;
        if (r5 == 0) goto L_0x0c8d;
    L_0x0c88:
        r5 = 1;
        r0 = r167;
        r0.scrollToTopUnReadOnResume = r5;
    L_0x0c8d:
        r0 = r167;
        r5 = r0.first;
        if (r5 == 0) goto L_0x09fa;
    L_0x0c93:
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x09fa;
    L_0x0c99:
        r0 = r167;
        r5 = r0.chatListView;
        r0 = r167;
        r6 = r0.emptyViewContainer;
        r5.setEmptyView(r6);
        goto L_0x09fa;
    L_0x0ca6:
        r5 = 2;
        r0 = r98;
        if (r0 != r5) goto L_0x0cbb;
    L_0x0cab:
        r5 = r105.size();
        if (r5 != 0) goto L_0x0b4d;
    L_0x0cb1:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.isEmpty();
        if (r5 == 0) goto L_0x0b4d;
    L_0x0cbb:
        r0 = r167;
        r5 = r0.endReached;
        r6 = 1;
        r5[r97] = r6;
        goto L_0x0b4d;
    L_0x0cc4:
        r0 = r167;
        r5 = r0.scrollToMessagePosition;
        r6 = -9000; // 0xffffffffffffdcd8 float:NaN double:NaN;
        if (r5 != r6) goto L_0x0cda;
    L_0x0ccc:
        r0 = r167;
        r5 = r0.scrollToMessage;
        r0 = r167;
        r166 = r0.getScrollOffsetForMessage(r5);
        r33 = 0;
        goto L_0x0b9d;
    L_0x0cda:
        r0 = r167;
        r5 = r0.scrollToMessagePosition;
        r6 = -10000; // 0xffffffffffffd8f0 float:NaN double:NaN;
        if (r5 != r6) goto L_0x0cf7;
    L_0x0ce2:
        r0 = r167;
        r5 = r0.chatListView;
        r5 = r5.getPaddingTop();
        r5 = -r5;
        r6 = 1088421888; // 0x40e00000 float:7.0 double:5.37751863E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r166 = r5 - r6;
        r33 = 0;
        goto L_0x0b9d;
    L_0x0cf7:
        r0 = r167;
        r0 = r0.scrollToMessagePosition;
        r166 = r0;
        goto L_0x0b9d;
    L_0x0cff:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r0 = r167;
        r6 = r0.chatAdapter;
        r6 = r6.messagesStartRow;
        r0 = r167;
        r7 = r0.messages;
        r0 = r167;
        r8 = r0.scrollToMessage;
        r7 = r7.indexOf(r8);
        r6 = r6 + r7;
        r0 = r166;
        r1 = r33;
        r5.scrollToPositionWithOffset(r6, r0, r1);
        goto L_0x0bea;
    L_0x0d21:
        r167.moveScrollToLastMessage();
        goto L_0x0c48;
    L_0x0d26:
        if (r122 == 0) goto L_0x0dce;
    L_0x0d28:
        r63 = 0;
        r0 = r167;
        r5 = r0.endReached;
        r5 = r5[r97];
        if (r5 == 0) goto L_0x0d5e;
    L_0x0d32:
        if (r97 != 0) goto L_0x0d3e;
    L_0x0d34:
        r0 = r167;
        r6 = r0.mergeDialogId;
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x0d43;
    L_0x0d3e:
        r5 = 1;
        r0 = r97;
        if (r0 != r5) goto L_0x0d5e;
    L_0x0d43:
        r63 = 1;
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r167;
        r6 = r0.chatAdapter;
        r6 = r6.loadingUpRow;
        r6 = r6 + -1;
        r7 = 2;
        r5.notifyItemRangeChanged(r6, r7);
        r0 = r167;
        r5 = r0.chatAdapter;
        r5.updateRows();
    L_0x0d5e:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r68 = r5.findFirstVisibleItemPosition();
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r0 = r68;
        r69 = r5.findViewByPosition(r0);
        if (r69 != 0) goto L_0x0db2;
    L_0x0d72:
        r150 = 0;
    L_0x0d74:
        if (r63 == 0) goto L_0x0dca;
    L_0x0d76:
        r5 = 1;
    L_0x0d77:
        r5 = r122 - r5;
        if (r5 <= 0) goto L_0x0da0;
    L_0x0d7b:
        r0 = r167;
        r5 = r0.chatAdapter;
        r84 = r5.messagesEndRow;
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r167;
        r6 = r0.chatAdapter;
        r6 = r6.loadingUpRow;
        r5.notifyItemChanged(r6);
        r0 = r167;
        r6 = r0.chatAdapter;
        if (r63 == 0) goto L_0x0dcc;
    L_0x0d98:
        r5 = 1;
    L_0x0d99:
        r5 = r122 - r5;
        r0 = r84;
        r6.notifyItemRangeInserted(r0, r5);
    L_0x0da0:
        r5 = -1;
        r0 = r68;
        if (r0 == r5) goto L_0x0c77;
    L_0x0da5:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r0 = r68;
        r1 = r150;
        r5.scrollToPositionWithOffset(r0, r1);
        goto L_0x0c77;
    L_0x0db2:
        r0 = r167;
        r5 = r0.chatListView;
        r5 = r5.getMeasuredHeight();
        r6 = r69.getBottom();
        r5 = r5 - r6;
        r0 = r167;
        r6 = r0.chatListView;
        r6 = r6.getPaddingBottom();
        r150 = r5 - r6;
        goto L_0x0d74;
    L_0x0dca:
        r5 = 0;
        goto L_0x0d77;
    L_0x0dcc:
        r5 = 0;
        goto L_0x0d99;
    L_0x0dce:
        r0 = r167;
        r5 = r0.endReached;
        r5 = r5[r97];
        if (r5 == 0) goto L_0x0c77;
    L_0x0dd6:
        if (r97 != 0) goto L_0x0de2;
    L_0x0dd8:
        r0 = r167;
        r6 = r0.mergeDialogId;
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x0de7;
    L_0x0de2:
        r5 = 1;
        r0 = r97;
        if (r0 != r5) goto L_0x0c77;
    L_0x0de7:
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r167;
        r6 = r0.chatAdapter;
        r6 = r6.loadingUpRow;
        r5.notifyItemRemoved(r6);
        goto L_0x0c77;
    L_0x0df8:
        r5 = 1;
        r0 = r167;
        r0.scrollToTopOnResume = r5;
        r0 = r167;
        r5 = r0.scrollToMessage;
        if (r5 == 0) goto L_0x09fa;
    L_0x0e03:
        r5 = 1;
        r0 = r167;
        r0.scrollToTopUnReadOnResume = r5;
        goto L_0x09fa;
    L_0x0e0a:
        r0 = r167;
        r5 = r0.progressView;
        if (r5 == 0) goto L_0x0a9c;
    L_0x0e10:
        r0 = r167;
        r5 = r0.progressView;
        r6 = 4;
        r5.setVisibility(r6);
        goto L_0x0a9c;
    L_0x0e1a:
        r5 = org.telegram.messenger.NotificationCenter.paymentSuccessMessage;
        r0 = r168;
        if (r0 != r5) goto L_0x0e37;
    L_0x0e20:
        r5 = 0;
        r106 = r169[r5];
        r106 = (java.lang.String) r106;
        r5 = "LEE";
        r6 = "NotificationCenter payment";
        android.util.Log.d(r5, r6);
        r0 = r167;
        r1 = r106;
        r0.sendMessage(r1);
        goto L_0x0015;
    L_0x0e37:
        r5 = org.telegram.messenger.NotificationCenter.goToPayment;
        r0 = r168;
        if (r0 != r5) goto L_0x0e91;
    L_0x0e3d:
        r5 = 0;
        r106 = r169[r5];
        r106 = (java.lang.String) r106;
        r96 = r106;
        r5 = "/";
        r0 = r96;
        r80 = r0.lastIndexOf(r5);
        r5 = r80 + 1;
        r0 = r96;
        r133 = r0.substring(r5);
        r5 = "alireza";
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "alireza payment id : ";
        r6 = r6.append(r7);
        r0 = r133;
        r6 = r6.append(r0);
        r6 = r6.toString();
        android.util.Log.d(r5, r6);
        r85 = new android.content.Intent;
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r6 = org.telegram.customization.Activities.PaymentConfirmActivity.class;
        r0 = r85;
        r0.<init>(r5, r6);
        r5 = "EXTRA_PAYMENT_ID";
        r0 = r85;
        r1 = r133;
        r0.putExtra(r5, r1);
        r5 = r167.getParentActivity();
        r0 = r85;
        r5.startActivity(r0);
        goto L_0x0015;
    L_0x0e91:
        r5 = org.telegram.messenger.NotificationCenter.emojiDidLoaded;
        r0 = r168;
        if (r0 != r5) goto L_0x0eda;
    L_0x0e97:
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x0ea4;
    L_0x0e9d:
        r0 = r167;
        r5 = r0.chatListView;
        r5.invalidateViews();
    L_0x0ea4:
        r0 = r167;
        r5 = r0.replyObjectTextView;
        if (r5 == 0) goto L_0x0eb1;
    L_0x0eaa:
        r0 = r167;
        r5 = r0.replyObjectTextView;
        r5.invalidate();
    L_0x0eb1:
        r0 = r167;
        r5 = r0.alertTextView;
        if (r5 == 0) goto L_0x0ebe;
    L_0x0eb7:
        r0 = r167;
        r5 = r0.alertTextView;
        r5.invalidate();
    L_0x0ebe:
        r0 = r167;
        r5 = r0.pinnedMessageTextView;
        if (r5 == 0) goto L_0x0ecb;
    L_0x0ec4:
        r0 = r167;
        r5 = r0.pinnedMessageTextView;
        r5.invalidate();
    L_0x0ecb:
        r0 = r167;
        r5 = r0.mentionListView;
        if (r5 == 0) goto L_0x0015;
    L_0x0ed1:
        r0 = r167;
        r5 = r0.mentionListView;
        r5.invalidateViews();
        goto L_0x0015;
    L_0x0eda:
        r5 = org.telegram.messenger.NotificationCenter.updateInterfaces;
        r0 = r168;
        if (r0 != r5) goto L_0x0fcd;
    L_0x0ee0:
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r156 = r5.intValue();
        r5 = r156 & 1;
        if (r5 != 0) goto L_0x0ef1;
    L_0x0eed:
        r5 = r156 & 16;
        if (r5 == 0) goto L_0x0f14;
    L_0x0ef1:
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x0fab;
    L_0x0ef7:
        r5 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r6 = r0.currentChat;
        r6 = r6.id;
        r6 = java.lang.Integer.valueOf(r6);
        r44 = r5.getChat(r6);
        if (r44 == 0) goto L_0x0f11;
    L_0x0f0b:
        r0 = r44;
        r1 = r167;
        r1.currentChat = r0;
    L_0x0f11:
        r167.updateTitle();
    L_0x0f14:
        r157 = 0;
        r5 = r156 & 32;
        if (r5 != 0) goto L_0x0f1e;
    L_0x0f1a:
        r5 = r156 & 4;
        if (r5 == 0) goto L_0x0f33;
    L_0x0f1e:
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x0f31;
    L_0x0f24:
        r0 = r167;
        r5 = r0.avatarContainer;
        if (r5 == 0) goto L_0x0f31;
    L_0x0f2a:
        r0 = r167;
        r5 = r0.avatarContainer;
        r5.updateOnlineCount();
    L_0x0f31:
        r157 = 1;
    L_0x0f33:
        r5 = r156 & 2;
        if (r5 != 0) goto L_0x0f3f;
    L_0x0f37:
        r5 = r156 & 8;
        if (r5 != 0) goto L_0x0f3f;
    L_0x0f3b:
        r5 = r156 & 1;
        if (r5 == 0) goto L_0x0f45;
    L_0x0f3f:
        r167.checkAndUpdateAvatar();
        r167.updateVisibleRows();
    L_0x0f45:
        r5 = r156 & 64;
        if (r5 == 0) goto L_0x0f4b;
    L_0x0f49:
        r157 = 1;
    L_0x0f4b:
        r0 = r156;
        r5 = r0 & 8192;
        if (r5 == 0) goto L_0x0f8b;
    L_0x0f51:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = org.telegram.messenger.ChatObject.isChannel(r5);
        if (r5 == 0) goto L_0x0f8b;
    L_0x0f5b:
        r5 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r6 = r0.currentChat;
        r6 = r6.id;
        r6 = java.lang.Integer.valueOf(r6);
        r44 = r5.getChat(r6);
        if (r44 == 0) goto L_0x0015;
    L_0x0f6f:
        r0 = r44;
        r1 = r167;
        r1.currentChat = r0;
        r157 = 1;
        r167.updateBottomOverlay();
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        if (r5 == 0) goto L_0x0f8b;
    L_0x0f80:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r0 = r167;
        r6 = r0.dialog_id;
        r5.setDialogId(r6);
    L_0x0f8b:
        r0 = r167;
        r5 = r0.isDownloadManager;
        if (r5 != 0) goto L_0x0fa0;
    L_0x0f91:
        r0 = r167;
        r5 = r0.avatarContainer;
        if (r5 == 0) goto L_0x0fa0;
    L_0x0f97:
        if (r157 == 0) goto L_0x0fa0;
    L_0x0f99:
        r0 = r167;
        r5 = r0.avatarContainer;
        r5.updateSubtitle();
    L_0x0fa0:
        r0 = r156;
        r5 = r0 & 128;
        if (r5 == 0) goto L_0x0015;
    L_0x0fa6:
        r167.updateContactStatus();
        goto L_0x0015;
    L_0x0fab:
        r0 = r167;
        r5 = r0.currentUser;
        if (r5 == 0) goto L_0x0f11;
    L_0x0fb1:
        r5 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r6 = r0.currentUser;
        r6 = r6.id;
        r6 = java.lang.Integer.valueOf(r6);
        r160 = r5.getUser(r6);
        if (r160 == 0) goto L_0x0f11;
    L_0x0fc5:
        r0 = r160;
        r1 = r167;
        r1.currentUser = r0;
        goto L_0x0f11;
    L_0x0fcd:
        r5 = org.telegram.messenger.NotificationCenter.didReceivedNewMessages;
        r0 = r168;
        if (r0 != r5) goto L_0x1c44;
    L_0x0fd3:
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r60 = r5.longValue();
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x0fe4:
        r53 = org.telegram.messenger.UserConfig.getClientUserId();
        r154 = 0;
        r74 = 0;
        r5 = 1;
        r26 = r169[r5];
        r26 = (java.util.ArrayList) r26;
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x10ae;
    L_0x0ff7:
        r5 = r26.size();
        r6 = 1;
        if (r5 != r6) goto L_0x10ae;
    L_0x0ffe:
        r5 = 0;
        r0 = r26;
        r124 = r0.get(r5);
        r124 = (org.telegram.messenger.MessageObject) r124;
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x10ae;
    L_0x100d:
        r5 = r124.isOut();
        if (r5 == 0) goto L_0x10ae;
    L_0x1013:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        if (r5 == 0) goto L_0x10ae;
    L_0x101b:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageEncryptedAction;
        if (r5 == 0) goto L_0x10ae;
    L_0x1025:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5.encryptedAction;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL;
        if (r5 == 0) goto L_0x10ae;
    L_0x1031:
        r5 = r167.getParentActivity();
        if (r5 == 0) goto L_0x10ae;
    L_0x1037:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        r5 = r5.layer;
        r5 = org.telegram.messenger.AndroidUtilities.getPeerLayerVersion(r5);
        r6 = 17;
        if (r5 >= r6) goto L_0x10ae;
    L_0x1045:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        r5 = r5.ttl;
        if (r5 <= 0) goto L_0x10ae;
    L_0x104d:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        r5 = r5.ttl;
        r6 = 60;
        if (r5 > r6) goto L_0x10ae;
    L_0x1057:
        r34 = new org.telegram.ui.ActionBar.AlertDialog$Builder;
        r5 = r167.getParentActivity();
        r0 = r34;
        r0.<init>(r5);
        r5 = "AppName";
        r6 = 2131230884; // 0x7f0800a4 float:1.8077833E38 double:1.052967963E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r0 = r34;
        r0.setTitle(r5);
        r5 = "OK";
        r6 = 2131232017; // 0x7f080511 float:1.8080131E38 double:1.052968523E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r6 = 0;
        r0 = r34;
        r0.setPositiveButton(r5, r6);
        r5 = "CompatibilityChat";
        r6 = 2131231192; // 0x7f0801d8 float:1.8078458E38 double:1.0529681153E-314;
        r7 = 2;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r0 = r167;
        r11 = r0.currentUser;
        r11 = r11.first_name;
        r7[r8] = r11;
        r8 = 1;
        r0 = r167;
        r11 = r0.currentUser;
        r11 = r11.first_name;
        r7[r8] = r11;
        r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7);
        r0 = r34;
        r0.setMessage(r5);
        r5 = r34.create();
        r0 = r167;
        r0.showDialog(r5);
    L_0x10ae:
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 != 0) goto L_0x10be;
    L_0x10b4:
        r0 = r167;
        r6 = r0.inlineReturn;
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x121c;
    L_0x10be:
        r4 = 0;
    L_0x10bf:
        r5 = r26.size();
        if (r4 >= r5) goto L_0x121c;
    L_0x10c5:
        r0 = r26;
        r107 = r0.get(r4);
        r107 = (org.telegram.messenger.MessageObject) r107;
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x11b9;
    L_0x10d3:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser;
        if (r5 == 0) goto L_0x10e9;
    L_0x10dd:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5.user_id;
        r0 = r53;
        if (r5 == r0) goto L_0x1105;
    L_0x10e9:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser;
        if (r5 == 0) goto L_0x113b;
    L_0x10f3:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5.users;
        r6 = java.lang.Integer.valueOf(r53);
        r5 = r5.contains(r6);
        if (r5 == 0) goto L_0x113b;
    L_0x1105:
        r5 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r6 = r0.currentChat;
        r6 = r6.id;
        r6 = java.lang.Integer.valueOf(r6);
        r117 = r5.getChat(r6);
        if (r117 == 0) goto L_0x1138;
    L_0x1119:
        r0 = r117;
        r1 = r167;
        r1.currentChat = r0;
        r167.checkActionBarMenu();
        r167.updateBottomOverlay();
        r0 = r167;
        r5 = r0.isDownloadManager;
        if (r5 != 0) goto L_0x1138;
    L_0x112b:
        r0 = r167;
        r5 = r0.avatarContainer;
        if (r5 == 0) goto L_0x1138;
    L_0x1131:
        r0 = r167;
        r5 = r0.avatarContainer;
        r5.updateSubtitle();
    L_0x1138:
        r4 = r4 + 1;
        goto L_0x10bf;
    L_0x113b:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.reply_to_msg_id;
        if (r5 == 0) goto L_0x1138;
    L_0x1143:
        r0 = r107;
        r5 = r0.replyMessageObject;
        if (r5 != 0) goto L_0x1138;
    L_0x1149:
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r0 = r107;
        r6 = r0.messageOwner;
        r6 = r6.reply_to_msg_id;
        r6 = java.lang.Integer.valueOf(r6);
        r5 = r5.get(r6);
        r5 = (org.telegram.messenger.MessageObject) r5;
        r0 = r107;
        r0.replyMessageObject = r5;
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionPinMessage;
        if (r5 == 0) goto L_0x1197;
    L_0x116e:
        r5 = 0;
        r6 = 0;
        r0 = r107;
        r0.generatePinMessageText(r5, r6);
    L_0x1175:
        r5 = r107.isMegagroup();
        if (r5 == 0) goto L_0x1138;
    L_0x117b:
        r0 = r107;
        r5 = r0.replyMessageObject;
        if (r5 == 0) goto L_0x1138;
    L_0x1181:
        r0 = r107;
        r5 = r0.replyMessageObject;
        r5 = r5.messageOwner;
        if (r5 == 0) goto L_0x1138;
    L_0x1189:
        r0 = r107;
        r5 = r0.replyMessageObject;
        r5 = r5.messageOwner;
        r6 = r5.flags;
        r7 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r6 = r6 | r7;
        r5.flags = r6;
        goto L_0x1138;
    L_0x1197:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionGameScore;
        if (r5 == 0) goto L_0x11a8;
    L_0x11a1:
        r5 = 0;
        r0 = r107;
        r0.generateGameMessageText(r5);
        goto L_0x1175;
    L_0x11a8:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionPaymentSent;
        if (r5 == 0) goto L_0x1175;
    L_0x11b2:
        r5 = 0;
        r0 = r107;
        r0.generatePaymentSentMessageText(r5);
        goto L_0x1175;
    L_0x11b9:
        r0 = r167;
        r6 = r0.inlineReturn;
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x1138;
    L_0x11c3:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.reply_markup;
        if (r5 == 0) goto L_0x1138;
    L_0x11cb:
        r31 = 0;
    L_0x11cd:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.reply_markup;
        r5 = r5.rows;
        r5 = r5.size();
        r0 = r31;
        if (r0 >= r5) goto L_0x1138;
    L_0x11dd:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.reply_markup;
        r5 = r5.rows;
        r0 = r31;
        r144 = r5.get(r0);
        r144 = (org.telegram.tgnet.TLRPC$TL_keyboardButtonRow) r144;
        r37 = 0;
    L_0x11ef:
        r0 = r144;
        r5 = r0.buttons;
        r5 = r5.size();
        r0 = r37;
        if (r0 >= r5) goto L_0x1216;
    L_0x11fb:
        r0 = r144;
        r5 = r0.buttons;
        r0 = r37;
        r36 = r5.get(r0);
        r36 = (org.telegram.tgnet.TLRPC$KeyboardButton) r36;
        r0 = r36;
        r5 = r0 instanceof org.telegram.tgnet.TLRPC$TL_keyboardButtonSwitchInline;
        if (r5 == 0) goto L_0x1219;
    L_0x120d:
        r36 = (org.telegram.tgnet.TLRPC$TL_keyboardButtonSwitchInline) r36;
        r0 = r167;
        r1 = r36;
        r0.processSwitchButton(r1);
    L_0x1216:
        r31 = r31 + 1;
        goto L_0x11cd;
    L_0x1219:
        r37 = r37 + 1;
        goto L_0x11ef;
    L_0x121c:
        r142 = 0;
        r0 = r167;
        r5 = r0.forwardEndReached;
        r6 = 0;
        r5 = r5[r6];
        if (r5 != 0) goto L_0x14d8;
    L_0x1227:
        r10 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r9 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x1234;
    L_0x1231:
        r9 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
    L_0x1234:
        r50 = 0;
        r4 = 0;
    L_0x1237:
        r5 = r26.size();
        if (r4 >= r5) goto L_0x13f5;
    L_0x123d:
        r0 = r26;
        r124 = r0.get(r4);
        r124 = (org.telegram.messenger.MessageObject) r124;
        r0 = r167;
        r5 = r0.currentUser;
        if (r5 == 0) goto L_0x1266;
    L_0x124b:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.bot;
        if (r5 == 0) goto L_0x1259;
    L_0x1253:
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x1263;
    L_0x1259:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.id;
        r0 = r53;
        if (r5 != r0) goto L_0x1266;
    L_0x1263:
        r124.setIsRead();
    L_0x1266:
        r0 = r167;
        r5 = r0.avatarContainer;
        if (r5 == 0) goto L_0x12a3;
    L_0x126c:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x12a3;
    L_0x1272:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        if (r5 == 0) goto L_0x12a3;
    L_0x127a:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageEncryptedAction;
        if (r5 == 0) goto L_0x12a3;
    L_0x1284:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5.encryptedAction;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL;
        if (r5 == 0) goto L_0x12a3;
    L_0x1290:
        r0 = r167;
        r6 = r0.avatarContainer;
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5.encryptedAction;
        r5 = (org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL) r5;
        r5 = r5.ttl_seconds;
        r6.setTime(r5);
    L_0x12a3:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo;
        if (r5 == 0) goto L_0x1307;
    L_0x12ad:
        r35 = new android.os.Bundle;
        r35.<init>();
        r5 = "chat_id";
        r0 = r124;
        r6 = r0.messageOwner;
        r6 = r6.action;
        r6 = r6.channel_id;
        r0 = r35;
        r0.putInt(r5, r6);
        r0 = r167;
        r5 = r0.parentLayout;
        r5 = r5.fragmentsStack;
        r5 = r5.size();
        if (r5 <= 0) goto L_0x1304;
    L_0x12ce:
        r0 = r167;
        r5 = r0.parentLayout;
        r5 = r5.fragmentsStack;
        r0 = r167;
        r6 = r0.parentLayout;
        r6 = r6.fragmentsStack;
        r6 = r6.size();
        r6 = r6 + -1;
        r5 = r5.get(r6);
        r5 = (org.telegram.ui.ActionBar.BaseFragment) r5;
        r89 = r5;
    L_0x12e8:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r0 = r5.channel_id;
        r43 = r0;
        r5 = new org.telegram.ui.ChatActivity$93;
        r0 = r167;
        r1 = r89;
        r2 = r35;
        r3 = r43;
        r5.<init>(r1, r2, r3);
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r5);
        goto L_0x0015;
    L_0x1304:
        r89 = 0;
        goto L_0x12e8;
    L_0x1307:
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x132b;
    L_0x130d:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = r5.megagroup;
        if (r5 == 0) goto L_0x132b;
    L_0x1315:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser;
        if (r5 != 0) goto L_0x1329;
    L_0x131f:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser;
        if (r5 == 0) goto L_0x132b;
    L_0x1329:
        r142 = 1;
    L_0x132b:
        r5 = r124.isOut();
        if (r5 == 0) goto L_0x133f;
    L_0x1331:
        r5 = r124.isSending();
        if (r5 == 0) goto L_0x133f;
    L_0x1337:
        r5 = 0;
        r0 = r167;
        r0.scrollToLastMessage(r5);
        goto L_0x0015;
    L_0x133f:
        r0 = r124;
        r5 = r0.type;
        if (r5 < 0) goto L_0x135a;
    L_0x1345:
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r6 = r124.getId();
        r6 = java.lang.Integer.valueOf(r6);
        r5 = r5.containsKey(r6);
        if (r5 == 0) goto L_0x135e;
    L_0x135a:
        r4 = r4 + 1;
        goto L_0x1237;
    L_0x135e:
        r124.checkLayout();
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r10 = java.lang.Math.max(r10, r5);
        r5 = r124.getId();
        if (r5 <= 0) goto L_0x13d6;
    L_0x1371:
        r5 = r124.getId();
        r9 = java.lang.Math.max(r5, r9);
        r0 = r167;
        r5 = r0.last_message_id;
        r6 = r124.getId();
        r5 = java.lang.Math.max(r5, r6);
        r0 = r167;
        r0.last_message_id = r5;
    L_0x1389:
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x13a1;
    L_0x138f:
        r5 = r124.isUnread();
        if (r5 == 0) goto L_0x13a1;
    L_0x1395:
        r0 = r167;
        r5 = r0.unread_to_load;
        r5 = r5 + 1;
        r0 = r167;
        r0.unread_to_load = r5;
        r50 = 1;
    L_0x13a1:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.mentioned;
        if (r5 == 0) goto L_0x13b9;
    L_0x13a9:
        r5 = r124.isContentUnread();
        if (r5 == 0) goto L_0x13b9;
    L_0x13af:
        r0 = r167;
        r5 = r0.newMentionsCount;
        r5 = r5 + 1;
        r0 = r167;
        r0.newMentionsCount = r5;
    L_0x13b9:
        r0 = r167;
        r5 = r0.newUnreadMessageCount;
        r5 = r5 + 1;
        r0 = r167;
        r0.newUnreadMessageCount = r5;
        r0 = r124;
        r5 = r0.type;
        r6 = 10;
        if (r5 == r6) goto L_0x13d3;
    L_0x13cb:
        r0 = r124;
        r5 = r0.type;
        r6 = 11;
        if (r5 != r6) goto L_0x135a;
    L_0x13d3:
        r154 = 1;
        goto L_0x135a;
    L_0x13d6:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x1389;
    L_0x13dc:
        r5 = r124.getId();
        r9 = java.lang.Math.min(r5, r9);
        r0 = r167;
        r5 = r0.last_message_id;
        r6 = r124.getId();
        r5 = java.lang.Math.min(r5, r6);
        r0 = r167;
        r0.last_message_id = r5;
        goto L_0x1389;
    L_0x13f5:
        r0 = r167;
        r5 = r0.newUnreadMessageCount;
        if (r5 == 0) goto L_0x1425;
    L_0x13fb:
        r0 = r167;
        r5 = r0.pagedownButtonCounter;
        if (r5 == 0) goto L_0x1425;
    L_0x1401:
        r0 = r167;
        r5 = r0.pagedownButtonCounter;
        r6 = 0;
        r5.setVisibility(r6);
        r0 = r167;
        r5 = r0.pagedownButtonCounter;
        r6 = "%d";
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r0 = r167;
        r11 = r0.newUnreadMessageCount;
        r11 = java.lang.Integer.valueOf(r11);
        r7[r8] = r11;
        r6 = java.lang.String.format(r6, r7);
        r5.setText(r6);
    L_0x1425:
        r0 = r167;
        r5 = r0.newMentionsCount;
        if (r5 == 0) goto L_0x145c;
    L_0x142b:
        r0 = r167;
        r5 = r0.mentiondownButtonCounter;
        if (r5 == 0) goto L_0x145c;
    L_0x1431:
        r0 = r167;
        r5 = r0.mentiondownButtonCounter;
        r6 = 0;
        r5.setVisibility(r6);
        r0 = r167;
        r5 = r0.mentiondownButtonCounter;
        r6 = "%d";
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r0 = r167;
        r11 = r0.newMentionsCount;
        r11 = java.lang.Integer.valueOf(r11);
        r7[r8] = r11;
        r6 = java.lang.String.format(r6, r7);
        r5.setText(r6);
        r5 = 1;
        r6 = 1;
        r0 = r167;
        r0.showMentiondownButton(r5, r6);
    L_0x145c:
        if (r50 == 0) goto L_0x1471;
    L_0x145e:
        r0 = r167;
        r5 = r0.paused;
        if (r5 == 0) goto L_0x14b1;
    L_0x1464:
        r5 = 1;
        r0 = r167;
        r0.readWhenResume = r5;
        r0 = r167;
        r0.readWithDate = r10;
        r0 = r167;
        r0.readWithMid = r9;
    L_0x1471:
        r167.updateVisibleRows();
    L_0x1474:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.isEmpty();
        if (r5 != 0) goto L_0x1496;
    L_0x147e:
        r0 = r167;
        r5 = r0.botUser;
        if (r5 == 0) goto L_0x1496;
    L_0x1484:
        r0 = r167;
        r5 = r0.botUser;
        r5 = r5.length();
        if (r5 != 0) goto L_0x1496;
    L_0x148e:
        r5 = 0;
        r0 = r167;
        r0.botUser = r5;
        r167.updateBottomOverlay();
    L_0x1496:
        if (r154 == 0) goto L_0x149e;
    L_0x1498:
        r167.updateTitle();
        r167.checkAndUpdateAvatar();
    L_0x149e:
        if (r142 == 0) goto L_0x0015;
    L_0x14a0:
        r5 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r6 = r0.currentChat;
        r6 = r6.id;
        r7 = 0;
        r8 = 1;
        r5.loadFullChat(r6, r7, r8);
        goto L_0x0015;
    L_0x14b1:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.size();
        if (r5 <= 0) goto L_0x1471;
    L_0x14bb:
        r5 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r6 = r0.dialog_id;
        r0 = r167;
        r8 = r0.messages;
        r11 = 0;
        r8 = r8.get(r11);
        r8 = (org.telegram.messenger.MessageObject) r8;
        r8 = r8.getId();
        r11 = 1;
        r12 = 0;
        r5.markDialogAsRead(r6, r8, r9, r10, r11, r12);
        goto L_0x1471;
    L_0x14d8:
        r119 = 0;
        r102 = 0;
        r153 = 1;
        r165 = 0;
        r5 = org.telegram.messenger.BuildVars.DEBUG_VERSION;
        if (r5 == 0) goto L_0x150e;
    L_0x14e4:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "received new messages ";
        r5 = r5.append(r6);
        r6 = r26.size();
        r5 = r5.append(r6);
        r6 = " in dialog ";
        r5 = r5.append(r6);
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = r5.append(r6);
        r5 = r5.toString();
        org.telegram.messenger.FileLog.d(r5);
    L_0x150e:
        r4 = 0;
    L_0x150f:
        r5 = r26.size();
        if (r4 >= r5) goto L_0x1aa5;
    L_0x1515:
        r134 = -1;
        r0 = r26;
        r124 = r0.get(r4);
        r124 = (org.telegram.messenger.MessageObject) r124;
        r0 = r167;
        r5 = r0.currentUser;
        if (r5 == 0) goto L_0x1540;
    L_0x1525:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.bot;
        if (r5 == 0) goto L_0x1533;
    L_0x152d:
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x153d;
    L_0x1533:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.id;
        r0 = r53;
        if (r5 != r0) goto L_0x1540;
    L_0x153d:
        r124.setIsRead();
    L_0x1540:
        r0 = r167;
        r5 = r0.avatarContainer;
        if (r5 == 0) goto L_0x157d;
    L_0x1546:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x157d;
    L_0x154c:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        if (r5 == 0) goto L_0x157d;
    L_0x1554:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageEncryptedAction;
        if (r5 == 0) goto L_0x157d;
    L_0x155e:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5.encryptedAction;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL;
        if (r5 == 0) goto L_0x157d;
    L_0x156a:
        r0 = r167;
        r6 = r0.avatarContainer;
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5.encryptedAction;
        r5 = (org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL) r5;
        r5 = r5.ttl_seconds;
        r6.setTime(r5);
    L_0x157d:
        r0 = r124;
        r5 = r0.type;
        if (r5 < 0) goto L_0x1598;
    L_0x1583:
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r6 = r124.getId();
        r6 = java.lang.Integer.valueOf(r6);
        r5 = r5.containsKey(r6);
        if (r5 == 0) goto L_0x159c;
    L_0x1598:
        r4 = r4 + 1;
        goto L_0x150f;
    L_0x159c:
        if (r4 != 0) goto L_0x15b6;
    L_0x159e:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.id;
        if (r5 >= 0) goto L_0x15b6;
    L_0x15a6:
        r0 = r124;
        r5 = r0.type;
        r6 = 5;
        if (r5 != r6) goto L_0x15b6;
    L_0x15ad:
        r0 = r167;
        r5 = r0.animatingMessageObjects;
        r0 = r124;
        r5.add(r0);
    L_0x15b6:
        r5 = r124.hasValidGroupId();
        if (r5 == 0) goto L_0x172d;
    L_0x15bc:
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r6 = r124.getGroupId();
        r6 = java.lang.Long.valueOf(r6);
        r72 = r5.get(r6);
        r72 = (org.telegram.messenger.MessageObject.GroupedMessages) r72;
        if (r72 != 0) goto L_0x15ee;
    L_0x15d0:
        r72 = new org.telegram.messenger.MessageObject$GroupedMessages;
        r72.<init>();
        r6 = r124.getGroupId();
        r0 = r72;
        r0.groupId = r6;
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r0 = r72;
        r6 = r0.groupId;
        r6 = java.lang.Long.valueOf(r6);
        r0 = r72;
        r5.put(r6, r0);
    L_0x15ee:
        if (r119 != 0) goto L_0x15f5;
    L_0x15f0:
        r119 = new java.util.HashMap;
        r119.<init>();
    L_0x15f5:
        r0 = r72;
        r6 = r0.groupId;
        r5 = java.lang.Long.valueOf(r6);
        r0 = r119;
        r1 = r72;
        r0.put(r5, r1);
        r0 = r72;
        r5 = r0.messages;
        r0 = r124;
        r5.add(r0);
    L_0x160d:
        if (r72 == 0) goto L_0x163e;
    L_0x160f:
        r0 = r72;
        r5 = r0.messages;
        r147 = r5.size();
        r5 = 1;
        r0 = r147;
        if (r0 <= r5) goto L_0x1731;
    L_0x161c:
        r0 = r72;
        r5 = r0.messages;
        r0 = r72;
        r6 = r0.messages;
        r6 = r6.size();
        r6 = r6 + -2;
        r5 = r5.get(r6);
        r5 = (org.telegram.messenger.MessageObject) r5;
        r107 = r5;
    L_0x1632:
        if (r107 == 0) goto L_0x163e;
    L_0x1634:
        r0 = r167;
        r5 = r0.messages;
        r0 = r107;
        r134 = r5.indexOf(r0);
    L_0x163e:
        r5 = -1;
        r0 = r134;
        if (r0 != r5) goto L_0x1657;
    L_0x1643:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.id;
        if (r5 < 0) goto L_0x1655;
    L_0x164b:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.isEmpty();
        if (r5 == 0) goto L_0x1735;
    L_0x1655:
        r134 = 0;
    L_0x1657:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x16ab;
    L_0x165d:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.media;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
        if (r5 == 0) goto L_0x16ab;
    L_0x1667:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.media;
        r5 = r5.webpage;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_webPageUrlPending;
        if (r5 == 0) goto L_0x16ab;
    L_0x1673:
        if (r165 != 0) goto L_0x167a;
    L_0x1675:
        r165 = new java.util.HashMap;
        r165.<init>();
    L_0x167a:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.media;
        r5 = r5.webpage;
        r5 = r5.url;
        r0 = r165;
        r29 = r0.get(r5);
        r29 = (java.util.ArrayList) r29;
        if (r29 != 0) goto L_0x16a4;
    L_0x168e:
        r29 = new java.util.ArrayList;
        r29.<init>();
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.media;
        r5 = r5.webpage;
        r5 = r5.url;
        r0 = r165;
        r1 = r29;
        r0.put(r5, r1);
    L_0x16a4:
        r0 = r29;
        r1 = r124;
        r0.add(r1);
    L_0x16ab:
        r124.checkLayout();
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo;
        if (r5 == 0) goto L_0x17fa;
    L_0x16b8:
        r35 = new android.os.Bundle;
        r35.<init>();
        r5 = "chat_id";
        r0 = r124;
        r6 = r0.messageOwner;
        r6 = r6.action;
        r6 = r6.channel_id;
        r0 = r35;
        r0.putInt(r5, r6);
        r0 = r167;
        r5 = r0.parentLayout;
        r5 = r5.fragmentsStack;
        r5 = r5.size();
        if (r5 <= 0) goto L_0x17f6;
    L_0x16d9:
        r0 = r167;
        r5 = r0.parentLayout;
        r5 = r5.fragmentsStack;
        r0 = r167;
        r6 = r0.parentLayout;
        r6 = r6.fragmentsStack;
        r6 = r6.size();
        r6 = r6 + -1;
        r5 = r5.get(r6);
        r5 = (org.telegram.ui.ActionBar.BaseFragment) r5;
        r89 = r5;
    L_0x16f3:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r0 = r5.channel_id;
        r43 = r0;
        r5 = new org.telegram.ui.ChatActivity$94;
        r0 = r167;
        r1 = r89;
        r2 = r35;
        r3 = r43;
        r5.<init>(r1, r2, r3);
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r5);
        if (r119 == 0) goto L_0x0015;
    L_0x170f:
        r5 = r119.entrySet();
        r6 = r5.iterator();
    L_0x1717:
        r5 = r6.hasNext();
        if (r5 == 0) goto L_0x0015;
    L_0x171d:
        r64 = r6.next();
        r64 = (java.util.Map.Entry) r64;
        r5 = r64.getValue();
        r5 = (org.telegram.messenger.MessageObject.GroupedMessages) r5;
        r5.calculate();
        goto L_0x1717;
    L_0x172d:
        r72 = 0;
        goto L_0x160d;
    L_0x1731:
        r107 = 0;
        goto L_0x1632;
    L_0x1735:
        r0 = r167;
        r5 = r0.messages;
        r147 = r5.size();
        r31 = 0;
    L_0x173f:
        r0 = r31;
        r1 = r147;
        if (r0 >= r1) goto L_0x17b9;
    L_0x1745:
        r0 = r167;
        r5 = r0.messages;
        r0 = r31;
        r91 = r5.get(r0);
        r91 = (org.telegram.messenger.MessageObject) r91;
        r0 = r91;
        r5 = r0.type;
        if (r5 < 0) goto L_0x17f2;
    L_0x1757:
        r0 = r91;
        r5 = r0.messageOwner;
        r5 = r5.date;
        if (r5 <= 0) goto L_0x17f2;
    L_0x175f:
        r0 = r91;
        r5 = r0.messageOwner;
        r5 = r5.id;
        if (r5 <= 0) goto L_0x177d;
    L_0x1767:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.id;
        if (r5 <= 0) goto L_0x177d;
    L_0x176f:
        r0 = r91;
        r5 = r0.messageOwner;
        r5 = r5.id;
        r0 = r124;
        r6 = r0.messageOwner;
        r6 = r6.id;
        if (r5 < r6) goto L_0x178b;
    L_0x177d:
        r0 = r91;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r0 = r124;
        r6 = r0.messageOwner;
        r6 = r6.date;
        if (r5 >= r6) goto L_0x17f2;
    L_0x178b:
        r6 = r91.getGroupId();
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x17d4;
    L_0x1795:
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r6 = r91.getGroupId();
        r6 = java.lang.Long.valueOf(r6);
        r90 = r5.get(r6);
        r90 = (org.telegram.messenger.MessageObject.GroupedMessages) r90;
        if (r90 == 0) goto L_0x17b5;
    L_0x17a9:
        r0 = r90;
        r5 = r0.messages;
        r5 = r5.size();
        if (r5 != 0) goto L_0x17b5;
    L_0x17b3:
        r90 = 0;
    L_0x17b5:
        if (r90 != 0) goto L_0x17d7;
    L_0x17b7:
        r134 = r31;
    L_0x17b9:
        r5 = -1;
        r0 = r134;
        if (r0 == r5) goto L_0x17ca;
    L_0x17be:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.size();
        r0 = r134;
        if (r0 <= r5) goto L_0x1657;
    L_0x17ca:
        r0 = r167;
        r5 = r0.messages;
        r134 = r5.size();
        goto L_0x1657;
    L_0x17d4:
        r90 = 0;
        goto L_0x17b5;
    L_0x17d7:
        r0 = r167;
        r5 = r0.messages;
        r0 = r90;
        r6 = r0.messages;
        r0 = r90;
        r7 = r0.messages;
        r7 = r7.size();
        r7 = r7 + -1;
        r6 = r6.get(r7);
        r134 = r5.indexOf(r6);
        goto L_0x17b9;
    L_0x17f2:
        r31 = r31 + 1;
        goto L_0x173f;
    L_0x17f6:
        r89 = 0;
        goto L_0x16f3;
    L_0x17fa:
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x181e;
    L_0x1800:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = r5.megagroup;
        if (r5 == 0) goto L_0x181e;
    L_0x1808:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser;
        if (r5 != 0) goto L_0x181c;
    L_0x1812:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser;
        if (r5 == 0) goto L_0x181e;
    L_0x181c:
        r142 = 1;
    L_0x181e:
        r0 = r167;
        r5 = r0.minDate;
        r6 = 0;
        r5 = r5[r6];
        if (r5 == 0) goto L_0x1836;
    L_0x1827:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r0 = r167;
        r6 = r0.minDate;
        r7 = 0;
        r6 = r6[r7];
        if (r5 >= r6) goto L_0x1843;
    L_0x1836:
        r0 = r167;
        r5 = r0.minDate;
        r6 = 0;
        r0 = r124;
        r7 = r0.messageOwner;
        r7 = r7.date;
        r5[r6] = r7;
    L_0x1843:
        r5 = r124.isOut();
        if (r5 == 0) goto L_0x184e;
    L_0x1849:
        r167.removeUnreadPlane();
        r74 = 1;
    L_0x184e:
        r5 = r124.getId();
        if (r5 <= 0) goto L_0x1a60;
    L_0x1854:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = 0;
        r7 = r124.getId();
        r0 = r167;
        r8 = r0.maxMessageId;
        r11 = 0;
        r8 = r8[r11];
        r7 = java.lang.Math.min(r7, r8);
        r5[r6] = r7;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = 0;
        r7 = r124.getId();
        r0 = r167;
        r8 = r0.minMessageId;
        r11 = 0;
        r8 = r8[r11];
        r7 = java.lang.Math.max(r7, r8);
        r5[r6] = r7;
    L_0x1880:
        r0 = r167;
        r5 = r0.maxDate;
        r6 = 0;
        r0 = r167;
        r7 = r0.maxDate;
        r8 = 0;
        r7 = r7[r8];
        r0 = r124;
        r8 = r0.messageOwner;
        r8 = r8.date;
        r7 = java.lang.Math.max(r7, r8);
        r5[r6] = r7;
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r6 = r124.getId();
        r6 = java.lang.Integer.valueOf(r6);
        r0 = r124;
        r5.put(r6, r0);
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r124;
        r6 = r0.dateKey;
        r58 = r5.get(r6);
        r58 = (java.util.ArrayList) r58;
        if (r58 != 0) goto L_0x1932;
    L_0x18bc:
        r58 = new java.util.ArrayList;
        r58.<init>();
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r124;
        r6 = r0.dateKey;
        r0 = r58;
        r5.put(r6, r0);
        r55 = new org.telegram.tgnet.TLRPC$TL_message;
        r55.<init>();
        r5 = "talagram";
        r6 = "arabgram";
        r5 = r5.contentEquals(r6);
        if (r5 == 0) goto L_0x1a94;
    L_0x18df:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r6 = (long) r5;
        r5 = org.telegram.messenger.LocaleController.formatDateChat(r6);
        r0 = r55;
        r0.message = r5;
    L_0x18ee:
        r5 = 0;
        r0 = r55;
        r0.id = r5;
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r0 = r55;
        r0.date = r5;
        r56 = new org.telegram.messenger.MessageObject;
        r5 = 0;
        r6 = 0;
        r0 = r56;
        r1 = r55;
        r0.<init>(r1, r5, r6);
        r5 = 10;
        r0 = r56;
        r0.type = r5;
        r5 = 1;
        r0 = r56;
        r0.contentType = r5;
        r5 = 1;
        r0 = r56;
        r0.isDateObject = r5;
        r0 = r167;
        r5 = r0.messages;
        r0 = r134;
        r1 = r56;
        r5.add(r0, r1);
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x1932;
    L_0x1929:
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r134;
        r5.notifyItemInserted(r0);
    L_0x1932:
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x19e5;
    L_0x1938:
        r0 = r167;
        r5 = r0.paused;
        if (r5 == 0) goto L_0x19c2;
    L_0x193e:
        if (r134 != 0) goto L_0x19c2;
    L_0x1940:
        r0 = r167;
        r5 = r0.scrollToTopUnReadOnResume;
        if (r5 != 0) goto L_0x195e;
    L_0x1946:
        r0 = r167;
        r5 = r0.unreadMessageObject;
        if (r5 == 0) goto L_0x195e;
    L_0x194c:
        r0 = r167;
        r5 = r0.unreadMessageObject;
        r0 = r167;
        r0.removeMessageObject(r5);
        if (r134 <= 0) goto L_0x1959;
    L_0x1957:
        r134 = r134 + -1;
    L_0x1959:
        r5 = 0;
        r0 = r167;
        r0.unreadMessageObject = r5;
    L_0x195e:
        r0 = r167;
        r5 = r0.unreadMessageObject;
        if (r5 != 0) goto L_0x19c2;
    L_0x1964:
        r55 = new org.telegram.tgnet.TLRPC$TL_message;
        r55.<init>();
        r5 = "";
        r0 = r55;
        r0.message = r5;
        r5 = 0;
        r0 = r55;
        r0.id = r5;
        r56 = new org.telegram.messenger.MessageObject;
        r5 = 0;
        r6 = 0;
        r0 = r56;
        r1 = r55;
        r0.<init>(r1, r5, r6);
        r5 = 6;
        r0 = r56;
        r0.type = r5;
        r5 = 2;
        r0 = r56;
        r0.contentType = r5;
        r0 = r167;
        r5 = r0.messages;
        r6 = 0;
        r0 = r56;
        r5.add(r6, r0);
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x19a2;
    L_0x199a:
        r0 = r167;
        r5 = r0.chatAdapter;
        r6 = 0;
        r5.notifyItemInserted(r6);
    L_0x19a2:
        r0 = r56;
        r1 = r167;
        r1.unreadMessageObject = r0;
        r0 = r167;
        r5 = r0.unreadMessageObject;
        r0 = r167;
        r0.scrollToMessage = r5;
        r5 = -10000; // 0xffffffffffffd8f0 float:NaN double:NaN;
        r0 = r167;
        r0.scrollToMessagePosition = r5;
        r153 = 0;
        r5 = 0;
        r0 = r167;
        r0.unread_to_load = r5;
        r5 = 1;
        r0 = r167;
        r0.scrollToTopUnReadOnResume = r5;
    L_0x19c2:
        r0 = r167;
        r5 = r0.unreadMessageObject;
        if (r5 == 0) goto L_0x19d4;
    L_0x19c8:
        r0 = r167;
        r5 = r0.unread_to_load;
        r5 = r5 + 1;
        r0 = r167;
        r0.unread_to_load = r5;
        r153 = 1;
    L_0x19d4:
        r5 = r124.isUnread();
        if (r5 == 0) goto L_0x19e5;
    L_0x19da:
        r0 = r167;
        r5 = r0.paused;
        if (r5 != 0) goto L_0x19e3;
    L_0x19e0:
        r124.setIsRead();
    L_0x19e3:
        r102 = 1;
    L_0x19e5:
        r5 = 0;
        r0 = r58;
        r1 = r124;
        r0.add(r5, r1);
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.size();
        r0 = r134;
        if (r0 <= r5) goto L_0x1a01;
    L_0x19f9:
        r0 = r167;
        r5 = r0.messages;
        r134 = r5.size();
    L_0x1a01:
        r0 = r167;
        r5 = r0.messages;
        r0 = r134;
        r1 = r124;
        r5.add(r0, r1);
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x1a24;
    L_0x1a12:
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r134;
        r5.notifyItemChanged(r0);
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r134;
        r5.notifyItemInserted(r0);
    L_0x1a24:
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x1a42;
    L_0x1a2a:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.mentioned;
        if (r5 == 0) goto L_0x1a42;
    L_0x1a32:
        r5 = r124.isContentUnread();
        if (r5 == 0) goto L_0x1a42;
    L_0x1a38:
        r0 = r167;
        r5 = r0.newMentionsCount;
        r5 = r5 + 1;
        r0 = r167;
        r0.newMentionsCount = r5;
    L_0x1a42:
        r0 = r167;
        r5 = r0.newUnreadMessageCount;
        r5 = r5 + 1;
        r0 = r167;
        r0.newUnreadMessageCount = r5;
        r0 = r124;
        r5 = r0.type;
        r6 = 10;
        if (r5 == r6) goto L_0x1a5c;
    L_0x1a54:
        r0 = r124;
        r5 = r0.type;
        r6 = 11;
        if (r5 != r6) goto L_0x1598;
    L_0x1a5c:
        r154 = 1;
        goto L_0x1598;
    L_0x1a60:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x1880;
    L_0x1a66:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = 0;
        r7 = r124.getId();
        r0 = r167;
        r8 = r0.maxMessageId;
        r11 = 0;
        r8 = r8[r11];
        r7 = java.lang.Math.max(r7, r8);
        r5[r6] = r7;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = 0;
        r7 = r124.getId();
        r0 = r167;
        r8 = r0.minMessageId;
        r11 = 0;
        r8 = r8[r11];
        r7 = java.lang.Math.min(r7, r8);
        r5[r6] = r7;
        goto L_0x1880;
    L_0x1a94:
        r0 = r124;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r6 = (long) r5;
        r5 = getPersianDate(r6);
        r0 = r55;
        r0.message = r5;
        goto L_0x18ee;
    L_0x1aa5:
        if (r165 == 0) goto L_0x1ab4;
    L_0x1aa7:
        r5 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r6 = r0.dialog_id;
        r0 = r165;
        r5.reloadWebPages(r6, r0);
    L_0x1ab4:
        if (r119 == 0) goto L_0x1b15;
    L_0x1ab6:
        r5 = r119.entrySet();
        r6 = r5.iterator();
    L_0x1abe:
        r5 = r6.hasNext();
        if (r5 == 0) goto L_0x1b15;
    L_0x1ac4:
        r64 = r6.next();
        r64 = (java.util.Map.Entry) r64;
        r72 = r64.getValue();
        r72 = (org.telegram.messenger.MessageObject.GroupedMessages) r72;
        r0 = r72;
        r5 = r0.posArray;
        r127 = r5.size();
        r5 = r64.getValue();
        r5 = (org.telegram.messenger.MessageObject.GroupedMessages) r5;
        r5.calculate();
        r0 = r72;
        r5 = r0.posArray;
        r118 = r5.size();
        r5 = r118 - r127;
        if (r5 <= 0) goto L_0x1abe;
    L_0x1aed:
        r0 = r167;
        r5 = r0.messages;
        r0 = r72;
        r7 = r0.messages;
        r0 = r72;
        r8 = r0.messages;
        r8 = r8.size();
        r8 = r8 + -1;
        r7 = r7.get(r8);
        r81 = r5.indexOf(r7);
        if (r81 < 0) goto L_0x1abe;
    L_0x1b09:
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r81;
        r1 = r118;
        r5.notifyItemRangeChanged(r0, r1);
        goto L_0x1abe;
    L_0x1b15:
        r0 = r167;
        r5 = r0.progressView;
        if (r5 == 0) goto L_0x1b23;
    L_0x1b1b:
        r0 = r167;
        r5 = r0.progressView;
        r6 = 4;
        r5.setVisibility(r6);
    L_0x1b23:
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x1bc7;
    L_0x1b29:
        if (r153 == 0) goto L_0x1b36;
    L_0x1b2b:
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r167;
        r6 = r0.unreadMessageObject;
        r5.updateRowWithMessageObject(r6);
    L_0x1b36:
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x1c10;
    L_0x1b3c:
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x1c10;
    L_0x1b42:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r92 = r5.findFirstVisibleItemPosition();
        r5 = -1;
        r0 = r92;
        if (r0 != r5) goto L_0x1b51;
    L_0x1b4f:
        r92 = 0;
    L_0x1b51:
        if (r92 == 0) goto L_0x1b55;
    L_0x1b53:
        if (r74 == 0) goto L_0x1bd7;
    L_0x1b55:
        r5 = 0;
        r0 = r167;
        r0.newUnreadMessageCount = r5;
        r0 = r167;
        r5 = r0.firstLoading;
        if (r5 != 0) goto L_0x1b6b;
    L_0x1b60:
        r0 = r167;
        r5 = r0.paused;
        if (r5 == 0) goto L_0x1bce;
    L_0x1b66:
        r5 = 1;
        r0 = r167;
        r0.scrollToTopOnResume = r5;
    L_0x1b6b:
        r0 = r167;
        r5 = r0.newMentionsCount;
        if (r5 == 0) goto L_0x1ba2;
    L_0x1b71:
        r0 = r167;
        r5 = r0.mentiondownButtonCounter;
        if (r5 == 0) goto L_0x1ba2;
    L_0x1b77:
        r0 = r167;
        r5 = r0.mentiondownButtonCounter;
        r6 = 0;
        r5.setVisibility(r6);
        r0 = r167;
        r5 = r0.mentiondownButtonCounter;
        r6 = "%d";
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r0 = r167;
        r11 = r0.newMentionsCount;
        r11 = java.lang.Integer.valueOf(r11);
        r7[r8] = r11;
        r6 = java.lang.String.format(r6, r7);
        r5.setText(r6);
        r5 = 1;
        r6 = 1;
        r0 = r167;
        r0.showMentiondownButton(r5, r6);
    L_0x1ba2:
        if (r102 == 0) goto L_0x1474;
    L_0x1ba4:
        r0 = r167;
        r5 = r0.paused;
        if (r5 == 0) goto L_0x1c16;
    L_0x1baa:
        r5 = 1;
        r0 = r167;
        r0.readWhenResume = r5;
        r0 = r167;
        r5 = r0.maxDate;
        r6 = 0;
        r5 = r5[r6];
        r0 = r167;
        r0.readWithDate = r5;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = 0;
        r5 = r5[r6];
        r0 = r167;
        r0.readWithMid = r5;
        goto L_0x1474;
    L_0x1bc7:
        r5 = 1;
        r0 = r167;
        r0.scrollToTopOnResume = r5;
        goto L_0x1b36;
    L_0x1bce:
        r5 = 1;
        r0 = r167;
        r0.forceScrollToTop = r5;
        r167.moveScrollToLastMessage();
        goto L_0x1b6b;
    L_0x1bd7:
        r0 = r167;
        r5 = r0.newUnreadMessageCount;
        if (r5 == 0) goto L_0x1c07;
    L_0x1bdd:
        r0 = r167;
        r5 = r0.pagedownButtonCounter;
        if (r5 == 0) goto L_0x1c07;
    L_0x1be3:
        r0 = r167;
        r5 = r0.pagedownButtonCounter;
        r6 = 0;
        r5.setVisibility(r6);
        r0 = r167;
        r5 = r0.pagedownButtonCounter;
        r6 = "%d";
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r0 = r167;
        r11 = r0.newUnreadMessageCount;
        r11 = java.lang.Integer.valueOf(r11);
        r7[r8] = r11;
        r6 = java.lang.String.format(r6, r7);
        r5.setText(r6);
    L_0x1c07:
        r5 = 1;
        r6 = 1;
        r0 = r167;
        r0.showPagedownButton(r5, r6);
        goto L_0x1b6b;
    L_0x1c10:
        r5 = 1;
        r0 = r167;
        r0.scrollToTopOnResume = r5;
        goto L_0x1ba2;
    L_0x1c16:
        r11 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r12 = r0.dialog_id;
        r0 = r167;
        r5 = r0.messages;
        r6 = 0;
        r5 = r5.get(r6);
        r5 = (org.telegram.messenger.MessageObject) r5;
        r14 = r5.getId();
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = 0;
        r15 = r5[r6];
        r0 = r167;
        r5 = r0.maxDate;
        r6 = 0;
        r16 = r5[r6];
        r17 = 1;
        r18 = 0;
        r11.markDialogAsRead(r12, r14, r15, r16, r17, r18);
        goto L_0x1474;
    L_0x1c44:
        r5 = org.telegram.messenger.NotificationCenter.closeChats;
        r0 = r168;
        if (r0 != r5) goto L_0x1c6c;
    L_0x1c4a:
        if (r169 == 0) goto L_0x1c67;
    L_0x1c4c:
        r0 = r169;
        r5 = r0.length;
        if (r5 <= 0) goto L_0x1c67;
    L_0x1c51:
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r60 = r5.longValue();
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x1c62:
        r167.finishFragment();
        goto L_0x0015;
    L_0x1c67:
        r167.removeSelfFromStack();
        goto L_0x0015;
    L_0x1c6c:
        r5 = org.telegram.messenger.NotificationCenter.messagesRead;
        r0 = r168;
        if (r0 != r5) goto L_0x1d5c;
    L_0x1c72:
        r5 = 0;
        r79 = r169[r5];
        r79 = (android.util.SparseArray) r79;
        r5 = 1;
        r131 = r169[r5];
        r131 = (android.util.SparseArray) r131;
        r158 = 0;
        r31 = 0;
    L_0x1c80:
        r5 = r79.size();
        r0 = r31;
        if (r0 >= r5) goto L_0x1cdc;
    L_0x1c88:
        r0 = r79;
        r1 = r31;
        r87 = r0.keyAt(r1);
        r0 = r79;
        r1 = r87;
        r5 = r0.get(r1);
        r5 = (java.lang.Long) r5;
        r108 = r5.longValue();
        r0 = r87;
        r6 = (long) r0;
        r0 = r167;
        r12 = r0.dialog_id;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x1cac;
    L_0x1ca9:
        r31 = r31 + 1;
        goto L_0x1c80;
    L_0x1cac:
        r4 = 0;
    L_0x1cad:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.size();
        if (r4 >= r5) goto L_0x1cdc;
    L_0x1cb7:
        r0 = r167;
        r5 = r0.messages;
        r124 = r5.get(r4);
        r124 = (org.telegram.messenger.MessageObject) r124;
        r5 = r124.isOut();
        if (r5 != 0) goto L_0x1d12;
    L_0x1cc7:
        r5 = r124.getId();
        if (r5 <= 0) goto L_0x1d12;
    L_0x1ccd:
        r5 = r124.getId();
        r0 = r108;
        r6 = (int) r0;
        if (r5 > r6) goto L_0x1d12;
    L_0x1cd6:
        r5 = r124.isUnread();
        if (r5 != 0) goto L_0x1d0d;
    L_0x1cdc:
        r31 = 0;
    L_0x1cde:
        r5 = r131.size();
        r0 = r31;
        if (r0 >= r5) goto L_0x1d44;
    L_0x1ce6:
        r0 = r131;
        r1 = r31;
        r87 = r0.keyAt(r1);
        r0 = r131;
        r1 = r87;
        r5 = r0.get(r1);
        r5 = (java.lang.Long) r5;
        r6 = r5.longValue();
        r0 = (int) r6;
        r17 = r0;
        r0 = r87;
        r6 = (long) r0;
        r0 = r167;
        r12 = r0.dialog_id;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x1d15;
    L_0x1d0a:
        r31 = r31 + 1;
        goto L_0x1cde;
    L_0x1d0d:
        r124.setIsRead();
        r158 = 1;
    L_0x1d12:
        r4 = r4 + 1;
        goto L_0x1cad;
    L_0x1d15:
        r4 = 0;
    L_0x1d16:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.size();
        if (r4 >= r5) goto L_0x1d44;
    L_0x1d20:
        r0 = r167;
        r5 = r0.messages;
        r124 = r5.get(r4);
        r124 = (org.telegram.messenger.MessageObject) r124;
        r5 = r124.isOut();
        if (r5 == 0) goto L_0x1d59;
    L_0x1d30:
        r5 = r124.getId();
        if (r5 <= 0) goto L_0x1d59;
    L_0x1d36:
        r5 = r124.getId();
        r0 = r17;
        if (r5 > r0) goto L_0x1d59;
    L_0x1d3e:
        r5 = r124.isUnread();
        if (r5 != 0) goto L_0x1d54;
    L_0x1d44:
        r5 = r79.size();
        if (r5 == 0) goto L_0x1d4d;
    L_0x1d4a:
        r167.removeUnreadPlane();
    L_0x1d4d:
        if (r158 == 0) goto L_0x0015;
    L_0x1d4f:
        r167.updateVisibleRows();
        goto L_0x0015;
    L_0x1d54:
        r124.setIsRead();
        r158 = 1;
    L_0x1d59:
        r4 = r4 + 1;
        goto L_0x1d16;
    L_0x1d5c:
        r5 = org.telegram.messenger.NotificationCenter.historyCleared;
        r0 = r168;
        if (r0 != r5) goto L_0x1fbf;
    L_0x1d62:
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r60 = r5.longValue();
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x1d73:
        r5 = 1;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r103 = r5.intValue();
        r158 = 0;
        r31 = 0;
    L_0x1d80:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.size();
        r0 = r31;
        if (r0 >= r5) goto L_0x1e35;
    L_0x1d8c:
        r0 = r167;
        r5 = r0.messages;
        r0 = r31;
        r124 = r5.get(r0);
        r124 = (org.telegram.messenger.MessageObject) r124;
        r112 = r124.getId();
        if (r112 <= 0) goto L_0x1da4;
    L_0x1d9e:
        r0 = r112;
        r1 = r103;
        if (r0 <= r1) goto L_0x1da7;
    L_0x1da4:
        r31 = r31 + 1;
        goto L_0x1d80;
    L_0x1da7:
        r0 = r167;
        r5 = r0.info;
        if (r5 == 0) goto L_0x1dd7;
    L_0x1dad:
        r0 = r167;
        r5 = r0.info;
        r5 = r5.pinned_msg_id;
        r0 = r112;
        if (r5 != r0) goto L_0x1dd7;
    L_0x1db7:
        r5 = 0;
        r0 = r167;
        r0.pinnedMessageObject = r5;
        r0 = r167;
        r5 = r0.info;
        r6 = 0;
        r5.pinned_msg_id = r6;
        r5 = org.telegram.messenger.MessagesStorage.getInstance();
        r0 = r167;
        r6 = r0.info;
        r6 = r6.id;
        r7 = 0;
        r5.updateChannelPinnedMessage(r6, r7);
        r5 = 1;
        r0 = r167;
        r0.updatePinnedMessageView(r5);
    L_0x1dd7:
        r0 = r167;
        r5 = r0.messages;
        r0 = r31;
        r5.remove(r0);
        r31 = r31 + -1;
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r6 = java.lang.Integer.valueOf(r112);
        r5.remove(r6);
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r124;
        r6 = r0.dateKey;
        r57 = r5.get(r6);
        r57 = (java.util.ArrayList) r57;
        if (r57 == 0) goto L_0x1e31;
    L_0x1e00:
        r0 = r57;
        r1 = r124;
        r0.remove(r1);
        r5 = r57.isEmpty();
        if (r5 == 0) goto L_0x1e31;
    L_0x1e0d:
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r124;
        r6 = r0.dateKey;
        r5.remove(r6);
        if (r31 < 0) goto L_0x1e31;
    L_0x1e1a:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.size();
        r0 = r31;
        if (r0 >= r5) goto L_0x1e31;
    L_0x1e26:
        r0 = r167;
        r5 = r0.messages;
        r0 = r31;
        r5.remove(r0);
        r31 = r31 + -1;
    L_0x1e31:
        r158 = 1;
        goto L_0x1da4;
    L_0x1e35:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.isEmpty();
        if (r5 == 0) goto L_0x1f04;
    L_0x1e3f:
        r0 = r167;
        r5 = r0.endReached;
        r6 = 0;
        r5 = r5[r6];
        if (r5 != 0) goto L_0x1f7f;
    L_0x1e48:
        r0 = r167;
        r5 = r0.loading;
        if (r5 != 0) goto L_0x1f7f;
    L_0x1e4e:
        r0 = r167;
        r5 = r0.progressView;
        if (r5 == 0) goto L_0x1e5c;
    L_0x1e54:
        r0 = r167;
        r5 = r0.progressView;
        r6 = 4;
        r5.setVisibility(r6);
    L_0x1e5c:
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x1e6a;
    L_0x1e62:
        r0 = r167;
        r5 = r0.chatListView;
        r6 = 0;
        r5.setEmptyView(r6);
    L_0x1e6a:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x1f58;
    L_0x1e70:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = 0;
        r0 = r167;
        r7 = r0.maxMessageId;
        r8 = 1;
        r11 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r7[r8] = r11;
        r5[r6] = r11;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = 0;
        r0 = r167;
        r7 = r0.minMessageId;
        r8 = 1;
        r11 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r7[r8] = r11;
        r5[r6] = r11;
    L_0x1e91:
        r0 = r167;
        r5 = r0.maxDate;
        r6 = 0;
        r0 = r167;
        r7 = r0.maxDate;
        r8 = 1;
        r11 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r7[r8] = r11;
        r5[r6] = r11;
        r0 = r167;
        r5 = r0.minDate;
        r6 = 0;
        r0 = r167;
        r7 = r0.minDate;
        r8 = 1;
        r11 = 0;
        r7[r8] = r11;
        r5[r6] = r11;
        r0 = r167;
        r5 = r0.waitingForLoad;
        r0 = r167;
        r6 = r0.lastLoadIndex;
        r6 = java.lang.Integer.valueOf(r6);
        r5.add(r6);
        r11 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r12 = r0.dialog_id;
        r14 = 30;
        r15 = 0;
        r16 = 0;
        r0 = r167;
        r5 = r0.cacheEndReached;
        r6 = 0;
        r5 = r5[r6];
        if (r5 != 0) goto L_0x1f7b;
    L_0x1ed5:
        r17 = 1;
    L_0x1ed7:
        r0 = r167;
        r5 = r0.minDate;
        r6 = 0;
        r18 = r5[r6];
        r0 = r167;
        r0 = r0.classGuid;
        r19 = r0;
        r20 = 0;
        r21 = 0;
        r0 = r167;
        r5 = r0.currentChat;
        r22 = org.telegram.messenger.ChatObject.isChannel(r5);
        r0 = r167;
        r0 = r0.lastLoadIndex;
        r23 = r0;
        r5 = r23 + 1;
        r0 = r167;
        r0.lastLoadIndex = r5;
        r11.loadMessages(r12, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23);
        r5 = 1;
        r0 = r167;
        r0.loading = r5;
    L_0x1f04:
        if (r158 == 0) goto L_0x1f16;
    L_0x1f06:
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x1f16;
    L_0x1f0c:
        r167.removeUnreadPlane();
        r0 = r167;
        r5 = r0.chatAdapter;
        r5.notifyDataSetChanged();
    L_0x1f16:
        r0 = r167;
        r5 = r0.isDownloadManager;
        if (r5 == 0) goto L_0x0015;
    L_0x1f1c:
        r0 = r167;
        r5 = r0.messages;	 Catch:{ Exception -> 0x1f55 }
        r76 = r5.size();	 Catch:{ Exception -> 0x1f55 }
        if (r76 == 0) goto L_0x1f28;
    L_0x1f26:
        r76 = r76 + -1;
    L_0x1f28:
        r0 = r167;
        r5 = r0.avatarContainer;	 Catch:{ Exception -> 0x1f55 }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x1f55 }
        r6.<init>();	 Catch:{ Exception -> 0x1f55 }
        r7 = "downloadCount";
        r8 = 2131232709; // 0x7f0807c5 float:1.8081535E38 double:1.052968865E-314;
        r7 = org.telegram.messenger.LocaleController.getString(r7, r8);	 Catch:{ Exception -> 0x1f55 }
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x1f55 }
        r7 = " : ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x1f55 }
        r0 = r76;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x1f55 }
        r6 = r6.toString();	 Catch:{ Exception -> 0x1f55 }
        r5.setSubtitle(r6);	 Catch:{ Exception -> 0x1f55 }
        goto L_0x0015;
    L_0x1f55:
        r5 = move-exception;
        goto L_0x0015;
    L_0x1f58:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = 0;
        r0 = r167;
        r7 = r0.maxMessageId;
        r8 = 1;
        r11 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r7[r8] = r11;
        r5[r6] = r11;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = 0;
        r0 = r167;
        r7 = r0.minMessageId;
        r8 = 1;
        r11 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r7[r8] = r11;
        r5[r6] = r11;
        goto L_0x1e91;
    L_0x1f7b:
        r17 = 0;
        goto L_0x1ed7;
    L_0x1f7f:
        r0 = r167;
        r5 = r0.botButtons;
        if (r5 == 0) goto L_0x1f99;
    L_0x1f85:
        r5 = 0;
        r0 = r167;
        r0.botButtons = r5;
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        if (r5 == 0) goto L_0x1f99;
    L_0x1f90:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r6 = 0;
        r7 = 0;
        r5.setButtons(r6, r7);
    L_0x1f99:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x1f04;
    L_0x1f9f:
        r0 = r167;
        r5 = r0.currentUser;
        if (r5 == 0) goto L_0x1f04;
    L_0x1fa5:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.bot;
        if (r5 == 0) goto L_0x1f04;
    L_0x1fad:
        r0 = r167;
        r5 = r0.botUser;
        if (r5 != 0) goto L_0x1f04;
    L_0x1fb3:
        r5 = "";
        r0 = r167;
        r0.botUser = r5;
        r167.updateBottomOverlay();
        goto L_0x1f04;
    L_0x1fbf:
        r5 = org.telegram.messenger.NotificationCenter.messagesDeleted;
        r0 = r168;
        if (r0 != r5) goto L_0x22ed;
    L_0x1fc5:
        r5 = 0;
        r101 = r169[r5];
        r101 = (java.util.ArrayList) r101;
        r5 = 1;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r41 = r5.intValue();
        r97 = 0;
        r0 = r167;
        r5 = r0.currentChat;
        r5 = org.telegram.messenger.ChatObject.isChannel(r5);
        if (r5 == 0) goto L_0x20f6;
    L_0x1fdf:
        if (r41 != 0) goto L_0x20e8;
    L_0x1fe1:
        r0 = r167;
        r6 = r0.mergeDialogId;
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x20e8;
    L_0x1feb:
        r97 = 1;
    L_0x1fed:
        r158 = 0;
        r119 = 0;
        r4 = 0;
    L_0x1ff2:
        r5 = r101.size();
        if (r4 >= r5) goto L_0x20fa;
    L_0x1ff8:
        r0 = r101;
        r77 = r0.get(r4);
        r77 = (java.lang.Integer) r77;
        r0 = r167;
        r5 = r0.messagesDict;
        r5 = r5[r97];
        r0 = r77;
        r124 = r5.get(r0);
        r124 = (org.telegram.messenger.MessageObject) r124;
        if (r97 != 0) goto L_0x203e;
    L_0x2010:
        r0 = r167;
        r5 = r0.info;
        if (r5 == 0) goto L_0x203e;
    L_0x2016:
        r0 = r167;
        r5 = r0.info;
        r5 = r5.pinned_msg_id;
        r6 = r77.intValue();
        if (r5 != r6) goto L_0x203e;
    L_0x2022:
        r5 = 0;
        r0 = r167;
        r0.pinnedMessageObject = r5;
        r0 = r167;
        r5 = r0.info;
        r6 = 0;
        r5.pinned_msg_id = r6;
        r5 = org.telegram.messenger.MessagesStorage.getInstance();
        r6 = 0;
        r0 = r41;
        r5.updateChannelPinnedMessage(r0, r6);
        r5 = 1;
        r0 = r167;
        r0.updatePinnedMessageView(r5);
    L_0x203e:
        if (r124 == 0) goto L_0x20e4;
    L_0x2040:
        r0 = r167;
        r5 = r0.messages;
        r0 = r124;
        r81 = r5.indexOf(r0);
        r5 = -1;
        r0 = r81;
        if (r0 == r5) goto L_0x20e4;
    L_0x204f:
        r0 = r167;
        r5 = r0.messages;
        r0 = r81;
        r143 = r5.remove(r0);
        r143 = (org.telegram.messenger.MessageObject) r143;
        r6 = r143.getGroupId();
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x2098;
    L_0x2065:
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r6 = r143.getGroupId();
        r6 = java.lang.Long.valueOf(r6);
        r72 = r5.get(r6);
        r72 = (org.telegram.messenger.MessageObject.GroupedMessages) r72;
        if (r72 == 0) goto L_0x2098;
    L_0x2079:
        if (r119 != 0) goto L_0x2080;
    L_0x207b:
        r119 = new java.util.HashMap;
        r119.<init>();
    L_0x2080:
        r0 = r72;
        r6 = r0.groupId;
        r5 = java.lang.Long.valueOf(r6);
        r0 = r119;
        r1 = r72;
        r0.put(r5, r1);
        r0 = r72;
        r5 = r0.messages;
        r0 = r124;
        r5.remove(r0);
    L_0x2098:
        r0 = r167;
        r5 = r0.messagesDict;
        r5 = r5[r97];
        r0 = r77;
        r5.remove(r0);
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r124;
        r6 = r0.dateKey;
        r57 = r5.get(r6);
        r57 = (java.util.ArrayList) r57;
        if (r57 == 0) goto L_0x20e2;
    L_0x20b3:
        r0 = r57;
        r1 = r124;
        r0.remove(r1);
        r5 = r57.isEmpty();
        if (r5 == 0) goto L_0x20e2;
    L_0x20c0:
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r124;
        r6 = r0.dateKey;
        r5.remove(r6);
        if (r81 < 0) goto L_0x20e2;
    L_0x20cd:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.size();
        r0 = r81;
        if (r0 >= r5) goto L_0x20e2;
    L_0x20d9:
        r0 = r167;
        r5 = r0.messages;
        r0 = r81;
        r5.remove(r0);
    L_0x20e2:
        r158 = 1;
    L_0x20e4:
        r4 = r4 + 1;
        goto L_0x1ff2;
    L_0x20e8:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = r5.id;
        r0 = r41;
        if (r0 != r5) goto L_0x0015;
    L_0x20f2:
        r97 = 0;
        goto L_0x1fed;
    L_0x20f6:
        if (r41 == 0) goto L_0x1fed;
    L_0x20f8:
        goto L_0x0015;
    L_0x20fa:
        if (r119 == 0) goto L_0x2173;
    L_0x20fc:
        r5 = r119.entrySet();
        r5 = r5.iterator();
    L_0x2104:
        r6 = r5.hasNext();
        if (r6 == 0) goto L_0x2173;
    L_0x210a:
        r64 = r5.next();
        r64 = (java.util.Map.Entry) r64;
        r72 = r64.getValue();
        r72 = (org.telegram.messenger.MessageObject.GroupedMessages) r72;
        r0 = r72;
        r6 = r0.messages;
        r6 = r6.isEmpty();
        if (r6 == 0) goto L_0x2130;
    L_0x2120:
        r0 = r167;
        r6 = r0.groupedMessagesMap;
        r0 = r72;
        r12 = r0.groupId;
        r7 = java.lang.Long.valueOf(r12);
        r6.remove(r7);
        goto L_0x2104;
    L_0x2130:
        r72.calculate();
        r0 = r72;
        r6 = r0.messages;
        r0 = r72;
        r7 = r0.messages;
        r7 = r7.size();
        r7 = r7 + -1;
        r107 = r6.get(r7);
        r107 = (org.telegram.messenger.MessageObject) r107;
        r0 = r167;
        r6 = r0.messages;
        r0 = r107;
        r81 = r6.indexOf(r0);
        if (r81 < 0) goto L_0x2104;
    L_0x2153:
        r0 = r167;
        r6 = r0.chatAdapter;
        if (r6 == 0) goto L_0x2104;
    L_0x2159:
        r0 = r167;
        r6 = r0.chatAdapter;
        r0 = r167;
        r7 = r0.chatAdapter;
        r7 = r7.messagesStartRow;
        r7 = r7 + r81;
        r0 = r72;
        r8 = r0.messages;
        r8 = r8.size();
        r6.notifyItemRangeChanged(r7, r8);
        goto L_0x2104;
    L_0x2173:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.isEmpty();
        if (r5 == 0) goto L_0x2242;
    L_0x217d:
        r0 = r167;
        r5 = r0.endReached;
        r6 = 0;
        r5 = r5[r6];
        if (r5 != 0) goto L_0x227c;
    L_0x2186:
        r0 = r167;
        r5 = r0.loading;
        if (r5 != 0) goto L_0x227c;
    L_0x218c:
        r0 = r167;
        r5 = r0.progressView;
        if (r5 == 0) goto L_0x219a;
    L_0x2192:
        r0 = r167;
        r5 = r0.progressView;
        r6 = 4;
        r5.setVisibility(r6);
    L_0x219a:
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x21a8;
    L_0x21a0:
        r0 = r167;
        r5 = r0.chatListView;
        r6 = 0;
        r5.setEmptyView(r6);
    L_0x21a8:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x2256;
    L_0x21ae:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = 0;
        r0 = r167;
        r7 = r0.maxMessageId;
        r8 = 1;
        r11 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r7[r8] = r11;
        r5[r6] = r11;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = 0;
        r0 = r167;
        r7 = r0.minMessageId;
        r8 = 1;
        r11 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r7[r8] = r11;
        r5[r6] = r11;
    L_0x21cf:
        r0 = r167;
        r5 = r0.maxDate;
        r6 = 0;
        r0 = r167;
        r7 = r0.maxDate;
        r8 = 1;
        r11 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r7[r8] = r11;
        r5[r6] = r11;
        r0 = r167;
        r5 = r0.minDate;
        r6 = 0;
        r0 = r167;
        r7 = r0.minDate;
        r8 = 1;
        r11 = 0;
        r7[r8] = r11;
        r5[r6] = r11;
        r0 = r167;
        r5 = r0.waitingForLoad;
        r0 = r167;
        r6 = r0.lastLoadIndex;
        r6 = java.lang.Integer.valueOf(r6);
        r5.add(r6);
        r11 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r12 = r0.dialog_id;
        r14 = 30;
        r15 = 0;
        r16 = 0;
        r0 = r167;
        r5 = r0.cacheEndReached;
        r6 = 0;
        r5 = r5[r6];
        if (r5 != 0) goto L_0x2279;
    L_0x2213:
        r17 = 1;
    L_0x2215:
        r0 = r167;
        r5 = r0.minDate;
        r6 = 0;
        r18 = r5[r6];
        r0 = r167;
        r0 = r0.classGuid;
        r19 = r0;
        r20 = 0;
        r21 = 0;
        r0 = r167;
        r5 = r0.currentChat;
        r22 = org.telegram.messenger.ChatObject.isChannel(r5);
        r0 = r167;
        r0 = r0.lastLoadIndex;
        r23 = r0;
        r5 = r23 + 1;
        r0 = r167;
        r0.lastLoadIndex = r5;
        r11.loadMessages(r12, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23);
        r5 = 1;
        r0 = r167;
        r0.loading = r5;
    L_0x2242:
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x0015;
    L_0x2248:
        if (r158 == 0) goto L_0x22bb;
    L_0x224a:
        r167.removeUnreadPlane();
        r0 = r167;
        r5 = r0.chatAdapter;
        r5.notifyDataSetChanged();
        goto L_0x0015;
    L_0x2256:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = 0;
        r0 = r167;
        r7 = r0.maxMessageId;
        r8 = 1;
        r11 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r7[r8] = r11;
        r5[r6] = r11;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = 0;
        r0 = r167;
        r7 = r0.minMessageId;
        r8 = 1;
        r11 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r7[r8] = r11;
        r5[r6] = r11;
        goto L_0x21cf;
    L_0x2279:
        r17 = 0;
        goto L_0x2215;
    L_0x227c:
        r0 = r167;
        r5 = r0.botButtons;
        if (r5 == 0) goto L_0x2296;
    L_0x2282:
        r5 = 0;
        r0 = r167;
        r0.botButtons = r5;
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        if (r5 == 0) goto L_0x2296;
    L_0x228d:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r6 = 0;
        r7 = 0;
        r5.setButtons(r6, r7);
    L_0x2296:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x2242;
    L_0x229c:
        r0 = r167;
        r5 = r0.currentUser;
        if (r5 == 0) goto L_0x2242;
    L_0x22a2:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.bot;
        if (r5 == 0) goto L_0x2242;
    L_0x22aa:
        r0 = r167;
        r5 = r0.botUser;
        if (r5 != 0) goto L_0x2242;
    L_0x22b0:
        r5 = "";
        r0 = r167;
        r0.botUser = r5;
        r167.updateBottomOverlay();
        goto L_0x2242;
    L_0x22bb:
        r5 = 0;
        r0 = r167;
        r0.first_unread_id = r5;
        r5 = 0;
        r0 = r167;
        r0.last_message_id = r5;
        r5 = 0;
        r0 = r167;
        r0.createUnreadMessageAfterId = r5;
        r5 = 0;
        r0 = r167;
        r0.unread_to_load = r5;
        r0 = r167;
        r5 = r0.unreadMessageObject;
        r0 = r167;
        r0.removeMessageObject(r5);
        r5 = 0;
        r0 = r167;
        r0.unreadMessageObject = r5;
        r0 = r167;
        r5 = r0.pagedownButtonCounter;
        if (r5 == 0) goto L_0x0015;
    L_0x22e3:
        r0 = r167;
        r5 = r0.pagedownButtonCounter;
        r6 = 4;
        r5.setVisibility(r6);
        goto L_0x0015;
    L_0x22ed:
        r5 = org.telegram.messenger.NotificationCenter.messageReceivedByServer;
        r0 = r168;
        if (r0 != r5) goto L_0x24fb;
    L_0x22f3:
        r5 = 0;
        r115 = r169[r5];
        r115 = (java.lang.Integer) r115;
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r0 = r115;
        r124 = r5.get(r0);
        r124 = (org.telegram.messenger.MessageObject) r124;
        if (r124 == 0) goto L_0x24d8;
    L_0x2309:
        r5 = 1;
        r120 = r169[r5];
        r120 = (java.lang.Integer) r120;
        r0 = r120;
        r1 = r115;
        r5 = r0.equals(r1);
        if (r5 != 0) goto L_0x2397;
    L_0x2318:
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r0 = r120;
        r5 = r5.containsKey(r0);
        if (r5 == 0) goto L_0x2397;
    L_0x2327:
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r0 = r115;
        r143 = r5.remove(r0);
        r143 = (org.telegram.messenger.MessageObject) r143;
        if (r143 == 0) goto L_0x0015;
    L_0x2338:
        r0 = r167;
        r5 = r0.messages;
        r0 = r143;
        r81 = r5.indexOf(r0);
        r0 = r167;
        r5 = r0.messages;
        r0 = r81;
        r5.remove(r0);
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r143;
        r6 = r0.dateKey;
        r57 = r5.get(r6);
        r57 = (java.util.ArrayList) r57;
        r0 = r57;
        r1 = r124;
        r0.remove(r1);
        r5 = r57.isEmpty();
        if (r5 == 0) goto L_0x2388;
    L_0x2366:
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r124;
        r6 = r0.dateKey;
        r5.remove(r6);
        if (r81 < 0) goto L_0x2388;
    L_0x2373:
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.size();
        r0 = r81;
        if (r0 >= r5) goto L_0x2388;
    L_0x237f:
        r0 = r167;
        r5 = r0.messages;
        r0 = r81;
        r5.remove(r0);
    L_0x2388:
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x0015;
    L_0x238e:
        r0 = r167;
        r5 = r0.chatAdapter;
        r5.notifyDataSetChanged();
        goto L_0x0015;
    L_0x2397:
        r5 = 2;
        r121 = r169[r5];
        r121 = (org.telegram.tgnet.TLRPC$Message) r121;
        r104 = 0;
        r159 = 0;
        if (r121 == 0) goto L_0x245c;
    L_0x23a2:
        r5 = r124.isForwarded();	 Catch:{ Exception -> 0x24f5 }
        if (r5 == 0) goto L_0x24ed;
    L_0x23a8:
        r0 = r124;
        r5 = r0.messageOwner;	 Catch:{ Exception -> 0x24f5 }
        r5 = r5.reply_markup;	 Catch:{ Exception -> 0x24f5 }
        if (r5 != 0) goto L_0x23b6;
    L_0x23b0:
        r0 = r121;
        r5 = r0.reply_markup;	 Catch:{ Exception -> 0x24f5 }
        if (r5 != 0) goto L_0x23c6;
    L_0x23b6:
        r0 = r124;
        r5 = r0.messageOwner;	 Catch:{ Exception -> 0x24f5 }
        r5 = r5.message;	 Catch:{ Exception -> 0x24f5 }
        r0 = r121;
        r6 = r0.message;	 Catch:{ Exception -> 0x24f5 }
        r5 = r5.equals(r6);	 Catch:{ Exception -> 0x24f5 }
        if (r5 != 0) goto L_0x24ed;
    L_0x23c6:
        r159 = 1;
    L_0x23c8:
        if (r159 != 0) goto L_0x2407;
    L_0x23ca:
        r0 = r124;
        r5 = r0.messageOwner;	 Catch:{ Exception -> 0x24f5 }
        r5 = r5.params;	 Catch:{ Exception -> 0x24f5 }
        if (r5 == 0) goto L_0x23e1;
    L_0x23d2:
        r0 = r124;
        r5 = r0.messageOwner;	 Catch:{ Exception -> 0x24f5 }
        r5 = r5.params;	 Catch:{ Exception -> 0x24f5 }
        r6 = "query_id";
        r5 = r5.containsKey(r6);	 Catch:{ Exception -> 0x24f5 }
        if (r5 != 0) goto L_0x2407;
    L_0x23e1:
        r0 = r121;
        r5 = r0.media;	 Catch:{ Exception -> 0x24f5 }
        if (r5 == 0) goto L_0x24f1;
    L_0x23e7:
        r0 = r124;
        r5 = r0.messageOwner;	 Catch:{ Exception -> 0x24f5 }
        r5 = r5.media;	 Catch:{ Exception -> 0x24f5 }
        if (r5 == 0) goto L_0x24f1;
    L_0x23ef:
        r0 = r121;
        r5 = r0.media;	 Catch:{ Exception -> 0x24f5 }
        r5 = r5.getClass();	 Catch:{ Exception -> 0x24f5 }
        r0 = r124;
        r6 = r0.messageOwner;	 Catch:{ Exception -> 0x24f5 }
        r6 = r6.media;	 Catch:{ Exception -> 0x24f5 }
        r6 = r6.getClass();	 Catch:{ Exception -> 0x24f5 }
        r5 = r5.equals(r6);	 Catch:{ Exception -> 0x24f5 }
        if (r5 != 0) goto L_0x24f1;
    L_0x2407:
        r104 = 1;
    L_0x2409:
        r6 = r124.getGroupId();
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x2442;
    L_0x2413:
        r0 = r121;
        r6 = r0.grouped_id;
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x2442;
    L_0x241d:
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r6 = r124.getGroupId();
        r6 = java.lang.Long.valueOf(r6);
        r128 = r5.get(r6);
        r128 = (org.telegram.messenger.MessageObject.GroupedMessages) r128;
        if (r128 == 0) goto L_0x2442;
    L_0x2431:
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r0 = r121;
        r6 = r0.grouped_id;
        r6 = java.lang.Long.valueOf(r6);
        r0 = r128;
        r5.put(r6, r0);
    L_0x2442:
        r0 = r121;
        r1 = r124;
        r1.messageOwner = r0;
        r5 = 1;
        r0 = r124;
        r0.generateThumbs(r5);
        r124.setType();
        r0 = r121;
        r5 = r0.media;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGame;
        if (r5 == 0) goto L_0x245c;
    L_0x2459:
        r124.applyNewText();
    L_0x245c:
        if (r159 == 0) goto L_0x2461;
    L_0x245e:
        r124.measureInlineBotButtons();
    L_0x2461:
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r0 = r115;
        r5.remove(r0);
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r0 = r120;
        r1 = r124;
        r5.put(r0, r1);
        r0 = r124;
        r5 = r0.messageOwner;
        r6 = r120.intValue();
        r5.id = r6;
        r0 = r124;
        r5 = r0.messageOwner;
        r6 = 0;
        r5.send_state = r6;
        r0 = r104;
        r1 = r124;
        r1.forceUpdate = r0;
        r105 = new java.util.ArrayList;
        r105.<init>();
        r0 = r105;
        r1 = r124;
        r0.add(r1);
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x24ad;
    L_0x24a4:
        r0 = r167;
        r6 = r0.dialog_id;
        r0 = r105;
        org.telegram.messenger.query.MessagesQuery.loadReplyMessagesForMessages(r0, r6);
    L_0x24ad:
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x24bc;
    L_0x24b3:
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r124;
        r5.updateRowWithMessageObject(r0);
    L_0x24bc:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        if (r5 == 0) goto L_0x24d1;
    L_0x24c2:
        if (r104 == 0) goto L_0x24d1;
    L_0x24c4:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r5 = r5.findFirstVisibleItemPosition();
        if (r5 != 0) goto L_0x24d1;
    L_0x24ce:
        r167.moveScrollToLastMessage();
    L_0x24d1:
        r5 = org.telegram.messenger.NotificationsController.getInstance();
        r5.playOutChatSound();
    L_0x24d8:
        r0 = r167;
        r5 = r0.isAdvancedForward;
        if (r5 == 0) goto L_0x0015;
    L_0x24de:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r6 = "";
        r5.setFieldText(r6);
        r167.finishFragment();
        goto L_0x0015;
    L_0x24ed:
        r159 = 0;
        goto L_0x23c8;
    L_0x24f1:
        r104 = 0;
        goto L_0x2409;
    L_0x24f5:
        r59 = move-exception;
        org.telegram.messenger.FileLog.e(r59);
        goto L_0x2409;
    L_0x24fb:
        r5 = org.telegram.messenger.NotificationCenter.messageReceivedByAck;
        r0 = r168;
        if (r0 != r5) goto L_0x252f;
    L_0x2501:
        r5 = 0;
        r115 = r169[r5];
        r115 = (java.lang.Integer) r115;
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r0 = r115;
        r124 = r5.get(r0);
        r124 = (org.telegram.messenger.MessageObject) r124;
        if (r124 == 0) goto L_0x0015;
    L_0x2517:
        r0 = r124;
        r5 = r0.messageOwner;
        r6 = 0;
        r5.send_state = r6;
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x0015;
    L_0x2524:
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r124;
        r5.updateRowWithMessageObject(r0);
        goto L_0x0015;
    L_0x252f:
        r5 = org.telegram.messenger.NotificationCenter.messageSendError;
        r0 = r168;
        if (r0 != r5) goto L_0x2557;
    L_0x2535:
        r5 = 0;
        r115 = r169[r5];
        r115 = (java.lang.Integer) r115;
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r0 = r115;
        r124 = r5.get(r0);
        r124 = (org.telegram.messenger.MessageObject) r124;
        if (r124 == 0) goto L_0x0015;
    L_0x254b:
        r0 = r124;
        r5 = r0.messageOwner;
        r6 = 2;
        r5.send_state = r6;
        r167.updateVisibleRows();
        goto L_0x0015;
    L_0x2557:
        r5 = org.telegram.messenger.NotificationCenter.chatInfoDidLoaded;
        r0 = r168;
        if (r0 != r5) goto L_0x2813;
    L_0x255d:
        r5 = 0;
        r45 = r169[r5];
        r45 = (org.telegram.tgnet.TLRPC$ChatFull) r45;
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x0015;
    L_0x2568:
        r0 = r45;
        r5 = r0.id;
        r0 = r167;
        r6 = r0.currentChat;
        r6 = r6.id;
        if (r5 != r6) goto L_0x0015;
    L_0x2574:
        r0 = r45;
        r5 = r0 instanceof org.telegram.tgnet.TLRPC$TL_channelFull;
        if (r5 == 0) goto L_0x25ec;
    L_0x257a:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = r5.megagroup;
        if (r5 == 0) goto L_0x25d6;
    L_0x2582:
        r88 = 0;
        r0 = r45;
        r5 = r0.participants;
        if (r5 == 0) goto L_0x25ae;
    L_0x258a:
        r4 = 0;
    L_0x258b:
        r0 = r45;
        r5 = r0.participants;
        r5 = r5.participants;
        r5 = r5.size();
        if (r4 >= r5) goto L_0x25ae;
    L_0x2597:
        r0 = r45;
        r5 = r0.participants;
        r5 = r5.participants;
        r5 = r5.get(r4);
        r5 = (org.telegram.tgnet.TLRPC$ChatParticipant) r5;
        r5 = r5.date;
        r0 = r88;
        r88 = java.lang.Math.max(r5, r0);
        r4 = r4 + 1;
        goto L_0x258b;
    L_0x25ae:
        if (r88 == 0) goto L_0x25c5;
    L_0x25b0:
        r6 = java.lang.System.currentTimeMillis();
        r12 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r6 = r6 / r12;
        r0 = r88;
        r12 = (long) r0;
        r6 = r6 - r12;
        r6 = java.lang.Math.abs(r6);
        r12 = 3600; // 0xe10 float:5.045E-42 double:1.7786E-320;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 <= 0) goto L_0x25d6;
    L_0x25c5:
        r5 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r6 = r0.currentChat;
        r6 = r6.id;
        r6 = java.lang.Integer.valueOf(r6);
        r5.loadChannelParticipants(r6);
    L_0x25d6:
        r0 = r45;
        r5 = r0.participants;
        if (r5 != 0) goto L_0x25ec;
    L_0x25dc:
        r0 = r167;
        r5 = r0.info;
        if (r5 == 0) goto L_0x25ec;
    L_0x25e2:
        r0 = r167;
        r5 = r0.info;
        r5 = r5.participants;
        r0 = r45;
        r0.participants = r5;
    L_0x25ec:
        r0 = r45;
        r1 = r167;
        r1.info = r0;
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        if (r5 == 0) goto L_0x2603;
    L_0x25f8:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r0 = r167;
        r6 = r0.info;
        r5.setChatInfo(r6);
    L_0x2603:
        r0 = r167;
        r5 = r0.mentionsAdapter;
        if (r5 == 0) goto L_0x2614;
    L_0x2609:
        r0 = r167;
        r5 = r0.mentionsAdapter;
        r0 = r167;
        r6 = r0.info;
        r5.setChatInfo(r6);
    L_0x2614:
        r5 = 3;
        r5 = r169[r5];
        r5 = r5 instanceof org.telegram.messenger.MessageObject;
        if (r5 == 0) goto L_0x26c2;
    L_0x261b:
        r5 = 3;
        r5 = r169[r5];
        r5 = (org.telegram.messenger.MessageObject) r5;
        r0 = r167;
        r0.pinnedMessageObject = r5;
        r5 = 0;
        r0 = r167;
        r0.updatePinnedMessageView(r5);
    L_0x262a:
        r0 = r167;
        r5 = r0.isDownloadManager;
        if (r5 != 0) goto L_0x2644;
    L_0x2630:
        r0 = r167;
        r5 = r0.avatarContainer;
        if (r5 == 0) goto L_0x2644;
    L_0x2636:
        r0 = r167;
        r5 = r0.avatarContainer;
        r5.updateOnlineCount();
        r0 = r167;
        r5 = r0.avatarContainer;
        r5.updateSubtitle();
    L_0x2644:
        r0 = r167;
        r5 = r0.isBroadcast;
        if (r5 == 0) goto L_0x2655;
    L_0x264a:
        r5 = org.telegram.messenger.SendMessagesHelper.getInstance();
        r0 = r167;
        r6 = r0.info;
        r5.setCurrentChatInfo(r6);
    L_0x2655:
        r0 = r167;
        r5 = r0.info;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_chatFull;
        if (r5 == 0) goto L_0x2741;
    L_0x265d:
        r5 = 0;
        r0 = r167;
        r0.hasBotsCommands = r5;
        r0 = r167;
        r5 = r0.botInfo;
        r5.clear();
        r5 = 0;
        r0 = r167;
        r0.botsCount = r5;
        r5 = 0;
        org.telegram.ui.Components.URLSpanBotCommand.enabled = r5;
        r4 = 0;
    L_0x2672:
        r0 = r167;
        r5 = r0.info;
        r5 = r5.participants;
        r5 = r5.participants;
        r5 = r5.size();
        if (r4 >= r5) goto L_0x26ca;
    L_0x2680:
        r0 = r167;
        r5 = r0.info;
        r5 = r5.participants;
        r5 = r5.participants;
        r132 = r5.get(r4);
        r132 = (org.telegram.tgnet.TLRPC$ChatParticipant) r132;
        r5 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r132;
        r6 = r0.user_id;
        r6 = java.lang.Integer.valueOf(r6);
        r160 = r5.getUser(r6);
        if (r160 == 0) goto L_0x26bf;
    L_0x26a0:
        r0 = r160;
        r5 = r0.bot;
        if (r5 == 0) goto L_0x26bf;
    L_0x26a6:
        r5 = 1;
        org.telegram.ui.Components.URLSpanBotCommand.enabled = r5;
        r0 = r167;
        r5 = r0.botsCount;
        r5 = r5 + 1;
        r0 = r167;
        r0.botsCount = r5;
        r0 = r160;
        r5 = r0.id;
        r6 = 1;
        r0 = r167;
        r7 = r0.classGuid;
        org.telegram.messenger.query.BotQuery.loadBotInfo(r5, r6, r7);
    L_0x26bf:
        r4 = r4 + 1;
        goto L_0x2672;
    L_0x26c2:
        r5 = 1;
        r0 = r167;
        r0.updatePinnedMessageView(r5);
        goto L_0x262a;
    L_0x26ca:
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x26d7;
    L_0x26d0:
        r0 = r167;
        r5 = r0.chatListView;
        r5.invalidateViews();
    L_0x26d7:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        if (r5 == 0) goto L_0x26ec;
    L_0x26dd:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r0 = r167;
        r6 = r0.botsCount;
        r0 = r167;
        r7 = r0.hasBotsCommands;
        r5.setBotsCount(r6, r7);
    L_0x26ec:
        r0 = r167;
        r5 = r0.mentionsAdapter;
        if (r5 == 0) goto L_0x26fd;
    L_0x26f2:
        r0 = r167;
        r5 = r0.mentionsAdapter;
        r0 = r167;
        r6 = r0.botsCount;
        r5.setBotsCount(r6);
    L_0x26fd:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = org.telegram.messenger.ChatObject.isChannel(r5);
        if (r5 == 0) goto L_0x0015;
    L_0x2707:
        r0 = r167;
        r6 = r0.mergeDialogId;
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x2711:
        r0 = r167;
        r5 = r0.info;
        r5 = r5.migrated_from_chat_id;
        if (r5 == 0) goto L_0x0015;
    L_0x2719:
        r0 = r167;
        r5 = r0.info;
        r5 = r5.migrated_from_chat_id;
        r5 = -r5;
        r6 = (long) r5;
        r0 = r167;
        r0.mergeDialogId = r6;
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = 1;
        r0 = r167;
        r7 = r0.info;
        r7 = r7.migrated_from_max_id;
        r5[r6] = r7;
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x0015;
    L_0x2738:
        r0 = r167;
        r5 = r0.chatAdapter;
        r5.notifyDataSetChanged();
        goto L_0x0015;
    L_0x2741:
        r0 = r167;
        r5 = r0.info;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_channelFull;
        if (r5 == 0) goto L_0x26d7;
    L_0x2749:
        r5 = 0;
        r0 = r167;
        r0.hasBotsCommands = r5;
        r0 = r167;
        r5 = r0.botInfo;
        r5.clear();
        r5 = 0;
        r0 = r167;
        r0.botsCount = r5;
        r0 = r167;
        r5 = r0.info;
        r5 = r5.bot_info;
        r5 = r5.isEmpty();
        if (r5 != 0) goto L_0x27d9;
    L_0x2766:
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x27d9;
    L_0x276c:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = r5.megagroup;
        if (r5 == 0) goto L_0x27d9;
    L_0x2774:
        r5 = 1;
    L_0x2775:
        org.telegram.ui.Components.URLSpanBotCommand.enabled = r5;
        r0 = r167;
        r5 = r0.info;
        r5 = r5.bot_info;
        r5 = r5.size();
        r0 = r167;
        r0.botsCount = r5;
        r4 = 0;
    L_0x2786:
        r0 = r167;
        r5 = r0.info;
        r5 = r5.bot_info;
        r5 = r5.size();
        if (r4 >= r5) goto L_0x27db;
    L_0x2792:
        r0 = r167;
        r5 = r0.info;
        r5 = r5.bot_info;
        r32 = r5.get(r4);
        r32 = (org.telegram.tgnet.TLRPC$BotInfo) r32;
        r0 = r32;
        r5 = r0.commands;
        r5 = r5.isEmpty();
        if (r5 != 0) goto L_0x27c5;
    L_0x27a8:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = org.telegram.messenger.ChatObject.isChannel(r5);
        if (r5 == 0) goto L_0x27c0;
    L_0x27b2:
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x27c5;
    L_0x27b8:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = r5.megagroup;
        if (r5 == 0) goto L_0x27c5;
    L_0x27c0:
        r5 = 1;
        r0 = r167;
        r0.hasBotsCommands = r5;
    L_0x27c5:
        r0 = r167;
        r5 = r0.botInfo;
        r0 = r32;
        r6 = r0.user_id;
        r6 = java.lang.Integer.valueOf(r6);
        r0 = r32;
        r5.put(r6, r0);
        r4 = r4 + 1;
        goto L_0x2786;
    L_0x27d9:
        r5 = 0;
        goto L_0x2775;
    L_0x27db:
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x27e8;
    L_0x27e1:
        r0 = r167;
        r5 = r0.chatListView;
        r5.invalidateViews();
    L_0x27e8:
        r0 = r167;
        r5 = r0.mentionsAdapter;
        if (r5 == 0) goto L_0x26d7;
    L_0x27ee:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = org.telegram.messenger.ChatObject.isChannel(r5);
        if (r5 == 0) goto L_0x2806;
    L_0x27f8:
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x26d7;
    L_0x27fe:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = r5.megagroup;
        if (r5 == 0) goto L_0x26d7;
    L_0x2806:
        r0 = r167;
        r5 = r0.mentionsAdapter;
        r0 = r167;
        r6 = r0.botInfo;
        r5.setBotInfo(r6);
        goto L_0x26d7;
    L_0x2813:
        r5 = org.telegram.messenger.NotificationCenter.chatInfoCantLoad;
        r0 = r168;
        if (r0 != r5) goto L_0x28db;
    L_0x2819:
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r46 = r5.intValue();
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x0015;
    L_0x2828:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = r5.id;
        r0 = r46;
        if (r5 != r0) goto L_0x0015;
    L_0x2832:
        r5 = 1;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r141 = r5.intValue();
        r5 = r167.getParentActivity();
        if (r5 == 0) goto L_0x0015;
    L_0x2841:
        r0 = r167;
        r5 = r0.closeChatDialog;
        if (r5 != 0) goto L_0x0015;
    L_0x2847:
        r34 = new org.telegram.ui.ActionBar.AlertDialog$Builder;
        r5 = r167.getParentActivity();
        r0 = r34;
        r0.<init>(r5);
        r5 = "AppName";
        r6 = 2131230884; // 0x7f0800a4 float:1.8077833E38 double:1.052967963E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r0 = r34;
        r0.setTitle(r5);
        if (r141 != 0) goto L_0x28b1;
    L_0x2863:
        r5 = "ChannelCantOpenPrivate";
        r6 = 2131231061; // 0x7f080155 float:1.8078192E38 double:1.0529680506E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r0 = r34;
        r0.setMessage(r5);
    L_0x2872:
        r5 = "OK";
        r6 = 2131232017; // 0x7f080511 float:1.8080131E38 double:1.052968523E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r6 = 0;
        r0 = r34;
        r0.setPositiveButton(r5, r6);
        r5 = r34.create();
        r0 = r167;
        r0.closeChatDialog = r5;
        r0 = r167;
        r0.showDialog(r5);
        r5 = 0;
        r0 = r167;
        r0.loading = r5;
        r0 = r167;
        r5 = r0.progressView;
        if (r5 == 0) goto L_0x28a2;
    L_0x289a:
        r0 = r167;
        r5 = r0.progressView;
        r6 = 4;
        r5.setVisibility(r6);
    L_0x28a2:
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x0015;
    L_0x28a8:
        r0 = r167;
        r5 = r0.chatAdapter;
        r5.notifyDataSetChanged();
        goto L_0x0015;
    L_0x28b1:
        r5 = 1;
        r0 = r141;
        if (r0 != r5) goto L_0x28c6;
    L_0x28b6:
        r5 = "ChannelCantOpenNa";
        r6 = 2131231060; // 0x7f080154 float:1.807819E38 double:1.05296805E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r0 = r34;
        r0.setMessage(r5);
        goto L_0x2872;
    L_0x28c6:
        r5 = 2;
        r0 = r141;
        if (r0 != r5) goto L_0x2872;
    L_0x28cb:
        r5 = "ChannelCantOpenBanned";
        r6 = 2131231059; // 0x7f080153 float:1.8078188E38 double:1.0529680496E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r0 = r34;
        r0.setMessage(r5);
        goto L_0x2872;
    L_0x28db:
        r5 = org.telegram.messenger.NotificationCenter.contactsDidLoaded;
        r0 = r168;
        if (r0 != r5) goto L_0x2902;
    L_0x28e1:
        r167.updateContactStatus();
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x28ed;
    L_0x28ea:
        r167.updateSpamView();
    L_0x28ed:
        r0 = r167;
        r5 = r0.isDownloadManager;
        if (r5 != 0) goto L_0x0015;
    L_0x28f3:
        r0 = r167;
        r5 = r0.avatarContainer;
        if (r5 == 0) goto L_0x0015;
    L_0x28f9:
        r0 = r167;
        r5 = r0.avatarContainer;
        r5.updateSubtitle();
        goto L_0x0015;
    L_0x2902:
        r5 = org.telegram.messenger.NotificationCenter.encryptedChatUpdated;
        r0 = r168;
        if (r0 != r5) goto L_0x29a0;
    L_0x2908:
        r5 = 0;
        r44 = r169[r5];
        r44 = (org.telegram.tgnet.TLRPC$EncryptedChat) r44;
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x0015;
    L_0x2913:
        r0 = r44;
        r5 = r0.id;
        r0 = r167;
        r6 = r0.currentEncryptedChat;
        r6 = r6.id;
        if (r5 != r6) goto L_0x0015;
    L_0x291f:
        r0 = r44;
        r1 = r167;
        r1.currentEncryptedChat = r0;
        r167.updateContactStatus();
        r167.updateSecretStatus();
        r167.initStickers();
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        if (r5 == 0) goto L_0x296c;
    L_0x2934:
        r0 = r167;
        r7 = r0.chatActivityEnterView;
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x294c;
    L_0x293e:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        r5 = r5.layer;
        r5 = org.telegram.messenger.AndroidUtilities.getPeerLayerVersion(r5);
        r6 = 23;
        if (r5 < r6) goto L_0x299a;
    L_0x294c:
        r5 = 1;
    L_0x294d:
        r0 = r167;
        r6 = r0.currentEncryptedChat;
        if (r6 == 0) goto L_0x2961;
    L_0x2953:
        r0 = r167;
        r6 = r0.currentEncryptedChat;
        r6 = r6.layer;
        r6 = org.telegram.messenger.AndroidUtilities.getPeerLayerVersion(r6);
        r8 = 46;
        if (r6 < r8) goto L_0x299c;
    L_0x2961:
        r6 = 1;
    L_0x2962:
        r7.setAllowStickersAndGifs(r5, r6);
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r5.checkRoundVideo();
    L_0x296c:
        r0 = r167;
        r5 = r0.mentionsAdapter;
        if (r5 == 0) goto L_0x0015;
    L_0x2972:
        r0 = r167;
        r6 = r0.mentionsAdapter;
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r5 = r5.isEditingMessage();
        if (r5 != 0) goto L_0x299e;
    L_0x2980:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x2994;
    L_0x2986:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        r5 = r5.layer;
        r5 = org.telegram.messenger.AndroidUtilities.getPeerLayerVersion(r5);
        r7 = 46;
        if (r5 < r7) goto L_0x299e;
    L_0x2994:
        r5 = 1;
    L_0x2995:
        r6.setNeedBotContext(r5);
        goto L_0x0015;
    L_0x299a:
        r5 = 0;
        goto L_0x294d;
    L_0x299c:
        r6 = 0;
        goto L_0x2962;
    L_0x299e:
        r5 = 0;
        goto L_0x2995;
    L_0x29a0:
        r5 = org.telegram.messenger.NotificationCenter.messagesReadEncrypted;
        r0 = r168;
        if (r0 != r5) goto L_0x2a03;
    L_0x29a6:
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r62 = r5.intValue();
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 == 0) goto L_0x0015;
    L_0x29b5:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        r5 = r5.id;
        r0 = r62;
        if (r5 != r0) goto L_0x0015;
    L_0x29bf:
        r5 = 1;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r54 = r5.intValue();
        r0 = r167;
        r5 = r0.messages;
        r5 = r5.iterator();
    L_0x29d0:
        r6 = r5.hasNext();
        if (r6 == 0) goto L_0x29ee;
    L_0x29d6:
        r124 = r5.next();
        r124 = (org.telegram.messenger.MessageObject) r124;
        r6 = r124.isOut();
        if (r6 == 0) goto L_0x29d0;
    L_0x29e2:
        r6 = r124.isOut();
        if (r6 == 0) goto L_0x29f3;
    L_0x29e8:
        r6 = r124.isUnread();
        if (r6 != 0) goto L_0x29f3;
    L_0x29ee:
        r167.updateVisibleRows();
        goto L_0x0015;
    L_0x29f3:
        r0 = r124;
        r6 = r0.messageOwner;
        r6 = r6.date;
        r6 = r6 + -1;
        r0 = r54;
        if (r6 > r0) goto L_0x29d0;
    L_0x29ff:
        r124.setIsRead();
        goto L_0x29d0;
    L_0x2a03:
        r5 = org.telegram.messenger.NotificationCenter.removeAllMessagesFromDialog;
        r0 = r168;
        if (r0 != r5) goto L_0x2bc3;
    L_0x2a09:
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r60 = r5.longValue();
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r6 > r60 ? 1 : (r6 == r60 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x2a1a:
        r0 = r167;
        r5 = r0.messages;
        r5.clear();
        r0 = r167;
        r5 = r0.waitingForLoad;
        r5.clear();
        r0 = r167;
        r5 = r0.messagesByDays;
        r5.clear();
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r5.clear();
        r4 = 1;
    L_0x2a37:
        if (r4 < 0) goto L_0x2a98;
    L_0x2a39:
        r0 = r167;
        r5 = r0.messagesDict;
        r5 = r5[r4];
        r5.clear();
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x2a86;
    L_0x2a48:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r5[r4] = r6;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r5[r4] = r6;
    L_0x2a59:
        r0 = r167;
        r5 = r0.maxDate;
        r6 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r5[r4] = r6;
        r0 = r167;
        r5 = r0.minDate;
        r6 = 0;
        r5[r4] = r6;
        r0 = r167;
        r5 = r0.selectedMessagesIds;
        r5 = r5[r4];
        r5.clear();
        r0 = r167;
        r5 = r0.selectedMessagesCanCopyIds;
        r5 = r5[r4];
        r5.clear();
        r0 = r167;
        r5 = r0.selectedMessagesCanStarIds;
        r5 = r5[r4];
        r5.clear();
        r4 = r4 + -1;
        goto L_0x2a37;
    L_0x2a86:
        r0 = r167;
        r5 = r0.maxMessageId;
        r6 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r5[r4] = r6;
        r0 = r167;
        r5 = r0.minMessageId;
        r6 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r5[r4] = r6;
        goto L_0x2a59;
    L_0x2a98:
        r5 = 0;
        r0 = r167;
        r0.cantDeleteMessagesCount = r5;
        r5 = 0;
        r0 = r167;
        r0.canEditMessagesCount = r5;
        r0 = r167;
        r5 = r0.actionBar;
        r5.hideActionMode();
        r5 = 1;
        r0 = r167;
        r0.updatePinnedMessageView(r5);
        r0 = r167;
        r5 = r0.botButtons;
        if (r5 == 0) goto L_0x2ac9;
    L_0x2ab5:
        r5 = 0;
        r0 = r167;
        r0.botButtons = r5;
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        if (r5 == 0) goto L_0x2ac9;
    L_0x2ac0:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r6 = 0;
        r7 = 0;
        r5.setButtons(r6, r7);
    L_0x2ac9:
        r5 = 1;
        r5 = r169[r5];
        r5 = (java.lang.Boolean) r5;
        r5 = r5.booleanValue();
        if (r5 == 0) goto L_0x2ba9;
    L_0x2ad4:
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x2af5;
    L_0x2ada:
        r0 = r167;
        r6 = r0.progressView;
        r0 = r167;
        r5 = r0.chatAdapter;
        r5 = r5.botInfoRow;
        r7 = -1;
        if (r5 != r7) goto L_0x2b11;
    L_0x2ae9:
        r5 = 0;
    L_0x2aea:
        r6.setVisibility(r5);
        r0 = r167;
        r5 = r0.chatListView;
        r6 = 0;
        r5.setEmptyView(r6);
    L_0x2af5:
        r4 = 0;
    L_0x2af6:
        r5 = 2;
        if (r4 >= r5) goto L_0x2b13;
    L_0x2af9:
        r0 = r167;
        r5 = r0.endReached;
        r6 = 0;
        r5[r4] = r6;
        r0 = r167;
        r5 = r0.cacheEndReached;
        r6 = 0;
        r5[r4] = r6;
        r0 = r167;
        r5 = r0.forwardEndReached;
        r6 = 1;
        r5[r4] = r6;
        r4 = r4 + 1;
        goto L_0x2af6;
    L_0x2b11:
        r5 = 4;
        goto L_0x2aea;
    L_0x2b13:
        r5 = 1;
        r0 = r167;
        r0.first = r5;
        r5 = 1;
        r0 = r167;
        r0.firstLoading = r5;
        r5 = 1;
        r0 = r167;
        r0.loading = r5;
        r5 = 0;
        r0 = r167;
        r0.startLoadFromMessageId = r5;
        r5 = 0;
        r0 = r167;
        r0.needSelectFromMessageId = r5;
        r0 = r167;
        r5 = r0.waitingForLoad;
        r0 = r167;
        r6 = r0.lastLoadIndex;
        r6 = java.lang.Integer.valueOf(r6);
        r5.add(r6);
        r11 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r167;
        r12 = r0.dialog_id;
        r5 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r5 == 0) goto L_0x2ba6;
    L_0x2b49:
        r14 = 30;
    L_0x2b4b:
        r15 = 0;
        r16 = 0;
        r17 = 1;
        r18 = 0;
        r0 = r167;
        r0 = r0.classGuid;
        r19 = r0;
        r20 = 2;
        r21 = 0;
        r0 = r167;
        r5 = r0.currentChat;
        r22 = org.telegram.messenger.ChatObject.isChannel(r5);
        r0 = r167;
        r0 = r0.lastLoadIndex;
        r23 = r0;
        r5 = r23 + 1;
        r0 = r167;
        r0.lastLoadIndex = r5;
        r11.loadMessages(r12, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23);
    L_0x2b73:
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x2b80;
    L_0x2b79:
        r0 = r167;
        r5 = r0.chatAdapter;
        r5.notifyDataSetChanged();
    L_0x2b80:
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x0015;
    L_0x2b86:
        r0 = r167;
        r5 = r0.currentUser;
        if (r5 == 0) goto L_0x0015;
    L_0x2b8c:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.bot;
        if (r5 == 0) goto L_0x0015;
    L_0x2b94:
        r0 = r167;
        r5 = r0.botUser;
        if (r5 != 0) goto L_0x0015;
    L_0x2b9a:
        r5 = "";
        r0 = r167;
        r0.botUser = r5;
        r167.updateBottomOverlay();
        goto L_0x0015;
    L_0x2ba6:
        r14 = 20;
        goto L_0x2b4b;
    L_0x2ba9:
        r0 = r167;
        r5 = r0.progressView;
        if (r5 == 0) goto L_0x2b73;
    L_0x2baf:
        r0 = r167;
        r5 = r0.progressView;
        r6 = 4;
        r5.setVisibility(r6);
        r0 = r167;
        r5 = r0.chatListView;
        r0 = r167;
        r6 = r0.emptyViewContainer;
        r5.setEmptyView(r6);
        goto L_0x2b73;
    L_0x2bc3:
        r5 = org.telegram.messenger.NotificationCenter.screenshotTook;
        r0 = r168;
        if (r0 != r5) goto L_0x2bce;
    L_0x2bc9:
        r167.updateInformationForScreenshotDetector();
        goto L_0x0015;
    L_0x2bce:
        r5 = org.telegram.messenger.NotificationCenter.blockedUsersDidLoaded;
        r0 = r168;
        if (r0 != r5) goto L_0x2c05;
    L_0x2bd4:
        r0 = r167;
        r5 = r0.currentUser;
        if (r5 == 0) goto L_0x0015;
    L_0x2bda:
        r0 = r167;
        r0 = r0.userBlocked;
        r130 = r0;
        r5 = org.telegram.messenger.MessagesController.getInstance();
        r5 = r5.blockedUsers;
        r0 = r167;
        r6 = r0.currentUser;
        r6 = r6.id;
        r6 = java.lang.Integer.valueOf(r6);
        r5 = r5.contains(r6);
        r0 = r167;
        r0.userBlocked = r5;
        r0 = r167;
        r5 = r0.userBlocked;
        r0 = r130;
        if (r0 == r5) goto L_0x0015;
    L_0x2c00:
        r167.updateBottomOverlay();
        goto L_0x0015;
    L_0x2c05:
        r5 = org.telegram.messenger.NotificationCenter.FileNewChunkAvailable;
        r0 = r168;
        if (r0 != r5) goto L_0x2c54;
    L_0x2c0b:
        r5 = 0;
        r107 = r169[r5];
        r107 = (org.telegram.messenger.MessageObject) r107;
        r5 = 2;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r66 = r5.longValue();
        r6 = 0;
        r5 = (r66 > r6 ? 1 : (r66 == r6 ? 0 : -1));
        if (r5 == 0) goto L_0x0015;
    L_0x2c1f:
        r0 = r167;
        r6 = r0.dialog_id;
        r12 = r107.getDialogId();
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x2c2b:
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r6 = r107.getId();
        r6 = java.lang.Integer.valueOf(r6);
        r52 = r5.get(r6);
        r52 = (org.telegram.messenger.MessageObject) r52;
        if (r52 == 0) goto L_0x0015;
    L_0x2c42:
        r0 = r52;
        r5 = r0.messageOwner;
        r5 = r5.media;
        r5 = r5.document;
        r0 = r66;
        r6 = (int) r0;
        r5.size = r6;
        r167.updateVisibleRows();
        goto L_0x0015;
    L_0x2c54:
        r5 = org.telegram.messenger.NotificationCenter.didCreatedNewDeleteTask;
        r0 = r168;
        if (r0 != r5) goto L_0x2ce0;
    L_0x2c5a:
        r5 = 0;
        r114 = r169[r5];
        r114 = (android.util.SparseArray) r114;
        r39 = 0;
        r76 = 0;
    L_0x2c63:
        r5 = r114.size();
        r0 = r76;
        if (r0 >= r5) goto L_0x2cd9;
    L_0x2c6b:
        r0 = r114;
        r1 = r76;
        r87 = r0.keyAt(r1);
        r0 = r114;
        r1 = r87;
        r25 = r0.get(r1);
        r25 = (java.util.ArrayList) r25;
        r4 = 0;
    L_0x2c7e:
        r5 = r25.size();
        if (r4 >= r5) goto L_0x2cd6;
    L_0x2c84:
        r0 = r25;
        r5 = r0.get(r4);
        r5 = (java.lang.Long) r5;
        r112 = r5.longValue();
        if (r4 != 0) goto L_0x2cb1;
    L_0x2c92:
        r5 = 32;
        r6 = r112 >> r5;
        r0 = (int) r6;
        r41 = r0;
        if (r41 >= 0) goto L_0x2c9d;
    L_0x2c9b:
        r41 = 0;
    L_0x2c9d:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = org.telegram.messenger.ChatObject.isChannel(r5);
        if (r5 == 0) goto L_0x2cd4;
    L_0x2ca7:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = r5.id;
    L_0x2cad:
        r0 = r41;
        if (r0 != r5) goto L_0x0015;
    L_0x2cb1:
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r0 = r112;
        r6 = (int) r0;
        r6 = java.lang.Integer.valueOf(r6);
        r107 = r5.get(r6);
        r107 = (org.telegram.messenger.MessageObject) r107;
        if (r107 == 0) goto L_0x2cd1;
    L_0x2cc7:
        r0 = r107;
        r5 = r0.messageOwner;
        r0 = r87;
        r5.destroyTime = r0;
        r39 = 1;
    L_0x2cd1:
        r4 = r4 + 1;
        goto L_0x2c7e;
    L_0x2cd4:
        r5 = 0;
        goto L_0x2cad;
    L_0x2cd6:
        r76 = r76 + 1;
        goto L_0x2c63;
    L_0x2cd9:
        if (r39 == 0) goto L_0x0015;
    L_0x2cdb:
        r167.updateVisibleRows();
        goto L_0x0015;
    L_0x2ce0:
        r5 = org.telegram.messenger.NotificationCenter.messagePlayingDidStarted;
        r0 = r168;
        if (r0 != r5) goto L_0x2da3;
    L_0x2ce6:
        r5 = 0;
        r107 = r169[r5];
        r107 = (org.telegram.messenger.MessageObject) r107;
        r0 = r107;
        r6 = r0.eventId;
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x2cf5:
        r0 = r167;
        r1 = r107;
        r0.sendSecretMessageRead(r1);
        r5 = r107.isRoundVideo();
        if (r5 == 0) goto L_0x2d1c;
    L_0x2d02:
        r5 = org.telegram.messenger.MediaController.getInstance();
        r6 = 1;
        r0 = r167;
        r6 = r0.createTextureView(r6);
        r0 = r167;
        r7 = r0.aspectRatioFrameLayout;
        r0 = r167;
        r8 = r0.roundVideoContainer;
        r11 = 1;
        r5.setTextureView(r6, r7, r8, r11);
        r167.updateTextureViewPosition();
    L_0x2d1c:
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x0015;
    L_0x2d22:
        r0 = r167;
        r5 = r0.chatListView;
        r47 = r5.getChildCount();
        r4 = 0;
    L_0x2d2b:
        r0 = r47;
        if (r4 >= r0) goto L_0x2d69;
    L_0x2d2f:
        r0 = r167;
        r5 = r0.chatListView;
        r162 = r5.getChildAt(r4);
        r0 = r162;
        r5 = r0 instanceof org.telegram.ui.Cells.ChatMessageCell;
        if (r5 == 0) goto L_0x2d59;
    L_0x2d3d:
        r38 = r162;
        r38 = (org.telegram.ui.Cells.ChatMessageCell) r38;
        r110 = r38.getMessageObject();
        if (r110 == 0) goto L_0x2d59;
    L_0x2d47:
        r5 = r110.isVoice();
        if (r5 != 0) goto L_0x2d53;
    L_0x2d4d:
        r5 = r110.isMusic();
        if (r5 == 0) goto L_0x2d5c;
    L_0x2d53:
        r5 = 0;
        r0 = r38;
        r0.updateButtonState(r5);
    L_0x2d59:
        r4 = r4 + 1;
        goto L_0x2d2b;
    L_0x2d5c:
        r5 = r110.isRoundVideo();
        if (r5 == 0) goto L_0x2d59;
    L_0x2d62:
        r5 = 0;
        r0 = r38;
        r0.checkRoundVideoPlayback(r5);
        goto L_0x2d59;
    L_0x2d69:
        r0 = r167;
        r5 = r0.mentionListView;
        r47 = r5.getChildCount();
        r4 = 0;
    L_0x2d72:
        r0 = r47;
        if (r4 >= r0) goto L_0x0015;
    L_0x2d76:
        r0 = r167;
        r5 = r0.mentionListView;
        r162 = r5.getChildAt(r4);
        r0 = r162;
        r5 = r0 instanceof org.telegram.ui.Cells.ContextLinkCell;
        if (r5 == 0) goto L_0x2da0;
    L_0x2d84:
        r38 = r162;
        r38 = (org.telegram.ui.Cells.ContextLinkCell) r38;
        r110 = r38.getMessageObject();
        if (r110 == 0) goto L_0x2da0;
    L_0x2d8e:
        r5 = r110.isVoice();
        if (r5 != 0) goto L_0x2d9a;
    L_0x2d94:
        r5 = r110.isMusic();
        if (r5 == 0) goto L_0x2da0;
    L_0x2d9a:
        r5 = 0;
        r0 = r38;
        r0.updateButtonState(r5);
    L_0x2da0:
        r4 = r4 + 1;
        goto L_0x2d72;
    L_0x2da3:
        r5 = org.telegram.messenger.NotificationCenter.messagePlayingDidReset;
        r0 = r168;
        if (r0 == r5) goto L_0x2daf;
    L_0x2da9:
        r5 = org.telegram.messenger.NotificationCenter.messagePlayingPlayStateChanged;
        r0 = r168;
        if (r0 != r5) goto L_0x2e4b;
    L_0x2daf:
        r5 = org.telegram.messenger.NotificationCenter.messagePlayingDidReset;
        r0 = r168;
        if (r0 != r5) goto L_0x2db8;
    L_0x2db5:
        r167.destroyTextureView();
    L_0x2db8:
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x0015;
    L_0x2dbe:
        r0 = r167;
        r5 = r0.chatListView;
        r47 = r5.getChildCount();
        r4 = 0;
    L_0x2dc7:
        r0 = r47;
        if (r4 >= r0) goto L_0x2e11;
    L_0x2dcb:
        r0 = r167;
        r5 = r0.chatListView;
        r162 = r5.getChildAt(r4);
        r0 = r162;
        r5 = r0 instanceof org.telegram.ui.Cells.ChatMessageCell;
        if (r5 == 0) goto L_0x2df5;
    L_0x2dd9:
        r38 = r162;
        r38 = (org.telegram.ui.Cells.ChatMessageCell) r38;
        r107 = r38.getMessageObject();
        if (r107 == 0) goto L_0x2df5;
    L_0x2de3:
        r5 = r107.isVoice();
        if (r5 != 0) goto L_0x2def;
    L_0x2de9:
        r5 = r107.isMusic();
        if (r5 == 0) goto L_0x2df8;
    L_0x2def:
        r5 = 0;
        r0 = r38;
        r0.updateButtonState(r5);
    L_0x2df5:
        r4 = r4 + 1;
        goto L_0x2dc7;
    L_0x2df8:
        r5 = r107.isRoundVideo();
        if (r5 == 0) goto L_0x2df5;
    L_0x2dfe:
        r5 = org.telegram.messenger.MediaController.getInstance();
        r0 = r107;
        r5 = r5.isPlayingMessage(r0);
        if (r5 != 0) goto L_0x2df5;
    L_0x2e0a:
        r5 = 1;
        r0 = r38;
        r0.checkRoundVideoPlayback(r5);
        goto L_0x2df5;
    L_0x2e11:
        r0 = r167;
        r5 = r0.mentionListView;
        r47 = r5.getChildCount();
        r4 = 0;
    L_0x2e1a:
        r0 = r47;
        if (r4 >= r0) goto L_0x0015;
    L_0x2e1e:
        r0 = r167;
        r5 = r0.mentionListView;
        r162 = r5.getChildAt(r4);
        r0 = r162;
        r5 = r0 instanceof org.telegram.ui.Cells.ContextLinkCell;
        if (r5 == 0) goto L_0x2e48;
    L_0x2e2c:
        r38 = r162;
        r38 = (org.telegram.ui.Cells.ContextLinkCell) r38;
        r107 = r38.getMessageObject();
        if (r107 == 0) goto L_0x2e48;
    L_0x2e36:
        r5 = r107.isVoice();
        if (r5 != 0) goto L_0x2e42;
    L_0x2e3c:
        r5 = r107.isMusic();
        if (r5 == 0) goto L_0x2e48;
    L_0x2e42:
        r5 = 0;
        r0 = r38;
        r0.updateButtonState(r5);
    L_0x2e48:
        r4 = r4 + 1;
        goto L_0x2e1a;
    L_0x2e4b:
        r5 = org.telegram.messenger.NotificationCenter.messagePlayingProgressDidChanged;
        r0 = r168;
        if (r0 != r5) goto L_0x2ead;
    L_0x2e51:
        r5 = 0;
        r112 = r169[r5];
        r112 = (java.lang.Integer) r112;
        r0 = r167;
        r5 = r0.chatListView;
        if (r5 == 0) goto L_0x0015;
    L_0x2e5c:
        r0 = r167;
        r5 = r0.chatListView;
        r47 = r5.getChildCount();
        r4 = 0;
    L_0x2e65:
        r0 = r47;
        if (r4 >= r0) goto L_0x0015;
    L_0x2e69:
        r0 = r167;
        r5 = r0.chatListView;
        r162 = r5.getChildAt(r4);
        r0 = r162;
        r5 = r0 instanceof org.telegram.ui.Cells.ChatMessageCell;
        if (r5 == 0) goto L_0x2eaa;
    L_0x2e77:
        r38 = r162;
        r38 = (org.telegram.ui.Cells.ChatMessageCell) r38;
        r136 = r38.getMessageObject();
        if (r136 == 0) goto L_0x2eaa;
    L_0x2e81:
        r5 = r136.getId();
        r6 = r112.intValue();
        if (r5 != r6) goto L_0x2eaa;
    L_0x2e8b:
        r5 = org.telegram.messenger.MediaController.getInstance();
        r135 = r5.getPlayingMessageObject();
        if (r135 == 0) goto L_0x0015;
    L_0x2e95:
        r0 = r135;
        r5 = r0.audioProgress;
        r0 = r136;
        r0.audioProgress = r5;
        r0 = r135;
        r5 = r0.audioProgressSec;
        r0 = r136;
        r0.audioProgressSec = r5;
        r38.updatePlayingMessageProgress();
        goto L_0x0015;
    L_0x2eaa:
        r4 = r4 + 1;
        goto L_0x2e65;
    L_0x2ead:
        r5 = org.telegram.messenger.NotificationCenter.updateMessageMedia;
        r0 = r168;
        if (r0 != r5) goto L_0x3049;
    L_0x2eb3:
        r5 = 0;
        r106 = r169[r5];
        r106 = (org.telegram.tgnet.TLRPC$Message) r106;
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r0 = r106;
        r6 = r0.id;
        r6 = java.lang.Integer.valueOf(r6);
        r65 = r5.get(r6);
        r65 = (org.telegram.messenger.MessageObject) r65;
        if (r65 == 0) goto L_0x0015;
    L_0x2ecf:
        r0 = r65;
        r5 = r0.messageOwner;
        r0 = r106;
        r6 = r0.media;
        r5.media = r6;
        r0 = r65;
        r5 = r0.messageOwner;
        r0 = r106;
        r6 = r0.attachPath;
        r5.attachPath = r6;
        r5 = 0;
        r0 = r65;
        r0.generateThumbs(r5);
        r6 = r65.getGroupId();
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x2ff1;
    L_0x2ef3:
        r0 = r65;
        r5 = r0.photoThumbs;
        if (r5 == 0) goto L_0x2f03;
    L_0x2ef9:
        r0 = r65;
        r5 = r0.photoThumbs;
        r5 = r5.isEmpty();
        if (r5 == 0) goto L_0x2ff1;
    L_0x2f03:
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r6 = r65.getGroupId();
        r6 = java.lang.Long.valueOf(r6);
        r72 = r5.get(r6);
        r72 = (org.telegram.messenger.MessageObject.GroupedMessages) r72;
        if (r72 == 0) goto L_0x2ff1;
    L_0x2f17:
        r0 = r72;
        r5 = r0.messages;
        r0 = r65;
        r78 = r5.indexOf(r0);
        if (r78 < 0) goto L_0x2ff1;
    L_0x2f23:
        r0 = r72;
        r5 = r0.messages;
        r155 = r5.size();
        r107 = 0;
        if (r78 <= 0) goto L_0x2fb9;
    L_0x2f2f:
        r0 = r72;
        r5 = r0.messages;
        r5 = r5.size();
        r5 = r5 + -1;
        r0 = r78;
        if (r0 >= r5) goto L_0x2fb9;
    L_0x2f3d:
        r148 = new org.telegram.messenger.MessageObject$GroupedMessages;
        r148.<init>();
        r5 = org.telegram.messenger.Utilities.random;
        r6 = r5.nextLong();
        r0 = r148;
        r0.groupId = r6;
        r0 = r148;
        r5 = r0.messages;
        r0 = r72;
        r6 = r0.messages;
        r7 = r78 + 1;
        r0 = r72;
        r8 = r0.messages;
        r8 = r8.size();
        r6 = r6.subList(r7, r8);
        r5.addAll(r6);
        r31 = 0;
    L_0x2f67:
        r0 = r148;
        r5 = r0.messages;
        r5 = r5.size();
        r0 = r31;
        if (r0 >= r5) goto L_0x2f91;
    L_0x2f73:
        r0 = r148;
        r5 = r0.messages;
        r0 = r31;
        r5 = r5.get(r0);
        r5 = (org.telegram.messenger.MessageObject) r5;
        r0 = r148;
        r6 = r0.groupId;
        r5.localGroupId = r6;
        r0 = r72;
        r5 = r0.messages;
        r6 = r78 + 1;
        r5.remove(r6);
        r31 = r31 + 1;
        goto L_0x2f67;
    L_0x2f91:
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r0 = r148;
        r6 = r0.groupId;
        r6 = java.lang.Long.valueOf(r6);
        r0 = r148;
        r5.put(r6, r0);
        r0 = r148;
        r5 = r0.messages;
        r0 = r148;
        r6 = r0.messages;
        r6 = r6.size();
        r6 = r6 + -1;
        r107 = r5.get(r6);
        r107 = (org.telegram.messenger.MessageObject) r107;
        r148.calculate();
    L_0x2fb9:
        r0 = r72;
        r5 = r0.messages;
        r0 = r78;
        r5.remove(r0);
        if (r107 != 0) goto L_0x2fd8;
    L_0x2fc4:
        r0 = r72;
        r5 = r0.messages;
        r0 = r72;
        r6 = r0.messages;
        r6 = r6.size();
        r6 = r6 + -1;
        r107 = r5.get(r6);
        r107 = (org.telegram.messenger.MessageObject) r107;
    L_0x2fd8:
        r0 = r72;
        r5 = r0.messages;
        r5 = r5.isEmpty();
        if (r5 == 0) goto L_0x301b;
    L_0x2fe2:
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r0 = r72;
        r6 = r0.groupId;
        r6 = java.lang.Long.valueOf(r6);
        r5.remove(r6);
    L_0x2ff1:
        r0 = r106;
        r5 = r0.media;
        r5 = r5.ttl_seconds;
        if (r5 == 0) goto L_0x3044;
    L_0x2ff9:
        r0 = r106;
        r5 = r0.media;
        r5 = r5.photo;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_photoEmpty;
        if (r5 != 0) goto L_0x300d;
    L_0x3003:
        r0 = r106;
        r5 = r0.media;
        r5 = r5.document;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_documentEmpty;
        if (r5 == 0) goto L_0x3044;
    L_0x300d:
        r65.setType();
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r65;
        r5.updateRowWithMessageObject(r0);
        goto L_0x0015;
    L_0x301b:
        r72.calculate();
        r0 = r167;
        r5 = r0.messages;
        r0 = r107;
        r81 = r5.indexOf(r0);
        if (r81 < 0) goto L_0x2ff1;
    L_0x302a:
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x2ff1;
    L_0x3030:
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r167;
        r6 = r0.chatAdapter;
        r6 = r6.messagesStartRow;
        r6 = r6 + r81;
        r0 = r155;
        r5.notifyItemRangeChanged(r6, r0);
        goto L_0x2ff1;
    L_0x3044:
        r167.updateVisibleRows();
        goto L_0x0015;
    L_0x3049:
        r5 = org.telegram.messenger.NotificationCenter.replaceMessagesObjects;
        r0 = r168;
        if (r0 != r5) goto L_0x33ac;
    L_0x304f:
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r60 = r5.longValue();
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 == 0) goto L_0x3068;
    L_0x3060:
        r0 = r167;
        r6 = r0.mergeDialogId;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x3068:
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x321a;
    L_0x3070:
        r97 = 0;
    L_0x3072:
        r39 = 0;
        r104 = 0;
        r5 = 1;
        r111 = r169[r5];
        r111 = (java.util.ArrayList) r111;
        r119 = 0;
        r4 = 0;
    L_0x307e:
        r5 = r111.size();
        if (r4 >= r5) goto L_0x331d;
    L_0x3084:
        r0 = r111;
        r107 = r0.get(r4);
        r107 = (org.telegram.messenger.MessageObject) r107;
        r0 = r167;
        r5 = r0.messagesDict;
        r5 = r5[r97];
        r6 = r107.getId();
        r6 = java.lang.Integer.valueOf(r6);
        r126 = r5.get(r6);
        r126 = (org.telegram.messenger.MessageObject) r126;
        r0 = r167;
        r5 = r0.pinnedMessageObject;
        if (r5 == 0) goto L_0x30c0;
    L_0x30a6:
        r0 = r167;
        r5 = r0.pinnedMessageObject;
        r5 = r5.getId();
        r6 = r107.getId();
        if (r5 != r6) goto L_0x30c0;
    L_0x30b4:
        r0 = r107;
        r1 = r167;
        r1.pinnedMessageObject = r0;
        r5 = 1;
        r0 = r167;
        r0.updatePinnedMessageView(r5);
    L_0x30c0:
        if (r126 == 0) goto L_0x32a1;
    L_0x30c2:
        r0 = r107;
        r5 = r0.type;
        if (r5 < 0) goto L_0x3230;
    L_0x30c8:
        if (r104 != 0) goto L_0x30d6;
    L_0x30ca:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.media;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
        if (r5 == 0) goto L_0x30d6;
    L_0x30d4:
        r104 = 1;
    L_0x30d6:
        r0 = r126;
        r5 = r0.replyMessageObject;
        if (r5 == 0) goto L_0x30f4;
    L_0x30dc:
        r0 = r126;
        r5 = r0.replyMessageObject;
        r0 = r107;
        r0.replyMessageObject = r5;
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionGameScore;
        if (r5 == 0) goto L_0x321e;
    L_0x30ee:
        r5 = 0;
        r0 = r107;
        r0.generateGameMessageText(r5);
    L_0x30f4:
        r0 = r107;
        r5 = r0.messageOwner;
        r0 = r126;
        r6 = r0.messageOwner;
        r6 = r6.attachPath;
        r5.attachPath = r6;
        r0 = r126;
        r5 = r0.attachPathExists;
        r0 = r107;
        r0.attachPathExists = r5;
        r0 = r126;
        r5 = r0.mediaExists;
        r0 = r107;
        r0.mediaExists = r5;
        r0 = r167;
        r5 = r0.messagesDict;
        r5 = r5[r97];
        r6 = r126.getId();
        r6 = java.lang.Integer.valueOf(r6);
        r0 = r107;
        r5.put(r6, r0);
    L_0x3123:
        r0 = r167;
        r5 = r0.messages;
        r0 = r126;
        r81 = r5.indexOf(r0);
        if (r81 < 0) goto L_0x32a1;
    L_0x312f:
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r126;
        r6 = r0.dateKey;
        r57 = r5.get(r6);
        r57 = (java.util.ArrayList) r57;
        r82 = -1;
        if (r57 == 0) goto L_0x3149;
    L_0x3141:
        r0 = r57;
        r1 = r126;
        r82 = r0.indexOf(r1);
    L_0x3149:
        r6 = r126.getGroupId();
        r12 = 0;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x326c;
    L_0x3153:
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r6 = r126.getGroupId();
        r6 = java.lang.Long.valueOf(r6);
        r72 = r5.get(r6);
        r72 = (org.telegram.messenger.MessageObject.GroupedMessages) r72;
        if (r72 == 0) goto L_0x326c;
    L_0x3167:
        r0 = r72;
        r5 = r0.messages;
        r0 = r126;
        r78 = r5.indexOf(r0);
        if (r78 < 0) goto L_0x326c;
    L_0x3173:
        r6 = r126.getGroupId();
        r12 = r107.getGroupId();
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 == 0) goto L_0x3190;
    L_0x317f:
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r6 = r107.getGroupId();
        r6 = java.lang.Long.valueOf(r6);
        r0 = r72;
        r5.put(r6, r0);
    L_0x3190:
        r0 = r107;
        r5 = r0.photoThumbs;
        if (r5 == 0) goto L_0x31a0;
    L_0x3196:
        r0 = r107;
        r5 = r0.photoThumbs;
        r5 = r5.isEmpty();
        if (r5 == 0) goto L_0x32a5;
    L_0x31a0:
        if (r119 != 0) goto L_0x31a7;
    L_0x31a2:
        r119 = new java.util.HashMap;
        r119.<init>();
    L_0x31a7:
        r0 = r72;
        r6 = r0.groupId;
        r5 = java.lang.Long.valueOf(r6);
        r0 = r119;
        r1 = r72;
        r0.put(r5, r1);
        if (r78 <= 0) goto L_0x3263;
    L_0x31b8:
        r0 = r72;
        r5 = r0.messages;
        r5 = r5.size();
        r5 = r5 + -1;
        r0 = r78;
        if (r0 >= r5) goto L_0x3263;
    L_0x31c6:
        r148 = new org.telegram.messenger.MessageObject$GroupedMessages;
        r148.<init>();
        r5 = org.telegram.messenger.Utilities.random;
        r6 = r5.nextLong();
        r0 = r148;
        r0.groupId = r6;
        r0 = r148;
        r5 = r0.messages;
        r0 = r72;
        r6 = r0.messages;
        r7 = r78 + 1;
        r0 = r72;
        r8 = r0.messages;
        r8 = r8.size();
        r6 = r6.subList(r7, r8);
        r5.addAll(r6);
        r31 = 0;
    L_0x31f0:
        r0 = r148;
        r5 = r0.messages;
        r5 = r5.size();
        r0 = r31;
        if (r0 >= r5) goto L_0x3243;
    L_0x31fc:
        r0 = r148;
        r5 = r0.messages;
        r0 = r31;
        r5 = r5.get(r0);
        r5 = (org.telegram.messenger.MessageObject) r5;
        r0 = r148;
        r6 = r0.groupId;
        r5.localGroupId = r6;
        r0 = r72;
        r5 = r0.messages;
        r6 = r78 + 1;
        r5.remove(r6);
        r31 = r31 + 1;
        goto L_0x31f0;
    L_0x321a:
        r97 = 1;
        goto L_0x3072;
    L_0x321e:
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.action;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionPaymentSent;
        if (r5 == 0) goto L_0x30f4;
    L_0x3228:
        r5 = 0;
        r0 = r107;
        r0.generatePaymentSentMessageText(r5);
        goto L_0x30f4;
    L_0x3230:
        r0 = r167;
        r5 = r0.messagesDict;
        r5 = r5[r97];
        r6 = r126.getId();
        r6 = java.lang.Integer.valueOf(r6);
        r5.remove(r6);
        goto L_0x3123;
    L_0x3243:
        r0 = r148;
        r6 = r0.groupId;
        r5 = java.lang.Long.valueOf(r6);
        r0 = r119;
        r1 = r148;
        r0.put(r5, r1);
        r0 = r167;
        r5 = r0.groupedMessagesMap;
        r0 = r148;
        r6 = r0.groupId;
        r6 = java.lang.Long.valueOf(r6);
        r0 = r148;
        r5.put(r6, r0);
    L_0x3263:
        r0 = r72;
        r5 = r0.messages;
        r0 = r78;
        r5.remove(r0);
    L_0x326c:
        r0 = r107;
        r5 = r0.type;
        if (r5 < 0) goto L_0x32ca;
    L_0x3272:
        r0 = r167;
        r5 = r0.messages;
        r0 = r81;
        r1 = r107;
        r5.set(r0, r1);
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x3294;
    L_0x3283:
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r167;
        r6 = r0.chatAdapter;
        r6 = r6.messagesStartRow;
        r6 = r6 + r81;
        r5.notifyItemChanged(r6);
    L_0x3294:
        if (r82 < 0) goto L_0x329f;
    L_0x3296:
        r0 = r57;
        r1 = r82;
        r2 = r107;
        r0.set(r1, r2);
    L_0x329f:
        r39 = 1;
    L_0x32a1:
        r4 = r4 + 1;
        goto L_0x307e;
    L_0x32a5:
        r0 = r72;
        r5 = r0.messages;
        r0 = r78;
        r1 = r107;
        r5.set(r0, r1);
        r0 = r72;
        r5 = r0.positions;
        r0 = r126;
        r129 = r5.remove(r0);
        r129 = (org.telegram.messenger.MessageObject.GroupedMessagePosition) r129;
        if (r129 == 0) goto L_0x326c;
    L_0x32be:
        r0 = r72;
        r5 = r0.positions;
        r0 = r107;
        r1 = r129;
        r5.put(r0, r1);
        goto L_0x326c;
    L_0x32ca:
        r0 = r167;
        r5 = r0.messages;
        r0 = r81;
        r5.remove(r0);
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x32ea;
    L_0x32d9:
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r167;
        r6 = r0.chatAdapter;
        r6 = r6.messagesStartRow;
        r6 = r6 + r81;
        r5.notifyItemRemoved(r6);
    L_0x32ea:
        if (r82 < 0) goto L_0x329f;
    L_0x32ec:
        r0 = r57;
        r1 = r82;
        r0.remove(r1);
        r5 = r57.isEmpty();
        if (r5 == 0) goto L_0x329f;
    L_0x32f9:
        r0 = r167;
        r5 = r0.messagesByDays;
        r0 = r126;
        r6 = r0.dateKey;
        r5.remove(r6);
        r0 = r167;
        r5 = r0.messages;
        r0 = r81;
        r5.remove(r0);
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r167;
        r6 = r0.chatAdapter;
        r6 = r6.messagesStartRow;
        r5.notifyItemRemoved(r6);
        goto L_0x329f;
    L_0x331d:
        if (r119 == 0) goto L_0x3396;
    L_0x331f:
        r5 = r119.entrySet();
        r5 = r5.iterator();
    L_0x3327:
        r6 = r5.hasNext();
        if (r6 == 0) goto L_0x3396;
    L_0x332d:
        r64 = r5.next();
        r64 = (java.util.Map.Entry) r64;
        r72 = r64.getValue();
        r72 = (org.telegram.messenger.MessageObject.GroupedMessages) r72;
        r0 = r72;
        r6 = r0.messages;
        r6 = r6.isEmpty();
        if (r6 == 0) goto L_0x3353;
    L_0x3343:
        r0 = r167;
        r6 = r0.groupedMessagesMap;
        r0 = r72;
        r12 = r0.groupId;
        r7 = java.lang.Long.valueOf(r12);
        r6.remove(r7);
        goto L_0x3327;
    L_0x3353:
        r72.calculate();
        r0 = r72;
        r6 = r0.messages;
        r0 = r72;
        r7 = r0.messages;
        r7 = r7.size();
        r7 = r7 + -1;
        r107 = r6.get(r7);
        r107 = (org.telegram.messenger.MessageObject) r107;
        r0 = r167;
        r6 = r0.messages;
        r0 = r107;
        r81 = r6.indexOf(r0);
        if (r81 < 0) goto L_0x3327;
    L_0x3376:
        r0 = r167;
        r6 = r0.chatAdapter;
        if (r6 == 0) goto L_0x3327;
    L_0x337c:
        r0 = r167;
        r6 = r0.chatAdapter;
        r0 = r167;
        r7 = r0.chatAdapter;
        r7 = r7.messagesStartRow;
        r7 = r7 + r81;
        r0 = r72;
        r8 = r0.messages;
        r8 = r8.size();
        r6.notifyItemRangeChanged(r7, r8);
        goto L_0x3327;
    L_0x3396:
        if (r39 == 0) goto L_0x0015;
    L_0x3398:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        if (r5 == 0) goto L_0x0015;
    L_0x339e:
        if (r104 == 0) goto L_0x0015;
    L_0x33a0:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r5 = r5.findFirstVisibleItemPosition();
        if (r5 != 0) goto L_0x0015;
    L_0x33aa:
        goto L_0x0015;
    L_0x33ac:
        r5 = org.telegram.messenger.NotificationCenter.notificationsSettingsUpdated;
        r0 = r168;
        if (r0 != r5) goto L_0x33c4;
    L_0x33b2:
        r167.updateTitleIcons();
        r0 = r167;
        r5 = r0.currentChat;
        r5 = org.telegram.messenger.ChatObject.isChannel(r5);
        if (r5 == 0) goto L_0x0015;
    L_0x33bf:
        r167.updateBottomOverlay();
        goto L_0x0015;
    L_0x33c4:
        r5 = org.telegram.messenger.NotificationCenter.didLoadedReplyMessages;
        r0 = r168;
        if (r0 != r5) goto L_0x33e0;
    L_0x33ca:
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r60 = r5.longValue();
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x33db:
        r167.updateVisibleRows();
        goto L_0x0015;
    L_0x33e0:
        r5 = org.telegram.messenger.NotificationCenter.didLoadedPinnedMessage;
        r0 = r168;
        if (r0 != r5) goto L_0x341c;
    L_0x33e6:
        r5 = 0;
        r106 = r169[r5];
        r106 = (org.telegram.messenger.MessageObject) r106;
        r6 = r106.getDialogId();
        r0 = r167;
        r12 = r0.dialog_id;
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x33f7:
        r0 = r167;
        r5 = r0.info;
        if (r5 == 0) goto L_0x0015;
    L_0x33fd:
        r0 = r167;
        r5 = r0.info;
        r5 = r5.pinned_msg_id;
        r6 = r106.getId();
        if (r5 != r6) goto L_0x0015;
    L_0x3409:
        r0 = r106;
        r1 = r167;
        r1.pinnedMessageObject = r0;
        r5 = 0;
        r0 = r167;
        r0.loadingPinnedMessage = r5;
        r5 = 1;
        r0 = r167;
        r0.updatePinnedMessageView(r5);
        goto L_0x0015;
    L_0x341c:
        r5 = org.telegram.messenger.NotificationCenter.didReceivedWebpages;
        r0 = r168;
        if (r0 != r5) goto L_0x34ac;
    L_0x3422:
        r5 = 0;
        r30 = r169[r5];
        r30 = (java.util.ArrayList) r30;
        r158 = 0;
        r4 = 0;
    L_0x342a:
        r5 = r30.size();
        if (r4 >= r5) goto L_0x3492;
    L_0x3430:
        r0 = r30;
        r106 = r0.get(r4);
        r106 = (org.telegram.tgnet.TLRPC$Message) r106;
        r60 = org.telegram.messenger.MessageObject.getDialogId(r106);
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 == 0) goto L_0x344f;
    L_0x3444:
        r0 = r167;
        r6 = r0.mergeDialogId;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 == 0) goto L_0x344f;
    L_0x344c:
        r4 = r4 + 1;
        goto L_0x342a;
    L_0x344f:
        r0 = r167;
        r6 = r0.messagesDict;
        r0 = r167;
        r12 = r0.dialog_id;
        r5 = (r60 > r12 ? 1 : (r60 == r12 ? 0 : -1));
        if (r5 != 0) goto L_0x3490;
    L_0x345b:
        r5 = 0;
    L_0x345c:
        r5 = r6[r5];
        r0 = r106;
        r6 = r0.id;
        r6 = java.lang.Integer.valueOf(r6);
        r51 = r5.get(r6);
        r51 = (org.telegram.messenger.MessageObject) r51;
        if (r51 == 0) goto L_0x344c;
    L_0x346e:
        r0 = r51;
        r5 = r0.messageOwner;
        r6 = new org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
        r6.<init>();
        r5.media = r6;
        r0 = r51;
        r5 = r0.messageOwner;
        r5 = r5.media;
        r0 = r106;
        r6 = r0.media;
        r6 = r6.webpage;
        r5.webpage = r6;
        r5 = 1;
        r0 = r51;
        r0.generateThumbs(r5);
        r158 = 1;
        goto L_0x344c;
    L_0x3490:
        r5 = 1;
        goto L_0x345c;
    L_0x3492:
        if (r158 == 0) goto L_0x0015;
    L_0x3494:
        r167.updateVisibleRows();
        r0 = r167;
        r5 = r0.chatLayoutManager;
        if (r5 == 0) goto L_0x0015;
    L_0x349d:
        r0 = r167;
        r5 = r0.chatLayoutManager;
        r5 = r5.findFirstVisibleItemPosition();
        if (r5 != 0) goto L_0x0015;
    L_0x34a7:
        r167.moveScrollToLastMessage();
        goto L_0x0015;
    L_0x34ac:
        r5 = org.telegram.messenger.NotificationCenter.didReceivedWebpagesInUpdates;
        r0 = r168;
        if (r0 != r5) goto L_0x34ef;
    L_0x34b2:
        r0 = r167;
        r5 = r0.foundWebPage;
        if (r5 == 0) goto L_0x0015;
    L_0x34b8:
        r5 = 0;
        r75 = r169[r5];
        r75 = (java.util.HashMap) r75;
        r5 = r75.values();
        r5 = r5.iterator();
    L_0x34c5:
        r6 = r5.hasNext();
        if (r6 == 0) goto L_0x0015;
    L_0x34cb:
        r15 = r5.next();
        r15 = (org.telegram.tgnet.TLRPC$WebPage) r15;
        r6 = r15.id;
        r0 = r167;
        r8 = r0.foundWebPage;
        r12 = r8.id;
        r6 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r6 != 0) goto L_0x34c5;
    L_0x34dd:
        r5 = r15 instanceof org.telegram.tgnet.TLRPC$TL_webPageEmpty;
        if (r5 != 0) goto L_0x34ed;
    L_0x34e1:
        r12 = 1;
    L_0x34e2:
        r13 = 0;
        r14 = 0;
        r16 = 0;
        r11 = r167;
        r11.showReplyPanel(r12, r13, r14, r15, r16);
        goto L_0x0015;
    L_0x34ed:
        r12 = 0;
        goto L_0x34e2;
    L_0x34ef:
        r5 = org.telegram.messenger.NotificationCenter.messagesReadContent;
        r0 = r168;
        if (r0 != r5) goto L_0x35a1;
    L_0x34f5:
        r5 = 0;
        r28 = r169[r5];
        r28 = (java.util.ArrayList) r28;
        r158 = 0;
        r0 = r167;
        r5 = r0.currentChat;
        r5 = org.telegram.messenger.ChatObject.isChannel(r5);
        if (r5 == 0) goto L_0x3535;
    L_0x3506:
        r0 = r167;
        r5 = r0.currentChat;
        r0 = r5.id;
        r49 = r0;
    L_0x350e:
        r4 = 0;
    L_0x350f:
        r5 = r28.size();
        if (r4 >= r5) goto L_0x359a;
    L_0x3515:
        r0 = r28;
        r5 = r0.get(r4);
        r5 = (java.lang.Long) r5;
        r112 = r5.longValue();
        r5 = 32;
        r6 = r112 >> r5;
        r0 = (int) r6;
        r41 = r0;
        if (r41 >= 0) goto L_0x352c;
    L_0x352a:
        r41 = 0;
    L_0x352c:
        r0 = r41;
        r1 = r49;
        if (r0 == r1) goto L_0x3538;
    L_0x3532:
        r4 = r4 + 1;
        goto L_0x350f;
    L_0x3535:
        r49 = 0;
        goto L_0x350e;
    L_0x3538:
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r0 = r112;
        r6 = (int) r0;
        r6 = java.lang.Integer.valueOf(r6);
        r51 = r5.get(r6);
        r51 = (org.telegram.messenger.MessageObject) r51;
        if (r51 == 0) goto L_0x3532;
    L_0x354e:
        r51.setContentIsRead();
        r158 = 1;
        r0 = r51;
        r5 = r0.messageOwner;
        r5 = r5.mentioned;
        if (r5 == 0) goto L_0x3532;
    L_0x355b:
        r0 = r167;
        r5 = r0.newMentionsCount;
        r5 = r5 + -1;
        r0 = r167;
        r0.newMentionsCount = r5;
        r0 = r167;
        r5 = r0.newMentionsCount;
        if (r5 > 0) goto L_0x357d;
    L_0x356b:
        r5 = 0;
        r0 = r167;
        r0.newMentionsCount = r5;
        r5 = 1;
        r0 = r167;
        r0.hasAllMentionsLocal = r5;
        r5 = 0;
        r6 = 1;
        r0 = r167;
        r0.showMentiondownButton(r5, r6);
        goto L_0x3532;
    L_0x357d:
        r0 = r167;
        r5 = r0.mentiondownButtonCounter;
        r6 = "%d";
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r0 = r167;
        r11 = r0.newMentionsCount;
        r11 = java.lang.Integer.valueOf(r11);
        r7[r8] = r11;
        r6 = java.lang.String.format(r6, r7);
        r5.setText(r6);
        goto L_0x3532;
    L_0x359a:
        if (r158 == 0) goto L_0x0015;
    L_0x359c:
        r167.updateVisibleRows();
        goto L_0x0015;
    L_0x35a1:
        r5 = org.telegram.messenger.NotificationCenter.botInfoDidLoaded;
        r0 = r168;
        if (r0 != r5) goto L_0x3645;
    L_0x35a7:
        r5 = 1;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r73 = r5.intValue();
        r0 = r167;
        r5 = r0.classGuid;
        r0 = r73;
        if (r5 != r0) goto L_0x0015;
    L_0x35b8:
        r5 = 0;
        r83 = r169[r5];
        r83 = (org.telegram.tgnet.TLRPC$BotInfo) r83;
        r0 = r167;
        r5 = r0.currentEncryptedChat;
        if (r5 != 0) goto L_0x3640;
    L_0x35c3:
        r0 = r83;
        r5 = r0.commands;
        r5 = r5.isEmpty();
        if (r5 != 0) goto L_0x35dc;
    L_0x35cd:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = org.telegram.messenger.ChatObject.isChannel(r5);
        if (r5 != 0) goto L_0x35dc;
    L_0x35d7:
        r5 = 1;
        r0 = r167;
        r0.hasBotsCommands = r5;
    L_0x35dc:
        r0 = r167;
        r5 = r0.botInfo;
        r0 = r83;
        r6 = r0.user_id;
        r6 = java.lang.Integer.valueOf(r6);
        r0 = r83;
        r5.put(r6, r0);
        r0 = r167;
        r5 = r0.chatAdapter;
        if (r5 == 0) goto L_0x3602;
    L_0x35f3:
        r0 = r167;
        r5 = r0.chatAdapter;
        r0 = r167;
        r6 = r0.chatAdapter;
        r6 = r6.botInfoRow;
        r5.notifyItemChanged(r6);
    L_0x3602:
        r0 = r167;
        r5 = r0.mentionsAdapter;
        if (r5 == 0) goto L_0x362b;
    L_0x3608:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = org.telegram.messenger.ChatObject.isChannel(r5);
        if (r5 == 0) goto L_0x3620;
    L_0x3612:
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x362b;
    L_0x3618:
        r0 = r167;
        r5 = r0.currentChat;
        r5 = r5.megagroup;
        if (r5 == 0) goto L_0x362b;
    L_0x3620:
        r0 = r167;
        r5 = r0.mentionsAdapter;
        r0 = r167;
        r6 = r0.botInfo;
        r5.setBotInfo(r6);
    L_0x362b:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        if (r5 == 0) goto L_0x3640;
    L_0x3631:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r0 = r167;
        r6 = r0.botsCount;
        r0 = r167;
        r7 = r0.hasBotsCommands;
        r5.setBotsCount(r6, r7);
    L_0x3640:
        r167.updateBotButtons();
        goto L_0x0015;
    L_0x3645:
        r5 = org.telegram.messenger.NotificationCenter.botKeyboardDidLoaded;
        r0 = r168;
        if (r0 != r5) goto L_0x3760;
    L_0x364b:
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = 1;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r12 = r5.longValue();
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x365c:
        r5 = 0;
        r106 = r169[r5];
        r106 = (org.telegram.tgnet.TLRPC$Message) r106;
        if (r106 == 0) goto L_0x3724;
    L_0x3663:
        r0 = r167;
        r5 = r0.userBlocked;
        if (r5 != 0) goto L_0x3724;
    L_0x3669:
        r5 = new org.telegram.messenger.MessageObject;
        r6 = 0;
        r7 = 0;
        r0 = r106;
        r5.<init>(r0, r6, r7);
        r0 = r167;
        r0.botButtons = r5;
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        if (r5 == 0) goto L_0x0015;
    L_0x367c:
        r0 = r167;
        r5 = r0.botButtons;
        r5 = r5.messageOwner;
        r5 = r5.reply_markup;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_replyKeyboardForceReply;
        if (r5 == 0) goto L_0x36f3;
    L_0x3688:
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r6 = "mainconfig";
        r7 = 0;
        r137 = r5.getSharedPreferences(r6, r7);
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "answered_";
        r5 = r5.append(r6);
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = r5.append(r6);
        r5 = r5.toString();
        r6 = 0;
        r0 = r137;
        r5 = r0.getInt(r5, r6);
        r0 = r167;
        r6 = r0.botButtons;
        r6 = r6.getId();
        if (r5 == r6) goto L_0x0015;
    L_0x36bb:
        r0 = r167;
        r5 = r0.replyingMessageObject;
        if (r5 == 0) goto L_0x36cb;
    L_0x36c1:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r5 = r5.getFieldText();
        if (r5 != 0) goto L_0x0015;
    L_0x36cb:
        r0 = r167;
        r5 = r0.botButtons;
        r0 = r167;
        r0.botReplyButtons = r5;
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r0 = r167;
        r6 = r0.botButtons;
        r5.setButtons(r6);
        r17 = 1;
        r0 = r167;
        r0 = r0.botButtons;
        r18 = r0;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r16 = r167;
        r16.showReplyPanel(r17, r18, r19, r20, r21);
        goto L_0x0015;
    L_0x36f3:
        r0 = r167;
        r5 = r0.replyingMessageObject;
        if (r5 == 0) goto L_0x3717;
    L_0x36f9:
        r0 = r167;
        r5 = r0.botReplyButtons;
        r0 = r167;
        r6 = r0.replyingMessageObject;
        if (r5 != r6) goto L_0x3717;
    L_0x3703:
        r5 = 0;
        r0 = r167;
        r0.botReplyButtons = r5;
        r17 = 0;
        r18 = 0;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r16 = r167;
        r16.showReplyPanel(r17, r18, r19, r20, r21);
    L_0x3717:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r0 = r167;
        r6 = r0.botButtons;
        r5.setButtons(r6);
        goto L_0x0015;
    L_0x3724:
        r5 = 0;
        r0 = r167;
        r0.botButtons = r5;
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        if (r5 == 0) goto L_0x0015;
    L_0x372f:
        r0 = r167;
        r5 = r0.replyingMessageObject;
        if (r5 == 0) goto L_0x3753;
    L_0x3735:
        r0 = r167;
        r5 = r0.botReplyButtons;
        r0 = r167;
        r6 = r0.replyingMessageObject;
        if (r5 != r6) goto L_0x3753;
    L_0x373f:
        r5 = 0;
        r0 = r167;
        r0.botReplyButtons = r5;
        r17 = 0;
        r18 = 0;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r16 = r167;
        r16.showReplyPanel(r17, r18, r19, r20, r21);
    L_0x3753:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r0 = r167;
        r6 = r0.botButtons;
        r5.setButtons(r6);
        goto L_0x0015;
    L_0x3760:
        r5 = org.telegram.messenger.NotificationCenter.chatSearchResultsAvailable;
        r0 = r168;
        if (r0 != r5) goto L_0x37d1;
    L_0x3766:
        r0 = r167;
        r6 = r0.classGuid;
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r5 = r5.intValue();
        if (r6 != r5) goto L_0x0015;
    L_0x3775:
        r5 = 1;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r17 = r5.intValue();
        r5 = 3;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r60 = r5.longValue();
        if (r17 == 0) goto L_0x379e;
    L_0x3789:
        r18 = 0;
        r19 = 1;
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x37ce;
    L_0x3795:
        r20 = 0;
    L_0x3797:
        r21 = 0;
        r16 = r167;
        r16.scrollToMessageId(r17, r18, r19, r20, r21);
    L_0x379e:
        r5 = 2;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r6 = r5.intValue();
        r5 = 4;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r7 = r5.intValue();
        r5 = 5;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r5 = r5.intValue();
        r0 = r167;
        r0.updateSearchButtons(r6, r7, r5);
        r0 = r167;
        r5 = r0.searchItem;
        if (r5 == 0) goto L_0x0015;
    L_0x37c4:
        r0 = r167;
        r5 = r0.searchItem;
        r6 = 0;
        r5.setShowSearchProgress(r6);
        goto L_0x0015;
    L_0x37ce:
        r20 = 1;
        goto L_0x3797;
    L_0x37d1:
        r5 = org.telegram.messenger.NotificationCenter.chatSearchResultsLoading;
        r0 = r168;
        if (r0 != r5) goto L_0x37f6;
    L_0x37d7:
        r0 = r167;
        r6 = r0.classGuid;
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r5 = r5.intValue();
        if (r6 != r5) goto L_0x0015;
    L_0x37e6:
        r0 = r167;
        r5 = r0.searchItem;
        if (r5 == 0) goto L_0x0015;
    L_0x37ec:
        r0 = r167;
        r5 = r0.searchItem;
        r6 = 1;
        r5.setShowSearchProgress(r6);
        goto L_0x0015;
    L_0x37f6:
        r5 = org.telegram.messenger.NotificationCenter.didUpdatedMessagesViews;
        r0 = r168;
        if (r0 != r5) goto L_0x3858;
    L_0x37fc:
        r5 = 0;
        r42 = r169[r5];
        r42 = (android.util.SparseArray) r42;
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (int) r6;
        r0 = r42;
        r27 = r0.get(r5);
        r27 = (android.util.SparseIntArray) r27;
        if (r27 == 0) goto L_0x0015;
    L_0x3810:
        r158 = 0;
        r4 = 0;
    L_0x3813:
        r5 = r27.size();
        if (r4 >= r5) goto L_0x3851;
    L_0x3819:
        r0 = r27;
        r17 = r0.keyAt(r4);
        r0 = r167;
        r5 = r0.messagesDict;
        r6 = 0;
        r5 = r5[r6];
        r6 = java.lang.Integer.valueOf(r17);
        r107 = r5.get(r6);
        r107 = (org.telegram.messenger.MessageObject) r107;
        if (r107 == 0) goto L_0x384e;
    L_0x3832:
        r0 = r27;
        r1 = r17;
        r123 = r0.get(r1);
        r0 = r107;
        r5 = r0.messageOwner;
        r5 = r5.views;
        r0 = r123;
        if (r0 <= r5) goto L_0x384e;
    L_0x3844:
        r0 = r107;
        r5 = r0.messageOwner;
        r0 = r123;
        r5.views = r0;
        r158 = 1;
    L_0x384e:
        r4 = r4 + 1;
        goto L_0x3813;
    L_0x3851:
        if (r158 == 0) goto L_0x0015;
    L_0x3853:
        r167.updateVisibleRows();
        goto L_0x0015;
    L_0x3858:
        r5 = org.telegram.messenger.NotificationCenter.peerSettingsDidLoaded;
        r0 = r168;
        if (r0 != r5) goto L_0x3874;
    L_0x385e:
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r60 = r5.longValue();
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x386f:
        r167.updateSpamView();
        goto L_0x0015;
    L_0x3874:
        r5 = org.telegram.messenger.NotificationCenter.newDraftReceived;
        r0 = r168;
        if (r0 != r5) goto L_0x3893;
    L_0x387a:
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r60 = r5.longValue();
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = (r60 > r6 ? 1 : (r60 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x388b:
        r5 = 1;
        r0 = r167;
        r0.applyDraftMaybe(r5);
        goto L_0x0015;
    L_0x3893:
        r5 = org.telegram.messenger.NotificationCenter.userInfoDidLoaded;
        r0 = r168;
        if (r0 != r5) goto L_0x38d7;
    L_0x3899:
        r5 = 0;
        r151 = r169[r5];
        r151 = (java.lang.Integer) r151;
        r0 = r167;
        r5 = r0.currentUser;
        if (r5 == 0) goto L_0x0015;
    L_0x38a4:
        r0 = r167;
        r5 = r0.currentUser;
        r5 = r5.id;
        r6 = r151.intValue();
        if (r5 != r6) goto L_0x0015;
    L_0x38b0:
        r5 = 1;
        r161 = r169[r5];
        r161 = (org.telegram.tgnet.TLRPC$TL_userFull) r161;
        r0 = r167;
        r5 = r0.headerItem;
        if (r5 == 0) goto L_0x0015;
    L_0x38bb:
        r0 = r161;
        r5 = r0.phone_calls_available;
        if (r5 == 0) goto L_0x38cc;
    L_0x38c1:
        r0 = r167;
        r5 = r0.headerItem;
        r6 = 32;
        r5.showSubItem(r6);
        goto L_0x0015;
    L_0x38cc:
        r0 = r167;
        r5 = r0.headerItem;
        r6 = 32;
        r5.hideSubItem(r6);
        goto L_0x0015;
    L_0x38d7:
        r5 = org.telegram.messenger.NotificationCenter.didSetNewWallpapper;
        r0 = r168;
        if (r0 != r5) goto L_0x392c;
    L_0x38dd:
        r0 = r167;
        r5 = r0.fragmentView;
        if (r5 == 0) goto L_0x0015;
    L_0x38e3:
        r0 = r167;
        r5 = r0.fragmentView;
        r5 = (org.telegram.ui.Components.SizeNotifierFrameLayout) r5;
        r6 = org.telegram.ui.ActionBar.Theme.getCachedWallpaper();
        r5.setBackgroundImage(r6);
        r0 = r167;
        r5 = r0.progressView2;
        r5 = r5.getBackground();
        r6 = org.telegram.ui.ActionBar.Theme.colorFilter;
        r5.setColorFilter(r6);
        r0 = r167;
        r5 = r0.emptyView;
        if (r5 == 0) goto L_0x3910;
    L_0x3903:
        r0 = r167;
        r5 = r0.emptyView;
        r5 = r5.getBackground();
        r6 = org.telegram.ui.ActionBar.Theme.colorFilter;
        r5.setColorFilter(r6);
    L_0x3910:
        r0 = r167;
        r5 = r0.bigEmptyView;
        if (r5 == 0) goto L_0x3923;
    L_0x3916:
        r0 = r167;
        r5 = r0.bigEmptyView;
        r5 = r5.getBackground();
        r6 = org.telegram.ui.ActionBar.Theme.colorFilter;
        r5.setColorFilter(r6);
    L_0x3923:
        r0 = r167;
        r5 = r0.chatListView;
        r5.invalidateViews();
        goto L_0x0015;
    L_0x392c:
        r5 = org.telegram.messenger.NotificationCenter.channelRightsUpdated;
        r0 = r168;
        if (r0 != r5) goto L_0x3964;
    L_0x3932:
        r5 = 0;
        r44 = r169[r5];
        r44 = (org.telegram.tgnet.TLRPC$Chat) r44;
        r0 = r167;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x0015;
    L_0x393d:
        r0 = r44;
        r5 = r0.id;
        r0 = r167;
        r6 = r0.currentChat;
        r6 = r6.id;
        if (r5 != r6) goto L_0x0015;
    L_0x3949:
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        if (r5 == 0) goto L_0x0015;
    L_0x394f:
        r0 = r44;
        r1 = r167;
        r1.currentChat = r0;
        r0 = r167;
        r5 = r0.chatActivityEnterView;
        r5.checkChannelRights();
        r167.checkRaiseSensors();
        r167.updateSecretStatus();
        goto L_0x0015;
    L_0x3964:
        r5 = org.telegram.messenger.NotificationCenter.updateMentionsCount;
        r0 = r168;
        if (r0 != r5) goto L_0x39c9;
    L_0x396a:
        r0 = r167;
        r6 = r0.dialog_id;
        r5 = 0;
        r5 = r169[r5];
        r5 = (java.lang.Long) r5;
        r12 = r5.longValue();
        r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1));
        if (r5 != 0) goto L_0x0015;
    L_0x397b:
        r5 = 1;
        r5 = r169[r5];
        r5 = (java.lang.Integer) r5;
        r47 = r5.intValue();
        r0 = r167;
        r5 = r0.newMentionsCount;
        r0 = r47;
        if (r5 <= r0) goto L_0x0015;
    L_0x398c:
        r0 = r47;
        r1 = r167;
        r1.newMentionsCount = r0;
        r0 = r167;
        r5 = r0.newMentionsCount;
        if (r5 > 0) goto L_0x39ab;
    L_0x3998:
        r5 = 0;
        r0 = r167;
        r0.newMentionsCount = r5;
        r5 = 1;
        r0 = r167;
        r0.hasAllMentionsLocal = r5;
        r5 = 0;
        r6 = 1;
        r0 = r167;
        r0.showMentiondownButton(r5, r6);
        goto L_0x0015;
    L_0x39ab:
        r0 = r167;
        r5 = r0.mentiondownButtonCounter;
        r6 = "%d";
        r7 = 1;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r0 = r167;
        r11 = r0.newMentionsCount;
        r11 = java.lang.Integer.valueOf(r11);
        r7[r8] = r11;
        r6 = java.lang.String.format(r6, r7);
        r5.setText(r6);
        goto L_0x0015;
    L_0x39c9:
        r5 = org.telegram.messenger.NotificationCenter.DownloadServiceStart;
        r0 = r168;
        if (r0 != r5) goto L_0x39ea;
    L_0x39cf:
        r0 = r167;
        r5 = r0.bottomOverlayChatText;
        r6 = "StopDownloadService";
        r7 = 2131232471; // 0x7f0806d7 float:1.8081052E38 double:1.052968747E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r6, r7);
        r5.setText(r6);
        r0 = r167;
        r5 = r0.downloadManagerLoading;
        r6 = 0;
        r5.setVisibility(r6);
        goto L_0x0015;
    L_0x39ea:
        r5 = org.telegram.messenger.NotificationCenter.DownloadServiceStop;
        r0 = r168;
        if (r0 != r5) goto L_0x0015;
    L_0x39f0:
        r0 = r167;
        r5 = r0.bottomOverlayChatText;
        r6 = "StartDownloadService";
        r7 = 2131232453; // 0x7f0806c5 float:1.8081016E38 double:1.0529687383E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r6, r7);
        r5.setText(r6);
        r0 = r167;
        r5 = r0.downloadManagerLoading;
        r6 = 8;
        r5.setVisibility(r6);
        goto L_0x0015;
    L_0x3a0c:
        r5 = move-exception;
        goto L_0x0af6;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.ChatActivity.didReceivedNotification(int, java.lang.Object[]):void");
    }

    public boolean processSwitchButton(TLRPC$TL_keyboardButtonSwitchInline button) {
        if (this.inlineReturn == 0 || button.same_peer || this.parentLayout == null) {
            return false;
        }
        String query = "@" + this.currentUser.username + " " + button.query;
        if (this.inlineReturn == this.dialog_id) {
            this.inlineReturn = 0;
            this.chatActivityEnterView.setFieldText(query);
        } else {
            DraftQuery.saveDraft(this.inlineReturn, query, null, null, false);
            if (this.parentLayout.fragmentsStack.size() > 1) {
                BaseFragment prevFragment = (BaseFragment) this.parentLayout.fragmentsStack.get(this.parentLayout.fragmentsStack.size() - 2);
                if ((prevFragment instanceof ChatActivity) && ((ChatActivity) prevFragment).dialog_id == this.inlineReturn) {
                    finishFragment();
                } else {
                    Bundle bundle = new Bundle();
                    int lower_part = (int) this.inlineReturn;
                    int high_part = (int) (this.inlineReturn >> 32);
                    if (lower_part == 0) {
                        bundle.putInt("enc_id", high_part);
                    } else if (lower_part > 0) {
                        bundle.putInt("user_id", lower_part);
                    } else if (lower_part < 0) {
                        bundle.putInt("chat_id", -lower_part);
                    }
                    presentFragment(new ChatActivity(bundle), true);
                }
            }
        }
        return true;
    }

    private void updateSearchButtons(int mask, int num, int count) {
        float f = 1.0f;
        if (this.searchUpButton != null) {
            boolean z;
            float f2;
            this.searchUpButton.setEnabled((mask & 1) != 0);
            ImageView imageView = this.searchDownButton;
            if ((mask & 2) != 0) {
                z = true;
            } else {
                z = false;
            }
            imageView.setEnabled(z);
            imageView = this.searchUpButton;
            if (this.searchUpButton.isEnabled()) {
                f2 = 1.0f;
            } else {
                f2 = 0.5f;
            }
            imageView.setAlpha(f2);
            ImageView imageView2 = this.searchDownButton;
            if (!this.searchDownButton.isEnabled()) {
                f = 0.5f;
            }
            imageView2.setAlpha(f);
            if (count < 0) {
                this.searchCountText.setText("");
            } else if (count == 0) {
                this.searchCountText.setText(LocaleController.getString("NoResult", R.string.NoResult));
            } else {
                this.searchCountText.setText(LocaleController.formatString("Of", R.string.Of, new Object[]{Integer.valueOf(num + 1), Integer.valueOf(count)}));
            }
        }
    }

    public boolean needDelayOpenAnimation() {
        return this.firstLoading;
    }

    public void onTransitionAnimationStart(boolean isOpen, boolean backward) {
        NotificationCenter.getInstance().setAllowedNotificationsDutingAnimation(new int[]{NotificationCenter.chatInfoDidLoaded, NotificationCenter.dialogsNeedReload, NotificationCenter.closeChats, NotificationCenter.messagesDidLoaded, NotificationCenter.botKeyboardDidLoaded});
        NotificationCenter.getInstance().setAnimationInProgress(true);
        if (isOpen) {
            this.openAnimationEnded = false;
        }
    }

    public void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        NotificationCenter.getInstance().setAnimationInProgress(false);
        if (isOpen) {
            this.openAnimationEnded = true;
            if (this.currentUser != null) {
                MessagesController.getInstance().loadFullUser(this.currentUser, this.classGuid, false);
            }
            if (VERSION.SDK_INT >= 21) {
                createChatAttachView();
            }
            if (this.chatActivityEnterView.hasRecordVideo() && !this.chatActivityEnterView.isSendButtonVisible()) {
                boolean isChannel = false;
                if (this.currentChat != null) {
                    if (!ChatObject.isChannel(this.currentChat) || this.currentChat.megagroup) {
                        isChannel = false;
                    } else {
                        isChannel = true;
                    }
                }
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                String key = isChannel ? "needShowRoundHintChannel" : "needShowRoundHint";
                if (preferences.getBoolean(key, true) && Utilities.random.nextFloat() < 0.2f) {
                    showVoiceHint(false, this.chatActivityEnterView.isInVideoMode());
                    preferences.edit().putBoolean(key, false).commit();
                }
            }
        }
    }

    protected void onDialogDismiss(Dialog dialog) {
        if (this.closeChatDialog != null && dialog == this.closeChatDialog) {
            MessagesController.getInstance().deleteDialog(this.dialog_id, 0);
            if (this.parentLayout == null || this.parentLayout.fragmentsStack.isEmpty() || this.parentLayout.fragmentsStack.get(this.parentLayout.fragmentsStack.size() - 1) == this) {
                finishFragment();
                return;
            }
            BaseFragment fragment = (BaseFragment) this.parentLayout.fragmentsStack.get(this.parentLayout.fragmentsStack.size() - 1);
            removeSelfFromStack();
            fragment.finishFragment();
        }
    }

    public boolean extendActionMode(Menu menu) {
        if (!(this.chatActivityEnterView.getSelectionLength() == 0 || menu.findItem(16908321) == null)) {
            if (VERSION.SDK_INT >= 23) {
                menu.removeItem(16908341);
            }
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(LocaleController.getString("Bold", R.string.Bold));
            stringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), 0, stringBuilder.length(), 33);
            menu.add(R.id.menu_groupbolditalic, R.id.menu_bold, 6, stringBuilder);
            stringBuilder = new SpannableStringBuilder(LocaleController.getString("Italic", R.string.Italic));
            stringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/ritalic.ttf")), 0, stringBuilder.length(), 33);
            menu.add(R.id.menu_groupbolditalic, R.id.menu_italic, 7, stringBuilder);
            menu.add(R.id.menu_groupbolditalic, R.id.menu_regular, 8, LocaleController.getString("Regular", R.string.Regular));
        }
        return true;
    }

    private void updateBottomOverlay() {
        if (this.bottomOverlayChatText != null) {
            if (this.currentChat != null) {
                if (!ChatObject.isChannel(this.currentChat) || (this.currentChat instanceof TLRPC$TL_channelForbidden)) {
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

    public void showAlert(String name, String message) {
        if (this.alertView != null && name != null && message != null) {
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
                animatorArr[0] = ObjectAnimator.ofFloat(this.alertView, "translationY", new float[]{0.0f});
                animatorSet.playTogether(animatorArr);
                this.alertViewAnimator.setDuration(200);
                this.alertViewAnimator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        if (ChatActivity.this.alertViewAnimator != null && ChatActivity.this.alertViewAnimator.equals(animation)) {
                            ChatActivity.this.alertViewAnimator = null;
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (ChatActivity.this.alertViewAnimator != null && ChatActivity.this.alertViewAnimator.equals(animation)) {
                            ChatActivity.this.alertViewAnimator = null;
                        }
                    }
                });
                this.alertViewAnimator.start();
            }
            this.alertNameTextView.setText(name);
            this.alertTextView.setText(Emoji.replaceEmoji(message.replace('\n', ' '), this.alertTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
            if (this.hideAlertViewRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(this.hideAlertViewRunnable);
            }
            Runnable anonymousClass97 = new Runnable() {

                /* renamed from: org.telegram.ui.ChatActivity$97$1 */
                class C24361 extends AnimatorListenerAdapter {
                    C24361() {
                    }

                    public void onAnimationEnd(Animator animation) {
                        if (ChatActivity.this.alertViewAnimator != null && ChatActivity.this.alertViewAnimator.equals(animation)) {
                            ChatActivity.this.alertView.setVisibility(8);
                            ChatActivity.this.alertViewAnimator = null;
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (ChatActivity.this.alertViewAnimator != null && ChatActivity.this.alertViewAnimator.equals(animation)) {
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
                        ChatActivity.this.alertViewAnimator.addListener(new C24361());
                        ChatActivity.this.alertViewAnimator.start();
                    }
                }
            };
            this.hideAlertViewRunnable = anonymousClass97;
            AndroidUtilities.runOnUIThread(anonymousClass97, 3000);
        }
    }

    private void hidePinnedMessageView(boolean animated) {
        if (this.pinnedMessageView.getTag() == null) {
            this.pinnedMessageView.setTag(Integer.valueOf(1));
            if (this.pinnedMessageViewAnimator != null) {
                this.pinnedMessageViewAnimator.cancel();
                this.pinnedMessageViewAnimator = null;
            }
            if (animated) {
                this.pinnedMessageViewAnimator = new AnimatorSet();
                AnimatorSet animatorSet = this.pinnedMessageViewAnimator;
                Animator[] animatorArr = new Animator[1];
                animatorArr[0] = ObjectAnimator.ofFloat(this.pinnedMessageView, "translationY", new float[]{(float) (-AndroidUtilities.dp(50.0f))});
                animatorSet.playTogether(animatorArr);
                this.pinnedMessageViewAnimator.setDuration(200);
                this.pinnedMessageViewAnimator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        if (ChatActivity.this.pinnedMessageViewAnimator != null && ChatActivity.this.pinnedMessageViewAnimator.equals(animation)) {
                            ChatActivity.this.pinnedMessageView.setVisibility(8);
                            ChatActivity.this.pinnedMessageViewAnimator = null;
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (ChatActivity.this.pinnedMessageViewAnimator != null && ChatActivity.this.pinnedMessageViewAnimator.equals(animation)) {
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

    private void updatePinnedMessageView(boolean animated) {
        if (this.pinnedMessageView != null) {
            if (this.info != null) {
                if (!(this.pinnedMessageObject == null || this.info.pinned_msg_id == this.pinnedMessageObject.getId())) {
                    this.pinnedMessageObject = null;
                }
                if (this.info.pinned_msg_id != 0 && this.pinnedMessageObject == null) {
                    this.pinnedMessageObject = (MessageObject) this.messagesDict[0].get(Integer.valueOf(this.info.pinned_msg_id));
                }
            }
            SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            if (this.info == null || this.info.pinned_msg_id == 0 || this.info.pinned_msg_id == preferences.getInt("pin_" + this.dialog_id, 0) || (this.actionBar != null && (this.actionBar.isActionModeShowed() || this.actionBar.isSearchFieldVisible()))) {
                hidePinnedMessageView(animated);
            } else if (this.pinnedMessageObject != null) {
                if (this.pinnedMessageView.getTag() != null) {
                    this.pinnedMessageView.setTag(null);
                    if (this.pinnedMessageViewAnimator != null) {
                        this.pinnedMessageViewAnimator.cancel();
                        this.pinnedMessageViewAnimator = null;
                    }
                    if (animated) {
                        this.pinnedMessageView.setVisibility(0);
                        this.pinnedMessageViewAnimator = new AnimatorSet();
                        AnimatorSet animatorSet = this.pinnedMessageViewAnimator;
                        Animator[] animatorArr = new Animator[1];
                        animatorArr[0] = ObjectAnimator.ofFloat(this.pinnedMessageView, "translationY", new float[]{0.0f});
                        animatorSet.playTogether(animatorArr);
                        this.pinnedMessageViewAnimator.setDuration(200);
                        this.pinnedMessageViewAnimator.addListener(new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator animation) {
                                if (ChatActivity.this.pinnedMessageViewAnimator != null && ChatActivity.this.pinnedMessageViewAnimator.equals(animation)) {
                                    ChatActivity.this.pinnedMessageViewAnimator = null;
                                }
                            }

                            public void onAnimationCancel(Animator animation) {
                                if (ChatActivity.this.pinnedMessageViewAnimator != null && ChatActivity.this.pinnedMessageViewAnimator.equals(animation)) {
                                    ChatActivity.this.pinnedMessageViewAnimator = null;
                                }
                            }
                        });
                        this.pinnedMessageViewAnimator.start();
                    } else {
                        this.pinnedMessageView.setTranslationY(0.0f);
                        this.pinnedMessageView.setVisibility(0);
                    }
                }
                FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) this.pinnedMessageNameTextView.getLayoutParams();
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.pinnedMessageTextView.getLayoutParams();
                TLRPC$PhotoSize photoSize = FileLoader.getClosestPhotoSizeWithSize(this.pinnedMessageObject.photoThumbs2, AndroidUtilities.dp(50.0f));
                if (photoSize == null) {
                    photoSize = FileLoader.getClosestPhotoSizeWithSize(this.pinnedMessageObject.photoThumbs, AndroidUtilities.dp(50.0f));
                }
                int dp;
                if (photoSize == null || (photoSize instanceof TLRPC$TL_photoSizeEmpty) || (photoSize.location instanceof TLRPC$TL_fileLocationUnavailable) || this.pinnedMessageObject.type == 13) {
                    this.pinnedMessageImageView.setImageBitmap(null);
                    this.pinnedImageLocation = null;
                    this.pinnedMessageImageView.setVisibility(4);
                    dp = AndroidUtilities.dp(18.0f);
                    layoutParams2.leftMargin = dp;
                    layoutParams1.leftMargin = dp;
                } else {
                    if (this.pinnedMessageObject.isRoundVideo()) {
                        this.pinnedMessageImageView.setRoundRadius(AndroidUtilities.dp(16.0f));
                    } else {
                        this.pinnedMessageImageView.setRoundRadius(0);
                    }
                    this.pinnedImageLocation = photoSize.location;
                    this.pinnedMessageImageView.setImage(this.pinnedImageLocation, "50_50", (Drawable) null);
                    this.pinnedMessageImageView.setVisibility(0);
                    dp = AndroidUtilities.dp(55.0f);
                    layoutParams2.leftMargin = dp;
                    layoutParams1.leftMargin = dp;
                }
                this.pinnedMessageNameTextView.setLayoutParams(layoutParams1);
                this.pinnedMessageTextView.setLayoutParams(layoutParams2);
                this.pinnedMessageNameTextView.setText(LocaleController.getString("PinnedMessage", R.string.PinnedMessage));
                if (this.pinnedMessageObject.type == 14) {
                    this.pinnedMessageTextView.setText(String.format("%s - %s", new Object[]{this.pinnedMessageObject.getMusicAuthor(), this.pinnedMessageObject.getMusicTitle()}));
                } else if (this.pinnedMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                    this.pinnedMessageTextView.setText(Emoji.replaceEmoji(this.pinnedMessageObject.messageOwner.media.game.title, this.pinnedMessageTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                } else if (this.pinnedMessageObject.messageText != null) {
                    String mess = this.pinnedMessageObject.messageText.toString();
                    if (mess.length() > 150) {
                        mess = mess.substring(0, 150);
                    }
                    this.pinnedMessageTextView.setText(Emoji.replaceEmoji(mess.replace('\n', ' '), this.pinnedMessageTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(14.0f), false));
                }
            } else {
                this.pinnedImageLocation = null;
                hidePinnedMessageView(animated);
                if (this.loadingPinnedMessage != this.info.pinned_msg_id) {
                    this.loadingPinnedMessage = this.info.pinned_msg_id;
                    MessagesQuery.loadPinnedMessage(this.currentChat.id, this.info.pinned_msg_id, true);
                }
            }
            checkListViewPaddings();
        }
    }

    private void updateSpamView() {
        if (this.reportSpamView == null) {
            FileLog.d("no spam view found");
            return;
        }
        boolean show;
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        if (this.currentEncryptedChat != null) {
            if (this.currentEncryptedChat.admin_id == UserConfig.getClientUserId() || ContactsController.getInstance().isLoadingContacts() || ContactsController.getInstance().contactsDict.get(Integer.valueOf(this.currentUser.id)) != null) {
                show = false;
            } else {
                show = true;
            }
            if (show && preferences.getInt("spam3_" + this.dialog_id, 0) == 1) {
                show = false;
            }
        } else {
            show = preferences.getInt(new StringBuilder().append("spam3_").append(this.dialog_id).toString(), 0) == 2;
        }
        AnimatorSet animatorSet;
        Animator[] animatorArr;
        if (show) {
            if (this.reportSpamView.getTag() != null) {
                FileLog.d("show spam button");
                this.reportSpamView.setTag(null);
                this.reportSpamView.setVisibility(0);
                if (this.reportSpamViewAnimator != null) {
                    this.reportSpamViewAnimator.cancel();
                }
                this.reportSpamViewAnimator = new AnimatorSet();
                animatorSet = this.reportSpamViewAnimator;
                animatorArr = new Animator[1];
                animatorArr[0] = ObjectAnimator.ofFloat(this.reportSpamView, "translationY", new float[]{0.0f});
                animatorSet.playTogether(animatorArr);
                this.reportSpamViewAnimator.setDuration(200);
                this.reportSpamViewAnimator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        if (ChatActivity.this.reportSpamViewAnimator != null && ChatActivity.this.reportSpamViewAnimator.equals(animation)) {
                            ChatActivity.this.reportSpamViewAnimator = null;
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (ChatActivity.this.reportSpamViewAnimator != null && ChatActivity.this.reportSpamViewAnimator.equals(animation)) {
                            ChatActivity.this.reportSpamViewAnimator = null;
                        }
                    }
                });
                this.reportSpamViewAnimator.start();
            }
        } else if (this.reportSpamView.getTag() == null) {
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
                public void onAnimationEnd(Animator animation) {
                    if (ChatActivity.this.reportSpamViewAnimator != null && ChatActivity.this.reportSpamViewAnimator.equals(animation)) {
                        ChatActivity.this.reportSpamView.setVisibility(8);
                        ChatActivity.this.reportSpamViewAnimator = null;
                    }
                }

                public void onAnimationCancel(Animator animation) {
                    if (ChatActivity.this.reportSpamViewAnimator != null && ChatActivity.this.reportSpamViewAnimator.equals(animation)) {
                        ChatActivity.this.reportSpamViewAnimator = null;
                    }
                }
            });
            this.reportSpamViewAnimator.start();
        }
        checkListViewPaddings();
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

    private void checkListViewPaddings() {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                try {
                    int firstVisPos = ChatActivity.this.chatLayoutManager.findFirstVisibleItemPosition();
                    int top = 0;
                    if (firstVisPos != -1) {
                        View firstVisView = ChatActivity.this.chatLayoutManager.findViewByPosition(firstVisPos);
                        top = firstVisView == null ? 0 : (ChatActivity.this.chatListView.getMeasuredHeight() - firstVisView.getBottom()) - ChatActivity.this.chatListView.getPaddingBottom();
                    }
                    FrameLayout.LayoutParams layoutParams;
                    if (ChatActivity.this.chatListView.getPaddingTop() != AndroidUtilities.dp(52.0f) && ((ChatActivity.this.pinnedMessageView != null && ChatActivity.this.pinnedMessageView.getTag() == null) || (ChatActivity.this.reportSpamView != null && ChatActivity.this.reportSpamView.getTag() == null))) {
                        ChatActivity.this.chatListView.setPadding(0, AndroidUtilities.dp(52.0f), 0, AndroidUtilities.dp(3.0f));
                        layoutParams = (FrameLayout.LayoutParams) ChatActivity.this.floatingDateView.getLayoutParams();
                        layoutParams.topMargin = AndroidUtilities.dp(52.0f);
                        ChatActivity.this.floatingDateView.setLayoutParams(layoutParams);
                        ChatActivity.this.chatListView.setTopGlowOffset(AndroidUtilities.dp(48.0f));
                    } else if (ChatActivity.this.chatListView.getPaddingTop() == AndroidUtilities.dp(4.0f) || ((ChatActivity.this.pinnedMessageView != null && ChatActivity.this.pinnedMessageView.getTag() == null) || (ChatActivity.this.reportSpamView != null && ChatActivity.this.reportSpamView.getTag() == null))) {
                        firstVisPos = -1;
                    } else {
                        ChatActivity.this.chatListView.setPadding(0, AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(3.0f));
                        layoutParams = (FrameLayout.LayoutParams) ChatActivity.this.floatingDateView.getLayoutParams();
                        layoutParams.topMargin = AndroidUtilities.dp(4.0f);
                        ChatActivity.this.floatingDateView.setLayoutParams(layoutParams);
                        ChatActivity.this.chatListView.setTopGlowOffset(0);
                    }
                    if (firstVisPos != -1) {
                        ChatActivity.this.chatLayoutManager.scrollToPositionWithOffset(firstVisPos, top);
                    }
                } catch (Exception e) {
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

    public void dismissCurrentDialig() {
        if (this.chatAttachAlert == null || this.visibleDialog != this.chatAttachAlert) {
            super.dismissCurrentDialig();
            return;
        }
        this.chatAttachAlert.closeCamera(false);
        this.chatAttachAlert.dismissInternal();
        this.chatAttachAlert.hideCamera(true);
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
                int yOffset;
                boolean bottom = true;
                if (this.scrollToMessagePosition == -9000) {
                    yOffset = getScrollOffsetForMessage(this.scrollToMessage);
                    bottom = false;
                } else if (this.scrollToMessagePosition == -10000) {
                    yOffset = (-this.chatListView.getPaddingTop()) - AndroidUtilities.dp(7.0f);
                    bottom = false;
                } else {
                    yOffset = this.scrollToMessagePosition;
                }
                this.chatLayoutManager.scrollToPositionWithOffset(this.chatAdapter.messagesStartRow + this.messages.indexOf(this.scrollToMessage), yOffset, bottom);
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
        if (this.channelIsFilter) {
            try {
                showCantOpenAlert(this, AppPreferences.getFilterMessage(ApplicationLoader.applicationContext), true);
            } catch (Exception e) {
            }
        }
    }

    public void onPause() {
        boolean z;
        super.onPause();
        AndroidUtilities.cancelRunOnUIThread(this.readRunnable);
        MediaController.getInstance().stopRaiseToEarSensors(this);
        this.paused = true;
        this.wasPaused = true;
        NotificationsController.getInstance().setOpenedDialogId(0);
        CharSequence draftMessage = null;
        MessageObject replyMessage = null;
        boolean searchWebpage = true;
        if (!(this.ignoreAttachOnPause || this.chatActivityEnterView == null || this.bottomOverlayChat.getVisibility() == 0)) {
            this.chatActivityEnterView.onPause();
            replyMessage = this.replyingMessageObject;
            if (!this.chatActivityEnterView.isEditingMessage()) {
                CharSequence text = AndroidUtilities.getTrimmedString(this.chatActivityEnterView.getFieldText());
                if (!TextUtils.isEmpty(text)) {
                    if (!TextUtils.equals(text, "@gif")) {
                        draftMessage = text;
                    }
                }
            }
            searchWebpage = this.chatActivityEnterView.isMessageWebPageSearchEnabled();
            this.chatActivityEnterView.setFieldFocused(false);
        }
        if (this.chatAttachAlert != null) {
            if (this.ignoreAttachOnPause) {
                this.ignoreAttachOnPause = false;
            } else {
                this.chatAttachAlert.onPause();
            }
        }
        CharSequence[] message = new CharSequence[]{draftMessage};
        ArrayList<TLRPC$MessageEntity> entities = MessagesQuery.getEntities(message);
        long j = this.dialog_id;
        CharSequence charSequence = message[0];
        TLRPC$Message tLRPC$Message = replyMessage != null ? replyMessage.messageOwner : null;
        if (searchWebpage) {
            z = false;
        } else {
            z = true;
        }
        DraftQuery.saveDraft(j, charSequence, entities, tLRPC$Message, z);
        MessagesController.getInstance().cancelTyping(0, this.dialog_id);
        if (!this.pausedOnLastMessage) {
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            int messageId = 0;
            int offset = 0;
            if (this.chatLayoutManager != null) {
                int position = this.chatLayoutManager.findFirstVisibleItemPosition();
                if (position != 0) {
                    Holder holder = (Holder) this.chatListView.findViewHolderForAdapterPosition(position);
                    if (holder != null) {
                        if (holder.itemView instanceof ChatMessageCell) {
                            messageId = ((ChatMessageCell) holder.itemView).getMessageObject().getId();
                        } else if (holder.itemView instanceof ChatActionCell) {
                            messageId = ((ChatActionCell) holder.itemView).getMessageObject().getId();
                        }
                        if (messageId != 0) {
                            offset = holder.itemView.getBottom() - this.chatListView.getMeasuredHeight();
                            FileLog.d("save offset = " + offset + " for mid " + messageId);
                        }
                    }
                }
            }
            if (messageId != 0) {
                editor.putInt("diditem" + this.dialog_id, messageId);
                editor.putInt("diditemo" + this.dialog_id, offset);
            } else {
                this.pausedOnLastMessage = true;
                editor.remove("diditem" + this.dialog_id);
                editor.remove("diditemo" + this.dialog_id);
            }
            editor.commit();
        }
        if (this.currentUser != null) {
            this.chatLeaveTime = System.currentTimeMillis();
            updateInformationForScreenshotDetector();
        }
    }

    private void applyDraftMaybe(boolean canClear) {
        if (this.chatActivityEnterView != null) {
            TLRPC$DraftMessage draftMessage = DraftQuery.getDraft(this.dialog_id);
            TLRPC$Message draftReplyMessage = (draftMessage == null || draftMessage.reply_to_msg_id == 0) ? null : DraftQuery.getDraftMessage(this.dialog_id);
            if (this.chatActivityEnterView.getFieldText() == null) {
                if (draftMessage != null) {
                    CharSequence message;
                    this.chatActivityEnterView.setWebPage(null, !draftMessage.no_webpage);
                    if (draftMessage.entities.isEmpty()) {
                        message = draftMessage.message;
                    } else {
                        SpannableStringBuilder stringBuilder = SpannableStringBuilder.valueOf(draftMessage.message);
                        MessagesQuery.sortEntities(draftMessage.entities);
                        int addToOffset = 0;
                        for (int a = 0; a < draftMessage.entities.size(); a++) {
                            TLRPC$MessageEntity entity = (TLRPC$MessageEntity) draftMessage.entities.get(a);
                            if ((entity instanceof TLRPC$TL_inputMessageEntityMentionName) || (entity instanceof TLRPC$TL_messageEntityMentionName)) {
                                int user_id;
                                if (entity instanceof TLRPC$TL_inputMessageEntityMentionName) {
                                    user_id = ((TLRPC$TL_inputMessageEntityMentionName) entity).user_id.user_id;
                                } else {
                                    user_id = ((TLRPC$TL_messageEntityMentionName) entity).user_id;
                                }
                                if ((entity.offset + addToOffset) + entity.length < stringBuilder.length() && stringBuilder.charAt((entity.offset + addToOffset) + entity.length) == ' ') {
                                    entity.length++;
                                }
                                stringBuilder.setSpan(new URLSpanUserMention("" + user_id, true), entity.offset + addToOffset, (entity.offset + addToOffset) + entity.length, 33);
                            } else if (entity instanceof TLRPC$TL_messageEntityCode) {
                                stringBuilder.insert((entity.offset + entity.length) + addToOffset, "`");
                                stringBuilder.insert(entity.offset + addToOffset, "`");
                                addToOffset += 2;
                            } else if (entity instanceof TLRPC$TL_messageEntityPre) {
                                stringBuilder.insert((entity.offset + entity.length) + addToOffset, "```");
                                stringBuilder.insert(entity.offset + addToOffset, "```");
                                addToOffset += 6;
                            } else if (entity instanceof TLRPC$TL_messageEntityBold) {
                                stringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), entity.offset + addToOffset, (entity.offset + entity.length) + addToOffset, 33);
                            } else if (entity instanceof TLRPC$TL_messageEntityItalic) {
                                stringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/ritalic.ttf")), entity.offset + addToOffset, (entity.offset + entity.length) + addToOffset, 33);
                            }
                        }
                        message = stringBuilder;
                    }
                    this.chatActivityEnterView.setFieldText(message);
                    if (getArguments().getBoolean("hasUrl", false)) {
                        this.chatActivityEnterView.setSelection(draftMessage.message.indexOf(10) + 1);
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
            } else if (canClear && draftMessage == null) {
                this.chatActivityEnterView.setFieldText("");
                showReplyPanel(false, null, null, null, false);
            }
            if (this.replyingMessageObject == null && draftReplyMessage != null) {
                this.replyingMessageObject = new MessageObject(draftReplyMessage, MessagesController.getInstance().getUsers(), false);
                showReplyPanel(true, this.replyingMessageObject, null, null, false);
            }
        }
    }

    private void updateInformationForScreenshotDetector() {
        if (this.currentUser != null) {
            if (this.currentEncryptedChat != null) {
                ArrayList<Long> visibleMessages = new ArrayList();
                if (this.chatListView != null) {
                    int count = this.chatListView.getChildCount();
                    for (int a = 0; a < count; a++) {
                        View view = this.chatListView.getChildAt(a);
                        MessageObject object = null;
                        if (view instanceof ChatMessageCell) {
                            object = ((ChatMessageCell) view).getMessageObject();
                        }
                        if (!(object == null || object.getId() >= 0 || object.messageOwner.random_id == 0)) {
                            visibleMessages.add(Long.valueOf(object.messageOwner.random_id));
                        }
                    }
                }
                MediaController.getInstance().setLastVisibleMessageIds(this.chatEnterTime, this.chatLeaveTime, this.currentUser, this.currentEncryptedChat, visibleMessages, 0);
                return;
            }
            SecretMediaViewer viewer = SecretMediaViewer.getInstance();
            MessageObject messageObject = viewer.getCurrentMessageObject();
            if (messageObject != null && !messageObject.isOut()) {
                MediaController.getInstance().setLastVisibleMessageIds(viewer.getOpenTime(), viewer.getCloseTime(), this.currentUser, null, null, messageObject.getId());
            }
        }
    }

    private boolean fixLayoutInternal() {
        if (AndroidUtilities.isTablet() || ApplicationLoader.applicationContext.getResources().getConfiguration().orientation != 2) {
            this.selectedMessagesCountTextView.setTextSize(20);
        } else {
            this.selectedMessagesCountTextView.setTextSize(18);
        }
        HashMap<Long, GroupedMessages> newGroups = null;
        int count = this.chatListView.getChildCount();
        for (int a = 0; a < count; a++) {
            View child = this.chatListView.getChildAt(a);
            if (child instanceof ChatMessageCell) {
                GroupedMessages groupedMessages = ((ChatMessageCell) child).getCurrentMessagesGroup();
                if (groupedMessages != null && groupedMessages.hasSibling) {
                    if (newGroups == null) {
                        newGroups = new HashMap();
                    }
                    if (!newGroups.containsKey(Long.valueOf(groupedMessages.groupId))) {
                        newGroups.put(Long.valueOf(groupedMessages.groupId), groupedMessages);
                        int idx = this.messages.indexOf((MessageObject) groupedMessages.messages.get(groupedMessages.messages.size() - 1));
                        if (idx >= 0) {
                            this.chatAdapter.notifyItemRangeChanged(this.chatAdapter.messagesStartRow + idx, groupedMessages.messages.size());
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
            if (this.fragmentContextView != null && this.fragmentContextView.getParent() == null) {
                ((ViewGroup) this.fragmentView).addView(this.fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, 0.0f, -36.0f, 0.0f, 0.0f));
            }
        } else {
            ActionBar actionBar = this.actionBar;
            boolean z = this.parentLayout == null || this.parentLayout.fragmentsStack.isEmpty() || this.parentLayout.fragmentsStack.get(0) == this || this.parentLayout.fragmentsStack.size() == 1;
            actionBar.setBackButtonDrawable(new BackDrawable(z));
            if (!(this.fragmentContextView == null || this.fragmentContextView.getParent() == null)) {
                this.fragmentView.setPadding(0, 0, 0, 0);
                ((ViewGroup) this.fragmentView).removeView(this.fragmentContextView);
            }
        }
        return false;
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

    public void onConfigurationChanged(Configuration newConfig) {
        fixLayout();
        if (this.visibleDialog instanceof DatePickerDialog) {
            this.visibleDialog.dismiss();
        }
    }

    private void createDeleteMessagesAlert(MessageObject finalSelectedObject, GroupedMessages selectedGroup) {
        createDeleteMessagesAlert(finalSelectedObject, selectedGroup, 1);
    }

    private void createDeleteMessagesAlert(MessageObject finalSelectedObject, GroupedMessages finalSelectedGroup, int loadParticipant) {
        if (getParentActivity() != null) {
            int count;
            org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
            if (finalSelectedGroup != null) {
                count = finalSelectedGroup.messages.size();
            } else if (finalSelectedObject != null) {
                count = 1;
            } else {
                count = this.selectedMessagesIds[0].size() + this.selectedMessagesIds[1].size();
            }
            builder.setMessage(LocaleController.formatString("AreYouSureDeleteMessages", R.string.AreYouSureDeleteMessages, new Object[]{LocaleController.formatPluralString("messages", count)}));
            builder.setTitle(LocaleController.getString("Message", R.string.Message));
            boolean[] checks = new boolean[3];
            boolean[] deleteForAll = new boolean[1];
            User user = null;
            boolean z;
            int currentDate;
            int a;
            MessageObject msg;
            boolean exit;
            View frameLayout;
            CheckBoxCell cell;
            final boolean[] zArr;
            if (this.currentChat != null && this.currentChat.megagroup) {
                z = false;
                boolean canBan = ChatObject.canBlockUsers(this.currentChat);
                currentDate = ConnectionsManager.getInstance().getCurrentTime();
                if (finalSelectedObject != null) {
                    if (finalSelectedObject.messageOwner.action == null || (finalSelectedObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty) || (finalSelectedObject.messageOwner.action instanceof TLRPC$TL_messageActionChatDeleteUser)) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(finalSelectedObject.messageOwner.from_id));
                    }
                    if (!finalSelectedObject.isSendError() && finalSelectedObject.getDialogId() == this.mergeDialogId && ((finalSelectedObject.messageOwner.action == null || (finalSelectedObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty)) && finalSelectedObject.isOut() && currentDate - finalSelectedObject.messageOwner.date <= 172800)) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else {
                    int from_id = -1;
                    for (a = 1; a >= 0; a--) {
                        for (Entry<Integer, MessageObject> entry : this.selectedMessagesIds[a].entrySet()) {
                            msg = (MessageObject) entry.getValue();
                            if (from_id == -1) {
                                from_id = msg.messageOwner.from_id;
                            }
                            if (from_id >= 0) {
                                if (from_id != msg.messageOwner.from_id) {
                                }
                            }
                            from_id = -2;
                        }
                        if (from_id == -2) {
                            break;
                        }
                    }
                    exit = false;
                    for (a = 1; a >= 0; a--) {
                        for (Entry<Integer, MessageObject> entry2 : this.selectedMessagesIds[a].entrySet()) {
                            msg = (MessageObject) entry2.getValue();
                            if (a != 1) {
                                if (a == 0 && !msg.isOut()) {
                                    z = false;
                                    exit = true;
                                    break;
                                }
                            } else if (!msg.isOut() || msg.messageOwner.action != null) {
                                z = false;
                                exit = true;
                                break;
                            } else if (currentDate - msg.messageOwner.date <= 172800) {
                                z = true;
                            }
                        }
                        if (exit) {
                            break;
                        }
                    }
                    if (from_id != -1) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(from_id));
                    }
                }
                int dp;
                int dp2;
                if (user == null || user.id == UserConfig.getClientUserId() || loadParticipant == 2) {
                    if (z) {
                        frameLayout = new FrameLayout(getParentActivity());
                        cell = new CheckBoxCell(getParentActivity(), true);
                        cell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                        if (this.currentChat != null) {
                            cell.setText(LocaleController.getString("DeleteForAll", R.string.DeleteForAll), "", false, false);
                        } else {
                            cell.setText(LocaleController.formatString("DeleteForUser", R.string.DeleteForUser, new Object[]{UserObject.getFirstName(this.currentUser)}), "", false, false);
                        }
                        if (LocaleController.isRTL) {
                            dp = AndroidUtilities.dp(16.0f);
                        } else {
                            dp = AndroidUtilities.dp(8.0f);
                        }
                        if (LocaleController.isRTL) {
                            dp2 = AndroidUtilities.dp(8.0f);
                        } else {
                            dp2 = AndroidUtilities.dp(16.0f);
                        }
                        cell.setPadding(dp, 0, dp2, 0);
                        frameLayout.addView(cell, LayoutHelper.createFrame(-1, 48.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
                        zArr = deleteForAll;
                        cell.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                boolean z;
                                CheckBoxCell cell = (CheckBoxCell) v;
                                boolean[] zArr = zArr;
                                if (zArr[0]) {
                                    z = false;
                                } else {
                                    z = true;
                                }
                                zArr[0] = z;
                                cell.setChecked(zArr[0], true);
                            }
                        });
                        builder.setView(frameLayout);
                    } else {
                        user = null;
                    }
                } else if (loadParticipant != 1 || this.currentChat.creator) {
                    frameLayout = new FrameLayout(getParentActivity());
                    int num = 0;
                    a = 0;
                    while (a < 3) {
                        if (canBan || a != 0) {
                            cell = new CheckBoxCell(getParentActivity(), true);
                            cell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                            cell.setTag(Integer.valueOf(a));
                            if (a == 0) {
                                cell.setText(LocaleController.getString("DeleteBanUser", R.string.DeleteBanUser), "", false, false);
                            } else if (a == 1) {
                                cell.setText(LocaleController.getString("DeleteReportSpam", R.string.DeleteReportSpam), "", false, false);
                            } else if (a == 2) {
                                cell.setText(LocaleController.formatString("DeleteAllFrom", R.string.DeleteAllFrom, new Object[]{ContactsController.formatName(user.first_name, user.last_name)}), "", false, false);
                            }
                            if (LocaleController.isRTL) {
                                dp = AndroidUtilities.dp(16.0f);
                            } else {
                                dp = AndroidUtilities.dp(8.0f);
                            }
                            if (LocaleController.isRTL) {
                                dp2 = AndroidUtilities.dp(8.0f);
                            } else {
                                dp2 = AndroidUtilities.dp(16.0f);
                            }
                            cell.setPadding(dp, 0, dp2, 0);
                            frameLayout.addView(cell, LayoutHelper.createFrame(-1, 48.0f, 51, 0.0f, (float) (num * 48), 0.0f, 0.0f));
                            zArr = checks;
                            cell.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    if (v.isEnabled()) {
                                        CheckBoxCell cell = (CheckBoxCell) v;
                                        Integer num = (Integer) cell.getTag();
                                        zArr[num.intValue()] = !zArr[num.intValue()];
                                        cell.setChecked(zArr[num.intValue()], true);
                                    }
                                }
                            });
                            num++;
                        }
                        a++;
                    }
                    builder.setView(frameLayout);
                } else {
                    org.telegram.ui.ActionBar.AlertDialog[] progressDialog = new org.telegram.ui.ActionBar.AlertDialog[]{new org.telegram.ui.ActionBar.AlertDialog(getParentActivity(), 1)};
                    TLObject req = new TLRPC$TL_channels_getParticipant();
                    req.channel = MessagesController.getInputChannel(this.currentChat);
                    req.user_id = MessagesController.getInputUser(user);
                    final org.telegram.ui.ActionBar.AlertDialog[] alertDialogArr = progressDialog;
                    final MessageObject messageObject = finalSelectedObject;
                    final GroupedMessages groupedMessages = finalSelectedGroup;
                    int requestId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                        public void run(final TLObject response, TLRPC$TL_error error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    try {
                                        alertDialogArr[0].dismiss();
                                    } catch (Throwable th) {
                                    }
                                    alertDialogArr[0] = null;
                                    int loadType = 2;
                                    if (response != null) {
                                        TLRPC$TL_channels_channelParticipant participant = response;
                                        if (!((participant.participant instanceof TLRPC$TL_channelParticipantAdmin) || (participant.participant instanceof TLRPC$TL_channelParticipantCreator))) {
                                            loadType = 0;
                                        }
                                    }
                                    ChatActivity.this.createDeleteMessagesAlert(messageObject, groupedMessages, loadType);
                                }
                            });
                        }
                    });
                    if (requestId != 0) {
                        alertDialogArr = progressDialog;
                        final int i = requestId;
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.ui.ChatActivity$107$1 */
                            class C23851 implements OnClickListener {
                                C23851() {
                                }

                                public void onClick(DialogInterface dialog, int which) {
                                    ConnectionsManager.getInstance().cancelRequest(i, true);
                                    try {
                                        dialog.dismiss();
                                    } catch (Exception e) {
                                        FileLog.e(e);
                                    }
                                }
                            }

                            public void run() {
                                if (alertDialogArr[0] != null) {
                                    alertDialogArr[0].setMessage(LocaleController.getString("Loading", R.string.Loading));
                                    alertDialogArr[0].setCanceledOnTouchOutside(false);
                                    alertDialogArr[0].setCancelable(false);
                                    alertDialogArr[0].setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C23851());
                                    ChatActivity.this.showDialog(alertDialogArr[0]);
                                }
                            }
                        }, 1000);
                        return;
                    }
                    return;
                }
            } else if (!ChatObject.isChannel(this.currentChat) && this.currentEncryptedChat == null) {
                z = false;
                currentDate = ConnectionsManager.getInstance().getCurrentTime();
                if ((this.currentUser != null && this.currentUser.id != UserConfig.getClientUserId() && !this.currentUser.bot) || this.currentChat != null) {
                    if (finalSelectedObject == null) {
                        exit = false;
                        for (a = 1; a >= 0; a--) {
                            for (Entry<Integer, MessageObject> entry22 : this.selectedMessagesIds[a].entrySet()) {
                                msg = (MessageObject) entry22.getValue();
                                if (msg.messageOwner.action == null) {
                                    if (!msg.isOut() && (this.currentChat == null || (!this.currentChat.creator && (!this.currentChat.admin || !this.currentChat.admins_enabled)))) {
                                        exit = true;
                                        z = false;
                                        break;
                                    } else if (!z && currentDate - msg.messageOwner.date <= 172800) {
                                        z = true;
                                    }
                                }
                            }
                            if (exit) {
                                break;
                            }
                        }
                    } else if (finalSelectedObject.isSendError() || (!(finalSelectedObject.messageOwner.action == null || (finalSelectedObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty)) || ((!finalSelectedObject.isOut() && (this.currentChat == null || !(this.currentChat.creator || (this.currentChat.admin && this.currentChat.admins_enabled)))) || currentDate - finalSelectedObject.messageOwner.date > 172800))) {
                        z = false;
                    } else {
                        z = true;
                    }
                }
                if (z) {
                    frameLayout = new FrameLayout(getParentActivity());
                    cell = new CheckBoxCell(getParentActivity(), true);
                    cell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                    if (this.currentChat != null) {
                        cell.setText(LocaleController.getString("DeleteForAll", R.string.DeleteForAll), "", false, false);
                    } else {
                        cell.setText(LocaleController.formatString("DeleteForUser", R.string.DeleteForUser, new Object[]{UserObject.getFirstName(this.currentUser)}), "", false, false);
                    }
                    cell.setPadding(LocaleController.isRTL ? AndroidUtilities.dp(16.0f) : AndroidUtilities.dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(8.0f) : AndroidUtilities.dp(16.0f), 0);
                    frameLayout.addView(cell, LayoutHelper.createFrame(-1, 48.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
                    zArr = deleteForAll;
                    cell.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            boolean z;
                            CheckBoxCell cell = (CheckBoxCell) v;
                            boolean[] zArr = zArr;
                            if (zArr[0]) {
                                z = false;
                            } else {
                                z = true;
                            }
                            zArr[0] = z;
                            cell.setChecked(zArr[0], true);
                        }
                    });
                    builder.setView(frameLayout);
                }
            }
            final User userFinal = user;
            final MessageObject messageObject2 = finalSelectedObject;
            final GroupedMessages groupedMessages2 = finalSelectedGroup;
            final boolean[] zArr2 = deleteForAll;
            final boolean[] zArr3 = checks;
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {

                /* renamed from: org.telegram.ui.ChatActivity$111$1 */
                class C23861 implements RequestDelegate {
                    C23861() {
                    }

                    public void run(TLObject response, TLRPC$TL_error error) {
                    }
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    ArrayList<Integer> ids = null;
                    ArrayList<Long> random_ids;
                    int a;
                    if (messageObject2 != null) {
                        ids = new ArrayList();
                        random_ids = null;
                        if (groupedMessages2 != null) {
                            for (a = 0; a < groupedMessages2.messages.size(); a++) {
                                MessageObject messageObject = (MessageObject) groupedMessages2.messages.get(a);
                                ids.add(Integer.valueOf(messageObject.getId()));
                                if (!(ChatActivity.this.currentEncryptedChat == null || messageObject.messageOwner.random_id == 0 || messageObject.type == 10)) {
                                    if (random_ids == null) {
                                        random_ids = new ArrayList();
                                    }
                                    random_ids.add(Long.valueOf(messageObject.messageOwner.random_id));
                                }
                            }
                        } else {
                            ids.add(Integer.valueOf(messageObject2.getId()));
                            if (!(ChatActivity.this.currentEncryptedChat == null || messageObject2.messageOwner.random_id == 0 || messageObject2.type == 10)) {
                                random_ids = new ArrayList();
                                random_ids.add(Long.valueOf(messageObject2.messageOwner.random_id));
                            }
                            if (ChatActivity.this.isDownloadManager) {
                                MessagesController.getInstance().deleteMessages(ids, random_ids, ChatActivity.this.currentEncryptedChat, 0, zArr2[0]);
                            }
                        }
                        MessagesController.getInstance().deleteMessages(ids, random_ids, ChatActivity.this.currentEncryptedChat, messageObject2.messageOwner.to_id.channel_id, zArr2[0]);
                    } else {
                        for (a = 1; a >= 0; a--) {
                            MessageObject msg;
                            ids = new ArrayList(ChatActivity.this.selectedMessagesIds[a].keySet());
                            random_ids = null;
                            int channelId = 0;
                            if (!ids.isEmpty()) {
                                msg = (MessageObject) ChatActivity.this.selectedMessagesIds[a].get(ids.get(0));
                                if (null == null && msg.messageOwner.to_id.channel_id != 0) {
                                    channelId = msg.messageOwner.to_id.channel_id;
                                }
                            }
                            if (ChatActivity.this.currentEncryptedChat != null) {
                                random_ids = new ArrayList();
                                for (Entry<Integer, MessageObject> entry : ChatActivity.this.selectedMessagesIds[a].entrySet()) {
                                    msg = (MessageObject) entry.getValue();
                                    if (!(msg.messageOwner.random_id == 0 || msg.type == 10)) {
                                        random_ids.add(Long.valueOf(msg.messageOwner.random_id));
                                    }
                                }
                            }
                            MessagesController.getInstance().deleteMessages(ids, random_ids, ChatActivity.this.currentEncryptedChat, channelId, zArr2[0]);
                        }
                        ChatActivity.this.actionBar.hideActionMode();
                        ChatActivity.this.updatePinnedMessageView(true);
                    }
                    if (userFinal != null) {
                        if (zArr3[0]) {
                            MessagesController.getInstance().deleteUserFromChat(ChatActivity.this.currentChat.id, userFinal, ChatActivity.this.info);
                        }
                        if (zArr3[1]) {
                            TLRPC$TL_channels_reportSpam req = new TLRPC$TL_channels_reportSpam();
                            req.channel = MessagesController.getInputChannel(ChatActivity.this.currentChat);
                            req.user_id = MessagesController.getInputUser(userFinal);
                            req.id = ids;
                            ConnectionsManager.getInstance().sendRequest(req, new C23861());
                        }
                        if (zArr3[2]) {
                            MessagesController.getInstance().deleteUserChannelHistory(ChatActivity.this.currentChat, userFinal, 0);
                        }
                    }
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            showDialog(builder.create());
        }
    }

    private void createMenu(View v, boolean single, boolean listView) {
        createMenu(v, single, listView, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void createMenu(android.view.View r31, boolean r32, boolean r33, boolean r34) {
        /*
        r30 = this;
        r0 = r30;
        r4 = r0.actionBar;
        r4 = r4.isActionModeShowed();
        if (r4 == 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r24 = 0;
        r0 = r31;
        r4 = r0 instanceof org.telegram.ui.Cells.ChatMessageCell;
        if (r4 == 0) goto L_0x0044;
    L_0x0013:
        r31 = (org.telegram.ui.Cells.ChatMessageCell) r31;
        r24 = r31.getMessageObject();
    L_0x0019:
        if (r24 == 0) goto L_0x000a;
    L_0x001b:
        r0 = r30;
        r1 = r24;
        r27 = r0.getMessageType(r1);
        if (r32 == 0) goto L_0x0051;
    L_0x0025:
        r0 = r24;
        r4 = r0.messageOwner;
        r4 = r4.action;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageActionPinMessage;
        if (r4 == 0) goto L_0x0051;
    L_0x002f:
        r0 = r24;
        r4 = r0.messageOwner;
        r5 = r4.reply_to_msg_id;
        r0 = r24;
        r4 = r0.messageOwner;
        r6 = r4.id;
        r7 = 1;
        r8 = 0;
        r9 = 0;
        r4 = r30;
        r4.scrollToMessageId(r5, r6, r7, r8, r9);
        goto L_0x000a;
    L_0x0044:
        r0 = r31;
        r4 = r0 instanceof org.telegram.ui.Cells.ChatActionCell;
        if (r4 == 0) goto L_0x0019;
    L_0x004a:
        r31 = (org.telegram.ui.Cells.ChatActionCell) r31;
        r24 = r31.getMessageObject();
        goto L_0x0019;
    L_0x0051:
        r4 = 0;
        r0 = r30;
        r0.selectedObject = r4;
        r4 = 0;
        r0 = r30;
        r0.selectedObjectGroup = r4;
        r4 = 0;
        r0 = r30;
        r0.forwardingMessage = r4;
        r4 = 0;
        r0 = r30;
        r0.forwardingMessageGroup = r4;
        r10 = 1;
    L_0x0066:
        if (r10 < 0) goto L_0x0086;
    L_0x0068:
        r0 = r30;
        r4 = r0.selectedMessagesCanCopyIds;
        r4 = r4[r10];
        r4.clear();
        r0 = r30;
        r4 = r0.selectedMessagesCanStarIds;
        r4 = r4[r10];
        r4.clear();
        r0 = r30;
        r4 = r0.selectedMessagesIds;
        r4 = r4[r10];
        r4.clear();
        r10 = r10 + -1;
        goto L_0x0066;
    L_0x0086:
        r4 = 0;
        r0 = r30;
        r0.cantDeleteMessagesCount = r4;
        r4 = 0;
        r0 = r30;
        r0.canEditMessagesCount = r4;
        r0 = r30;
        r4 = r0.actionBar;
        r4.hideActionMode();
        r4 = 1;
        r0 = r30;
        r0.updatePinnedMessageView(r4);
        if (r34 == 0) goto L_0x02d1;
    L_0x009f:
        r0 = r30;
        r1 = r24;
        r21 = r0.getValidGroupedMessage(r1);
    L_0x00a7:
        r12 = 1;
        r4 = r24.getDialogId();
        r0 = r30;
        r6 = r0.mergeDialogId;
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x02d5;
    L_0x00b4:
        r4 = r24.getId();
        if (r4 <= 0) goto L_0x02d5;
    L_0x00ba:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = org.telegram.messenger.ChatObject.isChannel(r4);
        if (r4 == 0) goto L_0x02d5;
    L_0x00c4:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.creator;
        if (r4 != 0) goto L_0x00f8;
    L_0x00cc:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.admin_rights;
        if (r4 == 0) goto L_0x02d5;
    L_0x00d4:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.megagroup;
        if (r4 == 0) goto L_0x00e6;
    L_0x00dc:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.admin_rights;
        r4 = r4.pin_messages;
        if (r4 != 0) goto L_0x00f8;
    L_0x00e6:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.megagroup;
        if (r4 != 0) goto L_0x02d5;
    L_0x00ee:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.admin_rights;
        r4 = r4.edit_messages;
        if (r4 == 0) goto L_0x02d5;
    L_0x00f8:
        r0 = r24;
        r4 = r0.messageOwner;
        r4 = r4.action;
        if (r4 == 0) goto L_0x010a;
    L_0x0100:
        r0 = r24;
        r4 = r0.messageOwner;
        r4 = r4.action;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageActionEmpty;
        if (r4 == 0) goto L_0x02d5;
    L_0x010a:
        r14 = 1;
    L_0x010b:
        r4 = r24.getDialogId();
        r0 = r30;
        r6 = r0.mergeDialogId;
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x02d8;
    L_0x0117:
        r0 = r30;
        r4 = r0.info;
        if (r4 == 0) goto L_0x02d8;
    L_0x011d:
        r0 = r30;
        r4 = r0.info;
        r4 = r4.pinned_msg_id;
        r5 = r24.getId();
        if (r4 != r5) goto L_0x02d8;
    L_0x0129:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.creator;
        if (r4 != 0) goto L_0x015d;
    L_0x0131:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.admin_rights;
        if (r4 == 0) goto L_0x02d8;
    L_0x0139:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.megagroup;
        if (r4 == 0) goto L_0x014b;
    L_0x0141:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.admin_rights;
        r4 = r4.pin_messages;
        if (r4 != 0) goto L_0x015d;
    L_0x014b:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.megagroup;
        if (r4 != 0) goto L_0x02d8;
    L_0x0153:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.admin_rights;
        r4 = r4.edit_messages;
        if (r4 == 0) goto L_0x02d8;
    L_0x015d:
        r15 = 1;
    L_0x015e:
        if (r21 != 0) goto L_0x02db;
    L_0x0160:
        r0 = r30;
        r4 = r0.currentChat;
        r0 = r24;
        r4 = r0.canEditMessage(r4);
        if (r4 == 0) goto L_0x02db;
    L_0x016c:
        r0 = r30;
        r4 = r0.chatActivityEnterView;
        r4 = r4.hasAudioToSend();
        if (r4 != 0) goto L_0x02db;
    L_0x0176:
        r4 = r24.getDialogId();
        r0 = r30;
        r6 = r0.mergeDialogId;
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x02db;
    L_0x0182:
        r13 = 1;
    L_0x0183:
        r0 = r30;
        r4 = r0.currentEncryptedChat;
        if (r4 == 0) goto L_0x0197;
    L_0x0189:
        r0 = r30;
        r4 = r0.currentEncryptedChat;
        r4 = r4.layer;
        r4 = org.telegram.messenger.AndroidUtilities.getPeerLayerVersion(r4);
        r5 = 46;
        if (r4 < r5) goto L_0x0206;
    L_0x0197:
        r4 = 1;
        r0 = r27;
        if (r0 != r4) goto L_0x01ae;
    L_0x019c:
        r4 = r24.getDialogId();
        r0 = r30;
        r6 = r0.mergeDialogId;
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x0206;
    L_0x01a8:
        r4 = r24.isSecretPhoto();
        if (r4 != 0) goto L_0x0206;
    L_0x01ae:
        r0 = r30;
        r4 = r0.currentEncryptedChat;
        if (r4 != 0) goto L_0x01ba;
    L_0x01b4:
        r4 = r24.getId();
        if (r4 < 0) goto L_0x0206;
    L_0x01ba:
        r0 = r30;
        r4 = r0.bottomOverlayChat;
        if (r4 == 0) goto L_0x01ca;
    L_0x01c0:
        r0 = r30;
        r4 = r0.bottomOverlayChat;
        r4 = r4.getVisibility();
        if (r4 == 0) goto L_0x0206;
    L_0x01ca:
        r0 = r30;
        r4 = r0.isBroadcast;
        if (r4 != 0) goto L_0x0206;
    L_0x01d0:
        r0 = r30;
        r4 = r0.currentChat;
        if (r4 == 0) goto L_0x0207;
    L_0x01d6:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = org.telegram.messenger.ChatObject.isNotInChat(r4);
        if (r4 != 0) goto L_0x0206;
    L_0x01e0:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = org.telegram.messenger.ChatObject.isChannel(r4);
        if (r4 == 0) goto L_0x01fc;
    L_0x01ea:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = org.telegram.messenger.ChatObject.canPost(r4);
        if (r4 != 0) goto L_0x01fc;
    L_0x01f4:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.megagroup;
        if (r4 == 0) goto L_0x0206;
    L_0x01fc:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = org.telegram.messenger.ChatObject.canSendMessages(r4);
        if (r4 != 0) goto L_0x0207;
    L_0x0206:
        r12 = 0;
    L_0x0207:
        if (r32 != 0) goto L_0x0220;
    L_0x0209:
        r4 = 2;
        r0 = r27;
        if (r0 < r4) goto L_0x0220;
    L_0x020e:
        r4 = 20;
        r0 = r27;
        if (r0 == r4) goto L_0x0220;
    L_0x0214:
        r4 = r24.isSecretPhoto();
        if (r4 != 0) goto L_0x0220;
    L_0x021a:
        r4 = r24.isLiveLocation();
        if (r4 == 0) goto L_0x0c0b;
    L_0x0220:
        if (r27 < 0) goto L_0x000a;
    L_0x0222:
        r0 = r24;
        r1 = r30;
        r1.selectedObject = r0;
        r0 = r21;
        r1 = r30;
        r1.selectedObjectGroup = r0;
        r4 = r30.getParentActivity();
        if (r4 == 0) goto L_0x000a;
    L_0x0234:
        r18 = new org.telegram.ui.ActionBar.AlertDialog$Builder;
        r4 = r30.getParentActivity();
        r0 = r18;
        r0.<init>(r4);
        r23 = new java.util.ArrayList;
        r23.<init>();
        r25 = new java.util.ArrayList;
        r25.<init>();
        if (r27 != 0) goto L_0x02de;
    L_0x024b:
        r4 = "Retry";
        r5 = 2131232270; // 0x7f08060e float:1.8080645E38 double:1.052968648E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 0;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "Delete";
        r5 = 2131231270; // 0x7f080226 float:1.8078616E38 double:1.052968154E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 1;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x027d:
        r4 = r25.isEmpty();
        if (r4 != 0) goto L_0x000a;
    L_0x0283:
        r4 = r23.size();
        r4 = new java.lang.CharSequence[r4];
        r0 = r23;
        r20 = r0.toArray(r4);
        r20 = (java.lang.CharSequence[]) r20;
        r4 = new org.telegram.ui.ChatActivity$112;
        r0 = r30;
        r1 = r25;
        r4.<init>(r1);
        r0 = r18;
        r1 = r20;
        r0.setItems(r1, r4);
        r4 = "Message";
        r5 = 2131231802; // 0x7f08043a float:1.8079695E38 double:1.0529684167E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r18;
        r0.setTitle(r4);
        r4 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r5 = "plusconfig";
        r6 = 0;
        r26 = r4.getSharedPreferences(r5, r6);
        r4 = "disableMessageClick";
        r5 = 0;
        r0 = r26;
        r4 = r0.getBoolean(r4, r5);
        if (r4 != 0) goto L_0x000a;
    L_0x02c6:
        r4 = r18.create();
        r0 = r30;
        r0.showDialog(r4);
        goto L_0x000a;
    L_0x02d1:
        r21 = 0;
        goto L_0x00a7;
    L_0x02d5:
        r14 = 0;
        goto L_0x010b;
    L_0x02d8:
        r15 = 0;
        goto L_0x015e;
    L_0x02db:
        r13 = 0;
        goto L_0x0183;
    L_0x02de:
        r4 = 1;
        r0 = r27;
        if (r0 != r4) goto L_0x0474;
    L_0x02e3:
        r0 = r30;
        r4 = r0.currentChat;
        if (r4 == 0) goto L_0x03a7;
    L_0x02e9:
        r0 = r30;
        r4 = r0.isBroadcast;
        if (r4 != 0) goto L_0x03a7;
    L_0x02ef:
        if (r12 == 0) goto L_0x030b;
    L_0x02f1:
        r4 = "Reply";
        r5 = 2131232227; // 0x7f0805e3 float:1.8080557E38 double:1.0529686267E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 8;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x030b:
        r0 = r30;
        r4 = r0.directShareToMenu;
        if (r4 == 0) goto L_0x032b;
    L_0x0311:
        r4 = "DirectShare";
        r5 = 2131231301; // 0x7f080245 float:1.807868E38 double:1.052968169E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 98;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x032b:
        if (r15 == 0) goto L_0x038a;
    L_0x032d:
        r4 = "UnpinMessage";
        r5 = 2131232534; // 0x7f080716 float:1.808118E38 double:1.0529687783E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 14;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0347:
        if (r13 == 0) goto L_0x0363;
    L_0x0349:
        r4 = "Edit";
        r5 = 2131231326; // 0x7f08025e float:1.807873E38 double:1.0529681815E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 12;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0363:
        r0 = r30;
        r4 = r0.currentChat;
        r0 = r24;
        r4 = r0.canDeleteMessage(r4);
        if (r4 == 0) goto L_0x027d;
    L_0x036f:
        r4 = "Delete";
        r5 = 2131231270; // 0x7f080226 float:1.8078616E38 double:1.052968154E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 1;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x027d;
    L_0x038a:
        if (r14 == 0) goto L_0x0347;
    L_0x038c:
        r4 = "PinMessage";
        r5 = 2131232162; // 0x7f0805a2 float:1.8080425E38 double:1.0529685946E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 13;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x0347;
    L_0x03a7:
        r0 = r24;
        r4 = r0.messageOwner;
        r4 = r4.action;
        if (r4 == 0) goto L_0x0413;
    L_0x03af:
        r0 = r24;
        r4 = r0.messageOwner;
        r4 = r4.action;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall;
        if (r4 == 0) goto L_0x0413;
    L_0x03b9:
        r0 = r24;
        r4 = r0.messageOwner;
        r0 = r4.action;
        r19 = r0;
        r19 = (org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall) r19;
        r0 = r19;
        r4 = r0.reason;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonMissed;
        if (r4 != 0) goto L_0x03d3;
    L_0x03cb:
        r0 = r19;
        r4 = r0.reason;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonBusy;
        if (r4 == 0) goto L_0x0468;
    L_0x03d3:
        r4 = r24.isOutOwner();
        if (r4 != 0) goto L_0x0468;
    L_0x03d9:
        r4 = "CallBack";
        r5 = 2131231000; // 0x7f080118 float:1.8078069E38 double:1.0529680205E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
    L_0x03e3:
        r0 = r23;
        r0.add(r4);
        r4 = 18;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = org.telegram.ui.Components.voip.VoIPHelper.canRateCall(r19);
        if (r4 == 0) goto L_0x0413;
    L_0x03f9:
        r4 = "CallMessageReportProblem";
        r5 = 2131231008; // 0x7f080120 float:1.8078085E38 double:1.0529680244E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 19;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0413:
        if (r32 == 0) goto L_0x043b;
    L_0x0415:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.getId();
        if (r4 <= 0) goto L_0x043b;
    L_0x041f:
        if (r12 == 0) goto L_0x043b;
    L_0x0421:
        r4 = "Reply";
        r5 = 2131232227; // 0x7f0805e3 float:1.8080557E38 double:1.0529686267E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 8;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x043b:
        r0 = r30;
        r4 = r0.isDownloadManager;
        if (r4 != 0) goto L_0x044d;
    L_0x0441:
        r0 = r30;
        r4 = r0.currentChat;
        r0 = r24;
        r4 = r0.canDeleteMessage(r4);
        if (r4 == 0) goto L_0x027d;
    L_0x044d:
        r4 = "Delete";
        r5 = 2131231270; // 0x7f080226 float:1.8078616E38 double:1.052968154E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 1;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x027d;
    L_0x0468:
        r4 = "CallAgain";
        r5 = 2131230999; // 0x7f080117 float:1.8078067E38 double:1.05296802E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        goto L_0x03e3;
    L_0x0474:
        r4 = 20;
        r0 = r27;
        if (r0 != r4) goto L_0x04c7;
    L_0x047a:
        r4 = "Retry";
        r5 = 2131232270; // 0x7f08060e float:1.8080645E38 double:1.052968648E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 0;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "Copy";
        r5 = 2131231218; // 0x7f0801f2 float:1.807851E38 double:1.052968128E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 3;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "Delete";
        r5 = 2131231270; // 0x7f080226 float:1.8078616E38 double:1.052968154E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 1;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x027d;
    L_0x04c7:
        r0 = r30;
        r4 = r0.currentEncryptedChat;
        if (r4 != 0) goto L_0x0a66;
    L_0x04cd:
        if (r12 == 0) goto L_0x04e9;
    L_0x04cf:
        r4 = "Reply";
        r5 = 2131232227; // 0x7f0805e3 float:1.8080557E38 double:1.0529686267E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 8;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x04e9:
        r0 = r30;
        r4 = r0.directShareToMenu;
        if (r4 == 0) goto L_0x0509;
    L_0x04ef:
        r4 = "DirectShare";
        r5 = 2131231301; // 0x7f080245 float:1.807868E38 double:1.052968169E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 98;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0509:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.type;
        if (r4 == 0) goto L_0x0519;
    L_0x0511:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.caption;
        if (r4 == 0) goto L_0x0532;
    L_0x0519:
        r4 = "Copy";
        r5 = 2131231218; // 0x7f0801f2 float:1.807851E38 double:1.052968128E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 3;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0532:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = org.telegram.messenger.ChatObject.isChannel(r4);
        if (r4 == 0) goto L_0x0574;
    L_0x053c:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.megagroup;
        if (r4 == 0) goto L_0x0574;
    L_0x0544:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = r4.username;
        r4 = android.text.TextUtils.isEmpty(r4);
        if (r4 != 0) goto L_0x0574;
    L_0x0550:
        r0 = r30;
        r4 = r0.currentChat;
        r4 = org.telegram.messenger.ChatObject.hasAdminRights(r4);
        if (r4 == 0) goto L_0x0574;
    L_0x055a:
        r4 = "CopyLink";
        r5 = 2131231219; // 0x7f0801f3 float:1.8078513E38 double:1.0529681287E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 22;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0574:
        r4 = 3;
        r0 = r27;
        if (r0 != r4) goto L_0x06bd;
    L_0x0579:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.messageOwner;
        r4 = r4.media;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
        if (r4 == 0) goto L_0x05b1;
    L_0x0585:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.messageOwner;
        r4 = r4.media;
        r4 = r4.webpage;
        r4 = r4.document;
        r4 = org.telegram.messenger.MessageObject.isNewGifDocument(r4);
        if (r4 == 0) goto L_0x05b1;
    L_0x0597:
        r4 = "SaveToGIFs";
        r5 = 2131232301; // 0x7f08062d float:1.8080707E38 double:1.052968663E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 11;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x05b1:
        r4 = "MultiForward";
        r5 = 2131231844; // 0x7f080464 float:1.807978E38 double:1.0529684374E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 23;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "AdvancedForward";
        r5 = 2131230854; // 0x7f080086 float:1.8077773E38 double:1.0529679483E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 24;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "ForwardNoQuote";
        r5 = 2131231497; // 0x7f080309 float:1.8079077E38 double:1.052968266E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 2;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.isMediaEmpty();
        if (r4 != 0) goto L_0x062a;
    L_0x0608:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.mediaExists;
        if (r4 != 0) goto L_0x062a;
    L_0x0610:
        r4 = "AddDownloadManager";
        r5 = 2131230832; // 0x7f080070 float:1.8077728E38 double:1.0529679374E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 25;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x062a:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.isSecretPhoto();
        if (r4 != 0) goto L_0x0658;
    L_0x0634:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.isLiveLocation();
        if (r4 != 0) goto L_0x0658;
    L_0x063e:
        r4 = "Forward";
        r5 = 2131231492; // 0x7f080304 float:1.8079067E38 double:1.0529682635E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 30;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0658:
        if (r15 == 0) goto L_0x0a48;
    L_0x065a:
        r4 = "UnpinMessage";
        r5 = 2131232534; // 0x7f080716 float:1.808118E38 double:1.0529687783E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 14;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0674:
        if (r13 == 0) goto L_0x0690;
    L_0x0676:
        r4 = "Edit";
        r5 = 2131231326; // 0x7f08025e float:1.807873E38 double:1.0529681815E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 12;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0690:
        r0 = r30;
        r4 = r0.isDownloadManager;
        if (r4 != 0) goto L_0x06a2;
    L_0x0696:
        r0 = r30;
        r4 = r0.currentChat;
        r0 = r24;
        r4 = r0.canDeleteMessage(r4);
        if (r4 == 0) goto L_0x027d;
    L_0x06a2:
        r4 = "Delete";
        r5 = 2131231270; // 0x7f080226 float:1.8078616E38 double:1.052968154E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 1;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x027d;
    L_0x06bd:
        r4 = 4;
        r0 = r27;
        if (r0 != r4) goto L_0x07d5;
    L_0x06c2:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.isVideo();
        if (r4 == 0) goto L_0x070a;
    L_0x06cc:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.isSecretPhoto();
        if (r4 != 0) goto L_0x05b1;
    L_0x06d6:
        r4 = "SaveToGallery";
        r5 = 2131232302; // 0x7f08062e float:1.808071E38 double:1.0529686637E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 4;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "ShareFile";
        r5 = 2131232401; // 0x7f080691 float:1.808091E38 double:1.0529687126E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 6;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x070a:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.isMusic();
        if (r4 == 0) goto L_0x0749;
    L_0x0714:
        r4 = "SaveToMusic";
        r5 = 2131232304; // 0x7f080630 float:1.8080713E38 double:1.0529686647E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 10;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "ShareFile";
        r5 = 2131232401; // 0x7f080691 float:1.808091E38 double:1.0529687126E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 6;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x0749:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.getDocument();
        if (r4 == 0) goto L_0x07b0;
    L_0x0753:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.getDocument();
        r4 = org.telegram.messenger.MessageObject.isNewGifDocument(r4);
        if (r4 == 0) goto L_0x077b;
    L_0x0761:
        r4 = "SaveToGIFs";
        r5 = 2131232301; // 0x7f08062d float:1.8080707E38 double:1.052968663E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 11;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x077b:
        r4 = "SaveToDownloads";
        r5 = 2131232300; // 0x7f08062c float:1.8080705E38 double:1.0529686627E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 10;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "ShareFile";
        r5 = 2131232401; // 0x7f080691 float:1.808091E38 double:1.0529687126E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 6;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x07b0:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.isSecretPhoto();
        if (r4 != 0) goto L_0x05b1;
    L_0x07ba:
        r4 = "SaveToGallery";
        r5 = 2131232302; // 0x7f08062e float:1.808071E38 double:1.0529686637E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 4;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x07d5:
        r4 = 5;
        r0 = r27;
        if (r0 != r4) goto L_0x0828;
    L_0x07da:
        r4 = "ApplyLocalizationFile";
        r5 = 2131230886; // 0x7f0800a6 float:1.8077837E38 double:1.052967964E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 5;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "SaveToDownloads";
        r5 = 2131232300; // 0x7f08062c float:1.8080705E38 double:1.0529686627E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 10;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "ShareFile";
        r5 = 2131232401; // 0x7f080691 float:1.808091E38 double:1.0529687126E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 6;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x0828:
        r4 = 10;
        r0 = r27;
        if (r0 != r4) goto L_0x087c;
    L_0x082e:
        r4 = "ApplyThemeFile";
        r5 = 2131230888; // 0x7f0800a8 float:1.8077841E38 double:1.052967965E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 5;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "SaveToDownloads";
        r5 = 2131232300; // 0x7f08062c float:1.8080705E38 double:1.0529686627E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 10;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "ShareFile";
        r5 = 2131232401; // 0x7f080691 float:1.808091E38 double:1.0529687126E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 6;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x087c:
        r4 = 6;
        r0 = r27;
        if (r0 != r4) goto L_0x08cf;
    L_0x0881:
        r4 = "SaveToGallery";
        r5 = 2131232302; // 0x7f08062e float:1.808071E38 double:1.0529686637E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 7;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "SaveToDownloads";
        r5 = 2131232300; // 0x7f08062c float:1.8080705E38 double:1.0529686627E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 10;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "ShareFile";
        r5 = 2131232401; // 0x7f080691 float:1.808091E38 double:1.0529687126E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 6;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x08cf:
        r4 = 7;
        r0 = r27;
        if (r0 != r4) goto L_0x0960;
    L_0x08d4:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.isMask();
        if (r4 == 0) goto L_0x08fa;
    L_0x08de:
        r4 = "AddToMasks";
        r5 = 2131230847; // 0x7f08007f float:1.8077758E38 double:1.052967945E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 9;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x08fa:
        r4 = "AddToStickers";
        r5 = 2131230848; // 0x7f080080 float:1.807776E38 double:1.0529679454E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 9;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.getDocument();
        r4 = org.telegram.messenger.query.StickersQuery.isStickerInFavorites(r4);
        if (r4 != 0) goto L_0x0944;
    L_0x0922:
        r4 = org.telegram.messenger.query.StickersQuery.canAddStickerToFavorites();
        if (r4 == 0) goto L_0x05b1;
    L_0x0928:
        r4 = "AddToFavorites";
        r5 = 2131230846; // 0x7f08007e float:1.8077756E38 double:1.0529679444E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 20;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x0944:
        r4 = "DeleteFromFavorites";
        r5 = 2131231284; // 0x7f080234 float:1.8078645E38 double:1.052968161E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 21;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x0960:
        r4 = 8;
        r0 = r27;
        if (r0 != r4) goto L_0x09fc;
    L_0x0966:
        r4 = org.telegram.messenger.MessagesController.getInstance();
        r0 = r30;
        r5 = r0.selectedObject;
        r5 = r5.messageOwner;
        r5 = r5.media;
        r5 = r5.user_id;
        r5 = java.lang.Integer.valueOf(r5);
        r28 = r4.getUser(r5);
        if (r28 == 0) goto L_0x09b6;
    L_0x097e:
        r0 = r28;
        r4 = r0.id;
        r5 = org.telegram.messenger.UserConfig.getClientUserId();
        if (r4 == r5) goto L_0x09b6;
    L_0x0988:
        r4 = org.telegram.messenger.ContactsController.getInstance();
        r4 = r4.contactsDict;
        r0 = r28;
        r5 = r0.id;
        r5 = java.lang.Integer.valueOf(r5);
        r4 = r4.get(r5);
        if (r4 != 0) goto L_0x09b6;
    L_0x099c:
        r4 = "AddContactTitle";
        r5 = 2131230831; // 0x7f08006f float:1.8077726E38 double:1.052967937E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 15;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x09b6:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.messageOwner;
        r4 = r4.media;
        r4 = r4.phone_number;
        r4 = android.text.TextUtils.isEmpty(r4);
        if (r4 != 0) goto L_0x05b1;
    L_0x09c6:
        r4 = "Copy";
        r5 = 2131231218; // 0x7f0801f2 float:1.807851E38 double:1.052968128E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 16;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "Call";
        r5 = 2131230998; // 0x7f080116 float:1.8078065E38 double:1.0529680195E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 17;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x09fc:
        r4 = 9;
        r0 = r27;
        if (r0 != r4) goto L_0x05b1;
    L_0x0a02:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.getDocument();
        r4 = org.telegram.messenger.query.StickersQuery.isStickerInFavorites(r4);
        if (r4 != 0) goto L_0x0a2c;
    L_0x0a10:
        r4 = "AddToFavorites";
        r5 = 2131230846; // 0x7f08007e float:1.8077756E38 double:1.0529679444E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 20;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x0a2c:
        r4 = "DeleteFromFavorites";
        r5 = 2131231284; // 0x7f080234 float:1.8078645E38 double:1.052968161E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 21;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x05b1;
    L_0x0a48:
        if (r14 == 0) goto L_0x0674;
    L_0x0a4a:
        r4 = "PinMessage";
        r5 = 2131232162; // 0x7f0805a2 float:1.8080425E38 double:1.0529685946E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 13;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x0674;
    L_0x0a66:
        if (r12 == 0) goto L_0x0a82;
    L_0x0a68:
        r4 = "Reply";
        r5 = 2131232227; // 0x7f0805e3 float:1.8080557E38 double:1.0529686267E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 8;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0a82:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.type;
        if (r4 == 0) goto L_0x0a92;
    L_0x0a8a:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.caption;
        if (r4 == 0) goto L_0x0aab;
    L_0x0a92:
        r4 = "Copy";
        r5 = 2131231218; // 0x7f0801f2 float:1.807851E38 double:1.052968128E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 3;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0aab:
        r4 = 4;
        r0 = r27;
        if (r0 != r4) goto L_0x0ba9;
    L_0x0ab0:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.isVideo();
        if (r4 == 0) goto L_0x0b07;
    L_0x0aba:
        r4 = "SaveToGallery";
        r5 = 2131232302; // 0x7f08062e float:1.808071E38 double:1.0529686637E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 4;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "ShareFile";
        r5 = 2131232401; // 0x7f080691 float:1.808091E38 double:1.0529687126E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 6;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
    L_0x0aec:
        r4 = "Delete";
        r5 = 2131231270; // 0x7f080226 float:1.8078616E38 double:1.052968154E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 1;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x027d;
    L_0x0b07:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.isMusic();
        if (r4 == 0) goto L_0x0b45;
    L_0x0b11:
        r4 = "SaveToMusic";
        r5 = 2131232304; // 0x7f080630 float:1.8080713E38 double:1.0529686647E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 10;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "ShareFile";
        r5 = 2131232401; // 0x7f080691 float:1.808091E38 double:1.0529687126E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 6;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x0aec;
    L_0x0b45:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.isVideo();
        if (r4 != 0) goto L_0x0b8e;
    L_0x0b4f:
        r0 = r30;
        r4 = r0.selectedObject;
        r4 = r4.getDocument();
        if (r4 == 0) goto L_0x0b8e;
    L_0x0b59:
        r4 = "SaveToDownloads";
        r5 = 2131232300; // 0x7f08062c float:1.8080705E38 double:1.0529686627E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 10;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        r4 = "ShareFile";
        r5 = 2131232401; // 0x7f080691 float:1.808091E38 double:1.0529687126E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 6;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x0aec;
    L_0x0b8e:
        r4 = "SaveToGallery";
        r5 = 2131232302; // 0x7f08062e float:1.808071E38 double:1.0529686637E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 4;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x0aec;
    L_0x0ba9:
        r4 = 5;
        r0 = r27;
        if (r0 != r4) goto L_0x0bc9;
    L_0x0bae:
        r4 = "ApplyLocalizationFile";
        r5 = 2131230886; // 0x7f0800a6 float:1.8077837E38 double:1.052967964E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 5;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x0aec;
    L_0x0bc9:
        r4 = 10;
        r0 = r27;
        if (r0 != r4) goto L_0x0bea;
    L_0x0bcf:
        r4 = "ApplyThemeFile";
        r5 = 2131230888; // 0x7f0800a8 float:1.8077841E38 double:1.052967965E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 5;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x0aec;
    L_0x0bea:
        r4 = 7;
        r0 = r27;
        if (r0 != r4) goto L_0x0aec;
    L_0x0bef:
        r4 = "AddToStickers";
        r5 = 2131230848; // 0x7f080080 float:1.807776E38 double:1.0529679454E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r0 = r23;
        r0.add(r4);
        r4 = 9;
        r4 = java.lang.Integer.valueOf(r4);
        r0 = r25;
        r0.add(r4);
        goto L_0x0aec;
    L_0x0c0b:
        r0 = r30;
        r4 = r0.actionBar;
        r11 = r4.createActionMode();
        r4 = 11;
        r22 = r11.getItem(r4);
        if (r22 == 0) goto L_0x0c21;
    L_0x0c1b:
        r4 = 0;
        r0 = r22;
        r0.setVisibility(r4);
    L_0x0c21:
        r4 = 12;
        r22 = r11.getItem(r4);
        if (r22 == 0) goto L_0x0c2f;
    L_0x0c29:
        r4 = 0;
        r0 = r22;
        r0.setVisibility(r4);
    L_0x0c2f:
        r0 = r30;
        r4 = r0.actionBar;
        r4.showActionMode();
        r4 = 1;
        r0 = r30;
        r0.updatePinnedMessageView(r4);
        r16 = new android.animation.AnimatorSet;
        r16.<init>();
        r17 = new java.util.ArrayList;
        r17.<init>();
        r10 = 0;
    L_0x0c47:
        r0 = r30;
        r4 = r0.actionModeViews;
        r4 = r4.size();
        if (r10 >= r4) goto L_0x0c81;
    L_0x0c51:
        r0 = r30;
        r4 = r0.actionModeViews;
        r29 = r4.get(r10);
        r29 = (android.view.View) r29;
        r4 = org.telegram.ui.ActionBar.ActionBar.getCurrentActionBarHeight();
        r4 = r4 / 2;
        r4 = (float) r4;
        r0 = r29;
        r0.setPivotY(r4);
        org.telegram.messenger.AndroidUtilities.clearDrawableAnimation(r29);
        r4 = "scaleY";
        r5 = 2;
        r5 = new float[r5];
        r5 = {1036831949, 1065353216};
        r0 = r29;
        r4 = android.animation.ObjectAnimator.ofFloat(r0, r4, r5);
        r0 = r17;
        r0.add(r4);
        r10 = r10 + 1;
        goto L_0x0c47;
    L_0x0c81:
        r16.playTogether(r17);
        r4 = 250; // 0xfa float:3.5E-43 double:1.235E-321;
        r0 = r16;
        r0.setDuration(r4);
        r16.start();
        r0 = r30;
        r1 = r24;
        r2 = r33;
        r0.addToSelectedMessages(r1, r2);
        r0 = r30;
        r4 = r0.selectedMessagesCountTextView;
        r0 = r30;
        r5 = r0.selectedMessagesIds;
        r6 = 0;
        r5 = r5[r6];
        r5 = r5.size();
        r0 = r30;
        r6 = r0.selectedMessagesIds;
        r7 = 1;
        r6 = r6[r7];
        r6 = r6.size();
        r5 = r5 + r6;
        r6 = 0;
        r4.setNumber(r5, r6);
        r30.updateVisibleRows();
        goto L_0x000a;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.ChatActivity.createMenu(android.view.View, boolean, boolean, boolean):void");
    }

    private void startEditingMessageObject(MessageObject messageObject) {
        if (messageObject != null && getParentActivity() != null) {
            boolean z;
            if (this.searchItem != null && this.actionBar.isSearchFieldVisible()) {
                this.actionBar.closeSearchField();
                this.chatActivityEnterView.setFieldFocused();
            }
            this.mentionsAdapter.setNeedBotContext(false);
            this.chatListView.setOnItemLongClickListener(null);
            this.chatListView.setOnItemClickListener((OnItemClickListenerExtended) null);
            this.chatListView.setClickable(false);
            this.chatListView.setLongClickable(false);
            ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
            if (messageObject.isMediaEmpty()) {
                z = false;
            } else {
                z = true;
            }
            chatActivityEnterView.setEditingMessageObject(messageObject, z);
            updateBottomOverlay();
            if (this.chatActivityEnterView.isEditingCaption()) {
                this.mentionsAdapter.setAllowNewMentions(false);
            }
            this.actionModeTitleContainer.setVisibility(0);
            this.selectedMessagesCountTextView.setVisibility(8);
            checkEditTimer();
            this.chatActivityEnterView.setAllowStickersAndGifs(false, false);
            ActionBarMenu actionMode = this.actionBar.createActionMode();
            actionMode.getItem(19).setVisibility(8);
            actionMode.getItem(10).setVisibility(8);
            actionMode.getItem(11).setVisibility(8);
            actionMode.getItem(12).setVisibility(8);
            actionMode.getItem(23).setVisibility(8);
            this.actionBar.showActionMode();
            updatePinnedMessageView(true);
            updateVisibleRows();
            TLRPC$TL_messages_getMessageEditData req = new TLRPC$TL_messages_getMessageEditData();
            req.peer = MessagesController.getInputPeer((int) this.dialog_id);
            req.id = messageObject.getId();
            this.editingMessageObjectReqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            ChatActivity.this.editingMessageObjectReqId = 0;
                            if (response == null) {
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

    private String getMessageContent(MessageObject messageObject, int previousUid, boolean name) {
        String str = "";
        if (name && previousUid != messageObject.messageOwner.from_id) {
            if (messageObject.messageOwner.from_id > 0) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.from_id));
                if (user != null) {
                    str = ContactsController.formatName(user.first_name, user.last_name) + ":\n";
                }
            } else if (messageObject.messageOwner.from_id < 0) {
                TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-messageObject.messageOwner.from_id));
                if (chat != null) {
                    str = chat.title + ":\n";
                }
            }
        }
        if (messageObject.type == 0 && messageObject.messageOwner.message != null) {
            return str + messageObject.messageOwner.message;
        }
        if (messageObject.messageOwner.media == null || messageObject.messageOwner.media.caption == null) {
            return str + messageObject.messageText;
        }
        return str + messageObject.messageOwner.media.caption;
    }

    private void saveMessageToGallery(MessageObject messageObject) {
        String path = messageObject.messageOwner.attachPath;
        if (!(TextUtils.isEmpty(path) || new File(path).exists())) {
            path = null;
        }
        if (TextUtils.isEmpty(path)) {
            path = FileLoader.getPathToMessage(messageObject.messageOwner).toString();
        }
        MediaController.saveFile(path, getParentActivity(), messageObject.type == 3 ? 1 : 0, null, null);
    }

    private void processSelectedOption(int option) {
        if (this.selectedObject != null) {
            int a;
            Bundle args;
            BaseFragment dialogsActivity;
            File file;
            org.telegram.ui.ActionBar.AlertDialog.Builder builder;
            String path;
            Intent intent;
            switch (option) {
                case 0:
                    if (this.selectedObjectGroup == null) {
                        if (SendMessagesHelper.getInstance().retrySendMessage(this.selectedObject, false)) {
                            moveScrollToLastMessage();
                            break;
                        }
                    }
                    boolean success = true;
                    for (a = 0; a < this.selectedObjectGroup.messages.size(); a++) {
                        if (!SendMessagesHelper.getInstance().retrySendMessage((MessageObject) this.selectedObjectGroup.messages.get(a), false)) {
                            success = false;
                        }
                    }
                    if (success) {
                        moveScrollToLastMessage();
                        break;
                    }
                    break;
                case 1:
                    if (getParentActivity() != null) {
                        createDeleteMessagesAlert(this.selectedObject, this.selectedObjectGroup);
                        break;
                    }
                    this.selectedObject = null;
                    this.selectedObjectGroup = null;
                    return;
                case 2:
                case 30:
                    QuoteForward = option == 30;
                    this.forwardingMessage = this.selectedObject;
                    this.forwardingMessageGroup = this.selectedObjectGroup;
                    args = new Bundle();
                    args.putBoolean("onlySelect", true);
                    args.putInt("dialogsType", 3);
                    dialogsActivity = new DialogsActivity(args);
                    dialogsActivity.setDelegate(this);
                    presentFragment(dialogsActivity);
                    break;
                case 3:
                    AndroidUtilities.addToClipboard(getMessageContent(this.selectedObject, 0, false));
                    break;
                case 4:
                    if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        if (this.selectedObjectGroup == null) {
                            saveMessageToGallery(this.selectedObject);
                            break;
                        }
                        for (a = 0; a < this.selectedObjectGroup.messages.size(); a++) {
                            saveMessageToGallery((MessageObject) this.selectedObjectGroup.messages.get(a));
                        }
                        break;
                    }
                    getParentActivity().requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 4);
                    this.selectedObject = null;
                    this.selectedObjectGroup = null;
                    return;
                case 5:
                    File locFile = null;
                    if (!TextUtils.isEmpty(this.selectedObject.messageOwner.attachPath)) {
                        file = new File(this.selectedObject.messageOwner.attachPath);
                        if (file.exists()) {
                            locFile = file;
                        }
                    }
                    if (locFile == null) {
                        File f = FileLoader.getPathToMessage(this.selectedObject.messageOwner);
                        if (f.exists()) {
                            locFile = f;
                        }
                    }
                    if (locFile != null) {
                        if (!locFile.getName().endsWith("attheme")) {
                            if (!LocaleController.getInstance().applyLanguageFile(locFile)) {
                                if (getParentActivity() != null) {
                                    builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
                                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                    builder.setMessage(LocaleController.getString("IncorrectLocalization", R.string.IncorrectLocalization));
                                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                    showDialog(builder.create());
                                    break;
                                }
                                this.selectedObject = null;
                                this.selectedObjectGroup = null;
                                return;
                            }
                            presentFragment(new LanguageSelectActivity());
                            break;
                        }
                        if (this.chatLayoutManager != null) {
                            int lastPosition = this.chatLayoutManager.findFirstVisibleItemPosition();
                            if (lastPosition != 0) {
                                this.scrollToPositionOnRecreate = lastPosition;
                                Holder holder = (Holder) this.chatListView.findViewHolderForAdapterPosition(this.scrollToPositionOnRecreate);
                                if (holder != null) {
                                    this.scrollToOffsetOnRecreate = (this.chatListView.getMeasuredHeight() - holder.itemView.getBottom()) - this.chatListView.getPaddingBottom();
                                } else {
                                    this.scrollToPositionOnRecreate = -1;
                                }
                            } else {
                                this.scrollToPositionOnRecreate = -1;
                            }
                        }
                        ThemeInfo themeInfo = Theme.applyThemeFile(locFile, this.selectedObject.getDocumentName(), true);
                        if (themeInfo == null) {
                            this.scrollToPositionOnRecreate = -1;
                            if (getParentActivity() != null) {
                                builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                builder.setMessage(LocaleController.getString("IncorrectTheme", R.string.IncorrectTheme));
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                showDialog(builder.create());
                                break;
                            }
                            this.selectedObject = null;
                            this.selectedObjectGroup = null;
                            return;
                        }
                        presentFragment(new ThemePreviewActivity(locFile, themeInfo));
                        break;
                    }
                    break;
                case 6:
                    path = this.selectedObject.messageOwner.attachPath;
                    if (!(path == null || path.length() <= 0 || new File(path).exists())) {
                        path = null;
                    }
                    if (path == null || path.length() == 0) {
                        path = FileLoader.getPathToMessage(this.selectedObject.messageOwner).toString();
                    }
                    intent = new Intent("android.intent.action.SEND");
                    intent.setType(this.selectedObject.getDocument().mime_type);
                    file = new File(path);
                    if (VERSION.SDK_INT >= 24) {
                        try {
                            intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(getParentActivity(), "org.ir.talaeii.provider", file));
                            intent.setFlags(1);
                        } catch (Exception e) {
                            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                        }
                    } else {
                        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                    }
                    getParentActivity().startActivityForResult(Intent.createChooser(intent, LocaleController.getString("ShareFile", R.string.ShareFile)), 500);
                    break;
                case 7:
                    path = this.selectedObject.messageOwner.attachPath;
                    if (!(path == null || path.length() <= 0 || new File(path).exists())) {
                        path = null;
                    }
                    if (path == null || path.length() == 0) {
                        path = FileLoader.getPathToMessage(this.selectedObject.messageOwner).toString();
                    }
                    if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        MediaController.saveFile(path, getParentActivity(), 0, null, null);
                        break;
                    }
                    getParentActivity().requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 4);
                    this.selectedObject = null;
                    this.selectedObjectGroup = null;
                    return;
                case 8:
                    showReplyPanel(true, this.selectedObject, null, null, false);
                    break;
                case 9:
                    Context parentActivity = getParentActivity();
                    TLRPC$InputStickerSet inputStickerSet = this.selectedObject.getInputStickerSet();
                    StickersAlertDelegate stickersAlertDelegate = (this.bottomOverlayChat.getVisibility() == 0 || !ChatObject.canSendStickers(this.currentChat)) ? null : this.chatActivityEnterView;
                    showDialog(new StickersAlert(parentActivity, this, inputStickerSet, null, stickersAlertDelegate));
                    break;
                case 10:
                    if (VERSION.SDK_INT < 23 || getParentActivity().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        String fileName = FileLoader.getDocumentFileName(this.selectedObject.getDocument());
                        if (TextUtils.isEmpty(fileName)) {
                            fileName = this.selectedObject.getFileName();
                        }
                        path = this.selectedObject.messageOwner.attachPath;
                        if (!(path == null || path.length() <= 0 || new File(path).exists())) {
                            path = null;
                        }
                        if (path == null || path.length() == 0) {
                            path = FileLoader.getPathToMessage(this.selectedObject.messageOwner).toString();
                        }
                        MediaController.saveFile(path, getParentActivity(), this.selectedObject.isMusic() ? 3 : 2, fileName, this.selectedObject.getDocument() != null ? this.selectedObject.getDocument().mime_type : "");
                        break;
                    }
                    getParentActivity().requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 4);
                    this.selectedObject = null;
                    this.selectedObjectGroup = null;
                    return;
                    break;
                case 11:
                    TLRPC$Document document = this.selectedObject.getDocument();
                    MessagesController.getInstance().saveGif(document);
                    showGifHint();
                    this.chatActivityEnterView.addRecentGif(document);
                    break;
                case 12:
                    startEditingMessageObject(this.selectedObject);
                    this.selectedObject = null;
                    this.selectedObjectGroup = null;
                    break;
                case 13:
                    boolean[] checks;
                    int mid = this.selectedObject.getId();
                    builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
                    if (ChatObject.isChannel(this.currentChat) && this.currentChat.megagroup) {
                        int i;
                        builder.setMessage(LocaleController.getString("PinMessageAlert", R.string.PinMessageAlert));
                        checks = new boolean[]{true};
                        View frameLayout = new FrameLayout(getParentActivity());
                        frameLayout = new CheckBoxCell(getParentActivity(), true);
                        frameLayout.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                        frameLayout.setText(LocaleController.getString("PinNotify", R.string.PinNotify), "", true, false);
                        int dp = LocaleController.isRTL ? AndroidUtilities.dp(8.0f) : 0;
                        if (LocaleController.isRTL) {
                            i = 0;
                        } else {
                            i = AndroidUtilities.dp(8.0f);
                        }
                        frameLayout.setPadding(dp, 0, i, 0);
                        frameLayout.addView(frameLayout, LayoutHelper.createFrame(-1, 48.0f, 51, 8.0f, 0.0f, 8.0f, 0.0f));
                        final boolean[] zArr = checks;
                        frameLayout.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                boolean z;
                                CheckBoxCell cell = (CheckBoxCell) v;
                                boolean[] zArr = zArr;
                                if (zArr[0]) {
                                    z = false;
                                } else {
                                    z = true;
                                }
                                zArr[0] = z;
                                cell.setChecked(zArr[0], true);
                            }
                        });
                        builder.setView(frameLayout);
                    } else {
                        builder.setMessage(LocaleController.getString("PinMessageAlertChannel", R.string.PinMessageAlertChannel));
                        checks = new boolean[]{false};
                    }
                    final int i2 = mid;
                    final boolean[] zArr2 = checks;
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MessagesController.getInstance().pinChannelMessage(ChatActivity.this.currentChat, i2, zArr2[0]);
                        }
                    });
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    showDialog(builder.create());
                    break;
                case 14:
                    builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
                    builder.setMessage(LocaleController.getString("UnpinMessageAlert", R.string.UnpinMessageAlert));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MessagesController.getInstance().pinChannelMessage(ChatActivity.this.currentChat, 0, false);
                        }
                    });
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    showDialog(builder.create());
                    break;
                case 15:
                    args = new Bundle();
                    args.putInt("user_id", this.selectedObject.messageOwner.media.user_id);
                    args.putString("phone", this.selectedObject.messageOwner.media.phone_number);
                    args.putBoolean("addContact", true);
                    presentFragment(new ContactAddActivity(args));
                    break;
                case 16:
                    AndroidUtilities.addToClipboard(this.selectedObject.messageOwner.media.phone_number);
                    break;
                case 17:
                    try {
                        intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + this.selectedObject.messageOwner.media.phone_number));
                        intent.addFlags(268435456);
                        getParentActivity().startActivityForResult(intent, 500);
                        break;
                    } catch (Exception e2) {
                        FileLog.e(e2);
                        break;
                    }
                case 18:
                    if (this.currentUser != null) {
                        VoIPHelper.startCall(this.currentUser, getParentActivity(), MessagesController.getInstance().getUserFull(this.currentUser.id));
                        break;
                    }
                    break;
                case 19:
                    VoIPHelper.showRateAlert(getParentActivity(), (TLRPC$TL_messageActionPhoneCall) this.selectedObject.messageOwner.action);
                    break;
                case 20:
                    StickersQuery.addRecentSticker(2, this.selectedObject.getDocument(), (int) (System.currentTimeMillis() / 1000), false);
                    break;
                case 21:
                    StickersQuery.addRecentSticker(2, this.selectedObject.getDocument(), (int) (System.currentTimeMillis() / 1000), true);
                    break;
                case 22:
                    TLObject req = new TLRPC$TL_channels_exportMessageLink();
                    req.id = this.selectedObject.getId();
                    req.channel = MessagesController.getInputChannel(this.currentChat);
                    ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                        public void run(final TLObject response, TLRPC$TL_error error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (response != null) {
                                        try {
                                            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", response.link));
                                            Toast.makeText(ApplicationLoader.applicationContext, LocaleController.getString("LinkCopied", R.string.LinkCopied), 0).show();
                                        } catch (Exception e) {
                                            FileLog.e(e);
                                        }
                                    }
                                }
                            });
                        }
                    });
                    break;
                case 23:
                    this.forwardingMessage = this.selectedObject;
                    this.showingDialog = true;
                    ArrayList<MessageObject> arr = new ArrayList();
                    arr.add(this.forwardingMessage);
                    ShareAlert visibleDialog = new ShareAlert(getParentActivity(), arr, "telegram", true, "", false);
                    showDialog(visibleDialog);
                    visibleDialog.setOnDismissListener(new OnDismissListener() {
                        public void onDismiss(DialogInterface dialog) {
                            ChatActivity.this.showingDialog = false;
                        }
                    });
                    visibleDialog.setOnCancelListener(new OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            ChatActivity.this.showingDialog = false;
                        }
                    });
                    break;
                case 24:
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isAdvancedForward", true);
                    dialogsActivity = new ChatActivity(bundle);
                    dialogsActivity.setMessageObject(this.selectedObject);
                    presentFragment(dialogsActivity);
                    break;
                case 25:
                    break;
            }
            int lastId = MessagesStorage.getInstance().getTotalMessageCount(111444999);
            TLRPC$messages_Messages messagesRes = new TLRPC$TL_messages_messages();
            this.selectedObject.messageOwner.date = (int) (System.currentTimeMillis() / 1000);
            this.selectedObject.messageOwner.id = lastId + 1;
            messagesRes.messages.add(this.selectedObject.messageOwner);
            MessagesStorage.getInstance().putMessages(messagesRes, 111444999, -1, 0, false);
            this.selectedObject = null;
            this.selectedObjectGroup = null;
        }
    }

    public void didSelectDialogs(DialogsActivity fragment, ArrayList<Long> dids, CharSequence message, boolean param) {
        if (this.forwardingMessage != null || !this.selectedMessagesIds[0].isEmpty() || !this.selectedMessagesIds[1].isEmpty()) {
            int a;
            ArrayList<MessageObject> fmessages = new ArrayList();
            if (this.forwardingMessage != null) {
                if (this.forwardingMessageGroup != null) {
                    fmessages.addAll(this.forwardingMessageGroup.messages);
                } else {
                    fmessages.add(this.forwardingMessage);
                }
                this.forwardingMessage = null;
                this.forwardingMessageGroup = null;
            } else {
                for (a = 1; a >= 0; a--) {
                    ArrayList<Integer> arrayList = new ArrayList(this.selectedMessagesIds[a].keySet());
                    Collections.sort(arrayList);
                    for (int b = 0; b < arrayList.size(); b++) {
                        Integer id = (Integer) arrayList.get(b);
                        MessageObject messageObject = (MessageObject) this.selectedMessagesIds[a].get(id);
                        if (messageObject != null && id.intValue() > 0) {
                            fmessages.add(messageObject);
                        }
                    }
                    this.selectedMessagesCanCopyIds[a].clear();
                    this.selectedMessagesCanStarIds[a].clear();
                    this.selectedMessagesIds[a].clear();
                }
                this.cantDeleteMessagesCount = 0;
                this.canEditMessagesCount = 0;
                this.actionBar.hideActionMode();
                updatePinnedMessageView(true);
            }
            long did;
            if (dids.size() > 1 || ((Long) dids.get(0)).longValue() == ((long) UserConfig.getClientUserId()) || message != null) {
                for (a = 0; a < dids.size(); a++) {
                    did = ((Long) dids.get(a)).longValue();
                    if (message != null) {
                        SendMessagesHelper.getInstance().sendMessage(message.toString(), did, null, null, true, null, null, null);
                    }
                    SendMessagesHelper.getInstance().sendMessage(fmessages, did);
                }
                fragment.finishFragment();
                return;
            }
            did = ((Long) dids.get(0)).longValue();
            if (did != this.dialog_id) {
                int lower_part = (int) did;
                int high_part = (int) (did >> 32);
                Bundle args = new Bundle();
                args.putBoolean("scrollToTopOnResume", this.scrollToTopOnResume);
                if (lower_part == 0) {
                    args.putInt("enc_id", high_part);
                } else if (lower_part > 0) {
                    args.putInt("user_id", lower_part);
                } else if (lower_part < 0) {
                    args.putInt("chat_id", -lower_part);
                }
                if (lower_part == 0 || MessagesController.checkCanOpenChat(args, fragment)) {
                    ChatActivity chatActivity = new ChatActivity(args);
                    if (presentFragment(chatActivity, true)) {
                        chatActivity.showReplyPanel(true, null, fmessages, null, false);
                        if (!AndroidUtilities.isTablet()) {
                            removeSelfFromStack();
                            return;
                        }
                        return;
                    }
                    fragment.finishFragment();
                    return;
                }
                return;
            }
            fragment.finishFragment();
            moveScrollToLastMessage();
            showReplyPanel(true, null, fmessages, null, false);
            if (AndroidUtilities.isTablet()) {
                this.actionBar.hideActionMode();
                updatePinnedMessageView(true);
            }
            updateVisibleRows();
        }
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
            public void onClick(DialogInterface dialog, int which) {
                if (ChatActivity.this.chatActivityEnterView != null) {
                    ChatActivity.this.chatActivityEnterView.cancelRecordingAudioVideo();
                }
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
        return true;
    }

    public boolean onBackPressed() {
        if (checkRecordLocked()) {
            return false;
        }
        if (this.actionBar != null && this.actionBar.isActionModeShowed()) {
            for (int a = 1; a >= 0; a--) {
                this.selectedMessagesIds[a].clear();
                this.selectedMessagesCanCopyIds[a].clear();
                this.selectedMessagesCanStarIds[a].clear();
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

    private void updateVisibleRows() {
        if (this.chatListView != null) {
            int count = this.chatListView.getChildCount();
            MessageObject editingMessageObject = this.chatActivityEnterView != null ? this.chatActivityEnterView.getEditingMessageObject() : null;
            for (int a = 0; a < count; a++) {
                View view = this.chatListView.getChildAt(a);
                if (view instanceof ChatMessageCell) {
                    ChatMessageCell cell = (ChatMessageCell) view;
                    MessageObject messageObject = cell.getMessageObject();
                    boolean disableSelection = false;
                    boolean selected = false;
                    if (this.actionBar.isActionModeShowed()) {
                        int idx = messageObject.getDialogId() == this.dialog_id ? 0 : 1;
                        if (messageObject == editingMessageObject || this.selectedMessagesIds[idx].containsKey(Integer.valueOf(messageObject.getId()))) {
                            setCellSelectionBackground(messageObject, cell, idx);
                            selected = true;
                        } else {
                            view.setBackgroundDrawable(null);
                        }
                        disableSelection = true;
                    } else {
                        view.setBackgroundDrawable(null);
                    }
                    cell.setMessageObject(cell.getMessageObject(), cell.getCurrentMessagesGroup(), cell.isPinnedBottom(), cell.isPinnedTop());
                    boolean z = !disableSelection;
                    boolean z2 = disableSelection && selected;
                    cell.setCheckPressed(z, z2);
                    z2 = (this.highlightMessageId == Integer.MAX_VALUE || messageObject == null || messageObject.getId() != this.highlightMessageId) ? false : true;
                    cell.setHighlighted(z2);
                    if (this.searchContainer != null && this.searchContainer.getVisibility() == 0) {
                        if (MessagesSearchQuery.isMessageFound(messageObject.getId(), messageObject.getDialogId() == this.mergeDialogId) && MessagesSearchQuery.getLastSearchQuery() != null) {
                            cell.setHighlightedText(MessagesSearchQuery.getLastSearchQuery());
                        }
                    }
                    cell.setHighlightedText(null);
                } else if (view instanceof ChatActionCell) {
                    ChatActionCell cell2 = (ChatActionCell) view;
                    cell2.setMessageObject(cell2.getMessageObject());
                }
            }
            this.chatListView.invalidate();
        }
    }

    private void checkEditTimer() {
        if (this.chatActivityEnterView != null) {
            MessageObject messageObject = this.chatActivityEnterView.getEditingMessageObject();
            if (messageObject == null) {
                return;
            }
            if (this.currentUser == null || !this.currentUser.self) {
                int dt = messageObject.canEditMessageAnytime(this.currentChat) ? 360 : (MessagesController.getInstance().maxEditTime + 300) - Math.abs(ConnectionsManager.getInstance().getCurrentTime() - messageObject.messageOwner.date);
                if (dt > 0) {
                    if (dt <= 300) {
                        if (this.actionModeSubTextView.getVisibility() != 0) {
                            this.actionModeSubTextView.setVisibility(0);
                        }
                        SimpleTextView simpleTextView = this.actionModeSubTextView;
                        Object[] objArr = new Object[1];
                        objArr[0] = String.format("%d:%02d", new Object[]{Integer.valueOf(dt / 60), Integer.valueOf(dt % 60)});
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

    private ArrayList<MessageObject> createVoiceMessagesPlaylist(MessageObject startMessageObject, boolean playingUnreadMedia) {
        ArrayList<MessageObject> messageObjects = new ArrayList();
        messageObjects.add(startMessageObject);
        int messageId = startMessageObject.getId();
        long startDialogId = startMessageObject.getDialogId();
        if (messageId != 0) {
            for (int a = this.messages.size() - 1; a >= 0; a--) {
                MessageObject messageObject = (MessageObject) this.messages.get(a);
                if ((messageObject.getDialogId() != this.mergeDialogId || startMessageObject.getDialogId() == this.mergeDialogId) && (((this.currentEncryptedChat == null && messageObject.getId() > messageId) || (this.currentEncryptedChat != null && messageObject.getId() < messageId)) && ((messageObject.isVoice() || messageObject.isRoundVideo()) && (!playingUnreadMedia || (messageObject.isContentUnread() && !messageObject.isOut()))))) {
                    messageObjects.add(messageObject);
                }
            }
        }
        return messageObjects;
    }

    private void alertUserOpenError(MessageObject message) {
        if (getParentActivity() != null) {
            org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            if (message.type == 3) {
                builder.setMessage(LocaleController.getString("NoPlayerInstalled", R.string.NoPlayerInstalled));
            } else {
                builder.setMessage(LocaleController.formatString("NoHandleAppInstalled", R.string.NoHandleAppInstalled, new Object[]{message.getDocument().mime_type}));
            }
            showDialog(builder.create());
        }
    }

    private void openSearchWithText(String text) {
        this.avatarContainer.setVisibility(8);
        this.headerItem.setVisibility(8);
        this.attachItem.setVisibility(8);
        this.searchItem.setVisibility(0);
        updateSearchButtons(0, 0, -1);
        updateBottomOverlay();
        this.openSearchKeyboard = text == null;
        this.searchItem.openSearch(this.openSearchKeyboard);
        if (text != null) {
            this.searchItem.getSearchField().setText(text);
            this.searchItem.getSearchField().setSelection(this.searchItem.getSearchField().length());
            MessagesSearchQuery.searchMessagesInChat(text, this.dialog_id, this.mergeDialogId, this.classGuid, 0, this.searchingUserMessages);
        }
        updatePinnedMessageView(true);
    }

    public void didSelectLocation(TLRPC$MessageMedia location, int live) {
        SendMessagesHelper.getInstance().sendMessage(location, this.dialog_id, this.replyingMessageObject, null, null);
        moveScrollToLastMessage();
        if (live == 1) {
            showReplyPanel(false, null, null, null, false);
            DraftQuery.cleanDraft(this.dialog_id, true);
        }
        if (this.paused) {
            this.scrollToTopOnResume = true;
        }
    }

    public boolean isSecretChat() {
        return this.currentEncryptedChat != null;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public TLRPC$Chat getCurrentChat() {
        return this.currentChat;
    }

    public boolean allowGroupPhotos() {
        return this.currentEncryptedChat == null || AndroidUtilities.getPeerLayerVersion(this.currentEncryptedChat.layer) >= 73;
    }

    public TLRPC$EncryptedChat getCurrentEncryptedChat() {
        return this.currentEncryptedChat;
    }

    public TLRPC$ChatFull getCurrentChatInfo() {
        return this.info;
    }

    public void sendMedia(MediaController$PhotoEntry photoEntry, VideoEditedInfo videoEditedInfo) {
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

    public void showOpenGameAlert(TLRPC$TL_game game, MessageObject messageObject, String urlStr, boolean ask, int uid) {
        User user = MessagesController.getInstance().getUser(Integer.valueOf(uid));
        if (ask) {
            String name;
            org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            if (user != null) {
                name = ContactsController.formatName(user.first_name, user.last_name);
            } else {
                name = "";
            }
            builder.setMessage(LocaleController.formatString("BotPermissionGameAlert", R.string.BotPermissionGameAlert, new Object[]{name}));
            final TLRPC$TL_game tLRPC$TL_game = game;
            final MessageObject messageObject2 = messageObject;
            final String str = urlStr;
            final int i = uid;
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ChatActivity.this.showOpenGameAlert(tLRPC$TL_game, messageObject2, str, false, i);
                    ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putBoolean("askgame_" + i, false).commit();
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            showDialog(builder.create());
        } else if (VERSION.SDK_INT < 21 || AndroidUtilities.isTablet() || !WebviewActivity.supportWebview()) {
            Activity parentActivity = getParentActivity();
            r2 = game.short_name;
            String str2 = (user == null || user.username == null) ? "" : user.username;
            WebviewActivity.openGameInBrowser(urlStr, messageObject, parentActivity, r2, str2);
        } else if (this.parentLayout.fragmentsStack.get(this.parentLayout.fragmentsStack.size() - 1) == this) {
            r2 = (user == null || TextUtils.isEmpty(user.username)) ? "" : user.username;
            presentFragment(new WebviewActivity(urlStr, r2, game.title, game.short_name, messageObject));
        }
    }

    public void showOpenUrlAlert(final String url, boolean ask) {
        boolean z = true;
        if (Browser.isInternalUrl(url, null) || !ask) {
            Context parentActivity = getParentActivity();
            if (this.inlineReturn != 0) {
                z = false;
            }
            Browser.openUrl(parentActivity, url, z);
            return;
        }
        org.telegram.ui.ActionBar.AlertDialog.Builder builder = new org.telegram.ui.ActionBar.AlertDialog.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
        builder.setMessage(LocaleController.formatString("OpenUrlAlert", R.string.OpenUrlAlert, new Object[]{url}));
        builder.setPositiveButton(LocaleController.getString("Open", R.string.Open), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Browser.openUrl(ChatActivity.this.getParentActivity(), url, ChatActivity.this.inlineReturn == 0);
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
    }

    private void removeMessageObject(MessageObject messageObject) {
        int index = this.messages.indexOf(messageObject);
        if (index != -1) {
            this.messages.remove(index);
            if (this.chatAdapter != null) {
                this.chatAdapter.notifyItemRemoved(this.chatAdapter.messagesStartRow + index);
            }
        }
    }

    private void setCellSelectionBackground(MessageObject message, ChatMessageCell messageCell, int idx) {
        GroupedMessages groupedMessages = getValidGroupedMessage(message);
        if (groupedMessages != null) {
            boolean hasUnselected = false;
            for (int a = 0; a < groupedMessages.messages.size(); a++) {
                if (!this.selectedMessagesIds[idx].containsKey(Integer.valueOf(((MessageObject) groupedMessages.messages.get(a)).getId()))) {
                    hasUnselected = true;
                    break;
                }
            }
            if (!hasUnselected) {
                groupedMessages = null;
            }
        }
        if (groupedMessages == null) {
            messageCell.setBackgroundColor(Theme.getColor(Theme.key_chat_selectedBackground));
        } else {
            messageCell.setBackground(null);
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate selectedBackgroundDelegate = new ThemeDescriptionDelegate() {
            public void didSetColor(int color) {
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
        themeDescriptionArr[81] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, selectedBackgroundDelegate, Theme.key_chat_selectedBackground);
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
        themeDescriptionArr[TransportMediator.KEYCODE_MEDIA_PLAY] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inTimeSelectedText);
        themeDescriptionArr[127] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_adminText);
        themeDescriptionArr[128] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_adminSelectedText);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_AC3] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outTimeSelectedText);
        themeDescriptionArr[130] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioPerfomerText);
        themeDescriptionArr[131] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioPerfomerText);
        themeDescriptionArr[132] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioTitleText);
        themeDescriptionArr[133] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioTitleText);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_SPLICE_INFO] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioDurationText);
        themeDescriptionArr[135] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioDurationText);
        themeDescriptionArr[136] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioDurationSelectedText);
        themeDescriptionArr[137] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioDurationSelectedText);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_DTS] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioSeekbar);
        themeDescriptionArr[139] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioSeekbar);
        themeDescriptionArr[140] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioSeekbarSelected);
        themeDescriptionArr[141] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioSeekbarSelected);
        themeDescriptionArr[142] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioSeekbarFill);
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
        themeDescriptionArr[FetchConst.NETWORK_WIFI] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_locationDrawable[0]}, null, Theme.key_chat_inLocationBackground);
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
        themeDescriptionArr[250] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"seekBarWaveform"}, null, null, null, Theme.key_chat_recordedVoiceProgress);
        themeDescriptionArr[251] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"seekBarWaveform"}, null, null, null, Theme.key_chat_recordedVoiceProgressInner);
        themeDescriptionArr[252] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"playDrawable"}, null, null, null, Theme.key_chat_recordedVoicePlayPause);
        themeDescriptionArr[253] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"pauseDrawable"}, null, null, null, Theme.key_chat_recordedVoicePlayPause);
        themeDescriptionArr[254] = new ThemeDescription(this.chatActivityEnterView, 0, new Class[]{ChatActivityEnterView.class}, new String[]{"dotPaint"}, null, null, null, Theme.key_chat_emojiPanelNewTrending);
        themeDescriptionArr[255] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, new Class[]{ChatActivityEnterView.class}, new String[]{"playDrawable"}, null, null, null, Theme.key_chat_recordedVoicePlayPausePressed);
        themeDescriptionArr[256] = new ThemeDescription(this.chatActivityEnterView, ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, new Class[]{ChatActivityEnterView.class}, new String[]{"pauseDrawable"}, null, null, null, Theme.key_chat_recordedVoicePlayPausePressed);
        themeDescriptionArr[InputDeviceCompat.SOURCE_KEYBOARD] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{""}, null, null, null, Theme.key_chat_emojiPanelBackground);
        themeDescriptionArr[258] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{""}, null, null, null, Theme.key_chat_emojiPanelShadowLine);
        themeDescriptionArr[259] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{""}, null, null, null, Theme.key_chat_emojiPanelEmptyText);
        themeDescriptionArr[260] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{""}, null, null, null, Theme.key_chat_emojiPanelIcon);
        themeDescriptionArr[261] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{""}, null, null, null, Theme.key_chat_emojiPanelIconSelected);
        themeDescriptionArr[262] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{""}, null, null, null, Theme.key_chat_emojiPanelStickerPackSelector);
        themeDescriptionArr[jp.wasabeef.recyclerview.BuildConfig.VERSION_CODE] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{""}, null, null, null, Theme.key_chat_emojiPanelIconSelector);
        themeDescriptionArr[264] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{""}, null, null, null, Theme.key_chat_emojiPanelBackspace);
        themeDescriptionArr[265] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{""}, null, null, null, Theme.key_chat_emojiPanelTrendingTitle);
        themeDescriptionArr[266] = new ThemeDescription(this.chatActivityEnterView.getEmojiView(), 0, new Class[]{EmojiView.class}, new String[]{""}, null, null, null, Theme.key_chat_emojiPanelTrendingDescription);
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
        themeDescriptionArr[300] = new ThemeDescription(this.bottomOverlayChatText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_fieldOverlayText);
        themeDescriptionArr[301] = new ThemeDescription(this.bigEmptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[302] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[303] = new ThemeDescription(this.progressBar, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[304] = new ThemeDescription(this.stickersPanelArrow, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chat_stickersHintPanel);
        themeDescriptionArr[305] = new ThemeDescription(this.stickersListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{StickerCell.class}, null, null, null, Theme.key_chat_stickersHintPanel);
        themeDescriptionArr[306] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE, new Class[]{ChatUnreadCell.class}, new String[]{"backgroundLayout"}, null, null, null, Theme.key_chat_unreadMessagesStartBackground);
        themeDescriptionArr[StatusLine.HTTP_TEMP_REDIRECT] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{ChatUnreadCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_chat_unreadMessagesStartArrowIcon);
        themeDescriptionArr[StatusLine.HTTP_PERM_REDIRECT] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{ChatUnreadCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chat_unreadMessagesStartText);
        themeDescriptionArr[309] = new ThemeDescription(this.progressView2, ThemeDescription.FLAG_SERVICEBACKGROUND, null, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[FetchService.ACTION_ENQUEUE] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_SERVICEBACKGROUND, null, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[FetchService.ACTION_PAUSE] = new ThemeDescription(this.bigEmptyView, ThemeDescription.FLAG_SERVICEBACKGROUND, null, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[FetchService.ACTION_RESUME] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_SERVICEBACKGROUND, new Class[]{ChatLoadingCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[FetchService.ACTION_REMOVE] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_PROGRESSBAR, new Class[]{ChatLoadingCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[FetchService.ACTION_NETWORK] = new ThemeDescription(this.mentionListView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{BotSwitchCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chat_botSwitchToInlineText);
        themeDescriptionArr[FetchService.ACTION_PROCESS_PENDING] = new ThemeDescription(this.mentionListView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{MentionCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[FetchService.ACTION_QUERY] = new ThemeDescription(this.mentionListView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{MentionCell.class}, new String[]{"usernameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[FetchService.ACTION_PRIORITY] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, new Drawable[]{Theme.chat_inlineResultFile, Theme.chat_inlineResultAudio, Theme.chat_inlineResultLocation}, null, Theme.key_chat_inlineResultIcon);
        themeDescriptionArr[FetchService.ACTION_RETRY] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[FetchService.ACTION_REMOVE_ALL] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_windowBackgroundWhiteLinkText);
        themeDescriptionArr[FetchService.ACTION_LOGGING] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[FetchService.ACTION_CONCURRENT_DOWNLOADS_LIMIT] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_chat_inAudioProgress);
        themeDescriptionArr[FetchService.ACTION_UPDATE_REQUEST_URL] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_chat_inAudioSelectedProgress);
        themeDescriptionArr[FetchService.ACTION_ON_UPDATE_INTERVAL] = new ThemeDescription(this.mentionListView, 0, new Class[]{ContextLinkCell.class}, null, null, null, Theme.key_divider);
        themeDescriptionArr[FetchService.ACTION_REMOVE_REQUEST] = new ThemeDescription(this.gifHintTextView, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chat_gifSaveHintBackground);
        themeDescriptionArr[FetchService.ACTION_REMOVE_REQUEST_ALL] = new ThemeDescription(this.gifHintTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_gifSaveHintText);
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

    private void addQuickAccessToUsers(Context context, SizeNotifierFrameLayout contentView) {
        if (!this.isAdvancedForward) {
            final View rootView = LayoutInflater.from(context).inflate(R.layout.quick_access_to_users, contentView, false);
            final android.support.v7.widget.RecyclerView recyclerView = (android.support.v7.widget.RecyclerView) rootView.findViewById(R.id.recycler);
            recyclerView.setLayoutManager(new android.support.v7.widget.LinearLayoutManager(context, 0, false));
            recyclerView.setAdapter(new UserViewAdapter(new OnQuickAccessClickListener() {
                public void onClick(User user, TLRPC$Chat chat) {
                    ChatActivity.this.translateQuickAccess(rootView, recyclerView, false);
                    if (user != null) {
                        Bundle args = new Bundle();
                        args.putInt("user_id", user.id);
                        if (MessagesController.checkCanOpenChat(args, ChatActivity.this)) {
                            ChatActivity.this.presentFragment(new ChatActivity(args), false);
                        }
                    } else if (chat != null) {
                        IntentMaker.goToChannel((long) chat.id, 0, ChatActivity.this.getParentActivity(), chat.username);
                    }
                }
            }));
            rootView.findViewById(R.id.openDialog).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.d("LEE", "Click Quick");
                    ChatActivity.this.translateQuickAccess(rootView, recyclerView, false);
                }
            });
            this.isShowingQuickAccess = false;
            try {
                ((ImageView) rootView.findViewById(R.id.openDialogMark)).setImageResource(this.isShowingQuickAccess ? R.drawable.opmu : R.drawable.opmd);
                rootView.post(new Runnable() {
                    public void run() {
                        try {
                            rootView.setY(rootView.getY() - ((float) recyclerView.getHeight()));
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }
            contentView.addView(rootView);
        }
    }

    private void translateQuickAccess(final View rootView, android.support.v7.widget.RecyclerView recyclerView, boolean fast) {
        long j;
        this.isShowingQuickAccess = !this.isShowingQuickAccess;
        ViewPropertyAnimator animate = rootView.animate();
        if (fast) {
            j = 1;
        } else {
            j = 500;
        }
        animate.setDuration(j).setListener(new AnimatorListener() {
            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                try {
                    ((ImageView) rootView.findViewById(R.id.openDialogMark)).setImageResource(ChatActivity.this.isShowingQuickAccess ? R.drawable.opmu : R.drawable.opmd);
                } catch (Exception e) {
                }
            }

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }
        }).translationYBy(this.isShowingQuickAccess ? (float) recyclerView.getHeight() : (float) (-recyclerView.getHeight()));
    }

    private void openAddMember() {
        boolean z = true;
        Bundle args = new Bundle();
        args.putBoolean("onlyUsers", true);
        args.putBoolean("destroyAfterSelect", true);
        args.putBoolean("returnAsResult", true);
        String str = "needForwardCount";
        if (ChatObject.isChannel(this.currentChat)) {
            z = false;
        }
        args.putBoolean(str, z);
        if (this.chat_id > 0) {
            if (this.currentChat.creator) {
                args.putInt("chat_id", this.currentChat.id);
            }
            args.putString("selectAlertString", LocaleController.getString("AddToTheGroup", R.string.AddToTheGroup));
        }
        ContactsActivity fragment = new ContactsActivity(args);
        fragment.setDelegate(new ContactsActivityDelegate() {
            public void didSelectContact(User user, String param, ContactsActivity activity) {
                MessagesController.getInstance().addUserToChat(ChatActivity.this.chat_id, user, ChatActivity.this.info, param != null ? Utilities.parseInt(param).intValue() : 0, null, ChatActivity.this);
            }
        });
        if (this.info instanceof TLRPC$TL_chatFull) {
            HashMap<Integer, User> users = new HashMap();
            for (int a = 0; a < this.info.participants.participants.size(); a++) {
                users.put(Integer.valueOf(((TLRPC$ChatParticipant) this.info.participants.participants.get(a)).user_id), null);
            }
            fragment.setIgnoreUsers(users);
        }
        presentFragment(fragment);
    }

    public static String getPersianDate(long mDate) {
        Date date = new Date((long) (Double.valueOf((double) mDate).doubleValue() * 1000.0d));
        String day = utils.Utilities.getShamsiDateDay(date);
        String month = utils.Utilities.getShamsiDateMonth(date);
        String year = utils.Utilities.getShamsiDateYear(date);
        String currentYear = utils.Utilities.getShamsiDateYear(new Date());
        String d = "" + day + " " + month;
        if (currentYear.contentEquals(year)) {
            return d;
        }
        return d + " " + year;
    }

    private void addInviteBoxToUser(Context context, SizeNotifierFrameLayout contentView) {
        try {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(Integer.parseInt(this.dialog_id + "")));
            if (!this.isAdvancedForward && user != null) {
                this.inviteRootView = LayoutInflater.from(context).inflate(R.layout.invite_to_user, contentView, false);
                TextView textView = (TextView) this.inviteRootView.findViewById(R.id.tv);
                this.inviteRootView.findViewById(R.id.ll_container).setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
                textView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
                textView.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
                this.inviteRootView.findViewById(R.id.ll_container).setOnClickListener(new View.OnClickListener() {

                    /* renamed from: org.telegram.ui.ChatActivity$130$1 */
                    class C23931 implements OnClickListener {
                        C23931() {
                        }

                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }

                    /* renamed from: org.telegram.ui.ChatActivity$130$2 */
                    class C23942 implements OnClickListener {
                        C23942() {
                        }

                        public void onClick(DialogInterface dialog, int which) {
                            ChatActivity.this.sendMessage(AppPreferences.getInviteMessage(ChatActivity.this.getParentActivity()));
                        }
                    }

                    public void onClick(View view) {
                        new android.app.AlertDialog.Builder(ChatActivity.this.getParentActivity()).setMessage("برای ارسال دعوتنامه مطمین هستید؟").setPositiveButton("بله", new C23942()).setNegativeButton("خیر", new C23931()).setIcon(17301543).show();
                        AppApplication.getDatabaseHandler().updateDialogStatus(ChatActivity.this.dialog_id, true);
                        ChatActivity.this.inviteRootView.setVisibility(8);
                    }
                });
                this.inviteBoxAdded = true;
                contentView.addView(this.inviteRootView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        this.chatActivityEnterView.setFieldText(message);
        this.chatActivityEnterView.getSendButton().performClick();
        this.chatActivityEnterView.setFieldText("");
    }

    private void showLoadingDialog(Context context, String title, String message) {
        if (getParentActivity() == null) {
            return;
        }
        if (this.dialogLoading == null) {
            this.dialogLoading = new ProgressDialog(getParentActivity());
            this.dialogLoading.setTitle(title);
            this.dialogLoading.setMessage(message);
            this.dialogLoading.show();
            return;
        }
        this.dialogLoading.show();
    }
}
