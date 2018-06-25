package org.telegram.messenger;

import android.webkit.MimeTypeMap;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import org.telegram.messenger.FileLoadOperation.FileLoadOperationDelegate;
import org.telegram.messenger.FileUploadOperation.FileUploadOperationDelegate;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$InputEncryptedFile;
import org.telegram.tgnet.TLRPC$InputFile;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeFilename;
import org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaInvoice;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_photoCachedSize;
import org.telegram.tgnet.TLRPC$TL_webDocument;
import org.telegram.tgnet.TLRPC.DocumentAttribute;

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

        void fileDidUploaded(String str, TLRPC$InputFile tLRPC$InputFile, TLRPC$InputEncryptedFile tLRPC$InputEncryptedFile, byte[] bArr, byte[] bArr2, long j);

        void fileLoadProgressChanged(String str, float f);

        void fileUploadProgressChanged(String str, float f, boolean z);
    }

    public interface FileLoaderDelegateCustomForDownloader {
        void fileDidFailedLoad(TLRPC$Document tLRPC$Document, TLRPC$FileLocation tLRPC$FileLocation);

        void fileDidLoaded(TLRPC$Document tLRPC$Document, TLRPC$FileLocation tLRPC$FileLocation);

        void fileLoadProgressChanged(TLRPC$Document tLRPC$Document, TLRPC$FileLocation tLRPC$FileLocation);
    }

    public static FileLoader getInstance() {
        FileLoader localInstance = Instance;
        if (localInstance == null) {
            synchronized (FileLoader.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        FileLoader localInstance2 = new FileLoader();
                        try {
                            Instance = localInstance2;
                            localInstance = localInstance2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            localInstance = localInstance2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return localInstance;
    }

    public void setMediaDirs(HashMap<Integer, File> dirs) {
        this.mediaDirs = dirs;
    }

    public File checkDirectory(int type) {
        return (File) this.mediaDirs.get(Integer.valueOf(type));
    }

    public File getDirectory(int type) {
        File dir = (File) this.mediaDirs.get(Integer.valueOf(type));
        if (dir == null && type != 4) {
            dir = (File) this.mediaDirs.get(Integer.valueOf(4));
        }
        try {
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
        } catch (Exception e) {
        }
        return dir;
    }

    public void cancelUploadFile(final String location, final boolean enc) {
        this.fileLoaderQueue.postRunnable(new Runnable() {
            public void run() {
                FileUploadOperation operation;
                if (enc) {
                    operation = (FileUploadOperation) FileLoader.this.uploadOperationPathsEnc.get(location);
                } else {
                    operation = (FileUploadOperation) FileLoader.this.uploadOperationPaths.get(location);
                }
                FileLoader.this.uploadSizes.remove(location);
                if (operation != null) {
                    FileLoader.this.uploadOperationPathsEnc.remove(location);
                    FileLoader.this.uploadOperationQueue.remove(operation);
                    FileLoader.this.uploadSmallOperationQueue.remove(operation);
                    operation.cancel();
                }
            }
        });
    }

    public void checkUploadNewDataAvailable(String location, boolean encrypted, long finalSize) {
        final boolean z = encrypted;
        final String str = location;
        final long j = finalSize;
        this.fileLoaderQueue.postRunnable(new Runnable() {
            public void run() {
                FileUploadOperation operation;
                if (z) {
                    operation = (FileUploadOperation) FileLoader.this.uploadOperationPathsEnc.get(str);
                } else {
                    operation = (FileUploadOperation) FileLoader.this.uploadOperationPaths.get(str);
                }
                if (operation != null) {
                    operation.checkNewDataAvailable(j);
                } else if (j != 0) {
                    FileLoader.this.uploadSizes.put(str, Long.valueOf(j));
                }
            }
        });
    }

    public void uploadFile(String location, boolean encrypted, boolean small, int type) {
        uploadFile(location, encrypted, small, 0, type);
    }

    public void uploadFile(String location, boolean encrypted, boolean small, int estimatedSize, int type) {
        if (location != null) {
            final boolean z = encrypted;
            final String str = location;
            final int i = estimatedSize;
            final int i2 = type;
            final boolean z2 = small;
            this.fileLoaderQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.FileLoader$3$1 */
                class C13301 implements FileUploadOperationDelegate {

                    /* renamed from: org.telegram.messenger.FileLoader$3$1$2 */
                    class C13292 implements Runnable {
                        C13292() {
                        }

                        public void run() {
                            if (z) {
                                FileLoader.this.uploadOperationPathsEnc.remove(str);
                            } else {
                                FileLoader.this.uploadOperationPaths.remove(str);
                            }
                            if (FileLoader.this.delegate != null) {
                                FileLoader.this.delegate.fileDidFailedUpload(str, z);
                            }
                            FileUploadOperation operation;
                            if (z2) {
                                FileLoader.this.currentUploadSmallOperationsCount = FileLoader.this.currentUploadSmallOperationsCount - 1;
                                if (FileLoader.this.currentUploadSmallOperationsCount < 1) {
                                    operation = (FileUploadOperation) FileLoader.this.uploadSmallOperationQueue.poll();
                                    if (operation != null) {
                                        FileLoader.this.currentUploadSmallOperationsCount = FileLoader.this.currentUploadSmallOperationsCount + 1;
                                        operation.start();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            FileLoader.this.currentUploadOperationsCount = FileLoader.this.currentUploadOperationsCount - 1;
                            if (FileLoader.this.currentUploadOperationsCount < 1) {
                                operation = (FileUploadOperation) FileLoader.this.uploadOperationQueue.poll();
                                if (operation != null) {
                                    FileLoader.this.currentUploadOperationsCount = FileLoader.this.currentUploadOperationsCount + 1;
                                    operation.start();
                                }
                            }
                        }
                    }

                    C13301() {
                    }

                    public void didFinishUploadingFile(FileUploadOperation operation, TLRPC$InputFile inputFile, TLRPC$InputEncryptedFile inputEncryptedFile, byte[] key, byte[] iv) {
                        final TLRPC$InputFile tLRPC$InputFile = inputFile;
                        final TLRPC$InputEncryptedFile tLRPC$InputEncryptedFile = inputEncryptedFile;
                        final byte[] bArr = key;
                        final byte[] bArr2 = iv;
                        final FileUploadOperation fileUploadOperation = operation;
                        FileLoader.this.fileLoaderQueue.postRunnable(new Runnable() {
                            public void run() {
                                if (z) {
                                    FileLoader.this.uploadOperationPathsEnc.remove(str);
                                } else {
                                    FileLoader.this.uploadOperationPaths.remove(str);
                                }
                                FileUploadOperation operation;
                                if (z2) {
                                    FileLoader.this.currentUploadSmallOperationsCount = FileLoader.this.currentUploadSmallOperationsCount - 1;
                                    if (FileLoader.this.currentUploadSmallOperationsCount < 1) {
                                        operation = (FileUploadOperation) FileLoader.this.uploadSmallOperationQueue.poll();
                                        if (operation != null) {
                                            FileLoader.this.currentUploadSmallOperationsCount = FileLoader.this.currentUploadSmallOperationsCount + 1;
                                            operation.start();
                                        }
                                    }
                                } else {
                                    FileLoader.this.currentUploadOperationsCount = FileLoader.this.currentUploadOperationsCount - 1;
                                    if (FileLoader.this.currentUploadOperationsCount < 1) {
                                        operation = (FileUploadOperation) FileLoader.this.uploadOperationQueue.poll();
                                        if (operation != null) {
                                            FileLoader.this.currentUploadOperationsCount = FileLoader.this.currentUploadOperationsCount + 1;
                                            operation.start();
                                        }
                                    }
                                }
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileDidUploaded(str, tLRPC$InputFile, tLRPC$InputEncryptedFile, bArr, bArr2, fileUploadOperation.getTotalFileSize());
                                }
                            }
                        });
                    }

                    public void didFailedUploadingFile(FileUploadOperation operation) {
                        FileLoader.this.fileLoaderQueue.postRunnable(new C13292());
                    }

                    public void didChangedUploadProgress(FileUploadOperation operation, float progress) {
                        if (FileLoader.this.delegate != null) {
                            FileLoader.this.delegate.fileUploadProgressChanged(str, progress, z);
                        }
                    }
                }

                public void run() {
                    if (z) {
                        if (FileLoader.this.uploadOperationPathsEnc.containsKey(str)) {
                            return;
                        }
                    } else if (FileLoader.this.uploadOperationPaths.containsKey(str)) {
                        return;
                    }
                    int esimated = i;
                    if (!(esimated == 0 || ((Long) FileLoader.this.uploadSizes.get(str)) == null)) {
                        esimated = 0;
                        FileLoader.this.uploadSizes.remove(str);
                    }
                    FileUploadOperation operation = new FileUploadOperation(str, z, esimated, i2);
                    if (z) {
                        FileLoader.this.uploadOperationPathsEnc.put(str, operation);
                    } else {
                        FileLoader.this.uploadOperationPaths.put(str, operation);
                    }
                    operation.setDelegate(new C13301());
                    if (z2) {
                        if (FileLoader.this.currentUploadSmallOperationsCount < 1) {
                            FileLoader.this.currentUploadSmallOperationsCount = FileLoader.this.currentUploadSmallOperationsCount + 1;
                            operation.start();
                            return;
                        }
                        FileLoader.this.uploadSmallOperationQueue.add(operation);
                    } else if (FileLoader.this.currentUploadOperationsCount < 1) {
                        FileLoader.this.currentUploadOperationsCount = FileLoader.this.currentUploadOperationsCount + 1;
                        operation.start();
                    } else {
                        FileLoader.this.uploadOperationQueue.add(operation);
                    }
                }
            });
        }
    }

    public void cancelLoadFile(TLRPC$Document document) {
        cancelLoadFile(document, null, null, null);
    }

    public void cancelLoadFile(TLRPC$TL_webDocument document) {
        cancelLoadFile(null, document, null, null);
    }

    public void cancelLoadFile(TLRPC$PhotoSize photo) {
        cancelLoadFile(null, null, photo.location, null);
    }

    public void cancelLoadFile(TLRPC$FileLocation location, String ext) {
        cancelLoadFile(null, null, location, ext);
    }

    private void cancelLoadFile(TLRPC$Document document, TLRPC$TL_webDocument webDocument, TLRPC$FileLocation location, String locationExt) {
        if (location != null || document != null) {
            final TLRPC$FileLocation tLRPC$FileLocation = location;
            final String str = locationExt;
            final TLRPC$Document tLRPC$Document = document;
            final TLRPC$TL_webDocument tLRPC$TL_webDocument = webDocument;
            this.fileLoaderQueue.postRunnable(new Runnable() {
                public void run() {
                    String fileName = null;
                    if (tLRPC$FileLocation != null) {
                        fileName = FileLoader.getAttachFileName(tLRPC$FileLocation, str);
                    } else if (tLRPC$Document != null) {
                        fileName = FileLoader.getAttachFileName(tLRPC$Document);
                    } else if (tLRPC$TL_webDocument != null) {
                        fileName = FileLoader.getAttachFileName(tLRPC$TL_webDocument);
                    }
                    if (fileName != null) {
                        FileLoadOperation operation = (FileLoadOperation) FileLoader.this.loadOperationPaths.remove(fileName);
                        if (operation != null) {
                            if (MessageObject.isVoiceDocument(tLRPC$Document) || MessageObject.isVoiceWebDocument(tLRPC$TL_webDocument)) {
                                if (!FileLoader.this.audioLoadOperationQueue.remove(operation)) {
                                    FileLoader.this.currentAudioLoadOperationsCount = FileLoader.this.currentAudioLoadOperationsCount - 1;
                                }
                            } else if (tLRPC$FileLocation != null) {
                                if (!FileLoader.this.photoLoadOperationQueue.remove(operation) || MessageObject.isImageWebDocument(tLRPC$TL_webDocument)) {
                                    FileLoader.this.currentPhotoLoadOperationsCount = FileLoader.this.currentPhotoLoadOperationsCount - 1;
                                }
                            } else if (!FileLoader.this.loadOperationQueue.remove(operation)) {
                                FileLoader.this.currentLoadOperationsCount = FileLoader.this.currentLoadOperationsCount - 1;
                            }
                            operation.cancel();
                        }
                    }
                }
            });
        }
    }

    public boolean isLoadingFile(final String fileName) {
        final Semaphore semaphore = new Semaphore(0);
        final Boolean[] result = new Boolean[1];
        this.fileLoaderQueue.postRunnable(new Runnable() {
            public void run() {
                result[0] = Boolean.valueOf(FileLoader.this.loadOperationPaths.containsKey(fileName));
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (Exception e) {
            FileLog.e(e);
        }
        return result[0].booleanValue();
    }

    public void loadFile(TLRPC$PhotoSize photo, String ext, int cacheType) {
        if (cacheType == 0 && ((photo != null && photo.size == 0) || photo.location.key != null)) {
            cacheType = 1;
        }
        loadFile(null, null, photo.location, ext, photo.size, false, cacheType);
    }

    public void loadFile(TLRPC$Document document, boolean force, int cacheType) {
        if (!(cacheType != 0 || document == null || document.key == null)) {
            cacheType = 1;
        }
        loadFile(document, null, null, null, 0, force, cacheType);
    }

    public void loadFile(TLRPC$TL_webDocument document, boolean force, int cacheType) {
        loadFile(null, document, null, null, 0, force, cacheType);
    }

    public void loadFile(TLRPC$FileLocation location, String ext, int size, int cacheType) {
        if (cacheType == 0 && (size == 0 || !(location == null || location.key == null))) {
            cacheType = 1;
        }
        loadFile(null, null, location, ext, size, true, cacheType);
    }

    private void loadFile(TLRPC$Document document, TLRPC$TL_webDocument webDocument, TLRPC$FileLocation location, String locationExt, int locationSize, boolean force, int cacheType) {
        final TLRPC$FileLocation tLRPC$FileLocation = location;
        final String str = locationExt;
        final TLRPC$Document tLRPC$Document = document;
        final TLRPC$TL_webDocument tLRPC$TL_webDocument = webDocument;
        final boolean z = force;
        final int i = locationSize;
        final int i2 = cacheType;
        this.fileLoaderQueue.postRunnable(new Runnable() {
            public void run() {
                String fileName = null;
                if (tLRPC$FileLocation != null) {
                    fileName = FileLoader.getAttachFileName(tLRPC$FileLocation, str);
                } else if (tLRPC$Document != null) {
                    fileName = FileLoader.getAttachFileName(tLRPC$Document);
                } else if (tLRPC$TL_webDocument != null) {
                    fileName = FileLoader.getAttachFileName(tLRPC$TL_webDocument);
                }
                if (fileName != null && !fileName.contains("-2147483648")) {
                    FileLoadOperation operation = (FileLoadOperation) FileLoader.this.loadOperationPaths.get(fileName);
                    if (operation == null) {
                        File tempDir = FileLoader.this.getDirectory(4);
                        File storeDir = tempDir;
                        int type = 4;
                        if (tLRPC$FileLocation != null) {
                            operation = new FileLoadOperation(tLRPC$FileLocation, str, i);
                            type = 0;
                        } else if (tLRPC$Document != null) {
                            operation = new FileLoadOperation(tLRPC$Document);
                            if (MessageObject.isVoiceDocument(tLRPC$Document)) {
                                type = 1;
                            } else if (MessageObject.isVideoDocument(tLRPC$Document)) {
                                type = 2;
                            } else {
                                type = 3;
                            }
                        } else if (tLRPC$TL_webDocument != null) {
                            operation = new FileLoadOperation(tLRPC$TL_webDocument);
                            if (MessageObject.isVoiceWebDocument(tLRPC$TL_webDocument)) {
                                type = 1;
                            } else if (MessageObject.isVideoWebDocument(tLRPC$TL_webDocument)) {
                                type = 2;
                            } else if (MessageObject.isImageWebDocument(tLRPC$TL_webDocument)) {
                                type = 0;
                            } else {
                                type = 3;
                            }
                        }
                        if (i2 == 0) {
                            storeDir = FileLoader.this.getDirectory(type);
                        } else if (i2 == 2) {
                            operation.setEncryptFile(true);
                        }
                        operation.setPaths(storeDir, tempDir);
                        final String finalFileName = fileName;
                        final int finalType = type;
                        operation.setDelegate(new FileLoadOperationDelegate() {
                            public void didFinishLoadingFile(FileLoadOperation operation, File finalFile) {
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileDidLoaded(finalFileName, finalFile, finalType);
                                }
                                FileLoader.this.checkDownloadQueue(tLRPC$Document, tLRPC$TL_webDocument, tLRPC$FileLocation, finalFileName);
                            }

                            public void didFailedLoadingFile(FileLoadOperation operation, int reason) {
                                FileLoader.this.checkDownloadQueue(tLRPC$Document, tLRPC$TL_webDocument, tLRPC$FileLocation, finalFileName);
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileDidFailedLoad(finalFileName, reason);
                                }
                            }

                            public void didChangedLoadProgress(FileLoadOperation operation, float progress) {
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileLoadProgressChanged(finalFileName, progress);
                                }
                            }
                        });
                        operation.setDelegate(new FileLoadOperationDelegate() {
                            public void didFinishLoadingFile(FileLoadOperation operation, File finalFile) {
                                Iterator it;
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileDidLoaded(finalFileName, finalFile, finalType);
                                }
                                if (FileLoader.this.delegates != null) {
                                    try {
                                        it = FileLoader.this.delegates.iterator();
                                        while (it.hasNext()) {
                                            try {
                                                ((FileLoaderDelegate) it.next()).fileDidLoaded(finalFileName, finalFile, finalType);
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
                                            ((FileLoaderDelegateCustomForDownloader) it.next()).fileDidLoaded(tLRPC$Document, tLRPC$FileLocation);
                                        }
                                    }
                                }
                                FileLoader.this.checkDownloadQueue(tLRPC$Document, tLRPC$TL_webDocument, tLRPC$FileLocation, finalFileName);
                            }

                            public void didFailedLoadingFile(FileLoadOperation operation, int canceled) {
                                Iterator it;
                                FileLoader.this.checkDownloadQueue(tLRPC$Document, tLRPC$TL_webDocument, tLRPC$FileLocation, finalFileName);
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileDidFailedLoad(finalFileName, canceled);
                                }
                                if (FileLoader.this.delegates != null) {
                                    try {
                                        it = FileLoader.this.delegates.iterator();
                                        while (it.hasNext()) {
                                            try {
                                                ((FileLoaderDelegate) it.next()).fileDidFailedLoad(finalFileName, canceled);
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
                                            ((FileLoaderDelegateCustomForDownloader) it.next()).fileDidFailedLoad(tLRPC$Document, tLRPC$FileLocation);
                                        }
                                    }
                                }
                            }

                            public void didChangedLoadProgress(FileLoadOperation operation, float progress) {
                                Iterator it;
                                if (FileLoader.this.delegate != null) {
                                    FileLoader.this.delegate.fileLoadProgressChanged(finalFileName, progress);
                                }
                                if (FileLoader.this.delegates != null) {
                                    try {
                                        it = FileLoader.this.delegates.iterator();
                                        while (it.hasNext()) {
                                            try {
                                                ((FileLoaderDelegate) it.next()).fileLoadProgressChanged(finalFileName, progress);
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
                                            ((FileLoaderDelegateCustomForDownloader) it.next()).fileLoadProgressChanged(tLRPC$Document, tLRPC$FileLocation);
                                        }
                                    }
                                }
                            }
                        });
                        FileLoader.this.loadOperationPaths.put(fileName, operation);
                        int maxCount = z ? 3 : 1;
                        if (type == 1) {
                            if (FileLoader.this.currentAudioLoadOperationsCount < maxCount) {
                                if (operation.start()) {
                                    FileLoader.this.currentAudioLoadOperationsCount = FileLoader.this.currentAudioLoadOperationsCount + 1;
                                }
                            } else if (z) {
                                FileLoader.this.audioLoadOperationQueue.add(0, operation);
                            } else {
                                FileLoader.this.audioLoadOperationQueue.add(operation);
                            }
                        } else if (tLRPC$FileLocation != null) {
                            if (FileLoader.this.currentPhotoLoadOperationsCount < maxCount) {
                                if (operation.start()) {
                                    FileLoader.this.currentPhotoLoadOperationsCount = FileLoader.this.currentPhotoLoadOperationsCount + 1;
                                }
                            } else if (z) {
                                FileLoader.this.photoLoadOperationQueue.add(0, operation);
                            } else {
                                FileLoader.this.photoLoadOperationQueue.add(operation);
                            }
                        } else if (FileLoader.this.currentLoadOperationsCount < maxCount) {
                            if (operation.start()) {
                                FileLoader.this.currentLoadOperationsCount = FileLoader.this.currentLoadOperationsCount + 1;
                            }
                        } else if (z) {
                            FileLoader.this.loadOperationQueue.add(0, operation);
                        } else {
                            FileLoader.this.loadOperationQueue.add(operation);
                        }
                    } else if (z) {
                        LinkedList<FileLoadOperation> downloadQueue;
                        operation.setForceRequest(true);
                        if (MessageObject.isVoiceDocument(tLRPC$Document) || MessageObject.isVoiceWebDocument(tLRPC$TL_webDocument)) {
                            downloadQueue = FileLoader.this.audioLoadOperationQueue;
                        } else if (tLRPC$FileLocation != null || MessageObject.isImageWebDocument(tLRPC$TL_webDocument)) {
                            downloadQueue = FileLoader.this.photoLoadOperationQueue;
                        } else {
                            downloadQueue = FileLoader.this.loadOperationQueue;
                        }
                        if (downloadQueue != null) {
                            int index = downloadQueue.indexOf(operation);
                            if (index > 0) {
                                downloadQueue.remove(index);
                                downloadQueue.add(0, operation);
                            }
                        }
                    }
                }
            }
        });
    }

    private void checkDownloadQueue(TLRPC$Document document, TLRPC$TL_webDocument webDocument, TLRPC$FileLocation location, String arg1) {
        final String str = arg1;
        final TLRPC$Document tLRPC$Document = document;
        final TLRPC$TL_webDocument tLRPC$TL_webDocument = webDocument;
        final TLRPC$FileLocation tLRPC$FileLocation = location;
        this.fileLoaderQueue.postRunnable(new Runnable() {
            public void run() {
                FileLoadOperation operation = (FileLoadOperation) FileLoader.this.loadOperationPaths.remove(str);
                int maxCount;
                if (MessageObject.isVoiceDocument(tLRPC$Document) || MessageObject.isVoiceWebDocument(tLRPC$TL_webDocument)) {
                    if (operation != null) {
                        if (operation.wasStarted()) {
                            FileLoader.this.currentAudioLoadOperationsCount = FileLoader.this.currentAudioLoadOperationsCount - 1;
                        } else {
                            FileLoader.this.audioLoadOperationQueue.remove(operation);
                        }
                    }
                    while (!FileLoader.this.audioLoadOperationQueue.isEmpty()) {
                        if (((FileLoadOperation) FileLoader.this.audioLoadOperationQueue.get(0)).isForceRequest()) {
                            maxCount = 3;
                        } else {
                            maxCount = 1;
                        }
                        if (FileLoader.this.currentAudioLoadOperationsCount < maxCount) {
                            operation = (FileLoadOperation) FileLoader.this.audioLoadOperationQueue.poll();
                            if (operation != null && operation.start()) {
                                FileLoader.this.currentAudioLoadOperationsCount = FileLoader.this.currentAudioLoadOperationsCount + 1;
                            }
                        } else {
                            return;
                        }
                    }
                } else if (tLRPC$FileLocation != null || MessageObject.isImageWebDocument(tLRPC$TL_webDocument)) {
                    if (operation != null) {
                        if (operation.wasStarted()) {
                            FileLoader.this.currentPhotoLoadOperationsCount = FileLoader.this.currentPhotoLoadOperationsCount - 1;
                        } else {
                            FileLoader.this.photoLoadOperationQueue.remove(operation);
                        }
                    }
                    while (!FileLoader.this.photoLoadOperationQueue.isEmpty()) {
                        if (((FileLoadOperation) FileLoader.this.photoLoadOperationQueue.get(0)).isForceRequest()) {
                            maxCount = 3;
                        } else {
                            maxCount = 1;
                        }
                        if (FileLoader.this.currentPhotoLoadOperationsCount < maxCount) {
                            operation = (FileLoadOperation) FileLoader.this.photoLoadOperationQueue.poll();
                            if (operation != null && operation.start()) {
                                FileLoader.this.currentPhotoLoadOperationsCount = FileLoader.this.currentPhotoLoadOperationsCount + 1;
                            }
                        } else {
                            return;
                        }
                    }
                } else {
                    if (operation != null) {
                        if (operation.wasStarted()) {
                            FileLoader.this.currentLoadOperationsCount = FileLoader.this.currentLoadOperationsCount - 1;
                        } else {
                            FileLoader.this.loadOperationQueue.remove(operation);
                        }
                    }
                    while (!FileLoader.this.loadOperationQueue.isEmpty()) {
                        if (((FileLoadOperation) FileLoader.this.loadOperationQueue.get(0)).isForceRequest()) {
                            maxCount = 3;
                        } else {
                            maxCount = 1;
                        }
                        if (FileLoader.this.currentLoadOperationsCount < maxCount) {
                            operation = (FileLoadOperation) FileLoader.this.loadOperationQueue.poll();
                            if (operation != null && operation.start()) {
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

    public void setDelegate(FileLoaderDelegate delegate) {
        this.delegate = delegate;
    }

    public static String getMessageFileName(TLRPC$Message message) {
        if (message == null) {
            return "";
        }
        ArrayList<TLRPC$PhotoSize> sizes;
        TLRPC$PhotoSize sizeFull;
        if (message instanceof TLRPC$TL_messageService) {
            if (message.action.photo != null) {
                sizes = message.action.photo.sizes;
                if (sizes.size() > 0) {
                    sizeFull = getClosestPhotoSizeWithSize(sizes, AndroidUtilities.getPhotoSize());
                    if (sizeFull != null) {
                        return getAttachFileName(sizeFull);
                    }
                }
            }
        } else if (message.media instanceof TLRPC$TL_messageMediaDocument) {
            return getAttachFileName(message.media.document);
        } else {
            if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
                sizes = message.media.photo.sizes;
                if (sizes.size() > 0) {
                    sizeFull = getClosestPhotoSizeWithSize(sizes, AndroidUtilities.getPhotoSize());
                    if (sizeFull != null) {
                        return getAttachFileName(sizeFull);
                    }
                }
            } else if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
                if (message.media.webpage.photo != null) {
                    sizes = message.media.webpage.photo.sizes;
                    if (sizes.size() > 0) {
                        sizeFull = getClosestPhotoSizeWithSize(sizes, AndroidUtilities.getPhotoSize());
                        if (sizeFull != null) {
                            return getAttachFileName(sizeFull);
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
                TLRPC$TL_webDocument document = ((TLRPC$TL_messageMediaInvoice) message.media).photo;
                if (document != null) {
                    return Utilities.MD5(document.url) + "." + ImageLoader.getHttpUrlExtension(document.url, getExtensionByMime(document.mime_type));
                }
            }
        }
        return "";
    }

    public static File getPathToMessage(TLRPC$Message message) {
        boolean z = false;
        boolean z2 = true;
        if (message == null) {
            return new File("");
        }
        ArrayList<TLRPC$PhotoSize> sizes;
        TLRPC$PhotoSize sizeFull;
        if (message instanceof TLRPC$TL_messageService) {
            if (message.action.photo != null) {
                sizes = message.action.photo.sizes;
                if (sizes.size() > 0) {
                    sizeFull = getClosestPhotoSizeWithSize(sizes, AndroidUtilities.getPhotoSize());
                    if (sizeFull != null) {
                        return getPathToAttach(sizeFull);
                    }
                }
            }
        } else if (message.media instanceof TLRPC$TL_messageMediaDocument) {
            TLObject tLObject = message.media.document;
            if (message.media.ttl_seconds != 0) {
                z = true;
            }
            return getPathToAttach(tLObject, z);
        } else if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
            sizes = message.media.photo.sizes;
            if (sizes.size() > 0) {
                sizeFull = getClosestPhotoSizeWithSize(sizes, AndroidUtilities.getPhotoSize());
                if (sizeFull != null) {
                    if (message.media.ttl_seconds == 0) {
                        z2 = false;
                    }
                    return getPathToAttach(sizeFull, z2);
                }
            }
        } else if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
            if (message.media.webpage.document != null) {
                return getPathToAttach(message.media.webpage.document);
            }
            if (message.media.webpage.photo != null) {
                sizes = message.media.webpage.photo.sizes;
                if (sizes.size() > 0) {
                    sizeFull = getClosestPhotoSizeWithSize(sizes, AndroidUtilities.getPhotoSize());
                    if (sizeFull != null) {
                        return getPathToAttach(sizeFull);
                    }
                }
            }
        } else if (message.media instanceof TLRPC$TL_messageMediaInvoice) {
            return getPathToAttach(((TLRPC$TL_messageMediaInvoice) message.media).photo, true);
        }
        return new File("");
    }

    public static File getPathToAttach(TLObject attach) {
        return getPathToAttach(attach, null, false);
    }

    public static File getPathToAttach(TLObject attach, boolean forceCache) {
        return getPathToAttach(attach, null, forceCache);
    }

    public static File getPathToAttach(TLObject attach, String ext, boolean forceCache) {
        File dir = null;
        if (forceCache) {
            dir = getInstance().getDirectory(4);
        } else if (attach instanceof TLRPC$Document) {
            TLRPC$Document document = (TLRPC$Document) attach;
            if (document.key != null) {
                dir = getInstance().getDirectory(4);
            } else if (MessageObject.isVoiceDocument(document)) {
                dir = getInstance().getDirectory(1);
            } else if (MessageObject.isVideoDocument(document)) {
                dir = getInstance().getDirectory(2);
            } else {
                dir = getInstance().getDirectory(3);
            }
        } else if (attach instanceof TLRPC$PhotoSize) {
            TLRPC$PhotoSize photoSize = (TLRPC$PhotoSize) attach;
            if (photoSize.location == null || photoSize.location.key != null || ((photoSize.location.volume_id == -2147483648L && photoSize.location.local_id < 0) || photoSize.size < 0)) {
                dir = getInstance().getDirectory(4);
            } else {
                dir = getInstance().getDirectory(0);
            }
        } else if (attach instanceof TLRPC$FileLocation) {
            TLRPC$FileLocation fileLocation = (TLRPC$FileLocation) attach;
            if (fileLocation.key != null || (fileLocation.volume_id == -2147483648L && fileLocation.local_id < 0)) {
                dir = getInstance().getDirectory(4);
            } else {
                dir = getInstance().getDirectory(0);
            }
        } else if (attach instanceof TLRPC$TL_webDocument) {
            TLRPC$TL_webDocument document2 = (TLRPC$TL_webDocument) attach;
            if (document2.mime_type.startsWith("image/")) {
                dir = getInstance().getDirectory(0);
            } else if (document2.mime_type.startsWith("audio/")) {
                dir = getInstance().getDirectory(1);
            } else if (document2.mime_type.startsWith("video/")) {
                dir = getInstance().getDirectory(2);
            } else {
                dir = getInstance().getDirectory(3);
            }
        }
        if (dir == null) {
            return new File("");
        }
        return new File(dir, getAttachFileName(attach, ext));
    }

    public static TLRPC$PhotoSize getClosestPhotoSizeWithSize(ArrayList<TLRPC$PhotoSize> sizes, int side) {
        return getClosestPhotoSizeWithSize(sizes, side, false);
    }

    public static TLRPC$PhotoSize getClosestPhotoSizeWithSize(ArrayList<TLRPC$PhotoSize> sizes, int side, boolean byMinSide) {
        if (sizes == null || sizes.isEmpty()) {
            return null;
        }
        int lastSide = 0;
        TLRPC$PhotoSize closestObject = null;
        for (int a = 0; a < sizes.size(); a++) {
            TLRPC$PhotoSize obj = (TLRPC$PhotoSize) sizes.get(a);
            if (obj != null) {
                int currentSide;
                if (byMinSide) {
                    currentSide = obj.f77h >= obj.f78w ? obj.f78w : obj.f77h;
                    if (closestObject == null || ((side > 100 && closestObject.location != null && closestObject.location.dc_id == Integer.MIN_VALUE) || (obj instanceof TLRPC$TL_photoCachedSize) || (side > lastSide && lastSide < currentSide))) {
                        closestObject = obj;
                        lastSide = currentSide;
                    }
                } else {
                    currentSide = obj.f78w >= obj.f77h ? obj.f78w : obj.f77h;
                    if (closestObject == null || ((side > 100 && closestObject.location != null && closestObject.location.dc_id == Integer.MIN_VALUE) || (obj instanceof TLRPC$TL_photoCachedSize) || (currentSide <= side && lastSide < currentSide))) {
                        closestObject = obj;
                        lastSide = currentSide;
                    }
                }
            }
        }
        return closestObject;
    }

    public static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(46) + 1);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDocumentFileName(TLRPC$Document document) {
        String fileName = null;
        if (document != null) {
            if (document.file_name != null) {
                fileName = document.file_name;
            } else {
                for (int a = 0; a < document.attributes.size(); a++) {
                    DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(a);
                    if (documentAttribute instanceof TLRPC$TL_documentAttributeFilename) {
                        fileName = documentAttribute.file_name;
                    }
                }
            }
        }
        if (fileName != null) {
            fileName = fileName.replaceAll("[\u0001-\u001f<>:\"/\\\\|?*]+", "").trim();
        }
        return fileName != null ? fileName : "";
    }

    public static String getExtensionByMime(String mime) {
        int index = mime.indexOf(47);
        if (index != -1) {
            return mime.substring(index + 1);
        }
        return "";
    }

    public static File getInternalCacheDir() {
        return ApplicationLoader.applicationContext.getCacheDir();
    }

    public static String getDocumentExtension(TLRPC$Document document) {
        String fileName = getDocumentFileName(document);
        int idx = fileName.lastIndexOf(46);
        String ext = null;
        if (idx != -1) {
            ext = fileName.substring(idx + 1);
        }
        if (ext == null || ext.length() == 0) {
            ext = document.mime_type;
        }
        if (ext == null) {
            ext = "";
        }
        return ext.toUpperCase();
    }

    public static String getAttachFileName(TLObject attach) {
        return getAttachFileName(attach, null);
    }

    public static String getAttachFileName(TLObject attach, String ext) {
        Object obj = -1;
        if (attach instanceof TLRPC$Document) {
            TLRPC$Document document = (TLRPC$Document) attach;
            String docExt = null;
            if (null == null) {
                docExt = getDocumentFileName(document);
                if (docExt != null) {
                    int idx = docExt.lastIndexOf(46);
                    if (idx != -1) {
                        docExt = docExt.substring(idx);
                    }
                }
                docExt = "";
            }
            if (docExt.length() <= 1) {
                if (document.mime_type != null) {
                    String str = document.mime_type;
                    switch (str.hashCode()) {
                        case 187091926:
                            if (str.equals("audio/ogg")) {
                                int i = 1;
                                break;
                            }
                            break;
                        case 1331848029:
                            if (str.equals(MimeTypes.VIDEO_MP4)) {
                                obj = null;
                                break;
                            }
                            break;
                    }
                    switch (obj) {
                        case null:
                            docExt = ".mp4";
                            break;
                        case 1:
                            docExt = ".ogg";
                            break;
                        default:
                            docExt = "";
                            break;
                    }
                }
                docExt = "";
            }
            if (document.version == 0) {
                if (docExt.length() > 1) {
                    return document.dc_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.id + docExt;
                }
                return document.dc_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.id;
            } else if (docExt.length() > 1) {
                return document.dc_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.version + docExt;
            } else {
                return document.dc_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.version;
            }
        } else if (attach instanceof TLRPC$TL_webDocument) {
            TLRPC$TL_webDocument document2 = (TLRPC$TL_webDocument) attach;
            return Utilities.MD5(document2.url) + "." + ImageLoader.getHttpUrlExtension(document2.url, getExtensionByMime(document2.mime_type));
        } else if (attach instanceof TLRPC$PhotoSize) {
            TLRPC$PhotoSize photo = (TLRPC$PhotoSize) attach;
            if (photo.location == null || (photo.location instanceof TLRPC$TL_fileLocationUnavailable)) {
                return "";
            }
            r5 = new StringBuilder().append(photo.location.volume_id).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(photo.location.local_id).append(".");
            if (ext == null) {
                ext = "jpg";
            }
            return r5.append(ext).toString();
        } else if (!(attach instanceof TLRPC$FileLocation)) {
            return "";
        } else {
            if (attach instanceof TLRPC$TL_fileLocationUnavailable) {
                return "";
            }
            TLRPC$FileLocation location = (TLRPC$FileLocation) attach;
            r5 = new StringBuilder().append(location.volume_id).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(location.local_id).append(".");
            if (ext == null) {
                ext = "jpg";
            }
            return r5.append(ext).toString();
        }
    }

    public void deleteFiles(final ArrayList<File> files, final int type) {
        if (files != null && !files.isEmpty()) {
            this.fileLoaderQueue.postRunnable(new Runnable() {
                public void run() {
                    for (int a = 0; a < files.size(); a++) {
                        File file = (File) files.get(a);
                        File encrypted = new File(file.getAbsolutePath() + ".enc");
                        if (encrypted.exists()) {
                            try {
                                if (!encrypted.delete()) {
                                    encrypted.deleteOnExit();
                                }
                            } catch (Exception e) {
                                FileLog.e(e);
                            }
                            try {
                                File key = new File(FileLoader.getInternalCacheDir(), file.getName() + ".enc.key");
                                if (!key.delete()) {
                                    key.deleteOnExit();
                                }
                            } catch (Exception e2) {
                                FileLog.e(e2);
                            }
                        } else if (file.exists()) {
                            try {
                                if (!file.delete()) {
                                    file.deleteOnExit();
                                }
                            } catch (Exception e22) {
                                FileLog.e(e22);
                            }
                        }
                        try {
                            File qFile = new File(file.getParentFile(), "q_" + file.getName());
                            if (qFile.exists() && !qFile.delete()) {
                                qFile.deleteOnExit();
                            }
                        } catch (Exception e222) {
                            FileLog.e(e222);
                        }
                    }
                    if (type == 2) {
                        ImageLoader.getInstance().clearMemory();
                    }
                }
            });
        }
    }

    public void addDelegate(FileLoaderDelegate delegate) {
        synchronized (this) {
            if (this.delegates == null) {
                this.delegates = new ArrayList();
            }
            this.delegates.add(delegate);
        }
    }

    public void addCustomDelegate(FileLoaderDelegateCustomForDownloader delegate) {
        synchronized (this) {
            if (this.customDelegates == null) {
                this.customDelegates = new ArrayList();
            }
            this.customDelegates.add(delegate);
        }
    }

    public static String getMimeType(String url) {
        if (MimeTypeMap.getFileExtensionFromUrl(url).contentEquals("mp3")) {
            return "audio/mp3";
        }
        return "audio/ogg";
    }

    public static String slsGetAttachFileName(TLObject attach, String ext) {
        Object obj = -1;
        if (attach instanceof TLRPC$Document) {
            TLRPC$Document document = (TLRPC$Document) attach;
            String docExt = null;
            if (null == null) {
                docExt = getDocumentFileName(document);
                if (docExt != null) {
                    int idx = docExt.lastIndexOf(46);
                    if (idx != -1) {
                        docExt = docExt.substring(idx);
                    }
                }
                docExt = "";
            }
            if (docExt.length() <= 1) {
                if (document.mime_type != null) {
                    String str = document.mime_type;
                    switch (str.hashCode()) {
                        case 187090231:
                            if (str.equals("audio/mp3")) {
                                obj = 2;
                                break;
                            }
                            break;
                        case 187091926:
                            if (str.equals("audio/ogg")) {
                                int i = 1;
                                break;
                            }
                            break;
                        case 1331848029:
                            if (str.equals(MimeTypes.VIDEO_MP4)) {
                                obj = null;
                                break;
                            }
                            break;
                        case 1504831518:
                            if (str.equals(MimeTypes.AUDIO_MPEG)) {
                                obj = 3;
                                break;
                            }
                            break;
                    }
                    switch (obj) {
                        case null:
                            docExt = ".mp4";
                            break;
                        case 1:
                            docExt = ".ogg";
                            break;
                        case 2:
                            docExt = ".mp3";
                            break;
                        case 3:
                            docExt = ".mp3";
                            break;
                        default:
                            docExt = "";
                            break;
                    }
                }
                docExt = "";
            }
            if (document.version == 0) {
                if (docExt.length() > 1) {
                    return document.dc_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.id + docExt;
                }
                return document.dc_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.id;
            } else if (docExt.length() > 1) {
                return document.dc_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.version + docExt;
            } else {
                return document.dc_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + document.version;
            }
        } else if (attach instanceof TLRPC$TL_webDocument) {
            TLRPC$TL_webDocument document2 = (TLRPC$TL_webDocument) attach;
            return Utilities.MD5(document2.url) + "." + ImageLoader.getHttpUrlExtension(document2.url, getExtensionByMime(document2.mime_type));
        } else if (attach instanceof TLRPC$PhotoSize) {
            TLRPC$PhotoSize photo = (TLRPC$PhotoSize) attach;
            if (photo.location == null || (photo.location instanceof TLRPC$TL_fileLocationUnavailable)) {
                return "";
            }
            r5 = new StringBuilder().append(photo.location.volume_id).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(photo.location.local_id).append(".");
            if (ext == null) {
                ext = "jpg";
            }
            return r5.append(ext).toString();
        } else if (!(attach instanceof TLRPC$FileLocation)) {
            return "";
        } else {
            if (attach instanceof TLRPC$TL_fileLocationUnavailable) {
                return "";
            }
            TLRPC$FileLocation location = (TLRPC$FileLocation) attach;
            r5 = new StringBuilder().append(location.volume_id).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(location.local_id).append(".");
            if (ext == null) {
                ext = "jpg";
            }
            return r5.append(ext).toString();
        }
    }
}
