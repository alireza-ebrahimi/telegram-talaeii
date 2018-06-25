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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetEmpty;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetShortName;
import org.telegram.tgnet.TLRPC$TL_messages_getStickerSet;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.TL_channels_setStickers;
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
    private ChatFull info;
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
    class C47801 extends ActionBarMenuOnItemClick {
        C47801() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                GroupStickersActivity.this.finishFragment();
            } else if (i == 1 && !GroupStickersActivity.this.donePressed) {
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
    class C47823 implements TextWatcher {
        boolean ignoreTextChange;

        C47823() {
        }

        public void afterTextChanged(Editable editable) {
            if (GroupStickersActivity.this.eraseImageView != null) {
                GroupStickersActivity.this.eraseImageView.setVisibility(editable.length() > 0 ? 0 : 4);
            }
            if (!this.ignoreTextChange && !GroupStickersActivity.this.ignoreTextChanges) {
                if (editable.length() > 5) {
                    this.ignoreTextChange = true;
                    try {
                        Uri parse = Uri.parse(editable.toString());
                        if (parse != null) {
                            List pathSegments = parse.getPathSegments();
                            if (pathSegments.size() == 2 && ((String) pathSegments.get(0)).toLowerCase().equals("addstickers")) {
                                GroupStickersActivity.this.usernameTextView.setText((CharSequence) pathSegments.get(1));
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

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.GroupStickersActivity$4 */
    class C47834 implements OnClickListener {
        C47834() {
        }

        public void onClick(View view) {
            GroupStickersActivity.this.searchWas = false;
            GroupStickersActivity.this.selectedStickerSet = null;
            GroupStickersActivity.this.usernameTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
            GroupStickersActivity.this.updateRows();
        }
    }

    /* renamed from: org.telegram.ui.GroupStickersActivity$6 */
    class C47856 implements OnItemClickListener {
        C47856() {
        }

        public void onItemClick(View view, int i) {
            if (GroupStickersActivity.this.getParentActivity() != null) {
                if (i == GroupStickersActivity.this.selectedStickerRow) {
                    if (GroupStickersActivity.this.selectedStickerSet != null) {
                        GroupStickersActivity.this.showDialog(new StickersAlert(GroupStickersActivity.this.getParentActivity(), GroupStickersActivity.this, null, GroupStickersActivity.this.selectedStickerSet, null));
                    }
                } else if (i >= GroupStickersActivity.this.stickersStartRow && i < GroupStickersActivity.this.stickersEndRow) {
                    boolean z = GroupStickersActivity.this.selectedStickerRow == -1;
                    int findFirstVisibleItemPosition = GroupStickersActivity.this.layoutManager.findFirstVisibleItemPosition();
                    Holder holder = (Holder) GroupStickersActivity.this.listView.findViewHolderForAdapterPosition(findFirstVisibleItemPosition);
                    int top = holder != null ? holder.itemView.getTop() : Integer.MAX_VALUE;
                    GroupStickersActivity.this.selectedStickerSet = (TLRPC$TL_messages_stickerSet) StickersQuery.getStickerSets(0).get(i - GroupStickersActivity.this.stickersStartRow);
                    GroupStickersActivity.this.ignoreTextChanges = true;
                    GroupStickersActivity.this.usernameTextView.setText(GroupStickersActivity.this.selectedStickerSet.set.short_name);
                    GroupStickersActivity.this.usernameTextView.setSelection(GroupStickersActivity.this.usernameTextView.length());
                    GroupStickersActivity.this.ignoreTextChanges = false;
                    AndroidUtilities.hideKeyboard(GroupStickersActivity.this.usernameTextView);
                    GroupStickersActivity.this.updateRows();
                    if (z && top != Integer.MAX_VALUE) {
                        GroupStickersActivity.this.layoutManager.scrollToPositionWithOffset(findFirstVisibleItemPosition + 1, top);
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.GroupStickersActivity$7 */
    class C47867 extends OnScrollListener {
        C47867() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 1) {
                AndroidUtilities.hideKeyboard(GroupStickersActivity.this.getParentActivity().getCurrentFocus());
            }
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        }
    }

    /* renamed from: org.telegram.ui.GroupStickersActivity$9 */
    class C47909 implements Runnable {
        C47909() {
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

        public int getItemViewType(int i) {
            return (i < GroupStickersActivity.this.stickersStartRow || i >= GroupStickersActivity.this.stickersEndRow) ? i == GroupStickersActivity.this.infoRow ? 1 : i == GroupStickersActivity.this.nameRow ? 2 : i == GroupStickersActivity.this.stickersShadowRow ? 3 : i == GroupStickersActivity.this.headerRow ? 4 : i == GroupStickersActivity.this.selectedStickerRow ? 5 : 0 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return itemViewType == 0 || itemViewType == 2 || itemViewType == 5;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = true;
            StickerSetCell stickerSetCell;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    ArrayList stickerSets = StickersQuery.getStickerSets(0);
                    int access$1200 = i - GroupStickersActivity.this.stickersStartRow;
                    stickerSetCell = (StickerSetCell) viewHolder.itemView;
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) stickerSets.get(access$1200);
                    stickerSetCell.setStickersSet((TLRPC$TL_messages_stickerSet) stickerSets.get(access$1200), access$1200 != stickerSets.size() + -1);
                    long j = GroupStickersActivity.this.selectedStickerSet != null ? GroupStickersActivity.this.selectedStickerSet.set.id : (GroupStickersActivity.this.info == null || GroupStickersActivity.this.info.stickerset == null) ? 0 : GroupStickersActivity.this.info.stickerset.id;
                    if (tLRPC$TL_messages_stickerSet.set.id != j) {
                        z = false;
                    }
                    stickerSetCell.setChecked(z);
                    return;
                case 1:
                    if (i == GroupStickersActivity.this.infoRow) {
                        CharSequence string = LocaleController.getString("ChooseStickerSetMy", R.string.ChooseStickerSetMy);
                        String str = "@stickers";
                        int indexOf = string.indexOf(str);
                        if (indexOf != -1) {
                            try {
                                CharSequence spannableStringBuilder = new SpannableStringBuilder(string);
                                spannableStringBuilder.setSpan(new URLSpanNoUnderline("@stickers") {
                                    public void onClick(View view) {
                                        MessagesController.openByUserName("stickers", GroupStickersActivity.this, 1);
                                    }
                                }, indexOf, str.length() + indexOf, 18);
                                ((TextInfoPrivacyCell) viewHolder.itemView).setText(spannableStringBuilder);
                                return;
                            } catch (Throwable e) {
                                FileLog.e(e);
                                ((TextInfoPrivacyCell) viewHolder.itemView).setText(string);
                                return;
                            }
                        }
                        ((TextInfoPrivacyCell) viewHolder.itemView).setText(string);
                        return;
                    }
                    return;
                case 4:
                    ((HeaderCell) viewHolder.itemView).setText(LocaleController.getString("ChooseFromYourStickers", R.string.ChooseFromYourStickers));
                    return;
                case 5:
                    stickerSetCell = (StickerSetCell) viewHolder.itemView;
                    if (GroupStickersActivity.this.selectedStickerSet != null) {
                        stickerSetCell.setStickersSet(GroupStickersActivity.this.selectedStickerSet, false);
                        return;
                    } else if (GroupStickersActivity.this.searching) {
                        stickerSetCell.setText(LocaleController.getString("Loading", R.string.Loading), null, 0, false);
                        return;
                    } else {
                        stickerSetCell.setText(LocaleController.getString("ChooseStickerSetNotFound", R.string.ChooseStickerSetNotFound), LocaleController.getString("ChooseStickerSetNotFoundInfo", R.string.ChooseStickerSetNotFoundInfo), R.drawable.ic_smiles2_sad, false);
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            switch (i) {
                case 0:
                case 5:
                    View stickerSetCell = new StickerSetCell(this.mContext, i == 0 ? 3 : 2);
                    stickerSetCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    view = stickerSetCell;
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
    }

    public GroupStickersActivity(int i) {
        this.chatId = i;
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
            final String obj = this.usernameTextView.getText().toString();
            TLRPC$TL_messages_stickerSet stickerSetByName = StickersQuery.getStickerSetByName(obj);
            if (stickerSetByName != null) {
                this.selectedStickerSet = stickerSetByName;
            }
            if (this.selectedStickerRow == -1) {
                updateRows();
            } else {
                this.listAdapter.notifyItemChanged(this.selectedStickerRow);
            }
            if (stickerSetByName != null) {
                this.searching = false;
                return;
            }
            Runnable c47898 = new Runnable() {

                /* renamed from: org.telegram.ui.GroupStickersActivity$8$1 */
                class C47881 implements RequestDelegate {
                    C47881() {
                    }

                    public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                GroupStickersActivity.this.searching = false;
                                if (tLObject instanceof TLRPC$TL_messages_stickerSet) {
                                    GroupStickersActivity.this.selectedStickerSet = (TLRPC$TL_messages_stickerSet) tLObject;
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
                        TLObject tLRPC$TL_messages_getStickerSet = new TLRPC$TL_messages_getStickerSet();
                        tLRPC$TL_messages_getStickerSet.stickerset = new TLRPC$TL_inputStickerSetShortName();
                        tLRPC$TL_messages_getStickerSet.stickerset.short_name = obj;
                        GroupStickersActivity.this.reqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getStickerSet, new C47881());
                    }
                }
            };
            this.queryRunnable = c47898;
            AndroidUtilities.runOnUIThread(c47898, 500);
        }
    }

    private void saveStickerSet() {
        if (this.info == null || (!(this.info.stickerset == null || this.selectedStickerSet == null || this.selectedStickerSet.set.id != this.info.stickerset.id) || (this.info.stickerset == null && this.selectedStickerSet == null))) {
            finishFragment();
            return;
        }
        showEditDoneProgress(true);
        TLObject tL_channels_setStickers = new TL_channels_setStickers();
        tL_channels_setStickers.channel = MessagesController.getInputChannel(this.chatId);
        if (this.selectedStickerSet == null) {
            tL_channels_setStickers.stickerset = new TLRPC$TL_inputStickerSetEmpty();
        } else {
            ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().remove("group_hide_stickers_" + this.info.id).commit();
            tL_channels_setStickers.stickerset = new TLRPC$TL_inputStickerSetID();
            tL_channels_setStickers.stickerset.id = this.selectedStickerSet.set.id;
            tL_channels_setStickers.stickerset.access_hash = this.selectedStickerSet.set.access_hash;
        }
        ConnectionsManager.getInstance().sendRequest(tL_channels_setStickers, new RequestDelegate() {
            public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (tLRPC$TL_error == null) {
                            if (GroupStickersActivity.this.selectedStickerSet == null) {
                                GroupStickersActivity.this.info.stickerset = null;
                            } else {
                                GroupStickersActivity.this.info.stickerset = GroupStickersActivity.this.selectedStickerSet.set;
                                StickersQuery.putGroupStickerSet(GroupStickersActivity.this.selectedStickerSet);
                            }
                            if (GroupStickersActivity.this.info.stickerset == null) {
                                ChatFull access$1900 = GroupStickersActivity.this.info;
                                access$1900.flags |= 256;
                            } else {
                                GroupStickersActivity.this.info.flags &= -257;
                            }
                            MessagesStorage.getInstance().updateChatInfo(GroupStickersActivity.this.info, false);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{GroupStickersActivity.this.info, Integer.valueOf(0), Boolean.valueOf(true), null});
                            GroupStickersActivity.this.finishFragment();
                            return;
                        }
                        Toast.makeText(GroupStickersActivity.this.getParentActivity(), LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + "\n" + tLRPC$TL_error.text, 0).show();
                        GroupStickersActivity.this.donePressed = false;
                        GroupStickersActivity.this.showEditDoneProgress(false);
                    }
                });
            }
        });
    }

    private void showEditDoneProgress(final boolean z) {
        if (this.doneItem != null) {
            if (this.doneItemAnimation != null) {
                this.doneItemAnimation.cancel();
            }
            this.doneItemAnimation = new AnimatorSet();
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (z) {
                this.progressView.setVisibility(0);
                this.doneItem.setEnabled(false);
                animatorSet = this.doneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
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
                animatorArr[2] = ObjectAnimator.ofFloat(this.progressView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[3] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.doneItem.getImageView(), "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            }
            this.doneItemAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    if (GroupStickersActivity.this.doneItemAnimation != null && GroupStickersActivity.this.doneItemAnimation.equals(animator)) {
                        GroupStickersActivity.this.doneItemAnimation = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (GroupStickersActivity.this.doneItemAnimation != null && GroupStickersActivity.this.doneItemAnimation.equals(animator)) {
                        if (z) {
                            GroupStickersActivity.this.doneItem.getImageView().setVisibility(4);
                        } else {
                            GroupStickersActivity.this.progressView.setVisibility(4);
                        }
                    }
                }
            });
            this.doneItemAnimation.setDuration(150);
            this.doneItemAnimation.start();
        }
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
        ArrayList stickerSets = StickersQuery.getStickerSets(0);
        if (stickerSets.isEmpty()) {
            this.headerRow = -1;
            this.stickersStartRow = -1;
            this.stickersEndRow = -1;
            this.stickersShadowRow = -1;
        } else {
            int i2 = this.rowCount;
            this.rowCount = i2 + 1;
            this.headerRow = i2;
            this.stickersStartRow = this.rowCount;
            this.stickersEndRow = this.rowCount + stickerSets.size();
            this.rowCount = stickerSets.size() + this.rowCount;
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

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("GroupStickers", R.string.GroupStickers));
        this.actionBar.setActionBarMenuOnItemClick(new C47801());
        this.doneItem = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.progressView = new ContextProgressView(context, 1);
        this.doneItem.addView(this.progressView, LayoutHelper.createFrame(-1, -1.0f));
        this.progressView.setVisibility(4);
        this.nameContainer = new LinearLayout(context) {
            protected void onDraw(Canvas canvas) {
                if (GroupStickersActivity.this.selectedStickerSet != null) {
                    canvas.drawLine(BitmapDescriptorFactory.HUE_RED, (float) (getHeight() - 1), (float) getWidth(), (float) (getHeight() - 1), Theme.dividerPaint);
                }
            }

            protected void onMeasure(int i, int i2) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(42.0f), 1073741824));
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
        this.usernameTextView.addTextChangedListener(new C47823());
        this.nameContainer.addView(this.usernameTextView, LayoutHelper.createLinear(0, 42, 1.0f));
        this.eraseImageView = new ImageView(context);
        this.eraseImageView.setScaleType(ScaleType.CENTER);
        this.eraseImageView.setImageResource(R.drawable.ic_close_white);
        this.eraseImageView.setPadding(AndroidUtilities.dp(16.0f), 0, 0, 0);
        this.eraseImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3), Mode.MULTIPLY));
        this.eraseImageView.setVisibility(4);
        this.eraseImageView.setOnClickListener(new C47834());
        this.nameContainer.addView(this.eraseImageView, LayoutHelper.createLinear(42, 42, (float) BitmapDescriptorFactory.HUE_RED));
        if (!(this.info == null || this.info.stickerset == null)) {
            this.ignoreTextChanges = true;
            this.usernameTextView.setText(this.info.stickerset.short_name);
            this.usernameTextView.setSelection(this.usernameTextView.length());
            this.ignoreTextChanges = false;
        }
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setFocusable(true);
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.layoutManager = new LinearLayoutManager(context) {
            public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
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
        this.listView.setOnItemClickListener(new C47856());
        this.listView.setOnScrollListener(new C47867());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.stickersDidLoaded) {
            if (((Integer) objArr[0]).intValue() == 0) {
                updateRows();
            }
        } else if (i == NotificationCenter.chatInfoDidLoaded) {
            ChatFull chatFull = (ChatFull) objArr[0];
            if (chatFull.id == this.chatId) {
                if (this.info == null && chatFull.stickerset != null) {
                    this.selectedStickerSet = StickersQuery.getGroupStickerSetById(chatFull.stickerset);
                }
                this.info = chatFull;
                updateRows();
            }
        } else if (i == NotificationCenter.groupStickersDidLoaded) {
            ((Long) objArr[0]).longValue();
            if (this.info != null && this.info.stickerset != null && this.info.stickerset.id == ((long) i)) {
                updateRows();
            }
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

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z) {
            AndroidUtilities.runOnUIThread(new C47909(), 100);
        }
    }

    public void setInfo(ChatFull chatFull) {
        this.info = chatFull;
        if (this.info != null && this.info.stickerset != null) {
            this.selectedStickerSet = StickersQuery.getGroupStickerSetById(this.info.stickerset);
        }
    }
}
