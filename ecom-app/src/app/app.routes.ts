import { Routes } from '@angular/router';


import { CustomerPageComponent } from './pages/customer-page/customer-page.component';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { AddDetailsComponent } from './components/customer/add-details/add-details.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { CustomerCartComponent } from './components/customer/customer-cart/customer-cart.component';
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
        }
       
];