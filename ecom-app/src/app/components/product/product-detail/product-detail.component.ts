import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../service/product.service';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { OrderService } from '../../../service/order.service';
import { CustomerNavbarComponent } from '../../customer/customer-navbar/customer-navbar.component';
import { CustomerService } from '../../../service/customer.service';
import { FormsModule } from '@angular/forms';
import { CartService } from '../../../service/cart.service';
import { ReviewService } from '../../../service/review.service';

@Component({
  selector: 'app-product-detail',
  imports: [NgIf, CustomerNavbarComponent, FormsModule],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit {
  successMessage: any;
  errorMessage: any;
  product: any;
  productId: any;
  customerId: any;
  subTotal: any;
  gst: any;
  username: string | null = localStorage.getItem('username');
  wishlist: any[] = [];
  msg: any;
  path: any = './images/';
  reviewText: string = '';
  rating: number = 0;
  showModal: boolean = false;


  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private router: Router,
    private customerService: CustomerService,
    private reviewService: ReviewService
  ) {


  }


  ngOnInit(): void {
    this.route.paramMap.subscribe(p => {
      this.productId = p.get('id');
      this.productService.getProductById(this.productId).subscribe({
        next: (data) => {
          this.product = data[0];
          this.product.images.forEach((i: any) => {
            i.path = './images/' + i.fileName;
          });

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

  getProductDetails() {
    this.route.paramMap.subscribe(p => {
      this.productId = p.get('id');
      this.productService.getProductById(this.productId).subscribe({
        next: (data) => {
          this.product = data;
          this.product.images.forEach((i: any) => {
            i.path = './images/' + i.fileName
          });
        },
        error: () => { }
      })
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
      subtotal: this.product.price,
      gst: 0.18,
      total: this.product.price * quantity * this.gst,
      image: this.product.image || "https://via.placeholder.com/100x100"
    };
    cart.push(productDetails);
    localStorage.setItem('cart', JSON.stringify(cart));

    this.router.navigateByUrl("/cart");
  }

  openReviewModal(): void {
    this.showModal = true;  // Show the modal when the button is clicked
    this.successMessage = '';  // Clear any success message when opening the modal
    this.errorMessage = '';    // Clear any error message when opening the modal
  }

  closeReviewModal(): void {
    this.showModal = false;  // Hide the modal when the close button is clicked
    this.successMessage = '';  // Clear success message
    this.errorMessage = '';    // Clear error message
  }

  submitReview(): void {
    if (!this.rating || !this.reviewText) {
      this.errorMessage = 'Please provide both rating and review text.';
      return;
    }

    const reviewData = {
      rating: this.rating,
      review_text: this.reviewText
    };

    this.reviewService.addReview(this.customerId, this.productId, reviewData).subscribe({
      next: (response) => {
        this.successMessage = 'Review added successfully!';
        setTimeout(() => {
          this.successMessage = '';  // Clear success message after 5 seconds
          this.closeReviewModal();  // Close the modal after success
        }, 5000);
      },
      error: (err) => {
        this.successMessage = 'Review added successfully';
        setTimeout(() => {
          this.errorMessage = '';  // Clear error message after 5 seconds
        }, 5000);
      }
    });
  }

}


