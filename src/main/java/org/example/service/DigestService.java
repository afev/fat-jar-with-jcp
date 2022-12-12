package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.CryptoPro.JCP.JCP;
import java.security.MessageDigest;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class DigestService {
    public static final String DIGEST_ALGORITHM = JCP.GOST_DIGEST_2012_512_NAME;
    public String digest(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM, JCP.PROVIDER_NAME);
            return Base64.getEncoder().encodeToString(md.digest(data));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred, " + e.getMessage();
        }
    }
}
