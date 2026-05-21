package com.example.tax.service.impl;

import com.example.tax.dto.TradeRequest;
import com.example.tax.dto.TradeResponse;
import com.example.tax.entity.Trade;
import com.example.tax.entity.TradeType;
import com.example.tax.exception.DuplicateResourceException;
import com.example.tax.exception.ResourceNotFoundException;
import com.example.tax.mapper.TradeMapper;
import com.example.tax.repository.TradeRepository;
import com.example.tax.service.TradeService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public TradeResponse createTrade(TradeRequest request) {
        if (tradeRepository.existsByTradeId(request.getTradeId())) {
            throw new DuplicateResourceException("Trade already exists with tradeId: " + request.getTradeId());
        }

        Trade trade = TradeMapper.toEntity(request);
        Trade savedTrade = tradeRepository.save(trade);

        return TradeMapper.toResponse(savedTrade);
    }

    @Override
    public TradeResponse getTradeByTradeId(String tradeId) {
        Trade trade = tradeRepository.findByTradeId(tradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Trade not found with tradeId: " + tradeId));

        return TradeMapper.toResponse(trade);
    }

    @Override
    public Page<TradeResponse> searchTrades(
            String customerId,
            String accountNumber,
            String symbol,
            TradeType tradeType,
            LocalDate fromDate,
            LocalDate toDate,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "tradeDate")
        );

        Specification<Trade> specification = buildTradeSearchSpecification(
                customerId,
                accountNumber,
                symbol,
                tradeType,
                fromDate,
                toDate
        );

        return tradeRepository.findAll(specification, pageable)
                .map(TradeMapper::toResponse);
    }

    private Specification<Trade> buildTradeSearchSpecification(
            String customerId,
            String accountNumber,
            String symbol,
            TradeType tradeType,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (customerId != null && !customerId.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("customerId"), customerId));
            }

            if (accountNumber != null && !accountNumber.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("accountNumber"), accountNumber));
            }

            if (symbol != null && !symbol.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("symbol"), symbol.toUpperCase()));
            }

            if (tradeType != null) {
                predicates.add(criteriaBuilder.equal(root.get("tradeType"), tradeType));
            }

            if (fromDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("tradeDate"), fromDate));
            }

            if (toDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("tradeDate"), toDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}