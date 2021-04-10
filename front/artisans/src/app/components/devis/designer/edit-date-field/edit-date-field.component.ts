import {Component, OnInit} from '@angular/core';
import {RvlEditField} from 'rhum1-sakharov-core-lib';

@Component({
  selector: 'app-edit-date-field',
  templateUrl: './edit-date-field.component.html',
  styleUrls: ['./edit-date-field.component.scss']
})
export class EditDateFieldComponent  extends RvlEditField implements OnInit {


  constructor() {
    super();
  }

  ngOnInit(): void {
  }




}

