package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.DigestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final DigestService digestService;
    @GetMapping(value = "/v1/digest/{data}")
    public String digest(@PathVariable String data) {
        return "Digest (" + DigestService.DIGEST_ALGORITHM + ") is '" + digestService.digest(data.getBytes(StandardCharsets.UTF_8)) + "'";
    }

}
