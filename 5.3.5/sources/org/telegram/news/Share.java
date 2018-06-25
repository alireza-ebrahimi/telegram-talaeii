package org.telegram.news;

import android.content.Intent;
import android.util.Log;
import com.persianswitch.sdk.base.log.LogCollector;

public class Share {
    public static Intent createShareIntent(String title, String descrption, String link, boolean isManual) {
        Intent share = new Intent("android.intent.action.SEND");
        share.setType("text/plain");
        share.addFlags(524288);
        share.putExtra("android.intent.extra.SUBJECT", title);
        String shareText = "";
        if (isManual) {
            shareText = title + LogCollector.LINE_SEPARATOR + descrption + LogCollector.LINE_SEPARATOR + link + "\n\n\n" + "آگهی:" + LogCollector.LINE_SEPARATOR + "تگ" + LogCollector.LINE_SEPARATOR + "http://app.tag.ir/tag.apk";
        } else {
            shareText = descrption;
            Log.d("sadegh", "is not manuallll");
        }
        share.putExtra("android.intent.extra.TEXT", shareText);
        return Intent.createChooser(share, "نرم افزار تگ-انتشار خبر");
    }

    public static Intent shareInSpecApp(String uri, String title, String desc, String link, boolean isManual) {
        Intent share = new Intent("android.intent.action.SEND");
        share.setType("text/plain");
        share.addFlags(524288);
        share.putExtra("android.intent.extra.SUBJECT", title);
        String shareText = "";
        if (isManual) {
            shareText = title + LogCollector.LINE_SEPARATOR + desc + LogCollector.LINE_SEPARATOR + link + "\n\n\n" + "آگهی:" + LogCollector.LINE_SEPARATOR + "تگ" + LogCollector.LINE_SEPARATOR + "http://app.tag.ir/tag.apk";
        } else {
            shareText = desc;
            Log.d("sadegh", "is not manuallll");
        }
        share.putExtra("android.intent.extra.TEXT", shareText);
        share.setPackage(uri);
        return share;
    }
}
