import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Router, ActivatedRouteSnapshot, CanActivate } from '@angular/router';
import { TokenStorageService } from './token-storage.service';
import decode from 'jwt-decode';

const TOKEN_KEY = 'AuthToken';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable()
export class RoleGuardService implements CanActivate {

    constructor(public auth: AuthService, public router: Router, private tokenStorage: TokenStorageService) {}

    canActivate(route: ActivatedRouteSnapshot): boolean {

        const expectedRole = route.data.expectedRole;
        const token = this.tokenStorage.getToken();

        if(!token) {
            window.alert("Please Log in!");
            this.router.navigate(['login']);
        }
      
        const tokenPayload = decode(token);
        
/*
        if(sessionStorage.getItem(TOKEN_KEY)) {
            JSON.parse(sessionStorage.getItem(AUTHORITIES_KEY)).forEach(authority => {
                if(authority.authority == 'ROLE_ADMIN') {
                    return true;
                }
            });
        }
*/
        return true; // treba FALSE
        
    }

}