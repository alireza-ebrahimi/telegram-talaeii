package org.telegram.messenger;

import android.support.v13.view.inputmethod.InputContentInfoCompat;
import java.util.ArrayList;

class SendMessagesHelper$21 implements Runnable {
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ boolean val$forceDocument;
    final /* synthetic */ boolean val$groupPhotos;
    final /* synthetic */ InputContentInfoCompat val$inputContent;
    final /* synthetic */ ArrayList val$media;
    final /* synthetic */ MessageObject val$reply_to_msg;

    SendMessagesHelper$21(ArrayList arrayList, long j, boolean z, boolean z2, MessageObject messageObject, InputContentInfoCompat inputContentInfoCompat) {
        this.val$media = arrayList;
        this.val$dialog_id = j;
        this.val$forceDocument = z;
        this.val$groupPhotos = z2;
        this.val$reply_to_msg = messageObject;
        this.val$inputContent = inputContentInfoCompat;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r75 = this;
        r30 = java.lang.System.currentTimeMillis();
        r0 = r75;
        r4 = r0.val$media;
        r33 = r4.size();
        r0 = r75;
        r4 = r0.val$dialog_id;
        r4 = (int) r4;
        if (r4 != 0) goto L_0x00a3;
    L_0x0013:
        r46 = 1;
    L_0x0015:
        r39 = 0;
        if (r46 == 0) goto L_0x0039;
    L_0x0019:
        r0 = r75;
        r4 = r0.val$dialog_id;
        r10 = 32;
        r4 = r4 >> r10;
        r0 = (int) r4;
        r44 = r0;
        r4 = org.telegram.messenger.MessagesController.getInstance();
        r5 = java.lang.Integer.valueOf(r44);
        r38 = r4.getEncryptedChat(r5);
        if (r38 == 0) goto L_0x0039;
    L_0x0031:
        r0 = r38;
        r4 = r0.layer;
        r39 = org.telegram.messenger.AndroidUtilities.getPeerLayerVersion(r4);
    L_0x0039:
        if (r46 == 0) goto L_0x0041;
    L_0x003b:
        r4 = 73;
        r0 = r39;
        if (r0 < r4) goto L_0x0156;
    L_0x0041:
        r0 = r75;
        r4 = r0.val$forceDocument;
        if (r4 != 0) goto L_0x0156;
    L_0x0047:
        r0 = r75;
        r4 = r0.val$groupPhotos;
        if (r4 == 0) goto L_0x0156;
    L_0x004d:
        r74 = new java.util.HashMap;
        r74.<init>();
        r26 = 0;
    L_0x0054:
        r0 = r26;
        r1 = r33;
        if (r0 >= r1) goto L_0x0158;
    L_0x005a:
        r0 = r75;
        r4 = r0.val$media;
        r0 = r26;
        r8 = r4.get(r0);
        r8 = (org.telegram.messenger.SendMessagesHelper$SendingMediaInfo) r8;
        r4 = r8.searchImage;
        if (r4 != 0) goto L_0x00a0;
    L_0x006a:
        r4 = r8.isVideo;
        if (r4 != 0) goto L_0x00a0;
    L_0x006e:
        r0 = r8.path;
        r54 = r0;
        r0 = r8.path;
        r69 = r0;
        if (r69 != 0) goto L_0x0088;
    L_0x0078:
        r4 = r8.uri;
        if (r4 == 0) goto L_0x0088;
    L_0x007c:
        r4 = r8.uri;
        r69 = org.telegram.messenger.AndroidUtilities.getPath(r4);
        r4 = r8.uri;
        r54 = r4.toString();
    L_0x0088:
        if (r69 == 0) goto L_0x00a7;
    L_0x008a:
        r4 = ".gif";
        r0 = r69;
        r4 = r0.endsWith(r4);
        if (r4 != 0) goto L_0x00a0;
    L_0x0095:
        r4 = ".webp";
        r0 = r69;
        r4 = r0.endsWith(r4);
        if (r4 == 0) goto L_0x00a7;
    L_0x00a0:
        r26 = r26 + 1;
        goto L_0x0054;
    L_0x00a3:
        r46 = 0;
        goto L_0x0015;
    L_0x00a7:
        if (r69 != 0) goto L_0x00bd;
    L_0x00a9:
        r4 = r8.uri;
        if (r4 == 0) goto L_0x00bd;
    L_0x00ad:
        r4 = r8.uri;
        r4 = org.telegram.messenger.MediaController.isGif(r4);
        if (r4 != 0) goto L_0x00a0;
    L_0x00b5:
        r4 = r8.uri;
        r4 = org.telegram.messenger.MediaController.isWebp(r4);
        if (r4 != 0) goto L_0x00a0;
    L_0x00bd:
        if (r69 == 0) goto L_0x0133;
    L_0x00bf:
        r68 = new java.io.File;
        r68.<init>(r69);
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r54;
        r4 = r4.append(r0);
        r10 = r68.length();
        r4 = r4.append(r10);
        r5 = "_";
        r4 = r4.append(r5);
        r10 = r68.lastModified();
        r4 = r4.append(r10);
        r54 = r4.toString();
    L_0x00ea:
        r58 = 0;
        if (r46 != 0) goto L_0x011a;
    L_0x00ee:
        r4 = r8.ttl;
        if (r4 != 0) goto L_0x011a;
    L_0x00f2:
        r5 = org.telegram.messenger.MessagesStorage.getInstance();
        if (r46 != 0) goto L_0x0136;
    L_0x00f8:
        r4 = 0;
    L_0x00f9:
        r0 = r54;
        r58 = r5.getSentFile(r0, r4);
        r58 = (org.telegram.tgnet.TLRPC$TL_photo) r58;
        if (r58 != 0) goto L_0x011a;
    L_0x0103:
        r4 = r8.uri;
        if (r4 == 0) goto L_0x011a;
    L_0x0107:
        r5 = org.telegram.messenger.MessagesStorage.getInstance();
        r4 = r8.uri;
        r10 = org.telegram.messenger.AndroidUtilities.getPath(r4);
        if (r46 != 0) goto L_0x0138;
    L_0x0113:
        r4 = 0;
    L_0x0114:
        r58 = r5.getSentFile(r10, r4);
        r58 = (org.telegram.tgnet.TLRPC$TL_photo) r58;
    L_0x011a:
        r73 = new org.telegram.messenger.SendMessagesHelper$MediaSendPrepareWorker;
        r4 = 0;
        r0 = r73;
        r0.<init>();
        r0 = r74;
        r1 = r73;
        r0.put(r8, r1);
        if (r58 == 0) goto L_0x013a;
    L_0x012b:
        r0 = r58;
        r1 = r73;
        r1.photo = r0;
        goto L_0x00a0;
    L_0x0133:
        r54 = 0;
        goto L_0x00ea;
    L_0x0136:
        r4 = 3;
        goto L_0x00f9;
    L_0x0138:
        r4 = 3;
        goto L_0x0114;
    L_0x013a:
        r4 = new java.util.concurrent.CountDownLatch;
        r5 = 1;
        r4.<init>(r5);
        r0 = r73;
        r0.sync = r4;
        r4 = org.telegram.messenger.SendMessagesHelper.access$1800();
        r5 = new org.telegram.messenger.SendMessagesHelper$21$1;
        r0 = r75;
        r1 = r73;
        r5.<init>(r1, r8);
        r4.execute(r5);
        goto L_0x00a0;
    L_0x0156:
        r74 = 0;
    L_0x0158:
        r42 = 0;
        r48 = 0;
        r61 = 0;
        r63 = 0;
        r62 = 0;
        r40 = 0;
        r60 = 0;
        r26 = 0;
    L_0x0168:
        r0 = r26;
        r1 = r33;
        if (r0 >= r1) goto L_0x0a6c;
    L_0x016e:
        r0 = r75;
        r4 = r0.val$media;
        r0 = r26;
        r8 = r4.get(r0);
        r8 = (org.telegram.messenger.SendMessagesHelper$SendingMediaInfo) r8;
        r0 = r75;
        r4 = r0.val$groupPhotos;
        if (r4 == 0) goto L_0x019b;
    L_0x0180:
        if (r46 == 0) goto L_0x0188;
    L_0x0182:
        r4 = 73;
        r0 = r39;
        if (r0 < r4) goto L_0x019b;
    L_0x0188:
        r4 = 1;
        r0 = r33;
        if (r0 <= r4) goto L_0x019b;
    L_0x018d:
        r4 = r60 % 10;
        if (r4 != 0) goto L_0x019b;
    L_0x0191:
        r4 = org.telegram.messenger.Utilities.random;
        r42 = r4.nextLong();
        r48 = r42;
        r60 = 0;
    L_0x019b:
        r4 = r8.searchImage;
        if (r4 == 0) goto L_0x052c;
    L_0x019f:
        r4 = r8.searchImage;
        r4 = r4.type;
        r5 = 1;
        if (r4 != r5) goto L_0x03ae;
    L_0x01a6:
        r9 = new java.util.HashMap;
        r9.<init>();
        r35 = 0;
        r4 = r8.searchImage;
        r4 = r4.document;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_document;
        if (r4 == 0) goto L_0x0327;
    L_0x01b5:
        r4 = r8.searchImage;
        r0 = r4.document;
        r35 = r0;
        r35 = (org.telegram.tgnet.TLRPC$TL_document) r35;
        r4 = 1;
        r0 = r35;
        r32 = org.telegram.messenger.FileLoader.getPathToAttach(r0, r4);
    L_0x01c4:
        if (r35 != 0) goto L_0x02ed;
    L_0x01c6:
        r4 = r8.searchImage;
        r4 = r4.localUrl;
        if (r4 == 0) goto L_0x01d6;
    L_0x01cc:
        r4 = "url";
        r5 = r8.searchImage;
        r5 = r5.localUrl;
        r9.put(r4, r5);
    L_0x01d6:
        r71 = 0;
        r35 = new org.telegram.tgnet.TLRPC$TL_document;
        r35.<init>();
        r4 = 0;
        r0 = r35;
        r0.id = r4;
        r4 = org.telegram.tgnet.ConnectionsManager.getInstance();
        r4 = r4.getCurrentTime();
        r0 = r35;
        r0.date = r4;
        r41 = new org.telegram.tgnet.TLRPC$TL_documentAttributeFilename;
        r41.<init>();
        r4 = "animation.gif";
        r0 = r41;
        r0.file_name = r4;
        r0 = r35;
        r4 = r0.attributes;
        r0 = r41;
        r4.add(r0);
        r4 = r8.searchImage;
        r4 = r4.size;
        r0 = r35;
        r0.size = r4;
        r4 = 0;
        r0 = r35;
        r0.dc_id = r4;
        r4 = r32.toString();
        r5 = "mp4";
        r4 = r4.endsWith(r5);
        if (r4 == 0) goto L_0x0385;
    L_0x021e:
        r4 = "video/mp4";
        r0 = r35;
        r0.mime_type = r4;
        r0 = r35;
        r4 = r0.attributes;
        r5 = new org.telegram.tgnet.TLRPC$TL_documentAttributeAnimated;
        r5.<init>();
        r4.add(r5);
    L_0x0231:
        r4 = r32.exists();
        if (r4 == 0) goto L_0x038e;
    L_0x0237:
        r71 = r32;
    L_0x0239:
        if (r71 != 0) goto L_0x0280;
    L_0x023b:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = r8.searchImage;
        r5 = r5.thumbUrl;
        r5 = org.telegram.messenger.Utilities.MD5(r5);
        r4 = r4.append(r5);
        r5 = ".";
        r4 = r4.append(r5);
        r5 = r8.searchImage;
        r5 = r5.thumbUrl;
        r10 = "jpg";
        r5 = org.telegram.messenger.ImageLoader.getHttpUrlExtension(r5, r10);
        r4 = r4.append(r5);
        r70 = r4.toString();
        r71 = new java.io.File;
        r4 = org.telegram.messenger.FileLoader.getInstance();
        r5 = 4;
        r4 = r4.getDirectory(r5);
        r0 = r71;
        r1 = r70;
        r0.<init>(r4, r1);
        r4 = r71.exists();
        if (r4 != 0) goto L_0x0280;
    L_0x027e:
        r71 = 0;
    L_0x0280:
        if (r71 == 0) goto L_0x02af;
    L_0x0282:
        r4 = r71.getAbsolutePath();	 Catch:{ Exception -> 0x03a2 }
        r5 = "mp4";
        r4 = r4.endsWith(r5);	 Catch:{ Exception -> 0x03a2 }
        if (r4 == 0) goto L_0x0392;
    L_0x028f:
        r4 = r71.getAbsolutePath();	 Catch:{ Exception -> 0x03a2 }
        r5 = 1;
        r29 = android.media.ThumbnailUtils.createVideoThumbnail(r4, r5);	 Catch:{ Exception -> 0x03a2 }
    L_0x0298:
        if (r29 == 0) goto L_0x02af;
    L_0x029a:
        r4 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r5 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r10 = 55;
        r0 = r29;
        r1 = r46;
        r4 = org.telegram.messenger.ImageLoader.scaleAndSaveImage(r0, r4, r5, r10, r1);	 Catch:{ Exception -> 0x03a2 }
        r0 = r35;
        r0.thumb = r4;	 Catch:{ Exception -> 0x03a2 }
        r29.recycle();	 Catch:{ Exception -> 0x03a2 }
    L_0x02af:
        r0 = r35;
        r4 = r0.thumb;
        if (r4 != 0) goto L_0x02ed;
    L_0x02b5:
        r4 = new org.telegram.tgnet.TLRPC$TL_photoSize;
        r4.<init>();
        r0 = r35;
        r0.thumb = r4;
        r0 = r35;
        r4 = r0.thumb;
        r5 = r8.searchImage;
        r5 = r5.width;
        r4.f78w = r5;
        r0 = r35;
        r4 = r0.thumb;
        r5 = r8.searchImage;
        r5 = r5.height;
        r4.f77h = r5;
        r0 = r35;
        r4 = r0.thumb;
        r5 = 0;
        r4.size = r5;
        r0 = r35;
        r4 = r0.thumb;
        r5 = new org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
        r5.<init>();
        r4.location = r5;
        r0 = r35;
        r4 = r0.thumb;
        r5 = "x";
        r4.type = r5;
    L_0x02ed:
        r4 = r8.caption;
        r0 = r35;
        r0.caption = r4;
        r36 = r35;
        r4 = r8.searchImage;
        r0 = r4.imageUrl;
        r55 = r0;
        if (r32 != 0) goto L_0x03a8;
    L_0x02fd:
        r4 = r8.searchImage;
        r0 = r4.imageUrl;
        r57 = r0;
    L_0x0303:
        if (r9 == 0) goto L_0x0315;
    L_0x0305:
        r4 = r8.searchImage;
        r4 = r4.imageUrl;
        if (r4 == 0) goto L_0x0315;
    L_0x030b:
        r4 = "originalPath";
        r5 = r8.searchImage;
        r5 = r5.imageUrl;
        r9.put(r4, r5);
    L_0x0315:
        r4 = new org.telegram.messenger.SendMessagesHelper$21$2;
        r0 = r75;
        r1 = r36;
        r2 = r57;
        r4.<init>(r1, r2, r9);
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r4);
    L_0x0323:
        r26 = r26 + 1;
        goto L_0x0168;
    L_0x0327:
        if (r46 != 0) goto L_0x0344;
    L_0x0329:
        r5 = org.telegram.messenger.MessagesStorage.getInstance();
        r4 = r8.searchImage;
        r10 = r4.imageUrl;
        if (r46 != 0) goto L_0x0383;
    L_0x0333:
        r4 = 1;
    L_0x0334:
        r34 = r5.getSentFile(r10, r4);
        r34 = (org.telegram.tgnet.TLRPC$Document) r34;
        r0 = r34;
        r4 = r0 instanceof org.telegram.tgnet.TLRPC$TL_document;
        if (r4 == 0) goto L_0x0344;
    L_0x0340:
        r35 = r34;
        r35 = (org.telegram.tgnet.TLRPC$TL_document) r35;
    L_0x0344:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = r8.searchImage;
        r5 = r5.imageUrl;
        r5 = org.telegram.messenger.Utilities.MD5(r5);
        r4 = r4.append(r5);
        r5 = ".";
        r4 = r4.append(r5);
        r5 = r8.searchImage;
        r5 = r5.imageUrl;
        r10 = "jpg";
        r5 = org.telegram.messenger.ImageLoader.getHttpUrlExtension(r5, r10);
        r4 = r4.append(r5);
        r47 = r4.toString();
        r32 = new java.io.File;
        r4 = org.telegram.messenger.FileLoader.getInstance();
        r5 = 4;
        r4 = r4.getDirectory(r5);
        r0 = r32;
        r1 = r47;
        r0.<init>(r4, r1);
        goto L_0x01c4;
    L_0x0383:
        r4 = 4;
        goto L_0x0334;
    L_0x0385:
        r4 = "image/gif";
        r0 = r35;
        r0.mime_type = r4;
        goto L_0x0231;
    L_0x038e:
        r32 = 0;
        goto L_0x0239;
    L_0x0392:
        r4 = r71.getAbsolutePath();	 Catch:{ Exception -> 0x03a2 }
        r5 = 0;
        r10 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r11 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r15 = 1;
        r29 = org.telegram.messenger.ImageLoader.loadBitmap(r4, r5, r10, r11, r15);	 Catch:{ Exception -> 0x03a2 }
        goto L_0x0298;
    L_0x03a2:
        r37 = move-exception;
        org.telegram.messenger.FileLog.e(r37);
        goto L_0x02af;
    L_0x03a8:
        r57 = r32.toString();
        goto L_0x0303;
    L_0x03ae:
        r53 = 1;
        r58 = 0;
        if (r46 != 0) goto L_0x03c9;
    L_0x03b4:
        r4 = r8.ttl;
        if (r4 != 0) goto L_0x03c9;
    L_0x03b8:
        r5 = org.telegram.messenger.MessagesStorage.getInstance();
        r4 = r8.searchImage;
        r10 = r4.imageUrl;
        if (r46 != 0) goto L_0x0529;
    L_0x03c2:
        r4 = 0;
    L_0x03c3:
        r58 = r5.getSentFile(r10, r4);
        r58 = (org.telegram.tgnet.TLRPC$TL_photo) r58;
    L_0x03c9:
        if (r58 != 0) goto L_0x04c1;
    L_0x03cb:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = r8.searchImage;
        r5 = r5.imageUrl;
        r5 = org.telegram.messenger.Utilities.MD5(r5);
        r4 = r4.append(r5);
        r5 = ".";
        r4 = r4.append(r5);
        r5 = r8.searchImage;
        r5 = r5.imageUrl;
        r10 = "jpg";
        r5 = org.telegram.messenger.ImageLoader.getHttpUrlExtension(r5, r10);
        r4 = r4.append(r5);
        r47 = r4.toString();
        r32 = new java.io.File;
        r4 = org.telegram.messenger.FileLoader.getInstance();
        r5 = 4;
        r4 = r4.getDirectory(r5);
        r0 = r32;
        r1 = r47;
        r0.<init>(r4, r1);
        r4 = r32.exists();
        if (r4 == 0) goto L_0x0429;
    L_0x040e:
        r4 = r32.length();
        r10 = 0;
        r4 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1));
        if (r4 == 0) goto L_0x0429;
    L_0x0418:
        r4 = org.telegram.messenger.SendMessagesHelper.getInstance();
        r5 = r32.toString();
        r10 = 0;
        r58 = r4.generatePhotoSizes(r5, r10);
        if (r58 == 0) goto L_0x0429;
    L_0x0427:
        r53 = 0;
    L_0x0429:
        if (r58 != 0) goto L_0x04c1;
    L_0x042b:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = r8.searchImage;
        r5 = r5.thumbUrl;
        r5 = org.telegram.messenger.Utilities.MD5(r5);
        r4 = r4.append(r5);
        r5 = ".";
        r4 = r4.append(r5);
        r5 = r8.searchImage;
        r5 = r5.thumbUrl;
        r10 = "jpg";
        r5 = org.telegram.messenger.ImageLoader.getHttpUrlExtension(r5, r10);
        r4 = r4.append(r5);
        r47 = r4.toString();
        r32 = new java.io.File;
        r4 = org.telegram.messenger.FileLoader.getInstance();
        r5 = 4;
        r4 = r4.getDirectory(r5);
        r0 = r32;
        r1 = r47;
        r0.<init>(r4, r1);
        r4 = r32.exists();
        if (r4 == 0) goto L_0x047b;
    L_0x046e:
        r4 = org.telegram.messenger.SendMessagesHelper.getInstance();
        r5 = r32.toString();
        r10 = 0;
        r58 = r4.generatePhotoSizes(r5, r10);
    L_0x047b:
        if (r58 != 0) goto L_0x04c1;
    L_0x047d:
        r58 = new org.telegram.tgnet.TLRPC$TL_photo;
        r58.<init>();
        r4 = org.telegram.tgnet.ConnectionsManager.getInstance();
        r4 = r4.getCurrentTime();
        r0 = r58;
        r0.date = r4;
        r59 = new org.telegram.tgnet.TLRPC$TL_photoSize;
        r59.<init>();
        r4 = r8.searchImage;
        r4 = r4.width;
        r0 = r59;
        r0.w = r4;
        r4 = r8.searchImage;
        r4 = r4.height;
        r0 = r59;
        r0.h = r4;
        r4 = 0;
        r0 = r59;
        r0.size = r4;
        r4 = new org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
        r4.<init>();
        r0 = r59;
        r0.location = r4;
        r4 = "x";
        r0 = r59;
        r0.type = r4;
        r0 = r58;
        r4 = r0.sizes;
        r0 = r59;
        r4.add(r0);
    L_0x04c1:
        if (r58 == 0) goto L_0x0323;
    L_0x04c3:
        r4 = r8.caption;
        r0 = r58;
        r0.caption = r4;
        r6 = r58;
        r7 = r53;
        r9 = new java.util.HashMap;
        r9.<init>();
        r4 = r8.searchImage;
        r4 = r4.imageUrl;
        if (r4 == 0) goto L_0x04e2;
    L_0x04d8:
        r4 = "originalPath";
        r5 = r8.searchImage;
        r5 = r5.imageUrl;
        r9.put(r4, r5);
    L_0x04e2:
        r0 = r75;
        r4 = r0.val$groupPhotos;
        if (r4 == 0) goto L_0x051d;
    L_0x04e8:
        r60 = r60 + 1;
        r4 = "groupId";
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r10 = "";
        r5 = r5.append(r10);
        r0 = r42;
        r5 = r5.append(r0);
        r5 = r5.toString();
        r9.put(r4, r5);
        r4 = 10;
        r0 = r60;
        if (r0 == r4) goto L_0x0512;
    L_0x050c:
        r4 = r33 + -1;
        r0 = r26;
        if (r0 != r4) goto L_0x051d;
    L_0x0512:
        r4 = "final";
        r5 = "1";
        r9.put(r4, r5);
        r48 = 0;
    L_0x051d:
        r4 = new org.telegram.messenger.SendMessagesHelper$21$3;
        r5 = r75;
        r4.<init>(r6, r7, r8, r9);
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r4);
        goto L_0x0323;
    L_0x0529:
        r4 = 3;
        goto L_0x03c3;
    L_0x052c:
        r4 = r8.isVideo;
        if (r4 == 0) goto L_0x0822;
    L_0x0530:
        r70 = 0;
        r72 = 0;
        r4 = r8.videoEditedInfo;
        if (r4 != 0) goto L_0x0543;
    L_0x0538:
        r4 = r8.path;
        r5 = "mp4";
        r4 = r4.endsWith(r5);
        if (r4 == 0) goto L_0x0801;
    L_0x0543:
        r0 = r8.path;
        r56 = r0;
        r0 = r8.path;
        r54 = r0;
        r68 = new java.io.File;
        r0 = r68;
        r1 = r54;
        r0.<init>(r1);
        r66 = 0;
        r52 = 0;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r54;
        r4 = r4.append(r0);
        r10 = r68.length();
        r4 = r4.append(r10);
        r5 = "_";
        r4 = r4.append(r5);
        r10 = r68.lastModified();
        r4 = r4.append(r10);
        r54 = r4.toString();
        r4 = r8.videoEditedInfo;
        if (r4 == 0) goto L_0x0602;
    L_0x0582:
        r4 = r8.videoEditedInfo;
        r0 = r4.muted;
        r52 = r0;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r54;
        r4 = r4.append(r0);
        r5 = r8.videoEditedInfo;
        r10 = r5.estimatedDuration;
        r4 = r4.append(r10);
        r5 = "_";
        r4 = r4.append(r5);
        r5 = r8.videoEditedInfo;
        r10 = r5.startTime;
        r4 = r4.append(r10);
        r5 = "_";
        r4 = r4.append(r5);
        r5 = r8.videoEditedInfo;
        r10 = r5.endTime;
        r5 = r4.append(r10);
        r4 = r8.videoEditedInfo;
        r4 = r4.muted;
        if (r4 == 0) goto L_0x077e;
    L_0x05bf:
        r4 = "_m";
    L_0x05c2:
        r4 = r5.append(r4);
        r54 = r4.toString();
        r4 = r8.videoEditedInfo;
        r4 = r4.resultWidth;
        r5 = r8.videoEditedInfo;
        r5 = r5.originalWidth;
        if (r4 != r5) goto L_0x05f2;
    L_0x05d4:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r54;
        r4 = r4.append(r0);
        r5 = "_";
        r4 = r4.append(r5);
        r5 = r8.videoEditedInfo;
        r5 = r5.resultWidth;
        r4 = r4.append(r5);
        r54 = r4.toString();
    L_0x05f2:
        r4 = r8.videoEditedInfo;
        r4 = r4.startTime;
        r10 = 0;
        r4 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1));
        if (r4 < 0) goto L_0x0783;
    L_0x05fc:
        r4 = r8.videoEditedInfo;
        r0 = r4.startTime;
        r66 = r0;
    L_0x0602:
        r35 = 0;
        if (r46 != 0) goto L_0x0619;
    L_0x0606:
        r4 = r8.ttl;
        if (r4 != 0) goto L_0x0619;
    L_0x060a:
        r5 = org.telegram.messenger.MessagesStorage.getInstance();
        if (r46 != 0) goto L_0x0787;
    L_0x0610:
        r4 = 2;
    L_0x0611:
        r0 = r54;
        r35 = r5.getSentFile(r0, r4);
        r35 = (org.telegram.tgnet.TLRPC$TL_document) r35;
    L_0x0619:
        if (r35 != 0) goto L_0x0711;
    L_0x061b:
        r4 = r8.path;
        r0 = r66;
        r70 = org.telegram.messenger.SendMessagesHelper.access$1900(r4, r0);
        if (r70 != 0) goto L_0x062c;
    L_0x0625:
        r4 = r8.path;
        r5 = 1;
        r70 = android.media.ThumbnailUtils.createVideoThumbnail(r4, r5);
    L_0x062c:
        r4 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r5 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r10 = 55;
        r0 = r70;
        r1 = r46;
        r65 = org.telegram.messenger.ImageLoader.scaleAndSaveImage(r0, r4, r5, r10, r1);
        if (r70 == 0) goto L_0x0640;
    L_0x063c:
        if (r65 == 0) goto L_0x0640;
    L_0x063e:
        r70 = 0;
    L_0x0640:
        r35 = new org.telegram.tgnet.TLRPC$TL_document;
        r35.<init>();
        r0 = r65;
        r1 = r35;
        r1.thumb = r0;
        r0 = r35;
        r4 = r0.thumb;
        if (r4 != 0) goto L_0x078a;
    L_0x0651:
        r4 = new org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
        r4.<init>();
        r0 = r35;
        r0.thumb = r4;
        r0 = r35;
        r4 = r0.thumb;
        r5 = "s";
        r4.type = r5;
    L_0x0663:
        r4 = "video/mp4";
        r0 = r35;
        r0.mime_type = r4;
        r4 = 0;
        org.telegram.messenger.UserConfig.saveConfig(r4);
        if (r46 == 0) goto L_0x079c;
    L_0x0670:
        r4 = 66;
        r0 = r39;
        if (r0 < r4) goto L_0x0795;
    L_0x0676:
        r27 = new org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
        r27.<init>();
    L_0x067b:
        r0 = r35;
        r4 = r0.attributes;
        r0 = r27;
        r4.add(r0);
        r4 = r8.videoEditedInfo;
        if (r4 == 0) goto L_0x07e3;
    L_0x0688:
        r4 = r8.videoEditedInfo;
        r4 = r4.needConvert();
        if (r4 == 0) goto L_0x07e3;
    L_0x0690:
        r4 = r8.videoEditedInfo;
        r4 = r4.muted;
        if (r4 == 0) goto L_0x07a3;
    L_0x0696:
        r0 = r35;
        r4 = r0.attributes;
        r5 = new org.telegram.tgnet.TLRPC$TL_documentAttributeAnimated;
        r5.<init>();
        r4.add(r5);
        r4 = r8.path;
        r5 = r8.videoEditedInfo;
        r0 = r27;
        org.telegram.messenger.SendMessagesHelper.access$2000(r4, r0, r5);
        r4 = r8.videoEditedInfo;
        r0 = r27;
        r5 = r0.w;
        r4.originalWidth = r5;
        r4 = r8.videoEditedInfo;
        r0 = r27;
        r5 = r0.h;
        r4.originalHeight = r5;
        r4 = r8.videoEditedInfo;
        r4 = r4.resultWidth;
        r0 = r27;
        r0.w = r4;
        r4 = r8.videoEditedInfo;
        r4 = r4.resultHeight;
        r0 = r27;
        r0.h = r4;
    L_0x06cb:
        r4 = r8.videoEditedInfo;
        r4 = r4.estimatedSize;
        r4 = (int) r4;
        r0 = r35;
        r0.size = r4;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "-2147483648_";
        r4 = r4.append(r5);
        r5 = org.telegram.messenger.UserConfig.lastLocalId;
        r4 = r4.append(r5);
        r5 = ".mp4";
        r4 = r4.append(r5);
        r41 = r4.toString();
        r4 = org.telegram.messenger.UserConfig.lastLocalId;
        r4 = r4 + -1;
        org.telegram.messenger.UserConfig.lastLocalId = r4;
        r32 = new java.io.File;
        r4 = org.telegram.messenger.FileLoader.getInstance();
        r5 = 4;
        r4 = r4.getDirectory(r5);
        r0 = r32;
        r1 = r41;
        r0.<init>(r4, r1);
        r4 = 0;
        org.telegram.messenger.UserConfig.saveConfig(r4);
        r56 = r32.getAbsolutePath();
    L_0x0711:
        r14 = r35;
        r55 = r54;
        r16 = r56;
        r9 = new java.util.HashMap;
        r9.<init>();
        r12 = r70;
        r13 = r72;
        r4 = r8.caption;
        if (r4 == 0) goto L_0x07fc;
    L_0x0724:
        r4 = r8.caption;
    L_0x0726:
        r14.caption = r4;
        if (r54 == 0) goto L_0x0732;
    L_0x072a:
        r4 = "originalPath";
        r0 = r54;
        r9.put(r4, r0);
    L_0x0732:
        if (r52 != 0) goto L_0x076f;
    L_0x0734:
        r0 = r75;
        r4 = r0.val$groupPhotos;
        if (r4 == 0) goto L_0x076f;
    L_0x073a:
        r60 = r60 + 1;
        r4 = "groupId";
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r10 = "";
        r5 = r5.append(r10);
        r0 = r42;
        r5 = r5.append(r0);
        r5 = r5.toString();
        r9.put(r4, r5);
        r4 = 10;
        r0 = r60;
        if (r0 == r4) goto L_0x0764;
    L_0x075e:
        r4 = r33 + -1;
        r0 = r26;
        if (r0 != r4) goto L_0x076f;
    L_0x0764:
        r4 = "final";
        r5 = "1";
        r9.put(r4, r5);
        r48 = 0;
    L_0x076f:
        r10 = new org.telegram.messenger.SendMessagesHelper$21$4;
        r11 = r75;
        r15 = r8;
        r17 = r9;
        r10.<init>(r12, r13, r14, r15, r16, r17);
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r10);
        goto L_0x0323;
    L_0x077e:
        r4 = "";
        goto L_0x05c2;
    L_0x0783:
        r66 = 0;
        goto L_0x0602;
    L_0x0787:
        r4 = 5;
        goto L_0x0611;
    L_0x078a:
        r0 = r35;
        r4 = r0.thumb;
        r5 = "s";
        r4.type = r5;
        goto L_0x0663;
    L_0x0795:
        r27 = new org.telegram.tgnet.TLRPC$TL_documentAttributeVideo_layer65;
        r27.<init>();
        goto L_0x067b;
    L_0x079c:
        r27 = new org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
        r27.<init>();
        goto L_0x067b;
    L_0x07a3:
        r4 = r8.videoEditedInfo;
        r4 = r4.estimatedDuration;
        r10 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r10;
        r4 = (int) r4;
        r0 = r27;
        r0.duration = r4;
        r4 = r8.videoEditedInfo;
        r4 = r4.rotationValue;
        r5 = 90;
        if (r4 == r5) goto L_0x07bf;
    L_0x07b7:
        r4 = r8.videoEditedInfo;
        r4 = r4.rotationValue;
        r5 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        if (r4 != r5) goto L_0x07d1;
    L_0x07bf:
        r4 = r8.videoEditedInfo;
        r4 = r4.resultHeight;
        r0 = r27;
        r0.w = r4;
        r4 = r8.videoEditedInfo;
        r4 = r4.resultWidth;
        r0 = r27;
        r0.h = r4;
        goto L_0x06cb;
    L_0x07d1:
        r4 = r8.videoEditedInfo;
        r4 = r4.resultWidth;
        r0 = r27;
        r0.w = r4;
        r4 = r8.videoEditedInfo;
        r4 = r4.resultHeight;
        r0 = r27;
        r0.h = r4;
        goto L_0x06cb;
    L_0x07e3:
        r4 = r68.exists();
        if (r4 == 0) goto L_0x07f2;
    L_0x07e9:
        r4 = r68.length();
        r4 = (int) r4;
        r0 = r35;
        r0.size = r4;
    L_0x07f2:
        r4 = r8.path;
        r5 = 0;
        r0 = r27;
        org.telegram.messenger.SendMessagesHelper.access$2000(r4, r0, r5);
        goto L_0x0711;
    L_0x07fc:
        r4 = "";
        goto L_0x0726;
    L_0x0801:
        r0 = r8.path;
        r18 = r0;
        r0 = r8.path;
        r19 = r0;
        r20 = 0;
        r21 = 0;
        r0 = r75;
        r0 = r0.val$dialog_id;
        r22 = r0;
        r0 = r75;
        r0 = r0.val$reply_to_msg;
        r24 = r0;
        r0 = r8.caption;
        r25 = r0;
        org.telegram.messenger.SendMessagesHelper.access$1500(r18, r19, r20, r21, r22, r24, r25);
        goto L_0x0323;
    L_0x0822:
        r0 = r8.path;
        r54 = r0;
        r0 = r8.path;
        r69 = r0;
        if (r69 != 0) goto L_0x083c;
    L_0x082c:
        r4 = r8.uri;
        if (r4 == 0) goto L_0x083c;
    L_0x0830:
        r4 = r8.uri;
        r69 = org.telegram.messenger.AndroidUtilities.getPath(r4);
        r4 = r8.uri;
        r54 = r4.toString();
    L_0x083c:
        r45 = 0;
        r0 = r75;
        r4 = r0.val$forceDocument;
        if (r4 == 0) goto L_0x087b;
    L_0x0844:
        r45 = 1;
        r4 = new java.io.File;
        r0 = r69;
        r4.<init>(r0);
        r40 = org.telegram.messenger.FileLoader.getFileExtension(r4);
    L_0x0851:
        if (r45 == 0) goto L_0x08e9;
    L_0x0853:
        if (r61 != 0) goto L_0x0864;
    L_0x0855:
        r61 = new java.util.ArrayList;
        r61.<init>();
        r63 = new java.util.ArrayList;
        r63.<init>();
        r62 = new java.util.ArrayList;
        r62.<init>();
    L_0x0864:
        r0 = r61;
        r1 = r69;
        r0.add(r1);
        r0 = r63;
        r1 = r54;
        r0.add(r1);
        r4 = r8.caption;
        r0 = r62;
        r0.add(r4);
        goto L_0x0323;
    L_0x087b:
        if (r69 == 0) goto L_0x08a8;
    L_0x087d:
        r4 = ".gif";
        r0 = r69;
        r4 = r0.endsWith(r4);
        if (r4 != 0) goto L_0x0893;
    L_0x0888:
        r4 = ".webp";
        r0 = r69;
        r4 = r0.endsWith(r4);
        if (r4 == 0) goto L_0x08a8;
    L_0x0893:
        r4 = ".gif";
        r0 = r69;
        r4 = r0.endsWith(r4);
        if (r4 == 0) goto L_0x08a4;
    L_0x089e:
        r40 = "gif";
    L_0x08a1:
        r45 = 1;
        goto L_0x0851;
    L_0x08a4:
        r40 = "webp";
        goto L_0x08a1;
    L_0x08a8:
        if (r69 != 0) goto L_0x0851;
    L_0x08aa:
        r4 = r8.uri;
        if (r4 == 0) goto L_0x0851;
    L_0x08ae:
        r4 = r8.uri;
        r4 = org.telegram.messenger.MediaController.isGif(r4);
        if (r4 == 0) goto L_0x08cb;
    L_0x08b6:
        r45 = 1;
        r4 = r8.uri;
        r54 = r4.toString();
        r4 = r8.uri;
        r5 = "gif";
        r69 = org.telegram.messenger.MediaController.copyFileToCache(r4, r5);
        r40 = "gif";
        goto L_0x0851;
    L_0x08cb:
        r4 = r8.uri;
        r4 = org.telegram.messenger.MediaController.isWebp(r4);
        if (r4 == 0) goto L_0x0851;
    L_0x08d3:
        r45 = 1;
        r4 = r8.uri;
        r54 = r4.toString();
        r4 = r8.uri;
        r5 = "webp";
        r69 = org.telegram.messenger.MediaController.copyFileToCache(r4, r5);
        r40 = "webp";
        goto L_0x0851;
    L_0x08e9:
        if (r69 == 0) goto L_0x0993;
    L_0x08eb:
        r68 = new java.io.File;
        r68.<init>(r69);
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r54;
        r4 = r4.append(r0);
        r10 = r68.length();
        r4 = r4.append(r10);
        r5 = "_";
        r4 = r4.append(r5);
        r10 = r68.lastModified();
        r4 = r4.append(r10);
        r54 = r4.toString();
    L_0x0916:
        r58 = 0;
        if (r74 == 0) goto L_0x09a0;
    L_0x091a:
        r0 = r74;
        r73 = r0.get(r8);
        r73 = (org.telegram.messenger.SendMessagesHelper$MediaSendPrepareWorker) r73;
        r0 = r73;
        r0 = r0.photo;
        r58 = r0;
        if (r58 != 0) goto L_0x0937;
    L_0x092a:
        r0 = r73;
        r4 = r0.sync;	 Catch:{ Exception -> 0x0996 }
        r4.await();	 Catch:{ Exception -> 0x0996 }
    L_0x0931:
        r0 = r73;
        r0 = r0.photo;
        r58 = r0;
    L_0x0937:
        if (r58 == 0) goto L_0x0a44;
    L_0x0939:
        r6 = r58;
        r9 = new java.util.HashMap;
        r9.<init>();
        r4 = r8.caption;
        r0 = r58;
        r0.caption = r4;
        r4 = r8.masks;
        if (r4 == 0) goto L_0x09e2;
    L_0x094a:
        r4 = r8.masks;
        r4 = r4.isEmpty();
        if (r4 != 0) goto L_0x09e2;
    L_0x0952:
        r4 = 1;
    L_0x0953:
        r0 = r58;
        r0.has_stickers = r4;
        if (r4 == 0) goto L_0x09f3;
    L_0x0959:
        r64 = new org.telegram.tgnet.SerializedData;
        r4 = r8.masks;
        r4 = r4.size();
        r4 = r4 * 20;
        r4 = r4 + 4;
        r0 = r64;
        r0.<init>(r4);
        r4 = r8.masks;
        r4 = r4.size();
        r0 = r64;
        r0.writeInt32(r4);
        r28 = 0;
    L_0x0977:
        r4 = r8.masks;
        r4 = r4.size();
        r0 = r28;
        if (r0 >= r4) goto L_0x09e5;
    L_0x0981:
        r4 = r8.masks;
        r0 = r28;
        r4 = r4.get(r0);
        r4 = (org.telegram.tgnet.TLRPC$InputDocument) r4;
        r0 = r64;
        r4.serializeToStream(r0);
        r28 = r28 + 1;
        goto L_0x0977;
    L_0x0993:
        r54 = 0;
        goto L_0x0916;
    L_0x0996:
        r37 = move-exception;
        r4 = "tmessages";
        r0 = r37;
        org.telegram.messenger.FileLog.e(r4, r0);
        goto L_0x0931;
    L_0x09a0:
        if (r46 != 0) goto L_0x09ce;
    L_0x09a2:
        r4 = r8.ttl;
        if (r4 != 0) goto L_0x09ce;
    L_0x09a6:
        r5 = org.telegram.messenger.MessagesStorage.getInstance();
        if (r46 != 0) goto L_0x09de;
    L_0x09ac:
        r4 = 0;
    L_0x09ad:
        r0 = r54;
        r58 = r5.getSentFile(r0, r4);
        r58 = (org.telegram.tgnet.TLRPC$TL_photo) r58;
        if (r58 != 0) goto L_0x09ce;
    L_0x09b7:
        r4 = r8.uri;
        if (r4 == 0) goto L_0x09ce;
    L_0x09bb:
        r5 = org.telegram.messenger.MessagesStorage.getInstance();
        r4 = r8.uri;
        r10 = org.telegram.messenger.AndroidUtilities.getPath(r4);
        if (r46 != 0) goto L_0x09e0;
    L_0x09c7:
        r4 = 0;
    L_0x09c8:
        r58 = r5.getSentFile(r10, r4);
        r58 = (org.telegram.tgnet.TLRPC$TL_photo) r58;
    L_0x09ce:
        if (r58 != 0) goto L_0x0937;
    L_0x09d0:
        r4 = org.telegram.messenger.SendMessagesHelper.getInstance();
        r5 = r8.path;
        r10 = r8.uri;
        r58 = r4.generatePhotoSizes(r5, r10);
        goto L_0x0937;
    L_0x09de:
        r4 = 3;
        goto L_0x09ad;
    L_0x09e0:
        r4 = 3;
        goto L_0x09c8;
    L_0x09e2:
        r4 = 0;
        goto L_0x0953;
    L_0x09e5:
        r4 = "masks";
        r5 = r64.toByteArray();
        r5 = org.telegram.messenger.Utilities.bytesToHex(r5);
        r9.put(r4, r5);
    L_0x09f3:
        if (r54 == 0) goto L_0x09fd;
    L_0x09f5:
        r4 = "originalPath";
        r0 = r54;
        r9.put(r4, r0);
    L_0x09fd:
        r0 = r75;
        r4 = r0.val$groupPhotos;
        if (r4 == 0) goto L_0x0a38;
    L_0x0a03:
        r60 = r60 + 1;
        r4 = "groupId";
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r10 = "";
        r5 = r5.append(r10);
        r0 = r42;
        r5 = r5.append(r0);
        r5 = r5.toString();
        r9.put(r4, r5);
        r4 = 10;
        r0 = r60;
        if (r0 == r4) goto L_0x0a2d;
    L_0x0a27:
        r4 = r33 + -1;
        r0 = r26;
        if (r0 != r4) goto L_0x0a38;
    L_0x0a2d:
        r4 = "final";
        r5 = "1";
        r9.put(r4, r5);
        r48 = 0;
    L_0x0a38:
        r4 = new org.telegram.messenger.SendMessagesHelper$21$5;
        r0 = r75;
        r4.<init>(r6, r9, r8);
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r4);
        goto L_0x0323;
    L_0x0a44:
        if (r61 != 0) goto L_0x0a55;
    L_0x0a46:
        r61 = new java.util.ArrayList;
        r61.<init>();
        r63 = new java.util.ArrayList;
        r63.<init>();
        r62 = new java.util.ArrayList;
        r62.<init>();
    L_0x0a55:
        r0 = r61;
        r1 = r69;
        r0.add(r1);
        r0 = r63;
        r1 = r54;
        r0.add(r1);
        r4 = r8.caption;
        r0 = r62;
        r0.add(r4);
        goto L_0x0323;
    L_0x0a6c:
        r4 = 0;
        r4 = (r48 > r4 ? 1 : (r48 == r4 ? 0 : -1));
        if (r4 == 0) goto L_0x0a80;
    L_0x0a72:
        r50 = r48;
        r4 = new org.telegram.messenger.SendMessagesHelper$21$6;
        r0 = r75;
        r1 = r50;
        r4.<init>(r1);
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r4);
    L_0x0a80:
        r0 = r75;
        r4 = r0.val$inputContent;
        if (r4 == 0) goto L_0x0a8d;
    L_0x0a86:
        r0 = r75;
        r4 = r0.val$inputContent;
        r4.releasePermission();
    L_0x0a8d:
        if (r61 == 0) goto L_0x0ad3;
    L_0x0a8f:
        r4 = r61.isEmpty();
        if (r4 != 0) goto L_0x0ad3;
    L_0x0a95:
        r26 = 0;
    L_0x0a97:
        r4 = r61.size();
        r0 = r26;
        if (r0 >= r4) goto L_0x0ad3;
    L_0x0a9f:
        r0 = r61;
        r1 = r26;
        r18 = r0.get(r1);
        r18 = (java.lang.String) r18;
        r0 = r63;
        r1 = r26;
        r19 = r0.get(r1);
        r19 = (java.lang.String) r19;
        r20 = 0;
        r0 = r75;
        r0 = r0.val$dialog_id;
        r22 = r0;
        r0 = r75;
        r0 = r0.val$reply_to_msg;
        r24 = r0;
        r0 = r62;
        r1 = r26;
        r25 = r0.get(r1);
        r25 = (java.lang.CharSequence) r25;
        r21 = r40;
        org.telegram.messenger.SendMessagesHelper.access$1500(r18, r19, r20, r21, r22, r24, r25);
        r26 = r26 + 1;
        goto L_0x0a97;
    L_0x0ad3:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "total send time = ";
        r4 = r4.append(r5);
        r10 = java.lang.System.currentTimeMillis();
        r10 = r10 - r30;
        r4 = r4.append(r10);
        r4 = r4.toString();
        org.telegram.messenger.FileLog.d(r4);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper$21.run():void");
    }
}
