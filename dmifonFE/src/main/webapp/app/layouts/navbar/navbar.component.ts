import { Component, OnInit, Compiler, Injector, Type } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { version } from 'app/app.constants';
import { UsersService } from '../../services/users.service';
import { IammUteruosById, IUsers } from '../../models/users.model';
import { EnteService } from '../../services/ente.service';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { ProfilesService } from '../../services/profiles.service';
import { JsogService } from 'jsog-typescript';
import { ParametroService } from '../../services/parametro.service';
import { take } from 'rxjs';
@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  roles!: Array<any>;
  selectedRole!: IammUteruosById;
  errorMessage!: string;
  lingua!: string;
  openAPIEnabled?: boolean;
  version = version;
  account: Account | null = null;
  iniziali = '';
  idUte!: number;
  ente: any;
  LogoNavbar: string;
  entitiesNavbarItems: any[] = [];
  loading = false;

  constructor(
    private loginService: LoginService,
    private userService: UsersService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private parametroService: ParametroService,
    private compiler: Compiler,
    private injector: Injector,
    private accountService: AccountService,
    private profileService: ProfileService,
    private roleService: ProfilesService,
    private rolesCheckService: RolesCheckService,
    private enteService: EnteService,
    private router: Router
  ) {
    this.LogoNavbar = 'N';
  }

  ngOnInit(): void {
    const jsog = new JsogService();
    this.entitiesNavbarItems = EntityNavbarItems;
    this.enteService.getEnte().subscribe(res => {
      this.ente = res;
    });
    this.parametroService
      .getParametroByCodice('LogoNavbar')
      .pipe(take(1))
      .subscribe(res => {
        this.LogoNavbar = res;
      });
    this.lingua = !this.sessionStorageService.retrieve('locale') ? 'it' : this.sessionStorageService.retrieve('locale');
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });
    this.loading = true;
    this.accountService.getAuthenticationState().subscribe(
      account => {
        this.account = account;
        this.userService.ricercaUtenteRuolo(account!.login).subscribe(
          (res: any) => {
            this.loading = false;
            localStorage.setItem('curUte', JSON.stringify({ id: res.id, username: res.username, cognome: res.cognome, nome: res.nome }));
            this.idUte = res.id;
            localStorage.setItem('idUte', this.idUte.toString());
            this.iniziali = res.nome.substring(0, 1) + res.cognome.substring(0, 1);
            if (res.ammUteruosById.length !== 0) {
              this.roles = (<IUsers>jsog.deserialize(res)).ammUteruosById;
              if (localStorage.getItem('ammUteRuo')) {
                this.changeRole(<IammUteruosById>jsog.deserialize(JSON.parse(localStorage.getItem('ammUteRuo'))));
              } else {
                let pickRole = false;
                this.roles.forEach(role => {
                  if (role.flgdef === 'S' && !pickRole) {
                    this.changeRole(role);
                    pickRole = true;
                  }
                });
                if (!pickRole) {
                  this.changeRole(this.roles[0]);
                }
              }
              this.accountService.setHasRoles(true);
            } else {
              this.accountService.setHasRoles(false);
            }
          },
          err => {
            this.accountService.setErrorMessage(err.error.userMessage);
          }
        );
      },
      error => {
        this.loading = false;
      }
    );
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.lingua = this.sessionStorageService.retrieve('locale');
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    localStorage.clear();
    this.loginService.login();
  }

  logout(): void {
    this.collapseNavbar();
    localStorage.clear();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  goToProfile(): void {
    this.router.navigate(['amm-utenti/' + this.idUte + '/viewDetail']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  changeRole(role: IammUteruosById): void {
    const jsog = new JsogService();
    localStorage.setItem('ammUteRuo', JSON.stringify(jsog.serialize(role)));
    this.selectedRole = role;
    this.accountService.setCurIdRuo(role.ammRuoByIdRuo ? role.ammRuoByIdRuo.id : 0);
    localStorage.setItem('curRole', role.id.toString());
    this.accountService.setCurIdUteRuo(role.id);
    this.sessionStorageService.store('tipcondat', role.tipcondat.toString());
    localStorage.setItem('curRuo', role.ammRuoByIdRuo ? role.ammRuoByIdRuo.id.toString() : '');
    this.loading = true;
    this.roleService.getPermessiRuolo().subscribe(
      res => {
        this.loading = false;
        this.rolesCheckService.setEndpoints(res);
      },
      error => {
        this.loading = false;
      }
    );
  }

  refreshPagina(): void {
    window.location.reload();
  }

  private loadModule(moduleType: Type<any>): void {
    const moduleFactory = this.compiler.compileModuleAndAllComponentsSync(moduleType);
    moduleFactory.ngModuleFactory.create(this.injector);
  }
}
