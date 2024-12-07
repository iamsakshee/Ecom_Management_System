import { Routes } from '@angular/router';


import { CustomerPageComponent } from './pages/customer-page/customer-page.component';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { AddDetailsComponent } from './components/customer/add-details/add-details.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { CustomerCartComponent } from './components/customer/customer-cart/customer-cart.component';
import { ProductDetailComponent } from './components/product/product-detail/product-detail.component';
import { ProductCategoryComponent } from './components/product/product-category/product-category.component';
import { ProductWishlistComponent } from './components/product/product-wishlist/product-wishlist.component';
import { ProductCheckoutComponent } from './components/product/product-checkout/product-checkout.component';
import { CustomerMyorderComponent } from './components/customer/customer-myorder/customer-myorder.component';
export const routes: Routes = [

        {
            path: '' , component: LoginComponent
        },
        {
            path:'sign-up', component : SignupComponent
        },
        {
            path: 'add-details', component:AddDetailsComponent
        },

        {
            path:'dashboard', component: DashboardPageComponent
        },
        {
            path: 'customer', component: CustomerPageComponent
        },
        {
            path:'cart', component:CustomerCartComponent
        },
        {
            path: 'product/:id', component: ProductDetailComponent 
        },
        {
            path: 'category/:categoryId', component: ProductCategoryComponent
        },
        {
            path: 'wishlist', component: ProductWishlistComponent
        },
        {
            path: 'checkout', component: ProductCheckoutComponent
        },
        {
            path: 'order/:customerId', component: CustomerMyorderComponent
        }
       
];