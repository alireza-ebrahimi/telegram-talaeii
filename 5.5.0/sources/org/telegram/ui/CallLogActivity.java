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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterPhoneCalls;
import org.telegram.tgnet.TLRPC$TL_inputPeerEmpty;
import org.telegram.tgnet.TLRPC$TL_messageActionHistoryClear;
import org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall;
import org.telegram.tgnet.TLRPC$TL_messages_search;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonBusy;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonMissed;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.PhoneCallDiscardReason;
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
    private OnClickListener callBtnClickListener = new C39661();
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
    class C39661 implements OnClickListener {
        C39661() {
        }

        public void onClick(View view) {
            VoIPHelper.startCall(CallLogActivity.this.lastCallUser = ((CallLogRow) view.getTag()).user, CallLogActivity.this.getParentActivity(), null);
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$2 */
    class C39672 extends ActionBarMenuOnItemClick {
        C39672() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                CallLogActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$3 */
    class C39683 implements OnItemClickListener {
        C39683() {
        }

        public void onItemClick(View view, int i) {
            if (i >= 0 && i < CallLogActivity.this.calls.size()) {
                CallLogRow callLogRow = (CallLogRow) CallLogActivity.this.calls.get(i);
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", callLogRow.user.id);
                bundle.putInt("message_id", ((Message) callLogRow.calls.get(0)).id);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                CallLogActivity.this.presentFragment(new ChatActivity(bundle), true);
            }
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$4 */
    class C39704 implements OnItemLongClickListener {
        C39704() {
        }

        public boolean onItemClick(View view, int i) {
            if (i < 0 || i >= CallLogActivity.this.calls.size()) {
                return false;
            }
            final CallLogRow callLogRow = (CallLogRow) CallLogActivity.this.calls.get(i);
            ArrayList arrayList = new ArrayList();
            arrayList.add(LocaleController.getString("Delete", R.string.Delete));
            if (VoIPHelper.canRateCall((TLRPC$TL_messageActionPhoneCall) ((Message) callLogRow.calls.get(0)).action)) {
                arrayList.add(LocaleController.getString("CallMessageReportProblem", R.string.CallMessageReportProblem));
            }
            new Builder(CallLogActivity.this.getParentActivity()).setTitle(LocaleController.getString("Calls", R.string.Calls)).setItems((CharSequence[]) arrayList.toArray(new String[arrayList.size()]), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0:
                            CallLogActivity.this.confirmAndDelete(callLogRow);
                            return;
                        case 1:
                            VoIPHelper.showRateAlert(CallLogActivity.this.getParentActivity(), (TLRPC$TL_messageActionPhoneCall) ((Message) callLogRow.calls.get(0)).action);
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
    class C39715 extends OnScrollListener {
        C39715() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            boolean z = false;
            int findFirstVisibleItemPosition = CallLogActivity.this.layoutManager.findFirstVisibleItemPosition();
            int abs = findFirstVisibleItemPosition == -1 ? 0 : Math.abs(CallLogActivity.this.layoutManager.findLastVisibleItemPosition() - findFirstVisibleItemPosition) + 1;
            if (abs > 0) {
                int itemCount = CallLogActivity.this.listViewAdapter.getItemCount();
                if (!(CallLogActivity.this.endReached || CallLogActivity.this.loading || CallLogActivity.this.calls.isEmpty() || abs + findFirstVisibleItemPosition < itemCount - 5)) {
                    CallLogRow callLogRow = (CallLogRow) CallLogActivity.this.calls.get(CallLogActivity.this.calls.size() - 1);
                    CallLogActivity.this.getCalls(((Message) callLogRow.calls.get(callLogRow.calls.size() - 1)).id, 100);
                }
            }
            if (CallLogActivity.this.floatingButton.getVisibility() != 8) {
                boolean z2;
                int i3;
                View childAt = recyclerView.getChildAt(0);
                abs = childAt != null ? childAt.getTop() : 0;
                if (CallLogActivity.this.prevPosition == findFirstVisibleItemPosition) {
                    int access$1100 = CallLogActivity.this.prevTop - abs;
                    z2 = abs < CallLogActivity.this.prevTop;
                    if (Math.abs(access$1100) > 1) {
                        i3 = 1;
                    }
                } else {
                    if (findFirstVisibleItemPosition > CallLogActivity.this.prevPosition) {
                        z = true;
                    }
                    z2 = z;
                    i3 = 1;
                }
                if (i3 != 0 && CallLogActivity.this.scrollUpdated) {
                    CallLogActivity.this.hideFloatingButton(z2);
                }
                CallLogActivity.this.prevPosition = findFirstVisibleItemPosition;
                CallLogActivity.this.prevTop = abs;
                CallLogActivity.this.scrollUpdated = true;
            }
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$6 */
    class C39726 extends ViewOutlineProvider {
        C39726() {
        }

        @SuppressLint({"NewApi"})
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$7 */
    class C39747 implements OnClickListener {

        /* renamed from: org.telegram.ui.CallLogActivity$7$1 */
        class C39731 implements ContactsActivityDelegate {
            C39731() {
            }

            public void didSelectContact(User user, String str, ContactsActivity contactsActivity) {
                VoIPHelper.startCall(user, CallLogActivity.this.getParentActivity(), null);
            }
        }

        C39747() {
        }

        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("destroyAfterSelect", true);
            bundle.putBoolean("returnAsResult", true);
            bundle.putBoolean("onlyUsers", true);
            BaseFragment contactsActivity = new ContactsActivity(bundle);
            contactsActivity.setDelegate(new C39731());
            CallLogActivity.this.presentFragment(contactsActivity);
        }
    }

    /* renamed from: org.telegram.ui.CallLogActivity$8 */
    class C39768 implements RequestDelegate {
        C39768() {
        }

        public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (tLRPC$TL_error == null) {
                        SparseArray sparseArray = new SparseArray();
                        TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                        CallLogActivity.this.endReached = tLRPC$messages_Messages.messages.isEmpty();
                        for (int i = 0; i < tLRPC$messages_Messages.users.size(); i++) {
                            User user = (User) tLRPC$messages_Messages.users.get(i);
                            sparseArray.put(user.id, user);
                        }
                        CallLogRow callLogRow = CallLogActivity.this.calls.size() > 0 ? (CallLogRow) CallLogActivity.this.calls.get(CallLogActivity.this.calls.size() - 1) : null;
                        for (int i2 = 0; i2 < tLRPC$messages_Messages.messages.size(); i2++) {
                            Message message = (Message) tLRPC$messages_Messages.messages.get(i2);
                            if (!(message.action == null || (message.action instanceof TLRPC$TL_messageActionHistoryClear))) {
                                int i3 = message.from_id == UserConfig.getClientUserId() ? 0 : 1;
                                PhoneCallDiscardReason phoneCallDiscardReason = message.action.reason;
                                if (i3 == 1 && ((phoneCallDiscardReason instanceof TLRPC$TL_phoneCallDiscardReasonMissed) || (phoneCallDiscardReason instanceof TLRPC$TL_phoneCallDiscardReasonBusy))) {
                                    i3 = 2;
                                }
                                int i4 = message.from_id == UserConfig.getClientUserId() ? message.to_id.user_id : message.from_id;
                                if (!(callLogRow != null && callLogRow.user.id == i4 && callLogRow.type == i3)) {
                                    if (!(callLogRow == null || CallLogActivity.this.calls.contains(callLogRow))) {
                                        CallLogActivity.this.calls.add(callLogRow);
                                    }
                                    CallLogRow callLogRow2 = new CallLogRow();
                                    callLogRow2.calls = new ArrayList();
                                    callLogRow2.user = (User) sparseArray.get(i4);
                                    callLogRow2.type = i3;
                                    callLogRow = callLogRow2;
                                }
                                callLogRow.calls.add(message);
                            }
                        }
                        if (!(callLogRow == null || callLogRow.calls.size() <= 0 || CallLogActivity.this.calls.contains(callLogRow))) {
                            CallLogActivity.this.calls.add(callLogRow);
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
        public List<Message> calls;
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

        public int getItemCount() {
            int size = CallLogActivity.this.calls.size();
            return (CallLogActivity.this.calls.isEmpty() || CallLogActivity.this.endReached) ? size : size + 1;
        }

        public int getItemViewType(int i) {
            return i < CallLogActivity.this.calls.size() ? 0 : (CallLogActivity.this.endReached || i != CallLogActivity.this.calls.size()) ? 2 : 1;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getAdapterPosition() != CallLogActivity.this.calls.size();
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() == 0) {
                ViewItem viewItem = (ViewItem) viewHolder.itemView.getTag();
                ProfileSearchCell profileSearchCell = viewItem.cell;
                CallLogRow callLogRow = (CallLogRow) CallLogActivity.this.calls.get(i);
                Message message = (Message) callLogRow.calls.get(0);
                String str = LocaleController.isRTL ? "‫" : TtmlNode.ANONYMOUS_REGION_ID;
                CharSequence spannableString = callLogRow.calls.size() == 1 ? new SpannableString(str + "  " + LocaleController.formatDateCallLog((long) message.date)) : new SpannableString(String.format(str + "  (%d) %s", new Object[]{Integer.valueOf(callLogRow.calls.size()), LocaleController.formatDateCallLog((long) message.date)}));
                switch (callLogRow.type) {
                    case 0:
                        spannableString.setSpan(CallLogActivity.this.iconOut, str.length(), str.length() + 1, 0);
                        break;
                    case 1:
                        spannableString.setSpan(CallLogActivity.this.iconIn, str.length(), str.length() + 1, 0);
                        break;
                    case 2:
                        spannableString.setSpan(CallLogActivity.this.iconMissed, str.length(), str.length() + 1, 0);
                        break;
                }
                profileSearchCell.setData(callLogRow.user, null, null, spannableString, false, false);
                boolean z = (i == CallLogActivity.this.calls.size() + -1 && CallLogActivity.this.endReached) ? false : true;
                profileSearchCell.useSeparator = z;
                viewItem.button.setTag(callLogRow);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            switch (i) {
                case 0:
                    View customCell = new CustomCell(this.mContext);
                    customCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    View profileSearchCell = new ProfileSearchCell(this.mContext);
                    profileSearchCell.setPaddingRight(AndroidUtilities.dp(32.0f));
                    customCell.addView(profileSearchCell);
                    View imageView = new ImageView(this.mContext);
                    imageView.setImageResource(R.drawable.profile_phone);
                    imageView.setAlpha(214);
                    imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayIcon), Mode.MULTIPLY));
                    imageView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
                    imageView.setScaleType(ScaleType.CENTER);
                    imageView.setOnClickListener(CallLogActivity.this.callBtnClickListener);
                    customCell.addView(imageView, LayoutHelper.createFrame(48, 48.0f, (LocaleController.isRTL ? 3 : 5) | 16, 8.0f, BitmapDescriptorFactory.HUE_RED, 8.0f, BitmapDescriptorFactory.HUE_RED));
                    customCell.setTag(new ViewItem(imageView, profileSearchCell));
                    view = customCell;
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
    }

    private class ViewItem {
        public ImageView button;
        public ProfileSearchCell cell;

        public ViewItem(ImageView imageView, ProfileSearchCell profileSearchCell) {
            this.button = imageView;
            this.cell = profileSearchCell;
        }
    }

    private void confirmAndDelete(final CallLogRow callLogRow) {
        if (getParentActivity() != null) {
            new Builder(getParentActivity()).setTitle(LocaleController.getString("AppName", R.string.AppName)).setMessage(LocaleController.getString("ConfirmDeleteCallLog", R.string.ConfirmDeleteCallLog)).setPositiveButton(LocaleController.getString("Delete", R.string.Delete), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ArrayList arrayList = new ArrayList();
                    for (Message message : callLogRow.calls) {
                        arrayList.add(Integer.valueOf(message.id));
                    }
                    MessagesController.getInstance().deleteMessages(arrayList, null, null, 0, false);
                }
            }).setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null).show().setCanceledOnTouchOutside(true);
        }
    }

    private void getCalls(int i, int i2) {
        if (!this.loading) {
            this.loading = true;
            if (!(this.emptyView == null || this.firstLoaded)) {
                this.emptyView.showProgress();
            }
            if (this.listViewAdapter != null) {
                this.listViewAdapter.notifyDataSetChanged();
            }
            TLObject tLRPC$TL_messages_search = new TLRPC$TL_messages_search();
            tLRPC$TL_messages_search.limit = i2;
            tLRPC$TL_messages_search.peer = new TLRPC$TL_inputPeerEmpty();
            tLRPC$TL_messages_search.filter = new TLRPC$TL_inputMessagesFilterPhoneCalls();
            tLRPC$TL_messages_search.f10166q = TtmlNode.ANONYMOUS_REGION_ID;
            tLRPC$TL_messages_search.offset_id = i;
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_search, new C39768(), 2), this.classGuid);
        }
    }

    private void hideFloatingButton(boolean z) {
        if (this.floatingHidden != z) {
            this.floatingHidden = z;
            ImageView imageView = this.floatingButton;
            String str = "translationY";
            float[] fArr = new float[1];
            fArr[0] = this.floatingHidden ? (float) AndroidUtilities.dp(100.0f) : BitmapDescriptorFactory.HUE_RED;
            ObjectAnimator duration = ObjectAnimator.ofFloat(imageView, str, fArr).setDuration(300);
            duration.setInterpolator(this.floatingInterpolator);
            this.floatingButton.setClickable(!z);
            duration.start();
        }
    }

    public View createView(Context context) {
        Drawable combinedDrawable;
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
        this.actionBar.setActionBarMenuOnItemClick(new C39672());
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
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
        this.listView.setOnItemClickListener(new C39683());
        this.listView.setOnItemLongClickListener(new C39704());
        this.listView.setOnScrollListener(new C39715());
        if (this.loading) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
        this.floatingButton = new ImageView(context);
        this.floatingButton.setVisibility(0);
        this.floatingButton.setScaleType(ScaleType.CENTER);
        Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_chats_actionBackground), Theme.getColor(Theme.key_chats_actionPressedBackground));
        if (VERSION.SDK_INT < 21) {
            Drawable mutate = context.getResources().getDrawable(R.drawable.floating_shadow).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, Mode.MULTIPLY));
            combinedDrawable = new CombinedDrawable(mutate, createSimpleSelectorCircleDrawable, 0, 0);
            combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        } else {
            combinedDrawable = createSimpleSelectorCircleDrawable;
        }
        this.floatingButton.setBackgroundDrawable(combinedDrawable);
        this.floatingButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), Mode.MULTIPLY));
        this.floatingButton.setImageResource(R.drawable.ic_call_white_24dp);
        if (VERSION.SDK_INT >= 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.floatingButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
            stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this.floatingButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
            this.floatingButton.setStateListAnimator(stateListAnimator);
            this.floatingButton.setOutlineProvider(new C39726());
        }
        frameLayout.addView(this.floatingButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, (LocaleController.isRTL ? 3 : 5) | 80, LocaleController.isRTL ? 14.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 14.0f, 14.0f));
        this.floatingButton.setOnClickListener(new C39747());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int i2 = 0;
        Iterator it;
        CallLogRow callLogRow;
        if (i == NotificationCenter.didReceivedNewMessages && this.firstLoaded) {
            it = ((ArrayList) objArr[1]).iterator();
            while (it.hasNext()) {
                MessageObject messageObject = (MessageObject) it.next();
                if (messageObject.messageOwner.action != null && (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPhoneCall)) {
                    int i3 = messageObject.messageOwner.from_id == UserConfig.getClientUserId() ? messageObject.messageOwner.to_id.user_id : messageObject.messageOwner.from_id;
                    int i4 = messageObject.messageOwner.from_id == UserConfig.getClientUserId() ? 0 : 1;
                    PhoneCallDiscardReason phoneCallDiscardReason = messageObject.messageOwner.action.reason;
                    int i5 = (i4 == 1 && ((phoneCallDiscardReason instanceof TLRPC$TL_phoneCallDiscardReasonMissed) || (phoneCallDiscardReason instanceof TLRPC$TL_phoneCallDiscardReasonBusy))) ? 2 : i4;
                    if (this.calls.size() > 0) {
                        callLogRow = (CallLogRow) this.calls.get(0);
                        if (callLogRow.user.id == i3 && callLogRow.type == i5) {
                            callLogRow.calls.add(0, messageObject.messageOwner);
                            this.listViewAdapter.notifyItemChanged(0);
                        }
                    }
                    callLogRow = new CallLogRow();
                    callLogRow.calls = new ArrayList();
                    callLogRow.calls.add(messageObject.messageOwner);
                    callLogRow.user = MessagesController.getInstance().getUser(Integer.valueOf(i3));
                    callLogRow.type = i5;
                    this.calls.add(0, callLogRow);
                    this.listViewAdapter.notifyItemInserted(0);
                }
            }
        } else if (i == NotificationCenter.messagesDeleted && this.firstLoaded) {
            ArrayList arrayList = (ArrayList) objArr[0];
            Iterator it2 = this.calls.iterator();
            while (it2.hasNext()) {
                callLogRow = (CallLogRow) it2.next();
                it = callLogRow.calls.iterator();
                while (it.hasNext()) {
                    if (arrayList.contains(Integer.valueOf(((Message) it.next()).id))) {
                        it.remove();
                        i2 = 1;
                    }
                }
                if (callLogRow.calls.size() == 0) {
                    it2.remove();
                }
            }
            if (i2 != 0 && this.listViewAdapter != null) {
                this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        AnonymousClass10 anonymousClass10 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
                int childCount = CallLogActivity.this.listView.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = CallLogActivity.this.listView.getChildAt(i2);
                    if (childAt instanceof ProfileSearchCell) {
                        ((ProfileSearchCell) childAt).update(0);
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
        themeDescriptionArr[23] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[24] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[25] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[26] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[27] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[28] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[29] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundPink);
        themeDescriptionArr[30] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, null, new Drawable[]{this.greenDrawable, this.greenDrawable2, Theme.chat_msgCallUpRedDrawable, Theme.chat_msgCallDownRedDrawable}, null, Theme.key_calls_callReceivedGreenIcon);
        themeDescriptionArr[31] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, null, new Drawable[]{this.redDrawable, Theme.chat_msgCallUpGreenDrawable, Theme.chat_msgCallDownGreenDrawable}, null, Theme.key_calls_callReceivedRedIcon);
        return themeDescriptionArr;
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

    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (i != 101) {
            return;
        }
        if (iArr.length <= 0 || iArr[0] != 0) {
            VoIPHelper.permissionDenied(getParentActivity(), null);
        } else {
            VoIPHelper.startCall(this.lastCallUser, getParentActivity(), null);
        }
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }
}
