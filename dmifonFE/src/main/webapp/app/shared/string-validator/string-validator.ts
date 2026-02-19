import { AbstractControl, ValidatorFn } from '@angular/forms';

export class StringValidator {
  private static readonly excludedChars: Array<string> = ['\\', ':', '?', '$', '#', '&', '<', '>', '*', '[', ']', '{', '}'];
  private static readonly excludedWords: Array<string> = ['http', 'https', 'select', 'update', 'insert', 'delete', 'grant', 'drop'];

  static patternValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const input = encodeURIComponent(control.value);
      let valid = true;
      this.excludedChars.forEach(function (e) {
        if (input.includes(encodeURIComponent(e))) {
          valid = false;
        }
      });
      this.excludedWords.forEach(function (e) {
        if (input.includes(encodeURIComponent(e))) {
          valid = false;
        }
      });
      return valid ? null : { pattern: { value: control.value } };
    };
  }
}
