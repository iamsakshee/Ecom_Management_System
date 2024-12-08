import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartKey = 'cart';

  // Get the cart from localStorage
  getCart(): any[] {
    const cart = localStorage.getItem(this.cartKey);
    return cart ? JSON.parse(cart) : [];
  }

  // Add product to cart
  addToCart(product: any, quantity: number = 1, gst: number = 0.18): void {
    let cart = this.getCart();
    const productDetails = {
      productId: product.id,
      name: product.name,
      price: product.price,
      quantity: quantity,
      subTotal: product.price,
      gst : 0.18,
      total: product.price * quantity * gst,
      images: product.images || "https://via.placeholder.com/100x100"
    };
    
    // Check if the product already exists in the cart
    const existingProduct = cart.find(item => item.productId === productDetails.productId);
    if (existingProduct) {
      // If product exists, update the quantity
      existingProduct.quantity += quantity;
      existingProduct.total = existingProduct.price * existingProduct.quantity;
    } else {
      // If product doesn't exist, add new product to cart
      cart.push(productDetails);
    }

    // Save the updated cart to localStorage
    localStorage.setItem(this.cartKey, JSON.stringify(cart));
  }

  // Calculate the total price of all items in the cart
  getSubtotal(): number {
    const cart = this.getCart();
    return cart.reduce((total, item) => total + item.total, 0);
  }

  // Clear the cart
  clearCart(): void {
    localStorage.removeItem(this.cartKey);
  }
}
