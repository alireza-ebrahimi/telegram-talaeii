package org.telegram.ui.Adapters;

import android.location.Location;
import android.os.AsyncTask;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
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

    public void setDelegate(BaseLocationAdapterDelegate delegate) {
        this.delegate = delegate;
    }

    public void searchDelayed(final String query, final Location coordinate) {
        if (query == null || query.length() == 0) {
            this.places.clear();
            notifyDataSetChanged();
            return;
        }
        try {
            if (this.searchTimer != null) {
                this.searchTimer.cancel();
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        this.searchTimer = new Timer();
        this.searchTimer.schedule(new TimerTask() {

            /* renamed from: org.telegram.ui.Adapters.BaseLocationAdapter$1$1 */
            class C20091 implements Runnable {
                C20091() {
                }

                public void run() {
                    BaseLocationAdapter.this.lastSearchLocation = null;
                    BaseLocationAdapter.this.searchGooglePlacesWithQuery(query, coordinate);
                }
            }

            public void run() {
                try {
                    BaseLocationAdapter.this.searchTimer.cancel();
                    BaseLocationAdapter.this.searchTimer = null;
                } catch (Exception e) {
                    FileLog.e(e);
                }
                AndroidUtilities.runOnUIThread(new C20091());
            }
        }, 200, 500);
    }

    public void searchGooglePlacesWithQuery(String query, Location coordinate) {
        if (this.lastSearchLocation == null || coordinate.distanceTo(this.lastSearchLocation) >= 200.0f) {
            this.lastSearchLocation = coordinate;
            if (this.searching) {
                this.searching = false;
                if (this.currentTask != null) {
                    this.currentTask.cancel(true);
                    this.currentTask = null;
                }
            }
            try {
                String str;
                this.searching = true;
                Locale locale = Locale.US;
                String str2 = "https://api.foursquare.com/v2/venues/search/?v=%s&locale=en&limit=25&client_id=%s&client_secret=%s&ll=%s%s";
                r5 = new Object[5];
                r5[3] = String.format(Locale.US, "%f,%f", new Object[]{Double.valueOf(coordinate.getLatitude()), Double.valueOf(coordinate.getLongitude())});
                if (query == null || query.length() <= 0) {
                    str = "";
                } else {
                    str = "&query=" + URLEncoder.encode(query, "UTF-8");
                }
                r5[4] = str;
                final String url = String.format(locale, str2, r5);
                this.currentTask = new AsyncTask<Void, Void, JSONObject>() {
                    private boolean canRetry = true;

                    /* JADX WARNING: inconsistent code. */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    private java.lang.String downloadUrlContent(java.lang.String r22) {
                        /*
                        r21 = this;
                        r3 = 1;
                        r11 = 0;
                        r7 = 0;
                        r15 = 0;
                        r10 = 0;
                        r8 = new java.net.URL;	 Catch:{ Throwable -> 0x011e }
                        r0 = r22;
                        r8.<init>(r0);	 Catch:{ Throwable -> 0x011e }
                        r10 = r8.openConnection();	 Catch:{ Throwable -> 0x011e }
                        r18 = "User-Agent";
                        r19 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
                        r0 = r18;
                        r1 = r19;
                        r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x011e }
                        r18 = "Accept-Language";
                        r19 = "en-us,en;q=0.5";
                        r0 = r18;
                        r1 = r19;
                        r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x011e }
                        r18 = "Accept";
                        r19 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
                        r0 = r18;
                        r1 = r19;
                        r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x011e }
                        r18 = "Accept-Charset";
                        r19 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
                        r0 = r18;
                        r1 = r19;
                        r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x011e }
                        r18 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
                        r0 = r18;
                        r10.setConnectTimeout(r0);	 Catch:{ Throwable -> 0x011e }
                        r18 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
                        r0 = r18;
                        r10.setReadTimeout(r0);	 Catch:{ Throwable -> 0x011e }
                        r0 = r10 instanceof java.net.HttpURLConnection;	 Catch:{ Throwable -> 0x011e }
                        r18 = r0;
                        if (r18 == 0) goto L_0x00d6;
                    L_0x0058:
                        r0 = r10;
                        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x011e }
                        r12 = r0;
                        r18 = 1;
                        r0 = r18;
                        r12.setInstanceFollowRedirects(r0);	 Catch:{ Throwable -> 0x011e }
                        r17 = r12.getResponseCode();	 Catch:{ Throwable -> 0x011e }
                        r18 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
                        r0 = r17;
                        r1 = r18;
                        if (r0 == r1) goto L_0x007f;
                    L_0x006f:
                        r18 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
                        r0 = r17;
                        r1 = r18;
                        if (r0 == r1) goto L_0x007f;
                    L_0x0077:
                        r18 = 303; // 0x12f float:4.25E-43 double:1.497E-321;
                        r0 = r17;
                        r1 = r18;
                        if (r0 != r1) goto L_0x00d6;
                    L_0x007f:
                        r18 = "Location";
                        r0 = r18;
                        r13 = r12.getHeaderField(r0);	 Catch:{ Throwable -> 0x011e }
                        r18 = "Set-Cookie";
                        r0 = r18;
                        r5 = r12.getHeaderField(r0);	 Catch:{ Throwable -> 0x011e }
                        r8 = new java.net.URL;	 Catch:{ Throwable -> 0x011e }
                        r8.<init>(r13);	 Catch:{ Throwable -> 0x011e }
                        r10 = r8.openConnection();	 Catch:{ Throwable -> 0x011e }
                        r18 = "Cookie";
                        r0 = r18;
                        r10.setRequestProperty(r0, r5);	 Catch:{ Throwable -> 0x011e }
                        r18 = "User-Agent";
                        r19 = "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)";
                        r0 = r18;
                        r1 = r19;
                        r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x011e }
                        r18 = "Accept-Language";
                        r19 = "en-us,en;q=0.5";
                        r0 = r18;
                        r1 = r19;
                        r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x011e }
                        r18 = "Accept";
                        r19 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
                        r0 = r18;
                        r1 = r19;
                        r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x011e }
                        r18 = "Accept-Charset";
                        r19 = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
                        r0 = r18;
                        r1 = r19;
                        r10.addRequestProperty(r0, r1);	 Catch:{ Throwable -> 0x011e }
                    L_0x00d6:
                        r10.connect();	 Catch:{ Throwable -> 0x011e }
                        r11 = r10.getInputStream();	 Catch:{ Throwable -> 0x011e }
                    L_0x00dd:
                        if (r3 == 0) goto L_0x0117;
                    L_0x00df:
                        if (r10 == 0) goto L_0x00ff;
                    L_0x00e1:
                        r0 = r10 instanceof java.net.HttpURLConnection;	 Catch:{ Exception -> 0x015b }
                        r18 = r0;
                        if (r18 == 0) goto L_0x00ff;
                    L_0x00e7:
                        r10 = (java.net.HttpURLConnection) r10;	 Catch:{ Exception -> 0x015b }
                        r4 = r10.getResponseCode();	 Catch:{ Exception -> 0x015b }
                        r18 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
                        r0 = r18;
                        if (r4 == r0) goto L_0x00ff;
                    L_0x00f3:
                        r18 = 202; // 0xca float:2.83E-43 double:1.0E-321;
                        r0 = r18;
                        if (r4 == r0) goto L_0x00ff;
                    L_0x00f9:
                        r18 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
                        r0 = r18;
                        if (r4 == r0) goto L_0x00ff;
                    L_0x00ff:
                        if (r11 == 0) goto L_0x0112;
                    L_0x0101:
                        r18 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
                        r0 = r18;
                        r6 = new byte[r0];	 Catch:{ Throwable -> 0x019a }
                        r16 = r15;
                    L_0x010a:
                        r18 = r21.isCancelled();	 Catch:{ Throwable -> 0x01aa }
                        if (r18 == 0) goto L_0x0160;
                    L_0x0110:
                        r15 = r16;
                    L_0x0112:
                        if (r11 == 0) goto L_0x0117;
                    L_0x0114:
                        r11.close();	 Catch:{ Throwable -> 0x01a0 }
                    L_0x0117:
                        if (r7 == 0) goto L_0x01a6;
                    L_0x0119:
                        r18 = r15.toString();
                    L_0x011d:
                        return r18;
                    L_0x011e:
                        r9 = move-exception;
                        r0 = r9 instanceof java.net.SocketTimeoutException;
                        r18 = r0;
                        if (r18 == 0) goto L_0x0130;
                    L_0x0125:
                        r18 = org.telegram.tgnet.ConnectionsManager.isNetworkOnline();
                        if (r18 == 0) goto L_0x012c;
                    L_0x012b:
                        r3 = 0;
                    L_0x012c:
                        org.telegram.messenger.FileLog.e(r9);
                        goto L_0x00dd;
                    L_0x0130:
                        r0 = r9 instanceof java.net.UnknownHostException;
                        r18 = r0;
                        if (r18 == 0) goto L_0x0138;
                    L_0x0136:
                        r3 = 0;
                        goto L_0x012c;
                    L_0x0138:
                        r0 = r9 instanceof java.net.SocketException;
                        r18 = r0;
                        if (r18 == 0) goto L_0x0153;
                    L_0x013e:
                        r18 = r9.getMessage();
                        if (r18 == 0) goto L_0x012c;
                    L_0x0144:
                        r18 = r9.getMessage();
                        r19 = "ECONNRESET";
                        r18 = r18.contains(r19);
                        if (r18 == 0) goto L_0x012c;
                    L_0x0151:
                        r3 = 0;
                        goto L_0x012c;
                    L_0x0153:
                        r0 = r9 instanceof java.io.FileNotFoundException;
                        r18 = r0;
                        if (r18 == 0) goto L_0x012c;
                    L_0x0159:
                        r3 = 0;
                        goto L_0x012c;
                    L_0x015b:
                        r9 = move-exception;
                        org.telegram.messenger.FileLog.e(r9);
                        goto L_0x00ff;
                    L_0x0160:
                        r14 = r11.read(r6);	 Catch:{ Exception -> 0x0192 }
                        if (r14 <= 0) goto L_0x0185;
                    L_0x0166:
                        if (r16 != 0) goto L_0x01b0;
                    L_0x0168:
                        r15 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0192 }
                        r15.<init>();	 Catch:{ Exception -> 0x0192 }
                    L_0x016d:
                        r18 = new java.lang.String;	 Catch:{ Exception -> 0x01ae }
                        r19 = 0;
                        r20 = "UTF-8";
                        r0 = r18;
                        r1 = r19;
                        r2 = r20;
                        r0.<init>(r6, r1, r14, r2);	 Catch:{ Exception -> 0x01ae }
                        r0 = r18;
                        r15.append(r0);	 Catch:{ Exception -> 0x01ae }
                        r16 = r15;
                        goto L_0x010a;
                    L_0x0185:
                        r18 = -1;
                        r0 = r18;
                        if (r14 != r0) goto L_0x018f;
                    L_0x018b:
                        r7 = 1;
                        r15 = r16;
                        goto L_0x0112;
                    L_0x018f:
                        r15 = r16;
                        goto L_0x0112;
                    L_0x0192:
                        r9 = move-exception;
                        r15 = r16;
                    L_0x0195:
                        org.telegram.messenger.FileLog.e(r9);	 Catch:{ Throwable -> 0x019a }
                        goto L_0x0112;
                    L_0x019a:
                        r9 = move-exception;
                    L_0x019b:
                        org.telegram.messenger.FileLog.e(r9);
                        goto L_0x0112;
                    L_0x01a0:
                        r9 = move-exception;
                        org.telegram.messenger.FileLog.e(r9);
                        goto L_0x0117;
                    L_0x01a6:
                        r18 = 0;
                        goto L_0x011d;
                    L_0x01aa:
                        r9 = move-exception;
                        r15 = r16;
                        goto L_0x019b;
                    L_0x01ae:
                        r9 = move-exception;
                        goto L_0x0195;
                    L_0x01b0:
                        r15 = r16;
                        goto L_0x016d;
                        */
                        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Adapters.BaseLocationAdapter.2.downloadUrlContent(java.lang.String):java.lang.String");
                    }

                    protected JSONObject doInBackground(Void... voids) {
                        String code = downloadUrlContent(url);
                        if (isCancelled()) {
                            return null;
                        }
                        try {
                            return new JSONObject(code);
                        } catch (Exception e) {
                            FileLog.e(e);
                            return null;
                        }
                    }

                    protected void onPostExecute(JSONObject response) {
                        if (response != null) {
                            try {
                                BaseLocationAdapter.this.places.clear();
                                BaseLocationAdapter.this.iconUrls.clear();
                                JSONArray result = response.getJSONObject("response").getJSONArray("venues");
                                for (int a = 0; a < result.length(); a++) {
                                    try {
                                        JSONObject object = result.getJSONObject(a);
                                        String iconUrl = null;
                                        if (object.has("categories")) {
                                            JSONArray categories = object.getJSONArray("categories");
                                            if (categories.length() > 0) {
                                                JSONObject category = categories.getJSONObject(0);
                                                if (category.has(SettingsJsonConstants.APP_ICON_KEY)) {
                                                    JSONObject icon = category.getJSONObject(SettingsJsonConstants.APP_ICON_KEY);
                                                    iconUrl = String.format(Locale.US, "%s64%s", new Object[]{icon.getString("prefix"), icon.getString("suffix")});
                                                }
                                            }
                                        }
                                        BaseLocationAdapter.this.iconUrls.add(iconUrl);
                                        JSONObject location = object.getJSONObject(Param.LOCATION);
                                        TLRPC$TL_messageMediaVenue venue = new TLRPC$TL_messageMediaVenue();
                                        venue.geo = new TLRPC$TL_geoPoint();
                                        venue.geo.lat = location.getDouble("lat");
                                        venue.geo._long = location.getDouble("lng");
                                        if (location.has("address")) {
                                            venue.address = location.getString("address");
                                        } else if (location.has("city")) {
                                            venue.address = location.getString("city");
                                        } else if (location.has("state")) {
                                            venue.address = location.getString("state");
                                        } else if (location.has("country")) {
                                            venue.address = location.getString("country");
                                        } else {
                                            venue.address = String.format(Locale.US, "%f,%f", new Object[]{Double.valueOf(venue.geo.lat), Double.valueOf(venue.geo._long)});
                                        }
                                        if (object.has("name")) {
                                            venue.title = object.getString("name");
                                        }
                                        venue.venue_type = "";
                                        venue.venue_id = object.getString("id");
                                        venue.provider = "foursquare";
                                        BaseLocationAdapter.this.places.add(venue);
                                    } catch (Exception e) {
                                        FileLog.e(e);
                                    }
                                }
                            } catch (Exception e2) {
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
            } catch (Exception e) {
                FileLog.e(e);
                this.searching = false;
                if (this.delegate != null) {
                    this.delegate.didLoadedSearchResult(this.places);
                }
            }
            notifyDataSetChanged();
        }
    }
}
