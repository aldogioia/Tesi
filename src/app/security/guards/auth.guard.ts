import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../../service/auth/auth.service";

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  if (!authService.getAccessToken()) {
    router.navigate(['/login'], {queryParams: {returnUrl: state.url}}).then();
    return false;
  }
  return true;
};
