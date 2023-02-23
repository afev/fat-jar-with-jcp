package org.example.controller;

import lombok.RequiredArgsConstructor;

import org.example.service.TspService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TspController {
    private final TspService tspService;
    @PostMapping(path = "/v1/tsp", consumes = "application/timestamp-query", produces = "application/timestamp-response")
    public ResponseEntity<?> response(@RequestBody byte[] tspRequestData) {
        try {
            return ResponseEntity.ok(tspService.makeTspResponse(tspRequestData));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
