package org.telegram.messenger;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.crashlytics.android.p064a.C1333b;
import com.crashlytics.android.p064a.C1351m;
import com.p118i.p119a.C1998a;
import com.p118i.p119a.C2002c;
import com.p118i.p119a.C2002c.C2001a;
import com.p118i.p119a.C2010f;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;
import org.telegram.customization.fetch.p165c.C2747a;
import org.telegram.customization.fetch.p166d.C2752b;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentEncrypted;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_fileEncryptedLocation;
import org.telegram.tgnet.TLRPC$TL_fileLocation;
import org.telegram.tgnet.TLRPC$TL_inputDocumentFileLocation;
import org.telegram.tgnet.TLRPC$TL_inputEncryptedFileLocation;
import org.telegram.tgnet.TLRPC$TL_inputFileLocation;
import org.telegram.tgnet.TLRPC$TL_inputWebFileLocation;
import org.telegram.tgnet.TLRPC$TL_upload_cdnFile;
import org.telegram.tgnet.TLRPC$TL_upload_cdnFileReuploadNeeded;
import org.telegram.tgnet.TLRPC$TL_upload_file;
import org.telegram.tgnet.TLRPC$TL_upload_fileCdnRedirect;
import org.telegram.tgnet.TLRPC$TL_upload_getCdnFile;
import org.telegram.tgnet.TLRPC$TL_upload_getCdnFileHashes;
import org.telegram.tgnet.TLRPC$TL_upload_getFile;
import org.telegram.tgnet.TLRPC$TL_upload_getWebFile;
import org.telegram.tgnet.TLRPC$TL_upload_reuploadCdnFile;
import org.telegram.tgnet.TLRPC$TL_upload_webFile;
import org.telegram.tgnet.TLRPC$TL_webDocument;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputFileLocation;
import org.telegram.tgnet.TLRPC.TL_cdnFileHash;
import org.telegram.ui.ChatActivity;
import utils.C5319b;

public class FileLoadOperation implements C2010f, C2747a {
    private static final int bigFileSizeFrom = 1048576;
    private static final int cdnChunkCheckSize = 131072;
    private static final int downloadChunkSize = 32768;
    private static final int downloadChunkSizeBig = 131072;
    private static final int maxDownloadRequests = 4;
    private static final int maxDownloadRequestsBig = 2;
    private static final int stateDownloading = 1;
    private static final int stateFailed = 2;
    private static final int stateFinished = 3;
    private static final int stateIdle = 0;
    private int bytesCountPadding;
    private File cacheFileFinal;
    private File cacheFileTemp;
    private File cacheIvTemp;
    String cacheStat;
    private byte[] cdnCheckBytes;
    private int cdnDatacenterId;
    private HashMap<Integer, TL_cdnFileHash> cdnHashes;
    private byte[] cdnIv;
    private byte[] cdnKey;
    private byte[] cdnToken;
    private int currentDownloadChunkSize;
    private int currentMaxDownloadRequests;
    private int currentType;
    private int datacenter_id;
    private ArrayList<RequestInfo> delayedRequestInfos;
    private FileLoadOperationDelegate delegate;
    private long downloadId;
    private int downloadIdThin;
    private int downloadedBytes;
    private boolean encryptFile;
    private byte[] encryptIv;
    private byte[] encryptKey;
    String eventData;
    private String ext;
    private RandomAccessFile fileOutputStream;
    private RandomAccessFile fileReadStream;
    private RandomAccessFile fiv;
    private boolean isCdn;
    private boolean isForceRequest;
    private byte[] iv;
    private byte[] key;
    private int lastCheckedCdnPart;
    private InputFileLocation location;
    String monoUrl;
    private int nextDownloadOffset;
    private int renameRetryCount;
    private ArrayList<RequestInfo> requestInfos;
    private boolean requestSentToMono;
    private boolean requestingCdnOffsets;
    private int requestsCount;
    private boolean reuploadingCdn;
    private boolean started;
    private volatile int state;
    private File storePath;
    private File tempPath;
    private int totalBytesCount;
    private TLRPC$TL_inputWebFileLocation webLocation;

    /* renamed from: org.telegram.messenger.FileLoadOperation$1 */
    class C30241 implements Runnable {
        C30241() {
        }

        public void run() {
            if (FileLoadOperation.this.totalBytesCount == 0 || FileLoadOperation.this.downloadedBytes != FileLoadOperation.this.totalBytesCount) {
                FileLoadOperation.this.startDownloadRequest();
                return;
            }
            try {
                FileLoadOperation.this.onFinishLoadingFile(false);
            } catch (Exception e) {
                FileLoadOperation.this.onFail(true, 0);
            }
        }
    }

    /* renamed from: org.telegram.messenger.FileLoadOperation$2 */
    class C30252 implements Runnable {
        C30252() {
        }

        public void run() {
            if (FileLoadOperation.this.downloadId >= 0) {
                ApplicationLoader.fetch.m12740a(FileLoadOperation.this.downloadId);
                FileLoadOperation.this.downloadId = -1;
            }
            if (FileLoadOperation.this.state != 3 && FileLoadOperation.this.state != 2) {
                if (FileLoadOperation.this.requestInfos != null) {
                    for (int i = 0; i < FileLoadOperation.this.requestInfos.size(); i++) {
                        RequestInfo requestInfo = (RequestInfo) FileLoadOperation.this.requestInfos.get(i);
                        if (requestInfo.requestToken != 0) {
                            ConnectionsManager.getInstance().cancelRequest(requestInfo.requestToken, true);
                        }
                    }
                }
                FileLoadOperation.this.onFail(false, 1);
            }
        }
    }

    /* renamed from: org.telegram.messenger.FileLoadOperation$4 */
    class C30274 implements RequestDelegate {
        C30274() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLRPC$TL_error != null) {
                FileLoadOperation.this.onFail(false, 0);
                return;
            }
            int i;
            FileLoadOperation.this.requestingCdnOffsets = false;
            TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
            if (!tLRPC$Vector.objects.isEmpty()) {
                if (FileLoadOperation.this.cdnHashes == null) {
                    FileLoadOperation.this.cdnHashes = new HashMap();
                }
                for (i = 0; i < tLRPC$Vector.objects.size(); i++) {
                    TL_cdnFileHash tL_cdnFileHash = (TL_cdnFileHash) tLRPC$Vector.objects.get(i);
                    FileLoadOperation.this.cdnHashes.put(Integer.valueOf(tL_cdnFileHash.offset), tL_cdnFileHash);
                }
            }
            for (i = 0; i < FileLoadOperation.this.delayedRequestInfos.size(); i++) {
                RequestInfo requestInfo = (RequestInfo) FileLoadOperation.this.delayedRequestInfos.get(i);
                if (FileLoadOperation.this.downloadedBytes == requestInfo.offset) {
                    FileLoadOperation.this.delayedRequestInfos.remove(i);
                    if (!FileLoadOperation.this.processRequestResult(requestInfo, null)) {
                        if (requestInfo.response != null) {
                            requestInfo.response.disableFree = false;
                            requestInfo.response.freeResources();
                            return;
                        } else if (requestInfo.responseWeb != null) {
                            requestInfo.responseWeb.disableFree = false;
                            requestInfo.responseWeb.freeResources();
                            return;
                        } else if (requestInfo.responseCdn != null) {
                            requestInfo.responseCdn.disableFree = false;
                            requestInfo.responseCdn.freeResources();
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                }
            }
        }
    }

    /* renamed from: org.telegram.messenger.FileLoadOperation$8 */
    class C30318 implements Runnable {
        C30318() {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r8 = this;
            r7 = 1;
            r0 = org.telegram.messenger.FileLoadOperation.this;
            r0 = r0.monoUrl;
            r0 = new java.net.URL;	 Catch:{ Exception -> 0x00cb }
            r1 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r1 = r1.monoUrl;	 Catch:{ Exception -> 0x00cb }
            r0.<init>(r1);	 Catch:{ Exception -> 0x00cb }
            r0 = r0.openConnection();	 Catch:{ Exception -> 0x00cb }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x00cb }
            r1 = "Range";
            r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00cb }
            r2.<init>();	 Catch:{ Exception -> 0x00cb }
            r3 = "bytes=";
            r2 = r2.append(r3);	 Catch:{ Exception -> 0x00cb }
            r3 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r3 = r3.downloadedBytes;	 Catch:{ Exception -> 0x00cb }
            r2 = r2.append(r3);	 Catch:{ Exception -> 0x00cb }
            r3 = "-";
            r2 = r2.append(r3);	 Catch:{ Exception -> 0x00cb }
            r2 = r2.toString();	 Catch:{ Exception -> 0x00cb }
            r0.setRequestProperty(r1, r2);	 Catch:{ Exception -> 0x00cb }
            r1 = 1;
            r0.setDoInput(r1);	 Catch:{ Exception -> 0x00cb }
            r1 = new java.io.BufferedInputStream;	 Catch:{ Exception -> 0x00cb }
            r0 = r0.getInputStream();	 Catch:{ Exception -> 0x00cb }
            r1.<init>(r0);	 Catch:{ Exception -> 0x00cb }
            r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
            r0 = new byte[r0];	 Catch:{ Exception -> 0x00cb }
        L_0x004c:
            r2 = 0;
            r3 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
            r2 = r1.read(r0, r2, r3);	 Catch:{ Exception -> 0x00cb }
            if (r2 <= 0) goto L_0x00d5;
        L_0x0055:
            r3 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r3 = r3.state;	 Catch:{ Exception -> 0x00cb }
            if (r3 != r7) goto L_0x0090;
        L_0x005d:
            r3 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r3 = r3.totalBytesCount;	 Catch:{ Exception -> 0x00cb }
            if (r3 <= 0) goto L_0x0073;
        L_0x0065:
            r3 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r3 = r3.nextDownloadOffset;	 Catch:{ Exception -> 0x00cb }
            r4 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r4 = r4.totalBytesCount;	 Catch:{ Exception -> 0x00cb }
            if (r3 >= r4) goto L_0x0090;
        L_0x0073:
            r3 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r3 = r3.requestInfos;	 Catch:{ Exception -> 0x00cb }
            r3 = r3.size();	 Catch:{ Exception -> 0x00cb }
            r4 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r4 = r4.delayedRequestInfos;	 Catch:{ Exception -> 0x00cb }
            r4 = r4.size();	 Catch:{ Exception -> 0x00cb }
            r3 = r3 + r4;
            r4 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r4 = r4.currentMaxDownloadRequests;	 Catch:{ Exception -> 0x00cb }
            if (r3 < r4) goto L_0x0094;
        L_0x0090:
            r1.close();	 Catch:{ Exception -> 0x00cb }
        L_0x0093:
            return;
        L_0x0094:
            r3 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r3 = r3.fileOutputStream;	 Catch:{ Exception -> 0x00cb }
            r4 = 0;
            r3.write(r0, r4, r2);	 Catch:{ Exception -> 0x00cb }
            r3 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r4 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r4 = r4.downloadedBytes;	 Catch:{ Exception -> 0x00cb }
            r2 = r2 + r4;
            r3.downloadedBytes = r2;	 Catch:{ Exception -> 0x00cb }
            r2 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r2 = r2.delegate;	 Catch:{ Exception -> 0x00cb }
            r3 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r4 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r5 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r5 = r5.downloadedBytes;	 Catch:{ Exception -> 0x00cb }
            r5 = (float) r5;	 Catch:{ Exception -> 0x00cb }
            r6 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r6 = r6.totalBytesCount;	 Catch:{ Exception -> 0x00cb }
            r6 = (float) r6;	 Catch:{ Exception -> 0x00cb }
            r5 = r5 / r6;
            r4 = java.lang.Math.min(r4, r5);	 Catch:{ Exception -> 0x00cb }
            r2.didChangedLoadProgress(r3, r4);	 Catch:{ Exception -> 0x00cb }
            goto L_0x004c;
        L_0x00cb:
            r0 = move-exception;
            r0.printStackTrace();
            r0 = org.telegram.messenger.FileLoadOperation.this;
            r0.startDownloadRequestByTelegram();
            goto L_0x0093;
        L_0x00d5:
            r0 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r0 = r0.downloadedBytes;	 Catch:{ Exception -> 0x00cb }
            r1 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r1 = r1.totalBytesCount;	 Catch:{ Exception -> 0x00cb }
            if (r0 != r1) goto L_0x00ea;
        L_0x00e3:
            r0 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r1 = 1;
            r0.onFinishLoadingFile(r1);	 Catch:{ Exception -> 0x00cb }
            goto L_0x0093;
        L_0x00ea:
            r0 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r1 = "";
            r0.monoUrl = r1;	 Catch:{ Exception -> 0x00cb }
            r0 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00cb }
            r0.startDownloadRequestAfterCheckMono();	 Catch:{ Exception -> 0x00cb }
            goto L_0x0093;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileLoadOperation.8.run():void");
        }
    }

    public interface FileLoadOperationDelegate {
        void didChangedLoadProgress(FileLoadOperation fileLoadOperation, float f);

        void didFailedLoadingFile(FileLoadOperation fileLoadOperation, int i);

        void didFinishLoadingFile(FileLoadOperation fileLoadOperation, File file);
    }

    private static class RequestInfo {
        private int offset;
        private int requestToken;
        private TLRPC$TL_upload_file response;
        private TLRPC$TL_upload_cdnFile responseCdn;
        private TLRPC$TL_upload_webFile responseWeb;

        private RequestInfo() {
        }
    }

    public FileLoadOperation(Document document) {
        int i = -1;
        this.state = 0;
        this.monoUrl = null;
        this.requestSentToMono = false;
        this.cacheStat = TtmlNode.ANONYMOUS_REGION_ID;
        this.eventData = TtmlNode.ANONYMOUS_REGION_ID;
        this.downloadId = -1;
        this.downloadIdThin = -1;
        try {
            String str;
            if (document instanceof TLRPC$TL_documentEncrypted) {
                this.location = new TLRPC$TL_inputEncryptedFileLocation();
                this.location.id = document.id;
                this.location.access_hash = document.access_hash;
                this.datacenter_id = document.dc_id;
                this.iv = new byte[32];
                System.arraycopy(document.iv, 0, this.iv, 0, this.iv.length);
                this.key = document.key;
            } else if ((document instanceof TLRPC$TL_document) || document.access_hash == -1) {
                this.location = new TLRPC$TL_inputDocumentFileLocation();
                this.location.id = document.id;
                this.location.access_hash = document.access_hash;
                this.datacenter_id = document.dc_id;
            }
            this.totalBytesCount = document.size;
            if (!(this.key == null || this.totalBytesCount % 16 == 0)) {
                this.bytesCountPadding = 16 - (this.totalBytesCount % 16);
                this.totalBytesCount += this.bytesCountPadding;
            }
            this.ext = FileLoader.getDocumentFileName(document);
            if (this.ext != null) {
                int lastIndexOf = this.ext.lastIndexOf(46);
                if (lastIndexOf != -1) {
                    this.ext = this.ext.substring(lastIndexOf);
                    if ("audio/ogg".equals(document.mime_type)) {
                        this.currentType = ConnectionsManager.FileTypeAudio;
                    } else if (MimeTypes.VIDEO_MP4.equals(document.mime_type)) {
                        this.currentType = ConnectionsManager.FileTypeFile;
                    } else {
                        this.currentType = ConnectionsManager.FileTypeVideo;
                    }
                    if (this.ext.length() > 1) {
                    }
                    if (document.mime_type == null) {
                        str = document.mime_type;
                        switch (str.hashCode()) {
                            case 187091926:
                                if (str.equals("audio/ogg")) {
                                    i = 1;
                                    break;
                                }
                                break;
                            case 1331848029:
                                if (str.equals(MimeTypes.VIDEO_MP4)) {
                                    i = 0;
                                    break;
                                }
                                break;
                        }
                        switch (i) {
                            case 0:
                                this.ext = ".mp4";
                                return;
                            case 1:
                                this.ext = ".ogg";
                                return;
                            default:
                                this.ext = TtmlNode.ANONYMOUS_REGION_ID;
                                return;
                        }
                    }
                    this.ext = TtmlNode.ANONYMOUS_REGION_ID;
                    return;
                }
            }
            this.ext = TtmlNode.ANONYMOUS_REGION_ID;
            if ("audio/ogg".equals(document.mime_type)) {
                this.currentType = ConnectionsManager.FileTypeAudio;
            } else if (MimeTypes.VIDEO_MP4.equals(document.mime_type)) {
                this.currentType = ConnectionsManager.FileTypeFile;
            } else {
                this.currentType = ConnectionsManager.FileTypeVideo;
            }
            if (this.ext.length() > 1) {
                if (document.mime_type == null) {
                    this.ext = TtmlNode.ANONYMOUS_REGION_ID;
                    return;
                }
                str = document.mime_type;
                switch (str.hashCode()) {
                    case 187091926:
                        if (str.equals("audio/ogg")) {
                            i = 1;
                            break;
                        }
                        break;
                    case 1331848029:
                        if (str.equals(MimeTypes.VIDEO_MP4)) {
                            i = 0;
                            break;
                        }
                        break;
                }
                switch (i) {
                    case 0:
                        this.ext = ".mp4";
                        return;
                    case 1:
                        this.ext = ".ogg";
                        return;
                    default:
                        this.ext = TtmlNode.ANONYMOUS_REGION_ID;
                        return;
                }
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
            onFail(true, 0);
        }
    }

    public FileLoadOperation(FileLocation fileLocation, String str, int i) {
        this.state = 0;
        this.monoUrl = null;
        this.requestSentToMono = false;
        this.cacheStat = TtmlNode.ANONYMOUS_REGION_ID;
        this.eventData = TtmlNode.ANONYMOUS_REGION_ID;
        this.downloadId = -1;
        this.downloadIdThin = -1;
        if (fileLocation instanceof TLRPC$TL_fileEncryptedLocation) {
            this.location = new TLRPC$TL_inputEncryptedFileLocation();
            this.location.id = fileLocation.volume_id;
            this.location.volume_id = fileLocation.volume_id;
            this.location.access_hash = fileLocation.secret;
            this.location.local_id = fileLocation.local_id;
            this.iv = new byte[32];
            System.arraycopy(fileLocation.iv, 0, this.iv, 0, this.iv.length);
            this.key = fileLocation.key;
            this.datacenter_id = fileLocation.dc_id;
        } else if (fileLocation instanceof TLRPC$TL_fileLocation) {
            this.location = new TLRPC$TL_inputFileLocation();
            this.location.volume_id = fileLocation.volume_id;
            this.location.secret = fileLocation.secret;
            this.location.local_id = fileLocation.local_id;
            this.datacenter_id = fileLocation.dc_id;
        }
        this.currentType = 16777216;
        this.totalBytesCount = i;
        if (str == null) {
            str = "jpg";
        }
        this.ext = str;
    }

    public FileLoadOperation(TLRPC$TL_webDocument tLRPC$TL_webDocument) {
        this.state = 0;
        this.monoUrl = null;
        this.requestSentToMono = false;
        this.cacheStat = TtmlNode.ANONYMOUS_REGION_ID;
        this.eventData = TtmlNode.ANONYMOUS_REGION_ID;
        this.downloadId = -1;
        this.downloadIdThin = -1;
        this.webLocation = new TLRPC$TL_inputWebFileLocation();
        this.webLocation.url = tLRPC$TL_webDocument.url;
        this.webLocation.access_hash = tLRPC$TL_webDocument.access_hash;
        this.totalBytesCount = tLRPC$TL_webDocument.size;
        this.datacenter_id = tLRPC$TL_webDocument.dc_id;
        String extensionByMime = FileLoader.getExtensionByMime(tLRPC$TL_webDocument.mime_type);
        if (tLRPC$TL_webDocument.mime_type.startsWith("image/")) {
            this.currentType = 16777216;
        } else if (tLRPC$TL_webDocument.mime_type.equals("audio/ogg")) {
            this.currentType = ConnectionsManager.FileTypeAudio;
        } else if (tLRPC$TL_webDocument.mime_type.startsWith("video/")) {
            this.currentType = ConnectionsManager.FileTypeVideo;
        } else {
            this.currentType = ConnectionsManager.FileTypeFile;
        }
        this.ext = ImageLoader.getHttpUrlExtension(tLRPC$TL_webDocument.url, extensionByMime);
    }

    private void cleanup() {
        try {
            if (this.fileOutputStream != null) {
                try {
                    this.fileOutputStream.getChannel().close();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
                this.fileOutputStream.close();
                this.fileOutputStream = null;
            }
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
        }
        try {
            if (this.fileReadStream != null) {
                try {
                    this.fileReadStream.getChannel().close();
                } catch (Throwable e22) {
                    FileLog.m13728e(e22);
                }
                this.fileReadStream.close();
                this.fileReadStream = null;
            }
        } catch (Throwable e222) {
            FileLog.m13728e(e222);
        }
        try {
            if (this.fiv != null) {
                this.fiv.close();
                this.fiv = null;
            }
        } catch (Throwable e2222) {
            FileLog.m13728e(e2222);
        }
        if (this.delayedRequestInfos != null) {
            for (int i = 0; i < this.delayedRequestInfos.size(); i++) {
                RequestInfo requestInfo = (RequestInfo) this.delayedRequestInfos.get(i);
                if (requestInfo.response != null) {
                    requestInfo.response.disableFree = false;
                    requestInfo.response.freeResources();
                } else if (requestInfo.responseWeb != null) {
                    requestInfo.responseWeb.disableFree = false;
                    requestInfo.responseWeb.freeResources();
                } else if (requestInfo.responseCdn != null) {
                    requestInfo.responseCdn.disableFree = false;
                    requestInfo.responseCdn.freeResources();
                }
            }
            this.delayedRequestInfos.clear();
        }
    }

    private void clearOperaion(RequestInfo requestInfo) {
        int i;
        int i2 = Integer.MAX_VALUE;
        for (i = 0; i < this.requestInfos.size(); i++) {
            RequestInfo requestInfo2 = (RequestInfo) this.requestInfos.get(i);
            i2 = Math.min(requestInfo2.offset, i2);
            if (!(requestInfo == requestInfo2 || requestInfo2.requestToken == 0)) {
                ConnectionsManager.getInstance().cancelRequest(requestInfo2.requestToken, true);
            }
        }
        this.requestInfos.clear();
        for (i = 0; i < this.delayedRequestInfos.size(); i++) {
            requestInfo2 = (RequestInfo) this.delayedRequestInfos.get(i);
            if (requestInfo2.response != null) {
                requestInfo2.response.disableFree = false;
                requestInfo2.response.freeResources();
            } else if (requestInfo2.responseWeb != null) {
                requestInfo2.responseWeb.disableFree = false;
                requestInfo2.responseWeb.freeResources();
            } else if (requestInfo2.responseCdn != null) {
                requestInfo2.responseCdn.disableFree = false;
                requestInfo2.responseCdn.freeResources();
            }
            i2 = Math.min(requestInfo2.offset, i2);
        }
        this.delayedRequestInfos.clear();
        this.requestsCount = 0;
        this.nextDownloadOffset = i2;
    }

    private void delayRequestInfo(RequestInfo requestInfo) {
        this.delayedRequestInfos.add(requestInfo);
        if (requestInfo.response != null) {
            requestInfo.response.disableFree = true;
        } else if (requestInfo.responseWeb != null) {
            requestInfo.responseWeb.disableFree = true;
        } else if (requestInfo.responseCdn != null) {
            requestInfo.responseCdn.disableFree = true;
        }
    }

    private void logToMono(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                C1333b.m6818c().m6821a((C1351m) new C1351m("NETWORK_ERROR").m6784a("Download File", str + "_" + this.monoUrl));
            } catch (Exception e) {
            }
        }
    }

    private void onFail(boolean z, final int i) {
        cleanup();
        if (this.downloadId >= 0) {
            ApplicationLoader.fetch.m12740a(this.downloadId);
            this.downloadId = -1;
        }
        if (this.downloadIdThin >= 0) {
            ApplicationLoader.thinDl.m9082a(this.downloadIdThin);
            this.downloadIdThin = -1;
        }
        this.state = 2;
        if (z) {
            Utilities.stageQueue.postRunnable(new Runnable() {
                public void run() {
                    FileLoadOperation.this.delegate.didFailedLoadingFile(FileLoadOperation.this, i);
                }
            });
        } else {
            this.delegate.didFailedLoadingFile(this, i);
        }
    }

    private void onFinishLoadingFile(final boolean z) {
        if (this.state == 1) {
            this.state = 3;
            cleanup();
            if (this.cacheIvTemp != null) {
                this.cacheIvTemp.delete();
                this.cacheIvTemp = null;
            }
            if (!(this.cacheFileTemp == null || this.cacheFileTemp.renameTo(this.cacheFileFinal))) {
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.m13726e("unable to rename temp = " + this.cacheFileTemp + " to final = " + this.cacheFileFinal + " retry = " + this.renameRetryCount);
                }
                this.renameRetryCount++;
                if (this.renameRetryCount < 3) {
                    this.state = 1;
                    Utilities.stageQueue.postRunnable(new Runnable() {
                        public void run() {
                            try {
                                FileLoadOperation.this.onFinishLoadingFile(z);
                            } catch (Exception e) {
                                FileLoadOperation.this.onFail(false, 0);
                            }
                        }
                    }, 200);
                    return;
                }
                this.cacheFileFinal = this.cacheFileTemp;
            }
            if (BuildVars.DEBUG_VERSION) {
                FileLog.m13726e("finished downloading file to " + this.cacheFileFinal);
            }
            this.delegate.didFinishLoadingFile(this, this.cacheFileFinal);
            if (!z) {
                return;
            }
            if (this.currentType == ConnectionsManager.FileTypeAudio) {
                StatsController.getInstance().incrementReceivedItemsCount(ConnectionsManager.getCurrentNetworkType(), 3, 1);
            } else if (this.currentType == ConnectionsManager.FileTypeVideo) {
                StatsController.getInstance().incrementReceivedItemsCount(ConnectionsManager.getCurrentNetworkType(), 2, 1);
            } else if (this.currentType == 16777216) {
                StatsController.getInstance().incrementReceivedItemsCount(ConnectionsManager.getCurrentNetworkType(), 4, 1);
            } else if (this.currentType == ConnectionsManager.FileTypeFile) {
                StatsController.getInstance().incrementReceivedItemsCount(ConnectionsManager.getCurrentNetworkType(), 5, 1);
            }
        }
    }

    private boolean processRequestResult(RequestInfo requestInfo, TLRPC$TL_error tLRPC$TL_error) {
        Integer num = null;
        if (this.state != 1) {
            return false;
        }
        this.requestInfos.remove(requestInfo);
        if (tLRPC$TL_error == null) {
            try {
                if (this.downloadedBytes != requestInfo.offset) {
                    delayRequestInfo(requestInfo);
                    return false;
                }
                NativeByteBuffer nativeByteBuffer;
                if (requestInfo.response != null) {
                    nativeByteBuffer = requestInfo.response.bytes;
                } else if (requestInfo.responseWeb != null) {
                    nativeByteBuffer = requestInfo.responseWeb.bytes;
                } else if (requestInfo.responseCdn != null) {
                    nativeByteBuffer = requestInfo.responseCdn.bytes;
                } else {
                    Object obj = num;
                }
                if (nativeByteBuffer == null || nativeByteBuffer.limit() == 0) {
                    onFinishLoadingFile(true);
                    return false;
                }
                int i;
                int access$1500;
                int limit = nativeByteBuffer.limit();
                if (this.isCdn) {
                    int i2 = (this.downloadedBytes + limit) / 131072;
                    i = (i2 - (this.lastCheckedCdnPart != i2 ? 1 : 0)) * 131072;
                    if (this.cdnHashes != null) {
                        num = (TL_cdnFileHash) this.cdnHashes.get(Integer.valueOf(i));
                    }
                    if (num == null) {
                        delayRequestInfo(requestInfo);
                        requestFileOffsets(i);
                        return true;
                    }
                }
                if (requestInfo.responseCdn != null) {
                    access$1500 = requestInfo.offset / 16;
                    this.cdnIv[15] = (byte) (access$1500 & 255);
                    this.cdnIv[14] = (byte) ((access$1500 >> 8) & 255);
                    this.cdnIv[13] = (byte) ((access$1500 >> 16) & 255);
                    this.cdnIv[12] = (byte) ((access$1500 >> 24) & 255);
                    Utilities.aesCtrDecryption(nativeByteBuffer.buffer, this.cdnKey, this.cdnIv, 0, nativeByteBuffer.limit());
                }
                this.downloadedBytes += limit;
                boolean z = limit != this.currentDownloadChunkSize || ((this.totalBytesCount == this.downloadedBytes || this.downloadedBytes % this.currentDownloadChunkSize != 0) && (this.totalBytesCount <= 0 || this.totalBytesCount <= this.downloadedBytes));
                if (this.key != null) {
                    Utilities.aesIgeEncryption(nativeByteBuffer.buffer, this.key, this.iv, false, true, 0, nativeByteBuffer.limit());
                    if (z && this.bytesCountPadding != 0) {
                        nativeByteBuffer.limit(nativeByteBuffer.limit() - this.bytesCountPadding);
                    }
                }
                if (this.encryptFile) {
                    access$1500 = requestInfo.offset / 16;
                    this.encryptIv[15] = (byte) (access$1500 & 255);
                    this.encryptIv[14] = (byte) ((access$1500 >> 8) & 255);
                    this.encryptIv[13] = (byte) ((access$1500 >> 16) & 255);
                    this.encryptIv[12] = (byte) ((access$1500 >> 24) & 255);
                    Utilities.aesCtrDecryption(nativeByteBuffer.buffer, this.encryptKey, this.encryptIv, 0, nativeByteBuffer.limit());
                }
                this.fileOutputStream.getChannel().write(nativeByteBuffer.buffer);
                if (this.isCdn) {
                    limit = this.downloadedBytes / 131072;
                    if (limit != this.lastCheckedCdnPart || z) {
                        this.fileOutputStream.getFD().sync();
                        i = (limit - (this.lastCheckedCdnPart != limit ? 1 : 0)) * 131072;
                        TL_cdnFileHash tL_cdnFileHash = (TL_cdnFileHash) this.cdnHashes.get(Integer.valueOf(i));
                        if (this.fileReadStream == null) {
                            this.cdnCheckBytes = new byte[131072];
                            this.fileReadStream = new RandomAccessFile(this.cacheFileTemp, "r");
                            if (i != 0) {
                                this.fileReadStream.seek((long) i);
                            }
                        }
                        i = this.lastCheckedCdnPart != limit ? 131072 : this.downloadedBytes - (limit * 131072);
                        this.fileReadStream.readFully(this.cdnCheckBytes, 0, i);
                        if (Arrays.equals(Utilities.computeSHA256(this.cdnCheckBytes, 0, i), tL_cdnFileHash.hash)) {
                            this.lastCheckedCdnPart = limit;
                        } else {
                            if (this.location != null) {
                                FileLog.m13726e("invalid cdn hash " + this.location + " id = " + this.location.id + " local_id = " + this.location.local_id + " access_hash = " + this.location.access_hash + " volume_id = " + this.location.volume_id + " secret = " + this.location.secret);
                            } else if (this.webLocation != null) {
                                FileLog.m13726e("invalid cdn hash  " + this.webLocation + " id = " + this.webLocation.url + " access_hash = " + this.webLocation.access_hash);
                            }
                            onFail(false, 0);
                            this.cacheFileTemp.delete();
                            return false;
                        }
                    }
                }
                if (this.fiv != null) {
                    this.fiv.seek(0);
                    this.fiv.write(this.iv);
                }
                if (this.totalBytesCount > 0 && this.state == 1) {
                    this.delegate.didChangedLoadProgress(this, Math.min(1.0f, ((float) this.downloadedBytes) / ((float) this.totalBytesCount)));
                }
                i = 0;
                while (i < this.delayedRequestInfos.size()) {
                    RequestInfo requestInfo2 = (RequestInfo) this.delayedRequestInfos.get(i);
                    if (this.downloadedBytes == requestInfo2.offset) {
                        this.delayedRequestInfos.remove(i);
                        if (!processRequestResult(requestInfo2, null)) {
                            if (requestInfo2.response != null) {
                                requestInfo2.response.disableFree = false;
                                requestInfo2.response.freeResources();
                            } else if (requestInfo2.responseWeb != null) {
                                requestInfo2.responseWeb.disableFree = false;
                                requestInfo2.responseWeb.freeResources();
                            } else if (requestInfo2.responseCdn != null) {
                                requestInfo2.responseCdn.disableFree = false;
                                requestInfo2.responseCdn.freeResources();
                            }
                        }
                        if (z) {
                            startDownloadRequest();
                        } else {
                            onFinishLoadingFile(true);
                        }
                    } else {
                        i++;
                    }
                }
                if (z) {
                    startDownloadRequest();
                } else {
                    onFinishLoadingFile(true);
                }
            } catch (Throwable e) {
                onFail(false, 0);
                FileLog.m13728e(e);
            }
        } else if (tLRPC$TL_error.text.contains("FILE_MIGRATE_")) {
            Scanner scanner = new Scanner(tLRPC$TL_error.text.replace("FILE_MIGRATE_", TtmlNode.ANONYMOUS_REGION_ID));
            scanner.useDelimiter(TtmlNode.ANONYMOUS_REGION_ID);
            try {
                num = Integer.valueOf(scanner.nextInt());
            } catch (Exception e2) {
            }
            if (num == null) {
                onFail(false, 0);
            } else {
                this.datacenter_id = num.intValue();
                this.nextDownloadOffset = 0;
                startDownloadRequest();
            }
        } else if (tLRPC$TL_error.text.contains("OFFSET_INVALID")) {
            if (this.downloadedBytes % this.currentDownloadChunkSize == 0) {
                try {
                    onFinishLoadingFile(true);
                } catch (Throwable e3) {
                    FileLog.m13728e(e3);
                    onFail(false, 0);
                }
            } else {
                onFail(false, 0);
            }
        } else if (tLRPC$TL_error.text.contains("RETRY_LIMIT")) {
            onFail(false, 2);
        } else {
            if (this.location != null) {
                FileLog.m13726e(TtmlNode.ANONYMOUS_REGION_ID + this.location + " id = " + this.location.id + " local_id = " + this.location.local_id + " access_hash = " + this.location.access_hash + " volume_id = " + this.location.volume_id + " secret = " + this.location.secret);
            } else if (this.webLocation != null) {
                FileLog.m13726e(TtmlNode.ANONYMOUS_REGION_ID + this.webLocation + " id = " + this.webLocation.url + " access_hash = " + this.webLocation.access_hash);
            }
            onFail(false, 0);
        }
        return false;
    }

    private void requestFileOffsets(int i) {
        if (!this.requestingCdnOffsets) {
            this.requestingCdnOffsets = true;
            TLObject tLRPC$TL_upload_getCdnFileHashes = new TLRPC$TL_upload_getCdnFileHashes();
            tLRPC$TL_upload_getCdnFileHashes.file_token = this.cdnToken;
            tLRPC$TL_upload_getCdnFileHashes.offset = i;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_upload_getCdnFileHashes, new C30274(), null, null, 0, this.datacenter_id, 1, true);
        }
    }

    private void startDownloadRequest() {
        if (ConnectionsManager.isNetworkOnline()) {
            DocAvailableInfo docAvailableInfo = new DocAvailableInfo(this.location.id, this.location.local_id, this.location.volume_id, 0, false);
            this.cacheStat = C5319b.a(C5319b.a(docAvailableInfo));
            this.eventData = this.location.id + "," + this.location.local_id + "," + this.location.volume_id + " , " + System.currentTimeMillis();
            if (this.monoUrl != null || this.storePath.equals(this.tempPath)) {
                startDownloadRequestAfterCheckMono();
            } else if (this.requestSentToMono) {
                startDownloadRequestAfterCheckMono();
            } else {
                this.requestSentToMono = true;
                this.monoUrl = C5319b.b(docAvailableInfo);
                startDownloadRequestAfterCheckMono();
            }
        }
    }

    private void startDownloadRequestAfterCheckMono() {
        if (TextUtils.isEmpty(this.monoUrl)) {
            startDownloadRequestByTelegram();
        } else if (this.state != 1 || ((this.totalBytesCount > 0 && this.nextDownloadOffset >= this.totalBytesCount) || this.requestInfos.size() + this.delayedRequestInfos.size() >= this.currentMaxDownloadRequests)) {
            startDownloadRequestByTelegram();
        } else {
            C2752b c2752b = new C2752b(this.monoUrl, this.cacheFileTemp.getPath());
            c2752b.m12747a(601);
            this.downloadId = ApplicationLoader.fetch.m12738a(c2752b, (long) this.totalBytesCount);
            if (this.downloadId != -1) {
                ApplicationLoader.fetch.m12741a((C2747a) this);
            } else {
                startDownloadRequestByTelegram();
            }
        }
    }

    private void startDownloadRequestAfterCheckMonoOld() {
        if (TextUtils.isEmpty(this.monoUrl)) {
            startDownloadRequestByTelegram();
        } else if (this.state == 1 && this.requestInfos.size() + this.delayedRequestInfos.size() < this.currentMaxDownloadRequests) {
            new Thread(new C30318()).start();
        }
    }

    private void startDownloadRequestAfterCheckMonoThin() {
        if (TextUtils.isEmpty(this.monoUrl)) {
            startDownloadRequestByTelegram();
        } else if (this.state != 1 || this.requestInfos.size() + this.delayedRequestInfos.size() >= this.currentMaxDownloadRequests) {
            startDownloadRequestByTelegram();
        } else {
            Uri parse = Uri.parse(this.monoUrl);
            this.downloadIdThin = ApplicationLoader.thinDl.m9083a(new C2002c(parse).m9050a(new C1998a()).m9047a(Uri.parse(this.cacheFileTemp.getPath())).m9048a(C2001a.HIGH).m9051a(ApplicationLoader.applicationContext).m9049a((C2010f) this));
        }
    }

    private void startDownloadRequestByTelegram() {
        if (this.state != 1) {
            return;
        }
        if ((this.totalBytesCount <= 0 || this.nextDownloadOffset < this.totalBytesCount) && this.requestInfos.size() + this.delayedRequestInfos.size() < this.currentMaxDownloadRequests) {
            int max = this.totalBytesCount > 0 ? Math.max(0, this.currentMaxDownloadRequests - this.requestInfos.size()) : 1;
            int i = 0;
            while (i < max) {
                if (this.totalBytesCount <= 0 || this.nextDownloadOffset < this.totalBytesCount) {
                    TLObject tLRPC$TL_upload_getCdnFile;
                    int i2;
                    boolean z = this.totalBytesCount <= 0 || i == max - 1 || (this.totalBytesCount > 0 && this.nextDownloadOffset + this.currentDownloadChunkSize >= this.totalBytesCount);
                    int i3 = this.requestsCount % 2 == 0 ? 2 : ConnectionsManager.ConnectionTypeDownload2;
                    int i4 = (this.isForceRequest ? 32 : 0) | 2;
                    if (this.isCdn) {
                        tLRPC$TL_upload_getCdnFile = new TLRPC$TL_upload_getCdnFile();
                        tLRPC$TL_upload_getCdnFile.file_token = this.cdnToken;
                        i2 = this.nextDownloadOffset;
                        tLRPC$TL_upload_getCdnFile.offset = i2;
                        tLRPC$TL_upload_getCdnFile.limit = this.currentDownloadChunkSize;
                        i4 |= 1;
                    } else if (this.webLocation != null) {
                        tLRPC$TL_upload_getCdnFile = new TLRPC$TL_upload_getWebFile();
                        tLRPC$TL_upload_getCdnFile.location = this.webLocation;
                        i2 = this.nextDownloadOffset;
                        tLRPC$TL_upload_getCdnFile.offset = i2;
                        tLRPC$TL_upload_getCdnFile.limit = this.currentDownloadChunkSize;
                    } else {
                        tLRPC$TL_upload_getCdnFile = new TLRPC$TL_upload_getFile();
                        tLRPC$TL_upload_getCdnFile.location = this.location;
                        i2 = this.nextDownloadOffset;
                        tLRPC$TL_upload_getCdnFile.offset = i2;
                        tLRPC$TL_upload_getCdnFile.limit = this.currentDownloadChunkSize;
                    }
                    this.nextDownloadOffset += this.currentDownloadChunkSize;
                    final RequestInfo requestInfo = new RequestInfo();
                    this.requestInfos.add(requestInfo);
                    requestInfo.offset = i2;
                    requestInfo.requestToken = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_upload_getCdnFile, new RequestDelegate() {

                        /* renamed from: org.telegram.messenger.FileLoadOperation$6$1 */
                        class C30291 implements RequestDelegate {
                            C30291() {
                            }

                            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                FileLoadOperation.this.reuploadingCdn = false;
                                if (tLRPC$TL_error == null) {
                                    TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
                                    if (!tLRPC$Vector.objects.isEmpty()) {
                                        if (FileLoadOperation.this.cdnHashes == null) {
                                            FileLoadOperation.this.cdnHashes = new HashMap();
                                        }
                                        for (int i = 0; i < tLRPC$Vector.objects.size(); i++) {
                                            TL_cdnFileHash tL_cdnFileHash = (TL_cdnFileHash) tLRPC$Vector.objects.get(i);
                                            FileLoadOperation.this.cdnHashes.put(Integer.valueOf(tL_cdnFileHash.offset), tL_cdnFileHash);
                                        }
                                    }
                                    FileLoadOperation.this.startDownloadRequest();
                                } else if (tLRPC$TL_error.text.equals("FILE_TOKEN_INVALID") || tLRPC$TL_error.text.equals("REQUEST_TOKEN_INVALID")) {
                                    FileLoadOperation.this.isCdn = false;
                                    FileLoadOperation.this.clearOperaion(requestInfo);
                                    FileLoadOperation.this.startDownloadRequest();
                                } else {
                                    FileLoadOperation.this.onFail(false, 0);
                                }
                            }
                        }

                        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            int i = 0;
                            if (!FileLoadOperation.this.requestInfos.contains(requestInfo)) {
                                return;
                            }
                            if (tLRPC$TL_error != null && (tLRPC$TL_upload_getCdnFile instanceof TLRPC$TL_upload_getCdnFile) && tLRPC$TL_error.text.equals("FILE_TOKEN_INVALID")) {
                                FileLoadOperation.this.isCdn = false;
                                FileLoadOperation.this.clearOperaion(requestInfo);
                                FileLoadOperation.this.startDownloadRequest();
                            } else if (tLObject instanceof TLRPC$TL_upload_fileCdnRedirect) {
                                TLRPC$TL_upload_fileCdnRedirect tLRPC$TL_upload_fileCdnRedirect = (TLRPC$TL_upload_fileCdnRedirect) tLObject;
                                if (!tLRPC$TL_upload_fileCdnRedirect.cdn_file_hashes.isEmpty()) {
                                    if (FileLoadOperation.this.cdnHashes == null) {
                                        FileLoadOperation.this.cdnHashes = new HashMap();
                                    }
                                    while (i < tLRPC$TL_upload_fileCdnRedirect.cdn_file_hashes.size()) {
                                        TL_cdnFileHash tL_cdnFileHash = (TL_cdnFileHash) tLRPC$TL_upload_fileCdnRedirect.cdn_file_hashes.get(i);
                                        FileLoadOperation.this.cdnHashes.put(Integer.valueOf(tL_cdnFileHash.offset), tL_cdnFileHash);
                                        i++;
                                    }
                                }
                                if (tLRPC$TL_upload_fileCdnRedirect.encryption_iv == null || tLRPC$TL_upload_fileCdnRedirect.encryption_key == null || tLRPC$TL_upload_fileCdnRedirect.encryption_iv.length != 16 || tLRPC$TL_upload_fileCdnRedirect.encryption_key.length != 32) {
                                    TLRPC$TL_error tLRPC$TL_error2 = new TLRPC$TL_error();
                                    tLRPC$TL_error2.text = "bad redirect response";
                                    tLRPC$TL_error2.code = ChatActivity.scheduleDownloads;
                                    FileLoadOperation.this.processRequestResult(requestInfo, tLRPC$TL_error2);
                                    return;
                                }
                                FileLoadOperation.this.isCdn = true;
                                FileLoadOperation.this.cdnDatacenterId = tLRPC$TL_upload_fileCdnRedirect.dc_id;
                                FileLoadOperation.this.cdnIv = tLRPC$TL_upload_fileCdnRedirect.encryption_iv;
                                FileLoadOperation.this.cdnKey = tLRPC$TL_upload_fileCdnRedirect.encryption_key;
                                FileLoadOperation.this.cdnToken = tLRPC$TL_upload_fileCdnRedirect.file_token;
                                FileLoadOperation.this.clearOperaion(requestInfo);
                                FileLoadOperation.this.startDownloadRequest();
                            } else if (!(tLObject instanceof TLRPC$TL_upload_cdnFileReuploadNeeded)) {
                                if (tLObject instanceof TLRPC$TL_upload_file) {
                                    requestInfo.response = (TLRPC$TL_upload_file) tLObject;
                                } else if (tLObject instanceof TLRPC$TL_upload_webFile) {
                                    requestInfo.responseWeb = (TLRPC$TL_upload_webFile) tLObject;
                                } else {
                                    requestInfo.responseCdn = (TLRPC$TL_upload_cdnFile) tLObject;
                                }
                                if (tLObject != null) {
                                    if (FileLoadOperation.this.currentType == ConnectionsManager.FileTypeAudio) {
                                        StatsController.getInstance().incrementReceivedBytesCount(tLObject.networkType, 3, (long) (tLObject.getObjectSize() + 4));
                                    } else if (FileLoadOperation.this.currentType == ConnectionsManager.FileTypeVideo) {
                                        StatsController.getInstance().incrementReceivedBytesCount(tLObject.networkType, 2, (long) (tLObject.getObjectSize() + 4));
                                    } else if (FileLoadOperation.this.currentType == 16777216) {
                                        StatsController.getInstance().incrementReceivedBytesCount(tLObject.networkType, 4, (long) (tLObject.getObjectSize() + 4));
                                    } else if (FileLoadOperation.this.currentType == ConnectionsManager.FileTypeFile) {
                                        StatsController.getInstance().incrementReceivedBytesCount(tLObject.networkType, 5, (long) (tLObject.getObjectSize() + 4));
                                    }
                                }
                                FileLoadOperation.this.processRequestResult(requestInfo, tLRPC$TL_error);
                            } else if (!FileLoadOperation.this.reuploadingCdn) {
                                FileLoadOperation.this.clearOperaion(requestInfo);
                                FileLoadOperation.this.reuploadingCdn = true;
                                TLRPC$TL_upload_cdnFileReuploadNeeded tLRPC$TL_upload_cdnFileReuploadNeeded = (TLRPC$TL_upload_cdnFileReuploadNeeded) tLObject;
                                TLObject tLRPC$TL_upload_reuploadCdnFile = new TLRPC$TL_upload_reuploadCdnFile();
                                tLRPC$TL_upload_reuploadCdnFile.file_token = FileLoadOperation.this.cdnToken;
                                tLRPC$TL_upload_reuploadCdnFile.request_token = tLRPC$TL_upload_cdnFileReuploadNeeded.request_token;
                                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_upload_reuploadCdnFile, new C30291(), null, null, 0, FileLoadOperation.this.datacenter_id, 1, true);
                            }
                        }
                    }, null, null, i4, this.isCdn ? this.cdnDatacenterId : this.datacenter_id, i3, z);
                    this.requestsCount++;
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public void cancel() {
        Utilities.stageQueue.postRunnable(new C30252());
    }

    public int getCurrentType() {
        return this.currentType;
    }

    public String getFileName() {
        return this.location != null ? this.location.volume_id + "_" + this.location.local_id + "." + this.ext : Utilities.MD5(this.webLocation.url) + "." + this.ext;
    }

    public boolean isForceRequest() {
        return this.isForceRequest;
    }

    public void onDownloadComplete(C2002c c2002c) {
        try {
            onFinishLoadingFile(true);
        } catch (Exception e) {
        }
    }

    public void onDownloadFailed(C2002c c2002c, int i, String str) {
        startDownloadRequestByTelegram();
        logToMono(String.valueOf(i));
    }

    public void onProgress(C2002c c2002c, long j, long j2, int i) {
        this.delegate.didChangedLoadProgress(this, Math.min(1.0f, ((float) j2) / ((float) this.totalBytesCount)));
    }

    public void onUpdate(long j, int i, int i2, long j2, long j3, int i3) {
        if (j == this.downloadId) {
            String str;
            switch (i) {
                case -900:
                case 904:
                    ArrayList arrayList = new ArrayList();
                    DocAvailableInfo docAvailableInfo = new DocAvailableInfo(this.location.id, this.location.local_id, this.location.volume_id, 0, false);
                    docAvailableInfo.exists = false;
                    arrayList.add(docAvailableInfo);
                    C5319b.a(arrayList);
                    Log.d("LEE", "Fetch.STATUS_REMOVED " + i3 + " , " + j2 + " , " + this.totalBytesCount);
                    startDownloadRequestByTelegram();
                    ApplicationLoader.fetch.m12742b((C2747a) this);
                    str = "telegram on fail_2";
                    ApplicationLoader.fetch.m12740a(this.downloadId);
                    break;
                case 900:
                    return;
                case 901:
                    this.delegate.didChangedLoadProgress(this, Math.min(1.0f, ((float) j2) / ((float) this.totalBytesCount)));
                    return;
                case 903:
                    onFinishLoadingFile(true);
                    ApplicationLoader.fetch.m12742b((C2747a) this);
                    str = "mono_2";
                    ApplicationLoader.fetch.m12740a(this.downloadId);
                    break;
                default:
                    try {
                        ApplicationLoader.fetch.m12740a(this.downloadId);
                        return;
                    } catch (Exception e) {
                        startDownloadRequestByTelegram();
                        ApplicationLoader.fetch.m12742b((C2747a) this);
                        str = "telegram on fail_2_exc";
                        ApplicationLoader.fetch.m12740a(this.downloadId);
                        return;
                    }
            }
            if (i3 != -1) {
                startDownloadRequestByTelegram();
                ApplicationLoader.fetch.m12742b((C2747a) this);
                "telegram on fail_2_err_" + i3;
                ApplicationLoader.fetch.m12740a(this.downloadId);
            }
        }
    }

    public void setDelegate(FileLoadOperationDelegate fileLoadOperationDelegate) {
        this.delegate = fileLoadOperationDelegate;
    }

    public void setEncryptFile(boolean z) {
        this.encryptFile = z;
    }

    public void setForceRequest(boolean z) {
        this.isForceRequest = z;
    }

    public void setPaths(File file, File file2) {
        this.storePath = file;
        this.tempPath = file2;
    }

    public boolean start() {
        boolean z;
        Throwable e;
        if (this.state != 0) {
            return false;
        }
        if (this.location == null && this.webLocation == null) {
            onFail(true, 0);
            return false;
        }
        String str;
        String str2;
        String str3 = null;
        if (this.webLocation != null) {
            String MD5 = Utilities.MD5(this.webLocation.url);
            if (this.encryptFile) {
                str = MD5 + ".temp.enc";
                str2 = MD5 + "." + this.ext + ".enc";
                if (this.key != null) {
                    str3 = MD5 + ".iv.enc";
                }
            } else {
                str = MD5 + ".temp";
                str2 = MD5 + "." + this.ext;
                if (this.key != null) {
                    str3 = MD5 + ".iv";
                }
            }
        } else if (this.location.volume_id == 0 || this.location.local_id == 0) {
            if (this.datacenter_id == 0 || this.location.id == 0) {
                onFail(true, 0);
                return false;
            } else if (this.encryptFile) {
                str = this.datacenter_id + "_" + this.location.id + ".temp.enc";
                str2 = this.datacenter_id + "_" + this.location.id + this.ext + ".enc";
                if (this.key != null) {
                    str3 = this.datacenter_id + "_" + this.location.id + ".iv.enc";
                }
            } else {
                str = this.datacenter_id + "_" + this.location.id + ".temp";
                str2 = this.datacenter_id + "_" + this.location.id + this.ext;
                if (this.key != null) {
                    str3 = this.datacenter_id + "_" + this.location.id + ".iv";
                }
            }
        } else if (this.datacenter_id == Integer.MIN_VALUE || this.location.volume_id == -2147483648L || this.datacenter_id == 0) {
            onFail(true, 0);
            return false;
        } else if (this.encryptFile) {
            str = this.location.volume_id + "_" + this.location.local_id + ".temp.enc";
            str2 = this.location.volume_id + "_" + this.location.local_id + "." + this.ext + ".enc";
            if (this.key != null) {
                str3 = this.location.volume_id + "_" + this.location.local_id + ".iv.enc";
            }
        } else {
            str = this.location.volume_id + "_" + this.location.local_id + ".temp";
            str2 = this.location.volume_id + "_" + this.location.local_id + "." + this.ext;
            if (this.key != null) {
                str3 = this.location.volume_id + "_" + this.location.local_id + ".iv";
            }
        }
        this.currentDownloadChunkSize = this.totalBytesCount >= 1048576 ? 131072 : 32768;
        this.currentMaxDownloadRequests = this.totalBytesCount >= 1048576 ? 2 : 4;
        this.requestInfos = new ArrayList(this.currentMaxDownloadRequests);
        this.delayedRequestInfos = new ArrayList(this.currentMaxDownloadRequests - 1);
        this.state = 1;
        this.cacheFileFinal = new File(this.storePath, str2);
        if (!(!this.cacheFileFinal.exists() || this.totalBytesCount == 0 || ((long) this.totalBytesCount) == this.cacheFileFinal.length())) {
            this.cacheFileFinal.delete();
        }
        if (this.cacheFileFinal.exists()) {
            this.started = true;
            try {
                onFinishLoadingFile(false);
            } catch (Exception e2) {
                onFail(true, 0);
            }
        } else {
            int i;
            long length;
            this.cacheFileTemp = new File(this.tempPath, str);
            if (this.encryptFile) {
                File file = new File(FileLoader.getInternalCacheDir(), str2 + ".key");
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rws");
                    long length2 = file.length();
                    this.encryptKey = new byte[32];
                    this.encryptIv = new byte[16];
                    if (length2 <= 0 || length2 % 48 != 0) {
                        Utilities.random.nextBytes(this.encryptKey);
                        Utilities.random.nextBytes(this.encryptIv);
                        randomAccessFile.write(this.encryptKey);
                        randomAccessFile.write(this.encryptIv);
                        z = true;
                    } else {
                        randomAccessFile.read(this.encryptKey, 0, 32);
                        randomAccessFile.read(this.encryptIv, 0, 16);
                        z = false;
                    }
                    try {
                        randomAccessFile.getChannel().close();
                    } catch (Throwable e3) {
                        FileLog.m13728e(e3);
                    }
                    try {
                        randomAccessFile.close();
                    } catch (Exception e4) {
                        e3 = e4;
                        FileLog.m13728e(e3);
                        if (this.cacheFileTemp.exists()) {
                            if (z) {
                                this.downloadedBytes = (int) this.cacheFileTemp.length();
                                i = (this.downloadedBytes / this.currentDownloadChunkSize) * this.currentDownloadChunkSize;
                                this.downloadedBytes = i;
                                this.nextDownloadOffset = i;
                            } else {
                                this.cacheFileTemp.delete();
                            }
                        }
                        if (BuildVars.DEBUG_VERSION) {
                            FileLog.m13725d("start loading file to temp = " + this.cacheFileTemp + " final = " + this.cacheFileFinal);
                        }
                        if (str3 != null) {
                            this.cacheIvTemp = new File(this.tempPath, str3);
                            try {
                                this.fiv = new RandomAccessFile(this.cacheIvTemp, "rws");
                                if (!z) {
                                    length = this.cacheIvTemp.length();
                                    if (length > 0) {
                                    }
                                    this.downloadedBytes = 0;
                                }
                            } catch (Throwable e5) {
                                FileLog.m13728e(e5);
                                this.downloadedBytes = 0;
                            }
                        }
                        this.fileOutputStream = new RandomAccessFile(this.cacheFileTemp, "rws");
                        if (this.downloadedBytes != 0) {
                            this.fileOutputStream.seek((long) this.downloadedBytes);
                        }
                        if (this.fileOutputStream == null) {
                            this.started = true;
                            Utilities.stageQueue.postRunnable(new C30241());
                            return true;
                        }
                        onFail(true, 0);
                        return false;
                    }
                } catch (Throwable e6) {
                    e3 = e6;
                    z = false;
                    FileLog.m13728e(e3);
                    if (this.cacheFileTemp.exists()) {
                        if (z) {
                            this.cacheFileTemp.delete();
                        } else {
                            this.downloadedBytes = (int) this.cacheFileTemp.length();
                            i = (this.downloadedBytes / this.currentDownloadChunkSize) * this.currentDownloadChunkSize;
                            this.downloadedBytes = i;
                            this.nextDownloadOffset = i;
                        }
                    }
                    if (BuildVars.DEBUG_VERSION) {
                        FileLog.m13725d("start loading file to temp = " + this.cacheFileTemp + " final = " + this.cacheFileFinal);
                    }
                    if (str3 != null) {
                        this.cacheIvTemp = new File(this.tempPath, str3);
                        this.fiv = new RandomAccessFile(this.cacheIvTemp, "rws");
                        if (z) {
                            length = this.cacheIvTemp.length();
                            if (length > 0) {
                            }
                            this.downloadedBytes = 0;
                        }
                    }
                    this.fileOutputStream = new RandomAccessFile(this.cacheFileTemp, "rws");
                    if (this.downloadedBytes != 0) {
                        this.fileOutputStream.seek((long) this.downloadedBytes);
                    }
                    if (this.fileOutputStream == null) {
                        onFail(true, 0);
                        return false;
                    }
                    this.started = true;
                    Utilities.stageQueue.postRunnable(new C30241());
                    return true;
                }
            }
            z = false;
            if (this.cacheFileTemp.exists()) {
                if (z) {
                    this.cacheFileTemp.delete();
                } else {
                    this.downloadedBytes = (int) this.cacheFileTemp.length();
                    i = (this.downloadedBytes / this.currentDownloadChunkSize) * this.currentDownloadChunkSize;
                    this.downloadedBytes = i;
                    this.nextDownloadOffset = i;
                }
            }
            if (BuildVars.DEBUG_VERSION) {
                FileLog.m13725d("start loading file to temp = " + this.cacheFileTemp + " final = " + this.cacheFileFinal);
            }
            if (str3 != null) {
                this.cacheIvTemp = new File(this.tempPath, str3);
                this.fiv = new RandomAccessFile(this.cacheIvTemp, "rws");
                if (z) {
                    length = this.cacheIvTemp.length();
                    if (length > 0 || length % 32 != 0) {
                        this.downloadedBytes = 0;
                    } else {
                        this.fiv.read(this.iv, 0, 32);
                    }
                }
            }
            try {
                this.fileOutputStream = new RandomAccessFile(this.cacheFileTemp, "rws");
                if (this.downloadedBytes != 0) {
                    this.fileOutputStream.seek((long) this.downloadedBytes);
                }
            } catch (Throwable e52) {
                FileLog.m13728e(e52);
            }
            if (this.fileOutputStream == null) {
                onFail(true, 0);
                return false;
            }
            this.started = true;
            Utilities.stageQueue.postRunnable(new C30241());
        }
        return true;
    }

    public boolean wasStarted() {
        return this.started;
    }
}
