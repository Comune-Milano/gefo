import {
  Directive,
  ElementRef,
  HostListener
  // Renderer2
} from '@angular/core';
@Directive({
  selector: '[jhiSelectedLink]',
  exportAs: 'jhiSelectedLink'
})
export class SelectedLinkDirective {
  status = 'closed';
  icon = 'plus';
  constructor(
    private elem: ElementRef<HTMLElement>
    ) {}

  @HostListener('click',['$event']) onClick(e:MouseEvent) {
    const ul = this.elem.nativeElement.querySelector('ul')
    if(ul) {
      ul.classList.toggle('d-none');
      if(ul.classList.contains('d-none')){
        this.icon = 'plus';
      }
      else {
		  this.icon = 'minus';
	  }
      e.stopPropagation();
    }
 }
 checkStatus() : string {
   return this.icon;
 }
}