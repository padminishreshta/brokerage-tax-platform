import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Trade, TradeService } from '../../services/trade';

@Component({
  selector: 'app-trades',
  imports: [CommonModule, FormsModule],
  templateUrl: './trades.html',
  styleUrl: './trades.scss',
})
export class Trades implements OnInit {
  trades: Trade[] = [];

  customerId = '';
  symbol = '';
  tradeType = '';

  totalElements = 0;
  page = 0;
  size = 10;

  isLoading = false;
  errorMessage = '';

  constructor(private tradeService: TradeService) {}

  ngOnInit(): void {
    this.loadTrades();
  }

  loadTrades(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.tradeService
      .getTrades({
        customerId: this.customerId,
        symbol: this.symbol,
        tradeType: this.tradeType,
        page: this.page,
        size: this.size,
      })
      .subscribe({
        next: (response) => {
          this.trades = response.content;
          this.totalElements = response.totalElements;
          this.isLoading = false;
        },
        error: () => {
          this.isLoading = false;
          this.errorMessage =
            'Unable to load trades. Please make sure backend is running on port 8080.';
        },
      });
  }

  searchTrades(): void {
    this.page = 0;
    this.loadTrades();
  }

  clearFilters(): void {
    this.customerId = '';
    this.symbol = '';
    this.tradeType = '';
    this.page = 0;
    this.loadTrades();
  }

  nextPage(): void {
    if ((this.page + 1) * this.size < this.totalElements) {
      this.page++;
      this.loadTrades();
    }
  }

  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadTrades();
    }
  }
}
