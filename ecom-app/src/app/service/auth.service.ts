import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
        providedIn: 'root'
    })
    export class AuthService {
      private loginApiUrl = 'http://localhost:8082/api/token';
      private signUpApiUrl = 'http://localhost:8082/auth/sign-up';
      private userDetailsApiUrl = 'http://localhost:8082/auth/user';
      private createCustomerApi='http://localhost:8082/customer/register';

    
      constructor(private httpClient: HttpClient) {}
    
      signUp(user: any): Observable<any> {
        return this.httpClient.post(this.signUpApiUrl, user);
      }
    
      login(user: any): Observable<any> {
        return this.httpClient.post(this.loginApiUrl, user);
      }
      
      getUserDetails(token: any): Observable<any> {
        const httpOptions = {
          headers: new HttpHeaders({
            Authorization: 'Bearer ' + token,
          }),
        };
        return this.httpClient.get(this.userDetailsApiUrl, httpOptions);
      }


      addCustomerDetails(obj: any) :Observable<any>{

        const httpOptions = {
          headers: new HttpHeaders({
             Authorization: 'Bearer '+ localStorage.getItem('token')
          })
        };

        let postObj = {
          name: obj.name,
          email: obj.email,
          phoneNumber: obj.phoneNumber,
          user:{
                 username: obj.username,
                 password: obj.password
          }
        };

        return this.httpClient.post(this.createCustomerApi, postObj, httpOptions);
        }
    }

    

        
  
   