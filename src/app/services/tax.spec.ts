import { TestBed } from '@angular/core/testing';

import { Tax } from './tax';

describe('Tax', () => {
  let service: Tax;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Tax);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
