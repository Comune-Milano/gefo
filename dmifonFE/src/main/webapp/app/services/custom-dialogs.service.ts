import { Injectable } from '@angular/core';
import { ModalConfirmComponent } from 'app/dialogs/modal-confirm/modal-confirm.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
	providedIn: 'root'
})
export class CustomDialogsService {

	modalConfirmRef!: NgbModalRef;

	constructor(
		private modalService: NgbModal,
		protected translateService: TranslateService
	) { }

	openCustomConfirm(message:string) : any {
		 let messageDialog : string;
		 messageDialog = '';
		 
		 messageDialog = message;
		 this.modalConfirmRef = this.modalService.open(ModalConfirmComponent, {
				windowClass: 'md-class',
				backdrop: 'static',
				keyboard: false,
				centered: true
			})
			if(messageDialog === "")
			{
			  messageDialog = this.translateService.instant('modals.generic-confirmation');	
			}
			this.modalConfirmRef.componentInstance.messages = [messageDialog];
			return this.modalConfirmRef; 
	}
}
