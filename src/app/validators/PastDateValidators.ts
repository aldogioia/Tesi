import { AbstractControl, ValidationErrors } from '@angular/forms';

export function pastDateValidator(control: AbstractControl): ValidationErrors | null {
  const date = new Date(control.value);
  if (isNaN(date.getTime())) {
    return { invalidDate: 'Invalid date' };
  }
  if (date >= new Date()) {
    return { futureDate: 'Date must be in the past' };
  }
  return null;
}