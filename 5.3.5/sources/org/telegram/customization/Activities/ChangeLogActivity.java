package org.telegram.customization.Activities;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Adapters.ChangeLogAdapter;
import org.telegram.customization.Adapters.ChangeLogAdapter.OnQuickAccessClickListener;
import org.telegram.customization.Application.AppApplication;
import org.telegram.customization.Model.ContactChangeLog;
import org.telegram.customization.util.AppUtilities;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.LayoutHelper;
import utils.view.FarsiTextView;

public class ChangeLogActivity extends BaseFragment implements NotificationCenterDelegate {
    private FarsiTextView emptyView;
    RecyclerView recyclerView;

    /* renamed from: org.telegram.customization.Activities.ChangeLogActivity$1 */
    class C10151 extends ActionBarMenuOnItemClick {
        C10151() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                ChangeLogActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.ChangeLogActivity$3 */
    class C10203 implements OnQuickAccessClickListener {
        C10203() {
        }

        public void onClick(User user, TLRPC$Chat chat) {
            if (user != null) {
                Bundle args = new Bundle();
                args.putInt("user_id", user.id);
                if (MessagesController.checkCanOpenChat(args, ChangeLogActivity.this)) {
                    ChangeLogActivity.this.presentFragment(new ChatActivity(args), false);
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.ChangeLogActivity$4 */
    class C10214 implements OnPreDrawListener {
        C10214() {
        }

        public boolean onPreDraw() {
            if (ChangeLogActivity.this.fragmentView != null) {
                ChangeLogActivity.this.fragmentView.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            return true;
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public View createView(final Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        ActionBarMenu menu = this.actionBar.createMenu();
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setTitle(LocaleController.getString("UserChangeLog", R.string.UserChangeLog));
        this.actionBar.setActionBarMenuOnItemClick(new C10151());
        this.fragmentView = new FrameLayout(context);
        this.emptyView = new FarsiTextView(getParentActivity());
        FrameLayout frameLayout = this.fragmentView;
        this.recyclerView = new RecyclerView(getParentActivity());
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        ArrayList<ContactChangeLog> changeLogs = AppApplication.getDatabaseHandler().getContactChangeLogWithType(0);
        menu.addItem(1000, (int) R.drawable.ic_delete_white_24dp).setOnClickListener(new OnClickListener() {

            /* renamed from: org.telegram.customization.Activities.ChangeLogActivity$2$1 */
            class C10161 implements DialogInterface.OnClickListener {
                C10161() {
                }

                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            }

            /* renamed from: org.telegram.customization.Activities.ChangeLogActivity$2$2 */
            class C10182 implements DialogInterface.OnClickListener {

                /* renamed from: org.telegram.customization.Activities.ChangeLogActivity$2$2$1 */
                class C10171 implements OnQuickAccessClickListener {
                    C10171() {
                    }

                    public void onClick(User user, TLRPC$Chat chat) {
                        if (user != null) {
                            Bundle args = new Bundle();
                            args.putInt("user_id", user.id);
                            if (MessagesController.checkCanOpenChat(args, ChangeLogActivity.this)) {
                                ChangeLogActivity.this.presentFragment(new ChatActivity(args), false);
                            }
                        }
                    }
                }

                C10182() {
                }

                public void onClick(DialogInterface dialog, int id) {
                    AppApplication.getDatabaseHandler().deleteAllChangeLog();
                    ChangeLogActivity.this.recyclerView.setAdapter(new ChangeLogAdapter(new C10171(), AppApplication.getDatabaseHandler().getContactChangeLogWithType(0)));
                    ChangeLogActivity.this.emptyView.setVisibility(0);
                }
            }

            public void onClick(View view) {
                Builder alertDialogBuilder = new Builder(context);
                alertDialogBuilder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                alertDialogBuilder.setMessage("تغییرات پاک شوند ؟").setCancelable(true).setPositiveButton("بله", new C10182()).setNegativeButton("خیر", new C10161());
                alertDialogBuilder.create().show();
            }
        });
        if (changeLogs != null && changeLogs.size() > 0) {
            this.emptyView.setVisibility(8);
        }
        this.recyclerView.setAdapter(new ChangeLogAdapter(new C10203(), changeLogs));
        this.emptyView.setText("لیست تغییرات مخاطبان خالی می باشد");
        frameLayout.addView(this.recyclerView, LayoutHelper.createFrame(-1, -1, 51));
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-2, -2, 17));
        frameLayout.addView(this.actionBar);
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
        updateTheme();
        fixLayout();
    }

    private void updateTheme() {
        SharedPreferences themePrefs = ApplicationLoader.applicationContext.getSharedPreferences(AppUtilities.THEME_PREFS, 0);
        int def = themePrefs.getInt("themeColor", AppUtilities.defColor);
        this.actionBar.setTitleColor(themePrefs.getInt("prefHeaderTitleColor", -1));
        Drawable back = getParentActivity().getResources().getDrawable(R.drawable.ic_ab_back);
        back.setColorFilter(themePrefs.getInt("prefHeaderIconsColor", -1), Mode.MULTIPLY);
        this.actionBar.setBackButtonDrawable(back);
        getParentActivity().getResources().getDrawable(R.drawable.ic_ab_other).setColorFilter(themePrefs.getInt("prefHeaderIconsColor", -1), Mode.MULTIPLY);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        fixLayout();
    }

    private void fixLayout() {
        if (this.fragmentView != null) {
            this.fragmentView.getViewTreeObserver().addOnPreDrawListener(new C10214());
        }
    }

    public void didReceivedNotification(int id, Object... args) {
    }
}
