package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.extractor.ogg.DefaultOggSeeker;

public final class zzciz {
    static List<zzcja<Integer>> zzjic = new ArrayList();
    static List<zzcja<Long>> zzjid = new ArrayList();
    static List<zzcja<Boolean>> zzjie = new ArrayList();
    static List<zzcja<String>> zzjif = new ArrayList();
    private static List<zzcja<Double>> zzjig = new ArrayList();
    private static zzcja<Boolean> zzjih = zzcja.zzb("measurement.log_third_party_store_events_enabled", false, false);
    private static zzcja<Boolean> zzjii = zzcja.zzb("measurement.log_installs_enabled", false, false);
    private static zzcja<Boolean> zzjij = zzcja.zzb("measurement.log_upgrades_enabled", false, false);
    private static zzcja<Boolean> zzjik = zzcja.zzb("measurement.log_androidId_enabled", false, false);
    public static zzcja<Boolean> zzjil = zzcja.zzb("measurement.upload_dsid_enabled", false, false);
    public static zzcja<Boolean> zzjim = zzcja.zzb("measurement.event_sampling_enabled", false, false);
    public static zzcja<String> zzjin = zzcja.zzj("measurement.log_tag", "FA", "FA-SVC");
    public static zzcja<Long> zzjio = zzcja.zzb("measurement.ad_id_cache_time", 10000, 10000);
    public static zzcja<Long> zzjip = zzcja.zzb("measurement.monitoring.sample_period_millis", 86400000, 86400000);
    public static zzcja<Long> zzjiq = zzcja.zzb("measurement.config.cache_time", 86400000, (long) DateUtils.MILLIS_PER_HOUR);
    public static zzcja<String> zzjir;
    public static zzcja<String> zzjis;
    public static zzcja<Integer> zzjit = zzcja.zzm("measurement.upload.max_bundles", 100, 100);
    public static zzcja<Integer> zzjiu = zzcja.zzm("measurement.upload.max_batch_size", 65536, 65536);
    public static zzcja<Integer> zzjiv = zzcja.zzm("measurement.upload.max_bundle_size", 65536, 65536);
    public static zzcja<Integer> zzjiw = zzcja.zzm("measurement.upload.max_events_per_bundle", 1000, 1000);
    public static zzcja<Integer> zzjix = zzcja.zzm("measurement.upload.max_events_per_day", DefaultOggSeeker.MATCH_BYTE_RANGE, DefaultOggSeeker.MATCH_BYTE_RANGE);
    public static zzcja<Integer> zzjiy = zzcja.zzm("measurement.upload.max_error_events_per_day", 1000, 1000);
    public static zzcja<Integer> zzjiz = zzcja.zzm("measurement.upload.max_public_events_per_day", 50000, 50000);
    public static zzcja<Integer> zzjja = zzcja.zzm("measurement.upload.max_conversions_per_day", 500, 500);
    public static zzcja<Integer> zzjjb = zzcja.zzm("measurement.upload.max_realtime_events_per_day", 10, 10);
    public static zzcja<Integer> zzjjc = zzcja.zzm("measurement.store.max_stored_events_per_app", DefaultOggSeeker.MATCH_BYTE_RANGE, DefaultOggSeeker.MATCH_BYTE_RANGE);
    public static zzcja<String> zzjjd;
    public static zzcja<Long> zzjje = zzcja.zzb("measurement.upload.backoff_period", 43200000, 43200000);
    public static zzcja<Long> zzjjf = zzcja.zzb("measurement.upload.window_interval", (long) DateUtils.MILLIS_PER_HOUR, (long) DateUtils.MILLIS_PER_HOUR);
    public static zzcja<Long> zzjjg = zzcja.zzb("measurement.upload.interval", (long) DateUtils.MILLIS_PER_HOUR, (long) DateUtils.MILLIS_PER_HOUR);
    public static zzcja<Long> zzjjh = zzcja.zzb("measurement.upload.realtime_upload_interval", 10000, 10000);
    public static zzcja<Long> zzjji = zzcja.zzb("measurement.upload.debug_upload_interval", 1000, 1000);
    public static zzcja<Long> zzjjj = zzcja.zzb("measurement.upload.minimum_delay", 500, 500);
    public static zzcja<Long> zzjjk = zzcja.zzb("measurement.alarm_manager.minimum_interval", 60000, 60000);
    public static zzcja<Long> zzjjl = zzcja.zzb("measurement.upload.stale_data_deletion_interval", 86400000, 86400000);
    public static zzcja<Long> zzjjm = zzcja.zzb("measurement.upload.refresh_blacklisted_config_interval", 604800000, 604800000);
    public static zzcja<Long> zzjjn = zzcja.zzb("measurement.upload.initial_upload_delay_time", 15000, 15000);
    public static zzcja<Long> zzjjo = zzcja.zzb("measurement.upload.retry_time", 1800000, 1800000);
    public static zzcja<Integer> zzjjp = zzcja.zzm("measurement.upload.retry_count", 6, 6);
    public static zzcja<Long> zzjjq = zzcja.zzb("measurement.upload.max_queue_time", 2419200000L, 2419200000L);
    public static zzcja<Integer> zzjjr = zzcja.zzm("measurement.lifetimevalue.max_currency_tracked", 4, 4);
    public static zzcja<Integer> zzjjs = zzcja.zzm("measurement.audience.filter_result_max_count", 200, 200);
    public static zzcja<Long> zzjjt = zzcja.zzb("measurement.service_client.idle_disconnect_millis", (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    public static zzcja<Boolean> zzjju = zzcja.zzb("measurement.lifetimevalue.user_engagement_tracking_enabled", false, false);
    public static zzcja<Boolean> zzjjv = zzcja.zzb("measurement.audience.complex_param_evaluation", false, false);

    static {
        String str = "https";
        zzjir = zzcja.zzj("measurement.config.url_scheme", str, str);
        str = "app-measurement.com";
        zzjis = zzcja.zzj("measurement.config.url_authority", str, str);
        str = "https://app-measurement.com/a";
        zzjjd = zzcja.zzj("measurement.upload.url", str, str);
    }
}
