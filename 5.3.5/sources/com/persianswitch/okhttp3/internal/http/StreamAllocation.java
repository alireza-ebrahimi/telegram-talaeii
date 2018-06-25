package com.persianswitch.okhttp3.internal.http;

import com.persianswitch.okhttp3.Address;
import com.persianswitch.okhttp3.ConnectionPool;
import com.persianswitch.okhttp3.Route;
import com.persianswitch.okhttp3.internal.Internal;
import com.persianswitch.okhttp3.internal.RouteDatabase;
import com.persianswitch.okhttp3.internal.Util;
import com.persianswitch.okhttp3.internal.framed.ErrorCode;
import com.persianswitch.okhttp3.internal.framed.StreamResetException;
import com.persianswitch.okhttp3.internal.io.RealConnection;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public final class StreamAllocation {
    public final Address address;
    private boolean canceled;
    private RealConnection connection;
    private final ConnectionPool connectionPool;
    private int refusedStreamCount;
    private boolean released;
    private Route route;
    private final RouteSelector routeSelector;
    private HttpStream stream;

    public StreamAllocation(ConnectionPool connectionPool, Address address) {
        this.connectionPool = connectionPool;
        this.address = address;
        this.routeSelector = new RouteSelector(address, routeDatabase());
    }

    public HttpStream newStream(int connectTimeout, int readTimeout, int writeTimeout, boolean connectionRetryEnabled, boolean doExtensiveHealthChecks) throws IOException {
        try {
            HttpStream resultStream;
            RealConnection resultConnection = findHealthyConnection(connectTimeout, readTimeout, writeTimeout, connectionRetryEnabled, doExtensiveHealthChecks);
            if (resultConnection.framedConnection != null) {
                resultStream = new Http2xStream(this, resultConnection.framedConnection);
            } else {
                resultConnection.socket().setSoTimeout(readTimeout);
                resultConnection.source.timeout().timeout((long) readTimeout, TimeUnit.MILLISECONDS);
                resultConnection.sink.timeout().timeout((long) writeTimeout, TimeUnit.MILLISECONDS);
                resultStream = new Http1xStream(this, resultConnection.source, resultConnection.sink);
            }
            synchronized (this.connectionPool) {
                this.stream = resultStream;
            }
            return resultStream;
        } catch (IOException e) {
            throw new RouteException(e);
        }
    }

    private RealConnection findHealthyConnection(int connectTimeout, int readTimeout, int writeTimeout, boolean connectionRetryEnabled, boolean doExtensiveHealthChecks) throws IOException {
        RealConnection candidate;
        while (true) {
            candidate = findConnection(connectTimeout, readTimeout, writeTimeout, connectionRetryEnabled);
            synchronized (this.connectionPool) {
                if (candidate.successCount != 0) {
                    if (candidate.isHealthy(doExtensiveHealthChecks)) {
                        break;
                    }
                    noNewStreams();
                } else {
                    break;
                }
            }
        }
        return candidate;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.persianswitch.okhttp3.internal.io.RealConnection findConnection(int r10, int r11, int r12, boolean r13) throws java.io.IOException {
        /*
        r9 = this;
        r2 = r9.connectionPool;
        monitor-enter(r2);
        r1 = r9.released;	 Catch:{ all -> 0x0010 }
        if (r1 == 0) goto L_0x0013;
    L_0x0007:
        r1 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0010 }
        r3 = "released";
        r1.<init>(r3);	 Catch:{ all -> 0x0010 }
        throw r1;	 Catch:{ all -> 0x0010 }
    L_0x0010:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0010 }
        throw r1;
    L_0x0013:
        r1 = r9.stream;	 Catch:{ all -> 0x0010 }
        if (r1 == 0) goto L_0x0020;
    L_0x0017:
        r1 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0010 }
        r3 = "stream != null";
        r1.<init>(r3);	 Catch:{ all -> 0x0010 }
        throw r1;	 Catch:{ all -> 0x0010 }
    L_0x0020:
        r1 = r9.canceled;	 Catch:{ all -> 0x0010 }
        if (r1 == 0) goto L_0x002d;
    L_0x0024:
        r1 = new java.io.IOException;	 Catch:{ all -> 0x0010 }
        r3 = "Canceled";
        r1.<init>(r3);	 Catch:{ all -> 0x0010 }
        throw r1;	 Catch:{ all -> 0x0010 }
    L_0x002d:
        r6 = r9.connection;	 Catch:{ all -> 0x0010 }
        if (r6 == 0) goto L_0x0037;
    L_0x0031:
        r1 = r6.noNewStreams;	 Catch:{ all -> 0x0010 }
        if (r1 != 0) goto L_0x0037;
    L_0x0035:
        monitor-exit(r2);	 Catch:{ all -> 0x0010 }
    L_0x0036:
        return r6;
    L_0x0037:
        r1 = com.persianswitch.okhttp3.internal.Internal.instance;	 Catch:{ all -> 0x0010 }
        r3 = r9.connectionPool;	 Catch:{ all -> 0x0010 }
        r4 = r9.address;	 Catch:{ all -> 0x0010 }
        r7 = r1.get(r3, r4, r9);	 Catch:{ all -> 0x0010 }
        if (r7 == 0) goto L_0x0048;
    L_0x0043:
        r9.connection = r7;	 Catch:{ all -> 0x0010 }
        monitor-exit(r2);	 Catch:{ all -> 0x0010 }
        r6 = r7;
        goto L_0x0036;
    L_0x0048:
        r8 = r9.route;	 Catch:{ all -> 0x0010 }
        monitor-exit(r2);	 Catch:{ all -> 0x0010 }
        if (r8 != 0) goto L_0x005c;
    L_0x004d:
        r1 = r9.routeSelector;
        r8 = r1.next();
        r2 = r9.connectionPool;
        monitor-enter(r2);
        r9.route = r8;	 Catch:{ all -> 0x0080 }
        r1 = 0;
        r9.refusedStreamCount = r1;	 Catch:{ all -> 0x0080 }
        monitor-exit(r2);	 Catch:{ all -> 0x0080 }
    L_0x005c:
        r0 = new com.persianswitch.okhttp3.internal.io.RealConnection;
        r0.<init>(r8);
        r9.acquire(r0);
        r2 = r9.connectionPool;
        monitor-enter(r2);
        r1 = com.persianswitch.okhttp3.internal.Internal.instance;	 Catch:{ all -> 0x007d }
        r3 = r9.connectionPool;	 Catch:{ all -> 0x007d }
        r1.put(r3, r0);	 Catch:{ all -> 0x007d }
        r9.connection = r0;	 Catch:{ all -> 0x007d }
        r1 = r9.canceled;	 Catch:{ all -> 0x007d }
        if (r1 == 0) goto L_0x0083;
    L_0x0074:
        r1 = new java.io.IOException;	 Catch:{ all -> 0x007d }
        r3 = "Canceled";
        r1.<init>(r3);	 Catch:{ all -> 0x007d }
        throw r1;	 Catch:{ all -> 0x007d }
    L_0x007d:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x007d }
        throw r1;
    L_0x0080:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0080 }
        throw r1;
    L_0x0083:
        monitor-exit(r2);	 Catch:{ all -> 0x007d }
        r1 = r9.address;
        r4 = r1.connectionSpecs();
        r1 = r10;
        r2 = r11;
        r3 = r12;
        r5 = r13;
        r0.connect(r1, r2, r3, r4, r5);
        r1 = r9.routeDatabase();
        r2 = r0.route();
        r1.connected(r2);
        r6 = r0;
        goto L_0x0036;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.persianswitch.okhttp3.internal.http.StreamAllocation.findConnection(int, int, int, boolean):com.persianswitch.okhttp3.internal.io.RealConnection");
    }

    public void streamFinished(boolean noNewStreams, HttpStream stream) {
        synchronized (this.connectionPool) {
            if (stream != null) {
                if (stream == this.stream) {
                    if (!noNewStreams) {
                        RealConnection realConnection = this.connection;
                        realConnection.successCount++;
                    }
                }
            }
            throw new IllegalStateException("expected " + this.stream + " but was " + stream);
        }
        deallocate(noNewStreams, false, true);
    }

    public HttpStream stream() {
        HttpStream httpStream;
        synchronized (this.connectionPool) {
            httpStream = this.stream;
        }
        return httpStream;
    }

    private RouteDatabase routeDatabase() {
        return Internal.instance.routeDatabase(this.connectionPool);
    }

    public synchronized RealConnection connection() {
        return this.connection;
    }

    public void release() {
        deallocate(false, true, false);
    }

    public void noNewStreams() {
        deallocate(true, false, false);
    }

    private void deallocate(boolean noNewStreams, boolean released, boolean streamFinished) {
        RealConnection connectionToClose = null;
        synchronized (this.connectionPool) {
            if (streamFinished) {
                this.stream = null;
            }
            if (released) {
                this.released = true;
            }
            if (this.connection != null) {
                if (noNewStreams) {
                    this.connection.noNewStreams = true;
                }
                if (this.stream == null && (this.released || this.connection.noNewStreams)) {
                    release(this.connection);
                    if (this.connection.allocations.isEmpty()) {
                        this.connection.idleAtNanos = System.nanoTime();
                        if (Internal.instance.connectionBecameIdle(this.connectionPool, this.connection)) {
                            connectionToClose = this.connection;
                        }
                    }
                    this.connection = null;
                }
            }
        }
        if (connectionToClose != null) {
            Util.closeQuietly(connectionToClose.socket());
        }
    }

    public void cancel() {
        synchronized (this.connectionPool) {
            this.canceled = true;
            HttpStream streamToCancel = this.stream;
            RealConnection connectionToCancel = this.connection;
        }
        if (streamToCancel != null) {
            streamToCancel.cancel();
        } else if (connectionToCancel != null) {
            connectionToCancel.cancel();
        }
    }

    public void streamFailed(IOException e) {
        boolean noNewStreams = false;
        synchronized (this.connectionPool) {
            if (e instanceof StreamResetException) {
                StreamResetException streamResetException = (StreamResetException) e;
                if (streamResetException.errorCode == ErrorCode.REFUSED_STREAM) {
                    this.refusedStreamCount++;
                }
                if (streamResetException.errorCode != ErrorCode.REFUSED_STREAM || this.refusedStreamCount > 1) {
                    noNewStreams = true;
                    this.route = null;
                }
            } else if (!(this.connection == null || this.connection.isMultiplexed())) {
                noNewStreams = true;
                if (this.connection.successCount == 0) {
                    if (!(this.route == null || e == null)) {
                        this.routeSelector.connectFailed(this.route, e);
                    }
                    this.route = null;
                }
            }
        }
        deallocate(noNewStreams, false, true);
    }

    public void acquire(RealConnection connection) {
        connection.allocations.add(new WeakReference(this));
    }

    private void release(RealConnection connection) {
        int size = connection.allocations.size();
        for (int i = 0; i < size; i++) {
            if (((Reference) connection.allocations.get(i)).get() == this) {
                connection.allocations.remove(i);
                return;
            }
        }
        throw new IllegalStateException();
    }

    public boolean hasMoreRoutes() {
        return this.route != null || this.routeSelector.hasNext();
    }

    public String toString() {
        return this.address.toString();
    }
}
