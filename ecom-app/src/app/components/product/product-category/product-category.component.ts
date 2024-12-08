import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ProductService } from '../../../service/product.service';
import { NgFor, NgIf } from '@angular/common';
import { CustomerNavbarComponent } from '../../customer/customer-navbar/customer-navbar.component';
import { FormsModule } from '@angular/forms';
import { ReviewService } from '../../../service/review.service';

@Component({
  selector: 'app-product-category',
  imports: [NgIf, NgFor, CustomerNavbarComponent, FormsModule, RouterLink],
  templateUrl: './product-category.component.html',
  styleUrl: './product-category.component.css'
})
export class ProductCategoryComponent implements OnInit {
  categoryId!: number; // Category ID from the URL
  products: any[] = []; // Array to store products
  product: any;
  gst: any;
  quantity:any;  
  productId: any;
  customerId: any;
  subTotal: any;
  reviewText: string = '';
  rating: number = 0;
  showModal: boolean = false;
  successMessage: any;
  errorMessage: any;
 
  username: string | null = localStorage.getItem('username');
  wishlist: any[] = [];
  msg: any;
  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private router: Router,
    private reviewService: ReviewService
  ) {}

  ngOnInit(): void {
    // Get the category ID from the route parameter
    this.route.params.subscribe((params) => {
      this.categoryId = params['categoryId']; 
      this.fetchProducts();
    });
  }

  fetchProducts(): void {
    // Fetch products by category from ProductService
    this.productService.getProductsByCategory(this.categoryId).subscribe({
      next: (data) => {
        this.products = data; // Update product list
        
        // Loop through each product and update image paths
        this.products.forEach((product: any) => {
          if (product.images && product.images.length > 0) {
            product.images.forEach((image: any) => {
              image.path = './images/' + image.fileName; // Update image path
            });
          }
        });
      },
      error: (error) => {
        console.error('Error fetching products', error);
      }
    });
  }
  

  addToCart(product: any): void {
    // Store product details in local storage
    let cart = JSON.parse(localStorage.getItem('cart') || '[]');
    const quantity = product.quantity || 1;
    const subtotal = Math.round(product.price * quantity); // Round to the nearest integer
    const gst = Math.round(subtotal * 0.18);                // Round to the nearest integer
    const total = Math.round(subtotal + gst); 
    const productDetails = {
      productId: product.id,
      name: product.name,
      price: product.price,
      quantity: quantity,
      subtotal: subtotal, // Now an integer
      gst: gst,           // Now an integer
      total: total,  
      image: product.image || "https://via.placeholder.com/100x100"
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