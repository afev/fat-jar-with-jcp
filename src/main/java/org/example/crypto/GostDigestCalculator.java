package org.example.crypto;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.DigestCalculator;

import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;

public class GostDigestCalculator implements DigestCalculator {
    private final String digestOid;
    private final DigestOutputStream digestOutputStream;
    public GostDigestCalculator(String digestOid, String provider) throws Exception {
        this.digestOid = digestOid;
        MessageDigest messageDigest = MessageDigest.getInstance(digestOid, provider);
        digestOutputStream = new DigestOutputStream(new NullOutputStream(), messageDigest);
    }
    @Override
    public AlgorithmIdentifier getAlgorithmIdentifier() {
        return new AlgorithmIdentifier(new ASN1ObjectIdentifier(digestOid));
    }
    @Override
    public OutputStream getOutputStream() {
        return digestOutputStream;
    }
    @Override
    public byte[] getDigest() {
        return digestOutputStream.getMessageDigest().digest();
    }
}
