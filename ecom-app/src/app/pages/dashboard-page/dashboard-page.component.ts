import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CustomerService } from '../../service/customer.service';
import { CustomerNavbarComponent } from "../../components/customer/customer-navbar/customer-navbar.component";
import { NgFor } from '@angular/common';
import { ProductService } from '../../service/product.service';
import { CartService } from '../../service/cart.service';
@Component({
  selector: 'app-dashboard-page',
  imports: [CustomerNavbarComponent, NgFor, RouterLink],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.css'
})
export class DashboardPageComponent implements OnInit {

  products: any[] = []; 
style: any;
  
  constructor(private productService: ProductService, private cartService: CartService, private router: Router) {}

  ngOnInit(): void {
    
    this.getAllProducts();
  }

  getAllProducts(): void {
   
    this.productService.getAllProducts().subscribe({
      next: (data) => {
       
        this.products = data;
      },
      error: (error) => {
        
        console.error('There was an error fetching the products!', error);
      }
    });
  }

  wishlist: any[] = []; // Array to store wishlist items

  addToWishlist(product: any) {
    if (!this.wishlist.some(item => item.id === product.id)) {
      this.wishlist.push(product);
      alert(`${product.name} has been added to your wishlist!`);
    } else {
      alert(`${product.name} is already in your wishlist!`);
    }
    console.log('Wishlist:', this.wishlist); // Debugging
  }

  
}
  

