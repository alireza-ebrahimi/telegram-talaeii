package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Cells.GroupCreateSectionCell;
import org.telegram.ui.Cells.GroupCreateUserCell;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.AvatarUpdater;
import org.telegram.ui.Components.AvatarUpdater.AvatarUpdaterDelegate;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.ContextProgressView;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.GroupCreateDividerItemDecoration;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class GroupCreateFinalActivity extends BaseFragment implements NotificationCenterDelegate, AvatarUpdaterDelegate {
    private static final int done_button = 1;
    private GroupCreateAdapter adapter;
    private FileLocation avatar;
    private AvatarDrawable avatarDrawable;
    private BackupImageView avatarImage;
    private AvatarUpdater avatarUpdater = new AvatarUpdater();
    private int chatType = 0;
    private boolean createAfterUpload;
    private ActionBarMenuItem doneItem;
    private AnimatorSet doneItemAnimation;
    private boolean donePressed;
    private EditTextBoldCursor editText;
    private FrameLayout editTextContainer;
    private RecyclerView listView;
    private String nameToSet;
    private ContextProgressView progressView;
    private int reqId;
    private ArrayList<Integer> selectedContacts;
    private InputFile uploadedAvatar;

    /* renamed from: org.telegram.ui.GroupCreateFinalActivity$2 */
    class C47652 extends ActionBarMenuOnItemClick {
        C47652() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                GroupCreateFinalActivity.this.finishFragment();
            } else if (i == 1 && !GroupCreateFinalActivity.this.donePressed) {
                if (GroupCreateFinalActivity.this.editText.length() == 0) {
                    Vibrator vibrator = (Vibrator) GroupCreateFinalActivity.this.getParentActivity().getSystemService("vibrator");
                    if (vibrator != null) {
                        vibrator.vibrate(200);
                    }
                    AndroidUtilities.shakeView(GroupCreateFinalActivity.this.editText, 2.0f, 0);
                    return;
                }
                GroupCreateFinalActivity.this.donePressed = true;
                AndroidUtilities.hideKeyboard(GroupCreateFinalActivity.this.editText);
                GroupCreateFinalActivity.this.editText.setEnabled(false);
                if (GroupCreateFinalActivity.this.avatarUpdater.uploadingAvatar != null) {
                    GroupCreateFinalActivity.this.createAfterUpload = true;
                    return;
                }
                GroupCreateFinalActivity.this.showEditDoneProgress(true);
                GroupCreateFinalActivity.this.reqId = MessagesController.getInstance().createChat(GroupCreateFinalActivity.this.editText.getText().toString(), GroupCreateFinalActivity.this.selectedContacts, null, GroupCreateFinalActivity.this.chatType, GroupCreateFinalActivity.this);
            }
        }
    }

    /* renamed from: org.telegram.ui.GroupCreateFinalActivity$4 */
    class C47684 implements OnClickListener {

        /* renamed from: org.telegram.ui.GroupCreateFinalActivity$4$1 */
        class C47671 implements DialogInterface.OnClickListener {
            C47671() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    GroupCreateFinalActivity.this.avatarUpdater.openCamera();
                } else if (i == 1) {
                    GroupCreateFinalActivity.this.avatarUpdater.openGallery();
                } else if (i == 2) {
                    GroupCreateFinalActivity.this.avatar = null;
                    GroupCreateFinalActivity.this.uploadedAvatar = null;
                    GroupCreateFinalActivity.this.avatarImage.setImage(GroupCreateFinalActivity.this.avatar, "50_50", GroupCreateFinalActivity.this.avatarDrawable);
                }
            }
        }

        C47684() {
        }

        public void onClick(View view) {
            if (GroupCreateFinalActivity.this.getParentActivity() != null) {
                Builder builder = new Builder(GroupCreateFinalActivity.this.getParentActivity());
                builder.setItems(GroupCreateFinalActivity.this.avatar != null ? new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("DeletePhoto", R.string.DeletePhoto)} : new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley)}, new C47671());
                GroupCreateFinalActivity.this.showDialog(builder.create());
            }
        }
    }

    /* renamed from: org.telegram.ui.GroupCreateFinalActivity$5 */
    class C47695 implements TextWatcher {
        C47695() {
        }

        public void afterTextChanged(Editable editable) {
            GroupCreateFinalActivity.this.avatarDrawable.setInfo(5, GroupCreateFinalActivity.this.editText.length() > 0 ? GroupCreateFinalActivity.this.editText.getText().toString() : null, null, false);
            GroupCreateFinalActivity.this.avatarImage.invalidate();
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.GroupCreateFinalActivity$6 */
    class C47706 extends OnScrollListener {
        C47706() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 1) {
                AndroidUtilities.hideKeyboard(GroupCreateFinalActivity.this.editText);
            }
        }
    }

    /* renamed from: org.telegram.ui.GroupCreateFinalActivity$9 */
    class C47739 implements ThemeDescriptionDelegate {
        C47739() {
        }

        public void didSetColor(int i) {
            int childCount = GroupCreateFinalActivity.this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = GroupCreateFinalActivity.this.listView.getChildAt(i2);
                if (childAt instanceof GroupCreateUserCell) {
                    ((GroupCreateUserCell) childAt).update(0);
                }
            }
            GroupCreateFinalActivity.this.avatarDrawable.setInfo(5, GroupCreateFinalActivity.this.editText.length() > 0 ? GroupCreateFinalActivity.this.editText.getText().toString() : null, null, false);
            GroupCreateFinalActivity.this.avatarImage.invalidate();
        }
    }

    public class GroupCreateAdapter extends SelectionAdapter {
        private Context context;

        public GroupCreateAdapter(Context context) {
            this.context = context;
        }

        public int getItemCount() {
            return GroupCreateFinalActivity.this.selectedContacts.size() + 1;
        }

        public int getItemViewType(int i) {
            switch (i) {
                case 0:
                    return 0;
                default:
                    return 1;
            }
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return false;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    ((GroupCreateSectionCell) viewHolder.itemView).setText(LocaleController.formatPluralString("Members", GroupCreateFinalActivity.this.selectedContacts.size()));
                    return;
                default:
                    ((GroupCreateUserCell) viewHolder.itemView).setUser(MessagesController.getInstance().getUser((Integer) GroupCreateFinalActivity.this.selectedContacts.get(i - 1)), null, null);
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View groupCreateSectionCell;
            switch (i) {
                case 0:
                    groupCreateSectionCell = new GroupCreateSectionCell(this.context);
                    break;
                default:
                    groupCreateSectionCell = new GroupCreateUserCell(this.context, false);
                    break;
            }
            return new Holder(groupCreateSectionCell);
        }

        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == 1) {
                ((GroupCreateUserCell) viewHolder.itemView).recycle();
            }
        }
    }

    public GroupCreateFinalActivity(Bundle bundle) {
        super(bundle);
        this.chatType = bundle.getInt("chatType", 0);
        this.avatarDrawable = new AvatarDrawable();
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
                    if (GroupCreateFinalActivity.this.doneItemAnimation != null && GroupCreateFinalActivity.this.doneItemAnimation.equals(animator)) {
                        GroupCreateFinalActivity.this.doneItemAnimation = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (GroupCreateFinalActivity.this.doneItemAnimation != null && GroupCreateFinalActivity.this.doneItemAnimation.equals(animator)) {
                        if (z) {
                            GroupCreateFinalActivity.this.doneItem.getImageView().setVisibility(4);
                        } else {
                            GroupCreateFinalActivity.this.progressView.setVisibility(4);
                        }
                    }
                }
            });
            this.doneItemAnimation.setDuration(150);
            this.doneItemAnimation.start();
        }
    }

    public View createView(Context context) {
        int i = 1;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("NewGroup", R.string.NewGroup));
        this.actionBar.setActionBarMenuOnItemClick(new C47652());
        this.doneItem = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.progressView = new ContextProgressView(context, 1);
        this.doneItem.addView(this.progressView, LayoutHelper.createFrame(-1, -1.0f));
        this.progressView.setVisibility(4);
        this.fragmentView = new LinearLayout(context) {
            protected boolean drawChild(Canvas canvas, View view, long j) {
                boolean drawChild = super.drawChild(canvas, view, j);
                if (view == GroupCreateFinalActivity.this.listView) {
                    GroupCreateFinalActivity.this.parentLayout.drawHeaderShadow(canvas, GroupCreateFinalActivity.this.editTextContainer.getMeasuredHeight());
                }
                return drawChild;
            }
        };
        LinearLayout linearLayout = (LinearLayout) this.fragmentView;
        linearLayout.setOrientation(1);
        this.editTextContainer = new FrameLayout(context);
        linearLayout.addView(this.editTextContainer, LayoutHelper.createLinear(-1, -2));
        this.avatarImage = new BackupImageView(context);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(32.0f));
        this.avatarDrawable.setInfo(5, null, null, this.chatType == 1);
        this.avatarImage.setImageDrawable(this.avatarDrawable);
        this.editTextContainer.addView(this.avatarImage, LayoutHelper.createFrame(64, 64.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 16.0f, 16.0f, LocaleController.isRTL ? 16.0f : BitmapDescriptorFactory.HUE_RED, 16.0f));
        this.avatarDrawable.setDrawPhoto(true);
        this.avatarImage.setOnClickListener(new C47684());
        this.editText = new EditTextBoldCursor(context);
        this.editText.setHint(this.chatType == 0 ? LocaleController.getString("EnterGroupNamePlaceholder", R.string.EnterGroupNamePlaceholder) : LocaleController.getString("EnterListName", R.string.EnterListName));
        if (this.nameToSet != null) {
            this.editText.setText(this.nameToSet);
            this.nameToSet = null;
        }
        this.editText.setMaxLines(4);
        this.editText.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        this.editText.setTextSize(1, 18.0f);
        this.editText.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.editText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.editText.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        this.editText.setImeOptions(ErrorDialogData.BINDER_CRASH);
        this.editText.setInputType(MessagesController.UPDATE_MASK_CHAT_ADMINS);
        this.editText.setPadding(0, 0, 0, AndroidUtilities.dp(8.0f));
        this.editText.setFilters(new InputFilter[]{new LengthFilter(100)});
        this.editText.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.editText.setCursorSize(AndroidUtilities.dp(20.0f));
        this.editText.setCursorWidth(1.5f);
        this.editTextContainer.addView(this.editText, LayoutHelper.createFrame(-1, -2.0f, 16, LocaleController.isRTL ? 16.0f : 96.0f, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? 96.0f : 16.0f, BitmapDescriptorFactory.HUE_RED));
        this.editText.addTextChangedListener(new C47695());
        LayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false);
        this.listView = new RecyclerListView(context);
        RecyclerView recyclerView = this.listView;
        Adapter groupCreateAdapter = new GroupCreateAdapter(context);
        this.adapter = groupCreateAdapter;
        recyclerView.setAdapter(groupCreateAdapter);
        this.listView.setLayoutManager(linearLayoutManager);
        this.listView.setVerticalScrollBarEnabled(false);
        RecyclerView recyclerView2 = this.listView;
        if (!LocaleController.isRTL) {
            i = 2;
        }
        recyclerView2.setVerticalScrollbarPosition(i);
        this.listView.addItemDecoration(new GroupCreateDividerItemDecoration());
        linearLayout.addView(this.listView, LayoutHelper.createLinear(-1, -1));
        this.listView.setOnScrollListener(new C47706());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int i2 = 0;
        if (i == NotificationCenter.updateInterfaces) {
            if (this.listView != null) {
                int intValue = ((Integer) objArr[0]).intValue();
                if ((intValue & 2) != 0 || (intValue & 1) != 0 || (intValue & 4) != 0) {
                    int childCount = this.listView.getChildCount();
                    while (i2 < childCount) {
                        View childAt = this.listView.getChildAt(i2);
                        if (childAt instanceof GroupCreateUserCell) {
                            ((GroupCreateUserCell) childAt).update(intValue);
                        }
                        i2++;
                    }
                }
            }
        } else if (i == NotificationCenter.chatDidFailCreate) {
            this.reqId = 0;
            this.donePressed = false;
            showEditDoneProgress(false);
            if (this.editText != null) {
                this.editText.setEnabled(true);
            }
        } else if (i == NotificationCenter.chatDidCreated) {
            this.reqId = 0;
            int intValue2 = ((Integer) objArr[0]).intValue();
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
            Bundle bundle = new Bundle();
            bundle.putInt("chat_id", intValue2);
            presentFragment(new ChatActivity(bundle), true);
            if (this.uploadedAvatar != null) {
                MessagesController.getInstance().changeChatAvatar(intValue2, this.uploadedAvatar);
            }
        }
    }

    public void didUploadedPhoto(final InputFile inputFile, final PhotoSize photoSize, PhotoSize photoSize2) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                GroupCreateFinalActivity.this.uploadedAvatar = inputFile;
                GroupCreateFinalActivity.this.avatar = photoSize.location;
                GroupCreateFinalActivity.this.avatarImage.setImage(GroupCreateFinalActivity.this.avatar, "50_50", GroupCreateFinalActivity.this.avatarDrawable);
                if (GroupCreateFinalActivity.this.createAfterUpload) {
                    MessagesController.getInstance().createChat(GroupCreateFinalActivity.this.editText.getText().toString(), GroupCreateFinalActivity.this.selectedContacts, null, GroupCreateFinalActivity.this.chatType, GroupCreateFinalActivity.this);
                }
            }
        });
    }

    public ThemeDescription[] getThemeDescriptions() {
        C47739 c47739 = new C47739();
        r10 = new ThemeDescription[34];
        r10[10] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r10[11] = new ThemeDescription(this.editText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[12] = new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_groupcreate_hintText);
        r10[13] = new ThemeDescription(this.editText, ThemeDescription.FLAG_CURSORCOLOR, null, null, null, null, Theme.key_groupcreate_cursor);
        r10[14] = new ThemeDescription(this.editText, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r10[15] = new ThemeDescription(this.editText, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        r10[16] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GroupCreateSectionCell.class}, null, null, null, Theme.key_graySection);
        r10[17] = new ThemeDescription(this.listView, 0, new Class[]{GroupCreateSectionCell.class}, new String[]{"drawable"}, null, null, null, Theme.key_groupcreate_sectionShadow);
        r10[18] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateSectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_groupcreate_sectionText);
        r10[19] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateUserCell.class}, new String[]{"textView"}, null, null, null, Theme.key_groupcreate_sectionText);
        r10[20] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{GroupCreateUserCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_groupcreate_onlineText);
        r10[21] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{GroupCreateUserCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_groupcreate_offlineText);
        r10[22] = new ThemeDescription(this.listView, 0, new Class[]{GroupCreateUserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, c47739, Theme.key_avatar_text);
        r10[23] = new ThemeDescription(null, 0, null, null, null, c47739, Theme.key_avatar_backgroundRed);
        r10[24] = new ThemeDescription(null, 0, null, null, null, c47739, Theme.key_avatar_backgroundOrange);
        r10[25] = new ThemeDescription(null, 0, null, null, null, c47739, Theme.key_avatar_backgroundViolet);
        r10[26] = new ThemeDescription(null, 0, null, null, null, c47739, Theme.key_avatar_backgroundGreen);
        r10[27] = new ThemeDescription(null, 0, null, null, null, c47739, Theme.key_avatar_backgroundCyan);
        r10[28] = new ThemeDescription(null, 0, null, null, null, c47739, Theme.key_avatar_backgroundBlue);
        r10[29] = new ThemeDescription(null, 0, null, null, null, c47739, Theme.key_avatar_backgroundPink);
        r10[30] = new ThemeDescription(this.progressView, 0, null, null, null, null, Theme.key_contextProgressInner2);
        r10[31] = new ThemeDescription(this.progressView, 0, null, null, null, null, Theme.key_contextProgressOuter2);
        r10[32] = new ThemeDescription(this.editText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[33] = new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        return r10;
    }

    public void onActivityResultFragment(int i, int i2, Intent intent) {
        this.avatarUpdater.onActivityResult(i, i2, intent);
    }

    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatDidCreated);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatDidFailCreate);
        this.avatarUpdater.parentFragment = this;
        this.avatarUpdater.delegate = this;
        this.selectedContacts = getArguments().getIntegerArrayList("result");
        final ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.selectedContacts.size(); i++) {
            Integer num = (Integer) this.selectedContacts.get(i);
            if (MessagesController.getInstance().getUser(num) == null) {
                arrayList.add(num);
            }
        }
        if (!arrayList.isEmpty()) {
            final Semaphore semaphore = new Semaphore(0);
            final ArrayList arrayList2 = new ArrayList();
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    arrayList2.addAll(MessagesStorage.getInstance().getUsers(arrayList));
                    semaphore.release();
                }
            });
            try {
                semaphore.acquire();
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (arrayList.size() != arrayList2.size() || arrayList2.isEmpty()) {
                return false;
            }
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                MessagesController.getInstance().putUser((User) it.next(), true);
            }
        }
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatDidCreated);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatDidFailCreate);
        this.avatarUpdater.clear();
        if (this.reqId != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.reqId, true);
        }
    }

    public void onResume() {
        super.onResume();
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
    }

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z) {
            this.editText.requestFocus();
            AndroidUtilities.showKeyboard(this.editText);
        }
    }

    public void restoreSelfArgs(Bundle bundle) {
        if (this.avatarUpdater != null) {
            this.avatarUpdater.currentPicturePath = bundle.getString("path");
        }
        Object string = bundle.getString("nameTextView");
        if (string == null) {
            return;
        }
        if (this.editText != null) {
            this.editText.setText(string);
        } else {
            this.nameToSet = string;
        }
    }

    public void saveSelfArgs(Bundle bundle) {
        if (!(this.avatarUpdater == null || this.avatarUpdater.currentPicturePath == null)) {
            bundle.putString("path", this.avatarUpdater.currentPicturePath);
        }
        if (this.editText != null) {
            String obj = this.editText.getText().toString();
            if (obj != null && obj.length() != 0) {
                bundle.putString("nameTextView", obj);
            }
        }
    }
}
