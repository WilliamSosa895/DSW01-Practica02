import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { ErrorResponse } from '../auth/session.model';

export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return next(req).pipe(
    catchError((error: unknown) => {
      if (!(error instanceof HttpErrorResponse)) {
        return throwError(() => error);
      }

      const backendError = error.error as Partial<ErrorResponse> | undefined;
      const message = backendError?.message ?? 'Error inesperado en la solicitud';

      if (error.status === 401) {
        authService.logout(false);
        void router.navigate(['/login'], {
          queryParams: { reason: 'unauthorized' }
        });
      }

      if (error.status === 403) {
        window.alert(backendError?.action ?? 'No tiene permisos para ejecutar esta accion.');
      }

      if (error.status === 409 && backendError?.code === 'CONCURRENCY_CONFLICT') {
        window.alert('Registro desactualizado. Se recargara para reintentar.');
      }

      return throwError(() => new Error(message));
    })
  );
};
