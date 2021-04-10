import {Component, OnInit} from '@angular/core';
import {RvlEditField} from 'rhum1-sakharov-core-lib';

@Component({
  selector: 'app-edit-text-field',
  templateUrl: './edit-text-field.component.html',
  styleUrls: ['./edit-text-field.component.scss']
})
export class EditTextFieldComponent extends RvlEditField implements OnInit {


  constructor() {
    super();
  }

  ngOnInit(): void {
  }




}
