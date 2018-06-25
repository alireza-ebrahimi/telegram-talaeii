package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.LinearSmoothScrollerMiddle;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.SmoothScroller;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetEmpty;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetShortName;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_replyInlineMarkup;
import org.telegram.tgnet.TLRPC.ChannelParticipant;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.KeyboardButton;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEvent;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionChangeStickerSet;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventsFilter;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsAdmins;
import org.telegram.tgnet.TLRPC.TL_channels_adminLogResults;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC.TL_channels_getAdminLog;
import org.telegram.tgnet.TLRPC.TL_channels_getParticipants;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet.Builder;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.BotHelpCell;
import org.telegram.ui.Cells.BotHelpCell.BotHelpCellDelegate;
import org.telegram.ui.Cells.ChatActionCell;
import org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate;
import org.telegram.ui.Cells.ChatLoadingCell;
import org.telegram.ui.Cells.ChatMessageCell;
import org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate;
import org.telegram.ui.Cells.ChatUnreadCell;
import org.telegram.ui.Components.AdminLogFilterAlert;
import org.telegram.ui.Components.AdminLogFilterAlert.AdminLogFilterAlertDelegate;
import org.telegram.ui.Components.ChatAvatarContainer;
import org.telegram.ui.Components.EmbedBottomSheet;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.PipRoundVideoView;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.ShareAlert;
import org.telegram.ui.Components.SizeNotifierFrameLayout;
import org.telegram.ui.Components.StickersAlert;
import org.telegram.ui.Components.URLSpanMono;
import org.telegram.ui.Components.URLSpanNoUnderline;
import org.telegram.ui.Components.URLSpanReplacement;
import org.telegram.ui.Components.URLSpanUserMention;
import org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PlaceProviderObject;

public class ChannelAdminLogActivity extends BaseFragment implements NotificationCenterDelegate {
    private ArrayList<ChannelParticipant> admins;
    private Paint aspectPaint;
    private Path aspectPath;
    private AspectRatioFrameLayout aspectRatioFrameLayout;
    private ChatAvatarContainer avatarContainer;
    private FrameLayout bottomOverlayChat;
    private TextView bottomOverlayChatText;
    private ImageView bottomOverlayImage;
    private ChatActivityAdapter chatAdapter;
    private LinearLayoutManager chatLayoutManager;
    private RecyclerListView chatListView;
    private ArrayList<ChatMessageCell> chatMessageCellsCache = new ArrayList();
    private boolean checkTextureViewPosition;
    private SizeNotifierFrameLayout contentView;
    protected Chat currentChat;
    private TL_channelAdminLogEventsFilter currentFilter = null;
    private boolean currentFloatingDateOnScreen;
    private boolean currentFloatingTopIsNotMessage;
    private long dialog_id;
    private TextView emptyView;
    private FrameLayout emptyViewContainer;
    private boolean endReached;
    private AnimatorSet floatingDateAnimation;
    private ChatActionCell floatingDateView;
    private boolean loading;
    private int loadsCount;
    protected ArrayList<MessageObject> messages = new ArrayList();
    private HashMap<String, ArrayList<MessageObject>> messagesByDays = new HashMap();
    private HashMap<Long, MessageObject> messagesDict = new HashMap();
    private int[] mid = new int[]{2};
    private int minDate;
    private long minEventId;
    private boolean openAnimationEnded;
    private boolean paused = true;
    private RadialProgressView progressBar;
    private FrameLayout progressView;
    private View progressView2;
    private PhotoViewerProvider provider = new C41021();
    private FrameLayout roundVideoContainer;
    private MessageObject scrollToMessage;
    private int scrollToOffsetOnRecreate = 0;
    private int scrollToPositionOnRecreate = -1;
    private boolean scrollingFloatingDate;
    private ImageView searchCalendarButton;
    private FrameLayout searchContainer;
    private SimpleTextView searchCountText;
    private ImageView searchDownButton;
    private ActionBarMenuItem searchItem;
    private String searchQuery = TtmlNode.ANONYMOUS_REGION_ID;
    private ImageView searchUpButton;
    private boolean searchWas;
    private HashMap<Integer, User> selectedAdmins;
    private MessageObject selectedObject;
    private TextureView videoTextureView;
    private boolean wasPaused = false;

    /* renamed from: org.telegram.ui.ChannelAdminLogActivity$1 */
    class C41021 extends EmptyPhotoViewerProvider {
        C41021() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            int childCount = ChannelAdminLogActivity.this.chatListView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                ImageReceiver imageReceiver = null;
                View childAt = ChannelAdminLogActivity.this.chatListView.getChildAt(i2);
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
                    placeProviderObject.parentView = ChannelAdminLogActivity.this.chatListView;
                    placeProviderObject.imageReceiver = imageReceiver;
                    placeProviderObject.thumb = imageReceiver.getBitmap();
                    placeProviderObject.radius = imageReceiver.getRoundRadius();
                    placeProviderObject.isEvent = true;
                    return placeProviderObject;
                }
            }
            return null;
        }
    }

    /* renamed from: org.telegram.ui.ChannelAdminLogActivity$2 */
    class C41052 implements RequestDelegate {
        C41052() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLObject != null) {
                final TL_channels_adminLogResults tL_channels_adminLogResults = (TL_channels_adminLogResults) tLObject;
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        int i = 1;
                        MessagesController.getInstance().putUsers(tL_channels_adminLogResults.users, false);
                        MessagesController.getInstance().putChats(tL_channels_adminLogResults.chats, false);
                        int size = ChannelAdminLogActivity.this.messages.size();
                        int i2 = 0;
                        boolean z = false;
                        while (i2 < tL_channels_adminLogResults.events.size()) {
                            boolean z2;
                            TL_channelAdminLogEvent tL_channelAdminLogEvent = (TL_channelAdminLogEvent) tL_channels_adminLogResults.events.get(i2);
                            if (ChannelAdminLogActivity.this.messagesDict.containsKey(Long.valueOf(tL_channelAdminLogEvent.id))) {
                                z2 = z;
                            } else {
                                ChannelAdminLogActivity.this.minEventId = Math.min(ChannelAdminLogActivity.this.minEventId, tL_channelAdminLogEvent.id);
                                MessageObject messageObject = new MessageObject(tL_channelAdminLogEvent, ChannelAdminLogActivity.this.messages, ChannelAdminLogActivity.this.messagesByDays, ChannelAdminLogActivity.this.currentChat, ChannelAdminLogActivity.this.mid);
                                if (messageObject.contentType < 0) {
                                    z2 = true;
                                } else {
                                    ChannelAdminLogActivity.this.messagesDict.put(Long.valueOf(tL_channelAdminLogEvent.id), messageObject);
                                    z2 = true;
                                }
                            }
                            i2++;
                            z = z2;
                        }
                        int size2 = ChannelAdminLogActivity.this.messages.size() - size;
                        ChannelAdminLogActivity.this.loading = false;
                        if (!z) {
                            ChannelAdminLogActivity.this.endReached = true;
                        }
                        ChannelAdminLogActivity.this.progressView.setVisibility(4);
                        ChannelAdminLogActivity.this.chatListView.setEmptyView(ChannelAdminLogActivity.this.emptyViewContainer);
                        if (size2 != 0) {
                            int i3;
                            if (ChannelAdminLogActivity.this.endReached) {
                                ChannelAdminLogActivity.this.chatAdapter.notifyItemRangeChanged(0, 2);
                                i3 = 1;
                            } else {
                                z = false;
                            }
                            int findLastVisibleItemPosition = ChannelAdminLogActivity.this.chatLayoutManager.findLastVisibleItemPosition();
                            View findViewByPosition = ChannelAdminLogActivity.this.chatLayoutManager.findViewByPosition(findLastVisibleItemPosition);
                            int top = (findViewByPosition == null ? 0 : findViewByPosition.getTop()) - ChannelAdminLogActivity.this.chatListView.getPaddingTop();
                            if (size2 - (i3 != 0 ? 1 : 0) > 0) {
                                int i4 = (i3 != 0 ? 0 : 1) + 1;
                                ChannelAdminLogActivity.this.chatAdapter.notifyItemChanged(i4);
                                ChannelAdminLogActivity.this.chatAdapter.notifyItemRangeInserted(i4, size2 - (i3 != 0 ? 1 : 0));
                            }
                            if (findLastVisibleItemPosition != -1) {
                                LinearLayoutManager access$1000 = ChannelAdminLogActivity.this.chatLayoutManager;
                                size2 += findLastVisibleItemPosition;
                                if (i3 == 0) {
                                    i = 0;
                                }
                                access$1000.scrollToPositionWithOffset(size2 - i, top);
                            }
                        } else if (ChannelAdminLogActivity.this.endReached) {
                            ChannelAdminLogActivity.this.chatAdapter.notifyItemRemoved(0);
                        }
                    }
                });
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelAdminLogActivity$3 */
    class C41063 extends ActionBarMenuOnItemClick {
        C41063() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ChannelAdminLogActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelAdminLogActivity$4 */
    class C41074 extends ActionBarMenuItemSearchListener {
        C41074() {
        }

        public void onSearchCollapse() {
            ChannelAdminLogActivity.this.searchQuery = TtmlNode.ANONYMOUS_REGION_ID;
            ChannelAdminLogActivity.this.avatarContainer.setVisibility(0);
            if (ChannelAdminLogActivity.this.searchWas) {
                ChannelAdminLogActivity.this.searchWas = false;
                ChannelAdminLogActivity.this.loadMessages(true);
            }
            ChannelAdminLogActivity.this.updateBottomOverlay();
        }

        public void onSearchExpand() {
            ChannelAdminLogActivity.this.avatarContainer.setVisibility(8);
            ChannelAdminLogActivity.this.updateBottomOverlay();
        }

        public void onSearchPressed(EditText editText) {
            ChannelAdminLogActivity.this.searchWas = true;
            ChannelAdminLogActivity.this.searchQuery = editText.getText().toString();
            ChannelAdminLogActivity.this.loadMessages(true);
        }
    }

    /* renamed from: org.telegram.ui.ChannelAdminLogActivity$6 */
    class C41096 implements OnTouchListener {
        C41096() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChannelAdminLogActivity$8 */
    class C41118 implements OnItemClickListener {
        C41118() {
        }

        public void onItemClick(View view, int i) {
            ChannelAdminLogActivity.this.createMenu(view);
        }
    }

    public class ChatActivityAdapter extends Adapter {
        private int loadingUpRow;
        private Context mContext;
        private int messagesEndRow;
        private int messagesStartRow;
        private int rowCount;

        /* renamed from: org.telegram.ui.ChannelAdminLogActivity$ChatActivityAdapter$1 */
        class C41141 implements ChatMessageCellDelegate {
            C41141() {
            }

            public boolean canPerformActions() {
                return true;
            }

            public void didLongPressed(ChatMessageCell chatMessageCell) {
                ChannelAdminLogActivity.this.createMenu(chatMessageCell);
            }

            public void didPressedBotButton(ChatMessageCell chatMessageCell, KeyboardButton keyboardButton) {
            }

            public void didPressedCancelSendButton(ChatMessageCell chatMessageCell) {
            }

            public void didPressedChannelAvatar(ChatMessageCell chatMessageCell, Chat chat, int i) {
                if (chat != null && chat != ChannelAdminLogActivity.this.currentChat) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("chat_id", chat.id);
                    if (i != 0) {
                        bundle.putInt("message_id", i);
                    }
                    if (MessagesController.checkCanOpenChat(bundle, ChannelAdminLogActivity.this)) {
                        ChannelAdminLogActivity.this.presentFragment(new ChatActivity(bundle), true);
                    }
                }
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void didPressedImage(org.telegram.ui.Cells.ChatMessageCell r11) {
                /*
                r10 = this;
                r8 = 0;
                r6 = 1;
                r3 = -1;
                r4 = 0;
                r5 = r11.getMessageObject();
                r0 = r5.type;
                r1 = 13;
                if (r0 != r1) goto L_0x002d;
            L_0x000f:
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r6 = org.telegram.ui.ChannelAdminLogActivity.this;
                r0 = new org.telegram.ui.Components.StickersAlert;
                r1 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChannelAdminLogActivity.this;
                r1 = r1.getParentActivity();
                r2 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChannelAdminLogActivity.this;
                r3 = r5.getInputStickerSet();
                r5 = r4;
                r0.<init>(r1, r2, r3, r4, r5);
                r6.showDialog(r0);
            L_0x002c:
                return;
            L_0x002d:
                r0 = r5.isVideo();
                if (r0 != 0) goto L_0x0047;
            L_0x0033:
                r0 = r5.type;
                if (r0 == r6) goto L_0x0047;
            L_0x0037:
                r0 = r5.type;
                if (r0 != 0) goto L_0x0041;
            L_0x003b:
                r0 = r5.isWebpageDocument();
                if (r0 == 0) goto L_0x0047;
            L_0x0041:
                r0 = r5.isGif();
                if (r0 == 0) goto L_0x0076;
            L_0x0047:
                r0 = org.telegram.ui.PhotoViewer.getInstance();
                r1 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChannelAdminLogActivity.this;
                r1 = r1.getParentActivity();
                r0.setParentActivity(r1);
                r0 = org.telegram.ui.PhotoViewer.getInstance();
                r1 = r5.type;
                if (r1 == 0) goto L_0x0074;
            L_0x005e:
                r1 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChannelAdminLogActivity.this;
                r2 = r1.dialog_id;
            L_0x0066:
                r1 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChannelAdminLogActivity.this;
                r6 = r1.provider;
                r1 = r5;
                r4 = r8;
                r0.openPhoto(r1, r2, r4, r6);
                goto L_0x002c;
            L_0x0074:
                r2 = r8;
                goto L_0x0066;
            L_0x0076:
                r0 = r5.type;
                r1 = 3;
                if (r0 != r1) goto L_0x00ee;
            L_0x007b:
                r0 = r5.messageOwner;	 Catch:{ Exception -> 0x00d9 }
                r0 = r0.attachPath;	 Catch:{ Exception -> 0x00d9 }
                if (r0 == 0) goto L_0x01fb;
            L_0x0081:
                r0 = r5.messageOwner;	 Catch:{ Exception -> 0x00d9 }
                r0 = r0.attachPath;	 Catch:{ Exception -> 0x00d9 }
                r0 = r0.length();	 Catch:{ Exception -> 0x00d9 }
                if (r0 == 0) goto L_0x01fb;
            L_0x008b:
                r4 = new java.io.File;	 Catch:{ Exception -> 0x00d9 }
                r0 = r5.messageOwner;	 Catch:{ Exception -> 0x00d9 }
                r0 = r0.attachPath;	 Catch:{ Exception -> 0x00d9 }
                r4.<init>(r0);	 Catch:{ Exception -> 0x00d9 }
                r0 = r4;
            L_0x0095:
                if (r0 == 0) goto L_0x009d;
            L_0x0097:
                r1 = r0.exists();	 Catch:{ Exception -> 0x00d9 }
                if (r1 != 0) goto L_0x00a3;
            L_0x009d:
                r0 = r5.messageOwner;	 Catch:{ Exception -> 0x00d9 }
                r0 = org.telegram.messenger.FileLoader.getPathToMessage(r0);	 Catch:{ Exception -> 0x00d9 }
            L_0x00a3:
                r1 = new android.content.Intent;	 Catch:{ Exception -> 0x00d9 }
                r2 = "android.intent.action.VIEW";
                r1.<init>(r2);	 Catch:{ Exception -> 0x00d9 }
                r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x00d9 }
                r3 = 24;
                if (r2 < r3) goto L_0x00e3;
            L_0x00b1:
                r2 = 1;
                r1.setFlags(r2);	 Catch:{ Exception -> 0x00d9 }
                r2 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;	 Catch:{ Exception -> 0x00d9 }
                r2 = org.telegram.ui.ChannelAdminLogActivity.this;	 Catch:{ Exception -> 0x00d9 }
                r2 = r2.getParentActivity();	 Catch:{ Exception -> 0x00d9 }
                r3 = "org.ir.talaeii.provider";
                r0 = android.support.v4.content.FileProvider.a(r2, r3, r0);	 Catch:{ Exception -> 0x00d9 }
                r2 = "video/mp4";
                r1.setDataAndType(r0, r2);	 Catch:{ Exception -> 0x00d9 }
            L_0x00ca:
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;	 Catch:{ Exception -> 0x00d9 }
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;	 Catch:{ Exception -> 0x00d9 }
                r0 = r0.getParentActivity();	 Catch:{ Exception -> 0x00d9 }
                r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
                r0.startActivityForResult(r1, r2);	 Catch:{ Exception -> 0x00d9 }
                goto L_0x002c;
            L_0x00d9:
                r0 = move-exception;
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;
                r0.alertUserOpenError(r5);
                goto L_0x002c;
            L_0x00e3:
                r0 = android.net.Uri.fromFile(r0);	 Catch:{ Exception -> 0x00d9 }
                r2 = "video/mp4";
                r1.setDataAndType(r0, r2);	 Catch:{ Exception -> 0x00d9 }
                goto L_0x00ca;
            L_0x00ee:
                r0 = r5.type;
                r1 = 4;
                if (r0 != r1) goto L_0x010f;
            L_0x00f3:
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;
                r0 = org.telegram.messenger.AndroidUtilities.isGoogleMapsInstalled(r0);
                if (r0 == 0) goto L_0x002c;
            L_0x00fd:
                r0 = new org.telegram.ui.LocationActivity;
                r1 = 0;
                r0.<init>(r1);
                r0.setMessageObject(r5);
                r1 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r1 = org.telegram.ui.ChannelAdminLogActivity.this;
                r1.presentFragment(r0);
                goto L_0x002c;
            L_0x010f:
                r0 = r5.type;
                r1 = 9;
                if (r0 == r1) goto L_0x0119;
            L_0x0115:
                r0 = r5.type;
                if (r0 != 0) goto L_0x002c;
            L_0x0119:
                r0 = r5.getDocumentName();
                r1 = "attheme";
                r0 = r0.endsWith(r1);
                if (r0 == 0) goto L_0x01e1;
            L_0x0126:
                r0 = r5.messageOwner;
                r0 = r0.attachPath;
                if (r0 == 0) goto L_0x01f8;
            L_0x012c:
                r0 = r5.messageOwner;
                r0 = r0.attachPath;
                r0 = r0.length();
                if (r0 == 0) goto L_0x01f8;
            L_0x0136:
                r1 = new java.io.File;
                r0 = r5.messageOwner;
                r0 = r0.attachPath;
                r1.<init>(r0);
                r0 = r1.exists();
                if (r0 == 0) goto L_0x01f8;
            L_0x0145:
                if (r1 != 0) goto L_0x0154;
            L_0x0147:
                r0 = r5.messageOwner;
                r0 = org.telegram.messenger.FileLoader.getPathToMessage(r0);
                r2 = r0.exists();
                if (r2 == 0) goto L_0x0154;
            L_0x0153:
                r1 = r0;
            L_0x0154:
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;
                r0 = r0.chatLayoutManager;
                if (r0 == 0) goto L_0x01b2;
            L_0x015e:
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;
                r0 = r0.chatLayoutManager;
                r0 = r0.findLastVisibleItemPosition();
                r2 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChannelAdminLogActivity.this;
                r2 = r2.chatLayoutManager;
                r2 = r2.getItemCount();
                r2 = r2 + -1;
                if (r0 >= r2) goto L_0x01d2;
            L_0x017a:
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;
                r2 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChannelAdminLogActivity.this;
                r2 = r2.chatLayoutManager;
                r2 = r2.findFirstVisibleItemPosition();
                r0.scrollToPositionOnRecreate = r2;
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;
                r0 = r0.chatListView;
                r2 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChannelAdminLogActivity.this;
                r2 = r2.scrollToPositionOnRecreate;
                r0 = r0.findViewHolderForAdapterPosition(r2);
                r0 = (org.telegram.ui.Components.RecyclerListView.Holder) r0;
                if (r0 == 0) goto L_0x01ca;
            L_0x01a5:
                r2 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChannelAdminLogActivity.this;
                r0 = r0.itemView;
                r0 = r0.getTop();
                r2.scrollToOffsetOnRecreate = r0;
            L_0x01b2:
                r0 = r5.getDocumentName();
                r0 = org.telegram.ui.ActionBar.Theme.applyThemeFile(r1, r0, r6);
                if (r0 == 0) goto L_0x01da;
            L_0x01bc:
                r2 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r2 = org.telegram.ui.ChannelAdminLogActivity.this;
                r3 = new org.telegram.ui.ThemePreviewActivity;
                r3.<init>(r1, r0);
                r2.presentFragment(r3);
                goto L_0x002c;
            L_0x01ca:
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;
                r0.scrollToPositionOnRecreate = r3;
                goto L_0x01b2;
            L_0x01d2:
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;
                r0.scrollToPositionOnRecreate = r3;
                goto L_0x01b2;
            L_0x01da:
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;
                r0.scrollToPositionOnRecreate = r3;
            L_0x01e1:
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;	 Catch:{ Exception -> 0x01ee }
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;	 Catch:{ Exception -> 0x01ee }
                r0 = r0.getParentActivity();	 Catch:{ Exception -> 0x01ee }
                org.telegram.messenger.AndroidUtilities.openForView(r5, r0);	 Catch:{ Exception -> 0x01ee }
                goto L_0x002c;
            L_0x01ee:
                r0 = move-exception;
                r0 = org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.this;
                r0 = org.telegram.ui.ChannelAdminLogActivity.this;
                r0.alertUserOpenError(r5);
                goto L_0x002c;
            L_0x01f8:
                r1 = r4;
                goto L_0x0145;
            L_0x01fb:
                r0 = r4;
                goto L_0x0095;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.ChannelAdminLogActivity.ChatActivityAdapter.1.didPressedImage(org.telegram.ui.Cells.ChatMessageCell):void");
            }

            public void didPressedInstantButton(ChatMessageCell chatMessageCell, int i) {
                MessageObject messageObject = chatMessageCell.getMessageObject();
                if (i != 0) {
                    Browser.openUrl(ChannelAdminLogActivity.this.getParentActivity(), messageObject.messageOwner.media.webpage.url);
                } else if (messageObject.messageOwner.media != null && messageObject.messageOwner.media.webpage != null && messageObject.messageOwner.media.webpage.cached_page != null) {
                    ArticleViewer.getInstance().setParentActivity(ChannelAdminLogActivity.this.getParentActivity(), ChannelAdminLogActivity.this);
                    ArticleViewer.getInstance().open(messageObject);
                }
            }

            public void didPressedOther(ChatMessageCell chatMessageCell) {
                ChannelAdminLogActivity.this.createMenu(chatMessageCell);
            }

            public void didPressedReplyMessage(ChatMessageCell chatMessageCell, int i) {
            }

            public void didPressedShare(ChatMessageCell chatMessageCell) {
                if (ChannelAdminLogActivity.this.getParentActivity() != null) {
                    ChannelAdminLogActivity channelAdminLogActivity = ChannelAdminLogActivity.this;
                    Context access$4200 = ChatActivityAdapter.this.mContext;
                    MessageObject messageObject = chatMessageCell.getMessageObject();
                    boolean z = ChatObject.isChannel(ChannelAdminLogActivity.this.currentChat) && !ChannelAdminLogActivity.this.currentChat.megagroup && ChannelAdminLogActivity.this.currentChat.username != null && ChannelAdminLogActivity.this.currentChat.username.length() > 0;
                    channelAdminLogActivity.showDialog(ShareAlert.createShareAlert(access$4200, messageObject, null, z, null, false));
                }
            }

            public void didPressedUrl(MessageObject messageObject, CharacterStyle characterStyle, boolean z) {
                if (characterStyle != null) {
                    if (characterStyle instanceof URLSpanMono) {
                        ((URLSpanMono) characterStyle).copyToClipboard();
                        Toast.makeText(ChannelAdminLogActivity.this.getParentActivity(), LocaleController.getString("TextCopied", R.string.TextCopied), 0).show();
                    } else if (characterStyle instanceof URLSpanUserMention) {
                        User user = MessagesController.getInstance().getUser(Utilities.parseInt(((URLSpanUserMention) characterStyle).getURL()));
                        if (user != null) {
                            MessagesController.openChatOrProfileWith(user, null, ChannelAdminLogActivity.this, 0, false);
                        }
                    } else if (characterStyle instanceof URLSpanNoUnderline) {
                        r0 = ((URLSpanNoUnderline) characterStyle).getURL();
                        if (r0.startsWith("@")) {
                            MessagesController.openByUserName(r0.substring(1), ChannelAdminLogActivity.this, 0);
                        } else if (r0.startsWith("#")) {
                            BaseFragment dialogsActivity = new DialogsActivity(null);
                            dialogsActivity.setSearchString(r0);
                            ChannelAdminLogActivity.this.presentFragment(dialogsActivity);
                        }
                    } else {
                        r0 = ((URLSpan) characterStyle).getURL();
                        if (z) {
                            Builder builder = new Builder(ChannelAdminLogActivity.this.getParentActivity());
                            builder.setTitle(r0);
                            builder.setItems(new CharSequence[]{LocaleController.getString("Open", R.string.Open), LocaleController.getString("Copy", R.string.Copy)}, new OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i == 0) {
                                        Browser.openUrl(ChannelAdminLogActivity.this.getParentActivity(), r0, true);
                                    } else if (i == 1) {
                                        CharSequence charSequence = r0;
                                        if (charSequence.startsWith("mailto:")) {
                                            charSequence = charSequence.substring(7);
                                        } else if (charSequence.startsWith("tel:")) {
                                            charSequence = charSequence.substring(4);
                                        }
                                        AndroidUtilities.addToClipboard(charSequence);
                                    }
                                }
                            });
                            ChannelAdminLogActivity.this.showDialog(builder.create());
                        } else if (characterStyle instanceof URLSpanReplacement) {
                            ChannelAdminLogActivity.this.showOpenUrlAlert(((URLSpanReplacement) characterStyle).getURL(), true);
                        } else if (characterStyle instanceof URLSpan) {
                            if (!(!(messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) || messageObject.messageOwner.media.webpage == null || messageObject.messageOwner.media.webpage.cached_page == null)) {
                                CharSequence toLowerCase = r0.toLowerCase();
                                Object toLowerCase2 = messageObject.messageOwner.media.webpage.url.toLowerCase();
                                if ((toLowerCase.contains("telegra.ph") || toLowerCase.contains("t.me/iv")) && (toLowerCase.contains(toLowerCase2) || toLowerCase2.contains(toLowerCase))) {
                                    ArticleViewer.getInstance().setParentActivity(ChannelAdminLogActivity.this.getParentActivity(), ChannelAdminLogActivity.this);
                                    ArticleViewer.getInstance().open(messageObject);
                                    return;
                                }
                            }
                            Browser.openUrl(ChannelAdminLogActivity.this.getParentActivity(), r0, true);
                        } else if (characterStyle instanceof ClickableSpan) {
                            ((ClickableSpan) characterStyle).onClick(ChannelAdminLogActivity.this.fragmentView);
                        }
                    }
                }
            }

            public void didPressedUserAvatar(ChatMessageCell chatMessageCell, User user) {
                if (user != null && user.id != UserConfig.getClientUserId()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("user_id", user.id);
                    ChannelAdminLogActivity.this.addCanBanUser(bundle, user.id);
                    BaseFragment profileActivity = new ProfileActivity(bundle);
                    profileActivity.setPlayProfileAnimation(false);
                    ChannelAdminLogActivity.this.presentFragment(profileActivity);
                }
            }

            public void didPressedViaBot(ChatMessageCell chatMessageCell, String str) {
            }

            public void didTagPressed(String str) {
            }

            public boolean isChatAdminCell(int i) {
                return false;
            }

            public void needOpenWebView(String str, String str2, String str3, String str4, int i, int i2) {
                EmbedBottomSheet.show(ChatActivityAdapter.this.mContext, str2, str3, str4, str, i, i2);
            }

            public boolean needPlayMessage(MessageObject messageObject) {
                if (!messageObject.isVoice() && !messageObject.isRoundVideo()) {
                    return messageObject.isMusic() ? MediaController.getInstance().setPlaylist(ChannelAdminLogActivity.this.messages, messageObject) : false;
                } else {
                    boolean playMessage = MediaController.getInstance().playMessage(messageObject);
                    MediaController.getInstance().setVoiceMessagesPlaylist(null, false);
                    return playMessage;
                }
            }
        }

        /* renamed from: org.telegram.ui.ChannelAdminLogActivity$ChatActivityAdapter$2 */
        class C41152 implements ChatActionCellDelegate {
            C41152() {
            }

            public void didClickedImage(ChatActionCell chatActionCell) {
                MessageObject messageObject = chatActionCell.getMessageObject();
                PhotoViewer.getInstance().setParentActivity(ChannelAdminLogActivity.this.getParentActivity());
                PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 640);
                if (closestPhotoSizeWithSize != null) {
                    PhotoViewer.getInstance().openPhoto(closestPhotoSizeWithSize.location, ChannelAdminLogActivity.this.provider);
                    return;
                }
                PhotoViewer.getInstance().openPhoto(messageObject, 0, 0, ChannelAdminLogActivity.this.provider);
            }

            public void didLongPressed(ChatActionCell chatActionCell) {
                ChannelAdminLogActivity.this.createMenu(chatActionCell);
            }

            public void didPressedBotButton(MessageObject messageObject, KeyboardButton keyboardButton) {
            }

            public void didPressedReplyMessage(ChatActionCell chatActionCell, int i) {
            }

            public void needOpenUserProfile(int i) {
                Bundle bundle;
                if (i < 0) {
                    bundle = new Bundle();
                    bundle.putInt("chat_id", -i);
                    if (MessagesController.checkCanOpenChat(bundle, ChannelAdminLogActivity.this)) {
                        ChannelAdminLogActivity.this.presentFragment(new ChatActivity(bundle), true);
                    }
                } else if (i != UserConfig.getClientUserId()) {
                    bundle = new Bundle();
                    bundle.putInt("user_id", i);
                    ChannelAdminLogActivity.this.addCanBanUser(bundle, i);
                    BaseFragment profileActivity = new ProfileActivity(bundle);
                    profileActivity.setPlayProfileAnimation(false);
                    ChannelAdminLogActivity.this.presentFragment(profileActivity);
                }
            }
        }

        /* renamed from: org.telegram.ui.ChannelAdminLogActivity$ChatActivityAdapter$3 */
        class C41163 implements BotHelpCellDelegate {
            C41163() {
            }

            public void didPressUrl(String str) {
                if (str.startsWith("@")) {
                    MessagesController.openByUserName(str.substring(1), ChannelAdminLogActivity.this, 0);
                } else if (str.startsWith("#")) {
                    BaseFragment dialogsActivity = new DialogsActivity(null);
                    dialogsActivity.setSearchString(str);
                    ChannelAdminLogActivity.this.presentFragment(dialogsActivity);
                }
            }
        }

        public ChatActivityAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return this.rowCount;
        }

        public long getItemId(int i) {
            return -1;
        }

        public int getItemViewType(int i) {
            return (i < this.messagesStartRow || i >= this.messagesEndRow) ? 4 : ((MessageObject) ChannelAdminLogActivity.this.messages.get((ChannelAdminLogActivity.this.messages.size() - (i - this.messagesStartRow)) - 1)).contentType;
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
            updateRows();
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
            updateRows();
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
            boolean z = true;
            if (i == this.loadingUpRow) {
                ((ChatLoadingCell) viewHolder.itemView).setProgressVisible(ChannelAdminLogActivity.this.loadsCount > 1);
            } else if (i >= this.messagesStartRow && i < this.messagesEndRow) {
                MessageObject messageObject = (MessageObject) ChannelAdminLogActivity.this.messages.get((ChannelAdminLogActivity.this.messages.size() - (i - this.messagesStartRow)) - 1);
                View view = viewHolder.itemView;
                if (view instanceof ChatMessageCell) {
                    boolean z2;
                    MessageObject messageObject2;
                    ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                    chatMessageCell.isChat = true;
                    int itemViewType = getItemViewType(i + 1);
                    int itemViewType2 = getItemViewType(i - 1);
                    if ((messageObject.messageOwner.reply_markup instanceof TLRPC$TL_replyInlineMarkup) || itemViewType != viewHolder.getItemViewType()) {
                        z2 = false;
                    } else {
                        messageObject2 = (MessageObject) ChannelAdminLogActivity.this.messages.get((ChannelAdminLogActivity.this.messages.size() - ((i + 1) - this.messagesStartRow)) - 1);
                        boolean z3 = messageObject2.isOutOwner() == messageObject.isOutOwner() && messageObject2.messageOwner.from_id == messageObject.messageOwner.from_id && Math.abs(messageObject2.messageOwner.date - messageObject.messageOwner.date) <= 300;
                        z2 = z3;
                    }
                    if (itemViewType2 == viewHolder.getItemViewType()) {
                        messageObject2 = (MessageObject) ChannelAdminLogActivity.this.messages.get(ChannelAdminLogActivity.this.messages.size() - (i - this.messagesStartRow));
                        if ((messageObject2.messageOwner.reply_markup instanceof TLRPC$TL_replyInlineMarkup) || messageObject2.isOutOwner() != messageObject.isOutOwner() || messageObject2.messageOwner.from_id != messageObject.messageOwner.from_id || Math.abs(messageObject2.messageOwner.date - messageObject.messageOwner.date) > 300) {
                            z = false;
                        }
                    } else {
                        z = false;
                    }
                    chatMessageCell.setMessageObject(messageObject, null, z2, z);
                    if ((view instanceof ChatMessageCell) && MediaController.getInstance().canDownloadMedia(messageObject)) {
                        ((ChatMessageCell) view).downloadAudioIfNeed();
                    }
                    chatMessageCell.setHighlighted(false);
                    chatMessageCell.setHighlightedText(null);
                } else if (view instanceof ChatActionCell) {
                    ChatActionCell chatActionCell = (ChatActionCell) view;
                    chatActionCell.setMessageObject(messageObject);
                    chatActionCell.setAlpha(1.0f);
                }
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            if (i == 0) {
                if (ChannelAdminLogActivity.this.chatMessageCellsCache.isEmpty()) {
                    view = new ChatMessageCell(this.mContext);
                } else {
                    View view2 = (View) ChannelAdminLogActivity.this.chatMessageCellsCache.get(0);
                    ChannelAdminLogActivity.this.chatMessageCellsCache.remove(0);
                    view = view2;
                }
                ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                chatMessageCell.setDelegate(new C41141());
                chatMessageCell.setAllowAssistant(true);
            } else if (i == 1) {
                view = new ChatActionCell(this.mContext);
                ((ChatActionCell) view).setDelegate(new C41152());
            } else if (i == 2) {
                view = new ChatUnreadCell(this.mContext);
            } else if (i == 3) {
                view = new BotHelpCell(this.mContext);
                ((BotHelpCell) view).setDelegate(new C41163());
            } else if (i == 4) {
                view = new ChatLoadingCell(this.mContext);
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }

        public void onViewAttachedToWindow(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ChatMessageCell) {
                final ChatMessageCell chatMessageCell = (ChatMessageCell) viewHolder.itemView;
                chatMessageCell.getMessageObject();
                chatMessageCell.setBackgroundDrawable(null);
                chatMessageCell.setCheckPressed(true, false);
                chatMessageCell.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                    public boolean onPreDraw() {
                        chatMessageCell.getViewTreeObserver().removeOnPreDrawListener(this);
                        int measuredHeight = ChannelAdminLogActivity.this.chatListView.getMeasuredHeight();
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
                chatMessageCell.setHighlighted(false);
            }
        }

        public void updateRowWithMessageObject(MessageObject messageObject) {
            int indexOf = ChannelAdminLogActivity.this.messages.indexOf(messageObject);
            if (indexOf != -1) {
                notifyItemChanged(((this.messagesStartRow + ChannelAdminLogActivity.this.messages.size()) - indexOf) - 1);
            }
        }

        public void updateRows() {
            this.rowCount = 0;
            if (ChannelAdminLogActivity.this.messages.isEmpty()) {
                this.loadingUpRow = -1;
                this.messagesStartRow = -1;
                this.messagesEndRow = -1;
                return;
            }
            if (ChannelAdminLogActivity.this.endReached) {
                this.loadingUpRow = -1;
            } else {
                int i = this.rowCount;
                this.rowCount = i + 1;
                this.loadingUpRow = i;
            }
            this.messagesStartRow = this.rowCount;
            this.rowCount += ChannelAdminLogActivity.this.messages.size();
            this.messagesEndRow = this.rowCount;
        }
    }

    public ChannelAdminLogActivity(Chat chat) {
        this.currentChat = chat;
    }

    private void addCanBanUser(Bundle bundle, int i) {
        if (this.currentChat.megagroup && this.admins != null && ChatObject.canBlockUsers(this.currentChat)) {
            for (int i2 = 0; i2 < this.admins.size(); i2++) {
                ChannelParticipant channelParticipant = (ChannelParticipant) this.admins.get(i2);
                if (channelParticipant.user_id == i) {
                    if (!channelParticipant.can_edit) {
                        return;
                    }
                    bundle.putInt("ban_chat_id", this.currentChat.id);
                }
            }
            bundle.putInt("ban_chat_id", this.currentChat.id);
        }
    }

    private void alertUserOpenError(MessageObject messageObject) {
        if (getParentActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
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

    private void checkScrollForLoad(boolean z) {
        if (this.chatLayoutManager != null && !this.paused) {
            int findFirstVisibleItemPosition = this.chatLayoutManager.findFirstVisibleItemPosition();
            if ((findFirstVisibleItemPosition == -1 ? 0 : Math.abs(this.chatLayoutManager.findLastVisibleItemPosition() - findFirstVisibleItemPosition) + 1) > 0) {
                this.chatAdapter.getItemCount();
                if (findFirstVisibleItemPosition <= (z ? 25 : 5) && !this.loading && !this.endReached) {
                    loadMessages(false);
                }
            }
        }
    }

    private void createMenu(View view) {
        MessageObject messageObject = null;
        if (view instanceof ChatMessageCell) {
            messageObject = ((ChatMessageCell) view).getMessageObject();
        } else if (view instanceof ChatActionCell) {
            messageObject = ((ChatActionCell) view).getMessageObject();
        }
        if (messageObject != null) {
            int messageType = getMessageType(messageObject);
            this.selectedObject = messageObject;
            if (getParentActivity() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                ArrayList arrayList = new ArrayList();
                final ArrayList arrayList2 = new ArrayList();
                if (this.selectedObject.type == 0 || this.selectedObject.caption != null) {
                    arrayList.add(LocaleController.getString("Copy", R.string.Copy));
                    arrayList2.add(Integer.valueOf(3));
                }
                if (messageType == 1) {
                    if (this.selectedObject.currentEvent != null && (this.selectedObject.currentEvent.action instanceof TL_channelAdminLogEventActionChangeStickerSet)) {
                        InputStickerSet inputStickerSet = this.selectedObject.currentEvent.action.new_stickerset;
                        if (inputStickerSet == null || (inputStickerSet instanceof TLRPC$TL_inputStickerSetEmpty)) {
                            inputStickerSet = this.selectedObject.currentEvent.action.prev_stickerset;
                        }
                        if (inputStickerSet != null) {
                            showDialog(new StickersAlert(getParentActivity(), this, inputStickerSet, null, null));
                            return;
                        }
                    }
                } else if (messageType == 3) {
                    if ((this.selectedObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) && MessageObject.isNewGifDocument(this.selectedObject.messageOwner.media.webpage.document)) {
                        arrayList.add(LocaleController.getString("SaveToGIFs", R.string.SaveToGIFs));
                        arrayList2.add(Integer.valueOf(11));
                    }
                } else if (messageType == 4) {
                    if (this.selectedObject.isVideo()) {
                        arrayList.add(LocaleController.getString("SaveToGallery", R.string.SaveToGallery));
                        arrayList2.add(Integer.valueOf(4));
                        arrayList.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                        arrayList2.add(Integer.valueOf(6));
                    } else if (this.selectedObject.isMusic()) {
                        arrayList.add(LocaleController.getString("SaveToMusic", R.string.SaveToMusic));
                        arrayList2.add(Integer.valueOf(10));
                        arrayList.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                        arrayList2.add(Integer.valueOf(6));
                    } else if (this.selectedObject.getDocument() != null) {
                        if (MessageObject.isNewGifDocument(this.selectedObject.getDocument())) {
                            arrayList.add(LocaleController.getString("SaveToGIFs", R.string.SaveToGIFs));
                            arrayList2.add(Integer.valueOf(11));
                        }
                        arrayList.add(LocaleController.getString("SaveToDownloads", R.string.SaveToDownloads));
                        arrayList2.add(Integer.valueOf(10));
                        arrayList.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                        arrayList2.add(Integer.valueOf(6));
                    } else {
                        arrayList.add(LocaleController.getString("SaveToGallery", R.string.SaveToGallery));
                        arrayList2.add(Integer.valueOf(4));
                    }
                } else if (messageType == 5) {
                    arrayList.add(LocaleController.getString("ApplyLocalizationFile", R.string.ApplyLocalizationFile));
                    arrayList2.add(Integer.valueOf(5));
                    arrayList.add(LocaleController.getString("SaveToDownloads", R.string.SaveToDownloads));
                    arrayList2.add(Integer.valueOf(10));
                    arrayList.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                    arrayList2.add(Integer.valueOf(6));
                } else if (messageType == 10) {
                    arrayList.add(LocaleController.getString("ApplyThemeFile", R.string.ApplyThemeFile));
                    arrayList2.add(Integer.valueOf(5));
                    arrayList.add(LocaleController.getString("SaveToDownloads", R.string.SaveToDownloads));
                    arrayList2.add(Integer.valueOf(10));
                    arrayList.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                    arrayList2.add(Integer.valueOf(6));
                } else if (messageType == 6) {
                    arrayList.add(LocaleController.getString("SaveToGallery", R.string.SaveToGallery));
                    arrayList2.add(Integer.valueOf(7));
                    arrayList.add(LocaleController.getString("SaveToDownloads", R.string.SaveToDownloads));
                    arrayList2.add(Integer.valueOf(10));
                    arrayList.add(LocaleController.getString("ShareFile", R.string.ShareFile));
                    arrayList2.add(Integer.valueOf(6));
                } else if (messageType == 7) {
                    if (this.selectedObject.isMask()) {
                        arrayList.add(LocaleController.getString("AddToMasks", R.string.AddToMasks));
                    } else {
                        arrayList.add(LocaleController.getString("AddToStickers", R.string.AddToStickers));
                    }
                    arrayList2.add(Integer.valueOf(9));
                } else if (messageType == 8) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(this.selectedObject.messageOwner.media.user_id));
                    if (!(user == null || user.id == UserConfig.getClientUserId() || ContactsController.getInstance().contactsDict.get(Integer.valueOf(user.id)) != null)) {
                        arrayList.add(LocaleController.getString("AddContactTitle", R.string.AddContactTitle));
                        arrayList2.add(Integer.valueOf(15));
                    }
                    if (!(this.selectedObject.messageOwner.media.phone_number == null && this.selectedObject.messageOwner.media.phone_number.length() == 0)) {
                        arrayList.add(LocaleController.getString("Copy", R.string.Copy));
                        arrayList2.add(Integer.valueOf(16));
                        arrayList.add(LocaleController.getString("Call", R.string.Call));
                        arrayList2.add(Integer.valueOf(17));
                    }
                }
                if (!arrayList2.isEmpty()) {
                    builder.setItems((CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (ChannelAdminLogActivity.this.selectedObject != null && i >= 0 && i < arrayList2.size()) {
                                ChannelAdminLogActivity.this.processSelectedOption(((Integer) arrayList2.get(i)).intValue());
                            }
                        }
                    });
                    builder.setTitle(LocaleController.getString("Message", R.string.Message));
                    showDialog(builder.create());
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
                        ChannelAdminLogActivity.this.contentView.invalidate();
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
                        canvas.drawPath(ChannelAdminLogActivity.this.aspectPath, ChannelAdminLogActivity.this.aspectPaint);
                    }

                    protected void onSizeChanged(int i, int i2, int i3, int i4) {
                        super.onSizeChanged(i, i2, i3, i4);
                        ChannelAdminLogActivity.this.aspectPath.reset();
                        ChannelAdminLogActivity.this.aspectPath.addCircle((float) (i / 2), (float) (i2 / 2), (float) (i / 2), Direction.CW);
                        ChannelAdminLogActivity.this.aspectPath.toggleInverseFillType();
                    }

                    public void setTranslationY(float f) {
                        super.setTranslationY(f);
                        ChannelAdminLogActivity.this.contentView.invalidate();
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
                    if (ChannelAdminLogActivity.this.avatarContainer != null) {
                        ChannelAdminLogActivity.this.avatarContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                    }
                    return true;
                }
            });
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
        Object obj = 1;
        if (messageObject == null || messageObject.type == 6) {
            return -1;
        }
        if (messageObject.type == 10 || messageObject.type == 11 || messageObject.type == 16) {
            return messageObject.getId() != 0 ? 1 : -1;
        } else {
            if (messageObject.isVoice()) {
                return 2;
            }
            if (messageObject.isSticker()) {
                InputStickerSet inputStickerSet = messageObject.getInputStickerSet();
                if (inputStickerSet instanceof TLRPC$TL_inputStickerSetID) {
                    if (!StickersQuery.isStickerPackInstalled(inputStickerSet.id)) {
                        return 7;
                    }
                } else if ((inputStickerSet instanceof TLRPC$TL_inputStickerSetShortName) && !StickersQuery.isStickerPackInstalled(inputStickerSet.short_name)) {
                    return 7;
                }
            } else if ((!messageObject.isRoundVideo() || (messageObject.isRoundVideo() && BuildVars.DEBUG_VERSION)) && ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) || messageObject.getDocument() != null || messageObject.isMusic() || messageObject.isVideo())) {
                Object obj2 = null;
                if (!(messageObject.messageOwner.attachPath == null || messageObject.messageOwner.attachPath.length() == 0 || !new File(messageObject.messageOwner.attachPath).exists())) {
                    obj2 = 1;
                }
                if (!(obj2 == null && FileLoader.getPathToMessage(messageObject.messageOwner).exists())) {
                    obj = obj2;
                }
                if (obj != null) {
                    if (messageObject.getDocument() != null) {
                        String str = messageObject.getDocument().mime_type;
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
                            if (animator.equals(ChannelAdminLogActivity.this.floatingDateAnimation)) {
                                ChannelAdminLogActivity.this.floatingDateAnimation = null;
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

    private void loadAdmins() {
        TLObject tL_channels_getParticipants = new TL_channels_getParticipants();
        tL_channels_getParticipants.channel = MessagesController.getInputChannel(this.currentChat);
        tL_channels_getParticipants.filter = new TL_channelParticipantsAdmins();
        tL_channels_getParticipants.offset = 0;
        tL_channels_getParticipants.limit = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipants, new RequestDelegate() {
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (tLRPC$TL_error == null) {
                            TL_channels_channelParticipants tL_channels_channelParticipants = (TL_channels_channelParticipants) tLObject;
                            MessagesController.getInstance().putUsers(tL_channels_channelParticipants.users, false);
                            ChannelAdminLogActivity.this.admins = tL_channels_channelParticipants.participants;
                            if (ChannelAdminLogActivity.this.visibleDialog instanceof AdminLogFilterAlert) {
                                ((AdminLogFilterAlert) ChannelAdminLogActivity.this.visibleDialog).setCurrentAdmins(ChannelAdminLogActivity.this.admins);
                            }
                        }
                    }
                });
            }
        }), this.classGuid);
    }

    private void loadMessages(boolean z) {
        if (!this.loading) {
            if (z) {
                this.minEventId = Long.MAX_VALUE;
                if (this.progressView != null) {
                    this.progressView.setVisibility(0);
                    this.emptyViewContainer.setVisibility(4);
                    this.chatListView.setEmptyView(null);
                }
                this.messagesDict.clear();
                this.messages.clear();
                this.messagesByDays.clear();
            }
            this.loading = true;
            TLObject tL_channels_getAdminLog = new TL_channels_getAdminLog();
            tL_channels_getAdminLog.channel = MessagesController.getInputChannel(this.currentChat);
            tL_channels_getAdminLog.f10148q = this.searchQuery;
            tL_channels_getAdminLog.limit = 50;
            if (z || this.messages.isEmpty()) {
                tL_channels_getAdminLog.max_id = 0;
            } else {
                tL_channels_getAdminLog.max_id = this.minEventId;
            }
            tL_channels_getAdminLog.min_id = 0;
            if (this.currentFilter != null) {
                tL_channels_getAdminLog.flags |= 1;
                tL_channels_getAdminLog.events_filter = this.currentFilter;
            }
            if (this.selectedAdmins != null) {
                tL_channels_getAdminLog.flags |= 2;
                for (Entry value : this.selectedAdmins.entrySet()) {
                    tL_channels_getAdminLog.admins.add(MessagesController.getInputUser((User) value.getValue()));
                }
            }
            updateEmptyPlaceholder();
            ConnectionsManager.getInstance().sendRequest(tL_channels_getAdminLog, new C41052());
            if (z && this.chatAdapter != null) {
                this.chatAdapter.notifyDataSetChanged();
            }
        }
    }

    private void moveScrollToLastMessage() {
        if (this.chatListView != null && !this.messages.isEmpty()) {
            this.chatLayoutManager.scrollToPositionWithOffset(this.messages.size() - 1, -100000 - this.chatListView.getPaddingTop());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processSelectedOption(int r8) {
        /*
        r7 = this;
        r3 = 3;
        r5 = -1;
        r2 = 0;
        r1 = 1;
        r4 = 0;
        r0 = r7.selectedObject;
        if (r0 != 0) goto L_0x000a;
    L_0x0009:
        return;
    L_0x000a:
        switch(r8) {
            case 3: goto L_0x0010;
            case 4: goto L_0x001a;
            case 5: goto L_0x0089;
            case 6: goto L_0x01b6;
            case 7: goto L_0x0251;
            case 8: goto L_0x000d;
            case 9: goto L_0x02ae;
            case 10: goto L_0x02c4;
            case 11: goto L_0x0356;
            case 12: goto L_0x000d;
            case 13: goto L_0x000d;
            case 14: goto L_0x000d;
            case 15: goto L_0x0365;
            case 16: goto L_0x0396;
            case 17: goto L_0x03a3;
            default: goto L_0x000d;
        };
    L_0x000d:
        r7.selectedObject = r4;
        goto L_0x0009;
    L_0x0010:
        r0 = r7.selectedObject;
        r0 = r7.getMessageContent(r0, r2, r1);
        org.telegram.messenger.AndroidUtilities.addToClipboard(r0);
        goto L_0x000d;
    L_0x001a:
        r0 = r7.selectedObject;
        r0 = r0.messageOwner;
        r0 = r0.attachPath;
        if (r0 == 0) goto L_0x0034;
    L_0x0022:
        r5 = r0.length();
        if (r5 <= 0) goto L_0x0034;
    L_0x0028:
        r5 = new java.io.File;
        r5.<init>(r0);
        r5 = r5.exists();
        if (r5 != 0) goto L_0x0034;
    L_0x0033:
        r0 = r4;
    L_0x0034:
        if (r0 == 0) goto L_0x003c;
    L_0x0036:
        r5 = r0.length();
        if (r5 != 0) goto L_0x0048;
    L_0x003c:
        r0 = r7.selectedObject;
        r0 = r0.messageOwner;
        r0 = org.telegram.messenger.FileLoader.getPathToMessage(r0);
        r0 = r0.toString();
    L_0x0048:
        r5 = r7.selectedObject;
        r5 = r5.type;
        if (r5 == r3) goto L_0x0054;
    L_0x004e:
        r5 = r7.selectedObject;
        r5 = r5.type;
        if (r5 != r1) goto L_0x000d;
    L_0x0054:
        r5 = android.os.Build.VERSION.SDK_INT;
        r6 = 23;
        if (r5 < r6) goto L_0x0079;
    L_0x005a:
        r5 = r7.getParentActivity();
        r6 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r5 = r5.checkSelfPermission(r6);
        if (r5 == 0) goto L_0x0079;
    L_0x0067:
        r0 = r7.getParentActivity();
        r1 = new java.lang.String[r1];
        r3 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r1[r2] = r3;
        r2 = 4;
        r0.requestPermissions(r1, r2);
        r7.selectedObject = r4;
        goto L_0x0009;
    L_0x0079:
        r5 = r7.getParentActivity();
        r6 = r7.selectedObject;
        r6 = r6.type;
        if (r6 != r3) goto L_0x0087;
    L_0x0083:
        org.telegram.messenger.MediaController.saveFile(r0, r5, r1, r4, r4);
        goto L_0x000d;
    L_0x0087:
        r1 = r2;
        goto L_0x0083;
    L_0x0089:
        r0 = r7.selectedObject;
        r0 = r0.messageOwner;
        r0 = r0.attachPath;
        if (r0 == 0) goto L_0x03e1;
    L_0x0091:
        r0 = r7.selectedObject;
        r0 = r0.messageOwner;
        r0 = r0.attachPath;
        r0 = r0.length();
        if (r0 == 0) goto L_0x03e1;
    L_0x009d:
        r2 = new java.io.File;
        r0 = r7.selectedObject;
        r0 = r0.messageOwner;
        r0 = r0.attachPath;
        r2.<init>(r0);
        r0 = r2.exists();
        if (r0 == 0) goto L_0x03e1;
    L_0x00ae:
        if (r2 != 0) goto L_0x00bf;
    L_0x00b0:
        r0 = r7.selectedObject;
        r0 = r0.messageOwner;
        r0 = org.telegram.messenger.FileLoader.getPathToMessage(r0);
        r3 = r0.exists();
        if (r3 == 0) goto L_0x00bf;
    L_0x00be:
        r2 = r0;
    L_0x00bf:
        if (r2 == 0) goto L_0x000d;
    L_0x00c1:
        r0 = r2.getName();
        r3 = "attheme";
        r0 = r0.endsWith(r3);
        if (r0 == 0) goto L_0x015f;
    L_0x00ce:
        r0 = r7.chatLayoutManager;
        if (r0 == 0) goto L_0x00fe;
    L_0x00d2:
        r0 = r7.chatLayoutManager;
        r0 = r0.findLastVisibleItemPosition();
        r3 = r7.chatLayoutManager;
        r3 = r3.getItemCount();
        r3 = r3 + -1;
        if (r0 >= r3) goto L_0x0117;
    L_0x00e2:
        r0 = r7.chatLayoutManager;
        r0 = r0.findFirstVisibleItemPosition();
        r7.scrollToPositionOnRecreate = r0;
        r0 = r7.chatListView;
        r3 = r7.scrollToPositionOnRecreate;
        r0 = r0.findViewHolderForAdapterPosition(r3);
        r0 = (org.telegram.ui.Components.RecyclerListView.Holder) r0;
        if (r0 == 0) goto L_0x0114;
    L_0x00f6:
        r0 = r0.itemView;
        r0 = r0.getTop();
        r7.scrollToOffsetOnRecreate = r0;
    L_0x00fe:
        r0 = r7.selectedObject;
        r0 = r0.getDocumentName();
        r0 = org.telegram.ui.ActionBar.Theme.applyThemeFile(r2, r0, r1);
        if (r0 == 0) goto L_0x011a;
    L_0x010a:
        r1 = new org.telegram.ui.ThemePreviewActivity;
        r1.<init>(r2, r0);
        r7.presentFragment(r1);
        goto L_0x000d;
    L_0x0114:
        r7.scrollToPositionOnRecreate = r5;
        goto L_0x00fe;
    L_0x0117:
        r7.scrollToPositionOnRecreate = r5;
        goto L_0x00fe;
    L_0x011a:
        r7.scrollToPositionOnRecreate = r5;
        r0 = r7.getParentActivity();
        if (r0 != 0) goto L_0x0126;
    L_0x0122:
        r7.selectedObject = r4;
        goto L_0x0009;
    L_0x0126:
        r0 = new org.telegram.ui.ActionBar.AlertDialog$Builder;
        r1 = r7.getParentActivity();
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
        r0.setPositiveButton(r1, r4);
        r0 = r0.create();
        r7.showDialog(r0);
        goto L_0x000d;
    L_0x015f:
        r0 = org.telegram.messenger.LocaleController.getInstance();
        r0 = r0.applyLanguageFile(r2);
        if (r0 == 0) goto L_0x0173;
    L_0x0169:
        r0 = new org.telegram.ui.LanguageSelectActivity;
        r0.<init>();
        r7.presentFragment(r0);
        goto L_0x000d;
    L_0x0173:
        r0 = r7.getParentActivity();
        if (r0 != 0) goto L_0x017d;
    L_0x0179:
        r7.selectedObject = r4;
        goto L_0x0009;
    L_0x017d:
        r0 = new org.telegram.ui.ActionBar.AlertDialog$Builder;
        r1 = r7.getParentActivity();
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
        r0.setPositiveButton(r1, r4);
        r0 = r0.create();
        r7.showDialog(r0);
        goto L_0x000d;
    L_0x01b6:
        r0 = r7.selectedObject;
        r0 = r0.messageOwner;
        r0 = r0.attachPath;
        if (r0 == 0) goto L_0x01d0;
    L_0x01be:
        r1 = r0.length();
        if (r1 <= 0) goto L_0x01d0;
    L_0x01c4:
        r1 = new java.io.File;
        r1.<init>(r0);
        r1 = r1.exists();
        if (r1 != 0) goto L_0x01d0;
    L_0x01cf:
        r0 = r4;
    L_0x01d0:
        if (r0 == 0) goto L_0x01d8;
    L_0x01d2:
        r1 = r0.length();
        if (r1 != 0) goto L_0x01e4;
    L_0x01d8:
        r0 = r7.selectedObject;
        r0 = r0.messageOwner;
        r0 = org.telegram.messenger.FileLoader.getPathToMessage(r0);
        r0 = r0.toString();
    L_0x01e4:
        r1 = new android.content.Intent;
        r2 = "android.intent.action.SEND";
        r1.<init>(r2);
        r2 = r7.selectedObject;
        r2 = r2.getDocument();
        r2 = r2.mime_type;
        r1.setType(r2);
        r2 = android.os.Build.VERSION.SDK_INT;
        r3 = 24;
        if (r2 < r3) goto L_0x0241;
    L_0x01fd:
        r2 = "android.intent.extra.STREAM";
        r3 = r7.getParentActivity();	 Catch:{ Exception -> 0x0230 }
        r5 = "org.ir.talaeii.provider";
        r6 = new java.io.File;	 Catch:{ Exception -> 0x0230 }
        r6.<init>(r0);	 Catch:{ Exception -> 0x0230 }
        r3 = android.support.v4.content.FileProvider.a(r3, r5, r6);	 Catch:{ Exception -> 0x0230 }
        r1.putExtra(r2, r3);	 Catch:{ Exception -> 0x0230 }
        r2 = 1;
        r1.setFlags(r2);	 Catch:{ Exception -> 0x0230 }
    L_0x0217:
        r0 = r7.getParentActivity();
        r2 = "ShareFile";
        r3 = 2131232402; // 0x7f080692 float:1.8080912E38 double:1.052968713E-314;
        r2 = org.telegram.messenger.LocaleController.getString(r2, r3);
        r1 = android.content.Intent.createChooser(r1, r2);
        r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r0.startActivityForResult(r1, r2);
        goto L_0x000d;
    L_0x0230:
        r2 = move-exception;
        r2 = "android.intent.extra.STREAM";
        r3 = new java.io.File;
        r3.<init>(r0);
        r0 = android.net.Uri.fromFile(r3);
        r1.putExtra(r2, r0);
        goto L_0x0217;
    L_0x0241:
        r2 = "android.intent.extra.STREAM";
        r3 = new java.io.File;
        r3.<init>(r0);
        r0 = android.net.Uri.fromFile(r3);
        r1.putExtra(r2, r0);
        goto L_0x0217;
    L_0x0251:
        r0 = r7.selectedObject;
        r0 = r0.messageOwner;
        r0 = r0.attachPath;
        if (r0 == 0) goto L_0x026b;
    L_0x0259:
        r3 = r0.length();
        if (r3 <= 0) goto L_0x026b;
    L_0x025f:
        r3 = new java.io.File;
        r3.<init>(r0);
        r3 = r3.exists();
        if (r3 != 0) goto L_0x026b;
    L_0x026a:
        r0 = r4;
    L_0x026b:
        if (r0 == 0) goto L_0x0273;
    L_0x026d:
        r3 = r0.length();
        if (r3 != 0) goto L_0x027f;
    L_0x0273:
        r0 = r7.selectedObject;
        r0 = r0.messageOwner;
        r0 = org.telegram.messenger.FileLoader.getPathToMessage(r0);
        r0 = r0.toString();
    L_0x027f:
        r3 = android.os.Build.VERSION.SDK_INT;
        r5 = 23;
        if (r3 < r5) goto L_0x02a5;
    L_0x0285:
        r3 = r7.getParentActivity();
        r5 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r3 = r3.checkSelfPermission(r5);
        if (r3 == 0) goto L_0x02a5;
    L_0x0292:
        r0 = r7.getParentActivity();
        r1 = new java.lang.String[r1];
        r3 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r1[r2] = r3;
        r2 = 4;
        r0.requestPermissions(r1, r2);
        r7.selectedObject = r4;
        goto L_0x0009;
    L_0x02a5:
        r1 = r7.getParentActivity();
        org.telegram.messenger.MediaController.saveFile(r0, r1, r2, r4, r4);
        goto L_0x000d;
    L_0x02ae:
        r0 = new org.telegram.ui.Components.StickersAlert;
        r1 = r7.getParentActivity();
        r2 = r7.selectedObject;
        r3 = r2.getInputStickerSet();
        r2 = r7;
        r5 = r4;
        r0.<init>(r1, r2, r3, r4, r5);
        r7.showDialog(r0);
        goto L_0x000d;
    L_0x02c4:
        r0 = android.os.Build.VERSION.SDK_INT;
        r5 = 23;
        if (r0 < r5) goto L_0x02ea;
    L_0x02ca:
        r0 = r7.getParentActivity();
        r5 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r0 = r0.checkSelfPermission(r5);
        if (r0 == 0) goto L_0x02ea;
    L_0x02d7:
        r0 = r7.getParentActivity();
        r1 = new java.lang.String[r1];
        r3 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r1[r2] = r3;
        r2 = 4;
        r0.requestPermissions(r1, r2);
        r7.selectedObject = r4;
        goto L_0x0009;
    L_0x02ea:
        r0 = r7.selectedObject;
        r0 = r0.getDocument();
        r0 = org.telegram.messenger.FileLoader.getDocumentFileName(r0);
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 == 0) goto L_0x0300;
    L_0x02fa:
        r0 = r7.selectedObject;
        r0 = r0.getFileName();
    L_0x0300:
        r1 = r7.selectedObject;
        r1 = r1.messageOwner;
        r1 = r1.attachPath;
        if (r1 == 0) goto L_0x031a;
    L_0x0308:
        r2 = r1.length();
        if (r2 <= 0) goto L_0x031a;
    L_0x030e:
        r2 = new java.io.File;
        r2.<init>(r1);
        r2 = r2.exists();
        if (r2 != 0) goto L_0x031a;
    L_0x0319:
        r1 = r4;
    L_0x031a:
        if (r1 == 0) goto L_0x0322;
    L_0x031c:
        r2 = r1.length();
        if (r2 != 0) goto L_0x032e;
    L_0x0322:
        r1 = r7.selectedObject;
        r1 = r1.messageOwner;
        r1 = org.telegram.messenger.FileLoader.getPathToMessage(r1);
        r1 = r1.toString();
    L_0x032e:
        r5 = r7.getParentActivity();
        r2 = r7.selectedObject;
        r2 = r2.isMusic();
        if (r2 == 0) goto L_0x0350;
    L_0x033a:
        r2 = r3;
    L_0x033b:
        r3 = r7.selectedObject;
        r3 = r3.getDocument();
        if (r3 == 0) goto L_0x0352;
    L_0x0343:
        r3 = r7.selectedObject;
        r3 = r3.getDocument();
        r3 = r3.mime_type;
    L_0x034b:
        org.telegram.messenger.MediaController.saveFile(r1, r5, r2, r0, r3);
        goto L_0x000d;
    L_0x0350:
        r2 = 2;
        goto L_0x033b;
    L_0x0352:
        r3 = "";
        goto L_0x034b;
    L_0x0356:
        r0 = r7.selectedObject;
        r0 = r0.getDocument();
        r1 = org.telegram.messenger.MessagesController.getInstance();
        r1.saveGif(r0);
        goto L_0x000d;
    L_0x0365:
        r0 = new android.os.Bundle;
        r0.<init>();
        r2 = "user_id";
        r3 = r7.selectedObject;
        r3 = r3.messageOwner;
        r3 = r3.media;
        r3 = r3.user_id;
        r0.putInt(r2, r3);
        r2 = "phone";
        r3 = r7.selectedObject;
        r3 = r3.messageOwner;
        r3 = r3.media;
        r3 = r3.phone_number;
        r0.putString(r2, r3);
        r2 = "addContact";
        r0.putBoolean(r2, r1);
        r1 = new org.telegram.ui.ContactAddActivity;
        r1.<init>(r0);
        r7.presentFragment(r1);
        goto L_0x000d;
    L_0x0396:
        r0 = r7.selectedObject;
        r0 = r0.messageOwner;
        r0 = r0.media;
        r0 = r0.phone_number;
        org.telegram.messenger.AndroidUtilities.addToClipboard(r0);
        goto L_0x000d;
    L_0x03a3:
        r0 = new android.content.Intent;	 Catch:{ Exception -> 0x03db }
        r1 = "android.intent.action.DIAL";
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x03db }
        r2.<init>();	 Catch:{ Exception -> 0x03db }
        r3 = "tel:";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x03db }
        r3 = r7.selectedObject;	 Catch:{ Exception -> 0x03db }
        r3 = r3.messageOwner;	 Catch:{ Exception -> 0x03db }
        r3 = r3.media;	 Catch:{ Exception -> 0x03db }
        r3 = r3.phone_number;	 Catch:{ Exception -> 0x03db }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x03db }
        r2 = r2.toString();	 Catch:{ Exception -> 0x03db }
        r2 = android.net.Uri.parse(r2);	 Catch:{ Exception -> 0x03db }
        r0.<init>(r1, r2);	 Catch:{ Exception -> 0x03db }
        r1 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r0.addFlags(r1);	 Catch:{ Exception -> 0x03db }
        r1 = r7.getParentActivity();	 Catch:{ Exception -> 0x03db }
        r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r1.startActivityForResult(r0, r2);	 Catch:{ Exception -> 0x03db }
        goto L_0x000d;
    L_0x03db:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x000d;
    L_0x03e1:
        r2 = r4;
        goto L_0x00ae;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.ChannelAdminLogActivity.processSelectedOption(int):void");
    }

    private void removeMessageObject(MessageObject messageObject) {
        int indexOf = this.messages.indexOf(messageObject);
        if (indexOf != -1) {
            this.messages.remove(indexOf);
            if (this.chatAdapter != null) {
                this.chatAdapter.notifyItemRemoved(((this.chatAdapter.messagesStartRow + this.messages.size()) - indexOf) - 1);
            }
        }
    }

    private void updateBottomOverlay() {
    }

    private void updateEmptyPlaceholder() {
        if (this.emptyView != null) {
            if (!TextUtils.isEmpty(this.searchQuery)) {
                this.emptyView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(5.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(5.0f));
                this.emptyView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("EventLogEmptyTextSearch", R.string.EventLogEmptyTextSearch, new Object[]{this.searchQuery})));
            } else if (this.selectedAdmins == null && this.currentFilter == null) {
                this.emptyView.setPadding(AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f));
                if (this.currentChat.megagroup) {
                    this.emptyView.setText(AndroidUtilities.replaceTags(LocaleController.getString("EventLogEmpty", R.string.EventLogEmpty)));
                } else {
                    this.emptyView.setText(AndroidUtilities.replaceTags(LocaleController.getString("EventLogEmptyChannel", R.string.EventLogEmptyChannel)));
                }
            } else {
                this.emptyView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(5.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(5.0f));
                this.emptyView.setText(AndroidUtilities.replaceTags(LocaleController.getString("EventLogEmptySearch", R.string.EventLogEmptySearch)));
            }
        }
    }

    private void updateMessagesVisisblePart() {
        if (this.chatListView != null) {
            int childCount = this.chatListView.getChildCount();
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
                        this.roundVideoContainer.setTranslationY((float) (photoImage.getImageY() + (this.fragmentView.getPaddingTop() + top)));
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
                    if (playingMessageObject != null && playingMessageObject.isRoundVideo() && this.checkTextureViewPosition) {
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

    private void updateTextureViewPosition() {
        boolean z;
        int childCount = this.chatListView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.chatListView.getChildAt(i);
            if (childAt instanceof ChatMessageCell) {
                ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                MessageObject messageObject = chatMessageCell.getMessageObject();
                if (this.roundVideoContainer != null && messageObject.isRoundVideo() && MediaController.getInstance().isPlayingMessage(messageObject)) {
                    ImageReceiver photoImage = chatMessageCell.getPhotoImage();
                    this.roundVideoContainer.setTranslationX((float) photoImage.getImageX());
                    this.roundVideoContainer.setTranslationY((float) ((chatMessageCell.getTop() + this.fragmentView.getPaddingTop()) + photoImage.getImageY()));
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
            if (z) {
                MediaController.getInstance().setCurrentRoundVisible(true);
                return;
            }
            this.roundVideoContainer.setTranslationY((float) ((-AndroidUtilities.roundMessageSize) - 100));
            this.fragmentView.invalidate();
            if (playingMessageObject != null && playingMessageObject.isRoundVideo()) {
                if (this.checkTextureViewPosition || PipRoundVideoView.getInstance() != null) {
                    MediaController.getInstance().setCurrentRoundVisible(false);
                }
            }
        }
    }

    public View createView(Context context) {
        if (this.chatMessageCellsCache.isEmpty()) {
            for (int i = 0; i < 8; i++) {
                this.chatMessageCellsCache.add(new ChatMessageCell(context));
            }
        }
        this.searchWas = false;
        this.hasOwnBackground = true;
        Theme.createChatResources(context, false);
        this.actionBar.setAddToContainer(false);
        ActionBar actionBar = this.actionBar;
        boolean z = VERSION.SDK_INT >= 21 && !AndroidUtilities.isTablet();
        actionBar.setOccupyStatusBar(z);
        this.actionBar.setBackButtonDrawable(new BackDrawable(false));
        this.actionBar.setActionBarMenuOnItemClick(new C41063());
        this.avatarContainer = new ChatAvatarContainer(context, null, false);
        this.actionBar.addView(this.avatarContainer, 0, LayoutHelper.createFrame(-2, -1.0f, 51, 56.0f, BitmapDescriptorFactory.HUE_RED, 40.0f, BitmapDescriptorFactory.HUE_RED));
        this.searchItem = this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C41074());
        this.searchItem.getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.avatarContainer.setEnabled(false);
        this.avatarContainer.setTitle(this.currentChat.title);
        this.avatarContainer.setSubtitle(LocaleController.getString("EventLogAllEvents", R.string.EventLogAllEvents));
        this.avatarContainer.setChatAvatar(this.currentChat);
        this.fragmentView = new SizeNotifierFrameLayout(context) {
            protected boolean drawChild(Canvas canvas, View view, long j) {
                boolean drawChild = super.drawChild(canvas, view, j);
                if (view == ChannelAdminLogActivity.this.actionBar && ChannelAdminLogActivity.this.parentLayout != null) {
                    ChannelAdminLogActivity.this.parentLayout.drawHeaderShadow(canvas, ChannelAdminLogActivity.this.actionBar.getVisibility() == 0 ? ChannelAdminLogActivity.this.actionBar.getMeasuredHeight() : 0);
                }
                return drawChild;
            }

            protected boolean isActionBarVisible() {
                return ChannelAdminLogActivity.this.actionBar.getVisibility() == 0;
            }

            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
                if (playingMessageObject != null && playingMessageObject.isRoundVideo() && playingMessageObject.getDialogId() == ChannelAdminLogActivity.this.dialog_id) {
                    MediaController.getInstance().setTextureView(ChannelAdminLogActivity.this.createTextureView(false), ChannelAdminLogActivity.this.aspectRatioFrameLayout, ChannelAdminLogActivity.this.roundVideoContainer, true);
                }
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int childCount = getChildCount();
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
                                i7 = ((((i4 - i2) - measuredHeight) / 2) + layoutParams.topMargin) - layoutParams.bottomMargin;
                                break;
                            case 48:
                                i7 = layoutParams.topMargin + getPaddingTop();
                                if (childAt != ChannelAdminLogActivity.this.actionBar && ChannelAdminLogActivity.this.actionBar.getVisibility() == 0) {
                                    i7 += ChannelAdminLogActivity.this.actionBar.getMeasuredHeight();
                                    break;
                                }
                            case 80:
                                i7 = ((i4 - i2) - measuredHeight) - layoutParams.bottomMargin;
                                break;
                            default:
                                i7 = layoutParams.topMargin;
                                break;
                        }
                        if (childAt == ChannelAdminLogActivity.this.emptyViewContainer) {
                            i7 -= AndroidUtilities.dp(24.0f) - (ChannelAdminLogActivity.this.actionBar.getVisibility() == 0 ? ChannelAdminLogActivity.this.actionBar.getMeasuredHeight() / 2 : 0);
                        } else if (childAt == ChannelAdminLogActivity.this.actionBar) {
                            i7 -= getPaddingTop();
                        }
                        childAt.layout(i6, i7, i6 + measuredWidth, i7 + measuredHeight);
                    }
                }
                ChannelAdminLogActivity.this.updateMessagesVisisblePart();
                notifyHeightChanged();
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i);
                int size2 = MeasureSpec.getSize(i2);
                setMeasuredDimension(size, size2);
                int paddingTop = size2 - getPaddingTop();
                measureChildWithMargins(ChannelAdminLogActivity.this.actionBar, i, 0, i2, 0);
                size2 = ChannelAdminLogActivity.this.actionBar.getMeasuredHeight();
                if (ChannelAdminLogActivity.this.actionBar.getVisibility() == 0) {
                    paddingTop -= size2;
                }
                getKeyboardHeight();
                int childCount = getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    View childAt = getChildAt(i3);
                    if (!(childAt == null || childAt.getVisibility() == 8 || childAt == ChannelAdminLogActivity.this.actionBar)) {
                        if (childAt == ChannelAdminLogActivity.this.chatListView || childAt == ChannelAdminLogActivity.this.progressView) {
                            childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(Math.max(AndroidUtilities.dp(10.0f), paddingTop - AndroidUtilities.dp(50.0f)), 1073741824));
                        } else if (childAt == ChannelAdminLogActivity.this.emptyViewContainer) {
                            childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(paddingTop, 1073741824));
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
        this.emptyViewContainer.setOnTouchListener(new C41096());
        this.emptyView = new TextView(context);
        this.emptyView.setTextSize(1, 14.0f);
        this.emptyView.setGravity(17);
        this.emptyView.setTextColor(Theme.getColor(Theme.key_chat_serviceText));
        this.emptyView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(10.0f), Theme.getServiceMessageColor()));
        this.emptyView.setPadding(AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f));
        this.emptyViewContainer.addView(this.emptyView, LayoutHelper.createFrame(-2, -2.0f, 17, 16.0f, BitmapDescriptorFactory.HUE_RED, 16.0f, BitmapDescriptorFactory.HUE_RED));
        this.chatListView = new RecyclerListView(context) {
            public boolean drawChild(Canvas canvas, View view, long j) {
                boolean drawChild = super.drawChild(canvas, view, j);
                if (view instanceof ChatMessageCell) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                    ImageReceiver avatarImage = chatMessageCell.getAvatarImage();
                    if (avatarImage != null) {
                        ViewHolder childViewHolder;
                        int i;
                        int top;
                        int height;
                        int top2 = view.getTop();
                        if (chatMessageCell.isPinnedBottom()) {
                            childViewHolder = ChannelAdminLogActivity.this.chatListView.getChildViewHolder(view);
                            if (!(childViewHolder == null || ChannelAdminLogActivity.this.chatListView.findViewHolderForAdapterPosition(childViewHolder.getAdapterPosition() + 1) == null)) {
                                avatarImage.setImageY(-AndroidUtilities.dp(1000.0f));
                                avatarImage.draw(canvas);
                                return drawChild;
                            }
                        }
                        if (chatMessageCell.isPinnedTop()) {
                            childViewHolder = ChannelAdminLogActivity.this.chatListView.getChildViewHolder(view);
                            if (childViewHolder != null) {
                                i = top2;
                                while (true) {
                                    ViewHolder findViewHolderForAdapterPosition = ChannelAdminLogActivity.this.chatListView.findViewHolderForAdapterPosition(childViewHolder.getAdapterPosition() - 1);
                                    if (findViewHolderForAdapterPosition == null) {
                                        break;
                                    }
                                    i = findViewHolderForAdapterPosition.itemView.getTop();
                                    if (!(findViewHolderForAdapterPosition.itemView instanceof ChatMessageCell) || !((ChatMessageCell) findViewHolderForAdapterPosition.itemView).isPinnedTop()) {
                                        break;
                                    }
                                    childViewHolder = findViewHolderForAdapterPosition;
                                }
                                top = view.getTop() + chatMessageCell.getLayoutHeight();
                                height = ChannelAdminLogActivity.this.chatListView.getHeight() - ChannelAdminLogActivity.this.chatListView.getPaddingBottom();
                                if (top <= height) {
                                    height = top;
                                }
                                if (height - AndroidUtilities.dp(48.0f) < i) {
                                    height = AndroidUtilities.dp(48.0f) + i;
                                }
                                avatarImage.setImageY(height - AndroidUtilities.dp(44.0f));
                                avatarImage.draw(canvas);
                            }
                        }
                        i = top2;
                        top = view.getTop() + chatMessageCell.getLayoutHeight();
                        height = ChannelAdminLogActivity.this.chatListView.getHeight() - ChannelAdminLogActivity.this.chatListView.getPaddingBottom();
                        if (top <= height) {
                            height = top;
                        }
                        if (height - AndroidUtilities.dp(48.0f) < i) {
                            height = AndroidUtilities.dp(48.0f) + i;
                        }
                        avatarImage.setImageY(height - AndroidUtilities.dp(44.0f));
                        avatarImage.draw(canvas);
                    }
                }
                return drawChild;
            }
        };
        this.chatListView.setOnItemClickListener(new C41118());
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
        this.chatLayoutManager = new LinearLayoutManager(context) {
            public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
                SmoothScroller linearSmoothScrollerMiddle = new LinearSmoothScrollerMiddle(recyclerView.getContext());
                linearSmoothScrollerMiddle.setTargetPosition(i);
                startSmoothScroll(linearSmoothScrollerMiddle);
            }

            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.chatLayoutManager.setOrientation(1);
        this.chatLayoutManager.setStackFromEnd(true);
        this.chatListView.setLayoutManager(this.chatLayoutManager);
        this.contentView.addView(this.chatListView, LayoutHelper.createFrame(-1, -1.0f));
        this.chatListView.setOnScrollListener(new OnScrollListener() {
            private final int scrollValue = AndroidUtilities.dp(100.0f);
            private float totalDy = BitmapDescriptorFactory.HUE_RED;

            /* renamed from: org.telegram.ui.ChannelAdminLogActivity$10$1 */
            class C40971 extends AnimatorListenerAdapter {
                C40971() {
                }

                public void onAnimationEnd(Animator animator) {
                    if (animator.equals(ChannelAdminLogActivity.this.floatingDateAnimation)) {
                        ChannelAdminLogActivity.this.floatingDateAnimation = null;
                    }
                }
            }

            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 1) {
                    ChannelAdminLogActivity.this.scrollingFloatingDate = true;
                    ChannelAdminLogActivity.this.checkTextureViewPosition = true;
                } else if (i == 0) {
                    ChannelAdminLogActivity.this.scrollingFloatingDate = false;
                    ChannelAdminLogActivity.this.checkTextureViewPosition = false;
                    ChannelAdminLogActivity.this.hideFloatingDateView(true);
                }
            }

            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                ChannelAdminLogActivity.this.chatListView.invalidate();
                if (i2 != 0 && ChannelAdminLogActivity.this.scrollingFloatingDate && !ChannelAdminLogActivity.this.currentFloatingTopIsNotMessage && ChannelAdminLogActivity.this.floatingDateView.getTag() == null) {
                    if (ChannelAdminLogActivity.this.floatingDateAnimation != null) {
                        ChannelAdminLogActivity.this.floatingDateAnimation.cancel();
                    }
                    ChannelAdminLogActivity.this.floatingDateView.setTag(Integer.valueOf(1));
                    ChannelAdminLogActivity.this.floatingDateAnimation = new AnimatorSet();
                    ChannelAdminLogActivity.this.floatingDateAnimation.setDuration(150);
                    AnimatorSet access$2700 = ChannelAdminLogActivity.this.floatingDateAnimation;
                    Animator[] animatorArr = new Animator[1];
                    animatorArr[0] = ObjectAnimator.ofFloat(ChannelAdminLogActivity.this.floatingDateView, "alpha", new float[]{1.0f});
                    access$2700.playTogether(animatorArr);
                    ChannelAdminLogActivity.this.floatingDateAnimation.addListener(new C40971());
                    ChannelAdminLogActivity.this.floatingDateAnimation.start();
                }
                ChannelAdminLogActivity.this.checkScrollForLoad(true);
                ChannelAdminLogActivity.this.updateMessagesVisisblePart();
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
        this.floatingDateView = new ChatActionCell(context);
        this.floatingDateView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.contentView.addView(this.floatingDateView, LayoutHelper.createFrame(-2, -2.0f, 49, BitmapDescriptorFactory.HUE_RED, 4.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.contentView.addView(this.actionBar);
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
        this.contentView.addView(this.bottomOverlayChat, LayoutHelper.createFrame(-1, 51, 80));
        this.bottomOverlayChat.setOnClickListener(new View.OnClickListener() {

            /* renamed from: org.telegram.ui.ChannelAdminLogActivity$12$1 */
            class C40981 implements AdminLogFilterAlertDelegate {
                C40981() {
                }

                public void didSelectRights(TL_channelAdminLogEventsFilter tL_channelAdminLogEventsFilter, HashMap<Integer, User> hashMap) {
                    ChannelAdminLogActivity.this.currentFilter = tL_channelAdminLogEventsFilter;
                    ChannelAdminLogActivity.this.selectedAdmins = hashMap;
                    if (ChannelAdminLogActivity.this.currentFilter == null && ChannelAdminLogActivity.this.selectedAdmins == null) {
                        ChannelAdminLogActivity.this.avatarContainer.setSubtitle(LocaleController.getString("EventLogAllEvents", R.string.EventLogAllEvents));
                    } else {
                        ChannelAdminLogActivity.this.avatarContainer.setSubtitle(LocaleController.getString("EventLogSelectedEvents", R.string.EventLogSelectedEvents));
                    }
                    ChannelAdminLogActivity.this.loadMessages(true);
                }
            }

            public void onClick(View view) {
                if (ChannelAdminLogActivity.this.getParentActivity() != null) {
                    Dialog adminLogFilterAlert = new AdminLogFilterAlert(ChannelAdminLogActivity.this.getParentActivity(), ChannelAdminLogActivity.this.currentFilter, ChannelAdminLogActivity.this.selectedAdmins, ChannelAdminLogActivity.this.currentChat.megagroup);
                    adminLogFilterAlert.setCurrentAdmins(ChannelAdminLogActivity.this.admins);
                    adminLogFilterAlert.setAdminLogFilterAlertDelegate(new C40981());
                    ChannelAdminLogActivity.this.showDialog(adminLogFilterAlert);
                }
            }
        });
        this.bottomOverlayChatText = new TextView(context);
        this.bottomOverlayChatText.setTextSize(1, 15.0f);
        this.bottomOverlayChatText.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.bottomOverlayChatText.setTextColor(Theme.getColor(Theme.key_chat_fieldOverlayText));
        this.bottomOverlayChatText.setText(LocaleController.getString("SETTINGS", R.string.SETTINGS).toUpperCase());
        this.bottomOverlayChat.addView(this.bottomOverlayChatText, LayoutHelper.createFrame(-2, -2, 17));
        this.bottomOverlayImage = new ImageView(context);
        this.bottomOverlayImage.setImageResource(R.drawable.log_info);
        this.bottomOverlayImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_fieldOverlayText), Mode.MULTIPLY));
        this.bottomOverlayImage.setScaleType(ScaleType.CENTER);
        this.bottomOverlayChat.addView(this.bottomOverlayImage, LayoutHelper.createFrame(48, 48.0f, 53, 3.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.bottomOverlayImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChannelAdminLogActivity.this.getParentActivity());
                if (ChannelAdminLogActivity.this.currentChat.megagroup) {
                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString("EventLogInfoDetail", R.string.EventLogInfoDetail)));
                } else {
                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString("EventLogInfoDetailChannel", R.string.EventLogInfoDetailChannel)));
                }
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                builder.setTitle(LocaleController.getString("EventLogInfoTitle", R.string.EventLogInfoTitle));
                ChannelAdminLogActivity.this.showDialog(builder.create());
            }
        });
        this.searchContainer = new FrameLayout(context) {
            public void onDraw(Canvas canvas) {
                int intrinsicHeight = Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                Theme.chat_composeShadowDrawable.setBounds(0, 0, getMeasuredWidth(), intrinsicHeight);
                Theme.chat_composeShadowDrawable.draw(canvas);
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) intrinsicHeight, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
            }
        };
        this.searchContainer.setWillNotDraw(false);
        this.searchContainer.setVisibility(4);
        this.searchContainer.setFocusable(true);
        this.searchContainer.setFocusableInTouchMode(true);
        this.searchContainer.setClickable(true);
        this.searchContainer.setPadding(0, AndroidUtilities.dp(3.0f), 0, 0);
        this.contentView.addView(this.searchContainer, LayoutHelper.createFrame(-1, 51, 80));
        this.searchCalendarButton = new ImageView(context);
        this.searchCalendarButton.setScaleType(ScaleType.CENTER);
        this.searchCalendarButton.setImageResource(R.drawable.search_calendar);
        this.searchCalendarButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_searchPanelIcons), Mode.MULTIPLY));
        this.searchContainer.addView(this.searchCalendarButton, LayoutHelper.createFrame(48, 48, 53));
        this.searchCalendarButton.setOnClickListener(new View.OnClickListener() {

            /* renamed from: org.telegram.ui.ChannelAdminLogActivity$15$1 */
            class C40991 implements OnDateSetListener {
                C40991() {
                }

                public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                    Calendar instance = Calendar.getInstance();
                    instance.clear();
                    instance.set(i, i2, i3);
                    int time = (int) (instance.getTime().getTime() / 1000);
                    ChannelAdminLogActivity.this.loadMessages(true);
                }
            }

            /* renamed from: org.telegram.ui.ChannelAdminLogActivity$15$2 */
            class C41002 implements OnClickListener {
                C41002() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }

            public void onClick(View view) {
                if (ChannelAdminLogActivity.this.getParentActivity() != null) {
                    AndroidUtilities.hideKeyboard(ChannelAdminLogActivity.this.searchItem.getSearchField());
                    Calendar instance = Calendar.getInstance();
                    try {
                        Dialog datePickerDialog = new DatePickerDialog(ChannelAdminLogActivity.this.getParentActivity(), new C40991(), instance.get(1), instance.get(2), instance.get(5));
                        final DatePicker datePicker = datePickerDialog.getDatePicker();
                        datePicker.setMinDate(1375315200000L);
                        datePicker.setMaxDate(System.currentTimeMillis());
                        datePickerDialog.setButton(-1, LocaleController.getString("JumpToDate", R.string.JumpToDate), datePickerDialog);
                        datePickerDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C41002());
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
                        ChannelAdminLogActivity.this.showDialog(datePickerDialog);
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
        this.searchContainer.addView(this.searchCountText, LayoutHelper.createFrame(-1, -2.0f, 19, 108.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.chatAdapter.updateRows();
        if (this.loading && this.messages.isEmpty()) {
            this.progressView.setVisibility(0);
            this.chatListView.setEmptyView(null);
        } else {
            this.progressView.setVisibility(4);
            this.chatListView.setEmptyView(this.emptyViewContainer);
        }
        updateEmptyPlaceholder();
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.emojiDidLoaded) {
            if (this.chatListView != null) {
                this.chatListView.invalidateViews();
            }
        } else if (i == NotificationCenter.messagePlayingDidStarted) {
            if (((MessageObject) objArr[0]).isRoundVideo()) {
                MediaController.getInstance().setTextureView(createTextureView(true), this.aspectRatioFrameLayout, this.roundVideoContainer, true);
                updateTextureViewPosition();
            }
            if (this.chatListView != null) {
                r3 = this.chatListView.getChildCount();
                for (r2 = 0; r2 < r3; r2++) {
                    r0 = this.chatListView.getChildAt(r2);
                    if (r0 instanceof ChatMessageCell) {
                        r0 = (ChatMessageCell) r0;
                        r4 = r0.getMessageObject();
                        if (r4 != null) {
                            if (r4.isVoice() || r4.isMusic()) {
                                r0.updateButtonState(false);
                            } else if (r4.isRoundVideo()) {
                                r0.checkRoundVideoPlayback(false);
                            }
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.messagePlayingDidReset || i == NotificationCenter.messagePlayingPlayStateChanged) {
            if (this.chatListView != null) {
                r3 = this.chatListView.getChildCount();
                for (r2 = 0; r2 < r3; r2++) {
                    r0 = this.chatListView.getChildAt(r2);
                    if (r0 instanceof ChatMessageCell) {
                        r0 = (ChatMessageCell) r0;
                        r4 = r0.getMessageObject();
                        if (r4 != null) {
                            if (r4.isVoice() || r4.isMusic()) {
                                r0.updateButtonState(false);
                            } else if (r4.isRoundVideo() && !MediaController.getInstance().isPlayingMessage(r4)) {
                                r0.checkRoundVideoPlayback(true);
                            }
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.messagePlayingProgressDidChanged) {
            Integer num = (Integer) objArr[0];
            if (this.chatListView != null) {
                r3 = this.chatListView.getChildCount();
                for (r2 = 0; r2 < r3; r2++) {
                    View childAt = this.chatListView.getChildAt(r2);
                    if (childAt instanceof ChatMessageCell) {
                        ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                        r4 = chatMessageCell.getMessageObject();
                        if (r4 != null && r4.getId() == num.intValue()) {
                            MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
                            if (playingMessageObject != null) {
                                r4.audioProgress = playingMessageObject.audioProgress;
                                r4.audioProgressSec = playingMessageObject.audioProgressSec;
                                chatMessageCell.updatePlayingMessageProgress();
                                return;
                            }
                            return;
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.didSetNewWallpapper && this.fragmentView != null) {
            ((SizeNotifierFrameLayout) this.fragmentView).setBackgroundImage(Theme.getCachedWallpaper());
            this.progressView2.getBackground().setColorFilter(Theme.colorFilter);
            if (this.emptyView != null) {
                this.emptyView.getBackground().setColorFilter(Theme.colorFilter);
            }
            this.chatListView.invalidateViews();
        }
    }

    public Chat getCurrentChat() {
        return this.currentChat;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[205];
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
        themeDescriptionArr[13] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        themeDescriptionArr[14] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[15] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[16] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[17] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[18] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[19] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[20] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_backgroundPink);
        themeDescriptionArr[21] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageRed);
        themeDescriptionArr[22] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageOrange);
        themeDescriptionArr[23] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageViolet);
        themeDescriptionArr[24] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageGreen);
        themeDescriptionArr[25] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageCyan);
        themeDescriptionArr[26] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessageBlue);
        themeDescriptionArr[27] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_avatar_nameInMessagePink);
        themeDescriptionArr[28] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInDrawable, Theme.chat_msgInMediaDrawable}, null, Theme.key_chat_inBubble);
        themeDescriptionArr[29] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInSelectedDrawable, Theme.chat_msgInMediaSelectedDrawable}, null, Theme.key_chat_inBubbleSelected);
        themeDescriptionArr[30] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInShadowDrawable, Theme.chat_msgInMediaShadowDrawable}, null, Theme.key_chat_inBubbleShadow);
        themeDescriptionArr[31] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutDrawable, Theme.chat_msgOutMediaDrawable}, null, Theme.key_chat_outBubble);
        themeDescriptionArr[32] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutSelectedDrawable, Theme.chat_msgOutMediaSelectedDrawable}, null, Theme.key_chat_outBubbleSelected);
        themeDescriptionArr[33] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutShadowDrawable, Theme.chat_msgOutMediaShadowDrawable}, null, Theme.key_chat_outBubbleShadow);
        themeDescriptionArr[34] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{ChatActionCell.class}, Theme.chat_actionTextPaint, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[35] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_LINKCOLOR, new Class[]{ChatActionCell.class}, Theme.chat_actionTextPaint, null, null, Theme.key_chat_serviceLink);
        themeDescriptionArr[36] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_shareIconDrawable, Theme.chat_botInlineDrawable, Theme.chat_botLinkDrawalbe, Theme.chat_goIconDrawable}, null, Theme.key_chat_serviceIcon);
        themeDescriptionArr[37] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class, ChatActionCell.class}, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[38] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class, ChatActionCell.class}, null, null, null, Theme.key_chat_serviceBackgroundSelected);
        themeDescriptionArr[39] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_messageTextIn);
        themeDescriptionArr[40] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_messageTextOut);
        themeDescriptionArr[41] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_LINKCOLOR, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_messageLinkIn, null);
        themeDescriptionArr[42] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_LINKCOLOR, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_messageLinkOut, null);
        themeDescriptionArr[43] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutCheckDrawable, Theme.chat_msgOutHalfCheckDrawable}, null, Theme.key_chat_outSentCheck);
        themeDescriptionArr[44] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutCheckSelectedDrawable, Theme.chat_msgOutHalfCheckSelectedDrawable}, null, Theme.key_chat_outSentCheckSelected);
        themeDescriptionArr[45] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutClockDrawable}, null, Theme.key_chat_outSentClock);
        themeDescriptionArr[46] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutSelectedClockDrawable}, null, Theme.key_chat_outSentClockSelected);
        themeDescriptionArr[47] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInClockDrawable}, null, Theme.key_chat_inSentClock);
        themeDescriptionArr[48] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInSelectedClockDrawable}, null, Theme.key_chat_inSentClockSelected);
        themeDescriptionArr[49] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgMediaCheckDrawable, Theme.chat_msgMediaHalfCheckDrawable}, null, Theme.key_chat_mediaSentCheck);
        themeDescriptionArr[50] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgStickerHalfCheckDrawable, Theme.chat_msgStickerCheckDrawable, Theme.chat_msgStickerClockDrawable, Theme.chat_msgStickerViewsDrawable}, null, Theme.key_chat_serviceText);
        themeDescriptionArr[51] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgMediaClockDrawable}, null, Theme.key_chat_mediaSentClock);
        themeDescriptionArr[52] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutViewsDrawable}, null, Theme.key_chat_outViews);
        themeDescriptionArr[53] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutViewsSelectedDrawable}, null, Theme.key_chat_outViewsSelected);
        themeDescriptionArr[54] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInViewsDrawable}, null, Theme.key_chat_inViews);
        themeDescriptionArr[55] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInViewsSelectedDrawable}, null, Theme.key_chat_inViewsSelected);
        themeDescriptionArr[56] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgMediaViewsDrawable}, null, Theme.key_chat_mediaViews);
        themeDescriptionArr[57] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutMenuDrawable}, null, Theme.key_chat_outMenu);
        themeDescriptionArr[58] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutMenuSelectedDrawable}, null, Theme.key_chat_outMenuSelected);
        themeDescriptionArr[59] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInMenuDrawable}, null, Theme.key_chat_inMenu);
        themeDescriptionArr[60] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInMenuSelectedDrawable}, null, Theme.key_chat_inMenuSelected);
        themeDescriptionArr[61] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgMediaMenuDrawable}, null, Theme.key_chat_mediaMenu);
        themeDescriptionArr[62] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutInstantDrawable, Theme.chat_msgOutCallDrawable}, null, Theme.key_chat_outInstant);
        themeDescriptionArr[63] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgOutCallSelectedDrawable}, null, Theme.key_chat_outInstantSelected);
        themeDescriptionArr[64] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInInstantDrawable, Theme.chat_msgInCallDrawable}, null, Theme.key_chat_inInstant);
        themeDescriptionArr[65] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgInCallSelectedDrawable}, null, Theme.key_chat_inInstantSelected);
        themeDescriptionArr[66] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgCallUpRedDrawable, Theme.chat_msgCallDownRedDrawable}, null, Theme.key_calls_callReceivedRedIcon);
        themeDescriptionArr[67] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgCallUpGreenDrawable, Theme.chat_msgCallDownGreenDrawable}, null, Theme.key_calls_callReceivedGreenIcon);
        themeDescriptionArr[68] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_msgErrorPaint, null, null, Theme.key_chat_sentError);
        themeDescriptionArr[69] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_msgErrorDrawable}, null, Theme.key_chat_sentErrorIcon);
        themeDescriptionArr[70] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_durationPaint, null, null, Theme.key_chat_previewDurationText);
        themeDescriptionArr[71] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_gamePaint, null, null, Theme.key_chat_previewGameText);
        themeDescriptionArr[72] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inPreviewInstantText);
        themeDescriptionArr[73] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outPreviewInstantText);
        themeDescriptionArr[74] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inPreviewInstantSelectedText);
        themeDescriptionArr[75] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outPreviewInstantSelectedText);
        themeDescriptionArr[76] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_deleteProgressPaint, null, null, Theme.key_chat_secretTimeText);
        themeDescriptionArr[77] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_stickerNameText);
        themeDescriptionArr[78] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_botButtonPaint, null, null, Theme.key_chat_botButtonText);
        themeDescriptionArr[79] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_botProgressPaint, null, null, Theme.key_chat_botProgress);
        themeDescriptionArr[80] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inForwardedNameText);
        themeDescriptionArr[81] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outForwardedNameText);
        themeDescriptionArr[82] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inViaBotNameText);
        themeDescriptionArr[83] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outViaBotNameText);
        themeDescriptionArr[84] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_stickerViaBotNameText);
        themeDescriptionArr[85] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inReplyLine);
        themeDescriptionArr[86] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outReplyLine);
        themeDescriptionArr[87] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_stickerReplyLine);
        themeDescriptionArr[88] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inReplyNameText);
        themeDescriptionArr[89] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outReplyNameText);
        themeDescriptionArr[90] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_stickerReplyNameText);
        themeDescriptionArr[91] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inReplyMessageText);
        themeDescriptionArr[92] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outReplyMessageText);
        themeDescriptionArr[93] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inReplyMediaMessageText);
        themeDescriptionArr[94] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outReplyMediaMessageText);
        themeDescriptionArr[95] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inReplyMediaMessageSelectedText);
        themeDescriptionArr[96] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outReplyMediaMessageSelectedText);
        themeDescriptionArr[97] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_stickerReplyMessageText);
        themeDescriptionArr[98] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inPreviewLine);
        themeDescriptionArr[99] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outPreviewLine);
        themeDescriptionArr[100] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inSiteNameText);
        themeDescriptionArr[101] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outSiteNameText);
        themeDescriptionArr[102] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inContactNameText);
        themeDescriptionArr[103] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outContactNameText);
        themeDescriptionArr[104] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inContactPhoneText);
        themeDescriptionArr[105] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outContactPhoneText);
        themeDescriptionArr[106] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_mediaProgress);
        themeDescriptionArr[107] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioProgress);
        themeDescriptionArr[108] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioProgress);
        themeDescriptionArr[109] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioSelectedProgress);
        themeDescriptionArr[110] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioSelectedProgress);
        themeDescriptionArr[111] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_mediaTimeText);
        themeDescriptionArr[112] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inTimeText);
        themeDescriptionArr[113] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outTimeText);
        themeDescriptionArr[114] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inTimeSelectedText);
        themeDescriptionArr[115] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outTimeSelectedText);
        themeDescriptionArr[116] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioPerfomerText);
        themeDescriptionArr[117] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioPerfomerText);
        themeDescriptionArr[118] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioTitleText);
        themeDescriptionArr[119] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioTitleText);
        themeDescriptionArr[120] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioDurationText);
        themeDescriptionArr[121] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioDurationText);
        themeDescriptionArr[122] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioDurationSelectedText);
        themeDescriptionArr[123] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioDurationSelectedText);
        themeDescriptionArr[124] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioSeekbar);
        themeDescriptionArr[125] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioSeekbar);
        themeDescriptionArr[126] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioSeekbarSelected);
        themeDescriptionArr[127] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioSeekbarSelected);
        themeDescriptionArr[128] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inAudioSeekbarFill);
        themeDescriptionArr[129] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outAudioSeekbarFill);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_HDMV_DTS] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVoiceSeekbar);
        themeDescriptionArr[131] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVoiceSeekbar);
        themeDescriptionArr[132] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVoiceSeekbarSelected);
        themeDescriptionArr[133] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVoiceSeekbarSelected);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_SPLICE_INFO] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVoiceSeekbarFill);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_E_AC3] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVoiceSeekbarFill);
        themeDescriptionArr[136] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileProgress);
        themeDescriptionArr[137] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileProgress);
        themeDescriptionArr[TsExtractor.TS_STREAM_TYPE_DTS] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileProgressSelected);
        themeDescriptionArr[139] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileProgressSelected);
        themeDescriptionArr[140] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileNameText);
        themeDescriptionArr[141] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileNameText);
        themeDescriptionArr[BuildConfig.VERSION_CODE] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileInfoText);
        themeDescriptionArr[143] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileInfoText);
        themeDescriptionArr[144] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileInfoSelectedText);
        themeDescriptionArr[145] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileInfoSelectedText);
        themeDescriptionArr[146] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileBackground);
        themeDescriptionArr[147] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileBackground);
        themeDescriptionArr[148] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inFileBackgroundSelected);
        themeDescriptionArr[149] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outFileBackgroundSelected);
        themeDescriptionArr[150] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVenueNameText);
        themeDescriptionArr[151] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVenueNameText);
        themeDescriptionArr[152] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVenueInfoText);
        themeDescriptionArr[153] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVenueInfoText);
        themeDescriptionArr[154] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_inVenueInfoSelectedText);
        themeDescriptionArr[155] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_outVenueInfoSelectedText);
        themeDescriptionArr[156] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, null, null, Theme.key_chat_mediaInfoText);
        themeDescriptionArr[157] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_urlPaint, null, null, Theme.key_chat_linkSelectBackground);
        themeDescriptionArr[158] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, Theme.chat_textSearchSelectionPaint, null, null, Theme.key_chat_textSelectBackground);
        themeDescriptionArr[159] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[0][0], Theme.chat_fileStatesDrawable[1][0], Theme.chat_fileStatesDrawable[2][0], Theme.chat_fileStatesDrawable[3][0], Theme.chat_fileStatesDrawable[4][0]}, null, Theme.key_chat_outLoader);
        themeDescriptionArr[160] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[0][0], Theme.chat_fileStatesDrawable[1][0], Theme.chat_fileStatesDrawable[2][0], Theme.chat_fileStatesDrawable[3][0], Theme.chat_fileStatesDrawable[4][0]}, null, Theme.key_chat_outBubble);
        themeDescriptionArr[161] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[0][1], Theme.chat_fileStatesDrawable[1][1], Theme.chat_fileStatesDrawable[2][1], Theme.chat_fileStatesDrawable[3][1], Theme.chat_fileStatesDrawable[4][1]}, null, Theme.key_chat_outLoaderSelected);
        themeDescriptionArr[162] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[0][1], Theme.chat_fileStatesDrawable[1][1], Theme.chat_fileStatesDrawable[2][1], Theme.chat_fileStatesDrawable[3][1], Theme.chat_fileStatesDrawable[4][1]}, null, Theme.key_chat_outBubbleSelected);
        themeDescriptionArr[163] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[5][0], Theme.chat_fileStatesDrawable[6][0], Theme.chat_fileStatesDrawable[7][0], Theme.chat_fileStatesDrawable[8][0], Theme.chat_fileStatesDrawable[9][0]}, null, Theme.key_chat_inLoader);
        themeDescriptionArr[164] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[5][0], Theme.chat_fileStatesDrawable[6][0], Theme.chat_fileStatesDrawable[7][0], Theme.chat_fileStatesDrawable[8][0], Theme.chat_fileStatesDrawable[9][0]}, null, Theme.key_chat_inBubble);
        themeDescriptionArr[165] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[5][1], Theme.chat_fileStatesDrawable[6][1], Theme.chat_fileStatesDrawable[7][1], Theme.chat_fileStatesDrawable[8][1], Theme.chat_fileStatesDrawable[9][1]}, null, Theme.key_chat_inLoaderSelected);
        themeDescriptionArr[166] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_fileStatesDrawable[5][1], Theme.chat_fileStatesDrawable[6][1], Theme.chat_fileStatesDrawable[7][1], Theme.chat_fileStatesDrawable[8][1], Theme.chat_fileStatesDrawable[9][1]}, null, Theme.key_chat_inBubbleSelected);
        themeDescriptionArr[167] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[0][0], Theme.chat_photoStatesDrawables[1][0], Theme.chat_photoStatesDrawables[2][0], Theme.chat_photoStatesDrawables[3][0]}, null, Theme.key_chat_mediaLoaderPhoto);
        themeDescriptionArr[168] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[0][0], Theme.chat_photoStatesDrawables[1][0], Theme.chat_photoStatesDrawables[2][0], Theme.chat_photoStatesDrawables[3][0]}, null, Theme.key_chat_mediaLoaderPhotoIcon);
        themeDescriptionArr[169] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[0][1], Theme.chat_photoStatesDrawables[1][1], Theme.chat_photoStatesDrawables[2][1], Theme.chat_photoStatesDrawables[3][1]}, null, Theme.key_chat_mediaLoaderPhotoSelected);
        themeDescriptionArr[170] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[0][1], Theme.chat_photoStatesDrawables[1][1], Theme.chat_photoStatesDrawables[2][1], Theme.chat_photoStatesDrawables[3][1]}, null, Theme.key_chat_mediaLoaderPhotoIconSelected);
        themeDescriptionArr[171] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[7][0], Theme.chat_photoStatesDrawables[8][0]}, null, Theme.key_chat_outLoaderPhoto);
        themeDescriptionArr[172] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[7][0], Theme.chat_photoStatesDrawables[8][0]}, null, Theme.key_chat_outLoaderPhotoIcon);
        themeDescriptionArr[173] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[7][1], Theme.chat_photoStatesDrawables[8][1]}, null, Theme.key_chat_outLoaderPhotoSelected);
        themeDescriptionArr[174] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[7][1], Theme.chat_photoStatesDrawables[8][1]}, null, Theme.key_chat_outLoaderPhotoIconSelected);
        themeDescriptionArr[175] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[10][0], Theme.chat_photoStatesDrawables[11][0]}, null, Theme.key_chat_inLoaderPhoto);
        themeDescriptionArr[176] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[10][0], Theme.chat_photoStatesDrawables[11][0]}, null, Theme.key_chat_inLoaderPhotoIcon);
        themeDescriptionArr[177] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[10][1], Theme.chat_photoStatesDrawables[11][1]}, null, Theme.key_chat_inLoaderPhotoSelected);
        themeDescriptionArr[178] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[10][1], Theme.chat_photoStatesDrawables[11][1]}, null, Theme.key_chat_inLoaderPhotoIconSelected);
        themeDescriptionArr[179] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[9][0]}, null, Theme.key_chat_outFileIcon);
        themeDescriptionArr[180] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[9][1]}, null, Theme.key_chat_outFileSelectedIcon);
        themeDescriptionArr[181] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[12][0]}, null, Theme.key_chat_inFileIcon);
        themeDescriptionArr[182] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_photoStatesDrawables[12][1]}, null, Theme.key_chat_inFileSelectedIcon);
        themeDescriptionArr[183] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_contactDrawable[0]}, null, Theme.key_chat_inContactBackground);
        themeDescriptionArr[184] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_contactDrawable[0]}, null, Theme.key_chat_inContactIcon);
        themeDescriptionArr[185] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_contactDrawable[1]}, null, Theme.key_chat_outContactBackground);
        themeDescriptionArr[186] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_contactDrawable[1]}, null, Theme.key_chat_outContactIcon);
        themeDescriptionArr[187] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_locationDrawable[0]}, null, Theme.key_chat_inLocationBackground);
        themeDescriptionArr[188] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_locationDrawable[0]}, null, Theme.key_chat_inLocationIcon);
        themeDescriptionArr[PsExtractor.PRIVATE_STREAM_1] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_locationDrawable[1]}, null, Theme.key_chat_outLocationBackground);
        themeDescriptionArr[190] = new ThemeDescription(this.chatListView, 0, new Class[]{ChatMessageCell.class}, null, new Drawable[]{Theme.chat_locationDrawable[1]}, null, Theme.key_chat_outLocationIcon);
        themeDescriptionArr[191] = new ThemeDescription(this.bottomOverlayChat, 0, null, Theme.chat_composeBackgroundPaint, null, null, Theme.key_chat_messagePanelBackground);
        themeDescriptionArr[PsExtractor.AUDIO_STREAM] = new ThemeDescription(this.bottomOverlayChat, 0, null, null, new Drawable[]{Theme.chat_composeShadowDrawable}, null, Theme.key_chat_messagePanelShadow);
        themeDescriptionArr[193] = new ThemeDescription(this.bottomOverlayChatText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_fieldOverlayText);
        themeDescriptionArr[194] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[195] = new ThemeDescription(this.progressBar, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[196] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE, new Class[]{ChatUnreadCell.class}, new String[]{"backgroundLayout"}, null, null, null, Theme.key_chat_unreadMessagesStartBackground);
        themeDescriptionArr[197] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{ChatUnreadCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_chat_unreadMessagesStartArrowIcon);
        themeDescriptionArr[198] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{ChatUnreadCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chat_unreadMessagesStartText);
        themeDescriptionArr[199] = new ThemeDescription(this.progressView2, ThemeDescription.FLAG_SERVICEBACKGROUND, null, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[Callback.DEFAULT_DRAG_ANIMATION_DURATION] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_SERVICEBACKGROUND, null, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[201] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_SERVICEBACKGROUND, new Class[]{ChatLoadingCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chat_serviceBackground);
        themeDescriptionArr[202] = new ThemeDescription(this.chatListView, ThemeDescription.FLAG_PROGRESSBAR, new Class[]{ChatLoadingCell.class}, new String[]{"textView"}, null, null, null, Theme.key_chat_serviceText);
        themeDescriptionArr[203] = new ThemeDescription(this.avatarContainer.getTimeItem(), 0, null, null, null, null, Theme.key_chat_secretTimerBackground);
        themeDescriptionArr[204] = new ThemeDescription(this.avatarContainer.getTimeItem(), 0, null, null, null, null, Theme.key_chat_secretTimerText);
        return themeDescriptionArr;
    }

    public void onConfigurationChanged(Configuration configuration) {
        fixLayout();
        if (this.visibleDialog instanceof DatePickerDialog) {
            this.visibleDialog.dismiss();
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidStarted);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didSetNewWallpapper);
        loadMessages(true);
        loadAdmins();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidStarted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didSetNewWallpapper);
    }

    public void onPause() {
        super.onPause();
        this.paused = true;
        this.wasPaused = true;
    }

    public void onResume() {
        super.onResume();
        this.paused = false;
        checkScrollForLoad(false);
        if (this.wasPaused) {
            this.wasPaused = false;
            if (this.chatAdapter != null) {
                this.chatAdapter.notifyDataSetChanged();
            }
        }
        fixLayout();
    }

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        NotificationCenter.getInstance().setAnimationInProgress(false);
        if (z) {
            this.openAnimationEnded = true;
        }
    }

    public void onTransitionAnimationStart(boolean z, boolean z2) {
        NotificationCenter.getInstance().setAllowedNotificationsDutingAnimation(new int[]{NotificationCenter.chatInfoDidLoaded, NotificationCenter.dialogsNeedReload, NotificationCenter.closeChats, NotificationCenter.messagesDidLoaded, NotificationCenter.botKeyboardDidLoaded});
        NotificationCenter.getInstance().setAnimationInProgress(true);
        if (z) {
            this.openAnimationEnded = false;
        }
    }

    public void showOpenUrlAlert(final String str, boolean z) {
        if (Browser.isInternalUrl(str, null) || !z) {
            Browser.openUrl(getParentActivity(), str, true);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
        builder.setMessage(LocaleController.formatString("OpenUrlAlert", R.string.OpenUrlAlert, new Object[]{str}));
        builder.setPositiveButton(LocaleController.getString("Open", R.string.Open), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Browser.openUrl(ChannelAdminLogActivity.this.getParentActivity(), str, true);
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
    }
}
