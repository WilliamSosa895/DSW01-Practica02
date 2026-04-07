import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
	{
		path: 'login',
		loadComponent: () => import('./features/auth/login/login.component').then((m) => m.LoginComponent)
	},
	{
		path: 'empleados',
		canActivate: [authGuard],
		loadComponent: () => import('./features/empleados/pages/empleados-list/empleados-list.component').then((m) => m.EmpleadosListComponent)
	},
	{
		path: 'empleados/nuevo',
		canActivate: [authGuard, roleGuard],
		data: { requiredRole: 'MASTER' },
		loadComponent: () => import('./features/empleados/pages/empleados-edit/empleados-edit.component').then((m) => m.EmpleadosEditComponent)
	},
	{
		path: 'empleados/:clave/editar',
		canActivate: [authGuard, roleGuard],
		data: { requiredRole: 'MASTER' },
		loadComponent: () => import('./features/empleados/pages/empleados-edit/empleados-edit.component').then((m) => m.EmpleadosEditComponent)
	},
	{
		path: 'empleados/:clave',
		canActivate: [authGuard],
		loadComponent: () => import('./features/empleados/pages/empleado-detail/empleado-detail.component').then((m) => m.EmpleadoDetailComponent)
	},
	{
		path: '',
		pathMatch: 'full',
		redirectTo: 'empleados'
	},
	{
		path: '**',
		redirectTo: 'empleados'
	}
];
