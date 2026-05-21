import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaxSummary } from './tax-summary';

describe('TaxSummary', () => {
  let component: TaxSummary;
  let fixture: ComponentFixture<TaxSummary>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaxSummary],
    }).compileComponents();

    fixture = TestBed.createComponent(TaxSummary);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
