import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { RolEmpleado } from '../../../core/auth/session.model';

export interface Departamento {
  clave: number;
  nombre: string;
}

export interface DepartamentoPage {
  content: Departamento[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface Empleado {
  clave: number;
  nombre: string;
  email: string;
  direccion: string;
  telefono: string;
  rol: RolEmpleado;
  version: number;
  departamento: Departamento;
}

export interface EmpleadoPage {
  content: Empleado[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface EmpleadoWriteRequest {
  nombre: string;
  email: string;
  direccion: string;
  telefono: string;
  contrasena: string;
  rol: RolEmpleado;
  departamentoClave: number;
  version?: number;
}

@Injectable({ providedIn: 'root' })
export class EmpleadosApiService {
  private readonly apiUrl = environment.apiBaseUrl;

  constructor(private readonly http: HttpClient) {}

  list(page = 0, size = 5): Observable<EmpleadoPage> {
    return this.http.get<EmpleadoPage>(`${this.apiUrl}/empleados?page=${page}&size=${size}`);
  }

  getByClave(clave: number): Observable<Empleado> {
    return this.http.get<Empleado>(`${this.apiUrl}/empleados/${clave}`);
  }

  create(payload: EmpleadoWriteRequest): Observable<Empleado> {
    return this.http.post<Empleado>(`${this.apiUrl}/empleados`, payload);
  }

  update(clave: number, payload: EmpleadoWriteRequest): Observable<Empleado> {
    return this.http.put<Empleado>(`${this.apiUrl}/empleados/${clave}`, payload);
  }

  delete(clave: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/empleados/${clave}`);
  }

  listDepartamentos(): Observable<DepartamentoPage> {
    return this.http.get<DepartamentoPage>(`${this.apiUrl}/departamentos?page=0&size=5`);
  }
}
