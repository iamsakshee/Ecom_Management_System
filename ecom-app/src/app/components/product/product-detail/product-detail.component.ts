import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../service/product.service';
import { NgFor, NgIf } from '@angular/common';
import { OrderService } from '../../../service/order.service';
import { CustomerNavbarComponent } from '../../customer/customer-navbar/customer-navbar.component';
import { CustomerService } from '../../../service/customer.service';
import { FormsModule } from '@angular/forms';
import { CartService } from '../../../service/cart.service';

@Component({
  selector: 'app-product-detail',
  imports: [NgIf, CustomerNavbarComponent, FormsModule],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit {
  product: any;  
  productId: any;
  customerId: any;
  username: string | null = localStorage.getItem('username');
  wishlist: any[] = [];
  msg: any;
  path:any = './images/';
  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private orderService: OrderService,
    private router: Router,
    private customerService: CustomerService
  ) { }


  ngOnInit(): void {
    this.route.paramMap.subscribe(p=>{
      this.productId = p.get('id'); 
    // this.productId = +this.route.snapshot.paramMap.get('id')!;
    this.productService.getProductById(this.productId).subscribe({
      next: (data) => {
        this.product = data[0];
        this.product.forEach((p: { images: any[]; }) => {
          console.log(p.images);
          p.images.forEach((i: any) => {
            i.path = './images/' + i.fileName;
          });
        });
        this.product.description = this.product.description; // API response is an array, take the first item
      },
      error: (error) => {
        console.error('Error fetching product details', error);
      }
    });
    if (this.username) {
      this.fetchCustomerIdByUsername(this.username);
    }
  })
}
  fetchCustomerIdByUsername(username: string): void {
    this.customerService.getCustomerDetailsByUsername(username).subscribe({
      next: (data) => {
        this.customerId = data.customerId;
        console.log('Customer ID:', this.customerId);
      },
      error: (error) => {
        console.error('Error fetching customer ID', error);
      }
    });
  }
  addToCart() {
    // Store product details in local storage
    let cart = JSON.parse(localStorage.getItem('cart') || '[]');
    const quantity = this.product.quantity || 1; // Default to 1 if quantity is not set
    const productDetails = {
      productId: this.product.id,
      name: this.product.name,
      price: this.product.price,
      quantity: quantity,
      total: this.product.price * quantity,
      image: this.product.image || "https://via.placeholder.com/100x100"
    };
    cart.push(productDetails);
    localStorage.setItem('cart', JSON.stringify(cart));
    
    this.router.navigateByUrl("/cart");
  }
  // purchaseProduct() {
  //   const quantity = this.product.quantity;
  //   console.log('Customer ID:', this.customerId);  
  //   console.log('Product ID:', this.productId);    
  //   if (!this.customerId || !this.productId) {
  //     alert('Invalid customer or product ID');
  //     return; 
  //   }
  //   this.orderService.purchaseProduct(this.customerId, this.productId, quantity)
  //     .subscribe({
  //       next: (response: any) => {
  //         alert(response.msg);  
  //         this.router.navigate(['/cart']);  
  //       },
  //       error: (error) => {
  //         alert('Error: ' + error.error.msg);
  //       }
  //     });
  // }
}