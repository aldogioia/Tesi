import { HttpEvent, HttpHandlerFn, HttpRequest, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError, catchError } from "rxjs";
import {AuthService} from "../service/auth/auth.service";
import {inject} from "@angular/core";
import {Router} from "@angular/router";


export function tokenInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<any>> {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.getAccessToken() && !req.headers.has('Authorization')) {
    req = req.clone({
      setHeaders: {
        Authorization: "Bearer " + authService.getAccessToken()
      }
    });
  }

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        authService.removeItemFromStorage()
        router.navigate(['/login']).then();
      }
      return throwError(() => error);
    })
  );
}
