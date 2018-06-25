package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.persianswitch.sdk.base.log.LogCollector;
import java.util.ArrayList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$TL_channels_setStickers;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetEmpty;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetShortName;
import org.telegram.tgnet.TLRPC$TL_messages_getStickerSet;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.StickerSetCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.ContextProgressView;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.StickersAlert;
import org.telegram.ui.Components.URLSpanNoUnderline;

public class GroupStickersActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int done_button = 1;
    private int chatId;
    private ActionBarMenuItem doneItem;
    private AnimatorSet doneItemAnimation;
    private boolean donePressed;
    private EditText editText;
    private ImageView eraseImageView;
    private int headerRow;
    private boolean ignoreTextChanges;
    private TLRPC$ChatFull info;
    private int infoRow;
    private LinearLayoutManager layoutManager;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private LinearLayout nameContainer;
    private int nameRow;
    private ContextProgressView progressView;
    private Runnable queryRunnable;
    private int reqId;
    private int rowCount;
    private boolean searchWas;
    private boolean searching;
    private int selectedStickerRow;
    private TLRPC$TL_messages_stickerSet selectedStickerSet;
    private int stickersEndRow;
    private int stickersShadowRow;
    private int stickersStartRow;
    private EditTextBoldCursor usernameTextView;

    /* renamed from: org.telegram.ui.GroupStickersActivity$1 */
    class C29421 extends ActionBarMenuOnItemClick {
        C29421() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                GroupStickersActivity.this.finishFragment();
            } else if (id == 1 && !GroupStickersActivity.this.donePressed) {
                GroupStickersActivity.this.donePressed = true;
                if (GroupStickersActivity.this.searching) {
                    GroupStickersActivity.this.showEditDoneProgress(true);
                } else {
                    GroupStickersActivity.this.saveStickerSet();
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.GroupStickersActivity$3 */
    class C29443 implements TextWatcher {
        boolean ignoreTextChange;

        C29443() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (GroupStickersActivity.this.eraseImageView != null) {
                GroupStickersActivity.this.eraseImageView.setVisibility(s.length() > 0 ? 0 : 4);
            }
            if (!this.ignoreTextChange && !GroupStickersActivity.this.ignoreTextChanges) {
                if (s.length() > 5) {
                    this.ignoreTextChange = true;
                    try {
                        Uri uri = Uri.parse(s.toString());
                        if (uri != null) {
                            List<String> segments = uri.getPathSegments();
                            if (segments.size() == 2 && ((String) segments.get(0)).toLowerCase().equals("addstickers")) {
                                GroupStickersActivity.this.usernameTextView.setText((CharSequence) segments.get(1));
                                GroupStickersActivity.this.usernameTextView.setSelection(GroupStickersActivity.this.usernameTextView.length());
                            }
                        }
                    } catch (Exception e) {
                    }
                    this.ignoreTextChange = false;
                }
                GroupStickersActivity.this.resolveStickerSet();
            }
        }
    }

    /* renamed from: org.telegram.ui.GroupStickersActivity$4 */
    class C29454 implements OnClickListener {
        C29454() {
        }

        public void onClick(View v) {
            GroupStickersActivity.this.searchWas = false;
            GroupStickersActivity.this.selectedStickerSet = null;
            GroupStickersActivity.this.usernameTextView.setText("");
            GroupStickersActivity.this.updateRows();
        }
    }

    /* renamed from: org.telegram.ui.GroupStickersActivity$6 */
    class C29476 implements OnItemClickListener {
        C29476() {
        }

        public void onItemClick(View view, int position) {
            if (GroupStickersActivity.this.getParentActivity() != null) {
                if (position == GroupStickersActivity.this.selectedStickerRow) {
                    if (GroupStickersActivity.this.selectedStickerSet != null) {
                        GroupStickersActivity.this.showDialog(new StickersAlert(GroupStickersActivity.this.getParentActivity(), GroupStickersActivity.this, null, GroupStickersActivity.this.selectedStickerSet, null));
                    }
                } else if (position >= GroupStickersActivity.this.stickersStartRow && position < GroupStickersActivity.this.stickersEndRow) {
                    boolean needScroll;
                    if (GroupStickersActivity.this.selectedStickerRow == -1) {
                        needScroll = true;
                    } else {
                        needScroll = false;
                    }
                    int row = GroupStickersActivity.this.layoutManager.findFirstVisibleItemPosition();
                    int top = Integer.MAX_VALUE;
                    Holder holder = (Holder) GroupStickersActivity.this.listView.findViewHolderForAdapterPosition(row);
                    if (holder != null) {
                        top = holder.itemView.getTop();
                    }
                    GroupStickersActivity.this.selectedStickerSet = (TLRPC$TL_messages_stickerSet) StickersQuery.getStickerSets(0).get(position - GroupStickersActivity.this.stickersStartRow);
                    GroupStickersActivity.this.ignoreTextChanges = true;
                    GroupStickersActivity.this.usernameTextView.setText(GroupStickersActivity.this.selectedStickerSet.set.short_name);
                    GroupStickersActivity.this.usernameTextView.setSelection(GroupStickersActivity.this.usernameTextView.length());
                    GroupStickersActivity.this.ignoreTextChanges = false;
                    AndroidUtilities.hideKeyboard(GroupStickersActivity.this.usernameTextView);
                    GroupStickersActivity.this.updateRows();
                    if (needScroll && top != Integer.MAX_VALUE) {
                        GroupStickersActivity.this.layoutManager.scrollToPositionWithOffset(row + 1, top);
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.GroupStickersActivity$7 */
    class C29487 extends OnScrollListener {
        C29487() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == 1) {
                AndroidUtilities.hideKeyboard(GroupStickersActivity.this.getParentActivity().getCurrentFocus());
            }
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        }
    }

    /* renamed from: org.telegram.ui.GroupStickersActivity$9 */
    class C29529 implements Runnable {
        C29529() {
        }

        public void run() {
            if (GroupStickersActivity.this.usernameTextView != null) {
                GroupStickersActivity.this.usernameTextView.requestFocus();
                AndroidUtilities.showKeyboard(GroupStickersActivity.this.usernameTextView);
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return GroupStickersActivity.this.rowCount;
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            StickerSetCell cell;
            switch (holder.getItemViewType()) {
                case 0:
                    long id;
                    boolean z;
                    ArrayList<TLRPC$TL_messages_stickerSet> arrayList = StickersQuery.getStickerSets(0);
                    int row = position - GroupStickersActivity.this.stickersStartRow;
                    cell = holder.itemView;
                    TLRPC$TL_messages_stickerSet set = (TLRPC$TL_messages_stickerSet) arrayList.get(row);
                    cell.setStickersSet((TLRPC$TL_messages_stickerSet) arrayList.get(row), row != arrayList.size() + -1);
                    if (GroupStickersActivity.this.selectedStickerSet != null) {
                        id = GroupStickersActivity.this.selectedStickerSet.set.id;
                    } else if (GroupStickersActivity.this.info == null || GroupStickersActivity.this.info.stickerset == null) {
                        id = 0;
                    } else {
                        id = GroupStickersActivity.this.info.stickerset.id;
                    }
                    if (set.set.id == id) {
                        z = true;
                    } else {
                        z = false;
                    }
                    cell.setChecked(z);
                    return;
                case 1:
                    if (position == GroupStickersActivity.this.infoRow) {
                        String text = LocaleController.getString("ChooseStickerSetMy", R.string.ChooseStickerSetMy);
                        String botName = "@stickers";
                        int index = text.indexOf(botName);
                        if (index != -1) {
                            try {
                                SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
                                stringBuilder.setSpan(new URLSpanNoUnderline("@stickers") {
                                    public void onClick(View widget) {
                                        MessagesController.openByUserName("stickers", GroupStickersActivity.this, 1);
                                    }
                                }, index, botName.length() + index, 18);
                                ((TextInfoPrivacyCell) holder.itemView).setText(stringBuilder);
                                return;
                            } catch (Exception e) {
                                FileLog.e(e);
                                ((TextInfoPrivacyCell) holder.itemView).setText(text);
                                return;
                            }
                        }
                        ((TextInfoPrivacyCell) holder.itemView).setText(text);
                        return;
                    }
                    return;
                case 4:
                    ((HeaderCell) holder.itemView).setText(LocaleController.getString("ChooseFromYourStickers", R.string.ChooseFromYourStickers));
                    return;
                case 5:
                    cell = (StickerSetCell) holder.itemView;
                    if (GroupStickersActivity.this.selectedStickerSet != null) {
                        cell.setStickersSet(GroupStickersActivity.this.selectedStickerSet, false);
                        return;
                    } else if (GroupStickersActivity.this.searching) {
                        cell.setText(LocaleController.getString("Loading", R.string.Loading), null, 0, false);
                        return;
                    } else {
                        cell.setText(LocaleController.getString("ChooseStickerSetNotFound", R.string.ChooseStickerSetNotFound), LocaleController.getString("ChooseStickerSetNotFoundInfo", R.string.ChooseStickerSetNotFoundInfo), R.drawable.ic_smiles2_sad, false);
                        return;
                    }
                default:
                    return;
            }
        }

        public boolean isEnabled(ViewHolder holder) {
            int type = holder.getItemViewType();
            return type == 0 || type == 2 || type == 5;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
                case 0:
                case 5:
                    view = new StickerSetCell(this.mContext, viewType == 0 ? 3 : 2);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    view = new TextInfoPrivacyCell(this.mContext);
                    view.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
                case 2:
                    view = GroupStickersActivity.this.nameContainer;
                    break;
                case 3:
                    view = new ShadowSectionCell(this.mContext);
                    view.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
                case 4:
                    view = new HeaderCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }

        public int getItemViewType(int i) {
            if (i >= GroupStickersActivity.this.stickersStartRow && i < GroupStickersActivity.this.stickersEndRow) {
                return 0;
            }
            if (i == GroupStickersActivity.this.infoRow) {
                return 1;
            }
            if (i == GroupStickersActivity.this.nameRow) {
                return 2;
            }
            if (i == GroupStickersActivity.this.stickersShadowRow) {
                return 3;
            }
            if (i == GroupStickersActivity.this.headerRow) {
                return 4;
            }
            if (i == GroupStickersActivity.this.selectedStickerRow) {
                return 5;
            }
            return 0;
        }
    }

    public GroupStickersActivity(int id) {
        this.chatId = id;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        StickersQuery.checkStickers(0);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.stickersDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.groupStickersDidLoaded);
        updateRows();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.stickersDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatInfoDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.groupStickersDidLoaded);
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("GroupStickers", R.string.GroupStickers));
        this.actionBar.setActionBarMenuOnItemClick(new C29421());
        this.doneItem = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.progressView = new ContextProgressView(context, 1);
        this.doneItem.addView(this.progressView, LayoutHelper.createFrame(-1, -1.0f));
        this.progressView.setVisibility(4);
        this.nameContainer = new LinearLayout(context) {
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(42.0f), 1073741824));
            }

            protected void onDraw(Canvas canvas) {
                if (GroupStickersActivity.this.selectedStickerSet != null) {
                    canvas.drawLine(0.0f, (float) (getHeight() - 1), (float) getWidth(), (float) (getHeight() - 1), Theme.dividerPaint);
                }
            }
        };
        this.nameContainer.setWeightSum(1.0f);
        this.nameContainer.setWillNotDraw(false);
        this.nameContainer.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.nameContainer.setOrientation(0);
        this.nameContainer.setPadding(AndroidUtilities.dp(17.0f), 0, AndroidUtilities.dp(14.0f), 0);
        this.editText = new EditText(context);
        this.editText.setText(MessagesController.getInstance().linkPrefix + "/addstickers/");
        this.editText.setTextSize(1, 17.0f);
        this.editText.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.editText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.editText.setMaxLines(1);
        this.editText.setLines(1);
        this.editText.setEnabled(false);
        this.editText.setFocusable(false);
        this.editText.setBackgroundDrawable(null);
        this.editText.setPadding(0, 0, 0, 0);
        this.editText.setGravity(16);
        this.editText.setSingleLine(true);
        this.editText.setInputType(163840);
        this.editText.setImeOptions(6);
        this.nameContainer.addView(this.editText, LayoutHelper.createLinear(-2, 42));
        this.usernameTextView = new EditTextBoldCursor(context);
        this.usernameTextView.setTextSize(1, 17.0f);
        this.usernameTextView.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.usernameTextView.setCursorSize(AndroidUtilities.dp(20.0f));
        this.usernameTextView.setCursorWidth(1.5f);
        this.usernameTextView.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.usernameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.usernameTextView.setMaxLines(1);
        this.usernameTextView.setLines(1);
        this.usernameTextView.setBackgroundDrawable(null);
        this.usernameTextView.setPadding(0, 0, 0, 0);
        this.usernameTextView.setSingleLine(true);
        this.usernameTextView.setGravity(16);
        this.usernameTextView.setInputType(163872);
        this.usernameTextView.setImeOptions(6);
        this.usernameTextView.setHint(LocaleController.getString("ChooseStickerSetPlaceholder", R.string.ChooseStickerSetPlaceholder));
        this.usernameTextView.addTextChangedListener(new C29443());
        this.nameContainer.addView(this.usernameTextView, LayoutHelper.createLinear(0, 42, 1.0f));
        this.eraseImageView = new ImageView(context);
        this.eraseImageView.setScaleType(ScaleType.CENTER);
        this.eraseImageView.setImageResource(R.drawable.ic_close_white);
        this.eraseImageView.setPadding(AndroidUtilities.dp(16.0f), 0, 0, 0);
        this.eraseImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3), Mode.MULTIPLY));
        this.eraseImageView.setVisibility(4);
        this.eraseImageView.setOnClickListener(new C29454());
        this.nameContainer.addView(this.eraseImageView, LayoutHelper.createLinear(42, 42, 0.0f));
        if (!(this.info == null || this.info.stickerset == null)) {
            this.ignoreTextChanges = true;
            this.usernameTextView.setText(this.info.stickerset.short_name);
            this.usernameTextView.setSelection(this.usernameTextView.length());
            this.ignoreTextChanges = false;
        }
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setFocusable(true);
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.layoutManager = new LinearLayoutManager(context) {
            public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate, boolean focusedChildVisible) {
                return false;
            }

            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.layoutManager.setOrientation(1);
        this.listView.setLayoutManager(this.layoutManager);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C29476());
        this.listView.setOnScrollListener(new C29487());
        return this.fragmentView;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.stickersDidLoaded) {
            if (((Integer) args[0]).intValue() == 0) {
                updateRows();
            }
        } else if (id == NotificationCenter.chatInfoDidLoaded) {
            TLRPC$ChatFull chatFull = args[0];
            if (chatFull.id == this.chatId) {
                if (this.info == null && chatFull.stickerset != null) {
                    this.selectedStickerSet = StickersQuery.getGroupStickerSetById(chatFull.stickerset);
                }
                this.info = chatFull;
                updateRows();
            }
        } else if (id == NotificationCenter.groupStickersDidLoaded) {
            long setId = ((Long) args[0]).longValue();
            if (this.info != null && this.info.stickerset != null && this.info.stickerset.id == ((long) id)) {
                updateRows();
            }
        }
    }

    public void setInfo(TLRPC$ChatFull chatFull) {
        this.info = chatFull;
        if (this.info != null && this.info.stickerset != null) {
            this.selectedStickerSet = StickersQuery.getGroupStickerSetById(this.info.stickerset);
        }
    }

    private void resolveStickerSet() {
        if (this.listAdapter != null) {
            if (this.reqId != 0) {
                ConnectionsManager.getInstance().cancelRequest(this.reqId, true);
                this.reqId = 0;
            }
            if (this.queryRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(this.queryRunnable);
                this.queryRunnable = null;
            }
            this.selectedStickerSet = null;
            if (this.usernameTextView.length() <= 0) {
                this.searching = false;
                this.searchWas = false;
                if (this.selectedStickerRow != -1) {
                    updateRows();
                    return;
                }
                return;
            }
            this.searching = true;
            this.searchWas = true;
            final String query = this.usernameTextView.getText().toString();
            TLRPC$TL_messages_stickerSet existingSet = StickersQuery.getStickerSetByName(query);
            if (existingSet != null) {
                this.selectedStickerSet = existingSet;
            }
            if (this.selectedStickerRow == -1) {
                updateRows();
            } else {
                this.listAdapter.notifyItemChanged(this.selectedStickerRow);
            }
            if (existingSet != null) {
                this.searching = false;
                return;
            }
            Runnable c29518 = new Runnable() {

                /* renamed from: org.telegram.ui.GroupStickersActivity$8$1 */
                class C29501 implements RequestDelegate {
                    C29501() {
                    }

                    public void run(final TLObject response, TLRPC$TL_error error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                GroupStickersActivity.this.searching = false;
                                if (response instanceof TLRPC$TL_messages_stickerSet) {
                                    GroupStickersActivity.this.selectedStickerSet = (TLRPC$TL_messages_stickerSet) response;
                                    if (GroupStickersActivity.this.donePressed) {
                                        GroupStickersActivity.this.saveStickerSet();
                                    } else if (GroupStickersActivity.this.selectedStickerRow != -1) {
                                        GroupStickersActivity.this.listAdapter.notifyItemChanged(GroupStickersActivity.this.selectedStickerRow);
                                    } else {
                                        GroupStickersActivity.this.updateRows();
                                    }
                                } else {
                                    if (GroupStickersActivity.this.selectedStickerRow != -1) {
                                        GroupStickersActivity.this.listAdapter.notifyItemChanged(GroupStickersActivity.this.selectedStickerRow);
                                    }
                                    if (GroupStickersActivity.this.donePressed) {
                                        GroupStickersActivity.this.donePressed = false;
                                        GroupStickersActivity.this.showEditDoneProgress(false);
                                        if (GroupStickersActivity.this.getParentActivity() != null) {
                                            Toast.makeText(GroupStickersActivity.this.getParentActivity(), LocaleController.getString("AddStickersNotFound", R.string.AddStickersNotFound), 0).show();
                                        }
                                    }
                                }
                                GroupStickersActivity.this.reqId = 0;
                            }
                        });
                    }
                }

                public void run() {
                    if (GroupStickersActivity.this.queryRunnable != null) {
                        TLRPC$TL_messages_getStickerSet req = new TLRPC$TL_messages_getStickerSet();
                        req.stickerset = new TLRPC$TL_inputStickerSetShortName();
                        req.stickerset.short_name = query;
                        GroupStickersActivity.this.reqId = ConnectionsManager.getInstance().sendRequest(req, new C29501());
                    }
                }
            };
            this.queryRunnable = c29518;
            AndroidUtilities.runOnUIThread(c29518, 500);
        }
    }

    public void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if (isOpen) {
            AndroidUtilities.runOnUIThread(new C29529(), 100);
        }
    }

    private void saveStickerSet() {
        if (this.info == null || (!(this.info.stickerset == null || this.selectedStickerSet == null || this.selectedStickerSet.set.id != this.info.stickerset.id) || (this.info.stickerset == null && this.selectedStickerSet == null))) {
            finishFragment();
            return;
        }
        showEditDoneProgress(true);
        TLRPC$TL_channels_setStickers req = new TLRPC$TL_channels_setStickers();
        req.channel = MessagesController.getInputChannel(this.chatId);
        if (this.selectedStickerSet == null) {
            req.stickerset = new TLRPC$TL_inputStickerSetEmpty();
        } else {
            ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().remove("group_hide_stickers_" + this.info.id).commit();
            req.stickerset = new TLRPC$TL_inputStickerSetID();
            req.stickerset.id = this.selectedStickerSet.set.id;
            req.stickerset.access_hash = this.selectedStickerSet.set.access_hash;
        }
        ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
            public void run(TLObject response, final TLRPC$TL_error error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (error == null) {
                            if (GroupStickersActivity.this.selectedStickerSet == null) {
                                GroupStickersActivity.this.info.stickerset = null;
                            } else {
                                GroupStickersActivity.this.info.stickerset = GroupStickersActivity.this.selectedStickerSet.set;
                                StickersQuery.putGroupStickerSet(GroupStickersActivity.this.selectedStickerSet);
                            }
                            if (GroupStickersActivity.this.info.stickerset == null) {
                                TLRPC$ChatFull access$1900 = GroupStickersActivity.this.info;
                                access$1900.flags |= 256;
                            } else {
                                GroupStickersActivity.this.info.flags &= -257;
                            }
                            MessagesStorage.getInstance().updateChatInfo(GroupStickersActivity.this.info, false);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{GroupStickersActivity.this.info, Integer.valueOf(0), Boolean.valueOf(true), null});
                            GroupStickersActivity.this.finishFragment();
                            return;
                        }
                        Toast.makeText(GroupStickersActivity.this.getParentActivity(), LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + LogCollector.LINE_SEPARATOR + error.text, 0).show();
                        GroupStickersActivity.this.donePressed = false;
                        GroupStickersActivity.this.showEditDoneProgress(false);
                    }
                });
            }
        });
    }

    private void updateRows() {
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.nameRow = i;
        if (this.selectedStickerSet != null || this.searchWas) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.selectedStickerRow = i;
        } else {
            this.selectedStickerRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.infoRow = i;
        ArrayList<TLRPC$TL_messages_stickerSet> stickerSets = StickersQuery.getStickerSets(0);
        if (stickerSets.isEmpty()) {
            this.headerRow = -1;
            this.stickersStartRow = -1;
            this.stickersEndRow = -1;
            this.stickersShadowRow = -1;
        } else {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.headerRow = i;
            this.stickersStartRow = this.rowCount;
            this.stickersEndRow = this.rowCount + stickerSets.size();
            this.rowCount += stickerSets.size();
            i = this.rowCount;
            this.rowCount = i + 1;
            this.stickersShadowRow = i;
        }
        if (this.nameContainer != null) {
            this.nameContainer.invalidate();
        }
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        if (!ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("view_animations", true)) {
            this.usernameTextView.requestFocus();
            AndroidUtilities.showKeyboard(this.usernameTextView);
        }
    }

    private void showEditDoneProgress(final boolean show) {
        if (this.doneItem != null) {
            if (this.doneItemAnimation != null) {
                this.doneItemAnimation.cancel();
            }
            this.doneItemAnimation = new AnimatorSet();
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (show) {
                this.progressView.setVisibility(0);
                this.doneItem.setEnabled(false);
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "alpha", new float[]{0.0f});
                animatorArr[3] = ObjectAnimator.ofFloat(this.progressView, "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.progressView, "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            } else {
                this.doneItem.getImageView().setVisibility(0);
                this.doneItem.setEnabled(true);
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.progressView, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.progressView, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{0.0f});
                animatorArr[3] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            }
            this.doneItemAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    if (GroupStickersActivity.this.doneItemAnimation != null && GroupStickersActivity.this.doneItemAnimation.equals(animation)) {
                        if (show) {
                            GroupStickersActivity.this.doneItem.getImageView().setVisibility(4);
                        } else {
                            GroupStickersActivity.this.progressView.setVisibility(4);
                        }
                    }
                }

                public void onAnimationCancel(Animator animation) {
                    if (GroupStickersActivity.this.doneItemAnimation != null && GroupStickersActivity.this.doneItemAnimation.equals(animation)) {
                        GroupStickersActivity.this.doneItemAnimation = null;
                    }
                }
            });
            this.doneItemAnimation.setDuration(150);
            this.doneItemAnimation.start();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[24];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{StickerSetCell.class, TextSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[9] = new ThemeDescription(this.editText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[10] = new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[11] = new ThemeDescription(this.usernameTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[12] = new ThemeDescription(this.usernameTextView, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LINKCOLOR, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteLinkText);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[19] = new ThemeDescription(this.nameContainer, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[20] = new ThemeDescription(this.listView, 0, new Class[]{StickerSetCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[21] = new ThemeDescription(this.listView, 0, new Class[]{StickerSetCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[22] = new ThemeDescription(this.listView, ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, new Class[]{StickerSetCell.class}, new String[]{"optionsButton"}, null, null, null, Theme.key_stickers_menuSelector);
        themeDescriptionArr[23] = new ThemeDescription(this.listView, 0, new Class[]{StickerSetCell.class}, new String[]{"optionsButton"}, null, null, null, Theme.key_stickers_menu);
        return themeDescriptionArr;
    }
}
