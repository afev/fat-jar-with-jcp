package org.example.crypto;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class KeyStoreHolder {
    private final PrivateKey privateKey;
    private final X509Certificate certificate;
    public KeyStoreHolder(String keyStoreProvider, String keyStoreType, String keyAlias, String keyPassword) {
        try {
            KeyStore keyStore = keyStoreProvider != null && !keyStoreProvider.isEmpty()
                ? KeyStore.getInstance(keyStoreType, keyStoreProvider)
                : KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            char[] password = keyPassword != null && !keyPassword.isEmpty()
                ? keyPassword.toCharArray()
                : null;
            privateKey = (PrivateKey) keyStore.getKey(keyAlias, password);
            certificate = (X509Certificate) keyStore.getCertificate(keyAlias);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
    public X509Certificate getCertificate() {
        return certificate;
    }
}
