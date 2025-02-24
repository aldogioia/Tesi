import { TestBed } from '@angular/core/testing';

import { DailyHoursService } from './daily-hours.service';

describe('DailyHoursService', () => {
  let service: DailyHoursService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DailyHoursService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
