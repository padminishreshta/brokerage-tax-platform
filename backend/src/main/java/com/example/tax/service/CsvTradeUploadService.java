package com.example.tax.service;

import com.example.tax.dto.TradeUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CsvTradeUploadService {

    TradeUploadResponse uploadTrades(MultipartFile file);
}