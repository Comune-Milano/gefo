import { AfterViewInit, Component, OnInit } from '@angular/core';
import { UsersService } from 'app/services/users.service';
import { IUsers } from 'app/models/usersModel';
import { delay, map, Observable, startWith, Subscription, withLatestFrom } from 'rxjs';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-profili',
  templateUrl: './profili.component.html',
  styleUrls: ['./profili.component.scss'],
})
export class ProfiliComponent implements OnInit, AfterViewInit {
  profili: IUsers[] = [];
  profiliFiltered: IUsers[] = [];
  profiliObs$!: Observable<IUsers[]>;
  profiliFiltered$: Observable<IUsers[]> | undefined;
  subscriptions: Subscription[] = [];
  subscription!: Subscription;
  langChangeSubscription?: Subscription;
  formGroup!: FormGroup;
  filter = new FormControl('', { nonNullable: true });

  constructor(private usersService: UsersService, private formBuilder: FormBuilder, private router: Router) {
    this.formGroup = this.formBuilder.group({ filter: [''] });
    this.profiliObs$ = this.usersService.getUtenti();
    this.profiliFiltered$ = this.usersService.getUtenti();
  }

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.profiliFiltered$ = this.filter.valueChanges.pipe(
      startWith(''),
      delay(300),
      withLatestFrom(this.profiliObs$),
      map(([val, user]) =>
        !val
          ? user
          : user.filter(
              x => x.cognome.toLowerCase().includes(val) || x.nome.toLowerCase().includes(val) || x.username.toLowerCase().includes(val)
            )
      )
    );
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub: { unsubscribe: () => any }) => sub.unsubscribe());
  }
}
