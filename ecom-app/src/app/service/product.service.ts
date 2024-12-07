import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ProductService{

    private getAllProductsApi = "http://localhost:8082/customer/product/get";
    private getAllProductApii='http://localhost:8082/api/product/all';
    private getProductByIdApi= "http://localhost:8082/product/getProduct";
    private getProductByCategoryApi = 'http://localhost:8082/product/category';
    private searchProdcutApi = 'http://localhost:8082/api/products/search';
    

    constructor(private httpClient: HttpClient){}


    public getAllProducts(): Observable<any> {
        
        return this.httpClient.get(this.getAllProductApii);
      }

    public getProductById(id: number): Observable<any> {
        return this.httpClient.get(`${this.getProductByIdApi}/${id}`);
    }

    public getProductsByCategory(categoryId: number): Observable<any> {

        return this.httpClient.get(this.getProductByCategoryApi + "/" + categoryId)
    }

    searchProducts(query: string): Observable<any> {
        
        return this.httpClient.get(this.searchProdcutApi + "?query=" + query )
      }
      

}
