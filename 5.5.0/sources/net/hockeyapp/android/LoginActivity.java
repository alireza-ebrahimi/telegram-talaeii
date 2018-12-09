package net.hockeyapp.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import net.hockeyapp.android.C2417f.C2414b;
import net.hockeyapp.android.C2417f.C2415c;
import net.hockeyapp.android.C2417f.C2416d;
import net.hockeyapp.android.p136d.C2389f;
import net.hockeyapp.android.p137e.C2393a;
import net.hockeyapp.android.p137e.C2408i;
import org.telegram.tgnet.ConnectionsManager;

public class LoginActivity extends Activity {
    /* renamed from: a */
    private String f7927a;
    /* renamed from: b */
    private String f7928b;
    /* renamed from: c */
    private int f7929c;
    /* renamed from: d */
    private C2389f f7930d;
    /* renamed from: e */
    private Handler f7931e;
    /* renamed from: f */
    private Button f7932f;

    /* renamed from: net.hockeyapp.android.LoginActivity$1 */
    class C23531 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ LoginActivity f7925a;

        C23531(LoginActivity loginActivity) {
            this.f7925a = loginActivity;
        }

        public void onClick(View view) {
            this.f7925a.m11689c();
        }
    }

    /* renamed from: net.hockeyapp.android.LoginActivity$a */
    private static class C2354a extends Handler {
        /* renamed from: a */
        private final WeakReference<Activity> f7926a;

        public C2354a(Activity activity) {
            this.f7926a = new WeakReference(activity);
        }

        public void handleMessage(Message message) {
            Activity activity = (Activity) this.f7926a.get();
            if (activity != null) {
                if (message.getData().getBoolean(C1797b.SUCCESS)) {
                    activity.finish();
                    if (C2392d.f8064b != null) {
                        C2392d.f8064b.m11908a();
                        return;
                    }
                    return;
                }
                Toast.makeText(activity, "Login failed. Check your credentials.", 1).show();
            }
        }
    }

    /* renamed from: a */
    private void m11686a() {
        if (this.f7929c == 1) {
            ((EditText) findViewById(C2414b.input_password)).setVisibility(4);
        }
        ((TextView) findViewById(C2414b.text_headline)).setText(this.f7929c == 1 ? C2416d.hockeyapp_login_headline_text_email_only : C2416d.hockeyapp_login_headline_text);
        this.f7932f = (Button) findViewById(C2414b.button_login);
        this.f7932f.setOnClickListener(new C23531(this));
    }

    /* renamed from: b */
    private void m11688b() {
        this.f7931e = new C2354a(this);
    }

    /* renamed from: c */
    private void m11689c() {
        int i = 0;
        if (C2408i.m11882a((Context) this)) {
            CharSequence obj = ((EditText) findViewById(C2414b.input_email)).getText().toString();
            CharSequence obj2 = ((EditText) findViewById(C2414b.input_password)).getText().toString();
            Map hashMap = new HashMap();
            if (this.f7929c == 1) {
                int i2 = !TextUtils.isEmpty(obj) ? 1 : 0;
                hashMap.put(Scopes.EMAIL, obj);
                hashMap.put("authcode", m11690a(this.f7928b + obj));
                i = i2;
            } else if (this.f7929c == 2) {
                if (!(TextUtils.isEmpty(obj) || TextUtils.isEmpty(obj2))) {
                    i = 1;
                }
                hashMap.put(Scopes.EMAIL, obj);
                hashMap.put("password", obj2);
            }
            if (i != 0) {
                this.f7930d = new C2389f(this, this.f7931e, this.f7927a, this.f7929c, hashMap);
                C2393a.m11833a(this.f7930d);
                return;
            }
            Toast.makeText(this, getString(C2416d.hockeyapp_login_missing_credentials_toast), 1).show();
            return;
        }
        Toast.makeText(this, C2416d.hockeyapp_error_no_network_message, 1).show();
    }

    /* renamed from: a */
    public String m11690a(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                String toHexString = Integer.toHexString(b & 255);
                while (toHexString.length() < 2) {
                    toHexString = "0" + toHexString;
                }
                stringBuilder.append(toHexString);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C2415c.hockeyapp_activity_login);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.f7927a = extras.getString(ImagesContract.URL);
            this.f7928b = extras.getString("secret");
            this.f7929c = extras.getInt("mode");
        }
        m11686a();
        m11688b();
        Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
        if (lastNonConfigurationInstance != null) {
            this.f7930d = (C2389f) lastNonConfigurationInstance;
            this.f7930d.m11820a((Context) this, this.f7931e);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (C2392d.f8064b != null) {
                C2392d.f8064b.m11909b();
            } else if (C2392d.f8063a == null) {
                return true;
            } else {
                Intent intent = new Intent(this, C2392d.f8063a);
                intent.setFlags(ConnectionsManager.FileTypeFile);
                intent.putExtra("net.hockeyapp.android.EXIT", true);
                startActivity(intent);
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    public Object onRetainNonConfigurationInstance() {
        if (this.f7930d != null) {
            this.f7930d.m11819a();
        }
        return this.f7930d;
    }
}
