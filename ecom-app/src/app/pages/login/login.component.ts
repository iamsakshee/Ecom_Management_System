import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { FormsModule, NgModel } from '@angular/forms';
import { NgIf } from '@angular/common';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-login-page',
  imports: [NgIf, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  successMsg: string | undefined;
  errorMsg: string | undefined;
  msg: string | undefined;

  constructor(
    private authService: AuthService,
    private router: Router,
    private actRoute: ActivatedRoute
  ) {
    this.actRoute.queryParams.subscribe((p) => {
      this.msg = p['msg'];
    });
  }

  onLogin() {
    this.authService
      .login({
        username: this.username,
        password: this.password,
      })
      .subscribe({
        next: (data) => {
          let token = data.token;
          // console.log(token);
          // console.log(data);
          this.authService.getUserDetails(token).subscribe({
            next: (data) => {
              localStorage.setItem('token', token);
              localStorage.setItem('username', data.username);
              localStorage.setItem('name', data.name);
              let role = data.role;
              switch (role) {
                case 'EXECUTIVE':
                  this.router.navigateByUrl('/executive');
                  break;
                case 'CUSTOMER':
                  this.router.navigateByUrl('/dashboard');

                  break;
                default:
                  this.router.navigateByUrl('/broken-link');
                  break;
              }
            },
            error: (err) => {
              this.errorMsg = err.error.msg;
            },
          });
        },
        error: (err) => {
          this.errorMsg = err.error.msg;
        },
      });
  }
  navigateToSignUp() {
    this.router.navigateByUrl('/sign-up');
  }
  navigateToForgotPassword() {
    this.router.navigateByUrl('/forgot-password');
  }
}