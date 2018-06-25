package org.telegram.messenger;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadRequest.Priority;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.customization.fetch.listener.FetchListener;
import org.telegram.customization.fetch.request.Request;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$InputFileLocation;
import org.telegram.tgnet.TLRPC$TL_cdnFileHash;
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
import org.telegram.ui.ChatActivity;
import utils.FreeDownload;
import utils.view.Constants;

public class FileLoadOperation implements FetchListener, DownloadStatusListenerV1 {
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
    private HashMap<Integer, TLRPC$TL_cdnFileHash> cdnHashes;
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
    private TLRPC$InputFileLocation location;
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
    class C13181 implements Runnable {
        C13181() {
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
    class C13192 implements Runnable {
        C13192() {
        }

        public void run() {
            if (FileLoadOperation.this.downloadId >= 0) {
                ApplicationLoader.fetch.removeRequest(FileLoadOperation.this.downloadId);
                FileLoadOperation.this.downloadId = -1;
            }
            if (FileLoadOperation.this.state != 3 && FileLoadOperation.this.state != 2) {
                if (FileLoadOperation.this.requestInfos != null) {
                    for (int a = 0; a < FileLoadOperation.this.requestInfos.size(); a++) {
                        RequestInfo requestInfo = (RequestInfo) FileLoadOperation.this.requestInfos.get(a);
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
    class C13214 implements RequestDelegate {
        C13214() {
        }

        public void run(TLObject response, TLRPC$TL_error error) {
            if (error != null) {
                FileLoadOperation.this.onFail(false, 0);
                return;
            }
            int a;
            FileLoadOperation.this.requestingCdnOffsets = false;
            TLRPC$Vector vector = (TLRPC$Vector) response;
            if (!vector.objects.isEmpty()) {
                if (FileLoadOperation.this.cdnHashes == null) {
                    FileLoadOperation.this.cdnHashes = new HashMap();
                }
                for (a = 0; a < vector.objects.size(); a++) {
                    TLRPC$TL_cdnFileHash hash = (TLRPC$TL_cdnFileHash) vector.objects.get(a);
                    FileLoadOperation.this.cdnHashes.put(Integer.valueOf(hash.offset), hash);
                }
            }
            for (a = 0; a < FileLoadOperation.this.delayedRequestInfos.size(); a++) {
                RequestInfo delayedRequestInfo = (RequestInfo) FileLoadOperation.this.delayedRequestInfos.get(a);
                if (FileLoadOperation.this.downloadedBytes == delayedRequestInfo.offset) {
                    FileLoadOperation.this.delayedRequestInfos.remove(a);
                    if (!FileLoadOperation.this.processRequestResult(delayedRequestInfo, null)) {
                        if (delayedRequestInfo.response != null) {
                            delayedRequestInfo.response.disableFree = false;
                            delayedRequestInfo.response.freeResources();
                            return;
                        } else if (delayedRequestInfo.responseWeb != null) {
                            delayedRequestInfo.responseWeb.disableFree = false;
                            delayedRequestInfo.responseWeb.freeResources();
                            return;
                        } else if (delayedRequestInfo.responseCdn != null) {
                            delayedRequestInfo.responseCdn.disableFree = false;
                            delayedRequestInfo.responseCdn.freeResources();
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
    class C13258 implements Runnable {
        C13258() {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r14 = this;
            r13 = 1;
            r5 = 0;
            r8 = org.telegram.messenger.FileLoadOperation.this;
            r4 = r8.monoUrl;
            r6 = new java.net.URL;	 Catch:{ Exception -> 0x00fc }
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00fc }
            r8 = r8.monoUrl;	 Catch:{ Exception -> 0x00fc }
            r6.<init>(r8);	 Catch:{ Exception -> 0x00fc }
            r0 = r6.openConnection();	 Catch:{ Exception -> 0x00ce }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x00ce }
            r8 = "Range";
            r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ce }
            r9.<init>();	 Catch:{ Exception -> 0x00ce }
            r10 = "bytes=";
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00ce }
            r10 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r10 = r10.downloadedBytes;	 Catch:{ Exception -> 0x00ce }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00ce }
            r10 = "-";
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00ce }
            r9 = r9.toString();	 Catch:{ Exception -> 0x00ce }
            r0.setRequestProperty(r8, r9);	 Catch:{ Exception -> 0x00ce }
            r8 = 1;
            r0.setDoInput(r8);	 Catch:{ Exception -> 0x00ce }
            r3 = new java.io.BufferedInputStream;	 Catch:{ Exception -> 0x00ce }
            r8 = r0.getInputStream();	 Catch:{ Exception -> 0x00ce }
            r3.<init>(r8);	 Catch:{ Exception -> 0x00ce }
            r8 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
            r1 = new byte[r8];	 Catch:{ Exception -> 0x00ce }
            r7 = 0;
        L_0x004e:
            r8 = 0;
            r9 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
            r7 = r3.read(r1, r8, r9);	 Catch:{ Exception -> 0x00ce }
            if (r7 <= 0) goto L_0x00d9;
        L_0x0057:
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r8 = r8.state;	 Catch:{ Exception -> 0x00ce }
            if (r8 != r13) goto L_0x0092;
        L_0x005f:
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r8 = r8.totalBytesCount;	 Catch:{ Exception -> 0x00ce }
            if (r8 <= 0) goto L_0x0075;
        L_0x0067:
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r8 = r8.nextDownloadOffset;	 Catch:{ Exception -> 0x00ce }
            r9 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r9 = r9.totalBytesCount;	 Catch:{ Exception -> 0x00ce }
            if (r8 >= r9) goto L_0x0092;
        L_0x0075:
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r8 = r8.requestInfos;	 Catch:{ Exception -> 0x00ce }
            r8 = r8.size();	 Catch:{ Exception -> 0x00ce }
            r9 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r9 = r9.delayedRequestInfos;	 Catch:{ Exception -> 0x00ce }
            r9 = r9.size();	 Catch:{ Exception -> 0x00ce }
            r8 = r8 + r9;
            r9 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r9 = r9.currentMaxDownloadRequests;	 Catch:{ Exception -> 0x00ce }
            if (r8 < r9) goto L_0x0097;
        L_0x0092:
            r3.close();	 Catch:{ Exception -> 0x00ce }
            r5 = r6;
        L_0x0096:
            return;
        L_0x0097:
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r8 = r8.fileOutputStream;	 Catch:{ Exception -> 0x00ce }
            r9 = 0;
            r8.write(r1, r9, r7);	 Catch:{ Exception -> 0x00ce }
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r9 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r9 = r9.downloadedBytes;	 Catch:{ Exception -> 0x00ce }
            r9 = r9 + r7;
            r8.downloadedBytes = r9;	 Catch:{ Exception -> 0x00ce }
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r8 = r8.delegate;	 Catch:{ Exception -> 0x00ce }
            r9 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r10 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r11 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r11 = r11.downloadedBytes;	 Catch:{ Exception -> 0x00ce }
            r11 = (float) r11;	 Catch:{ Exception -> 0x00ce }
            r12 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r12 = r12.totalBytesCount;	 Catch:{ Exception -> 0x00ce }
            r12 = (float) r12;	 Catch:{ Exception -> 0x00ce }
            r11 = r11 / r12;
            r10 = java.lang.Math.min(r10, r11);	 Catch:{ Exception -> 0x00ce }
            r8.didChangedLoadProgress(r9, r10);	 Catch:{ Exception -> 0x00ce }
            goto L_0x004e;
        L_0x00ce:
            r2 = move-exception;
            r5 = r6;
        L_0x00d0:
            r2.printStackTrace();
            r8 = org.telegram.messenger.FileLoadOperation.this;
            r8.startDownloadRequestByTelegram();
            goto L_0x0096;
        L_0x00d9:
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r8 = r8.downloadedBytes;	 Catch:{ Exception -> 0x00ce }
            r9 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r9 = r9.totalBytesCount;	 Catch:{ Exception -> 0x00ce }
            if (r8 != r9) goto L_0x00ef;
        L_0x00e7:
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r9 = 1;
            r8.onFinishLoadingFile(r9);	 Catch:{ Exception -> 0x00ce }
        L_0x00ed:
            r5 = r6;
            goto L_0x0096;
        L_0x00ef:
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r9 = "";
            r8.monoUrl = r9;	 Catch:{ Exception -> 0x00ce }
            r8 = org.telegram.messenger.FileLoadOperation.this;	 Catch:{ Exception -> 0x00ce }
            r8.startDownloadRequestAfterCheckMono();	 Catch:{ Exception -> 0x00ce }
            goto L_0x00ed;
        L_0x00fc:
            r2 = move-exception;
            goto L_0x00d0;
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

    public FileLoadOperation(TLRPC$FileLocation photoLocation, String extension, int size) {
        this.state = 0;
        this.monoUrl = null;
        this.requestSentToMono = false;
        this.cacheStat = "";
        this.eventData = "";
        this.downloadId = -1;
        this.downloadIdThin = -1;
        if (photoLocation instanceof TLRPC$TL_fileEncryptedLocation) {
            this.location = new TLRPC$TL_inputEncryptedFileLocation();
            this.location.id = photoLocation.volume_id;
            this.location.volume_id = photoLocation.volume_id;
            this.location.access_hash = photoLocation.secret;
            this.location.local_id = photoLocation.local_id;
            this.iv = new byte[32];
            System.arraycopy(photoLocation.iv, 0, this.iv, 0, this.iv.length);
            this.key = photoLocation.key;
            this.datacenter_id = photoLocation.dc_id;
        } else if (photoLocation instanceof TLRPC$TL_fileLocation) {
            this.location = new TLRPC$TL_inputFileLocation();
            this.location.volume_id = photoLocation.volume_id;
            this.location.secret = photoLocation.secret;
            this.location.local_id = photoLocation.local_id;
            this.datacenter_id = photoLocation.dc_id;
        }
        this.currentType = 16777216;
        this.totalBytesCount = size;
        if (extension == null) {
            extension = "jpg";
        }
        this.ext = extension;
    }

    public FileLoadOperation(TLRPC$TL_webDocument webDocument) {
        this.state = 0;
        this.monoUrl = null;
        this.requestSentToMono = false;
        this.cacheStat = "";
        this.eventData = "";
        this.downloadId = -1;
        this.downloadIdThin = -1;
        this.webLocation = new TLRPC$TL_inputWebFileLocation();
        this.webLocation.url = webDocument.url;
        this.webLocation.access_hash = webDocument.access_hash;
        this.totalBytesCount = webDocument.size;
        this.datacenter_id = webDocument.dc_id;
        String defaultExt = FileLoader.getExtensionByMime(webDocument.mime_type);
        if (webDocument.mime_type.startsWith("image/")) {
            this.currentType = 16777216;
        } else if (webDocument.mime_type.equals("audio/ogg")) {
            this.currentType = ConnectionsManager.FileTypeAudio;
        } else if (webDocument.mime_type.startsWith("video/")) {
            this.currentType = ConnectionsManager.FileTypeVideo;
        } else {
            this.currentType = ConnectionsManager.FileTypeFile;
        }
        this.ext = ImageLoader.getHttpUrlExtension(webDocument.url, defaultExt);
    }

    public FileLoadOperation(TLRPC$Document documentLocation) {
        int i = -1;
        this.state = 0;
        this.monoUrl = null;
        this.requestSentToMono = false;
        this.cacheStat = "";
        this.eventData = "";
        this.downloadId = -1;
        this.downloadIdThin = -1;
        try {
            String str;
            if (documentLocation instanceof TLRPC$TL_documentEncrypted) {
                this.location = new TLRPC$TL_inputEncryptedFileLocation();
                this.location.id = documentLocation.id;
                this.location.access_hash = documentLocation.access_hash;
                this.datacenter_id = documentLocation.dc_id;
                this.iv = new byte[32];
                System.arraycopy(documentLocation.iv, 0, this.iv, 0, this.iv.length);
                this.key = documentLocation.key;
            } else if ((documentLocation instanceof TLRPC$TL_document) || documentLocation.access_hash == -1) {
                this.location = new TLRPC$TL_inputDocumentFileLocation();
                this.location.id = documentLocation.id;
                this.location.access_hash = documentLocation.access_hash;
                this.datacenter_id = documentLocation.dc_id;
            }
            this.totalBytesCount = documentLocation.size;
            if (!(this.key == null || this.totalBytesCount % 16 == 0)) {
                this.bytesCountPadding = 16 - (this.totalBytesCount % 16);
                this.totalBytesCount += this.bytesCountPadding;
            }
            this.ext = FileLoader.getDocumentFileName(documentLocation);
            if (this.ext != null) {
                int idx = this.ext.lastIndexOf(46);
                if (idx != -1) {
                    this.ext = this.ext.substring(idx);
                    if ("audio/ogg".equals(documentLocation.mime_type)) {
                        this.currentType = ConnectionsManager.FileTypeAudio;
                    } else if (MimeTypes.VIDEO_MP4.equals(documentLocation.mime_type)) {
                        this.currentType = ConnectionsManager.FileTypeFile;
                    } else {
                        this.currentType = ConnectionsManager.FileTypeVideo;
                    }
                    if (this.ext.length() > 1) {
                    }
                    if (documentLocation.mime_type == null) {
                        str = documentLocation.mime_type;
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
                                this.ext = "";
                                return;
                        }
                    }
                    this.ext = "";
                    return;
                }
            }
            this.ext = "";
            if ("audio/ogg".equals(documentLocation.mime_type)) {
                this.currentType = ConnectionsManager.FileTypeAudio;
            } else if (MimeTypes.VIDEO_MP4.equals(documentLocation.mime_type)) {
                this.currentType = ConnectionsManager.FileTypeFile;
            } else {
                this.currentType = ConnectionsManager.FileTypeVideo;
            }
            if (this.ext.length() > 1) {
                if (documentLocation.mime_type == null) {
                    this.ext = "";
                    return;
                }
                str = documentLocation.mime_type;
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
                        this.ext = "";
                        return;
                }
            }
        } catch (Exception e) {
            FileLog.e(e);
            onFail(true, 0);
        }
    }

    public void setEncryptFile(boolean value) {
        this.encryptFile = value;
    }

    public void setForceRequest(boolean forceRequest) {
        this.isForceRequest = forceRequest;
    }

    public boolean isForceRequest() {
        return this.isForceRequest;
    }

    public void setPaths(File store, File temp) {
        this.storePath = store;
        this.tempPath = temp;
    }

    public boolean wasStarted() {
        return this.started;
    }

    public int getCurrentType() {
        return this.currentType;
    }

    public String getFileName() {
        if (this.location != null) {
            return this.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.local_id + "." + this.ext;
        }
        return Utilities.MD5(this.webLocation.url) + "." + this.ext;
    }

    public boolean start() {
        if (this.state != 0) {
            return false;
        }
        if (this.location == null && this.webLocation == null) {
            onFail(true, 0);
            return false;
        }
        String fileNameTemp;
        String fileNameFinal;
        String fileNameIv = null;
        if (this.webLocation != null) {
            String md5 = Utilities.MD5(this.webLocation.url);
            if (this.encryptFile) {
                fileNameTemp = md5 + ".temp.enc";
                fileNameFinal = md5 + "." + this.ext + ".enc";
                if (this.key != null) {
                    fileNameIv = md5 + ".iv.enc";
                }
            } else {
                fileNameTemp = md5 + ".temp";
                fileNameFinal = md5 + "." + this.ext;
                if (this.key != null) {
                    fileNameIv = md5 + ".iv";
                }
            }
        } else if (this.location.volume_id == 0 || this.location.local_id == 0) {
            if (this.datacenter_id == 0 || this.location.id == 0) {
                onFail(true, 0);
                return false;
            } else if (this.encryptFile) {
                fileNameTemp = this.datacenter_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.id + ".temp.enc";
                fileNameFinal = this.datacenter_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.id + this.ext + ".enc";
                if (this.key != null) {
                    fileNameIv = this.datacenter_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.id + ".iv.enc";
                }
            } else {
                fileNameTemp = this.datacenter_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.id + ".temp";
                fileNameFinal = this.datacenter_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.id + this.ext;
                if (this.key != null) {
                    fileNameIv = this.datacenter_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.id + ".iv";
                }
            }
        } else if (this.datacenter_id == Integer.MIN_VALUE || this.location.volume_id == -2147483648L || this.datacenter_id == 0) {
            onFail(true, 0);
            return false;
        } else if (this.encryptFile) {
            fileNameTemp = this.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.local_id + ".temp.enc";
            fileNameFinal = this.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.local_id + "." + this.ext + ".enc";
            if (this.key != null) {
                fileNameIv = this.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.local_id + ".iv.enc";
            }
        } else {
            fileNameTemp = this.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.local_id + ".temp";
            fileNameFinal = this.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.local_id + "." + this.ext;
            if (this.key != null) {
                fileNameIv = this.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.location.local_id + ".iv";
            }
        }
        this.currentDownloadChunkSize = this.totalBytesCount >= 1048576 ? 131072 : 32768;
        this.currentMaxDownloadRequests = this.totalBytesCount >= 1048576 ? 2 : 4;
        this.requestInfos = new ArrayList(this.currentMaxDownloadRequests);
        this.delayedRequestInfos = new ArrayList(this.currentMaxDownloadRequests - 1);
        this.state = 1;
        this.cacheFileFinal = new File(this.storePath, fileNameFinal);
        if (!(!this.cacheFileFinal.exists() || this.totalBytesCount == 0 || ((long) this.totalBytesCount) == this.cacheFileFinal.length())) {
            this.cacheFileFinal.delete();
        }
        if (this.cacheFileFinal.exists()) {
            this.started = true;
            try {
                onFinishLoadingFile(false);
            } catch (Exception e) {
                onFail(true, 0);
            }
        } else {
            long len;
            this.cacheFileTemp = new File(this.tempPath, fileNameTemp);
            boolean newKeyGenerated = false;
            if (this.encryptFile) {
                File keyFile = new File(FileLoader.getInternalCacheDir(), fileNameFinal + ".key");
                RandomAccessFile file = new RandomAccessFile(keyFile, "rws");
                len = keyFile.length();
                this.encryptKey = new byte[32];
                this.encryptIv = new byte[16];
                if (len <= 0 || len % 48 != 0) {
                    try {
                        Utilities.random.nextBytes(this.encryptKey);
                        Utilities.random.nextBytes(this.encryptIv);
                        file.write(this.encryptKey);
                        file.write(this.encryptIv);
                        newKeyGenerated = true;
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                } else {
                    file.read(this.encryptKey, 0, 32);
                    file.read(this.encryptIv, 0, 16);
                }
                try {
                    file.getChannel().close();
                } catch (Exception e22) {
                    FileLog.e(e22);
                }
                file.close();
            }
            if (this.cacheFileTemp.exists()) {
                if (newKeyGenerated) {
                    this.cacheFileTemp.delete();
                } else {
                    this.downloadedBytes = (int) this.cacheFileTemp.length();
                    int i = (this.downloadedBytes / this.currentDownloadChunkSize) * this.currentDownloadChunkSize;
                    this.downloadedBytes = i;
                    this.nextDownloadOffset = i;
                }
            }
            if (BuildVars.DEBUG_VERSION) {
                FileLog.d("start loading file to temp = " + this.cacheFileTemp + " final = " + this.cacheFileFinal);
            }
            if (fileNameIv != null) {
                this.cacheIvTemp = new File(this.tempPath, fileNameIv);
                try {
                    this.fiv = new RandomAccessFile(this.cacheIvTemp, "rws");
                    if (!newKeyGenerated) {
                        len = this.cacheIvTemp.length();
                        if (len <= 0 || len % 32 != 0) {
                            this.downloadedBytes = 0;
                        } else {
                            this.fiv.read(this.iv, 0, 32);
                        }
                    }
                } catch (Exception e222) {
                    FileLog.e(e222);
                    this.downloadedBytes = 0;
                }
            }
            try {
                this.fileOutputStream = new RandomAccessFile(this.cacheFileTemp, "rws");
                if (this.downloadedBytes != 0) {
                    this.fileOutputStream.seek((long) this.downloadedBytes);
                }
            } catch (Exception e2222) {
                FileLog.e(e2222);
            }
            if (this.fileOutputStream == null) {
                onFail(true, 0);
                return false;
            }
            this.started = true;
            Utilities.stageQueue.postRunnable(new C13181());
        }
        return true;
    }

    public void cancel() {
        Utilities.stageQueue.postRunnable(new C13192());
    }

    private void cleanup() {
        try {
            if (this.fileOutputStream != null) {
                try {
                    this.fileOutputStream.getChannel().close();
                } catch (Exception e) {
                    FileLog.e(e);
                }
                this.fileOutputStream.close();
                this.fileOutputStream = null;
            }
        } catch (Exception e2) {
            FileLog.e(e2);
        }
        try {
            if (this.fileReadStream != null) {
                try {
                    this.fileReadStream.getChannel().close();
                } catch (Exception e22) {
                    FileLog.e(e22);
                }
                this.fileReadStream.close();
                this.fileReadStream = null;
            }
        } catch (Exception e222) {
            FileLog.e(e222);
        }
        try {
            if (this.fiv != null) {
                this.fiv.close();
                this.fiv = null;
            }
        } catch (Exception e2222) {
            FileLog.e(e2222);
        }
        if (this.delayedRequestInfos != null) {
            for (int a = 0; a < this.delayedRequestInfos.size(); a++) {
                RequestInfo requestInfo = (RequestInfo) this.delayedRequestInfos.get(a);
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

    private void onFinishLoadingFile(final boolean increment) throws Exception {
        if (this.state == 1) {
            this.state = 3;
            cleanup();
            if (this.cacheIvTemp != null) {
                this.cacheIvTemp.delete();
                this.cacheIvTemp = null;
            }
            if (!(this.cacheFileTemp == null || this.cacheFileTemp.renameTo(this.cacheFileFinal))) {
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.e("unable to rename temp = " + this.cacheFileTemp + " to final = " + this.cacheFileFinal + " retry = " + this.renameRetryCount);
                }
                this.renameRetryCount++;
                if (this.renameRetryCount < 3) {
                    this.state = 1;
                    Utilities.stageQueue.postRunnable(new Runnable() {
                        public void run() {
                            try {
                                FileLoadOperation.this.onFinishLoadingFile(increment);
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
                FileLog.e("finished downloading file to " + this.cacheFileFinal);
            }
            this.delegate.didFinishLoadingFile(this, this.cacheFileFinal);
            if (!increment) {
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

    private void requestFileOffsets(int offset) {
        if (!this.requestingCdnOffsets) {
            this.requestingCdnOffsets = true;
            TLRPC$TL_upload_getCdnFileHashes req = new TLRPC$TL_upload_getCdnFileHashes();
            req.file_token = this.cdnToken;
            req.offset = offset;
            ConnectionsManager.getInstance().sendRequest(req, new C13214(), null, null, 0, this.datacenter_id, 1, true);
        }
    }

    private boolean processRequestResult(RequestInfo requestInfo, TLRPC$TL_error error) {
        if (this.state != 1) {
            return false;
        }
        this.requestInfos.remove(requestInfo);
        if (error == null) {
            try {
                if (this.downloadedBytes != requestInfo.offset) {
                    delayRequestInfo(requestInfo);
                    return false;
                }
                NativeByteBuffer bytes;
                if (requestInfo.response != null) {
                    bytes = requestInfo.response.bytes;
                } else if (requestInfo.responseWeb != null) {
                    bytes = requestInfo.responseWeb.bytes;
                } else if (requestInfo.responseCdn != null) {
                    bytes = requestInfo.responseCdn.bytes;
                } else {
                    bytes = null;
                }
                if (bytes == null || bytes.limit() == 0) {
                    onFinishLoadingFile(true);
                    return false;
                }
                int cdnCheckPart;
                int fileOffset;
                int offset;
                int currentBytesSize = bytes.limit();
                if (this.isCdn) {
                    cdnCheckPart = (this.downloadedBytes + currentBytesSize) / 131072;
                    fileOffset = (cdnCheckPart - (this.lastCheckedCdnPart != cdnCheckPart ? 1 : 0)) * 131072;
                    if ((this.cdnHashes != null ? (TLRPC$TL_cdnFileHash) this.cdnHashes.get(Integer.valueOf(fileOffset)) : null) == null) {
                        delayRequestInfo(requestInfo);
                        requestFileOffsets(fileOffset);
                        return true;
                    }
                }
                if (requestInfo.responseCdn != null) {
                    offset = requestInfo.offset / 16;
                    this.cdnIv[15] = (byte) (offset & 255);
                    this.cdnIv[14] = (byte) ((offset >> 8) & 255);
                    this.cdnIv[13] = (byte) ((offset >> 16) & 255);
                    this.cdnIv[12] = (byte) ((offset >> 24) & 255);
                    Utilities.aesCtrDecryption(bytes.buffer, this.cdnKey, this.cdnIv, 0, bytes.limit());
                }
                this.downloadedBytes += currentBytesSize;
                boolean finishedDownloading = currentBytesSize != this.currentDownloadChunkSize || ((this.totalBytesCount == this.downloadedBytes || this.downloadedBytes % this.currentDownloadChunkSize != 0) && (this.totalBytesCount <= 0 || this.totalBytesCount <= this.downloadedBytes));
                if (this.key != null) {
                    Utilities.aesIgeEncryption(bytes.buffer, this.key, this.iv, false, true, 0, bytes.limit());
                    if (finishedDownloading && this.bytesCountPadding != 0) {
                        bytes.limit(bytes.limit() - this.bytesCountPadding);
                    }
                }
                if (this.encryptFile) {
                    offset = requestInfo.offset / 16;
                    this.encryptIv[15] = (byte) (offset & 255);
                    this.encryptIv[14] = (byte) ((offset >> 8) & 255);
                    this.encryptIv[13] = (byte) ((offset >> 16) & 255);
                    this.encryptIv[12] = (byte) ((offset >> 24) & 255);
                    Utilities.aesCtrDecryption(bytes.buffer, this.encryptKey, this.encryptIv, 0, bytes.limit());
                }
                this.fileOutputStream.getChannel().write(bytes.buffer);
                if (this.isCdn) {
                    cdnCheckPart = this.downloadedBytes / 131072;
                    if (cdnCheckPart != this.lastCheckedCdnPart || finishedDownloading) {
                        int count;
                        this.fileOutputStream.getFD().sync();
                        fileOffset = (cdnCheckPart - (this.lastCheckedCdnPart != cdnCheckPart ? 1 : 0)) * 131072;
                        TLRPC$TL_cdnFileHash hash = (TLRPC$TL_cdnFileHash) this.cdnHashes.get(Integer.valueOf(fileOffset));
                        if (this.fileReadStream == null) {
                            this.cdnCheckBytes = new byte[131072];
                            this.fileReadStream = new RandomAccessFile(this.cacheFileTemp, "r");
                            if (fileOffset != 0) {
                                this.fileReadStream.seek((long) fileOffset);
                            }
                        }
                        if (this.lastCheckedCdnPart != cdnCheckPart) {
                            count = 131072;
                        } else {
                            count = this.downloadedBytes - (131072 * cdnCheckPart);
                        }
                        this.fileReadStream.readFully(this.cdnCheckBytes, 0, count);
                        if (Arrays.equals(Utilities.computeSHA256(this.cdnCheckBytes, 0, count), hash.hash)) {
                            this.lastCheckedCdnPart = cdnCheckPart;
                        } else {
                            if (this.location != null) {
                                FileLog.e("invalid cdn hash " + this.location + " id = " + this.location.id + " local_id = " + this.location.local_id + " access_hash = " + this.location.access_hash + " volume_id = " + this.location.volume_id + " secret = " + this.location.secret);
                            } else if (this.webLocation != null) {
                                FileLog.e("invalid cdn hash  " + this.webLocation + " id = " + this.webLocation.url + " access_hash = " + this.webLocation.access_hash);
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
                int a = 0;
                while (a < this.delayedRequestInfos.size()) {
                    RequestInfo delayedRequestInfo = (RequestInfo) this.delayedRequestInfos.get(a);
                    if (this.downloadedBytes == delayedRequestInfo.offset) {
                        this.delayedRequestInfos.remove(a);
                        if (!processRequestResult(delayedRequestInfo, null)) {
                            if (delayedRequestInfo.response != null) {
                                delayedRequestInfo.response.disableFree = false;
                                delayedRequestInfo.response.freeResources();
                            } else if (delayedRequestInfo.responseWeb != null) {
                                delayedRequestInfo.responseWeb.disableFree = false;
                                delayedRequestInfo.responseWeb.freeResources();
                            } else if (delayedRequestInfo.responseCdn != null) {
                                delayedRequestInfo.responseCdn.disableFree = false;
                                delayedRequestInfo.responseCdn.freeResources();
                            }
                        }
                        if (finishedDownloading) {
                            startDownloadRequest();
                        } else {
                            onFinishLoadingFile(true);
                        }
                    } else {
                        a++;
                    }
                }
                if (finishedDownloading) {
                    startDownloadRequest();
                } else {
                    onFinishLoadingFile(true);
                }
            } catch (Exception e) {
                onFail(false, 0);
                FileLog.e(e);
            }
        } else if (error.text.contains("FILE_MIGRATE_")) {
            Integer val;
            Scanner scanner = new Scanner(error.text.replace("FILE_MIGRATE_", ""));
            scanner.useDelimiter("");
            try {
                val = Integer.valueOf(scanner.nextInt());
            } catch (Exception e2) {
                val = null;
            }
            if (val == null) {
                onFail(false, 0);
            } else {
                this.datacenter_id = val.intValue();
                this.nextDownloadOffset = 0;
                startDownloadRequest();
            }
        } else if (error.text.contains("OFFSET_INVALID")) {
            if (this.downloadedBytes % this.currentDownloadChunkSize == 0) {
                try {
                    onFinishLoadingFile(true);
                } catch (Exception e3) {
                    FileLog.e(e3);
                    onFail(false, 0);
                }
            } else {
                onFail(false, 0);
            }
        } else if (error.text.contains("RETRY_LIMIT")) {
            onFail(false, 2);
        } else {
            if (this.location != null) {
                FileLog.e("" + this.location + " id = " + this.location.id + " local_id = " + this.location.local_id + " access_hash = " + this.location.access_hash + " volume_id = " + this.location.volume_id + " secret = " + this.location.secret);
            } else if (this.webLocation != null) {
                FileLog.e("" + this.webLocation + " id = " + this.webLocation.url + " access_hash = " + this.webLocation.access_hash);
            }
            onFail(false, 0);
        }
        return false;
    }

    private void onFail(boolean thread, final int reason) {
        cleanup();
        if (this.downloadId >= 0) {
            ApplicationLoader.fetch.removeRequest(this.downloadId);
            this.downloadId = -1;
        }
        if (this.downloadIdThin >= 0) {
            ApplicationLoader.thinDl.cancel(this.downloadIdThin);
            this.downloadIdThin = -1;
        }
        this.state = 2;
        if (thread) {
            Utilities.stageQueue.postRunnable(new Runnable() {
                public void run() {
                    FileLoadOperation.this.delegate.didFailedLoadingFile(FileLoadOperation.this, reason);
                }
            });
        } else {
            this.delegate.didFailedLoadingFile(this, reason);
        }
    }

    private void clearOperaion(RequestInfo currentInfo) {
        int a;
        int minOffset = Integer.MAX_VALUE;
        for (a = 0; a < this.requestInfos.size(); a++) {
            RequestInfo info = (RequestInfo) this.requestInfos.get(a);
            minOffset = Math.min(info.offset, minOffset);
            if (!(currentInfo == info || info.requestToken == 0)) {
                ConnectionsManager.getInstance().cancelRequest(info.requestToken, true);
            }
        }
        this.requestInfos.clear();
        for (a = 0; a < this.delayedRequestInfos.size(); a++) {
            info = (RequestInfo) this.delayedRequestInfos.get(a);
            if (info.response != null) {
                info.response.disableFree = false;
                info.response.freeResources();
            } else if (info.responseWeb != null) {
                info.responseWeb.disableFree = false;
                info.responseWeb.freeResources();
            } else if (info.responseCdn != null) {
                info.responseCdn.disableFree = false;
                info.responseCdn.freeResources();
            }
            minOffset = Math.min(info.offset, minOffset);
        }
        this.delayedRequestInfos.clear();
        this.requestsCount = 0;
        this.nextDownloadOffset = minOffset;
    }

    private void startDownloadRequestByTelegram() {
        if (this.state != 1) {
            return;
        }
        if ((this.totalBytesCount <= 0 || this.nextDownloadOffset < this.totalBytesCount) && this.requestInfos.size() + this.delayedRequestInfos.size() < this.currentMaxDownloadRequests) {
            int count = 1;
            if (this.totalBytesCount > 0) {
                count = Math.max(0, this.currentMaxDownloadRequests - this.requestInfos.size());
            }
            int a = 0;
            while (a < count) {
                if (this.totalBytesCount <= 0 || this.nextDownloadOffset < this.totalBytesCount) {
                    int offset;
                    TLObject request;
                    int i;
                    boolean isLast = this.totalBytesCount <= 0 || a == count - 1 || (this.totalBytesCount > 0 && this.nextDownloadOffset + this.currentDownloadChunkSize >= this.totalBytesCount);
                    int connectionType = this.requestsCount % 2 == 0 ? 2 : ConnectionsManager.ConnectionTypeDownload2;
                    int flags = (this.isForceRequest ? 32 : 0) | 2;
                    TLObject req;
                    if (this.isCdn) {
                        req = new TLRPC$TL_upload_getCdnFile();
                        req.file_token = this.cdnToken;
                        offset = this.nextDownloadOffset;
                        req.offset = offset;
                        req.limit = this.currentDownloadChunkSize;
                        request = req;
                        flags |= 1;
                    } else if (this.webLocation != null) {
                        req = new TLRPC$TL_upload_getWebFile();
                        req.location = this.webLocation;
                        offset = this.nextDownloadOffset;
                        req.offset = offset;
                        req.limit = this.currentDownloadChunkSize;
                        request = req;
                    } else {
                        req = new TLRPC$TL_upload_getFile();
                        req.location = this.location;
                        offset = this.nextDownloadOffset;
                        req.offset = offset;
                        req.limit = this.currentDownloadChunkSize;
                        request = req;
                    }
                    this.nextDownloadOffset += this.currentDownloadChunkSize;
                    final RequestInfo requestInfo = new RequestInfo();
                    this.requestInfos.add(requestInfo);
                    requestInfo.offset = offset;
                    ConnectionsManager instance = ConnectionsManager.getInstance();
                    RequestDelegate c13246 = new RequestDelegate() {

                        /* renamed from: org.telegram.messenger.FileLoadOperation$6$1 */
                        class C13231 implements RequestDelegate {
                            C13231() {
                            }

                            public void run(TLObject response, TLRPC$TL_error error) {
                                FileLoadOperation.this.reuploadingCdn = false;
                                if (error == null) {
                                    TLRPC$Vector vector = (TLRPC$Vector) response;
                                    if (!vector.objects.isEmpty()) {
                                        if (FileLoadOperation.this.cdnHashes == null) {
                                            FileLoadOperation.this.cdnHashes = new HashMap();
                                        }
                                        for (int a = 0; a < vector.objects.size(); a++) {
                                            TLRPC$TL_cdnFileHash hash = (TLRPC$TL_cdnFileHash) vector.objects.get(a);
                                            FileLoadOperation.this.cdnHashes.put(Integer.valueOf(hash.offset), hash);
                                        }
                                    }
                                    FileLoadOperation.this.startDownloadRequest();
                                } else if (error.text.equals("FILE_TOKEN_INVALID") || error.text.equals("REQUEST_TOKEN_INVALID")) {
                                    FileLoadOperation.this.isCdn = false;
                                    FileLoadOperation.this.clearOperaion(requestInfo);
                                    FileLoadOperation.this.startDownloadRequest();
                                } else {
                                    FileLoadOperation.this.onFail(false, 0);
                                }
                            }
                        }

                        public void run(TLObject response, TLRPC$TL_error error) {
                            if (!FileLoadOperation.this.requestInfos.contains(requestInfo)) {
                                return;
                            }
                            if (error != null && (request instanceof TLRPC$TL_upload_getCdnFile) && error.text.equals("FILE_TOKEN_INVALID")) {
                                FileLoadOperation.this.isCdn = false;
                                FileLoadOperation.this.clearOperaion(requestInfo);
                                FileLoadOperation.this.startDownloadRequest();
                            } else if (response instanceof TLRPC$TL_upload_fileCdnRedirect) {
                                TLRPC$TL_upload_fileCdnRedirect res = (TLRPC$TL_upload_fileCdnRedirect) response;
                                if (!res.cdn_file_hashes.isEmpty()) {
                                    if (FileLoadOperation.this.cdnHashes == null) {
                                        FileLoadOperation.this.cdnHashes = new HashMap();
                                    }
                                    for (int a = 0; a < res.cdn_file_hashes.size(); a++) {
                                        TLRPC$TL_cdnFileHash hash = (TLRPC$TL_cdnFileHash) res.cdn_file_hashes.get(a);
                                        FileLoadOperation.this.cdnHashes.put(Integer.valueOf(hash.offset), hash);
                                    }
                                }
                                if (res.encryption_iv == null || res.encryption_key == null || res.encryption_iv.length != 16 || res.encryption_key.length != 32) {
                                    error = new TLRPC$TL_error();
                                    error.text = "bad redirect response";
                                    error.code = ChatActivity.scheduleDownloads;
                                    FileLoadOperation.this.processRequestResult(requestInfo, error);
                                    return;
                                }
                                FileLoadOperation.this.isCdn = true;
                                FileLoadOperation.this.cdnDatacenterId = res.dc_id;
                                FileLoadOperation.this.cdnIv = res.encryption_iv;
                                FileLoadOperation.this.cdnKey = res.encryption_key;
                                FileLoadOperation.this.cdnToken = res.file_token;
                                FileLoadOperation.this.clearOperaion(requestInfo);
                                FileLoadOperation.this.startDownloadRequest();
                            } else if (!(response instanceof TLRPC$TL_upload_cdnFileReuploadNeeded)) {
                                if (response instanceof TLRPC$TL_upload_file) {
                                    requestInfo.response = (TLRPC$TL_upload_file) response;
                                } else if (response instanceof TLRPC$TL_upload_webFile) {
                                    requestInfo.responseWeb = (TLRPC$TL_upload_webFile) response;
                                } else {
                                    requestInfo.responseCdn = (TLRPC$TL_upload_cdnFile) response;
                                }
                                if (response != null) {
                                    if (FileLoadOperation.this.currentType == ConnectionsManager.FileTypeAudio) {
                                        StatsController.getInstance().incrementReceivedBytesCount(response.networkType, 3, (long) (response.getObjectSize() + 4));
                                    } else if (FileLoadOperation.this.currentType == ConnectionsManager.FileTypeVideo) {
                                        StatsController.getInstance().incrementReceivedBytesCount(response.networkType, 2, (long) (response.getObjectSize() + 4));
                                    } else if (FileLoadOperation.this.currentType == 16777216) {
                                        StatsController.getInstance().incrementReceivedBytesCount(response.networkType, 4, (long) (response.getObjectSize() + 4));
                                    } else if (FileLoadOperation.this.currentType == ConnectionsManager.FileTypeFile) {
                                        StatsController.getInstance().incrementReceivedBytesCount(response.networkType, 5, (long) (response.getObjectSize() + 4));
                                    }
                                }
                                FileLoadOperation.this.processRequestResult(requestInfo, error);
                            } else if (!FileLoadOperation.this.reuploadingCdn) {
                                FileLoadOperation.this.clearOperaion(requestInfo);
                                FileLoadOperation.this.reuploadingCdn = true;
                                TLRPC$TL_upload_cdnFileReuploadNeeded res2 = (TLRPC$TL_upload_cdnFileReuploadNeeded) response;
                                TLRPC$TL_upload_reuploadCdnFile req = new TLRPC$TL_upload_reuploadCdnFile();
                                req.file_token = FileLoadOperation.this.cdnToken;
                                req.request_token = res2.request_token;
                                ConnectionsManager.getInstance().sendRequest(req, new C13231(), null, null, 0, FileLoadOperation.this.datacenter_id, 1, true);
                            }
                        }
                    };
                    if (this.isCdn) {
                        i = this.cdnDatacenterId;
                    } else {
                        i = this.datacenter_id;
                    }
                    requestInfo.requestToken = instance.sendRequest(request, c13246, null, null, flags, i, connectionType, isLast);
                    this.requestsCount++;
                    a++;
                } else {
                    return;
                }
            }
        }
    }

    private void startDownloadRequest() {
        if (ConnectionsManager.isNetworkOnline()) {
            DocAvailableInfo cacheDoc = new DocAvailableInfo(this.location.id, this.location.local_id, this.location.volume_id, 0, false);
            this.cacheStat = FreeDownload.getCacheHumanName(FreeDownload.isFree(cacheDoc));
            this.eventData = this.location.id + "," + this.location.local_id + "," + this.location.volume_id + " , " + System.currentTimeMillis();
            if (this.monoUrl != null || this.storePath.equals(this.tempPath)) {
                startDownloadRequestAfterCheckMono();
            } else if (this.requestSentToMono) {
                startDownloadRequestAfterCheckMono();
            } else {
                this.requestSentToMono = true;
                this.monoUrl = FreeDownload.getFreeUrl(cacheDoc);
                startDownloadRequestAfterCheckMono();
            }
        }
    }

    private void startDownloadRequestAfterCheckMonoOld() {
        if (TextUtils.isEmpty(this.monoUrl)) {
            startDownloadRequestByTelegram();
        } else if (this.state == 1 && this.requestInfos.size() + this.delayedRequestInfos.size() < this.currentMaxDownloadRequests) {
            new Thread(new C13258()).start();
        }
    }

    private void logToMono(String logCat) {
        if (!TextUtils.isEmpty(logCat)) {
            Answers.getInstance().logCustom((CustomEvent) new CustomEvent(Constants.TRACKER_NETWORK_ERROR).putCustomAttribute("Download File", logCat + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.monoUrl));
        }
    }

    public void onDownloadComplete(DownloadRequest downloadRequest) {
        try {
            onFinishLoadingFile(true);
        } catch (Exception e) {
        }
    }

    public void onDownloadFailed(DownloadRequest downloadRequest, int i, String s) {
        startDownloadRequestByTelegram();
        logToMono(String.valueOf(i));
    }

    public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {
        this.delegate.didChangedLoadProgress(this, Math.min(1.0f, ((float) downloadedBytes) / ((float) this.totalBytesCount)));
    }

    private void startDownloadRequestAfterCheckMonoThin() {
        if (TextUtils.isEmpty(this.monoUrl)) {
            startDownloadRequestByTelegram();
        } else if (this.state != 1 || this.requestInfos.size() + this.delayedRequestInfos.size() >= this.currentMaxDownloadRequests) {
            startDownloadRequestByTelegram();
        } else {
            Uri downloadUri = Uri.parse(this.monoUrl);
            this.downloadIdThin = ApplicationLoader.thinDl.add(new DownloadRequest(downloadUri).setRetryPolicy(new DefaultRetryPolicy()).setDestinationURI(Uri.parse(this.cacheFileTemp.getPath())).setPriority(Priority.HIGH).setDownloadContext(ApplicationLoader.applicationContext).setStatusListener(this));
        }
    }

    public void onUpdate(long id, int status, int progress, long downloadedBytes, long fileSize, int error) {
        if (id == this.downloadId) {
            String logCat;
            switch (status) {
                case FetchConst.STATUS_NOT_QUEUED /*-900*/:
                case FetchConst.STATUS_ERROR /*904*/:
                    ArrayList<DocAvailableInfo> tempDocs = new ArrayList();
                    DocAvailableInfo tempDoc = new DocAvailableInfo(this.location.id, this.location.local_id, this.location.volume_id, 0, false);
                    tempDoc.exists = false;
                    tempDocs.add(tempDoc);
                    FreeDownload.addDocs(tempDocs);
                    Log.d("LEE", "Fetch.STATUS_REMOVED " + error + " , " + downloadedBytes + " , " + this.totalBytesCount);
                    startDownloadRequestByTelegram();
                    ApplicationLoader.fetch.removeFetchListener(this);
                    logCat = Constants.CAT_TELEGRAM_ON_FAIL;
                    ApplicationLoader.fetch.removeRequest(this.downloadId);
                    break;
                case FetchConst.STATUS_QUEUED /*900*/:
                    return;
                case FetchConst.STATUS_DOWNLOADING /*901*/:
                    this.delegate.didChangedLoadProgress(this, Math.min(1.0f, ((float) downloadedBytes) / ((float) this.totalBytesCount)));
                    return;
                case FetchConst.STATUS_DONE /*903*/:
                    onFinishLoadingFile(true);
                    ApplicationLoader.fetch.removeFetchListener(this);
                    logCat = Constants.CAT_MONO;
                    ApplicationLoader.fetch.removeRequest(this.downloadId);
                    break;
                default:
                    try {
                        ApplicationLoader.fetch.removeRequest(this.downloadId);
                        return;
                    } catch (Exception e) {
                        startDownloadRequestByTelegram();
                        ApplicationLoader.fetch.removeFetchListener(this);
                        logCat = "telegram on fail_2_exc";
                        ApplicationLoader.fetch.removeRequest(this.downloadId);
                        return;
                    }
            }
            if (error != -1) {
                startDownloadRequestByTelegram();
                ApplicationLoader.fetch.removeFetchListener(this);
                logCat = "telegram on fail_2_err_" + error;
                ApplicationLoader.fetch.removeRequest(this.downloadId);
            }
        }
    }

    private void startDownloadRequestAfterCheckMono() {
        if (TextUtils.isEmpty(this.monoUrl)) {
            startDownloadRequestByTelegram();
        } else if (this.state != 1 || ((this.totalBytesCount > 0 && this.nextDownloadOffset >= this.totalBytesCount) || this.requestInfos.size() + this.delayedRequestInfos.size() >= this.currentMaxDownloadRequests)) {
            startDownloadRequestByTelegram();
        } else {
            Request request = new Request(this.monoUrl, this.cacheFileTemp.getPath());
            request.setPriority(FetchConst.PRIORITY_HIGH);
            this.downloadId = ApplicationLoader.fetch.enqueue(request, (long) this.totalBytesCount);
            if (this.downloadId != -1) {
                ApplicationLoader.fetch.addFetchListener(this);
            } else {
                startDownloadRequestByTelegram();
            }
        }
    }

    public void setDelegate(FileLoadOperationDelegate delegate) {
        this.delegate = delegate;
    }
}
