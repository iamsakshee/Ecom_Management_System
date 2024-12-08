import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CustomerService } from '../../service/customer.service';
import { CustomerNavbarComponent } from "../../components/customer/customer-navbar/customer-navbar.component";
import { NgFor, NgIf } from '@angular/common';
import { ProductService } from '../../service/product.service';
import { CartService } from '../../service/cart.service';
import { WishlistService } from '../../service/wishlist.service';
@Component({
  selector: 'app-dashboard-page',
  imports: [CustomerNavbarComponent, NgFor, RouterLink, NgIf],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.css'
})

export class DashboardPageComponent implements OnInit {

  products: any[] = []; 
  style: any;
  customerId: any;
  successMessage: string = '';
  errorMessage: string = '';
  data:any[] = []
  page:number=0;
  size:number=8;
  totalElements: number = 0;
  pageArray:any[] =[];
  searchKey: string = '';

  
  constructor(private productService: ProductService, 
              private wishlistService: WishlistService,  
              private customerService: CustomerService,
              private router: Router) {}

  ngOnInit(): void {
   
    this.getAllProducts();
    
  }

  // Fetch all products
  getAllProducts(searchKey:String = ""): void {
    this.productService.getAllProducts(this.page,this.size, searchKey).subscribe({
      next: (resp) => {
        this.data = resp.content; // Assuming 'content' contains the products
      this.products = resp.content; 
        this.totalElements = resp.totalElements;
        let totalPages = this.totalElements / this.size;
        let i=1; 
        this.pageArray = [];
        while(totalPages > 0){
          this.pageArray.push(i)
          totalPages = totalPages - 1;
          i++;
        }
        console.log("page" + this.pageArray)
        this.products.forEach(p=>{
          p.images.forEach((i:any) => {
            i.path = './images/'+ i.fileName
          }); })
          },
      error: (error) => {
        console.error('There was an error fetching the products!', error);
      }
    });
  }


  addToWishlist(product: any): void {
    const username = localStorage.getItem('username'); // Retrieve username from local storage
  
    if (username) {
      this.customerService.getCustomerDetailsByUsername(username).subscribe({
        next: (data) => {
          this.customerId = data.customerId;
          const productId = product.id;
  
          this.wishlistService.addProductToWishlist(this.customerId, productId).subscribe({
            next: () => {
              this.successMessage = 'Product added to wishlist successfully!';
              this.errorMessage = 'Product added to wishlist successfully!'; // Clear error message
              setTimeout(() => (this.successMessage = ''), 5000); // Clear message after 5 seconds
            },
            error: (error) => {
              if (error.status === 400) {
                this.errorMessage = 'Product already exists in wishlist.';
              }
              this.successMessage = ''; // Clear success message
              setTimeout(() => (this.errorMessage = ''), 5000); // Clear message after 5 seconds
            },
          });
        }
      })
    }
  }

  prev(){
    if(this.page >0){
     this.page = this.page - 1 
     this.getAllProducts() 
   }
       
  }
  next(){
   this.page = this.page + 1 
   this.getAllProducts()
  }
  onClick(i:number){
     this.page = i
     this.getAllProducts()
  }

  onSearch(searchTerm: string): void {
    this.searchKey = searchTerm; // Update the searchKey
    this.getAllProducts(this.searchKey); // Fetch filtered products
  }
}

      
  

