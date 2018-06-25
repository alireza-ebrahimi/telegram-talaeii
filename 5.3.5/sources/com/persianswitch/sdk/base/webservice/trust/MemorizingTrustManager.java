package com.persianswitch.sdk.base.webservice.trust;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MemorizingTrustManager implements X509TrustManager {
    private KeyStore keyStore = null;
    private Options options = null;
    private X509TrustManager storeTrustManager = null;
    private KeyStore transientKeyStore = null;
    private X509TrustManager transientTrustManager = null;

    public static class Options {
        File store = null;
        String storePassword;
        String storeType = KeyStore.getDefaultType();
        boolean trustOnFirstUse = false;
        File workingDir = null;

        public Options(Context ctxt, String storeRelPath, String storePassword) {
            this.workingDir = new File(ctxt.getFilesDir(), storeRelPath);
            this.workingDir.mkdirs();
            this.store = new File(this.workingDir, "memorized.bks");
            this.storePassword = storePassword;
        }

        public Options trustOnFirstUse() {
            this.trustOnFirstUse = true;
            return this;
        }

        public Options trustOnFirstUse(boolean trust) {
            this.trustOnFirstUse = trust;
            return this;
        }
    }

    public MemorizingTrustManager(Options options) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        this.options = options;
        clear(false);
    }

    public synchronized void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            this.storeTrustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException e) {
            try {
                this.transientTrustManager.checkClientTrusted(chain, authType);
            } catch (Exception e3) {
                throw new CertificateMemorizationException(e3);
            } catch (CertificateException e2) {
                if (!this.options.trustOnFirstUse || this.options.store.exists()) {
                    throw new CertificateNotMemorizedException(chain);
                }
                storeCert(chain);
            }
        }
    }

    public synchronized void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            this.storeTrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException e) {
            try {
                this.transientTrustManager.checkServerTrusted(chain, authType);
            } catch (Exception e3) {
                throw new CertificateMemorizationException(e3);
            } catch (CertificateException e2) {
                if (!this.options.trustOnFirstUse || this.options.store.exists()) {
                    throw new CertificateNotMemorizedException(chain);
                }
                storeCert(chain);
            }
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    public synchronized void storeCert(X509Certificate[] chain) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        for (X509Certificate cert : chain) {
            this.keyStore.setCertificateEntry(cert.getSubjectDN().getName(), cert);
        }
        initTrustManager();
        FileOutputStream fos = new FileOutputStream(this.options.store);
        this.keyStore.store(fos, this.options.storePassword.toCharArray());
        fos.close();
    }

    public synchronized void allowOnce(X509Certificate[] chain) throws KeyStoreException, NoSuchAlgorithmException {
        for (X509Certificate cert : chain) {
            this.transientKeyStore.setCertificateEntry(cert.getSubjectDN().getName(), cert);
        }
        initTrustManager();
    }

    public synchronized void clear(boolean clearPersistent) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        if (clearPersistent) {
            this.options.store.delete();
        }
        initTransientStore();
        initPersistentStore();
        initTrustManager();
    }

    private void initTransientStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        this.transientKeyStore = KeyStore.getInstance(this.options.storeType);
        this.transientKeyStore.load(null, null);
    }

    private void initPersistentStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        this.keyStore = KeyStore.getInstance(this.options.storeType);
        if (this.options.store.exists()) {
            this.keyStore.load(new FileInputStream(this.options.store), this.options.storePassword.toCharArray());
        } else {
            this.keyStore.load(null, this.options.storePassword.toCharArray());
        }
    }

    private void initTrustManager() throws KeyStoreException, NoSuchAlgorithmException {
        int i = 0;
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
        tmf.init(this.keyStore);
        for (TrustManager t : tmf.getTrustManagers()) {
            TrustManager t2;
            if (t2 instanceof X509TrustManager) {
                this.storeTrustManager = (X509TrustManager) t2;
                break;
            }
        }
        tmf = TrustManagerFactory.getInstance("X509");
        tmf.init(this.transientKeyStore);
        TrustManager[] trustManagers = tmf.getTrustManagers();
        int length = trustManagers.length;
        while (i < length) {
            t2 = trustManagers[i];
            if (t2 instanceof X509TrustManager) {
                this.transientTrustManager = (X509TrustManager) t2;
                return;
            }
            i++;
        }
    }
}
