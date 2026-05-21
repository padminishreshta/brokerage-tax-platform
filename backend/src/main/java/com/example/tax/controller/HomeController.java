package com.example.tax.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
                "application", "Brokerage Tax Processing Platform",
                "status", "running",
                "availableEndpoints", Map.of(
                        "trades", "/api/trades",
                        "customerTaxSummary", "/api/tax/customer/{customerId}",
                        "annualTaxReport", "/api/tax/customer/{customerId}/year/{year}",
                        "csvUpload", "/api/trades/upload"
                )
        );
    }
}