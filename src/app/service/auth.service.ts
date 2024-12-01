import { HttpBackend, HttpClient, HttpHeaders } from '@angular/common/http';
import { Token } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private signupApi='http://localhost:8081/auh/sign-up';
  private loginApi='http://localhost:8081/api/token';
  private userDetailsApi='http://localhost:8081/auth/user';

  constructor(private httpClient: HttpClient) { }
  
  getUserDetails(token: any): Observable<any>{
     
    const httpOptions = {
        headers: new HttpHeaders({
           Authorization: 'Bearer '+ token
        })
      };
    return this.httpClient.get(this.userDetailsApi,httpOptions); 
  }

  login(user: any): Observable<any> {
    return this.httpClient.post(this.loginApi, user);
  }

  signUp(user: any): Observable<any> {
    return this.httpClient.post(this.signupApi, user);
  }

}
