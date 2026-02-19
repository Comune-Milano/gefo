import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { observable, Observable, of } from 'rxjs';
import { ModalConfirmComponent } from 'app/dialogs/modal-confirm/modal-confirm.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
export interface IComponentCanDeactivate {
	//canDeactivate: () => boolean | Observable<boolean>;
	// canDeactivate: () => Observable<boolean> | Promise<boolean> | boolean;
	canDeactivate: () => Observable<boolean> | boolean;
}


// Promise<Observable<boolean>>
@Injectable({
	providedIn: 'root'
})
export class UnSaveChangeGuard implements CanDeactivate<IComponentCanDeactivate> {

	modalConfirmRef!: NgbModalRef;
	//bresponse: Observable<boolean> = of(true);
	stringConfirmMessage!: string;
	constructor(
		private modalService: NgbModal,
		protected translateService: TranslateService,
	) { }

	canDeactivate(
		component: IComponentCanDeactivate,
		currentRoute: ActivatedRouteSnapshot,
		currentState: RouterStateSnapshot,
		nextState?: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
		if (component.canDeactivate()) {
			return true;
		}

		//this.bresponse = of(false);
		this.modalConfirmRef = this.modalService.open(ModalConfirmComponent, {
			windowClass: 'md-class',
			backdrop: 'static',
			keyboard: false,
			centered: true
		})
		return this.modalConfirmRef.result;
		/* this.modalConfirmRef.result.then(result => {
			  if (result === true) {
				this.bresponse = of(true);
				return this.bresponse;
			  } else {this.bresponse = of(false);
			  return this.bresponse};
			});
		return this.bresponse;*/
	}
}

