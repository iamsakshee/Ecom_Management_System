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
export class ProductCategoryComponent  implements OnInit {
  categoryId: number = 1; // Example Category ID
  products: any[] = []; // List of products

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.fetchProducts();
  }

  fetchProducts(): void {
    this.productService.getProductsByCategory(this.categoryId).subscribe({
      next: (data) => {
       
        this.products = data.map((product: any) => ({ ...product, quantity: 1 }));
      },
      error: (err) => {
        console.error('Error fetching products:', err);
      },
    });
  }

  purchaseProduct(product: any): void {
    console.log(`Purchased ${product.quantity} of ${product.name}`);
  }
}