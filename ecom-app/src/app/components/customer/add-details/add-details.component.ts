import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CustomerService } from '../../../service/customer.service';
import { NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-add-details',
  imports: [FormsModule, NgIf, ReactiveFormsModule],
  templateUrl: './add-details.component.html',
  styleUrl: './add-details.component.css'
})
export class AddDetailsComponent implements OnInit {
  
  successMsg: string | undefined;
  errorMsg: string | undefined;
  customerForm: FormGroup;
  customerId: number | undefined; 
  customer: any;

  constructor(private router: Router, private customerService: CustomerService) {
    this.customerForm = new FormGroup({
      // Customer Details
      name: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      phoneNumber: new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(10)]),

      // Shipping Address Details
      addressLine1: new FormControl('', [Validators.required]),
      addressLine2: new FormControl(''),
      city: new FormControl('', [Validators.required]),
      state: new FormControl('', [Validators.required]),
      country: new FormControl('', [Validators.required]),
      zipCode: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(6)])
    });
  }

  ngOnInit(): void {
   
    const username = localStorage.getItem('username');
    
    if (username) {
      this.customerService.getCustomerDetailsByUsername(username).subscribe({
        next: (data) => {
          this.customer = data; 
          console.log(this.customer);
          

          if (this.customer && this.customer.shippingAddress) {
            this.customerForm.patchValue({
              name: this.customer.customerName,
              email: this.customer.customerEmail,
              phoneNumber: this.customer.phoneNumber,
              addressLine1: this.customer.shippingAddress.addressLine1,
              addressLine2: this.customer.shippingAddress.addressLine2,
              city: this.customer.shippingAddress.city,
              state: this.customer.shippingAddress.state,
              country: this.customer.shippingAddress.country,
              zipCode: this.customer.shippingAddress.zipCode
            });
          }
        },
        error: (err) => {
          console.error('Error fetching customer details:', err);
        }
      });
    }
  }
  
  onSignUp() {
    const customerDetails = {
      name: this.customerForm.value.name,
      email: this.customerForm.value.email,
      phoneNumber: this.customerForm.value.phoneNumber
    };

   
    this.customerService.addCustomerDetails(customerDetails).subscribe({
      next: (data: any) => {
        this.successMsg = "Customer registered";
        this.customerId = data.id; 
        this.addShippingAddress(); 
      },
      error: (err) => {
        this.errorMsg = err.error.msg;
      }
    });
  }

  addShippingAddress() {
    if (!this.customerId) {
      this.errorMsg = "Customer ID not found.";
      return;
    }

    const shippingAddress = {
      addressLine1: this.customerForm.value.addressLine1,
      addressLine2: this.customerForm.value.addressLine2,
      city: this.customerForm.value.city,
      state: this.customerForm.value.state,
      country: this.customerForm.value.country,
      zipCode: this.customerForm.value.zipCode
    };

    this.customerService.addShippingAddress(this.customerId, shippingAddress).subscribe({
      next: () => {
        this.successMsg = "Customer and shipping address registered successfully.";
      },
      error: (err) => {
        this.errorMsg = err.error.msg;
      }
    });
  }
}