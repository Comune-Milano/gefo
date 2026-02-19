import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor(
	  protected translateService: TranslateService
  ) { }
  
  //Genera il messaggio descrittivo dell'errore da mostrare agli utenti
  getErrorDescription(err: any) : string
	{
		let errDesc : string;
		errDesc = '';
		if(err?.error?.userMessage != null)
		{
			errDesc = err.error.userMessage;
		}
		//Verifica nel caso di errori jhipster
		else if(err?.error?.status != null && err.error.title != null)
		{
			errDesc = 'Error: '.concat(err.error.status, ' ' ,err.error.title); 
		}
		else if(err?.status != null && err.statusText != null)
		{
			errDesc = 'Error: '.concat(err.status, ' ' ,err.statusText); 
		}
		else
		{
			errDesc = this.translateService.instant('alert.genericError');	
		}
		return errDesc;
	}
}
