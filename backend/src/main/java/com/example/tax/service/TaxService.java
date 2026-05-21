package com.example.tax.service;

import com.example.tax.dto.TaxSummaryResponse;

public interface TaxService {

    TaxSummaryResponse getTaxSummaryByCustomer(String customerId);

    TaxSummaryResponse getAnnualTaxReport(String customerId, int year);
}
