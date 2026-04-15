import { Component } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Empleado, EmpleadosApiService } from '../../services/empleados-api.service';

@Component({
  selector: 'app-empleado-detail',
  imports: [RouterLink],
  templateUrl: './empleado-detail.component.html',
  styleUrl: './empleado-detail.component.css'
})
export class EmpleadoDetailComponent {
  empleado: Empleado | null = null;
  errorMessage = '';

  constructor(
    private readonly empleadosApi: EmpleadosApiService,
    private readonly route: ActivatedRoute
  ) {
    const claveParam = this.route.snapshot.paramMap.get('clave');
    const clave = claveParam ? Number(claveParam) : NaN;
    if (Number.isNaN(clave)) {
      this.errorMessage = 'Clave de empleado invalida.';
      return;
    }

    this.empleadosApi.getByClave(clave).subscribe({
      next: (response) => {
        this.empleado = response;
      },
      error: (error: unknown) => {
        this.errorMessage = error instanceof Error ? error.message : 'No se pudo cargar el detalle.';
      }
    });
  }
}
