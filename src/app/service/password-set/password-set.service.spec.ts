import { TestBed } from '@angular/core/testing';

import { PasswordSetService } from './password-set.service';

describe('PasswordSetService', () => {
  let service: PasswordSetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PasswordSetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
