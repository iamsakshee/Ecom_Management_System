import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoginHeaderComponent } from "./pages/login-header/login-header.component";
import { LoginComponent } from "./pages/login/login.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LoginHeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {


  
}
