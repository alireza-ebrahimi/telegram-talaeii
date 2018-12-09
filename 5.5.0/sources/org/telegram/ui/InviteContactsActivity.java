package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.ContactsController.Contact;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Cells.GroupCreateSectionCell;
import org.telegram.ui.Cells.InviteTextCell;
import org.telegram.ui.Cells.InviteUserCell;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.GroupCreateDividerItemDecoration;
import org.telegram.ui.Components.GroupCreateSpan;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class InviteContactsActivity extends BaseFragment implements OnClickListener, NotificationCenterDelegate {
    private InviteAdapter adapter;
    private ArrayList<GroupCreateSpan> allSpans = new ArrayList();
    private int containerHeight;
    private TextView counterTextView;
    private FrameLayout counterView;
    private GroupCreateSpan currentDeletingSpan;
    private GroupCreateDividerItemDecoration decoration;
    private EditTextBoldCursor editText;
    private EmptyTextProgressView emptyView;
    private int fieldY;
    private boolean ignoreScrollEvent;
    private TextView infoTextView;
    private RecyclerListView listView;
    private ArrayList<Contact> phoneBookContacts;
    private ScrollView scrollView;
    private boolean searchWas;
    private boolean searching;
    private HashMap<String, GroupCreateSpan> selectedContacts = new HashMap();
    private SpansContainer spansContainer;
    private TextView textView;

    /* renamed from: org.telegram.ui.InviteContactsActivity$1 */
    class C48081 extends ActionBarMenuOnItemClick {
        C48081() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                InviteContactsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.InviteContactsActivity$5 */
    class C48125 implements Callback {
        C48125() {
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

    /* renamed from: org.telegram.ui.InviteContactsActivity$6 */
    class C48136 implements OnKeyListener {
        private boolean wasEmpty;

        C48136() {
        }

        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == 0) {
                this.wasEmpty = InviteContactsActivity.this.editText.length() == 0;
            } else if (keyEvent.getAction() == 1 && this.wasEmpty && !InviteContactsActivity.this.allSpans.isEmpty()) {
                InviteContactsActivity.this.spansContainer.removeSpan((GroupCreateSpan) InviteContactsActivity.this.allSpans.get(InviteContactsActivity.this.allSpans.size() - 1));
                InviteContactsActivity.this.updateHint();
                InviteContactsActivity.this.checkVisibleRows();
                return true;
            }
            return false;
        }
    }

    /* renamed from: org.telegram.ui.InviteContactsActivity$7 */
    class C48147 implements TextWatcher {
        C48147() {
        }

        public void afterTextChanged(Editable editable) {
            if (InviteContactsActivity.this.editText.length() != 0) {
                InviteContactsActivity.this.searching = true;
                InviteContactsActivity.this.searchWas = true;
                InviteContactsActivity.this.adapter.setSearching(true);
                InviteContactsActivity.this.adapter.searchDialogs(InviteContactsActivity.this.editText.getText().toString());
                InviteContactsActivity.this.listView.setFastScrollVisible(false);
                InviteContactsActivity.this.listView.setVerticalScrollBarEnabled(true);
                InviteContactsActivity.this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
                return;
            }
            InviteContactsActivity.this.closeSearch();
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.InviteContactsActivity$8 */
    class C48158 implements OnItemClickListener {
        C48158() {
        }

        public void onItemClick(View view, int i) {
            if (i == 0 && !InviteContactsActivity.this.searching) {
                try {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("text/plain");
                    CharSequence inviteText = ContactsController.getInstance().getInviteText(0);
                    intent.putExtra("android.intent.extra.TEXT", inviteText);
                    InviteContactsActivity.this.getParentActivity().startActivityForResult(Intent.createChooser(intent, inviteText), ChatActivity.startAllServices);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            } else if (view instanceof InviteUserCell) {
                InviteUserCell inviteUserCell = (InviteUserCell) view;
                Contact contact = inviteUserCell.getContact();
                if (contact != null) {
                    boolean containsKey = InviteContactsActivity.this.selectedContacts.containsKey(contact.key);
                    if (containsKey) {
                        InviteContactsActivity.this.spansContainer.removeSpan((GroupCreateSpan) InviteContactsActivity.this.selectedContacts.get(contact.key));
                    } else {
                        GroupCreateSpan groupCreateSpan = new GroupCreateSpan(InviteContactsActivity.this.editText.getContext(), contact);
                        InviteContactsActivity.this.spansContainer.addSpan(groupCreateSpan);
                        groupCreateSpan.setOnClickListener(InviteContactsActivity.this);
                    }
                    InviteContactsActivity.this.updateHint();
                    if (InviteContactsActivity.this.searching || InviteContactsActivity.this.searchWas) {
                        AndroidUtilities.showKeyboard(InviteContactsActivity.this.editText);
                    } else {
                        inviteUserCell.setChecked(!containsKey, true);
                    }
                    if (InviteContactsActivity.this.editText.length() > 0) {
                        InviteContactsActivity.this.editText.setText(null);
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.InviteContactsActivity$9 */
    class C48169 extends OnScrollListener {
        C48169() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 1) {
                AndroidUtilities.hideKeyboard(InviteContactsActivity.this.editText);
            }
        }
    }

    public class InviteAdapter extends SelectionAdapter {
        private Context context;
        private ArrayList<Contact> searchResult = new ArrayList();
        private ArrayList<CharSequence> searchResultNames = new ArrayList();
        private Timer searchTimer;
        private boolean searching;

        public InviteAdapter(Context context) {
            this.context = context;
        }

        private void updateSearchResults(final ArrayList<Contact> arrayList, final ArrayList<CharSequence> arrayList2) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    InviteAdapter.this.searchResult = arrayList;
                    InviteAdapter.this.searchResultNames = arrayList2;
                    InviteAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public int getItemCount() {
            return this.searching ? this.searchResult.size() : InviteContactsActivity.this.phoneBookContacts.size() + 1;
        }

        public int getItemViewType(int i) {
            return (this.searching || i != 0) ? 0 : 1;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void notifyDataSetChanged() {
            boolean z = false;
            super.notifyDataSetChanged();
            int itemCount = getItemCount();
            InviteContactsActivity.this.emptyView.setVisibility(itemCount == 1 ? 0 : 4);
            GroupCreateDividerItemDecoration access$2800 = InviteContactsActivity.this.decoration;
            if (itemCount == 1) {
                z = true;
            }
            access$2800.setSingle(z);
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    Contact contact;
                    CharSequence charSequence;
                    InviteUserCell inviteUserCell = (InviteUserCell) viewHolder.itemView;
                    if (this.searching) {
                        contact = (Contact) this.searchResult.get(i);
                        charSequence = (CharSequence) this.searchResultNames.get(i);
                    } else {
                        contact = (Contact) InviteContactsActivity.this.phoneBookContacts.get(i - 1);
                        charSequence = null;
                    }
                    inviteUserCell.setUser(contact, charSequence);
                    inviteUserCell.setChecked(InviteContactsActivity.this.selectedContacts.containsKey(contact.key), false);
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View inviteTextCell;
            switch (i) {
                case 1:
                    inviteTextCell = new InviteTextCell(this.context);
                    ((InviteTextCell) inviteTextCell).setTextAndIcon(LocaleController.getString("ShareTelegram", R.string.ShareTelegram), R.drawable.share);
                    break;
                default:
                    inviteTextCell = new InviteUserCell(this.context, true);
                    break;
            }
            return new Holder(inviteTextCell);
        }

        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof InviteUserCell) {
                ((InviteUserCell) viewHolder.itemView).recycle();
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
                notifyDataSetChanged();
                return;
            }
            this.searchTimer = new Timer();
            this.searchTimer.schedule(new TimerTask() {

                /* renamed from: org.telegram.ui.InviteContactsActivity$InviteAdapter$1$1 */
                class C48181 implements Runnable {

                    /* renamed from: org.telegram.ui.InviteContactsActivity$InviteAdapter$1$1$1 */
                    class C48171 implements Runnable {
                        C48171() {
                        }

                        public void run() {
                            String toLowerCase = str.trim().toLowerCase();
                            if (toLowerCase.length() == 0) {
                                InviteAdapter.this.updateSearchResults(new ArrayList(), new ArrayList());
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
                            for (int i = 0; i < InviteContactsActivity.this.phoneBookContacts.size(); i++) {
                                Contact contact = (Contact) InviteContactsActivity.this.phoneBookContacts.get(i);
                                String toLowerCase2 = ContactsController.formatName(contact.first_name, contact.last_name).toLowerCase();
                                toLowerCase = LocaleController.getInstance().getTranslitString(toLowerCase2);
                                if (toLowerCase2.equals(toLowerCase)) {
                                    toLowerCase = null;
                                }
                                Object obj = null;
                                for (String str2 : strArr) {
                                    if (toLowerCase2.startsWith(str2) || toLowerCase2.contains(" " + str2) || (r2 != null && (r2.startsWith(str2) || r2.contains(" " + str2)))) {
                                        obj = 1;
                                    }
                                    if (obj != null) {
                                        arrayList2.add(AndroidUtilities.generateSearchName(contact.first_name, contact.last_name, str2));
                                        arrayList.add(contact);
                                        break;
                                    }
                                }
                            }
                            InviteAdapter.this.updateSearchResults(arrayList, arrayList2);
                        }
                    }

                    C48181() {
                    }

                    public void run() {
                        Utilities.searchQueue.postRunnable(new C48171());
                    }
                }

                public void run() {
                    try {
                        InviteAdapter.this.searchTimer.cancel();
                        InviteAdapter.this.searchTimer = null;
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    AndroidUtilities.runOnUIThread(new C48181());
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

        /* renamed from: org.telegram.ui.InviteContactsActivity$SpansContainer$1 */
        class C48211 extends AnimatorListenerAdapter {
            C48211() {
            }

            public void onAnimationEnd(Animator animator) {
                SpansContainer.this.addingSpan = null;
                SpansContainer.this.currentAnimation = null;
                SpansContainer.this.animationStarted = false;
                InviteContactsActivity.this.editText.setAllowDrawCursor(true);
            }
        }

        public SpansContainer(Context context) {
            super(context);
        }

        public void addSpan(GroupCreateSpan groupCreateSpan) {
            InviteContactsActivity.this.allSpans.add(groupCreateSpan);
            InviteContactsActivity.this.selectedContacts.put(groupCreateSpan.getKey(), groupCreateSpan);
            InviteContactsActivity.this.editText.setHintVisible(false);
            if (this.currentAnimation != null) {
                this.currentAnimation.setupEndValues();
                this.currentAnimation.cancel();
            }
            this.animationStarted = false;
            this.currentAnimation = new AnimatorSet();
            this.currentAnimation.addListener(new C48211());
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
            InviteContactsActivity.this.editText.measure(MeasureSpec.makeMeasureSpec(dp - dp2, 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(32.0f), 1073741824));
            if (!this.animationStarted) {
                dp3 += AndroidUtilities.dp(44.0f);
                dp2 += AndroidUtilities.dp(16.0f);
                InviteContactsActivity.this.fieldY = i5;
                if (this.currentAnimation != null) {
                    if (InviteContactsActivity.this.containerHeight != i5 + AndroidUtilities.dp(44.0f)) {
                        this.animators.add(ObjectAnimator.ofInt(InviteContactsActivity.this, "containerHeight", new int[]{i5 + AndroidUtilities.dp(44.0f)}));
                    }
                    if (InviteContactsActivity.this.editText.getTranslationX() != ((float) dp2)) {
                        this.animators.add(ObjectAnimator.ofFloat(InviteContactsActivity.this.editText, "translationX", new float[]{(float) dp2}));
                    }
                    if (InviteContactsActivity.this.editText.getTranslationY() != ((float) InviteContactsActivity.this.fieldY)) {
                        this.animators.add(ObjectAnimator.ofFloat(InviteContactsActivity.this.editText, "translationY", new float[]{(float) InviteContactsActivity.this.fieldY}));
                    }
                    InviteContactsActivity.this.editText.setAllowDrawCursor(false);
                    this.currentAnimation.playTogether(this.animators);
                    this.currentAnimation.start();
                    this.animationStarted = true;
                } else {
                    InviteContactsActivity.this.containerHeight = dp3;
                    InviteContactsActivity.this.editText.setTranslationX((float) dp2);
                    InviteContactsActivity.this.editText.setTranslationY((float) InviteContactsActivity.this.fieldY);
                }
            } else if (!(this.currentAnimation == null || InviteContactsActivity.this.ignoreScrollEvent || this.removingSpan != null)) {
                InviteContactsActivity.this.editText.bringPointIntoView(InviteContactsActivity.this.editText.getSelectionStart());
            }
            setMeasuredDimension(size, InviteContactsActivity.this.containerHeight);
        }

        public void removeSpan(final GroupCreateSpan groupCreateSpan) {
            InviteContactsActivity.this.ignoreScrollEvent = true;
            InviteContactsActivity.this.selectedContacts.remove(groupCreateSpan.getKey());
            InviteContactsActivity.this.allSpans.remove(groupCreateSpan);
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
                    InviteContactsActivity.this.editText.setAllowDrawCursor(true);
                    if (InviteContactsActivity.this.allSpans.isEmpty()) {
                        InviteContactsActivity.this.editText.setHintVisible(true);
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

    private void checkVisibleRows() {
        int childCount = this.listView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof InviteUserCell) {
                InviteUserCell inviteUserCell = (InviteUserCell) childAt;
                Contact contact = inviteUserCell.getContact();
                if (contact != null) {
                    inviteUserCell.setChecked(this.selectedContacts.containsKey(contact.key), true);
                }
            }
        }
    }

    private void closeSearch() {
        this.searching = false;
        this.searchWas = false;
        this.adapter.setSearching(false);
        this.adapter.searchDialogs(null);
        this.listView.setFastScrollVisible(true);
        this.listView.setVerticalScrollBarEnabled(false);
        this.emptyView.setText(LocaleController.getString("NoContacts", R.string.NoContacts));
    }

    private void fetchContacts() {
        this.phoneBookContacts = new ArrayList(ContactsController.getInstance().phoneBookContacts);
        Collections.sort(this.phoneBookContacts, new Comparator<Contact>() {
            public int compare(Contact contact, Contact contact2) {
                return contact.imported > contact2.imported ? -1 : contact.imported < contact2.imported ? 1 : 0;
            }
        });
        if (this.emptyView != null) {
            this.emptyView.showTextView();
        }
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
    }

    private void updateHint() {
        if (this.selectedContacts.isEmpty()) {
            this.infoTextView.setVisibility(0);
            this.counterView.setVisibility(4);
            return;
        }
        this.infoTextView.setVisibility(4);
        this.counterView.setVisibility(0);
        this.counterTextView.setText(String.format("%d", new Object[]{Integer.valueOf(this.selectedContacts.size())}));
    }

    public View createView(Context context) {
        this.searching = false;
        this.searchWas = false;
        this.allSpans.clear();
        this.selectedContacts.clear();
        this.currentDeletingSpan = null;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("InviteFriends", R.string.InviteFriends));
        this.actionBar.setActionBarMenuOnItemClick(new C48081());
        this.fragmentView = new ViewGroup(context) {
            protected boolean drawChild(Canvas canvas, View view, long j) {
                boolean drawChild = super.drawChild(canvas, view, j);
                if (view == InviteContactsActivity.this.listView || view == InviteContactsActivity.this.emptyView) {
                    InviteContactsActivity.this.parentLayout.drawHeaderShadow(canvas, InviteContactsActivity.this.scrollView.getMeasuredHeight());
                }
                return drawChild;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                InviteContactsActivity.this.scrollView.layout(0, 0, InviteContactsActivity.this.scrollView.getMeasuredWidth(), InviteContactsActivity.this.scrollView.getMeasuredHeight());
                InviteContactsActivity.this.listView.layout(0, InviteContactsActivity.this.scrollView.getMeasuredHeight(), InviteContactsActivity.this.listView.getMeasuredWidth(), InviteContactsActivity.this.scrollView.getMeasuredHeight() + InviteContactsActivity.this.listView.getMeasuredHeight());
                InviteContactsActivity.this.emptyView.layout(0, InviteContactsActivity.this.scrollView.getMeasuredHeight() + AndroidUtilities.dp(72.0f), InviteContactsActivity.this.emptyView.getMeasuredWidth(), InviteContactsActivity.this.scrollView.getMeasuredHeight() + InviteContactsActivity.this.emptyView.getMeasuredHeight());
                int measuredHeight = (i4 - i2) - InviteContactsActivity.this.infoTextView.getMeasuredHeight();
                InviteContactsActivity.this.infoTextView.layout(0, measuredHeight, InviteContactsActivity.this.infoTextView.getMeasuredWidth(), InviteContactsActivity.this.infoTextView.getMeasuredHeight() + measuredHeight);
                measuredHeight = (i4 - i2) - InviteContactsActivity.this.counterView.getMeasuredHeight();
                InviteContactsActivity.this.counterView.layout(0, measuredHeight, InviteContactsActivity.this.counterView.getMeasuredWidth(), InviteContactsActivity.this.counterView.getMeasuredHeight() + measuredHeight);
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i);
                int size2 = MeasureSpec.getSize(i2);
                setMeasuredDimension(size, size2);
                int dp = (AndroidUtilities.isTablet() || size2 > size) ? AndroidUtilities.dp(144.0f) : AndroidUtilities.dp(56.0f);
                InviteContactsActivity.this.infoTextView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(dp, Integer.MIN_VALUE));
                InviteContactsActivity.this.counterView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
                int measuredHeight = InviteContactsActivity.this.infoTextView.getVisibility() == 0 ? InviteContactsActivity.this.infoTextView.getMeasuredHeight() : InviteContactsActivity.this.counterView.getMeasuredHeight();
                InviteContactsActivity.this.scrollView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(dp, Integer.MIN_VALUE));
                InviteContactsActivity.this.listView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec((size2 - InviteContactsActivity.this.scrollView.getMeasuredHeight()) - measuredHeight, 1073741824));
                InviteContactsActivity.this.emptyView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec((size2 - InviteContactsActivity.this.scrollView.getMeasuredHeight()) - AndroidUtilities.dp(72.0f), 1073741824));
            }
        };
        ViewGroup viewGroup = (ViewGroup) this.fragmentView;
        this.scrollView = new ScrollView(context) {
            public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
                if (InviteContactsActivity.this.ignoreScrollEvent) {
                    InviteContactsActivity.this.ignoreScrollEvent = false;
                    return false;
                }
                rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
                rect.top += InviteContactsActivity.this.fieldY + AndroidUtilities.dp(20.0f);
                rect.bottom += InviteContactsActivity.this.fieldY + AndroidUtilities.dp(50.0f);
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
                if (InviteContactsActivity.this.currentDeletingSpan != null) {
                    InviteContactsActivity.this.currentDeletingSpan.cancelDeleteAnimation();
                    InviteContactsActivity.this.currentDeletingSpan = null;
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
        this.editText.setHintText(LocaleController.getString("SearchFriends", R.string.SearchFriends));
        this.editText.setCustomSelectionActionModeCallback(new C48125());
        this.editText.setOnKeyListener(new C48136());
        this.editText.addTextChangedListener(new C48147());
        this.emptyView = new EmptyTextProgressView(context);
        if (ContactsController.getInstance().isLoadingContacts()) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
        this.emptyView.setText(LocaleController.getString("NoContacts", R.string.NoContacts));
        viewGroup.addView(this.emptyView);
        LayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false);
        this.listView = new RecyclerListView(context);
        this.listView.setEmptyView(this.emptyView);
        RecyclerListView recyclerListView = this.listView;
        Adapter inviteAdapter = new InviteAdapter(context);
        this.adapter = inviteAdapter;
        recyclerListView.setAdapter(inviteAdapter);
        this.listView.setLayoutManager(linearLayoutManager);
        this.listView.setVerticalScrollBarEnabled(true);
        this.listView.setVerticalScrollbarPosition(LocaleController.isRTL ? 1 : 2);
        RecyclerListView recyclerListView2 = this.listView;
        ItemDecoration groupCreateDividerItemDecoration = new GroupCreateDividerItemDecoration();
        this.decoration = groupCreateDividerItemDecoration;
        recyclerListView2.addItemDecoration(groupCreateDividerItemDecoration);
        viewGroup.addView(this.listView);
        this.listView.setOnItemClickListener(new C48158());
        this.listView.setOnScrollListener(new C48169());
        this.infoTextView = new TextView(context);
        this.infoTextView.setBackgroundColor(Theme.getColor(Theme.key_contacts_inviteBackground));
        this.infoTextView.setTextColor(Theme.getColor(Theme.key_contacts_inviteText));
        this.infoTextView.setGravity(17);
        this.infoTextView.setText(LocaleController.getString("InviteFriendsHelp", R.string.InviteFriendsHelp));
        this.infoTextView.setTextSize(1, 13.0f);
        this.infoTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.infoTextView.setPadding(AndroidUtilities.dp(17.0f), AndroidUtilities.dp(9.0f), AndroidUtilities.dp(17.0f), AndroidUtilities.dp(9.0f));
        viewGroup.addView(this.infoTextView, LayoutHelper.createFrame(-1, -2, 83));
        this.counterView = new FrameLayout(context);
        this.counterView.setBackgroundColor(Theme.getColor(Theme.key_contacts_inviteBackground));
        this.counterView.setVisibility(4);
        viewGroup.addView(this.counterView, LayoutHelper.createFrame(-1, 48, 83));
        this.counterView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    int i = 0;
                    int i2 = 0;
                    while (i < InviteContactsActivity.this.allSpans.size()) {
                        Contact contact = ((GroupCreateSpan) InviteContactsActivity.this.allSpans.get(i)).getContact();
                        if (stringBuilder.length() != 0) {
                            stringBuilder.append(';');
                        }
                        stringBuilder.append((String) contact.phones.get(0));
                        int i3 = (i == 0 && InviteContactsActivity.this.allSpans.size() == 1) ? contact.imported : i2;
                        i++;
                        i2 = i3;
                    }
                    Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + stringBuilder.toString()));
                    intent.putExtra("sms_body", ContactsController.getInstance().getInviteText(i2));
                    InviteContactsActivity.this.getParentActivity().startActivityForResult(intent, ChatActivity.startAllServices);
                    MediaController.getInstance().startSmsObserver();
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                InviteContactsActivity.this.finishFragment();
            }
        });
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(0);
        this.counterView.addView(linearLayout, LayoutHelper.createFrame(-2, -1, 17));
        this.counterTextView = new TextView(context);
        this.counterTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.counterTextView.setTextSize(1, 14.0f);
        this.counterTextView.setTextColor(Theme.getColor(Theme.key_contacts_inviteBackground));
        this.counterTextView.setGravity(17);
        this.counterTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(10.0f), -1));
        this.counterTextView.setMinWidth(AndroidUtilities.dp(20.0f));
        this.counterTextView.setPadding(AndroidUtilities.dp(6.0f), 0, AndroidUtilities.dp(6.0f), AndroidUtilities.dp(1.0f));
        linearLayout.addView(this.counterTextView, LayoutHelper.createLinear(-2, 20, 16, 0, 0, 10, 0));
        this.textView = new TextView(context);
        this.textView.setTextSize(1, 14.0f);
        this.textView.setTextColor(Theme.getColor(Theme.key_contacts_inviteText));
        this.textView.setGravity(17);
        this.textView.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        this.textView.setText(LocaleController.getString("InviteToTelegram", R.string.InviteToTelegram).toUpperCase());
        this.textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        linearLayout.addView(this.textView, LayoutHelper.createLinear(-2, -2, 16));
        updateHint();
        this.adapter.notifyDataSetChanged();
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.contactsImported) {
            fetchContacts();
        }
    }

    public int getContainerHeight() {
        return this.containerHeight;
    }

    public ThemeDescription[] getThemeDescriptions() {
        AnonymousClass12 anonymousClass12 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
                int childCount = InviteContactsActivity.this.listView.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = InviteContactsActivity.this.listView.getChildAt(i2);
                    if (childAt instanceof InviteUserCell) {
                        ((InviteUserCell) childAt).update(0);
                    }
                }
            }
        };
        r10 = new ThemeDescription[44];
        r10[11] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r10[12] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        r10[13] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        r10[14] = new ThemeDescription(this.editText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[15] = new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_groupcreate_hintText);
        r10[16] = new ThemeDescription(this.editText, ThemeDescription.FLAG_CURSORCOLOR, null, null, null, null, Theme.key_groupcreate_cursor);
        r10[17] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GroupCreateSectionCell.class}, null, null, null, Theme.key_graySection);
        r10[18] = new ThemeDescription(this.listView, 0, new Class[]{GroupCreateSectionCell.class}, new String[]{"drawable"}, null, null, null, Theme.key_groupcreate_sectionShadow);
        r10[19] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateSectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_groupcreate_sectionText);
        r10[20] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{InviteUserCell.class}, new String[]{"textView"}, null, null, null, Theme.key_groupcreate_sectionText);
        r10[21] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{InviteUserCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_groupcreate_checkbox);
        r10[22] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{InviteUserCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_groupcreate_checkboxCheck);
        r10[23] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{InviteUserCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_groupcreate_onlineText);
        r10[24] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{InviteUserCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_groupcreate_offlineText);
        r10[25] = new ThemeDescription(this.listView, 0, new Class[]{InviteUserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[26] = new ThemeDescription(null, 0, null, null, null, anonymousClass12, Theme.key_avatar_backgroundRed);
        r10[27] = new ThemeDescription(null, 0, null, null, null, anonymousClass12, Theme.key_avatar_backgroundOrange);
        r10[28] = new ThemeDescription(null, 0, null, null, null, anonymousClass12, Theme.key_avatar_backgroundViolet);
        r10[29] = new ThemeDescription(null, 0, null, null, null, anonymousClass12, Theme.key_avatar_backgroundGreen);
        r10[30] = new ThemeDescription(null, 0, null, null, null, anonymousClass12, Theme.key_avatar_backgroundCyan);
        r10[31] = new ThemeDescription(null, 0, null, null, null, anonymousClass12, Theme.key_avatar_backgroundBlue);
        r10[32] = new ThemeDescription(null, 0, null, null, null, anonymousClass12, Theme.key_avatar_backgroundPink);
        r10[33] = new ThemeDescription(this.listView, 0, new Class[]{InviteTextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[34] = new ThemeDescription(this.listView, 0, new Class[]{InviteTextCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        r10[35] = new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, Theme.key_avatar_backgroundGroupCreateSpanBlue);
        r10[36] = new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, Theme.key_groupcreate_spanBackground);
        r10[37] = new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, Theme.key_groupcreate_spanText);
        r10[38] = new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, Theme.key_avatar_backgroundBlue);
        r10[39] = new ThemeDescription(this.infoTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_contacts_inviteText);
        r10[40] = new ThemeDescription(this.infoTextView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_contacts_inviteBackground);
        r10[41] = new ThemeDescription(this.counterView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_contacts_inviteBackground);
        r10[42] = new ThemeDescription(this.counterTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_contacts_inviteBackground);
        r10[43] = new ThemeDescription(this.textView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_contacts_inviteText);
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
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.contactsImported);
        fetchContacts();
        if (!UserConfig.contactsReimported) {
            ContactsController.getInstance().forceImportContacts();
            UserConfig.contactsReimported = true;
            UserConfig.saveConfig(false);
        }
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsImported);
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
}
