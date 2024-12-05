// cart.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart = new BehaviorSubject<any[]>([]);  // Observable cart

  constructor() {}

  addToCart(product: any) {
    if (!product || !product.id) {
      console.error('Product or Product ID is undefined');
      return;  // Prevent the method from executing if the product is not valid
    }

    const currentCart = this.cart.getValue();
    const productIndex = currentCart.findIndex(item => item.id === product.id);

    if (productIndex !== -1) {
      // If the product already exists in the cart, increase its quantity
      currentCart[productIndex].quantity += 1;
    } else {
      // If product does not exist, add it to the cart
      product.quantity = 1;
      currentCart.push(product);
    }

    this.cart.next(currentCart);  // Update the cart
  }

  getCart() {
    return this.cart.asObservable();
  }
}
