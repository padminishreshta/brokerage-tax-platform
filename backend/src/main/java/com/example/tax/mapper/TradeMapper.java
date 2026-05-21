package com.example.tax.mapper;

import com.example.tax.dto.TradeRequest;
import com.example.tax.dto.TradeResponse;
import com.example.tax.entity.Trade;

import java.math.BigDecimal;

public class TradeMapper {

    private TradeMapper() {
    }

    public static Trade toEntity(TradeRequest request) {
        Trade trade = new Trade();

        trade.setTradeId(request.getTradeId());
        trade.setCustomerId(request.getCustomerId());
        trade.setAccountNumber(request.getAccountNumber());
        trade.setSymbol(request.getSymbol().toUpperCase());
        trade.setTradeType(request.getTradeType());
        trade.setQuantity(request.getQuantity());
        trade.setPrice(request.getPrice());
        trade.setTradeDate(request.getTradeDate());
        trade.setSettlementDate(request.getSettlementDate());
        trade.setDividendAmount(request.getDividendAmount());

        return trade;
    }

    public static TradeResponse toResponse(Trade trade) {
        TradeResponse response = new TradeResponse();

        response.setId(trade.getId());
        response.setTradeId(trade.getTradeId());
        response.setCustomerId(trade.getCustomerId());
        response.setAccountNumber(trade.getAccountNumber());
        response.setSymbol(trade.getSymbol());
        response.setTradeType(trade.getTradeType());
        response.setQuantity(trade.getQuantity());
        response.setPrice(trade.getPrice());
        response.setTradeAmount(calculateTradeAmount(trade));
        response.setTradeDate(trade.getTradeDate());
        response.setSettlementDate(trade.getSettlementDate());
        response.setDividendAmount(trade.getDividendAmount());
        response.setCreatedAt(trade.getCreatedAt());

        return response;
    }

    private static BigDecimal calculateTradeAmount(Trade trade) {
        if (trade.getQuantity() == null || trade.getPrice() == null) {
            return BigDecimal.ZERO;
        }
        return trade.getQuantity().multiply(trade.getPrice());
    }
}