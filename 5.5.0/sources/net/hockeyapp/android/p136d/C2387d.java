package net.hockeyapp.android.p136d;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy;
import android.os.StrictMode.VmPolicy.Builder;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import net.hockeyapp.android.C2417f.C2416d;
import net.hockeyapp.android.p133b.C2359a;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

/* renamed from: net.hockeyapp.android.d.d */
public class C2387d extends AsyncTask<Void, Integer, Long> {
    /* renamed from: a */
    protected Context f8030a;
    /* renamed from: b */
    protected C2359a f8031b;
    /* renamed from: c */
    protected String f8032c;
    /* renamed from: d */
    protected String f8033d = (UUID.randomUUID() + ".apk");
    /* renamed from: e */
    protected String f8034e = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download");
    /* renamed from: f */
    protected ProgressDialog f8035f;
    /* renamed from: g */
    private String f8036g;

    /* renamed from: net.hockeyapp.android.d.d$1 */
    class C23851 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2387d f8028a;

        C23851(C2387d c2387d) {
            this.f8028a = c2387d;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f8028a.f8031b.mo3382a(this.f8028a, Boolean.valueOf(false));
        }
    }

    /* renamed from: net.hockeyapp.android.d.d$2 */
    class C23862 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2387d f8029a;

        C23862(C2387d c2387d) {
            this.f8029a = c2387d;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f8029a.f8031b.mo3382a(this.f8029a, Boolean.valueOf(true));
        }
    }

    public C2387d(Context context, String str, C2359a c2359a) {
        this.f8030a = context;
        this.f8032c = str;
        this.f8031b = c2359a;
        this.f8036g = null;
    }

    /* renamed from: a */
    protected Long mo3384a(Void... voidArr) {
        InputStream bufferedInputStream;
        Long valueOf;
        IOException e;
        Throwable th;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        OutputStream outputStream2 = null;
        try {
            URLConnection a = m11805a(new URL(m11811b()), 6);
            a.connect();
            int contentLength = a.getContentLength();
            String contentType = a.getContentType();
            if (contentType == null || !contentType.contains(MimeTypes.BASE_TYPE_TEXT)) {
                File file = new File(this.f8034e);
                if (file.mkdirs() || file.exists()) {
                    File file2 = new File(file, this.f8033d);
                    bufferedInputStream = new BufferedInputStream(a.getInputStream());
                    try {
                        OutputStream fileOutputStream = new FileOutputStream(file2);
                        try {
                            byte[] bArr = new byte[1024];
                            long j = 0;
                            while (true) {
                                int read = bufferedInputStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                j += (long) read;
                                publishProgress(new Integer[]{Integer.valueOf(Math.round((((float) j) * 100.0f) / ((float) contentLength)))});
                                fileOutputStream.write(bArr, 0, read);
                            }
                            fileOutputStream.flush();
                            valueOf = Long.valueOf(j);
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            if (bufferedInputStream != null) {
                                bufferedInputStream.close();
                            }
                        } catch (IOException e3) {
                            e = e3;
                            outputStream = fileOutputStream;
                            inputStream = bufferedInputStream;
                            try {
                                e.printStackTrace();
                                valueOf = Long.valueOf(0);
                                if (outputStream != null) {
                                    try {
                                        outputStream.close();
                                    } catch (IOException e22) {
                                        e22.printStackTrace();
                                    }
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                return valueOf;
                            } catch (Throwable th2) {
                                th = th2;
                                bufferedInputStream = inputStream;
                                if (outputStream != null) {
                                    try {
                                        outputStream.close();
                                    } catch (IOException e222) {
                                        e222.printStackTrace();
                                        throw th;
                                    }
                                }
                                if (bufferedInputStream != null) {
                                    bufferedInputStream.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            outputStream = fileOutputStream;
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            if (bufferedInputStream != null) {
                                bufferedInputStream.close();
                            }
                            throw th;
                        }
                    } catch (IOException e4) {
                        e = e4;
                        inputStream = bufferedInputStream;
                        e.printStackTrace();
                        valueOf = Long.valueOf(0);
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        return valueOf;
                    } catch (Throwable th4) {
                        th = th4;
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (bufferedInputStream != null) {
                            bufferedInputStream.close();
                        }
                        throw th;
                    }
                    return valueOf;
                }
                throw new IOException("Could not create the dir(s):" + file.getAbsolutePath());
            }
            this.f8036g = "The requested download does not appear to be a file.";
            valueOf = Long.valueOf(0);
            if (null != null) {
                try {
                    outputStream2.close();
                } catch (IOException e2222) {
                    e2222.printStackTrace();
                }
            }
            if (null != null) {
                inputStream.close();
            }
            return valueOf;
        } catch (IOException e5) {
            e = e5;
            inputStream = null;
            e.printStackTrace();
            valueOf = Long.valueOf(0);
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return valueOf;
        } catch (Throwable th5) {
            th = th5;
            bufferedInputStream = null;
            if (outputStream != null) {
                outputStream.close();
            }
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            throw th;
        }
    }

    /* renamed from: a */
    protected URLConnection m11805a(URL url, int i) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        m11809a(httpURLConnection);
        int responseCode = httpURLConnection.getResponseCode();
        if ((responseCode != 301 && responseCode != 302 && responseCode != 303) || i == 0) {
            return httpURLConnection;
        }
        URL url2 = new URL(httpURLConnection.getHeaderField("Location"));
        if (url.getProtocol().equals(url2.getProtocol())) {
            return httpURLConnection;
        }
        httpURLConnection.disconnect();
        return m11805a(url2, i - 1);
    }

    /* renamed from: a */
    public void m11806a() {
        this.f8030a = null;
        this.f8035f = null;
    }

    /* renamed from: a */
    public void m11807a(Context context) {
        this.f8030a = context;
    }

    /* renamed from: a */
    protected void mo3385a(Long l) {
        if (this.f8035f != null) {
            try {
                this.f8035f.dismiss();
            } catch (Exception e) {
            }
        }
        if (l.longValue() > 0) {
            this.f8031b.mo3381a(this);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(new File(this.f8034e, this.f8033d)), "application/vnd.android.package-archive");
            intent.setFlags(ErrorDialogData.BINDER_CRASH);
            VmPolicy vmPolicy = null;
            if (VERSION.SDK_INT >= 24) {
                vmPolicy = StrictMode.getVmPolicy();
                StrictMode.setVmPolicy(new Builder().penaltyLog().build());
            }
            this.f8030a.startActivity(intent);
            if (vmPolicy != null) {
                StrictMode.setVmPolicy(vmPolicy);
                return;
            }
            return;
        }
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.f8030a);
            builder.setTitle(C2416d.hockeyapp_download_failed_dialog_title);
            builder.setMessage(this.f8036g == null ? this.f8030a.getString(C2416d.hockeyapp_download_failed_dialog_message) : this.f8036g);
            builder.setNegativeButton(C2416d.hockeyapp_download_failed_dialog_negative_button, new C23851(this));
            builder.setPositiveButton(C2416d.hockeyapp_download_failed_dialog_positive_button, new C23862(this));
            builder.create().show();
        } catch (Exception e2) {
        }
    }

    /* renamed from: a */
    protected void m11809a(HttpURLConnection httpURLConnection) {
        httpURLConnection.addRequestProperty("User-Agent", "HockeySDK/Android 4.1.3");
        httpURLConnection.setInstanceFollowRedirects(true);
        if (VERSION.SDK_INT <= 9) {
            httpURLConnection.setRequestProperty("connection", "close");
        }
    }

    /* renamed from: a */
    protected void mo3386a(Integer... numArr) {
        try {
            if (this.f8035f == null) {
                this.f8035f = new ProgressDialog(this.f8030a);
                this.f8035f.setProgressStyle(1);
                this.f8035f.setMessage("Loading...");
                this.f8035f.setCancelable(false);
                this.f8035f.show();
            }
            this.f8035f.setProgress(numArr[0].intValue());
        } catch (Exception e) {
        }
    }

    /* renamed from: b */
    protected String m11811b() {
        return this.f8032c + "&type=apk";
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return mo3384a((Void[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        mo3385a((Long) obj);
    }

    protected /* synthetic */ void onProgressUpdate(Object[] objArr) {
        mo3386a((Integer[]) objArr);
    }
}
