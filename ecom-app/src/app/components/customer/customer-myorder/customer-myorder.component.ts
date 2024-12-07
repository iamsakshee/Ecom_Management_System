import { Component, OnInit } from '@angular/core';
import { CustomerNavbarComponent } from "../customer-navbar/customer-navbar.component";
import { NgFor, NgIf } from '@angular/common';
import { OrderService } from '../../../service/order.service';
import { ActivatedRoute } from '@angular/router';
import { CustomerService } from '../../../service/customer.service';

@Component({
  selector: 'app-customer-myorder',
  imports: [CustomerNavbarComponent, NgFor, NgIf],
  templateUrl: './customer-myorder.component.html',
  styleUrl: './customer-myorder.component.css'
})


export class CustomerMyorderComponent implements OnInit {
  customerId: any;
  orders: any;  // Holds the orders data

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService  // Inject the service
  ) {}

  ngOnInit(): void {
    // Get customerId from route params or local storage
    this.route.paramMap.subscribe(params => {
      this.customerId = +params.get('customerId')!;  // Get the customerId from route params
      this.fetchOrders();  // Fetch the orders based on customerId
    });
  }

  // Method to fetch the customer orders from the API
  fetchOrders(): void {
    this.orderService.getOrdersByCustomerId(this.customerId).subscribe({
      next: (data) => {
        this.orders = data;  // Set the received data to orders array
      },
      error: (error) => {
        console.error('Error fetching orders:', error);  // Handle errors
      }}
    );
  }
} 