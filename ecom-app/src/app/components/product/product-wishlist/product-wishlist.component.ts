import { NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { CustomerNavbarComponent } from '../../customer/customer-navbar/customer-navbar.component';

@Component({
  selector: 'app-product-wishlist',
  imports: [NgIf, NgFor, CustomerNavbarComponent],
  templateUrl: './product-wishlist.component.html',
  styleUrl: './product-wishlist.component.css'
})
export class ProductWishlistComponent  implements OnInit {

  
  wishlistItems = [
    { id: 1, name: 'Product 1', description: 'This is a great product' },
    { id: 2, name: 'Product 2', description: 'This is another great product' }
  ];

  constructor() { }

  ngOnInit(): void {
  }

  
  addToWishlist(product: any): void {
    this.wishlistItems.push(product);
  }

  removeFromWishlist(item: any): void {
    this.wishlistItems = this.wishlistItems.filter(i => i.id !== item.id);
  }
}
