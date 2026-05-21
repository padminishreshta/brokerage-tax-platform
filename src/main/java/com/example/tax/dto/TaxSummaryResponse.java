package com.example.tax.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TaxSummaryResponse {

    private String customerId;
    private Integer taxYear;

    private BigDecimal shortTermGain = BigDecimal.ZERO;
    private BigDecimal longTermGain = BigDecimal.ZERO;
    private BigDecimal totalRealizedGainLoss = BigDecimal.ZERO;

    private BigDecimal dividendIncome = BigDecimal.ZERO;

    private BigDecimal shortTermTax = BigDecimal.ZERO;
    private BigDecimal longTermTax = BigDecimal.ZERO;
    private BigDecimal dividendTax = BigDecimal.ZERO;
    private BigDecimal totalTaxLiability = BigDecimal.ZERO;

    private List<TaxTradeDetailResponse> taxableTrades = new ArrayList<>();

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(Integer taxYear) {
        this.taxYear = taxYear;
    }

    public BigDecimal getShortTermGain() {
        return shortTermGain;
    }

    public void setShortTermGain(BigDecimal shortTermGain) {
        this.shortTermGain = shortTermGain;
    }

    public BigDecimal getLongTermGain() {
        return longTermGain;
    }

    public void setLongTermGain(BigDecimal longTermGain) {
        this.longTermGain = longTermGain;
    }

    public BigDecimal getTotalRealizedGainLoss() {
        return totalRealizedGainLoss;
    }

    public void setTotalRealizedGainLoss(BigDecimal totalRealizedGainLoss) {
        this.totalRealizedGainLoss = totalRealizedGainLoss;
    }

    public BigDecimal getDividendIncome() {
        return dividendIncome;
    }

    public void setDividendIncome(BigDecimal dividendIncome) {
        this.dividendIncome = dividendIncome;
    }

    public BigDecimal getShortTermTax() {
        return shortTermTax;
    }

    public void setShortTermTax(BigDecimal shortTermTax) {
        this.shortTermTax = shortTermTax;
    }

    public BigDecimal getLongTermTax() {
        return longTermTax;
    }

    public void setLongTermTax(BigDecimal longTermTax) {
        this.longTermTax = longTermTax;
    }

    public BigDecimal getDividendTax() {
        return dividendTax;
    }

    public void setDividendTax(BigDecimal dividendTax) {
        this.dividendTax = dividendTax;
    }

    public BigDecimal getTotalTaxLiability() {
        return totalTaxLiability;
    }

    public void setTotalTaxLiability(BigDecimal totalTaxLiability) {
        this.totalTaxLiability = totalTaxLiability;
    }

    public List<TaxTradeDetailResponse> getTaxableTrades() {
        return taxableTrades;
    }

    public void setTaxableTrades(List<TaxTradeDetailResponse> taxableTrades) {
        this.taxableTrades = taxableTrades;
    }
}