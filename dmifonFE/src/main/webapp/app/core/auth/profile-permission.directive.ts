import { Directive, TemplateRef, ViewContainerRef, Input } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';

@Directive({
  selector: '[jhiProfilePermission]'
})
export class ProfilePermissionDirective {
  private params: string[] = [];

  constructor(private accountService: AccountService, private templateRef: TemplateRef<any>, private viewContainerRef: ViewContainerRef) {}

  @Input()
  set jhiProfilePermission(value: string[]) {
    this.params = typeof value === 'string' ? [value] : value;
    this.updateView();
    // Get notified each time authentication state changes.
    this.accountService.getAuthenticationState().subscribe(identity => this.updateView());
  }

  private updateView(): void {
    //const hasAnyAuthority = this.accountService.hasAnyAuthority(this.params[0]);
    const hasAnyAuthority = this.accountService.isAuthenticated();
    this.viewContainerRef.clear();
    if (hasAnyAuthority) {
      this.viewContainerRef.createEmbeddedView(this.templateRef);
    }
  }
}
