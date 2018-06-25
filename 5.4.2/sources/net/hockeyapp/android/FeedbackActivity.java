package net.hockeyapp.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.hockeyapp.android.C2417f.C2414b;
import net.hockeyapp.android.C2417f.C2415c;
import net.hockeyapp.android.C2417f.C2416d;
import net.hockeyapp.android.p134a.C2366a;
import net.hockeyapp.android.p135c.C2369a;
import net.hockeyapp.android.p135c.C2373d;
import net.hockeyapp.android.p135c.C2374e;
import net.hockeyapp.android.p135c.C2375f;
import net.hockeyapp.android.p136d.C2390g;
import net.hockeyapp.android.p136d.C2391h;
import net.hockeyapp.android.p137e.C2393a;
import net.hockeyapp.android.p137e.C2400d;
import net.hockeyapp.android.p137e.C2405g;
import net.hockeyapp.android.p137e.C2408i;
import net.hockeyapp.android.views.AttachmentListView;
import net.hockeyapp.android.views.C2424a;

public class FeedbackActivity extends Activity implements OnClickListener {
    /* renamed from: A */
    private boolean f7897A;
    /* renamed from: B */
    private String f7898B;
    /* renamed from: a */
    private String f7899a;
    /* renamed from: b */
    private String f7900b;
    /* renamed from: c */
    private Context f7901c;
    /* renamed from: d */
    private TextView f7902d;
    /* renamed from: e */
    private EditText f7903e;
    /* renamed from: f */
    private EditText f7904f;
    /* renamed from: g */
    private EditText f7905g;
    /* renamed from: h */
    private EditText f7906h;
    /* renamed from: i */
    private Button f7907i;
    /* renamed from: j */
    private Button f7908j;
    /* renamed from: k */
    private Button f7909k;
    /* renamed from: l */
    private Button f7910l;
    /* renamed from: m */
    private ScrollView f7911m;
    /* renamed from: n */
    private LinearLayout f7912n;
    /* renamed from: o */
    private ListView f7913o;
    /* renamed from: p */
    private C2391h f7914p;
    /* renamed from: q */
    private Handler f7915q;
    /* renamed from: r */
    private C2390g f7916r;
    /* renamed from: s */
    private Handler f7917s;
    /* renamed from: t */
    private List<Uri> f7918t;
    /* renamed from: u */
    private String f7919u;
    /* renamed from: v */
    private C2369a f7920v;
    /* renamed from: w */
    private C2366a f7921w;
    /* renamed from: x */
    private ArrayList<C2373d> f7922x;
    /* renamed from: y */
    private boolean f7923y;
    /* renamed from: z */
    private boolean f7924z;

    /* renamed from: net.hockeyapp.android.FeedbackActivity$1 */
    class C23461 implements DialogInterface.OnClickListener {
        /* renamed from: a */
        final /* synthetic */ FeedbackActivity f7887a;

        C23461(FeedbackActivity feedbackActivity) {
            this.f7887a = feedbackActivity;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f7887a.f7920v = null;
            dialogInterface.cancel();
        }
    }

    /* renamed from: net.hockeyapp.android.FeedbackActivity$3 */
    class C23483 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ FeedbackActivity f7890a;

        C23483(FeedbackActivity feedbackActivity) {
            this.f7890a = feedbackActivity;
        }

        public void run() {
            C2405g.m11864a().m11866a(this.f7890a, null);
            this.f7890a.getSharedPreferences("net.hockeyapp.android.feedback", 0).edit().remove("idLastMessageSend").remove("idLastMessageProcessed").apply();
            this.f7890a.m11684b(false);
        }
    }

    /* renamed from: net.hockeyapp.android.FeedbackActivity$a */
    private static class C2350a extends Handler {
        /* renamed from: a */
        private final WeakReference<FeedbackActivity> f7893a;

        public C2350a(FeedbackActivity feedbackActivity) {
            this.f7893a = new WeakReference(feedbackActivity);
        }

        public void handleMessage(Message message) {
            boolean z = false;
            C2369a c2369a = new C2369a();
            final FeedbackActivity feedbackActivity = (FeedbackActivity) this.f7893a.get();
            if (feedbackActivity != null) {
                if (message == null || message.getData() == null) {
                    c2369a.m11730a(feedbackActivity.getString(C2416d.hockeyapp_feedback_send_generic_error));
                } else {
                    Bundle data = message.getData();
                    String string = data.getString("feedback_response");
                    String string2 = data.getString("feedback_status");
                    String string3 = data.getString("request_type");
                    if ("send".equals(string3) && (string == null || Integer.parseInt(string2) != 201)) {
                        c2369a.m11730a(feedbackActivity.getString(C2416d.hockeyapp_feedback_send_generic_error));
                    } else if ("fetch".equals(string3) && string2 != null && (Integer.parseInt(string2) == WalletConstants.ERROR_CODE_INVALID_PARAMETERS || Integer.parseInt(string2) == 422)) {
                        feedbackActivity.m11679f();
                        z = true;
                    } else if (string != null) {
                        feedbackActivity.m11672b(string, string3);
                        z = true;
                    } else {
                        c2369a.m11730a(feedbackActivity.getString(C2416d.hockeyapp_feedback_send_network_error));
                    }
                }
                feedbackActivity.f7920v = c2369a;
                if (!z) {
                    feedbackActivity.runOnUiThread(new Runnable(this) {
                        /* renamed from: b */
                        final /* synthetic */ C2350a f7892b;

                        public void run() {
                            feedbackActivity.m11683a(true);
                            feedbackActivity.showDialog(0);
                        }
                    });
                }
                feedbackActivity.m11685c(z);
            }
        }
    }

    /* renamed from: net.hockeyapp.android.FeedbackActivity$b */
    private static class C2352b extends Handler {
        /* renamed from: a */
        private final WeakReference<FeedbackActivity> f7896a;

        public C2352b(FeedbackActivity feedbackActivity) {
            this.f7896a = new WeakReference(feedbackActivity);
        }

        public void handleMessage(Message message) {
            final FeedbackActivity feedbackActivity = (FeedbackActivity) this.f7896a.get();
            if (feedbackActivity != null) {
                boolean z;
                feedbackActivity.f7920v = new C2369a();
                if (!(message == null || message.getData() == null)) {
                    C2374e c2374e = (C2374e) message.getData().getSerializable("parse_feedback_response");
                    if (c2374e != null) {
                        if (!c2374e.m11766a().equalsIgnoreCase(C1797b.SUCCESS)) {
                            z = false;
                        } else if (c2374e.m11771c() != null) {
                            C2405g.m11864a().m11866a(feedbackActivity, c2374e.m11771c());
                            feedbackActivity.m11667a(c2374e);
                            feedbackActivity.f7923y = false;
                            z = true;
                        } else {
                            z = true;
                        }
                        if (!z) {
                            feedbackActivity.runOnUiThread(new Runnable(this) {
                                /* renamed from: b */
                                final /* synthetic */ C2352b f7895b;

                                public void run() {
                                    feedbackActivity.showDialog(0);
                                }
                            });
                        }
                        feedbackActivity.m11683a(true);
                    }
                }
                z = false;
                if (z) {
                    feedbackActivity.runOnUiThread(/* anonymous class already generated */);
                }
                feedbackActivity.m11683a(true);
            }
        }
    }

    /* renamed from: a */
    private void m11662a(EditText editText, int i) {
        editText.setError(getString(i));
        m11683a(true);
    }

    /* renamed from: a */
    private void m11663a(String str, String str2) {
        this.f7916r = new C2390g(this, str, this.f7917s, str2);
    }

    /* renamed from: a */
    private void m11664a(String str, String str2, String str3, String str4, String str5, List<Uri> list, String str6, Handler handler, boolean z) {
        this.f7914p = new C2391h(this.f7901c, str, str2, str3, str4, str5, list, str6, handler, z);
        C2393a.m11833a(this.f7914p);
    }

    @SuppressLint({"SimpleDateFormat"})
    /* renamed from: a */
    private void m11667a(final C2374e c2374e) {
        runOnUiThread(new Runnable(this) {
            /* renamed from: b */
            final /* synthetic */ FeedbackActivity f7889b;

            public void run() {
                this.f7889b.m11684b(true);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("d MMM h:mm a");
                if (c2374e != null && c2374e.m11769b() != null && c2374e.m11769b().m11731a() != null && c2374e.m11769b().m11731a().size() > 0) {
                    this.f7889b.f7922x = c2374e.m11769b().m11731a();
                    Collections.reverse(this.f7889b.f7922x);
                    try {
                        Date parse = simpleDateFormat.parse(((C2373d) this.f7889b.f7922x.get(0)).m11751b());
                        this.f7889b.f7902d.setText(String.format(this.f7889b.getString(C2416d.hockeyapp_feedback_last_updated_text), new Object[]{simpleDateFormat2.format(parse)}));
                        this.f7889b.f7902d.setVisibility(0);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (this.f7889b.f7921w == null) {
                        this.f7889b.f7921w = new C2366a(this.f7889b.f7901c, this.f7889b.f7922x);
                    } else {
                        this.f7889b.f7921w.m11714a();
                        Iterator it = this.f7889b.f7922x.iterator();
                        while (it.hasNext()) {
                            this.f7889b.f7921w.m11715a((C2373d) it.next());
                        }
                        this.f7889b.f7921w.notifyDataSetChanged();
                    }
                    this.f7889b.f7913o.setAdapter(this.f7889b.f7921w);
                }
            }
        });
    }

    /* renamed from: a */
    private boolean m11668a(int i) {
        Intent intent;
        if (i == 2) {
            intent = new Intent();
            intent.setType("*/*");
            intent.setAction("android.intent.action.GET_CONTENT");
            startActivityForResult(Intent.createChooser(intent, getString(C2416d.hockeyapp_feedback_select_file)), 2);
            return true;
        } else if (i != 1) {
            return false;
        } else {
            intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.GET_CONTENT");
            startActivityForResult(Intent.createChooser(intent, getString(C2416d.hockeyapp_feedback_select_picture)), 1);
            return true;
        }
    }

    /* renamed from: b */
    private void m11671b() {
        if (!this.f7924z || this.f7923y) {
            this.f7898B = C2405g.m11864a().m11865a(this);
        }
        if (this.f7898B == null || this.f7923y) {
            m11684b(false);
            return;
        }
        m11684b(true);
        m11664a(this.f7919u, null, null, null, null, null, this.f7898B, this.f7915q, true);
    }

    /* renamed from: b */
    private void m11672b(String str, String str2) {
        m11663a(str, str2);
        C2393a.m11833a(this.f7916r);
    }

    /* renamed from: c */
    private void m11674c() {
        if (this.f7906h != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.f7906h.getWindowToken(), 0);
        }
    }

    /* renamed from: d */
    private void m11676d() {
        this.f7915q = new C2350a(this);
    }

    /* renamed from: e */
    private void m11678e() {
        this.f7917s = new C2352b(this);
    }

    /* renamed from: f */
    private void m11679f() {
        runOnUiThread(new C23483(this));
    }

    /* renamed from: g */
    private void m11681g() {
        if (C2408i.m11882a((Context) this)) {
            m11683a(false);
            m11674c();
            String a = (!this.f7924z || this.f7923y) ? C2405g.m11864a().m11865a(this.f7901c) : null;
            String trim = this.f7903e.getText().toString().trim();
            String trim2 = this.f7904f.getText().toString().trim();
            String trim3 = this.f7905g.getText().toString().trim();
            Object trim4 = this.f7906h.getText().toString().trim();
            if (TextUtils.isEmpty(trim3)) {
                this.f7905g.setVisibility(0);
                m11662a(this.f7905g, C2416d.hockeyapp_feedback_validate_subject_error);
                return;
            } else if (C2368b.m11727b() == C2375f.REQUIRED && TextUtils.isEmpty(trim)) {
                m11662a(this.f7903e, C2416d.hockeyapp_feedback_validate_name_error);
                return;
            } else if (C2368b.m11728c() == C2375f.REQUIRED && TextUtils.isEmpty(trim2)) {
                m11662a(this.f7904f, C2416d.hockeyapp_feedback_validate_email_empty);
                return;
            } else if (TextUtils.isEmpty(trim4)) {
                m11662a(this.f7906h, C2416d.hockeyapp_feedback_validate_text_error);
                return;
            } else if (C2368b.m11728c() != C2375f.REQUIRED || C2408i.m11885b(trim2)) {
                C2405g.m11864a().m11867a(this.f7901c, trim, trim2, trim3);
                m11664a(this.f7919u, trim, trim2, trim3, trim4, ((AttachmentListView) findViewById(C2414b.wrapper_attachments)).getAttachments(), a, this.f7915q, false);
                return;
            } else {
                m11662a(this.f7904f, C2416d.hockeyapp_feedback_validate_email_error);
                return;
            }
        }
        Toast.makeText(this, C2416d.hockeyapp_error_no_network_message, 1).show();
    }

    @SuppressLint({"InflateParams"})
    /* renamed from: a */
    public View m11682a() {
        return getLayoutInflater().inflate(C2415c.hockeyapp_activity_feedback, null);
    }

    /* renamed from: a */
    public void m11683a(boolean z) {
        if (this.f7907i != null) {
            this.f7907i.setEnabled(z);
        }
    }

    /* renamed from: b */
    protected void m11684b(boolean z) {
        this.f7911m = (ScrollView) findViewById(C2414b.wrapper_feedback_scroll);
        this.f7912n = (LinearLayout) findViewById(C2414b.wrapper_messages);
        this.f7913o = (ListView) findViewById(C2414b.list_feedback_messages);
        if (z) {
            this.f7912n.setVisibility(0);
            this.f7911m.setVisibility(8);
            this.f7902d = (TextView) findViewById(C2414b.label_last_updated);
            this.f7902d.setVisibility(4);
            this.f7909k = (Button) findViewById(C2414b.button_add_response);
            this.f7909k.setOnClickListener(this);
            this.f7910l = (Button) findViewById(C2414b.button_refresh);
            this.f7910l.setOnClickListener(this);
            return;
        }
        this.f7912n.setVisibility(8);
        this.f7911m.setVisibility(0);
        this.f7903e = (EditText) findViewById(C2414b.input_name);
        this.f7904f = (EditText) findViewById(C2414b.input_email);
        this.f7905g = (EditText) findViewById(C2414b.input_subject);
        this.f7906h = (EditText) findViewById(C2414b.input_message);
        if (!this.f7897A) {
            String b = C2405g.m11864a().m11868b(this.f7901c);
            if (b != null) {
                String[] split = b.split("\\|");
                if (split != null && split.length >= 2) {
                    this.f7903e.setText(split[0]);
                    this.f7904f.setText(split[1]);
                    if (this.f7924z || split.length < 3) {
                        this.f7905g.requestFocus();
                    } else {
                        this.f7905g.setText(split[2]);
                        this.f7906h.requestFocus();
                    }
                }
            } else {
                this.f7903e.setText(this.f7899a);
                this.f7904f.setText(this.f7900b);
                this.f7905g.setText(TtmlNode.ANONYMOUS_REGION_ID);
                if (TextUtils.isEmpty(this.f7899a)) {
                    this.f7903e.requestFocus();
                } else if (TextUtils.isEmpty(this.f7900b)) {
                    this.f7904f.requestFocus();
                } else {
                    this.f7905g.requestFocus();
                }
            }
            this.f7897A = true;
        }
        this.f7903e.setVisibility(C2368b.m11727b() == C2375f.DONT_SHOW ? 8 : 0);
        this.f7904f.setVisibility(C2368b.m11728c() == C2375f.DONT_SHOW ? 8 : 0);
        this.f7906h.setText(TtmlNode.ANONYMOUS_REGION_ID);
        if ((!this.f7924z || this.f7923y) && C2405g.m11864a().m11865a(this.f7901c) != null) {
            this.f7905g.setVisibility(8);
        } else {
            this.f7905g.setVisibility(0);
        }
        ViewGroup viewGroup = (ViewGroup) findViewById(C2414b.wrapper_attachments);
        viewGroup.removeAllViews();
        if (this.f7918t != null) {
            for (Uri c2424a : this.f7918t) {
                viewGroup.addView(new C2424a((Context) this, viewGroup, c2424a, true));
            }
        }
        this.f7908j = (Button) findViewById(C2414b.button_attachment);
        this.f7908j.setOnClickListener(this);
        registerForContextMenu(this.f7908j);
        this.f7907i = (Button) findViewById(C2414b.button_send);
        this.f7907i.setOnClickListener(this);
    }

    /* renamed from: c */
    protected void m11685c(boolean z) {
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            if (i == 2) {
                Uri data = intent.getData();
                if (data != null) {
                    ViewGroup viewGroup = (ViewGroup) findViewById(C2414b.wrapper_attachments);
                    viewGroup.addView(new C2424a((Context) this, viewGroup, data, true));
                }
            } else if (i == 1) {
                Parcelable data2 = intent.getData();
                if (data2 != null) {
                    try {
                        Intent intent2 = new Intent(this, PaintActivity.class);
                        intent2.putExtra("imageUri", data2);
                        startActivityForResult(intent2, 3);
                    } catch (Throwable e) {
                        C2400d.m11842a("HockeyApp", "Paint activity not declared!", e);
                    }
                }
            } else if (i == 3) {
                Uri uri = (Uri) intent.getParcelableExtra("imageUri");
                if (uri != null) {
                    ViewGroup viewGroup2 = (ViewGroup) findViewById(C2414b.wrapper_attachments);
                    viewGroup2.addView(new C2424a((Context) this, viewGroup2, uri, true));
                }
            }
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C2414b.button_send) {
            m11681g();
        } else if (id == C2414b.button_attachment) {
            if (((ViewGroup) findViewById(C2414b.wrapper_attachments)).getChildCount() >= 3) {
                Toast.makeText(this, String.valueOf(3), 0).show();
            } else {
                openContextMenu(view);
            }
        } else if (id == C2414b.button_add_response) {
            this.f7923y = true;
            m11684b(false);
        } else if (id == C2414b.button_refresh) {
            m11664a(this.f7919u, null, null, null, null, null, C2405g.m11864a().m11865a(this.f7901c), this.f7915q, true);
        }
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 1:
            case 2:
                return m11668a(menuItem.getItemId());
            default:
                return super.onContextItemSelected(menuItem);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(m11682a());
        setTitle(getString(C2416d.hockeyapp_feedback_title));
        this.f7901c = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.f7919u = extras.getString(ImagesContract.URL);
            this.f7924z = extras.getBoolean("forceNewThread");
            this.f7899a = extras.getString("initialUserName");
            this.f7900b = extras.getString("initialUserEmail");
            Parcelable[] parcelableArray = extras.getParcelableArray("initialAttachments");
            if (parcelableArray != null) {
                this.f7918t = new ArrayList();
                for (Parcelable parcelable : parcelableArray) {
                    this.f7918t.add((Uri) parcelable);
                }
            }
        }
        if (bundle != null) {
            this.f7897A = bundle.getBoolean("feedbackViewInitialized");
            this.f7923y = bundle.getBoolean("inSendFeedback");
        } else {
            this.f7923y = false;
            this.f7897A = false;
        }
        ((NotificationManager) getSystemService("notification")).cancel(2);
        m11676d();
        m11678e();
        m11671b();
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        contextMenu.add(0, 2, 0, getString(C2416d.hockeyapp_feedback_attach_file));
        contextMenu.add(0, 1, 0, getString(C2416d.hockeyapp_feedback_attach_picture));
    }

    protected Dialog onCreateDialog(int i) {
        switch (i) {
            case 0:
                return new Builder(this).setMessage(getString(C2416d.hockeyapp_dialog_error_message)).setCancelable(false).setTitle(getString(C2416d.hockeyapp_dialog_error_title)).setIcon(17301543).setPositiveButton(getString(C2416d.hockeyapp_dialog_positive_button), new C23461(this)).create();
            default:
                return null;
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.f7923y) {
            this.f7923y = false;
            m11671b();
        } else {
            finish();
        }
        return true;
    }

    protected void onPrepareDialog(int i, Dialog dialog) {
        switch (i) {
            case 0:
                AlertDialog alertDialog = (AlertDialog) dialog;
                if (this.f7920v != null) {
                    alertDialog.setMessage(this.f7920v.m11729a());
                    return;
                } else {
                    alertDialog.setMessage(getString(C2416d.hockeyapp_feedback_generic_error));
                    return;
                }
            default:
                return;
        }
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        if (bundle != null) {
            ViewGroup viewGroup = (ViewGroup) findViewById(C2414b.wrapper_attachments);
            Iterator it = bundle.getParcelableArrayList("attachments").iterator();
            while (it.hasNext()) {
                Uri uri = (Uri) it.next();
                if (!this.f7918t.contains(uri)) {
                    viewGroup.addView(new C2424a((Context) this, viewGroup, uri, true));
                }
            }
            this.f7897A = bundle.getBoolean("feedbackViewInitialized");
        }
        super.onRestoreInstanceState(bundle);
    }

    public Object onRetainNonConfigurationInstance() {
        if (this.f7914p != null) {
            this.f7914p.m11831a();
        }
        return this.f7914p;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList("attachments", ((AttachmentListView) findViewById(C2414b.wrapper_attachments)).getAttachments());
        bundle.putBoolean("feedbackViewInitialized", this.f7897A);
        bundle.putBoolean("inSendFeedback", this.f7923y);
        super.onSaveInstanceState(bundle);
    }

    protected void onStop() {
        super.onStop();
        if (this.f7914p != null) {
            this.f7914p.m11831a();
        }
    }
}
