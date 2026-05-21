import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaxService, TaxSummary } from '../../services/tax';

@Component({
  selector: 'app-tax-summary',
  imports: [CommonModule, FormsModule],
  templateUrl: './tax-summary.html',
  styleUrl: './tax-summary.scss',
})
export class TaxSummaryComponent {
  customerId = 'CUST001';
  taxYear = 2025;

  summary?: TaxSummary;
  isLoading = false;
  errorMessage = '';

  constructor(private taxService: TaxService) {}

  loadCustomerSummary(): void {
    if (!this.customerId.trim()) {
      this.errorMessage = 'Please enter a customer ID.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.summary = undefined;

    this.taxService.getTaxSummary(this.customerId.trim()).subscribe({
      next: (response) => {
        this.summary = response;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage =
          'Unable to load tax summary. Please verify customer ID and backend status.';
      },
    });
  }

  loadAnnualSummary(): void {
    if (!this.customerId.trim()) {
      this.errorMessage = 'Please enter a customer ID.';
      return;
    }

    if (!this.taxYear) {
      this.errorMessage = 'Please enter a valid tax year.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.summary = undefined;

    this.taxService.getAnnualTaxReport(this.customerId.trim(), this.taxYear).subscribe({
      next: (response) => {
        this.summary = response;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage =
          'Unable to load annual tax report. Please verify customer ID/year and backend status.';
      },
    });
  }
}
