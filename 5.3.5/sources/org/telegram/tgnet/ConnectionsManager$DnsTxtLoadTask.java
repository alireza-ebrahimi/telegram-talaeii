package org.telegram.tgnet;

import android.os.AsyncTask;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.messenger.FileLog;

class ConnectionsManager$DnsTxtLoadTask extends AsyncTask<Void, Void, NativeByteBuffer> {

    /* renamed from: org.telegram.tgnet.ConnectionsManager$DnsTxtLoadTask$1 */
    class C19531 implements Comparator<String> {
        C19531() {
        }

        public int compare(String o1, String o2) {
            int l1 = o1.length();
            int l2 = o2.length();
            if (l1 > l2) {
                return -1;
            }
            if (l1 < l2) {
                return 1;
            }
            return 0;
        }
    }

    private ConnectionsManager$DnsTxtLoadTask() {
    }

    protected NativeByteBuffer doInBackground(Void... voids) {
        ByteArrayOutputStream outbuf;
        JSONArray array;
        int len;
        ArrayList<String> arrayList;
        int a;
        StringBuilder builder;
        byte[] bytes;
        NativeByteBuffer buffer;
        try {
            URLConnection httpConnection = new URL("https://google.com/resolve?name=" + String.format(Locale.US, ConnectionsManager.native_isTestBackend() != 0 ? "tap%1$s.stel.com" : "ap%1$s.stel.com", new Object[]{""}) + "&type=16").openConnection();
            httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0 like Mac OS X) AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A5297c Safari/602.1");
            httpConnection.addRequestProperty("Host", "dns.google.com");
            httpConnection.setConnectTimeout(5000);
            httpConnection.setReadTimeout(5000);
            httpConnection.connect();
            InputStream httpConnectionStream = httpConnection.getInputStream();
            outbuf = new ByteArrayOutputStream();
            byte[] data = new byte[32768];
            while (!isCancelled()) {
                int read = httpConnectionStream.read(data);
                if (read > 0) {
                    outbuf.write(data, 0, read);
                } else {
                    if (read == -1) {
                    }
                    if (httpConnectionStream != null) {
                        httpConnectionStream.close();
                    }
                    array = new JSONObject(new String(outbuf.toByteArray(), "UTF-8")).getJSONArray("Answer");
                    len = array.length();
                    arrayList = new ArrayList(len);
                    for (a = 0; a < len; a++) {
                        arrayList.add(array.getJSONObject(a).getString("data"));
                    }
                    Collections.sort(arrayList, new C19531());
                    builder = new StringBuilder();
                    for (a = 0; a < arrayList.size(); a++) {
                        builder.append(((String) arrayList.get(a)).replace("\"", ""));
                    }
                    bytes = Base64.decode(builder.toString(), 0);
                    buffer = new NativeByteBuffer(bytes.length);
                    buffer.writeBytes(bytes);
                    return buffer;
                }
            }
            if (httpConnectionStream != null) {
                httpConnectionStream.close();
            }
        } catch (Throwable e) {
            FileLog.e(e);
            return null;
        }
        array = new JSONObject(new String(outbuf.toByteArray(), "UTF-8")).getJSONArray("Answer");
        len = array.length();
        arrayList = new ArrayList(len);
        for (a = 0; a < len; a++) {
            arrayList.add(array.getJSONObject(a).getString("data"));
        }
        Collections.sort(arrayList, new C19531());
        builder = new StringBuilder();
        for (a = 0; a < arrayList.size(); a++) {
            builder.append(((String) arrayList.get(a)).replace("\"", ""));
        }
        bytes = Base64.decode(builder.toString(), 0);
        buffer = new NativeByteBuffer(bytes.length);
        buffer.writeBytes(bytes);
        return buffer;
    }

    protected void onPostExecute(NativeByteBuffer result) {
        if (result != null) {
            ConnectionsManager.native_applyDnsConfig(result.address);
        }
        ConnectionsManager.access$502(null);
    }
}
