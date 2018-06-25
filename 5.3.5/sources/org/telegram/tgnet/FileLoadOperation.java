package org.telegram.tgnet;

import java.io.File;

public class FileLoadOperation {
    private int address;
    private FileLoadOperationDelegate delegate;
    private boolean isForceRequest;
    private boolean started;

    public static native void native_cancelLoadOperation(int i);

    public static native int native_createLoadOpetation(int i, long j, long j2, long j3, int i2, byte[] bArr, byte[] bArr2, String str, int i3, int i4, String str2, String str3, Object obj);

    public static native void native_startLoadOperation(int i);

    public FileLoadOperation(int dc_id, long id, long volume_id, long access_hash, int local_id, byte[] encKey, byte[] encIv, String extension, int version, int size, File dest, File temp, FileLoadOperationDelegate fileLoadOperationDelegate) {
        this.address = native_createLoadOpetation(dc_id, id, volume_id, access_hash, local_id, encKey, encIv, extension, version, size, dest.getAbsolutePath(), temp.getAbsolutePath(), fileLoadOperationDelegate);
        this.delegate = fileLoadOperationDelegate;
    }

    public void setForceRequest(boolean forceRequest) {
        this.isForceRequest = forceRequest;
    }

    public boolean isForceRequest() {
        return this.isForceRequest;
    }

    public void start() {
        if (!this.started) {
            if (this.address == 0) {
                this.delegate.onFailed(0);
                return;
            }
            this.started = true;
            native_startLoadOperation(this.address);
        }
    }

    public void cancel() {
        if (this.started && this.address != 0) {
            native_cancelLoadOperation(this.address);
        }
    }

    public boolean wasStarted() {
        return this.started;
    }
}
