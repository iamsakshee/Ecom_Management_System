import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CustomerService } from '../../../service/customer.service';
import { ProductService } from '../../../service/product.service';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-customer-navbar',
  imports: [RouterLink, FormsModule],
  templateUrl: './customer-navbar.component.html',
  styleUrl: './customer-navbar.component.css'
})
export class CustomerNavbarComponent {
  customerId: any;
  searchQuery: string = ''; // Bind to the search input field
  searchResults: any[] = []; // To store search results
  allProducts: any[] = []; // Store all products

  constructor(
    private router: Router,
    private customerService: CustomerService,
    private productService: ProductService
  ) {
    // Fetch all products initially
    this.productService.getAllProducts().subscribe({
      next: (response) => {
        this.allProducts = response; // Store all products in the allProducts array
        this.searchResults = response; // Initially display all products
      },
      error: (error) => {
        console.error('Error fetching products', error); // Handle error
      }
    });
  }

  logout() {
    localStorage.clear();
    this.router.navigateByUrl('?msg=You have successfully logged out!!')
  }

  navigateToAddDetails() {
    this.router.navigateByUrl('/add-details');
  }

  navigateToCart() {
    this.router.navigateByUrl('/cart');
  }

  navigatetodashboard() {
    this.router.navigateByUrl('/dashboard');
  }

  navigateToWishlist() {
    this.router.navigateByUrl('/wishlist')
  }

  navigateToMyOrders(): void {
    const username = localStorage.getItem('username');  // Get the username from localStorage

    if (username) {
      // Fetch customer ID based on the username
      this.customerService.getCustomerDetailsByUsername(username).subscribe({
        next: (response) => {
          const customerId = response.customerId;
          if (customerId) {
            this.router.navigate(['/order', customerId]);  // Navigate with the customerId
          } else {
            console.error('Customer ID not found');
          }
        },
        error: (error) => {
          console.error('Error fetching customer details', error);
        }
      });
    } else {
      console.error('Username not found in localStorage');
    }
  }

  onSearch() {
    if (this.searchQuery.trim()) {
      // Filter products based on the search query
      this.searchResults = this.allProducts.filter(product =>
        product.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    } else {
      // If the search query is empty, show all products
      this.searchResults = this.allProducts;
    }
  }
}
 
