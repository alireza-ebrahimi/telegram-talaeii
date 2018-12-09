package org.telegram.messenger;

import android.webkit.MimeTypeMap;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import org.telegram.messenger.FileLoadOperation.FileLoadOperationDelegate;
import org.telegram.messenger.FileUploadOperation.FileUploadOperationDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_documentAttributeFilename;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaInvoice;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_photoCachedSize;
import org.telegram.tgnet.TLRPC$TL_webDocument;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputEncryptedFile;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.PhotoSize;

public class FileLoader {
    private static volatile FileLoader Instance = null;
    public static final int MEDIA_DIR_AUDIO = 1;
    public static final int MEDIA_DIR_CACHE = 4;
    public static final int MEDIA_DIR_DOCUMENT = 3;
    public static final int MEDIA_DIR_IMAGE = 0;
    public static final int MEDIA_DIR_VIDEO = 2;
    private LinkedList<FileLoadOperation> audioLoadOperationQueue = new LinkedList();
    private int currentAudioLoadOperationsCount = 0;
    private int currentLoadOperationsCount = 0;
    private int currentPhotoLoadOperationsCount = 0;
    private int currentUploadOperationsCount = 0;
    private int currentUploadSmallOperationsCount = 0;
    private ArrayList<FileLoaderDelegateCustomForDownloader> customDelegates = null;
    private FileLoaderDelegate delegate = null;
    private ArrayList<FileLoaderDelegate> delegates = null;
    private volatile DispatchQueue fileLoaderQueue = new DispatchQueue("fileUploadQueue");
    private ConcurrentHashMap<String, FileLoadOperation> loadOperationPaths = new ConcurrentHashMap();
    private LinkedList<FileLoadOperation> loadOperationQueue = new LinkedList();
    private HashMap<Integer, File> mediaDirs = null;
    private LinkedList<FileLoadOperation> photoLoadOperationQueue = new LinkedList();
    private ConcurrentHashMap<String, FileUploadOperation> uploadOperationPaths = new ConcurrentHashMap();
    private ConcurrentHashMap<String, FileUploadOperation> uploadOperationPathsEnc = new ConcurrentHashMap();
    private LinkedList<FileUploadOperation> uploadOperationQueue = new LinkedList();
    private HashMap<String, Long> uploadSizes = new HashMap();
    private LinkedList<FileUploadOperation> uploadSmallOperationQueue = new LinkedList();

    public interface FileLoaderDelegate {
        void fileDidFailedLoad(String str, int i);

        void fileDidFailedUpload(String str, boolean z);

        void fileDidLoaded(String str, File file, int i);

        void fileDidUploaded(String str, InputFile inputFile, InputEncryptedFile inputEncryptedFile, byte[] bArr, byte[] bArr2, long j);

        void fileLoadProgressChanged(String str, float f);

        void fileUploadProgressChanged(String str, float f, boolean z);
    }

    public interface FileLoaderDelegateCustomForDownloader {
        void fileDidFailedLoad(Document document, FileLocation fileLocation);

        void fileDidLoaded(Document document, FileLocation fileLocation);

        void fileLoadProgressChanged(Document document, FileLocation fileLocation);
    }

    private void cancelLoadFile(Document document, TLRPC$TL_webDocument tLRPC$TL_webDocument, FileLocation fileLocation, String str) {
        if (fileLocation != null || document != null) {
            final FileLocation fileLocation2 = fileLocation;
            final String str2 = str;
            final Document document2 = document;
            final TLRPC$TL_webDocument tLRPC$TL_webDocument2 = tLRPC$TL_webDocument;
            this.fileLoaderQueue.postRunnable(new Runnable() {
                public void run() {
                    Object obj = null;
                    if (fileLocation2 != null) {
                        obj = FileLoader.getAttachFileName(fileLocation2, str2);
                    } else if (document2 != null) {
                        obj = FileLoader.getAttachFileName(document2);
                    } else if (tLRPC$TL_webDocument2 != null) {
                        obj = FileLoader.getAttachFileName(tLRPC$TL_webDocument2);
                    }
                    if (obj != null) {
                        FileLoadOperation fileLoadOperation = (FileLoadOperation) FileLoader.this.loadOperationPaths.remove(obj);
                        if (fileLoadOperation != null) {
                            if (MessageObject.isVoiceDocument(document2) || MessageObject.isVoiceWebDocument(tLRPC$TL_webDocument2)) {
                                if (!FileLoader.this.audioLoadOperationQueue.remove(fileLoadOperation)) {
                                    FileLoader.this.currentAudioLoadOperationsCount = FileLoader.this.currentAudioLoadOperationsCount - 1;
                                }
                            } else if (fileLocation2 != null) {
                                if (!FileLoader.this.photoLoadOperationQueue.remove(fileLoadOperation) || MessageObject.isImageWebDocument(tLRPC$TL_webDocument2)) {
                                    FileLoader.this.currentPhotoLoadOperationsCount = FileLoader.this.currentPhotoLoadOperationsCount - 1;
                                }
                            } else if (!FileLoader.this.loadOperationQueue.remove(fileLoadOperation)) {
                                FileLoader.this.currentLoadOperationsCount = FileLoader.this.currentLoadOperationsCount - 1;
                            }
                            fileLoadOperation.cancel();
                        }
                    }
                }
            });
        }
    }

    private void checkDownloadQueue(Document document, TLRPC$TL_webDocument tLRPC$TL_webDocument, FileLocation fileLocation, String str) {
        final String str2 = str;
        final Document document2 = document;
        final TLRPC$TL_webDocument tLRPC$TL_webDocument2 = tLRPC$TL_webDocument;
        final FileLocation fileLocation2 = fileLocation;
        this.fileLoaderQueue.postRunnable(new Runnable() {
            public void run() {
                FileLoadOperation fileLoadOperation = (FileLoadOperation) FileLoader.this.loadOperationPaths.remove(str2);
                if (MessageObject.isVoiceDocument(document2) || MessageObject.isVoiceWebDocument(tLRPC$TL_webDocument2)) {
                    if (fileLoadOperation != null) {
                        if (fileLoadOperation.wasStarted()) {
                            FileLoader.this.currentAudioLoadOperationsCount = FileLoader.this.currentAudioLoadOperationsCount - 1;
                        } else {
                            FileLoader.this.audioLoadOperationQueue.remove(fileLoadOperation);
                        }
                    }
                    while (!FileLoader.this.audioLoadOperationQueue.isEmpty()) {
                        if (FileLoader.this.currentAudioLoadOperationsCount < (((FileLoadOperation) FileLoader.this.audioLoadOperationQueue.get(0)).isForceRequest() ? 3 : 1)) {
                            fileLoadOperation = (FileLoadOperation) FileLoader.this.audioLoadOperationQueue.poll();
                            if (fileLoadOperation != null && fileLoadOperation.start()) {
                                FileLoader.this.currentAudioLoadOperationsCount = FileLoader.this.currentAudioLoadOperationsCount + 1;
                            }
                        } else {
                            return;
                        }
                    }
                } else if (fileLocation2 != null || MessageObject.isImageWebDocument(tLRPC$TL_webDocument2)) {
                    if (fileLoadOperation != null) {
                        if (fileLoadOperation.wasStarted()) {
                            FileLoader.this.currentPhotoLoadOperationsCount = FileLoader.this.currentPhotoLoadOperationsCount - 1;
                        } else {
                            FileLoader.this.photoLoadOperationQueue.remove(fileLoadOperation);
                        }
                    }
                    while (!FileLoader.this.photoLoadOperationQueue.isEmpty()) {
                        if (FileLoader.this.currentPhotoLoadOperationsCount < (((FileLoadOperation) FileLoader.this.photoLoadOperationQueue.get(0)).isForceRequest() ? 3 : 1)) {
                            fileLoadOperation = (FileLoadOperation) FileLoader.this.photoLoadOperationQueue.poll();
                            if (fileLoadOperation != null && fileLoadOperation.start()) {
                                FileLoader.this.currentPhotoLoadOperationsCount = FileLoader.this.currentPhotoLoadOperationsCount + 1;
                            }
                        } else {
                            return;
                        }
                    }
                } else {
                    if (fileLoadOperation != null) {
                        if (fileLoadOperation.wasStarted()) {
                            FileLoader.this.currentLoadOperationsCount = FileLoader.this.currentLoadOperationsCount - 1;
                        } else {
                            FileLoader.this.loadOperationQueue.remove(fileLoadOperation);
                        }
                    }
                    while (!FileLoader.this.loadOperationQueue.isEmpty()) {
                        if (FileLoader.this.currentLoadOperationsCount < (((FileLoadOperation) FileLoader.this.loadOperationQueue.get(0)).isForceRequest() ? 3 : 1)) {
                            fileLoadOperation = (FileLoadOperation) FileLoader.this.loadOperationQueue.poll();
                            if (fileLoadOperation != null && fileLoadOperation.start()) {
                                FileLoader.this.currentLoadOperationsCount = FileLoader.this.currentLoadOperationsCount + 1;
                            }
                        } else {
                            return;
                        }
                    }
                }
            }
        });
    }

    public static String getAttachFileName(TLObject tLObject) {
        return getAttachFileName(tLObject, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getAttachFileName(org.telegram.tgnet.TLObject r4, java.lang.String r5) {
        /*
        r1 = -1;
        r2 = 1;
        r0 = r4 instanceof org.telegram.tgnet.TLRPC.Document;
        if (r0 == 0) goto L_0x0104;
    L_0x0006:
        r4 = (org.telegram.tgnet.TLRPC.Document) r4;
        r0 = 0;
        if (r0 != 0) goto L_0x001c;
    L_0x000b:
        r0 = getDocumentFileName(r4);
        if (r0 == 0) goto L_0x0019;
    L_0x0011:
        r3 = 46;
        r3 = r0.lastIndexOf(r3);
        if (r3 != r1) goto L_0x0061;
    L_0x0019:
        r0 = "";
    L_0x001c:
        r3 = r0.length();
        if (r3 > r2) goto L_0x0036;
    L_0x0022:
        r0 = r4.mime_type;
        if (r0 == 0) goto L_0x0084;
    L_0x0026:
        r0 = r4.mime_type;
        r3 = r0.hashCode();
        switch(r3) {
            case 187091926: goto L_0x0071;
            case 1331848029: goto L_0x0066;
            default: goto L_0x002f;
        };
    L_0x002f:
        r0 = r1;
    L_0x0030:
        switch(r0) {
            case 0: goto L_0x007c;
            case 1: goto L_0x0080;
            default: goto L_0x0033;
        };
    L_0x0033:
        r0 = "";
    L_0x0036:
        r1 = r4.version;
        if (r1 != 0) goto L_0x00a5;
    L_0x003a:
        r1 = r0.length();
        if (r1 <= r2) goto L_0x0088;
    L_0x0040:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = r4.dc_id;
        r1 = r1.append(r2);
        r2 = "_";
        r1 = r1.append(r2);
        r2 = r4.id;
        r1 = r1.append(r2);
        r0 = r1.append(r0);
        r0 = r0.toString();
    L_0x0060:
        return r0;
    L_0x0061:
        r0 = r0.substring(r3);
        goto L_0x001c;
    L_0x0066:
        r3 = "video/mp4";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x002f;
    L_0x006f:
        r0 = 0;
        goto L_0x0030;
    L_0x0071:
        r3 = "audio/ogg";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x002f;
    L_0x007a:
        r0 = r2;
        goto L_0x0030;
    L_0x007c:
        r0 = ".mp4";
        goto L_0x0036;
    L_0x0080:
        r0 = ".ogg";
        goto L_0x0036;
    L_0x0084:
        r0 = "";
        goto L_0x0036;
    L_0x0088:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = r4.dc_id;
        r0 = r0.append(r1);
        r1 = "_";
        r0 = r0.append(r1);
        r2 = r4.id;
        r0 = r0.append(r2);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x00a5:
        r1 = r0.length();
        if (r1 <= r2) goto L_0x00d9;
    L_0x00ab:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = r4.dc_id;
        r1 = r1.append(r2);
        r2 = "_";
        r1 = r1.append(r2);
        r2 = r4.id;
        r1 = r1.append(r2);
        r2 = "_";
        r1 = r1.append(r2);
        r2 = r4.version;
        r1 = r1.append(r2);
        r0 = r1.append(r0);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x00d9:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = r4.dc_id;
        r0 = r0.append(r1);
        r1 = "_";
        r0 = r0.append(r1);
        r2 = r4.id;
        r0 = r0.append(r2);
        r1 = "_";
        r0 = r0.append(r1);
        r1 = r4.version;
        r0 = r0.append(r1);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x0104:
        r0 = r4 instanceof org.telegram.tgnet.TLRPC$TL_webDocument;
        if (r0 == 0) goto L_0x0136;
    L_0x0108:
        r4 = (org.telegram.tgnet.TLRPC$TL_webDocument) r4;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = r4.url;
        r1 = org.telegram.messenger.Utilities.MD5(r1);
        r0 = r0.append(r1);
        r1 = ".";
        r0 = r0.append(r1);
        r1 = r4.url;
        r2 = r4.mime_type;
        r2 = getExtensionByMime(r2);
        r1 = org.telegram.messenger.ImageLoader.getHttpUrlExtension(r1, r2);
        r0 = r0.append(r1);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x0136:
        r0 = r4 instanceof org.telegram.tgnet.TLRPC.PhotoSize;
        if (r0 == 0) goto L_0x017e;
    L_0x013a:
        r4 = (org.telegram.tgnet.TLRPC.PhotoSize) r4;
        r0 = r4.location;
        if (r0 == 0) goto L_0x0146;
    L_0x0140:
        r0 = r4.location;
        r0 = r0 instanceof org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
        if (r0 == 0) goto L_0x014b;
    L_0x0146:
        r0 = "";
        goto L_0x0060;
    L_0x014b:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = r4.location;
        r2 = r1.volume_id;
        r0 = r0.append(r2);
        r1 = "_";
        r0 = r0.append(r1);
        r1 = r4.location;
        r1 = r1.local_id;
        r0 = r0.append(r1);
        r1 = ".";
        r0 = r0.append(r1);
        if (r5 == 0) goto L_0x017a;
    L_0x0170:
        r0 = r0.append(r5);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x017a:
        r5 = "jpg";
        goto L_0x0170;
    L_0x017e:
        r0 = r4 instanceof org.telegram.tgnet.TLRPC.FileLocation;
        if (r0 == 0) goto L_0x01bc;
    L_0x0182:
        r0 = r4 instanceof org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
        if (r0 == 0) goto L_0x018b;
    L_0x0186:
        r0 = "";
        goto L_0x0060;
    L_0x018b:
        r4 = (org.telegram.tgnet.TLRPC.FileLocation) r4;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r2 = r4.volume_id;
        r0 = r0.append(r2);
        r1 = "_";
        r0 = r0.append(r1);
        r1 = r4.local_id;
        r0 = r0.append(r1);
        r1 = ".";
        r0 = r0.append(r1);
        if (r5 == 0) goto L_0x01b8;
    L_0x01ae:
        r0 = r0.append(r5);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x01b8:
        r5 = "jpg";
        goto L_0x01ae;
    L_0x01bc:
        r0 = "";
        goto L_0x0060;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileLoader.getAttachFileName(org.telegram.tgnet.TLObject, java.lang.String):java.lang.String");
    }

    public static PhotoSize getClosestPhotoSizeWithSize(ArrayList<PhotoSize> arrayList, int i) {
        return getClosestPhotoSizeWithSize(arrayList, i, false);
    }

    public static PhotoSize getClosestPhotoSizeWithSize(ArrayList<PhotoSize> arrayList, int i, boolean z) {
        PhotoSize photoSize = null;
        if (!(arrayList == null || arrayList.isEmpty())) {
            int i2 = 0;
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                PhotoSize photoSize2 = (PhotoSize) arrayList.get(i3);
                if (photoSize2 != null) {
                    int i4;
                    if (z) {
                        int i5;
                        i4 = photoSize2.f10146h >= photoSize2.f10147w ? photoSize2.f10147w : photoSize2.f10146h;
                        if (photoSize == null || ((i > 100 && photoSize.location != null && photoSize.location.dc_id == Integer.MIN_VALUE) || (photoSize2 instanceof TLRPC$TL_photoCachedSize) || (i > i2 && i2 < i4))) {
                            i5 = i4;
                        } else {
                            photoSize2 = photoSize;
                            i5 = i2;
                        }
                        i2 = i5;
                        photoSize = photoSize2;
                    } else {
                        i4 = photoSize2.f10147w >= photoSize2.f10146h ? photoSize2.f10147w : photoSize2.f10146h;
                        if (photoSize == null || ((i > 100 && photoSize.location != null && photoSize.location.dc_id == Integer.MIN_VALUE) || (photoSize2 instanceof TLRPC$TL_photoCachedSize) || (i4 <= i && i2 < i4))) {
                            photoSize = photoSize2;
                            i2 = i4;
                        }
                    }
                }
            }
        }
        return photoSize;
    }

    public static String getDocumentExtension(Document document) {
        String documentFileName = getDocumentFileName(document);
        int lastIndexOf = documentFileName.lastIndexOf(46);
        String str = null;
        if (lastIndexOf != -1) {
            str = documentFileName.substring(lastIndexOf + 1);
        }
        if (str == null || str.length() == 0) {
            str = document.mime_type;
        }
        if (str == null) {
            str = TtmlNode.ANONYMOUS_REGION_ID;
        }
        return str.toUpperCase();
    }

    public static String getDocumentFileName(Document document) {
        String str;
        String str2 = null;
        if (document == null) {
            str = null;
        } else if (document.file_name != null) {
            str = document.file_name;
        } else {
            int i = 0;
            while (i < document.attributes.size()) {
                DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i);
                i++;
                str2 = documentAttribute instanceof TLRPC$TL_documentAttributeFilename ? documentAttribute.file_name : str2;
            }
            str = str2;
        }
        if (str != null) {
            str = str.replaceAll("[\u0001-\u001f<>:\"/\\\\|?*]+", TtmlNode.ANONYMOUS_REGION_ID).trim();
        }
        return str != null ? str : TtmlNode.ANONYMOUS_REGION_ID;
    }

    public static String getExtensionByMime(String str) {
        int indexOf = str.indexOf(47);
        return indexOf != -1 ? str.substring(indexOf + 1) : TtmlNode.ANONYMOUS_REGION_ID;
    }

    public static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(46) + 1);
        } catch (Exception e) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    public static FileLoader getInstance() {
        FileLoader fileLoader = Instance;
        if (fileLoader == null) {
            synchronized (FileLoader.class) {
                fileLoader = Instance;
                if (fileLoader == null) {
                    fileLoader = new FileLoader();
                    Instance = fileLoader;
                }
            }
        }
        return fileLoader;
    }

    public static File getInternalCacheDir() {
        return ApplicationLoader.applicationContext.getCacheDir();
    }

    public static String getMessageFileName(Message message) {
        if (message == null) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        ArrayList arrayList;
        TLObject closestPhotoSizeWithSize;
        if (message instanceof TLRPC$TL_messageService) {
            if (message.action.photo != null) {
                arrayList = message.action.photo.sizes;
                if (arrayList.size() > 0) {
                    closestPhotoSizeWithSize = getClosestPhotoSizeWithSize(arrayList, AndroidUtilities.getPhotoSize());
                    if (closestPhotoSizeWithSize != null) {
                        return getAttachFileName(closestPhotoSizeWithSize);
                    }
                }
            }
        } else if (message.media instanceof TLRPC$TL_messageMediaDocument) {
            return getAttachFileName(message.media.document);
        } else {
            if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
                arrayList = message.media.photo.sizes;
                if (arrayList.size() > 0) {
                    closestPhotoSizeWithSize = getClosestPhotoSizeWithSize(arrayList, AndroidUtilities.getPhotoSize());
                    if (closestPhotoSizeWithSize != null) {
                        return getAttachFileName(closestPhotoSizeWithSize);
                    }
                }
            } else if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
                if (message.media.webpage.photo != null) {
                    arrayList = message.media.webpage.photo.sizes;
                    if (arrayList.size() > 0) {
                        closestPhotoSizeWithSize = getClosestPhotoSizeWithSize(arrayList, AndroidUtilities.getPhotoSize());
                        if (closestPhotoSizeWithSize != null) {
                            return getAttachFileName(closestPhotoSizeWithSize);
                        }
                    }
                } else if (message.media.webpage.document != null) {
                    return getAttachFileName(message.media.webpage.document);
                } else {
                    if (message.media instanceof TLRPC$TL_messageMediaInvoice) {
                        return getAttachFileName(((TLRPC$TL_messageMediaInvoice) message.media).photo);
                    }
                }
            } else if (message.media instanceof TLRPC$TL_messageMediaInvoice) {
                TLRPC$TL_webDocument tLRPC$TL_webDocument = ((TLRPC$TL_messageMediaInvoice) message.media).photo;
                if (tLRPC$TL_webDocument != null) {
                    return Utilities.MD5(tLRPC$TL_webDocument.url) + "." + ImageLoader.getHttpUrlExtension(tLRPC$TL_webDocument.url, getExtensionByMime(tLRPC$TL_webDocument.mime_type));
                }
            }
        }
        return TtmlNode.ANONYMOUS_REGION_ID;
    }

    public static String getMimeType(String str) {
        return MimeTypeMap.getFileExtensionFromUrl(str).contentEquals("mp3") ? "audio/mp3" : "audio/ogg";
    }

    public static File getPathToAttach(TLObject tLObject) {
        return getPathToAttach(tLObject, null, false);
    }

    public static File getPathToAttach(TLObject tLObject, String str, boolean z) {
        File directory;
        if (z) {
            directory = getInstance().getDirectory(4);
        } else if (tLObject instanceof Document) {
            Document document = (Document) tLObject;
            r0 = document.key != null ? getInstance().getDirectory(4) : MessageObject.isVoiceDocument(document) ? getInstance().getDirectory(1) : MessageObject.isVideoDocument(document) ? getInstance().getDirectory(2) : getInstance().getDirectory(3);
            directory = r0;
        } else if (tLObject instanceof PhotoSize) {
            PhotoSize photoSize = (PhotoSize) tLObject;
            r0 = (photoSize.location == null || photoSize.location.key != null || ((photoSize.location.volume_id == -2147483648L && photoSize.location.local_id < 0) || photoSize.size < 0)) ? getInstance().getDirectory(4) : getInstance().getDirectory(0);
            directory = r0;
        } else if (tLObject instanceof FileLocation) {
            FileLocation fileLocation = (FileLocation) tLObject;
            r0 = (fileLocation.key != null || (fileLocation.volume_id == -2147483648L && fileLocation.local_id < 0)) ? getInstance().getDirectory(4) : getInstance().getDirectory(0);
            directory = r0;
        } else if (tLObject instanceof TLRPC$TL_webDocument) {
            TLRPC$TL_webDocument tLRPC$TL_webDocument = (TLRPC$TL_webDocument) tLObject;
            directory = tLRPC$TL_webDocument.mime_type.startsWith("image/") ? getInstance().getDirectory(0) : tLRPC$TL_webDocument.mime_type.startsWith("audio/") ? getInstance().getDirectory(1) : tLRPC$TL_webDocument.mime_type.startsWith("video/") ? getInstance().getDirectory(2) : getInstance().getDirectory(3);
        } else {
            directory = null;
        }
        return directory == null ? new File(TtmlNode.ANONYMOUS_REGION_ID) : new File(directory, getAttachFileName(tLObject, str));
    }

    public static File getPathToAttach(TLObject tLObject, boolean z) {
        return getPathToAttach(tLObject, null, z);
    }

    public static File getPathToMessage(Message message) {
        boolean z = false;
        boolean z2 = true;
        if (message == null) {
            return new File(TtmlNode.ANONYMOUS_REGION_ID);
        }
        ArrayList arrayList;
        TLObject closestPhotoSizeWithSize;
        if (message instanceof TLRPC$TL_messageService) {
            if (message.action.photo != null) {
                arrayList = message.action.photo.sizes;
                if (arrayList.size() > 0) {
                    closestPhotoSizeWithSize = getClosestPhotoSizeWithSize(arrayList, AndroidUtilities.getPhotoSize());
                    if (closestPhotoSizeWithSize != null) {
                        return getPathToAttach(closestPhotoSizeWithSize);
                    }
                }
            }
        } else if (message.media instanceof TLRPC$TL_messageMediaDocument) {
            r2 = message.media.document;
            if (message.media.ttl_seconds != 0) {
                z = true;
            }
            return getPathToAttach(r2, z);
        } else if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
            ArrayList arrayList2 = message.media.photo.sizes;
            if (arrayList2.size() > 0) {
                r2 = getClosestPhotoSizeWithSize(arrayList2, AndroidUtilities.getPhotoSize());
                if (r2 != null) {
                    if (message.media.ttl_seconds == 0) {
                        z2 = false;
                    }
                    return getPathToAttach(r2, z2);
                }
            }
        } else if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
            if (message.media.webpage.document != null) {
                return getPathToAttach(message.media.webpage.document);
            }
            if (message.media.webpage.photo != null) {
                arrayList = message.media.webpage.photo.sizes;
                if (arrayList.size() > 0) {
                    closestPhotoSizeWithSize = getClosestPhotoSizeWithSize(arrayList, AndroidUtilities.getPhotoSize());
                    if (closestPhotoSizeWithSize != null) {
                        return getPathToAttach(closestPhotoSizeWithSize);
                    }
                }
            }
        } else if (message.media instanceof TLRPC$TL_messageMediaInvoice) {
            return getPathToAttach(((TLRPC$TL_messageMediaInvoice) message.media).photo, true);
        }
        return new File(TtmlNode.ANONYMOUS_REGION_ID);
    }

    private void loadFile(Document document, TLRPC$TL_webDocument tLRPC$TL_webDocument, FileLocation fileLocation, String str, int i, boolean z, int i2) {
        final FileLocation fileLocation2 = fileLocation;
        final String str2 = str;
        final Document document2 = document;
        final TLRPC$TL_webDocument tLRPC$TL_webDocument2 = tLRPC$TL_webDocument;
        final boolean z2 = z;
        final int i3 = i;
        final int i4 = i2;
        this.fileLoaderQueue.postRunnable(new Runnable() {
            public void run() {
                boolean z = true;
                String attachFileName = fileLocation2 != null ? FileLoader.getAttachFileName(fileLocation2, str2) : document2 != null ? FileLoader.getAttachFileName(document2) : tLRPC$TL_webDocument2 != null ? FileLoader.getAttachFileName(tLRPC$TL_webDocument2) : null;
                if (attachFileName != null && !attachFileName.contains("-2147483648")) {
                    FileLoadOperation fileLoadOperation = (FileLoadOperation) FileLoader.this.loadOperationPaths.get(attachFileName);
                    if (fileLoadOperation == null) {
                        FileLoadOperation fileLoadOperation2;
                        int i;
                        File directory;
                        File directory2 = FileLoader.this.getDirectory(4);
                        if (fileLocation2 != null) {
                            fileLoadOperation2 = new FileLoadOperation(fileLocation2, str2, i3);
                            i = 0;
                        } else if (document2 != null) {
                            fileLoadOperation = new FileLoadOperation(document2);
                            if (MessageObject.isVoiceDocument(document2)) {
                                fileLoadOperation2 = fileLoadOperation;
                                i = 1;
                            } else if (MessageObject.isVideoDocument(document2)) {
                                fileLoadOperation2 = fileLoadOperation;
                                i = 2;
                            } else {
                                fileLoadOperation2 = fileLoadOperation;
                                i = 3;
                            }
                        } else if (tLRPC$TL_webDocument2 != null) {
                            fileLoadOperation = new FileLoadOperation(tLRPC$TL_webDocument2);
                            if (MessageObject.isVoiceWebDocument(tLRPC$TL_webDocument2)) {
                                fileLoadOperation2 = fileLoadOperation;
                                i = 1;
                            } else if (MessageObject.isVideoWebDocument(tLRPC$TL_webDocument2)) {
                                fileLoadOperation2 = fileLoadOperation;
                                i = 2;
                            } else if (MessageObject.isImageWebDocument(tLRPC$TL_webDocument2)) {
                                fileLoadOperation2 = fileLoadOperation;
                                i = 0;
                            } else {
                                fileLoadOperation2 = fileLoadOperation;
                                i = 3;
                            }
                        } else {
                            fileLoadOperation2 = fileLoadOperation;
                            i = 4;
                        }
                        if (i4 == 0) {
                            directory = FileLoader.this.getDirectory(i);
                        } else {
                            if (i4 == 2) {
                                fileLoadOperation2.setEncryptFile(true);
                            }
                            directory = directory2;
                        }
                        fileLoadOperation2.setPaths(directory, directory2);
                        fileLoadOperation2.setDelegate(new FileLoadOperationDelegate() {
                            public void didChangedLoadProgress(FileLoadOperation fileLoadOperation, float f) {
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileLoadProgressChanged(attachFileName, f);
                                }
                            }

                            public void didFailedLoadingFile(FileLoadOperation fileLoadOperation, int i) {
                                FileLoader.this.checkDownloadQueue(document2, tLRPC$TL_webDocument2, fileLocation2, attachFileName);
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileDidFailedLoad(attachFileName, i);
                                }
                            }

                            public void didFinishLoadingFile(FileLoadOperation fileLoadOperation, File file) {
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileDidLoaded(attachFileName, file, i);
                                }
                                FileLoader.this.checkDownloadQueue(document2, tLRPC$TL_webDocument2, fileLocation2, attachFileName);
                            }
                        });
                        fileLoadOperation2.setDelegate(new FileLoadOperationDelegate() {
                            public void didChangedLoadProgress(FileLoadOperation fileLoadOperation, float f) {
                                Iterator it;
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileLoadProgressChanged(attachFileName, f);
                                }
                                if (FileLoader.this.delegates != null) {
                                    try {
                                        it = FileLoader.this.delegates.iterator();
                                        while (it.hasNext()) {
                                            try {
                                                ((FileLoaderDelegate) it.next()).fileLoadProgressChanged(attachFileName, f);
                                            } catch (Exception e) {
                                            }
                                        }
                                    } catch (Exception e2) {
                                    }
                                }
                                synchronized (this) {
                                    if (FileLoader.this.customDelegates != null) {
                                        it = FileLoader.this.customDelegates.iterator();
                                        while (it.hasNext()) {
                                            ((FileLoaderDelegateCustomForDownloader) it.next()).fileLoadProgressChanged(document2, fileLocation2);
                                        }
                                    }
                                }
                            }

                            public void didFailedLoadingFile(FileLoadOperation fileLoadOperation, int i) {
                                Iterator it;
                                FileLoader.this.checkDownloadQueue(document2, tLRPC$TL_webDocument2, fileLocation2, attachFileName);
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileDidFailedLoad(attachFileName, i);
                                }
                                if (FileLoader.this.delegates != null) {
                                    try {
                                        it = FileLoader.this.delegates.iterator();
                                        while (it.hasNext()) {
                                            try {
                                                ((FileLoaderDelegate) it.next()).fileDidFailedLoad(attachFileName, i);
                                            } catch (Exception e) {
                                            }
                                        }
                                    } catch (Exception e2) {
                                    }
                                }
                                synchronized (this) {
                                    if (FileLoader.this.customDelegates != null) {
                                        it = FileLoader.this.customDelegates.iterator();
                                        while (it.hasNext()) {
                                            ((FileLoaderDelegateCustomForDownloader) it.next()).fileDidFailedLoad(document2, fileLocation2);
                                        }
                                    }
                                }
                            }

                            public void didFinishLoadingFile(FileLoadOperation fileLoadOperation, File file) {
                                Iterator it;
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileDidLoaded(attachFileName, file, i);
                                }
                                if (FileLoader.this.delegates != null) {
                                    try {
                                        it = FileLoader.this.delegates.iterator();
                                        while (it.hasNext()) {
                                            try {
                                                ((FileLoaderDelegate) it.next()).fileDidLoaded(attachFileName, file, i);
                                            } catch (Exception e) {
                                            }
                                        }
                                    } catch (Exception e2) {
                                    }
                                }
                                synchronized (this) {
                                    if (FileLoader.this.customDelegates != null) {
                                        it = FileLoader.this.customDelegates.iterator();
                                        while (it.hasNext()) {
                                            ((FileLoaderDelegateCustomForDownloader) it.next()).fileDidLoaded(document2, fileLocation2);
                                        }
                                    }
                                }
                                FileLoader.this.checkDownloadQueue(document2, tLRPC$TL_webDocument2, fileLocation2, attachFileName);
                            }
                        });
                        FileLoader.this.loadOperationPaths.put(attachFileName, fileLoadOperation2);
                        if (!z2) {
                            z = true;
                        }
                        if (i == 1) {
                            if (FileLoader.this.currentAudioLoadOperationsCount < z) {
                                if (fileLoadOperation2.start()) {
                                    FileLoader.this.currentAudioLoadOperationsCount = FileLoader.this.currentAudioLoadOperationsCount + 1;
                                }
                            } else if (z2) {
                                FileLoader.this.audioLoadOperationQueue.add(0, fileLoadOperation2);
                            } else {
                                FileLoader.this.audioLoadOperationQueue.add(fileLoadOperation2);
                            }
                        } else if (fileLocation2 != null) {
                            if (FileLoader.this.currentPhotoLoadOperationsCount < z) {
                                if (fileLoadOperation2.start()) {
                                    FileLoader.this.currentPhotoLoadOperationsCount = FileLoader.this.currentPhotoLoadOperationsCount + 1;
                                }
                            } else if (z2) {
                                FileLoader.this.photoLoadOperationQueue.add(0, fileLoadOperation2);
                            } else {
                                FileLoader.this.photoLoadOperationQueue.add(fileLoadOperation2);
                            }
                        } else if (FileLoader.this.currentLoadOperationsCount < z) {
                            if (fileLoadOperation2.start()) {
                                FileLoader.this.currentLoadOperationsCount = FileLoader.this.currentLoadOperationsCount + 1;
                            }
                        } else if (z2) {
                            FileLoader.this.loadOperationQueue.add(0, fileLoadOperation2);
                        } else {
                            FileLoader.this.loadOperationQueue.add(fileLoadOperation2);
                        }
                    } else if (z2) {
                        fileLoadOperation.setForceRequest(true);
                        LinkedList access$1000 = (MessageObject.isVoiceDocument(document2) || MessageObject.isVoiceWebDocument(tLRPC$TL_webDocument2)) ? FileLoader.this.audioLoadOperationQueue : (fileLocation2 != null || MessageObject.isImageWebDocument(tLRPC$TL_webDocument2)) ? FileLoader.this.photoLoadOperationQueue : FileLoader.this.loadOperationQueue;
                        if (access$1000 != null) {
                            int indexOf = access$1000.indexOf(fileLoadOperation);
                            if (indexOf > 0) {
                                access$1000.remove(indexOf);
                                access$1000.add(0, fileLoadOperation);
                            }
                        }
                    }
                }
            }
        });
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String slsGetAttachFileName(org.telegram.tgnet.TLObject r4, java.lang.String r5) {
        /*
        r1 = -1;
        r2 = 1;
        r0 = r4 instanceof org.telegram.tgnet.TLRPC.Document;
        if (r0 == 0) goto L_0x0123;
    L_0x0006:
        r4 = (org.telegram.tgnet.TLRPC.Document) r4;
        r0 = 0;
        if (r0 != 0) goto L_0x001c;
    L_0x000b:
        r0 = getDocumentFileName(r4);
        if (r0 == 0) goto L_0x0019;
    L_0x0011:
        r3 = 46;
        r3 = r0.lastIndexOf(r3);
        if (r3 != r1) goto L_0x0061;
    L_0x0019:
        r0 = "";
    L_0x001c:
        r3 = r0.length();
        if (r3 > r2) goto L_0x0036;
    L_0x0022:
        r0 = r4.mime_type;
        if (r0 == 0) goto L_0x00a2;
    L_0x0026:
        r0 = r4.mime_type;
        r3 = r0.hashCode();
        switch(r3) {
            case 187090231: goto L_0x007c;
            case 187091926: goto L_0x0071;
            case 1331848029: goto L_0x0066;
            case 1504831518: goto L_0x0087;
            default: goto L_0x002f;
        };
    L_0x002f:
        r0 = r1;
    L_0x0030:
        switch(r0) {
            case 0: goto L_0x0092;
            case 1: goto L_0x0096;
            case 2: goto L_0x009a;
            case 3: goto L_0x009e;
            default: goto L_0x0033;
        };
    L_0x0033:
        r0 = "";
    L_0x0036:
        r1 = r4.version;
        if (r1 != 0) goto L_0x00c3;
    L_0x003a:
        r1 = r0.length();
        if (r1 <= r2) goto L_0x00a6;
    L_0x0040:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = r4.dc_id;
        r1 = r1.append(r2);
        r2 = "_";
        r1 = r1.append(r2);
        r2 = r4.id;
        r1 = r1.append(r2);
        r0 = r1.append(r0);
        r0 = r0.toString();
    L_0x0060:
        return r0;
    L_0x0061:
        r0 = r0.substring(r3);
        goto L_0x001c;
    L_0x0066:
        r3 = "video/mp4";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x002f;
    L_0x006f:
        r0 = 0;
        goto L_0x0030;
    L_0x0071:
        r3 = "audio/ogg";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x002f;
    L_0x007a:
        r0 = r2;
        goto L_0x0030;
    L_0x007c:
        r3 = "audio/mp3";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x002f;
    L_0x0085:
        r0 = 2;
        goto L_0x0030;
    L_0x0087:
        r3 = "audio/mpeg";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x002f;
    L_0x0090:
        r0 = 3;
        goto L_0x0030;
    L_0x0092:
        r0 = ".mp4";
        goto L_0x0036;
    L_0x0096:
        r0 = ".ogg";
        goto L_0x0036;
    L_0x009a:
        r0 = ".mp3";
        goto L_0x0036;
    L_0x009e:
        r0 = ".mp3";
        goto L_0x0036;
    L_0x00a2:
        r0 = "";
        goto L_0x0036;
    L_0x00a6:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = r4.dc_id;
        r0 = r0.append(r1);
        r1 = "_";
        r0 = r0.append(r1);
        r2 = r4.id;
        r0 = r0.append(r2);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x00c3:
        r1 = r0.length();
        if (r1 <= r2) goto L_0x00f8;
    L_0x00c9:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = r4.dc_id;
        r1 = r1.append(r2);
        r2 = "_";
        r1 = r1.append(r2);
        r2 = r4.id;
        r1 = r1.append(r2);
        r2 = "_";
        r1 = r1.append(r2);
        r2 = r4.version;
        r1 = r1.append(r2);
        r0 = r1.append(r0);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x00f8:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = r4.dc_id;
        r0 = r0.append(r1);
        r1 = "_";
        r0 = r0.append(r1);
        r2 = r4.id;
        r0 = r0.append(r2);
        r1 = "_";
        r0 = r0.append(r1);
        r1 = r4.version;
        r0 = r0.append(r1);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x0123:
        r0 = r4 instanceof org.telegram.tgnet.TLRPC$TL_webDocument;
        if (r0 == 0) goto L_0x0155;
    L_0x0127:
        r4 = (org.telegram.tgnet.TLRPC$TL_webDocument) r4;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = r4.url;
        r1 = org.telegram.messenger.Utilities.MD5(r1);
        r0 = r0.append(r1);
        r1 = ".";
        r0 = r0.append(r1);
        r1 = r4.url;
        r2 = r4.mime_type;
        r2 = getExtensionByMime(r2);
        r1 = org.telegram.messenger.ImageLoader.getHttpUrlExtension(r1, r2);
        r0 = r0.append(r1);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x0155:
        r0 = r4 instanceof org.telegram.tgnet.TLRPC.PhotoSize;
        if (r0 == 0) goto L_0x019d;
    L_0x0159:
        r4 = (org.telegram.tgnet.TLRPC.PhotoSize) r4;
        r0 = r4.location;
        if (r0 == 0) goto L_0x0165;
    L_0x015f:
        r0 = r4.location;
        r0 = r0 instanceof org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
        if (r0 == 0) goto L_0x016a;
    L_0x0165:
        r0 = "";
        goto L_0x0060;
    L_0x016a:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = r4.location;
        r2 = r1.volume_id;
        r0 = r0.append(r2);
        r1 = "_";
        r0 = r0.append(r1);
        r1 = r4.location;
        r1 = r1.local_id;
        r0 = r0.append(r1);
        r1 = ".";
        r0 = r0.append(r1);
        if (r5 == 0) goto L_0x0199;
    L_0x018f:
        r0 = r0.append(r5);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x0199:
        r5 = "jpg";
        goto L_0x018f;
    L_0x019d:
        r0 = r4 instanceof org.telegram.tgnet.TLRPC.FileLocation;
        if (r0 == 0) goto L_0x01db;
    L_0x01a1:
        r0 = r4 instanceof org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
        if (r0 == 0) goto L_0x01aa;
    L_0x01a5:
        r0 = "";
        goto L_0x0060;
    L_0x01aa:
        r4 = (org.telegram.tgnet.TLRPC.FileLocation) r4;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r2 = r4.volume_id;
        r0 = r0.append(r2);
        r1 = "_";
        r0 = r0.append(r1);
        r1 = r4.local_id;
        r0 = r0.append(r1);
        r1 = ".";
        r0 = r0.append(r1);
        if (r5 == 0) goto L_0x01d7;
    L_0x01cd:
        r0 = r0.append(r5);
        r0 = r0.toString();
        goto L_0x0060;
    L_0x01d7:
        r5 = "jpg";
        goto L_0x01cd;
    L_0x01db:
        r0 = "";
        goto L_0x0060;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileLoader.slsGetAttachFileName(org.telegram.tgnet.TLObject, java.lang.String):java.lang.String");
    }

    public void addCustomDelegate(FileLoaderDelegateCustomForDownloader fileLoaderDelegateCustomForDownloader) {
        synchronized (this) {
            if (this.customDelegates == null) {
                this.customDelegates = new ArrayList();
            }
            this.customDelegates.add(fileLoaderDelegateCustomForDownloader);
        }
    }

    public void addDelegate(FileLoaderDelegate fileLoaderDelegate) {
        synchronized (this) {
            if (this.delegates == null) {
                this.delegates = new ArrayList();
            }
            this.delegates.add(fileLoaderDelegate);
        }
    }

    public void cancelLoadFile(Document document) {
        cancelLoadFile(document, null, null, null);
    }

    public void cancelLoadFile(FileLocation fileLocation, String str) {
        cancelLoadFile(null, null, fileLocation, str);
    }

    public void cancelLoadFile(PhotoSize photoSize) {
        cancelLoadFile(null, null, photoSize.location, null);
    }

    public void cancelLoadFile(TLRPC$TL_webDocument tLRPC$TL_webDocument) {
        cancelLoadFile(null, tLRPC$TL_webDocument, null, null);
    }

    public void cancelUploadFile(final String str, final boolean z) {
        this.fileLoaderQueue.postRunnable(new Runnable() {
            public void run() {
                FileUploadOperation fileUploadOperation = !z ? (FileUploadOperation) FileLoader.this.uploadOperationPaths.get(str) : (FileUploadOperation) FileLoader.this.uploadOperationPathsEnc.get(str);
                FileLoader.this.uploadSizes.remove(str);
                if (fileUploadOperation != null) {
                    FileLoader.this.uploadOperationPathsEnc.remove(str);
                    FileLoader.this.uploadOperationQueue.remove(fileUploadOperation);
                    FileLoader.this.uploadSmallOperationQueue.remove(fileUploadOperation);
                    fileUploadOperation.cancel();
                }
            }
        });
    }

    public File checkDirectory(int i) {
        return (File) this.mediaDirs.get(Integer.valueOf(i));
    }

    public void checkUploadNewDataAvailable(String str, boolean z, long j) {
        final boolean z2 = z;
        final String str2 = str;
        final long j2 = j;
        this.fileLoaderQueue.postRunnable(new Runnable() {
            public void run() {
                FileUploadOperation fileUploadOperation = z2 ? (FileUploadOperation) FileLoader.this.uploadOperationPathsEnc.get(str2) : (FileUploadOperation) FileLoader.this.uploadOperationPaths.get(str2);
                if (fileUploadOperation != null) {
                    fileUploadOperation.checkNewDataAvailable(j2);
                } else if (j2 != 0) {
                    FileLoader.this.uploadSizes.put(str2, Long.valueOf(j2));
                }
            }
        });
    }

    public void deleteFiles(final ArrayList<File> arrayList, final int i) {
        if (arrayList != null && !arrayList.isEmpty()) {
            this.fileLoaderQueue.postRunnable(new Runnable() {
                public void run() {
                    for (int i = 0; i < arrayList.size(); i++) {
                        File file = (File) arrayList.get(i);
                        File file2 = new File(file.getAbsolutePath() + ".enc");
                        if (file2.exists()) {
                            try {
                                if (!file2.delete()) {
                                    file2.deleteOnExit();
                                }
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                            try {
                                file2 = new File(FileLoader.getInternalCacheDir(), file.getName() + ".enc.key");
                                if (!file2.delete()) {
                                    file2.deleteOnExit();
                                }
                            } catch (Throwable e2) {
                                FileLog.m13728e(e2);
                            }
                        } else if (file.exists()) {
                            try {
                                if (!file.delete()) {
                                    file.deleteOnExit();
                                }
                            } catch (Throwable e22) {
                                FileLog.m13728e(e22);
                            }
                        }
                        try {
                            file2 = new File(file.getParentFile(), "q_" + file.getName());
                            if (file2.exists() && !file2.delete()) {
                                file2.deleteOnExit();
                            }
                        } catch (Throwable e3) {
                            FileLog.m13728e(e3);
                        }
                    }
                    if (i == 2) {
                        ImageLoader.getInstance().clearMemory();
                    }
                }
            });
        }
    }

    public File getDirectory(int i) {
        File file = (File) this.mediaDirs.get(Integer.valueOf(i));
        if (file == null && i != 4) {
            file = (File) this.mediaDirs.get(Integer.valueOf(4));
        }
        try {
            if (!file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e) {
        }
        return file;
    }

    public boolean isLoadingFile(final String str) {
        final Semaphore semaphore = new Semaphore(0);
        final Boolean[] boolArr = new Boolean[1];
        this.fileLoaderQueue.postRunnable(new Runnable() {
            public void run() {
                boolArr[0] = Boolean.valueOf(FileLoader.this.loadOperationPaths.containsKey(str));
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return boolArr[0].booleanValue();
    }

    public void loadFile(Document document, boolean z, int i) {
        int i2 = (i != 0 || document == null || document.key == null) ? i : 1;
        loadFile(document, null, null, null, 0, z, i2);
    }

    public void loadFile(FileLocation fileLocation, String str, int i, int i2) {
        int i3 = (i2 != 0 || (i != 0 && (fileLocation == null || fileLocation.key == null))) ? i2 : 1;
        loadFile(null, null, fileLocation, str, i, true, i3);
    }

    public void loadFile(PhotoSize photoSize, String str, int i) {
        int i2 = (i != 0 || ((photoSize == null || photoSize.size != 0) && photoSize.location.key == null)) ? i : 1;
        loadFile(null, null, photoSize.location, str, photoSize.size, false, i2);
    }

    public void loadFile(TLRPC$TL_webDocument tLRPC$TL_webDocument, boolean z, int i) {
        loadFile(null, tLRPC$TL_webDocument, null, null, 0, z, i);
    }

    public void setDelegate(FileLoaderDelegate fileLoaderDelegate) {
        this.delegate = fileLoaderDelegate;
    }

    public void setMediaDirs(HashMap<Integer, File> hashMap) {
        this.mediaDirs = hashMap;
    }

    public void uploadFile(String str, boolean z, boolean z2, int i) {
        uploadFile(str, z, z2, 0, i);
    }

    public void uploadFile(String str, boolean z, boolean z2, int i, int i2) {
        if (str != null) {
            final boolean z3 = z;
            final String str2 = str;
            final int i3 = i;
            final int i4 = i2;
            final boolean z4 = z2;
            this.fileLoaderQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.FileLoader$3$1 */
                class C30361 implements FileUploadOperationDelegate {

                    /* renamed from: org.telegram.messenger.FileLoader$3$1$2 */
                    class C30352 implements Runnable {
                        C30352() {
                        }

                        public void run() {
                            if (z3) {
                                FileLoader.this.uploadOperationPathsEnc.remove(str2);
                            } else {
                                FileLoader.this.uploadOperationPaths.remove(str2);
                            }
                            if (FileLoader.this.delegate != null) {
                                FileLoader.this.delegate.fileDidFailedUpload(str2, z3);
                            }
                            FileUploadOperation fileUploadOperation;
                            if (z4) {
                                FileLoader.this.currentUploadSmallOperationsCount = FileLoader.this.currentUploadSmallOperationsCount - 1;
                                if (FileLoader.this.currentUploadSmallOperationsCount < 1) {
                                    fileUploadOperation = (FileUploadOperation) FileLoader.this.uploadSmallOperationQueue.poll();
                                    if (fileUploadOperation != null) {
                                        FileLoader.this.currentUploadSmallOperationsCount = FileLoader.this.currentUploadSmallOperationsCount + 1;
                                        fileUploadOperation.start();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            FileLoader.this.currentUploadOperationsCount = FileLoader.this.currentUploadOperationsCount - 1;
                            if (FileLoader.this.currentUploadOperationsCount < 1) {
                                fileUploadOperation = (FileUploadOperation) FileLoader.this.uploadOperationQueue.poll();
                                if (fileUploadOperation != null) {
                                    FileLoader.this.currentUploadOperationsCount = FileLoader.this.currentUploadOperationsCount + 1;
                                    fileUploadOperation.start();
                                }
                            }
                        }
                    }

                    C30361() {
                    }

                    public void didChangedUploadProgress(FileUploadOperation fileUploadOperation, float f) {
                        if (FileLoader.this.delegate != null) {
                            FileLoader.this.delegate.fileUploadProgressChanged(str2, f, z3);
                        }
                    }

                    public void didFailedUploadingFile(FileUploadOperation fileUploadOperation) {
                        FileLoader.this.fileLoaderQueue.postRunnable(new C30352());
                    }

                    public void didFinishUploadingFile(FileUploadOperation fileUploadOperation, InputFile inputFile, InputEncryptedFile inputEncryptedFile, byte[] bArr, byte[] bArr2) {
                        final InputFile inputFile2 = inputFile;
                        final InputEncryptedFile inputEncryptedFile2 = inputEncryptedFile;
                        final byte[] bArr3 = bArr;
                        final byte[] bArr4 = bArr2;
                        final FileUploadOperation fileUploadOperation2 = fileUploadOperation;
                        FileLoader.this.fileLoaderQueue.postRunnable(new Runnable() {
                            public void run() {
                                if (z3) {
                                    FileLoader.this.uploadOperationPathsEnc.remove(str2);
                                } else {
                                    FileLoader.this.uploadOperationPaths.remove(str2);
                                }
                                FileUploadOperation fileUploadOperation;
                                if (z4) {
                                    FileLoader.this.currentUploadSmallOperationsCount = FileLoader.this.currentUploadSmallOperationsCount - 1;
                                    if (FileLoader.this.currentUploadSmallOperationsCount < 1) {
                                        fileUploadOperation = (FileUploadOperation) FileLoader.this.uploadSmallOperationQueue.poll();
                                        if (fileUploadOperation != null) {
                                            FileLoader.this.currentUploadSmallOperationsCount = FileLoader.this.currentUploadSmallOperationsCount + 1;
                                            fileUploadOperation.start();
                                        }
                                    }
                                } else {
                                    FileLoader.this.currentUploadOperationsCount = FileLoader.this.currentUploadOperationsCount - 1;
                                    if (FileLoader.this.currentUploadOperationsCount < 1) {
                                        fileUploadOperation = (FileUploadOperation) FileLoader.this.uploadOperationQueue.poll();
                                        if (fileUploadOperation != null) {
                                            FileLoader.this.currentUploadOperationsCount = FileLoader.this.currentUploadOperationsCount + 1;
                                            fileUploadOperation.start();
                                        }
                                    }
                                }
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileDidUploaded(str2, inputFile2, inputEncryptedFile2, bArr3, bArr4, fileUploadOperation2.getTotalFileSize());
                                }
                            }
                        });
                    }
                }

                public void run() {
                    int i;
                    if (z3) {
                        if (FileLoader.this.uploadOperationPathsEnc.containsKey(str2)) {
                            return;
                        }
                    } else if (FileLoader.this.uploadOperationPaths.containsKey(str2)) {
                        return;
                    }
                    int i2 = i3;
                    if (i2 == 0 || ((Long) FileLoader.this.uploadSizes.get(str2)) == null) {
                        i = i2;
                    } else {
                        i = 0;
                        FileLoader.this.uploadSizes.remove(str2);
                    }
                    FileUploadOperation fileUploadOperation = new FileUploadOperation(str2, z3, i, i4);
                    if (z3) {
                        FileLoader.this.uploadOperationPathsEnc.put(str2, fileUploadOperation);
                    } else {
                        FileLoader.this.uploadOperationPaths.put(str2, fileUploadOperation);
                    }
                    fileUploadOperation.setDelegate(new C30361());
                    if (z4) {
                        if (FileLoader.this.currentUploadSmallOperationsCount < 1) {
                            FileLoader.this.currentUploadSmallOperationsCount = FileLoader.this.currentUploadSmallOperationsCount + 1;
                            fileUploadOperation.start();
                            return;
                        }
                        FileLoader.this.uploadSmallOperationQueue.add(fileUploadOperation);
                    } else if (FileLoader.this.currentUploadOperationsCount < 1) {
                        FileLoader.this.currentUploadOperationsCount = FileLoader.this.currentUploadOperationsCount + 1;
                        fileUploadOperation.start();
                    } else {
                        FileLoader.this.uploadOperationQueue.add(fileUploadOperation);
                    }
                }
            });
        }
    }
}
