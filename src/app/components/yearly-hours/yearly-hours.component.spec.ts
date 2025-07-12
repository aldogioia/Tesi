import { ComponentFixture, TestBed } from '@angular/core/testing';

import { YearlyHoursComponent } from './yearly-hours.component';

describe('YearlyHoursComponent', () => {
  let component: YearlyHoursComponent;
  let fixture: ComponentFixture<YearlyHoursComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [YearlyHoursComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(YearlyHoursComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
