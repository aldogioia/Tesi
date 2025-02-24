import { TestBed } from '@angular/core/testing';

import { YearlyHoursService } from './yearly-hours.service';

describe('YearlyHoursService', () => {
  let service: YearlyHoursService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(YearlyHoursService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
