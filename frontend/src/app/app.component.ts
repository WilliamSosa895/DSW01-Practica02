import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AsyncPipe } from '@angular/common';
import { AuthService } from './core/auth/auth.service';
import { TopbarComponent } from './shared/components/topbar/topbar.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AsyncPipe, TopbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  constructor(public readonly authService: AuthService) {}
}
