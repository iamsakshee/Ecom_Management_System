import { Component, OnInit } from '@angular/core';
import { CustomerNavbarComponent } from '../customer-navbar/customer-navbar.component';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer-cart',
  imports: [CustomerNavbarComponent, NgFor, FormsModule],
  templateUrl: './customer-cart.component.html',
  styleUrl: './customer-cart.component.css'
})
export class CustomerCartComponent implements OnInit {
  cart: any[] = [];
  totalAmount: number = 0;

  constructor(private router: Router){}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart() {
    // Retrieve cart items from local storage
    this.cart = JSON.parse(localStorage.getItem('cart') || '[]');
    this.calculateTotalAmount();
  }

  calculateTotalAmount() {
    this.totalAmount = this.cart.reduce((acc, item) => acc + item.total, 0);
  }

  removeItem(productId: number) {
    // Remove item from cart
    this.cart = this.cart.filter(item => item.productId !== productId);
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.calculateTotalAmount();
  }

  updateItemTotal(item: any): void {
    item.total = item.price * item.quantity;
  }

  continueShopping(){
    this.router.navigateByUrl("/dashboard")
  }

}


