import { Directive, OnInit, ElementRef, HostListener, Renderer2, Input } from '@angular/core';
import { DecimalPipe } from '@angular/common';

@Directive({
  selector: '[importo]',
})
export class ImportoDirective implements OnInit {
  @Input() negativo?: boolean;

  dotChar = new RegExp('[.]', 'g'); // Carattere punto
  allowChar = new RegExp('/[^0-9.,]*/g');
  constructor(public el: ElementRef, public renderer: Renderer2, private decimalPipe: DecimalPipe) {}

  ngOnInit(): void {
    if (this.negativo) {
      this.renderer.setProperty(this.el.nativeElement, 'value', this.formatNeg(this.el.nativeElement.value));
    } else {
      this.renderer.setProperty(this.el.nativeElement, 'value', this.format(this.el.nativeElement.value));
    }
  }

  @HostListener('blur', ['$event']) onInputChange(event: any): void {
    let pos = this.el.nativeElement.selectionStart; // Posizione del cursore
    const initialValue: string = this.el.nativeElement.value || ''; // Testo iniziale
    if (this.negativo) {
      this.el.nativeElement.value = this.formatNeg(this.el.nativeElement.value);
    } else {
      this.el.nativeElement.value = this.format(this.el.nativeElement.value);
    }
    if (initialValue.length > this.el.nativeElement.value.length) {
      pos = pos - 1;
    } // Carattere non valido, sposto il cursore indietro

    this.el.nativeElement.setSelectionRange(pos, pos, 'none'); // Cursore nella posizione iniziale
    if (initialValue !== this.el.nativeElement.value) {
      event.stopPropagation(); // Fermo la propagazione dell'evento
    }
  }

  format(val: string): string {
    if (val === '') {
      val = '0';
    }
    if (val.includes(',')) {
      val = val.substring(0, val.indexOf(',') + 3); // Seleziono solo due decimali
    }
    val = val.replace(/[€!$(){}[\]:;<+?\\>]/g, '');
    val = val.replace(' ', '');
    const numberFormat = val
      .replace(/[^0-9.,]*/g, '')
      .replace(this.dotChar, '')
      .replace(/,/g, '.'); // Converto in number valido (il punto solo nei decimali)
    // const numberFormat = val.replace(this.allowChar, '').replace(this.dotChar, '').replace(/,/g, '.');
    return this.decimalPipe.transform(numberFormat, '1.2-2') ?? ''; // Trasformo
  }

  formatNeg(val: string): string {
    if (val === '') {
      val = '0';
    }
    if (val.includes(',')) {
      val = val.substring(0, val.indexOf(',') + 3); // Seleziono solo due decimali
    }
    val = val.replace(/[€!$(){}[\]:;<+?\\>]/g, '');
    val = val.replace(' ', '');
    const numberFormat = val
      .replace(/[^0-9.,]*/g, '')
      .replace(this.dotChar, '')
      .replace(/-/g, '')
      .replace(/,/g, '.'); // Converto in number valido (il punto solo nei decimali)
    // const numberFormat = val.replace(this.allowChar, '').replace(this.dotChar, '').replace(/,/g, '.');
    if (this.checkValore(val) < 0) {
      return '-' + this.decimalPipe.transform(numberFormat, '1.2-2'); // Trasformo
    } else {
      return this.decimalPipe.transform(numberFormat, '1.2-2'); // Trasformo
    }
  }

  checkValore(val: string): number {
    if (val.startsWith('-')) {
      val = val.replace(/-/g, '');
      val = '-' + val;
    }
    return Number.parseFloat(val.toString().replace(/,/g, '.'));
  }
}
