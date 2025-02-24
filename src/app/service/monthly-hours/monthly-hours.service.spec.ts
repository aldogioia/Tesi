import { TestBed } from '@angular/core/testing';

import { MonthlyHoursService } from './monthly-hours.service';

describe('MonthlyHoursService', () => {
  let service: MonthlyHoursService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MonthlyHoursService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
