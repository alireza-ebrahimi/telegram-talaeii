package org.telegram.customization.Activities;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;
import org.ir.talaeii.R;
import org.telegram.customization.service.DownloadManagerService;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
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
import utils.app.AppPreferences;

public class ScheduleDownloadActivity extends BaseFragment implements OnTimeSetListener {
    public static final int CHECK_CELL = 100;
    public static final int CHECK_CELL1 = 200;
    public static final int CHECK_CELL2 = 300;
    private volatile boolean canceled = false;
    String endTime = "انتخاب کنید";
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int rowCount = 5;
    String startTime = "انتخاب کنید";

    /* renamed from: org.telegram.customization.Activities.ScheduleDownloadActivity$1 */
    class C10881 extends ActionBarMenuOnItemClick {
        C10881() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                ScheduleDownloadActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.ScheduleDownloadActivity$2 */
    class C10892 implements OnTimeSetListener {
        C10892() {
        }

        public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
            ScheduleDownloadActivity.this.endTime = i + ":" + i1;
            ScheduleDownloadActivity.this.listAdapter.notifyDataSetChanged();
            AppPreferences.setEndDownloadTime(ScheduleDownloadActivity.this.getParentActivity(), ScheduleDownloadActivity.this.endTime);
            if (AppPreferences.isDownloadScheduled(ScheduleDownloadActivity.this.getParentActivity())) {
                DownloadManagerService.registerService(ApplicationLoader.applicationContext);
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public boolean isEnabled(ViewHolder holder) {
            int position = holder.getAdapterPosition();
            return true;
        }

        public int getItemCount() {
            return ScheduleDownloadActivity.this.rowCount;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new CustomCell(this.mContext, 0, 0, false);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 100:
                case 200:
                case ScheduleDownloadActivity.CHECK_CELL2 /*300*/:
                    view = new TextCheckCell(this.mContext);
                    break;
                default:
                    view = new CustomCell(this.mContext, 0, 0, false);
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    CustomCell textCell = holder.itemView;
                    User user;
                    if (position == 1) {
                        user = new User();
                        user.first_name = LocaleController.getString("startTime", R.string.startTime);
                        textCell.setData(user, LocaleController.getString("startTime", R.string.startTime), ScheduleDownloadActivity.this.startTime, R.drawable.approval);
                        return;
                    } else if (position == 2) {
                        user = new User();
                        user.first_name = LocaleController.getString("endTime", R.string.endTime);
                        textCell.setData(user, LocaleController.getString("endTime", R.string.endTime), ScheduleDownloadActivity.this.endTime, R.drawable.approval);
                        return;
                    } else {
                        return;
                    }
                case 100:
                    holder.itemView.setTextAndCheck(LocaleController.getString("scheduleDownloads", R.string.scheduleDownloads), AppPreferences.isDownloadScheduled(this.mContext), true);
                    return;
                case 200:
                    holder.itemView.setTextAndCheck(LocaleController.getString("turnWifiOn", R.string.turnWifiOn), AppPreferences.isTurnWifiOn(this.mContext), true);
                    return;
                case ScheduleDownloadActivity.CHECK_CELL2 /*300*/:
                    holder.itemView.setTextAndCheck(LocaleController.getString("turnWifiOff", R.string.turnWifiOff), AppPreferences.isTurnWifiOff(this.mContext), true);
                    return;
                default:
                    return;
            }
        }

        public int getItemViewType(int i) {
            if (i == 0) {
                return 100;
            }
            if (i == 3) {
                return 200;
            }
            if (i == 4) {
                return ScheduleDownloadActivity.CHECK_CELL2;
            }
            return 0;
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("scheduleDownloads", R.string.scheduleDownloads));
        this.actionBar.setActionBarMenuOnItemClick(new C10881());
        if (!TextUtils.isEmpty(AppPreferences.getStartDownloadTime(getParentActivity()))) {
            this.startTime = AppPreferences.getStartDownloadTime(getParentActivity());
        }
        if (!TextUtils.isEmpty(AppPreferences.getEndDownloadTime(getParentActivity()))) {
            this.endTime = AppPreferences.getEndDownloadTime(getParentActivity());
        }
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, 12, 0, true);
        final TimePickerDialog timePickerDialogEnd = TimePickerDialog.newInstance(new C10892(), 12, 0, true);
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                boolean z = true;
                if (ScheduleDownloadActivity.this.getParentActivity() != null) {
                    boolean hide;
                    TextCheckCell textCheckCell;
                    boolean z2;
                    Context parentActivity;
                    if (position == 0) {
                        hide = AppPreferences.isDownloadScheduled(ScheduleDownloadActivity.this.getParentActivity());
                        if (view instanceof TextCheckCell) {
                            textCheckCell = (TextCheckCell) view;
                            if (hide) {
                                z2 = false;
                            } else {
                                z2 = true;
                            }
                            textCheckCell.setChecked(z2);
                            parentActivity = ScheduleDownloadActivity.this.getParentActivity();
                            if (hide) {
                                z = false;
                            }
                            AppPreferences.setDownloadScheduled(parentActivity, z);
                        }
                        if (AppPreferences.isDownloadScheduled(ScheduleDownloadActivity.this.getParentActivity())) {
                            DownloadManagerService.registerService(ApplicationLoader.applicationContext);
                        }
                    } else if (position == 1) {
                        timePickerDialog.show(ScheduleDownloadActivity.this.getParentActivity().getFragmentManager(), "select time");
                    } else if (position == 2) {
                        timePickerDialogEnd.show(ScheduleDownloadActivity.this.getParentActivity().getFragmentManager(), "select time end");
                    } else if (position == 3) {
                        hide = AppPreferences.isTurnWifiOn(ScheduleDownloadActivity.this.getParentActivity());
                        if (view instanceof TextCheckCell) {
                            textCheckCell = (TextCheckCell) view;
                            if (hide) {
                                z2 = false;
                            } else {
                                z2 = true;
                            }
                            textCheckCell.setChecked(z2);
                            parentActivity = ScheduleDownloadActivity.this.getParentActivity();
                            if (hide) {
                                z = false;
                            }
                            AppPreferences.setTurnWifiOn(parentActivity, z);
                        }
                    } else if (position == 4) {
                        hide = AppPreferences.isTurnWifiOff(ScheduleDownloadActivity.this.getParentActivity());
                        if (view instanceof TextCheckCell) {
                            textCheckCell = (TextCheckCell) view;
                            if (hide) {
                                z2 = false;
                            } else {
                                z2 = true;
                            }
                            textCheckCell.setChecked(z2);
                            parentActivity = ScheduleDownloadActivity.this.getParentActivity();
                            if (hide) {
                                z = false;
                            }
                            AppPreferences.setTurnWifiOff(parentActivity, z);
                        }
                    }
                }
            }
        });
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
        this.startTime = i + ":" + i1;
        this.listAdapter.notifyDataSetChanged();
        AppPreferences.setStartDownloadTime(getParentActivity(), this.startTime);
        if (AppPreferences.isDownloadScheduled(getParentActivity())) {
            DownloadManagerService.registerService(ApplicationLoader.applicationContext);
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[12];
        r9[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[8] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[9] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        r9[10] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[11] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        return r9;
    }
}
