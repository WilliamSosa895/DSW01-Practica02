import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const requiredRole = route.data['requiredRole'] as string | undefined;

  if (!requiredRole || authService.isMaster()) {
    return true;
  }

  return router.parseUrl('/empleados');
};
