import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';

@Component({
  selector: 'app-edit-text-field',
  templateUrl: './edit-text-field.component.html',
  styleUrls: ['./edit-text-field.component.scss']
})
export class EditTextFieldComponent implements OnInit {

  @Input() inputValue: any;
  @Input() width: number;

  @Output() emit = new EventEmitter<string>();

  isReadonly = true;

  @ViewChild('input') input: ElementRef;

  constructor() {
  }

  ngOnInit(): void {
  }

  setReadOnly(isReadonly: boolean) {

    this.isReadonly = isReadonly;

    if (this.isReadonly) {
      this.emit.emit(this.inputValue);
    } else {
      setTimeout(() => {
        this.input.nativeElement.focus();
        this.input.nativeElement.select();
      }, 0);
    }
  }


}
