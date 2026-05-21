package com.example.tax.repository;

import com.example.tax.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long>, JpaSpecificationExecutor<Trade> {

    Optional<Trade> findByTradeId(String tradeId);

    boolean existsByTradeId(String tradeId);

    List<Trade> findByCustomerIdOrderByTradeDateAsc(String customerId);
}