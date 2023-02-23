package org.example.service;

import lombok.RequiredArgsConstructor;

import org.example.config.TspProperties;
import org.example.crypto.KeyStoreHolder;

import org.example.crypto.TSPServiceResponse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(TspProperties.class)
@RequiredArgsConstructor
public class TspService {
    private final TspProperties tspProperties;
    public byte[] makeTspResponse(byte[] tspRequestData) throws Exception {
        KeyStoreHolder keyStoreHolder = new KeyStoreHolder(
            tspProperties.getKeyStoreProvider(),
            tspProperties.getKeyStoreType(),
            tspProperties.getKeyAlias(),
            tspProperties.getKeyPassword()
        );
        TSPServiceResponse serviceResponse = new TSPServiceResponse(
            tspProperties.getSignatureProvider(),
            keyStoreHolder
        );
        return serviceResponse.make(tspRequestData);
    }
}
