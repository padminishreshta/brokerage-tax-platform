package com.example.tax.controller;

import com.example.tax.dto.TradeUploadResponse;
import com.example.tax.service.CsvTradeUploadService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/trades")
public class TradeUploadController {

    private final CsvTradeUploadService csvTradeUploadService;

    public TradeUploadController(CsvTradeUploadService csvTradeUploadService) {
        this.csvTradeUploadService = csvTradeUploadService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TradeUploadResponse> uploadTrades(@RequestParam("file") MultipartFile file) {
        TradeUploadResponse response = csvTradeUploadService.uploadTrades(file);
        return ResponseEntity.ok(response);
    }
}