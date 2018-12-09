package p033b.p034a.p035a.p036a.p037a.p042d;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/* renamed from: b.a.a.a.a.d.g */
public class C1173g extends C1172h {
    public C1173g(Context context, File file, String str, String str2) {
        super(context, file, str, str2);
    }

    /* renamed from: a */
    public OutputStream mo1043a(File file) {
        return new GZIPOutputStream(new FileOutputStream(file));
    }
}
