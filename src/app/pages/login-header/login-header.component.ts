import { Component } from '@angular/core';
import { Router, RouterLink, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login-header',
  imports: [RouterLink],
  templateUrl: './login-header.component.html',
  styleUrl: './login-header.component.css'
})
export class LoginHeaderComponent {
  
  constructor(private router: Router){}

  navigateTologin(){
    this.router.navigateByUrl("/login");
  }

  navigateToSignUp(){
    this.router.navigateByUrl("/sign-up");
  }

}
