import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CustomerService } from '../../../service/customer.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-add-details',
  imports: [FormsModule, NgIf, ReactiveFormsModule],
  templateUrl: './add-details.component.html',
  styleUrl: './add-details.component.css'
})
export class AddDetailsComponent implements OnInit{
  
  username : string| undefined;
  password: string | undefined;
  successMsg: string | undefined;
  errorMsg: string | undefined;
  customerForm: FormGroup;

  constructor(private router: Router, private customerService: CustomerService) {

    this.customerForm= new FormGroup({
      name: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required]),
      phoneNumber:new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(10)])   
    });
  }
  ngOnInit(): void { 
  }

  onSignUp() {
    
    console.log(this.customerForm.value)
    this.customerService.addCustomerDetails(this.customerForm.value).subscribe({
      next: (data)=>{
        this.successMsg="customer registered"
        this.errorMsg=undefined;
      },

      error:(err)=>{
        this.errorMsg = err.error.msg;
        
      }
    })

  }
}



