package com.example.tax.controller;

import com.example.tax.dto.TradeRequest;
import com.example.tax.dto.TradeResponse;
import com.example.tax.entity.TradeType;
import com.example.tax.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping
    public ResponseEntity<TradeResponse> createTrade(@Valid @RequestBody TradeRequest request) {
        TradeResponse response = tradeService.createTrade(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{tradeId}")
    public ResponseEntity<TradeResponse> getTradeByTradeId(@PathVariable String tradeId) {
        TradeResponse response = tradeService.getTradeByTradeId(tradeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<TradeResponse>> searchTrades(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String symbol,
            @RequestParam(required = false) TradeType tradeType,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<TradeResponse> response = tradeService.searchTrades(
                customerId,
                accountNumber,
                symbol,
                tradeType,
                fromDate,
                toDate,
                page,
                size
        );

        return ResponseEntity.ok(response);
    }
}