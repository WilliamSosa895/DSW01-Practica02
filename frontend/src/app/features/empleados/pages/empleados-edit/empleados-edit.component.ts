import { Component } from '@angular/core';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import {
  Departamento,
  EmpleadoWriteRequest,
  EmpleadosApiService
} from '../../services/empleados-api.service';
import { RolEmpleado } from '../../../../core/auth/session.model';

@Component({
  selector: 'app-empleados-edit',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './empleados-edit.component.html',
  styleUrl: './empleados-edit.component.css'
})
export class EmpleadosEditComponent {
  private static readonly PASSWORD_POLICY = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[^A-Za-z0-9])[\x20-\x7E]{8,64}$/;

  readonly form = new FormGroup({
    nombre: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.maxLength(100)] }),
    email: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.email] }),
    direccion: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.maxLength(100)] }),
    telefono: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.maxLength(100)] }),
    contrasena: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(64),
        Validators.pattern(EmpleadosEditComponent.PASSWORD_POLICY)
      ]
    }),
    departamentoClave: new FormControl(0, { nonNullable: true, validators: [Validators.required, Validators.min(1)] }),
    rol: new FormControl<RolEmpleado>('STANDARD', { nonNullable: true, validators: [Validators.required] }),
    version: new FormControl(0, { nonNullable: true })
  });

  departamentos: Departamento[] = [];
  errorMessage = '';
  successMessage = '';
  loading = false;
  readonly clave: number | null;

  constructor(
    private readonly empleadosApi: EmpleadosApiService,
    private readonly route: ActivatedRoute,
    private readonly router: Router
  ) {
    const routeClave = this.route.snapshot.paramMap.get('clave');
    this.clave = routeClave ? Number(routeClave) : null;
    this.loadDepartamentos();
    if (this.clave) {
      this.loadEmpleado(this.clave);
    }
  }

  get isEditMode(): boolean {
    return this.clave !== null;
  }

  submit(): void {
    if (this.form.invalid || this.loading) {
      this.form.markAllAsTouched();
      return;
    }
    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const raw = this.form.getRawValue();
    const payload: EmpleadoWriteRequest = {
      nombre: raw.nombre,
      email: raw.email,
      direccion: raw.direccion,
      telefono: raw.telefono,
      contrasena: raw.contrasena,
      departamentoClave: raw.departamentoClave,
      rol: raw.rol,
      version: raw.version
    };

    const request$ = this.isEditMode && this.clave
      ? this.empleadosApi.update(this.clave, payload)
      : this.empleadosApi.create(payload);

    request$.subscribe({
      next: () => {
        this.loading = false;
        this.successMessage = this.isEditMode ? 'Empleado actualizado correctamente.' : 'Empleado creado correctamente.';
        void this.router.navigate(['/empleados']);
      },
      error: (error: unknown) => {
        this.loading = false;
        this.errorMessage = error instanceof Error ? error.message : 'No fue posible guardar los cambios.';
        if (this.isEditMode && this.clave && this.errorMessage.toLowerCase().includes('version')) {
          this.loadEmpleado(this.clave);
        }
      }
    });
  }

  private loadDepartamentos(): void {
    this.empleadosApi.listDepartamentos().subscribe({
      next: (response) => {
        this.departamentos = response.content;
        if (!this.isEditMode && this.departamentos.length > 0) {
          this.form.patchValue({ departamentoClave: this.departamentos[0].clave });
        }
      },
      error: () => {
        this.errorMessage = 'No se pudo cargar la lista de departamentos.';
      }
    });
  }

  private loadEmpleado(clave: number): void {
    this.empleadosApi.getByClave(clave).subscribe({
      next: (empleado) => {
        this.form.patchValue({
          nombre: empleado.nombre,
          email: empleado.email,
          direccion: empleado.direccion,
          telefono: empleado.telefono,
          contrasena: '',
          departamentoClave: empleado.departamento.clave,
          rol: empleado.rol,
          version: empleado.version
        });
      },
      error: (error: unknown) => {
        this.errorMessage = error instanceof Error ? error.message : 'No se pudo cargar el empleado.';
      }
    });
  }
}
