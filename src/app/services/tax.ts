import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TaxTradeDetail {
  sellTradeId: string;
  buyTradeId: string;
  symbol: string;
  quantity: number;
  buyPrice: number;
  sellPrice: number;
  realizedGainLoss: number;
  holdingDays: number;
  holdingType: string;
  buyDate: string;
  sellDate: string;
}

export interface TaxSummary {
  customerId: string;
  taxYear: number | null;
  shortTermGain: number;
  longTermGain: number;
  totalRealizedGainLoss: number;
  dividendIncome: number;
  shortTermTax: number;
  longTermTax: number;
  dividendTax: number;
  totalTaxLiability: number;
  taxableTrades: TaxTradeDetail[];
}

@Injectable({
  providedIn: 'root',
})
export class TaxService {
  private readonly baseUrl = 'http://localhost:8080/api/tax';

  constructor(private http: HttpClient) {}

  getTaxSummary(customerId: string): Observable<TaxSummary> {
    return this.http.get<TaxSummary>(`${this.baseUrl}/customer/${customerId}`);
  }

  getAnnualTaxReport(customerId: string, year: number): Observable<TaxSummary> {
    return this.http.get<TaxSummary>(`${this.baseUrl}/customer/${customerId}/year/${year}`);
  }
}
