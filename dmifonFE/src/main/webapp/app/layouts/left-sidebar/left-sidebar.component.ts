import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { Observable } from 'rxjs';
import { SpinnerService } from '../../shared/spinner/spinner.service';
import { ActivatedRoute, Router } from '@angular/router';

interface ILeftMenu {
  label: string;
  profilePermission: string[];
  icon?: string;
  items?: ILeftMenu[];
}

@Component({
  selector: 'jhi-left-sidebar',
  templateUrl: './left-sidebar.component.html',
  styleUrls: ['./left-sidebar.component.scss'],
})
export class LeftSidebarComponent implements OnInit {
  @ViewChild('leftMenu', { static: false }) menu?: ElementRef<HTMLElement>;
  @Output() collapseStautsToEmit: EventEmitter<boolean> = new EventEmitter<boolean>();
  isCollapsedMenu = true;
  clickCollapseMenu = false;
  isOpened: boolean;
  _jsonUrl = '';
  menus?: ILeftMenu[];

  constructor(
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private http: HttpClient,
    protected spinnerService: SpinnerService,
    protected router: Router
  ) {
    this.isOpened = false;
  }

  ngOnInit(): void {
    this._jsonUrl = '/content/json/menu.json';
    console.log(this.activatedRoute.snapshot);
    this.getJson().subscribe(menu => {
      this.menus = menu;
    });
  }

  collapseOnClickBarsMenu(): void {
    document.querySelector('html')?.setAttribute('style', 'overflow: hidden !important;');
    this.isCollapsedMenu = !this.isCollapsedMenu;
    this.isOpened = true;
    this.clickCollapseMenu = !this.clickCollapseMenu;
    this.collapseStautsToEmit.emit(this.isCollapsedMenu);
  }

  collapseOnClickCloseButtonMenu(): void {
    document.querySelector('html')?.setAttribute('style', 'overflow: auto !important;');
    this.isCollapsedMenu = true;
    this.clickCollapseMenu = false;
    this.isOpened = false;
    this.collapseStautsToEmit.emit(this.isCollapsedMenu);
  }

  collapseOnClickLink(urlMenu: string | null): void {
    if (urlMenu != null) {
      document.querySelector('html')?.setAttribute('style', 'overflow: auto !important;');
      this.isCollapsedMenu = true;
      this.clickCollapseMenu = false;
      this.collapseStautsToEmit.emit(this.isCollapsedMenu);
      this.router.navigateByUrl(urlMenu);
    }
  }

  /*   collapseOverMenuBtn() : void {
  }

  collapseOutMenuBtn() : void {
  }*/

  preventAction(event: Event): void {
    event.preventDefault();
    event.stopPropagation();
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  hasRoles(): boolean {
    return this.accountService.getHasRoles();
  }

  private getJson(): Observable<any> {
    return this.http.get(this._jsonUrl);
  }
}
