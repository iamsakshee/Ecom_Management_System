import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { AuthPageComponent } from './pages/auth-page/auth-page.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';

export const routes: Routes = [
            {
                path:'login',component: LoginComponent
            },
            {
                path:'sign-up',component: SignupComponent
            },
    
    //we have to give the path of different model delivery,manager,shipment,warehouse
    {
        path: '*', component : PageNotFoundComponent
    }
];
