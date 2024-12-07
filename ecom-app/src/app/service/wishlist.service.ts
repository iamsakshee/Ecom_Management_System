import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})

export class WishlistService {
  private getWishlistApi = 'http://localhost:8082/api/wishlist'; 
  private addProductToWishlistApi = 'http://localhost:8082/api/wishlist';

  constructor(private httpClient: HttpClient) {}

  getWishlistProducts(customerId: number): Observable<any> {
    return this.httpClient.get(this.getWishlistApi +"/" + customerId);
  }

  addProductToWishlist(customerId: number, productId: number): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + localStorage.getItem('token')
      })
    };
    return this.httpClient.post(this.addProductToWishlistApi + "/" + customerId + "/" + productId, httpOptions)
  }

}
