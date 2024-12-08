import { Component, OnInit } from '@angular/core';
import { CustomerNavbarComponent } from '../customer-navbar/customer-navbar.component';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-customer-cart',
  imports: [CustomerNavbarComponent, NgFor, FormsModule],
  templateUrl: './customer-cart.component.html',
  styleUrl: './customer-cart.component.css'
})
export class CustomerCartComponent implements OnInit {

  cart: any[] = []; // Array to store cart items
  subtotal: number = 0; // Subtotal of the cart
  gst: number = 0; // GST calculated from the subtotal
  total: number = 0; // Total price including GST
  productId: any;
  quantity: any;

  constructor(private router: Router){}

  ngOnInit(): void {
    this.loadCart();
    this.calculateCartTotals();
  }

  // Load the cart from local storage (or an empty array if nothing is saved)
  loadCart() {
    const storedCart = localStorage.getItem('cart');
    console.log(storedCart)
    if (storedCart) {
      this.cart = JSON.parse(storedCart);
    } else {
      this.cart = [];
    }
  }

  // Save the cart to local storage whenever there are changes
  saveCart() {
    localStorage.setItem('cart', JSON.stringify(this.cart));
  }

  // Update the quantity of an item
  updateQuantity(item: any, change: number): void {
    // Ensure that the quantity is always a positive number
    if (item.quantity + change >= 1) {
      item.quantity += change;
      this.updateItemTotal(item);
      this.saveCart(); // Save the updated cart to local storage
      this.calculateCartTotals(); // Recalculate totals after quantity change
    }
  }

  // Update the total for a particular item based on its quantity
  updateItemTotal(item: any): void {
    item.total = item.price * item.quantity;
  }

  // Calculate the cart's subtotal, GST, and total
  calculateCartTotals(): void {
    this.subtotal = this.cart.reduce((acc, item) => acc + item.total, 0);
    this.gst = this.subtotal * 0.18; // 18% GST
    this.total = this.subtotal + this.gst;
  }

  // Remove an item from the cart
  removeItem(productId: string): void {
    this.cart = this.cart.filter(item => item.productId !== productId);
    this.saveCart(); // Save the updated cart to local storage
    this.calculateCartTotals(); // Recalculate totals after item removal
  }

  // Continue shopping button - can be redirected to another page
  continueShopping() {
    // Redirect to another page or handle the continue shopping logic
    console.log('Continuing shopping...');
  }


  goToCheckout(): void {
    // Pass cart totals to the checkout page
    this.router.navigate(['/checkout'], { 
      queryParams: { 
        productId : this.productId,
        quantiy: this.quantity,
        subtotal: this.subtotal, 
        total: this.total 
      }
    });
  }

}

