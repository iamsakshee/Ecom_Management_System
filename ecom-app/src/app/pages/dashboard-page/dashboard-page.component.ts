import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CustomerService } from '../../service/customer.service';
import { CustomerNavbarComponent } from "../../components/customer/customer-navbar/customer-navbar.component";
import { NgFor, NgIf } from '@angular/common';
import { ProductService } from '../../service/product.service';
import { CartService } from '../../service/cart.service';
import { WishlistService } from '../../service/wishlist.service';
@Component({
  selector: 'app-dashboard-page',
  imports: [CustomerNavbarComponent, NgFor, RouterLink, NgIf],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.css'
})

export class DashboardPageComponent implements OnInit {

  products: any[] = []; 
  style: any;
  customerId: any;
  successMessage: string = '';
  errorMessage: string = '';

  
  constructor(private productService: ProductService, 
              private wishlistService: WishlistService,  
              private customerService: CustomerService,
              private router: Router) {}

  ngOnInit(): void {
   
    this.getAllProducts();
  }

  // Fetch all products
  getAllProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.products.forEach(p=>{
          p.images.forEach((i:any) => {
            i.path = './images/'+ i.fileName
          });
        })
        console.log(this.products)
        
       
      },
      error: (error) => {
        console.error('There was an error fetching the products!', error);
      }
    });
  }


  addToWishlist(product: any): void {
    const username = localStorage.getItem('username'); // Retrieve username from local storage
  
    if (username) {
      this.customerService.getCustomerDetailsByUsername(username).subscribe({
        next: (data) => {
          this.customerId = data.customerId;
          const productId = product.id;
  
          this.wishlistService.addProductToWishlist(this.customerId, productId).subscribe({
            next: () => {
              this.successMessage = 'Product added to wishlist successfully!';
              this.errorMessage = 'Product added to wishlist successfully!'; // Clear error message
              setTimeout(() => (this.successMessage = ''), 5000); // Clear message after 5 seconds
            },
            error: (error) => {
              if (error.status === 400) {
                this.errorMessage = 'Product already exists in wishlist.';
              }
              this.successMessage = ''; // Clear success message
              setTimeout(() => (this.errorMessage = ''), 5000); // Clear message after 5 seconds
            },
          });
        }
      })
    }
  }
}
      
  

