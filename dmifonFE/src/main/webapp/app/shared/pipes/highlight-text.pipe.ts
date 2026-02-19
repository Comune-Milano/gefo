import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Pipe({
  name: 'highlight',
})
export class HighlightPipe implements PipeTransform {
  constructor(private _sanitizer: DomSanitizer) {}
  transform(wholeText: string, searchQuery: string): SafeHtml {
    if (!searchQuery) {
      return wholeText;
    }
    const re = new RegExp(searchQuery, 'gi');
    return this._sanitizer.bypassSecurityTrustHtml(
      wholeText.replace(re, '<strong style="background-color: yellow;color: #333;">$&</strong>')
    );
  }
}
