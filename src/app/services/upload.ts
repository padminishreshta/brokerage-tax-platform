import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UploadResponse {
  totalRecords: number;
  successfulRecords: number;
  failedRecords: number;
  errors: string[];
}

@Injectable({
  providedIn: 'root',
})
export class UploadService {
  private readonly uploadUrl = 'http://localhost:8080/api/trades/upload';

  constructor(private http: HttpClient) {}

  uploadCsv(file: File): Observable<UploadResponse> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<UploadResponse>(this.uploadUrl, formData);
  }
}
