package com.example.tax.service.impl;

import com.example.tax.dto.TaxSummaryResponse;
import com.example.tax.dto.TaxTradeDetailResponse;
import com.example.tax.entity.Trade;
import com.example.tax.entity.TradeType;
import com.example.tax.exception.ResourceNotFoundException;
import com.example.tax.repository.TradeRepository;
import com.example.tax.service.TaxService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TaxServiceImpl implements TaxService {

    private static final BigDecimal SHORT_TERM_TAX_RATE = new BigDecimal("0.30");
    private static final BigDecimal LONG_TERM_TAX_RATE = new BigDecimal("0.15");
    private static final BigDecimal DIVIDEND_TAX_RATE = new BigDecimal("0.10");

    private final TradeRepository tradeRepository;

    public TaxServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public TaxSummaryResponse getTaxSummaryByCustomer(String customerId) {
        List<Trade> trades = tradeRepository.findByCustomerIdOrderByTradeDateAsc(customerId);

        if (trades.isEmpty()) {
            throw new ResourceNotFoundException("No trades found for customerId: " + customerId);
        }

        return calculateTaxSummary(customerId, null, trades);
    }

    @Override
    public TaxSummaryResponse getAnnualTaxReport(String customerId, int year) {
        List<Trade> trades = tradeRepository.findByCustomerIdOrderByTradeDateAsc(customerId);

        if (trades.isEmpty()) {
            throw new ResourceNotFoundException("No trades found for customerId: " + customerId);
        }

        return calculateTaxSummary(customerId, year, trades);
    }

    private TaxSummaryResponse calculateTaxSummary(String customerId, Integer year, List<Trade> trades) {
        TaxSummaryResponse response = new TaxSummaryResponse();
        response.setCustomerId(customerId);
        response.setTaxYear(year);

        BigDecimal shortTermGain = BigDecimal.ZERO;
        BigDecimal longTermGain = BigDecimal.ZERO;
        BigDecimal dividendIncome = BigDecimal.ZERO;

        List<Trade> buyTrades = trades.stream()
                .filter(trade -> trade.getTradeType() == TradeType.BUY)
                .sorted(Comparator.comparing(Trade::getTradeDate))
                .toList();

        List<Trade> sellTrades = trades.stream()
                .filter(trade -> trade.getTradeType() == TradeType.SELL)
                .filter(trade -> year == null || trade.getTradeDate().getYear() == year)
                .sorted(Comparator.comparing(Trade::getTradeDate))
                .toList();

        for (Trade trade : trades) {
            if (trade.getDividendAmount() != null) {
                if (year == null || trade.getTradeDate().getYear() == year) {
                    dividendIncome = dividendIncome.add(trade.getDividendAmount());
                }
            }
        }

        for (Trade sellTrade : sellTrades) {
            Optional<Trade> matchingBuyTrade = findMatchingBuyTrade(buyTrades, sellTrade);

            if (matchingBuyTrade.isEmpty()) {
                continue;
            }

            Trade buyTrade = matchingBuyTrade.get();

            BigDecimal realizedGainLoss = sellTrade.getPrice()
                    .subtract(buyTrade.getPrice())
                    .multiply(sellTrade.getQuantity());

            long holdingDays = ChronoUnit.DAYS.between(
                    buyTrade.getTradeDate(),
                    sellTrade.getTradeDate()
            );

            String holdingType;

            if (holdingDays < 365) {
                holdingType = "SHORT_TERM";
                shortTermGain = shortTermGain.add(realizedGainLoss);
            } else {
                holdingType = "LONG_TERM";
                longTermGain = longTermGain.add(realizedGainLoss);
            }

            TaxTradeDetailResponse detail = buildTaxTradeDetail(
                    sellTrade,
                    buyTrade,
                    realizedGainLoss,
                    holdingDays,
                    holdingType
            );

            response.getTaxableTrades().add(detail);
        }

        BigDecimal shortTermTax = shortTermGain.compareTo(BigDecimal.ZERO) > 0
                ? shortTermGain.multiply(SHORT_TERM_TAX_RATE)
                : BigDecimal.ZERO;

        BigDecimal longTermTax = longTermGain.compareTo(BigDecimal.ZERO) > 0
                ? longTermGain.multiply(LONG_TERM_TAX_RATE)
                : BigDecimal.ZERO;

        BigDecimal dividendTax = dividendIncome.multiply(DIVIDEND_TAX_RATE);

        BigDecimal totalRealizedGainLoss = shortTermGain.add(longTermGain);
        BigDecimal totalTaxLiability = shortTermTax.add(longTermTax).add(dividendTax);

        response.setShortTermGain(formatMoney(shortTermGain));
        response.setLongTermGain(formatMoney(longTermGain));
        response.setTotalRealizedGainLoss(formatMoney(totalRealizedGainLoss));
        response.setDividendIncome(formatMoney(dividendIncome));
        response.setShortTermTax(formatMoney(shortTermTax));
        response.setLongTermTax(formatMoney(longTermTax));
        response.setDividendTax(formatMoney(dividendTax));
        response.setTotalTaxLiability(formatMoney(totalTaxLiability));

        return response;
    }

    private Optional<Trade> findMatchingBuyTrade(List<Trade> buyTrades, Trade sellTrade) {
        return buyTrades.stream()
                .filter(buyTrade -> buyTrade.getCustomerId().equals(sellTrade.getCustomerId()))
                .filter(buyTrade -> buyTrade.getAccountNumber().equals(sellTrade.getAccountNumber()))
                .filter(buyTrade -> buyTrade.getSymbol().equals(sellTrade.getSymbol()))
                .filter(buyTrade -> !buyTrade.getTradeDate().isAfter(sellTrade.getTradeDate()))
                .findFirst();
    }

    private TaxTradeDetailResponse buildTaxTradeDetail(
            Trade sellTrade,
            Trade buyTrade,
            BigDecimal realizedGainLoss,
            long holdingDays,
            String holdingType
    ) {
        TaxTradeDetailResponse detail = new TaxTradeDetailResponse();

        detail.setSellTradeId(sellTrade.getTradeId());
        detail.setBuyTradeId(buyTrade.getTradeId());
        detail.setSymbol(sellTrade.getSymbol());
        detail.setQuantity(sellTrade.getQuantity());
        detail.setBuyPrice(formatMoney(buyTrade.getPrice()));
        detail.setSellPrice(formatMoney(sellTrade.getPrice()));
        detail.setRealizedGainLoss(formatMoney(realizedGainLoss));
        detail.setHoldingDays(holdingDays);
        detail.setHoldingType(holdingType);
        detail.setBuyDate(buyTrade.getTradeDate());
        detail.setSellDate(sellTrade.getTradeDate());

        return detail;
    }

    private BigDecimal formatMoney(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }

        return value.setScale(2, RoundingMode.HALF_UP);
    }
}