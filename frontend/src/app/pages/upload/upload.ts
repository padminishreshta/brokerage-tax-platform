import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UploadResponse, UploadService } from '../../services/upload';

@Component({
  selector: 'app-upload',
  imports: [CommonModule],
  templateUrl: './upload.html',
  styleUrl: './upload.scss',
})
export class Upload {
  selectedFile?: File;
  uploadResult?: UploadResponse;

  isUploading = false;
  errorMessage = '';
  successMessage = '';

  constructor(private uploadService: UploadService) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (!input.files || input.files.length === 0) {
      this.selectedFile = undefined;
      return;
    }

    const file = input.files[0];

    if (!file.name.toLowerCase().endsWith('.csv')) {
      this.selectedFile = undefined;
      this.uploadResult = undefined;
      this.successMessage = '';
      this.errorMessage = 'Please select a valid CSV file.';
      return;
    }

    this.selectedFile = file;
    this.errorMessage = '';
    this.successMessage = '';
    this.uploadResult = undefined;
  }

  uploadCsv(): void {
    if (!this.selectedFile) {
      this.errorMessage = 'Please select a CSV file before uploading.';
      return;
    }

    this.isUploading = true;
    this.errorMessage = '';
    this.successMessage = '';
    this.uploadResult = undefined;

    this.uploadService.uploadCsv(this.selectedFile).subscribe({
      next: (response) => {
        this.uploadResult = response;
        this.isUploading = false;

        if (response.failedRecords === 0) {
          this.successMessage = 'CSV uploaded successfully.';
        } else {
          this.errorMessage =
            'CSV uploaded with some failed records. Please review the errors below.';
        }
      },
      error: () => {
        this.isUploading = false;
        this.errorMessage =
          'Upload failed. Please confirm backend is running and the file format is correct.';
      },
    });
  }

  reset(): void {
    this.selectedFile = undefined;
    this.uploadResult = undefined;
    this.errorMessage = '';
    this.successMessage = '';
  }
}
