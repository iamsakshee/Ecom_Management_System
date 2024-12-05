import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn:'root'
})
export class OrderService{

    private purchaseProductApi = 'http://localhost:8082/customer/product/purchase';

    constructor(private httpClient: HttpClient) { }

    purchaseProduct(customerId: number, productId: number, quantity: number): Observable<any> {

        const httpOptions = {
            headers: new HttpHeaders({
               Authorization: 'Bearer '+ localStorage.getItem('token')
            })
          };

        const url = `${this.purchaseProductApi}/${customerId}/${productId}?quantity=${quantity}`;
        return this.httpClient.post(url, {}, httpOptions);
      }

}