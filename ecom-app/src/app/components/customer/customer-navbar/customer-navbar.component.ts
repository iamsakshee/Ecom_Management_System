import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-customer-navbar',
  imports: [RouterLink],
  templateUrl: './customer-navbar.component.html',
  styleUrl: './customer-navbar.component.css'
})
export class CustomerNavbarComponent {

  constructor(private router:Router){}


logout(){
    localStorage.clear();
    this.router.navigateByUrl('?msg=You have successfully logged out!!')
}

navigateToAddDetails(){
  this.router.navigateByUrl('/add-details');
}

navigateToCart(){
  this.router.navigateByUrl('/cart');
}

navigatetodashboard(){
  this.router.navigateByUrl('/dashboard');
}

navigateToWishlist(){
  this.router.navigateByUrl('/wishlist')
}
}
