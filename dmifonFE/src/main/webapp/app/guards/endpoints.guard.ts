import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, ActivatedRoute } from '@angular/router';
import { AccountService } from '../core/auth/account.service';
import { LoginService } from '../login/login.service';
import { RolesCheckService } from '../core/roles-check/rolesCheck.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EndpointsGuard {
  static forEndpoint(endpoint: string): any {
    @Injectable({
      providedIn: 'root',
    })
    class epCheck implements CanActivate {
      constructor(private router: Router) {}
      canActivate(): Observable<boolean> | Promise<boolean> | boolean {
        const listEp: Array<string> = JSON.parse(<string>localStorage.getItem('listEp'));
        if (listEp.includes(endpoint)) {
          return true;
        } else {
          this.router.navigate(['/accessdenied']);
          return false;
        }
      }
    }

    return epCheck;
  }
}
