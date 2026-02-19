import { Component, OnInit } from '@angular/core';

import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Router } from '@angular/router';
import { JoyrideService } from 'ngx-joyride';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  account: Account | null = null;
  hasRoles: boolean;

  constructor(
    protected accountService: AccountService,
    private loginService: LoginService,
    protected router: Router,
    private joyrideService: JoyrideService
  ) {
    this.hasRoles = this.accountService.getHasRoles();
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      this.account = account;
      if (account == null) {
        this.login();
      }
      this.hasRoles = this.accountService.getHasRoles();
    });
  }

  login(): void {
    this.loginService.login();
  }

  reloadWindow(): void {
    window.location.reload();
  }

  onClick(): any {
    this.joyrideService.startTour(
      { steps: ['firstStep', 'secondStep', 'thirdStep', 'fourthStep', 'fifthStep', 'sixthStep'], waitingTime: 100 } // Your steps order
    );
  }

  navigateLink(s: string): void {
    this.router.navigateByUrl(s);
  }
}
