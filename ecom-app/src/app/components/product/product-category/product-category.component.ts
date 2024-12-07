import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../service/product.service';
import { NgFor, NgIf } from '@angular/common';
import { CustomerNavbarComponent } from '../../customer/customer-navbar/customer-navbar.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-product-category',
  imports: [NgIf, NgFor, CustomerNavbarComponent, FormsModule],
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
 
  username: string | null = localStorage.getItem('username');
  wishlist: any[] = [];
  msg: any;
  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private router: Router
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
  
}