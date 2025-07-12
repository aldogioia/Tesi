import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../service/auth/auth.service";
import {AuthRequestDto} from "../../model/dto/AuthRequestDto";
import {AccessRole} from "../../model/enum/AccessRoleEnum";
import {PasswordSetService} from "../../service/password-set/password-set.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../../../../public/css/input.css'],
  host: {'class' : 'main'}
})
export class LoginComponent {
  form: FormGroup = new FormGroup({});

  toPassword = false;
  showPassword = false;
  firstAccess = false;

  email = "";

  showToast: boolean = false;
  message: string = "";
  isError: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private passwordSetService: PasswordSetService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    })
  }

  initLogin(){
    this.email = this.form.get('email')!.value;

    this.authService.checkFirstAccess(this.email).subscribe({
      next: (response) => {
        if (response.firstAccess)
          this.firstAccess = true
        else this.toPassword = true;
      },
      error: () => {
        this.isError = true;
        this.message = "Errore, riprova più tardi.";
        this.showToast = true;
      }
    });

    setTimeout(() => {
      this.showToast = false;
    }, 3000);
  }

  initSetPassword(){
    this.passwordSetService.initSetPassword(this.email).subscribe({
      next: () => {
        this.isError = false;
        this.message = "Email invitata con successo, controlla la tua posta in entrata.";
        this.showToast = true;
        this.toPassword = true;
      },
      error: () => {
        this.isError = true;
        this.message = "Errore, riprova più tardi.";
        this.showToast = true;
      }
    });

    setTimeout(() => {
      this.showToast = false;
    }, 3000);
  }

  login() {
    const email: string = this.email;
    const password: string = this.form.get('password')!.value;

    this.authService.login(
      new AuthRequestDto({email, password})
    ).subscribe({
      next: (response) => {
        const authorization = response.headers.get('Authorization')
        //const refreshToken = response.headers.get('Refresh-Token');

        if (authorization) {
          const accessToken = authorization.replace('Bearer ', '');
          this.authService.setItemInStorage(accessToken, response.body!);
        }

        this.router.navigateByUrl(
          this.route.snapshot.queryParams['returnUrl'] || '/'
        ).then();
      },
      error: () => {
        this.form.get('password')!.setValue('');
        this.isError = true;
        this.message = "Email o password errati. Riprova grazie.";
        this.showToast = true;
      }
    });

    setTimeout(() => {
      this.showToast = false;
    }, 3000);
  }
}
