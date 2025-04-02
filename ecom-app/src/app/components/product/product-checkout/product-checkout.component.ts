import { NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from '../../../service/order.service';
import { CartService } from '../../../service/cart.service';
import { CustomerService } from '../../../service/customer.service';
import { ProductService } from '../../../service/product.service';
import { CustomerNavbarComponent } from "../../customer/customer-navbar/customer-navbar.component";
import jsPDF from 'jspdf';

@Component({
  selector: 'app-product-checkout',
  imports: [NgIf, CustomerNavbarComponent],
  templateUrl: './product-checkout.component.html',
  styleUrl: './product-checkout.component.css'
})
export class ProductCheckoutComponent implements OnInit {

  customerId: any;
  productId: any;
  quantity: any;
  username: string | null = null; // Assuming you store the username in local storage
  successMessage: any;
  subTotal: any;
  total: any;
  msg: any;

  constructor(
    private orderService: OrderService,
    private customerService: CustomerService, // For fetching customer ID
    private router: Router
  ) { }

  ngOnInit(): void {
    // Fetch username from local storage
    this.username = localStorage.getItem('username');
    console.log('Username:', this.username);

    // Fetch productId and quantity from the cart in local storage
    const cartData = localStorage.getItem('cart');
    if (cartData) {
      const cartArray = JSON.parse(cartData);
      if (cartArray.length > 0) {
        this.productId = cartArray[0].productId;
        this.quantity = cartArray[0].quantity;
        this.subTotal = cartArray[0].subTotal;
        this.total = cartArray[0].total;
        console.log('Fetched productId:', this.productId);
        console.log('Fetched quantity:', this.quantity);
        console.log('fetched subtotal', this.subTotal)
      } else {
        console.error('Cart is empty.');
      }
    } else {
      console.error('No cart data found in local storage.');
    }

    // Fetch customer ID if username is available
    if (this.username) {
      this.fetchCustomerIdByUsername(this.username);
    }
    
  }


  // Fetch the customer ID using the username
  fetchCustomerIdByUsername(username: string): void {
    this.customerService.getCustomerDetailsByUsername(username).subscribe({
      next: (data) => {
        this.customerId = data.customerId; // Assuming API returns { customerId: ... }
        console.log('Customer ID fetched:', this.customerId);
      },
      error: (error) => {
        console.error('Error fetching customer ID:', error);
      }
    });
  }

  // Handle product purchase
  
// processPayment(): void {
//   console.log('customerId:', this.customerId);
//   console.log('productId:', this.productId);
//   console.log('quantity:', this.quantity);

//   if (!this.customerId || !this.productId || !this.quantity) {
//     alert('Invalid customer, product ID, or quantity.');
//     return;
//   }

//   this.orderService.purchaseProduct(this.customerId, this.productId, this.quantity)
//     .subscribe({
//       next: (response: any) => {
//         this.successMessage = "Product purchased successfully!";
//         console.log(response);

//         // Save order details in local storage for the invoice page
//         localStorage.setItem('invoiceData', JSON.stringify({
//           productId: this.productId,
//           quantity: this.quantity,
//           total: this.total
//         }));

//         // Redirect to invoice page after a delay
//         setTimeout(() => {
//           this.router.navigate(['/invoice']);
//         }, 2000);
//       },
//       error: (error) => {
//         console.error('Error processing payment:', error);
//       }
//     });
// }

processPayment(): void {
  console.log('customerId:', this.customerId);
  console.log('productId:', this.productId);
  console.log('quantity:', this.quantity);

  if (!this.customerId || !this.productId || !this.quantity) {
    alert('Invalid customer, product ID, or quantity.');
    return;
  }

  this.customerService.getCustomerDetailsByUsername(this.username!).subscribe({
    next: (customerData: any) => {
      this.orderService.purchaseProduct(this.customerId, this.productId, this.quantity)
        .subscribe({
          next: (response: any) => {
            this.successMessage = "Product purchased successfully!";
            console.log(response);

            // Save order details in local storage for the invoice page
            localStorage.setItem('invoiceData', JSON.stringify({
              productId: this.productId,
              quantity: this.quantity,
              total: this.total,
              customerName: customerData.name, // Store name
              shippingAddress: customerData.shippingAddress // Store address
            }));

            // Redirect to invoice page after a delay
            setTimeout(() => {
              this.router.navigate(['/invoice']);
            }, 2000);
          },
          error: (error) => {
            console.error('Error processing payment:', error);
          }
        });
    },
    error: (error) => {
      console.error('Error fetching customer details:', error);
    }
  });
}


}