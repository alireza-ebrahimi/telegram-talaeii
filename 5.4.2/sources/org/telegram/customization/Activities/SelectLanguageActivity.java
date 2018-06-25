package org.telegram.customization.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.C0235a;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocaleController.LocaleInfo;
import org.telegram.ui.LaunchActivity;

public class SelectLanguageActivity extends Activity implements OnClickListener {
    /* renamed from: a */
    TextView f8424a;
    /* renamed from: b */
    TextView f8425b;
    /* renamed from: c */
    TextView f8426c;
    /* renamed from: d */
    LocaleInfo f8427d = null;

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tvEn:
                this.f8425b.setBackgroundResource(R.drawable.rounded_border6);
                this.f8425b.setTextColor(C0235a.m1075c(getApplicationContext(), R.color.white));
                this.f8424a.setTextColor(C0235a.m1075c(getApplicationContext(), R.color.black));
                this.f8424a.setBackgroundResource(R.drawable.rounded_border10);
                this.f8427d = new LocaleInfo();
                this.f8427d.name = "English";
                this.f8427d.shortName = "en";
                this.f8427d.nameEnglish = "English";
                LocaleController.getInstance().applyLanguage(this.f8427d, true, true);
                intent = new Intent(getApplicationContext(), LaunchActivity.class);
                intent.putExtra("fromIntro", true);
                startActivity(intent);
                finish();
                return;
            case R.id.tvFa:
                this.f8424a.setBackgroundResource(R.drawable.rounded_border6);
                this.f8424a.setTextColor(C0235a.m1075c(getApplicationContext(), R.color.white));
                this.f8425b.setTextColor(C0235a.m1075c(getApplicationContext(), R.color.black));
                this.f8425b.setBackgroundResource(R.drawable.rounded_border10);
                this.f8427d = new LocaleInfo();
                this.f8427d.name = "پارسی";
                this.f8427d.shortName = "fa";
                this.f8427d.nameEnglish = "Parsi";
                LocaleController.getInstance().applyLanguage(this.f8427d, true, true);
                intent = new Intent(getApplicationContext(), LaunchActivity.class);
                intent.putExtra("fromIntro", true);
                startActivity(intent);
                finish();
                return;
            case R.id.tvNext:
                if (this.f8427d == null) {
                    this.f8427d = new LocaleInfo();
                    this.f8425b.setBackgroundResource(R.drawable.rounded_border6);
                    this.f8425b.setTextColor(C0235a.m1075c(getApplicationContext(), R.color.white));
                    this.f8424a.setTextColor(C0235a.m1075c(getApplicationContext(), R.color.black));
                    this.f8424a.setBackgroundResource(R.drawable.rounded_border10);
                    this.f8427d.name = "English";
                    this.f8427d.shortName = "en";
                    this.f8427d.nameEnglish = "English";
                }
                LocaleController.getInstance().applyLanguage(this.f8427d, true, true);
                intent = new Intent(getApplicationContext(), LaunchActivity.class);
                intent.putExtra("fromIntro", true);
                startActivity(intent);
                finish();
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_select_language);
        this.f8425b = (TextView) findViewById(R.id.tvEn);
        this.f8424a = (TextView) findViewById(R.id.tvFa);
        this.f8426c = (TextView) findViewById(R.id.tvNext);
        this.f8425b.setOnClickListener(this);
        this.f8424a.setOnClickListener(this);
        this.f8426c.setOnClickListener(this);
    }
}
