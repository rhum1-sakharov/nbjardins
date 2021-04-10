import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';


@Component({
  selector: 'app-edit-text-field',
  templateUrl: './edit-text-field.component.html',
  styleUrls: ['./edit-text-field.component.scss']
})
export class EditTextFieldComponent implements OnInit {

  @Input() inputValue !: string;
  @Input() placeholder !: string;
  @Input() width !: number;
  @Output() emit = new EventEmitter<any>();

  @ViewChild('input') input !: ElementRef;

  isReadonly = true;

  public setReadOnly(isReadonly: boolean) {

    this.isReadonly = isReadonly;

    if (this.isReadonly) {
      this.emit.emit(this.inputValue);
    } else {


      setTimeout(() => {
        if (this.input) {
          this.input.nativeElement.focus();
          this.input.nativeElement.select();
        }
      }, 10);
    }
  }

  ngOnInit(): void {
  }
}
