import { Routes } from '@angular/router';
import { Dashboard } from './pages/dashboard/dashboard';
import { Trades } from './pages/trades/trades';
import { TaxSummaryComponent } from './pages/tax-summary/tax-summary';
import { Upload } from './pages/upload/upload';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: Dashboard },
  { path: 'trades', component: Trades },
  { path: 'tax-summary', component: TaxSummaryComponent },
  { path: 'upload', component: Upload },
  { path: '**', redirectTo: 'dashboard' },
];
