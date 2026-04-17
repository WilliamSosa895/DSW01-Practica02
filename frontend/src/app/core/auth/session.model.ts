export type RolEmpleado = 'MASTER' | 'STANDARD';

export interface Session {
  username: string;
  rol: RolEmpleado;
  authenticated: boolean;
}

export interface ErrorResponse {
  status: number;
  error: string;
  message: string;
  path: string;
  timestamp: string;
  code?: string;
  action?: string;
}
