import {Component, Input, OnInit} from '@angular/core';
import {ObjectUtils, TYPE_FIELD} from 'rhum1-sakharov-core-lib';


@Component({
  selector: 'app-block-edit-field',
  templateUrl: './block-edit-field.component.html',
  styleUrls: ['./block-edit-field.component.scss']
})
export class BlockEditFieldComponent implements OnInit {

  readonly objectUtils = ObjectUtils;
  readonly tf = TYPE_FIELD;

  @Input() inputValue !: string;
  @Input() placeholder !: string;
  @Input() width !: number;
  @Input() type !: TYPE_FIELD;

  ngOnInit(): void {
  }
}

