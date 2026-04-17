import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, of, tap, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { Session } from './session.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly apiUrl = environment.apiBaseUrl;
  private readonly tokenKey = 'auth_basic_token';
  private readonly sessionSubject = new BehaviorSubject<Session | null>(null);

  readonly session$ = this.sessionSubject.asObservable();

  constructor(
    private readonly http: HttpClient,
    private readonly router: Router
  ) {
    this.restoreSession();
  }

  get token(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isAuthenticated(): boolean {
    return this.sessionSubject.value?.authenticated === true;
  }

  isMaster(): boolean {
    return this.sessionSubject.value?.rol === 'MASTER';
  }

  login(email: string, password: string): Observable<Session> {
    const token = btoa(`${email}:${password}`);
    localStorage.setItem(this.tokenKey, token);
    return this.fetchContext().pipe(
      tap((session) => this.sessionSubject.next(session)),
      catchError((error) => {
        this.logout(false);
        return throwError(() => error);
      })
    );
  }

  fetchContext(): Observable<Session> {
    return this.http.get<Session>(`${this.apiUrl}/empleados/contexto`).pipe(
      tap((session) => this.sessionSubject.next(session))
    );
  }

  logout(redirect = true): void {
    localStorage.removeItem(this.tokenKey);
    this.sessionSubject.next(null);
    if (redirect) {
      void this.router.navigate(['/login']);
    }
  }

  private restoreSession(): void {
    if (!this.token) {
      return;
    }
    this.fetchContext()
      .pipe(
        catchError(() => {
          this.logout(false);
          return of(null);
        })
      )
      .subscribe();
  }
}
