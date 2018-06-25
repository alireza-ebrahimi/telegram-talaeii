package org.telegram.messenger;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$InputEncryptedFile;
import org.telegram.tgnet.TLRPC$InputFile;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputEncryptedFileBigUploaded;
import org.telegram.tgnet.TLRPC$TL_inputEncryptedFileUploaded;
import org.telegram.tgnet.TLRPC$TL_inputFile;
import org.telegram.tgnet.TLRPC$TL_inputFileBig;
import org.telegram.tgnet.TLRPC$TL_upload_saveBigFilePart;
import org.telegram.tgnet.TLRPC$TL_upload_saveFilePart;
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
    private int uploadChunkSize = 65536;
    private int uploadStartTime;
    private long uploadedBytesCount;
    private String uploadingFilePath;

    public interface FileUploadOperationDelegate {
        void didChangedUploadProgress(FileUploadOperation fileUploadOperation, float f);

        void didFailedUploadingFile(FileUploadOperation fileUploadOperation);

        void didFinishUploadingFile(FileUploadOperation fileUploadOperation, TLRPC$InputFile tLRPC$InputFile, TLRPC$InputEncryptedFile tLRPC$InputEncryptedFile, byte[] bArr, byte[] bArr2);
    }

    /* renamed from: org.telegram.messenger.FileUploadOperation$1 */
    class C13391 implements Runnable {
        C13391() {
        }

        public void run() {
            FileUploadOperation.this.preferences = ApplicationLoader.applicationContext.getSharedPreferences("uploadinfo", 0);
            for (int a = 0; a < 8; a++) {
                FileUploadOperation.this.startUploadRequest();
            }
        }
    }

    /* renamed from: org.telegram.messenger.FileUploadOperation$2 */
    class C13402 implements Runnable {
        C13402() {
        }

        public void run() {
            for (Integer num : FileUploadOperation.this.requestTokens.values()) {
                ConnectionsManager.getInstance().cancelRequest(num.intValue(), true);
            }
        }
    }

    /* renamed from: org.telegram.messenger.FileUploadOperation$5 */
    class C13445 implements WriteToSocketDelegate {

        /* renamed from: org.telegram.messenger.FileUploadOperation$5$1 */
        class C13431 implements Runnable {
            C13431() {
            }

            public void run() {
                if (FileUploadOperation.this.currentUploadRequetsCount < FileUploadOperation.this.maxRequestsCount) {
                    FileUploadOperation.this.startUploadRequest();
                }
            }
        }

        C13445() {
        }

        public void run() {
            Utilities.stageQueue.postRunnable(new C13431());
        }
    }

    private class UploadCachedResult {
        private long bytesOffset;
        private byte[] iv;

        private UploadCachedResult() {
        }
    }

    public FileUploadOperation(String location, boolean encrypted, int estimated, int type) {
        this.uploadingFilePath = location;
        this.isEncrypted = encrypted;
        this.estimatedSize = estimated;
        this.currentType = type;
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
            Utilities.stageQueue.postRunnable(new C13391());
        }
    }

    public void cancel() {
        if (this.state != 3) {
            this.state = 2;
            Utilities.stageQueue.postRunnable(new C13402());
            this.delegate.didFailedUploadingFile(this);
            cleanup();
        }
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
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    protected void checkNewDataAvailable(final long finalSize) {
        Utilities.stageQueue.postRunnable(new Runnable() {
            public void run() {
                if (!(FileUploadOperation.this.estimatedSize == 0 || finalSize == 0)) {
                    FileUploadOperation.this.estimatedSize = 0;
                    FileUploadOperation.this.totalFileSize = finalSize;
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

    private void storeFileUploadInfo() {
        Editor editor = this.preferences.edit();
        editor.putInt(this.fileKey + "_time", this.uploadStartTime);
        editor.putLong(this.fileKey + "_size", this.totalFileSize);
        editor.putLong(this.fileKey + "_id", this.currentFileId);
        editor.remove(this.fileKey + "_uploaded");
        if (this.isEncrypted) {
            editor.putString(this.fileKey + "_iv", Utilities.bytesToHex(this.iv));
            editor.putString(this.fileKey + "_ivc", Utilities.bytesToHex(this.ivChange));
            editor.putString(this.fileKey + "_key", Utilities.bytesToHex(this.key));
        }
        editor.commit();
    }

    private void startUploadRequest() {
        if (this.state == 1) {
            try {
                int a;
                int toAdd;
                NativeByteBuffer nativeByteBuffer;
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
                        } catch (NoSuchAlgorithmException e) {
                            FileLog.e(e);
                        }
                    }
                    this.uploadChunkSize = (int) Math.max(64, ((this.totalFileSize + 3072000) - 1) / 3072000);
                    if (1024 % this.uploadChunkSize != 0) {
                        int chunkSize = 64;
                        while (this.uploadChunkSize > chunkSize) {
                            chunkSize *= 2;
                        }
                        this.uploadChunkSize = chunkSize;
                    }
                    this.maxRequestsCount = 2048 / this.uploadChunkSize;
                    if (this.isEncrypted) {
                        this.freeRequestIvs = new ArrayList(this.maxRequestsCount);
                        for (a = 0; a < this.maxRequestsCount; a++) {
                            this.freeRequestIvs.add(new byte[32]);
                        }
                    }
                    this.uploadChunkSize *= 1024;
                    this.totalPartsCount = ((int) ((this.totalFileSize + ((long) this.uploadChunkSize)) - 1)) / this.uploadChunkSize;
                    this.readBuffer = new byte[this.uploadChunkSize];
                    this.fileKey = Utilities.MD5(this.uploadingFilePath + (this.isEncrypted ? "enc" : ""));
                    long fileSize = this.preferences.getLong(this.fileKey + "_size", 0);
                    this.uploadStartTime = (int) (System.currentTimeMillis() / 1000);
                    boolean rewrite = false;
                    if (this.estimatedSize == 0 && fileSize == this.totalFileSize) {
                        this.currentFileId = this.preferences.getLong(this.fileKey + "_id", 0);
                        int date = this.preferences.getInt(this.fileKey + "_time", 0);
                        long uploadedSize = this.preferences.getLong(this.fileKey + "_uploaded", 0);
                        if (this.isEncrypted) {
                            String ivString = this.preferences.getString(this.fileKey + "_iv", null);
                            String keyString = this.preferences.getString(this.fileKey + "_key", null);
                            if (ivString == null || keyString == null) {
                                rewrite = true;
                            } else {
                                this.key = Utilities.hexToBytes(keyString);
                                this.iv = Utilities.hexToBytes(ivString);
                                if (this.key == null || this.iv == null || this.key.length != 32 || this.iv.length != 32) {
                                    rewrite = true;
                                } else {
                                    this.ivChange = new byte[32];
                                    System.arraycopy(this.iv, 0, this.ivChange, 0, 32);
                                }
                            }
                        }
                        if (rewrite || date == 0) {
                            rewrite = true;
                        } else {
                            if (this.isBigFile && date < this.uploadStartTime - 86400) {
                                date = 0;
                            } else if (!this.isBigFile && ((float) date) < ((float) this.uploadStartTime) - 5400.0f) {
                                date = 0;
                            }
                            if (date != 0) {
                                if (uploadedSize > 0) {
                                    this.readBytesCount = uploadedSize;
                                    this.currentPartNum = (int) (uploadedSize / ((long) this.uploadChunkSize));
                                    if (this.isBigFile) {
                                        this.stream.skip(uploadedSize);
                                        if (this.isEncrypted) {
                                            String ivcString = this.preferences.getString(this.fileKey + "_ivc", null);
                                            if (ivcString != null) {
                                                this.ivChange = Utilities.hexToBytes(ivcString);
                                                if (this.ivChange == null || this.ivChange.length != 32) {
                                                    rewrite = true;
                                                    this.readBytesCount = 0;
                                                    this.currentPartNum = 0;
                                                }
                                            } else {
                                                rewrite = true;
                                                this.readBytesCount = 0;
                                                this.currentPartNum = 0;
                                            }
                                        }
                                    } else {
                                        for (int b = 0; ((long) b) < this.readBytesCount / ((long) this.uploadChunkSize); b++) {
                                            int bytesRead = this.stream.read(this.readBuffer);
                                            toAdd = 0;
                                            if (this.isEncrypted && bytesRead % 16 != 0) {
                                                toAdd = 0 + (16 - (bytesRead % 16));
                                            }
                                            nativeByteBuffer = new NativeByteBuffer(bytesRead + toAdd);
                                            if (bytesRead != this.uploadChunkSize || this.totalPartsCount == this.currentPartNum + 1) {
                                                this.isLastPart = true;
                                            }
                                            nativeByteBuffer.writeBytes(this.readBuffer, 0, bytesRead);
                                            if (this.isEncrypted) {
                                                for (a = 0; a < toAdd; a++) {
                                                    nativeByteBuffer.writeByte(0);
                                                }
                                                Utilities.aesIgeEncryption(nativeByteBuffer.buffer, this.key, this.ivChange, true, true, 0, bytesRead + toAdd);
                                            }
                                            nativeByteBuffer.rewind();
                                            this.mdEnc.update(nativeByteBuffer.buffer);
                                            nativeByteBuffer.reuse();
                                        }
                                    }
                                } else {
                                    rewrite = true;
                                }
                            }
                        }
                    } else {
                        rewrite = true;
                    }
                    if (rewrite) {
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
                            MessageDigest md = MessageDigest.getInstance("MD5");
                            Object arr = new byte[64];
                            System.arraycopy(this.key, 0, arr, 0, 32);
                            System.arraycopy(this.iv, 0, arr, 32, 32);
                            byte[] digest = md.digest(arr);
                            for (a = 0; a < 4; a++) {
                                this.fingerprint |= ((digest[a] ^ digest[a + 4]) & 255) << (a * 8);
                            }
                        } catch (Exception e2) {
                            FileLog.e(e2);
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
                int currentRequestBytes = this.stream.read(this.readBuffer);
                if (currentRequestBytes != -1) {
                    byte[] currentRequestIv;
                    TLObject finalRequest;
                    toAdd = 0;
                    if (this.isEncrypted && currentRequestBytes % 16 != 0) {
                        toAdd = 0 + (16 - (currentRequestBytes % 16));
                    }
                    nativeByteBuffer = new NativeByteBuffer(currentRequestBytes + toAdd);
                    if (currentRequestBytes != this.uploadChunkSize || (this.estimatedSize == 0 && this.totalPartsCount == this.currentPartNum + 1)) {
                        this.isLastPart = true;
                    }
                    nativeByteBuffer.writeBytes(this.readBuffer, 0, currentRequestBytes);
                    if (this.isEncrypted) {
                        for (a = 0; a < toAdd; a++) {
                            nativeByteBuffer.writeByte(0);
                        }
                        Utilities.aesIgeEncryption(nativeByteBuffer.buffer, this.key, this.ivChange, true, true, 0, currentRequestBytes + toAdd);
                        currentRequestIv = (byte[]) this.freeRequestIvs.get(0);
                        System.arraycopy(this.ivChange, 0, currentRequestIv, 0, 32);
                        this.freeRequestIvs.remove(0);
                    } else {
                        currentRequestIv = null;
                    }
                    nativeByteBuffer.rewind();
                    if (!this.isBigFile) {
                        this.mdEnc.update(nativeByteBuffer.buffer);
                    }
                    TLObject req;
                    if (this.isBigFile) {
                        req = new TLRPC$TL_upload_saveBigFilePart();
                        req.file_part = this.currentPartNum;
                        req.file_id = this.currentFileId;
                        if (this.estimatedSize != 0) {
                            req.file_total_parts = -1;
                        } else {
                            req.file_total_parts = this.totalPartsCount;
                        }
                        req.bytes = nativeByteBuffer;
                        finalRequest = req;
                    } else {
                        req = new TLRPC$TL_upload_saveFilePart();
                        req.file_part = this.currentPartNum;
                        req.file_id = this.currentFileId;
                        req.bytes = nativeByteBuffer;
                        finalRequest = req;
                    }
                    this.readBytesCount += (long) currentRequestBytes;
                    this.currentUploadRequetsCount++;
                    final int requestNumFinal = this.requestNum;
                    this.requestNum = requestNumFinal + 1;
                    final long currentRequestBytesOffset = this.readBytesCount;
                    final int currentRequestPartNum = this.currentPartNum;
                    this.currentPartNum = currentRequestPartNum + 1;
                    final int requestSize = finalRequest.getObjectSize() + 4;
                    final int i = currentRequestBytes;
                    this.requestTokens.put(Integer.valueOf(requestNumFinal), Integer.valueOf(ConnectionsManager.getInstance().sendRequest(finalRequest, new RequestDelegate() {
                        public void run(TLObject response, TLRPC$TL_error error) {
                            int networkType = response != null ? response.networkType : ConnectionsManager.getCurrentNetworkType();
                            if (FileUploadOperation.this.currentType == ConnectionsManager.FileTypeAudio) {
                                StatsController.getInstance().incrementSentBytesCount(networkType, 3, (long) requestSize);
                            } else if (FileUploadOperation.this.currentType == ConnectionsManager.FileTypeVideo) {
                                StatsController.getInstance().incrementSentBytesCount(networkType, 2, (long) requestSize);
                            } else if (FileUploadOperation.this.currentType == 16777216) {
                                StatsController.getInstance().incrementSentBytesCount(networkType, 4, (long) requestSize);
                            } else if (FileUploadOperation.this.currentType == ConnectionsManager.FileTypeFile) {
                                StatsController.getInstance().incrementSentBytesCount(networkType, 5, (long) requestSize);
                            }
                            if (currentRequestIv != null) {
                                FileUploadOperation.this.freeRequestIvs.add(currentRequestIv);
                            }
                            FileUploadOperation.this.requestTokens.remove(Integer.valueOf(requestNumFinal));
                            if (response instanceof TLRPC$TL_boolTrue) {
                                FileUploadOperation.this.uploadedBytesCount = FileUploadOperation.this.uploadedBytesCount + ((long) i);
                                FileUploadOperation.this.delegate.didChangedUploadProgress(FileUploadOperation.this, ((float) FileUploadOperation.this.uploadedBytesCount) / ((float) FileUploadOperation.this.totalFileSize));
                                FileUploadOperation.this.currentUploadRequetsCount = FileUploadOperation.this.currentUploadRequetsCount - 1;
                                if (FileUploadOperation.this.isLastPart && FileUploadOperation.this.currentUploadRequetsCount == 0 && FileUploadOperation.this.state == 1) {
                                    FileUploadOperation.this.state = 3;
                                    if (FileUploadOperation.this.key == null) {
                                        TLRPC$InputFile result;
                                        if (FileUploadOperation.this.isBigFile) {
                                            result = new TLRPC$TL_inputFileBig();
                                        } else {
                                            result = new TLRPC$TL_inputFile();
                                            result.md5_checksum = String.format(Locale.US, "%32s", new Object[]{new BigInteger(1, FileUploadOperation.this.mdEnc.digest()).toString(16)}).replace(' ', '0');
                                        }
                                        result.parts = FileUploadOperation.this.currentPartNum;
                                        result.id = FileUploadOperation.this.currentFileId;
                                        result.name = FileUploadOperation.this.uploadingFilePath.substring(FileUploadOperation.this.uploadingFilePath.lastIndexOf("/") + 1);
                                        FileUploadOperation.this.delegate.didFinishUploadingFile(FileUploadOperation.this, result, null, null, null);
                                        FileUploadOperation.this.cleanup();
                                    } else {
                                        TLRPC$InputEncryptedFile result2;
                                        if (FileUploadOperation.this.isBigFile) {
                                            result2 = new TLRPC$TL_inputEncryptedFileBigUploaded();
                                        } else {
                                            result2 = new TLRPC$TL_inputEncryptedFileUploaded();
                                            result2.md5_checksum = String.format(Locale.US, "%32s", new Object[]{new BigInteger(1, FileUploadOperation.this.mdEnc.digest()).toString(16)}).replace(' ', '0');
                                        }
                                        result2.parts = FileUploadOperation.this.currentPartNum;
                                        result2.id = FileUploadOperation.this.currentFileId;
                                        result2.key_fingerprint = FileUploadOperation.this.fingerprint;
                                        FileUploadOperation.this.delegate.didFinishUploadingFile(FileUploadOperation.this, null, result2, FileUploadOperation.this.key, FileUploadOperation.this.iv);
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
                                        UploadCachedResult result3;
                                        if (currentRequestPartNum == FileUploadOperation.this.lastSavedPartNum) {
                                            FileUploadOperation.this.lastSavedPartNum = FileUploadOperation.this.lastSavedPartNum + 1;
                                            long offsetToSave = currentRequestBytesOffset;
                                            byte[] ivToSave = currentRequestIv;
                                            while (true) {
                                                result3 = (UploadCachedResult) FileUploadOperation.this.cachedResults.get(Integer.valueOf(FileUploadOperation.this.lastSavedPartNum));
                                                if (result3 == null) {
                                                    break;
                                                }
                                                offsetToSave = result3.bytesOffset;
                                                ivToSave = result3.iv;
                                                FileUploadOperation.this.cachedResults.remove(Integer.valueOf(FileUploadOperation.this.lastSavedPartNum));
                                                FileUploadOperation.this.lastSavedPartNum = FileUploadOperation.this.lastSavedPartNum + 1;
                                            }
                                            if ((FileUploadOperation.this.isBigFile && offsetToSave % 1048576 == 0) || (!FileUploadOperation.this.isBigFile && FileUploadOperation.this.saveInfoTimes == 0)) {
                                                Editor editor = FileUploadOperation.this.preferences.edit();
                                                editor.putLong(FileUploadOperation.this.fileKey + "_uploaded", offsetToSave);
                                                if (FileUploadOperation.this.isEncrypted) {
                                                    editor.putString(FileUploadOperation.this.fileKey + "_ivc", Utilities.bytesToHex(ivToSave));
                                                }
                                                editor.commit();
                                            }
                                        } else {
                                            result3 = new UploadCachedResult();
                                            result3.bytesOffset = currentRequestBytesOffset;
                                            if (currentRequestIv != null) {
                                                result3.iv = new byte[32];
                                                System.arraycopy(currentRequestIv, 0, result3.iv, 0, 32);
                                            }
                                            FileUploadOperation.this.cachedResults.put(Integer.valueOf(currentRequestPartNum), result3);
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
                    }, null, new C13445(), 0, Integer.MAX_VALUE, ((requestNumFinal % 4) << 16) | 4, true)));
                }
            } catch (Exception e22) {
                FileLog.e(e22);
                this.delegate.didFailedUploadingFile(this);
                cleanup();
            }
        }
    }
}
