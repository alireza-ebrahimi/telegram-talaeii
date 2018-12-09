package com.mohamadamin.persianmaterialdatetimepicker.p121a;

import java.util.ArrayList;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.a.a */
public class C2017a {
    /* renamed from: a */
    public static String m9087a(String str) {
        return str.replace("0", "۰").replace("1", "١").replace("2", "۲").replace("3", "۳").replace("4", "۴").replace("5", "۵").replace("6", "۶").replace("7", "۷").replace("8", "۸").replace("9", "۹");
    }

    /* renamed from: a */
    public static ArrayList<String> m9088a(ArrayList<String> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.set(i, C2017a.m9087a((String) arrayList.get(i)));
        }
        return arrayList;
    }

    /* renamed from: a */
    public static String[] m9089a(String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = C2017a.m9087a(strArr[i]);
        }
        return strArr;
    }

    /* renamed from: b */
    public static String m9090b(String str) {
        return str.replace("۰", "0").replace("١", "1").replace("۲", "2").replace("۳", "3").replace("۴", "4").replace("۵", "5").replace("۶", "6").replace("۷", "7").replace("۸", "8").replace("۹", "9");
    }
}
