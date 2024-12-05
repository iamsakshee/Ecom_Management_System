 
 import { NgFor } from '@angular/common';
 import { Component, OnDestroy, OnInit  } from '@angular/core';
 import { RouterOutlet } from '@angular/router';
 import { NavbarComponent } from "./components/navbar/navbar.component";
 import { LoginHeaderComponent } from "./components/login-header/login-header.component";
import { DashboardPageComponent } from "./pages/dashboard-page/dashboard-page.component";
import { LoginComponent } from "./pages/login/login.component";
 
 @Component({
   selector: 'app-root',
   imports: [RouterOutlet],
   templateUrl: './app.component.html',
   styleUrl: './app.component.css'
 })
 export class AppComponent{
   
 }