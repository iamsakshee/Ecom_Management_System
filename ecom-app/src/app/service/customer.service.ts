import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class CustomerService {

  private createCustomerApi = 'http://localhost:8082/customer/register';

  constructor(private httpClient: HttpClient) { }



  addCustomerDetails(obj: any): Observable <any> {

    let postObj = {
      name: obj.name,
      email: obj.email,
      phoneNumber: obj.phoneNumber
    };

    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + localStorage.getItem('token')
      })
    };


    return this.httpClient.post(this.createCustomerApi, postObj, httpOptions);
  }




}