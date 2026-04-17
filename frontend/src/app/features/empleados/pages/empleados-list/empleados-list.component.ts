import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../../core/auth/auth.service';
import { Empleado, EmpleadosApiService } from '../../services/empleados-api.service';

@Component({
  selector: 'app-empleados-list',
  imports: [RouterLink],
  templateUrl: './empleados-list.component.html',
  styleUrl: './empleados-list.component.css'
})
export class EmpleadosListComponent {
  empleados: Empleado[] = [];
  loading = false;
  errorMessage = '';
  page = 0;
  totalPages = 0;

  constructor(
    private readonly empleadosApi: EmpleadosApiService,
    private readonly authService: AuthService
  ) {
    this.load();
  }

  get isMaster(): boolean {
    return this.authService.isMaster();
  }

  load(page = this.page): void {
    this.loading = true;
    this.errorMessage = '';
    this.empleadosApi.list(page, 5).subscribe({
      next: (response) => {
        this.empleados = response.content;
        this.page = response.page;
        this.totalPages = response.totalPages;
        this.loading = false;
      },
      error: (error: unknown) => {
        this.loading = false;
        this.errorMessage = error instanceof Error ? error.message : 'No se pudo consultar el listado.';
      }
    });
  }

  remove(clave: number): void {
    if (!this.isMaster) {
      return;
    }
    const confirmed = window.confirm('Esta seguro de eliminar este empleado?');
    if (!confirmed) {
      return;
    }
    this.empleadosApi.delete(clave).subscribe({
      next: () => this.load(),
      error: (error: unknown) => {
        this.errorMessage = error instanceof Error ? error.message : 'No se pudo eliminar el empleado.';
      }
    });
  }

  prevPage(): void {
    if (this.page > 0) {
      this.load(this.page - 1);
    }
  }

  nextPage(): void {
    if (this.page + 1 < this.totalPages) {
      this.load(this.page + 1);
    }
  }
}
