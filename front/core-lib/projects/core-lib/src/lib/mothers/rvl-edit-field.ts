import {ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';

export class RvlEditField {

  @Input() inputValue !: string;
  @Input() placeholder !: string;
  @Input() width !: number;
  @Output() emit = new EventEmitter<any>();

  @ViewChild('input') input !: ElementRef;

  isReadonly = true;

  public setReadOnly(isReadonly: boolean) {

    this.isReadonly = isReadonly;

    if (this.isReadonly) {
      this.emit.emit(this.input);
    } else {

      if(this.input){
        setTimeout(() => {
          this.input.nativeElement.focus();
          this.input.nativeElement.select();
        }, 0);
      }
    }
  }

}
