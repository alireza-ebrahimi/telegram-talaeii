package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC.InputUser;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Adapters.SearchAdapterHelper;
import org.telegram.ui.Adapters.SearchAdapterHelper.HashtagObject;
import org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate;
import org.telegram.ui.Cells.GroupCreateSectionCell;
import org.telegram.ui.Cells.GroupCreateUserCell;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.GroupCreateDividerItemDecoration;
import org.telegram.ui.Components.GroupCreateSpan;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.FastScrollAdapter;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;

public class GroupCreateActivity extends BaseFragment implements OnClickListener, NotificationCenterDelegate {
    public static final int ACTION_DELETE = 1;
    private static final int done_button = 1;
    private int actionType;
    private GroupCreateAdapter adapter;
    private ArrayList<GroupCreateSpan> allSpans = new ArrayList();
    private int chatId;
    private int chatType = 0;
    private int containerHeight;
    private GroupCreateSpan currentDeletingSpan;
    private AnimatorSet currentDoneButtonAnimation;
    private GroupCreateActivityDelegate delegate;
    private View doneButton;
    private boolean doneButtonVisible;
    private EditTextBoldCursor editText;
    private EmptyTextProgressView emptyView;
    private int fieldY;
    private boolean ignoreScrollEvent;
    private boolean isAlwaysShare;
    private boolean isGroup;
    private boolean isNeverShare;
    private GroupCreateDividerItemDecoration itemDecoration;
    private RecyclerListView listView;
    private int maxCount = MessagesController.getInstance().maxMegagroupCount;
    private ScrollView scrollView;
    private boolean searchWas;
    private boolean searching;
    private HashMap<Integer, GroupCreateSpan> selectedContacts = new HashMap();
    private SpansContainer spansContainer;

    /* renamed from: org.telegram.ui.GroupCreateActivity$1 */
    class C47481 extends ActionBarMenuOnItemClick {
        C47481() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                GroupCreateActivity.this.finishFragment();
            } else if (i == 1) {
                GroupCreateActivity.this.onDonePressed();
            }
        }
    }

    /* renamed from: org.telegram.ui.GroupCreateActivity$5 */
    class C47525 implements Callback {
        C47525() {
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        public void onDestroyActionMode(ActionMode actionMode) {
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }
    }

    /* renamed from: org.telegram.ui.GroupCreateActivity$6 */
    class C47536 implements OnEditorActionListener {
        C47536() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            return i == 6 && GroupCreateActivity.this.onDonePressed();
        }
    }

    /* renamed from: org.telegram.ui.GroupCreateActivity$7 */
    class C47547 implements OnKeyListener {
        private boolean wasEmpty;

        C47547() {
        }

        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (i == 67) {
                if (keyEvent.getAction() == 0) {
                    this.wasEmpty = GroupCreateActivity.this.editText.length() == 0;
                } else if (keyEvent.getAction() == 1 && this.wasEmpty && !GroupCreateActivity.this.allSpans.isEmpty()) {
                    GroupCreateActivity.this.spansContainer.removeSpan((GroupCreateSpan) GroupCreateActivity.this.allSpans.get(GroupCreateActivity.this.allSpans.size() - 1));
                    GroupCreateActivity.this.updateHint();
                    GroupCreateActivity.this.checkVisibleRows();
                    return true;
                }
            }
            return false;
        }
    }

    /* renamed from: org.telegram.ui.GroupCreateActivity$8 */
    class C47558 implements TextWatcher {
        C47558() {
        }

        public void afterTextChanged(Editable editable) {
            if (GroupCreateActivity.this.editText.length() != 0) {
                GroupCreateActivity.this.searching = true;
                GroupCreateActivity.this.searchWas = true;
                GroupCreateActivity.this.adapter.setSearching(true);
                GroupCreateActivity.this.itemDecoration.setSearching(true);
                GroupCreateActivity.this.adapter.searchDialogs(GroupCreateActivity.this.editText.getText().toString());
                GroupCreateActivity.this.listView.setFastScrollVisible(false);
                GroupCreateActivity.this.listView.setVerticalScrollBarEnabled(true);
                GroupCreateActivity.this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
                return;
            }
            GroupCreateActivity.this.closeSearch();
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.GroupCreateActivity$9 */
    class C47569 implements OnItemClickListener {
        C47569() {
        }

        public void onItemClick(View view, int i) {
            boolean z = false;
            if (view instanceof GroupCreateUserCell) {
                GroupCreateUserCell groupCreateUserCell = (GroupCreateUserCell) view;
                User user = groupCreateUserCell.getUser();
                if (user != null) {
                    boolean containsKey = GroupCreateActivity.this.selectedContacts.containsKey(Integer.valueOf(user.id));
                    if (containsKey) {
                        GroupCreateActivity.this.spansContainer.removeSpan((GroupCreateSpan) GroupCreateActivity.this.selectedContacts.get(Integer.valueOf(user.id)));
                    } else if (GroupCreateActivity.this.maxCount != 0 && GroupCreateActivity.this.selectedContacts.size() == GroupCreateActivity.this.maxCount) {
                        return;
                    } else {
                        if (GroupCreateActivity.this.chatType == 0 && GroupCreateActivity.this.selectedContacts.size() == MessagesController.getInstance().maxGroupCount) {
                            Builder builder = new Builder(GroupCreateActivity.this.getParentActivity());
                            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                            builder.setMessage(LocaleController.getString("SoftUserLimitAlert", R.string.SoftUserLimitAlert));
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                            GroupCreateActivity.this.showDialog(builder.create());
                            return;
                        }
                        MessagesController.getInstance().putUser(user, !GroupCreateActivity.this.searching);
                        GroupCreateSpan groupCreateSpan = new GroupCreateSpan(GroupCreateActivity.this.editText.getContext(), user);
                        GroupCreateActivity.this.spansContainer.addSpan(groupCreateSpan);
                        groupCreateSpan.setOnClickListener(GroupCreateActivity.this);
                    }
                    GroupCreateActivity.this.updateHint();
                    if (GroupCreateActivity.this.searching || GroupCreateActivity.this.searchWas) {
                        AndroidUtilities.showKeyboard(GroupCreateActivity.this.editText);
                    } else {
                        if (!containsKey) {
                            z = true;
                        }
                        groupCreateUserCell.setChecked(z, true);
                    }
                    if (GroupCreateActivity.this.editText.length() > 0) {
                        GroupCreateActivity.this.editText.setText(null);
                    }
                }
            }
        }
    }

    public interface GroupCreateActivityDelegate {
        void didSelectUsers(ArrayList<Integer> arrayList);
    }

    public class GroupCreateAdapter extends FastScrollAdapter {
        private ArrayList<User> contacts = new ArrayList();
        private Context context;
        private SearchAdapterHelper searchAdapterHelper;
        private ArrayList<User> searchResult = new ArrayList();
        private ArrayList<CharSequence> searchResultNames = new ArrayList();
        private Timer searchTimer;
        private boolean searching;

        public GroupCreateAdapter(Context context) {
            this.context = context;
            ArrayList arrayList = ContactsController.getInstance().contacts;
            for (int i = 0; i < arrayList.size(); i++) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) arrayList.get(i)).user_id));
                if (!(user == null || user.self || user.deleted)) {
                    this.contacts.add(user);
                }
            }
            this.searchAdapterHelper = new SearchAdapterHelper();
            this.searchAdapterHelper.setDelegate(new SearchAdapterHelperDelegate(GroupCreateActivity.this) {
                public void onDataSetChanged() {
                    GroupCreateAdapter.this.notifyDataSetChanged();
                }

                public void onSetHashtags(ArrayList<HashtagObject> arrayList, HashMap<String, HashtagObject> hashMap) {
                }
            });
        }

        private void updateSearchResults(final ArrayList<User> arrayList, final ArrayList<CharSequence> arrayList2) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    GroupCreateAdapter.this.searchResult = arrayList;
                    GroupCreateAdapter.this.searchResultNames = arrayList2;
                    GroupCreateAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public int getItemCount() {
            if (!this.searching) {
                return this.contacts.size();
            }
            int size = this.searchResult.size();
            int size2 = this.searchAdapterHelper.getGlobalSearch().size();
            return size2 != 0 ? size + (size2 + 1) : size;
        }

        public int getItemViewType(int i) {
            return (this.searching && i == this.searchResult.size()) ? 0 : 1;
        }

        public String getLetter(int i) {
            if (i < 0 || i >= this.contacts.size()) {
                return null;
            }
            User user = (User) this.contacts.get(i);
            if (user == null) {
                return null;
            }
            if (LocaleController.nameDisplayOrder == 1) {
                if (!TextUtils.isEmpty(user.first_name)) {
                    return user.first_name.substring(0, 1).toUpperCase();
                }
                if (!TextUtils.isEmpty(user.last_name)) {
                    return user.last_name.substring(0, 1).toUpperCase();
                }
            } else if (!TextUtils.isEmpty(user.last_name)) {
                return user.last_name.substring(0, 1).toUpperCase();
            } else {
                if (!TextUtils.isEmpty(user.first_name)) {
                    return user.first_name.substring(0, 1).toUpperCase();
                }
            }
            return TtmlNode.ANONYMOUS_REGION_ID;
        }

        public int getPositionForScrollProgress(float f) {
            return (int) (((float) getItemCount()) * f);
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Object obj;
            CharSequence charSequence = null;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    GroupCreateSectionCell groupCreateSectionCell = (GroupCreateSectionCell) viewHolder.itemView;
                    if (this.searching) {
                        groupCreateSectionCell.setText(LocaleController.getString("GlobalSearch", R.string.GlobalSearch));
                        return;
                    }
                    return;
                default:
                    CharSequence charSequence2;
                    User user;
                    GroupCreateUserCell groupCreateUserCell = (GroupCreateUserCell) viewHolder.itemView;
                    if (this.searching) {
                        int size = this.searchResult.size();
                        User user2 = (i < 0 || i >= size) ? (i <= size || i > this.searchAdapterHelper.getGlobalSearch().size() + size) ? null : (User) this.searchAdapterHelper.getGlobalSearch().get((i - size) - 1) : (User) this.searchResult.get(i);
                        if (user2 != null) {
                            if (i < size) {
                                charSequence2 = (CharSequence) this.searchResultNames.get(i);
                                if (!(charSequence2 == null || TextUtils.isEmpty(user2.username) || !charSequence2.toString().startsWith("@" + user2.username))) {
                                    charSequence = charSequence2;
                                    charSequence2 = null;
                                }
                            } else if (i > size && !TextUtils.isEmpty(user2.username)) {
                                String lastFoundUsername = this.searchAdapterHelper.getLastFoundUsername();
                                String substring = lastFoundUsername.startsWith("@") ? lastFoundUsername.substring(1) : lastFoundUsername;
                                try {
                                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(null);
                                    spannableStringBuilder.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), 0, substring.length(), 33);
                                    charSequence2 = null;
                                    obj = spannableStringBuilder;
                                } catch (Exception e) {
                                    obj = user2.username;
                                    charSequence2 = null;
                                }
                            }
                            user = user2;
                        }
                        charSequence2 = null;
                        user = user2;
                    } else {
                        user = (User) this.contacts.get(i);
                        charSequence2 = null;
                    }
                    groupCreateUserCell.setUser(user, charSequence2, charSequence);
                    groupCreateUserCell.setChecked(GroupCreateActivity.this.selectedContacts.containsKey(Integer.valueOf(user.id)), false);
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
                    groupCreateSectionCell = new GroupCreateUserCell(this.context, true);
                    break;
            }
            return new Holder(groupCreateSectionCell);
        }

        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof GroupCreateUserCell) {
                ((GroupCreateUserCell) viewHolder.itemView).recycle();
            }
        }

        public void searchDialogs(final String str) {
            try {
                if (this.searchTimer != null) {
                    this.searchTimer.cancel();
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (str == null) {
                this.searchResult.clear();
                this.searchResultNames.clear();
                this.searchAdapterHelper.queryServerSearch(null, true, false, false, false, 0, false);
                notifyDataSetChanged();
                return;
            }
            this.searchTimer = new Timer();
            this.searchTimer.schedule(new TimerTask() {

                /* renamed from: org.telegram.ui.GroupCreateActivity$GroupCreateAdapter$2$1 */
                class C47591 implements Runnable {

                    /* renamed from: org.telegram.ui.GroupCreateActivity$GroupCreateAdapter$2$1$1 */
                    class C47581 implements Runnable {
                        C47581() {
                        }

                        public void run() {
                            String toLowerCase = str.trim().toLowerCase();
                            if (toLowerCase.length() == 0) {
                                GroupCreateAdapter.this.updateSearchResults(new ArrayList(), new ArrayList());
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
                            ArrayList arrayList2 = new ArrayList();
                            for (int i = 0; i < GroupCreateAdapter.this.contacts.size(); i++) {
                                User user = (User) GroupCreateAdapter.this.contacts.get(i);
                                String toLowerCase2 = ContactsController.formatName(user.first_name, user.last_name).toLowerCase();
                                toLowerCase = LocaleController.getInstance().getTranslitString(toLowerCase2);
                                if (toLowerCase2.equals(toLowerCase)) {
                                    toLowerCase = null;
                                }
                                int length = strArr.length;
                                Object obj = null;
                                int i2 = 0;
                                while (i2 < length) {
                                    String str2 = strArr[i2];
                                    if (toLowerCase2.startsWith(str2) || toLowerCase2.contains(" " + str2) || (r2 != null && (r2.startsWith(str2) || r2.contains(" " + str2)))) {
                                        obj = 1;
                                    } else if (user.username != null && user.username.startsWith(str2)) {
                                        obj = 2;
                                    }
                                    if (r3 != null) {
                                        if (r3 == 1) {
                                            arrayList2.add(AndroidUtilities.generateSearchName(user.first_name, user.last_name, str2));
                                        } else {
                                            arrayList2.add(AndroidUtilities.generateSearchName("@" + user.username, null, "@" + str2));
                                        }
                                        arrayList.add(user);
                                    } else {
                                        i2++;
                                    }
                                }
                            }
                            GroupCreateAdapter.this.updateSearchResults(arrayList, arrayList2);
                        }
                    }

                    C47591() {
                    }

                    public void run() {
                        GroupCreateAdapter.this.searchAdapterHelper.queryServerSearch(str, true, false, false, false, 0, false);
                        Utilities.searchQueue.postRunnable(new C47581());
                    }
                }

                public void run() {
                    try {
                        GroupCreateAdapter.this.searchTimer.cancel();
                        GroupCreateAdapter.this.searchTimer = null;
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    AndroidUtilities.runOnUIThread(new C47591());
                }
            }, 200, 300);
        }

        public void setSearching(boolean z) {
            if (this.searching != z) {
                this.searching = z;
                notifyDataSetChanged();
            }
        }
    }

    private class SpansContainer extends ViewGroup {
        private View addingSpan;
        private boolean animationStarted;
        private ArrayList<Animator> animators = new ArrayList();
        private AnimatorSet currentAnimation;
        private View removingSpan;

        /* renamed from: org.telegram.ui.GroupCreateActivity$SpansContainer$1 */
        class C47621 extends AnimatorListenerAdapter {
            C47621() {
            }

            public void onAnimationEnd(Animator animator) {
                SpansContainer.this.addingSpan = null;
                SpansContainer.this.currentAnimation = null;
                SpansContainer.this.animationStarted = false;
                GroupCreateActivity.this.editText.setAllowDrawCursor(true);
            }
        }

        public SpansContainer(Context context) {
            super(context);
        }

        public void addSpan(GroupCreateSpan groupCreateSpan) {
            GroupCreateActivity.this.allSpans.add(groupCreateSpan);
            GroupCreateActivity.this.selectedContacts.put(Integer.valueOf(groupCreateSpan.getUid()), groupCreateSpan);
            GroupCreateActivity.this.editText.setHintVisible(false);
            if (this.currentAnimation != null) {
                this.currentAnimation.setupEndValues();
                this.currentAnimation.cancel();
            }
            this.animationStarted = false;
            this.currentAnimation = new AnimatorSet();
            this.currentAnimation.addListener(new C47621());
            this.currentAnimation.setDuration(150);
            this.addingSpan = groupCreateSpan;
            this.animators.clear();
            this.animators.add(ObjectAnimator.ofFloat(this.addingSpan, "scaleX", new float[]{0.01f, 1.0f}));
            this.animators.add(ObjectAnimator.ofFloat(this.addingSpan, "scaleY", new float[]{0.01f, 1.0f}));
            this.animators.add(ObjectAnimator.ofFloat(this.addingSpan, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}));
            addView(groupCreateSpan);
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                childAt.layout(0, 0, childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
            }
        }

        protected void onMeasure(int i, int i2) {
            int i3;
            int childCount = getChildCount();
            int size = MeasureSpec.getSize(i);
            int dp = size - AndroidUtilities.dp(32.0f);
            int dp2 = AndroidUtilities.dp(12.0f);
            int i4 = 0;
            int dp3 = AndroidUtilities.dp(12.0f);
            int i5 = dp2;
            dp2 = 0;
            for (i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt instanceof GroupCreateSpan) {
                    childAt.measure(MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(32.0f), 1073741824));
                    if (childAt != this.removingSpan && childAt.getMeasuredWidth() + dp2 > dp) {
                        i5 += childAt.getMeasuredHeight() + AndroidUtilities.dp(12.0f);
                        dp2 = 0;
                    }
                    if (childAt.getMeasuredWidth() + i4 > dp) {
                        dp3 += childAt.getMeasuredHeight() + AndroidUtilities.dp(12.0f);
                        i4 = 0;
                    }
                    int dp4 = AndroidUtilities.dp(16.0f) + dp2;
                    if (!this.animationStarted) {
                        if (childAt == this.removingSpan) {
                            childAt.setTranslationX((float) (AndroidUtilities.dp(16.0f) + i4));
                            childAt.setTranslationY((float) dp3);
                        } else if (this.removingSpan != null) {
                            if (childAt.getTranslationX() != ((float) dp4)) {
                                this.animators.add(ObjectAnimator.ofFloat(childAt, "translationX", new float[]{(float) dp4}));
                            }
                            if (childAt.getTranslationY() != ((float) i5)) {
                                this.animators.add(ObjectAnimator.ofFloat(childAt, "translationY", new float[]{(float) i5}));
                            }
                        } else {
                            childAt.setTranslationX((float) dp4);
                            childAt.setTranslationY((float) i5);
                        }
                    }
                    if (childAt != this.removingSpan) {
                        dp2 += childAt.getMeasuredWidth() + AndroidUtilities.dp(9.0f);
                    }
                    i4 += childAt.getMeasuredWidth() + AndroidUtilities.dp(9.0f);
                }
            }
            i3 = AndroidUtilities.isTablet() ? AndroidUtilities.dp(366.0f) / 3 : (Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y) - AndroidUtilities.dp(164.0f)) / 3;
            if (dp - dp2 < i3) {
                dp2 = 0;
                i5 += AndroidUtilities.dp(44.0f);
            }
            if (dp - i4 < i3) {
                dp3 += AndroidUtilities.dp(44.0f);
            }
            GroupCreateActivity.this.editText.measure(MeasureSpec.makeMeasureSpec(dp - dp2, 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(32.0f), 1073741824));
            if (!this.animationStarted) {
                dp3 += AndroidUtilities.dp(44.0f);
                dp2 += AndroidUtilities.dp(16.0f);
                GroupCreateActivity.this.fieldY = i5;
                if (this.currentAnimation != null) {
                    if (GroupCreateActivity.this.containerHeight != i5 + AndroidUtilities.dp(44.0f)) {
                        this.animators.add(ObjectAnimator.ofInt(GroupCreateActivity.this, "containerHeight", new int[]{i5 + AndroidUtilities.dp(44.0f)}));
                    }
                    if (GroupCreateActivity.this.editText.getTranslationX() != ((float) dp2)) {
                        this.animators.add(ObjectAnimator.ofFloat(GroupCreateActivity.this.editText, "translationX", new float[]{(float) dp2}));
                    }
                    if (GroupCreateActivity.this.editText.getTranslationY() != ((float) GroupCreateActivity.this.fieldY)) {
                        this.animators.add(ObjectAnimator.ofFloat(GroupCreateActivity.this.editText, "translationY", new float[]{(float) GroupCreateActivity.this.fieldY}));
                    }
                    GroupCreateActivity.this.editText.setAllowDrawCursor(false);
                    this.currentAnimation.playTogether(this.animators);
                    this.currentAnimation.start();
                    this.animationStarted = true;
                } else {
                    GroupCreateActivity.this.containerHeight = dp3;
                    GroupCreateActivity.this.editText.setTranslationX((float) dp2);
                    GroupCreateActivity.this.editText.setTranslationY((float) GroupCreateActivity.this.fieldY);
                }
            } else if (!(this.currentAnimation == null || GroupCreateActivity.this.ignoreScrollEvent || this.removingSpan != null)) {
                GroupCreateActivity.this.editText.bringPointIntoView(GroupCreateActivity.this.editText.getSelectionStart());
            }
            setMeasuredDimension(size, GroupCreateActivity.this.containerHeight);
        }

        public void removeSpan(final GroupCreateSpan groupCreateSpan) {
            GroupCreateActivity.this.ignoreScrollEvent = true;
            GroupCreateActivity.this.selectedContacts.remove(Integer.valueOf(groupCreateSpan.getUid()));
            GroupCreateActivity.this.allSpans.remove(groupCreateSpan);
            groupCreateSpan.setOnClickListener(null);
            if (this.currentAnimation != null) {
                this.currentAnimation.setupEndValues();
                this.currentAnimation.cancel();
            }
            this.animationStarted = false;
            this.currentAnimation = new AnimatorSet();
            this.currentAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    SpansContainer.this.removeView(groupCreateSpan);
                    SpansContainer.this.removingSpan = null;
                    SpansContainer.this.currentAnimation = null;
                    SpansContainer.this.animationStarted = false;
                    GroupCreateActivity.this.editText.setAllowDrawCursor(true);
                    if (GroupCreateActivity.this.allSpans.isEmpty()) {
                        GroupCreateActivity.this.editText.setHintVisible(true);
                    }
                }
            });
            this.currentAnimation.setDuration(150);
            this.removingSpan = groupCreateSpan;
            this.animators.clear();
            this.animators.add(ObjectAnimator.ofFloat(this.removingSpan, "scaleX", new float[]{1.0f, 0.01f}));
            this.animators.add(ObjectAnimator.ofFloat(this.removingSpan, "scaleY", new float[]{1.0f, 0.01f}));
            this.animators.add(ObjectAnimator.ofFloat(this.removingSpan, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
            requestLayout();
        }
    }

    public GroupCreateActivity(Bundle bundle) {
        super(bundle);
        this.chatType = bundle.getInt("chatType", 0);
        this.isAlwaysShare = bundle.getBoolean("isAlwaysShare", false);
        this.actionType = bundle.getInt("actionType", -1);
        this.isNeverShare = bundle.getBoolean("isNeverShare", false);
        this.isGroup = bundle.getBoolean("isGroup", false);
        this.chatId = bundle.getInt("chatId");
        this.maxCount = this.chatType == 0 ? MessagesController.getInstance().maxMegagroupCount : MessagesController.getInstance().maxBroadcastCount;
    }

    private void checkVisibleRows() {
        int childCount = this.listView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof GroupCreateUserCell) {
                GroupCreateUserCell groupCreateUserCell = (GroupCreateUserCell) childAt;
                User user = groupCreateUserCell.getUser();
                if (user != null) {
                    groupCreateUserCell.setChecked(this.selectedContacts.containsKey(Integer.valueOf(user.id)), true);
                }
            }
        }
    }

    private void closeSearch() {
        this.searching = false;
        this.searchWas = false;
        this.itemDecoration.setSearching(false);
        this.adapter.setSearching(false);
        this.adapter.searchDialogs(null);
        this.listView.setFastScrollVisible(true);
        this.listView.setVerticalScrollBarEnabled(false);
        this.emptyView.setText(LocaleController.getString("NoContacts", R.string.NoContacts));
    }

    private boolean onDonePressed() {
        ArrayList arrayList;
        if (this.actionType == 1) {
            arrayList = new ArrayList();
            for (Integer user : this.selectedContacts.keySet()) {
                User user2 = MessagesController.getInstance().getUser(user);
                if (user2 != null) {
                    arrayList.add(user2);
                }
            }
            ContactsController.getInstance().deleteContact(arrayList);
            ContactsController.getInstance().loadContacts(false, 0);
            finishFragment();
            return true;
        }
        if (this.chatType == 2) {
            arrayList = new ArrayList();
            for (Integer user3 : this.selectedContacts.keySet()) {
                InputUser inputUser = MessagesController.getInputUser(MessagesController.getInstance().getUser(user3));
                if (inputUser != null) {
                    arrayList.add(inputUser);
                }
            }
            MessagesController.getInstance().addUsersToChannel(this.chatId, arrayList, null);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
            Bundle bundle = new Bundle();
            bundle.putInt("chat_id", this.chatId);
            presentFragment(new ChatActivity(bundle), true);
        } else if (!this.doneButtonVisible || this.selectedContacts.isEmpty()) {
            return false;
        } else {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.addAll(this.selectedContacts.keySet());
            if (this.isAlwaysShare || this.isNeverShare) {
                if (this.delegate != null) {
                    this.delegate.didSelectUsers(arrayList2);
                }
                finishFragment();
            } else {
                Bundle bundle2 = new Bundle();
                bundle2.putIntegerArrayList("result", arrayList2);
                bundle2.putInt("chatType", this.chatType);
                presentFragment(new GroupCreateFinalActivity(bundle2));
            }
        }
        return true;
    }

    private void updateHint() {
        if (!(this.isAlwaysShare || this.isNeverShare)) {
            if (this.chatType == 2) {
                this.actionBar.setSubtitle(LocaleController.formatPluralString("Members", this.selectedContacts.size()));
            } else if (this.selectedContacts.isEmpty()) {
                this.actionBar.setSubtitle(LocaleController.formatString("MembersCountZero", R.string.MembersCountZero, new Object[]{LocaleController.formatPluralString("Members", this.maxCount)}));
            } else {
                this.actionBar.setSubtitle(LocaleController.formatString("MembersCount", R.string.MembersCount, new Object[]{Integer.valueOf(this.selectedContacts.size()), Integer.valueOf(this.maxCount)}));
            }
        }
        if (this.chatType == 2) {
            return;
        }
        AnimatorSet animatorSet;
        Animator[] animatorArr;
        if (this.doneButtonVisible && this.allSpans.isEmpty()) {
            if (this.currentDoneButtonAnimation != null) {
                this.currentDoneButtonAnimation.cancel();
            }
            this.currentDoneButtonAnimation = new AnimatorSet();
            animatorSet = this.currentDoneButtonAnimation;
            animatorArr = new Animator[3];
            animatorArr[0] = ObjectAnimator.ofFloat(this.doneButton, "scaleX", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr[1] = ObjectAnimator.ofFloat(this.doneButton, "scaleY", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr[2] = ObjectAnimator.ofFloat(this.doneButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorSet.playTogether(animatorArr);
            this.currentDoneButtonAnimation.setDuration(180);
            this.currentDoneButtonAnimation.start();
            this.doneButtonVisible = false;
        } else if (!this.doneButtonVisible && !this.allSpans.isEmpty()) {
            if (this.currentDoneButtonAnimation != null) {
                this.currentDoneButtonAnimation.cancel();
            }
            this.currentDoneButtonAnimation = new AnimatorSet();
            animatorSet = this.currentDoneButtonAnimation;
            animatorArr = new Animator[3];
            animatorArr[0] = ObjectAnimator.ofFloat(this.doneButton, "scaleX", new float[]{1.0f});
            animatorArr[1] = ObjectAnimator.ofFloat(this.doneButton, "scaleY", new float[]{1.0f});
            animatorArr[2] = ObjectAnimator.ofFloat(this.doneButton, "alpha", new float[]{1.0f});
            animatorSet.playTogether(animatorArr);
            this.currentDoneButtonAnimation.setDuration(180);
            this.currentDoneButtonAnimation.start();
            this.doneButtonVisible = true;
        }
    }

    public View createView(Context context) {
        int i = 1;
        this.searching = false;
        this.searchWas = false;
        this.allSpans.clear();
        this.selectedContacts.clear();
        this.currentDeletingSpan = null;
        this.doneButtonVisible = this.chatType == 2;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (this.chatType == 2) {
            this.actionBar.setTitle(LocaleController.getString("ChannelAddMembers", R.string.ChannelAddMembers));
        } else if (this.isAlwaysShare) {
            if (this.isGroup) {
                this.actionBar.setTitle(LocaleController.getString("AlwaysAllow", R.string.AlwaysAllow));
            } else {
                this.actionBar.setTitle(LocaleController.getString("AlwaysShareWithTitle", R.string.AlwaysShareWithTitle));
            }
        } else if (!this.isNeverShare) {
            this.actionBar.setTitle(this.chatType == 0 ? LocaleController.getString("NewGroup", R.string.NewGroup) : LocaleController.getString("NewBroadcastList", R.string.NewBroadcastList));
        } else if (this.isGroup) {
            this.actionBar.setTitle(LocaleController.getString("NeverAllow", R.string.NeverAllow));
        } else {
            this.actionBar.setTitle(LocaleController.getString("NeverShareWithTitle", R.string.NeverShareWithTitle));
        }
        this.actionBar.setActionBarMenuOnItemClick(new C47481());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        if (this.chatType != 2) {
            this.doneButton.setScaleX(BitmapDescriptorFactory.HUE_RED);
            this.doneButton.setScaleY(BitmapDescriptorFactory.HUE_RED);
            this.doneButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
        }
        this.fragmentView = new ViewGroup(context) {
            protected boolean drawChild(Canvas canvas, View view, long j) {
                boolean drawChild = super.drawChild(canvas, view, j);
                if (view == GroupCreateActivity.this.listView || view == GroupCreateActivity.this.emptyView) {
                    GroupCreateActivity.this.parentLayout.drawHeaderShadow(canvas, GroupCreateActivity.this.scrollView.getMeasuredHeight());
                }
                return drawChild;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                GroupCreateActivity.this.scrollView.layout(0, 0, GroupCreateActivity.this.scrollView.getMeasuredWidth(), GroupCreateActivity.this.scrollView.getMeasuredHeight());
                GroupCreateActivity.this.listView.layout(0, GroupCreateActivity.this.scrollView.getMeasuredHeight(), GroupCreateActivity.this.listView.getMeasuredWidth(), GroupCreateActivity.this.scrollView.getMeasuredHeight() + GroupCreateActivity.this.listView.getMeasuredHeight());
                GroupCreateActivity.this.emptyView.layout(0, GroupCreateActivity.this.scrollView.getMeasuredHeight(), GroupCreateActivity.this.emptyView.getMeasuredWidth(), GroupCreateActivity.this.scrollView.getMeasuredHeight() + GroupCreateActivity.this.emptyView.getMeasuredHeight());
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i);
                int size2 = MeasureSpec.getSize(i2);
                setMeasuredDimension(size, size2);
                int dp = (AndroidUtilities.isTablet() || size2 > size) ? AndroidUtilities.dp(144.0f) : AndroidUtilities.dp(56.0f);
                GroupCreateActivity.this.scrollView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(dp, Integer.MIN_VALUE));
                GroupCreateActivity.this.listView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(size2 - GroupCreateActivity.this.scrollView.getMeasuredHeight(), 1073741824));
                GroupCreateActivity.this.emptyView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(size2 - GroupCreateActivity.this.scrollView.getMeasuredHeight(), 1073741824));
            }
        };
        ViewGroup viewGroup = (ViewGroup) this.fragmentView;
        this.scrollView = new ScrollView(context) {
            public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
                if (GroupCreateActivity.this.ignoreScrollEvent) {
                    GroupCreateActivity.this.ignoreScrollEvent = false;
                    return false;
                }
                rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
                rect.top += GroupCreateActivity.this.fieldY + AndroidUtilities.dp(20.0f);
                rect.bottom += GroupCreateActivity.this.fieldY + AndroidUtilities.dp(50.0f);
                return super.requestChildRectangleOnScreen(view, rect, z);
            }
        };
        this.scrollView.setVerticalScrollBarEnabled(false);
        AndroidUtilities.setScrollViewEdgeEffectColor(this.scrollView, Theme.getColor(Theme.key_windowBackgroundWhite));
        viewGroup.addView(this.scrollView);
        this.spansContainer = new SpansContainer(context);
        this.scrollView.addView(this.spansContainer, LayoutHelper.createFrame(-1, -2.0f));
        this.editText = new EditTextBoldCursor(context) {
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (GroupCreateActivity.this.currentDeletingSpan != null) {
                    GroupCreateActivity.this.currentDeletingSpan.cancelDeleteAnimation();
                    GroupCreateActivity.this.currentDeletingSpan = null;
                }
                return super.onTouchEvent(motionEvent);
            }
        };
        this.editText.setTextSize(1, 18.0f);
        this.editText.setHintColor(Theme.getColor(Theme.key_groupcreate_hintText));
        this.editText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.editText.setCursorColor(Theme.getColor(Theme.key_groupcreate_cursor));
        this.editText.setCursorWidth(1.5f);
        this.editText.setInputType(655536);
        this.editText.setSingleLine(true);
        this.editText.setBackgroundDrawable(null);
        this.editText.setVerticalScrollBarEnabled(false);
        this.editText.setHorizontalScrollBarEnabled(false);
        this.editText.setTextIsSelectable(false);
        this.editText.setPadding(0, 0, 0, 0);
        this.editText.setImeOptions(268435462);
        this.editText.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        this.spansContainer.addView(this.editText);
        if (this.chatType == 2) {
            this.editText.setHintText(LocaleController.getString("AddMutual", R.string.AddMutual));
        } else if (this.isAlwaysShare) {
            if (this.isGroup) {
                this.editText.setHintText(LocaleController.getString("AlwaysAllowPlaceholder", R.string.AlwaysAllowPlaceholder));
            } else {
                this.editText.setHintText(LocaleController.getString("AlwaysShareWithPlaceholder", R.string.AlwaysShareWithPlaceholder));
            }
        } else if (!this.isNeverShare) {
            this.editText.setHintText(LocaleController.getString("SendMessageTo", R.string.SendMessageTo));
        } else if (this.isGroup) {
            this.editText.setHintText(LocaleController.getString("NeverAllowPlaceholder", R.string.NeverAllowPlaceholder));
        } else {
            this.editText.setHintText(LocaleController.getString("NeverShareWithPlaceholder", R.string.NeverShareWithPlaceholder));
        }
        this.editText.setCustomSelectionActionModeCallback(new C47525());
        this.editText.setOnEditorActionListener(new C47536());
        this.editText.setOnKeyListener(new C47547());
        this.editText.addTextChangedListener(new C47558());
        this.emptyView = new EmptyTextProgressView(context);
        if (ContactsController.getInstance().isLoadingContacts()) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
        this.emptyView.setShowAtCenter(true);
        this.emptyView.setText(LocaleController.getString("NoContacts", R.string.NoContacts));
        viewGroup.addView(this.emptyView);
        LayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false);
        this.listView = new RecyclerListView(context);
        this.listView.setFastScrollEnabled();
        this.listView.setEmptyView(this.emptyView);
        RecyclerListView recyclerListView = this.listView;
        Adapter groupCreateAdapter = new GroupCreateAdapter(context);
        this.adapter = groupCreateAdapter;
        recyclerListView.setAdapter(groupCreateAdapter);
        this.listView.setLayoutManager(linearLayoutManager);
        this.listView.setVerticalScrollBarEnabled(false);
        RecyclerListView recyclerListView2 = this.listView;
        if (!LocaleController.isRTL) {
            i = 2;
        }
        recyclerListView2.setVerticalScrollbarPosition(i);
        RecyclerListView recyclerListView3 = this.listView;
        ItemDecoration groupCreateDividerItemDecoration = new GroupCreateDividerItemDecoration();
        this.itemDecoration = groupCreateDividerItemDecoration;
        recyclerListView3.addItemDecoration(groupCreateDividerItemDecoration);
        viewGroup.addView(this.listView);
        this.listView.setOnItemClickListener(new C47569());
        this.listView.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 1) {
                    AndroidUtilities.hideKeyboard(GroupCreateActivity.this.editText);
                }
            }
        });
        updateHint();
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int i2 = 0;
        if (i == NotificationCenter.contactsDidLoaded) {
            if (this.emptyView != null) {
                this.emptyView.showTextView();
            }
            if (this.adapter != null) {
                this.adapter.notifyDataSetChanged();
            }
        } else if (i == NotificationCenter.updateInterfaces) {
            if (this.listView != null) {
                int intValue = ((Integer) objArr[0]).intValue();
                int childCount = this.listView.getChildCount();
                if ((intValue & 2) != 0 || (intValue & 1) != 0 || (intValue & 4) != 0) {
                    while (i2 < childCount) {
                        View childAt = this.listView.getChildAt(i2);
                        if (childAt instanceof GroupCreateUserCell) {
                            ((GroupCreateUserCell) childAt).update(intValue);
                        }
                        i2++;
                    }
                }
            }
        } else if (i == NotificationCenter.chatDidCreated) {
            removeSelfFromStack();
        }
    }

    public int getContainerHeight() {
        return this.containerHeight;
    }

    public ThemeDescription[] getThemeDescriptions() {
        AnonymousClass11 anonymousClass11 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
                int childCount = GroupCreateActivity.this.listView.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = GroupCreateActivity.this.listView.getChildAt(i2);
                    if (childAt instanceof GroupCreateUserCell) {
                        ((GroupCreateUserCell) childAt).update(0);
                    }
                }
            }
        };
        r10 = new ThemeDescription[37];
        r10[11] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r10[12] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        r10[13] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        r10[14] = new ThemeDescription(this.editText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[15] = new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_groupcreate_hintText);
        r10[16] = new ThemeDescription(this.editText, ThemeDescription.FLAG_CURSORCOLOR, null, null, null, null, Theme.key_groupcreate_cursor);
        r10[17] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GroupCreateSectionCell.class}, null, null, null, Theme.key_graySection);
        r10[18] = new ThemeDescription(this.listView, 0, new Class[]{GroupCreateSectionCell.class}, new String[]{"drawable"}, null, null, null, Theme.key_groupcreate_sectionShadow);
        r10[19] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateSectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_groupcreate_sectionText);
        r10[20] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateUserCell.class}, new String[]{"textView"}, null, null, null, Theme.key_groupcreate_sectionText);
        r10[21] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateUserCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_groupcreate_checkbox);
        r10[22] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateUserCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_groupcreate_checkboxCheck);
        r10[23] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{GroupCreateUserCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_groupcreate_onlineText);
        r10[24] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{GroupCreateUserCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_groupcreate_offlineText);
        r10[25] = new ThemeDescription(this.listView, 0, new Class[]{GroupCreateUserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[26] = new ThemeDescription(null, 0, null, null, null, anonymousClass11, Theme.key_avatar_backgroundRed);
        r10[27] = new ThemeDescription(null, 0, null, null, null, anonymousClass11, Theme.key_avatar_backgroundOrange);
        r10[28] = new ThemeDescription(null, 0, null, null, null, anonymousClass11, Theme.key_avatar_backgroundViolet);
        r10[29] = new ThemeDescription(null, 0, null, null, null, anonymousClass11, Theme.key_avatar_backgroundGreen);
        r10[30] = new ThemeDescription(null, 0, null, null, null, anonymousClass11, Theme.key_avatar_backgroundCyan);
        r10[31] = new ThemeDescription(null, 0, null, null, null, anonymousClass11, Theme.key_avatar_backgroundBlue);
        r10[32] = new ThemeDescription(null, 0, null, null, null, anonymousClass11, Theme.key_avatar_backgroundPink);
        r10[33] = new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, Theme.key_avatar_backgroundGroupCreateSpanBlue);
        r10[34] = new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, Theme.key_groupcreate_spanBackground);
        r10[35] = new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, Theme.key_groupcreate_spanText);
        r10[36] = new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, Theme.key_avatar_backgroundBlue);
        return r10;
    }

    public void onClick(View view) {
        GroupCreateSpan groupCreateSpan = (GroupCreateSpan) view;
        if (groupCreateSpan.isDeleting()) {
            this.currentDeletingSpan = null;
            this.spansContainer.removeSpan(groupCreateSpan);
            updateHint();
            checkVisibleRows();
            return;
        }
        if (this.currentDeletingSpan != null) {
            this.currentDeletingSpan.cancelDeleteAnimation();
        }
        this.currentDeletingSpan = groupCreateSpan;
        groupCreateSpan.startDeleteAnimation();
    }

    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.contactsDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatDidCreated);
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatDidCreated);
    }

    public void onResume() {
        super.onResume();
        if (this.editText != null) {
            this.editText.requestFocus();
        }
    }

    public void setContainerHeight(int i) {
        this.containerHeight = i;
        if (this.spansContainer != null) {
            this.spansContainer.requestLayout();
        }
    }

    public void setDelegate(GroupCreateActivityDelegate groupCreateActivityDelegate) {
        this.delegate = groupCreateActivityDelegate;
    }
}
