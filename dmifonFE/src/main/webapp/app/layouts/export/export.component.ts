import { Component, Injectable, OnInit } from '@angular/core';
import { ExportProgettiService } from '../../services/export-progetti.service';
import { ExportImpegniService } from '../../services/export-impegni.service';
import { ExportCapitoliService } from '../../services/export-capitoli.service';
import { ExportDdrService } from '../../services/export-ddr.service';
import { ExportAvaFasService } from '../../services/export-ava-fas.service';
import { ExportManService } from '../../services/export-man.service';

@Injectable({
  providedIn: 'root',
})
@Component({
  selector: 'jhi-export',
  templateUrl: './export.component.html',
  styleUrls: ['./export.component.scss'],
})
export class ExportComponent implements OnInit {
  exportObj: any;
  showText: boolean;
  countExport = 0;

  exportEntity: string;

  constructor(
    protected exportService: ExportProgettiService,
    protected exportImpegniService: ExportImpegniService,
    protected exportCapitoliService: ExportCapitoliService,
    protected exportDdrService: ExportDdrService,
    protected exportAvaService: ExportAvaFasService,
    protected exportManService: ExportManService
  ) {
    this.exportObj = {};
    this.showText = true;
    this.exportEntity = '';
  }

  ngOnInit(): void {}

  startExport(): void {
    this.exportService.setShowError(false);
    this.countExport = this.exportService.getCountExport();
    if (this.exportEntity === 'PRO') {
      this.exportService.exportProgetti(this.exportObj).subscribe(
        res => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.downloadExport(res);
          this.exportService.setIsExport(false);
        },
        error => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.exportService.setIsExport(false);
          this.exportService.setShowError(true);
          //console.log(error);
        }
      );
    } else if (this.exportEntity === 'IMP') {
      this.exportImpegniService.exportImpegni(this.exportObj).subscribe(
        res => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.downloadExport(res);
          this.exportService.setIsExport(false);
          setTimeout(() => {
            this.exportService.setShowError(false);
          }, 4000);
        },
        error => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.exportService.setIsExport(false);
          this.exportService.setShowError(true);
          setTimeout(() => {
            this.exportService.setShowError(false);
          }, 4000);
          //console.log(error);
        }
      );
    } else if (this.exportEntity === 'CAP') {
      this.exportCapitoliService.exportCapitoli(this.exportObj).subscribe(
        res => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.downloadExport(res);
          this.exportService.setIsExport(false);
        },
        error => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.exportService.setIsExport(false);
          this.exportService.setShowError(true);
          setTimeout(() => {
            this.exportService.setShowError(false);
          }, 4000);
          //console.log(error);
        }
      );
    } else if (this.exportEntity === 'DDR') {
      this.exportDdrService.exportDdr(this.exportObj).subscribe(
        res => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.downloadExport(res);
          this.exportService.setIsExport(false);
        },
        error => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.exportService.setIsExport(false);
          this.exportService.setShowError(true);
          setTimeout(() => {
            this.exportService.setShowError(false);
          }, 4000);
          //console.log(error);
        }
      );
    } else if (this.exportEntity === 'AVA') {
      this.exportAvaService.exportAvanzamenti(this.exportObj).subscribe(
        res => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.downloadExport(res);
          this.exportService.setIsExport(false);
        },
        error => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.exportService.setIsExport(false);
          this.exportService.setShowError(true);
          setTimeout(() => {
            this.exportService.setShowError(false);
          }, 4000);
          //console.log(error);
        }
      );
    } else if (this.exportEntity === 'MAN') {
      this.exportManService.exportMandati(this.exportObj).subscribe(
        res => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.downloadExport(res);
          this.exportService.setIsExport(false);
        },
        error => {
          this.exportService.decrementCountExport();
          this.countExport = this.exportService.getCountExport();
          this.exportService.setIsExport(false);
          this.exportService.setShowError(true);
          setTimeout(() => {
            this.exportService.setShowError(false);
          }, 4000);
          //console.log(error);
        }
      );
    }
  }

  downloadExport(csv: any): void {
    const data = atob(csv.data);
    const byteNumbers = new Array(data.length);
    for (let i = 0; i < data.length; i++) {
      byteNumbers[i] = data.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: 'text/csv;charset=utf-8' });
    const link = document.createElement('a');
    if (link.download !== undefined) {
      // Browsers that support HTML5 download attribute
      const url = URL.createObjectURL(blob);
      link.setAttribute('href', url);
      link.setAttribute('download', csv.fileName);
      link.style.visibility = 'hidden';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  }

  collapseText(): void {
    this.showText = !this.showText;
  }
}
