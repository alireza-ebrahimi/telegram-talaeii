package org.telegram.tgnet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import org.telegram.messenger.FileLog;

class ConnectionsManager$DnsLoadTask extends AsyncTask<Void, Void, NativeByteBuffer> {
    private ConnectionsManager$DnsLoadTask() {
    }

    protected NativeByteBuffer doInBackground(Void... voids) {
        ByteArrayOutputStream outbuf;
        byte[] bytes;
        NativeByteBuffer buffer;
        try {
            URL downloadUrl;
            if (ConnectionsManager.native_isTestBackend() != 0) {
                downloadUrl = new URL("https://google.com/test/");
            } else {
                downloadUrl = new URL("https://google.com");
            }
            URLConnection httpConnection = downloadUrl.openConnection();
            httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0 like Mac OS X) AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A5297c Safari/602.1");
            httpConnection.addRequestProperty("Host", String.format(Locale.US, "dns-telegram%1$s.appspot.com", new Object[]{""}));
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
                    bytes = Base64.decode(outbuf.toByteArray(), 0);
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
        bytes = Base64.decode(outbuf.toByteArray(), 0);
        buffer = new NativeByteBuffer(bytes.length);
        buffer.writeBytes(bytes);
        return buffer;
    }

    protected void onPostExecute(NativeByteBuffer result) {
        if (result != null) {
            ConnectionsManager.access$502(null);
            ConnectionsManager.native_applyDnsConfig(result.address);
            return;
        }
        ConnectionsManager$DnsTxtLoadTask task = new ConnectionsManager$DnsTxtLoadTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
        ConnectionsManager.access$502(task);
    }

    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isConnected() && info.getType() == 0;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }
}
