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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.io.FileOutputStream;
import net.hockeyapp.android.utils.HockeyLog;
import net.hockeyapp.android.views.PaintView;

public class PaintActivity extends Activity {
    public static final String EXTRA_IMAGE_URI = "imageUri";
    private static final int MENU_CLEAR_ID = 3;
    private static final int MENU_SAVE_ID = 1;
    private static final int MENU_UNDO_ID = 2;
    private String mImageName;
    private PaintView mPaintView;

    /* renamed from: net.hockeyapp.android.PaintActivity$1 */
    class C09601 implements OnClickListener {
        C09601() {
        }

        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case -2:
                    PaintActivity.this.finish();
                    return;
                case -1:
                    PaintActivity.this.makeResult();
                    return;
                default:
                    return;
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras == null || extras.getParcelable(EXTRA_IMAGE_URI) == null) {
            HockeyLog.error("Can't set up PaintActivity as image extra was not provided!");
            return;
        }
        Uri imageUri = (Uri) extras.getParcelable(EXTRA_IMAGE_URI);
        this.mImageName = determineFilename(imageUri, imageUri.getLastPathSegment());
        int displayWidth = getResources().getDisplayMetrics().widthPixels;
        int displayHeight = getResources().getDisplayMetrics().heightPixels;
        int currentOrientation = displayWidth > displayHeight ? 0 : 1;
        int desiredOrientation = PaintView.determineOrientation(getContentResolver(), imageUri);
        setRequestedOrientation(desiredOrientation);
        if (currentOrientation != desiredOrientation) {
            HockeyLog.debug("Image loading skipped because activity will be destroyed for orientation change.");
            return;
        }
        this.mPaintView = new PaintView(this, imageUri, displayWidth, displayHeight);
        LinearLayout vLayout = new LinearLayout(this);
        vLayout.setLayoutParams(new LayoutParams(-1, -1));
        vLayout.setGravity(17);
        vLayout.setOrientation(1);
        LinearLayout hLayout = new LinearLayout(this);
        hLayout.setLayoutParams(new LayoutParams(-1, -1));
        hLayout.setGravity(17);
        hLayout.setOrientation(0);
        vLayout.addView(hLayout);
        hLayout.addView(this.mPaintView);
        setContentView(vLayout);
        Toast.makeText(this, getString(C0962R.string.hockeyapp_paint_indicator_toast), 1).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, getString(C0962R.string.hockeyapp_paint_menu_save));
        menu.add(0, 2, 0, getString(C0962R.string.hockeyapp_paint_menu_undo));
        menu.add(0, 3, 0, getString(C0962R.string.hockeyapp_paint_menu_clear));
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                makeResult();
                return true;
            case 2:
                this.mPaintView.undo();
                return true;
            case 3:
                this.mPaintView.clearImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || this.mPaintView.isClear()) {
            return super.onKeyDown(keyCode, event);
        }
        OnClickListener dialogClickListener = new C09601();
        new Builder(this).setMessage(C0962R.string.hockeyapp_paint_dialog_message).setPositiveButton(C0962R.string.hockeyapp_paint_dialog_positive_button, dialogClickListener).setNegativeButton(C0962R.string.hockeyapp_paint_dialog_negative_button, dialogClickListener).setNeutralButton(C0962R.string.hockeyapp_paint_dialog_neutral_button, dialogClickListener).show();
        return true;
    }

    private void makeResult() {
        File hockeyAppCache = new File(getCacheDir(), "HockeyApp");
        hockeyAppCache.mkdir();
        File result = new File(hockeyAppCache, this.mImageName + ".jpg");
        int suffix = 1;
        while (result.exists()) {
            result = new File(hockeyAppCache, this.mImageName + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + suffix + ".jpg");
            suffix++;
        }
        this.mPaintView.setDrawingCacheEnabled(true);
        final Bitmap bitmap = this.mPaintView.getDrawingCache();
        new AsyncTask<File, Void, Void>() {
            protected Void doInBackground(File... args) {
                try {
                    FileOutputStream out = new FileOutputStream(args[0]);
                    bitmap.compress(CompressFormat.JPEG, 100, out);
                    out.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                    HockeyLog.error("Could not save image.", e);
                }
                return null;
            }
        }.execute(new File[]{result});
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IMAGE_URI, Uri.fromFile(result));
        if (getParent() == null) {
            setResult(-1, intent);
        } else {
            getParent().setResult(-1, intent);
        }
        finish();
    }

    private String determineFilename(Uri uri, String fallback) {
        String path = null;
        Cursor metaCursor = getApplicationContext().getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        if (metaCursor != null) {
            try {
                if (metaCursor.moveToFirst()) {
                    path = metaCursor.getString(0);
                }
                metaCursor.close();
            } catch (Throwable th) {
                metaCursor.close();
            }
        }
        return path == null ? fallback : new File(path).getName();
    }
}
