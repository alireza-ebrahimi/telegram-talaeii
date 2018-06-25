package com.persianswitch.sdk.base.webservice.trust;

import android.content.Context;
import com.persianswitch.sdk.base.webservice.trust.MemorizingTrustManager.Options;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class TrustManagerBuilder {
    private static final String BKS = "BKS";
    private static final String X509 = "X.509";
    private Context ctxt;
    private MemorizingTrustManager memo;
    private CompositeTrustManager mgr;

    public TrustManagerBuilder() {
        this(null);
    }

    public TrustManagerBuilder(Context ctxt) {
        this.mgr = CompositeTrustManager.matchAll(new X509TrustManager[0]);
        this.ctxt = null;
        this.memo = null;
        this.ctxt = ctxt;
    }

    public TrustManager build() {
        return this.mgr;
    }

    public TrustManager[] buildArray() {
        return new TrustManager[]{build()};
    }

    public TrustManagerBuilder or() {
        if (this.mgr.isMatchAll()) {
            if (this.mgr.size() < 2) {
                this.mgr.setMatchAll(false);
            } else {
                this.mgr = CompositeTrustManager.matchAny(this.mgr);
            }
        }
        return this;
    }

    public TrustManagerBuilder and() {
        if (!this.mgr.isMatchAll()) {
            if (this.mgr.size() < 2) {
                this.mgr.setMatchAll(true);
            } else {
                this.mgr = CompositeTrustManager.matchAll(this.mgr);
            }
        }
        return this;
    }

    public TrustManagerBuilder useDefault() throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init((KeyStore) null);
        addAll(tmf.getTrustManagers());
        return this;
    }

    public TrustManagerBuilder denyAll() {
        this.mgr.add(new DenyAllTrustManager());
        return this;
    }

    public TrustManagerBuilder allowCA(File caFile) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        return allowCA(caFile, X509);
    }

    public TrustManagerBuilder allowCA(File caFile, String certType) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        addAll(TrustManagers.allowCA(new BufferedInputStream(new FileInputStream(caFile)), certType));
        return this;
    }

    public TrustManagerBuilder allowCA(int rawResourceId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        return allowCA(rawResourceId, X509);
    }

    public TrustManagerBuilder allowCA(int rawResourceId, String certType) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        checkContext();
        addAll(TrustManagers.allowCA(this.ctxt.getResources().openRawResource(rawResourceId), certType));
        return this;
    }

    public TrustManagerBuilder allowCA(String assetPath) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        return allowCA(assetPath, X509);
    }

    public TrustManagerBuilder allowCA(String assetPath, String certType) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        checkContext();
        addAll(TrustManagers.allowCA(this.ctxt.getAssets().open(assetPath), certType));
        return this;
    }

    public TrustManagerBuilder selfSigned(File store, char[] password) throws NullPointerException, GeneralSecurityException, IOException {
        return selfSigned(store, password, BKS);
    }

    public TrustManagerBuilder selfSigned(File store, char[] password, String format) throws NullPointerException, GeneralSecurityException, IOException {
        addAll(TrustManagers.useTrustStore(new BufferedInputStream(new FileInputStream(store)), password, format));
        return this;
    }

    public TrustManagerBuilder selfSigned(int rawResourceId, char[] password) throws NullPointerException, GeneralSecurityException, IOException {
        return selfSigned(rawResourceId, password, BKS);
    }

    public TrustManagerBuilder selfSigned(int rawResourceId, char[] password, String format) throws NullPointerException, GeneralSecurityException, IOException {
        checkContext();
        addAll(TrustManagers.useTrustStore(this.ctxt.getResources().openRawResource(rawResourceId), password, format));
        return this;
    }

    public TrustManagerBuilder selfSigned(String assetPath, char[] password) throws IOException, NullPointerException, GeneralSecurityException {
        return selfSigned(assetPath, password, BKS);
    }

    public TrustManagerBuilder selfSigned(String assetPath, char[] password, String format) throws IOException, NullPointerException, GeneralSecurityException {
        checkContext();
        addAll(TrustManagers.useTrustStore(this.ctxt.getAssets().open(assetPath), password, format));
        return this;
    }

    public TrustManagerBuilder memorize(Options options) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        if (this.memo != null) {
            throw new IllegalStateException("Cannot add a 2nd MemorizingTrustManager");
        }
        this.memo = new MemorizingTrustManager(options);
        this.mgr.add(this.memo);
        return this;
    }

    public TrustManagerBuilder addAll(TrustManager[] mgrs) {
        for (TrustManager tm : mgrs) {
            if (tm instanceof X509TrustManager) {
                this.mgr.add((X509TrustManager) tm);
            }
        }
        return this;
    }

    public void memorizeCert(X509Certificate[] chain) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        this.memo.storeCert(chain);
    }

    public void allowCertOnce(X509Certificate[] chain) throws KeyStoreException, NoSuchAlgorithmException {
        this.memo.allowOnce(chain);
    }

    public void clearMemorizedCerts(boolean clearPersistent) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        this.memo.clear(clearPersistent);
    }

    private void checkContext() {
        if (this.ctxt == null) {
            throw new IllegalArgumentException("Must use constructor supplying a Context");
        }
    }
}
