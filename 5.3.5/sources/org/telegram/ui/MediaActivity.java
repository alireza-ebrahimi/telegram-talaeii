package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.query.SharedMediaQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterDocument;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterMusic;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterUrl;
import org.telegram.tgnet.TLRPC$TL_messages_search;
import org.telegram.tgnet.TLRPC$TL_webPageEmpty;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.ActionBarPopupWindow.ActionBarPopupWindowLayout;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.Theme.ThemeInfo;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.LoadingCell;
import org.telegram.ui.Cells.SharedDocumentCell;
import org.telegram.ui.Cells.SharedLinkCell;
import org.telegram.ui.Cells.SharedLinkCell.SharedLinkCellDelegate;
import org.telegram.ui.Cells.SharedMediaSectionCell;
import org.telegram.ui.Cells.SharedPhotoVideoCell;
import org.telegram.ui.Cells.SharedPhotoVideoCell.SharedPhotoVideoCellDelegate;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.EmbedBottomSheet;
import org.telegram.ui.Components.FragmentContextView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.NumberTextView;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SectionsAdapter;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.DialogsActivity.DialogsActivityDelegate;
import org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PlaceProviderObject;

public class MediaActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int delete = 4;
    private static final int files_item = 2;
    private static final int forward = 3;
    private static final int links_item = 5;
    private static final int music_item = 6;
    private static final int shared_media_item = 1;
    private ArrayList<View> actionModeViews = new ArrayList();
    private SharedDocumentsAdapter audioAdapter;
    private MediaSearchAdapter audioSearchAdapter;
    private int cantDeleteMessagesCount;
    private ArrayList<SharedPhotoVideoCell> cellCache = new ArrayList(6);
    private int columnsCount = 4;
    private long dialog_id;
    private SharedDocumentsAdapter documentsAdapter;
    private MediaSearchAdapter documentsSearchAdapter;
    private TextView dropDown;
    private ActionBarMenuItem dropDownContainer;
    private Drawable dropDownDrawable;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private LinearLayout emptyView;
    private FragmentContextView fragmentContextView;
    protected TLRPC$ChatFull info = null;
    private LinearLayoutManager layoutManager;
    private SharedLinksAdapter linksAdapter;
    private MediaSearchAdapter linksSearchAdapter;
    private RecyclerListView listView;
    private long mergeDialogId;
    private SharedPhotoVideoAdapter photoVideoAdapter;
    private ActionBarPopupWindowLayout popupLayout;
    private RadialProgressView progressBar;
    private LinearLayout progressView;
    private PhotoViewerProvider provider = new C30981();
    private boolean scrolling;
    private ActionBarMenuItem searchItem;
    private boolean searchWas;
    private boolean searching;
    private HashMap<Integer, MessageObject>[] selectedFiles = new HashMap[]{new HashMap(), new HashMap()};
    private NumberTextView selectedMessagesCountTextView;
    private int selectedMode;
    private SharedMediaData[] sharedMediaData = new SharedMediaData[5];

    /* renamed from: org.telegram.ui.MediaActivity$1 */
    class C30981 extends EmptyPhotoViewerProvider {
        C30981() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
            if (messageObject == null || MediaActivity.this.listView == null || MediaActivity.this.selectedMode != 0) {
                return null;
            }
            int count = MediaActivity.this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View view = MediaActivity.this.listView.getChildAt(a);
                if (view instanceof SharedPhotoVideoCell) {
                    SharedPhotoVideoCell cell = (SharedPhotoVideoCell) view;
                    for (int i = 0; i < 6; i++) {
                        MessageObject message = cell.getMessageObject(i);
                        if (message == null) {
                            continue;
                            break;
                        }
                        BackupImageView imageView = cell.getImageView(i);
                        if (message.getId() == messageObject.getId()) {
                            int[] coords = new int[2];
                            imageView.getLocationInWindow(coords);
                            PlaceProviderObject object = new PlaceProviderObject();
                            object.viewX = coords[0];
                            object.viewY = coords[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight);
                            object.parentView = MediaActivity.this.listView;
                            object.imageReceiver = imageView.getImageReceiver();
                            object.thumb = object.imageReceiver.getBitmap();
                            object.parentView.getLocationInWindow(coords);
                            object.clipTopAddition = AndroidUtilities.dp(40.0f);
                            return object;
                        }
                    }
                    continue;
                }
            }
            return null;
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$2 */
    class C31022 extends ActionBarMenuOnItemClick {

        /* renamed from: org.telegram.ui.MediaActivity$2$3 */
        class C31013 implements DialogsActivityDelegate {
            C31013() {
            }

            public void didSelectDialogs(DialogsActivity fragment, ArrayList<Long> dids, CharSequence message, boolean param) {
                int a;
                ArrayList<MessageObject> fmessages = new ArrayList();
                for (a = 1; a >= 0; a--) {
                    ArrayList<Integer> arrayList = new ArrayList(MediaActivity.this.selectedFiles[a].keySet());
                    Collections.sort(arrayList);
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        Integer id = (Integer) it.next();
                        if (id.intValue() > 0) {
                            fmessages.add(MediaActivity.this.selectedFiles[a].get(id));
                        }
                    }
                    MediaActivity.this.selectedFiles[a].clear();
                }
                MediaActivity.this.cantDeleteMessagesCount = 0;
                MediaActivity.this.actionBar.hideActionMode();
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
                int lower_part = (int) did;
                int high_part = (int) (did >> 32);
                Bundle args = new Bundle();
                args.putBoolean("scrollToTopOnResume", true);
                if (lower_part == 0) {
                    args.putInt("enc_id", high_part);
                } else if (lower_part > 0) {
                    args.putInt("user_id", lower_part);
                } else if (lower_part < 0) {
                    args.putInt("chat_id", -lower_part);
                }
                if (lower_part == 0 || MessagesController.checkCanOpenChat(args, fragment)) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                    ChatActivity chatActivity = new ChatActivity(args);
                    MediaActivity.this.presentFragment(chatActivity, true);
                    chatActivity.showReplyPanel(true, null, fmessages, null, false);
                    if (!AndroidUtilities.isTablet()) {
                        MediaActivity.this.removeSelfFromStack();
                    }
                }
            }
        }

        C31022() {
        }

        public void onItemClick(int id) {
            int a;
            if (id == -1) {
                if (MediaActivity.this.actionBar.isActionModeShowed()) {
                    for (a = 1; a >= 0; a--) {
                        MediaActivity.this.selectedFiles[a].clear();
                    }
                    MediaActivity.this.cantDeleteMessagesCount = 0;
                    MediaActivity.this.actionBar.hideActionMode();
                    int count = MediaActivity.this.listView.getChildCount();
                    for (a = 0; a < count; a++) {
                        View child = MediaActivity.this.listView.getChildAt(a);
                        if (child instanceof SharedDocumentCell) {
                            ((SharedDocumentCell) child).setChecked(false, true);
                        } else if (child instanceof SharedPhotoVideoCell) {
                            for (int b = 0; b < 6; b++) {
                                ((SharedPhotoVideoCell) child).setChecked(b, false, true);
                            }
                        } else if (child instanceof SharedLinkCell) {
                            ((SharedLinkCell) child).setChecked(false, true);
                        }
                    }
                    return;
                }
                MediaActivity.this.finishFragment();
            } else if (id == 1) {
                if (MediaActivity.this.selectedMode != 0) {
                    MediaActivity.this.selectedMode = 0;
                    MediaActivity.this.switchToCurrentSelectedMode();
                }
            } else if (id == 2) {
                if (MediaActivity.this.selectedMode != 1) {
                    MediaActivity.this.selectedMode = 1;
                    MediaActivity.this.switchToCurrentSelectedMode();
                }
            } else if (id == 5) {
                if (MediaActivity.this.selectedMode != 3) {
                    MediaActivity.this.selectedMode = 3;
                    MediaActivity.this.switchToCurrentSelectedMode();
                }
            } else if (id == 6) {
                if (MediaActivity.this.selectedMode != 4) {
                    MediaActivity.this.selectedMode = 4;
                    MediaActivity.this.switchToCurrentSelectedMode();
                }
            } else if (id == 4) {
                if (MediaActivity.this.getParentActivity() != null) {
                    final boolean[] zArr;
                    Builder builder = new Builder(MediaActivity.this.getParentActivity());
                    builder.setMessage(LocaleController.formatString("AreYouSureDeleteMessages", R.string.AreYouSureDeleteMessages, new Object[]{LocaleController.formatPluralString("items", MediaActivity.this.selectedFiles[0].size() + MediaActivity.this.selectedFiles[1].size())}));
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    boolean[] deleteForAll = new boolean[1];
                    int lower_id = (int) MediaActivity.this.dialog_id;
                    if (lower_id != 0) {
                        User currentUser;
                        TLRPC$Chat currentChat;
                        if (lower_id > 0) {
                            currentUser = MessagesController.getInstance().getUser(Integer.valueOf(lower_id));
                            currentChat = null;
                        } else {
                            currentUser = null;
                            currentChat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
                        }
                        if (!(currentUser == null && ChatObject.isChannel(currentChat))) {
                            int currentDate = ConnectionsManager.getInstance().getCurrentTime();
                            if (!((currentUser == null || currentUser.id == UserConfig.getClientUserId()) && currentChat == null)) {
                                boolean hasOutgoing = false;
                                for (a = 1; a >= 0; a--) {
                                    for (Entry<Integer, MessageObject> entry : MediaActivity.this.selectedFiles[a].entrySet()) {
                                        MessageObject msg = (MessageObject) entry.getValue();
                                        if (msg.messageOwner.action == null) {
                                            if (!msg.isOut()) {
                                                hasOutgoing = false;
                                                break;
                                            } else if (currentDate - msg.messageOwner.date <= 172800) {
                                                hasOutgoing = true;
                                            }
                                        }
                                    }
                                    if (hasOutgoing) {
                                        break;
                                    }
                                }
                                if (hasOutgoing) {
                                    int dp;
                                    int dp2;
                                    View frameLayout = new FrameLayout(MediaActivity.this.getParentActivity());
                                    CheckBoxCell cell = new CheckBoxCell(MediaActivity.this.getParentActivity(), true);
                                    cell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                                    if (currentChat != null) {
                                        cell.setText(LocaleController.getString("DeleteForAll", R.string.DeleteForAll), "", false, false);
                                    } else {
                                        cell.setText(LocaleController.formatString("DeleteForUser", R.string.DeleteForUser, new Object[]{UserObject.getFirstName(currentUser)}), "", false, false);
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
                                    cell.setOnClickListener(new OnClickListener() {
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
                        }
                    }
                    zArr = deleteForAll;
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (int a = 1; a >= 0; a--) {
                                MessageObject msg;
                                ArrayList<Integer> ids = new ArrayList(MediaActivity.this.selectedFiles[a].keySet());
                                ArrayList<Long> random_ids = null;
                                TLRPC$EncryptedChat currentEncryptedChat = null;
                                int channelId = 0;
                                if (!ids.isEmpty()) {
                                    msg = (MessageObject) MediaActivity.this.selectedFiles[a].get(ids.get(0));
                                    if (null == null && msg.messageOwner.to_id.channel_id != 0) {
                                        channelId = msg.messageOwner.to_id.channel_id;
                                    }
                                }
                                if (((int) MediaActivity.this.dialog_id) == 0) {
                                    currentEncryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (MediaActivity.this.dialog_id >> 32)));
                                }
                                if (currentEncryptedChat != null) {
                                    random_ids = new ArrayList();
                                    for (Entry<Integer, MessageObject> entry : MediaActivity.this.selectedFiles[a].entrySet()) {
                                        msg = (MessageObject) entry.getValue();
                                        if (!(msg.messageOwner.random_id == 0 || msg.type == 10)) {
                                            random_ids.add(Long.valueOf(msg.messageOwner.random_id));
                                        }
                                    }
                                }
                                MessagesController.getInstance().deleteMessages(ids, random_ids, currentEncryptedChat, channelId, zArr[0]);
                                MediaActivity.this.selectedFiles[a].clear();
                            }
                            MediaActivity.this.actionBar.hideActionMode();
                            MediaActivity.this.actionBar.closeSearchField();
                            MediaActivity.this.cantDeleteMessagesCount = 0;
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    MediaActivity.this.showDialog(builder.create());
                }
            } else if (id == 3) {
                Bundle args = new Bundle();
                args.putBoolean("onlySelect", true);
                args.putInt("dialogsType", 3);
                BaseFragment dialogsActivity = new DialogsActivity(args);
                dialogsActivity.setDelegate(new C31013());
                MediaActivity.this.presentFragment(dialogsActivity);
            }
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$3 */
    class C31033 extends ActionBarMenuItemSearchListener {
        C31033() {
        }

        public void onSearchExpand() {
            MediaActivity.this.dropDownContainer.setVisibility(8);
            MediaActivity.this.searching = true;
        }

        public void onSearchCollapse() {
            MediaActivity.this.dropDownContainer.setVisibility(0);
            if (MediaActivity.this.selectedMode == 1) {
                MediaActivity.this.documentsSearchAdapter.search(null);
            } else if (MediaActivity.this.selectedMode == 3) {
                MediaActivity.this.linksSearchAdapter.search(null);
            } else if (MediaActivity.this.selectedMode == 4) {
                MediaActivity.this.audioSearchAdapter.search(null);
            }
            MediaActivity.this.searching = false;
            MediaActivity.this.searchWas = false;
            MediaActivity.this.switchToCurrentSelectedMode();
        }

        public void onTextChanged(EditText editText) {
            String text = editText.getText().toString();
            if (text.length() != 0) {
                MediaActivity.this.searchWas = true;
                MediaActivity.this.switchToCurrentSelectedMode();
            }
            if (MediaActivity.this.selectedMode == 1) {
                if (MediaActivity.this.documentsSearchAdapter != null) {
                    MediaActivity.this.documentsSearchAdapter.search(text);
                }
            } else if (MediaActivity.this.selectedMode == 3) {
                if (MediaActivity.this.linksSearchAdapter != null) {
                    MediaActivity.this.linksSearchAdapter.search(text);
                }
            } else if (MediaActivity.this.selectedMode == 4 && MediaActivity.this.audioSearchAdapter != null) {
                MediaActivity.this.audioSearchAdapter.search(text);
            }
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$4 */
    class C31044 implements OnClickListener {
        C31044() {
        }

        public void onClick(View view) {
            MediaActivity.this.dropDownContainer.toggleSubMenu();
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$5 */
    class C31055 implements OnTouchListener {
        C31055() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$6 */
    class C31066 implements OnItemClickListener {
        C31066() {
        }

        public void onItemClick(View view, int position) {
            if ((MediaActivity.this.selectedMode == 1 || MediaActivity.this.selectedMode == 4) && (view instanceof SharedDocumentCell)) {
                MediaActivity.this.onItemClick(position, view, ((SharedDocumentCell) view).getMessage(), 0);
            } else if (MediaActivity.this.selectedMode == 3 && (view instanceof SharedLinkCell)) {
                MediaActivity.this.onItemClick(position, view, ((SharedLinkCell) view).getMessage(), 0);
            }
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$7 */
    class C31077 extends OnScrollListener {
        C31077() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            boolean z = true;
            if (newState == 1 && MediaActivity.this.searching && MediaActivity.this.searchWas) {
                AndroidUtilities.hideKeyboard(MediaActivity.this.getParentActivity().getCurrentFocus());
            }
            MediaActivity mediaActivity = MediaActivity.this;
            if (newState == 0) {
                z = false;
            }
            mediaActivity.scrolling = z;
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!MediaActivity.this.searching || !MediaActivity.this.searchWas) {
                int firstVisibleItem = MediaActivity.this.layoutManager.findFirstVisibleItemPosition();
                int visibleItemCount = firstVisibleItem == -1 ? 0 : Math.abs(MediaActivity.this.layoutManager.findLastVisibleItemPosition() - firstVisibleItem) + 1;
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                if (visibleItemCount != 0 && firstVisibleItem + visibleItemCount > totalItemCount - 2 && !MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].loading) {
                    int type;
                    if (MediaActivity.this.selectedMode == 0) {
                        type = 0;
                    } else if (MediaActivity.this.selectedMode == 1) {
                        type = 1;
                    } else if (MediaActivity.this.selectedMode == 2) {
                        type = 2;
                    } else if (MediaActivity.this.selectedMode == 4) {
                        type = 4;
                    } else {
                        type = 3;
                    }
                    if (!MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].endReached[0]) {
                        MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].loading = true;
                        SharedMediaQuery.loadMedia(MediaActivity.this.dialog_id, 50, MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].max_id[0], type, true, MediaActivity.this.classGuid);
                    } else if (MediaActivity.this.mergeDialogId != 0 && !MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].endReached[1]) {
                        MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].loading = true;
                        SharedMediaQuery.loadMedia(MediaActivity.this.mergeDialogId, 50, MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].max_id[1], type, true, MediaActivity.this.classGuid);
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$8 */
    class C31088 implements OnItemLongClickListener {
        C31088() {
        }

        public boolean onItemClick(View view, int position) {
            if ((MediaActivity.this.selectedMode == 1 || MediaActivity.this.selectedMode == 4) && (view instanceof SharedDocumentCell)) {
                return MediaActivity.this.onItemLongClick(((SharedDocumentCell) view).getMessage(), view, 0);
            } else if (MediaActivity.this.selectedMode != 3 || !(view instanceof SharedLinkCell)) {
                return false;
            } else {
                return MediaActivity.this.onItemLongClick(((SharedLinkCell) view).getMessage(), view, 0);
            }
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$9 */
    class C31099 implements OnTouchListener {
        C31099() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    public class MediaSearchAdapter extends SelectionAdapter {
        private int currentType;
        protected ArrayList<MessageObject> globalSearch = new ArrayList();
        private int lastReqId;
        private Context mContext;
        private int reqId = 0;
        private ArrayList<MessageObject> searchResult = new ArrayList();
        private Timer searchTimer;

        /* renamed from: org.telegram.ui.MediaActivity$MediaSearchAdapter$5 */
        class C31165 implements SharedLinkCellDelegate {
            C31165() {
            }

            public void needOpenWebView(TLRPC$WebPage webPage) {
                MediaActivity.this.openWebView(webPage);
            }

            public boolean canPerformActions() {
                return !MediaActivity.this.actionBar.isActionModeShowed();
            }
        }

        public MediaSearchAdapter(Context context, int type) {
            this.mContext = context;
            this.currentType = type;
        }

        public void queryServerSearch(String query, final int max_id, long did) {
            int uid = (int) did;
            if (uid != 0) {
                if (this.reqId != 0) {
                    ConnectionsManager.getInstance().cancelRequest(this.reqId, true);
                    this.reqId = 0;
                }
                if (query == null || query.length() == 0) {
                    this.globalSearch.clear();
                    this.lastReqId = 0;
                    notifyDataSetChanged();
                    return;
                }
                TLRPC$TL_messages_search req = new TLRPC$TL_messages_search();
                req.limit = 50;
                req.offset_id = max_id;
                if (this.currentType == 1) {
                    req.filter = new TLRPC$TL_inputMessagesFilterDocument();
                } else if (this.currentType == 3) {
                    req.filter = new TLRPC$TL_inputMessagesFilterUrl();
                } else if (this.currentType == 4) {
                    req.filter = new TLRPC$TL_inputMessagesFilterMusic();
                }
                req.f87q = query;
                req.peer = MessagesController.getInputPeer(uid);
                if (req.peer != null) {
                    final int currentReqId = this.lastReqId + 1;
                    this.lastReqId = currentReqId;
                    this.reqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                        public void run(TLObject response, TLRPC$TL_error error) {
                            final ArrayList<MessageObject> messageObjects = new ArrayList();
                            if (error == null) {
                                TLRPC$messages_Messages res = (TLRPC$messages_Messages) response;
                                for (int a = 0; a < res.messages.size(); a++) {
                                    TLRPC$Message message = (TLRPC$Message) res.messages.get(a);
                                    if (max_id == 0 || message.id <= max_id) {
                                        messageObjects.add(new MessageObject(message, null, false));
                                    }
                                }
                            }
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (currentReqId == MediaSearchAdapter.this.lastReqId) {
                                        MediaSearchAdapter.this.globalSearch = messageObjects;
                                        MediaSearchAdapter.this.notifyDataSetChanged();
                                    }
                                    MediaSearchAdapter.this.reqId = 0;
                                }
                            });
                        }
                    }, 2);
                    ConnectionsManager.getInstance().bindRequestToGuid(this.reqId, MediaActivity.this.classGuid);
                }
            }
        }

        public void search(final String query) {
            try {
                if (this.searchTimer != null) {
                    this.searchTimer.cancel();
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (query == null) {
                this.searchResult.clear();
                notifyDataSetChanged();
                return;
            }
            this.searchTimer = new Timer();
            this.searchTimer.schedule(new TimerTask() {
                public void run() {
                    try {
                        MediaSearchAdapter.this.searchTimer.cancel();
                        MediaSearchAdapter.this.searchTimer = null;
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    MediaSearchAdapter.this.processSearch(query);
                }
            }, 200, 300);
        }

        private void processSearch(final String query) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (!MediaActivity.this.sharedMediaData[MediaSearchAdapter.this.currentType].messages.isEmpty()) {
                        if (MediaSearchAdapter.this.currentType == 1 || MediaSearchAdapter.this.currentType == 4) {
                            MessageObject messageObject = (MessageObject) MediaActivity.this.sharedMediaData[MediaSearchAdapter.this.currentType].messages.get(MediaActivity.this.sharedMediaData[MediaSearchAdapter.this.currentType].messages.size() - 1);
                            MediaSearchAdapter.this.queryServerSearch(query, messageObject.getId(), messageObject.getDialogId());
                        } else if (MediaSearchAdapter.this.currentType == 3) {
                            MediaSearchAdapter.this.queryServerSearch(query, 0, MediaActivity.this.dialog_id);
                        }
                    }
                    if (MediaSearchAdapter.this.currentType == 1 || MediaSearchAdapter.this.currentType == 4) {
                        final ArrayList<MessageObject> copy = new ArrayList();
                        copy.addAll(MediaActivity.this.sharedMediaData[MediaSearchAdapter.this.currentType].messages);
                        Utilities.searchQueue.postRunnable(new Runnable() {
                            public void run() {
                                String search1 = query.trim().toLowerCase();
                                if (search1.length() == 0) {
                                    MediaSearchAdapter.this.updateSearchResults(new ArrayList());
                                    return;
                                }
                                String search2 = LocaleController.getInstance().getTranslitString(search1);
                                if (search1.equals(search2) || search2.length() == 0) {
                                    search2 = null;
                                }
                                String[] search = new String[((search2 != null ? 1 : 0) + 1)];
                                search[0] = search1;
                                if (search2 != null) {
                                    search[1] = search2;
                                }
                                ArrayList<MessageObject> resultArray = new ArrayList();
                                for (int a = 0; a < copy.size(); a++) {
                                    MessageObject messageObject = (MessageObject) copy.get(a);
                                    for (String q : search) {
                                        String name = messageObject.getDocumentName();
                                        if (!(name == null || name.length() == 0)) {
                                            if (!name.toLowerCase().contains(q)) {
                                                if (MediaSearchAdapter.this.currentType == 4) {
                                                    TLRPC$Document document;
                                                    if (messageObject.type == 0) {
                                                        document = messageObject.messageOwner.media.webpage.document;
                                                    } else {
                                                        document = messageObject.messageOwner.media.document;
                                                    }
                                                    boolean ok = false;
                                                    int c = 0;
                                                    while (c < document.attributes.size()) {
                                                        DocumentAttribute attribute = (DocumentAttribute) document.attributes.get(c);
                                                        if (attribute instanceof TLRPC$TL_documentAttributeAudio) {
                                                            if (attribute.performer != null) {
                                                                ok = attribute.performer.toLowerCase().contains(q);
                                                            }
                                                            if (!(ok || attribute.title == null)) {
                                                                ok = attribute.title.toLowerCase().contains(q);
                                                            }
                                                            if (ok) {
                                                                resultArray.add(messageObject);
                                                                break;
                                                            }
                                                        } else {
                                                            c++;
                                                        }
                                                    }
                                                    if (ok) {
                                                        resultArray.add(messageObject);
                                                        break;
                                                    }
                                                } else {
                                                    continue;
                                                }
                                            } else {
                                                resultArray.add(messageObject);
                                                break;
                                            }
                                        }
                                    }
                                }
                                MediaSearchAdapter.this.updateSearchResults(resultArray);
                            }
                        });
                    }
                }
            });
        }

        private void updateSearchResults(final ArrayList<MessageObject> documents) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MediaSearchAdapter.this.searchResult = documents;
                    MediaSearchAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public boolean isEnabled(ViewHolder holder) {
            return holder.getItemViewType() != this.searchResult.size() + this.globalSearch.size();
        }

        public int getItemCount() {
            int count = this.searchResult.size();
            int globalCount = this.globalSearch.size();
            if (globalCount != 0) {
                return count + globalCount;
            }
            return count;
        }

        public boolean isGlobalSearch(int i) {
            int localCount = this.searchResult.size();
            int globalCount = this.globalSearch.size();
            if ((i < 0 || i >= localCount) && i > localCount && i <= globalCount + localCount) {
                return true;
            }
            return false;
        }

        public MessageObject getItem(int i) {
            if (i < this.searchResult.size()) {
                return (MessageObject) this.searchResult.get(i);
            }
            return (MessageObject) this.globalSearch.get(i - this.searchResult.size());
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (this.currentType == 1 || this.currentType == 4) {
                view = new SharedDocumentCell(this.mContext);
            } else {
                view = new SharedLinkCell(this.mContext);
                ((SharedLinkCell) view).setDelegate(new C31165());
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            boolean z = true;
            MessageObject messageObject;
            boolean z2;
            HashMap[] access$700;
            int i;
            if (this.currentType == 1 || this.currentType == 4) {
                SharedDocumentCell sharedDocumentCell = holder.itemView;
                messageObject = getItem(position);
                if (position != getItemCount() - 1) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                sharedDocumentCell.setDocument(messageObject, z2);
                if (MediaActivity.this.actionBar.isActionModeShowed()) {
                    access$700 = MediaActivity.this.selectedFiles;
                    if (messageObject.getDialogId() == MediaActivity.this.dialog_id) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    z2 = access$700[i].containsKey(Integer.valueOf(messageObject.getId()));
                    if (MediaActivity.this.scrolling) {
                        z = false;
                    }
                    sharedDocumentCell.setChecked(z2, z);
                    return;
                }
                if (MediaActivity.this.scrolling) {
                    z = false;
                }
                sharedDocumentCell.setChecked(false, z);
            } else if (this.currentType == 3) {
                SharedLinkCell sharedLinkCell = holder.itemView;
                messageObject = getItem(position);
                if (position != getItemCount() - 1) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                sharedLinkCell.setLink(messageObject, z2);
                if (MediaActivity.this.actionBar.isActionModeShowed()) {
                    access$700 = MediaActivity.this.selectedFiles;
                    if (messageObject.getDialogId() == MediaActivity.this.dialog_id) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    z2 = access$700[i].containsKey(Integer.valueOf(messageObject.getId()));
                    if (MediaActivity.this.scrolling) {
                        z = false;
                    }
                    sharedLinkCell.setChecked(z2, z);
                    return;
                }
                if (MediaActivity.this.scrolling) {
                    z = false;
                }
                sharedLinkCell.setChecked(false, z);
            }
        }

        public int getItemViewType(int i) {
            return 0;
        }
    }

    private class SharedDocumentsAdapter extends SectionsAdapter {
        private int currentType;
        private Context mContext;

        public SharedDocumentsAdapter(Context context, int type) {
            this.mContext = context;
            this.currentType = type;
        }

        public boolean isEnabled(int section, int row) {
            return row != 0;
        }

        public int getSectionCount() {
            int i = 1;
            int size = MediaActivity.this.sharedMediaData[this.currentType].sections.size();
            if (MediaActivity.this.sharedMediaData[this.currentType].sections.isEmpty() || (MediaActivity.this.sharedMediaData[this.currentType].endReached[0] && MediaActivity.this.sharedMediaData[this.currentType].endReached[1])) {
                i = 0;
            }
            return i + size;
        }

        public Object getItem(int section, int position) {
            return null;
        }

        public int getCountForSection(int section) {
            if (section < MediaActivity.this.sharedMediaData[this.currentType].sections.size()) {
                return ((ArrayList) MediaActivity.this.sharedMediaData[this.currentType].sectionArrays.get(MediaActivity.this.sharedMediaData[this.currentType].sections.get(section))).size() + 1;
            }
            return 1;
        }

        public View getSectionHeaderView(int section, View view) {
            if (view == null) {
                view = new GraySectionCell(this.mContext);
            }
            if (section < MediaActivity.this.sharedMediaData[this.currentType].sections.size()) {
                ((GraySectionCell) view).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) ((ArrayList) MediaActivity.this.sharedMediaData[this.currentType].sectionArrays.get((String) MediaActivity.this.sharedMediaData[this.currentType].sections.get(section))).get(0)).messageOwner.date) * 1000).toUpperCase());
            }
            return view;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new GraySectionCell(this.mContext);
                    break;
                case 1:
                    view = new SharedDocumentCell(this.mContext);
                    break;
                default:
                    view = new LoadingCell(this.mContext);
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(int section, int position, ViewHolder holder) {
            boolean z = true;
            if (holder.getItemViewType() != 2) {
                ArrayList<MessageObject> messageObjects = (ArrayList) MediaActivity.this.sharedMediaData[this.currentType].sectionArrays.get((String) MediaActivity.this.sharedMediaData[this.currentType].sections.get(section));
                switch (holder.getItemViewType()) {
                    case 0:
                        ((GraySectionCell) holder.itemView).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) messageObjects.get(0)).messageOwner.date) * 1000).toUpperCase());
                        return;
                    case 1:
                        boolean z2;
                        SharedDocumentCell sharedDocumentCell = holder.itemView;
                        MessageObject messageObject = (MessageObject) messageObjects.get(position - 1);
                        if (position != messageObjects.size() || (section == MediaActivity.this.sharedMediaData[this.currentType].sections.size() - 1 && MediaActivity.this.sharedMediaData[this.currentType].loading)) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        sharedDocumentCell.setDocument(messageObject, z2);
                        if (MediaActivity.this.actionBar.isActionModeShowed()) {
                            int i;
                            HashMap[] access$700 = MediaActivity.this.selectedFiles;
                            if (messageObject.getDialogId() == MediaActivity.this.dialog_id) {
                                i = 0;
                            } else {
                                i = 1;
                            }
                            z2 = access$700[i].containsKey(Integer.valueOf(messageObject.getId()));
                            if (MediaActivity.this.scrolling) {
                                z = false;
                            }
                            sharedDocumentCell.setChecked(z2, z);
                            return;
                        }
                        if (MediaActivity.this.scrolling) {
                            z = false;
                        }
                        sharedDocumentCell.setChecked(false, z);
                        return;
                    default:
                        return;
                }
            }
        }

        public int getItemViewType(int section, int position) {
            if (section >= MediaActivity.this.sharedMediaData[this.currentType].sections.size()) {
                return 2;
            }
            if (position == 0) {
                return 0;
            }
            return 1;
        }

        public String getLetter(int position) {
            return null;
        }

        public int getPositionForScrollProgress(float progress) {
            return 0;
        }
    }

    private class SharedLinksAdapter extends SectionsAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.MediaActivity$SharedLinksAdapter$1 */
        class C31171 implements SharedLinkCellDelegate {
            C31171() {
            }

            public void needOpenWebView(TLRPC$WebPage webPage) {
                MediaActivity.this.openWebView(webPage);
            }

            public boolean canPerformActions() {
                return !MediaActivity.this.actionBar.isActionModeShowed();
            }
        }

        public SharedLinksAdapter(Context context) {
            this.mContext = context;
        }

        public Object getItem(int section, int position) {
            return null;
        }

        public boolean isEnabled(int section, int row) {
            return row != 0;
        }

        public int getSectionCount() {
            int i = 1;
            int size = MediaActivity.this.sharedMediaData[3].sections.size();
            if (MediaActivity.this.sharedMediaData[3].sections.isEmpty() || (MediaActivity.this.sharedMediaData[3].endReached[0] && MediaActivity.this.sharedMediaData[3].endReached[1])) {
                i = 0;
            }
            return i + size;
        }

        public int getCountForSection(int section) {
            if (section < MediaActivity.this.sharedMediaData[3].sections.size()) {
                return ((ArrayList) MediaActivity.this.sharedMediaData[3].sectionArrays.get(MediaActivity.this.sharedMediaData[3].sections.get(section))).size() + 1;
            }
            return 1;
        }

        public View getSectionHeaderView(int section, View view) {
            if (view == null) {
                view = new GraySectionCell(this.mContext);
            }
            if (section < MediaActivity.this.sharedMediaData[3].sections.size()) {
                ((GraySectionCell) view).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) ((ArrayList) MediaActivity.this.sharedMediaData[3].sectionArrays.get((String) MediaActivity.this.sharedMediaData[3].sections.get(section))).get(0)).messageOwner.date) * 1000).toUpperCase());
            }
            return view;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new GraySectionCell(this.mContext);
                    break;
                case 1:
                    view = new SharedLinkCell(this.mContext);
                    ((SharedLinkCell) view).setDelegate(new C31171());
                    break;
                default:
                    view = new LoadingCell(this.mContext);
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(int section, int position, ViewHolder holder) {
            boolean z = true;
            if (holder.getItemViewType() != 2) {
                ArrayList<MessageObject> messageObjects = (ArrayList) MediaActivity.this.sharedMediaData[3].sectionArrays.get((String) MediaActivity.this.sharedMediaData[3].sections.get(section));
                switch (holder.getItemViewType()) {
                    case 0:
                        ((GraySectionCell) holder.itemView).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) messageObjects.get(0)).messageOwner.date) * 1000).toUpperCase());
                        return;
                    case 1:
                        boolean z2;
                        SharedLinkCell sharedLinkCell = holder.itemView;
                        MessageObject messageObject = (MessageObject) messageObjects.get(position - 1);
                        if (position != messageObjects.size() || (section == MediaActivity.this.sharedMediaData[3].sections.size() - 1 && MediaActivity.this.sharedMediaData[3].loading)) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        sharedLinkCell.setLink(messageObject, z2);
                        if (MediaActivity.this.actionBar.isActionModeShowed()) {
                            int i;
                            HashMap[] access$700 = MediaActivity.this.selectedFiles;
                            if (messageObject.getDialogId() == MediaActivity.this.dialog_id) {
                                i = 0;
                            } else {
                                i = 1;
                            }
                            z2 = access$700[i].containsKey(Integer.valueOf(messageObject.getId()));
                            if (MediaActivity.this.scrolling) {
                                z = false;
                            }
                            sharedLinkCell.setChecked(z2, z);
                            return;
                        }
                        if (MediaActivity.this.scrolling) {
                            z = false;
                        }
                        sharedLinkCell.setChecked(false, z);
                        return;
                    default:
                        return;
                }
            }
        }

        public int getItemViewType(int section, int position) {
            if (section >= MediaActivity.this.sharedMediaData[3].sections.size()) {
                return 2;
            }
            if (position == 0) {
                return 0;
            }
            return 1;
        }

        public String getLetter(int position) {
            return null;
        }

        public int getPositionForScrollProgress(float progress) {
            return 0;
        }
    }

    private class SharedMediaData {
        private boolean[] endReached;
        private boolean loading;
        private int[] max_id;
        private ArrayList<MessageObject> messages;
        private HashMap<Integer, MessageObject>[] messagesDict;
        private HashMap<String, ArrayList<MessageObject>> sectionArrays;
        private ArrayList<String> sections;
        private int totalCount;

        private SharedMediaData() {
            this.messages = new ArrayList();
            this.messagesDict = new HashMap[]{new HashMap(), new HashMap()};
            this.sections = new ArrayList();
            this.sectionArrays = new HashMap();
            this.endReached = new boolean[]{false, true};
            this.max_id = new int[]{0, 0};
        }

        public boolean addMessage(MessageObject messageObject, boolean isNew, boolean enc) {
            int loadIndex;
            if (messageObject.getDialogId() == MediaActivity.this.dialog_id) {
                loadIndex = 0;
            } else {
                loadIndex = 1;
            }
            if (this.messagesDict[loadIndex].containsKey(Integer.valueOf(messageObject.getId()))) {
                return false;
            }
            ArrayList<MessageObject> messageObjects = (ArrayList) this.sectionArrays.get(messageObject.monthKey);
            if (messageObjects == null) {
                messageObjects = new ArrayList();
                this.sectionArrays.put(messageObject.monthKey, messageObjects);
                if (isNew) {
                    this.sections.add(0, messageObject.monthKey);
                } else {
                    this.sections.add(messageObject.monthKey);
                }
            }
            if (isNew) {
                messageObjects.add(0, messageObject);
                this.messages.add(0, messageObject);
            } else {
                messageObjects.add(messageObject);
                this.messages.add(messageObject);
            }
            this.messagesDict[loadIndex].put(Integer.valueOf(messageObject.getId()), messageObject);
            if (enc) {
                this.max_id[loadIndex] = Math.max(messageObject.getId(), this.max_id[loadIndex]);
            } else if (messageObject.getId() > 0) {
                this.max_id[loadIndex] = Math.min(messageObject.getId(), this.max_id[loadIndex]);
            }
            return true;
        }

        public boolean deleteMessage(int mid, int loadIndex) {
            MessageObject messageObject = (MessageObject) this.messagesDict[loadIndex].get(Integer.valueOf(mid));
            if (messageObject == null) {
                return false;
            }
            ArrayList<MessageObject> messageObjects = (ArrayList) this.sectionArrays.get(messageObject.monthKey);
            if (messageObjects == null) {
                return false;
            }
            messageObjects.remove(messageObject);
            this.messages.remove(messageObject);
            this.messagesDict[loadIndex].remove(Integer.valueOf(messageObject.getId()));
            if (messageObjects.isEmpty()) {
                this.sectionArrays.remove(messageObject.monthKey);
                this.sections.remove(messageObject.monthKey);
            }
            this.totalCount--;
            return true;
        }

        public void replaceMid(int oldMid, int newMid) {
            MessageObject obj = (MessageObject) this.messagesDict[0].get(Integer.valueOf(oldMid));
            if (obj != null) {
                this.messagesDict[0].remove(Integer.valueOf(oldMid));
                this.messagesDict[0].put(Integer.valueOf(newMid), obj);
                obj.messageOwner.id = newMid;
            }
        }
    }

    private class SharedPhotoVideoAdapter extends SectionsAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.MediaActivity$SharedPhotoVideoAdapter$1 */
        class C31181 implements SharedPhotoVideoCellDelegate {
            C31181() {
            }

            public void didClickItem(SharedPhotoVideoCell cell, int index, MessageObject messageObject, int a) {
                MediaActivity.this.onItemClick(index, cell, messageObject, a);
            }

            public boolean didLongClickItem(SharedPhotoVideoCell cell, int index, MessageObject messageObject, int a) {
                return MediaActivity.this.onItemLongClick(messageObject, cell, a);
            }
        }

        public SharedPhotoVideoAdapter(Context context) {
            this.mContext = context;
        }

        public Object getItem(int section, int position) {
            return null;
        }

        public boolean isEnabled(int section, int row) {
            return false;
        }

        public int getSectionCount() {
            int i = 1;
            int size = MediaActivity.this.sharedMediaData[0].sections.size();
            if (MediaActivity.this.sharedMediaData[0].sections.isEmpty() || (MediaActivity.this.sharedMediaData[0].endReached[0] && MediaActivity.this.sharedMediaData[0].endReached[1])) {
                i = 0;
            }
            return i + size;
        }

        public int getCountForSection(int section) {
            if (section < MediaActivity.this.sharedMediaData[0].sections.size()) {
                return ((int) Math.ceil((double) (((float) ((ArrayList) MediaActivity.this.sharedMediaData[0].sectionArrays.get(MediaActivity.this.sharedMediaData[0].sections.get(section))).size()) / ((float) MediaActivity.this.columnsCount)))) + 1;
            }
            return 1;
        }

        public View getSectionHeaderView(int section, View view) {
            if (view == null) {
                view = new SharedMediaSectionCell(this.mContext);
                view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            }
            if (section < MediaActivity.this.sharedMediaData[0].sections.size()) {
                ((SharedMediaSectionCell) view).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) ((ArrayList) MediaActivity.this.sharedMediaData[0].sectionArrays.get((String) MediaActivity.this.sharedMediaData[0].sections.get(section))).get(0)).messageOwner.date) * 1000).toUpperCase());
            }
            return view;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new SharedMediaSectionCell(this.mContext);
                    break;
                case 1:
                    if (MediaActivity.this.cellCache.isEmpty()) {
                        view = new SharedPhotoVideoCell(this.mContext);
                    } else {
                        view = (View) MediaActivity.this.cellCache.get(0);
                        MediaActivity.this.cellCache.remove(0);
                    }
                    ((SharedPhotoVideoCell) view).setDelegate(new C31181());
                    break;
                default:
                    view = new LoadingCell(this.mContext);
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(int section, int position, ViewHolder holder) {
            if (holder.getItemViewType() != 2) {
                ArrayList<MessageObject> messageObjects = (ArrayList) MediaActivity.this.sharedMediaData[0].sectionArrays.get((String) MediaActivity.this.sharedMediaData[0].sections.get(section));
                switch (holder.getItemViewType()) {
                    case 0:
                        ((SharedMediaSectionCell) holder.itemView).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) messageObjects.get(0)).messageOwner.date) * 1000).toUpperCase());
                        return;
                    case 1:
                        SharedPhotoVideoCell cell = holder.itemView;
                        cell.setItemsCount(MediaActivity.this.columnsCount);
                        for (int a = 0; a < MediaActivity.this.columnsCount; a++) {
                            int index = ((position - 1) * MediaActivity.this.columnsCount) + a;
                            if (index < messageObjects.size()) {
                                MessageObject messageObject = (MessageObject) messageObjects.get(index);
                                cell.setIsFirst(position == 1);
                                cell.setItem(a, MediaActivity.this.sharedMediaData[0].messages.indexOf(messageObject), messageObject);
                                if (MediaActivity.this.actionBar.isActionModeShowed()) {
                                    int i;
                                    boolean z;
                                    HashMap[] access$700 = MediaActivity.this.selectedFiles;
                                    if (messageObject.getDialogId() == MediaActivity.this.dialog_id) {
                                        i = 0;
                                    } else {
                                        i = 1;
                                    }
                                    boolean containsKey = access$700[i].containsKey(Integer.valueOf(messageObject.getId()));
                                    if (MediaActivity.this.scrolling) {
                                        z = false;
                                    } else {
                                        z = true;
                                    }
                                    cell.setChecked(a, containsKey, z);
                                } else {
                                    cell.setChecked(a, false, !MediaActivity.this.scrolling);
                                }
                            } else {
                                cell.setItem(a, index, null);
                            }
                        }
                        cell.requestLayout();
                        return;
                    default:
                        return;
                }
            }
        }

        public int getItemViewType(int section, int position) {
            if (section >= MediaActivity.this.sharedMediaData[0].sections.size()) {
                return 2;
            }
            if (position == 0) {
                return 0;
            }
            return 1;
        }

        public String getLetter(int position) {
            return null;
        }

        public int getPositionForScrollProgress(float progress) {
            return 0;
        }
    }

    public MediaActivity(Bundle args) {
        super(args);
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.mediaDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDeleted);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceivedNewMessages);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messageReceivedByServer);
        this.dialog_id = getArguments().getLong("dialog_id", 0);
        for (int a = 0; a < this.sharedMediaData.length; a++) {
            this.sharedMediaData[a] = new SharedMediaData();
            this.sharedMediaData[a].max_id[0] = ((int) this.dialog_id) == 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            if (!(this.mergeDialogId == 0 || this.info == null)) {
                this.sharedMediaData[a].max_id[1] = this.info.migrated_from_max_id;
                this.sharedMediaData[a].endReached[1] = false;
            }
        }
        this.sharedMediaData[0].loading = true;
        SharedMediaQuery.loadMedia(this.dialog_id, 50, 0, 0, true, this.classGuid);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.mediaDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceivedNewMessages);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDeleted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messageReceivedByServer);
    }

    public View createView(Context context) {
        int a;
        this.actionBar.setBackButtonDrawable(new BackDrawable(false));
        this.actionBar.setTitle("");
        this.actionBar.setAllowOverlayTitle(false);
        this.actionBar.setActionBarMenuOnItemClick(new C31022());
        for (a = 1; a >= 0; a--) {
            this.selectedFiles[a].clear();
        }
        this.cantDeleteMessagesCount = 0;
        this.actionModeViews.clear();
        ActionBarMenu menu = this.actionBar.createMenu();
        this.searchItem = menu.addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C31033());
        this.searchItem.getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.searchItem.setVisibility(8);
        this.dropDownContainer = new ActionBarMenuItem(context, menu, 0, 0);
        this.dropDownContainer.setSubMenuOpenSide(1);
        this.dropDownContainer.addSubItem(1, LocaleController.getString("SharedMediaTitle", R.string.SharedMediaTitle));
        this.dropDownContainer.addSubItem(2, LocaleController.getString("DocumentsTitle", R.string.DocumentsTitle));
        if (((int) this.dialog_id) != 0) {
            this.dropDownContainer.addSubItem(5, LocaleController.getString("LinksTitle", R.string.LinksTitle));
            this.dropDownContainer.addSubItem(6, LocaleController.getString("AudioTitle", R.string.AudioTitle));
        } else {
            TLRPC$EncryptedChat currentEncryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (this.dialog_id >> 32)));
            if (currentEncryptedChat != null && AndroidUtilities.getPeerLayerVersion(currentEncryptedChat.layer) >= 46) {
                this.dropDownContainer.addSubItem(6, LocaleController.getString("AudioTitle", R.string.AudioTitle));
            }
        }
        this.actionBar.addView(this.dropDownContainer, 0, LayoutHelper.createFrame(-2, -1.0f, 51, AndroidUtilities.isTablet() ? 64.0f : 56.0f, 0.0f, 40.0f, 0.0f));
        this.dropDownContainer.setOnClickListener(new C31044());
        this.dropDown = new TextView(context);
        this.dropDown.setGravity(3);
        this.dropDown.setSingleLine(true);
        this.dropDown.setLines(1);
        this.dropDown.setMaxLines(1);
        this.dropDown.setEllipsize(TruncateAt.END);
        this.dropDown.setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
        this.dropDown.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.dropDownDrawable = context.getResources().getDrawable(R.drawable.ic_arrow_drop_down).mutate();
        this.dropDownDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_actionBarDefaultTitle), Mode.MULTIPLY));
        this.dropDown.setCompoundDrawablesWithIntrinsicBounds(null, null, this.dropDownDrawable, null);
        this.dropDown.setCompoundDrawablePadding(AndroidUtilities.dp(4.0f));
        this.dropDown.setPadding(0, 0, AndroidUtilities.dp(10.0f), 0);
        this.dropDownContainer.addView(this.dropDown, LayoutHelper.createFrame(-2, -2.0f, 16, 16.0f, 0.0f, 0.0f, 0.0f));
        ActionBarMenu actionMode = this.actionBar.createActionMode();
        this.selectedMessagesCountTextView = new NumberTextView(actionMode.getContext());
        this.selectedMessagesCountTextView.setTextSize(18);
        this.selectedMessagesCountTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.selectedMessagesCountTextView.setTextColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon));
        this.selectedMessagesCountTextView.setOnTouchListener(new C31055());
        actionMode.addView(this.selectedMessagesCountTextView, LayoutHelper.createLinear(0, -1, 1.0f, 65, 0, 0, 0));
        if (((int) this.dialog_id) != 0) {
            this.actionModeViews.add(actionMode.addItemWithWidth(3, R.drawable.ic_ab_forward, AndroidUtilities.dp(54.0f)));
        }
        this.actionModeViews.add(actionMode.addItemWithWidth(4, R.drawable.ic_ab_delete, AndroidUtilities.dp(54.0f)));
        this.photoVideoAdapter = new SharedPhotoVideoAdapter(context);
        this.documentsAdapter = new SharedDocumentsAdapter(context, 1);
        this.audioAdapter = new SharedDocumentsAdapter(context, 4);
        this.documentsSearchAdapter = new MediaSearchAdapter(context, 1);
        this.audioSearchAdapter = new MediaSearchAdapter(context, 4);
        this.linksSearchAdapter = new MediaSearchAdapter(context, 3);
        this.linksAdapter = new SharedLinksAdapter(context);
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        int scrollToPositionOnRecreate = -1;
        int scrollToOffsetOnRecreate = 0;
        if (this.layoutManager != null) {
            scrollToPositionOnRecreate = this.layoutManager.findFirstVisibleItemPosition();
            if (scrollToPositionOnRecreate != this.layoutManager.getItemCount() - 1) {
                Holder holder = (Holder) this.listView.findViewHolderForAdapterPosition(scrollToPositionOnRecreate);
                if (holder != null) {
                    scrollToOffsetOnRecreate = holder.itemView.getTop();
                } else {
                    scrollToPositionOnRecreate = -1;
                }
            } else {
                scrollToPositionOnRecreate = -1;
            }
        }
        this.listView = new RecyclerListView(context);
        this.listView.setClipToPadding(false);
        this.listView.setSectionsType(2);
        RecyclerListView recyclerListView = this.listView;
        LayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false);
        this.layoutManager = linearLayoutManager;
        recyclerListView.setLayoutManager(linearLayoutManager);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemClickListener(new C31066());
        this.listView.setOnScrollListener(new C31077());
        this.listView.setOnItemLongClickListener(new C31088());
        if (scrollToPositionOnRecreate != -1) {
            this.layoutManager.scrollToPositionWithOffset(scrollToPositionOnRecreate, scrollToOffsetOnRecreate);
        }
        for (a = 0; a < 6; a++) {
            this.cellCache.add(new SharedPhotoVideoCell(context));
        }
        this.emptyView = new LinearLayout(context);
        this.emptyView.setOrientation(1);
        this.emptyView.setGravity(17);
        this.emptyView.setVisibility(8);
        this.emptyView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.emptyView.setOnTouchListener(new C31099());
        this.emptyImageView = new ImageView(context);
        this.emptyView.addView(this.emptyImageView, LayoutHelper.createLinear(-2, -2));
        this.emptyTextView = new TextView(context);
        this.emptyTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.emptyTextView.setGravity(17);
        this.emptyTextView.setTextSize(1, 17.0f);
        this.emptyTextView.setPadding(AndroidUtilities.dp(40.0f), 0, AndroidUtilities.dp(40.0f), AndroidUtilities.dp(128.0f));
        this.emptyView.addView(this.emptyTextView, LayoutHelper.createLinear(-2, -2, 17, 0, 24, 0, 0));
        this.progressView = new LinearLayout(context);
        this.progressView.setGravity(17);
        this.progressView.setOrientation(1);
        this.progressView.setVisibility(8);
        this.progressView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        frameLayout.addView(this.progressView, LayoutHelper.createFrame(-1, -1.0f));
        this.progressBar = new RadialProgressView(context);
        this.progressView.addView(this.progressBar, LayoutHelper.createLinear(-2, -2));
        switchToCurrentSelectedMode();
        if (!AndroidUtilities.isTablet()) {
            View fragmentContextView = new FragmentContextView(context, this, false);
            this.fragmentContextView = fragmentContextView;
            frameLayout.addView(fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, 0.0f, -36.0f, 0.0f, 0.0f));
        }
        return this.fragmentView;
    }

    public void didReceivedNotification(int id, Object... args) {
        int type;
        ArrayList<MessageObject> arr;
        boolean enc;
        int loadIndex;
        int a;
        ActionBarMenuItem actionBarMenuItem;
        int i;
        if (id == NotificationCenter.mediaDidLoaded) {
            long uid = ((Long) args[0]).longValue();
            if (((Integer) args[3]).intValue() == this.classGuid) {
                type = ((Integer) args[4]).intValue();
                this.sharedMediaData[type].loading = false;
                this.sharedMediaData[type].totalCount = ((Integer) args[1]).intValue();
                arr = args[2];
                enc = ((int) this.dialog_id) == 0;
                loadIndex = uid == this.dialog_id ? 0 : 1;
                for (a = 0; a < arr.size(); a++) {
                    this.sharedMediaData[type].addMessage((MessageObject) arr.get(a), false, enc);
                }
                this.sharedMediaData[type].endReached[loadIndex] = ((Boolean) args[5]).booleanValue();
                if (loadIndex == 0 && this.sharedMediaData[type].endReached[loadIndex] && this.mergeDialogId != 0) {
                    this.sharedMediaData[type].loading = true;
                    SharedMediaQuery.loadMedia(this.mergeDialogId, 50, this.sharedMediaData[type].max_id[1], type, true, this.classGuid);
                }
                if (!this.sharedMediaData[type].loading) {
                    if (this.progressView != null) {
                        this.progressView.setVisibility(8);
                    }
                    if (this.selectedMode == type && this.listView != null && this.listView.getEmptyView() == null) {
                        this.listView.setEmptyView(this.emptyView);
                    }
                }
                this.scrolling = true;
                if (this.selectedMode == 0 && type == 0) {
                    if (this.photoVideoAdapter != null) {
                        this.photoVideoAdapter.notifyDataSetChanged();
                    }
                } else if (this.selectedMode == 1 && type == 1) {
                    if (this.documentsAdapter != null) {
                        this.documentsAdapter.notifyDataSetChanged();
                    }
                } else if (this.selectedMode == 3 && type == 3) {
                    if (this.linksAdapter != null) {
                        this.linksAdapter.notifyDataSetChanged();
                    }
                } else if (this.selectedMode == 4 && type == 4 && this.audioAdapter != null) {
                    this.audioAdapter.notifyDataSetChanged();
                }
                if (this.selectedMode == 1 || this.selectedMode == 3 || this.selectedMode == 4) {
                    actionBarMenuItem = this.searchItem;
                    if (this.sharedMediaData[type].messages.isEmpty() || this.searching) {
                        i = 8;
                    } else {
                        i = 0;
                    }
                    actionBarMenuItem.setVisibility(i);
                }
            }
        } else if (id == NotificationCenter.messagesDeleted) {
            TLRPC$Chat currentChat = null;
            if (((int) this.dialog_id) < 0) {
                currentChat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id)));
            }
            int channelId = ((Integer) args[1]).intValue();
            loadIndex = 0;
            if (ChatObject.isChannel(currentChat)) {
                if (channelId == 0 && this.mergeDialogId != 0) {
                    loadIndex = 1;
                } else if (channelId == currentChat.id) {
                    loadIndex = 0;
                } else {
                    return;
                }
            } else if (channelId != 0) {
                return;
            }
            updated = false;
            Iterator it = args[0].iterator();
            while (it.hasNext()) {
                Integer ids = (Integer) it.next();
                for (SharedMediaData data : this.sharedMediaData) {
                    if (data.deleteMessage(ids.intValue(), loadIndex)) {
                        updated = true;
                    }
                }
            }
            if (updated) {
                this.scrolling = true;
                if (this.photoVideoAdapter != null) {
                    this.photoVideoAdapter.notifyDataSetChanged();
                }
                if (this.documentsAdapter != null) {
                    this.documentsAdapter.notifyDataSetChanged();
                }
                if (this.linksAdapter != null) {
                    this.linksAdapter.notifyDataSetChanged();
                }
                if (this.audioAdapter != null) {
                    this.audioAdapter.notifyDataSetChanged();
                }
                if (this.selectedMode == 1 || this.selectedMode == 3 || this.selectedMode == 4) {
                    actionBarMenuItem = this.searchItem;
                    i = (this.sharedMediaData[this.selectedMode].messages.isEmpty() || this.searching) ? 8 : 0;
                    actionBarMenuItem.setVisibility(i);
                }
            }
        } else if (id == NotificationCenter.didReceivedNewMessages) {
            if (((Long) args[0]).longValue() == this.dialog_id) {
                arr = (ArrayList) args[1];
                enc = ((int) this.dialog_id) == 0;
                updated = false;
                for (a = 0; a < arr.size(); a++) {
                    MessageObject obj = (MessageObject) arr.get(a);
                    if (!(obj.messageOwner.media == null || obj.isSecretPhoto())) {
                        type = SharedMediaQuery.getMediaType(obj.messageOwner);
                        if (type == -1) {
                            return;
                        }
                        if (this.sharedMediaData[type].addMessage(obj, true, enc)) {
                            updated = true;
                        }
                    }
                }
                if (updated) {
                    this.scrolling = true;
                    if (this.photoVideoAdapter != null) {
                        this.photoVideoAdapter.notifyDataSetChanged();
                    }
                    if (this.documentsAdapter != null) {
                        this.documentsAdapter.notifyDataSetChanged();
                    }
                    if (this.linksAdapter != null) {
                        this.linksAdapter.notifyDataSetChanged();
                    }
                    if (this.audioAdapter != null) {
                        this.audioAdapter.notifyDataSetChanged();
                    }
                    if (this.selectedMode == 1 || this.selectedMode == 3 || this.selectedMode == 4) {
                        actionBarMenuItem = this.searchItem;
                        i = (this.sharedMediaData[this.selectedMode].messages.isEmpty() || this.searching) ? 8 : 0;
                        actionBarMenuItem.setVisibility(i);
                    }
                }
            }
        } else if (id == NotificationCenter.messageReceivedByServer) {
            Integer msgId = args[0];
            Integer newMsgId = args[1];
            for (SharedMediaData data2 : this.sharedMediaData) {
                data2.replaceMid(msgId.intValue(), newMsgId.intValue());
            }
        }
    }

    public void onPause() {
        super.onPause();
        if (this.dropDownContainer != null) {
            this.dropDownContainer.closeSubMenu();
        }
    }

    public void onResume() {
        super.onResume();
        this.scrolling = true;
        if (this.photoVideoAdapter != null) {
            this.photoVideoAdapter.notifyDataSetChanged();
        }
        if (this.documentsAdapter != null) {
            this.documentsAdapter.notifyDataSetChanged();
        }
        if (this.linksAdapter != null) {
            this.linksAdapter.notifyDataSetChanged();
        }
        fixLayoutInternal();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.listView != null) {
            this.listView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() {
                    MediaActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
                    MediaActivity.this.fixLayoutInternal();
                    return true;
                }
            });
        }
    }

    public void setChatInfo(TLRPC$ChatFull chatInfo) {
        this.info = chatInfo;
        if (this.info != null && this.info.migrated_from_chat_id != 0) {
            this.mergeDialogId = (long) (-this.info.migrated_from_chat_id);
        }
    }

    public void setMergeDialogId(long did) {
        this.mergeDialogId = did;
    }

    private void switchToCurrentSelectedMode() {
        if (this.searching && this.searchWas) {
            if (this.listView != null) {
                if (this.selectedMode == 1) {
                    this.listView.setAdapter(this.documentsSearchAdapter);
                    this.documentsSearchAdapter.notifyDataSetChanged();
                } else if (this.selectedMode == 3) {
                    this.listView.setAdapter(this.linksSearchAdapter);
                    this.linksSearchAdapter.notifyDataSetChanged();
                } else if (this.selectedMode == 4) {
                    this.listView.setAdapter(this.audioSearchAdapter);
                    this.audioSearchAdapter.notifyDataSetChanged();
                }
            }
            if (this.emptyTextView != null) {
                this.emptyTextView.setText(LocaleController.getString("NoResult", R.string.NoResult));
                this.emptyTextView.setTextSize(1, 20.0f);
                this.emptyImageView.setVisibility(8);
                return;
            }
            return;
        }
        this.emptyTextView.setTextSize(1, 17.0f);
        this.emptyImageView.setVisibility(0);
        if (this.selectedMode == 0) {
            this.listView.setAdapter(this.photoVideoAdapter);
            this.dropDown.setText(LocaleController.getString("SharedMediaTitle", R.string.SharedMediaTitle));
            this.emptyImageView.setImageResource(R.drawable.tip1);
            if (((int) this.dialog_id) == 0) {
                this.emptyTextView.setText(LocaleController.getString("NoMediaSecret", R.string.NoMediaSecret));
            } else {
                this.emptyTextView.setText(LocaleController.getString("NoMedia", R.string.NoMedia));
            }
            this.searchItem.setVisibility(8);
            if (this.sharedMediaData[this.selectedMode].loading && this.sharedMediaData[this.selectedMode].messages.isEmpty()) {
                this.progressView.setVisibility(0);
                this.listView.setEmptyView(null);
                this.emptyView.setVisibility(8);
            } else {
                this.progressView.setVisibility(8);
                this.listView.setEmptyView(this.emptyView);
            }
            this.listView.setVisibility(0);
            this.listView.setPadding(0, 0, 0, AndroidUtilities.dp(4.0f));
        } else if (this.selectedMode == 1 || this.selectedMode == 4) {
            if (this.selectedMode == 1) {
                this.listView.setAdapter(this.documentsAdapter);
                this.dropDown.setText(LocaleController.getString("DocumentsTitle", R.string.DocumentsTitle));
                this.emptyImageView.setImageResource(R.drawable.tip2);
                if (((int) this.dialog_id) == 0) {
                    this.emptyTextView.setText(LocaleController.getString("NoSharedFilesSecret", R.string.NoSharedFilesSecret));
                } else {
                    this.emptyTextView.setText(LocaleController.getString("NoSharedFiles", R.string.NoSharedFiles));
                }
            } else if (this.selectedMode == 4) {
                this.listView.setAdapter(this.audioAdapter);
                this.dropDown.setText(LocaleController.getString("AudioTitle", R.string.AudioTitle));
                this.emptyImageView.setImageResource(R.drawable.tip4);
                if (((int) this.dialog_id) == 0) {
                    this.emptyTextView.setText(LocaleController.getString("NoSharedAudioSecret", R.string.NoSharedAudioSecret));
                } else {
                    this.emptyTextView.setText(LocaleController.getString("NoSharedAudio", R.string.NoSharedAudio));
                }
            }
            r1 = this.searchItem;
            if (this.sharedMediaData[this.selectedMode].messages.isEmpty()) {
                r0 = 8;
            } else {
                r0 = 0;
            }
            r1.setVisibility(r0);
            if (!(this.sharedMediaData[this.selectedMode].loading || this.sharedMediaData[this.selectedMode].endReached[0] || !this.sharedMediaData[this.selectedMode].messages.isEmpty())) {
                int i;
                this.sharedMediaData[this.selectedMode].loading = true;
                long j = this.dialog_id;
                if (this.selectedMode == 1) {
                    i = 1;
                } else {
                    i = 4;
                }
                SharedMediaQuery.loadMedia(j, 50, 0, i, true, this.classGuid);
            }
            this.listView.setVisibility(0);
            if (this.sharedMediaData[this.selectedMode].loading && this.sharedMediaData[this.selectedMode].messages.isEmpty()) {
                this.progressView.setVisibility(0);
                this.listView.setEmptyView(null);
                this.emptyView.setVisibility(8);
            } else {
                this.progressView.setVisibility(8);
                this.listView.setEmptyView(this.emptyView);
            }
            this.listView.setPadding(0, 0, 0, AndroidUtilities.dp(4.0f));
        } else if (this.selectedMode == 3) {
            this.listView.setAdapter(this.linksAdapter);
            this.dropDown.setText(LocaleController.getString("LinksTitle", R.string.LinksTitle));
            this.emptyImageView.setImageResource(R.drawable.tip3);
            if (((int) this.dialog_id) == 0) {
                this.emptyTextView.setText(LocaleController.getString("NoSharedLinksSecret", R.string.NoSharedLinksSecret));
            } else {
                this.emptyTextView.setText(LocaleController.getString("NoSharedLinks", R.string.NoSharedLinks));
            }
            r1 = this.searchItem;
            if (this.sharedMediaData[3].messages.isEmpty()) {
                r0 = 8;
            } else {
                r0 = 0;
            }
            r1.setVisibility(r0);
            if (!(this.sharedMediaData[this.selectedMode].loading || this.sharedMediaData[this.selectedMode].endReached[0] || !this.sharedMediaData[this.selectedMode].messages.isEmpty())) {
                this.sharedMediaData[this.selectedMode].loading = true;
                SharedMediaQuery.loadMedia(this.dialog_id, 50, 0, 3, true, this.classGuid);
            }
            this.listView.setVisibility(0);
            if (this.sharedMediaData[this.selectedMode].loading && this.sharedMediaData[this.selectedMode].messages.isEmpty()) {
                this.progressView.setVisibility(0);
                this.listView.setEmptyView(null);
                this.emptyView.setVisibility(8);
            } else {
                this.progressView.setVisibility(8);
                this.listView.setEmptyView(this.emptyView);
            }
            this.listView.setPadding(0, 0, 0, AndroidUtilities.dp(4.0f));
        }
    }

    private boolean onItemLongClick(MessageObject item, View view, int a) {
        if (this.actionBar.isActionModeShowed()) {
            return false;
        }
        int i;
        AndroidUtilities.hideKeyboard(getParentActivity().getCurrentFocus());
        HashMap[] hashMapArr = this.selectedFiles;
        if (item.getDialogId() == this.dialog_id) {
            i = 0;
        } else {
            i = 1;
        }
        hashMapArr[i].put(Integer.valueOf(item.getId()), item);
        if (!item.canDeleteMessage(null)) {
            this.cantDeleteMessagesCount++;
        }
        this.actionBar.createActionMode().getItem(4).setVisibility(this.cantDeleteMessagesCount == 0 ? 0 : 8);
        this.selectedMessagesCountTextView.setNumber(1, false);
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList<Animator> animators = new ArrayList();
        for (int i2 = 0; i2 < this.actionModeViews.size(); i2++) {
            View view2 = (View) this.actionModeViews.get(i2);
            AndroidUtilities.clearDrawableAnimation(view2);
            animators.add(ObjectAnimator.ofFloat(view2, "scaleY", new float[]{0.1f, 1.0f}));
        }
        animatorSet.playTogether(animators);
        animatorSet.setDuration(250);
        animatorSet.start();
        this.scrolling = false;
        if (view instanceof SharedDocumentCell) {
            ((SharedDocumentCell) view).setChecked(true, true);
        } else if (view instanceof SharedPhotoVideoCell) {
            ((SharedPhotoVideoCell) view).setChecked(a, true, true);
        } else if (view instanceof SharedLinkCell) {
            ((SharedLinkCell) view).setChecked(true, true);
        }
        this.actionBar.showActionMode();
        return true;
    }

    private void onItemClick(int index, View view, MessageObject message, int a) {
        if (message != null) {
            if (this.actionBar.isActionModeShowed()) {
                int loadIndex = message.getDialogId() == this.dialog_id ? 0 : 1;
                if (this.selectedFiles[loadIndex].containsKey(Integer.valueOf(message.getId()))) {
                    this.selectedFiles[loadIndex].remove(Integer.valueOf(message.getId()));
                    if (!message.canDeleteMessage(null)) {
                        this.cantDeleteMessagesCount--;
                    }
                } else if (this.selectedFiles[0].size() + this.selectedFiles[1].size() < 100) {
                    this.selectedFiles[loadIndex].put(Integer.valueOf(message.getId()), message);
                    if (!message.canDeleteMessage(null)) {
                        this.cantDeleteMessagesCount++;
                    }
                } else {
                    return;
                }
                if (this.selectedFiles[0].isEmpty() && this.selectedFiles[1].isEmpty()) {
                    this.actionBar.hideActionMode();
                } else {
                    this.selectedMessagesCountTextView.setNumber(this.selectedFiles[0].size() + this.selectedFiles[1].size(), true);
                }
                this.actionBar.createActionMode().getItem(4).setVisibility(this.cantDeleteMessagesCount == 0 ? 0 : 8);
                this.scrolling = false;
                if (view instanceof SharedDocumentCell) {
                    ((SharedDocumentCell) view).setChecked(this.selectedFiles[loadIndex].containsKey(Integer.valueOf(message.getId())), true);
                } else if (view instanceof SharedPhotoVideoCell) {
                    ((SharedPhotoVideoCell) view).setChecked(a, this.selectedFiles[loadIndex].containsKey(Integer.valueOf(message.getId())), true);
                } else if (view instanceof SharedLinkCell) {
                    ((SharedLinkCell) view).setChecked(this.selectedFiles[loadIndex].containsKey(Integer.valueOf(message.getId())), true);
                }
            } else if (this.selectedMode == 0) {
                PhotoViewer.getInstance().setParentActivity(getParentActivity());
                PhotoViewer.getInstance().openPhoto(this.sharedMediaData[this.selectedMode].messages, index, this.dialog_id, this.mergeDialogId, this.provider);
            } else if (this.selectedMode == 1 || this.selectedMode == 4) {
                if (view instanceof SharedDocumentCell) {
                    SharedDocumentCell cell = (SharedDocumentCell) view;
                    if (cell.isLoaded()) {
                        if (!message.isMusic() || !MediaController.getInstance().setPlaylist(this.sharedMediaData[this.selectedMode].messages, message)) {
                            File f = null;
                            String fileName = message.messageOwner.media != null ? FileLoader.getAttachFileName(message.getDocument()) : "";
                            if (!(message.messageOwner.attachPath == null || message.messageOwner.attachPath.length() == 0)) {
                                f = new File(message.messageOwner.attachPath);
                            }
                            if (f == null || !(f == null || f.exists())) {
                                f = FileLoader.getPathToMessage(message.messageOwner);
                            }
                            if (f != null && f.exists()) {
                                Builder builder;
                                if (f.getName().endsWith("attheme")) {
                                    ThemeInfo themeInfo = Theme.applyThemeFile(f, message.getDocumentName(), true);
                                    if (themeInfo != null) {
                                        presentFragment(new ThemePreviewActivity(f, themeInfo));
                                        return;
                                    }
                                    builder = new Builder(getParentActivity());
                                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                    builder.setMessage(LocaleController.getString("IncorrectTheme", R.string.IncorrectTheme));
                                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                    showDialog(builder.create());
                                    return;
                                }
                                String realMimeType = null;
                                try {
                                    Intent intent = new Intent("android.intent.action.VIEW");
                                    intent.setFlags(1);
                                    MimeTypeMap myMime = MimeTypeMap.getSingleton();
                                    int idx = fileName.lastIndexOf(46);
                                    if (idx != -1) {
                                        realMimeType = myMime.getMimeTypeFromExtension(fileName.substring(idx + 1).toLowerCase());
                                        if (realMimeType == null) {
                                            realMimeType = message.getDocument().mime_type;
                                            if (realMimeType == null || realMimeType.length() == 0) {
                                                realMimeType = null;
                                            }
                                        }
                                    }
                                    if (VERSION.SDK_INT >= 24) {
                                        intent.setDataAndType(FileProvider.getUriForFile(getParentActivity(), "org.ir.talaeii.provider", f), realMimeType != null ? realMimeType : "text/plain");
                                    } else {
                                        intent.setDataAndType(Uri.fromFile(f), realMimeType != null ? realMimeType : "text/plain");
                                    }
                                    if (realMimeType != null) {
                                        try {
                                            getParentActivity().startActivityForResult(intent, 500);
                                            return;
                                        } catch (Exception e) {
                                            if (VERSION.SDK_INT >= 24) {
                                                intent.setDataAndType(FileProvider.getUriForFile(getParentActivity(), "org.ir.talaeii.provider", f), "text/plain");
                                            } else {
                                                intent.setDataAndType(Uri.fromFile(f), "text/plain");
                                            }
                                            getParentActivity().startActivityForResult(intent, 500);
                                            return;
                                        }
                                    }
                                    getParentActivity().startActivityForResult(intent, 500);
                                } catch (Exception e2) {
                                    if (getParentActivity() != null) {
                                        builder = new Builder(getParentActivity());
                                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                        builder.setMessage(LocaleController.formatString("NoHandleAppInstalled", R.string.NoHandleAppInstalled, new Object[]{message.getDocument().mime_type}));
                                        showDialog(builder.create());
                                    }
                                }
                            }
                        }
                    } else if (cell.isLoading()) {
                        FileLoader.getInstance().cancelLoadFile(cell.getMessage().getDocument());
                        cell.updateFileExistIcon();
                    } else {
                        FileLoader.getInstance().loadFile(cell.getMessage().getDocument(), false, 0);
                        cell.updateFileExistIcon();
                    }
                }
            } else if (this.selectedMode == 3) {
                try {
                    TLRPC$WebPage webPage = message.messageOwner.media.webpage;
                    String link = null;
                    if (!(webPage == null || (webPage instanceof TLRPC$TL_webPageEmpty))) {
                        if (webPage.embed_url == null || webPage.embed_url.length() == 0) {
                            link = webPage.url;
                        } else {
                            openWebView(webPage);
                            return;
                        }
                    }
                    if (link == null) {
                        link = ((SharedLinkCell) view).getLink(0);
                    }
                    if (link != null) {
                        Browser.openUrl(getParentActivity(), link);
                    }
                } catch (Exception e3) {
                    FileLog.e(e3);
                }
            }
        }
    }

    private void openWebView(TLRPC$WebPage webPage) {
        EmbedBottomSheet.show(getParentActivity(), webPage.site_name, webPage.description, webPage.url, webPage.embed_url, webPage.embed_width, webPage.embed_height);
    }

    private void fixLayoutInternal() {
        int i = 0;
        if (this.listView != null) {
            int rotation = ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
            if (AndroidUtilities.isTablet() || ApplicationLoader.applicationContext.getResources().getConfiguration().orientation != 2) {
                this.selectedMessagesCountTextView.setTextSize(20);
            } else {
                this.selectedMessagesCountTextView.setTextSize(18);
            }
            if (AndroidUtilities.isTablet()) {
                this.columnsCount = 4;
                this.emptyTextView.setPadding(AndroidUtilities.dp(40.0f), 0, AndroidUtilities.dp(40.0f), AndroidUtilities.dp(128.0f));
            } else if (rotation == 3 || rotation == 1) {
                this.columnsCount = 6;
                this.emptyTextView.setPadding(AndroidUtilities.dp(40.0f), 0, AndroidUtilities.dp(40.0f), 0);
            } else {
                this.columnsCount = 4;
                this.emptyTextView.setPadding(AndroidUtilities.dp(40.0f), 0, AndroidUtilities.dp(40.0f), AndroidUtilities.dp(128.0f));
            }
            this.photoVideoAdapter.notifyDataSetChanged();
            if (this.dropDownContainer != null) {
                if (!AndroidUtilities.isTablet()) {
                    LayoutParams layoutParams = (LayoutParams) this.dropDownContainer.getLayoutParams();
                    if (VERSION.SDK_INT >= 21) {
                        i = AndroidUtilities.statusBarHeight;
                    }
                    layoutParams.topMargin = i;
                    this.dropDownContainer.setLayoutParams(layoutParams);
                }
                if (AndroidUtilities.isTablet() || ApplicationLoader.applicationContext.getResources().getConfiguration().orientation != 2) {
                    this.dropDown.setTextSize(20.0f);
                } else {
                    this.dropDown.setTextSize(18.0f);
                }
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new ThemeDescriptionDelegate() {
            public void didSetColor(int color) {
                int count = MediaActivity.this.listView.getChildCount();
                for (int a = 0; a < count; a++) {
                    View child = MediaActivity.this.listView.getChildAt(a);
                    if (child instanceof SharedPhotoVideoCell) {
                        ((SharedPhotoVideoCell) child).updateCheckboxColor();
                    }
                }
            }
        };
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[53];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.progressView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[7] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUBACKGROUND, null, null, null, null, Theme.key_actionBarDefaultSubmenuBackground);
        themeDescriptionArr[9] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUITEM, null, null, null, null, Theme.key_actionBarDefaultSubmenuItem);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[11] = new ThemeDescription(this.dropDown, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[12] = new ThemeDescription(this.dropDown, 0, null, null, new Drawable[]{this.dropDownDrawable}, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[13] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        themeDescriptionArr[14] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultIcon);
        themeDescriptionArr[15] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_BACKGROUND, null, null, null, null, Theme.key_actionBarActionModeDefault);
        themeDescriptionArr[16] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_TOPBACKGROUND, null, null, null, null, Theme.key_actionBarActionModeDefaultTop);
        themeDescriptionArr[17] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultSelector);
        themeDescriptionArr[18] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch);
        themeDescriptionArr[19] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder);
        themeDescriptionArr[20] = new ThemeDescription(this.selectedMessagesCountTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultIcon);
        themeDescriptionArr[21] = new ThemeDescription(this.progressBar, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[22] = new ThemeDescription(this.emptyTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[23] = new ThemeDescription(this.listView, 0, new Class[]{GraySectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[24] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GraySectionCell.class}, null, null, null, Theme.key_graySection);
        themeDescriptionArr[25] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{SharedDocumentCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[26] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{SharedDocumentCell.class}, new String[]{"dateTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[27] = new ThemeDescription(this.listView, ThemeDescription.FLAG_PROGRESSBAR, new Class[]{SharedDocumentCell.class}, new String[]{"progressView"}, null, null, null, Theme.key_sharedMedia_startStopLoadIcon);
        themeDescriptionArr[28] = new ThemeDescription(this.listView, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{SharedDocumentCell.class}, new String[]{"statusImageView"}, null, null, null, Theme.key_sharedMedia_startStopLoadIcon);
        themeDescriptionArr[29] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOX, new Class[]{SharedDocumentCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_checkbox);
        themeDescriptionArr[30] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{SharedDocumentCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_checkboxCheck);
        themeDescriptionArr[31] = new ThemeDescription(this.listView, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{SharedDocumentCell.class}, new String[]{"thumbImageView"}, null, null, null, Theme.key_files_folderIcon);
        themeDescriptionArr[32] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{SharedDocumentCell.class}, new String[]{"extTextView"}, null, null, null, Theme.key_files_iconText);
        themeDescriptionArr[33] = new ThemeDescription(this.listView, 0, new Class[]{GraySectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[34] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GraySectionCell.class}, null, null, null, Theme.key_graySection);
        themeDescriptionArr[35] = new ThemeDescription(this.listView, 0, new Class[]{LoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[36] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOX, new Class[]{SharedLinkCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_checkbox);
        themeDescriptionArr[37] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{SharedLinkCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_checkboxCheck);
        themeDescriptionArr[38] = new ThemeDescription(this.listView, 0, new Class[]{SharedLinkCell.class}, new String[]{"titleTextPaint"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[39] = new ThemeDescription(this.listView, 0, new Class[]{SharedLinkCell.class}, null, null, null, Theme.key_windowBackgroundWhiteLinkText);
        themeDescriptionArr[40] = new ThemeDescription(this.listView, 0, new Class[]{SharedLinkCell.class}, Theme.linkSelectionPaint, null, null, Theme.key_windowBackgroundWhiteLinkSelection);
        themeDescriptionArr[41] = new ThemeDescription(this.listView, 0, new Class[]{SharedLinkCell.class}, new String[]{"letterDrawable"}, null, null, null, Theme.key_sharedMedia_linkPlaceholderText);
        themeDescriptionArr[42] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{SharedLinkCell.class}, new String[]{"letterDrawable"}, null, null, null, Theme.key_sharedMedia_linkPlaceholder);
        themeDescriptionArr[43] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{SharedMediaSectionCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[44] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SECTIONS, new Class[]{SharedMediaSectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[45] = new ThemeDescription(this.listView, 0, new Class[]{SharedMediaSectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[46] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOX, new Class[]{SharedPhotoVideoCell.class}, null, null, сellDelegate, Theme.key_checkbox);
        themeDescriptionArr[47] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{SharedPhotoVideoCell.class}, null, null, сellDelegate, Theme.key_checkboxCheck);
        themeDescriptionArr[48] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_inappPlayerBackground);
        themeDescriptionArr[49] = new ThemeDescription(this.fragmentContextView, 0, new Class[]{FragmentContextView.class}, new String[]{"playButton"}, null, null, null, Theme.key_inappPlayerPlayPause);
        themeDescriptionArr[50] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{FragmentContextView.class}, new String[]{"titleTextView"}, null, null, null, Theme.key_inappPlayerTitle);
        themeDescriptionArr[51] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_inappPlayerPerformer);
        themeDescriptionArr[52] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"closeButton"}, null, null, null, Theme.key_inappPlayerClose);
        return themeDescriptionArr;
    }
}
