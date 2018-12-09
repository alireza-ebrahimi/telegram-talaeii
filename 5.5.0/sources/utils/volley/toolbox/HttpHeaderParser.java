package utils.volley.toolbox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import utils.volley.Cache.Entry;
import utils.volley.Header;
import utils.volley.NetworkResponse;
import utils.volley.VolleyLog;

public class HttpHeaderParser {
    private static final String DEFAULT_CONTENT_CHARSET = "ISO-8859-1";
    static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String RFC1123_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";

    static String formatEpochAsRfc1123(long j) {
        return newRfc1123Formatter().format(new Date(j));
    }

    private static SimpleDateFormat newRfc1123Formatter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RFC1123_FORMAT, Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat;
    }

    public static Entry parseCacheHeaders(NetworkResponse networkResponse) {
        Object obj;
        long j;
        Object obj2;
        long currentTimeMillis = System.currentTimeMillis();
        Map map = networkResponse.headers;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        String str = (String) map.get("Date");
        if (str != null) {
            j2 = parseDateAsEpoch(str);
        }
        str = (String) map.get("Cache-Control");
        if (str != null) {
            String[] split = str.split(",", 0);
            obj = null;
            j = 0;
            j4 = 0;
            for (String trim : split) {
                String trim2 = trim2.trim();
                if (trim2.equals("no-cache") || trim2.equals("no-store")) {
                    return null;
                }
                if (trim2.startsWith("max-age=")) {
                    try {
                        j4 = Long.parseLong(trim2.substring(8));
                    } catch (Exception e) {
                    }
                } else if (trim2.startsWith("stale-while-revalidate=")) {
                    try {
                        j = Long.parseLong(trim2.substring(23));
                    } catch (Exception e2) {
                    }
                } else if (trim2.equals("must-revalidate") || trim2.equals("proxy-revalidate")) {
                    obj = 1;
                }
            }
            j3 = j4;
            j4 = j;
            obj2 = 1;
        } else {
            obj = null;
            obj2 = null;
        }
        str = (String) map.get("Expires");
        long parseDateAsEpoch = str != null ? parseDateAsEpoch(str) : 0;
        str = (String) map.get("Last-Modified");
        long parseDateAsEpoch2 = str != null ? parseDateAsEpoch(str) : 0;
        str = (String) map.get("ETag");
        if (obj2 != null) {
            j3 = currentTimeMillis + (1000 * j3);
            j = obj != null ? j3 : (1000 * j4) + j3;
        } else if (j2 <= 0 || parseDateAsEpoch < j2) {
            j = 0;
            j3 = 0;
        } else {
            j = (parseDateAsEpoch - j2) + currentTimeMillis;
            j3 = j;
        }
        Entry entry = new Entry();
        entry.data = networkResponse.data;
        entry.etag = str;
        entry.softTtl = j3;
        entry.ttl = j;
        entry.serverDate = j2;
        entry.lastModified = parseDateAsEpoch2;
        entry.responseHeaders = map;
        entry.allResponseHeaders = networkResponse.allHeaders;
        return entry;
    }

    public static String parseCharset(Map<String, String> map) {
        return parseCharset(map, DEFAULT_CONTENT_CHARSET);
    }

    public static String parseCharset(Map<String, String> map, String str) {
        String str2 = (String) map.get(HEADER_CONTENT_TYPE);
        if (str2 == null) {
            return str;
        }
        String[] split = str2.split(";", 0);
        for (int i = 1; i < split.length; i++) {
            String[] split2 = split[i].trim().split("=", 0);
            if (split2.length == 2 && split2[0].equals("charset")) {
                return split2[1];
            }
        }
        return str;
    }

    public static long parseDateAsEpoch(String str) {
        try {
            return newRfc1123Formatter().parse(str).getTime();
        } catch (Throwable e) {
            VolleyLog.m14408e(e, "Unable to parse dateStr: %s, falling back to 0", str);
            return 0;
        }
    }

    static List<Header> toAllHeaderList(Map<String, String> map) {
        List<Header> arrayList = new ArrayList(map.size());
        for (Map.Entry entry : map.entrySet()) {
            arrayList.add(new Header((String) entry.getKey(), (String) entry.getValue()));
        }
        return arrayList;
    }

    static Map<String, String> toHeaderMap(List<Header> list) {
        Map<String, String> treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (Header header : list) {
            treeMap.put(header.getName(), header.getValue());
        }
        return treeMap;
    }
}
