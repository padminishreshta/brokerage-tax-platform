package com.example.tax.service.impl;

import com.example.tax.dto.TradeRequest;
import com.example.tax.dto.TradeUploadResponse;
import com.example.tax.entity.TradeType;
import com.example.tax.service.CsvTradeUploadService;
import com.example.tax.service.TradeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Service
public class CsvTradeUploadServiceImpl implements CsvTradeUploadService {

    private final TradeService tradeService;

    public CsvTradeUploadServiceImpl(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @Override
    public TradeUploadResponse uploadTrades(MultipartFile file) {
        TradeUploadResponse response = new TradeUploadResponse();

        if (file == null || file.isEmpty()) {
            response.setFailedRecords(1);
            response.getErrors().add("Uploaded file is empty");
            return response;
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            response.setFailedRecords(1);
            response.getErrors().add("Only CSV files are supported");
            return response;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)
        )) {
            String line;
            int rowNumber = 0;

            while ((line = reader.readLine()) != null) {
                rowNumber++;

                // Skip header row
                if (rowNumber == 1 && line.toLowerCase().contains("tradeid")) {
                    continue;
                }

                if (line.isBlank()) {
                    continue;
                }

                response.setTotalRecords(response.getTotalRecords() + 1);

                try {
                    TradeRequest request = parseCsvLine(line);
                    tradeService.createTrade(request);
                    response.setSuccessfulRecords(response.getSuccessfulRecords() + 1);
                } catch (Exception exception) {
                    response.setFailedRecords(response.getFailedRecords() + 1);
                    response.getErrors().add("Row " + rowNumber + " failed: " + exception.getMessage());
                }
            }

        } catch (Exception exception) {
            response.getErrors().add("File processing failed: " + exception.getMessage());
            response.setFailedRecords(response.getFailedRecords() + 1);
        }

        return response;
    }

    private TradeRequest parseCsvLine(String line) {
        String[] values = line.split(",");

        if (values.length < 9) {
            throw new IllegalArgumentException("Invalid CSV format. Expected 9 columns");
        }

        TradeRequest request = new TradeRequest();

        request.setTradeId(values[0].trim());
        request.setCustomerId(values[1].trim());
        request.setAccountNumber(values[2].trim());
        request.setSymbol(values[3].trim());
        request.setTradeType(TradeType.valueOf(values[4].trim().toUpperCase()));
        request.setQuantity(new BigDecimal(values[5].trim()));
        request.setPrice(new BigDecimal(values[6].trim()));
        request.setTradeDate(LocalDate.parse(values[7].trim()));
        request.setSettlementDate(LocalDate.parse(values[8].trim()));

        if (values.length >= 10 && !values[9].trim().isBlank()) {
            request.setDividendAmount(new BigDecimal(values[9].trim()));
        } else {
            request.setDividendAmount(BigDecimal.ZERO);
        }

        return request;
    }
}