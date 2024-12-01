import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../service/auth.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-signup',
  imports: [NgIf,FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  name: string ="";
  username: string ="";
  password: string=""; 
  successMsg: string | undefined;
  errorMsg: string | undefined;

  constructor(private authService:AuthService,private router:Router){}

  onSignUp(){
    this.authService.signUp({
      name: this.name,
      username: this.username,
      password: this.password 
    }).subscribe({
      next: (data)=>{
        this.successMsg = 'Sign-Up Success, Please login!';
      },
      error: (err)=>{
        this.errorMsg= err.error.msg;
      }
    })
  }
  

}
