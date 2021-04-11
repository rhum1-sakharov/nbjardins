import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {InputNumber} from 'primeng/inputnumber';

@Component({
  selector: 'app-edit-decimal-field',
  templateUrl: './edit-decimal-field.component.html',
  styleUrls: ['./edit-decimal-field.component.scss']
})
export class EditDecimalFieldComponent implements OnInit {

  @Input() inputValue !: number;
  @Input() placeholder !: string;
  @Input() width !: number;
  @Output() emit = new EventEmitter<any>();

  @ViewChild('input') input !: InputNumber;

  isReadonly = true;

  public setReadOnly(isReadonly: boolean) {

    this.isReadonly = isReadonly;

    if (this.isReadonly) {
      this.emit.emit(this.inputValue);
    } else {


      setTimeout(() => {
        if (this.input) {

          this.input.input.nativeElement.focus();
          this.input.input.nativeElement.select();
        }
      }, 10);
    }
  }

  ngOnInit(): void {
  }


}
