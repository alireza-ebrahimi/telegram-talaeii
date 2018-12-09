package org.telegram.messenger.exoplayer2.util;

public final class LibraryLoader {
    private boolean isAvailable;
    private boolean loadAttempted;
    private String[] nativeLibraries;

    public LibraryLoader(String... strArr) {
        this.nativeLibraries = strArr;
    }

    public synchronized boolean isAvailable() {
        boolean z;
        if (this.loadAttempted) {
            z = this.isAvailable;
        } else {
            this.loadAttempted = true;
            try {
                for (String loadLibrary : this.nativeLibraries) {
                    System.loadLibrary(loadLibrary);
                }
                this.isAvailable = true;
            } catch (UnsatisfiedLinkError e) {
            }
            z = this.isAvailable;
        }
        return z;
    }

    public synchronized void setLibraries(String... strArr) {
        Assertions.checkState(!this.loadAttempted, "Cannot set libraries after loading");
        this.nativeLibraries = strArr;
    }
}
