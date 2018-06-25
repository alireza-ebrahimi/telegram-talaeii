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
import org.telegram.customization.p156a.C2637c;
import org.telegram.customization.p156a.C2637c.C2532b;
import org.telegram.customization.p159b.C2666a;
import org.telegram.customization.util.C2872c;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.LayoutHelper;
import utils.view.FarsiTextView;

/* renamed from: org.telegram.customization.Activities.b */
public class C2538b extends BaseFragment implements NotificationCenterDelegate {
    /* renamed from: a */
    RecyclerView f8487a;
    /* renamed from: b */
    private FarsiTextView f8488b;

    /* renamed from: org.telegram.customization.Activities.b$1 */
    class C25301 extends ActionBarMenuOnItemClick {
        /* renamed from: a */
        final /* synthetic */ C2538b f8479a;

        C25301(C2538b c2538b) {
            this.f8479a = c2538b;
        }

        public void onItemClick(int i) {
            if (i == -1) {
                this.f8479a.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.b$3 */
    class C25363 implements C2532b {
        /* renamed from: a */
        final /* synthetic */ C2538b f8485a;

        C25363(C2538b c2538b) {
            this.f8485a = c2538b;
        }

        /* renamed from: a */
        public void mo3430a(User user, Chat chat) {
            if (user != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", user.id);
                if (MessagesController.checkCanOpenChat(bundle, this.f8485a)) {
                    this.f8485a.presentFragment(new ChatActivity(bundle), false);
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.b$4 */
    class C25374 implements OnPreDrawListener {
        /* renamed from: a */
        final /* synthetic */ C2538b f8486a;

        C25374(C2538b c2538b) {
            this.f8486a = c2538b;
        }

        public boolean onPreDraw() {
            if (this.f8486a.fragmentView != null) {
                this.f8486a.fragmentView.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            return true;
        }
    }

    /* renamed from: a */
    private void m12299a() {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0);
        sharedPreferences.getInt("themeColor", C2872c.f9484b);
        this.actionBar.setTitleColor(sharedPreferences.getInt("prefHeaderTitleColor", -1));
        Drawable drawable = getParentActivity().getResources().getDrawable(R.drawable.ic_ab_back);
        drawable.setColorFilter(sharedPreferences.getInt("prefHeaderIconsColor", -1), Mode.MULTIPLY);
        this.actionBar.setBackButtonDrawable(drawable);
        getParentActivity().getResources().getDrawable(R.drawable.ic_ab_other).setColorFilter(sharedPreferences.getInt("prefHeaderIconsColor", -1), Mode.MULTIPLY);
    }

    /* renamed from: b */
    private void m12301b() {
        if (this.fragmentView != null) {
            this.fragmentView.getViewTreeObserver().addOnPreDrawListener(new C25374(this));
        }
    }

    public View createView(final Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        ActionBarMenu createMenu = this.actionBar.createMenu();
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setTitle(LocaleController.getString("UserChangeLog", R.string.UserChangeLog));
        this.actionBar.setActionBarMenuOnItemClick(new C25301(this));
        this.fragmentView = new FrameLayout(context);
        this.f8488b = new FarsiTextView(getParentActivity());
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.f8487a = new RecyclerView(getParentActivity());
        this.f8487a.setLayoutManager(new LinearLayoutManager(context, 1, false));
        ArrayList a = C2666a.getDatabaseHandler().m12210a(0);
        createMenu.addItem(1000, R.drawable.ic_delete_white_24dp).setOnClickListener(new OnClickListener(this) {
            /* renamed from: b */
            final /* synthetic */ C2538b f8484b;

            /* renamed from: org.telegram.customization.Activities.b$2$1 */
            class C25311 implements DialogInterface.OnClickListener {
                /* renamed from: a */
                final /* synthetic */ C25352 f8480a;

                C25311(C25352 c25352) {
                    this.f8480a = c25352;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }

            /* renamed from: org.telegram.customization.Activities.b$2$2 */
            class C25342 implements DialogInterface.OnClickListener {
                /* renamed from: a */
                final /* synthetic */ C25352 f8482a;

                /* renamed from: org.telegram.customization.Activities.b$2$2$1 */
                class C25331 implements C2532b {
                    /* renamed from: a */
                    final /* synthetic */ C25342 f8481a;

                    C25331(C25342 c25342) {
                        this.f8481a = c25342;
                    }

                    /* renamed from: a */
                    public void mo3430a(User user, Chat chat) {
                        if (user != null) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("user_id", user.id);
                            if (MessagesController.checkCanOpenChat(bundle, this.f8481a.f8482a.f8484b)) {
                                this.f8481a.f8482a.f8484b.presentFragment(new ChatActivity(bundle), false);
                            }
                        }
                    }
                }

                C25342(C25352 c25352) {
                    this.f8482a = c25352;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    C2666a.getDatabaseHandler().m12224d();
                    this.f8482a.f8484b.f8487a.setAdapter(new C2637c(new C25331(this), C2666a.getDatabaseHandler().m12210a(0)));
                    this.f8482a.f8484b.f8488b.setVisibility(0);
                }
            }

            public void onClick(View view) {
                Builder builder = new Builder(context);
                builder.setTitle(LocaleController.getString("MyAppName", R.string.MyAppName));
                builder.setMessage("تغییرات پاک شوند ؟").setCancelable(true).setPositiveButton("بله", new C25342(this)).setNegativeButton("خیر", new C25311(this));
                builder.create().show();
            }
        });
        if (a != null && a.size() > 0) {
            this.f8488b.setVisibility(8);
        }
        this.f8487a.setAdapter(new C2637c(new C25363(this), a));
        this.f8488b.setText("لیست تغییرات مخاطبان خالی می باشد");
        frameLayout.addView(this.f8487a, LayoutHelper.createFrame(-1, -1, 51));
        frameLayout.addView(this.f8488b, LayoutHelper.createFrame(-2, -2, 17));
        frameLayout.addView(this.actionBar);
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        m12301b();
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
        m12299a();
        m12301b();
    }
}
