package android.support.v4.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class FileProvider extends ContentProvider {
    /* renamed from: a */
    private static final String[] f1159a = new String[]{"_display_name", "_size"};
    /* renamed from: b */
    private static final File f1160b = new File("/");
    /* renamed from: c */
    private static HashMap<String, C0396a> f1161c = new HashMap();
    /* renamed from: d */
    private C0396a f1162d;

    /* renamed from: android.support.v4.content.FileProvider$a */
    interface C0396a {
        /* renamed from: a */
        Uri mo312a(File file);

        /* renamed from: a */
        File mo313a(Uri uri);
    }

    /* renamed from: android.support.v4.content.FileProvider$b */
    static class C0397b implements C0396a {
        /* renamed from: a */
        private final String f1157a;
        /* renamed from: b */
        private final HashMap<String, File> f1158b = new HashMap();

        public C0397b(String str) {
            this.f1157a = str;
        }

        /* renamed from: a */
        public Uri mo312a(File file) {
            try {
                String canonicalPath = file.getCanonicalPath();
                Entry entry = null;
                for (Entry entry2 : this.f1158b.entrySet()) {
                    Entry entry22;
                    String path = ((File) entry22.getValue()).getPath();
                    if (!canonicalPath.startsWith(path) || (entry != null && path.length() <= ((File) entry.getValue()).getPath().length())) {
                        entry22 = entry;
                    }
                    entry = entry22;
                }
                if (entry == null) {
                    throw new IllegalArgumentException("Failed to find configured root that contains " + canonicalPath);
                }
                String path2 = ((File) entry.getValue()).getPath();
                return new Builder().scheme(C1797b.CONTENT).authority(this.f1157a).encodedPath(Uri.encode((String) entry.getKey()) + '/' + Uri.encode(path2.endsWith("/") ? canonicalPath.substring(path2.length()) : canonicalPath.substring(path2.length() + 1), "/")).build();
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file);
            }
        }

        /* renamed from: a */
        public File mo313a(Uri uri) {
            String encodedPath = uri.getEncodedPath();
            int indexOf = encodedPath.indexOf(47, 1);
            String decode = Uri.decode(encodedPath.substring(1, indexOf));
            String decode2 = Uri.decode(encodedPath.substring(indexOf + 1));
            File file = (File) this.f1158b.get(decode);
            if (file == null) {
                throw new IllegalArgumentException("Unable to find configured root for " + uri);
            }
            File file2 = new File(file, decode2);
            try {
                File canonicalFile = file2.getCanonicalFile();
                if (canonicalFile.getPath().startsWith(file.getPath())) {
                    return canonicalFile;
                }
                throw new SecurityException("Resolved path jumped beyond configured root");
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file2);
            }
        }

        /* renamed from: a */
        public void m1843a(String str, File file) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Name must not be empty");
            }
            try {
                this.f1158b.put(str, file.getCanonicalFile());
            } catch (Throwable e) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file, e);
            }
        }
    }

    /* renamed from: a */
    private static int m1844a(String str) {
        if ("r".equals(str)) {
            return ErrorDialogData.BINDER_CRASH;
        }
        if ("w".equals(str) || "wt".equals(str)) {
            return 738197504;
        }
        if ("wa".equals(str)) {
            return 704643072;
        }
        if ("rw".equals(str)) {
            return 939524096;
        }
        if ("rwt".equals(str)) {
            return 1006632960;
        }
        throw new IllegalArgumentException("Invalid mode: " + str);
    }

    /* renamed from: a */
    public static Uri m1845a(Context context, String str, File file) {
        return m1846a(context, str).mo312a(file);
    }

    /* renamed from: a */
    private static C0396a m1846a(Context context, String str) {
        C0396a c0396a;
        synchronized (f1161c) {
            c0396a = (C0396a) f1161c.get(str);
            if (c0396a == null) {
                try {
                    c0396a = m1850b(context, str);
                    f1161c.put(str, c0396a);
                } catch (Throwable e) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e);
                } catch (Throwable e2) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e2);
                }
            }
        }
        return c0396a;
    }

    /* renamed from: a */
    private static File m1847a(File file, String... strArr) {
        int length = strArr.length;
        int i = 0;
        File file2 = file;
        while (i < length) {
            String str = strArr[i];
            i++;
            file2 = str != null ? new File(file2, str) : file2;
        }
        return file2;
    }

    /* renamed from: a */
    private static Object[] m1848a(Object[] objArr, int i) {
        Object obj = new Object[i];
        System.arraycopy(objArr, 0, obj, 0, i);
        return obj;
    }

    /* renamed from: a */
    private static String[] m1849a(String[] strArr, int i) {
        Object obj = new String[i];
        System.arraycopy(strArr, 0, obj, 0, i);
        return obj;
    }

    /* renamed from: b */
    private static C0396a m1850b(Context context, String str) {
        C0396a c0397b = new C0397b(str);
        XmlResourceParser loadXmlMetaData = context.getPackageManager().resolveContentProvider(str, 128).loadXmlMetaData(context.getPackageManager(), "android.support.FILE_PROVIDER_PATHS");
        if (loadXmlMetaData == null) {
            throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        }
        while (true) {
            int next = loadXmlMetaData.next();
            if (next == 1) {
                return c0397b;
            }
            if (next == 2) {
                File file;
                String name = loadXmlMetaData.getName();
                String attributeValue = loadXmlMetaData.getAttributeValue(null, "name");
                String attributeValue2 = loadXmlMetaData.getAttributeValue(null, "path");
                if ("root-path".equals(name)) {
                    file = f1160b;
                } else if ("files-path".equals(name)) {
                    file = context.getFilesDir();
                } else if ("cache-path".equals(name)) {
                    file = context.getCacheDir();
                } else if ("external-path".equals(name)) {
                    file = Environment.getExternalStorageDirectory();
                } else {
                    File[] a;
                    if ("external-files-path".equals(name)) {
                        a = C0235a.m1071a(context, null);
                        if (a.length > 0) {
                            file = a[0];
                        }
                    } else if ("external-cache-path".equals(name)) {
                        a = C0235a.m1070a(context);
                        if (a.length > 0) {
                            file = a[0];
                        }
                    }
                    file = null;
                }
                if (file != null) {
                    c0397b.m1843a(attributeValue, m1847a(file, attributeValue2));
                }
            }
        }
    }

    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.exported) {
            throw new SecurityException("Provider must not be exported");
        } else if (providerInfo.grantUriPermissions) {
            this.f1162d = m1846a(context, providerInfo.authority);
        } else {
            throw new SecurityException("Provider must grant uri permissions");
        }
    }

    public int delete(Uri uri, String str, String[] strArr) {
        return this.f1162d.mo313a(uri).delete() ? 1 : 0;
    }

    public String getType(Uri uri) {
        File a = this.f1162d.mo313a(uri);
        int lastIndexOf = a.getName().lastIndexOf(46);
        if (lastIndexOf >= 0) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(a.getName().substring(lastIndexOf + 1));
            if (mimeTypeFromExtension != null) {
                return mimeTypeFromExtension;
            }
        }
        return "application/octet-stream";
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No external inserts");
    }

    public boolean onCreate() {
        return true;
    }

    public ParcelFileDescriptor openFile(Uri uri, String str) {
        return ParcelFileDescriptor.open(this.f1162d.mo313a(uri), m1844a(str));
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        File a = this.f1162d.mo313a(uri);
        if (strArr == null) {
            strArr = f1159a;
        }
        String[] strArr3 = new String[strArr.length];
        Object[] objArr = new Object[strArr.length];
        int length = strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3;
            Object obj = strArr[i];
            if ("_display_name".equals(obj)) {
                strArr3[i2] = "_display_name";
                i3 = i2 + 1;
                objArr[i2] = a.getName();
            } else if ("_size".equals(obj)) {
                strArr3[i2] = "_size";
                i3 = i2 + 1;
                objArr[i2] = Long.valueOf(a.length());
            } else {
                i3 = i2;
            }
            i++;
            i2 = i3;
        }
        String[] a2 = m1849a(strArr3, i2);
        Object[] a3 = m1848a(objArr, i2);
        Cursor matrixCursor = new MatrixCursor(a2, 1);
        matrixCursor.addRow(a3);
        return matrixCursor;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("No external updates");
    }
}
