import { Directive, HostBinding, HostListener, Output, EventEmitter } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

export interface FileHandle {
  file: File;
  url: SafeUrl;
}

@Directive({
  selector: '[jhiAppDrag]',
})
export class DragDropDirective {
  @Output() files: EventEmitter<FileHandle> = new EventEmitter();

  @HostBinding('style.opacity') private opacity = 1;

  constructor(private sanitizer: DomSanitizer) {}

  @HostListener('dragover', ['$event']) public onDragOver(evt: DragEvent): any {
    evt.preventDefault();
    evt.stopPropagation();
    this.opacity = 0.7;
  }

  @HostListener('dragleave', ['$event']) public onDragLeave(evt: DragEvent): any {
    evt.preventDefault();
    evt.stopPropagation();
    this.opacity = 1;
  }

  @HostListener('drop', ['$event']) public onDrop(evt: DragEvent): any {
    evt.preventDefault();
    evt.stopPropagation();
    this.opacity = 1;

    const files: FileHandle[] = [];
    // @ts-ignore
    const file = evt.dataTransfer.files[0];
    const url = this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(file));
    files.push({ file, url });
    if (files.length > 0) {
      this.files.emit({ file, url });
    }
  }
}
