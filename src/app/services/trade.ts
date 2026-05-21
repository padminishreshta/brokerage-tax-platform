import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Trade {
  id?: number;
  tradeId: string;
  customerId: string;
  accountNumber: string;
  symbol: string;
  tradeType: 'BUY' | 'SELL';
  quantity: number;
  price: number;
  tradeAmount?: number;
  tradeDate: string;
  settlementDate: string;
  dividendAmount?: number;
  createdAt?: string;
}

export interface TradePageResponse {
  content: Trade[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class TradeService {
  private readonly baseUrl = 'http://localhost:8080/api/trades';

  constructor(private http: HttpClient) {}

  getTrades(filters?: {
    customerId?: string;
    symbol?: string;
    tradeType?: string;
    page?: number;
    size?: number;
  }): Observable<TradePageResponse> {
    let params = new HttpParams();

    if (filters?.customerId) {
      params = params.set('customerId', filters.customerId);
    }

    if (filters?.symbol) {
      params = params.set('symbol', filters.symbol);
    }

    if (filters?.tradeType) {
      params = params.set('tradeType', filters.tradeType);
    }

    params = params.set('page', filters?.page ?? 0);
    params = params.set('size', filters?.size ?? 10);

    return this.http.get<TradePageResponse>(this.baseUrl, { params });
  }

  createTrade(trade: Trade): Observable<Trade> {
    return this.http.post<Trade>(this.baseUrl, trade);
  }

  getTradeById(tradeId: string): Observable<Trade> {
    return this.http.get<Trade>(`${this.baseUrl}/${tradeId}`);
  }
}
