import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProfessorsComponent } from './add-professors.component';

describe('AddProfessorsComponent', () => {
  let component: AddProfessorsComponent;
  let fixture: ComponentFixture<AddProfessorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddProfessorsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddProfessorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
