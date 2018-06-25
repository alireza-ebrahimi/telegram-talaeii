package org.telegram.ui;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Outline;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$PhoneCallDiscardReason;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterPhoneCalls;
import org.telegram.tgnet.TLRPC$TL_inputPeerEmpty;
import org.telegram.tgnet.TLRPC$TL_messageActionHistoryClear;
import org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall;
import org.telegram.tgnet.TLRPC$TL_messages_search;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonBusy;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonMissed;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Cells.LoadingCell;
import org.telegram.ui.Cells.LocationCell;
import org.telegram.ui.Cells.ProfileSearchCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.voip.VoIPHelper;
import org.telegram.ui.ContactsActivity.ContactsActivityDelegate;

public class CallLogActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int TYPE_IN = 1;
    private static final int TYPE_MISSED = 2;
    private static final int TYPE_OUT = 0;
    private OnClickListener callBtnClickListener = new C21281();
    private ArrayList<CallLogRow> calls = new ArrayList();
    private EmptyTextProgressView emptyView;
    private boolean endReached;
    private boolean firstLoaded;
    private ImageView floatingButton;
    private boolean floatingHidden;
    private final AccelerateDecelerateInterpolator floatingInterpolator = new AccelerateDecelerateInterpolator();
    private Drawable greenDrawable;
    private Drawable greenDrawable2;
    private ImageSpan iconIn;
    private ImageSpan iconMissed;
    private ImageSpan iconOut;
    private User lastCallUser;
    private LinearLayoutManager layoutManager;
    private RecyclerListView listView;
    private ListAdapter listViewAdapter;
    private boolean loading;
    private int prevPosition;
    private int prevTop;
    private Drawable redDrawable;
    private boolean scrollUpdated;

    /* renamed from: org.telegram.ui.CallLogActivity$1 */
    class C21281 implements OnClickListener {
        C21281() {
        }

        public void onClick(View v) {
            VoIPHelper.startCall(CallLogActivity.this.lastCallUser = ((CallLogRow) v.getTag()).user, CallLogActivity.this.getParentActivity(), null);
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$2 */
    class C21292 extends ActionBarMenuOnItemClick {
        C21292() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                CallLogActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$3 */
    class C21303 implements OnItemClickListener {
        C21303() {
        }

        public void onItemClick(View view, int position) {
            if (position >= 0 && position < CallLogActivity.this.calls.size()) {
                CallLogRow row = (CallLogRow) CallLogActivity.this.calls.get(position);
                Bundle args = new Bundle();
                args.putInt("user_id", row.user.id);
                args.putInt("message_id", ((TLRPC$Message) row.calls.get(0)).id);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                CallLogActivity.this.presentFragment(new ChatActivity(args), true);
            }
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$4 */
    class C21324 implements OnItemLongClickListener {
        C21324() {
        }

        public boolean onItemClick(View view, int position) {
            if (position < 0 || position >= CallLogActivity.this.calls.size()) {
                return false;
            }
            final CallLogRow row = (CallLogRow) CallLogActivity.this.calls.get(position);
            ArrayList<String> items = new ArrayList();
            items.add(LocaleController.getString("Delete", R.string.Delete));
            if (VoIPHelper.canRateCall((TLRPC$TL_messageActionPhoneCall) ((TLRPC$Message) row.calls.get(0)).action)) {
                items.add(LocaleController.getString("CallMessageReportProblem", R.string.CallMessageReportProblem));
            }
            new Builder(CallLogActivity.this.getParentActivity()).setTitle(LocaleController.getString("Calls", R.string.Calls)).setItems((CharSequence[]) items.toArray(new String[items.size()]), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            CallLogActivity.this.confirmAndDelete(row);
                            return;
                        case 1:
                            VoIPHelper.showRateAlert(CallLogActivity.this.getParentActivity(), (TLRPC$TL_messageActionPhoneCall) ((TLRPC$Message) row.calls.get(0)).action);
                            return;
                        default:
                            return;
                    }
                }
            }).show();
            return true;
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$5 */
    class C21335 extends OnScrollListener {
        C21335() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int visibleItemCount;
            int firstVisibleItem = CallLogActivity.this.layoutManager.findFirstVisibleItemPosition();
            if (firstVisibleItem == -1) {
                visibleItemCount = 0;
            } else {
                visibleItemCount = Math.abs(CallLogActivity.this.layoutManager.findLastVisibleItemPosition() - firstVisibleItem) + 1;
            }
            if (visibleItemCount > 0) {
                int totalItemCount = CallLogActivity.this.listViewAdapter.getItemCount();
                if (!(CallLogActivity.this.endReached || CallLogActivity.this.loading || CallLogActivity.this.calls.isEmpty() || firstVisibleItem + visibleItemCount < totalItemCount - 5)) {
                    CallLogRow row = (CallLogRow) CallLogActivity.this.calls.get(CallLogActivity.this.calls.size() - 1);
                    CallLogActivity.this.getCalls(((TLRPC$Message) row.calls.get(row.calls.size() - 1)).id, 100);
                }
            }
            if (CallLogActivity.this.floatingButton.getVisibility() != 8) {
                boolean goingDown;
                View topChild = recyclerView.getChildAt(0);
                int firstViewTop = 0;
                if (topChild != null) {
                    firstViewTop = topChild.getTop();
                }
                boolean changed = true;
                if (CallLogActivity.this.prevPosition == firstVisibleItem) {
                    int topDelta = CallLogActivity.this.prevTop - firstViewTop;
                    goingDown = firstViewTop < CallLogActivity.this.prevTop;
                    changed = Math.abs(topDelta) > 1;
                } else {
                    goingDown = firstVisibleItem > CallLogActivity.this.prevPosition;
                }
                if (changed && CallLogActivity.this.scrollUpdated) {
                    CallLogActivity.this.hideFloatingButton(goingDown);
                }
                CallLogActivity.this.prevPosition = firstVisibleItem;
                CallLogActivity.this.prevTop = firstViewTop;
                CallLogActivity.this.scrollUpdated = true;
            }
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$6 */
    class C21346 extends ViewOutlineProvider {
        C21346() {
        }

        @SuppressLint({"NewApi"})
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$7 */
    class C21367 implements OnClickListener {

        /* renamed from: org.telegram.ui.CallLogActivity$7$1 */
        class C21351 implements ContactsActivityDelegate {
            C21351() {
            }

            public void didSelectContact(User user, String param, ContactsActivity activity) {
                VoIPHelper.startCall(user, CallLogActivity.this.getParentActivity(), null);
            }
        }

        C21367() {
        }

        public void onClick(View v) {
            Bundle args = new Bundle();
            args.putBoolean("destroyAfterSelect", true);
            args.putBoolean("returnAsResult", true);
            args.putBoolean("onlyUsers", true);
            ContactsActivity contactsFragment = new ContactsActivity(args);
            contactsFragment.setDelegate(new C21351());
            CallLogActivity.this.presentFragment(contactsFragment);
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$8 */
    class C21388 implements RequestDelegate {
        C21388() {
        }

        public void run(final TLObject response, final TLRPC$TL_error error) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (error == null) {
                        int a;
                        CallLogRow currentRow;
                        SparseArray<User> users = new SparseArray();
                        TLRPC$messages_Messages msgs = response;
                        CallLogActivity.this.endReached = msgs.messages.isEmpty();
                        for (a = 0; a < msgs.users.size(); a++) {
                            User user = (User) msgs.users.get(a);
                            users.put(user.id, user);
                        }
                        if (CallLogActivity.this.calls.size() > 0) {
                            currentRow = (CallLogRow) CallLogActivity.this.calls.get(CallLogActivity.this.calls.size() - 1);
                        } else {
                            currentRow = null;
                        }
                        for (a = 0; a < msgs.messages.size(); a++) {
                            TLRPC$Message msg = (TLRPC$Message) msgs.messages.get(a);
                            if (!(msg.action == null || (msg.action instanceof TLRPC$TL_messageActionHistoryClear))) {
                                int callType;
                                if (msg.from_id == UserConfig.getClientUserId()) {
                                    callType = 0;
                                } else {
                                    callType = 1;
                                }
                                TLRPC$PhoneCallDiscardReason reason = msg.action.reason;
                                if (callType == 1 && ((reason instanceof TLRPC$TL_phoneCallDiscardReasonMissed) || (reason instanceof TLRPC$TL_phoneCallDiscardReasonBusy))) {
                                    callType = 2;
                                }
                                int userID = msg.from_id == UserConfig.getClientUserId() ? msg.to_id.user_id : msg.from_id;
                                if (!(currentRow != null && currentRow.user.id == userID && currentRow.type == callType)) {
                                    if (!(currentRow == null || CallLogActivity.this.calls.contains(currentRow))) {
                                        CallLogActivity.this.calls.add(currentRow);
                                    }
                                    CallLogRow row = new CallLogRow();
                                    row.calls = new ArrayList();
                                    row.user = (User) users.get(userID);
                                    row.type = callType;
                                    currentRow = row;
                                }
                                currentRow.calls.add(msg);
                            }
                        }
                        if (!(currentRow == null || currentRow.calls.size() <= 0 || CallLogActivity.this.calls.contains(currentRow))) {
                            CallLogActivity.this.calls.add(currentRow);
                        }
                    } else {
                        CallLogActivity.this.endReached = true;
                    }
                    CallLogActivity.this.loading = false;
                    CallLogActivity.this.firstLoaded = true;
                    if (CallLogActivity.this.emptyView != null) {
                        CallLogActivity.this.emptyView.showTextView();
                    }
                    if (CallLogActivity.this.listViewAdapter != null) {
                        CallLogActivity.this.listViewAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private class CallLogRow {
        public List<TLRPC$Message> calls;
        public int type;
        public User user;

        private CallLogRow() {
        }
    }

    private class CustomCell extends FrameLayout {
        public CustomCell(Context context) {
            super(context);
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public boolean isEnabled(ViewHolder holder) {
            return holder.getAdapterPosition() != CallLogActivity.this.calls.size();
        }

        public int getItemCount() {
            int count = CallLogActivity.this.calls.size();
            if (CallLogActivity.this.calls.isEmpty() || CallLogActivity.this.endReached) {
                return count;
            }
            return count + 1;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    View frameLayout = new CustomCell(this.mContext);
                    frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ProfileSearchCell cell = new ProfileSearchCell(this.mContext);
                    cell.setPaddingRight(AndroidUtilities.dp(32.0f));
                    frameLayout.addView(cell);
                    ImageView imageView = new ImageView(this.mContext);
                    imageView.setImageResource(R.drawable.profile_phone);
                    imageView.setAlpha(214);
                    imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayIcon), Mode.MULTIPLY));
                    imageView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
                    imageView.setScaleType(ScaleType.CENTER);
                    imageView.setOnClickListener(CallLogActivity.this.callBtnClickListener);
                    frameLayout.addView(imageView, LayoutHelper.createFrame(48, 48.0f, (LocaleController.isRTL ? 3 : 5) | 16, 8.0f, 0.0f, 8.0f, 0.0f));
                    view = frameLayout;
                    view.setTag(new ViewItem(imageView, cell));
                    break;
                case 1:
                    view = new LoadingCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    view = new TextInfoPrivacyCell(this.mContext);
                    view.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder.getItemViewType() == 0) {
                SpannableString subtitle;
                ViewItem viewItem = (ViewItem) holder.itemView.getTag();
                ProfileSearchCell cell = viewItem.cell;
                CallLogRow row = (CallLogRow) CallLogActivity.this.calls.get(position);
                TLRPC$Message last = (TLRPC$Message) row.calls.get(0);
                String ldir = LocaleController.isRTL ? "‫" : "";
                if (row.calls.size() == 1) {
                    subtitle = new SpannableString(ldir + "  " + LocaleController.formatDateCallLog((long) last.date));
                } else {
                    subtitle = new SpannableString(String.format(ldir + "  (%d) %s", new Object[]{Integer.valueOf(row.calls.size()), LocaleController.formatDateCallLog((long) last.date)}));
                }
                switch (row.type) {
                    case 0:
                        subtitle.setSpan(CallLogActivity.this.iconOut, ldir.length(), ldir.length() + 1, 0);
                        break;
                    case 1:
                        subtitle.setSpan(CallLogActivity.this.iconIn, ldir.length(), ldir.length() + 1, 0);
                        break;
                    case 2:
                        subtitle.setSpan(CallLogActivity.this.iconMissed, ldir.length(), ldir.length() + 1, 0);
                        break;
                }
                cell.setData(row.user, null, null, subtitle, false, false);
                boolean z = (position == CallLogActivity.this.calls.size() + -1 && CallLogActivity.this.endReached) ? false : true;
                cell.useSeparator = z;
                viewItem.button.setTag(row);
            }
        }

        public int getItemViewType(int i) {
            if (i < CallLogActivity.this.calls.size()) {
                return 0;
            }
            if (CallLogActivity.this.endReached || i != CallLogActivity.this.calls.size()) {
                return 2;
            }
            return 1;
        }
    }

    private class ViewItem {
        public ImageView button;
        public ProfileSearchCell cell;

        public ViewItem(ImageView button, ProfileSearchCell cell) {
            this.button = button;
            this.cell = cell;
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        CallLogRow row;
        if (id == NotificationCenter.didReceivedNewMessages && this.firstLoaded) {
            Iterator it = args[1].iterator();
            while (it.hasNext()) {
                MessageObject msg = (MessageObject) it.next();
                if (msg.messageOwner.action != null && (msg.messageOwner.action instanceof TLRPC$TL_messageActionPhoneCall)) {
                    int userID = msg.messageOwner.from_id == UserConfig.getClientUserId() ? msg.messageOwner.to_id.user_id : msg.messageOwner.from_id;
                    int callType = msg.messageOwner.from_id == UserConfig.getClientUserId() ? 0 : 1;
                    TLRPC$PhoneCallDiscardReason reason = msg.messageOwner.action.reason;
                    if (callType == 1 && ((reason instanceof TLRPC$TL_phoneCallDiscardReasonMissed) || (reason instanceof TLRPC$TL_phoneCallDiscardReasonBusy))) {
                        callType = 2;
                    }
                    if (this.calls.size() > 0) {
                        CallLogRow topRow = (CallLogRow) this.calls.get(0);
                        if (topRow.user.id == userID && topRow.type == callType) {
                            topRow.calls.add(0, msg.messageOwner);
                            this.listViewAdapter.notifyItemChanged(0);
                        }
                    }
                    CallLogActivity callLogActivity = this;
                    row = new CallLogRow();
                    row.calls = new ArrayList();
                    row.calls.add(msg.messageOwner);
                    row.user = MessagesController.getInstance().getUser(Integer.valueOf(userID));
                    row.type = callType;
                    this.calls.add(0, row);
                    this.listViewAdapter.notifyItemInserted(0);
                }
            }
        } else if (id == NotificationCenter.messagesDeleted && this.firstLoaded) {
            boolean didChange = false;
            ArrayList<Integer> ids = args[0];
            Iterator<CallLogRow> itrtr = this.calls.iterator();
            while (itrtr.hasNext()) {
                row = (CallLogRow) itrtr.next();
                Iterator<TLRPC$Message> msgs = row.calls.iterator();
                while (msgs.hasNext()) {
                    if (ids.contains(Integer.valueOf(((TLRPC$Message) msgs.next()).id))) {
                        didChange = true;
                        msgs.remove();
                    }
                }
                if (row.calls.size() == 0) {
                    itrtr.remove();
                }
            }
            if (didChange && this.listViewAdapter != null) {
                this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        getCalls(0, 50);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceivedNewMessages);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDeleted);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceivedNewMessages);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDeleted);
    }

    public View createView(Context context) {
        this.greenDrawable = getParentActivity().getResources().getDrawable(R.drawable.ic_call_made_green_18dp).mutate();
        this.greenDrawable.setBounds(0, 0, this.greenDrawable.getIntrinsicWidth(), this.greenDrawable.getIntrinsicHeight());
        this.greenDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_calls_callReceivedGreenIcon), Mode.MULTIPLY));
        this.iconOut = new ImageSpan(this.greenDrawable, 0);
        this.greenDrawable2 = getParentActivity().getResources().getDrawable(R.drawable.ic_call_received_green_18dp).mutate();
        this.greenDrawable2.setBounds(0, 0, this.greenDrawable2.getIntrinsicWidth(), this.greenDrawable2.getIntrinsicHeight());
        this.greenDrawable2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_calls_callReceivedGreenIcon), Mode.MULTIPLY));
        this.iconIn = new ImageSpan(this.greenDrawable2, 0);
        this.redDrawable = getParentActivity().getResources().getDrawable(R.drawable.ic_call_received_green_18dp).mutate();
        this.redDrawable.setBounds(0, 0, this.redDrawable.getIntrinsicWidth(), this.redDrawable.getIntrinsicHeight());
        this.redDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_calls_callReceivedRedIcon), Mode.MULTIPLY));
        this.iconMissed = new ImageSpan(this.redDrawable, 0);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("Calls", R.string.Calls));
        this.actionBar.setActionBarMenuOnItemClick(new C21292());
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = this.fragmentView;
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.setText(LocaleController.getString("NoCallLog", R.string.NoCallLog));
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setEmptyView(this.emptyView);
        RecyclerListView recyclerListView = this.listView;
        LayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false);
        this.layoutManager = linearLayoutManager;
        recyclerListView.setLayoutManager(linearLayoutManager);
        recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listViewAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setVerticalScrollbarPosition(LocaleController.isRTL ? 1 : 2);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemClickListener(new C21303());
        this.listView.setOnItemLongClickListener(new C21324());
        this.listView.setOnScrollListener(new C21335());
        if (this.loading) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
        this.floatingButton = new ImageView(context);
        this.floatingButton.setVisibility(0);
        this.floatingButton.setScaleType(ScaleType.CENTER);
        Drawable drawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_chats_actionBackground), Theme.getColor(Theme.key_chats_actionPressedBackground));
        if (VERSION.SDK_INT < 21) {
            Drawable shadowDrawable = context.getResources().getDrawable(R.drawable.floating_shadow).mutate();
            shadowDrawable.setColorFilter(new PorterDuffColorFilter(-16777216, Mode.MULTIPLY));
            Drawable combinedDrawable = new CombinedDrawable(shadowDrawable, drawable, 0, 0);
            combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
            drawable = combinedDrawable;
        }
        this.floatingButton.setBackgroundDrawable(drawable);
        this.floatingButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), Mode.MULTIPLY));
        this.floatingButton.setImageResource(R.drawable.ic_call_white_24dp);
        if (VERSION.SDK_INT >= 21) {
            StateListAnimator animator = new StateListAnimator();
            animator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.floatingButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
            animator.addState(new int[0], ObjectAnimator.ofFloat(this.floatingButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
            this.floatingButton.setStateListAnimator(animator);
            this.floatingButton.setOutlineProvider(new C21346());
        }
        frameLayout.addView(this.floatingButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, (LocaleController.isRTL ? 3 : 5) | 80, LocaleController.isRTL ? 14.0f : 0.0f, 0.0f, LocaleController.isRTL ? 0.0f : 14.0f, 14.0f));
        this.floatingButton.setOnClickListener(new C21367());
        return this.fragmentView;
    }

    private void hideFloatingButton(boolean hide) {
        if (this.floatingHidden != hide) {
            boolean z;
            this.floatingHidden = hide;
            ImageView imageView = this.floatingButton;
            String str = "translationY";
            float[] fArr = new float[1];
            fArr[0] = this.floatingHidden ? (float) AndroidUtilities.dp(100.0f) : 0.0f;
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, str, fArr).setDuration(300);
            animator.setInterpolator(this.floatingInterpolator);
            imageView = this.floatingButton;
            if (hide) {
                z = false;
            } else {
                z = true;
            }
            imageView.setClickable(z);
            animator.start();
        }
    }

    private void getCalls(int max_id, int count) {
        if (!this.loading) {
            this.loading = true;
            if (!(this.emptyView == null || this.firstLoaded)) {
                this.emptyView.showProgress();
            }
            if (this.listViewAdapter != null) {
                this.listViewAdapter.notifyDataSetChanged();
            }
            TLRPC$TL_messages_search req = new TLRPC$TL_messages_search();
            req.limit = count;
            req.peer = new TLRPC$TL_inputPeerEmpty();
            req.filter = new TLRPC$TL_inputMessagesFilterPhoneCalls();
            req.f87q = "";
            req.offset_id = max_id;
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(req, new C21388(), 2), this.classGuid);
        }
    }

    private void confirmAndDelete(final CallLogRow row) {
        if (getParentActivity() != null) {
            new Builder(getParentActivity()).setTitle(LocaleController.getString("AppName", R.string.AppName)).setMessage(LocaleController.getString("ConfirmDeleteCallLog", R.string.ConfirmDeleteCallLog)).setPositiveButton(LocaleController.getString("Delete", R.string.Delete), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<Integer> ids = new ArrayList();
                    for (TLRPC$Message msg : row.calls) {
                        ids.add(Integer.valueOf(msg.id));
                    }
                    MessagesController.getInstance().deleteMessages(ids, null, null, 0, false);
                }
            }).setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null).show().setCanceledOnTouchOutside(true);
        }
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != 101) {
            return;
        }
        if (grantResults.length <= 0 || grantResults[0] != 0) {
            VoIPHelper.permissionDenied(getParentActivity(), null);
        } else {
            VoIPHelper.startCall(this.lastCallUser, getParentActivity(), null);
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new ThemeDescriptionDelegate() {
            public void didSetColor(int color) {
                int count = CallLogActivity.this.listView.getChildCount();
                for (int a = 0; a < count; a++) {
                    View child = CallLogActivity.this.listView.getChildAt(a);
                    if (child instanceof ProfileSearchCell) {
                        ((ProfileSearchCell) child).update(0);
                    }
                }
            }
        };
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[32];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{LocationCell.class, CustomCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[9] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        themeDescriptionArr[10] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[11] = new ThemeDescription(this.listView, 0, new Class[]{LoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[14] = new ThemeDescription(this.floatingButton, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chats_actionIcon);
        themeDescriptionArr[15] = new ThemeDescription(this.floatingButton, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chats_actionBackground);
        themeDescriptionArr[16] = new ThemeDescription(this.floatingButton, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_chats_actionPressedBackground);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_verifiedCheckDrawable}, null, Theme.key_chats_verifiedCheck);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_verifiedDrawable}, null, Theme.key_chats_verifiedBackground);
        themeDescriptionArr[19] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_offlinePaint, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[20] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_onlinePaint, null, null, Theme.key_windowBackgroundWhiteBlueText3);
        themeDescriptionArr[21] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_namePaint, null, null, Theme.key_chats_name);
        themeDescriptionArr[22] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        themeDescriptionArr[23] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[24] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[25] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[26] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[27] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[28] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[29] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundPink);
        themeDescriptionArr[30] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, null, new Drawable[]{this.greenDrawable, this.greenDrawable2, Theme.chat_msgCallUpRedDrawable, Theme.chat_msgCallDownRedDrawable}, null, Theme.key_calls_callReceivedGreenIcon);
        themeDescriptionArr[31] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, null, new Drawable[]{this.redDrawable, Theme.chat_msgCallUpGreenDrawable, Theme.chat_msgCallDownGreenDrawable}, null, Theme.key_calls_callReceivedRedIcon);
        return themeDescriptionArr;
    }
}
