package org.telegram.p150b;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.telegram.customization.Model.ContactChangeLog;
import org.telegram.customization.Model.DialogStatus;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.service.C2840b;
import org.telegram.messenger.ApplicationLoader;

/* renamed from: org.telegram.b.a */
public class C2491a extends SQLiteOpenHelper {
    /* renamed from: a */
    private static SQLiteDatabase f8339a = null;

    public C2491a(Context context) {
        super(context, "favourites", null, 11);
        if (f8339a == null) {
            f8339a = getWritableDatabase();
        }
    }

    /* renamed from: a */
    static String m12201a(String str, String str2, String str3, String str4) {
        String str5 = "ALTER TABLE " + str + " ADD " + str2 + " " + str3;
        return !TextUtils.isEmpty(str4) ? str5 + " DEFAULT " + str4 : str5;
    }

    /* renamed from: a */
    private void m12202a(SQLiteDatabase sQLiteDatabase) {
        try {
            String str = "CREATE TABLE IF NOT EXISTS TABLE_DIALOG_STATUS(id INTEGER PRIMARY KEY ,has_hotgram INTEGER DEFAULT 0,invite_sent INTEGER DEFAULT 0 ,is_filter INTEGER DEFAULT 0)";
            Log.d("LEE", "CREATE_DIALOG_STATUS_TABLE" + str);
            sQLiteDatabase.execSQL(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: b */
    private void m12203b(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbl_favs_messages(id INTEGER PRIMARY KEY AUTOINCREMENT,chat_id INTEGER,msg_id INTEGER,cloud_id INTEGER DEFAULT -100 )");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: b */
    private boolean m12204b(ContactChangeLog contactChangeLog) {
        Exception e;
        String string;
        Throwable th;
        Cursor cursor = null;
        if (contactChangeLog == null) {
            return false;
        }
        Cursor cursor2;
        f8339a.beginTransaction();
        Cursor query;
        try {
            query = !f8339a.isDbLockedByOtherThreads() ? f8339a.query("TABLE_CONTACT_CHANGE_LOG", new String[]{TtmlNode.ATTR_ID}, "date = ? AND chat_id = ? ", new String[]{String.valueOf(contactChangeLog.getDate()), String.valueOf(contactChangeLog.getChatId())}, null, null, "date DESC ") : null;
            if (query != null) {
                try {
                    f8339a.setTransactionSuccessful();
                    if (query.moveToNext()) {
                        if (query != null) {
                            query.close();
                        }
                        f8339a.endTransaction();
                        return false;
                    }
                } catch (Exception e2) {
                    e = e2;
                    try {
                        e.printStackTrace();
                        if (query != null) {
                            query.close();
                        }
                        f8339a.endTransaction();
                        cursor2 = query;
                        f8339a.beginTransaction();
                        cursor2 = f8339a.query("TABLE_CONTACT_CHANGE_LOG", new String[]{"previous_name"}, "change_type = ? AND chat_id = ? ", new String[]{String.valueOf(contactChangeLog.getType()), String.valueOf(contactChangeLog.getChatId())}, null, null, "date DESC ", "1");
                        if (cursor2 != null) {
                            f8339a.setTransactionSuccessful();
                            while (cursor2.moveToNext()) {
                                string = cursor2.getString(0);
                                if (string != null) {
                                }
                            }
                        }
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        try {
                            f8339a.endTransaction();
                        } catch (Exception e3) {
                        }
                        return true;
                    } catch (Throwable th2) {
                        cursor = query;
                        th = th2;
                        if (cursor != null) {
                            cursor.close();
                        }
                        f8339a.endTransaction();
                        throw th;
                    }
                }
            }
            if (query != null) {
                query.close();
            }
            f8339a.endTransaction();
            cursor2 = query;
        } catch (Exception e4) {
            e = e4;
            query = null;
            e.printStackTrace();
            if (query != null) {
                query.close();
            }
            f8339a.endTransaction();
            cursor2 = query;
            f8339a.beginTransaction();
            cursor2 = f8339a.query("TABLE_CONTACT_CHANGE_LOG", new String[]{"previous_name"}, "change_type = ? AND chat_id = ? ", new String[]{String.valueOf(contactChangeLog.getType()), String.valueOf(contactChangeLog.getChatId())}, null, null, "date DESC ", "1");
            if (cursor2 != null) {
                f8339a.setTransactionSuccessful();
                while (cursor2.moveToNext()) {
                    string = cursor2.getString(0);
                    if (string != null) {
                    }
                }
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            f8339a.endTransaction();
            return true;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                cursor.close();
            }
            f8339a.endTransaction();
            throw th;
        }
        f8339a.beginTransaction();
        try {
            cursor2 = f8339a.query("TABLE_CONTACT_CHANGE_LOG", new String[]{"previous_name"}, "change_type = ? AND chat_id = ? ", new String[]{String.valueOf(contactChangeLog.getType()), String.valueOf(contactChangeLog.getChatId())}, null, null, "date DESC ", "1");
            if (cursor2 != null) {
                f8339a.setTransactionSuccessful();
                while (cursor2.moveToNext()) {
                    string = cursor2.getString(0);
                    if (string != null && string.equals(contactChangeLog.getPreviousName())) {
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        try {
                            f8339a.endTransaction();
                        } catch (Exception e5) {
                        }
                        return false;
                    }
                }
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            f8339a.endTransaction();
        } catch (Exception e42) {
            e42.printStackTrace();
            if (cursor2 != null) {
                cursor2.close();
            }
            try {
                f8339a.endTransaction();
            } catch (Exception e6) {
            }
        } catch (Throwable th4) {
            if (cursor2 != null) {
                cursor2.close();
            }
            try {
                f8339a.endTransaction();
            } catch (Exception e7) {
            }
            throw th4;
        }
        return true;
    }

    /* renamed from: c */
    private void m12205c(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbl_favs(id INTEGER PRIMARY KEY AUTOINCREMENT,chat_id INTEGER)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: c */
    public static boolean m12206c(long j) {
        DialogStatus b = ApplicationLoader.databaseHandler.m12220b(j);
        return b != null ? b.isFilter() : false;
    }

    /* renamed from: d */
    private void m12207d(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS TABLE_DOWNLOAD_QUEUE(id INTEGER PRIMARY KEY AUTOINCREMENT,chat_id INTEGER,msg_id INTEGER)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: e */
    private void m12208e(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS TABLE_CONTACT_CHANGE_LOG(id INTEGER PRIMARY KEY AUTOINCREMENT,chat_id INTEGER,change_type INTEGER, date INTEGER, previous_name TEXT)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    public java.util.ArrayList<org.telegram.customization.Model.Favourite> m12209a() {
        /*
        r10 = this;
        r8 = 0;
        r9 = new java.util.ArrayList;
        r9.<init>();
        r0 = f8339a;
        r0.beginTransaction();
        r0 = 3;
        r2 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0087, all -> 0x0074 }
        r0 = 0;
        r1 = "id";
        r2[r0] = r1;	 Catch:{ Exception -> 0x0087, all -> 0x0074 }
        r0 = 1;
        r1 = "chat_id";
        r2[r0] = r1;	 Catch:{ Exception -> 0x0087, all -> 0x0074 }
        r0 = 2;
        r1 = "msg_id";
        r2[r0] = r1;	 Catch:{ Exception -> 0x0087, all -> 0x0074 }
        r3 = "";
        r0 = 0;
        r4 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0087, all -> 0x0074 }
        r0 = f8339a;	 Catch:{ Exception -> 0x0087, all -> 0x0074 }
        r1 = "tbl_favs_messages";
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x0087, all -> 0x0074 }
        if (r8 == 0) goto L_0x0067;
    L_0x0034:
        r0 = r8.moveToNext();	 Catch:{ Exception -> 0x0052, all -> 0x0074 }
        if (r0 == 0) goto L_0x0062;
    L_0x003a:
        r1 = new org.telegram.customization.Model.Favourite;	 Catch:{ Exception -> 0x0052, all -> 0x0074 }
        r0 = 0;
        r2 = r8.getLong(r0);	 Catch:{ Exception -> 0x0052, all -> 0x0074 }
        r0 = 1;
        r4 = r8.getLong(r0);	 Catch:{ Exception -> 0x0052, all -> 0x0074 }
        r0 = 2;
        r6 = r8.getLong(r0);	 Catch:{ Exception -> 0x0052, all -> 0x0074 }
        r1.<init>(r2, r4, r6);	 Catch:{ Exception -> 0x0052, all -> 0x0074 }
        r9.add(r1);	 Catch:{ Exception -> 0x0052, all -> 0x0074 }
        goto L_0x0034;
    L_0x0052:
        r0 = move-exception;
        r1 = r8;
    L_0x0054:
        r0.printStackTrace();	 Catch:{ all -> 0x0082 }
        if (r1 == 0) goto L_0x005c;
    L_0x0059:
        r1.close();
    L_0x005c:
        r0 = f8339a;	 Catch:{ Exception -> 0x0085 }
        r0.endTransaction();	 Catch:{ Exception -> 0x0085 }
    L_0x0061:
        return r9;
    L_0x0062:
        r0 = f8339a;	 Catch:{ Exception -> 0x0052, all -> 0x0074 }
        r0.setTransactionSuccessful();	 Catch:{ Exception -> 0x0052, all -> 0x0074 }
    L_0x0067:
        if (r8 == 0) goto L_0x006c;
    L_0x0069:
        r8.close();
    L_0x006c:
        r0 = f8339a;	 Catch:{ Exception -> 0x0072 }
        r0.endTransaction();	 Catch:{ Exception -> 0x0072 }
        goto L_0x0061;
    L_0x0072:
        r0 = move-exception;
        goto L_0x0061;
    L_0x0074:
        r0 = move-exception;
    L_0x0075:
        if (r8 == 0) goto L_0x007a;
    L_0x0077:
        r8.close();
    L_0x007a:
        r1 = f8339a;	 Catch:{ Exception -> 0x0080 }
        r1.endTransaction();	 Catch:{ Exception -> 0x0080 }
    L_0x007f:
        throw r0;
    L_0x0080:
        r1 = move-exception;
        goto L_0x007f;
    L_0x0082:
        r0 = move-exception;
        r8 = r1;
        goto L_0x0075;
    L_0x0085:
        r0 = move-exception;
        goto L_0x0061;
    L_0x0087:
        r0 = move-exception;
        r1 = r8;
        goto L_0x0054;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.b.a.a():java.util.ArrayList<org.telegram.customization.Model.Favourite>");
    }

    /* renamed from: a */
    public ArrayList<ContactChangeLog> m12210a(int i) {
        Exception e;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        ArrayList<ContactChangeLog> arrayList = new ArrayList();
        f8339a.beginTransaction();
        try {
            String[] strArr = new String[]{TtmlNode.ATTR_ID, "chat_id", "change_type", "previous_name", "date"};
            Object obj = i != 0 ? "change_type = ?" : null;
            String[] strArr2 = new String[]{String.valueOf(i)};
            SQLiteDatabase sQLiteDatabase = f8339a;
            String str = "TABLE_CONTACT_CHANGE_LOG";
            if (TextUtils.isEmpty(obj)) {
                strArr2 = null;
            }
            Cursor query = sQLiteDatabase.query(str, strArr, obj, strArr2, null, null, "date DESC ", "200");
            if (query != null) {
                try {
                    f8339a.setTransactionSuccessful();
                    while (query.moveToNext()) {
                        arrayList.add(new ContactChangeLog(query.getInt(0), query.getLong(1), query.getInt(2), query.getString(3), query.getLong(4)));
                    }
                } catch (Exception e2) {
                    e = e2;
                    cursor = query;
                    try {
                        e.printStackTrace();
                        if (cursor != null) {
                            cursor.close();
                        }
                        try {
                            f8339a.endTransaction();
                        } catch (Exception e3) {
                        }
                        return arrayList;
                    } catch (Throwable th2) {
                        th = th2;
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        try {
                            f8339a.endTransaction();
                        } catch (Exception e4) {
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    cursor2 = query;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    f8339a.endTransaction();
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            try {
                f8339a.endTransaction();
            } catch (Exception e5) {
            }
        } catch (Exception e6) {
            e = e6;
            cursor = null;
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            f8339a.endTransaction();
            return arrayList;
        } catch (Throwable th4) {
            th = th4;
            if (cursor2 != null) {
                cursor2.close();
            }
            f8339a.endTransaction();
            throw th;
        }
        return arrayList;
    }

    /* renamed from: a */
    public Favourite m12211a(long j) {
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        f8339a.beginTransaction();
        try {
            String[] strArr = new String[]{TtmlNode.ATTR_ID, "chat_id"};
            String[] strArr2 = new String[]{String.valueOf(j)};
            Cursor query = f8339a.query("tbl_favs", strArr, "chat_id=?", strArr2, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        f8339a.setTransactionSuccessful();
                        Favourite favourite = new Favourite(query.getLong(1));
                        if (query != null) {
                            query.close();
                        }
                        try {
                            f8339a.endTransaction();
                            return favourite;
                        } catch (Exception e) {
                            return favourite;
                        }
                    }
                } catch (Exception e2) {
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    try {
                        f8339a.endTransaction();
                    } catch (Exception e3) {
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    cursor2 = query;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    try {
                        f8339a.endTransaction();
                    } catch (Exception e4) {
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            try {
                f8339a.endTransaction();
            } catch (Exception e5) {
            }
        } catch (Exception e6) {
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            f8339a.endTransaction();
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (cursor2 != null) {
                cursor2.close();
            }
            f8339a.endTransaction();
            throw th;
        }
        return null;
    }

    /* renamed from: a */
    public Favourite m12212a(Long l, long j) {
        Exception e;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        f8339a.beginTransaction();
        try {
            String[] strArr = new String[]{TtmlNode.ATTR_ID, "chat_id", "msg_id", "cloud_id"};
            String[] strArr2 = new String[]{String.valueOf(l), String.valueOf(j)};
            Cursor query = f8339a.query("tbl_favs_messages", strArr, "chat_id=? AND msg_id=?", strArr2, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        f8339a.setTransactionSuccessful();
                        Favourite favourite = new Favourite(query.getLong(0), query.getLong(1), query.getLong(2), query.getLong(3));
                        if (query != null) {
                            query.close();
                        }
                        try {
                            f8339a.endTransaction();
                            return favourite;
                        } catch (Exception e2) {
                            return favourite;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    cursor = query;
                    try {
                        e.printStackTrace();
                        if (cursor != null) {
                            cursor.close();
                        }
                        try {
                            f8339a.endTransaction();
                        } catch (Exception e4) {
                        }
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        try {
                            f8339a.endTransaction();
                        } catch (Exception e5) {
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    cursor2 = query;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    f8339a.endTransaction();
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            try {
                f8339a.endTransaction();
            } catch (Exception e6) {
            }
        } catch (Exception e7) {
            e = e7;
            cursor = null;
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            f8339a.endTransaction();
            return null;
        } catch (Throwable th4) {
            th = th4;
            if (cursor2 != null) {
                cursor2.close();
            }
            f8339a.endTransaction();
            throw th;
        }
        return null;
    }

    /* renamed from: a */
    public void m12213a(long j, boolean z) {
        f8339a.beginTransaction();
        ContentValues contentValues = new ContentValues();
        DialogStatus b = m12220b(j);
        if (b != null) {
            contentValues.put(TtmlNode.ATTR_ID, Long.valueOf(b.getDialogId()));
            contentValues.put("has_hotgram", Boolean.valueOf(b.isHasHotgram()));
            contentValues.put("invite_sent", Boolean.valueOf(z));
            String[] strArr = new String[]{String.valueOf(j)};
            f8339a.update("TABLE_DIALOG_STATUS", contentValues, "id=? ", strArr);
        }
        f8339a.setTransactionSuccessful();
        f8339a.endTransaction();
    }

    /* renamed from: a */
    public void m12214a(Long l) {
        f8339a.beginTransaction();
        f8339a.delete("tbl_favs", "chat_id = ?", new String[]{String.valueOf(l)});
        f8339a.setTransactionSuccessful();
        f8339a.endTransaction();
    }

    /* renamed from: a */
    public void m12215a(Long l, Long l2) {
        f8339a.beginTransaction();
        f8339a.delete("tbl_favs_messages", "chat_id = ? AND msg_id = ?", new String[]{String.valueOf(l), String.valueOf(l2)});
        f8339a.setTransactionSuccessful();
        f8339a.endTransaction();
    }

    /* renamed from: a */
    public void m12216a(ArrayList<Integer> arrayList) {
        String join = TextUtils.join(",", arrayList);
        f8339a.beginTransaction();
        f8339a.delete("tbl_favs_messages", "cloud_id IN (?)", new String[]{join});
        f8339a.setTransactionSuccessful();
        f8339a.endTransaction();
    }

    /* renamed from: a */
    public void m12217a(ContactChangeLog contactChangeLog) {
        if (m12204b(contactChangeLog)) {
            f8339a.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("chat_id", Long.valueOf(contactChangeLog.getChatId()));
            contentValues.put("change_type", Integer.valueOf(contactChangeLog.getType()));
            contentValues.put("date", Long.valueOf(contactChangeLog.getDate()));
            contentValues.put("previous_name", contactChangeLog.getPreviousName());
            f8339a.insert("TABLE_CONTACT_CHANGE_LOG", null, contentValues);
            f8339a.setTransactionSuccessful();
            f8339a.endTransaction();
        }
    }

    /* renamed from: a */
    public void m12218a(DialogStatus dialogStatus) {
        DialogStatus b = m12220b(dialogStatus.getDialogId());
        f8339a.beginTransaction();
        ContentValues contentValues = new ContentValues();
        if (b != null) {
            contentValues.put(TtmlNode.ATTR_ID, Long.valueOf(dialogStatus.getDialogId()));
            contentValues.put("has_hotgram", Boolean.valueOf(b.isHasHotgram()));
            contentValues.put("invite_sent", Boolean.valueOf(b.isInviteSent()));
            contentValues.put("is_filter", Boolean.valueOf(dialogStatus.isFilter()));
            String[] strArr = new String[]{String.valueOf(b.getDialogId())};
            f8339a.update("TABLE_DIALOG_STATUS", contentValues, "id=? ", strArr);
        } else {
            contentValues.put(TtmlNode.ATTR_ID, Long.valueOf(dialogStatus.getDialogId()));
            contentValues.put("has_hotgram", Boolean.valueOf(dialogStatus.isHasHotgram()));
            contentValues.put("invite_sent", Boolean.valueOf(dialogStatus.isInviteSent()));
            contentValues.put("is_filter", Boolean.valueOf(dialogStatus.isFilter()));
            f8339a.insert("TABLE_DIALOG_STATUS", null, contentValues);
        }
        f8339a.setTransactionSuccessful();
        f8339a.endTransaction();
    }

    /* renamed from: a */
    public void m12219a(Favourite favourite) {
        f8339a.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("chat_id", Long.valueOf(favourite.getChatID()));
        f8339a.insert("tbl_favs", null, contentValues);
        f8339a.setTransactionSuccessful();
        f8339a.endTransaction();
    }

    /* renamed from: b */
    public DialogStatus m12220b(long j) {
        Exception e;
        Throwable th;
        Cursor cursor = null;
        f8339a.beginTransaction();
        Cursor query;
        try {
            query = f8339a.query("TABLE_DIALOG_STATUS", new String[]{TtmlNode.ATTR_ID, "has_hotgram", "invite_sent", "is_filter"}, "id=? ", new String[]{String.valueOf(j)}, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        f8339a.setTransactionSuccessful();
                        DialogStatus dialogStatus = new DialogStatus();
                        dialogStatus.setDialogId(query.getLong(0));
                        dialogStatus.setHasHotgram(query.getInt(1) == 1);
                        dialogStatus.setInviteSent(query.getInt(2) == 1);
                        dialogStatus.setFilter(query.getInt(3) == 1);
                        if (query != null) {
                            query.close();
                        }
                        try {
                            f8339a.endTransaction();
                            return dialogStatus;
                        } catch (Exception e2) {
                            return dialogStatus;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    try {
                        e.printStackTrace();
                        if (query != null) {
                            query.close();
                        }
                        try {
                            f8339a.endTransaction();
                        } catch (Exception e4) {
                        }
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = query;
                        if (cursor != null) {
                            cursor.close();
                        }
                        try {
                            f8339a.endTransaction();
                        } catch (Exception e5) {
                        }
                        throw th;
                    }
                }
            }
            if (query != null) {
                query.close();
            }
            try {
                f8339a.endTransaction();
            } catch (Exception e6) {
            }
        } catch (Exception e7) {
            e = e7;
            query = null;
            e.printStackTrace();
            if (query != null) {
                query.close();
            }
            f8339a.endTransaction();
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                cursor.close();
            }
            f8339a.endTransaction();
            throw th;
        }
        return null;
    }

    /* renamed from: b */
    public void m12221b(Favourite favourite) {
        f8339a.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("chat_id", Long.valueOf(favourite.getChatID()));
        contentValues.put("msg_id", Long.valueOf(favourite.getMsg_id()));
        contentValues.put("cloud_id", Long.valueOf(favourite.getCloudId()));
        f8339a.insert("tbl_favs_messages", null, contentValues);
        f8339a.setTransactionSuccessful();
        f8339a.endTransaction();
    }

    /* renamed from: b */
    public boolean m12222b() {
        Exception e;
        Throwable th;
        Cursor cursor = null;
        f8339a.beginTransaction();
        Cursor query;
        try {
            boolean z;
            query = f8339a.query("tbl_favs_messages", new String[]{TtmlNode.ATTR_ID}, TtmlNode.ANONYMOUS_REGION_ID, new String[0], null, null, null);
            if (query != null) {
                try {
                    f8339a.setTransactionSuccessful();
                    if (query.getCount() > 0) {
                        z = true;
                        if (query != null) {
                            query.close();
                        }
                        f8339a.endTransaction();
                        return z;
                    }
                } catch (Exception e2) {
                    e = e2;
                    try {
                        e.printStackTrace();
                        if (query != null) {
                            query.close();
                        }
                        try {
                            f8339a.endTransaction();
                            return false;
                        } catch (Exception e3) {
                            return false;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = query;
                        if (cursor != null) {
                            cursor.close();
                        }
                        try {
                            f8339a.endTransaction();
                        } catch (Exception e4) {
                        }
                        throw th;
                    }
                }
            }
            z = false;
            if (query != null) {
                query.close();
            }
            try {
                f8339a.endTransaction();
                return z;
            } catch (Exception e5) {
                return z;
            }
        } catch (Exception e6) {
            e = e6;
            query = null;
            e.printStackTrace();
            if (query != null) {
                query.close();
            }
            f8339a.endTransaction();
            return false;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                cursor.close();
            }
            f8339a.endTransaction();
            throw th;
        }
    }

    /* renamed from: c */
    public List<Favourite> m12223c() {
        Throwable th;
        Cursor cursor = null;
        List<Favourite> arrayList = new ArrayList();
        f8339a.beginTransaction();
        Cursor query;
        try {
            query = f8339a.query("tbl_favs", new String[]{TtmlNode.ATTR_ID, "chat_id"}, TtmlNode.ANONYMOUS_REGION_ID, new String[0], null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        do {
                            Favourite favourite = new Favourite();
                            favourite.setID((long) Integer.parseInt(query.getString(0)));
                            favourite.setChatID(query.getLong(1));
                            arrayList.add(favourite);
                        } while (query.moveToNext());
                    }
                } catch (Exception e) {
                    if (query != null) {
                        query.close();
                    }
                    try {
                        f8339a.endTransaction();
                    } catch (Exception e2) {
                    }
                    return arrayList;
                } catch (Throwable th2) {
                    cursor = query;
                    th = th2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    try {
                        f8339a.endTransaction();
                    } catch (Exception e3) {
                    }
                    throw th;
                }
            }
            f8339a.setTransactionSuccessful();
            if (query != null) {
                query.close();
            }
            try {
                f8339a.endTransaction();
            } catch (Exception e4) {
            }
        } catch (Exception e5) {
            query = null;
            if (query != null) {
                query.close();
            }
            f8339a.endTransaction();
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                cursor.close();
            }
            f8339a.endTransaction();
            throw th;
        }
        return arrayList;
    }

    public void close() {
        try {
            f8339a.close();
        } catch (Exception e) {
        }
    }

    /* renamed from: d */
    public void m12224d() {
        f8339a.beginTransaction();
        f8339a.delete("TABLE_CONTACT_CHANGE_LOG", " 1 ", null);
        f8339a.setTransactionSuccessful();
        f8339a.endTransaction();
    }

    /* renamed from: e */
    public void m12225e() {
        f8339a.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_filter", Boolean.valueOf(false));
        f8339a.update("TABLE_DIALOG_STATUS", contentValues, null, null);
        f8339a.setTransactionSuccessful();
        f8339a.endTransaction();
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        m12203b(sQLiteDatabase);
        m12205c(sQLiteDatabase);
        m12207d(sQLiteDatabase);
        m12208e(sQLiteDatabase);
        m12202a(sQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        m12203b(sQLiteDatabase);
        m12205c(sQLiteDatabase);
        m12207d(sQLiteDatabase);
        m12208e(sQLiteDatabase);
        switch (i) {
            case 1:
            case 2:
                try {
                    sQLiteDatabase.execSQL(C2491a.m12201a("tbl_favs_messages", "cloud_id", "INTEGER", "-100"));
                    ApplicationLoader.applicationContext.startService(new Intent(ApplicationLoader.applicationContext, C2840b.class));
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            case 3:
                break;
            case 4:
            case 5:
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                break;
            default:
                return;
        }
        m12208e(sQLiteDatabase);
        m12202a(sQLiteDatabase);
        try {
            sQLiteDatabase.execSQL(C2491a.m12201a("TABLE_DIALOG_STATUS", "is_filter", "INTEGER", "0"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
