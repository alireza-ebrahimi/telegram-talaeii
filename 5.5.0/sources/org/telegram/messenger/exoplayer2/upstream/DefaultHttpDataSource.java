package org.telegram.messenger.exoplayer2.upstream;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.upstream.HttpDataSource.HttpDataSourceException;
import org.telegram.messenger.exoplayer2.upstream.HttpDataSource.InvalidContentTypeException;
import org.telegram.messenger.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException;
import org.telegram.messenger.exoplayer2.upstream.HttpDataSource.RequestProperties;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Predicate;
import org.telegram.messenger.exoplayer2.util.Util;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

public class DefaultHttpDataSource implements HttpDataSource {
    private static final Pattern CONTENT_RANGE_HEADER = Pattern.compile("^bytes (\\d+)-(\\d+)/(\\d+)$");
    public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 8000;
    public static final int DEFAULT_READ_TIMEOUT_MILLIS = 8000;
    private static final long MAX_BYTES_TO_DRAIN = 2048;
    private static final int MAX_REDIRECTS = 20;
    private static final String TAG = "DefaultHttpDataSource";
    private static final AtomicReference<byte[]> skipBufferReference = new AtomicReference();
    private final boolean allowCrossProtocolRedirects;
    private long bytesRead;
    private long bytesSkipped;
    private long bytesToRead;
    private long bytesToSkip;
    private final int connectTimeoutMillis;
    private HttpURLConnection connection;
    private final Predicate<String> contentTypePredicate;
    private DataSpec dataSpec;
    private final RequestProperties defaultRequestProperties;
    private InputStream inputStream;
    private final TransferListener<? super DefaultHttpDataSource> listener;
    private boolean opened;
    private final int readTimeoutMillis;
    private final RequestProperties requestProperties;
    private final String userAgent;

    public DefaultHttpDataSource(String str, Predicate<String> predicate) {
        this(str, predicate, null);
    }

    public DefaultHttpDataSource(String str, Predicate<String> predicate, TransferListener<? super DefaultHttpDataSource> transferListener) {
        this(str, predicate, transferListener, 8000, 8000);
    }

    public DefaultHttpDataSource(String str, Predicate<String> predicate, TransferListener<? super DefaultHttpDataSource> transferListener, int i, int i2) {
        this(str, predicate, transferListener, i, i2, false, null);
    }

    public DefaultHttpDataSource(String str, Predicate<String> predicate, TransferListener<? super DefaultHttpDataSource> transferListener, int i, int i2, boolean z, RequestProperties requestProperties) {
        this.userAgent = Assertions.checkNotEmpty(str);
        this.contentTypePredicate = predicate;
        this.listener = transferListener;
        this.requestProperties = new RequestProperties();
        this.connectTimeoutMillis = i;
        this.readTimeoutMillis = i2;
        this.allowCrossProtocolRedirects = z;
        this.defaultRequestProperties = requestProperties;
    }

    private void closeConnectionQuietly() {
        if (this.connection != null) {
            try {
                this.connection.disconnect();
            } catch (Throwable e) {
                Log.e(TAG, "Unexpected error while disconnecting", e);
            }
            this.connection = null;
        }
    }

    private static long getContentLength(HttpURLConnection httpURLConnection) {
        long j = -1;
        String headerField = httpURLConnection.getHeaderField("Content-Length");
        if (!TextUtils.isEmpty(headerField)) {
            try {
                j = Long.parseLong(headerField);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Unexpected Content-Length [" + headerField + "]");
            }
        }
        String headerField2 = httpURLConnection.getHeaderField("Content-Range");
        if (TextUtils.isEmpty(headerField2)) {
            return j;
        }
        Matcher matcher = CONTENT_RANGE_HEADER.matcher(headerField2);
        if (!matcher.find()) {
            return j;
        }
        try {
            long parseLong = (Long.parseLong(matcher.group(2)) - Long.parseLong(matcher.group(1))) + 1;
            if (j < 0) {
                return parseLong;
            }
            if (j == parseLong) {
                return j;
            }
            Log.w(TAG, "Inconsistent headers [" + headerField + "] [" + headerField2 + "]");
            return Math.max(j, parseLong);
        } catch (NumberFormatException e2) {
            Log.e(TAG, "Unexpected Content-Range [" + headerField2 + "]");
            return j;
        }
    }

    private static URL handleRedirect(URL url, String str) {
        if (str == null) {
            throw new ProtocolException("Null location redirect");
        }
        URL url2 = new URL(url, str);
        String protocol = url2.getProtocol();
        if ("https".equals(protocol) || "http".equals(protocol)) {
            return url2;
        }
        throw new ProtocolException("Unsupported protocol redirect: " + protocol);
    }

    private HttpURLConnection makeConnection(URL url, byte[] bArr, long j, long j2, boolean z, boolean z2) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(this.connectTimeoutMillis);
        httpURLConnection.setReadTimeout(this.readTimeoutMillis);
        if (this.defaultRequestProperties != null) {
            for (Entry entry : this.defaultRequestProperties.getSnapshot().entrySet()) {
                httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
        }
        for (Entry entry2 : this.requestProperties.getSnapshot().entrySet()) {
            httpURLConnection.setRequestProperty((String) entry2.getKey(), (String) entry2.getValue());
        }
        if (!(j == 0 && j2 == -1)) {
            String str = "bytes=" + j + "-";
            if (j2 != -1) {
                str = str + ((j + j2) - 1);
            }
            httpURLConnection.setRequestProperty("Range", str);
        }
        httpURLConnection.setRequestProperty("User-Agent", this.userAgent);
        if (!z) {
            httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
        }
        httpURLConnection.setInstanceFollowRedirects(z2);
        httpURLConnection.setDoOutput(bArr != null);
        if (bArr != null) {
            httpURLConnection.setRequestMethod("POST");
            if (bArr.length == 0) {
                httpURLConnection.connect();
            } else {
                httpURLConnection.setFixedLengthStreamingMode(bArr.length);
                httpURLConnection.connect();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(bArr);
                outputStream.close();
            }
        } else {
            httpURLConnection.connect();
        }
        return httpURLConnection;
    }

    private HttpURLConnection makeConnection(DataSpec dataSpec) {
        URL url = new URL(dataSpec.uri.toString());
        byte[] bArr = dataSpec.postBody;
        long j = dataSpec.position;
        long j2 = dataSpec.length;
        boolean isFlagSet = dataSpec.isFlagSet(1);
        if (!this.allowCrossProtocolRedirects) {
            return makeConnection(url, bArr, j, j2, isFlagSet, true);
        }
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (i <= 20) {
                HttpURLConnection makeConnection = makeConnection(url, bArr, j, j2, isFlagSet, false);
                int responseCode = makeConnection.getResponseCode();
                if (!(responseCode == 300 || responseCode == 301 || responseCode == 302 || responseCode == 303)) {
                    if (bArr != null) {
                        return makeConnection;
                    }
                    if (!(responseCode == 307 || responseCode == 308)) {
                        return makeConnection;
                    }
                }
                bArr = null;
                String headerField = makeConnection.getHeaderField("Location");
                makeConnection.disconnect();
                url = handleRedirect(url, headerField);
                i = i2;
            } else {
                throw new NoRouteToHostException("Too many redirects: " + i2);
            }
        }
    }

    private static void maybeTerminateInputStream(HttpURLConnection httpURLConnection, long j) {
        if (Util.SDK_INT == 19 || Util.SDK_INT == 20) {
            try {
                InputStream inputStream = httpURLConnection.getInputStream();
                if (j == -1) {
                    if (inputStream.read() == -1) {
                        return;
                    }
                } else if (j <= MAX_BYTES_TO_DRAIN) {
                    return;
                }
                String name = inputStream.getClass().getName();
                if (name.equals("com.android.okhttp.internal.http.HttpTransport$ChunkedInputStream") || name.equals("com.android.okhttp.internal.http.HttpTransport$FixedLengthInputStream")) {
                    Method declaredMethod = inputStream.getClass().getSuperclass().getDeclaredMethod("unexpectedEndOfInput", new Class[0]);
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(inputStream, new Object[0]);
                }
            } catch (Exception e) {
            }
        }
    }

    private int readInternal(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return 0;
        }
        if (this.bytesToRead != -1) {
            long j = this.bytesToRead - this.bytesRead;
            if (j == 0) {
                return -1;
            }
            i2 = (int) Math.min((long) i2, j);
        }
        int read = this.inputStream.read(bArr, i, i2);
        if (read != -1) {
            this.bytesRead += (long) read;
            if (this.listener != null) {
                this.listener.onBytesTransferred(this, read);
            }
            return read;
        } else if (this.bytesToRead == -1) {
            return -1;
        } else {
            throw new EOFException();
        }
    }

    private void skipInternal() {
        if (this.bytesSkipped != this.bytesToSkip) {
            Object obj = (byte[]) skipBufferReference.getAndSet(null);
            if (obj == null) {
                obj = new byte[4096];
            }
            while (this.bytesSkipped != this.bytesToSkip) {
                int read = this.inputStream.read(obj, 0, (int) Math.min(this.bytesToSkip - this.bytesSkipped, (long) obj.length));
                if (Thread.interrupted()) {
                    throw new InterruptedIOException();
                } else if (read == -1) {
                    throw new EOFException();
                } else {
                    this.bytesSkipped += (long) read;
                    if (this.listener != null) {
                        this.listener.onBytesTransferred(this, read);
                    }
                }
            }
            skipBufferReference.set(obj);
        }
    }

    protected final long bytesRead() {
        return this.bytesRead;
    }

    protected final long bytesRemaining() {
        return this.bytesToRead == -1 ? this.bytesToRead : this.bytesToRead - this.bytesRead;
    }

    protected final long bytesSkipped() {
        return this.bytesSkipped;
    }

    public void clearAllRequestProperties() {
        this.requestProperties.clear();
    }

    public void clearRequestProperty(String str) {
        Assertions.checkNotNull(str);
        this.requestProperties.remove(str);
    }

    public void close() {
        try {
            if (this.inputStream != null) {
                maybeTerminateInputStream(this.connection, bytesRemaining());
                this.inputStream.close();
            }
            this.inputStream = null;
            closeConnectionQuietly();
            if (this.opened) {
                this.opened = false;
                if (this.listener != null) {
                    this.listener.onTransferEnd(this);
                }
            }
        } catch (IOException e) {
            throw new HttpDataSourceException(e, this.dataSpec, 3);
        } catch (Throwable th) {
            this.inputStream = null;
            closeConnectionQuietly();
            if (this.opened) {
                this.opened = false;
                if (this.listener != null) {
                    this.listener.onTransferEnd(this);
                }
            }
        }
    }

    protected final HttpURLConnection getConnection() {
        return this.connection;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.connection == null ? null : this.connection.getHeaderFields();
    }

    public Uri getUri() {
        return this.connection == null ? null : Uri.parse(this.connection.getURL().toString());
    }

    public long open(DataSpec dataSpec) {
        long j = 0;
        this.dataSpec = dataSpec;
        this.bytesRead = 0;
        this.bytesSkipped = 0;
        try {
            this.connection = makeConnection(dataSpec);
            try {
                int responseCode = this.connection.getResponseCode();
                if (responseCode < Callback.DEFAULT_DRAG_ANIMATION_DURATION || responseCode > 299) {
                    Map headerFields = this.connection.getHeaderFields();
                    closeConnectionQuietly();
                    InvalidResponseCodeException invalidResponseCodeException = new InvalidResponseCodeException(responseCode, headerFields, dataSpec);
                    if (responseCode == 416) {
                        invalidResponseCodeException.initCause(new DataSourceException(0));
                    }
                    throw invalidResponseCodeException;
                }
                String contentType = this.connection.getContentType();
                if (this.contentTypePredicate == null || this.contentTypePredicate.evaluate(contentType)) {
                    if (responseCode == Callback.DEFAULT_DRAG_ANIMATION_DURATION && dataSpec.position != 0) {
                        j = dataSpec.position;
                    }
                    this.bytesToSkip = j;
                    if (dataSpec.isFlagSet(1)) {
                        this.bytesToRead = dataSpec.length;
                    } else if (dataSpec.length != -1) {
                        this.bytesToRead = dataSpec.length;
                    } else {
                        j = getContentLength(this.connection);
                        this.bytesToRead = j != -1 ? j - this.bytesToSkip : -1;
                    }
                    try {
                        this.inputStream = this.connection.getInputStream();
                        this.opened = true;
                        if (this.listener != null) {
                            this.listener.onTransferStart(this, dataSpec);
                        }
                        return this.bytesToRead;
                    } catch (IOException e) {
                        closeConnectionQuietly();
                        throw new HttpDataSourceException(e, dataSpec, 1);
                    }
                }
                closeConnectionQuietly();
                throw new InvalidContentTypeException(contentType, dataSpec);
            } catch (IOException e2) {
                closeConnectionQuietly();
                throw new HttpDataSourceException("Unable to connect to " + dataSpec.uri.toString(), e2, dataSpec, 1);
            }
        } catch (IOException e22) {
            throw new HttpDataSourceException("Unable to connect to " + dataSpec.uri.toString(), e22, dataSpec, 1);
        }
    }

    public int read(byte[] bArr, int i, int i2) {
        try {
            skipInternal();
            return readInternal(bArr, i, i2);
        } catch (IOException e) {
            throw new HttpDataSourceException(e, this.dataSpec, 2);
        }
    }

    public void setRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        this.requestProperties.set(str, str2);
    }
}
