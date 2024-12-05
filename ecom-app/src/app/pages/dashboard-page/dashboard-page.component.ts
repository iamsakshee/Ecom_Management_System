import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerService } from '../../service/customer.service';
import { CustomerNavbarComponent } from "../../components/customer/customer-navbar/customer-navbar.component";
@Component({
  selector: 'app-dashboard-page',
  imports: [CustomerNavbarComponent],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.css'
})
export class DashboardPageComponent {

  name: any;

  constructor(private router : Router, private customerService:CustomerService){
    this.name = localStorage.getItem('username');}

  

 

  

}
