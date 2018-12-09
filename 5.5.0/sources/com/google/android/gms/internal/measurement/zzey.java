package com.google.android.gms.internal.measurement;

import android.net.Uri;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkedTrackBlacklistUtil;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.ui.ChatActivity;

@VisibleForTesting
public final class zzey {
    static zzec zzagd;
    static List<zza<Integer>> zzage = new ArrayList();
    static List<zza<Long>> zzagf = new ArrayList();
    static List<zza<Boolean>> zzagg = new ArrayList();
    static List<zza<String>> zzagh = new ArrayList();
    static List<zza<Double>> zzagi = new ArrayList();
    private static final zzxe zzagj;
    private static zza<Boolean> zzagk = zza.zzb("measurement.log_third_party_store_events_enabled", false, false);
    private static zza<Boolean> zzagl = zza.zzb("measurement.log_installs_enabled", false, false);
    private static zza<Boolean> zzagm = zza.zzb("measurement.log_upgrades_enabled", false, false);
    public static zza<Boolean> zzagn = zza.zzb("measurement.log_androidId_enabled", false, false);
    public static zza<Boolean> zzago = zza.zzb("measurement.upload_dsid_enabled", false, false);
    public static zza<String> zzagp = zza.zzd("measurement.log_tag", "FA", "FA-SVC");
    public static zza<Long> zzagq = zza.zzb("measurement.ad_id_cache_time", 10000, 10000);
    public static zza<Long> zzagr = zza.zzb("measurement.monitoring.sample_period_millis", 86400000, 86400000);
    public static zza<Long> zzags = zza.zzb("measurement.config.cache_time", 86400000, 3600000);
    public static zza<String> zzagt;
    public static zza<String> zzagu;
    public static zza<Integer> zzagv = zza.zzc("measurement.upload.max_bundles", 100, 100);
    public static zza<Integer> zzagw = zza.zzc("measurement.upload.max_batch_size", C3446C.DEFAULT_BUFFER_SEGMENT_SIZE, C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
    public static zza<Integer> zzagx = zza.zzc("measurement.upload.max_bundle_size", C3446C.DEFAULT_BUFFER_SEGMENT_SIZE, C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
    public static zza<Integer> zzagy = zza.zzc("measurement.upload.max_events_per_bundle", 1000, 1000);
    public static zza<Integer> zzagz = zza.zzc("measurement.upload.max_events_per_day", DefaultOggSeeker.MATCH_BYTE_RANGE, DefaultOggSeeker.MATCH_BYTE_RANGE);
    public static zza<Integer> zzaha = zza.zzc("measurement.upload.max_error_events_per_day", 1000, 1000);
    public static zza<Integer> zzahb = zza.zzc("measurement.upload.max_public_events_per_day", 50000, 50000);
    public static zza<Integer> zzahc = zza.zzc("measurement.upload.max_conversions_per_day", ChatActivity.startAllServices, ChatActivity.startAllServices);
    public static zza<Integer> zzahd = zza.zzc("measurement.upload.max_realtime_events_per_day", 10, 10);
    public static zza<Integer> zzahe = zza.zzc("measurement.store.max_stored_events_per_app", DefaultOggSeeker.MATCH_BYTE_RANGE, DefaultOggSeeker.MATCH_BYTE_RANGE);
    public static zza<String> zzahf;
    public static zza<Long> zzahg = zza.zzb("measurement.upload.backoff_period", 43200000, 43200000);
    public static zza<Long> zzahh = zza.zzb("measurement.upload.window_interval", 3600000, 3600000);
    public static zza<Long> zzahi = zza.zzb("measurement.upload.interval", 3600000, 3600000);
    public static zza<Long> zzahj = zza.zzb("measurement.upload.realtime_upload_interval", 10000, 10000);
    public static zza<Long> zzahk = zza.zzb("measurement.upload.debug_upload_interval", 1000, 1000);
    public static zza<Long> zzahl = zza.zzb("measurement.upload.minimum_delay", 500, 500);
    public static zza<Long> zzahm = zza.zzb("measurement.alarm_manager.minimum_interval", (long) ChunkedTrackBlacklistUtil.DEFAULT_TRACK_BLACKLIST_MS, (long) ChunkedTrackBlacklistUtil.DEFAULT_TRACK_BLACKLIST_MS);
    public static zza<Long> zzahn = zza.zzb("measurement.upload.stale_data_deletion_interval", 86400000, 86400000);
    public static zza<Long> zzaho = zza.zzb("measurement.upload.refresh_blacklisted_config_interval", 604800000, 604800000);
    public static zza<Long> zzahp = zza.zzb("measurement.upload.initial_upload_delay_time", 15000, 15000);
    public static zza<Long> zzahq = zza.zzb("measurement.upload.retry_time", 1800000, 1800000);
    public static zza<Integer> zzahr = zza.zzc("measurement.upload.retry_count", 6, 6);
    public static zza<Long> zzahs = zza.zzb("measurement.upload.max_queue_time", 2419200000L, 2419200000L);
    public static zza<Integer> zzaht = zza.zzc("measurement.lifetimevalue.max_currency_tracked", 4, 4);
    public static zza<Integer> zzahu = zza.zzc("measurement.audience.filter_result_max_count", Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
    public static zza<Long> zzahv = zza.zzb("measurement.service_client.idle_disconnect_millis", (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    public static zza<Boolean> zzahw = zza.zzb("measurement.test.boolean_flag", false, false);
    public static zza<String> zzahx;
    public static zza<Long> zzahy = zza.zzb("measurement.test.long_flag", -1, -1);
    public static zza<Integer> zzahz = zza.zzc("measurement.test.int_flag", -2, -2);
    public static zza<Double> zzaia = zza.zza("measurement.test.double_flag", -3.0d, -3.0d);
    public static zza<Boolean> zzaib = zza.zzb("measurement.lifetimevalue.user_engagement_tracking_enabled", false, false);
    public static zza<Boolean> zzaic = zza.zzb("measurement.audience.complex_param_evaluation", false, false);
    public static zza<Boolean> zzaid = zza.zzb("measurement.validation.internal_limits_internal_event_params", false, false);
    public static zza<Boolean> zzaie = zza.zzb("measurement.quality.unsuccessful_update_retry_counter", false, false);
    public static zza<Boolean> zzaif = zza.zzb("measurement.iid.disable_on_collection_disabled", true, true);
    public static zza<Boolean> zzaig = zza.zzb("measurement.app_launch.call_only_when_enabled", true, true);
    public static zza<Boolean> zzaih = zza.zzb("measurement.run_on_worker_inline", true, false);
    private static zza<Boolean> zzaii = zza.zzb("measurement.audience.dynamic_filters", false, false);
    public static zza<Boolean> zzaij = zza.zzb("measurement.reset_analytics.persist_time", false, false);

    @VisibleForTesting
    public static final class zza<V> {
        private final V zzaab;
        private zzwu<V> zzaik;
        private final V zzail;
        private volatile V zzaim;
        private final String zzny;

        private zza(String str, V v, V v2) {
            this.zzny = str;
            this.zzaab = v;
            this.zzail = v2;
        }

        static zza<Double> zza(String str, double d, double d2) {
            zza<Double> zza = new zza(str, Double.valueOf(-3.0d), Double.valueOf(-3.0d));
            zzey.zzagi.add(zza);
            return zza;
        }

        static zza<Long> zzb(String str, long j, long j2) {
            zza<Long> zza = new zza(str, Long.valueOf(j), Long.valueOf(j2));
            zzey.zzagf.add(zza);
            return zza;
        }

        static zza<Boolean> zzb(String str, boolean z, boolean z2) {
            zza<Boolean> zza = new zza(str, Boolean.valueOf(z), Boolean.valueOf(z2));
            zzey.zzagg.add(zza);
            return zza;
        }

        static zza<Integer> zzc(String str, int i, int i2) {
            zza<Integer> zza = new zza(str, Integer.valueOf(i), Integer.valueOf(i2));
            zzey.zzage.add(zza);
            return zza;
        }

        static zza<String> zzd(String str, String str2, String str3) {
            zza<String> zza = new zza(str, str2, str3);
            zzey.zzagh.add(zza);
            return zza;
        }

        private static void zzil() {
            synchronized (zza.class) {
                if (zzec.isMainThread()) {
                    throw new IllegalStateException("Tried to refresh flag cache on main thread or on package side.");
                }
                zzec zzec = zzey.zzagd;
                for (zza zza : zzey.zzagg) {
                    zza.zzaim = zza.zzaik.get();
                }
                for (zza zza2 : zzey.zzagh) {
                    zza2.zzaim = zza2.zzaik.get();
                }
                for (zza zza22 : zzey.zzagf) {
                    zza22.zzaim = zza22.zzaik.get();
                }
                for (zza zza222 : zzey.zzage) {
                    zza222.zzaim = zza222.zzaik.get();
                }
                for (zza zza2222 : zzey.zzagi) {
                    zza2222.zzaim = zza2222.zzaik.get();
                }
            }
        }

        private static void zzm() {
            synchronized (zza.class) {
                for (zza zza : zzey.zzagg) {
                    zzxe zzik = zzey.zzagj;
                    String str = zza.zzny;
                    zzec zzec = zzey.zzagd;
                    zza.zzaik = zzik.zzf(str, ((Boolean) zza.zzaab).booleanValue());
                }
                for (zza zza2 : zzey.zzagh) {
                    zzik = zzey.zzagj;
                    str = zza2.zzny;
                    zzec = zzey.zzagd;
                    zza2.zzaik = zzik.zzv(str, (String) zza2.zzaab);
                }
                for (zza zza22 : zzey.zzagf) {
                    zzik = zzey.zzagj;
                    str = zza22.zzny;
                    zzec = zzey.zzagd;
                    zza22.zzaik = zzik.zze(str, ((Long) zza22.zzaab).longValue());
                }
                for (zza zza222 : zzey.zzage) {
                    zzik = zzey.zzagj;
                    str = zza222.zzny;
                    zzec = zzey.zzagd;
                    zza222.zzaik = zzik.zzd(str, ((Integer) zza222.zzaab).intValue());
                }
                for (zza zza2222 : zzey.zzagi) {
                    zzik = zzey.zzagj;
                    str = zza2222.zzny;
                    zzec = zzey.zzagd;
                    zza2222.zzaik = zzik.zzb(str, ((Double) zza2222.zzaab).doubleValue());
                }
            }
        }

        public final V get() {
            if (zzey.zzagd == null) {
                return this.zzaab;
            }
            zzec zzec = zzey.zzagd;
            if (zzec.isMainThread()) {
                return this.zzaim == null ? this.zzaab : this.zzaim;
            } else {
                zzil();
                return this.zzaik.get();
            }
        }

        public final V get(V v) {
            if (v != null) {
                return v;
            }
            if (zzey.zzagd == null) {
                return this.zzaab;
            }
            zzec zzec = zzey.zzagd;
            if (zzec.isMainThread()) {
                return this.zzaim == null ? this.zzaab : this.zzaim;
            } else {
                zzil();
                return this.zzaik.get();
            }
        }

        public final String getKey() {
            return this.zzny;
        }
    }

    static {
        String str = "content://com.google.android.gms.phenotype/";
        String valueOf = String.valueOf(Uri.encode("com.google.android.gms.measurement"));
        zzagj = new zzxe(Uri.parse(valueOf.length() != 0 ? str.concat(valueOf) : new String(str)));
        String str2 = "https";
        zzagt = zza.zzd("measurement.config.url_scheme", str2, str2);
        str2 = "app-measurement.com";
        zzagu = zza.zzd("measurement.config.url_authority", str2, str2);
        str2 = "https://app-measurement.com/a";
        zzahf = zza.zzd("measurement.upload.url", str2, str2);
        str2 = "---";
        zzahx = zza.zzd("measurement.test.string_flag", str2, str2);
    }

    static void zza(zzec zzec) {
        zzagd = zzec;
        zza.zzm();
    }
}
