package org.telegram.SLSSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import com.thin.downloadmanager.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import org.telegram.customization.Model.ContactChangeLog;
import org.telegram.customization.Model.DialogStatus;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.service.ForwardFavMessage;
import org.telegram.customization.util.DownloadManager;
import org.telegram.messenger.ApplicationLoader;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String COL_CHANGE_TYPE = "change_type";
    private static final String COL_CHAT_ID = "chat_id";
    private static final String COL_DATE = "date";
    private static final String COL_ID = "id";
    private static final String COL_PREVIOUS_NAME = "previous_name";
    private static final String DATABASE_NAME = "favourites";
    private static final int DATABASE_VERSION = 11;
    public static final String DIALOG_STATUS_COL_FILTER = "is_filter";
    public static final String DIALOG_STATUS_COL_HAS_HOTGRAM = "has_hotgram";
    public static final String DIALOG_STATUS_COL_ID = "id";
    public static final String DIALOG_STATUS_COL_INVITE_SENT = "invite_sent";
    private static final String KEY_CHAT_ID = "chat_id";
    private static final String KEY_CLOUD_ID = "cloud_id";
    private static final String KEY_ID = "id";
    private static final String KEY_MSG_ID = "msg_id";
    private static final String TABLE_CONTACT_CHANGE_LOG = "TABLE_CONTACT_CHANGE_LOG";
    private static final String TABLE_DIALOG_STATUS = "TABLE_DIALOG_STATUS";
    private static final String TABLE_DOWNLOAD_QUEUE = "TABLE_DOWNLOAD_QUEUE";
    private static final String TABLE_FAVS = "tbl_favs";
    private static final String TABLE_FAVS_MESSAGES = "tbl_favs_messages";
    private static SQLiteDatabase db = null;

    public java.util.ArrayList<org.telegram.customization.Model.ContactChangeLog> getContactChangeLogWithType(int r17) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x008c in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r16 = this;
        r14 = new java.util.ArrayList;
        r14.<init>();
        r0 = db;
        r0.beginTransaction();
        r12 = 0;
        r0 = 5;
        r2 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = 0;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r1 = "id";	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = 1;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r1 = "chat_id";	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = 2;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r1 = "change_type";	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = 3;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r1 = "previous_name";	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = 4;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r1 = "date";	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r3 = 0;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        if (r17 == 0) goto L_0x0032;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
    L_0x002f:
        r3 = "change_type = ?";	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
    L_0x0032:
        r0 = 1;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r15 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = 0;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r1 = java.lang.String.valueOf(r17);	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r15[r0] = r1;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = db;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r1 = "TABLE_CONTACT_CHANGE_LOG";	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r5 = android.text.TextUtils.isEmpty(r3);	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        if (r5 != 0) goto L_0x0092;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
    L_0x0047:
        r4 = r15;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
    L_0x0048:
        r5 = 0;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r6 = 0;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r7 = "date DESC ";	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r8 = "200";	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r12 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        if (r12 == 0) goto L_0x0094;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
    L_0x0056:
        r0 = db;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0.setTransactionSuccessful();	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
    L_0x005b:
        r0 = r12.moveToNext();	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        if (r0 == 0) goto L_0x0094;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
    L_0x0061:
        r4 = new org.telegram.customization.Model.ContactChangeLog;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = 0;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r5 = r12.getInt(r0);	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = 1;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r6 = r12.getLong(r0);	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = 2;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r8 = r12.getInt(r0);	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = 3;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r9 = r12.getString(r0);	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r0 = 4;	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r10 = r12.getLong(r0);	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r4.<init>(r5, r6, r8, r9, r10);	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        r14.add(r4);	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        goto L_0x005b;
    L_0x0083:
        r13 = move-exception;
        r13.printStackTrace();	 Catch:{ Exception -> 0x0083, all -> 0x00a1 }
        if (r12 == 0) goto L_0x008c;
    L_0x0089:
        r12.close();
    L_0x008c:
        r0 = db;	 Catch:{ Exception -> 0x00af }
        r0.endTransaction();	 Catch:{ Exception -> 0x00af }
    L_0x0091:
        return r14;
    L_0x0092:
        r4 = 0;
        goto L_0x0048;
    L_0x0094:
        if (r12 == 0) goto L_0x0099;
    L_0x0096:
        r12.close();
    L_0x0099:
        r0 = db;	 Catch:{ Exception -> 0x009f }
        r0.endTransaction();	 Catch:{ Exception -> 0x009f }
        goto L_0x0091;
    L_0x009f:
        r0 = move-exception;
        goto L_0x0091;
    L_0x00a1:
        r0 = move-exception;
        if (r12 == 0) goto L_0x00a7;
    L_0x00a4:
        r12.close();
    L_0x00a7:
        r1 = db;	 Catch:{ Exception -> 0x00ad }
        r1.endTransaction();	 Catch:{ Exception -> 0x00ad }
    L_0x00ac:
        throw r0;
    L_0x00ad:
        r1 = move-exception;
        goto L_0x00ac;
    L_0x00af:
        r0 = move-exception;
        goto L_0x0091;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.SLSSQLite.DatabaseHandler.getContactChangeLogWithType(int):java.util.ArrayList<org.telegram.customization.Model.ContactChangeLog>");
    }

    public java.util.ArrayList<org.telegram.customization.Model.Favourite> getFavouriteMessages() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005b in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r15 = this;
        r14 = new java.util.ArrayList;
        r14.<init>();
        r0 = db;
        r0.beginTransaction();
        r12 = 0;
        r0 = 3;
        r2 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r0 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r1 = "id";	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r0 = 1;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r1 = "chat_id";	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r0 = 2;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r1 = "msg_id";	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r3 = "";	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r0 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r4 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r0 = db;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r1 = "tbl_favs_messages";	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r5 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r6 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r7 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r12 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        if (r12 == 0) goto L_0x0066;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
    L_0x0034:
        r0 = r12.moveToNext();	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        if (r0 == 0) goto L_0x0061;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
    L_0x003a:
        r5 = new org.telegram.customization.Model.Favourite;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r0 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r6 = r12.getLong(r0);	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r0 = 1;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r8 = r12.getLong(r0);	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r0 = 2;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r10 = r12.getLong(r0);	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r5.<init>(r6, r8, r10);	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r14.add(r5);	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        goto L_0x0034;
    L_0x0052:
        r13 = move-exception;
        r13.printStackTrace();	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        if (r12 == 0) goto L_0x005b;
    L_0x0058:
        r12.close();
    L_0x005b:
        r0 = db;	 Catch:{ Exception -> 0x0081 }
        r0.endTransaction();	 Catch:{ Exception -> 0x0081 }
    L_0x0060:
        return r14;
    L_0x0061:
        r0 = db;	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
        r0.setTransactionSuccessful();	 Catch:{ Exception -> 0x0052, all -> 0x0073 }
    L_0x0066:
        if (r12 == 0) goto L_0x006b;
    L_0x0068:
        r12.close();
    L_0x006b:
        r0 = db;	 Catch:{ Exception -> 0x0071 }
        r0.endTransaction();	 Catch:{ Exception -> 0x0071 }
        goto L_0x0060;
    L_0x0071:
        r0 = move-exception;
        goto L_0x0060;
    L_0x0073:
        r0 = move-exception;
        if (r12 == 0) goto L_0x0079;
    L_0x0076:
        r12.close();
    L_0x0079:
        r1 = db;	 Catch:{ Exception -> 0x007f }
        r1.endTransaction();	 Catch:{ Exception -> 0x007f }
    L_0x007e:
        throw r0;
    L_0x007f:
        r1 = move-exception;
        goto L_0x007e;
    L_0x0081:
        r0 = move-exception;
        goto L_0x0060;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.SLSSQLite.DatabaseHandler.getFavouriteMessages():java.util.ArrayList<org.telegram.customization.Model.Favourite>");
    }

    public java.util.ArrayList<org.telegram.customization.util.DownloadManager> getMessagesFromDownloadQueue() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005b in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r15 = this;
        r14 = new java.util.ArrayList;
        r14.<init>();
        r0 = db;
        r0.beginTransaction();
        r12 = 0;
        r0 = 3;
        r2 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r0 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r1 = "id";	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r0 = 1;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r1 = "chat_id";	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r0 = 2;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r1 = "msg_id";	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r3 = "";	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r0 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r4 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r0 = db;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r1 = "TABLE_DOWNLOAD_QUEUE";	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r5 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r6 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r7 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r12 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        if (r12 == 0) goto L_0x0061;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
    L_0x0034:
        r0 = r12.moveToNext();	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        if (r0 == 0) goto L_0x0061;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
    L_0x003a:
        r5 = new org.telegram.customization.util.DownloadManager;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r0 = 0;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r6 = r12.getLong(r0);	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r0 = 1;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r8 = r12.getLong(r0);	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r0 = 2;	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r10 = r12.getLong(r0);	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r5.<init>(r6, r8, r10);	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        r14.add(r5);	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        goto L_0x0034;
    L_0x0052:
        r13 = move-exception;
        r13.printStackTrace();	 Catch:{ Exception -> 0x0052, all -> 0x006e }
        if (r12 == 0) goto L_0x005b;
    L_0x0058:
        r12.close();
    L_0x005b:
        r0 = db;	 Catch:{ Exception -> 0x007c }
        r0.endTransaction();	 Catch:{ Exception -> 0x007c }
    L_0x0060:
        return r14;
    L_0x0061:
        if (r12 == 0) goto L_0x0066;
    L_0x0063:
        r12.close();
    L_0x0066:
        r0 = db;	 Catch:{ Exception -> 0x006c }
        r0.endTransaction();	 Catch:{ Exception -> 0x006c }
        goto L_0x0060;
    L_0x006c:
        r0 = move-exception;
        goto L_0x0060;
    L_0x006e:
        r0 = move-exception;
        if (r12 == 0) goto L_0x0074;
    L_0x0071:
        r12.close();
    L_0x0074:
        r1 = db;	 Catch:{ Exception -> 0x007a }
        r1.endTransaction();	 Catch:{ Exception -> 0x007a }
    L_0x0079:
        throw r0;
    L_0x007a:
        r1 = move-exception;
        goto L_0x0079;
    L_0x007c:
        r0 = move-exception;
        goto L_0x0060;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.SLSSQLite.DatabaseHandler.getMessagesFromDownloadQueue():java.util.ArrayList<org.telegram.customization.util.DownloadManager>");
    }

    public boolean hasFavouriteMessages() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0044 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r11 = this;
        r10 = 0;
        r0 = db;
        r0.beginTransaction();
        r8 = 0;
        r0 = 1;
        r2 = new java.lang.String[r0];	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r0 = 0;	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r1 = "id";	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r2[r0] = r1;	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r3 = "";	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r0 = 0;	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r4 = new java.lang.String[r0];	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r0 = db;	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r1 = "tbl_favs_messages";	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r5 = 0;	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r6 = 0;	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r7 = 0;	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r8 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        if (r8 == 0) goto L_0x0030;	 Catch:{ Exception -> 0x003b, all -> 0x004c }
    L_0x0024:
        r0 = db;	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r0.setTransactionSuccessful();	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        r0 = r8.getCount();	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        if (r0 <= 0) goto L_0x0030;
    L_0x002f:
        r10 = 1;
    L_0x0030:
        if (r8 == 0) goto L_0x0035;
    L_0x0032:
        r8.close();
    L_0x0035:
        r0 = db;	 Catch:{ Exception -> 0x005a }
        r0.endTransaction();	 Catch:{ Exception -> 0x005a }
    L_0x003a:
        return r10;
    L_0x003b:
        r9 = move-exception;
        r9.printStackTrace();	 Catch:{ Exception -> 0x003b, all -> 0x004c }
        if (r8 == 0) goto L_0x0044;
    L_0x0041:
        r8.close();
    L_0x0044:
        r0 = db;	 Catch:{ Exception -> 0x004a }
        r0.endTransaction();	 Catch:{ Exception -> 0x004a }
        goto L_0x003a;
    L_0x004a:
        r0 = move-exception;
        goto L_0x003a;
    L_0x004c:
        r0 = move-exception;
        if (r8 == 0) goto L_0x0052;
    L_0x004f:
        r8.close();
    L_0x0052:
        r1 = db;	 Catch:{ Exception -> 0x0058 }
        r1.endTransaction();	 Catch:{ Exception -> 0x0058 }
    L_0x0057:
        throw r0;
    L_0x0058:
        r1 = move-exception;
        goto L_0x0057;
    L_0x005a:
        r0 = move-exception;
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.SLSSQLite.DatabaseHandler.hasFavouriteMessages():boolean");
    }

    public boolean isDownloadQueueEmpty() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0049 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r13 = this;
        r11 = 1;
        r12 = 0;
        r10 = 0;
        r0 = db;
        r0.beginTransaction();
        r8 = 0;
        r0 = 1;
        r2 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r0 = 0;	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r1 = "id";	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r2[r0] = r1;	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r3 = "";	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r0 = 0;	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r4 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r0 = db;	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r1 = "TABLE_DOWNLOAD_QUEUE";	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r5 = 0;	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r6 = 0;	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r7 = 0;	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r8 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        if (r8 == 0) goto L_0x0032;	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
    L_0x0026:
        r0 = db;	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r0.setTransactionSuccessful();	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        r0 = r8.getCount();	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        if (r0 <= 0) goto L_0x0032;
    L_0x0031:
        r10 = 1;
    L_0x0032:
        if (r8 == 0) goto L_0x0037;
    L_0x0034:
        r8.close();
    L_0x0037:
        r0 = db;	 Catch:{ Exception -> 0x0061 }
        r0.endTransaction();	 Catch:{ Exception -> 0x0061 }
    L_0x003c:
        if (r10 != 0) goto L_0x005d;
    L_0x003e:
        r0 = r11;
    L_0x003f:
        return r0;
    L_0x0040:
        r9 = move-exception;
        r9.printStackTrace();	 Catch:{ Exception -> 0x0040, all -> 0x0051 }
        if (r8 == 0) goto L_0x0049;
    L_0x0046:
        r8.close();
    L_0x0049:
        r0 = db;	 Catch:{ Exception -> 0x004f }
        r0.endTransaction();	 Catch:{ Exception -> 0x004f }
        goto L_0x003c;
    L_0x004f:
        r0 = move-exception;
        goto L_0x003c;
    L_0x0051:
        r0 = move-exception;
        if (r8 == 0) goto L_0x0057;
    L_0x0054:
        r8.close();
    L_0x0057:
        r1 = db;	 Catch:{ Exception -> 0x005f }
        r1.endTransaction();	 Catch:{ Exception -> 0x005f }
    L_0x005c:
        throw r0;
    L_0x005d:
        r0 = r12;
        goto L_0x003f;
    L_0x005f:
        r1 = move-exception;
        goto L_0x005c;
    L_0x0061:
        r0 = move-exception;
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.SLSSQLite.DatabaseHandler.isDownloadQueueEmpty():boolean");
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 11);
        if (db == null) {
            db = getWritableDatabase();
        }
    }

    public void close() {
        try {
            db.close();
        } catch (Exception e) {
        }
    }

    public void onCreate(SQLiteDatabase db) {
        createFavoriteDialogsTable(db);
        createFavoriteMessageTable(db);
        createDownloadTable(db);
        createContactChangeTable(db);
        createDialogStatus(db);
    }

    private void createDialogStatus(SQLiteDatabase db) {
        try {
            String CREATE_DIALOG_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS TABLE_DIALOG_STATUS(id INTEGER PRIMARY KEY ,has_hotgram INTEGER DEFAULT 0,invite_sent INTEGER DEFAULT 0 ,is_filter INTEGER DEFAULT 0)";
            Log.d("LEE", "CREATE_DIALOG_STATUS_TABLE" + CREATE_DIALOG_STATUS_TABLE);
            db.execSQL(CREATE_DIALOG_STATUS_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFavoriteDialogsTable(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_favs_messages(id INTEGER PRIMARY KEY AUTOINCREMENT,chat_id INTEGER,msg_id INTEGER,cloud_id INTEGER DEFAULT -100 )");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFavoriteMessageTable(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_favs(id INTEGER PRIMARY KEY AUTOINCREMENT,chat_id INTEGER)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDownloadTable(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TABLE_DOWNLOAD_QUEUE(id INTEGER PRIMARY KEY AUTOINCREMENT,chat_id INTEGER,msg_id INTEGER)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createContactChangeTable(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TABLE_CONTACT_CHANGE_LOG(id INTEGER PRIMARY KEY AUTOINCREMENT,chat_id INTEGER,change_type INTEGER, date INTEGER, previous_name TEXT)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        createFavoriteDialogsTable(db);
        createFavoriteMessageTable(db);
        createDownloadTable(db);
        createContactChangeTable(db);
        switch (oldVersion) {
            case 1:
            case 2:
                try {
                    db.execSQL(getAddColumnQuery(TABLE_FAVS_MESSAGES, KEY_CLOUD_ID, "INTEGER", "-100"));
                    ApplicationLoader.applicationContext.startService(new Intent(ApplicationLoader.applicationContext, ForwardFavMessage.class));
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
        createContactChangeTable(db);
        createDialogStatus(db);
        try {
            db.execSQL(getAddColumnQuery(TABLE_DIALOG_STATUS, DIALOG_STATUS_COL_FILTER, "INTEGER", "0"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    static String getAddColumnQuery(String tbl, String column, String dataType, String defaultVal) {
        String ans = "ALTER TABLE " + tbl + " ADD " + column + " " + dataType;
        if (TextUtils.isEmpty(defaultVal)) {
            return ans;
        }
        return ans + " DEFAULT " + defaultVal;
    }

    public void addFavourite(Favourite favourite) {
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("chat_id", Long.valueOf(favourite.getChatID()));
        db.insert(TABLE_FAVS, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Favourite getFavouriteByChatId(long chat_id) {
        db.beginTransaction();
        Cursor cursor = null;
        try {
            String[] projection = new String[]{"id", "chat_id"};
            String[] whereArgs = new String[]{String.valueOf(chat_id)};
            cursor = db.query(TABLE_FAVS, projection, "chat_id=?", whereArgs, null, null, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                try {
                    db.endTransaction();
                } catch (Exception e) {
                }
                return null;
            }
            db.setTransactionSuccessful();
            Favourite favourite = new Favourite(cursor.getLong(1));
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
                return favourite;
            } catch (Exception e2) {
                return favourite;
            }
        } catch (Exception e3) {
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e4) {
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e5) {
            }
            throw th;
        }
    }

    public void deleteFavourite(Long chat_id) {
        db.beginTransaction();
        db.delete(TABLE_FAVS, "chat_id = ?", new String[]{String.valueOf(chat_id)});
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void addFavouriteMessage(Favourite favourite) {
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("chat_id", Long.valueOf(favourite.getChatID()));
        values.put(KEY_MSG_ID, Long.valueOf(favourite.getMsg_id()));
        values.put(KEY_CLOUD_ID, Long.valueOf(favourite.getCloudId()));
        db.insert(TABLE_FAVS_MESSAGES, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Favourite getFavouriteMessageByMessageId(Long dialogId, long messageId) {
        db.beginTransaction();
        Cursor cursor = null;
        try {
            String[] projection = new String[]{"id", "chat_id", KEY_MSG_ID, KEY_CLOUD_ID};
            String[] whereArgs = new String[]{String.valueOf(dialogId), String.valueOf(messageId)};
            cursor = db.query(TABLE_FAVS_MESSAGES, projection, "chat_id=? AND msg_id=?", whereArgs, null, null, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                try {
                    db.endTransaction();
                } catch (Exception e) {
                }
                return null;
            }
            db.setTransactionSuccessful();
            Favourite favourite = new Favourite(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), cursor.getLong(3));
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
                return favourite;
            } catch (Exception e2) {
                return favourite;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e4) {
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e5) {
            }
            throw th;
        }
    }

    public void deleteFavouriteMessage(Long chat_id, Long msg_id) {
        db.beginTransaction();
        db.delete(TABLE_FAVS_MESSAGES, "chat_id = ? AND msg_id = ?", new String[]{String.valueOf(chat_id), String.valueOf(msg_id)});
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void deleteFavouriteMessagesByIds(ArrayList<Integer> messages) {
        String InIDs = TextUtils.join(",", messages);
        db.beginTransaction();
        db.delete(TABLE_FAVS_MESSAGES, "cloud_id IN (?)", new String[]{InIDs});
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void addMessageToDownloadQueue(DownloadManager file) {
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("chat_id", Long.valueOf(file.getChatID()));
        values.put(KEY_MSG_ID, Long.valueOf(file.getMsg_id()));
        db.insert(TABLE_DOWNLOAD_QUEUE, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public DownloadManager getMessageFromDownloadQueue(long dialogId, long messageId) {
        db.beginTransaction();
        Cursor cursor = null;
        try {
            String[] projection = new String[]{"id", "chat_id", KEY_MSG_ID};
            String[] whereArgs = new String[]{String.valueOf(dialogId), String.valueOf(messageId)};
            cursor = db.query(TABLE_DOWNLOAD_QUEUE, projection, "chat_id=? AND msg_id=?", whereArgs, null, null, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                try {
                    db.endTransaction();
                } catch (Exception e) {
                }
                return null;
            }
            db.setTransactionSuccessful();
            DownloadManager downloadManager = new DownloadManager(cursor.getLong(1));
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
                return downloadManager;
            } catch (Exception e2) {
                return downloadManager;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e4) {
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e5) {
            }
            throw th;
        }
    }

    public void deleteMessageFromDownloadQueue(long chatId, long messageId) {
        db.beginTransaction();
        db.delete(TABLE_DOWNLOAD_QUEUE, "chat_id = ? AND msg_id = ?", new String[]{String.valueOf(chatId), String.valueOf(messageId)});
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public List<Favourite> getAllFavourites() {
        List<Favourite> favsList = new ArrayList();
        db.beginTransaction();
        Cursor cursor = null;
        try {
            String[] projection = new String[]{"id", "chat_id"};
            String[] whereArgs = new String[0];
            cursor = db.query(TABLE_FAVS, projection, "", whereArgs, null, null, null);
            if (cursor == null || !cursor.moveToFirst()) {
                db.setTransactionSuccessful();
                if (cursor != null) {
                    cursor.close();
                }
                try {
                    db.endTransaction();
                } catch (Exception e) {
                }
                return favsList;
            }
            while (true) {
                Favourite favourite = new Favourite();
                favourite.setID((long) Integer.parseInt(cursor.getString(0)));
                favourite.setChatID(cursor.getLong(1));
                favsList.add(favourite);
                if (!cursor.moveToNext()) {
                    break;
                }
            }
            db.setTransactionSuccessful();
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
            return favsList;
        } catch (Exception e2) {
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e3) {
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e4) {
            }
            throw th;
        }
    }

    public void addContactChangeLog(ContactChangeLog changeLog) {
        if (canAdd(changeLog)) {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("chat_id", Long.valueOf(changeLog.getChatId()));
            values.put(COL_CHANGE_TYPE, Integer.valueOf(changeLog.getType()));
            values.put(COL_DATE, Long.valueOf(changeLog.getDate()));
            values.put(COL_PREVIOUS_NAME, changeLog.getPreviousName());
            db.insert(TABLE_CONTACT_CHANGE_LOG, null, values);
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    private boolean canAdd(ContactChangeLog changeLog) {
        if (changeLog == null) {
            return false;
        }
        String[] projection;
        String[] whereArgs;
        db.beginTransaction();
        Cursor cursor = null;
        try {
            projection = new String[]{"id"};
            String whereClause = "date = ? AND chat_id = ? ";
            whereArgs = new String[]{String.valueOf(changeLog.getDate()), String.valueOf(changeLog.getChatId())};
            if (!db.isDbLockedByOtherThreads()) {
                cursor = db.query(TABLE_CONTACT_CHANGE_LOG, projection, whereClause, whereArgs, null, null, "date DESC ");
            }
            if (cursor != null) {
                db.setTransactionSuccessful();
                if (cursor.moveToNext()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    db.endTransaction();
                    return false;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
            throw th;
        }
        db.beginTransaction();
        try {
            projection = new String[]{COL_PREVIOUS_NAME};
            whereArgs = new String[]{String.valueOf(changeLog.getType()), String.valueOf(changeLog.getChatId())};
            cursor = db.query(TABLE_CONTACT_CHANGE_LOG, projection, "change_type = ? AND chat_id = ? ", whereArgs, null, null, "date DESC ", BuildConfig.VERSION_NAME);
            if (cursor != null) {
                db.setTransactionSuccessful();
                while (cursor.moveToNext()) {
                    String prevName = cursor.getString(0);
                    if (prevName != null && prevName.equals(changeLog.getPreviousName())) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        try {
                            db.endTransaction();
                        } catch (Exception e2) {
                        }
                        return false;
                    }
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e3) {
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e5) {
            }
        } catch (Throwable th2) {
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e6) {
            }
            throw th2;
        }
        return true;
    }

    public void deleteAllChangeLog() {
        db.beginTransaction();
        db.delete(TABLE_CONTACT_CHANGE_LOG, " 1 ", null);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void deleteContactChangeLogWithDate(long date) {
        db.beginTransaction();
        db.delete(TABLE_CONTACT_CHANGE_LOG, "date < ? ", new String[]{String.valueOf(date)});
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public DialogStatus getDialogStatus(long id) {
        db.beginTransaction();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_DIALOG_STATUS, new String[]{"id", DIALOG_STATUS_COL_HAS_HOTGRAM, DIALOG_STATUS_COL_INVITE_SENT, DIALOG_STATUS_COL_FILTER}, "id=? ", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                try {
                    db.endTransaction();
                } catch (Exception e) {
                }
                return null;
            }
            db.setTransactionSuccessful();
            DialogStatus dialogStatus = new DialogStatus();
            dialogStatus.setDialogId(cursor.getLong(0));
            dialogStatus.setHasHotgram(cursor.getInt(1) == 1);
            dialogStatus.setInviteSent(cursor.getInt(2) == 1);
            dialogStatus.setFilter(cursor.getInt(3) == 1);
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
                return dialogStatus;
            } catch (Exception e2) {
                return dialogStatus;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e4) {
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            try {
                db.endTransaction();
            } catch (Exception e5) {
            }
            throw th;
        }
    }

    public void clearFilterStatus() {
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(DIALOG_STATUS_COL_FILTER, Boolean.valueOf(false));
        db.update(TABLE_DIALOG_STATUS, values, null, null);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void createOrUpdateDialogStatus(DialogStatus dialogStatus) {
        DialogStatus dialogStatus1 = getDialogStatus(dialogStatus.getDialogId());
        db.beginTransaction();
        ContentValues values = new ContentValues();
        if (dialogStatus1 != null) {
            values.put("id", Long.valueOf(dialogStatus.getDialogId()));
            values.put(DIALOG_STATUS_COL_HAS_HOTGRAM, Boolean.valueOf(dialogStatus1.isHasHotgram()));
            values.put(DIALOG_STATUS_COL_INVITE_SENT, Boolean.valueOf(dialogStatus1.isInviteSent()));
            values.put(DIALOG_STATUS_COL_FILTER, Boolean.valueOf(dialogStatus.isFilter()));
            String[] whereArgs = new String[]{String.valueOf(dialogStatus1.getDialogId())};
            db.update(TABLE_DIALOG_STATUS, values, "id=? ", whereArgs);
        } else {
            values.put("id", Long.valueOf(dialogStatus.getDialogId()));
            values.put(DIALOG_STATUS_COL_HAS_HOTGRAM, Boolean.valueOf(dialogStatus.isHasHotgram()));
            values.put(DIALOG_STATUS_COL_INVITE_SENT, Boolean.valueOf(dialogStatus.isInviteSent()));
            values.put(DIALOG_STATUS_COL_FILTER, Boolean.valueOf(dialogStatus.isFilter()));
            db.insert(TABLE_DIALOG_STATUS, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void updateDialogStatus(long dialogId, boolean inviteSent) {
        db.beginTransaction();
        ContentValues values = new ContentValues();
        DialogStatus dialogStatus = getDialogStatus(dialogId);
        if (dialogStatus != null) {
            values.put("id", Long.valueOf(dialogStatus.getDialogId()));
            values.put(DIALOG_STATUS_COL_HAS_HOTGRAM, Boolean.valueOf(dialogStatus.isHasHotgram()));
            values.put(DIALOG_STATUS_COL_INVITE_SENT, Boolean.valueOf(inviteSent));
            String[] whereArgs = new String[]{String.valueOf(dialogId)};
            db.update(TABLE_DIALOG_STATUS, values, "id=? ", whereArgs);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void updateDialogStatusFilter(long dialogId, boolean filter) {
        db.beginTransaction();
        ContentValues values = new ContentValues();
        DialogStatus dialogStatus = getDialogStatus(dialogId);
        if (dialogStatus != null) {
            values.put("id", Long.valueOf(dialogStatus.getDialogId()));
            values.put(DIALOG_STATUS_COL_HAS_HOTGRAM, Boolean.valueOf(dialogStatus.isHasHotgram()));
            values.put(DIALOG_STATUS_COL_INVITE_SENT, Boolean.valueOf(dialogStatus.isInviteSent()));
            values.put(DIALOG_STATUS_COL_FILTER, Boolean.valueOf(filter));
            String[] whereArgs = new String[]{String.valueOf(dialogId)};
            db.update(TABLE_DIALOG_STATUS, values, "id=? ", whereArgs);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public int getAllDialogsWhoHasTelegram() {
        db.beginTransaction();
        Cursor cursor = db.rawQuery("SELECT  * FROM TABLE_DIALOG_STATUS", null);
        int i = cursor.getCount();
        cursor.close();
        db.setTransactionSuccessful();
        db.endTransaction();
        return i;
    }

    public static boolean isFilter(long dialogId) {
        DialogStatus dialogStatus = ApplicationLoader.databaseHandler.getDialogStatus(dialogId);
        if (dialogStatus != null) {
            return dialogStatus.isFilter();
        }
        return false;
    }
}
