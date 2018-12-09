package org.telegram.messenger;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputEncryptedFileBigUploaded;
import org.telegram.tgnet.TLRPC$TL_inputEncryptedFileUploaded;
import org.telegram.tgnet.TLRPC$TL_inputFile;
import org.telegram.tgnet.TLRPC$TL_inputFileBig;
import org.telegram.tgnet.TLRPC$TL_upload_saveBigFilePart;
import org.telegram.tgnet.TLRPC$TL_upload_saveFilePart;
import org.telegram.tgnet.TLRPC.InputEncryptedFile;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.TL_boolTrue;
import org.telegram.tgnet.WriteToSocketDelegate;

public class FileUploadOperation {
    private static final int initialRequestsCount = 8;
    private static final int maxUploadingKBytes = 2048;
    private static final int minUploadChunkSize = 64;
    private HashMap<Integer, UploadCachedResult> cachedResults = new HashMap();
    private long currentFileId;
    private int currentPartNum;
    private int currentType;
    private int currentUploadRequetsCount;
    private int currentUploadingBytes;
    private FileUploadOperationDelegate delegate;
    private int estimatedSize;
    private String fileKey;
    private int fingerprint;
    private ArrayList<byte[]> freeRequestIvs;
    private boolean isBigFile;
    private boolean isEncrypted;
    private boolean isLastPart = false;
    private byte[] iv;
    private byte[] ivChange;
    private byte[] key;
    private int lastSavedPartNum;
    private int maxRequestsCount;
    private MessageDigest mdEnc;
    private SharedPreferences preferences;
    private byte[] readBuffer;
    private long readBytesCount;
    private int requestNum;
    private HashMap<Integer, Integer> requestTokens = new HashMap();
    private int saveInfoTimes;
    private boolean started;
    private int state;
    private FileInputStream stream;
    private long totalFileSize;
    private int totalPartsCount;
    private int uploadChunkSize = C3446C.DEFAULT_BUFFER_SEGMENT_SIZE;
    private int uploadStartTime;
    private long uploadedBytesCount;
    private String uploadingFilePath;

    public interface FileUploadOperationDelegate {
        void didChangedUploadProgress(FileUploadOperation fileUploadOperation, float f);

        void didFailedUploadingFile(FileUploadOperation fileUploadOperation);

        void didFinishUploadingFile(FileUploadOperation fileUploadOperation, InputFile inputFile, InputEncryptedFile inputEncryptedFile, byte[] bArr, byte[] bArr2);
    }

    /* renamed from: org.telegram.messenger.FileUploadOperation$1 */
    class C30501 implements Runnable {
        C30501() {
        }

        public void run() {
            int i = 0;
            FileUploadOperation.this.preferences = ApplicationLoader.applicationContext.getSharedPreferences("uploadinfo", 0);
            while (i < 8) {
                FileUploadOperation.this.startUploadRequest();
                i++;
            }
        }
    }

    /* renamed from: org.telegram.messenger.FileUploadOperation$2 */
    class C30512 implements Runnable {
        C30512() {
        }

        public void run() {
            for (Integer intValue : FileUploadOperation.this.requestTokens.values()) {
                ConnectionsManager.getInstance().cancelRequest(intValue.intValue(), true);
            }
        }
    }

    /* renamed from: org.telegram.messenger.FileUploadOperation$5 */
    class C30555 implements WriteToSocketDelegate {

        /* renamed from: org.telegram.messenger.FileUploadOperation$5$1 */
        class C30541 implements Runnable {
            C30541() {
            }

            public void run() {
                if (FileUploadOperation.this.currentUploadRequetsCount < FileUploadOperation.this.maxRequestsCount) {
                    FileUploadOperation.this.startUploadRequest();
                }
            }
        }

        C30555() {
        }

        public void run() {
            Utilities.stageQueue.postRunnable(new C30541());
        }
    }

    private class UploadCachedResult {
        private long bytesOffset;
        private byte[] iv;

        private UploadCachedResult() {
        }
    }

    public FileUploadOperation(String str, boolean z, int i, int i2) {
        this.uploadingFilePath = str;
        this.isEncrypted = z;
        this.estimatedSize = i;
        this.currentType = i2;
    }

    private void cleanup() {
        if (this.preferences == null) {
            this.preferences = ApplicationLoader.applicationContext.getSharedPreferences("uploadinfo", 0);
        }
        this.preferences.edit().remove(this.fileKey + "_time").remove(this.fileKey + "_size").remove(this.fileKey + "_uploaded").remove(this.fileKey + "_id").remove(this.fileKey + "_iv").remove(this.fileKey + "_key").remove(this.fileKey + "_ivc").commit();
        try {
            if (this.stream != null) {
                this.stream.close();
                this.stream = null;
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    private void startUploadRequest() {
        if (this.state == 1) {
            try {
                int i;
                int i2;
                this.started = true;
                if (this.stream == null) {
                    File file = new File(this.uploadingFilePath);
                    this.stream = new FileInputStream(file);
                    if (this.estimatedSize != 0) {
                        this.totalFileSize = (long) this.estimatedSize;
                    } else {
                        this.totalFileSize = file.length();
                    }
                    if (this.totalFileSize > 10485760) {
                        this.isBigFile = true;
                    } else {
                        try {
                            this.mdEnc = MessageDigest.getInstance("MD5");
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                    this.uploadChunkSize = (int) Math.max(64, ((this.totalFileSize + 3072000) - 1) / 3072000);
                    if (1024 % this.uploadChunkSize != 0) {
                        i = 64;
                        while (this.uploadChunkSize > i) {
                            i *= 2;
                        }
                        this.uploadChunkSize = i;
                    }
                    this.maxRequestsCount = 2048 / this.uploadChunkSize;
                    if (this.isEncrypted) {
                        this.freeRequestIvs = new ArrayList(this.maxRequestsCount);
                        for (i = 0; i < this.maxRequestsCount; i++) {
                            this.freeRequestIvs.add(new byte[32]);
                        }
                    }
                    this.uploadChunkSize *= 1024;
                    this.totalPartsCount = ((int) ((this.totalFileSize + ((long) this.uploadChunkSize)) - 1)) / this.uploadChunkSize;
                    this.readBuffer = new byte[this.uploadChunkSize];
                    this.fileKey = Utilities.MD5(this.uploadingFilePath + (this.isEncrypted ? "enc" : TtmlNode.ANONYMOUS_REGION_ID));
                    long j = this.preferences.getLong(this.fileKey + "_size", 0);
                    this.uploadStartTime = (int) (System.currentTimeMillis() / 1000);
                    Object obj = null;
                    if (this.estimatedSize == 0 && j == this.totalFileSize) {
                        this.currentFileId = this.preferences.getLong(this.fileKey + "_id", 0);
                        i = this.preferences.getInt(this.fileKey + "_time", 0);
                        long j2 = this.preferences.getLong(this.fileKey + "_uploaded", 0);
                        if (this.isEncrypted) {
                            String string = this.preferences.getString(this.fileKey + "_iv", null);
                            String string2 = this.preferences.getString(this.fileKey + "_key", null);
                            if (string == null || string2 == null) {
                                obj = 1;
                            } else {
                                this.key = Utilities.hexToBytes(string2);
                                this.iv = Utilities.hexToBytes(string);
                                if (this.key == null || this.iv == null || this.key.length != 32 || this.iv.length != 32) {
                                    obj = 1;
                                } else {
                                    this.ivChange = new byte[32];
                                    System.arraycopy(this.iv, 0, this.ivChange, 0, 32);
                                }
                            }
                        }
                        if (obj != null || i == 0) {
                            obj = 1;
                        } else {
                            if (this.isBigFile && i < this.uploadStartTime - 86400) {
                                i = 0;
                            } else if (!this.isBigFile && ((float) i) < ((float) this.uploadStartTime) - 5400.0f) {
                                i = 0;
                            }
                            if (i != 0) {
                                if (j2 > 0) {
                                    this.readBytesCount = j2;
                                    this.currentPartNum = (int) (j2 / ((long) this.uploadChunkSize));
                                    if (this.isBigFile) {
                                        this.stream.skip(j2);
                                        if (this.isEncrypted) {
                                            Object obj2;
                                            String string3 = this.preferences.getString(this.fileKey + "_ivc", null);
                                            if (string3 != null) {
                                                this.ivChange = Utilities.hexToBytes(string3);
                                                if (this.ivChange == null || this.ivChange.length != 32) {
                                                    obj2 = 1;
                                                    this.readBytesCount = 0;
                                                    this.currentPartNum = 0;
                                                } else {
                                                    obj2 = obj;
                                                }
                                            } else {
                                                obj2 = 1;
                                                this.readBytesCount = 0;
                                                this.currentPartNum = 0;
                                            }
                                            obj = obj2;
                                        }
                                    } else {
                                        for (int i3 = 0; ((long) i3) < this.readBytesCount / ((long) this.uploadChunkSize); i3++) {
                                            int read = this.stream.read(this.readBuffer);
                                            i2 = (!this.isEncrypted || read % 16 == 0) ? 0 : 0 + (16 - (read % 16));
                                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(read + i2);
                                            if (read != this.uploadChunkSize || this.totalPartsCount == this.currentPartNum + 1) {
                                                this.isLastPart = true;
                                            }
                                            nativeByteBuffer.writeBytes(this.readBuffer, 0, read);
                                            if (this.isEncrypted) {
                                                for (i = 0; i < i2; i++) {
                                                    nativeByteBuffer.writeByte(0);
                                                }
                                                Utilities.aesIgeEncryption(nativeByteBuffer.buffer, this.key, this.ivChange, true, true, 0, i2 + read);
                                            }
                                            nativeByteBuffer.rewind();
                                            this.mdEnc.update(nativeByteBuffer.buffer);
                                            nativeByteBuffer.reuse();
                                        }
                                    }
                                } else {
                                    obj = 1;
                                }
                            }
                        }
                    } else {
                        obj = 1;
                    }
                    if (obj != null) {
                        if (this.isEncrypted) {
                            this.iv = new byte[32];
                            this.key = new byte[32];
                            this.ivChange = new byte[32];
                            Utilities.random.nextBytes(this.iv);
                            Utilities.random.nextBytes(this.key);
                            System.arraycopy(this.iv, 0, this.ivChange, 0, 32);
                        }
                        this.currentFileId = Utilities.random.nextLong();
                        if (this.estimatedSize == 0) {
                            storeFileUploadInfo();
                        }
                    }
                    if (this.isEncrypted) {
                        try {
                            MessageDigest instance = MessageDigest.getInstance("MD5");
                            Object obj3 = new byte[64];
                            System.arraycopy(this.key, 0, obj3, 0, 32);
                            System.arraycopy(this.iv, 0, obj3, 32, 32);
                            byte[] digest = instance.digest(obj3);
                            for (i = 0; i < 4; i++) {
                                this.fingerprint |= ((digest[i] ^ digest[i + 4]) & 255) << (i * 8);
                            }
                        } catch (Throwable e2) {
                            FileLog.m13728e(e2);
                        }
                    }
                    this.uploadedBytesCount = this.readBytesCount;
                    this.lastSavedPartNum = this.currentPartNum;
                }
                if (this.estimatedSize != 0) {
                    if (this.readBytesCount + ((long) this.uploadChunkSize) > this.stream.getChannel().size()) {
                        return;
                    }
                }
                int read2 = this.stream.read(this.readBuffer);
                if (read2 != -1) {
                    byte[] bArr;
                    TLObject tLRPC$TL_upload_saveBigFilePart;
                    i2 = (!this.isEncrypted || read2 % 16 == 0) ? 0 : 0 + (16 - (read2 % 16));
                    NativeByteBuffer nativeByteBuffer2 = new NativeByteBuffer(read2 + i2);
                    if (read2 != this.uploadChunkSize || (this.estimatedSize == 0 && this.totalPartsCount == this.currentPartNum + 1)) {
                        this.isLastPart = true;
                    }
                    nativeByteBuffer2.writeBytes(this.readBuffer, 0, read2);
                    if (this.isEncrypted) {
                        for (i = 0; i < i2; i++) {
                            nativeByteBuffer2.writeByte(0);
                        }
                        Utilities.aesIgeEncryption(nativeByteBuffer2.buffer, this.key, this.ivChange, true, true, 0, i2 + read2);
                        byte[] bArr2 = (byte[]) this.freeRequestIvs.get(0);
                        System.arraycopy(this.ivChange, 0, bArr2, 0, 32);
                        this.freeRequestIvs.remove(0);
                        bArr = bArr2;
                    } else {
                        bArr = null;
                    }
                    nativeByteBuffer2.rewind();
                    if (!this.isBigFile) {
                        this.mdEnc.update(nativeByteBuffer2.buffer);
                    }
                    if (this.isBigFile) {
                        tLRPC$TL_upload_saveBigFilePart = new TLRPC$TL_upload_saveBigFilePart();
                        tLRPC$TL_upload_saveBigFilePart.file_part = this.currentPartNum;
                        tLRPC$TL_upload_saveBigFilePart.file_id = this.currentFileId;
                        if (this.estimatedSize != 0) {
                            tLRPC$TL_upload_saveBigFilePart.file_total_parts = -1;
                        } else {
                            tLRPC$TL_upload_saveBigFilePart.file_total_parts = this.totalPartsCount;
                        }
                        tLRPC$TL_upload_saveBigFilePart.bytes = nativeByteBuffer2;
                    } else {
                        tLRPC$TL_upload_saveBigFilePart = new TLRPC$TL_upload_saveFilePart();
                        tLRPC$TL_upload_saveBigFilePart.file_part = this.currentPartNum;
                        tLRPC$TL_upload_saveBigFilePart.file_id = this.currentFileId;
                        tLRPC$TL_upload_saveBigFilePart.bytes = nativeByteBuffer2;
                    }
                    this.readBytesCount += (long) read2;
                    this.currentUploadRequetsCount++;
                    final int i4 = this.requestNum;
                    this.requestNum = i4 + 1;
                    final long j3 = this.readBytesCount;
                    final int i5 = this.currentPartNum;
                    this.currentPartNum = i5 + 1;
                    final int objectSize = tLRPC$TL_upload_saveBigFilePart.getObjectSize() + 4;
                    i2 = read2;
                    this.requestTokens.put(Integer.valueOf(i4), Integer.valueOf(ConnectionsManager.getInstance().sendRequest(tLRPC$TL_upload_saveBigFilePart, new RequestDelegate() {
                        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            int currentNetworkType = tLObject != null ? tLObject.networkType : ConnectionsManager.getCurrentNetworkType();
                            if (FileUploadOperation.this.currentType == ConnectionsManager.FileTypeAudio) {
                                StatsController.getInstance().incrementSentBytesCount(currentNetworkType, 3, (long) objectSize);
                            } else if (FileUploadOperation.this.currentType == ConnectionsManager.FileTypeVideo) {
                                StatsController.getInstance().incrementSentBytesCount(currentNetworkType, 2, (long) objectSize);
                            } else if (FileUploadOperation.this.currentType == 16777216) {
                                StatsController.getInstance().incrementSentBytesCount(currentNetworkType, 4, (long) objectSize);
                            } else if (FileUploadOperation.this.currentType == ConnectionsManager.FileTypeFile) {
                                StatsController.getInstance().incrementSentBytesCount(currentNetworkType, 5, (long) objectSize);
                            }
                            if (bArr != null) {
                                FileUploadOperation.this.freeRequestIvs.add(bArr);
                            }
                            FileUploadOperation.this.requestTokens.remove(Integer.valueOf(i4));
                            if (tLObject instanceof TL_boolTrue) {
                                FileUploadOperation.this.uploadedBytesCount = FileUploadOperation.this.uploadedBytesCount + ((long) i2);
                                FileUploadOperation.this.delegate.didChangedUploadProgress(FileUploadOperation.this, ((float) FileUploadOperation.this.uploadedBytesCount) / ((float) FileUploadOperation.this.totalFileSize));
                                FileUploadOperation.this.currentUploadRequetsCount = FileUploadOperation.this.currentUploadRequetsCount - 1;
                                if (FileUploadOperation.this.isLastPart && FileUploadOperation.this.currentUploadRequetsCount == 0 && FileUploadOperation.this.state == 1) {
                                    FileUploadOperation.this.state = 3;
                                    if (FileUploadOperation.this.key == null) {
                                        InputFile tLRPC$TL_inputFileBig;
                                        if (FileUploadOperation.this.isBigFile) {
                                            tLRPC$TL_inputFileBig = new TLRPC$TL_inputFileBig();
                                        } else {
                                            tLRPC$TL_inputFileBig = new TLRPC$TL_inputFile();
                                            tLRPC$TL_inputFileBig.md5_checksum = String.format(Locale.US, "%32s", new Object[]{new BigInteger(1, FileUploadOperation.this.mdEnc.digest()).toString(16)}).replace(' ', '0');
                                        }
                                        tLRPC$TL_inputFileBig.parts = FileUploadOperation.this.currentPartNum;
                                        tLRPC$TL_inputFileBig.id = FileUploadOperation.this.currentFileId;
                                        tLRPC$TL_inputFileBig.name = FileUploadOperation.this.uploadingFilePath.substring(FileUploadOperation.this.uploadingFilePath.lastIndexOf("/") + 1);
                                        FileUploadOperation.this.delegate.didFinishUploadingFile(FileUploadOperation.this, tLRPC$TL_inputFileBig, null, null, null);
                                        FileUploadOperation.this.cleanup();
                                    } else {
                                        InputEncryptedFile tLRPC$TL_inputEncryptedFileBigUploaded;
                                        if (FileUploadOperation.this.isBigFile) {
                                            tLRPC$TL_inputEncryptedFileBigUploaded = new TLRPC$TL_inputEncryptedFileBigUploaded();
                                        } else {
                                            tLRPC$TL_inputEncryptedFileBigUploaded = new TLRPC$TL_inputEncryptedFileUploaded();
                                            tLRPC$TL_inputEncryptedFileBigUploaded.md5_checksum = String.format(Locale.US, "%32s", new Object[]{new BigInteger(1, FileUploadOperation.this.mdEnc.digest()).toString(16)}).replace(' ', '0');
                                        }
                                        tLRPC$TL_inputEncryptedFileBigUploaded.parts = FileUploadOperation.this.currentPartNum;
                                        tLRPC$TL_inputEncryptedFileBigUploaded.id = FileUploadOperation.this.currentFileId;
                                        tLRPC$TL_inputEncryptedFileBigUploaded.key_fingerprint = FileUploadOperation.this.fingerprint;
                                        FileUploadOperation.this.delegate.didFinishUploadingFile(FileUploadOperation.this, null, tLRPC$TL_inputEncryptedFileBigUploaded, FileUploadOperation.this.key, FileUploadOperation.this.iv);
                                        FileUploadOperation.this.cleanup();
                                    }
                                    if (FileUploadOperation.this.currentType == ConnectionsManager.FileTypeAudio) {
                                        StatsController.getInstance().incrementSentItemsCount(ConnectionsManager.getCurrentNetworkType(), 3, 1);
                                        return;
                                    } else if (FileUploadOperation.this.currentType == ConnectionsManager.FileTypeVideo) {
                                        StatsController.getInstance().incrementSentItemsCount(ConnectionsManager.getCurrentNetworkType(), 2, 1);
                                        return;
                                    } else if (FileUploadOperation.this.currentType == 16777216) {
                                        StatsController.getInstance().incrementSentItemsCount(ConnectionsManager.getCurrentNetworkType(), 4, 1);
                                        return;
                                    } else if (FileUploadOperation.this.currentType == ConnectionsManager.FileTypeFile) {
                                        StatsController.getInstance().incrementSentItemsCount(ConnectionsManager.getCurrentNetworkType(), 5, 1);
                                        return;
                                    } else {
                                        return;
                                    }
                                } else if (FileUploadOperation.this.currentUploadRequetsCount < FileUploadOperation.this.maxRequestsCount) {
                                    if (FileUploadOperation.this.estimatedSize == 0) {
                                        if (FileUploadOperation.this.saveInfoTimes >= 4) {
                                            FileUploadOperation.this.saveInfoTimes = 0;
                                        }
                                        UploadCachedResult uploadCachedResult;
                                        if (i5 == FileUploadOperation.this.lastSavedPartNum) {
                                            FileUploadOperation.this.lastSavedPartNum = FileUploadOperation.this.lastSavedPartNum + 1;
                                            long j = j3;
                                            byte[] bArr = bArr;
                                            while (true) {
                                                uploadCachedResult = (UploadCachedResult) FileUploadOperation.this.cachedResults.get(Integer.valueOf(FileUploadOperation.this.lastSavedPartNum));
                                                if (uploadCachedResult == null) {
                                                    break;
                                                }
                                                j = uploadCachedResult.bytesOffset;
                                                byte[] access$3000 = uploadCachedResult.iv;
                                                FileUploadOperation.this.cachedResults.remove(Integer.valueOf(FileUploadOperation.this.lastSavedPartNum));
                                                FileUploadOperation.this.lastSavedPartNum = FileUploadOperation.this.lastSavedPartNum + 1;
                                                bArr = access$3000;
                                            }
                                            if ((FileUploadOperation.this.isBigFile && j % 1048576 == 0) || (!FileUploadOperation.this.isBigFile && FileUploadOperation.this.saveInfoTimes == 0)) {
                                                Editor edit = FileUploadOperation.this.preferences.edit();
                                                edit.putLong(FileUploadOperation.this.fileKey + "_uploaded", j);
                                                if (FileUploadOperation.this.isEncrypted) {
                                                    edit.putString(FileUploadOperation.this.fileKey + "_ivc", Utilities.bytesToHex(bArr));
                                                }
                                                edit.commit();
                                            }
                                        } else {
                                            uploadCachedResult = new UploadCachedResult();
                                            uploadCachedResult.bytesOffset = j3;
                                            if (bArr != null) {
                                                uploadCachedResult.iv = new byte[32];
                                                System.arraycopy(bArr, 0, uploadCachedResult.iv, 0, 32);
                                            }
                                            FileUploadOperation.this.cachedResults.put(Integer.valueOf(i5), uploadCachedResult);
                                        }
                                        FileUploadOperation.this.saveInfoTimes = FileUploadOperation.this.saveInfoTimes + 1;
                                    }
                                    FileUploadOperation.this.startUploadRequest();
                                    return;
                                } else {
                                    return;
                                }
                            }
                            FileUploadOperation.this.delegate.didFailedUploadingFile(FileUploadOperation.this);
                            FileUploadOperation.this.cleanup();
                        }
                    }, null, new C30555(), 0, Integer.MAX_VALUE, ((i4 % 4) << 16) | 4, true)));
                }
            } catch (Throwable e22) {
                FileLog.m13728e(e22);
                this.delegate.didFailedUploadingFile(this);
                cleanup();
            }
        }
    }

    private void storeFileUploadInfo() {
        Editor edit = this.preferences.edit();
        edit.putInt(this.fileKey + "_time", this.uploadStartTime);
        edit.putLong(this.fileKey + "_size", this.totalFileSize);
        edit.putLong(this.fileKey + "_id", this.currentFileId);
        edit.remove(this.fileKey + "_uploaded");
        if (this.isEncrypted) {
            edit.putString(this.fileKey + "_iv", Utilities.bytesToHex(this.iv));
            edit.putString(this.fileKey + "_ivc", Utilities.bytesToHex(this.ivChange));
            edit.putString(this.fileKey + "_key", Utilities.bytesToHex(this.key));
        }
        edit.commit();
    }

    public void cancel() {
        if (this.state != 3) {
            this.state = 2;
            Utilities.stageQueue.postRunnable(new C30512());
            this.delegate.didFailedUploadingFile(this);
            cleanup();
        }
    }

    protected void checkNewDataAvailable(final long j) {
        Utilities.stageQueue.postRunnable(new Runnable() {
            public void run() {
                if (!(FileUploadOperation.this.estimatedSize == 0 || j == 0)) {
                    FileUploadOperation.this.estimatedSize = 0;
                    FileUploadOperation.this.totalFileSize = j;
                    FileUploadOperation.this.totalPartsCount = ((int) ((FileUploadOperation.this.totalFileSize + ((long) FileUploadOperation.this.uploadChunkSize)) - 1)) / FileUploadOperation.this.uploadChunkSize;
                    if (FileUploadOperation.this.started) {
                        FileUploadOperation.this.storeFileUploadInfo();
                    }
                }
                if (FileUploadOperation.this.currentUploadRequetsCount < FileUploadOperation.this.maxRequestsCount) {
                    FileUploadOperation.this.startUploadRequest();
                }
            }
        });
    }

    public long getTotalFileSize() {
        return this.totalFileSize;
    }

    public void setDelegate(FileUploadOperationDelegate fileUploadOperationDelegate) {
        this.delegate = fileUploadOperationDelegate;
    }

    public void start() {
        if (this.state == 0) {
            this.state = 1;
            Utilities.stageQueue.postRunnable(new C30501());
        }
    }
}
