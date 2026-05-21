package com.example.tax.controller;

import com.example.tax.dto.TaxSummaryResponse;
import com.example.tax.service.TaxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tax")
public class TaxController {

    private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<TaxSummaryResponse> getTaxSummaryByCustomer(@PathVariable String customerId) {
        TaxSummaryResponse response = taxService.getTaxSummaryByCustomer(customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}/year/{year}")
    public ResponseEntity<TaxSummaryResponse> getAnnualTaxReport(
            @PathVariable String customerId,
            @PathVariable int year
    ) {
        TaxSummaryResponse response = taxService.getAnnualTaxReport(customerId, year);
        return ResponseEntity.ok(response);
    }
}