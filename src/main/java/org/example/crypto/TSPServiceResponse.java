package org.example.crypto;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSSignatureEncryptionAlgorithmFinder;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampResponseGenerator;
import org.bouncycastle.tsp.TimeStampTokenGenerator;
import org.bouncycastle.util.CollectionStore;

import ru.CryptoPro.CAdES.tools.verifier.GostCMSSignatureEncryptionAlgorithmFinder;
import ru.CryptoPro.CAdES.tools.verifier.GostContentSignerProvider;
import ru.CryptoPro.CAdES.tools.verifier.GostDigestCalculatorProvider;

import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.JCP.tools.AlgorithmUtility;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.*;

public class TSPServiceResponse {
    public static final ASN1ObjectIdentifier TSP_POLICY = new ASN1ObjectIdentifier("1.2.3.4.5"); // must be for our test
    public static final BigInteger TSP_SERIAL = new BigInteger("16"); // fixed counter
    public static final Set<ASN1ObjectIdentifier> acceptedAlgorithms = new HashSet<>();
    private final TimeStampResponseGenerator tspResponseGenerator;
    static {
        acceptedAlgorithms.add(new ASN1ObjectIdentifier(JCP.GOST_DIGEST_OID));
        acceptedAlgorithms.add(new ASN1ObjectIdentifier(JCP.GOST_DIGEST_2012_256_OID));
        acceptedAlgorithms.add(new ASN1ObjectIdentifier(JCP.GOST_DIGEST_2012_512_OID));
    }
    public TSPServiceResponse(String provider, KeyStoreHolder keyStoreHolder) throws Exception {
        this.tspResponseGenerator = createTimeStampResponseGenerator(provider, keyStoreHolder);
    }
    private static TimeStampResponseGenerator createTimeStampResponseGenerator(String provider, KeyStoreHolder keyStoreHolder) throws Exception {
        PrivateKey privateKey = keyStoreHolder.getPrivateKey();
        X509Certificate certificate = keyStoreHolder.getCertificate();
        DigestCalculatorProvider digestProvider = new GostDigestCalculatorProvider(privateKey, provider, false, false);
        CMSSignatureEncryptionAlgorithmFinder algorithmFinder = new GostCMSSignatureEncryptionAlgorithmFinder(privateKey);
        X509CertificateHolder signerCertHolder = new X509CertificateHolder(certificate.getEncoded());
        ContentSigner contentSigner = new GostContentSignerProvider(privateKey, provider);
        SignerInfoGeneratorBuilder signerInfoGeneratorBuilder = new SignerInfoGeneratorBuilder(digestProvider, algorithmFinder);
        SignerInfoGenerator signerInfoGenerator = signerInfoGeneratorBuilder.build(contentSigner, signerCertHolder);
        String digestOid = AlgorithmUtility.keyAlgToDigestOid(privateKey.getAlgorithm());
        DigestCalculator digestCalculator = new GostDigestCalculator(digestOid, provider);
        // We need to include issuer-serial.
        TimeStampTokenGenerator tspTokenGenerator = new TimeStampTokenGenerator(signerInfoGenerator, digestCalculator, TSP_POLICY, true);
        Collection<X509CertificateHolder> certs = new ArrayList<>();
        certs.add(signerCertHolder);
        CollectionStore<X509CertificateHolder> certStore = new CollectionStore<>(certs);
        tspTokenGenerator.addCertificates(certStore);
        return new TimeStampResponseGenerator(tspTokenGenerator, acceptedAlgorithms);
    }
    public byte[] make(byte[] tspRequestData) throws Exception {
        TimeStampRequest tspRequest = new TimeStampRequest(tspRequestData);
        TimeStampResponse tspResponse = tspResponseGenerator.generate(tspRequest, TSP_SERIAL, new Date()); // fixed counter
        return tspResponse.getEncoded();
    }
}
