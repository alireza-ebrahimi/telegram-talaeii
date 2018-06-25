package p033b.p034a.p035a.p036a.p037a.p045g;

import com.google.android.gms.common.internal.ImagesContract;
import org.json.JSONObject;
import p033b.p034a.p035a.p036a.p037a.p039b.C1113k;

/* renamed from: b.a.a.a.a.g.k */
class C1208k implements C1207v {
    C1208k() {
    }

    /* renamed from: a */
    private long m6342a(C1113k c1113k, long j, JSONObject jSONObject) {
        return jSONObject.has("expires_at") ? jSONObject.getLong("expires_at") : c1113k.mo1024a() + (1000 * j);
    }

    /* renamed from: a */
    private C1200e m6343a(JSONObject jSONObject) {
        String string = jSONObject.getString("identifier");
        String string2 = jSONObject.getString("status");
        String string3 = jSONObject.getString(ImagesContract.URL);
        String string4 = jSONObject.getString("reports_url");
        String string5 = jSONObject.getString("ndk_reports_url");
        boolean optBoolean = jSONObject.optBoolean("update_required", false);
        C1198c c1198c = null;
        if (jSONObject.has("icon") && jSONObject.getJSONObject("icon").has("hash")) {
            c1198c = m6344b(jSONObject.getJSONObject("icon"));
        }
        return new C1200e(string, string2, string3, string4, string5, optBoolean, c1198c);
    }

    /* renamed from: b */
    private C1198c m6344b(JSONObject jSONObject) {
        return new C1198c(jSONObject.getString("hash"), jSONObject.getInt("width"), jSONObject.getInt("height"));
    }

    /* renamed from: c */
    private C1211m m6345c(JSONObject jSONObject) {
        return new C1211m(jSONObject.optBoolean("prompt_enabled", false), jSONObject.optBoolean("collect_logged_exceptions", true), jSONObject.optBoolean("collect_reports", true), jSONObject.optBoolean("collect_analytics", false));
    }

    /* renamed from: d */
    private C1197b m6346d(JSONObject jSONObject) {
        return new C1197b(jSONObject.optString(ImagesContract.URL, "https://e.crashlytics.com/spi/v2/events"), jSONObject.optInt("flush_interval_secs", 600), jSONObject.optInt("max_byte_size_per_file", 8000), jSONObject.optInt("max_file_count_per_send", 1), jSONObject.optInt("max_pending_send_file_count", 100), jSONObject.optBoolean("forward_to_google_analytics", false), jSONObject.optBoolean("include_purchase_events_in_forwarded_events", false), jSONObject.optBoolean("track_custom_events", true), jSONObject.optBoolean("track_predefined_events", true), jSONObject.optInt("sampling_rate", 1), jSONObject.optBoolean("flush_on_background", true));
    }

    /* renamed from: e */
    private C1214p m6347e(JSONObject jSONObject) {
        return new C1214p(jSONObject.optInt("log_buffer_size", 64000), jSONObject.optInt("max_chained_exception_depth", 8), jSONObject.optInt("max_custom_exception_events", 64), jSONObject.optInt("max_custom_key_value_pairs", 64), jSONObject.optInt("identifier_mask", 255), jSONObject.optBoolean("send_session_without_crash", false), jSONObject.optInt("max_complete_sessions_count", 4));
    }

    /* renamed from: f */
    private C1213o m6348f(JSONObject jSONObject) {
        return new C1213o(jSONObject.optString("title", "Send Crash Report?"), jSONObject.optString("message", "Looks like we crashed! Please help us fix the problem by sending a crash report."), jSONObject.optString("send_button_title", "Send"), jSONObject.optBoolean("show_cancel_button", true), jSONObject.optString("cancel_button_title", "Don't Send"), jSONObject.optBoolean("show_always_send_button", true), jSONObject.optString("always_send_button_title", "Always Send"));
    }

    /* renamed from: g */
    private C1201f m6349g(JSONObject jSONObject) {
        return new C1201f(jSONObject.optString("update_endpoint", C1220u.f3529a), jSONObject.optInt("update_suspend_duration", 3600));
    }

    /* renamed from: a */
    public C1219t mo1059a(C1113k c1113k, JSONObject jSONObject) {
        int optInt = jSONObject.optInt("settings_version", 0);
        int optInt2 = jSONObject.optInt("cache_duration", 3600);
        return new C1219t(m6342a(c1113k, (long) optInt2, jSONObject), m6343a(jSONObject.getJSONObject("app")), m6347e(jSONObject.getJSONObject("session")), m6348f(jSONObject.getJSONObject("prompt")), m6345c(jSONObject.getJSONObject("features")), m6346d(jSONObject.getJSONObject("analytics")), m6349g(jSONObject.getJSONObject("beta")), optInt, optInt2);
    }
}
