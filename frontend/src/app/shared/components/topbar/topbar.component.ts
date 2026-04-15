import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Session } from '../../../core/auth/session.model';
import { AuthService } from '../../../core/auth/auth.service';

@Component({
  selector: 'app-topbar',
  imports: [RouterLink],
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.css'
})
export class TopbarComponent {
  @Input({ required: true }) session!: Session;

  constructor(private readonly authService: AuthService) {}

  logout(): void {
    this.authService.logout();
  }
}
