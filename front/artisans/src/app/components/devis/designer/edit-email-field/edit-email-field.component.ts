import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';

@Component({
  selector: 'app-edit-email-field',
  templateUrl: './edit-email-field.component.html',
  styleUrls: ['./edit-email-field.component.scss']
})
export class EditEmailFieldComponent implements OnInit {

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
