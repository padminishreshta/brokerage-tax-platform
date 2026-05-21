package com.example.tax.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxTradeDetailResponse {

    private String sellTradeId;
    private String buyTradeId;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private BigDecimal realizedGainLoss;
    private long holdingDays;
    private String holdingType;
    private LocalDate buyDate;
    private LocalDate sellDate;

    public String getSellTradeId() {
        return sellTradeId;
    }

    public void setSellTradeId(String sellTradeId) {
        this.sellTradeId = sellTradeId;
    }

    public String getBuyTradeId() {
        return buyTradeId;
    }

    public void setBuyTradeId(String buyTradeId) {
        this.buyTradeId = buyTradeId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getRealizedGainLoss() {
        return realizedGainLoss;
    }

    public void setRealizedGainLoss(BigDecimal realizedGainLoss) {
        this.realizedGainLoss = realizedGainLoss;
    }

    public long getHoldingDays() {
        return holdingDays;
    }

    public void setHoldingDays(long holdingDays) {
        this.holdingDays = holdingDays;
    }

    public String getHoldingType() {
        return holdingType;
    }

    public void setHoldingType(String holdingType) {
        this.holdingType = holdingType;
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }

    public LocalDate getSellDate() {
        return sellDate;
    }

    public void setSellDate(LocalDate sellDate) {
        this.sellDate = sellDate;
    }
}