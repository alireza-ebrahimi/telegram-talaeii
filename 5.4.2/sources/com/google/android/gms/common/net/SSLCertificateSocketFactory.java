package com.google.android.gms.common.net;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.PrivateKey;
import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class SSLCertificateSocketFactory extends SSLSocketFactory {
    private static final TrustManager[] zzvf = new TrustManager[]{new zza()};
    @VisibleForTesting
    private final Context mContext;
    @VisibleForTesting
    private SSLSocketFactory zzvg = null;
    @VisibleForTesting
    private SSLSocketFactory zzvh = null;
    @VisibleForTesting
    private TrustManager[] zzvi = null;
    @VisibleForTesting
    private KeyManager[] zzvj = null;
    @VisibleForTesting
    private byte[] zzvk = null;
    @VisibleForTesting
    private byte[] zzvl = null;
    @VisibleForTesting
    private PrivateKey zzvm = null;
    @VisibleForTesting
    private final int zzvn;
    @VisibleForTesting
    private final boolean zzvo;
    @VisibleForTesting
    private final boolean zzvp;
    @VisibleForTesting
    private final String zzvq;

    private SSLCertificateSocketFactory(Context context, int i, boolean z, boolean z2, String str) {
        this.mContext = context.getApplicationContext();
        this.zzvn = i;
        this.zzvo = z;
        this.zzvp = z2;
        this.zzvq = str;
    }

    public static SocketFactory getDefault(Context context, int i) {
        return new SSLCertificateSocketFactory(context, i, false, true, null);
    }

    public static SSLSocketFactory getDefaultWithCacheDir(int i, Context context, String str) {
        return new SSLCertificateSocketFactory(context, i, true, true, str);
    }

    public static SSLSocketFactory getDefaultWithSessionCache(int i, Context context) {
        return new SSLCertificateSocketFactory(context, i, true, true, null);
    }

    public static SSLSocketFactory getInsecure(Context context, int i, boolean z) {
        return new SSLCertificateSocketFactory(context, i, z, false, null);
    }

    public static void verifyHostname(Socket socket, String str) {
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            sSLSocket.startHandshake();
            SSLSession session = sSLSocket.getSession();
            if (session == null) {
                throw new SSLException("Cannot verify SSL socket without session");
            } else if (!HttpsURLConnection.getDefaultHostnameVerifier().verify(str, session)) {
                String str2 = "Cannot verify hostname: ";
                String valueOf = String.valueOf(str);
                throw new SSLPeerUnverifiedException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            } else {
                return;
            }
        }
        throw new IllegalArgumentException("Attempt to verify non-SSL socket");
    }

    @VisibleForTesting
    private static void zza(Socket socket, int i) {
        Throwable e;
        String valueOf;
        if (socket != null) {
            try {
                socket.getClass().getMethod("setHandshakeTimeout", new Class[]{Integer.TYPE}).invoke(socket, new Object[]{Integer.valueOf(i)});
            } catch (Throwable e2) {
                Throwable th = e2;
                e2 = th.getCause();
                if (e2 instanceof RuntimeException) {
                    throw ((RuntimeException) e2);
                }
                valueOf = String.valueOf(socket.getClass());
                throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 46).append("Failed to invoke setSocketHandshakeTimeout on ").append(valueOf).toString(), th);
            } catch (NoSuchMethodException e3) {
                e2 = e3;
                valueOf = String.valueOf(socket.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 45).append(valueOf).append(" does not implement setSocketHandshakeTimeout").toString(), e2);
            } catch (IllegalAccessException e4) {
                e2 = e4;
                valueOf = String.valueOf(socket.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 45).append(valueOf).append(" does not implement setSocketHandshakeTimeout").toString(), e2);
            }
        }
    }

    @VisibleForTesting
    private static void zza(Socket socket, PrivateKey privateKey) {
        Throwable e;
        String valueOf;
        if (socket != null) {
            try {
                socket.getClass().getMethod("setChannelIdPrivateKey", new Class[]{PrivateKey.class}).invoke(socket, new Object[]{privateKey});
            } catch (Throwable e2) {
                Throwable th = e2;
                e2 = th.getCause();
                if (e2 instanceof RuntimeException) {
                    throw ((RuntimeException) e2);
                }
                valueOf = String.valueOf(socket.getClass());
                throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 43).append("Failed to invoke setChannelIdPrivateKey on ").append(valueOf).toString(), th);
            } catch (NoSuchMethodException e3) {
                e2 = e3;
                valueOf = String.valueOf(socket.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 42).append(valueOf).append(" does not implement setChannelIdPrivateKey").toString(), e2);
            } catch (IllegalAccessException e4) {
                e2 = e4;
                valueOf = String.valueOf(socket.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 42).append(valueOf).append(" does not implement setChannelIdPrivateKey").toString(), e2);
            }
        }
    }

    @VisibleForTesting
    private static void zza(Socket socket, byte[] bArr) {
        Throwable e;
        String valueOf;
        if (socket != null) {
            try {
                socket.getClass().getMethod("setNpnProtocols", new Class[]{byte[].class}).invoke(socket, new Object[]{bArr});
            } catch (Throwable e2) {
                Throwable th = e2;
                e2 = th.getCause();
                if (e2 instanceof RuntimeException) {
                    throw ((RuntimeException) e2);
                }
                valueOf = String.valueOf(socket.getClass());
                throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 36).append("Failed to invoke setNpnProtocols on ").append(valueOf).toString(), th);
            } catch (NoSuchMethodException e3) {
                e2 = e3;
                valueOf = String.valueOf(socket.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 35).append(valueOf).append(" does not implement setNpnProtocols").toString(), e2);
            } catch (IllegalAccessException e4) {
                e2 = e4;
                valueOf = String.valueOf(socket.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 35).append(valueOf).append(" does not implement setNpnProtocols").toString(), e2);
            }
        }
    }

    private static byte[] zza(byte[]... bArr) {
        if (bArr.length == 0) {
            throw new IllegalArgumentException("items.length == 0");
        }
        int i;
        int i2;
        int i3 = 0;
        for (byte[] bArr2 : bArr) {
            if (bArr2.length == 0 || bArr2.length > 255) {
                throw new IllegalArgumentException("s.length == 0 || s.length > 255: " + bArr2.length);
            }
            i3 += bArr2.length + 1;
        }
        byte[] bArr3 = new byte[i3];
        i3 = 0;
        for (byte[] bArr4 : bArr) {
            i = i3 + 1;
            bArr3[i3] = (byte) bArr4.length;
            int length = bArr4.length;
            i3 = i;
            i = 0;
            while (i < length) {
                i2 = i3 + 1;
                bArr3[i3] = bArr4[i];
                i++;
                i3 = i2;
            }
        }
        return bArr3;
    }

    @VisibleForTesting
    private static void zzb(Socket socket, byte[] bArr) {
        Throwable e;
        String valueOf;
        if (socket != null) {
            try {
                socket.getClass().getMethod("setAlpnProtocols", new Class[]{byte[].class}).invoke(socket, new Object[]{bArr});
            } catch (Throwable e2) {
                Throwable th = e2;
                e2 = th.getCause();
                if (e2 instanceof RuntimeException) {
                    throw ((RuntimeException) e2);
                }
                valueOf = String.valueOf(socket.getClass());
                throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 37).append("Failed to invoke setAlpnProtocols on ").append(valueOf).toString(), th);
            } catch (NoSuchMethodException e3) {
                e2 = e3;
                valueOf = String.valueOf(socket.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 36).append(valueOf).append(" does not implement setAlpnProtocols").toString(), e2);
            } catch (IllegalAccessException e4) {
                e2 = e4;
                valueOf = String.valueOf(socket.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 36).append(valueOf).append(" does not implement setAlpnProtocols").toString(), e2);
            }
        }
    }

    @VisibleForTesting
    private final synchronized SSLSocketFactory zzcx() {
        SSLSocketFactory sSLSocketFactory;
        if (this.zzvp) {
            if (this.zzvq != null) {
                if (this.zzvh == null) {
                    this.zzvh = SocketFactoryCreator.getInstance().makeSocketFactoryWithCacheDir(this.mContext, this.zzvj, this.zzvi, this.zzvq);
                }
            } else if (this.zzvh == null) {
                this.zzvh = SocketFactoryCreator.getInstance().makeSocketFactory(this.mContext, this.zzvj, this.zzvi, this.zzvo);
            }
            sSLSocketFactory = this.zzvh;
        } else {
            if (this.zzvg == null) {
                Log.w("SSLCertificateSocketFactory", "Bypassing SSL security checks at caller's request");
                this.zzvg = SocketFactoryCreator.getInstance().makeSocketFactory(this.mContext, this.zzvj, zzvf, this.zzvo);
            }
            sSLSocketFactory = this.zzvg;
        }
        return sSLSocketFactory;
    }

    public Socket createSocket() {
        Socket createSocket = zzcx().createSocket();
        zza(createSocket, this.zzvk);
        zzb(createSocket, this.zzvl);
        zza(createSocket, this.zzvn);
        zza(createSocket, this.zzvm);
        return createSocket;
    }

    public Socket createSocket(String str, int i) {
        Socket createSocket = zzcx().createSocket(str, i);
        zza(createSocket, this.zzvk);
        zzb(createSocket, this.zzvl);
        zza(createSocket, this.zzvn);
        zza(createSocket, this.zzvm);
        if (this.zzvp) {
            verifyHostname(createSocket, str);
        }
        return createSocket;
    }

    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) {
        Socket createSocket = zzcx().createSocket(str, i, inetAddress, i2);
        zza(createSocket, this.zzvk);
        zzb(createSocket, this.zzvl);
        zza(createSocket, this.zzvn);
        zza(createSocket, this.zzvm);
        if (this.zzvp) {
            verifyHostname(createSocket, str);
        }
        return createSocket;
    }

    public Socket createSocket(InetAddress inetAddress, int i) {
        Socket createSocket = zzcx().createSocket(inetAddress, i);
        zza(createSocket, this.zzvk);
        zzb(createSocket, this.zzvl);
        zza(createSocket, this.zzvn);
        zza(createSocket, this.zzvm);
        return createSocket;
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) {
        Socket createSocket = zzcx().createSocket(inetAddress, i, inetAddress2, i2);
        zza(createSocket, this.zzvk);
        zzb(createSocket, this.zzvl);
        zza(createSocket, this.zzvn);
        zza(createSocket, this.zzvm);
        return createSocket;
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) {
        Socket createSocket = zzcx().createSocket(socket, str, i, z);
        zza(createSocket, this.zzvk);
        zzb(createSocket, this.zzvl);
        zza(createSocket, this.zzvn);
        zza(createSocket, this.zzvm);
        if (this.zzvp) {
            verifyHostname(createSocket, str);
        }
        return createSocket;
    }

    public byte[] getAlpnSelectedProtocol(Socket socket) {
        Throwable e;
        String valueOf;
        try {
            return (byte[]) socket.getClass().getMethod("getAlpnSelectedProtocol", new Class[0]).invoke(socket, new Object[0]);
        } catch (Throwable e2) {
            Throwable th = e2;
            e2 = th.getCause();
            if (e2 instanceof RuntimeException) {
                throw ((RuntimeException) e2);
            }
            valueOf = String.valueOf(socket.getClass());
            throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 44).append("Failed to invoke getAlpnSelectedProtocol on ").append(valueOf).toString(), th);
        } catch (NoSuchMethodException e3) {
            e2 = e3;
            valueOf = String.valueOf(socket.getClass());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 43).append(valueOf).append(" does not implement getAlpnSelectedProtocol").toString(), e2);
        } catch (IllegalAccessException e4) {
            e2 = e4;
            valueOf = String.valueOf(socket.getClass());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 43).append(valueOf).append(" does not implement getAlpnSelectedProtocol").toString(), e2);
        }
    }

    public String[] getDefaultCipherSuites() {
        return zzcx().getDefaultCipherSuites();
    }

    public byte[] getNpnSelectedProtocol(Socket socket) {
        Throwable e;
        String valueOf;
        try {
            return (byte[]) socket.getClass().getMethod("getNpnSelectedProtocol", new Class[0]).invoke(socket, new Object[0]);
        } catch (Throwable e2) {
            Throwable th = e2;
            e2 = th.getCause();
            if (e2 instanceof RuntimeException) {
                throw ((RuntimeException) e2);
            }
            valueOf = String.valueOf(socket.getClass());
            throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 43).append("Failed to invoke getNpnSelectedProtocol on ").append(valueOf).toString(), th);
        } catch (NoSuchMethodException e3) {
            e2 = e3;
            valueOf = String.valueOf(socket.getClass());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 42).append(valueOf).append(" does not implement getNpnSelectedProtocol").toString(), e2);
        } catch (IllegalAccessException e4) {
            e2 = e4;
            valueOf = String.valueOf(socket.getClass());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 42).append(valueOf).append(" does not implement getNpnSelectedProtocol").toString(), e2);
        }
    }

    public String[] getSupportedCipherSuites() {
        return zzcx().getSupportedCipherSuites();
    }

    public void setAlpnProtocols(byte[][] bArr) {
        this.zzvl = zza(bArr);
    }

    public void setChannelIdPrivateKey(PrivateKey privateKey) {
        this.zzvm = privateKey;
    }

    public void setHostname(Socket socket, String str) {
        Throwable e;
        String valueOf;
        try {
            socket.getClass().getMethod("setHostname", new Class[]{String.class}).invoke(socket, new Object[]{str});
        } catch (Throwable e2) {
            Throwable th = e2;
            e2 = th.getCause();
            if (e2 instanceof RuntimeException) {
                throw ((RuntimeException) e2);
            }
            valueOf = String.valueOf(socket.getClass());
            throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 32).append("Failed to invoke setHostname on ").append(valueOf).toString(), th);
        } catch (NoSuchMethodException e3) {
            e2 = e3;
            valueOf = String.valueOf(socket.getClass());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 31).append(valueOf).append(" does not implement setHostname").toString(), e2);
        } catch (IllegalAccessException e4) {
            e2 = e4;
            valueOf = String.valueOf(socket.getClass());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 31).append(valueOf).append(" does not implement setHostname").toString(), e2);
        }
    }

    public void setKeyManagers(KeyManager[] keyManagerArr) {
        this.zzvj = keyManagerArr;
        this.zzvh = null;
        this.zzvg = null;
    }

    public void setNpnProtocols(byte[][] bArr) {
        this.zzvk = zza(bArr);
    }

    public void setSoWriteTimeout(Socket socket, int i) {
        Throwable e;
        String valueOf;
        try {
            socket.getClass().getMethod("setSoWriteTimeout", new Class[]{Integer.TYPE}).invoke(socket, new Object[]{Integer.valueOf(i)});
        } catch (Throwable e2) {
            Throwable th = e2;
            e2 = th.getCause();
            if (e2 instanceof SocketException) {
                throw ((SocketException) e2);
            } else if (e2 instanceof RuntimeException) {
                throw ((RuntimeException) e2);
            } else {
                valueOf = String.valueOf(socket.getClass());
                throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 38).append("Failed to invoke setSoWriteTimeout on ").append(valueOf).toString(), th);
            }
        } catch (NoSuchMethodException e3) {
            e2 = e3;
            valueOf = String.valueOf(socket.getClass());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 37).append(valueOf).append(" does not implement setSoWriteTimeout").toString(), e2);
        } catch (IllegalAccessException e4) {
            e2 = e4;
            valueOf = String.valueOf(socket.getClass());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 37).append(valueOf).append(" does not implement setSoWriteTimeout").toString(), e2);
        }
    }

    public void setTrustManagers(TrustManager[] trustManagerArr) {
        this.zzvi = trustManagerArr;
        this.zzvh = null;
    }

    public void setUseSessionTickets(Socket socket, boolean z) {
        Throwable e;
        String valueOf;
        try {
            socket.getClass().getMethod("setUseSessionTickets", new Class[]{Boolean.TYPE}).invoke(socket, new Object[]{Boolean.valueOf(z)});
        } catch (Throwable e2) {
            Throwable th = e2;
            e2 = th.getCause();
            if (e2 instanceof RuntimeException) {
                throw ((RuntimeException) e2);
            }
            valueOf = String.valueOf(socket.getClass());
            throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 41).append("Failed to invoke setUseSessionTickets on ").append(valueOf).toString(), th);
        } catch (NoSuchMethodException e3) {
            e2 = e3;
            valueOf = String.valueOf(socket.getClass());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 40).append(valueOf).append(" does not implement setUseSessionTickets").toString(), e2);
        } catch (IllegalAccessException e4) {
            e2 = e4;
            valueOf = String.valueOf(socket.getClass());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 40).append(valueOf).append(" does not implement setUseSessionTickets").toString(), e2);
        }
    }
}
