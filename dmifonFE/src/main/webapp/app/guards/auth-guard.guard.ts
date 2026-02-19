import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, ActivatedRoute } from '@angular/router';
import { AccountService } from '../core/auth/account.service';
import { LoginService } from '../login/login.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuardService implements CanActivate {
  routeURL: string;
  constructor(
    private accountService: AccountService,
    private router: Router,
    private loginService: LoginService,
    private activatedRoute: ActivatedRoute
  ) {
    this.routeURL = this.router.url;
  }

  // the Router call canActivate() method,
  // if canActivate is registered in Routes[]
  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    // here we check if user is logged in or not
    // the authService returs user object, or
    // it returns undefined/null when user is not logged in

    // SINCE OUR 'authService.user' IS OF TYPE 'Observable'
    // WE MUST USE 'subscribe' TO GET VALUE OF 'user'
    return new Promise((resolve, reject) => {
      this.accountService.identity().subscribe(
        user => {
          // here we check if user is logged in or not
          // the authService returs user object, or
          // it returns undefined/null when user is not logged in
          console.log(this.routeURL);
          if (!user) {
            // just return false - if user is not logged in
            this.loginService.login();
            return resolve(false);
          } else {
            console.log(user);
            // just return true - if user is logged in
            return resolve(true);
          }
        },
        error => {
          console.log(error);
        }
      );
    });
  }
}
