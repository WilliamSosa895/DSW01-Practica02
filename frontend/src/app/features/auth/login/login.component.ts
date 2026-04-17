import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/auth/auth.service';
import { buildLoginForm } from './login.form';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  readonly form = buildLoginForm();
  errorMessage = '';
  loading = false;

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  submit(): void {
    if (this.form.invalid || this.loading) {
      this.form.markAllAsTouched();
      return;
    }

    const { email, password } = this.form.getRawValue();
    this.loading = true;
    this.errorMessage = '';

    this.authService.login(email, password).subscribe({
      next: () => {
        this.loading = false;
        void this.router.navigate(['/empleados']);
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'Credenciales invalidas o usuario sin acceso.';
      }
    });
  }
}
