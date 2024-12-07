import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class CustomerService {
  
  private addCustomerApi = 'http://localhost:8082/customer/register';
  private getAllCustomersApi = 'http://localhost:8082/customer/all';
  private addShippingAddressApi = 'http://localhost:8082/customer/address/post';
  private getCustomerDetailsByUsernameApi='http://localhost:8082/customer/details';
  private customerUpdateApi = 'http://localhost:8081/api/customer/update';
  private shippingAddressUpdateApi = 'http://localhost:8081/customer/update/address';



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


    return this.httpClient.post(this.addCustomerApi, postObj, httpOptions);
  }

  getCustomerDetails(customerId: number): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + localStorage.getItem('token'),
      }),
    };
    return this.httpClient.get(`${this.getAllCustomersApi}/${customerId}`, httpOptions);
  }

  addShippingAddress(customerId: number, shippingAddress: any): Observable<any> {
    const postObj = {
      addressLine1: shippingAddress.addressLine1,
      addressLine2: shippingAddress.addressLine2,
      city: shippingAddress.city,
      state: shippingAddress.state,
      country: shippingAddress.country,
      zipCode: shippingAddress.zipCode
    };

    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      })
    };

    return this.httpClient.post(`${this.addShippingAddressApi}/${customerId}`, postObj, httpOptions);
  }

  public getCustomerDetailsByUsername(username: any) : Observable<any>{
    return this.httpClient.get(this.getCustomerDetailsByUsernameApi + '?username=' + username)
}

public customerUpdate(customerId:any): Observable<any>{

  const httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Bearer ' + localStorage.getItem('token'),
    }),
  };
  return this.httpClient.get(this.customerUpdateApi +"/" + customerId, httpOptions);
}
public shippingAddressUpdate(customerId:any): Observable<any>{

  const httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Bearer ' + localStorage.getItem('token'),
    }),
  };
  return this.httpClient.get(this.shippingAddressUpdateApi +"/" + customerId, httpOptions);
}

}