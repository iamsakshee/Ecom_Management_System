import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";


@Injectable({
    providedIn: 'root'
  })
export class ReviewService{

    private addReviewApi ='http://localhost:8082/customer/add/review'

    constructor(private httpClient: HttpClient){}

    addReview(customerId: number, productId: number, obj:any): Observable<any>{
        let postObj = {
           rating: obj.rating,
           review_text: obj.review_text
          };

          const httpOptions = {
            headers: new HttpHeaders({
              Authorization: 'Bearer ' + localStorage.getItem('token')
            })
          };

        return this.httpClient.post(this.addReviewApi + "/" + customerId + "/" + productId, postObj, httpOptions)
    }

}  