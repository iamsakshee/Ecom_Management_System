import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../service/auth.service';
import { NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-signup',
  imports: [FormsModule, NgIf, RouterLink],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

  name: string ="";
  username: string ="";
  password: string=""; 
  errorMsg: string | undefined;
  successMsg: string | undefined;

  constructor(private authService: AuthService){}

  onSignUp(){
    // console.log(this.name);
    // console.log(this.username);
    // console.log(this.password);
    this.authService.signUp({
      username: this.username,
      password: this.password
    }).subscribe({
      next: (data)=>{
        this.successMsg = 'Sign Up Success, Please login';
      },
      error: (err)=>{
        console.log(err)
        this.errorMsg = 'Username already in use!!'; 
      }
    })
   
  }
}