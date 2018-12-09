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
import android.support.v4.content.C0235a;
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
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.support.widget.GridLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_exportedMessageLink;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.TL_channels_exportMessageLink;
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
    class C45753 implements OnTouchListener {
        C45753() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.Components.ShareAlert$4 */
    class C45764 implements OnTouchListener {
        C45764() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.Components.ShareAlert$5 */
    class C45775 implements OnClickListener {
        C45775() {
        }

        public void onClick(View view) {
            if (!ShareAlert.this.selectedDialogs.isEmpty() || (!ShareAlert.this.isPublicChannel && ShareAlert.this.linkToCopy == null)) {
                if (ShareAlert.this.sendingMessageObject != null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(ShareAlert.this.sendingMessageObject);
                    for (Entry entry : ShareAlert.this.selectedDialogs.entrySet()) {
                        if (ShareAlert.this.quoteSwitch.isChecked()) {
                            SendMessagesHelper.getInstance().sendMessage(arrayList, ((Long) entry.getKey()).longValue());
                        } else {
                            SendMessagesHelper.getInstance().processForwardFromMyName((MessageObject) arrayList.get(0), ((Long) entry.getKey()).longValue(), true);
                        }
                    }
                } else if (ShareAlert.this.sendingText != null) {
                    for (Entry key : ShareAlert.this.selectedDialogs.entrySet()) {
                        SendMessagesHelper.getInstance().sendMessage(ShareAlert.this.sendingText, ((Long) key.getKey()).longValue(), null, null, true, null, null, null);
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
    class C45786 implements OnCheckedChangeListener {
        C45786() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit().putBoolean("directShareQuote", z).apply();
            ShareAlert.this.setCheckColor();
        }
    }

    /* renamed from: org.telegram.ui.Components.ShareAlert$8 */
    class C45808 implements TextWatcher {
        C45808() {
        }

        public void afterTextChanged(Editable editable) {
            String obj = ShareAlert.this.nameTextView.getText().toString();
            if (obj.length() != 0) {
                if (ShareAlert.this.gridView.getAdapter() != ShareAlert.this.searchAdapter) {
                    ShareAlert.this.topBeforeSwitch = ShareAlert.this.getCurrentTop();
                    ShareAlert.this.gridView.setAdapter(ShareAlert.this.searchAdapter);
                    ShareAlert.this.searchAdapter.notifyDataSetChanged();
                }
                if (ShareAlert.this.searchEmptyView != null) {
                    ShareAlert.this.searchEmptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
                }
            } else if (ShareAlert.this.gridView.getAdapter() != ShareAlert.this.listAdapter) {
                int access$2100 = ShareAlert.this.getCurrentTop();
                ShareAlert.this.searchEmptyView.setText(LocaleController.getString("NoChats", R.string.NoChats));
                ShareAlert.this.gridView.setAdapter(ShareAlert.this.listAdapter);
                ShareAlert.this.listAdapter.notifyDataSetChanged();
                if (access$2100 > 0) {
                    ShareAlert.this.layoutManager.scrollToPositionWithOffset(0, -access$2100);
                }
            }
            if (ShareAlert.this.searchAdapter != null) {
                ShareAlert.this.searchAdapter.searchDialogs(obj);
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.Components.ShareAlert$9 */
    class C45819 extends ItemDecoration {
        C45819() {
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            int i = 0;
            Holder holder = (Holder) recyclerView.getChildViewHolder(view);
            if (holder != null) {
                int adapterPosition = holder.getAdapterPosition();
                rect.left = adapterPosition % 4 == 0 ? 0 : AndroidUtilities.dp(4.0f);
                if (adapterPosition % 4 != 3) {
                    i = AndroidUtilities.dp(4.0f);
                }
                rect.right = i;
                return;
            }
            rect.left = AndroidUtilities.dp(4.0f);
            rect.right = AndroidUtilities.dp(4.0f);
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
            int i;
            TLRPC$TL_dialog tLRPC$TL_dialog;
            int i2 = 0;
            this.dialogs.clear();
            if (ShareAlert.this.favsFirst) {
                for (i = 0; i < MessagesController.getInstance().dialogsFavs.size(); i++) {
                    tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogsFavs.get(i);
                    int i3 = (int) tLRPC$TL_dialog.id;
                    int i4 = (int) (tLRPC$TL_dialog.id >> 32);
                    if (!(i3 == 0 || i4 == 1)) {
                        if (i3 > 0) {
                            this.dialogs.add(tLRPC$TL_dialog);
                        } else {
                            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-i3));
                            if (!(chat == null || ChatObject.isNotInChat(chat) || (ChatObject.isChannel(chat) && !chat.creator && !chat.megagroup))) {
                                this.dialogs.add(tLRPC$TL_dialog);
                            }
                        }
                    }
                }
            }
            while (i2 < MessagesController.getInstance().dialogsServerOnly.size()) {
                tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogsServerOnly.get(i2);
                if (!ShareAlert.this.favsFirst || !Favourite.isFavourite(Long.valueOf(tLRPC$TL_dialog.id))) {
                    i = (int) tLRPC$TL_dialog.id;
                    i3 = (int) (tLRPC$TL_dialog.id >> 32);
                    if (!(i == 0 || i3 == 1)) {
                        if (i > 0) {
                            this.dialogs.add(tLRPC$TL_dialog);
                        } else {
                            Chat chat2 = MessagesController.getInstance().getChat(Integer.valueOf(-i));
                            if (!(chat2 == null || ChatObject.isNotInChat(chat2) || (ChatObject.isChannel(chat2) && !chat2.creator && !chat2.megagroup))) {
                                this.dialogs.add(tLRPC$TL_dialog);
                            }
                        }
                    }
                }
                i2++;
            }
            notifyDataSetChanged();
        }

        public TLRPC$TL_dialog getItem(int i) {
            return (i < 0 || i >= this.dialogs.size()) ? null : (TLRPC$TL_dialog) this.dialogs.get(i);
        }

        public int getItemCount() {
            return this.dialogs.size();
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            ShareDialogCell shareDialogCell = (ShareDialogCell) viewHolder.itemView;
            TLRPC$TL_dialog item = getItem(i);
            shareDialogCell.setDialog((int) item.id, ShareAlert.this.selectedDialogs.containsKey(Long.valueOf(item.id)), null);
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View shareDialogCell = new ShareDialogCell(this.context);
            shareDialogCell.setLayoutParams(new LayoutParams(-1, AndroidUtilities.dp(100.0f)));
            return new Holder(shareDialogCell);
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

        private void searchDialogsInternal(final String str, final int i) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.ui.Components.ShareAlert$ShareSearchAdapter$1$1 */
                class C45821 implements Comparator<DialogSearchResult> {
                    C45821() {
                    }

                    public int compare(DialogSearchResult dialogSearchResult, DialogSearchResult dialogSearchResult2) {
                        return dialogSearchResult.date < dialogSearchResult2.date ? 1 : dialogSearchResult.date > dialogSearchResult2.date ? -1 : 0;
                    }
                }

                public void run() {
                    try {
                        String toLowerCase = str.trim().toLowerCase();
                        if (toLowerCase.length() == 0) {
                            ShareSearchAdapter.this.lastSearchId = -1;
                            ShareSearchAdapter.this.updateSearchResults(new ArrayList(), ShareSearchAdapter.this.lastSearchId);
                            return;
                        }
                        int i;
                        String e;
                        String str;
                        int lastIndexOf;
                        String substring;
                        int i2;
                        AbstractSerializedData g;
                        DialogSearchResult dialogSearchResult;
                        TLObject TLdeserialize;
                        String translitString = LocaleController.getInstance().getTranslitString(toLowerCase);
                        String str2 = (toLowerCase.equals(translitString) || translitString.length() == 0) ? null : translitString;
                        String[] strArr = new String[((str2 != null ? 1 : 0) + 1)];
                        strArr[0] = toLowerCase;
                        if (str2 != null) {
                            strArr[1] = str2;
                        }
                        Iterable arrayList = new ArrayList();
                        Iterable arrayList2 = new ArrayList();
                        int i3 = 0;
                        HashMap hashMap = new HashMap();
                        SQLiteCursor b = MessagesStorage.getInstance().getDatabase().b("SELECT did, date FROM dialogs ORDER BY date DESC LIMIT 400", new Object[0]);
                        while (b.a()) {
                            long d = b.d(0);
                            DialogSearchResult dialogSearchResult2 = new DialogSearchResult();
                            dialogSearchResult2.date = b.b(1);
                            hashMap.put(Long.valueOf(d), dialogSearchResult2);
                            i = (int) d;
                            int i4 = (int) (d >> 32);
                            if (!(i == 0 || i4 == 1)) {
                                if (i > 0) {
                                    if (!arrayList.contains(Integer.valueOf(i))) {
                                        arrayList.add(Integer.valueOf(i));
                                    }
                                } else if (!arrayList2.contains(Integer.valueOf(-i))) {
                                    arrayList2.add(Integer.valueOf(-i));
                                }
                            }
                        }
                        b.b();
                        if (!arrayList.isEmpty()) {
                            SQLiteCursor b2 = MessagesStorage.getInstance().getDatabase().b(String.format(Locale.US, "SELECT data, status, name FROM users WHERE uid IN(%s)", new Object[]{TextUtils.join(",", arrayList)}), new Object[0]);
                            while (b2.a()) {
                                e = b2.e(2);
                                translitString = LocaleController.getInstance().getTranslitString(e);
                                str = e.equals(translitString) ? null : translitString;
                                lastIndexOf = e.lastIndexOf(";;;");
                                substring = lastIndexOf != -1 ? e.substring(lastIndexOf + 3) : null;
                                int length = strArr.length;
                                i = 0;
                                i2 = 0;
                                while (i < length) {
                                    String str3 = strArr[i];
                                    lastIndexOf = (e.startsWith(str3) || e.contains(" " + str3) || (str != null && (str.startsWith(str3) || str.contains(" " + str3)))) ? 1 : (substring == null || !substring.startsWith(str3)) ? i2 : 2;
                                    if (lastIndexOf != 0) {
                                        g = b2.g(0);
                                        if (g != null) {
                                            TLObject TLdeserialize2 = User.TLdeserialize(g, g.readInt32(false), false);
                                            g.reuse();
                                            dialogSearchResult = (DialogSearchResult) hashMap.get(Long.valueOf((long) TLdeserialize2.id));
                                            if (TLdeserialize2.status != null) {
                                                TLdeserialize2.status.expires = b2.b(1);
                                            }
                                            if (lastIndexOf == 1) {
                                                dialogSearchResult.name = AndroidUtilities.generateSearchName(TLdeserialize2.first_name, TLdeserialize2.last_name, str3);
                                            } else {
                                                dialogSearchResult.name = AndroidUtilities.generateSearchName("@" + TLdeserialize2.username, null, "@" + str3);
                                            }
                                            dialogSearchResult.object = TLdeserialize2;
                                            dialogSearchResult.dialog.id = (long) TLdeserialize2.id;
                                            i2 = i3 + 1;
                                            i3 = i2;
                                        }
                                        i2 = i3;
                                        i3 = i2;
                                    } else {
                                        i++;
                                        i2 = lastIndexOf;
                                    }
                                }
                                i2 = i3;
                                i3 = i2;
                            }
                            b2.b();
                        }
                        if (!arrayList2.isEmpty()) {
                            SQLiteCursor b3 = MessagesStorage.getInstance().getDatabase().b(String.format(Locale.US, "SELECT data, name FROM chats WHERE uid IN(%s)", new Object[]{TextUtils.join(",", arrayList2)}), new Object[0]);
                            while (b3.a()) {
                                substring = b3.e(1);
                                translitString = LocaleController.getInstance().getTranslitString(substring);
                                if (substring.equals(translitString)) {
                                    translitString = null;
                                }
                                lastIndexOf = 0;
                                while (lastIndexOf < strArr.length) {
                                    str = strArr[lastIndexOf];
                                    if (substring.startsWith(str) || substring.contains(" " + str) || (r0 != null && (r0.startsWith(str) || r0.contains(" " + str)))) {
                                        g = b3.g(0);
                                        if (g != null) {
                                            TLdeserialize = Chat.TLdeserialize(g, g.readInt32(false), false);
                                            g.reuse();
                                            if (TLdeserialize == null || ChatObject.isNotInChat(TLdeserialize) || (ChatObject.isChannel(TLdeserialize) && !TLdeserialize.creator && ((TLdeserialize.admin_rights == null || !TLdeserialize.admin_rights.post_messages) && !TLdeserialize.megagroup))) {
                                                i2 = i3;
                                            } else {
                                                dialogSearchResult = (DialogSearchResult) hashMap.get(Long.valueOf(-((long) TLdeserialize.id)));
                                                dialogSearchResult.name = AndroidUtilities.generateSearchName(TLdeserialize.title, null, str);
                                                dialogSearchResult.object = TLdeserialize;
                                                dialogSearchResult.dialog.id = (long) (-TLdeserialize.id);
                                                i2 = i3 + 1;
                                            }
                                            i3 = i2;
                                        }
                                    } else {
                                        lastIndexOf++;
                                    }
                                }
                            }
                            b3.b();
                        }
                        Object arrayList3 = new ArrayList(i3);
                        for (DialogSearchResult dialogSearchResult3 : hashMap.values()) {
                            if (!(dialogSearchResult3.object == null || dialogSearchResult3.name == null)) {
                                arrayList3.add(dialogSearchResult3);
                            }
                        }
                        SQLiteCursor b4 = MessagesStorage.getInstance().getDatabase().b("SELECT u.data, u.status, u.name, u.uid FROM users as u INNER JOIN contacts as c ON u.uid = c.uid", new Object[0]);
                        while (b4.a()) {
                            if (!hashMap.containsKey(Long.valueOf((long) b4.b(3)))) {
                                String e2 = b4.e(2);
                                translitString = LocaleController.getInstance().getTranslitString(e2);
                                String str4 = e2.equals(translitString) ? null : translitString;
                                i3 = e2.lastIndexOf(";;;");
                                toLowerCase = i3 != -1 ? e2.substring(i3 + 3) : null;
                                int length2 = strArr.length;
                                Object obj = null;
                                i3 = 0;
                                while (i3 < length2) {
                                    e = strArr[i3];
                                    if (e2.startsWith(e) || e2.contains(" " + e) || (str4 != null && (str4.startsWith(e) || str4.contains(" " + e)))) {
                                        obj = 1;
                                    } else if (toLowerCase != null && toLowerCase.startsWith(e)) {
                                        obj = 2;
                                    }
                                    if (i2 != null) {
                                        AbstractSerializedData g2 = b4.g(0);
                                        if (g2 != null) {
                                            TLdeserialize = User.TLdeserialize(g2, g2.readInt32(false), false);
                                            g2.reuse();
                                            DialogSearchResult dialogSearchResult4 = new DialogSearchResult();
                                            if (TLdeserialize.status != null) {
                                                TLdeserialize.status.expires = b4.b(1);
                                            }
                                            dialogSearchResult4.dialog.id = (long) TLdeserialize.id;
                                            dialogSearchResult4.object = TLdeserialize;
                                            if (i2 == 1) {
                                                dialogSearchResult4.name = AndroidUtilities.generateSearchName(TLdeserialize.first_name, TLdeserialize.last_name, e);
                                            } else {
                                                dialogSearchResult4.name = AndroidUtilities.generateSearchName("@" + TLdeserialize.username, null, "@" + e);
                                            }
                                            arrayList3.add(dialogSearchResult4);
                                        }
                                    } else {
                                        i3++;
                                    }
                                }
                            }
                        }
                        b4.b();
                        Collections.sort(arrayList3, new C45821());
                        ShareSearchAdapter.this.updateSearchResults(arrayList3, i);
                    } catch (Throwable e3) {
                        FileLog.e(e3);
                    }
                }
            });
        }

        private void updateSearchResults(final ArrayList<DialogSearchResult> arrayList, final int i) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    boolean z = true;
                    if (i == ShareSearchAdapter.this.lastSearchId) {
                        for (int i = 0; i < arrayList.size(); i++) {
                            DialogSearchResult dialogSearchResult = (DialogSearchResult) arrayList.get(i);
                            if (dialogSearchResult.object instanceof User) {
                                MessagesController.getInstance().putUser((User) dialogSearchResult.object, true);
                            } else if (dialogSearchResult.object instanceof Chat) {
                                MessagesController.getInstance().putChat((Chat) dialogSearchResult.object, true);
                            }
                        }
                        boolean z2 = !ShareSearchAdapter.this.searchResult.isEmpty() && arrayList.isEmpty();
                        if (!(ShareSearchAdapter.this.searchResult.isEmpty() && arrayList.isEmpty())) {
                            z = false;
                        }
                        if (z2) {
                            ShareAlert.this.topBeforeSwitch = ShareAlert.this.getCurrentTop();
                        }
                        ShareSearchAdapter.this.searchResult = arrayList;
                        ShareSearchAdapter.this.notifyDataSetChanged();
                        if (!z && !z2 && ShareAlert.this.topBeforeSwitch > 0) {
                            ShareAlert.this.layoutManager.scrollToPositionWithOffset(0, -ShareAlert.this.topBeforeSwitch);
                            ShareAlert.this.topBeforeSwitch = C3446C.PRIORITY_DOWNLOAD;
                        }
                    }
                }
            });
        }

        public TLRPC$TL_dialog getItem(int i) {
            return (i < 0 || i >= this.searchResult.size()) ? null : ((DialogSearchResult) this.searchResult.get(i)).dialog;
        }

        public int getItemCount() {
            return this.searchResult.size();
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            DialogSearchResult dialogSearchResult = (DialogSearchResult) this.searchResult.get(i);
            ((ShareDialogCell) viewHolder.itemView).setDialog((int) dialogSearchResult.dialog.id, ShareAlert.this.selectedDialogs.containsKey(Long.valueOf(dialogSearchResult.dialog.id)), dialogSearchResult.name);
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View shareDialogCell = new ShareDialogCell(this.context);
            shareDialogCell.setLayoutParams(new LayoutParams(-1, AndroidUtilities.dp(100.0f)));
            return new Holder(shareDialogCell);
        }

        public void searchDialogs(final String str) {
            if (str == null || this.lastSearchText == null || !str.equals(this.lastSearchText)) {
                this.lastSearchText = str;
                try {
                    if (this.searchTimer != null) {
                        this.searchTimer.cancel();
                        this.searchTimer = null;
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                if (str == null || str.length() == 0) {
                    this.searchResult.clear();
                    ShareAlert.this.topBeforeSwitch = ShareAlert.this.getCurrentTop();
                    notifyDataSetChanged();
                    return;
                }
                final int i = this.lastSearchId + 1;
                this.lastSearchId = i;
                this.searchTimer = new Timer();
                this.searchTimer.schedule(new TimerTask() {
                    public void run() {
                        try {
                            cancel();
                            ShareSearchAdapter.this.searchTimer.cancel();
                            ShareSearchAdapter.this.searchTimer = null;
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                        ShareSearchAdapter.this.searchDialogsInternal(str, i);
                    }
                }, 200, 300);
            }
        }
    }

    public ShareAlert(final Context context, ArrayList<MessageObject> arrayList, String str, boolean z, String str2, final boolean z2) {
        super(context, true);
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.favsFirst = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("directShareFavsFirst", false);
        this.linkToCopy = str2;
        this.sendingMessageObjects = arrayList;
        this.searchAdapter = new ShareSearchAdapter(context);
        this.isPublicChannel = z;
        this.sendingText = str;
        if (z) {
            this.loadingLink = true;
            TLObject tL_channels_exportMessageLink = new TL_channels_exportMessageLink();
            tL_channels_exportMessageLink.id = ((MessageObject) arrayList.get(0)).getId();
            tL_channels_exportMessageLink.channel = MessagesController.getInputChannel(((MessageObject) arrayList.get(0)).messageOwner.to_id.channel_id);
            ConnectionsManager.getInstance().sendRequest(tL_channels_exportMessageLink, new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLObject != null) {
                                ShareAlert.this.exportedMessageLink = (TLRPC$TL_exportedMessageLink) tLObject;
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

            protected void onDraw(Canvas canvas) {
                ShareAlert.this.shadowDrawable.setBounds(0, ShareAlert.this.scrollOffsetY - ShareAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                ShareAlert.this.shadowDrawable.draw(canvas);
            }

            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0 || ShareAlert.this.scrollOffsetY == 0 || motionEvent.getY() >= ((float) ShareAlert.this.scrollOffsetY)) {
                    return super.onInterceptTouchEvent(motionEvent);
                }
                ShareAlert.this.dismiss();
                return true;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                ShareAlert.this.updateLayout();
            }

            protected void onMeasure(int i, int i2) {
                float f = 8.0f;
                int size = MeasureSpec.getSize(i2);
                if (VERSION.SDK_INT >= 21) {
                    size -= AndroidUtilities.statusBarHeight;
                }
                int access$2500 = ShareAlert.backgroundPaddingTop + ((Math.max(3, (int) Math.ceil((double) (((float) Math.max(ShareAlert.this.searchAdapter.getItemCount(), ShareAlert.this.listAdapter.getItemCount())) / 4.0f))) * AndroidUtilities.dp(100.0f)) + AndroidUtilities.dp(48.0f));
                int dp = access$2500 < size ? 0 : (size - ((size / 5) * 3)) + AndroidUtilities.dp(8.0f);
                if (ShareAlert.this.gridView.getPaddingTop() != dp) {
                    this.ignoreLayout = true;
                    RecyclerListView access$800 = ShareAlert.this.gridView;
                    if (ShareAlert.this.frameLayout2.getTag() != null) {
                        f = 56.0f;
                    }
                    access$800.setPadding(0, dp, 0, AndroidUtilities.dp(f));
                    this.ignoreLayout = false;
                }
                super.onMeasure(i, MeasureSpec.makeMeasureSpec(Math.min(access$2500, size), 1073741824));
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                return !ShareAlert.this.isDismissed() && super.onTouchEvent(motionEvent);
            }

            public void requestLayout() {
                if (!this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.containerView.setWillNotDraw(false);
        this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        this.frameLayout = new FrameLayout(context);
        this.frameLayout.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.frameLayout.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.doneButton = new LinearLayout(context);
        this.doneButton.setOrientation(0);
        this.doneButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 0));
        this.doneButton.setPadding(AndroidUtilities.dp(21.0f), 0, AndroidUtilities.dp(21.0f), 0);
        this.frameLayout.addView(this.doneButton, LayoutHelper.createFrame(-2, -1, 53));
        this.doneButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!ShareAlert.this.selectedDialogs.isEmpty() || (!ShareAlert.this.isPublicChannel && ShareAlert.this.linkToCopy == null)) {
                    if (ShareAlert.this.sendingMessageObjects != null) {
                        for (Entry entry : ShareAlert.this.selectedDialogs.entrySet()) {
                            if (ShareAlert.this.frameLayout2.getTag() != null && ShareAlert.this.commentTextView.length() > 0) {
                                SendMessagesHelper.getInstance().sendMessage(ShareAlert.this.commentTextView.getText().toString(), ((Long) entry.getKey()).longValue(), null, null, true, null, null, null);
                            }
                            SendMessagesHelper.getInstance().sendMessage(ShareAlert.this.sendingMessageObjects, ((Long) entry.getKey()).longValue());
                        }
                    } else if (ShareAlert.this.sendingText != null) {
                        for (Entry entry2 : ShareAlert.this.selectedDialogs.entrySet()) {
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
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        this.quoteSwitch = new Switch(context);
        this.quoteSwitch.setTag("chat");
        this.quoteSwitch.setDuplicateParentStateEnabled(false);
        this.quoteSwitch.setFocusable(false);
        this.quoteSwitch.setFocusableInTouchMode(false);
        this.quoteSwitch.setClickable(true);
        setCheck(sharedPreferences.getBoolean("directShareQuote", true));
        setCheckColor();
        this.frameLayout.addView(this.quoteSwitch, LayoutHelper.createFrame(-2, -2.0f, 19, BitmapDescriptorFactory.HUE_RED, 2.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.quoteSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit().putBoolean("directShareQuote", z).apply();
                ShareAlert.this.setCheckColor();
            }
        });
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (z2) {
                    ShareAlert.this.setCheck(false);
                    ShareAlert.this.quoteSwitch.setVisibility(8);
                    ShareAlert.this.quoteTextView.setVisibility(8);
                }
            }
        }, 200);
        this.quoteTextView = new TextView(context);
        this.quoteTextView.setTextSize(1, 9.0f);
        this.quoteTextView.setTextColor(C0235a.c(getContext(), R.color.black));
        this.quoteTextView.setGravity(17);
        this.quoteTextView.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        this.quoteTextView.setText(LocaleController.getString("Quote", R.string.Quote).toUpperCase());
        this.quoteTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.frameLayout.addView(this.quoteTextView, LayoutHelper.createFrame(-2, -2.0f, 51, 12.0f, 2.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        View imageView = new ImageView(context);
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
        this.nameTextView.setImeOptions(ErrorDialogData.BINDER_CRASH);
        this.nameTextView.setInputType(16385);
        this.nameTextView.setCursorColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.nameTextView.setCursorSize(AndroidUtilities.dp(20.0f));
        this.nameTextView.setCursorWidth(1.5f);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.frameLayout.addView(this.nameTextView, LayoutHelper.createFrame(-1, -1.0f, 51, 48.0f, 2.0f, 96.0f, BitmapDescriptorFactory.HUE_RED));
        this.nameTextView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
                String obj = ShareAlert.this.nameTextView.getText().toString();
                if (obj.length() != 0) {
                    if (ShareAlert.this.gridView.getAdapter() != ShareAlert.this.searchAdapter) {
                        ShareAlert.this.topBeforeSwitch = ShareAlert.this.getCurrentTop();
                        ShareAlert.this.gridView.setAdapter(ShareAlert.this.searchAdapter);
                        ShareAlert.this.searchAdapter.notifyDataSetChanged();
                    }
                    if (ShareAlert.this.searchEmptyView != null) {
                        ShareAlert.this.searchEmptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
                    }
                } else if (ShareAlert.this.gridView.getAdapter() != ShareAlert.this.listAdapter) {
                    int access$2100 = ShareAlert.this.getCurrentTop();
                    ShareAlert.this.searchEmptyView.setText(LocaleController.getString("NoChats", R.string.NoChats));
                    ShareAlert.this.gridView.setAdapter(ShareAlert.this.listAdapter);
                    ShareAlert.this.listAdapter.notifyDataSetChanged();
                    if (access$2100 > 0) {
                        ShareAlert.this.layoutManager.scrollToPositionWithOffset(0, -access$2100);
                    }
                }
                if (ShareAlert.this.searchAdapter != null) {
                    ShareAlert.this.searchAdapter.searchDialogs(obj);
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
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
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
                int i = 0;
                Holder holder = (Holder) recyclerView.getChildViewHolder(view);
                if (holder != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    rect.left = adapterPosition % 4 == 0 ? 0 : AndroidUtilities.dp(4.0f);
                    if (adapterPosition % 4 != 3) {
                        i = AndroidUtilities.dp(4.0f);
                    }
                    rect.right = i;
                    return;
                }
                rect.left = AndroidUtilities.dp(4.0f);
                rect.right = AndroidUtilities.dp(4.0f);
            }
        });
        this.containerView.addView(this.gridView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        recyclerListView = this.gridView;
        Adapter shareDialogsAdapter = new ShareDialogsAdapter(context);
        this.listAdapter = shareDialogsAdapter;
        recyclerListView.setAdapter(shareDialogsAdapter);
        this.gridView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int i) {
                if (i >= 0) {
                    TLRPC$TL_dialog item = ShareAlert.this.gridView.getAdapter() == ShareAlert.this.listAdapter ? ShareAlert.this.listAdapter.getItem(i) : ShareAlert.this.searchAdapter.getItem(i);
                    if (item != null) {
                        ShareDialogCell shareDialogCell = (ShareDialogCell) view;
                        if (ShareAlert.this.selectedDialogs.containsKey(Long.valueOf(item.id))) {
                            ShareAlert.this.selectedDialogs.remove(Long.valueOf(item.id));
                            shareDialogCell.setChecked(false, true);
                        } else {
                            ShareAlert.this.selectedDialogs.put(Long.valueOf(item.id), item);
                            shareDialogCell.setChecked(true, true);
                        }
                        ShareAlert.this.updateSelectedCount();
                    }
                }
            }
        });
        this.gridView.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                ShareAlert.this.updateLayout();
            }
        });
        this.searchEmptyView = new EmptyTextProgressView(context);
        this.searchEmptyView.setShowAtCenter(true);
        this.searchEmptyView.showTextView();
        this.searchEmptyView.setText(LocaleController.getString("NoChats", R.string.NoChats));
        this.gridView.setEmptyView(this.searchEmptyView);
        this.containerView.addView(this.searchEmptyView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.containerView.addView(this.frameLayout, LayoutHelper.createFrame(-1, 48, 51));
        this.shadow = new View(context);
        this.shadow.setBackgroundResource(R.drawable.header_shadow);
        this.containerView.addView(this.shadow, LayoutHelper.createFrame(-1, 3.0f, 51, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.frameLayout2 = new FrameLayout(context);
        this.frameLayout2.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.frameLayout2.setTranslationY((float) AndroidUtilities.dp(53.0f));
        this.containerView.addView(this.frameLayout2, LayoutHelper.createFrame(-1, 48, 83));
        this.frameLayout2.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
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
        this.commentTextView.setImeOptions(ErrorDialogData.BINDER_CRASH);
        this.commentTextView.setInputType(16385);
        this.commentTextView.setCursorColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.commentTextView.setCursorSize(AndroidUtilities.dp(20.0f));
        this.commentTextView.setCursorWidth(1.5f);
        this.commentTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.frameLayout2.addView(this.commentTextView, LayoutHelper.createFrame(-1, -1.0f, 51, 8.0f, 1.0f, 8.0f, BitmapDescriptorFactory.HUE_RED));
        this.shadow2 = new View(context);
        this.shadow2.setBackgroundResource(R.drawable.header_shadow_reverse);
        this.shadow2.setTranslationY((float) AndroidUtilities.dp(53.0f));
        this.containerView.addView(this.shadow2, LayoutHelper.createFrame(-1, 3.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
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

    public ShareAlert(final Context context, MessageObject messageObject, String str, boolean z, String str2, final boolean z2, boolean z3) {
        super(context, true);
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.favsFirst = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("directShareFavsFirst", false);
        this.linkToCopy = str2;
        this.sendingMessageObject = messageObject;
        this.searchAdapter = new ShareSearchAdapter(context);
        this.isPublicChannel = z;
        this.sendingText = str;
        if (z) {
            this.loadingLink = true;
            TLObject tL_channels_exportMessageLink = new TL_channels_exportMessageLink();
            tL_channels_exportMessageLink.id = messageObject.getId();
            tL_channels_exportMessageLink.channel = MessagesController.getInputChannel(messageObject.messageOwner.to_id.channel_id);
            ConnectionsManager.getInstance().sendRequest(tL_channels_exportMessageLink, new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLObject != null) {
                                ShareAlert.this.exportedMessageLink = (TLRPC$TL_exportedMessageLink) tLObject;
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

            protected void onDraw(Canvas canvas) {
                ShareAlert.this.shadowDrawable.setBounds(0, ShareAlert.this.scrollOffsetY - ShareAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                ShareAlert.this.shadowDrawable.draw(canvas);
            }

            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0 || ShareAlert.this.scrollOffsetY == 0 || motionEvent.getY() >= ((float) ShareAlert.this.scrollOffsetY)) {
                    return super.onInterceptTouchEvent(motionEvent);
                }
                ShareAlert.this.dismiss();
                return true;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                ShareAlert.this.updateLayout();
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i2);
                if (VERSION.SDK_INT >= 21) {
                    size -= AndroidUtilities.statusBarHeight;
                }
                int access$700 = ShareAlert.backgroundPaddingTop + ((Math.max(3, (int) Math.ceil((double) (((float) Math.max(ShareAlert.this.searchAdapter.getItemCount(), ShareAlert.this.listAdapter.getItemCount())) / 4.0f))) * AndroidUtilities.dp(100.0f)) + AndroidUtilities.dp(48.0f));
                int dp = access$700 < size ? 0 : (size - ((size / 5) * 3)) + AndroidUtilities.dp(8.0f);
                if (ShareAlert.this.gridView.getPaddingTop() != dp) {
                    this.ignoreLayout = true;
                    ShareAlert.this.gridView.setPadding(0, dp, 0, AndroidUtilities.dp(8.0f));
                    this.ignoreLayout = false;
                }
                super.onMeasure(i, MeasureSpec.makeMeasureSpec(Math.min(access$700, size), 1073741824));
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                return !ShareAlert.this.isDismissed() && super.onTouchEvent(motionEvent);
            }

            public void requestLayout() {
                if (!this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.containerView.setWillNotDraw(false);
        this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        this.frameLayout = new FrameLayout(context);
        this.frameLayout.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.frameLayout.setOnTouchListener(new C45753());
        this.frameLayout2 = new FrameLayout(context);
        this.frameLayout2.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.frameLayout2.setTranslationY((float) AndroidUtilities.dp(53.0f));
        this.containerView.addView(this.frameLayout2, LayoutHelper.createFrame(-1, 48, 83));
        this.frameLayout2.setOnTouchListener(new C45764());
        this.doneButton = new LinearLayout(context);
        this.doneButton.setOrientation(0);
        this.doneButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 0));
        this.doneButton.setPadding(AndroidUtilities.dp(21.0f), 0, AndroidUtilities.dp(21.0f), 0);
        this.frameLayout.addView(this.doneButton, LayoutHelper.createFrame(-2, -1, 53));
        this.doneButton.setOnClickListener(new C45775());
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
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        this.quoteSwitch = new Switch(context);
        this.quoteSwitch.setTag("chat");
        this.quoteSwitch.setDuplicateParentStateEnabled(false);
        this.quoteSwitch.setFocusable(false);
        this.quoteSwitch.setFocusableInTouchMode(false);
        this.quoteSwitch.setClickable(true);
        setCheck(sharedPreferences.getBoolean("directShareQuote", true));
        setCheckColor();
        this.frameLayout.addView(this.quoteSwitch, LayoutHelper.createFrame(-2, -2.0f, 19, BitmapDescriptorFactory.HUE_RED, 2.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.quoteSwitch.setOnCheckedChangeListener(new C45786());
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (z2) {
                    Log.d("alireza", "alireza tttttttttt 2" + z2);
                    ShareAlert.this.setCheck(false);
                    ShareAlert.this.quoteSwitch.setVisibility(8);
                    ShareAlert.this.quoteTextView.setVisibility(8);
                }
            }
        }, 200);
        this.quoteTextView = new TextView(context);
        this.quoteTextView.setTextSize(1, 9.0f);
        this.quoteTextView.setTextColor(C0235a.c(getContext(), R.color.black));
        this.quoteTextView.setGravity(17);
        this.quoteTextView.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        this.quoteTextView.setText(LocaleController.getString("Quote", R.string.Quote).toUpperCase());
        this.quoteTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.frameLayout.addView(this.quoteTextView, LayoutHelper.createFrame(-2, -2.0f, 51, 12.0f, 2.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.nameTextView = new EditTextBoldCursor(context);
        this.nameTextView.setHint(LocaleController.getString("ShareSendTo", R.string.ShareSendTo));
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setGravity(19);
        this.nameTextView.setTextSize(1, 16.0f);
        this.nameTextView.setBackgroundDrawable(null);
        this.nameTextView.setHintTextColor(Theme.getColor(Theme.key_dialogTextHint));
        this.nameTextView.setImeOptions(ErrorDialogData.BINDER_CRASH);
        this.nameTextView.setInputType(16385);
        AndroidUtilities.clearCursorDrawable(this.nameTextView);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.frameLayout.addView(this.nameTextView, LayoutHelper.createFrame(-1, -1.0f, 51, 48.0f, 2.0f, 96.0f, BitmapDescriptorFactory.HUE_RED));
        this.nameTextView.addTextChangedListener(new C45808());
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
        this.gridView.addItemDecoration(new C45819());
        this.containerView.addView(this.gridView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        recyclerListView = this.gridView;
        Adapter shareDialogsAdapter = new ShareDialogsAdapter(context);
        this.listAdapter = shareDialogsAdapter;
        recyclerListView.setAdapter(shareDialogsAdapter);
        this.gridView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int i) {
                if (i >= 0) {
                    TLRPC$TL_dialog item = ShareAlert.this.gridView.getAdapter() == ShareAlert.this.listAdapter ? ShareAlert.this.listAdapter.getItem(i) : ShareAlert.this.searchAdapter.getItem(i);
                    if (item != null) {
                        ShareDialogCell shareDialogCell = (ShareDialogCell) view;
                        if (ShareAlert.this.selectedDialogs.containsKey(Long.valueOf(item.id))) {
                            ShareAlert.this.selectedDialogs.remove(Long.valueOf(item.id));
                            shareDialogCell.setChecked(false, true);
                        } else {
                            ShareAlert.this.selectedDialogs.put(Long.valueOf(item.id), item);
                            shareDialogCell.setChecked(true, true);
                        }
                        ShareAlert.this.updateSelectedCount();
                    }
                }
            }
        });
        this.gridView.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                ShareAlert.this.updateLayout();
            }
        });
        this.searchEmptyView = new EmptyTextProgressView(context);
        this.searchEmptyView.setShowAtCenter(true);
        this.searchEmptyView.showTextView();
        this.searchEmptyView.setText(LocaleController.getString("NoChats", R.string.NoChats));
        this.gridView.setEmptyView(this.searchEmptyView);
        this.containerView.addView(this.searchEmptyView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.containerView.addView(this.frameLayout, LayoutHelper.createFrame(-1, 48, 51));
        this.shadow = new View(context);
        this.shadow.setBackgroundResource(R.drawable.header_shadow);
        this.containerView.addView(this.shadow, LayoutHelper.createFrame(-1, 3.0f, 51, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
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

    private void copyLink(Context context) {
        if (this.exportedMessageLink != null || this.linkToCopy != null) {
            try {
                ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", this.linkToCopy != null ? this.linkToCopy : this.exportedMessageLink.link));
                Toast.makeText(context, LocaleController.getString("LinkCopied", R.string.LinkCopied), 0).show();
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    public static ShareAlert createShareAlert(Context context, MessageObject messageObject, String str, boolean z, String str2, boolean z2) {
        ArrayList arrayList;
        if (messageObject != null) {
            arrayList = new ArrayList();
            arrayList.add(messageObject);
        } else {
            arrayList = null;
        }
        return new ShareAlert(context, arrayList, str, z, str2, z2);
    }

    private int getCurrentTop() {
        if (this.gridView.getChildCount() != 0) {
            View childAt = this.gridView.getChildAt(0);
            Holder holder = (Holder) this.gridView.findContainingViewHolder(childAt);
            if (holder != null) {
                int paddingTop = this.gridView.getPaddingTop();
                int top = (holder.getAdapterPosition() != 0 || childAt.getTop() < 0) ? 0 : childAt.getTop();
                return paddingTop - top;
            }
        }
        return C3446C.PRIORITY_DOWNLOAD;
    }

    private void setCheckColor() {
        this.quoteSwitch.setColor(ApplicationLoader.applicationContext.getSharedPreferences("theme", 0).getInt("chatAttachTextColor", -9079435));
    }

    private void showCommentTextView(final boolean z) {
        float f = BitmapDescriptorFactory.HUE_RED;
        boolean z2 = true;
        try {
            if (this.frameLayout2.getTag() == null) {
                z2 = false;
            }
            if (z != z2) {
                if (this.animatorSet != null) {
                    this.animatorSet.cancel();
                }
                this.frameLayout2.setTag(z ? Integer.valueOf(1) : null);
                AndroidUtilities.hideKeyboard(this.commentTextView);
                this.animatorSet = new AnimatorSet();
                AnimatorSet animatorSet = this.animatorSet;
                Animator[] animatorArr = new Animator[2];
                View view = this.shadow2;
                String str = "translationY";
                float[] fArr = new float[1];
                fArr[0] = (float) AndroidUtilities.dp(z ? BitmapDescriptorFactory.HUE_RED : 53.0f);
                animatorArr[0] = ObjectAnimator.ofFloat(view, str, fArr);
                FrameLayout frameLayout = this.frameLayout2;
                String str2 = "translationY";
                float[] fArr2 = new float[1];
                if (!z) {
                    f = 53.0f;
                }
                fArr2[0] = (float) AndroidUtilities.dp(f);
                animatorArr[1] = ObjectAnimator.ofFloat(frameLayout, str2, fArr2);
                animatorSet.playTogether(animatorArr);
                this.animatorSet.setInterpolator(new DecelerateInterpolator());
                this.animatorSet.setDuration(180);
                this.animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationCancel(Animator animator) {
                        if (animator.equals(ShareAlert.this.animatorSet)) {
                            ShareAlert.this.animatorSet = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (animator.equals(ShareAlert.this.animatorSet)) {
                            ShareAlert.this.gridView.setPadding(0, 0, 0, AndroidUtilities.dp(z ? 56.0f : 8.0f));
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

    @SuppressLint({"NewApi"})
    private void updateLayout() {
        if (this.gridView.getChildCount() > 0) {
            View childAt = this.gridView.getChildAt(0);
            Holder holder = (Holder) this.gridView.findContainingViewHolder(childAt);
            int top = childAt.getTop() - AndroidUtilities.dp(8.0f);
            int i = (top <= 0 || holder == null || holder.getAdapterPosition() != 0) ? 0 : top;
            if (this.scrollOffsetY != i) {
                RecyclerListView recyclerListView = this.gridView;
                this.scrollOffsetY = i;
                recyclerListView.setTopGlowOffset(i);
                this.frameLayout.setTranslationY((float) this.scrollOffsetY);
                this.shadow.setTranslationY((float) this.scrollOffsetY);
                this.searchEmptyView.setTranslationY((float) this.scrollOffsetY);
                this.containerView.invalidate();
            }
        }
    }

    protected boolean canDismissWithSwipe() {
        return false;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.dialogsNeedReload) {
            if (this.listAdapter != null) {
                this.listAdapter.fetchDialogs();
            }
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.dialogsNeedReload);
        }
    }

    public void dismiss() {
        super.dismiss();
        try {
            this.sendingMessageObject.restoreCaptionAndText();
        } catch (Exception e) {
        }
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.dialogsNeedReload);
    }

    public void setCheck(boolean z) {
        if (VERSION.SDK_INT < 11) {
            this.quoteSwitch.resetLayout();
            this.quoteSwitch.requestLayout();
        }
        this.quoteSwitch.setChecked(z);
        setCheckColor();
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
            this.doneButtonTextView.setText(LocaleController.getString("Send", R.string.Send).toUpperCase());
            return;
        }
        showCommentTextView(true);
        this.doneButtonTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        this.doneButtonBadgeTextView.setVisibility(0);
        this.doneButtonBadgeTextView.setText(String.format("%d", new Object[]{Integer.valueOf(this.selectedDialogs.size())}));
        this.doneButtonTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlue3));
        this.doneButton.setEnabled(true);
        this.doneButtonTextView.setText(LocaleController.getString("Send", R.string.Send).toUpperCase());
    }
}
