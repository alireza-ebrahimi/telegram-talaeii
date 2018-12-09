package org.telegram.ui;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.SharedMediaQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterDocument;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterMusic;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterUrl;
import org.telegram.tgnet.TLRPC$TL_messages_search;
import org.telegram.tgnet.TLRPC$TL_webPageEmpty;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.Message;
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
    protected ChatFull info = null;
    private LinearLayoutManager layoutManager;
    private SharedLinksAdapter linksAdapter;
    private MediaSearchAdapter linksSearchAdapter;
    private RecyclerListView listView;
    private long mergeDialogId;
    private SharedPhotoVideoAdapter photoVideoAdapter;
    private ActionBarPopupWindowLayout popupLayout;
    private RadialProgressView progressBar;
    private LinearLayout progressView;
    private PhotoViewerProvider provider = new C49371();
    private boolean scrolling;
    private ActionBarMenuItem searchItem;
    private boolean searchWas;
    private boolean searching;
    private HashMap<Integer, MessageObject>[] selectedFiles = new HashMap[]{new HashMap(), new HashMap()};
    private NumberTextView selectedMessagesCountTextView;
    private int selectedMode;
    private SharedMediaData[] sharedMediaData = new SharedMediaData[5];

    /* renamed from: org.telegram.ui.MediaActivity$1 */
    class C49371 extends EmptyPhotoViewerProvider {
        C49371() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            if (messageObject == null || MediaActivity.this.listView == null || MediaActivity.this.selectedMode != 0) {
                return null;
            }
            int childCount = MediaActivity.this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = MediaActivity.this.listView.getChildAt(i2);
                if (childAt instanceof SharedPhotoVideoCell) {
                    SharedPhotoVideoCell sharedPhotoVideoCell = (SharedPhotoVideoCell) childAt;
                    for (int i3 = 0; i3 < 6; i3++) {
                        MessageObject messageObject2 = sharedPhotoVideoCell.getMessageObject(i3);
                        if (messageObject2 == null) {
                            continue;
                            break;
                        }
                        BackupImageView imageView = sharedPhotoVideoCell.getImageView(i3);
                        if (messageObject2.getId() == messageObject.getId()) {
                            int[] iArr = new int[2];
                            imageView.getLocationInWindow(iArr);
                            PlaceProviderObject placeProviderObject = new PlaceProviderObject();
                            placeProviderObject.viewX = iArr[0];
                            placeProviderObject.viewY = iArr[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight);
                            placeProviderObject.parentView = MediaActivity.this.listView;
                            placeProviderObject.imageReceiver = imageView.getImageReceiver();
                            placeProviderObject.thumb = placeProviderObject.imageReceiver.getBitmap();
                            placeProviderObject.parentView.getLocationInWindow(iArr);
                            placeProviderObject.clipTopAddition = AndroidUtilities.dp(40.0f);
                            return placeProviderObject;
                        }
                    }
                    continue;
                }
            }
            return null;
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$2 */
    class C49412 extends ActionBarMenuOnItemClick {

        /* renamed from: org.telegram.ui.MediaActivity$2$3 */
        class C49403 implements DialogsActivityDelegate {
            C49403() {
            }

            public void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
                ArrayList arrayList2 = new ArrayList();
                for (int i = 1; i >= 0; i--) {
                    Object arrayList3 = new ArrayList(MediaActivity.this.selectedFiles[i].keySet());
                    Collections.sort(arrayList3);
                    Iterator it = arrayList3.iterator();
                    while (it.hasNext()) {
                        Integer num = (Integer) it.next();
                        if (num.intValue() > 0) {
                            arrayList2.add(MediaActivity.this.selectedFiles[i].get(num));
                        }
                    }
                    MediaActivity.this.selectedFiles[i].clear();
                }
                MediaActivity.this.cantDeleteMessagesCount = 0;
                MediaActivity.this.actionBar.hideActionMode();
                if (arrayList.size() > 1 || ((Long) arrayList.get(0)).longValue() == ((long) UserConfig.getClientUserId()) || charSequence != null) {
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        long longValue = ((Long) arrayList.get(i2)).longValue();
                        if (charSequence != null) {
                            SendMessagesHelper.getInstance().sendMessage(charSequence.toString(), longValue, null, null, true, null, null, null);
                        }
                        SendMessagesHelper.getInstance().sendMessage(arrayList2, longValue);
                    }
                    dialogsActivity.finishFragment();
                    return;
                }
                long longValue2 = ((Long) arrayList.get(0)).longValue();
                int i3 = (int) longValue2;
                int i4 = (int) (longValue2 >> 32);
                Bundle bundle = new Bundle();
                bundle.putBoolean("scrollToTopOnResume", true);
                if (i3 == 0) {
                    bundle.putInt("enc_id", i4);
                } else if (i3 > 0) {
                    bundle.putInt("user_id", i3);
                } else if (i3 < 0) {
                    bundle.putInt("chat_id", -i3);
                }
                if (i3 == 0 || MessagesController.checkCanOpenChat(bundle, dialogsActivity)) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                    BaseFragment chatActivity = new ChatActivity(bundle);
                    MediaActivity.this.presentFragment(chatActivity, true);
                    chatActivity.showReplyPanel(true, null, arrayList2, null, false);
                    if (!AndroidUtilities.isTablet()) {
                        MediaActivity.this.removeSelfFromStack();
                    }
                }
            }
        }

        C49412() {
        }

        public void onItemClick(int i) {
            int i2;
            if (i == -1) {
                if (MediaActivity.this.actionBar.isActionModeShowed()) {
                    for (int i3 = 1; i3 >= 0; i3--) {
                        MediaActivity.this.selectedFiles[i3].clear();
                    }
                    MediaActivity.this.cantDeleteMessagesCount = 0;
                    MediaActivity.this.actionBar.hideActionMode();
                    int childCount = MediaActivity.this.listView.getChildCount();
                    for (i2 = 0; i2 < childCount; i2++) {
                        View childAt = MediaActivity.this.listView.getChildAt(i2);
                        if (childAt instanceof SharedDocumentCell) {
                            ((SharedDocumentCell) childAt).setChecked(false, true);
                        } else if (childAt instanceof SharedPhotoVideoCell) {
                            for (int i4 = 0; i4 < 6; i4++) {
                                ((SharedPhotoVideoCell) childAt).setChecked(i4, false, true);
                            }
                        } else if (childAt instanceof SharedLinkCell) {
                            ((SharedLinkCell) childAt).setChecked(false, true);
                        }
                    }
                    return;
                }
                MediaActivity.this.finishFragment();
            } else if (i == 1) {
                if (MediaActivity.this.selectedMode != 0) {
                    MediaActivity.this.selectedMode = 0;
                    MediaActivity.this.switchToCurrentSelectedMode();
                }
            } else if (i == 2) {
                if (MediaActivity.this.selectedMode != 1) {
                    MediaActivity.this.selectedMode = 1;
                    MediaActivity.this.switchToCurrentSelectedMode();
                }
            } else if (i == 5) {
                if (MediaActivity.this.selectedMode != 3) {
                    MediaActivity.this.selectedMode = 3;
                    MediaActivity.this.switchToCurrentSelectedMode();
                }
            } else if (i == 6) {
                if (MediaActivity.this.selectedMode != 4) {
                    MediaActivity.this.selectedMode = 4;
                    MediaActivity.this.switchToCurrentSelectedMode();
                }
            } else if (i == 4) {
                if (MediaActivity.this.getParentActivity() != null) {
                    Builder builder = new Builder(MediaActivity.this.getParentActivity());
                    builder.setMessage(LocaleController.formatString("AreYouSureDeleteMessages", R.string.AreYouSureDeleteMessages, new Object[]{LocaleController.formatPluralString("items", MediaActivity.this.selectedFiles[0].size() + MediaActivity.this.selectedFiles[1].size())}));
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    final boolean[] zArr = new boolean[1];
                    int access$200 = (int) MediaActivity.this.dialog_id;
                    if (access$200 != 0) {
                        User user;
                        Chat chat;
                        if (access$200 > 0) {
                            user = MessagesController.getInstance().getUser(Integer.valueOf(access$200));
                            chat = null;
                        } else {
                            user = null;
                            chat = MessagesController.getInstance().getChat(Integer.valueOf(-access$200));
                        }
                        if (!(user == null && ChatObject.isChannel(chat))) {
                            int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                            if (!((user == null || user.id == UserConfig.getClientUserId()) && chat == null)) {
                                boolean z = false;
                                for (i2 = 1; i2 >= 0; i2--) {
                                    boolean z2 = z;
                                    for (Entry value : MediaActivity.this.selectedFiles[i2].entrySet()) {
                                        MessageObject messageObject = (MessageObject) value.getValue();
                                        if (messageObject.messageOwner.action == null) {
                                            if (!messageObject.isOut()) {
                                                z = false;
                                                break;
                                            }
                                            z2 = currentTime - messageObject.messageOwner.date <= 172800 ? true : z2;
                                        }
                                    }
                                    z = z2;
                                    if (z) {
                                        break;
                                    }
                                }
                                if (z) {
                                    View frameLayout = new FrameLayout(MediaActivity.this.getParentActivity());
                                    View checkBoxCell = new CheckBoxCell(MediaActivity.this.getParentActivity(), true);
                                    checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                                    if (chat != null) {
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
                                    builder.setView(frameLayout);
                                }
                            }
                        }
                    }
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (int i2 = 1; i2 >= 0; i2--) {
                                MessageObject messageObject;
                                ArrayList arrayList = new ArrayList(MediaActivity.this.selectedFiles[i2].keySet());
                                ArrayList arrayList2 = null;
                                EncryptedChat encryptedChat = null;
                                int i3 = 0;
                                if (!arrayList.isEmpty()) {
                                    messageObject = (MessageObject) MediaActivity.this.selectedFiles[i2].get(arrayList.get(0));
                                    if (messageObject.messageOwner.to_id.channel_id != 0) {
                                        i3 = messageObject.messageOwner.to_id.channel_id;
                                    }
                                }
                                if (((int) MediaActivity.this.dialog_id) == 0) {
                                    encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (MediaActivity.this.dialog_id >> 32)));
                                }
                                if (encryptedChat != null) {
                                    arrayList2 = new ArrayList();
                                    for (Entry value : MediaActivity.this.selectedFiles[i2].entrySet()) {
                                        messageObject = (MessageObject) value.getValue();
                                        if (!(messageObject.messageOwner.random_id == 0 || messageObject.type == 10)) {
                                            arrayList2.add(Long.valueOf(messageObject.messageOwner.random_id));
                                        }
                                    }
                                }
                                MessagesController.getInstance().deleteMessages(arrayList, arrayList2, encryptedChat, i3, zArr[0]);
                                MediaActivity.this.selectedFiles[i2].clear();
                            }
                            MediaActivity.this.actionBar.hideActionMode();
                            MediaActivity.this.actionBar.closeSearchField();
                            MediaActivity.this.cantDeleteMessagesCount = 0;
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    MediaActivity.this.showDialog(builder.create());
                }
            } else if (i == 3) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("onlySelect", true);
                bundle.putInt("dialogsType", 3);
                BaseFragment dialogsActivity = new DialogsActivity(bundle);
                dialogsActivity.setDelegate(new C49403());
                MediaActivity.this.presentFragment(dialogsActivity);
            }
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$3 */
    class C49423 extends ActionBarMenuItemSearchListener {
        C49423() {
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

        public void onSearchExpand() {
            MediaActivity.this.dropDownContainer.setVisibility(8);
            MediaActivity.this.searching = true;
        }

        public void onTextChanged(EditText editText) {
            String obj = editText.getText().toString();
            if (obj.length() != 0) {
                MediaActivity.this.searchWas = true;
                MediaActivity.this.switchToCurrentSelectedMode();
            }
            if (MediaActivity.this.selectedMode == 1) {
                if (MediaActivity.this.documentsSearchAdapter != null) {
                    MediaActivity.this.documentsSearchAdapter.search(obj);
                }
            } else if (MediaActivity.this.selectedMode == 3) {
                if (MediaActivity.this.linksSearchAdapter != null) {
                    MediaActivity.this.linksSearchAdapter.search(obj);
                }
            } else if (MediaActivity.this.selectedMode == 4 && MediaActivity.this.audioSearchAdapter != null) {
                MediaActivity.this.audioSearchAdapter.search(obj);
            }
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$4 */
    class C49434 implements OnClickListener {
        C49434() {
        }

        public void onClick(View view) {
            MediaActivity.this.dropDownContainer.toggleSubMenu();
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$5 */
    class C49445 implements OnTouchListener {
        C49445() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$6 */
    class C49456 implements OnItemClickListener {
        C49456() {
        }

        public void onItemClick(View view, int i) {
            if ((MediaActivity.this.selectedMode == 1 || MediaActivity.this.selectedMode == 4) && (view instanceof SharedDocumentCell)) {
                MediaActivity.this.onItemClick(i, view, ((SharedDocumentCell) view).getMessage(), 0);
            } else if (MediaActivity.this.selectedMode == 3 && (view instanceof SharedLinkCell)) {
                MediaActivity.this.onItemClick(i, view, ((SharedLinkCell) view).getMessage(), 0);
            }
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$7 */
    class C49467 extends OnScrollListener {
        C49467() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            boolean z = true;
            if (i == 1 && MediaActivity.this.searching && MediaActivity.this.searchWas) {
                AndroidUtilities.hideKeyboard(MediaActivity.this.getParentActivity().getCurrentFocus());
            }
            MediaActivity mediaActivity = MediaActivity.this;
            if (i == 0) {
                z = false;
            }
            mediaActivity.scrolling = z;
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            int i3 = 2;
            if (!MediaActivity.this.searching || !MediaActivity.this.searchWas) {
                int findFirstVisibleItemPosition = MediaActivity.this.layoutManager.findFirstVisibleItemPosition();
                int abs = findFirstVisibleItemPosition == -1 ? 0 : Math.abs(MediaActivity.this.layoutManager.findLastVisibleItemPosition() - findFirstVisibleItemPosition) + 1;
                int itemCount = recyclerView.getAdapter().getItemCount();
                if (abs != 0 && abs + findFirstVisibleItemPosition > itemCount - 2 && !MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].loading) {
                    if (MediaActivity.this.selectedMode == 0) {
                        i3 = 0;
                    } else if (MediaActivity.this.selectedMode == 1) {
                        i3 = 1;
                    } else if (MediaActivity.this.selectedMode != 2) {
                        i3 = MediaActivity.this.selectedMode == 4 ? 4 : 3;
                    }
                    if (!MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].endReached[0]) {
                        MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].loading = true;
                        SharedMediaQuery.loadMedia(MediaActivity.this.dialog_id, 50, MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].max_id[0], i3, true, MediaActivity.this.classGuid);
                    } else if (MediaActivity.this.mergeDialogId != 0 && !MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].endReached[1]) {
                        MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].loading = true;
                        SharedMediaQuery.loadMedia(MediaActivity.this.mergeDialogId, 50, MediaActivity.this.sharedMediaData[MediaActivity.this.selectedMode].max_id[1], i3, true, MediaActivity.this.classGuid);
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.MediaActivity$8 */
    class C49478 implements OnItemLongClickListener {
        C49478() {
        }

        public boolean onItemClick(View view, int i) {
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
    class C49489 implements OnTouchListener {
        C49489() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
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
        class C49555 implements SharedLinkCellDelegate {
            C49555() {
            }

            public boolean canPerformActions() {
                return !MediaActivity.this.actionBar.isActionModeShowed();
            }

            public void needOpenWebView(TLRPC$WebPage tLRPC$WebPage) {
                MediaActivity.this.openWebView(tLRPC$WebPage);
            }
        }

        public MediaSearchAdapter(Context context, int i) {
            this.mContext = context;
            this.currentType = i;
        }

        private void processSearch(final String str) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (!MediaActivity.this.sharedMediaData[MediaSearchAdapter.this.currentType].messages.isEmpty()) {
                        if (MediaSearchAdapter.this.currentType == 1 || MediaSearchAdapter.this.currentType == 4) {
                            MessageObject messageObject = (MessageObject) MediaActivity.this.sharedMediaData[MediaSearchAdapter.this.currentType].messages.get(MediaActivity.this.sharedMediaData[MediaSearchAdapter.this.currentType].messages.size() - 1);
                            MediaSearchAdapter.this.queryServerSearch(str, messageObject.getId(), messageObject.getDialogId());
                        } else if (MediaSearchAdapter.this.currentType == 3) {
                            MediaSearchAdapter.this.queryServerSearch(str, 0, MediaActivity.this.dialog_id);
                        }
                    }
                    if (MediaSearchAdapter.this.currentType == 1 || MediaSearchAdapter.this.currentType == 4) {
                        final ArrayList arrayList = new ArrayList();
                        arrayList.addAll(MediaActivity.this.sharedMediaData[MediaSearchAdapter.this.currentType].messages);
                        Utilities.searchQueue.postRunnable(new Runnable() {
                            public void run() {
                                String toLowerCase = str.trim().toLowerCase();
                                if (toLowerCase.length() == 0) {
                                    MediaSearchAdapter.this.updateSearchResults(new ArrayList());
                                    return;
                                }
                                String translitString = LocaleController.getInstance().getTranslitString(toLowerCase);
                                String str = (toLowerCase.equals(translitString) || translitString.length() == 0) ? null : translitString;
                                String[] strArr = new String[((str != null ? 1 : 0) + 1)];
                                strArr[0] = toLowerCase;
                                if (str != null) {
                                    strArr[1] = str;
                                }
                                ArrayList arrayList = new ArrayList();
                                for (int i = 0; i < arrayList.size(); i++) {
                                    MessageObject messageObject = (MessageObject) arrayList.get(i);
                                    for (CharSequence charSequence : strArr) {
                                        String documentName = messageObject.getDocumentName();
                                        if (!(documentName == null || documentName.length() == 0)) {
                                            if (!documentName.toLowerCase().contains(charSequence)) {
                                                if (MediaSearchAdapter.this.currentType == 4) {
                                                    boolean contains;
                                                    Document document = messageObject.type == 0 ? messageObject.messageOwner.media.webpage.document : messageObject.messageOwner.media.document;
                                                    int i2 = 0;
                                                    while (i2 < document.attributes.size()) {
                                                        DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i2);
                                                        if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                                                            boolean contains2 = documentAttribute.performer != null ? documentAttribute.performer.toLowerCase().contains(charSequence) : false;
                                                            contains = (contains2 || documentAttribute.title == null) ? contains2 : documentAttribute.title.toLowerCase().contains(charSequence);
                                                            if (contains) {
                                                                arrayList.add(messageObject);
                                                                break;
                                                            }
                                                        } else {
                                                            i2++;
                                                        }
                                                    }
                                                    contains = false;
                                                    if (contains) {
                                                        arrayList.add(messageObject);
                                                        break;
                                                    }
                                                } else {
                                                    continue;
                                                }
                                            } else {
                                                arrayList.add(messageObject);
                                                break;
                                            }
                                        }
                                    }
                                }
                                MediaSearchAdapter.this.updateSearchResults(arrayList);
                            }
                        });
                    }
                }
            });
        }

        private void updateSearchResults(final ArrayList<MessageObject> arrayList) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MediaSearchAdapter.this.searchResult = arrayList;
                    MediaSearchAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public MessageObject getItem(int i) {
            return i < this.searchResult.size() ? (MessageObject) this.searchResult.get(i) : (MessageObject) this.globalSearch.get(i - this.searchResult.size());
        }

        public int getItemCount() {
            int size = this.searchResult.size();
            int size2 = this.globalSearch.size();
            return size2 != 0 ? size + size2 : size;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getItemViewType() != this.searchResult.size() + this.globalSearch.size();
        }

        public boolean isGlobalSearch(int i) {
            int size = this.searchResult.size();
            return (i < 0 || i >= size) && i > size && i <= size + this.globalSearch.size();
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = true;
            MessageObject item;
            boolean containsKey;
            if (this.currentType == 1 || this.currentType == 4) {
                SharedDocumentCell sharedDocumentCell = (SharedDocumentCell) viewHolder.itemView;
                item = getItem(i);
                sharedDocumentCell.setDocument(item, i != getItemCount() + -1);
                if (MediaActivity.this.actionBar.isActionModeShowed()) {
                    containsKey = MediaActivity.this.selectedFiles[item.getDialogId() == MediaActivity.this.dialog_id ? 0 : 1].containsKey(Integer.valueOf(item.getId()));
                    if (MediaActivity.this.scrolling) {
                        z = false;
                    }
                    sharedDocumentCell.setChecked(containsKey, z);
                    return;
                }
                if (MediaActivity.this.scrolling) {
                    z = false;
                }
                sharedDocumentCell.setChecked(false, z);
            } else if (this.currentType == 3) {
                SharedLinkCell sharedLinkCell = (SharedLinkCell) viewHolder.itemView;
                item = getItem(i);
                sharedLinkCell.setLink(item, i != getItemCount() + -1);
                if (MediaActivity.this.actionBar.isActionModeShowed()) {
                    containsKey = MediaActivity.this.selectedFiles[item.getDialogId() == MediaActivity.this.dialog_id ? 0 : 1].containsKey(Integer.valueOf(item.getId()));
                    if (MediaActivity.this.scrolling) {
                        z = false;
                    }
                    sharedLinkCell.setChecked(containsKey, z);
                    return;
                }
                if (MediaActivity.this.scrolling) {
                    z = false;
                }
                sharedLinkCell.setChecked(false, z);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View sharedDocumentCell;
            if (this.currentType == 1 || this.currentType == 4) {
                sharedDocumentCell = new SharedDocumentCell(this.mContext);
            } else {
                sharedDocumentCell = new SharedLinkCell(this.mContext);
                ((SharedLinkCell) sharedDocumentCell).setDelegate(new C49555());
            }
            return new Holder(sharedDocumentCell);
        }

        public void queryServerSearch(String str, final int i, long j) {
            int i2 = (int) j;
            if (i2 != 0) {
                if (this.reqId != 0) {
                    ConnectionsManager.getInstance().cancelRequest(this.reqId, true);
                    this.reqId = 0;
                }
                if (str == null || str.length() == 0) {
                    this.globalSearch.clear();
                    this.lastReqId = 0;
                    notifyDataSetChanged();
                    return;
                }
                TLObject tLRPC$TL_messages_search = new TLRPC$TL_messages_search();
                tLRPC$TL_messages_search.limit = 50;
                tLRPC$TL_messages_search.offset_id = i;
                if (this.currentType == 1) {
                    tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterDocument();
                } else if (this.currentType == 3) {
                    tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterUrl();
                } else if (this.currentType == 4) {
                    tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterMusic();
                }
                tLRPC$TL_messages_search.f10166q = str;
                tLRPC$TL_messages_search.peer = MessagesController.getInputPeer(i2);
                if (tLRPC$TL_messages_search.peer != null) {
                    i2 = this.lastReqId + 1;
                    this.lastReqId = i2;
                    this.reqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_search, new RequestDelegate() {
                        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            final ArrayList arrayList = new ArrayList();
                            if (tLRPC$TL_error == null) {
                                TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                                for (int i = 0; i < tLRPC$messages_Messages.messages.size(); i++) {
                                    Message message = (Message) tLRPC$messages_Messages.messages.get(i);
                                    if (i == 0 || message.id <= i) {
                                        arrayList.add(new MessageObject(message, null, false));
                                    }
                                }
                            }
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (i2 == MediaSearchAdapter.this.lastReqId) {
                                        MediaSearchAdapter.this.globalSearch = arrayList;
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

        public void search(final String str) {
            try {
                if (this.searchTimer != null) {
                    this.searchTimer.cancel();
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (str == null) {
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
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    MediaSearchAdapter.this.processSearch(str);
                }
            }, 200, 300);
        }
    }

    private class SharedDocumentsAdapter extends SectionsAdapter {
        private int currentType;
        private Context mContext;

        public SharedDocumentsAdapter(Context context, int i) {
            this.mContext = context;
            this.currentType = i;
        }

        public int getCountForSection(int i) {
            return i < MediaActivity.this.sharedMediaData[this.currentType].sections.size() ? ((ArrayList) MediaActivity.this.sharedMediaData[this.currentType].sectionArrays.get(MediaActivity.this.sharedMediaData[this.currentType].sections.get(i))).size() + 1 : 1;
        }

        public Object getItem(int i, int i2) {
            return null;
        }

        public int getItemViewType(int i, int i2) {
            return i < MediaActivity.this.sharedMediaData[this.currentType].sections.size() ? i2 == 0 ? 0 : 1 : 2;
        }

        public String getLetter(int i) {
            return null;
        }

        public int getPositionForScrollProgress(float f) {
            return 0;
        }

        public int getSectionCount() {
            int i = 1;
            int size = MediaActivity.this.sharedMediaData[this.currentType].sections.size();
            if (MediaActivity.this.sharedMediaData[this.currentType].sections.isEmpty() || (MediaActivity.this.sharedMediaData[this.currentType].endReached[0] && MediaActivity.this.sharedMediaData[this.currentType].endReached[1])) {
                i = 0;
            }
            return i + size;
        }

        public View getSectionHeaderView(int i, View view) {
            View graySectionCell = view == null ? new GraySectionCell(this.mContext) : view;
            if (i < MediaActivity.this.sharedMediaData[this.currentType].sections.size()) {
                ((GraySectionCell) graySectionCell).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) ((ArrayList) MediaActivity.this.sharedMediaData[this.currentType].sectionArrays.get((String) MediaActivity.this.sharedMediaData[this.currentType].sections.get(i))).get(0)).messageOwner.date) * 1000).toUpperCase());
            }
            return graySectionCell;
        }

        public boolean isEnabled(int i, int i2) {
            return i2 != 0;
        }

        public void onBindViewHolder(int i, int i2, ViewHolder viewHolder) {
            boolean z = true;
            if (viewHolder.getItemViewType() != 2) {
                ArrayList arrayList = (ArrayList) MediaActivity.this.sharedMediaData[this.currentType].sectionArrays.get((String) MediaActivity.this.sharedMediaData[this.currentType].sections.get(i));
                switch (viewHolder.getItemViewType()) {
                    case 0:
                        ((GraySectionCell) viewHolder.itemView).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) arrayList.get(0)).messageOwner.date) * 1000).toUpperCase());
                        return;
                    case 1:
                        SharedDocumentCell sharedDocumentCell = (SharedDocumentCell) viewHolder.itemView;
                        MessageObject messageObject = (MessageObject) arrayList.get(i2 - 1);
                        boolean z2 = i2 != arrayList.size() || (i == MediaActivity.this.sharedMediaData[this.currentType].sections.size() - 1 && MediaActivity.this.sharedMediaData[this.currentType].loading);
                        sharedDocumentCell.setDocument(messageObject, z2);
                        if (MediaActivity.this.actionBar.isActionModeShowed()) {
                            z2 = MediaActivity.this.selectedFiles[messageObject.getDialogId() == MediaActivity.this.dialog_id ? 0 : 1].containsKey(Integer.valueOf(messageObject.getId()));
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

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View graySectionCell;
            switch (i) {
                case 0:
                    graySectionCell = new GraySectionCell(this.mContext);
                    break;
                case 1:
                    graySectionCell = new SharedDocumentCell(this.mContext);
                    break;
                default:
                    graySectionCell = new LoadingCell(this.mContext);
                    break;
            }
            return new Holder(graySectionCell);
        }
    }

    private class SharedLinksAdapter extends SectionsAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.MediaActivity$SharedLinksAdapter$1 */
        class C49561 implements SharedLinkCellDelegate {
            C49561() {
            }

            public boolean canPerformActions() {
                return !MediaActivity.this.actionBar.isActionModeShowed();
            }

            public void needOpenWebView(TLRPC$WebPage tLRPC$WebPage) {
                MediaActivity.this.openWebView(tLRPC$WebPage);
            }
        }

        public SharedLinksAdapter(Context context) {
            this.mContext = context;
        }

        public int getCountForSection(int i) {
            return i < MediaActivity.this.sharedMediaData[3].sections.size() ? ((ArrayList) MediaActivity.this.sharedMediaData[3].sectionArrays.get(MediaActivity.this.sharedMediaData[3].sections.get(i))).size() + 1 : 1;
        }

        public Object getItem(int i, int i2) {
            return null;
        }

        public int getItemViewType(int i, int i2) {
            return i < MediaActivity.this.sharedMediaData[3].sections.size() ? i2 == 0 ? 0 : 1 : 2;
        }

        public String getLetter(int i) {
            return null;
        }

        public int getPositionForScrollProgress(float f) {
            return 0;
        }

        public int getSectionCount() {
            int i = 1;
            int size = MediaActivity.this.sharedMediaData[3].sections.size();
            if (MediaActivity.this.sharedMediaData[3].sections.isEmpty() || (MediaActivity.this.sharedMediaData[3].endReached[0] && MediaActivity.this.sharedMediaData[3].endReached[1])) {
                i = 0;
            }
            return i + size;
        }

        public View getSectionHeaderView(int i, View view) {
            View graySectionCell = view == null ? new GraySectionCell(this.mContext) : view;
            if (i < MediaActivity.this.sharedMediaData[3].sections.size()) {
                ((GraySectionCell) graySectionCell).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) ((ArrayList) MediaActivity.this.sharedMediaData[3].sectionArrays.get((String) MediaActivity.this.sharedMediaData[3].sections.get(i))).get(0)).messageOwner.date) * 1000).toUpperCase());
            }
            return graySectionCell;
        }

        public boolean isEnabled(int i, int i2) {
            return i2 != 0;
        }

        public void onBindViewHolder(int i, int i2, ViewHolder viewHolder) {
            boolean z = true;
            if (viewHolder.getItemViewType() != 2) {
                ArrayList arrayList = (ArrayList) MediaActivity.this.sharedMediaData[3].sectionArrays.get((String) MediaActivity.this.sharedMediaData[3].sections.get(i));
                switch (viewHolder.getItemViewType()) {
                    case 0:
                        ((GraySectionCell) viewHolder.itemView).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) arrayList.get(0)).messageOwner.date) * 1000).toUpperCase());
                        return;
                    case 1:
                        SharedLinkCell sharedLinkCell = (SharedLinkCell) viewHolder.itemView;
                        MessageObject messageObject = (MessageObject) arrayList.get(i2 - 1);
                        boolean z2 = i2 != arrayList.size() || (i == MediaActivity.this.sharedMediaData[3].sections.size() - 1 && MediaActivity.this.sharedMediaData[3].loading);
                        sharedLinkCell.setLink(messageObject, z2);
                        if (MediaActivity.this.actionBar.isActionModeShowed()) {
                            z2 = MediaActivity.this.selectedFiles[messageObject.getDialogId() == MediaActivity.this.dialog_id ? 0 : 1].containsKey(Integer.valueOf(messageObject.getId()));
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

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View graySectionCell;
            switch (i) {
                case 0:
                    graySectionCell = new GraySectionCell(this.mContext);
                    break;
                case 1:
                    graySectionCell = new SharedLinkCell(this.mContext);
                    ((SharedLinkCell) graySectionCell).setDelegate(new C49561());
                    break;
                default:
                    graySectionCell = new LoadingCell(this.mContext);
                    break;
            }
            return new Holder(graySectionCell);
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

        public boolean addMessage(MessageObject messageObject, boolean z, boolean z2) {
            int i = messageObject.getDialogId() == MediaActivity.this.dialog_id ? 0 : 1;
            if (this.messagesDict[i].containsKey(Integer.valueOf(messageObject.getId()))) {
                return false;
            }
            ArrayList arrayList = (ArrayList) this.sectionArrays.get(messageObject.monthKey);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.sectionArrays.put(messageObject.monthKey, arrayList);
                if (z) {
                    this.sections.add(0, messageObject.monthKey);
                } else {
                    this.sections.add(messageObject.monthKey);
                }
            }
            if (z) {
                arrayList.add(0, messageObject);
                this.messages.add(0, messageObject);
            } else {
                arrayList.add(messageObject);
                this.messages.add(messageObject);
            }
            this.messagesDict[i].put(Integer.valueOf(messageObject.getId()), messageObject);
            if (z2) {
                this.max_id[i] = Math.max(messageObject.getId(), this.max_id[i]);
            } else if (messageObject.getId() > 0) {
                this.max_id[i] = Math.min(messageObject.getId(), this.max_id[i]);
            }
            return true;
        }

        public boolean deleteMessage(int i, int i2) {
            MessageObject messageObject = (MessageObject) this.messagesDict[i2].get(Integer.valueOf(i));
            if (messageObject == null) {
                return false;
            }
            ArrayList arrayList = (ArrayList) this.sectionArrays.get(messageObject.monthKey);
            if (arrayList == null) {
                return false;
            }
            arrayList.remove(messageObject);
            this.messages.remove(messageObject);
            this.messagesDict[i2].remove(Integer.valueOf(messageObject.getId()));
            if (arrayList.isEmpty()) {
                this.sectionArrays.remove(messageObject.monthKey);
                this.sections.remove(messageObject.monthKey);
            }
            this.totalCount--;
            return true;
        }

        public void replaceMid(int i, int i2) {
            MessageObject messageObject = (MessageObject) this.messagesDict[0].get(Integer.valueOf(i));
            if (messageObject != null) {
                this.messagesDict[0].remove(Integer.valueOf(i));
                this.messagesDict[0].put(Integer.valueOf(i2), messageObject);
                messageObject.messageOwner.id = i2;
            }
        }
    }

    private class SharedPhotoVideoAdapter extends SectionsAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.MediaActivity$SharedPhotoVideoAdapter$1 */
        class C49571 implements SharedPhotoVideoCellDelegate {
            C49571() {
            }

            public void didClickItem(SharedPhotoVideoCell sharedPhotoVideoCell, int i, MessageObject messageObject, int i2) {
                MediaActivity.this.onItemClick(i, sharedPhotoVideoCell, messageObject, i2);
            }

            public boolean didLongClickItem(SharedPhotoVideoCell sharedPhotoVideoCell, int i, MessageObject messageObject, int i2) {
                return MediaActivity.this.onItemLongClick(messageObject, sharedPhotoVideoCell, i2);
            }
        }

        public SharedPhotoVideoAdapter(Context context) {
            this.mContext = context;
        }

        public int getCountForSection(int i) {
            return i < MediaActivity.this.sharedMediaData[0].sections.size() ? ((int) Math.ceil((double) (((float) ((ArrayList) MediaActivity.this.sharedMediaData[0].sectionArrays.get(MediaActivity.this.sharedMediaData[0].sections.get(i))).size()) / ((float) MediaActivity.this.columnsCount)))) + 1 : 1;
        }

        public Object getItem(int i, int i2) {
            return null;
        }

        public int getItemViewType(int i, int i2) {
            return i < MediaActivity.this.sharedMediaData[0].sections.size() ? i2 == 0 ? 0 : 1 : 2;
        }

        public String getLetter(int i) {
            return null;
        }

        public int getPositionForScrollProgress(float f) {
            return 0;
        }

        public int getSectionCount() {
            int i = 1;
            int size = MediaActivity.this.sharedMediaData[0].sections.size();
            if (MediaActivity.this.sharedMediaData[0].sections.isEmpty() || (MediaActivity.this.sharedMediaData[0].endReached[0] && MediaActivity.this.sharedMediaData[0].endReached[1])) {
                i = 0;
            }
            return i + size;
        }

        public View getSectionHeaderView(int i, View view) {
            View sharedMediaSectionCell;
            if (view == null) {
                sharedMediaSectionCell = new SharedMediaSectionCell(this.mContext);
                sharedMediaSectionCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else {
                sharedMediaSectionCell = view;
            }
            if (i < MediaActivity.this.sharedMediaData[0].sections.size()) {
                ((SharedMediaSectionCell) sharedMediaSectionCell).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) ((ArrayList) MediaActivity.this.sharedMediaData[0].sectionArrays.get((String) MediaActivity.this.sharedMediaData[0].sections.get(i))).get(0)).messageOwner.date) * 1000).toUpperCase());
            }
            return sharedMediaSectionCell;
        }

        public boolean isEnabled(int i, int i2) {
            return false;
        }

        public void onBindViewHolder(int i, int i2, ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() != 2) {
                ArrayList arrayList = (ArrayList) MediaActivity.this.sharedMediaData[0].sectionArrays.get((String) MediaActivity.this.sharedMediaData[0].sections.get(i));
                switch (viewHolder.getItemViewType()) {
                    case 0:
                        ((SharedMediaSectionCell) viewHolder.itemView).setText(LocaleController.getInstance().formatterMonthYear.format(((long) ((MessageObject) arrayList.get(0)).messageOwner.date) * 1000).toUpperCase());
                        return;
                    case 1:
                        SharedPhotoVideoCell sharedPhotoVideoCell = (SharedPhotoVideoCell) viewHolder.itemView;
                        sharedPhotoVideoCell.setItemsCount(MediaActivity.this.columnsCount);
                        for (int i3 = 0; i3 < MediaActivity.this.columnsCount; i3++) {
                            int access$3000 = ((i2 - 1) * MediaActivity.this.columnsCount) + i3;
                            if (access$3000 < arrayList.size()) {
                                MessageObject messageObject = (MessageObject) arrayList.get(access$3000);
                                sharedPhotoVideoCell.setIsFirst(i2 == 1);
                                sharedPhotoVideoCell.setItem(i3, MediaActivity.this.sharedMediaData[0].messages.indexOf(messageObject), messageObject);
                                if (MediaActivity.this.actionBar.isActionModeShowed()) {
                                    sharedPhotoVideoCell.setChecked(i3, MediaActivity.this.selectedFiles[messageObject.getDialogId() == MediaActivity.this.dialog_id ? 0 : 1].containsKey(Integer.valueOf(messageObject.getId())), !MediaActivity.this.scrolling);
                                } else {
                                    sharedPhotoVideoCell.setChecked(i3, false, !MediaActivity.this.scrolling);
                                }
                            } else {
                                sharedPhotoVideoCell.setItem(i3, access$3000, null);
                            }
                        }
                        sharedPhotoVideoCell.requestLayout();
                        return;
                    default:
                        return;
                }
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View sharedMediaSectionCell;
            switch (i) {
                case 0:
                    sharedMediaSectionCell = new SharedMediaSectionCell(this.mContext);
                    break;
                case 1:
                    if (MediaActivity.this.cellCache.isEmpty()) {
                        sharedMediaSectionCell = new SharedPhotoVideoCell(this.mContext);
                    } else {
                        View view = (View) MediaActivity.this.cellCache.get(0);
                        MediaActivity.this.cellCache.remove(0);
                        sharedMediaSectionCell = view;
                    }
                    ((SharedPhotoVideoCell) sharedMediaSectionCell).setDelegate(new C49571());
                    break;
                default:
                    sharedMediaSectionCell = new LoadingCell(this.mContext);
                    break;
            }
            return new Holder(sharedMediaSectionCell);
        }
    }

    public MediaActivity(Bundle bundle) {
        super(bundle);
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

    private void onItemClick(int i, View view, MessageObject messageObject, int i2) {
        if (messageObject != null) {
            if (this.actionBar.isActionModeShowed()) {
                int i3 = messageObject.getDialogId() == this.dialog_id ? 0 : 1;
                if (this.selectedFiles[i3].containsKey(Integer.valueOf(messageObject.getId()))) {
                    this.selectedFiles[i3].remove(Integer.valueOf(messageObject.getId()));
                    if (!messageObject.canDeleteMessage(null)) {
                        this.cantDeleteMessagesCount--;
                    }
                } else if (this.selectedFiles[0].size() + this.selectedFiles[1].size() < 100) {
                    this.selectedFiles[i3].put(Integer.valueOf(messageObject.getId()), messageObject);
                    if (!messageObject.canDeleteMessage(null)) {
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
                    ((SharedDocumentCell) view).setChecked(this.selectedFiles[i3].containsKey(Integer.valueOf(messageObject.getId())), true);
                } else if (view instanceof SharedPhotoVideoCell) {
                    ((SharedPhotoVideoCell) view).setChecked(i2, this.selectedFiles[i3].containsKey(Integer.valueOf(messageObject.getId())), true);
                } else if (view instanceof SharedLinkCell) {
                    ((SharedLinkCell) view).setChecked(this.selectedFiles[i3].containsKey(Integer.valueOf(messageObject.getId())), true);
                }
            } else if (this.selectedMode == 0) {
                PhotoViewer.getInstance().setParentActivity(getParentActivity());
                PhotoViewer.getInstance().openPhoto(this.sharedMediaData[this.selectedMode].messages, i, this.dialog_id, this.mergeDialogId, this.provider);
            } else if (this.selectedMode == 1 || this.selectedMode == 4) {
                if (view instanceof SharedDocumentCell) {
                    SharedDocumentCell sharedDocumentCell = (SharedDocumentCell) view;
                    if (sharedDocumentCell.isLoaded()) {
                        if (!messageObject.isMusic() || !MediaController.getInstance().setPlaylist(this.sharedMediaData[this.selectedMode].messages, messageObject)) {
                            r0 = messageObject.messageOwner.media != null ? FileLoader.getAttachFileName(messageObject.getDocument()) : TtmlNode.ANONYMOUS_REGION_ID;
                            File file = (messageObject.messageOwner.attachPath == null || messageObject.messageOwner.attachPath.length() == 0) ? null : new File(messageObject.messageOwner.attachPath);
                            File pathToMessage = (file == null || !(file == null || file.exists())) ? FileLoader.getPathToMessage(messageObject.messageOwner) : file;
                            if (pathToMessage != null && pathToMessage.exists()) {
                                Builder builder;
                                if (pathToMessage.getName().endsWith("attheme")) {
                                    ThemeInfo applyThemeFile = Theme.applyThemeFile(pathToMessage, messageObject.getDocumentName(), true);
                                    if (applyThemeFile != null) {
                                        presentFragment(new ThemePreviewActivity(pathToMessage, applyThemeFile));
                                        return;
                                    }
                                    builder = new Builder(getParentActivity());
                                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                    builder.setMessage(LocaleController.getString("IncorrectTheme", R.string.IncorrectTheme));
                                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                    showDialog(builder.create());
                                    return;
                                }
                                try {
                                    String mimeTypeFromExtension;
                                    Intent intent = new Intent("android.intent.action.VIEW");
                                    intent.setFlags(1);
                                    MimeTypeMap singleton = MimeTypeMap.getSingleton();
                                    int lastIndexOf = r0.lastIndexOf(46);
                                    if (lastIndexOf != -1) {
                                        mimeTypeFromExtension = singleton.getMimeTypeFromExtension(r0.substring(lastIndexOf + 1).toLowerCase());
                                        if (mimeTypeFromExtension == null) {
                                            mimeTypeFromExtension = messageObject.getDocument().mime_type;
                                            if (mimeTypeFromExtension == null || mimeTypeFromExtension.length() == 0) {
                                                mimeTypeFromExtension = null;
                                            }
                                        }
                                    } else {
                                        mimeTypeFromExtension = null;
                                    }
                                    if (VERSION.SDK_INT >= 24) {
                                        intent.setDataAndType(FileProvider.a(getParentActivity(), "org.ir.talaeii.provider", pathToMessage), mimeTypeFromExtension != null ? mimeTypeFromExtension : "text/plain");
                                    } else {
                                        intent.setDataAndType(Uri.fromFile(pathToMessage), mimeTypeFromExtension != null ? mimeTypeFromExtension : "text/plain");
                                    }
                                    if (mimeTypeFromExtension != null) {
                                        try {
                                            getParentActivity().startActivityForResult(intent, ChatActivity.startAllServices);
                                            return;
                                        } catch (Exception e) {
                                            if (VERSION.SDK_INT >= 24) {
                                                intent.setDataAndType(FileProvider.a(getParentActivity(), "org.ir.talaeii.provider", pathToMessage), "text/plain");
                                            } else {
                                                intent.setDataAndType(Uri.fromFile(pathToMessage), "text/plain");
                                            }
                                            getParentActivity().startActivityForResult(intent, ChatActivity.startAllServices);
                                            return;
                                        }
                                    }
                                    getParentActivity().startActivityForResult(intent, ChatActivity.startAllServices);
                                } catch (Exception e2) {
                                    if (getParentActivity() != null) {
                                        builder = new Builder(getParentActivity());
                                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                        builder.setMessage(LocaleController.formatString("NoHandleAppInstalled", R.string.NoHandleAppInstalled, new Object[]{messageObject.getDocument().mime_type}));
                                        showDialog(builder.create());
                                    }
                                }
                            }
                        }
                    } else if (sharedDocumentCell.isLoading()) {
                        FileLoader.getInstance().cancelLoadFile(sharedDocumentCell.getMessage().getDocument());
                        sharedDocumentCell.updateFileExistIcon();
                    } else {
                        FileLoader.getInstance().loadFile(sharedDocumentCell.getMessage().getDocument(), false, 0);
                        sharedDocumentCell.updateFileExistIcon();
                    }
                }
            } else if (this.selectedMode == 3) {
                try {
                    TLRPC$WebPage tLRPC$WebPage = messageObject.messageOwner.media.webpage;
                    if (tLRPC$WebPage == null || (tLRPC$WebPage instanceof TLRPC$TL_webPageEmpty)) {
                        r0 = null;
                    } else if (tLRPC$WebPage.embed_url == null || tLRPC$WebPage.embed_url.length() == 0) {
                        r0 = tLRPC$WebPage.url;
                    } else {
                        openWebView(tLRPC$WebPage);
                        return;
                    }
                    if (r0 == null) {
                        r0 = ((SharedLinkCell) view).getLink(0);
                    }
                    if (r0 != null) {
                        Browser.openUrl(getParentActivity(), r0);
                    }
                } catch (Throwable e3) {
                    FileLog.e(e3);
                }
            }
        }
    }

    private boolean onItemLongClick(MessageObject messageObject, View view, int i) {
        if (this.actionBar.isActionModeShowed()) {
            return false;
        }
        AndroidUtilities.hideKeyboard(getParentActivity().getCurrentFocus());
        this.selectedFiles[messageObject.getDialogId() == this.dialog_id ? 0 : 1].put(Integer.valueOf(messageObject.getId()), messageObject);
        if (!messageObject.canDeleteMessage(null)) {
            this.cantDeleteMessagesCount++;
        }
        this.actionBar.createActionMode().getItem(4).setVisibility(this.cantDeleteMessagesCount == 0 ? 0 : 8);
        this.selectedMessagesCountTextView.setNumber(1, false);
        AnimatorSet animatorSet = new AnimatorSet();
        Collection arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.actionModeViews.size(); i2++) {
            View view2 = (View) this.actionModeViews.get(i2);
            AndroidUtilities.clearDrawableAnimation(view2);
            arrayList.add(ObjectAnimator.ofFloat(view2, "scaleY", new float[]{0.1f, 1.0f}));
        }
        animatorSet.playTogether(arrayList);
        animatorSet.setDuration(250);
        animatorSet.start();
        this.scrolling = false;
        if (view instanceof SharedDocumentCell) {
            ((SharedDocumentCell) view).setChecked(true, true);
        } else if (view instanceof SharedPhotoVideoCell) {
            ((SharedPhotoVideoCell) view).setChecked(i, true, true);
        } else if (view instanceof SharedLinkCell) {
            ((SharedLinkCell) view).setChecked(true, true);
        }
        this.actionBar.showActionMode();
        return true;
    }

    private void openWebView(TLRPC$WebPage tLRPC$WebPage) {
        EmbedBottomSheet.show(getParentActivity(), tLRPC$WebPage.site_name, tLRPC$WebPage.description, tLRPC$WebPage.url, tLRPC$WebPage.embed_url, tLRPC$WebPage.embed_width, tLRPC$WebPage.embed_height);
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
            this.searchItem.setVisibility(!this.sharedMediaData[this.selectedMode].messages.isEmpty() ? 0 : 8);
            if (!(this.sharedMediaData[this.selectedMode].loading || this.sharedMediaData[this.selectedMode].endReached[0] || !this.sharedMediaData[this.selectedMode].messages.isEmpty())) {
                this.sharedMediaData[this.selectedMode].loading = true;
                SharedMediaQuery.loadMedia(this.dialog_id, 50, 0, this.selectedMode == 1 ? 1 : 4, true, this.classGuid);
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
            this.searchItem.setVisibility(!this.sharedMediaData[3].messages.isEmpty() ? 0 : 8);
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

    public View createView(Context context) {
        int i;
        this.actionBar.setBackButtonDrawable(new BackDrawable(false));
        this.actionBar.setTitle(TtmlNode.ANONYMOUS_REGION_ID);
        this.actionBar.setAllowOverlayTitle(false);
        this.actionBar.setActionBarMenuOnItemClick(new C49412());
        for (i = 1; i >= 0; i--) {
            this.selectedFiles[i].clear();
        }
        this.cantDeleteMessagesCount = 0;
        this.actionModeViews.clear();
        ActionBarMenu createMenu = this.actionBar.createMenu();
        this.searchItem = createMenu.addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C49423());
        this.searchItem.getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.searchItem.setVisibility(8);
        this.dropDownContainer = new ActionBarMenuItem(context, createMenu, 0, 0);
        this.dropDownContainer.setSubMenuOpenSide(1);
        this.dropDownContainer.addSubItem(1, LocaleController.getString("SharedMediaTitle", R.string.SharedMediaTitle));
        this.dropDownContainer.addSubItem(2, LocaleController.getString("DocumentsTitle", R.string.DocumentsTitle));
        if (((int) this.dialog_id) != 0) {
            this.dropDownContainer.addSubItem(5, LocaleController.getString("LinksTitle", R.string.LinksTitle));
            this.dropDownContainer.addSubItem(6, LocaleController.getString("AudioTitle", R.string.AudioTitle));
        } else {
            EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (this.dialog_id >> 32)));
            if (encryptedChat != null && AndroidUtilities.getPeerLayerVersion(encryptedChat.layer) >= 46) {
                this.dropDownContainer.addSubItem(6, LocaleController.getString("AudioTitle", R.string.AudioTitle));
            }
        }
        this.actionBar.addView(this.dropDownContainer, 0, LayoutHelper.createFrame(-2, -1.0f, 51, AndroidUtilities.isTablet() ? 64.0f : 56.0f, BitmapDescriptorFactory.HUE_RED, 40.0f, BitmapDescriptorFactory.HUE_RED));
        this.dropDownContainer.setOnClickListener(new C49434());
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
        this.dropDownContainer.addView(this.dropDown, LayoutHelper.createFrame(-2, -2.0f, 16, 16.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        ActionBarMenu createActionMode = this.actionBar.createActionMode();
        this.selectedMessagesCountTextView = new NumberTextView(createActionMode.getContext());
        this.selectedMessagesCountTextView.setTextSize(18);
        this.selectedMessagesCountTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.selectedMessagesCountTextView.setTextColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon));
        this.selectedMessagesCountTextView.setOnTouchListener(new C49445());
        createActionMode.addView(this.selectedMessagesCountTextView, LayoutHelper.createLinear(0, -1, 1.0f, 65, 0, 0, 0));
        if (((int) this.dialog_id) != 0) {
            this.actionModeViews.add(createActionMode.addItemWithWidth(3, R.drawable.ic_ab_forward, AndroidUtilities.dp(54.0f)));
        }
        this.actionModeViews.add(createActionMode.addItemWithWidth(4, R.drawable.ic_ab_delete, AndroidUtilities.dp(54.0f)));
        this.photoVideoAdapter = new SharedPhotoVideoAdapter(context);
        this.documentsAdapter = new SharedDocumentsAdapter(context, 1);
        this.audioAdapter = new SharedDocumentsAdapter(context, 4);
        this.documentsSearchAdapter = new MediaSearchAdapter(context, 1);
        this.audioSearchAdapter = new MediaSearchAdapter(context, 4);
        this.linksSearchAdapter = new MediaSearchAdapter(context, 3);
        this.linksAdapter = new SharedLinksAdapter(context);
        View frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        i = -1;
        int i2 = 0;
        if (this.layoutManager != null) {
            int findFirstVisibleItemPosition = this.layoutManager.findFirstVisibleItemPosition();
            if (findFirstVisibleItemPosition != this.layoutManager.getItemCount() - 1) {
                Holder holder = (Holder) this.listView.findViewHolderForAdapterPosition(findFirstVisibleItemPosition);
                if (holder != null) {
                    i = holder.itemView.getTop();
                    i2 = findFirstVisibleItemPosition;
                } else {
                    i2 = -1;
                    i = 0;
                }
                int i3 = i;
                i = i2;
                i2 = i3;
            } else {
                i = -1;
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
        this.listView.setOnItemClickListener(new C49456());
        this.listView.setOnScrollListener(new C49467());
        this.listView.setOnItemLongClickListener(new C49478());
        if (i != -1) {
            this.layoutManager.scrollToPositionWithOffset(i, i2);
        }
        for (i = 0; i < 6; i++) {
            this.cellCache.add(new SharedPhotoVideoCell(context));
        }
        this.emptyView = new LinearLayout(context);
        this.emptyView.setOrientation(1);
        this.emptyView.setGravity(17);
        this.emptyView.setVisibility(8);
        this.emptyView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.emptyView.setOnTouchListener(new C49489());
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
            frameLayout.addView(fragmentContextView, LayoutHelper.createFrame(-1, 39.0f, 51, BitmapDescriptorFactory.HUE_RED, -36.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        }
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int intValue;
        ArrayList arrayList;
        boolean z;
        int i2;
        int i3;
        ActionBarMenuItem actionBarMenuItem;
        int i4;
        if (i == NotificationCenter.mediaDidLoaded) {
            long longValue = ((Long) objArr[0]).longValue();
            if (((Integer) objArr[3]).intValue() == this.classGuid) {
                intValue = ((Integer) objArr[4]).intValue();
                this.sharedMediaData[intValue].loading = false;
                this.sharedMediaData[intValue].totalCount = ((Integer) objArr[1]).intValue();
                arrayList = (ArrayList) objArr[2];
                z = ((int) this.dialog_id) == 0;
                i2 = longValue == this.dialog_id ? 0 : 1;
                for (i3 = 0; i3 < arrayList.size(); i3++) {
                    this.sharedMediaData[intValue].addMessage((MessageObject) arrayList.get(i3), false, z);
                }
                this.sharedMediaData[intValue].endReached[i2] = ((Boolean) objArr[5]).booleanValue();
                if (i2 == 0 && this.sharedMediaData[intValue].endReached[i2] && this.mergeDialogId != 0) {
                    this.sharedMediaData[intValue].loading = true;
                    SharedMediaQuery.loadMedia(this.mergeDialogId, 50, this.sharedMediaData[intValue].max_id[1], intValue, true, this.classGuid);
                }
                if (!this.sharedMediaData[intValue].loading) {
                    if (this.progressView != null) {
                        this.progressView.setVisibility(8);
                    }
                    if (this.selectedMode == intValue && this.listView != null && this.listView.getEmptyView() == null) {
                        this.listView.setEmptyView(this.emptyView);
                    }
                }
                this.scrolling = true;
                if (this.selectedMode == 0 && intValue == 0) {
                    if (this.photoVideoAdapter != null) {
                        this.photoVideoAdapter.notifyDataSetChanged();
                    }
                } else if (this.selectedMode == 1 && intValue == 1) {
                    if (this.documentsAdapter != null) {
                        this.documentsAdapter.notifyDataSetChanged();
                    }
                } else if (this.selectedMode == 3 && intValue == 3) {
                    if (this.linksAdapter != null) {
                        this.linksAdapter.notifyDataSetChanged();
                    }
                } else if (this.selectedMode == 4 && intValue == 4 && this.audioAdapter != null) {
                    this.audioAdapter.notifyDataSetChanged();
                }
                if (this.selectedMode == 1 || this.selectedMode == 3 || this.selectedMode == 4) {
                    actionBarMenuItem = this.searchItem;
                    i4 = (this.sharedMediaData[intValue].messages.isEmpty() || this.searching) ? 8 : 0;
                    actionBarMenuItem.setVisibility(i4);
                }
            }
        } else if (i == NotificationCenter.messagesDeleted) {
            int i5;
            Chat chat = ((int) this.dialog_id) < 0 ? MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id))) : null;
            r2 = ((Integer) objArr[1]).intValue();
            if (ChatObject.isChannel(chat)) {
                if (r2 == 0 && this.mergeDialogId != 0) {
                    i5 = 1;
                } else if (r2 == chat.id) {
                    i5 = 0;
                } else {
                    return;
                }
            } else if (r2 == 0) {
                i5 = 0;
            } else {
                return;
            }
            Object obj = null;
            Iterator it = ((ArrayList) objArr[0]).iterator();
            while (it.hasNext()) {
                r0 = (Integer) it.next();
                for (SharedMediaData deleteMessage : this.sharedMediaData) {
                    if (deleteMessage.deleteMessage(r0.intValue(), i5)) {
                        obj = 1;
                    }
                }
            }
            if (obj != null) {
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
                    i4 = (this.sharedMediaData[this.selectedMode].messages.isEmpty() || this.searching) ? 8 : 0;
                    actionBarMenuItem.setVisibility(i4);
                }
            }
        } else if (i == NotificationCenter.didReceivedNewMessages) {
            if (((Long) objArr[0]).longValue() == this.dialog_id) {
                arrayList = (ArrayList) objArr[1];
                z = ((int) this.dialog_id) == 0;
                Object obj2 = null;
                for (i2 = 0; i2 < arrayList.size(); i2++) {
                    MessageObject messageObject = (MessageObject) arrayList.get(i2);
                    if (!(messageObject.messageOwner.media == null || messageObject.isSecretPhoto())) {
                        i3 = SharedMediaQuery.getMediaType(messageObject.messageOwner);
                        if (i3 == -1) {
                            return;
                        }
                        if (this.sharedMediaData[i3].addMessage(messageObject, true, z)) {
                            obj2 = 1;
                        }
                    }
                }
                if (obj2 != null) {
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
                        i4 = (this.sharedMediaData[this.selectedMode].messages.isEmpty() || this.searching) ? 8 : 0;
                        actionBarMenuItem.setVisibility(i4);
                    }
                }
            }
        } else if (i == NotificationCenter.messageReceivedByServer) {
            r0 = (Integer) objArr[0];
            Integer num = (Integer) objArr[1];
            for (SharedMediaData replaceMid : this.sharedMediaData) {
                replaceMid.replaceMid(r0.intValue(), num.intValue());
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        AnonymousClass11 anonymousClass11 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
                int childCount = MediaActivity.this.listView.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = MediaActivity.this.listView.getChildAt(i2);
                    if (childAt instanceof SharedPhotoVideoCell) {
                        ((SharedPhotoVideoCell) childAt).updateCheckboxColor();
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
        themeDescriptionArr[46] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOX, new Class[]{SharedPhotoVideoCell.class}, null, null, anonymousClass11, Theme.key_checkbox);
        themeDescriptionArr[47] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{SharedPhotoVideoCell.class}, null, null, anonymousClass11, Theme.key_checkboxCheck);
        themeDescriptionArr[48] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_inappPlayerBackground);
        themeDescriptionArr[49] = new ThemeDescription(this.fragmentContextView, 0, new Class[]{FragmentContextView.class}, new String[]{"playButton"}, null, null, null, Theme.key_inappPlayerPlayPause);
        themeDescriptionArr[50] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{FragmentContextView.class}, new String[]{"titleTextView"}, null, null, null, Theme.key_inappPlayerTitle);
        themeDescriptionArr[51] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{FragmentContextView.class}, new String[]{"frameLayout"}, null, null, null, Theme.key_inappPlayerPerformer);
        themeDescriptionArr[52] = new ThemeDescription(this.fragmentContextView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FragmentContextView.class}, new String[]{"closeButton"}, null, null, null, Theme.key_inappPlayerClose);
        return themeDescriptionArr;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
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

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.mediaDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDeleted);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceivedNewMessages);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messageReceivedByServer);
        this.dialog_id = getArguments().getLong("dialog_id", 0);
        for (int i = 0; i < this.sharedMediaData.length; i++) {
            this.sharedMediaData[i] = new SharedMediaData();
            this.sharedMediaData[i].max_id[0] = ((int) this.dialog_id) == 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            if (!(this.mergeDialogId == 0 || this.info == null)) {
                this.sharedMediaData[i].max_id[1] = this.info.migrated_from_max_id;
                this.sharedMediaData[i].endReached[1] = false;
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

    public void setChatInfo(ChatFull chatFull) {
        this.info = chatFull;
        if (this.info != null && this.info.migrated_from_chat_id != 0) {
            this.mergeDialogId = (long) (-this.info.migrated_from_chat_id);
        }
    }

    public void setMergeDialogId(long j) {
        this.mergeDialogId = j;
    }
}
