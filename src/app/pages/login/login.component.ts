import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule,NgIf],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {

  username: string="";
  password: string="";
  successMsg: string | undefined;
  errorMsg:string | undefined;

  constructor(private authService: AuthService, private router: Router){}

  onLogin(){
    this.authService.login({
      username: this.username,
      password: this.password
    }).subscribe({
      next: (data)=>{
         let token = data.token; 
        this.authService.getUserDetails(token).subscribe({
          next: (data)=>{
            localStorage.setItem('token', token); 
            localStorage.setItem('username', data.username);
            localStorage.setItem('name', data.name);
            let role = data.role; 
            // switch(role){
            //   case 'MANAGER':
            //     //console.log('i vl take you to customer screen');
            //     this.router.navigateByUrl("/manager");
            //     break; 
            //   case 'EXECUTIVE':
            //     //console.log('i vl take you to executive screen')
            //     this.router.navigateByUrl("/executive");
            //     break; 
            //   default: 
            //     this.router.navigateByUrl("/broken-link");
            //     break; 
            // }
          },
          error: (err)=>{
            this.errorMsg = err.error.msg; 
          }
        })
      },
      error: (err)=>{
        this.errorMsg = err.error.msg; 
      }
    })
  }
}
