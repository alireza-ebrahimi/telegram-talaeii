package org.telegram.customization.Activities;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.C2070e;
import com.mohamadamin.persianmaterialdatetimepicker.time.C2070e.C2069c;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import org.ir.talaeii.R;
import org.telegram.customization.service.DownloadManagerService;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.CustomCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import utils.p178a.C3791b;

/* renamed from: org.telegram.customization.Activities.j */
public class C2601j extends BaseFragment implements C2069c {
    /* renamed from: a */
    String f8684a = "انتخاب کنید";
    /* renamed from: b */
    String f8685b = "انتخاب کنید";
    /* renamed from: c */
    private C2600a f8686c;
    /* renamed from: d */
    private RecyclerListView f8687d;
    /* renamed from: e */
    private int f8688e = 5;
    /* renamed from: f */
    private volatile boolean f8689f = false;

    /* renamed from: org.telegram.customization.Activities.j$1 */
    class C25971 extends ActionBarMenuOnItemClick {
        /* renamed from: a */
        final /* synthetic */ C2601j f8677a;

        C25971(C2601j c2601j) {
            this.f8677a = c2601j;
        }

        public void onItemClick(int i) {
            if (i == -1) {
                this.f8677a.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.j$2 */
    class C25982 implements C2069c {
        /* renamed from: a */
        final /* synthetic */ C2601j f8678a;

        C25982(C2601j c2601j) {
            this.f8678a = c2601j;
        }

        /* renamed from: a */
        public void mo3448a(RadialPickerLayout radialPickerLayout, int i, int i2) {
            this.f8678a.f8685b = i + ":" + i2;
            this.f8678a.f8686c.notifyDataSetChanged();
            C3791b.m13963d(this.f8678a.getParentActivity(), this.f8678a.f8685b);
            if (C3791b.m13918V(this.f8678a.getParentActivity())) {
                DownloadManagerService.m13182b(ApplicationLoader.applicationContext);
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.j$a */
    private class C2600a extends SelectionAdapter {
        /* renamed from: a */
        final /* synthetic */ C2601j f8682a;
        /* renamed from: b */
        private Context f8683b;

        public C2600a(C2601j c2601j, Context context) {
            this.f8682a = c2601j;
            this.f8683b = context;
        }

        public int getItemCount() {
            return this.f8682a.f8688e;
        }

        public int getItemViewType(int i) {
            return i == 0 ? 100 : i == 3 ? Callback.DEFAULT_DRAG_ANIMATION_DURATION : i == 4 ? 300 : 0;
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
                    if (i == 1) {
                        user = new User();
                        user.first_name = LocaleController.getString("startTime", R.string.startTime);
                        customCell.setData(user, LocaleController.getString("startTime", R.string.startTime), this.f8682a.f8684a, R.drawable.approval);
                        return;
                    } else if (i == 2) {
                        user = new User();
                        user.first_name = LocaleController.getString("endTime", R.string.endTime);
                        customCell.setData(user, LocaleController.getString("endTime", R.string.endTime), this.f8682a.f8685b, R.drawable.approval);
                        return;
                    } else {
                        return;
                    }
                case 100:
                    ((TextCheckCell) viewHolder.itemView).setTextAndCheck(LocaleController.getString("scheduleDownloads", R.string.scheduleDownloads), C3791b.m13918V(this.f8683b), true);
                    return;
                case Callback.DEFAULT_DRAG_ANIMATION_DURATION /*200*/:
                    ((TextCheckCell) viewHolder.itemView).setTextAndCheck(LocaleController.getString("turnWifiOn", R.string.turnWifiOn), C3791b.m13919W(this.f8683b), true);
                    return;
                case 300:
                    ((TextCheckCell) viewHolder.itemView).setTextAndCheck(LocaleController.getString("turnWifiOff", R.string.turnWifiOff), C3791b.m13920X(this.f8683b), true);
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View customCell;
            switch (i) {
                case 0:
                    customCell = new CustomCell(this.f8683b, 0, 0, false);
                    customCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 100:
                case Callback.DEFAULT_DRAG_ANIMATION_DURATION /*200*/:
                case 300:
                    customCell = new TextCheckCell(this.f8683b);
                    break;
                default:
                    customCell = new CustomCell(this.f8683b, 0, 0, false);
                    break;
            }
            return new Holder(customCell);
        }
    }

    /* renamed from: a */
    public void mo3448a(RadialPickerLayout radialPickerLayout, int i, int i2) {
        this.f8684a = i + ":" + i2;
        this.f8686c.notifyDataSetChanged();
        C3791b.m13953c(getParentActivity(), this.f8684a);
        if (C3791b.m13918V(getParentActivity())) {
            DownloadManagerService.m13182b(ApplicationLoader.applicationContext);
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("scheduleDownloads", R.string.scheduleDownloads));
        this.actionBar.setActionBarMenuOnItemClick(new C25971(this));
        if (!TextUtils.isEmpty(C3791b.m13973f(getParentActivity()))) {
            this.f8684a = C3791b.m13973f(getParentActivity());
        }
        if (!TextUtils.isEmpty(C3791b.m13980g(getParentActivity()))) {
            this.f8685b = C3791b.m13980g(getParentActivity());
        }
        final C2070e a = C2070e.m9257a((C2069c) this, 12, 0, true);
        final C2070e a2 = C2070e.m9257a(new C25982(this), 12, 0, true);
        this.f8686c = new C2600a(this, context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.f8687d = new RecyclerListView(context);
        this.f8687d.setVerticalScrollBarEnabled(false);
        this.f8687d.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.f8687d, LayoutHelper.createFrame(-1, -1.0f));
        this.f8687d.setAdapter(this.f8686c);
        this.f8687d.setOnItemClickListener(new OnItemClickListener(this) {
            /* renamed from: c */
            final /* synthetic */ C2601j f8681c;

            public void onItemClick(View view, int i) {
                boolean z = true;
                if (this.f8681c.getParentActivity() != null) {
                    boolean V;
                    Context parentActivity;
                    if (i == 0) {
                        V = C3791b.m13918V(this.f8681c.getParentActivity());
                        if (view instanceof TextCheckCell) {
                            ((TextCheckCell) view).setChecked(!V);
                            parentActivity = this.f8681c.getParentActivity();
                            if (V) {
                                z = false;
                            }
                            C3791b.m14021n(parentActivity, z);
                        }
                        if (C3791b.m13918V(this.f8681c.getParentActivity())) {
                            DownloadManagerService.m13182b(ApplicationLoader.applicationContext);
                        }
                    } else if (i == 1) {
                        a.show(this.f8681c.getParentActivity().getFragmentManager(), "select time");
                    } else if (i == 2) {
                        a2.show(this.f8681c.getParentActivity().getFragmentManager(), "select time end");
                    } else if (i == 3) {
                        V = C3791b.m13919W(this.f8681c.getParentActivity());
                        if (view instanceof TextCheckCell) {
                            ((TextCheckCell) view).setChecked(!V);
                            parentActivity = this.f8681c.getParentActivity();
                            if (V) {
                                z = false;
                            }
                            C3791b.m14025o(parentActivity, z);
                        }
                    } else if (i == 4) {
                        V = C3791b.m13920X(this.f8681c.getParentActivity());
                        if (view instanceof TextCheckCell) {
                            ((TextCheckCell) view).setChecked(!V);
                            parentActivity = this.f8681c.getParentActivity();
                            if (V) {
                                z = false;
                            }
                            C3791b.m14029p(parentActivity, z);
                        }
                    }
                }
            }
        });
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[12];
        r9[0] = new ThemeDescription(this.f8687d, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[3] = new ThemeDescription(this.f8687d, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[7] = new ThemeDescription(this.f8687d, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[8] = new ThemeDescription(this.f8687d, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[9] = new ThemeDescription(this.f8687d, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        r9[10] = new ThemeDescription(this.f8687d, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[11] = new ThemeDescription(this.f8687d, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        return r9;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public void onResume() {
        super.onResume();
        if (this.f8686c != null) {
            this.f8686c.notifyDataSetChanged();
        }
    }
}
