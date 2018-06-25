package org.telegram.ui.Adapters;

import android.location.Location;
import android.os.AsyncTask;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.TLRPC$TL_geoPoint;
import org.telegram.tgnet.TLRPC$TL_messageMediaVenue;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public abstract class BaseLocationAdapter extends SelectionAdapter {
    private AsyncTask<Void, Void, JSONObject> currentTask;
    private BaseLocationAdapterDelegate delegate;
    protected ArrayList<String> iconUrls = new ArrayList();
    private Location lastSearchLocation;
    protected ArrayList<TLRPC$TL_messageMediaVenue> places = new ArrayList();
    private Timer searchTimer;
    protected boolean searching;

    public interface BaseLocationAdapterDelegate {
        void didLoadedSearchResult(ArrayList<TLRPC$TL_messageMediaVenue> arrayList);
    }

    public void destroy() {
        if (this.currentTask != null) {
            this.currentTask.cancel(true);
            this.currentTask = null;
        }
    }

    public void searchDelayed(final String str, final Location location) {
        if (str == null || str.length() == 0) {
            this.places.clear();
            notifyDataSetChanged();
            return;
        }
        try {
            if (this.searchTimer != null) {
                this.searchTimer.cancel();
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
        this.searchTimer = new Timer();
        this.searchTimer.schedule(new TimerTask() {

            /* renamed from: org.telegram.ui.Adapters.BaseLocationAdapter$1$1 */
            class C38471 implements Runnable {
                C38471() {
                }

                public void run() {
                    BaseLocationAdapter.this.lastSearchLocation = null;
                    BaseLocationAdapter.this.searchGooglePlacesWithQuery(str, location);
                }
            }

            public void run() {
                try {
                    BaseLocationAdapter.this.searchTimer.cancel();
                    BaseLocationAdapter.this.searchTimer = null;
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                AndroidUtilities.runOnUIThread(new C38471());
            }
        }, 200, 500);
    }

    public void searchGooglePlacesWithQuery(String str, Location location) {
        if (this.lastSearchLocation == null || location.distanceTo(this.lastSearchLocation) >= 200.0f) {
            this.lastSearchLocation = location;
            if (this.searching) {
                this.searching = false;
                if (this.currentTask != null) {
                    this.currentTask.cancel(true);
                    this.currentTask = null;
                }
            }
            try {
                this.searching = true;
                Locale locale = Locale.US;
                String str2 = "https://api.foursquare.com/v2/venues/search/?v=%s&locale=en&limit=25&client_id=%s&client_secret=%s&ll=%s%s";
                r3 = new Object[5];
                r3[3] = String.format(Locale.US, "%f,%f", new Object[]{Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude())});
                String str3 = (str == null || str.length() <= 0) ? TtmlNode.ANONYMOUS_REGION_ID : "&query=" + URLEncoder.encode(str, C3446C.UTF8_NAME);
                r3[4] = str3;
                str3 = String.format(locale, str2, r3);
                this.currentTask = new AsyncTask<Void, Void, JSONObject>() {
                    private boolean canRetry = true;

                    /* JADX WARNING: inconsistent code. */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    private java.lang.String downloadUrlContent(java.lang.String r13) {
                        /*
                        r12 = this;
                        r4 = 1;
                        r5 = 0;
                        r3 = 0;
                        r1 = new java.net.URL;	 Catch:{ Throwable -> 0x00d5 }
                        r1.<init>(r13);	 Catch:{ Throwable -> 0x00d5 }
                        r2 = r1.openConnection();	 Catch:{ Throwable -> 0x00d5 }
                        r1 = "User-Agent";
                        r6 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
                        r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0153 }
                        r1 = "Accept-Language";
                        r6 = "en-us,en;q=0.5";
                        r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0153 }
                        r1 = "Accept";
                        r6 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
                        r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0153 }
                        r1 = "Accept-Charset";
                        r6 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
                        r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0153 }
                        r1 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
                        r2.setConnectTimeout(r1);	 Catch:{ Throwable -> 0x0153 }
                        r1 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
                        r2.setReadTimeout(r1);	 Catch:{ Throwable -> 0x0153 }
                        r1 = r2 instanceof java.net.HttpURLConnection;	 Catch:{ Throwable -> 0x0153 }
                        if (r1 == 0) goto L_0x0097;
                    L_0x003e:
                        r0 = r2;
                        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x0153 }
                        r1 = r0;
                        r6 = 1;
                        r1.setInstanceFollowRedirects(r6);	 Catch:{ Throwable -> 0x0153 }
                        r6 = r1.getResponseCode();	 Catch:{ Throwable -> 0x0153 }
                        r7 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
                        if (r6 == r7) goto L_0x0056;
                    L_0x004e:
                        r7 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
                        if (r6 == r7) goto L_0x0056;
                    L_0x0052:
                        r7 = 303; // 0x12f float:4.25E-43 double:1.497E-321;
                        if (r6 != r7) goto L_0x0097;
                    L_0x0056:
                        r6 = "Location";
                        r6 = r1.getHeaderField(r6);	 Catch:{ Throwable -> 0x0153 }
                        r7 = "Set-Cookie";
                        r1 = r1.getHeaderField(r7);	 Catch:{ Throwable -> 0x0153 }
                        r7 = new java.net.URL;	 Catch:{ Throwable -> 0x0153 }
                        r7.<init>(r6);	 Catch:{ Throwable -> 0x0153 }
                        r2 = r7.openConnection();	 Catch:{ Throwable -> 0x0153 }
                        r6 = "Cookie";
                        r2.setRequestProperty(r6, r1);	 Catch:{ Throwable -> 0x0153 }
                        r1 = "User-Agent";
                        r6 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
                        r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0153 }
                        r1 = "Accept-Language";
                        r6 = "en-us,en;q=0.5";
                        r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0153 }
                        r1 = "Accept";
                        r6 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
                        r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0153 }
                        r1 = "Accept-Charset";
                        r6 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
                        r2.addRequestProperty(r1, r6);	 Catch:{ Throwable -> 0x0153 }
                    L_0x0097:
                        r2.connect();	 Catch:{ Throwable -> 0x0153 }
                        r1 = r2.getInputStream();	 Catch:{ Throwable -> 0x0153 }
                        r6 = r1;
                        r1 = r2;
                        r2 = r4;
                    L_0x00a1:
                        if (r2 == 0) goto L_0x015c;
                    L_0x00a3:
                        if (r1 == 0) goto L_0x00bb;
                    L_0x00a5:
                        r2 = r1 instanceof java.net.HttpURLConnection;	 Catch:{ Exception -> 0x010f }
                        if (r2 == 0) goto L_0x00bb;
                    L_0x00a9:
                        r1 = (java.net.HttpURLConnection) r1;	 Catch:{ Exception -> 0x010f }
                        r1 = r1.getResponseCode();	 Catch:{ Exception -> 0x010f }
                        r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
                        if (r1 == r2) goto L_0x00bb;
                    L_0x00b3:
                        r2 = 202; // 0xca float:2.83E-43 double:1.0E-321;
                        if (r1 == r2) goto L_0x00bb;
                    L_0x00b7:
                        r2 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
                        if (r1 == r2) goto L_0x00bb;
                    L_0x00bb:
                        if (r6 == 0) goto L_0x0159;
                    L_0x00bd:
                        r1 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
                        r7 = new byte[r1];	 Catch:{ Throwable -> 0x014c }
                        r1 = r5;
                    L_0x00c3:
                        r2 = r12.isCancelled();	 Catch:{ Throwable -> 0x013c }
                        if (r2 == 0) goto L_0x0114;
                    L_0x00c9:
                        if (r6 == 0) goto L_0x00ce;
                    L_0x00cb:
                        r6.close();	 Catch:{ Throwable -> 0x0145 }
                    L_0x00ce:
                        if (r3 == 0) goto L_0x014a;
                    L_0x00d0:
                        r1 = r1.toString();
                    L_0x00d4:
                        return r1;
                    L_0x00d5:
                        r1 = move-exception;
                        r2 = r1;
                        r6 = r5;
                    L_0x00d8:
                        r1 = r2 instanceof java.net.SocketTimeoutException;
                        if (r1 == 0) goto L_0x00ea;
                    L_0x00dc:
                        r1 = org.telegram.tgnet.ConnectionsManager.isNetworkOnline();
                        if (r1 == 0) goto L_0x015f;
                    L_0x00e2:
                        r1 = r3;
                    L_0x00e3:
                        org.telegram.messenger.FileLog.e(r2);
                        r2 = r1;
                        r1 = r6;
                        r6 = r5;
                        goto L_0x00a1;
                    L_0x00ea:
                        r1 = r2 instanceof java.net.UnknownHostException;
                        if (r1 == 0) goto L_0x00f0;
                    L_0x00ee:
                        r1 = r3;
                        goto L_0x00e3;
                    L_0x00f0:
                        r1 = r2 instanceof java.net.SocketException;
                        if (r1 == 0) goto L_0x0109;
                    L_0x00f4:
                        r1 = r2.getMessage();
                        if (r1 == 0) goto L_0x015f;
                    L_0x00fa:
                        r1 = r2.getMessage();
                        r7 = "ECONNRESET";
                        r1 = r1.contains(r7);
                        if (r1 == 0) goto L_0x015f;
                    L_0x0107:
                        r1 = r3;
                        goto L_0x00e3;
                    L_0x0109:
                        r1 = r2 instanceof java.io.FileNotFoundException;
                        if (r1 == 0) goto L_0x015f;
                    L_0x010d:
                        r1 = r3;
                        goto L_0x00e3;
                    L_0x010f:
                        r1 = move-exception;
                        org.telegram.messenger.FileLog.e(r1);
                        goto L_0x00bb;
                    L_0x0114:
                        r8 = r6.read(r7);	 Catch:{ Exception -> 0x0151 }
                        if (r8 <= 0) goto L_0x012f;
                    L_0x011a:
                        if (r1 != 0) goto L_0x0157;
                    L_0x011c:
                        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0151 }
                        r2.<init>();	 Catch:{ Exception -> 0x0151 }
                    L_0x0121:
                        r1 = new java.lang.String;	 Catch:{ Exception -> 0x0134, Throwable -> 0x014f }
                        r9 = 0;
                        r10 = "UTF-8";
                        r1.<init>(r7, r9, r8, r10);	 Catch:{ Exception -> 0x0134, Throwable -> 0x014f }
                        r2.append(r1);	 Catch:{ Exception -> 0x0134, Throwable -> 0x014f }
                        r1 = r2;
                        goto L_0x00c3;
                    L_0x012f:
                        r2 = -1;
                        if (r8 != r2) goto L_0x00c9;
                    L_0x0132:
                        r3 = r4;
                        goto L_0x00c9;
                    L_0x0134:
                        r1 = move-exception;
                        r11 = r1;
                        r1 = r2;
                        r2 = r11;
                    L_0x0138:
                        org.telegram.messenger.FileLog.e(r2);	 Catch:{ Throwable -> 0x013c }
                        goto L_0x00c9;
                    L_0x013c:
                        r2 = move-exception;
                        r11 = r2;
                        r2 = r1;
                        r1 = r11;
                    L_0x0140:
                        org.telegram.messenger.FileLog.e(r1);
                        r1 = r2;
                        goto L_0x00c9;
                    L_0x0145:
                        r2 = move-exception;
                        org.telegram.messenger.FileLog.e(r2);
                        goto L_0x00ce;
                    L_0x014a:
                        r1 = r5;
                        goto L_0x00d4;
                    L_0x014c:
                        r1 = move-exception;
                        r2 = r5;
                        goto L_0x0140;
                    L_0x014f:
                        r1 = move-exception;
                        goto L_0x0140;
                    L_0x0151:
                        r2 = move-exception;
                        goto L_0x0138;
                    L_0x0153:
                        r1 = move-exception;
                        r6 = r2;
                        r2 = r1;
                        goto L_0x00d8;
                    L_0x0157:
                        r2 = r1;
                        goto L_0x0121;
                    L_0x0159:
                        r1 = r5;
                        goto L_0x00c9;
                    L_0x015c:
                        r1 = r5;
                        goto L_0x00ce;
                    L_0x015f:
                        r1 = r4;
                        goto L_0x00e3;
                        */
                        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Adapters.BaseLocationAdapter.2.downloadUrlContent(java.lang.String):java.lang.String");
                    }

                    protected JSONObject doInBackground(Void... voidArr) {
                        String downloadUrlContent = downloadUrlContent(str3);
                        if (isCancelled()) {
                            return null;
                        }
                        try {
                            return new JSONObject(downloadUrlContent);
                        } catch (Throwable e) {
                            FileLog.e(e);
                            return null;
                        }
                    }

                    protected void onPostExecute(JSONObject jSONObject) {
                        if (jSONObject != null) {
                            try {
                                BaseLocationAdapter.this.places.clear();
                                BaseLocationAdapter.this.iconUrls.clear();
                                JSONArray jSONArray = jSONObject.getJSONObject("response").getJSONArray("venues");
                                for (int i = 0; i < jSONArray.length(); i++) {
                                    try {
                                        JSONObject jSONObject2;
                                        JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                                        Object obj = null;
                                        if (jSONObject3.has("categories")) {
                                            JSONArray jSONArray2 = jSONObject3.getJSONArray("categories");
                                            if (jSONArray2.length() > 0) {
                                                JSONObject jSONObject4 = jSONArray2.getJSONObject(0);
                                                if (jSONObject4.has("icon")) {
                                                    jSONObject2 = jSONObject4.getJSONObject("icon");
                                                    obj = String.format(Locale.US, "%s64%s", new Object[]{jSONObject2.getString("prefix"), jSONObject2.getString("suffix")});
                                                }
                                            }
                                        }
                                        BaseLocationAdapter.this.iconUrls.add(obj);
                                        jSONObject2 = jSONObject3.getJSONObject(C1797b.LOCATION);
                                        TLRPC$TL_messageMediaVenue tLRPC$TL_messageMediaVenue = new TLRPC$TL_messageMediaVenue();
                                        tLRPC$TL_messageMediaVenue.geo = new TLRPC$TL_geoPoint();
                                        tLRPC$TL_messageMediaVenue.geo.lat = jSONObject2.getDouble("lat");
                                        tLRPC$TL_messageMediaVenue.geo._long = jSONObject2.getDouble("lng");
                                        if (jSONObject2.has("address")) {
                                            tLRPC$TL_messageMediaVenue.address = jSONObject2.getString("address");
                                        } else if (jSONObject2.has("city")) {
                                            tLRPC$TL_messageMediaVenue.address = jSONObject2.getString("city");
                                        } else if (jSONObject2.has("state")) {
                                            tLRPC$TL_messageMediaVenue.address = jSONObject2.getString("state");
                                        } else if (jSONObject2.has("country")) {
                                            tLRPC$TL_messageMediaVenue.address = jSONObject2.getString("country");
                                        } else {
                                            tLRPC$TL_messageMediaVenue.address = String.format(Locale.US, "%f,%f", new Object[]{Double.valueOf(tLRPC$TL_messageMediaVenue.geo.lat), Double.valueOf(tLRPC$TL_messageMediaVenue.geo._long)});
                                        }
                                        if (jSONObject3.has("name")) {
                                            tLRPC$TL_messageMediaVenue.title = jSONObject3.getString("name");
                                        }
                                        tLRPC$TL_messageMediaVenue.venue_type = TtmlNode.ANONYMOUS_REGION_ID;
                                        tLRPC$TL_messageMediaVenue.venue_id = jSONObject3.getString(TtmlNode.ATTR_ID);
                                        tLRPC$TL_messageMediaVenue.provider = "foursquare";
                                        BaseLocationAdapter.this.places.add(tLRPC$TL_messageMediaVenue);
                                    } catch (Throwable e) {
                                        FileLog.e(e);
                                    }
                                }
                            } catch (Throwable e2) {
                                FileLog.e(e2);
                            }
                            BaseLocationAdapter.this.searching = false;
                            BaseLocationAdapter.this.notifyDataSetChanged();
                            if (BaseLocationAdapter.this.delegate != null) {
                                BaseLocationAdapter.this.delegate.didLoadedSearchResult(BaseLocationAdapter.this.places);
                                return;
                            }
                            return;
                        }
                        BaseLocationAdapter.this.searching = false;
                        BaseLocationAdapter.this.notifyDataSetChanged();
                        if (BaseLocationAdapter.this.delegate != null) {
                            BaseLocationAdapter.this.delegate.didLoadedSearchResult(BaseLocationAdapter.this.places);
                        }
                    }
                };
                this.currentTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
            } catch (Throwable e) {
                FileLog.e(e);
                this.searching = false;
                if (this.delegate != null) {
                    this.delegate.didLoadedSearchResult(this.places);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void setDelegate(BaseLocationAdapterDelegate baseLocationAdapterDelegate) {
        this.delegate = baseLocationAdapterDelegate;
    }
}
