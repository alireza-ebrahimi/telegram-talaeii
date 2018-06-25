package org.telegram.customization.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.persianswitch.sdk.base.manager.LanguageManager;
import org.ir.talaeii.R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocaleController$LocaleInfo;
import org.telegram.ui.LaunchActivity;

public class SelectLanguageActivity extends Activity implements OnClickListener {
    LocaleController$LocaleInfo localeInfo = null;
    TextView textViewEn;
    TextView textViewFa;
    TextView textViewNext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        this.textViewEn = (TextView) findViewById(R.id.tvEn);
        this.textViewFa = (TextView) findViewById(R.id.tvFa);
        this.textViewNext = (TextView) findViewById(R.id.tvNext);
        this.textViewEn.setOnClickListener(this);
        this.textViewFa.setOnClickListener(this);
        this.textViewNext.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvEn:
                this.textViewEn.setBackgroundResource(R.drawable.rounded_border6);
                this.textViewEn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                this.textViewFa.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                this.textViewFa.setBackgroundResource(R.drawable.rounded_border10);
                this.localeInfo = new LocaleController$LocaleInfo();
                this.localeInfo.name = "English";
                this.localeInfo.shortName = LanguageManager.ENGLISH;
                this.localeInfo.nameEnglish = "English";
                LocaleController.getInstance().applyLanguage(this.localeInfo, true, true);
                Intent intent3 = new Intent(getApplicationContext(), LaunchActivity.class);
                intent3.putExtra("fromIntro", true);
                startActivity(intent3);
                finish();
                return;
            case R.id.tvFa:
                this.textViewFa.setBackgroundResource(R.drawable.rounded_border6);
                this.textViewFa.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                this.textViewEn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                this.textViewEn.setBackgroundResource(R.drawable.rounded_border10);
                this.localeInfo = new LocaleController$LocaleInfo();
                this.localeInfo.name = "پارسی";
                this.localeInfo.shortName = LanguageManager.PERSIAN;
                this.localeInfo.nameEnglish = "Parsi";
                LocaleController.getInstance().applyLanguage(this.localeInfo, true, true);
                Intent intent2 = new Intent(getApplicationContext(), LaunchActivity.class);
                intent2.putExtra("fromIntro", true);
                startActivity(intent2);
                finish();
                return;
            case R.id.tvNext:
                if (this.localeInfo == null) {
                    this.localeInfo = new LocaleController$LocaleInfo();
                    this.textViewEn.setBackgroundResource(R.drawable.rounded_border6);
                    this.textViewEn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    this.textViewFa.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                    this.textViewFa.setBackgroundResource(R.drawable.rounded_border10);
                    this.localeInfo.name = "English";
                    this.localeInfo.shortName = LanguageManager.ENGLISH;
                    this.localeInfo.nameEnglish = "English";
                }
                LocaleController.getInstance().applyLanguage(this.localeInfo, true, true);
                Intent intent4 = new Intent(getApplicationContext(), LaunchActivity.class);
                intent4.putExtra("fromIntro", true);
                startActivity(intent4);
                finish();
                return;
            default:
                return;
        }
    }
}
