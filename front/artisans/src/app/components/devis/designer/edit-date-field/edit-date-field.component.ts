import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {DateUtils} from 'rhum1-sakharov-core-lib';
import {Calendar} from 'primeng/calendar';

@Component({
  selector: 'app-edit-date-field',
  templateUrl: './edit-date-field.component.html',
  styleUrls: ['./edit-date-field.component.scss']
})
export class EditDateFieldComponent implements OnInit {

  _inputValue !: Date;

  @Input() set inputValue(value: string) {
    this._inputValue = DateUtils.getDateFromIso(value);
  }

  @Input() placeholder !: string;
  @Input() width !: number;
  @Output() emit = new EventEmitter<any>();

  @ViewChild('input') input !: Calendar;

  isReadonly = true;

  constructor() {

  }

  public setReadOnly(isReadonly: boolean) {

    this.isReadonly = isReadonly;

    if (this.isReadonly) {

      this.emit.emit(DateUtils.getIsoFromDate(this._inputValue));
    } else {

      setTimeout(() => {
        if (this.input) {
          this.input.inputfieldViewChild.nativeElement.click();
        }
      }, 10);
    }
  }


  ngOnInit(): void {
  }
}

