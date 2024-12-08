import { NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { CustomerNavbarComponent } from '../../customer/customer-navbar/customer-navbar.component';
import { WishlistService } from '../../../service/wishlist.service';
import { CustomerService } from '../../../service/customer.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-wishlist',
  imports: [CustomerNavbarComponent, NgFor, NgIf],
  templateUrl: './product-wishlist.component.html',
  styleUrl: './product-wishlist.component.css'
})
export class ProductWishlistComponent implements OnInit {

  product: any;
  wishlist: any[] = [];
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private wishlistService: WishlistService,
    private customerService: CustomerService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const username = localStorage.getItem('username'); // Retrieve username from local storage
    if (username) {
      // Fetch customer ID based on username
      this.customerService.getCustomerDetailsByUsername(username).subscribe({
        next: (data) => {
          const customerId = data.customerId;

          // Fetch wishlist for the customer
          this.wishlistService.getWishlistProducts(customerId).subscribe({
            next: (products) => {
              this.wishlist = products; // Populate wishlist with data from API

              // Loop through each product and update image paths
              this.wishlist.forEach((product: any) => {
                if (product.images && product.images.length > 0) {
                  product.images.forEach((image: any) => {
                    image.path = './images/' + image.fileName; // Update image path
                  });
                }
              });

            },
            error: (err) => {
              this.errorMessage = 'Failed to load wishlist. Please try again.';
              setTimeout(() => (this.errorMessage = ''), 5000); // Clear message after 5 seconds
            },
          });
        },
        error: (err) => {
          this.errorMessage =
            'Failed to fetch customer details. Please try again.';
          setTimeout(() => (this.errorMessage = ''), 5000); // Clear message after 5 seconds
        },
      });
    } else {
      this.errorMessage = 'Username not found in local storage. Please log in.';
      setTimeout(() => (this.errorMessage = ''), 5000); // Clear message after 5 seconds
    }
  }

  addToCart(item: any): void {
    // Check if the item already exists in the cart
    let cart = JSON.parse(localStorage.getItem('cart') || '[]');

    // Find if the item already exists in the cart by matching product ID
    const existingProduct = cart.find((cartItem: any) => cartItem.productId === item.id);

    if (existingProduct) {
      // Update the quantity if the product already exists
      existingProduct.quantity += 1;
      existingProduct.total = existingProduct.quantity * existingProduct.price;
    } else {
      // If product doesn't exist in the cart, add it
      const productDetails = {
        productId: item.id,
        name: item.name,
        price: item.price,
        quantity: 1, // Default quantity is 1
        total: item.price, // Initial total is price * 1
        image: item.image || "https://via.placeholder.com/100x100"
      };
      cart.push(productDetails);
    }

    // Save the updated cart to local storage
    localStorage.setItem('cart', JSON.stringify(cart));

    // Redirect to the cart page
    this.router.navigateByUrl("/cart");
  }


  // Method to remove product from wishlist and show message
  removeFromWishlist(item: any): void {
    this.wishlistService.deleteProductFromWishlist(item.id).subscribe({
      next: () => {
        this.wishlist = this.wishlist.filter((product) => product.id !== item.id); // Remove product from the list
        this.successMessage = `Product removed from your wishlist.`; // Display success message
        setTimeout(() => (this.successMessage = ''), 5000); // Clear message after 5 seconds
      },
      error: () => {
        this.errorMessage = 'Failed to remove the product from the wishlist. Please try again.'; // Display error message
        setTimeout(() => (this.errorMessage = ''), 5000); // Clear message after 5 seconds
      }
    });
  }
  
}

