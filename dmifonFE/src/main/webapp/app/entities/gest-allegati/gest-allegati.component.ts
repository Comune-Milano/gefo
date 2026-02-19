import { Component, Injector, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { AlertService } from 'app/shared/alert/alert.service';
import { TranslateService } from '@ngx-translate/core';
import { TipFinService } from 'app/services/tip-fin.service';
import { ItipFin } from 'app/models/tip_fin.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { debounceTime, distinctUntilChanged, map, Observable, OperatorFunction, Subject, takeUntil } from 'rxjs';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { FileHandle } from '../../shared/drag-drop/drag-drop.directive';
import { AllegatoService } from '../../services/allegato.service';
import { CommonPage } from '../../shared/baseclass/common-page';
import { IAllegatoResponse, IAmmAll } from '../../models/allegato.model';
import { JsogService } from 'jsog-typescript';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-gest-allegati',
  templateUrl: './gest-allegati.component.html',
  styleUrls: ['./gest-allegati.component.scss'],
})
export class GestAllegatiComponent extends CommonPage implements OnInit, IComponentCanDeactivate {
  mostraCard = false;
  formUpload!: FormGroup;
  formAllMod!: FormGroup;
  allegato: any = null;
  file: FileHandle[] = [];
  isFetchingAll = false;
  reader = new FileReader();
  idEnt!: number;
  isEditing!: number;
  curTipEnt = '';
  codDesPro!: string;
  listAllegati!: Array<IAmmAll>;
  mapTipEnt;
  jsog = new JsogService();
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private _fb: FormBuilder,
    private allegatoService: AllegatoService,
    private activatedRoute: ActivatedRoute
  ) {
    super(injector);
    this.mapTipEnt = new Map<string, string>([
      ['PRO', 'Progetto'],
      ['AVA', 'Avanzamento'],
      ['DDR', 'DDR'],
    ]);
  }

  ngOnInit(): void {
    this.allegato = null;
    this.isFetchingAll = false;
    this.curTipEnt = this.activatedRoute.snapshot.paramMap.get('tipEnt') ?? '';
    this.formUpload = this._fb.group({
      allegato: ['', [Validators.required]],
      nomfil: ['', [Validators.required, StringValidator.patternValidator()]],
      notfil: ['', [Validators.required, StringValidator.patternValidator()]],
      dataType: [''],
      blob: [''],
    });
    if (this.curTipEnt === 'AVA') {
      this.idEnt = this.activatedRoute.snapshot.params.idAva;
    } else if (this.curTipEnt === 'DDR') {
      this.idEnt = this.activatedRoute.snapshot.params.idDdr;
    } else {
      this.idEnt = this.activatedRoute.snapshot.params.id;
    }
    this.ricercaAllegati();
  }

  ricercaAllegati(): void {
    this.allegatoService
      .getAllegato(this.idEnt, this.curTipEnt)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          this.codDesPro = res.codice + ' - ' + res.descrizione;
          this.listAllegati = <IAmmAll[]>this.jsog.deserialize(res.allegati);
        },
        error => {
          this.listAllegati = [];
          if (error.error.userMessage !== 'La ricerca non ha prodotto risultati') {
            this.checkErrorStatus(error);
            this.showAlertError(error);
          }
        }
      );
  }

  canDeactivate(): Observable<boolean> | boolean {
    return true;
  }

  filesDropped(file: FileHandle): void {
    this.allegato = file.file;
    this.reader.readAsDataURL(this.allegato);
    this.reader.onloadend = () => {
      this.formUpload.patchValue({
        allegato: this.reader.result ? this.reader.result : 'prova',
        nomfil: this.allegato.name,
      }); // <-- Set Value for Validation
    };
  }

  loadingDragDrop(): void {
    const dragDropImage = document.getElementById('uploadChangeStateTarget');
    const text = document.getElementById('simText');
    dragDropImage?.classList.remove('loading');
    dragDropImage?.classList.remove('success');
    dragDropImage?.classList.add('loading');
    text!.innerText = 'Caricamento in corso...';
  }

  onFileChange($event: any): void {
    this.allegato = $event.target.files[0];
    this.reader.readAsDataURL(this.allegato);
    this.reader.onloadend = () => {
      this.formUpload.patchValue({
        allegato: this.reader.result ? this.reader.result : 'prova',
        nomfil: this.allegato.name,
      }); // <-- Set Value for Validation
    };
  }

  attivaCard(): void {
    this.mostraCard = true;
  }

  createAllObj(): any {
    const bs64 = String(this.formUpload.get('allegato')?.value).split(',');
    return {
      file: {
        dataType: bs64[0],
        file: bs64[1],
      },
      allegato: {
        tipent: this.curTipEnt,
        ident: Number(this.idEnt),
        nomfil: this.formUpload.get('nomfil')?.value,
        notfil: this.formUpload.get('notfil')?.value,
      },
    };
  }

  sendAllegato(): void {
    console.log(this.createAllObj());
    this.isFetchingAll = true;
    this.allegatoService
      .caricaFileAllegato(this.createAllObj())
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          console.log(res);
          document.getElementById('uploadChangeStateTarget')?.classList.add('success');
          this.isFetchingAll = false;
          this.showAlertMessage(res.userMessage);
          setTimeout(() => {
            this.disattivaCard();
            this.ngOnInit();
          }, 3000);
        },
        error => {
          this.checkErrorStatus(error);
          this.showAlertError(error);
          this.isFetchingAll = false;
        }
      );
  }

  scaricaAllegato(idFil: number, nomeFile: string): void {
    this.allegatoService
      .scaricaAllegato(idFil)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        const link = document.createElement('a');
        if (link.download !== undefined) {
          // Browsers that support HTML5 download attribute
          try {
            const url = res.file;
            link.setAttribute('href', url);
            link.setAttribute('download', nomeFile);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
          } catch (error) {
            this.showAlertError({ userMessage: error });
            console.error(error);
          }
        }
      });
  }

  cancellaAllegato(idAll: number): void {
    this.allegatoService
      .cancellaAllegato(idAll)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.viewAlert = true;
        this.showAlertMessage(res);
        this.ricercaAllegati();
      });
  }

  modificaAllegato(): void {
    this.allegatoService
      .modificaAllegato(this.formAllMod.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.viewAlert = true;
        this.showAlertMessage(res);
        this.formAllMod.reset();
        this.ricercaAllegati();
      });
  }

  annullaModificaAllegato(): void {
    this.formAllMod.reset();
  }

  createFormAllDaModificare(all: IAmmAll): void {
    this.formAllMod = this._fb.group({
      id: [all.id],
      tipent: [all.tipent],
      ident: [all.ident],
      idFil: [all.idFil],
      nomfil: [all.nomfil],
      notfil: [all.notfil],
    });
  }

  disattivaCard(): void {
    this.mostraCard = false;
    this.allegato = null;
    this.formUpload.reset();
  }

  ngOnDestroy(): void {
    super.closeAlert();
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
