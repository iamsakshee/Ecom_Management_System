import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService
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

  purchaseProduct(product: any): void {
    alert(`You have purchased: ${product.name}`);
  }
}