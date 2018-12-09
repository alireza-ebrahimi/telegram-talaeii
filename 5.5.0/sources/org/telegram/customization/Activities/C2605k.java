package org.telegram.customization.Activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import org.ir.talaeii.R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.CustomCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import utils.p178a.C3791b;

/* renamed from: org.telegram.customization.Activities.k */
public class C2605k extends BaseFragment {
    /* renamed from: a */
    int f8694a;
    /* renamed from: b */
    int f8695b;
    /* renamed from: c */
    int f8696c;
    /* renamed from: d */
    int f8697d;
    /* renamed from: e */
    private C2604a f8698e;
    /* renamed from: f */
    private RecyclerListView f8699f;
    /* renamed from: g */
    private int f8700g;
    /* renamed from: h */
    private volatile boolean f8701h = false;

    /* renamed from: org.telegram.customization.Activities.k$1 */
    class C26021 extends ActionBarMenuOnItemClick {
        /* renamed from: a */
        final /* synthetic */ C2605k f8690a;

        C26021(C2605k c2605k) {
            this.f8690a = c2605k;
        }

        public void onItemClick(int i) {
            if (i == -1) {
                this.f8690a.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.k$2 */
    class C26032 implements OnItemClickListener {
        /* renamed from: a */
        final /* synthetic */ C2605k f8691a;

        C26032(C2605k c2605k) {
            this.f8691a = c2605k;
        }

        public void onItemClick(View view, int i) {
            if (this.f8691a.getParentActivity() != null) {
                if (i == this.f8691a.f8694a) {
                    C3791b.m14003j(this.f8691a.getParentActivity(), TtmlNode.ANONYMOUS_REGION_ID);
                    C3791b.m13961d(this.f8691a.getParentActivity(), 1);
                } else if (i == this.f8691a.f8697d) {
                    C3791b.m14003j(this.f8691a.getParentActivity(), LocaleController.getString("un_silent_chats", R.string.un_silent_chats));
                    C3791b.m13961d(this.f8691a.getParentActivity(), 4);
                } else if (i == this.f8691a.f8696c) {
                    C3791b.m14003j(this.f8691a.getParentActivity(), LocaleController.getString("silent_chats", R.string.silent_chats));
                    C3791b.m13961d(this.f8691a.getParentActivity(), 3);
                } else if (i == this.f8691a.f8695b) {
                    C3791b.m14003j(this.f8691a.getParentActivity(), LocaleController.getString("unreadChats", R.string.unreadChats));
                    C3791b.m13961d(this.f8691a.getParentActivity(), 2);
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, Integer.valueOf(-1));
                this.f8691a.finishFragment(true);
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.k$a */
    private class C2604a extends SelectionAdapter {
        /* renamed from: a */
        final /* synthetic */ C2605k f8692a;
        /* renamed from: b */
        private Context f8693b;

        public C2604a(C2605k c2605k, Context context) {
            this.f8692a = c2605k;
            this.f8693b = context;
        }

        public int getItemCount() {
            return this.f8692a.f8700g;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            viewHolder.getAdapterPosition();
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    CustomCell customCell = (CustomCell) viewHolder.itemView;
                    TLObject user;
                    if (i == this.f8692a.f8694a) {
                        user = new User();
                        user.first_name = LocaleController.getString("All", R.string.All);
                        customCell.setData(user, LocaleController.getString("All", R.string.All), LocaleController.getString("AllDialogs", R.string.AllDialogs), R.drawable.approval);
                        return;
                    } else if (i == this.f8692a.f8695b) {
                        user = new User();
                        user.first_name = LocaleController.getString("unreadChats", R.string.unreadChats);
                        customCell.setData(user, LocaleController.getString("unreadChats", R.string.unreadChats), LocaleController.getString("unreadChats1", R.string.unreadChats1), R.drawable.approval);
                        return;
                    } else if (i == this.f8692a.f8697d) {
                        user = new User();
                        user.first_name = LocaleController.getString("notifOn", R.string.notifOn);
                        customCell.setData(user, LocaleController.getString("notifOn", R.string.notifOn), LocaleController.getString("notifOnChats", R.string.notifOnChats), R.drawable.approval);
                        return;
                    } else if (i == this.f8692a.f8696c) {
                        user = new User();
                        user.first_name = LocaleController.getString("mutedChats", R.string.mutedChats);
                        customCell.setData(user, LocaleController.getString("mutedChats", R.string.mutedChats), LocaleController.getString("mutedChats", R.string.mutedChats1), R.drawable.approval);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View customCell;
            switch (i) {
                case 0:
                    customCell = new CustomCell(this.f8693b, 0, 0, false);
                    customCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    customCell = new CustomCell(this.f8693b, 0, 0, false);
                    break;
            }
            return new Holder(customCell);
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("CategoryDialogs", R.string.CategoryDialogs));
        this.actionBar.setActionBarMenuOnItemClick(new C26021(this));
        this.f8698e = new C2604a(this, context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.f8699f = new RecyclerListView(context);
        this.f8699f.setVerticalScrollBarEnabled(false);
        this.f8699f.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.f8699f, LayoutHelper.createFrame(-1, -1.0f));
        this.f8699f.setAdapter(this.f8698e);
        this.f8699f.setOnItemClickListener(new C26032(this));
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[12];
        r9[0] = new ThemeDescription(this.f8699f, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[3] = new ThemeDescription(this.f8699f, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[7] = new ThemeDescription(this.f8699f, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[8] = new ThemeDescription(this.f8699f, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[9] = new ThemeDescription(this.f8699f, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        r9[10] = new ThemeDescription(this.f8699f, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[11] = new ThemeDescription(this.f8699f, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        return r9;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.f8700g = 0;
        int i = this.f8700g;
        this.f8700g = i + 1;
        this.f8694a = i;
        i = this.f8700g;
        this.f8700g = i + 1;
        this.f8695b = i;
        i = this.f8700g;
        this.f8700g = i + 1;
        this.f8696c = i;
        i = this.f8700g;
        this.f8700g = i + 1;
        this.f8697d = i;
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public void onResume() {
        super.onResume();
        if (this.f8698e != null) {
            this.f8698e.notifyDataSetChanged();
        }
    }
}
