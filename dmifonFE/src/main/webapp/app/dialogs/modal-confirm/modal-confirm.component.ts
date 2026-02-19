import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder } from '@angular/forms';
@Component({
  selector: 'jhi-modal-confirm',
  templateUrl: './modal-confirm.component.html',
  styleUrls: ['./modal-confirm.component.scss']
})
export class ModalConfirmComponent implements OnInit {
@Input() title?: string;

  messages = [];

  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit(): void  {
	  if(this.title == null)
	  {this.title ="";}
  }

  confirm():void {
    this.activeModal.close(true);
  }

  clear():void {
    this.activeModal.close(false);
  }
}

