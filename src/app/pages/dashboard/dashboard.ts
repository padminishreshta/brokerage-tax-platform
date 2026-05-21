import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Trade, TradeService } from '../../services/trade';
import { TaxService, TaxSummary } from '../../services/tax';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {
  trades: Trade[] = [];
  taxSummary?: TaxSummary;

  totalTrades = 0;
  totalBuyTrades = 0;
  totalSellTrades = 0;

  isLoading = false;
  errorMessage = '';

  constructor(
    private tradeService: TradeService,
    private taxService: TaxService,
  ) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.tradeService.getTrades({ page: 0, size: 10 }).subscribe({
      next: (response) => {
        this.trades = response.content;
        this.totalTrades = response.totalElements;
        this.totalBuyTrades = this.trades.filter((trade) => trade.tradeType === 'BUY').length;
        this.totalSellTrades = this.trades.filter((trade) => trade.tradeType === 'SELL').length;
        this.loadTaxSummary();
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage =
          'Unable to load trades. Please confirm backend is running on http://localhost:8080.';
      },
    });
  }

  loadTaxSummary(): void {
    this.taxService.getTaxSummary('CUST001').subscribe({
      next: (summary) => {
        this.taxSummary = summary;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage =
          'Trades loaded, but tax summary is unavailable. Create both BUY and SELL trades for CUST001.';
      },
    });
  }
}
