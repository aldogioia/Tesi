import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth/auth.service";
import {ActivatedRoute} from "@angular/router";
import {PasswordSetService} from "../../service/password-set/password-set.service";
import {RequestSetPasswordDto} from "../../model/dto/RequestSetPasswordDto";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css', '../../../../public/css/input.css'],
  host: {'class': 'main'}
})
export class ResetPasswordComponent implements OnInit {
  form: FormGroup = new FormGroup({});

  showPassword1 = false;
  showPassword2 = false;

  isError: boolean = false;
  message: string = "";
  showToast: boolean = false;

  token: string | null = "";

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private passwordSetService: PasswordSetService,
    private route: ActivatedRoute
  ) {
    this.form = this.formBuilder.group({
      newPassword: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]]
    })
  }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      this.token = params.get('token');
    });
  }

  submit() {
    if (this.form.valid) {
      const newPassword = this.form.get('newPassword')!.value;
      const confirmPassword = this.form.get('confirmPassword')!.value;

      if (newPassword === confirmPassword && this.token != null) {
        const requestSetPasswordDto = new RequestSetPasswordDto(this.token, newPassword);
        this.passwordSetService.setPassword(requestSetPasswordDto).subscribe({
          next: () => {
            this.isError = false;
            this.message = "Password modificata con successo.";
            this.showToast = true;
          },
          error: () => {
            this.isError = true;
            this.message = "Errore durante il reset della password.";
            this.showToast = true;
          }
        });
      } else {
        this.isError = true;
        this.message = "Le password non coincidono.";
        this.showToast = true;
      }
      setTimeout(() => {
        this.showToast = false;
      }, 3000);
    }
  }
}
