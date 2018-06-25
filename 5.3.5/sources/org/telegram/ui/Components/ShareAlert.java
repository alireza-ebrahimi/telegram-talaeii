package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.util.AppUtilities;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.support.widget.GridLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_channels_exportMessageLink;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_exportedMessageLink;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.ShareDialogCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.DialogsActivity;

public class ShareAlert extends BottomSheet implements NotificationCenterDelegate {
    private AnimatorSet animatorSet;
    private EditTextBoldCursor commentTextView;
    private boolean copyLinkOnEnd;
    private LinearLayout doneButton;
    private TextView doneButtonBadgeTextView;
    private TextView doneButtonTextView;
    private TLRPC$TL_exportedMessageLink exportedMessageLink;
    private boolean favsFirst;
    private FrameLayout frameLayout;
    private FrameLayout frameLayout2;
    private RecyclerListView gridView;
    private boolean isPublicChannel;
    private GridLayoutManager layoutManager;
    private String linkToCopy;
    private ShareDialogsAdapter listAdapter;
    private boolean loadingLink;
    private EditTextBoldCursor nameTextView;
    private Switch quoteSwitch;
    TextView quoteTextView;
    private int scrollOffsetY;
    private ShareSearchAdapter searchAdapter;
    private EmptyTextProgressView searchEmptyView;
    private HashMap<Long, TLRPC$TL_dialog> selectedDialogs = new HashMap();
    private MessageObject sendingMessageObject;
    private ArrayList<MessageObject> sendingMessageObjects;
    private String sendingText;
    private View shadow;
    private View shadow2;
    private Drawable shadowDrawable;
    private int topBeforeSwitch;

    /* renamed from: org.telegram.ui.Components.ShareAlert$3 */
    class C27373 implements OnTouchListener {
        C27373() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.Components.ShareAlert$4 */
    class C27384 implements OnTouchListener {
        C27384() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.Components.ShareAlert$5 */
    class C27395 implements OnClickListener {
        C27395() {
        }

        public void onClick(View v) {
            if (!ShareAlert.this.selectedDialogs.isEmpty() || (!ShareAlert.this.isPublicChannel && ShareAlert.this.linkToCopy == null)) {
                if (ShareAlert.this.sendingMessageObject != null) {
                    ArrayList<MessageObject> arrayList = new ArrayList();
                    arrayList.add(ShareAlert.this.sendingMessageObject);
                    for (Entry<Long, TLRPC$TL_dialog> entry : ShareAlert.this.selectedDialogs.entrySet()) {
                        if (ShareAlert.this.quoteSwitch.isChecked()) {
                            SendMessagesHelper.getInstance().sendMessage(arrayList, ((Long) entry.getKey()).longValue());
                        } else {
                            SendMessagesHelper.getInstance().processForwardFromMyName((MessageObject) arrayList.get(0), ((Long) entry.getKey()).longValue(), true);
                        }
                    }
                } else if (ShareAlert.this.sendingText != null) {
                    for (Entry<Long, TLRPC$TL_dialog> entry2 : ShareAlert.this.selectedDialogs.entrySet()) {
                        SendMessagesHelper.getInstance().sendMessage(ShareAlert.this.sendingText, ((Long) entry2.getKey()).longValue(), null, null, true, null, null, null);
                    }
                }
                ShareAlert.this.dismiss();
                return;
            }
            if (ShareAlert.this.linkToCopy == null && ShareAlert.this.loadingLink) {
                ShareAlert.this.copyLinkOnEnd = true;
                Toast.makeText(ShareAlert.this.getContext(), LocaleController.getString("Loading", R.string.Loading), 0).show();
            } else {
                ShareAlert.this.copyLink(ShareAlert.this.getContext());
            }
            ShareAlert.this.dismiss();
        }
    }

    /* renamed from: org.telegram.ui.Components.ShareAlert$6 */
    class C27406 implements OnCheckedChangeListener {
        C27406() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit().putBoolean("directShareQuote", isChecked).apply();
            ShareAlert.this.setCheckColor();
        }
    }

    /* renamed from: org.telegram.ui.Components.ShareAlert$8 */
    class C27428 implements TextWatcher {
        C27428() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            String text = ShareAlert.this.nameTextView.getText().toString();
            if (text.length() != 0) {
                if (ShareAlert.this.gridView.getAdapter() != ShareAlert.this.searchAdapter) {
                    ShareAlert.this.topBeforeSwitch = ShareAlert.this.getCurrentTop();
                    ShareAlert.this.gridView.setAdapter(ShareAlert.this.searchAdapter);
                    ShareAlert.this.searchAdapter.notifyDataSetChanged();
                }
                if (ShareAlert.this.searchEmptyView != null) {
                    ShareAlert.this.searchEmptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
                }
            } else if (ShareAlert.this.gridView.getAdapter() != ShareAlert.this.listAdapter) {
                int top = ShareAlert.this.getCurrentTop();
                ShareAlert.this.searchEmptyView.setText(LocaleController.getString("NoChats", R.string.NoChats));
                ShareAlert.this.gridView.setAdapter(ShareAlert.this.listAdapter);
                ShareAlert.this.listAdapter.notifyDataSetChanged();
                if (top > 0) {
                    ShareAlert.this.layoutManager.scrollToPositionWithOffset(0, -top);
                }
            }
            if (ShareAlert.this.searchAdapter != null) {
                ShareAlert.this.searchAdapter.searchDialogs(text);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ShareAlert$9 */
    class C27439 extends ItemDecoration {
        C27439() {
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            int i = 0;
            Holder holder = (Holder) parent.getChildViewHolder(view);
            if (holder != null) {
                int pos = holder.getAdapterPosition();
                outRect.left = pos % 4 == 0 ? 0 : AndroidUtilities.dp(4.0f);
                if (pos % 4 != 3) {
                    i = AndroidUtilities.dp(4.0f);
                }
                outRect.right = i;
                return;
            }
            outRect.left = AndroidUtilities.dp(4.0f);
            outRect.right = AndroidUtilities.dp(4.0f);
        }
    }

    private class ShareDialogsAdapter extends SelectionAdapter {
        private Context context;
        private int currentCount;
        private ArrayList<TLRPC$TL_dialog> dialogs = new ArrayList();

        public ShareDialogsAdapter(Context context) {
            this.context = context;
            fetchDialogs();
        }

        public void fetchDialogs() {
            int a;
            TLRPC$TL_dialog dialog;
            int lower_id;
            TLRPC$Chat chat;
            this.dialogs.clear();
            if (ShareAlert.this.favsFirst) {
                for (a = 0; a < MessagesController.getInstance().dialogsFavs.size(); a++) {
                    dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogsFavs.get(a);
                    lower_id = (int) dialog.id;
                    int high_id = (int) (dialog.id >> 32);
                    if (!(lower_id == 0 || high_id == 1)) {
                        if (lower_id > 0) {
                            this.dialogs.add(dialog);
                        } else {
                            chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
                            if (!(chat == null || ChatObject.isNotInChat(chat) || (ChatObject.isChannel(chat) && !chat.creator && !chat.megagroup))) {
                                this.dialogs.add(dialog);
                            }
                        }
                    }
                }
            }
            for (a = 0; a < MessagesController.getInstance().dialogsServerOnly.size(); a++) {
                dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogsServerOnly.get(a);
                if (!ShareAlert.this.favsFirst || !Favourite.isFavourite(Long.valueOf(dialog.id))) {
                    lower_id = (int) dialog.id;
                    high_id = (int) (dialog.id >> 32);
                    if (!(lower_id == 0 || high_id == 1)) {
                        if (lower_id > 0) {
                            this.dialogs.add(dialog);
                        } else {
                            chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
                            if (!(chat == null || ChatObject.isNotInChat(chat) || (ChatObject.isChannel(chat) && !chat.creator && !chat.megagroup))) {
                                this.dialogs.add(dialog);
                            }
                        }
                    }
                }
            }
            notifyDataSetChanged();
        }

        public int getItemCount() {
            return this.dialogs.size();
        }

        public TLRPC$TL_dialog getItem(int i) {
            if (i < 0 || i >= this.dialogs.size()) {
                return null;
            }
            return (TLRPC$TL_dialog) this.dialogs.get(i);
        }

        public boolean isEnabled(ViewHolder holder) {
            return true;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = new ShareDialogCell(this.context);
            view.setLayoutParams(new LayoutParams(-1, AndroidUtilities.dp(100.0f)));
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            ShareDialogCell cell = holder.itemView;
            TLRPC$TL_dialog dialog = getItem(position);
            cell.setDialog((int) dialog.id, ShareAlert.this.selectedDialogs.containsKey(Long.valueOf(dialog.id)), null);
        }

        public int getItemViewType(int i) {
            return 0;
        }
    }

    public class ShareSearchAdapter extends SelectionAdapter {
        private Context context;
        private int lastReqId;
        private int lastSearchId = 0;
        private String lastSearchText;
        private int reqId = 0;
        private ArrayList<DialogSearchResult> searchResult = new ArrayList();
        private Timer searchTimer;

        private class DialogSearchResult {
            public int date;
            public TLRPC$TL_dialog dialog;
            public CharSequence name;
            public TLObject object;

            private DialogSearchResult() {
                this.dialog = new TLRPC$TL_dialog();
            }
        }

        public ShareSearchAdapter(Context context) {
            this.context = context;
        }

        private void searchDialogsInternal(final String query, final int searchId) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.ui.Components.ShareAlert$ShareSearchAdapter$1$1 */
                class C27441 implements Comparator<DialogSearchResult> {
                    C27441() {
                    }

                    public int compare(DialogSearchResult lhs, DialogSearchResult rhs) {
                        if (lhs.date < rhs.date) {
                            return 1;
                        }
                        if (lhs.date > rhs.date) {
                            return -1;
                        }
                        return 0;
                    }
                }

                public void run() {
                    try {
                        String search1 = query.trim().toLowerCase();
                        if (search1.length() == 0) {
                            ShareSearchAdapter.this.lastSearchId = -1;
                            ShareSearchAdapter.this.updateSearchResults(new ArrayList(), ShareSearchAdapter.this.lastSearchId);
                            return;
                        }
                        DialogSearchResult dialogSearchResult;
                        String name;
                        String tName;
                        String username;
                        int usernamePos;
                        int found;
                        int length;
                        int i;
                        String q;
                        NativeByteBuffer data;
                        TLObject user;
                        String search2 = LocaleController.getInstance().getTranslitString(search1);
                        if (search1.equals(search2) || search2.length() == 0) {
                            search2 = null;
                        }
                        String[] search = new String[((search2 != null ? 1 : 0) + 1)];
                        search[0] = search1;
                        if (search2 != null) {
                            search[1] = search2;
                        }
                        ArrayList<Integer> usersToLoad = new ArrayList();
                        ArrayList<Integer> chatsToLoad = new ArrayList();
                        int resultCount = 0;
                        HashMap<Long, DialogSearchResult> dialogsResult = new HashMap();
                        SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized("SELECT did, date FROM dialogs ORDER BY date DESC LIMIT 400", new Object[0]);
                        while (cursor.next()) {
                            long id = cursor.longValue(0);
                            dialogSearchResult = new DialogSearchResult();
                            dialogSearchResult.date = cursor.intValue(1);
                            dialogsResult.put(Long.valueOf(id), dialogSearchResult);
                            int lower_id = (int) id;
                            int high_id = (int) (id >> 32);
                            if (!(lower_id == 0 || high_id == 1)) {
                                if (lower_id > 0) {
                                    if (!usersToLoad.contains(Integer.valueOf(lower_id))) {
                                        usersToLoad.add(Integer.valueOf(lower_id));
                                    }
                                } else if (!chatsToLoad.contains(Integer.valueOf(-lower_id))) {
                                    chatsToLoad.add(Integer.valueOf(-lower_id));
                                }
                            }
                        }
                        cursor.dispose();
                        if (!usersToLoad.isEmpty()) {
                            cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT data, status, name FROM users WHERE uid IN(%s)", new Object[]{TextUtils.join(",", usersToLoad)}), new Object[0]);
                            while (cursor.next()) {
                                name = cursor.stringValue(2);
                                tName = LocaleController.getInstance().getTranslitString(name);
                                if (name.equals(tName)) {
                                    tName = null;
                                }
                                username = null;
                                usernamePos = name.lastIndexOf(";;;");
                                if (usernamePos != -1) {
                                    username = name.substring(usernamePos + 3);
                                }
                                found = 0;
                                length = search.length;
                                i = 0;
                                while (i < length) {
                                    q = search[i];
                                    if (name.startsWith(q) || name.contains(" " + q) || (tName != null && (tName.startsWith(q) || tName.contains(" " + q)))) {
                                        found = 1;
                                    } else if (username != null && username.startsWith(q)) {
                                        found = 2;
                                    }
                                    if (found != 0) {
                                        data = cursor.byteBufferValue(0);
                                        if (data != null) {
                                            user = User.TLdeserialize(data, data.readInt32(false), false);
                                            data.reuse();
                                            dialogSearchResult = (DialogSearchResult) dialogsResult.get(Long.valueOf((long) user.id));
                                            if (user.status != null) {
                                                user.status.expires = cursor.intValue(1);
                                            }
                                            if (found == 1) {
                                                dialogSearchResult.name = AndroidUtilities.generateSearchName(user.first_name, user.last_name, q);
                                            } else {
                                                dialogSearchResult.name = AndroidUtilities.generateSearchName("@" + user.username, null, "@" + q);
                                            }
                                            dialogSearchResult.object = user;
                                            dialogSearchResult.dialog.id = (long) user.id;
                                            resultCount++;
                                        }
                                    } else {
                                        i++;
                                    }
                                }
                            }
                            cursor.dispose();
                        }
                        if (!chatsToLoad.isEmpty()) {
                            cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT data, name FROM chats WHERE uid IN(%s)", new Object[]{TextUtils.join(",", chatsToLoad)}), new Object[0]);
                            while (cursor.next()) {
                                name = cursor.stringValue(1);
                                tName = LocaleController.getInstance().getTranslitString(name);
                                if (name.equals(tName)) {
                                    tName = null;
                                }
                                int a = 0;
                                while (a < search.length) {
                                    q = search[a];
                                    if (name.startsWith(q) || name.contains(" " + q) || (tName != null && (tName.startsWith(q) || tName.contains(" " + q)))) {
                                        data = cursor.byteBufferValue(0);
                                        if (data != null) {
                                            TLRPC$Chat chat = TLRPC$Chat.TLdeserialize(data, data.readInt32(false), false);
                                            data.reuse();
                                            if (!(chat == null || ChatObject.isNotInChat(chat))) {
                                                if (!ChatObject.isChannel(chat) || chat.creator || ((chat.admin_rights != null && chat.admin_rights.post_messages) || chat.megagroup)) {
                                                    dialogSearchResult = (DialogSearchResult) dialogsResult.get(Long.valueOf(-((long) chat.id)));
                                                    dialogSearchResult.name = AndroidUtilities.generateSearchName(chat.title, null, q);
                                                    dialogSearchResult.object = chat;
                                                    dialogSearchResult.dialog.id = (long) (-chat.id);
                                                    resultCount++;
                                                }
                                            }
                                        }
                                    } else {
                                        a++;
                                    }
                                }
                            }
                            cursor.dispose();
                        }
                        ArrayList<DialogSearchResult> arrayList = new ArrayList(resultCount);
                        for (DialogSearchResult dialogSearchResult2 : dialogsResult.values()) {
                            if (!(dialogSearchResult2.object == null || dialogSearchResult2.name == null)) {
                                arrayList.add(dialogSearchResult2);
                            }
                        }
                        cursor = MessagesStorage.getInstance().getDatabase().queryFinalized("SELECT u.data, u.status, u.name, u.uid FROM users as u INNER JOIN contacts as c ON u.uid = c.uid", new Object[0]);
                        while (cursor.next()) {
                            if (!dialogsResult.containsKey(Long.valueOf((long) cursor.intValue(3)))) {
                                name = cursor.stringValue(2);
                                tName = LocaleController.getInstance().getTranslitString(name);
                                if (name.equals(tName)) {
                                    tName = null;
                                }
                                username = null;
                                usernamePos = name.lastIndexOf(";;;");
                                if (usernamePos != -1) {
                                    username = name.substring(usernamePos + 3);
                                }
                                found = 0;
                                length = search.length;
                                i = 0;
                                while (i < length) {
                                    q = search[i];
                                    if (name.startsWith(q) || name.contains(" " + q) || (tName != null && (tName.startsWith(q) || tName.contains(" " + q)))) {
                                        found = 1;
                                    } else if (username != null && username.startsWith(q)) {
                                        found = 2;
                                    }
                                    if (found != 0) {
                                        data = cursor.byteBufferValue(0);
                                        if (data != null) {
                                            user = User.TLdeserialize(data, data.readInt32(false), false);
                                            data.reuse();
                                            dialogSearchResult2 = new DialogSearchResult();
                                            if (user.status != null) {
                                                user.status.expires = cursor.intValue(1);
                                            }
                                            dialogSearchResult2.dialog.id = (long) user.id;
                                            dialogSearchResult2.object = user;
                                            if (found == 1) {
                                                dialogSearchResult2.name = AndroidUtilities.generateSearchName(user.first_name, user.last_name, q);
                                            } else {
                                                dialogSearchResult2.name = AndroidUtilities.generateSearchName("@" + user.username, null, "@" + q);
                                            }
                                            arrayList.add(dialogSearchResult2);
                                        }
                                    } else {
                                        i++;
                                    }
                                }
                            }
                        }
                        cursor.dispose();
                        Collections.sort(arrayList, new C27441());
                        ShareSearchAdapter.this.updateSearchResults(arrayList, searchId);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }

        private void updateSearchResults(final ArrayList<DialogSearchResult> result, final int searchId) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (searchId == ShareSearchAdapter.this.lastSearchId) {
                        boolean becomeEmpty;
                        boolean isEmpty;
                        for (int a = 0; a < result.size(); a++) {
                            DialogSearchResult obj = (DialogSearchResult) result.get(a);
                            if (obj.object instanceof User) {
                                MessagesController.getInstance().putUser(obj.object, true);
                            } else if (obj.object instanceof TLRPC$Chat) {
                                MessagesController.getInstance().putChat(obj.object, true);
                            }
                        }
                        if (ShareSearchAdapter.this.searchResult.isEmpty() || !result.isEmpty()) {
                            becomeEmpty = false;
                        } else {
                            becomeEmpty = true;
                        }
                        if (ShareSearchAdapter.this.searchResult.isEmpty() && result.isEmpty()) {
                            isEmpty = true;
                        } else {
                            isEmpty = false;
                        }
                        if (becomeEmpty) {
                            ShareAlert.this.topBeforeSwitch = ShareAlert.this.getCurrentTop();
                        }
                        ShareSearchAdapter.this.searchResult = result;
                        ShareSearchAdapter.this.notifyDataSetChanged();
                        if (!isEmpty && !becomeEmpty && ShareAlert.this.topBeforeSwitch > 0) {
                            ShareAlert.this.layoutManager.scrollToPositionWithOffset(0, -ShareAlert.this.topBeforeSwitch);
                            ShareAlert.this.topBeforeSwitch = -1000;
                        }
                    }
                }
            });
        }

        public void searchDialogs(final String query) {
            if (query == null || this.lastSearchText == null || !query.equals(this.lastSearchText)) {
                this.lastSearchText = query;
                try {
                    if (this.searchTimer != null) {
                        this.searchTimer.cancel();
                        this.searchTimer = null;
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
                if (query == null || query.length() == 0) {
                    this.searchResult.clear();
                    ShareAlert.this.topBeforeSwitch = ShareAlert.this.getCurrentTop();
                    notifyDataSetChanged();
                    return;
                }
                final int searchId = this.lastSearchId + 1;
                this.lastSearchId = searchId;
                this.searchTimer = new Timer();
                this.searchTimer.schedule(new TimerTask() {
                    public void run() {
                        try {
                            cancel();
                            ShareSearchAdapter.this.searchTimer.cancel();
                            ShareSearchAdapter.this.searchTimer = null;
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                        ShareSearchAdapter.this.searchDialogsInternal(query, searchId);
                    }
                }, 200, 300);
            }
        }

        public int getItemCount() {
            return this.searchResult.size();
        }

        public TLRPC$TL_dialog getItem(int i) {
            if (i < 0 || i >= this.searchResult.size()) {
                return null;
            }
            return ((DialogSearchResult) this.searchResult.get(i)).dialog;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public boolean isEnabled(ViewHolder holder) {
            return true;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = new ShareDialogCell(this.context);
            view.setLayoutParams(new LayoutParams(-1, AndroidUtilities.dp(100.0f)));
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            DialogSearchResult result = (DialogSearchResult) this.searchResult.get(position);
            holder.itemView.setDialog((int) result.dialog.id, ShareAlert.this.selectedDialogs.containsKey(Long.valueOf(result.dialog.id)), result.name);
        }

        public int getItemViewType(int i) {
            return 0;
        }
    }

    public static ShareAlert createShareAlert(Context context, MessageObject messageObject, String text, boolean publicChannel, String copyLink, boolean fullScreen) {
        ArrayList<MessageObject> arrayList;
        if (messageObject != null) {
            arrayList = new ArrayList();
            arrayList.add(messageObject);
        } else {
            arrayList = null;
        }
        return new ShareAlert(context, arrayList, text, publicChannel, copyLink, fullScreen);
    }

    public ShareAlert(final Context context, MessageObject messageObject, String text, boolean publicChannel, String copyLink, boolean fullScreen, boolean ali) {
        super(context, true);
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.favsFirst = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("directShareFavsFirst", false);
        this.linkToCopy = copyLink;
        this.sendingMessageObject = messageObject;
        this.searchAdapter = new ShareSearchAdapter(context);
        this.isPublicChannel = publicChannel;
        this.sendingText = text;
        if (publicChannel) {
            this.loadingLink = true;
            TLRPC$TL_channels_exportMessageLink req = new TLRPC$TL_channels_exportMessageLink();
            req.id = messageObject.getId();
            req.channel = MessagesController.getInputChannel(messageObject.messageOwner.to_id.channel_id);
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (response != null) {
                                ShareAlert.this.exportedMessageLink = (TLRPC$TL_exportedMessageLink) response;
                                if (ShareAlert.this.copyLinkOnEnd) {
                                    ShareAlert.this.copyLink(context);
                                }
                            }
                            ShareAlert.this.loadingLink = false;
                        }
                    });
                }
            });
        }
        this.containerView = new FrameLayout(context) {
            private boolean ignoreLayout = false;

            public boolean onInterceptTouchEvent(MotionEvent ev) {
                if (ev.getAction() != 0 || ShareAlert.this.scrollOffsetY == 0 || ev.getY() >= ((float) ShareAlert.this.scrollOffsetY)) {
                    return super.onInterceptTouchEvent(ev);
                }
                ShareAlert.this.dismiss();
                return true;
            }

            public boolean onTouchEvent(MotionEvent e) {
                return !ShareAlert.this.isDismissed() && super.onTouchEvent(e);
            }

            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int height = MeasureSpec.getSize(heightMeasureSpec);
                if (VERSION.SDK_INT >= 21) {
                    height -= AndroidUtilities.statusBarHeight;
                }
                int contentSize = (AndroidUtilities.dp(48.0f) + (Math.max(3, (int) Math.ceil((double) (((float) Math.max(ShareAlert.this.searchAdapter.getItemCount(), ShareAlert.this.listAdapter.getItemCount())) / 4.0f))) * AndroidUtilities.dp(100.0f))) + ShareAlert.backgroundPaddingTop;
                int padding = contentSize < height ? 0 : (height - ((height / 5) * 3)) + AndroidUtilities.dp(8.0f);
                if (ShareAlert.this.gridView.getPaddingTop() != padding) {
                    this.ignoreLayout = true;
                    ShareAlert.this.gridView.setPadding(0, padding, 0, AndroidUtilities.dp(8.0f));
                    this.ignoreLayout = false;
                }
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Math.min(contentSize, height), 1073741824));
            }

            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                super.onLayout(changed, left, top, right, bottom);
                ShareAlert.this.updateLayout();
            }

            public void requestLayout() {
                if (!this.ignoreLayout) {
                    super.requestLayout();
                }
            }

            protected void onDraw(Canvas canvas) {
                ShareAlert.this.shadowDrawable.setBounds(0, ShareAlert.this.scrollOffsetY - ShareAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                ShareAlert.this.shadowDrawable.draw(canvas);
            }
        };
        this.containerView.setWillNotDraw(false);
        this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        this.frameLayout = new FrameLayout(context);
        this.frameLayout.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.frameLayout.setOnTouchListener(new C27373());
        this.frameLayout2 = new FrameLayout(context);
        this.frameLayout2.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.frameLayout2.setTranslationY((float) AndroidUtilities.dp(53.0f));
        this.containerView.addView(this.frameLayout2, LayoutHelper.createFrame(-1, 48, 83));
        this.frameLayout2.setOnTouchListener(new C27384());
        this.doneButton = new LinearLayout(context);
        this.doneButton.setOrientation(0);
        this.doneButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 0));
        this.doneButton.setPadding(AndroidUtilities.dp(21.0f), 0, AndroidUtilities.dp(21.0f), 0);
        this.frameLayout.addView(this.doneButton, LayoutHelper.createFrame(-2, -1, 53));
        this.doneButton.setOnClickListener(new C27395());
        this.doneButtonBadgeTextView = new TextView(context);
        this.doneButtonBadgeTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.doneButtonBadgeTextView.setTextSize(1, 13.0f);
        this.doneButtonBadgeTextView.setTextColor(Theme.getColor(Theme.key_dialogBadgeText));
        this.doneButtonBadgeTextView.setGravity(17);
        this.doneButtonBadgeTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(12.5f), Theme.getColor(Theme.key_dialogBadgeBackground)));
        this.doneButtonBadgeTextView.setMinWidth(AndroidUtilities.dp(23.0f));
        this.doneButtonBadgeTextView.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(8.0f), AndroidUtilities.dp(1.0f));
        this.doneButton.addView(this.doneButtonBadgeTextView, LayoutHelper.createLinear(-2, 23, 16, 0, 0, 10, 0));
        this.doneButtonTextView = new TextView(context);
        this.doneButtonTextView.setTextSize(1, 14.0f);
        this.doneButtonTextView.setGravity(17);
        this.doneButtonTextView.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        this.doneButtonTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.doneButton.addView(this.doneButtonTextView, LayoutHelper.createLinear(-2, -2, 16));
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        this.quoteSwitch = new Switch(context);
        this.quoteSwitch.setTag("chat");
        this.quoteSwitch.setDuplicateParentStateEnabled(false);
        this.quoteSwitch.setFocusable(false);
        this.quoteSwitch.setFocusableInTouchMode(false);
        this.quoteSwitch.setClickable(true);
        setCheck(preferences.getBoolean("directShareQuote", true));
        setCheckColor();
        this.frameLayout.addView(this.quoteSwitch, LayoutHelper.createFrame(-2, -2.0f, 19, 0.0f, 2.0f, 0.0f, 0.0f));
        this.quoteSwitch.setOnCheckedChangeListener(new C27406());
        final boolean z = fullScreen;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (z) {
                    Log.d("alireza", "alireza tttttttttt 2" + z);
                    ShareAlert.this.setCheck(false);
                    ShareAlert.this.quoteSwitch.setVisibility(8);
                    ShareAlert.this.quoteTextView.setVisibility(8);
                }
            }
        }, 200);
        this.quoteTextView = new TextView(context);
        this.quoteTextView.setTextSize(1, 9.0f);
        this.quoteTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        this.quoteTextView.setGravity(17);
        this.quoteTextView.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        this.quoteTextView.setText(LocaleController.getString("Quote", R.string.Quote).toUpperCase());
        this.quoteTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.frameLayout.addView(this.quoteTextView, LayoutHelper.createFrame(-2, -2.0f, 51, 12.0f, 2.0f, 0.0f, 0.0f));
        this.nameTextView = new EditTextBoldCursor(context);
        this.nameTextView.setHint(LocaleController.getString("ShareSendTo", R.string.ShareSendTo));
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setGravity(19);
        this.nameTextView.setTextSize(1, 16.0f);
        this.nameTextView.setBackgroundDrawable(null);
        this.nameTextView.setHintTextColor(Theme.getColor(Theme.key_dialogTextHint));
        this.nameTextView.setImeOptions(268435456);
        this.nameTextView.setInputType(16385);
        AndroidUtilities.clearCursorDrawable(this.nameTextView);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.frameLayout.addView(this.nameTextView, LayoutHelper.createFrame(-1, -1.0f, 51, 48.0f, 2.0f, 96.0f, 0.0f));
        this.nameTextView.addTextChangedListener(new C27428());
        this.gridView = new RecyclerListView(context);
        this.gridView.setTag(Integer.valueOf(13));
        this.gridView.setPadding(0, 0, 0, AndroidUtilities.dp(8.0f));
        this.gridView.setClipToPadding(false);
        RecyclerListView recyclerListView = this.gridView;
        LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        this.layoutManager = gridLayoutManager;
        recyclerListView.setLayoutManager(gridLayoutManager);
        this.gridView.setHorizontalScrollBarEnabled(false);
        this.gridView.setVerticalScrollBarEnabled(false);
        this.gridView.addItemDecoration(new C27439());
        this.containerView.addView(this.gridView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 48.0f, 0.0f, 0.0f));
        recyclerListView = this.gridView;
        Adapter shareDialogsAdapter = new ShareDialogsAdapter(context);
        this.listAdapter = shareDialogsAdapter;
        recyclerListView.setAdapter(shareDialogsAdapter);
        this.gridView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                if (position >= 0) {
                    TLRPC$TL_dialog dialog;
                    if (ShareAlert.this.gridView.getAdapter() == ShareAlert.this.listAdapter) {
                        dialog = ShareAlert.this.listAdapter.getItem(position);
                    } else {
                        dialog = ShareAlert.this.searchAdapter.getItem(position);
                    }
                    if (dialog != null) {
                        ShareDialogCell cell = (ShareDialogCell) view;
                        if (ShareAlert.this.selectedDialogs.containsKey(Long.valueOf(dialog.id))) {
                            ShareAlert.this.selectedDialogs.remove(Long.valueOf(dialog.id));
                            cell.setChecked(false, true);
                        } else {
                            ShareAlert.this.selectedDialogs.put(Long.valueOf(dialog.id), dialog);
                            cell.setChecked(true, true);
                        }
                        ShareAlert.this.updateSelectedCount();
                    }
                }
            }
        });
        this.gridView.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                ShareAlert.this.updateLayout();
            }
        });
        this.searchEmptyView = new EmptyTextProgressView(context);
        this.searchEmptyView.setShowAtCenter(true);
        this.searchEmptyView.showTextView();
        this.searchEmptyView.setText(LocaleController.getString("NoChats", R.string.NoChats));
        this.gridView.setEmptyView(this.searchEmptyView);
        this.containerView.addView(this.searchEmptyView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 48.0f, 0.0f, 0.0f));
        this.containerView.addView(this.frameLayout, LayoutHelper.createFrame(-1, 48, 51));
        this.shadow = new View(context);
        this.shadow.setBackgroundResource(R.drawable.header_shadow);
        this.containerView.addView(this.shadow, LayoutHelper.createFrame(-1, 3.0f, 51, 0.0f, 48.0f, 0.0f, 0.0f));
        updateSelectedCount();
        if (!DialogsActivity.dialogsLoaded) {
            MessagesController.getInstance().loadDialogs(0, 100, true);
            ContactsController.getInstance().checkInviteText();
            DialogsActivity.dialogsLoaded = true;
        }
        if (this.listAdapter.dialogs.isEmpty()) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.dialogsNeedReload);
        }
    }

    public ShareAlert(Context context, ArrayList<MessageObject> messages, String text, boolean publicChannel, String copyLink, boolean fullScreen) {
        super(context, true);
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.favsFirst = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("directShareFavsFirst", false);
        this.linkToCopy = copyLink;
        this.sendingMessageObjects = messages;
        this.searchAdapter = new ShareSearchAdapter(context);
        this.isPublicChannel = publicChannel;
        this.sendingText = text;
        if (publicChannel) {
            this.loadingLink = true;
            TLRPC$TL_channels_exportMessageLink req = new TLRPC$TL_channels_exportMessageLink();
            req.id = ((MessageObject) messages.get(0)).getId();
            req.channel = MessagesController.getInputChannel(((MessageObject) messages.get(0)).messageOwner.to_id.channel_id);
            final Context context2 = context;
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (response != null) {
                                ShareAlert.this.exportedMessageLink = (TLRPC$TL_exportedMessageLink) response;
                                if (ShareAlert.this.copyLinkOnEnd) {
                                    ShareAlert.this.copyLink(context2);
                                }
                            }
                            ShareAlert.this.loadingLink = false;
                        }
                    });
                }
            });
        }
        this.containerView = new FrameLayout(context) {
            private boolean ignoreLayout = false;

            public boolean onInterceptTouchEvent(MotionEvent ev) {
                if (ev.getAction() != 0 || ShareAlert.this.scrollOffsetY == 0 || ev.getY() >= ((float) ShareAlert.this.scrollOffsetY)) {
                    return super.onInterceptTouchEvent(ev);
                }
                ShareAlert.this.dismiss();
                return true;
            }

            public boolean onTouchEvent(MotionEvent e) {
                return !ShareAlert.this.isDismissed() && super.onTouchEvent(e);
            }

            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                float f = 8.0f;
                int height = MeasureSpec.getSize(heightMeasureSpec);
                if (VERSION.SDK_INT >= 21) {
                    height -= AndroidUtilities.statusBarHeight;
                }
                int contentSize = (AndroidUtilities.dp(48.0f) + (Math.max(3, (int) Math.ceil((double) (((float) Math.max(ShareAlert.this.searchAdapter.getItemCount(), ShareAlert.this.listAdapter.getItemCount())) / 4.0f))) * AndroidUtilities.dp(100.0f))) + ShareAlert.backgroundPaddingTop;
                int padding = contentSize < height ? 0 : (height - ((height / 5) * 3)) + AndroidUtilities.dp(8.0f);
                if (ShareAlert.this.gridView.getPaddingTop() != padding) {
                    this.ignoreLayout = true;
                    RecyclerListView access$800 = ShareAlert.this.gridView;
                    if (ShareAlert.this.frameLayout2.getTag() != null) {
                        f = 56.0f;
                    }
                    access$800.setPadding(0, padding, 0, AndroidUtilities.dp(f));
                    this.ignoreLayout = false;
                }
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Math.min(contentSize, height), 1073741824));
            }

            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                super.onLayout(changed, left, top, right, bottom);
                ShareAlert.this.updateLayout();
            }

            public void requestLayout() {
                if (!this.ignoreLayout) {
                    super.requestLayout();
                }
            }

            protected void onDraw(Canvas canvas) {
                ShareAlert.this.shadowDrawable.setBounds(0, ShareAlert.this.scrollOffsetY - ShareAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                ShareAlert.this.shadowDrawable.draw(canvas);
            }
        };
        this.containerView.setWillNotDraw(false);
        this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        this.frameLayout = new FrameLayout(context);
        this.frameLayout.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.frameLayout.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.doneButton = new LinearLayout(context);
        this.doneButton.setOrientation(0);
        this.doneButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 0));
        this.doneButton.setPadding(AndroidUtilities.dp(21.0f), 0, AndroidUtilities.dp(21.0f), 0);
        this.frameLayout.addView(this.doneButton, LayoutHelper.createFrame(-2, -1, 53));
        this.doneButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!ShareAlert.this.selectedDialogs.isEmpty() || (!ShareAlert.this.isPublicChannel && ShareAlert.this.linkToCopy == null)) {
                    if (ShareAlert.this.sendingMessageObjects != null) {
                        for (Entry<Long, TLRPC$TL_dialog> entry : ShareAlert.this.selectedDialogs.entrySet()) {
                            if (ShareAlert.this.frameLayout2.getTag() != null && ShareAlert.this.commentTextView.length() > 0) {
                                SendMessagesHelper.getInstance().sendMessage(ShareAlert.this.commentTextView.getText().toString(), ((Long) entry.getKey()).longValue(), null, null, true, null, null, null);
                            }
                            SendMessagesHelper.getInstance().sendMessage(ShareAlert.this.sendingMessageObjects, ((Long) entry.getKey()).longValue());
                        }
                    } else if (ShareAlert.this.sendingText != null) {
                        for (Entry<Long, TLRPC$TL_dialog> entry2 : ShareAlert.this.selectedDialogs.entrySet()) {
                            if (ShareAlert.this.frameLayout2.getTag() != null && ShareAlert.this.commentTextView.length() > 0) {
                                SendMessagesHelper.getInstance().sendMessage(ShareAlert.this.commentTextView.getText().toString(), ((Long) entry2.getKey()).longValue(), null, null, true, null, null, null);
                            }
                            SendMessagesHelper.getInstance().sendMessage(ShareAlert.this.sendingText, ((Long) entry2.getKey()).longValue(), null, null, true, null, null, null);
                        }
                    }
                    ShareAlert.this.dismiss();
                    return;
                }
                if (ShareAlert.this.linkToCopy == null && ShareAlert.this.loadingLink) {
                    ShareAlert.this.copyLinkOnEnd = true;
                    Toast.makeText(ShareAlert.this.getContext(), LocaleController.getString("Loading", R.string.Loading), 0).show();
                } else {
                    ShareAlert.this.copyLink(ShareAlert.this.getContext());
                }
                ShareAlert.this.dismiss();
            }
        });
        this.doneButtonBadgeTextView = new TextView(context);
        this.doneButtonBadgeTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.doneButtonBadgeTextView.setTextSize(1, 13.0f);
        this.doneButtonBadgeTextView.setTextColor(Theme.getColor(Theme.key_dialogBadgeText));
        this.doneButtonBadgeTextView.setGravity(17);
        this.doneButtonBadgeTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(12.5f), Theme.getColor(Theme.key_dialogBadgeBackground)));
        this.doneButtonBadgeTextView.setMinWidth(AndroidUtilities.dp(23.0f));
        this.doneButtonBadgeTextView.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(8.0f), AndroidUtilities.dp(1.0f));
        this.doneButton.addView(this.doneButtonBadgeTextView, LayoutHelper.createLinear(-2, 23, 16, 0, 0, 10, 0));
        this.doneButtonTextView = new TextView(context);
        this.doneButtonTextView.setTextSize(1, 14.0f);
        this.doneButtonTextView.setGravity(17);
        this.doneButtonTextView.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        this.doneButtonTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.doneButton.addView(this.doneButtonTextView, LayoutHelper.createLinear(-2, -2, 16));
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        this.quoteSwitch = new Switch(context);
        this.quoteSwitch.setTag("chat");
        this.quoteSwitch.setDuplicateParentStateEnabled(false);
        this.quoteSwitch.setFocusable(false);
        this.quoteSwitch.setFocusableInTouchMode(false);
        this.quoteSwitch.setClickable(true);
        setCheck(preferences.getBoolean("directShareQuote", true));
        setCheckColor();
        this.frameLayout.addView(this.quoteSwitch, LayoutHelper.createFrame(-2, -2.0f, 19, 0.0f, 2.0f, 0.0f, 0.0f));
        this.quoteSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit().putBoolean("directShareQuote", isChecked).apply();
                ShareAlert.this.setCheckColor();
            }
        });
        final boolean z = fullScreen;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (z) {
                    ShareAlert.this.setCheck(false);
                    ShareAlert.this.quoteSwitch.setVisibility(8);
                    ShareAlert.this.quoteTextView.setVisibility(8);
                }
            }
        }, 200);
        this.quoteTextView = new TextView(context);
        this.quoteTextView.setTextSize(1, 9.0f);
        this.quoteTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        this.quoteTextView.setGravity(17);
        this.quoteTextView.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        this.quoteTextView.setText(LocaleController.getString("Quote", R.string.Quote).toUpperCase());
        this.quoteTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.frameLayout.addView(this.quoteTextView, LayoutHelper.createFrame(-2, -2.0f, 51, 12.0f, 2.0f, 0.0f, 0.0f));
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.ic_ab_search);
        imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogIcon), Mode.MULTIPLY));
        imageView.setScaleType(ScaleType.CENTER);
        imageView.setPadding(0, AndroidUtilities.dp(2.0f), 0, 0);
        this.frameLayout.addView(imageView, LayoutHelper.createFrame(48, 48, 19));
        this.nameTextView = new EditTextBoldCursor(context);
        this.nameTextView.setHint(LocaleController.getString("ShareSendTo", R.string.ShareSendTo));
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setGravity(19);
        this.nameTextView.setTextSize(1, 16.0f);
        this.nameTextView.setBackgroundDrawable(null);
        this.nameTextView.setHintTextColor(Theme.getColor(Theme.key_dialogTextHint));
        this.nameTextView.setImeOptions(268435456);
        this.nameTextView.setInputType(16385);
        this.nameTextView.setCursorColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.nameTextView.setCursorSize(AndroidUtilities.dp(20.0f));
        this.nameTextView.setCursorWidth(1.5f);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.frameLayout.addView(this.nameTextView, LayoutHelper.createFrame(-1, -1.0f, 51, 48.0f, 2.0f, 96.0f, 0.0f));
        this.nameTextView.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                String text = ShareAlert.this.nameTextView.getText().toString();
                if (text.length() != 0) {
                    if (ShareAlert.this.gridView.getAdapter() != ShareAlert.this.searchAdapter) {
                        ShareAlert.this.topBeforeSwitch = ShareAlert.this.getCurrentTop();
                        ShareAlert.this.gridView.setAdapter(ShareAlert.this.searchAdapter);
                        ShareAlert.this.searchAdapter.notifyDataSetChanged();
                    }
                    if (ShareAlert.this.searchEmptyView != null) {
                        ShareAlert.this.searchEmptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
                    }
                } else if (ShareAlert.this.gridView.getAdapter() != ShareAlert.this.listAdapter) {
                    int top = ShareAlert.this.getCurrentTop();
                    ShareAlert.this.searchEmptyView.setText(LocaleController.getString("NoChats", R.string.NoChats));
                    ShareAlert.this.gridView.setAdapter(ShareAlert.this.listAdapter);
                    ShareAlert.this.listAdapter.notifyDataSetChanged();
                    if (top > 0) {
                        ShareAlert.this.layoutManager.scrollToPositionWithOffset(0, -top);
                    }
                }
                if (ShareAlert.this.searchAdapter != null) {
                    ShareAlert.this.searchAdapter.searchDialogs(text);
                }
            }
        });
        this.gridView = new RecyclerListView(context);
        this.gridView.setTag(Integer.valueOf(13));
        this.gridView.setPadding(0, 0, 0, AndroidUtilities.dp(8.0f));
        this.gridView.setClipToPadding(false);
        RecyclerListView recyclerListView = this.gridView;
        LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        this.layoutManager = gridLayoutManager;
        recyclerListView.setLayoutManager(gridLayoutManager);
        this.gridView.setHorizontalScrollBarEnabled(false);
        this.gridView.setVerticalScrollBarEnabled(false);
        this.gridView.addItemDecoration(new ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                int i = 0;
                Holder holder = (Holder) parent.getChildViewHolder(view);
                if (holder != null) {
                    int pos = holder.getAdapterPosition();
                    outRect.left = pos % 4 == 0 ? 0 : AndroidUtilities.dp(4.0f);
                    if (pos % 4 != 3) {
                        i = AndroidUtilities.dp(4.0f);
                    }
                    outRect.right = i;
                    return;
                }
                outRect.left = AndroidUtilities.dp(4.0f);
                outRect.right = AndroidUtilities.dp(4.0f);
            }
        });
        this.containerView.addView(this.gridView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 48.0f, 0.0f, 0.0f));
        recyclerListView = this.gridView;
        Adapter shareDialogsAdapter = new ShareDialogsAdapter(context);
        this.listAdapter = shareDialogsAdapter;
        recyclerListView.setAdapter(shareDialogsAdapter);
        this.gridView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                if (position >= 0) {
                    TLRPC$TL_dialog dialog;
                    if (ShareAlert.this.gridView.getAdapter() == ShareAlert.this.listAdapter) {
                        dialog = ShareAlert.this.listAdapter.getItem(position);
                    } else {
                        dialog = ShareAlert.this.searchAdapter.getItem(position);
                    }
                    if (dialog != null) {
                        ShareDialogCell cell = (ShareDialogCell) view;
                        if (ShareAlert.this.selectedDialogs.containsKey(Long.valueOf(dialog.id))) {
                            ShareAlert.this.selectedDialogs.remove(Long.valueOf(dialog.id));
                            cell.setChecked(false, true);
                        } else {
                            ShareAlert.this.selectedDialogs.put(Long.valueOf(dialog.id), dialog);
                            cell.setChecked(true, true);
                        }
                        ShareAlert.this.updateSelectedCount();
                    }
                }
            }
        });
        this.gridView.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                ShareAlert.this.updateLayout();
            }
        });
        this.searchEmptyView = new EmptyTextProgressView(context);
        this.searchEmptyView.setShowAtCenter(true);
        this.searchEmptyView.showTextView();
        this.searchEmptyView.setText(LocaleController.getString("NoChats", R.string.NoChats));
        this.gridView.setEmptyView(this.searchEmptyView);
        this.containerView.addView(this.searchEmptyView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 48.0f, 0.0f, 0.0f));
        this.containerView.addView(this.frameLayout, LayoutHelper.createFrame(-1, 48, 51));
        this.shadow = new View(context);
        this.shadow.setBackgroundResource(R.drawable.header_shadow);
        this.containerView.addView(this.shadow, LayoutHelper.createFrame(-1, 3.0f, 51, 0.0f, 48.0f, 0.0f, 0.0f));
        this.frameLayout2 = new FrameLayout(context);
        this.frameLayout2.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.frameLayout2.setTranslationY((float) AndroidUtilities.dp(53.0f));
        this.containerView.addView(this.frameLayout2, LayoutHelper.createFrame(-1, 48, 83));
        this.frameLayout2.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.commentTextView = new EditTextBoldCursor(context);
        this.commentTextView.setHint(LocaleController.getString("ShareComment", R.string.ShareComment));
        this.commentTextView.setMaxLines(1);
        this.commentTextView.setSingleLine(true);
        this.commentTextView.setGravity(19);
        this.commentTextView.setTextSize(1, 16.0f);
        this.commentTextView.setBackgroundDrawable(null);
        this.commentTextView.setHintTextColor(Theme.getColor(Theme.key_dialogTextHint));
        this.commentTextView.setImeOptions(268435456);
        this.commentTextView.setInputType(16385);
        this.commentTextView.setCursorColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.commentTextView.setCursorSize(AndroidUtilities.dp(20.0f));
        this.commentTextView.setCursorWidth(1.5f);
        this.commentTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.frameLayout2.addView(this.commentTextView, LayoutHelper.createFrame(-1, -1.0f, 51, 8.0f, 1.0f, 8.0f, 0.0f));
        this.shadow2 = new View(context);
        this.shadow2.setBackgroundResource(R.drawable.header_shadow_reverse);
        this.shadow2.setTranslationY((float) AndroidUtilities.dp(53.0f));
        this.containerView.addView(this.shadow2, LayoutHelper.createFrame(-1, 3.0f, 83, 0.0f, 0.0f, 0.0f, 48.0f));
        updateSelectedCount();
        if (!DialogsActivity.dialogsLoaded) {
            MessagesController.getInstance().loadDialogs(0, 100, true);
            ContactsController.getInstance().checkInviteText();
            DialogsActivity.dialogsLoaded = true;
        }
        if (this.listAdapter.dialogs.isEmpty()) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.dialogsNeedReload);
        }
    }

    private int getCurrentTop() {
        int i = 0;
        if (this.gridView.getChildCount() != 0) {
            View child = this.gridView.getChildAt(0);
            Holder holder = (Holder) this.gridView.findContainingViewHolder(child);
            if (holder != null) {
                int paddingTop = this.gridView.getPaddingTop();
                if (holder.getAdapterPosition() == 0 && child.getTop() >= 0) {
                    i = child.getTop();
                }
                return paddingTop - i;
            }
        }
        return -1000;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.dialogsNeedReload) {
            if (this.listAdapter != null) {
                this.listAdapter.fetchDialogs();
            }
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.dialogsNeedReload);
        }
    }

    protected boolean canDismissWithSwipe() {
        return false;
    }

    @SuppressLint({"NewApi"})
    private void updateLayout() {
        int newOffset = 0;
        if (this.gridView.getChildCount() > 0) {
            View child = this.gridView.getChildAt(0);
            Holder holder = (Holder) this.gridView.findContainingViewHolder(child);
            int top = child.getTop() - AndroidUtilities.dp(8.0f);
            if (top > 0 && holder != null && holder.getAdapterPosition() == 0) {
                newOffset = top;
            }
            if (this.scrollOffsetY != newOffset) {
                RecyclerListView recyclerListView = this.gridView;
                this.scrollOffsetY = newOffset;
                recyclerListView.setTopGlowOffset(newOffset);
                this.frameLayout.setTranslationY((float) this.scrollOffsetY);
                this.shadow.setTranslationY((float) this.scrollOffsetY);
                this.searchEmptyView.setTranslationY((float) this.scrollOffsetY);
                this.containerView.invalidate();
            }
        }
    }

    private void copyLink(Context context) {
        if (this.exportedMessageLink != null || this.linkToCopy != null) {
            try {
                ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", this.linkToCopy != null ? this.linkToCopy : this.exportedMessageLink.link));
                Toast.makeText(context, LocaleController.getString("LinkCopied", R.string.LinkCopied), 0).show();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    private void showCommentTextView(final boolean show) {
        float f = 0.0f;
        boolean z = true;
        try {
            if (this.frameLayout2.getTag() == null) {
                z = false;
            }
            if (show != z) {
                float f2;
                if (this.animatorSet != null) {
                    this.animatorSet.cancel();
                }
                this.frameLayout2.setTag(show ? Integer.valueOf(1) : null);
                AndroidUtilities.hideKeyboard(this.commentTextView);
                this.animatorSet = new AnimatorSet();
                AnimatorSet animatorSet = this.animatorSet;
                Animator[] animatorArr = new Animator[2];
                View view = this.shadow2;
                String str = "translationY";
                float[] fArr = new float[1];
                if (show) {
                    f2 = 0.0f;
                } else {
                    f2 = 53.0f;
                }
                fArr[0] = (float) AndroidUtilities.dp(f2);
                animatorArr[0] = ObjectAnimator.ofFloat(view, str, fArr);
                FrameLayout frameLayout = this.frameLayout2;
                String str2 = "translationY";
                float[] fArr2 = new float[1];
                if (!show) {
                    f = 53.0f;
                }
                fArr2[0] = (float) AndroidUtilities.dp(f);
                animatorArr[1] = ObjectAnimator.ofFloat(frameLayout, str2, fArr2);
                animatorSet.playTogether(animatorArr);
                this.animatorSet.setInterpolator(new DecelerateInterpolator());
                this.animatorSet.setDuration(180);
                this.animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        if (animation.equals(ShareAlert.this.animatorSet)) {
                            ShareAlert.this.gridView.setPadding(0, 0, 0, AndroidUtilities.dp(show ? 56.0f : 8.0f));
                            ShareAlert.this.animatorSet = null;
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (animation.equals(ShareAlert.this.animatorSet)) {
                            ShareAlert.this.animatorSet = null;
                        }
                    }
                });
                this.animatorSet.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSelectedCount() {
        if (this.selectedDialogs.isEmpty()) {
            showCommentTextView(false);
            this.doneButtonBadgeTextView.setVisibility(8);
            if (this.isPublicChannel || this.linkToCopy != null) {
                this.doneButtonTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlue2));
                this.doneButton.setEnabled(true);
                this.doneButtonTextView.setText(LocaleController.getString("CopyLink", R.string.CopyLink).toUpperCase());
                return;
            }
            this.doneButtonTextView.setTextColor(Theme.getColor(Theme.key_dialogTextGray4));
            this.doneButton.setEnabled(false);
            this.doneButtonTextView.setText(LocaleController.getString(SettingsJsonConstants.PROMPT_SEND_BUTTON_TITLE_DEFAULT, R.string.Send).toUpperCase());
            return;
        }
        showCommentTextView(true);
        this.doneButtonTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        this.doneButtonBadgeTextView.setVisibility(0);
        this.doneButtonBadgeTextView.setText(String.format("%d", new Object[]{Integer.valueOf(this.selectedDialogs.size())}));
        this.doneButtonTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlue3));
        this.doneButton.setEnabled(true);
        this.doneButtonTextView.setText(LocaleController.getString(SettingsJsonConstants.PROMPT_SEND_BUTTON_TITLE_DEFAULT, R.string.Send).toUpperCase());
    }

    public void dismiss() {
        super.dismiss();
        try {
            this.sendingMessageObject.restoreCaptionAndText();
        } catch (Exception e) {
        }
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.dialogsNeedReload);
    }

    public void setCheck(boolean checked) {
        if (VERSION.SDK_INT < 11) {
            this.quoteSwitch.resetLayout();
            this.quoteSwitch.requestLayout();
        }
        this.quoteSwitch.setChecked(checked);
        setCheckColor();
    }

    private void setCheckColor() {
        this.quoteSwitch.setColor(ApplicationLoader.applicationContext.getSharedPreferences(AppUtilities.THEME_PREFS, 0).getInt("chatAttachTextColor", -9079435));
    }
}
