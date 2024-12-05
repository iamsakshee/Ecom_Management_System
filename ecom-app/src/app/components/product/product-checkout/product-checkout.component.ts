import { NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from '../../../service/order.service';

@Component({
  selector: 'app-product-checkout',
  imports: [NgIf],
  templateUrl: './product-checkout.component.html',
  styleUrl: './product-checkout.component.css'
})
export class CheckoutComponent implements OnInit {
  totalAmount: number = 0;
  paymentMethod: string = '';
  isPaymentConfirmed: boolean = false;
  customerId: any;
  productId: any;
  quantity: number = 1;

  constructor(
    private route: ActivatedRoute, 
    private router: Router,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    // Subscribe to query parameters and extract the data
    this.route.queryParams.subscribe(params => {
      this.totalAmount = params['total']; // Get total amount from queryParams
      this.customerId = params['customerId']; // Get customerId from queryParams
      this.productId = params['productId']; // Get productId from queryParams

      // Ensure that the parameters exist
      console.log('Total Amount:', this.totalAmount);
      console.log('Customer ID:', this.customerId);
      console.log('Product ID:', this.productId);
    });
  }

  // Method to handle payment method selection
  selectPaymentMethod(method: string): void {
    this.paymentMethod = method;
  }

  // Method to confirm the order after payment method selection
  confirmOrder(): void {
    if (this.paymentMethod) {
      // Simulate order confirmation and payment processing
      this.isPaymentConfirmed = true; // Payment is confirmed
      alert(`Payment method selected: ${this.paymentMethod}. Your order is confirmed!`);

      // Trigger the purchase API after confirmation
      this.purchaseProduct(); 

    } else {
      alert('Please select a payment method');
    }
  }

  // Method to simulate order processing and triggering the API
  purchaseProduct(): void {
    const quantity = this.quantity; // You can get the quantity dynamically if needed
    console.log('Customer ID:', this.customerId);  
    console.log('Product ID:', this.productId);    

    if (!this.customerId || !this.productId) {
      alert('Invalid customer or product ID');
      return; 
    }

    // Call the purchase service to process the order
    this.orderService.purchaseProduct(this.customerId, this.productId, quantity)
      .subscribe({
        next: (response: any) => {
          alert(response.msg);  
          this.router.navigate(['/cart']);  
        },
        error: (error) => {
          alert('Error: ' + error.error.msg);
        }
      });
  }

  // Method to simulate payment and order confirmation
  completePayment(): void {
    if (this.isPaymentConfirmed) {
      alert(`Payment confirmed using ${this.paymentMethod}. Thank you for your order!`);
      // Navigate to a final confirmation or home page
      this.router.navigate(['/']);
    } else {
      alert('Please confirm your payment method first.');
    }
  }
}