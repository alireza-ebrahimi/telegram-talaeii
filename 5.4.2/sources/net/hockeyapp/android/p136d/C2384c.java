package net.hockeyapp.android.p136d;

import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/* renamed from: net.hockeyapp.android.d.c */
public abstract class C2384c<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    /* renamed from: a */
    private static String m11802a(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuilder.append(readLine + "\n");
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                try {
                    inputStream.close();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                throw th;
            }
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    /* renamed from: a */
    protected static String m11803a(HttpURLConnection httpURLConnection) {
        InputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        String a = C2384c.m11802a(bufferedInputStream);
        bufferedInputStream.close();
        return a;
    }
}
