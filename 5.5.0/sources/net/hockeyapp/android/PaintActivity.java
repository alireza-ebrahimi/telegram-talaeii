package net.hockeyapp.android;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import net.hockeyapp.android.C2417f.C2416d;
import net.hockeyapp.android.p137e.C2400d;
import net.hockeyapp.android.views.C2427c;

public class PaintActivity extends Activity {
    /* renamed from: a */
    private C2427c f7936a;
    /* renamed from: b */
    private String f7937b;

    /* renamed from: net.hockeyapp.android.PaintActivity$1 */
    class C23551 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ PaintActivity f7933a;

        C23551(PaintActivity paintActivity) {
            this.f7933a = paintActivity;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case -2:
                    this.f7933a.finish();
                    return;
                case -1:
                    this.f7933a.m11693a();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: a */
    private String m11692a(Uri uri, String str) {
        String str2 = null;
        Cursor query = getApplicationContext().getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    str2 = query.getString(0);
                }
                query.close();
            } catch (Throwable th) {
                query.close();
            }
        }
        return str2 == null ? str : new File(str2).getName();
    }

    /* renamed from: a */
    private void m11693a() {
        File file = new File(getCacheDir(), "HockeyApp");
        file.mkdir();
        File file2 = new File(file, this.f7937b + ".jpg");
        int i = 1;
        while (file2.exists()) {
            file2 = new File(file, this.f7937b + "_" + i + ".jpg");
            i++;
        }
        this.f7936a.setDrawingCacheEnabled(true);
        final Bitmap drawingCache = this.f7936a.getDrawingCache();
        new AsyncTask<File, Void, Void>(this) {
            /* renamed from: b */
            final /* synthetic */ PaintActivity f7935b;

            /* renamed from: a */
            protected Void m11691a(File... fileArr) {
                try {
                    OutputStream fileOutputStream = new FileOutputStream(fileArr[0]);
                    drawingCache.compress(CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                    C2400d.m11843a("Could not save image.", e);
                }
                return null;
            }

            protected /* synthetic */ Object doInBackground(Object[] objArr) {
                return m11691a((File[]) objArr);
            }
        }.execute(new File[]{file2});
        Intent intent = new Intent();
        intent.putExtra("imageUri", Uri.fromFile(file2));
        if (getParent() == null) {
            setResult(-1, intent);
        } else {
            getParent().setResult(-1, intent);
        }
        finish();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        if (extras == null || extras.getParcelable("imageUri") == null) {
            C2400d.m11846c("Can't set up PaintActivity as image extra was not provided!");
            return;
        }
        Uri uri = (Uri) extras.getParcelable("imageUri");
        this.f7937b = m11692a(uri, uri.getLastPathSegment());
        int i = getResources().getDisplayMetrics().widthPixels;
        int i2 = getResources().getDisplayMetrics().heightPixels;
        int i3 = i > i2 ? 0 : 1;
        int a = C2427c.m11933a(getContentResolver(), uri);
        setRequestedOrientation(a);
        if (i3 != a) {
            C2400d.m11840a("Image loading skipped because activity will be destroyed for orientation change.");
            return;
        }
        this.f7936a = new C2427c(this, uri, i, i2);
        View linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LayoutParams(-1, -1));
        linearLayout.setGravity(17);
        linearLayout.setOrientation(1);
        View linearLayout2 = new LinearLayout(this);
        linearLayout2.setLayoutParams(new LayoutParams(-1, -1));
        linearLayout2.setGravity(17);
        linearLayout2.setOrientation(0);
        linearLayout.addView(linearLayout2);
        linearLayout2.addView(this.f7936a);
        setContentView(linearLayout);
        Toast.makeText(this, getString(C2416d.hockeyapp_paint_indicator_toast), 1).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, getString(C2416d.hockeyapp_paint_menu_save));
        menu.add(0, 2, 0, getString(C2416d.hockeyapp_paint_menu_undo));
        menu.add(0, 3, 0, getString(C2416d.hockeyapp_paint_menu_clear));
        return true;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || this.f7936a.m11942c()) {
            return super.onKeyDown(i, keyEvent);
        }
        OnClickListener c23551 = new C23551(this);
        new Builder(this).setMessage(C2416d.hockeyapp_paint_dialog_message).setPositiveButton(C2416d.hockeyapp_paint_dialog_positive_button, c23551).setNegativeButton(C2416d.hockeyapp_paint_dialog_negative_button, c23551).setNeutralButton(C2416d.hockeyapp_paint_dialog_neutral_button, c23551).show();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 1:
                m11693a();
                return true;
            case 2:
                this.f7936a.m11941b();
                return true;
            case 3:
                this.f7936a.m11940a();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }
}
