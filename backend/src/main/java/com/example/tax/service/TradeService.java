package com.example.tax.service;

import com.example.tax.dto.TradeRequest;
import com.example.tax.dto.TradeResponse;
import com.example.tax.entity.TradeType;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface TradeService {

    TradeResponse createTrade(TradeRequest request);

    TradeResponse getTradeByTradeId(String tradeId);

    Page<TradeResponse> searchTrades(
            String customerId,
            String accountNumber,
            String symbol,
            TradeType tradeType,
            LocalDate fromDate,
            LocalDate toDate,
            int page,
            int size
    );
}