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
    const username = localStorage.getItem('username'); 
    if (username) {
      this.customerService.getCustomerDetailsByUsername(username).subscribe({
        next: (data) => {
          const customerId = data.customerId;
          this.wishlistService.getWishlistProducts(customerId).subscribe({
            next: (products) => {
              this.wishlist = products;
              this.wishlist.forEach((product: any) => {
                if (product.images && product.images.length > 0) {
                  product.images.forEach((image: any) => {
                    image.path = './images/' + image.fileName; 
                  });
                }
              });
            },
            error: (err) => {
              this.errorMessage = 'Failed to load wishlist. Please try again.';
            },
          });
        },
        error: (err) => {
          this.errorMessage =
            'Failed to fetch customer details. Please try again.';
        },
      });
    } 
  }

  removeFromWishlist(item: any): void {
    this.wishlistService.deleteProductFromWishlist(item.id).subscribe({
      next: () => {
        this.wishlist = this.wishlist.filter((product) => product.id !== item.id); 
        this.successMessage = `Product removed from your wishlist.`; 
        
      },
      error: () => {
        this.errorMessage = 'Failed to remove the product from the wishlist. Please try again.'; 
       
      }
    });
  }

  addToCart(item: any): void {
    
    let cart = JSON.parse(localStorage.getItem('cart') || '[]');

    const existingProduct = cart.find((cartItem: any) => cartItem.productId === item.id);

    if (existingProduct) {
    
      existingProduct.quantity += 1;
      existingProduct.total = existingProduct.quantity * existingProduct.price;
    } else {
     
      const productDetails = {
        productId: item.id,
        name: item.name,
        price: item.price,
        quantity: 1, 
        total: item.price,
        image: item.image || "https://via.placeholder.com/100x100"
      };
      cart.push(productDetails);
    }
    localStorage.setItem('cart', JSON.stringify(cart));

    this.router.navigateByUrl("/cart");
  }


 
 
  
}

